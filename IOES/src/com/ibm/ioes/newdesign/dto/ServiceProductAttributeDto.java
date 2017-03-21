package com.ibm.ioes.newdesign.dto;

import java.util.Date;

/**
 * 
 * @author IBM_ADMIN
 *@since June 2014
 */
public class ServiceProductAttributeDto {
	/* declare all attribues related to TPRODUCTATTVALUE */
	 Long labelId;
	 String labelAttId;
	 String labelAttValue;
	 Long serviceDetailId;
	 Date createdDate;
	 Long createdBy;
	 Date modifiedDate;
	 Long modifiedBy;
	/* declare all attribues related to TPRODUCTATTVALUE */
	public Long getLabelId() {
		return labelId;
	}
	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}
	public String getLabelAttId() {
		return labelAttId;
	}
	public void setLabelAttId(String labelAttId) {
		this.labelAttId = labelAttId;
	}
	public String getLabelAttValue() {
		return labelAttValue;
	}
	public void setLabelAttValue(String labelAttValue) {
		this.labelAttValue = labelAttValue;
	}
	public Long getServiceDetailId() {
		return serviceDetailId;
	}
	public void setServiceDetailId(Long serviceDetailId) {
		this.serviceDetailId = serviceDetailId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Long getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(Long modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	 
	 
}
