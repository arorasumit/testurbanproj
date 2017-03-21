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
<%--  [001] Priya Gupta  --%>
<html:html>
<head>
<%
response.setContentType("application/vnd.ms-excel");
%>
</head>
var isUsage ="<%= request.getAttribute("isUsage") %>";
<body>
<logic:present name="displayAdvancePaymentReport" scope="request">
	<% response.setHeader("content-Disposition","attachment;filename=BillingTriggerLineitemDump.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="25" align="left" style="font-size:10px"><strong>Billing Trigger Line item Dump</strong></td>
		</tr>
		<tr align="Center">
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Item No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Item Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical SI No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Logical SI No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Change Order Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>SI ID</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>FX Child Accoun</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>FX Status</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Challan No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Challan Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LOC No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LOC Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LOC Recv Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trigger Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Segment</b></td>
				<!-- [001] start -->
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Trigger Process</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LOC UPDATE Process</b></td>
			<logic:equal value="2" name ="reportsBean" property="actiontype">
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Automatic Trigger Error</b></td>	
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Trigger_Process_Pendency_Start</b></td>	
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Loc_Update_Process_Pendency_Start</b></td>						
			</logic:equal>
			<!-- [001] end-->
		</tr>
		<logic:empty name="displayAdvancePaymentReport">
			<tr
				><td colspan="25" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
		<logic:notEmpty  name="displayAdvancePaymentReport" scope="request"> 					
			<logic:iterate id="row" name="displayAdvancePaymentReport" indexId="recordId">
				<tr>
					<td><bean:write name="row" property="lineNo"/></td>
					<td><bean:write name="row" property="lineName"/></td>
					<td><bean:write name="row" property="lsi"/></td>
					<td><bean:write name="row" property="clsi"/></td>
					<td><bean:write name="row" property="orderNo"/></td>
					<td><bean:write name="row" property="orderType"/></td>
					<td><bean:write name="row" property="change_OrderType"/></td>
					<td><bean:write name="row" property="siID"/></td>
					<td><bean:write name="row" property="fxChildAccount"/></td>
					<td><bean:write name="row" property="fxStatus"/></td>
					<td><bean:write name="row" property="lineStatus"/></td>
					<td><bean:write name="row" property="challanNo"/></td>
					<td><bean:write name="row" property="locNo"/></td>
					<td><bean:write name="row" property="locDate"/></td>
					<td><bean:write name="row" property="loc_Recv_Date"/></td>
					<td><bean:write name="row" property="billingTriggerDate"/></td>
					<td><bean:write name="row" property="customerSegment"/></td>
						<!-- [001] start-->
					<td><bean:write name="row" property="triggerprocess"/></td>
					<td><bean:write name="row" property="locupdateprocess"/></td>
					<logic:equal value="2" name ="reportsBean" property="actiontype">
					<td><bean:write name="row" property="automatictriggereror"/></td>
					<td><bean:write name="row" property="triggerprocesspendencystart_String"/></td>
					<td><bean:write name="row" property="locupdateprocesspendencystart_String"/></td>											
					</logic:equal>
					<!-- [001] end-->
				</tr>
			</logic:iterate>
		</logic:notEmpty>
	</table>
</logic:present>
</body>
</html:html>