package com.kingdee.eas.bos.pureflex.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.Logger;

public class AppContext {
	private static Logger logger = Logger.getLogger(AppContext.class);
	public static final String APP_HOME = "APP_HOME";
	public static final String WEB_APP_IP = "webAppIp";
	public static final String WEB_APP_PORT = "webAppPort";
	public static final String EAS_WORKLOAD_NAME = "easWorkload";
	private static String contextRecordPath;
	private static Properties contents;
	
	static{
		contextRecordPath = System.getProperty(AppContext.APP_HOME) + "/config/appConfig.properties";
		loadContext();
	}
	
	private static void loadContext() {
		File contextRecordFile = new File(contextRecordPath)  ;
		try {
			FileInputStream fis = new FileInputStream(contextRecordFile);
			contents = new Properties();
			contents.load(fis);
			fis.close();
			System.getProperties().putAll(contents);
		} catch (FileNotFoundException e) {
			logger.error("load context properties from "+contextRecordPath+" failed..", e);
			System.exit(-1);
		}catch (IOException e) {
			logger.warn("i/o failed.. target " + contextRecordPath, e);
			System.exit(-1);
		}
	}
	
	
	public static boolean saveContext(){
		File contextRecordFile = new File(contextRecordPath)  ;
		try {
			FileWriter fw = new FileWriter(contextRecordFile, false);
			contents.store(fw, SimpleDateFormat.getInstance().format(new Date()));
			return true;
		} catch (FileNotFoundException e) {
			logger.error("save context properties to "+contextRecordPath+" failed..", e);
			return false;
		}catch (IOException e) {
			logger.warn("i/o failed.. target " + contextRecordPath, e);
			return false;
		}
			
	}
	
	/**
	 * @param key
	 * @param value
	 */
	public static void put(String key ,String value){
		contents.setProperty(key, value);
	}
	
	
	/**
	 * @param key
	 * @return
	 */
	public static String get(String key){
		return contents.getProperty(key);
	}

	/**
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String get(String key,String defaultValue){
		return contents.getProperty(key, defaultValue);
	}
	
	public static String getHome(){
		return System.getProperty(APP_HOME);
	}
}
