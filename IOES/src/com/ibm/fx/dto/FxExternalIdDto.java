package com.ibm.fx.dto;

public class FxExternalIdDto {

	private String externalId = null;
	private Short externalIdType = null;
	private Integer accountInternalId = null;
	private Boolean isCurrent = null;
	public final static String AssociatedWith_SERVICE = "SERVICE";
	public final static String AssociatedWith_SERVICEUPDATE = "SERVICEUPDATE";	
	
	
	public Integer getAccountInternalId() {
		return accountInternalId;
	}
	public void setAccountInternalId(Integer accountInternalId) {
		this.accountInternalId = accountInternalId;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public Short getExternalIdType() {
		return externalIdType;
	}
	public void setExternalIdType(Short externalIdType) {
		this.externalIdType = externalIdType;
	}
	public Boolean getIsCurrent() {
		return isCurrent;
	}
	public void setIsCurrent(Boolean isCurrent) {
		this.isCurrent = isCurrent;
	}
	
	
}
