package com.ibm.ioes.newdesign.dto;

import java.util.Date;
import java.util.List;

import com.ibm.ioes.utilities.PagingSorting;

/**
 * 
 * @author IBM_ADMIN
 *@since June 2014
 */
public class ServiceDto {
	/* Declare All attributes related to TPOSERVICEMASTER */
	Long serviceId;
	Long servicetypeId;
	String serviceStage;
	Date effectiveStartDate;
	String custLogicalSiNo;
	Date effectiveEndDate;
	String provisionPlan;
	String remarks;
	Long orderNo;
	Long serviceSubtypeId;
	String m6OrderNo;
	String preOrderNo;
	String oldCustLogicalSiNo;
	Long oldServiceId;
	String m6EvenetTypeId;
	String m6Reason;
	Long serviceCounter;
	Long changeType;
	Long logical_si_no;
	Long customer_logical_si_no;
	Date createdDate;
	Date modifiedDate;
	Long createdBy;
	Long modifiedBy;
	Long isDisconnected;
	Long isSuspended;
	Long isChangedLsi;
	Long subChangeTypeId;
	Long m6FXProgresStatus;
	Integer billingTriggerEndMeet;
	Long siTransfered;
	Long isPublish;
	Long lsiCurrencyChanged;
	Date rfsDate;
	Long productId;
	Long subProductId;
	Integer isInHistory;
	Long isServiceInactive;
	Integer publishIndex;
	String cancelServiceReason;
	Long publishByEmpId;
	String bundleType;	
	Long publishedByRoleId;
	Date publishedDate;
	String cktPushedIntoCRM;
	String bundled;
	String cancelledOrderServiceRef;
	Integer isDemo;
	Integer parallelUpgrageLSINo;
	String downtimeClause;
	Date demoStartDate;
	Date demoEndDate;
	Long demoMailSent;
	String oldLogicalSINo;
	Long initiatedTo;
	Integer publishReady;
	Integer cancellationReasonId;
	Integer isSaasBundle;
	Integer isServiceValidated;
	Long accountId;
	
	//Shubhranshu
	String effectiveDate;
	String valChangeReason;
	String lblChangeReason;
	//Shubhranshu,DROP&CARRY
	String sqlMsg;
	String sqlMsgCode;
/*	String autoType;
	Integer bt_Status;
	String m6_Fx_Progress_Status;
	String autoBillingStatus;
	String statusLocUpdateAtBT;
	String receivedLocNo;
	String receivedLocDate;
	String receivedLoc_Rec_Date;
	Long  serviceProductId;
	String locDate;
	String locNo;
	String locReceivedDate;*/
		//Shubhranshu
		
	/* Declare All attributes related to TPOSERVICEMASTER */
	
		//Shubhranshu,Drop&Carry, TPOSERVICEMASTER_EXTENDED
			private String serviceFlavor;
			private String circuitType;
			private String headEndCode;

			PagingSorting pagingSorting = new PagingSorting();	 //added by Shubhranshu
			private int maxPageNo;
		//
	
	public String getLblChangeReason() {
		return lblChangeReason;
	}
	public void setLblChangeReason(String lblChangeReason) {
		this.lblChangeReason = lblChangeReason;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
		public String getValChangeReason() {
		return valChangeReason;
	}
	public void setValChangeReason(String valChangeReason) {
		this.valChangeReason = valChangeReason;
	}
	/* Declare other attributes which associated with service*/
		List<ServiceProductAttributeDto> lsServiceProdAtt; 
		List<LineItemDto> lsLineItemDetails;
	/* Declare other attributes which associated with service*/
		
		public Long getServiceId() {
			return serviceId;
		}
		public void setServiceId(Long serviceId) {
			this.serviceId = serviceId;
		}
		public Long getServicetypeId() {
			return servicetypeId;
		}
		public void setServicetypeId(Long servicetypeId) {
			this.servicetypeId = servicetypeId;
		}
		public String getServiceStage() {
			return serviceStage;
		}
		public void setServiceStage(String serviceStage) {
			this.serviceStage = serviceStage;
		}
		public Date getEffectiveStartDate() {
			return effectiveStartDate;
		}
		public void setEffectiveStartDate(Date effectiveStartDate) {
			this.effectiveStartDate = effectiveStartDate;
		}
		public String getCustLogicalSiNo() {
			return custLogicalSiNo;
		}
		public void setCustLogicalSiNo(String custLogicalSiNo) {
			this.custLogicalSiNo = custLogicalSiNo;
		}
		public Date getEffectiveEndDate() {
			return effectiveEndDate;
		}
		public void setEffectiveEndDate(Date effectiveEndDate) {
			this.effectiveEndDate = effectiveEndDate;
		}
		public String getProvisionPlan() {
			return provisionPlan;
		}
		public void setProvisionPlan(String provisionPlan) {
			this.provisionPlan = provisionPlan;
		}
		public String getRemarks() {
			return remarks;
		}
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		public Long getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(Long orderNo) {
			this.orderNo = orderNo;
		}
		public Long getServiceSubtypeId() {
			return serviceSubtypeId;
		}
		public void setServiceSubtypeId(Long serviceSubtypeId) {
			this.serviceSubtypeId = serviceSubtypeId;
		}
		public String getM6OrderNo() {
			return m6OrderNo;
		}
		public void setM6OrderNo(String m6OrderNo) {
			this.m6OrderNo = m6OrderNo;
		}
		public String getPreOrderNo() {
			return preOrderNo;
		}
		public void setPreOrderNo(String preOrderNo) {
			this.preOrderNo = preOrderNo;
		}
		public String getOldCustLogicalSiNo() {
			return oldCustLogicalSiNo;
		}
		public void setOldCustLogicalSiNo(String oldCustLogicalSiNo) {
			this.oldCustLogicalSiNo = oldCustLogicalSiNo;
		}
		public Long getOldServiceId() {
			return oldServiceId;
		}
		public void setOldServiceId(Long oldServiceId) {
			this.oldServiceId = oldServiceId;
		}
		public String getM6EvenetTypeId() {
			return m6EvenetTypeId;
		}
		public void setM6EvenetTypeId(String m6EvenetTypeId) {
			this.m6EvenetTypeId = m6EvenetTypeId;
		}
		public String getM6Reason() {
			return m6Reason;
		}
		public void setM6Reason(String m6Reason) {
			this.m6Reason = m6Reason;
		}
		public Long getServiceCounter() {
			return serviceCounter;
		}
		public void setServiceCounter(Long serviceCounter) {
			this.serviceCounter = serviceCounter;
		}
		public Long getChangeType() {
			return changeType;
		}
		public void setChangeType(Long changeType) {
			this.changeType = changeType;
		}
		public Long getLogical_si_no() {
			return logical_si_no;
		}
		public void setLogical_si_no(Long logical_si_no) {
			this.logical_si_no = logical_si_no;
		}
		public Long getCustomer_logical_si_no() {
			return customer_logical_si_no;
		}
		public void setCustomer_logical_si_no(Long customer_logical_si_no) {
			this.customer_logical_si_no = customer_logical_si_no;
		}
		public Date getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		public Date getModifiedDate() {
			return modifiedDate;
		}
		public void setModifiedDate(Date modifiedDate) {
			this.modifiedDate = modifiedDate;
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
		public Long getIsDisconnected() {
			return isDisconnected;
		}
		public void setIsDisconnected(Long isDisconnected) {
			this.isDisconnected = isDisconnected;
		}
		public Long getIsSuspended() {
			return isSuspended;
		}
		public void setIsSuspended(Long isSuspended) {
			this.isSuspended = isSuspended;
		}
		public Long getIsChangedLsi() {
			return isChangedLsi;
		}
		public void setIsChangedLsi(Long isChangedLsi) {
			this.isChangedLsi = isChangedLsi;
		}
		public Long getSubChangeTypeId() {
			return subChangeTypeId;
		}
		public void setSubChangeTypeId(Long subChangeTypeId) {
			this.subChangeTypeId = subChangeTypeId;
		}
		public Long getM6FXProgresStatus() {
			return m6FXProgresStatus;
		}
		public void setM6FXProgresStatus(Long m6fxProgresStatus) {
			m6FXProgresStatus = m6fxProgresStatus;
		}
		public Integer getBillingTriggerEndMeet() {
			return billingTriggerEndMeet;
		}
		public void setBillingTriggerEndMeet(Integer billingTriggerEndMeet) {
			this.billingTriggerEndMeet = billingTriggerEndMeet;
		}
		public Long getSiTransfered() {
			return siTransfered;
		}
		public void setSiTransfered(Long siTransfered) {
			this.siTransfered = siTransfered;
		}
		public Long getIsPublish() {
			return isPublish;
		}
		public void setIsPublish(Long isPublish) {
			this.isPublish = isPublish;
		}
		public Long getLsiCurrencyChanged() {
			return lsiCurrencyChanged;
		}
		public void setLsiCurrencyChanged(Long lsiCurrencyChanged) {
			this.lsiCurrencyChanged = lsiCurrencyChanged;
		}
		public Date getRfsDate() {
			return rfsDate;
		}
		public void setRfsDate(Date rfsDate) {
			this.rfsDate = rfsDate;
		}
		public Long getProductId() {
			return productId;
		}
		public void setProductId(Long productId) {
			this.productId = productId;
		}
		public Long getSubProductId() {
			return subProductId;
		}
		public void setSubProductId(Long subProductId) {
			this.subProductId = subProductId;
		}
		public Integer getIsInHistory() {
			return isInHistory;
		}
		public void setIsInHistory(Integer isInHistory) {
			this.isInHistory = isInHistory;
		}
		public Long getIsServiceInactive() {
			return isServiceInactive;
		}
		public void setIsServiceInactive(Long isServiceInactive) {
			this.isServiceInactive = isServiceInactive;
		}
		public Integer getPublishIndex() {
			return publishIndex;
		}
		public void setPublishIndex(Integer publishIndex) {
			this.publishIndex = publishIndex;
		}
		public String getCancelServiceReason() {
			return cancelServiceReason;
		}
		public void setCancelServiceReason(String cancelServiceReason) {
			this.cancelServiceReason = cancelServiceReason;
		}
		public Long getPublishByEmpId() {
			return publishByEmpId;
		}
		public void setPublishByEmpId(Long publishByEmpId) {
			this.publishByEmpId = publishByEmpId;
		}
		public String getBundleType() {
			return bundleType;
		}
		public void setBundleType(String bundleType) {
			this.bundleType = bundleType;
		}
		public Long getPublishedByRoleId() {
			return publishedByRoleId;
		}
		public void setPublishedByRoleId(Long publishedByRoleId) {
			this.publishedByRoleId = publishedByRoleId;
		}
		public Date getPublishedDate() {
			return publishedDate;
		}
		public void setPublishedDate(Date publishedDate) {
			this.publishedDate = publishedDate;
		}
		public String getCktPushedIntoCRM() {
			return cktPushedIntoCRM;
		}
		public void setCktPushedIntoCRM(String cktPushedIntoCRM) {
			this.cktPushedIntoCRM = cktPushedIntoCRM;
		}
		public String getBundled() {
			return bundled;
		}
		public void setBundled(String bundled) {
			this.bundled = bundled;
		}
		public String getCancelledOrderServiceRef() {
			return cancelledOrderServiceRef;
		}
		public void setCancelledOrderServiceRef(String cancelledOrderServiceRef) {
			this.cancelledOrderServiceRef = cancelledOrderServiceRef;
		}
		public Integer getIsDemo() {
			return isDemo;
		}
		public void setIsDemo(Integer isDemo) {
			this.isDemo = isDemo;
		}
		public Integer getParallelUpgrageLSINo() {
			return parallelUpgrageLSINo;
		}
		public void setParallelUpgrageLSINo(Integer parallelUpgrageLSINo) {
			this.parallelUpgrageLSINo = parallelUpgrageLSINo;
		}
		public String getDowntimeClause() {
			return downtimeClause;
		}
		public void setDowntimeClause(String downtimeClause) {
			this.downtimeClause = downtimeClause;
		}
		public Date getDemoStartDate() {
			return demoStartDate;
		}
		public void setDemoStartDate(Date demoStartDate) {
			this.demoStartDate = demoStartDate;
		}
		public Date getDemoEndDate() {
			return demoEndDate;
		}
		public void setDemoEndDate(Date demoEndDate) {
			this.demoEndDate = demoEndDate;
		}
		public Long getDemoMailSent() {
			return demoMailSent;
		}
		public void setDemoMailSent(Long demoMailSent) {
			this.demoMailSent = demoMailSent;
		}
		public String getOldLogicalSINo() {
			return oldLogicalSINo;
		}
		public void setOldLogicalSINo(String oldLogicalSINo) {
			this.oldLogicalSINo = oldLogicalSINo;
		}
		public Long getInitiatedTo() {
			return initiatedTo;
		}
		public void setInitiatedTo(Long initiatedTo) {
			this.initiatedTo = initiatedTo;
		}
		public Integer getPublishReady() {
			return publishReady;
		}
		public void setPublishReady(Integer publishReady) {
			this.publishReady = publishReady;
		}
		public Integer getCancellationReasonId() {
			return cancellationReasonId;
		}
		public void setCancellationReasonId(Integer cancellationReasonId) {
			this.cancellationReasonId = cancellationReasonId;
		}
		public Integer getIsSaasBundle() {
			return isSaasBundle;
		}
		public void setIsSaasBundle(Integer isSaasBundle) {
			this.isSaasBundle = isSaasBundle;
		}
		public Integer getIsServiceValidated() {
			return isServiceValidated;
		}
		public void setIsServiceValidated(Integer isServiceValidated) {
			this.isServiceValidated = isServiceValidated;
		}
		public List<ServiceProductAttributeDto> getLsServiceProdAtt() {
			return lsServiceProdAtt;
		}
		public void setLsServiceProdAtt(
				List<ServiceProductAttributeDto> lsServiceProdAtt) {
			this.lsServiceProdAtt = lsServiceProdAtt;
		}
		public List<LineItemDto> getLsLineItemDetails() {
			return lsLineItemDetails;
		}
		public void setLsLineItemDetails(List<LineItemDto> lsLineItemDetails) {
			this.lsLineItemDetails = lsLineItemDetails;
		}
		public Long getAccountId() {
			return accountId;
		}
		public void setAccountId(Long accountId) {
			this.accountId = accountId;
		}
		
		//Shubhranshu,Drop&Carry
		public String getServiceFlavor() {
			return serviceFlavor;
		}
		public void setServiceFlavor(String serviceFlavor) {
			this.serviceFlavor = serviceFlavor;
		}
		public String getCircuitType() {
			return circuitType;
		}
		public void setCircuitType(String circuitType) {
			this.circuitType = circuitType;
		}
		public String getHeadEndCode() {
			return headEndCode;
		}
		public void setHeadEndCode(String headEndCode) {
			this.headEndCode = headEndCode;
		}
		public PagingSorting getPagingSorting() {
			return pagingSorting;
		}
		public void setPagingSorting(PagingSorting pagingSorting) {
			this.pagingSorting = pagingSorting;
		}
		public int getMaxPageNo() {
			return maxPageNo;
		}
		public void setMaxPageNo(int maxPageNo) {
			this.maxPageNo = maxPageNo;
		}
		public String getSqlMsg() {
			return sqlMsg;
		}
		public void setSqlMsg(String sqlMsg) {
			this.sqlMsg = sqlMsg;
		}
		public String getSqlMsgCode() {
			return sqlMsgCode;
		}
		public void setSqlMsgCode(String sqlMsgCode) {
			this.sqlMsgCode = sqlMsgCode;
		}
}
