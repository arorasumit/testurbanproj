package com.ibm.fx.dto;

import java.util.Date;



public class PackageDTO {

	
	
	
	
	
	
	
	
	
	private Long rowId = null;
	
	private String acctExternalId = null;
	private Long   package_inst_id = null;
	private Long   package_inst_id_serv = null;
	private String tokenid    = null;
	private Date packageActiveDate = null;
	private String  packageid      = null;
	
	
	
	

	private Exception exception = null;
	private String exceptionMessage = null;
	private int returnStatus=0;
	private long id;
	private boolean isDataIssueException;
		
	public boolean isDataIssueException() {
			return isDataIssueException;
		}
	public void setDataIssueException(boolean isDataIssueException) {
			this.isDataIssueException = isDataIssueException;
		}

	
	public String getAcctExternalId() {
		return acctExternalId;
	}

	public void setAcctExternalId(String acctExternalId) {
		this.acctExternalId = acctExternalId;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(int returnStatus) {
		this.returnStatus = returnStatus;
	}

	public Long getRowId() {
		return rowId;
	}

	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}

	public Long getPackage_inst_id() {
		return package_inst_id;
	}

	public void setPackage_inst_id(Long package_inst_id) {
		this.package_inst_id = package_inst_id;
	}

	public Long getPackage_inst_id_serv() {
		return package_inst_id_serv;
	}

	public void setPackage_inst_id_serv(Long package_inst_id_serv) {
		this.package_inst_id_serv = package_inst_id_serv;
	}

	public String getTokenid() {
		return tokenid;
	}

	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}

	public Date getPackageActiveDate() {
		return packageActiveDate;
	}

	public void setPackageActiveDate(Date packageActiveDate) {
		this.packageActiveDate = packageActiveDate;
	}

	public String getPackageid() {
		return packageid;
	}

	public void setPackageid(String packageid) {
		this.packageid = packageid;
	}

	
	
	
	
}
