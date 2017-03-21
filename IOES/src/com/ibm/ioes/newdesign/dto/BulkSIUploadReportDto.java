package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;

public class BulkSIUploadReportDto {
	PagingSorting pagingSorting = new PagingSorting();
	private String accountName;
	private int orderNumber;
	private String crm_service_opms_id;
	private String serviceName;
	private String installation_addressaa1;
	private String installation_addressaa2;
	private String installation_addressaa3;
	private String from_city;
	private String from_state;
	private String from_country;
	private String installation_addressab1;
	private String installation_addressab2;
	private String installation_addressab3;
	private String to_city;
	private String to_state;
	private String to_country;
	private String date_of_inst;
	private String date_of_act;
	private String bandwaidth;
	private String uom;
	private String lob;
	private String circle;
	private String zone;
	private String location_from;
	private String location_to;
	private String commited_sla;
	private String hub_location;
	private String platform;
	private int customer_logicalSINumber;
	private String fxSiId;
	private String ipls;
	private String managed_yes_no;
	private String actmngname;
	private String prjmngname;
	private String service_provider;
	private String lineItemDescription;
	private String date_of_installation_from;
	private String date_of_installation_to;
	private int partyNo;
	private int party_no;
	private String orderNo;
	private String logical_SI_No;
	private String logicalSINo;
	private String linename;
	//lawkush start
	
	private long templateID;
	private long fileID;
	
	//lawkush end
	
	public long getTemplateID() {
		return templateID;
	}
	public void setTemplateID(long templateID) {
		this.templateID = templateID;
	}
	public long getFileID() {
		return fileID;
	}
	public void setFileID(long fileID) {
		this.fileID = fileID;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getActmngname() {
		return actmngname;
	}
	public void setActmngname(String actmngname) {
		this.actmngname = actmngname;
	}
	public String getBandwaidth() {
		return bandwaidth;
	}
	public void setBandwaidth(String bandwaidth) {
		this.bandwaidth = bandwaidth;
	}
	public String getCircle() {
		return circle;
	}
	public void setCircle(String circle) {
		this.circle = circle;
	}
	public String getCommited_sla() {
		return commited_sla;
	}
	public void setCommited_sla(String commited_sla) {
		this.commited_sla = commited_sla;
	}
	public String getCrm_service_opms_id() {
		return crm_service_opms_id;
	}
	public void setCrm_service_opms_id(String crm_service_opms_id) {
		this.crm_service_opms_id = crm_service_opms_id;
	}
	public int getCustomer_logicalSINumber() {
		return customer_logicalSINumber;
	}
	public void setCustomer_logicalSINumber(int customer_logicalSINumber) {
		this.customer_logicalSINumber = customer_logicalSINumber;
	}
	public String getDate_of_act() {
		return date_of_act;
	}
	public void setDate_of_act(String date_of_act) {
		this.date_of_act = date_of_act;
	}
	public String getDate_of_inst() {
		return date_of_inst;
	}
	public void setDate_of_inst(String date_of_inst) {
		this.date_of_inst = date_of_inst;
	}
	public String getFrom_city() {
		return from_city;
	}
	public void setFrom_city(String from_city) {
		this.from_city = from_city;
	}
	public String getFrom_country() {
		return from_country;
	}
	public void setFrom_country(String from_country) {
		this.from_country = from_country;
	}
	public String getFrom_state() {
		return from_state;
	}
	public void setFrom_state(String from_state) {
		this.from_state = from_state;
	}
	public String getFxSiId() {
		return fxSiId;
	}
	public void setFxSiId(String fxSiId) {
		this.fxSiId = fxSiId;
	}
	public String getHub_location() {
		return hub_location;
	}
	public void setHub_location(String hub_location) {
		this.hub_location = hub_location;
	}
	public String getInstallation_addressaa1() {
		return installation_addressaa1;
	}
	public void setInstallation_addressaa1(String installation_addressaa1) {
		this.installation_addressaa1 = installation_addressaa1;
	}
	public String getInstallation_addressaa2() {
		return installation_addressaa2;
	}
	public void setInstallation_addressaa2(String installation_addressaa2) {
		this.installation_addressaa2 = installation_addressaa2;
	}
	public String getInstallation_addressaa3() {
		return installation_addressaa3;
	}
	public void setInstallation_addressaa3(String installation_addressaa3) {
		this.installation_addressaa3 = installation_addressaa3;
	}
	public String getInstallation_addressab1() {
		return installation_addressab1;
	}
	public void setInstallation_addressab1(String installation_addressab1) {
		this.installation_addressab1 = installation_addressab1;
	}
	public String getInstallation_addressab2() {
		return installation_addressab2;
	}
	public void setInstallation_addressab2(String installation_addressab2) {
		this.installation_addressab2 = installation_addressab2;
	}
	public String getInstallation_addressab3() {
		return installation_addressab3;
	}
	public void setInstallation_addressab3(String installation_addressab3) {
		this.installation_addressab3 = installation_addressab3;
	}
	public String getIpls() {
		return ipls;
	}
	public void setIpls(String ipls) {
		this.ipls = ipls;
	}
	public String getLineItemDescription() {
		return lineItemDescription;
	}
	public void setLineItemDescription(String lineItemDescription) {
		this.lineItemDescription = lineItemDescription;
	}
	public String getLob() {
		return lob;
	}
	public void setLob(String lob) {
		this.lob = lob;
	}
	public String getLocation_from() {
		return location_from;
	}
	public void setLocation_from(String location_from) {
		this.location_from = location_from;
	}
	public String getLocation_to() {
		return location_to;
	}
	public void setLocation_to(String location_to) {
		this.location_to = location_to;
	}
	public String getManaged_yes_no() {
		return managed_yes_no;
	}
	public void setManaged_yes_no(String managed_yes_no) {
		this.managed_yes_no = managed_yes_no;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getPlatform() {
		return platform;
	}
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	public String getPrjmngname() {
		return prjmngname;
	}
	public void setPrjmngname(String prjmngname) {
		this.prjmngname = prjmngname;
	}
	public String getService_provider() {
		return service_provider;
	}
	public void setService_provider(String service_provider) {
		this.service_provider = service_provider;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getTo_city() {
		return to_city;
	}
	public void setTo_city(String to_city) {
		this.to_city = to_city;
	}
	public String getTo_country() {
		return to_country;
	}
	public void setTo_country(String to_country) {
		this.to_country = to_country;
	}
	public String getTo_state() {
		return to_state;
	}
	public void setTo_state(String to_state) {
		this.to_state = to_state;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getDate_of_installation_from() {
		return date_of_installation_from;
	}
	public void setDate_of_installation_from(String date_of_installation_from) {
		this.date_of_installation_from = date_of_installation_from;
	}
	public String getDate_of_installation_to() {
		return date_of_installation_to;
	}
	public void setDate_of_installation_to(String date_of_installation_to) {
		this.date_of_installation_to = date_of_installation_to;
	}
	public String getLinename() {
		return linename;
	}
	public void setLinename(String linename) {
		this.linename = linename;
	}
	public String getLogical_SI_No() {
		return logical_SI_No;
	}
	public void setLogical_SI_No(String logical_SI_No) {
		this.logical_SI_No = logical_SI_No;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public int getPartyNo() {
		return partyNo;
	}
	public void setPartyNo(int partyNo) {
		this.partyNo = partyNo;
	}
	
	
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public String getLogicalSINo() {
		return logicalSINo;
	}
	public void setLogicalSINo(String logicalSINo) {
		this.logicalSINo = logicalSINo;
	}
	public int getParty_no() {
		return party_no;
	}
	public void setParty_no(int party_no) {
		this.party_no = party_no;
	}
	
	
	
	
	
	
	
	

}
