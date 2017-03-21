package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;

public class TelemediaOrderReportDTO {
	PagingSorting pagingSorting = new PagingSorting();
	private String partyName;
	private String orderNo;
	private String fromCopcApprovedDate; 
	private String toCopcApprovedDate;
	private int isUsage;
	private String crmAccountNoString;
	private String child_act_no;
	private String copcApproveDate;
	public String getChild_act_no() {
		return child_act_no;
	}
	public void setChild_act_no(String child_act_no) {
		this.child_act_no = child_act_no;
	}
	public String getCopcApproveDate() {
		return copcApproveDate;
	}
	public void setCopcApproveDate(String copcApproveDate) {
		this.copcApproveDate = copcApproveDate;
	}
	public String getCrmAccountNoString() {
		return crmAccountNoString;
	}
	public void setCrmAccountNoString(String crmAccountNoString) {
		this.crmAccountNoString = crmAccountNoString;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
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
	public String getFromCopcApprovedDate() {
		return fromCopcApprovedDate;
	}
	public void setFromCopcApprovedDate(String fromCopcApprovedDate) {
		this.fromCopcApprovedDate = fromCopcApprovedDate;
	}
	public int getIsUsage() {
		return isUsage;
	}
	public void setIsUsage(int isUsage) {
		this.isUsage = isUsage;
	}
	public String getToCopcApprovedDate() {
		return toCopcApprovedDate;
	}
	public void setToCopcApprovedDate(String toCopcApprovedDate) {
		this.toCopcApprovedDate = toCopcApprovedDate;
	}

}
