package com.ibm.ioes.forms;

public class ServiceSubTypeDto 
{
	private String serviceSubTypeName;
	
	private int serviceSubTypeId;
	
	private int serviceTypeId;

	public int getServiceSubTypeId() {
		return serviceSubTypeId;
	}

	public void setServiceSubTypeId(int serviceSubTypeId) {
		this.serviceSubTypeId = serviceSubTypeId;
	}

	public String getServiceSubTypeName() {
		return serviceSubTypeName;
	}

	public void setServiceSubTypeName(String serviceSubTypeName) {
		this.serviceSubTypeName = serviceSubTypeName;
	}

	public int getServiceTypeId() {
		return serviceTypeId;
	}

	public void setServiceTypeId(int serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
}
