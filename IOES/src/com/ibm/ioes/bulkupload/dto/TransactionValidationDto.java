package com.ibm.ioes.bulkupload.dto;

import com.ibm.ioes.utilities.PagingSorting;

/**
 * @version 	1.0
 * @author 		Sumit Arora & Anil Kumar
 *
 */
/*
 * Tag Name		Resource Name		Date		CSR No			Description
 * [001]	    GUNJAN SINGLA  29-SEPT-2014  CBR_20140704-XX-020224     Global Data Billing Efficiencies Phase1
*/
public class TransactionValidationDto 
{
	private int fileId;
	private int transactionId;
	private int userId;	
	private String strFileName = null;
	private String strSelectedTransactionName = null;
	private String strTransDate = null;
	private String strTransactionName = null;
	private String strFileStatus;
	private int fileStatusId;
	private String filePath;
	private int searchFileStausId;
	private String searchStrFileStatus;	
	private String searchfromDate;
	private String searchToDate;
	private int searchFileID;
	private int selectedTransactionID;
	PagingSorting pagingSorting = new PagingSorting();
	//[001] start
	private String searchUserID;
	//[001] end
	
	public int getFileId() {
		return fileId;
	}
	public void setFileId(int fileId) {
		this.fileId = fileId;
	}
	public String getStrFileName() {
		return strFileName;
	}
	public void setStrFileName(String strFileName) {
		this.strFileName = strFileName;
	}
	public String getStrSelectedTransactionName() {
		return strSelectedTransactionName;
	}
	public void setStrSelectedTransactionName(String strSelectedTransactionName) {
		this.strSelectedTransactionName = strSelectedTransactionName;
	}
	public String getStrTransactionName() {
		return strTransactionName;
	}
	public void setStrTransactionName(String strTransactionName) {
		this.strTransactionName = strTransactionName;
	}
	public String getStrTransDate() {
		return strTransDate;
	}
	public void setStrTransDate(String strTransDate) {
		this.strTransDate = strTransDate;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getStrFileStatus() {
		return strFileStatus;
	}
	public void setStrFileStatus(String strFileStatus) {
		this.strFileStatus = strFileStatus;
	}
	public int getFileStatusId() {
		return fileStatusId;
	}
	public void setFileStatusId(int fileStatusId) {
		this.fileStatusId = fileStatusId;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public int getSearchFileID() {
		return searchFileID;
	}
	public void setSearchFileID(int searchFileID) {
		this.searchFileID = searchFileID;
	}
	public int getSearchFileStausId() {
		return searchFileStausId;
	}
	public void setSearchFileStausId(int searchFileStausId) {
		this.searchFileStausId = searchFileStausId;
	}
	public String getSearchfromDate() {
		return searchfromDate;
	}
	public void setSearchfromDate(String searchfromDate) {
		this.searchfromDate = searchfromDate;
	}
	public String getSearchStrFileStatus() {
		return searchStrFileStatus;
	}
	public void setSearchStrFileStatus(String searchStrFileStatus) {
		this.searchStrFileStatus = searchStrFileStatus;
	}
	public String getSearchToDate() {
		return searchToDate;
	}
	public void setSearchToDate(String searchToDate) {
		this.searchToDate = searchToDate;
	}
	public int getSelectedTransactionID() {
		return selectedTransactionID;
	}
	public void setSelectedTransactionID(int selectedTransactionID) {
		this.selectedTransactionID = selectedTransactionID;
	}
	//[001] start
	public String getSearchUserID() {
		return searchUserID;
	}
	public void setSearchUserID(String searchUserID) {
		this.searchUserID = searchUserID;
	}
	//[001] end
	
}
