<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<title> Logged out</title>
<SCRIPT type="text/javascript">
	//window.history.forward();
	function f1()
	{
		alert('You Have successfully Logged Out');
		window.close();
		//location.replace("<%=request.getContextPath()%>/defaultAction.do?method=LoginAction");
		//window.history.go(+1);
		//alert('b');
	}
	
	
	/*window.onbeforeunload=function ()
	 {
	 var pp;
	 return pp="hello";
	 }*/
	 
	 	 	 
	
	 
/*function login() 
{
		//document.forms[0].action="http://localhost:9080/IOES/iB2BUrlRdr.jsp?userId=manisha.misra";
		//document.forms[0].submit();			
}*/

</script> 
<% 
	response.setHeader("Cache-Control","no-cache"); 
	response.setHeader("Cache-Control","no-store"); 
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma","no-cache");
%>

</head>

<body bgcolor="#F7F7E7" onload="f1();" >
<br />
<br />
<FORM>

			

<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="gifs/tabLeft.gif"></td>
				<td background="gifs/tabbg.gif" class="tabHeading" width="30%">
				LOGOUT
				</td>
				<td><img src="gifs/tabRight.gif"></td>
				<td width="70%"><img src="gifs/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
	
	<table width="100%" border="0" class="tabledata" cellpadding="0"
	cellspacing="0" align="center">
		<tr>
			<td align="left" styleClass="inputBorder1"><font color="red" face="Verdana" size="4">
			You have successfully logged out of iB2B!!! Thank You</td>
		</tr>
		
		<tr>
			<td align="left"><font color="red" face="Verdana" size="2"><A
			href="#" onclick="login();">click to login </A></td>
		</tr>
	</table>
	<input type="hidden" name="sessionId" id="sessionId"
		value="<c:out value='${sessionScope.sessionId}'/>" />
	</FORM>

</body>
</html>