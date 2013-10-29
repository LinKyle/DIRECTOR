package com.kingdee.eas.bos.pureflex.manager.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import com.kingdee.eas.bos.pureflex.manager.exception.LoginErrorException;
import com.kingdee.eas.bos.pureflex.manager.sce.SCEContentProvider;
import com.kingdee.eas.bos.pureflex.manager.sce.SCEContext;

public class LoginStoreServer {
	private static String serverUrl = "https://192.168.1.228/login";
	private static Logger logger = Logger.getLogger(LoginStoreServer.class);

	
	public static void main(String[] args) {
		LoginStoreServer.login("superuser","passw0rd");
	}

	private static String login(String user, String password) {
		HttpPost request = new HttpPost(serverUrl); //$NON-NLS-1$
        request.addHeader("Content-Type", "application/x-www-form-urlencoded"); //$NON-NLS-1$ //$NON-NLS-2$
        request.setEntity(new EntityTemplate(new SCEContentProvider(MessageFormat.format("login={0}&password={1}", user, password)))); //$NON-NLS-1$
        
        HttpClient client = new DefaultHttpClient();
//        CredentialsProvider credsProvider = new BasicCredentialsProvider();
//        credsProvider.setCredentials(
//                new AuthScope("192.168.1.228", 443),
//                new UsernamePasswordCredentials(user, password));
//        CloseableHttpClient httpclient = HttpClients.custom()
//                .setDefaultCredentialsProvider(credsProvider).build();
        try {
            HttpResponse resp = client.execute(request);
            int rc = resp.getStatusLine().getStatusCode();
            if(rc == 200) {
                try {
                	logger.info("Loging into SCE...");
                	SCEContext.putValue(SCEContext.AUTH_TOKEN, "Basic " + Base64.encodeBase64String((user + ":" + password).getBytes("UTF-8"))); //$NON-NLS-1$ //$NON-NLS-2$)
                	logger.info("Login successfully.");
                	return "";
                } catch (UnsupportedEncodingException e) {
                    logger.error( "",e);
                    return "无法正确设置AUTH_TOKEN";
                }
            } else {
            	logger.error(new LoginErrorException("Error username or password in LoginStep.")); //$NON-NLS-1$
            	return "您输入的用户名或密码不正确，请重新输入。";
            }
        } catch (ClientProtocolException e) {
            logger.error( "",e);
            return "连接SCE服务器协议错误!";
        } catch (IOException e) {
            logger.error( "",e);
            return "连接超时,请确认SCE服务器是否已经启动!";
        } finally {
        	client.getConnectionManager().shutdown();
        }
	}
	
}
