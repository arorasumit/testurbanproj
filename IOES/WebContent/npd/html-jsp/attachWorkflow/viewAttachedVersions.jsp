<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="com.ibm.ioes.npd.utilities.Messages"%>
<%@page import="com.ibm.ioes.npd.beans.ProjectPlanInstanceBean"%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy"%>
<%@page import="com.ibm.ioes.npd.hibernate.beans.TtrnProjectworkflow"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.npd.utilities.Utility"%>
<%@page import="java.sql.Date"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">

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
function show(tbl,btn)
{
	if (btn.value=="-")
	{
		document.all.item(tbl).style.display="None";
		btn.value="+";
	}
	else
	{
		document.all.item(tbl).style.display="Inline";
		btn.value="-";
	}
}


</script>

</head>
<body onload="document.body.click();">
<div id="menu_abc" style="display: block;">
<html:form action="/attachEditProjectPlan">
<bean:define id="formBean" name="projectPlanInstanceBean"></bean:define>
<div class="Head"><span>Attached Project Workflow Versions</span></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border" align="center">
<tr valign="middle" id="newProduct">
	<td valign="bottom" width="100%" align="center">
		<!--<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Attached Project Workflow Versions</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1">
				</td>
			</tr>
	</table>-->
</tr>
<tr>
	<td>
		<table width="100%" border="0" class="tab2" cellpadding="3"
							cellspacing="1" align="center">
			<logic:present name="formBean" property="versionWorkflowList">
			<logic:notEmpty name="formBean" property="versionWorkflowList">
				<logic:iterate id="workflow" name="formBean" property="versionWorkflowList" indexId="count">
				<%
							String classType=null;
							if(count.intValue() % 2 == 0)
							{
								classType="normal";
							}
							else
							{
								classType="lightBg";				
							}											
				%>	
					<tr class="<%=classType%>">
						<td><input name="btn1"
										onClick="show('tbl<%=count %>',this)" type="button" value="+"
										style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;&nbsp;
						
						<logic:equal name="workflow" property="isactive" value="2">
							(In Draft)
						</logic:equal>
						<logic:equal name="workflow" property="isactive" value="0">
							Version <%= count.intValue()+1%>.0(Previous Version)
						</logic:equal>
						<logic:equal name="workflow" property="isactive" value="1">
							Version <%= count.intValue()+1%>.0(Current Version)
						</logic:equal>												
						</td>
					</tr>
					<tr>
						<td>
							<table width="100%" border="0" class="tab2" cellpadding="3" id="tbl<%=count%>"
								cellspacing="1" align="center" style="display:none ">
								<tr class="lightBgWihtoutHover">
									<!--<td>S.No</td>-->
									<td>Stage Name</td>
									<!--  <td>Task Name</td>-->
									<!--  <td>Task Description</td>-->
									<td>Is Mandatory</td>
									<td>Rejection Allowed</td>									
									<!--  <td>Stake Holder</td>-->
									<td>Assigned To</td>
									<!--  <td>Is First Task</td>-->
									<!--  <td>Is Last Task</td>-->
									<!--<td>Next Tasks</td>-->
									<td>Planned Duration</td>
									<!--<td>Document</td>-->
									<!--<td>Remarks</td>-->
									<td>Target Start</td>
									<td>Target End</td>									
									<td>Actual Start</td>
									<td>Actual End</td>				
									<td>Action Taken</td>
								</tr>
								<%
								//HashMap< Long, ArrayList<TtrnProjecthierarchy>> versionWorkflowsData=((ProjectPlanInstanceBean)formBean).getVersionWorkflowsData();
								//ArrayList<TtrnProjecthierarchy> taskList=versionWorkflowsData.get(((TtrnProjectworkflow)workflow).getProjectworkflowid());
								HashMap versionWorkflowsData=((ProjectPlanInstanceBean)formBean).getVersionWorkflowsData();
								ArrayList taskList=(ArrayList)versionWorkflowsData.get((Object)new Long(((TtrnProjectworkflow)workflow).getProjectworkflowid()));
								
								request.setAttribute("taskList",taskList);
								 %>
								 <%String []colors=new String[]{"normal","lightBg"}; %>
								<logic:iterate id="task" name="taskList" indexId="index_task">
								<tr class="<%=colors[index_task.intValue()%2]%>">
									<!--<td align="center"><%=index_task.intValue()+1%></td>-->
									<td align="center"><bean:write name="task" property="stagename"/></td>
									<!--<td align="center"><bean:write name="task" property="taskname"/></td>-->
									<!--  <td align="center"><bean:write name="task" property="taskdesc"/></td>-->

									<logic:equal name="task" property="taskTasktype" value="1">
									<td align="center">Y</td>									
									</logic:equal>
									<logic:equal name="task" property="taskTasktype" value="0">
									<td align="center">&nbsp;</td>									
									</logic:equal>
									
									<td align="center">
									<logic:equal name="task" property="rejectionAllowed" value="1">
									Y									
									</logic:equal>
									<logic:equal name="task" property="rejectionAllowed" value="0">
									&nbsp;
									</logic:equal>
									</td>
									
									<td align="center"><bean:write name="task" property="taskstakeholderName"/></td>
									<td align="center"><bean:write name="task" property="assignedtouserName"/></td>

									<logic:equal name="task" property="isfirsttask" value="1">
									<!-- <td align="center">Y</td>-->
									</logic:equal>
									<logic:equal name="task" property="isfirsttask" value="0">
									<!--  <td align="center">&nbsp;</td>-->
									</logic:equal>									

									<logic:equal name="task" property="islasttask" value="1">
									<!--  <td align="center">Y</td>-->
									</logic:equal>
									<logic:equal name="task" property="islasttask" value="0">
									<!--<td align="center">&nbsp;</td>-->								
									</logic:equal>									



									<!--<td align="center"><bean:write name="task" property="CSV_nextTasksName"/></td>-->
									<!--<td align="center"><bean:write name="task" property="taskduration"/></td>-->
									<!--<td align="center"><bean:write name="task" property="taskReferencedocname"/></td>-->																																																																																										
									<!--<td align="center"><bean:write name="task" property="taskTaskinstructionremarks"/></td>-->
									<td align="center">&nbsp;
										<logic:present name="task" property="taskstartdate">
											<bean:define id="id_taskstartdate" name="task" property="taskstartdate"/>
											<%=Utility.showDate_Report((Date)id_taskstartdate) %>
										</logic:present>
									</td>
									<td align="center">&nbsp;
										<logic:present name="task" property="taskenddate">
											<bean:define id="id_taskenddate" name="task" property="taskenddate"/>
											<%=Utility.showDate_Report((Date)id_taskenddate) %>
										</logic:present>
										
									</td>
									<td align="center">&nbsp;
										<logic:present name="task" property="actualtaskstartdate">
											<bean:define id="id_actualtaskstartdate" name="task" property="actualtaskstartdate"/>
											<%=Utility.showDate_Report((Date)id_actualtaskstartdate) %>										
										</logic:present>
									</td>
									<td align="center">&nbsp;
										<logic:present name="task" property="actualtaskenddate">									
											<bean:define id="id_actualtaskenddate" name="task" property="actualtaskenddate"/>
											<%=Utility.showDate_Report((Date)id_actualtaskenddate) %>	
										</logic:present>	
									</td>
									<td align="center">
									<logic:equal name="task" property="taskstatus" value="2">
									Closed
									</logic:equal>
									<logic:notEqual name="task" property="taskstatus" value="0">
									&nbsp;									
									</logic:notEqual>	</td>

								</tr>
								</logic:iterate>
							</table>
						</td>
					</tr>
					
				</logic:iterate>			
			</logic:notEmpty>
			</logic:present>
		</table>
	</td>
</tr>
</table>
</html:form>
</div>
</BODY>


</html:html>
