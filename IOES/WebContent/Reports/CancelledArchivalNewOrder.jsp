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
<title>Archival Cancelled Report</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>


<script language="javascript" src="<%=request.getContextPath()%>/js/openViewAttachment.js"></script>

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
	myform.method.value='cancelledReportOrder';
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
	myform.method.value='cancelledReportOrder';
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
	var jsData ={
	
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=cancelledReportOrder";
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=cancelledReportOrder&inExcel=true";
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
				<tr >
					<td align="left"> <span>Archival Cancelled Report</span>
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
					<%-- <td align="right" style="font-size:9px">CKT Id</td>
					<td align="left" nowrap width="139">
						<html:text  property="ckt_id" maxlength="25" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'ckt_id')};"></html:text>
					</td> --%>

					</td>
				</tr>
				<tr>
					<td align="right" style="font-size:9px">LSI No</td>
					<td align="left" nowrap width="139">
						<html:text  property="LOGICALSINO" maxlength="25" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'LOGICALSINO')};"></html:text>
					</td>
					<%-- <td align="right" style="font-size:9px" width="122">LineItem No</td>
					<td align="left" nowrap width="151">
						<html:text  property="LINE_IT_NO" maxlength="25" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'LINE_IT_NO')};"></html:text>
					</td>
					 --%><td align="right" style="font-size:9px" width="86">M6 Order No</td>
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
			<legend> <b>Archival Cancelled Report</b></legend>
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
<td align="center" class='inner col3 head1' ><b>Account Manager</b></td>
<td align="center" class='inner col3 head1' ><b>Account Number</b></td>
<td align="center" class='inner col3 head1' ><b>PO Amount</b></td>
<td align="center" class='inner col3 head1' ><b>Customer Segment</b></td>
<td align="center" class='inner col3 head1' ><b>Account Category</b></td>
<td align="center" class='inner col3 head1' ><b>Vertical</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Charge Start Date</b></td>
<td align="center" class='inner col3 head1' ><b>Service Name</b></td>
<td align="center" class='inner col3 head1' ><b>Order Line No</b></td>
<td align="center" class='inner col3 head1' ><b>Line Name </b></td>
<td align="center" class='inner col3 head1' ><b>Cancel Flag</b></td>
<td align="center" class='inner col3 head1' ><b>Provision Bandwidth</b></td>
<td align="center" class='inner col3 head1' ><b>UOM</b></td>
<td align="center" class='inner col3 head1' ><b>Billing Bandwidth</b></td>
<td align="center" class='inner col3 head1' ><b>Store Address</b></td>
<td align="center" class='inner col3 head1' ><b>Bill UOM</b></td>
<td align="center" class='inner col3 head1' ><b>Category of Order</b></td>
<td align="center" class='inner col3 head1' ><b>Contract Period</b></td>
<td align="center" class='inner col3 head1' ><b>Company Name</b></td>
<td align="center" class='inner col3 head1' ><b>Customer RFS Date</b></td>
<td align="center" class='inner col3 head1' ><b>Customer Service RFS Date</b></td>
<td align="center" class='inner col3 head1' ><b>Currency</b></td>
<td align="center" class='inner col3 head1' ><b>Charge Name</b></td>
<td align="center" class='inner col3 head1' ><b>Customer PO Date</b></td>
<td align="center" class='inner col3 head1' ><b>Customer PO Number</b></td>
<td align="center" class='inner col3 head1' ><b>Cyclic or Non Cyclic</b></td>
<td align="center" class='inner col3 head1' ><b>Challen No</b></td>
<td align="center" class='inner col3 head1' ><b>Account Id</b></td>
<td align="center" class='inner col3 head1' ><b>From Site</b></td>
<td align="center" class='inner col3 head1' ><b>To Site</b></td>
<td align="center" class='inner col3 head1' ><b>Item Quantity</b></td>
<td align="center" class='inner col3 head1' ><b>KMS Distance</b></td>
<td align="center" class='inner col3 head1' ><b>Line Item Amount</b></td>
<td align="center" class='inner col3 head1' ><b>COPC Approved Date</b></td>
<td align="center" class='inner col3 head1' ><b>Iine Item Description</b></td>
<td align="center" class='inner col3 head1' ><b>Loc Date</b></td>
<td align="center" class='inner col3 head1' ><b>Account Manager Receive Date</b></td>
<td align="center" class='inner col3 head1' ><b>Order Total</b></td>
<td align="center" class='inner col3 head1' ><b>Order Entry Date</b></td>
<td align="center" class='inner col3 head1' ><b>Taxation</b></td>
<td align="center" class='inner col3 head1' ><b>Taxexemption Reason</b></td>
<td align="center" class='inner col3 head1' ><b>Licence Company</b></td>
<td align="center" class='inner col3 head1' ><b>Logical Circuit ID</b></td>
<td align="center" class='inner col3 head1' ><b>Order Type</b></td>
<td align="center" class='inner col3 head1' ><b>Payment Term</b></td>
<td align="center" class='inner col3 head1' ><b>Project MGR</b></td>
<td align="center" class='inner col3 head1' ><b>Region Name</b></td>
<td align="center" class='inner col3 head1' ><b>Old Line Item Amount</b></td>
<td align="center" class='inner col3 head1' ><b>Demo Type</b></td>
<td align="center" class='inner col3 head1' ><b>Party Name</b></td>
<td align="center" class='inner col3 head1' ><b>Order Stage Description</b></td>
<td align="center" class='inner col3 head1' ><b>Service Stage Description</b></td>
<td align="center" class='inner col3 head1' ><b>Charge End Date</b></td>
<td align="center" class='inner col3 head1' ><b>End Date Logic</b></td>
<td align="center" class='inner col3 head1' ><b>New Order Remark</b></td>
<td align="center" class='inner col3 head1' ><b>Remarks</b></td>
<td align="center" class='inner col3 head1' ><b>Service Order Type</b></td>
<td align="center" class='inner col3 head1' ><b>OSP</b></td>
<td align="center" class='inner col3 head1' ><b>Opportunity ID</b></td>
<td align="center" class='inner col3 head1' ><b>Logical SI No</b></td>
<td align="center" class='inner col3 head1' ><b>Line IT No</b></td>
<td align="center" class='inner col3 head1' ><b>Order Creation Date</b></td>
<td align="center" class='inner col3 head1' ><b>CKT ID</b></td>
<td align="center" class='inner col3 head1' ><b>M6 Order No</b></td>
<td align="center" class='inner col3 head1' ><b>Service ID</b></td>
<td align="center" class='inner col3 head1' ><b>Old LSI</b></td>
<td align="center" class='inner col3 head1' ><b>Cancel Reason</b></td>
<td align="center" class='inner col3 head1' ><b>Cancel Date</b></td>
<td align="center" class='inner col3 head1' ><b>Product</b></td>
<td align="center" class='inner col3 head1' ><b>Sub Product</b></td>
<td align="center" class='inner col3 head1' ><b>Effective Start Data</b></td>
								



									
									</tr>

							</table>
						</div>
						<div id='contentscroll' class='content1' onscroll='reposHead(this);'>
							<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tableAdvancePaymentReport'>
								<logic:present name="cancelledReportOrder" scope="request">
									<logic:notEmpty  name="cancelledReportOrder" scope="request">
										<logic:iterate id="row" name="cancelledReportOrder" indexId="recordId">
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
											
											
											
<td align="center" class='inner col3'><a href="#" onclick="getAttachmentList(<bean:write  name="row" property="account_id"/>,<bean:write  name="row" property="order_no"/>,<bean:write name="row" property="account_number"/>);">
												<bean:write  name="row" property="order_no"/>
												</a></td>
<td align="center" class='inner col3'><bean:write  name="row" property="account_manager"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="account_number"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="po_amount"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="customer_segment"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="account_category"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="vertical"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="billing_charge_start_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="service_name"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="order_line_no"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="line_name"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="cancel_flag"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="provision_bandwidth"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="uom"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="billing_bandwidth"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="store_address"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="bill_uom"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="category_of_order"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="contract_period"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="company_name"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="customer_rfs_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="customer_service_rfs_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="currency"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="charge_name"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="customer_po_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="customer_po_number"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="cyclic_or_non_cyclic"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="challen_no"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="account_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="from_site"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="to_site"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="item_quantity"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="kms_distance"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="line_item_amount"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="copc_approved_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="line_item_description"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="loc_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="account_manager_receive_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="order_total"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="order_entry_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="taxation"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="taxexemption_reason"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="licence_company"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="logical_circuit_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="order_type"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="payment_term"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="project_mgr"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="region_name"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="old_line_item_amount"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="demo_type"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="party_name"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="order_stage_description"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="service_stage_description"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="charge_end_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="end_date_logic"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="new_order_remark"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="remarks"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="service_order_type"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="osp"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="opportunity_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="logical_si_no"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="line_it_no"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="order_creation_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="ckt_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="m6orderno"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="service_id"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="oldLsi"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="cancel_reason"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="cancel_date"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="product"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="sub_product"/>&nbsp;</td>
<td align="center" class='inner col3'><bean:write  name="row" property="effective_start_data"/>&nbsp;</td>
</tr>
										</logic:iterate>
									</logic:notEmpty>
									<logic:empty  name="cancelledReportOrder">
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