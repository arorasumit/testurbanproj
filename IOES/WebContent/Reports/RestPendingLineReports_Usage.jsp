<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- [TRNG21052013004]    Vijay        26 June   - create freez header -->
<!-- [001] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
<!-- [BFR5] Nagarjuna        GlobalDataBillingEfficiency Project to add TaxExemptionReason -->
<!-- [002] Neha Maheshwari   14-Apr-15     CSR-20150212-R1-021088  Addition of Column in Active and Pending Line Item Reports -->
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
<title>Rest Pending Line Report</title>
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
var isUsage =<%= request.getParameter("usage") %>;

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
	//myform.method.value='viewRestPendingLineReport&usage=1';	
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewRestPendingLineReport&usage="+isUsage;
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
	myform.method.value='viewRestPendingLineReport&usage=1';	
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
//		var path = "<%=request.getContextPath()%>/reportsAction_Usage.do?method=fetchStatus";		
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
		
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewRestPendingLineReport&usage="+isUsage;
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
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewRestPendingLineReport&inExcel=true&usage="+isUsage;
		showLayerExcel();
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	
	myForm.fromDate.value='' ;	
	myForm.toDate.value='' ;	
	myForm.subtype.value='' ;
	myForm.servicename.value='' ;
	myForm.verticalDetails.value='' ;
	
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
	
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	
	if((fromDate == null & toDate == null) || ( trim(fromDate)=="" & trim(toDate) == "")){
		alert("Please enter  From Date and To Date!");
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
	return true;	
	
	
}

//function fnViewOrder(orderNo)
//{
//      document.forms[0].action="./viewOrderAction.do?methodName=viewOrder&orderNo="+orderNo;
//      document.forms[0].submit();
//}

path='<%=request.getContextPath()%>';
--></script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload="javascript:maxLimitChecker(document.getElementById('searchForm'),'<%=ApplicationFlags.ReportsExcelMaxSize%>','<%=Messages.getMessageValue("ReportsExcelMaxSizeMessage") %>');">
 

<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/reportsAction_Usage" styleId="searchForm" method="post">
		<bean:define id="formBean" name="reportsBean"></bean:define>
		<html:hidden property="toExcel"/>
		<html:hidden property="reportProcessForLimit"/>
			<html:hidden property="interfaceId"/>
		<html:hidden property="isDumpAvailable"/>
		<html:hidden property="dumpFileName"/>	
<html:hidden property="reportCurrentCount"/>
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
		<logic:equal name="isUsage" value="0">
		<div border="1" class="head"> <span>Rest Pending Line Report</span> </div>
		</logic:equal>
		<logic:equal name="isUsage" value="1">
			<div border="1" class="head"> 
			
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>Rest Pending Line Report(USAGE)</span> 
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
					
						<td width="10px"/>
						<td align="right" style="font-size:9px;">From Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="datefrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">To  Date:</td>
						<td nowrap><html:text  property="toDate" styleId="todate" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
							
								<td width="10px"/>
						<td align="right" style="font-size:9px">Service Order Type</td>
						<td align="left">
							<html:text  property="subtype" maxlength="18" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Service Order Type')};" ></html:text>
						</td>
						
						</tr>
						<tr>
						
						<td width="10px"/>
						<td align="right" style="font-size:9px">Service Name</td>
						<td align="left">
							<html:text  property="servicename" maxlength="18" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Service Name')};" ></html:text>
						</td>
						
						<td width="10px"/>
						<td align="right" style="font-size:9px">Vertical</td>
						<td align="left">
							<html:text  property="verticalDetails" maxlength="18" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Vertical')};" ></html:text>
						</td>												
						<td align="left" >
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td width="1%"/>
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>																			
						<td align="left" width="20%">
							<div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div>
						</td>
						<td width="60px"/>																			
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Rest Pending Line Report</b></legend>
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
			
			<!-- Start Scrolling and Freezing of Rest Pending Line Report(USAGE)    -->
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
					<td align="center" style="font-size:9px"  ><b>Account No </b></td>
					<td align="center" style="font-size:9px"  ><b>Party Name </b></td>
					<td align="center" style="font-size:9px"  ><b>Party No </b></td>
					<td align="center" style="font-size:9px"  ><b>Prj Manager </b></td>
					<td align="center" style="font-size:9px"  ><b>Account Manager  </b></td>
					<td align="center" style="font-size:9px"  ><b>Order Date  </b></td>
					<td align="center" style="font-size:9px"  ><b>Dispatch Address  </b></td>
					<td align="center" style="font-size:9px"  ><b>Charge Start Date  </b></td>
					<td align="center" style="font-size:9px"><b>Po Date</b></td>
					<td align="center" style="font-size:9px"  ><b>Approve Date  </b></td>
					<td align="center" style="font-size:9px"  ><b> COPC Approve Date  </b></td>
					<td align="center" style="font-size:9px"  ><b>Order Date  </b></td>
					<td align="center" style="font-size:9px"  ><b>Cust Po Rec Date  </b></td>
					<td align="center" style="font-size:9px"  ><b>Cust PO Date  </b></td>
				    <td align="center" style="font-size:9px"  ><b>Cancel Date </b></td>
				    <td align="center" style="font-size:9px"  ><b>Rate Code </b></td>
				    <td align="center" style="font-size:9px"  ><b>Last Mile Media </b></td>
				    <td align="center" style="font-size:9px"  ><b>Last Mile Remarks </b></td>
				    <td align="center" style="font-size:9px"  ><b>Link Type </b></td>
				    <td align="center" style="font-size:9px"  ><b>Indicative Value </b></td>
				     <td align="center" style="font-size:9px"  ><b>Bandwidth UOM  </b></td>
				     <td align="center" style="font-size:9px"  ><b>Last Mile Provider  </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Location</b></td>
					<td align="center" style="font-size:9px"  ><b>Hardware Flag  </b></td>
					<td align="center" style="font-size:9px"  ><b>Charge Type  </b></td>
					<td align="center" style="font-size:9px"  ><b>Charge Status  </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Mode </b></td>
					<td align="center" style="font-size:9px"  ><b>Form C Available  </b></td>
					<td align="center" style="font-size:9px"  ><b>Charge Name </b></td>
					<td align="center" style="font-size:9px"  ><b>PoNumber </b></td>
					<td align="center" style="font-size:9px"  ><b>CustPoNumber  </b></td>
					<td align="center" style="font-size:9px"  ><b>Cancel By  </b></td>
					<td align="center" style="font-size:9px"  ><b>Primary Location  </b></td>
					<td align="center" style="font-size:9px"  ><b>Secondary Location  </b></td>
					<td align="center" style="font-size:9px"  ><b>Address  </b></td>
					<td align="center" style="font-size:9px"  ><b>Fx Status  </b></td>
					<td align="center" style="font-size:9px"  ><b>Fx Sd Charge Status  </b></td>
					<td align="center" style="font-size:9px"  ><b>Fx Ed Charge Status </b></td>
					<td align="center" style="font-size:9px"><b>Child A/C Fx Status</b></td>
					<td align="center" style="font-size:9px"><b>RFS Date</b></td>
					<td align="center" style="font-size:9px"><b>Opms Act Id</b></td>				
					<td align="center" style="font-size:9px"><b>Product Name</b></td>
					<td align="center" style="font-size:9px"><b>Sub Product Name</b></td>	
					<td align="center" style="font-size:9px"><b>LineItemNo</b></td>	
					<td align="center" style="font-size:9px"><b>Frequency</b></td>	
					<td align="center" style="font-size:9px"><b>Mocn No</b></td>	
					<td align="center" style="font-size:9px"><b>CRM Product Name</b></td>	
					<td align="center" style="font-size:9px"><b>Billing Level No</b></td>	
					<td align="center" style="font-size:9px"><b>Logical Ckt Id</b></td>	
					<td align="center" style="font-size:9px"><b>Cust Logical SI No</b></td>	
					<td align="center" style="font-size:9px"><b>Service Name</b></td>
					<td align="center" style="font-size:9px"><b>Line Name</b></td>	
					<td align="center" style="font-size:9px"><b>CKT ID</b></td>	
					<td align="center" style="font-size:9px"><b>Location From</b></td>	
					<td align="center" style="font-size:9px"><b>Location To</b></td>	
					<td align="center" style="font-size:9px"><b>Service Stage</b></td>	
					<td align="center" style="font-size:9px"><b>BI-Source</b></td>
					<td align="center" style="font-size:9px"><b>Hardware Type</b></td>	
					<td align="center" style="font-size:9px"><b>Token No</b></td>	
					<td align="center" style="font-size:9px"><b>Billing Level</b></td>	
					<td align="center" style="font-size:9px"><b>Nature Of Sale</b></td>	
					<td align="center" style="font-size:9px"><b>Demo Type</b></td>	
					<td align="center" style="font-size:9px"><b>Type Of Sale</b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Bandwidth </b></td>
					<td align="center" style="font-size:9px"  ><b>Billing Uom </b></td>	
					<td align="center" style="font-size:9px"  ><b>Kms Distance </b></td>
					<td align="center" style="font-size:9px"  ><b>Ratio </b></td>		
					<td align="center" style="font-size:9px"  ><b>Order Stage </b></td>	
					<td align="center" style="font-size:9px"  ><b>Annotation </b></td>	
					<td align="center" style="font-size:9px"  ><b>Child Act No </b></td>	
					<td align="center" style="font-size:9px"  ><b>Cancel Reason </b></td>	
					<td align="center" style="font-size:9px"  ><b>Charge Type </b></td>	
					<td align="center" style="font-size:9px"  ><b>Order Type </b></td>	
					<td align="center" style="font-size:9px"  ><b>OrderSubType </b></td>	
					<td align="center" style="font-size:9px"  ><b>Vertical </b></td>	
					<td align="center" style="font-size:9px"  ><b>Region </b></td>	
					<td align="center" style="font-size:9px"  ><b>Credit Period </b></td>	
					<td align="center" style="font-size:9px"  ><b>Currency </b></td>	
					<td align="center" style="font-size:9px"  ><b>Legal Entity </b></td>	
					<td align="center" style="font-size:9px"  ><b>Bill Type </b></td>
					<td align="center" style="font-size:9px"  ><b>Bill Format </b></td>	
					<td align="center" style="font-size:9px"  ><b>Licence Company </b></td>	
					<td align="center" style="font-size:9px"  ><b>Taxation </b></td>
					 <!--GlobalDataBillingEfficiency BFR5  -->
				    <td align="center" style="font-size:9px"  ><b>TaxExemption Reason </b></td>	
					<td align="center" style="font-size:9px"  ><b>StoreName </b></td>	
					<td align="center" style="font-size:9px"  ><b>BillPeriod </b></td>	
					<td align="center" style="font-size:9px"  ><b> 	
					<logic:present name="RestPendingLineReport" scope="request">
							<logic:notEmpty  name="RestPendingLineReport" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Crm Order Id</a>
							</logic:notEmpty>
							<logic:empty  name="RestPendingLineReport" scope="request">
								Crm Order Id
							</logic:empty>
						</logic:present>
						</b></td>
					<td align="center" style="font-size:9px"  ><b>InvAmt</b></td>	
					<td align="center" style="font-size:9px"  ><b>LineAmmount </b></td>	
					<td align="center" style="font-size:9px"  ><b>TotalAmt </b></td>
					<td align="center" style="font-size:9px"  ><b>Periods in Month </b></td>
					<td align="center" style="font-size:9px"  ><b>Total PO AMT </b></td>			
					<td align="center" style="font-size:9px"  ><b>Party Id </b></td>	
					<td align="center" style="font-size:9px"  ><b>CrmAccountNo </b></td>	
					<td align="center" style="font-size:9px"  ><b>M6 Product ID </b></td>	
					<td align="center" style="font-size:9px"  ><b>Cust Total Po Amt </b></td>
					<td align="center" style="font-size:9px"  ><b>Business Serial No </b></td>	
					<td align="center" style="font-size:9px"  ><b>ServiceNo </b></td>
					<td align="center" style="font-size:9px"  ><b>LineNo </b></td>
					<td align="center" style="font-size:9px"  ><b>LB Service List Id </b></td>	
					<td align="center" style="font-size:9px"  ><b>LB PK Charge Id </b></td>	
					<td align="center" style="font-size:9px"  ><b>ChargeInfoId </b></td>	
				   <td align="center" style="font-size:9px"  ><b>Annual Rate </b></td>	
				</logic:equal>
				
				<logic:equal name="isUsage" value="1">
								
					<td align="center" class='inner col2 head1'  ><b>Account Id </b></td>
					<td align="center" class='inner col3 head1'  ><b>Party Name </b></td>
					<td align="center" class='inner col2 head1'  ><b>Party No </b></td>
					<td align="center" class='inner col4 head1'  ><b>Prj Manager </b></td>
					<td align="center" class='inner col4 head1'  ><b>Account Manager  </b></td>
					<td align="center" class='inner col2 head1'  ><b>Order Date  </b></td>										
					<td align="center" class='inner col2 head1'  ><b>Po Date</b></td>

					<td align="center" class='inner col2 head1'  ><b>COPC Approve Date  </b></td>					
					<td align="center" class='inner col2 head1'  ><b>Cust Po Rec Date  </b></td>
					<td align="center" class='inner col2 head1'  ><b>Cust PO Date  </b></td>
				    <td align="center" class='inner col2 head1'  ><b>Cancel Date </b></td>				    
				    <td align="center" class='inner col1 head1'  ><b>Bandwidth UOM  </b></td>				    
					<td align="center" class='inner col3 head1'  ><b>Billing Location</b></td>										
					<td align="center" class='inner col2 head1'  ><b>Billing Mode </b></td>										
					<td align="center" class='inner col2 head1'  ><b>PoNumber </b></td>
					<td align="center" class='inner col2 head1'  ><b>CustPoNumber  </b></td>
					<td align="center" class='inner col2 head1'  ><b>Cancel By  </b></td>
					<td align="center" class='inner col4 head1'  ><b>Primary Location  </b></td>
					<td align="center" class='inner col4 head1'  ><b>Secondary Location  </b></td>
					<td align="center" class='inner col3 head1'  ><b>Address  </b></td>					
					<td align="center" class='inner col2 head1'  ><b>RFS Date</b></td>								
					<td align="center" class='inner col2 head1'  ><b>Product Name</b></td>
					<td align="center" class='inner col2 head1'  ><b>Sub Product Name</b></td>	
					<td align="center" class='inner col2 head1'  ><b>LineItemNo</b></td>												
					<td align="center" class='inner col2 head1'  ><b>CRM Product Name</b></td>	
					<td align="center" class='inner col2 head1'  ><b>Billing Level No</b></td>	
					<td align="center" class='inner col2 head1'  ><b>Logical SI No</b></td>	
					<td align="center" class='inner col2 head1'  ><b>Cust Logical SI No</b></td>	
					<td align="center" class='inner col2 head1'  ><b>Service Name</b></td>
					<td align="center" class='inner col2 head1'  ><b>Line Name</b></td>	
					<td align="center" class='inner col2 head1'  ><b>CKT ID</b></td>	
					<td align="center" class='inner col2 head1'  ><b>Location From</b></td>	
					<td align="center" class='inner col2 head1'  ><b>Location To</b></td>	
					<td align="center" class='inner col2 head1'  ><b>Service Stage</b></td>																
					<td align="center" class='inner col2 head1'  ><b>Billing Level</b></td>							
					<td align="center" class='inner col2 head1'  ><b>Demo Type</b></td>						
					<td align="center" class='inner col1 head1'  ><b>Billing Bandwidth </b></td>
					<td align="center" class='inner col1 head1'  ><b>Billing Uom </b></td>	
					<td align="center" class='inner col1 head1'  ><b>Kms Distance </b></td>						
					<td align="center" class='inner col2 head1'  ><b>Order Stage </b></td>							
					<td align="center" class='inner col3 head1'  ><b>Child Act No </b></td>	
					<td align="center" class='inner col2 head1'  ><b>Child Act FX Internal No </b></td>
					<td align="center" class='inner col2 head1'  ><b>Cancel Reason </b></td>							
					<td align="center" class='inner col2 head1'  ><b>Order Type </b></td>	
					<td align="center" class='inner col2 head1'  ><b>Service Order Type </b></td>	
					<td align="center" class='inner col2 head1'  ><b>Vertical </b></td>	
					<td align="center" class='inner col2 head1'  ><b>Region </b></td>	
					<td align="center" class='inner col2 head1'  ><b>Credit Period </b></td>	
					<td align="center" class='inner col2 head1'  ><b>Currency </b></td>	
					<td align="center" class='inner col4 head1'  ><b>Legal Entity </b></td>	
					<td align="center" class='inner col2 head1'  ><b>Bill Type </b></td>
					<td align="center" class='inner col4 head1'  ><b>Bill Format </b></td>	
					<td align="center" class='inner col3 head1'  ><b>Licence Company </b></td>	
					<td align="center" class='inner col2 head1'  ><b>Taxation </b></td>	
					 <!--GlobalDataBillingEfficiency BFR5  -->
					<td align="center" class='inner col2 head1'  ><b>TaxExemption Reason </b></td>											
					<td align="center" class='inner col2 head1'  ><b> 	
					<logic:present name="RestPendingLineReport" scope="request">
							<logic:notEmpty  name="RestPendingLineReport" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Crm Order Id</a>
							</logic:notEmpty>
							<logic:empty  name="RestPendingLineReport" scope="request">
								Crm Order Id
							</logic:empty>
					</logic:present>
					</b></td>	
					<td align="center" class='inner col1 head1'  ><b>Total Amount</b></td>									
					<td align="center" class='inner col1 head1'  ><b>Periods in Month </b></td>
					<td align="center" class='inner col1 head1'  ><b>Total PO AMT </b></td>			
					<td align="center" class='inner col2 head1'  ><b>Party Id </b></td>	
					<td align="center" class='inner col2 head1'  ><b>CrmAccountNo </b></td>	
					<td align="center" class='inner col2 head1'  ><b>M6 Product ID </b></td>										
					<td align="center" class='inner col2 head1'  ><b>ServiceNo </b></td>
					<td align="center" class='inner col2 head1'  ><b>LineNo </b></td>		
					<td align="center" class='inner col2 head1'  ><b>Package ID</b></td>
					<td align="center" class='inner col3 head1'  ><b>Package Name</b></td>	
					<td align="center" class='inner col2 head1'  ><b>Component ID</b></td>
					<td align="center" class='inner col3 head1'  ><b>Component Name</b></td>
					<td align="center" class='inner col2 head1'  ><b>Component InfoID</b></td>
					<td align="center" class='inner col2 head1'  ><b>Component Status</b></td>
					<td align="center" class='inner col2 head1'  ><b>Component Start Date</b></td>
					<td align="center" class='inner col2 head1'  ><b>Component Type</b></td>
					<td align="center" class='inner col2 head1'  ><b>Component FX Instance Id</b></td>					
					<td align="center" class='inner col2 head1'  ><b>Start Token No</b></td>
					<td align="center" class='inner col2 head1'  ><b>Start FX Status</b></td>					
					<td align="center" class='inner col2 head1'  ><b>End FX Status</b></td>
					<!-- [002] Start  -->
					<td align="center" class='inner col2 head1'  ><b>Installation From City</b></td>
					<td align="center" class='inner col2 head1'  ><b>Installation To City</b></td>
					<td align="center" class='inner col2 head1'  ><b>Installation From State</b></td>
					<td align="center" class='inner col2 head1'  ><b>Installation To State</b></td>
					<td align="center" class='inner col2 head1'  ><b>Billing Contact Name</b></td>
					<td align="center" class='inner col2 head1'  ><b>Billing Contact Number</b></td>
					<td align="center" class='inner col2 head1'  ><b>Billing EmailId</b></td>
					<!-- [002] End  -->			
					<%--neha Start--%>
					<td align="center" class='inner col2 head1'  ><b>Change Order Reason</b></td>
					<%--neha End--%>																						   																
				</logic:equal>
				</tr>
				</table>
					 </div> 	
					 <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
		       			<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>
				<logic:present name="RestPendingLineReport" scope="request">
					<logic:notEmpty  name="RestPendingLineReport" scope="request"> 					
						<logic:iterate id="row" name="RestPendingLineReport" indexId="recordId">
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
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="partyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="partyNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="projectManager"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountManager"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="dispatchAddress1"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeEndDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="amapprovaldate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="copcapprovaldate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poReceiveDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="canceldate"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="rate_code"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="last_mile_media"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="last_mile_remarks"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="link_type"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="indicative_value"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="uom"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="last_mile_provider"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_address"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="hardware_flag"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="charge_status"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingMode"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="formAvailable"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="custPoDetailNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cancelflag"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="primarylocation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="seclocation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_address"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="fx_status"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="fx_sd_status"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="fx_ed_status"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="child_ac_fxstatus"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="rfs_date"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="opms_act_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="productName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="subProductName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="frequencyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="mocn_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="crm_productname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevelNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="logicalCircuitId"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="linename"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ckt_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_from"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_to"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceStage"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bisource"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="hardwaretypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="tokenno"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingLevel"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="saleNature"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="demoType"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="saleType"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_bandwidth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billing_uom"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="distance"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ratio"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="stageName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="annitation"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="child_act_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cancelServiceReason"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="order_type"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ordersubtype"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="verticalDetails"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="regionName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="creditPeriod"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="currencyName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="entity"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingTypeName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="billingFormatName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lcompanyname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="taxation"/></td>
						<!--GlobalDataBillingEfficiency BFR5  -->
               					<td  align="left" class='inner col2'><bean:write  name="row" property="taxExcemption_Reason"/>&nbsp;</td>
						
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="storename"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bill_period"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeAmount"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineamt"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="poAmountSum"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="contractPeriod"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="totalAmountIncludingCurrent"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="party_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="crmAccountNo"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6cktid"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="cust_total_poamt"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="business_serial_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceId"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceproductid"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lb_service_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lb_pk_charge_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="chargeinfoID"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="annual_rate"/></td>
						</logic:equal>

						<logic:equal name="isUsage" value="1">									
								<td align="left" class='inner col2'><bean:write  name="row" property="accountID"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="partyName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="partyNo"/>&nbsp;</td>
								<td align="left" class='inner col4'><bean:write  name="row" property="projectManager"/>&nbsp;</td>
								<td align="left" class='inner col4'><bean:write  name="row" property="accountManager"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="orderDate"/>&nbsp;</td>																
								<td align="left" class='inner col2'><bean:write  name="row" property="poDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="copcapprovaldate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="poReceiveDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="custPoDate"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="canceldate"/>&nbsp;</td>								
								<td align="left" class='inner col1'><bean:write  name="row" property="uom"/>&nbsp;</td>								
								<td align="left" class='inner col3'><bean:write  name="row" property="billing_address"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="billingMode"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="poNumber"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="custPoDetailNo"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="cancelBy"/>&nbsp;</td>
								<td align="left" class='inner col4'><bean:write  name="row" property="primarylocation"/>&nbsp;</td>
								<td align="left" class='inner col4'><bean:write  name="row" property="seclocation"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="billing_address"/>&nbsp;</td>								
								<td align="left" class='inner col2'><bean:write  name="row" property="rfs_date"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="productName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="subProductName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="lineno"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="crm_productname"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="billingLevelNo"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="logicalCircuitId"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="customer_logicalSINumber"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="serviceName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="linename"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="ckt_id"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="location_from"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="location_to"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="serviceStage"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="billingLevel"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="demoType"/>&nbsp;</td>
								<td align="left" class='inner col1'><bean:write  name="row" property="billing_bandwidth"/>&nbsp;</td>
								<td align="left" class='inner col1'><bean:write  name="row" property="billing_uom"/>&nbsp;</td>
								<td align="left" class='inner col1'><bean:write  name="row" property="distance"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="stageName"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="child_act_no"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="fx_internal_acc_id"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="cancelServiceReason"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="order_type"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="ordersubtype"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="verticalDetails"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="regionName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="creditPeriodName"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="currencyName"/>&nbsp;</td>
								<td align="left" class='inner col4'><bean:write  name="row" property="entity"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="billingTypeName"/>&nbsp;</td>
								<td align="left" class='inner col4'><bean:write  name="row" property="billingFormatName"/>&nbsp;</td>
								<td align="left" class='inner col3'><bean:write  name="row" property="lcompanyname"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="taxation"/>&nbsp;</td>
								<!--GlobalDataBillingEfficiency BFR5  -->
               					<td  align="left" class='inner col2'><bean:write  name="row" property="taxExcemption_Reason"/>&nbsp;</td>
						
								<td align="left" class='inner col2'><bean:write  name="row" property="orderNumber"/>&nbsp;</td>
								<td align="left" class='inner col1'><bean:write  name="row" property="poAmountSum"/>&nbsp;</td>
								<td align="left" class='inner col1'><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
								<td align="left" class='inner col1'><bean:write  name="row" property="totalAmountIncludingCurrent"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="party_id"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="crmAccountNo"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="m6_prod_id"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="serviceId"/>&nbsp;</td>
								<td align="left" class='inner col2'><bean:write  name="row" property="serviceproductid"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="packageID"/>&nbsp;</td>
								<td align="left" class='inner col3' ><bean:write  name="row" property="packageName"/>&nbsp;</td>	
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentID"/>&nbsp;</td>
								<td align="left" class='inner col3' ><bean:write  name="row" property="componentName"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentInfoID"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentStatus"/>&nbsp;</td> 
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.startDate"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentType"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.componentInstanceID"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.fxTokenNo"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.fxStartStatus"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="componentDto.endFxStatus"/>&nbsp;</td>
								<!-- [002] Start  -->
								<td align="center" class='inner col2'><bean:write  name="row" property="installationFromCity"/>&nbsp;</td>
								<td align="center" class='inner col2'><bean:write  name="row" property="installationToCity"/>&nbsp;</td>
								<td align="center" class='inner col2'><bean:write  name="row" property="installationFromState"/>&nbsp;</td>
								<td align="center" class='inner col2'><bean:write  name="row" property="installationToState"/>&nbsp;</td>
								<td align="center" class='inner col2'><bean:write  name="row" property="billingContactName"/>&nbsp;</td>
								<td align="center" class='inner col2'><bean:write  name="row" property="billingContactNumber"/>&nbsp;</td>
								<td align="center" class='inner col2'><bean:write  name="row" property="billingEmailId"/>&nbsp;</td>
								<!-- [002] End  -->
								<%--neha Start--%>
								<td align="center" class='inner col2'><bean:write  name="row" property="standardReason"/>&nbsp;</td>
								<%--neha End--%>
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
		              	<div class='horizontal-scrollrestpendinglinereportusage' onscroll='reposHorizontal(this);'>
		                  	<div>
		                  	</div>
		              	</div>	
		          	</td>
		     	</tr>	
		     </table>	
		     	 <!-- End Scrolling and Freezing of Rest Pending Line Report(USAGE)    -->
					
					<logic:empty  name="RestPendingLineReport">
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
