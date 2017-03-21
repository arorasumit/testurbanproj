<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html:html>
<head>
<%
response.setContentType("application/vnd.ms-excel");
%>
</head>
var isUsage ="<%=request.getAttribute("isUsage")%>";
<body>
<logic:present name="orderReportNewListExcel" scope="request">
	<%
				response.setHeader("content-Disposition",
				"attachment;filename=DemoDaysDetails.xls");
	%>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr>
			<td colspan="25" align="left" style="font-size:10px"><strong>DEMO
			DAYS CHANGE IN EXCEL </strong></td>
		</tr>
		
			<tr>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Logical Si No
				</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>OrderNo
				</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Order Type
				</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>AccountNo
				</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>AccountName
				</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo Start Date
				</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo End Date
				</b></td>
				<td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Demo Days
				</b></td>
		
				<!--	shourya end --> 
				
			</tr>
		
		<logic:empty name="orderReportNewListExcel">
			<tr>
				<td colspan="25" align="center" style="font-size:10px;color:red;"><strong>No
				Records Found</strong></td>
			</tr>
		</logic:empty>
		<logic:notEmpty name="orderReportNewListExcel" scope="request">
			<logic:iterate id="row" name="orderReportNewListExcel"
				indexId="recordId">
					<tr>
						<td><bean:write name="row" property="logicalsi" /></td>
						<td><bean:write name="row" property="orderNo" /></td>
						<td><bean:write name="row" property="order_types" /></td>
						<td><bean:write name="row" property="crmAccountNo" /></td>
						<td><bean:write name="row" property="accountName" /></td>
						<td><bean:write name="row" property="demoStartDate" /></td>
						<td><bean:write name="row" property="demoEndDate" /></td>
						<td><bean:write name="row" property="demoDays" /></td>
						
					
					</tr>
				</logic:iterate>
		</logic:notEmpty>
	</table>
</logic:present>
</body>
</html:html>
