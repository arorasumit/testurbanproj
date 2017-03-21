package com.ibm.ioes.ei.scm.dto;
//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Kalpana	11-January-2014			New File for receive attributes for third party
import java.util.ArrayList;
import java.util.HashMap;

public class SCMRecXmlDto {
	
	
	private String prNumber;
	private String prValue;
	private String prStatus;
	private int totalLine;
	private String nfaNumber;
	private int serviceId;
	private int servicePrdId;
	private String operatnExec;
	private String messageID=null;
	private int orderNumber;
	private int m6OrderNumber;
	private String  delToRequestor;
	 private String  preparer;
	 private String  headerDesc;
	 private String  needByDate ;
	 private String  circle	;
	 private String  buyingType;
	 private String  fdoaCapOpType;
	 private String  fdoaZoneLoc;
	 private String  ib2bRemarks;
	 private String  prType; 
	 private String  prDate;
	 private String  requestor; 
	 private String  prRaisedBy;
	 private String  prDesc;
	 private String authStatus;
	 private String creatnDate;
	 private String orderType;
	 private HashMap<String,String> attrMap;
	 private String errorCode;
	 private String errorMess;
	 private int oldServiceId;
	private int oldServicePrdId;
	private int oldOrderNumber;
	private int oldM6OrderNumber;
		
	
	 
	public int getOldM6OrderNumber() {
		return oldM6OrderNumber;
	}
	public void setOldM6OrderNumber(int oldM6OrderNumber) {
		this.oldM6OrderNumber = oldM6OrderNumber;
	}
	private ArrayList<PRCreatnSCMXmlDto> prCreatnSCMXmlDtoList;
	
	public String getPrNumber() {
		return prNumber;
	}
	public void setPrNumber(String prNumber) {
		this.prNumber = prNumber;
	}
	public String getPrValue() {
		return prValue;
	}
	public void setPrValue(String prValue) {
		this.prValue = prValue;
	}
	public String getPrStatus() {
		return prStatus;
	}
	public void setPrStatus(String prStatus) {
		this.prStatus = prStatus;
	}
	public int getTotalLine() {
		return totalLine;
	}
	public void setTotalLine(int totalLine) {
		this.totalLine = totalLine;
	}
	public String getNfaNumber() {
		return nfaNumber;
	}
	public void setNfaNumber(String nfaNumber) {
		this.nfaNumber = nfaNumber;
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
	public int getM6OrderNumber() {
		return m6OrderNumber;
	}
	public void setM6OrderNumber(int m6OrderNumber) {
		this.m6OrderNumber = m6OrderNumber;
	}
	public ArrayList<PRCreatnSCMXmlDto> getPrCreatnSCMXmlDtoList() {
		return prCreatnSCMXmlDtoList;
	}
	public void setPrCreatnSCMXmlDtoList(
ArrayList<PRCreatnSCMXmlDto> prCreatnSCMXmlDtoList) {
		this.prCreatnSCMXmlDtoList = prCreatnSCMXmlDtoList;
	}
	public String getDelToRequestor() {
		return delToRequestor;
	}
	public void setDelToRequestor(String delToRequestor) {
		this.delToRequestor = delToRequestor;
	}
	public String getPreparer() {
		return preparer;
	}
	public void setPreparer(String preparer) {
		this.preparer = preparer;
	}
	public String getHeaderDesc() {
		return headerDesc;
	}
	public void setHeaderDesc(String headerDesc) {
		this.headerDesc = headerDesc;
	}
	public String getNeedByDate() {
		return needByDate;
	}
	public void setNeedByDate(String needByDate) {
		this.needByDate = needByDate;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public String getBuyingType() {
		return buyingType;
	}
	public void setBuyingType(String buyingType) {
		this.buyingType = buyingType;
	}
	public String getFdoaCapOpType() {
		return fdoaCapOpType;
	}
	public void setFdoaCapOpType(String fdoaCapOpType) {
		this.fdoaCapOpType = fdoaCapOpType;
	}
	public String getFdoaZoneLoc() {
		return fdoaZoneLoc;
	}
	public void setFdoaZoneLoc(String fdoaZoneLoc) {
		this.fdoaZoneLoc = fdoaZoneLoc;
	}
	public String getIb2bRemarks() {
		return ib2bRemarks;
	}
	public void setIb2bRemarks(String ib2bRemarks) {
		this.ib2bRemarks = ib2bRemarks;
	}
	public String getPrType() {
		return prType;
	}
	public void setPrType(String prType) {
		this.prType = prType;
	}
	public String getPrDate() {
		return prDate;
	}
	public void setPrDate(String prDate) {
		this.prDate = prDate;
	}
	public String getRequestor() {
		return requestor;
	}
	public void setRequestor(String requestor) {
		this.requestor = requestor;
	}
	public String getPrRaisedBy() {
		return prRaisedBy;
	}
	public void setPrRaisedBy(String prRaisedBy) {
		this.prRaisedBy = prRaisedBy;
	}
	public String getPrDesc() {
		return prDesc;
	}
	public void setPrDesc(String prDesc) {
		this.prDesc = prDesc;
	}
	public String getAuthStatus() {
		return authStatus;
	}
	public void setAuthStatus(String authStatus) {
		this.authStatus = authStatus;
	}
	public String getCreatnDate() {
		return creatnDate;
	}
	public void setCreatnDate(String creatnDate) {
		this.creatnDate = creatnDate;
	}
	public HashMap<String, String> getAttrMap() {
		return attrMap;
	}
	public void setAttrMap(HashMap<String, String> attrMap) {
		this.attrMap = attrMap;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMess() {
		return errorMess;
	}
	public void setErrorMess(String errorMess) {
		this.errorMess = errorMess;
	}
	public int getOldServiceId() {
		return oldServiceId;
	}
	public void setOldServiceId(int oldServiceId) {
		this.oldServiceId = oldServiceId;
	}
	public int getOldServicePrdId() {
		return oldServicePrdId;
	}
	public void setOldServicePrdId(int oldServicePrdId) {
		this.oldServicePrdId = oldServicePrdId;
	}
	public int getOldOrderNumber() {
		return oldOrderNumber;
	}
	public void setOldOrderNumber(int oldOrderNumber) {
		this.oldOrderNumber = oldOrderNumber;
	}
	
}
