/**
 * 
 */
package com.ibm.fx.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Anshuli
 *
 */
public class ServiceDTO {
	
	private String token_id = null; // added by sandeep on 22-jun-2011
	private Long rowId = null;
	private String viewId=null; 
	
	private int subscrNo;
	private int subscrNoReset ;
	
	private String a_serviceCompany=null;
	private String a_serviceFname=null;
	private String a_serviceMname=null;
	private String a_serviceLname=null;
	private String a_serviceAddress1=null;
	private String a_serviceAddress2=null;
	private String a_serviceAddress3=null;
	private String a_serviceCity=null;
	private String a_serviceState=null;
	private Short a_serviceCountryCode=null;
	private String a_serviceZip=null;
	
	
	private String b_serviceCompany=null;
	private String b_serviceFname=null;
	private String b_serviceMname=null;
	private String b_serviceLname=null;
	private String b_serviceAddress1=null;
	private String b_serviceAddress2=null;
	private String b_serviceAddress3=null;
	private String b_serviceCity=null;
	//private String b_serviceState=null;
	private Short b_serviceCountryCode=null;
	private String b_serviceZip=null;
	
	ArrayList<ExtendedData> extendedData=null;
	
	private String serviceUpdateFlag  = null;

	private boolean isDataIssueException;

	
	
	/*private String acctExternalId ;
	private String prename ;
	private String fName ;
	private String lName ;
	private String address1 = null;
	private String address2 = null;
	private String address3 = null;
	private String billcompany = null;
	private String billPeriod;
	private int billcountrycode;
	private String billstate = null;
	private String billcity = null;
	private String billzip = null;
	private String custfaxno = null;
	private int rateClassDefault =0;
	private String mobNo = null;
	
	private String stbId = null;
	private String vcId = null;
	private String casId = null;*/
	//////////////////////////////////////////////////////////
	
	private Integer emfConfigId = null;
	private Date serviceActiveDate = null;
	
	private Integer privacyLevel = null;
	
	private Integer revRcvCostCtr = null;
	
	
	private Integer serviceExternalIdType = null;
	private String acctExternalId = null;
	private Long serviceProductId = null;
	
	String servExtId = null;
	
	ArrayList<NrcDto> nrcs=null;
	ArrayList<RcDto> rcs=null;
	
	private Exception exception = null;
	private String exceptionMessage = null;
	private int returnStatus=0;
	private Integer serviceDisplayExternalIdType = null;
	private long id;
	private int subFxSchdUpdateStatus=0;
	private int subProcessingStatus=0;
	
	ArrayList<FxExternalIdDto> externalIds = null;
	
	ArrayList<ExtendedData> orderExtendedData=null;
	
	private Integer rateClass = null;
	private Integer currencyCode = null;

	public ArrayList<ExtendedData> getOrderExtendedData() {
		return orderExtendedData;
	}

	public void setOrderExtendedData(ArrayList<ExtendedData> orderExtendedData) {
		this.orderExtendedData = orderExtendedData;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getReturnStatus() {
		return returnStatus;
	}

	public void setReturnStatus(int returnStatus) {
		this.returnStatus = returnStatus;
	}

	

	/**
	 * @return the subscrNo
	 */
	public int getSubscrNo() {
		return this.subscrNo;
	}

	/**
	 * @param subscrNo the subscrNo to set
	 */
	public void setSubscrNo(int subscrNo) {
		this.subscrNo = subscrNo;
	}

	/**
	 * @return the subscrNoReset
	 */
	public int getSubscrNoReset() {
		return this.subscrNoReset;
	}

	/**
	 * @param subscrNoReset the subscrNoReset to set
	 */
	public void setSubscrNoReset(int subscrNoReset) {
		this.subscrNoReset = subscrNoReset;
	}

	/**
	 * @return the servExtId
	 */
	public String getServExtId() {
		return this.servExtId;
	}

	/**
	 * @param servExtId the servExtId to set
	 */
	public void setServExtId(String servExtId) {
		this.servExtId = servExtId;
	}

	/**
	 * @return the address1
	 */

	public ArrayList<NrcDto> getNrcs() {
		return nrcs;
	}

	public void setNrcs(ArrayList<NrcDto> nrcs) {
		this.nrcs = nrcs;
	}

	public ArrayList<RcDto> getRcs() {
		return rcs;
	}

	public void setRcs(ArrayList<RcDto> rcs) {
		this.rcs = rcs;
	}

	public Integer getEmfConfigId() {
		return emfConfigId;
	}

	public Integer getPrivacyLevel() {
		return privacyLevel;
	}

	public void setPrivacyLevel(Integer privacyLevel) {
		this.privacyLevel = privacyLevel;
	}

	public Integer getRevRcvCostCtr() {
		return revRcvCostCtr;
	}

	public void setRevRcvCostCtr(Integer revRcvCostCtr) {
		this.revRcvCostCtr = revRcvCostCtr;
	}

	public Date getServiceActiveDate() {
		return serviceActiveDate;
	}

	public void setServiceActiveDate(Date serviceActiveDate) {
		this.serviceActiveDate = serviceActiveDate;
	}

	public void setEmfConfigId(Integer emfConfigId) {
		this.emfConfigId = emfConfigId;
	}

	

	public Integer getServiceExternalIdType() {
		return serviceExternalIdType;
	}

	public void setServiceExternalIdType(Integer serviceExternalIdType) {
		this.serviceExternalIdType = serviceExternalIdType;
	}

	public Long getServiceProductId() {
		return serviceProductId;
	}

	public void setServiceProductId(Long serviceProductId) {
		this.serviceProductId = serviceProductId;
	}

	public String getAcctExternalId() {
		return acctExternalId;
	}

	public void setAcctExternalId(String acctExternalId) {
		this.acctExternalId = acctExternalId;
	}

	public String getA_serviceAddress1() {
		return a_serviceAddress1;
	}

	public void setA_serviceAddress1(String address1) {
		a_serviceAddress1 = address1;
	}

	public String getA_serviceAddress2() {
		return a_serviceAddress2;
	}

	public void setA_serviceAddress2(String address2) {
		a_serviceAddress2 = address2;
	}

	public String getA_serviceAddress3() {
		return a_serviceAddress3;
	}

	public void setA_serviceAddress3(String address3) {
		a_serviceAddress3 = address3;
	}

	public String getA_serviceCity() {
		return a_serviceCity;
	}

	public void setA_serviceCity(String city) {
		a_serviceCity = city;
	}

	public String getA_serviceCompany() {
		return a_serviceCompany;
	}

	public void setA_serviceCompany(String company) {
		a_serviceCompany = company;
	}

	public Short getA_serviceCountryCode() {
		return a_serviceCountryCode;
	}

	public void setA_serviceCountryCode(Short countryCode) {
		a_serviceCountryCode = countryCode;
	}

	public String getA_serviceFname() {
		return a_serviceFname;
	}

	public void setA_serviceFname(String fname) {
		a_serviceFname = fname;
	}

	public String getA_serviceLname() {
		return a_serviceLname;
	}

	public void setA_serviceLname(String lname) {
		a_serviceLname = lname;
	}

	public String getA_serviceMname() {
		return a_serviceMname;
	}

	public void setA_serviceMname(String mname) {
		a_serviceMname = mname;
	}

	public String getA_serviceState() {
		return a_serviceState;
	}

	public void setA_serviceState(String state) {
		a_serviceState = state;
	}

	public String getA_serviceZip() {
		return a_serviceZip;
	}

	public void setA_serviceZip(String zip) {
		a_serviceZip = zip;
	}

	public String getB_serviceAddress1() {
		return b_serviceAddress1;
	}

	public void setB_serviceAddress1(String address1) {
		b_serviceAddress1 = address1;
	}

	public String getB_serviceAddress2() {
		return b_serviceAddress2;
	}

	public void setB_serviceAddress2(String address2) {
		b_serviceAddress2 = address2;
	}

	public String getB_serviceAddress3() {
		return b_serviceAddress3;
	}

	public void setB_serviceAddress3(String address3) {
		b_serviceAddress3 = address3;
	}

	public String getB_serviceCity() {
		return b_serviceCity;
	}

	public void setB_serviceCity(String city) {
		b_serviceCity = city;
	}

	public String getB_serviceCompany() {
		return b_serviceCompany;
	}

	public void setB_serviceCompany(String company) {
		b_serviceCompany = company;
	}

	public Short getB_serviceCountryCode() {
		return b_serviceCountryCode;
	}

	public void setB_serviceCountryCode(Short countryCode) {
		b_serviceCountryCode = countryCode;
	}

	public String getB_serviceFname() {
		return b_serviceFname;
	}

	public void setB_serviceFname(String fname) {
		b_serviceFname = fname;
	}

	public String getB_serviceLname() {
		return b_serviceLname;
	}

	public void setB_serviceLname(String lname) {
		b_serviceLname = lname;
	}

	public String getB_serviceMname() {
		return b_serviceMname;
	}

	public void setB_serviceMname(String mname) {
		b_serviceMname = mname;
	}

	public String getB_serviceZip() {
		return b_serviceZip;
	}

	public void setB_serviceZip(String zip) {
		b_serviceZip = zip;
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public Integer getServiceDisplayExternalIdType() {
		return serviceDisplayExternalIdType;
	}

	public void setServiceDisplayExternalIdType(Integer serviceDisplayExternalIdType) {
		this.serviceDisplayExternalIdType = serviceDisplayExternalIdType;
	}

	public Long getRowId() {
		return rowId;
	}

	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}
	public ArrayList<ExtendedData> getExtendedData() {
		return extendedData;
	}

	public void setExtendedData(ArrayList<ExtendedData> extendedData) {
		this.extendedData = extendedData;
	}

	public ArrayList<FxExternalIdDto> getExternalIds() {
		return externalIds;
	}

	public void setExternalIds(ArrayList<FxExternalIdDto> externalIds) {
		this.externalIds = externalIds;
	}

	public String getToken_id() {
		return token_id;
	}

	public void setToken_id(String token_id) {
		this.token_id = token_id;
	}

	public String getViewId() {
		return viewId;
	}

	public void setViewId(String viewId) {
		this.viewId = viewId;
	}

	public String getServiceUpdateFlag() {
		return serviceUpdateFlag;
	}

	public void setServiceUpdateFlag(String serviceUpdateFlag) {
		this.serviceUpdateFlag = serviceUpdateFlag;
	}

	public Integer getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(Integer currencyCode) {
		this.currencyCode = currencyCode;
	}

	public Integer getRateClass() {
		return rateClass;
	}

	public void setRateClass(Integer rateClass) {
		this.rateClass = rateClass;
	}

	public Integer getSubFxSchdUpdateStatus() {
		return subFxSchdUpdateStatus;
	}

	public void setSubFxSchdUpdateStatus(Integer subFxSchdUpdateStatus) {
		this.subFxSchdUpdateStatus = subFxSchdUpdateStatus;
	}

	public int getSubProcessingStatus() {
		return subProcessingStatus;
	}

	public void setSubProcessingStatus(int subProcessingStatus) {
		this.subProcessingStatus = subProcessingStatus;
	}

	public boolean isDataIssueException() {
		return isDataIssueException;
	}

	public void setDataIssueException(boolean isDataIssueException) {
		this.isDataIssueException = isDataIssueException;
	}

}
