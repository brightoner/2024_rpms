/******************************************************************
 * Copyright RASTECH 2023
 ******************************************************************/
package kr.go.rastech.ptl.mng.code.controller;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.commons.utils.XmlUtil;
import kr.go.rastech.ptl.mng.code.service.MngCodeService;
import kr.go.rastech.ptl.mng.code.vo.MngCodeVo;


/**
 * <pre>
 * FileName: MngCodeController.java
 * Package : kr.go.ncmiklib.ptl.mng.code.controller
 * 
 * mng Code 관리 Controller
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 1. 18.
 */
@Controller
public class MngCodeController extends BaseController {

	@Resource
	private MngCodeService mngCodeService;

	/**
	 * <pre>
	 * 코드관리 페이지
	 * 
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 18.
	 * @param sendCd
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException , SQLException , NullPointerException
	 */
	@RequestMapping("mng/code/listCode.do")
	public String listCode(MngCodeVo mngCodeVo, Model model, HttpServletRequest request,String sel_cd)  throws IOException , SQLException , NullPointerException {

		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		model.addAttribute("sel_cd", sel_cd);
		model.addAttribute("if_yn", param.get("if_yn"));

		return "mng/code/listCode.mngPlatForm"; //rtnUrl("mng/code/listCode");
	}
	
	
	/**
	 * <pre>
	 * 코드관리 - 상위코드저장
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 18.
	 * @param mngCodeVo
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException , SQLException , NullPointerException
	 */
	@RequestMapping("mng/code/insertCdMng.do")
	public String saveCd(MngCodeVo mngCodeVo, Model model, HttpServletRequest request)  throws IOException , SQLException , NullPointerException {
		
//		mngCodeVo.setCreate_id();
		
        if(StringUtils.equals(mngCodeVo.getSave_type(),"I")){
     	   mngCodeService.insertCdMng(mngCodeVo);
        }else if(StringUtils.equals(mngCodeVo.getSave_type(),"U")){
     	   mngCodeService.updateCdMng(mngCodeVo);
        }
		
		return "redirect:/mng/code/listCode.do?sel_cd="+mngCodeVo.getCd();
	}
	
	/**
	 * <pre>
	 * 코드관리 - 코드삭제
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 2. 18.
	 * @param mngCodeVo
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException , SQLException , NullPointerException
	 */
	@RequestMapping("mng/code/delCode.do")
	public String delCode(MngCodeVo mngCodeVo, Model model, HttpServletRequest request,String cd)  throws IOException , SQLException , NullPointerException {
		
		mngCodeVo.setCd(cd);

		mngCodeService.deleteCdMng(mngCodeVo);
		
		return "redirect:/mng/code/listCode.do";
	}
	
	/**
	 * <pre>
	 * 상세코드정보 저장
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 20.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException , SQLException , NullPointerException
	 */
	@RequestMapping("/mng/code/insertCdList.do")
	public String insertCdList(Model model, HttpServletRequest request)  throws IOException , SQLException , NullPointerException {
		
		// 01. Request >> Map 객체로 전환
		Enumeration em = request.getParameterNames();

        String parameterName = (String)em.nextElement();

        JSONParser jsonParser = new JSONParser();
        // JSON데이터를 넣어 JSON Object 로 만들어 준다.
        JSONObject jsonObject = null;
		try {
			jsonObject = (JSONObject) jsonParser.parse(parameterName);
		} catch (ParseException e) {
			logger.debug("ERROR insertCdList ParseException");
		}
        // books의 배열을 추출
        JSONArray cdInfoArray = (JSONArray) jsonObject.get("list");

        MngCodeVo mngCodeVo = null;
          
        String up_cd="";
        
        for (int i = 0; i < cdInfoArray.size(); i++) {

           // 배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
           JSONObject cdObject = (JSONObject) cdInfoArray.get(i);
           // JSON name으로 추출
           mngCodeVo = new MngCodeVo();
           mngCodeVo.setUp_cd(String.valueOf(cdObject.get("up_cd")));
           mngCodeVo.setCd(String.valueOf(cdObject.get("cd")));
           mngCodeVo.setCd_nm(String.valueOf(cdObject.get("cd_nm")));
           mngCodeVo.setCd_desc(String.valueOf(cdObject.get("cd_desc")));
           mngCodeVo.setCd_ord(String.valueOf(cdObject.get("cd_ord")));
           mngCodeVo.setRef_val1(String.valueOf(cdObject.get("ref_val1")));
           mngCodeVo.setRef_val2(String.valueOf(cdObject.get("ref_val2")));
           mngCodeVo.setRef_val3(String.valueOf(cdObject.get("ref_val3")));
           mngCodeVo.setUse_yn(String.valueOf(cdObject.get("use_yn")));
           mngCodeVo.setCreate_id();
           
           if(StringUtils.equals(String.valueOf(cdObject.get("save_type")), "I")){
        	   mngCodeService.insertCdMng(mngCodeVo);
           }else{
        	   mngCodeService.updateCdMng(mngCodeVo);
           }
        }// end for
		
		return "redirect:/mng/code/listCode.do";
	}
	
	/**
	 * <pre>
	 * 코드관리 페이지
	 * 
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 18.
	 * @param sendCd
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException , SQLException , NullPointerException
	 */
	@RequestMapping("mng/code/readCodeList.do")
	public void readCodeList(MngCodeVo mngCodeVo, HttpServletResponse resp, Writer out)  throws IOException , SQLException , NullPointerException {

		String xml="";
		
		List<MngCodeVo> codeList = mngCodeService.selectCdMng(mngCodeVo);


    	if(codeList.size() > 0){
    		xml = XmlUtil.listToXml(codeList);
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
	 * 코드관리 - 코드중복체크
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 3. 25.
	 * @param mngCodeVo
	 * @param resp
	 * @param out
	 * @throws IOException , SQLException , NullPointerException
	 */
	@RequestMapping("/mng/code/codeChk.do")
	@ResponseBody
	public String codeChk(MngCodeVo mngCodeVo, HttpServletResponse resp,String code)  throws IOException , SQLException , NullPointerException {
		
		String result="N";
		
		mngCodeVo.setCd(code);
		
		List<MngCodeVo> codeList = mngCodeService.selectCdMng(mngCodeVo);
		
		if(codeList.size() > 0){
			result="Y";
		}
		
		return result;
	}

}
