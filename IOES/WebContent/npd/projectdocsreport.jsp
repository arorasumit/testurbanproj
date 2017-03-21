<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.beans.ProjectPlanReportBean"%>

<html:html>
<head>
<link rel="stylesheet" type="text/css" href="gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">
<script type="text/javascript" src="js/scw.js"></script>
<script language="javascript" src="js/utility.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>
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
<title>NPD PORTAL-Project Documents</title>

<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>


<script type="text/javascript">
// to check whether stage already exists in the database.

	function ValidateFields()
	{
		 myform=document.getElementById('searchForm');
		 if(CheckNumeric(myform.searchprojectid,"Project ID")==false)
		   return false;
		 if(ValidateTextField(myform.searchprojectname,'<%=request.getContextPath()%>', 'Project Name')==false)
			return false;
		 if(ValidateTextField(myform.searchStageName,'<%=request.getContextPath()%>','Stage Name')==false)
			return false;
		 if(ValidateTextField(myform.searchTaskName,'<%=request.getContextPath()%>','Task Name')==false)
			return false;

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


function searchSubmit(flag)
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	if(ValidateFields()==false)
	{
		return false;
	}
	if(flag=='5')										// 5 -- Go Button Filter
	{
		if(document.forms[0].dateFilter.value=='0' &&  document.forms[0].projectStatusFilter.value=='')
		{
			alert("Please Select Any Filter Type !!");
			return false;
		}
	}
	
	
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;

	document.searchForm.action="<%=request.getContextPath()%>/projectDocReport.do?method=viewProjectDocReport";
	
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
	document.searchForm.action="<%=request.getContextPath()%>/projectDocReport.do?method=viewProjectDocReport";
	showLayer();
	myform.submit();
}

function downloadFile(projectActionid,fileName,checkFlag)
{
	document.searchForm.action="<%=request.getContextPath()%>/projectDocReport.do?method=DownloadProjectDocFile";
	myform=document.getElementById('searchForm');
	myform.elements["projectActionId"].value=projectActionid;
	myform.elements["fileName"].value=fileName;
	myform.elements["checkFlag"].value=checkFlag;
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
	document.searchForm.action="<%=request.getContextPath()%>/projectDocReport.do?method=viewProjectDocReport";
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
<logic:present name="validation_errors">
	<table width="80%" class="tabledata" align="center">
		<tr>
			<td>
				<div class="error" align="center">
					<logic:iterate id="error_row" name="validation_errors" scope="request">
							<font color="red"><bean:write name="error_row" /></font><BR>
					</logic:iterate>
				</div>
			</td>
		</tr>
	</table>
</logic:present>
<html:form action="/projectDocReport" method="post" styleId="searchForm">

<input type="hidden" name="method" value="viewrProjectPlanDetail">
<bean:define id="formBean" name="projectPlanReportBean"></bean:define>
<html:hidden name="formBean" property="pagingSorting.sortByColumn"/>
<html:hidden name="formBean" property="pagingSorting.sortByOrder"/>
<html:hidden name="formBean" property="pagingSorting.pageNumber"/>
<html:hidden name="formBean" property="projectActionId"/>
<html:hidden name="formBean" property="fileName"/>
<html:hidden name="formBean" property="checkFlag"/>
<input type="hidden" name="method" value="viewProjectPlan"/>

<html:hidden property="projectPlanId" />
	<table border="0"
		align="center" cellpadding="0" width="100%" cellspacing="0" class="borderTab">
		<tr>
			<td width="6" height="19"><img src="npd/Images/tabLeft.gif"></td>
			<td background="npd/Images/tabbg.gif" class="tabHeading" width="240">Project Document Report</td>
			<td width="6"><img src="npd/Images/tabRight.gif"></td>
			<td align="left"><font size="1"> 
			  <html:select
				property="dateFilter" styleId="Id_searchDateFilter"
				styleClass="tabledata">
				<html:option value="0">---- None ----</html:option>
				<html:option value="plannedStartDate">Planned Start Date </html:option>
				<html:option value="plannedEndDate">Planned End Date </html:option>
				<html:option value="actualstartdate">Actual Start Date </html:option>
				<html:option value="actualenddate">Actual End Date </html:option>
			</html:select> From Date<html:text property="fromDate" size="10" readonly="true"
				styleId="dateFrom" styleClass="tabledata" /><img
				src="npd/Images/cal.gif" width="16" height="16" border="0"
				alt="Pick a date" onClick="scwShow(scwID('fromDate'),event);">
			To Date<html:text property="toDate" size="10" readonly="true"
				styleId="dateTo" styleClass="tabledata" /> <font
				size="1"><img
				src="npd/Images/cal.gif" width="16" height="16" border="0"
				alt="Pick a date" onClick="scwShow(scwID('toDate'),event);">
			Project Status <html:select property="projectStatusFilter"
				styleId="Id_searchprojectStatus" styleClass="tabledata">
				<html:option value="-1">---- All ----</html:option>
				<html:option value="1">Open</html:option>
				<html:option value="0">Close</html:option>
		  </html:select>
			</font></td>
			<td width="56" align="right" style="padding-right:10px;">

			<a href="#"><img onClick="searchSubmit();"
				src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;
			<logic:notEmpty
				name="listProjectPlan">
				<!-- <a href="#"><img onClick="exportToExcel();"
					src="Images/excel.gif" title="Export To Excel" height="15"></a> -->
		  </logic:notEmpty></td>
		</tr>
	</table>

				<table cellSpacing="0" cellPadding="0" width="100%" border="0" id="tbl1"
		class="tabledata" style="display:block" align="center">
  <tr>
				  <td class="tabledata"><bean:define
						id="pagingBean" name="formBean"></bean:define> <%  String  currPageNumber =String.valueOf(((ProjectPlanReportBean)pagingBean).getPagingSorting().getPageNumber());
						String  maxPageNumber =String.valueOf(((ProjectPlanReportBean)pagingBean).getPagingSorting().getMaxPageNumber());
					%> <jsp:include flush="true" page="html-jsp/commons/paging.jsp">
					  <jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
					  <jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
				  </jsp:include></td>
  </tr>
  </table>
					<div style="overflow:scroll;height:370px;width:100%" class="scroll">
					<table align="center" cellSpacing="1" cellPadding="4" border="0"
						class="tabledata" id="tblRollList" width="100%">
						<tr valign="middle" class="rptHeader" >
							<td align="left" nowrap>SNo.</td>
							<td align="left" nowrap>File Uploaded</td>
							<td align="left" nowrap><a href="#"
								onclick="sortSubmit('projectid')">Project Id</a></td>
							<td align="left" nowrap><a href="#"
								onclick="sortSubmit('projectname')">Project Name</a></td>
							<td align="left" nowrap><a href="#"
								onclick="sortSubmit('stageName')">Stage</td>
							<td align="left" nowrap><a href="#"
								onclick="sortSubmit('taskName')">Task</td>
							<td align="left" nowrap><a href="#"
								onclick="sortSubmit('plannedstartdate')">Planned Start Date</td>
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
						</tr>
						<tr valign="middle" class="rptHeader">
							<td align="left" nowrap>&nbsp;</td>
							<td align="left" nowrap>&nbsp;</td>
							<td align="left" nowrap><html:text
								name="projectPlanReportBean" size="6" property="searchprojectid" maxlength="5"/></td>
							<td align="left" nowrap><html:text
								name="projectPlanReportBean" property="searchprojectname" maxlength="20"/></td>
							<td align="left" nowrap><html:text
								name="projectPlanReportBean" property="searchStageName" maxlength="20"/></td>
							<td align="left" nowrap><html:text
								name="projectPlanReportBean" property="searchTaskName" maxlength="20"/></td>
							<td align="left" nowrap>&nbsp;</td>
							<td align="left" nowrap>&nbsp;</td>
							<td align="left" nowrap>&nbsp;</td>
							<td align="left" nowrap>&nbsp;</td>
							<td align="left" nowrap>&nbsp;</td>
							<td align="left" nowrap>&nbsp;</td>
						</tr>
						<%int nSNo=0; 
						int newsno=((ProjectPlanReportBean)pagingBean).getPagingSorting().getSerialNoStart();
						
						%>
						<logic:notEmpty name="listProjectPlan">
						<logic:present name="listProjectPlan" scope="request">
								<logic:iterate name="listProjectPlan" id="row"
									indexId="recordId"
									type="com.ibm.ioes.npd.hibernate.beans.ProjectPlanReportDto">
									<%
					String classType=null;
					if(recordId.intValue() % 2 == 0)
					{
					classType="redBg";
					}
					else
					{
					classType="alterRedBg";				
					}	
					nSNo++;	
									
				%>
									<tr class="<%=classType%>">

										<td align="center"><%=newsno %></td>
										<td nowrap="nowrap"><a href="#"
											onclick="downloadFile(<bean:write name="row" property="projectActionId" />,'<bean:write name="row" property="docName" />',<bean:write name="row" property="checkFlag" />);">
												<bean:write name="row"
												property="docName" /></td>		
										<td align="center"><bean:write name="row"
											property="project.projectid" /></td>
										<td align="center" nowrap="nowrap"><bean:write name="row"
											property="project.projectName" /></td>
										<td nowrap="nowrap">
											<logic:notEqual value="NOT DEFINED" name="row" property="stagename">
												<bean:write name="row" property="stagename"/>	
											</logic:notEqual>
										</td>
										<td nowrap="nowrap">
											<logic:notEqual value="NOT DEFINED" name="row" property="taskname">
												<bean:write name="row" property="taskname" />
											</logic:notEqual>
										</td>
										<td nowrap="nowrap">
											<logic:notEqual value="1900-01-01" name="row" property="plannedstartdate">
												<bean:write name="row" property="plannedstartdate" />
											</logic:notEqual>
										</td>
										<td nowrap="nowrap">
											<logic:notEqual value="1900-01-01" name="row" property="plannedenddate">
												<bean:write name="row" property="plannedstartdate" />
											</logic:notEqual>
										</td>
										<td nowrap="nowrap">
											<logic:notEqual value="1900-01-01" name="row" property="actualstartdate">
												<bean:write name="row" property="plannedstartdate" />
											</logic:notEqual>
										</td>
										<td nowrap="nowrap">
											<logic:notEqual value="1900-01-01" name="row" property="plannedenddate">
												<bean:write name="row" property="actualenddate" />
											</logic:notEqual>
										</td>
										<td nowrap="nowrap">
											<logic:notEqual value="NOT DEFINED" name="row" property="stakeholderrole">
												<bean:write name="row" property="stakeholderrole" />
											</logic:notEqual>
										</td>
											
										<td nowrap="nowrap">
											<logic:notEqual value="NOT DEFINED" name="row" property="stakeholdername">
												<bean:write name="row" property="stakeholdername" />
											</logic:notEqual>
										</td>

									</tr>
									<%newsno=newsno+1; %>
								</logic:iterate>
							
						</logic:present>
						</logic:notEmpty>
						<logic:empty name="listProjectPlan">
							<tr class="alterRedBg" align="center">
								<td colspan="12"><b>NO RECORDS FOUND</b></td>
							</tr>
						</logic:empty>
					</table>
					</div>
</html:form>
</div>
</BODY>
</html:html>
