package com.kingdee.eas.bos.pureflex.manager.sce;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.EntityTemplate;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.kingdee.eas.bos.pureflex.manager.AppContext;
import com.kingdee.eas.bos.pureflex.manager.exception.LoginErrorException;
import com.kingdee.eas.bos.pureflex.manager.exception.SCEExecutionException;
import com.kingdee.eas.bos.pureflex.manager.sce.info.DeployConfig;
import com.kingdee.eas.bos.pureflex.manager.sce.info.Deployment;

public class PureflexServiceImpl implements PureflexService{

	Logger logger = Logger.getLogger(PureflexServiceImpl.class);
	public String loginSceServer(String user, String password) {
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
	
	public String calcWorkloadsIP(String type , String easIP){
		String lastIPPart = easIP.split("\\.")[3];
		String preIPPart = easIP.substring(0,easIP.length() - lastIPPart.length());
		int lastPart = Integer.valueOf(lastIPPart);
		if(type.equals(AppContext.EAS_IMAGE)){
			return preIPPart + String.valueOf(lastPart-1);
		}else{
			return preIPPart + String.valueOf(lastPart-2);
		}
	}
	
	public String generateWorkloadName(String imageType,String feature){
		String concurrent = "";
		if(feature.equals("ls300")){
			concurrent = "300";
		}else if( feature.equals("gt300ls600")){
			concurrent =  "600";
		}else {
			concurrent = "800";
		}
		if(imageType.equals(AppContext.EAS_IMAGE)){
			return "EAS" + concurrent;
		}else{
			return "ORA" + concurrent;
		}
	}

	public void createDeployment(DeployConfig deployConfig) throws Exception{
		HttpPost request = new HttpPost(SCEContext.SECURED_API_URL + "/workloads"); //$NON-NLS-1$
		request.addHeader("Authorization", SCEContext.getContent(SCEContext.AUTH_TOKEN)); //$NON-NLS-1$ //$NON-NLS-2$
		request.addHeader("Content-Type", "application/json"); //$NON-NLS-1$ //$NON-NLS-2$
		request.setEntity(new EntityTemplate(new SCEContentProvider("{'appliance':'" +
				deployConfig.getImageId() + "'}"))); //$NON-NLS-1$
		
		HttpClient client = new DefaultHttpClient();
		System.out.println("Creating workload.");
		Deployment deployment = null;
		try {
			HttpResponse resp = client.execute(request);
			int rc = resp.getStatusLine().getStatusCode();
			if(rc == 201) {
				HttpEntity entity = resp.getEntity();
				if (entity != null) {
					String jsonRes = EntityUtils.toString(entity);
					logger.info(jsonRes);
					deployment = SCEJsonParser.toDeployment(jsonRes);
				}
			}
			
			if (deployment == null) {
				throw new SCEExecutionException("The deployment hasn't been created successfully");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new SCEExecutionException("The deployment hasn't been created successfully");
		} catch (IOException e) {
			e.printStackTrace();
			throw new SCEExecutionException("The deployment hasn't been created successfully");
		} finally {
			client.getConnectionManager().shutdown();
		}
		
		System.out.println("Updating workload.");
		client = new DefaultHttpClient();
		HttpPut updateRequest = new HttpPut(SCEContext.SECURED_API_URL + "/workloads/" + deployment.getId());
		updateRequest.addHeader("Authorization", SCEContext.getContent(SCEContext.AUTH_TOKEN)); //$NON-NLS-1$ //$NON-NLS-2$
		updateRequest.addHeader("Content-Type", "application/json"); //$NON-NLS-1$ //$NON-NLS-2$
		try {
			String updateModel =  JsonFileReader.readFile(AppContext.getHome() + "/config/updateDeployment.json");
			String workloadName = this.generateWorkloadName(deployConfig.getImageType(),deployConfig.getConcurrentNum());
			String workloadIp = this.calcWorkloadsIP(deployConfig.getImageType(), deployConfig.getEasIp());
//			String updateDate = updateModel.replace("[WORKLOAD_NAME]", workloadName).replace("[IP]", workloadIp).replace("[GATEWAY]", deployConfig.getGateway()); 
			String updateDate = updateModel.replace("[WORKLOAD_NAME]", workloadName); 
			updateRequest.setEntity(new EntityTemplate(new SCEContentProvider(updateDate)));
		} catch (IOException e) {
			e.printStackTrace();
		} //$NON-NLS-1$
		
		try {
			HttpResponse resp = client.execute(updateRequest);
			int rc = resp.getStatusLine().getStatusCode();
			if(rc != 200) {
				throw new SCEExecutionException("The deployment cannot be updated");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new SCEExecutionException("The deployment hasn't been updated successfully");
		} catch (IOException e) {
			e.printStackTrace();
			throw new SCEExecutionException("The deployment hasn't been updated successfully");
		} finally {
			client.getConnectionManager().shutdown();
		}
		
		System.out.println("Synchronizing workload status.");
		HttpGet getRequest = new HttpGet(SCEContext.SECURED_API_URL + "/workloads/" + deployment.getId()); //$NON-NLS-1$
		getRequest.addHeader("Authorization", SCEContext.getContent(SCEContext.AUTH_TOKEN)); //$NON-NLS-1$ //$NON-NLS-2$
		
        int retryMax = 10;
        for (int i = 0; i < retryMax; i++) {
        	String deploymentState = getDeploymentState(getRequest);
        	if (deploymentState != null && 
        			deploymentState.equalsIgnoreCase(SCEContext.EXECUTING)) {
        		try {
					Thread.sleep(15*1000);
					retryMax++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        	} else {
        		System.out.println("The deployment has been done.");
        		break;
        	}
        }
	}
	
	/**
	 * @param client
	 * @param getRequest
	 * @return
	 * @throws SCEExecutionException 
	 */
	private String getDeploymentState(HttpGet getRequest) throws SCEExecutionException {
		HttpClient client = new DefaultHttpClient();
		try {
			HttpResponse resp = client.execute(getRequest);
			int rc = resp.getStatusLine().getStatusCode();
			
			if(rc == 200) {
				HttpEntity entity = resp.getEntity();
				if (entity != null) {
					String jsonRes = EntityUtils.toString(entity);
					return SCEJsonParser.getDeploymentState(jsonRes);
				}
			} else {
				throw new SCEExecutionException("Get error when getting deployment state");
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			throw new SCEExecutionException("Get error when getting deployment state");
		} catch (IOException e) {
			e.printStackTrace();
			throw new SCEExecutionException("Get error when getting deployment state");
		} finally {
			client.getConnectionManager().shutdown();
		}
		return null;
	}
	

	
	
}
