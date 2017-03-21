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
<html>
<head>
<title>Billing Work Queue - Report</title>
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
var isUsage ="<%= request.getAttribute("isUsage") %>";
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
	//myform.method.value='viewBillingWorkQueueReport&usage='+isUsage;
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewBillingWorkQueueReport&usage="+isUsage;
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
	
	//myform.method.value='viewBillingWorkQueueReport&usage='+isUsage;	
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewBillingWorkQueueReport&usage="+isUsage;
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
	
	var isHard = myform=document.getElementById('isHard');
	if(isHard.checked==true){
		document.getElementById('isHardware').value="1";
	}
	else{
		document.getElementById('isHardware').value="0";
	}
	if(!isBlankForm()){
		
		return;
	} 
	else {
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewBillingWorkQueueReport&usage="+isUsage;
		showLayer();
		myForm.submit(); 
	}
}

function exportToExcel()
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='true';

	 if(!isBlankForm()){
		return;
	} 
	else { 
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewBillingWorkQueueReport&inExcel=true&usage="+isUsage;
		showLayerExcel();
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	document.getElementById('orderType').value="";
	//myForm.orderType.value='' ; 
	myForm.fromDate.value='' ;	
	myForm.toDate.value='' ;	
	myForm.fromOrderNo.value=0 ;	
	myForm.toOrderNo.value=0 ;
	document.getElementById('orderStageDescription').value="";	
	document.getElementById('isHard').checked = false ;
	myForm.partyNo.value=0 ;	
	myForm.sourcePartyName.value='' ;	
	
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	var orderType=document.getElementById('orderType').value;
	//var orderType=myForm.orderType.value;
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	
	var fromOrderNo = myForm.fromOrderNo.value;	
	var toOrderNo = myForm.toOrderNo.value;
	var orderStage = document.getElementById('orderStageDescription').value;	
	var isHardware = document.getElementById('isHard');
	var partyNo = myForm.partyNo.value;	
	var partyName = myForm.sourcePartyName.value;	
	
		
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
			
		var isAnyFilterSelectFlag = false;
		
		if(orderType !=null && trim(orderType) != "")	
		{
			isAnyFilterSelectFlag = true;
		}
		else if(orderType !=null && trim(orderType) != "")	
		{
			isAnyFilterSelectFlag = true;
		 }
		else if(fromDate !=null && trim(fromDate) != "")
		{
			isAnyFilterSelectFlag = true;
		}
		else if(fromOrderNo !=null && trim(fromOrderNo) != "" && trim(fromOrderNo) != "0")
		{
			isAnyFilterSelectFlag = true;
		}
		else if(toOrderNo !=null && trim(toOrderNo) != "" && trim(toOrderNo) != "0")
		{
			isAnyFilterSelectFlag = true;
		}
		else if(orderStage !=null && trim(orderStage) != "")
		{
			isAnyFilterSelectFlag = true;
		}
		else if(partyNo !=null && trim(partyNo) != "" && trim(partyNo) != 0)
		{
			isAnyFilterSelectFlag = true;
		}
		else if(partyName !=null && trim(partyName) != "")
		{
			isAnyFilterSelectFlag = true;
		}
		else if(isHardware.checked)
		{
			isAnyFilterSelectFlag = true;
		}
		
		if(! isAnyFilterSelectFlag){
			alert("Please select any search criteria.");
			return false ;
		}
		/*
		 *checking that any search criteria is entered or not.
		  User will get a message if no search filter is selected.
		*/	
		
		
		
		return true;
	
}

function enablechangetype()
{
var e=document.getElementById("orderType")
var value = e.options[e.selectedIndex].text;
//alert("Selected value :"+value);
/*
if(value=="CHANGE")
{
document.getElementById("changeType").disabled=false;
}
else
{
document.getElementById("changeType").disabled=true;
}
*/
}

function popitup(url)
{
if(url=="SelectPartyNo")
	{
		//var path =path+"/NewOrderAction.do?method=fetchPartyNo";		
		//window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:530px");
		//fnGetQuoteNo();
		//fnGetOpportunityId();
		
		var path = "<%=request.getContextPath()%>/reportsAction.do?method=fetchPartyForBillingWorkQueue";		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
	}
}

function checkPage()
{
	var hardwareValue = document.getElementById('isHardware');
	if(hardwareValue.value =="1")	
	{
		document.getElementById('isHard').checked = true ;
	}	
}

function getTip(value)
{	
	Tip("<Table border=0> "+ value +"   </table>");	
}

function UnTip()
{
	return false;
}
function downloadDump(formId,fileName)
{
	myForm=document.getElementById(formId);
	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=getDumpFile&fileName="+fileName;
//	showLayer();
	myForm.submit(); 
}

path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>

<body onload="checkPage();javascript:maxLimitChecker(document.getElementById('searchForm'),'<%=ApplicationFlags.ReportsExcelMaxSize%>','<%=Messages.getMessageValue("ReportsExcelMaxSizeMessage") %>');">
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
	<html:form action="/reportsAction_Usage" styleId="searchForm" method="post">
		<bean:define id="formBean" name="reportsBean"></bean:define>
		<html:hidden property="toExcel"/>
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="pagingSorting.sortByOrder"/>
		<html:hidden property="pagingSorting.pageNumber"/>
		<html:hidden property="isHardware"/>
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
		<logic:equal name="isUsage" value="0">	
		<div border="1" class="head"> <span>BILLING WORK QUEUE REPORT</span> </div>
		</logic:equal>
		<logic:equal name="isUsage" value="1">
		<div border="1" class="head"> 
						
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>BILLING WORK QUEUE REPORT(USAGE)</span> 
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
		</logic:equal>
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
					<td align="right" style="font-size:9px"> Order Type:</td>
						<td align="left" nowrap>
							<html:select onmouseover='getTip(value)' onmouseout='UnTip()' property="orderType" onchange="enablechangetype()" >
							<html:option value="">SELECT</html:option>
							<html:option value="New">NEW</html:option>
							<html:option value="Change">CHANGE</html:option>
							</html:select>
						</td>
				<!--  		
						<td align="right" style="font-size:9px"> Change Type:</td>
						<td align="left" nowrap>
							<logic:present name="listChangeType" scope="request">
							<html:select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' property="changeType" styleId="changeTypeId" style="width:70%" styleClass="inputBorder1" disabled="true">
								<html:option value="">--Select--</html:option>
								<html:optionsCollection name="listChangeType" label="changeTypeName" value="changeTypeId" />
							</html:select>
							</logic:present>
						</td>
				-->		
						<td align="right" style="font-size:9px;">Date:</td>
						<td align="left" nowrap>
							<html:select onmouseover='getTip(value)' onmouseout='UnTip()' property="orderType"  >
						<!--  	<html:option value="">SELECT</html:option>
							<html:option value="New">AM Approval Date</html:option>
							<html:option value="New">PM Approval Date</html:option> -->
							<html:option value="COPC Approval Date">COPC Approval Date</html:option> 
							</html:select>
						</td>
						<td align="right" style="font-size:9px;">From Date:</td>
						<td nowrap><html:text  onmouseover='getTip(value)' onmouseout='UnTip()' property="fromDate" styleId="dateFrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Select Date"></a>
							</font>
						</td>
						<td align="right" style="font-size:9px;">To Date:</td>
						<td nowrap><html:text onmouseover='getTip(value)' onmouseout='UnTip()' property="toDate" styleId="dateTo" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Select Date"></a>
							</font>
						</td>
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
						<logic:present name="customerList" scope="request">
							
							
							
							
						</logic:present>
					</tr>
					<tr>
							<td align="right" style="font-size:9px;">FROM ORDER NO:</td>
							<td align="left"><html:text  onmouseover='getTip(value)' onmouseout='UnTip()' property="fromOrderNo" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text></td>
							<td align="right" style="font-size:9px">TO ORDER NO:</td>
							<td align="left">
								<html:text  onmouseover='getTip(value)' onmouseout='UnTip()' property="toOrderNo" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
							</td>
							<td align="right" style="font-size:9px;">Order Stage:</td>
						<td align="left" nowrap>
							<html:select  onmouseover='getTip(value)' onmouseout='UnTip()' property="orderStageDescription"   >
								<html:option value="">--Select--</html:option>
								<html:optionsCollection name="listOrderStage" label="orderStage" value="orderStage" />
							</html:select>
						</td>
				<!--  		
						<td align="right" style="font-size:9px;">Service Stage:</td>
						<td align="left" nowrap>
							<html:select  property="orderType" styleClass="inputBorder1" style="width:90px;float:left;"  >
							<html:option value="">--Select--</html:option>
							<html:optionsCollection name="listOfServiceStage" label="serviceName" value="serviceName" />
							</html:select>
						</td>
				-->
						
						<td align="center"><input type="checkbox" name="isHard" id="isHard" value=""> Is Hardware</td>
						<td align="left" width="150" ><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
					</tr>
					<tr>
						<td align="right" style="font-size:9px"> Party No </td>
						<td align="left">
								<!--<html:text onmouseover='getTip(value)' onmouseout='UnTip()' maxlength="10"  readonly="false" property="partyNo" styleId="crmAccNo" styleClass="inputBorder1" style="width:75px;float:left;" ></html:text>
								<img border="0" src="<%//=request.getContextPath()%>/images/loading.gif" alt="Loading..." width="20" height="21"  id="imgLoadingId" style="display:none" />
								<div class="searchBg1" id="divPartyNo"><a href="#"  title="Select Party no"  onClick="popitup('SelectPartyNo')">...</a></div> 										
								-->
								
								<html:text  onmouseover='getTip(value)' onmouseout='UnTip()' property="partyNo" ></html:text>
								<div class="searchBg1"><a href="#" onClick="popitup('SelectPartyNo')">...</a></div> 
								
						</td>
							
							<td align="right" style="font-size:9px;">Party Name:</td>
							<td align="left"><html:text  onmouseover='getTip(value)' onmouseout='UnTip()' property="sourcePartyName" ></html:text></td>
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" valign="top">
			<legend> <b>Billing Work Queue items</b></legend>
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
			
			<div class="scrollWithAutoScroll1" class="head"  style="height:325px;width:99%; vertical-align: top"  >
			<table style="display:block;overflow:auto vertical-align:top;" width="99%" border="1" align="center" class="tab2" >
				<tr>
					<td align="center" style="font-size:9px" ><b>Order Number</b></td>
					<td align="center" style="font-size:9px" ><b>Party Number</b></td>
					<td align="center" style="font-size:9px" ><b>Order Subtype</b></td>
					<td align="center" style="font-size:9px" ><b>Service Number</b></td>
					<td align="center" style="font-size:9px" ><b>Logical Circuit Id</b></td>
					<td align="center" style="font-size:9px" ><b>Order Line No</b></td>
					<td align="center" style="font-size:9px" ><b>Account Number</b></td>
					<td align="center" style="font-size:9px" ><b>Currency</b></td>
					<td align="center" style="font-size:9px" ><b>Taxation</b></td>
					<td align="center" style="font-size:9px" ><b>Service Name</b></td>
					<td align="center" style="font-size:9px" ><b>Billing Trig Date</b></td>
					<td align="center" style="font-size:9px" ><b>Po Number</b></td>
					<td align="center" style="font-size:9px" ><b>Po Date</b></td>
					<td align="center" style="font-size:9px" ><b>PO Amt</b></td>
					<td align="center" style="font-size:9px" ><b>Order Date</b></td>
					<td align="center" style="font-size:9px" ><b>Approved Date</b></td>
					<td align="center" style="font-size:9px" ><b>Cust Logical Si Id</b></td>
					<td align="center" style="font-size:9px" ><b>Line Name</b></td>
					<td align="center" style="font-size:9px" ><b>Bill Type</b></td>
					<td align="center" style="font-size:9px" ><b>Bill Format</b></td>
					<td align="center" style="font-size:9px" ><b>Legal Entity</b></td>
					<td align="center" style="font-size:9px" ><b>Credit Period</b></td>
					<td align="center" style="font-size:9px" ><b>Licence Company</b></td>
					<td align="center" style="font-size:9px" ><b>Billing Level</b></td>
					<td align="center" style="font-size:9px" ><b>Billing Level Number</b></td>
					<td align="center" style="font-size:9px" ><b>Notice Period</b></td>
					<td align="center" style="font-size:9px" ><b>Billing Mode</b></td>
					<td align="center" style="font-size:9px" ><b>Penalty Clause</b></td>
					<td align="center" style="font-size:9px" ><b>Commitment Period</b></td>
					<td align="center" style="font-size:9px" ><b>Pri Location</b></td>
					<td align="center" style="font-size:9px" ><b>Sec Location</b></td>
					<td align="center" style="font-size:9px" ><b>Contract Start Date</b></td>
					<td align="center" style="font-size:9px" ><b>Contract End Date</b></td>
					<td align="center" style="font-size:9px" ><b>Billing Trig Flag</b></td>
					<td align="center" style="font-size:9px" ><b>LOC Date</b></td>
					<td align="center" style="font-size:9px" ><b>Pm Prov Date</b></td>
					<td align="center" style="font-size:9px" ><b>Child Account Number</b></td>
					<td align="center" style="font-size:9px" ><b>Child Account Fx Status</b></td>
					<td align="center" style="font-size:9px" ><b>Order Type</b></td>
					<td align="center" style="font-size:9px" ><b>Cust PO No.</b></td>
					<td align="center" style="font-size:9px" ><b>Cust PO Date</b></td>
					<td align="center" style="font-size:9px" ><b>Cust Tot PO Amt</b></td>
					<td align="center" style="font-size:9px" ><b>LOC No.</b></td>
					<td align="center" style="font-size:9px" ><b>Billing Address</b></td>
					<td align="center" style="font-size:9px" ><b>SI ID</b></td>
					<td align="center" style="font-size:9px" ><b>Cancel By</b></td>
					<td align="center" style="font-size:9px" ><b>Cancel Date</b></td>
					<td align="center" style="font-size:9px" ><b>Cancel Reason</b></td>
					<td align="center" style="font-size:9px" ><b>Demo Type</b></td>
					<td align="center" style="font-size:9px" ><b>Service Order Type Desc</b></td>					
					<td align="center" style="font-size:9px" ><b>Package ID</b></td>
					<td align="center" style="font-size:9px" ><b>Package Name</b></td>
					<td align="center" style="font-size:9px" ><b>Component ID</b></td>
					<td align="center" style="font-size:9px" ><b>Component Name</b></td>
					<td align="center" style="font-size:9px" ><b>Component InfoID</b></td>
					<td align="center" style="font-size:9px" ><b>Component Type</b></td>
					<!--<td align="center" style="font-size:9px" ><b>rental / nrc component amount</b></td>-->
					<td align="center" style="font-size:9px" ><b>Component Status</b></td>
					<td align="center" style="font-size:9px" ><b>Component Start Logic</b></td>
					<td align="center" style="font-size:9px" ><b>Component Start Date</b></td>
					<td align="center" style="font-size:9px" ><b>Component End Logic</b></td>
					<td align="center" style="font-size:9px" ><b>Component End Date</b></td>
					<td align="center" style="font-size:9px" ><b>Start Token No</b></td>
					<td align="center" style="font-size:9px" ><b>End Token No</b></td>
					<td align="center" style="font-size:9px" ><b>Component FX Instance Id</b></td>
					<td align="center" style="font-size:9px" ><b>Start FX Status</b></td>
					<td align="center" style="font-size:9px" ><b>End FX Status</b></td>
				</tr>
				<logic:present name="customerList" scope="request">
					<logic:notEmpty  name="customerList" scope="request"> 					
						<logic:iterate id="row" name="customerList" indexId="recordId">
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
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderNumber"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="party_num"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="ordersubtype"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceId"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="logicalSINo"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceproductid"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="accountID"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="currencyName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="taxation"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billing_trigger_date"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="poNumber"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="poDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="totalPoAmt"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="copcapprovaldate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custSINo"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="linename"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingTypeName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingformat"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="entity"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="creditPeriodName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="licCompanyName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingLevelName"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingLevelNo"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="noticePeriod"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingMode"/></td>
								
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="penaltyClause"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="commitmentPeriod"/></td>
								
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="primaryLocation"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="seclocation"/></td>
								
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractStartDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="contractEndDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billingTriggerFlag"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="locDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="pm_pro_date"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="child_act_no"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="child_ac_fxstatus"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="orderType"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custPoDetailNo"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="custPoDate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="cust_total_poamt"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="LOC_No"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="billing_address"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="m6cktid"/></td>
								
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="cancelBy"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="canceldate"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="cancelReason"/></td>
								
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="demo"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="serviceTypeDescription"/></td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageID"/>&nbsp;</td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="packageName"/>&nbsp;</td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentID"/>&nbsp;</td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentName"/>&nbsp;</td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentInfoID"/>&nbsp;</td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentType"/>&nbsp;</td>
								<!--<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentAmount"/>&nbsp;</td>-->
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentStatus"/>&nbsp;</td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startLogic"/>&nbsp;</td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.startDate"/>&nbsp;</td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endLogic"/>&nbsp;</td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.end_date"/>&nbsp;</td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxTokenNo"/>&nbsp;</td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endTokenNo"/>&nbsp;</td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.componentInstanceID"/>&nbsp;</td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.fxStartStatus"/>&nbsp;</td>
								<td align="left" style="font-size:9px" width="5%" ><bean:write  name="row" property="componentDto.endFxStatus"/>&nbsp;</td>
							</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty  name="customerList">
					   <tr align="center" >
						 <td colspan="46" align="centre"><B><font color="red">No Records Found</font></B></td>
					   </tr>
					</logic:empty>	
				</logic:present>				
			</table>
			</div>
		</fieldset>
	</html:form>
</body>
</html>