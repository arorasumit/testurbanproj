<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.ibm.ioes.npd.beans.PerformanceReportBean"%>
<%@page import="com.ibm.ioes.npd.hibernate.beans.PerformanceReportDto;"%>
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
	
	if(CheckNumeric(myform.searchDeviation,"Deviation")==false)
	{
		return false;
	}

	if(ValidateTextField(myform.productManager,'<%=request.getContextPath()%>',"Project Manager")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.projectName,'<%=request.getContextPath()%>',"Project Description")==false)
	{
		return false;
	}
	
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	document.searchForm.action="<%=request.getContextPath()%>/PerformanceReport.do?method=initPerformanceReport";
	
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
	
	if(CheckNumeric(myform.searchDeviation,"Deviation")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.productManager,'<%=request.getContextPath()%>',"Project Manager")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.projectName,'<%=request.getContextPath()%>',"Project Name")==false)
	{
		return false;
	}
	document.searchForm.action="<%=request.getContextPath()%>/PerformanceReport.do?method=initPerformanceReport";
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
	
	if(CheckNumeric(myform.searchDeviation,"Deviation")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.productManager,'<%=request.getContextPath()%>',"Project Manager")==false)
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

	document.searchForm.action="<%=request.getContextPath()%>/PerformanceReport.do?method=initPerformanceReport";
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
	
	if(CheckNumeric(myform.searchDeviation,"Deviation")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.productManager,'<%=request.getContextPath()%>',"Project Manager")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.projectName,'<%=request.getContextPath()%>',"Project Name")==false)
	{
		return false;
	}
	
	document.searchForm.action="<%=request.getContextPath()%>/PerformanceReport.do?method=viewExportToExcel";
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
<html:form action="/PerformanceReport" styleId="searchForm" method="post">
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
				<table width="100%"  cellspacing="0" cellpadding="0" align="center" class="borderTab">
					<tr>
						<td><img src="npd/Images/tabLeft.gif"></td>
						<td background="npd/Images/tabbg.gif" class="tabHeading" width="25%">Performance Report</td>
						<td><img src="npd/Images/tabRight.gif"></td>
						<td align="left" width="70%">
							<font size="1"> 
								<html:select property="dateFilter" styleId="Id_searchDateFilter" styleClass="tabledata">
									<html:option value="0">--Select a Date--</html:option>
									<html:option value="plannedLaunchDate">Planned Launch Date</html:option>
									<html:option value="actualLaunchDate">Actual Launch Date</html:option>
								</html:select> 
								From Date<html:text property="fromDate" size="10" readonly="true" styleId="dateFrom" styleClass="tabledata"/>
								<img src="npd/Images/cal.gif" width="16" height="16" border="0" alt="Pick a date" onclick="scwShow(scwID('fromDate'),event);">
								To Date<html:text property="toDate" size="10" readonly="true" styleId="dateTo" styleClass="tabledata"/>
								<font size="1">
								<img src="npd/Images/cal.gif" width="16" height="16" border="0" alt="Pick a date" onclick="scwShow(scwID('toDate'),event);"></font>
							</font> 	
							
						</td>
						<td width="4%"><img src="npd/Images/zero.gif" width="15" height="1"></td>
						<td align="right" style="padding-right:10px;" width="20%"><a href="#"><img onclick="searchSubmit();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;</td>
						<td align="right" style="padding-right:10px;" width="36%">
							<logic:equal value="1" name="performanceReportBean" property="checkRptData">
								<img id="excelImage" src="<%=request.getContextPath()%>/Images/excel.gif" border="0" onclick="javascript:exportToExcel();" style="visibility: visible" height="15"/>								
							</logic:equal>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

	<bean:define id="formBean" name="performanceReportBean"></bean:define>
	<html:hidden name="formBean" property="pagingSorting.sortByColumn"/>
	<html:hidden name="formBean" property="pagingSorting.sortByOrder"/>
	<html:hidden name="formBean" property="pagingSorting.pageNumber"/>
	<input type="hidden" name="method" value="initPerformanceReport"/>
	<html:hidden property="projectID" />
	<html:hidden property="reportID" value="3"/>
	<table width="100%" border="2" class="tabledata" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td width="70%">
				<div style="overflow:scroll;height:370px;width:100%" class="scroll">
					<table width="100%" border="1" class="tabledata" cellpadding="0" cellspacing="0">
							<tr>
								<td colspan="15" class="tabledata" >
				  					<bean:define id="pagingBean" name="formBean"></bean:define>
									<%  String  currPageNumber =String.valueOf(((PerformanceReportBean)pagingBean).getPagingSorting().getPageNumber());
										String  maxPageNumber =String.valueOf(((PerformanceReportBean)pagingBean).getPagingSorting().getMaxPageNumber());
									%>
									<jsp:include flush="true" page="html-jsp/commons/paging.jsp">
										<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
										<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
									</jsp:include>
								</td>
							</tr>	
							<tr bgcolor="#FF9255" >	
								<th background="#FF9255">S.No</th>
								<th background="#FF9255"><a href="#" onclick="sortSubmit('projectManager')">Product Manager</th>
								<th background="#FF9255"><a href="#" onclick="sortSubmit('projectID')">Project ID</th>
								<th background="#FF9255"><a href="#" onclick="sortSubmit('projectName')">Project Description</th>
								<th background="#FF9255"><a href="#" onclick="sortSubmit('plndLaunchDate')">Planned Launched Date</th>
								<th background="#FF9255"><a href="#" onclick="sortSubmit('actLaunchDate')">Actual Launched Date</th>
								<th background="#FF9255"><a href="#" onclick="sortSubmit('deviation')">Deviation(In Days)</th>
								<th background="#FF9255">No of Days in Project</th>
								<th background="#FF9255"><a href="#" onclick="sortSubmit('projectStatus')">Project Status</th>
							</tr>
							<tr bgcolor="#FF9255" class="rptHeader">	
								<th background="#FF9255">&nbsp;</th>
								<th background="#FF9255"><html:text name="performanceReportBean" property="productManager" maxlength="20"/></th>
								<th background="#FF9255"><html:text name="performanceReportBean" property="searchProjectID" size="5" maxlength="5"/></th>
								<th background="#FF9255"><html:text name="performanceReportBean" property="projectName" maxlength="20"/></th>
								<th background="#FF9255">&nbsp;</th>
								<th background="#FF9255">&nbsp;</th>
								<th background="#FF9255">
									<html:select name="performanceReportBean" property="searchFilter">
										<html:option value="EQUAL">=</html:option>
										<html:option value="GREATER">></html:option>
										<html:option value="GEQUAL">>=</html:option>
										<html:option value="LESS"><</html:option>
										<html:option value="LEQUAL"><=</html:option>
									</html:select>
									<html:text name="performanceReportBean" property="searchDeviation" size="5" maxlength="5"/>
								</th>
								<th background="#FF9255">&nbsp;</th>
								<td background="#FF9255">
									<html:select name="performanceReportBean" property="searchProjectStatus">
										<html:option value="-1">ALL</html:option>
										<html:option value="0">CLOSED</html:option>
										<html:option value="1">OPEN</html:option>
										<html:option value="2">DRAFT</html:option>
									</html:select>
								</td>
							</tr>
							<%int sno;
							sno=0;
							int newsno=((PerformanceReportBean)pagingBean).getPagingSorting().getSerialNoStart();
							%> 
							<logic:notEmpty name="listPerformanceReport">							
							<logic:iterate id="row"  name="listPerformanceReport" indexId="index1">
								<%sno=sno+1;%>
								<c:if test="${index1%2==0}">
								<tr class="redBg" align="center">
									<td><%=newsno%></td>
									<td><bean:write name='row' property="productManager" /></td>
									<td><bean:write name='row' property="projectID" /></td>
									<td><bean:write name='row' property="projectName" /></td>
									<td><bean:write name='row' property="plndLaunchDate" /></td>
									<td><bean:write name='row' property="actLaunchDate" /></td>
									<td><bean:write name='row' property="deviation" /></td>
									<td><bean:write name='row' property="daysInProject" /></td>
									<td>
										<logic:equal value="0" name='row' property="projectStatus">CLOSED</logic:equal>
										<logic:equal value="1" name='row' property="projectStatus">OPEN</logic:equal>
										<logic:equal value="2" name='row' property="projectStatus">DRAFT</logic:equal>
									</td>
								</tr>
							</c:if>
							<c:if test="${index1%2!=0}">
								<tr class="alterRedBg" align="center">
									<td><%=newsno%></td>
									<td><bean:write name='row' property="productManager" /></td>
									<td><bean:write name='row' property="projectID" /></td>
									<td><bean:write name='row' property="projectName" /></td>
									<td><bean:write name='row' property="plndLaunchDate" /></td>
									<td><bean:write name='row' property="actLaunchDate" /></td>
									<td><bean:write name='row' property="deviation" /></td>
									<td><bean:write name='row' property="daysInProject" /></td>
									<td>
										<logic:equal value="0" name='row' property="projectStatus">CLOSED</logic:equal>
										<logic:equal value="1" name='row' property="projectStatus">OPEN</logic:equal>
										<logic:equal value="2" name='row' property="projectStatus">DRAFT</logic:equal>
									</td>
								</tr>
							</c:if>
							<%newsno=newsno+1;%>
							</logic:iterate>
						</logic:notEmpty>
						<logic:empty name="listPerformanceReport">
							<tr class="alterRedBg" align="center">
								<td colspan="9">NO RECORDS FOUND</td>
							</tr>
						</logic:empty>
					</table>
			</div>
			</td>
		</tr>
	</table>
	
</html:form>
</div>
</BODY>
</html:html>
