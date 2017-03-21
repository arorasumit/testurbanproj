//[0001]   Neha Maheshwari    added service segment    NORTH STAR ACCOUNT TAGGING
package com.ibm.ioes.forms;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.utilities.PagingSorting;

public class UniversalWorkQueueFormBean extends ActionForm{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	long checkedOrderNo=0;
	private String searchTaskId;
	private String searchTaskOwner; 
	private String currentRole;
	private String isBillingOrder="0";
	
	//ramana 
	private String toDate;
	
	
	private String fromDate;
	private String orderType;
	private String searchCRMOrder;
	private String searchAccountNo;
	private String searchAccountName;
	private String searchfromDate;
	private String searchToDate;
	private String searchSource;
	private String searchQuoteNumber;
	private String searchCurrency;
	private String searchOrderType;
	private String searchStageName;
	private String searchRegion;
	private String searchOrderStage;
	private String searchDemoType;
	private String searchTaskStatus;
	private String searchPartyNumber;
    //[0001] Start
	private String searchServiceSegment;
    //[0001] End
	private String searchIndustrySegment;
	private String searchAmFromDate;
	private String searchAmToDate;
	private String searchPmFromDate;
	private String searchPmToDate;
	private String searchCopcFromDate;
	private String searchCopcToDate;
	private String searchCustomerSegment;
	private String orderStage;
	private ArrayList orderList=null;
	PagingSorting pagingSorting = new PagingSorting();
	private ArrayList universalWorkQueueList=null;
	private ArrayList billingWorkQueueList=null;
	private String searchSubChangeType;
	
	//Ramana
	private int ponumber;
	private ArrayList queryOptionList;
	private int queryID;
	private String queryName;
	private String queryOptions;
	private String valueID;
	private String methodName;
	private int selectedQueryOptions;
	private ArrayList queryValuesList;
	private String expectedValue;
	private String toExcel;
	
	private String projectManagerAssigned;
	private String projectManagerSearched;
	public String getProjectManagerAssigned() {
		return projectManagerAssigned;
	}

	public void setProjectManagerAssigned(String projectManagerAssigned) {
		this.projectManagerAssigned = projectManagerAssigned;
	}

	public String getProjectManagerSearched() {
		return projectManagerSearched;
	}

	public void setProjectManagerSearched(String projectManagerSearched) {
		this.projectManagerSearched = projectManagerSearched;
	}

	public String getToExcel() {
		return toExcel;
	}

	public void setToExcel(String toExcel) {
		this.toExcel = toExcel;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public int getQueryID() {
		return queryID;
	}

	public void setQueryID(int queryID) {
		this.queryID = queryID;
	}

	public String getQueryName() {
		return queryName;
	}

	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}

	public ArrayList getQueryOptionList() {
		return queryOptionList;
	}

	public void setQueryOptionList(ArrayList queryOptionList) {
		this.queryOptionList = queryOptionList;
	}

	public String getQueryOptions() {
		return queryOptions;
	}

	public void setQueryOptions(String queryOptions) {
		this.queryOptions = queryOptions;
	}

	public ArrayList getQueryValuesList() {
		return queryValuesList;
	}

	public void setQueryValuesList(ArrayList queryValuesList) {
		this.queryValuesList = queryValuesList;
	}

	public int getSelectedQueryOptions() {
		return selectedQueryOptions;
	}

	public void setSelectedQueryOptions(int selectedQueryOptions) {
		this.selectedQueryOptions = selectedQueryOptions;
	}

	public String getValueID() {
		return valueID;
	}

	public void setValueID(String valueID) {
		this.valueID = valueID;
	}

	public int getPonumber() {
		return ponumber;
	}

	public void setPonumber(int ponumber) {
		this.ponumber = ponumber;
	}

	public ArrayList getOrderList() {
		return orderList;
	}

	public void setOrderList(ArrayList orderList) {
		this.orderList = orderList;
	}

	public String getCurrentRole() {
		return currentRole;
	}

	public void setCurrentRole(String currentRole) {
		this.currentRole = currentRole;
	}

	public long getCheckedOrderNo() {
		return checkedOrderNo;
	}

	public void setCheckedOrderNo(long checkedOrderNo) {
		this.checkedOrderNo = checkedOrderNo;
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

	public String getIsBillingOrder() {
		return isBillingOrder;
	}

	public void setIsBillingOrder(String isBillingOrder) {
		this.isBillingOrder = isBillingOrder;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getSearchAccountName() {
		return searchAccountName;
	}

	public void setSearchAccountName(String searchAccountName) {
		this.searchAccountName = searchAccountName;
	}

	public String getSearchAccountNo() {
		return searchAccountNo;
	}

	public void setSearchAccountNo(String searchAccountNo) {
		this.searchAccountNo = searchAccountNo;
	}

	public String getSearchCRMOrder() {
		return searchCRMOrder;
	}

	public void setSearchCRMOrder(String searchCRMOrder) {
		this.searchCRMOrder = searchCRMOrder;
	}

	public String getSearchCurrency() {
		return searchCurrency;
	}

	public void setSearchCurrency(String searchCurrency) {
		this.searchCurrency = searchCurrency;
	}

	public String getSearchfromDate() {
		return searchfromDate;
	}

	public void setSearchfromDate(String searchfromDate) {
		this.searchfromDate = searchfromDate;
	}

	public String getSearchOrderType() {
		return searchOrderType;
	}

	public void setSearchOrderType(String searchOrderType) {
		this.searchOrderType = searchOrderType;
	}

	public String getSearchQuoteNumber() {
		return searchQuoteNumber;
	}

	public void setSearchQuoteNumber(String searchQuoteNumber) {
		this.searchQuoteNumber = searchQuoteNumber;
	}

	public String getSearchSource() {
		return searchSource;
	}

	public void setSearchSource(String searchSource) {
		this.searchSource = searchSource;
	}

	public String getSearchStageName() {
		return searchStageName;
	}

	public void setSearchStageName(String searchStageName) {
		this.searchStageName = searchStageName;
	}

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public String getSearchToDate() {
		return searchToDate;
	}

	public void setSearchToDate(String searchToDate) {
		this.searchToDate = searchToDate;
	}

	public ArrayList getUniversalWorkQueueList() {
		return universalWorkQueueList;
	}

	public void setUniversalWorkQueueList(ArrayList universalWorkQueueList) {
		this.universalWorkQueueList = universalWorkQueueList;
	}

	public ArrayList getBillingWorkQueueList() {
		return billingWorkQueueList;
	}

	public void setBillingWorkQueueList(ArrayList billingWorkQueueList) {
		this.billingWorkQueueList = billingWorkQueueList;
	}

	public String getExpectedValue() {
		return expectedValue;
	}

	public void setExpectedValue(String expectedValue) {
		this.expectedValue = expectedValue;
	}

	public String getOrderStage() {
		return orderStage;
	}

	public void setOrderStage(String orderStage) {
		this.orderStage = orderStage;
	}

	public String getSearchSubChangeType() {
		return searchSubChangeType;
	}

	public void setSearchSubChangeType(String searchSubChangeType) {
		this.searchSubChangeType = searchSubChangeType;
	}

	public String getSearchAmFromDate() {
		return searchAmFromDate;
	}

	public void setSearchAmFromDate(String searchAmFromDate) {
		this.searchAmFromDate = searchAmFromDate;
	}

	public String getSearchAmToDate() {
		return searchAmToDate;
	}

	public void setSearchAmToDate(String searchAmToDate) {
		this.searchAmToDate = searchAmToDate;
	}

	public String getSearchCopcFromDate() {
		return searchCopcFromDate;
	}

	public void setSearchCopcFromDate(String searchCopcFromDate) {
		this.searchCopcFromDate = searchCopcFromDate;
	}

	public String getSearchCopcToDate() {
		return searchCopcToDate;
	}

	public void setSearchCopcToDate(String searchCopcToDate) {
		this.searchCopcToDate = searchCopcToDate;
	}

	public String getSearchDemoType() {
		return searchDemoType;
	}

	public void setSearchDemoType(String searchDemoType) {
		this.searchDemoType = searchDemoType;
	}

	public String getSearchIndustrySegment() {
		return searchIndustrySegment;
	}

	public void setSearchIndustrySegment(String searchIndustrySegment) {
		this.searchIndustrySegment = searchIndustrySegment;
	}

	public String getSearchOrderStage() {
		return searchOrderStage;
	}

	public void setSearchOrderStage(String searchOrderStage) {
		this.searchOrderStage = searchOrderStage;
	}

	public String getSearchPartyNumber() {
		return searchPartyNumber;
	}

	public void setSearchPartyNumber(String searchPartyNumber) {
		this.searchPartyNumber = searchPartyNumber;
	}

	public String getSearchPmFromDate() {
		return searchPmFromDate;
	}

	public void setSearchPmFromDate(String searchPmFromDate) {
		this.searchPmFromDate = searchPmFromDate;
	}

	public String getSearchPmToDate() {
		return searchPmToDate;
	}

	public void setSearchPmToDate(String searchPmToDate) {
		this.searchPmToDate = searchPmToDate;
	}

	public String getSearchRegion() {
		return searchRegion;
	}

	public void setSearchRegion(String searchRegion) {
		this.searchRegion = searchRegion;
	}

	public String getSearchTaskStatus() {
		return searchTaskStatus;
	}

	public void setSearchTaskStatus(String searchTaskStatus) {
		this.searchTaskStatus = searchTaskStatus;
	}

	public String getSearchCustomerSegment() {
		return searchCustomerSegment;
	}

	public void setSearchCustomerSegment(String searchCustomerSegment) {
		this.searchCustomerSegment = searchCustomerSegment;
	}
	 //[0001] Start
	public String getSearchServiceSegment() {
		return searchServiceSegment;
	}

	public void setSearchServiceSegment(String searchServiceSegment) {
		this.searchServiceSegment = searchServiceSegment;
	}
    //[0001] End
	

}
