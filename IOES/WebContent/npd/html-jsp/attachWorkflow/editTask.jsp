<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Anil Kumar		3-Oct-11	CSR00-05422     Use Ctrl + S for Save  -->
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
<script type="text/javascript" src="npd/js/utility.js"></script>
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>
<script type="text/javascript" src="npd/js/inputColor.js"></script>

<script type="text/javascript">
var isUpdateFlag=false;
function checkFirst(elem)
{
	if(elem.checked==true)
	{
		myForm=document.getElementById('taskForm');
		var status;
		var bean;
		var currentTaskId=myForm.elements["taskInstanceBean.currenttaskid"].value;
		var projectWorkflowId=myForm.elements["projectWorkflowId"].value;
		
		var appContextPath = '<%=request.getContextPath()%>';
		try
		{	
			//var obj = document.getElementById('var_assignedtouserid');
			jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	   		var result;
			result = jsonrpc.processes.getInfoProjectInstanceFirstTask(projectWorkflowId,currentTaskId);

			status=result.list[0];
			bean=result.list[1];			
		}
		catch(e) {
//			alert(e);
			return false;
		}
		
		
		if(status=='firstAlreadyPresent')
		{
//			alert(bean.taskname);
			var answer=confirm(bean.taskname+" is already a First Task. Do you want to change the First task");
			if(answer)
			{
				elem.checked=true;
			}
			else
			{
				elem.checked=false;
			}
		}
		//else if(status=='noFirstPresent'){} doing none
	}
	 forNoPrevTaskForFirst();
}


function checkLast(elem)
{
	if(elem.checked==true)
	{
		myForm=document.getElementById('taskForm');
		var status;
		var bean;
		var currentTaskId=myForm.elements["taskInstanceBean.currenttaskid"].value;
		var projectWorkflowId=myForm.elements["projectWorkflowId"].value;
		
		var appContextPath = '<%=request.getContextPath()%>';
		try
		{	
			//var obj = document.getElementById('var_assignedtouserid');
			jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	   		var result;
			result = jsonrpc.processes.getInfoProjectInstanceLastTask(projectWorkflowId,currentTaskId);

			status=result.list[0];
			bean=result.list[1];			
		}
		catch(e) {
			//alert(e);
			return false;
		}
		
		
		if(status=='lastAlreadyPresent')
		{
			//alert(bean.taskname);
			var answer=confirm(bean.taskname+" is already a Last Task. Do you want to change the Last task");
			if(answer)
			{
				elem.checked=true;
			}
			else
			{
				elem.checked=false;
			}
		}
		//else if(status=='noFirstPresent'){} doing none
	}
}

function toggleTemplateSelect(elem)
{
	if(elem.checked==true)
	{
		document.getElementById('id_taskReferencedocid').disabled=false;
	}
	else
	{
		document.getElementById('id_taskReferencedocid').disabled=true;	
	}
}

function fillEmployees(elem)
{
	var appContextPath = '<%=request.getContextPath()%>';
	try
	{	
		var obj = document.getElementById('var_assignedtouserid');
			jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	   		var result;
			result = jsonrpc.processes.getUsersOfRole(elem.value);

			for(i=obj.options.length-1;i>=0 ;i--)
			{
			obj.options[i] = null;
			}
			obj.options[0] = new Option("Select", "-1");
			if( result !=null && result.list.length > 0)
			{
				for(i=0;i < result.list.length;i++)
				{
				var name = result.list[i].empname;
				var id=result.list[i].npdempid;
				obj.options[i+1] = new Option(name,id);
				
				}//for
			}//if result
	}
	catch(e) {
		//alert(e);
		return false;
	}
}
function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function checkForm(val)
{
	var errorString="";
	if(document.getElementById("id_stageId").value==-1)
	{
		errorString+="Please Select Stage"+"\n";
	}
	if(document.getElementById("id_taskname").value==null||trim(document.getElementById("id_taskname").value)=="")
	{
		errorString+="Please enter Task"+"\n";
	}
	if(document.getElementById("id_taskdesc").value==null||trim(document.getElementById("id_taskdesc").value)=="")
	{
		errorString+="Please enter Task Description"+"\n";
	}
	else
	{
	if(document.getElementById("id_taskdesc").value.length>500)
		{
			errorString+="Task Decription Cannot Be Greater Than 500 characters.";
		}
	}
	
	if(val=='saveNew')
	{
		if(document.getElementById("id_taskstakeholder").value==-1)
		{
			errorString+="Please Select Stake Holder"+"\n";
		}
	}
	/*if(document.getElementById("var_assignedtouserid").value==-1)
	{
		errorString+="Please Select User"+"\n";
	}*/	
	
	
	if(document.getElementById("id_taskTaskinstructionremarks").value==null||trim(document.getElementById("id_taskTaskinstructionremarks").value)=="")
	{
		errorString+="Please enter Remarks"+"\n";
	}
	else
	{
	if(document.getElementById("id_taskTaskinstructionremarks").value.length>1000)
		{
			errorString+="Remarks Cannot Be Greater Than 1000 characters.";
		}
	}
	if(errorString!='')
	{
		
		alert(errorString);
		return false;
	}
	if(ValidateTextField(document.getElementById("id_taskname"),'<%=request.getContextPath()%>',"Task Name")==false)
		{
			return false;
		}
		if(ValidateTextField(document.getElementById("id_taskdesc"),'<%=request.getContextPath()%>',"Task Description")==false)
		{
			return false;
		}
		if(ValidateTextField(document.getElementById("id_taskTaskinstructionremarks"),'<%=request.getContextPath()%>',"Remarks")==false)
		{
			return false;
		}
	return true;
}
/*function chkForCanChange()
{
	<logic:equal name="projectPlanInstanceBean" property="taskInstanceBean.currentTaskStatus" value="OPEN_NOACTION">
		alert("Cannot Change Previous Tasks of OPEN Tasks");
		restorePrevious();
	</logic:equal>
}*/
/*function restorePrevious()
{
		var idd=document.getElementById('id_prevtaskid');
		var csv=csvPrevious;
		for(i=idd.options.length-1;i>=0;i--)
		{
			idd.options[i]=null;
		}
		
		//parse csv to array
		var myArr = csv.split(',');
		
		for (var j=0; j<myArr.length; j+=1) {
			var name = csvPreviousNames[j];
			var id=myArr[j];
			idd.options[j] = new Option(name,id);
		}		
}*/

function selectAllSelected()
{
	if(document.getElementById("id_prevtaskid").length > 0)
	{
		for(x=0;x<document.getElementById("id_prevtaskid").length;x++)
		{
			document.getElementById("id_prevtaskid").options[x].selected=true;
		}
	}
}

function performAction(val)
{
	myform1=document.getElementById("taskForm");
	setFormBean(myform1);
	if(val=='update')
	{
		var myForm=document.getElementById('taskForm');
		if(checkForm('update')==false)
		{
			return false;
		}
		myForm.method.value='saveUpdate';
		
		selectAllSelected();
		showLayer(); 
		myForm.submit();
	}
	else if(val=='viewEditTasks')
	{/*
		var myPlanForm=document.getElementById('taskForm');
		
		myform=document.createElement("form");
		document.body.appendChild(myform);
		myform.action="<%=request.getContextPath()%>/attachEditProjectPlan.do";
		

		myform.appendChild(getHiddenField("method","viewEditTasks"));
		myform.appendChild(getHiddenField("projectId",myPlanForm.projectId.value));		
		myform.appendChild(getHiddenField("projectWorkflowId",myPlanForm.projectWorkflowId.value));				
		myform.appendChild(getHiddenField("selectedStageId",myPlanForm.selectedStageId.value));				
		myform.appendChild(getHiddenField("attachMode",myPlanForm.attachMode.value));	
		

		
		
		showLayer(); 
		myform.submit();
		*/
		
		var myForm=document.getElementById('taskForm');
		myForm.method.value='viewEditTasks';
		showLayer(); 
		myForm.submit();
		
	}else if(val=='saveNew')
	{
		var myForm=document.getElementById('taskForm');
		if(checkForm('saveNew')==false)
		{
			return false;
		}
		
		myForm.method.value='saveNewTask';
		
		selectAllSelected();
		showLayer(); 
		myForm.submit();
	}
	
}


function DisableAndGenerateHidden(elem)
{
//alert(elem.name);
//alert(elem.value);
//	var input = document.createElement("input");
//	input.setAttribute("type", "hidden");
//	input.setAttribute("name", elem.name);
//	input.setAttribute("value", elem.value);
//	par=elem.parentNode;
//	par.appendChild(input);
//	elem.disabled=true;
}


function forUpdateTask()
{
	DisableAndGenerateHidden(document.getElementById('id_stageId'));
	document.getElementById('id_taskname').readOnly=true;
	document.getElementById('id_taskdesc').readOnly=true;	
	DisableAndGenerateHidden(document.getElementById('id_taskstakeholder'));	
	//DisableAndGenerateHidden(document.getElementById('var_assignedtouserid'));	

	if(document.projectPlanInstanceBean.attachMode.value=='editingFinalized')
	{
		if(document.getElementById('id_isfirsttask').checked==true)//if updating a first task of already finalized one , then we cannot unselect it
		{
			DisableAndGenerateHidden(document.getElementById('id_isfirsttask'));
		}
		else
		{
			document.getElementById('id_isfirsttask').disabled=true;//cannot make another task first for a already finalized plan
		}
	}
}

function forCreateTask()
{
	//DisableAndGenerateHidden(document.getElementById('var_assignedtouserid'));
	if(document.projectPlanInstanceBean.attachMode.value=='editingFinalized')
	{
		document.getElementById('id_isfirsttask').disabled=true;
	}
}

function forViewTask()
{
	//set all field & button disabled
	DisableAndGenerateHidden(document.getElementById('id_stageId'));
	document.getElementById('id_taskname').readOnly=true;
	document.getElementById('id_taskdesc').readOnly=true;
	document.getElementById('id_taskTasktype').disabled=true;	
//	document.getElementById('id_rejectionAllowed').disabled=true;		
	//document.getElementById('id_isemailtemplate').disabled=true;		
	DisableAndGenerateHidden(document.getElementById('id_taskstakeholder'));		
	//DisableAndGenerateHidden(document.getElementById('var_assignedtouserid'));	
	document.getElementById('id_isfirsttask').disabled=true;		
	document.getElementById('id_islasttask').disabled=true;		
//	DisableAndGenerateHidden(document.getElementById('id_prevtaskid'));	
//	document.getElementById('id_taskduration').readOnly=true;	
//	document.getElementById('id_taskIsattachmen').disabled=true;	
//	DisableAndGenerateHidden(document.getElementById('id_taskReferencedocid'));
	document.getElementById('id_taskTaskinstructionremarks').readOnly=true;		
	
	document.getElementById('id_buttonShift1').disabled="true";
	document.getElementById('id_buttonShift2').disabled="true";
	
	document.onclick = documentClick;
}


function documentClick(){
    document.getElementById('taskForm').reset();
}




function forNoPrevTaskForFirst()//no prev task for a fist task
{
	if(document.getElementById('id_isfirsttask').checked==true)
	{
		document.getElementById('id_prevtaskid').value='-1';		
		document.getElementById('id_prevtaskid').disabled=true;
	}
	else
	{
		document.getElementById('id_prevtaskid').disabled=false;
	}
}
/*
function storePrevious()//storing prev values for a open no action taken task , sotha t its previous cannot change
{
	<logic:equal name="projectPlanInstanceBean" property="taskInstanceBean.currentTaskStatus" value="OPEN_NOACTION">
		idd=document.getElementById('id_prevtaskid');
		var csv="";
		csvPreviousNames=new Array();
		for(i=0;i<idd.options.length;i++)
		{
			//if(idd.options[i].selected==true)
			//{
				csv=csv+","+idd.options[i].value;
				csvPreviousNames[i]=idd.options[i].name;
			//}
		}
		if(csv.length>0)
		{
			csv=csv.substring(1);
		}
		csvPrevious=csv;
	</logic:equal>

}*/
function hidePreviousChange()//storing prev values for a open no action taken task , sotha t its previous cannot change
{
	<logic:equal name="projectPlanInstanceBean" property="taskInstanceBean.currentTaskStatus" value="OPEN_NOACTION">
		document.getElementById('id_buttonShift1').disabled="true";
		document.getElementById('id_buttonShift2').disabled="true";
	</logic:equal>

}
//var csvPrevious="";
//var csvPreviousNames;

function forTaskMode()
{
	val_taskMode=document.getElementById('id_taskMode').value;
	if(val_taskMode=='view')
	{
		forViewTask();
		document.getElementById("viewHeading").style.display="block";
		document.getElementById("viewBackButton").style.display="block";
	}
	else if(val_taskMode=='createNew')
	{
		forCreateTask();
		//forIsAttachment();
		//document.getElementById("createHeading").style.display="block";	
		document.getElementById("viewBackButton").style.display="none";			
		isUpdateFlag=false;
	}
	else if(val_taskMode=='update')
	{
		forUpdateTask();
		//forIsAttachment();
		forNoPrevTaskForFirst();
		//storePrevious();
		hidePreviousChange();
		//document.getElementById("editHeading").style.display="block";		
		document.getElementById("viewBackButton").style.display="none";		
		isUpdateFlag=true;
	}
}

function forIsAttachment()
{
	/*elem=document.getElementById('id_taskIsattachmen');
	if(elem.checked==true)
	{
		document.getElementById('id_taskReferencedocid').disabled=false;
	}
	else
	{
		document.getElementById('id_taskReferencedocid').disabled=true;	
	}*/
}


function onBodyLoad()
{
	forTaskMode();
	document.getElementById("id_stageId").selectedIndex =1;
	
}

function addOption1()
{
	myform=document.getElementById('taskForm');

	totaloption=document.getElementById("id_unSelectedPrevtaskid").length;
	for(i=0;i<totaloption;i++)
	{
		if(document.getElementById("id_unSelectedPrevtaskid").options[i].selected)
	 	{
			optionValue=document.getElementById("id_unSelectedPrevtaskid").options[i].value
			optionLabelTxt=document.getElementById("id_unSelectedPrevtaskid").options[i].text
			optionTxt=optionLabelTxt
			newoption= new Option(optionTxt,optionValue); 
			document.getElementById("id_prevtaskid").options[document.getElementById("id_prevtaskid").length]=newoption;
		}
	}
	for(i=totaloption-1;i>=0;i--)
	{
		if(document.getElementById("id_unSelectedPrevtaskid").options[i].selected)
 		{
 			document.getElementById("id_unSelectedPrevtaskid").options[i]=null
  		}
	}
}

function removeOption1()
{
	myform=document.getElementById('taskForm');
	
	totaloption=document.getElementById("id_prevtaskid").length;
	for(i=0;i<totaloption;i++)
	{
		if(document.getElementById("id_prevtaskid").options[i].selected)
		{
			optionValue=(document.getElementById("id_prevtaskid").options[i].value);//.split("~~~+++")[1];
			optionLabelTxt=document.getElementById("id_prevtaskid").options[i].text;
			//optionLabelTxt=optionLabelTxt.substring(optionLabelTxt.indexOf("-")+1,  optionLabelTxt.length)
			optionTxt=optionLabelTxt; //(document.getElementById("optionalId").options[i].value).split("~~~+++")[2]
			newoption= new Option(optionTxt,optionValue);
			document.getElementById("id_unSelectedPrevtaskid").options[document.getElementById("id_unSelectedPrevtaskid").length]=newoption;
		}
	}
	
	for(i=totaloption-1;i>=0;i--)
	{
		if(document.getElementById("id_prevtaskid").options[i].selected)
		{
			document.getElementById("id_prevtaskid").options[i]=null
		}
	}
}
function onViewBackButton()
{
	window.close();
}
//start[001]
document.onkeydown=checkKeyPress;
function checkKeyPress()
{
	if (event.ctrlKey && event.keyCode == 83) {						
				event.keyCode=4; 
				if(isUpdateFlag==false)
				{
					performAction('saveNew');
					document.getElementById("firstButton").tabIndex = -1;
					document.getElementById("firstButton").focus();				   				   							
				}else{
					performAction('update');
					isUpdateFlag=false;
					document.getElementById("firstButton").tabIndex = -1;
					document.getElementById("firstButton").focus();			
				}
				
   }   
}
//end[001]
</script>

</head>
<body onload="onBodyLoad()">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
		
</div>
<div class="head"> <span>Edit Task</span> </div>
<span id="viewBackButton" class="buttonLarge"
onClick="onViewBackButton()">Back</span> 
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" align="center">
		<!-- <table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
					<td height="19" width="1"><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" height="19" width="29%">Edit Task
					<div id="editHeading" style="display: none;">Edit Task</div>
					<div id="createHeading" style="display: none;">Create Task</div>
					<div id="viewHeading" style="display: none;">View Task</div>										
					</td>
					<td height="19" width="0"><img src="npd/Images/tabRight.gif"></td>
					<td align="left" height="19" width="772"><font size="1">
				<td ><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>-->
		</td>
	</tr>
</table>
<table class="tab2" align="center">
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
</table>
<html:form action="/attachEditProjectPlan" method="post" styleId="taskForm">
	<bean:define id="formBean" name="projectPlanInstanceBean"/>
	<html:hidden property="taskInstanceBean.taskMode" styleId="id_taskMode"/>
	<html:hidden property="taskInstanceBean.currenttaskid"/>
	<html:hidden property="projectId"/>	
	<html:hidden property="projectWorkflowId"/>	
	<html:hidden property="selectedStageId"/>	
	<html:hidden property="attachMode"/>
	<input type="hidden" name="method"/>	
	
	<!-- hidden properties for implementing back on the page from which user came from-->
	<html:hidden property="tasksPS.sortByColumn"/>
	<html:hidden property="tasksPS.sortByOrder"/>
	<html:hidden property="tasksPS.pageNumber"/>
	<html:hidden property="taskOption"/>
	<html:hidden property="searchTaskName"/>

	<html:hidden property="searchTask_roleHolder"/>
	<html:hidden property="searchTask_assignedTo"/>
		
	<div style="overflow:scroll;height:100%;width:98%"
									class="scroll">
	<table width="75%" border="2" class="tab2" cellpadding="0"
		cellspacing="0" align="center">
		<html:messages id="message" message="true">
		<tr>
			<td colspan="2" align="center" style="color: red;">

					<li><bean:write name="message"/></li>

			</td>
		</tr>
		</html:messages>
		<tr>

			<td width="100%">
			<table width="100%" border="0" class="tab2" cellpadding="0"
				cellspacing="0" align="center">
				<tbody id='addTask' >
						<tr style="visibility: hidden;">
							<th width="30%" scope="row">
							<div align="left">Stage <font color="#990033">*</font></div>
							</th>
							<td width="70%"><html:select property="taskInstanceBean.currentstageid"
								style="width:140px" styleId="id_stageId" styleClass="inputBorder1">
								<html:option value="-1">Select</html:option>
								<logic:notEmpty name="formBean"
									property="taskInstanceBean.stageList">
									<html:optionsCollection property="taskInstanceBean.stageList"
										label="stagename" value="currentstageid" />
								</logic:notEmpty>
							</html:select></td>
						</tr>

						<tr>
							<th width="30%" scope="row">
							<div align="left">Task <font color="#990033">*</font></div>
							</th>
							<td width="70%"><html:text property="taskInstanceBean.taskname" styleClass="inputBorder1" maxlength="500"
								style="width:400px" styleId="id_taskname"/></td>
						</tr>

						<tr>
							<th width="30%" scope="row">
							<div align="left">Task Description<font color="#990033">*</font></div>
							</th>
							<td width="70%"><html:textarea property="taskInstanceBean.taskdesc"
								cols="100" rows="7" styleClass="inputBorder1" styleId="id_taskdesc" /></td>
						</tr>
							
						<tr>
							<th width="30%" scope="row">
							<div align="left">Stake Holder <font color="#990033">*</font></div>
							</th>
							<td width="70%"><html:select property="taskInstanceBean.taskstakeholder"
								style="width:250px" onchange="fillEmployees(this)" styleClass="inputBorder1" styleId="id_taskstakeholder">
								<html:option value="-1">Select</html:option>
								<logic:notEmpty name="formBean"
									property="taskInstanceBean.taskstakeholderList">
									<html:optionsCollection property="taskInstanceBean.taskstakeholderList"
										label="rolename" value="roleid" />
								</logic:notEmpty>
							</html:select></td>
						</tr>						
						<tr>
							<th width="30%" scope="row">
							<div align="left">First Task<font color="#990033"></font></div>
							</th>
							<td width="70%"><html:checkbox property="taskInstanceBean.isfirsttask" 
														styleId="id_isfirsttask"	styleClass="inputBorder1"	value="Y" onclick="checkFirst(this)">
							</html:checkbox></td>

						</tr>
						<tr>
							<th width="30%" scope="row">
							<div align="left">Last Task<font color="#990033"></font></div>
							</th>
							<td width="70%"><html:checkbox property="taskInstanceBean.islasttask" value="Y"
												onclick="checkLast(this)" styleClass="inputBorder1" styleId="id_islasttask"></html:checkbox></td>

						</tr>

						<tr>
							<th width="30%" scope="row">
							<div align="left">Previous Task <font color="#990033"></font></div>
							</th>
							<td width="50%">
								<table width="100%"><tr><td width="47%">
								<html:select property="taskInstanceBean.unSelectedPrevtaskid" 
								style="width:100%" styleClass="inputBorder1" styleId="id_unSelectedPrevtaskid" multiple="true">
									<logic:notEmpty name="formBean" property="taskInstanceBean.prevTaskUnSelectedList">
										<html:optionsCollection property="taskInstanceBean.prevTaskUnSelectedList" label="taskname"
											value="currenttaskid" />
									</logic:notEmpty>
								</html:select></td><td style="vertical-align: middle" align="center">
								<input type="button" value="&gt;&gt;&gt;" onclick="addOption1();" id="id_buttonShift1"><br><br>
								<input type="button" value="&lt;&lt;&lt;" onclick="removeOption1();" id="id_buttonShift2"></td>
								<td width="47%">
								<html:select property="taskInstanceBean.prevtaskid" 
								style="width:100%" styleClass="inputBorder1" styleId="id_prevtaskid" multiple="true">
									<logic:notEmpty name="formBean" property="taskInstanceBean.prevTaskSelectedList">
										<html:optionsCollection property="taskInstanceBean.prevTaskSelectedList" label="taskname"
											value="currenttaskid" />
									</logic:notEmpty>
								</html:select></td>
								</tr></table>
							</td>
						</tr>
						<tr>
							<th width="30%" scope="row">
							<div align="left">Remarks<font color="#990033">*</font></div>
							</th>
							<td width="70%"><html:textarea property="taskInstanceBean.taskTaskinstructionremarks" cols="100"
								rows="10" styleClass="inputBorder1" styleId="id_taskTaskinstructionremarks"/></td>
						</tr>


						<tr align="center">
							<th colspan="2" scope="row" width="50%">
							<table width="50%" border="0">
								<tr>
								
								<logic:equal name="formBean" property="taskInstanceBean.taskMode" value="createNew">
									<td>
										<span id="firstButton" class="buttonLarge"
											onClick="performAction('saveNew')">Save</span> 
									</td>
									<td>
										<span id="firstButton" class="buttonLarge"
											onClick="performAction('viewEditTasks')">Back</span> 
									</td>
								</logic:equal>

								<logic:equal name="formBean" property="taskInstanceBean.taskMode" value="update">
									<td>
										<span id="firstButton" class="buttonLarge"
											onClick="performAction('update')">Update</span> 
									</td>
									<td>
										<span id="firstButton" class="buttonLarge"
											onClick="performAction('viewEditTasks')">Back</span> 
									</td>
								</logic:equal>
								<logic:equal name="formBean" property="taskInstanceBean.taskMode" value="view">
									<td>
										<logic:equal name="formBean" property="taskInstanceBean.secondaryTaskMode" value="mainWindow">
											<span id="firstButton" class="buttonLarge"
												onClick="performAction('viewEditTasks')">Back</span> 
										</logic:equal>
									</td>
								</logic:equal>	
								</tr>
							</table>
							</th>
						</tr>

				</tbody>
				
			</table>
		</tr>

	</table>
	</div>
</html:form>
</div>
</BODY>


</html:html>
