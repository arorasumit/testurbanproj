
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page isErrorPage="true"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page
	import="com.ibm.ioes.npd.utilities.AppConstants,com.ibm.ioes.npd.utilities.AppUtility"%>
<link type="text/css" rel="stylesheet" href="CSS/style.css">
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Error Page</title>
<SCRIPT type="text/javascript">

function func_1(thisObj, thisEvent,value) 
{	
	history.go(-1);
}
</SCRIPT>
</head>
<body bgcolor="#fff6f4">
<br />
<br />
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">UnAuthorized User</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<html:form action="/LoginAction" method="post">
	<table width="100%" border="0" class="tabledata" cellpadding="0"
	cellspacing="0" align="center">
		<tr>
			<td align="left"><font color="red" face="Verdana" size="2">1.
			You Don't Have Access to the Application.Please Contact The Administrator For Futher Assistance</td>
		</tr>
		<tr>
			
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
	<input type="hidden" name="sessionId" id="sessionId"
		value="<c:out value='${sessionScope.sessionId}'/>" />
	<%
			if (exception != null) {
			AppConstants.NPDLOGGER.error(AppUtility
			.getStackTrace(exception));
		}
	%>

</html:form>
</body>
</html:html>
