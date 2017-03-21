package com.ibm.ioes.forms;

import java.util.Map;

public class MastersAttributesDto {
	private String attID;
	private String attDescription;
	private String attDataType;
	private String attAliasName;
	private String attExpectedValue;
	private String attMaxLenghth;
	private String attIsmandatory;

	private String serviceType;
	private String serviceTypeId;
	
	
	//************Product List**********
	private String serviceDetailId;
	private String serviceDetailDescription;
	private String serviceDetailType;
	private Map<String, String> applicatlionPropertiesMap =  null;
	
	private String oeParentID;
	private String m6ParentID;
	private String m6ChildID;
	private String sendToFX;
	private String sendToM6;
	private String typeOfService;
	private String serviceSummary;
	private String billingInfo;
	private String chargeInfo;
	private String hardwareInfo;
	private String serviceLocation;
	private String masterValue;
	
	
	public String getMasterValue() {
		return masterValue;
	}
	public void setMasterValue(String masterValue) {
		this.masterValue = masterValue;
	}
	public String getBillingInfo() {
		return billingInfo;
	}
	public void setBillingInfo(String billingInfo) {
		this.billingInfo = billingInfo;
	}
	public String getChargeInfo() {
		return chargeInfo;
	}
	public void setChargeInfo(String chargeInfo) {
		this.chargeInfo = chargeInfo;
	}
	public String getHardwareInfo() {
		return hardwareInfo;
	}
	public void setHardwareInfo(String hardwareInfo) {
		this.hardwareInfo = hardwareInfo;
	}
	public String getM6ChildID() {
		return m6ChildID;
	}
	public void setM6ChildID(String childID) {
		m6ChildID = childID;
	}
	public String getM6ParentID() {
		return m6ParentID;
	}
	public void setM6ParentID(String parentID) {
		m6ParentID = parentID;
	}
	public String getOeParentID() {
		return oeParentID;
	}
	public void setOeParentID(String oeParentID) {
		this.oeParentID = oeParentID;
	}
	public String getSendToFX() {
		return sendToFX;
	}
	public void setSendToFX(String sendToFX) {
		this.sendToFX = sendToFX;
	}
	public String getSendToM6() {
		return sendToM6;
	}
	public void setSendToM6(String sendToM6) {
		this.sendToM6 = sendToM6;
	}
	public String getServiceLocation() {
		return serviceLocation;
	}
	public void setServiceLocation(String serviceLocation) {
		this.serviceLocation = serviceLocation;
	}
	public String getServiceSummary() {
		return serviceSummary;
	}
	public void setServiceSummary(String serviceSummary) {
		this.serviceSummary = serviceSummary;
	}
	public String getTypeOfService() {
		return typeOfService;
	}
	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}
	public Map<String, String> getApplicatlionPropertiesMap() {
		return applicatlionPropertiesMap;
	}
	public void setApplicatlionPropertiesMap(
			Map<String, String> applicatlionPropertiesMap) {
		this.applicatlionPropertiesMap = applicatlionPropertiesMap;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceTypeId() {
		return serviceTypeId;
	}
	public void setServiceTypeId(String serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	public String getAttID() {
		return attID;
	}
	public void setAttID(String attID) {
		this.attID = attID;
	}
	public String getAttAliasName() {
		return attAliasName;
	}
	public void setAttAliasName(String attAliasName) {
		this.attAliasName = attAliasName;
	}
	public String getAttDataType() {
		return attDataType;
	}
	public void setAttDataType(String attDataType) {
		this.attDataType = attDataType;
	}
	public String getAttDescription() {
		return attDescription;
	}
	public void setAttDescription(String attDescription) {
		this.attDescription = attDescription;
	}
	public String getAttExpectedValue() {
		return attExpectedValue;
	}
	public void setAttExpectedValue(String attExpectedValue) {
		this.attExpectedValue = attExpectedValue;
	}
	public String getAttIsmandatory() {
		return attIsmandatory;
	}
	public void setAttIsmandatory(String attIsmandatory) {
		this.attIsmandatory = attIsmandatory;
	}
	public String getAttMaxLenghth() {
		return attMaxLenghth;
	}
	public void setAttMaxLenghth(String attMaxLenghth) {
		this.attMaxLenghth = attMaxLenghth;
	}
	public String getServiceDetailDescription() {
		return serviceDetailDescription;
	}
	public void setServiceDetailDescription(String serviceDetailDescription) {
		this.serviceDetailDescription = serviceDetailDescription;
	}
	public String getServiceDetailId() {
		return serviceDetailId;
	}
	public void setServiceDetailId(String serviceDetailId) {
		this.serviceDetailId = serviceDetailId;
	}
	public String getServiceDetailType() {
		return serviceDetailType;
	}
	public void setServiceDetailType(String serviceDetailType) {
		this.serviceDetailType = serviceDetailType;
	}

}
