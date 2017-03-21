package com.ibm.ioes.forms;

import java.util.ArrayList;

public class CurrencyChangeDto extends PagingDto {
	
	private String sourcePartyName;
	private String targetPartyName;
	private String sourcePartyNo;
	private String targetPartyNo;
	private String accountno;
	private String accountcreation;
	private String neworderno;
	private String bdisconorderno;
	private String remarks;
	private String transferdate;
	private String error;
	private int  spid;
	private int 	sino;
	private int  bcpId;
	private String bcpname;
	private int dispatchId;
	private String dispatchName;
	private String searchBatchId;	
	private String productname;
		
	private String dateOfTransfer;
	private String statusOfCurrencyChange;
	private int batchID;
	
	private String searchLogicalSI;
	private String progress;
	private int oldCurrencyId;
	private String oldCurrencyName;
	private int newCurrencyId;
	private String newCurrencyName;
	private double rate;
	
	//ArrayList<String> logicalSis = new ArrayList<String>();
	
	String [] logicalSiList ;
	String logicalSistr;
	
	private String[] oldCurList;
	private String oldCurIds;
	private String[] rateList;
	private String rateStr;
	private String[] newCurList;
	private String newCurIds;
	private String[] listOfTransferDate;
	
	private String filterLSIList;
	
	public String getSourceModule() {
		return sourceModule;
	}
	public void setSourceModule(String sourceModule) {
		this.sourceModule = sourceModule;
	}
	public String getFilterLSIList() {
		return filterLSIList;
	}
	public void setFilterLSIList(String filterLSIList) {
		this.filterLSIList = filterLSIList;
	}
	public String[] getListOfTransferDate() {
		return listOfTransferDate;
	}
	public void setListOfTransferDate(String[] listOfTransferDate) {
		this.listOfTransferDate = listOfTransferDate;
	}
	public String getLogicalSistr() {
		return logicalSistr;
	}
	public void setLogicalSistr(String logicalSistr) {
		this.logicalSistr = logicalSistr;
	}
	public String[] getLogicalSiList() {
		return logicalSiList;
	}
	public void setLogicalSiList(String[] logicalSiList) {
		this.logicalSiList = logicalSiList;
	}
	
	private String userSpecificError = "";
	private String sourceModule="";
	
	public enum SuccessFailure{
		SUCCESS,FAILURE
	}
	
	public String getUserSpecificError() {
		return userSpecificError;
	}
	public void setUserSpecificError(String userSpecificError) {
		this.userSpecificError = userSpecificError;
	}
	//public ArrayList getLogicalSis() {
	//	return logicalSis;
	//}
	
	public String getAccountcreation() {
		return accountcreation;
	}
	public void setAccountcreation(String accountcreation) {
		this.accountcreation = accountcreation;
	}
	
	public int getSino() {
		return sino;
	}
	public void setSino(int sino) {
		this.sino = sino;
	}
	public int getBcpId() {
		return bcpId;
	}
	public void setBcpId(int bcpId) {
		this.bcpId = bcpId;
	}
		public String getBcpname() {
			return bcpname;
		}
		public void setBcpname(String bcpname) {
			this.bcpname = bcpname;
		}
		public int getSpid() {
		return spid;
	}
	public void setSpid(int spid) {
		this.spid = spid;
	}
	public int getBatchID() {
		return batchID;
	}
	public void setBatchID(int batchID) {
		this.batchID = batchID;
	}
	public String getBdisconorderno() {
		return bdisconorderno;
	}
	public void setBdisconorderno(String bdisconorderno) {
		this.bdisconorderno = bdisconorderno;
	}
	public String getDateOfTransfer() {
		return dateOfTransfer;
	}
	public void setDateOfTransfer(String dateOfTransfer) {
		this.dateOfTransfer = dateOfTransfer;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public String getNeworderno() {
		return neworderno;
	}
	public void setNeworderno(String neworderno) {
		this.neworderno = neworderno;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
		
	public String getStatusOfCurrencyChange() {
		return statusOfCurrencyChange;
	}
	public void setStatusOfCurrencyChange(String statusOfCurrencyChange) {
		this.statusOfCurrencyChange = statusOfCurrencyChange;
	}
	public String getTransferdate() {
		return transferdate;
	}
	public void setTransferdate(String transferdate) {
		this.transferdate = transferdate;
	}
	public String getAccountno() {
		return accountno;
	}
	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}
	public String getSourcePartyName() {
		return sourcePartyName;
	}
	public void setSourcePartyName(String sourcePartyName) {
		this.sourcePartyName = sourcePartyName;
	}
	public String getSourcePartyNo() {
		return sourcePartyNo;
	}
	public void setSourcePartyNo(String sourcePartyNo) {
		this.sourcePartyNo = sourcePartyNo;
	}
	public String getTargetPartyName() {
		return targetPartyName;
	}
	public void setTargetPartyName(String targetPartyName) {
		this.targetPartyName = targetPartyName;
	}
	public String getTargetPartyNo() {
		return targetPartyNo;
	}
	public void setTargetPartyNo(String targetPartyNo) {
		this.targetPartyNo = targetPartyNo;
	}
	//public void setLogicalSis(ArrayList<String> logicalSis) {
		//this.logicalSis = logicalSis;
	//}
	
	public int getDispatchId() {
		return dispatchId;
	}
	public void setDispatchId(int dispatchId) {
		this.dispatchId = dispatchId;
	}
	public String getDispatchName() {
		return dispatchName;
	}
	public void setDispatchName(String dispatchName) {
		this.dispatchName = dispatchName;
	}
	public String getSearchBatchId() {
		return searchBatchId;
	}
	public void setSearchBatchId(String searchBatchId) {
		this.searchBatchId = searchBatchId;
	}
	public String getProgress() {
		return progress;
	}
	public void setProgress(String progress) {
		this.progress = progress;
	}
	public String getSearchLogicalSI() {
		return searchLogicalSI;
	}
	public void setSearchLogicalSI(String searchLogicalSI) {
		this.searchLogicalSI = searchLogicalSI;
	}
	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public int getNewCurrencyId() {
		return newCurrencyId;
	}
	public void setNewCurrencyId(int newCurrencyId) {
		this.newCurrencyId = newCurrencyId;
	}
	public String getNewCurrencyName() {
		return newCurrencyName;
	}
	public void setNewCurrencyName(String newCurrencyName) {
		this.newCurrencyName = newCurrencyName;
	}
	public int getOldCurrencyId() {
		return oldCurrencyId;
	}
	public void setOldCurrencyId(int oldCurrencyId) {
		this.oldCurrencyId = oldCurrencyId;
	}
	public String getOldCurrencyName() {
		return oldCurrencyName;
	}
	public void setOldCurrencyName(String oldCurrencyName) {
		this.oldCurrencyName = oldCurrencyName;
	}
	
	public double getRate() {
		return rate;
	}
	public void setRate(double rate) {
		this.rate = rate;
	}
	public String getNewCurIds() {
		return newCurIds;
	}
	public void setNewCurIds(String newCurIds) {
		this.newCurIds = newCurIds;
	}
	public String[] getNewCurList() {
		return newCurList;
	}
	public void setNewCurList(String[] newCurList) {
		this.newCurList = newCurList;
	}
	public String getOldCurIds() {
		return oldCurIds;
	}
	public void setOldCurIds(String oldCurIds) {
		this.oldCurIds = oldCurIds;
	}
	public String[] getOldCurList() {
		return oldCurList;
	}
	public void setOldCurList(String[] oldCurList) {
		this.oldCurList = oldCurList;
	}
	public String[] getRateList() {
		return rateList;
	}
	public void setRateList(String[] rateList) {
		this.rateList = rateList;
	}
	public String getRateStr() {
		return rateStr;
	}
	public void setRateStr(String rateStr) {
		this.rateStr = rateStr;
	}
	
}
