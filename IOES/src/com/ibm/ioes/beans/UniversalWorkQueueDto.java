package com.ibm.ioes.beans;

import com.ibm.ioes.utilities.PagingSorting;

//Tag Name Resource Name  Date		CSR No			Description 
//[001]	   ROHIT VERMA	  8-Feb-11	00-05422		Add IsDemo Property for IsDemo Flag 
//[002]	   Vishwa	  7-Nov-11	00-05422		Adding New Columns 
//[003]	 Kalpana	    30-March-13	HYPR22032013003	COPC region wise change
//[004]   Neha Maheshwari    added service segment    NORTH STAR ACCOUNT TAGGING
public class UniversalWorkQueueDto {

	String orderNo=null;  
	String orderType=null;  
	String orderDate=null;  
	String customerName=null;  
	String orderSource=null;  
	String quoteNumber=null;
	String currentRole=null;
	String isBillingOrder=null;
	String orderStage=null;
	
	
	
	String fromDate;
	String toDate;
	private String searchCRMOrder;
	private String searchAccountNo;
	private String searchAccountName;
	private String searchfromDate;
	private String searchToDate;
	private String searchSource;
	private String searchQuoteNumber;
	private String searchCurrency;	
	private PagingSorting pagingSorting;
	private String searchOrderType;
	private String quoteNo;
	private String searchStageName;
	private String searchSourceName;
	private String searchCurrencyName;
	private String searchOrderTypes;
	private String regionName;	
	private String searchDemoType;
	private String searchPartyNumber;
	private String searchSubchangeType;
	private String searchCustomerSegmentName;
	private String searchRegionName;
    //[004] Start
	private String searchServiceSegmentName;
    //[004] End
	private String SearchIndustrySegmentName;
	private String searchOrderStageName;
	private String searchAmFromDate;
	private String searchAmToDate;
	private String searchPmFromDate;
	private String searchPmToDate;
	private String searchCopcFromDate;
	private String searchCopcToDate;
	//[001]	Start
	String isDemo=null;
	//	start anil
	private String queryName;
	private int queryNameID;
	private String queryFormValue;
	private int OrderNO;
	private int accountID;
	private String accountName;
	private int custLogicalSINo;
	private int logicalSINo;
	private int m6OrderNo;
	private int locNO;
	private int lineitemNo;
	private String stage;
	private String expectedValue;
	//end anil
	//[002] Start
	private String demoType;
	private String taskNo;
	private String taskStatus;
	private String partyNo;
    //[004] Start
	private String serviceSegment;
    //[004] End
	private String industrySegment;
	private String amApprovalDate;
	private String pmApprovalDate;
	private String copcApprovalDate;
	private String customerSegment;
	private String subChangeType;
	private int regionId;//added by kalpana for copc region change
//	[002] End
	/*vijay start
	 * add a variable for Total amount on po level
	 */
	private double poAmount;
	private String agingDaysHours;
	private String accMangerName;
	//vijay end
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}
	public String getIsDemo() {
		return isDemo;
	}
	public void setIsDemo(String isDemo) {
		this.isDemo = isDemo;
	}
	//[001]	End
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getOrderSource() {
		return orderSource;
	}
	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getQuoteNumber() {
		return quoteNumber;
	}
	public void setQuoteNumber(String quoteNumber) {
		this.quoteNumber = quoteNumber;
	}
	public String getCurrentRole() {
		return currentRole;
	}
	public void setCurrentRole(String currentRole) {
		this.currentRole = currentRole;
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
	public String getSearchToDate() {
		return searchToDate;
	}
	public void setSearchToDate(String searchToDate) {
		this.searchToDate = searchToDate;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public String getSearchOrderType() {
		return searchOrderType;
	}
	public void setSearchOrderType(String searchOrderType) {
		this.searchOrderType = searchOrderType;
	}
	public String getQuoteNo() {
		return quoteNo;
	}
	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}
	public String getSearchStageName() {
		return searchStageName;
	}
	public void setSearchStageName(String searchStageName) {
		this.searchStageName = searchStageName;
	}
	public String getSearchCurrencyName() {
		return searchCurrencyName;
	}
	public void setSearchCurrencyName(String searchCurrencyName) {
		this.searchCurrencyName = searchCurrencyName;
	}
	public String getSearchSourceName() {
		return searchSourceName;
	}
	public void setSearchSourceName(String searchSourceName) {
		this.searchSourceName = searchSourceName;
	}
	public String getSearchOrderTypes() {
		return searchOrderTypes;
	}
	public void setSearchOrderTypes(String searchOrderTypes) {
		this.searchOrderTypes = searchOrderTypes;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public int getCustLogicalSINo() {
		return custLogicalSINo;
	}
	public void setCustLogicalSINo(int custLogicalSINo) {
		this.custLogicalSINo = custLogicalSINo;
	}
	public int getLineitemNo() {
		return lineitemNo;
	}
	public void setLineitemNo(int lineitemNo) {
		this.lineitemNo = lineitemNo;
	}
	public int getLocNO() {
		return locNO;
	}
	public void setLocNO(int locNO) {
		this.locNO = locNO;
	}
	public int getLogicalSINo() {
		return logicalSINo;
	}
	public void setLogicalSINo(int logicalSINo) {
		this.logicalSINo = logicalSINo;
	}
	public int getM6OrderNo() {
		return m6OrderNo;
	}
	public void setM6OrderNo(int orderNo) {
		m6OrderNo = orderNo;
	}
	public int getOrderNO() {
		return OrderNO;
	}
	public void setOrderNO(int orderNO) {
		OrderNO = orderNO;
	}
	public String getQueryFormValue() {
		return queryFormValue;
	}
	public void setQueryFormValue(String queryFormValue) {
		this.queryFormValue = queryFormValue;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	public int getQueryNameID() {
		return queryNameID;
	}
	public void setQueryNameID(int queryNameID) {
		this.queryNameID = queryNameID;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
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
	public String getAmApprovalDate() {
		return amApprovalDate;
	}
	public void setAmApprovalDate(String amApprovalDate) {
		this.amApprovalDate = amApprovalDate;
	}
	public String getCopcApprovalDate() {
		return copcApprovalDate;
	}
	public void setCopcApprovalDate(String copcApprovalDate) {
		this.copcApprovalDate = copcApprovalDate;
	}
	public String getCustomerSegment() {
		return customerSegment;
	}
	public void setCustomerSegment(String customerSegment) {
		this.customerSegment = customerSegment;
	}
	public String getDemoType() {
		return demoType;
	}
	public void setDemoType(String demoType) {
		this.demoType = demoType;
	}
	public String getIndustrySegment() {
		return industrySegment;
	}
	public void setIndustrySegment(String industrySegment) {
		this.industrySegment = industrySegment;
	}
	public String getPartyNo() {
		return partyNo;
	}
	public void setPartyNo(String partyNo) {
		this.partyNo = partyNo;
	}
	public String getPmApprovalDate() {
		return pmApprovalDate;
	}
	public void setPmApprovalDate(String pmApprovalDate) {
		this.pmApprovalDate = pmApprovalDate;
	}
	public String getTaskNo() {
		return taskNo;
	}
	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}
	public String getTaskStatus() {
		return taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getSubChangeType() {
		return subChangeType;
	}
	public void setSubChangeType(String subChangeType) {
		this.subChangeType = subChangeType;
	}
	public String getSearchSubchangeType() {
		return searchSubchangeType;
	}
	public void setSearchSubchangeType(String searchSubchangeType) {
		this.searchSubchangeType = searchSubchangeType;
	}
	public String getSearchCustomerSegmentName() {
		return searchCustomerSegmentName;
	}
	public void setSearchCustomerSegmentName(String searchCustomerSegmentName) {
		this.searchCustomerSegmentName = searchCustomerSegmentName;
	}
	public String getSearchRegionName() {
		return searchRegionName;
	}
	public void setSearchRegionName(String searchRegionName) {
		this.searchRegionName = searchRegionName;
	}
	public String getSearchIndustrySegmentName() {
		return SearchIndustrySegmentName;
	}
	public void setSearchIndustrySegmentName(String searchIndustrySegmentName) {
		SearchIndustrySegmentName = searchIndustrySegmentName;
	}
	public String getSearchOrderStageName() {
		return searchOrderStageName;
	}
	public void setSearchOrderStageName(String searchOrderStageName) {
		this.searchOrderStageName = searchOrderStageName;
	}
	public String getSearchDemoType() {
		return searchDemoType;
	}
	public void setSearchDemoType(String searchDemoType) {
		this.searchDemoType = searchDemoType;
	}
	public String getSearchPartyNumber() {
		return searchPartyNumber;
	}
	public void setSearchPartyNumber(String searchPartyNumber) {
		this.searchPartyNumber = searchPartyNumber;
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
	public double getPoAmount() {
		return poAmount;
	}
	public void setPoAmount(double poAmount) {
		this.poAmount = poAmount;
	}
	public String getAccMangerName() {
		return accMangerName;
	}
	public void setAccMangerName(String accMangerName) {
		this.accMangerName = accMangerName;
	}
	public String getAgingDaysHours() {
		return agingDaysHours;
	}
	public void setAgingDaysHours(String agingDaysHours) {
		this.agingDaysHours = agingDaysHours;
	}
	//[004] Start
	public String getServiceSegment() {
		return serviceSegment;
	}
	public void setServiceSegment(String serviceSegment) {
		this.serviceSegment = serviceSegment;
	}
	//[004] End
	//[004] Start
	public String getSearchServiceSegmentName(){
		return searchServiceSegmentName;
	}
	public void setSearchServiceSegmentName(String searchServiceSegmentName){
		this.searchServiceSegmentName = searchServiceSegmentName;
	}
    //[004] End
}
