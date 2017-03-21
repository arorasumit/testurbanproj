<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="com.ibm.ioes.npd.utilities.Messages"%>
<%@page import="com.ibm.ioes.npd.beans.ProjectPlanInstanceBean"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script language="JavaScript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/scw.js"></script>
<script type="text/javascript" src="npd/js/utility.js"></script>
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
function goToHome()
{
	var myForm=document.getElementById('planForm');
	//myForm.toExcel.value='false';
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}
function planActions(val)
{
	if(val=='discardDraft')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='discardDraft';
		showLayer();
		myForm.submit();
	}
	else if(val=='draft')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='draft';
		showLayer();
		myForm.submit();
	}
	else if(val=='finalize')
	{
		var myForm=document.getElementById('planForm');
		var msg='';
		var total=document.getElementById('totalTasks').value;								
		if(total=='0')
		{
			msg=msg+"\n"+"Plan Cannot Be Finalized As No Tasks Present";
			alert(msg);
			return;
		}

		if(document.getElementById('firstTaskDefined').value=='NO')
		{
			msg=msg+"\n"+"No First Task Defined";
		}
		if(document.getElementById('lastTaskDefined').value=='NO')
		{
			msg=msg+"\n"+"No Last Task Defined";		
		}
		if(document.getElementById('allPrevTaskDefined').value=='NO')
		{
			msg=msg+"\n"+"Previous Task of Some Tasks Not Defined";		
		}
		if(!fnGetPreviousTaskStatus())
		{			
			return false;
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
	else if(val=='viewEditStages')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='viewEditStages';
		showLayer();
		myForm.submit();
	}
	else if(val=='viewEditTasks')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='viewEditTasks';
		showLayer();
		myForm.submit();
	}
}
function showTasks()
{
	var myForm=document.getElementById('planForm');
	//myForm.searchTaskName.value='';
	//myForm.searchTask_roleHolder.value='';
	//myForm.searchTask_assignedTo.value='';
	myForm.method.value='attachedProjectPlanHome';
	myForm.elements["tasksPS.pageNumber"].value=1;
	showLayer();
	myForm.submit();
	
}
function viewTaskDetails(valTaskId)
{
	var myForm=document.getElementById('planForm');


	var_projectId=myForm.projectId.value;
	var_projectWorkflowId=myForm.projectWorkflowId.value;
	var_taskId=valTaskId;
	//windowURL="<c:out value='${sessionScope.MenuContextPath}' />/attachEditProjectPlan.do?method=viewTask&projectId="+var_projectId+"&projectWorkflowId="+var_projectWorkflowId+"&selectedTaskId="+var_taskId;

	//windowDefault='<%=Messages.getMessageValue("windowDefault")%>';
	da=new Date();
	//window.open(windowURL,"Task"+da.getTime(),windowDefault);
	
	
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
	showLayer();
	myform.method.value='attachedProjectPlanHome';
	myform.submit();
}

function pagingSubmit(val)
{
	myform=document.getElementById('planForm');
	myform.elements["tasksPS.pageNumber"].value=val;
	
	myform.method.value='attachedProjectPlanHome';	
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
	myform.method.value='attachedProjectPlanHome';	
	myform.submit();
}
function forAgainFinalizationFailure()
{
	var msg="Actions on following tasks have been taken by user after the draft process started \n  and hence they reverted and updated:";
	<logic:present name="finalizingAgainMessage">
		<logic:iterate id="task" name="allRevertedTasks">
//			msg=msg+"\n"+"Task Name :"+"<bean:write name="task" property="taskname"/>"+"  Stage Name:"+"<bean:write name="task" property="stagename"/>";
		</logic:iterate>
		msg=msg+"\n"+"Tasks Closed :";
		<logic:empty name="closedList">
			msg=msg+"\n"+"	-No Tasks Reverted";			
		</logic:empty>
		<logic:iterate id="task" name="closedList">
			msg=msg+"\n"+"	-Task Name :"+"<bean:write name="task" property="taskname"/>"+"  Stage Name:"+"<bean:write name="task" property="stagename"/>";
		</logic:iterate>
		msg=msg+"\n"+"Following Tasks were Open with Action Taken by user";
		<logic:empty name="openActionTakenList">
			msg=msg+"\n"+"	-No Tasks Reverted";			
		</logic:empty>
		<logic:iterate id="task" name="openActionTakenList">
			msg=msg+"\n"+"	-Task Name :"+"<bean:write name="task" property="taskname"/>"+"  Stage Name:"+"<bean:write name="task" property="stagename"/>";
		</logic:iterate>
		msg=msg+"\n"+"Following Tasks were Open with No Action Taken By user";
		<logic:empty name="openNoActionList">
			msg=msg+"\n"+"	-No Tasks Reverted";			
		</logic:empty>
		<logic:iterate id="task" name="openNoActionList">
			msg=msg+"\n"+"	-Task Name :"+"<bean:write name="task" property="taskname"/>"+"  Stage Name:"+"<bean:write name="task" property="stagename"/>";
		</logic:iterate>		
		msg=msg+"\n"+"Verify the plan and click on finalize";
		alert(msg);
	</logic:present>
}
function fnOnBodyLoad()
{
	document.body.click();
	<logic:present name="finalizingAgainMessage">
		forAgainFinalizationFailure();
	</logic:present>
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
<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">
				<td><img src="gifs/top/home.gif" title="Home" onClick="goToHome('Home');"></img></td>
			</tr>
</table>
<html:form action="/attachEditProjectPlan" styleId="planForm">
<bean:define id="formBean" name="projectPlanInstanceBean"></bean:define>
<input type="hidden" name="method" />
<html:hidden property="projectId"/>
<html:hidden property="projectWorkflowId"/>
<html:hidden property="attachMode"/>

<html:hidden property="tasksPS.sortByColumn"/>
<html:hidden property="tasksPS.sortByOrder"/>
<html:hidden property="tasksPS.pageNumber"/>
<div class="head"> <span>Attach Edit Project Workflow</span> </div>
<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border" align="center">

<tr>
	<td>
		<table width="100%" border="0" align="center" >
						
			<tr valign="middle" id="newProduct">
				<td valign="bottom" width="100%" class="tabledata"  align="center">
					<bean:define id="projectBean" name="formBean" property="project"></bean:define>
					<%request.setAttribute("projectBean",projectBean); %>
					<jsp:include flush="true" page="../commons/projectDetailHeader.jsp">
					</jsp:include>
				</td>
			</tr>		
		</table>
	</td>
</tr>
<tr>
	<td>
		<table width="40%" border="0" class="tab2" cellpadding="3"
							cellspacing="1" align="center">
			<tr class="lightBg">
				<td  width="100%" align="left" colspan="2">
					Checklist
					<html:hidden property="totalTasks"/>
				</td>
			</tr>
			<tr class="normal">
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
			<tr class="lightBg">
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
			<tr class="normal">
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
<tr>
</tr>
<tr>
	<td>
		<table width="80%" border="0" align="center" cellpadding="5" >
						<tr>
							<logic:equal name="formBean" property="attachMode" value="editingFinalized">
								<td align="center" nowrap><span id="search" class="buttonLarge"
									onClick="planActions('discardDraft');">Discard Draft<span></td>
							</logic:equal>
							<td align="center" nowrap><span id="search" class="buttonLarge"
								onClick="planActions('draft');">Draft &amp; Close<span></td>
							<td align="center" nowrap><span id="search" class="buttonLarge"
								onClick="planActions('finalize');">Finalize<span></td>								
							
							<td align="center" nowrap><span class="buttonLarge" onClick="planActions('viewEditTasks');">View/Edit Tasks</span></td>
							<!-- <td align="center" nowrap><span id="search" class="buttonLagre"
								onClick="planActions('viewEditTasks');">View/Edit Tasks<span></td>-->
						</tr>						
					</table>
	</td>
</tr>
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
			<table width="80%" border="0" class="tab2" cellpadding="3"
							cellspacing="1" align="center">
				<tr>
					<td colspan="7">
					<table width="100%" border="0" class="tab2" cellpadding="3"
						cellspacing="1" align="center">
						<tr width="100%" class="grayBg">
							<!--<th align="center" width="53" colspan="3">Tasks Details</th>-->
							<td align="center" width="851"><b>Tasks Details</b>
							<table>
								<tr class="normal">
									<td><html:select property="taskOption"
										onchange="showTasks()" styleClass="inputBorder1" style="display:none">
										<html:option value="ALL">All</html:option>
										<html:option value="NOPREVLIST">No Previous Defined</html:option>
										<html:option value="FIRST">First</html:option>
										<html:option value="LAST">Last</html:option>
										<html:option value="NOUSERATTACHED">No User Assigned</html:option>
									</html:select></td>									
								</tr>
							</table>
							</td>
							<!--<td align="right" width="45"><a href="#"><img
								onclick="searchSubmit();" src="npd/Images/search.gif" title="search"
								height="15"></a>&nbsp;</td>-->
						</tr>
					</table>
					</td>

				</tr>
				<tr>
					<td colspan="7"><bean:define id="pagingBean" name="formBean"></bean:define>
					<%String currPageNumber =String.valueOf(((ProjectPlanInstanceBean)formBean).getTasksPS().getPageNumber());
							String 	maxPageNumber =String.valueOf(((ProjectPlanInstanceBean)formBean).getTasksPS().getMaxPageNumber());
							%> <jsp:include flush="true" page="./../commons/paging.jsp">
						<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
						<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
					</jsp:include></td>
				</tr>	
				<tr class="normal">

					<th width="4%" >S.No.</th>		
					<th width="5%" ><a href="#" onclick="sortSubmit('taskName')">Task Name</a></th>					
					<th width="5%" ><a href="#" onclick="sortSubmit('roleHolder')">Role Holder</a></th>
					
							
				</tr>
				<tr class="lightBg">
					
					<th width="4%" >&nbsp;</th>		
					<th width="5%" ><html:text property="searchTaskName" maxlength="20" styleId="id_taskName_search" style="display:none"/></th>					
					<th width="5%" ><html:text property="searchTask_roleHolder" maxlength="20" styleId="id_roleHolder_search" style="display:none" /></th>
							
				</tr>
				
				<logic:empty name="formBean" property="tasksView">
					<tr class="lightBg">
						<td colspan="8" align="center">No Tasks Present</td>
					</tr>
				</logic:empty>
				<logic:notEmpty name="formBean" property="tasksView">
				<%int taskSno= 1+(((ProjectPlanInstanceBean)formBean).getTasksPS().getPageNumber()-1)*
										((ProjectPlanInstanceBean)formBean).getTasksPS().getPageRecords();
				String []colors=new String[]{"normal","lightBg"}; %>
				<logic:iterate id="taskRow"	name="formBean" property="tasksView" indexId="index_task">
						<tr class="<%=colors[index_task.intValue()%2]%>">
							
							<td align="center">
								<%=taskSno++%>
							</td>

							<td align="center"><font color="brown"><a href="#" onclick="viewTaskDetails(<bean:write name="taskRow" property="currenttaskid"/>)">
								<bean:write name="taskRow" property="taskname"/></a>
								</font>
							</td>
							<td align="center"><bean:write name="taskRow" property="taskstakeholderName"/></td>																									
						</tr>
				</logic:iterate>
						<tr><td>
							<input type="hidden" name="taskStatus<bean:write name="taskRow" property="currenttaskid"/>" 
											value="<bean:write name="taskRow" property="taskstatus"/>" 
											id="id_taskStatus<bean:write name="taskRow" property="currenttaskid"/>">
						</td></tr>
				</logic:notEmpty>
			</table>
		</td>
	</tr>
</table>
</html:form>
</div>
</BODY>


</html:html>
