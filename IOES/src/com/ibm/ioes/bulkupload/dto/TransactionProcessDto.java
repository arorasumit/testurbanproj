package com.ibm.ioes.bulkupload.dto;
/**
 * @version 	1.0
 * @author 		Sumit Arora & Anil Kumar
 *
 */
public class TransactionProcessDto 
{
	private int fileId;
	private int transactionId;
	private String strFileName = null;
	private String strSelectedTransactionName = null;
	private String strTransDate = null;
	private String strTransactionName = null;

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
	public String getStrTransactionName() {
		return strTransactionName;
	}
	public void setStrTransactionName(String strTransactionName) {
		this.strTransactionName = strTransactionName;
	}
	public String getStrSelectedTransactionName() {
		return strSelectedTransactionName;
	}
	public void setStrSelectedTransactionName(String strSelectedTransactionName) {
		this.strSelectedTransactionName = strSelectedTransactionName;
	}
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

	
}
