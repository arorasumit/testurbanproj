package com.ibm.ioes.npd.beans;

import java.util.ArrayList;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.npd.utilities.PagingSorting;

public class HolidayMasterBean extends ActionForm
{
	private String holidayID;
	
	private String holidayName;
	
	private String holidayDate;
	
	private PagingSorting pagingSorting;
	
	private ArrayList holidayDetailList;
	
	private String searchHolidayName;
	
	private String dateFilter = null;

	private String fromDate = null;

	private String toDate = null;
	
	private int actionType;
	
	public String getDateFilter() {
		return dateFilter;
	}

	public void setDateFilter(String dateFilter) {
		this.dateFilter = dateFilter;
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

	public HolidayMasterBean() 
	{
		pagingSorting = new PagingSorting();
	}

	public String getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(String holidayDate) {
		this.holidayDate = holidayDate;
	}

	public ArrayList getHolidayDetailList() {
		return holidayDetailList;
	}

	public void setHolidayDetailList(ArrayList holidayDetailList) {
		this.holidayDetailList = holidayDetailList;
	}

	public String getHolidayName() {
		return holidayName;
	}

	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}

	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}

	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}

	public String getHolidayID() {
		return holidayID;
	}

	public void setHolidayID(String holidayID) {
		this.holidayID = holidayID;
	}

	public String getSearchHolidayName() {
		return searchHolidayName;
	}

	public void setSearchHolidayName(String searchHolidayName) {
		this.searchHolidayName = searchHolidayName;
	}

	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}
}
