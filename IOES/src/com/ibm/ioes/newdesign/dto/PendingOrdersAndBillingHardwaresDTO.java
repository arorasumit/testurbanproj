//[010] VIPIN SAHARIA 05-Nov-2013 added serviceNumber and Currency
package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;

public class PendingOrdersAndBillingHardwaresDTO {

	
	PagingSorting pagingSorting = new PagingSorting();
	private String verticalDetails;
    private String cus_segment;
    private String act_category;
	private String regionName;
	private String actmngname;
	private String prjmngname;
	private int accountID;
	private String copcApproveDate;
	private String orderNo;
	private int poNumber;
	private String serviceName;
	private String logicalCircuitId;
	private String factory_ckt_id;
	private String linename;
	private String chargeAmount_String;
	private String startDate;
	private String challenno;
	private String challendate;
	private String partyName;
	private String orderType;
	private int fromAccountNo;
	private int toAccountNo;
	private String fromOrderDate;
	private String toOrderDate;
	private String fromCrmOrderid;
	private String toCrmOrderid;
	private String party;
	//[010] Start
	private int serviceNumber;
	private String currency; 
	
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCurrency() {
		return currency;
	}
	public void setServiceNumber(int serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public int getServiceNumber() {
		return serviceNumber;
	}
	//[010] End
	
	public String getParty() {
		return party;
	}
	public void setParty(String party) {
		this.party = party;
	}
	public int getFromAccountNo() {
		return fromAccountNo;
	}
	public void setFromAccountNo(int fromAccountNo) {
		this.fromAccountNo = fromAccountNo;
	}
	public String getFromCrmOrderid() {
		return fromCrmOrderid;
	}
	public void setFromCrmOrderid(String fromCrmOrderid) {
		this.fromCrmOrderid = fromCrmOrderid;
	}
	public String getFromOrderDate() {
		return fromOrderDate;
	}
	public void setFromOrderDate(String fromOrderDate) {
		this.fromOrderDate = fromOrderDate;
	}
	public int getToAccountNo() {
		return toAccountNo;
	}
	public void setToAccountNo(int toAccountNo) {
		this.toAccountNo = toAccountNo;
	}
	public String getToCrmOrderid() {
		return toCrmOrderid;
	}
	public void setToCrmOrderid(String toCrmOrderid) {
		this.toCrmOrderid = toCrmOrderid;
	}
	public String getToOrderDate() {
		return toOrderDate;
	}
	public void setToOrderDate(String toOrderDate) {
		this.toOrderDate = toOrderDate;
	}
	public int getAccountID() {
		return accountID;
	}
	public void setAccountID(int accountID) {
		this.accountID = accountID;
	}
	public String getAct_category() {
		return act_category;
	}
	public void setAct_category(String act_category) {
		this.act_category = act_category;
	}
	public String getActmngname() {
		return actmngname;
	}
	public void setActmngname(String actmngname) {
		this.actmngname = actmngname;
	}
	public String getChallendate() {
		return challendate;
	}
	public void setChallendate(String challendate) {
		this.challendate = challendate;
	}
	public String getChallenno() {
		return challenno;
	}
	public void setChallenno(String challenno) {
		this.challenno = challenno;
	}
	public String getChargeAmount_String() {
		return chargeAmount_String;
	}
	public void setChargeAmount_String(String chargeAmount_String) {
		this.chargeAmount_String = chargeAmount_String;
	}
	public String getCopcApproveDate() {
		return copcApproveDate;
	}
	public void setCopcApproveDate(String copcApproveDate) {
		this.copcApproveDate = copcApproveDate;
	}
	public String getCus_segment() {
		return cus_segment;
	}
	public void setCus_segment(String cus_segment) {
		this.cus_segment = cus_segment;
	}
	public String getFactory_ckt_id() {
		return factory_ckt_id;
	}
	public void setFactory_ckt_id(String factory_ckt_id) {
		this.factory_ckt_id = factory_ckt_id;
	}
	public String getLinename() {
		return linename;
	}
	public void setLinename(String linename) {
		this.linename = linename;
	}
	public String getLogicalCircuitId() {
		return logicalCircuitId;
	}
	public void setLogicalCircuitId(String logicalCircuitId) {
		this.logicalCircuitId = logicalCircuitId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public int getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(int poNumber) {
		this.poNumber = poNumber;
	}
	public String getPrjmngname() {
		return prjmngname;
	}
	public void setPrjmngname(String prjmngname) {
		this.prjmngname = prjmngname;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getVerticalDetails() {
		return verticalDetails;
	}
	public void setVerticalDetails(String verticalDetails) {
		this.verticalDetails = verticalDetails;
	}
	
	
	

	

	
}
