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

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jun Hong Li
 *
 */
public class SCEContext {
	public static final String AUTH_TOKEN = "authToken";
//	public static final String UNSECURED_API_BASE = "http://localhost:8080/unsecured/cloud/api";
//	public static final String SECURED_API_BASE = "http://localhost:8080/cloud/api";
	public static final String IMAGE_001 = "Image 001";
	public static final String IMAGE_ID = "applianceId";
	public static final String EXECUTING = "EXECUTING";
	public static final String SECURED_API_URL ="http://"+System.getProperty("sceServerIp","localhost")+":"+System.getProperty("sceServerPort","8080")+"/cloud/api" ;
	public static final String UNSECURED_API_URL = "http://"+System.getProperty("sceServerIp","localhost")+":"+System.getProperty("sceServerPort","8080")+"/unsecured/cloud/api";
	
	
	private static Map<String, String> contextMap = new HashMap<String, String>();
	public SCEContext() {
	}
	
	public static String getContent(String key) {
		return contextMap.get(key);
	}
	
	public static void putValue(String key, String value) {
		contextMap.put(key, value);
	}
}
