package com.kingdee.eas.bos.pureflex.manager.sce.info;


public class WorkloadInfo {
	private String id;
	private String name;
	private String hostNameId;
	private String netInterfaceId;
	private String netInterfaceMaskId;
	private String state;
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	public String getHostNameId() {
		return hostNameId;
	}
	public void setHostNameId(String hostNameId) {
		this.hostNameId = hostNameId;
	}
	public String getNetInterfaceId() {
		return netInterfaceId;
	}
	public void setNetInterfaceId(String netInterfaceId) {
		this.netInterfaceId = netInterfaceId;
	}
	public String getNetInterfaceMaskId() {
		return netInterfaceMaskId;
	}
	public void setNetInterfaceMaskId(String netInterfaceMaskId) {
		this.netInterfaceMaskId = netInterfaceMaskId;
	}
	
	
}
