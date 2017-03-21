<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.beans.ProjectPlanReportBean"%>
<%@page import="com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto"%>
<html:html>
<head>
<link rel="stylesheet" type="text/css" href="gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">

<script language="JavaScript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/chrome.js"></script>

<script type="text/javascript" src="js/timepicker.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>
<script type="text/javascript" src="js/inputColor.js"></script>
<script language="javascript" src="js/utility.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>

<%@include file="js/scwblank.html"%>


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

<script type="text/javascript" src="js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<script type="text/javascript">
// to check whether stage already exists in the database.


	function ValidateFields()
	{
		 myform=document.getElementById('searchForm');
		
	if(CheckNumeric(myform.searchprojectid,"Project ID")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.searchprojectname,'<%=request.getContextPath()%>','Project Name')==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.searchStageName,'<%=request.getContextPath()%>','Stage Name')==false)
	{
		return false;
	}
	if(ValidateTextField(myform.searchTaskName,'<%=request.getContextPath()%>','Task Name')==false)
	{
		return false;
	}

	}


function searchSubmit(flag)
{
	
    myform=document.getElementById('searchForm');
    setFormBean(myform);
    if(ValidateFields()==false)
	    return false;
    
	if(flag=='5')										// 5 -- Go Button Filter
	{
		if(document.forms[0].dateFilter.value=='0')
		{
			alert("Please Select Date Filter Type !!");
			return false;
		}
	}

	myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	/*if(checkForm()==false)
	{
		return ;
	}*/
	//showLayer();
	document.searchForm.action="<%=request.getContextPath()%>/projectPlanReport.do?method=viewrProjectPlanDetail";
	
	showLayer();
	myform.submit();
}

function pagingSubmit(val)
{
	if(ValidateFields()==false)
	  {
			return false;
	  }

	myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.pageNumber"].value=val;
	showLayer();
	document.searchForm.action="<%=request.getContextPath()%>/projectPlanReport.do?method=viewrProjectPlanDetail";
	showLayer();
	myform.submit();
}

function exportToExcel()
{
	if(ValidateFields()==false)
	  {
			return false;
	  }

	document.searchForm.action="<%=request.getContextPath()%>/projectPlanReport.do?method=exportToExcel";
	document.searchForm.submit();
}

function sortSubmit(sortBy)
{
	if(ValidateFields()==false)
	  {
			return false;
	  }

	myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.pageNumber"].value=1;
	if(myform.elements["pagingSorting.sortByColumn"].value==sortBy)
	{
		if(myform.elements["pagingSorting.sortByOrder"].value=="decrement")
		{
			myform.elements["pagingSorting.sortByOrder"].value="increment";
		}
		else
		{
			myform.elements["pagingSorting.sortByOrder"].value="decrement";
		}
	}
	else
	{
		myform.elements["pagingSorting.sortByColumn"].value=sortBy;
		myform.elements["pagingSorting.sortByOrder"].value="decrement";
	}
	/*if(checkForm()==false)
	{
		return ;
	}*/
	document.searchForm.action="<%=request.getContextPath()%>/projectPlanReport.do?method=viewrProjectPlanDetail";
	showLayer();
	myform.submit();
}

</script>

</head>
<body onLoad="javascript:document.body.click();">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
    <br /><br /><br />
</div>
<div class="error" align="center">
	<logic:present name="validation_errors">
		<logic:iterate id="error_row" name="validation_errors" scope="request">
				<font color="red"><bean:write name="error_row" /></font><BR>
		</logic:iterate>
	</logic:present>
</div>
<script type="text/javascript" src="js/scw.js"></script>
<html:form action="/projectPlanReport" method="post"
	styleId="searchForm">

	<html:hidden property="projectId"/>
	<html:hidden property="taskId"/>
	<html:hidden property="stageId"/>

	<input type="hidden" name="method" value="viewrProjectPlanDetail">
	<bean:define id="formBean" name="projectPlanReportBean"></bean:define>
	<html:hidden name="formBean" property="pagingSorting.sortByColumn" />
	<html:hidden name="formBean" property="pagingSorting.sortByOrder" />
	<html:hidden name="formBean" property="pagingSorting.pageNumber" />
	<input type="hidden" name="method" value="viewProjectPlan" />


	<html:hidden property="projectPlanId" />
	<table border="0" cellpadding="0" cellspacing="0" class="border"
		align="center" width="100%">
		<tr valign="middle" id="projectPlan">
			<td height="48" align="center" valign="bottom">
			<table width="106%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr>
					<td height="19" width="1"><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" height="19" width="29%">Project Plan Report</td>
					<td height="19" width="0"><img src="npd/Images/tabRight.gif"></td>
					<td align="left" height="19" width="772"><font size="1">
					<html:select property="dateFilter" styleId="Id_searchDateFilter"
						styleClass="tabledata">
						<html:option value="0">---- None ----</html:option>
						<html:option value="plannedStartDate">Planned Start Date </html:option>
						<html:option value="plannedEndDate">Planned End Date </html:option>
						<html:option value="actualstartdate">Actual Start Date </html:option>
						<html:option value="actualenddate">Actual End Date </html:option>
					</html:select> From Date<html:text property="fromDate" size="10" readonly="true"
						styleId="dateFrom" styleClass="tabledata" /> <img
						src="npd/Images/cal.gif" width="16" height="16" border="0"
						alt="Pick a date" onClick="scwShow(scwID('fromDate'),event);">
					To Date<html:text property="toDate" size="10" readonly="true"
						styleId="dateTo" styleClass="tabledata" /><font
						size="1"><a
						href="#"><img
						src="npd/Images/cal.gif" width="16" height="16" border="0"
						alt="Pick a date" onClick="scwShow(scwID('toDate'),event);"></a></font>&nbsp;<a
						href="#"><img onClick="searchSubmit();"
						src="npd/Images/search.gif" title="search" height="15"></a>
					<logic:notEmpty
						name="listProjectPlan"><a
						href="#">
						<img onClick="exportToExcel();"
						src="Images/excel.gif" title="Export To Excel" height="15"></a>
						</logic:notEmpty>
						</td>
				
				</tr>
			</table>
			</td>
		</tr>
	</table>

						<table cellSpacing="0" cellPadding="0" width="100%" border="0">

							<tr>
								<td class="tabledata"><bean:define id="pagingBean"
									name="formBean"></bean:define> <%
 			String currPageNumber = String
 			.valueOf(((ProjectPlanReportBean) pagingBean)
 			.getPagingSorting().getPageNumber());
 	String maxPageNumber = String
 			.valueOf(((ProjectPlanReportBean) pagingBean)
 			.getPagingSorting().getMaxPageNumber());
 %> <jsp:include flush="true" page="html-jsp/commons/paging.jsp">
									<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
									<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
								</jsp:include></td>
							</tr>
							</table>
							
								<div style="overflow:scroll;height:320px;width:100%"
									class="scroll">
								<table align="center" cellSpacing="1" cellPadding="4" border="0"
									class="tabledata" id="tblRollList" width="100%">
									<tr valign="middle" class="rptHeader">
										<td align="left" nowrap>SNo.</td>
									<td align="left" nowrap><a href="#"
										onclick="sortSubmit('projectid')">Project Id</a></td>
									<td align="left" nowrap><a href="#"
										onclick="sortSubmit('projectname')">Project Name</a></td>
									<td align="left" nowrap>No Of Days in Project</td>
									<td align="left" nowrap>Project Status</td>
									<td align="left" nowrap><a href="#"
											onclick="sortSubmit('stageName')">Stage</td>
										<td align="left" nowrap><a href="#"
											onclick="sortSubmit('taskName')">Task</td>
										<td align="left" nowrap><a href="#"
											onclick="sortSubmit('plannedstartdate')">Planned Start
										Date</td>
										<td align="left" nowrap><a href="#"
											onclick="sortSubmit('plannedenddate')">Planned End Date</td>
										<td align="left" nowrap><a href="#"
											onclick="sortSubmit('actualstartdate')">Acutal Start Date</td>
										<td align="left" nowrap><a href="#"
											onclick="sortSubmit('actualenddate')">Actual End Date</td>
										<td align="left" nowrap><a href="#"
											onclick="sortSubmit('stakeholderrole')">Stake Holder Role</td>
										<td align="left" nowrap><a href="#"
											onclick="sortSubmit('stakeholdername')">Stake Holder Name</td>
										<td align="left" nowrap><a href="#"
											onclick="sortSubmit('delayindays')">Delay In Days</td>
										<td align="left" nowrap><a href="#"
											onclick="sortSubmit('delayindays')">Task Status</a></td>
									</tr>
									<tr valign="middle" class="rptHeader">
										<td align="left" nowrap>&nbsp;</td>
									<td align="left" nowrap><html:text
											name="projectPlanReportBean" size="6" property="searchprojectid" /></td>
									<td align="left" nowrap><html:text
											name="projectPlanReportBean" property="searchprojectname" /></td>
									<td align="left" nowrap>&nbsp;</td>
									<td background="#FF9255">
										<html:select name="projectPlanReportBean" property="projectStatusFilter">
											<html:option value="-1">ALL</html:option>
											<html:option value="1">OPEN</html:option>
											<html:option value="0">CLOSED</html:option>
											<html:option value="2">DRAFT</html:option>
										</html:select>
									</td>
									<td align="left" nowrap><html:text
											name="projectPlanReportBean"  property="searchStageName" /></td>
										<td align="left" nowrap><html:text
											name="projectPlanReportBean" property="searchTaskName" /></td>
										<td align="left" nowrap>&nbsp;</td>
										<td align="left" nowrap>&nbsp;</td>
										<td align="left" nowrap>&nbsp;</td>
										<td align="left" nowrap>&nbsp;</td>
										<td align="left" nowrap>&nbsp;</td>
										<td align="left" nowrap>&nbsp;</td>
										<td align="left" nowrap>&nbsp;</td>
										<td align="left" nowrap></td>
									</tr>
									<%
									int nSNo = 0;
									int newsno=((ProjectPlanReportBean)pagingBean).getPagingSorting().getSerialNoStart();
									%>
							<logic:empty name="listProjectPlan">
								<tr class="alterRedBg" align="center" >
									<td colspan="14" align="center" nowrap><B>No Record
									Found</td>
								</tr>
							</logic:empty>
									
									<logic:notEmpty name="listProjectPlan">
									<logic:present name="listProjectPlan" scope="request">
										<logic:iterate name="listProjectPlan" id="row"
											indexId="recordId"
											type="com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto">
											<%
														String classType = null;
														if (recordId.intValue() % 2 == 0) {
													classType = "redBg";
														} else {
													classType = "alterRedBg";
														}
														nSNo++;

														if (row.getDelayindays() > 0) {
													classType = "delay";
														}
											%>
											<tr class="<%=classType%>">
												<td align="center" nowrap="nowrap"><%=newsno%></td>
												<td align="center" nowrap="nowrap" ><bean:write name="row" property="project.projectid" /></td>
												<td align="center" nowrap="nowrap"><bean:write name="row" property="project.projectName" /></td>
												<td align="center" nowrap="nowrap"><bean:write name='row' property="daysInProject" /></td>
												<td align="center" nowrap="nowrap">
													<logic:equal value="0" name='row' property="projectStatus">CLOSED</logic:equal>
													<logic:equal value="1" name='row' property="projectStatus">OPEN</logic:equal>
													<logic:equal value="2" name='row' property="projectStatus">DRAFT</logic:equal>
												</td>
												<td nowrap="nowrap" ><bean:write name="row" property="stagename" /></td>
												<td nowrap="nowrap"><bean:write name="row"
													property="taskname" /></td>
												<td nowrap="nowrap"><bean:write name="row" property="plannedstartdate" /></td>
												<td nowrap="nowrap"><bean:write name="row" property="plannedenddate" /></td>
												<td nowrap="nowrap"><bean:write name="row" property="actualstartdate" /></td>
												<td nowrap="nowrap"><bean:write name="row" property="actualenddate" /></td>
												<td nowrap="nowrap"><bean:write name="row" property="stakeholderrole" /></td>
												<td nowrap="nowrap"><bean:write name="row" property="stakeholdername" /></td>
												<td nowrap="nowrap"><bean:write name="row" property="delayindays" /></td>
												<td nowrap="nowrap"><bean:write name="row" property="currentTaskStatus" /></td>

											</tr>
											<%newsno=newsno+1;%>
										</logic:iterate>
									</logic:present>
									</logic:notEmpty>
								</table>
								</div>

</html:form>
</div>
</BODY>

</html:html>
