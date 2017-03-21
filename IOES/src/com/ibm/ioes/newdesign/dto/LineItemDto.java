package com.ibm.ioes.newdesign.dto;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author IBM_ADMIN
 * @since June 2014
 */
public class LineItemDto {
	/* Declare all properties which related to TPOSERVICEDETAILS*/
	Long serviceProductId;
	Long serviceId;
	Long serviceDetailId;
	String locdate;
	String billingTriggerDate;
	String locNo;
	String triggerDate;
	Long parentServiceProductId;
	Long counterId;
	Long oldServiceProductId;
	Integer isDisconnected;
	Integer isSuspended;
	Integer isDisconnectedInFx;
	Integer isDisconnectedInM6;
	Integer isSuspenedInFx;
	Integer isSuspendedInM6;
	Long changeOrderNo;
	Integer billingTriggerStatus;
	String fxAccountExternalId;
	String m6ChildSerKey;
	String m6ParentSerKey;
	Long subChangeTypeId;
	Date createdDate;
	Date modifiedDate;
	String cktId;
	String priloc;
    String hubLoc;
    String uom;
    String loc_date;
    Integer billingTriggerDisconectedStatus;
    Long createdby;
    Long modifiedBy;
    String challanNo;
	String challanDate;
	String m6OraclePRNo;
	String m6HwInstallDate;
	Long changeServiceId;
	Long changeParentSpId;
	Long additionalNode;
	String hwInstallStatus;
	Long isAutoBilling;
	String logicalCircuitId;
	String infraOrderNo;
	String metasolvCircuitId;
	String serialNumber;
	String logicalCircuitIdNew;
	String infraOrderNoNew;
	String metasolvCircuitIdNew;
	String locRecDate;
	String fxSIId;
	String lineStatus;
	String m6AttFxChanged;
	Date serviceActiveDt;
	Date billingTriggerCreateDate;
	String duplicateCktId;
	String forBillingTrigger;
	Long configId;
	Integer fxServiceUpdateStatus;
	Integer isDummy;
	Integer noFxSiFlag;
	Long vcsBundledService;
	Long btDoneBy;
	String poExpiryDateString;
	Date poExpiryDate;
	Date btActionDate;
	Long locDataUpdate;
	String scmProgressStatus;
	Long locDataUpdate1;
	Long prId;
	Long isPrReuse;
	/* Declare all properties which related to TPOSERVICEDETAILS*/
	
	/* Declare other attributes which associated with LinesItem */
		List<ServiceSummarryAttributesDto> lstServiceSummarryDetails;
		BillingDetailsDto billingDetails;
		LocationDetailsDto locationDetails;
		HardwareDetailsDto hardwareDetails;
		List<ChargeDetailsDto> lstChargeDetails;
		List<ComponentDTO> lstComponentDetails;
		List<ServiceDetailsConfigAttValuesDTO> lineConfigAttDetails;
		AdvancePaymentDTO advancePaymentDetails;
		List<SCMDetailsDto> lstScmDetails;
		
		/* Declare other attributes which associated with LinesItem */
		public Long getServiceProductId() {
			return serviceProductId;
		}
		public void setServiceProductId(Long serviceProductId) {
			this.serviceProductId = serviceProductId;
		}
		public Long getServiceId() {
			return serviceId;
		}
		public void setServiceId(Long serviceId) {
			this.serviceId = serviceId;
		}
		public Long getServiceDetailId() {
			return serviceDetailId;
		}
		public void setServiceDetailId(Long serviceDetailId) {
			this.serviceDetailId = serviceDetailId;
		}
		public String getLocdate() {
			return locdate;
		}
		public void setLocdate(String locdate) {
			this.locdate = locdate;
		}
		public String getBillingTriggerDate() {
			return billingTriggerDate;
		}
		public void setBillingTriggerDate(String billingTriggerDate) {
			this.billingTriggerDate = billingTriggerDate;
		}
		public String getLocNo() {
			return locNo;
		}
		public void setLocNo(String locNo) {
			this.locNo = locNo;
		}
		public String getTriggerDate() {
			return triggerDate;
		}
		public void setTriggerDate(String triggerDate) {
			this.triggerDate = triggerDate;
		}
		public Long getParentServiceProductId() {
			return parentServiceProductId;
		}
		public void setParentServiceProductId(Long parentServiceProductId) {
			this.parentServiceProductId = parentServiceProductId;
		}
		public Long getCounterId() {
			return counterId;
		}
		public void setCounterId(Long counterId) {
			this.counterId = counterId;
		}
		public Long getOldServiceProductId() {
			return oldServiceProductId;
		}
		public void setOldServiceProductId(Long oldServiceProductId) {
			this.oldServiceProductId = oldServiceProductId;
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
		public Integer getIsSuspenedInFx() {
			return isSuspenedInFx;
		}
		public void setIsSuspenedInFx(Integer isSuspenedInFx) {
			this.isSuspenedInFx = isSuspenedInFx;
		}
		public Integer getIsSuspendedInM6() {
			return isSuspendedInM6;
		}
		public void setIsSuspendedInM6(Integer isSuspendedInM6) {
			this.isSuspendedInM6 = isSuspendedInM6;
		}
		public Long getChangeOrderNo() {
			return changeOrderNo;
		}
		public void setChangeOrderNo(Long changeOrderNo) {
			this.changeOrderNo = changeOrderNo;
		}
		public Integer getBillingTriggerStatus() {
			return billingTriggerStatus;
		}
		public void setBillingTriggerStatus(Integer billingTriggerStatus) {
			this.billingTriggerStatus = billingTriggerStatus;
		}
		public String getFxAccountExternalId() {
			return fxAccountExternalId;
		}
		public void setFxAccountExternalId(String fxAccountExternalId) {
			this.fxAccountExternalId = fxAccountExternalId;
		}
		public String getM6ChildSerKey() {
			return m6ChildSerKey;
		}
		public void setM6ChildSerKey(String m6ChildSerKey) {
			this.m6ChildSerKey = m6ChildSerKey;
		}
		public String getM6ParentSerKey() {
			return m6ParentSerKey;
		}
		public void setM6ParentSerKey(String m6ParentSerKey) {
			this.m6ParentSerKey = m6ParentSerKey;
		}
		public Long getSubChangeTypeId() {
			return subChangeTypeId;
		}
		public void setSubChangeTypeId(Long subChangeTypeId) {
			this.subChangeTypeId = subChangeTypeId;
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
		public String getCktId() {
			return cktId;
		}
		public void setCktId(String cktId) {
			this.cktId = cktId;
		}
		public String getPriloc() {
			return priloc;
		}
		public void setPriloc(String priloc) {
			this.priloc = priloc;
		}
		public String getHubLoc() {
			return hubLoc;
		}
		public void setHubLoc(String hubLoc) {
			this.hubLoc = hubLoc;
		}
		public String getUom() {
			return uom;
		}
		public void setUom(String uom) {
			this.uom = uom;
		}
		public String getLoc_date() {
			return loc_date;
		}
		public void setLoc_date(String loc_date) {
			this.loc_date = loc_date;
		}
		public Integer getBillingTriggerDisconectedStatus() {
			return billingTriggerDisconectedStatus;
		}
		public void setBillingTriggerDisconectedStatus(
				Integer billingTriggerDisconectedStatus) {
			this.billingTriggerDisconectedStatus = billingTriggerDisconectedStatus;
		}
		public Long getCreatedby() {
			return createdby;
		}
		public void setCreatedby(Long createdby) {
			this.createdby = createdby;
		}
		public Long getModifiedBy() {
			return modifiedBy;
		}
		public void setModifiedBy(Long modifiedBy) {
			this.modifiedBy = modifiedBy;
		}
		public String getChallanNo() {
			return challanNo;
		}
		public void setChallanNo(String challanNo) {
			this.challanNo = challanNo;
		}
		public String getChallanDate() {
			return challanDate;
		}
		public void setChallanDate(String challanDate) {
			this.challanDate = challanDate;
		}
		public String getM6OraclePRNo() {
			return m6OraclePRNo;
		}
		public void setM6OraclePRNo(String m6OraclePRNo) {
			this.m6OraclePRNo = m6OraclePRNo;
		}
		public String getM6HwInstallDate() {
			return m6HwInstallDate;
		}
		public void setM6HwInstallDate(String m6HwInstallDate) {
			this.m6HwInstallDate = m6HwInstallDate;
		}
		public Long getChangeServiceId() {
			return changeServiceId;
		}
		public void setChangeServiceId(Long changeServiceId) {
			this.changeServiceId = changeServiceId;
		}
		public Long getChangeParentSpId() {
			return changeParentSpId;
		}
		public void setChangeParentSpId(Long changeParentSpId) {
			this.changeParentSpId = changeParentSpId;
		}
		public Long getAdditionalNode() {
			return additionalNode;
		}
		public void setAdditionalNode(Long additionalNode) {
			this.additionalNode = additionalNode;
		}
		public String getHwInstallStatus() {
			return hwInstallStatus;
		}
		public void setHwInstallStatus(String hwInstallStatus) {
			this.hwInstallStatus = hwInstallStatus;
		}
		public Long getIsAutoBilling() {
			return isAutoBilling;
		}
		public void setIsAutoBilling(Long isAutoBilling) {
			this.isAutoBilling = isAutoBilling;
		}
		public String getLogicalCircuitId() {
			return logicalCircuitId;
		}
		public void setLogicalCircuitId(String logicalCircuitId) {
			this.logicalCircuitId = logicalCircuitId;
		}
		public String getInfraOrderNo() {
			return infraOrderNo;
		}
		public void setInfraOrderNo(String infraOrderNo) {
			this.infraOrderNo = infraOrderNo;
		}
		public String getMetasolvCircuitId() {
			return metasolvCircuitId;
		}
		public void setMetasolvCircuitId(String metasolvCircuitId) {
			this.metasolvCircuitId = metasolvCircuitId;
		}
		public String getSerialNumber() {
			return serialNumber;
		}
		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}
		public String getLogicalCircuitIdNew() {
			return logicalCircuitIdNew;
		}
		public void setLogicalCircuitIdNew(String logicalCircuitIdNew) {
			this.logicalCircuitIdNew = logicalCircuitIdNew;
		}
		public String getInfraOrderNoNew() {
			return infraOrderNoNew;
		}
		public void setInfraOrderNoNew(String infraOrderNoNew) {
			this.infraOrderNoNew = infraOrderNoNew;
		}
		public String getMetasolvCircuitIdNew() {
			return metasolvCircuitIdNew;
		}
		public void setMetasolvCircuitIdNew(String metasolvCircuitIdNew) {
			this.metasolvCircuitIdNew = metasolvCircuitIdNew;
		}
		public String getLocRecDate() {
			return locRecDate;
		}
		public void setLocRecDate(String locRecDate) {
			this.locRecDate = locRecDate;
		}
		public String getFxSIId() {
			return fxSIId;
		}
		public void setFxSIId(String fxSIId) {
			this.fxSIId = fxSIId;
		}
		public String getLineStatus() {
			return lineStatus;
		}
		public void setLineStatus(String lineStatus) {
			this.lineStatus = lineStatus;
		}
		public String getM6AttFxChanged() {
			return m6AttFxChanged;
		}
		public void setM6AttFxChanged(String m6AttFxChanged) {
			this.m6AttFxChanged = m6AttFxChanged;
		}
		public Date getServiceActiveDt() {
			return serviceActiveDt;
		}
		public void setServiceActiveDt(Date serviceActiveDt) {
			this.serviceActiveDt = serviceActiveDt;
		}
		public Date getBillingTriggerCreateDate() {
			return billingTriggerCreateDate;
		}
		public void setBillingTriggerCreateDate(Date billingTriggerCreateDate) {
			this.billingTriggerCreateDate = billingTriggerCreateDate;
		}
		public String getDuplicateCktId() {
			return duplicateCktId;
		}
		public void setDuplicateCktId(String duplicateCktId) {
			this.duplicateCktId = duplicateCktId;
		}
		public String getForBillingTrigger() {
			return forBillingTrigger;
		}
		public void setForBillingTrigger(String forBillingTrigger) {
			this.forBillingTrigger = forBillingTrigger;
		}
		public Long getConfigId() {
			return configId;
		}
		public void setConfigId(Long configId) {
			this.configId = configId;
		}
		public Integer getFxServiceUpdateStatus() {
			return fxServiceUpdateStatus;
		}
		public void setFxServiceUpdateStatus(Integer fxServiceUpdateStatus) {
			this.fxServiceUpdateStatus = fxServiceUpdateStatus;
		}
		public Integer getIsDummy() {
			return isDummy;
		}
		public void setIsDummy(Integer isDummy) {
			this.isDummy = isDummy;
		}
		public Integer getNoFxSiFlag() {
			return noFxSiFlag;
		}
		public void setNoFxSiFlag(Integer noFxSiFlag) {
			this.noFxSiFlag = noFxSiFlag;
		}
		public Long getVcsBundledService() {
			return vcsBundledService;
		}
		public void setVcsBundledService(Long vcsBundledService) {
			this.vcsBundledService = vcsBundledService;
		}
		public Long getBtDoneBy() {
			return btDoneBy;
		}
		public void setBtDoneBy(Long btDoneBy) {
			this.btDoneBy = btDoneBy;
		}
		public String getPoExpiryDateString() {
			return poExpiryDateString;
		}
		public void setPoExpiryDateString(String poExpiryDateString) {
			this.poExpiryDateString = poExpiryDateString;
		}
		public Date getPoExpiryDate() {
			return poExpiryDate;
		}
		public void setPoExpiryDate(Date poExpiryDate) {
			this.poExpiryDate = poExpiryDate;
		}
		public Date getBtActionDate() {
			return btActionDate;
		}
		public void setBtActionDate(Date btActionDate) {
			this.btActionDate = btActionDate;
		}
		public Long getLocDataUpdate() {
			return locDataUpdate;
		}
		public void setLocDataUpdate(Long locDataUpdate) {
			this.locDataUpdate = locDataUpdate;
		}
		public String getScmProgressStatus() {
			return scmProgressStatus;
		}
		public void setScmProgressStatus(String scmProgressStatus) {
			this.scmProgressStatus = scmProgressStatus;
		}
		public Long getLocDataUpdate1() {
			return locDataUpdate1;
		}
		public void setLocDataUpdate1(Long locDataUpdate1) {
			this.locDataUpdate1 = locDataUpdate1;
		}
		public Long getPrId() {
			return prId;
		}
		public void setPrId(Long prId) {
			this.prId = prId;
		}
		public Long getIsPrReuse() {
			return isPrReuse;
		}
		public void setIsPrReuse(Long isPrReuse) {
			this.isPrReuse = isPrReuse;
		}
		public List<ServiceSummarryAttributesDto> getLstServiceSummarryDetails() {
			return lstServiceSummarryDetails;
		}
		public void setLstServiceSummarryDetails(
				List<ServiceSummarryAttributesDto> lstServiceSummarryDetails) {
			this.lstServiceSummarryDetails = lstServiceSummarryDetails;
		}
		public BillingDetailsDto getBillingDetails() {
			return billingDetails;
		}
		public void setBillingDetails(BillingDetailsDto billingDetails) {
			this.billingDetails = billingDetails;
		}
		public LocationDetailsDto getLocationDetails() {
			return locationDetails;
		}
		public void setLocationDetails(LocationDetailsDto locationDetails) {
			this.locationDetails = locationDetails;
		}
		public HardwareDetailsDto getHardwareDetails() {
			return hardwareDetails;
		}
		public void setHardwareDetails(HardwareDetailsDto hardwareDetails) {
			this.hardwareDetails = hardwareDetails;
		}
		public List<ChargeDetailsDto> getLstChargeDetails() {
			return lstChargeDetails;
		}
		public void setLstChargeDetails(List<ChargeDetailsDto> lstChargeDetails) {
			this.lstChargeDetails = lstChargeDetails;
		}
		public List<ComponentDTO> getLstComponentDetails() {
			return lstComponentDetails;
		}
		public void setLstComponentDetails(List<ComponentDTO> lstComponentDetails) {
			this.lstComponentDetails = lstComponentDetails;
		}
		public List<ServiceDetailsConfigAttValuesDTO> getLineConfigAttDetails() {
			return lineConfigAttDetails;
		}
		public void setLineConfigAttDetails(
				List<ServiceDetailsConfigAttValuesDTO> lineConfigAttDetails) {
			this.lineConfigAttDetails = lineConfigAttDetails;
		}
		public AdvancePaymentDTO getAdvancePaymentDetails() {
			return advancePaymentDetails;
		}
		public void setAdvancePaymentDetails(AdvancePaymentDTO advancePaymentDetails) {
			this.advancePaymentDetails = advancePaymentDetails;
		}
		public List<SCMDetailsDto> getLstScmDetails() {
			return lstScmDetails;
		}
		public void setLstScmDetails(List<SCMDetailsDto> lstScmDetails) {
			this.lstScmDetails = lstScmDetails;
		}
		
}
