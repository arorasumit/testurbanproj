package com.ibm.ioes.newdesign.dto;

public class ServiceDetailsConfigAttValuesDTO {
	
	Long id;
	Long serviceProductId;
	String attributeId;
	String attributeValue;
	Integer milestoneStatus;
	String milestoneStatusLog;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getServiceProductId() {
		return serviceProductId;
	}
	public void setServiceProductId(Long serviceProductId) {
		this.serviceProductId = serviceProductId;
	}
	public String getAttributeId() {
		return attributeId;
	}
	public void setAttributeId(String attributeId) {
		this.attributeId = attributeId;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	public Integer getMilestoneStatus() {
		return milestoneStatus;
	}
	public void setMilestoneStatus(Integer milestoneStatus) {
		this.milestoneStatus = milestoneStatus;
	}
	public String getMilestoneLog() {
		return milestoneStatusLog;
	}
	public void setMilestoneLog(String milestoneStatusLog) {
		this.milestoneStatusLog = milestoneStatusLog;
	}
}
