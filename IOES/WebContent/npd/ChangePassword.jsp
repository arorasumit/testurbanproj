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
function reset()
{
	document.forms[0].oldpassword.value=="";
	document.forms[0].newpassword.value=="";
	document.forms[0].confirmpassword.value=="";
}

function submitPassword()
{
	myform=document.getElementById('searchForm');
	if(document.forms[0].oldpassword.value == "")
	{
		alert("Please Enter Your Current Password");
		document.forms[0].oldpassword.focus();
		return false;
	}
		
	if(ValidateTextField(myform.oldpassword,'<%=request.getContextPath()%>',"Old Password")==false)
	{
		return false;
	}
	
	if(document.forms[0].newpassword.value == "")
	{
		alert("Please Enter Your New Password");
		document.forms[0].newpassword.focus();
		return false;
	}
	
	if(ValidateTextField(myform.newpassword,'<%=request.getContextPath()%>',"New Password")==false)
	{
		return false;
	}
	
	if(document.forms[0].confirmpassword.value == "")
	{
		alert("Please Enter Your New Password again");
		document.forms[0].confirmpassword.focus();
		return false;
	}
	
	if(ValidateTextField(myform.confirmpassword,'<%=request.getContextPath()%>',"Confirm Password")==false)
	{
		return false;
	}
	
	if(document.forms[0].newpassword.value != document.forms[0].confirmpassword.value )
	{
	  	alert("New Password and Confirm Password must be same");
	  	document.forms[0].confirmpassword.focus();
	  	return false;
	}
	
	if(document.forms[0].emailConfirmation.checked)
	{
		document.forms[0].confirmpassword.value=1;
	}
	else
	{
		document.forms[0].confirmpassword.value=0;
	}
	document.forms[0].newpassword.value = encryptPassword(document.forms[0].newpassword.value);
 	document.forms[0].confirmpassword.value = encryptPassword(document.forms[0].confirmpassword.value);
  	document.forms[0].oldpassword.value = encryptPassword(document.forms[0].oldpassword.value);
	document.forms[0].action='./changePwd.do?method=changePassword'; 
	showLayer();
	document.forms[0].submit();

}
</script>

</head>
<body onload="javascript:document.body.click();">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
    <br /><br /><br />
</div>
<html:form action="/changePwd" styleId="searchForm" method="post">
	<logic:messagesPresent>
		<table width="50%" align="center">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><font color="red" face="Verdana" size="2">
					<html:messages id="message">
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
	<table width="70%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="borderTab">
					<tr>
						<td><img src="npd/Images/tabLeft.gif"></td>
						<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Change Password</td>
						<td><img src="npd/Images/tabRight.gif"></td>
						<td width="70%"><img src="npd/Images/zero.gif" width="15" height="1"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width="70%" border="2" class="tabledata" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td align="center" style="vertical-align: bottom"><Strong>Current Password</Strong></td>
			<td><html:password property="oldpassword" size="20"></html:password></td>
		</tr>
		<tr>
			<td align="center" style="vertical-align: bottom"><Strong>New Password</Strong></td>
			<td width="70%"><html:password  property="newpassword" size="20"></html:password></td>
		</tr>
		<tr>
			<td align="center" style="vertical-align: bottom"><Strong>Confirm Password</Strong></td>
			<td width="70%"><html:password  property="confirmpassword" size="20"></html:password></td>
		</tr>
		<tr>
			<td align="center" style="vertical-align: bottom"><Strong>Want E-Mail Confirmation??</Strong></td>
			<td width="70%"><html:checkbox property="emailConfirmation" value="1"></html:checkbox></td>
		</tr>
		<tr>
			<td colspan="1" align="right">
				<div class="buttonVSmall" onClick="javascript:submitPassword();" id="submit">Submit</div>
			</td>
			<td colspan="1" align="left">
				<div class="buttonVSmall" onClick="javascript:reset();" id="reset">Reset</div>
			</td>
		</tr>
	</table>
	<br>
	<table width="70%" border="2" class="tabledata" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td align="left" class="wel">
				<font face="Verdana" size="2">
					<strong>Password should abide the following rules:-</strong>
				</font>
			</td>
		</tr>
		<tr>
			<td align="left" >
				<font color="red" face="Verdana" size="2">
					1.Password should be atleast of 8 characters.
				</font>
			</td>
		</tr>
		<tr>
			<td align="left" >
				<font color="red" face="Verdana" size="2">
					2.Password should contain atleast one alphabetic and one non-alphabetic character.
				</font>
			</td>
		</tr>
		<tr>
			<td align="left" >
				<font color="red" face="Verdana" size="2">
					3.Password should not begin or end with non-alphabetic characters.
				</font>
			</td>
		</tr>
		<tr>
			<td align="left" >
				<font color="red" face="Verdana" size="2">
					4.Password should not contain userid as a part of it.
				</font>
			</td>
		</tr>
		<tr>
			<td align="left" >
				<font color="red" face="Verdana" size="2">
					5.Password should not contain identical character from any position in the 3 previous passwords.
				</font>
			</td>
		</tr>	
	</table>
	</html:form>
</BODY>
</html:html>
