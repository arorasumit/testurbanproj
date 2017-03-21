<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<!-- [001]  14/01/2016 Varun Gupta add  -->

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
<title>Archival Permanent Disconnction Report</title>
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
<script language="javascript" src="<%=request.getContextPath()%>/js/openViewAttachment.js"></script> 
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
var gb_path="<%=request.getContextPath()%>";             
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
	myform.method.value='pdReportOrder';
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
	myform.method.value='pdReportOrder';
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
	var jsData ={ userId:userId,
		interfaceId:interfaceId,
		actionType:searchName
	     };
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	jsonrpc.processes.populateReportUsageDetails(jsData);

	if(!isBlankForm()){
		return;
	}
	else {
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=pdReportOrder";
		showLayerExcel();
		myForm.submit();
	}
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm')
	var fromDate=myForm.FROMDATE.value;
	var toDate=myForm.TODATE.value;
	if((fromDate == null & toDate == null) || ( trim(fromDate)=="" & trim(toDate) == ""))
	{
		alert("Please enter From Order Date and To Order Date!");
		return false;
	}
	else
	{
		if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
		{
			alert("Please enter From Order Date!");
			return false;
		}
		else if((toDate == null || trim(toDate)== "" ) && (fromDate != null && trim(fromDate) != "" ))
		{
			alert("Please enter To Order Date!");
			return false;
		}
		if(dateCompare(fromDate,toDate)==1)
		{
			alert("From Order Date cannot be greater than To Order Date!");
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

	if(!isBlankForm()){
		return;
	}
	else {
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=pdReportOrder&inExcel=true";
		showLayerExcel();
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.LOGICALSINO.value='' ;
	myForm.LINE_IT_NO.value='' ;
	myForm.m6orderno.value='' ;
	myForm.ACCOUNT_ID.value='' ;
	myForm.ORDER_NO.value='' ;
	myForm.FROMDATE.value='' ;
	myForm.TODATE.value='' ;
	myForm.ckt_id.value='' ;

}

function downloadDump(formId,fileName)
{
	myForm=document.getElementById(formId);
	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=getDumpFile&fileName="+fileName;
	//showLayer();
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
<body onload="javascript:maxLimitChecker(document.getElementById('searchForm'),'<%=ApplicationFlags.ARCHIVAL_REPORT_LIMIT%>','<%=Messages.getMessageValue("ReportsExcelMaxSizeMessage") %>');">
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
				<tr>
					<td align="left"> <span>Archival Permanent Disconnection Report</span>
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
			<legend><b>search</b></legend>
			<table border="0"  align="center" style="margin-top:7px" width="100%">
				<tr>
					<td align="right" style="font-size:9px;" width="122">From Order Date:</td>
				 <td nowrap><html:text  property="FROMDATE" styleId="dateFrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
					    <font size="1">
							<a href="javascript:show_calendar(searchForm.FROMDATE);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
						</font>
					</td>
					<td align="right" style="font-size:9px;" width="122">To Order Date:</td>
					<td nowrap width="151"><html:text  property="TODATE" styleId="dateTo" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
					    <font size="1">
							<a href="javascript:show_calendar(searchForm.TODATE);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
						</font>
					</td>
					<td align="right" style="font-size:9px">Order No :</td>
					<td align="left" nowrap width="139">
						<html:text  property="ORDER_NO" maxlength="25" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'ORDER_NO')};"></html:text>
					</td>
					 <td align="right" style="font-size:9px">Circuit Id</td>
					<td align="left" nowrap width="139">
						<html:text  property="CIRCUIT_ID" maxlength="25" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'ckt_id')};"></html:text>
					</td>
					</td>
				</tr>
				<tr>
					<td align="right" style="font-size:9px">LSI No</td>
					<td align="left" nowrap width="139">
						<html:text  property="LOGICALSINO" maxlength="25" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'LOGICALSINO')};"></html:text>
					</td>
					 <td align="right" style="font-size:9px" width="122">Order Line Id</td>
					<td align="left" nowrap width="151">
						<html:text  property="ORDER_LINE_ID" maxlength="25" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'LINE_IT_NO')};"></html:text>
					</td>
					<td align="right" style="font-size:9px" width="86">M6 Order Id</td>
					<td align="left" nowrap width="106">
						<html:text  property="m6orderno" maxlength="25" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'M6ORDERNO')};"></html:text>
					</td>

					<td align="right" style="font-size:9px" width="86">Account No</td>
					<td align="left" nowrap width="106">
						<html:text  property="ACCOUNT_ID" maxlength="25" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'ACCOUNT_ID')};"></html:text>
					</td>


					<td align="left" width="111">

					</td>

					<td align="center" colspan="2">
						 <p> <%-- <img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/> --%>
						<a href="#"><img onclick="searchClick()" src="npd/Images/search.gif" title="search" height="15"></a>
				        <img title="Export To Excel" src="./gifs/top/excel1.gif" onClick="javascript:exportToExcel();"> </img>
			            <img src="gifs/top/home.gif" onClick="goToHome('Home');"></p>
						</img></td>
					<td width="0"></td>
				</tr>
			</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Archival Permanent Disconnction Report</b></legend>
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
									<td align="center" class='inner col3 head1' ><b>Order No</b></td>
						<td align="center" class='inner col3 head1' ><b>Account Mgr</b></td>
<td align="center" class='inner col3 head1' ><b>Account No</b></td>
<td align="center" class='inner col3 head1' ><b>Amount</b></td>
<td align="center" class='inner col3 head1' ><b>Annotation</b></td>
<td align="center" class='inner col3 head1' ><b>Annual Rate</b></td>
<td align="center" class='inner col3 head1' ><b>Bandwidth</b></td>
<td align="center" class='inner col3 head1' ><b>Bandwidth uom</b></td>
<td align="center" class='inner col3 head1' ><b>Bill Format</b></td>
<td align="center" class='inner col3 head1' ><b>Bill Period</b></td>
<td align="center" class='inner col3 head1' ><b>Bill TRG Create Date</b></td>
<td align="center" class='inner col3 head1' ><b>Bill Type</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Bandwidth</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Bandwidth uom</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Level</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Level number</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Location from</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Location to</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Mode</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Trig flag</b></td>
<td align="center" class='inner col3 head1' ><b>Challen Date</b></td>
<td align="center" class='inner col3 head1' ><b>Charge End Date</b></td>
<td align="center" class='inner col3 head1' ><b>Charge HDR ID</b></td>
<td align="center" class='inner col3 head1' ><b>Charge Name</b></td>
<td align="center" class='inner col3 head1' ><b>Charge Start Date</b></td>
<td align="center" class='inner col3 head1' ><b>Charge Status</b></td>
<td align="center" class='inner col3 head1' ><b>Charge Type</b></td>
<td align="center" class='inner col3 head1' ><b>Charge Type ID</b></td>
<td align="center" class='inner col3 head1' ><b>Chargeable Distance</b></td>
<td align="center" class='inner col3 head1' ><b>Child Acc FX Status</b></td>
<td align="center" class='inner col3 head1' ><b>Child Acc No</b></td>
<td align="center" class='inner col3 head1' ><b>Circuit ID</b></td>
<td align="center" class='inner col3 head1' ><b>Commitment Period</b></td>
<td align="center" class='inner col3 head1' ><b>Contract Period Months</b></td>
<td align="center" class='inner col3 head1' ><b>COPC Approval Date</b></td>
<td align="center" class='inner col3 head1' ><b>Credit Period</b></td>
<td align="center" class='inner col3 head1' ><b>Currency</b></td>
<td align="center" class='inner col3 head1' ><b>Cust Acc ID</b></td>
<td align="center" class='inner col3 head1' ><b>Cust Logical SI No</b></td>
<td align="center" class='inner col3 head1' ><b>Cust PO Date</b></td>
<td align="center" class='inner col3 head1' ><b>Cust PO Number</b></td>
<td align="center" class='inner col3 head1' ><b>Cust PO Receive Date</b></td>
<td align="center" class='inner col3 head1' ><b>Customer Segment</b></td>
<td align="center" class='inner col3 head1' ><b>customer Service RFS Date</b></td>
<td align="center" class='inner col3 head1' ><b>Demo Type</b></td>
<td align="center" class='inner col3 head1' ><b>Disconnection Remark</b></td>
<td align="center" class='inner col3 head1' ><b>End Date Days</b></td>
<td align="center" class='inner col3 head1' ><b>End Date Logic</b></td>
<td align="center" class='inner col3 head1' ><b>End Date Months</b></td>
<td align="center" class='inner col3 head1' ><b>Form C Available</b></td>
<td align="center" class='inner col3 head1' ><b>Frequency</b></td>
<td align="center" class='inner col3 head1' ><b>Hardware Flag</b></td>
<td align="center" class='inner col3 head1' ><b>Hardware Type</b></td>
<td align="center" class='inner col3 head1' ><b>Indicative Value</b></td>
<td align="center" class='inner col3 head1' ><b>Invoice Amt</b></td>
<td align="center" class='inner col3 head1' ><b>Last Mile Media</b></td>
<td align="center" class='inner col3 head1' ><b>Last Mile Provider</b></td>
<td align="center" class='inner col3 head1' ><b>Last Mile Remarks</b></td>
<td align="center" class='inner col3 head1' ><b>Legal Entity</b></td>
<td align="center" class='inner col3 head1' ><b>Licence Company</b></td>
<td align="center" class='inner col3 head1' ><b>Link Type</b></td>
<td align="center" class='inner col3 head1' ><b>Loc Date</b></td>
<td align="center" class='inner col3 head1' ><b>Loc Number</b></td>
<td align="center" class='inner col3 head1' ><b>Logical Circuit ID</b></td>
<td align="center" class='inner col3 head1' ><b>M6 Order ID</b></td>
<td align="center" class='inner col3 head1' ><b>Nature of Sale</b></td>
<td align="center" class='inner col3 head1' ><b>New Order Remarks</b></td>
<td align="center" class='inner col3 head1' ><b>Notice Period</b></td>
<td align="center" class='inner col3 head1' ><b>Order Creation Date</b></td>
<td align="center" class='inner col3 head1' ><b>Order Date</b></td>
<td align="center" class='inner col3 head1' ><b>Order Line ID</b></td>
<td align="center" class='inner col3 head1' ><b>Order Month</b></td>
<td align="center" class='inner col3 head1' ><b>Order Stage</b></td>
<td align="center" class='inner col3 head1' ><b>Order Type</b></td>
<td align="center" class='inner col3 head1' ><b>Party</b></td>
<td align="center" class='inner col3 head1' ><b>party ID</b></td>
<td align="center" class='inner col3 head1' ><b>Penalty Clause</b></td>
<td align="center" class='inner col3 head1' ><b>Period in Month</b></td>
<td align="center" class='inner col3 head1' ><b>PK Chageges ID</b></td>
<td align="center" class='inner col3 head1' ><b>PM Prov Date</b></td>
<td align="center" class='inner col3 head1' ><b>PO Date</b></td>
<td align="center" class='inner col3 head1' ><b>Pre CRM Order ID</b></td>
<td align="center" class='inner col3 head1' ><b>Product</b></td>
<td align="center" class='inner col3 head1' ><b>Product Name</b></td>
<td align="center" class='inner col3 head1' ><b>Region</b></td>
<td align="center" class='inner col3 head1' ><b>Request Received Date</b></td>
<td align="center" class='inner col3 head1' ><b>Sec Loc</b></td>
<td align="center" class='inner col3 head1' ><b>Service No</b></td>
<td align="center" class='inner col3 head1' ><b>Service Order Type</b></td>
<td align="center" class='inner col3 head1' ><b>Service Stage</b></td>
<td align="center" class='inner col3 head1' ><b>SR Number</b></td>
<td align="center" class='inner col3 head1' ><b>Standard Reason</b></td>
<td align="center" class='inner col3 head1' ><b>Start Date Days</b></td>
<td align="center" class='inner col3 head1' ><b>Start Date Logic</b></td>
<td align="center" class='inner col3 head1' ><b>Start Date Months</b></td>
<td align="center" class='inner col3 head1' ><b>Store</b></td>
<td align="center" class='inner col3 head1' ><b>Sub Product</b></td>
<td align="center" class='inner col3 head1' ><b>Taxation</b></td>
<td align="center" class='inner col3 head1' ><b>Token No</b></td>
<td align="center" class='inner col3 head1' ><b>TOT PO Amt</b></td>
<td align="center" class='inner col3 head1' ><b>Total Amount</b></td>
<td align="center" class='inner col3 head1' ><b>Type of Sale</b></td>
<td align="center" class='inner col3 head1' ><b>Vertical</b></td>
<td align="center" class='inner col3 head1' ><b>Project Mgr</b></td>
<td align="center" class='inner col3 head1' ><b>Project Mgr Email</b></td>
<td align="center" class='inner col3 head1' ><b>Provision Bandwidth</b></td>
<td align="center" class='inner col3 head1' ><b>Quote No</b></td>
<td align="center" class='inner col3 head1' ><b>Ratio</b></td>
<td align="center" class='inner col3 head1' ><b>Region Name</b></td>
<td align="center" class='inner col3 head1' ><b>Re Logged LSI No</b></td>
<td align="center" class='inner col3 head1' ><b>Service Name</b></td>
<td align="center" class='inner col3 head1' ><b>Service Number</b></td>
<td align="center" class='inner col3 head1' ><b>Sub Change Type</b></td>
<td align="center" class='inner col3 head1' ><b>Sub Product Type</b></td>
<td align="center" class='inner col3 head1' ><b>Taxexemption Reason</b></td>
<td align="center" class='inner col3 head1' ><b>To City</b></td>
<td align="center" class='inner col3 head1' ><b>To Site</b></td>
<td align="center" class='inner col3 head1' ><b>UOM</b></td>
<td align="center" class='inner col3 head1' ><b>Zone</b></td>
<td align="center" class='inner col3 head1' ><b>Dis Sr</b></td>
<td align="center" class='inner col3 head1' ><b>Date of Disconnection</b></td>
<!-- <td align="center" class='inner col3 head1' ><b>Order No</b></td> -->
<td align="center" class='inner col3 head1' ><b>Logical SI No</b></td>
<td align="center" class='inner col3 head1' ><b>Line IT No</b></td>
<td align="center" class='inner col3 head1' ><b>Ckt ID</b></td>
<td align="center" class='inner col3 head1' ><b>Package ID</b></td>
<td align="center" class='inner col3 head1' ><b>Package Name</b></td>
<td align="center" class='inner col3 head1' ><b>Component Info ID</b></td>
<td align="center" class='inner col3 head1' ><b>Component ID</b></td>
<td align="center" class='inner col3 head1' ><b>Component Name</b></td>
<td align="center" class='inner col3 head1' ><b>Component Status</b></td>
<td align="center" class='inner col3 head1' ><b>Component Start Logic</b></td>
<td align="center" class='inner col3 head1' ><b>Component Start Date</b></td>
<td align="center" class='inner col3 head1' ><b>Component End Logic</b></td>
<td align="center" class='inner col3 head1' ><b>Component End Date</b></td>
<td align="center" class='inner col3 head1' ><b>Comp Start Days</b></td>
<td align="center" class='inner col3 head1' ><b>Comp Start Months</b></td>
<td align="center" class='inner col3 head1' ><b>Comp End Days</b></td>
<td align="center" class='inner col3 head1' ><b>Comp End Months</b></td>
<td align="center" class='inner col3 head1' ><b>Component Type</b></td>
<td align="center" class='inner col3 head1' ><b>Component Instance ID</b></td>
<td align="center" class='inner col3 head1' ><b>Start Component Token No</b></td>
<td align="center" class='inner col3 head1' ><b>End Component Token No</b></td>
<td align="center" class='inner col3 head1' ><b>Rate Code</b></td>
<td align="center" class='inner col3 head1' ><b>Pri Loc</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Trig Date</b></td>
<td align="center" class='inner col3 head1' ><b>Challen No</b></td>
<td align="center" class='inner col3 head1' ><b>Dispatch Address</b></td>
<td align="center" class='inner col3 head1' ><b>Line Name</b></td>
<td align="center" class='inner col3 head1' ><b>PO Number</b></td>
						

								</tr>
							</table>
						</div>
						<div id='contentscroll' class='content1' onscroll='reposHead(this);'>
							<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tableAdvancePaymentReport'>
								<logic:present name="pdReportOrder" scope="request">
									<logic:notEmpty  name="pdReportOrder" scope="request">
										<logic:iterate id="row" name="pdReportOrder" indexId="recordId">
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
											
											
											<td align="center" class='inner col3'><a href="#" onclick="getAttachmentList(<bean:write  name="row" property="cust_acc_id"/>,<bean:write  name="row" property="order_no"/>,<bean:write name="row" property="account_no"/>);">
												<bean:write  name="row" property="order_no"/>
												</a></td>
											
<td align="center" class='inner col3'><bean:write  name="row" property="account_mgr"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="account_no"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="amt"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="annotation"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="annual_rate"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="bandwidth"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="bandwidth_uom"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="bill_format"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="bill_period"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="bill_trg_Create_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="bill_type"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="billing_bandwidth"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="billing_bandwidth_uom"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="billing_level"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="billing_level_number"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="billing_location_from"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="billing_location_to"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="billing_mode"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="billing_trig_flag"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="challen_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="charge_end_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="charge_hdr_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="charge_name"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="charge_start_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="charge_status"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="charge_type"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="charge_type_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="chargeable_distance"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="child_acc_fx_status"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="child_acc_no"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="circuit_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="commitment_period"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="contract_period_months"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="copc_approval_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="credit_period"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="currency"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="cust_acc_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="cust_logical_si_no"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="cust_po_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="cust_po_number"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="cust_po_receive_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="customer_segment"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="customer_service_rfs_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="demo_type"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="disconnection_remark"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="end_date_days"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="end_date_logic"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="end_date_months"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="form_c_available"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="frequency"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="hardware_flag"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="hardware_type"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="indicative_value"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="inv_amt"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="last_mile_media"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="last_mile_provider"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="last_mile_remarks"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="legal_entity"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="licence_company"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="link_type"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="loc_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="loc_number"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="logical_circuit_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="m6_order_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="nature_of_sale"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="new_order_remarks"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="notice_period"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="order_creation_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="order_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="order_line_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="order_month"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="order_stage"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="order_type"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="party"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="party_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="penalty_clause"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="period_in_month"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="pk_chageges_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="pm_prov_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="po_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="pre_crm_order_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="product"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="product_name"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="region"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="request_received_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="sec_loc"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="service_no"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="service_order_type"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="service_stage"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="sr_number"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="standard_reason"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="start_date_days"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="start_date_logic"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="start_date_months"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="store"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="sub_product"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="taxation"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="token_no"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="tot_po_amt"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="total_amount"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="type_of_sale"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="vertical"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="project_mgr"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="project_mgr_email"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="provision_bandwidth"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="quote_no"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="ratio"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="region_name"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="re_logged_lsi_no"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="service_name"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="service_number"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="sub_change_type"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="sub_product_type"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="taxexemption_reason"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="to_city"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="to_site"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="uom"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="zone"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="dis_sr"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="dod"/>&nbsp;</td>
<%-- <td align="center" class='inner col3'><bean:write  name="row" property="order_no"/>&nbsp;</td> --%>
<td align="center" class='inner col3'><bean:write  name="row" property="LOGICAL_SI_NO"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="line_it_no"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="ckt_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="package_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="package_name"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="componentinfoid"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="component_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="component_name"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="component_status"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="component_start_logic"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="component_start_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="component_end_logic"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="component_end_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="comp_start_days"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="comp_start_months"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="comp_end_days"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="comp_end_months"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="component_type"/>&nbsp;</td>
<td align="center" class='inner col6'><bean:write  name="row" property="component_instance_id"/>&nbsp;</td>
<td align="center" class='inner col6'><bean:write  name="row" property="start_component_token_no"/>&nbsp;</td>
<td align="center" class='inner col6'><bean:write  name="row" property="end_component_token_no"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="rate_code"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="pri_loc"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="billing_trig_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="CHALLEN_NO"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="dispatch_address"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="LINE_NAME"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="po_number"/>&nbsp;</td>


				</tr>
										</logic:iterate>
									</logic:notEmpty>
									<logic:empty  name="pdReportOrder">
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
					<td class='tableverticalscroll' rowspan="20">
						<div class='vertical-scroll' onscroll='reposVertical(this);'>
	                        <div></div>
	                    </div>
	                    <div class='ff-fill'>
	                    </div>
					</td>
				</tr>
				<tr>
	               	<td colspan="3">
	                   	<div class='horizontal-scrollArchivalReports' onscroll='reposHorizontal(this);'>
	                       	<div></div>
	                   	</div>
	               	</td>
				</tr>
			</table>
		</fieldset>
	</html:form>
</body>
</html>