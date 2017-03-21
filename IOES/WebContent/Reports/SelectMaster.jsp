<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- [001] Gunjan Singla  added a method populate report usage details -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic"
	prefix="logic"%>

<%@page import="com.ibm.ioes.beans.ReportsBean"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>Master- Report</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
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
<script language="javascript" type="text/javascript">

var counter = 1;

var userId = '<%=request.getAttribute("userId")%>';
var interfaceId = '<%=request.getParameter("interfaceId")%>';
var actionType;
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	showLayer();
	myForm.submit();
}

function pagingSubmit(val)
{

	myform=document.getElementById('searchForm');
	myform.toExcel.value='false';
	//setFormBean(myform);
	//myform.reset();

	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.method.value='viewMasterHistory';	
	showLayer();
	myform.submit();
}

function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	myform.toExcel.value='false';
	//setFormBean(myform);
	//myform.reset();
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
	myform.method.value='viewMasterHistory';	
	showLayer();
	myform.submit();
}


function popitup(url) 
{
	if(url=="SelectAccount")
	{
		var path = "<%=request.getContextPath()%>/bcpAddress.do?method=fetchAccount";		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
	}
	return false;
}

/*function searchClick()
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";
	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewBCPList";
	myForm.submit(); 
	
} */

function exportToExcel()
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='true';
	
	var excelName= '<%= AppConstants.ACTION_EXCEL %>';
	var jsData =	{
		userId:userId,
		interfaceId:interfaceId,
		actionType:excelName
		
	};		
    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	jsonrpc.processes.populateReportUsageDetails(jsData);  
	
	if(myForm.selectedMaster.value==0){
	
	alert("Please Select a Master for History");
	return;
	
	}
	myForm.masterValue.value=myForm.selectedMaster.value;
	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewMasterHistory&inExcel=true";
	myForm.submit();
	
}
function fnViewMasterHistory()
{
	var myForm=document.getElementById('searchForm');
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";
	if(myForm.selectedMaster.value==0){
	
	alert("Please Select a Master for History");
	return;
	
	}
	myForm.masterValue.value=myForm.selectedMaster.value;
	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewMasterHistory";
	showLayer();
	myForm.submit();



}


path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body>
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<html:form action="/reportsAction" styleId="searchForm" method="post">
	<bean:define id="formBean" name="reportsBean"></bean:define>
	<html:hidden property="toExcel" />
	<html:hidden property="pagingSorting.sortByColumn" />
	<html:hidden property="pagingSorting.sortByOrder" />
	<html:hidden property="pagingSorting.pageNumber" />
	<html:hidden property="masterValue" />

	<input type="hidden" name="method" />
	<table border="0" align="center" cellpadding="0" cellspacing="0"
		width="98%">
		<tr align="left">
			<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
		</tr>
	</table>

	<div border="1" class="head"><span>MASTER REPORT</span></div>
	<div border="1" class="error" align="center"><logic:present
		name="validation_errors">
		<logic:iterate id="error_row" name="validation_errors" scope="request">
			<font color="red"><bean:write name="error_row" /></font>
			<BR>
		</logic:iterate>
	</logic:present> <html:messages id="message" message="true">
		<li><font color="red"><bean:write name="message" /></font></li>
	</html:messages></div>
	<fieldset width="100%" border="1" align="center" class="border3">
	<legend><b>Search</b></legend>
	<table border="0" cellspacing="0" align="center" cellpadding="0"
		style="margin-top:7px">
		<tr>
			<td align="Center">Select Master</td>
			<td align="Center"><html:select property="selectedMaster"
				styleId="selectedMaster_1" styleClass="inputBorder1"
				style="width:150px;">
				<html:option value="0">--Select--</html:option>
				<html:option value="1">Product Configurator</html:option>
				<html:option value="2">Change Type</html:option>

			</html:select></td>
			<td align="center">
			<div class="searchBg" style="margin-right:10px;font-size:11px;"><a
				href="#" title="search" onClick="javascript:fnViewMasterHistory();">Search</a></div>
			<div class="searchBg" style="margin-right:10px;font-size:11px;"><a
				href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div>
			</td>

		</tr>
	</table>

	<br />
	</fieldset>
	<br />

	

		<fieldset width="100%" border="1" align="center" class="border3">
		<table width="100%">
			<tr>
				<td>
				<%
							String currPageNumber = String.valueOf(((ReportsBean) formBean)
							.getPagingSorting().getPageNumber());
					String maxPageNumber = String.valueOf(((ReportsBean) formBean)
							.getPagingSorting().getMaxPageNumber());
				%> <jsp:include flush="true" page="../paging.jsp">
					<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
					<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
				</jsp:include></td>
			</tr>
		</table>
		<table width="100%" border="1" cellpadding="0" cellspacing="0"
			class="tab2">

			<tr align="Center">
				<td align="center" style="font-size:9px"><b>Master Name</b></td>
				<td align="center" style="font-size:9px"><b>Column Name </b></td>
				<td align="center" style="font-size:9px"><b>Old Values</b></td>
				<td align="center" style="font-size:9px"><b>New Values</b></td>
				<td align="center" style="font-size:9px"><b>Operation Name
				</b></td>
				<td align="center" style="font-size:9px"><b>Modified Date
				</b></td>
				<td align="center" style="font-size:9px"><b>Modified By</b></td>
				<td align="center" style="font-size:9px"><b>
					<logic:present name="masterHistoryList" scope="request">
				<logic:empty name="masterHistoryList">
				Attribute ID
				</logic:empty>
				<logic:notEmpty name="masterHistoryList">
				<a href="#" onclick="sortSubmit('attributeID')">Attribute ID</a>
				</logic:notEmpty>
				</logic:present>
				</b></td>
				

			</tr>
		<logic:present name="masterHistoryList" scope="request">
			<logic:empty name="masterHistoryList">
				<tr>
					<td colspan="8" align="center" style="font-size:10px;color:red;"><strong>No
					Records Found</strong></td>
				</tr>
			</logic:empty>
			<logic:notEmpty name="masterHistoryList" scope="request">
				<logic:iterate id="row" name="masterHistoryList" indexId="recordId">
					<%
								String classType = null;
								if (recordId.intValue() % 2 == 0) {
							classType = "lightBg";
								} else {
							classType = "normal";
								}
					%>
					<tr class="<%=classType%>">
						<logic:equal value="TSERVICETYPEDETAIL" name="row"
							property="masterName">
							<td align="left" style="font-size:9px" width="5%">Product
							Configurator Master</td>
						</logic:equal>
						<logic:equal value="TCHANGETYPE_MASTER" name="row"
							property="masterName">
							<td align="left" style="font-size:9px" width="5%">Change
							Type Master</td>
						</logic:equal>
						<td align="left" style="font-size:9px" width="14%"><bean:write
							name="row" property="columnName" /></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write
							name="row" property="oldValues" /></td>
						<td align="left" style="font-size:9px" width="10%"><bean:write
							name="row" property="newValues" /></td>

						<logic:equal value="U" property="operationName" name="row">
							<td align="left" style="font-size:9px" width="5%">Updated</td>
						</logic:equal>
						<logic:equal value="I" property="operationName" name="row">
							<td align="left" style="font-size:9px" width="5%">Inserted</td>
						</logic:equal>
						<td align="left" style="font-size:9px" width="14%"><bean:write
							name="row" property="modifiedDate" /></td>
						<td align="left" style="font-size:9px" width="5%"><bean:write
							name="row" property="modifiedBy" /></td>
						<td align="left" style="font-size:9px" width="10%"><bean:write
							name="row" property="attribiuteId" /></td>

					</tr>
				</logic:iterate>
			</logic:notEmpty>
			</logic:present>
		</table>
	

</html:form>
</body>
</html>
