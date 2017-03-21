package com.ibm.fx.dto;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class ComponentDTO {

	private Integer returnStatus=0;
	private Exception exception;
	private String exceptionMessage = null;
	private String fx_Level;
	private Long rowId = null;
	int saveStatus;
	Integer accountInternalId;
	private String subScrNo = null;
	private int subScrNoReset ;
	private Date billingActiveDate = null;
	private String  packageid      = null;
	private String  componentid    = null;
	private String  package_inst_id = null;
	private String  component_inst_id = null;
	private String  package_inst_id_serv = null;
	private String  component_inst_id_serv = null;
	private String  tokenid = null;
	private String  accountExternalId = null;
	public Timestamp component_disconnection_date = null; 
	public int index_key	    		=	0;
	private String componentStatus="";
	private String compEndLogic="";
	private int compEndDays;
	private int compEndMonths;
	ArrayList<ExtendedData> orderExtendedData=null;
	public String serviceProductId;
	public String serviceId;
	public String concateServiceLineItemCompIds="";
	private boolean isDataIssueException;
	public boolean isDataIssueException() {
		return isDataIssueException;
	}
	public void setDataIssueException(boolean isDataIssueException) {
		this.isDataIssueException = isDataIssueException;
	}
	public String getConcateServiceLineItemCompIds() {
		return concateServiceLineItemCompIds;
	}
	public void setConcateServiceLineItemCompIds(
			String concateServiceLineItemCompIds) {
		this.concateServiceLineItemCompIds = concateServiceLineItemCompIds;
	}
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceProductId() {
		return serviceProductId;
	}
	public void setServiceProductId(String serviceProductId) {
		this.serviceProductId = serviceProductId;
	}
	public ArrayList<ExtendedData> getOrderExtendedData() {
		return orderExtendedData;
	}
	public void setOrderExtendedData(ArrayList<ExtendedData> orderExtendedData) {
		this.orderExtendedData = orderExtendedData;
	}
	public int getCompEndDays() {
		return compEndDays;
	}
	public void setCompEndDays(int compEndDays) {
		this.compEndDays = compEndDays;
	}
	public String getCompEndLogic() {
		return compEndLogic;
	}
	public void setCompEndLogic(String compEndLogic) {
		this.compEndLogic = compEndLogic;
	}
	public int getCompEndMonths() {
		return compEndMonths;
	}
	public void setCompEndMonths(int compEndMonths) {
		this.compEndMonths = compEndMonths;
	}
	public String getComponentStatus() {
		return componentStatus;
	}
	public void setComponentStatus(String componentStatus) {
		this.componentStatus = componentStatus;
	}
	public String getAccountExternalId() {
		return accountExternalId;
	}
	public void setAccountExternalId(String accountExternalId) {
		this.accountExternalId = accountExternalId;
	}
	public String getComponentid() {
		return componentid;
	}
	public void setComponentid(String componentid) {
		this.componentid = componentid;
	}
	public String getPackageid() {
		return packageid;
	}
	public void setPackageid(String packageid) {
		this.packageid = packageid;
	}
	public Integer getAccountInternalId() {
		return accountInternalId;
	}
	public void setAccountInternalId(Integer accountInternalId) {
		this.accountInternalId = accountInternalId;
	}
	public Date getBillingActiveDate() {
		return billingActiveDate;
	}
	public void setBillingActiveDate(Date billingActiveDate) {
		this.billingActiveDate = billingActiveDate;
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
	public Integer getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(Integer returnStatus) {
		this.returnStatus = returnStatus;
	}
	public int getSaveStatus() {
		return saveStatus;
	}
	public void setSaveStatus(int saveStatus) {
		this.saveStatus = saveStatus;
	}
	
	
	public String getFx_Level() {
		return fx_Level;
	}
	public void setFx_Level(String fx_Level) {
		this.fx_Level = fx_Level;
	}
	public String getComponent_inst_id() {
		return component_inst_id;
	}
	public void setComponent_inst_id(String component_inst_id) {
		this.component_inst_id = component_inst_id;
	}
	public String getComponent_inst_id_serv() {
		return component_inst_id_serv;
	}
	public void setComponent_inst_id_serv(String component_inst_id_serv) {
		this.component_inst_id_serv = component_inst_id_serv;
	}
	public String getPackage_inst_id() {
		return package_inst_id;
	}
	public void setPackage_inst_id(String package_inst_id) {
		this.package_inst_id = package_inst_id;
	}
	public String getPackage_inst_id_serv() {
		return package_inst_id_serv;
	}
	public void setPackage_inst_id_serv(String package_inst_id_serv) {
		this.package_inst_id_serv = package_inst_id_serv;
	}
	public String getSubScrNo() {
		return subScrNo;
	}
	public void setSubScrNo(String subScrNo) {
		this.subScrNo = subScrNo;
	}
	
	public Long getRowId() {
		return rowId;
	}
	public void setRowId(Long rowId) {
		this.rowId = rowId;
	}
	public String getTokenid() {
		return tokenid;
	}
	public void setTokenid(String tokenid) {
		this.tokenid = tokenid;
	}
	public int getSubScrNoReset() {
		return subScrNoReset;
	}
	public void setSubScrNoReset(int subScrNoReset) {
		this.subScrNoReset = subScrNoReset;
	}
	public Timestamp getComponent_disconnection_date() {
		return component_disconnection_date;
	}
	public void setComponent_disconnection_date(
			Timestamp component_disconnection_date) {
		this.component_disconnection_date = component_disconnection_date;
	}
	public int getIndex_key() {
		return index_key;
	}
	public void setIndex_key(int index_key) {
		this.index_key = index_key;
	}
	
	
}
