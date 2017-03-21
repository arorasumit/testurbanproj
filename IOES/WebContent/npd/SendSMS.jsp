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
<script language="JavaScript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/scw.js"></script>
<script type="text/javascript" src="js/timepicker.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>
<script type="text/javascript" src="js/inputColor.js"></script>
<script language="javascript" src="js/utility.js"></script>
<title>SEND SMS</title>
<script type="text/javascript">
function validate()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	if(myform.mobileNumber.value=="")
	{
		alert("Please Input Mobile Number");
		return false;
	}
	
	if(CheckNumeric(myform.mobileNumber,"Mobile Number")==false)
	{
		return false;
	}	
	if(myform.txtMessage.value=="")
	{
		alert("Please Input Text");
		return false;
	}
	else if(myform.txtMessage.value.length>255)
	{
		alert('Message Cannot Be Greater Than 255 characters');
		return false;
	}
	
	if(ValidateTextField(myform.txtMessage,'<%=request.getContextPath()%>',"Message")==false)
	{
		return false;
	}
	
	document.searchForm.action="<%=request.getContextPath()%>/SendSMS.do?method=SendSMS";
	document.searchForm.submit();
}
function fnValidateEmail()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	if(myform.emailId.value=="")
	{
		alert("Please Input Email Id");
		return false;
	}
	
		
	if(myform.emailMessage.value=="")
	{
		alert("Please Input Text");
		return false;
	}
	else if(myform.emailMessage.value.length>255)
	{
		alert('Message Cannot Be Greater Than 255 characters');
		return false;
	}
	
	if(ValidateTextField(myform.emailMessage,'<%=request.getContextPath()%>',"Message")==false)
	{
		return false;
	}
	
	document.searchForm.action="<%=request.getContextPath()%>/SendSMS.do?method=SendEmail";
	document.searchForm.submit();
}
</script>
<script language="JavaScript" src="js/staticValidatorScript.js"></script>
</head>
<body>
<br>
<br>
<br>
<br>
<br>
<br>

<html:form method="post" action="/SendSMS" styleId="searchForm">
	<table width="50%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
		<tr valign="middle">
			<td valign="bottom" width="100%" align="center">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr>
					<td><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Send SMS</td>
					<td><img src="npd/Images/tabRight.gif"></td>
					<td width="70%"><img src="npd/Images/zero.gif" width="15" height="1"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<table width="50%" border="0" class="tabledata" cellpadding="0"
		cellspacing="0" align="center">
		<tr>

			<td width="70%">
			<table width="100%" border="0" class="tabledata" cellpadding="0"
				cellspacing="0" align="center">

				<tr>
					<th width="30%" scope="row">
					<div align="left">Mobile Number <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text style="width:50%"
						property="mobileNumber" maxlength="10" size="20"></html:text></td>
				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="left">Text<font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:textarea style="width:50%"
						property="txtMessage"></html:textarea></td>
				</tr>

				<tr align="center">
					<th colspan="2" scope="row">
					<table width="50%" border="0">
						<tr>
							<div class="buttonVSmall" onClick="javascript:validate();">Send SMS	</div>

						</tr>
					</table>
				</tr>
			</table>
			</td>
		</tr>


	</table>
	<table width="50%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
		<tr valign="middle">
			<td valign="bottom" width="100%" align="center">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr>
					<td><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Send Email</td>
					<td><img src="npd/Images/tabRight.gif"></td>
					<td width="70%"><img src="npd/Images/zero.gif" width="15" height="1"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<table width="50%" border="0" class="tabledata" cellpadding="0"
		cellspacing="0" align="center">
		<tr>

			<td width="70%">
			<table width="100%" border="0" class="tabledata" cellpadding="0"
				cellspacing="0" align="center">

				<tr>
					<th width="30%" scope="row">
					<div align="left">Email(csv) <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text style="width:50%"
						property="emailId"  size="20"></html:text></td>
				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="left">Text<font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:textarea style="width:50%"
						property="emailMessage"></html:textarea></td>
				</tr>

				<tr align="center">
					<th colspan="2" scope="row">
					<table width="50%" border="0">
						<tr>
							<div class="buttonVSmall" onClick="javascript:fnValidateEmail();">Send Email	</div>

						</tr>
					</table>
				</tr>
			</table>
			</td>
		</tr>


	</table>
</html:form>


</body>
</html:html>

