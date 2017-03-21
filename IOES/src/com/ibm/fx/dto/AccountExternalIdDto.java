package com.ibm.fx.dto;

public class AccountExternalIdDto {

	private String accountExternalId = null;
	private Short accountExternalIdType = null;
	private Integer accountInternalId = null;
	private Boolean isCurrent = null;
	
	public String getAccountExternalId() {
		return accountExternalId;
	}
	public void setAccountExternalId(String accountExternalId) {
		this.accountExternalId = accountExternalId;
	}
	public Short getAccountExternalIdType() {
		return accountExternalIdType;
	}
	public void setAccountExternalIdType(Short accountExternalIdType) {
		this.accountExternalIdType = accountExternalIdType;
	}
	public Integer getAccountInternalId() {
		return accountInternalId;
	}
	public void setAccountInternalId(Integer accountInternalId) {
		this.accountInternalId = accountInternalId;
	}
	public Boolean getIsCurrent() {
		return isCurrent;
	}
	public void setIsCurrent(Boolean isCurrent) {
		this.isCurrent = isCurrent;
	}
}
