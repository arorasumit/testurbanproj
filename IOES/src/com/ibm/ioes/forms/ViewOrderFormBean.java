//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Manisha Garg	22-jun-11	00-05422		for Searching LSI in Billing Trigger Page for New and Change Order
package com.ibm.ioes.forms;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.utilities.PagingSorting;

public class ViewOrderFormBean extends ActionForm {

	private String searchTaskId;
	private String searchTaskOwner;
	private String searchTaskOwnerId;
	private String createdBy;
	private String createdDate;
	private String notesType;
	private String notesMeaning;
	private String orderNo;
	private String rejectionTo;
	private ArrayList lstTo;
	private String actionRemraks;
	private String message;
	private int searchLogicalSI;
	private String isOrderPublished;
	private String isBillingTriggerReady;
	private String billingOrderNo;
	private String strBillingTrigger;
	private String projectManager;
	private String region;
	private String zone;
	private String regionId;
	private String zoneId = "0";
	private String poNumber;
	private int hdnPOCount;
	private int hdnPOnumbers;
	private int attCount;
	private String accountID;
	private String LSIResult;
	private String remarks;
	//001 START 
	private String searchLSI;
	private String disconnectionDate;
	private String searchCustomerLSI;
	private String sortLineItems=null;
	//001 END
	ArrayList<ChargeSummaryServiceDto> services = null;
	ArrayList<ChargeSummaryProductCatelog> productCatelogs = null;
	HashMap<String,ArrayList<ChargeSummaryProductCatelog>> map_serviceId_prdCatelogs = null;
	HashMap<String,ArrayList<ChargeSummaryChargeSection>> map_serviceproductId_prdCatelogs_charges = null;
	PagingSorting pagingSorting =new PagingSorting();
	HashMap<String,ArrayList<ChargeSummaryComponentSection>> map_serviceproductId_prdCatelogs_components = null;
	
	private String searchLineTriggerStatus = null; 

	public String getStrBillingTrigger() {
		return strBillingTrigger;
	}
	public void setStrBillingTrigger(String strBillingTrigger) {
		this.strBillingTrigger = strBillingTrigger;
	}
	public String getBillingOrderNo() {
		return billingOrderNo;
	}
	public void setBillingOrderNo(String billingOrderNo) {
		this.billingOrderNo = billingOrderNo;
	}
	public String getActionRemraks() {
		return actionRemraks;
	}
	public void setActionRemraks(String actionRemraks) {
		this.actionRemraks = actionRemraks;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getSearchTaskId() {
		return searchTaskId;
	}
	public void setSearchTaskId(String searchTaskId) {
		this.searchTaskId = searchTaskId;
	}
	public String getSearchTaskOwner() {
		return searchTaskOwner;
	}
	public void setSearchTaskOwner(String searchTaskOwner) {
		this.searchTaskOwner = searchTaskOwner;
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
	public String getNotesMeaning() {
		return notesMeaning;
	}
	public void setNotesMeaning(String notesMeaning) {
		this.notesMeaning = notesMeaning;
	}
	public String getNotesType() {
		return notesType;
	}
	public void setNotesType(String notesType) {
		this.notesType = notesType;
	}
	public ArrayList getLstTo() {
		return lstTo;
	}
	public void setLstTo(ArrayList lstTo) {
		this.lstTo = lstTo;
	}
	public String getRejectionTo() {
		return rejectionTo;
	}
	public void setRejectionTo(String rejectionTo) {
		this.rejectionTo = rejectionTo;
	}
	public String getSearchTaskOwnerId() {
		return searchTaskOwnerId;
	}
	public void setSearchTaskOwnerId(String searchTaskOwnerId) {
		this.searchTaskOwnerId = searchTaskOwnerId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getIsOrderPublished() {
		return isOrderPublished;
	}
	public void setIsOrderPublished(String isOrderPublished) {
		this.isOrderPublished = isOrderPublished;
	}
	public String getIsBillingTriggerReady() {
		return isBillingTriggerReady;
	}
	public void setIsBillingTriggerReady(String isBillingTriggerReady) {
		this.isBillingTriggerReady = isBillingTriggerReady;
	} 
		public ArrayList<ChargeSummaryProductCatelog> getProductCatelogs() {
		return productCatelogs;
	}
	public void setProductCatelogs(
			ArrayList<ChargeSummaryProductCatelog> productCatelogs) {
		this.productCatelogs = productCatelogs;
	}
	public ArrayList<ChargeSummaryServiceDto> getServices() {
		return services;
	}
	public void setServices(ArrayList<ChargeSummaryServiceDto> services) {
		this.services = services;
	}
	public HashMap<String, ArrayList<ChargeSummaryProductCatelog>> getMap_serviceId_prdCatelogs() {
		return map_serviceId_prdCatelogs;
	}
	public void setMap_serviceId_prdCaelogs(
			HashMap<String, ArrayList<ChargeSummaryProductCatelog>> map_serviceId_prdCatelogs) {
		this.map_serviceId_prdCatelogs = map_serviceId_prdCatelogs;
	}
	public String getProjectManager() {
		return projectManager;
	}
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getRegionId() {
		return regionId;
	}
	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getZoneId() {
		return zoneId;
	}
	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}
	public int getHdnPOCount() {
		return hdnPOCount;
	}
	public void setHdnPOCount(int hdnPOCount) {
		this.hdnPOCount = hdnPOCount;
	}
	public int getHdnPOnumbers() {
		return hdnPOnumbers;
	}
	public void setHdnPOnumbers(int hdnPOnumbers) {
		this.hdnPOnumbers = hdnPOnumbers;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public int getAttCount() {
		return attCount;
	}
	public void setAttCount(int attCount) {
		this.attCount = attCount;
	}
	public String getAccountID() {
		return accountID;
	}
	public void setAccountID(String accountID) {
		this.accountID = accountID;
	} 
//	001 START
	public String getSearchLSI() {
		return searchLSI;
	}
	public void setSearchLSI(String searchLSI) {
		this.searchLSI = searchLSI;
	}
	public int getSearchLogicalSI() {
		return searchLogicalSI;
	}
//	001 START
	public void setSearchLogicalSI(int searchLogicalSI) {
		this.searchLogicalSI = searchLogicalSI;
	}
	public String getLSIResult() {
		return LSIResult;
	}
	public void setLSIResult(String result) {
		LSIResult = result;
	}
	public String getSearchCustomerLSI() {
		return searchCustomerLSI;
	}
	public void setSearchCustomerLSI(String searchCustomerLSI) {
		this.searchCustomerLSI = searchCustomerLSI;
	}
	public String getDisconnectionDate() {
		return disconnectionDate;
	}
	public void setDisconnectionDate(String disconnectionDate) {
		this.disconnectionDate = disconnectionDate;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
public HashMap<String, ArrayList<ChargeSummaryChargeSection>> getMap_serviceproductId_prdCatelogs_charges() {
		return map_serviceproductId_prdCatelogs_charges;
	}
	public void setMap_serviceproductId_prdCatelogs_charges(
			HashMap<String, ArrayList<ChargeSummaryChargeSection>> map_serviceproductId_prdCatelogs_charges) {
		this.map_serviceproductId_prdCatelogs_charges = map_serviceproductId_prdCatelogs_charges;
	}
public String getSortLineItems() {
		return sortLineItems;
	}
	public void setSortLineItems(String sortLineItems) {
		this.sortLineItems = sortLineItems;
	}
	
	public void setMap_serviceId_prdCatelogs(
			HashMap<String, ArrayList<ChargeSummaryProductCatelog>> map_serviceId_prdCatelogs) {
		this.map_serviceId_prdCatelogs = map_serviceId_prdCatelogs;
	}
	public String getSearchLineTriggerStatus() {
		return searchLineTriggerStatus;
	}
	public void setSearchLineTriggerStatus(String searchLineTriggerStatus) {
		this.searchLineTriggerStatus = searchLineTriggerStatus;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public HashMap<String, ArrayList<ChargeSummaryComponentSection>> getMap_serviceproductId_prdCatelogs_components() {
		return map_serviceproductId_prdCatelogs_components;
	}
	public void setMap_serviceproductId_prdCatelogs_components(
			HashMap<String, ArrayList<ChargeSummaryComponentSection>> map_serviceproductId_prdCatelogs_components) {
		this.map_serviceproductId_prdCatelogs_components = map_serviceproductId_prdCatelogs_components;
	}
}
