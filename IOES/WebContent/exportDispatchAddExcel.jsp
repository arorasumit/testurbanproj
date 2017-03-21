<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<link rel="stylesheet" type="text/css" href="gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">

<html:html>
<head>
<%
response.setContentType("application/vnd.ms-excel");
%>
</head>
<body>

<%
int nSNo = 0;
%>
			<%
						response.setHeader("content-Disposition",
						"attachment;filename=DispatchAddressDetailsDump.xls");
			%>
	<strong>Dispatch Details</strong>
	<table width="100%" border="1" cellpadding="0" cellspacing="0" id="tabViewDispatchDetailsId">
		<tr bgcolor="#FF9255" align="Center">
			<td align="left" nowrap>Dispatch Id</td>
			<td align="left" nowrap>Dispatch Name</td>
			<td align="left" nowrap>Address-1</td>
			<td align="left" nowrap>Address-2</td>
			<td align="left" nowrap>Address-3</td>
			<td align="left" nowrap>Address-4</td>
			<td align="left" nowrap>Contact No</td>
			<td align="left" nowrap>Email</td>
			<td align="left" nowrap>PinCode</td>
			<td align="left" nowrap>City</td>
			<td align="left" nowrap>State</td>
			<td align="left" nowrap>Country</td>
			<td align="left" nowrap>Lst No</td>
			<td align="left" nowrap>Designation</td>
		</tr>
		<logic:empty name="dispatchDetails">
			<tr>
				<td colspan="25" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td></tr>
		</logic:empty>
		<logic:notEmpty  name="dispatchDetails" scope="request"> 					
			<logic:iterate id="row" name="dispatchDetails" indexId="recordId">
				<tr>
					<td><bean:write  name="row" property="dispatchAddressID"/>
					<td><bean:write  name="row" property="dispatchAddressName"/>
					<td><bean:write  name="row" property="dispatchAddress1"/>
					<td><bean:write  name="row" property="dispatchAddress2"/>
					<td><bean:write  name="row" property="dispatchAddress3"/>
					<td><bean:write  name="row" property="dispatchAddress4"/>
					<td><bean:write  name="row" property="dispatchPhoneNo"/>
					<td><bean:write  name="row" property="dispatchEmail"/>
					<td><bean:write  name="row" property="dispatchPinNo"/>
					<td><bean:write  name="row" property="dispatchCityName"/>
					<td><bean:write  name="row" property="dispatchStateName"/>
					<td><bean:write  name="row" property="dispatchCountyName"/>
					<td><bean:write  name="row" property="dispatchLSTNo"/>
					<td><bean:write  name="row" property="dispatchDesignation"/>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
	</table>
</body>
</html:html>