package com.ibm.ioes.bulkupload.formbean;

import java.util.ArrayList;
import org.apache.struts.action.ActionForm;

import com.ibm.ioes.utilities.PagingSorting;
/**
 * @version 	1.0
 * @author 		Anil Kumar
 *
 */
/*
 * Tag Name     Resource Name      Date		        CSR No			        Description 
 * [001]        Gunjan Singla   29-Sept-2014  CBR_20140704-XX-020224     Global Data Billing Efficiencies Phase1   
 * */


public class TransactionValidationFormBean extends ActionForm
{
	private static final long serialVersionUID = 1L;
	private int selectedTransactionId;
	private int transactionId;
	private int validationStatus=0;
	private int fileFoundStatus=0;
	private String fileId;
	private String filePath;
	private String errorMessage=null;
	private String fileLink=null;	
	private String fileToValidate;
	private String fileToProcess;
	private String methodName;
    private String message=null;
    private String userId = null;
    private ArrayList transactionList = null;
	private ArrayList processDetails = null;
    private ArrayList transFileList = null;
    private int transactionIdSelected;
    private String strFileStatus;
    private int fileStatusId;
    private String fileToDownload;
    private int processStatus;
    
	private String transactionName;
	private String transactionTemplate;	
	private String templateId;
	private String trRowID;
	private int validationFlag;
	private String searchFileStatus;
    private String searchfromDate;
    private String searchToDate;
    private int searchFileID;
    //[001] start
    private String searchUserID;
    //[001] end
    PagingSorting pagingSorting = new PagingSorting();
    public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTransactionName() {
		return transactionName;
	}
	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}
	public String getTransactionTemplate() {
		return transactionTemplate;
	}
	public void setTransactionTemplate(String transactionTemplate) {
		this.transactionTemplate = transactionTemplate;
	}
	public String getFileToValidate() {
		return fileToValidate;
	}
	public void setFileToValidate(String fileToValidate) {
		this.fileToValidate = fileToValidate;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public ArrayList getProcessDetails() {
		return processDetails;
	}
	public void setProcessDetails(ArrayList processDetails) {
		this.processDetails = processDetails;
	}
	public int getSelectedTransactionId() {
		return selectedTransactionId;
	}
	public void setSelectedTransactionId(int selectedTransactionId) {
		this.selectedTransactionId = selectedTransactionId;
	}
	public int getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}
	public ArrayList getTransactionList() {
		return transactionList;
	}
	public void setTransactionList(ArrayList transactionList) {
		this.transactionList = transactionList;
	}
	public ArrayList getTransFileList() {
		return transFileList;
	}
	public void setTransFileList(ArrayList transFileList) {
		this.transFileList = transFileList;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage.toUpperCase();
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message.toUpperCase();
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public int getValidationStatus() {
		return validationStatus;
	}
	public void setValidationStatus(int validationStatus) {
		this.validationStatus = validationStatus;
	}
	public String getFileLink() {
		return fileLink;
	}
	public void setFileLink(String fileLink) {
		this.fileLink = fileLink;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public int getTransactionIdSelected() {
		return transactionIdSelected;
	}
	public void setTransactionIdSelected(int transactionIdSelected) {
		this.transactionIdSelected = transactionIdSelected;
	}
	public String getFileToProcess() {
		return fileToProcess;
	}
	public void setFileToProcess(String fileToProcess) {
		this.fileToProcess = fileToProcess;
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
	public String getFileToDownload() {
		return fileToDownload;
	}
	public void setFileToDownload(String fileToDownload) {
		this.fileToDownload = fileToDownload;
	}
	public int getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
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
	public String getSearchFileStatus() {
		return searchFileStatus;
	}
	public void setSearchFileStatus(String searchFileStatus) {
		this.searchFileStatus = searchFileStatus;
	}
	public String getSearchfromDate() {
		return searchfromDate;
	}
	public void setSearchfromDate(String searchfromDate) {
		this.searchfromDate = searchfromDate;
	}
	public String getSearchToDate() {
		return searchToDate;
	}
	public void setSearchToDate(String searchToDate) {
		this.searchToDate = searchToDate;
	}
	public int getFileFoundStatus() {
		return fileFoundStatus;
	}
	public void setFileFoundStatus(int fileFoundStatus) {
		this.fileFoundStatus = fileFoundStatus;
	}
	public String getTrRowID() {
		return trRowID;
	}
	public void setTrRowID(String trRowID) {
		this.trRowID = trRowID;
	}
	public int getValidationFlag() {
		return validationFlag;
	}
	public void setValidationFlag(int validationFlag) {
		this.validationFlag = validationFlag;
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
