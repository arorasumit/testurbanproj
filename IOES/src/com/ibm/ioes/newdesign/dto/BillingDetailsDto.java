package com.ibm.ioes.newdesign.dto;

import java.util.Date;

/**
 * 
 * @author IBM_ADMIN
 *@since June 2014
 */
public class BillingDetailsDto {
	/* Declare all properties related to TBILLING_INFO */
		Long billingInfoId;
		Long poDetailId;
		Long accountId;
		Long creditPeriod;
		Long entityId;
		String billingMode;
		String billingFormat;
		Long licenseCoId;
		String taxation;
		String billingLevel;
		Long commitmentPeriod;
		String penaltyClause;
		Long serviceProductId;
		Long billingType;
		Long bcpId;
		Integer isDisconnected;
		Integer isSuspended;
		Integer isDisconnectedInFx;
		Integer isDisconnectedInM6;
		Integer isSuspendedInFX;
		Integer isSuspendedInM6;
		Long  billingLevelNo;
		Date createdDate1;
		Date modifiedDate1;
		Long createdBy;
		Long modifiedBy;
		Date createdDate2;
		Date modifiedDate2;
		Long noticePeriod;
		Integer isNFA;
		Long latestSelectedPODetailId;
		Long stdReasonId;
		Integer billingScenario;
		Long fxRedirectionLSI;
		Long fxRedirectionSpId;
		Integer isUsage;
		String warrantyClause;
		Long serviceBcpId;
		
		//satyapan OSP Tagging By nagarjuna
		private Integer isOSPTagging;
		private String txtOSPRegNo;
		private Date txtOSPRegDate;
	
		//satyapan OSP Tagging By nagarjuna
	/* Declare all properties related to TBILLING_INFO */
		public Long getBillingInfoId() {
			return billingInfoId;
		}
		public void setBillingInfoId(Long billingInfoId) {
			this.billingInfoId = billingInfoId;
		}
		public Long getPoDetailId() {
			return poDetailId;
		}
		public void setPoDetailId(Long poDetailId) {
			this.poDetailId = poDetailId;
		}
		public Long getAccountId() {
			return accountId;
		}
		public void setAccountId(Long accountId) {
			this.accountId = accountId;
		}
		public Long getCreditPeriod() {
			return creditPeriod;
		}
		public void setCreditPeriod(Long creditPeriod) {
			this.creditPeriod = creditPeriod;
		}
		public Long getEntityId() {
			return entityId;
		}
		public void setEntityId(Long entityId) {
			this.entityId = entityId;
		}
		public String getBillingMode() {
			return billingMode;
		}
		public void setBillingMode(String billingMode) {
			this.billingMode = billingMode;
		}
		public String getBillingFormat() {
			return billingFormat;
		}
		public void setBillingFormat(String billingFormat) {
			this.billingFormat = billingFormat;
		}
		public Long getLicenseCoId() {
			return licenseCoId;
		}
		public void setLicenseCoId(Long licenseCoId) {
			this.licenseCoId = licenseCoId;
		}
		public String getTaxation() {
			return taxation;
		}
		public void setTaxation(String taxation) {
			this.taxation = taxation;
		}
		public String getBillingLevel() {
			return billingLevel;
		}
		public void setBillingLevel(String billingLevel) {
			this.billingLevel = billingLevel;
		}
		public Long getCommitmentPeriod() {
			return commitmentPeriod;
		}
		public void setCommitmentPeriod(Long commitmentPeriod) {
			this.commitmentPeriod = commitmentPeriod;
		}
		public String getPenaltyClause() {
			return penaltyClause;
		}
		public void setPenaltyClause(String penaltyClause) {
			this.penaltyClause = penaltyClause;
		}
		public Long getServiceProductId() {
			return serviceProductId;
		}
		public void setServiceProductId(Long serviceProductId) {
			this.serviceProductId = serviceProductId;
		}
		public Long getBillingType() {
			return billingType;
		}
		public void setBillingType(Long billingType) {
			this.billingType = billingType;
		}
		public Long getBcpId() {
			return bcpId;
		}
		public void setBcpId(Long bcpId) {
			this.bcpId = bcpId;
		}
		public Integer getIsDisconnected() {
			return isDisconnected;
		}
		public void setIsDisconnected(Integer isDisconnected) {
			this.isDisconnected = isDisconnected;
		}
		public Integer getIsSuspended() {
			return isSuspended;
		}
		public void setIsSuspended(Integer isSuspended) {
			this.isSuspended = isSuspended;
		}
		public Integer getIsDisconnectedInFx() {
			return isDisconnectedInFx;
		}
		public void setIsDisconnectedInFx(Integer isDisconnectedInFx) {
			this.isDisconnectedInFx = isDisconnectedInFx;
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
		public Long getBillingLevelNo() {
			return billingLevelNo;
		}
		public void setBillingLevelNo(Long billingLevelNo) {
			this.billingLevelNo = billingLevelNo;
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
		public Long getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(Long createdBy) {
			this.createdBy = createdBy;
		}
		public Long getModifiedBy() {
			return modifiedBy;
		}
		public void setModifiedBy(Long modifiedBy) {
			this.modifiedBy = modifiedBy;
		}
		public Date getCreatedDate2() {
			return createdDate2;
		}
		public void setCreatedDate2(Date createdDate2) {
			this.createdDate2 = createdDate2;
		}
		public Date getModifiedDate2() {
			return modifiedDate2;
		}
		public void setModifiedDate2(Date modifiedDate2) {
			this.modifiedDate2 = modifiedDate2;
		}
		public Long getNoticePeriod() {
			return noticePeriod;
		}
		public void setNoticePeriod(Long noticePeriod) {
			this.noticePeriod = noticePeriod;
		}
		public Integer getIsNFA() {
			return isNFA;
		}
		public void setIsNFA(Integer isNFA) {
			this.isNFA = isNFA;
		}
		public Long getLatestSelectedPODetailId() {
			return latestSelectedPODetailId;
		}
		public void setLatestSelectedPODetailId(Long latestSelectedPODetailId) {
			this.latestSelectedPODetailId = latestSelectedPODetailId;
		}
		public Long getStdReasonId() {
			return stdReasonId;
		}
		public void setStdReasonId(Long stdReasonId) {
			this.stdReasonId = stdReasonId;
		}
		public Integer getBillingScenario() {
			return billingScenario;
		}
		public void setBillingScenario(Integer billingScenario) {
			this.billingScenario = billingScenario;
		}
		public Long getFxRedirectionLSI() {
			return fxRedirectionLSI;
		}
		public void setFxRedirectionLSI(Long fxRedirectionLSI) {
			this.fxRedirectionLSI = fxRedirectionLSI;
		}
		public Long getFxRedirectionSpId() {
			return fxRedirectionSpId;
		}
		public void setFxRedirectionSpId(Long fxRedirectionSpId) {
			this.fxRedirectionSpId = fxRedirectionSpId;
		}
		public Integer getIsUsage() {
			return isUsage;
		}
		public void setIsUsage(Integer isUsage) {
			this.isUsage = isUsage;
		}
		public String getWarrantyClause() {
			return warrantyClause;
		}
		public void setWarrantyClause(String warrantyClause) {
			this.warrantyClause = warrantyClause;
		}
		public Long getServiceBcpId() {
			return serviceBcpId;
		}
		public void setServiceBcpId(Long serviceBcpId) {
			this.serviceBcpId = serviceBcpId;
		}
		public Integer getIsOSPTagging() {
			return isOSPTagging;
		}
		public void setIsOSPTagging(Integer isOSPTagging) {
			this.isOSPTagging = isOSPTagging;
		}
		
		public String getTxtOSPRegNo() {
			return txtOSPRegNo;
		}
		public void setTxtOSPRegNo(String txtOSPRegNo) {
			this.txtOSPRegNo = txtOSPRegNo;
		}
		
		public Date getTxtOSPRegDate() {
			return txtOSPRegDate;
		}
		public void setTxtOSPRegDate(Date txtOSPRegDate) {
			this.txtOSPRegDate = txtOSPRegDate;
		}
		
		
		
}
