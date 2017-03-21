package com.ibm.ioes.bulkupload.dto;
/**
 * @version 	1.0
 * @author 		Sumit Arora & Anil Kumar
 *
 */
public class TransactionUploadDto 
{
	private int errMsgOrder;
	private int referenceMasterID;
	private int templateID = 0;
	private String errMsgString;
	private String strTransactionID = null;
	private String strTransactionName = null;
	private String referenceMasterName;
	private String strUploadFileName = null;
	private String strCompleteFileName;
	private int fileid;
	
	public int getFileid() {
		return fileid;
	}
	public void setFileid(int fileid) {
		this.fileid = fileid;
	}
	public int getReferenceMasterID() {
		return referenceMasterID;
	}
	public void setReferenceMasterID(int referenceMasterID) {
		this.referenceMasterID = referenceMasterID;
	}
	public String getReferenceMasterName() {
		return referenceMasterName;
	}
	public void setReferenceMasterName(String referenceMasterName) {
		this.referenceMasterName = referenceMasterName;
	}
	
	public String getStrUploadFileName() {
		return strUploadFileName;
	}
	public void setStrUploadFileName(String strUploadFileName) {
		this.strUploadFileName = strUploadFileName;
	}
	public int getTemplateID() {
		return templateID;
	}
	public void setTemplateID(int templateID) {
		this.templateID = templateID;
	}
	public String getStrTransactionID() {
		return strTransactionID;
	}
	public void setStrTransactionID(String strTransactionID) {
		this.strTransactionID = strTransactionID;
	}
	public String getStrTransactionName() {
		return strTransactionName;
	}
	public void setStrTransactionName(String strTransactionName) {
		this.strTransactionName = strTransactionName;
	}
	public int getErrMsgOrder() {
		return errMsgOrder;
	}
	public void setErrMsgOrder(int errMsgOrder) {
		this.errMsgOrder = errMsgOrder;
	}
	public String getErrMsgString() {
		return errMsgString;
	}
	public void setErrMsgString(String errMstString) {
		this.errMsgString = errMstString.toUpperCase();
	}
	public String getStrCompleteFileName() {
		return strCompleteFileName;
	}
	public void setStrCompleteFileName(String strCompleteFileName) {
		this.strCompleteFileName = strCompleteFileName;
	}

}
