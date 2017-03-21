package com.ibm.ioes.newdesign.dto;

public class OpportunityDto{
	private String opportunityId;
	private String quoteNo;
	private String leadNo;
	private int accountId;
	private String salesForceopportunityNo;
	
	
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public String getLeadNo() {
		return leadNo;
	}
	public void setLeadNo(String leadNo) {
		this.leadNo = leadNo;
	}
	public String getOpportunityId() {
		return opportunityId;
	}
	public void setOpportunityId(String opportunityId) {
		this.opportunityId = opportunityId;
	}
	public String getQuoteNo() {
		return quoteNo;
	}
	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}
	public String getSalesForceopportunityNo() {
		return salesForceopportunityNo;
	}
	public void setSalesForceopportunityNo(String salesForceopportunityNo) {
		this.salesForceopportunityNo = salesForceopportunityNo;
	}
}
