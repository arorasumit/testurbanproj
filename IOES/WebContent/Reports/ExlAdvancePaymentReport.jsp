<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%--[RPT7052013020]    Vijay    15-May-2013          Remove Account name in This report     --%>
<%--[RPT7052013019]    Mohit     16-May-2013         Added for ORDER REPORT NEW DETAIL CWN --%>
<%--    Mohit     29-May-2013         Added for PERFORMANCE DETAILS REPORT --%>
<%--    Mohit     3-June-2013         Added for MIGRATED APPROVED NEW ORDER DETAIL REPORT --%>
<%--    Mohit     3-June-2013         Added for NON APPROVED/APPROVED CHANGE ORDER DETAILS --%>
<%--   [404040]  opportunityId added in  reports   by Lawkush "ACTIVE LINE ITEMS REPORT   ","PERFORMANCE SUMMARY REPORT  ","PERFORMANCE DETAIL REPORT","ORDER DETAIL REPORT","ORDER REPORT NEW DETAIL CWN","LEPM ORDER DETAIL REPORT"--%>
<%--  [002] Raveendra  12/02/2015   add new field for refund process  --%>
<html:html>
<head>
<%
response.setContentType("application/vnd.ms-excel");
%>
</head>
var isUsage ="<%= request.getAttribute("isUsage") %>";
<body>
<logic:present name="displayAdvancePaymentInExcelExt" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=AdvancePaymentReport.xls"); %>
<table width="100%" border="1" cellpadding="0" cellspacing="0">
<tr><td colspan="25" align="left" style="font-size:10px"><strong>Advance Payment -OTC Report</strong></td>
</tr>
<tr align="Center">
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party ID</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party No</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CRM Account No</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CRM Account Name</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Currency of Order</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Segment</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Creation Date</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>AM Approval Date</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PM Approval Date</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Approval Date</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PO Date</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PO Recieve Date</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LSI</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service No</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Type</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Product</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Sub Product</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Item No</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Item Name</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>License Company</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>FX Child Account</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LOC Date</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trigger Date</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trigger Action Date</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ARC Exempted</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ARC ExpReason</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ARC Chq No</b></td>
<%-- Start [002] --%><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ARC Re Chq No</b></td><%-- End [002] --%>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ARC Chq Date</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ARC Drawn of Bank</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ARC Chq Amt</b></td>
<%-- Start [002] --%><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ARC Re Chq Amt</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ARC Bank A/C No</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ARC Re Bank A/C No</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ARC IFSC Code</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ARC Re IFSC Code</b></td><%-- End [002] --%>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ARC Amt to be adjusted against this LSI</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OTC Exempted</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OTC ExpReason</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OTC Chq No</b></td>
<%-- Start [002] --%><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OTC Re Chq No</b></td><%-- End [002] --%>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OTC Chq Date</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OTC Drawn of Bank</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OTC Chq Amt</b></td>
<%-- Start [002] --%><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OTC Re Chq Amt</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OTC Bank A/C No</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OTC Re Bank A/C No</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OTC IFSC Code</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OTC Re IFSC Code</b></td><%-- End [002] --%>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OTC Amt to be adjusted against this LSI</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Status</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Zone</b></td>
</tr>
<logic:empty name="displayAdvancePaymentInExcelExt">
<tr><td colspan="25" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td></tr>
</logic:empty>
<logic:notEmpty  name="displayAdvancePaymentInExcelExt" scope="request"> 					
<logic:iterate id="row" name="displayAdvancePaymentInExcelExt" indexId="recordId">
<tr>
<td><bean:write name="row" property="party_id"/></td>
<td><bean:write name="row" property="partyNo"/></td>
<td><bean:write name="row" property="crmAccountNo"/></td>
<td><bean:write name="row" property="accountName"/></td>
<td><bean:write name="row" property="orderNo"/></td>
<td><bean:write name="row" property="currencyofOrder"/></td>
<td><bean:write name="row" property="customerSegment"/></td>
<td><bean:write name="row" property="orderCreationDate"/></td>
<td><bean:write name="row" property="amApprovalDate"/></td>
<td><bean:write name="row" property="pmApprovalDate"/></td>
<td><bean:write name="row" property="orderApprovalDate"/></td>
<td><bean:write name="row" property="podate_String"/></td>
<td><bean:write name="row" property="poRecieveDate_String"/></td>
<td><bean:write name="row" property="lsi"/></td>
<td><bean:write name="row" property="serviceNo"/></td>
<td><bean:write name="row" property="product"/></td>
<td><bean:write name="row" property="productName"/></td>
<td><bean:write name="row" property="subproductName"/></td>
<td><bean:write name="row" property="lineNo"/></td>
<td><bean:write name="row" property="lineName"/></td>
<td><bean:write name="row" property="licenseCompany"/></td>
<td><bean:write name="row" property="fxChildAccount"/></td>
<td><bean:write name="row" property="locDate"/></td>
<td><bean:write name="row" property="billingTriggerDate"/></td>
<td><bean:write name="row" property="billingTriggerActionDate_string"/></td>
<td><bean:write name="row" property="arcExempted"/></td>
<td><bean:write name="row" property="arcExpreason"/></td>
<td><bean:write name="row" property="arcChqNo"/></td>
<%-- Start [002] --%><td><bean:write name="row" property="arcReEnterCheckNumber" /></td><%-- End [002] --%>
<td><bean:write name="row" property="arcChqDate"/></td>
<td><bean:write name="row" property="arcBankName"/></td>
<td><bean:write name="row" property="arcChqAmt"/></td>
<%-- Start [002] --%><td><bean:write	name="row" property="arcReEnterCheckamount" /></td>
<td><bean:write	name="row" property="arcBankAccountNumber" /></td>
<td><bean:write name="row" property="arcReEnterBankAccountNumber" /></td>
<td><bean:write name="row" property="arcIfscCode" /></td>
<td><bean:write name="row" property="arcReEnterIfscCode" /></td><%-- End [002] --%>
<td><bean:write name="row" property="arcAmtAjd"/></td>
<td><bean:write name="row" property="otcExempted"/></td>
<td><bean:write name="row" property="otcExpreason"/></td>
<td><bean:write name="row" property="otcChqNo"/></td>
<%-- Start [002] --%><td><bean:write name="row" property="otcReEnterCheckNumber" /></td><%-- End [002] --%>
<td><bean:write name="row" property="otcChqDate"/></td>
<td><bean:write name="row" property="otcBankName"/></td>
<td><bean:write name="row" property="otcChqAmt"/></td>
<%-- Start [002] --%><td><bean:write	name="row" property="otcReEnterCheckamount" /></td>
<td><bean:write	name="row" property="otcBankAccountNumber" /></td>
<td><bean:write name="row" property="otcReEnterBankAccountNumber" /></td>
<td><bean:write name="row" property="otcIfscCode" /></td>
<td><bean:write name="row" property="otcReEnterIfscCode" /></td><%-- End [002] --%>
<td><bean:write name="row" property="otcAmtAjd"/></td>
<td><bean:write name="row" property="orderStatus"/></td>
<td><bean:write name="row" property="zoneName"/></td>
</tr>
</logic:iterate>
</logic:notEmpty>
</table>
</logic:present>
</body>
</html:html>







