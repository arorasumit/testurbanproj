<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.ibm.appsecure.util.IEncryption"%>
<%@page import="com.ibm.appsecure.util.Encryption"%>

<%@page import="javax.wsdl.Import"%>
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
<br>
<br>
<body onload="javascript:document.body.click();">
<div class="error" align="center">
	<logic:present name="validation_errors">
		<logic:iterate id="error_row" name="validation_errors" scope="request">
				<font color="red"><bean:write name="error_row" /></font><BR>
		</logic:iterate>
	</logic:present>
</div>
	<html:form action="/RoleSelection" styleId="searchForm" method="post">
		
		<table width="60%" border="2" class="tabledata" cellpadding="0" cellspacing="0" align="center">
			<tr align="center">
				<td width="100%" align="Center" valign="bottom" colspan="4" style="font:bold;font-size:large;" >Welcome to NPD PORTAL</td>
			</tr>
			<tr align="center">
				<th width="100%" scope="row" colspan="4">
					<div align="center"><font color="#990033">Please Select a Role To Proceed Further</font></div>
				</th>
			</tr>
			<tr>
				<th scope="row" align="right">
					<div align="center"></div>
				Role Name<font color="#990033"></font></th>
				<td  colspan="2">
					<html:select property="roleFilter" style="width:140px" onchange="goToMenu();">
						<html:option value="-1">Select a Role</html:option>
						<logic:notEmpty name="roleSelectionBean" property="roleList">
							<html:optionsCollection property="roleList" label="roleName" value="roleID"/>
						</logic:notEmpty>
					</html:select>
				</td>
			</tr>
			<tr>
				<td align="right">	<div class="buttonVSmall" onClick="goToMenu();">Submit</div></td>
				<td align="left"><div class="buttonVSmall" onClick="goToLogOut();">LogOut</div></td>
		    </tr>
	</table>
	</html:form>
</body>
</html:html>
