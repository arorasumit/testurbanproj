//Tag Name Resource Name  Date		CSR No			Description
//[003]	 Gunjan Singla	25-Jun-14	CSR_20140526_R1_020159     Order Cancellation Post Publish
package com.ibm.ioes.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.forms.LSICancellationDto;
import com.ibm.ioes.utilities.PagingSorting;

public class DefaultBean extends ActionForm
{
	private String userId;
	
	private String searchCRMOrder;
	private String searchAccountNo;
	private String searchOrderType;
	private String searchfromDate;
	private String searchToDate;
	private String searchSourceName;
	private String searchQuoteNumber;
	private String searchCurrency;
	private String searchCurrencyName;
	private String searchStageName;
	private String searchSource;
	private String searchAccountName;
	PagingSorting pagingSorting = new PagingSorting();
//	manisha start
	private String searchSRNO;
	private String  searchLSI;
	private ArrayList pDorderList=null;
	private String  searchdisc_date;
	private String  searchfromdisc_date;
	private String  searchTodisc_date;
	private String orderOriginType;
	private String orderSubChangeType;
	//[003] start
	private String searchServiceNo;
	//private ArrayList eligibleLSIForCancelList=null;
	private String searchFromOrd_Date;
	private String searchToOrd_Date;
	private String searchLSIScenario;
	private ArrayList<LSICancellationDto> LSICancellationDtolist=null;
	//[003] end






	private String jspRendered;
	public ArrayList<LSICancellationDto> getLSICancellationDtolist() {
		return LSICancellationDtolist;
	}
	public void setLSICancellationDtolist(
			ArrayList<LSICancellationDto> lSICancellationDtolist) {
		LSICancellationDtolist = lSICancellationDtolist;
	}

	
	//added by mohit
	private String hiddenInterfaceName;
	//manisha end
	
	/*Vijay
	 * Add a variable for Demo order check 
	 */
	private String isDemoOrder;
	
	
	private ArrayList orderList=null;
	

	public ArrayList getOrderList() {
		return orderList;
	}

	public void setOrderList(ArrayList orderList) {
		this.orderList = orderList;
	}

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
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

	public String getSearchCurrencyName() {
		return searchCurrencyName;
	}

	public void setSearchCurrencyName(String searchCurrencyName) {
		this.searchCurrencyName = searchCurrencyName;
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

	public String getSearchSourceName() {
		return searchSourceName;
	}

	public void setSearchSourceName(String searchSourceName) {
		this.searchSourceName = searchSourceName;
	}

	public String getSearchStageName() {
		return searchStageName;
	}

	public void setSearchStageName(String searchStageName) {
		this.searchStageName = searchStageName;
	}

	public String getSearchToDate() {
		return searchToDate;
	}

	public void setSearchToDate(String searchToDate) {
		this.searchToDate = searchToDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSearchLSI() {
		return searchLSI;
	}

	public void setSearchLSI(String searchLSI) {
		this.searchLSI = searchLSI;
	}

	public String getSearchSRNO() {
		return searchSRNO;
	}

	public void setSearchSRNO(String searchSRNO) {
		this.searchSRNO = searchSRNO;
	}

	public ArrayList getPDorderList() {
		return pDorderList;
	}

	public void setPDorderList(ArrayList dorderList) {
		pDorderList = dorderList;
	}

	public String getSearchdisc_date() {
		return searchdisc_date;
	}

	public void setSearchdisc_date(String searchdisc_date) {
		this.searchdisc_date = searchdisc_date;
	}

	public String getSearchfromdisc_date() {
		return searchfromdisc_date;
	}

	public void setSearchfromdisc_date(String searchfromdisc_date) {
		this.searchfromdisc_date = searchfromdisc_date;
	}

	public String getSearchTodisc_date() {
		return searchTodisc_date;
	}

	public void setSearchTodisc_date(String searchTodisc_date) {
		this.searchTodisc_date = searchTodisc_date;
	}

	public String getHiddenInterfaceName() {
		return hiddenInterfaceName;
	}

	public void setHiddenInterfaceName(String hiddenInterfaceName) {
		this.hiddenInterfaceName = hiddenInterfaceName;
	}

	public String getIsDemoOrder() {
		return isDemoOrder;
	}

	public void setIsDemoOrder(String isDemoOrder) {
		this.isDemoOrder = isDemoOrder;
	}

	public String getOrderOriginType() {
		return orderOriginType;
	}

	public void setOrderOriginType(String orderOriginType) {
		this.orderOriginType = orderOriginType;
	}

	public String getOrderSubChangeType() {
		return orderSubChangeType;
	}

	public void setOrderSubChangeType(String orderSubChangeType) {
		this.orderSubChangeType = orderSubChangeType;
	}
	//[003] start
	public String getSearchServiceNo() {
		return searchServiceNo;
	}

	public void setSearchServiceNo(String searchServiceNo) {
		this.searchServiceNo = searchServiceNo;
	}
	/*public ArrayList getEligibleLSIForCancelList() {
		return eligibleLSIForCancelList;
	}

	public void setEligibleLSIForCancelList(ArrayList eligibleLSIForCancelList) {
		this.eligibleLSIForCancelList = eligibleLSIForCancelList;
	}*/
	public String getSearchFromOrd_Date() {
		return searchFromOrd_Date;
	}

	public void setSearchFromOrd_Date(String searchFromOrd_Date) {
		this.searchFromOrd_Date = searchFromOrd_Date;
	}

	public String getSearchToOrd_Date() {
		return searchToOrd_Date;
	}

	public void setSearchToOrd_Date(String searchToOrd_Date) {
		this.searchToOrd_Date = searchToOrd_Date;
	}

	public String getSearchLSIScenario() {
		return searchLSIScenario;
	}

	public void setSearchLSIScenario(String searchLSIScenario) {
		this.searchLSIScenario = searchLSIScenario;
	}

	public String getJspRendered() {
		return jspRendered;
	}

	public void setJspRendered(String jspRendered) {
		this.jspRendered = jspRendered;
	}
}
