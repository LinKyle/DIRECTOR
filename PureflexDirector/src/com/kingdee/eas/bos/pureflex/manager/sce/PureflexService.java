package com.kingdee.eas.bos.pureflex.manager.sce;

import java.util.List;

import com.kingdee.eas.bos.pureflex.manager.sce.info.WorkLoadInfo;

public interface PureflexService {
	public boolean loginSceServer(String user,String password) throws Exception;
//	public List<WorkLoadInfo> getWorkLoadList();
}
