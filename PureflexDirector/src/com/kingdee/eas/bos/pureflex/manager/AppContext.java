package com.kingdee.eas.bos.pureflex.manager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.kingdee.eas.bos.pureflex.manager.util.InetUtil;
import com.kingdee.eas.bos.pureflex.manager.util.StringUtil;

public class AppContext {
	private static AppContext context = new AppContext();
	private static Logger logger = Logger.getLogger(AppContext.class);
	private String appHome;
	private String webAppIp;
	private String webAppPort;
	
	public static AppContext getInstance(){
		return context;
	}
	
	private AppContext(){
		//load vm properties
		this.appHome = System.getProperty("APP_HOME");
		String configFilePath = appHome + "/config/appConfig.properties";
		loadVmProperties(configFilePath);
		this.webAppIp = System.getProperty("bindIp", "");
		this.webAppPort = System.getProperty("webAppPort","19691");
	}
	
	private void loadVmProperties(String configFilePath) {
		File vmPropertiesFile = new File(configFilePath)  ;
		try {
			FileInputStream fis = new FileInputStream(vmPropertiesFile);
			Properties p = new Properties();
			p.load(fis);
			fis.close();
			System.getProperties().putAll(p);
		} catch (FileNotFoundException e) {
			logger.error("load vm properties from "+configFilePath+" failed..", e);
		}catch (IOException e) {
			logger.warn("i/o failed.. target " + configFilePath, e);
		}
	}

	public String getHome(){
		return this.appHome;
	}
	
	public String getWebAppIP(){
		if(StringUtil.hasText(this.webAppIp)){
			//do nothing
		}else{
			this.webAppIp = InetUtil.getLocalIP();
		}
		return this.webAppIp;
	}
	
	public String getWebAppPort(){
		return this.webAppPort;
	}
	
	public String getXXX(){
		return null;
	}
}
