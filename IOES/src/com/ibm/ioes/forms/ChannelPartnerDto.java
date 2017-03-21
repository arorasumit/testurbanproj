package com.ibm.ioes.forms;

import java.util.ArrayList;

public class ChannelPartnerDto {

	private Long channelPartnerId;
	private String channelpartnerCode;
	private String channelpartnerCodeHidden;
	private String channelPartnerName;
	private int partner_Is_Active;
	private ArrayList<Long> cust_Segment_Id;
	private ArrayList<FieldEnginnerDto> addList=null;
	private ArrayList<FieldEnginnerDto> editList=null;
	private Long respId;
	private Long empId;
	
	public Long getChannelPartnerId() {
		return channelPartnerId;
	}
	public void setChannelPartnerId(Long channelPartnerId) {
		this.channelPartnerId = channelPartnerId;
	}
	public String getChannelpartnerCode() {
		return channelpartnerCode;
	}
	public void setChannelpartnerCode(String channelpartnerCode) {
		this.channelpartnerCode = channelpartnerCode;
	}
	public String getChannelPartnerName() {
		return channelPartnerName;
	}
	public void setChannelPartnerName(String channelPartnerName) {
		this.channelPartnerName = channelPartnerName;
	}
	public int getPartner_Is_Active() {
		return partner_Is_Active;
	}
	public void setPartner_Is_Active(int partner_Is_Active) {
		this.partner_Is_Active = partner_Is_Active;
	}
	public ArrayList<FieldEnginnerDto> getAddList() {
		return addList;
	}
	public void setAddList(ArrayList<FieldEnginnerDto> addList) {
		this.addList = addList;
	}
	public ArrayList<FieldEnginnerDto> getEditList() {
		return editList;
	}
	public void setEditList(ArrayList<FieldEnginnerDto> editList) {
		this.editList = editList;
	}
	public Long getEmpId() {
		return empId;
	}
	public void setEmpId(Long empId) {
		this.empId = empId;
	}
	public Long getRespId() {
		return respId;
	}
	public void setRespId(Long respId) {
		this.respId = respId;
	}
	public ArrayList<Long> getCust_Segment_Id() {
		return cust_Segment_Id;
	}
	public void setCust_Segment_Id(ArrayList<Long> cust_Segment_Id) {
		this.cust_Segment_Id = cust_Segment_Id;
	}
	public String getChannelpartnerCodeHidden() {
		return channelpartnerCodeHidden;
	}
	public void setChannelpartnerCodeHidden(String channelpartnerCodeHidden) {
		this.channelpartnerCodeHidden = channelpartnerCodeHidden;
	}
}
