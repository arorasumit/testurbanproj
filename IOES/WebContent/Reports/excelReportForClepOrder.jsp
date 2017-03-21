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

<logic:present name="orderClepExcelList" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=ClepOrderReport.xls"); %>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="17" align="left" style="font-size:10px"><strong>CLEP ORDER REPORT</strong></td>
		</tr>
		<tr align="Center">
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order No</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order type</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Stage</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Created Date</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account ID</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Account Name</b></td>
					<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>CRM Account No</b></td>
		</tr>

		<logic:empty name="orderClepExcelList">
			<tr>
				<td colspan="17" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td>
			</tr>
		</logic:empty>
			<logic:notEmpty  name="orderClepExcelList" scope="request"> 					
				<logic:iterate id="row" name="orderClepExcelList" indexId="recordId">
					<tr >
							  <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderType"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderStage"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6OrderDate"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountID"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="crmAccountNo"/></td>
							
					</tr>
				</logic:iterate>		
			</logic:notEmpty>
	</table>
</logic:present>
</body>
</html:html>

