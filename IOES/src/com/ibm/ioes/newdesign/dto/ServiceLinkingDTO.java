/**
 * @author vipin
 */
package com.ibm.ioes.newdesign.dto;


public class ServiceLinkingDTO {

	private long primaryServiceTypeId;
	private long primaryServiceDetailId;
	private long secServiceTypeId;
	private long secServiceDetailId;
	private long primaryAttmasterId;
	private String mandLogic;
	private String publishCondition;
	private Long secServiceId;
	private Long secLogicalSiNo;
	private Long priServiceId;
	private Long priLogicalSiNo;
	private Long priServiceProductId;
	private String priM6FXProgressStatus;
	private String priServiceTypeName;
	private String secServiceTypeName;
	
	private String validationScenario;
	private Long secServiceProductId;
	private Long priOrderNo;
	
		
	public long getPrimaryServiceTypeId() {
		return primaryServiceTypeId;
	}
	public void setPrimaryServiceTypeId(long primaryServiceTypeId) {
		this.primaryServiceTypeId = primaryServiceTypeId;
	}
	public long getPrimaryServiceDetailId() {
		return primaryServiceDetailId;
	}
	public void setPrimaryServiceDetailId(long primaryServiceDetailId) {
		this.primaryServiceDetailId = primaryServiceDetailId;
	}
	public long getSecServiceTypeId() {
		return secServiceTypeId;
	}
	public void setSecServiceTypeId(long secServiceTypeId) {
		this.secServiceTypeId = secServiceTypeId;
	}
	public long getSecServiceDetailId() {
		return secServiceDetailId;
	}
	public void setSecServiceDetailId(long secServiceDetailId) {
		this.secServiceDetailId = secServiceDetailId;
	}
	public long getPrimaryAttmasterId() {
		return primaryAttmasterId;
	}
	public void setPrimaryAttmasterId(long primaryAttmasterId) {
		this.primaryAttmasterId = primaryAttmasterId;
	}	
	public String getMandLogic() {
		return mandLogic;
	}
	public void setMandLogic(String mandLogic) {
		this.mandLogic = mandLogic;
	}
	public String getPublishCondition() {
		return publishCondition;
	}
	public void setPublishCondition(String publishCondition) {
		this.publishCondition = publishCondition;
	}
	public Long getSecServiceId() {
		return secServiceId;
	}
	public void setSecServiceId(Long secServiceId) {
		this.secServiceId = secServiceId;
	}
	public Long getSecLogicalSiNo() {
		return secLogicalSiNo;
	}
	public void setSecLogicalSiNo(Long secLogicalSiNo) {
		this.secLogicalSiNo = secLogicalSiNo;
	}
	public Long getPriServiceId() {
		return priServiceId;
	}
	public void setPriServiceId(Long priServiceId) {
		this.priServiceId = priServiceId;
	}
	public Long getPriLogicalSiNo() {
		return priLogicalSiNo;
	}
	public void setPriLogicalSiNo(Long priLogicalSiNo) {
		this.priLogicalSiNo = priLogicalSiNo;
	}
	public Long getPriServiceProductId() {
		return priServiceProductId;
	}
	public void setPriServiceProductId(Long priServiceProductId) {
		this.priServiceProductId = priServiceProductId;
	}
	public String getPriM6FXProgressStatus() {
		return priM6FXProgressStatus;
	}
	public void setPriM6FXProgressStatus(String priM6FXProgressStatus) {
		this.priM6FXProgressStatus = priM6FXProgressStatus;
	}
	public String getPriServiceTypeName() {
		return priServiceTypeName;
	}
	public void setPriServiceTypeName(String priServiceTypeName) {
		this.priServiceTypeName = priServiceTypeName;
	}
	public String getSecServiceTypeName() {
		return secServiceTypeName;
	}
	public void setSecServiceTypeName(String secServiceTypeName) {
		this.secServiceTypeName = secServiceTypeName;
	}
	public String getValidationScenario() {
		return validationScenario;
	}
	public void setValidationScenario(String validationScenario) {
		this.validationScenario = validationScenario;
	}
	public Long getSecServiceProductId() {
		return secServiceProductId;
	}
	public void setSecServiceProductId(Long secServiceProductId) {
		this.secServiceProductId = secServiceProductId;
	}
	public Long getPriOrderNo() {
		return priOrderNo;
	}
	public void setPriOrderNo(Long priOrderNo) {
		this.priOrderNo = priOrderNo;
	}
	
}
 