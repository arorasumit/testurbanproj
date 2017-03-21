package com.ibm.ioes.npd.hibernate.beans;

public class ProductCategoryDto {
	private String productId;

	private String productDesc;

	private String isActive;

	private String createdDate;

	private String createdBy;

	private String modyfiedDate;

	private String modifiedBy;
	
	private String mappedStatus;

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

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModyfiedDate() {
		return modyfiedDate;
	}

	public void setModyfiedDate(String modyfiedDate) {
		this.modyfiedDate = modyfiedDate;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getMappedStatus() {
		return mappedStatus;
	}

	public void setMappedStatus(String mappedStatus) {
		this.mappedStatus = mappedStatus;
	}

}
