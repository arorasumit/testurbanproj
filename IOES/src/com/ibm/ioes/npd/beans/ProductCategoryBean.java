package com.ibm.ioes.npd.beans;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

public class ProductCategoryBean extends ActionForm {

	private String productCategoryId;

	private String[] description;

	private String isActive;

	private String createdDate;

	private String createdBy;

	private String modifiedDate;

	private String modyfiedBy;

	private String[] updateStatus;
	
	private String descReplica;
	
	private String active;
	private String inActive;

	public void reset(ActionMapping mapping, HttpServletRequest request) {
		this.productCategoryId = null;
		this.description = null;
		this.isActive = null;
		this.createdDate = null;
		this.createdBy = null;
		this.modifiedDate = null;
		this.modyfiedBy = null;
		this.updateStatus = null;
		this.descReplica = null;
		this.active = null;
		this.inActive = null;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public String[] getDescription() {
		return description;
	}

	public void setDescription(String[] description) {
		this.description = description;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModyfiedBy() {
		return modyfiedBy;
	}

	public void setModyfiedBy(String modyfiedBy) {
		this.modyfiedBy = modyfiedBy;
	}

	public String getProductCategoryId() {
		return productCategoryId;
	}

	public void setProductCategoryId(String productCategoryId) {
		this.productCategoryId = productCategoryId;
	}

	public String[] getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(String[] updateStatus) {
		this.updateStatus = updateStatus;
	}

	public String getDescReplica() {
		return descReplica;
	}

	public void setDescReplica(String descReplica) {
		this.descReplica = descReplica;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getInActive() {
		return inActive;
	}

	public void setInActive(String inActive) {
		this.inActive = inActive;
	}

}
