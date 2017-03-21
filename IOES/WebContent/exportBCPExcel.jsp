<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<link rel="stylesheet" type="text/css" href="gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">

<%@page import="com.ibm.ioes.npd.hibernate.beans.TmWorkflowstage"%>
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
						"attachment;filename=BCPDetailsDump.xls");
			%>
	<strong>BCP Details</strong>
	<table width="100%" border="1" cellpadding="0" cellspacing="0" id="tabViewBcpDetailsId">
		<tr bgcolor="#FF9255" align="Center">
			<td align="left" nowrap>BCP Id</td>
			<td align="left" nowrap>BCP Name</td>
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
		</tr>
		<logic:empty name="bcpDetails">
			<tr>
				<td colspan="25" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td></tr>
		</logic:empty>
		<logic:notEmpty  name="bcpDetails" scope="request"> 					
			<logic:iterate id="row" name="bcpDetails" indexId="recordId">
				<tr>
					<td><bean:write  name="row" property="bcpID"/>
					<td><bean:write  name="row" property="bfirstname"/>
					<td><bean:write  name="row" property="baddress1"/>
					<td><bean:write  name="row" property="baddress2"/>
					<td><bean:write  name="row" property="baddress3"/>
					<td><bean:write  name="row" property="baddress4"/>
					<td><bean:write  name="row" property="bcontactNo"/>
					<td><bean:write  name="row" property="bemailID"/>
					<td><bean:write  name="row" property="bpincode"/>
					<td><bean:write  name="row" property="bcity"/>
					<td><bean:write  name="row" property="bstate"/>
					<td><bean:write  name="row" property="bcountry"/>
				</tr>
			</logic:iterate>
		</logic:notEmpty>
	</table>

</body>
</html:html>