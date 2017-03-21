<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.npd.beans.UnMappedEmployeeBean;"%>
<html:html>
<head>
<title>ChangeOrder Workflow</title>
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">
<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/chrome.js"></script>
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
<script type="text/javascript">
function searchSubmit(flag)
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);	
	
	if(CheckNumeric(myform.employeeId,"Employee ID")==false)
	{
		return false;
	}	

	if(ValidateTextField(myform.employeeName,'<%=request.getContextPath()%>',"Employee Name")==false)
	{
		return false;
	}	
	
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	document.searchForm.action="<%=request.getContextPath()%>/UnMappedResource.do?method=getUnMappedEmployee";
	showLayer();
	myform.submit();
}

function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.pageNumber"].value=val;
	
	if(CheckNumeric(myform.employeeId,"Employee ID")==false)
	{
		return false;
	}	

	if(ValidateTextField(myform.employeeName,'<%=request.getContextPath()%>',"Employee Name")==false)
	{
		return false;
	}	
	
	document.searchForm.action="<%=request.getContextPath()%>/UnMappedResource.do?method=getUnMappedEmployee";
	showLayer();
	myform.submit();
}

function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	if(CheckNumeric(myform.employeeId,"Employee ID")==false)
	{
		return false;
	}	

	if(ValidateTextField(myform.employeeName,'<%=request.getContextPath()%>',"Employee Name")==false)
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

	document.searchForm.action="<%=request.getContextPath()%>/UnMappedResource.do?method=getUnMappedEmployee";
	showLayer();
	myform.submit();
}

function exportToExcel()
{
	myform=document.getElementById('searchForm');
	if(CheckNumeric(myform.employeeId,"Employee ID")==false)
	{
		return true;
	}	

	if(ValidateTextField(myform.employeeName,'<%=request.getContextPath()%>',"Employee Name")==false)
	{
		return true;
	}	
	//document.searchForm.action="<%=request.getContextPath()%>/UnMappedResource.do?method=getUnMappedEmployee";
	document.searchForm.action="<%=request.getContextPath()%>/UnMappedResource.do?method=viewExportToExcel";
	document.searchForm.submit();
}
</script>

</head>
<body>
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait While Data Uploads..</b></font>
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

<html:form action="/UnMappedResource" method="post" styleId="searchForm">
	<table width="80%" border="0" cellpadding="0" cellspacing="0"
		class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
			<table width="80%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr>
					<td width="5"><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" width="367">UnMapped Employee Report</td>
					<td width="9"><img src="npd/Images/tabRight.gif"></td>
					<td align="left" width="703"><font size="1"> </font></td>
					<td width="62" align="right" style="padding-right:10px;"><a
						href="#"><img onclick="searchSubmit();"
						src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;</td>

					<td><logic:equal value="1" name="unMappedEmployeeBean"
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
	<bean:define id="formBean" name="unMappedEmployeeBean"></bean:define>
	<html:hidden name="formBean" property="pagingSorting.sortByColumn" />
	<html:hidden name="formBean" property="pagingSorting.sortByOrder" />
	<html:hidden name="formBean" property="pagingSorting.pageNumber" />
	<html:hidden property="methodName" />
	<html:hidden property="reportID" value="6" />
	<input type="hidden" name="method" value="getUnMappedEmployee" />
	<table width="70%" border="1" class="tabledata" cellpadding="3"
		cellspacing="1" align="center">
		<tr>
			<td colspan="15" class="tabledata"><bean:define id="pagingBean"
				name="unMappedEmployeeBean"></bean:define> <%
 			String currPageNumber = String
 			.valueOf(((UnMappedEmployeeBean) pagingBean)
 			.getPagingSorting().getPageNumber());
 			String maxPageNumber = String
 			.valueOf(((UnMappedEmployeeBean) pagingBean)
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
					<td><a href="#" onclick="sortSubmit('empID')">Employee
					ID</td>
					<td><a href="#" onclick="sortSubmit('empName')">Employee
					Name</td>
				</tr>
				<tr bgcolor="#FF9255" class="rptHeader">
					<td background="#FF9255"></td>
					<td background="#FF9255"><html:text name="unMappedEmployeeBean"
						property="employeeId" maxlength="5" size="5"/></td>
					<td background="#FF9255"><html:text name="unMappedEmployeeBean"
						property="employeeName" maxlength="20"/></td>
				</tr>
				<%
					int sno;
					sno = 0;
					int newsno=((UnMappedEmployeeBean)pagingBean).getPagingSorting().getSerialNoStart();
				%>
				<logic:empty name="listEmpResource">
					<tr class="rptHeader">
						<td colspan="3" background="#FF9255">No Records Found</td>
					</tr>
				</logic:empty>
				<logic:notEmpty name="listEmpResource">
					<logic:iterate id="row" name="listEmpResource" indexId="index1">
						<%
						sno = sno + 1;
						%>
						<c:if test="${index1%2==0}">
							<tr class="redBg" align="center">
								<td><%=newsno%></td>
								<td><bean:write name='row' property="employeeId" /></td>
								<td><bean:write name='row' property="employeeName" /></td>
							</tr>
						</c:if>
						<c:if test="${index1%2!=0}">
							<tr class="alterRedBg" align="center">
								<td><%=newsno%></td>
								<td><bean:write name='row' property="employeeId" /></td>
								<td><bean:write name='row' property="employeeName" /></td>
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
</body>
</html:html>
