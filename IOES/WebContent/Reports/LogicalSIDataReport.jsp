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
<%@page import="com.ibm.ioes.utilities.ApplicationFlags"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">

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
	myform.method.value='viewLogicalSIDataReport';	
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
	myform.method.value='viewLogicalSIDataReport';	
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

		var path = "<%=request.getContextPath()%>/reportsAction.do?method=fetchStatus";		
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewLogicalSIDataReport";
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewLogicalSIDataReport&inExcel=true";
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
			
		<div border="1" class="head"><table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left">		
			 <span>LOGICAL SI DATA REPORT</span> 
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
			
			<!-- Start Scrolling and Freezing of LogicalSIData Report-->
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
									<td class='inner col3 head1'><b>
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
									<td class='inner col3 head1'><b>Record Status</b></td>
									<td class='inner col3 head1'><b>Logical Si Number</b></td>
									<td class='inner col3 head1'><b>Service Name</b></td>
									<td class='inner col3 head1'><b>Parent Line Name</b></td>
									<td class='inner col3 head1'><b>Line Name</b></td>
									<td class='inner col3 head1'><b>Charge Type</b></td>
									<td class='inner col3 head1'><b>Charge Name</b></td>
									<td class='inner col3 head1'><b>Charge Status</b></td>
									<td class='inner col3 head1'><b>Contract Period Mnths</b></td>
									<td class='inner col3 head1'><b>Total Amount</b></td>
									<td class='inner col3 head1'><b>Frequency</b></td>
									<td class='inner col3 head1'><b>Frequency Amt</b></td>
									<td class='inner col3 head1'><b>Start Date Logic</b></td>
									<td class='inner col3 head1'><b>Start Date Months</b></td>
									<td class='inner col3 head1'><b>Start Date Days</b></td>
									<td class='inner col3 head1'><b>Billing Trigger Date</b></td>
									<td class='inner col3 head1'><b>End Date Logic</b></td>
									<td class='inner col3 head1'><b>End Date Months</b></td>
									<td class='inner col3 head1'><b>End Date Days</b></td>
									<td class='inner col3 head1'><b>Charge End Date</b></td>
									<td class='inner col3 head1'><b>Annual Rate</b></td>
									<td class='inner col3 head1'><b>Trai Rate</b></td>
									<td class='inner col3 head1'><b>Discount</b></td>
									<td class='inner col3 head1'><b>Advance</b></td>
									<td class='inner col3 head1'><b>Installment Rate</b></td>
									<td class='inner col3 head1'><b>Dnd Dispatch But Not Delivered</b></td>
									<td class='inner col3 head1'><b>Dnd Dispatch And Delivered</b></td>
									<td class='inner col3 head1'><b>Ddni Disp Del Not Installed</b></td>
									<td class='inner col3 head1'><b>Ddni Disp Delivered Installed</b></td>
									<td class='inner col3 head1'><b>Po Valid Exclude</b></td>
									<td class='inner col3 head1'><b>Cust Po Number</b></td>
									<td class='inner col3 head1'><b>Cust Tot Po Amt</b></td>
									<td class='inner col3 head1'><b>Legal Entity</b></td>
									<td class='inner col3 head1'><b>Cust Po Date</b></td>
									<td class='inner col3 head1'><b>Po Contract Period</b></td>
									<td class='inner col3 head1'><b>Contract Start Date</b></td>
									<td class='inner col3 head1'><b>Contract End Date</b></td>
									<td class='inner col3 head1'><b>Cust Po Receive Date</b></td>
									<td class='inner col3 head1'><b>Charge Id</b></td>
									<td class='inner col3 head1'><b>Order Line Id</b></td>
									<td class='inner col3 head1'><b>Service No</b></td>
									<td class='inner col3 head1'><b>Pk Charges Id</b></td>
									<td class='inner col3 head1'><b>M6 Product Id</b></td>
									<td class='inner col3 head1'><b>Parent Product Id</b></td>
									<td class='inner col3 head1'><b>Charge Hdr Id</b></td>
									<td class='inner col3 head1'><b>Ib Pk Charges Id</b></td>
									<td class='inner col3 head1'><b>Ib Order Line Id</b></td>
									<td class='inner col3 head1'><b>M6 Order Id</b></td>
									<td class='inner col3 head1'><b>Order Line Si No</b></td>
									<td class='inner col3 head1'><b>Annotation</b></td>
									<td class='inner col3 head1'><b>Remarks</b></td>
									<td class='inner col3 head1'><b>Credit Period</b></td>
									<td class='inner col3 head1'><b>Bill Type</b></td>
									<td class='inner col3 head1'><b>Bill Format</b></td>
									<td class='inner col3 head1'><b>Taxation</b></td>
									<td class='inner col3 head1'><b>License Co</b></td>
									<td class='inner col3 head1'><b>Country</b></td>
									<td class='inner col3 head1'><b>Address1</b></td>
									<td class='inner col3 head1'><b>Address2</b></td>
									<td class='inner col3 head1'><b>Address3</b></td>
									<td class='inner col3 head1'><b>Address4</b></td>
									<td class='inner col3 head1'><b>City</b></td>
									<td class='inner col3 head1'><b>Postal Code</b></td>
									<td class='inner col3 head1'><b>State</b></td>
									<td class='inner col3 head1'><b>Active End Date</b></td>
									<td class='inner col3 head1'><b>Contact Person Name</b></td>
									<td class='inner col3 head1'><b>Person Designation</b></td>
									<td class='inner col3 head1'><b>Person Mobile</b></td>
									<td class='inner col3 head1'><b>Person Email</b></td>
									<td class='inner col3 head1'><b>Person Fax</b></td>
									<td class='inner col3 head1'><b>Lst No</b></td>
									<td class='inner col3 head1'><b>Lst Date</b></td>
									<td class='inner col3 head1'><b>Billing Address Type</b></td>
									<td class='inner col3 head1'><b>Attribute Name</b></td>
									<td class='inner col3 head1'><b>Attribute Value</b></td>
									<td class='inner col3 head1'><b>Store</b></td>
									<td class='inner col3 head1'><b>Hardware Type</b></td>
									<td class='inner col3 head1'><b>Nature Sale</b></td>
									<td class='inner col3 head1'><b>Form Available</b></td>
									<td class='inner col3 head1'><b>Type Of Sale</b></td>
									<td class='inner col3 head1'><b>Principle Amt</b></td>
									<td class='inner col3 head1'><b>Interest Rate</b></td>
									<td class='inner col3 head1'><b>Warranty Start Dt Logic</b></td>
									<td class='inner col3 head1'><b>Warranty Period Months</b></td>
									<td class='inner col3 head1'><b>Warranty Period Days</b></td>
									<td class='inner col3 head1'><b>Warranty Start Date</b></td>
									<td class='inner col3 head1'><b>Warranty End Date Logic</b></td>
									<td class='inner col3 head1'><b>Warranty End Period Months</b></td>
									<td class='inner col3 head1'><b>Warranty End Period Days</b></td>
									<td class='inner col3 head1'><b>Warranty End Date</b></td>
									<td class='inner col3 head1'><b>Extnd Support Period Months</b></td>
									<td class='inner col3 head1'><b>Extnd Support Period Days</b></td>
									<td class='inner col3 head1'><b>Extnd Support End Date</b></td>
									<td class='inner col3 head1'><b>Dispatch Address1</b></td>
									<td class='inner col3 head1'><b>Dispatch Address2</b></td>
									<td class='inner col3 head1'><b>Dispatch Address3</b></td>
									<td class='inner col3 head1'><b>Dispatch City</b></td>
									<td class='inner col3 head1'><b>Dispatch Postal Code</b></td>
									<td class='inner col3 head1'><b>Dispatch State</b></td>
									<td class='inner col3 head1'><b>Dispatch Conact Person Name</b></td>
									<td class='inner col3 head1'><b>Dispatch Contact Person Mobile</b></td>
									<td class='inner col3 head1'><b>Dispatch Lst Number</b></td>
									<td class='inner col3 head1'><b>Dispatch Lst Date</b></td>
									<td class='inner col3 head1'><b>Dispatch Address Type</b></td>
									<td class='inner col3 head1'><b>New Service List Id</b></td>
									<td class='inner col3 head1'><b>New Crm Order Id</b></td>
									<td class='inner col3 head1'><b>Remrks</b></td>
									<td class='inner col3 head1'><b>Billing Level</b></td>
									<td class='inner col3 head1'><b>Commitment Period</b></td>
									<td class='inner col3 head1'><b>Notice Period</b></td>
									<td class='inner col3 head1'><b>Circuit Id</b></td>
								</tr>
							</table>
						</div>
						<div id='contentscroll' class='content1' onscroll='reposHead(this);'>
							<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tableLogicalSIDataReport'>
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
												<td align="left" class='inner col3'><bean:write  name="row" property="orderNumber"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="recordStatus"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="logicalSINumber"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="serviceName"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="parent_name"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="serviceDetDescription"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="chargeTypeName"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="chargeName"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="charge_status"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="totalPoAmt"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="frequencyName"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="frequencyAmt"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="startDateLogic"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="startDateMonth"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="startDateDays"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="billingTriggerDate"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="endDateLogic"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="endDateMonth"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="endDateDays"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="chargeEndDate"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="annual_rate"/>&nbsp;</td>
												<td align="left" class='inner col3'>&nbsp;</td><!-- traiRate -->
												<td align="left" class='inner col3'>&nbsp;</td><!-- discount -->
												<td align="left" class='inner col3'>&nbsp;</td><!-- advance -->
												<td align="left" class='inner col3'>&nbsp;</td><!-- installmentRate -->
												<td align="left" class='inner col3'><bean:write  name="row" property="dnd_Dispatch_But_Not_Delivered"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="dnd_Dispatch_And_Delivered"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="dnd_Disp_Del_Not_Installed"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="dnd_Disp_Delivered_Installed"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="poExclude"/>&nbsp;</td><!-- poValidExclude -->
												<td align="left" class='inner col3'><bean:write  name="row" property="custPoDetailNo"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="poAmount"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="entity"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="custPoDate"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="contractStartDate"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="contractEndDate"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="poRecieveDate"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="chargeinfoID"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="orderLineNumber"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="serviceId"/>&nbsp;</td>
												<td align="left" class='inner col3'>&nbsp;</td><!-- pkChargesId-->
												<td align="left" class='inner col3'>&nbsp;</td><!-- m6ProductId -->
												<td align="left" class='inner col3'>&nbsp;</td><!-- parentProductId -->
												<td align="left" class='inner col3'><bean:write  name="row" property="billingInfoID"/>&nbsp;</td><!-- chargeHdrId -->
												<td align="left" class='inner col3'>&nbsp;</td><!-- ibPkChargesId -->
												<td align="left" class='inner col3'>&nbsp;</td><!-- ibOrderLineId -->
												<td align="left" class='inner col3'>&nbsp;</td><!-- m6orderId -->
												<td align="left" class='inner col3'>&nbsp;</td><!-- orderLineSiNo -->
												<td align="left" class='inner col3'><bean:write  name="row" property="chargeAnnotation"/>&nbsp;</td>
												<td align="left" class='inner col3'>&nbsp;</td><!-- remarks -->
												<td align="left" class='inner col3'><bean:write  name="row" property="creditPeriodName"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="billingTypeName"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="billingFormatName"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="taxation"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="lcompanyname"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="countyName"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="address1"/>&nbsp;</td><!-- address1 -->
												<td align="left" class='inner col3'><bean:write  name="row" property="address2"/>&nbsp;</td><!-- address2 -->
												<td align="left" class='inner col3'><bean:write  name="row" property="address3"/>&nbsp;</td><!-- address3 -->
												<td align="left" class='inner col3'><bean:write  name="row" property="address4"/>&nbsp;</td><!-- address4 -->
												<td align="left" class='inner col3'><bean:write  name="row" property="cityName"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="postalCode"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="stateName"/>&nbsp;</td>
												<td align="left" class='inner col3'>&nbsp;</td><!-- activeEndDate -->
												<td align="left" class='inner col3'><bean:write  name="row" property="contactName"/>&nbsp;</td><!-- contarPersonName -->
												<td align="left" class='inner col3'><bean:write  name="row" property="designation"/>&nbsp;</td><!-- personDesignation -->
												<td align="left" class='inner col3'><bean:write  name="row" property="telePhoneNo"/>&nbsp;</td><!-- personMobile -->
												<td align="left" class='inner col3'><bean:write  name="row" property="emailId"/>&nbsp;</td><!-- personEmail -->
												<td align="left" class='inner col3'><bean:write  name="row" property="fax"/>&nbsp;</td><!-- personFax -->
												<td align="left" class='inner col3'><bean:write  name="row" property="lst_No"/>&nbsp;</td><!-- lstNo-->
												<td align="left" class='inner col3'><bean:write  name="row" property="lstDate"/>&nbsp;</td><!-- lstDate -->
												<td align="left" class='inner col3'>&nbsp;</td><!-- billingAddressType -->
												<td align="left" class='inner col3'><bean:write  name="row" property="attributeLabel"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="attributeValue"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="storeName"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="hardwaretypeName"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="saleNature"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="formAvailable"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="saleType"/>&nbsp;</td>
												<td align="left" class='inner col3'>&nbsp;</td><!-- principalAmt -->
												<td align="left" class='inner col3'>&nbsp;</td><!-- intrestRate -->
												<td align="left" class='inner col3'><bean:write  name="row" property="warrantyStartDateLogic"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="warrantyPeriodMonths"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="warrantyPeriodDays"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="warrantyStartDate"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="warrantyEndDateLogic"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="warrantyEndPeriodMonths"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="warrantyEndPeriodDays"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="warrantyEndDate"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="extndSupportPeriodMonths"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="extndSupportPeriodDays"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="extSuportEndDate"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="dispatchAddress1"/>&nbsp;</td><!-- dispatchAddress1 -->
												<td align="left" class='inner col3'><bean:write  name="row" property="dispatchAddress2"/>&nbsp;</td><!-- dispatchAddress2 -->
												<td align="left" class='inner col3'><bean:write  name="row" property="dispatchAddress3"/>&nbsp;</td><!-- dispatchAddress3 -->
												<td align="left" class='inner col3'><bean:write  name="row" property="dispatchCityName"/>&nbsp;</td><!-- dispatchCity -->
												<td align="left" class='inner col3'><bean:write  name="row" property="dispatchPostalCode"/>&nbsp;</td><!-- dispatchPostalCode -->
												<td align="left" class='inner col3'><bean:write  name="row" property="dispatchStateName"/>&nbsp;</td><!-- dispatchState -->
												<td align="left" class='inner col3'><bean:write  name="row" property="dispatchPersonName"/>&nbsp;</td><!-- dispatchContactPersonName -->
												<td align="left" class='inner col3'><bean:write  name="row" property="dispatchPhoneNo"/>&nbsp;</td><!-- dispatchContactPersonMobile -->
												<td align="left" class='inner col3'><bean:write  name="row" property="dispatchLstNumber"/>&nbsp;</td><!-- dispatchLstNumber -->
												<td align="left" class='inner col3'><bean:write  name="row" property="dispatchLstDate"/>&nbsp;</td><!-- dispatchLstDate -->
												<td align="left" class='inner col3'>&nbsp;</td><!-- DispatchAddressType -->
												<td align="left" class='inner col3'>&nbsp;</td><!-- NewServiceListId -->
												<td align="left" class='inner col3'>&nbsp;</td><!-- NewCrmOrderId -->
												<td align="left" class='inner col3'>&nbsp;</td><!-- remarks -->
												<td align="left" class='inner col3'><bean:write  name="row" property="billingLevel"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="commitmentPeriod"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="noticePeriod"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="logicalCircuitId"/>&nbsp;</td>

					
								
							</tr>
						</logic:iterate>
					</logic:notEmpty>
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
	                   	<div class='horizontal-scrollLogicalSIDataReport' onscroll='reposHorizontal(this);'>
	                       	<div>
	                       	</div>
	                   	</div>
	               	</td>
				</tr>
			</table>
			<!-- End Scrolling and Freezing of LogicalSIData Report-->	
		</fieldset>
	</html:form>
</body>
</html>
