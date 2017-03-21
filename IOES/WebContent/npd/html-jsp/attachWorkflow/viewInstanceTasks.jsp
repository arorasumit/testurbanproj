<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="com.ibm.ioes.npd.utilities.Messages"%>
<%@page import="com.ibm.ioes.npd.beans.ProjectPlanInstanceBean"%>

<%@page import="com.ibm.ioes.npd.beans.UploadProductPlanBean"%>
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

function planActions(val)
{
	if(val=='finalize')
		{
		var myForm=document.getElementById('planForm');
		var msg='';
		if(myForm.totalTasks.value=='0')
		{
			msg=msg+"\n"+"Plan Cannot Be Finalized As No Tasks Present";
			alert(msg);
			return;
		}
		if(!fnGetPreviousTaskStatus())
		{			
			return false;
		}
		if(myForm.firstTaskDefined.value=='NO')
		{
			msg=msg+"\n"+"No First Task Defined";
		}
		if(myForm.lastTaskDefined.value=='NO')
		{
			msg=msg+"\n"+"No Last Task Defined";		
		}
		if(myForm.allPrevTaskDefined.value=='NO')
		{
			msg=msg+"\n"+"Previous Task of Some Tasks Not Defined";		
		}
		
		if(msg!='')
		{
			alert(msg);
			return;
		}
		myForm.method.value='finalize';
		showLayer();
		myForm.submit();
	}
	if(val=='draft')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='draft';
		showLayer();
		myForm.submit();
	}
	else if(val=='addNewTask')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='addNewTask';
		showLayer();
		myForm.submit();
	}
	//ROHIT VERMA
	else if(val=='removeTask')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='removeTask';
		myForm.action="<%=request.getContextPath()%>/attachEditProjectPlan.do?method=removeTask";
		showLayer();
		myForm.submit();
	}
	else if(val=='updateRoleEmployee')
	{
		var myForm=document.getElementById('planForm');
		var_projectId=myForm.projectId.value;
		var_projectWorkflowId=myForm.projectWorkflowId.value;
		//windowURL="<c:out value='${sessionScope.MenuContextPath}' />/attachEditProjectPlan.do?method=init_updateRoleEmployee&projectId="+var_projectId+"&projectWorkflowId="+var_projectWorkflowId;
		//windowDefault='<%=Messages.getMessageValue("windowDefault")%>';
		da=new Date();
		//window.open(windowURL,"Assign"+da.getTime(),windowDefault);
		
		windowDefault='<%=Messages.getMessageValue("windowDefault")%>';
		var chil=window.open("","Assign"+da.getTime(),windowDefault);
		var strF="<HTML><HEAD></HEAD><BODY><FORM name='childform' method='post' action='<c:out value='${sessionScope.MenuContextPath}' />/attachEditProjectPlan.do'>";
		var str1="<input type='hidden' name='method' value='init_updateRoleEmployee'>";
		var str2="<input type='hidden' name='projectId' value='"+var_projectId+"'>";
		var str3="<input type='hidden' name='projectWorkflowId' value='"+var_projectWorkflowId+"'>";		
		var strL="</FORM></BODY></HTML>";			
	
		var str=strF+str1+str2+str3+strL;
		chil.document.write(str);
		var chilForm=chil.document.childform;
		chilForm.submit();
	}

	else if(val=='updateTask')
	{
		var myForm=document.getElementById('planForm');
		
		if(!checkTaskSelectionMessage())
		{
			
			return;
		}
		
		if(document.getElementById('planForm').attachMode.value=='editingFinalized')
		{
			if(fnCheckForClosedTask())
			{
				alert('Task Already Closed hence Cannot Be Removed');
				return;
			}
		}
		
		myForm.method.value='updateTask';
		showLayer();
		myForm.submit();
	}
	else if(val=='viewTask')
	{
		var myForm=document.getElementById('planForm');
		alert('view');
		if(!checkTaskSelectionMessage())
		{
			return;
		}
		
		myForm.method.value='viewTask';
		showLayer();
		myForm.submit();
	}
	else if(val=='viewEditStages')
	{
		var myPlanForm=document.getElementById('planForm');
		
		myform=document.createElement("form");
		document.body.appendChild(myform);
		myform.action="<%=request.getContextPath()%>/attachEditProjectPlan.do";
		
		myform.appendChild(getHiddenField("method","viewEditStages"));
		myform.appendChild(getHiddenField("projectId",myPlanForm.projectId.value));		
		myform.appendChild(getHiddenField("projectWorkflowId",myPlanForm.projectWorkflowId.value));				
		myform.appendChild(getHiddenField("attachMode",myPlanForm.attachMode.value));						

		showLayer();
		myform.submit();
	}
	else if(val=='workflowHome')
	{
		var myPlanForm=document.getElementById('planForm');
		
		myform=document.createElement("form");
		document.body.appendChild(myform);
		myform.action="<%=request.getContextPath()%>/attachEditProjectPlan.do";
		
		myform.appendChild(getHiddenField("method","attachedProjectPlanHome"));
		myform.appendChild(getHiddenField("projectId",myPlanForm.projectId.value));	
		myform.appendChild(getHiddenField("attachMode",myPlanForm.attachMode.value));	
		
		showLayer();
		myform.submit();
	}
	else if(val=='updateAssignedTo')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='updateAssignedTo';
		
		showLayer();
		myForm.submit();
	}
	//ROHIT VERMA	NEW CR START
	else if(val=='prevTask')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='prevTask';
		myForm.action="<%=request.getContextPath()%>/attachEditProjectPlan.do?method=prevTask";
		showLayer();
		myForm.submit();
	}
	//ROHIT VERMA	NEW CR END
	else if(val=='uploadPlanExcel')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='uploadPlanExcelInitPage';
		showLayer();
		myForm.submit();
	}
	/*else if(val=='downloadMasterExcel')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='downloadPlanExcelForEdit';
		myForm.submit();
	}
	else if(val=='downloadTemplateExcel')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='downloadTemplateExcel';
		myForm.submit();
	}
	else if(val=='uploadPlanExcel')
	{
		var myForm=document.getElementById('planForm');
		var_projectId=myForm.projectId.value;
		var_projectWorkflowId=myForm.projectWorkflowId.value;
		
		windowDefault='<%=Messages.getMessageValue("excelWindowDefault")%>';
		var chil=window.open("","uploadPlanExcel",windowDefault);
		var strF="<HTML><HEAD></HEAD><BODY><FORM name='childform' method='post' action='<c:out value='${sessionScope.MenuContextPath}' />/attachEditProjectPlan.do'>";
		var str1="<input type='hidden' name='method' value='uploadPlanExcelInitPage'>";
		var str2="<input type='hidden' name='projectId' value='"+var_projectId+"'>";
		var str3="<input type='hidden' name='projectWorkflowId' value='"+var_projectWorkflowId+"'>";		
		var str4="<input type='hidden' name='uploadProductPlanBean.actionName' value='<%=UploadProductPlanBean.ACTION_INITPAGE%>'>";
		var strL="</FORM></BODY></HTML>";			
	
		var str=strF+str1+str2+str3+str4+strL;
	
		chil.document.write(str);
		var chilForm=chil.document.childform;
		chilForm.submit();

	}*/
}
function viewTaskDetails(valTaskId)
{
	var myForm=document.getElementById('planForm');


	var_projectId=myForm.projectId.value;
	var_projectWorkflowId=myForm.projectWorkflowId.value;
	var_taskId=valTaskId;
//	windowURL="<c:out value='${sessionScope.MenuContextPath}' />/attachEditProjectPlan.do?method=viewTask&projectId="+var_projectId+"&projectWorkflowId="+var_projectWorkflowId+"&selectedTaskId="+var_taskId;

//	windowDefault='<%=Messages.getMessageValue("windowDefault")%>';
	da=new Date();
//	window.open(windowURL,"Task"+da.getTime(),windowDefault);
	
	
	windowDefault='<%=Messages.getMessageValue("windowDefault")%>';
	var chil=window.open("","Assign"+da.getTime(),windowDefault);
	var strF="<HTML><HEAD></HEAD><BODY><FORM name='childform' method='post' action='<c:out value='${sessionScope.MenuContextPath}' />/attachEditProjectPlan.do'>";
	var str1="<input type='hidden' name='method' value='viewTask'>";
	var str2="<input type='hidden' name='projectId' value='"+var_projectId+"'>";
	var str3="<input type='hidden' name='projectWorkflowId' value='"+var_projectWorkflowId+"'>";		
	var str4="<input type='hidden' name='selectedTaskId' value='"+var_taskId+"'>";	
	var strL="</FORM></BODY></HTML>";			

	var str=strF+str1+str2+str3+str4+strL;

	chil.document.write(str);
	var chilForm=chil.document.childform;
	chilForm.submit();
}

function fnCheckForClosedTask()
{
	var myForm=document.getElementById('planForm');
	var var_selectedTaskId=myForm.selectedTaskId;
	
	var val_taskId;
	if(var_selectedTaskId.length!=null)
	{
		var isSelected=0;
		for(i=0;i<var_selectedTaskId.length;i++)
		{
			if(var_selectedTaskId[i].checked==true)
			{
				val_taskId=var_selectedTaskId[i].value;
			}
		}
	}
	else
	{
		if(var_selectedTaskId.checked==true)
		{
			val_taskId=var_selectedTaskId.value;
		}
	}		
	var taskStatus;	
	taskStatus=document.getElementById('id_taskStatus'+val_taskId).value;	
	if(taskStatus==2)
	{
		return true;
	}
	return false;
}

function flag(elem)
{
	var myForm=document.getElementById('planForm');
	str=new String(elem.name);
	i=str.indexOf("[");
	j=str.indexOf("]");
	val=str.substring(Number(i)+1,j);
	myForm.elements["taskRow["+val+"].flag"].value=1;
	myForm.elements["taskRow["+val+"].newAssigned"].value=elem.value;
	

}

function showTasks()
{
	var myForm=document.getElementById('planForm');
	//myForm.searchTaskName.value='';
	//myForm.searchTask_roleHolder.value='';
	//myForm.searchTask_assignedTo.value='';
	myForm.method.value='viewEditTasks';
	myForm.elements["tasksPS.pageNumber"].value=1;
	showLayer();
	myForm.submit();
	
}
function checkForm()
{
	if(ValidateTextField(document.getElementById("id_taskName_search"),'<%=request.getContextPath()%>',"Search Field Task Name")==false)
	{
		return false;
	}
	if(ValidateTextField(document.getElementById("id_roleHolder_search"),'<%=request.getContextPath()%>',"Search Field Role Holder")==false)
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
function forDisablingAssigned()
{
	var id_selectedTaskId=document.forms[0].selectedTaskId;

	if(id_selectedTaskId==null)
	{
		return false;
	}
	if(id_selectedTaskId.length==null)
	{
		val=document.forms[0].elements["tempTaskStatus0"].value;
		if(val=='CLOSED' || val=='OPEN_ACTIONTAKEN')
		{
			DisableAndGenerateHidden(document.forms[0].elements["taskRow[0].assignedtouserid"]);
			//askRow" property="assignedtouserid
		}
	}
	else
	{
		for(i=0;i<id_selectedTaskId.length;i++)
		{
			val=document.forms[0].elements["tempTaskStatus"+i].value;
			
			if(val=='CLOSED' || val=='OPEN_ACTIONTAKEN')
			{
				DisableAndGenerateHidden(document.forms[0].elements["taskRow["+i+"].assignedtouserid"]);
				//askRow" property="assignedtouserid
			}
		}
	}
}

function fnOnBodyLoad()
{
	fnRadioSelection();
	document.body.click();
	//forDisablingAssigned();
}
/*Function		:fnGetPreviousTaskStatus
*return type	:boolean
*@Author		:Anil Kumar
*Date			:09-02-2011
*Purpose		:To validation Previous task status when finalize the task in add new task,
*					through project page
*/
function fnGetPreviousTaskStatus()
{	
	var appContextPath = '<%=request.getContextPath()%>';
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var isValid = jsonrpc.processes.isWorkflowValid(document.forms[0].projectWorkflowId.value,"project");
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
<body onload="javascript:fnOnBodyLoad()">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
		
</div>
<html:form action="/attachEditProjectPlan" styleId="planForm">
<bean:define id="formBean" name="projectPlanInstanceBean"></bean:define>
<input type="hidden" name="method"/>
<html:hidden property="projectId"/>
<html:hidden property="projectWorkflowId"/>
<html:hidden property="tasksPS.sortByColumn"/>
<html:hidden property="tasksPS.sortByOrder"/>
<html:hidden property="tasksPS.pageNumber"/>
<html:hidden property="attachMode"/>

<div class="head"> <span>Attach Project Plan</span> </div>
<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border" align="center">
<tr valign="middle" id="newProduct">
	<td valign="bottom" width="100%" align="center">
		<!-- <table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Attach Project Plan</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1">
				</td>
			</tr>
	</table>-->
</tr>

			<%String []colors=new String[]{"normal","lightBg"}; %>
<tr>
	<td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="tab2" align="center">
		<html:messages id="message" message="true">
		<tr>
			<td align="center" style="color: red;">
				
					<li><bean:write name="message"/></li>

			</td>
		</tr>
		</html:messages>
	
		<tr>
			<td><table class="tab2" align="center"><tr><td>
				<div class="error" align="center">
					<logic:present name="validation_errors">
						<logic:iterate id="error_row" name="validation_errors" scope="request">
							<font color="red"><bean:write name="error_row" /></font><BR>
						</logic:iterate>
					</logic:present>
				</div></td></tr></table>
			</td>
		</tr>
	<tr>
		
		<td align="left">
			<table width="100%" border="0" class="tab2" cellpadding="3"
							cellspacing="1" align="center">
				<tr>
				<td colspan="9">
				<table width="100%" border="0" class="tab2" cellpadding="3"
					cellspacing="1" align="center">
					<tr width="100%" class="normal">
					<th align="left" width="53">
						Tasks
					</TH>
					<td align="left" >
						<table><tr>
						<td>
							<html:select property="taskOption" styleClass="inputBorder1" onchange="showTasks()">
								<html:option value="ALL">All</html:option>
								<html:option value="NOPREVLIST">No Previous Defined</html:option>
								<html:option value="FIRST">First</html:option>
								<html:option value="LAST">Last</html:option>
								<!--<html:option value="NOUSERATTACHED">No User Assigned</html:option>-->																														
							</html:select>
						</td>						
						</tr></table>
					</td>
					<th align="left" >
						Product Id: <bean:write name="formBean" property="project.projectid"/>
					</th>
					<th align="left" >
						Product Name: <bean:write name="formBean" property="project.projectName"/>
					</th>
					<td align="right" nowrap><span id="search" class="buttonLarge"
								onClick="planActions('finalize');">Finalize</span></td>	
					<td align="right"><a href="#"><img
						onclick="searchSubmit();" src="npd/Images/search.gif" title="search"
						height="15"></a>&nbsp;
					</td>
					</tr>
				</table>
				</td>
				
				</tr>
				<tr>
					<td colspan="9"  >
						  <bean:define id="pagingBean" name="formBean"></bean:define>
							<%String currPageNumber =String.valueOf(((ProjectPlanInstanceBean)formBean).getTasksPS().getPageNumber());
							String 	maxPageNumber =String.valueOf(((ProjectPlanInstanceBean)formBean).getTasksPS().getMaxPageNumber());
							%>
							<jsp:include flush="true" page="./../commons/paging.jsp">
								<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
								<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
							</jsp:include>
					</td>
				</tr>	
				<tr class="normal">
					<th width="4%" >
						&nbsp;
					</th>
					<th width="4%" >S.No.</th>		
					<th width="5%" ><a href="#" onclick="sortSubmit('taskName')">Task Name</a></th>
							
					<th width="5%" ><a href="#" onclick="sortSubmit('roleHolder')">Role Holder</a></th>
					
							
				</tr>
				<tr class="lightBg">
					<th width="4%" >
						&nbsp;
					</th>
					<th width="4%" >&nbsp;</th>		
					<th width="5%" ><html:text property="searchTaskName" maxlength="20" styleId="id_taskName_search" style="display:none"/></th>
								
					<th width="5%" ><html:text property="searchTask_roleHolder" maxlength="20" styleId="id_roleHolder_search" style="display:none"/></th>
					
								
				</tr>
				
				<logic:empty name="formBean" property="tasksView">
					<tr>
						<td colspan="9" align="center">No Tasks Present</td>
					</tr>
				</logic:empty>
				<logic:notEmpty name="formBean" property="tasksView">
				<%int taskSno= 1+(((ProjectPlanInstanceBean)formBean).getTasksPS().getPageNumber()-1)*
										((ProjectPlanInstanceBean)formBean).getTasksPS().getPageRecords();%>
				<%int count=0; %>
				<logic:iterate id="taskRow"	name="formBean" property="tasksView" indexId="index_task">
						<tr class="<%=colors[index_task.intValue()%2]%>">
							<td align="center">
								<%count=index_task.intValue(); %>
								
								<logic:equal name="taskRow" property="currentTaskStatus" value="CLOSED">
									<input type="radio" name="selectedTaskId" value='<bean:write name="taskRow" property="currenttaskid"/>' disabled="disabled">
								</logic:equal>
								<logic:equal name="taskRow" property="currentTaskStatus" value="OPEN_ACTIONTAKEN">
									<input type="radio" name="selectedTaskId" value='<bean:write name="taskRow" property="currenttaskid"/>' disabled="disabled">
								</logic:equal>
								<logic:equal name="taskRow" property="currentTaskStatus" value="OPEN_NOACTION">
									<input type="radio" name="selectedTaskId" value='<bean:write name="taskRow" property="currenttaskid"/>'>
								</logic:equal>
								<logic:equal name="taskRow" property="currentTaskStatus" value="NOTSTARTED">
									<input type="radio" name="selectedTaskId" value='<bean:write name="taskRow" property="currenttaskid"/>'>
								</logic:equal>
																
								<html:hidden name="taskRow" property="currenttaskid" indexed="true"/>
								<input type="hidden" name="taskRow[<%=index_task %>].flag" value="0">
							</td>
							<td align="center">
								<%=taskSno++%>
							</td>

							<td align="center"><a href="#" onclick="viewTaskDetails(<bean:write name="taskRow" property="currenttaskid"/>)">
								<bean:write name="taskRow" property="taskname"/></a>
								<input type="hidden" name="tempTaskStatus<%=index_task.intValue()%>" value='<bean:write name="taskRow" property="currentTaskStatus"/>'/>
								<logic:equal name="taskRow" property="currentTaskStatus" value="CLOSED">
									&nbsp;(Closed)
								</logic:equal>
								<logic:equal name="taskRow" property="currentTaskStatus" value="OPEN_ACTIONTAKEN">
									&nbsp;(OPEN :ACTION TAKEN)
								</logic:equal>
								<logic:equal name="taskRow" property="currentTaskStatus" value="OPEN_NOACTION">
									&nbsp;(OPEN :NO ACTION TAKEN)
								</logic:equal>
								<logic:equal name="taskRow" property="currentTaskStatus" value="NOTSTARTED">
									&nbsp;
								</logic:equal>								
							</td>
							<td align="center"><bean:write name="taskRow" property="taskstakeholderName"/></td>																									
						</tr>	
						<input type="hidden" name="taskStatus<bean:write name="taskRow" property="currenttaskid"/>" 
											value="<bean:write name="taskRow" property="taskstatus"/>" 
											id="id_taskStatus<bean:write name="taskRow" property="currenttaskid"/>" />					
				</logic:iterate>
					
				<input type="hidden" name="count" value="<%=count %>"/>
				
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
							<td align="center" nowrap><span id="search" class="buttonLarge"
								onClick="planActions('addNewTask');">New Task</span></td>								
							<td align="center" nowrap><span id="search" class="buttonLarge"
								onClick="planActions('removeTask');">Remove Task</span></td>		
							<td align="center" nowrap><span id="search" class="buttonLarge"
								onClick="planActions('updateTask');">Modify Task</span></td>
							
							<td align="center" nowrap><span id="search" class="buttonLarge"
								onClick="planActions('draft');">Draft &amp; Close</span></td>
							<td align="center" nowrap><span id="search" class="buttonLarge"
								onClick="planActions('workflowHome');">Workflow Home</span></td>	
								<!-- ROHIT VERMA	NEW CR START -->
							<td align="center" nowrap><span id="search" class="buttonLarge"
								onClick="planActions('prevTask');">Edit Previous Task</span></td>	
								<!-- ROHIT VERMA	NEW CR End -->																								
							
							<logic:equal name="formBean" property="attachMode" value="attachNew">
							<!--<td align="center" nowrap><span id="search" class="buttonLarge"
								onClick="planActions('uploadPlanExcel');">Upload Excel</span></td>-->
							</logic:equal>
						</tr>						
					</table>
	</td>
</tr>
<tr>
	<td>
		<table width="40%" border="0" class="tab2" cellpadding="3"
							cellspacing="1" align="center" style="display: block;">
			<tr class="normal">
				<td  width="100%" align="left" colspan="2">
					Checklist
					<html:hidden property="totalTasks"/>
				</td>
			</tr>
			<tr class="lightBg">
				<td align="center" nowrap>First Task :</td>		
				<td align="center" nowrap>
					<logic:present name="formBean" property="firstTask">
						YES (<bean:write name="formBean" property="firstTask.taskname"/>)
						<input type="hidden" name="firstTaskDefined" value="YES"/>
					</logic:present>
					<logic:notPresent name="formBean" property="firstTask">
						NOT DEFINED
						<input type="hidden" name="firstTaskDefined" value="NO"/>						
					</logic:notPresent>					
				</td>		
			</tr>						
			<tr class="normal">
				<td align="center" nowrap>Last Task :</td>		
				<td align="center" nowrap>
					<logic:present name="formBean" property="lastTask">
						YES (<bean:write name="formBean" property="lastTask.taskname"/>)
						<input type="hidden" name="lastTaskDefined" value="YES"/>						
					</logic:present>
					<logic:notPresent name="formBean" property="lastTask">
						NOT DEFINED
						<input type="hidden" name="lastTaskDefined" value="NO"/>												
					</logic:notPresent>					
				</td>		
			</tr>		
			<tr class="lightBg">
				<td align="center" nowrap>Previous Tasks of all Tasks Defined :</td>		
				<td align="center" nowrap>
					<logic:empty name="formBean" property="noPrevTaskList">
						<input type="hidden" name="allPrevTaskDefined" value="YES"/>											
						YES
					</logic:empty>
					<logic:notEmpty name="formBean" property="noPrevTaskList">
						<input type="hidden" name="allPrevTaskDefined" value="NO"/>																
						NO
					</logic:notEmpty>
				</td>		
			</tr>											
							
		</table>
	</td>
</tr>
</table>
</html:form>
</div>
</BODY>


</html:html>
