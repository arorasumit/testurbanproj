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
<title>Non Migrated Approved/Unapproved New Order Details</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ReportHeaderFreeze.js"></script>
<link href="./css/ReportHeaderFreeze.css" type="text/css" rel="stylesheet">
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
	myform.method.value='viewNonMigAppUnappNewOrderDetails';	
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
	myform.method.value='viewNonMigAppUnappNewOrderDetails';	
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewNonMigAppUnappNewOrderDetails";
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewNonMigAppUnappNewOrderDetails&inExcel=true";
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
	myForm.servicename.value='' ;	
	myForm.ordermonth.value='' ;	
	myForm.approvalType.value='';	
	myForm.orderyear.value='' ;
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	var ordermonth=myForm.ordermonth.value;	
	var orderyear=myForm.orderyear.value;
	var toOrderNo=myForm.toOrderNo.value;
	var fromOrderNo=myForm.fromOrderNo.value;		
	
	if((ordermonth == null || trim(ordermonth)== "" ) && (orderyear == null || trim(orderyear)== "" ))
	{
		alert("Please enter ordermonth and orderyear");
		return false;
	}
	else if((ordermonth == null || trim(ordermonth)== "" ) && (orderyear != null && trim(orderyear) != ""))
	{
			alert("Please enter ordermonth");
			return false;
	}
	else if((orderyear == null || trim(orderyear)== "" ) && (ordermonth != null && trim(ordermonth) != "" ))
	{
			alert("Please enter orderyear");
			return false;
	}	
	 
	if((fromOrderNo == null || trim(fromOrderNo)== "" ) && (toOrderNo != null && trim(toOrderNo) != "" ))
	{
		alert("Please enter From Order No");
		return false;
	}
	else if((toOrderNo == null || trim(toOrderNo)== "" ) && (fromOrderNo != null && trim(fromOrderNo) != "" ))
	{
		alert("Please enter To Order No");
		return false;
	}
	
	if(toOrderNo < fromOrderNo)
		{			
			alert("From Order No cannot be greater than To Order No");
			return false;
		}
		
	return true;	
}
function resetFilterCriterion(){
	var myForm=document.getElementById('searchForm');
	var orderType=myForm.orderType.value;
	var fromOrderNo=myForm.fromOrderNo.value;
	var toOrderNo=myForm.toOrderNo.value;
	var servicename=myForm.servicename.value;
	var ordermonth=myForm.ordermonth.value;
	var approvalType=myForm.approvalType.value;
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
		<html:hidden property="interfaceId"/>
		<html:hidden property="isDumpAvailable"/>
		<html:hidden property="dumpFileName"/>		
		<html:hidden property="reportProcessForLimit"/>
		<html:hidden property="reportCurrentCount"/>
		<input type="hidden" name="fromPageSubmit" value="1"/>
		<input type="hidden" name="orderType" value="New"/>
		<input type="hidden" name="method" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="left">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> 
			
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>NON MIGRATED APPROVED/UNAPPROVED NEW ORDER DETAILS</span> 
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
						<td align="right" style="font-size:9px">APPROVAL:</td>
						<td align="left" nowrap>
							<html:select property="approvalType" >
							<html:option value="">SELECT</html:option>
							<html:option value="APPROVED">APPROVED</html:option>
							<html:option value="UNAPPROVED">UNAPPROVED</html:option>
							</html:select>
						</td>						
						<td align="right" style="font-size:9px;">FROM ORDER NO:</td>
						<td align="left"><html:text  property="fromOrderNo" value="" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text></td>
						<td align="right" style="font-size:9px">TO ORDER NO:</td>
						<td align="left">
							<html:text  property="toOrderNo" value="" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
						</td>
						<td width="10px"/>
						<td width="10px"/>						
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
					</tr>
					<tr>
						<td align="right" style="font-size:9px"> Order Month:</td>	
						<td align="left" nowrap>
							<html:select  property="ordermonth" styleClass="inputBorder1" style="width:90px;float:left;"  >
								<html:option value="">SELECT</html:option>
								<html:option value="1">January</html:option>
							    <html:option value="2">February</html:option>
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
						<td align="right" style="font-size:9px">Order Year(YYYY):</td>
						<td align="left">
							<html:text  property="orderyear" maxlength="4" styleClass="inputBorder1" style="width:90px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" ></html:text>
						</td>											
						<!-- td width="10px"/-->
						<td align="right" style="font-size:9px">Service Name:</td>
						<td align="left">
							<html:text  property="servicename" maxlength="18" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Service Name')};" ></html:text>
						</td>
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td align="left">
							<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
						</td>
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Non Migrated Approved/Unapproved New Order Details</b></legend>
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
			
			<!-- Start Scrolling and Freezing of nonMigAppUnAppNewOrderDetailsReport-->	
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
									<td class='inner col3 head1'><b>Party Number</b></td>
									<td class='inner col3 head1'><b>Account Number</b></td>
									<td class='inner col3 head1'><b>Party Name</b></td>
									<td class='inner col3 head1'><b>Vertical</b></td>
									<td class='inner col3 head1'><b>Region</b></td>										
									<td class='inner col3 head1'><b>
					<logic:present name="nonMigAppUnappNewOrderDetailsList" scope="request">
							<logic:notEmpty  name="nonMigAppUnappNewOrderDetailsList" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Order Number</a>
							</logic:notEmpty>
							<logic:empty  name="nonMigAppUnappNewOrderDetailsList" scope="request">
								Order Number
							</logic:empty>
						</logic:present>
						</b>
					</td>
									<td class='inner col3 head1'><b>Order Type</b></td>
									<td class='inner col3 head1'><b>M6 Order No</b></td>
									<td class='inner col3 head1'><b>Service Name</b></td>
									<td class='inner col3 head1'><b>Cust Logical Si Id</b></td>
									<td class='inner col3 head1'><b>Line Name</b></td>
									<td class='inner col3 head1'><b>Service Order Type</b></td>
									<td class='inner col3 head1'><b>Circuit Id</b></td>
									<td class='inner col3 head1'><b>Bandwidth</b></td>
									<td class='inner col3 head1'><b>Billling Bandwidth</b></td>
									<td class='inner col3 head1'><b>Bill Uom</b></td>
									<td class='inner col3 head1'><b>Kms Distance</b></td>	
									<td class='inner col3 head1'><b>Ratio</b></td>	
									<td class='inner col3 head1'><b>Location From</b></td>	
									<td class='inner col3 head1'><b>Location To</b></td>										
									<td class='inner col3 head1'><b>Pri Location</b></td>
									<td class='inner col3 head1'><b>Sec Location</b></td>					
									<td class='inner col3 head1'><b>Product</b></td>
									<td class='inner col3 head1'><b>Subproduct</b></td>					
									<td class='inner col3 head1'><b>Legal Entity</b></td>
									<td class='inner col3 head1'><b>Licence Company</b></td>
									<td class='inner col3 head1'><b>Currency</b></td>
									<td class='inner col3 head1'><b>Credit Period</b></td>
									<td class='inner col3 head1'><b>Bill Type</b></td>
									<td class='inner col3 head1'><b>Bill Format</b></td>
									<td class='inner col3 head1'><b>Billing Level</b></td>
									<td class='inner col3 head1'><b>Billing Level Number</b></td>
									<td class='inner col3 head1'><b>Taxation</b></td>
									<td class='inner col3 head1'><b>Hardware Type</b></td>
									<td class='inner col3 head1'><b>Hardware Flag</b></td>
									<td class='inner col3 head1'><b>Store</b></td>
									<td class='inner col3 head1'><b>Type Of Sale</b></td>
									<td class='inner col3 head1'><b>Bill Period</b></td>
									<td class='inner col3 head1'><b>Billing Mode</b></td>
									<td class='inner col3 head1'><b>Po Number</b></td>					
									<td class='inner col3 head1'><b>Po Date</b></td>
									<td class='inner col3 head1'><b>Period In Month</b></td>
									<td class='inner col3 head1'><b>Tot Po Amt</b></td>
									<td class='inner col3 head1'><b>Cust Po Receive Date</b></td>
									<td class='inner col3 head1'><b>Cust Po Number</b></td>
									<td class='inner col3 head1'><b>Cust Po Date</b></td>
									<td class='inner col3 head1'><b>Cust Tot Po Amt</b></td>
									<td class='inner col3 head1'><b>Charge Type</b></td>
									<td class='inner col3 head1'><b>Charge Name</b></td>
									<td class='inner col3 head1'><b>Charge Start Date</b></td>
									<td class='inner col3 head1'><b>Inv Amt</b></td>
									<td class='inner col3 head1'><b>Amt</b></td>
									<td class='inner col3 head1'><b>Annotation</b></td>					
									<td class='inner col3 head1'><b>Loc Date</b></td>
									<td class='inner col3 head1'><b>Loc Number</b></td>
									<td class='inner col3 head1'><b>Billing Trig Date</b></td>					
									<td class='inner col3 head1'><b>Bill Trg Create Date</b></td>
									<td class='inner col3 head1'><b>Pm Prov Date</b></td>
									<td class='inner col3 head1'><b>Billing Trig Flag</b></td>
									<td class='inner col3 head1'><b>Token No</b></td>
									<td class='inner col3 head1'><b>Fx Sd Chg Status</b></td>
									<td class='inner col3 head1'><b>Fx Status</b></td>					
									<td class='inner col3 head1'><b>Business Serial No</b></td>
									<td class='inner col3 head1'><b>Opms Account Id</b></td>					
									<td class='inner col3 head1'><b>Lineitemnumber</b></td>
									<td class='inner col3 head1'><b>Order Stage</b></td>
				</tr>
							</table>
						</div>
						<div id='contentscroll' class='content1' onscroll='reposHead(this);'style="height: ">
							<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablenonMigAppUnappNewOrderDetailsReport'>		
				<logic:present name="nonMigAppUnappNewOrderDetailsList" scope="request">
					<logic:notEmpty  name="nonMigAppUnappNewOrderDetailsList" scope="request"> 					
						<logic:iterate id="row" name="nonMigAppUnappNewOrderDetailsList" indexId="recordId">
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
							
											<td align="left" class='inner col3'><bean:write  name="row" property="party_no"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="crmAccountNoString"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="partyName"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="verticalDetails"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="regionName"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="orderNumber"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="orderType"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="m6OrderNo2"/>&nbsp;</td>	
											<td align="left" class='inner col3'><bean:write  name="row" property="serviceName"/>&nbsp;</td>	
											<td align="left" class='inner col3'><bean:write  name="row" property="customer_logicalSINumber"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="serviceDetDescription"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="serviceOrderType"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="logicalCircuitId"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="bandwaidth"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="billing_bandwidth"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="billUom"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="kmsDistance"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="ratio"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="fromLocation"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="toLocation"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="primaryLocation"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="secondaryLocation"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="productName"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="subProductName"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="entity"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="lcompanyname"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="currencyName"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="creditPeriodName"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="billingTypeName"/>&nbsp;</td><!-- Bill Type -->
											<td align="left" class='inner col3'><bean:write  name="row" property="billingFormatName"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="billingLevelName"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="billingLevelNo"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="taxation"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="hardwaretypeName"/>&nbsp;</td>							
											<td align="left" class='inner col3'><bean:write  name="row" property="hardwareFlag"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="storename"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="saleType"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="billPeriod"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="billingMode"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="ponum"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="poDate"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="totalPoAmt"/>&nbsp;</td><!--Total Amount-->
											<td align="left" class='inner col3'><bean:write  name="row" property="poRecieveDate"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="custPoDetailNo"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="custPoDate"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="poAmount"/>&nbsp;</td><!--Cust Tot Po Amt-->
								   			<td align="left" class='inner col3'><bean:write  name="row" property="chargeTypeName"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="chargeName"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="startDate"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="chargeAmount_String"/>&nbsp;</td><!--Inv Amt-->
											<td align="left" class='inner col3'><bean:write  name="row" property="lineItemAmount"/>&nbsp;</td><!-- Amt-->
											<td align="left" class='inner col3'><bean:write  name="row" property="chargeAnnotation"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="locDate"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="LOC_No"/>&nbsp;</td>							
											<td align="left" class='inner col3'><bean:write  name="row" property="billingTriggerDate"/>&nbsp;</td>
											<td align="left" class='inner col3'>&nbsp;</td><!-- Bill Trg Create Date -->							
											<td align="left" class='inner col3'><bean:write  name="row" property="pmApproveDate"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="billing_Trigger_Flag"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="tokenNO"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="fx_St_Chg_Status"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="fxStatus"/>&nbsp;</td>
											<td align="left" class='inner col3'>&nbsp;</td><!-- Business Serial No	-->
											<td align="left" class='inner col3'>&nbsp;</td><!-- Opms Account Id -->	
											<td align="left" class='inner col3'><bean:write  name="row" property="orderLineNumber"/>&nbsp;</td><!-- Order Line Id-->
											<td align="left" class='inner col3'><bean:write  name="row" property="orderStage"/>&nbsp;</td><!-- Order Line Id-->
							
				
					
							
								
							</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty  name="nonMigAppUnappNewOrderDetailsList">
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
                   	<div class='horizontal-scrollNonMigAppUnappNewOrderDetailsReport' onscroll='reposHorizontal(this);'>
                       	<div>
                       	</div>
                   	</div>
               	</td>
			</tr>
		</table>
		<!-- End Scrolling and Freezing of nonMigAppUnAppNewOrderDetailsReport-->		
		</fieldset>
	</html:form>
</body>
</html>
