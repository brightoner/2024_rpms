package kr.go.rastech.commons.help.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import kr.go.rastech.base.controller.BaseController;

/**
 * <pre>
 * FileName: HelpController.java
 * Package : kr.go.ncmiklib.commons.help.controller
 *
 * 도우미 Controller.
 *
 * </pre>
 * @author : rastech
 * @date   : 2023. 3. 9.
 */
@Controller("helpController")
public class HelpController extends BaseController {

	
    /**
     *
     * <pre>
     * sql도우미 실행
	 *
	 * </pre>
	 * @author : rastech
     * @date   : 2023. 01. 11.
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    @RequestMapping(value="/help/sqlHelper.do")
    @ResponseBody
	public void sqlHelper()  throws IOException, SQLException , NullPointerException {
    	
		String currDir = this.getClass().getResource("/sqlHelper.exe").getPath(); 

		Runtime runtime = Runtime.getRuntime();

		runtime.exec(currDir);
		
	}
    
    /**
     *
     * <pre>
     * sql도우미 실행
     *
     * </pre>
     * @author : rastech
     * @date   : 2023. 01. 11.
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    @RequestMapping(value="/help/scriptHelper.do")
    public String scriptHelper()  throws IOException, SQLException , NullPointerException {
    	
    	return "temp/cmmn/popup/scriptHelper";
    	
    }
    
    /**
     *
     * <pre>
     * sql도우미 실행
     *
     * </pre>
     * @author : rastech
     * @date   : 2023. 01. 11.
     * @return
     * @throws IOException, SQLException , NullPointerException
     */
    @RequestMapping(value="/help/popup/searchPop.pop")
    public String searchEngine()  throws IOException, SQLException , NullPointerException {
    	
    	return "mng/sys/searchPop";
    	
    }


}
