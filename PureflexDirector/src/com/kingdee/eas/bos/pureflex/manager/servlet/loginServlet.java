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

import com.kingdee.eas.bos.pureflex.manager.util.IOUtil;
import com.sun.xml.internal.ws.util.StringUtils;

public class loginServlet extends HttpServlet{
	Logger logger = Logger.getLogger(loginServlet.class);

	protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//设置数据格式
		//设置编码格式
		//获得用户密码，尝试登录。
		 response.setContentType("application/json;charset=UTF-8");
		    PrintWriter infoStream = response.getWriter();

		    request.setCharacterEncoding("utf-8");
		    String jsonString = IOUtil.getRequestParam(request.getInputStream(), "utf-8");
		    this.logger.info("request input :" + jsonString);
		    String password = "";
		    try {
		      JSONObject requestJsonObject = new JSONObject(jsonString);
		      password = requestJsonObject.getString("password");
		    } catch (JSONException e1) {
		      this.logger.error(e1);
		    }

		    JSONObject result = new JSONObject();
		    JSONObject data = new JSONObject();
		    //1.验证用户名密码
		    //2.将验证结果返回到界面
		    //3.组装提示信息。

		    DomainService service = ConsoleService.getInstance().getDomainService();
		    String userName = "admin";
		    String adminPassword = service.getAdminPassword();

		    String tips = "";
		    try {
		      if (!StringUtils.hasText(adminPassword))
		      {
		        tips = "管理控制台未初始化密码.请在管理控制台的工具栏[系统]->[修改密码]修改,初始密码为空.";
		        result.put("success", false);
		        result.put("error", tips);
		        result.put("errorCode", 0);
		        result.put("data", data);
		      }
		      else {
		        String encodeAdminPassword = encodeLogingInfo(userName, adminPassword);
		        if (encodeAdminPassword.equalsIgnoreCase(password))
		        {
		          request.getSession().setAttribute("user", "admin");
		          this.logger.info("setNeedRefreshIndexPage = true");
		          MonitorCacheManager.getInstance().setNeedRefreshIndexPage(true);

		          result.put("success", true);
		          result.put("error", "");
		          result.put("errorCode", 1);
		          String homePage = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/index.html?appClientId=" + appClientId;
		          this.logger.info("homePage:" + homePage);
		          data.put("homePage", homePage);
		          result.put("data", data);
		        } else {
		          tips = "密码错误,请重新登录.如果忘记密码,可以在管理控制台的工具栏[工具]->[控制台参数]重置adminPassword参数。";
		          request.getSession().setAttribute("user", "");
		          result.put("success", false);
		          result.put("error", tips);
		          result.put("errorCode", 1);
		          result.put("data", data);
		        }
		      }
		    } catch (JSONException e) {
		      this.logger.error("it will not happen", e);
		    }

		    infoStream.println(result.toString());
		    infoStream.flush();
		    infoStream.close();
	}
	
}
