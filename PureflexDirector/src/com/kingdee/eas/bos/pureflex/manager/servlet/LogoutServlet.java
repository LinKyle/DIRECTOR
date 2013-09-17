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
import com.kingdee.eas.bos.pureflex.manager.util.StringUtil;
import com.sun.xml.internal.ws.util.StringUtils;

public class LogoutServlet extends HttpServlet{
	Logger logger = Logger.getLogger(LogoutServlet.class);

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 	response.setContentType("application/json;charset=UTF-8");
		    PrintWriter infoStream = response.getWriter();
		    request.setCharacterEncoding("UTF-8");
		    request.getSession().setAttribute("user", "");
		    JSONObject result = new JSONObject();
		    try {
		    	result.put("success", true);
		    } catch (JSONException e) {
		    	this.logger.error("it will not happen", e);
		    }

		    infoStream.println(result.toString());
		    infoStream.flush();
		    infoStream.close();
	}
}
