package com.ibm.ioes.newdesign.dto;

import java.util.Date;

/**
 * 
 * @author IBM_ADMIN
 *@since June 2014
 */
public class LocationDetailsDto {

	/* Declare all properties related to TLOCATION_INFO */
	Long locationId;
	Long primaryLocationType;
	Long primaryLocationId;
	Long secondaryLocationType;
	Long secondaryLocationId;
	Long accountId;
	Long serviceProductId;
	Integer isDisconnected;
	Integer issuspended;
	Integer isDisconnectedInFX;
	Integer isDisconnectedInM6;
	Integer isSuspendedInFX;
	Integer isSuspendedInM6;
	Date createdDate1;
	Date modifiedDate1;
	String toAddress;
	String fromAddress;
	Date createdDate2;
	Long createdBy;
	Date modifiedDate2;
	Long modifiedBy;
	/* Declare all properties related to TLOCATION_INFO */
	public Long getLocationId() {
		return locationId;
	}
	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}
	public Long getPrimaryLocationType() {
		return primaryLocationType;
	}
	public void setPrimaryLocationType(Long primaryLocationType) {
		this.primaryLocationType = primaryLocationType;
	}
	public Long getPrimaryLocationId() {
		return primaryLocationId;
	}
	public void setPrimaryLocationId(Long primaryLocationId) {
		this.primaryLocationId = primaryLocationId;
	}
	public Long getSecondaryLocationType() {
		return secondaryLocationType;
	}
	public void setSecondaryLocationType(Long secondaryLocationType) {
		this.secondaryLocationType = secondaryLocationType;
	}
	public Long getSecondaryLocationId() {
		return secondaryLocationId;
	}
	public void setSecondaryLocationId(Long secondaryLocationId) {
		this.secondaryLocationId = secondaryLocationId;
	}
	public Long getAccountId() {
		return accountId;
	}
	public void setAccountId(Long accountId) {
		this.accountId = accountId;
	}
	public Long getServiceProductId() {
		return serviceProductId;
	}
	public void setServiceProductId(Long serviceProductId) {
		this.serviceProductId = serviceProductId;
	}
	public Integer getIsDisconnected() {
		return isDisconnected;
	}
	public void setIsDisconnected(Integer isDisconnected) {
		this.isDisconnected = isDisconnected;
	}
	public Integer getIssuspended() {
		return issuspended;
	}
	public void setIssuspended(Integer issuspended) {
		this.issuspended = issuspended;
	}
	public Integer getIsDisconnectedInFX() {
		return isDisconnectedInFX;
	}
	public void setIsDisconnectedInFX(Integer isDisconnectedInFX) {
		this.isDisconnectedInFX = isDisconnectedInFX;
	}
	public Integer getIsDisconnectedInM6() {
		return isDisconnectedInM6;
	}
	public void setIsDisconnectedInM6(Integer isDisconnectedInM6) {
		this.isDisconnectedInM6 = isDisconnectedInM6;
	}
	public Integer getIsSuspendedInFX() {
		return isSuspendedInFX;
	}
	public void setIsSuspendedInFX(Integer isSuspendedInFX) {
		this.isSuspendedInFX = isSuspendedInFX;
	}
	public Integer getIsSuspendedInM6() {
		return isSuspendedInM6;
	}
	public void setIsSuspendedInM6(Integer isSuspendedInM6) {
		this.isSuspendedInM6 = isSuspendedInM6;
	}
	public Date getCreatedDate1() {
		return createdDate1;
	}
	public void setCreatedDate1(Date createdDate1) {
		this.createdDate1 = createdDate1;
	}
	public Date getModifiedDate1() {
		return modifiedDate1;
	}
	public void setModifiedDate1(Date modifiedDate1) {
		this.modifiedDate1 = modifiedDate1;
	}
	public String getToAddress() {
		return toAddress;
	}
	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}
	public String getFromAddress() {
		return fromAddress;
	}
	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}
	public Date getCreatedDate2() {
		return createdDate2;
	}
	public void setCreatedDate2(Date createdDate2) {
		this.createdDate2 = createdDate2;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getModifiedDate2() {
		return modifiedDate2;
	}
	public void setModifiedDate2(Date modifiedDate2) {
		this.modifiedDate2 = modifiedDate2;
	}
	public Long getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	
}
