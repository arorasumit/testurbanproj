<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!--  [101010] Rampratap added from date and to date -->
<!--  [202020] Rampratap added from date and to date validation -->
<!--  [303030] Rampratap added from date and to date validation -->
<!-- [001] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.ReportsBean"%>
<%@page import="com.ibm.ioes.utilities.ApplicationFlags"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>

<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>LEPM Cancel Order Report</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ReportHeaderFreeze.js"></script>
<link href="./css/ReportHeaderFreeze.css" type="text/css" rel="stylesheet"> 
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
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
	//myform.reset();
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.method.value='viewLEPMOrderCancelReport';	
	showLayer();
	myform.submit();
}

function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	myform.toExcel.value='false';
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
	myform.method.value='viewLEPMOrderCancelReport';	
	showLayer();
	myform.submit();
}


function searchClick()
{
	myForm=document.getElementById('searchForm');
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
	//[202020] START
	var fromDate=myForm.canceldatefrom.value;
	var toDate=myForm.canceldateto.value;
		if((fromDate == null & toDate == null) 
		|| ( trim(fromDate)=="" & trim(toDate) == "")){
		alert("Please enter From Cancel Date and To Cancel Date!");
		return false;
		
	} 
	
	if(dateCompare(fromDate,toDate)==1)
			{			
				alert("From Date cannot be greater than To Date");
				return;
			}
			
	else {
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewLEPMOrderCancelReport";
		showLayer();
		myForm.submit(); 
	}
	//[202020] END
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
		//[303030]START
		var fromDate=myForm.canceldatefrom.value;
	var toDate=myForm.canceldateto.value;
	if((fromDate == null & toDate == null) 
		|| ( trim(fromDate)=="" & trim(toDate) == "")){
		alert("Please enter From Cancel Date and To Cancel Date!");
		return false;
		
	} 
	
	if(dateCompare(fromDate,toDate)==1)
			{			
				alert("From Date cannot be greater than To Date");
				return;
			}
			
	else {
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewLEPMOrderCancelReport&inExcel=true";
		showLayerExcel();
		myForm.submit();	
		}
		//[303030]END
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.canceldatefrom.value='' ; 
	myForm.canceldateto.value='' ;
	
	
}

function downloadDump(formId,fileName)
{
	myForm=document.getElementById(formId);
	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=getDumpFile&fileName="+fileName;
//	showLayer();
	
	var dumpname = '<%= AppConstants.ACTION_DUMP %>';
	var jsData =	{
		userId:userId,
		interfaceId:interfaceId,
		actionType:dumpname
		
	};		
    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	jsonrpc.processes.populateReportUsageDetails(jsData);  
	
	myForm.submit(); 
}

path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload="javascript:maxLimitChecker(document.getElementById('searchForm'),'<%=ApplicationFlags.ReportsExcelMaxSize%>','<%=Messages.getMessageValue("ReportsExcelMaxSizeMessage") %>');">
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/reportsAction" styleId="searchForm" method="post">
		<bean:define id="formBean" name="reportsBean"></bean:define>
		<html:hidden property="toExcel"/>
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="pagingSorting.sortByOrder"/>
		<html:hidden property="pagingSorting.pageNumber"/>
		<html:hidden property="interfaceId"/>
		<html:hidden property="isDumpAvailable"/>
		<html:hidden property="dumpFileName"/>	
		<html:hidden property="reportProcessForLimit"/>
		<html:hidden property="reportCurrentCount"/>
		
		<input type="hidden" name="fromPageSubmit" value="1"/>
		<input type="hidden" name="method" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="left">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> 
			
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>LEPM Cancel Order Report</span> 
			</td>
			<logic:present name="formBean" property="isDumpAvailable">
				<logic:equal name="formBean" property="isDumpAvailable" value="Y">
					<td align="right">
					<span style="text-align: right;">
					<a  href="#" title="Download Dump" onClick="javascript:downloadDump('searchForm','<bean:write name="formBean" property="dumpFileName"/>');">
						<font color="white">Download Dump</font>
					</a>&nbsp;&nbsp;
					</span>
					</td>
				</logic:equal>
			</logic:present>
			</tr></table>
					
			
			
			</div>	
		
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Search</b></legend>
				<table border="0"  align="center" style="margin-top:7px">
					<tr>
					<td width="90px"/>
						
						
					 <!-- <td align="right" style="font-size:9px;">Cancel date:</td>
						<td nowrap><html:text  property="canceldate" styleId="canceldate" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.canceldate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>  -->
						 <!--  [101010] START -->
						<td align="right" style="font-size:9px;">From Cancel date :</td>
						<td nowrap><html:text  property="canceldatefrom" styleId="canceldatefrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.canceldatefrom);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td> 
						<td align="right" style="font-size:9px;">To Cancel date:</td>
						<td nowrap><html:text  property="canceldateto" styleId="canceldateto" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.canceldateto);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td> 
						 <!--  [101010] END -->
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>						
							<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel"  onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
					</tr>
					</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>LEPM Cancel Order Report</b></legend>
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
			
			<div class="scrollWithAutoScroll1" class="head"  style="height:400px;width:100%; vertical-align: top;" >
			<table style="display:block;overflow:auto; vertical-align: top;" width="100%" border="1" align="center" class="tab2">
				<tr>
				
					<td align="center" style="font-size:9px"><b>Party Name</b></td>			
					<td align="center" style="font-size:9px"><b>Party Id</b></td>			
					<td align="center" style="font-size:9px"  ><b> 	
					<logic:present name="LEPMOrderCancelReport" scope="request">
							<logic:notEmpty  name="LEPMOrderCancelReport" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Crm Order Id</a>
							</logic:notEmpty>
							<logic:empty  name="LEPMOrderCancelReport" scope="request">
								Crm Order Id
							</logic:empty>
						</logic:present>
						</b></td>
					<td align="center" style="font-size:9px"><b>Copc Approved Date</b></td>
					<td align="center" style="font-size:9px"><b>Logical Circuit Id</b></td>
					<td align="center" style="font-size:9px"><b>Service Number</b></td>
					<td align="center" style="font-size:9px"><b>Quotenumber</b></td>
					<td align="center" style="font-size:9px"><b>Product Name</b></td>
					<td align="center" style="font-size:9px"><b>Product Subtype</b></td>
					<td align="center" style="font-size:9px"><b>From Site </b></td>
					<td align="center" style="font-size:9px"><b>To Site</b></td>
					<td align="center" style="font-size:9px"><b>Order Type</b></td>
					<td align="center" style="font-size:9px"><b>Order Sub Type</b></td>
				   	<td align="center" style="font-size:9px"><b>Provision Bandwidth </b></td>
				   	<td align="center" style="font-size:9px"><b>Line Item Amount</b></td>
				   	<td align="center" style="font-size:9px"><b>Project Mgr</b></td>
				   	<td align="center" style="font-size:9px"><b>Project Mgr Email </b></td>
				    <td align="center" style="font-size:9px"><b>Account Mgr </b></td>
				    <td align="center" style="font-size:9px"><b>Zone </b></td>
				    <td align="center" style="font-size:9px"><b>Region Name </b></td>
					<td align="center" style="font-size:9px"><b>Vertical </b></td>
					<td align="center" style="font-size:9px"><b>Account Category </b></td>
				  	<td align="center" style="font-size:9px"><b>Customer Po Number </b></td>
				   	<td align="center" style="font-size:9px"><b>Customer Po Date </b></td>
				  	<td align="center" style="font-size:9px"><b>Order Entry Date </b></td>
				  	<td align="center" style="font-size:9px"><b>Pm Approval Date </b></td>
				  	<td align="center" style="font-size:9px"><b>Account Manager Receive Date </b></td>
				  	<td align="center" style="font-size:9px"><b>Nio Approval Date </b></td>
				  	<td align="center" style="font-size:9px"><b>Customer Rfs Date </b></td>
				  	<td align="center" style="font-size:9px"><b>Order Category</b></td>
				  	<td align="center" style="font-size:9px"><b>Order Reporting Status </b></td>
				  	<td align="center" style="font-size:9px"><b>Line Item Description </b></td>
				  	<td align="center" style="font-size:9px"><b>Order Line Name </b></td>
				  	<td align="center" style="font-size:9px"><b>Order Subline Name </b></td>
				  	<td align="center" style="font-size:9px"><b>Charge Name </b></td>
				  	<td align="center" style="font-size:9px"><b>Pk Charges Id </b></td>
				  	<td align="center" style="font-size:9px"><b>Order Line No </b></td>
				  	<td align="center" style="font-size:9px"><b>Service Name </b></td>
				  	<td align="center" style="font-size:9px"><b>Account Number </b></td>
				  	<td align="center" style="font-size:9px"><b>Companyname </b></td>
				  	<td align="center" style="font-size:9px"><b>Licence Company</b></td>
				  	<td align="center" style="font-size:9px"><b>Contract Period</b></td>
				  	<td align="center" style="font-size:9px"><b>Payment Term</b></td>
				  	<td align="center" style="font-size:9px"><b>Cyclic Non Cyclic</b></td>
				  	<td align="center" style="font-size:9px"><b>Order Line Type</b></td>
				  	<td align="center" style="font-size:9px"><b>Uom</b></td>
				  	<td align="center" style="font-size:9px"><b>Billing Bandwidth</b></td>
				  	<td align="center" style="font-size:9px"><b>Bill Uom</b></td>
				  	<td align="center" style="font-size:9px"><b>From City</b></td>
				  	<td align="center" style="font-size:9px"><b>To City</b></td>
				    <td align="center" style="font-size:9px"><b>Ratio</b></td>
				    <td align="center" style="font-size:9px"><b>Taxation</b></td>
					<td align="center" style="font-size:9px"><b>Kms Distance</b></td>
				   	<td align="center" style="font-size:9px"><b>Account Manager Emailid</b></td>
				   	<td align="center" style="font-size:9px"><b>Currency</b></td>
					<td align="center" style="font-size:9px"><b>Order Total</b></td>
					<td align="center" style="font-size:9px"><b>Po Amount</b></td>
				 
					<td align="center" style="font-size:9px"><b>Dispatch Address</b></td>
				   	<td align="center" style="font-size:9px"><b>Service Stage</b></td>
				   	<td align="center" style="font-size:9px"><b>Cancel Date</b></td>
				   	<!--<td align="center" style="font-size:9px"><b>Pm Approval Task Remark</b></td>
				   	<td align="center" style="font-size:9px"><b>Copc Approval Task Remark</b></td>
				   	<td align="center" style="font-size:9px"><b>Order Entry Task Remark</b></td>-->
				   	<td align="center" style="font-size:9px"><b>Opportunity ID</b></td>
					<td align="center" style="font-size:9px"><b>Charge Disconnection Status</b></td>
					<!-- gunjan -->				
					<td align="center" style="font-size:9px"><b>Order Cancellation Reason</b></td>
					
					
				    <!-- sada -->	
				    <td align="center" style="font-size:9px"><b>Service Cancelledby</b></td>
					<td align="center" style="font-size:9px"><b>Service Cancellation Reason</b></td>
				   <td align="center" style="font-size:9px"><b>Service Cancel Remarks</b></td>				
		</tr>
				<logic:present name="LEPMOrderCancelReport" scope="request">
					<logic:notEmpty  name="LEPMOrderCancelReport" scope="request"> 					
						<logic:iterate id="row" name="LEPMOrderCancelReport" indexId="recordId">
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
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>	
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyid"/></td>								
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalCircuitId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="quoteNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="productName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subProductName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primarylocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="seclocation"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="orderType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ordersubtype"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lineItemAmount"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="prjmngname"/></td>
								<td align="left" style="font-size:9px" width="10%"><bean:write name="row" property="prjmgremail"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="actmngname"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="zoneName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="act_category"/></td>
								<td align="left" style="font-size:9px" width="10%"><bean:write name="row" property="custPoDetailNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmApproveDate"/></td>
							    <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="amApproveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="nio_approve_date"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="rfs_date"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="ordercategory"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="orderStatus"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="line_desc"/></td> 
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="linename"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="sub_linename"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="chargeName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="chargeinfoID"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="serviceProductID"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="serviceName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="accountID"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="entity"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="licCompanyName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="contractPeriod"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="frequencyName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="chargeTypeName"/></td>
							    <td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="serviceType"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="uom"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="billing_bandwidth"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="billing_uom"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="from_city"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="to_city"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="ratio"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="taxation"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="distance"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="accountManager"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="currencyCode"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="orderTotal"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="poAmount"/></td>
							
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="dispatchAddress1"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="serviceStage"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="canceldate"/></td>
								<!--<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="pm_approval_remarks"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="copc_approval_remarks"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="am_approval_remarks"/></td>-->
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="opportunityId"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="charge_Disconnection_Status"/></td>
								<!-- gunjan -->
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="ord_cancel_reason"/>&nbsp;</td>
								<%-- <td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="srv_cancel_reason"/>&nbsp;</td> --%>
								
								<!-- sada -->
								
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="service_cancelledby"/>&nbsp;</td>
								 <td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="serv_cancel_reson"/>&nbsp;</td> 
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="serv_cancel_remarks"/>&nbsp;</td>
								
							</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty  name="LEPMOrderCancelReport">
					 <% String fromPageSubmit=request.getParameter("fromPageSubmit");
							if(("1").equalsIgnoreCase(fromPageSubmit)){%>
					   <tr align="center" >
						 <td colspan="17" align="center"><B><font color="red">No Records Found</font></B></td>
					   </tr>
						<%}%>
					  </logic:empty>	
				</logic:present>				
			</table>
			</div>
		</fieldset>
	</html:form>
</body>
</html>
