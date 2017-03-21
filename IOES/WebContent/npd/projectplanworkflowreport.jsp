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
<title> View Workflow  </title>
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
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/CSS/style.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/chrome.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/staticValidatorScript.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/staticValidatorScript.js"></script>


<%@include file="/js/scwblank.html"%>


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
		
		
		 if(ValidateTextField(myform.searchprojectname,'<%=request.getContextPath()%>',"Project Name")==false)
			return false;

		 if(ValidateTextField(myform.searchStageName,'<%=request.getContextPath()%>',"Stage Name")==false)
			return false;
		 if(ValidateTextField(myform.searchTaskName,'<%=request.getContextPath()%>',"Task Name")==false)
			return false;

	}


function searchSubmit(flag)
{
	if(ValidateFields()==false)
	  {
			return false;
	  }

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
	document.searchForm.action="<%=request.getContextPath()%>/projectPlanReport.do?method=viewprojectworkflow";
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
	//showLayer();
	document.searchForm.action="<%=request.getContextPath()%>/projectPlanReport.do?method=viewprojectworkflow";
	showLayer();
	myform.submit();
}

function exportToExcel()
{
	if(ValidateFields()==false)
	  {
			return false;
	  }

	document.searchForm.action="<%=request.getContextPath()%>/projectPlanReport.do?method=exportProjectStatus";
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
	//showLayer();
	document.searchForm.action="<%=request.getContextPath()%>/projectPlanReport.do?method=viewprojectworkflow";
	showLayer();
	myform.submit();
}

function viewDiv(view1 , view2,view3,view4)
{
		document.getElementById('issueTab').style.display=view1;
		document.getElementById('assumptionTab').style.display=view2;
		document.getElementById('riskTab').style.display=view3;
		document.getElementById('rfiTab').style.display=view4;
}

function download(docid,docname)
{
	document.searchForm("docid").value = docid;
	document.searchForm("rfidocname").value = docname;
	document.searchForm.action="<%=request.getContextPath()%>/projectPlanReport.do?method=DownloadRFIFile";
	document.searchForm.submit(); 
}


</script>

</head>
<body onload="javascript:document.body.click();" >
<div id="menu_abc" style="display: block;">
	<div id="shadow" class="opaqueLayer"> 
		<br /><br /><br /><br /><br /><br /><br /><br />
	    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
	    <br /><br /><br />
	</div>
	<script type="text/javascript" src="js/scw.js"></script>
	<html:form action="/projectPlanReport" method="post" styleId="searchForm">
		<html:hidden property="projectId"/>
		<html:hidden property="taskId"/>
		<html:hidden property="stageId"/>
		<html:hidden property="docid"/>
		<html:hidden property="rfidocname"/>
		<input type="hidden" name="method" value="viewrProjectPlanDetail">
		<bean:define id="formBean" name="projectPlanReportBean"></bean:define>
		<html:hidden name="formBean" property="pagingSorting.sortByColumn" />
		<html:hidden name="formBean" property="pagingSorting.sortByOrder" />
		<html:hidden name="formBean" property="pagingSorting.pageNumber" />
		<input type="hidden" name="method" value="viewProjectPlan" />
		<html:hidden property="projectPlanId" />
		<table border="0" cellpadding="0" cellspacing="0" class="border" align="center" width="91%">
			<tr valign="middle" id="projectPlan">
				<td  height="48" align="center" valign="bottom">
					<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="borderTab">
						<tr>
							<td width="0" height="19"><img src="npd/Images/tabLeft.gif"></td>
							<td background="npd/Images/tabbg.gif" class="tabHeading" height="19"
								width="26%">Project Plan Workflow</td>
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
								styleId="dateFrom" styleClass="tabledata" /> <a
								href="javascript:show_calendar('forms[0].fromDate');"><img
								src="npd/Images/cal.gif" width="16" height="16" border="0"
								alt="Pick a date" onclick="scwShow(scwID('fromDate'),event);"></a>
								To Date<html:text property="toDate" size="10" readonly="true"
									styleId="dateTo" styleClass="tabledata" /> <a
									href="javascript:show_calendar('forms[0].toDate');"><font
									size="1"><a
									href="javascript:show_calendar('forms[0].toDate');"><img
									src="npd/Images/cal.gif" width="16" height="16" border="0"
									alt="Pick a date" onclick="scwShow(scwID('toDate'),event);"></a></font>
									<input type="button" value="Go"
										onclick="javascript:searchSubmit(5);"
										style="background-color:#FF9933;height:20px">
							</td>
							<td align="right" style="padding-right:10px;" width="166"
								height="19"><a href="#"><img onclick="searchSubmit();"
								src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;
								<logic:notEmpty name="listProjectPlan">
								<a href="#"><img onclick="exportToExcel();"
								src="Images/excel.gif" title="Export To Excel" height="15"></a>
								</logic:notEmpty>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<table cellSpacing="0" cellPadding="0" width="91%" border="0" id="tbl1"
			class="tabledata" style="display:block" align="center">
	
			<tr>
				<td>
				<table border="0" cellpadding="0" cellspacing="0" class="border"
					align="center" width="100%">
				</table>
	
				</td>
			</tr>
			<tr valign="bottom" align="center">
				<td align="right">
				<table cellSpacing="0" cellPadding="0" width="100%" border="0"
					id="tbl1" class="tabledata" style="display:block" align="center">
					<tr>
						<td align="center"><logic:empty name="listProjectPlan">No Record Found</logic:empty>
						
	
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
								<tr>
									<td colspan="2" align="center" width="1247">
									<div style="overflow:scroll;height:370px;width:100%"
										class="scroll">
									<table align="center" cellSpacing="1" cellPadding="4" border="0"
										class="scroll" id="tblRollList" width="108%">
										<tr valign="middle" class="rptHeader">
											<td align="left" nowrap>SNo.</td>
										<td align="left" nowrap><a href="#"
											onclick="sortSubmit('projectid')">Project Id</a></td>
										<td align="left" nowrap><a href="#"
											onclick="sortSubmit('projectname')">Project Name</a></td>
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
										<td align="left" nowrap><html:hidden
												name="projectPlanReportBean" property="searchprojectid" /></td>
										<td align="left" nowrap><html:text
												name="projectPlanReportBean" property="searchprojectname" /></td>
										<td align="left" nowrap><html:text
												name="projectPlanReportBean" property="searchStageName" /></td>
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
										%>
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
													<td align="center"><%=nSNo%></td>
													<td align="center"><bean:write name="row" property="project.projectid" /></td>
													<td align="center"><bean:write name="row" property="project.projectName" /></td>
													<td><bean:write name="row" property="stagename" /></td>
													<td nowrap="nowrap"><bean:write name="row"
														property="taskname" /></td>
													<td><bean:write name="row" property="plannedstartdate" /></td>
													<td><bean:write name="row" property="plannedenddate" /></td>
													<td><bean:write name="row" property="actualstartdate" /></td>
													<td><bean:write name="row" property="actualenddate" /></td>
													<td><bean:write name="row" property="stakeholderrole" /></td>
													<td><bean:write name="row" property="stakeholdername" /></td>
													<td><bean:write name="row" property="delayindays" /></td>
													<td><bean:write name="row" property="currentTaskStatus" /></td>
	
												</tr>
											</logic:iterate>
										</logic:present>
										</logic:notEmpty>
									</table>
									</div>
									</td>
								</tr>
	
							</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>

	</table>
	<table>
	
	
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
<tr valign="middle" id="projectPlan" align="left">
	
	<td valign="bottom" align="left" onclick="javascript:viewDiv('block','none','none','none')" id="issuetd">
	<table border="0" id="issuetable" cellspacing="0" cellpadding="0"
		align="center" class="borderTab">
		<tr>
			<td width="0"><img src="<%=request.getContextPath()%>/npd/Images/tabLeft.gif"></td>
			<td align="left" background="<%=request.getContextPath()%>/npd/Images/tabbg.gif" class="tabHeading" nowrap="nowrap" >(Issues)</td>
			<td align="left"><img src="<%=request.getContextPath()%>/npd/Images/tabRight.gif"></td>
			
			
		</tr>
	</table>
	</td>
	<td valign="bottom" align="left" onclick="javascript:viewDiv('none' , 'block','none','none')" >
	<table border="0" cellspacing="0" cellpadding="0"
		align="center" class="borderTab">
		<tr>
			<td width="0"><img src="<%=request.getContextPath()%>/npd/Images/tabLeft.gif"></td>
			<td align="left" background="<%=request.getContextPath()%>/npd/Images/tabbg.gif" class="tabHeading" nowrap="nowrap" >(Assumptions)</td>
			<td align="left"><img src="<%=request.getContextPath()%>/npd/Images/tabRight.gif"></td>
			
			
		</tr>
	</table>
	</td>

	<td valign="bottom" onclick="javascript:viewDiv('none' , 'none','block','none')">
	<table border="0" cellspacing="0" cellpadding="0"
		align="center" class="borderTab">
		<tr>
			<td width="0"><img src="<%=request.getContextPath()%>/npd/Images/tabLeft.gif"></td>
			<td align="left" background="<%=request.getContextPath()%>/npd/Images/tabbg.gif" class="tabHeading" nowrap="nowrap" >(Risks)</td>
			<td align="left"><img src="<%=request.getContextPath()%>/npd/Images/tabRight.gif"></td>
			
			
		</tr>
	</table>
	</td>

	<td valign="bottom" onclick="javascript:viewDiv('none' , 'none','none','block')">
	<table border="0" cellspacing="0" cellpadding="0"
		align="center" class="borderTab">
		<tr>
			<td width="0"><img src="<%=request.getContextPath()%>/npd/Images/tabLeft.gif"></td>
			<td align="left" background="<%=request.getContextPath()%>/npd/Images/tabbg.gif" class="tabHeading" nowrap="nowrap" >(RFI)</td>
			<td align="left"><img src="<%=request.getContextPath()%>/npd/Images/tabRight.gif"></td>
			
			
		</tr>
	</table>
	</td>

</tr>
</table>
	<table cellSpacing="0" cellPadding="0" width="98%" border="0" id="tbl1"
		class="tabledata" style="display:block" align="center">
			<tr>
			<td>
			
						<div style="overflow:scroll;height:370px;width:100%;display:block;" class="scroll" id="issueTab" >
								<table align="center" cellSpacing="1" cellPadding="4" border="0" class="scroll" id="tblRollList" width="108%">
									<tr valign="middle" class="rptHeader">
												<td align="left" nowrap>Issue Desc</td>
												<td align="left" nowrap>Issue Priority</td>

									</tr>
									<%
										nSNo = 0;
									%>
									<logic:notEmpty name="listProjectIssue">
									<logic:present name="listProjectIssue" scope="request">
										<logic:iterate name="listProjectIssue" id="row"
											indexId="recordId" type="com.ibm.ioes.npd.hibernate.beans.TtrnProjectisssues">
											<%
														String classType = null;
														if (recordId.intValue() % 2 == 0) {
													classType = "redBg";
														} else {
													classType = "alterRedBg";
														}
														nSNo++;
											%>
											<tr class="<%=classType%>">
												<td><bean:write name="row" property="issuedesc" /></td>
												<td><bean:write name="row" property="priority" /></td>

											</tr>
										</logic:iterate>
									</logic:present>
									</logic:notEmpty>
								</table>
								</div>
			
						<div style="overflow:scroll;height:370px;width:100%;display:none;" class="scroll" id="assumptionTab">
								<table align="center" cellSpacing="1" cellPadding="4" border="0" class="scroll" id="tblRollList" width="100%">
									<tr valign="middle" class="rptHeader">
												<td align="left" nowrap>Assumption Desc</td>
												<td align="left" nowrap>Impact</td>
												<td align="left" nowrap>Rasied Date</td>

									</tr>
									<%
										nSNo = 0;
										String impact = null;
									%>
									<logic:notEmpty name="listProjectassumption">
									<logic:present name="listProjectassumption" scope="request">
										<logic:iterate name="listProjectassumption" id="rowassumption"
											indexId="recordId" type="com.ibm.ioes.npd.hibernate.beans.TtrnProjectassumptions" >
											<%
														String classType = null;
														if (recordId.intValue() % 2 == 0) {
													classType = "redBg";
														} else {
													classType = "alterRedBg";
														}
														nSNo++;
												
												if(rowassumption.getImpact().equalsIgnoreCase("0"))
													impact = "High";
												if(rowassumption.getImpact().equalsIgnoreCase("1"))
													impact = "Medium";
												if(rowassumption.getImpact().equalsIgnoreCase("2"))
													impact = "Low";
													
											%>
											
											<tr class="<%=classType%>">
												<td><bean:write name="rowassumption" property="description" /></td>
												<td><%=impact  %></td>
												<td><bean:write name="rowassumption" property="raiseddate" /></td>

											</tr>
										</logic:iterate>
									</logic:present>
									</logic:notEmpty>
								</table>
								</div>
				
						<div style="overflow:scroll;height:370px;width:100%;display:none;" class="scroll" id="riskTab">
								<table align="center" cellSpacing="1" cellPadding="4" border="0" class="scroll" id="tblRollList" width="100%">
									<tr valign="middle" class="rptHeader">
												<td align="left" nowrap>Risk Desc</td>
												<td align="left" nowrap>Status</td>
												<td align="left" nowrap>Source</td>
												<td align="left" nowrap>Priority</td>

									</tr>
									<%
										nSNo = 0;
										String status = null;
									%>
									<logic:notEmpty name="listProjectrisk">
									<logic:present name="listProjectrisk" scope="request">
										<logic:iterate name="listProjectrisk" id="rowrisk"
											indexId="recordId" type="com.ibm.ioes.npd.hibernate.beans.TtrnProjectrisks" >
											<%
														String classType = null;
														if (recordId.intValue() % 2 == 0) {
													classType = "redBg";
														} else {
													classType = "alterRedBg";
														}
														nSNo++;
														
												            if(rowrisk.getStatus()==1)
												            	status = "Open";
												            else
												            	status = "Close";
														
											%>
											
											<tr class="<%=classType%>">
												<td><bean:write name="rowrisk" property="description" /></td>
												<td><%=status%></td>
												<td><bean:write name="rowrisk" property="risksource" /></td>
												<td><bean:write name="rowrisk" property="priority" /></td>

											</tr>
										</logic:iterate>
									</logic:present>
									</logic:notEmpty>
								</table>
								</div>

						<div style="overflow:scroll;height:370px;width:100%;display:none;" class="scroll" id="rfiTab">
								<table align="center" cellSpacing="1" cellPadding="4" border="0" class="scroll" id="tblRollList" width="100%">
									<tr valign="middle" class="rptHeader">
									
												<td align="left" nowrap> Task Name</td>
												<td align="left" nowrap>RFI Raised Against</td>
												<td align="left" nowrap>Role</td>
												<td align="left" nowrap>RFI Action </td>
												<td align="left" nowrap>Remarks</td>
												<td align="left" nowrap>RFI Action Date</td>
												<td align="left" nowrap>Document</td>

									</tr>
									<%
										nSNo = 0;
										 status = null;
									%>
									<logic:notEmpty name="listProjectrfiaction">
									<logic:present name="listProjectrfiaction" scope="request">
										<logic:iterate name="listProjectrfiaction" id="rowrfi"
											indexId="recordId" >
											<%
														String classType = null;
														if (recordId.intValue() % 2 == 0) {
													classType = "redBg";
														} else {
													classType = "alterRedBg";
														}
														nSNo++;
											%>
											
											<tr class="<%=classType%>">
												<td><bean:write name="rowrfi" property="rfitaskname" /></td>
												<td><bean:write name="rowrfi" property="assignedtoname" /></td>
												<td><bean:write name="rowrfi" property="assignedtorolename" /></td>
												<td><bean:write name="rowrfi" property="actiontaken" /></td>
												<td><bean:write name="rowrfi" property="actionremarks" /></td>
												<td><bean:write name="rowrfi" property="actiondate" /></td>
												<td>
												<a href="#" onclick="download(<bean:write name="rowrfi" property="rfidocid" />,'<bean:write name="rowrfi" property="rfidocname" />')"> 
													<bean:write name="rowrfi" property="rfidocname" />
												</a>
												</td>
												

											</tr>
										</logic:iterate>
									</logic:present>
									</logic:notEmpty>
								</table>
								</div>


			</td>
			</tr>
			</table>
		</table>
		</html:form>
		</div>
</BODY>

</html:html>
