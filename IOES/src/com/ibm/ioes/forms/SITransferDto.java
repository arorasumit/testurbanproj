package com.ibm.ioes.forms;

import java.util.ArrayList;

public class SITransferDto extends NewOrderDto {
	private String  cktid;
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
	private String statusOfSITransfer;
	private int batchID;
	
	private String searchLogicalSI;
	private String progress;
	
	private String bcp_details;
	private String dispatch_details;
	private int hardwareAllowed;
	private String bcpid;
	//ArrayList<String> logicalSis = new ArrayList<String>();
	
	
	String [] logicalSiList ;
	String logicalSistr;
	String accountstr;
	String dateOfTransfers;

		private String filterLSIList;

	/*This field contain user specific error during SI Transfer process. 
	 * The purpose of this field is to display the error to user if SI Transfer fail. */
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
	public String getFilterLSIList() {
			return filterLSIList;
		}
		public void setFilterLSIList(String filterLSIList) {
			this.filterLSIList = filterLSIList;
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
	
	public String getStatusOfSITransfer() {
		return statusOfSITransfer;
	}
	public void setStatusOfSITransfer(String statusOfSITransfer) {
		this.statusOfSITransfer = statusOfSITransfer;
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
	public String getAccountstr() {
		return accountstr;
	}
	public void setAccountstr(String accountstr) {
		this.accountstr = accountstr;
	}
	public String getBcp_details() {
		return bcp_details;
	}
	public void setBcp_details(String bcp_details) {
		this.bcp_details = bcp_details;
	}
	public String getDispatch_details() {
		return dispatch_details;
	}
	public void setDispatch_details(String dispatch_details) {
		this.dispatch_details = dispatch_details;
	}
	public int getHardwareAllowed() {
		return hardwareAllowed;
	}
	public void setHardwareAllowed(int hardwareAllowed) {
		this.hardwareAllowed = hardwareAllowed;
	}
	public String getCktid() {
		return cktid;
	}
	public void setCktid(String cktid) {
		this.cktid = cktid;
	}
	public String getDateOfTransfers() {
		return dateOfTransfers;
	}
	public void setDateOfTransfers(String dateOfTransfers) {
		this.dateOfTransfers = dateOfTransfers;
	}
	public String getBcpid() {
		return bcpid;
	}
	public void setBcpid(String bcpid) {
		this.bcpid = bcpid;
	}
	public String getSourceModule() {
		return sourceModule;
	}
	public void setSourceModule(String sourceModule) {
		this.sourceModule = sourceModule;
	}
	
	
	
}
