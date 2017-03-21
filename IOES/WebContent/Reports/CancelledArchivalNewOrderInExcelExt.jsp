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
<logic:present name="cancelledReportOrderExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=ArchivalCancelledReport.xls"); %>
<table width="100%" border="1" cellpadding="0" cellspacing="0">
<tr><td colspan="25" align="left" style="font-size:10px"><strong>Archival Cancelled Report</strong></td>
</tr>
<tr align="Center">
<td align="center" class='inner col1 head1' ><b>Order No</b></td>
<td align="center" class='inner col1 head1' ><b>Account ID</b></td>
<td align="center" class='inner col1 head1' ><b>Account Manager</b></td>
<td align="center" class='inner col1 head1' ><b>Account Number</b></td>
<td align="center" class='inner col1 head1' ><b>PO Amount</b></td>
<td align="center" class='inner col1 head1' ><b>Customer Segment</b></td>
<td align="center" class='inner col1 head1' ><b>Account Category</b></td>
<td align="center" class='inner col1 head1' ><b>Vertical</b></td>
<td align="center" class='inner col1 head1' ><b>Billing Charge Start Date</b></td>
<td align="center" class='inner col1 head1' ><b>Service Name</b></td>
<td align="center" class='inner col1 head1' ><b>Order Line No</b></td>
<td align="center" class='inner col1 head1' ><b>Line Name </b></td>
<td align="center" class='inner col1 head1' ><b>Cancel Flag</b></td>
<td align="center" class='inner col1 head1' ><b>Provision Bandwidth</b></td>
<td align="center" class='inner col1 head1' ><b>UOM</b></td>
<td align="center" class='inner col1 head1' ><b>Billing Bandwidth</b></td>
<td align="center" class='inner col1 head1' ><b>Store Address</b></td>
<td align="center" class='inner col1 head1' ><b>Bill UOM</b></td>
<td align="center" class='inner col1 head1' ><b>Category of Order</b></td>
<td align="center" class='inner col1 head1' ><b>Contract Period</b></td>
<td align="center" class='inner col1 head1' ><b>Company Name</b></td>
<td align="center" class='inner col1 head1' ><b>Customer RFS Date</b></td>
<td align="center" class='inner col1 head1' ><b>Customer Service RFS Date</b></td>
<td align="center" class='inner col1 head1' ><b>Currency</b></td>
<td align="center" class='inner col1 head1' ><b>Charge Name</b></td>
<td align="center" class='inner col1 head1' ><b>Customer PO Date</b></td>
<td align="center" class='inner col1 head1' ><b>Customer PO Number</b></td>
<td align="center" class='inner col1 head1' ><b>Cyclic or Non Cyclic</b></td>
<td align="center" class='inner col1 head1' ><b>Challen No</b></td>

<td align="center" class='inner col1 head1' ><b>From Site</b></td>
<td align="center" class='inner col1 head1' ><b>To Site</b></td>
<td align="center" class='inner col1 head1' ><b>Item Quantity</b></td>
<td align="center" class='inner col1 head1' ><b>KMS Distance</b></td>
<td align="center" class='inner col1 head1' ><b>Line Item Amount</b></td>
<td align="center" class='inner col1 head1' ><b>COPC Approved Date</b></td>
<td align="center" class='inner col1 head1' ><b>Iine Item Description</b></td>
<td align="center" class='inner col1 head1' ><b>Loc Date</b></td>
<td align="center" class='inner col1 head1' ><b>Account Manager Receive Date</b></td>
<td align="center" class='inner col1 head1' ><b>Order Total</b></td>
<td align="center" class='inner col1 head1' ><b>Order Entry Date</b></td>
<td align="center" class='inner col1 head1' ><b>Taxation</b></td>
<td align="center" class='inner col1 head1' ><b>Taxexemption Reason</b></td>
<td align="center" class='inner col1 head1' ><b>Licence Company</b></td>
<td align="center" class='inner col1 head1' ><b>Logical Circuit ID</b></td>
<td align="center" class='inner col1 head1' ><b>Order Type</b></td>
<td align="center" class='inner col1 head1' ><b>Payment Term</b></td>
<td align="center" class='inner col1 head1' ><b>Project MGR</b></td>
<td align="center" class='inner col1 head1' ><b>Region Name</b></td>
<td align="center" class='inner col1 head1' ><b>Old Line Item Amount</b></td>
<td align="center" class='inner col1 head1' ><b>Demo Type</b></td>
<td align="center" class='inner col1 head1' ><b>Party Name</b></td>
<td align="center" class='inner col1 head1' ><b>Order Stage Description</b></td>
<td align="center" class='inner col1 head1' ><b>Service Stage Description</b></td>
<td align="center" class='inner col1 head1' ><b>Charge End Date</b></td>
<td align="center" class='inner col1 head1' ><b>End Date Logic</b></td>
<td align="center" class='inner col1 head1' ><b>New Order Remark</b></td>
<td align="center" class='inner col1 head1' ><b>Remarks</b></td>
<td align="center" class='inner col1 head1' ><b>Service Order Type</b></td>
<td align="center" class='inner col1 head1' ><b>OSP</b></td>
<td align="center" class='inner col1 head1' ><b>Opportunity ID</b></td>
<td align="center" class='inner col1 head1' ><b>Logical SI No</b></td>
<td align="center" class='inner col1 head1' ><b>Line IT No</b></td>
<td align="center" class='inner col1 head1' ><b>Order Creation Date</b></td>
<td align="center" class='inner col1 head1' ><b>CKT ID</b></td>
<td align="center" class='inner col1 head1' ><b>M6 Order No</b></td>
<td align="center" class='inner col1 head1' ><b>Service ID</b></td>
<td align="center" class='inner col1 head1' ><b>Old LSI</b></td>
<td align="center" class='inner col1 head1' ><b>Cancel Reason</b></td>
<td align="center" class='inner col1 head1' ><b>Cancel Date</b></td>
<td align="center" class='inner col1 head1' ><b>Product</b></td>
<td align="center" class='inner col1 head1' ><b>Sub Product</b></td>
<td align="center" class='inner col1 head1' ><b>Effective Start Data</b></td>

</tr>
<logic:empty name="cancelledReportOrderExcel">
<tr><td colspan="25" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td></tr>
</logic:empty>
<logic:notEmpty  name="cancelledReportOrderExcel" scope="request">
<logic:iterate id="row" name="cancelledReportOrderExcel" indexId="recordId">
<tr>
<td><bean:write  name="row" property="order_no"/></td>
<td><bean:write  name="row" property="account_id"/></td>
<td><bean:write  name="row" property="account_manager"/></td>
<td><bean:write  name="row" property="account_number"/></td>
<td><bean:write  name="row" property="po_amount"/></td>
<td><bean:write  name="row" property="customer_segment"/></td>
<td><bean:write  name="row" property="account_category"/></td>
<td><bean:write  name="row" property="vertical"/></td>
<td><bean:write  name="row" property="billing_charge_start_date"/></td>
<td><bean:write  name="row" property="service_name"/></td>
<td><bean:write  name="row" property="order_line_no"/></td>
<td><bean:write  name="row" property="line_name"/></td>
<td><bean:write  name="row" property="cancel_flag"/></td>
<td><bean:write  name="row" property="provision_bandwidth"/></td>
<td><bean:write  name="row" property="uom"/></td>
<td><bean:write  name="row" property="billing_bandwidth"/></td>
<td><bean:write  name="row" property="store_address"/></td>
<td><bean:write  name="row" property="bill_uom"/></td>
<td><bean:write  name="row" property="category_of_order"/></td>
<td><bean:write  name="row" property="contract_period"/></td>
<td><bean:write  name="row" property="company_name"/></td>
<td><bean:write  name="row" property="customer_rfs_date"/></td>
<td><bean:write  name="row" property="customer_service_rfs_date"/></td>
<td><bean:write  name="row" property="currency"/></td>
<td><bean:write  name="row" property="charge_name"/></td>
<td><bean:write  name="row" property="customer_po_date"/></td>
<td><bean:write  name="row" property="customer_po_number"/></td>
<td><bean:write  name="row" property="cyclic_or_non_cyclic"/></td>
<td><bean:write  name="row" property="challen_no"/></td>

<td><bean:write  name="row" property="from_site"/></td>
<td><bean:write  name="row" property="to_site"/></td>
<td><bean:write  name="row" property="item_quantity"/></td>
<td><bean:write  name="row" property="kms_distance"/></td>
<td><bean:write  name="row" property="line_item_amount"/></td>
<td><bean:write  name="row" property="copc_approved_date"/></td>
<td><bean:write  name="row" property="line_item_description"/></td>
<td><bean:write  name="row" property="loc_date"/></td>
<td><bean:write  name="row" property="account_manager_receive_date"/></td>
<td><bean:write  name="row" property="order_total"/></td>
<td><bean:write  name="row" property="order_entry_date"/></td>
<td><bean:write  name="row" property="taxation"/></td>
<td><bean:write  name="row" property="taxexemption_reason"/></td>
<td><bean:write  name="row" property="licence_company"/></td>
<td><bean:write  name="row" property="logical_circuit_id"/></td>
<td><bean:write  name="row" property="order_type"/></td>
<td><bean:write  name="row" property="payment_term"/></td>
<td><bean:write  name="row" property="project_mgr"/></td>
<td><bean:write  name="row" property="region_name"/></td>
<td><bean:write  name="row" property="old_line_item_amount"/></td>
<td><bean:write  name="row" property="demo_type"/></td>
<td><bean:write  name="row" property="party_name"/></td>
<td><bean:write  name="row" property="order_stage_description"/></td>
<td><bean:write  name="row" property="service_stage_description"/></td>
<td><bean:write  name="row" property="charge_end_date"/></td>
<td><bean:write  name="row" property="end_date_logic"/></td>
<td><bean:write  name="row" property="new_order_remark"/></td>
<td><bean:write  name="row" property="remarks"/></td>
<td><bean:write  name="row" property="service_order_type"/></td>
<td><bean:write  name="row" property="osp"/></td>
<td><bean:write  name="row" property="opportunity_id"/></td>
<td><bean:write  name="row" property="logical_si_no"/></td>
<td><bean:write  name="row" property="line_it_no"/></td>
<td><bean:write  name="row" property="order_creation_date"/></td>
<td><bean:write  name="row" property="ckt_id"/></td>
<td><bean:write  name="row" property="m6orderno"/></td>
<td><bean:write  name="row" property="service_id"/></td>
<td><bean:write  name="row" property="oldLsi"/></td>
<td><bean:write  name="row" property="cancel_reason"/></td>
<td><bean:write  name="row" property="cancel_date"/></td>
<td><bean:write  name="row" property="product"/></td>
<td><bean:write  name="row" property="sub_product"/></td>
<td><bean:write  name="row" property="effective_start_data"/></td>
</tr>
</logic:iterate>
</logic:notEmpty>
</table>
</logic:present>
</body>
</html:html>







