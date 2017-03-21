<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- [TRNG21052013004]    Vijay        24 June   - create freez header -->
<!-- [001] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.utilities.ApplicationFlags"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.ReportsBean"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>CUSTOMER BASE REPORT</title>
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
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewCustomerBaseReport";	
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
	myform.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewCustomerBaseReport";		
	showLayer();
	myform.submit();
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
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewCustomerBaseReport";	
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
		myForm.action="<%=request.getContextPath()%>/reportsAction_Usage.do?method=viewCustomerBaseReport&inExcel=true";
		showLayerExcel();
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.crmAccountNo.value='' ;	
	myForm.fromDate.value='' ;
	myForm.toDate.value='' ;
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
		
	if((fromDate == null & toDate == null) || ( trim(fromDate)=="" & trim(toDate) == "")){
		alert("Please enter  From Date and To Date!");
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
		
	return true;	
}

path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload="javascript:maxLimitChecker(document.getElementById('searchForm'),'<%=ApplicationFlags.ReportsExcelMaxSize%>','<%=Messages.getMessageValue("ReportsExcelMaxSizeMessage") %>');">

<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/reportsAction_Usage" styleId="searchForm" method="post">
		<bean:define id="formBean" name="reportsBean"></bean:define>
		<html:hidden property="toExcel"/>
		<html:hidden property="interfaceId"/>
		<html:hidden property="isDumpAvailable"/>
		<html:hidden property="dumpFileName"/>		
		<html:hidden property="reportProcessForLimit"/>
         <html:hidden property="reportCurrentCount"/>
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
			
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>Customer Base Report</span> 
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
					<td align="right" style="font-size:9px;">From Date:</td>
					<td nowrap><html:text  property="fromDate" styleId="fromDate" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
					    <font size="1">
							<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Select Date"></a>
						</font>
					</td>
					<td align="right" style="font-size:9px;">To Date:</td>
					<td nowrap><html:text  property="toDate" styleId="toDate" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
					    <font size="1">
							<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Select Date"></a>
						</font>
					</td>
					<td width="1%"/>
					<td width="20%"/>
					<td width="20%"/>
				</tr>
				<tr>				 	
					<td align="right" style="font-size:9px">Account No.</td>
					<td align="left" nowrap>
						<html:text property="crmAccountNo" style="width:90px;float:left;" 
						maxlength="15" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
					</td>
					<td width="1%"/>					
					<td align="left" >
						<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
					</td>					
					<td width="1%"/>
					<td align="center" valign="top" width="20%">
						<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
					</td>
					<logic:present name="CustomerBaseReport" scope="request">						
						<td align="left" width="20%"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>										
					</logic:present>					
				</tr>
			</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Customer Base Report</b></legend>
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
			
			<!-- Start Scrolling and Freezing of Copy and Cancel Report   -->
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
				   	<td align="center" class='inner col2 head1' ><b>OrderNo</b></td>
				    <td align="center" class='inner col2 head1' ><b>External Id</b></td>
					<td align="center" class='inner col3 head1' ><b>External Account No</b></td>
					<td align="center" class='inner col2 head1' ><b>Active Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Inactive Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Crm Account No</b></td>
					<td align="center" class='inner col2 head1' ><b>AccountNo</b></td>
					<td align="center" class='inner col2 head1' ><b>Account Segment </b></td>
		<!--	<td align="center" class='inner col1 head1' ><b>Parent Id</b></td>				-->
					<td align="center" class='inner col2 head1' ><b>Bill Fname</b></td>
					<td align="center" class='inner col3 head1' ><b>Bill Company</b></td>
					<td align="center" class='inner col3 head1' ><b>Bill Address1</b></td>
					<td align="center" class='inner col3 head1' ><b>Bill Address2</b></td>
					<td align="center" class='inner col2 head1' ><b>Bill Address3</b></td>
					<td align="center" class='inner col2 head1' ><b>Bill City</b></td>
					<td align="center" class='inner col2 head1' ><b>Bill State</b></td>
					<td align="center" class='inner col3 head1' ><b>Account Manager</b></td>
					<td align="center" class='inner col3 head1' ><b>Account Manager EmailId</b></td>
					<td align="center" class='inner col2 head1' ><b>Account Manager Phone No</b></td>
					<td align="center" class='inner col2 head1' ><b>Contact Person Phone(Contact1 Phone)</b></td>
<!--					<td align="center" class='inner col1 head1' ><b>Contact2 Phone</b></td>-->
					<td align="center" class='inner col2 head1' ><b>Bill Zip</b></td>
					<td align="center" class='inner col2 head1' ><b>Order Type</b></td>
					<td align="center" class='inner col3 head1' ><b>Contact Person Name</b></td>
					<td align="center" class='inner col3 head1' ><b>Contact Person Email</b></td>
					<td align="center" class='inner col3 head1' ><b>Chairperson Name</b></td>
					<td align="center" class='inner col2 head1' ><b>Chairperson Phone</b></td>
					<td align="center" class='inner col3 head1' ><b>Chairperson Email</b></td>
					<td align="center" class='inner col3 head1' ><b>Component Name</b></td>
					<td align="center" class='inner col2 head1' ><b>Component Active Date</b></td>
					<td align="center" class='inner col2 head1' ><b>Business Segment</b></td>
					<td align="center" class='inner col2 head1' ><b>Bill Period</b></td>
					<td align="center" class='inner col2 head1' ><b>Bill Frequency</b></td>
					<td align="center" class='inner col4 head1' ><b>Product Name</b></td>
			</tr>
			</table>
			 </div> 	
			 <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
       			<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>
       			
			<logic:present name="CustomerBaseReport" scope="request">
				<logic:notEmpty  name="CustomerBaseReport" scope="request"> 					
					<logic:iterate id="row" name="CustomerBaseReport" indexId="recordId">
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
				    	<td align="left" class='inner col2'><bean:write  name="row" property="orderNo"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="fxSiId"/>&nbsp;</td>
						<td align="left" class='inner col3'><bean:write  name="row" property="fx_external_acc_id"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="activeDate"/>&nbsp;</td>
					    <td align="left" class='inner col2'><bean:write  name="row" property="inActiveDate"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="crmACcountNO"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="accountno"/>&nbsp;</td>						
						<td align="left" class='inner col2'><bean:write  name="row" property="accountSegment"/>&nbsp;</td>
					<!--		<td align="left" class='inner col2'><bean:write  name="row" property="parent_name"/>&nbsp;</td>-->
						<td align="left" class='inner col2'><bean:write  name="row" property="billingFormatName"/>&nbsp;</td>
						<td align="left" class='inner col3'><bean:write  name="row" property="billCompany"/>&nbsp;</td>
						<td align="left" class='inner col3'><bean:write  name="row" property="billingAddress"/>&nbsp;</td>
						<td align="left" class='inner col3'><bean:write  name="row" property="billing_address2"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="billing_address"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="billCity"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="billState"/>&nbsp;</td>
						<td align="left" class='inner col3'><bean:write  name="row" property="accountManager"/>&nbsp;</td>
						<td align="left" class='inner col3'><bean:write  name="row" property="acmgrEmail"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="accountMgrPhoneNo"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="contact1_Phone"/>&nbsp;</td>
<!--						<td align="left" class='inner col2'><bean:write  name="row" property="contact2_Phone"/>&nbsp;</td> -->
						<td align="left" class='inner col2'><bean:write  name="row" property="billZip"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="order_type"/>&nbsp;</td>
						<td align="left" class='inner col3'><bean:write  name="row" property="contactName"/>&nbsp;</td>
						<td align="left" class='inner col3'><bean:write  name="row" property="contactPersonEmail"/>&nbsp;</td>
						<td align="left" class='inner col3'><bean:write  name="row" property="chairPersonName"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="chairPersonPhone"/>&nbsp;</td>
						<td align="left" class='inner col3'><bean:write  name="row" property="chairPersonEmail"/>&nbsp;</td>
						<td align="left" class='inner col3'><bean:write  name="row" property="componentName"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="componentActiveDate"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="businessSegment"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="bill_period"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="frequencyName"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="productName"/>&nbsp;</td>
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
		              	<div class='horizontal-scrollcustomerbasereport' onscroll='reposHorizontal(this);'>
		                  	<div>
		                  	</div>
		              	</div>
		          	</td>
		     	</tr>	
		     </table>	
		     	 <!-- End Scrolling and Freezing of Copy and Cancel Report   -->
		     	 
				<logic:empty  name="CustomerBaseReport">
				  <% String fromPageSubmit=request.getParameter("fromPageSubmit");
						if(("1").equalsIgnoreCase(fromPageSubmit)){%>
				   <tr align="center" >
					 <td colspan="32" align="center"><b><font color="red">No Records Found</font></b></td>
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
