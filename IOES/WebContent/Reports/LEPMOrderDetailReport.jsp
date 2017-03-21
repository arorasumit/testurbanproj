<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[RPT7052013010]    Vijay    20-May-2013          Add Date validation. Records should be fetch on the basis of Date filter.     -->
<!-- [202020] VIPIN SAHARIA 2-Dec-2013 onnetOffnet, OrderCategory, media added to report "LEMP Order Detail Report" -->
<!-- [001] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!--  [101010] Changed by Rampratap  -->
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.ReportsBean"%>
<%@page import="com.ibm.ioes.utilities.ApplicationFlags"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>LEPM Order Detail Report</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ReportHeaderFreeze.js"></script>
<link href="./css/ReportHeaderFreeze.css" type="text/css" rel="stylesheet">
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
	myform.method.value='viewLEPMOrderDetailReport';	
//[RPT7052013010] --start	
	if(isBlankForm()==false)
	{
		return ;
	}
//[RPT7052013010] --end	
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
	myform.method.value='viewLEPMOrderDetailReport';	
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewLEPMOrderDetailReport";
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewLEPMOrderDetailReport&inExcel=true";
			showLayerExcel();
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.orderType.value='' ; 
	myForm.copcapprovalfromdate.value='' ;
	myForm.copcapprovaltodate.value='' ;	
	myForm.verticalDetails.value='' ;	
	myForm.fromOrderNo.value='';
	myForm.toOrderNo.value='';
	
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	var fromDate=myForm.copcapprovalfromdate.value;
	var toDate=myForm.copcapprovaltodate.value;
	var fromOrderNo=myForm.fromOrderNo.value;
	var toOrderNo=myForm.toOrderNo.value;
	
		/*	if((fromOrderNo == 0 || trim(fromOrderNo)== "" ) && (toOrderNo != 0 && trim(toOrderNo) != "" ))
			{
				alert("Please enter From OrderNo ");
				return false;
			}
			else if((fromOrderNo == 0 || trim(fromOrderNo)== "" ) && (toOrderNo != 0 && trim(toOrderNo) != "" ))
			{
				alert("Please enter To OrderNo ");
				return false;
			}
			if(fromOrderNo > toOrderNo)
			{			
				alert("From OrderNo cannot be greater than To OrderNo");
				return false;
			}
			if(dateCompare(fromDate,toDate)==1)
			{			
				alert("From Date cannot be greater than To Date");
				return;
			}
		*/
		if((fromDate == null & toDate == null) || ( trim(fromDate)=="" & trim(toDate) == "")){
			alert("Please enter COPC Approved From Date and COPC Approved To Date!");
			return false;
		}	
		 else{
	        if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
			{
				alert("Please enter COPC Approved From Date");
				return false;
			}
			else if((toDate == null || trim(toDate)== "" ) && (fromDate != null && trim(fromDate) != "" ))
			{
				alert("Please enter COPC Approved To Date");
				return false;
			}
			if(dateCompare(fromDate,toDate)==1)
			{			
					alert("COPC Approved From Date cannot be greater than COPC Approved To Date");
					return false;
			}
		}	
		
	return true;
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
				<tr ><td align="left"><span>LEPM Order Detail Report</span> 
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
						<td align="right" style="font-size:9px" width="47"> Order Type :</td>
						<td align="left" nowrap width="100">
							<html:select property="orderType" styleClass="inputBorder2" style="width:70px;float:left;" >
							<html:option value="">Select</html:option>
							<html:option value="New">New</html:option>
							<html:option value="Change">Change</html:option>
							</html:select>
						</td>
						
						<td align="right" style="font-size:9px;" width="33">From Order No:</td>
						<td align="left" width="132"><html:text  property="fromOrderNo" styleId="fromOrderNo" styleClass="inputBorder2" maxlength="18"  style="width:75px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text></td>

						<td align="right" style="font-size:9px" width="33">To Order No:</td>
						<td align="left" width="132">
							<html:text  property="toOrderNo" styleId="toOrderNo" styleClass="inputBorder2"  style="width:75px;float:left;" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
						</td>
						
						
					</tr>
					<tr>
						<td width="90px"/>
						<!--  [101010] START -->
						 <td align="right" style="font-size:9px;" width="54">COPC Approved From Date:</td>
						<td nowrap><html:text  property="copcapprovalfromdate" styleId="datefrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.copcapprovalfromdate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td> 
						<td align="right" style="font-size:9px;" width="54">COPC Approved To Date:</td>
						<td nowrap><html:text  property="copcapprovaltodate" styleId="dateto" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.copcapprovaltodate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td> 
						<!--  [101010]END -->
						
						<td align="right" style="font-size:9px" width="42">Vertical:</td>
						<td align="left" width="90">
							<html:text  property="verticalDetails" styleClass="inputBorder2" style="width:90px;float:left;" maxlength="50" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Vertical')};"></html:text>
						</td>
							
							<td align="left" width="41">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
							
						<td align="left">
							<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
						</td>
						
						<td align="center"  width="158">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>	
							
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>LEPM Order Detail Report</b></legend>
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
			
				<!-- Start Scrolling and Freezing of LEPM Order REPORT -->
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
				
					<td align="center" class='inner col51 head1'><b>Party Name</b></td>
								<td align="center" class='inner col31 head1'  ><b> 	
					<logic:present name="LEPMOrderDetailList" scope="request">
							<logic:notEmpty  name="LEPMOrderDetailList" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Crm Order Id</a>
							</logic:notEmpty>
							<logic:empty  name="LEPMOrderDetailList" scope="request">
								Crm Order Id
							</logic:empty>
						</logic:present>
						</b></td>
								<td align="center" class='inner col31 head1'><b>Copc Approved Date</b></td>
								<td align="center" class='inner col31 head1'><b>Logical Circuit Id</b></td>
								<td align="center" class='inner col31 head1'><b>Service Number</b></td>
								<td align="center" class='inner col31 head1'><b>Quotenumber</b></td>
								<td align="center" class='inner col31 head1'><b>Product Name</b></td>
								<td align="center" class='inner col31 head1'><b>Product Subtype</b></td>
								<td align="center" class='inner col51 head1'><b>From Site </b></td>
								<td align="center" class='inner col51 head1'><b>To Site</b></td>
								<td align="center" class='inner col2 head1'><b>Order Sub Type</b></td>
							   	<td align="center" class='inner col31 head1'><b>Provision Bandwidth </b></td>
							   	<td align="center" class='inner col2 head1'><b>Line Item Amount</b></td>
							   	<td align="center" class='inner col31 head1'><b>Project Mgr</b></td>
							   	<td align="center" class='inner col51 head1'><b>Project Mgr Email </b></td>
							    <td align="center" class='inner col31 head1'><b>Account Mgr </b></td>
							    <td align="center" class='inner col31 head1'><b>Zone </b></td>
							    <td align="center" class='inner col31 head1'><b>Region Name </b></td>
								<td align="center" class='inner col31 head1'><b>Vertical </b></td>
								<td align="center" class='inner col31 head1'><b>Account Category </b></td>
							  	<td align="center" class='inner col31 head1'><b>Customer Po Number </b></td>
							   	<td align="center" class='inner col31 head1'><b>Customer Po Date </b></td>
							  	<td align="center" class='inner col31 head1'><b>Order Entry Date </b></td>
							  	<td align="center" class='inner col31 head1'><b>Pm Approval Date </b></td>
							  	<td align="center" class='inner col31 head1'><b>A/C Mgr Receive Date </b></td>
							  	<td align="center" class='inner col31 head1'><b>Nio Approval Date </b></td>
							  	<td align="center" class='inner col31 head1'><b>Demo Infrastartdate </b></td>
							  	<td align="center" class='inner col31 head1'><b>Demo Infraenddate </b></td>
							  	<td align="center" class='inner col31 head1'><b>Customer Rfs Date </b></td>
							  	<td align="center" class='inner col31 head1'><b>Order Category</b></td>
							  	<td align="center" class='inner col31 head1'><b>Order Reporting Status </b></td>
							  	<td align="center" class='inner col31 head1'><b>Line Item Description </b></td>
							  	<td align="center" class='inner col51 head1'><b>Order Line Name </b></td>
							  	<td align="center" class='inner col31 head1'><b>Order Subline Name </b></td>
							  	<td align="center" class='inner col31 head1'><b>Charge Name </b></td>
							  	<td align="center" class='inner col31 head1'><b>Pk Charges Id </b></td>
							  	<td align="center" class='inner col31 head1'><b>Order Line No </b></td>
							  	<td align="center" class='inner col51 head1'><b>Service Name </b></td>
							  	<td align="center" class='inner col31 head1'><b>Account Number </b></td>
							  	<td align="center" class='inner col31 head1'><b>Companyname </b></td>
							  	<td align="center" class='inner col31 head1'><b>Licence Company</b></td>
							  	<td align="center" class='inner col2 head1'><b>Contract Period</b></td>
							  	<td align="center" class='inner col31 head1'><b>Payment Term</b></td>
							  	<td align="center" class='inner col31 head1'><b>Cyclic Non Cyclic</b></td>
							  	<td align="center" class='inner col2 head1'><b>Order Line Type</b></td>
							  	<td align="center" class='inner col1 head1'><b>Uom</b></td>
							  	<td align="center" class='inner col1 head1'><b>Billing Bandwidth</b></td>
							  	<td align="center" class='inner col1 head1'><b>Bill Uom</b></td>
							  	<td align="center" class='inner col2 head1'><b>From City</b></td>
							  	<td align="center" class='inner col2 head1'><b>To City</b></td>
							  	<td align="center" class='inner col31 head1'><b>Old Order Total</b></td>
							  	<td align="center" class='inner col31 head1'><b>Old Lineitem Amount</b></td>
							 	<td align="center" class='inner col2 head1'><b>Old Contract Period</b></td>
							    <td align="center" class='inner col1 head1'><b>Ratio</b></td>
							    <td align="center" class='inner col1 head1'><b>Taxation</b></td>
								<td align="center" class='inner col2 head1'><b>Factory Circuit Id</b></td>
								<td align="center" class='inner col2 head1'><b>Kms Distance</b></td>
							   	<td align="center" class='inner col51 head1'><b>A/C Mgr Emailid</b></td>
							   	<td align="center" class='inner col31 head1'><b>Currency</b></td>
								<td align="center" class='inner col31 head1'><b>Order Total</b></td>
								<td align="center" class='inner col31 head1'><b>Po Amount</b></td>
							   	<td align="center" class='inner col31 head1'><b>Bl Source</b></td>
							   	<td align="center" class='inner col31 head1'><b>Order Type</b></td>
								<td align="center" class='inner col51 head1'><b>Dispatch Address</b></td>
								<td align="center" class='inner col51 head1'><b>Parent Line Name</b></td>
								<td align="center" class='inner col2 head1' ><b>Opportunity Id</b></td>
								<!-- [202020] Start -->
								<td align="center" class='inner col51 head1'><b>OnNet/OffNet</b></td>
								<td align="center" class='inner col2 head1'><b>Media</b></td>
								<td align="center" class='inner col51 head1'><b>Project Category</b></td>
								<!-- [202020] End -->
					</tr>
						</table>
				</div>                		 	                		
	          <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	               <table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>
				<logic:present name="LEPMOrderDetailList" scope="request">
					<logic:notEmpty  name="LEPMOrderDetailList" scope="request"> 					
						<logic:iterate id="row" name="LEPMOrderDetailList" indexId="recordId">
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
								<td align="left" class='inner col51'><bean:write  name="row" property="partyName"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write  name="row" property="orderNo"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write  name="row" property="copcApproveDate"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write  name="row" property="logicalCircuitId"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write  name="row" property="serviceId"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write  name="row" property="quoteNo"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write  name="row" property="productName"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write  name="row" property="subProductName"/>&nbsp;</td>
								<td align="left" class='inner col51'><bean:write  name="row" property="primarylocation"/>&nbsp;</td>
								<td align="left" class='inner col51'><bean:write  name="row" property="seclocation"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="ordersubtype"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write  name="row" property="bandwaidth"/>&nbsp;</td>
								<td align="center" class='inner col2'><bean:write  name="row" property="chargeAmount_String"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write  name="row" property="prjmngname"/>&nbsp;</td>
								<td align="left" class='inner col51'><bean:write name="row" property="prjmgremail"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write  name="row" property="actmngname"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write  name="row" property="zoneName"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write  name="row" property="regionName"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write  name="row" property="verticalDetails"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write  name="row" property="act_category"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write name="row" property="custPoDetailNo"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write  name="row" property="custPoDate"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write  name="row" property="orderDate"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write  name="row" property="pmApproveDate"/>&nbsp;</td>
							    <td align="center" class='inner col31'><bean:write  name="row" property="amApproveDate"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write  name="row" property="nio_approve_date"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write  name="row" property="demo_infrastartdate"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write  name="row" property="demo_infra_enddate"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write  name="row" property="rfs_date"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write name="row" property="ordercategory"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write name="row" property="orderStatus"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write name="row" property="line_desc"/>&nbsp;</td> 
								<td align="left" class='inner col51'><bean:write name="row" property="linename"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write name="row" property="sub_linename"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write name="row" property="chargeName"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write name="row" property="chargeinfoID"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write name="row" property="serviceProductID"/>&nbsp;</td>
								<td align="left" class='inner col51'><bean:write name="row" property="serviceName"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write name="row" property="accountID"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write name="row" property="entity"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write name="row" property="licCompanyName"/>&nbsp;</td>
								<td align="center" class='inner col2'><bean:write name="row" property="contractPeriod"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write name="row" property="frequencyName"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write name="row" property="chargeTypeName"/>&nbsp;</td>
							    <td align="left" class='inner col2'><bean:write name="row" property="serviceType"/>&nbsp;</td>
								<td align="center" class='inner col1'><bean:write name="row" property="uom"/>&nbsp;</td>
								<td align="center" class='inner col1'><bean:write name="row" property="billing_bandwidth"/>&nbsp;</td>
								<td align="center" class='inner col1'><bean:write name="row" property="billing_uom"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write name="row" property="from_city"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write name="row" property="to_city"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write name="row" property="oldordertotal"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="oldlineamt"/>&nbsp;</td>
								<td align="center" class='inner col2'><bean:write name="row" property="old_contract_period"/>&nbsp;</td>
								<td align="center" class='inner col1'><bean:write name="row" property="ratio"/>&nbsp;</td>
								<td align="left" class='inner col1'><bean:write name="row" property="taxation"/>&nbsp;</td>
								<td align="center" class='inner col2'><bean:write name="row" property="factory_ckt_id"/>&nbsp;</td>
								<td align="center" class='inner col2' ><bean:write  name="row" property="distance"/>&nbsp;</td>
								<td align="left" class='inner col51'><bean:write name="row" property="accountManager"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write name="row" property="currencyCode"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write name="row" property="totalPoAmt"/>&nbsp;</td>
								<td align="center" class='inner col31'><bean:write name="row" property="poAmount"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write name="row" property="bisource"/>&nbsp;</td>
								<td align="left" class='inner col31'><bean:write name="row" property="orderType"/>&nbsp;</td>
								<td align="left" class='inner col51'><bean:write name="row" property="dispatchAddress1"/>&nbsp;</td>
								<td align="left" class='inner col51'><bean:write name="row" property="parent_name"/>&nbsp;</td>
								<td align="center" class='inner col2'><bean:write  name="row" property="opportunityId"/>&nbsp;</td>
								<!-- [202020] Start -->
								<td align="center" class='inner col51'><bean:write  name="row" property="onnetOffnet"/>&nbsp;</td>
								<td align="center" class='inner col2'><bean:write  name="row" property="media"/>&nbsp;</td>
								<td align="center" class='inner col51'><bean:write  name="row" property="servOrderCategory"/>&nbsp;</td>
								<!-- [202020] End -->
							</tr>
											
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty  name="LEPMOrderDetailList">
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
	                    	<div class='horizontal-scrolllepmdetailreport' onscroll='reposHorizontal(this);'>
	                        	<div>
	                        	</div>
	                    	</div>
	                	</td>
            		</tr>
				</table>
				<!-- End Scrolling and Freezing of LEPM Order REPORT  -->
			</table>
			</div>
		</fieldset>
	</html:form>
</body>
</html>
