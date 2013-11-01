package com.kingdee.eas.bos.pureflex.manager.sce.info;

public class DeployResult {
	private boolean isSuccess;
	private String tips;
	public DeployResult() {
	}
	
	
	public DeployResult(boolean isSuccess, String tips) {
		this.isSuccess = isSuccess;
		this.tips = tips;
	}
	public boolean isSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getTips() {
		return tips;
	}
	public void setTips(String tips) {
		this.tips = tips;
	}

}
