<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="../../ErrorPage.jsp"%>

<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.beans.ProjectPlanInstanceBean"%>
<%@page import="com.ibm.ioes.npd.hibernate.beans.TmstProjecthierarchytasksflow"%>
<%@page import="com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy"%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>
<script language="javascript" src="npd/js/utility.js"></script>
<link rel="stylesheet" href="/theme/Master.css" type="text/css">
<title>Previous Task</title>
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
	myForm.method.value='prevTask';
	myForm.action="<%=request.getContextPath()%>/attachEditProjectPlan.do?method=prevTask";
	myForm.elements["tasksPS.pageNumber"].value=1;
	showLayer(); 
	myForm.submit()
}
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

function submitForm()
{
	counter=0;
	for(x=0;x<document.getElementById("id_prevtaskid").length;x++)
	{
			counter++;
	}
	if(counter<1)
	{
		alert("Please select a Previous Task");
		return false;
	}
	
	var myForm=document.getElementById('searchForm');
	myForm.method.value='updatePrevTask';
	myForm.action="<%=request.getContextPath()%>/attachEditProjectPlan.do?method=updatePrevTask";
	selectAllSelected();
	showLayer(); 
	myForm.submit();
}

function submitShow()
{
	var appContextPath = '<%=request.getContextPath()%>';
	
	var indexes=document.forms[0].prevPageSelectedTaskId;
	var tasks=new Array();
	if(indexes==null)
	{
		alert('No Task Present');
		return;
	}
	else if(indexes.length==null)
	{
		
		if(indexes.checked==true)
		{
			tasks[0]=document.forms[0].elements["updatePrevTaskRow["+indexes.value+"].currenttaskid"].value;      
		}
	}
	else
	{
		j=0;
		for(i=0;i<indexes.length;i++)
		{
			if(indexes[i].checked==true)
			{
				nam="updatePrevTaskRow["+indexes[i].value+"].currenttaskid";
				
				
				tasks[j]=document.forms[0].elements[nam].value;        
				j=j+1;
			}
		}
	}

	if(tasks.length==0)
	{
		alert('Please Select a Task.');
		return;
	}
	
	try
	{	
		var obj = document.forms[0].unselected_NewPreviousIds;
	
		jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
   		var result;
   		result = jsonrpc.processes.getPrevTaskList(document.forms[0].projectId.value,document.forms[0].projectWorkflowId.value,tasks);
   	
		for(i=obj.options.length-1;i>=0 ;i--)
		{
			obj.options[i] = null;
		}
		
		if( result !=null && result.list.length > 0)
		{
			for(i=0;i < result.list.length;i++)
			{
			var name = result.list[i].taskname;//
			var id=result.list[i].currenttaskid;//
			obj.options[i] = new Option(name,id);
			
			}//for
		}//if result
		
		//clear old entries in right select
		var objRight = document.forms[0].select_NewPreviousIds;
		for(i=objRight.options.length-1;i>=0 ;i--)
		{
			objRight.options[i] = null;
		}
		
		document.getElementById('displayPrevious').style.display='block';
		document.getElementById('updatebtn').style.display='block';
	}
	catch(e) {
		//alert(e);
		return false;
	}
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

function fnEnable()
{
	document.getElementById('displayPrevious').style.display='none';
	document.getElementById('updatebtn').style.display='none';
}

function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	myform.elements["tasksPS.pageNumber"].value=val;
	myform.action="<%=request.getContextPath()%>/attachEditProjectPlan.do?method=prevTask";
	showLayer();
	myform.submit();
}

function addOption1()
{
	myform=document.getElementById('searchForm');

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
	myform=document.getElementById('searchForm');
	
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
</script>
</head>

<body onload="javascript:document.body.click();">
<div id="shadow" class="opaqueLayer"> 
		<br /><br /><br /><br /><br /><br /><br /><br />
	    <font color="#FFFFFF" size="9"><b>Please Wait While Data Uploads..</b></font>
	    <br /><br /><br />
</div>
<div class="head"> <span>Modify Previous Task</span> </div>
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
	<html:hidden property="tasksPS.pageNumber"/>
	<!--<table width="80%" border="0" cellpadding="0" cellspacing="0" align="center">
		<tr id="newProduct">
			<td width="100%" align="center" v>
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="borderTab">
					<tr>
						<td><img src="npd/Images/tabLeft.gif"></td>
						<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Update Previous Task</td>
						<td><img src="npd/Images/tabRight.gif"></td>
						<td width="70%"><img src="npd/Images/zero.gif" width="15" height="1"></td>
						<td align="right" style="padding-right:10px;" width="20%"><a href="#"><img onclick="javascript:fnsearchTaksDetails();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>-->
	<logic:present name="projectPlanInstanceBean" property="tasksView">
		<div class="scroll">
		<table width="80%" border="2" class="tab2" cellpadding="0" cellspacing="0" align="center">
			<tr>
				<td valign="top">
					
						<table align="center" cellSpacing="1" cellPadding="4" border="0" class="scroll" id="tblRollList" width="100%">
							<tr valign="middle" class="lightBgWihtoutHover">	
								<td width="100%"  colspan="8" align="center"> 
									<strong style="color: black">
										Project ID: <bean:write name="projectPlanInstanceBean" property="projectId"/>&nbsp;
										Product Name: <bean:write name="projectPlanInstanceBean" property="project.projectName"/>
									</strong>
								</td>
							</tr>
							<tr>
								<td colspan="8">
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
							<tr valign="middle" class="grayBg">	
								<td><strong>Select</strong></td>
								<td><strong>S.No.</strong></td>
								<td><strong>Task Name</strong></td>
								<td><strong>Stage Name</strong></td>
								<td><strong>Previous Task Name</strong></td>
								<td><strong>Role Holder</strong></td>
								<!--<td><strong>Assigned To</strong></td>-->
								<td><strong>Duration</strong></td>
							</tr>
							<tr class="lightBg">								
								<td  align="center" colspan="3">
								<html:text property="searchTaskName" maxlength="20" styleClass="inputBorder1" styleId="id_taskName_search" style="display:none"/></td>
								<td  align="center">
									<html:select property="selectedStageId" styleClass="inputBorder1">
										<html:option value="-1">-All Stages-</html:option>
										<logic:notEmpty name="projectPlanInstanceBean" property="stageList">
											<html:optionsCollection property="stageList" label="stagename" value="currentstageid"/>
										</logic:notEmpty>
									</html:select>
								</td>
								<td colspan="3">&nbsp;</td>												
							</tr>
							<logic:notEmpty name="projectPlanInstanceBean" property="tasksView">
								<%int sno= 1+(((ProjectPlanInstanceBean)formBean).getTasksPS().getPageNumber()-1)*
										((ProjectPlanInstanceBean)formBean).getTasksPS().getPageRecords();%>
								<logic:iterate id="updatePrevTaskRow" name="projectPlanInstanceBean" property="tasksView" indexId="index1">
									<%if((index1.intValue())%2==0) {%>
										<tr class="normal" align="center">
									<% }else{ %>
										<tr class="lightBg" align="center">
									<% } %>
											<td width="5%">
												<%String captionLabel=""; %>
												<logic:equal name="updatePrevTaskRow" property="currentTaskStatus" value="CLOSED">
													<input type="checkbox" name="prevPageSelectedTaskId" value="<%=index1 %>" disabled="disabled">
													<%captionLabel="(Closed)" ;%>
												</logic:equal>
												<logic:equal name="updatePrevTaskRow" property="currentTaskStatus" value="OPEN_ACTIONTAKEN">
													<input type="checkbox" name="prevPageSelectedTaskId" value="<%=index1 %>" disabled="disabled">
													<%captionLabel="(Open- Action Taken)" ;%>
												</logic:equal>
												<logic:equal name="updatePrevTaskRow" property="currentTaskStatus" value="OPEN_NOACTION">
													<input type="checkbox" name="prevPageSelectedTaskId" value="<%=index1 %>" disabled="disabled">
													<%captionLabel="(Open- No Action Taken)" ;%>
												</logic:equal>
												<logic:equal name="updatePrevTaskRow" property="currentTaskStatus" value="NOTSTARTED">
													<logic:equal name="updatePrevTaskRow" property="isfirsttask"  value="1">
														<input type="checkbox" name="prevPageSelectedTaskId" value="<%=index1 %>" disabled="disabled">
														
													</logic:equal>
													<logic:notEqual name="updatePrevTaskRow" property="isfirsttask"  value="1">
														<input type="checkbox" name="prevPageSelectedTaskId" value="<%=index1 %>" onclick="javascript:fnEnable();"><!-- onClick="javascript:fnShowFields(this,<%=sno%>);"> -->
													</logic:notEqual>
												</logic:equal>
												
												<html:hidden name="updatePrevTaskRow" property="currenttaskid" indexed="true"/>
											</td>
											<td width="5%"><%=sno++%></td>
											<td width="25%"><bean:write name='updatePrevTaskRow' property='taskname' />
												<font color="red"><%=captionLabel %></font>
												<logic:equal name="updatePrevTaskRow" property="isfirsttask"  value="1">
													<font color="red">(First)</font>
												</logic:equal>
												<logic:equal name="updatePrevTaskRow" property="islasttask"  value="1">
													<font color="red">(Last)</font>
												</logic:equal>												
											</td>
											<td width="10%"><bean:write name='updatePrevTaskRow' property='stagename' /></td>
											<td width="27%">
												<% String csvPrevTaskName=""; %>
												
												<logic:iterate id="prevTask" name="updatePrevTaskRow" property="prevTaskList" >
													<% csvPrevTaskName=csvPrevTaskName+","+((TmstProjecthierarchytasksflow)prevTask).getPrevTaskName(); %>
												</logic:iterate>
													<% if(csvPrevTaskName.length()>0) csvPrevTaskName=csvPrevTaskName.substring(1);%>
											
												<%= csvPrevTaskName%>
											</td>
											<td width="10%"><bean:write name='updatePrevTaskRow' property='taskstakeholderName' /></td>
											<!--<td width="13%"><bean:write name='updatePrevTaskRow' property='assignedtouserName' /></td>-->
											<td width="5%"><bean:write name='updatePrevTaskRow' property='taskduration' /></td>
										</tr>
										
								</logic:iterate>
								<tr id="displayPrevious" style="display:none">
									<td colspan="11" align="right" valign="middle" >
										<table border="0" cellpadding="0" cellspacing="0" width="100%" align="right">
											<tr class="Filter"> 
												<td width="40%" align="center" >
													<select name="unselected_NewPreviousIds" multiple="multiple" size="10" class="inputBorder1" id="id_unSelectedPrevtaskid"
														style="width:100%">
													</select>
												</td>
												<td width="5%" align="center" style="vertical-align: middle">
													<input type="button" value="&gt;&gt;&gt;" onclick="addOption1();" id="id_buttonShift1"><br><br>
													<input type="button" value="&lt;&lt;&lt;" onclick="removeOption1();" id="id_buttonShift2">
												</td>
												<td width="40%">
													<select name="select_NewPreviousIds" multiple="multiple" size="10" class="inputBorder1" id="id_prevtaskid"
														style="width:100%">
													</select>
												</td>
											</tr>
										</table>
									</td>
								</tr>
							</logic:notEmpty>	
						</table>
					
				</td>
			</tr>
		</table>	
		</div>	
	</logic:present>
	
	<table width="80%" border="0" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td colspan="1" align="right" width="25%">
				<div class="buttonLarge" onClick="javascript:submitShow();">Show</div>
			</td>
			<td colspan="1" align="center" width="25%" style="display:none" id="updatebtn">
				<div class="buttonLarge" onClick="javascript:submitForm();">Update</div>
			</td>
			<td colspan="1" align="center" width="25%">
				<div class="buttonLarge" onClick="javascript:wkHome();">Workflow Home</div>
			</td>
			<td colspan="1" align="left" width="25%">
			
			</td>
		</tr>
	</table>
</html:form>
</body>

