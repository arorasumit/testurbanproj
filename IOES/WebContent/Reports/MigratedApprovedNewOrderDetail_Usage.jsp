<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- [TRNG21052013004]    Vijay        27 June   - create freez header -->
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
<title>Migrated Approved New Order Details</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
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
	//myform.method.value='viewMigAppNewOrderDetails&usage='+isUsage;
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewMigAppNewOrderDetails&usage="+isUsage;	
	if(isBlankForm()==false)
	{
		return ;
	}
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
	myform.method.value='viewMigAppNewOrderDetails&usage='+isUsage;	
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
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewMigAppNewOrderDetails&usage="+isUsage;
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
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewMigAppNewOrderDetails&inExcel=true&usage="+isUsage;
		showLayerExcel();
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.servicename.value='' ;	
	myForm.ordermonth.value='' ;	
	myForm.orderyear.value='' ;
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	var ordermonth=myForm.ordermonth.value;	
	var orderyear=myForm.orderyear.value;	
	
	if((ordermonth == null || trim(ordermonth)== "" ) && (orderyear == null || trim(orderyear)== "" ))
	{
		alert("Please enter ordermonth and orderyear");
		return false;
	}
	else if((ordermonth == null || trim(ordermonth)== "" ) && (orderyear != null || trim(orderyear) != "" ))
	{
			alert("Please enter ordermonth");
			return false;
	}
	else if((orderyear == null || trim(orderyear)== "") && (ordermonth != null || trim(ordermonth) != "" ))
	{
			alert("Please enter orderyear");
			return false;
	}	 
		
	return true;
}

path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<body onload="javascript:maxLimitChecker(document.getElementById('searchForm'),'<%=ApplicationFlags.ReportsExcelMaxSize%>','<%=Messages.getMessageValue("ReportsExcelMaxSizeMessage") %>');">

<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/reportsAction_Usage" styleId="searchForm" method="post">
		<bean:define id="formBean" name="reportsBean"></bean:define>
		<html:hidden property="toExcel"/>
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="pagingSorting.sortByOrder"/>
		<html:hidden property="pagingSorting.pageNumber"/>
		<html:hidden property="reportProcessForLimit"/>
		<html:hidden property="interfaceId"/>
		<html:hidden property="isDumpAvailable"/>
		<html:hidden property="dumpFileName"/>		
		
<html:hidden property="reportCurrentCount"/>
		<input type="hidden" name="fromPageSubmit" value="1"/>
		<input type="hidden" name="orderType" value="New"/>
		<input type="hidden" name="method" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="left">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
		<logic:equal name="isUsage" value="0">
		<div border="1" class="head"> <span>MIGRATED APPROVED NEW ORDER DETAILS</span> </div>
		</logic:equal>
		<logic:equal name="isUsage" value="1">
		<div border="1" class="head"> 
			
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>MIGRATED APPROVED NEW ORDER DETAILS(USAGE)</span> 
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
						<td width="90px"/>
						<td align="right" style="font-size:9px"> Order Month:</td>						
						<td align="left" nowrap>
							<html:select  property="ordermonth" styleClass="inputBorder1" style="width:90px;float:left;"  >
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
						<td align="right" style="font-size:9px">Order Year(YYYY):</td>
						<td align="left">
							<html:text  property="orderyear" maxlength="4" styleClass="inputBorder1" style="width:90px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" ></html:text>
						</td>
						<td align="right" style="font-size:9px">Service Name:</td>
						<td align="left">
							<html:text  property="servicename" maxlength="18" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Service Name')};" ></html:text>
						</td>
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick()">Search</a></div>
						</td>
						<td align="left">
							<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
						</td>
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Migrated Approved/Unapproved New Order Details</b></legend>
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
			
<!-- Start Scrolling and Freezing of MIGRATED APPROVED NEW ORDER DETAILS(USAGE)    -->
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
					<td align="center" style="font-size:9px"><b>Party Number</b></td>
					<td align="center" style="font-size:9px"><b>Account Number</b></td>
					<td align="center" style="font-size:9px"><b>Party Name</b></td>
					<td align="center" style="font-size:9px"><b>Vertical</b></td>
					<td align="center" style="font-size:9px"><b>Region</b></td>										
					<td align="center" style="font-size:9px"><b>
					<logic:present name="MigAppNewOrderDetailsList" scope="request">
							<logic:notEmpty  name="MigAppNewOrderDetailsList" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Order Number</a>
							</logic:notEmpty>
							<logic:empty  name="MigAppNewOrderDetailsList" scope="request">
								Order Number
							</logic:empty>
						</logic:present>
						</b>
					</td>
					<td align="center" style="font-size:9px"><b>Order Month</b></td>
					<td align="center" style="font-size:9px"><b>Order Type</b></td>
					<td align="center" style="font-size:9px"><b>M6 Order No</b></td>
					<td align="center" style="font-size:9px"><b>Service Name</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Line Name</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Service Order Type</b></td>
					<td align="center" style="font-size:9px"><b>Circuit Id</b></td>
					<td align="center" style="font-size:9px" width="20px"><b>Bandwidth</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Billling Bandwidth</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Bill Uom</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Kms Distance</b></td>	
					<td align="center" style="font-size:9px" height="30px"><b>Ratio</b></td>	
					<td align="center" style="font-size:9px" height="30px"><b>Location From</b></td>	
					<td align="center" style="font-size:9px" height="30px"><b>Location To</b></td>										
					<td align="center" style="font-size:9px" height="30px"><b>Pri Location</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Sec Location</b></td>					
					<td align="center" style="font-size:9px" height="30px"><b>Product</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Subproduct</b></td>					
					<td align="center" style="font-size:9px" height="30px"><b>Legal Entity</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Licence Company</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Currency</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Credit Period</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Bill Type</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Bill Format</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Billing Level</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Billing Level Number</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Taxation</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Hardware Type</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Hardware Flag</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Store</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Type Of Sale</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Bill Period</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Billing Mode</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Po Number</b></td>					
					<td align="center" style="font-size:9px" height="30px"><b>Po Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Period In Month</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Tot Po Amt</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Cust Po Receive Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Cust Po Number</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Cust Po Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Cust Tot Po Amt</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Charge Type</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Charge Name</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Charge Start Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Inv Amt</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Amt</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Annotation</b></td>					
					<td align="center" style="font-size:9px" height="30px"><b>Loc Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Loc Number</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Billing Trig Date</b></td>					
					<td align="center" style="font-size:9px" height="30px"><b>Bill Trg Create Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Pm Prov Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Billing Trig Flag</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Token No</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Fx Sd Chg Status</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Fx Status</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Bl Source</b></td>					
					<td align="center" style="font-size:9px" height="30px"><b>Business Serial No</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Opms Account Id</b></td>					
					<td align="center" style="font-size:9px" height="30px"><b>Lineitemnumber</b></td>
				</logic:equal>
				
				<logic:equal name="isUsage" value="1">
					<td align="center" class='inner col2 head1'><b>Party Number</b></td>
					<td align="center" class='inner col2 head1'><b>Account Number</b></td>
					<td align="center" class='inner col3 head1'><b>Party Name</b></td>
					<td align="center" class='inner col2 head1'><b>Vertical</b></td>
					<td align="center" class='inner col2 head1'><b>Region</b></td>										
					<td align="center" class='inner col2 head1'><b>
					<logic:present name="MigAppNewOrderDetailsList" scope="request">
							<logic:notEmpty  name="MigAppNewOrderDetailsList" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Order Number</a>
							</logic:notEmpty>
							<logic:empty  name="MigAppNewOrderDetailsList" scope="request">
								Order Number
							</logic:empty>
						</logic:present>
						</b>
					</td>
					<td align="center" class='inner col1 head1'><b>Order Month</b></td>
					<td align="center" class='inner col2 head1'><b>Order Type</b></td>
					<td align="center" class='inner col2 head1'><b>M6 Order No</b></td>
					<td align="center" class='inner col2 head1'><b>Service Name</b></td>
					<td align="center" class='inner col2 head1' ><b>Cust Logical Si Id</b></td>
					<td align="center" class='inner col2 head1' ><b>Line Name</b></td>
					<td align="center" class='inner col2 head1' ><b>Service Order Type</b></td>
					<td align="center" class='inner col4 head1'><b>Circuit Id</b></td>
					<td align="center" class='inner col1 head1'><b>Bandwidth</b></td>
					<td align="center" class='inner col1 head1' ><b>Billling Bandwidth</b></td>
					<td align="center" class='inner col1 head1' ><b>Bill Uom</b></td>
					<td align="center" class='inner col1 head1' ><b>Kms Distance</b></td>						
					<td align="center" class='inner col2 head1' ><b>Location From</b></td>	
					<td align="center" class='inner col2 head1' ><b>Location To</b></td>										
					<td align="center" class='inner col4 head1' ><b>Pri Location</b></td>
					<td align="center" class='inner col4 head1' ><b>Sec Location</b></td>					
					<td align="center" class='inner col4 head1' ><b>Product</b></td>
					<td align="center" class='inner col4 head1' ><b>Subproduct</b></td>					
					<td align="center" class='inner col4 head1' ><b>Legal Entity</b></td>
					<td align="center" class='inner col4 head1' ><b>Licence Company</b></td>
					<td align="center" class='inner col2 head1' ><b>Currency</b></td>
					<td align="center" class='inner col2 head1' ><b>Credit Period</b></td>
					<td align="center" class='inner col2 head1' ><b>Bill Type</b></td>
					<td align="center" class='inner col4 head1' ><b>Bill Format</b></td>
					<td align="center" class='inner col4 head1' ><b>Billing Level</b></td>
					<td align="center" class='inner col2 head1' ><b>Billing Level Number</b></td>
					<td align="center" class='inner col2 head1' ><b>Taxation</b></td>										
					<td align="center" class='inner col2 head1' ><b>Billing Mode</b></td>
					<td align="center" class='inner col2 head1' ><b>Po Number</b></td>					
					<td align="center" class='inner col2 head1' ><b>Po Date</b></td>
					<td align="center" class='inner col1 head1' ><b>Period In Month</b></td>
					<td align="center" class='inner col1 head1' ><b>Tot Po Amt</b></td>
					<td align="center" class='inner col2 head1' ><b>Cust Po Receive Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Cust Po Number</b></td>
					<td align="center" class='inner col2 head1' ><b>Cust Po Date</b></td>													
					<td align="center" class='inner col2 head1' ><b>Loc Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Loc Number</b></td>
					<td align="center" class='inner col2 head1' ><b>Billing Trig Date</b></td>					
					<td align="center" class='inner col2 head1' ><b>Bill Trg Create Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Pm Prov Date</b></td>
					<td align="center" class='inner col1 head1' ><b>Billing Trig Flag</b></td>									
					<td align="center" class='inner col2 head1' ><b>Lineitemnumber</b></td>					
					<td align="center" class='inner col3 head1' ><b>Child Account Number</b></td>
					<td align="center" class='inner col2 head1' ><b>Child Internal Account No</b></td>
					<td align="center" class='inner col2 head1' ><b>Child Account Fx Status</b></td>	
					<td align="center" class='inner col2 head1' ><b>Package ID</b></td>
					<td align="center" class='inner col4 head1' ><b>Package Name</b></td>								
					<td align="center" class='inner col2 head1' ><b>Component InfoID</b></td>
					<td align="center" class='inner col2 head1' ><b>Component ID</b></td>
					<td align="center" class='inner col3 head1' ><b>Component Name</b></td>
					<td align="center" class='inner col2 head1' ><b>Component Status</b></td>
					<td align="center" class='inner col4 head1' ><b>Component Start Logic</b></td>
					<td align="center" class='inner col2 head1' ><b>Component Start Date</b></td>
					<td align="center" class='inner col4 head1' ><b>Component End Logic</b></td>
					<td align="center" class='inner col2 head1' ><b>Component End Date</b></td>
					<td align="center" class='inner col1 head1' ><b>Start Date Days</b></td>
					<td align="center" class='inner col1 head1' ><b>Start Date Months</b></td>
					<td align="center" class='inner col1 head1' ><b>End Date Days</b></td>
					<td align="center" class='inner col1 head1' ><b>End Date Months</b></td>
					<td align="center" class='inner col2 head1' ><b>Component Type</b></td>
					<!--<td align="center" class='inner col2 head1' ><b>Component Cyclic/Non-Cyclic Amount</b></td>-->							
					<td align="center" class='inner col2 head1' ><b>Component FX Instance Id</b></td>					
					<td align="center" class='inner col2 head1' ><b>Start Token No</b></td>
					<td align="center" class='inner col1 head1' ><b>Start FX Status</b></td>
					<td align="center" class='inner col2 head1' ><b>End Token No</b></td>
					<td align="center" class='inner col1 head1' ><b>End FX Status</b></td>	
				</logic:equal>
				</tr>
				</table>
					 </div> 	
					 <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	        			<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>		
	        			
				<logic:present name="MigAppNewOrderDetailsList" scope="request">
					<logic:notEmpty  name="MigAppNewOrderDetailsList" scope="request"> 					
						<logic:iterate id="row" name="MigAppNewOrderDetailsList" indexId="recordId">
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
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="party_no"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountNoString"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ordermonth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="m6OrderNo"/></td>	
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceName"/></td>	
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceDetDescription"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceOrderType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalCircuitId"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_bandwidth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billUom"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="kmsDistance"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ratio"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fromLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="toLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primaryLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="secondaryLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="productName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="subProductName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="entity"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lcompanyname"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="creditPeriodName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTypeName"/></td><!-- Bill Type -->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingFormatName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taxation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="hardwaretypeName"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="hardwareFlag"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="storename"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="saleType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingMode"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ponum"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="totalPoAmt"/></td><!--Total Amount-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poRecieveDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDetailNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="totalPoAmt"/></td><!--Cust Tot Po Amt-->
				   			<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeTypeName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAmount_String"/></td><!--Inv Amt-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lineItemAmount"/></td><!-- Amt-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAnnotation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="locDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_No"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTriggerDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingtrigger_createdate"/></td><!-- Bill Trg Create Date -->							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="pmApproveDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="tokenNO"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_St_Chg_Status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fxStatus"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="blSource"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="business_serial_no"/></td><!-- Business Serial No	-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="opms_Account_Id"/></td><!-- Opms Account Id -->	
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderLineNumber"/></td><!-- Order Line Id-->
							</logic:equal>
							
							<logic:equal name="isUsage" value="1">
							<td align="left" class='inner col2'><bean:write  name="row" property="party_no"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="crmAccountNoString"/>&nbsp;</td>
							<td align="left" class='inner col3'><bean:write  name="row" property="partyName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="verticalDetails"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="regionName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="orderNumber"/>&nbsp;</td>
							<td align="left" class='inner col1'><bean:write  name="row" property="ordermonth"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="orderType"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="m6OrderNo"/>&nbsp;</td>	
							<td align="left" class='inner col2'><bean:write  name="row" property="serviceName"/>&nbsp;</td>	
							<td align="left" class='inner col2'><bean:write  name="row" property="customer_logicalSINumber"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="serviceDetDescription"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="serviceOrderType"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="logicalCircuitId"/>&nbsp;</td>
							<td align="left" class='inner col1'><bean:write  name="row" property="bandwaidth"/>&nbsp;</td>
							<td align="left" class='inner col1'><bean:write  name="row" property="billing_bandwidth"/>&nbsp;</td>
							<td align="left" class='inner col1'><bean:write  name="row" property="billUom"/>&nbsp;</td>
							<td align="left" class='inner col1'><bean:write  name="row" property="kmsDistance"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="fromLocation"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="toLocation"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="primaryLocation"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="secondaryLocation"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="productName"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="subProductName"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="entity"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="lcompanyname"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="currencyName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="creditPeriodName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="billingTypeName"/>&nbsp;</td><!-- Bill Type -->
							<td align="left" class='inner col4'><bean:write  name="row" property="billingFormatName"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="billingLevelName"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="billingLevelNo"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="taxation"/>&nbsp;</td>							
							<td align="left" class='inner col2'><bean:write  name="row" property="billingMode"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="ponum"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="poDate"/>&nbsp;</td>
							<td align="left" class='inner col1'><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
							<td align="left" class='inner col1'><bean:write  name="row" property="totalPoAmt"/>&nbsp;</td><!--Total Amount-->
							<td align="left" class='inner col2'><bean:write  name="row" property="poRecieveDate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="custPoDetailNo"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="custPoDate"/>&nbsp;</td>							
							<td align="left" class='inner col2'><bean:write  name="row" property="locDate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="LOC_No"/>&nbsp;</td>							
							<td align="left" class='inner col2'><bean:write  name="row" property="billingTriggerDate"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="billingtrigger_createdate"/>&nbsp;</td><!-- Bill Trg Create Date -->							
							<td align="left" class='inner col2'><bean:write  name="row" property="pmApproveDate"/>&nbsp;</td>
							<td align="left" class='inner col1'><bean:write  name="row" property="billing_Trigger_Flag"/>&nbsp;</td>							
							<td align="left" class='inner col2'><bean:write  name="row" property="orderLineNumber"/>&nbsp;</td><!-- Order Line Id-->
							<td align="left" class='inner col3'><bean:write  name="row" property="fx_external_acc_id"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="fx_internal_acc_id"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="childAccountFXSataus"/>&nbsp;</td>
							<td align="left" class='inner col2'><bean:write  name="row" property="packageID"/>&nbsp;</td>
							<td align="left" class='inner col4'><bean:write  name="row" property="packageName"/>&nbsp;</td>				
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentInfoID"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentID"/>&nbsp;</td>
							<td align="left" class='inner col3' ><bean:write  name="row" property="componentName"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentStatus"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="componentDto.startLogic"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.startDate"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="componentDto.endLogic"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.end_date"/>&nbsp;</td>
							<td align="left" class='inner col1' ><bean:write  name="row" property="startDateDays"/>&nbsp;</td>
							<td align="left" class='inner col1' ><bean:write  name="row" property="startDateMonth"/>&nbsp;</td>
							<td align="left" class='inner col1' ><bean:write  name="row" property="endDateDays"/>&nbsp;</td>
							<td align="left" class='inner col1' ><bean:write  name="row" property="endDateMonth"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentType"/>&nbsp;</td>
							<!--<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentAmount"/>&nbsp;</td>-->
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentInstanceID"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.fxTokenNo"/>&nbsp;</td>
							<td align="left" class='inner col1' ><bean:write  name="row" property="componentDto.fxStartStatus"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.endTokenNo"/>&nbsp;</td>
							<td align="left" class='inner col1' ><bean:write  name="row" property="componentDto.endFxStatus"/>&nbsp;</td>
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
		              	<div class='horizontal-scrollmigappneworderreportusage' onscroll='reposHorizontal(this);'>
		                  	<div>
		                  	</div>
		              	</div>
		          	</td>
		     	</tr>	
		     </table>	
		     	 <!-- End Scrolling and Freezing of MIGRATED APPROVED NEW ORDER DETAILS(USAGE)    -->
					
					<logic:empty  name="MigAppNewOrderDetailsList">
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
