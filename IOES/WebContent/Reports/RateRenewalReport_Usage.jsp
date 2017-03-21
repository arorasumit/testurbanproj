<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- [TRNG21052013004]    Vijay        26 June   - create freez header -->
<!-- [001] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.utilities.ApplicationFlags"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.ReportsBean"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>RATE RENEWAL REPORT</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
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
var isUsage ="<%= request.getAttribute("isUsage") %>";
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


		

function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	myform.toExcel.value='false';
	//myform.reset();
	myform.elements["pagingSorting.pageNumber"].value=val;
	//myform.method.value='viewRateRenewalReport&usage='+isUsage;	
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewRateRenewalReport&usage="+isUsage;
	if(isBlankForm()==false)
	{
		return ;
	}
	showLayer();
	myform.submit();
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
	//myform.method.value='viewRateRenewalReport&usage='+isUsage;	
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewRateRenewalReport&usage="+isUsage;
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
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewRateRenewalReport&usage="+isUsage;
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
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewRateRenewalReport&inExcel=true&usage="+isUsage;
		showLayerExcel();
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
//	myForm.orderType.value='' ; 
	myForm.fromDate.value='' ;	
	myForm.toDate.value='' ;
//	myForm.demo.value='' ;
	myForm.toAccountNo.value='' ;
	myForm.fromAccountNo.value='' ;
	myForm.toOrderNo.value='' ;
	myForm.fromOrderNo.value='' ;	
	myForm.fromCopcApprovedDate.value='' ;	
	myForm.toCopcApprovedDate.value='' ;
	
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
//	var orderStage=myForm.orderType.value;
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
//	var demo=myForm.demo.value; 
	var toAccountNo=myForm.toAccountNo.value;
	var fromAccountNo=myForm.fromAccountNo.value;
	var toOrderNo=myForm.toOrderNo.value;
	var fromOrderNo=myForm.fromOrderNo.value;
	var fromCopcApprovedDate=myForm.fromCopcApprovedDate.value;
	var toCopcApprovedDate=myForm.toCopcApprovedDate.value;
		
	if((fromDate == null & toDate == null) || ( trim(fromDate)=="" & trim(toDate) == "")){
		alert("Please enter  From Date and To Date!");
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
		
		
	if((fromOrderNo == null || trim(fromOrderNo)== "" ) && (toOrderNo != null && trim(toOrderNo) != "" ))
	{
		alert("Please enter fromOrderNo");
		return false;
	}
	else if((toOrderNo == null || trim(toOrderNo)== "" ) && (fromOrderNo != null && trim(fromOrderNo) != "" ))
	{
		alert("Please enter toOrderNo");
		return false;
	}
	
	if(toOrderNo < fromOrderNo)
		{			
			alert("From Order No cannot be greater than To Order No");
			return false;
		}
		
	if((fromAccountNo == null || trim(fromAccountNo)== "" ) && (toAccountNo != null && trim(toAccountNo) != "" ))
	{
		alert("Please enter fromAccountNo");
		return false;
	}
	else if((toAccountNo == null || trim(toAccountNo)== "" ) && (fromAccountNo != null && trim(fromAccountNo) != "" ))
	{
		alert("Please enter toAccountNo");
		return false;
	}
	
	if(toAccountNo < fromAccountNo)
		{			
			alert("From Account No cannot be greater than To Account No");
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
		   <html:hidden property="reportProcessForLimit"/>
           <html:hidden property="reportCurrentCount"/>
		<bean:define id="formBean" name="reportsBean"></bean:define>
		<html:hidden property="toExcel"/>
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="interfaceId"/>
		<html:hidden property="isDumpAvailable"/>
		<html:hidden property="dumpFileName"/>	
		<html:hidden property="pagingSorting.sortByOrder"/>
		<html:hidden property="pagingSorting.pageNumber"/>
		<input type="hidden" name="fromPageSubmit" value="1"/>
		<input type="hidden" name="method" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="left">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
		
		<logic:equal name="isUsage" value="0">	
			<div border="1" class="head"> <span>RATE RENEWAL REPORT</span> </div>
		</logic:equal>
		<logic:equal name="isUsage" value="1">
<div border="1" class="head"> 
			
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>RATE RENEWAL REPORT(USAGE)</span> 
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
		</logic:equal>
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
						<td align="right" style="font-size:9px;">From Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="dateFrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Select Date"></a>
							</font>
						</td>
						<td align="right" style="font-size:9px;">To Date:</td>
						<td nowrap><html:text  property="toDate" styleId="dateTo" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Select Date"></a>
							</font>
						</td>
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td width="10px"/>
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>							
							<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
						
						<td width="90px"/>
						
					</tr>
					<tr>
						<td align="right" style="font-size:9px;">COPC Approval From Date:</td>
						<td nowrap><html:text  property="fromCopcApprovedDate" styleId="datefrom2" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromCopcApprovedDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
					
						<td align="right" style="font-size:9px;">COPC Approval To Date:</td>
						<td nowrap><html:text  property="toCopcApprovedDate" styleId="dateto2" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toCopcApprovedDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td width="90px"/>
						<td width="90px"/>
						<td width="90px"/>
						<td width="90px"/>
						<td width="90px"/>						
					</tr>
					<tr>
					<td align="right" style="font-size:9px;">From Order No:</td>
						<td nowrap><html:text  property="fromOrderNo" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
					</td>
					<td align="right" style="font-size:9px;">To Order No:</td>
						<td nowrap><html:text  property="toOrderNo" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
					</td>
					<td align="right" style="font-size:9px;">From Account No:</td>
						<td nowrap><html:text  property="fromAccountNo" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
					</td>
					<td align="right" style="font-size:9px;">To Account No:</td>
						<td nowrap><html:text  property="toAccountNo" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
					</td>
					
					</tr>
					</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>RATE RENEWAL REPORT</b></legend>
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
			
			<!-- Start Scrolling and Freezing of RATE RENEWAL REPORT(USAGE)     -->
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
					<td align="center" style="font-size:9px"><b>Party Number</b></td>
					<td align="center" style="font-size:9px"><b>Party Name</b></td>
					<td align="center" style="font-size:9px"><b>Account No</b></td>		
					<td align="center" style="font-size:9px"><b>Account Mgr</b></td>
					<td align="center" style="font-size:9px"><b>Customer Segment</b></td>
					<td align="center" style="font-size:9px"><b>Service Segment</b></td>	
					<td align="center" style="font-size:9px"><b>Vertical</b></td>		
					<td align="center" style="font-size:9px"><b>Region</b></td>
					<td align="center" style="font-size:9px"><b>Service Type</b></td>
					<td align="center" style="font-size:9px"><b>Billling Bandwidth</b></td>
				 	<td align="center" style="font-size:9px"><b>Bill Uom</b></td>  
					<td align="center" style="font-size:9px"><b>Catagory Of Order</b></td>
					<td align="center" style="font-size:9px"><b>Ordertype</b></td>
					<td align="center" style="font-size:9px"><b>Company Name</b></td>		
					<td align="center" style="font-size:9px"><b>Creation Date</b></td>
					<td align="center" style="font-size:9px"><b>Currency</b></td>
					<td align="center" style="font-size:9px"><b>Po Date</b></td>	
					<td align="center" style="font-size:9px"><b>Po Number</b></td>		
					<td align="center" style="font-size:9px"><b>Charge Name</b></td>
					<td align="center" style="font-size:9px"><b>Charge Type</b></td>
					<td align="center" style="font-size:9px"><b>From Site</b></td>
					<td align="center" style="font-size:9px"><b>To Site</b></td>
					<td align="center" style="font-size:9px"><b>Kms Distance</b></td>
					<td align="center" style="font-size:9px"><b>Line Item Description</b></td>
					<td align="center" style="font-size:9px"><b>Loc Date</b></td>
					<td align="center" style="font-size:9px"><b>Copc Approved Date</b></td>		
					<td align="center" style="font-size:9px"><b>Circuit Id</b></td>	
					<td align="center" style="font-size:9px"><b>Payment Term</b></td>
					<td align="center" style="font-size:9px"><b>Taxation</b></td>
					<td align="center" style="font-size:9px"><b>Licence Company</b></td>	
					<td align="center" style="font-size:9px"><b>Demo Type</b></td>		
					<td align="center" style="font-size:9px"><b>Service Stage</b></td>
					<td align="center" style="font-size:9px"><b>Order Stage Description</b></td>
					<td align="center" style="font-size:9px"><b>New Order Remark</b></td>
				  	<td align="center" style="font-size:9px"><b>Remarks</b></td>  
					<td align="center" style="font-size:9px"><b>Product</b></td>
					<td align="center" style="font-size:9px"><b>Sub Product</b></td>
					<td align="center" style="font-size:9px"><b>Billing Trigger Date</b></td>
					<td align="center" style="font-size:9px"><b>Billing Trigger Flag</b></td>		
					<td align="center" style="font-size:9px"><b>Line Name</b></td>	
					<td align="center" style="font-size:9px"><b>Charge Start Date</b></td>
					<td align="center" style="font-size:9px"><b>Charge End Date</b></td>
					<td align="center" style="font-size:9px"><b>End Date Logic</b></td>
					<td align="center" style="font-size:9px"><b>Charge Status</b></td>
					<td align="center" style="font-size:9px"><b>Charge Start Days Logic</b></td>
					<td align="center" style="font-size:9px"><b>Charge Start Months Logic</b></td>		
					<td align="center" style="font-size:9px"><b>Zone</b></td>
					<td align="center" style="font-size:9px"><b>Name</b></td>
					<td align="center" style="font-size:9px"><b>Po Amount</b></td>	
					<td align="center" style="font-size:9px"><b>Contract Period</b></td>
					<td align="center" style="font-size:9px"><b>Charge Period Months</b></td>
					<td align="center" style="font-size:9px"><b>Item Quantity</b></td>
					<td align="center" style="font-size:9px"><b>Order Total</b></td>
					<td align="center" style="font-size:9px"><b>Service No</b></td>
					<td align="center" style="font-size:9px"><b>M6 Order Id</b></td>
					<td align="center" style="font-size:9px"><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px"><b>M6 Ckt Dtls Id</b></td>		
					<td align="center" style="font-size:9px"><b>Annual Rate</b></td>	
					<td align="center" style="font-size:9px"><b>Order Line Number</b></td>
					<td align="center" style="font-size:9px"><b>Order No</b></td>
					<td align="center" style="font-size:9px"><b>Last  Order No</b></td>
					<td align="center" style="font-size:9px"><b>Line Item Amount</b></td>	
					<td align="center" style="font-size:9px"><b>Old Line item amount</b></td>
					<td align="center" style="font-size:9px"><b>Start Date Days</b></td>
					<td align="center" style="font-size:9px"><b>Start Date Months</b></td>
				</logic:equal>
				<!-- Saurabh : End -->
					
				<!--  Meenakshi : change for usage -->		
				<logic:equal name="isUsage" value="1">
					<td align="center" class='inner col2 head1'><b>Party Number</b></td>
					<td align="center" class='inner col3 head1'><b>Party Name</b></td>
					<td align="center" class='inner col2 head1'><b>Account No</b></td>		
					<td align="center" class='inner col4 head1'><b>Account Mgr</b></td>
					<td align="center" class='inner col2 head1'><b>Customer Segment</b></td>
					<td align="center" class='inner col1 head1'><b>Service Segment</b></td>	
					<td align="center" class='inner col2 head1'><b>Vertical</b></td>		
					<td align="center" class='inner col2 head1'><b>Region</b></td>
					<td align="center" class='inner col2 head1'><b>Service Type</b></td>
					<td align="center" class='inner col1 head1'><b>Billling Bandwidth</b></td>
				 	<td align="center" class='inner col1 head1'><b>Bill Uom</b></td>  
					<td align="center" class='inner col2 head1'><b>Category Of Order</b></td>
					<td align="center" class='inner col2 head1'><b>Ordertype</b></td>
					<td align="center" class='inner col4 head1'><b>Company Name</b></td>		
					<td align="center" class='inner col2 head1'><b>Currency</b></td>
					<td align="center" class='inner col2 head1'><b>Po Date</b></td>	
					<td align="center" class='inner col2 head1'><b>Po Number</b></td>		
					<td align="center" class='inner col3 head1'><b>From Site</b></td>
					<td align="center" class='inner col3 head1'><b>To Site</b></td>
					<td align="center" class='inner col1 head1'><b>Kms Distance</b></td>
					<td align="center" class='inner col4 head1'><b>Line Item Description</b></td>
					<td align="center" class='inner col2 head1'><b>Loc Date</b></td>
					<td align="center" class='inner col2 head1'><b>Copc Approved Date</b></td>
					<td align="center" class='inner col2 head1'><b>Logical SI</b></td>
					<td align="center" class='inner col2 head1'><b>Circuit Id</b></td>	
					<td align="center" class='inner col2 head1'><b>Taxation</b></td>
					<td align="center" class='inner col4 head1'><b>Licence Company</b></td>	
					<td align="center" class='inner col2 head1'><b>Demo Type</b></td>		
					<td align="center" class='inner col4 head1'><b>Service Stage</b></td>
					<td align="center" class='inner col4 head1'><b>Order Stage Description</b></td>
					<td align="center" class='inner col2 head1'><b>New Order Remark</b></td>
				  	<td align="center" class='inner col2 head1'><b>Remarks</b></td>  
					<td align="center" class='inner col4 head1'><b>Product</b></td>
					<td align="center" class='inner col4 head1'><b>Sub Product</b></td>
					<td align="center" class='inner col2 head1'><b>Billing Trigger Date</b></td>
					<td align="center" class='inner col1 head1'><b>Billing Trigger Flag</b></td>
					<td align="center" class='inner col2 head1'><b>Zone</b></td>	
					<td align="center" class='inner col2 head1'><b>Name</b></td>
					<td align="center" class='inner col1 head1'><b>Po Amount</b></td>	
					<td align="center" class='inner col1 head1'><b>Contract Period</b></td>
					<td align="center" class='inner col1 head1'><b>Item Quantity</b></td>
					<td align="center" class='inner col1 head1'><b>Order Total</b></td>
					<td align="center" class='inner col2 head1'><b>Service No</b></td>
					<td align="center" class='inner col2 head1'><b>M6 Order Id</b></td>
					<td align="center" class='inner col2 head1'><b>Cust Logical Si Id</b></td>
					<td align="center" class='inner col2 head1'><b>Line Number</b></td>
					<td align="center" class='inner col2 head1'><b>Order No</b></td>
					<td align="center" class='inner col2 head1'><b>Last  Order No</b></td>
					<td align="center" class='inner col3 head1'><b>Child Account Number</b></td>
					<td align="center" class='inner col2 head1'><b>Child Account No Internal</b></td>
					<td align="center" class='inner col2 head1'><b>Child Account Fx Status</b></td>
					<td align="center" class='inner col2 head1'><b>Package ID</b></td>
					<td align="center" class='inner col3 head1'><b>Package Name</b></td>
					<td align="center" class='inner col2 head1'><b>Component ID</b></td>
					<td align="center" class='inner col3 head1'><b>Component Name</b></td>
					<td align="center" class='inner col1 head1'><b>Component InfoID</b></td>
					<td align="center" class='inner col1 head1'><b>Component Type</b></td>
					<!--<td align="center" class='inner col2 head1'><b>rental / nrc component amount</b></td>-->
					<td align="center" class='inner col2 head1'><b>Component Status</b></td>
					<td align="center" class='inner col2 head1'><b>Component Start Logic</b></td>
					<td align="center" class='inner col1 head1'><b>Start Date Days</b></td>
					<td align="center" class='inner col1 head1'><b>Start Date Months</b></td>
					<td align="center" class='inner col2 head1'><b>End Date Logic</b></td>
					<td align="center" class='inner col1 head1'><b>End Date Days</b></td>
					<td align="center" class='inner col1 head1'><b>End Date Months</b></td>
					<td align="center" class='inner col2 head1'><b>Component Start Date</b></td>
					<td align="center" class='inner col2 head1'><b>Component End Date</b></td>
					<!-- nancy start -->
					<td align="center" class='inner col2 head1'><b>Is Differential</b></td>
				    <td align="center" class='inner col1 head1'><b>STF</b></td>
				    <td align="center" class='inner col2 head1'><b>Effective Date</b></td>
				    <td align="center" class='inner col4 head1'><b>Billing Trg CreateDate</b></td>
				    <td align="center" class='inner col4 head1'><b>IsTriggerRequired</b></td>
				    <td align="center" class='inner col2 head1'><b>LineTriggered</b></td>
				    <td align="center" class='inner col2 head1'><b>TriggerProcess</b></td>
				    <td align="center" class='inner col2 head1'><b>TriggerDoneBy</b></td>
				    <td align="center" class='inner col4 head1'><b>AutomaticTriggerError</b></td>
				   <!-- nancy end -->
				</logic:equal>
				<!-- Meenakshi : End -->	
				</tr>
				</table>
					 </div> 	
					 <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
		       			<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>
				<logic:present name="rateRenewalReportList" scope="request">
					<logic:notEmpty  name="rateRenewalReportList" scope="request"> 					
						<logic:iterate id="row" name="rateRenewalReportList" indexId="recordId">
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
							<!--  Saurabh : changes for Usage to show charge data-->
							<logic:equal name="isUsage" value="0">
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmACcountNO"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountManager"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cus_segment"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceSegment"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceDetDescription"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_bandwidth"/></td>
    							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_uom"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="changeTypeName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="companyName"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="createdDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyCode"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDetailNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeTypeName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fromLocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="toLocation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="distance"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lineItemDescription"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_Date"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalCircuitNumber"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="paymentTerm"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taxationName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lcompanyname"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="order_type"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceStage"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="stageName"/></td> 
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="newOrderRemark"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="remarks"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="productName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subProductName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_trigger_date"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTriggerFlag"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="linename"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endHWDateLogic"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="charge_status"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDaysLogic"/></td>
								<td align="left" style="font-size:9px" width="14%"><bean:write name="row" property="startMonthsLogic"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="zoneName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="salesCoordinator"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poAmounts"/></td>
						  		<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargePeriod"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="itemQuantity"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderTotal"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6OrderNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custSINo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6cktid"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="annual_rate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceProductID"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="oldOrderNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAmount"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="oldLineitemAmount"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="txtStartDays"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="txtStartMonth"/></td>
							</logic:equal>
							<!-- Saurabh : End -->
							<!--  Meenaskhi : changes for Usage -->
							<logic:equal name="isUsage" value="1">
								<td align="left" class='inner col2'><bean:write  name="row" property="partyNo"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="partyName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="crmACcountNO"/>&nbsp;</td>
								<td align="left" class='inner col4'><bean:write  name="row" property="accountManager"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="cus_segment"/>&nbsp;</td>
								<td align="left" class='inner col1'><bean:write  name="row" property="serviceSegment"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="verticalDetails"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="regionName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="serviceDetDescription"/>&nbsp;</td>
								<td align="left" class='inner col1'><bean:write  name="row" property="billing_bandwidth"/>&nbsp;</td>
    							<td align="left" class='inner col1'><bean:write  name="row" property="billing_uom"/>&nbsp;</td>
    							<td align="left" class='inner col2'><bean:write  name="row" property="changeTypeName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="orderType"/>&nbsp;</td>
								<td align="left" class='inner col4'><bean:write  name="row" property="companyName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="currencyCode"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="poDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="custPoDetailNo"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="fromLocation"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="toLocation"/>&nbsp;</td>
								<td align="left" class='inner col1'><bean:write  name="row" property="distance"/>&nbsp;</td>
								<td align="left" class='inner col4'><bean:write  name="row" property="lineItemDescription"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="LOC_Date"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="copcApproveDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="logicalSINumber"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="logicalCircuitNumber"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="taxationName"/>&nbsp;</td>
								<td align="left" class='inner col4'><bean:write  name="row" property="lcompanyname"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="order_type"/>&nbsp;</td>
								<td align="left" class='inner col4'><bean:write  name="row" property="serviceStage"/>&nbsp;</td>
								<td align="left" class='inner col4'><bean:write  name="row" property="stageName"/>&nbsp;</td> 
								<td align="left" class='inner col2'><bean:write  name="row" property="newOrderRemark"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="remarks"/>&nbsp;</td>
								<td align="left" class='inner col4'><bean:write  name="row" property="productName"/>&nbsp;</td>
								<td align="left" class='inner col4'><bean:write  name="row" property="subProductName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="billing_trigger_date"/>&nbsp;</td>
								<td align="left" class='inner col1'><bean:write  name="row" property="billingTriggerFlag"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="zoneName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="salesCoordinator"/>&nbsp;</td>
								<td align="left" class='inner col1'><bean:write  name="row" property="poAmounts"/>&nbsp;</td>
						  		<td align="left" class='inner col1'><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
								<td align="left" class='inner col1'><bean:write  name="row" property="itemQuantity"/>&nbsp;</td>
								<td align="left" class='inner col1'><bean:write  name="row" property="orderTotal"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="serviceId"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="m6OrderNo"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="custSINo"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="serviceProductID"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="orderNo"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="oldOrderNo"/>&nbsp;</td>
								<td align="left" class='inner col3' ><bean:write  name="row" property="fx_external_acc_id"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="fxInternalId"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="child_account_creation_status"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="packageID"/>&nbsp;</td>
								<td align="left" class='inner col3' ><bean:write  name="row" property="packageName"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentID"/>&nbsp;</td>
								<td align="left" class='inner col3' ><bean:write  name="row" property="componentName"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="componentInfoID"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="componentDto.componentType"/>&nbsp;</td>
								<!--<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentAmount"/>&nbsp;</td>-->
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentStatus"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.startLogic"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="componentDto.startDateDays"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="componentDto.startDateMonth"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.endLogic"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="componentDto.endDateDays"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="componentDto.endDateMonth"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.startDate"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.end_date"/>&nbsp;</td>
								<!-- nancy start -->
							    <td align="left" class='inner col2'><bean:write  name="row" property="isDifferential"/>&nbsp;</td>
							 	<td align="left" class='inner col1'><bean:write  name="row" property="copcApproverName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="effectiveDate"/>&nbsp;</td>
							    <td align="left" class='inner col4'><bean:write  name="row" property="billingTriggerCreateDate"/>&nbsp;</td>
							    <td align="left" class='inner col4'><bean:write  name="row" property="isTriggerRequired"/>&nbsp;</td>
							    <td align="left" class='inner col2'><bean:write  name="row" property="lineTriggered"/>&nbsp;</td>
							    <td align="left" class='inner col2'><bean:write  name="row" property="triggerProcess"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="triggerDoneBy"/>&nbsp;</td>
							    <td align="left" class='inner col4'><bean:write  name="row" property="automaticTriggerError"/>&nbsp;</td>
							    <!-- nancy end -->
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
		              	<div class='horizontal-scrollraterenewalreportusage' onscroll='reposHorizontal(this);'>
		                  	<div>
		                  	</div>
		              	</div>	
		          	</td>
		     	</tr>	
		     </table>	
		     	 <!-- End Scrolling and Freezing of RATE RENEWAL REPORT(USAGE)    -->
					
					<logic:empty  name="rateRenewalReportList">
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

