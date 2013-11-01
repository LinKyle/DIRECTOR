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
import com.kingdee.eas.bos.pureflex.manager.sce.info.DeployResult;
import com.kingdee.eas.bos.pureflex.manager.sce.info.WorkloadInfo;

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

	public DeployResult deployWorkload(DeployConfig deployConfig){
		WorkloadInfo workload = createWorkload(deployConfig);
		if(workload == null){
			boolean isCreateSuccess = false;
			String tips = "创建虚拟机失败,请联系金蝶技术支持人员分析";
			return new DeployResult(isCreateSuccess,tips);
		}
		logger.info("Updating workload.");
		boolean isUpdateSuccess = false;
		String tips = "部署虚拟机失败,请联系金蝶技术支持人员分析";
		HttpClient client = new DefaultHttpClient();
		HttpPut updateRequest = new HttpPut(SCEContext.SECURED_API_URL + "/workloads/" + workload.getId());
		updateRequest.addHeader("Authorization", SCEContext.getContent(SCEContext.AUTH_TOKEN)); 
		updateRequest.addHeader("Content-Type", "application/json"); 
		String updateData = generateUpdateData(workload,deployConfig);
		updateRequest.setEntity(new EntityTemplate(new SCEContentProvider(updateData)));
		try {
			HttpResponse resp = client.execute(updateRequest);
			int rc = resp.getStatusLine().getStatusCode();
			if(rc != 200) {
				logger.error("The deployment cannot be updated , statusCode "+rc);
		        return new DeployResult(isUpdateSuccess,tips);
			}
		} catch (ClientProtocolException e) {
			logger.error("",e);
		} catch (IOException e) {
			logger.error("",e);
		} finally {
			client.getConnectionManager().shutdown();
		}
		logger.info("Synchronizing workload status.");
		HttpGet getRequest = new HttpGet(SCEContext.SECURED_API_URL + "/workloads/" + workload.getId()); //$NON-NLS-1$
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
        		logger.info("The deployment has been done.");
        		isUpdateSuccess = true;
        		tips = "";
        		if(deployConfig.getImageType().equals(AppContext.ORA_IMAGE)){
        			AppContext.put(AppContext.ORA_WORKLOAD_ID, workload.getId());
        			AppContext.put(AppContext.ALREADY_INSTALLED,"true");
        			AppContext.saveContext2File();
        		}else{
        			//先创建eas虚拟机，完成后等待创建ora虚拟机，先不保存上下文
        			AppContext.put(AppContext.EAS_WORKLOAD_ID, workload.getId());
        		}
        		break;
        	}
        }
        return new DeployResult(isUpdateSuccess,tips);
	}
	
	private String generateUpdateData(WorkloadInfo workload,DeployConfig config) {
		//参数准备
		String workloadName = this.generateWorkloadName(config.getImageType(),config.getConcurrentNum());
		String gateway = config.getGateway();
		String hostName = workloadName;
		String workloadIp = this.calcWorkloadsIP(config.getImageType(), config.getEasIp());
		try {
			String updateModel =  JsonFileReader.readFile(AppContext.getHome() + "/config/updateDeployment.json");
			String updateData = updateModel.replace("[WORKLOAD_NAME]", workloadName);
			updateData = updateData.replace("[INTERFACE_PARAM]", workload.getNetInterfaceId());
			updateData = updateData.replace("[INTERFACE_SETTING]", workloadIp);
			updateData = updateData.replace("[HOST_NAME]", hostName);
			updateData = updateData.replace("[MASK_PARAM]", workload.getNetInterfaceMaskId());
			updateData = updateData.replace("[GATEWAY_SETTING]", gateway);
			logger.info("updateDate:");
			logger.info(updateData);
			return updateData;
		} catch (IOException e) {
			logger.error("",e);
		} 		
		return null;
	}

	private WorkloadInfo createWorkload(DeployConfig config){
		HttpPost request = new HttpPost(SCEContext.SECURED_API_URL + "/workloads");
		request.addHeader("Authorization", SCEContext.getContent(SCEContext.AUTH_TOKEN));
		request.addHeader("Content-Type", "application/json"); 
		request.setEntity(new EntityTemplate(new SCEContentProvider("{'appliance':'" +
				config.getImageId() + "'}")));
		HttpClient client = new DefaultHttpClient();
		logger.info("Creating workload.");
		WorkloadInfo workload = null;
		try {
			HttpResponse resp = client.execute(request);
			if(resp.getStatusLine().getStatusCode() == 201) {
				HttpEntity entity = resp.getEntity();
				if (entity != null) {
					String jsonRes = EntityUtils.toString(entity);
					workload = SCEJsonParser.toWorkload(jsonRes);
				}
			}
		} catch (ClientProtocolException e) {
			logger.error("", e);
		} catch (IOException e) {
			logger.error("", e);
		} finally {
			client.getConnectionManager().shutdown();
		}
		return workload;
	}
	
	/**
	 * @param client
	 * @param getRequest
	 * @return
	 * @throws SCEExecutionException 
	 */
	private String getDeploymentState(HttpGet getRequest) {
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
				logger.warn("Get error when getting deployment state");
			}
		} catch (ClientProtocolException e) {
			logger.warn("Get error when getting deployment state");
		} catch (IOException e) {
			logger.warn("Get error when getting deployment state");
		} finally {
			client.getConnectionManager().shutdown();
		}
		return null;
	}
	
	public DeployResult updateWorkload(DeployConfig deployConfig){
			WorkloadInfo workload = new WorkloadInfo();
			logger.info("Updating workload.");
			boolean isUpdateSuccess = false;
			String tips = "部署虚拟机失败,请联系金蝶技术支持人员分析";
			HttpClient client = new DefaultHttpClient();
			HttpPut updateRequest = new HttpPut(SCEContext.SECURED_API_URL + "/workloads/" + workload.getId());
			updateRequest.addHeader("Authorization", SCEContext.getContent(SCEContext.AUTH_TOKEN)); 
			updateRequest.addHeader("Content-Type", "application/json"); 
			String updateData = generateUpdateData(workload,deployConfig);
			updateRequest.setEntity(new EntityTemplate(new SCEContentProvider(updateData)));
			try {
				HttpResponse resp = client.execute(updateRequest);
				int rc = resp.getStatusLine().getStatusCode();
				if(rc != 200) {
					logger.error("The deployment cannot be updated , statusCode "+rc);
			        return new DeployResult(isUpdateSuccess,tips);
				}
			} catch (ClientProtocolException e) {
				logger.error("",e);
			} catch (IOException e) {
				logger.error("",e);
			} finally {
				client.getConnectionManager().shutdown();
			}
			logger.info("Synchronizing workload status.");
			HttpGet getRequest = new HttpGet(SCEContext.SECURED_API_URL + "/workloads/" + workload.getId()); //$NON-NLS-1$
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
	        		logger.info("The deployment has been done.");
	        		isUpdateSuccess = true;
	        		tips = "";
	        		if(deployConfig.getImageType().equals(AppContext.ORA_IMAGE)){
	        			AppContext.put(AppContext.ALREADY_INSTALLED,"true");
	        			AppContext.saveContext2File();
	        		}
	        		break;
	        	}
	        }
	        return new DeployResult(isUpdateSuccess,tips);
	}
	
	
}
