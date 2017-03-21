<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="java.util.Date"%>

<html>
<head>
<title>Role Access Matrix</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">

var counter = 1;
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}
function fnCheckAll()
{
	ids=document.forms[0].chkbox;
		if(ids==null)
		{	
			alert('No Request Present.');
			document.getElementById('chkHead').checked=false;
			return false;
		}

	var i;
	if(document.getElementById("chkHead").checked==true)
	{	
		if(document.forms[0].chkbox.length!=null)
		{	for(i=0;i<document.forms[0].chkbox.length;i++ )
			{
				document.forms[0].chkbox[i].checked=true;
				document.acessMatrixBean.allowedInterfaceId.value = document.acessMatrixBean.allowedInterfaceId.value+'&'+document.forms[0].chkbox[i].value;
				document.acessMatrixBean.prohibitedInterfaceId.value= ''
				
			}
		}
		else
		{
			document.forms[0].chkbox.checked=true;
			document.acessMatrixBean.allowedInterfaceId.value = document.acessMatrixBean.allowedInterfaceId.value+'&'+document.forms[0].chkbox.value;
			
		}
	}
	else
	{
		if(document.forms[0].chkbox.length!=null)
		{
			for(i=0;i<document.forms[0].chkbox.length;i++ )
			{
				document.forms[0].chkbox[i].checked=false;
				document.acessMatrixBean.prohibitedInterfaceId.value = document.acessMatrixBean.prohibitedInterfaceId.value+'&'+document.forms[0].chkbox[i].value;		
				document.acessMatrixBean.allowedInterfaceId.value= ''
			}
		}
		else
		{
			document.forms[0].chkbox.checked=false;
			document.acessMatrixBean.prohibitedInterfaceId.value = document.acessMatrixBean.prohibitedInterfaceId.value+'&'+document.forms[0].chkbox.value;		
		}	
		
	}

}

function removeCheckHead()
{
	javascript:document.getElementById('chkHead').checked=false;
	var temp_chk=0;
		if(document.forms[0].chkbox.length!=null)
		{
			for(i=0;i<document.forms[0].chkbox.length;i++ )
			{
				if(document.forms[0].chkbox[i].checked==true)
				{
					temp_chk=temp_chk+1;
				}else if(document.forms[0].chkbox[i].checked==false){
				
					temp_chk=temp_chk+1;
				
				}
			}
		}
		else 
		{	if(document.forms[0].chkbox.checked==true) {
					temp_chk=temp_chk+1;
				} else if(document.forms[0].chkbox.checked==false)
		{
		
					temp_chk=temp_chk+1;
			
		}
		}
		
}

function viewInterfaceDetails()
{
	if(document.forms[0].roleFilter.value=="-1")
	{
		alert("Select a Role First !!");
		return false;
	}

	myform=document.getElementById('searchForm');	
	document.acessMatrixBean.selectedRoleID.value=myform.roleFilter.value;
	document.acessMatrixBean.selectedModuleID.value=myform.moduleFilter.value;
	document.searchForm.action="<%=request.getContextPath()%>/accessMatrix.do?method=getInterfaceList";
	document.searchForm.submit();
}
function getcheckedInterfaceId(chk,id)
{
	if(chk)
	{
		document.acessMatrixBean.allowedInterfaceId.value = document.acessMatrixBean.allowedInterfaceId.value+'&'+id;
	}
	else
	{
		document.acessMatrixBean.prohibitedInterfaceId.value = document.acessMatrixBean.prohibitedInterfaceId.value+'&'+id;		
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
	document.acessMatrixBean.selectedRoleID.value=myform.roleFilter.value;
	document.acessMatrixBean.selectedModuleID.value=myform.moduleFilter.value;
	document.searchForm.action="<%=request.getContextPath()%>/accessMatrix.do?method=UpdateMatrix&roleID";
	document.searchForm.submit();
}
path='<%=request.getContextPath()%>';

</script>

<body>
	<html:form action="/accessMatrix" styleId="searchForm" method="post">
		<bean:define id="formBean" name="acessMatrixBean"></bean:define>
		<input type="hidden" name="method" />
		<html:hidden property="selectedRoleID" value=""/>
		<html:hidden property="selectedModuleID" value=""/>
		<html:hidden property="methodName" />
		<html:hidden property="allowedInterfaceId" value=""/>
		<html:hidden property="prohibitedInterfaceId" value=""/>
		
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">
				<td><img src="gifs/top/home.gif" onClick="goToHome();"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> <span>R</span>OLE ACCESS MATRIX</div>
		<div border="1" class="error" align="center">
			<logic:present name="validation_errors">
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>
			</logic:present>
			<html:messages id="message" message="true">
				<li><font color="red"><bean:write name="message"/></font></li>
			</html:messages>
		</div>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Search</b></legend>
			<br/>
			<table border="0" cellspacing="0" cellpadding="0" style="margin-top:7px " width="100%">
				<tr>
				<td align="right" style="font-size:9px" width="40%">Role</td>
				<td width="1%">
				<td align="left" style="font-size:9px" width="16%">
				<html:select property="roleFilter" style="width:140px" styleClass="inputBorder1">
					<html:option value="-1">Select a Role</html:option>
					<logic:notEmpty name="acessMatrixBean" property="listRoles">
						<html:optionsCollection property="listRoles" label="roleName" value="roleID" />
					</logic:notEmpty>
				</html:select>
				</td>
				<td align="left" width="43%"></td>
				</tr>
				<tr>
				<td align="right" style="font-size:9px" width="40%">Module</td>
				<td width="1%">
				<td align="left" style="font-size:9px" width="16%">
				<html:select property="moduleFilter" style="width:140px" styleClass="inputBorder1">
					<html:option value="-1">All</html:option>
					<logic:notEmpty name="acessMatrixBean" property="moduleList">
						<html:optionsCollection property="moduleList" label="moduleName" value="modID" />
					</logic:notEmpty>
				</html:select>
				</td>
				<td align="left" width="43%">
				<input type = "button" value="Search" style="float:left;" onclick="javascript:viewInterfaceDetails();" />&nbsp;</td>
				</tr>
						
			</table>
			</fieldset>	
			<fieldset border="1" align="center" class="border2" >
			<legend> <b>Role Access Matrix</b></legend>
			<br/>
			<logic:present name="acessMatrixBean" property="listInterface">
			<table class="tab2" style="overflow:auto;" width="70%" border="1" align="center" >
			<tr>
					<td align="center" style="font-size:9px"><b>Interface Name</b></td>
					<td align="center" style="font-size:9px"><b>Access</b><input type="checkbox" id="chkHead" onclick="fnCheckAll();"></td>
					
			</tr>
			<logic:notEmpty name="acessMatrixBean" property="listInterface">
				<%int sno=0;%>
				<logic:iterate id="assumptionDetailList_id" name="acessMatrixBean" property="listInterface" indexId="index1">
					<%sno=sno+1;%>
					<%if((index1.intValue())%2==0) {%>
						<tr class="lightBg" align="left">
					<% }else{ %>
						<tr class="normal" align="left">
					<% } %>
					<td width="70%"><bean:write name='assumptionDetailList_id' property='interfaceName' /></td>
						<logic:equal value="1" name="assumptionDetailList_id" property="accessFlag">		
							<td align="center" width="15%">
								<input type="checkbox" id="chkbox" checked="checked" name="interface" value='<bean:write name="assumptionDetailList_id" property="interfaceID" />#<bean:write name="assumptionDetailList_id" property="accessFlag"/>' onclick="javascript: getcheckedInterfaceId(this.checked,this.value);"> 
							</td>
						</logic:equal>	
						<logic:notEqual value="1" name="assumptionDetailList_id" property="accessFlag">		
							<td align="center" width="15%">
								<input type="checkbox" id="chkbox" name="interface" value=<bean:write name="assumptionDetailList_id" property="interfaceID"/>#<bean:write name="assumptionDetailList_id" property="accessFlag"/> onclick="javascript:getcheckedInterfaceId(this.checked,this.value);"> 
							</td>
						</logic:notEqual>	
					</tr>
					</logic:iterate>
					<tr>
						<td colspan="4" align="center">
							<input type = "button" value="Save" onclick="javascript:submitForm();" />
						</td>
					</tr>
							
							
				</logic:notEmpty>
				<logic:empty name="acessMatrixBean" property="listInterface">
					<tr>
						<td colspan="3" align="Center" ><font color="red"><b>No Records Found</b></font></td>
					</tr>
				</logic:empty>
	</table>
	</logic:present>
</fieldset>
</html:form>
</body>
</html>
