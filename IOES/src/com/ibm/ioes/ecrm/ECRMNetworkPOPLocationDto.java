/*
 * Created on Nov 9, 2010
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.ibm.ioes.ecrm;

/**
 * @author Ravish
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class ECRMNetworkPOPLocationDto 
{
	private long popLocId;
	private String popLocationName;
	private String popLocCode;
	private String popLocCity;
	private String popLocState;
	private String popLocAddress;
	private String UpdatedDate;
	private String createdDate;
	
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public String getPopLocAddress() {
		return popLocAddress;
	}
	public void setPopLocAddress(String popLocAddress) {
		this.popLocAddress = popLocAddress;
	}
	public String getPopLocationName() {
		return popLocationName;
	}
	public void setPopLocationName(String popLocationName) {
		this.popLocationName = popLocationName;
	}
	public String getPopLocCity() {
		return popLocCity;
	}
	public void setPopLocCity(String popLocCity) {
		this.popLocCity = popLocCity;
	}
	public String getPopLocCode() {
		return popLocCode;
	}
	public void setPopLocCode(String popLocCode) {
		this.popLocCode = popLocCode;
	}
	public long getPopLocId() {
		return popLocId;
	}
	public void setPopLocId(long popLocId) {
		this.popLocId = popLocId;
	}
	public String getPopLocState() {
		return popLocState;
	}
	public void setPopLocState(String popLocState) {
		this.popLocState = popLocState;
	}
	public String getUpdatedDate() {
		return UpdatedDate;
	}
	public void setUpdatedDate(String updatedDate) {
		UpdatedDate = updatedDate;
	}
	
}
