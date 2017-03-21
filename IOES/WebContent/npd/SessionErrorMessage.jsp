<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.npd.utilities.AppConstants;"%>
<html>
<head>
<title>ChangeOrder Workflow</title>
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">
<title>Login</title>
<SCRIPT type="text/javascript">

function login() 
{
	if (top.frames.length > 0)
	{
	    	top.location="Login.jsp";
	}
	else
	{
		//document.forms[0].action="http://10.14.51.5/airtel/logon.do";
		//document.forms[0].submit();
		top.location="";
	}
	
}
function onBodyLoad()
{
	<%if(!"1".equals((String)request.getParameter("redirectcount")))
	{
	%>
	top.location="SessionErrorMessage.jsp?redirectcount=1";
	<%
	}
	%>
}
</SCRIPT>
</head>
<body bgcolor="#fff6f4" onload="onBodyLoad()">
<FORM>
<%
			request.getSession().removeAttribute(
			AppConstants.NPD_USERS_SESSION_NAME);
%>

<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Your
				Session Has Been Expired !!</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<table width="100%" border="0" class="tabledata" cellpadding="0"
	cellspacing="0" align="center">

	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align="left"><font color="red" face="Verdana" size="2">Due
		to any of the following Reasons :</td>
	</tr>
	<tr>
		<td align="left"><font color="red" face="Verdana" size="2">1.
		You entered invalid character. Invalid characters are $*\&lt;&gt;%^</td>
	</tr>
	<tr>
		<td align="left"><font color="red" face="Verdana" size="2">2.
		You are trying to see the page which you are not authorize to see.</td>
	</tr>
	<tr>
		<td align="left"><font color="red" face="Verdana" size="2">3.
		Someone else Logged-In From Your Username &amp; Password.</td>
	</tr>
	<tr>
		<td align="left"><font color="red" face="Verdana" size="2">4.
		Session has Timeout due to Inactivity.</td>
	</tr>
	<tr>
		<td align="left"><font color="red" face="Verdana" size="2">&nbsp;</td>
	</tr>
	<tr>
		<td align="left"><font color="red" face="Verdana" size="2">Please
		<A href="#" onclick="login();">Login here</A></td>
	</tr>


</table>
</FORM>


</body>
</html>
