package com.ibm.ioes.newdesign.dto;

import com.ibm.ioes.utilities.PagingSorting;

public class AttributeDetailsReportDTO {
	PagingSorting pagingSorting = new PagingSorting();
	private int serviceTypeId;
	private String serviceTypeName;
	private String fromDate;
	private String toDate;
	private String serviceName;
	private String linename;
    private String crm_att;
    private String m6_label_name;
	private String m6_label_value;
	private int custLogicalSI;
    private int crm_order_id;
    
    
    public String getCrm_att() {
		return crm_att;
	}
	public void setCrm_att(String crm_att) {
		this.crm_att = crm_att;
	}
	public int getCrm_order_id() {
		return crm_order_id;
	}
	public void setCrm_order_id(int crm_order_id) {
		this.crm_order_id = crm_order_id;
	}
	public int getCustLogicalSI() {
		return custLogicalSI;
	}
	public void setCustLogicalSI(int custLogicalSI) {
		this.custLogicalSI = custLogicalSI;
	}
	public String getLinename() {
		return linename;
	}
	public void setLinename(String linename) {
		this.linename = linename;
	}
	public String getM6_label_name() {
		return m6_label_name;
	}
	public void setM6_label_name(String m6_label_name) {
		this.m6_label_name = m6_label_name;
	}
	public String getM6_label_value() {
		return m6_label_value;
	}
	public void setM6_label_value(String m6_label_value) {
		this.m6_label_value = m6_label_value;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public PagingSorting getPagingSorting() {
		// TODO Auto-generated method stub
		return pagingSorting;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public int getServiceTypeId() {
		return serviceTypeId;
	}
	public void setServiceTypeId(int serviceTypeId) {
		this.serviceTypeId = serviceTypeId;
	}
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

}
