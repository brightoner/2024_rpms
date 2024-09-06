package kr.go.rastech.ptl.mng.sch.controller;

import java.io.IOException;
import java.io.Writer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import kr.go.rastech.ptl.mng.sch.service.SchService;
import kr.go.rastech.ptl.mng.sch.vo.EtlMngVo;

@Controller
public class SchController extends BaseController {

	@Resource
	SchService schService;
	   
   /**
    * <pre>
    *    etl 설정정보 저장
    * </pre>
    * @author :
    * @date   : 2023. . .
    * @param
    * @param
    * @return
 * @throws java.text.ParseException 
    * @throws ParseException 
    * @throws NumberFormatException 
    * */
   @RequestMapping(value="/mng/sch/saveEtlList.do")
   public String saveEtlList(Model model, HttpServletRequest request)  throws IOException , SQLException , NullPointerException, NumberFormatException, java.text.ParseException {
		
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

       EtlMngVo etlMngVo = null;
         
       List<EtlMngVo> list = new ArrayList<EtlMngVo>();
       for (int i = 0; i < cdInfoArray.size(); i++) {

          // 배열 안에 있는것도 JSON형식 이기 때문에 JSON Object 로 추출
          JSONObject cdObject = (JSONObject) cdInfoArray.get(i);
          // JSON name으로 추출
          etlMngVo = new EtlMngVo();
          etlMngVo.setEtlSn(String.valueOf(cdObject.get("etl_sn")));
          etlMngVo.setEtlId(String.valueOf(cdObject.get("etl_id")));
          etlMngVo.setEtlNm(String.valueOf(cdObject.get("etl_nm")));
          etlMngVo.setEtlType(String.valueOf(cdObject.get("etl_type")));
          etlMngVo.setEtlTime(String.valueOf(cdObject.get("etl_time")));
          etlMngVo.setEtlTb(String.valueOf(cdObject.get("etl_tb")));
          etlMngVo.setEtlGbn(String.valueOf(cdObject.get("etl_gbn")));
          etlMngVo.setEtlHh(String.valueOf(cdObject.get("etl_hh")));
          etlMngVo.setEtlSs(String.valueOf(cdObject.get("etl_ss")));
          etlMngVo.setUseYn(String.valueOf(cdObject.get("use_yn")));
          etlMngVo.setSave_type(String.valueOf(cdObject.get("save_type")));
          list.add(etlMngVo);
       }// end for
       
       schService.saveEtlList(list);
       
		return "redirect:/mng/sch/listSch.do";
	}
	

	/**
	 * <pre>
	 * etl 설정정보 조회
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 5. 9.
	 * @param req
	 * @param model
	 * @throws SQLException 
	 * @throws IOException 
	 * @throws NullPointerException 

	 */
	@RequestMapping("/mng/sch/listSch.do")
	public String etlList(HttpServletRequest request, Model model) throws NullPointerException, IOException, SQLException{
		
		model.addAttribute("etl_type", getCodeList("ETL_TYPE"));
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		
		return rtnUrl("mng/sch/listSch");
	}
	
	/**
	 * <pre>
	 * etl 설정정보 조회
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 5. 9.
	 * @param req
	 * @param model
	 * @throws IOException 

	 */
	@RequestMapping("/mng/sch/selectEtlList.do")
	public void selectETLList(HttpServletRequest req, HttpServletResponse resp, Writer out) throws IOException{
		Map<String, Object> params = ReqUtils.getParameterMap(req);
		List<Map<String, Object>> list = schService.selectEtlList(params);
		String xml = "";
    	if(list != null){
    		xml = XmlUtil.listMapToXml(list);
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
	 * etl 설정상세정보 조회
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2017. 5. 9.
	 * @param req
	 * @param model

	 */
	@RequestMapping("/mng/sch/selectEtlDtlList.do")
	public String selectEtlDtlList(HttpServletRequest req, Model model){
		Map<String, Object> params = ReqUtils.getParameterMap(req);
		List<Map<String, Object>> list = schService.selectEtlDtlList(params);
		String xml = XmlUtil.listMapToXml(list);
		return xml;
	}
	
	@RequestMapping(value="/mng/sch/init.do")
	@ResponseBody
	public void init(HttpServletRequest request, HttpServletResponse resp)  throws IOException, SQLException , NullPointerException {
		Map<String, Object> params = ReqUtils.getParameterMap(request);
		List<Map<String, Object>> list = schService.selectEtlList(params);
		try
		{
		for (Map<String, Object> scMap : list) {
    		scMap.put("task", scMap.get("etl_id").toString());
    		scMap.put("proc_type", "a");
    		schService.updNextDt(scMap);	
    	}  
		}catch(SQLException e)
		{
			logger.info("Error: " + "SchedulerPro 오류발생! ");
		}
	}
	
}
