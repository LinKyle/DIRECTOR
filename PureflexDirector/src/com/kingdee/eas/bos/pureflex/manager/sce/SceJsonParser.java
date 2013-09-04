/* 
 * Licensed Materials - Property of IBM 
 * 
 * OCO Source Materials 
 * 
 * (C) Copyright IBM Corp. 2013 All Rights Reserved 
 * 
 * The source code for this program is not published or other- 
 * wise divested of its trade secrets, irrespective of what has 
 * been deposited with the U.S. Copyright Office. 
 */ 
package com.kingdee.eas.bos.pureflex.manager.sce;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jun Hong Li
 *
 */
public final class SceJsonParser {

    private SceJsonParser() {
        
    }
//    /**
//     * @param jsonStr
//     * @return appliance collection
//     */
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
//    
//    public static Deployment toDeployment(String jsonStr) {
//    	Deployment deployment = new Deployment();
//    	JSONObject jsonObj = JSONObject.fromObject(jsonStr);
//    	deployment.setId(jsonObj.optString("id"));
//    	deployment.setName(jsonObj.optString("name"));
//    	JSONObject stateObj = jsonObj.optJSONObject("state");
//    	if (stateObj != null) {
//    		deployment.setState(stateObj.optString("id"));
//    	}
//    	return deployment;
//    }
    
}
