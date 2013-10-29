package com.kingdee.eas.bos.pureflex.manager.sce;

import java.util.HashMap;
import java.util.Map;


public class SCEContext {
	public static final String AUTH_TOKEN = "authToken";
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
