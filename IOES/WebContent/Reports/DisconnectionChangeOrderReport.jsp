<!-- [001] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
<!-- [002] Sadananda Behera  added new searching filter for Customer Segment -->
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
<title>Disconnection Change Order Report</title>
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
	myform.action="<%=request.getContextPath()%>/reportsAction.do?method=viewDisconnectionChangeOrderReport";	
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
	myform.action="<%=request.getContextPath()%>/reportsAction.do?method=viewDisconnectionChangeOrderReport";	
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
//		var path = "<%=request.getContextPath()%>/reportsAction.do?method=fetchStatus";		
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewDisconnectionChangeOrderReport";	
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewDisconnectionChangeOrderReport&inExcel=true";
		showLayerExcel();
		myForm.submit();
	}
	
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.fromDate.value='' ;	
	myForm.toDate.value='' ;	
	myForm.servicename.value='' ;
	myForm.ordersubtype.value='' ;
	//myForm.ordermonth.value='' ;
	myForm.verticalDetails.value='' ;
	myForm.cus_segment.value=0;
	myForm.srrequest.value=0;
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
	
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	var servicename=myForm.servicename.value;
	//var orderMonth=myForm.ordermonth.value;
	var orderSubType=myForm.ordersubtype.value;
	var verticaldetails=myForm.verticalDetails.value;
	var srrequest=myForm.srrequest.value;
	
		/*if((fromDate == null || trim(fromDate)== "" ) && (toDate == null || trim(toDate) == "" ) && (servicename == null || trim(servicename) == "") && (orderMonth == null || trim(orderMonth) == "" ) && (orderSubType == null || trim(orderSubType) == "" ) && (verticaldetails == null || trim(verticaldetails) == "" ))
		{
				alert('Please enter atleast one search creteria');
				return false;
				
		}
		else
		{	
		if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
		{
			alert("Please enter From COPC Approved Date ");
			return false;
		}
		else if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
		{
			alert("Please enter To COPC Approved Date ");
			return false;
		}
		
		if(dateCompare(fromDate,toDate)==1)
			{			
				alert("From COPC Approved Date cannot be greater than To COPC Approved Date");
				return false;
			}*/
		
		
		if((fromDate == null || trim(fromDate)== "" ) && (toDate == null || trim(toDate) == "" ))
		{
			alert("Please enter From and To Order Date ");
			return false;
		}
		if(fromDate == null || trim(fromDate)== "" )
		{
			alert("Please enter From Order Date ");
			return false;
		}
		if(toDate == null || trim(toDate)== "" )
		{
			alert("Please enter To Order Date ");
			return false;
		}
		if(dateCompare(fromDate,toDate)==1)
			{			
				alert("From  Date cannot be greater than To  Date");
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

//[002]	
	
	function getCustomerSegmentForDiscon()
	{
		
		    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			var cust_seg=jsonrpc.processes.populateCustomerSegmentDetailForDiscon();
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
			 var custsegment = <%=request.getAttribute("custsegment")%>;
			 if(custsegment !=null && custsegment !=''){
				
			    combomain.value=custsegment;
			   }
			 else
				 {
				 combomain.value=0;
				 
				 }
	}

//[002]
path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload="javascript:maxLimitChecker(document.getElementById('searchForm'),getCustomerSegmentForDiscon(),'<%=ApplicationFlags.ReportsExcelMaxSize%>','<%=Messages.getMessageValue("ReportsExcelMaxSizeMessage") %>');">
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/reportsAction" styleId="searchForm" method="post">
		<bean:define id="formBean" name="reportsBean"></bean:define>
		<html:hidden property="toExcel"/>
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="reportProcessForLimit"/>
          <html:hidden property="reportCurrentCount"/>
		<html:hidden property="interfaceId"/>
		<html:hidden property="isDumpAvailable"/>
		<html:hidden property="dumpFileName"/>		
		
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
				<tr ><td align="left"><span>Disconnection Change Order Report</span>  
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
				
						<td width="10px"/>
						<td align="right" style="font-size:9px;">From Order Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="datefrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">To Order Date:</td>
						<td nowrap><html:text  property="toDate" styleId="dateTo" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
							</td>
				    <!-- [002] -->
						<td align="right" style="font-size:9px;">Customer Segment:</td>	
						 <td>
						 <select id="customerSegment" styleClass="inputBorder1" name="cus_segment" style="width:175px;" property="cus_segment" >
							<option value="0">Select Customer Segment </option>
						</select> &nbsp;	
						</td>   
					<!-- [002] -->
					
					     </tr>
						<tr>
						<td width="10px"/>
						<td align="right" style="font-size:9px">Service Name:</td>
						<td align="left">
							<html:text  property="servicename"  style="width:75px;float:left;" maxlength="51" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Service Name')};" ></html:text>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px">Service Sub Order Type:</td>
						<td align="left">
							<html:select  property="ordersubtype"  style="width:150px;float:left;">
								<html:option value="">SELECT</html:option>
								<html:option value="3">Permanent Disconnection</html:option>
							    <html:option value="4">Permanent Disconnection After Suspention</html:option>
							    <html:option value="6">Suspension</html:option>
							    <html:option value="7">Resumption</html:option>
							    <html:option value="13">Demo Suspention</html:option>
							    <html:option value="14">Demo Resumption</html:option>
							    <html:option value="15">Demo Permanent Disconnection</html:option>
							    <html:option value="16">Demo Permanent Disconnection After Suspension</html:option>
								</html:select>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">SR Request:</td>	
						 <td>
							<html:select property="srrequest" style="width:100px;">
							<html:option value="0">All</html:option>
							<html:option value="1">Only SR </html:option>
							<html:option value="2">Non SR</html:option>
							</html:select>
						</td>
						</tr>
						<tr>
						<td width="50px"/>
						<!--<td align="right" style="font-size:9px"> Order Month:</td>
						
						<td align="left" nowrap>
							<html:select  property="ordermonth" styleClass="inputBorder1" style="width:90px;float:left;"  >
							<html:option value="">SELECT</html:option>
							<html:option value="1">January</html:option>
						    <html:option value="2">Februry</html:option>
						    <html:option value="3">March</html:option>
						    <html:option value="4">April</html:option>
						    <html:option value="5">May</html:option>
						    <html:option value="6">June</html:option>
						    <html:option value="7">July</html:option>
						    <html:option value="8">August</html:option>
						    <html:option value="9">September</html:option>
						    <html:option value="10">October</html:option>
						    <html:option value="11">November</html:option>
						    <html:option value="12">December</html:option>
						  
						 
						
							</html:select>
						</td>
						<td width="50px"/>-->
						<td align="right" style="font-size:9px"> Vertical:</td>
						<td align="left" nowrap>
							<html:text  property="verticalDetails"  style="width:90px;float:left;" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Vertical')};"></html:text>
						</td>
						<td width="10px"/>
						<td align="center" valign="top">
							<div class="searchBg" ><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
						<td align="left" >
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td align="left" >
							<div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div>
						</td>
							
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Disconnection Change Order Report</b></legend>
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
			
				<!-- start Scrolling and Freezing of Disconnection Change Report-->
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
					<td align="center" class='inner col2 head1'><b>Logical Circuit Id</b></td>
					<td align="center" class='inner col4 head1'><b>Service Name </b></td>
					<td align="center" class='inner col4 head1'><b>Line Name </b></td>
					<td align="center" class='inner col1 head1'><b>Charge Type </b></td>
					<td align="center" class='inner col4 head1'><b>Charge Name</b></td>
					<td align="center" class='inner col2 head1' ><b>Frequency</b></td>
					<td align="center" class='inner col2 head1' ><b>Bill Period </b></td>
					<td align="center" class='inner col31 head1' ><b>Start Date Logic </b></td>
					<td align="center" class='inner col31 head1' ><b>End Date Logic </b></td>
					<td align="center" class='inner col31 head1' ><b>Charge End Date</b></td>
					<td align="center" class='inner col31 head1' ><b>Charge Start Date </b></td>
					<td align="center" class='inner col2 head1' ><b>Account Number </b></td>
					<td align="center" class='inner col2 head1' ><b>Credit Period</b></td>
					<td align="center" class='inner col2 head1' ><b>Currency</b></td>
					<td align="center" class='inner col31 head1' ><b>Legal Entity </b></td>
					<td align="center" class='inner col2 head1' ><b>Billing Mode </b></td>
					<td align="center" class='inner col1 head1' ><b>Bill Type </b></td>
					<td align="center" class='inner col31 head1' ><b>Bill Format </b></td>
					<td align="center" class='inner col31 head1' ><b>Licence Company </b></td>
					<td align="center" class='inner col2 head1' ><b>Taxation </b></td>
					<td align="center" class='inner col2 head1' ><b>Penalty Clause </b></td>
					<td align="center" class='inner col2 head1' ><b>Billing Level </b></td>
					<td align="center" class='inner col31 head1' ><b>Billing Level Number</b></td>
					<td align="center" class='inner col2 head1' ><b>Store </b></td>
					<td align="center" class='inner col2 head1' ><b>Hardware Type</b></td>
					<td align="center" class='inner col2 head1' ><b>Form C Available</b></td>
					<td align="center" class='inner col2 head1' ><b>Nature Of Sale </b></td>
					<td align="center" class='inner col2 head1' ><b>Type of Sale </b></td>
					<td align="center" class='inner col31 head1' ><b>Po No </b></td>
					<td align="center" class='inner col31 head1' ><b>PO Date </b></td>
					<td align="center" class='inner col31 head1' ><b>Party </b></td>
					<td align="center" class='inner col4 head1' ><b>Annotation </b></td>
					<td align="center" class='inner col31 head1' ><b>Token NO </b></td>
					<td align="center" class='inner col31 head1' ><b>Billing Trig Status </b></td>
					<td align="center" class='inner col31 head1' ><b>Billing Trig Flag </b></td>
					<td align="center" class='inner col31 head1' ><b>PM Prov Date</b></td>
					<td align="center" class='inner col31 head1' ><b>LOC Date </b></td>
					<td align="center" class='inner col31 head1' ><b>Billing Trig Date </b></td>
					<td align="center" class='inner col31 head1' ><b>Challen No </b></td>
					<td align="center" class='inner col31 head1' ><b>Challen Date </b></td>
					<td align="center" class='inner col4 head1' ><b>Child Account Number</b></td>
					<td align="center" class='inner col31 head1' ><b>Child Account Fx Status</b></td>
					<td align="center" class='inner col31 head1' ><b>Order Date </b></td>
					<td align="center" class='inner col2 head1' ><b>Order Type </b></td>
					<td align="center" class='inner col2 head1' ><b>Service Order Type </b></td>
					<td align="center" class='inner col31 head1' ><b>COPC Approved Date </b></td>
					<td align="center" class='inner col31 head1' ><b>Bill Trg Create Date</b></td>
					<td align="center" class='inner col2 head1' ><b> Cust Logical SI No.</b></td>
					<td align="center" class='inner col1 head1' ><b>Hardware Flag </b></td>
					<td align="center" class='inner col1 head1' ><b>Charge Type ID </b></td>
					<td align="center" class='inner col31 head1' ><b>Service Stage </b></td>
					<td align="center" class='inner col31 head1' ><b>Order Stage </b></td>
					<td align="center" class='inner col31 head1' ><b>Account Mgr </b></td>
					<td align="center" class='inner col31 head1' ><b>Project Mgr </b></td>
					<td align="center" class='inner col31 head1' ><b>Order Creation Date </b></td>
					<td align="center" class='inner col31 head1' ><b>Customer Service Rfs Date</b></td>
					<td align="center" class='inner col31 head1' ><b>Cust Po Receive Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Charge Status </b></td>
					<td align="center" class='inner col2 head1' ><b>Vertical </b></td>
					<td align="center" class='inner col2 head1' ><b>Region </b></td>
					<td align="center" class='inner col2 head1' ><b>Demo Type </b></td>
					<td align="center" class='inner col31 head1' ><b>New Order Remark</b></td>
					<!--<td align="center" style="font-size:9px" ><b>Order Stage Description </b></td>
					<td align="center" class='inner col2 head1' ><b>Mocn Number</b></td>-->
					<td align="center" class='inner col31 head1' ><b>Disconnection Remarks </b></td>
					<td align="center" class='inner col2 head1' ><b>Sr. Number</b></td>
					<td align="center" class='inner col31 head1' ><b>Request Received Date </b></td>
					<!--<td align="center" style="font-size:9px" ><b>Line Item Number </b></td>-->
					<td align="center" class='inner col2 head1' ><b>Order Month</b></td>
					<td align="center" class='inner col2 head1' ><b>Circuit Id</b></td>
					<td align="center" class='inner col31 head1' ><b>Standard Reason </b></td>
					<td align="center" class='inner col2 head1' ><b>Bandwidth </b></td>
					<td align="center" class='inner col2 head1' ><b>Billing Bandwidth </b></td>
					<td align="center" class='inner col2 head1' ><b>Billing Bandwidth UOM </b></td>
					<!--<td align="center" style="font-size:9px" ><b>Bandwidth Attribute </b></td>-->
					<td align="center" class='inner col2 head1' ><b>Bandwidth UOM </b></td>
					<td align="center" class='inner col2 head1' ><b> Rate Code</b></td>
					<td align="center" class='inner col2 head1' ><b>Last Mile Media </b></td>
					<td align="center" class='inner col2 head1' ><b>Last Mile Remarks </b></td>
					<td align="center" class='inner col2 head1' ><b>Chargeable Distance</b></td>
					<td align="center" class='inner col2 head1' ><b>Last Mile Provider</b></td>
					<td align="center" class='inner col2 head1' ><b>Link Type</b></td>
					<td align="center" class='inner col51 head1' ><b>Dispatch Address</b></td>
					<td align="center" class='inner col2 head1' ><b>Indicative Value</b></td>
					<td align="center" class='inner col31 head1' ><b>Product Name</b></td>
					<td align="center" class='inner col31 head1' ><b>Cust Po Date</b></td>
					<td align="center" class='inner col31 head1' ><b>Cust Po Number</b></td>
					<td align="center" class='inner col31 head1' ><b>Loc Number</b></td>
					<td align="center" class='inner col31 head1' ><b>Pri Loc</b></td>
					<td align="center" class='inner col31 head1' ><b>Product</b></td>
					<td align="center" class='inner col1 head1' ><b>Ratio</b></td>
					<td align="center" class='inner col31 head1' ><b>Sec Loc</b></td>
					<td align="center" class='inner col31 head1' ><b>Sub Product</b></td>
					<!--<td align="center" style="font-size:9px" ><b>Token Number</b></td>-->
					<td align="center" class='inner col2 head1' ><b>Order Number</b></td>
					<td align="center" class='inner col2 head1' ><b>Charge Hdr Id</b></td>
					<td align="center" class='inner col2 head1' ><b>Order Line Id</b></td>
					<!--<td align="center" style="font-size:9px" ><b>Cust Logical Si Id</b></td>-->
					<td align="center" class='inner col2 head1' ><b>Service No</b></td>
					<td align="center" class='inner col2 head1' ><b>Inv Amt</b></td>
					<td align="center" class='inner col2 head1' ><b>Amt</b></td>
					<td align="center" class='inner col2 head1' ><b>Total Amount</b></td>
					<!--<td align="center" style="font-size:9px" ><b>Advance</b></td>
					<td align="center" class='inner col2 head1' ><b>Installment Rate</b></td>-->
					<td align="center" class='inner col2 head1' ><b>Start Date Days</b></td>
					<td align="center" class='inner col2 head1' ><b>Start Date Months</b></td>
					<td align="center" class='inner col2 head1' ><b>End Date Days</b></td>
					<td align="center" class='inner col2 head1' ><b>End Date Months</b></td>
					<td align="center" class='inner col2 head1' ><b>Annual Rate</b></td>
					<td align="center" class='inner col2 head1' ><b>Contract Period Mnths</b></td>
					<td align="center" class='inner col2 head1' ><b>Commitment Period</b></td>
					<td align="center" class='inner col2 head1' ><b>Notice Period</b></td>
					<td align="center" class='inner col2 head1' ><b>Period In Month</b></td>
					<td align="center" class='inner col2 head1' ><b>Tot Po Amt</b></td>
					<td align="center" class='inner col2 head1' ><b>Party Id</b></td>
					<td align="center" class='inner col31 head1' ><b>Cust Account Id</b></td>
					<!--<td align="center" style="font-size:9px" ><b>M6 Product Id</b></td>-->
					<td align="center" class='inner col2 head1' ><b>M6 Order Id</b></td>
					<!--<td align="center" style="font-size:9px" ><b>Ib Service List Id</b></td>
					<td align="center" class='inner col2 head1' ><b>Ib Order Line Id</b></td>
					<td align="center" class='inner col2 head1' ><b>Ib Pk Charges Id</b></td>
					<td align="center" class='inner col2 head1' ><b>Ib Charge Hdr Id</b></td>-->
					<td align="center" class='inner col2 head1' ><b>Pre Crm Order Id</b></td>
					<!--<td align="center" style="font-size:9px" ><b>M6 Ckt Dtls Id</b></td>-->
					<td align="center" class='inner col2 head1' ><b>Pk Charges Id</b></td>
					<!--<td align="center" style="font-size:9px" ><b>Business Serial No</b></td>-->
					<td align="center" class='inner col31 head1' ><b>Billing Location From</b></td>
					<td align="center" class='inner col31 head1' ><b>Billing Location To</b></td>
					<td align="center" class='inner col4 head1'><b>Customer Segment</b></td>
					<!-- Changes mad to add extra column in View disconnection change of report@ satish starts -->
					<td align="center" class='inner col4 head1'><b>Desired Due Date</b></td>
					<td align="center" class='inner col4 head1' ><b>Derived Disconnection Date</b></td>
					<td align="center" class='inner col4 head1' ><b>Is Trigger Required</b></td>
					<td align="center" class='inner col4 head1' ><b>Line Triggered</b></td>
					<td align="center" class='inner col4 head1' ><b>Trigger Process</b></td>
					<td align="center" class='inner col4 head1' ><b>Trigger DoneBy</b></td>
					<td align="center" class='inner col6 head1' ><b>Automatic Trigger Error</b></td> 
					
					<!-- Changes mad to add extra column in View disconnection change of report@ satish ends -->
			</tr>
						</table>
				</div>                		 	                		
	          <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	               <table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>
						<logic:present name="DisconnectChangeOrderReport" scope="request">
					<logic:notEmpty  name="DisconnectChangeOrderReport" scope="request"> 					
						<logic:iterate id="row" name="DisconnectChangeOrderReport" indexId="recordId">
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
						<td align="center" class='inner col2'><bean:write  name="row" property="logicalCircuitId"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="serviceName"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="linename"/>&nbsp;</td>
					    <td align="left" class='inner col1'><bean:write  name="row" property="chargeTypeName"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="chargeName"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="frequencyName"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="bill_period"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="startDateLogic"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="endDateLogic"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="chargeEndDate"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="startDate"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="accountID"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="creditPeriodName"/>&nbsp;</td>
					    <td align="left" class='inner col2'><bean:write  name="row" property="currencyName"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="entity"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="billingMode"/>&nbsp;</td>
						<td align="left" class='inner col1'><bean:write  name="row" property="billingTypeName"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="billingformat"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="licCompanyName"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="taxation"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="penaltyClause"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="billingLevel"/>&nbsp;</td>
					   	<td align="left" class='inner col31'><bean:write  name="row" property="billingLevelId"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="store"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="hardwareType"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="formAvailable"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="nature_sale"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="type_sale"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="poNumber"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="poDate"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="party"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="annitation"/>&nbsp;</td>
					    <td align="left" class='inner col31'><bean:write  name="row" property="tokenno"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="billing_Trigger_Status"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="billing_Trigger_Flag"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="pm_pro_date"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="locDate"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="billingTriggerDate"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="challenno"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="challendate"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="child_act_no"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="child_ac_fxstatus"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="orderDate"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="order_type"/>&nbsp;</td>
					    <td align="left" class='inner col2'><bean:write  name="row" property="serviceOrderType"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="copcapprovaldate"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="billingtrigger_createdate"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="customer_logicalSINumber"/>&nbsp;</td>
						<td align="left" class='inner col1'><bean:write  name="row" property="hardware_flag"/>&nbsp;</td>
						<td align="center" class='inner col1'><bean:write  name="row" property="chargeinfoID"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="serviceStage"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="orderStage"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="actmgrname"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="prjmngname"/>&nbsp;</td>
					    <td align="center" class='inner col31'><bean:write  name="row" property="orderDate"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="rfs_date"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="cust_po_rec_date"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="charge_status"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="verticalDetails"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="region"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="demo"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="newOrderRemark"/>&nbsp;</td>
						<!--<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderStageDescription"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="mocn_no"/>&nbsp;</td>-->
					    <td align="left" class='inner col31'><bean:write  name="row" property="disconnection_remarks"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="srno"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="request_rec_date"/>&nbsp;</td>
						<!--<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lineno"/>&nbsp;</td>-->
						<td align="center" class='inner col2'><bean:write  name="row" property="ordermonth"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="ckt_id"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="standard_reason"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="bandwaidth"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="billing_bandwidth"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="billing_uom"/>&nbsp;</td>
					    <!--<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bandwidth_att"/>&nbsp;</td>-->
						<td align="center" class='inner col2'><bean:write  name="row" property="uom"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="rate_code"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="last_mile_media"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="last_mile_remarks"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="chargeable_Distance"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="last_mile_provider"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="link_type"/>&nbsp;</td>
						<td align="left" class='inner col51'><bean:write  name="row" property="dispatchAddress1"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="indicative_value"/>&nbsp;</td>
					    <td align="left" class='inner col31'><bean:write  name="row" property="productName"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="custPoDate"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="custPoDetailNo"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="locno"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="primarylocation"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="prodAlisName"/>&nbsp;</td>
						<td align="left" class='inner col1'><bean:write  name="row" property="ratio"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="seclocation"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="sub_linename"/>&nbsp;</td>
						<!--<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="tokenNO"/>&nbsp;</td>-->
					    <td align="center" class='inner col2'><bean:write  name="row" property="orderNo"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="charge_hdr_id"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="orderLineNumber"/>&nbsp;</td>
						<!--<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/>&nbsp;</td>-->
						<td align="center" class='inner col2'><bean:write  name="row" property="serviceId"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="frequencyAmt"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="lineItemAmount"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="chargeAmount_String"/>&nbsp;</td>
						<!--<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="advance"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="installation_addressaa1"/>&nbsp;</td>-->
					    <td align="center" class='inner col2'><bean:write  name="row" property="startDateDays"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="startDateMonth"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="endDateDays"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="endDateMonth"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="annualRate"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="contractMonths"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="commitmentPeriod"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="noticePeriod"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="periodsInMonths"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="totalPoAmt"/>&nbsp;</td>
					    <td align="center" class='inner col2'><bean:write  name="row" property="party_id"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="cust_act_id"/>&nbsp;</td>
						<!--<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6_prod_id"/>&nbsp;</td>-->
						<td align="center" class='inner col2'><bean:write  name="row" property="m6_order_id"/>&nbsp;</td>
						<!--<td align="left" style="font-size:9px" width="14%"></td>
						<td align="left" style="font-size:9px" width="14%"></td>
						<td align="left" style="font-size:9px" width="14%"></td>
						<td align="left" style="font-size:9px" width="14%"></td>-->
						<td align="center" class='inner col2'><bean:write  name="row" property="pre_crmorderid"/>&nbsp;</td>
						<!--<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="m6cktid"/>&nbsp;</td>-->
						<td align="center" class='inner col2'><bean:write  name="row" property="pk_charge_id"/>&nbsp;</td>
					    <!--<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="business_serial_no"/>&nbsp;</td>-->
						<td align="left" class='inner col31'><bean:write  name="row" property="billing_location_from"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="billing_location_to"/>&nbsp;</td>
						
						<td align="left" class='inner col4'><bean:write  name="row" property="cus_segment"/>&nbsp;</td>
						<%-- Disconnection Change Order Report Usage  satish starts --%>
						<td align="left" class='inner col4'><bean:write  name="row" property="desiredDueDate"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="derivedDisconnectionDate"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="isTriggerRequired"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="lineTriggered"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="triggerProcess"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="triggerProcess"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="triggerDoneBy"/>&nbsp;</td>
						<td align="left" class='inner col6'><bean:write  name="row" property="automaticTriggerError"/>&nbsp;</td>
						<%-- Disconnection Change Order Report Usage  satish ends --%>
						

					</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty  name="DisconnectChangeOrderReport">
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
                        		<div>
                        		</div>
                    		</div>
                    		<div class='ff-fill'>
                    		</div>
                		</td>
					</tr>
					<tr>
	                	<td colspan="3">
	                    	<div class='horizontal-scrolldisconnectionchange' onscroll='reposHorizontal(this);'>
	                        	<div>
	                        	</div>
	                    	</div>
	                	</td>
            		</tr>
				</table>
				<!-- End Scrolling and Freezing of Disconnection Change Report-->				
			</table>
			</div>
		</fieldset>
	</html:form>
</body>
</html>
