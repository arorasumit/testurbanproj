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
<logic:present name="DisconnectChangeOrderReportInExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=DISCONNECTIONCHANGEORDERREPORT.xls"); %>
<table width="100%" border="1" cellpadding="0" cellspacing="0">
<tr><logic:equal name="isUsage" value="1"><td colspan="16" align="left" style="font-size:10px"><strong>Disconnection Change Order Report (Usage)</strong></td></logic:equal></tr><logic:equal name="isUsage" value="1"><tr>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Logical SI No </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Service Name </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Line Name </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Account No </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Credit Period</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Currency</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Legal Entity </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Mode </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Trigger Flag </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Type </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Format </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Licence Company </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Taxation </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Penalty Clause </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Level </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Level No</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>PO No </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>PO Date </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>PM Prov Date</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>LOC Date </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Trigger Date </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Child A/C No </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Child A/C Internal No </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Child A/C FX Status </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Date </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Type </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Service Order Type </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>COPC Approve Date </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust Logical SI No.</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Service Stage </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Stage </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Account Mgr </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Project Mgr </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Creation Date </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>RFS Date </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust PO Rec Date </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Vertical </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Region </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Demo Type </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>New Order Remark</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Stage Description </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Disconnection Remarks </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>LineItemNo </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Months </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Ckt Id </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Standard Reason </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bandwidth </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Bandwidth </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Bandwidth UOM </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bandwidth UOM </b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Chargeable Distance</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Product Name</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust Po Date</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust Po Number</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Loc Number</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Pri Loc</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Sec Loc</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Product</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Sub Product</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Ratio</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Number</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Line Id</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust Logical Si Id</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Service No</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Total Amount</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Contract Period Mnths</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Commitment Period</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Notice Period</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Period In Month</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Tot Po Amt</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party Id</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust Account Id</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>M6 Ckt Id</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>M6 Order Id</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Location From</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Location To</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Package ID</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Package Name</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component InfoID</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component ID</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component Name</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component Status</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component Start Logic</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component Start Date</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component End Logic</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component End Date</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Start Date Days</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Start Date Months</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>End Date Days</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>End Date Months</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component Type</b></td>
<%--<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component Cyclic/Non-Cyclic Amount</b></td>--%>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component FX Instance Id</b></td>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Start Token No</b></td>
<%--<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Start FX Status</b></td>--%>
<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>End Token No</b></td>
<%--<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>End FX Status</b></td>--%>
					<%--Satish Starts --%>
					<td align="center" style="font-size: 9px" bgcolor="#FF9255"><b>Billing Trigger CreateDate</b></td>
							<td align="center" style="font-size: 9px" bgcolor="#FF9255"><b>SR NO</b></td>
							<td align="center" style="font-size: 9px" bgcolor="#FF9255"><b>Customer SEGMENT CODE</b></td>
							<td align="center" style="font-size: 9px" bgcolor="#FF9255"><b>Desired Due Date</b></td>
							<td align="center" style="font-size: 9px" bgcolor="#FF9255"><b>Derived Disconnection Date</b></td>
							<td align="center" style="font-size: 9px" bgcolor="#FF9255"><b>IsTrigger Required</b></td>
							<td align="center" style="font-size: 9px" bgcolor="#FF9255"><b>Line Triggered</b></td>
							<td align="center" style="font-size: 9px" bgcolor="#FF9255"><b>Trigger Process</b></td>
							<td align="center" style="font-size: 9px" bgcolor="#FF9255"><b>Trigger DoneBy</b></td>
							<td align="center" style="font-size: 9px" bgcolor="#FF9255"><b>Automatic Trigger Error</b></td>
						<%-- Satish Starts --%>	
				</tr>
</logic:equal>
<logic:empty name="DisconnectChangeOrderReportInExcel">
<tr><td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td></tr>
</logic:empty>
<logic:notEmpty  name="DisconnectChangeOrderReportInExcel" scope="request"> 					
<logic:iterate id="row" name="DisconnectChangeOrderReportInExcel" indexId="recordId">
<logic:equal name="isUsage" value="1">
<tr><td><bean:write  name="row" property="customer_logicalSINumber"/></td>
<td><bean:write  name="row" property="serviceName"/></td><td>
<bean:write  name="row" property="linename"/></td>
<td><bean:write  name="row" property="crmAccountNoString"/></td>
<td><bean:write  name="row" property="creditPeriodName"/></td>
<td><bean:write  name="row" property="currencyName"/></td>
<td><bean:write  name="row" property="entity"/></td>
<td><bean:write  name="row" property="billingMode"/></td>
<td><bean:write  name="row" property="billing_Trigger_Flag"/></td>
<td><bean:write  name="row" property="billingTypeName"/></td>
<td><bean:write  name="row" property="billingformat"/></td>
<td><bean:write  name="row" property="licCompanyName"/></td>
<td><bean:write  name="row" property="taxation"/></td>
<td><bean:write  name="row" property="penaltyClause"/></td>
<td><bean:write  name="row" property="billingLevelName"/></td>
<td><bean:write  name="row" property="billingLevelId"/></td>
<td><bean:write  name="row" property="poNumber"/></td>
<td><bean:write  name="row" property="poDate"/></td>
<td><bean:write  name="row" property="party"/></td>
<td><bean:write  name="row" property="pm_pro_date"/></td>
<td><bean:write  name="row" property="locDate"/></td>
<td><bean:write  name="row" property="billingTriggerDate"/></td>
<td><bean:write  name="row" property="child_act_no"/></td>
<td><bean:write  name="row" property="fx_internal_acc_id"/></td>
<td><bean:write  name="row" property="child_ac_fxstatus"/></td>
<td><bean:write  name="row" property="orderDate"/></td>
<td><bean:write  name="row" property="order_type"/></td>
<td><bean:write  name="row" property="serviceOrderType"/></td>
<td><bean:write  name="row" property="copcapprovaldate"/></td>
<td><bean:write  name="row" property="customer_logicalSINumber"/></td>
<td><bean:write  name="row" property="serviceStage"/></td>
<td><bean:write  name="row" property="orderStage"/></td>
<td><bean:write  name="row" property="actmgrname"/></td>
<td><bean:write  name="row" property="prjmngname"/></td>
<td><bean:write  name="row" property="orderDate"/></td>
<td><bean:write  name="row" property="rfs_date"/></td>
<td><bean:write  name="row" property="cust_po_rec_date"/></td>
<td><bean:write  name="row" property="verticalDetails"/></td>
<td><bean:write  name="row" property="region"/></td>
<td><bean:write  name="row" property="demo"/></td>
<td><bean:write  name="row" property="newOrderRemark"/></td>
<td><bean:write  name="row" property="orderStageDescription"/></td>
<td><bean:write  name="row" property="disconnection_remarks"/></td>
<td><bean:write  name="row" property="lineno"/></td>
<td><bean:write  name="row" property="ordermonth"/></td>
<td><bean:write  name="row" property="ckt_id"/></td>
<td><bean:write  name="row" property="standard_reason"/></td>
<td><bean:write  name="row" property="bandwaidth"/></td>
<td><bean:write  name="row" property="billing_bandwidth"/></td>
<td><bean:write  name="row" property="billing_uom"/></td>
<td><bean:write  name="row" property="uom"/></td>
<td><bean:write  name="row" property="chargeable_Distance"/></td>
<td><bean:write  name="row" property="productName"/></td>
<td><bean:write  name="row" property="custPoDate"/></td>
<td><bean:write  name="row" property="custPoDetailNo"/></td>
<td><bean:write  name="row" property="locno"/></td>
<td><bean:write  name="row" property="primarylocation"/></td>
<td><bean:write  name="row" property="seclocation"/></td>
<td><bean:write  name="row" property="prodAlisName"/></td>
<td><bean:write  name="row" property="sub_linename"/></td>
<td><bean:write  name="row" property="ratio"/></td>
<td><bean:write  name="row" property="orderNo"/></td>
<td><bean:write  name="row" property="orderLineNumber"/></td>
<td><bean:write  name="row" property="customer_logicalSINumber"/></td>
<td><bean:write  name="row" property="serviceId"/></td>
<td><bean:write  name="row" property="totalAmountIncludingCurrent"/></td>
<td><bean:write  name="row" property="contractMonths"/></td>
<td><bean:write  name="row" property="commitmentPeriod"/></td>
<td><bean:write  name="row" property="noticePeriod"/></td>
<td><bean:write  name="row" property="periodsInMonths"/></td>
<td><bean:write  name="row" property="totalPoAmt"/></td>
<td><bean:write  name="row" property="party_id"/></td>
<td><bean:write  name="row" property="cust_act_id"/></td>
<td><bean:write  name="row" property="m6cktid"/></td>
<td><bean:write  name="row" property="m6_order_id"/></td>
<td><bean:write  name="row" property="billing_location_from"/></td>
<td><bean:write  name="row" property="billing_location_to"/>
</td><%--  Manisha : changes for Usage --%><td>
<bean:write  name="row" property="packageID"/></td>
<td><bean:write  name="row" property="packageName"/></td>
<td><bean:write  name="row" property="componentInfoID"/></td>
<td><bean:write  name="row" property="componentID"/></td><td>
<bean:write  name="row" property="componentName"/></td>
<td><bean:write  name="row" property="componentDto.componentStatus"/></td>
<td><bean:write  name="row" property="componentDto.startLogic"/></td>
<td><bean:write  name="row" property="componentDto.startDate"/></td>
<td><bean:write  name="row" property="componentDto.endLogic"/></td>
<td><bean:write  name="row" property="componentDto.end_date"/></td>
<td><bean:write  name="row" property="startDateDays"/></td>
<td><bean:write  name="row" property="startDateMonth"/></td>
<td><bean:write  name="row" property="endDateDays"/></td>
<td><bean:write  name="row" property="endDateMonth"/></td>
<td><bean:write  name="row" property="componentDto.componentType"/></td>
<%--<td><bean:write  name="row" property="componentDto.componentAmount"/></td>--%>
<td><bean:write  name="row" property="componentDto.componentInstanceID"/></td>
<td><bean:write  name="row" property="componentDto.fxTokenNo"/></td>
<%--<td><bean:write  name="row" property="componentDto.fxStartStatus"/></td>--%>
<td><bean:write  name="row" property="componentDto.endTokenNo"/></td>
<%--<td><bean:write  name="row" property="componentDto.endFxStatus"/></td>--%>
<%--  Manisha : End --%>
							<%--  Satish : Start --%>
							<td><bean:write name="row" property="billingtrigger_createdate" /></td>
							<td><bean:write name="row" property="srno" /></td>
							<td><bean:write name="row" property="cus_segment" /></td>
							<td><bean:write name="row" property="desiredDueDate" /></td>
							<td><bean:write name="row" property="derivedDisconnectionDate" /></td>
							<td><bean:write name="row" property="isTriggerRequired" /></td>
							<td><bean:write name="row" property="lineTriggered" /></td>
							<td><bean:write name="row" property="triggerProcess" /></td>
							<td><bean:write name="row" property="triggerDoneBy" /></td>
							<td><bean:write name="row" property="automaticTriggerError" /></td>
							<%--  Satish : End --%>
						</tr>
</logic:equal>
</logic:iterate>	
</logic:notEmpty>
</table>
</logic:present>
</body>
</html:html>