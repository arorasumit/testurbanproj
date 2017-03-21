/*
 * Created on Nov 9, 2010
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.ioes.ecrm;

/**
 * @author Ravish
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ECRMQuoteDto 
{
	String partyId;
	String quoteNo;
	String status;
	String lastUpdatedOn;
	String unionTerritory;
	String leadNo;
	int resourceId;
	String createdDate;
	String salesforceOpportunityNo;
	
	
	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}
	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	public String getQuoteNo() {
		return quoteNo;
	}
	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUnionTerritory() {
		return unionTerritory;
	}
	public void setUnionTerritory(String unionTerritory) {
		this.unionTerritory = unionTerritory;
	}
	public String getLeadNo() {
		return leadNo;
	}
	public void setLeadNo(String leadNo) {
		this.leadNo = leadNo;
	}
	
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public int getResourceId() {
		return resourceId;
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public String getSalesforceOpportunityNo() {
		return salesforceOpportunityNo;
	}
	public void setSalesforceOpportunityNo(String salesforceOpportunityNo) {
		this.salesforceOpportunityNo = salesforceOpportunityNo;
	}
	
	
	
}
