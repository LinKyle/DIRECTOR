package com.kingdee.eas.bos.pureflex.manager.sce;

import org.json.JSONException;
import org.json.JSONObject;

import com.kingdee.eas.bos.pureflex.manager.sce.info.Deployment;


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
    
    public static Deployment toDeployment(String jsonStr) {
    	Deployment deployment = new Deployment();
    	JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(jsonStr);
		} catch (JSONException e) {
		}
    	deployment.setId(jsonObj.optString("id"));
    	deployment.setName(jsonObj.optString("name"));
    	JSONObject stateObj = jsonObj.optJSONObject("state");
    	if (stateObj != null) {
    		deployment.setState(stateObj.optString("id"));
    	}
    	return deployment;
    }
    
}
