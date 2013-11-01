package com.kingdee.eas.bos.pureflex.manager.sce.info;

import com.kingdee.eas.bos.pureflex.manager.AppContext;

public class DeployConfig {
	private String easIp = "";
	private String storeServerIp ="";
	private String storeServerUser ="";
	private String storeServerPassword ="";
	private String concurrentNum ="";
	private String imageType = "";
	private String gateway = "";
			
	
	public String getEasIp() {
		return easIp;
	}
	public void setEasIp(String easIp) {
		this.easIp = easIp;
	}
	public String getStoreServerIp() {
		return storeServerIp;
	}
	public void setStoreServerIp(String storeServerIp) {
		this.storeServerIp = storeServerIp;
	}
	public String getStoreServerUser() {
		return storeServerUser;
	}
	public void setStoreServerUser(String storeServerUser) {
		this.storeServerUser = storeServerUser;
	}
	public String getStoreServerPassword() {
		return storeServerPassword;
	}
	public void setStoreServerPassword(String storeServerPassword) {
		this.storeServerPassword = storeServerPassword;
	}
	public String getConcurrentNum() {
		return concurrentNum;
	}
	public void setConcurrentNum(String concurrentNum) {
		this.concurrentNum = concurrentNum;
	}
	public String getImageId() {
		return AppContext.get(imageType);
	}

	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public String getGateway() {
		return gateway;
	}
	public void setGateway(String gateway) {
		this.gateway = gateway;
	}
	
}
