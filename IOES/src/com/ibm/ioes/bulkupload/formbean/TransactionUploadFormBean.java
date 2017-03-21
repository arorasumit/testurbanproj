package com.ibm.ioes.bulkupload.formbean;

import org.apache.struts.action.ActionForm;
import org.apache.struts.upload.FormFile;
import java.util.ArrayList;

/**
 * @version 	1.0
 * @author		Sumit Arora
 */
public class TransactionUploadFormBean extends ActionForm
{

	private static final long serialVersionUID = 1L;
	private int checkStatus;
	private int processStatus=-1;
	private FormFile newFile;
	private String errorMessage=null;
	private String fileID;
	private String filePath;
	private String fileToDownload;
	private String message=null;
	private String methodName;
	private String method;
	private String selectedReferenceId;
	private String fileLink=null;	
	private String templateId;
	private String transactionName;
	private String transactionTemplate;
	private String transactionType;
	private ArrayList masterReferenceList;
	private ArrayList msgList=null;
	private ArrayList transactionList;
	private ArrayList parameterDataList;
	private String optionValue;
	private String lsiList;
	private int flagParameter;
	private int hdnViewLinkValue;
	private String subChangeTypeId;
	private String fromDate;
	private String toDate;
	private String interfaceId = null;
	public String getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
	private String isDumpAvailable = null;
	private String dumpFileName = null;
	public String getIsDumpAvailable() {
		return isDumpAvailable;
	}
	public void setIsDumpAvailable(String isDumpAvailable) {
		this.isDumpAvailable = isDumpAvailable;
	}
	public String getDumpFileName() {
		return dumpFileName;
	}
	public void setDumpFileName(String dumpFileName) {
		this.dumpFileName = dumpFileName;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	public int getHdnViewLinkValue() {
		return hdnViewLinkValue;
	}
	public void setHdnViewLinkValue(int hdnViewLinkValue) {
		this.hdnViewLinkValue = hdnViewLinkValue;
	}
	public String getOptionValue() {
		return optionValue;
	}
	public void setOptionValue(String optionValue) {
		this.optionValue = optionValue;
	}
	public ArrayList getParameterDataList() {
		return parameterDataList;
	}
	public void setParameterDataList(ArrayList parameterDataList) {
		this.parameterDataList = parameterDataList;
	}
	public int getCheckStatus() {
		return checkStatus;
	}
	public void setCheckStatus(int checkStatus) {
		this.checkStatus = checkStatus;
	}
	public int getProcessStatus() {
		return processStatus;
	}
	public void setProcessStatus(int processStatus) {
		this.processStatus = processStatus;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public ArrayList getMasterReferenceList() {
		return masterReferenceList;
	}
	public void setMasterReferenceList(ArrayList masterReferenceList) {
		this.masterReferenceList = masterReferenceList;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public FormFile getNewFile() {
		return newFile;
	}
	public void setNewFile(FormFile newFile) {
		this.newFile = newFile;
	}
	public String getSelectedReferenceId() {
		return selectedReferenceId;
	}
	public void setSelectedReferenceId(String selectedReferenceId) {
		this.selectedReferenceId = selectedReferenceId;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public ArrayList getTransactionList() {
		return transactionList;
	}
	public void setTransactionList(ArrayList transactionList) {
		this.transactionList = transactionList;
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
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getFileID() {
		return fileID;
	}
	public void setFileID(String fileID) {
		this.fileID = fileID;
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
	public String getFileToDownload() {
		return fileToDownload;
	}
	public void setFileToDownload(String fileToDownload) {
		this.fileToDownload = fileToDownload;
	}
	public ArrayList getMsgList() {
		return msgList;
	}
	public void setMsgList(ArrayList msgList) {
		this.msgList = msgList;
	}
	public String getFileLink() {
		return fileLink;
	}
	public void setFileLink(String fileLink) {
		this.fileLink = fileLink;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getLsiList() {
		return lsiList;
	}
	public void setLsiList(String lsiList) {
		this.lsiList = lsiList;
	}
	public int getFlagParameter() {
		return flagParameter;
	}
	public void setFlagParameter(int flagParameter) {
		this.flagParameter = flagParameter;
	}
	public String getSubChangeTypeId() {
		return subChangeTypeId;
	}
	public void setSubChangeTypeId(String subChangeTypeId) {
		this.subChangeTypeId = subChangeTypeId;
	}
	
}
