package com.kingdee.eas.bos.pureflex.manager;

import java.io.IOException;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.WebApplicationContext;
import org.mortbay.util.InetAddrPort;

public class WebServer {
	private static Logger logger = Logger.getLogger(WebServer.class);
	private Server server;
	public static void main(String[] args) {
		logger.info("==============================================");
		logger.info("           EAS DIRECTOR SERVER                ");
		logger.info("==============================================");

		WebServer webserver = new WebServer();
		webserver.init();
		webserver.start();
	}

	private void init() {
		this.server = new Server();
		try {
			this.server.addListener(new InetAddrPort(AppContext.get(AppContext.WEB_APP_PORT,"16961")));
			WebApplicationContext webContext = this.server.addWebApplication("/easDirector", AppContext.getHome()+"/WebContent");
		} catch (UnknownHostException e) {
			logger.error(e);
		} catch (IOException e) {
			logger.error(e);
		}
	}
	
	private void start() {
		try {
			this.server.start();
		} catch (Exception e) {
			logger.error("start web server failed.. " ,e );
		}
		try {
			this.server.join();
		} catch (InterruptedException e) {
			logger.error(e);
		}
	}
}