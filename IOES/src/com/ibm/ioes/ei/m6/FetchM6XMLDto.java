package com.ibm.ioes.ei.m6;

import java.util.ArrayList;

public class FetchM6XMLDto {

	
	
	private String xml = null;
	private Long xmlId = null;
	private int send_status;
	private String messageID=null;
	
	private String save_status=null;

	private String readResponseXMLId=null;
	
	private String 	crmorderId = null;
	private String 	ServiceListId = null;
	private String 	PreOrderNo = null;
	private String 	M6OrderId = null;
	private String 	orderStatus = null;
	private String 	eventId = null;
	private String 	eventTypeId = null;
	private String 	creationDate = null;
	private String 	reason = null;
	private String serviceProductId=null;
	
	private int status;
// Raghu
	 private ArrayList<FetchLastTaskNewOrderXMLDto> fetchLastTaskNewOrderXMLDtoList;
	 
	 // Start added by Saurabh for data driven values
	 private String m6Queue_host = null;
	 private String m6Queue_channel = null;
	 private String m6Queue_port = null;
	 private String m6Queue_qmgrName = null;
	 private String m6Queue_changeReqQName = null;
	 private String m6Queue_reqQName = null;
	 private String m6Queue_respQName = null;
	 private String M6_Response_Path = null;
	 // End
	 private String isHeaderRequired;
	 private String orderObjectType;
	 private String orderType;
	 
	 //neha
	 private String desiredDueDate;
	 //neha
	

	

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getIsHeaderRequired() {
		return isHeaderRequired;
	}

	public void setIsHeaderRequired(String isHeaderRequired) {
		this.isHeaderRequired = isHeaderRequired;
	}

	public String getOrderObjectType() {
		return orderObjectType;
	}

	public void setOrderObjectType(String orderObjectType) {
		this.orderObjectType = orderObjectType;
	}

	public int getSend_status() {
		return send_status;
	}

	public void setSend_status(int send_status) {
		this.send_status = send_status;
	}

	public String getXml() {
		return xml;
	}

	public void setXml(String xml) {
		this.xml = xml;
	}

	public Long getXmlId() {
		return xmlId;
	}

	public void setXmlId(Long xmlId) {
		this.xmlId = xmlId;
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}

	public String getSave_status() {
		return save_status;
	}

	public void setSave_status(String save_status) {
		this.save_status = save_status;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getCrmorderId() {
		return crmorderId;
	}

	public void setCrmorderId(String crmorderId) {
		this.crmorderId = crmorderId;
	}

	public String getEventId() {
		return eventId;
	}

	public void setEventId(String eventId) {
		this.eventId = eventId;
	}

	public String getEventTypeId() {
		return eventTypeId;
	}

	public void setEventTypeId(String eventTypeId) {
		this.eventTypeId = eventTypeId;
	}

	public String getM6OrderId() {
		return M6OrderId;
	}

	public void setM6OrderId(String orderId) {
		M6OrderId = orderId;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPreOrderNo() {
		return PreOrderNo;
	}

	public void setPreOrderNo(String preOrderNo) {
		PreOrderNo = preOrderNo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getServiceListId() {
		return ServiceListId;
	}

	public void setServiceListId(String serviceListId) {
		ServiceListId = serviceListId;
	}

	public String getReadResponseXMLId() {
		return readResponseXMLId;
	}

	public void setReadResponseXMLId(String readResponseXMLId) {
		this.readResponseXMLId = readResponseXMLId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public ArrayList<FetchLastTaskNewOrderXMLDto> getFetchLastTaskNewOrderXMLDtoList() {
		return fetchLastTaskNewOrderXMLDtoList;
	}

	public void setFetchLastTaskNewOrderXMLDtoList(
			ArrayList<FetchLastTaskNewOrderXMLDto> fetchLastTaskNewOrderXMLDtoList) {
		this.fetchLastTaskNewOrderXMLDtoList = fetchLastTaskNewOrderXMLDtoList;
	}

	public String getM6_Response_Path() {
		return M6_Response_Path;
	}

	public void setM6_Response_Path(String response_Path) {
		M6_Response_Path = response_Path;
	}

	public String getM6Queue_changeReqQName() {
		return m6Queue_changeReqQName;
	}

	public void setM6Queue_changeReqQName(String queue_changeReqQName) {
		m6Queue_changeReqQName = queue_changeReqQName;
	}

	public String getM6Queue_channel() {
		return m6Queue_channel;
	}

	public void setM6Queue_channel(String queue_channel) {
		m6Queue_channel = queue_channel;
	}

	public String getM6Queue_host() {
		return m6Queue_host;
	}

	public void setM6Queue_host(String queue_host) {
		m6Queue_host = queue_host;
	}

	public String getM6Queue_port() {
		return m6Queue_port;
	}

	public void setM6Queue_port(String queue_port) {
		m6Queue_port = queue_port;
	}

	public String getM6Queue_qmgrName() {
		return m6Queue_qmgrName;
	}

	public void setM6Queue_qmgrName(String queue_qmgrName) {
		m6Queue_qmgrName = queue_qmgrName;
	}

	public String getM6Queue_reqQName() {
		return m6Queue_reqQName;
	}

	public void setM6Queue_reqQName(String queue_reqQName) {
		m6Queue_reqQName = queue_reqQName;
	}

	public String getM6Queue_respQName() {
		return m6Queue_respQName;
	}

	public void setM6Queue_respQName(String queue_respQName) {
		m6Queue_respQName = queue_respQName;
	}

	public String getServiceProductId() {
		return serviceProductId;
	}

	public void setServiceProductId(String serviceProductId) {
		this.serviceProductId = serviceProductId;
	}

	//neha
	
	public String getDesiredDueDate() {
		return desiredDueDate;
	}

	public void setDesiredDueDate(String desiredDueDate) {
		this.desiredDueDate = desiredDueDate;
	}
	
	//neha
	
}
