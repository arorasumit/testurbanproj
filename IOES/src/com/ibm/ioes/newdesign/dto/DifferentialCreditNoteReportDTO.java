package com.ibm.ioes.newdesign.dto;

import java.util.Date;

import com.ibm.ioes.utilities.PagingSorting;

public class DifferentialCreditNoteReportDTO 
{
	PagingSorting pagingSorting = new PagingSorting();
	private String srno;
	private String differentialAmt;
	private int orderLineNumber;
	private String searchBillableAcNo;
	private String orderNo;
	private String logicalSINo;
	private String searchfromDate;
	private String searchToDate;
	private String searchSRNO;
	private String searchLSI;
	private String SearchAccount;
	private String searchAccountName;
	private String searchLineNo;
	private String oldChargeID;
	private String oldChargeName;
	private String oldChargeAmt;
	private String newChargeID;
	private String newChargeName;
	private String newChargeAmt;
	private String billableAccount;
	private String chargeInititationDate;
	private double oldChargeFreqAmt;
	private String oldChargeFXFreqId=null; 
	private Date oldChargeInitiationDate=null;
	private Date oldChargeEndDate=null;
	private double newChargeFreqAmt;
	private String newChargeFXFreqId=null;
	private Date newChargeInitiationDate=null;
	private Date newChargeActiveDate=null;
	private long linkingId;
	private double dataDifferentialAmt;
	private int returnStatus;
	private String productName;
	private String subProductName;
	private String currency;
	
	private String serviceProductID=null;
	private Long newChargeInfoId=null;
	private int newCharge_start_date_days=0;
	private int newCharge_start_date_month=0;
	private Date old_charge_start_date=null;
	private Long oldChargeinfoid=null;
	
	
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
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getDifferentialAmt() {
		return differentialAmt;
	}
	public void setDifferentialAmt(String differentialAmt) {
		this.differentialAmt = differentialAmt;
	}
	public String getSearchSRNO() {
		return searchSRNO;
	}
	public void setSearchSRNO(String searchSRNO) {
		this.searchSRNO = searchSRNO;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public String getSrno() {
		return srno;
	}
	public void setSrno(String srno) {
		this.srno = srno;
	}
	public int getOrderLineNumber() {
		return orderLineNumber;
	}
	public void setOrderLineNumber(int orderLineNumber) {
		this.orderLineNumber = orderLineNumber;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getLogicalSINo() {
		return logicalSINo;
	}
	public void setLogicalSINo(String logicalSINo) {
		this.logicalSINo = logicalSINo;
	}
	public String getSearchfromDate() {
		return searchfromDate;
	}
	public void setSearchfromDate(String searchfromDate) {
		this.searchfromDate = searchfromDate;
	}
	public String getSearchToDate() {
		return searchToDate;
	}
	public void setSearchToDate(String searchToDate) {
		this.searchToDate = searchToDate;
	}
	public String getSearchLSI() {
		return searchLSI;
	}
	public void setSearchLSI(String searchLSI) {
		this.searchLSI = searchLSI;
	}
	public String getSearchAccount() {
		return SearchAccount;
	}
	public void setSearchAccount(String searchAccount) {
		SearchAccount = searchAccount;
	}
	public String getSearchAccountName() {
		return searchAccountName;
	}
	public void setSearchAccountName(String searchAccountName) {
		this.searchAccountName = searchAccountName;
	}
	public String getSearchLineNo() {
		return searchLineNo;
	}
	public void setSearchLineNo(String searchLineNo) {
		this.searchLineNo = searchLineNo;
	}
	public String getOldChargeID() {
		return oldChargeID;
	}
	public void setOldChargeID(String oldChargeID) {
		this.oldChargeID = oldChargeID;
	}
	public String getOldChargeName() {
		return oldChargeName;
	}
	public void setOldChargeName(String oldChargeName) {
		this.oldChargeName = oldChargeName;
	}
	public String getOldChargeAmt() {
		return oldChargeAmt;
	}
	public void setOldChargeAmt(String oldChargeAmt) {
		this.oldChargeAmt = oldChargeAmt;
	}
	public String getNewChargeID() {
		return newChargeID;
	}
	public void setNewChargeID(String newChargeID) {
		this.newChargeID = newChargeID;
	}
	public String getNewChargeName() {
		return newChargeName;
	}
	public void setNewChargeName(String newChargeName) {
		this.newChargeName = newChargeName;
	}
	public String getNewChargeAmt() {
		return newChargeAmt;
	}
	public void setNewChargeAmt(String newChargeAmt) {
		this.newChargeAmt = newChargeAmt;
	}
	public String getBillableAccount() {
		return billableAccount;
	}
	public void setBillableAccount(String billableAccount) {
		this.billableAccount = billableAccount;
	}
	public String getChargeInititationDate() {
		return chargeInititationDate;
	}
	public void setChargeInititationDate(String chargeInititationDate) {
		this.chargeInititationDate = chargeInititationDate;
	}
	public String getSearchBillableAcNo() {
		return searchBillableAcNo;
	}
	public void setSearchBillableAcNo(String searchBillableAcNo) {
		this.searchBillableAcNo = searchBillableAcNo;
	}
	public double getDataDifferentialAmt() {
		return dataDifferentialAmt;
	}
	public void setDataDifferentialAmt(double dataDifferentialAmt) {
		this.dataDifferentialAmt = dataDifferentialAmt;
	}
	public long getLinkingId() {
		return linkingId;
	}
	public void setLinkingId(long linkingId) {
		this.linkingId = linkingId;
	}
	public Date getNewChargeActiveDate() {
		return newChargeActiveDate;
	}
	public void setNewChargeActiveDate(Date newChargeActiveDate) {
		this.newChargeActiveDate = newChargeActiveDate;
	}
	public double getNewChargeFreqAmt() {
		return newChargeFreqAmt;
	}
	public void setNewChargeFreqAmt(double newChargeFreqAmt) {
		this.newChargeFreqAmt = newChargeFreqAmt;
	}
	public String getNewChargeFXFreqId() {
		return newChargeFXFreqId;
	}
	public void setNewChargeFXFreqId(String newChargeFXFreqId) {
		this.newChargeFXFreqId = newChargeFXFreqId;
	}
	public Date getNewChargeInitiationDate() {
		return newChargeInitiationDate;
	}
	public void setNewChargeInitiationDate(Date newChargeInitiationDate) {
		this.newChargeInitiationDate = newChargeInitiationDate;
	}
	public Date getOldChargeEndDate() {
		return oldChargeEndDate;
	}
	public void setOldChargeEndDate(Date oldChargeEndDate) {
		this.oldChargeEndDate = oldChargeEndDate;
	}
	public double getOldChargeFreqAmt() {
		return oldChargeFreqAmt;
	}
	public void setOldChargeFreqAmt(double oldChargeFreqAmt) {
		this.oldChargeFreqAmt = oldChargeFreqAmt;
	}
	public String getOldChargeFXFreqId() {
		return oldChargeFXFreqId;
	}
	public void setOldChargeFXFreqId(String oldChargeFXFreqId) {
		this.oldChargeFXFreqId = oldChargeFXFreqId;
	}
	public Date getOldChargeInitiationDate() {
		return oldChargeInitiationDate;
	}
	public void setOldChargeInitiationDate(Date oldChargeInitiationDate) {
		this.oldChargeInitiationDate = oldChargeInitiationDate;
	}
	public int getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(int returnStatus) {
		this.returnStatus = returnStatus;
	}
	public int getNewCharge_start_date_days() {
		return newCharge_start_date_days;
	}
	public void setNewCharge_start_date_days(int newCharge_start_date_days) {
		this.newCharge_start_date_days = newCharge_start_date_days;
	}
	public int getNewCharge_start_date_month() {
		return newCharge_start_date_month;
	}
	public void setNewCharge_start_date_month(int newCharge_start_date_month) {
		this.newCharge_start_date_month = newCharge_start_date_month;
	}

	public Long getNewChargeInfoId() {
		return newChargeInfoId;
	}
	public void setNewChargeInfoId(Long newChargeInfoId) {
		this.newChargeInfoId = newChargeInfoId;
	}
	public Date getOld_charge_start_date() {
		return old_charge_start_date;
	}
	public void setOld_charge_start_date(Date old_charge_start_date) {
		this.old_charge_start_date = old_charge_start_date;
	}

	public Long getOldChargeinfoid() {
		return oldChargeinfoid;
	}
	public void setOldChargeinfoid(Long oldChargeinfoid) {
		this.oldChargeinfoid = oldChargeinfoid;
	}
	public String getServiceProductID() {
		return serviceProductID;
	}
	public void setServiceProductID(String serviceProductID) {
		this.serviceProductID = serviceProductID;
	}
}

