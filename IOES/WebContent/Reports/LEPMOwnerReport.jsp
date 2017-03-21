<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[RPT7052013011]    Vijay    20-May-2013          Add Date validation. Records should be fetch on the basis of Date filter.     -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!-- [101010] Rampratap added from date and to date -->
<!-- [202020] Rampratap added for date validation--> 
<!-- [303030] Rampratap added for date validation--> 
<!-- [001] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.ReportsBean"%>
<%@page import="com.ibm.ioes.utilities.ApplicationFlags"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>LEPM Owner - Report</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>

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
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.method.value='viewLEPMOwnerReport';	
	//[RPT7052013011] --start
	if(isBlankForm()==false)
	{
		return ;
	}
	//[RPT7052013011] --end
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
		myform.method.value='viewLEPMOwnerReport';	
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
		// [202020]
	//[RPT7052013011] --start	
		if(!isBlankForm())
		{
			return;
		} 
	//[RPT7052013011] --end	
		else
		{
			myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewLEPMOwnerReport";
			showLayer();
			myForm.submit(); 
		}
	//[202020]END
}

function exportToExcel()
{
		var myForm=document.getElementById('searchForm');
		myForm.toExcel.value='true';
		//[303030] START
		var fromDate=myForm.copcapprovalfromdate.value;
		var toDate=myForm.copcapprovaltodate.value;
		
	var excelName= '<%= AppConstants.ACTION_EXCEL %>';
	var jsData =	{
		userId:userId,
		interfaceId:interfaceId,
		actionType:excelName
		
	};		
    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	jsonrpc.processes.populateReportUsageDetails(jsData);  
	//[RPT7052013011] --start	
		if(!isBlankForm())
		{
			return;
		} 
	//[RPT7052013011] --end	
		else
		{
			myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewLEPMOwnerReport&inExcel=true";
				showLayerExcel();
			myForm.submit();
		}
		//[303030]END

}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.orderNo.value='' ; 
	myForm.copcapprovalfromdate.value='' ;
	myForm.copcapprovaltodate.value='' ;
	myForm.fromDate.value='' ;
	myForm.toDate.value='' ;
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
//[RPT7052013011] --start			

//	var orderNo=myForm.orderNo.value;
//	var copcApproveDate=myForm.copcApproveDate.value;
	var copcapprovalfromdate=myForm.copcapprovalfromdate.value;
	var copcapprovaltodate=myForm.copcapprovaltodate.value;
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	
	if((fromDate == null & toDate == null) || ( trim(fromDate)=="" & trim(toDate) == "")){
			alert("Please enter From Date and To Date!");
			return false;
		}
    else{
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
        if((copcapprovalfromdate == null || trim(copcapprovalfromdate)== "" ) && (copcapprovaltodate != null && trim(copcapprovaltodate) != "" ))
		{
			alert("Please enter COPC Approval From Date");
			return false;
		}
		else if((copcapprovaltodate == null || trim(copcapprovaltodate)== "" ) && (copcapprovalfromdate != null && trim(copcapprovalfromdate) != "" ))
		{
			alert("Please enter COPC Approval To Date");
			return false;
		}
		if(dateCompare(copcapprovalfromdate,copcapprovaltodate)==1)
		{			
				alert("COPC Approval From Date cannot be greater than COPC Approval To Date");
				return false;
		}
	}	
//[RPT7052013011] --end			
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
				<tr ><td align="left"><span>LEPM OWNER REPORT</span> 
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
				<table border="0"  align="center" style="margin-top:7px">
					<tr>
					<td width="90px"/>
					<td width="10px"/>
							<td align="right" style="font-size:9px;">Order No:</td>
							<td align="left"><html:text  property="orderNo" styleClass="inputBorder2"  style="width:75px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text></td>
							<td width="10px"/>
						<!-- <td align="right" style="font-size:9px;">COPC Approval Date:</td>
						<td nowrap><html:text  property="copcApproveDate" styleId="copcDate" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.copcApproveDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Select Date"></a>
							</font>
						</td> -->
						<!-- [101010]START-->
						<td align="right" style="font-size:9px;">COPC Approval From Date:</td>
						<td nowrap><html:text  property="copcapprovalfromdate" styleId="copcFromDate" styleClass="inputBorder2" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.copcapprovalfromdate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Select Date"></a>
							</font>
						</td>
						<td align="right" style="font-size:9px;">COPC Approval To Date:</td>
						<td nowrap><html:text  property="copcapprovaltodate" styleId="copcToDate" styleClass="inputBorder2" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.copcapprovaltodate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Select Date"></a>
							</font>
						</td>
						<!-- [101010] END -->
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>	
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>		
						
						
						<!-- [RPT7052013011] -start -->
						
						<td align="left">
							<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
						</td>
						
					</tr>
					<tr>						
						<td width="90px"/>
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
						<td /><td /><td />
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>LEPM Owner Report</b></legend>
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
			
			<!-- Start Scrolling and Freezing of OrderClepReport-->
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
									<td class='inner col2 head1'><b>Task Number</b></td>
									<td class='inner col2 head1'><b>Owner</b></td>
									<td class='inner col3 head1'><b>User Name</b></td>
									<td class='inner col2 head1'><b>Owner Phone</b></td>
									<td class='inner col4 head1'><b>Owner Emailid</b></td>
									<td class='inner col4 head1'><b>Task Name</b></td>
									<td class='inner col4 head1'><b>Actual Start Date</b></td>
									<td class='inner col2 head1'><b>Actual End Date</b></td>
									<td class='inner col2 head1'><b>Planned Start Date</b></td>
									<td class='inner col2 head1'><b>Planned End Date</b></td>
									<td class='inner col2 head1'><b>Order Approved Date</b></td>
									<td class='inner col2 head1'><b>
										<logic:present name="LEPMOwnerList" scope="request">
											<logic:notEmpty  name="LEPMOwnerList" scope="request"> 
												<a href="#" onclick="sortSubmit('ORDERNO')">Crm Order Id</a>
											</logic:notEmpty>
											<logic:empty  name="LEPMOwnerList" scope="request">
												Crm Order Id
											</logic:empty>
										</logic:present>
									</b></td>
								</tr>
							</table>
						</div>
						<div id='contentscroll' class='content1' onscroll='reposHead(this);'style="height: ">
							<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tableLEPMOwnerReport'>
								<logic:present name="LEPMOwnerList" scope="request">
									<logic:notEmpty  name="LEPMOwnerList" scope="request"> 					
										<logic:iterate id="row" name="LEPMOwnerList" indexId="recordId">
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
												<td align="left" class='inner col2'><bean:write  name="row" property="taskNumber"/>&nbsp;</td>
												<td align="left" class='inner col2'><bean:write  name="row" property="pmName"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="userName"/>&nbsp;</td>
												<td align="left" class='inner col2'><bean:write  name="row" property="contactCell"/>&nbsp;</td>
												<td align="left" class='inner col4'><bean:write  name="row" property="emailId"/>&nbsp;</td>
												<td align="left" class='inner col4'><bean:write  name="row" property="taskName"/>&nbsp;</td>
												<td align="left" class='inner col4'><bean:write  name="row" property="pm_pro_date"/>&nbsp;</td>
												<td align="left" class='inner col2'><bean:write  name="row" property="pmApproveDate"/>&nbsp;</td>
												<td align="left" class='inner col2'><bean:write  name="row" property="pm_pro_date"/>&nbsp;</td>
												<td align="left" class='inner col2'><bean:write  name="row" property="pmapprovaldate"/>&nbsp;</td>
												<td align="left" class='inner col2'><bean:write  name="row" property="copcApproveDate"/>&nbsp;</td>
												<td align="left" class='inner col2'><bean:write  name="row" property="orderNo"/>&nbsp;</td>
												
											</tr>
										</logic:iterate>
									</logic:notEmpty>
								   <logic:empty  name="LEPMOwnerList">
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
	                   	<div class='horizontal-scrollLEPMOwnerReport' onscroll='reposHorizontal(this);'>
	                       	<div>
	                       	</div>
	                   	</div>
	               	</td>
				</tr>
			</table>
			<!-- End Scrolling and Freezing of OrderClepReport-->
		</fieldset>
	</html:form>
</body>
</html>
