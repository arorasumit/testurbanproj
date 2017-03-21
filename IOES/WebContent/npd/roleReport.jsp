<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.utilities.AppConstants"%>
<html:html>
<head>
<title>ChangeOrder Workflow</title>
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">

<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>
<script type="text/javascript" src="js/inputColor.js"></script>
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

function fnSubmit()
{
	var errorString="";
	if(document.getElementById("id_reportRoleId").value==-1)
	{
	errorString+="Please Select Role"+"\n";
	}
	if(errorString!="")
	{
	alert(errorString);
	return false;
	}
	else
	{
	//setFormBean(document.getElementById('myform'));
	showLayer();
	return true;
	
	}
}

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

</script>

</head>
<body >
<div id="menu_abc" style="display: block;">
<div class="error" align="center">
	<logic:present name="validation_errors">
		<logic:iterate id="error_row" name="validation_errors" scope="request">
				<font color="red"><bean:write name="error_row" /></font><BR>
		</logic:iterate>
	</logic:present>
</div>
<table width="98%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Role-Employee Report</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<html:form action="/roleReport" method="post" styleId="myform" 	onsubmit="return fnSubmit();">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
    <br /><br /><br />
</div>
	<table width="100%" border="2" class="tabledata" cellpadding="0"
		cellspacing="0" align="left">
		<tr>
			<td width="70%">
			<table width="100%" border="0" class="tabledata" cellpadding="0"
				cellspacing="0" align="center">

				<tr>
					<th width="30%" scope="row">
					<div align="right">Role <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:select property="reportRoleId" styleId="id_reportRoleId"
						style="width:250px;height:150px">
							<html:option value="-1">Select</html:option>
						<logic:notEmpty name="userNpdSpocs" property="roleList">
							<html:optionsCollection property="roleList" label="rolename"
								value="roleid" />
						</logic:notEmpty>
					</html:select></td>
				</tr>
				
				<tr align="center">
					<th colspan="2" scope="row">
					<table width="50%" border="0">
						<tr>
							<td align="center"><html:submit property="method">
								<bean:message key="showRoleReport" bundle="ButtonResources" />
							</html:submit></td>

						</tr>
						<tr>
							<td></td>
							<td></td>

						</tr>
						<tr>
							<td></td>
							<td></td>

						</tr>
						<tr>
							<td></td>
							<td></td>

						</tr>
						<html:messages id="message" message="true">
							<tr>
								<td colspan="2" align="center" style="color: red;">
					
										<li><bean:write name="message"/></li>
					
								</td>
							</tr>
						</html:messages>
						<logic:notEmpty property="employeeList" name="userNpdSpocs">

							<table width="80%" border="1" class="tabledata" cellpadding="3"
								cellspacing="1" align="center">

								<tr bgcolor="#FF9255">
									<th width="20%" nowrap="nowrap">S.No</th>
									<th width="30%" nowrap="nowrap">Employess Name</th>
									<th width="30%" nowrap="nowrap">Email Id</th>
								</tr>
									<%String []colors=new String[]{"redBg","alterRedBg"}; %>
								<logic:iterate id="row"
									name="userNpdSpocs" property="employeeList"
									indexId="index1">
									<tr class="<%=colors[index1.intValue()%2]%>">
										<td nowrap="nowrap" align="center"><%=index1.intValue()+1 %></td>
										<td nowrap="nowrap" align="center"><bean:write name="row" property="empname"/></td>
										<td nowrap="nowrap" align="center"><bean:write name="row" property="email"/></td>
									</tr>

								</logic:iterate>
							</table>
						</logic:notEmpty>
						
								
							</table>
					</table>

</td>
</tr>
</table>
</html:form>
</div>
</BODY>
</html:html>
