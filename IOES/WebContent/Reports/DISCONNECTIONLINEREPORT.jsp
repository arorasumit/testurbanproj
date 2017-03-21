<!-- [001] Gunjan Singla added jsonrpc method populatereportusagedetails -->
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
<title>Disconnection Line Report</title>
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
	myform.method.value='viewDisconnectionLineReport';	
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
	myform.method.value='viewDisconnectionLineReport';	
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewDisconnectionLineReport";
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewDisconnectionLineReport&inExcel=true";
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
	myForm.ordermonth.value='' ;
	myForm.verticalDetails.value='' ;
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	
	if((fromDate == null & toDate == null) || ( trim(fromDate)=="" & trim(toDate) == "")){
			alert("Please enter From Order Date and To Order Date!");
			return false;
		}
    else{
        
        if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
		{
			alert("Please enter From Order Date");
			return false;
		}
		else if((toDate == null || trim(toDate)== "" ) && (fromDate != null && trim(fromDate) != "" ))
		{
			alert("Please enter To Order Date");
			return false;
		}
		if(dateCompare(fromDate,toDate)==1)
		{			
				alert("From Order Date cannot be greater than To Order Date");
				return false;
		}
	}	
		
		return true;
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
			<tr align="right">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
		<div border="1" class="head"> 
			
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>DISCONNECTION LINE REPORT</span> 
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
				
						<td width="10px"/>
						<td align="right" style="font-size:9px;">From Order Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="datefrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">To Order Date:</td>
						<td nowrap><html:text  property="toDate" styleId="dateto" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
							</td>
		<!--					</tr>
		  		Comment. becoz this filter is not using 
					<tr>
						<td width="10px"/>
						<td align="right" style="font-size:9px">Service Name:</td>
						<td align="left">
							<html:text  property="servicename" styleClass="inputBorder1" style="width:75px;float:left;" maxlength="18" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Service Name')};" ></html:text>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px">Service Order Type:</td>
						<td align="left">
							<html:text  property="ordersubtype" styleClass="inputBorder1" style="width:75px;float:left;"
							maxlength="18" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Service Order Type')};" ></html:text>
						</td>
					 </tr>

						<tr> 					--> 
						<!--  Comment. becoz this filter is not using
						<td width="50px"/>
						<td align="right" style="font-size:9px"> Order Month:</td>
						
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
						<td width="50px"/>
						<td align="right" style="font-size:9px"> Vertical:</td>
						<td align="left" nowrap>
							<html:text  property="verticalDetails" styleClass="inputBorder1" style="width:90px;float:left;" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Vertical')};"></html:text>
						</td>
					-->						
						<td width="60px"/>
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
						<td width="1%"/>
						<td align="left" >
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td align="left">
							<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
						</td>
				  </tr> 
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Disconnection Line Report</b></legend>
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
			
			<!-- start Scrolling and Freezing of Disconnection Line Report-->
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
					<td align="center" class='inner col2 head1'><b>Party No </b></td>
					<td align="center" class='inner col2 head1'><b>Account No </b></td>
					<td align="center" class='inner col31 head1'><b>Customer Name </b></td>
					<td align="center" class='inner col2 head1'><b>Vertical </b></td>
					<td align="center" class='inner col2 head1'><b>Region </b></td>
					<td align="center" class='inner col2 head1'><b> 	
					<logic:present name="DisconnectionLineReport" scope="request">
							<logic:notEmpty  name="DisconnectionLineReport" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Order No</a>
							</logic:notEmpty>
							<logic:empty  name="DisconnectionLineReport" scope="request">
								Order No
							</logic:empty>
						</logic:present>
						</b></td>
					<td align="center" class='inner col2 head1'><b> Order Type</b></td>
					<td align="center" class='inner col2 head1'><b>M6 Order No </b></td>
					<td align="center" class='inner col4 head1'><b>Service Name </b></td>
					<td align="center" class='inner col2 head1'><b>Cust Logical SI No </b></td>
					<td align="center" class='inner col4 head1'><b> Line Name</b></td>
					<td align="center" class='inner col2 head1'><b>Sub Type </b></td>
					<td align="center" class='inner col2 head1'><b>Circuit Id </b></td>
					<td align="center" class='inner col2 head1'><b>Bandwidth </b></td>
					<td align="center" class='inner col2 head1'><b>Billing Bandwidth </b></td>
					<td align="center" class='inner col2 head1'><b>Billing UOM </b></td>
					<td align="center" class='inner col1 head1'><b>KMS Distance </b></td>
					<td align="center" class='inner col1 head1'><b>RATIO </b></td>
					<td align="center" class='inner col51 head1'><b>Location From </b></td>
					<td align="center" class='inner col51 head1'><b>Location To </b></td>
					<td align="center" class='inner col51 head1'><b>Pri Location </b></td>
					<td align="center" class='inner col51 head1'><b>Sec Location </b></td>
					<td align="center" class='inner col31 head1'><b>Product </b></td>
					<td align="center" class='inner col31 head1'><b>Sub Product </b></td>
					<td align="center" class='inner col31 head1'><b>Legal Entity </b></td>
					<td align="center" class='inner col31 head1'><b>Licence Company </b></td>
					<td align="center" class='inner col31 head1'><b>Currency </b></td>
					<td align="center" class='inner col2 head1'><b>Credit period </b></td>
					<td align="center" class='inner col31 head1'><b>Bill Type </b></td>
					<td align="center" class='inner col31 head1'><b>Bill Format </b></td>
					<td align="center" class='inner col31 head1'><b>Bill Level </b></td>
					<td align="center" class='inner col2 head1'><b>Bill Level No </b></td>
					<td align="center" class='inner col2 head1'><b>Taxation </b></td>
					<td align="center" class='inner col1 head1'><b>Hardware Type </b></td>
					<td align="center" class='inner col1 head1'><b>H/W Flag </b></td>
					<td align="center" class='inner col2 head1'><b>Store </b></td>
					<td align="center" class='inner col1 head1'><b>Type Of Sale </b></td>
					<td align="center" class='inner col2 head1'><b>Bill Period </b></td>
					<td align="center" class='inner col2 head1'><b>Bill Mode </b></td>
					<td align="center" class='inner col2 head1'><b>Po No </b></td>
					<td align="center" class='inner col31 head1'><b>PO Date </b></td>
					<td align="center" class='inner col2 head1'><b>Period in month </b></td>
					<td align="center" class='inner col2 head1'><b>Total PO Amt </b></td>
					<td align="center" class='inner col31 head1'><b>Cust PO Rec Date </b></td>
					<td align="center" class='inner col31 head1'><b>Cust PO No </b></td>
					<td align="center" class='inner col31 head1'><b>Cust PO Date </b></td>
					<td align="center" class='inner col2 head1'><b> Cust PoTotal Amt</b></td>
					<td align="center" class='inner col2 head1'><b> Charge Type</b></td>
					<td align="center" class='inner col4 head1'><b>Charge Name </b></td>
					<td align="center" class='inner col31 head1'><b>Charge Start Date </b></td>
					<td align="center" class='inner col2 head1'><b>Inv Amt </b></td>
					<td align="center" class='inner col2 head1'><b>Amt </b></td>
					<td align="center" class='inner col4 head1'><b>Annotation </b></td>
					<td align="center" class='inner col2 head1'><b>Loc Date </b></td>
					<td align="center" class='inner col2 head1'><b>Loc No </b></td>
					<td align="center" class='inner col31 head1'><b>Billing Trigger Date </b></td>
					<td align="center" class='inner col31 head1'><b>Billing Trigger Create Date </b></td>
					<td align="center" class='inner col31 head1'><b>PM Provisioning Date </b></td>
					<td align="center" class='inner col1 head1'><b>Billing Trigger Flag </b></td>
					<td align="center" class='inner col2 head1'><b>Token No </b></td>
					<td align="center" class='inner col1 head1'><b>Fx Charge Start Status </b></td>
					<td align="center" class='inner col2 head1'><b>Fx Status </b></td>
					<td align="center" class='inner col1 head1'><b>Business Serial No </b></td>
					<td align="center" class='inner col1 head1'><b>Opms Act Id </b></td>
					<td align="center" class='inner col2 head1'><b>Serviceno </b></td>
					<td align="center" class='inner col2 head1'><b>Pre Crm Order Id </b></td>
					<td align="center" class='inner col31 head1'><b>Disconnection Remarks </b></td>
					<td align="center" class='inner col31 head1'><b>Order Stage </b></td>
					<td align="center" class='inner col31 head1'><b>Service Stage </b></td>
					<td align="center" class='inner col31 head1'><b>New Order Remarks </b></td>
					<td align="center" class='inner col31 head1'><b>Copc Date </b></td>
					<td align="center" class='inner col31 head1'><b>Standard Remarks </b></td>
					<td align="center" class='inner col31 head1'><b>Order Date </b></td>
					<td align="center" class='inner col31 head1'><b>Old LSI </b></td>
					<td align="center" class='inner col31 head1'><b>Child Account Number  </b></td>
					<td align="center" class='inner col31 head1'><b>Order Month </b></td>
					<td align="center" class='inner col31 head1'><b>CKT ID </b></td>
					<td align="center" class='inner col31 head1'><b>UOM </b></td>
					</tr>
						</table>
				</div>                		 	                		
	          <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	               <table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>
				<logic:present name="DisconnectionLineReport" scope="request">
					<logic:notEmpty  name="DisconnectionLineReport" scope="request"> 					
						<logic:iterate id="row" name="DisconnectionLineReport" indexId="recordId">
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
						<td align="center" class='inner col2'><bean:write  name="row" property="partyNo"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="accountID"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="cust_name"/>&nbsp;</td>
					    <td align="left" class='inner col2'><bean:write  name="row" property="verticalDetails"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="regionName"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="orderNumber"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="order_type"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="m6OrderNo"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="serviceName"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="customer_logicalSINumber"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="linename"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="ordersubtype"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="ckt_id"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="bandwaidth"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="billing_bandwidth"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="billing_uom"/>&nbsp;</td>
						<td align="center" class='inner col1'><bean:write  name="row" property="distance"/>&nbsp;</td>
						<td align="center" class='inner col1'><bean:write  name="row" property="ratio"/>&nbsp;</td>
						<td align="left" class='inner col51'><bean:write  name="row" property="location_from"/>&nbsp;</td>
						<td align="left" class='inner col51'><bean:write  name="row" property="location_to"/>&nbsp;</td>
						<td align="left" class='inner col51'><bean:write  name="row" property="primarylocation"/>&nbsp;</td>
						<td align="left" class='inner col51'><bean:write  name="row" property="seclocation"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="productName"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="subProductName"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="entity"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="lcompanyname"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="currencyName"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="creditPeriod"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="billingTypeName"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="billingFormatName"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="billingLevel"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="billingLevelNo"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="taxation"/>&nbsp;</td>
						<td align="left" class='inner col1'><bean:write  name="row" property="hardwaretypeName"/>&nbsp;</td>
						<td align="left" class='inner col1'><bean:write  name="row" property="hardware_flag"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="storename"/>&nbsp;</td>
						<td align="left" class='inner col1'><bean:write  name="row" property="saleType"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="bill_period"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="billingMode"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="poNumber"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="poDate"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="totalAmountIncludingCurrent"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="poReceiveDate"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="custPoDetailNo"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="custPoDate"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="cust_total_poamt"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="chargeTypeName"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="chargeName"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="chargeEndDate"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="frequencyAmount"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="chargeAmount"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="annitation"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="locDate"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="locno"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="billing_trigger_date"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="billingtrigger_createdate"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="pmapprovaldate"/>&nbsp;</td>
						<td align="left" class='inner col1'><bean:write  name="row" property="billing_Trigger_Flag"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="tokenno"/>&nbsp;</td>
						<td align="left" class='inner col1'><bean:write  name="row" property="fx_sd_status"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="fx_status"/>&nbsp;</td>
						<td align="center" class='inner col1'><bean:write  name="row" property="business_serial_n"/>&nbsp;</td>
						<td align="center" class='inner col1'><bean:write  name="row" property="opms_Account_Id"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="serviceId"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="pre_crmorderid"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="disconnection_remarks"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="stageName"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="serviceStage"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="neworder_remarks"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="copcapprovaldate"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="standard_reason"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="orderDate"/>&nbsp;</td>						
						 <td align="center" class='inner col31'><bean:write  name="row" property="oldLsi"/>&nbsp;</td>						
						<td align="center" class='inner col31'><bean:write  name="row" property="child_account_number"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="ordermonth"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="ckt_id"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="uom"/>&nbsp;</td>				
					

					</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty  name="DisconnectionLineReport">
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
	                    	<div class='horizontal-scrolldisconnectionline' onscroll='reposHorizontal(this);'>
	                        	<div>
	                        	</div>
	                    	</div>
	                	</td>
            		</tr>
				</table>
				<!-- End Scrolling and Freezing of Disconnection Line Report-->
			</table>
			</div>
		</fieldset>
	</html:form>
</body>
</html>
