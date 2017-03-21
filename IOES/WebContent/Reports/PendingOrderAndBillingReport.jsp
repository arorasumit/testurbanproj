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
<title>Pending Order And Billing Report</title>
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
<script language="javascript" type="text/javascript"><!--

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
	myform.method.value='viewPendingOrderAndBillingList';	
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
	myform.method.value='viewPendingOrderAndBillingList';	
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewPendingOrderAndBillingList";
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewPendingOrderAndBillingList&inExcel=true";
		showLayerExcel();
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.orderType.value='' ;	
	myForm.fromCopcApprovedDate.value='' ;	
	myForm.toCopcApprovedDate.value='' ;	
	myForm.locDate.value='' ;
	myForm.fromOrderNo.value='' ;
	myForm.toOrderNo.value='' ;
	myForm.fromAccountNo.value='' ;
	myForm.toAccountNo.value='' ;
	myForm.actmngname.value='' ;
	//myForm.osp.value='' ;
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
	var orderType=myForm.orderType.value;
	var fromCopcApprovedDate=myForm.fromCopcApprovedDate.value;
	var toCopcApprovedDate=myForm.toCopcApprovedDate.value;
	var locDate=myForm.locDate.value;
	var fromOrderNo=myForm.fromOrderNo.value;
	var toOrderNo=myForm.toOrderNo.value;
	var fromAccountNo=myForm.fromAccountNo.value;
	var toAccountNo=myForm.toAccountNo.value;
	
	
				
		/*if((fromCopcApprovedDate == null || trim(fromCopcApprovedDate)== "" ) && (toCopcApprovedDate != null && trim(toCopcApprovedDate) != "" ))
		{
			alert("Please enter From COPC Approved Date ");
			return false;
		}
		else if((fromCopcApprovedDate == null || trim(fromCopcApprovedDate)== "" ) && (toCopcApprovedDate != null && trim(toCopcApprovedDate) != "" ))
		{
			alert("Please enter To COPC Approved Date ");
			return false;
		}
		
		if(dateCompare(fromCopcApprovedDate,toCopcApprovedDate)==1)
			{			
				alert("From COPC Approved Date cannot be greater than To COPC Approved Date");
				return false;
			}*/
		if((fromCopcApprovedDate == null || trim(fromCopcApprovedDate)== "" ) && (toCopcApprovedDate == null || trim(toCopcApprovedDate) == "" ))
		{
			alert("Please enter From and To COPC Approved Date ");
			return false;
		}
		if(fromCopcApprovedDate == null || trim(fromCopcApprovedDate)== "" )
		{
			alert("Please enter From COPC Approved Date ");
			return false;
		}
		if(toCopcApprovedDate == null || trim(toCopcApprovedDate)== "" )
		{
			alert("Please enter To COPC Approved Date ");
			return false;
		}
		if(dateCompare(fromCopcApprovedDate,toCopcApprovedDate)==1)
			{			
				alert("From COPC Approved Date cannot be greater than To COPC Approved Date");
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


//function fnViewOrder(orderNo)
//{
//      document.forms[0].action="./viewOrderAction.do?methodName=viewOrder&orderNo="+orderNo;
//      document.forms[0].submit();
//}
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
--></script>
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
				<tr ><td align="left"><span><span>PENDING ORDER AND BILLING REPORT</span> </span> 
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
					<td width="90px"/>
						<td align="right" style="font-size:9px"> Order Type:</td>
						<td align="left" nowrap>
							<html:select property="orderType" style="width:70px;float:left;" >
							<html:option value="">Select</html:option>
							<html:option value="New">New</html:option>
							<html:option value="Change">Change</html:option>
							</html:select>
							
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;" width="51">From COPC Approved Date:</td>
						<td nowrap width="142"><html:text  property="fromCopcApprovedDate" styleId="fromcopcdate"styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
									<a href="javascript:show_calendar(searchForm.fromCopcApprovedDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;" width="39">To COPC Approved Date:</td>
						<td nowrap width="223"><html:text  property="toCopcApprovedDate" styleId="tocopcdate" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toCopcApprovedDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						
						</tr>
						<tr>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">LOC Date:</td>
						<td nowrap><html:text  property="locDate" styleId="locdate"  style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.locDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px" width="51">From Order No:</td>
						<td align="left" width="142">
							<html:text  property="fromOrderNo" style="width:75px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" 
							maxlength="18" ></html:text>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px" width="39">To Order No:</td>
						<td align="left" width="223">
							<html:text  property="toOrderNo"  style="width:75px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" 
							maxlength="18" ></html:text>
						</td>
						
						
						
						</tr>
						<tr>
						<td width="10px"/>
						<td align="right" style="font-size:9px"> From A/C No:</td>
						<td align="left" nowrap>
							<html:text  property="fromAccountNo"  style="width:70px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px" > To A/C No:</td>
						<td align="left" nowrap width="142">
							<html:text  property="toAccountNo" style="width:70px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
						</td>
						<td width="10px"/>
						
						<td align="right" style="font-size:9px">Act Mgr:</td>
						<td align="left">
							<html:text  property="actmngname"  style="width:75px;float:left;" maxlength="18" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Act Mgr')};" ></html:text>
						</td>
						<!--<td align="right" style="font-size:9px;">OSP</td>
								<td nowrap>
								<html:select property="osp" styleClass="inputBorder1" style="width:70px;float:left;" >
								<html:option value="">Select</html:option> 
								<html:option value='YES'>Yes</html:option> 
								<html:option value='NO'>No</html:option> 
								</html:select>
							</td>-->
						
						<td align="left" >
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
					
						<td align="left" >
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
					
							<td align="left" ><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
						
							
							
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Pending Order And Billing Report</b></legend>
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
		<!-- Start Scrolling and Freezing of PENDING ORDER AND BILLING REPORT  -->
			
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
								    <td align="center" class='inner col2 head1'><b>Cust Account Id</b></td>
									<td align="center" class='inner col2 head1'><b>Region Name </b></td>
									<td align="center" class='inner col4 head1'><b>Customer Segment </b></td>
									<td align="center" class='inner col4 head1'><b>Account Category</b></td>
									<td align="center" class='inner col2 head1'><b>Account Number  </b></td>
									<td align="center" class='inner col3 head1'><b>CRM Product Name  </b></td>
								    <td align="center" class='inner col2 head1'><b>Logical Circuit Id  </b></td>
									<td align="center" class='inner col3 head1'><b>Company Name  </b></td>
									<td align="center" class='inner col2 head1'><b>Factory Circuit Id</b></td>
									<td align="center" class='inner col2 head1'><b>
						<logic:present name="pendingOrderList" scope="request">
							<logic:notEmpty  name="pendingOrderList" scope="request"> 
								<a href="#" onclick="sortSubmit('ORDERNO')">Crm Order Id</a>
							</logic:notEmpty>
							<logic:empty  name="pendingOrderList" scope="request">
								Crm Order Id
							</logic:empty>
						</logic:present>
					</b></td>
									<td align="center" class='inner col2 head1'><b>Customer PODate  </b></td>
									<td align="center" class='inner col2 head1'><b> Customer PO Number  </b></td>
									<td align="center" class='inner col3 head1'><b>Line Name  </b></td>
									<td align="center" class='inner col3 head1'><b>Licence Company </b></td>
									<td align="center" class='inner col3 head1'><b>Charge Name  </b></td>
									<td align="center" class='inner col3 head1'><b>Cyclic Non Cyclic  </b></td>
									<td align="center" class='inner col3 head1'><b>Store Address  </b></td>
									<td align="center" class='inner col2 head1'><b>M6 Order Id  </b></td>
									<td align="center" class='inner col2 head1'><b>Contract Duration  </b></td>
									<td align="center" class='inner col2 head1'><b>Currency  </b></td>
									<td align="center" class='inner col2 head1'><b>Annual Rate  </b></td>
									<td align="center" class='inner col2 head1'><b>Line Item Amount</b></td>
									<td align="center" class='inner col3 head1'><b>Line Item Description </b></td>
								    <td align="center" class='inner col3 head1'><b>Account Mgr</b></td>
									<td align="center" class='inner col2 head1'><b>Order Type  </b></td>
									<td align="center" class='inner col2 head1'><b>Payment Term  </b></td>
									<td align="center" class='inner col2 head1'><b>LOC Date  </b></td>
									<td align="center" class='inner col2 head1'><b>PM Prov Date  </b></td>
									<td align="center" class='inner col2 head1'><b>PO Create Date  </b></td>
									<td align="center" class='inner col2 head1'><b>PO Number  </b></td>
									<td align="center" class='inner col3 head1'><b>Project Mgr</b></td>
									<td align="center" class='inner col2 head1'><b>COPC Approved Date  </b></td>
									<td align="center" class='inner col2 head1'><b>Tax inc or exc  </b></td>	
									<td align="center" class='inner col3 head1'><b>Bandwidth  </b></td>
									<td align="center" class='inner col2 head1'><b>UOM  </b></td>
									<td align="center" class='inner col2 head1'><b>Billing Bandwidth</b></td>		
									<td align="center" class='inner col2 head1'><b>Bill UOM</b></td>								
									<td align="center" class='inner col2 head1'><b>Vertical </b></td>
									<td align="center" class='inner col2 head1'><b>Service Product Id </b></td>
									<td align="center" class='inner col2 head1'><b>Billing Trigger Status </b></td>
									<td align="center" class='inner col2 head1'><b>Charge Information Id </b></td>
									<td align="center" class='inner col2 head1'><b>Change Service Id</b></td>
											<!--		Added 4 column from bottom: by Anadi	-->	
						<!--<td align="center" class='inner col2 head1' ><b>OSP</b></td>-->
					</tr>
							</table>
						</div>
						<div id='contentscroll' class='content1' onscroll='reposHead(this);'>
		               		<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>	 
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
											<td align="left" class='inner col2'><bean:write  name="row" property="accountID"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="regionName"/>&nbsp;</td>
					                        <td align="left" class='inner col4'><bean:write  name="row" property="cus_segment"/>&nbsp;</td>
					                        <td align="left" class='inner col4'><bean:write  name="row" property="act_category"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="crmAccountNo"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="crm_productname"/>&nbsp;</td>
									        <td align="left" class='inner col2'><bean:write  name="row" property="logicalCircuitId"/>&nbsp;</td>
									        <td align="left" class='inner col3'><bean:write  name="row" property="companyName"/>&nbsp;</td>
									        <td align="left" class='inner col2'><bean:write  name="row" property="factory_ckt_id"/>&nbsp;</td>
										    <td align="left" class='inner col2'><bean:write  name="row" property="orderNumber"/>&nbsp;</td>
							                <td align="left" class='inner col2'><bean:write  name="row" property="custPoDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="custPoDetailNo"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="linename"/>&nbsp;</td>
										    <td align="left" class='inner col3'><bean:write  name="row" property="lcompanyname"/>&nbsp;</td>
										    <td align="left" class='inner col3'><bean:write  name="row" property="chargeName"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="chargeTypeName"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="storename"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="m6OrderNo"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="currencyName"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="annualRate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="chargeAmount_String"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="line_desc"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="actmngname"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="orderType"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="frequencyName"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="LOC_Date"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="pm_pro_date"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="orderDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="poNumber"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="prjmngname"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="copcApproveDate"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="taxation"/>&nbsp;</td>
											<td align="left" class='inner col3'><bean:write  name="row" property="bandwaidth"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write  name="row" property="uom"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write name="row" property="billing_bandwidth"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write name="row" property="billing_uom"/>&nbsp;</td>
										    <td align="left" class='inner col2'><bean:write name="row" property="verticalDetails"/>&nbsp;</td>
										     <!--		Added 4 column from bottom: by Anadi	-->
										    <td align="left" class='inner col2'><bean:write name="row" property="serviceProductId"/>&nbsp;</td>
										    <td align="left" class='inner col2'><bean:write name="row" property="billingTriggStatus"/>&nbsp;</td>
										    <td align="left" class='inner col2'><bean:write name="row" property="chargeInfoId"/>&nbsp;</td>
											<td align="left" class='inner col2'><bean:write name="row" property="changeServiceId"/>&nbsp;</td>
			
						<!--	<td align="left" class='inner col2' ><bean:write  name="row" property="osp"/>&nbsp;</td>-->
								
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
                   	<div class='horizontal-scrollpendinorderandbilling' onscroll='reposHorizontal(this);'>
                       	<div>
                       	</div>
                   	</div>
               	</td>
          	  </tr>
			</table>	
		<!-- End Scrolling and Freezing of PENDING ORDER AND BILLING REPORT  -->
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
