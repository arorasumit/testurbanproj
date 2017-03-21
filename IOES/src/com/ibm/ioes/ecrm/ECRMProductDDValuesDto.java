package com.ibm.ioes.ecrm;

public class ECRMProductDDValuesDto {
	
	private String text;
	private String lookUpCode;
	private String creation_Date;
	private String last_Update_date;
	private String productName;
	private String enabledFlag;
	private String partyId;
	private String opportunityid;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCreation_Date() {
		return creation_Date;
	}
	public void setCreation_Date(String creation_Date) {
		this.creation_Date = creation_Date;
	}
	public String getLast_Update_date() {
		return last_Update_date;
	}
	public void setLast_Update_date(String last_Update_date) {
		this.last_Update_date = last_Update_date;
	}
	public String getLookUpCode() {
		return lookUpCode;
	}
	public void setLookUpCode(String lookUpCode) {
		this.lookUpCode = lookUpCode;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	public String getOpportunityid() {
		return opportunityid;
	}
	public void setOpportunityid(String opportunityid) {
		this.opportunityid = opportunityid;
	}
	public String getPartyId() {
		return partyId;
	}
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	
}
