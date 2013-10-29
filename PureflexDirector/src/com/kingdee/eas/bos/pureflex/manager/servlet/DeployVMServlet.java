package com.kingdee.eas.bos.pureflex.manager.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.kingdee.eas.bos.pureflex.manager.AppContext;
import com.kingdee.eas.bos.pureflex.manager.sce.PureflexService;
import com.kingdee.eas.bos.pureflex.manager.sce.PureflexServiceImpl;
import com.kingdee.eas.bos.pureflex.manager.sce.info.DeployConfig;

public class DeployVMServlet extends HttpServlet{
	Logger logger = Logger.getLogger(DeployVMServlet.class);
	private PureflexService pureflexService = new PureflexServiceImpl();

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 	response.setContentType("application/json;charset=UTF-8");
		    PrintWriter infoStream = response.getWriter();

		    request.setCharacterEncoding("UTF-8");
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
		    deployConfig.setImageName(AppContext.get(AppContext.IMAGE_A));
		    deployConfig.setImageType(AppContext.IMAGE_A);
		    deployConfig.setGateway(gateway);
		    
		    try {
				pureflexService.createDeployment(deployConfig);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    JSONObject result = new JSONObject();
		    //如果没有登录信息返回，则说明登录成功。

		    infoStream.println(result.toString());
		    infoStream.flush();
		    infoStream.close();
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
