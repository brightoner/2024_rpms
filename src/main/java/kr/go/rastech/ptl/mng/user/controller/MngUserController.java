package kr.go.rastech.ptl.mng.user.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.security.PrivateKey;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.go.rastech.base.controller.BaseController;
import kr.go.rastech.commons.constants.Constants;
import kr.go.rastech.commons.login.dao.LoginDao;
import kr.go.rastech.commons.login.handler.LoginSuccessHandler;
import kr.go.rastech.commons.login.intercept.DefaultFilterInvocationSecurityMetadataSource;
import kr.go.rastech.commons.login.service.LoginService;
import kr.go.rastech.commons.login.vo.UserVo;
import kr.go.rastech.commons.menu.service.MenuService;
import kr.go.rastech.commons.utils.CryptoUtil;
import kr.go.rastech.commons.utils.ReqUtils;
import kr.go.rastech.commons.utils.StringUtil;
import kr.go.rastech.commons.utils.XmlUtil;
import kr.go.rastech.ptl.member.regi.service.PtlUserRegService;
import kr.go.rastech.ptl.mng.code.vo.MngCodeVo;
import kr.go.rastech.ptl.mng.user.service.MngUserService;
import kr.go.rastech.ptl.mng.user.vo.MngUserAuthVo;
import kr.go.rastech.ptl.mng.user.vo.MngUserClsVo;
import kr.go.rastech.ptl.mng.user.vo.MngUserLogVo;
import kr.go.rastech.ptl.mng.user.vo.PTLLoginVo;


/**
 * <pre>
 * FileName: MngUserController.java
 * Package : kr.go.ncmiklib.ptl.mng.user.controller
 * 관리자 인재DB관리 Controller
 * 
 *
 * </pre>
 * @author : hjysyj
 * @date   : 2023. 4. 15.
 */
@Controller
public class MngUserController extends BaseController {

	@Resource
	private MngUserService mngUserService;
	
	@Resource
	private MenuService menuService;
	
	@Resource
	private PtlUserRegService ptlUserRegService;
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private LoginSuccessHandler ls;
	
	@Resource
    private LoginDao loginDao;
	// 사용자별 권한 정의 객체
    @Autowired
    private DefaultFilterInvocationSecurityMetadataSource databaseSecurityMetadataSource;
	
	private String meshURL;
	private String errorURL;
	private Set<String> reviewerURL;
	private Set<String> indexerURL;
    
	/**
	 * <pre>
	 * 관리자 - 사용자화면
	 *
	 * </pre>
	 * @author : 이세진
	 * @date   : 2023. 2. 20.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("/mng/user/userList.do")
	public String userList(Model model, HttpServletRequest request, PTLLoginVo pTLLoginVo, HttpServletResponse resp)  throws IOException, SQLException , NullPointerException {
		
		
		request.getSession().setAttribute("chg_yn", "Y");
		
		List<MngCodeVo> auth_list = getCodeList("AUTH_GP"); 		// 권한그룹
		
		model.addAttribute("PTLLoginVo", pTLLoginVo);			// 사용자정보
		model.addAttribute("auth_list", auth_list);			// 권한그룹
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		model.addAttribute("if_yn", param.get("if_yn"));

		return "mng/user/userList.mngPlatForm";
		
	}
	
	/**
	 * <pre>
	 * 관리자 -
	 *
	 * </pre>
	 * @author : 이세진
	 * @date   : 2023. 2. 20.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("/mng/user/chgUser.do")
	public String chgUser(Model model, HttpServletRequest request,HttpSession session, PTLLoginVo pTLLoginVo, HttpServletResponse resp)  throws IOException, SQLException , NullPointerException {
		
		String chg_yn = StringUtil.nvl(request.getSession().getAttribute("chg_yn"),"N");
		String loginid = StringUtil.nvl(request.getSession().getAttribute("loginid"));
		
		if(!chg_yn.equals("Y")){
			return "redirect:/index/index.do";
		}
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("page","1");
		
		param.put("length", 20);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 20 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 20 );
		if(!"".equals(loginid)){
			param.put("loginid", loginid);
			request.getSession().setAttribute("chg_yn","N");
		}
		String returnURL =  "/index/proxyIndex.do";
		
		UserVo usrVo = loginService.selectUser(StringUtil.nvl(param.get("loginid")));
		
		SecurityContext context = SecurityContextHolder.getContext(); // 인증 객체를 얻습니다. 
		
		if(usrVo != null){
			
				List<String> authorList = loginDao.selectUserAuthList(loginid);
				if (authorList == null || authorList.isEmpty()) {
					authorList = new ArrayList<String>();
				}
				authorList.add("ROLE_GUEST");
				authorList.add("ROLE_USER");
				usrVo.setAuth(authorList);
				
				/** 로그인 통계 현황 저장 **/
				// 사용자 IP 가져오기
				String ip = request.getHeader("X-Forwarded-For");  
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
					ip = request.getHeader("Proxy-Client-IP");  
				}  
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
					ip = request.getHeader("WL-Proxy-Client-IP");  
				}  
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
					ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
				}  
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
					ip = request.getHeader("HTTP_X_FORWARDED");  
				}  
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
					ip = request.getHeader("HTTP_X_CLUSTER_CLIENT_IP");  
				}  
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
					ip = request.getHeader("HTTP_CLIENT_IP");  
				}  
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
					ip = request.getHeader("HTTP_FORWARDED_FOR");  
				}  
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
					ip = request.getHeader("HTTP_FORWARDED");  
				}  
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
					ip = request.getHeader("HTTP_VIA");  
				}  
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
					ip = request.getHeader("REMOTE_ADDR");  
				}  
				if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
				    ip = request.getRemoteAddr();  
				} 
				usrVo.setUsrIp(ip);
				
			
				request.getSession().setAttribute("userVo", usrVo);
				
				if (     ls.checkIPMatching("152.99.73.*", ip)     // 질병관리본부 대역
			       	 || ls.checkIPMatching("210.183.128.80", ip) || ls.checkIPMatching("210.183.128.81", ip) // 도서관 대역(2017.08.02. 수정)

					//	|| checkIPMatching("211.199.211.238", ip) || checkIPMatching("211.199.211.226", ip) // 도서관 대역
		      		//|| checkIPMatching("192.168.*.*",ip)      || checkIPMatching("127.0.0.1", ip)     // 개발 테스트
		      		//checkIPMatching("172.17.*.*", ip)      || 
		      	)
				{ // IP 대역이면
					usrVo.setInternalIp("Y");
				}
				//사용자 접속통계
				try {
					loginService.insertStatLogin(usrVo.getEmplyrkey());
				} catch (SQLException e) {			
					Logger.getLogger(LoginSuccessHandler.class).debug("IGNORED: onAuthenticationSuccess");
				}
				
				request.getSession().setAttribute("NCMIK_ELIB_LOGIN", "LOGIN");

				
				
				//로그인 했을 시  기타기관 및 전자자원승인여부 확인  ************** END ***************
				
				
				String rtnUrl = request.getSession().getAttribute("returnURL") == null?"" :request.getSession().getAttribute("returnURL").toString() ;
				
				
				if(StringUtils.isEmpty(rtnUrl)){
					request.getSession().setAttribute("PROXY_URL", "/index/index.do");			
				}else{
					request.getSession().setAttribute("PROXY_URL", rtnUrl);
				}
				
				
			  
				
				UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(usrVo.getLoginid(), usrVo.getPassword(), usrVo.getAuthorities());
				context.setAuthentication(result);
				
				request.getSession().setAttribute("userVo", usrVo);
				  
				// 사용자별 권한 정의 갱신(초기화)
				databaseSecurityMetadataSource.init();
				//상단메뉴와 왼쪽메뉴를 재구성
				menuService.reload();
		}
		return "redirect:/"+returnURL;
	}
	
	
	/**
	 * <pre>
	 * 관리자 - 인재DB관리(목록)
	 *
	 * </pre>
	 * @author : 이세진
	 * @date   : 2023. 2. 20.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping(value="/mng/user/selectUserList.do")
	public void selectUserList(HttpServletRequest request, PTLLoginVo pTLLoginVo, HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException {
		
		Map<String, Object> param = ReqUtils.getParameterMap(request);
		param.put("length", 20);
		param.put("start", (Integer.parseInt(param.get("page").toString())- 1) * 20 );
		param.put("rownum",(Integer.parseInt(param.get("page").toString())- 1) * 20 );
		
		String searchStatus = request.getParameter("searchStatus");
		String searchText = request.getParameter("searchText");
		String usrAuth = request.getParameter("usrAuth");
		
		pTLLoginVo.setSearchStatus(searchStatus);
		pTLLoginVo.setSearchText(searchText);
		pTLLoginVo.setUsrAuth(usrAuth);
		
		List<PTLLoginVo> list = mngUserService.selectUserList(param);
		String xml="";

    	int totalCnt = mngUserService.selectUserCnt(param);
    	
    	if(list != null){
    		if(list.size() > 0){
    			
    			xml = XmlUtil.listToXml(list);   
    			/*
    			 * 총카운터 세팅
    			 */
    	    	StringBuffer sb = new StringBuffer();
    	    	String[] str = xml.split("<items>");
    	    	
    	    	sb.append(str[0]);
    	    	sb.append("<items>");
    	    	sb.append("<totalCnt>"+Math.ceil((double)totalCnt/(double)20)+"</totalCnt>\n");
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
	 * 관리자 - 인재DB관리(상세)
	 * 
	 * </pre>
	 * @author : 이세진
	 * @date   : 2023. 2. 20.
	 * @param 
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping(value="/mng/user/userView.do")
	public String userView(Model model, HttpServletRequest request, PTLLoginVo pTLLoginVo)  throws IOException, SQLException , NullPointerException {

	
		return "mng/user/userView";
	}
	


	

	
	/**
	 * <pre>
	 * 회원관리 - 사용자권한 조회
	 *
	 * </pre>
	 * @author : 이세진
	 * @date   : 2023. 2. 20.
	 * @param loginid
	 * @param resp
	 * @param out
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("mng/user/selectUserAuth.do")
	public void selectUserAuth(String loginid, HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException {
		
		String xml="";

		List<MngUserAuthVo> auth_list = mngUserService.selectUserAuth(loginid);
		
		if(auth_list !=null){
			if(auth_list.size() > 0){
				
				xml = XmlUtil.listToXml(auth_list);
				
				StringBuffer sb = new StringBuffer();
		    	String[] str = xml.split("<items>");
		    	
		    	sb.append(str[0]);
		    	sb.append("<items>");
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
	 * 1. 개요 : 
	 * 2. 처리내용 : 
	 * </pre>
	 * @Method Name : insertAuth
	 * @author : 이세진
	 * @date   : 2023. 2. 20.
	 * @history : 
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */ 	
	@RequestMapping("mng/user/insertAuth.do")
	@ResponseBody
	public void insertAuth(Model model, HttpServletRequest request)  throws IOException, SQLException , NullPointerException {
		
        String loginid = request.getParameter("user_id");
        System.out.println("******** emplyrkey : " + request.getParameter("emplyrkey"));
        
        mngUserService.deleteUserAuth(loginid);
        
        String[] auth_list = request.getParameterValues("auth");
      
        MngUserAuthVo mngUserAuthVo;
        
        if(auth_list != null){
        	
	        for (int j = 0; j < auth_list.length; j++) {
	        	mngUserAuthVo = new MngUserAuthVo();
				mngUserAuthVo.setloginid(loginid);
				mngUserAuthVo.setUser_auth_cd(auth_list[j]);
				mngUserAuthVo.setCreate_id();
				mngUserAuthVo.setModify_id();
				
				mngUserService.insertUserAuth(mngUserAuthVo);
			}
	        
        }
        
	}
	
	
	
	/**
	 * <pre>
	 * 사용자 로그 관리 화면
	 *
	 * </pre>
	 * @author : 이세진
	 * @date   : 2023. 2. 20.
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping("/mng/user/userLog.do")
	public String userLog(Model model, HttpServletRequest request, PTLLoginVo pTLLoginVo, HttpServletResponse resp)  throws IOException, SQLException , NullPointerException {
		
		
		return "mng/user/userLog";
	}
	
	

	/**
	 * <pre>
	 * 사용자 로그 조회
	 *
	 * </pre>
	 * @author : 이세진
	 * @date   : 2023. 2. 20.
	 * @param request
	 * @param PTLLoginVo
	 * @param resp
	 * @param out
	 * @throws IOException, SQLException , NullPointerException
	 */
	@RequestMapping(value="/mng/user/selectUserLogList.do")
	public void selectUserLogList(HttpServletRequest request, MngUserLogVo mngUserLogVo, HttpServletResponse resp, Writer out)  throws IOException, SQLException , NullPointerException {

		String xml = "";

    	List<MngUserLogVo> list = mngUserService.selectUserLogList(mngUserLogVo);	// 사용자 - 인재DB등록관리 List
    	
    	if(list != null){
    		if(list.size() > 0){
    			xml = XmlUtil.listToXml(list);
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
	 * @param mngUserLogVo
	 * @param os
	 * @param response
	 * @throws IOException
	 * @throws SQLException
	 * @throws NullPointerException
	 */
	@RequestMapping(value="/mng/user/excelDown.do", method=RequestMethod.POST)
	public void excelDown(MngUserLogVo mngUserLogVo, OutputStream os, HttpServletResponse response) throws IOException, SQLException , NullPointerException {
		
		Date date = new Date();
	      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
	      String rDate = dateFormat.format(date);
	      
        String excel_name = mngUserLogVo.getSt_dt().replaceAll("-", ".")+"-"+ mngUserLogVo.getEd_dt().replaceAll("-", ".") +" 사용자로그.xls";
        String sheetl_name = "사용자로그 ( " + rDate + " )";
        
        excel_name = java.net.URLEncoder.encode(excel_name,"UTF-8");
        excel_name = excel_name.replaceAll("\\+", "%20");
        response.setHeader("Content-Disposition", "attachment; filename="+excel_name);
        
        //Excel Write
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        HSSFSheet sheet = workbook.createSheet(sheetl_name);
        
        // 색상 지정(색상 변경)
        HSSFPalette palette = workbook.getCustomPalette();
        palette.setColorAtIndex(HSSFColor.BLUE.index, (byte) 74, (byte) 125, (byte) 204);       // 헤더 글자색
        palette.setColorAtIndex(HSSFColor.SKY_BLUE.index, (byte) 211, (byte) 228, (byte) 246);  // 헤더 백그라운드색
        palette.setColorAtIndex(HSSFColor.PALE_BLUE.index, (byte) 242, (byte) 245, (byte) 249); // 내용2 백그라운드색
        palette.setColorAtIndex(HSSFColor.AQUA.index, (byte) 175, (byte) 202, (byte) 230);      // 보더색
        
        // 제목폰트 설정.
        HSSFFont titleFont = workbook.createFont();
        titleFont.setFontName("Tahoma");
        titleFont.setFontHeightInPoints((short) 12);
        titleFont.setBoldweight((short) HSSFFont.BOLDWEIGHT_BOLD);
        
        //검색조건 폰트
	       HSSFFont searchFont = workbook.createFont();
	       searchFont.setFontName("Tahoma");
	       searchFont.setFontHeightInPoints((short) 9);
	       searchFont.setBoldweight((short) HSSFFont.BOLDWEIGHT_BOLD);
        
        // 헤더
        HSSFFont headerFont = workbook.createFont();
        headerFont.setFontName("Tahoma");
        headerFont.setFontHeightInPoints((short) 9);
        headerFont.setBoldweight((short) HSSFFont.BOLDWEIGHT_BOLD);
        headerFont.setColor(HSSFColor.BLUE.index);
        
        HSSFFont contentFont = workbook.createFont();
        contentFont.setFontName("Tahoma");
        contentFont.setFontHeightInPoints((short) 9);
        
        // 제목의 스타일 지정
        HSSFCellStyle titleStyle = workbook.createCellStyle();
        titleStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        titleStyle.setFont(titleFont);
        
        // 헤더의 스타일 지정
        HSSFCellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
        headerStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        headerStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        headerStyle.setFont(headerFont);
        headerStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        headerStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        headerStyle.setLeftBorderColor(HSSFColor.AQUA.index);
        headerStyle.setRightBorderColor(HSSFColor.AQUA.index);
        headerStyle.setBottomBorderColor(HSSFColor.AQUA.index);
        headerStyle.setTopBorderColor(HSSFColor.AQUA.index);
        
        // 정상 스타일
        HSSFCellStyle nomalStyle = workbook.createCellStyle();
        nomalStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        nomalStyle.setFont(contentFont);
        nomalStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        nomalStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        nomalStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        nomalStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        nomalStyle.setLeftBorderColor(HSSFColor.AQUA.index);
        nomalStyle.setRightBorderColor(HSSFColor.AQUA.index);
        nomalStyle.setBottomBorderColor(HSSFColor.AQUA.index);
        nomalStyle.setTopBorderColor(HSSFColor.AQUA.index);
        
        //에러 스타일
        HSSFCellStyle errStyle = workbook.createCellStyle();
        errStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        errStyle.setFont(contentFont);
        errStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        errStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
        errStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        errStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
        errStyle.setLeftBorderColor(HSSFColor.AQUA.index);
        errStyle.setRightBorderColor(HSSFColor.AQUA.index);
        errStyle.setBottomBorderColor(HSSFColor.AQUA.index);
        errStyle.setTopBorderColor(HSSFColor.AQUA.index);
        
      //검색조건 스타일 지정
	       HSSFCellStyle searchStyle = workbook.createCellStyle();
	       searchStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
	       searchStyle.setFont(searchFont);
        
        int rowNum = 0;
        int cellNum = 0;
        
        //Row 생성
        HSSFRow row = sheet.createRow(rowNum++);
        
        // start header
        HSSFCell cell = row.createCell(cellNum);
        // cell 병합 ( row시작위치, cell 시작 위치, row 끝 위치, cell 끝 위치)
     	cell.setCellValue("사용자로그");
        
        
        cell.setCellStyle(titleStyle);
        
        rowNum = 1;
        
        row = sheet.createRow(rowNum++);
        cellNum = 0;
        String[] headNm =null;
        String[] mapNm = null;
        int[] headWidth =  null;
        
        //헤더 세팅
        headNm = new String[]{"순번","접속ID","접속일시","접속IP","열람대상ID","열람일시","내용"};
        //맵 이름 세팅
        mapNm = new String[] {"SEQ","loginid","LOG_DTTM","LOG_IP","VIEW_loginid","MDFY_DTTM","USER_LOG_CONT"};
        
        //채용정보 조회
        List<Map<String,String>> logList = mngUserService.selectUserLogMap(mngUserLogVo);

        //엑셀 컬럼 넓이 세팅
        headWidth= new int[] {3000, 5000, 5000,5000,5000,5000,7000};
        
        
        rowNum=2;
        row = sheet.createRow(rowNum++);
	       cellNum = 0;
	       cell = row.createCell(cellNum++);
	       cell.setCellValue("검색조건");
	       cell.setCellStyle(searchStyle);
	       
        if(mngUserLogVo.getSt_dt().length() >0 && mngUserLogVo.getEd_dt().length() > 0){
 	       cell = row.createCell(cellNum++);
 	       cell.setCellValue("등록일  :  "+mngUserLogVo.getSt_dt()+" ~ "+mngUserLogVo.getEd_dt());
 	       cell.setCellStyle(errStyle); 	       
 	       }
        
        if(mngUserLogVo.getUser_nm().length() > 0){  
		       cell = row.createCell(cellNum++);
		       cell.setCellValue("접속 ID : " + mngUserLogVo.getUser_nm());
		       cell.setCellStyle(errStyle);
	       }
        
        row = sheet.createRow(rowNum++);
        
        cellNum=0;
        //헤더영역
        for (String head : headNm) {
             cell = row.createCell(cellNum++);
             cell.setCellValue(head);
             cell.setCellStyle(headerStyle);
        }
        
        Map<String, String> vo = null;
        HSSFCellStyle contentStyle = null;
        
     // 데이터영역
        for (int i = 0; i < logList.size(); ++i) {
            row = sheet.createRow(rowNum++);
            cellNum = 0;
            vo = logList.get(i);
        
            	contentStyle = nomalStyle;
            
            for (int j = 0; j < mapNm.length; j++) {
                cell = row.createCell(cellNum++);
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                cell.setCellValue(String.valueOf(vo.get(mapNm[j])));
                cell.setCellStyle(contentStyle);
                
            }
        }
        
        // 셀넓이 조정
        for (int i = 0; i < cellNum; ++i) {
            sheet.setColumnWidth(i, headWidth[i]);
        }
        
        // 출력
        workbook.write(os);
        os.flush();
       
   }
}
