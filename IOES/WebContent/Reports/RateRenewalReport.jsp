<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[RPT7052013027]    Vijay    17-May-2013          Modify some columns and add some more columns     -->
<!-- [001] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
<!-- [002] Sadananda Behera  added new searching filter for Customer Segment -->
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
<title>RATE RENEWAL REPORT</title>
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
	myform.method.value='viewRateRenewalReport';	
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
	myform.method.value='viewRateRenewalReport';	
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewRateRenewalReport";
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewRateRenewalReport&inExcel=true";
			showLayerExcel();
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
//	myForm.orderType.value='' ; 
	myForm.fromDate.value='' ;	
	myForm.toDate.value='' ;
//	myForm.demo.value='' ;
	myForm.toAccountNo.value='' ;
	myForm.fromAccountNo.value='' ;
	myForm.toOrderNo.value='' ;
	myForm.fromOrderNo.value='' ;	
	myForm.cus_segment.value=0;
	
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
//	var orderStage=myForm.orderType.value;
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
//	var demo=myForm.demo.value; 
	var toAccountNo=myForm.toAccountNo.value;
	var fromAccountNo=myForm.fromAccountNo.value;
	var toOrderNo=myForm.toOrderNo.value;
	var fromOrderNo=myForm.fromOrderNo.value;
	

/*  if((fromDate==null && toDate==null &&    toAccountNo==null &&  fromAccountNo==null &&  toOrderNo==null &&  fromOrderNo==null 
	|| 
	 trim(toDate)=="" && trim(fromDate)==""   && trim(toAccountNo)==""  && trim(fromAccountNo)==""  && trim(toOrderNo)==""  && trim(fromOrderNo)==""  )){
		alert("Please enter atleast one search criteria!");
		return false;
		
	}
*/	 
	if((fromDate == null & toDate == null) || ( trim(fromDate)=="" & trim(toDate) == "")){
			alert("Please enter COPC Approval From Date and COPC Approval To Date!");
		return false;
	}
	else {
				
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
	/*	if((fromAccountNo == 0 || trim(fromAccountNo)== "" ) && (toAccountNo != 0 && trim(toAccountNo) != "" ))
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
		*/
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


//===[002]

function getCustomerSegment()
{
	
	    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		var cust_seg=jsonrpc.processes.populateCustomerSegmentDetail();
		var combomain = document.getElementById("customerSegment");	
		   var iCountRows = combomain.length;
		   for (var i = 1; i <  iCountRows; i++)
		   {
			   combomain.remove(i);
		   }
		 
		 for(var j=0;j<cust_seg.list.length;j++)
	     {
			 
		       var optionmain = document.createElement("option");
		       
		        optionmain.text = cust_seg.list[j].cus_segment;
		   		optionmain.title = cust_seg.list[j].cus_segment;
				optionmain.value = cust_seg.list[j].customerSegmentId;
				try 
				{
					combomain.add(optionmain, null); 
					
				}
				catch(error) 
				{
					combomain.add(optionmain); 
					 
				} 
				
	    	}
		
		 
		 var segment = <%=request.getAttribute("segment")%>;
		 if(segment !=null && segment !=''){
			
		    combomain.value=segment;
		   }
		 else
			 {
			 combomain.value=0;
			 
			 }
		 }

//====[002]

path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload="javascript:maxLimitChecker(document.getElementById('searchForm'),getCustomerSegment(),'<%=ApplicationFlags.ReportsExcelMaxSize%>','<%=Messages.getMessageValue("ReportsExcelMaxSizeMessage") %>');">
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
		
	<!-- [RPT7052013027] start -->
		<input type="hidden" name="fromPageSubmit" value="1"/>
	<!-- [RPT7052013027] end -->
		<input type="hidden" name="method" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="left">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> 
			
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>RATE RENEWAL REPORT</span> 
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
					
						
						<td align="right" style="font-size:9px;">COPC Approval From Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="datefrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
					
						<td align="right" style="font-size:9px;">COPC Approval To Date:</td>
						<td nowrap><html:text  property="toDate" styleId="dateto" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td align="right" style="font-size:9px;">Customer Segment:</td>
							
						 <td>
						 <select id="customerSegment" styleClass="inputBorder1" name="cus_segment" style="width:175px;" property="cus_segment" >
							<option value="0">Select Customer Segment </option>
						</select> &nbsp;	
						</td>  
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
						<td align="left">
							<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
						</td>
						
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
					
					</tr>
					</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>RATE RENEWAL REPORT</b></legend>
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
			
			<!-- Start Scrolling and Freezing of RATE RENEWAL REPORT  -->
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
								<td align="center" class='inner col2 head1'><b>Party Number</b></td>
								<td align="center" class='inner col3 head1'><b>Party Name</b></td>
								<td align="center" class='inner col2 head1'><b>Account No</b></td>		
								<td align="center" class='inner col3 head1'><b>Account Mgr</b></td>
								<td align="center" class='inner col4 head1'><b>Customer Segment</b></td>
								<td align="center" class='inner col2 head1'><b>Srv Segment</b></td>	
								<td align="center" class='inner col2 head1'><b>Vertical</b></td>		
								<td align="center" class='inner col2 head1'><b>Region</b></td>
								<td align="center" class='inner col4 head1'><b>Service Type</b></td>
								<td align="center" class='inner col2 head1'><b>Billling Bandwidth</b></td>
							 	<td align="center" class='inner col2 head1'><b>Bill Uom</b></td>  
								<td align="center" class='inner col4 head1'><b>Catagory Of Order</b></td>
								<td align="center" class='inner col2 head1'><b>Ordertype</b></td>
								<td align="center" class='inner col3 head1'><b>Company Name</b></td>		
								<td align="center" class='inner col2 head1'><b>Creation Date</b></td>
								<td align="center" class='inner col2 head1'><b>Currency</b></td>
								<td align="center" class='inner col2 head1'><b>Po Date</b></td>	
								<td align="center" class='inner col2 head1'><b>Po Number</b></td>		
								<td align="center" class='inner col3 head1'><b>Charge Name</b></td>
								<td align="center" class='inner col2 head1'><b>Charge Type</b></td>
								<td align="center" class='inner col4 head1'><b>From Site</b></td>
								<td align="center" class='inner col4 head1'><b>To Site</b></td>
								<td align="center" class='inner col2 head1'><b>Kms Distance</b></td>
								<td align="center" class='inner col3 head1'><b>Line Item Description</b></td>
								<td align="center" class='inner col2 head1'><b>Loc Date</b></td>
								<td align="center" class='inner col2 head1'><b>Copc Approved Date</b></td>		
	<!-- [RPT7052013027] --start -->														
								<td align="center" class='inner col2 head1'><b>Logical Circuit Id</b></td>		

								<td align="center" class='inner col4 head1'><b>Circuit Id</b></td>	
								<td align="center" class='inner col2 head1'><b>Payment Term</b></td>
							
								<td align="center" class='inner col2 head1'><b>Ratio</b></td>
								
								<td align="center" class='inner col2 head1'><b>Taxation</b></td>
								<td align="center" class='inner col4 head1'><b>Licence Company</b></td>	
								<td align="center" class='inner col1 head1'><b>Demo Type</b></td>		
								<td align="center" class='inner col4 head1'><b>Service Stage</b></td>
								<td align="center" class='inner col3 head1'><b>Order Stage Description</b></td>
								<td align="center" class='inner col4 head1'><b>New Order Remark</b></td>
			
								<td align="center" class='inner col4 head1'><b>Mocn Number</b></td>
   <!-- [RPT7052013027] --end -->							
							  	<td align="center" class='inner col3 head1'><b>Remarks</b></td>  
								<td align="center" class='inner col3 head1'><b>Product</b></td>
								<td align="center" class='inner col3 head1'><b>Sub Product</b></td>
								<td align="center" class='inner col4 head1'><b>Billing Trigger Date</b></td>
								<td align="center" class='inner col2 head1'><b>Billing Trigger Flag</b></td>		
								<td align="center" class='inner col3 head1'><b>Line Name</b></td>	
								<td align="center" class='inner col2 head1'><b>Charge Start Date</b></td>
								<td align="center" class='inner col2 head1'><b>Charge End Date</b></td>
								<td align="center" class='inner col2 head1'><b>End Date Logic</b></td>
								<td align="center" class='inner col4 head1'><b>Charge Status</b></td>
								<td align="center" class='inner col4 head1'><b>Charge Start Days Logic</b></td>
								<td align="center" class='inner col4 head1'><b>Charge Start Months Logic</b></td>		
								<td align="center" class='inner col2 head1'><b>Zone</b></td>
								<td align="center" class='inner col2 head1'><b>Name</b></td>
								<td align="center" class='inner col2 head1'><b>Po Amount</b></td>	
								<td align="center" class='inner col2 head1'><b>Contract Period</b></td>
								<td align="center" class='inner col2 head1'><b>Charge Period Months</b></td>
								<td align="center" class='inner col1 head1'><b>Item Quantity</b></td>
								<td align="center" class='inner col2 head1'><b>Order Total</b></td>
								<td align="center" class='inner col2 head1'><b>Service No</b></td>
								<td align="center" class='inner col2 head1'><b>M6 Order Id</b></td>
								<td align="center" class='inner col4 head1'><b>Cust Logical Si Id</b></td>
								<td align="center" class='inner col4 head1'><b>M6 Ckt Dtls Id</b></td>		
								<td align="center" class='inner col2 head1'><b>Annual Rate</b></td>	
								<td align="center" class='inner col2 head1'><b>Order Line Number</b></td>
							<!-- [RPT7052013027] --start -->									
								<td align="center" class='inner col2 head1'><b>Pk Charges Id</b></td>	
								<td align="center" class='inner col2 head1'><b>Crm Order Id</b></td>
							<!-- [RPT7052013027] --end -->											
								<td align="center" class='inner col2 head1'><b>Last Crm Order Id</b></td>
								<td align="center" class='inner col2 head1'><b>Line Item Amount</b></td>	
								<td align="center" class='inner col2 head1'><b>Old Line item amount</b></td>
								<td align="center" class='inner col2 head1'><b>Start Date Days</b></td>
								<td align="center" class='inner col2 head1'><b>Start Date Months</b></td>
							<!-- [RPT7052013027] --start -->	
								<!--<td align="center" class='inner col2 head1'><b>Service List Id</b></td>
							-->
							<!-- [RPT7052013027] --end -->		
								<td align="center" class='inner col2 head1'><b>Standard Reason</b></td>
								<!-- nancy start -->
				                <td align="center" class='inner col2 head1'><b>Is Differential</b></td>
				                <td align="center" class='inner col2 head1'><b>Old Pk ChargeId</b></td>
				                <td align="center" class='inner col2 head1'><b>STF</b></td>
				                <td align="center" class='inner col2 head1'><b>Effective Date</b></td>
				                <td align="center" class='inner col4 head1'><b>Billing Trg CreateDate</b></td>
				                <td align="center" class='inner col4 head1'><b>IsTriggerRequired</b></td>
				                <td align="center" class='inner col2 head1'><b>LineTriggered</b></td>
				                <td align="center" class='inner col2 head1'><b>TriggerProcess</b></td>
				                <td align="center" class='inner col2 head1'><b>TriggerDoneBy</b></td>
				                <td align="center" class='inner col4 head1'><b>AutomaticTriggerError</b></td>
				                <!-- nancy end -->
				</tr>
						</table>
					</div>
					<div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	        			<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>		
				<logic:present name="rateRenewalReportList" scope="request">
					<logic:notEmpty  name="rateRenewalReportList" scope="request"> 					
						<logic:iterate id="row" name="rateRenewalReportList" indexId="recordId">
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
											<td align="left" class='inner col2'><bean:write  name="row" property="partyNo"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="partyName"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="crmACcountNO"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="accountManager"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="cus_segment"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="serviceSegment"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="verticalDetails"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="regionName"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="serviceDetDescription"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="billing_bandwidth"/>&nbsp;</td>
			    							<td align="left" class='inner col2'><bean:write  name="row" property="billing_uom"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="changeTypeName"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="orderType"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="companyName"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write name="row" property="createdDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="currencyCode"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="poDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="custPoDetailNo"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="chargeName"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="chargeTypeName"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="fromLocation"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="toLocation"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="distance"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="lineItemDescription"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="LOC_Date"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="copcApproveDate"/>&nbsp;</td>
								<!-- [RPT7052013027] --start -->															
											<td align="left" class='inner col2'><bean:write  name="row" property="logicalCircuitId"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="logicalCircuitNumber"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="paymentTerm"/>&nbsp;</td>
										
											<td align="left" class='inner col2'><bean:write  name="row" property="ratio"/>&nbsp;</td>	
											
											<td align="left" class='inner col2'><bean:write  name="row" property="taxationName"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="lcompanyname"/>&nbsp;</td>
											<td align="left" class='inner col1'><bean:write  name="row" property="order_type"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="serviceStage"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="stageName"/>&nbsp;</td> 
											<td align="left" class='inner col4'><bean:write  name="row" property="newOrderRemark"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="mocn_no"/>&nbsp;</td>
								<!-- [RPT7052013027] --end -->						
											<td align="left" class='inner col3'><bean:write  name="row" property="remarks"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="productName"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="subProductName"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="billing_trigger_date"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="billingTriggerFlag"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="linename"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="startDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="endDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="endHWDateLogic"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="charge_status"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="startDaysLogic"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write name="row" property="startMonthsLogic"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="zoneName"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="salesCoordinator"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="poAmounts"/>&nbsp;</td>
									  		<td align="left" class='inner col2'><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="chargePeriod"/>&nbsp;</td>
											<td align="left" class='inner col1'><bean:write  name="row" property="itemQuantity"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="totalPoAmt"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="serviceId"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="m6OrderNo"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="logicalSINo"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="m6cktid"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="annual_rate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="serviceProductID"/>&nbsp;</td>
									<!-- [RPT7052013027] --start -->			
											<td align="left" class='inner col2'><bean:write  name="row" property="pk_charge_id"/>&nbsp;</td>
									<!-- [RPT7052013027] --end -->														
											<td align="left" class='inner col2'><bean:write  name="row" property="orderNo"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="oldOrderNo"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="chargeAmount_String"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="oldLineitemAmount"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="txtStartDays"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="txtStartMonth"/>&nbsp;</td>
									<!-- [RPT7052013027] --start -->			
											<!--<td align="left" class='inner col2'><bean:write  name="row" property="serviceId"/>&nbsp;</td>	
									-->
									<!-- [RPT7052013027] --end -->			
										    <td align="left" class='inner col2'><bean:write  name="row" property="standardReason"/>&nbsp;</td>	
									<!-- nancy start -->	
									        <td align="left" class='inner col2'><bean:write  name="row" property="isDifferential"/>&nbsp;</td>	
									        <td align="left" class='inner col2'><bean:write  name="row" property="oldPkChargeId"/>&nbsp;</td>
									        <td align="left" class='inner col2'><bean:write  name="row" property="copcApproverName"/>&nbsp;</td>
									        <td align="left" class='inner col2'><bean:write  name="row" property="effectiveDate"/>&nbsp;</td>
									        <td align="left" class='inner col4'><bean:write  name="row" property="billingTriggerCreateDate"/>&nbsp;</td>
									        <td align="left" class='inner col4'><bean:write  name="row" property="isTriggerRequired"/>&nbsp;</td>
									        <td align="left" class='inner col2'><bean:write  name="row" property="lineTriggered"/>&nbsp;</td>
									        <td align="left" class='inner col2'><bean:write  name="row" property="triggerProcess"/>&nbsp;</td>
									        <td align="left" class='inner col2'><bean:write  name="row" property="triggerDoneBy"/>&nbsp;</td>
									        <td align="left" class='inner col4'><bean:write  name="row" property="automaticTriggerError"/>&nbsp;</td>
									<!-- nancy end -->
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
		              	<div class='horizontal-scrollraterenewal' onscroll='reposHorizontal(this);'>
		                  	<div>
		                  	</div>
		              	</div>
		          	</td>
		     	</tr>
		     </table>
		     <!-- End Scrolling and Freezing of RATE RENEWAL REPORT   -->
			<logic:empty  name="rateRenewalReportList">
			   <!-- [RPT7052013027] start -->
					<% String fromPageSubmit=request.getParameter("fromPageSubmit");
							if(("1").equalsIgnoreCase(fromPageSubmit)){%>
			   <tr align="center" >
				 <td colspan="22" align="center"><B><font color="red">No Records Found</font></B></td>
			   </tr>
			  <%}%> 
			 <!-- [RPT7052013027] end -->	
			</logic:empty>	
			</logic:present>				
		   </table>
		 </div>
		</fieldset>
	</html:form>
</body>
</html>

