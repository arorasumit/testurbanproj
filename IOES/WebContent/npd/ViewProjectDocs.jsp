<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="com.ibm.ioes.npd.beans.RepositoryUploadBean"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">
<script language="JavaScript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/scw.js"></script>
<script type="text/javascript" src="js/timepicker.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>
<script type="text/javascript" src="js/inputColor.js"></script>
<title>ChangeOrder Workflow</title>
<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>
<script type="text/javascript">
function closeTask()
{
	window.close();
}

</script>
</head>

<body onload="javascript:document.body.click();">
<div id="menu_abc" style="display: block;">
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="newProduct">
		<td valign="bottom" width="80%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
			<td>&nbsp;</td>
			</tr>
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Project Documents</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<br>
<html:form action="/RepositoryUpload" method="post"  styleId="searchTask">
	<div style="overflow:scroll;height:320px;width:100%" class="scroll" align="center">
	<table align="center" cellSpacing="1" cellPadding="4" border="0" class="tabledata" id="tblRollList" width="100%">
		<tr bgcolor="#FF9255" class="tabledata">
			<td align="center" colspan="4">
				<font size="2"><strong>Project Name:<bean:write name="repositoryUploadBean" property="projectName"/>(<bean:write name="repositoryUploadBean" property="projectID"/>)</strong></font>
			</td>
		</tr>
		<tr bgcolor="#FF9255" align="center">	
			<td background="#FF9255">S.No.</td>
			<td background="#FF9255">Document Name</td>
			<td background="#FF9255">Stage Name</td>
			<td background="#FF9255">Task Name</td>
		</tr>
		<logic:notEmpty name="repositoryUploadBean" property="listProjectPlan">
			<%int sno;
			sno=0;%>
			<logic:iterate id="row" name="repositoryUploadBean" property="listProjectPlan" indexId="index1">
	 			<%sno=sno+1;%>
				<tr class="redBg" align="center">
					<td><%=sno%></td>
					<td><bean:write name='row' property="docName" /></td>
					<td><bean:write name='row' property="taskName" /></td>
					<td><bean:write name='row' property="stageName" /></td>
			</logic:iterate>
		</logic:notEmpty>
		<logic:empty name="repositoryUploadBean" property="listProjectPlan">
			<tr class="redBg" align="center">
				<td colspan="4">NO DOCUMENTS FOUND</td>
			</tr>
		</logic:empty>
		<tr>
			<td colspan="4" align="center">
				<div class="buttonVSmall" onClick="closeTask();">Close</div>
			</td>
		</tr>
	</table>
	</div>
	
</html:form>
</div>
</body>

</html:html>


