<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- [TRNG21052013004]    Vijay        19 June   - create freez header -->
<!-- [001] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
<!-- [BFR6] Nagarjuna        GlobalDataBillingEfficiency Project to add TaxExemptionReason -->
<!-- [002]  Priya Gupta   Added 3 new columns  -->
<!-- [003]  Gunjan Singla  5-May-16   20160418-R1-022266  Added columns -->
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
<title>Order Detail - Report</title>
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
	myform.method.value='viewOrderReportDetails';	
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
	myform.method.value='viewOrderReportDetails';	
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewOrderReportDetails";
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewOrderReportDetails&inExcel=true";
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
	//myForm.companyName.value='' ;
	//myForm.logical_SI_No.value='' ;
	myForm.demo.value='' ;
	myForm.toAccountNo.value='' ;
	myForm.fromAccountNo.value='' ;
	myForm.toOrderNo.value='' ;
	myForm.fromOrderNo.value='' ;	
	myForm.osp.value='' ;	
	myForm.fromOrderDate.value='' ;	
	myForm.toOrderDate.value='' ;
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
	var orderStage=myForm.orderType.value;
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	var demo=myForm.demo.value; 
	var toAccountNo=myForm.toAccountNo.value;
	var fromAccountNo=myForm.fromAccountNo.value;
	var toOrderNo=myForm.toOrderNo.value;
	var fromOrderNo=myForm.fromOrderNo.value;
	var osp=myForm.osp.value;
	var fromOrderDate=myForm.fromOrderDate.value;
	var toOrderDate=myForm.toOrderDate.value;
	

	//if((orderStage==null && fromDate==null && toDate==null &&  demo==null &&  toAccountNo==null &&  fromAccountNo==null &&  toOrderNo==null &&  fromOrderNo==null && osp==null
	//|| 
	//trim(orderStage)=="" && trim(toDate)=="" && trim(fromDate)=="" && trim(demo)==""  && trim(toAccountNo)==""  && trim(fromAccountNo)==""  && trim(toOrderNo)==""  && trim(fromOrderNo)=="" && trim(osp)=="" )){
	//	alert("Please enter atleast one search criteria!");
	//	return false;
		
	//} 
	//else {
				
		if((fromOrderDate == null & toOrderDate == null) || ( trim(fromOrderDate)=="" & trim(toOrderDate) == "")){
			alert("Please enter From Date and To Date!");
			return false;
		} 		
		if((fromOrderDate == null || trim(fromOrderDate)== "" ) && (toOrderDate != null && trim(toOrderDate) != "" ))
		{
			alert("Please enter From Date");
			return false;
		}
		else if((toOrderDate == null || trim(toOrderDate)== "" ) && (fromOrderDate != null && trim(fromOrderDate) != "" ))
		{
			alert("Please enter To Date");
			return false;
		}
		if(dateCompare(fromOrderDate,toOrderDate)==1)
			{			
				alert("From Date cannot be greater than To Date");
				return false;
			}
		if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
		{
			alert("Please enter COPC Approval From Date");
			return false;
		}
		else if((toDate == null || trim(toDate)== "" ) && (fromDate != null && trim(fromDate) != "" ))
		{
			alert("Please enter COPC Approval To Date");
			return false;
		}
		if(dateCompare(fromDate,toDate)==1)
			{			
				alert("COPC Approval From Date cannot be greater than COPC Approval To Date");
				return false;
			}
		if((fromAccountNo == 0 || trim(fromAccountNo)== "" ) && (toAccountNo != 0 && trim(toAccountNo) != "" ))
			{
				alert("Please enter From Account No");
				return false;
			}
		else if((toAccountNo == 0 || trim(toAccountNo)== "" ) && (fromAccountNo != 0 && trim(fromAccountNo) != "" ))
			{
				alert("Please enter To Account No");
				return false;
			}
		if(fromAccountNo > toAccountNo)
			{			
				alert("From AccountNo cannot be greater than To AccountNo");
				return false;
			}
		//order
		if((fromOrderNo == 0 || trim(fromOrderNo)== "" ) && (toOrderNo != 0 && trim(toOrderNo) != "" ))
			{
				alert("Please enter From Order No");
				return false;
			}
		else if((toOrderNo == 0 || trim(toOrderNo)== "" ) && (fromOrderNo != 0 && trim(fromOrderNo) != "" ))
			{
				alert("Please enter To Order No");
				return false;
			}
		if(fromOrderNo > toOrderNo)
			{			
				alert("From OrderNo cannot be greater than To OrderNo");
				return false;
			}
		return true;
	//}
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
				<tr ><td align="left"><span>ORDER DETAILS REPORT</span> 
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
					
						<td align="right" style="font-size:9px"> Order Type:</td>
						<td align="left" nowrap>
							<html:select  property="orderType" styleClass="inputBorder2" style="width:90px;float:left;"  >
							<html:option value="">SELECT</html:option>
							<html:option value="New">NEW</html:option>
							<html:option value="Change">CHANGE</html:option>
							</html:select>
						</td>
						<td align="right" style="font-size:9px;">COPC Approval From Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="dateFrom" styleClass="inputBorder2" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
					
						<td align="right" style="font-size:9px;">COPC Approval To Date:</td>
						<td nowrap><html:text  property="toDate" styleId="dateTo" styleClass="inputBorder2" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td align="right" style="font-size:9px;">From Date:</td>
						<td nowrap><html:text  property="fromOrderDate" styleId="orderDateFrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromOrderDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
					
						<td align="right" style="font-size:9px;">To Date:</td>
						<td nowrap><html:text  property="toOrderDate" styleId="orderDateTo" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toOrderDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
							
							<td align="left" colspan="2"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
							
						
						
					</tr>
					<tr>
					<td align="right" style="font-size:9px;">From Order No:</td>
						<td nowrap><html:text  property="fromOrderNo" styleClass="inputBorder2" style="width:75px;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
					</td>
					<td align="right" style="font-size:9px;">To Order No:</td>
						<td nowrap><html:text  property="toOrderNo" styleClass="inputBorder2" style="width:75px;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
					</td>
					<td align="right" style="font-size:9px;">From Account No:</td>
						<td nowrap><html:text  property="fromAccountNo" styleClass="inputBorder2" style="width:75px;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
					</td>
					<td align="right" style="font-size:9px;">To Account No:</td>
						<td nowrap><html:text  property="toAccountNo" styleClass="inputBorder2" style="width:75px;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
					</td>
					<td align="right" style="font-size:9px;">Demo</td>
						<td nowrap>
						<html:select property="demo" styleClass="inputBorder2" style="width:90px;float:left;" >
						<html:option value="">Select</html:option> 
						<html:option value="D">Yes</html:option> 
						<html:option value="N">No</html:option> 
						</html:select>
					</td>
					<td align="right" style="font-size:9px;">OSP</td>
						<td nowrap>
						<html:select property="osp" styleClass="inputBorder2" style="width:70px;float:left;" >
						<html:option value="">Select</html:option> 
						<html:option value='YES'>Yes</html:option> 
						<html:option value='NO'>No</html:option> 
						</html:select>
					</td>
					</tr>
					</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Order Detail Items</b></legend>
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
			
			<!-- Start Scrolling and Freezing of ORDER DETAILS REPORT   -->
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
					<td align="center" class='inner col2 head1'><b>Account ID</b></td>
					<td align="center" class='inner col3 head1'><b>Account Manager</b></td>
					<td align="center" class='inner col2 head1'><b>Account Number</b></td>		
					<td align="center" class='inner col1 head1'><b>Po Amount</b></td>
					<td align="center" class='inner col2 head1'><b>Customer Segment</b></td>
					<td align="center" class='inner col2 head1'><b>Account Category</b></td>	
					<td align="center" class='inner col2 head1'><b>Vertical</b></td>		
					<td align="center" class='inner col2 head1'><b>Billing Charge Start Date</b></td>
					<td align="center" class='inner col3 head1'><b>Service Name</b></td>
					<td align="center" class='inner col2 head1'><b>Order Line No</b></td>
				 	<td align="center" class='inner col3 head1'><b>Line Name</b></td>  
					<td align="center" class='inner col2 head1'><b>Cancelflag</b></td>
					<td align="center" class='inner col2 head1'><b>Provision Bandwidth</b></td>
					<td align="center" class='inner col2 head1'><b>Uom</b></td>		
					<td align="center" class='inner col2 head1'><b>Billing Bandwidth</b></td>
					<td align="center" class='inner col2 head1'><b>Store Address</b></td>
					<td align="center" class='inner col2 head1'><b>Bill Uom</b></td>	
					<td align="center" class='inner col2 head1'><b>Category Of Order</b></td>		
					<td align="center" class='inner col2 head1'><b>Contract Period</b></td>
					<td align="center" class='inner col3 head1'><b>Companyname</b></td>
					<td align="center" class='inner col2 head1'><b>Order Creation Date</b></td>
					<td align="center" class='inner col2 head1'><b>Customer Rfs Date</b></td>
					<td align="center" class='inner col2 head1'><b>Customer Service Rfs Date</b></td>
					<td align="center" class='inner col2 head1'><b>Currency</b></td>
					<td align="center" class='inner col2 head1'><b>Charge Name</b></td>		
					<td align="center" class='inner col2 head1'><b>Customer Po Date</b></td>
					<td align="center" class='inner col2 head1'><b>Customer Po Number</b></td>
					<td align="center" class='inner col2 head1'><b>Cyclic/Non-Cyclic</b></td>	
					<td align="center" class='inner col2 head1'><b>Challen No</b></td>		
					<td align="center" class='inner col2 head1'><b>Order No</b></td>
					<td align="center" class='inner col4 head1'><b>From Site</b></td>
					<td align="center" class='inner col4 head1'><b>To Site</b></td>
				  	<td align="center" class='inner col2 head1'><b>Item Quantity</b></td>  
					<td align="center" class='inner col1 head1'><b>Kms Distance</b></td>
					<td align="center" class='inner col2 head1'><b>Line Item Amount</b></td>
					<td align="center" class='inner col2 head1'><b>Copc Approved Date</b></td>
					<td align="center" class='inner col2 head1'><b>Line Item Description</b></td>		
					<td align="center" class='inner col2 head1'><b>Loc Date</b></td>	
					<td align="center" class='inner col2 head1'><b>Account Manager Receive Date</b></td>
					<td align="center" class='inner col2 head1'><b>Order Total</b></td>
					<td align="center" class='inner col2 head1'><b>Order Entry Date</b></td>
					<td align="center" class='inner col2 head1'><b>Taxation</b></td>
					 <!--GlobalDataBillingEfficiency BFR6  -->		
					<td align="center" class='inner col2 head1'><b>TaxExemption Reason</b></td>			
					
					<td align="center" class='inner col3 head1'><b>Licence Company</b></td>
					<td align="center" class='inner col2 head1'><b>Logical Circuit Id</b></td>		
					<td align="center" class='inner col2 head1'><b>Order Type</b></td>
					<td align="center" class='inner col2 head1'><b>Payment Term</b></td>
					<td align="center" class='inner col3 head1'><b>Project Mgr</b></td>	
					<td align="center" class='inner col2 head1'><b>Region Name</b></td>
					<td align="center" class='inner col2 head1'><b>Old Line item Amount</b></td>
					<td align="center" class='inner col2 head1'><b>Demo Type</b></td>
					<td align="center" class='inner col2 head1'><b>Party Name</b></td>
					<td align="center" class='inner col2 head1'><b>Order Stage Description</b></td>
					<td align="center" class='inner col2 head1'><b>Service Stage Description</b></td>
					<td align="center" class='inner col2 head1'><b>Charge End Date</b></td>
					<td align="center" class='inner col2 head1'><b>End Date Logic</b></td>		
					<td align="center" class='inner col2 head1'><b>New Order Remark</b></td>	
					<td align="center" class='inner col2 head1'><b>Remarks</b></td>
					<td align="center" class='inner col2 head1'><b>Service Order Type</b></td>
					<td align="center" class='inner col2 head1'><b>OSP</b></td>
					<td align="center" class='inner col2 head1'><b>Opportunity Id</b></td>
					<!-- [002] Start -->
					<td align="center" class='inner col2 head1'><b>SalesForce Opportunity Number</b></td>
					<td align="center" class='inner col2 head1'><b>Network Type</b></td>
					
					<!-- <td align="center" class='inner col2 head1'><b>Partner Id</b></td> commented by Gunjan-->
					<!-- [002] End -->
					<!-- [003] start -->
					<td align="center" class='inner col1 head1'><b>Partner Code</b></td>
					<td align="center" class='inner col2 head1'><b>Partner Name</b></td>	
					<td align="center" class='inner col3 head1'><b>Field Engineer</b></td>
					<!-- [003] end -->
					
				</tr>
				</table>
					 </div> 	
					 <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	        			<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>		
				<logic:present name="orderReportDetailList" scope="request">
					<logic:notEmpty  name="orderReportDetailList" scope="request"> 					
						<logic:iterate id="row" name="orderReportDetailList" indexId="recordId">
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
								<td align="left" class='inner col2'><bean:write  name="row" property="accountID"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="accountManager"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="crmACcountNO"/>&nbsp;</td>
								<td align="left" class='inner col1'><bean:write  name="row" property="poAmounts"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="cus_segment"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="act_category"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="verticalDetails"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="billingPODate"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="serviceDetDescription"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="orderLineNumber"/>&nbsp;</td>
    							<td align="left" class='inner col3'><bean:write  name="row" property="linename"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="cancelflag"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="provisionBandWidth"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="uom"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write name="row" property="billingBandWidth"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="storeName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="billUom"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="categoryOfOrder"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="companyName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="orderDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="customerRfsDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="customerServiceRfsDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="currencyCode"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="chargeName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="customerPoDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="customerPoNumber"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="cyclicNonCyclic"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="challenno"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="orderNumber"/>&nbsp;</td>
								<td align="left" class='inner col4'><bean:write  name="row" property="fromSite"/>&nbsp;</td>
								<td align="left" class='inner col4'><bean:write  name="row" property="toSite"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="itemQuantity"/>&nbsp;</td> 
								<td align="left" class='inner col1'><bean:write  name="row" property="kmsDistance"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="chargeAmount"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="copcApproveDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="lineItemDescription"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="locDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="amReceiveDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="orderTotal"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="orderEntryDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="taxation"/>&nbsp;</td>
								<!--GlobalDataBillingEfficiency BFR6  -->
               							<td align="left" class='inner col2'><bean:write  name="row" property="taxExcemption_Reason"/>&nbsp;</td>
								
								<td align="left" class='inner col3'><bean:write  name="row" property="licenceCoName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="logicalCircuitNumber"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write name="row" property="orderType"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="paymentTerm"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="projectManager"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="regionName"/>&nbsp;</td>
						  		<td align="left" class='inner col2'><bean:write  name="row" property="oldLineitemAmount"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="demoType"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="partyName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="orderStageDescription"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="serviceStageDescription"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="chargeEndDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="endDateLogic"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="newOrderRemark"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="remarks"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="serviceOrderType"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="osp"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="opportunityId"/>&nbsp;</td>
								<!-- [002] Start -->
								<td align="left" class='inner col2'><bean:write  name="row" property="salesForceOpportunityNumber"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="networkType"/>&nbsp;</td>
								
								<%-- <td align="left" class='inner col2'><bean:write  name="row" property="partnerId"/>&nbsp;</td> commented by Gunjan--%>
								<!-- [002] End -->
								<!-- [003] start -->
							    <td align="center" class='inner col1'><bean:write name="row" property="partnerCode"/>&nbsp;</td>
							    <td align="left" class='inner col2'><bean:write  name="row" property="channelPartner"/>&nbsp;</td>
							    <td align="center" class='inner col3'><bean:write name="row" property="fieldEngineer"/>&nbsp;</td>
							    <!-- [003] end -->
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
		              	<div class='horizontal-scrollorderdetailreport' onscroll='reposHorizontal(this);'>
		                  	<div>
		                  	</div>
		              	</div>
		          	</td>
		     	</tr>	
		     </table>	
		     	 <!-- End Scrolling and Freezing of ORDER DETAILS REPORT   -->
		     	 
					<logic:empty  name="orderReportDetailList">
						<% String fromPageSubmit=request.getParameter("fromPageSubmit");
							if(("1").equalsIgnoreCase(fromPageSubmit)){%>
					   <tr align="center" >
						 <td colspan="22" align="center"><B><font color="red">No Records Found</font></B></td>
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

