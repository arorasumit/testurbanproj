package com.ibm.ioes.newdesign.dto;

//Tag Name 	Resource Name  	Date		CSR No						Description
//[001]	 	VIPIN SAHARIA	13-Apr-15	CBR_20150202-R2-021036		Sending ePCD and SMS alerts to customer at every order life cycle stage

public class WelcomeMailSMSDTO {

	private long serviceId;
	private long serviceProductId;
	private long createdInServiceId;
	private long orderNo;
	private String event;
	private int id;
	private int serviceDetailId;
	
	private long circuitId;
	private String orderType;
	private String custEmail;
	private String custPhone;
	private String accMngrEmail;
	private String accMngrPhone;
	private String custAddress;
	
	private String salutation;
	private String custName;
	private String accName;
	private String commDate;
	private String url;
	private String userid;
	private String password;
	private String m6OrderNo;
	private String bandwidth;
	
	private int mailSmsFlag;
	private String preStatus;
	private String mailSMSStatus;
	private String toList;
	private String ccList;
	private String cellNoList;
	private int subChangeTypeId;
	
	public long getServiceId() {
		return serviceId;
	}
	public void setServiceId(long serviceId) {
		this.serviceId = serviceId;
	}
	public long getServiceProductId() {
		return serviceProductId;
	}
	public void setServiceProductId(long serviceProductId) {
		this.serviceProductId = serviceProductId;
	}
	public long getCreatedInServiceId() {
		return createdInServiceId;
	}
	public void setCreatedInServiceId(long createdInServiceId) {
		this.createdInServiceId = createdInServiceId;
	}
	public long getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(long orderNo) {
		this.orderNo = orderNo;
	}
	public String getEvent() {
		return event;
	}
	public void setEvent(String event) {
		this.event = event;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getServiceDetailId() {
		return serviceDetailId;
	}
	public void setServiceDetailId(int serviceDetailId) {
		this.serviceDetailId = serviceDetailId;
	}
	public long getCircuitId() {
		return circuitId;
	}
	public void setCircuitId(long circuitId) {
		this.circuitId = circuitId;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	public String getCustPhone() {
		return custPhone;
	}
	public void setCustPhone(String custPhone) {
		this.custPhone = custPhone;
	}
	public String getAccMngrEmail() {
		return accMngrEmail;
	}
	public void setAccMngrEmail(String accMngrEmail) {
		this.accMngrEmail = accMngrEmail;
	}
	public String getAccMngrPhone() {
		return accMngrPhone;
	}
	public void setAccMngrPhone(String accMngrPhone) {
		this.accMngrPhone = accMngrPhone;
	}
	public String getCustAddress() {
		return custAddress;
	}
	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}
	public String getSalutation() {
		return salutation;
	}
	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getAccName() {
		return accName;
	}
	public void setAccName(String accName) {
		this.accName = accName;
	}
	public String getCommDate() {
		return commDate;
	}
	public void setCommDate(String commDate) {
		this.commDate = commDate;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getM6OrderNo() {
		return m6OrderNo;
	}
	public void setM6OrderNo(String m6OrderNo) {
		this.m6OrderNo = m6OrderNo;
	}
	public String getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	public int getMailSmsFlag() {
		return mailSmsFlag;
	}
	public void setMailSmsFlag(int mailSmsFlag) {
		this.mailSmsFlag = mailSmsFlag;
	}
	public String getPreStatus() {
		return preStatus;
	}
	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}
	public String getMailSMSStatus() {
		return mailSMSStatus;
	}
	public void setMailSMSStatus(String mailSMSStatus) {
		this.mailSMSStatus = mailSMSStatus;
	}
	public String getToList() {
		return toList;
	}
	public void setToList(String toList) {
		this.toList = toList;
	}
	public String getCcList() {
		return ccList;
	}
	public void setCcList(String ccList) {
		this.ccList = ccList;
	}
	public String getCellNoList() {
		return cellNoList;
	}
	public void setCellNoList(String cellNoList) {
		this.cellNoList = cellNoList;
	}
	public int getSubChangeTypeId() {
		return subChangeTypeId;
	}
	public void setSubChangeTypeId(int subChangeTypeId) {
		this.subChangeTypeId = subChangeTypeId;
	}
	
}
