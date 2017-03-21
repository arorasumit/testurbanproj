<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.ibm.ioes.npd.beans.FunnelReportbean"%>
<%@page import="com.ibm.ioes.npd.hibernate.beans.FunnelReportDto"%>
<%@page import="com.ibm.ioes.npd.hibernate.beans.TmWorkflowstage"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">

<script language="JavaScript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/scw.js"></script>
<script type="text/javascript" src="js/timepicker.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>
<script type="text/javascript" src="js/inputColor.js"></script>
<script language="javascript" src="js/utility.js"></script>
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
<title>ChangeOrder Workflow</title>

<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>

<script type="text/javascript">
function searchSubmit(flag)
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	if(flag=='5')										// 5 -- Go Button Filter
	{
		if(document.forms[0].dateFilter.value=='0')
		{
			alert("Please Select Date Filter Type !!");
			return false;
		}
	}
	
	if(CheckNumeric(myform.searchProjectID,"Project ID")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.projectName,'<%=request.getContextPath()%>',"Project Name")==false)
	{
		return false;
	}
	
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	/*if(checkForm()==false)
	{
		return ;
	}*/
	//showLayer();
	document.searchForm.action="<%=request.getContextPath()%>/FunnelReport.do?method=initAccessMatrix";
	
	showLayer();
	myform.submit();
}

function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.pageNumber"].value=val;
	
	if(CheckNumeric(myform.searchProjectID,"Project ID")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.projectName,'<%=request.getContextPath()%>',"Project Name")==false)
	{
		return false;
	}
	document.searchForm.action="<%=request.getContextPath()%>/FunnelReport.do?method=initAccessMatrix";
	showLayer();
	myform.submit();
}

function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	if(CheckNumeric(myform.searchProjectID,"Project ID")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.projectName,'<%=request.getContextPath()%>',"Project Name")==false)
	{
		return false;
	}
	
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
	document.searchForm.action="<%=request.getContextPath()%>/FunnelReport.do?method=initAccessMatrix";
	showLayer();
	myform.submit();
}

function exportToExcel()
{
	myform=document.getElementById('searchForm');
	if(CheckNumeric(myform.searchProjectID,"Project ID")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.projectName,'<%=request.getContextPath()%>',"Project Name")==false)
	{
		return false;
	}
	
	document.searchForm.action="<%=request.getContextPath()%>/FunnelReport.do?method=viewExportToExcel";
	document.searchForm.submit();
}
</script>

</head>
<body onload="javascript:document.body.click();">
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
<html:form action="/FunnelReport" styleId="searchForm" method="post">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="borderTab">
					<tr>
						<td><img src="npd/Images/tabLeft.gif"></td>
						<td background="npd/Images/tabbg.gif" class="tabHeading" width="25%">Funnel Report</td>
						<td><img src="npd/Images/tabRight.gif"></td>
						<td align="left" width="70%">
							<font size="1"> 
								<html:select property="dateFilter" styleId="Id_searchDateFilter" styleClass="tabledata">
									<html:option value="0">--Select a Date--</html:option>
									<html:option value="revisedLaunchDate">Revised Launch Date</html:option>
								</html:select> 
								From Date<html:text property="fromDate" size="10" readonly="true" styleId="dateFrom" styleClass="tabledata"/>
								<img src="npd/Images/cal.gif" width="16" height="16" border="0" alt="Pick a date" onclick="scwShow(scwID('fromDate'),event);">
								To Date<html:text property="toDate" size="10" readonly="true" styleId="dateTo" styleClass="tabledata"/>
								<font size="1">
								<img src="npd/Images/cal.gif" width="16" height="16" border="0" alt="Pick a date" onclick="scwShow(scwID('toDate'),event);"></font>
							  </td>
						<td width="4%"><img src="npd/Images/zero.gif" width="15" height="1"></td>
						<td align="right" style="padding-right:10px;" width="20%"><a href="#"><img onclick="searchSubmit();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;</td>
						<td align="right" style="padding-right:10px;" width="36%">
							<logic:equal value="1" name="funnelBean" property="checkRptData">
								<img id="excelImage" src="<%=request.getContextPath()%>/Images/excel.gif" border="0" onclick="javascript:exportToExcel();" style="visibility: visible" height="15"/>								
							</logic:equal>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

	<bean:define id="formBean" name="funnelBean"></bean:define>
	<html:hidden name="formBean" property="pagingSorting.sortByColumn"/>
	<html:hidden name="formBean" property="pagingSorting.sortByOrder"/>
	<html:hidden name="formBean" property="pagingSorting.pageNumber"/>
	<input type="hidden" name="method" value="initAccessMatrix"/>
	<html:hidden property="projectID" />
	<html:hidden property="reportID" value="2"/>
	<table width="100%" border="1" class="tabledata" cellpadding="3" cellspacing="1" align="center">
	<tr>
		<td colspan="23" class="tabledata" >
					<bean:define id="pagingBean" name="formBean"></bean:define>
			<%  String  currPageNumber =String.valueOf(((FunnelReportbean)pagingBean).getPagingSorting().getPageNumber());
				String  maxPageNumber =String.valueOf(((FunnelReportbean)pagingBean).getPagingSorting().getMaxPageNumber());
			%>
			<jsp:include flush="true" page="html-jsp/commons/paging.jsp">
				<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
				<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
			</jsp:include>
		</td>
	</tr>	
	</table>
	<div style="overflow:scroll;height:350px;width:100%" class="scroll">
		<table cellSpacing="0" cellPadding="0" width="91%" border="0" id="tbl1" class="tabledata" style="display:block" align="center">
							<tr  valign="middle" class="rptHeader">	
								<td>S.No</td>
								<td ><a href="#" onclick="sortSubmit('projectID')">Project ID</td>
								<td ><a href="#" onclick="sortSubmit('projectName')">Project Name</td>
								<td>No of Days in Project</td>
								<td ><a href="#" onclick="sortSubmit('projectStatus')">Project Status</td>
								<td ><a href="#" onclick="sortSubmit('launchPriority')">Priority of launch</td>
								<td ><a href="#" onclick="sortSubmit('productBrief')">Product Brief</td>
								<td><a href="#" onclick="sortSubmit('targetMarket')">Target Market</td>
								<td><a href="#" onclick="sortSubmit('productCategory')">Product Category</td>
								<td><a href="#" onclick="sortSubmit('airtelPotential')">Airtel Potential</td>
								<td><a href="#" onclick="sortSubmit('TotalMktPotential')">Total Market Potential</td>
								<td><a href="#" onclick="sortSubmit('capexRequirement')">Capex Requirement</td>
								<td><a href="#" onclick="sortSubmit('productMgr')">Product Manager</td>
								<td><a href="#" onclick="sortSubmit('techLead')">Tech Lead</td>
								<td><a href="#" onclick="sortSubmit('startMonth')">Start Month</td>
								<td><a href="#" onclick="sortSubmit('baselinelaunchmonth')">Baseline Launch Month</td>
								<td><a href="#" onclick="sortSubmit('rvsdLaunchDate')">Revised Launch Date</td>
								<td><a href="#" onclick="sortSubmit('launchqtr')">Launch Quarter</td>
								<td><a href="#" onclick="sortSubmit('launchStatus')">Launch Status</td>
								<logic:iterate id="stagelistrow1"  name="stagelist" indexId="index1">
									<td>
											<bean:write name='stagelistrow1' property="stagedesc" />
									</td>
								</logic:iterate>
							</tr>
							<tr bgcolor="#FF9255" class="rptHeader">
								<td>&nbsp;</td>	
								<td><html:text name="funnelBean" property="searchProjectID" size="5" maxlength="5"/></td>
								<td><html:text name="funnelBean" property="projectName" maxlength="20"/></td>
								<td>&nbsp;</td>
								<td background="#FF9255">
									<html:select name="funnelBean" property="searchProjectStatus">
										<html:option value="-1">ALL</html:option>
										<html:option value="1">OPEN</html:option>
										<html:option value="0">CLOSED</html:option>
										<html:option value="2">DRAFT</html:option>
									</html:select>
								</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>&nbsp;</td>
								<td>
								<html:select name="funnelBean" property="searchlaunchstatus">
										<html:option value="ALL">ALL</html:option>
										<html:option value="Launched">Launched</html:option>
										<html:option value="Not Launched">Not Launched</html:option>
									</html:select>
								</td>
								<logic:iterate id="stagelistrow2"  name="stagelist" indexId="index2">
									<td></td>
								</logic:iterate>

							</tr>
							<%int sno;
							sno=0;
							int newsno=((FunnelReportbean)pagingBean).getPagingSorting().getSerialNoStart();
							%> 
							<logic:notEmpty name="listFunnelReport">							
							<logic:iterate id="row"  name="listFunnelReport" indexId="index1" type="com.ibm.ioes.npd.hibernate.beans.FunnelReportDto"  >
								<%sno=sno+1;%>
								<c:if test="${index1%2==0}">
								<tr class="redBg" align="center">
									<td><%=newsno%></td>
									<td><bean:write name='row' property="projectID" /></td>
									<td><bean:write name='row' property="projectName" /></td>
									<td><bean:write name='row' property="daysInProject" /></td>
									<td>
										<logic:equal value="0" name='row' property="projectStatus">CLOSED</logic:equal>
										<logic:equal value="1" name='row' property="projectStatus">OPEN</logic:equal>
										<logic:equal value="2" name='row' property="projectStatus">DRAFT</logic:equal>
									</td>
									<td><bean:write name='row' property="launchPriority" /></td>
									<td><bean:write name='row' property="productBrief" /></td>
									<td><bean:write name='row' property="targetMarket" /></td>
									<td><bean:write name='row' property="productCategory" /></td>
									<td><bean:write name='row' property="airtelPotential" /></td>
									<td><bean:write name='row' property="totalMktPotential" /></td>
									<td><bean:write name='row' property="capexRequirement" /></td>
									<td><bean:write name='row' property="productMgr" /></td>
									<td><bean:write name='row' property="techLead" /></td>
									<td><bean:write name='row' property="startMonth" /></td>
									<td><bean:write name='row' property="baselinelaunchmonth" /></td>
									<td><bean:write name='row' property="rvsdLaunchDate" /></td>
									<td><bean:write name='row' property="launchqtr" /></td>
									<td><bean:write name='row' property="launchStatus" /></td>
									<logic:iterate id="stagelistrow"  name="stagelist" indexId="index3" type="com.ibm.ioes.npd.hibernate.beans.TmWorkflowstage">
											<% 
												TmWorkflowstage dto = null;
												String var = null;
												for (int i=0; i<row.getProjectstageList().size();i++) 
												{
													//var = null;
													dto = new TmWorkflowstage();
													dto = (TmWorkflowstage) row.getProjectstageList().get(i);
													
													 if(dto.getStageid() == stagelistrow.getStageid())
													 {
													 	var = "X";		
													 }								  	 		
												  }
												  if(var ==null )
												  	var = "";
											  	%>
											  			
		
												<td> <%=var %> 	</td>
																							
									</logic:iterate>
									
								</tr>
							</c:if>
							<c:if test="${index1%2!=0}">
								<tr class="alterRedBg" align="center">
									<td><%=newsno%></td>
									<td><bean:write name='row' property="projectID" /></td>
									<td><bean:write name='row' property="projectName" /></td>
									<td><bean:write name='row' property="daysInProject" /></td>
									<td>
										<logic:equal value="0" name='row' property="projectStatus">CLOSED</logic:equal>
										<logic:equal value="1" name='row' property="projectStatus">OPEN</logic:equal>
										<logic:equal value="2" name='row' property="projectStatus">DRAFT</logic:equal>
									</td>
									<td><bean:write name='row' property="launchPriority" /></td>
									<td><bean:write name='row' property="productBrief" /></td>
									<td><bean:write name='row' property="targetMarket" /></td>
									<td><bean:write name='row' property="productCategory" /></td>
									<td><bean:write name='row' property="airtelPotential" /></td>
									<td><bean:write name='row' property="totalMktPotential" /></td>
									<td><bean:write name='row' property="capexRequirement" /></td>
									<td><bean:write name='row' property="productMgr" /></td>
									<td><bean:write name='row' property="techLead" /></td>
									<td><bean:write name='row' property="startMonth" /></td>
									<td><bean:write name='row' property="baselinelaunchmonth" /></td>
									<td><bean:write name='row' property="rvsdLaunchDate" /></td>
									<td><bean:write name='row' property="launchqtr" /></td>
									<td><bean:write name='row' property="launchStatus" /></td>
									<logic:iterate id="stagelistrowalt"  name="stagelist" indexId="index4" type="com.ibm.ioes.npd.hibernate.beans.TmWorkflowstage">
											<% 
												TmWorkflowstage dto = null;
												String var = null;
												for (int i=0; i<row.getProjectstageList().size();i++) 
												{
													//var = null;
													dto = new TmWorkflowstage();
													dto = (TmWorkflowstage) row.getProjectstageList().get(i);
													
													 if(dto.getStageid() == stagelistrowalt.getStageid())
													 {
													 	var = "X";		
													 }								  	 		
												  }
												  if(var ==null )
												  	var = "";
											  	%>
											  			
		
												<td> <%=var %> 	</td>
																							
									</logic:iterate>
									
								</tr>
							</c:if>
							<%newsno=newsno+1; %>
							</logic:iterate>
						</logic:notEmpty>
						<logic:empty name="listFunnelReport">
							<tr class="alterRedBg" align="center">
								<td colspan="23">NO RECORDS FOUND</td>
							</tr>
						</logic:empty>
				</table>
				
			
	</div>
</html:form>
</div>
</BODY>
</html:html>
