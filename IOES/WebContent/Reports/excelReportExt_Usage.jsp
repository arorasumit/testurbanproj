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



<logic:present name="orderReportNewListExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=OrderDetailsNewReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="25" align="left" style="font-size:10px"><strong>ORDER DETAIL NEW REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Crm Order Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Copc Aprv Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical Ckt Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Quote No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Product Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Sub Product Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>From Site</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>To Site</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Sub Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bandwidth</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LineAmt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Prj Magr</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Prj Mgr Email</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Act Mgr</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Zone</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Act Category</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CustPoNumber</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CustPoDate</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pm aprvl Datre</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Act mgr Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Nio Aprv Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo InfraStart Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo InfraEndDate</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Rfs Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Category</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Reporting Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Desc</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Line Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Sub LineName</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pk ChargeId</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Line No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Act No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Company Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Licence Company</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contract Period</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Payment Term</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cyclic Noncyclic</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order LineType</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>UOM</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Bandwidth</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Uom</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>From City</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>To City</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Old Order Total</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Old Line Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Old Contract Period</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Ratio</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Taxation</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Factory Ckt Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Kms Distance</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Act Mgr Email</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Currency</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Total</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>BiSource</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dispatch Address</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Parent Name</b></td>	
						<!--  Meenakshi : change for usage -->		
						<logic:equal name="isUsage" value="1">
								<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component InfoID</b></td>
								<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component ID</b></td>
								<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Name</b></td>
								<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Package ID</b></td>
								<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Package Name</b></td>
						</logic:equal>
			<!-- Meenakshi : End -->
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OSP</b></td>		
		</tr>

		<logic:empty name="orderReportNewListExcel">
			<tr>
				<td colspan="25" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="orderReportNewListExcel" scope="request"> 					
				<logic:iterate id="row" name="orderReportNewListExcel" indexId="recordId">
					<tr>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalCircuitId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="quoteNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="productName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subProductName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primarylocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="seclocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ordersubtype"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAmount"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="prjmngname"/></td>
								<td align="left" style="font-size:9px" width="10%"><bean:write name="row" property="prjmgremail"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="actmngname"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="zoneName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="act_category"/></td>
								<td align="left" style="font-size:9px" width="10%"><bean:write name="row" property="custPoDetailNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmApproveDate"/></td>
							    <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="amApproveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="nio_approve_date"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="demo_infrastartdate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="demo_infra_enddate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="rfs_date"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="ordercategory"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="orderStatus"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="line_desc"/></td> 
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="linename"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="sub_linename"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="chargeName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="chargeinfoID"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="serviceProductID"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="serviceName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="accountID"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="entity"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="licCompanyName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="contractPeriod"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="frequencyName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="chargeTypeName"/></td>
							    <td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="serviceType"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="uom"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="billing_bandwidth"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="billing_uom"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="from_city"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="to_city"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="oldordertotal"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="oldlineamt"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="old_contract_period"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="ratio"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="taxation"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="factory_ckt_id"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="distance"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="accountManager"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="currencyCode"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="orderTotal"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="poAmount"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="bisource"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="orderType"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="dispatchAddress1"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="parent_name"/></td>
								<!--  Meenaskhi : changes for Usage -->
								<logic:equal name="isUsage" value="1">
									<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentInfoID"/></td>
									<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentID"/></td>
									<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentName"/></td>
									<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageID"/></td>
									<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageName"/></td>
								</logic:equal>


								<!--  Meenakshi : End -->
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="osp"/></td>

					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

<logic:present name="startChargeNotPushedInFxExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=StartChargeNotPushedFx.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<logic:equal name="isUsage" value="0">
				<td colspan="14" align="left" style="font-size:10px"><strong>START CHARGE NOT PUSHED IN FX</strong></td>
			</logic:equal>
			<logic:equal name="isUsage" value="1">
				<td colspan="14" align="left" style="font-size:10px"><strong>START COMPONENT NOT PUSHED IN FX</strong></td>
			</logic:equal>
		</tr>
		<logic:equal name="isUsage" value="0">
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical Circuit Id</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Type</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Frequency</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Period</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dnd Dispatch But Not Delivered</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Dnd Dispatch And Delivered</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Dnd Disp Del Not Installed</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Dnd Disp Delivered Installed</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Start Date Logic</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>End Date Logic</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Charge Start Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Charge End Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Contract Start Date</b></td>	
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Contract End Date</b></td>	
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Account Number</b></td>	
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Credit Period</b></td>										
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Currency</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Legal Entity</b></td>					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Billing Mode</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Bill Type</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Bill Format</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Licence Company</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Taxation</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Dispatch Address</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Penelty Clause</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Billing Level</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Billing Level Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Store</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Hardware Type</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Form C Available</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Nature Of Sale</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Type Of Sale</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Pri Location</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Sec Location</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Po Number</b></td>					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Po Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Party</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Party Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Annotation</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Fx Status</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Fx Sd Chg Status</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Fx Ed Chg Status</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Token No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Billing Trig Flag</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Pm Prov Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Loc Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Billing Trig Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Challen No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Challen Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Child Account Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Child Account Fx Status</b></td>					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Warranty Start Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Warranty End Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Extnd Support End Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Orderdate</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Approved Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Type</b></td>
					<!--<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Type Id</b></td>-->
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Service Order Type</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Service Order Type Desc</b></td>					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Copc Approved Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Bill Trg Create Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Logical Si No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Hardware Flag</b></td>
					<!--<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Charge Type Id</b></td> -->
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Service Stage</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Stage</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Account Mgr</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Project Mgr</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Creation Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Customer Service Rfs Date</b></td>					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Po Receive Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Token No Ed</b></td>
					<!--<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Fx Status Ed</b></td>-->
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Po Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Po Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Charge Status</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Loc Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Billing Address</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Si Id</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cancel By</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cancel Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cancel Reason</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Opms Account Id</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Region</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Bandwidth</b></td>					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Vertical</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Location From</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Location To</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Coll Manager</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Coll Mgr Mail</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Coll Mgr Ph</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Billing Bandwidth</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Demo Type</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Crm Order Id</b></td>
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Number</b></td>-->
					<!--<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Charge Hdr Id</b></td>-->
					<!--<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Line Id</b></td>-->
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Service No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Inv Amt</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Amt</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Total Amount</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Installment Rate</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Start Date Days</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Start Date Months</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>End Date Days</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>End Date Months</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Annual Rate</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Trai Rate</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Discount</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Contract Period Mnths</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Commitment Period</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Notice Period</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Principal Amt</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Intrest Rate</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Period In Month</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Tot Po Amt</b></td>
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Party Id</b></td>-->
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Account Id</b></td>-->
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>M6 Product Id</b></td>-->
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>M6 Order Id</b></td>-->
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Ib Order Line Id</b></td>-->
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Ib Service List Id</b></td>-->
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Ib Pk Charges Id</b></td>-->
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Fx Sno</b></td>-->
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Fx Sno Ed</b></td>-->
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Tot Po Amt</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>M6 Order No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Business Serial No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Bus Serial</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Advance</b></td>																								
		</tr>
		</logic:equal>
		
		<logic:equal name="isUsage" value="1">
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No</b></td>				
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical SI No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Name</b></td>														
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Contract Start Date</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Contract End Date</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>CRM Account Number</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Credit Period</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Currency</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Legal Entity</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Mode</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bill Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bill Format</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Licence Company</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Taxation</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Penelty Clause</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Level</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Level Number</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Pri Location</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Sec Location</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Po Number</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Po Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Party</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Party Number</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Trig Flag</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Pm Prov Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Loc Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Trig Date</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Child Account Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Child Internal Account No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Child Account Fx Status</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Orderdate</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Approved Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Order Type</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Service Order Type</b></td>									
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Copc Approved Date</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Logical Si No</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Service Stage</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Order Stage</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Account Mgr</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Project Mgr</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Customer Service Rfs Date</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Po Receive Date</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Po Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Po Date</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Loc Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Address</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Si Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cancel By</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cancel Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cancel Reason</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Region</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bandwidth</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Vertical</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Location From</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Location To</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Coll Manager</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Coll Mgr Mail</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Coll Mgr Ph</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Bandwidth</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Demo Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Crm Order Id</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Order Line Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Service No</b></td>																				
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Contract Period Mnths</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Commitment Period</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Notice Period</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Period In Month</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Tot Po Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Party Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Account Id</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>M6 Order No</b></td>					
					<!--  Meenakshi : change for usage -->	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Package ID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Package Name</b></td>								
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component InfoID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component ID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Start Logic</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Start Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component End Logic</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component End Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Start Date Days</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Start Date Months</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>End Date Days</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>End Date Months</b></td>						
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Cyclic/Non-Cyclic Amount</b></td>							
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component FX Instance Id</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Start Token No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Start FX Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>End Token No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>End FX Status</b></td>						
					<!-- Meenakshi : End -->																	
		</tr>
		</logic:equal>
		
		<logic:empty name="startChargeNotPushedInFxExcelList">
			<tr>
				<td colspan="14" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="startChargeNotPushedInFxExcelList" scope="request"> 					
				<logic:iterate id="row" name="startChargeNotPushedInFxExcelList" indexId="recordId">
					<logic:equal name="isUsage" value="0">
					<tr >
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalSINumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceName"/></td>	
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceDetDescription"/></td>															
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeTypeName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="frequencyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dnd_Dispatch_But_Not_Delivered"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dnd_Dispatch_And_Delivered"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dnd_Disp_Del_Not_Installed"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dnd_Disp_Delivered_Installed"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDateLogic"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endDateLogic"/></td>								
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endDate"/></td>								
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractStartDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountNoString"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="creditPeriodName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="entity"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingMode"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTypeName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingFormatName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lcompanyname"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taxation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dispatchAddressName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="penaltyClause"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="store"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="hardwaretypeName"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="formAvailable"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="saleNature"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="saleType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primaryLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="secondaryLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ponum"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="party_no"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAnnotation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fxStatus"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_St_Chg_Status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_Ed_Chg_Status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="tokenNO"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_Date"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="locDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTriggerDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="challenno"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="challendate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_external_acc_id"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="childAccountFXSataus"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="warrantyStartDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="warrantyEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="extSuportEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
							<!--<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td> Order Type Id -->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Service Order Type -->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Service Order Type Desc -->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>	
							<td align="left" style="font-size:9px" width="5%"></td><!-- Bill Trg Create Date -->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Cust Logical SI NO -->														
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="hardwareFlag"/></td>
							<!--<td align="left" style="font-size:9px" width="5%"></td>Charge Type Id-->							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceStage"/></td>
							<td align="left" style="font-size:9px" width="5%"></td><!--Order Stage-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountManager"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="projectManager"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td><!-- order creation Date -->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="rfsDate"/></td><!--Customer Service Rfs Date-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poRecieveDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="tokenNoEd"/></td><!--Token No Ed-->
							<!--<td align="left" style="font-size:9px" width="5%"></td>Fx Status Ed-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDetailNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>																						
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="charge_status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_No"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingAddress"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fxSiId"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cancelBy"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="canceldate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cancelReason"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="opms_Account_Id"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="toLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fromLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="coll_Manager"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="coll_Manager_Mail"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="coll_Manager_Phone"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_bandwidth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="order_type"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>						
							<!--<td align="left" style="font-size:9px" width="5%"></td> Charge Hdr Id-->
							<!--<td align="left" style="font-size:9px" width="5%"></td> Order Line Id-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceId"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAmount_String"/></td><!--Inv Amt-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lineItemAmount"/></td><!--Line item Amt-->
							<td align="left" style="font-size:9px" width="5%" <bean:write  name="row" property="totalPoAmt"/></td><!--Total Amount-->
							<td align="left" style="font-size:9px" width="5%"></td><!--Installment Rate-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDateDays"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDateMonth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endDateDays"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endDateMonth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="annual_rate"/></td>
							<td align="left" style="font-size:9px" width="5%"></td><!-- Trai Rate -->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Discount -->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="commitmentPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="noticePeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"></td><!-- Principal Amt -->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Intrest Rate -->							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td><!-- Period in month -->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="totalPoAmt"/></td>
							<!--<td align="left" style="font-size:9px" width="5%">Party Id -->
							<!--<td align="left" style="font-size:9px" width="5%">Cust Account Id-->
							<!--<td align="left" style="font-size:9px" width="5%"></td>M6 Product Id-->
							<!--<td align="left" style="font-size:9px" width="5%"></td>M6 Order Id-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td>--Ib Order Line Id-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td><!--Ib Service List Id-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td><!--Ib Pk Charges Id-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td><!--Ib Pk Charges Id-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td><!--Fx Sno-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td><!--Fx Sno Ed-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poAmount"/></td><!--Cust Tot Po Amt-->
							<td align="left" style="font-size:9px" width="5%"></td><!--M6 Order No-->
							<td align="left" style="font-size:9px" width="5%"></td><!--Business Serial No-->
							<td align="left" style="font-size:9px" width="5%"></td><!--Bus Serial-->
							<td align="left" style="font-size:9px" width="5%"></td><!--Advance-->
					</tr>
					</logic:equal>
					
					<logic:equal name="isUsage" value="1">
					<tr >
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalSINumber"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceName"/></td>	
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceDetDescription"/></td>																																						
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractStartDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractEndDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountNoString"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="creditPeriodName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="entity"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingMode"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTypeName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingFormatName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lcompanyname"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taxation"/></td>								
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="penaltyClause"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelNo"/></td>								
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primaryLocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="secondaryLocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ponum"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poDate"/></td>								
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>							
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="party_no"/></td>	
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_Date"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="locDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTriggerDate"/></td>								
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_external_acc_id"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_internal_acc_id"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="childAccountFXSataus"/></td>																
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>							
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>								
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceOrderType"/></td>							
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>									
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customer_logicalSINumber"/></td>																																		
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceStage"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderStage"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountManager"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="projectManager"/></td>								
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="rfsDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poRecieveDate"/></td>								
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDetailNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>																						
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_No"/></td>							
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingAddress"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fxSiId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cancelBy"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="canceldate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cancelServiceReason"/></td>								
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="toLocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fromLocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="coll_Manager"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="coll_Manager_Mail"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="coll_Manager_Phone"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_bandwidth"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="order_type"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>						
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderLineNumber"/></td><!-- Order Line Id-->
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceId"/></td>																								
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>							
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="commitmentPeriod"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="noticePeriod"/></td>															
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td><!-- Period in month -->
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="totalPoAmt"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="sourcePartyID"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountID"/></td>																								
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6OrderNo"/></td><!--M6 Order No-->								
								<!--  Meenaskhi : changes for Usage -->				
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageID"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageName"/></td>				
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentInfoID"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentID"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentStatus"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startLogic"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endLogic"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.end_date"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="startDateDays"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="startDateMonth"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="endDateDays"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="endDateMonth"/></td>																
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentType"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentAmount"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentInstanceID"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxTokenNo"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxStartStatus"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endTokenNo"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endFxStatus"/></td>


								<!--  Meenakshi : End -->
					
					</tr>
					</logic:equal>
					
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

<logic:present name="pendingOrderAndBillingExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=PendingOrderAndBillingRport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<logic:equal name="isUsage" value="0">
				<td colspan="16" align="left" style="font-size:10px"><strong>PENDING ORDER AND BILLING REPORT</strong></td>
			</logic:equal>
			<logic:equal name="isUsage" value="1">
				<td colspan="16" align="left" style="font-size:10px"><strong>PENDING ORDER AND BILLING REPORT (USAGE)</strong></td>
			</logic:equal>
		</tr>
		<tr align="Center">
			<logic:equal name="isUsage" value="0">
					<tr>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CRM Act No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Child Act No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Segment </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>A/C Category </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CRM Account Id  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CRM Product Name  </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical Circuit Id  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Company Name  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Factory Ckt Id  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer PODate  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b> Customer PO Number  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Name  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Licence Company Name  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Name  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cyclic Non Cyclic  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Store Address  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>M6 Order Id  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contract Period  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Currency  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Annual Rate  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Amount  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Desc  </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>A/C Manager Name  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Payment Term  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LOC Date  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PM Prov Date  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PO Create Date  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PO Number  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Prj Managre  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>COPC Approved Date  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Tax inc or exc  </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bandwidth  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>UOM  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Bandwidth</b></td>		
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Bandwidth UOM</b></td>								
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OSP </b></td>			
			</tr>
		</logic:equal>
		
		<logic:equal name="isUsage" value="1">
			<tr>					
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CRM Act No </b></td>
			    <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Child Act No </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region Name </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Segment </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>A/C Category </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CRM Account Id  </b></td>
			    <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical SI No  </b></td>				
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer PODate  </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b> Customer PO Number  </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Name  </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Licence Company Name  </b></td>					
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>M6 Order Id  </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contract Period  </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Currency  </b></td>					
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Desc  </b></td>
			    <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>A/C Manager Name  </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type  </b></td>					
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LOC Date  </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PM Prov Date  </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PO Create Date  </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PO Number  </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Prj Manager  </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>COPC Approved Date  </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Tax inc or exc  </b></td>	
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bandwidth  </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>UOM  </b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Bandwidth</b></td>		
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Bandwidth UOM</b></td>								
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical </b></td>
				<!--  Meenakshi : change for usage -->						
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Package ID</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Package Name</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component ID</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Name</b></td>						
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ComponentInfo ID</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Type</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cyclic/Non-Cyclic component Amount</b></td>				
				<!-- Meenakshi : End -->										
			</tr>
		</logic:equal>
		
		<logic:empty name="pendingOrderAndBillingExcelList">
			<tr>
				<td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="pendingOrderAndBillingExcelList" scope="request"> 					
				<logic:iterate id="row" name="pendingOrderAndBillingExcelList" indexId="recordId">
					<logic:equal name="isUsage" value="0">
					<tr >
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="crmAccountNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="child_act_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="regionName"/></td>
                        <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cus_segment"/></td>
                        <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="act_category"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="crm_productname"/></td>
				        <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="logicalSINo"/></td>
				        <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="companyName"/></td>
				        <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="factory_ckt_id"/></td>
					    <td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderNumber"/></td>
		                <td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custPoDate"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custPoDetailNo"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="linename"/></td>
					    <td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="lcompanyname"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="storename"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6OrderNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="contractPeriod"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="currencyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="annual_rate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeAmount"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="line_desc"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="actmngname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderType"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="frequencyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="LOC_Date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="pm_pro_date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="prjmngname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="copcApproveDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="taxation"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="bandwaidth"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="uom"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="billing_bandwidth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="billing_uom"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="verticalDetails"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="osp"/></td>	
					</tr>
					</logic:equal>
					
					<logic:equal name="isUsage" value="1">
					<tr >
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="crmAccountNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="child_act_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="regionName"/></td>
                        <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cus_segment"/></td>
                        <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="act_category"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountID"/></td>
				        <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="logicalSINo"/></td>				        
					    <td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderNumber"/></td>
		                <td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custPoDate"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custPoDetailNo"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="linename"/></td>
					    <td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="lcompanyname"/></td>					    
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6OrderNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="contractPeriod"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="currencyName"/></td>						
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="line_desc"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="actmngname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderType"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="LOC_Date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="pm_pro_date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="prjmngname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="copcApproveDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="taxation"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="bandwaidth"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="uom"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="billing_bandwidth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="billing_uom"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="verticalDetails"/></td>
						<!--  Meenaskhi : changes for Usage -->																		
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageID"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageName"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentID"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentName"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentInfoID"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentType"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentRCNRCAmount"/></td>					
						<!--  Meenakshi : End -->	
					</tr>
					</logic:equal>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

<logic:present name="DisconnectionLineReportInExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=DISCONNECTIONLINEREPORT.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="16" align="left" style="font-size:10px"><strong>Disconnection Line Report</strong></td>
		</tr>
		<tr align="Center">
		<!--  Saurabh : change to show data for charges -->		
		<logic:equal name="isUsage" value="0">
						
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Party No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Account No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b> Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>M6 Order No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Service Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust Logical SI No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b> Line Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Sub Type </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Circuit Id </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bandwidth </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Bandwidth </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing UOM </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>KMS Distance </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>RATIO </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Location From </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Location To </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Pri Location </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Sec Location </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Product </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Sub Product </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Legal Entity </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Licence Company </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Currency </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Credit period </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Type </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Format </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Level </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Level No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Taxation </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Hardware Type </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>H/W Flag </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Store </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Type Of Sale </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Period </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Mode </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Po No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>PO Date </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Period in month </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Total PO Amt </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust PO Rec Date </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust PO No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust PO Date </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b> Cust PoTotal Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b> Charge Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Charge Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Charge Start Date </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Inv Amt </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Amt </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Annotation </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Loc Date </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Loc No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Trigger Date </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Trigger Create Date </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>PM Provisioning Date </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Trigger Flag </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Token No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Fx Charge Start Status </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Fx Status </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Business Serial No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Opms Act Id </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Line No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Serviceno </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Pre Crm Order Id </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Disconnection Remarks </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Stage </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Service Stage </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>New Order Remarks </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Copc Date </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Request Receive Date </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Standard Remarks </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Date </b></td>
		</logic:equal>		
		<!--  Saurabh : End -->
				<!--  Meenakshi : change for usage -->		
					<logic:equal name="isUsage" value="1">
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party No </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account No </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Name </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>M6 Order No </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Logical SI No </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Sub Type </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Circuit Id </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bandwidth </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Bandwidth </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing UOM </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>KMS Distance </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>RATIO </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Location From </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Location To </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pri Location </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Sec Location </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Product </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Sub Product </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Legal Entity </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Licence Company </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Currency </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Credit period </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Type </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Format </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Level </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Level No </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Taxation </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Period </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Mode </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po No </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PO Date </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Period in month </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Total PO Amt </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust PO Rec Date </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust PO No </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust PO Date </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Loc Date </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Loc No </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trigger Date </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PM Provisioning Date </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trigger Flag </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line No </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Serviceno </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pre Crm Order Id </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Disconnection Remarks </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Stage </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Stage </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>New Order Remarks </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Copc Date </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Request Receive Date </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Standard Remarks </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Date </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Child Account Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Child Account No Internal</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Child Account Fx Status</b></td>
						<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Package ID</b></td>
						<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Package Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component ID</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component InfoID</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>rental / nrc component amount</b></td>
						<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component Status</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Start Date</b></td>
						<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Start Token No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component FX Instance Id</b></td>
						<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Start FX Status</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component End Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component End Status</b></td>
				 </logic:equal>
			<!-- Meenakshi : End -->	

		</tr>

		<logic:empty name="DisconnectionLineReportInExcel">
			<tr>
				<td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="DisconnectionLineReportInExcel" scope="request"> 					
				<logic:iterate id="row" name="DisconnectionLineReportInExcel" indexId="recordId">
					<tr >
				<!--  Saurabh : change to show data for charges -->		
				<logic:equal name="isUsage" value="0">
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="partyNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cust_name"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="verticalDetails"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="regionName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="order_type"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6OrderNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="linename"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ordersubtype"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ckt_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bandwaidth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_bandwidth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_uom"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="distance"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ratio"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_from"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_to"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="primarylocation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="seclocation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="productName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="subProductName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="entity"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lcompanyname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="currencyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="creditPeriod"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingFormatName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevel"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevelNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="taxation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="hardwaretypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="hardware_flag"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="storename"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="saleType"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bill_period"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingMode"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="contractPeriod"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="totalAmountIncludingCurrent"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poReceiveDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDetailNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cust_total_poamt"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeEndDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeAmount"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineamt"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="annitation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="locDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="locno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_trigger_date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingtrigger_createdate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="pmapprovaldate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="tokenno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="fx_sd_status"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="fx_status"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="business_serial_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="opms_act_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceId"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="pre_crmorderid"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="disconnection_remarks"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="stageName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceStage"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="neworder_remarks"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="copcapprovaldate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="request_rec_date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="standard_reason"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderDate"/></td>
				</logic:equal>
				<!--  Saurabh : End -->	
					<!--  Meenaskhi : changes for Usage -->
						<logic:equal name="isUsage" value="1">
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="partyNo"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountID"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cust_name"/></td>
				    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="verticalDetails"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="regionName"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderNumber"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="order_type"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6OrderNo"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceName"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="linename"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ordersubtype"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ckt_id"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bandwaidth"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_bandwidth"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_uom"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="distance"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ratio"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_from"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_to"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="primarylocation"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="seclocation"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="productName"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="subProductName"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="entity"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lcompanyname"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="currencyName"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="creditPeriodName"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingTypeName"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingFormatName"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevel"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevelNo"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="taxation"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bill_period"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingMode"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poNumber"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poDate"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="contractPeriod"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="totalAmountIncludingCurrent"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poReceiveDate"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDetailNo"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDate"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="locDate"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="locno"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_trigger_date"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="pmapprovaldate"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineno"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceId"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="pre_crmorderid"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="disconnection_remarks"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="stageName"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceStage"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="neworder_remarks"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="copcapprovaldate"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="request_rec_date"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="standard_reason"/></td>
					<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderDate"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="fx_external_acc_id"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="fxInternalId"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="child_account_creation_status"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageID"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageName"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentID"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentName"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentInfoID"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentType"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentAmount"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentStatus"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxTokenNo"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentInstanceID"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxStartStatus"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.end_date"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endFxStatus"/></td>
						</logic:equal>


			<!--  Meenakshi : End -->	
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>
<logic:present name="CancelledFailedLineReportInExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=CANCELFAILEDLINEREPORT.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="16" align="left" style="font-size:10px"><strong>Cancel Failed Line Report</strong></td>
		</tr>
		<tr align="Center">
		<!--  Saurabh : change to show data for charges -->		
		<logic:equal name="isUsage" value="0"> 			
						
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Account No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Vertical </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Region </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b> Order No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b> Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Service Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust Logical SI No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b> Line Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Sub Type </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Circuit Id </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Bandwidth </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing UOM </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>KMS Distance </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Ratio </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Location From </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Location To </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Pri Location </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Sec Location </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Product </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Sub Product </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Legal Entity </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Licence Company </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Currency </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Credit period </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Type </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Format </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Level </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Level No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Taxation </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Hardware Type </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>H/W Flag </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Store </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Type Of Sale </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Period </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Mode </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Po No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>PO Date </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Period in month </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Total PO Amt </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust PO Rec Date </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust PO No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust PO Date </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b> Cust PoTotal Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b> Charge Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Charge Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Charge Start Date </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Inv Amt </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Amt </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Annotation </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Token No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Fx Charge Start Status </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Fx Status </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Business No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Opms Act Id </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Line No </b></td>
		</logic:equal>
		<!--  Saurabh : End -->	
					<!--  Meenakshi : change for usage -->		
					<logic:equal name="isUsage" value="1">
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party No </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Account No </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party Name </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Vertical </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Region </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Service Name </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust Logical SI No </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Line Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Sub Type </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Circuit Id </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Bandwidth </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing UOM </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>KMS Distance </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Ratio </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Location From </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Location To </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Pri Location </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Sec Location </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Product </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Sub Product </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Legal Entity </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Licence Company </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Currency </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Credit period </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Type </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Format </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Level </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Level No </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Taxation </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Period </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Mode </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Po No </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>PO Date </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Period in month </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Total PO Amt </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust PO Rec Date </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust PO No </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust PO Date </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Line No </b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Child Account Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Child Account No Internal</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Child Account Fx Status</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Package ID</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Package Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component ID</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component InfoID</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>rental / nrc component amount</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component Status</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component Start Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Start Token No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component FX Instance Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Start FX Status</b></td>
					</logic:equal>
					<!-- Meenakshi : End -->		


		</tr>

		<logic:empty name="CancelledFailedLineReportInExcel">
			<tr>
				<td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="CancelledFailedLineReportInExcel" scope="request"> 					
				<logic:iterate id="row" name="CancelledFailedLineReportInExcel" indexId="recordId">
					<tr >
					<!--  Saurabh : change to show data for charges -->		
					<logic:equal name="isUsage" value="0">
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="partyNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="partyName"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="verticalDetails"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="regionName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="order_type"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="linename"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ordersubtype"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ckt_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_bandwidth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_uom"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="distance"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ratio"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_from"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_to"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="primarylocation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="seclocation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="productName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="subProductName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="entity"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lcompanyname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="currencyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="creditPeriod"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingFormatName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevel"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevelNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="taxation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="hardwaretypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="hardware_flag"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="storename"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="saleType"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bill_period"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingMode"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="contractPeriod"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="totalAmountIncludingCurrent"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poReceiveDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDetailNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cust_total_poamt"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeEndDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeAmount"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineamt"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="annitation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="tokenno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="fx_sd_status"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="fx_status"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="business_serial_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="opms_act_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineno"/></td>
					</logic:equal>	
					<!--  Saurabh : End -->	
						<!--  Meenaskhi : changes for Usage -->
						<logic:equal name="isUsage" value="1">
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="partyNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="partyName"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="verticalDetails"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="regionName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="order_type"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="linename"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ordersubtype"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ckt_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_bandwidth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_uom"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="distance"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ratio"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_from"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_to"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="primarylocation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="seclocation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="productName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="subProductName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="entity"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lcompanyname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="currencyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="creditPeriodName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingFormatName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevel"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevelNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="taxation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bill_period"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingMode"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="contractPeriod"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="totalAmountIncludingCurrent"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poReceiveDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDetailNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineno"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="fx_external_acc_id"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="fxInternalId"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="child_account_creation_status"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageID"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageName"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentID"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentName"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentInfoID"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentType"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentAmount"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentStatus"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxTokenNo"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentInstanceID"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxStartStatus"/></td>
						</logic:equal>


								<!--  Meenakshi : End -->		
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>




<logic:present name="telemediaOrderExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=telemediaOrderReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="14" align="left" style="font-size:10px"><strong>TELEMEDIA ORDER REPORT</strong></td>
		</tr>
		<tr align="Center">					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Account Name</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Parent Account Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Child Account Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>COPC Approved Date</b></td>
							
		</tr>

		<logic:empty name="telemediaOrderExcelList">
			<tr>
				<td colspan="14" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="telemediaOrderExcelList" scope="request"> 					
				<logic:iterate id="row" name="telemediaOrderExcelList" indexId="recordId">
					<tr >						
						
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNo"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountNoString"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="child_act_no"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>
					
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

<logic:present name="MigAppNewOrderDetailsExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=MigratedApprovedNewOrderDetailReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<logic:equal name="isUsage" value="0">
				<td colspan="14" align="left" style="font-size:10px"><strong>MIGRATED APPROVED NEW ORDER DETAIL REPORT</strong></td>
			</logic:equal>
			<logic:equal name="isUsage" value="1">
				<td colspan="14" align="left" style="font-size:10px"><strong>MIGRATED APPROVED NEW ORDER DETAIL REPORT (USAGE)</strong></td>
			</logic:equal>
		</tr>
		<logic:equal name="isUsage" value="0">
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Month</b></td>						
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>M6 Order No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Circuit Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bandwidth</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billling Bandwidth</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Uom</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Kms Distance</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Ratio</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Location From</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Location To</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pri Location</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Sec Location</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Product</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Subproduct</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Legal Entity</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Licence Company</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Currency</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Credit Period</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Format</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Level</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Level Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Taxation</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Hardware Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Hardware Flag</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Store</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Type Of Sale</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Period</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Mode</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Number</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Period In Month</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Tot Po Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Receive Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Tot Po Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Start Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Inv Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Annotation</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Loc Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Loc Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trig Date</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Trg Create Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pm Prov Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trig Flag</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Token No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Fx Sd Chg Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Fx Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bl Source</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Business Serial No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Opms Account Id</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Lineitemnumber</b></td>
		</tr>
		</logic:equal>
		<logic:equal name="isUsage" value="1">
				<tr>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Month</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>M6 Order No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Line Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Service Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Circuit Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bandwidth</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billling Bandwidth</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bill Uom</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Kms Distance</b></td>						
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Location From</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Location To</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Pri Location</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Sec Location</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Product</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Subproduct</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Legal Entity</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Licence Company</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Currency</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Credit Period</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bill Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bill Format</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Level</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Level Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Taxation</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Mode</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Po Number</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Po Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Period In Month</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Tot Po Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Po Receive Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Po Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Po Date</b></td>													
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Loc Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Loc Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Trig Date</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bill Trg Create Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Pm Prov Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Trig Flag</b></td>									
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Lineitemnumber</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Child Account Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Child Internal Account No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Child Account Fx Status</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Package ID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Package Name</b></td>								
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component InfoID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component ID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Start Logic</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Start Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component End Logic</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component End Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Start Date Days</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Start Date Months</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>End Date Days</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>End Date Months</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Cyclic/Non-Cyclic Amount</b></td>							
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component FX Instance Id</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Start Token No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Start FX Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>End Token No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>End FX Status</b></td>
				</tr>
				</logic:equal>
		<logic:empty name="MigAppNewOrderDetailsExcelList">
			<tr>
				<td colspan="14" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
		<logic:notEmpty  name="MigAppNewOrderDetailsExcelList" scope="request"> 					
			<logic:iterate id="row" name="MigAppNewOrderDetailsExcelList" indexId="recordId">
			<logic:equal name="isUsage" value="0">
				<tr >
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="party_no"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountNoString"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ordermonth"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6OrderNo"/></td>	
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceName"/></td>	
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceDetDescription"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceOrderType"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalCircuitId"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_bandwidth"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billUom"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="kmsDistance"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ratio"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fromLocation"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="toLocation"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primaryLocation"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="secondaryLocation"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="productName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subProductName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="entity"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lcompanyname"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="creditPeriodName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTypeName"/></td><!-- Bill Type -->
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingFormatName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelNo"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taxation"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="hardwaretypeName"/></td>							
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="hardwareFlag"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="storename"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="saleType"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billPeriod"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingMode"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ponum"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poDate"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>
					<td align="left" style="font-size:9px" width="5%" <bean:write  name="row" property="totalPoAmt"/>></td><!--Total Amount-->
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poRecieveDate"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDetailNo"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="totalPoAmt"/></td><!--Cust Tot Po Amt-->
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeTypeName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDate"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAmount_String"/></td><!--Inv Amt-->
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lineItemAmount"/></td><!-- Amt-->
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAnnotation"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="locDate"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_No"/></td>							
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTriggerDate"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingtrigger_createdate"/></td><!-- Bill Trg Create Date -->							
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmApproveDate"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="tokenNO"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_St_Chg_Status"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fxStatus"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="blSource"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="business_serial_no"/></td><!-- Business Serial No	-->
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="opms_Account_Id"/></td><!-- Opms Account Id -->	
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderLineNumber"/></td><!-- Order Line Id-->
				</tr>
				</logic:equal>
				<logic:equal name="isUsage" value="1">
					<tr>							
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="party_no"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountNoString"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ordermonth"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6OrderNo"/></td>	
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceName"/></td>	
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceDetDescription"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceOrderType"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalCircuitId"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_bandwidth"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billUom"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="kmsDistance"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fromLocation"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="toLocation"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primaryLocation"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="secondaryLocation"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="productName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subProductName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="entity"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lcompanyname"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="creditPeriodName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTypeName"/></td><!-- Bill Type -->
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingFormatName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelName"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelNo"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taxation"/></td>							
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingMode"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ponum"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poDate"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="totalPoAmt"/></td><!--Total Amount-->
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poRecieveDate"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDetailNo"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>							
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="locDate"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_No"/></td>							
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTriggerDate"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingtrigger_createdate"/></td><!-- Bill Trg Create Date -->							
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmApproveDate"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>							
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderLineNumber"/></td><!-- Order Line Id-->
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_external_acc_id"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_internal_acc_id"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="childAccountFXSataus"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="packageID"/></td>
					<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="packageName"/></td>				
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentInfoID"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentID"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentName"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentStatus"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startLogic"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startDate"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endLogic"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.end_date"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="startDateDays"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="startDateMonth"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="endDateDays"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="endDateMonth"/></td>															
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentType"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentAmount"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentInstanceID"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxTokenNo"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxStartStatus"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endTokenNo"/></td>
					<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endFxStatus"/></td>								
				</tr>
			</logic:equal>
			</logic:iterate>		
		</logic:notEmpty>
	</table>
</logic:present>

<logic:present name="performanceExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=performanceReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="14" align="left" style="font-size:10px"><strong>PERFORMANCE REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Account No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Party Account Name</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Account Id</b></td>					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Region</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Vertical</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Zone</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Account Mgr</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Po Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Po Receive Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order No  </b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Cretion Date</b></td>					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>AM Approved Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Project manager</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>PM Approval Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>COPC Approval Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Type</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Demo Order</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Approval Status</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>po_recpt_delay</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>po_logging_delay</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>order_completion_date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>appr_delay_in_region</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>days_in_copc</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>days_for_order</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Value</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Product Name</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Customer Segment</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>OSP</b></td>
						
												
					
					
		</tr>

		<logic:empty name="performanceExcelList">
			<tr>
				<td colspan="14" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="performanceExcelList" scope="request"> 					
				<logic:iterate id="row" name="performanceExcelList" indexId="recordId">
					<tr >
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountNoString"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountId"/></td>														
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
						<td align="left" style="font-size:9px" width="10%"><bean:write name="row" property="verticalDetails"/></td>																
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="zoneName"/></td>								
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountManager"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poRecieveDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="amApproveDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="projectManager"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmApproveDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="order_type"/></td>	
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderStatus"/></td>	
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="po_recpt_delay"/></td>	
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="po_logging_delay"/></td>	
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="order_completion_date"/></td>	
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="appr_delay_in_region"/></td>	
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="days_in_copc"/></td>	
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="days_for_order"/></td>	
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poAmountSum"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="productName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cus_segment"/></td>	
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="osp"/></td>													
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

<logic:present name="orderReportChangeListExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=OrderDetailsChangeReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="17" align="left" style="font-size:10px"><strong>ORDER DETAIL CHANGE REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account ID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Stage</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Product ID </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>License Company</b></td>			
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contract Period</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No.</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Currency</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LOC Date </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>COPC Approval Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Amount</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical SI No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Frequency of Payment</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service ID </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>AM Receive Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OSP</b></td>
		</tr>

		<logic:empty name="orderReportChangeListExcel">
			<tr>
				<td colspan="17" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="orderReportChangeListExcel" scope="request"> 					
				<logic:iterate id="row" name="orderReportChangeListExcel" indexId="recordId">
					<tr >
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountID"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceproductid"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceSubTypeName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="licCompanyName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyCode"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_Date"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poAmount"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalSINo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="frequencyName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="row" property="amApproveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="osp"/></td>
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

<logic:present name="orderReportDetailListExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=OrderDetailsReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="22" align="left" style="font-size:10px"><strong>ORDER DETAIL REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px"><b>Account ID</b></td>
					<td align="center" style="font-size:9px"><b>Account Manager</b></td>
					<td align="center" style="font-size:9px"><b>Account Number</b></td>		
					<td align="center" style="font-size:9px"><b>Po Amount</b></td>
					<td align="center" style="font-size:9px"><b>Customer Segment</b></td>
					<td align="center" style="font-size:9px"><b>Account Category</b></td>	
					<td align="center" style="font-size:9px"><b>Vertical</b></td>		
					<td align="center" style="font-size:9px"><b>Billing Charge Start Date</b></td>
					<td align="center" style="font-size:9px"><b>Service Name</b></td>
					<td align="center" style="font-size:9px"><b>Order Line No</b></td>
				 	<td align="center" style="font-size:9px"><b>Line Name</b></td>  
					<td align="center" style="font-size:9px"><b>Cancelflag</b></td>
					<td align="center" style="font-size:9px"><b>Provision Bandwidth</b></td>
					<td align="center" style="font-size:9px"><b>Uom</b></td>		
					<td align="center" style="font-size:9px"><b>Billing Bandwidth</b></td>
					<td align="center" style="font-size:9px"><b>Store Address</b></td>
					<td align="center" style="font-size:9px"><b>Bill Uom</b></td>	
					<td align="center" style="font-size:9px"><b>Category Of Order</b></td>		
					<td align="center" style="font-size:9px"><b>Contract Period</b></td>
					<td align="center" style="font-size:9px"><b>Companyname</b></td>
					<td align="center" style="font-size:9px"><b>Order Creation Date</b></td>
					<td align="center" style="font-size:9px"><b>Customer Rfs Date</b></td>
					<td align="center" style="font-size:9px"><b>Customer Service Rfs Date</b></td>
					<td align="center" style="font-size:9px"><b>Currency</b></td>
					<td align="center" style="font-size:9px"><b>Charge Name</b></td>		
					<td align="center" style="font-size:9px"><b>Customer Po Date</b></td>
					<td align="center" style="font-size:9px"><b>Customer Po Number</b></td>
					<td align="center" style="font-size:9px"><b>Cyclic/Non-Cyclic</b></td>	
					<td align="center" style="font-size:9px"><b>Challen No</b></td>		
					<td align="center" style="font-size:9px"><b>Order No</b></td>
					<td align="center" style="font-size:9px"><b>From Site</b></td>
					<td align="center" style="font-size:9px"><b>To Site</b></td>
			  	    <td align="center" style="font-size:9px"><b>Item Quantity</b></td>  
					<td align="center" style="font-size:9px"><b>Kms Distance</b></td>
					<td align="center" style="font-size:9px"><b>Line Item Amount</b></td>
					<td align="center" style="font-size:9px"><b>Copc Approved Date</b></td>
					<td align="center" style="font-size:9px"><b>Line Item Description</b></td>		
					<td align="center" style="font-size:9px"><b>Loc Date</b></td>	
					<td align="center" style="font-size:9px"><b>Account Manager Receive Date</b></td>
					<td align="center" style="font-size:9px"><b>Order Total</b></td>
					<td align="center" style="font-size:9px"><b>Order Entry Date</b></td>
					<td align="center" style="font-size:9px"><b>Taxation</b></td>
					<td align="center" style="font-size:9px"><b>Licence Company</b></td>
					<td align="center" style="font-size:9px"><b>Logical Circuit Id</b></td>		
					<td align="center" style="font-size:9px"><b>Order Type</b></td>
					<td align="center" style="font-size:9px"><b>Payment Term</b></td>
					<td align="center" style="font-size:9px"><b>Project Mgr</b></td>	
					<td align="center" style="font-size:9px"><b>Region Name</b></td>
					<td align="center" style="font-size:9px"><b>Old Line item Amount</b></td>		
					<td align="center" style="font-size:9px"><b>Demo Type</b></td>
					<td align="center" style="font-size:9px"><b>Party Name</b></td>
					<td align="center" style="font-size:9px"><b>Order Stage Description</b></td>
					<td align="center" style="font-size:9px"><b>Service Stage Description</b></td>
					<td align="center" style="font-size:9px"><b>Charge End Date</b></td>
					<td align="center" style="font-size:9px"><b>End Date Logic</b></td>		
					<td align="center" style="font-size:9px"><b>New Order Remark</b></td>	
					<td align="center" style="font-size:9px"><b>Remarks</b></td>
					<td align="center" style="font-size:9px"><b>Service Order Type</b></td>
					<td align="center" style="font-size:9px"><b>OSP</b></td>
		</tr>

		<logic:empty name="orderReportDetailListExcel">
			<tr>
				<td colspan="22" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="orderReportDetailListExcel" scope="request"> 					
				<logic:iterate id="row" name="orderReportDetailListExcel" indexId="recordId">
					<tr >
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountID"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountManager"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmACcountNO"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poAmounts"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cus_segment"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="act_category"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingPODate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceDetDescription"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderLineNumber"/></td>
    							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="linename"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cancelflag"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="provisionBandWidth"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="uom"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="billingBandWidth"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="storeID"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billUom"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="categoryOfOrder"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="companyName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customerRfsDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customerServiceRfsDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyCode"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customerPoDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customerPoNumber"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cyclicNonCyclic"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="challenno"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fromSite"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="toSite"/></td>
	 							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="itemQuantity"/></td> 
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="kmsDistance"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAmount"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lineItemDescription"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="locDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="amReceiveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderTotal"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderEntryDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taxation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="licenceCoName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalCircuitNumber"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="orderType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="paymentTerm"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="projectManager"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
					 	  		<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="oldLineitemAmount"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="demoType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderStageDescription"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceStageDescription"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeEndDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endDateLogic"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="newOrderRemark"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="remarks"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceOrderType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="osp"/></td>
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>


<logic:present name="orderStageExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=OrderStageReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="16" align="left" style="font-size:10px"><strong>ORDER STAGE REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order No.</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Service ID</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Party Name</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Account Name</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Account Manager</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Creation Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>AM Approved Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>AM Approved By</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Project Manager</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>PM Approved Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>PM Approved By</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>COPC Approved Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>COPC Approved By</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Type  </b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Sevice Type</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>M6 Order No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>M6 Order Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Provision</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Billing Status</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Publish Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Eff Start Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Status</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Service Stage</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Party Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Circuit Status</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Region Name</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Zone Name</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Standard Reason</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Vertical</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Parent Order Subtype</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Logical Service Id</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>OSP</b></td>
		</tr>

		<logic:empty name="orderStageExcelList">
			<tr>
				<td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="orderStageExcelList" scope="request"> 					
				<logic:iterate id="row" name="orderStageExcelList" indexId="recordId">
					<tr >
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceId"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountManager"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="amApproveDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="amName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="projectManager"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmApproveDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceDetDescription"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6OrderNumber"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6OrderDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderProvision"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingStatus"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="publishedDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="effStartDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderStatus"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceStage"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyNo"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="circuitStatus"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="zoneName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="standardReason"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="parentOrderSubType"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custLogicalSI"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="osp"/></td>
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>


<logic:present name="performanceSummaryReportExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=performanceSummaryReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="14" align="left" style="font-size:10px"><strong>PERFORMANCE SUMMARY REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Account No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Party No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Party Name</b></td>					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Custsegment</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Indsegment</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Region</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Zone</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Account Manager</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Project Manager</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Demo Type</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Type</b></td>					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Sub Type</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Status</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Approved Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Publish Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Value</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Days IN AM</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Days IN PM</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Days IN COPC</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Days IN SED</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Total Days</b></td>	
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>OSP</b></td>					
						
												
					
					
		</tr>

		<logic:empty name="performanceSummaryReportExcelList">
			<tr>
				<td colspan="14" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="performanceSummaryReportExcelList" scope="request"> 					
				<logic:iterate id="row" name="performanceSummaryReportExcelList" indexId="recordId">
					<tr >
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountNoString"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="party_no"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cus_segment"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="industrySegment"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="zoneName"/></td>								
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountManager"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="projectManager"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="order_type"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subChangeTypeName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderStage"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="publishedDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poAmountSum"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dayInAM"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dayInPM"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dayInCOPC"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dayInSED"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="totalDays"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="osp"/></td>
					
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

<!-- PERFORMANCE DETAIL REPORT -->

<logic:present name="performanceDetailExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=performanceDetailReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="14" align="left" style="font-size:10px"><strong>PERFORMANCE DETAIL REPORT</strong></td>
		</tr>
		<tr>
					<td align="center" style="font-size:9px"><b>Party Number</b></td>
					<td align="center" style="font-size:9px"><b>Account No</b></td>
					<td align="center" style="font-size:9px"><b>Party Name</b></td>					
					<td align="center" style="font-size:9px"><b>Custsegment</b></td>
					<td align="center" style="font-size:9px"><b>Indsegment</b></td>
					<td align="center" style="font-size:9px"><b>Region</b></td>
					<td align="center" style="font-size:9px"><b>Zone</b></td>
					<td align="center" style="font-size:9px"><b>Account Mgr</b></td>
					<td align="center" style="font-size:9px"><b>Project Mgr</b></td>
					<td align="center" style="font-size:9px"><b>Order Type</b></td>					
					<td align="center" style="font-size:9px" ><b>Demo Type</b></td>
					<td align="center" style="font-size:9px" ><b>Order Subtype</b></td>
					<td align="center" style="font-size:9px" ><b>Order Status</b></td>
					<td align="center" style="font-size:9px" ><b>Orderdate</b></td>
					<td align="center" style="font-size:9px" ><b>Order Approved Date</b></td>
					<td align="center" style="font-size:9px" ><b>Publish Date</b></td>
					<td align="center" style="font-size:9px" ><b>Task Name</b></td>
					<td align="center" style="font-size:9px" ><b>Actual Start Date</b></td>
					<td align="center" style="font-size:9px" ><b>Actual End Date</b></td>
					<td align="center" style="font-size:9px" ><b>Task Number</b></td>
					<td align="center" style="font-size:9px" ><b>Owner</b></td>
					<td align="center" style="font-size:9px" ><b>User Name</b></td>
					<td align="center" style="font-size:9px" ><b>Owner Phone</b></td>
					<td align="center" style="font-size:9px" ><b>Owner Emailid</b></td>
					<td align="center" style="font-size:9px" ><b>Order No</b></td>
					<td align="center" style="font-size:9px" ><b>Total Days</b></td>
					<td align="center" style="font-size:9px" ><b>Order Value</b></td>
					<td align="center" style="font-size:9px" ><b>Outcome</b></td>
					<td align="center" style="font-size:9px" ><b>Approval Remark</b></td>
					<td align="center" style="font-size:9px" ><b>Vertical</b></td>
					<td align="center" style="font-size:9px" ><b>OSP</b></td>
					
				</tr>

		<logic:empty name="performanceDetailExcelList">
			<tr>
				<td colspan="14" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="performanceDetailExcelList" scope="request"> 					
				<logic:iterate id="row" name="performanceDetailExcelList" indexId="recordId">
					<tr>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmACcountNO"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>														
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cus_segment"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="industrySegment"/></td>																
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>								
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="zoneName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountManager"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="projectManager"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="order_type"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ordersubtype"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderStatus"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderApproveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="publishedDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taskName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="actualStartDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="actualEndDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taskNumber"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="owner"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="userName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accphoneNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="emailId"/></td>	
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="totalDays"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderTotal"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="outCome"/></td>	
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="remarks"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="vertical"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="osp"/></td>
							</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>


<logic:present name="orderActiveLineItemsList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=ActiveLineItemsReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="46" align="left" style="font-size:10px"><strong>ACTIVE LINE ITEMS REPORT</strong></td>
		</tr>
		<tr align="Center">
			<!--  Saurabh : change for Usage to show charge data-->	
			<logic:equal name="isUsage" value="0">
<td align="center" style="font-size:9px" bgcolor="#FF9255">Logical Circuit Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Logical Si Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Service Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Line Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Charge Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Charge Type Code</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Charge Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Frequency</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Bill Period</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Charge Start Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Account Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Credit Period</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Currency</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Legal Entity</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Billing Mode</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Bill Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Bill Format</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Licence Company</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Taxation</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Billing Level</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Billing Level Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Store</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Hardware Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Form C Available</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Nature Of Sale</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Type Of Sale</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Pri Location</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Sec Location</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Po Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Po Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Party Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Annotation</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Fx Status</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Fx Sd Chg Status</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Fx Ed Chg Status</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Token No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Last Update Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Billing Trig Flag</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Pm Prov Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Loc Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Billing Trig Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Challen No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Challen Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Child Account Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Child Account Fx Status</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Orderdate</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Approved Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Order Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Copc Approved Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Bill Trg Create Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Cust Logical Si No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Kms Distance</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Ratio</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Product</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Subproduct</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Hardware Flag</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Charge Type Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Service Stage</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Order Stage</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Order Creation Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Customer Service Rfs Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Cust Po Receive Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Cust Po Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Cust Po Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Charge Status</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Loc Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Address</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Circuit Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Cancel By</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Cancel Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Cancel Reason</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Sub Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Opms Account Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Region</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Bandwidth</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Vertical</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Mocn Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Project Mgr</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Account Manager</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Rate Code</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Last Mile Media</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Last Mile Remarks</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Chargable Distance</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Last Mile Provider</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Link Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Dispatch Address</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Indicative Value</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Product Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Party Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Billing Location From</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Demo Order</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Crm Product Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Billing Location To</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Location From</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Location To</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Billling Bandwidth</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Bill Uom</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Bl Source</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Lineitemnumber</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Order Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Inv Amt</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Amt</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Total Amount</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Period In Month</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Tot Po Amt</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Party Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Cust Account Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">M6 Product Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">M6 Order Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Cust Tot Po Amt</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">M6 Order No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Business Serial No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Order Line Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Ib Service List Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Ib Pk Charges Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Pk Charges Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Contract Period Mnths</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Annual Rate</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255">Service Number</b></td>
			</logic:equal>
			<!--  Meenakshi : change for usage -->		
			<logic:equal name="isUsage" value="1">
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical Circuit Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Logical Si Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Credit Period</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Currency</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Legal Entity</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Mode</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Format</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Licence Company</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Taxation</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Level</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Level Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trig Flag</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pm Prov Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Loc Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trig Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Child Account Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Child Account Fx Status</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Child Account FX Internal No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Orderdate</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Approved Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Copc Approved Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Trg Create Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Product</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Subproduct</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Stage</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Stage</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Creation Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Service Rfs Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Receive Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Circuit Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Sub Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Opms Account Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bandwidth</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Project Mgr</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Manager</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Product Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Location From</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo Order</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Location To</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Location From</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Location To</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billling Bandwidth</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Uom</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Minimum Bandwith</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Minimum Bandwith UOM</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Lineitemnumber</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Period In Month</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Tot Po Amt</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Account Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>M6 Order No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Line Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Package ID</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Package Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component ID</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component InfoID</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>rental / nrc component amount</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Status</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Start Logic</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Start Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component End Logic</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component End Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Start Token No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component FX Instance Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Start FX Status</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>End FX Status</b></td>
			</logic:equal>
			</tr>

		<logic:empty name="orderActiveLineItemsList">
			<tr>
				<td colspan="46" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="orderActiveLineItemsList" scope="request"> 					
				<logic:iterate id="row" name="orderActiveLineItemsList" indexId="recordId">
					<tr >
				<!--  Saurabh : change for Usage to show charge data-->	
				<logic:equal name="isUsage" value="0">
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="logicalSINo"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custSINo"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="linename"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeTypeName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeTypeID"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeFrequency"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billPeriod"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="startDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="accountID"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="creditPeriodName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="currencyName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="entity"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingMode"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingTypeName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingformat"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="licCompanyName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="taxation"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingLevelName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingLevelNo"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="store"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="hardwaretypeName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="formAvailable"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="saleNature"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="saleTypeName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="primaryLocation"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="seclocation"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="poNumber"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="poDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="party_num"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeAnnotation"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="fx_charge_status"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="fx_sd_charge_status"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="fx_Ed_Chg_Status"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="tokenID"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="modifiedDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingTriggerFlag"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="pm_pro_date"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="locDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billing_trigger_date"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="challenno"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="challendate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="fx_external_acc_id"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="child_account_creation_status"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="copcapprovaldate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderType"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="copcapprovaldate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingtrigger_createdate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custSINo"/></td>
								<td align="left" style="font-size:9px" width="5%" ></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="ratio"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="productName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="subProductName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="hardwareType"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeTypeID"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceStage"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderStage"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="rfsDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="poReceiveDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custPoDetailNo"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custPoDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="charge_status"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="LOC_No"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="address1"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="m6cktid"/></td>
								<td align="left" style="font-size:9px" width="5%" ></td>
								<td align="left" style="font-size:9px" width="5%" ></td>
								<td align="left" style="font-size:9px" width="5%" ></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="ordersubtype"/></td>
								<td align="left" style="font-size:9px" width="5%" ></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="region"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="bandwaidth"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="vertical"/></td>
								<td align="left" style="font-size:9px" width="5%" ></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="projectManager"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="accountManager"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="rate_code"/></td>
								<td align="left" style="font-size:9px" width="5%" ></td>
								<td align="left" style="font-size:9px" width="5%" ></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeable_Distance"/></td>
								<td align="left" style="font-size:9px" width="5%" ></td>
								<td align="left" style="font-size:9px" width="5%" ></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchAddress1"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="indicative_value"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="productName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="partyName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billing_location_from"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="demo"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="crm_productname"/></td>
								<td align="left" style="font-size:9px" width="5%" ></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="fromLocation"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="toLocation"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billing_bandwidth"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billing_uom"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="blSource"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceproductid"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderNumber"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeAmount"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="lineamt"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargesSumOfLineitem"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractPeriod"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="totalPoAmt"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="party_id"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="crmAccountNoString"/></td>
								<td align="left" style="font-size:9px" width="5%" ></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="m6OrderNo"/></td>	
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="cust_total_poamt"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="m6OrderNo"/></td>
								<td align="left" style="font-size:9px" width="5%" ></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceproductid"/></td>
								<td align="left" style="font-size:9px" width="5%" ></td>
								<td align="left" style="font-size:9px" width="5%" ></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="pk_charge_id"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractPeriod"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="annual_rate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceId"/></td>
				
				</logic:equal>
				<!--  Meenakshi : change for usage -->		
				<logic:equal name="isUsage" value="1">
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="logicalSINo"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custSINo"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceName"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="linename"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="accountID"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="creditPeriodName"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="currencyName"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="entity"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingMode"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingTypeName"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingformat"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="licCompanyName"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="taxation"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingLevelName"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingLevelNo"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="poNumber"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="poDate"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="party_num"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingTriggerFlag"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="pm_pro_date"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="locDate"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billing_trigger_date"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="fx_external_acc_id"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="child_account_creation_status"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="fxInternalId"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderDate"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="copcapprovaldate"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderType"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="copcapprovaldate"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingtrigger_createdate"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="productName"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="subProductName"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceStage"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderStage"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderDate"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="rfsDate"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="poReceiveDate"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custPoDetailNo"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custPoDate"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="m6cktid"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="ordersubtype"/></td>
				<td align="left" style="font-size:9px" width="5%" ></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="region"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="bandwaidth"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="vertical"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="projectManager"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="accountManager"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="productName"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="partyName"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billing_location_from"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="demo"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billing_location_to"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="fromLocation"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="toLocation"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billing_bandwidth"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billing_uom"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="minimum_bandwidth"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="minimum_bandwidth_UOM"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceproductid"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderNumber"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractPeriod"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="totalPoAmt"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="party_id"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="crmAccountNoString"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="m6OrderNo"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceproductid"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceId"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageID"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageName"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentID"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentName"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentInfoID"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentType"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentAmount"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentStatus"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startLogic"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startDate"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endLogic"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.end_date"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxTokenNo"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentInstanceID"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxStartStatus"/></td>
				<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endFxStatus"/></td>
				</logic:equal>
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>
<logic:present name="billingTriggerDoneButFailedInFXExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=BillingTriggerDoneButFailedInFX.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<logic:equal name="isUsage" value="0">
				<td colspan="14" align="left" style="font-size:10px"><strong>BILLING TRIGGER DONE BUT FAILED IN FX</strong></td>
			</logic:equal>
			<logic:equal name="isUsage" value="1">
				<td colspan="14" align="left" style="font-size:10px"><strong>BILLING TRIGGER DONE BUT FAILED IN FX (USAGE)</strong></td>
			</logic:equal>
		</tr>
		<logic:equal name="isUsage" value="0">
			<tr>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical Circuit Id</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Type</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Frequency</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Period</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dnd Dispatch But Not Delivered</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Dnd Dispatch And Delivered</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Dnd Disp Del Not Installed</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Dnd Disp Delivered Installed</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Start Date Logic</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>End Date Logic</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Charge Start Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Charge End Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Contract Start Date</b></td>	
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Contract End Date</b></td>	
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Account Number</b></td>	
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Credit Period</b></td>										
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Currency</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Legal Entity</b></td>					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Billing Mode</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Bill Type</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Bill Format</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Licence Company</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Taxation</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Dispatch Address</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Penelty Clause</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Billing Level</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Billing Level Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Store</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Hardware Type</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Form C Available</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Nature Of Sale</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Type Of Sale</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Pri Location</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Sec Location</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Po Number</b></td>					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Po Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Party</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Party Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Annotation</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Fx Status</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Fx Sd Chg Status</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Fx Ed Chg Status</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Token No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Billing Trig Flag</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Pm Prov Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Loc Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Billing Trig Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Challen No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Challen Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Child Account Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Child Account Fx Status</b></td>					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Warranty Start Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Warranty End Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Extnd Support End Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Orderdate</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Approved Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Type</b></td>
					<!--<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Type Id</b></td>-->
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Service Order Type</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Service Order Type Desc</b></td>					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Copc Approved Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Bill Trg Create Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Logical Si No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Hardware Flag</b></td>
					<!--<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Charge Type Id</b></td> -->
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Service Stage</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Stage</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Account Mgr</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Project Mgr</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Creation Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Customer Service Rfs Date</b></td>					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Po Receive Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Token No Ed</b></td>
					<!--<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Fx Status Ed</b></td>-->
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Po Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Po Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Charge Status</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Loc Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Billing Address</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Si Id</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cancel By</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cancel Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cancel Reason</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Opms Account Id</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Region</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Bandwidth</b></td>					
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Vertical</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Location From</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Location To</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Coll Manager</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Coll Mgr Mail</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Coll Mgr Ph</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Billing Bandwidth</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Demo Type</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Crm Order Id</b></td>
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Number</b></td>-->
					<!--<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Charge Hdr Id</b></td>-->
					<!--<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Line Id</b></td>-->
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Service No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Inv Amt</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Amt</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Total Amount</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Installment Rate</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Start Date Days</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Start Date Months</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>End Date Days</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>End Date Months</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Annual Rate</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Trai Rate</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Discount</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Contract Period Mnths</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Commitment Period</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Notice Period</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Principal Amt</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Intrest Rate</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Period In Month</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Tot Po Amt</b></td>
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Party Id</b></td>-->
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Account Id</b></td>-->
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>M6 Product Id</b></td>-->
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>M6 Order Id</b></td>-->
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Ib Order Line Id</b></td>-->
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Ib Service List Id</b></td>-->
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Ib Pk Charges Id</b></td>-->
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Fx Sno</b></td>-->
					<!-- <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Fx Sno Ed</b></td>-->
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Cust Tot Po Amt</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>M6 Order No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Business Serial No</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Bus Serial</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Advance</b></td>
				</tr>
		</logic:equal>
					
		<logic:equal name="isUsage" value="1">
			<tr>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No</b></td>				
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Logical SI No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Service Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Line Name</b></td>															
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Contract Start Date</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Contract End Date</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Account Number</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Credit Period</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Currency</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Legal Entity</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Mode</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bill Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bill Format</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Licence Company</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Taxation</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Penelty Clause</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Level</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Level Number</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Pri Location</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Sec Location</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Po Number</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Po Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Party</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Party Number</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Trig Flag</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Pm Prov Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Loc Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Trig Date</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Child Account Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Child Account Internal No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Child Account Fx Status</b></td>															
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Orderdate</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Approved Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Order Type</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Service Order Type</b></td>				
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Copc Approved Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bill Trg Create Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Logical Si No</b></td>				
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Service Stage</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Order Stage</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Account Mgr</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Project Mgr</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Order Creation Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Customer Service Rfs Date</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Po Receive Date</b></td>				
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Po Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Po Date</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Loc Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Address</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Si Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cancel By</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cancel Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cancel Reason</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Region</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bandwidth</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Vertical</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Location From</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Location To</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Coll Manager</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Coll Mgr Mail</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Coll Mgr Ph</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Bandwidth</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Demo Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Crm Order Id</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Service No</b></td>																			
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Contract Period Mnths</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Commitment Period</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Notice Period</b></td>									
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Period In Month</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Tot Po Amt</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Tot Po Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>M6 Order No</b></td>					
					<!--  Manisha : change for usage -->															
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Package ID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Package Name</b></td>								
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component InfoID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component ID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Start Logic</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Start Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component End Logic</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component End Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Start Date Days</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Start Date Months</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>End Date Days</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>End Date Months</b></td>						
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Cyclic/Non-Cyclic Amount</b></td>							
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component FX Instance Id</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Start Token No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Start FX Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>End Token No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>End FX Status</b></td>														
					<!-- Manisha : End -->						
				</tr>
		</logic:equal>

		<logic:empty name="billingTriggerDoneButFailedInFXExcelList">
			<tr>
				<td colspan="14" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="billingTriggerDoneButFailedInFXExcelList" scope="request"> 					
				<logic:iterate id="row" name="billingTriggerDoneButFailedInFXExcelList" indexId="recordId">
				<logic:equal name="isUsage" value="0">
					<tr >
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalSINumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceName"/></td>	
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceDetDescription"/></td>															
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeTypeName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="frequencyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dnd_Dispatch_But_Not_Delivered"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dnd_Dispatch_And_Delivered"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dnd_Disp_Del_Not_Installed"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dnd_Disp_Delivered_Installed"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDateLogic"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endDateLogic"/></td>								
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endDate"/></td>								
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractStartDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountNoString"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="creditPeriodName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="entity"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingMode"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTypeName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingFormatName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lcompanyname"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taxation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dispatchAddressName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="penaltyClause"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="store"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="hardwaretypeName"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="formAvailable"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="saleNature"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="saleType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primaryLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="secondaryLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ponum"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="party_no"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAnnotation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fxStatus"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_St_Chg_Status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_Ed_Chg_Status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="tokenNO"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_Date"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="locDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTriggerDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="challenno"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="challendate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_external_acc_id"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="childAccountFXSataus"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="warrantyStartDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="warrantyEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="extSuportEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
							<!--<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td> Order Type Id -->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Service Order Type -->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Service Order Type Desc -->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>	
							<td align="left" style="font-size:9px" width="5%"></td><!-- Bill Trg Create Date -->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Cust Logical SI NO -->														
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="hardwareFlag"/></td>
							<!--<td align="left" style="font-size:9px" width="5%"></td>Charge Type Id-->							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceStage"/></td>
							<td align="left" style="font-size:9px" width="5%"></td><!--Order Stage-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountManager"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="projectManager"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td><!-- order creation Date -->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="rfsDate"/></td><!--Customer Service Rfs Date-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poRecieveDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="tokenNoEd"/></td><!--Token No Ed-->
							<!--<td align="left" style="font-size:9px" width="5%"></td>Fx Status Ed-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDetailNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>																						
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="charge_status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_No"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingAddress"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fxSiId"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cancelBy"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="canceldate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cancelReason"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="opms_Account_Id"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="toLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fromLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="coll_Manager"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="coll_Manager_Mail"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="coll_Manager_Phone"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_bandwidth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="order_type"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>						
							<!--<td align="left" style="font-size:9px" width="5%"></td> Charge Hdr Id-->
							<!--<td align="left" style="font-size:9px" width="5%"></td> Order Line Id-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceId"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAmount_String"/></td><!--Inv Amt-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lineItemAmount"/></td><!--Line item Amt-->
							<td align="left" style="font-size:9px" width="5%" <bean:write  name="row" property="totalPoAmt"/>></td><!--Total Amount-->
							<td align="left" style="font-size:9px" width="5%"></td><!--Installment Rate-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDateDays"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDateMonth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endDateDays"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endDateMonth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="annual_rate"/></td>
							<td align="left" style="font-size:9px" width="5%"></td><!-- Trai Rate -->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Discount -->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="commitmentPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="noticePeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"></td><!-- Principal Amt -->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Intrest Rate -->							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td><!-- Period in month -->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="totalPoAmt"/></td>
							<!--<td align="left" style="font-size:9px" width="5%">Party Id -->
							<!--<td align="left" style="font-size:9px" width="5%">Cust Account Id-->
							<!--<td align="left" style="font-size:9px" width="5%"></td>M6 Product Id-->
							<!--<td align="left" style="font-size:9px" width="5%"></td>M6 Order Id-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td>--Ib Order Line Id-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td><!--Ib Service List Id-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td><!--Ib Pk Charges Id-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td><!--Ib Pk Charges Id-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td><!--Fx Sno-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td><!--Fx Sno Ed-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poAmount"/></td><!--Cust Tot Po Amt-->
							<td align="left" style="font-size:9px" width="5%"></td><!--M6 Order No-->
							<td align="left" style="font-size:9px" width="5%"></td><!--Business Serial No-->
							<td align="left" style="font-size:9px" width="5%"></td><!--Bus Serial-->
							<td align="left" style="font-size:9px" width="5%"></td><!--Advance-->
					</tr>
				</logic:equal>
					
				<logic:equal name="isUsage" value="1">
					<tr>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>																	
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalSINumber"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customer_logicalSINumber"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceName"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceDetDescription"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractStartDate"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractEndDate"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountNoString"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="creditPeriodName"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyName"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="entity"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingMode"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTypeName"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingFormatName"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lcompanyname"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taxation"/></td>																		
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="penaltyClause"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelName"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelNo"/></td>																										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primaryLocation"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="secondaryLocation"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ponum"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poDate"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="party_no"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>																												
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_Date"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="locDate"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTriggerDate"/></td>																											
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_external_acc_id"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_internal_acc_id"/></td>	
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="childAccountFXSataus"/></td>																											
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceOrderType"/></td><!-- Service Order Type -->																			
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>									 	
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTriggerDate"/></td><!-- Bill Trg Create Date -->										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customer_logicalSINumber"/></td><!-- Cust Logical SI NO -->																		
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceStage"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderStage"/></td>									
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountManager"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="projectManager"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td><!-- order creation Date -->										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="rfsDate"/></td><!--Customer Service Rfs Date-->										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poRecieveDate"/></td>																			
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDetailNo"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_No"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingAddress"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fxSiId"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cancelBy"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="canceldate"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cancelServiceReason"/></td>																			
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="toLocation"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fromLocation"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="coll_Manager"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="coll_Manager_Mail"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="coll_Manager_Phone"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_bandwidth"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="order_type"/></td>										
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>																		
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceId"/></td>																																							
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>											
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="commitmentPeriod"/></td>											
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="noticePeriod"/></td>																														
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td><!-- Period in month -->											
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="totalPoAmt"/></td>											
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poAmount"/></td><!--Cust Tot Po Amt-->											
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6OrderNo"/></td><!--M6 Order No	-->																													
								<!--  Manisha : changes for Usage -->											
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageID"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageName"/></td>				
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentInfoID"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentID"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentStatus"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startLogic"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endLogic"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.end_date"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="startDateDays"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="startDateMonth"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="endDateDays"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="endDateMonth"/></td>																
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentType"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentAmount"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentInstanceID"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxTokenNo"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxStartStatus"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endTokenNo"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endFxStatus"/></td>											
								<!--  Manisha : End -->																											
							</tr>
				</logic:equal>
			</logic:iterate>		
		</logic:notEmpty>
	</table>
</logic:present>

<logic:present name="DisconnectChangeOrderReportInExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=DISCONNECTIONCHANGEORDERREPORT.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<logic:equal name="isUsage" value="0">
			<td colspan="16" align="left" style="font-size:10px"><strong>Disconnection Change Order Report</strong></td>
			</logic:equal>
			<logic:equal name="isUsage" value="1">
				<td colspan="16" align="left" style="font-size:10px"><strong>Disconnection Change Order Report (Usage)</strong></td>
			</logic:equal>
		</tr>
		<logic:equal name="isUsage" value="0">
		<tr>
				<td align="center" style="font-size:9px" ><b>Logical CKT ID </b></td>
					<td align="center" style="font-size:9px" ><b>Service Name </b></td>
					<td align="center" style="font-size:9px"><b>Line Name </b></td>
					<td align="center" style="font-size:9px"><b>Charge Type </b></td>
					<td align="center" style="font-size:9px"><b>Charge Name</b></td>
					<td align="center" style="font-size:9px"  ><b>Frequency</b></td>
					<td align="center" style="font-size:9px"  ><b>Bill Period </b></td>
					<td align="center" style="font-size:9px"  ><b>Start Date Logic </b></td>
					<td align="center" style="font-size:9px"  ><b>End Date Logic </b></td>
					<td align="center" style="font-size:9px"  ><b>Charge End Date</b></td>
					<td align="center" style="font-size:9px"  ><b>Charge Start Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Account No </b></td>
					<td align="center" style="font-size:9px"  ><b>Credit Period</b></td>
					<td align="center" style="font-size:9px"  ><b>Currency</b></td>
					<td align="center" style="font-size:9px"  ><b>Legal Entity </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Mode </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Trigger Flag </b></td>
					<td align="center" style="font-size:9px"  ><b>Bill Type </b></td>
					<td align="center" style="font-size:9px"  ><b>Bill Format </b></td>
					<td align="center" style="font-size:9px"  ><b>Licence Company </b></td>
					<td align="center" style="font-size:9px"  ><b>Taxation </b></td>
					<td align="center" style="font-size:9px"  ><b>Penalty Clause </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Level </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Level No</b></td>
					<td align="center" style="font-size:9px"  ><b>Store </b></td>
					<td align="center" style="font-size:9px"  ><b>Hardware Type</b></td>
					<td align="center" style="font-size:9px"  ><b>Form C Avalaible</b></td>
					<td align="center" style="font-size:9px"  ><b>Nature Of Sale </b></td>
					<td align="center" style="font-size:9px"  ><b>Type of Sale </b></td>
					<td align="center" style="font-size:9px"  ><b>PO No </b></td>
					<td align="center" style="font-size:9px"  ><b>PO Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Party </b></td>
					<td align="center" style="font-size:9px"  ><b>Annotation </b></td>
					<td align="center" style="font-size:9px"  ><b>Token NO </b></td>
					<td align="center" style="font-size:9px"  ><b>PM Prov Date</b></td>
					<td align="center" style="font-size:9px"  ><b>LOC Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Trigger Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Challen NO </b></td>
					<td align="center" style="font-size:9px"  ><b>Challen Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Child A/C No </b></td>
					<td align="center" style="font-size:9px"  ><b>Child A/C FX Status </b></td>
					<td align="center" style="font-size:9px"  ><b>Order Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Order Type </b></td>
					<td align="center" style="font-size:9px"  ><b>Service Order Type </b></td>
					<td align="center" style="font-size:9px"  ><b>COPC Approve Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Trigger Creation Date</b></td>
					<td align="center" style="font-size:9px"  ><b> Cust Logical SI No.</b></td>
					<td align="center" style="font-size:9px"  ><b>Hardware Flag </b></td>
					<td align="center" style="font-size:9px"  ><b>Charge Type ID </b></td>
					<td align="center" style="font-size:9px"  ><b>Service Stage </b></td>
					<td align="center" style="font-size:9px"  ><b>Order Stage </b></td>
					<td align="center" style="font-size:9px"  ><b>Account Mgr </b></td>
					<td align="center" style="font-size:9px"  ><b>Project Mgr </b></td>
					<td align="center" style="font-size:9px"  ><b>Order Creation Date </b></td>
					<td align="center" style="font-size:9px"  ><b>RFS Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Cust PO Rec Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Charge Status </b></td>
					<td align="center" style="font-size:9px"  ><b>Vertical </b></td>
					<td align="center" style="font-size:9px"  ><b>Region </b></td>
					<td align="center" style="font-size:9px"  ><b>Demo Type </b></td>
					<td align="center" style="font-size:9px"  ><b>New Order Remark</b></td>
					<td align="center" style="font-size:9px"  ><b>Order Stage Description </b></td>
					<td align="center" style="font-size:9px"  ><b>Mocn No.</b></td>
					<td align="center" style="font-size:9px"  ><b>Disconnection Remarks </b></td>
					<td align="center" style="font-size:9px"  ><b>Sr. No </b></td>
					<td align="center" style="font-size:9px"  ><b>Request Receive Date </b></td>
					<td align="center" style="font-size:9px"  ><b>LineItemNo </b></td>
					<td align="center" style="font-size:9px"  ><b>Order Months </b></td>
					<td align="center" style="font-size:9px"  ><b>Ckt Id </b></td>
					<td align="center" style="font-size:9px"  ><b>Standard Reason </b></td>
					<td align="center" style="font-size:9px"  ><b>Bandwidth </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Bandwidth </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Bandwidth UOM </b></td>
					<td align="center" style="font-size:9px"  ><b>Bandwidth Attribute </b></td>
					<td align="center" style="font-size:9px"  ><b>Bandwidth UOM </b></td>
					<td align="center" style="font-size:9px"  ><b> Rate Code</b></td>
					<td align="center" style="font-size:9px"  ><b>Lat Mile Media </b></td>
					<td align="center" style="font-size:9px"  ><b>Last Mile Remarks </b></td>
					<td align="center" style="font-size:9px"  ><b>Chargeable Distance</b></td>
					<td align="center" style="font-size:9px"  ><b>Last Mile Provider</b></td>
					<td align="center" style="font-size:9px"  ><b>Link Type</b></td>
					<td align="center" style="font-size:9px"  ><b>Dispatch Address</b></td>
					<td align="center" style="font-size:9px"  ><b>Indicative Value</b></td>
					<td align="center" style="font-size:9px"  ><b>Product Name</b></td>
					<td align="center" style="font-size:9px"  ><b>Cust Po Date</b></td>
					<td align="center" style="font-size:9px"  ><b>Cust Po Number</b></td>
					<td align="center" style="font-size:9px"  ><b>Loc Number</b></td>
					<td align="center" style="font-size:9px"  ><b>Pri Loc</b></td>
					<td align="center" style="font-size:9px"  ><b>Product</b></td>
					<td align="center" style="font-size:9px"  ><b>Ratio</b></td>
					<td align="center" style="font-size:9px"  ><b>Sec Loc</b></td>
					<td align="center" style="font-size:9px"  ><b>Sub Product</b></td>
					<td align="center" style="font-size:9px"  ><b>Token Number</b></td>
					<td align="center" style="font-size:9px"  ><b>Order Number</b></td>
					<td align="center" style="font-size:9px"  ><b>Charge Hdr Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Order Line Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Service No</b></td>
					<td align="center" style="font-size:9px"  ><b>Inv Amt</b></td>
					<td align="center" style="font-size:9px"  ><b>Amt</b></td>
					<td align="center" style="font-size:9px"  ><b>Total Amount</b></td>
					<td align="center" style="font-size:9px"  ><b>Advance</b></td>
					<td align="center" style="font-size:9px"  ><b>Installment Rate</b></td>
					<td align="center" style="font-size:9px"  ><b>Start Date Days</b></td>
					<td align="center" style="font-size:9px"  ><b>Start Date Months</b></td>
					<td align="center" style="font-size:9px"  ><b>End Date Days</b></td>
					<td align="center" style="font-size:9px"  ><b>End Date Months</b></td>
					<td align="center" style="font-size:9px"  ><b>Annual Rate</b></td>
					<td align="center" style="font-size:9px"  ><b>Contract Period Mnths</b></td>
					<td align="center" style="font-size:9px"  ><b>Commitment Period</b></td>
					<td align="center" style="font-size:9px"  ><b>Notice Period</b></td>
					<td align="center" style="font-size:9px"  ><b>Period In Month</b></td>
					<td align="center" style="font-size:9px"  ><b>Tot Po Amt</b></td>
					<td align="center" style="font-size:9px"  ><b>Party Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Cust Account Id</b></td>
					<td align="center" style="font-size:9px"  ><b>M6 Product Id</b></td>
					<td align="center" style="font-size:9px"  ><b>M6 Order Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Ib Service List Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Ib Order Line Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Ib Pk Charges Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Ib Charge Hdr Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Pre Crm Order Id</b></td>
					<td align="center" style="font-size:9px"  ><b>M6 Ckt Dtls Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Pk Charges Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Business Serial No</b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Location From</b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Location To</b></td>
		</tr>
		</logic:equal>
		
		<logic:equal name="isUsage" value="1">
		<tr>
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
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b> Cust Logical SI No.</b></td>										
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
					<!--<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bandwidth Attribute </b></td>-->
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bandwidth UOM </b></td>
					<!--<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b> Rate Code</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Lat Mile Media </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Last Mile Remarks </b></td>-->
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Chargeable Distance</b></td>
					<!--<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Last Mile Provider</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Link Type</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Indicative Value</b></td>-->
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
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component Cyclic/Non-Cyclic Amount</b></td>							
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component FX Instance Id</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Start Token No</b></td>
					<!--<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Start FX Status</b></td>-->
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>End Token No</b></td>
					<!--<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>End FX Status</b></td>-->																			
					<td align="center" style="font-size: 9px" bgcolor="#FF9255"><b>Billing Trigger CreateDate</b></td>
							<td align="center" style="font-size: 9px" bgcolor="#FF9255"><b>Serial NO</b></td>
							<td align="center" style="font-size: 9px" bgcolor="#FF9255"><b>Customer SEGMENT CODE</b></td>
					<td align="center" style="font-size: 9px" bgcolor="#FF9255"><b>desired Due Date</b></td>
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
			<tr>
				<td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="DisconnectChangeOrderReportInExcel" scope="request"> 					
				<logic:iterate id="row" name="DisconnectChangeOrderReportInExcel" indexId="recordId">
					<logic:equal name="isUsage" value="0">
				<tr>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="logicalCircuitId"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="linename"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="frequencyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bill_period"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="startDateLogic"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="endDateLogic"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeEndDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="startDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="creditPeriod"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="currencyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="entity"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingMode"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingformat"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="licCompanyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="taxation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="penaltyClause"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevel"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevelId"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="store"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="hardwareType"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="formAvailable"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="nature_sale"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="type_sale"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="party"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="annitation"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="tokenno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="pm_pro_date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="locDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingTriggerDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="challenno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="challendate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="child_act_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="child_ac_fxstatus"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="order_type"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceOrderType"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="copcapprovaldate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingtrigger_createdate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="hardware_flag"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeTypeID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceStage"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderStage"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="actmgrname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="prjmngname"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="rfs_date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cust_po_rec_date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="charge_status"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="verticalDetails"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="region"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="demo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="newOrderRemark"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderStageDescription"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="mocn_no"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="disconnection_remarks"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="srno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="request_rec_date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ordermonth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ckt_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="standard_reason"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bandwaidth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_bandwidth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_uom"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bandwidth_att"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="uom"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="rate_code"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="last_mile_media"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="last_mile_remarks"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeable_Distance"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="last_mile_provider"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="link_type"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="dispatchAddress1"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="indicative_value"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="productName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDetailNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="locno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="primarylocation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="prodAlisName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ratio"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="seclocation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="sub_linename"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="tokenNO"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="charge_hdr_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderLineNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceId"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineItemAmount"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="amt"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="totalAmountIncludingCurrent"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="advance"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="installation_addressaa1"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="startDateDays"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="startDateMonth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="endDateDays"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="endDateMonth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="annualRate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="contractMonths"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="commitmentPeriod"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="noticePeriod"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="periodsInMonths"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="totalPoAmt"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="party_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cust_act_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6_prod_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6_order_id"/></td>
						<td align="left" style="font-size:9px" width="14%">&nbsp;</td>
						<td align="left" style="font-size:9px" width="14%">&nbsp;</td>
						<td align="left" style="font-size:9px" width="14%">&nbsp;</td>
						<td align="left" style="font-size:9px" width="14%">&nbsp;</td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="pre_crmorderid"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6cktid"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="pk_charge_id"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="business_serial_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_location_from"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_location_to"/></td>
					</tr>
					</logic:equal>
					
				<logic:equal name="isUsage" value="1">
							<tr>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceName"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="linename"/></td>						    
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="crmAccountNoString"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="creditPeriodName"/></td>
						    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="currencyName"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="entity"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingMode"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingTypeName"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingformat"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="licCompanyName"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="taxation"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="penaltyClause"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevelName"/></td>
						    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevelId"/></td>							
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poNumber"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poDate"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="party"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="pm_pro_date"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="locDate"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingTriggerDate"/></td>							
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="child_act_no"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="fx_internal_acc_id"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="child_ac_fxstatus"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderDate"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="order_type"/></td>
						    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceOrderType"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="copcapprovaldate"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceStage"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderStage"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="actmgrname"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="prjmngname"/></td>
						    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderDate"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="rfs_date"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cust_po_rec_date"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="verticalDetails"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="region"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="demo"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="newOrderRemark"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderStageDescription"/></td>
						    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="disconnection_remarks"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineno"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ordermonth"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ckt_id"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="standard_reason"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bandwaidth"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_bandwidth"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_uom"/></td>
						    <!--<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bandwidth_att"/></td>-->
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="uom"/></td>
							<!--<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="rate_code"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="last_mile_media"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="last_mile_remarks"/></td>-->
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeable_Distance"/></td>
							<!--<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="last_mile_provider"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="link_type"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="indicative_value"/></td>-->
						    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="productName"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDate"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDetailNo"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="locno"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="primarylocation"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="seclocation"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="prodAlisName"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="sub_linename"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ratio"/></td>
						    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderNo"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderLineNumber"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceId"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="totalAmountIncludingCurrent"/></td>							
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="contractMonths"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="commitmentPeriod"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="noticePeriod"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="periodsInMonths"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="totalPoAmt"/></td>
						    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="party_id"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cust_act_id"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6cktid"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6_order_id"/></td>														
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_location_from"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_location_to"/></td>
							<!--  Manisha : changes for Usage -->																	
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageID"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageName"/></td>				
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentInfoID"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentID"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentStatus"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startLogic"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endLogic"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.end_date"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="startDateDays"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="startDateMonth"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="endDateDays"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="endDateMonth"/></td>																
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentType"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentAmount"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentInstanceID"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxTokenNo"/></td>
							<!--<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxStartStatus"/></td>-->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endTokenNo"/></td>
							<!--<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endFxStatus"/></td>-->															
							<!--  Manisha : End -->						
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
<!-- LEPM OWNER REPORT -->

<logic:present name="LEPMOwnerExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=LEPMOwnerExcelList.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="14" align="left" style="font-size:10px"><strong>LEPM OWNER REPORT</strong></td>
		</tr>
		<tr>
			<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Task Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Owner</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>User Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Owner Phone</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Owner Emailid</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Task Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Actual Start Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Actual End Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Planned Start Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Planned End Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Order Approved Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Crm Order Id</b></td>
					
				</tr>

		<logic:empty name="LEPMOwnerExcelList">
			<tr>
				<td colspan="14" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="LEPMOwnerExcelList" scope="request"> 					
				<logic:iterate id="row" name="LEPMOwnerExcelList" indexId="recordId">
					<tr>
						<td align="left" style="font-size:9px" width="5%"   ><bean:write  name="row" property="taskNumber"/></td>
						<td align="left" style="font-size:9px" width="5%"   ><bean:write  name="row" property="pmName"/></td>
						<td align="left" style="font-size:9px" width="5%"   ><bean:write  name="row" property="userName"/></td>
						<td align="left" style="font-size:9px" width="5%"   ><bean:write  name="row" property="contactCell"/></td>
						<td align="left" style="font-size:9px" width="5%"   ><bean:write  name="row" property="emailId"/></td>
						<td align="left" style="font-size:9px" width="5%"   ><bean:write  name="row" property="taskName"/></td>
						<td align="left" style="font-size:9px" width="5%"   ><bean:write  name="row" property="pm_pro_date"/></td>
						<td align="left" style="font-size:9px" width="5%"   ><bean:write  name="row" property="pmApproveDate"/></td>
						<td align="left" style="font-size:9px" width="5%"   ><bean:write  name="row" property="pm_pro_date"/></td>
						<td align="left" style="font-size:9px" width="5%"   ><bean:write  name="row" property="pmapprovaldate"/></td>
						<td align="left" style="font-size:9px" width="5%"   ><bean:write  name="row" property="copcApproveDate"/></td>
						<td align="left" style="font-size:9px" width="5%"   ><bean:write  name="row" property="orderNo"/></td>			
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

<!--[001] start-->

<logic:present name="LEPMOrderCancelReportInExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=LEPMCANCELORDERREPORT.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="16" align="left" style="font-size:10px"><strong>LEPM Cancel Order Report</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CRM Order Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Copc Approved Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical Circuit Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Quotenumber</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Product Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Product subtype</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>From Site </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>To Site</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Sub Type</b></td>
				   	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Provision Bandwidth </b></td>
				   	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Item Amount</b></td>
				   	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Project Mgr</b></td>
				   	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Project Mgr Email </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Mgr </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Zone </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Category </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Po Number </b></td>
				   	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Po Date </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Entry Date </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pm Approval Date </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Manager Receive Date </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Nio Approval Date </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo Infrastartdate </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo Infraenddate </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Rfs Date </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Category</b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Reporting Status </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Item Description </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Line Name </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Subline Name </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Name </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pk Charges Id </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Line No </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Number </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Companyname </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Licence Company</b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contract Period</b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Payment Term</b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cyclic Non Cyclic</b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Line Type</b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Uom</b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Bandwidth</b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Uom</b></td>
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
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Parent Line Name</b></td>
				   	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Stage</b></td>
				   	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cancel Date</b></td>
					


		</tr>

		<logic:empty name="LEPMOrderCancelReportInExcel">
			<tr>
				<td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="LEPMOrderCancelReportInExcel" scope="request"> 					
				<logic:iterate id="row" name="LEPMOrderCancelReportInExcel" indexId="recordId">
				<tr>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalCircuitId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="quoteNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="productName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subProductName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primarylocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="seclocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ordersubtype"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAmount"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="prjmngname"/></td>
								<td align="left" style="font-size:9px" width="10%"><bean:write name="row" property="prjmgremail"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="actmngname"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="zoneName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="act_category"/></td>
								<td align="left" style="font-size:9px" width="10%"><bean:write name="row" property="custPoDetailNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmApproveDate"/></td>
							    <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="amApproveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="nio_approve_date"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="demo_infrastartdate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="demo_infra_enddate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="rfs_date"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="ordercategory"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="orderStatus"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="line_desc"/></td> 
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="linename"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="sub_linename"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="chargeName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="chargeinfoID"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="serviceProductID"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="serviceName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="accountID"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="entity"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="licCompanyName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="contractPeriod"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="frequencyName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="chargeTypeName"/></td>
							    <td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="serviceType"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="uom"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="billing_bandwidth"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="billing_uom"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="from_city"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="to_city"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="oldordertotal"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="oldlineamt"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="old_contract_period"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="ratio"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="taxation"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="factory_ckt_id"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="distance"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="accountManager"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="currencyCode"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="orderTotal"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="poAmount"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="bisource"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="orderType"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="dispatchAddress1"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="parent_name"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="serviceStage"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="canceldate"/></td>
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

<!--[001] end-->
<!--[002] start-->

<logic:present name="LEPMOrderDetailReportInExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=LEPMORDERDETAILREPORT.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="16" align="left" style="font-size:10px"><strong>LEPM Order Detail- Report</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CRM Order Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Copc Approved Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical Circuit Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Quotenumber</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Product Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Product subtype</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>From Site </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>To Site</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Sub Type</b></td>
				   	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Provision Bandwidth </b></td>
				   	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Item Amount</b></td>
				   	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Project Mgr</b></td>
				   	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Project Mgr Email </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Mgr </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Zone </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Category </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Po Number </b></td>
				   	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Po Date </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Entry Date </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pm Approval Date </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Manager Receive Date </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Nio Approval Date </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo Infrastartdate </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo Infraenddate </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Rfs Date </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Category</b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Reporting Status </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Item Description </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Line Name </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Subline Name </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Name </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pk Charges Id </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Line No </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Number </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Companyname </b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Licence Company</b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contract Period</b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Payment Term</b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cyclic Non Cyclic</b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Line Type</b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Uom</b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Bandwidth</b></td>
				  	<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Uom</b></td>
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
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Parent Line Name</b></td>
				   
					


		</tr>

		<logic:empty name="LEPMOrderDetailReportInExcel">
			<tr>
				<td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="LEPMOrderDetailReportInExcel" scope="request"> 					
				<logic:iterate id="row" name="LEPMOrderDetailReportInExcel" indexId="recordId">
				<tr>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalCircuitId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="quoteNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="productName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subProductName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primarylocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="seclocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ordersubtype"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAmount"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="prjmngname"/></td>
								<td align="left" style="font-size:9px" width="10%"><bean:write name="row" property="prjmgremail"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="actmngname"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="zoneName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="act_category"/></td>
								<td align="left" style="font-size:9px" width="10%"><bean:write name="row" property="custPoDetailNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmApproveDate"/></td>
							    <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="amApproveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="nio_approve_date"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="demo_infrastartdate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="demo_infra_enddate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="rfs_date"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="ordercategory"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="orderStatus"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="line_desc"/></td> 
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="linename"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="sub_linename"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="chargeName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="chargeinfoID"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="serviceProductID"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="serviceName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="accountID"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="entity"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="licCompanyName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="contractPeriod"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="frequencyName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="chargeTypeName"/></td>
							    <td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="serviceType"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="uom"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="billing_bandwidth"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="billing_uom"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="from_city"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="to_city"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="oldordertotal"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="oldlineamt"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="old_contract_period"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="ratio"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="taxation"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="factory_ckt_id"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="distance"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="accountManager"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="currencyCode"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="orderTotal"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="poAmount"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="bisource"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="orderType"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="dispatchAddress1"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="parent_name"/></td>
								
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>
<logic:present name="nonMigAppUnappNewOrderDetailsExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=NonMigratedAPP_UNAPPNewOrderDetails.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<logic:equal name="isUsage" value="0">
				<td colspan="14" align="left" style="font-size:10px"><strong>NON MIGRATED APPROVED AND UNAPPROVED NEW ORDER DETAILS</strong></td>
			</logic:equal>
			<logic:equal name="isUsage" value="1">
				<td colspan="14" align="left" style="font-size:10px"><strong>NON MIGRATED APPROVED AND UNAPPROVED NEW ORDER DETAILS (USAGE)</strong></td>
			</logic:equal>
		</tr>
		<logic:equal name="isUsage" value="0">
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Number</b></td>						
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>M6 Order No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Circuit Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bandwidth</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billling Bandwidth</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Uom</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Kms Distance</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Ratio</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Location From</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Location To</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pri Location</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Sec Location</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Product</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Subproduct</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Legal Entity</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Licence Company</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Currency</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Credit Period</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Format</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Level</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Level Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Taxation</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Hardware Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Hardware Flag</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Store</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Type Of Sale</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Period</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Mode</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Number</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Period In Month</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Tot Po Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Receive Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Tot Po Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Start Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Inv Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Annotation</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Loc Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Loc Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trig Date</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Trg Create Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pm Prov Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trig Flag</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Token No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Fx Sd Chg Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Fx Status</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Business Serial No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Opms Account Id</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Lineitemnumber</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Stage</b></td>																					
		</tr>
		</logic:equal>
		
		<logic:equal name="isUsage" value="1">
				<tr>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>M6 Order No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Line Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Service Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Circuit Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bandwidth</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billling Bandwidth</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bill Uom</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Kms Distance</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Ratio</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Location From</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Location To</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Pri Location</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Sec Location</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Product</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Subproduct</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Legal Entity</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Licence Company</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Currency</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Credit Period</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bill Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bill Format</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Level</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Level Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Taxation</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Mode</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Po Number</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Po Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Period In Month</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Tot Po Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Po Receive Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Po Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Po Date</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Loc Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Loc Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Trig Date</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Pm Prov Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Trig Flag</b></td>									
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Lineitemnumber</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Order Stage</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Child Account Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Child Internal Account No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Child Account Fx Status</b></td>	
					<!--  Manisha : change for usage -->												
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Package ID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Package Name</b></td>				
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component ID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component InfoID</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Start Logic</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Start Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component End Logic</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component End Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Token No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Cyclic/Non-Cyclic Amount</b></td>														
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component FX Instance Id</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Start FX Status</b></td>
					
					<!-- Manisha : End -->
				</tr>
				</logic:equal>
		<logic:empty name="nonMigAppUnappNewOrderDetailsExcelList">
			<tr>
				<td colspan="14" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="nonMigAppUnappNewOrderDetailsExcelList" scope="request"> 					
				<logic:iterate id="row" name="nonMigAppUnappNewOrderDetailsExcelList" indexId="recordId">
				<logic:equal name="isUsage" value="0">
					<tr >
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="party_no"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountNoString"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6OrderNo"/></td>	
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceName"/></td>	
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceDetDescription"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceOrderType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalCircuitId"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_bandwidth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billUom"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="kmsDistance"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ratio"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fromLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="toLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primaryLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="secondaryLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="productName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subProductName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="entity"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lcompanyname"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="creditPeriodName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTypeName"/></td><!-- Bill Type -->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingFormatName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taxation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="hardwaretypeName"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="hardwareFlag"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="storename"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="saleType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingMode"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ponum"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="totalPoAmt"/></td><!--Total Amount-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poRecieveDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDetailNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poAmount"/></td><!--Cust Tot Po Amt-->
				   			<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeTypeName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAmount_String"/></td><!--Inv Amt-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lineItemAmount"/></td><!-- Amt-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAnnotation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="locDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_No"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTriggerDate"/></td>
							<td align="left" style="font-size:9px" width="5%"></td><!-- Bill Trg Create Date -->							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmApproveDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="tokenNO"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_St_Chg_Status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fxStatus"/></td>
							<td align="left" style="font-size:9px" width="5%"></td><!-- Business Serial No	-->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Opms Account Id -->	
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderLineNumber"/></td><!-- Order Line Id-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderStage"/></td><!-- Order Line Id-->											
					</tr>
					</logic:equal>
					<logic:equal name="isUsage" value="1">	
							<tr>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="party_no"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountNoString"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6OrderNo"/></td>	
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceName"/></td>	
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceDetDescription"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceOrderType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalCircuitId"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_bandwidth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billUom"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="kmsDistance"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ratio"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fromLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="toLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primaryLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="secondaryLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="productName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subProductName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="entity"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lcompanyname"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="creditPeriodName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTypeName"/></td><!-- Bill Type -->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingFormatName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taxation"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingMode"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ponum"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="totalPoAmt"/></td><!--Total Amount-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poRecieveDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDetailNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="locDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_No"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTriggerDate"/></td>						
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmApproveDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderLineNumber"/></td><!-- Order Line Id-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderStage"/></td><!-- Order Line Id-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_external_acc_id"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_internal_acc_id"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="childAccountFXSataus"/></td>
							<!--  Manisha : changes for Usage -->	
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageID"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageName"/></td>																
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentID"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentInfoID"/></td>							
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentStatus"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startLogic"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endLogic"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.end_date"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxTokenNo"/></td>	
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentType"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentAmount"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentInstanceID"/></td>		
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxStartStatus"/></td>														
							<!--  Manisha : End -->				
							</tr>
							</logic:equal>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

<logic:present name="pendingBillingPDOrderListInExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=PENDINGBILLINGPERMANENTDISCONNECTIONREPORT.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="8" align="left" style="font-size:10px"><strong>SR Order Status Report</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>SR No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LSI No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Remarks</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>SR Raised By</b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>SR Creation Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Date Of Disconnection</b></td>
		</tr>

		<logic:empty name="pendingBillingPDOrderListInExcel">
			<tr>
				<td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="pendingBillingPDOrderListInExcel" scope="request"> 					
				<logic:iterate id="row" name="pendingBillingPDOrderListInExcel" indexId="recordId">
				<tr>
						<td align="left" style="font-size:9px" width="8%"><bean:write  name="row" property="srno"/></td>
						<td align="left" style="font-size:9px" width="8%"><bean:write  name="row" property="orderNo"/></td>
                        <td align="left" style="font-size:9px" width="8%"><bean:write  name="row" property="logicalSINo"/></td>
                        <td align="left" style="font-size:9px" width="10%"><bean:write  name="row" property="orderStatus"/></td>
                    	<td align="left" style="font-size:9px" width="40%"><bean:write  name="row" property="neworder_remarks"/></td>
						<td align="left" style="font-size:9px" width="18%"><bean:write  name="row" property="productName"/></td>
                    	<td align="left" style="font-size:9px" width="8%"><bean:write  name="row" property="srDate"/></td>
                    	<td align="left" style="font-size:9px" width="8%"><bean:write  name="row" property="disdate"/></td>				
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

<!--[003] end-->
<logic:present name="nonAppAppChangeOrderDetailsExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=NonAPP_APPChangeOrderDetails.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<logic:equal name="isUsage" value="0">
				<td colspan="14" align="left" style="font-size:10px"><strong>NON APPROVED AND APPROVED CHANGE ORDER DETAILS</strong></td>
			</logic:equal>
			<logic:equal name="isUsage" value="1">
				<td colspan="14" align="left" style="font-size:10px"><strong>NON APPROVED AND APPROVED CHANGE ORDER DETAILS (USAGE)</strong></td>
			</logic:equal>
		</tr>
		<logic:equal name="isUsage" value="0">
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Number</b></td>						
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>M6 Order No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Circuit Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bandwidth</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billling Bandwidth</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Uom</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Kms Distance</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Ratio</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Location From</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Location To</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pri Location</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Sec Location</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Product</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Subproduct</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Legal Entity</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Licence Company</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Currency</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Credit Period</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Format</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Level</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Level Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Taxation</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Hardware Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Hardware Flag</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Store</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Type Of Sale</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Period</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Mode</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Number</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Period In Month</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Tot Po Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Receive Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Tot Po Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Start Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Inv Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Annotation</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Loc Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Loc Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trig Date</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Trg Create Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pm Prov Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trig Flag</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Token No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Fx Sd Chg Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Fx Status</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Business Serial No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Opms Account Id</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Lineitemnumber</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Stage</b></td>					
		</tr>
		</logic:equal>
		<logic:equal name="isUsage" value="1">
				<tr>																													
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>M6 Order No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Line Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Service Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Circuit Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bandwidth</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billling Bandwidth</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bill Uom</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Kms Distance</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Ratio</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Location From</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Location To</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Pri Location</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Sec Location</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Product</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Subproduct</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Legal Entity</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Licence Company</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Currency</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Credit Period</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bill Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bill Format</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Level</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Level Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Taxation</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Mode</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Po Number</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Po Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Period In Month</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Tot Po Amt</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Po Receive Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Po Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Cust Po Date</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Loc Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Loc Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Trig Date</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Pm Prov Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Billing Trig Flag</b></td>									
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Lineitemnumber</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Order Stage</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Child Account Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Child Internal Account No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Child Account Fx Status</b></td>	
					<!--  Manisha : change for usage -->												
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Package ID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Package Name</b></td>				
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component ID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component InfoID</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Status</b></td>
					<!--<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Start Logic</b></td>-->
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Start Date</b></td>
					<!--<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component End Logic</b></td>-->
					<!--<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component End Date</b></td>-->
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Token No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Cyclic/Non-Cyclic Amount</b></td>														
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component FX Instance Id</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Start FX Status</b></td>														
					<!-- Manisha : End -->																
				</tr>
				</logic:equal>
		<logic:empty name="nonAppAppChangeOrderDetailsExcelList">
			<tr>
				<td colspan="14" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="nonAppAppChangeOrderDetailsExcelList" scope="request"> 					
				<logic:iterate id="row" name="nonAppAppChangeOrderDetailsExcelList" indexId="recordId">
				<logic:equal name="isUsage" value="0">
					<tr >
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="party_no"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountNoString"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6OrderNo"/></td>	
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceName"/></td>	
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceDetDescription"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceOrderType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalCircuitId"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_bandwidth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billUom"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="kmsDistance"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ratio"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fromLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="toLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primaryLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="secondaryLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="productName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subProductName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="entity"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lcompanyname"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="creditPeriodName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTypeName"/></td><!-- Bill Type -->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingFormatName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taxation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="hardwaretypeName"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="hardwareFlag"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="storename"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="saleType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingMode"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ponum"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"> <bean:write  name="row" property="totalPoAmt"/></td><!--Total Amount-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poRecieveDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDetailNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poAmount"/></td><!--Cust Tot Po Amt-->
				   			<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeTypeName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAmount_String"/></td><!--Inv Amt-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lineItemAmount"/></td><!-- Amt-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAnnotation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="locDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_No"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTriggerDate"/></td>
							<td align="left" style="font-size:9px" width="5%"></td><!-- Bill Trg Create Date -->							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmApproveDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="tokenNO"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_St_Chg_Status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fxStatus"/></td>
							<td align="left" style="font-size:9px" width="5%"></td><!-- Business Serial No	-->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Opms Account Id -->	
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderLineNumber"/></td><!-- Order Line Id-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderStage"/></td><!-- Order Line Id-->							
				
					</tr>
					</logic:equal>
					<logic:equal name="isUsage" value="1">
							<tr>							
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="party_no"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountNoString"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6OrderNo"/></td>	
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceName"/></td>	
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceDetDescription"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceOrderType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalCircuitId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_bandwidth"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billUom"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="kmsDistance"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ratio"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fromLocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="toLocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primaryLocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="secondaryLocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="productName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subProductName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="entity"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lcompanyname"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="creditPeriodName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTypeName"/></td><!-- Bill Type -->
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingFormatName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taxation"/></td>							
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingMode"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ponum"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="totalPoAmt"/></td><!--Total Amount-->
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poRecieveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDetailNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>							
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="locDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_No"/></td>							
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTriggerDate"/></td>						
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmApproveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>							
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderLineNumber"/></td><!-- Order Line Id-->
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderStage"/></td><!-- Order Line Id-->
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_external_acc_id"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_internal_acc_id"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="childAccountFXSataus"/></td>
								<!--  Manisha : changes for Usage -->	
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageID"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageName"/></td>																
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentID"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentInfoID"/></td>							
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentStatus"/></td>
								<!--<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startLogic"/></td>-->
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startDate"/></td>
								<!--<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endLogic"/></td>-->
								<!--<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.end_date"/></td>-->
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxTokenNo"/></td>	
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentType"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentAmount"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentInstanceID"/></td>		
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxStartStatus"/></td>								
								<!--  Manisha : End -->				
							</tr>
							</logic:equal>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>
<logic:present name="RestPendingLineReportInExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=RESTPENDINGLINEREPORT.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<logic:equal name="isUsage" value="0">
				<td colspan="16" align="left" style="font-size:10px"><strong>REST PENDING LINE REPORT</strong></td>
			</logic:equal>
			<logic:equal name="isUsage" value="1">
				<td colspan="16" align="left" style="font-size:10px"><strong>REST PENDING LINE REPORT (USAGE)</strong></td>
			</logic:equal>
		</tr>
		<tr align="Center">
			<logic:equal name="isUsage" value="0">
					<tr>											
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Account No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Prj Manager </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Account Manager  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Date  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Dispatch Address  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Charge Start Date  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Po Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Approve Date  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b> COPC Approve Date  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Date  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust Po Rec Date  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust PO Date  </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cancel Date </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Rate Code </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Last Mile Media </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Last Mile Remarks </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Link Type </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Indicative Value </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bandwidth UOM  </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Last Mile Provider  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Location</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Hardware Flag  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Charge Type  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Charge Status  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Mode </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Form C Available  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Charge Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>PoNumber </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>CustPoNumber  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cancel By  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Primary Location  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Secondary Location  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Address  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Fx Status  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Fx Sd Charge Status  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Fx Ed Charge Status </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Child A/C Fx Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>RFS Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Opms Act Id</b></td>				
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Product Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Sub Product Name</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LineItemNo</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Frequency</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Mocn No</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CRM Product Name</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Level No</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical Ckt Id</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Logical SI No</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Name</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CKT ID</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Location From</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Location To</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Stage</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>BI-Source</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Hardware Type</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Token No</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Level</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Nature Of Sale</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo Type</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Type Of Sale</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Bandwidth </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Uom </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Kms Distance </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Ratio </b></td>		
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Stage </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Annotation </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Child Act No </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cancel Reason </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Type </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OrderSubType </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Credit Period </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Currency </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Legal Entity </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Type </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Format </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Licence Company </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Taxation </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>StoreName </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>BillPeriod </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b> 	Crm Order Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>InvAmt</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LineAmmount </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>TotalAmt </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Periods in Month </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Total PO AMT </b></td>			
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Id </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CrmAccountNo </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>M6 Product ID </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Total Po Amt </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Business Serial No </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ServiceNo </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LineNo </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LB Service List Id </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LB PK Charge Id </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ChargeInfoId </b></td>	
				   <td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Annual Rate </b></td>	
		</tr>
		</logic:equal>
		<logic:equal name="isUsage" value="1">
		 <tr>																					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Account Id </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Prj Manager </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Account Manager  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Date  </b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Po Date</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>COPC Approve Date  </b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust Po Rec Date  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust PO Date  </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cancel Date </b></td>				    
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bandwidth UOM  </b></td>				    
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Location</b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Mode </b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>PoNumber </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>CustPoNumber  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cancel By  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Primary Location  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Secondary Location  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Address  </b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>RFS Date</b></td>								
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Product Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Sub Product Name</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>LineItemNo</b></td>												
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>CRM Product Name</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Level No</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Logical SI No</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust Logical SI No</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Service Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Line Name</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>CKT ID</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Location From</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Location To</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Service Stage</b></td>																
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Level</b></td>							
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Demo Type</b></td>						
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Bandwidth </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Billing Uom </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Kms Distance </b></td>						
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Stage </b></td>							
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Child Act No </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Child Act FX Internal No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cancel Reason </b></td>							
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Type </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Service Order Type </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Vertical </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Region </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Credit Period </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Currency </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Legal Entity </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Type </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Bill Format </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Licence Company </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Taxation </b></td>											
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b> Crm Order Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Total Amount</b></td>									
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Periods in Month </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Total PO AMT </b></td>			
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party Id </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>CrmAccountNo </b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>M6 Product ID </b></td>										
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>ServiceNo </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>LineNo </b></td>		
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Package ID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Package Name</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component ID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component InfoID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Component Start Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Component FX Instance Id</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Start Token No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Start FX Status</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>End FX Status</b></td>
		</tr>
		</logic:equal>
		<logic:empty name="RestPendingLineReportInExcel">
			<tr>
				<td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="RestPendingLineReportInExcel" scope="request"> 					
				<logic:iterate id="row" name="RestPendingLineReportInExcel" indexId="recordId">
				<logic:equal name="isUsage" value="0">
					<tr >
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="partyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="partyNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="projectManager"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountManager"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="dispatchAddress1"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeEndDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="amapprovaldate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="copcapprovaldate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poReceiveDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="canceldate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="rate_code"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="last_mile_media"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="last_mile_remarks"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="link_type"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="indicative_value"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="uom"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="last_mile_provider"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_address"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="hardware_flag"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="charge_status"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingMode"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="formAvailable"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDetailNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cancelflag"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="primarylocation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="seclocation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_address"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="fx_status"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="fx_sd_status"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="fx_ed_status"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="child_ac_fxstatus"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="rfs_date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="opms_act_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="productName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="subProductName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="frequencyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="mocn_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="crm_productname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevelNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="logicalCircuitId"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="linename"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ckt_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_from"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_to"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceStage"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bisource"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="hardwaretypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="tokenno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevel"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="saleNature"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="demoType"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="saleType"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_bandwidth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_uom"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="distance"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ratio"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="stageName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="annitation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="child_act_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cancelServiceReason"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="order_type"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ordersubtype"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="verticalDetails"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="regionName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="creditPeriod"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="currencyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="entity"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingFormatName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lcompanyname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="taxation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="storename"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bill_period"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeAmount"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineamt"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poAmountSum"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="contractPeriod"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="totalAmountIncludingCurrent"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="party_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="crmAccountNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6cktid"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cust_total_poamt"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="business_serial_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceId"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceproductid"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lb_service_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lb_pk_charge_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeInfoID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="annual_rate"/></td>
								
					</tr>
					</logic:equal>
					
					<logic:equal name="isUsage" value="1">
					<tr >
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="partyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="partyNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="projectManager"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountManager"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderDate"/></td>																
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="copcapprovaldate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poReceiveDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="canceldate"/></td>						
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="uom"/></td>						
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_address"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingMode"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDetailNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cancelBy"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="primarylocation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="seclocation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_address"/></td>								
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="rfs_date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="productName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="subProductName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="crm_productname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevelNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="logicalCircuitId"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="linename"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ckt_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_from"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_to"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceStage"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevel"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="demoType"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_bandwidth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_uom"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="distance"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="stageName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="child_act_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="fx_internal_acc_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cancelServiceReason"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="order_type"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ordersubtype"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="verticalDetails"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="regionName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="creditPeriodName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="currencyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="entity"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingFormatName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lcompanyname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="taxation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poAmountSum"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="contractPeriod"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="totalAmountIncludingCurrent"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="party_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="crmAccountNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6_prod_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceId"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceproductid"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageID"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageName"/></td>	
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentID"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentName"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentInfoID"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentStatus"/></td> 
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startDate"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentType"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentInstanceID"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxTokenNo"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxStartStatus"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endFxStatus"/></td>								
					</tr>
					</logic:equal>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>
<%-- start report for biling work queue --%>
<logic:present name="viewBillingWorkQueueList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=BillingWorkQueueReportUsage.xls"); %>
<table width="100%" border="1" cellpadding="0" cellspacing="0">
<tr><td colspan="46" align="left" style="font-size:10px"><strong>BILLING WORK QUEUE REPORT USAGE</strong></td></tr><tr align="Center"><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Number</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Number</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Subtype</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Number</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical Circuit Id</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Line No</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Number</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Currency</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Taxation</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trig Date</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Number</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Date</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PO Amt</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Date</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Approved Date</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Logical Si Id</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Name</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Type</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Format</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Legal Entity</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Credit Period</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Licence Company</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Level</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Level Number</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Notice Period</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Mode</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Penelty Clause</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Commitment Period</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pri Location</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Sec Location</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contract Start Date</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contract End Date</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trig Flag</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LOC Date</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pm Prov Date</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Child Account Number</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Child Account Fx Status</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust PO No.</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust PO Date</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Tot PO Amt</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>LOC No.</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Address</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>SI ID</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cancel By</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cancel Date</b></td><td align="center" style="fo
nt-size:9px" bgcolor="#FF9255"><b>Cancel Reason</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo Type</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Order Type Desc</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Package ID</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Package Name</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component ID</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Name</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component InfoID</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Type</b></td><%--<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>rental / nrc component amount</b></td>--%><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Status</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Start Logic</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Start Date</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component End Logic</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component End Date</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Start Token No</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>End Token No</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component FX Instance Id</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Start FX Status</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>End FX Status</b></td></tr>
<logic:empty name="viewBillingWorkQueueList">
<tr><td colspan="46" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td></tr>
</logic:empty>
<logic:notEmpty  name="viewBillingWorkQueueList" scope="request"> 					
<logic:iterate id="row" name="viewBillingWorkQueueList" indexId="recordId">
<tr><td><bean:write  name="row" property="orderNumber"/></td><td><bean:write  name="row" property="party_num"/></td><td><bean:write  name="row" property="ordersubtype"/></td><td><bean:write  name="row" property="serviceId"/></td><td><bean:write  name="row" property="logicalSINo"/></td><td><bean:write  name="row" property="serviceproductid"/></td><td><bean:write  name="row" property="accountID"/></td><td><bean:write  name="row" property="currencyName"/></td><td><bean:write  name="row" property="taxation"/></td><td><bean:write  name="row" property="serviceName"/></td><td><bean:write  name="row" property="billing_trigger_date"/></td><td><bean:write  name="row" property="poNumber"/></td><td><bean:write  name="row" property="poDate"/></td><td><bean:write  name="row" property="totalPoAmt"/></td><td><bean:write  name="row" property="orderDate"/></td><td><bean:write  name="row" property="copcapprovaldate"/></td><td><bean:write  name="row" property="custSINo"/></td><td><bean:write  name="row" property="linename"/></td><td><bean:write  name="row" property="billingTypeName"/></td><td><bean:write  name="row" property="billingformat"/></td><td><bean:write  name="row" property="entity"/></td><td><bean:write  name="row" property="creditPeriodName"/></td><td><bean:write  name="row" property="licCompanyName"/></td><td><bean:write  name="row" property="billingLevelName"/></td><td><bean:write  name="row" property="billingLevelNo"/></td><td><bean:write  name="row" property="noticePeriod"/></td><td><bean:write  name="row" property="billingMode"/></td><td><bean:write  name="row" property="penaltyClause"/></td><td><bean:write  name="row" property="commitmentPeriod"/></td><td><bean:write  name="row" property="primaryLocation"/></td><td><bean:write  name="row" property="seclocation"/></td><td><bean:write  name="row" property="contractStartDate"/></td><td><bean:write  name="row" property="contractEndDate"/></td><td><bean:write  name="row" property="billingTriggerFlag"/></td><td><bean:write  name="row" property="locDate"/></td><td><bean:write  name="row" property="pm_pro_date"/></td><td><bean:write  name="row" property="child_act_no"/></td><td><bean:write  name="row" property="child_ac_fxstatus"/></td><td><bean:write  name="row" property="orderType"/></td><td><bean:write  name="row" property="custPoDetailNo"/></td><td><bean:write  name="row" property="custPoDate"/></td><td><bean:write  name="row" property="cust_total_poamt"/></td><td><bean:write  name="row" property="LOC_No"/></td><td><bean:write  name="row" property="billing_address"/></td><td><bean:write  name="row" property="m6cktid"/></td><td><bean:write  name="row" property="cancelBy"/></td><td><bean:write  name="row" property="canceldate"/></td><td><bean:write  name="row" property="cancelReason"/></td><td><bean:write  name="row" property="demo"/></td><td><bean:write  name="row" property="serviceTypeDescription"/></td><td><bean:write  name="row" property="packageID"/></td><td><bean:write  name="row" property="packageName"/></td><td><bean:write  name="row" property="componentID"/></td><td><bean:write  name="row" property="componentName"/></td><td><bean:write  name="row" property="componentInfoID"/></td><td><bean:write  name="row" property="componentDto.componentType"/></td><%--<td><bean:write  name="row" property="componentDto.componentAmount"/></td>--%><td><bean:write  name="row" property="componentDto.componentStatus"/></td><td><bean:write  name="row" property="componentDto.startLogic"/></td><td><bean:write  name="row" property="componentDto.startDate"/></td><td><bean:write  name="row" property="componentDto.endLogic"/></td><td><bean:write  name="row" property="componentDto.end_date"/></td><td><bean:write  name="row" property="componentDto.fxTokenNo"/></td><td><bean:write  name="row" property="componentDto.endTokenNo"/></td><td><bean:write  name="row" property="componentDto.componentInstanceID"/></td><td><bean:write  name="row" property="componentDto.fxStartStatus"/></td><td><bean:write  name="row" property="componentDto.endFxStatus"/></td></tr>
</logic:iterate>
</logic:notEmpty>
</table>
</logic:present><%-- end of billing work queue report --%>			
</body>
</html:html>

