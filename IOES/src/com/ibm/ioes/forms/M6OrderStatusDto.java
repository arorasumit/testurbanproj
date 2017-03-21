package com.ibm.ioes.forms;

import java.util.ArrayList;
import com.ibm.ioes.utilities.PagingSorting;

public class M6OrderStatusDto 
{
	
	
	PagingSorting pagingSorting=new PagingSorting();
	private int updateFlag;
		private ArrayList list;
		
		private String searchAccount;
					
		private String accountID;
		
		private String accountName;
		
	    ArrayList errors_Validation=new ArrayList();
		
		private String searchAccountIdStr;
		private String searchAccountNameStr;
		private String searchOrderNoStr;
		private String searchM6OrderNoStr;
		private String remarks;
		private String createdDate;
				
		private String orderNo;
		private String m6OrderNo;
		private String hdnOrderNo;
		//Added by Anil for get and set config values
		private int configValue;
		private int m6AttributeId;
		private String m6AttributeValue;
		private long serviceDetailID;
		private int billingModeId;
		private String billingModeName;
		private long serviceId;		
		private int maxLineItemAllow;
		private int totalLineItemAttached;
		private String isUsageAllow;
		private int compInfo;
		private int chargeInfo;
		private int toComputeAlert;
		private int oldConfigValue;
		private String oldConfigAlert;
		private String newConfigAlert;
		private long serviceProductID;
		private int isDummy;
		private int dummyLineItem;
		private int nonDummyLineItem;
		private String fxChargeRedirectionType;
		private String fxChargeRedirectionTypeCumulative;
		
		private String componentStartDateLogicId;
		private String componentStartDateLogicDesc;
		private String componentEndDateLogicId;
		private String componentEndDateLogicDesc;
		private String fli_po_disable;

	public String getComponentEndDateLogicDesc() {
			return componentEndDateLogicDesc;
		}
		public void setComponentEndDateLogicDesc(String componentEndDateLogicDesc) {
			this.componentEndDateLogicDesc = componentEndDateLogicDesc;
		}
		public String getComponentEndDateLogicId() {
			return componentEndDateLogicId;
		}
		public void setComponentEndDateLogicId(String componentEndDateLogicId) {
			this.componentEndDateLogicId = componentEndDateLogicId;
		}
		public String getComponentStartDateLogicDesc() {
			return componentStartDateLogicDesc;
		}
		public void setComponentStartDateLogicDesc(String componentStartDateLogicDesc) {
			this.componentStartDateLogicDesc = componentStartDateLogicDesc;
		}
		public String getComponentStartDateLogicId() {
			return componentStartDateLogicId;
		}
		public void setComponentStartDateLogicId(String componentStartDateLogicId) {
			this.componentStartDateLogicId = componentStartDateLogicId;
		}
		
		public int getChargeInfo() {
			return chargeInfo;
		}
		public void setChargeInfo(int chargeInfo) {
			this.chargeInfo = chargeInfo;
		}
		public int getCompInfo() {
			return compInfo;
		}
		public void setCompInfo(int compInfo) {
			this.compInfo = compInfo;
		}
		public String getIsUsageAllow() {
			return isUsageAllow;
		}
		public void setIsUsageAllow(String isUsageAllow) {
			this.isUsageAllow = isUsageAllow;
		}
		public int getMaxLineItemAllow() {
			return maxLineItemAllow;
		}
		public void setMaxLineItemAllow(int maxLineItemAllow) {
			this.maxLineItemAllow = maxLineItemAllow;
		}
		public long getServiceId() {
			return serviceId;
		}
		public void setServiceId(long serviceId) {
			this.serviceId = serviceId;
		}
		public int getTotalLineItemAttached() {
			return totalLineItemAttached;
		}
		public void setTotalLineItemAttached(int totalLineItemAttached) {
			this.totalLineItemAttached = totalLineItemAttached;
		}
		public int getBillingModeId() {
			return billingModeId;
		}
		public void setBillingModeId(int billingModeId) {
			this.billingModeId = billingModeId;
		}
		public String getBillingModeName() {
			return billingModeName;
		}
		public void setBillingModeName(String billingModeName) {
			this.billingModeName = billingModeName;
		}
		public int getConfigValue() {
			return configValue;
		}
		public void setConfigValue(int configValue) {
			this.configValue = configValue;
		}
		public int getM6AttributeId() {
			return m6AttributeId;
		}
		public void setM6AttributeId(int attributeId) {
			m6AttributeId = attributeId;
		}
		public String getM6AttributeValue() {
			return m6AttributeValue;
		}
		public void setM6AttributeValue(String attributeValue) {
			m6AttributeValue = attributeValue;
		}
		public String getAccountName() {
			return accountName;
		}
		public void setAccountName(String accountName) {
			this.accountName = accountName;
		}
		
		public ArrayList getList() {
			return list;
		}
		public void setList(ArrayList list) {
			this.list = list;
		}
		
		
		
		public String getSearchAccount() {
			return searchAccount;
		}
		public void setSearchAccount(String searchAccount) {
			this.searchAccount = searchAccount;
		}

		public String getAccountID() {
			return accountID;
		}
		public void setAccountID(String accountID) {
			this.accountID = accountID;
		}
		
		public PagingSorting getPagingSorting() {
			return pagingSorting;
		}
		public void setPagingSorting(PagingSorting pagingSorting) {
			this.pagingSorting = pagingSorting;
		}
		public int getUpdateFlag() {
			return updateFlag;
		}
		public void setUpdateFlag(int updateFlag) {
			this.updateFlag = updateFlag;
		}
		
		public ArrayList getErrors_Validation() {
			return errors_Validation;
		}
		public void setErrors_Validation(ArrayList errors_Validation) {
			this.errors_Validation = errors_Validation;
		}
		public String getSearchAccountIdStr() {
			return searchAccountIdStr;
		}
		public void setSearchAccountIdStr(String searchAccountIdStr) {
			this.searchAccountIdStr = searchAccountIdStr;
		}
		
		
		public String getSearchAccountNameStr() {
			return searchAccountNameStr;
		}
		public void setSearchAccountNameStr(String searchAccountNameStr) {
			this.searchAccountNameStr = searchAccountNameStr;
		}
		/**
		 * @return the searchOrderNoStr
		 */
		public String getSearchOrderNoStr() {
			return searchOrderNoStr;
		}
		/**
		 * @param searchOrderNoStr the searchOrderNoStr to set
		 */
		public void setSearchOrderNoStr(String searchOrderNoStr) {
			this.searchOrderNoStr = searchOrderNoStr;
		}
		/**
		 * @return the orderNo
		 */
		public String getOrderNo() {
			return orderNo;
		}
		/**
		 * @param orderNo the orderNo to set
		 */
		public void setOrderNo(String orderNo) {
			this.orderNo = orderNo;
		}
		/**
		 * @return the m6OrderNo
		 */
		public String getM6OrderNo() {
			return m6OrderNo;
		}
		/**
		 * @param orderNo the m6OrderNo to set
		 */
		public void setM6OrderNo(String orderNo) {
			m6OrderNo = orderNo;
		}
		/**
		 * @return the searchM6OrderNoStr
		 */
		public String getSearchM6OrderNoStr() {
			return searchM6OrderNoStr;
		}
		/**
		 * @param searchM6OrderNoStr the searchM6OrderNoStr to set
		 */
		public void setSearchM6OrderNoStr(String searchM6OrderNoStr) {
			this.searchM6OrderNoStr = searchM6OrderNoStr;
		}
		
		/**
		 * @return the remarks
		 */
		public String getRemarks() {
			return remarks;
		}
		/**
		 * @param remarks the remarks to set
		 */
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		/**
		 * @return the createdDate
		 */
		public String getCreatedDate() {
			return createdDate;
		}
		/**
		 * @param createdDate the createdDate to set
		 */
		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}
		/**
		 * @return the hdnOrderNo
		 */
		public String getHdnOrderNo() {
			return hdnOrderNo;
		}
		/**
		 * @param hdnOrderNo the hdnOrderNo to set
		 */
		public void setHdnOrderNo(String hdnOrderNo) {
			this.hdnOrderNo = hdnOrderNo;
		}
		public long getServiceDetailID() {
			return serviceDetailID;
		}
		public void setServiceDetailID(long serviceDetailID) {
			this.serviceDetailID = serviceDetailID;
		}
		public int getToComputeAlert() {
			return toComputeAlert;
		}
		public void setToComputeAlert(int toComputeAlert) {
			this.toComputeAlert = toComputeAlert;
		}
		public int getOldConfigValue() {
			return oldConfigValue;
		}
		public void setOldConfigValue(int oldConfigValue) {
			this.oldConfigValue = oldConfigValue;
		}
		public String getNewConfigAlert() {
			return newConfigAlert;
		}
		public void setNewConfigAlert(String newConfigAlert) {
			this.newConfigAlert = newConfigAlert;
		}
		public String getOldConfigAlert() {
			return oldConfigAlert;
		}
		public void setOldConfigAlert(String oldConfigAlert) {
			this.oldConfigAlert = oldConfigAlert;
		}
		public long getServiceProductID() {
			return serviceProductID;
		}
		public void setServiceProductID(long serviceProductID) {
			this.serviceProductID = serviceProductID;
		}
		public int getDummyLineItem() {
			return dummyLineItem;
		}
		public void setDummyLineItem(int dummyLineItem) {
			this.dummyLineItem = dummyLineItem;
		}
		public int getIsDummy() {
			return isDummy;
		}
		public void setIsDummy(int isDummy) {
			this.isDummy = isDummy;
		}
		public int getNonDummyLineItem() {
			return nonDummyLineItem;
		}
		public void setNonDummyLineItem(int nonDummyLineItem) {
			this.nonDummyLineItem = nonDummyLineItem;
		}
		public String getFxChargeRedirectionType() {
			return fxChargeRedirectionType;
		}
		public void setFxChargeRedirectionType(String fxChargeRedirectionType) {
			this.fxChargeRedirectionType = fxChargeRedirectionType;
		}
		public String getFxChargeRedirectionTypeCumulative() {
			return fxChargeRedirectionTypeCumulative;
		}
		public void setFxChargeRedirectionTypeCumulative(
				String fxChargeRedirectionTypeCumulative) {
			this.fxChargeRedirectionTypeCumulative = fxChargeRedirectionTypeCumulative;
		}
		public String getFli_po_disable() {
			return fli_po_disable;
		}
		public void setFli_po_disable(String fli_po_disable) {
			this.fli_po_disable = fli_po_disable;
		}
		


		
}
