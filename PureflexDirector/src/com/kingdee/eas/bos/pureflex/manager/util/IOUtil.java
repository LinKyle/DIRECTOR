package com.kingdee.eas.bos.pureflex.manager.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;

public class IOUtil {
	  static Logger logger = Logger.getLogger(IOUtil.class);
	  public static String getRequestParam(InputStream in, String codeType) { BufferedReader infoReader = null;
	    String paramString = "";
	    try {
	      infoReader = new BufferedReader(new InputStreamReader(in, codeType));
	      paramString = infoReader.readLine();
	    } catch (UnsupportedEncodingException ex) {
	      logger.error(ex);
	    } catch (IOException e) {
	      logger.error(e);
	    }
	    return paramString;
	  }
}
