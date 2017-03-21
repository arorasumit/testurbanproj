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
<logic:present name="RestPendingLineReportInExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=RESTPENDINGLINEREPORT.xls"); %>
<table width="100%" border="1" cellpadding="0" cellspacing="0">
<tr><td colspan="16" align="left" style="font-size:10px"><strong>REST PENDING LINE REPORT (USAGE)</strong></td></tr><tr align="Center"><logic:equal name="isUsage" value="1"><tr><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Account Id </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party Name </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party No </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Prj Manager </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Account Manager  </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Date  </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Po Date</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>COPC Approve Date  </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust Po Rec Date  </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust PO Date  </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cancel Date </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bandwidth UOM  </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Location</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Mode </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>PoNumber </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>CustPoNumber  </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cancel By  </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Primary Location  </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Secondary Location  </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Address  </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>RFS Date</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Product Name</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Sub Product Name</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>LineItemNo</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>CRM Product Name</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Level No</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Logical SI No</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust Logical SI No</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Service Name</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Line Name</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>CKT ID</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Location From</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Location To</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Service Stage</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Level</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Demo Type</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Bandwidth </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Uom </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Kms Distance </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Stage </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Child Act No </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Child Act FX Internal No </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cancel Reason </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Type </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Service Order Type </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Vertical </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Region </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Credit Period </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Currency </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Legal Entity </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Type </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Format </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Licence Company </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Taxation </b></td><td align="center" style="font-size: 9px" bgcolor="#FF9255"><b>TaxExemption Reason</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Crm Order Id</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Total Amount</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Periods in Month </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Total PO AMT </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party Id </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>CrmAccountNo </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>M6 Product ID </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>ServiceNo </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>LineNo </b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Package ID</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Package Name</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component ID</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component Name</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component InfoID</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Status</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Start Date</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component Type</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component FX Instance Id</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Start Token No</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Start FX Status</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>End FX Status</b></td> <!--GlobalDataBillingEfficiency BFR5  --><%-- [002] Neha Start --%><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Installation From City</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Installation To City</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Installation From State</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Installation To State</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Contact Name</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Contact Number</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing EmailId</b></td><%-- [002] Neha End --%><%-- Neha Start --%><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Change Order Reason</b></td><%-- Neha End --%>						
</tr>
</logic:equal>
<logic:empty name="RestPendingLineReportInExcel">
<tr><td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td></tr>
</logic:empty>
<logic:notEmpty  name="RestPendingLineReportInExcel" scope="request"> 					
<logic:iterate id="row" name="RestPendingLineReportInExcel" indexId="recordId">
<logic:equal name="isUsage" value="1">
<tr><td><bean:write  name="row" property="accountID"/></td><td><bean:write  name="row" property="partyName"/></td><td><bean:write  name="row" property="partyNo"/></td><td><bean:write  name="row" property="projectManager"/></td><td><bean:write  name="row" property="accountManager"/></td><td><bean:write  name="row" property="orderDate"/></td><td><bean:write  name="row" property="poDate"/></td><td><bean:write  name="row" property="copcapprovaldate"/></td><td><bean:write  name="row" property="poReceiveDate"/></td><td><bean:write  name="row" property="custPoDate"/></td><td><bean:write  name="row" property="canceldate"/></td><td><bean:write  name="row" property="uom"/></td><td><bean:write  name="row" property="billing_address"/></td><td><bean:write  name="row" property="billingMode"/></td><td><bean:write  name="row" property="poNumber"/></td><td><bean:write  name="row" property="custPoDetailNo"/></td><td><bean:write  name="row" property="cancelBy"/></td><td><bean:write  name="row" property="primarylocation"/></td><td><bean:write  name="row" property="seclocation"/></td><td><bean:write  name="row" property="billing_address"/></td><td><bean:write  name="row" property="rfs_date"/></td><td><bean:write  name="row" property="productName"/></td><td><bean:write  name="row" property="subProductName"/></td><td><bean:write  name="row" property="lineno"/></td><td><bean:write  name="row" property="crm_productname"/></td><td><bean:write  name="row" property="billingLevelNo"/></td><td><bean:write  name="row" property="logicalCircuitId"/></td><td><bean:write  name="row" property="customer_logicalSINumber"/></td><td><bean:write  name="row" property="serviceName"/></td><td><bean:write  name="row" property="linename"/></td><td><bean:write  name="row" property="ckt_id"/></td><td><bean:write  name="row" property="location_from"/></td><td><bean:write  name="row" property="location_to"/></td><td><bean:write  name="row" property="serviceStage"/></td><td><bean:write  name="row" property="billingLevel"/></td><td><bean:write  name="row" property="demoType"/></td><td><bean:write  name="row" property="billing_bandwidth"/></td><td><bean:write  name="row" property="billing_uom"/></td><td><bean:write  name="row" property="distance"/></td><td><bean:write  name="row" property="stageName"/></td><td><bean:write  name="row" property="child_act_no"/></td><td><bean:write  name="row" property="fx_internal_acc_id"/></td><td><bean:write  name="row" property="cancelServiceReason"/></td><td><bean:write  name="row" property="order_type"/></td><td><bean:write  name="row" property="ordersubtype"/></td><td><bean:write  name="row" property="verticalDetails"/></td><td><bean:write  name="row" property="regionName"/></td><td><bean:write  name="row" property="creditPeriodName"/></td><td><bean:write  name="row" property="currencyName"/></td><td><bean:write  name="row" property="entity"/></td><td><bean:write  name="row" property="billingTypeName"/></td><td><bean:write  name="row" property="billingFormatName"/></td><td><bean:write  name="row" property="lcompanyname"/></td><td><bean:write  name="row" property="taxation"/></td><td> <bean:write  name="row" property="taxExcemption_Reason"/></td><td><bean:write  name="row" property="orderNumber"/></td><td><bean:write  name="row" property="poAmountSum"/></td><td><bean:write  name="row" property="contractPeriod"/></td><td><bean:write  name="row" property="totalAmountIncludingCurrent"/></td><td><bean:write  name="row" property="party_id"/></td><td><bean:write  name="row" property="crmAccountNo"/></td><td><bean:write  name="row" property="m6_prod_id"/></td><td><bean:write  name="row" property="serviceId"/></td><td><bean:write  name="row" property="serviceproductid"/></td><td><bean:write  name="row" property="packageID"/></td><td><bean:write  name="row" property="packageName"/></td><td><bean:write  name="row" property="componentID"/></td><td><bean:write  name="row" property="componentName"/></td><td><bean:write  name="row" property="componentInfoID"/></td><td><bean:write  name="row" property="componentDto.componentStatus"/></td><td><bean:write  name="row" property="componentDto.startDate"/></td><td><bean:write  name="row" property="componentDto.componentType"/></td><td><bean:write  name="row" property="componentDto.componentInstanceID"/></td><td><bean:write  name="row" property="componentDto.fxTokenNo"/></td><td><bean:write  name="row" property="componentDto.fxStartStatus"/></td><td><bean:write  name="row" property="componentDto.endFxStatus"/></td>	<!--GlobalDataBillingEfficiency BFR5  --><%-- [002] Neha Start --%><td><bean:write  name="row" property="installationFromCity"/></td><td><bean:write  name="row" property="installationToCity"/></td><td><bean:write  name="row" property="installationFromState"/></td><td><bean:write  name="row" property="installationToState"/></td><td><bean:write  name="row" property="billingContactName"/></td><td><bean:write  name="row" property="billingContactNumber"/></td><td><bean:write  name="row" property="billingEmailId"/></td><%-- [002] Neha End --%><%-- Neha Start --%><td><bean:write  name="row" property="standardReason"/></td><%-- Neha End --%>
</tr>
</logic:equal>
</logic:iterate>
</logic:notEmpty>
</table>
</logic:present>
</body>
</html:html>