package com.ibm.ioes.forms;

import java.util.Date;

import com.ibm.ioes.utilities.PagingSorting;

public class ArchivalReportDto {
	
	PagingSorting pagingSorting = new PagingSorting();

	
	private String 	 account_id;
	private String 	 account_manager;
	private String 	 account_number;
	private Double 	 po_amount;
	private String 	 customer_segment;
	private String 	 vertical;
	private String 	 billing_charge_start_date;
	private String 	 service_name;
	private String 	 order_line_no;
	private String 	 line_name;
	private String 	 cancel_flag;
	private String 	 uom;
	private String 	 billing_bandwidth;
	private String 	 bill_uom;
	private String 	 category_of_order;
	private String 	 contract_period;
	private String 	 order_creation_date;
	private String 	 customer_rfs_date;
	private String 	 customer_service_rfs_date;
	private String 	 currency;
	private String 	 customer_po_date;
	private String 	 customer_po_number;
	private String 	 cyclic_or_non_cyclic;
	private String 	 from_site;
	private String 	 item_quantity;
	private Double 	 line_item_amount;
	private String 	 copc_approved_date;
	private String 	 line_item_description;
	private String 	 loc_date;
	private String 	 account_manager_receive_date;
	private Double 	 order_total;
	private String 	 order_entry_date;
	private String 	 taxation;
	private String 	 taxexemption_reason;
	private String 	 licence_company;
	private String 	 logical_circuit_id;
	private String 	 order_type;
	private String 	 payment_term;
	private String 	 project_mgr;
	private String 	 region_name;
	private String 	 old_line_item_amount;
	private String 	 demo_type;
	private String 	 party_name;
	private String 	 order_stage_description;
	private String 	 service_stage_description;
	private String 	 charge_end_date;
	private String 	 new_order_remark;
	private String 	 remarks;
	private String 	 service_order_type;
	private String 	 osp;
	private String 	 opportunity_id;
	private String 	 account_category;
	private String 	 provision_bandwidth;
	private String 	 company_name;
	private String 	 charge_name;
	private String 	 challen_no;
	private String 	 order_no;
	private String 	 to_site;
	private String 	 kms_distance;
	private String 	 end_date_logic;
	private String 	 store_address;
	private String 	 fromdate;
	private String 	 todate;
	private String 	 m6orderno;
	private String 	 logicalsino;
	private String 	 line_it_no;
	private String 	 logical_si_no;
	private String 	 interfaceid;
	private String 	 ckt_id;
	private String 	 circuit_id;
	private String 	 cust_logical_si_no;
	private String 	 billing_trig_date;  
	private String 	 dispatch_address;   
	private String 	po_number;
	private String service_id;
	private String oldLsi;
	private String cancel_reason;
	private String cancel_date;
	private String product;
	private String sub_product;
	private String effective_start_data;
	
	
	
	
	
	public Double getLine_item_amount() {
		return line_item_amount;
	}
	public void setLine_item_amount(Double line_item_amount) {
		this.line_item_amount = line_item_amount;
	}
	public String getOldLsi() {
		return oldLsi;
	}
	public void setOldLsi(String oldLsi) {
		this.oldLsi = oldLsi;
	}
	public Double getPo_amount() {
		return po_amount;
	}
	public void setPo_amount(Double po_amount) {
		this.po_amount = po_amount;
	}
	public Double getOrder_total() {
		return order_total;
	}
	public void setOrder_total(Double order_total) {
		this.order_total = order_total;
	}
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	public String getCancel_reason() {
		return cancel_reason;
	}
	public void setCancel_reason(String cancel_reason) {
		this.cancel_reason = cancel_reason;
	}
	public String getCancel_date() {
		return cancel_date;
	}
	public void setCancel_date(String cancel_date) {
		this.cancel_date = cancel_date;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public String getSub_product() {
		return sub_product;
	}
	public void setSub_product(String sub_product) {
		this.sub_product = sub_product;
	}
	public String getEffective_start_data() {
		return effective_start_data;
	}
	public void setEffective_start_data(String effective_start_data) {
		this.effective_start_data = effective_start_data;
	}
	public PagingSorting getPagingSorting() {
		return pagingSorting;
	}
	public void setPagingSorting(PagingSorting pagingSorting) {
		this.pagingSorting = pagingSorting;
	}
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getAccount_manager() {
		return account_manager;
	}
	public void setAccount_manager(String account_manager) {
		this.account_manager = account_manager;
	}
	public String getAccount_number() {
		return account_number;
	}
	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}
	
	public String getCustomer_segment() {
		return customer_segment;
	}
	public void setCustomer_segment(String customer_segment) {
		this.customer_segment = customer_segment;
	}
	public String getVertical() {
		return vertical;
	}
	public void setVertical(String vertical) {
		this.vertical = vertical;
	}
	public String getBilling_charge_start_date() {
		return billing_charge_start_date;
	}
	public void setBilling_charge_start_date(String billing_charge_start_date) {
		this.billing_charge_start_date = billing_charge_start_date;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public String getOrder_line_no() {
		return order_line_no;
	}
	public void setOrder_line_no(String order_line_no) {
		this.order_line_no = order_line_no;
	}
	public String getLine_name() {
		return line_name;
	}
	public void setLine_name(String line_name) {
		this.line_name = line_name;
	}
	public String getCancel_flag() {
		return cancel_flag;
	}
	public void setCancel_flag(String cancel_flag) {
		this.cancel_flag = cancel_flag;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getBilling_bandwidth() {
		return billing_bandwidth;
	}
	public void setBilling_bandwidth(String billing_bandwidth) {
		this.billing_bandwidth = billing_bandwidth;
	}
	public String getBill_uom() {
		return bill_uom;
	}
	public void setBill_uom(String bill_uom) {
		this.bill_uom = bill_uom;
	}
	public String getCategory_of_order() {
		return category_of_order;
	}
	public void setCategory_of_order(String category_of_order) {
		this.category_of_order = category_of_order;
	}
	public String getContract_period() {
		return contract_period;
	}
	public void setContract_period(String contract_period) {
		this.contract_period = contract_period;
	}
	public String getOrder_creation_date() {
		return order_creation_date;
	}
	public void setOrder_creation_date(String order_creation_date) {
		this.order_creation_date = order_creation_date;
	}
	public String getCustomer_rfs_date() {
		return customer_rfs_date;
	}
	public void setCustomer_rfs_date(String customer_rfs_date) {
		this.customer_rfs_date = customer_rfs_date;
	}
	public String getCustomer_service_rfs_date() {
		return customer_service_rfs_date;
	}
	public void setCustomer_service_rfs_date(String customer_service_rfs_date) {
		this.customer_service_rfs_date = customer_service_rfs_date;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getCustomer_po_date() {
		return customer_po_date;
	}
	public void setCustomer_po_date(String customer_po_date) {
		this.customer_po_date = customer_po_date;
	}
	public String getCustomer_po_number() {
		return customer_po_number;
	}
	public void setCustomer_po_number(String customer_po_number) {
		this.customer_po_number = customer_po_number;
	}
	public String getCyclic_or_non_cyclic() {
		return cyclic_or_non_cyclic;
	}
	public void setCyclic_or_non_cyclic(String cyclic_or_non_cyclic) {
		this.cyclic_or_non_cyclic = cyclic_or_non_cyclic;
	}
	public String getFrom_site() {
		return from_site;
	}
	public void setFrom_site(String from_site) {
		this.from_site = from_site;
	}
	public String getItem_quantity() {
		return item_quantity;
	}
	public void setItem_quantity(String item_quantity) {
		this.item_quantity = item_quantity;
	}
	
	public String getCopc_approved_date() {
		return copc_approved_date;
	}
	public void setCopc_approved_date(String copc_approved_date) {
		this.copc_approved_date = copc_approved_date;
	}
	public String getLine_item_description() {
		return line_item_description;
	}
	public void setLine_item_description(String line_item_description) {
		this.line_item_description = line_item_description;
	}
	public String getLoc_date() {
		return loc_date;
	}
	public void setLoc_date(String loc_date) {
		this.loc_date = loc_date;
	}
	public String getAccount_manager_receive_date() {
		return account_manager_receive_date;
	}
	public void setAccount_manager_receive_date(String account_manager_receive_date) {
		this.account_manager_receive_date = account_manager_receive_date;
	}
	public String getOrder_entry_date() {
		return order_entry_date;
	}
	public void setOrder_entry_date(String order_entry_date) {
		this.order_entry_date = order_entry_date;
	}
	public String getTaxation() {
		return taxation;
	}
	public void setTaxation(String taxation) {
		this.taxation = taxation;
	}
	public String getTaxexemption_reason() {
		return taxexemption_reason;
	}
	public void setTaxexemption_reason(String taxexemption_reason) {
		this.taxexemption_reason = taxexemption_reason;
	}
	public String getLicence_company() {
		return licence_company;
	}
	public void setLicence_company(String licence_company) {
		this.licence_company = licence_company;
	}
	public String getLogical_circuit_id() {
		return logical_circuit_id;
	}
	public void setLogical_circuit_id(String logical_circuit_id) {
		this.logical_circuit_id = logical_circuit_id;
	}
	public String getOrder_type() {
		return order_type;
	}
	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}
	public String getPayment_term() {
		return payment_term;
	}
	public void setPayment_term(String payment_term) {
		this.payment_term = payment_term;
	}
	public String getProject_mgr() {
		return project_mgr;
	}
	public void setProject_mgr(String project_mgr) {
		this.project_mgr = project_mgr;
	}
	public String getRegion_name() {
		return region_name;
	}
	public void setRegion_name(String region_name) {
		this.region_name = region_name;
	}
	public String getOld_line_item_amount() {
		return old_line_item_amount;
	}
	public void setOld_line_item_amount(String old_line_item_amount) {
		this.old_line_item_amount = old_line_item_amount;
	}
	public String getDemo_type() {
		return demo_type;
	}
	public void setDemo_type(String demo_type) {
		this.demo_type = demo_type;
	}
	public String getParty_name() {
		return party_name;
	}
	public void setParty_name(String party_name) {
		this.party_name = party_name;
	}
	public String getOrder_stage_description() {
		return order_stage_description;
	}
	public void setOrder_stage_description(String order_stage_description) {
		this.order_stage_description = order_stage_description;
	}
	public String getService_stage_description() {
		return service_stage_description;
	}
	public void setService_stage_description(String service_stage_description) {
		this.service_stage_description = service_stage_description;
	}
	public String getCharge_end_date() {
		return charge_end_date;
	}
	public void setCharge_end_date(String charge_end_date) {
		this.charge_end_date = charge_end_date;
	}
	public String getNew_order_remark() {
		return new_order_remark;
	}
	public void setNew_order_remark(String new_order_remark) {
		this.new_order_remark = new_order_remark;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getService_order_type() {
		return service_order_type;
	}
	public void setService_order_type(String service_order_type) {
		this.service_order_type = service_order_type;
	}
	public String getOsp() {
		return osp;
	}
	public void setOsp(String osp) {
		this.osp = osp;
	}
	public String getOpportunity_id() {
		return opportunity_id;
	}
	public void setOpportunity_id(String opportunity_id) {
		this.opportunity_id = opportunity_id;
	}
	public String getAccount_category() {
		return account_category;
	}
	public void setAccount_category(String account_category) {
		this.account_category = account_category;
	}
	public String getProvision_bandwidth() {
		return provision_bandwidth;
	}
	public void setProvision_bandwidth(String provision_bandwidth) {
		this.provision_bandwidth = provision_bandwidth;
	}
	public String getCompany_name() {
		return company_name;
	}
	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}
	public String getCharge_name() {
		return charge_name;
	}
	public void setCharge_name(String charge_name) {
		this.charge_name = charge_name;
	}
	public String getChallen_no() {
		return challen_no;
	}
	public void setChallen_no(String challen_no) {
		this.challen_no = challen_no;
	}
	public String getOrder_no() {
		return order_no;
	}
	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}
	public String getTo_site() {
		return to_site;
	}
	public void setTo_site(String to_site) {
		this.to_site = to_site;
	}
	public String getKms_distance() {
		return kms_distance;
	}
	public void setKms_distance(String kms_distance) {
		this.kms_distance = kms_distance;
	}
	public String getEnd_date_logic() {
		return end_date_logic;
	}
	public void setEnd_date_logic(String end_date_logic) {
		this.end_date_logic = end_date_logic;
	}
	public String getStore_address() {
		return store_address;
	}
	public void setStore_address(String store_address) {
		this.store_address = store_address;
	}
	public String getFromdate() {
		return fromdate;
	}
	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}
	public String getTodate() {
		return todate;
	}
	public void setTodate(String todate) {
		this.todate = todate;
	}
	public String getM6orderno() {
		return m6orderno;
	}
	public void setM6orderno(String m6orderno) {
		this.m6orderno = m6orderno;
	}
	public String getLogicalsino() {
		return logicalsino;
	}
	public void setLogicalsino(String logicalsino) {
		this.logicalsino = logicalsino;
	}
	public String getLine_it_no() {
		return line_it_no;
	}
	public void setLine_it_no(String line_it_no) {
		this.line_it_no = line_it_no;
	}
	public String getLogical_si_no() {
		return logical_si_no;
	}
	public void setLogical_si_no(String logical_si_no) {
		this.logical_si_no = logical_si_no;
	}
	public String getInterfaceid() {
		return interfaceid;
	}
	public void setInterfaceid(String interfaceid) {
		this.interfaceid = interfaceid;
	}
	public String getCkt_id() {
		return ckt_id;
	}
	public void setCkt_id(String ckt_id) {
		this.ckt_id = ckt_id;
	}
	public String getCircuit_id() {
		return circuit_id;
	}
	public void setCircuit_id(String circuit_id) {
		this.circuit_id = circuit_id;
	}
	public String getCust_logical_si_no() {
		return cust_logical_si_no;
	}
	public void setCust_logical_si_no(String cust_logical_si_no) {
		this.cust_logical_si_no = cust_logical_si_no;
	}
	public String getBilling_trig_date() {
		return billing_trig_date;
	}
	public void setBilling_trig_date(String billing_trig_date) {
		this.billing_trig_date = billing_trig_date;
	}
	public String getDispatch_address() {
		return dispatch_address;
	}
	public void setDispatch_address(String dispatch_address) {
		this.dispatch_address = dispatch_address;
	}
	public String getPo_number() {
		return po_number;
	}
	public void setPo_number(String po_number) {
		this.po_number = po_number;
	}
       



}