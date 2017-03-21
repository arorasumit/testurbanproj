<!-- [001] Santosh Srivastava added this page on 28/02/2014 for ActiveLineDemoReport -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<title>Active Line Demo - Report</title>
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
	myform.method.value='viewActiveLineDemoReport';	
	/*if(isBlankForm()==false)
	{
		return ;
	}*/
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
	myform.method.value='viewActiveLineDemoReport';	
		//showLayer();
		showLayer();
	myform.submit();
}


function searchClick()
{
	alert("This feature is not available, please use Download Dump");
	return;
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewActiveLineDemoReport";
		showLayer();
		myForm.submit(); 
	}
}

function exportToExcel()
{
	alert("This feature is not available, please use Download Dump");
	return;
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewActiveLineDemoReport&inExcel=true";
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.fromDate.value='' ;	
	myForm.toDate.value='' ;	
}

function isBlankForm()
{

	var myForm=document.getElementById('searchForm');
		
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
		if(fromDate == null || trim(fromDate)== "" ) 
		{
			alert("Please enter From Date");
			return false;
		}
		else if(toDate == null || trim(toDate)== "" )
		{
			alert("Please enter To Date");
			return false;
		}
		if(dateCompare(fromDate,toDate)==1)
			{			
				alert("From Date cannot be greater than To Date");
				return false;
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
		
		<html:hidden property="interfaceId"/>
		<html:hidden property="isDumpAvailable"/>
		<html:hidden property="dumpFileName"/>	
		<html:hidden property="reportProcessForLimit"/>
		<html:hidden property="reportCurrentCount"/>
		<html:hidden property="toExcel"/>
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
			
		<div border="1" class="head"> 
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>ACTIVE LINE DEMO REPORT</span> 
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
						<!--<td align="right" style="font-size:9px"> Order Type:</td>
						<td align="left" nowrap>
							<html:select  property="orderType" styleClass="inputBorder1" style="width:90px;float:left;"  >
							<html:option value="">SELECT</html:option>
							<html:option value="New">NEW</html:option>
							<html:option value="Change">CHANGE</html:option>
							</html:select>
						</td>
						-->
						<td align="right" style="font-size:9px;">From Order Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="dateFrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Select Date"></a>
							</font>
						</td>
					
						<td align="right" style="font-size:9px;">To Order Date:</td>
						<td ><html:text  property="toDate" styleId="dateTo" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Select Date"></a>
							</font>
						</td>
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
						<logic:present name="demoReportList" scope="request">
							
							<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
							
							
						</logic:present>
					</tr>
					<tr>
							<td width="10px"/>
							<td align="right" style="font-size:9px;">FROM ORDER NO:</td>
							<td align="left"><html:text  property="fromOrderNo" styleClass="inputBorder1"  style="width:75px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text></td>
							<td align="right" style="font-size:9px">TO ORDER NO:</td>
							<td align="left">
								<html:text  property="toOrderNo" styleClass="inputBorder1"  style="width:75px;float:left;" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
							</td>
					</tr>
					</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" valign="top">
			<legend> <b>Active Line Demo Report</b></legend>
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
			
			<!-- Start Scrolling and Freezing of Active Line Report -->
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
			<td align="center" class='inner col31 head1' ><b>Party Name</b></td>
			<td align="center" class='inner col31 head1' ><b>Party Number</b></td>
			<td align="center" class='inner col31 head1' ><b>CRM Acc. No.</b></td>
			<td align="center" class='inner col31 head1' ><b>Cust Segment</b></td>
			<td align="center" class='inner col31 head1' ><b>Industry Segment</b></td>
			<td align="center" class='inner col31 head1' ><b>Region Name</b></td>
			<td align="center" class='inner col31 head1' ><b>Zone Name</b></td>
			<td align="center" class='inner col31 head1' ><b>Acct. MGR Name</b></td>
			<td align="center" class='inner col31 head1' ><b>PRJ MGR Name</b></td>
			<td align="center" class='inner col31 head1' ><b>Demo Order</b></td>
			<td align="center" class='inner col31 head1' ><b>No. Of Days</b></td>
			<td align="center" class='inner col31 head1' ><b>LSI DEMO TYPE</b></td>
			<td align="center" class='inner col31 head1' ><b>Order Type</b></td>
			<td align="center" class='inner col31 head1' ><b>Change Type Name</b></td>
			<td align="center" class='inner col31 head1' ><b>Order Sub Change Type</b></td>
			<td align="center" class='inner col31 head1' ><b>Order Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Service Id</b></td>
			<td align="center" class='inner col31 head1' ><b>Order No.</b></td>
			<td align="center" class='inner col31 head1' ><b>Logical SI No.</b></td>
			<td align="center" class='inner col31 head1' ><b>Customer Logical SI No.</b></td>
			<td align="center" class='inner col31 head1' ><b>CKT Id</b></td>
			<td align="center" class='inner col31 head1' ><b>Annotation</b></td>
			<td align="center" class='inner col31 head1' ><b>M6 Order No.</b></td>
			<td align="center" class='inner col31 head1' ><b>LOC No.</b></td>
			<td align="center" class='inner col31 head1' ><b>LOC Date</b></td>
			<td align="center" class='inner col31 head1' ><b>From Address</b></td>
			<td align="center" class='inner col31 head1' ><b>To Address</b></td>
			<td align="center" class='inner col31 head1' ><b>Billing Bandwidth</b></td>
			<td align="center" class='inner col31 head1' ><b>Billing Bandwidth UOM</b></td>
			<td align="center" class='inner col31 head1' ><b>Create Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Order Approval Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Published Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Service Closure Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Billing Trigger Create Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Billing Trigger Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Charge Current Start Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Current End Date</b></td>
			<td align="center" class='inner col31 head1' ><b>MST Charge Name</b></td>
			<td align="center" class='inner col31 head1' ><b>Product Name</b></td>
			<td align="center" class='inner col31 head1' ><b>Service Sub Type Name</b></td>
			<td align="center" class='inner col31 head1' ><b>Stage</b></td>
			<td align="center" class='inner col31 head1' ><b>Service Type Name</b></td>
			<td align="center" class='inner col31 head1' ><b>COPC Remark</b></td>
			<td align="center" class='inner col31 head1' ><b>Order Entry Remark</b></td>
			<td align="center" class='inner col31 head1' ><b>PM Remark</b></td>
			<td align="center" class='inner col31 head1' ><b>Total Amount</b></td>
			<td align="center" class='inner col31 head1' ><b>Cur Name</b></td>
			<td align="center" class='inner col31 head1' ><b>Annual Rate</b></td>
			<td align="center" class='inner col31 head1' ><b>Published</b></td>
			<td align="center" class='inner col31 head1' ><b>Service Stage</b></td>
		
		
				</tr>
						</table>
				</div>
				  <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	               <table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>        
				<logic:present name="demoReportList" scope="request" >
					<logic:notEmpty  name="demoReportList" scope="request"> 					
						<logic:iterate id="row" name="demoReportList" indexId="recordId">
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
								<td align="center" class='inner col31' ><bean:write  name="row" property="partyName"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="partyNo"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="crmAccNo"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="customerSegment"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="industrySegment"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="regionName"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="zoneName"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="acctMgrName"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="prjMGRName"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="demoOrder"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="noOfDays"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="lsiDemoType"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="orderType"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="changeTypeName"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="subChangeType"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="orderDate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="serviceId"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="orderNo"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="logicalSINo"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="custLogicalSINo"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="cktId"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="annotation"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="m6OrderNo"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="locNo"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="locDate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="fromAddress"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="toAddress"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="billingBandWidth"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="billingBandwidthUOM"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="createDate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="orderApprovalDate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="publishedDate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="serviceClosureDate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="billingTriggerCreateDate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="billingTriggerDate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="chargeCurrentStartDate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="chargeCurrentEndDate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="mstChargeName"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="productName"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="subTypeName"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="stage"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="serviceTypeName"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="copcApprovalRemark"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="orderEntryRemark"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="pMRemark"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="totalAmount"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="curName"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="annualRate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="published"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="serviceStage"/>&nbsp;</td>
					
								
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
	                    	<div class='horizontal-scrolldemoreport' onscroll='reposHorizontal(this);'>
	                        	<div>
	                        	</div>
	                    	</div>
	                	</td>
            		</tr>
				</table>
				<!-- End Scrolling and Freezing of Active Line Report -->		
				<logic:empty  name="demoReportList">
					<% String fromPageSubmit=request.getParameter("fromPageSubmit");
							if(("1").equalsIgnoreCase(fromPageSubmit)){%>
					   <tr align="center" >
						 <td colspan="46" align="center"><B><font color="red">No Records Found</font></B></td>
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
