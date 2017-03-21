package com.ibm.fx.dto;
//[001]	VIPIN SAHARIA 17-07-2014 Added methods to get charge details for Service - Sales charges validation for DC hardware products.
import java.util.ArrayList;
import java.util.Set;

public class ChargesDto {
	ArrayList<NrcDto> nrcs=null;
	ArrayList<RcDto> rcs=null;
	
	private Long serviceProductId=null;
	private String accountExternalId = null;
	
	private int returnStatus=0;
	//[001] START VIPIN
	private Set<String> chargesList;
	private int ServiceId;
	private int createdInServiceId;
	private int isServTaxPresentHw;
	private String message;
	//[001] END VIPIN
	
	public int getReturnStatus() {
		return returnStatus;
	}
	public void setReturnStatus(int returnStatus) {
		this.returnStatus = returnStatus;
	}
	public String getAccountExternalId() {
		return accountExternalId;
	}
	public void setAccountExternalId(String accountExternalId) {
		this.accountExternalId = accountExternalId;
	}
	public Long getServiceProductId() {
		return serviceProductId;
	}
	public void setServiceProductId(Long serviceProductId) {
		this.serviceProductId = serviceProductId;
	}
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
	public Set<String> getChargesList() {
		return chargesList;
	}
	public void setChargesList(Set<String> chargesList) {
		this.chargesList = chargesList;
	}
	public int getServiceId() {
		return ServiceId;
	}
	public void setServiceId(int serviceId) {
		ServiceId = serviceId;
	}
	public int getCreatedInServiceId() {
		return createdInServiceId;
	}
	public void setCreatedInServiceId(int createdInServiceId) {
		this.createdInServiceId = createdInServiceId;
	}
	public int getIsServTaxPresentHw() {
		return isServTaxPresentHw;
	}
	public void setIsServTaxPresentHw(int isServTaxPresentHw) {
		this.isServTaxPresentHw = isServTaxPresentHw;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public ChargesDto(Long serviceProductId, Set<String> chargesList,
			int serviceId, int createdInServiceId, int isServTaxPresentHw) {
		super();
		this.serviceProductId = serviceProductId;
		this.chargesList = chargesList;
		ServiceId = serviceId;
		this.createdInServiceId = createdInServiceId;
		this.isServTaxPresentHw = isServTaxPresentHw;
	}
	public ChargesDto(){
		//do
	}
	
}
