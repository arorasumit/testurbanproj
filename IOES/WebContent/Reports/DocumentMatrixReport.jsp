<!-- [001] Gunjan Singla   -->
<!-- [002] CSR::20141901-R1-020404  Resource:Anil Kumar Date::6-Feb-2015 Changes:: added three extra columns   -->
<!-- [003] CSR::20150410-R1-021216 Resource: Raveendra Kumar Date:29 April-2015 Rearrangement column-->
<!-- [004] CSR::20160301-XX-022139 Resource: Priya Gupta Date: 17 May 2016 Added a new column -->
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
<title>Document Matrix Report</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ReportHeaderFreeze.js"></script>
<link href="./css/ReportHeaderFreeze.css" type="text/css" rel="stylesheet"> 
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
	myform.action="<%=request.getContextPath()%>/reportsAction.do?method=viewDocumentMatrixReport";	
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
	myform.action="<%=request.getContextPath()%>/reportsAction.do?method=viewDocumentMatrixReport";	
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewDocumentMatrixReport";	
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewDocumentMatrixReport&inExcel=true";
		showLayerExcel();
		myForm.submit();
	}
	
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.fromDate.value='';
	myForm.toDate.value='';
	myForm.custSegment.value=0 ;
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	/* var custSegment=document.getElementById('customerSegment'); */
	
		if((fromDate == null || trim(fromDate)== "" ) && (toDate == null || trim(toDate) == "" ))
		{
			alert("Please enter From and To Order Date ");
			return false;
		}
		if(fromDate == null || trim(fromDate)== "" )
		{
			alert("Please enter From Order Date ");
			return false;
		}
		if(toDate == null || trim(toDate)== "" )
		{
			alert("Please enter To Order Date ");
			return false;
		}
		if(dateCompare(fromDate,toDate)==1)
			{			
				alert("From  Date cannot be greater than To  Date");
				return false;
			}
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

function getCustomerSegmentValues(){
	
	
	var myForm=document.getElementById('searchForm');
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	var custSeg=jsonrpc.processes.populateCustomerSegmentDetail();
	/* var combomain = document.getElementById("customerSegment");	 */
	 var combomain =myForm.custSegment;
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
	 var custSegm='<%=request.getAttribute("CustSegment")%>';
	 if(custSegm !='null' && custSegm !=''){
			
		    combomain.value=custSegm;
		   }
		
}
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload="javascript:maxLimitChecker(document.getElementById('searchForm'),getCustomerSegmentValues(),'<%=ApplicationFlags.ReportsExcelMaxSize%>','<%=Messages.getMessageValue("ReportsExcelMaxSizeMessage") %>');">
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/reportsAction" styleId="searchForm" method="post">
		<bean:define id="formBean" name="reportsBean"></bean:define>
		<html:hidden property="toExcel"/>
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="reportProcessForLimit"/>
        <html:hidden property="reportCurrentCount"/>
		<html:hidden property="interfaceId"/>
		<html:hidden property="isDumpAvailable"/>
		<html:hidden property="dumpFileName"/>
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
				<tr ><td style="color: white" align="left"><span>Document Matrix Report</span>  
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
				
						<td align="left" style="font-size:9px;">From COPC Approval Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="datefrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
					
						<td align="right" style="font-size:9px;">To COPC Approval Date:</td>
						<td nowrap><html:text  property="toDate" styleId="dateTo" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.toDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						
						<td align="right" style="font-size:9px;">Customer Segment:</td>	
						<td>
						<html:select property="custSegment" style="width:175px;" styleId="customerSegment">
						<html:option value="0">Select Customer Segment </html:option>
						</html:select></td>
						<!--  <td>
						 <select id="customerSegment" styleClass="inputBorder1" name="custsegment" style="width:175px;" property="custSegment" >
							<option value="0">Select Customer Segment </option>
						</select> &nbsp;	
						</td>  -->
						<td align="center" valign="top">
							<div class="searchBg" ><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
						<td align="left" >
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td align="left" >
							<div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div>
						</td>
						</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Document Matrix Report</b></legend>
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
			
				<!-- start Scrolling and Freezing of Disconnection Change Report-->
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
					<td align="center" class='inner col2 head1'><b>Account Number</b></td>
					<td align="center" class='inner col4 head1'><b>Account Name </b></td>
					<td align="center" class='inner col1 head1'><b>Order Type </b></td>
					<td align="center" class='inner col4 head1'><b>Change Order Subtype</b></td>
					<td align="center" class='inner col2 head1' ><b>Account Manager</b></td>
					<td align="center" class='inner col2 head1' ><b>Sales Co-ordinator</b></td>
					<td align="center" class='inner col2 head1' ><b>Region</b></td>
					<td align="center" class='inner col31 head1' ><b>customer segment </b></td>
					<td align="center" class='inner col31 head1' ><b>Order Number</b></td>
					<td align="center" class='inner col31 head1' ><b>Logical SI No </b></td>
					<td align="center" class='inner col2 head1' ><b>Service Number </b></td>
					<td align="center" class='inner col2 head1' ><b>Stage </b></td>
					<td align="center" class='inner col2 head1' ><b>COPC Approver</b></td>
					<td align="center" class='inner col5 head1' ><b>COPC Approver ID</b></td>
					<td align="center" class='inner col31 head1' ><b>COPC Approver Remarks </b></td>
					<td align="center" class='inner col2 head1' ><b>COPC Approval date </b></td>
					<td align="center" class='inner col31 head1' ><b>Any deviation </b></td>
					<td align="center" class='inner col31 head1' ><b>Non-India touching link </b></td>
					<td align="center" class='inner col2 head1' ><b>CAF </b></td>
					<td align="center" class='inner col2 head1' ><b>PO</b></td>
					<td align="center" class='inner col2 head1' ><b>Customer Agreement</b></td>
					<td align="center" class='inner col2 head1' ><b>e-PCN\NFA </b></td>
					<td align="center" class='inner col2 head1' ><b>Feasibility </b></td>
					<td align="center" class='inner col2 head1' ><b>ISP </b></td>
					<td align="center" class='inner col2 head1' ><b>LOU </b></td>
					<td align="center" class='inner col2 head1' ><b>Network Diagram </b></td>
					<td align="center" class='inner col2 head1' ><b>OFS</b></td>
					<td align="center" class='inner col2 head1' ><b>PI/Third Party Related</b></td>
					<td align="center" class='inner col2 head1' ><b>PCD </b></td>
					<td align="center" class='inner col2 head1' ><b>RFP </b></td>
					<td align="center" class='inner col2 head1' ><b>SOW</b></td>
					<td align="center" class='inner col2 head1' ><b>T&C </b></td>
					<td align="center" class='inner col2 head1' ><b>Others</b></td>
					<!-- [004] -->
					<td align="center" class='inner col2 head1' ><b>Previous Month Trigger Approval</b></td>
					</tr>
						</table>
				</div>                		 	                		
	          <div id='contentscroll' class='content1' onscroll='reposHead(this);'>
	               <table border="0" cellpadding="0" cellspacing="0" class='content1' id='tabledocumentreport'>
						<logic:present name="DocumentMatrixReport" scope="request">
					<logic:notEmpty  name="DocumentMatrixReport" scope="request"> 					
						<logic:iterate id="row" name="DocumentMatrixReport" indexId="recordId">
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
						<td align="center" class='inner col2'><bean:write  name="row" property="accountNo"/>&nbsp;</td>
						<td align="left" class='inner col4'><bean:write  name="row" property="accountName"/>&nbsp;</td>
						<td align="left" class='inner col1'><bean:write  name="row" property="ordType"/>&nbsp;</td>
					    <td align="left" class='inner col4'><bean:write  name="row" property="ordSubType"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="accntMgr"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="salesCo"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="region"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="custSegment"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="orderNo"/>&nbsp;</td>
						<td align="center" class='inner col31'><bean:write  name="row" property="lsiNo"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="serviceNo"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="stage"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="copcApprovr"/>&nbsp;</td>
						<td align="center" class='inner col5'><bean:write  name="row" property="copcApprovrID"/>&nbsp;</td>
					    <td align="left" class='inner col31'><bean:write  name="row" property="copcApprovrRemarks"/>&nbsp;</td>
					    <td align="left" class='inner col2'><bean:write  name="row" property="copcAprDate"/>&nbsp;</td>
					    <!--START[002]-->						
						<td align="left" class='inner col31'><bean:write  name="row" property="docAnyDeviation"/>&nbsp;</td>
						<td align="left" class='inner col31'><bean:write  name="row" property="docNonIndiaTouchLink"/>&nbsp;</td><!--END[002]-->
					    <td align="left" class='inner col2'><bean:write  name="row" property="docCAF"/>&nbsp;</td>
					    <td align="left" class='inner col2'><bean:write  name="row" property="docPO"/>&nbsp;</td>
					    <td align="left" class='inner col2'><bean:write  name="row" property="docCustAgreemnt"/>&nbsp;</td>
					    <td align="left" class='inner col2'><bean:write  name="row" property="docPCN"/>&nbsp;</td>
					    <td align="left" class='inner col2'><bean:write  name="row" property="docFeasibility"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="docISP"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="docLOU"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="docNtwrk"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="docOFS"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="docThirdParty"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="docPCD"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="docRFP"/>&nbsp;</td>
						<td align="center" class='inner col2'><bean:write  name="row" property="docSOW"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="docTnC"/>&nbsp;</td>
						<td align="left" class='inner col2'><bean:write  name="row" property="docOther"/>&nbsp;</td>
						<!-- [004] -->
						<td align="left" class='inner col2'><bean:write  name="row" property="docPMTA"/>&nbsp;</td>						
						</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty  name="DocumentMatrixReport">
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
	                    	<div class='horizontal-scrolldocumentmatrix' onscroll='reposHorizontal(this);'>
	                        	<div>
	                        	</div>
	                    	</div>
	                	</td>
            		</tr>
				</table>
				<!-- End Scrolling and Freezing of document matrix Report-->				
			</table>
			</div>
		</fieldset>
	</html:form>
</body>
</html>
