<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
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
<script language="javascript" src="js/utility.js"></script>
<script type="text/javascript"></script>
</head>
<body onload="javascript:document.body.click();">
<div id="menu_abc" style="display: block;">
<html:form action="/Dashboard" styleId="searchForm"
	method="post">
	<table width="80%" border="0" cellpadding="0" cellspacing="0"
		class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr>
					<td width="5"><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" width="367">Dashboard Report</td>
					<td width="9"><img src="npd/Images/tabRight.gif"></td>
					<td align="left" width="703"><font size="1"> </font></td>
					<td width="62" align="right" style="padding-right:10px;">&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<table width="80%" border="1" class="tabledata" cellpadding="3" cellspacing="1" align="center">
		<tr>
			<td>
			<table align="center" cellSpacing="1" cellPadding="4" border="0"
				class="scroll" id="tblRollList" width="100%">
				<tr valign="middle" class="rptHeader">
					<td width="54%">Task Pending in MyTodoList</td>
					<td><bean:write name="dashboardDto" property="taskpending_mytodoList"/>	</td>
				</tr>
				<tr bgcolor="#FF9255" class="alterRedBg" align="center">
					<td background="#FF9255">Task Pending for RFI</td>
					<td><bean:write name="dashboardDto" property="taskpending_rfi"/></td>	
				</tr>
				<tr class="rptHeader" align="left">
					<td>Task Pending for PLR Uploading</td>
					<td><bean:write name="dashboardDto" property="taskpending_plrUploading"/></td>
				</tr>
				<tr class="alterRedBg" align="center">
					<td>Total</td>
					<td><bean:write name="dashboardDto" property="total_taskPending"/></td>		
				</tr>

			</table>
			</td>
		</tr>
	</table>
</html:form>
</div>
</body>
</html:html>
