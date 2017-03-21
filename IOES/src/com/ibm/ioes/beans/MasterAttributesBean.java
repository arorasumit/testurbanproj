package com.ibm.ioes.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

public class MasterAttributesBean extends ActionForm {
	
	private static final long serialVersionUID = 5218185723412531138L;
	private String attDescription;
	private String attDataType;
	private String attAliasName;
	private String attExpectedValue;
	private String attMaxLenghth;
	private String attIsmandatory;
	private String attID;
	private String serviceType;
	private String serviceTypeId;
	private String serviceList;
	
	ArrayList services = null;
	ArrayList products = null;
	private String productList;
	private String hiddenServiceDetailId;
	private String hiddenServiceTypeId;
	private String hiddenAttributeId;
	private String hiddenFlag;
	private ArrayList attributeList;
	private String[] dataTypesList =  new String[20];
	private String[] expectedValuesList  =  new String[20];
	

	
	//***********************update fields**********
	private String attIdList;
	private String attDescList;
	private String attDataTypeList;
	private String attExpValList;
	private String attMandatoryList;
	
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
	
	private String attOEParentIdList;
	private String attM6ParentIdList;
	private String attM6ChildIdList;
	private String attSend2FXList;
	private String attSend2M6List;
	private String attBillInfoList;
	private String attServiceSummaryList;
	private String attChargeInfoList;
	private String attHarwareInfoList;
	private String attServiceLocationList;
	
	

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
	public ArrayList getAttributeList() {
		return attributeList;
	}
	public void setAttributeList(ArrayList attributeList) {
		this.attributeList = attributeList;
	}
	public String getHiddenFlag() {
		return hiddenFlag;
	}
	public void setHiddenFlag(String hiddenFlag) {
		this.hiddenFlag = hiddenFlag;
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
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getHiddenServiceTypeId() {
		return hiddenServiceTypeId;
	}
	public void setHiddenServiceTypeId(String hiddenServiceTypeId) {
		this.hiddenServiceTypeId = hiddenServiceTypeId;
	}
	public String getServiceTypeId() {
		return serviceTypeId;
	}
	public void setServiceTypeId(String serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	public String getServiceList() {
		return serviceList;
	}
	public void setServiceList(String serviceList) {
		this.serviceList = serviceList;
	}
	public ArrayList getServices() {
		return services;
	}
	public void setServices(ArrayList services) {
		this.services = services;
	}
	
	public String getHiddenAttributeId() {
		return hiddenAttributeId;
	}
	public void setHiddenAttributeId(String hiddenAttributeId) {
		this.hiddenAttributeId = hiddenAttributeId;
	}
	public String[] getDataTypesList() {
		return dataTypesList;
	}
	public void setDataTypesList(String[] dataTypesList) {
		this.dataTypesList = dataTypesList;
	}
	public String[] getExpectedValuesList() {
		return expectedValuesList;
	}
	public void setExpectedValuesList(String[] expectedValuesList) {
		this.expectedValuesList = expectedValuesList;
	}
	public String getAttDataTypeList() {
		return attDataTypeList;
	}
	public void setAttDataTypeList(String attDataTypeList) {
		this.attDataTypeList = attDataTypeList;
	}
	public String getAttDescList() {
		return attDescList;
	}
	public void setAttDescList(String attDescList) {
		this.attDescList = attDescList;
	}
	public String getAttExpValList() {
		return attExpValList;
	}
	public void setAttExpValList(String attExpValList) {
		this.attExpValList = attExpValList;
	}
	public String getAttIdList() {
		return attIdList;
	}
	public void setAttIdList(String attIdList) {
		this.attIdList = attIdList;
	}
	public String getAttMandatoryList() {
		return attMandatoryList;
	}
	public void setAttMandatoryList(String attMandatoryList) {
		this.attMandatoryList = attMandatoryList;
	}
	public ArrayList getProducts() {
		return products;
	}
	public void setProducts(ArrayList products) {
		this.products = products;
	}
	public String getProductList() {
		return productList;
	}
	public void setProductList(String productList) {
		this.productList = productList;
	}
	public String getHiddenServiceDetailId() {
		return hiddenServiceDetailId;
	}
	public void setHiddenServiceDetailId(String hiddenServiceDetailId) {
		this.hiddenServiceDetailId = hiddenServiceDetailId;
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
	public String getTypeOfService() {
		return typeOfService;
	}
	public void setTypeOfService(String typeOfService) {
		this.typeOfService = typeOfService;
	}
	public String getAttBillInfoList() {
		return attBillInfoList;
	}
	public void setAttBillInfoList(String attBillInfoList) {
		this.attBillInfoList = attBillInfoList;
	}
	public String getAttChargeInfoList() {
		return attChargeInfoList;
	}
	public void setAttChargeInfoList(String attChargeInfoList) {
		this.attChargeInfoList = attChargeInfoList;
	}
	public String getAttHarwareInfoList() {
		return attHarwareInfoList;
	}
	public void setAttHarwareInfoList(String attHarwareInfoList) {
		this.attHarwareInfoList = attHarwareInfoList;
	}
	public String getAttM6ChildIdList() {
		return attM6ChildIdList;
	}
	public void setAttM6ChildIdList(String attM6ChildIdList) {
		this.attM6ChildIdList = attM6ChildIdList;
	}
	public String getAttM6ParentIdList() {
		return attM6ParentIdList;
	}
	public void setAttM6ParentIdList(String attM6ParentIdList) {
		this.attM6ParentIdList = attM6ParentIdList;
	}
	public String getAttOEParentIdList() {
		return attOEParentIdList;
	}
	public void setAttOEParentIdList(String attOEParentIdList) {
		this.attOEParentIdList = attOEParentIdList;
	}
	public String getAttSend2FXList() {
		return attSend2FXList;
	}
	public void setAttSend2FXList(String attSend2FXList) {
		this.attSend2FXList = attSend2FXList;
	}
	public String getAttSend2M6List() {
		return attSend2M6List;
	}
	public void setAttSend2M6List(String attSend2M6List) {
		this.attSend2M6List = attSend2M6List;
	}
	public String getAttServiceLocationList() {
		return attServiceLocationList;
	}
	public void setAttServiceLocationList(String attServiceLocationList) {
		this.attServiceLocationList = attServiceLocationList;
	}
	public String getAttServiceSummaryList() {
		return attServiceSummaryList;
	}
	public void setAttServiceSummaryList(String attServiceSummaryList) {
		this.attServiceSummaryList = attServiceSummaryList;
	}
	
	
}
