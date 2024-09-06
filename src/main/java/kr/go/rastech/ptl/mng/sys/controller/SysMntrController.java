/******************************************************************
 * Copyright RASTECH 2016
 ******************************************************************/
package kr.go.rastech.ptl.mng.sys.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.commons.utils.SessionChecker;
import kr.go.rastech.commons.utils.StringUtil;
import kr.go.rastech.ptl.mng.code.service.MngCodeService;
import kr.go.rastech.ptl.mng.code.vo.MngCodeVo;
import kr.go.rastech.ptl.mng.sch.service.SchService;
import kr.go.rastech.ptl.mng.sys.service.SysMntrService;
import kr.go.rastech.ptl.mng.sys.vo.SysMntrVo;



/**
 * <pre>
 * FileName: sysController.java
 * Package : kr.go.ncmiklib.ptl.mng.sys.controller
 * 
 * 시스템관리 컨트롤러
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 3. 22.
 */
@Controller
public class SysMntrController extends BaseController {

	@Resource
	private MngCodeService mngCodeService;
	
	@Resource
	SysMntrService sysMntrService;
	
	/*@Resource
	Scheduler scheduler;*/

    @Resource
    private SessionChecker sessionChecker;
	
	@Resource
	private SchService schService;
    
	/**
	 * <pre>
	 * 시스템관리 - 모니터링 이동
	 * 
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 18.
	 * @param sendCd
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("/mng/sys/sysMntr.do")
	public String sysMntr(SysMntrVo sysMntrVo, Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {

		List<Map<String,Object>> sysMntrList = sysMntrService.selectSysMntrList(sysMntrVo);
		
		List<MngCodeVo> err_list = mngCodeService.getCodeList("SYS_CD");
		
		model.addAttribute("sysMntrList", sysMntrList);
		model.addAttribute("err_list", err_list);
		
		return rtnUrl("mng/sys/sysMntr");
	}
	
	/**
	 * <pre>
	 * 시스템관리 - 모니터링 이동
	 * 
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 18.
	 * @param sendCd
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("/mng/sys/selectMntrErr.do")
	@ResponseBody
	public String selectMntrErr(SysMntrVo sysMntrVo, Model model, HttpServletRequest request,String mntr_seq)  throws IOException, SQLException , NullPointerException {

		sysMntrVo.setMntr_seq(Integer.parseInt(mntr_seq));
		List<Map<String,Object>> sysMntrList = sysMntrService.selectSysMntrList(sysMntrVo);
		
		String result ="";
		if(sysMntrList.size() > 0 ){
			result = StringUtil.nvl(sysMntrList.get(0).get("err_desc"));
		}
		return result;    
	}
	

	/**
	 * <pre>
	 * 모니터링 에러처리 수정
	 *
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 3. 24.
	 * @param sysMntrVo
	 * @param model
	 * @param request
	 * @param mntr_seq
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("/mng/sys/procErr.do")
	public String procErr(SysMntrVo sysMntrVo, Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {

		sysMntrVo.setErr_prc_yn("Y");
		sysMntrVo.setModify_id();
		
		
		sysMntrService.updateSysMntr(sysMntrVo);
		
		return "redirect:/mng/sys/sysMntr.do";
		
	}

   /* *//**
     * <pre>
     * 스켸쥴링 실행
     *
     * </pre>
     * @author : rastech
     * @date   : 2023. 3. 24.
     * @throws IOException, SQLException , NullPointerException
     *//*
    @RequestMapping(value="/mng/sys/procSC.do")
    @ResponseBody
    public void procSC()  throws IOException, SQLException , NullPointerException {
    	//스켸쥴링 실행
    	scheduler.procRssReader();
    }
    */	

	/**
	 * <pre>
	 * 시간정보 저장 
	 *
	 * </pre>
	 * @author : Administrator
	 * @date   : 2023. 6. 7.
	 * @param sysMntrVo
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("/mng/sys/saveSch.do")
	@ResponseBody
	public void saveSch(SysMntrVo sysMntrVo)  throws IOException, SQLException , NullPointerException {
		
		sysMntrService.saveSch(sysMntrVo);
	}
		
	
	/**
	 * <pre>
	 * 시스템관리 - 스켸쥴관리 이동
	 * 
	 * </pre>
	 * @author : rastech
	 * @date   : 2023. 1. 18.
	 * @param sendCd
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("/mng/sys/listSch.do")
	public String listSch(SysMntrVo sysMntrVo, Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {

		List<SysMntrVo> schList = sysMntrService.selectListSch(sysMntrVo);
				
		model.addAttribute("schList", schList);
		
		return "/mng/sys/listSch";
		
	}
	
	@RequestMapping(value="/mng/sys/listAcessUser.do", produces = "application/json; charset=utf8")
	@ResponseBody
	public List<String> listAcessUser(HttpServletRequest request, HttpServletResponse resp)  throws IOException, SQLException , NullPointerException {
		
		List<String> list = sessionChecker.selectloginUsers();
		
		return list;
		
	}
	
	@RequestMapping(value="/mng/sys/selectMsg.do", produces = "application/json; charset=utf8")
	@ResponseBody
	public String selectMsg(HttpServletRequest request, HttpServletResponse resp)  throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		Map<String,String> msg = getUser().getMsgMap();
		
		return msg.get(param.get("user_id").toString());
	}
	
}
