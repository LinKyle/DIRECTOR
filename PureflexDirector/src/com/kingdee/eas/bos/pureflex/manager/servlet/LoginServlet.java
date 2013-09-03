package com.kingdee.eas.bos.pureflex.manager.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import com.kingdee.eas.bos.pureflex.manager.exception.LoginErrorException;
import com.kingdee.eas.bos.pureflex.manager.sce.PureflexService;
import com.kingdee.eas.bos.pureflex.manager.sce.PureflexServiceImpl;
import com.kingdee.eas.bos.pureflex.manager.util.IOUtil;
import com.sun.xml.internal.ws.util.StringUtils;

public class LoginServlet extends HttpServlet{
	Logger logger = Logger.getLogger(LoginServlet.class);

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 	response.setContentType("application/json;charset=UTF-8");
		    PrintWriter infoStream = response.getWriter();

		    request.setCharacterEncoding("UTF-8");
		    String password = request.getParameter("password");
		    String user = request.getParameter("user");

		    JSONObject result = new JSONObject();
		    PureflexService secService = new PureflexServiceImpl();
		    boolean success = false;
		    String  tips = "";
		    try{
				success = secService.loginSceServer(user, password);
		    }catch( Exception ex){
		    	tips = ex.getMessage();
		    }
		    if(success){
	          request.getSession().setAttribute("user", "admin");
	        } else {
	          request.getSession().setAttribute("user", "");
	        }
		    try {
		    	result.put("success", success);
		        result.put("tips", tips);
		    } catch (JSONException e) {
		    	this.logger.error("it will not happen", e);
		    }

		    infoStream.println(result.toString());
		    infoStream.flush();
		    infoStream.close();
	}
	
}
