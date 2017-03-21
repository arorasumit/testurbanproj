<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.ibm.ioes.npd.beans.NPDResourceListBean"%>

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
	if(CheckNumeric(myform.searchProjectID,"Project ID")==false)
	{
		return false;
	}	

	if(ValidateTextField(myform.projectName,'<%=request.getContextPath()%>',"Project Name")==false)
	{
		return false;
	}	
	
	if(ValidateTextField(myform.roleName,'<%=request.getContextPath()%>',"Role Name")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.empName,'<%=request.getContextPath()%>',"Employee Name")==false)
	{
		return false;
	}
	
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	document.searchForm.action="<%=request.getContextPath()%>/NPDResourceListReport.do?method=initNPDResourceList";
	
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
	
	if(ValidateTextField(myform.roleName,'<%=request.getContextPath()%>',"Role Name")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.empName,'<%=request.getContextPath()%>',"Employee Name")==false)
	{
		return false;
	}
	
	document.searchForm.action="<%=request.getContextPath()%>/NPDResourceListReport.do?method=initNPDResourceList";
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
	
	if(ValidateTextField(myform.roleName,'<%=request.getContextPath()%>',"Role Name")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.empName,'<%=request.getContextPath()%>',"Employee Name")==false)
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

	document.searchForm.action="<%=request.getContextPath()%>/NPDResourceListReport.do?method=initNPDResourceList";
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
	
	if(ValidateTextField(myform.roleName,'<%=request.getContextPath()%>',"Role Name")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.empName,'<%=request.getContextPath()%>',"Employee Name")==false)
	{
		return false;
	}
	
	document.searchForm.action="<%=request.getContextPath()%>/NPDResourceListReport.do?method=viewExportToExcel";
	document.searchForm.submit();
}
</script>

</head>
<body onload="javascript:document.body.click();">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait While Data Uploads..</b></font>
    <br /><br /><br />
</div>
<div class="error" align="center">
	<logic:present name="validation_errors">
		<logic:iterate id="error_row" name="validation_errors" scope="request">
				<font color="red"><bean:write name="error_row" /></font><BR>
		</logic:iterate>
	</logic:present>
</div>
<html:form action="/NPDResourceListReport" styleId="searchForm"
	method="post">
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
		class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr>
					<td width="5"><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" width="367">NPD
					Resource List Report</td>
					<td width="9"><img src="npd/Images/tabRight.gif"></td>
					<td align="left" width="703"><font size="1"> </font></td>
					<td width="62" align="right" style="padding-right:10px;"><a
						href="#"><img onclick="searchSubmit();"
						src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;</td>

					<td><logic:equal value="1" name="npdResourceListBean"
						property="checkRptData">
						<img id="excelImage"
							src="<%=request.getContextPath()%>/Images/excel.gif" border="0"
							onclick="javascript:exportToExcel();" style="visibility: visible"
							height="15" />
					</logic:equal></td>

				</tr>
			</table>
			</td>
		</tr>
	</table>

	<bean:define id="formBean" name="npdResourceListBean"></bean:define>
	<html:hidden name="formBean" property="pagingSorting.sortByColumn" />
	<html:hidden name="formBean" property="pagingSorting.sortByOrder" />
	<html:hidden name="formBean" property="pagingSorting.pageNumber" />
	<html:hidden property="methodName" />
	<html:hidden property="reportID" value="1" />
	<input type="hidden" name="method" value="initNPDResourceList" />
	<html:hidden property="projectID" />
	<table width="100%" border="1" class="tabledata" cellpadding="3"
		cellspacing="1" align="center">
		<tr>
			<td colspan="15" class="tabledata"><bean:define id="pagingBean"
				name="formBean"></bean:define> <%
 			String currPageNumber = String
 			.valueOf(((NPDResourceListBean) pagingBean)
 			.getPagingSorting().getPageNumber());
 	String maxPageNumber = String
 			.valueOf(((NPDResourceListBean) pagingBean)
 			.getPagingSorting().getMaxPageNumber());
 %> <jsp:include flush="true" page="html-jsp/commons/paging.jsp">
				<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
				<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
			</jsp:include></td>
		</tr>
		<tr>
			<td>
			<div style="overflow:scroll;height:370px;width:100%" class="scroll">
			<table align="center" cellSpacing="1" cellPadding="4" border="0"
				class="scroll" id="tblRollList" width="100%">
				<tr valign="middle" class="rptHeader">
					<td>S.No</td>
					<td><a href="#" onclick="sortSubmit('projectID')">Project
					ID</td>
					<td><a href="#" onclick="sortSubmit('projectName')">Project
					Name</td>
					<td><a href="#" onclick="sortSubmit('roleName')">Role
					Name</td>
					<td><a href="#" onclick="sortSubmit('empName')">Employee
					Name</td>
					<td><a href="#" onclick="sortSubmit('projectStatus')">Project 
					Status</td>
				</tr>
				<tr bgcolor="#FF9255" class="rptHeader">
					<td background="#FF9255"></td>
					<td background="#FF9255"><html:text name="npdResourceListBean"
						property="searchProjectID" maxlength="5" size="5"/></td>
					<td background="#FF9255"><html:text name="npdResourceListBean"
						property="projectName" maxlength="20"/></td>
					<td background="#FF9255"><html:text name="npdResourceListBean"
						property="roleName" maxlength="20"/></td>
					<td background="#FF9255"><html:text name="npdResourceListBean"
						property="empName" maxlength="20"/></td>
					<td background="#FF9255">
						<html:select name="npdResourceListBean" property="searchProjectStatus">
							<html:option value="-1">ALL</html:option>
							<html:option value="1">OPEN</html:option>
							<html:option value="0">CLOSED</html:option>
							<html:option value="2">DRAFT</html:option>
						</html:select>
					</td>
				</tr>
				<%
					int sno;
					sno = 0;
					int newsno=((NPDResourceListBean)pagingBean).getPagingSorting().getSerialNoStart();
				%>
				<logic:empty name="listNPDResource">
					<tr class="redBg" align="center">
						<td colspan="6">NO RECORDS FOUND</td>
					</tr>
				</logic:empty>
				<logic:notEmpty name="listNPDResource">
					<logic:iterate id="row" name="listNPDResource" indexId="index1">
						<%
						sno = sno + 1;
						%>
						<c:if test="${index1%2==0}">
							<tr class="redBg" align="center">
								<td><%=newsno%></td>
								<td><bean:write name='row' property="projectID" /></td>
								<td><bean:write name='row' property="projectName" /></td>
								<td><bean:write name='row' property="roleName" /></td>
								<td><bean:write name='row' property="empName" /></td>
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
								<td><bean:write name='row' property="projectID" /></td>
								<td><bean:write name='row' property="projectName" /></td>
								<td><bean:write name='row' property="roleName" /></td>
								<td><bean:write name='row' property="empName" /></td>
								<td>
									<logic:equal value="0" name='row' property="projectStatus">CLOSED</logic:equal>
									<logic:equal value="1" name='row' property="projectStatus">OPEN</logic:equal>
									<logic:equal value="2" name='row' property="projectStatus">DRAFT</logic:equal>
								</td>
							</tr>
						</c:if>
						<%newsno=newsno+1; %>
					</logic:iterate>
				</logic:notEmpty>
			</table>
			</div>
			</td>
		</tr>
	</table>
</html:form>
</div>
</BODY>
</html:html>
