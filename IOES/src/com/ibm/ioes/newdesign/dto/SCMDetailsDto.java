package com.ibm.ioes.newdesign.dto;

import java.sql.Date;

public class SCMDetailsDto {
	private Integer itemCode_Id;
	private Integer quntity;
	private Double chargeValue;
	private Long serviceProductId;
	private Date createDate;
	private Long createdBy;
	private Date modifyDate;
	private Long modifyBy;
	private Long deliveryLocationId;
	private Integer subInventryId;
	private Integer aop1_Id;
	private Integer aop2_Id;
	private	Integer isactive;
	private String  aopYear;
	private String po_no;
	public String getPo_no() {
		return po_no;
	}
	public void setPo_no(String po_no) {
		this.po_no = po_no;
	}
	private String poDate;
	private Double poAmount;
	
	public Integer getItemCode_Id() {
		return itemCode_Id;
	}
	public void setItemCode_Id(Integer itemCode_Id) {
		this.itemCode_Id = itemCode_Id;
	}
	public Integer getQuntity() {
		return quntity;
	}
	public void setQuntity(Integer quntity) {
		this.quntity = quntity;
	}
	public Double getChargeValue() {
		return chargeValue;
	}
	public void setChargeValue(Double chargeValue) {
		this.chargeValue = chargeValue;
	}
	public Long getServiceProductId() {
		return serviceProductId;
	}
	public void setServiceProductId(Long serviceProductId) {
		this.serviceProductId = serviceProductId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Long getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public Long getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(Long modifyBy) {
		this.modifyBy = modifyBy;
	}
	public Long getDeliveryLocationId() {
		return deliveryLocationId;
	}
	public void setDeliveryLocationId(Long deliveryLocationId) {
		this.deliveryLocationId = deliveryLocationId;
	}
	public Integer getSubInventryId() {
		return subInventryId;
	}
	public void setSubInventryId(Integer subInventryId) {
		this.subInventryId = subInventryId;
	}
	public Integer getAop1_Id() {
		return aop1_Id;
	}
	public void setAop1_Id(Integer aop1_Id) {
		this.aop1_Id = aop1_Id;
	}
	public Integer getAop2_Id() {
		return aop2_Id;
	}
	public void setAop2_Id(Integer aop2_Id) {
		this.aop2_Id = aop2_Id;
	}
	public Integer getIsactive() {
		return isactive;
	}
	public void setIsactive(Integer isactive) {
		this.isactive = isactive;
	}
	public String getAopYear() {
		return aopYear;
	}
	public void setAopYear(String aopYear) {
		this.aopYear = aopYear;
	}
	
	public String getPoDate() {
		return poDate;
	}
	public void setPoDate(String poDate) {
		this.poDate = poDate;
	}
	public Double getPoAmount() {
		return poAmount;
	}
	public void setPoAmount(Double poAmount) {
		this.poAmount = poAmount;
	}
	
}