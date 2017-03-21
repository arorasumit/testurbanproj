package com.ibm.ioes.forms;

import java.util.ArrayList;

import com.ibm.ioes.utilities.PagingSorting;

//[001] Rohit Verma		00-07481	Adding 16 New DTO for MAPPING LSI Interface
//[002] Rohit Verma		00-08440	Adding  New DTO for Cancel Hardware Line item GUI
//[003] VIPIN SAHARIA 04-06-2014 Added fxChareId required for extra logic DC_COLO_SERVICE_TAX Charge (Hardware DC)
public class LineItemDTO {
	private int userBasedCount;
	private int siteBasedCount;
	private int flexiCount;
	private int lineItemId;
	private String lineItemName;
	private int sublineItemId;
	private String subLineItemName;
	private int chargeInfoID;
	private String lineItemLbl;
	private String subLineItemLbl;
	
	//[001] Start
	private String lsiNO;
	private String productName;
	private String lineNo;
	private String lineName;
	private String redirectedLSINo;
	private String redirectedLineNo;
	private String redirectedLineName;
	private String redirectedProductName;
	private String mappedLSINo;
	private String mappedLineNo;
	private String mappedLineName;
	private String mappedProductName;
	private String accountName;
	private String accountNo;
	private int maxPageNo;
	private PagingDto pagingDto =new PagingDto();
	private int pagingRequired;
	//[001] End
	
	//[002] Start
	 private String orderNo;
	 private String m6OrderNo;
	 private String circuitID;
	 private String orderStage;
	 private String orderType;
	 private String serviceName;
	 private String serviceNo;
	 private ArrayList<LineItemDTO> lineItemStatusList;
	 private String requestStatus;
	 //[002] End
	 private int fxChareId;
	 
	 public String getRequestStatus() {
		return requestStatus;
	}


	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}


	public String getServiceName() {
		return serviceName;
	}


	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


	//[002] End 
	//PagingSorting pagingSorting = new PagingSorting();
	public PagingDto getPagingDto() {
		return pagingDto;
	}


	public void setPagingDto(PagingDto pagingDto) {
		this.pagingDto = pagingDto;
	}


	public String getAccountName() {
		return accountName;
	}


	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}


	public String getAccountNo() {
		return accountNo;
	}


	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}


	public String getSubLineItemLbl() {
		return subLineItemLbl;
	}


	public void setSubLineItemLbl(String subLineItemLbl) {
		this.subLineItemLbl = subLineItemLbl;
	}
	public String getLineItemLbl() {
		return lineItemLbl;
	}


	public void setLineItemLbl(String lineItemLbl) {
		this.lineItemLbl = lineItemLbl;
	}
	public int getChargeInfoID() {
		return chargeInfoID;
	}

	public void setChargeInfoID(int chargeInfoID) {
		this.chargeInfoID = chargeInfoID;
	}
	public String getSubLineItemName() {
		return subLineItemName;
	}
	public void setSubLineItemName(String subLineItemName) {
		this.subLineItemName = subLineItemName;
	}
	public int getSublineItemId() {
		return sublineItemId;
	}
	public void setSublineItemId(int sublineItemId) {
		this.sublineItemId = sublineItemId;
	}
	public String getLineItemName() {
		return lineItemName;
	}
	public void setLineItemName(String lineItemName) {
		this.lineItemName = lineItemName;
	}
	public int getLineItemId() {
		return lineItemId;
	}
	public void setLineItemId(int lineItemId) {
		this.lineItemId = lineItemId;
	}
	public int getFlexiCount() {
		return flexiCount;
	}


	public void setFlexiCount(int flexiCount) {
		this.flexiCount = flexiCount;
	}

	public int getSiteBasedCount() {
		return siteBasedCount;
	}


	public void setSiteBasedCount(int siteBasedCount) {
		this.siteBasedCount = siteBasedCount;
	}
	public int getUserBasedCount() {
		return userBasedCount;
	}


	public void setUserBasedCount(int userBasedCount) {
		this.userBasedCount = userBasedCount;
	}


	public String getLineName() {
		return lineName;
	}


	public void setLineName(String lineName) {
		this.lineName = lineName;
	}


	public String getLineNo() {
		return lineNo;
	}


	public void setLineNo(String lineNo) {
		this.lineNo = lineNo;
	}


	public String getLsiNO() {
		return lsiNO;
	}


	public void setLsiNO(String lsiNO) {
		this.lsiNO = lsiNO;
	}


	public String getProductName() {
		return productName;
	}


	public void setProductName(String productName) {
		this.productName = productName;
	}


	public String getRedirectedLineName() {
		return redirectedLineName;
	}


	public void setRedirectedLineName(String redirectedLineName) {
		this.redirectedLineName = redirectedLineName;
	}


	public String getRedirectedLineNo() {
		return redirectedLineNo;
	}


	public void setRedirectedLineNo(String redirectedLineNo) {
		this.redirectedLineNo = redirectedLineNo;
	}


	public String getRedirectedLSINo() {
		return redirectedLSINo;
	}


	public void setRedirectedLSINo(String redirectedLSINo) {
		this.redirectedLSINo = redirectedLSINo;
	}


	public String getRedirectedProductName() {
		return redirectedProductName;
	}


	public void setRedirectedProductName(String redirectedProductName) {
		this.redirectedProductName = redirectedProductName;
	}


	public String getMappedLineName() {
		return mappedLineName;
	}


	public void setMappedLineName(String mappedLineName) {
		this.mappedLineName = mappedLineName;
	}


	public String getMappedLineNo() {
		return mappedLineNo;
	}


	public void setMappedLineNo(String mappedLineNo) {
		this.mappedLineNo = mappedLineNo;
	}


	public String getMappedLSINo() {
		return mappedLSINo;
	}


	public void setMappedLSINo(String mappedLSINo) {
		this.mappedLSINo = mappedLSINo;
	}


	public String getMappedProductName() {
		return mappedProductName;
	}


	public void setMappedProductName(String mappedProductName) {
		this.mappedProductName = mappedProductName;
	}


	public int getMaxPageNo() {
		return maxPageNo;
	}


	public void setMaxPageNo(int maxPageNo) {
		this.maxPageNo = maxPageNo;
	}

	public int getPagingRequired() {
		return pagingRequired;
	}


	public void setPagingRequired(int pagingRequired) {
		this.pagingRequired = pagingRequired;
	}


	public String getCircuitID() {
		return circuitID;
	}


	public void setCircuitID(String circuitID) {
		this.circuitID = circuitID;
	}


	public String getM6OrderNo() {
		return m6OrderNo;
	}


	public void setM6OrderNo(String orderNo) {
		m6OrderNo = orderNo;
	}


	public String getOrderNo() {
		return orderNo;
	}


	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public String getOrderStage() {
		return orderStage;
	}


	public void setOrderStage(String orderStage) {
		this.orderStage = orderStage;
	}


	public String getOrderType() {
		return orderType;
	}


	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}


	public String getServiceNo() {
		return serviceNo;
	}


	public void setServiceNo(String serviceNo) {
		this.serviceNo = serviceNo;
	}


	public ArrayList<LineItemDTO> getLineItemStatusList() {
		return lineItemStatusList;
	}


	public void setLineItemStatusList(ArrayList<LineItemDTO> lineItemStatusList) {
		this.lineItemStatusList = lineItemStatusList;
	}

	public int getFxChareId() {
		return fxChareId;
	}
	public void setFxChareId(int fxChareId) {
		this.fxChareId = fxChareId;
	}

}
