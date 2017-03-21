<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.beans.ProjectDetailedStatusReportBean"%>
<html:html>
<head>
<link rel="stylesheet" type="text/css" href="gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">

<script language="JavaScript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/scw.js"></script>
<script type="text/javascript" src="js/timepicker.js"></script>
<script language="javascript" src="js/utility.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>
<script type="text/javascript" src="js/inputColor.js"></script>

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

<script type="text/javascript" src="js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<script type="text/javascript">
// to check whether stage already exists in the database.
function searchSubmit(flag)
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);	
	if(CheckNumeric(myform.searchByProjectid,"Project ID")==false)
	{
		return false;
	}	

	if(ValidateTextField(myform.searchProjectName,'<%=request.getContextPath()%>',"Project Name")==false)
	{
		return false;
	}	
	
	if(ValidateTextField(myform.stageName,'<%=request.getContextPath()%>',"Stage Name")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.taskName,'<%=request.getContextPath()%>',"Task Name")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.roleName,'<%=request.getContextPath()%>',"Role Name")==false)
	{
		return false;
	}
	
	if(CheckNumeric(myform.delays,"Delays")==false)
	{
		return false;
	}	
	
	if(ValidateTextField(myform.docName,'<%=request.getContextPath()%>',"Document Name")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.docType,'<%=request.getContextPath()%>',"Document Type")==false)
	{
		return false;
	}
	
	
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	document.searchForm.action="<%=request.getContextPath()%>/projectDetailedStatusReport.do?method=viewProjectStatusDetail";
	
	showLayer();
	myform.submit();
}


function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	if(CheckNumeric(myform.searchByProjectid,"Project ID")==false)
	{
		return false;
	}	

	if(ValidateTextField(myform.searchProjectName,'<%=request.getContextPath()%>',"Project Name")==false)
	{
		return false;
	}	
	
	if(ValidateTextField(myform.stageName,'<%=request.getContextPath()%>',"Stage Name")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.taskName,'<%=request.getContextPath()%>',"Task Name")==false)
	{
		return false;
	}
	
	
	if(ValidateTextField(myform.roleName,'<%=request.getContextPath()%>',"Role Name")==false)
	{
		return false;
	}
	
	if(CheckNumeric(myform.delays,"Delays")==false)
	{
		return false;
	}	
	
	if(ValidateTextField(myform.docName,'<%=request.getContextPath()%>',"Document Name")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.docType,'<%=request.getContextPath()%>',"Document Type")==false)
	{
		return false;
	}

	myform.elements["pagingSorting.pageNumber"].value=val;
	document.searchForm.action="<%=request.getContextPath()%>/projectDetailedStatusReport.do?method=viewProjectStatusDetail";
	showLayer();
	myform.submit();
}
function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	if(CheckNumeric(myform.searchByProjectid,"Project ID")==false)
	{
		return false;
	}	

	if(ValidateTextField(myform.searchProjectName,'<%=request.getContextPath()%>',"Project Name")==false)
	{
		return false;
	}	
	
	if(ValidateTextField(myform.stageName,'<%=request.getContextPath()%>',"Stage Name")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.taskName,'<%=request.getContextPath()%>',"Task Name")==false)
	{
		return false;
	}
	
	
	
	if(ValidateTextField(myform.roleName,'<%=request.getContextPath()%>',"Role Name")==false)
	{
		return false;
	}
	
	if(CheckNumeric(myform.delays,"Delays")==false)
	{
		return false;
	}	
	
	if(ValidateTextField(myform.docName,'<%=request.getContextPath()%>',"Document Name")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.docType,'<%=request.getContextPath()%>',"Document Type")==false)
	{
		return false;
	}
	
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

	document.searchForm.action="<%=request.getContextPath()%>/projectDetailedStatusReport.do?method=viewProjectStatusDetail";
	showLayer();
	myform.submit();
}

function exportToExcel()
{
	myform=document.getElementById('searchForm');
		
	if(CheckNumeric(myform.searchByProjectid,"Project ID")==false)
	{
		return false;
	}	

	if(ValidateTextField(myform.searchProjectName,'<%=request.getContextPath()%>',"Project Name")==false)
	{
		return false;
	}	
	
	if(ValidateTextField(myform.stageName,'<%=request.getContextPath()%>',"Stage Name")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.taskName,'<%=request.getContextPath()%>',"Task Name")==false)
	{
		return false;
	}

	if(ValidateTextField(myform.roleName,'<%=request.getContextPath()%>',"Role Name")==false)
	{
		return false;
	}
	
	if(CheckNumeric(myform.delays,"Delays")==false)
	{
		return false;
	}	
	
	if(ValidateTextField(myform.docName,'<%=request.getContextPath()%>',"Document Name")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.docType,'<%=request.getContextPath()%>',"Document Type")==false)
	{
		return false;
	}
	
	myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	document.searchForm.action="<%=request.getContextPath()%>/projectDetailedStatusReport.do?method=viewExportToExcel";
	myform.submit();
}
function fnDownload(projectid,taskid,docname)
{
	document.searchForm.action="<%=request.getContextPath()%>/projectDetailedStatusReport.do?method=DownloadFile";
	myform=document.getElementById('searchForm');
	myform.elements["docProjectId"].value=projectid;
	myform.elements["docTaskId"].value=taskid;
	myform.elements["docDocName"].value=docname;
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
<html:form action="/projectDetailedStatusReport" method="post" styleId="searchForm">
	<input type="hidden" name="method" value="viewProjectStatusDetail">
<bean:define id="formBean" name="projectDetailedStatusReportBean"></bean:define>

<html:hidden name="formBean" property="pagingSorting.sortByColumn"/>
<html:hidden name="formBean" property="pagingSorting.sortByOrder"/>
<html:hidden name="formBean" property="pagingSorting.pageNumber"/>
<input type="hidden" name="method" value="viewProjectPlan"/>
<html:hidden name="formBean" property="searchProjectid"/>
<html:hidden property="reportID" value="5"/>

<html:hidden property="docProjectId"/>
<html:hidden property="docTaskId"/>
<html:hidden property="docDocName"/>

<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
			<table width="91%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr >
					<td width="5"><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" width="367">Project
					Detailed Status Report</td>
					<td width="9"><img src="npd/Images/tabRight.gif"></td><td align="left" width="571"><font size="1"> 
					</font></td>
					<td align="right" style="padding-right:10px;" width="38"><a href="#"><img
						onclick="searchSubmit();" src="npd/Images/search.gif" title="search"
						height="15"></a>&nbsp;</td>
					<td align="right" style="padding-right:10px;" width="6%">
						<logic:equal value="1" name="projectDetailedStatusReportBean" property="checkRptData">
							<img id="excelImage" src="<%=request.getContextPath()%>/Images/excel.gif" border="0" onclick="javascript:exportToExcel();" style="visibility: visible" height="15"/>								
						</logic:equal>
					</td>
				</tr>
			</table>
			</td>
	</tr>
</table>
				

<table cellSpacing="0" cellPadding="0" width="100%" border="0">
		 <tr>
			<td  class="tabledata" >
				  <bean:define id="pagingBean" name="formBean"></bean:define>
					<%  String  currPageNumber = String.valueOf(((ProjectDetailedStatusReportBean)pagingBean).getPagingSorting().getPageNumber());
						String  maxPageNumber = String.valueOf(((ProjectDetailedStatusReportBean)pagingBean).getPagingSorting().getMaxPageNumber());
					%>
					<jsp:include flush="true" page="html-jsp/commons/paging.jsp">
						<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
						<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
					</jsp:include>
			</td>
		</tr>	
</table>
		<div style="overflow:scroll;height:320px;width:100%"
									class="scroll">
		<table align="center" cellSpacing="1" cellPadding="4" border="0"
									class="tabledata" id="tblRollList" width="100%">	
				<tr valign="middle" class="rptHeader">
					<td align="left" nowrap>SNo.</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('projectID')">Project id</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('projectName')">Project Name</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('stageName')">Stage Name</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('taskName')">Task Name</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('taskStartDate')">Planned Task Start Date</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('taskEndDate')">Planned Task End Date</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('actualStartDate')">Actual Task Start Date</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('actualEndDate')">Actual Task End Date</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('projectStatus')">Project Status</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('roleName')">Role Name</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('delays')">Delay In Days</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('docName')">Document Name</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('docType')">Document Type</td>
				</tr>
				<tr valign="middle" class="rptHeader">
					<td align="left" nowrap>&nbsp;</td>
					<td align="left" nowrap><html:text  property="searchByProjectid"/></td>
					<td align="left" nowrap><html:text  property="searchProjectName"/></td>
					<td align="left" nowrap><html:text  property="stageName"/></td>
					<td align="left" nowrap><html:text  property="taskName"/></td>
					<td align="left" nowrap></td>
					<td align="left" nowrap></td>
					<td align="left" nowrap></td>
					<td align="left" nowrap></td>
					<td align="left" nowrap></td>
					<td align="left" nowrap><html:text  property="roleName"/></td>
					<td align="left" nowrap><html:text  property="delays"/></td>
					<td align="left" nowrap><html:text  property="docName"/></td>
					<td align="left" nowrap><html:text  property="docType"/></td>
				</tr>				
				<%int nSNo=0;
				int newsno=((ProjectDetailedStatusReportBean)pagingBean).getPagingSorting().getSerialNoStart();
				 %>
				<logic:empty name="listProjectPlan">
								<tr class="alterRedBg" align="center" >
									<td colspan="14" align="center" nowrap><B>No Record
									Found</td>
								</tr>
				</logic:empty>
				<logic:notEmpty name="listProjectPlan">
				<logic:present name="listProjectPlan" scope="request">
				<logic:iterate name="listProjectPlan" id="row" indexId="recordId" type="com.ibm.ioes.npd.hibernate.beans.ProjectDetailedStatusReportDto">
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
					<td nowrap="nowrap"><bean:write name="row" property="projectID" /></td>			
					<td nowrap="nowrap"><bean:write name="row" property="projectName" /></td>
					<td><bean:write name="row" property="stageName" /></td>
					<td><bean:write name="row" property="taskName" /></td>
					<td><bean:write name="row" property="taskStartDate" /></td>
					<td><bean:write name="row" property="taskEndDate" /></td>
					<td><bean:write name="row" property="actualStartDate" /></td>
					<td><bean:write name="row" property="actualEndDate" /></td>
					
					<logic:equal value="1" name="row" property="projectStatus">
					<td>OPEN</td>
					</logic:equal>
					<logic:equal value="0" name="row" property="projectStatus">
					<td>CLOSE</td>
					</logic:equal>
					<td><bean:write name="row" property="roleName" /></td>
					<td><bean:write name="row" property="delays" /></td>
					<td><%if(row.getDocName()!=null && !"".equals(row.getDocName()))
						{
					%>
								<a href="#" onclick="fnDownload(<bean:write name='row' property='projectID' />,<bean:write name='row' property='currentTaskId' />,'<bean:write name='row' property='docName' />')">
									<bean:write name="row" property="docName" /></a>
						<%} %>
						
							</td>
					<td><bean:write name="row" property="docType" /></td>
				</tr>
				<%newsno=newsno+1; %>
				</logic:iterate>	
				</logic:present>
					</logic:notEmpty>
			    </table>
			    </div>

</html:form>
</div>
</BODY>


</html:html>
