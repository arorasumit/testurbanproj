<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- [TRNG21052013004]    Vijay        25 June   - create freez header -->
<!-- [001] Gunjan Singla  added a method populate report usage details -->
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
<title>Disconnection Change Order Report</title>
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
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewDisconnectionChangeOrderReport&usage="+isUsage;	
	//if(isBlankForm()==false)
	//{
	//	return ;
	//}
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
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewDisconnectionChangeOrderReport&usage="+isUsage;
	showLayer();
	myform.submit();
}

//function popitup(url) 
//{
//
//	if(url=="SelectAccount")
//	{
//		var path = "<%=request.getContextPath()%>/bcpAddress.do?method=fetchAccount";		
//		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
//	}
//	if(url=="SelectStatus")
//	{
//
//		var path = "<%=request.getContextPath()%>/reportsAction_Usage.do?method=fetchStatus";		
//		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:330px");
//	}
//	return false;
//}

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
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewDisconnectionChangeOrderReport&usage="+isUsage;
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
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewDisconnectionChangeOrderReport&inExcel=true&usage="+isUsage;
		showLayerExcel();
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.fromDate.value='' ;	
	myForm.toDate.value='' ;	
	myForm.servicename.value='' ;
	myForm.ordersubtype.value='' ;
	myForm.ordermonth.value='' ;
	myForm.verticalDetails.value='' ;
	myForm.srrequest.value=0;
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
	
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	var servicename=myForm.servicename.value;
	var orderMonth=myForm.ordermonth.value;
	var orderSubType=myForm.ordersubtype.value;
	var verticaldetails=myForm.verticalDetails.value;
	var srrequest=myForm.srrequest.value;
	
		if((fromDate == null || trim(fromDate)== "" ) && (toDate == null || trim(toDate) == "" ))
		{
				alert('Please enter From Date and To Date');
				return false;
			
		}
		else
		{	
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
		<html:hidden property="toExcel"/>
		<html:hidden property="reportProcessForLimit"/>
<html:hidden property="reportCurrentCount"/>
	<html:hidden property="interfaceId"/>
		<html:hidden property="isDumpAvailable"/>
		<html:hidden property="dumpFileName"/>	
		<html:hidden property="pagingSorting.sortByColumn"/>
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
		<div border="1" class="head"> <span>Disconnection Change Order Report</span> </div>
		</logic:equal>
		<logic:equal name="isUsage" value="1">
			<div border="1" class="head"> 
			
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>Disconnection Change Order Report(USAGE)</span> 
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
				
						<td width="10px"/>
						<td align="right" style="font-size:9px;">From Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="datefrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">To Date:</td>
						<td nowrap><html:text  property="toDate" styleId="dateTo" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
							</td>
							</tr>
						<tr>
						<td width="10px"/>
						<td align="right" style="font-size:9px">Service Name:</td>
						<td align="left">
							<html:text  property="servicename" maxlength="18" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Service Name')};" ></html:text>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px">Service Order Type:</td>
						<td align="left">
							<html:select  property="ordersubtype" >
								<html:option value="">SELECT</html:option>
								<html:option value="1">NEW</html:option>
							    <html:option value="3">Permanent Disconnection</html:option>
							    <html:option value="4">Permanent Dis After Suspention</html:option>
							    <html:option value="6">Suspention</html:option>
							    <html:option value="7">Resumption</html:option>
								</html:select>
						</td>
						<td align="right" style="font-size:9px;">SR Request:</td>	
						 <td>
							<html:select property="srrequest" style="width:100px;">
							<html:option value="0">All</html:option>
							<html:option value="1">Only SR </html:option>
							<html:option value="2">Non SR</html:option>
							</html:select>
						</td>
						</tr>
						<tr>
						<td width="50px"/>
						<td align="right" style="font-size:9px"> Order Month:</td>
						
						<td align="left" nowrap>
							<html:select  property="ordermonth"  >
							<html:option value="">SELECT</html:option>
							<html:option value="1">January</html:option>
						    <html:option value="2">February</html:option>
						    <html:option value="3">March</html:option>
						    <html:option value="4">April</html:option>
						    <html:option value="5">May</html:option>
						    <html:option value="6">June</html:option>
						    <html:option value="7">July</html:option>
						    <html:option value="8">August</html:option>
						    <html:option value="9">September</html:option>
						    <html:option value="10">October</html:option>
						    <html:option value="11">November</html:option>
						    <html:option value="12">December</html:option>
						
							</html:select>
						</td>
						<td width="50px"/>
						<td align="right" style="font-size:9px"> Vertical:</td>
						<td align="left" nowrap>
							<html:text  property="verticalDetails" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Vertical')};"></html:text>
						</td>
						<td align="left" >
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td width="1%"/>						
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>					
						<td align="left" width="20%"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>							
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Disconnection Change Order Report</b></legend>
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
				  <logic:equal name="isUsage" value="0">
				
					<td align="center" style="font-size:9px" ><b>Logical CKT ID </b></td>
					<td align="center" style="font-size:9px" ><b>Service Name </b></td>
					<td align="center" style="font-size:9px"><b>Line Name </b></td>
					<td align="center" style="font-size:9px"><b>Charge Type </b></td>
					<td align="center" style="font-size:9px"><b>Charge Name</b></td>
					<td align="center" style="font-size:9px"  ><b>Frequency</b></td>
					<td align="center" style="font-size:9px"  ><b>Bill Period </b></td>
					<td align="center" style="font-size:9px"  ><b>Start Date Logic </b></td>
					<td align="center" style="font-size:9px"  ><b>End Date Logic </b></td>
					<td align="center" style="font-size:9px"  ><b>Charge End Date</b></td>
					<td align="center" style="font-size:9px"  ><b>Charge Start Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Account No </b></td>
					<td align="center" style="font-size:9px"  ><b>Credit Period</b></td>
					<td align="center" style="font-size:9px"  ><b>Currency</b></td>
					<td align="center" style="font-size:9px"  ><b>Legal Entity </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Mode </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Trigger Flag </b></td>
					<td align="center" style="font-size:9px"  ><b>Bill Type </b></td>
					<td align="center" style="font-size:9px"  ><b>Bill Format </b></td>
					<td align="center" style="font-size:9px"  ><b>Licence Company </b></td>
					<td align="center" style="font-size:9px"  ><b>Taxation </b></td>
					<td align="center" style="font-size:9px"  ><b>Penalty Clause </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Level </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Level No</b></td>
					<td align="center" style="font-size:9px"  ><b>Store </b></td>
					<td align="center" style="font-size:9px"  ><b>Hardware Type</b></td>
					<td align="center" style="font-size:9px"  ><b>Form C Avalaible</b></td>
					<td align="center" style="font-size:9px"  ><b>Nature Of Sale </b></td>
					<td align="center" style="font-size:9px"  ><b>Type of Sale </b></td>
					<td align="center" style="font-size:9px"  ><b>PO No </b></td>
					<td align="center" style="font-size:9px"  ><b>PO Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Party </b></td>
					<td align="center" style="font-size:9px"  ><b>Annotation </b></td>
					<td align="center" style="font-size:9px"  ><b>Token NO </b></td>
					<td align="center" style="font-size:9px"  ><b>PM Prov Date</b></td>
					<td align="center" style="font-size:9px"  ><b>LOC Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Trigger Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Challen NO </b></td>
					<td align="center" style="font-size:9px"  ><b>Challen Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Child A/C No </b></td>
					<td align="center" style="font-size:9px"  ><b>Child A/C FX Status </b></td>
					<td align="center" style="font-size:9px"  ><b>Order Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Order Type </b></td>
					<td align="center" style="font-size:9px"  ><b>Service Order Type </b></td>
					<td align="center" style="font-size:9px"  ><b>COPC Approve Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Trigger Creation Date</b></td>
					<td align="center" style="font-size:9px"  ><b> Cust Logical SI No.</b></td>
					<td align="center" style="font-size:9px"  ><b>Hardware Flag </b></td>
					<td align="center" style="font-size:9px"  ><b>Charge Type ID </b></td>
					<td align="center" style="font-size:9px"  ><b>Service Stage </b></td>
					<td align="center" style="font-size:9px"  ><b>Order Stage </b></td>
					<td align="center" style="font-size:9px"  ><b>Account Mgr </b></td>
					<td align="center" style="font-size:9px"  ><b>Project Mgr </b></td>
					<td align="center" style="font-size:9px"  ><b>Order Creation Date </b></td>
					<td align="center" style="font-size:9px"  ><b>RFS Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Cust PO Rec Date </b></td>
					<td align="center" style="font-size:9px"  ><b>Charge Status </b></td>
					<td align="center" style="font-size:9px"  ><b>Vertical </b></td>
					<td align="center" style="font-size:9px"  ><b>Region </b></td>
					<td align="center" style="font-size:9px"  ><b>Demo Type </b></td>
					<td align="center" style="font-size:9px"  ><b>New Order Remark</b></td>
					<td align="center" style="font-size:9px"  ><b>Order Stage Description </b></td>
					<td align="center" style="font-size:9px"  ><b>Mocn No.</b></td>
					<td align="center" style="font-size:9px"  ><b>Disconnection Remarks </b></td>
					<td align="center" style="font-size:9px"  ><b>Sr. No </b></td>
					<td align="center" style="font-size:9px"  ><b>Request Receive Date </b></td>
					<td align="center" style="font-size:9px"  ><b>LineItemNo </b></td>
					<td align="center" style="font-size:9px"  ><b>Order Months </b></td>
					<td align="center" style="font-size:9px"  ><b>Ckt Id </b></td>
					<td align="center" style="font-size:9px"  ><b>Standard Reason </b></td>
					<td align="center" style="font-size:9px"  ><b>Bandwidth </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Bandwidth </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Bandwidth UOM </b></td>
					<td align="center" style="font-size:9px"  ><b>Bandwidth Attribute </b></td>
					<td align="center" style="font-size:9px"  ><b>Bandwidth UOM </b></td>
					<td align="center" style="font-size:9px"  ><b> Rate Code</b></td>
					<td align="center" style="font-size:9px"  ><b>Lat Mile Media </b></td>
					<td align="center" style="font-size:9px"  ><b>Last Mile Remarks </b></td>
					<td align="center" style="font-size:9px"  ><b>Chargeable Distance</b></td>
					<td align="center" style="font-size:9px"  ><b>Last Mile Provider</b></td>
					<td align="center" style="font-size:9px"  ><b>Link Type</b></td>
					<td align="center" style="font-size:9px"  ><b>Dispatch Address</b></td>
					<td align="center" style="font-size:9px"  ><b>Indicative Value</b></td>
					<td align="center" style="font-size:9px"  ><b>Product Name</b></td>
					<td align="center" style="font-size:9px"  ><b>Cust Po Date</b></td>
					<td align="center" style="font-size:9px"  ><b>Cust Po Number</b></td>
					<td align="center" style="font-size:9px"  ><b>Loc Number</b></td>
					<td align="center" style="font-size:9px"  ><b>Pri Loc</b></td>
					<td align="center" style="font-size:9px"  ><b>Product</b></td>
					<td align="center" style="font-size:9px"  ><b>Ratio</b></td>
					<td align="center" style="font-size:9px"  ><b>Sec Loc</b></td>
					<td align="center" style="font-size:9px"  ><b>Sub Product</b></td>
					<td align="center" style="font-size:9px"  ><b>Token Number</b></td>
					<td align="center" style="font-size:9px"  ><b>Order Number</b></td>
					<td align="center" style="font-size:9px"  ><b>Charge Hdr Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Order Line Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Service No</b></td>
					<td align="center" style="font-size:9px"  ><b>Inv Amt</b></td>
					<td align="center" style="font-size:9px"  ><b>Amt</b></td>
					<td align="center" style="font-size:9px"  ><b>Total Amount</b></td>
					<td align="center" style="font-size:9px"  ><b>Advance</b></td>
					<td align="center" style="font-size:9px"  ><b>Installment Rate</b></td>
					<td align="center" style="font-size:9px"  ><b>Start Date Days</b></td>
					<td align="center" style="font-size:9px"  ><b>Start Date Months</b></td>
					<td align="center" style="font-size:9px"  ><b>End Date Days</b></td>
					<td align="center" style="font-size:9px"  ><b>End Date Months</b></td>
					<td align="center" style="font-size:9px"  ><b>Annual Rate</b></td>
					<td align="center" style="font-size:9px"  ><b>Contract Period Mnths</b></td>
					<td align="center" style="font-size:9px"  ><b>Commitment Period</b></td>
					<td align="center" style="font-size:9px"  ><b>Notice Period</b></td>
					<td align="center" style="font-size:9px"  ><b>Period In Month</b></td>
					<td align="center" style="font-size:9px"  ><b>Tot Po Amt</b></td>
					<td align="center" style="font-size:9px"  ><b>Party Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Cust Account Id</b></td>
					<td align="center" style="font-size:9px"  ><b>M6 Product Id</b></td>
					<td align="center" style="font-size:9px"  ><b>M6 Order Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Ib Service List Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Ib Order Line Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Ib Pk Charges Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Ib Charge Hdr Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Pre Crm Order Id</b></td>
					<td align="center" style="font-size:9px"  ><b>M6 Ckt Dtls Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Pk Charges Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Business Serial No</b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Location From</b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Location To</b></td>
					
				 </logic:equal>
				<logic:equal name="isUsage" value="1">
					<td align="center" class='inner col2 head1'  ><b>Logical SI No </b></td>
					<td align="center" class='inner col3 head1'  ><b>Service Name </b></td>
					<td align="center" class='inner col3 head1'  ><b>Line Name </b></td>					
					<td align="center" class='inner col2 head1'  ><b>Account No </b></td>
					<td align="center" class='inner col2 head1'  ><b>Credit Period</b></td>
					<td align="center" class='inner col2 head1'  ><b>Currency</b></td>
					<td align="center" class='inner col4 head1'  ><b>Legal Entity </b></td>
					<td align="center" class='inner col2 head1'  ><b>Billing Mode </b></td>	
					<td align="center" class='inner col2 head1'  ><b>Billing Trigger Flag </b></td>				
					<td align="center" class='inner col2 head1'  ><b>Bill Type </b></td>
					<td align="center" class='inner col2 head1'  ><b>Bill Format </b></td>
					<td align="center" class='inner col3 head1'  ><b>Licence Company </b></td>
					<td align="center" class='inner col2 head1'  ><b>Taxation </b></td>
					<td align="center" class='inner col2 head1'  ><b>Penalty Clause </b></td>
					<td align="center" class='inner col2 head1'  ><b>Billing Level </b></td>
					<td align="center" class='inner col2 head1'  ><b>Billing Level No</b></td>					
					<td align="center" class='inner col2 head1'  ><b>PO No </b></td>
					<td align="center" class='inner col2 head1'  ><b>PO Date </b></td>
					<td align="center" class='inner col2 head1'  ><b>Party </b></td>										
					<td align="center" class='inner col2 head1'  ><b>PM Prov Date</b></td>
					<td align="center" class='inner col2 head1'  ><b>LOC Date </b></td>
					<td align="center" class='inner col2 head1'  ><b>Billing Trigger Date </b></td>					
					<td align="center" class='inner col3 head1'  ><b>Child A/C No </b></td>
					<td align="center" class='inner col2 head1'  ><b>Child A/C Internal No </b></td>
					<td align="center" class='inner col2 head1'  ><b>Child A/C FX Status </b></td>
					<td align="center" class='inner col2 head1'  ><b>Order Date </b></td>
					<td align="center" class='inner col2 head1'  ><b>Order Type </b></td>
					<td align="center" class='inner col2 head1'  ><b>Service Order Type </b></td>
					<td align="center" class='inner col2 head1'  ><b>COPC Approve Date </b></td>					
					<td align="center" class='inner col2 head1'  ><b> Cust Logical SI No.</b></td>										
					<td align="center" class='inner col2 head1'  ><b>Service Stage </b></td>
					<td align="center" class='inner col2 head1'  ><b>Order Stage </b></td>
					<td align="center" class='inner col4 head1'  ><b>Account Mgr </b></td>
					<td align="center" class='inner col4 head1'  ><b>Project Mgr </b></td>
					<td align="center" class='inner col2 head1'  ><b>Order Creation Date </b></td>
					<td align="center" class='inner col2 head1'  ><b>RFS Date </b></td>
					<td align="center" class='inner col2 head1'  ><b>Cust PO Rec Date </b></td>					
					<td align="center" class='inner col2 head1'  ><b>Vertical </b></td>
					<td align="center" class='inner col2 head1'  ><b>Region </b></td>
					<td align="center" class='inner col2 head1'  ><b>Demo Type </b></td>
					<td align="center" class='inner col4 head1'  ><b>New Order Remark</b></td>
					<td align="center" class='inner col4 head1'  ><b>Order Stage Description </b></td>
					<td align="center" class='inner col3 head1'  ><b>Disconnection Remarks </b></td>										
					<td align="center" class='inner col2 head1'  ><b>LineItemNo </b></td>
					<td align="center" class='inner col2 head1'  ><b>Order Months </b></td>
					<td align="center" class='inner col4 head1'  ><b>Ckt Id </b></td>
					<td align="center" class='inner col2 head1'  ><b>Standard Reason </b></td>
					<td align="center" class='inner col2 head1'  ><b>Bandwidth </b></td>
					<td align="center" class='inner col2 head1'  ><b>Billing Bandwidth </b></td>
					<td align="center" class='inner col2 head1'  ><b>Billing Bandwidth UOM </b></td>
					<!--<td align="center" class='inner col2 head1'  ><b>Bandwidth Attribute </b></td>-->
					<td align="center" class='inner col2 head1'  ><b>Bandwidth UOM </b></td>
					<!--<td align="center" class='inner col2 head1'  ><b> Rate Code</b></td>
					<td align="center" class='inner col2 head1'  ><b>Lat Mile Media </b></td>
					<td align="center" class='inner col2 head1'  ><b>Last Mile Remarks </b></td>-->
					<td align="center" class='inner col2 head1'  ><b>Chargeable Distance</b></td>
					<!--<td align="center" class='inner col2 head1'  ><b>Last Mile Provider</b></td>
					<td align="center" class='inner col2 head1'  ><b>Link Type</b></td>					
					<td align="center" class='inner col2 head1'  ><b>Indicative Value</b></td>-->
					<td align="center" class='inner col4 head1'  ><b>Product Name</b></td>
					<td align="center" class='inner col2 head1'  ><b>Cust Po Date</b></td>
					<td align="center" class='inner col2 head1'  ><b>Cust Po Number</b></td>
					<td align="center" class='inner col2 head1'  ><b>Loc Number</b></td>
					<td align="center" class='inner col3 head1'  ><b>Pri Loc</b></td>
					<td align="center" class='inner col2 head1'  ><b>Product</b></td>
					<td align="center" class='inner col2 head1'  ><b>Ratio</b></td>
					<td align="center" class='inner col3 head1'  ><b>Sec Loc</b></td>
					<td align="center" class='inner col2 head1'  ><b>Sub Product</b></td>					
					<td align="center" class='inner col2 head1'  ><b>Order Number</b></td>					
					<td align="center" class='inner col2 head1'  ><b>Order Line Id</b></td>
					<td align="center" class='inner col2 head1'  ><b>Cust Logical Si Id</b></td>
					<td align="center" class='inner col2 head1'  ><b>Service No</b></td>										
					<td align="center" class='inner col2 head1'  ><b>Total Amount</b></td>															
					<td align="center" class='inner col2 head1'  ><b>Contract Period Mnths</b></td>
					<td align="center" class='inner col2 head1'  ><b>Commitment Period</b></td>
					<td align="center" class='inner col2 head1'  ><b>Notice Period</b></td>
					<td align="center" class='inner col2 head1'  ><b>Period In Month</b></td>
					<td align="center" class='inner col2 head1'  ><b>Tot Po Amt</b></td>
					<td align="center" class='inner col2 head1'  ><b>Party Id</b></td>
					<td align="center" class='inner col2 head1'  ><b>Cust Account Id</b></td>
					<td align="center" class='inner col2 head1'  ><b>M6 Ckt Id</b></td>
					<td align="center" class='inner col2 head1'  ><b>M6 Order Id</b></td>					
					<td align="center" class='inner col3 head1'  ><b>Billing Location From</b></td>
					<td align="center" class='inner col3 head1'  ><b>Billing Location To</b></td>
					<td align="center" class='inner col2 head1'  ><b>Package ID</b></td>
					<td align="center" class='inner col4 head1'  ><b>Package Name</b></td>								
					<td align="center" class='inner col2 head1'  ><b>Component InfoID</b></td>
					<td align="center" class='inner col2 head1'  ><b>Component ID</b></td>
					<td align="center" class='inner col4 head1'  ><b>Component Name</b></td>
					<td align="center" class='inner col2 head1'  ><b>Component Status</b></td>
					<td align="center" class='inner col2 head1'  ><b>Component Start Logic</b></td>
					<td align="center" class='inner col2 head1'  ><b>Component Start Date</b></td>
					<td align="center" class='inner col2 head1'  ><b>Component End Logic</b></td>
					<td align="center" class='inner col2 head1'  ><b>Component End Date</b></td>
					<td align="center" class='inner col1 head1'  ><b>Start Date Days</b></td>
					<td align="center" class='inner col1 head1'  ><b>Start Date Months</b></td>
					<td align="center" class='inner col1 head1'  ><b>End Date Days</b></td>
					<td align="center" class='inner col1 head1'  ><b>End Date Months</b></td>						
					<td align="center" class='inner col2 head1'  ><b>Component Type</b></td>
					<!--<td align="center" class='inner col2 head1'  ><b>Component Cyclic/Non-Cyclic Amount</b></td>-->							
					<td align="center" class='inner col2 head1'  ><b>Component FX Instance Id</b></td>					
					<td align="center" class='inner col2 head1'  ><b>Start Token No</b></td>
					<!--<td align="center" class='inner col2 head1'  ><b>Start FX Status</b></td>-->
					<td align="center" class='inner col2 head1'  ><b>End Token No</b></td>
					<!--<td align="center" class='inner col2 head1'  ><b>End FX Status</b></td>-->
					<!-- Changes mad to add extra  in View disconnection change of report@ satish starts -->
					<td align="center" class='inner col2 head1' ><b>BILLING TRIGGER CREATEDATE</b></td>
					<td align="center" class='inner col4 head1' ><b>SR NO</b></td>
					<td align="center" class='inner col2 head1' ><b>Customer SEGMENT CODE</b></td>
					<td align="center" class='inner col2 head1' ><b>Desired Due Date</b></td>
					<td align="center" class='inner col4 head1' ><b>Derived Disconnection Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Is Trigger Required</b></td>
					<td align="center" class='inner col2 head1' ><b>Line Triggered</b></td>
					<td align="center" class='inner col4 head1'><b>Trigger Process</b></td>
					<td align="center" class='inner col2 head1' ><b>Trigger DoneBy</b></td>
					<td align="center" class='inner col6 head1' ><b>Automatic Trigger Error</b></td> 
					
					<!-- Changes mad to add extra  in View disconnection change of report@ satish ends -->
			     </logic:equal>
		      </tr>
		      </table>
					 </div> 	
					 <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
		       			<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>
		       			
				<logic:present name="DisconnectChangeOrderReport" scope="request">
					<logic:notEmpty  name="DisconnectChangeOrderReport" scope="request"> 					
						<logic:iterate id="row" name="DisconnectChangeOrderReport" indexId="recordId">
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
						<logic:equal name="isUsage" value="0">								
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="logicalCircuitId"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="linename"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="frequencyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bill_period"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="startDateLogic"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="endDateLogic"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeEndDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="startDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="creditPeriod"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="currencyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="entity"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingMode"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingformat"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="licCompanyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="taxation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="penaltyClause"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevel"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevelId"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="store"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="hardwareType"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="formAvailable"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="nature_sale"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="type_sale"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="party"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="annitation"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="tokenno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="pm_pro_date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="locDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingTriggerDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="challenno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="challendate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="child_act_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="child_ac_fxstatus"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="order_type"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceOrderType"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="copcapprovaldate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingtrigger_createdate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="hardware_flag"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeTypeID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceStage"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderStage"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="actmgrname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="prjmngname"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="rfs_date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cust_po_rec_date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="charge_status"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="verticalDetails"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="region"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="demo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="newOrderRemark"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderStageDescription"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="mocn_no"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="disconnection_remarks"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="srno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="request_rec_date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ordermonth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ckt_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="standard_reason"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bandwaidth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_bandwidth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_uom"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bandwidth_att"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="uom"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="rate_code"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="last_mile_media"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="last_mile_remarks"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeable_Distance"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="last_mile_provider"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="link_type"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="dispatchAddress1"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="indicative_value"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="productName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDetailNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="locno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="primarylocation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="prodAlisName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ratio"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="seclocation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="sub_linename"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="tokenNO"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="charge_hdr_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderLineNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceId"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineItemAmount"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="amt"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="totalAmountIncludingCurrent"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="advance"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="installation_addressaa1"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="startDateDays"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="startDateMonth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="endDateDays"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="endDateMonth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="annualRate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="contractMonths"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="commitmentPeriod"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="noticePeriod"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="periodsInMonths"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="totalPoAmt"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="party_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cust_act_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6_prod_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6_order_id"/></td>
						<td align="left" style="font-size:9px" width="14%">&nbsp;</td>
						<td align="left" style="font-size:9px" width="14%">&nbsp;</td>
						<td align="left" style="font-size:9px" width="14%">&nbsp;</td>
						<td align="left" style="font-size:9px" width="14%">&nbsp;</td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="pre_crmorderid"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6cktid"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="pk_charge_id"/></td>
					    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="business_serial_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_location_from"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_location_to"/></td>
					
					</logic:equal>
					<logic:equal name="isUsage" value="1">								
							<td align="left" class='inner col2'><bean:write  name="row" property="customer_logicalSINumber"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="serviceName"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="linename"/>&nbsp;</td>						    
							<td align="left" class='inner col2'><bean:write  name="row" property="crmAccountNoString"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="creditPeriodName"/>&nbsp;</td>
						    <td align="left" class='inner col2'><bean:write  name="row" property="currencyName"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="entity"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="billingMode"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="billing_Trigger_Flag"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="billingTypeName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="billingformat"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="licCompanyName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="taxation"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="penaltyClause"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="billingLevelName"/>&nbsp;</td>
						    <td align="left" class='inner col2'><bean:write  name="row" property="billingLevelId"/>&nbsp;</td>							
							<td align="left" class='inner col2'><bean:write  name="row" property="poNumber"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="poDate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="party"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="pm_pro_date"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="locDate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="billingTriggerDate"/>&nbsp;</td>							
							<td align="left" class='inner col3'><bean:write  name="row" property="child_act_no"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="fx_internal_acc_id"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="child_ac_fxstatus"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="orderDate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="order_type"/>&nbsp;</td>
						    <td align="left" class='inner col2'><bean:write  name="row" property="serviceOrderType"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="copcapprovaldate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="customer_logicalSINumber"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="serviceStage"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="orderStage"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="actmgrname"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="prjmngname"/>&nbsp;</td>
						    <td align="left" class='inner col2'><bean:write  name="row" property="orderDate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="rfs_date"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="cust_po_rec_date"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="verticalDetails"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="region"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="demo"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="newOrderRemark"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="orderStageDescription"/>&nbsp;</td>
						    <td align="left" class='inner col3'><bean:write  name="row" property="disconnection_remarks"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="lineno"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="ordermonth"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="ckt_id"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="standard_reason"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="bandwaidth"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="billing_bandwidth"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="billing_uom"/>&nbsp;</td>
						    <!--<td align="left" class='inner col2'><bean:write  name="row" property="bandwidth_att"/>&nbsp;</td>-->
							<td align="left" class='inner col2'><bean:write  name="row" property="uom"/>&nbsp;</td>
							<!--<td align="left" class='inner col2'><bean:write  name="row" property="rate_code"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="last_mile_media"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="last_mile_remarks"/>&nbsp;</td>-->
							<td align="left" class='inner col2'><bean:write  name="row" property="chargeable_Distance"/>&nbsp;</td>
							<!--<td align="left" class='inner col2'><bean:write  name="row" property="last_mile_provider"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="link_type"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="indicative_value"/>&nbsp;</td>-->
						    <td align="left" class='inner col4'><bean:write  name="row" property="productName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="custPoDate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="custPoDetailNo"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="locno"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="primarylocation"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="prodAlisName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="ratio"/>&nbsp;</td>							
							<td align="left" class='inner col3'><bean:write  name="row" property="seclocation"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="sub_linename"/>&nbsp;</td>
						    <td align="left" class='inner col2'><bean:write  name="row" property="orderNo"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="orderLineNumber"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="customer_logicalSINumber"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="serviceId"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="totalAmountIncludingCurrent"/>&nbsp;</td>							
							<td align="left" class='inner col2'><bean:write  name="row" property="contractMonths"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="commitmentPeriod"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="noticePeriod"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="periodsInMonths"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="totalPoAmt"/>&nbsp;</td>
						    <td align="left" class='inner col2'><bean:write  name="row" property="party_id"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="cust_act_id"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="m6cktid"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="m6_order_id"/>&nbsp;</td>														
							<td align="left" class='inner col3'><bean:write  name="row" property="billing_location_from"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="billing_location_to"/>&nbsp;</td>
							<!--  Manisha : changes for Usage -->																	
							<td align="left" class='inner col2' ><bean:write  name="row" property="packageID"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="packageName"/>&nbsp;</td>				
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentInfoID"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentID"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="componentName"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentStatus"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.startLogic"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.startDate"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.endLogic"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.end_date"/>&nbsp;</td>
							<td align="left" class='inner col1' ><bean:write  name="row" property="startDateDays"/>&nbsp;</td>
							<td align="left" class='inner col1' ><bean:write  name="row" property="startDateMonth"/>&nbsp;</td>
							<td align="left" class='inner col1' ><bean:write  name="row" property="endDateDays"/>&nbsp;</td>
							<td align="left" class='inner col1' ><bean:write  name="row" property="endDateMonth"/>&nbsp;</td>																
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentType"/>&nbsp;</td>
							<!--<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentAmount"/>&nbsp;</td>-->
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentInstanceID"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.fxTokenNo"/>&nbsp;</td>
							<!--<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.fxStartStatus"/>&nbsp;</td>-->	
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.endTokenNo"/>&nbsp;</td>
							<!--<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.endFxStatus"/></td>-->																
							<!--  Manisha : End -->						
							<td align="left" class='inner col2' ><bean:write  name="row" property="billingtrigger_createdate"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="srno"/>&nbsp;</td>				
							<td align="left" class='inner col2' ><bean:write  name="row" property="cus_segment"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="desiredDueDate"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="derivedDisconnectionDate"/>&nbsp;</td>				
							<td align="left" class='inner col2' ><bean:write  name="row" property="isTriggerRequired"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="lineTriggered"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="triggerProcess"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="triggerDoneBy"/>&nbsp;</td>
							<td align="left" class='inner col6' ><bean:write  name="row" property="automaticTriggerError"/>&nbsp;</td>	
												
	  					</logic:equal>
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
		              	<div class='horizontal-scrolldisconnectchangeorderreportusage' onscroll='reposHorizontal(this);'>
		                  	<div>
		                  	</div>
		              	</div>	
		          	</td>
		     	</tr>	
		     </table>	
		     	 <!-- End Scrolling and Freezing of Disconnection Change Order Report (Usage)    -->
				   
					<logic:empty  name="DisconnectChangeOrderReport">
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
