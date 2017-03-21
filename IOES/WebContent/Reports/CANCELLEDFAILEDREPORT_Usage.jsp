<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- [TRNG21052013004]    Vijay        24 June   - create freez header -->
<!-- [001] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
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
<title>Cancelled Failed Line Report</title>
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
	//myform.method.value='viewCancelledFailedLineReport&usage='+isUsage';	
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewCancelledFailedLineReport&usage="+isUsage;
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
	//myform.method.value='viewCancelledFailedLineReport&usage='+isUsage;	
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewCancelledFailedLineReport&usage="+isUsage;
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
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewCancelledFailedLineReport&usage="+isUsage;
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
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewCancelledFailedLineReport&inExcel=true&usage="+isUsage;
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
	

}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
	
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	var servicename=myForm.servicename.value;
	
	
		if((fromDate == null & toDate == null) || ( trim(fromDate)=="" & trim(toDate) == "")){
			alert("Please enter From Date and To Date!");
		return false;
		
		} 	
		if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
		{
			alert("Please enter From Order Date ");
			return false;
		}
		else if((toDate == null || trim(toDate)== "" ) && (fromDate != null && trim(fromDate) != "" ))
		{
			alert("Please enter To Order Date ");
			return false;
		}
		
		if(dateCompare(fromDate,toDate)==1)
			{			
				alert("From Order Date cannot be greater than To Order  Date");
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
			<div border="1" class="head"> <span>Cancelled Failed Line Report</span> </div>
		</logic:equal>
		<logic:equal name="isUsage" value="1">
			<div border="1" class="head"> 
			
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>Cancelled Failed Line Report(USAGE)</span> 
			</td>
			<logic:present  name="formBean" property="isDumpAvailable">
				<logic:equal  name="formBean" property="isDumpAvailable" value="Y">
					<td align="right">
					<span style="text-align: right;">
					<a  href="#" title="Download Dump" onClick="javascript:downloadDump('searchForm','<bean:write  name="formBean" property="dumpFileName"/>');">
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
					
					 <td width="50px"/>
						<td align="right" style="font-size:9px">Service Name</td>
						<td align="left" nowrap>
							<html:text  property="servicename" style="width:90px;float:left;" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'servicename')};"></html:text>
						</td>
					
						<td width="10px"/>
						<td align="right" style="font-size:9px;">From Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="dateFrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
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
						<td width="60px"/>
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
						<td width="1%"/>
						<td align="left" >
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td align="left" width="20%"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div>
						</td>
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Cancelled Failed Line Report</b></legend>
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
				    <td align="center" style="font-size:9px" ><b>Party No </b></td>
					<td align="center" style="font-size:9px" ><b>Account No </b></td>
					<td align="center" style="font-size:9px" ><b>Party Name </b></td>
					<td align="center" style="font-size:9px" ><b>Vertical </b></td>
					<td align="center" style="font-size:9px" ><b>Region </b></td>
					<td align="center" style="font-size:9px" ><b> 	
					<logic:present name="CancelledFailedLineReport" scope="request">
							<logic:notEmpty  name="CancelledFailedLineReport" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Order No</a>
							</logic:notEmpty>
							<logic:empty  name="CancelledFailedLineReport" scope="request">
								Order No
							</logic:empty>
						</logic:present>
						</b></td>
					<td align="center" style="font-size:9px" ><b>Order Type</b></td>
					<td align="center" style="font-size:9px" ><b>Service Name </b></td>
					<td align="center" style="font-size:9px" ><b>Cust Logical SI No </b></td>
					<td align="center" style="font-size:9px" ><b>Line Name</b></td>
					<td align="center" style="font-size:9px" ><b>Sub Type </b></td>
					<td align="center" style="font-size:9px" ><b>Circuit Id </b></td>
					<td align="center" style="font-size:9px" ><b>Billing Bandwidth </b></td>
					<td align="center" style="font-size:9px" ><b>Billing UOM </b></td>
					<td align="center" style="font-size:9px" ><b>KMS Distance </b></td>
					<td align="center" style="font-size:9px" ><b>Ratio </b></td>
					<td align="center" style="font-size:9px" ><b>Location From </b></td>
					<td align="center" style="font-size:9px" ><b>Location To </b></td>
					<td align="center" style="font-size:9px" ><b>Pri Location </b></td>
					<td align="center" style="font-size:9px" ><b>Sec Location </b></td>
					<td align="center" style="font-size:9px" ><b>Product </b></td>
					<td align="center" style="font-size:9px" ><b>Sub Product </b></td>
					<td align="center" style="font-size:9px" ><b>Legal Entity </b></td>
					<td align="center" style="font-size:9px" ><b>Licence Company </b></td>
					<td align="center" style="font-size:9px" ><b>Currency </b></td>
					<td align="center" style="font-size:9px" ><b>Credit period </b></td>
					<td align="center" style="font-size:9px" ><b>Bill Type </b></td>
					<td align="center" style="font-size:9px" ><b>Bill Format </b></td>
					<td align="center" style="font-size:9px" ><b>Bill Level </b></td>
					<td align="center" style="font-size:9px" ><b>Bill Level No </b></td>
					<td align="center" style="font-size:9px" ><b>Taxation </b></td>
					<td align="center" style="font-size:9px" ><b>Hardware Type </b></td>
					<td align="center" style="font-size:9px" ><b>H/W Flag </b></td>
					<td align="center" style="font-size:9px" ><b>Store </b></td>
					<td align="center" style="font-size:9px" ><b>Type Of Sale </b></td>
					<td align="center" style="font-size:9px" ><b>Bill Period </b></td>
					<td align="center" style="font-size:9px" ><b>Bill Mode </b></td>
					<td align="center" style="font-size:9px" ><b>Po No </b></td>
					<td align="center" style="font-size:9px" ><b>PO Date </b></td>
					<td align="center" style="font-size:9px" ><b>Period in month </b></td>
					<td align="center" style="font-size:9px" ><b>Total PO Amt </b></td>
					<td align="center" style="font-size:9px" ><b>Cust PO Rec Date </b></td>
					<td align="center" style="font-size:9px" ><b>Cust PO No </b></td>
					<td align="center" style="font-size:9px" ><b>Cust PO Date </b></td>
					<td align="center" style="font-size:9px" ><b>Cust PoTotal Amt</b></td>
					<td align="center" style="font-size:9px" ><b>Charge Type</b></td>
					<td align="center" style="font-size:9px" ><b>Charge Name </b></td>
					<td align="center" style="font-size:9px" ><b>Charge Start Date </b></td>
					<td align="center" style="font-size:9px" ><b>Inv Amt </b></td>
					<td align="center" style="font-size:9px" ><b>Amt </b></td>
					<td align="center" style="font-size:9px" ><b>Annotation </b></td>
					<td align="center" style="font-size:9px" ><b>Token No </b></td>
					<td align="center" style="font-size:9px" ><b>Fx Charge Start Status </b></td>
					<td align="center" style="font-size:9px" ><b>Fx Status </b></td>
					<td align="center" style="font-size:9px" ><b>Business No </b></td>
					<td align="center" style="font-size:9px" ><b>Opms Act Id </b></td>
					<td align="center" style="font-size:9px" ><b>Line No </b></td>
				</logic:equal>	
				<!-- Saurabh : End -->
				
				<!--  Meenakshi : change for usage -->		
				<logic:equal name="isUsage" value="1">
					
					<td align="center" class='inner col2 head1' ><b>Party No </b></td>
					<td align="center" class='inner col2 head1' ><b>Account No </b></td>
					<td align="center" class='inner col3 head1' ><b>Party Name </b></td>
					<td align="center" class='inner col2 head1' ><b>Vertical </b></td>
					<td align="center" class='inner col2 head1' ><b>Region </b></td>
					<td align="center" class='inner col2 head1' ><b> 	
					<logic:present name="CancelledFailedLineReport" scope="request">
							<logic:notEmpty  name="CancelledFailedLineReport" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Order No</a>
							</logic:notEmpty>
							<logic:empty  name="CancelledFailedLineReport" scope="request">
								Order No
							</logic:empty>
						</logic:present>
						</b></td>
					<td align="center" class='inner col2 head1' ><b>Order Type</b></td>
					<td align="center" class='inner col3 head1' ><b>Service Name </b></td>
					<td align="center" class='inner col2 head1' ><b>Cust Logical SI No </b></td>
					<td align="center" class='inner col3 head1' ><b>Line Name</b></td>
					<td align="center" class='inner col2 head1' ><b>Sub Type </b></td>
					<td align="center" class='inner col2 head1' ><b>Circuit Id </b></td>
					<td align="center" class='inner col1 head1' ><b>Billing Bandwidth </b></td>
					<td align="center" class='inner col1 head1' ><b>Billing UOM </b></td>
					<td align="center" class='inner col1 head1' ><b>KMS Distance </b></td>
					<td align="center" class='inner col1 head1' ><b>Ratio </b></td>
					<td align="center" class='inner col2 head1' ><b>Location From </b></td>
					<td align="center" class='inner col2 head1' ><b>Location To </b></td>
					<td align="center" class='inner col3 head1' ><b>Pri Location </b></td>
					<td align="center" class='inner col3 head1' ><b>Sec Location </b></td>
					<td align="center" class='inner col2 head1' ><b>Product </b></td>
					<td align="center" class='inner col2 head1' ><b>Sub Product </b></td>
					<td align="center" class='inner col2 head1' ><b>Legal Entity </b></td>
					<td align="center" class='inner col3 head1' ><b>Licence Company </b></td>
					<td align="center" class='inner col2 head1' ><b>Currency </b></td>
					<td align="center" class='inner col2 head1' ><b>Credit period </b></td>
					<td align="center" class='inner col2 head1' ><b>Bill Type </b></td>
					<td align="center" class='inner col2 head1' ><b>Bill Format </b></td>
					<td align="center" class='inner col2 head1' ><b>Bill Level </b></td>
					<td align="center" class='inner col2 head1' ><b>Bill Level No </b></td>
					<td align="center" class='inner col2 head1' ><b>Taxation </b></td>
					<td align="center" class='inner col2 head1' ><b>Bill Period </b></td>
					<td align="center" class='inner col2 head1' ><b>Bill Mode </b></td>
					<td align="center" class='inner col2 head1' ><b>Po No </b></td>
					<td align="center" class='inner col2 head1' ><b>PO Date </b></td>
					<td align="center" class='inner col1 head1' ><b>Period in month </b></td>
					<td align="center" class='inner col2 head1' ><b>Total PO Amt </b></td>
					<td align="center" class='inner col2 head1' ><b>Cust PO Rec Date </b></td>
					<td align="center" class='inner col2 head1' ><b>Cust PO No </b></td>
					<td align="center" class='inner col2 head1' ><b>Cust PO Date </b></td>
					<td align="center" class='inner col2 head1' ><b>Service No</b></td>
					<td align="center" class='inner col2 head1' ><b>Logical SI No</b></td>
					<td align="center" class='inner col2 head1' ><b>Line No </b></td>
					<td align="center" class='inner col4 head1' ><b>Child Account Number</b></td>
					<td align="center" class='inner col2 head1' ><b>Child Account No Internal</b></td>
					<td align="center" class='inner col2 head1' ><b>Child Account Fx Status</b></td>
					<td align="center" class='inner col2 head1' ><b>Package ID</b></td>
					<td align="center" class='inner col4 head1' ><b>Package Name</b></td>
					<td align="center" class='inner col2 head1' ><b>Component ID</b></td>
					<td align="center" class='inner col4 head1' ><b>Component Name</b></td>
					<td align="center" class='inner col2 head1' ><b>Component InfoID</b></td>
					<td align="center" class='inner col2 head1' ><b>Component Type</b></td>
					<!--<td align="center" class='inner col2 head1' ><b>rental / nrc component amount</b></td>-->
					<td align="center" class='inner col2 head1' ><b>Component Status</b></td>
					<td align="center" class='inner col2 head1' ><b>Component Start Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Start Token No</b></td>
					<td align="center" class='inner col2 head1' ><b>Component FX Instance Id</b></td>
					<td align="center" class='inner col2 head1' ><b>Start FX Status</b></td>

				</logic:equal>
			<!-- Meenakshi : End -->		
			</tr>
			</table>
			 </div> 	
			 <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
       			<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>
				<logic:present name="CancelledFailedLineReport" scope="request">
					<logic:notEmpty  name="CancelledFailedLineReport" scope="request"> 					
						<logic:iterate id="row" name="CancelledFailedLineReport" indexId="recordId">
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
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="partyNo"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountID"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="partyName"/></td>
						    <td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="verticalDetails"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="regionName"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderNumber"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="order_type"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceName"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="linename"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ordersubtype"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ckt_id"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_bandwidth"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_uom"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="distance"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ratio"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_from"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_to"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="primarylocation"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="seclocation"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="productName"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="subProductName"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="entity"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lcompanyname"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="currencyName"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="creditPeriod"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingTypeName"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingFormatName"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevel"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevelNo"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="taxation"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="hardwaretypeName"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="hardware_flag"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="storename"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="saleType"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bill_period"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingMode"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poNumber"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poDate"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="contractPeriod"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="totalAmountIncludingCurrent"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poReceiveDate"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDetailNo"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDate"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cust_total_poamt"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeTypeName"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeName"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeEndDate"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeAmount"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineamt"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="annitation"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="tokenno"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="fx_sd_status"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="fx_status"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="business_serial_no"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="opms_act_id"/></td>
							<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineno"/></td>
						</logic:equal>
						<!--  Saurabh : End -->		
						
						<!--  Meenaskhi : changes for Usage -->
						<logic:equal name="isUsage" value="1">
							<td align="left" class='inner col2'><bean:write  name="row" property="partyNo"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="accountID"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="partyName"/>&nbsp;</td>
						    <td align="left" class='inner col2'><bean:write  name="row" property="verticalDetails"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="regionName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="orderNumber"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="order_type"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="serviceName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="customer_logicalSINumber"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="linename"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="ordersubtype"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="ckt_id"/>&nbsp;</td>
							<td align="left" class='inner col1'><bean:write  name="row" property="billing_bandwidth"/>&nbsp;</td>
							<td align="left" class='inner col1'><bean:write  name="row" property="billing_uom"/>&nbsp;</td>
							<td align="left" class='inner col1'><bean:write  name="row" property="distance"/>&nbsp;</td>
							<td align="left" class='inner col1'><bean:write  name="row" property="ratio"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="location_from"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="location_to"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="primarylocation"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="seclocation"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="productName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="subProductName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="entity"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="lcompanyname"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="currencyName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="creditPeriodName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="billingTypeName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="billingFormatName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="billingLevel"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="billingLevelNo"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="taxation"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="bill_period"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="billingMode"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="poNumber"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="poDate"/>&nbsp;</td>
							<td align="left" class='inner col1'><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="totalAmountIncludingCurrent"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="poReceiveDate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="custPoDetailNo"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="custPoDate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="serviceId"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="logicalSINo"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="lineno"/>&nbsp;</td>
							
							<td align="left" class='inner col4' ><bean:write  name="row" property="fx_external_acc_id"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="fxInternalId"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="child_account_creation_status"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="packageID"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="packageName"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentID"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="componentName"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentInfoID"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentType"/>&nbsp;</td>
							<!--<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentAmount"/>&nbsp;</td>-->
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentStatus"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.startDate"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.fxTokenNo"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentInstanceID"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.fxStartStatus"/>&nbsp;</td>
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
		              	<div class='horizontal-scrollcancelfaillinereportusage' onscroll='reposHorizontal(this);'>
		                  	<div>
		                  	</div>
		              	</div>
		          	</td>
		     	</tr>	
		     </table>	
		     	 <!-- End Scrolling and Freezing of Copy and Cancel Report   -->
					<logic:empty  name="CancelledFailedLineReport">
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
