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
<title>ChangeOrder Workflow</title>
<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>
<script type="text/javascript">

function goToMenu()
{
	var appContextPath = '<%=request.getContextPath()%>';
	var RoleName;
	var RoleID;
	RoleID=document.forms[0].roleFilter.value;
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var result;
	
	if(RoleID!="-1")
	{
		RoleID = jsonrpc.processes.encryptString(RoleID);
		var w = document.forms[0].roleFilter.selectedIndex;
		RoleName=jsonrpc.processes.encryptString(document.forms[0].roleFilter.options[w].text);
		myform=document.getElementById('searchForm');	
		document.searchForm.action="<c:out value='${sessionScope.MenuContextPath}' />/RoleSelection.do?method=goToMenu&RoleID="+RoleID+"&RoleName="+RoleName;
		document.searchForm.submit();
	}
	else
	{
		alert("Please Select A Role");
		return false;
	}
}

function fnAction(opt)
{
	if(opt=='enter')
	{
		document.searchForm.action="<c:out value='${sessionScope.MenuContextPath}' />/LoginAction.do?method=Submit";
		document.searchForm.submit();
	}
	else if(opt=='idea')
	{
		document.searchForm.action="<c:out value='${sessionScope.MenuContextPath}' />/HaveAnIdea.do?method=init";
		document.searchForm.submit();
	}
}

function goToLogOut()
{
	myform=document.getElementById('searchForm');	
	document.searchForm.action="<c:out value='${sessionScope.MenuContextPath}' />/LogoutAction.do?method=Logout";
	document.searchForm.submit();
}

</script>
</head>
<table width="100%"  border="0" cellspacing="0" cellpadding="0" background="Images/gifs/header-bg.gif">
  <tr>
    <td> <img src="Images/gifs/logo.gif" height="40" width="94"></td>
    <td><img src="Images/gifs/npdPortal.gif" height="40" width="351"></td>
    <td width="100%" background="Images/gifs/right-bg.gif" style="background-position:right; background-repeat:no-repeat"></td>
  </tr>
</table>
<br>
<body onload="javascript:document.body.click();">
<div class="error" align="center">
	<logic:present name="validation_errors">
		<logic:iterate id="error_row" name="validation_errors" scope="request">
				<font color="red"><bean:write name="error_row" /></font><BR>
		</logic:iterate>
	</logic:present>
</div>
<logic:messagesPresent message="true">
	<table width="50%" align="center" id='messageBody'>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><font color="red" face="Verdana" size="2"><html:messages
				id="message" message="true">
				<li><bean:write name="message" /></li>
			</html:messages></font></td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
</logic:messagesPresent>
<html:form action="/WelcomeOption" styleId="searchForm" method="post">
	<table width="100%"  border="0" cellspacing="0" cellpadding="0" height="580">
		<tr>
   			<td width="50%" align="center"><a href="#" onClick="fnAction('idea');"><img src="Images/gifs/haveAnIdea1.gif" border="0"></a><br><br>
 			<a href="#" onClick="fnAction('idea');"><img src="Images/gifs/haveAnIdeaName.gif" border="0"></ a></td>
   			<td background="Images/gifs/dot.gif"><img src="Images/gifs/zero.gif" height="1" width="27"></td>
   			<td width="50%" align="center"><a href="#" onClick="fnAction('enter');"><img src="Images/gifs/interPortel1.gif" border="0"></a><br><br>
			<a href="#" onClick="fnAction('enter');"><img src="Images/gifs/interPortelName.gif" border="0"></a>
			</td>
 		</tr>
	</table>
	
	</html:form>
</body>
</html:html>
