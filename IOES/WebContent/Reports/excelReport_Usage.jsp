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

<logic:present name="bcpExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=BCPAddressReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="17" align="left" style="font-size:10px"><strong>BCP ADDRESS REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Id </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Name </b></td>							
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>BCP Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Title</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>First Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Last Name</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255" height="30px"><b>Telephone Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>E-Mail</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Fax</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address1</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address2</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address3</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address4</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>City</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>State</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Postal Code</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Country</b></td>
		</tr>

		<logic:empty name="bcpExcelList">
			<tr>
				<td colspan="17" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="bcpExcelList" scope="request"> 					
				<logic:iterate id="row" name="bcpExcelList" indexId="recordId">
					<tr >
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="BCPId"/></td>
						<td align="left" style="font-size:9px" width="10%"><bean:write  name="row" property="title"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="firstname"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lastName"/></td>														
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="telephonePhno"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="email_Id"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fax"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address1"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address2"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address3"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address4"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cityName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="stateName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="postalCode"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="countryName"/></td>
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

<logic:present name="m6OrderExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=M6OrderStatusReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="17" align="left" style="font-size:10px"><strong>M6 ORDER STATUS REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Id </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Name </b></td>							
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>M6 Order Number</b></td>
		</tr>

		<logic:empty name="m6OrderExcelList">
			<tr>
				<td colspan="17" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="m6OrderExcelList" scope="request"> 					
				<logic:iterate id="row" name="m6OrderExcelList" indexId="recordId">
					<tr >
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNo"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6OrderNo"/></td>
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>


<logic:present name="networkLocsExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=NetworkLocationsReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="15" align="left" style="font-size:10px"><strong>NETWORK LOCATIONS REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td  bgcolor="#FF9255" align="center" style="font-size:9px"><b>Network Location Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Title</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>First Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Last Name</b></td>
					<td align="center" style="font-size:9px" height="30px" bgcolor="#FF9255"><b>Telephone Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>E-Mail</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Fax</b></td>							
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address1</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address2</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address3</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address4</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>City</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>State</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Postal Code</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Country</b></td>
		</tr>

		<logic:empty name="networkLocsExcelList">
			<tr>
				<td colspan="15" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="networkLocsExcelList" scope="request"> 					
				<logic:iterate id="row" name="networkLocsExcelList" indexId="recordId">
					<tr >
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="networkLocationId"/></td>
						<td align="left" style="font-size:9px" width="10%"><bean:write  name="row" property="title"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="firstname"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lastName"/></td>														
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="telephonePhno"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="email_Id"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fax"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address1"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address2"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address3"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address4"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cityName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="stateName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="postalCode"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="countryName"/></td>
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

<logic:present name="clExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=CustomerLocationReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="16" align="left" style="font-size:10px"><strong>CUSTOMER LOCATIONS REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Id </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Name </b></td>							
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Location Code</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Location Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Name</b></td>
					<td align="center" style="font-size:9px" height="30px" bgcolor="#FF9255"><b>Telephone Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>E-Mail</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Fax</b></td>							
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address1</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address2</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address3</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address4</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>City</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>State</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Postal Code</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Country</b></td>
		</tr>

		<logic:empty name="clExcelList">
			<tr>
				<td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="clExcelList" scope="request"> 					
				<logic:iterate id="row" name="clExcelList" indexId="recordId">
					<tr >
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="locationId"/></td>
						<td align="left" style="font-size:9px" width="10%"><bean:write  name="row" property="locationName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customerName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="telephonePhno"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="email_Id"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fax"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address1"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address2"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address3"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address4"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cityName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="stateName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="postalCode"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="countryName"/></td>
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>
<logic:present name="contactExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=ContactReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="17" align="left" style="font-size:10px"><strong>CONTACTS REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Id </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Name </b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order No </b></td>	
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Contact Id</b></td>							
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Contact Type</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Salutation</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Contact Name</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255" height="30px"><b>Telephone Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>E-Mail</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Fax</b></td>							
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Address1</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Address2</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Address3</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>City</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>State</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Postal Code</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Country</b></td>
		</tr>

		<logic:empty name="contactExcelList">
			<tr>
				<td colspan="17" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="contactExcelList" scope="request"> 					
				<logic:iterate id="row" name="contactExcelList" indexId="recordId">
					<tr >
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountID"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contactId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contactType"/></td>
								<td align="left" style="font-size:9px" width="10%"><bean:write  name="row" property="saluation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contactName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contactCell"/></td>														
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cntEmail"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contactFax"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="address1"/></td>
								<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address2"/></td>
								<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address3"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cityName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="stateName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pinNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="countyName"/></td>
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>
<logic:present name="dispatchAddressExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=DispatchAddressReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="17" align="left" style="font-size:10px"><strong>DISPATCH ADDRESS REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Account Id </b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Account Name </b></td>							
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Dispatcher Code</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Dispatcher Address</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255" height="30px"><b>Telephone Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>E-Mail</b></td>						
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Address1</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Address2</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Address3</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Address4</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Fax</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Pin</b></td>	
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Title</b></td>						
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Postal Code</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Country</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>State</b></td>	
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>City</b></td>
		</tr>

		<logic:empty name="dispatchAddressExcelList">
			<tr>
				<td colspan="17" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="dispatchAddressExcelList" scope="request"> 					
				<logic:iterate id="row" name="dispatchAddressExcelList" indexId="recordId">
					<tr >
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dispatchAddressId"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dispatchAddressName"/></td>											
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="telephonePhno"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="email_Id"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address1"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address2"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address3"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address4"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fax"/></td>
						<td align="left" style="font-size:9px" width="10%"><bean:write  name="row" property="title"/></td>
						<td align="left" style="font-size:9px" width="10%"><bean:write  name="row" property="pin"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="postalCode"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="countryName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="stateName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cityName"/></td>
					
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>
<logic:present name="orderExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=OrderStatusReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="8" align="left" style="font-size:10px"><strong>ORDER STATUS REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Account Id </b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Account Name </b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order No </b></td>	
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Status</b></td>							
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Order Date</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Source Name</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255"><b>Quote Number</b></td>
					<td align="center" style="font-size:9px"  bgcolor="#FF9255" ><b>Currency</b></td>
		</tr>

		<logic:empty name="orderExcelList">
			<tr>
				<td colspan="8" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="orderExcelList" scope="request"> 					
				<logic:iterate id="row" name="orderExcelList" indexId="recordId">
					<tr >
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
						<td align="left" style="font-size:9px" width="10%"><bean:write  name="row" property="stageName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="sourceName"/></td>														
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="quoteNo"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyName"/></td>
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

 

<logic:present name="masterHistoryList" scope="request">
<logic:equal value="1" name="reportsBean" property="masterValue">
<% response.setHeader("content-Disposition","attachment;filename=ProductConfiguratorHistory.xls"); %>

</logic:equal>
<logic:equal value="2" name="reportsBean" property="masterValue">
<% response.setHeader("content-Disposition","attachment;filename=ChangeTypeMasterHistory.xls"); %>
</logic:equal>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<logic:equal value="1" name="reportsBean" property="masterValue">
		<tr>
			<td colspan="8" align="left" style="font-size:10px"><strong>Product Configurator History</strong></td>
		</tr>
		</logic:equal>
		<logic:equal value="2" name="reportsBean" property="masterValue">
		<tr>
			<td colspan="8" align="left" style="font-size:10px"><strong>Change Type History</strong></td>
		</tr>
		</logic:equal>
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Master Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Column Name </b></td>							
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Old Values</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>New Values</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Operation Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Modified Date </b></td>							
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Modified By</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Attribute Id</b></td>
					
		</tr>

		<logic:empty name="masterHistoryList">
			<tr>
				<td colspan="8" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="masterHistoryList" scope="request"> 					
				<logic:iterate id="row" name="masterHistoryList" indexId="recordId">
					<tr >
						<logic:equal value="TSERVICETYPEDETAIL" name="row" property="masterName">
						<td align="left" style="font-size:9px" width="5%">Product Configurator Master</td>
						</logic:equal>
						<logic:equal value="TCHANGETYPE_MASTER" name="row" property="masterName">
						<td align="left" style="font-size:9px" width="5%">Change Type Master</td>
						</logic:equal>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="columnName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="oldValues"/></td>
						<td align="left" style="font-size:9px" width="10%"><bean:write  name="row" property="newValues"/></td>
						<logic:equal value="U" property="operationName" name="row">
						<td align="left" style="font-size:9px" width="5%">Updated</td>
						</logic:equal>
						<logic:equal value="I" property="operationName" name="row">
						<td align="left" style="font-size:9px" width="5%">Inserted</td>
						</logic:equal>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="modifiedDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="modifiedBy"/></td>
						<td align="left" style="font-size:9px" width="10%"><bean:write  name="row" property="attribiuteId"/></td>
						
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
			
	</table>
</logic:present>

<logic:present name="pendingServicesReportExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=pendingServicesReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="14" align="left" style="font-size:10px"><strong>PENDING SERVICES REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Manager</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Creation Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>AM Approved Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>AM Approved By</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Project Manager</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PM Approved Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PM Approved By</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Final Approval Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>COPC Approved By</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td>					
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Approval Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Type</b></td>				
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>M6 Order Date</b></td>	
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Publish Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Effective Start Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Stage</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Circuit Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Zone</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Standard Reason</b></td>			
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical SI Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No</b></td>		
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service List Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Id</b></td>				
		</tr>
		<logic:empty name="pendingServicesReportExcelList">
			<tr>
				<td colspan="14" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="pendingServicesReportExcelList" scope="request"> 					
				<logic:iterate id="row" name="pendingServicesReportExcelList" indexId="recordId">
					<tr>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="party_no"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountNoString"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountManager"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="amApproveDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="amName"/></td>								
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="projectManager"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmApproveDate"/></td>								
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderStage"/></td>								
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6OrderDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingStatus"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="publishedDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="effStartDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderStatus"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceStage"/></td>								
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="circuitStatus"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="zoneName"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="standardReason"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalSINo"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceIdString"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountId"/></td>
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>
<logic:present name="nonMigAppUnappNewOrderDetailsExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=NonMigratedAPP_UNAPPNewOrderDetails.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="14" align="left" style="font-size:10px"><strong>NON MIGRATED APPROVED AND UNAPPROVED NEW ORDER DETAILS</strong></td>
		</tr>
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
		<logic:empty name="nonMigAppUnappNewOrderDetailsExcelList">
			<tr>
				<td colspan="14" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="nonMigAppUnappNewOrderDetailsExcelList" scope="request"> 					
				<logic:iterate id="row" name="nonMigAppUnappNewOrderDetailsExcelList" indexId="recordId">
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
							<td align="left" style="font-size:9px" width="5%" <bean:write  name="row" property="totalPoAmt"/>></td><!--Total Amount-->
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
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>
<logic:present name="nonAppAppChangeOrderDetailsExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=NonAPP_APPChangeOrderDetails.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="14" align="left" style="font-size:10px"><strong>NON APPROVED AND APPROVED CHANGE ORDER DETAILS</strong></td>
		</tr>
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
		<logic:empty name="nonAppAppChangeOrderDetailsExcelList">
			<tr>
				<td colspan="14" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="nonAppAppChangeOrderDetailsExcelList" scope="request"> 					
				<logic:iterate id="row" name="nonAppAppChangeOrderDetailsExcelList" indexId="recordId">
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
							<td align="left" style="font-size:9px" width="5%" <bean:write  name="row" property="totalPoAmt"/>></td><!--Total Amount-->
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
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>
<logic:present name="startChargeNotPushedInFxExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=StartChargeNotPushedFx.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="14" align="left" style="font-size:10px"><strong>START CHARGE NOT PUSHED IN FX</strong></td>
		</tr>
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

		<logic:empty name="startChargeNotPushedInFxExcelList">
			<tr>
				<td colspan="14" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="startChargeNotPushedInFxExcelList" scope="request"> 					
				<logic:iterate id="row" name="startChargeNotPushedInFxExcelList" indexId="recordId">
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
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>




<logic:present name="logicalSIDataReportExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=LogicalSIDataReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="14" align="left" style="font-size:10px"><strong>LOGICAL SI DATA REPORT</strong></td>
		</tr>
		<tr align="Center">
		<!--  Saurabh : change to show data for charges -->		
		<logic:equal name="isUsage" value="0">	
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Record Status</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical Si Number</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Parent Line Name</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Name</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Type</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Name</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Status</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contract Period Mnths</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Total Amount</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Frequency</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Frequency Amt</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Start Date Logic</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Start Date Months</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Start Date Days</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trigger Date</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>End Date Logic</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>End Date Months</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>End Date Days</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge End Date</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Annual Rate</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Trai Rate</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Discount</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Advance</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Installment Rate</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dnd Dispatch But Not Delivered</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dnd Dispatch And Delivered</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Ddni Disp Del Not Installed</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Ddni Disp Delivered Installed</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Valid Exclude</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Number</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Tot Po Amt</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Legal Entity</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Date</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Contract Period</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contract Start Date</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contract End Date</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Receive Date</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Id</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Line Id</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service No</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Pk Charges Id</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>M6 Product Id</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Parent Product Id</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Charge Hdr Id</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Ib Pk Charges Id</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Ib Order Line Id</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>M6 Order Id</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Line Si No</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Annotation</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Remarks</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Credit Period</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Type</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Format</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Taxation</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>License Co</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Country</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address1</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address2</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address3</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address4</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>City</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Postal Code</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>State</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Active End Date</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contact Person Name</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Person Designation</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Person Mobile</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Person Email</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Person Fax</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Lst No</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Lst Date</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Address Type</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Attribute Name</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Attribute Value</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Store</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Hardware Type</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Nature Sale</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Form Available</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Type Of Sale</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Principle Amt</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Interest Rate</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Warranty Start Dt Logic</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Warranty Period Months</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Warranty Period Days</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Warranty Start Date</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Warranty End Date Logic</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Warranty End Period Months</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Warranty End Period Days</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Warranty End Date</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Extnd Support Period Months</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Extnd Support Period Days</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Extnd Support End Date</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dispatch Address1</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dispatch Address2</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dispatch Address3</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dispatch City</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dispatch Postal Code</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dispatch State</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dispatch Conact Person Name</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dispatch Contact Person Mobile</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dispatch Lst Number</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dispatch Lst Date</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Dispatch Address Type</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>New Service List Id</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>New Crm Order Id</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Remrks</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Level</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Commitment Period</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Notice Period</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Circuit Id</b></td>
		</logic:equal>
		<!-- Saurabh : End -->
		<!--  Manisha : change for usage -->		
		<logic:equal name="isUsage" value="1">
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical Si Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Parent Line Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contract Period Mnths</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Total Amount</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Trigger Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Tot Po Amt</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Legal Entity</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Contract Period</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contract Start Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contract End Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cust Po Receive Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Line Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service No</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>M6 Order Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Remarks</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Credit Period</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Bill Format</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Taxation</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>License Co</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Country</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address1</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address2</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address3</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Address4</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>City</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Postal Code</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>State</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Contact Person Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Person Designation</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Person Mobile</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Person Email</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Person Fax</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Remarks</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Level</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Commitment Period</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Notice Period</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Circuit Id</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Child Account Number</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Child Account No Internal</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Child Account Fx Status</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Package ID</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Package Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component ID</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Name</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component InfoID</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Type</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>rental / nrc component amount</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Status</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Start Logic</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Start Date Days</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Start Date Months</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>End Date Logic</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>End Date Days</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>End Date Months</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Start Date</b></td>
			<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component End Date</b></td>
		</logic:equal>
		<!-- Manisha : End -->	
		</tr>
		<logic:empty name="logicalSIDataReportExcelList">
			<tr>
				<td colspan="14" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="logicalSIDataReportExcelList" scope="request"> 					
				<logic:iterate id="row" name="logicalSIDataReportExcelList" indexId="recordId">
					<tr >
					<!--  Saurabh : change to show data for charges -->		
					<logic:equal name="isUsage" value="0">
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderNumber"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="recordStatus"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="logicalSINumber"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="parent_name"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceDetDescription"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeTypeName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="charge_status"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="totalPoAmt"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="frequencyName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="frequencyAmt"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="startDateLogic"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="startDateMonth"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="startDateDays"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingTriggerDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="endDateLogic"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="endDateMonth"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="endDateDays"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="annual_rate"/></td>
							<td align="left" style="font-size:9px" width="5%" ></td><!-- traiRate -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- discount -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- advance -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- installmentRate -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dnd_Dispatch_But_Not_Delivered"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dnd_Dispatch_And_Delivered"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dnd_Disp_Del_Not_Installed"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dnd_Disp_Delivered_Installed"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="poExclude"/></td><!-- poValidExclude -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custPoDetailNo"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="poAmount"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="entity"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custPoDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractStartDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="poRecieveDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeinfoID"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderLineNumber"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceId"/></td>
							<td align="left" style="font-size:9px" width="5%" ></td><!-- pkChargesId-->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- m6ProductId -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- parentProductId -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingInfoID"/></td><!-- chargeHdrId -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- ibPkChargesId -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- ibOrderLineId -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- m6orderId -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- orderLineSiNo -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeAnnotation"/></td>
							<td align="left" style="font-size:9px" width="5%" ></td><!-- remarks -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="creditPeriodName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingTypeName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingFormatName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="taxation"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="lcompanyname"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="countyName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="address1"/></td><!-- address1 -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="address2"/></td><!-- address2 -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="address3"/></td><!-- address3 -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="address4"/></td><!-- address4 -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="cityName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="postalCode"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="stateName"/></td>
							<td align="left" style="font-size:9px" width="5%" ></td><!-- activeEndDate -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contactName"/></td><!-- contarPersonName -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="designation"/></td><!-- personDesignation -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="telePhoneNo"/></td><!-- personMobile -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="emailId"/></td><!-- personEmail -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="fax"/></td><!-- personFax -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="lst_No"/></td><!-- lstNo-->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="lstDate"/></td><!-- lstDate -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- billingAddressType -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="attributeLabel"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="attributeValue"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="storeName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="hardwaretypeName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="saleNature"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="formAvailable"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="saleType"/></td>
							<td align="left" style="font-size:9px" width="5%" ></td><!-- principalAmt -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- intrestRate -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="warrantyStartDateLogic"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="warrantyPeriodMonths"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="warrantyPeriodDays"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="warrantyStartDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="warrantyEndDateLogic"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="warrantyEndPeriodMonths"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="warrantyEndPeriodDays"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="warrantyEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="extndSupportPeriodMonths"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="extndSupportPeriodDays"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="extSuportEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchAddress1"/></td><!-- dispatchAddress1 -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchAddress2"/></td><!-- dispatchAddress2 -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchAddress3"/></td><!-- dispatchAddress3 -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchCityName"/></td><!-- dispatchCity -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchPostalCode"/></td><!-- dispatchPostalCode -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchStateName"/></td><!-- dispatchState -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchPersonName"/></td><!-- dispatchContactPersonName -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchPhoneNo"/></td><!-- dispatchContactPersonMobile -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchLstNumber"/></td><!-- dispatchLstNumber -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchLstDate"/></td><!-- dispatchLstDate -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- DispatchAddressType -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- NewServiceListId -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- NewCrmOrderId -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- remarks -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingLevel"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="commitmentPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="noticePeriod"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="logicalCircuitId"/></td>
					</logic:equal>
					<!-- Saurabh : End -->
					<!--  Manisha : changes for Usage -->
					<logic:equal name="isUsage" value="1">
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderNumber"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="logicalSINumber"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceName"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="parent_name"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceDetDescription"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractPeriod"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="totalPoAmt"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingTriggerDate"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custPoDetailNo"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="poAmount"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="entity"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custPoDate"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractPeriod"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractStartDate"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractEndDate"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="poRecieveDate"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderLineNumber"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceId"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="m6OrderNo"/></td><!-- m6orderId -->
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="neworder_remarks"/></td><!-- remarks -->
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="creditPeriodName"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingTypeName"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingFormatName"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="taxation"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="lcompanyname"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="countyName"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="address1"/></td><!-- address1 -->
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="address2"/></td><!-- address2 -->
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="address3"/></td><!-- address3 -->
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="address4"/></td><!-- address4 -->
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="cityName"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="postalCode"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="stateName"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contactName"/></td><!-- contarPersonName -->
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="designation"/></td><!-- personDesignation -->
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="telePhoneNo"/></td><!-- personMobile -->
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="emailId"/></td><!-- personEmail -->
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="fax"/></td><!-- personFax -->
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="disconnection_remarks"/></td></td><!-- remarks -->
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingLevel"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="commitmentPeriod"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="noticePeriod"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="logicalCircuitId"/></td>
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
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startLogic"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startDateDays"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startDateMonth"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endLogic"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endDateDays"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endDateMonth"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startDate"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.end_date"/></td>
					</logic:equal>
					<!--  Manisha : End -->
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
			<!--  Manisha : change for usage -->		
			<logic:equal name="isUsage" value="1">
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>ComponentInfoID</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component ID</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Component Name</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Package ID</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Package Name</b></td>
			</logic:equal>
			<!-- Manisha : End -->	
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
					<!--  Manisha : changes for Usage -->
					<logic:equal name="isUsage" value="1">
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentInfoID"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentID"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentName"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageID"/></td>
						<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageName"/></td>
					</logic:equal>
					<!--  Manisha : End -->
				</tr>
			</logic:iterate>		
		</logic:notEmpty>
	</table>
</logic:present>
<logic:present name="pendingOrderBillingExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=PendingOrderBilling.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="11" align="left" style="font-size:10px"><strong>Pending Order Billing and Hardware Reports</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Vertical  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Segment  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Category  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region Name  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>A/c Manager Name  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Project Manager Name  </b></td>
		            <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Number  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Copc Approve Date  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Crm Order id  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Number  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Name  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical Circuit Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Factory Ckt Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Item Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Line Item Amount</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Billing Charge Start Date  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Challen No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Challen Date  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type  </b></td>							
						
		</tr>

		<logic:empty name="pendingOrderBillingExcelList">
			<tr>
				<td colspan="11" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="pendingOrderBillingExcelList" scope="request"> 					
				<logic:iterate id="row" name="pendingOrderBillingExcelList" indexId="recordId">
					<tr >
						       
						        <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="verticalDetails"/></td>
                                <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cus_segment"/></td>
                                <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="act_category"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="regionName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="actmngname"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="prjmngname"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountID"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="copcApproveDate"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderNo"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poNumber"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="logicalCircuitId"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="factory_ckt_id"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="linename"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeAmount"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="startDate"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="challenno"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="challendate"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderType"/></td>
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

<logic:present name="universalExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=UniversalWorkqueueReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="15" align="left" style="font-size:10px"><strong>UNIVERSAL WORKQUEUE REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CRM Order No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CRM Account No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Source Name</b></td>							
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Quote Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Stage</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Task No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Task Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Industry Segment</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>AM Approval Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PM Approval Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>COPC Approval Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Segment</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Subchange Type</b></td>
		</tr>

		<logic:empty name="universalExcelList">
			<tr>
				<td colspan="15" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="universalExcelList" scope="request"> 					
				<logic:iterate id="row" name="universalExcelList" indexId="recordId">
					<tr >
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="searchCRMOrder"/></td>
						<td align="left" style="font-size:9px" width="10%"><bean:write  name="row" property="searchOrderType"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="searchAccountNo"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="searchAccountName"/></td>														
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="searchfromDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="searchSource"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="searchQuoteNumber"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="regionName"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="orderStage"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="demoType"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="taskNo"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="taskStatus"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyNo"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="industrySegment"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="amApprovalDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmApprovalDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApprovalDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customerSegment"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subChangeType"/></td>
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

<logic:present name="billingExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=BillingWorkqueueReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="15" align="left" style="font-size:10px"><strong>BILLING WORKQUEUE REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CRM Order No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CRM Account No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Source Name</b></td>							
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Quote Number</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Region Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Stage</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Task No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Task Status</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Party No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Industry Segment</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>AM Approval Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>PM Approval Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>COPC Approval Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer Segment</b></td>
		</tr>

		<logic:empty name="billingExcelList">
			<tr>
				<td colspan="15" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="billingExcelList" scope="request"> 					
				<logic:iterate id="row" name="billingExcelList" indexId="recordId">
					<tr >
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="searchCRMOrder"/></td>
						<td align="left" style="font-size:9px" width="10%"><bean:write  name="row" property="searchOrderType"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="searchfromDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="searchAccountNo"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="searchAccountName"/></td>														
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="searchSource"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="searchQuoteNumber"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="regionName"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="orderStage"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="demoType"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="taskNo"/></td>
						<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="taskStatus"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyNo"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="industrySegment"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="amApprovalDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmApprovalDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApprovalDate"/></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customerSegment"/></td>
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>


<logic:present name="M6orderCancelReportExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=M6OrderCancelReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="16" align="left" style="font-size:10px"><strong>M6 ORDER CANCEL REPORT</strong></td>
		</tr>
		       <tr align="Center">
					<tr>
					
						
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service NO</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Creation Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Effective Start Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Customer RFS Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Product</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Sub Product</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Service Stage </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical SI NO</b></td>
				   <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cancel Reason </b></td>
				   <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Cancel Date</b></td>
				      <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>BI Source </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo Order </b></td>

		</tr>

		<logic:empty name="M6orderCancelReportExcel">
			<tr>
				<td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="M6orderCancelReportExcel" scope="request"> 					
				<logic:iterate id="row" name="M6orderCancelReportExcel" indexId="recordId">
					<tr >
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="createdDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="effDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="rfs_date"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="productName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subProductName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceStage"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalSINo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cancelServiceReason"/></td>
								<td align="left" style="font-size:9px" width="10%"><bean:write name="row" property="canceldate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bisource"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ordertype_demo"/></td>
								
								
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>

<!-- lawkush Start -->


<logic:present name="zeroOrderValueReportListExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=ZeroOrderValueReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="22" align="left" style="font-size:10px"><strong>ZERO VALUE ORDER REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px"><b>Party Name</b></td>
					<td align="center" style="font-size:9px"><b>Order No</b></td>
					<td align="center" style="font-size:9px"><b>Po Id</b></td>		
					<td align="center" style="font-size:9px"><b>Cust Account Id</b></td>
					<td align="center" style="font-size:9px"><b>Legal Entity Code</b></td>
					<td align="center" style="font-size:9px"><b>Cust Po Number</b></td>	
					<td align="center" style="font-size:9px"><b>Cust Po Date</b></td>		
					<td align="center" style="font-size:9px"><b>Tot Po Amt</b></td>
					<td align="center" style="font-size:9px"><b>Po Payment Terms</b></td>
					<td align="center" style="font-size:9px"><b>Contract Start Date</b></td>
				 	<td align="center" style="font-size:9px"><b>Contract End Date</b></td>  
					<td align="center" style="font-size:9px"><b>Contract Period Mnths</b></td>
					<td align="center" style="font-size:9px"><b>Cust Po Receive Date</b></td>
					<td align="center" style="font-size:9px"><b>Po Issuing Person Name</b></td>		
					<td align="center" style="font-size:9px"><b>Po Issuing Person Email</b></td>
					<td align="center" style="font-size:9px"><b>Last Update Date</b></td>		
					<td align="center" style="font-size:9px"><b>Last Updated By</b></td>
					<td align="center" style="font-size:9px"><b>Creation Date</b></td>		
					<td align="center" style="font-size:9px"><b>Created By</b></td>
					<td align="center" style="font-size:9px"><b>Account Number</b></td>
					<td align="center" style="font-size:9px"><b>Default Flag</b></td>
					<td align="center" style="font-size:9px"><b>Po End Date</b></td>					
					<td align="center" style="font-size:9px"><b>Party Id</b></td>
					<td align="center" style="font-size:9px"><b>Bl Source</b></td>
					<td align="center" style="font-size:9px"><b>Demo Contract Period</b></td>		
					<td align="center" style="font-size:9px"><b>Logical Si Number</b></td>
					<td align="center" style="font-size:9px"><b>Service List Id</b></td>
					<td align="center" style="font-size:9px"><b>Account Mgr</b></td>	
					<td align="center" style="font-size:9px"><b>Location From</b></td>		
					<td align="center" style="font-size:9px"><b>Location To</b></td>
					<td align="center" style="font-size:9px"><b>Line Item Number</b></td>
					<td align="center" style="font-size:9px"><b>Token No</b></td>
				  	<td align="center" style="font-size:9px"><b>Region Name</b></td>  
					<td align="center" style="font-size:9px"><b>Si Id</b></td>
					<td align="center" style="font-size:9px"><b>M6 Order Id</b></td>
					<td align="center" style="font-size:9px"><b>Annotation</b></td>
					<td align="center" style="font-size:9px"><b>Demo Flag</b></td>		
					<td align="center" style="font-size:9px"><b>Billing Bandwidth</b></td>	
					<td align="center" style="font-size:9px"><b>Uom</b></td>
		</tr>

		<logic:empty name="zeroOrderValueReportListExcel">
			<tr>
				<td colspan="22" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="zeroOrderValueReportListExcel" scope="request"> 					
				<logic:iterate id="row" name="zeroOrderValueReportListExcel" indexId="recordId">
					<tr >
								
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poDetailNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountID"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="entityId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDetailNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poAmounts"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="paymentTerm"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractStartDate"/></td>
    							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractEndDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poRecieveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poIssueBy"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="poEmailId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lastUpdatedDate"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="lastUpdatedBy"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="createdDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="createdBy"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmACcountNO"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="isDefaultPO"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poEndDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="sourceName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poDemoContractPeriod"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalSINo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountManager"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fromLocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="toLocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lineItemDescription"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="tokenNO"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td> 
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fxSiId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6OrderNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAnnotation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="order_type"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingBandWidth"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="uom"/></td>
							</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>


<!-- lawkush End -->
<!-- lawkush Start -->


<logic:present name="rateRenewalReportListExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=RateRenewalReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="22" align="left" style="font-size:10px"><strong>RATE RENEWAL REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px"><b>Party Number</b></td>
					<td align="center" style="font-size:9px"><b>Party Name</b></td>
					<td align="center" style="font-size:9px"><b>Account No</b></td>		
					<td align="center" style="font-size:9px"><b>Account Mgr</b></td>
					<td align="center" style="font-size:9px"><b>Customer Segment</b></td>
					<td align="center" style="font-size:9px"><b>Service Segment</b></td>	
					<td align="center" style="font-size:9px"><b>Vertical</b></td>		
					<td align="center" style="font-size:9px"><b>Region</b></td>
					<td align="center" style="font-size:9px"><b>Service Type</b></td>
					<td align="center" style="font-size:9px"><b>Billling Bandwidth</b></td>
				 	<td align="center" style="font-size:9px"><b>Bill Uom</b></td>  
					<td align="center" style="font-size:9px"><b>Catagory Of Order</b></td>
					<td align="center" style="font-size:9px"><b>Ordertype</b></td>
					<td align="center" style="font-size:9px"><b>Company Name</b></td>		
					<td align="center" style="font-size:9px"><b>Creation Date</b></td>
					<td align="center" style="font-size:9px"><b>Currency</b></td>
					<td align="center" style="font-size:9px"><b>Po Date</b></td>	
					<td align="center" style="font-size:9px"><b>Po Number</b></td>		
					<td align="center" style="font-size:9px"><b>Charge Name</b></td>
					<td align="center" style="font-size:9px"><b>Charge Type</b></td>
					<td align="center" style="font-size:9px"><b>From Site</b></td>
					<td align="center" style="font-size:9px"><b>To Site</b></td>
					<td align="center" style="font-size:9px"><b>Kms Distance</b></td>
					<td align="center" style="font-size:9px"><b>Line Item Description</b></td>
					<td align="center" style="font-size:9px"><b>Loc Date</b></td>
					<td align="center" style="font-size:9px"><b>Copc Approved Date</b></td>		
					<td align="center" style="font-size:9px"><b>Circuit Id</b></td>	
					<td align="center" style="font-size:9px"><b>Payment Term</b></td>
					<td align="center" style="font-size:9px"><b>Taxation</b></td>
					<td align="center" style="font-size:9px"><b>Licence Company</b></td>	
					<td align="center" style="font-size:9px"><b>Demo Type</b></td>		
					<td align="center" style="font-size:9px"><b>Service Stage</b></td>
					<td align="center" style="font-size:9px"><b>Order Stage Description</b></td>
					<td align="center" style="font-size:9px"><b>New Order Remark</b></td>
				  	<td align="center" style="font-size:9px"><b>Remarks</b></td>  
					<td align="center" style="font-size:9px"><b>Product</b></td>
					<td align="center" style="font-size:9px"><b>Sub Product</b></td>
					<td align="center" style="font-size:9px"><b>Billing Trigger Date</b></td>
					<td align="center" style="font-size:9px"><b>Billing Trigger Flag</b></td>		
					<td align="center" style="font-size:9px"><b>Line Name</b></td>	
					<td align="center" style="font-size:9px"><b>Charge Start Date</b></td>
					<td align="center" style="font-size:9px"><b>Charge End Date</b></td>
					<td align="center" style="font-size:9px"><b>End Date Logic</b></td>
					<td align="center" style="font-size:9px"><b>Charge Status</b></td>
					<td align="center" style="font-size:9px"><b>Charge Start Days Logic</b></td>
					<td align="center" style="font-size:9px"><b>Charge Start Months Logic</b></td>		
					<td align="center" style="font-size:9px"><b>Zone</b></td>
					<td align="center" style="font-size:9px"><b>Name</b></td>
					<td align="center" style="font-size:9px"><b>Po Amount</b></td>	
					<td align="center" style="font-size:9px"><b>Contract Period</b></td>
					<td align="center" style="font-size:9px"><b>Charge Period Months</b></td>
					<td align="center" style="font-size:9px"><b>Item Quantity</b></td>
					<td align="center" style="font-size:9px"><b>Order Total</b></td>
					<td align="center" style="font-size:9px"><b>Service No</b></td>
					<td align="center" style="font-size:9px"><b>M6 Order Id</b></td>
					<td align="center" style="font-size:9px"><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px"><b>M6 Ckt Dtls Id</b></td>		
					<td align="center" style="font-size:9px"><b>Annual Rate</b></td>	
					<td align="center" style="font-size:9px"><b>Order Line Number</b></td>
					<td align="center" style="font-size:9px"><b>Order No</b></td>
					<td align="center" style="font-size:9px"><b>Last  Order No</b></td>
					<td align="center" style="font-size:9px"><b>Line Item Amount</b></td>	
					<td align="center" style="font-size:9px"><b>Old Line item amount</b></td>
					<td align="center" style="font-size:9px"><b>Start Date Days</b></td>
					<td align="center" style="font-size:9px"><b>Start Date Months</b></td>
					
				</tr>

		<logic:empty name="rateRenewalReportListExcel">
			<tr>
				<td colspan="22" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="rateRenewalReportListExcel" scope="request"> 					
				<logic:iterate id="row" name="rateRenewalReportListExcel" indexId="recordId">
					<tr >
								
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmACcountNO"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountManager"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cus_segment"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceSegment"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceDetDescription"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_bandwidth"/></td>
    							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_uom"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="changeTypeName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="companyName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="createdDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyCode"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDetailNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeTypeName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fromLocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="toLocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="distance"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lineItemDescription"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_Date"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalCircuitNumber"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="paymentTerm"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taxationName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lcompanyname"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="order_type"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceStage"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="stageName"/></td> 
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="newOrderRemark"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="remarks"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="productName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subProductName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_trigger_date"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTriggerFlag"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="linename"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endHWDateLogic"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="charge_status"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDaysLogic"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="startMonthsLogic"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="zoneName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="salesCoordinator"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poAmounts"/></td>
						  		<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargePeriod"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="itemQuantity"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderTotal"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6OrderNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalSINo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6cktid"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="annual_rate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceProductID"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="oldOrderNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAmount"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="oldLineitemAmount"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="txtStartDays"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="txtStartMonth"/></td>
							</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>


<!-- lawkush End -->




<logic:present name="RestPendingLineReportInExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=RESTPENDINGLINEREPORT.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="16" align="left" style="font-size:10px"><strong>REST PENDING LINE REPORT</strong></td>
		</tr>
		<tr align="Center">
					<tr>
					
						
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Account No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Party Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Prj Manager </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Account Manager  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Order Date  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Dispatch Address  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Charge Start Date  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Po Date</b></td>
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
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b> 	Crm Order Id
					
						</b></td>
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

		<logic:empty name="RestPendingLineReportInExcel">
			<tr>
				<td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="RestPendingLineReportInExcel" scope="request"> 					
				<logic:iterate id="row" name="RestPendingLineReportInExcel" indexId="recordId">
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
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeinfoID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="annual_rate"/></td>
								
					</tr>
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
					<tr>
					
						
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


		</tr>

		<logic:empty name="DisconnectionLineReportInExcel">
			<tr>
				<td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="DisconnectionLineReportInExcel" scope="request"> 					
				<logic:iterate id="row" name="DisconnectionLineReportInExcel" indexId="recordId">
					<tr >
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
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="seclocation"/></td>`
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
					<tr>
					
						
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Account No </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Party Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Vertical </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Region </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b> Order No
					
						</b></td>
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


		</tr>

		<logic:empty name="CancelledFailedLineReportInExcel">
			<tr>
				<td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="CancelledFailedLineReportInExcel" scope="request"> 					
				<logic:iterate id="row" name="CancelledFailedLineReportInExcel" indexId="recordId">
					<tr >
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
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="seclocation"/></td>`
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
								
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>


<logic:present name="AttributeDetailsReportInExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=ATTRIBUTEDETAILSREPORT.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="16" align="left" style="font-size:10px"><strong>Attribute Details Report</strong></td>
		</tr>
		<tr align="Center">
				<tr>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Service Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Line Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Crm Attributes </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>M6 Label Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>M6 Label Value  </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Cust Logical SI No  </b></td>
				    <td align="center" style="font-size:9px" bgcolor="#FF9255"  ><b>Last Crm Order Id</b></td>


		</tr>

		<logic:empty name="AttributeDetailsReportInExcel">
			<tr>
				<td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="AttributeDetailsReportInExcel" scope="request"> 					
				<logic:iterate id="row" name="AttributeDetailsReportInExcel" indexId="recordId">
				<tr>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="linename"/></td>
                        <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="crm_att"/></td>
                        <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6_label_name"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6_label_value"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custLogicalSI"/></td>
				        <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="crm_order_id"/></td>
				     
						
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>


<logic:present name="BulkSIUploadReportInExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=BULKSIUPLOADREPORT.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="16" align="left" style="font-size:10px"><strong>Bulk SI Upload Report</strong></td>
		</tr>
		<tr align="Center">
				<tr>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Accoun Name </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b> Crm Order Mocn No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Crm Service Opms Id</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Services </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Installation Address A1</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Installation Address A2 </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Installation Address A3</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>From City </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>From State</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>From Country </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Installation Address B1</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Installation Address B2 </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Installation Address B3</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b> To City</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>To State</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>To Country </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Date Of Installation</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Date Of Activation </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Bandwidth Value</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>UOM </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Parent Product</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Parent Circuit </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>LOB</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Circle </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Zone</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Support Location A </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Support Location B</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Commited SLA </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>HUB Location</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Platform </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Logical SI No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>SI Id </b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>IPLC</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Managed Yes No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Act Mgr</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Prj Mgr</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>TL</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255" ><b>Service Provider</b></td>


		</tr>

		<logic:empty name="BulkSIUploadReportInExcel">
			<tr>
				<td colspan="16" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="BulkSIUploadReportInExcel" scope="request"> 					
				<logic:iterate id="row" name="BulkSIUploadReportInExcel" indexId="recordId">
				<tr>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="crm_service_opms_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="installation_addressaa1"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="installation_addressaa2"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="installation_addressaa3"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="from_city"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="from_state"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="from_country"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="installation_addressab1"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="installation_addressab2"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="installation_addressab3"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="to_city"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="to_state"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="to_country"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="date_of_inst"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="date_of_act"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bandwaidth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="uom"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="parent_name"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="parent_circuit"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lob"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="circle"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="zone"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_from"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_to"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="commited_sla"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="hub_location"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="platform"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="si_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ipls"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="managed_yes_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="actmngname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="prjmngname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="tl"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="service_provider"/></td>
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>


</body>
</html:html>

