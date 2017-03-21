<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<html:html>
<head>
<%
response.setContentType("application/vnd.ms-excel");
%>
</head>
var isUsage ="<%= request.getAttribute("isUsage") %>";
<body>
<logic:present name="pdReportOrderExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=ArchivalPDReport.xls"); %>
<table width="100%" border="1" cellpadding="0" cellspacing="0">
<tr><td colspan="25" align="left" style="font-size:10px"><strong>Archival Permanent Disconnection Report</strong></td>
</tr>
<tr align="Center">


<td align="center" class='inner col3 head1' ><b>Order No</b></td>
<td align="center" class='inner col3 head1' ><b>Account Mgr</b></td>
<td align="center" class='inner col3 head1' ><b>Account No</b></td>
<td align="center" class='inner col3 head1' ><b>Amount</b></td>
<td align="center" class='inner col3 head1' ><b>Annotation</b></td>
<td align="center" class='inner col3 head1' ><b>Annual Rate</b></td>
<td align="center" class='inner col3 head1' ><b>Bandwidth</b></td>
<td align="center" class='inner col3 head1' ><b>Bandwidth uom</b></td>
<td align="center" class='inner col3 head1' ><b>Bill Format</b></td>
<td align="center" class='inner col3 head1' ><b>Bill Period</b></td>
<td align="center" class='inner col3 head1' ><b>Bill TRG Create Date</b></td>
<td align="center" class='inner col3 head1' ><b>Bill Type</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Bandwidth</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Bandwidth uom</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Level</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Level number</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Location from</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Location to</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Mode</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Trig flag</b></td>
<td align="center" class='inner col3 head1' ><b>Challen Date</b></td>
<td align="center" class='inner col3 head1' ><b>Charge End Date</b></td>
<td align="center" class='inner col3 head1' ><b>Charge HDR ID</b></td>
<td align="center" class='inner col3 head1' ><b>Charge Name</b></td>
<td align="center" class='inner col3 head1' ><b>Charge Start Date</b></td>
<td align="center" class='inner col3 head1' ><b>Charge Status</b></td>
<td align="center" class='inner col3 head1' ><b>Charge Type</b></td>
<td align="center" class='inner col3 head1' ><b>Charge Type ID</b></td>
<td align="center" class='inner col3 head1' ><b>Chargeable Distance</b></td>
<td align="center" class='inner col3 head1' ><b>Child Acc FX Status</b></td>
<td align="center" class='inner col3 head1' ><b>Child Acc No</b></td>
<td align="center" class='inner col3 head1' ><b>Circuit ID</b></td>
<td align="center" class='inner col3 head1' ><b>Commitment Period</b></td>
<td align="center" class='inner col3 head1' ><b>Contract Period Months</b></td>
<td align="center" class='inner col3 head1' ><b>COPC Approval Date</b></td>
<td align="center" class='inner col3 head1' ><b>Credit Period</b></td>
<td align="center" class='inner col3 head1' ><b>Currency</b></td>
<td align="center" class='inner col3 head1' ><b>Cust Acc ID</b></td>
<td align="center" class='inner col3 head1' ><b>Cust Logical SI No</b></td>
<td align="center" class='inner col3 head1' ><b>Cust PO Date</b></td>
<td align="center" class='inner col3 head1' ><b>Cust PO Number</b></td>
<td align="center" class='inner col3 head1' ><b>Cust PO Receive Date</b></td>
<td align="center" class='inner col3 head1' ><b>Customer Segment</b></td>
<td align="center" class='inner col3 head1' ><b>customer Service RFS Date</b></td>
<td align="center" class='inner col3 head1' ><b>Demo Type</b></td>
<td align="center" class='inner col3 head1' ><b>Disconnection Remark</b></td>
<td align="center" class='inner col3 head1' ><b>End Date Days</b></td>
<td align="center" class='inner col3 head1' ><b>End Date Logic</b></td>
<td align="center" class='inner col3 head1' ><b>End Date Months</b></td>
<td align="center" class='inner col3 head1' ><b>Form C Available</b></td>
<td align="center" class='inner col3 head1' ><b>Frequency</b></td>
<td align="center" class='inner col3 head1' ><b>Hardware Flag</b></td>
<td align="center" class='inner col3 head1' ><b>Hardware Type</b></td>
<td align="center" class='inner col3 head1' ><b>Indicative Value</b></td>
<td align="center" class='inner col3 head1' ><b>Invoice Amt</b></td>
<td align="center" class='inner col3 head1' ><b>Last Mile Media</b></td>
<td align="center" class='inner col3 head1' ><b>Last Mile Provider</b></td>
<td align="center" class='inner col3 head1' ><b>Last Mile Remarks</b></td>
<td align="center" class='inner col3 head1' ><b>Legal Entity</b></td>
<td align="center" class='inner col3 head1' ><b>Licence Company</b></td>
<td align="center" class='inner col3 head1' ><b>Link Type</b></td>
<td align="center" class='inner col3 head1' ><b>Loc Date</b></td>
<td align="center" class='inner col3 head1' ><b>Loc Number</b></td>
<td align="center" class='inner col3 head1' ><b>Logical Circuit ID</b></td>
<td align="center" class='inner col3 head1' ><b>M6 Order ID</b></td>
<td align="center" class='inner col3 head1' ><b>Nature of Sale</b></td>
<td align="center" class='inner col3 head1' ><b>New Order Remarks</b></td>
<td align="center" class='inner col3 head1' ><b>Notice Period</b></td>
<td align="center" class='inner col3 head1' ><b>Order Creation Date</b></td>
<td align="center" class='inner col3 head1' ><b>Order Date</b></td>
<td align="center" class='inner col3 head1' ><b>Order Line ID</b></td>
<td align="center" class='inner col3 head1' ><b>Order Month</b></td>
<td align="center" class='inner col3 head1' ><b>Order Stage</b></td>
<td align="center" class='inner col3 head1' ><b>Order Type</b></td>
<td align="center" class='inner col3 head1' ><b>Party</b></td>
<td align="center" class='inner col3 head1' ><b>party ID</b></td>
<td align="center" class='inner col3 head1' ><b>Penalty Clause</b></td>
<td align="center" class='inner col3 head1' ><b>Period in Month</b></td>
<td align="center" class='inner col3 head1' ><b>PK Chageges ID</b></td>
<td align="center" class='inner col3 head1' ><b>PM Prov Date</b></td>
<td align="center" class='inner col3 head1' ><b>PO Date</b></td>
<td align="center" class='inner col3 head1' ><b>Pre CRM Order ID</b></td>
<td align="center" class='inner col3 head1' ><b>Product</b></td>
<td align="center" class='inner col3 head1' ><b>Product Name</b></td>
<td align="center" class='inner col3 head1' ><b>Region</b></td>
<td align="center" class='inner col3 head1' ><b>Request Received Date</b></td>
<td align="center" class='inner col3 head1' ><b>Sec Loc</b></td>
<td align="center" class='inner col3 head1' ><b>Service No</b></td>
<td align="center" class='inner col3 head1' ><b>Service Order Type</b></td>
<td align="center" class='inner col3 head1' ><b>Service Stage</b></td>
<td align="center" class='inner col3 head1' ><b>SR Number</b></td>
<td align="center" class='inner col3 head1' ><b>Standard Reason</b></td>
<td align="center" class='inner col3 head1' ><b>Start Date Days</b></td>
<td align="center" class='inner col3 head1' ><b>Start Date Logic</b></td>
<td align="center" class='inner col3 head1' ><b>Start Date Months</b></td>
<td align="center" class='inner col3 head1' ><b>Store</b></td>
<td align="center" class='inner col3 head1' ><b>Sub Product</b></td>
<td align="center" class='inner col3 head1' ><b>Taxation</b></td>
<td align="center" class='inner col3 head1' ><b>Token No</b></td>
<td align="center" class='inner col3 head1' ><b>TOT PO Amt</b></td>
<td align="center" class='inner col3 head1' ><b>Total Amount</b></td>
<td align="center" class='inner col3 head1' ><b>Type of Sale</b></td>
<td align="center" class='inner col3 head1' ><b>Vertical</b></td>
<td align="center" class='inner col3 head1' ><b>Project Mgr</b></td>
<td align="center" class='inner col3 head1' ><b>Project Mgr Email</b></td>
<td align="center" class='inner col3 head1' ><b>Provision Bandwidth</b></td>
<td align="center" class='inner col3 head1' ><b>Quote No</b></td>
<td align="center" class='inner col3 head1' ><b>Ratio</b></td>
<td align="center" class='inner col3 head1' ><b>Region Name</b></td>
<td align="center" class='inner col3 head1' ><b>Re Logged LSI No</b></td>
<td align="center" class='inner col3 head1' ><b>Service Name</b></td>
<td align="center" class='inner col3 head1' ><b>Service Number</b></td>
<td align="center" class='inner col3 head1' ><b>Sub Change Type</b></td>
<td align="center" class='inner col3 head1' ><b>Sub Product Type</b></td>
<td align="center" class='inner col3 head1' ><b>Taxexemption Reason</b></td>
<td align="center" class='inner col3 head1' ><b>To City</b></td>
<td align="center" class='inner col3 head1' ><b>To Site</b></td>
<td align="center" class='inner col3 head1' ><b>UOM</b></td>
<td align="center" class='inner col3 head1' ><b>Zone</b></td>
<td align="center" class='inner col3 head1' ><b>Dis Sr</b></td>
<td align="center" class='inner col3 head1' ><b>Date of Disconnection</b></td>
<td align="center" class='inner col3 head1' ><b>Logical SI No</b></td>
<td align="center" class='inner col3 head1' ><b>Line IT No</b></td>
<td align="center" class='inner col3 head1' ><b>Ckt ID</b></td>
<td align="center" class='inner col3 head1' ><b>Package ID</b></td>
<td align="center" class='inner col3 head1' ><b>Package Name</b></td>
<td align="center" class='inner col3 head1' ><b>Component Info ID</b></td>
<td align="center" class='inner col3 head1' ><b>Component ID</b></td>
<td align="center" class='inner col3 head1' ><b>Component Name</b></td>
<td align="center" class='inner col3 head1' ><b>Component Status</b></td>
<td align="center" class='inner col3 head1' ><b>Component Start Logic</b></td>
<td align="center" class='inner col3 head1' ><b>Component Start Date</b></td>
<td align="center" class='inner col3 head1' ><b>Component End Logic</b></td>
<td align="center" class='inner col3 head1' ><b>Component End Date</b></td>
<td align="center" class='inner col3 head1' ><b>Comp Start Days</b></td>
<td align="center" class='inner col3 head1' ><b>Comp Start Months</b></td>
<td align="center" class='inner col3 head1' ><b>Comp End Days</b></td>
<td align="center" class='inner col3 head1' ><b>Comp End Months</b></td>
<td align="center" class='inner col3 head1' ><b>Component Type</b></td>
<td align="center" class='inner col3 head1' ><b>Component Instance ID</b></td>
<td align="center" class='inner col3 head1' ><b>Start Component Token No</b></td>
<td align="center" class='inner col3 head1' ><b>End Component Token No</b></td>
<td align="center" class='inner col3 head1' ><b>Rate Code</b></td>
<td align="center" class='inner col3 head1' ><b>Pri Loc</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Trig Date</b></td>
<td align="center" class='inner col3 head1' ><b>Challen No</b></td>
<td align="center" class='inner col3 head1' ><b>Dispatch Address</b></td>
<td align="center" class='inner col3 head1' ><b>Line Name</b></td>
<td align="center" class='inner col3 head1' ><b>PO Number</b></td>
						







</tr>
<logic:empty name="pdReportOrderExcel">
<tr><td colspan="25" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td></tr>
</logic:empty>
<logic:notEmpty  name="pdReportOrderExcel" scope="request">
<logic:iterate id="row" name="pdReportOrderExcel" indexId="recordId">
<tr>
<td><bean:write  name="row" property="order_no"/></td>
<td><bean:write  name="row" property="account_mgr"/></td>
<td><bean:write  name="row" property="account_no"/></td>
<td><bean:write  name="row" property="amt"/></td>
<td><bean:write  name="row" property="annotation"/></td>
<td><bean:write  name="row" property="annual_rate"/></td>
<td><bean:write  name="row" property="bandwidth"/></td>
<td><bean:write  name="row" property="bandwidth_uom"/></td>
<td><bean:write  name="row" property="bill_format"/></td>
<td><bean:write  name="row" property="bill_period"/></td>
<td><bean:write  name="row" property="bill_trg_Create_date"/></td>
<td><bean:write  name="row" property="bill_type"/></td>
<td><bean:write  name="row" property="billing_bandwidth"/></td>
<td><bean:write  name="row" property="billing_bandwidth_uom"/></td>
<td><bean:write  name="row" property="billing_level"/></td>
<td><bean:write  name="row" property="billing_level_number"/></td>
<td><bean:write  name="row" property="billing_location_from"/></td>
<td><bean:write  name="row" property="billing_location_to"/></td>
<td><bean:write  name="row" property="billing_mode"/></td>
<td><bean:write  name="row" property="billing_trig_flag"/></td>
<td><bean:write  name="row" property="challen_date"/></td>
<td><bean:write  name="row" property="charge_end_date"/></td>
<td><bean:write  name="row" property="charge_hdr_id"/></td>
<td><bean:write  name="row" property="charge_name"/></td>
<td><bean:write  name="row" property="charge_start_date"/></td>
<td><bean:write  name="row" property="charge_status"/></td>
<td><bean:write  name="row" property="charge_type"/></td>
<td><bean:write  name="row" property="charge_type_id"/></td>
<td><bean:write  name="row" property="chargeable_distance"/></td>
<td><bean:write  name="row" property="child_acc_fx_status"/></td>
<td><bean:write  name="row" property="child_acc_no"/></td>
<td><bean:write  name="row" property="circuit_id"/></td>
<td><bean:write  name="row" property="commitment_period"/></td>
<td><bean:write  name="row" property="contract_period_months"/></td>
<td><bean:write  name="row" property="copc_approval_date"/></td>
<td><bean:write  name="row" property="credit_period"/></td>
<td><bean:write  name="row" property="currency"/></td>
<td><bean:write  name="row" property="cust_acc_id"/></td>
<td><bean:write  name="row" property="cust_logical_si_no"/></td>
<td><bean:write  name="row" property="cust_po_date"/></td>
<td><bean:write  name="row" property="cust_po_number"/></td>
<td><bean:write  name="row" property="cust_po_receive_date"/></td>
<td><bean:write  name="row" property="customer_segment"/></td>
<td><bean:write  name="row" property="customer_service_rfs_date"/></td>
<td><bean:write  name="row" property="demo_type"/></td>
<td><bean:write  name="row" property="disconnection_remark"/></td>
<td><bean:write  name="row" property="end_date_days"/></td>
<td><bean:write  name="row" property="end_date_logic"/></td>
<td><bean:write  name="row" property="end_date_months"/></td>
<td><bean:write  name="row" property="form_c_available"/></td>
<td><bean:write  name="row" property="frequency"/></td>
<td><bean:write  name="row" property="hardware_flag"/></td>
<td><bean:write  name="row" property="hardware_type"/></td>
<td><bean:write  name="row" property="indicative_value"/></td>
<td><bean:write  name="row" property="inv_amt"/></td>
<td><bean:write  name="row" property="last_mile_media"/></td>
<td><bean:write  name="row" property="last_mile_provider"/></td>
<td><bean:write  name="row" property="last_mile_remarks"/></td>
<td><bean:write  name="row" property="legal_entity"/></td>
<td><bean:write  name="row" property="licence_company"/></td>
<td><bean:write  name="row" property="link_type"/></td>
<td><bean:write  name="row" property="loc_date"/></td>
<td><bean:write  name="row" property="loc_number"/></td>
<td><bean:write  name="row" property="logical_circuit_id"/></td>
<td><bean:write  name="row" property="m6_order_id"/></td>
<td><bean:write  name="row" property="nature_of_sale"/></td>
<td><bean:write  name="row" property="new_order_remarks"/></td>
<td><bean:write  name="row" property="notice_period"/></td>
<td><bean:write  name="row" property="order_creation_date"/></td>
<td><bean:write  name="row" property="order_date"/></td>
<td><bean:write  name="row" property="order_line_id"/></td>
<td><bean:write  name="row" property="order_month"/></td>
<%-- <td><bean:write  name="row" property="order_number"/></td> --%>
<td><bean:write  name="row" property="order_stage"/></td>
<td><bean:write  name="row" property="order_type"/></td>
<td><bean:write  name="row" property="party"/></td>
<td><bean:write  name="row" property="party_id"/></td>
<td><bean:write  name="row" property="penalty_clause"/></td>
<td><bean:write  name="row" property="period_in_month"/></td>
<td><bean:write  name="row" property="pk_chageges_id"/></td>
<td><bean:write  name="row" property="pm_prov_date"/></td>
<td><bean:write  name="row" property="po_date"/></td>
<td><bean:write  name="row" property="pre_crm_order_id"/></td>
<td><bean:write  name="row" property="product"/></td>
<td><bean:write  name="row" property="product_name"/></td>
<td><bean:write  name="row" property="region"/></td>
<td><bean:write  name="row" property="request_received_date"/></td>
<td><bean:write  name="row" property="sec_loc"/></td>
<td><bean:write  name="row" property="service_no"/></td>
<td><bean:write  name="row" property="service_order_type"/></td>
<td><bean:write  name="row" property="service_stage"/></td>
<td><bean:write  name="row" property="sr_number"/></td>
<td><bean:write  name="row" property="standard_reason"/></td>
<td><bean:write  name="row" property="start_date_days"/></td>
<td><bean:write  name="row" property="start_date_logic"/></td>
<td><bean:write  name="row" property="start_date_months"/></td>
<td><bean:write  name="row" property="store"/></td>
<td><bean:write  name="row" property="sub_product"/></td>
<td><bean:write  name="row" property="taxation"/></td>
<td><bean:write  name="row" property="token_no"/></td>
<td><bean:write  name="row" property="tot_po_amt"/></td>
<td><bean:write  name="row" property="total_amount"/></td>
<td><bean:write  name="row" property="type_of_sale"/></td>
<td><bean:write  name="row" property="vertical"/></td>
<td><bean:write  name="row" property="project_mgr"/></td>
<td><bean:write  name="row" property="project_mgr_email"/></td>
<td><bean:write  name="row" property="provision_bandwidth"/></td>
<td><bean:write  name="row" property="quote_no"/></td>
<td><bean:write  name="row" property="ratio"/></td>
<td><bean:write  name="row" property="region_name"/></td>
<td><bean:write  name="row" property="re_logged_lsi_no"/></td>
<td><bean:write  name="row" property="service_name"/></td>
<td><bean:write  name="row" property="service_number"/></td>
<td><bean:write  name="row" property="sub_change_type"/></td>
<td><bean:write  name="row" property="sub_product_type"/></td>
<td><bean:write  name="row" property="taxexemption_reason"/></td>
<td><bean:write  name="row" property="to_city"/></td>
<td><bean:write  name="row" property="to_site"/></td>
<td><bean:write  name="row" property="uom"/></td>
<td><bean:write  name="row" property="zone"/></td>
<td><bean:write  name="row" property="dis_sr"/></td>
<td><bean:write  name="row" property="dod"/></td>
<td><bean:write  name="row" property="logical_si_no"/></td>
<td><bean:write  name="row" property="line_it_no"/></td>
<%-- <td><bean:write  name="row" property="account_id"/></td> --%>
<td><bean:write  name="row" property="ckt_id"/></td>
<td><bean:write  name="row" property="package_id"/></td>
<td><bean:write  name="row" property="package_name"/></td>
<td><bean:write  name="row" property="componentinfoid"/></td>
<td><bean:write  name="row" property="component_id"/></td>
<td><bean:write  name="row" property="component_name"/></td>
<td><bean:write  name="row" property="component_status"/></td>
<td><bean:write  name="row" property="component_start_logic"/></td>
<td><bean:write  name="row" property="component_start_date"/></td>
<td><bean:write  name="row" property="component_end_logic"/></td>
<td><bean:write  name="row" property="component_end_date"/></td>
<td><bean:write  name="row" property="comp_start_days"/></td>
<td><bean:write  name="row" property="comp_start_months"/></td>
<td><bean:write  name="row" property="comp_end_days"/></td>
<td><bean:write  name="row" property="comp_end_months"/></td>
<td><bean:write  name="row" property="component_type"/></td>
<td><bean:write  name="row" property="component_instance_id"/></td>
<td><bean:write  name="row" property="start_component_token_no"/></td>
<td><bean:write  name="row" property="end_component_token_no"/></td>
<td><bean:write  name="row" property="rate_code"/></td>
<td><bean:write  name="row" property="pri_loc"/></td>
<td><bean:write  name="row" property="billing_trig_date"/></td>
<td><bean:write  name="row" property="CHALLEN_NO"/></td>
<td><bean:write  name="row" property="dispatch_address"/></td>
<td><bean:write  name="row" property="LINE_NAME"/></td>
<td><bean:write  name="row" property="po_number"/></td>






</tr>
</logic:iterate>
</logic:notEmpty>
</table>
</logic:present>
</body>
</html:html>







