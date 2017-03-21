<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.ibm.ioes.npd.beans.AccessMatrixBean;"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">
<script language="JavaScript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/scw.js"></script>
<script type="text/javascript" src="npd/js/timepicker.js"></script>
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>
<script type="text/javascript" src="npd/js/inputColor.js"></script>
<script language="javascript" src="npd/js/utility.js"></script>
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

<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>


<script type="text/javascript">
function viewInterfaceDetails()
{
	if(document.forms[0].roleFilter.value=="-1")
	{
		alert("Select a Role First !!");
		return false;
	}

	myform=document.getElementById('searchForm');	
	document.accessMatrixBean.selectedRoleID.value=myform.roleFilter.value;
	document.accessMatrixBean.selectedModuleID.value=myform.moduleFilter.value;
	document.searchForm.action="<%=request.getContextPath()%>/AccessMatrix.do?method=getInterfaceList";
	showLayer();
	document.searchForm.submit();
}

function getcheckedInterfaceId(chk,id)
{
	if(chk)
	{
		document.accessMatrixBean.allowedInterfaceId.value = document.accessMatrixBean.allowedInterfaceId.value+'&'+id;
	}
	else
	{
		document.accessMatrixBean.prohibitedInterfaceId.value = document.accessMatrixBean.prohibitedInterfaceId.value+'&'+id;		
	}
}

function submitForm()
{
	if(document.forms[0].roleFilter.value=="-1")
	{
		alert("Select a Role First !!");
		return false;
	}
	
	myform=document.getElementById('searchForm');	
	//roleID=myform.roleFilter.value;
	document.accessMatrixBean.selectedRoleID.value=myform.roleFilter.value;
	document.accessMatrixBean.selectedModuleID.value=myform.moduleFilter.value;
	document.searchForm.action="<%=request.getContextPath()%>/AccessMatrix.do?method=UpdateMatrix&roleID";
	showLayer();
	document.searchForm.submit();
}
</script>

</head>
<body onload="javascript:document.body.click();">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
    <br /><br /><br />
</div>
<logic:present name="validation_errors">
	<table width="80%" class="tabledata" align="center">
		<tr>
			<td>
				<div class="error" align="center">
					<logic:iterate id="error_row" name="validation_errors" scope="request">
							<font color="red"><bean:write name="error_row" /></font><BR>
					</logic:iterate>
				</div>
			</td>
		</tr>
	</table>
</logic:present>
<html:form action="/AccessMatrix" styleId="searchForm" method="post">
<html:hidden property="roleID" />
	<table width="80%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="borderTab">
					<tr>
						<td><img src="npd/Images/tabLeft.gif"></td>
						<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">User Access Matrix</td>
						<td><img src="npd/Images/tabRight.gif"></td>
						<td width="70%"><img src="npd/Images/zero.gif" width="15" height="1"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<html:hidden property="allowedInterfaceId" value=""/>
	<html:hidden property="prohibitedInterfaceId" value=""/>
	<table width="80%" border="2" class="tabledata" cellpadding="0" cellspacing="0" align="center">
		<tr valign="bottom">
			<td width="20%" align="Center" style="vertical-align: bottom"><strong>Select a Role</strong></td>
			<td width="20%" align="LEFT">
				<html:select property="roleFilter" style="width:140px">
					<html:option value="-1">Select a Role</html:option>
					<logic:notEmpty name="accessMatrixBean" property="listRole">
						<html:optionsCollection property="listRole" label="roleName" value="roleID" />
					</logic:notEmpty>
				</html:select>
			</td>
			<td width="20%" align="Center" style="vertical-align: bottom"><strong>Select a Module</strong></td>
			<td width="20%" align="LEFT">
				<html:select property="moduleFilter" style="width:140px">
					<html:option value="-1">All</html:option>
					<logic:notEmpty name="accessMatrixBean" property="moduleList">
						<html:optionsCollection property="moduleList" label="moduleName" value="modID" />
					</logic:notEmpty>
				</html:select>
			</td>
			<td align="right" style="padding-right:10px;" width="20%"><a href="#"><img onclick="javascript:viewInterfaceDetails();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;</td>
		</tr>
	</table>
	<html:hidden property="selectedRoleID" value=""/>
	<html:hidden property="selectedModuleID" value=""/>
	<html:hidden property="methodName" />
	<logic:present name="accessMatrixBean" property="listInterface">
		<table width="80%" border="2" class="tabledata" cellpadding="0" cellspacing="0" align="center">
			<tr>
				<td height="316">
					<div style="overflow:scroll;height:370px;width:100%" class="scroll">
						<table align="center" cellSpacing="1" cellPadding="4" border="0" class="scroll" id="tblRollList" width="100%">
							<tr valign="middle" class="rptHeader">	
								<td width="15%" background="#FF9255">Sno</td>
								<td width="70%" background="#FF9255">Interface Name</td>
								<td width="15%" background="#FF9255">Access</td>
							</tr>
							<logic:notEmpty name="accessMatrixBean" property="listInterface">
								<%int sno=0;%>
								<logic:iterate id="assumptionDetailList_id" name="accessMatrixBean" property="listInterface" indexId="index1">
									<%sno=sno+1;%>
									<%if((index1.intValue())%2==0) {%>
										<tr class="redBg" align="center">
									<% }else{ %>
										<tr class="alterRedBg" align="center">
									<% } %>
											<td width="15%"><%=sno%></td>
											<td width="70%"><bean:write name='assumptionDetailList_id' property='interfaceName' /></td>
											<logic:equal value="1" name="assumptionDetailList_id" property="accessFlag">		
												<td align="center" width="15%">
													<input type="checkbox" checked="checked" name="interface" value='<bean:write name="assumptionDetailList_id" property="interfaceID" />#<bean:write name="assumptionDetailList_id" property="accessFlag"/>' onclick="javascript: getcheckedInterfaceId(this.checked,this.value);"> 
												</td>
											</logic:equal>	
											<logic:notEqual value="1" name="assumptionDetailList_id" property="accessFlag">		
												<td align="center" width="15%">
													<input type="checkbox"  name="interface" value=<bean:write name="assumptionDetailList_id" property="interfaceID"/>#<bean:write name="assumptionDetailList_id" property="accessFlag"/> onclick="javascript: getcheckedInterfaceId(this.checked,this.value);"> 
												</td>
											</logic:notEqual>	
										</tr>
								</logic:iterate>
							</logic:notEmpty>
							<logic:empty name="accessMatrixBean" property="listInterface">
								<tr bgcolor="#FF9255">
									<td background="#FF9255" colspan="4" align="Center">NO Records Found</td>
								</tr>
							</logic:empty>
							<logic:notEmpty name="accessMatrixBean" property="listInterface">
								<tr>
									<td colspan="4" align="center">
										<div class="buttonVSmall" onClick="javascript:submitForm();">Save</div>
									</td>
								</tr>
							</logic:notEmpty>	
						</table>
					</div>
				</td>
			</tr>
		</table>		
	</logic:present>
</html:form>
</div>
</BODY>
</html:html>
