package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.forms.ComponentsDto;
import com.ibm.ioes.utilities.PagingSorting;

public class LogicalSIDataReportDTO {
	
	PagingSorting pagingSorting = new PagingSorting();
	private int orderNumber;
	private String recordStatus;
	private int logicalSINumber;
	private ComponentsDto componentDto;
	private int isUsage;
	private String fx_external_acc_id;
	private String disconnection_remarks;
	private String neworder_remarks;
	private int m6OrderNo;
	private int fxInternalId;
	private String child_account_creation_status;
	private int packageID;
	private String packageName;
	private int componentID;
	private int componentInfoID;
	private String componentName;
	private String serviceName;
	private String parent_name;
	private String serviceDetDescription;
	private String chargeTypeName;
	private String chargeName;
	private String charge_status;
	private int contractPeriod;
	private String totalPoAmt;
	private String frequencyName;
	private String frequencyAmt;
	private String startDateLogic;
	private int startDateMonth;
	private int startDateDays;
	private String billingTriggerDate;
	private String endDateLogic;
	private int endDateMonth;
	private int endDateDays;
	private String chargeEndDate;
	private double annual_rate;

	private String dnd_Dispatch_But_Not_Delivered;
	private String dnd_Dispatch_And_Delivered;
	private String dnd_Disp_Del_Not_Installed;
	private String dnd_Disp_Delivered_Installed;
	private String poExclude;
	private String custPoDetailNo;
	private String poAmount;
	private String entity;
	private String custPoDate;
	//private String contractPeriod;
	private String contractStartDate;
	private String contractEndDate;
	private String poRecieveDate;
	private String chargeinfoID;
	//private String orderLineNumber;
	private int serviceId;

	private int billingInfoID;

	private String chargeAnnotation;

	private String creditPeriodName;
	private String billingTypeName;
	private String billingFormatName;
	private String taxation;
	private String lcompanyname;
	private String countyName;
	private String address1;
	private String address2;
	private String address3;
	private String address4;
	private String cityName;
	private String postalCode;
	private String stateName;

	private String contactName;
	private String designation;
	private String telePhoneNo;
	private String emailId;
	private String fax;
	private String lst_No;
	private String lstDate;

	private String attributeLabel;
	private String attributeValue;
	private String storeName;
	private String hardwaretypeName;
	private String saleNature;
	private String formAvailable;
	private String saleType;

	private String warrantyStartDateLogic;
	private String warrantyPeriodMonths;
	private String warrantyPeriodDays;
	private String warrantyStartDate;
	private String warrantyEndDateLogic;
	private String warrantyEndPeriodMonths;
	private String warrantyEndPeriodDays;
	private String warrantyEndDate;
	private String extndSupportPeriodMonths;
	private String extndSupportPeriodDays;
	private String extSuportEndDate;
	private String dispatchAddress1;
	private String dispatchAddress2;
	private String dispatchAddress3;
	private String dispatchCityName;
	private String dispatchPostalCode;
	private String dispatchStateName;
	private String dispatchPersonName;
	private String dispatchPhoneNo;
	private String dispatchLstNumber;
	private String dispatchLstDate;

	private String billingLevel;
	private int commitmentPeriod;
	private long noticePeriod;
	private String logicalCircuitId;
	private String orderType;
	private String toDate;
	private String fromDate;
	private int toOrderNo;
	private int fromOrderNo;
	private int orderLineNumber;
	private String remarks;
	
	
	
	
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public int getFromOrderNo() {
		return fromOrderNo;
	}
	public void setFromOrderNo(int fromOrderNo) {
		this.fromOrderNo = fromOrderNo;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getToOrderNo() {
		return toOrderNo;
	}
	public void setToOrderNo(int toOrderNo) {
		this.toOrderNo = toOrderNo;
	}
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address1) {
		this.address1 = address1;
	}
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	public String getAddress3() {
		return address3;
	}
	public void setAddress3(String address3) {
		this.address3 = address3;
	}
	public String getAddress4() {
		return address4;
	}
	public void setAddress4(String address4) {
		this.address4 = address4;
	}
	public double getAnnual_rate() {
		return annual_rate;
	}
	public void setAnnual_rate(double annual_rate) {
		this.annual_rate = annual_rate;
	}
	public String getAttributeLabel() {
		return attributeLabel;
	}
	public void setAttributeLabel(String attributeLabel) {
		this.attributeLabel = attributeLabel;
	}
	public String getAttributeValue() {
		return attributeValue;
	}
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}
	public String getBillingFormatName() {
		return billingFormatName;
	}
	public void setBillingFormatName(String billingFormatName) {
		this.billingFormatName = billingFormatName;
	}
	public int getBillingInfoID() {
		return billingInfoID;
	}
	public void setBillingInfoID(int billingInfoID) {
		this.billingInfoID = billingInfoID;
	}
	public String getBillingLevel() {
		return billingLevel;
	}
	public void setBillingLevel(String billingLevel) {
		this.billingLevel = billingLevel;
	}
	public String getBillingTriggerDate() {
		return billingTriggerDate;
	}
	public void setBillingTriggerDate(String billingTriggerDate) {
		this.billingTriggerDate = billingTriggerDate;
	}
	public String getBillingTypeName() {
		return billingTypeName;
	}
	public void setBillingTypeName(String billingTypeName) {
		this.billingTypeName = billingTypeName;
	}
	public String getCharge_status() {
		return charge_status;
	}
	public void setCharge_status(String charge_status) {
		this.charge_status = charge_status;
	}
	public String getChargeAnnotation() {
		return chargeAnnotation;
	}
	public void setChargeAnnotation(String chargeAnnotation) {
		this.chargeAnnotation = chargeAnnotation;
	}
	public String getChargeEndDate() {
		return chargeEndDate;
	}
	public void setChargeEndDate(String chargeEndDate) {
		this.chargeEndDate = chargeEndDate;
	}
	public String getChargeinfoID() {
		return chargeinfoID;
	}
	public void setChargeinfoID(String chargeinfoID) {
		this.chargeinfoID = chargeinfoID;
	}
	public String getChargeName() {
		return chargeName;
	}
	public void setChargeName(String chargeName) {
		this.chargeName = chargeName;
	}
	public String getChargeTypeName() {
		return chargeTypeName;
	}
	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getCommitmentPeriod() {
		return commitmentPeriod;
	}
	public void setCommitmentPeriod(int commitmentPeriod) {
		this.commitmentPeriod = commitmentPeriod;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public int getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(int contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getCreditPeriodName() {
		return creditPeriodName;
	}
	public void setCreditPeriodName(String creditPeriodName) {
		this.creditPeriodName = creditPeriodName;
	}
	public String getCustPoDate() {
		return custPoDate;
	}
	public void setCustPoDate(String custPoDate) {
		this.custPoDate = custPoDate;
	}
	public String getCustPoDetailNo() {
		return custPoDetailNo;
	}
	public void setCustPoDetailNo(String custPoDetailNo) {
		this.custPoDetailNo = custPoDetailNo;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public String getDispatchAddress1() {
		return dispatchAddress1;
	}
	public void setDispatchAddress1(String dispatchAddress1) {
		this.dispatchAddress1 = dispatchAddress1;
	}
	public String getDispatchAddress2() {
		return dispatchAddress2;
	}
	public void setDispatchAddress2(String dispatchAddress2) {
		this.dispatchAddress2 = dispatchAddress2;
	}
	public String getDispatchAddress3() {
		return dispatchAddress3;
	}
	public void setDispatchAddress3(String dispatchAddress3) {
		this.dispatchAddress3 = dispatchAddress3;
	}
	public String getDispatchCityName() {
		return dispatchCityName;
	}
	public void setDispatchCityName(String dispatchCityName) {
		this.dispatchCityName = dispatchCityName;
	}
	public String getDispatchLstDate() {
		return dispatchLstDate;
	}
	public void setDispatchLstDate(String dispatchLstDate) {
		this.dispatchLstDate = dispatchLstDate;
	}
	public String getDispatchLstNumber() {
		return dispatchLstNumber;
	}
	public void setDispatchLstNumber(String dispatchLstNumber) {
		this.dispatchLstNumber = dispatchLstNumber;
	}
	public String getDispatchPersonName() {
		return dispatchPersonName;
	}
	public void setDispatchPersonName(String dispatchPersonName) {
		this.dispatchPersonName = dispatchPersonName;
	}
	public String getDispatchPhoneNo() {
		return dispatchPhoneNo;
	}
	public void setDispatchPhoneNo(String dispatchPhoneNo) {
		this.dispatchPhoneNo = dispatchPhoneNo;
	}
	public String getDispatchPostalCode() {
		return dispatchPostalCode;
	}
	public void setDispatchPostalCode(String dispatchPostalCode) {
		this.dispatchPostalCode = dispatchPostalCode;
	}
	public String getDispatchStateName() {
		return dispatchStateName;
	}
	public void setDispatchStateName(String dispatchStateName) {
		this.dispatchStateName = dispatchStateName;
	}
	public String getDnd_Disp_Del_Not_Installed() {
		return dnd_Disp_Del_Not_Installed;
	}
	public void setDnd_Disp_Del_Not_Installed(String dnd_Disp_Del_Not_Installed) {
		this.dnd_Disp_Del_Not_Installed = dnd_Disp_Del_Not_Installed;
	}
	public String getDnd_Disp_Delivered_Installed() {
		return dnd_Disp_Delivered_Installed;
	}
	public void setDnd_Disp_Delivered_Installed(String dnd_Disp_Delivered_Installed) {
		this.dnd_Disp_Delivered_Installed = dnd_Disp_Delivered_Installed;
	}
	public String getDnd_Dispatch_And_Delivered() {
		return dnd_Dispatch_And_Delivered;
	}
	public void setDnd_Dispatch_And_Delivered(String dnd_Dispatch_And_Delivered) {
		this.dnd_Dispatch_And_Delivered = dnd_Dispatch_And_Delivered;
	}
	public String getDnd_Dispatch_But_Not_Delivered() {
		return dnd_Dispatch_But_Not_Delivered;
	}
	public void setDnd_Dispatch_But_Not_Delivered(
			String dnd_Dispatch_But_Not_Delivered) {
		this.dnd_Dispatch_But_Not_Delivered = dnd_Dispatch_But_Not_Delivered;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public int getEndDateDays() {
		return endDateDays;
	}
	public void setEndDateDays(int endDateDays) {
		this.endDateDays = endDateDays;
	}
	public String getEndDateLogic() {
		return endDateLogic;
	}
	public void setEndDateLogic(String endDateLogic) {
		this.endDateLogic = endDateLogic;
	}
	public int getEndDateMonth() {
		return endDateMonth;
	}
	public void setEndDateMonth(int endDateMonth) {
		this.endDateMonth = endDateMonth;
	}
	public String getEntity() {
		return entity;
	}
	public void setEntity(String entity) {
		this.entity = entity;
	}
	public String getExtndSupportPeriodDays() {
		return extndSupportPeriodDays;
	}
	public void setExtndSupportPeriodDays(String extndSupportPeriodDays) {
		this.extndSupportPeriodDays = extndSupportPeriodDays;
	}
	public String getExtndSupportPeriodMonths() {
		return extndSupportPeriodMonths;
	}
	public void setExtndSupportPeriodMonths(String extndSupportPeriodMonths) {
		this.extndSupportPeriodMonths = extndSupportPeriodMonths;
	}
	public String getExtSuportEndDate() {
		return extSuportEndDate;
	}
	public void setExtSuportEndDate(String extSuportEndDate) {
		this.extSuportEndDate = extSuportEndDate;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getFormAvailable() {
		return formAvailable;
	}
	public void setFormAvailable(String formAvailable) {
		this.formAvailable = formAvailable;
	}
	public String getFrequencyAmt() {
		return frequencyAmt;
	}
	public void setFrequencyAmt(String frequencyAmt) {
		this.frequencyAmt = frequencyAmt;
	}
	public String getFrequencyName() {
		return frequencyName;
	}
	public void setFrequencyName(String frequencyName) {
		this.frequencyName = frequencyName;
	}
	public String getHardwaretypeName() {
		return hardwaretypeName;
	}
	public void setHardwaretypeName(String hardwaretypeName) {
		this.hardwaretypeName = hardwaretypeName;
	}
	public String getLcompanyname() {
		return lcompanyname;
	}
	public void setLcompanyname(String lcompanyname) {
		this.lcompanyname = lcompanyname;
	}
	public String getLogicalCircuitId() {
		return logicalCircuitId;
	}
	public void setLogicalCircuitId(String logicalCircuitId) {
		this.logicalCircuitId = logicalCircuitId;
	}
	public int getLogicalSINumber() {
		return logicalSINumber;
	}
	public void setLogicalSINumber(int logicalSINumber) {
		this.logicalSINumber = logicalSINumber;
	}
	public String getLst_No() {
		return lst_No;
	}
	public void setLst_No(String lst_No) {
		this.lst_No = lst_No;
	}
	public String getLstDate() {
		return lstDate;
	}
	public void setLstDate(String lstDate) {
		this.lstDate = lstDate;
	}
	public long getNoticePeriod() {
		return noticePeriod;
	}
	public void setNoticePeriod(long noticePeriod) {
		this.noticePeriod = noticePeriod;
	}
	public int getOrderLineNumber() {
		return orderLineNumber;
	}
	public void setOrderLineNumber(int orderLineNumber) {
		this.orderLineNumber = orderLineNumber;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	public String getPoAmount() {
		return poAmount;
	}
	public void setPoAmount(String poAmount) {
		this.poAmount = poAmount;
	}
	public String getPoExclude() {
		return poExclude;
	}
	public void setPoExclude(String poExclude) {
		this.poExclude = poExclude;
	}
	public String getPoRecieveDate() {
		return poRecieveDate;
	}
	public void setPoRecieveDate(String poRecieveDate) {
		this.poRecieveDate = poRecieveDate;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getRecordStatus() {
		return recordStatus;
	}
	public void setRecordStatus(String recordStatus) {
		this.recordStatus = recordStatus;
	}
	public String getSaleNature() {
		return saleNature;
	}
	public void setSaleNature(String saleNature) {
		this.saleNature = saleNature;
	}
	public String getSaleType() {
		return saleType;
	}
	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}
	public String getServiceDetDescription() {
		return serviceDetDescription;
	}
	public void setServiceDetDescription(String serviceDetDescription) {
		this.serviceDetDescription = serviceDetDescription;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public int getStartDateDays() {
		return startDateDays;
	}
	public void setStartDateDays(int startDateDays) {
		this.startDateDays = startDateDays;
	}
	public String getStartDateLogic() {
		return startDateLogic;
	}
	public void setStartDateLogic(String startDateLogic) {
		this.startDateLogic = startDateLogic;
	}
	public int getStartDateMonth() {
		return startDateMonth;
	}
	public void setStartDateMonth(int startDateMonth) {
		this.startDateMonth = startDateMonth;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getStoreName() {
		return storeName;
	}
	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}
	public String getTaxation() {
		return taxation;
	}
	public void setTaxation(String taxation) {
		this.taxation = taxation;
	}
	public String getTelePhoneNo() {
		return telePhoneNo;
	}
	public void setTelePhoneNo(String telePhoneNo) {
		this.telePhoneNo = telePhoneNo;
	}
	public String getTotalPoAmt() {
		return totalPoAmt;
	}
	public void setTotalPoAmt(String totalPoAmt) {
		this.totalPoAmt = totalPoAmt;
	}
	public String getWarrantyEndDate() {
		return warrantyEndDate;
	}
	public void setWarrantyEndDate(String warrantyEndDate) {
		this.warrantyEndDate = warrantyEndDate;
	}
	public String getWarrantyEndDateLogic() {
		return warrantyEndDateLogic;
	}
	public void setWarrantyEndDateLogic(String warrantyEndDateLogic) {
		this.warrantyEndDateLogic = warrantyEndDateLogic;
	}
	public String getWarrantyEndPeriodDays() {
		return warrantyEndPeriodDays;
	}
	public void setWarrantyEndPeriodDays(String warrantyEndPeriodDays) {
		this.warrantyEndPeriodDays = warrantyEndPeriodDays;
	}
	public String getWarrantyEndPeriodMonths() {
		return warrantyEndPeriodMonths;
	}
	public void setWarrantyEndPeriodMonths(String warrantyEndPeriodMonths) {
		this.warrantyEndPeriodMonths = warrantyEndPeriodMonths;
	}
	public String getWarrantyPeriodDays() {
		return warrantyPeriodDays;
	}
	public void setWarrantyPeriodDays(String warrantyPeriodDays) {
		this.warrantyPeriodDays = warrantyPeriodDays;
	}
	public String getWarrantyPeriodMonths() {
		return warrantyPeriodMonths;
	}
	public void setWarrantyPeriodMonths(String warrantyPeriodMonths) {
		this.warrantyPeriodMonths = warrantyPeriodMonths;
	}
	public String getWarrantyStartDate() {
		return warrantyStartDate;
	}
	public void setWarrantyStartDate(String warrantyStartDate) {
		this.warrantyStartDate = warrantyStartDate;
	}
	public String getWarrantyStartDateLogic() {
		return warrantyStartDateLogic;
	}
	public void setWarrantyStartDateLogic(String warrantyStartDateLogic) {
		this.warrantyStartDateLogic = warrantyStartDateLogic;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public int getIsUsage() {
		return isUsage;
	}
	public void setIsUsage(int isUsage) {
		this.isUsage = isUsage;
	}
	public ComponentsDto getComponentDto() {
		return componentDto;
	}
	public void setComponentDto(ComponentsDto componentDto) {
		this.componentDto = componentDto;
	}
	public String getChild_account_creation_status() {
		return child_account_creation_status;
	}
	public void setChild_account_creation_status(
			String child_account_creation_status) {
		this.child_account_creation_status = child_account_creation_status;
	}
	public int getComponentID() {
		return componentID;
	}
	public void setComponentID(int componentID) {
		this.componentID = componentID;
	}
	public int getComponentInfoID() {
		return componentInfoID;
	}
	public void setComponentInfoID(int componentInfoID) {
		this.componentInfoID = componentInfoID;
	}
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public String getFx_external_acc_id() {
		return fx_external_acc_id;
	}
	public void setFx_external_acc_id(String fx_external_acc_id) {
		this.fx_external_acc_id = fx_external_acc_id;
	}
	public int getFxInternalId() {
		return fxInternalId;
	}
	public void setFxInternalId(int fxInternalId) {
		this.fxInternalId = fxInternalId;
	}
	public int getPackageID() {
		return packageID;
	}
	public void setPackageID(int packageID) {
		this.packageID = packageID;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public String getDisconnection_remarks() {
		return disconnection_remarks;
	}
	public void setDisconnection_remarks(String disconnection_remarks) {
		this.disconnection_remarks = disconnection_remarks;
	}
	public int getM6OrderNo() {
		return m6OrderNo;
	}
	public void setM6OrderNo(int orderNo) {
		m6OrderNo = orderNo;
	}
	public String getNeworder_remarks() {
		return neworder_remarks;
	}
	public void setNeworder_remarks(String neworder_remarks) {
		this.neworder_remarks = neworder_remarks;
	}
	
	
	
	
	

}
