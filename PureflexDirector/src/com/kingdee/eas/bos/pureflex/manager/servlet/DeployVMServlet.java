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

public class DeployVMServlet extends HttpServlet{
	Logger logger = Logger.getLogger(DeployVMServlet.class);

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 	response.setContentType("application/json;charset=UTF-8");
		    PrintWriter infoStream = response.getWriter();

		    request.setCharacterEncoding("UTF-8");
		    String easIp = request.getParameter("easIp");
		    String storeServerIp = request.getParameter("storeServerIp");
		    String storeServerUser = request.getParameter("storeServerUser");
		    String storeServerPassword = request.getParameter("storeServerPassword");
		    String concurrentNum = request.getParameter("concurrentNum");

		    System.out.println(easIp);
		    System.out.println(storeServerIp);
		    System.out.println(storeServerUser);
		    System.out.println(storeServerPassword);
		    System.out.println(concurrentNum);
		    
		    JSONObject result = new JSONObject();
		    //如果没有登录信息返回，则说明登录成功。

		    infoStream.println(result.toString());
		    infoStream.flush();
		    infoStream.close();
	}
	
}
