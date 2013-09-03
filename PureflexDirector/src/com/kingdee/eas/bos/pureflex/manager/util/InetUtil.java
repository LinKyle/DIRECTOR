package com.kingdee.eas.bos.pureflex.manager.util;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Enumeration;
import org.apache.log4j.Logger;

/**
 * telnet���ߺi
 */
public final class InetUtil {
    private static Logger    logger        = Logger.getLogger(InetUtil.class);
    private static int       socketTimeout = Integer.parseInt(System.getProperty("SocketTimeOut", "3000"));
    
    private static String    localIP;

    private static ArrayList localIPList   = new ArrayList();

    
    static {
        localIP = System.getProperty("localIP");
        if (localIP != null && localIP.length()>0) {
            logger.info("Set localIP[" + localIP + "]!");
        }
    }

    public static boolean hasMoreThanOneNetworkInterface() {
        try {
            Enumeration enu = NetworkInterface.getNetworkInterfaces();
            if(enu.hasMoreElements()) {
                enu.nextElement();
                if(enu.hasMoreElements()) {
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error("Get localhost networkinterface exception!", e);
        }
        return false;
    }

    
    public static String getLocalIP() {
    	if (localIP == null || localIP.length()<=0) {
    		getLocalIPList(true);
    		if (localIP == null || localIP.length()<=0)
    			localIP = "127.0.0.1";

    	}
        return localIP;
    }

    
    public static String getLocalPrivateIP() {
        return "127.0.0.1";
    }
    
    public static boolean isMultiNetworkInterface() {
    	ArrayList list = new ArrayList();
    	try {
    		Enumeration enu = NetworkInterface.getNetworkInterfaces();
    		while (enu.hasMoreElements()) {
    			NetworkInterface ni = (NetworkInterface) (enu.nextElement());
    			if(!"lo".equalsIgnoreCase(ni.getDisplayName())) {
    				list.add(ni);
    			}
    		}
    	}catch (Exception e) {
    		logger.error("Check isMultiNetworkInterface exception!", e);
    		return true;
    	}
    	return list.size()>1;
    }
    
    public static ArrayList getLocalIPList() {
    	if(localIPList.size()==0) {
    		getLocalIPList(false);
    	}
    	return localIPList;
    }

    
    private static ArrayList getLocalIPList(boolean initLocalIP) {
    	if(localIPList.size()==0) {
    		try {
    			Enumeration enu = NetworkInterface.getNetworkInterfaces();
    			while (enu.hasMoreElements()) {
    				NetworkInterface ni = (NetworkInterface) (enu.nextElement());
    				Enumeration ias = ni.getInetAddresses();
    				while (ias.hasMoreElements()) {
    					InetAddress localAddress = (InetAddress) (ias.nextElement());
    					String hostAddress = localAddress.getHostAddress();
    					if (hostAddress != null) {
    						String[] ips = hostAddress.split("\\.");
    						if (ips.length == 4 && !"127.0.0.1".equals(hostAddress)) {
    							if(initLocalIP) {
    								localIP = hostAddress;
    							}
    							localIPList.add(hostAddress);
    						}
    					}
    				}
    			}
    		} catch (Exception e) {
    			logger.error("Get localhost ip exception!", e);
    			if(initLocalIP) {
    				localIP = "127.0.0.1";
    			}
    		}
    	}
    	return localIPList;
    	// ArrayList list = new ArrayList();
    	// list.add("192.168.16.1");
    	// list.add("192.168.18.8");
    	// list.add("127.0.0.1");
    	// return list;
    }

    
    public static void setLocalIP(String ip) {
        localIP = ip;
    }

    public static boolean isLocalComputer(String ipORname) {
        try {
            InetAddress varAddress = InetAddress.getByName(ipORname.trim());
            Enumeration enu = NetworkInterface.getNetworkInterfaces();

            while (enu.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) (enu.nextElement());
                Enumeration ias = ni.getInetAddresses();
                while (ias.hasMoreElements()) {
                    InetAddress localAddress = (InetAddress) (ias.nextElement());
                    
                    String hostName = localAddress.getHostName();
                    
                    String hostAddress = localAddress.getHostAddress();
                    if (varAddress.getHostName().equalsIgnoreCase(hostName)
                            || varAddress.getHostAddress().equalsIgnoreCase(hostAddress)) {
                        return true;
                    }
                }
            }

            
            if ("127.0.0.1".equalsIgnoreCase(ipORname)) {
                return true;
            }
            return false;
        } catch (Exception err) {
            logger.error("",err);
            return false;
        }
    }

    public static boolean testConnection(String ip, int port) {
        Socket socket = new Socket();
        try {
            SocketAddress addr = new InetSocketAddress(ip, port);
            socket.connect(addr, socketTimeout);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            try {
                socket.close();
            } catch (Exception e) {
            }
        }
    }
}
