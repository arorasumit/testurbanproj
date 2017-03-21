package com.ibm.ioes.newdesign.dto;

import java.util.Date;

/**
 * 
 * @author IBM_ADMIN
 *@since June 2014
 */
public class ServiceSummarryAttributesDto {

	/* Declare all properties related to TPRODUCTLINEATTVALUE and TPRODUCTLINEATTVALUE_HISTORY */
		
		Long attValueId;
		Long attMasterId;
		String attDescription;
		String attValue;
		String attValue_New;
		Long orderNo;
		String orderType;
		Long main_service_Id;
		Long serviceProductId;
		Date createdDate;
		Long createdBy;
		Date modifiedDate;
		Long modifiedBy;
		Long lastInsertHelperId;
		public Long getAttValueId() {
			return attValueId;
		}
		public void setAttValueId(Long attValueId) {
			this.attValueId = attValueId;
		}
		public Long getAttMasterId() {
			return attMasterId;
		}
		public void setAttMasterId(Long attMasterId) {
			this.attMasterId = attMasterId;
		}
		public String getAttDescription() {
			return attDescription;
		}
		public void setAttDescription(String attDescription) {
			this.attDescription = attDescription;
		}
		public String getAttValue() {
			return attValue;
		}
		public void setAttValue(String attValue) {
			this.attValue = attValue;
		}
		public String getAttValue_New() {
			return attValue_New;
		}
		public void setAttValue_New(String attValue_New) {
			this.attValue_New = attValue_New;
		}
		public Long getOrderNo() {
			return orderNo;
		}
		public void setOrderNo(Long orderNo) {
			this.orderNo = orderNo;
		}
		public String getOrderType() {
			return orderType;
		}
		public void setOrderType(String orderType) {
			this.orderType = orderType;
		}
		public Long getMain_service_Id() {
			return main_service_Id;
		}
		public void setMain_service_Id(Long main_service_Id) {
			this.main_service_Id = main_service_Id;
		}
		public Long getServiceProductId() {
			return serviceProductId;
		}
		public void setServiceProductId(Long serviceProductId) {
			this.serviceProductId = serviceProductId;
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
		public Long getLastInsertHelperId() {
			return lastInsertHelperId;
		}
		public void setLastInsertHelperId(Long lastInsertHelperId) {
			this.lastInsertHelperId = lastInsertHelperId;
		}
		
	/* Declare all properties related to TPRODUCTLINEATTVALUE and TPRODUCTLINEATTVALUE_HISTORY */
		
		
}
