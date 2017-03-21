<!-- prepared by priya -->
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<title>Parallel Upgrade Report</title>
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
	myform.method.value='viewParallelUpgradeReport';	
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
	myform.method.value='viewParallelUpgradeReport';	
	showLayer();
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewParallelUpgradeReport";
		showLayer();
		myForm.submit(); 
	}
}

function getAllCustomerSegment(){
	   jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	   var myForm=document.getElementById('searchForm');
	   var custSeg = jsonrpc.processes.getAllCustomerSegmentList();
	   var combomain =myForm.customerSegment;
	   var iCountRows = combomain.length;
	
	   for (var i = 1; i <  iCountRows; i++)
	   {
		   combomain.remove(i);
	   }
	 
	 for(var j=0;j<custSeg.list.length;j++)
  {
		
	       var optionmain = document.createElement("option");
	       
	        optionmain.text = custSeg.list[j].cus_segment;
	   		optionmain.title = custSeg.list[j].cus_segment;				
			optionmain.value = custSeg.list[j].customerSegmentId;
			try 
			{
				combomain.add(optionmain, null); 
				
			}
			catch(error) 
			{
				combomain.add(optionmain); 
				
			} 
 	}
		 var custSegm='<%=request.getAttribute("custSeg")%>';	
		 if(custSegm !='null' && custSegm !=''){
		    combomain.value=custSegm;
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewParallelUpgradeReport&inExcel=true";
		showLayerExcel();
		myForm.submit();
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.changeTypeId.value='' ; 	
	myForm.fromServiceNo.value='' ; 
	myForm.toServiceNo.value='' ;	
	myForm.fromDate.value='' ;	
	myForm.toDate.value='' ;
	myForm.customerSegment.value='' ;	
	myForm.excludeCompOrders.value='' ;	
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	var changeTypeId=myForm.changeTypeId.value;
	var fromServiceNo=myForm.fromServiceNo.value;
	var toServiceNo=myForm.toServiceNo.value;
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	var customerSegment=myForm.customerSegment.value;
	var excludeCompOrders=myForm.excludeCompOrders.value;
	
	
	/*if((fromDate == null & toDate == null) || ( trim(fromDate)=="" & trim(toDate) == "")){
		alert("Please enter From Date and To Date!");
	return false;
	
	} */		
	if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
	{
		alert("Please enter COPC Approval From Date");
		return false;
	}
	else if((toDate == null || trim(toDate)== "" ) && (fromDate != null && trim(fromDate) != "" ))
	{
		alert("Please enter COPC Approval To Date");
		return false;
	}
	
	if(dateCompare(fromDate,toDate)==1)
	{			
			alert("COPC Approval From Date cannot be greater than COPC Approval To Date");
			return false;
	}
	if((fromServiceNo == 0 || trim(fromServiceNo)== "" ) && (toServiceNo != 0 && trim(toServiceNo) != "" ))
	{
			alert("Please enter From ServiceNo ");
			return false;
	}
	else if((fromServiceNo == 0 || trim(fromServiceNo)== "" ) && (toServiceNo != 0 && trim(toServiceNo) != "" ))
		{
			alert("Please enter To ServiceNo ");
			return false;
		}
	if(fromServiceNo > toServiceNo)
		{			
			alert("From ServiceNo cannot be greater than To ServiceNo");
			return false;
		}

			
	return true;
}
function resetFilterCriterion(){
	var myForm=document.getElementById('searchForm');
	var changeTypeId=myForm.changeTypeId.value;
	var fromServiceNo=myForm.fromServiceNo.value;
	var toServiceNo=myForm.toServiceNo.value;
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	var customerSegment=myForm.customerSegment.value;
	var excludeCompOrders=myForm.excludeCompOrders.value;
	if(myForm.changeTypeId.value != null || trim(myForm.changeTypeId.value)!="" ) {
	
	}
}
function downloadDump(formId,fileName)
{
	myForm=document.getElementById(formId);
	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=getDumpFile&fileName="+fileName;
	//showLayer();
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
<body onload="javascript:maxLimitChecker(document.getElementById('searchForm'),'<%=ApplicationFlags.ReportsExcelMaxSize%>','<%=Messages.getMessageValue("ReportsExcelMaxSizeMessage") %>');getAllCustomerSegment();">

<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/reportsAction" styleId="searchForm" method="post">
		<bean:define id="formBean" name="reportsBean"></bean:define>

		<html:hidden property="interfaceId"/>
		<html:hidden property="isDumpAvailable"/>
		<html:hidden property="dumpFileName"/>
		<html:hidden property="reportProcessForLimit"/>
		<html:hidden property="reportCurrentCount"/>
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

			<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
				<tr ><td align="left"><span>PARALLEL UPGRADE REPORT</span>
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
					<td width="90px"/>
						<td align="right" style="font-size:9px"> Order Type:</td>
						<td align="left" nowrap>
							<html:select  property="changeTypeId" style="width:90px;float:left;"  >
							<html:option value="0">All</html:option>
							<html:optionsCollection value="changeTypeId" label="changeTypeName" name="changeTypes"/>
							</html:select>
						</td>
					<td width="10px"/>
						<td align="right" style="font-size:9px;">FROM SERVICE NO:</td>
						<td align="left"><html:text  property="fromServiceNo" style="width:75px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text></td>
						
						<td align="right" style="font-size:9px">TO SERVICE NO:</td>
						<td align="left">
							<html:text  property="toServiceNo" style="width:75px;float:left;" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
						</td>
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
						<td align="left">
						

							<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>

					</tr>
					<tr>
					<td width="10px"/>
						<td align="right" style="font-size:9px;">COPC Approval From Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="datefrom" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">COPC Approval To Date:</td>
						<td nowrap><html:text  property="toDate" styleId="dateto" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
					
						<td align="right" style="font-size:9px">Customer Segment:</td>
							
						<td align="left" nowrap>
							<td>     
								<html:select property="customerSegment"  style="width:190px;"  styleId="customerSegment">
								<html:option  value="0">Select Customer Segment </html:option>
								</html:select>
							</td>
						</td>	
					<td width="90px"/>
						<td align="right" style="font-size:9px"> Exclude Completed Orders Before:</td>
						<td align="left" nowrap>
							<html:select  property="excludeCompOrders"  style="width:90px;float:left;"  >
							<html:option value="comp_order_date">1/04/2016</html:option>
							<html:option value="">None</html:option>
							</html:select>
						</td>
					<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td align="center" valign="top">
							
						</td>
					</tr>
				</table>
			</fieldset>	
			<fieldset border="1" align="center" class="border2" >
				<legend> <b>Service Level Remarks Report</b></legend>
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
			<!-- Start Scrolling and Freezing of ServiceLevelRemarks Report-->
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
									<td class='inner col3 head1'><b>Customer Segment</b></td>
									<td class='inner col3 head1'><b>Order Type</b></td>
									<td class='inner col3 head1'><b>Change Type</b></td>
									<td class='inner col3 head1'><b>Customer Name</b></td>
									<td class='inner col3 head1'><b> 
									<logic:present name="ParallelUpgradeReportList" scope="request">
										<logic:notEmpty  name="ParallelUpgradeReportList" scope="request"> 
											<a href="#" onclick="sortSubmit('ORDERNO')">CRM_Order_ID</a>
										</logic:notEmpty>
										<logic:empty  name="ParallelUpgradeReportList" scope="request">
										CRM_Order_ID
										</logic:empty>
									</logic:present> 
									</b>
									</td>	
									<td class='inner col3 head1'><b>Order Date</b></td>
									<td class='inner col3 head1'><b>COPC Approval Date</b></td>
									<td class='inner col3 head1'><b>Logical Circuit Id</b></td>
									<td class='inner col3 head1'><b>Service No</b></td>
									<td class='inner col3 head1'><b>Change Reason</b></td>
									<td class='inner col3 head1'><b>LSI_Disconnection_SR_No</b></td>
									<td class='inner col3 head1'><b>Service_Stage</b></td>
									<td class='inner col3 head1'><b>Service BT Action Date</b></td>
									<td class='inner col3 head1'><b>Attribute Remarks</b></td>
									<td class='inner col3 head1'><b>Bin</b></td>
									<td class='inner col3 head1'><b>OLD LSI</b></td>
									<td class='inner col3 head1'><b>OLD LSI _Latest stage</b></td>		
									<td class='inner col3 head1'><b>OLD LSI _BT_Action_Date</b></td>
									<td class='inner col3 head1'><b>OLD_LSI_Disconnection_SRNO</b></td>
								</tr>
							</table>
						</div>
						<div id='contentscroll' class='content1' onscroll='reposHead(this);'>
							<table border="0" cellpadding="0" cellspacing="0" class='content1' id='tableserviceLevelRemarksReport'>
				<logic:present name="ParallelUpgradeReportList" scope="request">
					<logic:notEmpty  name="ParallelUpgradeReportList" scope="request"> 					
						<logic:iterate id="row" name="ParallelUpgradeReportList" indexId="recordId">
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
												<td align="left" class='inner col3'><bean:write  name="row" property="customerSegment"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="orderType"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="changeType"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="customername"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="crm_order_id"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="orderDate"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="copcApprovalDate"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="logical_si_no"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="serviceNo"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="changeReason"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="lsi_disconnection_SRNO"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="service_stage"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="serviceBTActionDate"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="attribute_remarks"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="bin"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="old_lsi"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="old_lsi_lateststage"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="old_lsi_BT_ActionDate"/>&nbsp;</td>
												<td align="left" class='inner col3'><bean:write  name="row" property="old_lsi_disconnection_SRno"/>&nbsp;</td>
							</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty  name="ParallelUpgradeReportList">
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
	                   	<div class='horizontal-scrollServiceLevelRemarksReport' onscroll='reposHorizontal(this);'>
	                       	<div>
	                       	</div>
	                   	</div>
	               	</td>
				</tr>
			</table>
			<!-- End Scrolling and Freezing of ServiceLevelRemarks Report-->	
		</fieldset>
	</html:form>
</body>
</html>