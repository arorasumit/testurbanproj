<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="com.ibm.ioes.npd.utilities.Messages"%>
<%@page import="com.ibm.ioes.npd.beans.ProjectPlanInstanceBean"%>

<%@page import="com.ibm.ioes.npd.beans.RoleEmployeeBean"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">
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
<script language="JavaScript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/utility.js"></script>
<script type="text/javascript" src="npd/js/scw.js"></script>
<script type="text/javascript" src="npd/js/timepicker.js"></script>
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>
<script type="text/javascript" src="npd/js/inputColor.js"></script>
<title>ChangeOrder Workflow</title>
<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>

<script type="text/javascript">

function checkTaskSelectionMessage()
{
	var myForm=document.getElementById('planForm');
	var var_selectedTaskId=myForm.selectedTaskId;
	
	if(var_selectedTaskId==null)
	{
		alert('No Task Present');
		return;
	}
	
	if(var_selectedTaskId.length!=null)
	{
		var isSelected=0;
		for(i=0;i<var_selectedTaskId.length;i++)
		{
			if(var_selectedTaskId[i].checked==true)
			{
				isSelected=isSelected+1;
			}
		}
		if(isSelected==0)
		{
			alert('No Task Selected');
			return false;
		}
	}
	else
	{
		if(var_selectedTaskId.checked==false)
		{
			alert('No Task Selected');
			return false;
		}
	}
	return true;
}
function checkSelection()
{
	var temp_chk=0;
		ids=document.forms[0].selectedRoleMapping;
		if(ids==null)
		{	
			alert('No Role Present.');
			return false;
		}
		if(document.forms[0].selectedRoleMapping.length!=null)
		{
			for(i=0;i<document.forms[0].selectedRoleMapping.length;i++ )
			{
				if(document.forms[0].selectedRoleMapping[i].checked==true)
				{
					temp_chk=temp_chk+1;
				}
			}
		}
		else
		{
			if(document.forms[0].selectedRoleMapping.checked==true)
				{
					temp_chk=temp_chk+1;
				}
		}
		if(temp_chk==0)
		{
		 alert('No Role Selected. Please Select a Role.');
		return false;
		}
		return true;
}
function planActions(val)
{

	if(val=='close')
	{
		window.close();
	}
	else if(val=='saveUpdateRoleEmployee')
	{
		var myForm=document.getElementById('planForm');
		if(checkSelection()==false)
		{
			return;
		}
		myForm.method.value='saveUpdateRoleEmployee';
		
		showLayer();
		myForm.submit();
	}

}

function removeCheckHead()
{
	javascript:document.getElementById('chkHead').checked=false;
}
function fnCheckAll()
{
	ids=document.forms[0].selectedRoleMapping;
		if(ids==null)
		{	
			alert('No Role Present.');
			document.getElementById('chkHead').checked=false;
			return false;
		}

	var i;
	if(document.getElementById("chkHead").checked==true)
	{	
		if(document.forms[0].selectedRoleMapping.length!=null)
		{	for(i=0;i<document.forms[0].selectedRoleMapping.length;i++ )
			{
				if(document.forms[0].selectedRoleMapping[i].disabled==false)
				{
					document.forms[0].selectedRoleMapping[i].checked=true;
				}
			}
		}
		else
		{
			if(document.forms[0].selectedRoleMapping.disabled==false)
			{
				document.forms[0].selectedRoleMapping.checked=true;
			}
		}
	}
	else
	{
		if(document.forms[0].selectedRoleMapping.length!=null)
		{
			for(i=0;i<document.forms[0].selectedRoleMapping.length;i++ )
			{
				document.forms[0].selectedRoleMapping[i].checked=false;
			}
		}
		else
		{
			document.forms[0].selectedRoleMapping.checked=false;
		}	

	}

}

function checkForm()
{
	if(ValidateTextField(document.getElementById("id_taskName_search"),'<%=request.getContextPath()%>',"Search Field-Task Name")==false)
	{
		return false;
	}
	if(ValidateTextField(document.getElementById("id_roleHolder_search"),'<%=request.getContextPath()%>',"Search Field-Role Holder")==false)
	{
		return false;
	}
	if(ValidateTextField(document.getElementById("id_assignedTo_search"),'<%=request.getContextPath()%>',"Search Field-Assigned To")==false)
	{
		return false;
	}


	return true;
}

function searchSubmit()
{
	myform=document.getElementById('planForm');
	myform.elements["tasksPS.sortByColumn"].value="";
	myform.elements["tasksPS.sortByOrder"].value="";
	myform.elements["tasksPS.pageNumber"].value=1;
	
	if(checkForm()==false)
	{
		return ;
	}
	//showLayer();
	showLayer();
	myform.method.value='viewEditTasks';
	myform.submit();
}

function pagingSubmit(val)
{
	myform=document.getElementById('planForm');
	myform.elements["tasksPS.pageNumber"].value=val;
	//showLayer();
	myform.method.value='viewEditTasks';
	if(checkForm()==false)
	{
		return ;
	}	
	showLayer();
	myform.submit();
}
function sortSubmit(sortBy)
{
	myform=document.getElementById('planForm');
	myform.elements["tasksPS.pageNumber"].value=1;
	if(myform.elements["tasksPS.sortByColumn"].value==sortBy)
	{
		if(myform.elements["tasksPS.sortByOrder"].value=="decrement")
		{
			myform.elements["tasksPS.sortByOrder"].value="increment";
		}
		else
		{
			myform.elements["tasksPS.sortByOrder"].value="decrement";
		}
	}
	else
	{
		myform.elements["tasksPS.sortByColumn"].value=sortBy;
		myform.elements["tasksPS.sortByOrder"].value="decrement";
	}
	if(checkForm()==false)
	{
		return ;
	}
	showLayer();
	myform.method.value='viewEditTasks';	
	myform.submit();
}


function fnRadioSelection()
{
	var selStage = document.getElementById('stage<bean:write name="projectPlanInstanceBean" property="selectedStageId"/>');
	if(selStage!=null)
	{
		selStage.checked=true;
	}
}

function fnParentPagingSubmit()
{
	myform=window.opener.document.forms[0];
//	alert(myform);
//	myform.elements["tasksPS.pageNumber"].value=val;
	myform.method.value='viewEditTasks';
	myform.submit();
	
}
function fnOnBodyLoad()
{
	//fnRadioSelection();
	document.body.click();
	//forDisablingAssigned();
	<logic:present name="REFRESHPARENT">
		fnParentPagingSubmit();
		
	</logic:present>
}
</script>

</head>
<body onload="javascript:fnOnBodyLoad()">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
		<br /><br /><br /><br /><br /><br /><br /><br />
	    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
	    <br /><br /><br />
</div>
<html:form action="/attachEditProjectPlan" styleId="planForm">
<bean:define id="formBean" name="projectPlanInstanceBean"></bean:define>
<input type="hidden" name="method"/>
<html:hidden property="projectId"/>
<html:hidden property="projectWorkflowId"/>




<table width="50%" border="0" cellpadding="0" cellspacing="0"
			class="border" align="center">
<tr valign="middle" id="newProduct">
	<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Map Employee</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1">
				</td>
			</tr>
	</table>
</tr>

			<%String []colors=new String[]{"redBg","alterRedBg"}; %>
<tr>
	<td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="tabledata" align="center">
		<html:messages id="message" message="true" >
		<tr>
			<td align="center" style="color: red;">
				
					<li><bean:write name="message"/></li>

			</td>
		</tr>
		</html:messages>
	<tr>
		
		<td align="left">
			<table width="100%" border="0" class="tabledata" cellpadding="3"
							cellspacing="1" align="center">
				
					
				<tr class="rptHeader">
					<th width="4%" >
						<input id="chkHead" type="checkbox" class="none" onClick="javascript:fnCheckAll();">
					</th>
					<th width="4%" >S.No.</th>		
					<th width="5%" >Role Name</th>
					<th width="15%" >Assigned To</th>
				</tr>
				<logic:empty name="formBean" property="roleView">
					<tr>
						<td colspan="9" align="center">No Role Present</td>
					</tr>
				</logic:empty>
				<logic:notEmpty name="formBean" property="roleView">


				<logic:iterate id="roleRow"	name="formBean" property="roleView" indexId="index">
						<tr class="<%=colors[index.intValue()%2]%>">
							<td align="center">&nbsp;
							
								<logic:equal name="roleRow" property="employeeId" value="0">
								 	<input type="checkbox" name="selectedRoleMapping" onclick="javascript:removeCheckHead();" 
										value="<%=index %>" />	&nbsp			
								</logic:equal>
								<logic:notEqual name="roleRow" property="employeeId" value="0">
									<input type=checkbox DISABLED name="selectedRoleMapping" onclick="javascript:removeCheckHead();" 
										value="<%=index %>" />	&nbsp			
								</logic:notEqual>								
							</td>
							<td align="center">
								<%=index.intValue()+1%>
								<html:hidden name="roleRow" property="roleId" indexed="true"/>
							</td>

							
							<td align="center"><bean:write name="roleRow" property="roleName"/></td>

							<td align="center">
								<input type="hidden" name="roleRow[<%=index.intValue()%>].newAssigned">
								<html:select name="roleRow" property="employeeId" indexed="true" style="width:200px">
									<html:option value="-1">-Select-</html:option>
									<logic:notEmpty name="roleRow" property="roleUserList">
										<html:optionsCollection name="roleRow" property="roleUserList"
																		label="empname" value="npdempid"	/>
									</logic:notEmpty>
								</html:select>
							</td>																								
																			
							
						</tr>
				</logic:iterate>

				
				</logic:notEmpty>
			</table>
		</td>
	</tr>
	</table>
	</td>
</tr>
<tr>
	<td>&nbsp;
	</td>
</tr>
<tr>
	<td>
		<table width="80%" border="0" align="center" >
						<tr>
							
							<td align="center" nowrap><span id="search" class="buttonLagre"
								onClick="planActions('saveUpdateRoleEmployee');">Update Assigned To<span></td>
							<td align="center" nowrap><span id="search" class="buttonLagre"
								onClick="planActions('close');">Close</td>
						</tr>						
					</table>
	</td>
</tr>

</table>
</html:form>
</div>
</BODY>


</html:html>
