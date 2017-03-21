package com.ibm.ioes.schedular;

public class BulkuploadDto {
	
	
	
	private String  fileID =null;
	private String  lsiNo =null;
	private String  fullSuspend =null;
	private String  lineItemNo =null;
	private String  reasonId =null;
	private String  reasonForSuspension=null;
	private String  reasonForResumption =null;
	private String sr_No=null;
	private String date_Disconnection=null;
	private String date_Disconnection_Cust=null;
	private String date_Disconnection_Np=null;
	private String	date_Intimated=null;
	private String componentid;
	private long btDoneBy;
	
	//---------------SI TRANSFER------------------------
		private String sourcePartyNo;
		private String targetPartyNo;
		private String targetAccountNo;		
		private String bcpId;
		private String dispatchId;
		private String transferedDate;
		private String errors="";
		private String rowId;
		private String serviceId;
		private String accountId;
		private boolean isDispatchIdAvailableInSheet=false;
	//--------------------------------------------------
	//----Currency Transfer---------
		private String oldCurrencyId;
		private String newCurrencyId;
		private Double exchangeRate;
		boolean currencyIdNotPresent=false;
	//------------------------------
		private Integer respId;
	//------------------------------	
	
	boolean srcPartyNotPresent=false;
	boolean tgtPartyNotPresent=false;
	boolean tgtAccountNotPresent=false;
	boolean lsiNotPresent=false;
	boolean bcpIdNotPresent=false;
	boolean dispatchIdNotPresent=false;
	
	private String srcAccountNo;
	
	public String getSrcAccountNo() {
		return srcAccountNo;
	}
	public void setSrcAccountNo(String srcAccountNo) {
		this.srcAccountNo = srcAccountNo;
	}
	public boolean isSrcPartyNotPresent() {
		return srcPartyNotPresent;
	}
	public void setSrcPartyNotPresent(boolean srcPartyNotPresent) {
		this.srcPartyNotPresent = srcPartyNotPresent;
	}
	public boolean isTgtPartyNotPresent() {
		return tgtPartyNotPresent;
	}
	public void setTgtPartyNotPresent(boolean tgtPartyNotPresent) {
		this.tgtPartyNotPresent = tgtPartyNotPresent;
	}
	public boolean isTgtAccountNotPresent() {
		return tgtAccountNotPresent;
	}
	public void setTgtAccountNotPresent(boolean tgtAccountNotPresent) {
		this.tgtAccountNotPresent = tgtAccountNotPresent;
	}
	public boolean isBcpIdNotPresent() {
		return bcpIdNotPresent;
	}
	public void setBcpIdNotPresent(boolean bcpIdNotPresent) {
		this.bcpIdNotPresent = bcpIdNotPresent;
	}
	public boolean isDispatchIdNotPresent() {
		return dispatchIdNotPresent;
	}
	public void setDispatchIdNotPresent(boolean dispatchIdNotPresent) {
		this.dispatchIdNotPresent = dispatchIdNotPresent;
	}
	public boolean isLsiNotPresent() {
		return lsiNotPresent;
	}
	public void setLsiNotPresent(boolean lsiNotPresent) {
		this.lsiNotPresent = lsiNotPresent;
	}
	public long getBtDoneBy() {
		return btDoneBy;
	}
	public void setBtDoneBy(long btDoneBy) {
		this.btDoneBy = btDoneBy;
	}
	public String getComponentid() {
		return componentid;
	}
	public void setComponentid(String componentid) {
		this.componentid = componentid;
	}
	public String getReasonForSuspension() {
		return reasonForSuspension;
	}
	public void setReasonForSuspension(String reasonForSuspension) {
		this.reasonForSuspension = reasonForSuspension;
	}
	public String getReasonForResumption() {
		return reasonForResumption;
	}
	public void setReasonForResumption(String reasonForResumption) {
		this.reasonForResumption = reasonForResumption;
	}
	public String getSr_No() {
		return sr_No;
	}
	public void setSr_No(String sr_No) {
		this.sr_No = sr_No;
	}
	public String getDate_Disconnection() {
		return date_Disconnection;
	}
	public void setDate_Disconnection(String date_Disconnection) {
		this.date_Disconnection = date_Disconnection;
	}
	public String getDate_Disconnection_Cust() {
		return date_Disconnection_Cust;
	}
	public void setDate_Disconnection_Cust(String date_Disconnection_Cust) {
		this.date_Disconnection_Cust = date_Disconnection_Cust;
	}
	public String getDate_Disconnection_Np() {
		return date_Disconnection_Np;
	}
	public void setDate_Disconnection_Np(String date_Disconnection_Np) {
		this.date_Disconnection_Np = date_Disconnection_Np;
	}
	public String getDate_Intimated() {
		return date_Intimated;
	}
	public void setDate_Intimated(String date_Intimated) {
		this.date_Intimated = date_Intimated;
	}
	public String getFileID() {
		return fileID;
	}
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}
	public String getLsiNo() {
		return lsiNo;
	}
	public void setLsiNo(String lsiNo) {
		this.lsiNo = lsiNo;
	}
	public String getFullSuspend() {
		return fullSuspend;
	}
	public void setFullSuspend(String fullSuspend) {
		this.fullSuspend = fullSuspend;
	}
	public String getLineItemNo() {
		return lineItemNo;
	}
	public void setLineItemNo(String lineItemNo) {
		this.lineItemNo = lineItemNo;
	}
	public String getReasonId() {
		return reasonId;
	}
	public void setReasonId(String reasonId) {
		this.reasonId = reasonId;
	}
	public String getSourcePartyNo() {
		return sourcePartyNo;
	}
	public void setSourcePartyNo(String sourcePartyNo) {
		this.sourcePartyNo = sourcePartyNo;
	}
	public String getTargetPartyNo() {
		return targetPartyNo;
	}
	public void setTargetPartyNo(String targetPartyNo) {
		this.targetPartyNo = targetPartyNo;
	}
	public String getTargetAccountNo() {
		return targetAccountNo;
	}
	public void setTargetAccountNo(String targetAccountNo) {
		this.targetAccountNo = targetAccountNo;
	}	
	public String getBcpId() {
		return bcpId;
	}
	public void setBcpId(String bcpId) {
		this.bcpId = bcpId;
	}
	public String getDispatchId() {
		return dispatchId;
	}
	public void setDispatchId(String dispatchId) {
		this.dispatchId = dispatchId;
	}
	public String getTransferedDate() {
		return transferedDate;
	}
	public void setTransferedDate(String transferedDate) {
		this.transferedDate = transferedDate;
	}
	public String getErrors() {
		return errors;
	}
	public void setErrors(String errors) {
		this.errors = errors;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public boolean isDispatchIdAvailableInSheet() {
		return isDispatchIdAvailableInSheet;
	}
	public void setDispatchIdAvailableInSheet(boolean isAvailable) {
		this.isDispatchIdAvailableInSheet = isAvailable;
	}
	public String getOldCurrencyId() {
		return oldCurrencyId;
	}
	public void setOldCurrencyId(String oldCurrencyId) {
		this.oldCurrencyId = oldCurrencyId;
	}
	public String getNewCurrencyId() {
		return newCurrencyId;
	}
	public void setNewCurrencyId(String newCurrencyId) {
		this.newCurrencyId = newCurrencyId;
	}
	public Double getExchangeRate() {
		return exchangeRate;
	}
	public void setExchangeRate(Double exchangeRate) {
		this.exchangeRate = exchangeRate;
	}
	public boolean isCurrencyIdNotPresent() {
		return currencyIdNotPresent;
	}
	public void setCurrencyIdNotPresent(boolean currencyIdNotPresent) {
		this.currencyIdNotPresent = currencyIdNotPresent;
	}
	public Integer getRespId() {
		return respId;
	}
	public void setRespId(Integer respId) {
		this.respId = respId;
	}
	
	
	

}
