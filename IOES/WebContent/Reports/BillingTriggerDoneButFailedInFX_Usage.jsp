<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- [TRNG21052013004]    Vijay        24 June   - create freez header -->
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
<title>Billing Trigger Done But Failed In FX</title>
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
	myForm.submit();
}


function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	myform.toExcel.value='false';
	//myform.reset();
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewBillingTriggerDoneButFailedInFX&usage="+isUsage;		
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
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewBillingTriggerDoneButFailedInFX&usage="+isUsage;
		//showLayer();
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
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewBillingTriggerDoneButFailedInFX&usage="+isUsage;
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
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewBillingTriggerDoneButFailedInFX&inExcel=true&usage="+isUsage;		
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
	myForm.fromAccountNo.value='' ;
	myForm.toAccountNo.value='' ;
	myForm.fromDate.value='' ;	
	myForm.toDate.value='' ;	
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');	
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	var toOrderNo=myForm.toOrderNo.value;
	var fromOrderNo=myForm.fromOrderNo.value;
	var toAccountNo=myForm.toAccountNo.value;
	var fromAccountNo=myForm.fromAccountNo.value;
	
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




function resetFilterCriterion(){
	var myForm=document.getElementById('searchForm');
	var orderType=myForm.orderType.value;
	var fromOrderNo=myForm.fromOrderNo.value;
	var toOrderNo=myForm.toOrderNo.value;
	var fromAccountNo=myForm.fromAccountNo.value;
	var toAccountNo=myForm.toAccountNo.value;
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
		<div border="1" class="head"> <span>BILLING TRIGGER DONE BUT FAILED IN FX</span> </div>
		</logic:equal>
		<logic:equal name="isUsage" value="1">
			<div border="1" class="head"> 
			
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>BILLING TRIGGER DONE BUT FAILED IN FX(USAGE)</span> 
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
						<td align="right" style="font-size:9px"> ORDER TYPE :</td>
						<td align="left" nowrap>
							<html:select property="orderType" >
							<html:option value="">SELECT</html:option>
							<html:option value="New">NEW</html:option>
							<html:option value="Change">CHANGE</html:option>
							</html:select>
						</td>
						<td align="right" style="font-size:9px;">FROM ORDER NO:</td>
						<td align="left">
							<html:text  property="fromOrderNo" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
						</td>
						<td align="right" style="font-size:9px">TO ORDER NO:</td>
						<td align="left">
							<html:text  property="toOrderNo" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
						</td>
						<td align="right" style="font-size:9px">FROM ACCOUNT NO:</td>
						<td align="left">
							<html:text  property="fromAccountNo" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
						</td>
						<td align="center" valign="top">							
						</td>
					</tr>
					<tr>
						<td align="right" style="font-size:9px;">TO ACCOUNT NO:</td>
						<td align="left">
							<html:text  property="toAccountNo" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();">></html:text>
						</td>
						<td align="right" style="font-size:9px;">From Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="dateFrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td align="right" style="font-size:9px;">To Date:</td>
						<td nowrap><html:text  property="toDate" styleId="dateTo" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td align="left">
							<div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" onClick="javascript:exportToExcel();">Export To Excel</a></div>
						</td>
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>		

					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Billing Trigger Done But Failed In FX</b></legend>
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
					<td align="center" style="font-size:9px"><b>
					<logic:present name="billingTriggerDoneButFailedInFXList" scope="request">
							<logic:notEmpty  name="billingTriggerDoneButFailedInFXList" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Order No</a>
							</logic:notEmpty>
							<logic:empty  name="billingTriggerDoneButFailedInFXList" scope="request">
								Order No
							</logic:empty>
						</logic:present>
						</b>
					</td>				
					<td align="center" style="font-size:9px"><b>Logical Circuit Id</b></td>
					<td align="center" style="font-size:9px"><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px"><b>Service Name</b></td>
					<td align="center" style="font-size:9px"><b>Line Name</b></td>
					<td align="center" style="font-size:9px"><b>Charge Type</b></td>										
					<td align="center" style="font-size:9px"><b>Charge Name</b></td>
					<td align="center" style="font-size:9px"><b>Frequency</b></td>
					<td align="center" style="font-size:9px"><b>Bill Period</b></td>
					<td align="center" style="font-size:9px"><b>Dnd Dispatch But Not Delivered</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dnd Dispatch And Delivered</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dnd Disp Del Not Installed</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dnd Disp Delivered Installed</b></td>
					<td align="center" style="font-size:9px"><b>Start Date Logic</b></td>
					<td align="center" style="font-size:9px" width="20px"><b>End Date Logic</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Charge Start Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Charge End Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Contract Start Date</b></td>	
					<td align="center" style="font-size:9px" height="30px"><b>Contract End Date</b></td>	
					<td align="center" style="font-size:9px" height="30px"><b>Account Number</b></td>	
					<td align="center" style="font-size:9px" height="30px"><b>Credit Period</b></td>										
					<td align="center" style="font-size:9px" height="30px"><b>Currency</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Legal Entity</b></td>					
					<td align="center" style="font-size:9px" height="30px"><b>Billing Mode</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Bill Type</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Bill Format</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Licence Company</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Taxation</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Dispatch Address</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Penelty Clause</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Billing Level</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Billing Level Number</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Store</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Hardware Type</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Form C Available</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Nature Of Sale</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Type Of Sale</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Pri Location</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Sec Location</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Po Number</b></td>					
					<td align="center" style="font-size:9px" height="30px"><b>Po Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Party</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Party Number</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Annotation</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Fx Status</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Fx Sd Chg Status</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Fx Ed Chg Status</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Token No</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Billing Trig Flag</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Pm Prov Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Loc Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Billing Trig Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Challen No</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Challen Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Child Account Number</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Child Account Fx Status</b></td>					
					<td align="center" style="font-size:9px" height="30px"><b>Warranty Start Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Warranty End Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Extnd Support End Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Orderdate</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Approved Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Order Type</b></td>
					<!--<td align="center" style="font-size:9px" height="30px"><b>Order Type Id</b></td>-->
					<td align="center" style="font-size:9px" height="30px"><b>Service Order Type</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Service Order Type Desc</b></td>					
					<td align="center" style="font-size:9px" height="30px"><b>Copc Approved Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Bill Trg Create Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Cust Logical Si No</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Hardware Flag</b></td>
					<!--<td align="center" style="font-size:9px" height="30px"><b>Charge Type Id</b></td> -->
					<td align="center" style="font-size:9px" height="30px"><b>Service Stage</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Order Stage</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Account Mgr</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Project Mgr</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Order Creation Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Customer Service Rfs Date</b></td>					
					<td align="center" style="font-size:9px" height="30px"><b>Cust Po Receive Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Token No Ed</b></td>
					<!--<td align="center" style="font-size:9px" height="30px"><b>Fx Status Ed</b></td>-->
					<td align="center" style="font-size:9px" height="30px"><b>Cust Po Number</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Cust Po Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Charge Status</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Loc Number</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Billing Address</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Si Id</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Cancel By</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Cancel Date</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Cancel Reason</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Opms Account Id</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Region</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Bandwidth</b></td>					
					<td align="center" style="font-size:9px" height="30px"><b>Vertical</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Location From</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Location To</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Coll Manager</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Coll Mgr Mail</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Coll Mgr Ph</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Billing Bandwidth</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Demo Type</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Crm Order Id</b></td>
					<!-- <td align="center" style="font-size:9px" height="30px"><b>Order Number</b></td>-->
					<!--<td align="center" style="font-size:9px" height="30px"><b>Charge Hdr Id</b></td>-->
					<td align="center" style="font-size:9px" height="30px"><b>Order Line Id</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Service No</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Inv Amt</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Amt</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Total Amount</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Installment Rate</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Start Date Days</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Start Date Months</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>End Date Days</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>End Date Months</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Annual Rate</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Trai Rate</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Discount</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Contract Period Mnths</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Commitment Period</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Notice Period</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Principal Amt</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Intrest Rate</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Period In Month</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Tot Po Amt</b></td>
					<!-- <td align="center" style="font-size:9px" height="30px"><b>Party Id</b></td>-->
					<!-- <td align="center" style="font-size:9px" height="30px"><b>Cust Account Id</b></td>-->
					<!-- <td align="center" style="font-size:9px" height="30px"><b>M6 Product Id</b></td>-->
					<!-- <td align="center" style="font-size:9px" height="30px"><b>M6 Order Id</b></td>-->
					<!-- <td align="center" style="font-size:9px" height="30px"><b>Ib Order Line Id</b></td>-->
					<!-- <td align="center" style="font-size:9px" height="30px"><b>Ib Service List Id</b></td>-->
					<!-- <td align="center" style="font-size:9px" height="30px"><b>Ib Pk Charges Id</b></td>-->
					<!-- <td align="center" style="font-size:9px" height="30px"><b>Fx Sno</b></td>-->
					<!-- <td align="center" style="font-size:9px" height="30px"><b>Fx Sno Ed</b></td>-->
					<td align="center" style="font-size:9px" height="30px"><b>Cust Tot Po Amt</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>M6 Order No</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Business Serial No</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Bus Serial</b></td>
					<td align="center" style="font-size:9px" height="30px"><b>Advance</b></td>
				</logic:equal>
				
				<logic:equal name="isUsage" value="1">
					<td align="center" class='inner col2 head1'><b>
					<logic:present name="billingTriggerDoneButFailedInFXList" scope="request">
							<logic:notEmpty  name="billingTriggerDoneButFailedInFXList" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Order No</a>
							</logic:notEmpty>
							<logic:empty  name="billingTriggerDoneButFailedInFXList" scope="request">
								Order No
							</logic:empty>
						</logic:present>
						</b>
					</td>				
					<td align="center" class='inner col2 head1'><b>Logical SI No</b></td>
					<td align="center" class='inner col2 head1'><b>Cust Logical Si Id</b></td>
					<td align="center" class='inner col3 head1'><b>Service Name</b></td>
					<td align="center" class='inner col3 head1'><b>Line Name</b></td>															
					<td align="center" class='inner col2 head1'><b>Contract Start Date</b></td>	
					<td align="center" class='inner col2 head1'><b>Contract End Date</b></td>	
					<td align="center" class='inner col2 head1'><b>Account Number</b></td>	
					<td align="center" class='inner col1 head1'><b>Credit Period</b></td>										
					<td align="center" class='inner col2 head1'><b>Currency</b></td>
					<td align="center" class='inner col2 head1'><b>Legal Entity</b></td>					
					<td align="center" class='inner col2 head1'><b>Billing Mode</b></td>
					<td align="center" class='inner col2 head1'><b>Bill Type</b></td>
					<td align="center" class='inner col2 head1'><b>Bill Format</b></td>
					<td align="center" class='inner col3 head1'><b>Licence Company</b></td>
					<td align="center" class='inner col2 head1'><b>Taxation</b></td>					
					<td align="center" class='inner col2 head1'><b>Penelty Clause</b></td>
					<td align="center" class='inner col2 head1'><b>Billing Level</b></td>
					<td align="center" class='inner col2 head1'><b>Billing Level Number</b></td>					
					<td align="center" class='inner col4 head1'><b>Pri Location</b></td>
					<td align="center" class='inner col4 head1'><b>Sec Location</b></td>
					<td align="center" class='inner col2 head1'><b>Po Number</b></td>					
					<td align="center" class='inner col2 head1'><b>Po Date</b></td>
					<td align="center" class='inner col3 head1'><b>Party</b></td>
					<td align="center" class='inner col2 head1'><b>Party Number</b></td>										
					<td align="center" class='inner col2 head1'><b>Billing Trig Flag</b></td>
					<td align="center" class='inner col2 head1'><b>Pm Prov Date</b></td>
					<td align="center" class='inner col2 head1'><b>Loc Date</b></td>
					<td align="center" class='inner col2 head1'><b>Billing Trig Date</b></td>					
					<td align="center" class='inner col4 head1'><b>Child Account Number</b></td>
					<td align="center" class='inner col2 head1'><b>Child Account Internal No</b></td>
					<td align="center" class='inner col2 head1'><b>Child Account Fx Status</b></td>															
					<td align="center" class='inner col2 head1'><b>Orderdate</b></td>
					<td align="center" class='inner col2 head1'><b>Approved Date</b></td>
					<td align="center" class='inner col2 head1'><b>Order Type</b></td>					
					<td align="center" class='inner col2 head1'><b>Service Order Type</b></td>					
					<td align="center" class='inner col2 head1'><b>Copc Approved Date</b></td>
					<td align="center" class='inner col2 head1'><b>Bill Trg Create Date</b></td>
					<td align="center" class='inner col2 head1'><b>Cust Logical Si No</b></td>								
					<td align="center" class='inner col2 head1'><b>Service Stage</b></td>
					<td align="center" class='inner col2 head1'><b>Order Stage</b></td>
					<td align="center" class='inner col2 head1'><b>Account Mgr</b></td>
					<td align="center" class='inner col2 head1'><b>Project Mgr</b></td>
					<td align="center" class='inner col2 head1'><b>Order Creation Date</b></td>
					<td align="center" class='inner col2 head1'><b>Customer Service Rfs Date</b></td>					
					<td align="center" class='inner col2 head1'><b>Cust Po Receive Date</b></td>					
					<td align="center" class='inner col2 head1'><b>Cust Po Number</b></td>
					<td align="center" class='inner col2 head1'><b>Cust Po Date</b></td>					
					<td align="center" class='inner col2 head1'><b>Loc Number</b></td>
					<td align="center" class='inner col4 head1'><b>Billing Address</b></td>
					<td align="center" class='inner col2 head1'><b>Si Id</b></td>
					<td align="center" class='inner col2 head1'><b>Cancel By</b></td>
					<td align="center" class='inner col2 head1'><b>Cancel Date</b></td>
					<td align="center" class='inner col2 head1'><b>Cancel Reason</b></td>
					<td align="center" class='inner col2 head1'><b>Region</b></td>
					<td align="center" class='inner col2 head1'><b>Bandwidth</b></td>					
					<td align="center" class='inner col2 head1'><b>Vertical</b></td>
					<td align="center" class='inner col2 head1'><b>Location From</b></td>
					<td align="center" class='inner col2 head1'><b>Location To</b></td>
					<td align="center" class='inner col3 head1'><b>Coll Manager</b></td>
					<td align="center" class='inner col3 head1'><b>Coll Mgr Mail</b></td>
					<td align="center" class='inner col2 head1'><b>Coll Mgr Ph</b></td>
					<td align="center" class='inner col1 head1'><b>Billing Bandwidth</b></td>
					<td align="center" class='inner col2 head1'><b>Demo Type</b></td>
					<td align="center" class='inner col2 head1'><b>Crm Order Id</b></td>										
					<td align="center" class='inner col2 head1'><b>Service No</b></td>																								
					<td align="center" class='inner col1 head1'><b>Contract Period Mnths</b></td>
					<td align="center" class='inner col1 head1'><b>Commitment Period</b></td>
					<td align="center" class='inner col1 head1'><b>Notice Period</b></td>									
					<td align="center" class='inner col1 head1'><b>Period In Month</b></td>
					<td align="center" class='inner col2 head1'><b>Tot Po Amt</b></td>					
					<td align="center" class='inner col2 head1'><b>Cust Tot Po Amt</b></td>
					<td align="center" class='inner col2 head1'><b>M6 Order No</b></td>					
					<!--  Manisha : change for usage -->															
					<td align="center" class='inner col2 head1' ><b>Package ID</b></td>
					<td align="center" class='inner col4 head1' ><b>Package Name</b></td>								
					<td align="center" class='inner col2 head1' ><b>Component InfoID</b></td>
					<td align="center" class='inner col2 head1' ><b>Component ID</b></td>
					<td align="center" class='inner col4 head1' ><b>Component Name</b></td>
					<td align="center" class='inner col2 head1' ><b>Component Status</b></td>
					<td align="center" class='inner col2 head1' ><b>Component Start Logic</b></td>
					<td align="center" class='inner col2 head1' ><b>Component Start Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Component End Logic</b></td>
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
					<td align="center" class='inner col2 head1' ><b>End FX Status</b></td>														
					<!-- Manisha : End -->	
				</logic:equal>
				</tr>
				</table>
					 </div> 	
					 <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	        			<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>
	        							
				<logic:present name="billingTriggerDoneButFailedInFXList" scope="request">
					<logic:notEmpty  name="billingTriggerDoneButFailedInFXList" scope="request"> 					
						<logic:iterate id="row" name="billingTriggerDoneButFailedInFXList" indexId="recordId">
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
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="logicalSINumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceName"/></td>	
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceDetDescription"/></td>															
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeTypeName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="frequencyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dnd_Dispatch_But_Not_Delivered"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dnd_Dispatch_And_Delivered"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dnd_Disp_Del_Not_Installed"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dnd_Disp_Delivered_Installed"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDateLogic"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endDateLogic"/></td>								
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endDate"/></td>								
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractStartDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="crmAccountNoString"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="creditPeriodName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="currencyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="entity"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingMode"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTypeName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingFormatName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lcompanyname"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="taxation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="dispatchAddressName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="penaltyClause"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingLevelNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="store"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="hardwaretypeName"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="formAvailable"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="saleNature"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="saleType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="primaryLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="secondaryLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="ponum"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="party_no"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="partyName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAnnotation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fxStatus"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_St_Chg_Status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_Ed_Chg_Status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="tokenNO"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_Trigger_Flag"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_Date"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="locDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingTriggerDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="challenno"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="challendate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fx_external_acc_id"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="childAccountFXSataus"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="warrantyStartDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="warrantyEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="extSuportEndDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderType"/></td>
							<!--<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td> Order Type Id -->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Service Order Type -->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Service Order Type Desc -->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="copcApproveDate"/></td>	
							<td align="left" style="font-size:9px" width="5%"></td><!-- Bill Trg Create Date -->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Cust Logical SI NO -->														
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="hardwareFlag"/></td>
							<!--<td align="left" style="font-size:9px" width="5%"></td>Charge Type Id-->							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceStage"/></td>
							<td align="left" style="font-size:9px" width="5%"></td><!--Order Stage-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="accountManager"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="projectManager"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderDate"/></td><!-- order creation Date -->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="rfsDate"/></td><!--Customer Service Rfs Date-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poRecieveDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="tokenNoEd"/></td><!--Token No Ed-->
							<!--<td align="left" style="font-size:9px" width="5%"></td>Fx Status Ed-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDetailNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="custPoDate"/></td>																						
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="charge_status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="LOC_No"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billingAddress"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fxSiId"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cancelBy"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="canceldate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="cancelReason"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="opms_Account_Id"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="regionName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="bandwaidth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="verticalDetails"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="toLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="fromLocation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="coll_Manager"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="coll_Manager_Mail"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="coll_Manager_Phone"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="billing_bandwidth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="order_type"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderNumber"/></td>						
							<!--<td align="left" style="font-size:9px" width="5%"></td> Charge Hdr Id-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="orderLineNumber"/></td><!-- Order Line Id-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="serviceId"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="chargeAmount_String"/></td><!--Inv Amt-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="lineItemAmount"/></td><!--Line item Amt-->
							<td align="left" style="font-size:9px" width="5%" <bean:write  name="row" property="totalPoAmt"/>></td><!--Total Amount-->
							<td align="left" style="font-size:9px" width="5%"></td><!--Installment Rate-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDateDays"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="startDateMonth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endDateDays"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="endDateMonth"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="annualRate"/></td>
							<td align="left" style="font-size:9px" width="5%"></td><!-- Trai Rate -->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Discount -->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td>							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="commitmentPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="noticePeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"></td><!-- Principal Amt -->
							<td align="left" style="font-size:9px" width="5%"></td><!-- Intrest Rate -->							
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="contractPeriod"/></td><!-- Period in month -->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="totalPoAmt"/></td>
							<!--<td align="left" style="font-size:9px" width="5%">Party Id -->
							<!--<td align="left" style="font-size:9px" width="5%">Cust Account Id-->
							<!--<td align="left" style="font-size:9px" width="5%"></td>M6 Product Id-->
							<!--<td align="left" style="font-size:9px" width="5%"></td>M6 Order Id-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td>--Ib Order Line Id-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td><!--Ib Service List Id-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td><!--Ib Pk Charges Id-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td><!--Ib Pk Charges Id-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td><!--Fx Sno-->
							<!-- <td align="left" style="font-size:9px" width="5%"><bean:write  name="row" /></td><!--Fx Sno Ed-->
							<td align="left" style="font-size:9px" width="5%"><bean:write  name="row" property="poAmount"/></td><!--Cust Tot Po Amt-->
							<td align="left" style="font-size:9px" width="5%"></td><!--M6 Order No-->
							<td align="left" style="font-size:9px" width="5%"></td><!--Business Serial No-->
							<td align="left" style="font-size:9px" width="5%"></td><!--Bus Serial-->
							<td align="left" style="font-size:9px" width="5%"></td><!--Advance-->
							</logic:equal>
								
							<logic:equal name="isUsage" value="1">
								<td align="left" class='inner col2'><bean:write  name="row" property="orderNumber"/>&nbsp;</td>																	
								<td align="left" class='inner col2'><bean:write  name="row" property="logicalSINumber"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="customer_logicalSINumber"/>&nbsp;</td>										
								<td align="left" class='inner col3'><bean:write  name="row" property="serviceName"/>&nbsp;</td>										
								<td align="left" class='inner col3'><bean:write  name="row" property="serviceDetDescription"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="contractStartDate"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="contractEndDate"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="crmAccountNoString"/>&nbsp;</td>										
								<td align="left" class='inner col1'><bean:write  name="row" property="creditPeriodName"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="currencyName"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="entity"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="billingMode"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="billingTypeName"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="billingFormatName"/>&nbsp;</td>										
								<td align="left" class='inner col3'><bean:write  name="row" property="lcompanyname"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="taxation"/>&nbsp;</td>																		
								<td align="left" class='inner col2'><bean:write  name="row" property="penaltyClause"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="billingLevelName"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="billingLevelNo"/>&nbsp;</td>																										
								<td align="left" class='inner col4'><bean:write  name="row" property="primaryLocation"/>&nbsp;</td>										
								<td align="left" class='inner col4'><bean:write  name="row" property="secondaryLocation"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="ponum"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="poDate"/>&nbsp;</td>										
								<td align="left" class='inner col3'><bean:write  name="row" property="partyName"/>&nbsp;</td>																												
								<td align="left" class='inner col2'><bean:write  name="row" property="party_no"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="billing_Trigger_Flag"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="LOC_Date"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="locDate"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="billingTriggerDate"/>&nbsp;</td>																											
								<td align="left" class='inner col4'><bean:write  name="row" property="fx_external_acc_id"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="fx_internal_acc_id"/>&nbsp;</td>	
								<td align="left" class='inner col2'><bean:write  name="row" property="childAccountFXSataus"/>&nbsp;</td>																											
								<td align="left" class='inner col2'><bean:write  name="row" property="orderDate"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="orderApproveDate"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="orderType"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="serviceOrderType"/>&nbsp;</td><!-- Service Order Type -->																			
								<td align="left" class='inner col2'><bean:write  name="row" property="copcApproveDate"/>&nbsp;</td>									 	
								<td align="left" class='inner col2'><bean:write  name="row" property="billingTriggerDate"/>&nbsp;</td><!-- Bill Trg Create Date -->										
								<td align="left" class='inner col2'><bean:write  name="row" property="customer_logicalSINumber"/>&nbsp;</td><!-- Cust Logical SI NO -->																		
								<td align="left" class='inner col2'><bean:write  name="row" property="serviceStage"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="orderStage"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="accountManager"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="projectManager"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="orderDate"/>&nbsp;</td><!-- order creation Date -->										
								<td align="left" class='inner col2'><bean:write  name="row" property="rfsDate"/>&nbsp;</td><!--Customer Service Rfs Date-->										
								<td align="left" class='inner col2'><bean:write  name="row" property="poRecieveDate"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="custPoDetailNo"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="custPoDate"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="LOC_No"/>&nbsp;</td>										
								<td align="left" class='inner col4'><bean:write  name="row" property="billingAddress"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="fxSiId"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="cancelBy"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="canceldate"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="cancelServiceReason"/>&nbsp;</td>																			
								<td align="left" class='inner col2'><bean:write  name="row" property="regionName"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="bandwaidth"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="verticalDetails"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="toLocation"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="fromLocation"/>&nbsp;</td>										
								<td align="left" class='inner col3'><bean:write  name="row" property="coll_Manager"/>&nbsp;</td>										
								<td align="left" class='inner col3'><bean:write  name="row" property="coll_Manager_Mail"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="coll_Manager_Phone"/>&nbsp;</td>										
								<td align="left" class='inner col1'><bean:write  name="row" property="billing_bandwidth"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="order_type"/>&nbsp;</td>										
								<td align="left" class='inner col2'><bean:write  name="row" property="orderNumber"/>&nbsp;</td>																		
								<td align="left" class='inner col2'><bean:write  name="row" property="serviceId"/>&nbsp;</td>																																								
								<td align="left" class='inner col1'><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>											
								<td align="left" class='inner col1'><bean:write  name="row" property="commitmentPeriod"/>&nbsp;</td>											
								<td align="left" class='inner col1'><bean:write  name="row" property="noticePeriod"/>&nbsp;</td>																														
								<td align="left" class='inner col1'><bean:write  name="row" property="contractPeriod"/>&nbsp;</td><!-- Period in month -->											
								<td align="left" class='inner col2'><bean:write  name="row" property="totalPoAmt"/>&nbsp;</td>											
								<td align="left" class='inner col2'><bean:write  name="row" property="poAmount"/>&nbsp;</td><!--Cust Tot Po Amt-->											
								<td align="left" class='inner col2'><bean:write  name="row" property="m6OrderNo"/>&nbsp;</td><!--M6 Order No	-->																												
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
								<td align="left" class='inner col1' ><bean:write  name="row" property="componentDto.fxStartStatus"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.endTokenNo"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.endFxStatus"/>&nbsp;</td>											
								<!--  Manisha : End -->																											
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
		              	<div class='horizontal-scrollbilltriggerdonebutfailinfxusage' onscroll='reposHorizontal(this);'>
		                  	<div>
		                  	</div>
		              	</div>
		          	</td>
		     	</tr>	
		     </table>	
		     	 <!-- End Scrolling and Freezing of Copy and Cancel Report   -->
		     	 
					<logic:empty  name="billingTriggerDoneButFailedInFXList">
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
