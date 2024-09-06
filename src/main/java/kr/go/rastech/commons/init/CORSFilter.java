package kr.go.rastech.commons.init;



import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class CORSFilter implements Filter  {

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) res;
	

		//response.setHeader("X-Content-Type-Options", "nosniff");

		//response.setHeader("X-XSS-Protection", "1; mode=block");
	

     //   response.setHeader("Access-Control-Allow-Origin","*");
      
       // response.setHeader("Access-Control-Max-Age","1728000");
        chain.doFilter(req, res);

    }
    public void init(FilterConfig filterConfig) {
    }

    public void destroy() {
    }
}