<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- [002] Raveendra  12/02/2015   add new field for refund process -->
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
<title>Advance Payment -OTC Report</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ReportHeaderFreeze.js"></script>
<link href="./css/ReportHeaderFreeze.css" type="text/css" rel="stylesheet">
</head>
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
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.method.value='viewAdvancePaymentReport';	
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
	myform.method.value='viewAdvancePaymentReport';	 
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
	
	if(!isBlankForm())
	{	
		return;
	} 
	else 
	{
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewAdvancePaymentReport"; 
		showLayer();
		myForm.submit(); 
	}
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm') 
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	var fromOrderDate=myForm.fromOrderDate.value;
	var toOrderDate=myForm.toOrderDate.value;
	
	if((fromDate == null & toDate == null) || ( trim(fromDate)=="" & trim(toDate) == ""))
	{
		alert("Please enter From Order Creation Date and To Order Creation Date!");
		return false;
	}
	else 
	{			
		if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
		{
			alert("Please enter From Order Creation Date");
			return false;
		}
		else if((toDate == null || trim(toDate)== "" ) && (fromDate != null && trim(fromDate) != "" ))
		{
			alert("Please enter To Order Creation Date"); 
			return false;
		}
		if(dateCompare(fromDate,toDate)==1)
		{			
			alert("From Order Creation Date cannot be greater than To Order Creation Date"); 
			return false;
		}
		
		if((fromOrderDate == null || trim(fromOrderDate)== "" ) && (toOrderDate != null && trim(toOrderDate) != "" ))
		{
			alert("Please enter From Chq Date:");
			return false;
		}
		else if((toOrderDate == null || trim(toOrderDate)== "" ) && (fromOrderDate != null && trim(fromOrderDate) != "" ))
		{
			alert("Please enter To Chq Date");
			return false;
		}
		if(dateCompare(fromOrderDate,toOrderDate)==1)
		{			
			alert("From Chq Date cannot be greater than To Chq Date");
			return false;
		}
		return true;
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
	if(!isBlankForm())
	{
	 	return;
	} 
	else 
	{
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewAdvancePaymentReport&inExcel=true"; 
		showLayerExcel();
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.crmAccountNo.value='' ; 
	myForm.arcChqNo.value='' ;	
	myForm.otcChqNo.value='' ;	
	myForm.fromDate.value='' ;
	myForm.toDate.value='' ;  
	myForm.fromOrderDate.value='' ; 
	myForm.toOrderDate.value='' ; 
	myForm.dateType.value='' ;   	
}
//start [002]
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
//End [002]
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
		<html:hidden property="reportProcessForLimit"/>
		<html:hidden property="reportCurrentCount"/>
		
		<html:hidden property="interfaceId"/>
		<html:hidden property="isDumpAvailable"/>
		<html:hidden property="dumpFileName"/>		
		
		<input type="hidden" name="fromPageSubmit" value="1"/>
		<input type="hidden" name="method" />	
			
		<div border="1" class="head">
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr >
					<td align="left"> <span>Advance Payment / OTC Report</span> 
					</td>
				<!-- Start [002] -->	
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
			<!-- End [002] -->
				</tr>
			</table>
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
			<table border="0"  align="center" style="margin-top:7px" width="100%">						
				<tr>
					<td width="112" align="right" style="font-size:9px;">From Order Date:</td>
					<td nowrap><html:text  property="fromDate" styleId="dateFrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
					    <font size="1">
							<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
						</font>
					</td>
					<td align="right" style="font-size:9px;" width="122">To Order Date:</td>
					<td nowrap width="151"><html:text  property="toDate" styleId="dateTo" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
					    <font size="1">
							<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
						</font>
					</td>
					<td align="right" style="font-size:9px">Date Type:</td>
					<td align="left" nowrap>
						<html:select  property="datetype" styleClass="inputBorder2" style="width:90px;float:left;"  >
						<html:option value="1">ARC</html:option>
						<html:option value="2">OTC</html:option>
						</html:select>
					</td>
					<td align="right" style="font-size:9px;">From Chq Date:</td>
					<td nowrap><html:text  property="fromOrderDate" styleId="orderDateFrom" styleClass="inputBorder2" style="width:75px;" readonly="true"></html:text>
					    <font size="1">
							<a href="javascript:show_calendar(searchForm.fromOrderDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
						</font>
					</td>
					<td align="right" style="font-size:9px;" width="86">To Chq Date:</td>
					<td nowrap width="245"><html:text  property="toOrderDate" styleId="orderDateTo" styleClass="inputBorder2" style="width:75px;" readonly="true"></html:text>
						<font size="1">
							<a href="javascript:show_calendar(searchForm.toOrderDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
						</font>
					</td>
				</tr>
				<tr>
					<td align="right" style="font-size:9px">CRM Account No :</td>
					<td align="left" nowrap width="139">
						<html:text  property="crmAccountNo" maxlength="25" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'CRM Account No')};"></html:text>
					</td>
					<td align="right" style="font-size:9px" width="122">ARC Chq No</td>
					<td align="left" nowrap width="151">
						<html:text  property="arcChqNo" maxlength="25" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'ARCCHQNO')};"></html:text>
					</td>
					<td align="right" style="font-size:9px" width="86">OTC Chq No</td>
					<td align="left" nowrap width="106">
						<html:text  property="otcChqNo" maxlength="25" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'OTCCHQNO')};"></html:text>
					</td>
					<td align="left" width="111">
						
					</td>
					
					<td align="center" colspan="2">
						<p> <img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/> <a href="#"><img onclick="searchClick()" src="npd/Images/search.gif" title="search" height="15"></a>
				           <img title="Export To Excel" src="./gifs/top/excel1.gif" onClick="javascript:exportToExcel();"> </img>
			            <img src="gifs/top/home.gif" onClick="goToHome('Home');"></p>
						</img></td>
					<td width="0"></td>
				</tr>
			</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Advance Payment / OTC Report</b></legend>
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
			<!-- Start Scrolling and Freezing of Advance Payment-->
			<table border="0" cellpadding="0" cellspacing="0" class='main'>
				<tr>
					<td class='tablefrozencolumn'>
	                   <div id='divroot' class='root'>
	                       <table border="0" cellpadding="0" cellspacing="0" width="100%" class='root'>
	                           <tr>
	                               <td class='inner frozencol colwidth head1 '> 
	                               </td>
	                           </tr>
	                       </table>
	                   </div>
	                   <div id='divfrozen' class='frozen'>
	                       <table border="0" cellpadding="0" cellspacing="0" width="100%" class='frozen' >
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
									<td align="center" class='inner col2 head1' ><b>Party ID</b></td>
									<td align="center" class='inner col2 head1' ><b>Party No</b></td>
								    <td align="center" class='inner col2 head1' ><b>CRM Account No</b></td>
								    <td align="center" class='inner col3 head1' ><b>CRM Account Name</b></td>
									<td align="center" class='inner col2 head1' ><b>Order No</b></td>
									<td align="center" class='inner col2 head1' ><b>Currency</b></td>
									<td align="center" class='inner col2 head1' ><b>Customer Segment</b></td>
									<td align="center" class='inner col2 head1' ><b>Order Creation Date</b></td>
									<td align="center" class='inner col2 head1' ><b>AM Approval Date</b></td>
									<td align="center" class='inner col2 head1' ><b>PM Approval Date</b></td>
									<td align="center" class='inner col2 head1' ><b>Order Approval Date</b></td>
									<td align="center" class='inner col2 head1' ><b>PO Date</b></td>
									<td align="center" class='inner col2 head1' ><b>PO Recieve Date</b></td>
									<td align="center" class='inner col1 head1' ><b>LSI</b></td>
									<td align="center" class='inner col1 head1' ><b>Service No</b></td>
									<td align="center" class='inner col2 head1' ><b>Service Type</b></td>
									<td align="center" class='inner col2 head1' ><b>Product</b></td>
									<td align="center" class='inner col2 head1' ><b>Sub Product</b></td>
									<td align="center" class='inner col2 head1' ><b>Line No</b></td>
									<td align="center" class='inner col2 head1' ><b>Line Name</b></td>
									<td align="center" class='inner col2 head1' ><b>License Company</b></td>
									<td align="center" class='inner col3 head1' ><b>FX Child Account</b></td>
									<td align="center" class='inner col2 head1' ><b>LOC Date</b></td>
									<td align="center" class='inner col2 head1' ><b>Billing Trigger Date</b></td>
									<td align="center" class='inner col2 head1' ><b>Billing Trigger Action Date</b></td>
									<td align="center" class='inner col1 head1' ><b>ARC Exempted</b></td>
									<td align="center" class='inner col3 head1' ><b>ARC ExpReason</b></td>
									<td align="center" class='inner col1 head1' ><b>ARC Chq No</b></td>
									<!-- Start [002] -->
									<td align="center" class='inner col1 head1'><b>ARC
										Re Chq No</b></td>
								    <!-- End [002] -->
									<td align="center" class='inner col2 head1' ><b>ARC Chq Date</b></td>
									<td align="center" class='inner col3 head1' ><b>ARC Drawn of Bank</b></td>
									<td align="center" class='inner col2 head1' ><b>ARC Chq Amt</b></td>
									
									<!-- Start [002] -->
									<td align="center" class='inner col2 head1'><b>ARC
										  Re Chq Amt</b></td>
									<td align="center" class='inner col3 head1'><b>ARC
										Bank A/C No</b></td>
									<td align="center" class='inner col3 head1'><b>ARC
										Re Bank A/C No</b></td>
									<td align="center" class='inner col3 head1'><b>ARC
										IFSC Code</b></td>
									<td align="center" class='inner col3 head1'><b>ARC
										Re IFSC Code</b></td>
									<!-- End [002] -->		
									
									
									<td align="center" class='inner col2 head1' ><b>ARC Amt adjusted for LSI</b></td>
									<td align="center" class='inner col1 head1' ><b>OTC Exempted</b></td>
									<td align="center" class='inner col3 head1' ><b>OTC ExpReason</b></td>
									<td align="center" class='inner col1 head1' ><b>OTC Chq No</b></td>
									
									<!-- Start [002] -->
									<td align="center" class='inner col1 head1'><b>OTC
										Re Chq No</b></td>
								    <!-- End [002] -->
									
									<td align="center" class='inner col2 head1' ><b>OTC Chq Date</b></td>
									<td align="center" class='inner col3 head1' ><b>OTC Drawn of Bank</b></td>
									<td align="center" class='inner col2 head1' ><b>OTC Chq Amt</b></td>
									
									<!-- Start [002] -->
									<td align="center" class='inner col2 head1'><b>OTC
										  Re Chq Amt</b></td>
									<td align="center" class='inner col3 head1'><b>OTC
										Bank A/C No</b></td>
									<td align="center" class='inner col3 head1'><b>OTC
										Re Bank A/C No</b></td>
									<td align="center" class='inner col3 head1'><b>OTC
										IFSC Code</b></td>
									<td align="center" class='inner col3 head1'><b>OTC
										Re IFSC Code</b></td>
									<!-- End [002] -->
									
									
									<td align="center" class='inner col2 head1' ><b>OTC Amt adjusted for LSI</b></td>
									<td align="center" class='inner col2 head1' ><b>Order Status</b></td>
									<td align="center" class='inner col2 head1' ><b>Zone</b></td>
								</tr>
							</table>
						</div>
						<div id='contentscroll' class='content1' onscroll='reposHead(this);'style="height: ">
							<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tableAdvancePaymentReport'>		
								<logic:present name="displayAdvancePaymentReport" scope="request">
									<logic:notEmpty  name="displayAdvancePaymentReport" scope="request"> 					
										<logic:iterate id="row" name="displayAdvancePaymentReport" indexId="recordId">
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
												<td align="center" class='inner col2'><bean:write  name="row" property="party_id"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="partyNo"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="crmAccountNo"/>&nbsp;</td>
												<td align="center" class='inner col3'><bean:write  name="row" property="accountName"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="orderNo"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="currencyofOrder"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="customerSegment"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="orderCreationDate"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="amApprovalDate"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="pmApprovalDate"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="orderApprovalDate"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="podate_String"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="poRecieveDate_String"/>&nbsp;</td>
												<td align="center" class='inner col1'><bean:write  name="row" property="lsi"/>&nbsp;</td>
												<td align="center" class='inner col1'><bean:write  name="row" property="serviceNo"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="product"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="productName"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="subproductName"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="lineNo"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="lineName"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="licenseCompany"/>&nbsp;</td>
												<td align="center" class='inner col3'><bean:write  name="row" property="fxChildAccount"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="locDate"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="billingTriggerDate"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="billingTriggerActionDate_string"/>&nbsp;</td>
											    <td align="center" class='inner col1'><bean:write  name="row" property="arcExempted"/>&nbsp;</td>
												<td align="center" class='inner col3'><bean:write  name="row" property="arcExpreason"/>&nbsp;</td>
												<td align="center" class='inner col1'><bean:write  name="row" property="arcChqNo"/>&nbsp;</td>
												
														
												<!-- Start [002] -->
												<td align="center" class='inner col1'><bean:write
														name="row" property="arcReEnterCheckNumber" />&nbsp;</td>
												<!-- End [002] -->	
												
												
												<td align="center" class='inner col2'><bean:write  name="row" property="arcChqDate"/>&nbsp;</td>
												<td align="center" class='inner col3'><bean:write  name="row" property="arcBankName"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="arcChqAmt"/>&nbsp;</td>
												
													    <!-- Start [002] -->
												<td align="center" class='inner col2'><bean:write
														name="row" property="arcReEnterCheckamount" />&nbsp;</td>
												<td align="center" class='inner col3'><bean:write
														name="row" property="arcBankAccountNumber" />&nbsp;</td>
												<td align="center" class='inner col3'><bean:write
														name="row" property="arcReEnterBankAccountNumber" />&nbsp;</td>
												<td align="center" class='inner col3'><bean:write
														name="row" property="arcIfscCode" />&nbsp;</td>
												<td align="center" class='inner col3'><bean:write
														name="row" property="arcReEnterIfscCode" />&nbsp;</td>
												<!-- End [002] -->	
										
												
												<td align="center" class='inner col2'><bean:write  name="row" property="arcAmtAjd"/>&nbsp;</td>
											    <td align="center" class='inner col1'><bean:write  name="row" property="otcExempted"/>&nbsp;</td>
											    <td align="center" class='inner col3'><bean:write  name="row" property="otcExpreason"/>&nbsp;</td>
											    <td align="center" class='inner col1'><bean:write  name="row" property="otcChqNo"/>&nbsp;</td>
												
												<!-- Start [002] -->
												<td align="center" class='inner col1'><bean:write
														name="row" property="otcReEnterCheckNumber" />&nbsp;</td>
												<!-- End [002] -->	
												
												<td align="center" class='inner col2'><bean:write  name="row" property="otcChqDate"/>&nbsp;</td>
												<td align="center" class='inner col3'><bean:write  name="row" property="otcBankName"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="otcChqAmt"/>&nbsp;</td>
												
												<!-- Start [002] -->
												<td align="center" class='inner col2'><bean:write
														name="row" property="otcReEnterCheckamount" />&nbsp;</td>
												<td align="center" class='inner col3'><bean:write
														name="row" property="otcBankAccountNumber" />&nbsp;</td>
												<td align="center" class='inner col3'><bean:write
														name="row" property="otcReEnterBankAccountNumber" />&nbsp;</td>
												<td align="center" class='inner col3'><bean:write
														name="row" property="otcIfscCode" />&nbsp;</td>
												<td align="center" class='inner col3'><bean:write
														name="row" property="otcReEnterIfscCode" />&nbsp;</td>
												<!-- End [002] -->	
												
												
											    <td align="center" class='inner col2'><bean:write  name="row" property="otcAmtAjd"/>&nbsp;</td>
											    <td align="center" class='inner col2'><bean:write  name="row" property="orderStatus"/>&nbsp;</td>
												<td align="center" class='inner col2'><bean:write  name="row" property="zoneName"/>&nbsp;</td>
											</tr>
										</logic:iterate>
									</logic:notEmpty>
									<logic:empty  name="displayAdvancePaymentReport">
									  <% String fromPageSubmit=request.getParameter("fromPageSubmit");
											if(("1").equalsIgnoreCase(fromPageSubmit))
											{%>
					   							<tr align="center" >
						 							<td colspan="56" align="center"><B><font color="red">No Records Found</font></B></td>
					   							</tr>
											<%}
										%>
									</logic:empty>	
								</logic:present>				
							</table>
						</div>
					</td>
					<td class='tableverticalscroll' rowspan="2">
						<div class='vertical-scroll' onscroll='reposVertical(this);'>
	                        <div></div>
	                    </div>
	                    <div class='ff-fill'>
	                    </div>		
					</td>
				</tr>
				<tr>
	               	<td colspan="3">
	                   	<div class='horizontal-scrollAdvancePaymentReport' onscroll='reposHorizontal(this);'>
	                       	<div></div>
	                   	</div>
	               	</td>
				</tr>
			</table>
			<!-- End Scrolling and Freezing of OrderNewReport-->	
		</fieldset>
	</html:form>
</body>
</html>