<!-- [001]      gunjan singla                        added jsonrpc populatereportusagedetails method -->
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
<title>Deleted Hardware Line Report</title>
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
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.method.value='viewHardwareCancelReport';	
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
	myform.method.value='viewHardwareCancelReport';	
	myform.submit();
}

function searchClick()
{
	myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
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
	} else {
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewHardwareCancelReport";
		myForm.submit(); 
	}
}

function exportToExcel()
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='true';
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	if(!isBlankForm()){
		
		return;
	} else {
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewHardwareCancelReport&inExcel=true";
		myForm.submit();	
	}
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	
	if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
	{
		alert("Please enter From Order Date ");
		return false;
	}
	else if((toDate == null || trim(toDate)== "" ) && (fromDate != null && trim(fromDate) != "" ))
	{
		alert("Please enter To Order Date ");
		return false;
	}
	if(dateCompare(fromDate,toDate)==1)
	{			
		alert("From Order Date cannot be greater than To Order  Date");
		return false;
	}
	return true;		
}

function showPage(requestId)
{
	var srno;
	srno=Number(requestId);
	var path = "<%=request.getContextPath()%>/reportsAction.do?method=fetchRequestHistory&requestID="+srno;
	window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:300px");
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.fromDate.value='' ; 
	myForm.toDate.value='' ;
	myForm.srNo.value='' ;
	myForm.logicalSINo.value='' ;
	myForm.crmAccountNo.value='' ;
	myForm.accountName.value='' ;
	myForm.searchLineItemNo.value='' ;
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
<body onload="javascript:maxLimitChecker(document.getElementById('searchForm'),'<%=ApplicationFlags.ReportsExcelMaxSize%>','<%=Messages.getMessageValue("ReportsExcelMaxSizeMessage") %>');">
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
			<tr align="left">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> 
			
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>Deleted Hardware Line Report</span> 
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
		
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Search</b></legend>
			<table border="0"  align="center" style="margin-top:7px" width="90%">
				<tr>
					
					<td align="right" style="font-size:9px;">From date</td>
					<td nowrap><html:text  property="fromDate" styleId="fromDate" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
					    <font size="1">
							<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
						</font>
					</td> 
					
					<td align="right" style="font-size:9px;">To date</td>
					<td nowrap><html:text  property="toDate" styleId="toDate" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
					    <font size="1">
							<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
						</font>
					</td>
					
					<td align="right" style="font-size:9px"> Request Id:</td>
					<td align="left" nowrap>
						<html:text  property="srNo" styleId="srNo" styleClass="inputBorder1" style="width:70px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
					</td> 
					
					<td align="right" style="font-size:9px"> LSI No:</td>
					<td align="left" nowrap>
						<html:text  property="logicalSINo" styleId="logicalSINo" styleClass="inputBorder1" style="width:70px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
					</td>
					<td></td>
					
				</tr>
				<tr>
					
					<td align="right" style="font-size:9px"> Account No:</td>
					<td align="left" nowrap>
						<html:text  property="crmAccountNo" styleId="crmAccountNo" styleClass="inputBorder1" style="width:70px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
					</td>
					
					<td align="right" style="font-size:9px"> Account Name:</td>
					<td align="left" nowrap>
						<html:text  property="accountName" styleId="accountName" styleClass="inputBorder1" style="width:70px;float:left;" ></html:text>
					</td>
					
					<td align="right" style="font-size:9px"> Line Item No:</td>
					<td align="left" nowrap>
						<html:text  property="searchLineItemNo" styleId="searchLineItemNo" styleClass="inputBorder1" style="width:70px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
					</td>
				
					<td align="right">
						<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
					</td>
					
					<td align="right" valign="top">
						<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
					</td>						
						
					<td align="right"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel"  onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
				
				</tr>
			</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Cancelled Hardware Line Report</b></legend>
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
									<td align="center" class='inner col31 head1'  ><b> 	
										<logic:present name="cancelHardwareLineItemList" scope="request">
											<logic:notEmpty  name="cancelHardwareLineItemList" scope="request"> 
												<a href="#" onclick="sortSubmit('REQUESTID')">RequestId</a>
											</logic:notEmpty>
											<logic:empty  name="cancelHardwareLineItemList" scope="request">
												RequestId
											</logic:empty>
										</logic:present>
									</b></td>
									<td align="center" class='inner col31 head1'><b>Account ID</b></td>
									<td align="center" class='inner col31 head1'><b>Account Name</b></td>
									<td align="center" class='inner col31 head1'><b>LineItem No</b></td>
									<td align="center" class='inner col31 head1'><b>LineItem Name</b></td>
									<td align="center" class='inner col31 head1'><b>OrderNo</b></td>
									<td align="center" class='inner col31 head1'><b>Service Name</b></td>
									<td align="center" class='inner col31 head1'><b>LSI No </b></td>
									<td align="center" class='inner col31 head1'><b>M6 OrderNo</b></td>
									<td align="center" class='inner col31 head1'><b>Remarks</b></td>
									<td align="center" class='inner col31 head1'><b>Requested by</b></td>
									<td align="center" class='inner col31 head1'><b>User Id</b></td>
									<td align="center" class='inner col31 head1'><b>Requested Date</b></td>
								</tr>
							</table>
						</div>                		 	                		
		          		<div id='contentscroll' class='content1' onscroll='reposHead(this);'>
		               		<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>
								<logic:present name="cancelHardwareLineItemList" scope="request">
									<logic:notEmpty  name="cancelHardwareLineItemList" scope="request"> 					
										<logic:iterate id="row" name="cancelHardwareLineItemList" indexId="recordId">
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
												<td align="center" class='inner col31'><a href="#" onclick="showPage('<bean:write  name="row" property="srno"/>')"><bean:write  name="row" property="srno"/></a></td>
												<td align="center" class='inner col31'><bean:write  name="row" property="accountID"/>&nbsp;</td>
												<td align="center" class='inner col31'><bean:write  name="row" property="accountName"/>&nbsp;</td>
												<td align="center" class='inner col31'><bean:write  name="row" property="orderLineNumber"/>&nbsp;</td>
												<td align="center" class='inner col31'><bean:write  name="row" property="lineItemName"/>&nbsp;</td>
												<td align="center" class='inner col31'><bean:write  name="row" property="orderNo"/>&nbsp;</td>
												<td align="center" class='inner col31'><bean:write  name="row" property="serviceName"/>&nbsp;</td>
												<td align="center" class='inner col31'><bean:write  name="row" property="logicalSINo"/>&nbsp;</td>
												<td align="center" class='inner col31'><bean:write  name="row" property="m6OrderNo"/>&nbsp;</td>
												<td align="center" class='inner col31'><bean:write  name="row" property="remarks"/>&nbsp;</td>
												<td align="center" class='inner col31'><bean:write  name="row" property="createdBy"/>&nbsp;</td>
												<td align="center" class='inner col31'><bean:write  name="row" property="userId"/>&nbsp;</td>
												<td align="center" class='inner col31'><bean:write  name="row" property="srDate"/>&nbsp;</td>
											</tr>
										</logic:iterate>
									</logic:notEmpty>
									<logic:empty  name="cancelHardwareLineItemList">
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
                    	<div class='horizontal-scrollcancelHardwareLinereport' onscroll='reposHorizontal(this);'>
                        	<div>
                        	</div>
                    	</div>
                	</td>
           		</tr>
			</table>
		</fieldset>
</html:form>
</body>
</html>