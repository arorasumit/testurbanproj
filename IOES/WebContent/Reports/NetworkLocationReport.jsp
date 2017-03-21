<!-- [001] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.ReportsBean"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>Network Location - Report</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
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
	myform.method.value='viewNetworkLocsList';
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
	myform.method.value='viewNetworkLocsList';	
    showLayer();
	myform.submit();
}

function searchClick()
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";
	
	
	var searchName= '<%= AppConstants.ACTION_SEARCH %>';
	var jsData =	{
		userId:userId,
		interfaceId:interfaceId,
		actionType:searchName
		
	};		
    
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	jsonrpc.processes.populateReportUsageDetails(jsData);  
	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewNetworkLocsList";
	showLayer();
	myForm.submit(); 
	
}

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
	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewNetworkLocsList";
	myForm.submit();
	
}



function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.networkLocationIdStr.value='' ; 
	myForm.contactNameStr.value='' ;
}

path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body >
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/reportsAction" styleId="searchForm" method="post">
		<bean:define id="formBean" name="reportsBean"></bean:define>
		<html:hidden property="interfaceId"/>
		<html:hidden property="toExcel"/>
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="pagingSorting.sortByOrder"/>
		<html:hidden property="pagingSorting.pageNumber"/>
		<input type="hidden" name="method" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="left">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> <span>NETWORK LOCATION REPORT</span> </div>
		<div border="1" class="error" align="center">
			<logic:present name="validation_errors">
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>
			</logic:present>
			<html:messages id="message" message="true">
				<li><font color="red"><bean:write name="message"/></font></li>
			</html:messages>
		</div>
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Search</b></legend>
				<table border="0" cellspacing="0"  align="center" cellpadding="0"  style="margin-top:7px" width="100%">
					<tr>
							<td width="150px"/>
							<td align="right" style="font-size:9px">Network Location Code:</td>
							<td align="left">
								<html:text  property="networkLocationIdStr" styleClass="inputBorder1" style="width:50px;float:left;" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
							</td>
							<td width="10px"/>
							<td align="center" style="font-size:9px;" nowrap>Contact Name:</td>
							<td><html:text  property="contactNameStr" styleClass="inputBorder1" style="width:150px;" maxlength="200" onblur="if( trim(this.value).length>0){ return ValidateTextField(this,path,'Contact Name')};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text></td>
							<td align="left">
								<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
							</td>
							<td width="50px"/>
							<td align="right" width="70px"><div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div></td>
							<logic:present name="customerList" scope="request">
							<logic:notEmpty  name="customerList" scope="request"> 
							<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
							</logic:notEmpty>
							<logic:empty  name="customerList" scope="request"> 		
							<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a style="cursor:default;">Export To Excel</a></div></td>
							</logic:empty>
							</logic:present>
					</tr>
					<tr height="5px"/>
					<tr>
						<td colspan="10" style="color: red;font-size: xx-small;" align="left" valign="bottom">*To see ALL Network Locations, click on Search without entering any search criteria</td>
					</tr>					
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Network Location Details</b></legend>
			<br/>
			<table width="100%">
				<tr>
					<td>
						<%	String currPageNumber =String.valueOf(((ReportsBean)formBean).getPagingSorting().getPageNumber());
							String 	maxPageNumber =String.valueOf(((ReportsBean)formBean).getPagingSorting().getMaxPageNumber());
						%>
						<jsp:include flush="true" page="../paging.jsp">
							<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
							<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
						</jsp:include>
					</td>
				</tr>
			</table>
			
			<div class="scrollWithAutoScroll1" class="head"  style="height:350px;width:99% " >
			<table style="display:block;overflow:auto;" class="tab2" width="99%" border="1" align="center" id='tblNetworkLocReport'>
				<tr>
					<td align="center" style="font-size:9px"><b>
						<logic:present name="customerList" scope="request">
							<logic:notEmpty  name="customerList" scope="request"> 
								<a href="#" onclick="sortSubmit('LOCATION_CODE')">Network Location Code</a>
							</logic:notEmpty>
							<logic:empty  name="customerList" scope="request">
								Network Location Code
							</logic:empty>
						</logic:present>
					</b></td>
					<td align="center" style="font-size:9px"><b>Title</b></td>
					<td align="center" style="font-size:9px"><b>First Name</b></td>
					<td align="center" style="font-size:9px"><b>Last Name</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Telephone Number</b></td>
					<td align="center" style="font-size:9px"><b>E-Mail</b></td>
					<td align="center" style="font-size:9px"><b>Fax</b></td>							
					<td align="center" style="font-size:9px"><b>Address1</b></td>
					<td align="center" style="font-size:9px"><b>Address2</b></td>
					<td align="center" style="font-size:9px"><b>Address3</b></td>
					<td align="center" style="font-size:9px"><b>Address4</b></td>
					<td align="center" style="font-size:9px"><b>City</b></td>
					<td align="center" style="font-size:9px"><b>State</b></td>
					<td align="center" style="font-size:9px"><b>Postal Code</b></td>
					<td align="center" style="font-size:9px"><b>Country</b></td>
				</tr>
				<logic:present name="customerList" scope="request">
					<logic:notEmpty  name="customerList" scope="request"> 					
						<logic:iterate id="row" name="customerList" indexId="recordId">
							<%
								String classType=null;
								if(recordId.intValue() % 2 == 0)
								{
								classType="lightBg";
								}
								else
								{
								classType="normal";				
								}	
							%>				
							<tr class="<%=classType%>">
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="networkLocationId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="title"/></td>
								<td align="left" style="font-size:9px" width="10%"><bean:write  name="row" property="firstname"/></td>
								<td align="left" style="font-size:9px" width="10%"><bean:write  name="row" property="lastName"/></td>														
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="telephonePhno"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="email_Id"/></td>
								<td align="left" style="font-size:9px" width="10%"><bean:write  name="row" property="fax"/></td>
								<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address1"/></td>
								<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address2"/></td>
								<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address3"/></td>
								<td align="left" style="font-size:9px" width="9%"><bean:write  name="row" property="address4"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cityName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="stateName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="postalCode"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="countryName"/></td>
							</tr>
						</logic:iterate>		
					</logic:notEmpty>
					<logic:empty  name="customerList">
					   <tr align="center" >
						 <td colspan="15" align="center"><B><font color="red">No Records Found</font></B></td>
					   </tr>
					</logic:empty>	
				</logic:present>				
			</table>
			</div>
		</fieldset>
	</html:form>
</body>
</html>
