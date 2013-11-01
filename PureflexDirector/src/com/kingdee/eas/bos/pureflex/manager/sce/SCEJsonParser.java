package com.kingdee.eas.bos.pureflex.manager.sce;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.kingdee.eas.bos.pureflex.manager.AppContext;
import com.kingdee.eas.bos.pureflex.manager.sce.info.WorkloadInfo;


public final class SCEJsonParser {

    private SCEJsonParser() {
        
    }
    /**
     * @param jsonStr
     * @return appliance collection
     */
//    public static List<Appliance> toAppliances(String jsonStr) {
//        List<Appliance> appliances = new ArrayList<Appliance>();
//        
//        JSONObject jsonObj = JSONObject.fromObject(jsonStr);
//        JSONArray jsonArray = JSONArray.fromObject(jsonObj.get("appliances")); //$NON-NLS-1$
//        @SuppressWarnings("unchecked")
//        Map<String, Object>[] array = (Map<String, Object>[])JSONArray.toArray(jsonArray, Map.class);
//        for(Map<String, Object> map : array) {
//            Appliance appliance = new Appliance();
//            appliance.setId((String) map.get("id")); //$NON-NLS-1$
//            appliance.setName((String) map.get("name")); //$NON-NLS-1$
//            appliance.setUri((String) map.get("uri")); //$NON-NLS-1$
//            appliances.add(appliance);
//        }
//        
//        return appliances;
//    }
    
    public static WorkloadInfo toWorkload(String jsonStr) {
    	WorkloadInfo workload = new WorkloadInfo();
    	JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(jsonStr);
		} catch (JSONException e) {
		}
		workload.setId(jsonObj.optString("id"));
    	try {
			JSONArray propertiesArray = jsonObj.getJSONArray("properties");
			for(int i = 0; i < propertiesArray.length(); i ++){
				JSONObject property = propertiesArray.getJSONObject(i);
				String propertyName = property.getString("name");
				if(propertyName.startsWith(AppContext.get(AppContext.IP_PARAM_PERFIX))){
					workload.setNetInterfaceId(propertyName);
				}
				if(propertyName.startsWith(AppContext.get(AppContext.MASK_PARAM_PERFIX))){
					workload.setNetInterfaceMaskId(propertyName);
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
    	return workload;
    }
    
    public static String getDeploymentState(String jsonString){
    	JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(jsonString);
		} catch (JSONException e) {
		}
    	JSONObject stateObj = jsonObj.optJSONObject("state");
    	if (stateObj != null) {
    		return stateObj.optString("id");
    	}
    	return null;
    }
    
}
