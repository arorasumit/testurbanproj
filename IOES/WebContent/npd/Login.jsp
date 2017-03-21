<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html:html>
<head>
<title>ChangeOrder Workflow</title>
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">
<script language="javascript" src="js/utility.js"></script>
<title>Login</title>
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
//Script to validate the login page
function validate()
{
	if(document.forms[0].loginId.value=="")
	{
		alert("Please Provide User ID");
		return false;
	}
	if(document.forms[0].password.value=="")
	{
		alert("Please Provide Password");
		return false;
	}
	if(document.forms[0].loginId.value != '')
	{
		var email = trim(document.forms[0].loginId.value);
		var isValidEmail = emailValidate(email);
		if(isValidEmail == false)
		{
			alert("Please Enter Valid User ID");
			return false;
		}		
	}
	document.forms[0].password.value = encryptPassword(document.forms[0].password.value);
	document.forms[0].action="<c:out value='${sessionScope.MenuContextPath}' />/LoginAction.do?method=Submit"; 
	showLayer();
	document.forms[0].submit();
	//document.getElementById("submit").disabled = true;
}

function resetPwd()
{
	window.location="resetPassword.jsp";
}
</script>
<script language="JavaScript" src="js/staticValidatorScript.js"></script>
<html:javascript formName="loginBean" staticJavascript="false" />
</head>
<body>
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
<br>
<br>
<html:form method="post" action="/LoginAction" >
	<logic:messagesPresent>
		<table width="50%" align="center">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><font color="red" face="Verdana" size="2"><html:messages
					id="message">
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
	<br>
	<table width="50%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
		<tr valign="middle">
			<td valign="bottom" width="100%" align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="borderTab">
					<tr>
						<td><img src="npd/Images/tabLeft.gif"></td>
						<td background="npd/Images/tabbg.gif" class="tabHeading" width="60%">Please Enter Details to Login</td>
						<td><img src="npd/Images/tabRight.gif"></td>
						<td width="70%"><img src="npd/Images/zero.gif" width="15" height="1"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width="50%" border="0" class="tabledata" cellpadding="0"	cellspacing="0" align="center">
		<tr>
			<td width="70%">
				<table width="100%" border="0" class="tabledata" cellpadding="0" cellspacing="0" align="center">
					<tr>
						<th width="30%" scope="row">
							<div align="left">User ID <font color="#990033">*</font></div>
						</th>
						<td width="70%">
							<html:text style="width:50%" property="loginId" maxlength="100" size="20"></html:text>
						</td>
					</tr>
					<tr>
						<th width="30%" scope="row">
							<div align="left">Password<font color="#990033">*</font></div>
						</th>
						<td width="70%">
							<html:password style="width:50%" property="password" maxlength="100" size="20" ></html:password>
						</td>
					</tr>
					<tr align="center">
						<th colspan="2" scope="row">
							<table width="50%" border="0">
									<tr>
										<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
											<div class="buttonVSmall" onclick="javascript:validate();" >Submit</div>
										</td>
										<td align="center" style="vertical-align: bottom" colspan="1">
											<div class="buttonLagre" onclick="javascript:resetPwd();" >Forgot Password</div>
										</td>
									</tr>
							</table>
						</th>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</html:form>


</body>
</html:html>
