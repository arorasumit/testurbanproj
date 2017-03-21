package com.ibm.ioes.ecrm;

import java.sql.Date;
import java.sql.Timestamp;

public class IB2BSingleViewDataDto {
	
	private int crmOrderId;
	private int serviceListId;
	private int orderLineId;
	private String circuitId;
	private Timestamp circuitDate;
	private int partyId;
	private int custAccountId;
	private int iB2BProductId;
	private int interfaceId;
	private int count;
	private Date creationDate;
	private String orderType;
	private String flag;
	private String productid;
	private String subproductid;
	private Date billingTriggerDate;
	private int circuitContractPeriod;
	private Date circuitEndDate;
	//Meenakshi : adding fields and their getter setters
	private String customerLogicalSI;
	private String logicalSI;
	private String m6_fx_progress_status;
	
	private String tpName;
	
	private String isThirdParty;
	
	public String getM6_fx_progress_status() {
		return m6_fx_progress_status;
	}
	public void setM6_fx_progress_status(String m6_fx_progress_status) {
		this.m6_fx_progress_status = m6_fx_progress_status;
	}
	private String lineItemID;
	private String lineItemName;
	private String redirected_LSI;
	private String linked_LSI;
	private String servicetypeid;
	private String serviceproductid;
	private String ref_priLocType;
	private String ref_priLocId;
	private String ref_secLocType;
	private String ref_secLocId;
	private String servicedetailid;
	private String loc_primary;
	private String ref_locationid;
	private String priloctype;
	private String bcpdetails_primary;
	private String address1_primary;
	private String address2_primary;
	private String address3_primary;
	private String address4_primary;
	private String city_primary;
	private String state_primary;
	private String country_primary;
	private String postal_primary;
	private String loccode_primary;
	private String address_primary;
	private String uom_l3;
	private String vsat;
	
	public String getVsat() {
		return vsat;
	}
	public void setVsat(String vsat) {
		this.vsat = vsat;
	}
	public String getUom_l3() {
		return uom_l3;
	}
	public void setUom_l3(String uom_l3) {
		this.uom_l3 = uom_l3;
	}
	public int getiB2BProductId() {
		return iB2BProductId;
	}
	public void setiB2BProductId(int iB2BProductId) {
		this.iB2BProductId = iB2BProductId;
	}
	public String getServiceproductid() {
		return serviceproductid;
	}
	public void setServiceproductid(String serviceproductid) {
		this.serviceproductid = serviceproductid;
	}
	public String getRef_priLocType() {
		return ref_priLocType;
	}
	public void setRef_priLocType(String ref_priLocType) {
		this.ref_priLocType = ref_priLocType;
	}
	public String getRef_priLocId() {
		return ref_priLocId;
	}
	public void setRef_priLocId(String ref_priLocId) {
		this.ref_priLocId = ref_priLocId;
	}
	public String getRef_secLocType() {
		return ref_secLocType;
	}
	public void setRef_secLocType(String ref_secLocType) {
		this.ref_secLocType = ref_secLocType;
	}
	public String getRef_secLocId() {
		return ref_secLocId;
	}
	public void setRef_secLocId(String ref_secLocId) {
		this.ref_secLocId = ref_secLocId;
	}
	public String getServicedetailid() {
		return servicedetailid;
	}
	public void setServicedetailid(String servicedetailid) {
		this.servicedetailid = servicedetailid;
	}
	public String getLoc_primary() {
		return loc_primary;
	}
	public void setLoc_primary(String loc_primary) {
		this.loc_primary = loc_primary;
	}
	public String getRef_locationid() {
		return ref_locationid;
	}
	public void setRef_locationid(String ref_locationid) {
		this.ref_locationid = ref_locationid;
	}
	public String getPriloctype() {
		return priloctype;
	}
	public void setPriloctype(String priloctype) {
		this.priloctype = priloctype;
	}
	public String getBcpdetails_primary() {
		return bcpdetails_primary;
	}
	public void setBcpdetails_primary(String bcpdetails_primary) {
		this.bcpdetails_primary = bcpdetails_primary;
	}
	public String getAddress1_primary() {
		return address1_primary;
	}
	public void setAddress1_primary(String address1_primary) {
		this.address1_primary = address1_primary;
	}
	public String getAddress2_primary() {
		return address2_primary;
	}
	public void setAddress2_primary(String address2_primary) {
		this.address2_primary = address2_primary;
	}
	public String getAddress3_primary() {
		return address3_primary;
	}
	public void setAddress3_primary(String address3_primary) {
		this.address3_primary = address3_primary;
	}
	public String getAddress4_primary() {
		return address4_primary;
	}
	public void setAddress4_primary(String address4_primary) {
		this.address4_primary = address4_primary;
	}
	public String getCity_primary() {
		return city_primary;
	}
	public void setCity_primary(String city_primary) {
		this.city_primary = city_primary;
	}
	public String getState_primary() {
		return state_primary;
	}
	public void setState_primary(String state_primary) {
		this.state_primary = state_primary;
	}
	public String getCountry_primary() {
		return country_primary;
	}
	public void setCountry_primary(String country_primary) {
		this.country_primary = country_primary;
	}
	public String getPostal_primary() {
		return postal_primary;
	}
	public void setPostal_primary(String postal_primary) {
		this.postal_primary = postal_primary;
	}
	public String getLoccode_primary() {
		return loccode_primary;
	}
	public void setLoccode_primary(String loccode_primary) {
		this.loccode_primary = loccode_primary;
	}
	public String getAddress_primary() {
		return address_primary;
	}
	public void setAddress_primary(String address_primary) {
		this.address_primary = address_primary;
	}
	public String getLoctype_secndry() {
		return loctype_secndry;
	}
	public void setLoctype_secndry(String loctype_secndry) {
		this.loctype_secndry = loctype_secndry;
	}
	public String getSec_loctype() {
		return sec_loctype;
	}
	public void setSec_loctype(String sec_loctype) {
		this.sec_loctype = sec_loctype;
	}
	public String getBcpdetails_secndry() {
		return bcpdetails_secndry;
	}
	public void setBcpdetails_secndry(String bcpdetails_secndry) {
		this.bcpdetails_secndry = bcpdetails_secndry;
	}
	public String getAddress1_secndry() {
		return address1_secndry;
	}
	public void setAddress1_secndry(String address1_secndry) {
		this.address1_secndry = address1_secndry;
	}
	public String getAddress2_secndry() {
		return address2_secndry;
	}
	public void setAddress2_secndry(String address2_secndry) {
		this.address2_secndry = address2_secndry;
	}
	public String getAddress3_secndry() {
		return address3_secndry;
	}
	public void setAddress3_secndry(String address3_secndry) {
		this.address3_secndry = address3_secndry;
	}
	public String getAddress4_secndry() {
		return address4_secndry;
	}
	public void setAddress4_secndry(String address4_secndry) {
		this.address4_secndry = address4_secndry;
	}
	public String getCity_secndry() {
		return city_secndry;
	}
	public void setCity_secndry(String city_secndry) {
		this.city_secndry = city_secndry;
	}
	public String getState_secndry() {
		return state_secndry;
	}
	public void setState_secndry(String state_secndry) {
		this.state_secndry = state_secndry;
	}
	public String getCountry_secndry() {
		return country_secndry;
	}
	public void setCountry_secndry(String country_secndry) {
		this.country_secndry = country_secndry;
	}
	public String getPostal_secndry() {
		return postal_secndry;
	}
	public void setPostal_secndry(String postal_secndry) {
		this.postal_secndry = postal_secndry;
	}
	public String getLoccode_secndry() {
		return loccode_secndry;
	}
	public void setLoccode_secndry(String loccode_secndry) {
		this.loccode_secndry = loccode_secndry;
	}
	public String getAddress_secndry() {
		return address_secndry;
	}
	public void setAddress_secndry(String address_secndry) {
		this.address_secndry = address_secndry;
	}
	private String loctype_secndry;
	private String sec_loctype;
	private String bcpdetails_secndry;
	private String address1_secndry;
	private String address2_secndry;
	private String address3_secndry;
	private String address4_secndry;
	private String city_secndry;
	private String state_secndry;
	private String country_secndry;
	private String postal_secndry;
	private String loccode_secndry;
	private String address_secndry;
	
	
	public String getServicetypeid() {
		return servicetypeid;
	}
	public void setServicetypeid(String servicetypeid) {
		this.servicetypeid = servicetypeid;
	}
	public String getPri_Address1() {
		return pri_Address1;
	}
	public void setPri_Address1(String pri_Address1) {
		this.pri_Address1 = pri_Address1;
	}
	public String getPri_Address2() {
		return pri_Address2;
	}
	public void setPri_Address2(String pri_Address2) {
		this.pri_Address2 = pri_Address2;
	}
	public String getPri_Address3() {
		return pri_Address3;
	}
	public void setPri_Address3(String pri_Address3) {
		this.pri_Address3 = pri_Address3;
	}
	public String getPri_Address4() {
		return pri_Address4;
	}
	public void setPri_Address4(String pri_Address4) {
		this.pri_Address4 = pri_Address4;
	}
	public String getPricity() {
		return pricity;
	}
	public void setPricity(String pricity) {
		this.pricity = pricity;
	}
	public String getPristate() {
		return pristate;
	}
	public void setPristate(String pristate) {
		this.pristate = pristate;
	}
	public String getPri_country() {
		return pri_country;
	}
	public void setPri_country(String pri_country) {
		this.pri_country = pri_country;
	}
	public String getPri_postal() {
		return pri_postal;
	}
	public void setPri_postal(String pri_postal) {
		this.pri_postal = pri_postal;
	}
	public String getSec_Address1() {
		return sec_Address1;
	}
	public void setSec_Address1(String sec_Address1) {
		this.sec_Address1 = sec_Address1;
	}
	public String getSec_Address2() {
		return sec_Address2;
	}
	public void setSec_Address2(String sec_Address2) {
		this.sec_Address2 = sec_Address2;
	}
	public String getSec_Address3() {
		return sec_Address3;
	}
	public void setSec_Address3(String sec_Address3) {
		this.sec_Address3 = sec_Address3;
	}
	public String getSec_Address4() {
		return sec_Address4;
	}
	public void setSec_Address4(String sec_Address4) {
		this.sec_Address4 = sec_Address4;
	}
	public String getSeccity() {
		return seccity;
	}
	public void setSeccity(String seccity) {
		this.seccity = seccity;
	}
	public String getSecstate() {
		return secstate;
	}
	public void setSecstate(String secstate) {
		this.secstate = secstate;
	}
	public String getSec_country() {
		return sec_country;
	}
	public void setSec_country(String sec_country) {
		this.sec_country = sec_country;
	}
	public String getSec_postal() {
		return sec_postal;
	}
	public void setSec_postal(String sec_postal) {
		this.sec_postal = sec_postal;
	}
	private String externalID;
	private String externalIDDesc;
	private String processFlag;
	private String createdby;
	private String modifiedby;
	private String bandwidth;
	private String bandwidth_uom;
	private String pri_Address1;
	private String pri_Address2;
	private String pri_Address3;
	private String pri_Address4;
	private String pricity;
	private String pristate;
	private String pri_country;
	private String pri_postal;
	private String sec_Address1;
	private String sec_Address2;
	private String sec_Address3;
	private String sec_Address4;
	private String seccity;
	private String secstate;
	private String sec_country;
	private String sec_postal;
	
	
	
	public String getBandwidth() {
		return bandwidth;
	}
	public void setBandwidth(String bandwidth) {
		this.bandwidth = bandwidth;
	}
	public String getBandwidth_uom() {
		return bandwidth_uom;
	}
	public void setBandwidth_uom(String bandwidth_uom) {
		this.bandwidth_uom = bandwidth_uom;
	}
	
	public Date getBillingTriggerDate() {
		return billingTriggerDate;
	}
	public void setBillingTriggerDate(Date billingTriggerDate) {
		this.billingTriggerDate = billingTriggerDate;
	}
	public int getCircuitContractPeriod() {
		return circuitContractPeriod;
	}
	public void setCircuitContractPeriod(int circuitContractPeriod) {
		this.circuitContractPeriod = circuitContractPeriod;
	}
	public Date getCircuitEndDate() {
		return circuitEndDate;
	}
	public void setCircuitEndDate(Date circuitEndDate) {
		this.circuitEndDate = circuitEndDate;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getProductid() {
		return productid;
	}
	public void setProductid(String productid) {
		this.productid = productid;
	}
	public String getSubproductid() {
		return subproductid;
	}
	public void setSubproductid(String subproductid) {
		this.subproductid = subproductid;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(int interfaceId) {
		this.interfaceId = interfaceId;
	}
	public Timestamp getCircuitDate() {
		return circuitDate;
	}
	public void setCircuitDate(Timestamp circuitDate) {
		this.circuitDate = circuitDate;
	}
	public String getCircuitId() {
		return circuitId;
	}
	public void setCircuitId(String circuitId) {
		this.circuitId = circuitId;
	}
	public int getCrmOrderId() {
		return crmOrderId;
	}
	public void setCrmOrderId(int crmOrderId) {
		this.crmOrderId = crmOrderId;
	}
	public int getCustAccountId() {
		return custAccountId;
	}
	public void setCustAccountId(int custAccountId) {
		this.custAccountId = custAccountId;
	}
	public int getIB2BProductId() {
		return iB2BProductId;
	}
	public void setIB2BProductId(int productId) {
		iB2BProductId = productId;
	}
	public int getOrderLineId() {
		return orderLineId;
	}
	public void setOrderLineId(int orderLineId) {
		this.orderLineId = orderLineId;
	}
	public int getPartyId() {
		return partyId;
	}
	public void setPartyId(int partyId) {
		this.partyId = partyId;
	}
	public int getServiceListId() {
		return serviceListId;
	}
	public void setServiceListId(int serviceListId) {
		this.serviceListId = serviceListId;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public String getCustomerLogicalSI() {
		return customerLogicalSI;
	}
	public void setCustomerLogicalSI(String customerLogicalSI) {
		this.customerLogicalSI = customerLogicalSI;
	}
	public String getExternalID() {
		return externalID;
	}
	public void setExternalID(String externalID) {
		this.externalID = externalID;
	}
	public String getLineItemID() {
		return lineItemID;
	}
	public void setLineItemID(String lineItemID) {
		this.lineItemID = lineItemID;
	}
	public String getLineItemName() {
		return lineItemName;
	}
	public void setLineItemName(String lineItemName) {
		this.lineItemName = lineItemName;
	}
	public String getLinked_LSI() {
		return linked_LSI;
	}
	public void setLinked_LSI(String linked_LSI) {
		this.linked_LSI = linked_LSI;
	}
	public String getLogicalSI() {
		return logicalSI;
	}
	public void setLogicalSI(String logicalSI) {
		this.logicalSI = logicalSI;
	}
	public String getRedirected_LSI() {
		return redirected_LSI;
	}
	public void setRedirected_LSI(String redirected_LSI) {
		this.redirected_LSI = redirected_LSI;
	}
	public String getCreatedby() {
		return createdby;
	}
	public void setCreatedby(String createdby) {
		this.createdby = createdby;
	}
	public String getExternalIDDesc() {
		return externalIDDesc;
	}
	public void setExternalIDDesc(String externalIDDesc) {
		this.externalIDDesc = externalIDDesc;
	}
	public String getModifiedby() {
		return modifiedby;
	}
	public void setModifiedby(String modifiedby) {
		this.modifiedby = modifiedby;
	}
	public String getProcessFlag() {
		return processFlag;
	}
	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}
	public void setTpName(String tpName) {
		this.tpName = tpName;
	}
	
	public String getTpName() {
		return tpName;
	}
	public String getIsThirdParty() {
		return isThirdParty;
	}
	public void setIsThirdParty(String isThirdParty) {
		this.isThirdParty = isThirdParty;
	}
	
}
