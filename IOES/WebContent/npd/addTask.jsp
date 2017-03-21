<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Anil Kumar		3-Oct-11	CSR00-05422     Use Ctrl + S for Save  -->
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
<script type="text/javascript" src="npd/js/utility.js"></script>

<script type="text/javascript">
var isUpdateFlag=false;
function goToHome()
{
	var myForm=document.getElementById('taskForm');
	//myForm.toExcel.value='false';
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
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
			if(result.taskid!=document.getElementById('taskId').value)
			{
			alert("Task with same Name for the selected stage already exists");
			return false;
			}
			else
			{
			return true;
			}
			
		}//if result
		else
		{
			return true;
		}
		
		
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
		//For checking whether duplicate task exists at First and Last position -
//    	result = jsonrpc.processes.checkForDuplicateFirstAndLastTask(document.getElementById("first").checked,document.getElementById("last").checked,document.getElementById("stageId").value);
    	result = jsonrpc.processes.fetchFirstAndLastTask(document.forms[0].workflowId.value);
		if( result !=null)
		{
			if(document.getElementById('first').checked==true)
			{
				if(result.list[0]!=null)
				{
					var bean=result.list[0];
					if(bean.taskid!=document.getElementById("taskid").value)
					{
						alert("First Task already exists for this Workflow.To make this task the first task , first remove that task from first task by editing that task and then make current task as first task.");
						return false;
					}
				}
			}
			
			if(document.getElementById('last').checked==true)
			{
				if(result.list[1]!=null)
				{
					var bean=result.list[1];
					if(bean.taskid!=document.getElementById("taskid").value)
					{
						alert("Last Task already exists for this Workflow.To make this task the last task , first remove that task from last task by editing that task and then make current task as last task.");
						return false;
					}
				}
			}
			
			return true;
		
		}//if result
		return true;
		
		
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
		//if(document.getElementById("attachment").checked)
		//{
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
		//}
		/*else
		{
		for(i=obj.options.length-1;i>=0 ;i--)
			{
			obj.options[i] = null;
			}
			obj.options[0] = new Option("Select", "-1");
		}*/
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}





function saveTask()
{
	myform11=document.getElementById("taskForm");
	setFormBean(myform11);
	var errorString="";
	if(document.getElementById("stageId").value==-1 || document.getElementById("stageId").value=='' || document.getElementById("stageId").value==null)
	{
	errorString+="-Please Select Stage"+"\n";
	}
	if(document.getElementById("task").value==null||trim(document.getElementById("task").value)=="")
	{
	errorString+="-Please enter Task"+"\n";
	}
	if(ValidateTextField(document.getElementById("task"),'<%=request.getContextPath()%>',"Task Name")==false)
	{
		return false;
	}

	if(document.getElementById("taskDescription").value==null||trim(document.getElementById("taskDescription").value)=="")
	{
	errorString+="-Please enter Task Description"+"\n";
	}
	if(ValidateTextField(document.getElementById("taskDescription"),'<%=request.getContextPath()%>',"Task Description")==false)
	{
		return false;
	}
	else
	{
	if(chkTaskDescriptionLength(document.getElementById("taskDescription").value)==false)
		{
		errorString+="-Task Description cannot contain more then 500 characters"+"\n";
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
		errorString+="-Please select a Previous Task"+"\n";
		}
	}
	else
	{
			for(x=0;x<document.getElementById("previousTaskId").length;x++)
		{
			
			document.getElementById("previousTaskId").options[x].selected=false;
		}
	}
	
	if(document.getElementById("stakeHolderId").value==-1)
	{
	errorString+="-Please select a StakeHolder"+"\n";
	}

	if(document.getElementById("remarks").value==null||trim(document.getElementById("remarks").value)=="")
	{
	errorString+="-Please enter Remarks"+"\n";
	}
	if(ValidateTextField(document.getElementById("remarks"),'<%=request.getContextPath()%>',"Remarks")==false)
	{
		return false;
	}
	else
	{
		if(chkRemarksLength(document.getElementById("remarks").value)==false)
		{
		errorString+="-Remarks cannot contain more then 1000 characters"+"\n";
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
			var aval=checkForDuplicateFirstAndLastTask();
			if(aval==false)
			{
				return false;
			}
			else
			{
				showLayer();
				document.masterProjectPlanBean.action="/IOES/addMasterPlan.do";
				return true;
			}
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
//		alert ("Remarks cannot contain more then 1000 characters");
		return false;
	}
	return true;
}

function chkTaskDescriptionLength(description)
{
	if (description != '' && 
	trim(description) != '' && 
	trim(description).length >500)
	{
//		alert ("Task Description cannot contain more then 500 characters");
		return false;
	}
	return true;
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
		errorString+="-Please enter Task"+"\n";
		}
		if(document.getElementById("taskDescription_update").value==null||trim(document.getElementById("taskDescription_update").value)=="")
		{
		errorString+="-Please enter Task Description"+"\n";
		}
		else
			{
			if(chkTaskDescriptionLength(document.getElementById("taskDescription_update").value)==false)
			{
			errorString+="-Task Description cannot contain more then 500 characters"+"\n";
			}
			}
		if(document.getElementById("stakeHolderId_update").value==-1)
		{
		errorString+="-Please select a StakeHolder"+"\n";
		}
		//alert(isInteger(document.getElementById("plannedDuration").value));
		if(document.getElementById("remarks_update").value==null||trim(document.getElementById("remarks_update").value)=="")
		{
		errorString+="-Please enter Remarks"+"\n";
		}
		else
		{
			if(chkRemarksLength(document.getElementById("remarks_update").value)==false)
			{
			errorString+="-Remarks cannot contain more then 1000 characters"+"\n";
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
function viewTask2(taskId)
{
	
	fillAllPreviousTasks(taskId);
	viewTask(taskId);
	document.getElementById('taskId').value="";
}
//,taskname,taskdesc,stakeholder,stageId,isfirsttask,islasttask,prevtaskid,planneddurationdays,isattachment,refdocid,taskinstructionremarks,taskType
function viewTask(taskId)
{
	try
	{
	var appContextPath = '<%=request.getContextPath()%>';
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	//fetch task details
	var result_task;
   	result_task = jsonrpc.processes.getTaskDetails(taskId);
	var bean=result_task;
	
	var taskname=result_task.taskname;
	var taskdesc=result_task.taskdesc;
	var stakeholder=result_task.stakeholder.roleid;
	var stageId=result_task.stage.stageid;
	var isfirsttask=result_task.isfirsttask;
	var islasttask=result_task.islasttask;
	var taskremarks=result_task.taskinstructionremarks;
	var prevtaskid="";
	
	
	document.getElementById("task").value=taskname;
	document.getElementById("taskDescription").value=taskdesc;
	document.getElementById("stakeHolderId").value=stakeholder;
	document.getElementById("stageId").value=stageId;
	document.getElementById("remarks").value=taskremarks;
		if(isfirsttask==0)
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
		if(islasttask==0)
		{
			document.getElementById("last").checked=false;
		}
		else
		{
			document.getElementById("last").checked=true;
		}
		
		
		if(taskType==0)
		{
			document.getElementById("taskTasktype").checked=false;
		}
		else
		{
			document.getElementById("taskTasktype").checked=true;
		}
		
		if(isrejectionallowed==0)
		{
			document.getElementById("rejectionAllowed").checked=false;
		}
		else
		{
			document.getElementById("rejectionAllowed").checked=true;
		}
	
	var result;
	
		//For getting previous task of a task
    	result = jsonrpc.processes.getPreviousTasks(taskId);
    	
   	for(x=0;x<document.getElementById("previousTaskId").length;x++)
		{
			
			document.getElementById("previousTaskId").options[x].selected=false;
		}
       if(result!=null)
       {
	   for(x=0;x<document.getElementById("previousTaskId").length;x++)
		{
			if(result.length!=null)
			{
				for(y=0;y<result.length;y++)
				{
					if(document.getElementById("previousTaskId").options[x].value==result[y])
					{
				document.getElementById("previousTaskId").options[x].selected=true;
					}
				}
			}
		}
	  }
	//toggleTemplate();
	//document.getElementById("remarks").value=taskinstructionremarks;
	
	document.getElementById('id_saveButton').style.display="block";
	document.getElementById('id_updateButton').style.display="none";
	}catch(e)
	{
		return false;
	}	
}

function DisableAndGenerateHidden(elem)
{
//alert(elem.name);
//alert(elem.value);
	var input = document.createElement("input");
	input.setAttribute("type", "hidden");
	input.setAttribute("name", elem.name);
	input.setAttribute("value", elem.value);
	par=elem.parentNode;
	par.appendChild(input);
	elem.disabled=true;
}
//ROHIT VERM ANEW CR CHANGES START
function deleteTask(taskId,isfirsttask,islasttask)
{
	var appContextPath = '<%=request.getContextPath()%>';
	//document.getElementById("taskid").value=taskId;
	//document.masterProjectPlanBean.action="<c:out value='${sessionScope.MenuContextPath}' /><%="/"+(String)request.getAttribute(AppConstants.INITIAL_SERVLET_REQUESTED)%>?method=deleteTask";
	//showLayer();
	//document.masterProjectPlanBean.submit();
	//var myForm=document.getElementById('searchForm');
	//myForm.method.value='removeSelectedTask';
	document.masterProjectPlanBean.action="<c:out value='${sessionScope.MenuContextPath}' />/addMasterPlan.do?method=deleteTask";
	showLayer(); 
	document.masterProjectPlanBean.submit();

}
//ROHIT VERMA NEW CR CHANGES END


function modifyTask(taskId)
{
	document.getElementById("taskid").value=taskId;

	fillAllPreviousTasks(taskId);
	viewTask(taskId);
	document.getElementById('id_saveButton').style.display="none";
	document.getElementById('id_updateButton').style.display="block";	
	isUpdateFlag=true;
}
function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function showDetails(var1) {

	if(var1=='addStage')
	{
	document.masterProjectPlanBean.action="<c:out value='${sessionScope.MenuContextPath}' /><%="/"+(String)request.getAttribute(AppConstants.INITIAL_SERVLET_REQUESTED)%>?method=viewMasterPlan&addStage=addStage";
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
	
	
	
	if(mymsg!="")
	{
		mymsg="Cannot Finalize Workflow : "+"\n"+mymsg;
		alert(mymsg);
		return false;
	}

	document.masterProjectPlanBean.action="/IOES/addMasterPlan.do?method=viewMasterPlan&finalize=finalize";
	showLayer();
	document.masterProjectPlanBean.submit();
	}
	

}
function EnableSelect(elem)
{
	m=document.getElementsByName(elem.name);
	if(m!=null)
	{	
		if(m.length==null)
		{	
			document.getElementById('templateId').disabled=false;
		}
		else
		{	
			for(i=0;i<m.length;i++)
			{	
				if(m[i].type!='select-one')
				{
					m[i].disabled=true;
				}
				else
				{
					m[i].disabled=false;
				}
			}
		}
	}
}
function toggleTemplate()
{
}
function emptyStageForm()
{
	document.getElementById("taskId").value="";
	document.getElementById("task").value="";
	document.getElementById("taskDescription").value="";
	document.getElementById("stakeHolderId").value="-1";
	//document.getElementById("stageId").value="-1";
	document.getElementById("first").checked=false;
	document.getElementById("last").checked=false;

   	for(x=0;x<document.getElementById("previousTaskId").length;x++)
	{
		document.getElementById("previousTaskId").options[x].selected=false;
	}
	
	//toggleTemplate();
	
	document.getElementById("remarks").value="";
}
function fillAllPreviousTasks(val_task)
{

	var appContextPath = '<%=request.getContextPath()%>';
	
	try
	{	
			var obj = document.getElementById("id_previousTaskId");
		//if(document.getElementById("attachment").checked)
		//{
			jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	   		var result;
    		
    		for(i=obj.options.length-1;i>=0 ;i--)
			{
				obj.options[i] = null;
			}
    		//For populating Template List
    		if(val_task=="")
    		{
    			result = jsonrpc.processes.getPreviousTaskList(document.forms[0].workflowId.value,"ALL","");  
    			/*Add by Anil Kumar to display all previous task while task creating*/
    			if( result !=null && result.list.length > 0)
				{
					var count=0;
					for(i=0;i < result.list.length;i++)
					{
						var name = result.list[i].taskname;
						var id=result.list[i].taskid;				
						obj.options[i] = new Option(name,id);
						count=i;
					}
				}
    		}
    		
    		if(val_task!="")
    		{
    			var count=0;
    			result = jsonrpc.processes.getPreviousTaskList(document.forms[0].workflowId.value,"VALID_FOR_TASK",val_task);
    			prevTaskId=jsonrpc.processes.getPreviousTask(val_task);
    			/*Add by Anil Kumar for display previous task in selected, against perticular task*/
    			if( result !=null && result.list.length > 0)
				{					
					for(i=0;i < result.list.length;i++)
					{
						var name = result.list[i].taskname;
						var id=result.list[i].taskid;								
						obj.options[i] = new Option(name,id);	
						obj.options[i].value=id;
						
					}//for												
					for(j=0;j<result.list.length;j++)
					{
						if(obj.options[j].value==prevTaskId)
						{							
							count=j;break;
						}
					}								
					obj.options[count].selected =true;																			
				}//if result  
    		}
    		    													
		//}
		/*else
		{
		for(i=obj.options.length-1;i>=0 ;i--)
			{
			obj.options[i] = null;
			}
			obj.options[0] = new Option("Select", "-1");
		}*/
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
}
function goToSave()
{
	emptyStageForm();
	myform=document.getElementById('taskForm');
	document.getElementById('id_saveButton').style.display="block";
	document.getElementById('id_updateButton').style.display="none";	
	fillAllPreviousTasks("");
}


//ROHIT VERMA NEW CR
function getcheckedTaskId(chk,id)
{
	if(chk)
	{
		document.masterProjectPlanBean.deleteTaskId.value = document.masterProjectPlanBean.deleteTaskId.value+'&'+id;
	}
	else
	{
		document.masterProjectPlanBean.noDeleteTaskId.value = document.masterProjectPlanBean.noDeleteTaskId.value+'&'+id;		
	}	
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


function onBodyLoad()
{
	getTemplateList('templateId');
	emptyStageForm();
	myForm=document.getElementById('taskForm');
	myForm.action.value="<%="/"+(String)request.getAttribute(AppConstants.INITIAL_SERVLET_REQUESTED)%>";
}
//start[001]
document.onkeydown=checkKeyPress;
function checkKeyPress()
{
	if (event.ctrlKey && event.keyCode == 83) {						
				event.keyCode=4; 
				saveTask();				
				if(isUpdateFlag==false)
				{
					document.getElementById("saveButton").click();
				}
				else
				{
					isUpdateFlag=false;
					document.getElementById("updateButton").click();
				}
   }   
}
//end[001]
</script>

</head>
<body onload="onBodyLoad();">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
		
</div>
<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">
				<td><img src="gifs/top/home.gif" title="Home" onClick="goToHome('Home');"></img></td>
			</tr>
</table>
<div class="head"> <span>Add Task</span> </div>
<table width="98%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
		</td>		
	</tr>
</table>
<html:form action="/addMasterPlan" method="post" styleId="taskForm">
<input type="hidden" name="opType"/>
<html:hidden property="taskListLength"/>
<table width="100%" class="tab2" align="center">
	<tr>
		<td>
			<div class="error" align="center">
				<logic:present name="validation_errors">
					<logic:iterate id="error_row" name="validation_errors" scope="request">
							<font color="red"><bean:write name="error_row" /></font><BR>
					</logic:iterate>
				</logic:present>
			</div>
		</td>
	</tr>
	<html:messages id="message" message="true" >
		<tr>
			<td align="center" style="color: red;">
				<li><bean:write name="message"/></li>
			</td>
		</tr>
	</html:messages>
</table>

	<table width="100%" border="2" class="tab2" cellpadding="0"
		cellspacing="0" align="left">
		<tr align="center">
			<th colspan="4" scope="row">
			<table width="100%" border="0">
				<tr>
					<td> 
						<span style="display: none;"
						id="fourthButton" class="buttonLarge"
						onClick="showDetails('updateTask')">Update/Remove Task</span><span
						id="thirdButton" class="buttonLarge"
						onClick="showDetails('finalize')">Finalize Workflow</span></td>

				</tr>
			</table>
			</th>
		</tr>

		<tr>
			<td width="70%">
				<table width="100%" border="0" class="tab2" cellpadding="0" cellspacing="0" align="center">
					<tr>
						<th width="30%" scope="row">
						<div align="left">ChangeOrder Category  <font color="#990033">*</font></div>
						</th>												
						<td width="70%" align="left"><html:select property="npdCategoryId"
							style="width:400px" disabled="true" styleClass="inputBorder1">
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
							maxlength="100" style="width:140px" readonly="true" /></td>
	
					</tr>-->
					<html:hidden property="workflowId" />
					<html:hidden property="npdCategoryId" />
					<html:hidden property="taskId" />				
							<logic:notEmpty name="masterProjectPlanBean" property="stageList">
								<logic:iterate id="masterProjectPlanBean_id"
									name="masterProjectPlanBean" property="stageList"
									indexId="index1">
									<html:text property="stageId" name="masterProjectPlanBean" styleClass="inputBorder1" style="display:none;" value="${masterProjectPlanBean_id.stageid}"></html:text>
								</logic:iterate>
							</logic:notEmpty>
	
					<tr>
						<th width="30%" scope="row">
						<div align="left">Task <font color="#990033">*</font></div>
						</th>
						<td width="70%"><html:text property="task" maxlength="500"
							style="width:400px" styleClass="inputBorder1"/></td>
					</tr>
	
					<tr>
						<th width="30%" scope="row">
						<div align="left">Task Description<font color="#990033">*</font></div>
						</th>
						<td width="70%">
						<html:textarea property="taskDescription" cols="100" rows="7" styleClass="inputBorder1" /></td>
					</tr>
								
	
					<tr>
						<th width="30%" scope="row">
						<div align="left">Stake Holder <font color="#990033">*</font></div>
						</th>
						<td width="70%"><html:select property="stakeHolderId"
							style="width:300px" styleClass="inputBorder1">
							<html:option value="-1">Select</html:option>
							<logic:notEmpty name="masterProjectPlanBean"
								property="stakeHolderList">
								<html:optionsCollection property="stakeHolderList"
									label="rolename" value="roleid" />
							</logic:notEmpty>
						</html:select></td>
					</tr>
					<tr>
						<th width="30%" scope="row">
						<div align="left">First Task<font color="#990033"></font></div>
						</th>
						<td width="70%"><html:checkbox property="first"></html:checkbox></td>
	
					</tr>
					<tr>
						<th width="30%" scope="row">
						<div align="left">Last Task<font color="#990033"></font></div>
						</th>
						<td width="70%"><html:checkbox property="last"></html:checkbox></td>
	
					</tr>
					<tr>
						<th width="30%" scope="row">
						<div align="left">Previous Task <font color="#990033"></font></div>
						</th>
						<td width="70%"><html:select property="previousTaskId" styleId="id_previousTaskId"
							multiple="" style="width:400px" styleClass="inputBorder1">
							<logic:notEmpty name="masterProjectPlanBean" property="taskList">
								<html:optionsCollection property="taskList" label="taskname"
									value="taskid" />
							</logic:notEmpty>
						</html:select></td>
					</tr>
					
					<tr>
						<th width="30%" scope="row">
						<div align="left">Remarks<font color="#990033">*</font></div>
						</th>
						<td width="70%"><html:textarea property="remarks" cols="100" styleId="remarks"
							rows="10" styleClass="inputBorder1"/></td>														
					</tr>
					
	
					<tr align="center">
						<th colspan="2" scope="row" width="50%">
						<table width="50%" border="0">
							<tr>
								<td></td>
								<td>
								<div id="id_saveButton" style="display:block;">
								<html:submit property="method" styleId="saveButton"
									onclick="return saveTask();">
									<bean:message key="submit.saveTask" bundle="ButtonResources" />
								</html:submit>
								</div>
								<div id="id_updateButton" style="display: none;">
									<html:submit property="method" styleId="updateButton"
										onclick="return saveTask();">
										<bean:message key="submit.updateTask" bundle="ButtonResources" />
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
			<td valign="top" width="70%">
			
				<!-- rohit verma new cr -->
				<html:hidden property="deleteTaskId" value=""/>
				<html:hidden property="noDeleteTaskId" value=""/>
				
				<logic:notEmpty name="masterProjectPlanBean" property="taskList">
					<table width="100%" border="1" class="tabledata" cellpadding="3"
						cellspacing="1" align="center">


						<tr class="grayBg">
							<th colspan="2">Existing Tasks</th>
							<th><div class="buttonLarge" onClick="javascript:deleteTask();">Remove</div></th>
						</tr>
						<tr>
							<th></th>
						</tr>
						<%String[] colors={"normal","lightBg"}; %>
						<logic:iterate id="taskList_id" name="masterProjectPlanBean"
							property="taskList" indexId="index1">
								<tr class="<%=colors[index1.intValue()%2]%>">
									<td><a href="#"
										onclick="viewTask2(<bean:write name="taskList_id" property="taskid"/>);"><c:out
										value="${taskList_id.taskname}" /></a>
										<logic:equal name="taskList_id" property="isfirsttask" value="1">
											<font color="red">(FIRST)</font>
										</logic:equal>
										<logic:equal name="taskList_id" property="islasttask" value="1">
											<font color="red">(LAST)</font>
										</logic:equal>
										</td>
									<td><a href="#" onclick="modifyTask(<bean:write name="taskList_id" property="taskid"/>)">
										Edit</a>
									</td>
									<td align="center"><!--Rohit verma new cr  
										<a href="#" onclick="deleteTask(<bean:write name="taskList_id" property="taskid"/>)">
										Delete</a
										>-->	
										<input type="checkbox" name="selectedTaskId" value='<bean:write name="taskList_id" property="taskid"/>' onclick="javascript: getcheckedTaskId(this.checked,this.value);")">
									</td>
								</tr>
							</logic:iterate>

					</table>
				</logic:notEmpty>
			
			</td>
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
