<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
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
<script language="JavaScript" src="js/staticValidatorScript.js"></script>
<title>ChangeOrder Workflow</title>

<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>
<style type="text/css">
    .opaqueLayer
    {
        display:none;
        position:absolute;
        top:0px;
        left:0px;
        opacity:0.5;
        filter:alpha(opacity=50);
        background-color: #000000;
        z-Index:1000;
        text-align:center;
        vertical-align:middle;
        padding:100px;
    }
    
</style>

<script type="text/javascript" src="js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<script type="text/javascript">
function resetPwd()
{
	if(document.forms[0].emailId.value=="")
	{
		alert("Please Provide Email ID in User ID");
		return false;
	}
	if(document.forms[0].emailId.value != "")
	{
		var email = trim(document.forms[0].emailId.value);
		var isValidEmail = emailValidate(email);
		if(isValidEmail == false)
		{
			alert("Please Enter Valid Email ID");
			return false;
		}		
	}
	document.forms[0].action="./ResetPasswordAction.do?method=resetPassword"; 
	//document.forms[0].action="<c:out value='${sessionScope.MenuContextPath}' />/LoginAction.do?method=Submit"; 
	showLayer();
	document.forms[0].submit();
}

function loginNPD()
{
	window.location="Login.jsp";
}
</script>

</head>
<body onload="javascript:document.body.click();">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
    <br /><br /><br />
</div>
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
<br>
<html:form action="/ResetPasswordAction" styleId="searchForm" method="post">
	<logic:messagesPresent>
		<table width="50%" align="center">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><font color="red" face="Verdana" size="2">
					<html:messages id="message" message="true">
						<li><bean:write name="message" /></li>
				</html:messages></font></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
	</logic:messagesPresent>
	<logic:messagesPresent message="true">
		<table width="50%" align="center">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><font color="red" face="Verdana" size="2">
					<html:messages id="message" message="true">
						<li><bean:write name="message" /></li>
					</html:messages></font>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
	</logic:messagesPresent>
	<div class="error" align="center">
		<logic:present name="validation_errors">
			<logic:iterate id="error_row" name="validation_errors" scope="request">
					<font color="red"><bean:write name="error_row" /></font><BR>
			</logic:iterate>
		</logic:present>
	</div>
	<br>
	<table width="60%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="borderTab">
					<tr>
						<td><img src="npd/Images/tabLeft.gif"></td>
						<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Forgot Password</td>
						<td><img src="npd/Images/tabRight.gif"></td>
						<td width="70%"><img src="npd/Images/zero.gif" width="15" height="1"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width="60%" border="0" class="tabledata" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td align="right" style="vertical-align: bottom"><Strong>User ID</Strong></td>
			<td><html:text property="emailId" size="30" maxlength="30"></html:text></td>
		</tr>
		<tr>
			<td align="right">
				<div class="buttonVSmall" onclick="javascript:resetPwd();">Submit</div>
			</td>
			<td>
				<div class="buttonVSmall" onclick="javascript:loginNPD();">Login</div>
			</td>
		</tr>
	</table>
	</html:form>
</BODY>
</html:html>
