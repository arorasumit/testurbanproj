/**
 * 
 */
package com.ibm.fx.dto;

import java.util.ArrayList;
import java.util.Date;


/**
 * @author ibm
 *
 */
public class AcctDTO {

		
	private String prename ;
	private String fName ;
	private String lName ;
	private String address1 = null;
	private String address2 = null;
	private String address3 = null;
	private String billcompany = null;
	private String billPeriod;
	private Integer billcountrycode;
	private String billstate = null;
	private String billcity = null;
	private String billzip = null;
	private String statmentfaxno = null;
	private Integer rateClassDefault =null;
	private Integer vipCode =0;
	private Integer mktCode = 0;
	private String mobNo = null;
	
	private String acctInternalId = null;
	private String acctExternalId = null;
	private Integer accountCategory = 0;
	private Integer returnStatus=0;
	private long id;
	private Integer parentInternalId = null;
	private String parentAccExternalId = null;
	
	private Integer custCountryCode = null;
	private Integer custFranchiseTaxCode = null;
	private Integer billFmtOpt = null;
	private Integer billDispMeth = null;
	private Integer exrateClass = null;
	private Integer rateClassSpecial = null;
	private Date dateActive = null;
	private Integer segmentId = null;
	
	private Integer currencyCode = null;
	
	private Integer owningCostCtr = null;
	
	private Integer orderNo=null;
	
	ArrayList<FxExternalIdDto> externalIds = null;
	ArrayList<ExtendedData> extendedData=null;
	
	private String contact1_Name = null;
	private String contact1_Phone = null;
	private String statement_to_email_for_contact1 = null;
	
	private String isCreatedNow = null;
	
	private Date foundActiveDate = null;
	
	private String exception_message;
	private Exception exception;
	private String accountUpdateStatus;
	
	private String typeOfOrder = null;
	
	private boolean isDataIssueException;

	

	public boolean isDataIssueException() {
		return isDataIssueException;
	}

	public void setDataIssueException(boolean isDataIssueException) {
		this.isDataIssueException = isDataIssueException;
	}

	public String getTypeOfOrder() {
		return typeOfOrder;
	}

	public void setTypeOfOrder(String typeOfOrder) {
		this.typeOfOrder = typeOfOrder;
	}
	

	public String getAccountUpdateStatus() {
		return accountUpdateStatus;
	}

	public void setAccountUpdateStatus(String accountUpdateStatus) {
		this.accountUpdateStatus = accountUpdateStatus;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getException_message() {
		return exception_message;
	}

	public void setException_message(String exception_message) {
		this.exception_message = exception_message;
	}

	public Integer getAccountCategory() {
		return accountCategory;
	}

	public void setAccountCategory(Integer accountCategory) {
		this.accountCategory = accountCategory;
	}

	public String getAcctExternalId() {
		return acctExternalId;
	}

	public void setAcctExternalId(String acctExternalId) {
		this.acctExternalId = acctExternalId;
	}

	public String getAcctInternalId() {
		return acctInternalId;
	}

	public void setAcctInternalId(String acctInternalId) {
		this.acctInternalId = acctInternalId;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getAddress3() {
		return address3;
	}

	public void setAddress3(String address3) {
		this.address3 = address3;
	}

	public String getBillcity() {
		return billcity;
	}

	public void setBillcity(String billcity) {
		this.billcity = billcity;
	}

	public String getBillcompany() {
		return billcompany;
	}

	public void setBillcompany(String billcompany) {
		this.billcompany = billcompany;
	}

	public Integer getBillcountrycode() {
		return billcountrycode;
	}

	public void setBillcountrycode(Integer billcountrycode) {
		this.billcountrycode = billcountrycode;
	}

	public Integer getBillDispMeth() {
		return billDispMeth;
	}

	public void setBillDispMeth(Integer billDispMeth) {
		this.billDispMeth = billDispMeth;
	}

	public Integer getBillFmtOpt() {
		return billFmtOpt;
	}

	public void setBillFmtOpt(Integer billFmtOpt) {
		this.billFmtOpt = billFmtOpt;
	}

	public String getBillPeriod() {
		return billPeriod;
	}

	public void setBillPeriod(String billPeriod) {
		this.billPeriod = billPeriod;
	}

	public String getBillstate() {
		return billstate;
	}

	public void setBillstate(String billstate) {
		this.billstate = billstate;
	}

	public String getBillzip() {
		return billzip;
	}

	public void setBillzip(String billzip) {
		this.billzip = billzip;
	}

	public Integer getCustCountryCode() {
		return custCountryCode;
	}

	public void setCustCountryCode(Integer custCountryCode) {
		this.custCountryCode = custCountryCode;
	}

	

	public String getStatmentfaxno() {
		return statmentfaxno;
	}

	public void setStatmentfaxno(String statmentfaxno) {
		this.statmentfaxno = statmentfaxno;
	}

	public Integer getCustFranchiseTaxCode() {
		return custFranchiseTaxCode;
	}

	public void setCustFranchiseTaxCode(Integer custFranchiseTaxCode) {
		this.custFranchiseTaxCode = custFranchiseTaxCode;
	}

	public Date getDateActive() {
		return dateActive;
	}

	public void setDateActive(Date dateActive) {
		this.dateActive = dateActive;
	}

	public Integer getExrateClass() {
		return exrateClass;
	}

	public void setExrateClass(Integer exrateClass) {
		this.exrateClass = exrateClass;
	}

	

	public ArrayList<FxExternalIdDto> getExternalIds() {
		return externalIds;
	}

	public void setExternalIds(ArrayList<FxExternalIdDto> externalIds) {
		this.externalIds = externalIds;
	}

	public String getFName() {
		return fName;
	}

	public void setFName(String name) {
		fName = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getLName() {
		return lName;
	}

	public void setLName(String name) {
		lName = name;
	}

	public Integer getMktCode() {
		return mktCode;
	}

	public void setMktCode(Integer mktCode) {
		this.mktCode = mktCode;
	}

	public String getMobNo() {
		return mobNo;
	}

	public void setMobNo(String mobNo) {
		this.mobNo = mobNo;
	}


	public Integer getParentInternalId() {
		return parentInternalId;
	}

	public void setParentInternalId(Integer parentInternalId) {
		this.parentInternalId = parentInternalId;
	}

	public String getPrename() {
		return prename;
	}

	public void setPrename(String prename) {
		this.prename = prename;
	}

	public Integer getRateClassDefault() {
		return rateClassDefault;
	}

	public void setRateClassDefault(Integer rateClassDefault) {
		this.rateClassDefault = rateClassDefault;
	}

	public Integer getRateClassSpecial() {
		return rateClassSpecial;
	}

	public void setRateClassSpecial(Integer rateClassSpecial) {
		this.rateClassSpecial = rateClassSpecial;
	}

	public Integer getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(Integer returnStatus) {
		this.returnStatus = returnStatus;
	}

	public Integer getVipCode() {
		return vipCode;
	}

	public void setVipCode(Integer vipCode) {
		this.vipCode = vipCode;
	}

	public Integer getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}

	public ArrayList<ExtendedData> getExtendedData() {
		return extendedData;
	}

	public void setExtendedData(ArrayList<ExtendedData> extendedData) {
		this.extendedData = extendedData;
	}

	public Integer getSegmentId() {
		return segmentId;
	}

	public void setSegmentId(Integer segmentId) {
		this.segmentId = segmentId;
	}

	public Integer getOwningCostCtr() {
		return owningCostCtr;
	}

	public void setOwningCostCtr(Integer owningCostCtr) {
		this.owningCostCtr = owningCostCtr;
	}

	public String getParentAccExternalId() {
		return parentAccExternalId;
	}

	public void setParentAccExternalId(String parentAccExternalId) {
		this.parentAccExternalId = parentAccExternalId;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getContact1_Name() {
		return contact1_Name;
	}

	public void setContact1_Name(String contact1_Name) {
		this.contact1_Name = contact1_Name;
	}

	public String getContact1_Phone() {
		return contact1_Phone;
	}

	public void setContact1_Phone(String contact1_Phone) {
		this.contact1_Phone = contact1_Phone;
	}

	public String getStatement_to_email_for_contact1() {
		return statement_to_email_for_contact1;
	}

	public void setStatement_to_email_for_contact1(
			String statement_to_email_for_contact1) {
		this.statement_to_email_for_contact1 = statement_to_email_for_contact1;
	}

	public String getIsCreatedNow() {
		return isCreatedNow;
	}

	public void setIsCreatedNow(String isCreatedNow) {
		this.isCreatedNow = isCreatedNow;
	}

	public Date getFoundActiveDate() {
		return foundActiveDate;
	}

	public void setFoundActiveDate(Date foundActiveDate) {
		this.foundActiveDate = foundActiveDate;
	}
	
	

}
