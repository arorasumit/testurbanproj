package com.ibm.ioes.forms;

import java.util.ArrayList;

import com.ibm.ioes.beans.DefaultBean;
import com.ibm.ioes.beans.ViewOrderDto;
import com.ibm.ioes.utilities.PagingSorting;

public class LSICancellationDto extends DefaultBean{
	private String searchCRMOrder;
	private String searchAccountNo;
	
	private String searchFromOrderDate;
	private String searchToOrderDate;
	private String searchAccountName;
	private String searchServiceNo;
	PagingSorting pagingSorting = new PagingSorting();

	private String  searchLSI;
	private String ordType;
	private String ordChangeType;
	private String ordSubChangeType;
	private String ordStage;
	private String search_ord_create_date;
	private String serviceName;
	private String productName;
	private String subProductName;
	private String searchLSIScenario;
	private int cancellationReasonId;
	private int cancellationReasonName;
	private String cancellationRemarks;
	private ArrayList<LSICancellationDto> eligibleLSIForCancelList=null;
	//private ArrayList eligibleLSIForCancelList=null;
	private ArrayList<LSICancellationDto> LSICancellationDtolist=null;
	private ArrayList<ViewOrderDto>cancelllationReasonList;
	private int maxPageNo;

	
	
	public LSICancellationDto(int pageSize) {
		pagingSorting.setPageRecords(pageSize);
	}
	public LSICancellationDto() {
	}
	public int getMaxPageNo() {
		return maxPageNo;
	}
	public void setMaxPageNo(int maxPageNo) {
		this.maxPageNo = maxPageNo;
	}
	public ArrayList<LSICancellationDto> getEligibleLSIForCancelList() {
		return eligibleLSIForCancelList;
	}
	public void setEligibleLSIForCancelList(
			ArrayList<LSICancellationDto> eligibleLSIForCancelList) {
		this.eligibleLSIForCancelList = eligibleLSIForCancelList;
	}
	public String getSearchCRMOrder() {
		return searchCRMOrder;
	}
	public void setSearchCRMOrder(String searchCRMOrder) {
		this.searchCRMOrder = searchCRMOrder;
	}
	public String getSearchAccountNo() {
		return searchAccountNo;
	}
	public void setSearchAccountNo(String searchAccountNo) {
		this.searchAccountNo = searchAccountNo;
	}
	public String getSearchFromOrderDate() {
		return searchFromOrderDate;
	}
	public void setSearchFromOrderDate(String searchFromOrderDate) {
		this.searchFromOrderDate = searchFromOrderDate;
	}
	public String getSearchToOrderDate() {
		return searchToOrderDate;
	}
	public void setSearchToOrderDate(String searchToOrderDate) {
		this.searchToOrderDate = searchToOrderDate;
	}
	public String getSearchAccountName() {
		return searchAccountName;
	}
	public void setSearchAccountName(String searchAccountName) {
		this.searchAccountName = searchAccountName;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public String getSearchLSI() {
		return searchLSI;
	}
	public void setSearchLSI(String searchLSI) {
		this.searchLSI = searchLSI;
	}
	public String getOrdType() {
		return ordType;
	}
	public void setOrdType(String ordType) {
		this.ordType = ordType;
	}
	public String getOrdChangeType() {
		return ordChangeType;
	}
	public void setOrdChangeType(String ordChangeType) {
		this.ordChangeType = ordChangeType;
	}
	public String getOrdSubChangeType() {
		return ordSubChangeType;
	}
	public void setOrdSubChangeType(String ordSubChangeType) {
		this.ordSubChangeType = ordSubChangeType;
	}
	public String getOrdStage() {
		return ordStage;
	}
	public void setOrdStage(String ordStage) {
		this.ordStage = ordStage;
	}
	public String getSearch_ord_create_date() {
		return search_ord_create_date;
	}
	public void setSearch_ord_create_date(String search_ord_create_date) {
		this.search_ord_create_date = search_ord_create_date;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSubProductName() {
		return subProductName;
	}
	public void setSubProductName(String subProductName) {
		this.subProductName = subProductName;
	}
	public String getSearchServiceNo() {
		return searchServiceNo;
	}
	public void setSearchServiceNo(String searchServiceNo) {
		this.searchServiceNo = searchServiceNo;
	}
	public String getSearchLSIScenario() {
		return searchLSIScenario;
	}
	public void setSearchLSIScenario(String searchLSIScenario) {
		this.searchLSIScenario = searchLSIScenario;
	}
	
	public int getCancellationReasonId() {
		return cancellationReasonId;
	}
	public void setCancellationReasonId(int cancellationReasonId) {
		this.cancellationReasonId = cancellationReasonId;
	}
	public String getCancellationRemarks() {
		return cancellationRemarks;
	}
	public void setCancellationRemarks(String cancellationRemarks) {
		this.cancellationRemarks = cancellationRemarks;
	}
	public ArrayList<LSICancellationDto> getLSICancellationDtolist() {
		return LSICancellationDtolist;
	}
	public void setLSICancellationDtolist(
			ArrayList<LSICancellationDto> lSICancellationDtolist) {
		LSICancellationDtolist = lSICancellationDtolist;
	}
	public ArrayList<ViewOrderDto> getCancelllationReasonList() {
		return cancelllationReasonList;
	}
	public void setCancelllationReasonList(ArrayList<ViewOrderDto> cancelllationReasonList) {
		this.cancelllationReasonList = cancelllationReasonList;
	}
	public int getCancellationReasonName() {
		return cancellationReasonName;
	}
	public void setCancellationReasonName(int cancellationReasonName) {
		this.cancellationReasonName = cancellationReasonName;
	}
}
