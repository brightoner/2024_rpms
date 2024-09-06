package egovframework.cmmn.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import egovframework.cmmn.EgovWebUtil;
import egovframework.rte.fdl.cmmn.exception.FdlException;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

/**
 * <pre>
 *  메시지 처리 관련 유틸리티
 * 
 * </pre>
 * @FileName : EgovFileMngUtil.java
 * @package  : egovframework.cmmn.service
 * @author   : user
 * @date     : 2018. 7. 10.
 * 
 */
@Component("EgovFileMngUtil")
public class EgovFileMngUtil {

    public static final int BUFF_SIZE = 2048;


    @Resource(name = "egovFileIdGnrService")
    private EgovIdGnrService idgenService;

    private static final Logger LOG = Logger.getLogger(EgovFileMngUtil.class.getName());

    /**
     * 첨부파일에 대한 목록 정보를 취득한다.
     *
     * @param files
     * @return
     * @throws IOException, SQLException , NullPointerException
     * @throws FdlException 
     */
    public List<FileVO> parseFileInf(Map<String, MultipartFile> files, String keyStr, int fileKeyParam, String atchFileId, String storePath) throws IOException, SQLException , NullPointerException, FdlException {
    	int fileKey = fileKeyParam;
    
    	String storePathString = "";
    	String atchFileIdString = "";
    
    	if ("".equals(storePath) || storePath == null) {
    	    storePathString = EgovProperties.getProperty("Globals.fileStorePath");
    	} else {
    	    storePathString = EgovProperties.getProperty(storePath);
    	}
    
    	String monthDir = new SimpleDateFormat("yyyyMM").format(new Date());
    	
    	storePathString = storePathString + File.separator + monthDir;
    	
    	if ("".equals(atchFileId) || atchFileId == null) {
    	    atchFileIdString = idgenService.getNextStringId();
    	} else {
    	    atchFileIdString = atchFileId;
    	}
    
    	File saveFolder = new File(EgovWebUtil.filePathBlackList(storePathString));
    	
    	if (!saveFolder.exists() || saveFolder.isFile()) {
    		saveFolder.setExecutable(false);
        	saveFolder.setReadable(true);
        	saveFolder.setWritable(false);
    	    saveFolder.mkdirs();
    	}
    
    	Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
    	MultipartFile file;
    	String filePath = "";
    	List<FileVO> result  = new ArrayList<FileVO>();
    	FileVO fvo;
    
    	while (itr.hasNext()) {
    	    Entry<String, MultipartFile> entry = itr.next();
    
    	    file = entry.getValue();
    	    String orginFileName = file.getOriginalFilename();
    
    	    //--------------------------------------
    	    // 원 파일명이 없는 경우 처리
    	    // (첨부가 되지 않은 input file type)
    	    //--------------------------------------
    	    if ("".equals(orginFileName)) {
    	        continue;
    	    }
    	    ////------------------------------------
    
    	    int index = orginFileName.lastIndexOf(".");
    	    //String fileName = orginFileName.substring(0, index);
    	    String fileExt = orginFileName.substring(index + 1);
    	    String newName = 	keyStr + getTimeStamp() + fileKey;
    	    long file_size = file.getSize();
    
    	    if (!"".equals(orginFileName)) {	        
    	        if ("xls".equalsIgnoreCase(fileExt)
                        || "xlsx".equalsIgnoreCase(fileExt)
                        || "doc".equalsIgnoreCase(fileExt)
                        || "docx".equalsIgnoreCase(fileExt)
                        || "hwp".equalsIgnoreCase(fileExt)
                        || "ppt".equalsIgnoreCase(fileExt)
                        || "pptx".equalsIgnoreCase(fileExt)
                        || "txt".equalsIgnoreCase(fileExt)
                        || "gif".equalsIgnoreCase(fileExt)
                        || "jpg".equalsIgnoreCase(fileExt)
                        || "jpeg".equalsIgnoreCase(fileExt)
                        || "png".equalsIgnoreCase(fileExt)
                        || "zip".equalsIgnoreCase(fileExt)
                        || "bmp".equalsIgnoreCase(fileExt)
                        || "pdf".equalsIgnoreCase(fileExt)) {	            
    	       
            		filePath = storePathString + File.separator + newName;
            		file.transferTo(new File(EgovWebUtil.filePathBlackList(filePath)));
            		
            		fvo = new FileVO();
                    fvo.setFileExtsn(fileExt);
                    fvo.setFileStreCours(storePathString);
                    fvo.setFileMg(Long.toString(file_size));
                    fvo.setOrignlFileNm(orginFileName);
                    fvo.setStreFileNm(newName);
                    fvo.setAtchFileId(atchFileIdString);
                    fvo.setFileSn(String.valueOf(fileKey));
                    fvo.setMonthDir(monthDir);
            
                    //writeFile(file, newName, storePathString);
                    result.add(fvo);
            
                    fileKey++;
    	        }
    	    }
    	}
    
    	return result;
    }

    /**
     * 첨부파일을 서버에 저장한다.
     *
     * @param file
     * @param newName
     * @param stordFilePath
     * @throws IOException, SQLException , NullPointerException
     */
    protected void writeUploadedFile(MultipartFile file, String newName, String stordFilePath) throws IOException, SQLException , NullPointerException {
    	InputStream stream = null;
    	OutputStream bos = null;
    
    	try {
    	    stream = file.getInputStream();
    	    File cFile = new File(stordFilePath);
    
    	    if (!cFile.isDirectory()) {
    	    	cFile.setExecutable(false);
    	    	cFile.setReadable(true);
    	    	cFile.setWritable(false);
        		boolean fileFlag = cFile.mkdir();
        		if (!fileFlag) {
        		    throw new IOException("Directory creation Failed ");
        		}
    	    }
    
    	    bos = new FileOutputStream(stordFilePath + File.separator + newName);
    
    	    int bytesRead = 0;
    	    byte[] buffer = new byte[BUFF_SIZE];
    
    	    while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
    	        bos.write(buffer, 0, bytesRead);
    	    }
    	} catch (IOException e) {
    	    LOG.error("IGNORE writeUploadedFile");	// 2011.10.10 보안점검 후속조치
    	} finally {
    	    if (bos != null) {
        		try {
        		    bos.close();
        		} catch (IOException ignore) {
        		    LOG.debug("IGNORED writeUploadedFile");
        		}
    	    }
    	    if (stream != null) {
        		try {
        		    stream.close();
        		} catch (IOException ignore) {
        		    LOG.debug("IGNORED writeUploadedFile");
        		}
    	    }
    	}
    }

    /**
     * 서버의 파일을 다운로드한다.
     *
     * @param request
     * @param response
     * @throws IOException, SQLException , NullPointerException
     */
    public static void downFile(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException , NullPointerException {

    	String downFileName = "";
    	String orgFileName = "";
    
    	if ((String)request.getAttribute("downFile") == null) {
    	    downFileName = "";
    	} else {
    	    downFileName = (String)request.getAttribute("downFile");
    	}
    
    	if ((String)request.getAttribute("orgFileName") == null) {
    	    orgFileName = "";
    	} else {
    	    orgFileName = (String)request.getAttribute("orginFile");
    	}
    
    	orgFileName = orgFileName.replaceAll("\r", "").replaceAll("\n", "");
    
    	File file = new File(EgovWebUtil.filePathBlackList(downFileName));
    
    	if (!file.exists()) {
    	    throw new FileNotFoundException(downFileName);
    	}
    
    	if (!file.isFile()) {
    	    throw new FileNotFoundException(downFileName);
    	}
    
    	byte[] b = new byte[BUFF_SIZE]; //buffer size 2K.
    
    	response.setContentType("application/x-msdownload");
    	response.setHeader("Content-Disposition:", "attachment; filename=" + new String(orgFileName.getBytes(), "EUC-KR"));
    	response.setHeader("Content-Transfer-Encoding", "binary");
    	response.setHeader("Pragma", "no-cache");
    	response.setHeader("Expires", "0");
    
    	BufferedInputStream fin = null;
    	BufferedOutputStream outs = null;
    	FileInputStream in = null;
    	try {
    		in = new FileInputStream(file);
    		fin = new BufferedInputStream(in);
    	    outs = new BufferedOutputStream(response.getOutputStream());
    	    int read = 0;
    
    		while ((read = fin.read(b)) != -1) {
    		    outs.write(b, 0, read);
    		}
    	} finally {
    	    if (outs != null) {
    			try {
    			    outs.close();
    			} catch (IOException ignore) {
    			
    			    LOG.debug("err downFile");
    			}
		    }
		    if (fin != null) {
    			try {
    			    fin.close();
    			} catch (IOException ignore) {
    			   
    			    LOG.debug("err downFile");
    			}
		    }
		    if (in != null) {
    			try {
    			    in.close();
    			} catch (IOException ignore) {
    			   
    			    LOG.debug("err downFile");
    			}
		    }
		}
    }

    /**
     * 첨부로 등록된 파일을 서버에 업로드한다.
     *
     * @param file
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    public static HashMap<String, String> uploadFile(MultipartFile file) throws IOException, SQLException , NullPointerException {

    	HashMap<String, String> map = new HashMap<String, String>();
    	//Write File 이후 Move File????
    	String newName = "";
    	String stordFilePath = EgovProperties.getProperty("Globals.fileStorePath");
    	String orginFileName = file.getOriginalFilename();
    
    	int index = orginFileName.lastIndexOf(".");
    	//String fileName = orginFileName.substring(0, _index);
    	String fileExt = orginFileName.substring(index + 1);
    	long size = file.getSize();
    
    	//newName 은 Naming Convention에 의해서 생성
    	newName = getTimeStamp() + "." + fileExt;
    	writeFile(file, newName, stordFilePath);
    	//storedFilePath는 지정
    	map.put(Globals.ORIGIN_FILE_NM, orginFileName);
    	map.put(Globals.UPLOAD_FILE_NM, newName);
    	map.put(Globals.FILE_EXT, fileExt);
    	map.put(Globals.FILE_PATH, stordFilePath);
    	map.put(Globals.FILE_SIZE, String.valueOf(size));
    
    	return map;
    }

    /**
     * 파일을 실제 물리적인 경로에 생성한다.
     *
     * @param file
     * @param newName
     * @param stordFilePath
     * @throws IOException, SQLException , NullPointerException
     */
    protected static void writeFile(MultipartFile file, String newName, String stordFilePath) throws IOException, SQLException , NullPointerException {
    	InputStream stream = null;
    	OutputStream bos = null;
    
    	try {
    	    stream = file.getInputStream();
    	    File cFile = new File(EgovWebUtil.filePathBlackList(stordFilePath));
    
    	    if (!cFile.isDirectory()){
    	    	cFile.setExecutable(false);
    	    	cFile.setReadable(true);
    	    	cFile.setWritable(false);
    	    	
    	    	cFile.mkdir();
    	    }
    
    	    bos = new FileOutputStream(EgovWebUtil.filePathBlackList(stordFilePath + File.separator + newName));
    
    	    int bytesRead = 0;
    	    byte[] buffer = new byte[BUFF_SIZE];
    
    	    while ((bytesRead = stream.read(buffer, 0, BUFF_SIZE)) != -1) {
    		bos.write(buffer, 0, bytesRead);
    	    }
    	} catch (IOException e) {
    		Logger.getLogger(EgovFileMngUtil.class).debug("IGNORED writeFile");
    	} finally {
    	    if (bos != null) {
        		try {
        		    bos.close();
        		} catch (IOException ignore) {
        		    Logger.getLogger(EgovFileMngUtil.class).debug("IGNORED writeFile");
        		}
    	    }
    	    if (stream != null) {
        		try {
        		    stream.close();
        		} catch (IOException ignore) {
        		    Logger.getLogger(EgovFileMngUtil.class).debug("IGNORED writeFile");
        		}
    	    }
    	}
    }

    /**
     * 서버 파일에 대하여 다운로드를 처리한다.
     *
     * @param response
     * @param streFileNm
     *            : 파일저장 경로가 포함된 형태
     * @param orignFileNm
     * @throws IOException, SQLException , NullPointerException
     */
    public void downFile(HttpServletResponse response, String streFileNm, String orignFileNm) throws IOException, SQLException , NullPointerException {
    	String downFileName = streFileNm;
    	String orgFileName = orignFileNm;
    
    	File file = new File(downFileName);
    	//log.debug(this.getClass().getName()+" downFile downFileName "+downFileName);
    	//log.debug(this.getClass().getName()+" downFile orgFileName "+orgFileName);
    
    	if (!file.exists()) {
    	    throw new FileNotFoundException(downFileName);
    	}
    
    	if (!file.isFile()) {
    	    throw new FileNotFoundException(downFileName);
    	}
    
    	//byte[] b = new byte[BUFF_SIZE]; //buffer size 2K.
    	int fSize = (int)file.length();
    	if (fSize > 0) {
    	    BufferedInputStream in = null;
    	    FileInputStream fin = null;
    	    try {
    	    	fin = new FileInputStream(file);
    	        in = new BufferedInputStream(fin);
    
        	    String mimetype = "text/html"; //"application/x-msdownload"
    
        	    response.setBufferSize(fSize);
        		response.setContentType(mimetype);
        		response.setHeader("Content-Disposition:", "attachment; filename=" + orgFileName);
        		response.setContentLength(fSize);
        		//response.setHeader("Content-Transfer-Encoding","binary");
        		//response.setHeader("Pragma","no-cache");
        		//response.setHeader("Expires","0");
        		FileCopyUtils.copy(in, response.getOutputStream());
    	    } finally {
        		if (in != null) {
        			try{
        		        in.close();
        			}catch(IOException dd){
        			    LOG.debug("err downFile IOException");
        			}
        		
        		  
        		}
        		if (fin != null) {
        			try{
        				fin.close();
        			}catch(IOException dd){
        			    LOG.debug("err downFile IOException");
        			}
        		
    		  
        		}
    	    }
        	response.getOutputStream().flush();
        	response.getOutputStream().close();
    	}
    }

    /**
     * 2011.08.09
     * 공통 컴포넌트 utl.fcc 패키지와 Dependency제거를 위해 내부 메서드로 추가 정의함
     * 응용어플리케이션에서 고유값을 사용하기 위해 시스템에서17자리의TIMESTAMP값을 구하는 기능
     *
     * @param
     * @return Timestamp 값
     * @exception MyException
     * @see
     */
    private static String getTimeStamp() {

    	String rtnStr = null;
    
    	// 문자열로 변환하기 위한 패턴 설정(년도-월-일 시:분:초:초(자정이후 초))
    	String pattern = "yyyyMMddhhmmssSSS";
    
    	try {
    	    SimpleDateFormat sdfCurrent = new SimpleDateFormat(pattern, Locale.KOREA);
    	    Timestamp ts = new Timestamp(System.currentTimeMillis());
    
    	    rtnStr = sdfCurrent.format(ts.getTime());
    	}catch (IllegalArgumentException e) {
    	    LOG.debug("IGNORED: getTimeStamp IllegalArgumentException");
    	}
    
    	return rtnStr;
    }
    
    /**
     * 첨부파일을 디렉토리에 저장하지않고 DB의 BLOB 로 저장하도록 한다.
     *
     * @param files
     * @return
     * @throws FdlException 
     * @throws Exception
     */
    public List<FileVO> parseBLOBFileInf(Map<String, MultipartFile> files, String keyStr, int fileKeyParam, String atchFileId, String storePath) throws IOException, FdlException {
	int fileKey = fileKeyParam;

	String storePathString = "";
	String atchFileIdString = "";

	if ("".equals(storePath) || storePath == null) {
	    storePathString = EgovProperties.getProperty("Globals.fileStorePath");
	} else {
	    storePathString = EgovProperties.getProperty(storePath);
	}

	if ("".equals(atchFileId) || atchFileId == null) {
	    atchFileIdString = idgenService.getNextStringId();
	} else {
	    atchFileIdString = atchFileId;
	}

	File saveFolder = new File(EgovWebUtil.filePathBlackList(storePathString));
	
	if (!saveFolder.exists() || saveFolder.isFile()) {
		saveFolder.setExecutable(false);
		saveFolder.setReadable(true);
		saveFolder.setWritable(false);
	    saveFolder.mkdirs();
	}

	Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
	MultipartFile file;
	String filePath = "";
	List<FileVO> result  = new ArrayList<FileVO>();
	FileVO fvo;

	while (itr.hasNext()) {
	    Entry<String, MultipartFile> entry = itr.next();

	    file = entry.getValue();
	    String orginFileName = file.getOriginalFilename();

	    //--------------------------------------
	    // 원 파일명이 없는 경우 처리
	    // (첨부가 되지 않은 input file type)
	    //--------------------------------------
	    if ("".equals(orginFileName)) {
		continue;
	    }
	    ////------------------------------------

	    int index = orginFileName.lastIndexOf(".");
	    //String fileName = orginFileName.substring(0, index);
	    String fileExt = orginFileName.substring(index + 1);
	    String newName = keyStr + getTimeStamp() + fileKey;
	    long size = file.getSize();

		//	        실제파일 디렉터리에 생성하는 부분
//	    if (!"".equals(orginFileName)) {
//		filePath = storePathString + File.separator + newName;
//		file.transferTo(new File(EgovWebUtil.filePathBlackList(filePath)));
//	    }
	    fvo = new FileVO();
	    fvo.setFileExtsn(fileExt);
	    fvo.setFileStreCours(storePathString);
	    fvo.setFileMg(Long.toString(size));
	    fvo.setOrignlFileNm(orginFileName); 
	    fvo.setStreFileNm(newName);
	    fvo.setAtchFileId(atchFileIdString);
	    fvo.setFileSn(String.valueOf(fileKey));
	    fvo.setFileByte(file.getBytes());

	    //writeFile(file, newName, storePathString);
	    result.add(fvo);

	    fileKey++;
	}

	return result;
    }
}
