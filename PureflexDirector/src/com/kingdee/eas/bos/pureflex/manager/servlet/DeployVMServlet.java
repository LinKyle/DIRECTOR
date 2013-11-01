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
import com.kingdee.eas.bos.pureflex.manager.sce.PureflexService;
import com.kingdee.eas.bos.pureflex.manager.sce.PureflexServiceImpl;
import com.kingdee.eas.bos.pureflex.manager.sce.info.DeployConfig;
import com.kingdee.eas.bos.pureflex.manager.sce.info.DeployResult;

public class DeployVMServlet extends HttpServlet{
	Logger logger = Logger.getLogger(DeployVMServlet.class);
	private PureflexService pureflexService = new PureflexServiceImpl();

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 	response.setContentType("application/json;charset=UTF-8");
		    PrintWriter infoStream = response.getWriter();

		    request.setCharacterEncoding("UTF-8");
		    String deployType = request.getParameter("deployType");
		    String easIp = request.getParameter("easIp");
		    String gateway = request.getParameter("gateway");
		    String storeServerIp = request.getParameter("storeServerIp");
		    String storeServerUser = request.getParameter("storeServerUser");
		    String storeServerPassword = request.getParameter("storeServerPassword");
		    String concurrentNum = request.getParameter("concurrentNum");
		    DeployConfig deployConfig = new DeployConfig();
		    deployConfig.setConcurrentNum(concurrentNum);
		    deployConfig.setEasIp(easIp);
		    deployConfig.setStoreServerIp(storeServerIp);
		    deployConfig.setStoreServerPassword(storeServerPassword);
		    deployConfig.setStoreServerUser(storeServerUser);
		    deployConfig.setImageType(getImageType(deployType));
		    deployConfig.setGateway(gateway);
			DeployResult deployResult = pureflexService.deployWorkload(deployConfig);
			
		    JSONObject result = new JSONObject();
		    try {
				result.put("isSuccess", deployResult.isSuccess());
			    result.put("tips",deployResult.getTips());
			} catch (JSONException e) {
				//never happen.
			}
		    infoStream.println(result.toString());
		    infoStream.flush();
		    infoStream.close();
	}
	
	private String getImageType(String deployType){
		if(deployType.equalsIgnoreCase("EAS")){
			return AppContext.EAS_IMAGE;
		}else{
			return AppContext.ORA_IMAGE;
		}
	}
	
	public static void main(String[] args) {
		String ip = "192.168.5.23";
		String[] ipSplit = ip.split("\\.");
		String lastIp = ipSplit[3];
		System.out.println(ipSplit.length);
		String preIPPart = ip.substring(0,ip.length() - lastIp.length());
		System.out.println(preIPPart);
	}
	
}
