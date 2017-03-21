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
<title>Logical SI Data Report</title>
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
	//myform.method.value='viewLogicalSIDataReport&usage='+isUsage;	
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewLogicalSIDataReport&usage="+isUsage;
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
	//myform.method.value='viewLogicalSIDataReport&usage='+isUsage;	
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewLogicalSIDataReport&usage="+isUsage;
	showLayer();
	myform.submit();
}


function popitup(url) 
{

	if(url=="SelectAccount")
	{
		var path = "<%=request.getContextPath()%>/bcpAddress.do?method=fetchAccount";		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
	}
	if(url=="SelectStatus")
	{

		var path = "<%=request.getContextPath()%>/reportsAction_Usage.do?method=fetchStatus";		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:330px");
	}
	return false;
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
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewLogicalSIDataReport&usage="+isUsage;
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
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewLogicalSIDataReport&inExcel=true&usage="+isUsage;
		showLayerExcel();
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.orderType.value='' ; 	
	myForm.fromOrderNo.value='' ; 
	myForm.toOrderNo.value='' ;	
	myForm.logicalsi_no.value='' ;
	//myForm.toAccountNo.value='' ;
	myForm.fromDate.value='' ;	
	myForm.toDate.value='' ;	
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	var orderType=myForm.orderType.value;
	var fromOrderNo=myForm.fromOrderNo.value;
	var toOrderNo=myForm.toOrderNo.value;
	var logicalsi_no=myForm.logicalsi_no.value;
	//var toAccountNo=myForm.toAccountNo.value;
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	

	//if((orderType==null && fromOrderNo==null && toOrderNo==null  && logicalsi_no == null &&  fromDate == null & toDate == null) 
	//	|| (trim(orderType)=="" && trim(fromOrderNo)=="" && trim(toOrderNo)=="" && trim(logicalsi_no) == ""   && trim(fromDate)=="")){
	//	alert("Please enter atleast one search criteria!");
	//	return false;
		//return true;
	//} 
	//else {
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
			if((fromOrderNo == 0 || trim(fromOrderNo)== "" ) && (toOrderNo != 0 && trim(toOrderNo) != "" ))
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
	
				
		return true;
	//}	
}
function resetFilterCriterion(){
	var myForm=document.getElementById('searchForm');
	var orderType=myForm.orderType.value;
	var fromOrderNo=myForm.fromOrderNo.value;
	var toOrderNo=myForm.toOrderNo.value;
	var logicalsi_no=myForm.logicalsi_no.value;
	//var toAccountNo=myForm.toAccountNo.value;
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	if(myForm.orderType.value != null || trim(myForm.orderType.value)!="" ) {
	
	}
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
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="pagingSorting.sortByOrder"/>
		<html:hidden property="pagingSorting.pageNumber"/>
		

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
		<logic:equal name="isUsage" value="0">	
			<div border="1" class="head"> <span>LOGICAL SI DATA REPORT</span> </div>
		</logic:equal>
		<logic:equal name="isUsage" value="1">
		<div border="1" class="head"> 
			
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>LOGICAL SI DATA REPORT(USAGE)</span> 
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
						<td align="right" style="font-size:9px"> ORDER TYPE :</td>
						<td align="left" nowrap>
							<html:select property="orderType" style="width:90px;float:left;" >
							<html:option value="">SELECT</html:option>
							<html:option value="New">NEW</html:option>
							<html:option value="Change">CHANGE</html:option>
							</html:select>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">FROM ORDER NO:</td>
						<td align="left"><html:text  property="fromOrderNo" style="width:75px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text></td>
						
						<td align="right" style="font-size:9px">TO ORDER NO:</td>
						<td align="left">
							<html:text  property="toOrderNo" style="width:75px;float:left;" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
						</td>
						<td align="left">
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
					</tr>
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
						<td nowrap><html:text  property="toDate" styleId="dateto" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td align="right" style="font-size:9px">LOGICAL SI NO:</td>
						<td align="left">
							<html:text  property="logicalsi_no" style="width:75px;float:left;" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
						</td>
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td align="center" valign="top">
							
						</td>
						<td align="left">
							<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
						</td>
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Logical SI Data Report</b></legend>
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
					<td align="center" style="font-size:9px"><b>
					<logic:present name="logicalSIDataReportList" scope="request">
							<logic:notEmpty  name="logicalSIDataReportList" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Order No</a>
							</logic:notEmpty>
							<logic:empty  name="logicalSIDataReportList" scope="request">
								Order No
							</logic:empty>
						</logic:present>
						</b>
					</td>				
					<td align="center" style="font-size:9px" height="30px"><b>Record Status</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Logical Si Number</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Service Name</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Parent Line Name</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Line Name</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Charge Type</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Charge Name</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Charge Status</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Contract Period Mnths</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Total Amount</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Frequency</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Frequency Amt</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Start Date Logic</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Start Date Months</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Start Date Days</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Billing Trigger Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>End Date Logic</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>End Date Months</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>End Date Days</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Charge End Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Annual Rate</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Trai Rate</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Discount</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Advance</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Installment Rate</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dnd Dispatch But Not Delivered</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dnd Dispatch And Delivered</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Ddni Disp Del Not Installed</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Ddni Disp Delivered Installed</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Po Valid Exclude</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Cust Po Number</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Cust Tot Po Amt</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Legal Entity</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Cust Po Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Po Contract Period</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Contract Start Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Contract End Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Cust Po Receive Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Charge Id</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Order Line Id</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Service No</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Pk Charges Id</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>M6 Product Id</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Parent Product Id</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Charge Hdr Id</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Ib Pk Charges Id</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Ib Order Line Id</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>M6 Order Id</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Order Line Si No</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Annotation</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Remarks</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Credit Period</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Bill Type</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Bill Format</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Taxation</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>License Co</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Country</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Address1</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Address2</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Address3</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Address4</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>City</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Postal Code</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>State</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Active End Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Contact Person Name</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Person Designation</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Person Mobile</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Person Email</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Person Fax</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Lst No</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Lst Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Billing Address Type</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Attribute Name</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Attribute Value</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Store</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Hardware Type</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Nature Sale</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Form Available</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Type Of Sale</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Principle Amt</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Interest Rate</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Warranty Start Dt Logic</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Warranty Period Months</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Warranty Period Days</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Warranty Start Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Warranty End Date Logic</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Warranty End Period Months</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Warranty End Period Days</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Warranty End Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Extnd Support Period Months</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Extnd Support Period Days</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Extnd Support End Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dispatch Address1</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dispatch Address2</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dispatch Address3</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dispatch City</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dispatch Postal Code</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dispatch State</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dispatch Conact Person Name</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dispatch Contact Person Mobile</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dispatch Lst Number</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dispatch Lst Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dispatch Address Type</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>New Service List Id</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>New Crm Order Id</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Remrks</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Billing Level</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Commitment Period</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Notice Period</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Circuit Id</b></td>
				</logic:equal>	
				<!-- Saurabh : End -->	
				<!--  Manisha : change for usage -->		
				<logic:equal name="isUsage" value="1">
					<td align="center" class='inner col2 head1'><b>
					<logic:present name="logicalSIDataReportList" scope="request">
							<logic:notEmpty  name="logicalSIDataReportList" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Order No</a>
							</logic:notEmpty>
							<logic:empty  name="logicalSIDataReportList" scope="request">
								Order No
							</logic:empty>
						</logic:present>
						</b>
					</td>
					<td align="center" class='inner col2 head1'><b>Logical Si Number</b></td>
					<td align="center" class='inner col2 head1'><b>Service Name</b></td>
					<td align="center" class='inner col2 head1'><b>Parent Line Name</b></td>
					<td align="center" class='inner col2 head1'><b>Line Name</b></td>
					<td align="center" class='inner col2 head1'><b>Contract Period Mnths</b></td>
					<td align="center" class='inner col2 head1'><b>Total Amount</b></td>
					<td align="center" class='inner col2 head1'><b>Billing Trigger Date</b></td>
					<td align="center" class='inner col4 head1'><b>Cust Po Number</b></td>
					<td align="center" class='inner col2 head1'><b>Cust Tot Po Amt</b></td>
					<td align="center" class='inner col4 head1'><b>Legal Entity</b></td>
					<td align="center" class='inner col2 head1'><b>Cust Po Date</b></td>
					<td align="center" class='inner col2 head1'><b>Po Contract Period</b></td>
					<td align="center" class='inner col2 head1'><b>Contract Start Date</b></td>
					<td align="center" class='inner col2 head1'><b>Contract End Date</b></td>
					<td align="center" class='inner col2 head1'><b>Cust Po Receive Date</b></td>
					<td align="center" class='inner col2 head1'><b>Order Line Id</b></td>
					<td align="center" class='inner col2 head1'><b>Service No</b></td>
					<td align="center" class='inner col2 head1'><b>M6 Order Id</b></td>
					<td align="center" class='inner col4 head1'><b>Remarks</b></td>
					<td align="center" class='inner col2 head1'><b>Credit Period</b></td>
					<td align="center" class='inner col2 head1'><b>Bill Type</b></td>
					<td align="center" class='inner col4 head1'><b>Bill Format</b></td>
					<td align="center" class='inner col2 head1'><b>Taxation</b></td>
					<td align="center" class='inner col4 head1'><b>License Co</b></td>
					<td align="center" class='inner col2 head1'><b>Country</b></td>
					<td align="center" class='inner col3 head1'><b>Address1</b></td>
					<td align="center" class='inner col2 head1'><b>Address2</b></td>
					<td align="center" class='inner col2 head1'><b>Address3</b></td>
					<td align="center" class='inner col2 head1'><b>Address4</b></td>
					<td align="center" class='inner col2 head1'><b>City</b></td>
					<td align="center" class='inner col2 head1'><b>Postal Code</b></td>
					<td align="center" class='inner col2 head1'><b>State</b></td>
					<td align="center" class='inner col2 head1'><b>Person Designation</b></td>
					<td align="center" class='inner col4 head1'><b>Contact Person Name</b></td>
					<td align="center" class='inner col2 head1'><b>Person Mobile</b></td>
					<td align="center" class='inner col4 head1'><b>Person Email</b></td>
					<td align="center" class='inner col2 head1'><b>Person Fax</b></td>
					<td align="center" class='inner col2 head1'><b>Remarks</b></td>
					<td align="center" class='inner col4 head1'><b>Billing Level</b></td>
					<td align="center" class='inner col2 head1'><b>Commitment Period</b></td>
					<td align="center" class='inner col2 head1'><b>Notice Period</b></td>
					<td align="center" class='inner col2 head1'><b>Circuit Id</b></td>
					<td align="center" class='inner col2 head1'><b>Child Account Number</b></td>
					<td align="center" class='inner col2 head1'><b>Child Account No Internal</b></td>
					<td align="center" class='inner col2 head1'><b>Child Account Fx Status</b></td>
					<td align="center" class='inner col2 head1'><b>Package ID</b></td>
					<td align="center" class='inner col4 head1'><b>Package Name</b></td>
					<td align="center" class='inner col2 head1'><b>Component ID</b></td>
					<td align="center" class='inner col3 head1'><b>Component Name</b></td>
					<td align="center" class='inner col2 head1'><b>Component InfoID</b></td>
					<td align="center" class='inner col2 head1'><b>Component Type</b></td>
					<!--<td align="center" class='inner col2 head1'><b>rental / nrc component amount</b></td>-->
					<td align="center" class='inner col2 head1'><b>Component Status</b></td>
					<td align="center" class='inner col4 head1'><b>Component Start Logic</b></td>
					<td align="center" class='inner col2 head1'><b>Start Date Days</b></td>
					<td align="center" class='inner col2 head1'><b>Start Date Months</b></td>
					<td align="center" class='inner col3 head1'><b>End Date Logic</b></td>
					<td align="center" class='inner col1 head1'><b>End Date Days</b></td>
					<td align="center" class='inner col1 head1'><b>End Date Months</b></td>
					<td align="center" class='inner col2 head1'><b>Component Start Date</b></td>
					<td align="center" class='inner col2 head1'><b>Component End Date</b></td>
				</logic:equal>
				<!-- Manisha : End -->	
				</tr>
				 </table>
					 </div> 	
					 <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
		       			<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>
		       			
				<logic:present name="logicalSIDataReportList" scope="request">
					<logic:notEmpty  name="logicalSIDataReportList" scope="request"> 					
						<logic:iterate id="row" name="logicalSIDataReportList" indexId="recordId">
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
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderNumber"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="recordStatus"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="logicalSINumber"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="parent_name"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceDetDescription"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeTypeName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="charge_status"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="totalPoAmt"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="frequencyName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="frequencyAmt"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="startDateLogic"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="startDateMonth"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="startDateDays"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingTriggerDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="endDateLogic"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="endDateMonth"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="endDateDays"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="annual_rate"/></td>
							<td align="left" style="font-size:9px" width="5%" ></td><!-- traiRate -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- discount -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- advance -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- installmentRate -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dnd_Dispatch_But_Not_Delivered"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dnd_Dispatch_And_Delivered"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dnd_Disp_Del_Not_Installed"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dnd_Disp_Delivered_Installed"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="poExclude"/></td><!-- poValidExclude -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custPoDetailNo"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="poAmount"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="entity"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custPoDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractStartDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="poRecieveDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeinfoID"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderLineNumber"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceId"/></td>
							<td align="left" style="font-size:9px" width="5%" ></td><!-- pkChargesId-->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- m6ProductId -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- parentProductId -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingInfoID"/></td><!-- chargeHdrId -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- ibPkChargesId -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- ibOrderLineId -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- m6orderId -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- orderLineSiNo -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="chargeAnnotation"/></td>
							<td align="left" style="font-size:9px" width="5%" ></td><!-- remarks -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="creditPeriodName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingTypeName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingFormatName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="taxation"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="lcompanyname"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="countyName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="address1"/></td><!-- address1 -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="address2"/></td><!-- address2 -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="address3"/></td><!-- address3 -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="address4"/></td><!-- address4 -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="cityName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="postalCode"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="stateName"/></td>
							<td align="left" style="font-size:9px" width="5%" ></td><!-- activeEndDate -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contactName"/></td><!-- contarPersonName -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="designation"/></td><!-- personDesignation -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="telePhoneNo"/></td><!-- personMobile -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="emailId"/></td><!-- personEmail -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="fax"/></td><!-- personFax -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="lst_No"/></td><!-- lstNo-->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="lstDate"/></td><!-- lstDate -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- billingAddressType -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="attributeLabel"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="attributeValue"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="storeName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="hardwaretypeName"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="saleNature"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="formAvailable"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="saleType"/></td>
							<td align="left" style="font-size:9px" width="5%" ></td><!-- principalAmt -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- intrestRate -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="warrantyStartDateLogic"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="warrantyPeriodMonths"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="warrantyPeriodDays"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="warrantyStartDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="warrantyEndDateLogic"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="warrantyEndPeriodMonths"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="warrantyEndPeriodDays"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="warrantyEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="extndSupportPeriodMonths"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="extndSupportPeriodDays"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="extSuportEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchAddress1"/></td><!-- dispatchAddress1 -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchAddress2"/></td><!-- dispatchAddress2 -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchAddress3"/></td><!-- dispatchAddress3 -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchCityName"/></td><!-- dispatchCity -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchPostalCode"/></td><!-- dispatchPostalCode -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchStateName"/></td><!-- dispatchState -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchPersonName"/></td><!-- dispatchContactPersonName -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchPhoneNo"/></td><!-- dispatchContactPersonMobile -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchLstNumber"/></td><!-- dispatchLstNumber -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="dispatchLstDate"/></td><!-- dispatchLstDate -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- DispatchAddressType -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- NewServiceListId -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- NewCrmOrderId -->
							<td align="left" style="font-size:9px" width="5%" ></td><!-- remarks -->
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingLevel"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="commitmentPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="noticePeriod"/></td>
							<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="logicalCircuitId"/></td>
						</logic:equal>	
						<!-- Saurabh : End -->

						<!--  Manisha : changes for Usage -->
						<logic:equal name="isUsage" value="1">
					
							<td align="left" class='inner col2' ><bean:write  name="row" property="orderNumber"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="logicalSINumber"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="serviceName"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="parent_name"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="serviceDetDescription"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="totalPoAmt"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="billingTriggerDate"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="custPoDetailNo"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="poAmount"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="entity"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="custPoDate"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="contractStartDate"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="contractEndDate"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="poRecieveDate"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="orderLineNumber"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="serviceId"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="m6OrderNo"/>&nbsp;</td><!-- m6orderId -->
							<td align="left" class='inner col4' ><bean:write  name="row" property="neworder_remarks"/>&nbsp;</td><!-- remarks -->
							<td align="left" class='inner col2' ><bean:write  name="row" property="creditPeriodName"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="billingTypeName"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="billingFormatName"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="taxation"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="lcompanyname"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="countyName"/>&nbsp;</td>
							<td align="left" class='inner col3' ><bean:write  name="row" property="address1"/>&nbsp;</td><!-- address1 -->
							<td align="left" class='inner col2' ><bean:write  name="row" property="address2"/>&nbsp;</td><!-- address2 -->
							<td align="left" class='inner col2' ><bean:write  name="row" property="address3"/>&nbsp;</td><!-- address3 -->
							<td align="left" class='inner col2' ><bean:write  name="row" property="address4"/>&nbsp;</td><!-- address4 -->
							<td align="left" class='inner col2' ><bean:write  name="row" property="cityName"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="postalCode"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="stateName"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="contactName"/>&nbsp;</td><!-- contarPersonName -->
							<td align="left" class='inner col4' ><bean:write  name="row" property="designation"/>&nbsp;</td><!-- personDesignation -->
							<td align="left" class='inner col2' ><bean:write  name="row" property="telePhoneNo"/>&nbsp;</td><!-- personMobile -->
							<td align="left" class='inner col4' ><bean:write  name="row" property="emailId"/>&nbsp;</td><!-- personEmail -->
							<td align="left" class='inner col2' ><bean:write  name="row" property="fax"/>&nbsp;</td><!-- personFax -->
							<td align="left" class='inner col2' ><bean:write  name="row" property="disconnection_remarks"/>&nbsp;</td></td><!-- remarks -->
							<td align="left" class='inner col4' ><bean:write  name="row" property="billingLevel"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="commitmentPeriod"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="noticePeriod"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="logicalCircuitId"/>&nbsp;</td>
								
							<td align="left" class='inner col2' ><bean:write  name="row" property="fx_external_acc_id"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="fxInternalId"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="child_account_creation_status"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="packageID"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="packageName"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentID"/>&nbsp;</td>
							<td align="left" class='inner col3' ><bean:write  name="row" property="componentName"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentInfoID"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentType"/>&nbsp;</td>
							<!--<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentAmount"/>&nbsp;</td>-->
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentStatus"/>&nbsp;</td>
							<td align="left" class='inner col4' ><bean:write  name="row" property="componentDto.startLogic"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.startDateDays"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.startDateMonth"/>&nbsp;</td>
							<td align="left" class='inner col3' ><bean:write  name="row" property="componentDto.endLogic"/>&nbsp;</td>
							<td align="left" class='inner col1' ><bean:write  name="row" property="componentDto.endDateDays"/>&nbsp;</td>
							<td align="left" class='inner col1' ><bean:write  name="row" property="componentDto.endDateMonth"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.startDate"/>&nbsp;</td>
							<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.end_date"/>&nbsp;</td>
						</logic:equal>
						<!--  Manisha : End -->
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
		              	<div class='horizontal-scrolllogicalsidatereportusage' onscroll='reposHorizontal(this);'>
		                  	<div>
		                  	</div>
		              	</div>	
		          	</td>
		     	</tr>	
		     </table>	
		     	 <!-- End Scrolling and Freezing of LOGICAL SI DATA REPORT(USAGE)    -->
					
					<logic:empty  name="logicalSIDataReportList">
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
