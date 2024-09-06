/******************************************************************
 * Copyright RASTECH 2015
 ******************************************************************/
package kr.go.rastech.commons.init;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 * FileName: ConfigServlet.java
 * Package : kr.go.ncmik.commons.init
 *
 * 환경설정 서블렛.
 *
 * </pre>
 * @author : rastech
 * @date   : 2015. 3. 9.
 */

public class ConfigServlet extends HttpServlet implements Serializable {
    /** serialVersionUID */
    private static final long serialVersionUID = -1621378941747673748L;

    /**
     *
     * <pre>
     * 환경 설정.
     *
     * </pre>
     *
     * @author : rastech
     * @date : 2015. 3. 9.
     * @param config
     * @throws ServletException
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext sc = config.getServletContext();

        String ctxt = sc.getContextPath();
        sc.setAttribute("ctxt", ctxt);

      
    }
    
    /**
	 * Sets the servlet configuration properties as attributes in the the
	 * request servlets' context. This method includes the appropriate sequence
	 * of calls to obtain data from the info method. 1. call the authentication
	 * service to obtain an authentication token if necessary (see documentation
	 * regarding different authentication types). 2. Call the EDS API CREATE
	 * SESSION method to generate a new session for the call to INFO, 3. Call
	 * the EDS API INFO method, 4. Call the EDS API END SESSION method. This
	 * application assumes that all users will be using the same profile, and
	 * this will be sharing the data returned from the INFO method. If you
	 * application allows the user of more than one profile, then INFO needs to
	 * be stored at the session level.
	 * 
	 * @param request
	 *            context in which to set the application attributes on
	 * @param response
	 * 
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			String a="1"; 
			
			
	}

	/**
	 * Sets the servlet configuration properties as attributes in the the
	 * request servlets' context. This method includes the appropriate sequence
	 * of calls to obtain data from the info method. 1. call the authentication
	 * service to obtain an authentication token if necessary (see documentation
	 * regarding different authentication types). 2. Call the EDS API CREATE
	 * SESSION method to generate a new session for the call to INFO, 3. Call
	 * the EDS API INFO method, 4. Call the EDS API END SESSION method. This
	 * application assumes that all users will be using the same profile, and
	 * this will be sharing the data returned from the INFO method.
	 * 
	 * @param request
	 *            context in which to set the application attributes on
	 * @param response
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
