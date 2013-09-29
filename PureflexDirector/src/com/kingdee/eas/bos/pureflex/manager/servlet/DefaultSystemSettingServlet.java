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

import com.kingdee.eas.bos.pureflex.manager.AppContext;
import com.kingdee.eas.bos.pureflex.manager.exception.LoginErrorException;
import com.kingdee.eas.bos.pureflex.manager.sce.PureflexService;
import com.kingdee.eas.bos.pureflex.manager.sce.PureflexServiceImpl;
import com.kingdee.eas.bos.pureflex.manager.util.IOUtil;
import com.kingdee.eas.bos.pureflex.manager.util.StringUtil;
import com.sun.xml.internal.ws.util.StringUtils;

public class DefaultSystemSettingServlet extends HttpServlet{
	Logger logger = Logger.getLogger(DefaultSystemSettingServlet.class);

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 	response.setContentType("application/json;charset=UTF-8");
		    PrintWriter infoStream = response.getWriter();
		    request.setCharacterEncoding("UTF-8");
		    JSONObject result = new JSONObject();
		    try {
		        result.put(AppContext.EAS_VERSION, AppContext.get(AppContext.EAS_VERSION));
		        result.put(AppContext.EAS_HOME, AppContext.get(AppContext.EAS_HOME));
		        result.put(AppContext.APP_SERVER_TYPE, AppContext.get(AppContext.APP_SERVER_TYPE));
		        result.put(AppContext.CLUSTER_HTTP_PORT, AppContext.get(AppContext.CLUSTER_HTTP_PORT));
		        result.put(AppContext.CLUSTER_RPC_PORT, AppContext.get(AppContext.CLUSTER_RPC_PORT));
		        result.put(AppContext.ORACLE_SID, AppContext.get(AppContext.ORACLE_SID));
		        result.put(AppContext.ORACLE_HOME, AppContext.get(AppContext.ORACLE_HOME));
		        result.put(AppContext.ORACLE_PORT, AppContext.get(AppContext.ORACLE_PORT));
		        result.put(AppContext.ORACLE_SYS_PSW, AppContext.get(AppContext.ORACLE_SYS_PSW));
		        result.put(AppContext.CONCURRENT_LS300, AppContext.get(AppContext.CONCURRENT_LS300));
		        result.put(AppContext.CONCURRENT_GT600LS800, AppContext.get(AppContext.CONCURRENT_GT600LS800));
		        result.put(AppContext.CONCURRENT_GT300LS600, AppContext.get(AppContext.CONCURRENT_GT300LS600));
		    } catch (JSONException e) {
		    	this.logger.error("it will not happen", e);
		    }
		    infoStream.println(result.toString());
		    infoStream.flush();
		    infoStream.close();
	}
	
}
