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
<script type="text/javascript" src="npd/js/utility.js"></script>
<script type="text/javascript" src="npd/js/inputColor.js"></script>

<script type="text/javascript">

function checkForDuplicateStage()
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
	var stageName =trim(document.getElementById("stage").value);
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var result;
	
		//For checking whether duplicate stage exists.
    	result = jsonrpc.processes.checkDuplicateStage(stageName,document.getElementById("workflowId").value);
    
		if( result !=null)
		{
			
			if(result.stageid!=document.getElementById('stageId').value)
			{
			alert("Stage with same Name already exists");
			return false;
			}
		}//if result
		
		
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}


function saveStage()
{
	var errorString="";
	myform=document.getElementById('stageForm');
	setFormBean(myform);
	if(document.getElementById("stage").value==null||trim(document.getElementById("stage").value)=="")
	{
	errorString+="Please enter Stage Name."+"\n";
	}
	if(ValidateTextField(document.getElementById("stage"),'<%=request.getContextPath()%>',"Stage Name")==false)
	{
		return false;
	}
	if(document.getElementById("stageDescription").value==null||trim(document.getElementById("stageDescription").value)=="")
	{
	errorString+="Please enter Stage Desciption."+"\n";
	}
	if(ValidateTextField(document.getElementById("stageDescription"),'<%=request.getContextPath()%>',"Stage Description")==false)
	{
		return false;
	}
	else
	if(document.getElementById("stageDescription").value.length>100)
	{
	errorString+="Stage Description Cannot Be Greater Than 100 characters."+"\n";	
	}
	
	if(errorString!="")
	{
	alert(errorString);
	return false;
	}
	else
	{
	
	
	
	var aval=checkForDuplicateStage();
	if(aval==false)
	{
		return false;
	}
	else
	{
		showLayer();
		document.masterProjectPlanBean.action="<c:out value='${sessionScope.MenuContextPath}' /><%="/"+(String)request.getAttribute(AppConstants.INITIAL_SERVLET_REQUESTED)%>";
		return true;
	}
//	return checkForDuplicateStage();
	
	
	return true;
	}
}


function viewStage(val)
{
//	document.getElementById("stage").value=var1;
	document.getElementById("stage").value=document.getElementById("idstagename"+val).value;
	document.getElementById("stageId").value="";	
	document.getElementById("stageDescription").value=document.getElementById("idstageDescription"+val).value;
	
	document.getElementById('id_saveButton').style.display="block";
	document.getElementById('id_updateButton').style.display="none";		
}

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function showDetails(var1) {

	if(var1=='addTask')
	{
	document.masterProjectPlanBean.action="<c:out value='${sessionScope.MenuContextPath}' /><%="/"+(String)request.getAttribute(AppConstants.INITIAL_SERVLET_REQUESTED)%>?method=viewMasterPlan&addTask=addTask";
	showLayer();
	document.masterProjectPlanBean.submit();
	}
	if(var1=='updateTask')
	{
	document.masterProjectPlanBean.action="<c:out value='${sessionScope.MenuContextPath}' /><%="/"+(String)request.getAttribute(AppConstants.INITIAL_SERVLET_REQUESTED)%>?method=viewMasterPlan&updateTask=updateTask";
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
	document.masterProjectPlanBean.action="<c:out value='${sessionScope.MenuContextPath}' /><%="/"+(String)request.getAttribute(AppConstants.INITIAL_SERVLET_REQUESTED)%>?method=viewMasterPlan&finalize=finalize";
	showLayer();
	document.masterProjectPlanBean.submit();
	}
	

}

function goToSave()
{
	myform=document.getElementById('stageForm');
	myform.stage.value="";
	myform.stageId.value="";
	myform.stageDescription.value="";
	
	document.getElementById('id_saveButton').style.display="block";
	document.getElementById('id_updateButton').style.display="none";	
}

function modifyStage(val)
{
	document.getElementById("stage").value=document.getElementById("idstagename"+val).value;
	document.getElementById("stageId").value=document.getElementById("idstageid"+val).value;	

	document.getElementById("stageDescription").value=document.getElementById("idstageDescription"+val).value;	

	document.getElementById('id_saveButton').style.display="none";
	document.getElementById('id_updateButton').style.display="block";	
	
}

function gotoTaskPage(val)
{
	showDetails('addTask');
	document.getElementById('stageId').value=val;

}

function emptyStageForm()
{
	myform=document.getElementById('stageForm');
	myform.stage.value="";
	myform.stageId.value="";
	myform.stageDescription.value="";	
}

function fnForAskUserAfterInsert(msg)
{
	askMsg=msg+"\n"+"Do you want to Add Another Stage :";
	var answer=confirm(askMsg);
	if(answer)
	{
		//continue in same page
		emptyStageForm();
	}
	else
	{
		//goto to task addition page
		gotoTaskPage(document.getElementById('stageForm').stageId.value);
	}
}
function afterUpdate(msg)
{
	alert(msg);
	emptyStageForm();
}


function onBodyLoad()
{
	<html:messages id="message" message="true" property="recordInsertUpdateSuccess">
		fnForAskUserAfterInsert('<bean:write name="message"/>');
	</html:messages>
	<html:messages id="message" message="true" property="STAGE_UPDATION">
		afterUpdate('<bean:write name="message"/>');
	</html:messages>
	myForm=document.getElementById('stageForm');
	myForm.action.value="<%="/"+(String)request.getAttribute(AppConstants.INITIAL_SERVLET_REQUESTED)%>";
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
<table width="98%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Add
				Workflow</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<html:form action="/addMasterPlan" method="post" styleId="stageForm">
<html:hidden property="taskListLength"/>
	<table width="100%" border="2" class="tabledata" cellpadding="0"
		cellspacing="0" align="left">
		<tr align="center">
			<th colspan="4" scope="row">
			<table width="100%" border="0">
				<tr>
					<td><span id="firstButton" class="buttonSmall"
						onClick="showDetails('addTask')">Add/Edit Task</span> <span style="display: none;"
						id="fourthButton" class="buttonLagre"
						onClick="showDetails('updateTask')">Update/Remove Task</span><span
						id="thirdButton" class="buttonLagre"
						onClick="showDetails('finalize')">Finalize Workflow</span></td>

				</tr>
			</table>
			</th>
		</tr>
		<tr><td>
		<div class="error" align="center">
			<logic:present name="validation_errors">
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>
			</logic:present>
		</div>
		</td></tr>
		<tr>

			<td width="70%">
			<table width="100%" border="0" class="tabledata" cellpadding="0"
				cellspacing="0" align="center">
				<tr>
					<th width="30%" scope="row">
					<div align="left">ChangeOrder Category <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:select property="npdCategoryId"
						style="width:140px" disabled="true">
						<html:option value="-1">Select</html:option>
						<logic:notEmpty name="masterProjectPlanBean"
							property="npdCategoryList">
							<html:optionsCollection property="npdCategoryList"
								label="npdcatdesc" value="npdcatid" />
						</logic:notEmpty>
					</html:select></td>
				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="left">WorkflowDesc <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text property="workflowDesc"
						maxlength="100" style="width:140px" readonly="true" /></td>

				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="left">Stage <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text property="stage" maxlength="50"
						style="width:140px" /></td>

				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="left">Description<font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:textarea property="stageDescription" rows="3" cols="6" styleId="stageDescription"
						style="width:140px" /></td>

				</tr>
				
				<html:hidden property="workflowId" />
				<html:hidden property="npdCategoryId" />
				<html:hidden property="stageId" styleId="stageId" />				
				<tr align="center">
					<th colspan="2" scope="row">
					<table width="50%" border="0">
						<tr>
							<td>
							<div id="id_saveButton" style="display:block;">
							<html:submit property="method"
								onclick="return saveStage();">
								<bean:message key="submit.saveStage" bundle="ButtonResources" />
							</html:submit>
							</div>
							<div id="id_updateButton" style="display: none;">
								<html:submit property="method"
									onclick="return saveStage();">
									<bean:message key="submit.updateStage" bundle="ButtonResources" />
								</html:submit>
								<html:button property="method"
									onclick="goToSave();">
									Cancel
								</html:button>
							</div>
							
							</td>

						</tr>
					</table>
					</th>
				</tr>

			</table>
			</td>
			<td width="70%">
			<table width="100%" border="0" class="tabledata" cellpadding="0"
				cellspacing="0" align="center">
				<logic:notEmpty name="masterProjectPlanBean" property="stageList">
					<table width="100%" border="1" class="tabledata" cellpadding="3"
						cellspacing="1" align="center">


						<tr bgcolor="#FF9255">
							<th background="#FF9255" colspan="2">Existing Stages</th>

						</tr>
						<tr>
							<th></th>
						</tr>
						<%String[] colors={"alterRedBg","redBg"}; %>
						<logic:iterate id="stageList_id" name="masterProjectPlanBean"
							property="stageList" indexId="index1">
								<tr class="<%=colors[index1.intValue()%2]%>">
									<td><a href="#"
										onclick="viewStage('<%=index1%>');"><c:out
										value="${stageList_id.stagename}" /></a></td>
									<td><a href="#" onclick="modifyStage('<%=index1%>')">Edit</a>
										<input type="hidden" name="txtstagename<%=index1%>" id="idstagename<%=index1%>"
														value='<bean:write name="stageList_id" property="stagename"/>'>
										<input type="hidden" name="txtstageid<%=index1%>"  id="idstageid<%=index1%>"
										value='<bean:write name="stageList_id" property="stageid"/>'>	
										<input type="hidden" name="txtstagedesc<%=index1%>"  id="idstageDescription<%=index1%>"
										value='<bean:write name="stageList_id" property="stagedesc"/>'>											
																		
									</td>
								</tr>
						</logic:iterate>

					</table>
				</logic:notEmpty>
			</table>
		</tr>

	</table>


	<!-- input color -->
	<script type="text/javascript">
<!--
 // initInputHighlightScript();
//-->
</script>
</html:form>
</div>
</BODY>


</html:html>

