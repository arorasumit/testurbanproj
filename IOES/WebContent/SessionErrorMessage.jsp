<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>


<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>ACS</title>
<link href="${pageContext.request.contextPath}/CSS/style.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/CSS/sdmenu.css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/JS/sdmenu.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/Master.css" type="text/css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/CSS/dropdown.css" type="text/css" />
<script type="text/javascript" src="${pageContext.request.contextPath}/JS/dropdown.js"></script>
<title>Login</title>
<SCRIPT type="text/javascript">

function login() 
{
		document.forms[0].action="http://localhost:9080/IOES/iB2BUrlRdr.jsp?userId=manisha.misra";
		document.forms[0].submit();
	
}

</SCRIPT>
</head>
<body bgcolor="#fff6f4" >
<FORM>
<%
			request.getSession().removeAttribute(
			AppConstants.APP_SESSION_USER_INFO);
			
%>

<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td></td>
				<td class="wel" align="left" style="font-size:17px">Not Authorised</td>
				<td></td>
				<td width="70%"><img src="Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" class="tab1" cellpadding="0"
	cellspacing="0" align="center">

	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="left"><font color="red" face="Verdana" size="2"><b><i>Due
		to any of the following Reasons :</i></b></td>
	</tr>
	<tr>
		<td align="left"><font color="red" face="Verdana" size="2">1.
		You are not Authorised to view this page. Contact Administrator..</font></td>
	</tr>
	<tr>
		<td align="left"><font color="red" face="Verdana" size="2">2.
		Your session may timed out. To Re-Login Please click below.</font></td>
	</tr>
	<tr>
		<td align="left"><font color="red" face="Verdana" size="2">Please
		<A href="#" onclick="login();">Login here</A></font></td>
	</tr>


</table>
</FORM>


</body>
</html>
