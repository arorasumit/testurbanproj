<!-- [001] Priya Gupta 28-Jan-2016 Added 3 new columns in ob value report usage -->
<!-- [002] Gunjan Singla 6-May-16   20160418-R1-022266  Added columns -->
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html:html>
<head>
<%
response.setContentType("application/vnd.ms-excel");
%>
</head>
<body>
<logic:present name="displayOBReportInExcelExt" scope="request">
	<% response.setHeader("content-Disposition","attachment;filename=OBVALUEReport.xls"); %>
	<table style="display:block;overflow:auto;vertical-align:top" width="99%" border="1" align="center" class="tab2" >
		<tr>
			<td colspan="46" align="left" style="font-size:10px"><strong>OB VALUE REPORT(USAGE)</strong></td>
		</tr>
		<tr>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Crm Order Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Opportunity Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LineType</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Copc Approved Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical Circuit Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Quotenumber</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Product Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Sub Product Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>From Site</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>To Site</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Sub Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Provision Bandwidth</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Project Mgr</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Project Mgr Email</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Mgr</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Zone</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Category</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Po Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Po Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Entry Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pm Approval Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Manager Receive Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo InfraStart Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo InfraEndDate</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Rfs Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Category<b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Reporting Status</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Line Name</b></td>
			
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Sub LineName</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pk Charges Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Line No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Company Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Licence Company</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contract Period</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order LineType</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>UOM</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Bandwidth</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Uom</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>From City</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>To City</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Old Order Total</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Old Lineitem Amount</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Old Contract Period</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Ratio</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Taxation</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Factory Circuit Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Kms Distance</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Manager Emailid</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Currency</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Total</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Amount</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bl Source</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dispatch Address</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Parent Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Loc_Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Manual OB</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>COPC Approved By</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PM Approved By</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo Flag</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>On-net/Off-net</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Media Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cancellation Reason</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>RR Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Days</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Entered By</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Currency ExchnageRate(INR)</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OB MONTH</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OB YEAR</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OB VALUE</b></td>
			
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OB VALUE(INR)</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Manual OB(INR)</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Segment</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Project Category</b></td>
			<!--	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Remarks for Service</b></td>  This column should be hidden temporary -->
											
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ENTRY TYPE</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>is NFA</b></td>
			<!-- [001] start -->
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>SalesForce Opportunity Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Network Type</b></td>
			<!-- <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Partner Id</b></td> [002]-->
			<!-- [001] end -->
			<!-- [002] start -->
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Partner Code</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Partner Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Field Engineer</b></td>
			<!-- [002] End -->
		</tr>
		<logic:empty  name="displayOBReportInExcelExt">
		<tr align="center" ><td colspan="32" align="center"><b><font color="red">No Records Found</font></b></td></tr>
		</logic:empty>
		<logic:present name="displayOBReportInExcelExt" scope="request">
			<logic:notEmpty  name="displayOBReportInExcelExt" scope="request">		
				<logic:iterate id="row" name="displayOBReportInExcelExt" indexId="recordId">
					<tr>
						<td ><bean:write name="row" property="partyNo"/>&nbsp;</td>
						<td ><bean:write name="row" property="partyName"/>&nbsp;</td>
						<td ><bean:write name="row" property="orderNo"/>&nbsp;</td>
						<td ><bean:write name="row" property="opportunityId"/>&nbsp;</td>
					    <td ><bean:write name="row" property="lineType"/>&nbsp;</td>
						<td ><bean:write name="row" property="copcApproveDate"/>&nbsp;</td>
						<td ><bean:write name="row" property="logicalCircuitId"/>&nbsp;</td>
						<td ><bean:write name="row" property="serviceId"/>&nbsp;</td>
						<td ><bean:write name="row" property="quoteNo"/>&nbsp;</td>
						<td ><bean:write name="row" property="productName"/>&nbsp;</td>
						<td ><bean:write name="row" property="subProductName"/>&nbsp;</td>
						<td ><bean:write name="row" property="primaryLocation"/>&nbsp;</td>
						<td ><bean:write name="row" property="seclocation"/>&nbsp;</td>
						<td ><bean:write name="row" property="ordersubtype"/>&nbsp;</td>
						<td ><bean:write name="row" property="bandwaidth"/>&nbsp;</td>
						<td ><bean:write name="row" property="prjmngname"/>&nbsp;</td>
						<td ><bean:write name="row" property="prjmgremail"/>&nbsp;</td>
						<td ><bean:write name="row" property="actmngname"/>&nbsp;</td>
						<td ><bean:write name="row" property="zoneName"/>&nbsp;</td>
						<td ><bean:write name="row" property="regionName"/>&nbsp;</td>
						<td ><bean:write name="row" property="verticalDetails"/>&nbsp;</td>
						<td ><bean:write name="row" property="act_category"/>&nbsp;</td>
						<td ><bean:write name="row" property="custPoDetailNo"/>&nbsp;</td>
						<td ><bean:write name="row" property="custPoDate"/>&nbsp;</td>
						<td ><bean:write name="row" property="orderDate"/>&nbsp;</td>
						<td ><bean:write name="row" property="pmApproveDate"/>&nbsp;</td>
					    <td ><bean:write name="row" property="amApproveDate"/>&nbsp;</td>
						<td ><bean:write name="row" property="demo_infrastartdate"/>&nbsp;</td>
						<td ><bean:write name="row" property="demo_infra_enddate"/>&nbsp;</td>
						<td ><bean:write name="row" property="rfs_date"/>&nbsp;</td>
						<td ><bean:write name="row" property="ordercategory"/>&nbsp;</td>
						<td ><bean:write name="row" property="orderStatus"/>&nbsp;</td>
						
						<td ><bean:write name="row" property="linename"/>&nbsp;</td>
						<td ><bean:write name="row" property="sub_linename"/>&nbsp;</td>
						<td ><bean:write name="row" property="componentName"/>&nbsp;</td>
						<td ><bean:write name="row" property="componentID"/>&nbsp;</td>
						<td ><bean:write name="row" property="serviceProductID"/>&nbsp;</td>
						<td ><bean:write name="row" property="serviceName"/>&nbsp;</td>
						<td ><bean:write name="row" property="accountID"/>&nbsp;</td>
						<td ><bean:write name="row" property="entity"/>&nbsp;</td>
						<td ><bean:write name="row" property="licCompanyName"/>&nbsp;</td>
						<td ><bean:write name="row" property="contractPeriod"/>&nbsp;</td>
					    <td ><bean:write name="row" property="serviceType"/>&nbsp;</td>
						<td ><bean:write name="row" property="uom"/>&nbsp;</td>
						<td ><bean:write name="row" property="billing_bandwidth"/>&nbsp;</td>
						<td ><bean:write name="row" property="billing_uom"/>&nbsp;</td>
						<td ><bean:write name="row" property="from_city"/>&nbsp;</td>
						<td ><bean:write name="row" property="to_city"/>&nbsp;</td>
						<td ><bean:write name="row" property="oldordertotal"/>&nbsp;</td>
						<td ><bean:write name="row" property="oldlineamt"/>&nbsp;</td>
						<td ><bean:write name="row" property="old_contract_period"/>&nbsp;</td>
						<td ><bean:write name="row" property="ratio"/>&nbsp;</td>
						<td ><bean:write name="row" property="taxation"/>&nbsp;</td>
						<td ><bean:write name="row" property="factory_ckt_id"/>&nbsp;</td>
						<td ><bean:write name="row" property="distance"/>&nbsp;</td>
						<td ><bean:write name="row" property="accountManager"/>&nbsp;</td>
						<td ><bean:write name="row" property="currencyCode"/>&nbsp;</td>
						<td ><bean:write name="row" property="orderTotal"/>&nbsp;</td>
						<td ><bean:write name="row" property="poAmount"/>&nbsp;</td>
						<td ><bean:write name="row" property="bisource"/>&nbsp;</td>
						<td ><bean:write name="row" property="orderType"/>&nbsp;</td>
						<td ><bean:write name="row" property="dispatchAddress1"/>&nbsp;</td>
						<td ><bean:write name="row" property="parent_name"/>&nbsp;</td>
						<td ><bean:write name="row" property="loc_date"/>&nbsp;</td>
						<td ><bean:write name="row" property="finalOB"/>&nbsp;</td>
						<td ><bean:write name="row" property="copcApprovedBy"/>&nbsp;</td>
						<td ><bean:write name="row" property="pmApprovedby"/>&nbsp;</td>
						<td ><bean:write name="row" property="demoFlag"/>&nbsp;</td>
						<td ><bean:write name="row" property="offnet"/>&nbsp;</td>
						<td ><bean:write name="row" property="mediaType"/>&nbsp;</td>
						<td ><bean:write name="row" property="cancellationReason"/>&nbsp;</td>
						<td ><bean:write name="row" property="rRDate"/>&nbsp;</td>
						<td ><bean:write name="row" property="diffDays"/>&nbsp;</td>
						<td ><bean:write name="row" property="orderEnteredBy"/>&nbsp;</td>
						<td ><bean:write name="row"  property="exchangeRate"/>&nbsp;</td>
						<td ><bean:write name="row" property="obMonth"/>&nbsp;</td>
						<td ><bean:write name="row" property="obYear"/>&nbsp;</td>
						<td ><bean:write name="row" property="obValue"/>&nbsp;</td>
						
						<td  ><bean:write name="row" property="obValueINR"/>&nbsp;</td>
						<td  ><bean:write name="row" property="finalOBINR"/>&nbsp;</td>
						
						<td  ><bean:write name="row" property="customerSegment"/>&nbsp;</td>
						<td  ><bean:write name="row" property="projectCategory"/>&nbsp;</td>
						<!--  <td align="left" class='inner col3'><bean:write  name="row" property="serviceRemarks"/>&nbsp;</td>-->
											
						<td ><bean:write name="row" property="entryType"/>&nbsp;</td>
						<td ><bean:write name="row" property="isNfa"/>&nbsp;</td>
						<!-- [001] start -->
						<td ><bean:write name="row" property="salesForceOpportunityNumber"/>&nbsp;</td>
						<td ><bean:write name="row" property="networkType"/>&nbsp;</td>
						<%-- <td ><bean:write name="row" property="partnerId"/>&nbsp;</td>[002] --%>
						<!-- [001] end -->
						<!-- [002] start -->
						<td ><bean:write name="row" property="partnerCode"/>&nbsp;</td>
						<td ><bean:write name="row" property="channelPartner"/>&nbsp;</td>
						<td ><bean:write name="row" property="fieldEngineer"/>&nbsp;</td>
						<!-- [002] end -->
					</tr>
				</logic:iterate>
			</logic:notEmpty>	
		</logic:present>
	</table>	
</logic:present>
</body>
</html:html>