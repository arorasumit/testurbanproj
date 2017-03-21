<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- [TRNG21052013004]    Vijay        14 June   - create freez header -->
<!-- [001] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
<!-- [002] Gunjan Singla     added  column customer segment description-->
<!-- [BFR7] Nagarjuna        GlobalDataBillingEfficiency Project to add TaxExemptionReason -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.ReportsBean"%>
<%@page import="com.ibm.ioes.utilities.ApplicationFlags"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>Order Detail New Report(Usage)</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script> 
<!-- [TRNG21052013004] start -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ReportHeaderFreeze.js"></script>
<link href="./css/ReportHeaderFreeze.css" type="text/css" rel="stylesheet">
<!-- [TRNG21052013004] end -->
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

var isUsage ="<%= request.getAttribute("isUsage") %>";
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
	//myform.method.value='viewOrderReportNew';	
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewOrderReportNew&usage="+isUsage;
	if(isBlankForm()==false)
	{
		return ;
	}
	showLayer();
	myform.submit();
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
function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	myform.toExcel.value='false';
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
	//myform.method.value='viewOrderReportNew';	
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewOrderReportNew&usage="+isUsage;
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
	if(!isBlankForm()){
		
		return;
	} 
	else {
		//myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewOrderReportNew";
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewOrderReportNew&usage="+isUsage;
		showLayer();
		myForm.submit(); 
	}
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
	if(!isBlankForm()){
		return;
	} 
	else {
		//myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewOrderReportNew&inExcel=true";
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewOrderReportNew&inExcel=true&usage="+isUsage;
		showLayerExcel();
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.orderType.value='' ; 
	myForm.fromDate.value='' ;	
	myForm.toDate.value='' ;	
	myForm.verticalDetails.value='' ;	
	myForm.osp.value='' ;	
	myForm.fromCopcApprovedDate.value='' ;	
	myForm.toCopcApprovedDate.value='' ;
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
	var orderStage=myForm.orderType.value;
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	var verticalDetails=myForm.verticalDetails.value;
	var fromCopcApprovedDate=myForm.fromCopcApprovedDate.value;
	var toCopcApprovedDate=myForm.toCopcApprovedDate.value;

	
		if((fromDate == null & toDate == null) || ( trim(fromDate)=="" & trim(toDate) == "")){
			alert("Please enter From Date and To Date!");
		return false;
		
		} 				
		if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
		{
			alert("Please enter From Date");
			return false;
		}
		else if((toDate == null || trim(toDate)== "" ) && (fromDate != null && trim(fromDate) != "" ))
		{
			alert("Please enter To Date");
			return false;
		}
		
		if(dateCompare(fromDate,toDate)==1)
			{			
				alert("From Date cannot be greater than To Date");
				return false;
			}
			
	if((fromCopcApprovedDate == null || trim(fromCopcApprovedDate)== "" ) && (toCopcApprovedDate != null && trim(toCopcApprovedDate) != "" ))
	{
		alert("Please enter COPC Approval From Date");
		return false;
	}
	else if((toCopcApprovedDate == null || trim(toCopcApprovedDate)== "" ) && (fromCopcApprovedDate != null && trim(fromCopcApprovedDate) != "" ))
	{
		alert("Please enter COPC Approval To Date");
		return false;
	}
	
	if(dateCompare(fromCopcApprovedDate,toCopcApprovedDate)==1)
		{			
			alert("COPC Approval From Date cannot be greater than COPC Approval To Date");
			return false;
		}			
		return true;

}

path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload="javascript:maxLimitChecker(document.getElementById('searchForm'),'<%=ApplicationFlags.ReportsExcelMaxSize%>','<%=Messages.getMessageValue("ReportsExcelMaxSizeMessage") %>');">


<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/reportsAction_Usage" styleId="searchForm" method="post">
		<bean:define id="formBean" name="reportsBean"></bean:define>
			<html:hidden property="interfaceId"/>
		<html:hidden property="isDumpAvailable"/>
		<html:hidden property="dumpFileName"/>	
		<html:hidden property="toExcel"/>
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="pagingSorting.sortByOrder"/>
		<html:hidden property="pagingSorting.pageNumber"/>
		
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
				<tr ><td align="left"><span>ORDER DETAIL NEW REPORT(USAGE)</span>
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
				<table border="0"  align="center" style="margin-top:7px">
					<tr>
						<td width="90px"/>
						<td align="right" style="font-size:9px;">From Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="dateFrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Select Date"></a>
							</font>
						</td>
						<td width="90px"/>
						<td align="right" style="font-size:9px;">To Date:</td>
						<td nowrap><html:text  property="toDate" styleId="dateTo" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Select Date"></a>
							</font>
						</td>
						<td width="90px"/>
					</tr>				
						
						<tr>
						<td width="90px"/>
						<td align="right" style="font-size:9px;">COPC Approval From Date:</td>
						<td nowrap><html:text  property="fromCopcApprovedDate" styleId="datefrom2" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromCopcApprovedDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						 <td width="12"/>
						<td align="right" style="font-size:9px;" width="25">COPC Approval To Date:</td>
						<td nowrap width="149"><html:text  property="toCopcApprovedDate" styleId="dateto2" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toCopcApprovedDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>						
						<td align="right" style="font-size:9px;" width="30">OSP</td>
								<td nowrap width="88">
								<html:select property="osp" style="width:70px;float:left;" >
								<html:option value="">Select</html:option> 
								<html:option value='YES'>Yes</html:option> 
								<html:option value='NO'>No</html:option> 
								</html:select>
							</td>							
						<td align="left" width="30">
					</tr>
					<tr>
					<td width="90px"/>
						<td align="right" style="font-size:9px"> Order Type:</td>
						<td align="left" nowrap>
							<html:select  property="orderType" style="width:70px;float:left;"  >
							<html:option value="">Select</html:option>
							<html:option value="New">New</html:option>
						    <html:option value="Change">Change</html:option>
						
							</html:select>
						</td>
						
						 <td width="12"/>
						<td align="right" style="font-size:9px" width="25">Vertical</td>
						<td align="left" nowrap width="149">
							<html:text  property="verticalDetails" style="width:90px;float:left;" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Vertical')};"></html:text>
						</td>
						
						
						<td align="left" width="30">
						<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td align="center" valign="top" width="88">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
						<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel"  onClick="javascript:exportToExcel();">Export To Excel</a></div></td>											
						</tr>
					</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>ORDER DETAIL NEW REPORT(USAGE)</b></legend>
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
			
<!-- Start Scrolling and Freezing of Copy and Cancel Report   -->
			<table border="0" cellpadding="0" cellspacing="0" class='main'>       
			 	<tr>
					<td class='tablefrozencolumn'>
	                    <div id='divroot' class='root'>
	                        <table border="0" cellpadding="0" cellspacing="0" width="100%" class='root'>
	                            <tr>
	                                <td class='inner frozencol colwidth head1'>
	                                    
	                                </td>
	                            </tr>
	                        </table>
	                    </div>
	                    <div id='divfrozen' class='frozen'>
	                        <table border="0" cellpadding="0" cellspacing="0" width="100%" class='frozen'>
	                            <tr>
	                                <td class='inner frozencol toprow'>
	                                    
	                                </td>
	                            </tr>                            
	                        </table>
	                    </div>
	                </td>
	                <td class='tablecontent'>	
	                   <div id='headscroll' class='divhead'>
	                	  <table border="0" cellpadding="0" cellspacing="0" class='head1'>
				<tr>
				<!--  Saurabh : change to show data for charges -->		
				<logic:equal name="isUsage" value="0">	
					<td align="center" style="font-size:9px"><b>Party Name</b></td>
					<td align="center" style="font-size:9px"  ><b> 	
					<logic:present name="orderReportNewList" scope="request">
							<logic:notEmpty  name="orderReportNewList" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">	Crm Order Id</a>
							</logic:notEmpty>
							<logic:empty  name="orderReportNewList" scope="request">
								Crm Order Id
							</logic:empty>
						</logic:present>
						</b></td>
					<td align="center" style="font-size:9px"><b>Copc Aprv Date</b></td>
					<td align="center" style="font-size:9px"><b>Logical Ckt Id</b></td>
					<td align="center" style="font-size:9px"><b>Service No</b></td>
					<td align="center" style="font-size:9px"><b>Quote No</b></td>
					<td align="center" style="font-size:9px"><b>Product Name</b></td>
					<td align="center" style="font-size:9px"><b>Sub Product Name</b></td>
					<td align="center" style="font-size:9px"><b>From Site</b></td>
					<td align="center" style="font-size:9px"><b>To Site</b></td>
					<td align="center" style="font-size:9px"><b>Order Sub Type</b></td>
					<td align="center" style="font-size:9px"><b>Bandwidth</b></td>
					<td align="center" style="font-size:9px"><b>LineAmt</b></td>
					<td align="center" style="font-size:9px"><b>Prj Magr</b></td>
					<td align="center" style="font-size:9px"><b>Prj Mgr Email</b></td>
					<td align="center" style="font-size:9px"><b>Act Mgr</b></td>
					<td align="center" style="font-size:9px"><b>Zone</b></td>
					<td align="center" style="font-size:9px"><b>Region</b></td>
					<td align="center" style="font-size:9px"><b>Vertical</b></td>
					<td align="center" style="font-size:9px"><b>Act Category</b></td>
					<td align="center" style="font-size:9px"><b>CustPoNumber</b></td>
					<td align="center" style="font-size:9px"><b>CustPoDate</b></td>
					<td align="center" style="font-size:9px"><b>Order Date</b></td>
					<td align="center" style="font-size:9px"><b>PM aprvl Datre</b></td>
					<td align="center" style="font-size:9px"><b>Act mgr Date</b></td>
					<td align="center" style="font-size:9px"><b>Nio Aprv Date</b></td>
					<td align="center" style="font-size:9px"><b>Demo InfraStart Date</b></td>
					<td align="center" style="font-size:9px"><b>Demo InfraEndDate</b></td>
					<td align="center" style="font-size:9px"><b>Cust Rfs Date</b></td>
					<td align="center" style="font-size:9px"><b>Order Category</b></td>
					<td align="center" style="font-size:9px"><b>Order Reporting Status</b></td>
					<td align="center" style="font-size:9px"><b>Line Desc</b></td>
					<td align="center" style="font-size:9px"><b>Order Line Name</b></td>
					<td align="center" style="font-size:9px"><b>Order Sub LineName</b></td>
					<td align="center" style="font-size:9px"><b>Charge Name</b></td>
					<td align="center" style="font-size:9px"><b>Pk ChargeId</b></td>
					<td align="center" style="font-size:9px"><b>Order Line No</b></td>
					<td align="center" style="font-size:9px"><b>Service Name</b></td>
					<td align="center" style="font-size:9px"><b>Act No</b></td>
					<td align="center" style="font-size:9px"><b>Legal Entity</b></td>
					<td align="center" style="font-size:9px"><b>Licence Company</b></td>
					<td align="center" style="font-size:9px"><b>Contract Period</b></td>
					<td align="center" style="font-size:9px"><b>Payment Term</b></td>
					<td align="center" style="font-size:9px"><b>Cyclic Noncyclic</b></td>
					<td align="center" style="font-size:9px"><b>Order LineType</b></td>
					<td align="center" style="font-size:9px"><b>UOM</b></td>
					<td align="center" style="font-size:9px"><b>Billing Bandwidth</b></td>
					<td align="center" style="font-size:9px"><b>Billing Uom</b></td>
					<td align="center" style="font-size:9px"><b>From City</b></td>
					<td align="center" style="font-size:9px"><b>To City</b></td>
					<td align="center" style="font-size:9px"><b>Old Order Total</b></td>
					<td align="center" style="font-size:9px"><b>Old Line Amt</b></td>
					<td align="center" style="font-size:9px"><b>Old Contract Period</b></td>
					<td align="center" style="font-size:9px"><b>Ratio</b></td>
					<td align="center" style="font-size:9px"><b>Taxation</b></td>
					<td align="center" style="font-size:9px"><b>Factory Ckt Id</b></td>
					<td align="center" style="font-size:9px"><b>Kms Distance</b></td>
					<td align="center" style="font-size:9px"><b>Act Mgr Email</b></td>
					<td align="center" style="font-size:9px"><b>Currency</b></td>
					<td align="center" style="font-size:9px"><b>Order Total</b></td>
					<td align="center" style="font-size:9px"><b>Po Amt</b></td>
					<td align="center" style="font-size:9px"><b>BiSource</b></td>
					<td align="center" style="font-size:9px"><b>Order Type</b></td>
					<td align="center" style="font-size:9px"><b>Dispatch Address</b></td>
					<td align="center" style="font-size:9px"><b>Parent Name</b></td>
					<td align="center" style="font-size:9px"><b>OSP</b></td>
					
				</logic:equal>
				<!-- Saurabh : End -->
				
				<!--  Meenakshi : change for usage -->
				<logic:equal name="isUsage" value="1">
					
					<td align="center" class='inner col2 head1'><b>Party Name</b></td>
					<td align="center" class='inner col2 head1'  ><b> 	
					<logic:present name="orderReportNewList" scope="request">
							<logic:notEmpty  name="orderReportNewList" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Order No</a>
							</logic:notEmpty>
							<logic:empty  name="orderReportNewList" scope="request">
								Crm Order Id
							</logic:empty>
						</logic:present>
						</b></td>
					<td align="center" class='inner col2 head1'><b>COPC Approval Date</b></td>
					<td align="center" class='inner col2 head1'><b>Logical Ckt Id</b></td>
					<td align="center" class='inner col2 head1'><b>Service No</b></td>
					<td align="center" class='inner col2 head1'><b>Quote No</b></td>
					<td align="center" class='inner col3 head1'><b>Product Name</b></td>
					<td align="center" class='inner col3 head1'><b>Sub Product Name</b></td>
					<td align="center" class='inner col4 head1'><b>From Site</b></td>
					<td align="center" class='inner col4 head1'><b>To Site</b></td>
					<td align="center" class='inner col2 head1'><b>Order Sub Type</b></td>
					<td align="center" class='inner col2 head1'><b>Bandwidth</b></td>
					<td align="center" class='inner col3 head1'><b>Project Mgr</b></td>
					<td align="center" class='inner col3 head1'><b>project Mgr Email</b></td>
					<td align="center" class='inner col3 head1'><b>Account Mgr</b></td>
					<td align="center" class='inner col2 head1'><b>Zone</b></td>
					<td align="center" class='inner col2 head1'><b>Region</b></td>
					<td align="center" class='inner col2 head1'><b>Vertical</b></td>
					<td align="center" class='inner col2 head1'><b>Account Category</b></td>
					<td align="center" class='inner col2 head1'><b>Customer PoNumber</b></td>
					<td align="center" class='inner col2 head1'><b>Customer PoDate</b></td>
					<td align="center" class='inner col2 head1'><b>Order Date</b></td>
					<td align="center" class='inner col2 head1'><b>PM Approval Date</b></td>					
					<td align="center" class='inner col2 head1'><b>Account Mgr Date</b></td>
					<td align="center" class='inner col2 head1'><b>Copc Approval Date</b></td>
					<td align="center" class='inner col3 head1'><b>Last Approval Remarks</b></td>
					<td align="center" class='inner col2 head1'><b>Demo InfraStartDate</b></td>
					<td align="center" class='inner col2 head1'><b>Demo InfraEndDate</b></td>
					<td align="center" class='inner col2 head1'><b>Customer Rfs Date</b></td>
					<td align="center" class='inner col2 head1'><b>Order Category</b></td>
					<td align="center" class='inner col2 head1'><b>Order Reporting Status</b></td>
					<td align="center" class='inner col3 head1'><b>Line Item Name</b></td>
					<td align="center" class='inner col2 head1'><b>Line Item Id</b></td>
					<td align="center" class='inner col3 head1'><b>Service Name</b></td>
					<td align="center" class='inner col2 head1'><b>Account No</b></td>
					<td align="center" class='inner col2 head1'><b>Legal Entity</b></td>
					<td align="center" class='inner col3 head1'><b>Licence Company</b></td>
					<td align="center" class='inner col2 head1'><b>Contract Period</b></td>
					<td align="center" class='inner col2 head1'><b>UOM</b></td>
					<td align="center" class='inner col2 head1'><b>Billing Bandwidth</b></td>
					<td align="center" class='inner col2 head1'><b>Billing Uom</b></td>
					<td align="center" class='inner col4 head1'><b>From City</b></td>
					<td align="center" class='inner col4 head1'><b>To City</b></td>
					<td align="center" class='inner col2 head1'><b>Ratio</b></td>
					<td align="center" class='inner col2 head1'><b>Taxation</b></td>
					<!--GlobalDataBillingEfficiency BFR7  -->		
					<td align="center" class='inner col2 head1'><b>TaxExemption Reason</b></td>			
					
					<td align="center" class='inner col2 head1'><b>Kms Distance</b></td>
					<td align="center" class='inner col3 head1'><b>Account Mgr EmailId</b></td>
					<td align="center" class='inner col2 head1'><b>Currency</b></td>
					<td align="center" class='inner col2 head1'><b>Order Total</b></td>
					<td align="center" class='inner col2 head1'><b>Po Amt</b></td>
					<td align="center" class='inner col2 head1'><b>BiSource</b></td>
					<td align="center" class='inner col2 head1'><b>Order Type</b></td>
					<td align="center" class='inner col3 head1'><b>Parent Name</b></td>
				
					<td align="center" class='inner col4 head1'><b>Child Account Number</b></td>
					<td align="center" class='inner col2 head1'><b>Child Account No Internal</b></td>
					<td align="center" class='inner col2 head1'><b>Child Account Fx Status</b></td>
					<td align="center" class='inner col2 head1'><b>Package ID</b></td>
					<td align="center" class='inner col3 head1'><b>Package Name</b></td>
					<td align="center" class='inner col2 head1'><b>Component ID</b></td>
					<td align="center" class='inner col4 head1'><b>Component Name</b></td>
					<td align="center" class='inner col2 head1'><b>Component InfoID</b></td>
					<td align="center" class='inner col2 head1'><b>Component Type</b></td>
					<!--<td align="center" class='inner col2 head1'><b>rental / nrc component amount</b></td>-->
					<td align="center" class='inner col2 head1'><b>Component Status</b></td>
					<td align="center" class='inner col2 head1'><b>OSP</b></td>
					<!--	shourya start--> 
					<td align="center" class='inner col2 head1'><b>Opportunity Id</b></td>
				 	<td align="center" class='inner col4 head1'><b>Re-logged LSI No</b></td>
                 	<td align="center" class='inner col6 head1'><b>Parallel Upgrade LSI No</b></td>
                 	<td align="center" class='inner col6 head1'><b>Charge Disconnection Status</b></td>
				    <td align="center" class='inner col6 head1'><b>Sub Change Type</b></td>
				    <td align="center" class='inner col6 head1'><b>Fx Account External Id</b></td>
				<!--	shourya end --> 
			<td align="center" class='inner col2 head1'><b>Customer Segment Description</b></td>
					<!-- Nagarjuna OB Value Usage -->
					<td align="center" class='inner col2 head1'><b>OB Value</b></td>
					<td align="center" class='inner col2 head1'><b>OB Value Last Update Date</b></td>
					<!-- End  OB Value Usage -->
					<!-- nancy start -->
					<td align="center" class='inner col2 head1'><b>ePCN No.</b></td>
					<td align="center" class='inner col2 head1'><b>Billing Trigger Create Date</b></td>
					<!-- nancy end -->
				</logic:equal>
				<!-- Meenakshi : End -->
				</tr>
				
				</table>
					 </div> 	
					 <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	        			<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>		
				<logic:present name="orderReportNewList" scope="request">
					<logic:notEmpty  name="orderReportNewList" scope="request"> 					
						<logic:iterate id="row" name="orderReportNewList" indexId="recordId">
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
						<!--  Saurabh : change to show data for charges -->		
						<logic:equal name="isUsage" value="0">
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalCircuitId"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceId"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="quoteNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="productName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subProductName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primarylocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="seclocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ordersubtype"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAmount"/></td>
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
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="demo_infrastartdate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="demo_infra_enddate"/></td>
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
							<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="oldordertotal"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write name="row" property="oldlineamt"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="old_contract_period"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="ratio"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="taxation"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="factory_ckt_id"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write name="row" property="distance"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="accountManager"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="currencyCode"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="orderTotal"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="poAmount"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="bisource"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="orderType"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="dispatchAddress1"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="parent_name"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="osp"/></td>
						</logic:equal>
						<!--  Saurabh : End -->		
						
						<!--  Meenaskhi : changes for Usage -->
						<logic:equal name="isUsage" value="1">
						
							<td align="left" class='inner col2'><bean:write  name="row" property="partyName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="orderNo"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="copcApproveDate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="logicalCircuitId"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="serviceId"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="quoteNo"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="productName"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="subProductName"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="primarylocation"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="seclocation"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="ordersubtype"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="bandwaidth"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="prjmngname"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write name="row" property="prjmgremail"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="actmngname"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="zoneName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="regionName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="verticalDetails"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="act_category"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="custPoDetailNo"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="custPoDate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="orderDate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="pmApproveDate"/>&nbsp;</td>
						    <td align="left" class='inner col2'><bean:write  name="row" property="amApproveDate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="nio_approve_date"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="lastApprovalRemarks"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="demo_infrastartdate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="demo_infra_enddate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="rfs_date"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="ordercategory"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="orderStatus"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write name="row" property="linename"/>&nbsp;</td> 
							<td align="left" class='inner col2'><bean:write name="row" property="serviceProductID"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write name="row" property="serviceName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="accountID"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="entity"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write name="row" property="licCompanyName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="contractPeriod"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="uom"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="billing_bandwidth"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="billing_uom"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write name="row" property="from_city"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write name="row" property="to_city"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="ratio"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="taxation"/>&nbsp;</td>
							<!--GlobalDataBillingEfficiency BFR7  -->
               				<td align="left" class='inner col2'><bean:write  name="row" property="taxExcemption_Reason"/>&nbsp;</td>
								
							<td align="left" class='inner col2'><bean:write name="row" property="distance"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write name="row" property="accountManager"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="currencyCode"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="orderTotal"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="poAmount"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="bisource"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="orderType"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write name="row" property="parent_name"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="fx_external_acc_id"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="fxInternalId"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="child_account_creation_status"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="packageID"/>&nbsp;</td>
							<td align="left" class='inner col3' ><bean:write  name="row" property="packageName"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentID"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="componentName"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentInfoID"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentType"/>&nbsp;</td>
							<!--<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentAmount"/>&nbsp;</td>-->
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentStatus"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="osp"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write name="row" property="opportunityId"/></td>
							<td align="left" class='inner col4'><bean:write name="row" property="RE_LOGGED_LSI_NO"/>&nbsp;</td>
							<td align="left" class='inner col6'><bean:write name="row" property="PARALLEL_UPGRADE_LSI_NO"/>&nbsp;</td>
							 <td align="left" class='inner col6'><bean:write name="row" property="CHARGEDISCONNECTIONSTATUS"/>&nbsp;</td>
								<td align="left" class='inner col6'><bean:write name="row" property="subchange_type"/>&nbsp;</td>
								<td align="left" class='inner col6'><bean:write name="row" property="fxAccountExternalId"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write name="row" property="custSeg_Description"/>&nbsp;</td>
							<!-- Nagarjuna OB Value Usage -->
							<td align="left" class='inner col2'><bean:write name="row" property="obValue"/>&nbsp;</td>
							<td align="center" class='inner col2'><bean:write name="row" property="obValueLastUpdateDate"/>&nbsp;</td>
							<!-- End OB Value Usage -->
							<!-- nancy start --><td align="center" class='inner col2'><bean:write name="row" property="ePCNNo"/>&nbsp;</td>
							<td align="center" class='inner col2'><bean:write name="row" property="billingTriggerCreateDate"/>&nbsp;</td><!-- nancy end -->
						</logic:equal>
						<!--  Meenakshi : End -->	
						</tr>
						</logic:iterate>
					</logic:notEmpty>
					</table>
					    </div>
				    </td>
				    <td class='tableverticalscroll' rowspan="2">
			       		<div class='vertical-scroll' onscroll='reposVertical(this);'>
			           		<div>
			           		</div>
			       		</div>
			       		<div class='ff-fill'>
			       		</div>
			   		</td>
			   	 </tr>
			   	 <tr>
		          	<td colspan="3">
		              	<div class='horizontal-scrollorderreportnewusage' onscroll='reposHorizontal(this);'>
		                  	<div>
		                  	</div>
		              	</div>
		          	</td>
		     	</tr>	
		     </table>	
		     	 <!-- End Scrolling and Freezing of Copy and Cancel Report   -->
					
					<logic:empty  name="orderReportNewList">
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
