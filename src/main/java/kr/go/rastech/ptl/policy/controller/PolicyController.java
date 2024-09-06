package kr.go.rastech.ptl.policy.controller;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.go.rastech.base.controller.BaseController;



/**
 * <pre>
 * 
 * 
 * </pre>
 * @FileName : PolicyController.java
 * @package  : kr.go.ncmik.ptl.policy.controller
 * @author   : user
 * @date     : 2018. 7. 4.
 * 
 */
@Controller
public class PolicyController extends BaseController {
	
	
    /**
     * <pre>
     * 저작권 정책 화면으로 이동한다.
     *
     * </pre>
     * @author : user
     * @date   : 2018. 7. 4. 
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws SQLException
     * @throws NullPointerException
     */
     @RequestMapping(value="/policy/copy.do")
	 public String copy(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException , NullPointerException {
		 return "policy/copy.subPlatForm";
	 }
     
     
     /**
     * <pre>
     * 회사소개.
     *
     * </pre>
     * @author : user
     * @date   : 2018. 7. 4. 
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws SQLException
     * @throws NullPointerException
     */
     @RequestMapping(value="/policy/company.do")
	 public String company(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException , NullPointerException {
		 return "policy/company.subPlatForm";
	 }
     
     
    /**
     * <pre>
     * 이용약관
     *
     * </pre>
     * @author : user
     * @date   : 2018. 7. 4. 
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws SQLException
     * @throws NullPointerException
     */
     @RequestMapping(value="/policy/usePolicy.do")
	 public String usePolicy(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException , NullPointerException {
		 return "policy/usePolicy.subPlatForm";
	 }
     
     
    /**
     * <pre>
     * 개인정보처리방침
     *
     * </pre>
     * @author : user
     * @date   : 2018. 7. 4. 
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws SQLException
     * @throws NullPointerException
     */
     @RequestMapping(value="/policy/privacy.do")
	 public String privacy(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException , NullPointerException {
		 return "policy/privacy.subPlatForm";
	 }
    
     
    /**
     * <pre>
     * 청소년 보호정책
     *
     * </pre>
     * @author : user
     * @date   : 2018. 7. 4. 
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws SQLException
     * @throws NullPointerException
     */
    @RequestMapping(value="/policy/youthPolicy.do")
	public String youthPolicy(HttpServletRequest request,HttpServletResponse response) throws IOException, SQLException , NullPointerException {
		 return "policy/youthPolicy.subPlatForm";
	}
    
    
}
