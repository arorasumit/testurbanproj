<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

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

<logic:present name="roleHistoryList" scope="request">
			<%
						response.setHeader("content-Disposition",
						"attachment;filename=NPDSPOCHistory.xls");
			%>
	<strong>NPD SPOC History</strong>
	<table width="100%" border="1" cellpadding="0" cellspacing="0">
		<tr bgcolor="#FF9255" align="Center">
			<td background="#FF9255">Employee Name</td>
			<td background="#FF9255">Old Roles</td>
			<td background="#FF9255">New Roles</td>
			<td background="#FF9255">Old Level 1</td>
			<td background="#FF9255">New Level 1</td>
			<td background="#FF9255">Old Level 2</td>
			<td background="#FF9255">New Level 2</td>
			<td background="#FF9255">Created Date</td>
		</tr>

		<logic:notEmpty name="roleHistoryList">
			<logic:iterate name="roleHistoryList" id="roleHistoryList_id" indexId="recordId">
				
				
						<tr>
							<td><bean:write name='roleHistoryList_id' property='employeeName' /></td>
							<td><bean:write name='roleHistoryList_id' property='oldRoles' /></td>
							<td><bean:write name='roleHistoryList_id' property='newRoles' /></td>
							<td><bean:write name='roleHistoryList_id' property='oldLevel1' /></td>
							<td><bean:write name='roleHistoryList_id' property='newLevel1' /></td>
							<td><bean:write name='roleHistoryList_id' property='oldLevel2' /></td>
							<td><bean:write name='roleHistoryList_id' property='newLevel2' /></td>
							<td><c:out value="${roleHistoryList_id.createdDate}" /></td>
						</tr>
					</logic:iterate>
		</logic:notEmpty>
		<logic:empty name="roleHistoryList">No Record Found</logic:empty>
	</table>
</logic:present>
</body>
</html:html>