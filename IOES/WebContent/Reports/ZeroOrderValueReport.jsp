<!-- [001] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.ReportsBean"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="com.ibm.ioes.utilities.ApplicationFlags"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>ZERO VALUE ORDER REPORT</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ReportHeaderFreeze.js"></script>
<link href="./css/ReportHeaderFreeze.css" type="text/css" rel="stylesheet">
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
	myform.method.value='viewZeroOrderValueReportDetails';	
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
	myform.method.value='viewZeroOrderValueReportDetails';	
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewZeroOrderValueReportDetails";
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewZeroOrderValueReportDetails&inExcel=true";
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
	myForm.toAccountNo.value='' ;
	myForm.fromAccountNo.value='' ;
	myForm.toOrderNo.value='' ;
	myForm.fromOrderNo.value='' ;	
	
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
	var orderStage=myForm.orderType.value;
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	var toAccountNo=myForm.toAccountNo.value;
	var fromAccountNo=myForm.fromAccountNo.value;
	var toOrderNo=myForm.toOrderNo.value;
	var fromOrderNo=myForm.fromOrderNo.value;
	var custPoDetailNo=myForm.custPoDetailNo.value;

  /*	if((orderStage==null && fromDate==null && toDate==null &&   toAccountNo==null &&  fromAccountNo==null &&  toOrderNo==null &&  fromOrderNo==null && custPoDetailNo == null
	|| 
	trim(orderStage)=="" && trim(toDate)=="" && trim(fromDate)==""  && trim(toAccountNo)==""  && trim(fromAccountNo)==""  && trim(toOrderNo)==""  && trim(fromOrderNo)=="" && trim(custPoDetailNo)==""  )){
		alert("Please enter atleast one search criteria!");
		return false;
		
	} */ 
	//else {
			
		if((fromDate == null & toDate == null) || ( trim(fromDate)=="" & trim(toDate) == "")){
			alert("Please enter From Cust PO Date and To Cust PO Date!");
			return false;
		}
    else{
        
        if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
		{
			alert("Please enter From Cust PO Date");
			return false;
		}
		else if((toDate == null || trim(toDate)== "" ) && (fromDate != null && trim(fromDate) != "" ))
		{
			alert("Please enter To Cust PO Date");
			return false;
		}
		if(dateCompare(fromDate,toDate)==1)
		{			
				alert("From Cust PO Date cannot be greater than To Cust PO Date");
				return false;
		}
	}	
		/*if((fromAccountNo == 0 || trim(fromAccountNo)== "" ) && (toAccountNo != 0 && trim(toAccountNo) != "" ))
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
			} */
		return true;
	//}
}
function downloadDump(formId,fileName)
{
	myForm=document.getElementById(formId);
	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=getDumpFile&fileName="+fileName;
	
	var dumpname = '<%= AppConstants.ACTION_DUMP %>';
	var jsData =	{
		userId:userId,
		interfaceId:interfaceId,
		actionType:dumpname
		
	};		
    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	jsonrpc.processes.populateReportUsageDetails(jsData);  
//	showLayer();
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
		<html:hidden property="interfaceId"/>
		<html:hidden property="isDumpAvailable"/>
		<html:hidden property="dumpFileName"/>		
		<html:hidden property="reportProcessForLimit"/>
		<html:hidden property="reportCurrentCount"/>
		<input type="hidden" name="fromPageSubmit" value="1"/>
		<input type="hidden" name="method" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="left">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> 
			
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>ZERO VALUE ORDER REPORT</span> 
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
							<html:select  property="orderType" styleClass="inputBorder2" style="width:90px;float:left;" onkeydown="if (event.keyCode == 13)  return searchClick();" >
							<html:option value="">SELECT</html:option>
							<html:option value="New">NEW</html:option>
							<html:option value="Change">CHANGE</html:option>
							</html:select>
						</td>
						<td align="right" style="font-size:9px;"> From Cust PO Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="datefrom" styleClass="inputBorder1" style="width:75px;" readonly="true" onkeydown="if (event.keyCode == 13)  return searchClick();"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
					
						<td align="right" style="font-size:9px;"> To Cust PO Date:</td>
						<td nowrap><html:text  property="toDate" styleId="dateto" styleClass="inputBorder1" style="width:75px;" readonly="true" onkeydown="if (event.keyCode == 13)  return searchClick();"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
							<td width="10px"/>
							<td align="left"  width="170px"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
						<td width="70px"/>
						
					</tr>
					<tr>
					<td align="right" style="font-size:9px;">From Order No:</td>
						<td nowrap><html:text  property="fromOrderNo" styleClass="inputBorder2" style="width:75px;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"  onkeydown="if (event.keyCode == 13)  return searchClick();"></html:text>
					</td>
					<td align="right" style="font-size:9px;">To Order No:</td>
						<td nowrap><html:text  property="toOrderNo" styleClass="inputBorder2" style="width:75px;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13)  return searchClick();"></html:text>
					</td>
					<td align="right" style="font-size:9px;">From Account No:</td>
						<td nowrap><html:text  property="fromAccountNo" styleClass="inputBorder2" style="width:75px;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13)  return searchClick();"></html:text>
					</td>
					<td align="right" style="font-size:9px;">To Account No:</td>
						<td nowrap><html:text  property="toAccountNo" styleClass="inputBorder2" style="width:75px;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13)  return searchClick();"></html:text>
					</td>
					
					<td align="right" style="font-size:9px;">Cust PO Detail No:</td>
					<td nowrap><html:text  property="custPoDetailNo" styleClass="inputBorder2" style="width:75px;" onkeydown="if (event.keyCode == 13)  return searchClick();" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'CustomerPODetailNo')};"></html:text>
					</td>
					
					</tr>
					</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b> Zero Value Order Report </b></legend>
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
			
			<!-- Start Scrolling and Freezing of ZERO VALUE ORDER REPORT  -->
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
								<td align="center" class='inner col3 head1'><b>Party Name</b></td>
								<td align="center" class='inner col2 head1'><b>Order No</b></td>
								<td align="center" class='inner col2 head1'><b>Po Id</b></td>		
								<td align="center" class='inner col2 head1'><b>Cust Account Id</b></td>
								<td align="center" class='inner col2 head1'><b>Legal Entity Code</b></td>
								<td align="center" class='inner col4 head1'><b>Cust Po Number</b></td>	
								<td align="center" class='inner col2 head1'><b>Cust Po Date</b></td>		
								<td align="center" class='inner col2 head1'><b>Tot Po Amt</b></td>
								<td align="center" class='inner col2 head1'><b>Po Payment Terms</b></td>
								<td align="center" class='inner col2 head1'><b>Contract Start Date</b></td>
							 	<td align="center" class='inner col2 head1'><b>Contract End Date</b></td>  
								<td align="center" class='inner col2 head1'><b>Contract Period Mnths</b></td>
								<td align="center" class='inner col2 head1'><b>Cust Po Receive Date</b></td>
								<td align="center" class='inner col3 head1'><b>Po Issuing Person Name</b></td>		
								<td align="center" class='inner col4 head1'><b>Po Issuing Person Email</b></td>
								<td align="center" class='inner col2 head1'><b>Last Update Date</b></td>		
								<td align="center" class='inner col3 head1'><b>Last Updated By</b></td>
								<td align="center" class='inner col2 head1'><b>Creation Date</b></td>		
								<td align="center" class='inner col2 head1'><b>Created By</b></td>
								<td align="center" class='inner col2 head1'><b>Account Number</b></td>
								<td align="center" class='inner col1 head1'><b>Default Flag</b></td>
								<td align="center" class='inner col2 head1'><b>Po End Date</b></td>					
								<td align="center" class='inner col2 head1'><b>Party Id</b></td>
								<td align="center" class='inner col4 head1'><b>Bl Source</b></td>
								<td align="center" class='inner col2 head1'><b>Contract Start Dt Cal</b></td>
								<td align="center" class='inner col2 head1'><b>Contract End Dt Cal</b></td>
								<td align="center" class='inner col2 head1'><b>Demo Contract Period</b></td>		
								<td align="center" class='inner col2 head1'><b>Logical Si Number</b></td>
								<td align="center" class='inner col2 head1'><b>Service List Id</b></td>
								<td align="center" class='inner col3 head1'><b>Account Mgr</b></td>	
								<td align="center" class='inner col3 head1'><b>Location From</b></td>		
								<td align="center" class='inner col3 head1'><b>Location To</b></td>
								<td align="center" class='inner col2 head1'><b>Line Item Number</b></td>
								<td align="center" class='inner col2 head1'><b>Token No</b></td>
							  	<td align="center" class='inner col2 head1'><b>Region Name</b></td>  
								<td align="center" class='inner col2 head1'><b>Si Id</b></td>
								<td align="center" class='inner col2 head1'><b>M6 Order Id</b></td>
								<td align="center" class='inner col2 head1'><b>Annotation</b></td>
								<td align="center" class='inner col1 head1'><b>Demo Flag</b></td>	
								<td align="center" class='inner col2 head1'><b>Business Serial No</b></td>	
								<td align="center" class='inner col2 head1'><b>Billing Bandwidth</b></td>	
								<td align="center" class='inner col2 head1'><b>Uom</b></td>
								<td align="center" class='inner col2 head1'><b>Mocn Number</b></td>
                                <td align="center" class='inner col2 head1'><b>Old LSI</b></td>
					
					
				</tr>
						</table>
					  </div>
					  <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	        			<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>	
				<logic:present name="zeroOrderValueReportList" scope="request">
					<logic:notEmpty  name="zeroOrderValueReportList" scope="request"> 					
						<logic:iterate id="row" name="zeroOrderValueReportList" indexId="recordId">
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
											<td align="left" class='inner col3'><bean:write  name="row" property="partyName"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="orderNumber"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="poDetailNo"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="accountID"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="entityId"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="custPoDetailNo"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="custPoDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="poAmounts"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="paymentTerm"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="contractStartDate"/>&nbsp;</td>
			    							<td align="left" class='inner col2'><bean:write  name="row" property="contractEndDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="poRecieveDate"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="poIssueBy"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write name="row" property="poEmailId"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="lastUpdatedDate"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write name="row" property="lastUpdatedBy"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="createdDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="createdBy"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="crmACcountNO"/>&nbsp;</td>
											<td align="left" class='inner col1'><bean:write  name="row" property="isDefaultPO"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="poEndDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="partyNo"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="sourceName"/>&nbsp;</td>
											<td align="left" class='inner col2'>&nbsp;</td>
											<td align="left" class='inner col2'>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="poDemoContractPeriod"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="logicalSINo"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="serviceId"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="accountManager"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="fromLocation"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="toLocation"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="lineItemDescription"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="tokenNO"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="regionName"/>&nbsp;</td> 
											<td align="left" class='inner col2'><bean:write  name="row" property="fxSiId"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="m6OrderNo"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="chargeAnnotation"/>&nbsp;</td>
											<td align="left" class='inner col1'><bean:write  name="row" property="order_type"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="business_serial_n"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="billingBandWidth"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="uom"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="mocn_no"/>&nbsp;</td>	
											<td align="left" class='inner col2'><bean:write  name="row" property="oldLsi"/>&nbsp;</td>
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
		              	<div class='horizontal-scrollzerovalueorder' onscroll='reposHorizontal(this);'>
		                  	<div>
		                  	</div>
		              	</div>
		          	</td>
		     	</tr>
		      </table>
		     <!-- End Scrolling and Freezing of ZERO VALUE ORDER REPORT   -->
					<logic:empty  name="zeroOrderValueReportList">
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

