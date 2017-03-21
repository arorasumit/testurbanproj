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
<title>Bulk SI Upload Report</title>
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
	myform.method.value='viewBulkSIUploadReport';	
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
	myform.method.value='viewBulkSIUploadReport';	
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
	
	var fromDate=myForm.date_of_installation_from.value;
	var toDate=myForm.date_of_installation_to.value;
		if((fromDate == null & toDate == null) 
		|| ( trim(fromDate)=="" & trim(toDate) == "")){
		alert("Please enter From Date and To Date!");
		return false;
		
	} 
	
	if(dateCompare(fromDate,toDate)==1)
			{			
				alert("From Date cannot be greater than To Date");
				return;
			}
			
	else {
	
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewBulkSIUploadReport";
		showLayer();
		myForm.submit(); 
		}
	
}

function exportToExcel()
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='true';
	var fromDate=myForm.date_of_installation_from.value;
	var toDate=myForm.date_of_installation_to.value;
		if((fromDate == null & toDate == null) 
		|| ( trim(fromDate)=="" & trim(toDate) == "")){
		alert("Please enter From Date and To Date!");
		return false;
		
	} 
	
	var excelName= '<%= AppConstants.ACTION_EXCEL %>';
	var jsData =	{
		userId:userId,
		interfaceId:interfaceId,
		actionType:excelName
		
	};		
    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	jsonrpc.processes.populateReportUsageDetails(jsData);  
	if(dateCompare(fromDate,toDate)==1)
			{			
				alert("From Date cannot be greater than To Date");
				return;
			}
			
	else {
	
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewBulkSIUploadReport&inExcel=true";
		showLayerExcel();
		myForm.submit();
		}
	
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.date_of_installation_from.value='' ;	
	myForm.date_of_installation_to.value='' ;	
	myForm.servicename.value='' ;	
	myForm.partyNo.value='' ;	
	myForm.orderNo.value='' ;
	myForm.logical_SI_No.value='' ;
	myForm.linename.value='' ;
	
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
			<tr align="left">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> 
			
			
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>Bulk SI Upload Report</span> 
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
						<td align="right" style="font-size:9px;">Date Of Installation From:</td>
						<td nowrap><html:text  property="date_of_installation_from" styleId="date_of_installation_from" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.date_of_installation_from);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						
						<td width="10px"/>
						<td align="right" style="font-size:9px;">Date Of Installation To:</td>
						<td nowrap><html:text  property="date_of_installation_to" styleId="date_of_installation_to" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.date_of_installation_to);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						
						
						<td width="10px"/>
						<td align="right" style="font-size:9px">Services:</td>
						<td align="left">
							<html:text  property="servicename" styleClass="inputBorder2" style="width:75px;float:left;" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Services')};"
							maxlength="18" ></html:text>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px">Party No:</td>
						<td align="left" width="78">
							<html:text  property="partyNo" styleClass="inputBorder2" style="width:75px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" 
							maxlength="18" ></html:text>
						</td>
						<td width="78"/>
						<td align="left" width="29">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						</tr>
						<tr>
						<td width="50px"/>
						<td align="right" style="font-size:9px"> Crm Order No:</td>
						<td align="left" nowrap>
							<html:text  property="orderNo" styleClass="inputBorder2" style="width:90px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
						</td>
						<td width="50px"/>
						<td align="right" style="font-size:9px"> Logical Id:</td>
						<td align="left" nowrap>
							<html:text  property="logical_SI_No" styleClass="inputBorder2" style="width:90px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
						</td>
						<td width="50px"/>
						<td align="right" style="font-size:9px">CRM Product Name:</td>
						<td align="left" nowrap>
							<html:text  property="linename" styleClass="inputBorder2" style="width:90px;float:left;" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'CRM Product Name')};"></html:text>
						</td>
						<td width="20px"/>
						<td align="left" >
							<div class="searchBg" style="width:48px;float:left;"><a href="#"  title="Search" onClick="searchClick();">Search</a></div>
						</td>
						<td align="left">
							<td align="left"><div class="searchBg" style="width:47px;float:left;" ><a href="#" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
						</td>
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Bulk SI Upload Report</b></legend>
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
			
				<!-- Start Scrolling and Freezing of Bulk SI Upload Report -->
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
					
					<td align="center" class='inner col31 head1'><b>Accoun Name </b></td>
					<td align="center" class='inner col2 head1'><b> Crm Order Mocn No</b></td>
					<td align="center" class='inner col2 head1'><b>Crm Service Opms Id</b></td>
					<td align="center" class='inner col2 head1'><b>Services </b></td>
					<td align="center" class='inner col31 head1'><b>Installation Address A1</b></td>
					<td align="center" class='inner col31 head1'><b>Installation Address A2 </b></td>
					<td align="center" class='inner col31 head1'><b>Installation Address A3</b></td>
					<td align="center" class='inner col31 head1'><b>From City </b></td>
					<td align="center" class='inner col31 head1'><b>From State</b></td>
					<td align="center" class='inner col31 head1'><b>From Country </b></td>
					<td align="center" class='inner col31 head1'><b>Installation Address B1</b></td>
					<td align="center" class='inner col31 head1'><b>Installation Address B2 </b></td>
					<td align="center" class='inner col31 head1'><b>Installation Address B3</b></td>
					<td align="center" class='inner col31 head1'><b> To City</b></td>
					<td align="center" class='inner col31 head1'><b>To State</b></td>
					<td align="center" class='inner col31 head1'><b>To Country </b></td>
					<td align="center" class='inner col31 head1'><b>Date Of Installation</b></td>
					<td align="center" class='inner col31 head1'><b>Date Of Activation </b></td>
					<td align="center" class='inner col2 head1'><b>Bandwidth Value</b></td>
					<td align="center" class='inner col2 head1'><b>UOM </b></td>
					<td align="center" class='inner col2 head1'><b>LOB</b></td>
					<td align="center" class='inner col2 head1'><b>Circle </b></td>
					<td align="center" class='inner col2 head1'><b>Zone</b></td>
					<td align="center" class='inner col31 head1'><b>Support Location A </b></td>
					<td align="center" class='inner col31 head1'><b>Support Location B</b></td>
					<td align="center" class='inner col31 head1'><b>Commited SLA </b></td>
					<td align="center" class='inner col31 head1'><b>HUB Location</b></td>
					<td align="center" class='inner col31 head1'><b>Platform </b></td>
					<td align="center" class='inner col31 head1'><b> 	
					<logic:present name="BulkSIUploadReport" scope="request">
							<logic:notEmpty  name="BulkSIUploadReport" scope="request"> 
								<a href="#" onclick="sortSubmit('CUSTOMER_LOGICAL_SI_NO')">Logical SI No</a>
							</logic:notEmpty>
							<logic:empty  name="BulkSIUploadReport" scope="request">
								Logical SI No
							</logic:empty>
						</logic:present>
						</b></td>
					<td align="center" class='inner col2 head1'><b>SI Id </b></td>
					<td align="center" class='inner col2 head1'><b>IPLC</b></td>
					<td align="center" class='inner col2 head1'><b>Managed Yes No</b></td>
					<td align="center" class='inner col31 head1'><b>Act Mgr</b></td>
					<td align="center" class='inner col31 head1'><b>Prj Mgr</b></td>
					<td align="center" class='inner col31 head1'><b>Service Provider</b></td>
					<td align="center" class='inner col31 head1'><b>Crm Product Name</b></td>
				
				</tr>
						</table>
				</div>
				  <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	               <table border="0" cellpadding="0" cellspacing="0" class='content1' id='tablelepmreport'>             
				<logic:present name="BulkSIUploadReport" scope="request">
					<logic:notEmpty  name="BulkSIUploadReport" scope="request"> 					
						<logic:iterate id="row" name="BulkSIUploadReport" indexId="recordId">
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
						<td align="left" class='inner col31'><bean:write  name="row" property="accountName"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="orderNumber"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="crm_service_opms_id"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="serviceName"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="installation_addressaa1"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="installation_addressaa2"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="installation_addressaa3"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="from_city"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="from_state"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="from_country"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="installation_addressab1"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="installation_addressab2"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="installation_addressab3"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="to_city"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="to_state"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="to_country"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="date_of_inst"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="date_of_act"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="bandwaidth"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="uom"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="lob"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="circle"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="zone"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="location_from"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="location_to"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="commited_sla"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="hub_location"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="platform"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="customer_logicalSINumber"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="fxSiId"/></td>
						<td align="left" class='inner col2'><bean:write  name="row" property="ipls"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="managed_yes_no"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="actmngname"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="prjmngname"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="service_provider"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="lineItemDescription"/>&nbsp;</td>
			
					</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty  name="BulkSIUploadReport">
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
	                    	<div class='horizontal-scrollbulkupload' onscroll='reposHorizontal(this);'>
	                        	<div>
	                        	</div>
	                    	</div>
	                	</td>
            		</tr>
				</table>
				<!-- End Scrolling and Freezing of Bulk SI Upload Report -->
							
			</table>
			</div>
		</fieldset>
	</html:form>
</body>
</html>
