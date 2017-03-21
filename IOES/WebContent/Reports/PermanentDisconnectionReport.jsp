<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Manisha Garg	17-Aug-12	00-05422		 Auto Create Permanent Disconnection Report -->
<!-- [002] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
<!-- [003]  Gunjan Singla  14-Jul-15  20141219_R2_020936  Billing efficiency drop2 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.ReportsBean"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>SR Order Status Report</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
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
	myform.method.value='viewPendingBillingPDOrderList';	
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
	myform.method.value='viewPendingBillingPDOrderList';	
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewPendingBillingPDOrderList";
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewPendingBillingPDOrderList&inExcel=true";
		showLayerExcel();
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.srNo.value='' ;
	myForm.fromDate.value='' ;
	myForm.toDate.value='' ;
	myForm.orderNo.value='' ;
	myForm.logical_SI_No.value='' ;
}
function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	
	if((fromDate == null & toDate == null) || ( trim(fromDate)=="" & trim(toDate) == "")){
		alert("Please enter From SR Date and To SR Date!");
	return false;
	
	} 		
	if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
	{
		alert("Please enter From SR Date");
		return false;
	}
	else if((toDate == null || trim(toDate)== "" ) && (fromDate != null && trim(fromDate) != "" ))
	{
		alert("Please enter To SR Date");
		return false;
	}
	
	if(dateCompare(fromDate,toDate)==1)
		{			
			alert("From SR Date cannot be greater than To SR Date");
			return false;
		}
	return true;	
}
//[003] start
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
//[003] end
path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body>
 <div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div> 
	<html:form action="/reportsAction" styleId="searchForm" method="post">
		<bean:define id="formBean" name="reportsBean"></bean:define>
		<html:hidden property="interfaceId"/>
		<html:hidden property="toExcel"/>
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
			
		<div border="1" class="head">
		<!-- [003] start-->
		 <table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>SR Order Status Report</span> 
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
			<!-- [003] end-->
		 </div>
		 
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Search</b></legend>
				<table border="0"  align="center" style="margin-top:7px">
					<tr>
					<td width="20px"/>
						<td align="left" style="font-size:12px">SR No:</td>
						<td align="left" nowrap><html:text  property="srNo" styleId="srNo" style="width:75px;" onblur="if( trim(this.value).length>0){ return ValidateTextField(this,path,'SR NO.')};" ></html:text>
						</td>
						<td width="40px"/>
						<td style="font-size:12px;">From SR Date:</td>
						<td nowrap ><html:text  property="fromDate" styleId="fromDate"styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
									<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td width="40px"/>
						<td style="font-size:12px;">To SR Date:</td>
						<td nowrap><html:text  property="toDate" styleId="toDate" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						</tr> 
						<tr>
						<td width="30px"/>
						<td align="left" style="font-size:12px">Order No:</td>
						<td align="left">
							<html:text  property="orderNo" style="width:75px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" 
							maxlength="18" ></html:text>
						</td>
						<td width="30px"/>
						<td  style="font-size:12px">Logical SI No:</td>
						<td>
							<html:text  property="logical_SI_No" style="width:75px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" 
							maxlength="18" ></html:text>
						</td>
						<td width="40">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
					
						<td  width="90">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
					
							<td align="left" width="27%"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>SR Order Status Report</b></legend>
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
			
			<div class="scrollWithAutoScroll1" class="head"  style="height:325px;width:99%; vertical-align: top" >
			<table style="display:block;overflow:auto vertical-align: top;" width="99%" border="1" align="center" class="tab2">
				<tr>
				    <td align="center" style="font-size:9px"><b>SR No</b></td>
					<td align="center" style="font-size:9px"><b>Order No</b></td>
					<td align="center" style="font-size:9px"><b>LSI No</b></td>
					<td align="center" style="font-size:9px"><b>Order Status</b></td>
					<td align="center" style="font-size:9px"><b>Remarks</b></td>
					<td align="center" style="font-size:9px"><b>SR Raised By</b></td>
				    <td align="center" style="font-size:9px"><b>SR Creation Date</b></td>
					<td align="center" style="font-size:9px"><b>Date Of Disconnection</b></td>
				</tr>
				<logic:present name="pendingOrderList" scope="request">
					<logic:notEmpty  name="pendingOrderList" scope="request"> 					
						<logic:iterate id="row" name="pendingOrderList" indexId="recordId">
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
								<td align="left" style="font-size:9px" width="8%"><bean:write  name="row" property="srno"/></td>
								<td align="left" style="font-size:9px" width="8%"><bean:write  name="row" property="orderNo"/></td>
		                        <td align="left" style="font-size:9px" width="8%"><bean:write  name="row" property="logicalSINo"/></td>
		                        <td align="left" style="font-size:9px" width="10%"><bean:write  name="row" property="orderStatus"/></td>
		                    	<td align="left" style="font-size:9px" width="40%"><bean:write  name="row" property="neworder_remarks"/></td>
								<td align="left" style="font-size:9px" width="18%"><bean:write  name="row" property="productName"/></td>
		                    	<td align="left" style="font-size:9px" width="8%"><bean:write  name="row" property="srDate"/></td>
		                    	<td align="left" style="font-size:9px" width="8%"><bean:write  name="row" property="disdate"/></td>								
							</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty  name="pendingOrderList">
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
