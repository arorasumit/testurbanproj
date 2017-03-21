package com.ibm.fx.dto;


import java.util.Map;

public class UpdateAccountDto {
	
	private Long  account_id = null;
	
	private String acctExternalId = null;
	
	private String partyName = null;
	
	private String regionId = null;
	
	private String accountCaegory = null;
	
	private Long customerSegment = null;
	
	private Long industrySegment = null;
	
	Map extendedData=null;
	
	private String exception_message;	
	
	private Exception exception;
	
	private long id;
	
	private Integer returnStatus=0;
	

	public Integer getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(Integer returnStatus) {
		this.returnStatus = returnStatus;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getException_message() {
		return exception_message;
	}

	public void setException_message(String exception_message) {
		this.exception_message = exception_message;
	}

	

	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Long getAccount_id() {
		return account_id;
	}

	public void setAccount_id(Long account_id) {
		this.account_id = account_id;
	}

	public String getAccountCaegory() {
		return accountCaegory;
	}

	public void setAccountCaegory(String accountCaegory) {
		this.accountCaegory = accountCaegory;
	}

	public String getAcctExternalId() {
		return acctExternalId;
	}

	public void setAcctExternalId(String acctExternalId) {
		this.acctExternalId = acctExternalId;
	}

	public Long getCustomerSegment() {
		return customerSegment;
	}

	public void setCustomerSegment(Long customerSegment) {
		this.customerSegment = customerSegment;
	}

	public Long getIndustrySegment() {
		return industrySegment;
	}

	public void setIndustrySegment(Long industrySegment) {
		this.industrySegment = industrySegment;
	}

	public String getPartyName() {
		return partyName;
	}

	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}

	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	public Map getExtendedData() {
		return extendedData;
	}

	public void setExtendedData(Map extendedData) {
		this.extendedData = extendedData;
	}	

}
