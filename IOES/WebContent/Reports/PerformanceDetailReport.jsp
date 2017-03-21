<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Kalpana	18-Apr-2013	HYPR11042013001		Changed accphoneNo to accountMgrPhoneNo from long to string -->
<!-- [002] Gunjan Singla  added a method populate report usage details -->
<!-- [003] Priya Gupta 28-Jan-2016	Added 2 new columns -->
<!-- [004] Gunjan Singla 6-May-16   20160418-R1-022266  Added columns -->
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
<title>Order Performance Report</title>
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
	myForm.submit();
}


function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	myform.toExcel.value='false';
	//myform.reset();
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.method.value='viewPerformanceDetailReport';	
	if(isBlankForm()==false)
	{
		return ;
	}
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
	myform.method.value='viewPerformanceDetailReport';	
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewPerformanceDetailReport";
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewPerformanceDetailReport&inExcel=true";
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
	myForm.osp.value='' ;		
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	var orderType=myForm.orderType.value;
	var fromOrderNo=myForm.fromOrderNo.value;
	var toOrderNo=myForm.toOrderNo.value;
	var fromAccountNo=myForm.fromAccountNo.value;
	var toAccountNo=myForm.toAccountNo.value;
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	var osp=myForm.osp.value;

	if((fromDate == null & toDate == null) 
		|| ( trim(fromDate)=="" & trim(toDate) == "")){
		alert("Please enter from Date and To Date!");
		return false;
		
	}  
	else {
				
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
	
		
		if((fromAccountNo == 0 || trim(fromAccountNo)== "" ) && (toAccountNo != 0 && trim(toAccountNo) != "" ))
			{
				alert("Please enter From AccountNo ");
				return false;
			}
		else if((fromAccountNo == 0 || trim(fromAccountNo)== "" ) && (toAccountNo != 0 && trim(toAccountNo) != "" ))
			{
				alert("Please enter To AccountNo ");
				return false;
			}
	
		if(fromAccountNo > toAccountNo)
			{			
				alert("From AccountNo cannot be greater than To AccountNo");
				return false;
			}
		return true;
	}
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
				<tr ><td align="left"><span>PERFORMANCE DETAIL REPORT</span> 
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
						<td align="right" style="font-size:9px" width="47"> ORDER TYPE :</td>
						<td align="left" nowrap width="100">
							<html:select property="orderType" / >
							<html:option value="">Select</html:option>
							<html:option value="New">New</html:option>
							<html:option value="Change">Change</html:option>
							</html:select>
						</td>
						
						<td align="right" style="font-size:9px;" width="54">FROM ORDER NO:</td>
						<td align="left" width="115"><html:text  property="fromOrderNo" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text></td>

						<td align="right" style="font-size:9px" width="33">TO ORDER NO:</td>
						<td align="left" width="132">
							<html:text  property="toOrderNo" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
						</td>
						<td align="right" style="font-size:9px" width="42">FROM ACCOUNT NO:</td>
						<td align="left" width="90">
							<html:text  property="fromAccountNo" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
						</td>
						<td width="41"/>
						<td align="center" valign="top" width="158">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
					</tr>
					<tr>						
						<td align="right" style="font-size:9px;" width="47">TO ACCOUNT NO:</td>
						<td align="left" width="100">
							<html:text  property="toAccountNo" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();">></html:text>
						</td>
						
						<td align="right" style="font-size:9px;" width="54">From Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="datefrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						
						<td align="right" style="font-size:9px;" width="33">To Date:</td>
						<td nowrap width="132"><html:text  property="toDate" styleId="todate" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						
							<td align="right" style="font-size:9px;" width="42">OSP</td>
								<td nowrap width="90">
								<html:select property="osp" / >
								<html:option value="">Select</html:option> 
								<html:option value='YES'>Yes</html:option> 
								<html:option value='NO'>No</html:option> 
								</html:select>
							</td>
							
							<td align="left" width="41">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
							
							<td align="left" width="158"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
							
							
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Performance Details</b></legend>
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
			
			<!-- Start Scrolling and Freezing of PERFORMANCE DETAIL REPORT   -->
			
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
								<td align="center" class='inner col4 head1'><b>Party Number</b></td>
								<td align="center" class='inner col2 head1'><b>Account No</b></td>
								<td align="center" class='inner col3 head1'><b>Party Name</b></td>					
								<td align="center" class='inner col4 head1'><b>Custsegment</b></td>
								<td align="center" class='inner col4 head1'><b>Indsegment</b></td>
								<td align="center" class='inner col2 head1'><b>Region</b></td>
								<td align="center" class='inner col2 head1'><b>Zone</b></td>
								<td align="center" class='inner col3 head1'><b>Account Mgr</b></td>
								<td align="center" class='inner col3 head1'><b>Project Mgr</b></td>
								<td align="center" class='inner col2 head1'><b>Order Type</b></td>					
								<td align="center" class='inner col2 head1' ><b>Demo Type</b></td>
								<td align="center" class='inner col2 head1'><b>Order Subtype</b></td>
								<td align="center" class='inner col2 head1'><b>Order Status</b></td>
								<td align="center" class='inner col2 head1'><b>Orderdate</b></td>
								<td align="center" class='inner col2 head1'><b>Order Approved Date</b></td>
								<td align="center" class='inner col2 head1'><b>Publish Date</b></td>
								<td align="center" class='inner col4 head1'><b>Task Name</b></td>
								<td align="center" class='inner col2 head1'><b>Actual Start Date</b></td>
								<td align="center" class='inner col2 head1'><b>Actual End Date</b></td>
								<td align="center" class='inner col2 head1'><b>Task Number</b></td>
								<td align="center" class='inner col3 head1'><b>Owner</b></td>
								<td align="center" class='inner col3 head1'><b>User Name</b></td>
								<td align="center" class='inner col4 head1'><b>Owner Phone</b></td>
								<td align="center" class='inner col4 head1'><b>Owner Emailid</b></td>
								<td align="center" class='inner col2 head1'><b>Order No</b></td>
								<td align="center" class='inner col1 head1'><b>Task Hours Taken</b></td>
								<td align="center" class='inner col2 head1'><b>Order Value</b></td>
								<td align="center" class='inner col4 head1'><b>Outcome</b></td>
								<td align="center" class='inner col3 head1'><b>Approval Remark</b></td>
								<td align="center" class='inner col4 head1'><b>Vertical</b></td>
								<td align="center" class='inner col4 head1'><b>OSP</b></td>
								<td align="center" class='inner col2 head1'><b>PO Detail Number</b></td>
								<td align="center" class='inner col2 head1'><b>Opportunity Id</b></td>
								<td align="center" class='inner col3 head1'><b>Group Name</b></td>
								<td align="center" class='inner col2 head1'><b>COPC Start Date</b></td>
								<td align="center" class='inner col2 head1'><b>COPC End Date</b></td>
					
 								<td align="center" class='inner col2 head1'><b>Old LSI</b></td>
 								<!-- [003] start -->
 								<td align="center" class='inner col2 head1'><b>SalesForce Opportunity Number</b></td>
 								
 								<!-- <td align="center" class='inner col2 head1'><b>Partner Id</b></td> [004]-->
 								<!-- [003] end -->
 								<!-- [004] start -->
 								<td align="center" class='inner col1 head1'><b>Partner Code</b></td>
 								<td align="center" class='inner col2 head1'><b>Partner Name</b></td>
							  	<td align="center" class='inner col3 head1'><b>Field Engineer</b></td>
 								<!-- [004] end -->
 								
				</tr>
						  </table>
					  </div> 
					  <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	        			 <table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>
				<logic:present name="performanceDetailList" scope="request">
					<logic:notEmpty  name="performanceDetailList" scope="request">
						<logic:iterate id="row" name="performanceDetailList" indexId="recordId">
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
											<td align="left" class='inner col4'><bean:write  name="row" property="partyNo"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="crmACcountNO"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="partyName"/>&nbsp;</td>														
											<td align="left" class='inner col4'><bean:write  name="row" property="cus_segment"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="industrySegment"/>&nbsp;</td>																
											<td align="left" class='inner col2'><bean:write  name="row" property="regionName"/>&nbsp;</td>								
											<td align="left" class='inner col2'><bean:write  name="row" property="zoneName"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="accountManager"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="projectManager"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="orderType"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="order_type"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="ordersubtype"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="orderStatus"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="orderDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="orderApproveDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="publishedDate"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="taskName"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="actualStartDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="actualEndDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="taskNumber"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="owner"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="userName"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="accountMgrPhoneNo"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="emailId"/>&nbsp;</td>	
											<td align="left" class='inner col2'><bean:write  name="row" property="orderNumber"/>&nbsp;</td>
											<td align="left" class='inner col1'><bean:write  name="row" property="totalDays"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="orderTotal"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="outCome"/>&nbsp;</td>	
											<td align="left" class='inner col3'><bean:write  name="row" property="remarks"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="vertical"/>&nbsp;</td>
											<td align="left" class='inner col4'><bean:write  name="row" property="osp"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="poNumber"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="opportunityId"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="groupName"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="copcStartDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="copcEndDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="oldLsi"/>&nbsp;</td>
											<!-- [003] start -->
											<td align="left" class='inner col2'><bean:write  name="row" property="salesForceOpportunityNumber"/>&nbsp;</td>
											
											<%-- <td align="left" class='inner col2'><bean:write  name="row" property="partnerId"/>&nbsp;</td> [004]--%>
											<!-- [003] end -->
											<!-- [004] start -->
											<td align="left" class='inner col1'><bean:write  name="row" property="partnerCode"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="channelPartner"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="fieldEngineer"/>&nbsp;</td>
											<!-- [004] end -->
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
			              	<div class='horizontal-scrollperformancedetailreport' onscroll='reposHorizontal(this);'>
			                  	<div>
			                  	</div>
			              	</div>
			          	</td>
			     	</tr>
			     </table>
		     <!-- End Scrolling and Freezing of PERFORMANCE DETAIL REPORT    -->
					<logic:empty  name="performanceDetailList">
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
