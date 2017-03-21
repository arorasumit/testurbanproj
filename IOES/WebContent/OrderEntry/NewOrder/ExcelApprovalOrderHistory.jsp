<%@page import="com.ibm.ioes.beans.ViewOrderDto"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%-- [226] Satish Kumar   28-Oct-2016  CSR-20160922-R1-022673  EportToExcelButton ON OrderRemovalHistory--%>
<html:html>
<head>
  <%response.setContentType("application/vnd.ms-excel");%>  <%--//for xls --%>
<%-- //response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");//for xlsx  --%>

</head>
<body>
	<%--[002] start--%>
	<logic:present name="OrderApprovalList" scope="request">
		 <%
			response.setHeader("content-Disposition",
							"attachment;filename=OrderApprovalReport.xls");
		%> 
		<table width="100%" border="1" cellpadding="0" cellspacing="0">
			<tr>
				<td width="8%" align="center" bgcolor="#FF9255"><b>Task Owner</b></td>
				<td align="center" bgcolor="#FF9255"><b>RoleName</b></td>
				<td width="5%" align="center" bgcolor="#FF9255"><b>EmployeeId</b></td>
				<td width="8%" align="center" bgcolor="#FF9255"><b>Action Taken by</b></td>
				<td width="5%" align="center" bgcolor="#FF9255"><b>Task Action</b></td>
				<td width="7%" align="center" bgcolor="#FF9255"><b>Action Date</b></td>
				<td width="9%" align="center" bgcolor="#FF9255"><b>Task Send To</b></td>
				<td width="6%" align="center" bgcolor="#FF9255"><b>Rejection Send to</b></td>
				<td width="9%" align="center" bgcolor="#FF9255"><b>Remarks</b></td>
				<!-- <td width="5%" align="center" bgcolor="#FF9255"><b>SelectService/M6OrderNo</b></td> -->
			    <td width="9%" align="center" bgcolor="#FF9255"><b>M6OrderNo</b></td>  
			     <td width="9%" align="center" bgcolor="#FF9255"><b>Service Id</b></td>
				<td width="7%" align="center" bgcolor="#FF9255"><b>Rejection Reason</b></td>
				<td width="9%" align="center" bgcolor="#FF9255"><b>Delay Reason</b></td>
			</tr>
			<logic:empty name="OrderApprovalList">
				<tr>
					<td colspan="13" align="center"
						style="font-size: 10px; color: red;"><strong>No Records Found</strong></td>
				</tr>
			</logic:empty>
			<logic:notEmpty name="OrderApprovalList" scope="request">
				<logic:iterate id="row" name="OrderApprovalList" indexId="recordId">
					<tr>
						<td><bean:write name="row" property="historyActionRoleName" />
						</td>
						<td>
							<%
								if ((((ViewOrderDto) row).getTaskOwnerId()) == 1) {
													out.println("AM");
												} else if ((((ViewOrderDto) row).getTaskOwnerId()) == 2) {
													out.println("PM");
												} else if ((((ViewOrderDto) row).getTaskOwnerId()) == 3) {
													out.println("copc");
												} else if ((((ViewOrderDto) row).getTaskOwnerId()) == 4) {
													out.println("SED");
												} else {
													out.println("-");
												}
							%>
						</td>
						<td><bean:write name="row" property="actionTakenById" /></td>
						<td><bean:write name="row" property="actionTakenByName" /></td>
						<td><bean:write name="row" property="historyAction" /></td>
						<td><bean:write name="row" property="historyActionDate" /></td>
						<td><bean:write name="row" property="taskSendTo" /></td>
						<td><bean:write name="row" property="historyRejectionSendTo" /></td>
						<td><bean:write name="row" property="historyRemraks" /></td>
						 <%-- <td><bean:write name="row" property="serviceno_m6" /> --%>
						 <td><bean:write name="row" property="m6No" /></td>
						 <td><bean:write name="row" property="serviceId" /></td>
						
						<td><bean:write name="row" property="reasonName" /></td>
						<td><bean:write name="row" property="delayReasonValue" /></td>
					</tr>
				</logic:iterate>
			</logic:notEmpty>
		</table>
	</logic:present>
	<%--[002] end--%>
</body>
</html:html>