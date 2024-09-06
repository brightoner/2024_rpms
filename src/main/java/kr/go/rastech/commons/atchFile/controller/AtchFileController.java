package kr.go.rastech.commons.atchFile.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.atchFile.service.AtchFileService;
import kr.go.rastech.commons.atchFile.vo.AtchFileVO;
import kr.go.rastech.ptl.center.noti.vo.NotiVo;

/**
 * 첨부파일 처리를 위한 Controller
 * @author user
 *
 */
@Controller
public class AtchFileController extends BaseController{

	 protected Log logger = LogFactory.getLog(this.getClass());

	 
	 @Resource
	 private AtchFileService atchFileService;
	 

	 /**
     * 공지사항등록시 첨부파일등록팝업페이지 로드
     * @param request
     * @param model
     * @return
     * @throws SQLException
     * @throws IOException
     * @throws NullPointerException
     * @throws FdlException
     */
  @RequestMapping("/atchFile/CmmnRegistFilePopup.do")
	public String insertAlitCnPopup(HttpServletRequest request, Model model, NotiVo notiVo, AtchFileVO atchFileVo) throws  SQLException, IOException , NullPointerException {
    	String fileGroup = request.getParameter("file_group");
    	String fileGb = request.getParameter("file_gb");

    	atchFileVo.setFileGroup(fileGroup);
    	atchFileVo.setFileGb(fileGb);

    	List<Map<String, Object>> atchFileInfo = atchFileService.selectAtchFileDetail(atchFileVo);
    	model.addAttribute("fileGroup", fileGroup);
    	model.addAttribute("fileGb", fileGb);
    	model.addAttribute("atchFileInfo",atchFileInfo);
    	model.addAttribute("_callbacknm",request.getParameter("_callbacknm"));

    	return "cmm/atchFile/CmmnRegistFilePopup";
	}


    /**
     * 첨부파일 등록
     * @param uploadFile
     * @param request
     * @param model
     * @param type
     * @param fileVO
     * @return
     * @throws IOException
     * @throws SQLException
     * @throws NullPointerException
     */
    @RequestMapping("/atchFile/CmmnFileInsert.do")
	public String CmmnFileInsert(@RequestParam("uploadFile") List<MultipartFile> uploadFile, MultipartHttpServletRequest request, Model model, String type, AtchFileVO atchFileVo) throws  IOException, SQLException, NullPointerException {
    	atchFileService.goInsertAtchFile(uploadFile, request, model, type, atchFileVo);

    	return "jsonView";
    }

   

    /** 첨부파일 수정시에 특정 첨부파일 삭제
     * @param request
     * @param model
     * @param atchFileVO
     * @return
     * @throws Exception
     */
    @RequestMapping("/atchFile/CmmnFileDelete.do")
    public String atchFileDelete(HttpServletRequest request, Model model, AtchFileVO atchFileVO) throws Exception{
    	
    	String fileGroup = request.getParameter("fileGroup");
    	int subFileId = Integer.parseInt(request.getParameter("fileId"));
    	atchFileService.deleteAttFile(atchFileVO); 	// 특정 첨부파일 삭제

    	return "jsonView";
    }



    /**
     * 첨부파일 팝업창에서 파일 추가, 삭제시 부모창에 비동기적으로 최신화
     * @param model
     * @param atchFileId
     * @return
     * @throws IOException
     * @throws SQLException
     * @throws NullPointerException
     */
    @RequestMapping("/atchFile/CmmnFileList.do")
    public String selectAjaxAtchFileList(Model model, @RequestParam(value="fileGroup", required=true) String fileGroup, @RequestParam(value="fileGb", required=true) String fileGb) throws  IOException, SQLException, NullPointerException {
    	
    	AtchFileVO atchVo = new AtchFileVO();
    	atchVo.setFileGroup(fileGroup);
    	atchVo.setFileGb(fileGb);
    	List<Map<String, Object>> fileList = atchFileService.selectAtchFileDetail(atchVo);

    	model.addAttribute("atchFileList", fileList);
    	return "jsonView";
    }


	/**
	 * 첨부파일 다운로드
	 * @param request
	 * @param response
	 * @param atchFileVO
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	@RequestMapping("/atchFile/CmmnFileDown.do")
	public void downloadFileInfo( HttpServletRequest request, HttpServletResponse response, AtchFileVO atchFileVO) throws  IOException, SQLException, NullPointerException {
		
		int fileId = Integer.parseInt(request.getParameter("fileId_value"));
		atchFileVO.setFileId(fileId);

		Map<String, Object> atchFileMap = (Map<String, Object>) atchFileService.getFileDetail(atchFileVO);
		
		String fileNm = (String) atchFileMap.get("file_nm");
		String filePath = (String) atchFileMap.get("file_path");
		String realFilePath = String.format("%s/%s", filePath, fileNm);
		String orgnFileNm = (String) atchFileMap.get("orgn_file_nm");

		File file = new File(realFilePath);
		if(file.length() > 0) {
			FileInputStream fis = null;
			BufferedOutputStream bos = null;

			response.reset() ;
			response.setContentType("application/octet-stream");
			response.setHeader("Content-Description", "JSP Generated Data");
			String client = request.getHeader("User-Agent");

		  
			// 파일 다운로드시 원본이름을 받을경우
			// 로컬 환경
		    if (client.contains("Edge")){
		    	orgnFileNm = URLEncoder.encode(orgnFileNm, "UTF-8").replaceAll("\\+", "%20");
		    	response.setHeader("Content-Disposition", "attachment;filename=\"" + orgnFileNm + "\"");
		    } else if (client.contains("MSIE") || client.contains("Trident")) { // IE 11버전부터 Trident로 변경되었기때문에 추가해준다.
		    	orgnFileNm = URLEncoder.encode(orgnFileNm, "UTF-8").replaceAll("\\+", "%20");
		    	response.setHeader("Content-Disposition", "attachment;filename=" + orgnFileNm + ";");
		    } else if (client.contains("Chrome")) {
		    	orgnFileNm = new String(orgnFileNm.getBytes("UTF-8"), "ISO-8859-1");
		    	response.setHeader("Content-Disposition", "attachment; filename=\"" + orgnFileNm + "\"");
		    } else if (client.contains("Opera")) {
		    	orgnFileNm = new String(orgnFileNm.getBytes("UTF-8"), "ISO-8859-1");
		    	response.setHeader("Content-Disposition", "attachment; filename=\"" + orgnFileNm + "\"");
		    } else if (client.contains("Firefox")) {
		    	orgnFileNm = new String(orgnFileNm.getBytes("UTF-8"), "ISO-8859-1");
		    	response.setHeader("Content-Disposition", "attachment; filename=" + orgnFileNm);
		    }
		    // 운영 환경
		    /*
		    if (client.contains("Edge")){
		    	orgnFileNm = URLEncoder.encode(orgnFileNm, "UTF-8").replaceAll("\\+", "%20");
		    	response.setHeader("Content-Disposition", "attachment;filename=\"" + orgnFileNm + "\"");
		    } else if (client.contains("MSIE") || client.contains("Trident")) { // IE 11버전부터 Trident로 변경되었기때문에 추가해준다.
		    	orgnFileNm = URLEncoder.encode(orgnFileNm, "UTF-8").replaceAll("\\+", "%20");
		    	response.setHeader("Content-Disposition", "attachment;filename=" + orgnFileNm + ";");
		    } else if (client.contains("Chrome")) {
		    	orgnFileNm = new String(orgnFileNm.getBytes("UTF-8"), "UTF-8");
		    	response.setHeader("Content-Disposition", "attachment; filename=\"" + orgnFileNm + "\"");
		    } else if (client.contains("Opera")) {
		    	orgnFileNm = new String(orgnFileNm.getBytes("UTF-8"), "UTF-8");
		    	response.setHeader("Content-Disposition", "attachment; filename=\"" + orgnFileNm + "\"");
		    } else if (client.contains("Firefox")) {
		    	orgnFileNm = new String(orgnFileNm.getBytes("UTF-8"), "UTF-8");
		    	response.setHeader("Content-Disposition", "attachment; filename=" + orgnFileNm);
		    }
		    */
           response.setHeader ("Content-Length", ""+file.length() );

			try {
				fis = new FileInputStream(file);
				int size = fis.available();

				byte[] buf = new byte[size];
				int readCount = fis.read(buf);
				response.flushBuffer();

				bos = new BufferedOutputStream(response.getOutputStream());
				bos.write(buf, 0, readCount);
				bos.flush();
			} catch (IOException e) {
				logger.error("다운로드 실패");
				throw new IOException("다운로드실패");
			} finally{
				if(null != fis) fis.close();
				if(null != bos) bos.close();
			}
		} else {
			logger.error("파일을 찾을 수 없습니다.");
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");                  // 받을 때 한글 인코딩
            response.getOutputStream().write("<script language='javascript'>alert('파일을 찾을 수 없습니다');history.back();</script>".getBytes("UTF-8"));
		}
	}


}
