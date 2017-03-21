package com.ibm.ioes.forms;

import java.sql.Blob;

public class SharepointDto {
    
	
	private int slno;
	private String fileBodyforXml;
	private String transactionId;
	private String status;
	private String sharepointUrl;
	private String exception;
	private Blob file;
	
	
	
	public Blob getFile() {
		return file;
	}
	public void setFile(Blob file) {
		this.file = file;
	}
	public int getSlno() {
		return slno;
	}
	public void setSlno(int slno) {
		this.slno = slno;
	}
	public String getFileBodyforXml() {
		return fileBodyforXml;
	}
	public void setFileBodyforXml(String fileBodyforXml) {
		this.fileBodyforXml = fileBodyforXml;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSharepointUrl() {
		return sharepointUrl;
	}
	public void setSharepointUrl(String sharepointUrl) {
		this.sharepointUrl = sharepointUrl;
	}
	public String getException() {
		return exception;
	}
	public void setException(String exception) {
		this.exception = exception;
	}

	
}