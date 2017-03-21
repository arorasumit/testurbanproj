<!--[HYPR22032013001]    Vijay     30-March-2013    Billing workqueue report  -->
<!-- [TRNG21052013004]    Vijay        19 June   - create freez header -->
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
<%@page import="com.ibm.ioes.utilities.ApplicationFlags"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
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
<!-- [TRNG21052013004] start -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ReportHeaderFreeze.js"></script>
<link href="./css/ReportHeaderFreeze.css" type="text/css" rel="stylesheet">
<!-- [TRNG21052013004] end -->
 
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
	myform.method.value='viewBillingWorkQueueReport';	
	/*if(isBlankForm()==false)
	{
		return ;
	}*/
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
	
	myform.method.value='viewBillingWorkQueueReport';	
		//showLayer();
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewBillingWorkQueueReport";
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewBillingWorkQueueReport&inExcel=true";
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

<body onload="checkPage();javascript:maxLimitChecker(document.getElementById('searchForm'),'<%=ApplicationFlags.ReportsExcelMaxSize%>','<%=Messages.getMessageValue("ReportsExcelMaxSizeMessage") %>');">
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
	<html:form action="/reportsAction" styleId="searchForm" method="post">
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
			<tr align="left">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> 
						
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>BILLING WORK QUEUE REPORT</span> 
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
			
			<!-- Start Scrolling and Freezing of BILLING WORK QUEUE REPORT   -->
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
					<td align="center" class='inner col2 head1' ><b>Order Number</b></td>
					<td align="center" class='inner col2 head1' ><b>Party Number</b></td>
					<td align="center" class='inner col2 head1' ><b>Order Subtype</b></td>
					<td align="center" class='inner col2 head1' ><b>Service Number</b></td>
					<td align="center" class='inner col2 head1' ><b>Annotation</b></td>
					<td align="center" class='inner col2 head1' ><b>Logical Circuit Id</b></td>
					<td align="center" class='inner col1 head1' ><b>End Dt Logic</b></td>
					<td align="center" class='inner col2 head1' ><b>Order Line No</b></td>
					<td align="center" class='inner col2 head1' ><b>Account Number</b></td>
					<td align="center" class='inner col2 head1' ><b>Total Amount</b></td>
					<td align="center" class='inner col2 head1' ><b>Annual Rate</b></td>
					<td align="center" class='inner col2 head1' ><b>Currency</b></td>
					<td align="center" class='inner col1 head1' ><b>Bill Period</b></td>
					<td align="center" class='inner col1 head1' ><b>Form C Available</b></td>
					<td align="center" class='inner col1 head1' ><b>Taxation</b></td>
					<td align="center" class='inner col3 head1' ><b>Service Name</b></td>
					<td align="center" class='inner col2 head1' ><b>Billing Trig Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Frequency Amt</b></td>
					<td align="center" class='inner col2 head1' ><b>Po Number</b></td>
					<td align="center" class='inner col2 head1' ><b>Po Date</b></td>
					<td align="center" class='inner col1 head1' ><b>Period In Month</b></td>
					<td align="center" class='inner col2 head1' ><b>Total PO Amt</b></td>
					<td align="center" class='inner col1 head1' ><b>Charge Type</b></td>
					<td align="center" class='inner col2 head1' ><b>Charge Name</b></td>
					<td align="center" class='inner col2 head1' ><b>Charge Start Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Charge End Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Order Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Approved Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Cust Logical Si Id</b></td>
					<td align="center" class='inner col2 head1' ><b>Line Name</b></td>
					<td align="center" class='inner col2 head1' ><b>Advance</b></td>
					<td align="center" class='inner col2 head1' ><b>TRAI Rate</b></td>
					<td align="center" class='inner col2 head1' ><b>Discount</b></td>
					<td align="center" class='inner col2 head1' ><b>Installment Rate</b></td>
					<td align="center" class='inner col1 head1' ><b>Intrest Rate</b></td>
					<td align="center" class='inner col1 head1' ><b>Principal Amt</b></td>
					<td align="center" class='inner col2 head1' ><b>Bill Type</b></td>
					<td align="center" class='inner col2 head1' ><b>Frequency</b></td>
					<td align="center" class='inner col3 head1' ><b>Bill Format</b></td>
					<td align="center" class='inner col3 head1' ><b>Legal Entity</b></td>
					<td align="center" class='inner col1 head1' ><b>Nature Of Sale</b></td>
					<td align="center" class='inner col1 head1' ><b>Type Of Sale</b></td>
					<td align="center" class='inner col2 head1' ><b>Credit Period</b></td>
					<td align="center" class='inner col2 head1' ><b>Licence Company</b></td>
					<td align="center" class='inner col2 head1' ><b>Store</b></td>
					<td align="center" class='inner col1 head1' ><b>Billing Level</b></td>
					<td align="center" class='inner col1 head1' ><b>Hardware Type</b></td>
					<td align="center" class='inner col2 head1' ><b>Billing Level Number</b></td>
					<td align="center" class='inner col2 head1' ><b>Contract Period Mnths</b></td>
					<td align="center" class='inner col1 head1' ><b>Notice Period</b></td>
					<td align="center" class='inner col1 head1' ><b>Billing Mode</b></td>
					<td align="center" class='inner col1 head1' ><b>Penalty Clause</b></td>
					<td align="center" class='inner col1 head1' ><b>Commitment Period</b></td>
					<td align="center" class='inner col2 head1' ><b>Dispatch Address</b></td>
					<td align="center" class='inner col4 head1' ><b>Pri Location</b></td>
					<td align="center" class='inner col4 head1' ><b>Sec Location</b></td>
					<td align="center" class='inner col2 head1' ><b>Warranty Start Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Warranty End Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Extnd Support End Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Contract Start Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Contract End Date</b></td>
					<td align="center" class='inner col2 head1' ><b>FX SNO Start Charge</b></td>
					<td align="center" class='inner col2 head1' ><b>FX Status Start Charge</b></td>
					<td align="center" class='inner col2 head1' ><b>Token No End Charge</b></td>
					<td align="center" class='inner col2 head1' ><b>End Dt Days</b></td>
					<td align="center" class='inner col2 head1' ><b>Fx Status End Charge</b></td>
					<td align="center" class='inner col2 head1' ><b>Token No Start Charge</b></td>
					<td align="center" class='inner col2 head1' ><b>FX SNO End Charge</b></td>
					<td align="center" class='inner col2 head1' ><b>Billing Trig Flag</b></td>
					<td align="center" class='inner col2 head1' ><b>Challen Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Start Dt Logic</b></td>
					<td align="center" class='inner col2 head1' ><b>LOC Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Pm Prov Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Challen No</b></td>
					<td align="center" class='inner col2 head1' ><b>Start Dt Days</b></td>
					<td align="center" class='inner col2 head1' ><b>DDNI Disp,Delivered,Installed</b></td>
					<td align="center" class='inner col2 head1' ><b>DDNI Disp,Del Not Installed</b></td>
					<td align="center" class='inner col2 head1' ><b>DND Dispatch And Delivered</b></td>
					<td align="center" class='inner col2 head1' ><b>DND Dispatch But Not Delivered</b></td>
					<td align="center" class='inner col2 head1' ><b>Child Account Number</b></td>
					<td align="center" class='inner col2 head1' ><b>Child Account Fx Status</b></td>
					<td align="center" class='inner col2 head1' ><b>Order Type</b></td>
					<td align="center" class='inner col2 head1' ><b>Cust PO No.</b></td>
					<td align="center" class='inner col2 head1' ><b>Cust PO Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Cust Tot PO Amt</b></td>
					<td align="center" class='inner col2 head1' ><b>LOC No.</b></td>
					<td align="center" class='inner col2 head1' ><b>Billing Address</b></td>
					<td align="center" class='inner col2 head1' ><b>SI ID</b></td>
					<td align="center" class='inner col2 head1' ><b>Cancel By</b></td>
					<td align="center" class='inner col2 head1' ><b>Cancel Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Cancel Reason</b></td>
					<td align="center" class='inner col2 head1' ><b>Demo Type</b></td>
					<td align="center" class='inner col2 head1' ><b>Service Order Type Desc</b></td>
					<td align="center" class='inner col2 head1' ><b>Opportunity ID</b></td>						
				</tr>
				</table>
					 </div> 	
					 <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	        			<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>		
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
								<td align="left" class='inner col2'><bean:write  name="row" property="orderNumber"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="party_num"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="ordersubtype"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="serviceId"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="chargeAnnotation"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="logicalSINo"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="endDateLogic"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="serviceproductid"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="accountID"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="chargesSumOfLineitem"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="annual_rate"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="currencyName"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="billPeriod"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="formAvailable"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="taxation"/>&nbsp;</td>
								<td align="left" class='inner col3' ><bean:write  name="row" property="serviceName"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="billing_trigger_date"/>&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="poNumber"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="poDate"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="totalPoAmt"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="chargeTypeName"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="chargeName"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="startDate"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="chargeEndDate"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="orderDate"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="copcapprovaldate"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="custSINo"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="linename"/>&nbsp;</td>
								
								<td align="left" class='inner col2' ><bean:write  name="row" property="advance"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="rate_code"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="discount"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="installRate"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="interestRate"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="principalAmount"/>&nbsp;</td>
								
								<td align="left" class='inner col2' ><bean:write  name="row" property="billingTypeName"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="chargeFrequency"/>&nbsp;</td>
								<td align="left" class='inner col3' ><bean:write  name="row" property="billingformat"/>&nbsp;</td>
								<td align="left" class='inner col3' ><bean:write  name="row" property="entity"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="saleNature"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="saleTypeName"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="creditPeriodName"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="licCompanyName"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="store"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="billingLevelName"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="hardwaretypeName"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="billingLevelNo"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="noticePeriod"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="billingMode"/>&nbsp;</td>
								
								<td align="left" class='inner col1' ><bean:write  name="row" property="penaltyClause"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="commitmentPeriod"/>&nbsp;</td>
								
								<td align="left" class='inner col2' ><bean:write  name="row" property="dispatchAddress1"/>&nbsp;</td>
								<td align="left" class='inner col4' ><bean:write  name="row" property="primaryLocation"/>&nbsp;</td>
								<td align="left" class='inner col4' ><bean:write  name="row" property="seclocation"/>&nbsp;</td>
								
								<td align="left" class='inner col2' ><bean:write  name="row" property="warrantyStartDate"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="warrantyEndDate"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="extSuportEndDate"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="contractStartDate"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="contractEndDate"/>&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="billingTriggerFlag"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="challendate"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="startDateLogic"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="locDate"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="pm_pro_date"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="challenno"/>&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="dnd_Dispatch_And_Delivered"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="dnd_Dispatch_But_Not_Delivered"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="child_act_no"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="child_ac_fxstatus"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="orderType"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="custPoDetailNo"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="custPoDate"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="cust_total_poamt"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="LOC_No"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="billing_address"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="m6cktid"/>&nbsp;</td>
								
								<td align="left" class='inner col2' ><bean:write  name="row" property="cancelBy"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="canceldate"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="cancelReason"/>&nbsp;</td>
								
								<td align="left" class='inner col2' ><bean:write  name="row" property="demo"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="serviceTypeDescription"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="opportunityId"/>&nbsp;</td>
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
		              	<div class='horizontal-scrollbillingworkqueuereport' onscroll='reposHorizontal(this);'>
		                  	<div>
		                  	</div>
		              	</div>
		          	</td>
		     	</tr>	
		     </table>	
		     	 <!-- End Scrolling and Freezing of BILLING WORK QUEUE REPORT   -->
					
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