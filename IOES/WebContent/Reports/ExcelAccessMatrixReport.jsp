
<%--[001] Rahul Tandon Access Matrix Report--%>
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
<logic:present name="AccessMatrixReportExcel" scope="request">
<% response.setHeader("content-Disposition","attachment;filename=AccessMatrixReport.xls"); %>
<table width="100%" border="1" cellpadding="0" cellspacing="0">
<tr><td colspan="25" align="left" style="font-size:10px"><strong>Access Matrix Report</strong></td></tr>
<tr align="Center"><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>User Id</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>User Name</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Role Name</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Status</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Date of modification</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Modification by (user name)</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Modification by (user id)</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>Old Customer Segment</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"><b>New Customer Segment</b></td><td align="center" style="font-size:9px" bgcolor="#FF9255"></td>
</tr>
<logic:empty name="AccessMatrixReportExcel">
<tr><td colspan="25" align="center" style="font-size:10px;color:red;"><strong>No Records Found</strong></td></tr>
</logic:empty>
<logic:notEmpty  name="AccessMatrixReportExcel" scope="request"> 					
<logic:iterate id="row" name="AccessMatrixReportExcel" indexId="recordId">
<tr>
<td><bean:write  name="row" property="userId"/></td><td><bean:write  name="row" property="userName"/></td><td><bean:write  name="row" property="roleName"/></td><td><bean:write  name="row" property="accessOrDenied"/></td><td><bean:write  name="row" property="dateOfmofification"/></td><td><bean:write  name="row" property="modifiedByUserName"/></td><td><bean:write  name="row" property="modifiedByUserId"/></td><td><bean:write  name="row" property="oldCustSegmentName"/></td><td><bean:write  name="row" property="cus_segment"/></td>
</tr>
</logic:iterate>
</logic:notEmpty>
</table>
</logic:present>
</body>
</html:html>
