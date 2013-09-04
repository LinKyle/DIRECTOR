package com.kingdee.eas.bos.pureflex.manager.sce;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.kingdee.eas.bos.pureflex.manager.exception.LoginErrorException;
import com.kingdee.eas.bos.pureflex.manager.sce.info.WorkLoadInfo;

public class PureflexServiceImpl implements PureflexService{

	Logger logger = Logger.getLogger(PureflexServiceImpl.class);
	public boolean loginSceServer(String user, String password)throws Exception {
		HttpPost request = new HttpPost(SCEContext.UNSECURED_API_URL + "/auth"); //$NON-NLS-1$
        request.addHeader("Content-Type", "application/x-www-form-urlencoded"); //$NON-NLS-1$ //$NON-NLS-2$
        request.setEntity(new EntityTemplate(new SCEContentProvider(MessageFormat.format("username={0}&password={1}", user, password)))); //$NON-NLS-1$
        HttpClient client = new DefaultHttpClient();
        try {
            HttpResponse resp = client.execute(request);
            int rc = resp.getStatusLine().getStatusCode();
            if(rc == 200) {
                try {
                	logger.info("Loging into SCE...");
                	SCEContext.putValue(SCEContext.AUTH_TOKEN, "Basic " + Base64.encodeBase64String((user + ":" + password).getBytes("UTF-8"))); //$NON-NLS-1$ //$NON-NLS-2$)
                	logger.info("Login successfully.");
                	return true;
                } catch (UnsupportedEncodingException e) {
                    logger.error( "",e);
                }
            } else {
            	throw new LoginErrorException("Error username or password in LoginStep."); //$NON-NLS-1$
            }
        } catch (ClientProtocolException e) {
            logger.error( "",e);
        } catch (IOException e) {
            logger.error( "",e);
        } finally {
        	client.getConnectionManager().shutdown();
        }
		return false;
	}
	
//	public List<WorkLoadInfo> getWorkLoadList() {
//		 HttpGet request = new HttpGet(SCEContext.SECURED_API_URL + "/workloads"); //$NON-NLS-1$
//	     request.addHeader("Authorization", SCEContext.getContent(SCEContext.AUTH_TOKEN)); //$NON-NLS-1$ //$NON-NLS-2$
//	       
//	     HttpClient client = new DefaultHttpClient();
//	     HttpResponse resp;
//		try {
//			resp = client.execute(request);
//		} catch (ClientProtocolException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	     int respCode = resp.getStatusLine().getStatusCode();
//	     if(respCode == 200){
//	    	 HttpEntity entity = resp.getEntity();
//			 String jsonRes = EntityUtils.toString(entity);
//			 JSONObject workloadsJson = new JSONObject(jsonRes);
//			 
//	     }else{
//	    	 
//	     }
//		return null;
//	}
//	
	
		
}
