package com.ibm.ioes.forms;

public class ChargeSummaryServiceDto {

	private String serviceId = null;
	
	private String serviceType = null;
	private String serviceSubType = null;
	private String serviceStage = null;
	private String custLogicalSINo = null;
	private String effectiveStartDate = null;
	private String effectiveEndDate = null;
	private String remarks = null;
	private String logicalSINo = null;
	//lawkush Start
	private String productType = null;
	private String rfsDate = null;
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getRfsDate() {
		return rfsDate;
	}
	public void setRfsDate(String rfsDate) {
		this.rfsDate = rfsDate;
	}
	//lawkush End
	public String getLogicalSINo() {
		return logicalSINo;
	}
	public void setLogicalSINo(String logicalSINo) {
		this.logicalSINo = logicalSINo;
	}
	public String getCustLogicalSINo() {
		return custLogicalSINo;
	}
	public void setCustLogicalSINo(String custLogicalSINo) {
		this.custLogicalSINo = custLogicalSINo;
	}
	public String getEffectiveEndDate() {
		return effectiveEndDate;
	}
	public void setEffectiveEndDate(String effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}
	public String getEffectiveStartDate() {
		return effectiveStartDate;
	}
	public void setEffectiveStartDate(String effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getServiceStage() {
		return serviceStage;
	}
	public void setServiceStage(String serviceStage) {
		this.serviceStage = serviceStage;
	}
	public String getServiceSubType() {
		return serviceSubType;
	}
	public void setServiceSubType(String serviceSubType) {
		this.serviceSubType = serviceSubType;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
}
