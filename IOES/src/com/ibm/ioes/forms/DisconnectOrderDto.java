//Tag Name Resource Name  Date		CSR No			Description
//[001]	 Manisha Garg	3-Sep-12	00-05422		Created for Auto Disconnection Order
//[002]  Priya Gupta	31-Jul-15					Added a column ordersource and added a variable for enable/disable checkbox
package com.ibm.ioes.forms;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.ibm.ioes.utilities.PagingSorting;

public class DisconnectOrderDto {
	
private String cancelorderstatus;
private int orderNumber;
private String approvestatus;
private String searchCRMOrder;
private String searchAccountNo;
private String searchAccountName;
private String searchfromDate;
private String searchToDate;
private String searchSRNO;
private String searchLSI;

	private String region;
	private String serviceTypeName;
	// [002]
	private String orderSource;
	//priya
	private String oldModifiedDate;

	private int successcount;
	private int failurecount;
	private String successorderList;
	private String failureorderList;
	private String srno;
	private String disconnection_login_date;
	private String disconnection_rec_date;
	private String disconnection_date_bycust;
	private String disconnection_date_with_np;
	private String intimatedate;

private Timestamp crm_disconnection_login_date;
private Timestamp crm_disconnection_rec_date;
private Timestamp crm_disconnection_date_bycust;
private Timestamp crm_disconnection_date_with_np;
private Timestamp crm_intimatedate;
private Timestamp crm_created_date;
private Timestamp crm_srDate;
private String search_from_dis_date;
private String search_to_dis_date;
private int lsi;
private int cancel_flag;
private String cancel_reason;
private String srDate;
private String created_date;
private int created_by;
private int processing_status;
PagingSorting pagingSorting = new PagingSorting();	
private int last_updated_by;
private String last_updated_date;
private String sr_raised_by;
private String search_dis_date;
private String order_created_by;
private String accountno;
private String accountname;
private String am_emailid;
private String pm_emailid;
private String orderno;
private String logical_si_no;
private String Demo_end_date;
private String ColMgr_emailid;
private String actmgrid;
private String bulkOriginType;
private String bulkSubchangetype;
	// [002] starts
	private String checkboxstatus;

	public String getCheckboxstatus() {
		return checkboxstatus;
	}

	public void setCheckboxstatus(String checkboxstatus) {
		this.checkboxstatus = checkboxstatus;
	}

	// [002] ends
	public String getBulkOriginType() {
		return bulkOriginType;
	}

	public void setBulkOriginType(String bulkOriginType) {
		this.bulkOriginType = bulkOriginType;
	}

	public String getBulkSubchangetype() {
		return bulkSubchangetype;
	}

	public void setBulkSubchangetype(String bulkSubchangetype) {
		this.bulkSubchangetype = bulkSubchangetype;
	}

	public String getAccountno() {
		return accountno;
	}

	public void setAccountno(String accountno) {
		this.accountno = accountno;
	}

	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

	public String getAm_emailid() {
		return am_emailid;
	}

	public void setAm_emailid(String am_emailid) {
		this.am_emailid = am_emailid;
	}

	public String getPm_emailid() {
		return pm_emailid;
	}

	public void setPm_emailid(String pm_emailid) {
		this.pm_emailid = pm_emailid;
	}

	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}

	public String getLogical_si_no() {
		return logical_si_no;
	}

	public void setLogical_si_no(String logical_si_no) {
		this.logical_si_no = logical_si_no;
	}

	public String getDemo_end_date() {
		return Demo_end_date;
	}

	public void setDemo_end_date(String demo_end_date) {
		Demo_end_date = demo_end_date;
	}

	public String getColMgr_emailid() {
		return ColMgr_emailid;
	}

	public void setColMgr_emailid(String colMgr_emailid) {
		ColMgr_emailid = colMgr_emailid;
	}

	public String getActmgrid() {
		return actmgrid;
	}

	public void setActmgrid(String actmgrid) {
		this.actmgrid = actmgrid;
	}

	private ArrayList<DisconnectOrderDto> cancelorderstatuslist;
	private ArrayList<DisconnectOrderDto> approveorderstatuslist;

	public ArrayList<DisconnectOrderDto> getApproveorderstatuslist() {
		return approveorderstatuslist;
	}

	public void setApproveorderstatuslist(
			ArrayList<DisconnectOrderDto> approveorderstatuslist) {
		this.approveorderstatuslist = approveorderstatuslist;
	}

	public String getCancelorderstatus() {
		return cancelorderstatus;
	}

	public void setCancelorderstatus(String cancelorderstatus) {
		this.cancelorderstatus = cancelorderstatus;
	}

	public ArrayList<DisconnectOrderDto> getCancelorderstatuslist() {
		return cancelorderstatuslist;
	}

	public void setCancelorderstatuslist(
			ArrayList<DisconnectOrderDto> cancelorderstatuslist) {
		this.cancelorderstatuslist = cancelorderstatuslist;
	}

	public int getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getApprovestatus() {
		return approvestatus;
	}

	public void setApprovestatus(String approvestatus) {
		this.approvestatus = approvestatus;
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

	public String getSearchfromDate() {
		return searchfromDate;
	}

	public void setSearchfromDate(String searchfromDate) {
		this.searchfromDate = searchfromDate;
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
public String getSearchToDate() {
	return searchToDate;
}
public void setSearchToDate(String searchToDate) {
	this.searchToDate = searchToDate;
}
public String getRegion() {
	return region;
}
public void setRegion(String region) {
	this.region = region;
}
public String getServiceTypeName() {
	return serviceTypeName;
}
public void setServiceTypeName(String serviceTypeName) {
	this.serviceTypeName = serviceTypeName;
}


public PagingSorting getPagingSorting() {
	return pagingSorting;
}
public void setPagingSorting(PagingSorting pagingSorting) {
	this.pagingSorting = pagingSorting;
}
public int getFailurecount() {
	return failurecount;
}
public void setFailurecount(int failurecount) {
	this.failurecount = failurecount;
}
public int getSuccesscount() {
	return successcount;
}
public void setSuccesscount(int successcount) {
	this.successcount = successcount;
}
public String getFailureorderList() {
	return failureorderList;
}
public void setFailureorderList(String failureorderList) {
	this.failureorderList = failureorderList;
}
public String getSuccessorderList() {
	return successorderList;
}
public void setSuccessorderList(String successorderList) {
	this.successorderList = successorderList;
}
public String getDisconnection_date_bycust() {
	return disconnection_date_bycust;
}
public void setDisconnection_date_bycust(String disconnection_date_bycust) {
	this.disconnection_date_bycust = disconnection_date_bycust;
}
public String getDisconnection_date_with_np() {
	return disconnection_date_with_np;
}
public void setDisconnection_date_with_np(String disconnection_date_with_np) {
	this.disconnection_date_with_np = disconnection_date_with_np;
}
public String getDisconnection_login_date() {
	return disconnection_login_date;
}
public void setDisconnection_login_date(String disconnection_login_date) {
	this.disconnection_login_date = disconnection_login_date;
}
public String getDisconnection_rec_date() {
	return disconnection_rec_date;
}
public void setDisconnection_rec_date(String disconnection_rec_date) {
	this.disconnection_rec_date = disconnection_rec_date;
}
public String getIntimatedate() {
	return intimatedate;
}
public void setIntimatedate(String intimatedate) {
	this.intimatedate = intimatedate;
}
public String getSrno() {
	return srno;
}
public void setSrno(String srno) {
	this.srno = srno;
}
public int getLsi() {
	return lsi;
}
public void setLsi(int lsi) {
	this.lsi = lsi;
}
public int getCancel_flag() {
	return cancel_flag;
}
public void setCancel_flag(int cancel_flag) {
	this.cancel_flag = cancel_flag;
}
public String getCancel_reason() {
	return cancel_reason;
}
public void setCancel_reason(String cancel_reason) {
	this.cancel_reason = cancel_reason;
}
public String getSrDate() {
	return srDate;
}
public void setSrDate(String srDate) {
	this.srDate = srDate;
}

public int getCreated_by() {
	return created_by;
}
public void setCreated_by(int created_by) {
	this.created_by = created_by;
}
public String getCreated_date() {
	return created_date;
}
public void setCreated_date(String created_date) {
	this.created_date = created_date;
}
public int getProcessing_status() {
	return processing_status;
}
public void setProcessing_status(int processing_status) {
	this.processing_status = processing_status;
}
public Timestamp getCrm_created_date() {
	return crm_created_date;
}
public void setCrm_created_date(Timestamp crm_created_date) {
	this.crm_created_date = crm_created_date;
}
public Timestamp getCrm_disconnection_date_bycust() {
	return crm_disconnection_date_bycust;
}
public void setCrm_disconnection_date_bycust(
		Timestamp crm_disconnection_date_bycust) {
	this.crm_disconnection_date_bycust = crm_disconnection_date_bycust;
}
public Timestamp getCrm_disconnection_date_with_np() {
	return crm_disconnection_date_with_np;
}
public void setCrm_disconnection_date_with_np(
		Timestamp crm_disconnection_date_with_np) {
	this.crm_disconnection_date_with_np = crm_disconnection_date_with_np;
}
public Timestamp getCrm_disconnection_login_date() {
	return crm_disconnection_login_date;
}
public void setCrm_disconnection_login_date(
		Timestamp crm_disconnection_login_date) {
	this.crm_disconnection_login_date = crm_disconnection_login_date;
}
public Timestamp getCrm_disconnection_rec_date() {
	return crm_disconnection_rec_date;
}
public void setCrm_disconnection_rec_date(Timestamp crm_disconnection_rec_date) {
	this.crm_disconnection_rec_date = crm_disconnection_rec_date;
}
public Timestamp getCrm_intimatedate() {
	return crm_intimatedate;
}
public void setCrm_intimatedate(Timestamp crm_intimatedate) {
	this.crm_intimatedate = crm_intimatedate;
}
public Timestamp getCrm_srDate() {
	return crm_srDate;
}
public void setCrm_srDate(Timestamp crm_srDate) {
	this.crm_srDate = crm_srDate;
}

public int getLast_updated_by() {
	return last_updated_by;
}
public void setLast_updated_by(int last_updated_by) {
	this.last_updated_by = last_updated_by;
}
public String getLast_updated_date() {
	return last_updated_date;
}
public void setLast_updated_date(String last_updated_date) {
	this.last_updated_date = last_updated_date;
}
public String getSr_raised_by() {
	return sr_raised_by;
}
public void setSr_raised_by(String sr_raised_by) {
	this.sr_raised_by = sr_raised_by;
}
public String getSearch_dis_date() {
	return search_dis_date;
}
public void setSearch_dis_date(String search_dis_date) {
	this.search_dis_date = search_dis_date;
}
public String getOrder_created_by() {
	return order_created_by;
}
public void setOrder_created_by(String order_created_by) {
	this.order_created_by = order_created_by;
}
public String getSearch_from_dis_date() {
	return search_from_dis_date;
}
public void setSearch_from_dis_date(String search_from_dis_date) {
	this.search_from_dis_date = search_from_dis_date;
}
public String getSearch_to_dis_date() {
	return search_to_dis_date;
}
public void setSearch_to_dis_date(String search_to_dis_date) {
	this.search_to_dis_date = search_to_dis_date;
}

	// [002] starts
	public String getOrderSource() {
		return orderSource;
	}

	public void setOrderSource(String orderSource) {
		this.orderSource = orderSource;
	}
	// [002] ends

	//priya
	public String getOldModifiedDate() {
		return oldModifiedDate;
	}

	public void setOldModifiedDate(String oldModifiedDate) {
		this.oldModifiedDate = oldModifiedDate;
	}
	
	
	
}
