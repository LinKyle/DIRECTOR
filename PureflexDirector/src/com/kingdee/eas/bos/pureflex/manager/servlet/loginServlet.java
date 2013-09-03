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
		//�������ݸ�ʽ
		//���ñ����ʽ
		//����û����룬���Ե�¼��
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
		    //1.��֤�û�������
		    //2.����֤������ص�����
		    //3.��װ��ʾ��Ϣ��

		    DomainService service = ConsoleService.getInstance().getDomainService();
		    String userName = "admin";
		    String adminPassword = service.getAdminPassword();

		    String tips = "";
		    try {
		      if (!StringUtils.hasText(adminPassword))
		      {
		        tips = "�������̨δ��ʼ������.���ڹ������̨�Ĺ�����[ϵͳ]->[�޸�����]�޸�,��ʼ����Ϊ��.";
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
		          tips = "�������,�����µ�¼.�����������,�����ڹ������̨�Ĺ�����[����]->[����̨����]����adminPassword������";
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
