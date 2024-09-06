package kr.go.rastech.commons.imgFile.contoller;



import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.imgFile.service.ImgFileService;
import kr.go.rastech.commons.imgFile.vo.ImgFileVo;






/**
 * <pre>
 * FileName: BannerMngController.java
 * Package : kr.go.ncmiklib.ptl.mng.banner.controller
 * 
 * 배너관리 컨트롤러
 *
 * </pre>
 * @author : lwk
 * @date   : 2019. 3. 28.
 */
@Controller
public class ImgFileController extends BaseController {

	
    /** ImgFileService */
    @Resource
    private ImgFileService imgFileService;


    /**
     * 이미지 첨부파일에 대한 목록을 조회한다.
     * 
     * @param fileVO
     * @param atchFileId
     * @param sessionVO
     * @param model
     * @return
     * @throws SQLException , IOException, NullPointerException
     */
    @RequestMapping("/cmm/fms/selectImageFileInfs.do") 
    public String selectImageFileInfs(ImgFileVo imgFileVo, HttpServletRequest request, Model model) throws SQLException , IOException, NullPointerException {
	    
    	String atch_img_id = request.getParameter("atch_img_id"); 
    	String menuGbn = request.getParameter("menuGbn"); 
		
		if(StringUtils.isNotBlank((String) menuGbn)){
			menuGbn = (String) menuGbn;
		}
		
		imgFileVo.setAtch_img_id(atch_img_id);
		
		List<ImgFileVo> result = imgFileService.selectImageFileList(imgFileVo);
		
		/*int size = result.size()-1;
		// 사본은 맨 마지막
		
		for(int i=0; i<size;  i++) {
				result.remove(0);
		}*/
		
		model.addAttribute("fileList", result);	
		
		model.addAttribute("workGbn", menuGbn);	
	
		return "/cmm/imgFile/AtchImgFileList";
    }
    
    /**
     * 첨부된 이미지에 대한 미리보기 기능을 제공한다. 2014-05-15 DB의 BLOB 에서 이미지를 로딩함
     *
     * @param atchFileId
     * @param fileSn
     * @param sessionVO
     * @param model
     * @param response
     * @throws SQLException , IOException, NullPointerException
     */
    @RequestMapping("/cmm/fms/getImage.do")
    public void getImageInf( Model model, HttpServletRequest request, HttpServletResponse response) throws SQLException , IOException, NullPointerException {

		//@RequestParam("atchFileId") String atchFileId,
		//@RequestParam("fileSn") String fileSn,
		String atch_img_id =request.getParameter("atch_img_id");
		String fileSn = request.getParameter("fileSn");
	
		ImgFileVo vo = new ImgFileVo();
	
		vo.setAtch_img_id(atch_img_id);
		vo.setFile_sn(fileSn);
	
		ImgFileVo fvo = imgFileService.selectImgFileInf(vo);	
		BufferedInputStream in = null;
		ByteArrayInputStream bis = null;
		ByteArrayOutputStream bStream = null;
		
		if(fvo != null){		
			if(fvo.getFile_byte() != null){
				try {
					bis = new ByteArrayInputStream( fvo.getFile_byte());
			
				    in = new BufferedInputStream(bis);
				    bStream = new ByteArrayOutputStream();
			
				    int imgByte;
				    while ((imgByte = in.read()) != -1) {
					bStream.write(imgByte);
				    }
			
					String type = "";
				
					if (fvo.getFile_extsn()!= null && !"".equals(fvo.getFile_extsn())) {
					    if ("jpg".equals(fvo.getFile_extsn().toLowerCase())) {
						type = "image/jpeg";
					    } else {
						type = "image/" + fvo.getFile_extsn().toLowerCase();
					    }
					    type = "image/" + fvo.getFile_extsn().toLowerCase();
				
					} else {
						logger.debug("Image fileType is null.");
					}
				
					response.setHeader("Content-Type", type);
					response.setContentLength(bStream.size());
				
					bStream.writeTo(response.getOutputStream());
				
					response.getOutputStream().flush();
					response.getOutputStream().close();
			
					// 2011.10.10 보안점검 후속조치 끝
				} finally {
					if (bStream != null) {
						try {
							bStream.close();
						} catch (IOException ignore) {
							//// 사용금지 System.out.println("IGNORE: " + ignore);
							logger.debug("IGNORE: ");
						}
					}
					if (in != null) {
						try {
							in.close();
						} catch (IOException ignore) {
							//// 사용금지 System.out.println("IGNORE: " + ignore);
							logger.debug("IGNORE: ");
						}
					}
					if (bis != null) {
						try {
							bis.close();
						} catch (IOException ignore) {
							//// 사용금지 System.out.println("IGNORE: " + ignore);
							logger.debug("IGNORE: ");
						}
					}
				}
			}
		}
    }

}