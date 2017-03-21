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
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">
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
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>
<script type="text/javascript" src="npd/js/inputColor.js"></script>

<script type="text/javascript">
function goToHome()
{
	var myForm=document.getElementById('myform');
	//myForm.toExcel.value='false';
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}

function showDetails(var1) {

	if(var1=='addStage')
	{
	document.masterProjectPlanBean.action="/IOES/addMasterPlan.do?method=viewMasterPlan&addStage=addStage";
	showLayer();
	document.masterProjectPlanBean.submit();
	}
	if(var1=='addTask')
	{	
	document.masterProjectPlanBean.action="/IOES/addMasterPlan.do?method=viewMasterPlan&addTask=addTask";
	showLayer();
	document.masterProjectPlanBean.submit();
	}
	if(var1=='updateTask')
	{
	document.masterProjectPlanBean.action="/IOES/addMasterPlan.do?method=viewMasterPlan&updateTask=updateTask";
	showLayer();
	document.masterProjectPlanBean.submit();
	}
	if(var1=='finalize')
	{	
	if(document.masterProjectPlanBean.taskListLength.value=='0')
	{
		alert('Cannot Finalize - At Least One task should be present');
		return false;
	}	
	if(!fnGetPreviousTaskStatus())
	{		
		return false;
	}
	var mymsg="";
	<logic:notPresent name="masterProjectPlanBean" property="firstTask">
		mymsg=mymsg+"\n* No First Task Present";
	</logic:notPresent>
	<logic:notPresent name="masterProjectPlanBean" property="lastTask">
		mymsg=mymsg+"\n* No Last Task Present";
	</logic:notPresent>
	<logic:present name="masterProjectPlanBean" property="noPreviousDefinedTasks">
		<logic:notEmpty name="masterProjectPlanBean" property="noPreviousDefinedTasks">
			mymsg=mymsg+"\n* No Previous Task Defined For Following Tasks :-";
			<logic:iterate id="row" name="masterProjectPlanBean" property="noPreviousDefinedTasks">
				mymsg=mymsg+"\n  -"+"<bean:write name='row' property='taskname'/>";
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	<logic:present name="masterProjectPlanBean" property="emptyRoles">
		<logic:notEmpty name="masterProjectPlanBean" property="emptyRoles">
			mymsg=mymsg+"\n* Following Roles of Workflow does not have any employees assigned to them   :-";
			<logic:iterate id="row" name="masterProjectPlanBean" property="emptyRoles">
				mymsg=mymsg+"\n  -"+"<bean:write name='row' property='rolename'/>";
			</logic:iterate>
		</logic:notEmpty>
	</logic:present>
	
	
	if(mymsg!="")
	{
		mymsg="Cannot Finalize Workflow : "+"\n"+mymsg;
		alert(mymsg);
		return false;
	}	
	document.masterProjectPlanBean.action="/IOES/editMasterPlan.do?method=viewMasterPlan&finalize=finalize";	
	showLayer();	
	document.masterProjectPlanBean.submit();
	}

}
function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function onBodyLoad()
{
	myForm=document.getElementById('myform');
	myForm.action.value="<%="/"+(String)request.getAttribute(AppConstants.INITIAL_SERVLET_REQUESTED)%>";
}
/*Function		:fnGetPreviousTaskStatus
*return type	:boolean
*@Author		:Anil Kumar
*Date			:07-02-2011
*Purpose		:To validation Previous task status when finalize the task in add new task,
*					through master page
*/
function fnGetPreviousTaskStatus()
{
	var appContextPath = '<%=request.getContextPath()%>';
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var isValid = jsonrpc.processes.isWorkflowValid(document.forms[0].workflowId.value,"master");
	if(isValid==1)
	{
		alert('Invalid workflow.Cannot Continue.Edit Previous task in proper order!');
		return false;	
	}	
	else
		return true;
}
</script>

</head>
<body onload="onBodyLoad()">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
		<br /><br /><br /><br /><br /><br /><br /><br />
	    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
	    <br /><br /><br />
</div>
<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">				
				<td><img src="gifs/top/home.gif" title="Home" onClick="goToHome('Home');"></img></td>
			</tr>
</table>
<div class="head"> <span>Edit Workflow</span> </div>
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
		<!--<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Edit
				Workflow</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>-->
		</td>
	</tr>
</table>
<html:form action="/addMasterPlan" method="post" styleId="myform">
<html:hidden property="taskListLength"/>
	<table width="83%" border="2" class="tab2" cellpadding="0"
		cellspacing="0" align="center">

		<tr>

			<td width="70%" height="179">
			<table width="90%" border="0" class="tab2" cellpadding="0"
				cellspacing="0" align="center">
				<tr>
					<th width="30%" scope="row">
					<div align="left">ChangeOrder Category <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:select property="npdCategoryId"
						style="width:400px" styleClass="inputBorder1" disabled="true">
						<html:option value="-1">Select</html:option>
						<logic:notEmpty name="masterProjectPlanBean"
							property="npdCategoryList">
							<html:optionsCollection property="npdCategoryList"
								label="npdcatdesc" value="npdcatid" />
						</logic:notEmpty>
					</html:select></td>
				</tr>
				<!--<tr>
					<th width="30%" scope="row">
					<div align="left">WorkflowDesc <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text property="workflowDesc"
						maxlength="100" styleClass="inputBorder1" style="width:140px;display:none;" readonly="true" /></td>

				</tr>-->
				<html:hidden property="workflowId" />
				<html:hidden property="npdCategoryId" />
				<tr align="center">
					<th colspan="4" scope="row">
					<table width="100%" border="0" align="center">
						<tr>
							<td> <span
								id="thirdButton" class="buttonLarge"
								onClick="showDetails('addTask')">Add/Edit Task</span> <span style="display: none;"
								id="fourthButton" class="buttonLarge"
								onClick="showDetails('updateTask')">Update/Remove Task</span><span
								id="thirdButton" class="buttonLarge"
								onClick="showDetails('finalize')">Finalize Workflow</span></td>
						</tr>
					</table>
					</th>
				</tr>
				

			</table>
			<table width="80%" align="center"><tr><td align="left">
					<div class="error" >
						<logic:present name="validation_errors">
							<logic:iterate id="error_row" name="validation_errors" scope="request">
									<font color="red"><bean:write name="error_row" /></font><BR>
							</logic:iterate>
						</logic:present>
					</div></td></tr>
			</table>

			<!-- input color --> <script type="text/javascript">
<!--
 // initInputHighlightScript();
//-->
</script>
</td>
</tr>
</table>
</html:form>
</div>
</BODY>
</html:html>
