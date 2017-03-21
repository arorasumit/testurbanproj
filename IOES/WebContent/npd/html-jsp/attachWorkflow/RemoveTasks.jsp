<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.beans.ProjectPlanInstanceBean;"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>
<script language="javascript" src="npd/js/utility.js"></script>
<link rel="stylesheet" href="npd/theme/Master.css" type="text/css">
<title>RemoveTasks</title>
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
<script type="text/javascript">
function fnsearchTaksDetails()
{
	var myForm=document.getElementById('searchForm');
	myForm.method.value='removeTask';
	myForm.action="<%=request.getContextPath()%>/attachEditProjectPlan.do?method=removeTask";
	myForm.elements["tasksPS.pageNumber"].value=1;
	showLayer(); 
	myForm.submit();
}

/*function getcheckedTaskId(chk,id)
{
	if(chk)
	{
		document.projectPlanInstanceBean.deleteTaskId.value = document.projectPlanInstanceBean.deleteTaskId.value+'&'+id;
	}
	else
	{
		document.projectPlanInstanceBean.noDeleteTaskId.value = document.projectPlanInstanceBean.noDeleteTaskId.value+'&'+id;		
	}	
}*/
function fnCheckAll()
{
	ids=document.forms[0].removeSelectedTaskId;
		if(ids==null)
		{	
			alert('No Task Present.');
			document.getElementById('chkHead').checked=false;
			return false;
		}

	var i;
	if(document.getElementById("chkHead").checked==true)
	{	
		if(document.forms[0].removeSelectedTaskId.length!=null)
		{	for(i=0;i<document.forms[0].removeSelectedTaskId.length;i++ )
			{
				if(document.forms[0].removeSelectedTaskId[i].disabled==false)
				{
					document.forms[0].removeSelectedTaskId[i].checked=true;
					//getcheckedTaskId(document.forms[0].removeSelectedTaskId[i].checked,document.forms[0].removeSelectedTaskId[i].value);
				}
			}
		}
		else
		{
			if(document.forms[0].removeSelectedTaskId.disabled==false)
			{
				document.forms[0].removeSelectedTaskId.checked=true;
				//getcheckedTaskId(document.forms[0].removeSelectedTaskId[i].checked,document.forms[0].removeSelectedTaskId[i].value);
			}
		}
	}
	else
	{
		if(document.forms[0].removeSelectedTaskId.length!=null)
		{
			for(i=0;i<document.forms[0].removeSelectedTaskId.length;i++ )
			{
				document.forms[0].removeSelectedTaskId[i].checked=false;
				//getcheckedTaskId(document.forms[0].removeSelectedTaskId[i].checked,document.forms[0].removeSelectedTaskId[i].value);
			}
		}
		else
		{
			document.forms[0].removeSelectedTaskId.checked=false;
			//getcheckedTaskId(document.forms[0].removeSelectedTaskId[i].checked,document.forms[0].removeSelectedTaskId[i].value);
		}	

	}

}
function removeCheckHead()
{
	javascript:document.getElementById('chkHead').checked=false;
}

function submitForm()
{
	var myForm=document.getElementById('searchForm');
	myForm.method.value='removeSelectedTask';
	myForm.action="/IOES/attachEditProjectPlan.do?method=removeSelectedTask";
	showLayer(); 
	myForm.submit()
}

function wkHome()
{
	var myPlanForm=document.getElementById('searchForm');
	
	myform=document.createElement("form");
	document.body.appendChild(myform);
	myform.action="<%=request.getContextPath()%>/attachEditProjectPlan.do";
	

	myform.appendChild(getHiddenField("method","viewEditTasks"));
	myform.appendChild(getHiddenField("projectId",myPlanForm.projectId.value));		
	myform.appendChild(getHiddenField("projectWorkflowId",myPlanForm.projectWorkflowId.value));				
	myform.appendChild(getHiddenField("attachMode",myPlanForm.attachMode.value));	
	
	showLayer(); 
	myform.submit();
}

function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	myform.elements["tasksPS.pageNumber"].value=val;
	myform.action="<%=request.getContextPath()%>/attachEditProjectPlan.do?method=removeTask";
	showLayer();
	myform.submit();
}
</script>
</head>

<body onload="javascript:document.body.click();">
<div id="shadow" class="opaqueLayer"> 
		
</div>
<div class="Head"><span>Task Removal</span></div>
<logic:messagesPresent message="true">
	<table width="50%" align="center" id='messageBody'>
		<tr>
			<td>&nbsp;</td>
		</tr>
		<tr>
			<td><font color="red" face="Verdana" size="2"><html:messages id="message" message="true">
				<li><bean:write name="message" /></li>
				</html:messages></font>
			</td>
		</tr>
		<tr>
			<td>&nbsp;</td>
		</tr>
	</table>
</logic:messagesPresent>
<html:form action="/attachEditProjectPlan" styleId="searchForm" method="post" >
	<input type="hidden" name="method"/>
	<bean:define id="formBean" name="projectPlanInstanceBean"></bean:define>
	<html:hidden property="projectId"/>	
	<html:hidden property="projectWorkflowId"/>
	<html:hidden property="attachMode"/>
	<html:hidden property="deleteTaskId" value=""/>
	<html:hidden property="noDeleteTaskId" value=""/>
	<html:hidden property="tasksPS.pageNumber"/>
	<!--<table width="80%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="borderTab">
					<tr>
						<td><img src="npd/Images/tabLeft.gif"></td>
						<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Task Removal</td>
						<td><img src="npd/Images/tabRight.gif"></td>
						<td width="70%"><img src="npd/Images/zero.gif" width="15" height="1"></td>
						<td align="right" style="padding-right:10px;" width="20%"><a href="#"><img onclick="javascript:fnsearchTaksDetails();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>-->
	<logic:present name="projectPlanInstanceBean" property="tasksView">
		<table width="80%" border="2" class="tab2" cellpadding="0" cellspacing="0" align="center">
			<tr>
				<td height="316">
					<div style="overflow:scroll;height:370px;width:100%" class="scroll">
						<table align="center" cellSpacing="1" cellPadding="4" border="0" class="scroll" id="tblRollList" width="100%">
							<tr valign="middle" class="lightBgWihtoutHover">	
								<td width="100%"  colspan="7" align="center"> 
									<strong style="color: black">
										Project ID: <bean:write name="projectPlanInstanceBean" property="projectId"/>&nbsp;
										Product Name: <bean:write name="projectPlanInstanceBean" property="project.projectName"/>
									</strong>
								</td>
							</tr>
							<tr>
								<td colspan="7">
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
								<td align="left" colspan="4">
								<input id="chkHead" type="checkbox" class="none" onClick="javascript:fnCheckAll();">	
								&nbsp;<b>Select All Task</b></td>
								<!--<html:text property="searchTaskName" maxlength="20" styleId="id_taskName_search" styleClass="inputBorder1" style="display:none"/>-->																													
							</tr>
							<tr valign="middle" class="grayBg">	
								<td width="5%" style="color: black" align="center"><strong>Select</strong></td>
								<td width="5%" style="color: black" align="center"><strong>S.No.</strong></td>
								<td width="40%" style="color: black" align="center"><strong>Task Name</strong></td>								
								<td width="20%" style="color: black" align="center"><strong>Role Holder</strong></td>								
							</tr>							
							<logic:notEmpty name="projectPlanInstanceBean" property="tasksView">
								<%int sno= 1+(((ProjectPlanInstanceBean)formBean).getTasksPS().getPageNumber()-1)*
										((ProjectPlanInstanceBean)formBean).getTasksPS().getPageRecords();%>
								<logic:iterate id="assumptionDetailList_id" name="projectPlanInstanceBean" property="tasksView" indexId="index1">
									<%if((index1.intValue())%2==0) {%>
										<tr class="normal" align="center">
									<% }else{ %>
										<tr class="lightBg" align="center">
									<% } %>
											<td width="5%">
											<%String captionLabel=""; %>
												<logic:equal name="assumptionDetailList_id" property="currentTaskStatus" value="CLOSED">
													<input type="checkbox" name="removeSelectedTaskId" value='<bean:write name="assumptionDetailList_id" property="currenttaskid"/>' disabled="disabled">
													<%captionLabel="(Closed)" ;%>
												</logic:equal>
												<logic:equal name="assumptionDetailList_id" property="currentTaskStatus" value="OPEN_ACTIONTAKEN">
													<input type="checkbox" name="removeSelectedTaskId" value='<bean:write name="assumptionDetailList_id" property="currenttaskid"/>' disabled="disabled">
													<%captionLabel="(Open- Action Taken)" ;%>
												</logic:equal>
												<logic:equal name="assumptionDetailList_id" property="currentTaskStatus" value="OPEN_NOACTION">
													<logic:equal name="assumptionDetailList_id" property="isfirsttask"  value="1">
														<input type="checkbox" name="removeSelectedTaskId" value='<bean:write name="assumptionDetailList_id" property="currenttaskid"/>' disabled="disabled">
														<%captionLabel="(Open- No Action Taken)" ;%>
													</logic:equal>
													<logic:notEqual name="assumptionDetailList_id" property="isfirsttask"  value="1">
														<input type="checkbox" name="removeSelectedTaskId" value='<bean:write name="assumptionDetailList_id" property="currenttaskid"/>' onclick="javascript: removeCheckHead();")">
														<%captionLabel="(Open- No Action Taken)" ;%>
													</logic:notEqual>
												</logic:equal>
												<logic:equal name="assumptionDetailList_id" property="currentTaskStatus" value="NOTSTARTED">
													<input type="checkbox" name="removeSelectedTaskId" value='<bean:write name="assumptionDetailList_id" property="currenttaskid"/>' onclick="javascript: removeCheckHead();")">
												</logic:equal>
											</td>
											<td width="5%"><%=sno++%></td>
											<td width="40%"><bean:write name='assumptionDetailList_id' property='taskname' />
														<font color="red"><%=captionLabel %></font>
														<logic:equal name="assumptionDetailList_id" property="isfirsttask"  value="1">
															<font color="red">(First)</font>
														</logic:equal>
														<logic:equal name="assumptionDetailList_id" property="islasttask"  value="1">
															<font color="red">(Last)</font>
														</logic:equal>
											</td>
										
											<td width="20%"><bean:write name='assumptionDetailList_id' property='taskstakeholderName' /></td>
											
											
										</tr>
								</logic:iterate>
							</logic:notEmpty>	
							
						</table>
					</div>
				</td>
			</tr>
		</table>		
	</logic:present>
	<table width="80%" border="0" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td colspan="1" align="right" width="50%">
				<div class="buttonLarge" onClick="javascript:submitForm();">Remove</div>
			</td>
			<td colspan="2" align="left" width="50%">
				<div class="buttonLarge" onClick="javascript:wkHome();">Workflow Home</div>
			</td>
		</tr>
	</table>
</html:form>
</body>

