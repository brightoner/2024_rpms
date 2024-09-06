package kr.go.rastech.ptl.content.banner.controller;



import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import egovframework.rte.fdl.cmmn.exception.FdlException;
import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.imgFile.service.ImgFileService;
import kr.go.rastech.commons.imgFile.vo.ImgFileVo;
import kr.go.rastech.commons.utils.ImgFileUtil;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.commons.utils.StringUtil;
import kr.go.rastech.commons.utils.XmlUtil;
import kr.go.rastech.ptl.content.banner.service.BannerMngService;
import kr.go.rastech.ptl.content.banner.vo.BannerMngVo;






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
public class BannerMngController extends BaseController {

	@Resource
	private BannerMngService bannerMngService;
	
    /** ImgFileUtil */
    @Resource
    private ImgFileUtil imgFileUtil;

	
    /** ImgFileService */
    @Resource
    private ImgFileService imgFileService;


	/**
	 * <pre>
	 * 배너 페이지 이동
	 * 
	 * </pre>
	 * @author : lwk
	 * @date   : 2019. 12. 11.
	 * @param sendCd
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("content/banner/bannerList.do")
	public String bannerList(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		model.addAttribute("banner", param);
		
		return "content/banner/bannerList.mngPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 배너관리 list
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2019. 12. 11.
	 * @param request
	 * @param bannerMngVo
	 * @param resp
	 * @param out
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping(value="content/banner/readBannerList.do")  
	public void readbannerList(HttpServletRequest request,  HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException  {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 10);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 10 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 10 );
		
		
		String xml = "";
    	
    	List<Map<String,Object>> list = bannerMngService.selectBannerList(param);
    
    	int totalCnt = bannerMngService.selectBannerTotalCount(param);
    	
    	if(list != null){
    		if(list.size() > 0){
    			
    			xml = XmlUtil.listMapToXml(list);
    			/*
    			 * 총카운터 세팅
    			 */
    	    	StringBuffer sb = new StringBuffer();
    	    	String[] str = xml.split("<items>");
    	    	
    	    	sb.append(str[0]);
    	    	sb.append("<items>");
				sb.append("<totalCnt>"+Math.ceil((double)totalCnt/(double)10)+"</totalCnt>\n");
				sb.append("<totalDataCnt>"+totalCnt+"</totalDataCnt>\n");
    	    	sb.append(str[1]);
    	    	
    	    	xml = sb.toString();
    		}
    	}	

    	
    	resp.setContentType("text/xml");
    	resp.setCharacterEncoding("UTF-8");
    	resp.setHeader("Cache-Control", "no-cache");
    	resp.setHeader("Pragma", "no-cache");
    	resp.setDateHeader("Expires", -1);
    	out.write(xml);
   	
    	out.flush();
	}

	/**
	 * <pre>
	 * 배너관리 등록 url이동
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2019. 12. 11.
	 * @param model
	 * @param bannerMngVo
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("content/banner/bannerWrite.do")
	public String bannerWrite(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);
	
		model.addAttribute("banner", param);
		
		return "content/banner/bannerWrite.mngPlatForm";
			
	}
	
	/**
	 * <pre>
	 * 배너관리 등록 url이동
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2019. 12. 11.
	 * @param model
	 * @param bannerMngVo
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("content/banner/bannerDetail.do")
	public String bannerDetail(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		Map<String,Object> dtl = bannerMngService.selectBannerDtl(param);
		
		if(!"".equals(param.get("banner_seq"))){
			String contents = String.valueOf(dtl.get("banner_contents"));
			contents = contents.replaceAll("&lt;", "<");
			contents = contents.replaceAll("&gt;", ">");
			dtl.put("banner_contents", contents);
		}
		model.addAttribute("data", dtl);
		
		model.addAttribute("banner", param);
		
		return "content/banner/bannerDetail.mngPlatForm";
			
	}

	
	@RequestMapping("content/banner/saveBanner.do")
	public String saveOrg(Model model, HttpServletRequest request, final MultipartHttpServletRequest multiRequest) throws IOException, SQLException , NullPointerException,FdlException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		int seq = 0;
		
		String atchImgId = "";
		List<ImgFileVo> result = null;

		final Map<String, MultipartFile> files = multiRequest.getFileMap();

		if(!files.isEmpty()){
			
			result = imgFileUtil.parseBLOBFileInf(files, "BANNER_", 0, "", "");
		 
			if(result != null){
				if(result.size() > 0){
					ImgFileVo vo = result.get(0);
			
					atchImgId = imgFileService.insertFileInfs(vo);  //파일이 생성되고나면 생성된 첨부파일 ID를 리턴한다.
					
					param.put("atch_img_id", atchImgId);
				}			
			}
		}

	
		if(StringUtils.equals(String.valueOf(param.get("save_type")) , "I" )){
			seq = bannerMngService.insertBannerMng(param);
		}else{
			bannerMngService.updateBannerMng(param);
			seq = Integer.parseInt(param.get("banner_seq").toString());
		}
	
		return "redirect:bannerDetail.do?banner_seq="+seq;
	}
	
	
	/**
	 * <pre>
	 * 메인용 배너 조회
	 *
	 * </pre>
	 * @author : lwk  
	 * @date   : 2019. 12. 11.
	 * @param bannerMngVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	// 메인화면에서 배너을 호출한다.
	// 현재 url은 관리자 페이지의 배너관리의  배너 미리보기에서도  사용된다. 변경시 참고 ! !  주의 !
	@RequestMapping(value="/content/banner/readMainBannerList.do")   
	public void readMainBannerList(HttpServletRequest request,  HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException  {
		
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		
		if(!StringUtil.nvl(getUserId()).equals("")){
			param.put("banner_target", "L");
			param.put("banner_type", "P");			
		}else{
			param.put("banner_target", "N");
			param.put("banner_type", "P");
		}
		String xml = "";
    	
    	List<Map<String,Object>> list = bannerMngService.selectMainBannerList(param);
        	
    	
    	if(list != null){
    		if(list.size() > 0){
    			
    			xml = XmlUtil.listMapToXml(list);
    			/*
    			 * 총카운터 세팅
    			 */
    	    	StringBuffer sb = new StringBuffer();
    	    	String[] str = xml.split("<items>");
    	    	
    	    	sb.append(str[0]);    
    	     	sb.append("<items>");			
    	    	sb.append(str[1]);
    	    	xml = sb.toString();
    		}
    	}	
    	System.out.println(xml);
    	
    	resp.setContentType("text/xml");
    	resp.setCharacterEncoding("UTF-8");
    	resp.setHeader("Cache-Control", "no-cache");
    	resp.setHeader("Pragma", "no-cache");
    	resp.setDateHeader("Expires", -1);
    	out.write(xml);
   	
    	out.flush();
	}
	
	/**
     * 이미지 파일을 삭제처리한다.
     * @param imgFileVo
     * @param bannerMngVo
     * @return	
     * @throws SQLException , IOException, NullPointerException
     */
    @RequestMapping(value="/content/banner/deleteFileInf.do")
    @ResponseBody
    public String deleteFileInf(ImgFileVo imgFileVo, Model model ,HttpServletRequest request)   throws SQLException , IOException, NullPointerException {
    	
    	Map<String, Object> param = ReqUtils.getParameterMap(request);
        	

    	bannerMngService.updateBannerImgFile(param , imgFileVo);
    	// 첨부파일 삭제 End.............

    	return "ok";
    }


	/**
     * 이미지 파일을 삭제처리한다.
     * @param imgFileVo
     * @param bannerMngVo
     * @return	
     * @throws SQLException , IOException, NullPointerException
     */
    @RequestMapping(value="/content/banner/deleteBanner.do")
 
    public String deleteBanner(ImgFileVo imgFileVo, Model model ,HttpServletRequest request)   throws SQLException , IOException, NullPointerException {
    	
    	Map<String, Object> param = ReqUtils.getParameterMap(request);
    
    

    	bannerMngService.deleteBanner(param,imgFileVo);
    	// 첨부파일 삭제 End.............

    	return "redirect:bannerList.do";
    }
    
    

	/**
	 * <pre>
	 * 배너관리 등록 url이동
	 *
	 * </pre>
	 * @author : lwk
	 * @date   : 2019. 12. 11.
	 * @param model
	 * @param bannerMngVo
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException 
	 */
	@RequestMapping("content/banner/mainBannerDetail.do")
	public String mainBannerDetail(Model model,  HttpServletRequest request ) throws IOException, SQLException , NullPointerException {
	
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		Map<String,Object> dtl = bannerMngService.selectBannerDtl(param);
		
		if(!"".equals(param.get("banner_seq"))){
			String contents = String.valueOf(dtl.get("banner_contents"));
			contents = contents.replaceAll("&lt;", "<");
			contents = contents.replaceAll("&gt;", ">");
			dtl.put("banner_contents", contents);
		}
		model.addAttribute("data", dtl);
		
		model.addAttribute("banner", param);
		
		return "content/banner/mainBannerDetail.subPlatForm";
			
	}

}