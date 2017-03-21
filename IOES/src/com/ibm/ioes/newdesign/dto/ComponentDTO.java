package com.ibm.ioes.newdesign.dto;

import java.sql.Date;

public class ComponentDTO {
	
	Long componentInfoId;
	Long accountId;
	Long serviceProductId;
	Long componentId;
	Integer isDisconnected;
	String createdBy;
	String modifiedBy;
	Date createdDate;
	Date modifiedDate;
	Long packageId;
	String componentStatus;
	Long createdInServiceId;
	Long disconnectedInServiceId;
	String componentStartDate;
	String componentEndDate;
	String componentStartLogic;
	String componentEndLogic;
	Long fxCreateIntegrationTableId;
	Long fxDisconnectIntegrationTableId;
	String fxStartStatus;
	String fxEndStatus;
	String systemStartDate;
	String systemEndDate;
	String systemStartStatus;
	String systemEndStatus;
	Long systemFxCreateIntegrationTableId;
	Long systemFxDisconnectIntegrationTableId;
	Long systemStartOrderNo;
	Long systemEndOrderNo;
	Integer startDays;
	Integer startMonths;
	Long oldComponentInfoId;
	Integer endDays;
	Integer endMonths;
	Integer packageGroupingId;
	Double obValue;
	Date obValueLastUpdateDate;
	Long netOb;
	Long obLinkComponentInfoId;
	Long netObId;
	String lepmYN;
	public Long getComponentInfoId() {
		return componentInfoId;
	}
	public void setComponentInfoId(Long componentInfoId) {
		this.componentInfoId = componentInfoId;
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
	public Long getComponentId() {
		return componentId;
	}
	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}
	public Integer getIsDisconnected() {
		return isDisconnected;
	}
	public void setIsDisconnected(Integer isDisconnected) {
		this.isDisconnected = isDisconnected;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
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
	public Long getPackageId() {
		return packageId;
	}
	public void setPackageId(Long packageId) {
		this.packageId = packageId;
	}
	public String getComponentStatus() {
		return componentStatus;
	}
	public void setComponentStatus(String componentStatus) {
		this.componentStatus = componentStatus;
	}
	public Long getCreatedInServiceId() {
		return createdInServiceId;
	}
	public void setCreatedInServiceId(Long createdInServiceId) {
		this.createdInServiceId = createdInServiceId;
	}
	public Long getDisconnectedInServiceId() {
		return disconnectedInServiceId;
	}
	public void setDisconnectedInServiceId(Long disconnectedInServiceId) {
		this.disconnectedInServiceId = disconnectedInServiceId;
	}
	public String getComponentStartDate() {
		return componentStartDate;
	}
	public void setComponentStartDate(String componentStartDate) {
		this.componentStartDate = componentStartDate;
	}
	public String getComponentEndDate() {
		return componentEndDate;
	}
	public void setComponentEndDate(String componentEndDate) {
		this.componentEndDate = componentEndDate;
	}
	public String getComponentStartLogic() {
		return componentStartLogic;
	}
	public void setComponentStartLogic(String componentStartLogic) {
		this.componentStartLogic = componentStartLogic;
	}
	public String getComponentEndLogic() {
		return componentEndLogic;
	}
	public void setComponentEndLogic(String componentEndLogic) {
		this.componentEndLogic = componentEndLogic;
	}
	public Long getFxCreateIntegrationTableId() {
		return fxCreateIntegrationTableId;
	}
	public void setFxCreateIntegrationTableId(Long fxCreateIntegrationTableId) {
		this.fxCreateIntegrationTableId = fxCreateIntegrationTableId;
	}
	public Long getFxDisconnectIntegrationTableId() {
		return fxDisconnectIntegrationTableId;
	}
	public void setFxDisconnectIntegrationTableId(
			Long fxDisconnectIntegrationTableId) {
		this.fxDisconnectIntegrationTableId = fxDisconnectIntegrationTableId;
	}
	public String getFxStartStatus() {
		return fxStartStatus;
	}
	public void setFxStartStatus(String fxStartStatus) {
		this.fxStartStatus = fxStartStatus;
	}
	public String getFxEndStatus() {
		return fxEndStatus;
	}
	public void setFxEndStatus(String fxEndStatus) {
		this.fxEndStatus = fxEndStatus;
	}
	public String getSystemStartDate() {
		return systemStartDate;
	}
	public void setSystemStartDate(String systemStartDate) {
		this.systemStartDate = systemStartDate;
	}
	public String getSystemEndDate() {
		return systemEndDate;
	}
	public void setSystemEndDate(String systemEndDate) {
		this.systemEndDate = systemEndDate;
	}
	public String getSystemStartStatus() {
		return systemStartStatus;
	}
	public void setSystemStartStatus(String systemStartStatus) {
		this.systemStartStatus = systemStartStatus;
	}
	public String getSystemEndStatus() {
		return systemEndStatus;
	}
	public void setSystemEndStatus(String systemEndStatus) {
		this.systemEndStatus = systemEndStatus;
	}
	public Long getSystemFxCreateIntegrationTableId() {
		return systemFxCreateIntegrationTableId;
	}
	public void setSystemFxCreateIntegrationTableId(
			Long systemFxCreateIntegrationTableId) {
		this.systemFxCreateIntegrationTableId = systemFxCreateIntegrationTableId;
	}
	public Long getSystemFxDisconnectIntegrationTableId() {
		return systemFxDisconnectIntegrationTableId;
	}
	public void setSystemFxDisconnectIntegrationTableId(
			Long systemFxDisconnectIntegrationTableId) {
		this.systemFxDisconnectIntegrationTableId = systemFxDisconnectIntegrationTableId;
	}
	public Long getSystemStartOrderNo() {
		return systemStartOrderNo;
	}
	public void setSystemStartOrderNo(Long systemStartOrderNo) {
		this.systemStartOrderNo = systemStartOrderNo;
	}
	public Long getSystemEndOrderNo() {
		return systemEndOrderNo;
	}
	public void setSystemEndOrderNo(Long systemEndOrderNo) {
		this.systemEndOrderNo = systemEndOrderNo;
	}
	public Integer getStartDays() {
		return startDays;
	}
	public void setStartDays(Integer startDays) {
		this.startDays = startDays;
	}
	public Integer getStartMonths() {
		return startMonths;
	}
	public void setStartMonths(Integer startMonths) {
		this.startMonths = startMonths;
	}
	public Long getOldComponentInfoId() {
		return oldComponentInfoId;
	}
	public void setOldComponentInfoId(Long oldComponentInfoId) {
		this.oldComponentInfoId = oldComponentInfoId;
	}
	public Integer getEndDays() {
		return endDays;
	}
	public void setEndDays(Integer endDays) {
		this.endDays = endDays;
	}
	public Integer getEndMonths() {
		return endMonths;
	}
	public void setEndMonths(Integer endMonths) {
		this.endMonths = endMonths;
	}
	public Integer getPackageGroupingId() {
		return packageGroupingId;
	}
	public void setPackageGroupingId(Integer packageGroupingId) {
		this.packageGroupingId = packageGroupingId;
	}
	public Double getObValue() {
		return obValue;
	}
	public void setObValue(Double obValue) {
		this.obValue = obValue;
	}
	public Date getObValueLastUpdateDate() {
		return obValueLastUpdateDate;
	}
	public void setObValueLastUpdateDate(Date obValueLastUpdateDate) {
		this.obValueLastUpdateDate = obValueLastUpdateDate;
	}
	public Long getNetOb() {
		return netOb;
	}
	public void setNetOb(Long netOb) {
		this.netOb = netOb;
	}
	public Long getObLinkComponentInfoId() {
		return obLinkComponentInfoId;
	}
	public void setObLinkComponentInfoId(Long obLinkComponentInfoId) {
		this.obLinkComponentInfoId = obLinkComponentInfoId;
	}
	public Long getNetObId() {
		return netObId;
	}
	public void setNetObId(Long netObId) {
		this.netObId = netObId;
	}
	public String getLepmYN() {
		return lepmYN;
	}
	public void setLepmYN(String lepmYN) {
		this.lepmYN = lepmYN;
	}
}
