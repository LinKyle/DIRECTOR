package com.kingdee.eas.bos.pureflex.manager.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.kingdee.eas.bos.pureflex.manager.util.StringUtil;


public class DirectorLoginFilter implements Filter{

	Logger logger = Logger.getLogger(DirectorLoginFilter.class);
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
	    HttpServletRequest httpRequest = (HttpServletRequest)request;
	    HttpServletResponse httpResponse = (HttpServletResponse)response;
	    String username = (String)httpRequest.getSession().getAttribute("user");
	    this.logger.info("filter , user request URI:" + httpRequest.getRequestURI());
	    if ((!StringUtil.hasText(username)) && (httpRequest.getRequestURI().indexOf("login") < 0)) {
	    	httpResponse.sendRedirect("/easDirector/login.html");
	    	return;
	    }
	    chain.doFilter(request, response);
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}
