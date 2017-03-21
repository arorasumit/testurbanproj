<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
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

<script type="text/javascript">
// to check whether stage already exists in the database.

function checkForDuplicateStage()
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
	var stageName =trim(document.getElementById("stage").value);
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var result;
	
		//For checking whether duplicate stage exists.
    	result = jsonrpc.processes.checkDuplicateStage(stageName,document.getElementById("npdCategoryId").value);
    
		if( result !=null)
		{
			if(result)
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

function getTaskListForACategory(objId,var1)
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
	var counter=0;
		
	   jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	  
    	var result;
       	var obj = document.getElementById(objId);
		if(objId=='taskId')
		{	
		//For populating Previous task List
    	result = jsonrpc.processes.getTaskListForACategory(var1,null);    	
    	
			for(i=obj.options.length-1;i>=0 ;i--)
			{
			obj.options[i] = null;
			}
			obj.options[0] = new Option("Select", "-1");
			if( result !=null && result.list.length > 0)
			{
				//alert("List Exists");
				for(i=0;i < result.list.length;i++)
				{
				var name = result.list[i].taskname;
				var id=result.list[i].taskid;
				obj.options[i+1] = new Option(name,id);
				
				}//for
			}//if result
		}
		else
		{
		//For populating Stage and Task hyperlinks for updation.
    	result = jsonrpc.processes.getTaskListForACategory(null,var1); 
    				for(x=document.getElementById("stageAndTaskList").rows.length-1;x>=0;x--)
					{
						document.getElementById("stageAndTaskList").deleteRow(x);
					}
			if( result !=null && result.list.length > 0)
			{
				document.getElementById('showTask').style.display="none";
				var stageid = '';
				
				for(i=0;i < result.list.length;i++)
				{
					if(stageid!=result.list[i].stage.stageid)
					{//creating Stage row
					var newRow = document.all("stageAndTaskList").insertRow();
				oCell = newRow.insertCell();
				oCell.innerHTML='<b>'+result.list[i].stage.stagename+'<b><br>';
								
					}
					//creating Task as hyperlink
				var newRow = document.all("stageAndTaskList").insertRow();
				oCell = newRow.insertCell();
				oCell.innerHTML='&nbsp;&nbsp;&nbsp;<a href="" onclick="return getTaskDetails('+result.list[i].taskid+','+result.list.length+','+i+');" >'
				+result.list[i].taskname+'</a>';
				stageid =result.list[i].stage.stageid;					
				}//for
			}//if result
			else
			{
				var newRow = document.all("stageAndTaskList").insertRow();
				oCell = newRow.insertCell();
				oCell.innerHTML='<td><font color="red">No Task for NPD the selected Category</font></td>';
			}
		}
		
		
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}

// Get the Task Related Details for a Particular Task

function getTaskDetails(taskid,taskListLength,taskNo)
{

var appContextPath = '<%=request.getContextPath()%>';
    	
	try
	{	
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var result;
    	
		//For checking whether duplicate stage exists.
    	result = jsonrpc.processes.getTaskDetails(taskid);
        	
		if( result !=null)
		{
			if(result.taskid>0)
			{
			document.getElementById('showTask').style.display="";
			document.getElementById('task_update').value = result.taskname;
			document.getElementById('taskDescription_update').value = result.taskdesc;
			document.getElementById('plannedDuration_update').value = result.planneddurationdays;
			document.getElementById('stakeHolderId_update').value = result.stakeholder.npdempid;
			document.getElementById('remarks_update').value = result.taskinstructionremarks;
			document.getElementById('updatedTaskID').value=taskid;
			document.getElementById('taskListLength').value=taskListLength;
			document.getElementById('taskNo').value=taskNo;
			}
		}//if result
		return false;
		
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}

// to check whether Task already exists in the database.

function checkForDuplicateTask()
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
	var taskName =trim(document.getElementById("task").value);
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var result;
	
		//For checking whether duplicate stage exists.
    	result = jsonrpc.processes.getDuplicateTask(taskName,document.getElementById("stageId").value);
    
		if( result !=null)
		{	
			if(result.taskid>0)
			{
			alert("Task with same Name for the selected stage already exists");
			return false;
			}
			else
			{
			return true;
			}
			
		}//if result
		
		
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}

// to check whether task already exists in the database as First or Last task.

function checkForDuplicateFirstAndLastTask()
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var result;
		//For checking whether duplicate task exists at First and Last position
    	result = jsonrpc.processes.checkForDuplicateFirstAndLastTask(document.getElementById("first").checked,document.getElementById("last").checked,document.getElementById("stageId").value);
		if( result !=null)
		{
			if(result)
			{
				if(document.getElementById("first").checked)
				{
				alert("First stage already exists for this Workflow");
				}
				if(document.getElementById("last").checked)
				{
					alert("Last stage already exists for this Workflow");
				}
		
			return false;
			}
			else
			{
			return true;
			}
			
		}//if result
		
		
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}

function getTemplateList(objId)
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
			var obj = document.getElementById(objId);
		if(document.getElementById("attachment").checked)
		{
			jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	   		var result;
    		
    		//For populating Template List
    		result = jsonrpc.processes.getTemplateDocList();
    				
			for(i=obj.options.length-1;i>=0 ;i--)
			{
			obj.options[i] = null;
			}
			obj.options[0] = new Option("Select", "-1");
			if( result !=null && result.list.length > 0)
			{
				for(i=0;i < result.list.length;i++)
				{
				var name = result.list[i].refdocname;
				var id=result.list[i].refdocid;
				obj.options[i+1] = new Option(name,id);
				
				}//for
			}//if result
		}
		else
		{
		for(i=obj.options.length-1;i>=0 ;i--)
			{
			obj.options[i] = null;
			}
			obj.options[0] = new Option("Select", "-1");
		}
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}

function loadEmployeeDetails(empid)
{
	document.userNpdSpocs.action="./manageNpdSpocs.do?method=viewMasterPlan";
	document.userNpdSpocs.submit();  
	
}


function saveWorkflow()
{
	var errorString="";
	if(document.getElementById("npdCategoryId").value==-1)
	{
	errorString+="Please Select ChangeOrder Category"+"\n";
	}
	if(document.getElementById("workflowDesc").value==null||trim(document.getElementById("workflowDesc").value)=="")
	{
	errorString+="Please enter Workflow Description"+"\n";
	}
	if(errorString!="")
	{
	alert(errorString);
	return false;
	}
	else
	{
	return true;
	}
}



function saveStage()
{
	var errorString="";
	if(document.getElementById("npdCategoryId_stage").value==-1)
	{
	errorString+="Please Select ChangeOrder Category"+"\n";
	}
	if(document.getElementById("stage").value==null||trim(document.getElementById("stage").value)=="")
	{
	errorString+="Please enter Stage"+"\n";
	}
	if(errorString!="")
	{
	alert(errorString);
	return false;
	}
	else
	{
	return checkForDuplicateStage();
	return true;
	}
}



function saveTask()
{
	var errorString="";
	if(document.getElementById("stageId").value==-1)
	{
	errorString+="Please Select Stage"+"\n";
	}
	if(document.getElementById("task").value==null||trim(document.getElementById("task").value)=="")
	{
	errorString+="Please enter Task"+"\n";
	}
	if(document.getElementById("taskDescription").value==null||trim(document.getElementById("taskDescription").value)=="")
	{
	errorString+="Please enter Task Description"+"\n";
	}
	else
	{
	if(chkTaskDescriptionLength(document.getElementById("taskDescription").value))
		{
		errorString+="";
		}
	}
	if(!document.getElementById("first").checked)
	{	
		if(document.getElementById("taskId").value==-1)
		{
		errorString+="Please select a Previous Task"+"\n";
		}
	}
	if(document.getElementById("taskId").value!=-1&&document.getElementById("first").checked)
	{
		errorString+="Please select either First or select a Previous Task"+"\n";
	}
	if(document.getElementById("stakeHolderId").value==-1)
	{
	errorString+="Please select a StakeHolder"+"\n";
	}
	//alert(isInteger(document.getElementById("plannedDuration").value));
	if(document.getElementById("plannedDuration").value==null||trim(document.getElementById("plannedDuration").value)=="")
	{
	errorString+="Please enter Planned Duration"+"\n";
	}
	else
		{
			if(!isInteger(document.getElementById("plannedDuration").value))
			{
			errorString+="Please Enter a Positive No. for Planned Duration"+"\n";
			}
		}
	if(document.getElementById("attachment").checked)
	{
		if(document.getElementById("templateId").value==-1)
		{
		errorString+="Please select a Template"+"\n";
		}
	
	}
	if(document.getElementById("remarks").value==null||trim(document.getElementById("remarks").value)=="")
	{
	errorString+="Please enter Remarks"+"\n";
	}
	else
	{
		if(chkRemarksLength(document.getElementById("remarks").value))
		{
		errorString+="";
		}
	}
	if(errorString!="")
	{
	alert(errorString);
	return false;
	}
	else
	{

		if(checkForDuplicateTask())
		{
			return checkForDuplicateFirstAndLastTask();
		}
		else
		{
		return false;
		}

	}
}

function chkRemarksLength(remarks)
{
	if (remarks != '' && 
	trim(remarks) != '' && 
	trim(remarks).length >1000)
	{
		alert ("Remarks cannot contain more then 1000 characters");
		return false;
	}
	return true;
}

function chkTaskDescriptionLength(description)
{
	if (description != '' && 
	trim(description) != '' && 
	trim(description).length >100)
	{
		alert ("Task Description cannot contain more then 100 characters");
		return false;
	}
	return true;
}

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function showDetails(var1) {

	if(var1=='addStage')
	{
	document.getElementById(var1).style.display="";
	document.getElementById('addTask').style.display="none";
	document.getElementById('updateTask').style.display="none";
	document.getElementById('addWorkflow').style.display="none";
	document.getElementById('showTask').style.display="none";
	document.getElementById('messageBody').style.display="none";
	
	}
	if(var1=='addTask')
	{
	document.getElementById(var1).style.display="";
	document.getElementById('addStage').style.display="none";
	document.getElementById('updateTask').style.display="none";
	document.getElementById('addWorkflow').style.display="none";
	document.getElementById('showTask').style.display="none";
	document.getElementById('messageBody').style.display="none";
	}
	if(var1=='updateTask')
	{
	document.getElementById(var1).style.display="";
	document.getElementById('addStage').style.display="none";
	document.getElementById('addTask').style.display="none";
	document.getElementById('addWorkflow').style.display="none";
	document.getElementById('showTask').style.display="none";
	document.getElementById('messageBody').style.display="none";
	}
	else if(var1=='addWorkflow')
	{
	document.getElementById(var1).style.display="";
	document.getElementById('addStage').style.display="none";
	document.getElementById('addTask').style.display="none";
	document.getElementById('updateTask').style.display="none";
	document.getElementById('showTask').style.display="none";
	document.getElementById('messageBody').style.display="none";
	}

}

// to delete the selected task from database.

function deleteSelectedTask()
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
	var taskListLength = document.getElementById('taskListLength').value;
	var taskNo=document.getElementById('taskNo').value;
	if(taskNo==0||taskNo==(taskListLength-1))
	{
	alert("First & Last Task Cannot be Deleted");
	return false;	
	}
	
	var taskId = document.getElementById('updatedTaskID').value
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var result;
	
		//For deleting the selected Task .
    	result = jsonrpc.processes.deleteTask(taskId);
    
		if( result !=null)
		{
			if(!result)
			{
			alert("This Task cannot be deleted");
			return false;
			}
			else
			{
			getTaskListForACategory('masterProjectPlanBean_id',document.getElementById('npdCategoryId_update').value);
			var newRow = document.all("stageAndTaskList").insertRow();
				oCell = newRow.insertCell();
				oCell.innerHTML='<td><font color="red">Task is deleted successfully</font></td>'
				
			}
		}//if result
		
		
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}

// to update the selected task in database.

function updateSelectedTask()
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
	
		var errorString="";
		if(document.getElementById("task_update").value==null||trim(document.getElementById("task_update").value)=="")
		{
		errorString+="Please enter Task"+"\n";
		}
		if(document.getElementById("taskDescription_update").value==null||trim(document.getElementById("taskDescription_update").value)=="")
		{
		errorString+="Please enter Task Description"+"\n";
		}
		else
			{
			if(chkTaskDescriptionLength(document.getElementById("taskDescription_update").value))
			{
			errorString+="";
			}
			}
		if(document.getElementById("stakeHolderId_update").value==-1)
		{
		errorString+="Please select a StakeHolder"+"\n";
		}
		//alert(isInteger(document.getElementById("plannedDuration").value));
		if(document.getElementById("plannedDuration_update").value==null||trim(document.getElementById("plannedDuration_update").value)=="")
		{
		errorString+="Please enter Planned Duration"+"\n";
		}
		else
		{
			if(!isInteger(document.getElementById("plannedDuration_update").value))
			{
			errorString+="Please Enter a Positive No. for Planned Duration"+"\n";
			}
		}
		if(document.getElementById("remarks_update").value==null||trim(document.getElementById("remarks_update").value)=="")
		{
		errorString+="Please enter Remarks"+"\n";
		}
		else
		{
			if(chkRemarksLength(document.getElementById("remarks_update").value))
			{
			errorString+="";
			}
		}
	if(errorString!="")
	{
	alert(errorString);
	return false;
	}
	
	var taskId = document.getElementById('updatedTaskID').value
	var remarks = document.getElementById('remarks_update').value
	var desc = document.getElementById('taskDescription_update').value
	var plannedDays = document.getElementById('plannedDuration_update').value
	var stakeHolderId = document.getElementById('stakeHolderId_update').value
	var taskName = document.getElementById('task_update').value
	
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var result;
	
		//For updating the selected Task .
    	result = jsonrpc.processes.updateTask(taskId,remarks,desc,stakeHolderId,plannedDays,taskName);
    
		if( result !=null)
		{
			if(!result)
			{
			alert("This Task cannot be updated");
			return false;
			}
			else
			{
			 getTaskListForACategory('masterProjectPlanBean_id',document.getElementById('npdCategoryId_update').value);
			var newRow = document.all("stageAndTaskList").insertRow();
				oCell = newRow.insertCell();
				oCell.innerHTML='<td><font color="red">Task is Updated successfully</font></td>'
				
			}
		}//if result
		
		
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}

//to check whether a value is an integer.
 function isInteger(val)
{
  
    if(val==null)
    {
        return false;
    }
    if (val.length==0)
    {
        return false;
    }
    for (var i = 0; i < val.length; i++) 
    {
        var ch = val.charAt(i)
        if (i == 0 && !ch == "-")
        {
            continue
        }
        if (ch < "0" || ch > "9")
        {
            return false
        }
    }
    return true
}


</script>

</head>
<body>
<div id="menu_abc" style="display: block;">
<table width="98%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
		<!--<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Master
				Project Plan Creation</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>-->
		</td>
	</tr>
</table>
<html:form action="/addMasterPlan" method="post">

	<table width="60%" border="2" class="tab2" cellpadding="0"
		cellspacing="0" align="left">
		<tr>

			<td><span id="firstButton" class="buttonLarge"
				onClick="showDetails('addWorkflow')">Add Workflow</span> <span
				id="secondButton" class="buttonLarge"
				onClick="showDetails('addStage')">Add Stage</span> <span
				id="thirdButton" class="buttonLarge"
				onClick="showDetails('addTask')">Add Task</span> <span
				id="fourthButton" class="buttonLarge"
				onClick="showDetails('updateTask')">Update/Remove Task</span></td>
		<tr>

			<td width="70%">
			<table width="100%" border="0" class="tab2" cellpadding="0"
				cellspacing="0" align="center">
				<tbody id='addWorkflow' style="display:none">
					<tr>
						<th width="30%" scope="row">
						<div align="left">ChangeOrder Category <font color="#990033">*</font></div>
						</th>
						<td width="70%"><html:select property="npdCategoryId" styleClass="inputBorder1" 
							style="width:140px">
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
							maxlength="100" styleClass="inputBorder1"  style="width:140px" /></td>

					</tr>

					<tr align="center">
						<th colspan="2" scope="row">
						 <span
								id="saveButton" property="method" class="buttonLarge"
								onClick="return saveWorkflow();">Save</span>
						<!--<table width="50%" border="0">
							<tr>
								<td><html:submit property="method"
									onclick="return saveWorkflow();">
									<bean:message key="submit.saveWorkflow"
										bundle="ButtonResources" />
								</html:submit></td>

							</tr>
						</table>-->
						</th>
					</tr>
				</tbody>
				<tbody id='addStage' style="display:none">
					<tr>
						<th width="30%" scope="row">
						<div align="left">ChangeOrder Category <font color="#990033">*</font></div>
						</th>
						<td width="70%"><html:select property="npdCategoryId_stage" styleClass="inputBorder1" 
							style="width:140px">
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
						<div align="left">Stage <font color="#990033">*</font></div>
						</th>
						<td width="70%"><html:text property="stage" maxlength="100"
							style="width:140px" styleClass="inputBorder1"/></td>

					</tr>

					<tr align="center">
						<th colspan="2" scope="row">
						<span
								id="saveButton" property="method" class="buttonLarge"
								onClick="return saveStage();">Save Stage</span>
						<!--<table width="50%" border="0">
							<tr>
								<td><html:submit property="method"
									onclick="return saveStage();">
									<bean:message key="submit.saveStage" bundle="ButtonResources" />
								</html:submit></td>

							</tr>
						</table>-->
						</th>
					</tr>
				</tbody>
				<tbody id='addTask' style="display:none">
					<logic:notEmpty name="masterProjectPlanBean" property="stageList">
						<tr>
							<th width="30%" scope="row">
							<div align="left">Stage <font color="#990033">*</font></div>
							</th>
							<td width="70%"><html:select property="stageId"
								style="width:140px"
								onchange="return getTaskListForACategory('taskId',this.value);">
								<html:option value="-1">Select</html:option>
								<logic:iterate id="masterProjectPlanBean_id"
									name="masterProjectPlanBean" property="stageList"
									indexId="index1">
									<%String classType="";
										if(index1.intValue()%2 == 0){classType="normal";}else{classType="lightBg";} %>
									<html:option value="${masterProjectPlanBean_id.stageid}" styleClass="<%=classType%>">
										<c:out
											value="${masterProjectPlanBean_id.stagename}--${masterProjectPlanBean_id.workflow.npdCategory.npdcatdesc}" />
									</html:option>
								</logic:iterate>

							</html:select></td>
						</tr>

						<tr>
							<th width="30%" scope="row">
							<div align="left">Task <font color="#990033">*</font></div>
							</th>
							<td width="70%"><html:text property="task" maxlength="100"
								style="width:140px" styleClass="inputBorder1"/></td>
						</tr>

						<tr>
							<th width="30%" scope="row">
							<div align="left">Task Description<font color="#990033">*</font></div>
							</th>
							<td width="70%"><html:textarea property="taskDescription"
								cols="17" rows="7" styleClass="inputBorder1"/></td>
						</tr>

						<tr>
							<th width="30%" scope="row">
							<div align="left">Stake Holder <font color="#990033">*</font></div>
							</th>
							<td width="70%"><html:select property="stakeHolderId"
								style="width:140px" styleClass="inputBorder1">
								<html:option value="-1">Select</html:option>
								<logic:notEmpty name="masterProjectPlanBean"
									property="stakeHolderList">
									<html:optionsCollection property="stakeHolderList"
										label="empname" value="npdempid" />
								</logic:notEmpty>
							</html:select></td>
						</tr>
						<tr>
							<th width="30%" scope="row">
							<div align="left">First Task<font color="#990033"></font></div>
							</th>
							<td width="70%"><html:checkbox property="first" class="none"></html:checkbox></td>

						</tr>
						<tr>
							<th width="30%" scope="row">
							<div align="left">Last Task<font color="#990033"></font></div>
							</th>
							<td width="70%"><html:checkbox property="last" class="none"></html:checkbox></td>

						</tr>

						<tr>
							<th width="30%" scope="row">
							<div align="left">Previous Task <font color="#990033"></font></div>
							</th>
							<td width="70%"><html:select property="taskId"
								style="width:140px">
								<html:option value="-1">Select</html:option>
								<logic:notEmpty name="masterProjectPlanBean" property="taskList">
									<html:optionsCollection property="taskList" label="taskname"
										value="taskid" />
								</logic:notEmpty>
							</html:select></td>
						</tr>
						<tr>
							<th width="30%" scope="row">
							<div align="left">Planned Duration(in Days) <font
								color="#990033">*</font></div>
							</th>
							<td width="70%"><html:text property="plannedDuration"
								maxlength="5" style="width:140px" styleClass="inputBorder1" /></td>
						</tr>

						<tr>
							<th width="30%" scope="row">
							<div align="left">Document Upload <font color="#990033">*</font></div>
							</th>
							<td width="70%"><html:checkbox property="attachment"
								onclick="getTemplateList('templateId');"></html:checkbox></td>

						</tr>

						<tr>
							<th width="30%" scope="row">
							<div align="left">Template <font color="#990033">*</font></div>
							</th>
							<td width="70%"><html:select property="templateId"
								style="width:140px" styleClass="inputBorder1">
								<html:option value="-1">Select</html:option>
								<logic:notEmpty name="masterProjectPlanBean"
									property="templateList">
									<html:optionsCollection property="templateList"
										label="taskname" value="taskid" />
								</logic:notEmpty>
							</html:select></td>

						</tr>
						<tr>
							<th width="30%" scope="row">
							<div align="left">Remarks<font color="#990033">*</font></div>
							</th>
							<td width="70%"><html:textarea property="remarks" cols="40"
								rows="10" styleClass="inputBorder1"/></td>
						</tr>


						<tr align="center">
							<th colspan="2" scope="row" width="50%">
							 <span
								id="saveButton" property="method" class="buttonLarge"
								onClick="return saveTask();">Save Task</span>
							<!--<table width="50%" border="0">
								<tr>
									<td><html:submit property="method"
										onclick="return saveTask();">
										<bean:message key="submit.saveTask" bundle="ButtonResources" />
									</html:submit></td>

								</tr>
							</table>-->
							</th>
						</tr>
					</logic:notEmpty>
				</tbody>
				<tbody id='updateTask' style="display:none">

					<tr>
						<th width="20%" scope="row">
						<div align="left">ChangeOrder Category <font color="#990033">*</font></div>
						</th>
						<td width="30%"><html:select property="npdCategoryId_update"
							style="width:140px" styleClass="inputBorder1"
							onchange="return getTaskListForACategory('masterProjectPlanBean_id',this.value)">

							<html:option value="-1">Select</html:option>
							<logic:notEmpty name="masterProjectPlanBean"
								property="npdCategoryList">
								<html:optionsCollection property="npdCategoryList"
									label="npdcatdesc" value="npdcatid" />
							</logic:notEmpty>
						</html:select></td>
						<td width="20%"></td>
						<td width="30%"></td>

					</tr>
					<tr>
						<td width="30%"></td>
						<td colspan="3">
						<table border="1" width="100%">
							<tbody id='stageAndTaskList'>

							</tbody>
						</table>
						</td>
					</tr>
				</tbody>
				<tbody>
					<logic:messagesPresent message="true">
						<table width="50%" align="center" id='messageBody'>
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
				</tbody>
			</table>
		</tr>

	</table>

	<table width="40%" border="2" class="tab2" cellpadding="0"
		cellspacing="0" align="right">
		<tbody id='showTask' style="display:none">
			<tr>

				<td width="70%">

				<table width="100%" border="0" class="tab2" cellpadding="0"
					cellspacing="0" align="center">
					<tr>
						<td width="20%"></td>
						<th width="20%" scope="row">
						<div align="left">Task <font color="#990033">*</font></div>
						</th>
						<td width="30%"><html:text property="task_update"
							maxlength="100" styleClass="inputBorder1" style="width:140px" /></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<th width="20%" scope="row">
						<div align="left">Stake Holder <font color="#990033">*</font></div>
						</th>
						<td width="30%"><html:select property="stakeHolderId_update"
							style="width:140px" styleClass="inputBorder1">
							<html:option value="-1">Select</html:option>
							<logic:notEmpty name="masterProjectPlanBean"
								property="stakeHolderList">
								<html:optionsCollection property="stakeHolderList"
									label="empname" value="npdempid" />
							</logic:notEmpty>
						</html:select></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<th width="20%" scope="row">
						<div align="left">Task Description<font color="#990033">*</font></div>
						</th>
						<td width="30%"><html:textarea
							property="taskDescription_update" styleClass="inputBorder1" cols="17" rows="7" /></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<th width="20%" scope="row">
						<div align="left">Planned Duration(in Days) <font
							color="#990033">*</font></div>
						</th>
						<td width="30%"><html:text property="plannedDuration_update"
							maxlength="5" style="width:140px" styleClass="inputBorder1"/></td>
					</tr>
					<tr>
						<td width="20%"></td>
						<th width="20%" scope="row">
						<div align="left">Remarks<font color="#990033">*</font></div>
						</th>
						<td width="30%"><html:textarea property="remarks_update"
							cols="40" rows="10" styleClass="inputBorder1"/></td>
					</tr>
					<html:hidden property="updatedTaskID" />
					<html:hidden property="taskListLength" />
					<html:hidden property="taskNo" />


					<tr>
						<td width="20%"></td>
						<td colspan="2"><span id="delete" class="buttonLarge"
							onClick="deleteSelectedTask();">Delete</span><span id="update"
							class="buttonLarge" onClick="updateSelectedTask();">Update</span></td>

					</tr>
				</table>


				</td>
			</tr>
		</tbody>
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
