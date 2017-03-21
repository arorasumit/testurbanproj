package com.ibm.ioes.beans;

import java.util.ArrayList;

public class SITransferBean extends NewOrderBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8650546858327820806L;

        private ArrayList siTransferDetails=null;
        private ArrayList siTransferBatchDetails=null;
		private String sourcePartyName;
		private String targetPartyName;
		private int sourcePartyNo;
		private int targetPartyNo;
		private String accountno;
		private String spid;
		private String productname;
		private int bcpid;
		private int dispatchid;
		private String accountcreation;
		private String neworderno;
		private String bdisconorderno;
		private String remarks;
		private String transferdate;
		private String error;
		private String searchBatchId;
		private String dateOfTransfer;
		private String statusOfSITransfer;
		private int batchID;
		private ArrayList siTransferList=null;
		private ArrayList logicalSIDetails=null;
		private String searchLogicalSI;
		private String progress;
		ArrayList logicalSis = new ArrayList();
		ArrayList serviceNames = new ArrayList();
		ArrayList serviceIds = new ArrayList();
		ArrayList accountNos = new ArrayList();
		ArrayList shortCodes = new ArrayList();
		private String shortcode;
		
		private String m6ShortCode;
		private String accountnoString;
		
		public String getAccountnoString() {
			return accountnoString;
		}
		public void setAccountnoString(String accountnoString) {
			this.accountnoString = accountnoString;
		}
		public String getM6ShortCode() {
			return m6ShortCode;
		}
		public void setM6ShortCode(String shortCode) {
			m6ShortCode = shortCode;
		}
		public String getShortcode() {
			return shortcode;
		}
		public void setShortcode(String shortcode) {
			this.shortcode = shortcode;
		}
		public String getSourcePartyName() {
			return sourcePartyName;
		}
		public void setSourcePartyName(String sourcePartyName) {
			this.sourcePartyName = sourcePartyName;
		}
		public String getTargetPartyName() {
			return targetPartyName;
		}
		public void setTargetPartyName(String targetPartyName) {
			this.targetPartyName = targetPartyName;
		}
		public int getSourcePartyNo() {
			return sourcePartyNo;
		}
		public void setSourcePartyNo(int sourcePartyNo) {
			this.sourcePartyNo = sourcePartyNo;
		}
		public int getTargetPartyNo() {
			return targetPartyNo;
		}
		public void setTargetPartyNo(int targetPartyNo) {
			this.targetPartyNo = targetPartyNo;
		}
		public ArrayList getAccountNos() {
			return accountNos;
		}
		public void setAccountNos(ArrayList accountNos) {
			this.accountNos = accountNos;
		}
		public ArrayList getLogicalSis() {
			return logicalSis;
		}
		public void setLogicalSis(ArrayList logicalSis) {
			this.logicalSis = logicalSis;
		}
		public ArrayList getServiceIds() {
			return serviceIds;
		}
		public void setServiceIds(ArrayList serviceIds) {
			this.serviceIds = serviceIds;
		}
		public ArrayList getServiceNames() {
			return serviceNames;
		}
		public void setServiceNames(ArrayList serviceNames) {
			this.serviceNames = serviceNames;
		}
		public ArrayList getShortCodes() {
			return shortCodes;
		}
		public void setShortCodes(ArrayList shortCodes) {
			this.shortCodes = shortCodes;
		}
		
		public String getAccountcreation() {
			return accountcreation;
		}
		public void setAccountcreation(String accountcreation) {
			this.accountcreation = accountcreation;
		}
		public String getAccountno() {
			return accountno;
		}
		public void setAccountno(String accountno) {
			this.accountno = accountno;
		}
		public String getBdisconorderno() {
			return bdisconorderno;
		}
		public void setBdisconorderno(String bdisconorderno) {
			this.bdisconorderno = bdisconorderno;
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
		public String getTransferdate() {
			return transferdate;
		}
		public void setTransferdate(String transferdate) {
			this.transferdate = transferdate;
		}
		public int getBatchID() {
			return batchID;
		}
		public void setBatchID(int batchID) {
			this.batchID = batchID;
		}
		public String getDateOfTransfer() {
			return dateOfTransfer;
		}
		public void setDateOfTransfer(String dateOfTransfer) {
			this.dateOfTransfer = dateOfTransfer;
		}
		public String getStatus() {
			return statusOfSITransfer;
		}
		public void setStatus(String statusOfSITransfer) {
			this.statusOfSITransfer = statusOfSITransfer;
		}
		public String getSearchBatchId() {
			return searchBatchId;
		}
		public void setSearchBatchId(String searchBatchId) {
			this.searchBatchId = searchBatchId;
		}
		public ArrayList getSiTransferList() {
			return siTransferList;
		}
		public void setSiTransferList(ArrayList siTransferList) {
			this.siTransferList = siTransferList;
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
		public ArrayList getSiTransferDetails() {
			return siTransferDetails;
		}
		public void setSiTransferDetails(ArrayList siTransferDetails) {
			this.siTransferDetails = siTransferDetails;
		}
		public ArrayList getSiTransferBatchDetails() {
			return siTransferBatchDetails;
		}
		public void setSiTransferBatchDetails(ArrayList siTransferBatchDetails) {
			this.siTransferBatchDetails = siTransferBatchDetails;
		}
		public ArrayList getLogicalSIDetails() {
			return logicalSIDetails;
		}
		public void setLogicalSIDetails(ArrayList logicalSIDetails) {
			this.logicalSIDetails = logicalSIDetails;
		}
		public int getBcpid() {
			return bcpid;
		}
		public void setBcpid(int bcpid) {
			this.bcpid = bcpid;
		}
		public int getDispatchid() {
			return dispatchid;
		}
		public void setDispatchid(int dispatchid) {
			this.dispatchid = dispatchid;
		}
		public String getProductname() {
			return productname;
		}
		public void setProductname(String productname) {
			this.productname = productname;
		}
		public String getSpid() {
			return spid;
		}
		public void setSpid(String spid) {
			this.spid = spid;
		}
		
}
