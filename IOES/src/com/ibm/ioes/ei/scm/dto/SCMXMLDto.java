package com.ibm.ioes.ei.scm.dto;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Kalpana	11-January-2014			New File for send xml to SCM for third party

public class SCMXMLDto {
	
	 
	 private String xml = null;
	 private int send_status;
	 private String messageID=null;
	 private int orderNumber;
	 private Long xmlId = null;
	 private int serviceId;
	 private int servicePrdId;
	 private String operatnExec;
	 private String scmProgressStatus = null;
	 private int prId;
	 private String attValue;
	 private Long serviceDetailId;
		
	public Long getXmlId() {
		return xmlId;
	}
	public void setXmlId(Long xmlId) {
		this.xmlId = xmlId;
	}

	public String getXml() {
		return xml;
	}
	public void setXml(String xml) {
		this.xml = xml;
	}
	public int getSend_status() {
		return send_status;
	}
	public void setSend_status(int send_status) {
		this.send_status = send_status;
	}
	public String getMessageID() {
		return messageID;
	}
	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public int getServicePrdId() {
		return servicePrdId;
	}
	public void setServicePrdId(int servicePrdId) {
		this.servicePrdId = servicePrdId;
	}
	public String getOperatnExec() {
		return operatnExec;
	}
	public void setOperatnExec(String operatnExec) {
		this.operatnExec = operatnExec;
	}
	
	public int hashCode(){
		return this.orderNumber;
	}
	
	public boolean equals(Object obj){
		if(this.orderNumber==((SCMXMLDto)obj).getOrderNumber() && 
				this.serviceId==((SCMXMLDto)obj).getServiceId() && 
				this.servicePrdId==((SCMXMLDto)obj).getServicePrdId() && 
				this.operatnExec.equalsIgnoreCase(((SCMXMLDto)obj).getOperatnExec())){
			return true;
		}else{
			return false;
		}
	}
	public String getScmProgressStatus() {
		return scmProgressStatus;
	}
	public void setScmProgressStatus(String scmProgressStatus) {
		this.scmProgressStatus = scmProgressStatus;
	}
	public int getPrId() {
		return prId;
	}
	public void setPrId(int prId) {
		this.prId = prId;
	}
	public String getAttValue() {
		return attValue;
	}
	public void setAttValue(String attValue) {
		this.attValue = attValue;
	}
	public Long getServiceDetailId() {
		return serviceDetailId;
	}
	public void setServiceDetailId(Long serviceDetailId) {
		this.serviceDetailId = serviceDetailId;
	}


}
