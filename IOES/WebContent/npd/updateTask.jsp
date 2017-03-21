<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%><head>
<title>ChangeOrder Workflow</title>
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">

<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>
<script type="text/javascript" src="npd/js/inputColor.js"></script>

<script type="text/javascript">

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function getTaskListForACategory(objId,var1)
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
	var counter=0;
		
	   jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	  
    	var result;
      
		if(objId=='taskId')
		{	
		 	var obj = document.getElementById(objId);
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
				oCell.innerHTML='<td><font color="red">No Task for ChangeOrder the selected Category</font></td>';
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
			document.getElementById('task_update').value = result.taskname;
			document.getElementById('taskDescription_update').value = result.taskdesc;
			document.getElementById('plannedDuration_update').value = result.planneddurationdays;
			document.getElementById('stakeHolderId_update').value = result.stakeholder.roleid;
			document.getElementById('remarks_update').value = result.taskinstructionremarks;
			document.getElementById('updatedTaskID').value=taskid;
			document.getElementById('stageId').value=result.stage.stageid;
			if(result.isfirsttask==0)
			{
			document.getElementById("first").checked=false;
			}
			else
			{
			document.getElementById("first").checked=true;
				for(x=0;x<document.getElementById("previousTaskId").length;x++)
				{
			
				document.getElementById("previousTaskId").options[x].selected=false;
				}
			}
			if(result.islasttask==0)
			{
			document.getElementById("last").checked=false;
			}
			else
			{
			document.getElementById("last").checked=true;
			}
			var result1;
	
		//For getting previous task of a task
    	result1 = jsonrpc.processes.getPreviousTasks(taskid);
    		for(x=0;x<document.getElementById("previousTaskId").length;x++)
		{
			
			document.getElementById("previousTaskId").options[x].selected=false;
		}
	  for(x=0;x<document.getElementById("previousTaskId").length;x++)
		{
			for(y=0;y<result1.length;y++)
			{
				if(document.getElementById("previousTaskId").options[x].value==result1[y])
				{
			document.getElementById("previousTaskId").options[x].selected=true;
				}
			}
				
		}
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

// to delete the selected task from database.

function deleteSelectedTask()
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
	var taskListLength = document.getElementById('taskListLength').value;
	var taskNo=document.getElementById('taskNo').value;
	if(taskNo==0||document.getElementById('last').checked)
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
			getTaskListForACategory('masterProjectPlanBean_id',document.getElementById('workflowId').value);
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
	
		if(!document.getElementById("first").checked)
	{	counter=0;
		for(x=0;x<document.getElementById("previousTaskId").length;x++)
		{
				if(document.getElementById("previousTaskId").options[x].selected)
				{
				counter++;
				}
		}
		if(counter<1)
		{
		errorString+="Please select a Previous Task"+"\n";
		}
	}
	else
	{
			for(x=0;x<document.getElementById("previousTaskId").length;x++)
		{
			
			document.getElementById("previousTaskId").options[x].selected=false;
		}
	}
	
	if(errorString!="")
		{
	alert(errorString);
	return false;
	}
		
	if(!checkForDuplicateFirstAndLastTask())
	{
	return false;
	}
		
	
	var taskId = document.getElementById('updatedTaskID').value
	var remarks = document.getElementById('remarks_update').value
	var desc = document.getElementById('taskDescription_update').value
	var plannedDays = document.getElementById('plannedDuration_update').value
	var stakeHolderId = document.getElementById('stakeHolderId_update').value
	var taskName = document.getElementById('task_update').value
	var isfirst = document.getElementById('first').checked;
	var islast =document.getElementById('last').checked;
	var prevTaskId = new Array(document.getElementById('previousTaskId').length)
	for(x=0;x<document.getElementById("previousTaskId").length;x++)
		{
				if(document.getElementById("previousTaskId").options[x].selected)
				{
				prevTaskId[x]=document.getElementById("previousTaskId").options[x].value;
				}
		}
	
	
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var result;
	//For updating the selected Task .
    	result = jsonrpc.processes.updateTask(taskId,remarks,desc,stakeHolderId,plannedDays,taskName,isfirst,islast,prevTaskId);
    
		if( result !=null)
		{
			if(!result)
			{
			alert("This Task cannot be updated");
			return false;
			}
			else
			{
			 getTaskListForACategory('masterProjectPlanBean_id',document.getElementById('workflowId').value);
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
			if(result.taskid>0)
			{	
				if(document.getElementById("first").checked&&document.getElementById("updatedTaskID").value!=result.taskid)
				{
				alert("First stage already exists for this Workflow");
				return false;
				}
				if(document.getElementById("last").checked&&document.getElementById("updatedTaskID").value!=result.taskid)
				{
					alert("Last stage already exists for this Workflow");
					return false;
				}
				return true;	
			}
			else
			{
			return true;
			}
			
		}
		//if result
		
		
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}


function showDetails(var1) {

	if(var1=='addTask')
	{
	document.masterProjectPlanBean.action="<c:out value='${sessionScope.MenuContextPath}' />/manageMasterPlan.do?method=viewMasterPlan&addTask=addTask";
	document.masterProjectPlanBean.submit();
	}
	if(var1=='updateTask')
	{
	document.masterProjectPlanBean.action="<c:out value='${sessionScope.MenuContextPath}' />/manageMasterPlan.do?method=viewMasterPlan&updateTask=updateTask";
	document.masterProjectPlanBean.submit();
	}
	if(var1=='finalize')
	{
	document.masterProjectPlanBean.action="<c:out value='${sessionScope.MenuContextPath}' />/manageMasterPlan.do?method=viewMasterPlan&finalize=finalize";
	document.masterProjectPlanBean.submit();
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
<body
	onload="getTaskListForACategory('masterProjectPlanBean','<bean:write name="masterProjectPlanBean" property="workflowId"/>
<html:html>

');">
<div class="head"> <span>Update Previous Task</span> </div>
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
<html:form action="/manageMasterPlan" method="post">
	<table width="100%" border="2" class="tabledata" cellpadding="0" cellspacing="0" align="left">
		<tr align="center">
			<th colspan="4" scope="row">
				<table width="100%" border="0">
					<tr>
						<td><span id="thirdButton" class="buttonSmall"
							onClick="showDetails('addTask')">Add Task</span> <span
							id="fourthButton" class="buttonLagre"
							onClick="showDetails('updateTask')">Update/Remove Task</span><span
							id="thirdButton" class="buttonLagre"
							onClick="showDetails('finalize')">Finalize Workflow</span></td>

				</tr>
			</table>
			</th>
		</tr>

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

				<html:hidden property="workflowId" />
				<html:hidden property="npdCategoryId" />
				<tr>
					<td width="30%"></td>
					<td colspan="3">
					<table border="1" width="100%">
						<tbody id='stageAndTaskList'>

						</tbody>
					</table>
					</td>
				</tr>

			</table>
			</td>
			<td width="70%">
			<table width="100%" border="0" class="tabledata" cellpadding="0"
				cellspacing="0" align="center">
				<tr>
					<td width="20%"></td>
					<th width="20%" scope="row">
					<div align="left">Task <font color="#990033">*</font></div>
					</th>
					<td width="30%"><html:text property="task_update"
						maxlength="100" style="width:140px" /></td>
				</tr>
				<tr>
					<td width="20%"></td>
					<th width="20%" scope="row">
					<div align="left">Stake Holder <font color="#990033">*</font></div>
					</th>
					<td width="30%"><html:select property="stakeHolderId_update"
						style="width:140px">
						<html:option value="-1">Select</html:option>
						<logic:notEmpty name="masterProjectPlanBean"
							property="stakeHolderList">
							<html:optionsCollection property="stakeHolderList"
								label="rolename" value="roleid" />
						</logic:notEmpty>
					</html:select></td>
				</tr>
				<tr>
					<td width="20%"></td>
					<th width="20%" scope="row">
					<div align="left">Task Description<font color="#990033">*</font></div>
					</th>
					<td width="30%"><html:textarea
						property="taskDescription_update" cols="17" rows="7" /></td>
				</tr>
				<tr>
					<td width="20%"></td>
					<th width="20%" scope="row">
					<div align="left">Planned Duration(in Days) <font
						color="#990033">*</font></div>
					</th>
					<td width="30%"><html:text property="plannedDuration_update"
						maxlength="3" style="width:140px" /></td>
				</tr>
				<tr>
					<td width="20%"></td>
					<th width="20%" scope="row">
					<div align="left">Remarks<font color="#990033">*</font></div>
					</th>
					<td width="30%"><html:textarea property="remarks_update"
						cols="40" rows="10" /></td>
				</tr>
				<tr>
					<td width="20%"></td>
					<th width="20%" scope="row">
					<div align="left">First Task<font color="#990033"></font></div>
					</th>
					<td width="30%"><html:checkbox property="first"></html:checkbox></td>

				</tr>
				<tr>
					<td width="20%"></td>
					<th width="20%" scope="row">
					<div align="left">Last Task<font color="#990033"></font></div>
					</th>
					<td width="30%"><html:checkbox property="last"></html:checkbox></td>

				</tr>

				<tr>
					<td width="20%"></td>
					<th width="20%" scope="row">
					<div align="left">Previous Task <font color="#990033"></font></div>
					</th>
					<td width="30%"><html:select property="previousTaskId"
						multiple="" style="width:140px">
						<logic:notEmpty name="masterProjectPlanBean" property="taskList">
							<html:optionsCollection property="taskList" label="taskname"
								value="taskid" />
						</logic:notEmpty>
					</html:select></td>
				</tr>
				<html:hidden property="updatedTaskID" />
				<html:hidden property="taskListLength" />
				<html:hidden property="taskNo" />
				<html:hidden property="stageId" />


				<tr>
					<td width="20%"></td>
					<td colspan="2"><span id="delete" class="buttonVsmall"
						onClick="deleteSelectedTask();">Delete</span><span id="update"
						class="buttonVsmall" onClick="updateSelectedTask();">Update</span></td>

				</tr>
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

</BODY>
</html:html>
