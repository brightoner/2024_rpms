package kr.go.rastech.commons.atchFile.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

import egovframework.cmmn.service.EgovProperties;
import kr.go.rastech.base.service.BaseServiceImpl;
import kr.go.rastech.commons.atchFile.dao.AtchFileDao;
import kr.go.rastech.commons.atchFile.vo.AtchFileVO;
import kr.go.rastech.commons.utils.FileUploadUtil;


/**
 * 첨부파일 처리를 위한 ServiceImpl
 * @author user
 *
 */
@Service
public class AtchFileServiceImpl extends BaseServiceImpl implements AtchFileService{


	@Resource(name = "atchFileDao")
	private AtchFileDao atchFileDao;

	
	/**
	 * 2023. 4. 28
	 * ljk
	 * 첨부파일 저장
	 * @param uploadFile
	 * @param request
	 * @param model
	 * @param type
	 * @param atchFileVo
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
    @Override
	public void goInsertAtchFile(@RequestParam("uploadFile") List<MultipartFile> uploadFile, HttpServletRequest request, Model model, String type, AtchFileVO atchFileVo)
			throws IOException, SQLException, NullPointerException {

    	//FileUploadUtil 객체생성
		FileUploadUtil fileUploadUtil = new FileUploadUtil();

		String fileGroup =  request.getParameter("fileGroup");
		String fileGb = request.getParameter("fileGb");

		atchFileVo.setFileGroup(fileGroup);
		atchFileVo.setFileGb(fileGb);

		//file_id 를 얻기
		int subFileId = atchFileDao.getFileId(atchFileVo);	//fileGroup
		// global.properties 에서 경로 가져오기
	    String  storePathString = EgovProperties.getProperty("Commons.uploadStorePath");

		UUID uuid = null;
		// 년도 구하기 (년도 폴더 생성을 위해)
		Calendar cal = Calendar.getInstance();

		String realSavedDir = String.format("%s/%s/%s/",storePathString, fileGb, cal.get(Calendar.YEAR));	//파일경로 : storePathString/fileGb/해당년도
		String realSaveFilePath;
		String fileNm;
		MultipartFile upfile;

		for(int i = 0; i < uploadFile.size(); i++) {

			subFileId = subFileId + 1;

			upfile = uploadFile.get(i);
			uuid = UUID.randomUUID();
			String orgnFileNm = uploadFile.get(i).getOriginalFilename();
			int index = orgnFileNm.lastIndexOf(".");
			String fileExt = orgnFileNm.substring(index + 1);

			fileNm = String.format("%s.%s", uuid.toString(), fileExt);
			realSaveFilePath = String.format("%s", realSavedDir);
    		String filePath = fileUploadUtil.fileUpload(request, upfile, realSaveFilePath, fileNm );
			long fileSize = uploadFile.get(i).getSize();

			atchFileVo.setSubFileId(subFileId);
			atchFileVo.setFilePath(filePath);
			atchFileVo.setOrgnFileNm(orgnFileNm);
			atchFileVo.setFileNm(fileNm);
			atchFileVo.setFileExt(fileExt);
			atchFileVo.setFileSize((int)fileSize);

			atchFileDao.insetAttFile(atchFileVo);	// 첨부파일 저장

		}
		model.addAttribute("fileGroup", fileGroup);
	}

    /**
	 * 2023. 4. 28
	 * ljk
	 * VOD 용 첨부파일 저장
	 * @param uploadFile
	 * @param request
	 * @param model
	 * @param type
	 * @param atchFileVo
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
    @Override
	public void goInsertVodAtchFile(@RequestParam("uploadFile") List<MultipartFile> uploadFile, HttpServletRequest request, Model model, String type, AtchFileVO atchFileVo)
			throws IOException, SQLException, NullPointerException {

    	// SFTP 서버 연결 정보 및 vod 관련  설정
        String  server = EgovProperties.getProperty("Commons.vodServer"); 
        int  port = Integer.parseInt(EgovProperties.getProperty("Commons.vodPort")); 
        String  username = EgovProperties.getProperty("Commons.vodId"); 
        String  password =  EgovProperties.getProperty("Commons.vodPass"); 
        String  vodFFmpegPath = EgovProperties.getProperty("Commons.vodFFmpegPath");
        String  uploadLocalStorePath = EgovProperties.getProperty("Commons.vodLocalStorePath");  // 운영에 먼저 vod를 업로드하여 인코딩 체크
                
        ChannelSftp sftpChannel = null;
        Session session = null;
        BufferedReader reader= null;
        Process process  =null;	
        
        String fileNm;
		MultipartFile upfile;
        UUID uuid = null;
		Calendar cal = Calendar.getInstance();		// 년도 구하기 (년도 폴더 생성을 위해)
		
		FileUploadUtil fileUploadUtil = new FileUploadUtil();
		
        try {
       
	        
			String fileGroup =  request.getParameter("fileGroup");
			String fileGb = request.getParameter("fileGb");

			uuid = UUID.randomUUID();			
			String orgnFileNm = uploadFile.get(0).getOriginalFilename();
			int index = orgnFileNm.lastIndexOf(".");
			String fileExt = orgnFileNm.substring(index + 1);
			long fileSize = uploadFile.get(0).getSize();			
			fileNm = String.format("%s.%s", uuid.toString(), fileExt);			
			upfile = uploadFile.get(0);
			
			
			// -----------------   서버에 VOD 파일을 업로드하여 우선 체크한다 코덱 정보 체크 STR  ---------------------- 
		
			String localSavedDir = String.format("%s/%s/%s/",uploadLocalStorePath, fileGb, cal.get(Calendar.YEAR));	//파일경로 : storePathString/fileGb/해당년
			// 운영 서버에 먼저 업로드 하여 영상을 체크한다.
            fileUploadUtil.fileUpload(request, upfile, localSavedDir, fileNm );
          
            //영상정보 확인 
            process = new ProcessBuilder(vodFFmpegPath , "-i",localSavedDir + fileNm).start();
            reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            String videoCodec = "";
            String audioCodec = "";
            while ((line = reader.readLine()) != null) {
            	 if (line.contains("Video:")) {
                     // 비디오 스트림 정보에서 비디오 코덱 추출
                     String[] parts = line.split(":");
                     String videoCodecInfo = parts[3].trim();
                     String[] codecParts = videoCodecInfo.split(" ");
                     videoCodec = codecParts[0];
                  //   System.out.println("Video Codec: " + videoCodec);
                 } else if (line.contains("Audio:")) {
                     // 오디오 스트림 정보에서 음성 코덱 추출
                     String[] parts = line.split(":");
                     String audioCodecInfo = parts[3].trim();
                     String[] codecParts = audioCodecInfo.split(" ");
                     audioCodec = codecParts[0];
                   //  System.out.println("Audio Codec: " + audioCodec);
                 }
            }
            process.waitFor();
            
            Boolean videoChk = false;
            Boolean audioChk = false;
        
            //video chk
            if(StringUtils.isNotBlank(videoCodec)) {            	
            	if(StringUtils.equals(videoCodec.toLowerCase()  , "h264") ){
            		videoChk = true;
            		            		
                }else {
                	videoChk = false;
                }
            }else{
            	
            	videoChk = false;
            	model.addAttribute("videoCodecNot", "N");
            }
        	model.addAttribute("videoCodecInfo", videoCodec);
            //audio chk            
            if(StringUtils.isNotBlank(audioCodec)) {            	
            	if(StringUtils.equals(audioCodec.toLowerCase()  , "aac") ){
            		audioChk = true;
                }else {        
                	audioChk = false;
                }
            }else{
            	audioChk = false;
            	model.addAttribute("audioCodecNot", "N");
            }
            model.addAttribute("audioCodecInfo", audioCodec);
			// -----------------   서버에 VOD 파일을 업로드하여 우선 체크한다 코덱 정보 체크 END  ----------------------
            
            
        	// 업로드 파일 인코딩 검증 했으니   운영 서버의 (wowza 아님 ) 업로드 파일을 삭제 한다.
	       	File fileToDelete = new File(localSavedDir + fileNm);
	       	try {
               if (fileToDelete.delete()) {
                   	logger.debug("파일이 성공적으로 삭제되었습니다.");
               } else {
              	   logger.debug("파일을 삭제하는 중에 오류가 발생했습니다.");
               }
            } catch (SecurityException e) {
           	   logger.debug("파일 삭제 권한이 없습니다.");
            }
            
            // 코덱 검증 되었을때면 wowza 에 전송한다.
            
            if(videoChk == true && audioChk ==true ) {
	        	// -----------------   WOWZA SFTP 서버에 업로드 STR  ----------------------
			 	// SFTP 접속 S
		    	JSch jsch = new JSch();
		        session = jsch.getSession(username, server, port);
		        session.setPassword(password);
		
		          // 호스트 키 검사를 비활성화합니다 (보안 주의)
		        Properties config = new Properties();
		        config.put("StrictHostKeyChecking", "no");
		        session.setConfig(config);
		
		        session.connect();
		        sftpChannel = (ChannelSftp) session.openChannel("sftp");
		        sftpChannel.connect();
	        	// SFTP 접속 E	    		        
	
				
				String realSavedDir = String.format("%s/%s/",  fileGb, cal.get(Calendar.YEAR));	//파일경로 : boardGb/해당년도 ,     //중요 ** 서버에 boardGb 폴더까지는 생성이 되어있어야한다.			
				// 디렉토리 생성
			    try {
			        sftpChannel.ls(realSavedDir);
			    } catch (SftpException e) {
			        // 디렉터리가 존재하지 않는 경우 예외가 발생하므로 디렉터리 생성
			    	logger.debug("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 여기에서 발생하는 exception은 폴더를 만들기 위함임으로 무시해도 된다 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			        sftpChannel.mkdir(realSavedDir);
			    }          
	           // SFTP 서버로 파일 업로드            
	            InputStream inputStream = upfile.getInputStream();
	
	            // 원격 디렉터리로 이동
	            sftpChannel.cd(realSavedDir);
	            // 파일 업로드
	            sftpChannel.put(inputStream, fileNm);
	
	            inputStream.close();
	           
	         
	
	        	// -----------------   WOWZA SFTP 서버에 업로드 END   ----------------------
	      
	            
	        	// -----------------   DB 저장 STR   ----------------------
	            atchFileVo.setFileGroup(fileGroup);
				atchFileVo.setFileGb(fileGb);
				
	           //file_id 를 얻기
				int fileId = atchFileDao.getFileId(atchFileVo);	//fileGroup
		 
	                
		          	// db 저장 		
				atchFileVo.setFileId(fileId + 1);
				atchFileVo.setFilePath(realSavedDir);
				atchFileVo.setOrgnFileNm(orgnFileNm);
				atchFileVo.setFileNm(fileNm);
				atchFileVo.setFileExt(fileExt);
				atchFileVo.setFileSize((int)fileSize);
	
				atchFileDao.insetAttFile(atchFileVo);	// 첨부파일 저장
	        	// -----------------   DB 저장 END   ----------------------
				
				model.addAttribute("vodUploadStatus", "Y");
            }else {
            
            	model.addAttribute("vodUploadStatus", "F");
            }
        
			
        } catch (Exception e) {
        	model.addAttribute("vodUploadStatus", "E1");
            e.printStackTrace(); // 예외 처리 로그 출력
        } finally {
        	
            // sFTP 클라이언트 연결 종료
            try {
            	
            	if(reader != null) {
            	  reader.close();
            	}
        	    if (process != null) {
                      process.destroy();
                }
        	    if(sftpChannel != null) {
	                if (sftpChannel.isConnected()) {
	                	
	                	sftpChannel.disconnect();
	                }
                }
        	    if(session!= null) {
	                if (session.isConnected()) {
	                	
	                	session.disconnect();
	                }
        	    }
            } catch (Exception e) {
              	model.addAttribute("vodUploadStatus", "E2");
                e.printStackTrace(); // 예외 처리 로그 출력
            }
        }		
	}
    
    
    /**
     * 2023. 4. 28
	 * ljk
	 * 첨부파일 조회
	 * @param atchFileVO
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws NullPointerException
	 */
	@Override
	public List<Map<String, Object>> selectAtchFileDetail(AtchFileVO atchFileVo) throws SQLException, IOException, NullPointerException {
		List<Map<String, Object>> detailMap = atchFileDao.selectAtchFileDetail(atchFileVo);
		return detailMap;
	}


	/**
	 * 2023. 4. 28
	 * ljk
	 * 첨부파일 수정시 특정 첨부파일 삭제
	 * @param atchFileVO
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	@Override
	public void deleteAttFile(AtchFileVO atchFileVO) throws IOException, SQLException, NullPointerException {
		atchFileDao.deleteAttFile(atchFileVO);
	}


	/**
	 * 2023. 4. 28
	 * ljk
	 * 특정 첨부파일 조회
	 * @param atchFileVO
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 * @throws NullPointerException
	 */
	@Override
	public Map<String, Object> getFileDetail(AtchFileVO atchFileVO) throws SQLException, IOException, NullPointerException {
		Map<String, Object> detailMap = atchFileDao.getFileDetail(atchFileVO);
		return detailMap;
	}



}
