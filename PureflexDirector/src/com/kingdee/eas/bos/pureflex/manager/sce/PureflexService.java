package com.kingdee.eas.bos.pureflex.manager.sce;

import com.kingdee.eas.bos.pureflex.manager.sce.info.DeployConfig;

public interface PureflexService {
	public String loginSceServer(String user,String password) ;
//	public List<WorkLoadInfo> getWorkLoadList();
	public String calcWorkloadsIP(String type , String easIP);
	public String generateWorkloadName(String imageType,String feature);
	public void createDeployment(DeployConfig deployConfig)throws Exception;
}
