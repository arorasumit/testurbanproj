package com.ibm.ioes.beans;

import java.util.ArrayList;
import java.util.Date;

import org.apache.struts.action.ActionForm;

import com.ibm.ioes.forms.ArchivalReportDto;
import com.ibm.ioes.utilities.PagingSorting;

public class ArchivalReportBean extends ActionForm  {
	

		private static final long serialVersionUID = -3998346555255256068L;

		PagingSorting pagingSorting = new PagingSorting();
		private int sNo;
		private String ACCOUNT_ID;
		private String ACCOUNT_MANAGER;
		private String ACCOUNT_NUMBER;
		private Double PO_AMOUNT;
		private String CUSTOMER_SEGMENT;
		private String VERTICAL;
		private String BILLING_CHARGE_START_DATE;
		private String SERVICE_NAME;
		private String ORDER_LINE_NO;
		private String LINE_NAME;
		private String CANCEL_FLAG;
		private String UOM;
		private String BILLING_BANDWIDTH;
		private String BILL_UOM;
		private String CATEGORY_OF_ORDER;
		private String CONTRACT_PERIOD;
		private String ORDER_CREATION_DATE;
		private String CUSTOMER_RFS_DATE;
		private String CUSTOMER_SERVICE_RFS_DATE;
		private String CURRENCY;
		private String CUSTOMER_PO_DATE;
		private String CUSTOMER_PO_NUMBER;
		private String CYCLIC_OR_NON_CYCLIC;
		private String FROM_SITE;
		private String ITEM_QUANTITY;
		private String LINE_ITEM_AMOUNT;
		private String COPC_APPROVED_DATE;
		private String LINE_ITEM_DESCRIPTION;
		private String LOC_DATE;
		private String ACCOUNT_MANAGER_RECEIVE_DATE;
		private Double ORDER_TOTAL;
		private String ORDER_ENTRY_DATE;
		private String TAXATION;
		private String TAXEXEMPTION_REASON;
		private String LICENCE_COMPANY;
		private String LOGICAL_CIRCUIT_ID;
		private String ORDER_TYPE;
		private String PAYMENT_TERM;
		private String PROJECT_MGR;
		private String REGION_NAME;
		private String OLD_LINE_ITEM_AMOUNT;
		private String DEMO_TYPE;
		private String PARTY_NAME;
		private String ORDER_STAGE_DESCRIPTION;
		private String SERVICE_STAGE_DESCRIPTION;
		private String CHARGE_END_DATE;
		private String NEW_ORDER_REMARK;
		private String REMARKS;
		private String SERVICE_ORDER_TYPE;
		private String OSP;
		private String OPPORTUNITY_ID;
		private String ACCOUNT_CATEGORY;
		private String PROVISION_BANDWIDTH;
		private String COMPANY_NAME;
		private String CHARGE_NAME;
		private String CHALLEN_NO;
		private String ORDER_NO;
		private String TO_SITE;
		private String KMS_DISTANCE;
		private String END_DATE_LOGIC;
		private String STORE_ADDRESS;
		private String FROMDATE;
		private String TODATE;
		private String M6ORDERNO;
		private String LOGICALSINO;
		private String LINE_IT_NO;
		private String LOGICAL_SI_NO;
		private String ToExcel;
		private ArrayList BcpExcelLis;
		private ArrayList CustomerList;
		private String LOC_Date;
		private String InterfaceId;
		//For PDOrder
		private String account_mgr;
		private String amt;
		private String annotation;
		private String annual;
		private String annual_rate;
		private String rate;
		private String bandwidth;
		private String bandwidth_uom;
		private String bill_format;
		private String bill_period;
		private String bill_trg_Create_date;
		private String bill_type;
		private String billing_bandwidth;
		private String billing_bandwidth_uom;
		private String billing_level;
		private String billing_level_number;
		private String billing_location_from;
		private String billing_location_to;
		private String billing_mode;
		private String billing_trig_flag;
		private String challen_date;
		private String charge_end_date;
		private String charge_hdr_id;
		private String charge_name;
		private String charge_start_date;
		private String charge_status;
		private String charge_type;
		private String charge_type_id;
		private String chargeable_distance;
		private String child_acc_fx_status;
		private String child_acc_no;
		private String circuit_id;
		private String commitment_period;
		private String contract_period_months;
		private String copc_approval_date;
		private String credit_period;
		private String currency;
		private String cust_acc_id;
		private String cust_logical_si_no;
		private String cust_po_date;
		private String cust_po_number;
		private String cust_po_receive_date;
		private String customer_segment;
		private String customer_service_rfs_date;
		private String demo_type;
		private String disconnection_remark;
		private String end_date_days;
		private String end_date_logic;
		private String end_date_months;
		private String form_c_available;
		private String frequency;
		private String hardware_flag;
		private String indicative_value;
		private String inv_amt;
		private String last_mile_media;
		private String last_mile_provider;
		private String last_mile_remarks;
		private String legal_entity;
		private String licence_company;
		private String link_type;
		private String loc_date;
		private String loc_number;
		private String logical_circuit_id;
		private String m6_order_id;
		private String nature_of_sale;
		private String new_order_remarks;
		private String order_creation_date;
		private String order_date;
		private String order_line_id;
		private String order_month;
		private String order_number;
		private String order_stage;
		private String order_type;
		private String party;
		private String party_id;
		private String penalty_clause;
		private String period_in_month;
		private String pk_chageges_id;
		private String pm_prov_date;
		private String po_date;
		private String pre_crm_order_id;
		private String pri_loc;
		private String product;
		private String product_name;
		private String rate_code;
		private String region;
		private String request_received_date;
		private String sec_loc;
		private String service_order_type;
		private String service_stage;
		private String sr_number;
		private String standard_reason;
		private String start_date_days;
		private String start_date_logic;
		private String start_date_months;
		private String store;
		private String taxation;
		private String token_no;
		private String tot_po_amt;
		private String total_amount;
		private String type_of_sale;
		private String vertical;
		private String project_mgr;
		private String project_mgr_email;
		private String provision_bandwidth;
		private String quote_no;
		private String ratio;
		private String region_name;
		private String re_logged_lsi_no;
		private String service_name;
		private String service_number;
		private String sub_change_type;
		private String sub_product_type;
		private String taxexcemption_reason;
		private String to_city;
		private String to_site;
		private String uom;
		private String zone;
		private String dis_sr;
		private String dod;
		private String fromDate;
		private String toDate;
		private String hardware_type;
		
		private String package_id;
		private String package_name;
		private String componentinfoid;
		private String component_id;
		private String component_name;
		private String component_status;
		private String component_start_logic;
		private String component_start_date;
		private String component_end_logic;
		private String component_end_date;
		private String comp_start_days;
		private String comp_start_months;
		private String comp_end_days;
		private String comp_end_months;
		private String component_type;
		private String component_instance_id;
		private String start_component_token_no;
		private String end_component_token_no;
		private String taxexemption_reason;
		private String billing_trig_date;
		private String dispatch_address;
		private String po_number;
		
		// For Cancelled Order
		
		private String service_type;
		private String creation_date;
		private String effective_start_data;
		private String customer_rfs_date;
		private String sub_product;
		private String account_no;
		private String logical_si_no;
		private String cancel_reason;
		private String cancel_date;
		private String bi_source;
		private String demo_order;
		private String old_lsi;
		private String order_no;
		private String line_item_no;
		private String ckt_id;
		private String m6_order_no;
		private String notice_period;
		private String service_no;
		private String line_it_no;
		private String account_id;
		//private String CIRCUIT_ID;
		//private String CUST_LOGICAL_SI_NO;
		//For Archival
		private String group_id;
		private String archival_phase;
		private int isValidated;
		private int iseLIGIBLE;
		private int isExistInDb;
		
		
		public int getIsExistInDb() {
			return isExistInDb;
		}
		public void setIsExistInDb(int isExistInDb) {
			this.isExistInDb = isExistInDb;
		}
		public int getiseLIGIBLE() {
			return isValidated;
		}
		/**
		 * @param isValidated the isValidated to set
		 */
		public void setiseLIGIBLE(int iseLIGIBLE) {
			this.iseLIGIBLE = iseLIGIBLE;
		}
	
		/**
		 * @return the isValidated
		 */
		public int getIsValidated() {
			return isValidated;
		}
		/**
		 * @param isValidated the isValidated to set
		 */
		public void setIsValidated(int isValidated) {
			this.isValidated = isValidated;
		}
		/**
		 * @return the archival_phase
		 */
		public String getArchival_phase() {
			return archival_phase;
		}
		/**
		 * @param archival_phase the archival_phase to set
		 */
		public void setArchival_phase(String archival_phase) {
			this.archival_phase = archival_phase;
		}
		/**
		 * @return the group_id
		 */
		public String getGroup_id() {
			return group_id;
		}
		/**
		 * @param group_id the group_id to set
		 */
		public void setGroup_id(String group_id) {
			this.group_id = group_id;
		}
		public String getTaxexemption_reason() {
			return taxexemption_reason;
		}
		public Double getPO_AMOUNT() {
			return PO_AMOUNT;
		}
		public void setPO_AMOUNT(Double pO_AMOUNT) {
			PO_AMOUNT = pO_AMOUNT;
		}
		public Double getORDER_TOTAL() {
			return ORDER_TOTAL;
		}
		public void setORDER_TOTAL(Double oRDER_TOTAL) {
			ORDER_TOTAL = oRDER_TOTAL;
		}
		public String getTAXEXEMPTION_REASON() {
			return TAXEXEMPTION_REASON;
		}
		public void setTAXEXEMPTION_REASON(String tAXEXEMPTION_REASON) {
			TAXEXEMPTION_REASON = tAXEXEMPTION_REASON;
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
		public void setTaxexemption_reason(String taxexemption_reason) {
			this.taxexemption_reason = taxexemption_reason;
		}
		public String getContract_period_months() {
			return contract_period_months;
		}
		public void setContract_period_months(String contract_period_months) {
			this.contract_period_months = contract_period_months;
		}
		public String getSub_change_type() {
			return sub_change_type;
		}
		public void setSub_change_type(String sub_change_type) {
			this.sub_change_type = sub_change_type;
		}
		public String getLink_type() {
			return link_type;
		}
		public void setLink_type(String link_type) {
			this.link_type = link_type;
		}
		public String getPri_loc() {
			return pri_loc;
		}
		public void setPri_loc(String pri_loc) {
			this.pri_loc = pri_loc;
		}
		public String getRate_code() {
			return rate_code;
		}
		public void setRate_code(String rate_code) {
			this.rate_code = rate_code;
		}
		public String getPackage_id() {
			return package_id;
		}
		public void setPackage_id(String package_id) {
			this.package_id = package_id;
		}
		public String getPackage_name() {
			return package_name;
		}
		public void setPackage_name(String package_name) {
			this.package_name = package_name;
		}
		public String getComponentinfoid() {
			return componentinfoid;
		}
		public void setComponentinfoid(String componentinfoid) {
			this.componentinfoid = componentinfoid;
		}
		public String getComponent_id() {
			return component_id;
		}
		public void setComponent_id(String component_id) {
			this.component_id = component_id;
		}
		public String getComponent_name() {
			return component_name;
		}
		public void setComponent_name(String component_name) {
			this.component_name = component_name;
		}
		public String getComponent_status() {
			return component_status;
		}
		public void setComponent_status(String component_status) {
			this.component_status = component_status;
		}
		public String getComponent_start_logic() {
			return component_start_logic;
		}
		public void setComponent_start_logic(String component_start_logic) {
			this.component_start_logic = component_start_logic;
		}
		public String getComponent_start_date() {
			return component_start_date;
		}
		public void setComponent_start_date(String component_start_date) {
			this.component_start_date = component_start_date;
		}
		public String getComponent_end_logic() {
			return component_end_logic;
		}
		public void setComponent_end_logic(String component_end_logic) {
			this.component_end_logic = component_end_logic;
		}
		public String getComponent_end_date() {
			return component_end_date;
		}
		public void setComponent_end_date(String component_end_date) {
			this.component_end_date = component_end_date;
		}
		public String getComp_start_days() {
			return comp_start_days;
		}
		public void setComp_start_days(String comp_start_days) {
			this.comp_start_days = comp_start_days;
		}
		public String getComp_start_months() {
			return comp_start_months;
		}
		public void setComp_start_months(String comp_start_months) {
			this.comp_start_months = comp_start_months;
		}
		public String getComp_end_days() {
			return comp_end_days;
		}
		public void setComp_end_days(String comp_end_days) {
			this.comp_end_days = comp_end_days;
		}
		public String getComp_end_months() {
			return comp_end_months;
		}
		public void setComp_end_months(String comp_end_months) {
			this.comp_end_months = comp_end_months;
		}
		public String getComponent_type() {
			return component_type;
		}
		public void setComponent_type(String component_type) {
			this.component_type = component_type;
		}
		public String getComponent_instance_id() {
			return component_instance_id;
		}
		public void setComponent_instance_id(String component_instance_id) {
			this.component_instance_id = component_instance_id;
		}
		public String getStart_component_token_no() {
			return start_component_token_no;
		}
		public void setStart_component_token_no(String start_component_token_no) {
			this.start_component_token_no = start_component_token_no;
		}
		public String getEnd_component_token_no() {
			return end_component_token_no;
		}
		public void setEnd_component_token_no(String end_component_token_no) {
			this.end_component_token_no = end_component_token_no;
		}
		public String getHardware_type() {
			return hardware_type;
		}
		public void setHardware_type(String hardware_type) {
			this.hardware_type = hardware_type;
		}
		public String getLine_it_no() {
			return line_it_no;
		}
		public void setLine_it_no(String line_it_no) {
			this.line_it_no = line_it_no;
		}
		public String getAccount_id() {
			return account_id;
		}
		public void setAccount_id(String account_id) {
			this.account_id = account_id;
		}
		public String getNotice_period() {
			return notice_period;
		}
		public void setNotice_period(String notice_period) {
			this.notice_period = notice_period;
		}
		public String getService_no() {
			return service_no;
		}
		public void setService_no(String service_no) {
			this.service_no = service_no;
		}
	
		public String getService_type() {
			return service_type;
		}
		public void setService_type(String service_type) {
			this.service_type = service_type;
		}
		public String getCreation_date() {
			return creation_date;
		}
		public void setCreation_date(String creation_date) {
			this.creation_date = creation_date;
		}
		public String getEffective_start_data() {
			return effective_start_data;
		}
		public void setEffective_start_data(String effective_start_data) {
			this.effective_start_data = effective_start_data;
		}
		public String getCustomer_rfs_date() {
			return customer_rfs_date;
		}
		public void setCustomer_rfs_date(String customer_rfs_date) {
			this.customer_rfs_date = customer_rfs_date;
		}
		public String getLogical_si_no() {
			return logical_si_no;
		}
		public void setLogical_si_no(String logical_si_no) {
			this.logical_si_no = logical_si_no;
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
		public String getBi_source() {
			return bi_source;
		}
		public void setBi_source(String bi_source) {
			this.bi_source = bi_source;
		}
		public String getDemo_order() {
			return demo_order;
		}
		public void setDemo_order(String demo_order) {
			this.demo_order = demo_order;
		}
		public String getOld_lsi() {
			return old_lsi;
		}
		public void setOld_lsi(String old_lsi) {
			this.old_lsi = old_lsi;
		}
		public String getOrder_no() {
			return order_no;
		}
		public void setOrder_no(String order_no) {
			this.order_no = order_no;
		}
		public String getLine_item_no() {
			return line_item_no;
		}
		public void setLine_item_no(String line_item_no) {
			this.line_item_no = line_item_no;
		}
		public String getCkt_id() {
			return ckt_id;
		}
		public void setCkt_id(String ckt_id) {
			this.ckt_id = ckt_id;
		}
		public String getM6_order_no() {
			return m6_order_no;
		}
		public void setM6_order_no(String m6_order_no) {
			this.m6_order_no = m6_order_no;
		}
		public String getFromDate() {
			return fromDate;
		}
		public void setFromDate(String fromDate) {
			this.fromDate = fromDate;
		}
		public String getToDate() {
			return toDate;
		}
		public void setToDate(String toDate) {
			this.toDate = toDate;
		}
		public String getBilling_bandwidth() {
			return billing_bandwidth;
		}
		public String getBILLING_BANDWIDTH() {
			return BILLING_BANDWIDTH;
		}
		public void setBILLING_BANDWIDTH(String bILLING_BANDWIDTH) {
			BILLING_BANDWIDTH = bILLING_BANDWIDTH;
		}
		public String getBill_type() {
			return bill_type;
		}
		public void setBill_type(String bill_type) {
			this.bill_type = bill_type;
		}
		public void setBilling_bandwidth(String billing_bandwidth) {
			this.billing_bandwidth = billing_bandwidth;
		}
		public String getFROMDATE() {
			return FROMDATE;
		}
		public void setFROMDATE(String fROMDATE) {
			FROMDATE = fROMDATE;
		}
		public String getTODATE() {
			return TODATE;
		}
		public void setTODATE(String tODATE) {
			TODATE = tODATE;
		}
		public String getInterfaceId() {
			return InterfaceId;
		}
		public void setInterfaceId(String interfaceId) {
			InterfaceId = interfaceId;
		}
	
		public String getLOC_Date() {
			return LOC_Date;
		}
		public void setLOC_Date(String lOC_Date) {
			LOC_Date = lOC_Date;
		}
		public ArrayList getBcpExcelLis() {
			return BcpExcelLis;
		}
		public void setBcpExcelLis(ArrayList bcpExcelLis) {
			BcpExcelLis = bcpExcelLis;
		}
		
		public ArrayList getCustomerList() {
			return CustomerList;
		}
		public void setCustomerList(ArrayList customerList) {
			CustomerList = customerList;
		}
		public String getToExcel() {
			return ToExcel;
		}
		public void setToExcel(String toExcel) {
			ToExcel = toExcel;
		}
		public PagingSorting getPagingSorting() {
			return pagingSorting;
		}
		public void setPagingSorting(PagingSorting pagingSorting) {
			this.pagingSorting = pagingSorting;
		}
		public int getsNo() {
			return sNo;
		}
		public void setsNo(int sNo) {
			this.sNo = sNo;
		}
		public String getACCOUNT_ID() {
			return ACCOUNT_ID;
		}
		public void setACCOUNT_ID(String aCCOUNT_ID) {
			ACCOUNT_ID = aCCOUNT_ID;
		}
		public String getACCOUNT_MANAGER() {
			return ACCOUNT_MANAGER;
		}
		public void setACCOUNT_MANAGER(String aCCOUNT_MANAGER) {
			ACCOUNT_MANAGER = aCCOUNT_MANAGER;
		}
		public String getACCOUNT_NUMBER() {
			return ACCOUNT_NUMBER;
		}
		public void setACCOUNT_NUMBER(String aCCOUNT_NUMBER) {
			ACCOUNT_NUMBER = aCCOUNT_NUMBER;
		}
		public String getCUSTOMER_SEGMENT() {
			return CUSTOMER_SEGMENT;
		}
		public void setCUSTOMER_SEGMENT(String cUSTOMER_SEGMENT) {
			CUSTOMER_SEGMENT = cUSTOMER_SEGMENT;
		}
		public String getVERTICAL() {
			return VERTICAL;
		}
		public void setVERTICAL(String vERTICAL) {
			VERTICAL = vERTICAL;
		}
		public String getBILLING_CHARGE_START_DATE() {
			return BILLING_CHARGE_START_DATE;
		}
		public void setBILLING_CHARGE_START_DATE(String bILLING_CHARGE_START_DATE) {
			BILLING_CHARGE_START_DATE = bILLING_CHARGE_START_DATE;
		}
		public String getSERVICE_NAME() {
			return SERVICE_NAME;
		}
		public void setSERVICE_NAME(String sERVICE_NAME) {
			SERVICE_NAME = sERVICE_NAME;
		}
		public String getORDER_LINE_NO() {
			return ORDER_LINE_NO;
		}
		public void setORDER_LINE_NO(String oRDER_LINE_NO) {
			ORDER_LINE_NO = oRDER_LINE_NO;
		}
		public String getLINE_NAME() {
			return LINE_NAME;
		}
		public void setLINE_NAME(String lINE_NAME) {
			LINE_NAME = lINE_NAME;
		}
		public String getCANCEL_FLAG() {
			return CANCEL_FLAG;
		}
		public void setCANCEL_FLAG(String cANCEL_FLAG) {
			CANCEL_FLAG = cANCEL_FLAG;
		}
		public String getUOM() {
			return UOM;
		}
		public void setUOM(String uOM) {
			UOM = uOM;
		}
		
		public String getBILL_UOM() {
			return BILL_UOM;
		}
		public void setBILL_UOM(String bILL_UOM) {
			BILL_UOM = bILL_UOM;
		}
		public String getCATEGORY_OF_ORDER() {
			return CATEGORY_OF_ORDER;
		}
		public void setCATEGORY_OF_ORDER(String cATEGORY_OF_ORDER) {
			CATEGORY_OF_ORDER = cATEGORY_OF_ORDER;
		}
		public String getCONTRACT_PERIOD() {
			return CONTRACT_PERIOD;
		}
		public void setCONTRACT_PERIOD(String cONTRACT_PERIOD) {
			CONTRACT_PERIOD = cONTRACT_PERIOD;
		}
		public String getORDER_CREATION_DATE() {
			return ORDER_CREATION_DATE;
		}
		public void setORDER_CREATION_DATE(String oRDER_CREATION_DATE) {
			ORDER_CREATION_DATE = oRDER_CREATION_DATE;
		}
		public String getCUSTOMER_RFS_DATE() {
			return CUSTOMER_RFS_DATE;
		}
		public void setCUSTOMER_RFS_DATE(String cUSTOMER_RFS_DATE) {
			CUSTOMER_RFS_DATE = cUSTOMER_RFS_DATE;
		}
		public String getCUSTOMER_SERVICE_RFS_DATE() {
			return CUSTOMER_SERVICE_RFS_DATE;
		}
		public void setCUSTOMER_SERVICE_RFS_DATE(String cUSTOMER_SERVICE_RFS_DATE) {
			CUSTOMER_SERVICE_RFS_DATE = cUSTOMER_SERVICE_RFS_DATE;
		}
		public String getCURRENCY() {
			return CURRENCY;
		}
		public void setCURRENCY(String cURRENCY) {
			CURRENCY = cURRENCY;
		}
		public String getCUSTOMER_PO_DATE() {
			return CUSTOMER_PO_DATE;
		}
		public void setCUSTOMER_PO_DATE(String cUSTOMER_PO_DATE) {
			CUSTOMER_PO_DATE = cUSTOMER_PO_DATE;
		}
		public String getCUSTOMER_PO_NUMBER() {
			return CUSTOMER_PO_NUMBER;
		}
		public void setCUSTOMER_PO_NUMBER(String cUSTOMER_PO_NUMBER) {
			CUSTOMER_PO_NUMBER = cUSTOMER_PO_NUMBER;
		}
		public String getCYCLIC_OR_NON_CYCLIC() {
			return CYCLIC_OR_NON_CYCLIC;
		}
		public void setCYCLIC_OR_NON_CYCLIC(String cYCLIC_OR_NON_CYCLIC) {
			CYCLIC_OR_NON_CYCLIC = cYCLIC_OR_NON_CYCLIC;
		}
		public String getFROM_SITE() {
			return FROM_SITE;
		}
		public void setFROM_SITE(String fROM_SITE) {
			FROM_SITE = fROM_SITE;
		}
		public String getITEM_QUANTITY() {
			return ITEM_QUANTITY;
		}
		public void setITEM_QUANTITY(String iTEM_QUANTITY) {
			ITEM_QUANTITY = iTEM_QUANTITY;
		}
		public String getLINE_ITEM_AMOUNT() {
			return LINE_ITEM_AMOUNT;
		}
		public void setLINE_ITEM_AMOUNT(String lINE_ITEM_AMOUNT) {
			LINE_ITEM_AMOUNT = lINE_ITEM_AMOUNT;
		}
		public String getCOPC_APPROVED_DATE() {
			return COPC_APPROVED_DATE;
		}
		public void setCOPC_APPROVED_DATE(String cOPC_APPROVED_DATE) {
			COPC_APPROVED_DATE = cOPC_APPROVED_DATE;
		}
		public String getLINE_ITEM_DESCRIPTION() {
			return LINE_ITEM_DESCRIPTION;
		}
		public void setLINE_ITEM_DESCRIPTION(String lINE_ITEM_DESCRIPTION) {
			LINE_ITEM_DESCRIPTION = lINE_ITEM_DESCRIPTION;
		}
		public String getLOC_DATE() {
			return LOC_DATE;
		}
		public void setLOC_DATE(String lOC_DATE) {
			LOC_DATE = lOC_DATE;
		}
		public String getACCOUNT_MANAGER_RECEIVE_DATE() {
			return ACCOUNT_MANAGER_RECEIVE_DATE;
		}
		public void setACCOUNT_MANAGER_RECEIVE_DATE(String aCCOUNT_MANAGER_RECEIVE_DATE) {
			ACCOUNT_MANAGER_RECEIVE_DATE = aCCOUNT_MANAGER_RECEIVE_DATE;
		}
		public String getORDER_ENTRY_DATE() {
			return ORDER_ENTRY_DATE;
		}
		public void setORDER_ENTRY_DATE(String oRDER_ENTRY_DATE) {
			ORDER_ENTRY_DATE = oRDER_ENTRY_DATE;
		}
		public String getTAXATION() {
			return TAXATION;
		}
		public void setTAXATION(String tAXATION) {
			TAXATION = tAXATION;
		}
		public String getLICENCE_COMPANY() {
			return LICENCE_COMPANY;
		}
		public void setLICENCE_COMPANY(String lICENCE_COMPANY) {
			LICENCE_COMPANY = lICENCE_COMPANY;
		}
		public String getLOGICAL_CIRCUIT_ID() {
			return LOGICAL_CIRCUIT_ID;
		}
		public void setLOGICAL_CIRCUIT_ID(String lOGICAL_CIRCUIT_ID) {
			LOGICAL_CIRCUIT_ID = lOGICAL_CIRCUIT_ID;
		}
		public String getORDER_TYPE() {
			return ORDER_TYPE;
		}
		public void setORDER_TYPE(String oRDER_TYPE) {
			ORDER_TYPE = oRDER_TYPE;
		}
		public String getPAYMENT_TERM() {
			return PAYMENT_TERM;
		}
		public void setPAYMENT_TERM(String pAYMENT_TERM) {
			PAYMENT_TERM = pAYMENT_TERM;
		}
		public String getPROJECT_MGR() {
			return PROJECT_MGR;
		}
		public void setPROJECT_MGR(String pROJECT_MGR) {
			PROJECT_MGR = pROJECT_MGR;
		}
		public String getREGION_NAME() {
			return REGION_NAME;
		}
		public void setREGION_NAME(String rEGION_NAME) {
			REGION_NAME = rEGION_NAME;
		}
		public String getOLD_LINE_ITEM_AMOUNT() {
			return OLD_LINE_ITEM_AMOUNT;
		}
		public void setOLD_LINE_ITEM_AMOUNT(String oLD_LINE_ITEM_AMOUNT) {
			OLD_LINE_ITEM_AMOUNT = oLD_LINE_ITEM_AMOUNT;
		}
		public String getDEMO_TYPE() {
			return DEMO_TYPE;
		}
		public void setDEMO_TYPE(String dEMO_TYPE) {
			DEMO_TYPE = dEMO_TYPE;
		}
		public String getPARTY_NAME() {
			return PARTY_NAME;
		}
		public void setPARTY_NAME(String pARTY_NAME) {
			PARTY_NAME = pARTY_NAME;
		}
		public String getORDER_STAGE_DESCRIPTION() {
			return ORDER_STAGE_DESCRIPTION;
		}
		public void setORDER_STAGE_DESCRIPTION(String oRDER_STAGE_DESCRIPTION) {
			ORDER_STAGE_DESCRIPTION = oRDER_STAGE_DESCRIPTION;
		}
		public String getSERVICE_STAGE_DESCRIPTION() {
			return SERVICE_STAGE_DESCRIPTION;
		}
		public void setSERVICE_STAGE_DESCRIPTION(String sERVICE_STAGE_DESCRIPTION) {
			SERVICE_STAGE_DESCRIPTION = sERVICE_STAGE_DESCRIPTION;
		}
		public String getCHARGE_END_DATE() {
			return CHARGE_END_DATE;
		}
		public void setCHARGE_END_DATE(String cHARGE_END_DATE) {
			CHARGE_END_DATE = cHARGE_END_DATE;
		}
		public String getNEW_ORDER_REMARK() {
			return NEW_ORDER_REMARK;
		}
		public void setNEW_ORDER_REMARK(String nEW_ORDER_REMARK) {
			NEW_ORDER_REMARK = nEW_ORDER_REMARK;
		}
		public String getREMARKS() {
			return REMARKS;
		}
		public void setREMARKS(String rEMARKS) {
			REMARKS = rEMARKS;
		}
		public String getSERVICE_ORDER_TYPE() {
			return SERVICE_ORDER_TYPE;
		}
		public void setSERVICE_ORDER_TYPE(String sERVICE_ORDER_TYPE) {
			SERVICE_ORDER_TYPE = sERVICE_ORDER_TYPE;
		}
		public String getOSP() {
			return OSP;
		}
		public void setOSP(String oSP) {
			OSP = oSP;
		}
		public String getOPPORTUNITY_ID() {
			return OPPORTUNITY_ID;
		}
		public void setOPPORTUNITY_ID(String oPPORTUNITY_ID) {
			OPPORTUNITY_ID = oPPORTUNITY_ID;
		}
		public String getACCOUNT_CATEGORY() {
			return ACCOUNT_CATEGORY;
		}
		public void setACCOUNT_CATEGORY(String aCCOUNT_CATEGORY) {
			ACCOUNT_CATEGORY = aCCOUNT_CATEGORY;
		}
		public String getPROVISION_BANDWIDTH() {
			return PROVISION_BANDWIDTH;
		}
		public void setPROVISION_BANDWIDTH(String pROVISION_BANDWIDTH) {
			PROVISION_BANDWIDTH = pROVISION_BANDWIDTH;
		}
		public String getCOMPANY_NAME() {
			return COMPANY_NAME;
		}
		public void setCOMPANY_NAME(String cOMPANY_NAME) {
			COMPANY_NAME = cOMPANY_NAME;
		}
		public String getCHARGE_NAME() {
			return CHARGE_NAME;
		}
		public void setCHARGE_NAME(String cHARGE_NAME) {
			CHARGE_NAME = cHARGE_NAME;
		}
		public String getCHALLEN_NO() {
			return CHALLEN_NO;
		}
		public void setCHALLEN_NO(String cHALLEN_NO) {
			CHALLEN_NO = cHALLEN_NO;
		}
		public String getORDER_NO() {
			return ORDER_NO;
		}
		public void setORDER_NO(String oRDER_NO) {
			ORDER_NO = oRDER_NO;
		}
		public String getTO_SITE() {
			return TO_SITE;
		}
		public void setTO_SITE(String tO_SITE) {
			TO_SITE = tO_SITE;
		}
		public String getKMS_DISTANCE() {
			return KMS_DISTANCE;
		}
		public void setKMS_DISTANCE(String kMS_DISTANCE) {
			KMS_DISTANCE = kMS_DISTANCE;
		}
		public String getEND_DATE_LOGIC() {
			return END_DATE_LOGIC;
		}
		public void setEND_DATE_LOGIC(String eND_DATE_LOGIC) {
			END_DATE_LOGIC = eND_DATE_LOGIC;
		}
		public String getSTORE_ADDRESS() {
			return STORE_ADDRESS;
		}
		public void setSTORE_ADDRESS(String sTORE_ADDRESS) {
			STORE_ADDRESS = sTORE_ADDRESS;
		}
		public String getM6ORDERNO() {
			return M6ORDERNO;
		}
		public void setM6ORDERNO(String m6orderno) {
			M6ORDERNO = m6orderno;
		}
		public String getLOGICALSINO() {
			return LOGICALSINO;
		}
		public void setLOGICALSINO(String lOGICALSINO) {
			LOGICALSINO = lOGICALSINO;
		}
		public String getLINE_IT_NO() {
			return LINE_IT_NO;
		}
		public void setLINE_IT_NO(String lINE_IT_NO) {
			LINE_IT_NO = lINE_IT_NO;
		}
		public String getLOGICAL_SI_NO() {
			return LOGICAL_SI_NO;
		}
		public void setLOGICAL_SI_NO(String lOGICAL_SI_NO) {
			LOGICAL_SI_NO = lOGICAL_SI_NO;
		}	
		//For PD Archival Order
		public String getAccount_mgr() {
			return account_mgr;
		}
		public void setAccount_mgr(String account_mgr) {
			this.account_mgr = account_mgr;
		}
		public String getAccount_no() {
			return account_no;
		}
		public void setAccount_no(String account_no) {
			this.account_no = account_no;
		}
		public String getAmt() {
			return amt;
		}
		public void setAmt(String amt) {
			this.amt = amt;
		}
		public String getAnnotation() {
			return annotation;
		}
		public void setAnnotation(String annotation) {
			this.annotation = annotation;
		}
		public String getAnnual() {
			return annual;
		}
		public void setAnnual(String annual) {
			this.annual = annual;
		}
		public String getRate() {
			return rate;
		}
		public void setRate(String rate) {
			this.rate = rate;
		}
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
		public String getBill_format() {
			return bill_format;
		}
		public void setBill_format(String bill_format) {
			this.bill_format = bill_format;
		}
		public String getBill_period() {
			return bill_period;
		}
		public void setBill_period(String bill_period) {
			this.bill_period = bill_period;
		}
		public String getBill_trg_Create_date() {
			return bill_trg_Create_date;
		}
		public void setBill_trg_Create_date(String bill_trg_Create_date) {
			this.bill_trg_Create_date = bill_trg_Create_date;
		}
		
		public String getBilling_bandwidth_uom() {
			return billing_bandwidth_uom;
		}
		public void setBilling_bandwidth_uom(String billing_bandwidth_uom) {
			this.billing_bandwidth_uom = billing_bandwidth_uom;
		}
		public String getBilling_level() {
			return billing_level;
		}
		public void setBilling_level(String billing_level) {
			this.billing_level = billing_level;
		}
		public String getBilling_level_number() {
			return billing_level_number;
		}
		public void setBilling_level_number(String billing_level_number) {
			this.billing_level_number = billing_level_number;
		}
		public String getBilling_location_from() {
			return billing_location_from;
		}
		public void setBilling_location_from(String billing_location_from) {
			this.billing_location_from = billing_location_from;
		}
		public String getBilling_location_to() {
			return billing_location_to;
		}
		public void setBilling_location_to(String billing_location_to) {
			this.billing_location_to = billing_location_to;
		}
		public String getBilling_mode() {
			return billing_mode;
		}
		public void setBilling_mode(String billing_mode) {
			this.billing_mode = billing_mode;
		}
		public String getBilling_trig_flag() {
			return billing_trig_flag;
		}
		public void setBilling_trig_flag(String billing_trig_flag) {
			this.billing_trig_flag = billing_trig_flag;
		}
		public String getChallen_date() {
			return challen_date;
		}
		public void setChallen_date(String challen_date) {
			this.challen_date = challen_date;
		}
		public String getCharge_end_date() {
			return charge_end_date;
		}
		public void setCharge_end_date(String charge_end_date) {
			this.charge_end_date = charge_end_date;
		}
		public String getCharge_hdr_id() {
			return charge_hdr_id;
		}
		public void setCharge_hdr_id(String charge_hdr_id) {
			this.charge_hdr_id = charge_hdr_id;
		}
		public String getCharge_name() {
			return charge_name;
		}
		public void setCharge_name(String charge_name) {
			this.charge_name = charge_name;
		}
		public String getCharge_start_date() {
			return charge_start_date;
		}
		public void setCharge_start_date(String charge_start_date) {
			this.charge_start_date = charge_start_date;
		}
		public String getCharge_status() {
			return charge_status;
		}
		public void setCharge_status(String charge_status) {
			this.charge_status = charge_status;
		}
		public String getCharge_type() {
			return charge_type;
		}
		public void setCharge_type(String charge_type) {
			this.charge_type = charge_type;
		}
		public String getCharge_type_id() {
			return charge_type_id;
		}
		public void setCharge_type_id(String charge_type_id) {
			this.charge_type_id = charge_type_id;
		}
		public String getChargeable_distance() {
			return chargeable_distance;
		}
		public void setChargeable_distance(String chargeable_distance) {
			this.chargeable_distance = chargeable_distance;
		}
		public String getChild_acc_fx_status() {
			return child_acc_fx_status;
		}
		public void setChild_acc_fx_status(String child_acc_fx_status) {
			this.child_acc_fx_status = child_acc_fx_status;
		}
		public String getChild_acc_no() {
			return child_acc_no;
		}
		public void setChild_acc_no(String child_acc_no) {
			this.child_acc_no = child_acc_no;
		}
		public String getCircuit_id() {
			return circuit_id;
		}
		public void setCircuit_id(String circuit_id) {
			this.circuit_id = circuit_id;
		}
		public String getCommitment_period() {
			return commitment_period;
		}
		public void setCommitment_period(String commitment_period) {
			this.commitment_period = commitment_period;
		}
		public String getCopc_approval_date() {
			return copc_approval_date;
		}
		public void setCopc_approval_date(String copc_approval_date) {
			this.copc_approval_date = copc_approval_date;
		}
		public String getCredit_period() {
			return credit_period;
		}
		public void setCredit_period(String credit_period) {
			this.credit_period = credit_period;
		}
		public String getCurrency() {
			return currency;
		}
		public void setCurrency(String currency) {
			this.currency = currency;
		}
		public String getCust_acc_id() {
			return cust_acc_id;
		}
		public void setCust_acc_id(String cust_acc_id) {
			this.cust_acc_id = cust_acc_id;
		}
		public String getCust_logical_si_no() {
			return cust_logical_si_no;
		}
		public void setCust_logical_si_no(String cust_logical_si_no) {
			this.cust_logical_si_no = cust_logical_si_no;
		}
		public String getCust_po_date() {
			return cust_po_date;
		}
		public void setCust_po_date(String cust_po_date) {
			this.cust_po_date = cust_po_date;
		}
		public String getCust_po_number() {
			return cust_po_number;
		}
		public void setCust_po_number(String cust_po_number) {
			this.cust_po_number = cust_po_number;
		}
		public String getCust_po_receive_date() {
			return cust_po_receive_date;
		}
		public void setCust_po_receive_date(String cust_po_receive_date) {
			this.cust_po_receive_date = cust_po_receive_date;
		}
		public String getCustomer_segment() {
			return customer_segment;
		}
		public void setCustomer_segment(String customer_segment) {
			this.customer_segment = customer_segment;
		}
		public String getCustomer_service_rfs_date() {
			return customer_service_rfs_date;
		}
		public void setCustomer_service_rfs_date(String customer_service_rfs_date) {
			this.customer_service_rfs_date = customer_service_rfs_date;
		}
		public String getDemo_type() {
			return demo_type;
		}
		public void setDemo_type(String demo_type) {
			this.demo_type = demo_type;
		}
		public String getDisconnection_remark() {
			return disconnection_remark;
		}
		public void setDisconnection_remark(String disconnection_remark) {
			this.disconnection_remark = disconnection_remark;
		}
		public String getEnd_date_days() {
			return end_date_days;
		}
		public void setEnd_date_days(String end_date_days) {
			this.end_date_days = end_date_days;
		}
		public String getEnd_date_logic() {
			return end_date_logic;
		}
		public void setEnd_date_logic(String end_date_logic) {
			this.end_date_logic = end_date_logic;
		}
		public String getEnd_date_months() {
			return end_date_months;
		}
		public void setEnd_date_months(String end_date_months) {
			this.end_date_months = end_date_months;
		}
		public String getForm_c_available() {
			return form_c_available;
		}
		public void setForm_c_available(String form_c_available) {
			this.form_c_available = form_c_available;
		}
		public String getFrequency() {
			return frequency;
		}
		public void setFrequency(String frequency) {
			this.frequency = frequency;
		}
		public String getHardware_flag() {
			return hardware_flag;
		}
		public void setHardware_flag(String hardware_flag) {
			this.hardware_flag = hardware_flag;
		}
		public String getIndicative_value() {
			return indicative_value;
		}
		public void setIndicative_value(String indicative_value) {
			this.indicative_value = indicative_value;
		}
		public String getInv_amt() {
			return inv_amt;
		}
		public void setInv_amt(String inv_amt) {
			this.inv_amt = inv_amt;
		}
		public String getLast_mile_media() {
			return last_mile_media;
		}
		public void setLast_mile_media(String last_mile_media) {
			this.last_mile_media = last_mile_media;
		}
		public String getLast_mile_provider() {
			return last_mile_provider;
		}
		public void setLast_mile_provider(String last_mile_provider) {
			this.last_mile_provider = last_mile_provider;
		}
		public String getLast_mile_remarks() {
			return last_mile_remarks;
		}
		public void setLast_mile_remarks(String last_mile_remarks) {
			this.last_mile_remarks = last_mile_remarks;
		}
		public String getLegal_entity() {
			return legal_entity;
		}
		public void setLegal_entity(String legal_entity) {
			this.legal_entity = legal_entity;
		}
		public String getLicence_company() {
			return licence_company;
		}
		public void setLicence_company(String licence_company) {
			this.licence_company = licence_company;
		}
		public String getLoc_date() {
			return loc_date;
		}
		public void setLoc_date(String loc_date) {
			this.loc_date = loc_date;
		}
		public String getLoc_number() {
			return loc_number;
		}
		public void setLoc_number(String loc_number) {
			this.loc_number = loc_number;
		}
		public String getLogical_circuit_id() {
			return logical_circuit_id;
		}
		public void setLogical_circuit_id(String logical_circuit_id) {
			this.logical_circuit_id = logical_circuit_id;
		}
		public String getM6_order_id() {
			return m6_order_id;
		}
		public void setM6_order_id(String m6_order_id) {
			this.m6_order_id = m6_order_id;
		}
		public String getNature_of_sale() {
			return nature_of_sale;
		}
		public void setNature_of_sale(String nature_of_sale) {
			this.nature_of_sale = nature_of_sale;
		}
		public String getNew_order_remarks() {
			return new_order_remarks;
		}
		public void setNew_order_remarks(String new_order_remarks) {
			this.new_order_remarks = new_order_remarks;
		}
		public String getOrder_creation_date() {
			return order_creation_date;
		}
		public void setOrder_creation_date(String order_creation_date) {
			this.order_creation_date = order_creation_date;
		}
		public String getOrder_date() {
			return order_date;
		}
		public void setOrder_date(String order_date) {
			this.order_date = order_date;
		}
		public String getOrder_line_id() {
			return order_line_id;
		}
		public void setOrder_line_id(String order_line_id) {
			this.order_line_id = order_line_id;
		}
		public String getOrder_month() {
			return order_month;
		}
		public void setOrder_month(String order_month) {
			this.order_month = order_month;
		}
		public String getOrder_number() {
			return order_number;
		}
		public void setOrder_number(String order_number) {
			this.order_number = order_number;
		}
		public String getOrder_stage() {
			return order_stage;
		}
		public void setOrder_stage(String order_stage) {
			this.order_stage = order_stage;
		}
		public String getOrder_type() {
			return order_type;
		}
		public void setOrder_type(String order_type) {
			this.order_type = order_type;
		}
		public String getParty() {
			return party;
		}
		public void setParty(String party) {
			this.party = party;
		}
		public String getParty_id() {
			return party_id;
		}
		public void setParty_id(String party_id) {
			this.party_id = party_id;
		}
		public String getPenalty_clause() {
			return penalty_clause;
		}
		public void setPenalty_clause(String penalty_clause) {
			this.penalty_clause = penalty_clause;
		}
		public String getPeriod_in_month() {
			return period_in_month;
		}
		public void setPeriod_in_month(String period_in_month) {
			this.period_in_month = period_in_month;
		}
		public String getPk_chageges_id() {
			return pk_chageges_id;
		}
		public void setPk_chageges_id(String pk_chageges_id) {
			this.pk_chageges_id = pk_chageges_id;
		}
		public String getPm_prov_date() {
			return pm_prov_date;
		}
		public void setPm_prov_date(String pm_prov_date) {
			this.pm_prov_date = pm_prov_date;
		}
		public String getPo_date() {
			return po_date;
		}
		public void setPo_date(String po_date) {
			this.po_date = po_date;
		}
		public String getPre_crm_order_id() {
			return pre_crm_order_id;
		}
		public void setPre_crm_order_id(String pre_crm_order_id) {
			this.pre_crm_order_id = pre_crm_order_id;
		}
		public String getProduct() {
			return product;
		}
		public void setProduct(String product) {
			this.product = product;
		}
		public String getProduct_name() {
			return product_name;
		}
		public void setProduct_name(String product_name) {
			this.product_name = product_name;
		}
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public String getRequest_received_date() {
			return request_received_date;
		}
		public void setRequest_received_date(String request_received_date) {
			this.request_received_date = request_received_date;
		}
		public String getSec_loc() {
			return sec_loc;
		}
		public void setSec_loc(String sec_loc) {
			this.sec_loc = sec_loc;
		}
		public String getService_order_type() {
			return service_order_type;
		}
		public void setService_order_type(String service_order_type) {
			this.service_order_type = service_order_type;
		}
		public String getService_stage() {
			return service_stage;
		}
		public void setService_stage(String service_stage) {
			this.service_stage = service_stage;
		}
		public String getSr_number() {
			return sr_number;
		}
		public void setSr_number(String sr_number) {
			this.sr_number = sr_number;
		}
		public String getStandard_reason() {
			return standard_reason;
		}
		public void setStandard_reason(String standard_reason) {
			this.standard_reason = standard_reason;
		}
		public String getStart_date_days() {
			return start_date_days;
		}
		public void setStart_date_days(String start_date_days) {
			this.start_date_days = start_date_days;
		}
		public String getStart_date_logic() {
			return start_date_logic;
		}
		public void setStart_date_logic(String start_date_logic) {
			this.start_date_logic = start_date_logic;
		}
		public String getStart_date_months() {
			return start_date_months;
		}
		public void setStart_date_months(String start_date_months) {
			this.start_date_months = start_date_months;
		}
		public String getStore() {
			return store;
		}
		public void setStore(String store) {
			this.store = store;
		}
		public String getSub_product() {
			return sub_product;
		}
		public void setSub_product(String sub_product) {
			this.sub_product = sub_product;
		}
		public String getTaxation() {
			return taxation;
		}
		public void setTaxation(String taxation) {
			this.taxation = taxation;
		}
		public String getToken_no() {
			return token_no;
		}
		public void setToken_no(String token_no) {
			this.token_no = token_no;
		}
		public String getTot_po_amt() {
			return tot_po_amt;
		}
		public void setTot_po_amt(String tot_po_amt) {
			this.tot_po_amt = tot_po_amt;
		}
		public String getTotal_amount() {
			return total_amount;
		}
		public void setTotal_amount(String total_amount) {
			this.total_amount = total_amount;
		}
		public String getType_of_sale() {
			return type_of_sale;
		}
		public void setType_of_sale(String type_of_sale) {
			this.type_of_sale = type_of_sale;
		}
		public String getVertical() {
			return vertical;
		}
		public void setVertical(String vertical) {
			this.vertical = vertical;
		}
		public String getProject_mgr() {
			return project_mgr;
		}
		public void setProject_mgr(String project_mgr) {
			this.project_mgr = project_mgr;
		}
		public String getProject_mgr_email() {
			return project_mgr_email;
		}
		public void setProject_mgr_email(String project_mgr_email) {
			this.project_mgr_email = project_mgr_email;
		}
		public String getProvision_bandwidth() {
			return provision_bandwidth;
		}
		public void setProvision_bandwidth(String provision_bandwidth) {
			this.provision_bandwidth = provision_bandwidth;
		}
		public String getQuote_no() {
			return quote_no;
		}
		public void setQuote_no(String quote_no) {
			this.quote_no = quote_no;
		}
		public String getRatio() {
			return ratio;
		}
		public void setRatio(String ratio) {
			this.ratio = ratio;
		}
		public String getRegion_name() {
			return region_name;
		}
		public void setRegion_name(String region_name) {
			this.region_name = region_name;
		}
		public String getRe_logged_lsi_no() {
			return re_logged_lsi_no;
		}
		public void setRe_logged_lsi_no(String re_logged_lsi_no) {
			this.re_logged_lsi_no = re_logged_lsi_no;
		}
		public String getService_name() {
			return service_name;
		}
		public void setService_name(String service_name) {
			this.service_name = service_name;
		}
		public String getService_number() {
			return service_number;
		}
		public void setService_number(String service_number) {
			this.service_number = service_number;
		}
		public String getSub_product_type() {
			return sub_product_type;
		}
		public void setSub_product_type(String sub_product_type) {
			this.sub_product_type = sub_product_type;
		}
		public String getTaxexcemption_reason() {
			return taxexcemption_reason;
		}
		public void setTaxexcemption_reason(String taxexcemption_reason) {
			this.taxexcemption_reason = taxexcemption_reason;
		}
		public String getTo_city() {
			return to_city;
		}
		public void setTo_city(String to_city) {
			this.to_city = to_city;
		}
		public String getTo_site() {
			return to_site;
		}
		public void setTo_site(String to_site) {
			this.to_site = to_site;
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
		public String getDis_sr() {
			return dis_sr;
		}
		public void setDis_sr(String dis_sr) {
			this.dis_sr = dis_sr;
		}
		public String getDod() {
			return dod;
		}
		public void setDod(String dod) {
			this.dod = dod;
		}
		public String getAnnual_rate() {
			return annual_rate;
		}
		public void setAnnual_rate(String annual_rate) {
			this.annual_rate = annual_rate;
		}
		
	
		

}
