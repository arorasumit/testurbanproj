package com.ibm.ioes.npd.hibernate.beans;

import com.ibm.ioes.npd.utilities.PagingSorting;

public class HolidayMasterDto implements java.io.Serializable
{
	PagingSorting pagingSorting=null;
	
	private String holidayName;
	
	private String holidayDate;
	
	private int holidayID;
	
	private String searchHolidayName;
	
	private String dateFilter=null;
	
	private String fromDate=null;
	
	private String toDate=null;

	private int actionType;
	
	public int getActionType() {
		return actionType;
	}

	public void setActionType(int actionType) {
		this.actionType = actionType;
	}

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

	public String getSearchHolidayName() {
		return searchHolidayName;
	}

	public void setSearchHolidayName(String searchHolidayName) {
		this.searchHolidayName = searchHolidayName;
	}

	public String getHolidayDate() {
		return holidayDate;
	}

	public void setHolidayDate(String holidayDate) {
		this.holidayDate = holidayDate;
	}

	public int getHolidayID() {
		return holidayID;
	}

	public void setHolidayID(int holidayID) {
		this.holidayID = holidayID;
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
}
