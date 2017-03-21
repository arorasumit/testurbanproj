<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[RPT7052013020]    Vijay    15-May-2013          Remove Account name in This report     -->
<!-- [TRNG21052013004]    Vijay        19 June   - create freez header -->
<!-- [001] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
<!-- [002] Vipin Saharia added 14 new columns CBR : 20140424-00-020010 -->
<!-- [003] Priya Gupta	added 4 new columns  -->
<!-- [004]  Gunjan Singla  6-May-16   20160418-R1-022266  Added columns -->
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
<title>Order Stage - Report</title>
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
<script language="javascript" type="text/javascript"><!--

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
	myform.method.value='viewOrderStageList';	
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
	myform.method.value='viewOrderStageList';	
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
//		var path = "<%=request.getContextPath()%>/reportsAction.do?method=fetchStatus";		
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewOrderStageList";
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewOrderStageList&inExcel=true";
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
	myForm.osp.value='' ;	
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
	var orderType=myForm.orderType.value;
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	var osp=myForm.osp.value;


	/* if(( orderType == null  && fromDate == null && toDate == null && osp==null ) || ( trim(orderType) == "" && trim(toDate)=="" && trim(fromDate)=="" && trim(osp)==""))
	{
		alert("Please enter atleast one search criteria!");
		return false;
		//return true;
	} */
	
	if((fromDate == null & toDate == null) || ( trim(fromDate)=="" & trim(toDate) == "")){
			alert("Please enter From Order Creation Date and To Order Creation Date!");
			return false;
	 }
	else {
				
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
		return true;
	}
}

//function fnViewOrder(orderNo)
//{
//      document.forms[0].action="./viewOrderAction.do?methodName=viewOrder&orderNo="+orderNo;
//      document.forms[0].submit();
//}
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
--></script>
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
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="left">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head">
			 <table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>ORDER STAGE REPORT</span> 
			</td>
			<logic:present name="formBean"property="isDumpAvailable">
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
					<td width="10px"/>
						<td align="right" style="font-size:9px"> Order Type:</td>
						<td align="left" nowrap>
							<html:select property="orderType" styleClass="inputBorder2" style="width:90px;float:left;" >
							<html:option value="">SELECT</html:option>
							<html:option value="New">NEW</html:option>
							<html:option value="Change">CHANGE</html:option>
							</html:select>
							
						</td>
						<td width="50px"/>
						<td align="right" style="font-size:9px;">From Order Creation Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="dateFrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">To Order Creation Date:</td>
						<td nowrap><html:text  property="toDate" styleClass="inputBorder1" styleId="dateTo"  style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td width="10px"/>
					</tr>
					<tr>	
					<td width="10px"/>	
						<td align="right" style="font-size:9px;">OSP</td>
							<td nowrap>
							<html:select property="osp" styleClass="inputBorder2" style="width:70px;float:left;" >
							<html:option value="">Select</html:option> 
							<html:option value='1'>Yes</html:option> 
							<html:option value='2'>No</html:option> 
							</html:select>
						</td>
						
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
						
						
							<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
							
						
							
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Order Stage Details</b></legend>
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
			
			<!-- Start Scrolling and Freezing of ORDER STAGE REPORT   -->
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
					<td align="center" class='inner col2 head1'><b>
						<logic:present name="orderStageList" scope="request">
							<logic:notEmpty  name="orderStageList" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Crm Order Id</a>
							</logic:notEmpty>
							<logic:empty  name="orderStageList" scope="request">
								Crm Order Id
							</logic:empty>
						</logic:present>
					</b></td>
					<td align="center" class='inner col2 head1'><b>Service ID</b></td>
					<td align="center" class='inner col3 head1'><b>Party Name</b></td>
						<!-- [RPT7052013020]- start -->	
						<!--  			<td class='inner col2 head1'><b>Account Name</b></td> -->
						<!-- [RPT7052013020]- start -->				
					<td align="center" class='inner col3 head1'><b>Account Manager</b></td>
					<td align="center" class='inner col2 head1'><b>Order Creation Date</b></td>
					<td align="center" class='inner col2 head1'><b>AM Approved Date</b></td>
					<td align="center" class='inner col3 head1'><b>AM Approved By</b></td>
					<td align="center" class='inner col3 head1'><b>Project Manager</b></td>
					<td align="center" class='inner col2 head1'><b>PM Approved Date</b></td>
					<td align="center" class='inner col3 head1'><b>PM Approved By</b></td>
					<td align="center" class='inner col2 head1'><b>Final Approved Date</b></td>
					<td align="center" class='inner col3 head1'><b>Final Approved By</b></td>
					<td align="center" class='inner col2 head1'><b>Order Type  </b></td>
					<td align="center" class='inner col2 head1'><b>Sevice Type</b></td>
					<td align="center" class='inner col2 head1'><b>M6 Order No</b></td>
					<td align="center" class='inner col2 head1'><b>M6 Order Date</b></td>
					<td align="center" class='inner col2 head1'><b>Order Provision</b></td>
					<td align="center" class='inner col2 head1'><b>Billing Status</b></td>
					<td align="center" class='inner col2 head1'><b>Publish Date</b></td>
					<td align="center" class='inner col2 head1'><b>Eff Start Date</b></td>
					<td align="center" class='inner col2 head1'><b>Order Status</b></td>
					<td align="center" class='inner col2 head1'><b>Service Stage</b></td>
					<td align="center" class='inner col2 head1'><b>Party Number</b></td>
					<td align="center" class='inner col2 head1'><b>Circuit Status</b></td>
					<td align="center" class='inner col2 head1'><b>Region</b></td>
					<td align="center" class='inner col2 head1'><b>Zone</b></td>
					<td align="center" class='inner col2 head1'><b>Change Order Reason</b></td>
					<td align="center" class='inner col2 head1'><b>Vertical</b></td>
					<td align="center" class='inner col2 head1'><b>Parent Order Subtype</b></td>
					<td align="center" class='inner col2 head1'><b>Cust Logical Service Id</b></td>
					<td align="center" class='inner col2 head1'><b>OSP</b></td>
					<td align="center" class='inner col2 head1'><b>PO Receive Date</b></td>
					<td align="center" class='inner col2 head1'><b>PO Date</b></td>
					<!-- 002 START -->
					<td align="center" class='inner col2 head1'><b>Demo Type</b></td>
					<td align="center" class='inner col2 head1'><b>Cust PO Number</b></td>
					<td align="center" class='inner col2 head1'><b>PO Contract CND</b></td>
					<td align="center" class='inner col2 head1'><b>Product</b></td>
					<td align="center" class='inner col3 head1'><b>Sub Product Name</b></td>
					<td align="center" class='inner col2 head1'><b>Cust Segment</b></td>
					<td align="center" class='inner col2 head1'><b>Service Status</b></td>
					<td align="center" class='inner col2 head1'><b>PO ID</b></td>
					<td align="center" class='inner col2 head1'><b>OnNet/OffNet</b></td>
					<td align="center" class='inner col2 head1'><b>Media</b></td>
					<td align="center" class='inner col2 head1'><b>Project Category</b></td>
					<td align="center" class='inner col3 head1'><b>PM Remarks</b></td>
					<td align="center" class='inner col2 head1'><b>Order Stage</b></td>
					<td align="center" class='inner col2 head1'><b>Total Charge Amount</b></td>
					<!-- 002 END -->
					<%--neha Start--%>
					<td align="center" class='inner col2 head1'><b>LD Clause</b></td>
					<%--neha End--%>
					<!-- [003] start -->
					<td align="center" class='inner col2 head1'><b>Salesforce Opportunity Number</b></td>
					
					<!-- <td align="center" class='inner col2 head1'><b>Partner Id</b></td> [004]-->
					<!-- [003] end -->
					<!-- [004] start -->
					<td align="center" class='inner col1 head1'><b>Partner Code</b></td>
					<td align="center" class='inner col2 head1'><b>Partner Name</b></td>
					<td align="center" class='inner col3 head1'><b>Field Engineer</b></td>
					<!-- [004] end -->	
				</tr>
				</table>
					 </div> 	
					 <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	        			<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>			
				<logic:present name="orderStageList" scope="request">
				
					<logic:notEmpty  name="orderStageList" scope="request"> 					
						<logic:iterate id="row" name="orderStageList" indexId="recordId">
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
								<td align="left" class='inner col2'><bean:write  name="row" property="orderNumber"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="serviceId"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="partyName"/>&nbsp;</td>
									<!-- [RPT7052013020]- start -->			
										<!--  	<td align="left" class='inner col2'><bean:write  name="row" property="accountName"/>&nbsp;</td> -->
									<!-- [RPT7052013020]- start -->			
								<td align="left" class='inner col3'><bean:write  name="row" property="accountManager"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="orderDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="amApproveDate"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="amName"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="projectManager"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="pmApproveDate"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="pmName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="copcApproveDate"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="copcName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="orderType"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="serviceDetDescription"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="m6OrderNumber"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="m6OrderDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="orderProvision"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="billingStatus"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="publishedDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="effStartDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="orderStatus"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="serviceStage"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="partyNo"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="circuitStatus"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="regionName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="zoneName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="standardReason"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="verticalDetails"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="parentOrderSubType"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="custLogicalSI"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="osp"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="poReceiveDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="poDate"/>&nbsp;</td>
								<!-- 002 START -->
								<td align="left" class='inner col2'><bean:write  name="row" property="demoType"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="custPoNumber"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="poContractCnd"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="productName"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="servSubTypeName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="customerSegment"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="serviceStatus"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="poDetailNumber"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="onnetOffnet"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="media"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="projectCategory"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="pmRemarks"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="orderStage"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="totalChargeAmount"/>&nbsp;</td>																	
								<!-- 002 END -->
								<%--neha Start--%>
								<td align="left" class='inner col2'><bean:write  name="row" property="ldClause"/>&nbsp;</td>
								<%--neha End--%>
								<!-- [003] start -->
								<td align="left" class='inner col2'><bean:write  name="row" property="salesForceOpportunityNumber"/>&nbsp;</td>
												
								<%-- <td align="left" class='inner col2'><bean:write  name="row" property="partnerId"/>&nbsp;</td> --%>
								<!-- [003] end -->
								<!-- [004] start -->
								<td align="center" class='inner col1'><bean:write  name="row" property="partnerCode"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="channelPartner"/>&nbsp;</td>	
								<td align="center" class='inner col3'><bean:write  name="row" property="fieldEngineer"/>&nbsp;</td>
								<!-- [004] end -->
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
		              	<div class='horizontal-scrollorderstagereport' onscroll='reposHorizontal(this);'>
		                  	<div>
		                  	</div>
		              	</div>
		          	</td>
		     	</tr>	
		     </table>	
		     	 <!-- End Scrolling and Freezing of ORDER STAGE REPORT   -->
					<logic:empty  name="orderStageList">
									<% String fromPageSubmit=request.getParameter("fromPageSubmit");
										if(("1").equalsIgnoreCase(fromPageSubmit)){%>
					   <tr align="center" >
						 <td colspan="17" align="center"><B><font color="red">No Records Found</font></B></td>
					   </tr>
							   	<%} %>	
					</logic:empty>	
				</logic:present>				
			</table>
			</div>
		</fieldset>
	</html:form>
</body>
</html>
