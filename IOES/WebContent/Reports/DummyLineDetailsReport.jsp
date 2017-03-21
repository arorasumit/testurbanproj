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
<title>Usage Based Dummy/Non Dummy Line Items Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<link href="./css/ReportHeaderFreeze.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ReportHeaderFreeze.js"></script> 
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
	
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.method.value='viewDummyLineDetailsReport';	
	
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
	myform.method.value='viewDummyLineDetailsReport';	
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

	//if(isBlankForm()){
		//return false;
	//}
	
	var searchName= '<%= AppConstants.ACTION_SEARCH %>';
	var jsData =	{
		userId:userId,
		interfaceId:interfaceId,
		actionType:searchName
		
	};		
    
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	jsonrpc.processes.populateReportUsageDetails(jsData);  
	
	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewDummyLineDetailsReport";
	showLayer();
	myForm.submit(); 
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

	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewDummyLineDetailsReport&inExcel=true";
	showLayerExcel();
	myForm.submit();
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');	
	myForm.crmAccountNo.value='' ; 
	myForm.serviceProductId.value='' ;	
	myForm.logicalsi_no.value='' ;	
	myForm.serviceTypeName.value='0';
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');	
	var crmAccountNo=myForm.crmAccountNo.value;
	var lineItemNo=myForm.serviceProductId.value;
	var logicalsi_no=myForm.logicalsi_no.value;
	var serviceName=myForm.serviceTypeName.value;

	if((crmAccountNo=='' || crmAccountNo=='0') && (lineItemNo=='' || lineItemNo=='0')
			&& (logicalsi_no=='' || logicalsi_no=='0') && (serviceName=='0')){
		
		alert("Please enter any search criteria");
		return true;
	}	
	
	return false;
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
			
		<div border="1" class="head">
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
			<tr>
				<td align="left"><span>USAGE BASED DUMMY/NON DUMMY LINE ITEMS REPORT</span></td>
				
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
			<legend><b>Search</b></legend>
				<table border="0"  align="center" style="margin-top:7px">					
					<tr>					
					
						<td align="center">Select Service Name :
							<html:select property="serviceTypeName" onchange="" >
								<logic:notEmpty name="reportsBean" property="serviceNameList">				
									<html:option value="0">----------Service Name --------</html:option>
									<html:optionsCollection name="reportsBean" property="serviceNameList" value="servicetypeid" label="servicename" ></html:optionsCollection>
								</logic:notEmpty>
								<logic:empty name="reportsBean" property="serviceNameList">				
									<option value="0">---------No Service Name-------</option>
								</logic:empty>
							</html:select>
						</td>								
						<td align="right" style="font-size:9px">LOGICAL SI NO:</td>
						<td align="left">
							<html:text  property="logicalsi_no" style="width:75px;float:left;" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
						</td>
						<td align="right" style="font-size:9px">ACCOUNT NO:</td>
						<td align="left">
							<html:text  property="crmAccountNo" style="width:75px;float:left;" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"  ></html:text>
						</td>
						<td align="right" style="font-size:9px">LINEITEM NO:</td>
						<td align="left">
							<html:text  property="serviceProductId" style="width:75px;float:left;" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();" ></html:text>
						</td>
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
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
			<legend> <b>Usage Based Dummy/Non Dummy Line Items Report</b></legend>
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
								<td class='inner col3 head1'><b>Party Name</b></td>
								<td class='inner col3 head1'><b>Crm Account Number</b></td>
								<td class='inner col3 head1'><b>Region Name</b></td>
								<td class='inner col3 head1'><b>Vertical</b></td>
									<td class='inner col3 head1'><b>
										<logic:present name="dummyLinesDetailsReportList" scope="request">
											<logic:notEmpty  name="dummyLinesDetailsReportList" scope="request"> 
												<a href="#" onclick="sortSubmit('ORDERNO')">Order No</a>
											</logic:notEmpty>
											<logic:empty  name="dummyLinesDetailsReportList" scope="request">
												Order No
											</logic:empty>
										</logic:present></b>
									</td>													
									<td class='inner col3 head1'><b>Logical Circuit Id</b></td>
									<td class='inner col3 head1'><b>Cust Logical Si Number</b></td>
									<td class='inner col3 head1'><b>Service Name</b></td>
									<td class='inner col3 head1'><b>Service Number</b></td>
									<td class='inner col3 head1'><b>Product Name</b></td>
									<td class='inner col3 head1'><b>Subproduct Name</b></td>
									<td class='inner col3 head1'><b>LineItem Number</b></td>
									<td class='inner col3 head1'><b>LineItem Name</b></td>
									<td class='inner col3 head1'><b>Fx External Id</b></td>
									<td class='inner col3 head1'><b>Fx Internal Id</b></td>
									<td class='inner col3 head1'><b>CKTID</b></td>
									<td class='inner col3 head1'><b>External ID(FX SI ID)</b></td>									
									<td class='inner col3 head1'><b>Line Status</b></td>
									<td class='inner col3 head1'><b>Disc Service Stage</b></td>									
								</tr>
							</table>
						</div>
						<div id='contentscroll' class='content1' onscroll='reposHead(this);'>
							<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tableDummyLineDetailsReport'>
								<logic:present name="dummyLinesDetailsReportList" scope="request">
									<logic:notEmpty  name="dummyLinesDetailsReportList" scope="request"> 					
										<logic:iterate id="row" name="dummyLinesDetailsReportList" indexId="recordId">
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
												<td align="left" class='inner col3'><bean:write  name="row" property="partyname"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="crmaccountno"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="regionname"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="verticalname"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="orderno"/>&nbsp;</td>												
												<td align="left" class='inner col3'><bean:write  name="row" property="logical_si_no"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="cust_logical_si_no"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="servicename"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="serviceid"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="productname"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="subproductname"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="lineitemid"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="lineitemname"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="fx_account_external_id"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="fx_account_internal_id"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="cktid"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="fx_si_id"/>&nbsp;</td>												
												<td align="left" class='inner col3'><bean:write  name="row" property="line_status"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="service_stage"/>&nbsp;</td>
										</tr>
										</logic:iterate>
									</logic:notEmpty>
								<logic:empty  name="dummyLinesDetailsReportList">
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
	                   	<div class='horizontal-scrolldummyLineReport' onscroll='reposHorizontal(this);'>
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
