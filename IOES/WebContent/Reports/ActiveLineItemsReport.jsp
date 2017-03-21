<!--Tag Name  Resource Name  Date		CSR No			       Description -->
<!-- [001]    Gunjan Singla                                    added jsonrpc method populatereportusagedetails -->
<!-- [002]    Gunjan Singla  22-sep-14  CBR_20140704-XX-020224 Global Data Billing Efficiencies Phase1 -->
<%-- [003]   Neha Maheshwari    04-Dec-2014   CSR20141014-R2-020535-Service Segment Configuration-SMB --%>
<!-- [004]    Neha Maheshwari   14-Apr-15     CSR-20150212-R1-021088  Addition of Column in Active and Pending Line Item Reports -->
<%-- [005]    Neha Maheshwari 10-Jun-2015 20141219-R2-020936 Billing Efficiency Program Reports --%>
<%-- [006]    Neha Maheshwari 07-Sep-2015 20150819-XX-021651 TimeStamp Reports --%>
<%-- [007]   Priya Gupta      25-Jan-2016	Added 4 new columns --%>
<!-- [008]  Gunjan Singla  6-May-16   20160418-R1-022266  Added columns -->
<!-- [009]  Nancy Singla 2-jun-2016   Added one new column ePCN NO      --> 
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
<title>Active Line Items - Report</title>
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
	myform.method.value='viewActiveLineItemsList';	
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
	myform.method.value='viewActiveLineItemsList';	
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
	if(!isBlankForm()){
		
		return;
	} 
	else {
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewActiveLineItemsList";
		showLayer();
		myForm.submit();
	}
}

function getAllActiveProductName(){
	   jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	   var myForm=document.getElementById('searchForm');
	   var productNameList = jsonrpc.processes.getAllActiveProductNameList();
	   var combomain =myForm.productName;
	   var iCountRows = combomain.length;
	
	   for (var i = 1; i <  iCountRows; i++)
	   {
		   combomain.remove(i);
	   }
	 
	 for(var j=0;j<productNameList.list.length;j++)
     {
		
	       var optionmain = document.createElement("option");
	       
	        optionmain.text = productNameList.list[j].productName;
	   		optionmain.title = productNameList.list[j].productName;				
			optionmain.value = productNameList.list[j].productId;
			try 
			{
				combomain.add(optionmain, null); 
				
			}
			catch(error) 
			{
				combomain.add(optionmain); 
				
			} 
    	}
    	var product='<%=request.getAttribute("product")%>';	
		 if(product !='null' && product !=''){
		    combomain.value=product;
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewActiveLineItemsList&inExcel=true";
		showLayerExcel();
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

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.orderType.value='' ; 
	myForm.fromDate.value='' ;	
	myForm.toDate.value='' ;	
	myForm.fromOrderNo.value='';
	myForm.toOrderNo.value='';
}

function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	
	var orderStage=myForm.orderType.value;
	var fromDate=myForm.fromDate.value;
	var toDate=myForm.toDate.value;
	var fromorder=myForm.fromOrderNo.value;
	var toorder=myForm.toOrderNo.value;
	
		if((fromDate == null & toDate == null) || ( trim(fromDate)=="" & trim(toDate) == "")){
			alert("Please enter COPC approval From Date and COPC approval To Date!");
			return false;
		}
    else{
        
        if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
		{
			alert("Please enter COPC approval From Date");
			return false;
		}
		else if((toDate == null || trim(toDate)== "" ) && (fromDate != null && trim(fromDate) != "" ))
		{
			alert("Please enter COPC approval To Date");
			return false;
		}
		if(dateCompare(fromDate,toDate)==1)
		{			
				alert("COPC approval From Date cannot be greater than COPC approval To Date");
				return false;
		}
	}
		return true;
	/*}*/
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

<body onload="javascript:maxLimitChecker(document.getElementById('searchForm'),'<%=ApplicationFlags.ReportsExcelMaxSize%>','<%=Messages.getMessageValue("ReportsExcelMaxSizeMessage") %>');getAllCustomerSegment();getAllActiveProductName();">

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
				<tr ><td align="left"><span>ACTIVE LINE ITEMS REPORT</span> 
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
							<html:select  property="orderType" style="width:90px;float:left;"  >
							<html:option value="">SELECT</html:option>
							<html:option value="New">NEW</html:option>
							<html:option value="Change">CHANGE</html:option>
							</html:select>
						</td>
						<td align="right" style="font-size:9px;">COPC Approval From Date:</td>
						<td nowrap><html:text  property="fromDate" styleId="dateFrom" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar(searchForm.fromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Select Date"></a>
							</font>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">COPC Approval To Date:</td>
						<td nowrap><html:text  property="toDate" styleId="dateTo" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
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
							
							<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
							
							
						</logic:present>
					</tr>
					<tr>
							<td width="10px"/>
							<td align="right" style="font-size:9px;">FROM ORDER NO:</td>
							<td align="left"><html:text  property="fromOrderNo"  maxlength="8"  style="width:75px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text></td>
							<td align="right" style="font-size:9px">TO ORDER NO:</td>
							<td align="left">
								<html:text  property="toOrderNo"   style="width:75px;float:left;" maxlength="8" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
							</td>
							<td align="right" style="font-size:9px">Party No:</td>
							<td align="left">
								<html:text  property="partyNo"   style="width:75px;float:left;" maxlength="8" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
							</td>
							<td align="right" style="font-size:9px">Customer Segment:</td>
							<%-- <td align="left">
								<html:text  property="customerSegment" styleClass="inputBorder1"  style="width:75px;float:left;" maxlength="8" onblur="if( trim(this.value).length>0);" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
							</td> --%>

							<td align="left" nowrap>
							<td>     
								<html:select property="customerSegment"  style="width:190px;"  styleId="customerSegment">
									<html:option  value="0">Select Customer Segment </html:option>
								</html:select>
							</td>	
							<%-- <html:select  property="customerSegment">
							<html:option value="">SELECT</html:option>
							<html:option value="BHARTI">BHARTI</html:option>
							<html:option value="RESELLER">RESELLER</html:option>
							<html:option value="CARRIER">CARRIER</html:option>
							<html:option value="AES CORP">AES CORP</html:option>
							<html:option value="AES CHANNEL">AES CHANNEL</html:option>
							<html:option value="CORPORATE">CORPORATE</html:option>
							<html:option value="SME">SME</html:option>
							<html:option value="RETAIL">RETAIL</html:option>
							<html:option value="CHANNELS">CHANNELS</html:option>
							<html:option value="SMB CHANNELS">SMB CHANNELS</html:option>
							<html:option value="NDL CHANNELS">NDL CHANNELS</html:option>

							</html:select> --%>
						</td>

							<td align="right" style="font-size:9px">PRODUCT NAME</td>
						    <td align="left" nowrap>
							    <td>     
									<html:select property="productName"  style="width:190px;"  styleId="productName">
										<html:option  value="0">Select Product Name </html:option>
									</html:select>
								</td>
						</td>
					 </tr>
					</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" valign="top">
			<legend> <b>Active Line Items</b></legend>
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
			
			<!-- Start Scrolling and Freezing of Active Line Report -->
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
			<td align="center" class='inner col31 head1' ><b>Party Number</b></td>
			<td align="center" class='inner col31 head1' ><b>Logical Circuit Id</b></td>
			<td align="center" class='inner col31 head1' ><b>Cust Logical Si Id</b></td>
			<td align="center" class='inner col4 head1' ><b>Service Name</b></td>
			<td align="center" class='inner col4 head1' ><b>Line Name</b></td>
			<td align="center" class='inner col1 head1' ><b>Charge Type</b></td>
			<td align="center" class='inner col1 head1' ><b>Charge Type Code</b></td>
			<td align="center" class='inner col4 head1' ><b>Charge Name</b></td>
			<td align="center" class='inner col31 head1' ><b>Frequency</b></td>
			<td align="center" class='inner col31 head1' ><b>Bill Period</b></td>
			<td align="center" class='inner col31 head1' ><b>Charge Start Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Account Number</b></td>
			<td align="center" class='inner col31 head1' ><b>Credit Period</b></td>
			<td align="center" class='inner col31 head1' ><b>Currency</b></td>
			<td align="center" class='inner col31 head1' ><b>Legal Entity</b></td>
			<td align="center" class='inner col31 head1' ><b>Billing Mode</b></td>
			<td align="center" class='inner col31 head1' ><b>Bill Type</b></td>
			<td align="center" class='inner col31 head1' ><b>Bill Format</b></td>
			<td align="center" class='inner col31 head1' ><b>Licence Company</b></td>
			<td align="center" class='inner col31 head1' ><b>Taxation</b></td>
			<!-- [002] start -->
			<td align="center" class='inner col31 head1'><b>Tax Exemption Reason</b></td>
			<!-- [002] end -->
			<td align="center" class='inner col31 head1' ><b>Billing Level</b></td>
			<td align="center" class='inner col2 head1' ><b>Billing Level Number</b></td>
			<td align="center" class='inner col2 head1' ><b>Store</b></td>
			<td align="center" class='inner col1 head1' ><b>Hardware Type</b></td>
			<td align="center" class='inner col2 head1' ><b>Form C Available</b></td>
			<td align="center" class='inner col1 head1' ><b>Nature Of Sale</b></td>
			<td align="center" class='inner col1 head1' ><b>Type Of Sale</b></td>
			<td align="center" class='inner col31 head1' ><b>Pri Location</b></td>
			<td align="center" class='inner col31 head1' ><b>Sec Location</b></td>
			<td align="center" class='inner col31 head1' ><b>Po Number</b></td>
			<td align="center" class='inner col31 head1' ><b>Po Date</b></td>
			<td align="center" class='inner col4 head1' ><b>Annotation</b></td>
			<td align="center" class='inner col31 head1' ><b>Fx Status</b></td>
			<td align="center" class='inner col1 head1' ><b>Fx Sd Chg Status</b></td>
			<td align="center" class='inner col1 head1' ><b>Fx Ed Chg Status</b></td>
			<td align="center" class='inner col31 head1' ><b>Token No</b></td>
			<td align="center" class='inner col31 head1' ><b>Last Update Date</b></td>
			<td align="center" class='inner col1 head1' ><b>Billing Trig Flag</b></td>
			<td align="center" class='inner col31 head1' ><b>Pm Prov Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Loc Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Billing Trig Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Challen No</b></td>
			<td align="center" class='inner col31 head1' ><b>Challen Date</b></td>
			<td align="center" class='inner col51 head1' ><b>Child Account Number</b></td>
			<td align="center" class='inner col31 head1' ><b>Child Account Fx Status</b></td>
			<td align="center" class='inner col31 head1' ><b>Orderdate</b></td>
			<td align="center" class='inner col31 head1' ><b>Approved Date</b></td>
			<td align="center" class='inner col2 head1' ><b>Order Type</b></td>
			<td align="center" class='inner col31 head1' ><b>PM Approved Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Copc Approved Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Bill Trg Create Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Cust Logical Si No</b></td>
			<td align="center" class='inner col1 head1' ><b>Kms Distance</b></td>
			<td align="center" class='inner col1 head1' ><b>Ratio</b></td>
			<td align="center" class='inner col31 head1' ><b>Product</b></td>
			<td align="center" class='inner col31 head1' ><b>Subproduct</b></td>
			<td align="center" class='inner col1 head1' ><b>Hardware Flag</b></td>
			<td align="center" class='inner col1 head1' ><b>Charge Type Id</b></td>
			<td align="center" class='inner col31 head1' ><b>Service Stage</b></td>
			<td align="center" class='inner col31 head1' ><b>Order Stage</b></td>
			<td align="center" class='inner col31 head1' ><b>Order Creation Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Customer Service Rfs Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Cust Po Receive Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Cust Po Number</b></td>
			<td align="center" class='inner col31 head1' ><b>Cust Po Date</b></td>
			<td align="center" class='inner col31 head1' ><b>Charge Status</b></td>
			<td align="center" class='inner col31 head1' ><b>Loc Number</b></td>
			<td align="center" class='inner col51 head1' ><b>Address</b></td>
			<td align="center" class='inner col31 head1' ><b>Circuit Id</b></td>
			<td align="center" class='inner col2 head1' ><b>Cancel By</b></td>
			<td align="center" class='inner col2 head1' ><b>Cancel Date</b></td>
			<td align="center" class='inner col2 head1' ><b>Cancel Reason</b></td>
			<td align="center" class='inner col31 head1' ><b>Sub Type</b></td>
			<td align="center" class='inner col31 head1' ><b>Opms Account Id</b></td>
			<td align="center" class='inner col2 head1' ><b>Region</b></td>
			<td align="center" class='inner col2 head1' ><b>Bandwidth</b></td>
			<td align="center" class='inner col31 head1' ><b>Vertical</b></td>
			<td align="center" class='inner col31 head1' ><b>Mocn Number</b></td>
			<td align="center" class='inner col31 head1' ><b>Project Mgr</b></td>
			<td align="center" class='inner col31 head1' ><b>Account Manager</b></td>
			<td align="center" class='inner col2 head1' ><b>Rate Code</b></td>
			<td align="center" class='inner col2 head1' ><b>Last Mile Media</b></td>
			<td align="center" class='inner col2 head1' ><b>Last Mile Remarks</b></td>
			<td align="center" class='inner col2 head1' ><b>Chargable Distance</b></td>
			<td align="center" class='inner col2 head1' ><b>Last Mile Provider</b></td>
			<td align="center" class='inner col2 head1' ><b>Link Type</b></td>
			<td align="center" class='inner col51 head1' ><b>Dispatch Address</b></td>
			<td align="center" class='inner col2 head1' ><b>Indicative Value</b></td>
			<td align="center" class='inner col31 head1' ><b>Product Name</b></td>
			<td align="center" class='inner col31 head1' ><b>Party Name</b></td>
			<td align="center" class='inner col51 head1' ><b>Billing Location From</b></td>
			<td align="center" class='inner col2 head1' ><b>Demo Order</b></td>
			<td align="center" class='inner col31 head1' ><b>Crm Product Name</b></td>
			<td align="center" class='inner col31 head1' ><b>Billing Location To</b></td>
			<td align="center" class='inner col2 head1' ><b>Location From</b></td>
			<td align="center" class='inner col2 head1' ><b>Location To</b></td>
			<td align="center" class='inner col2 head1' ><b>Billling Bandwidth</b></td>
			<td align="center" class='inner col2 head1' ><b>Bill Uom</b></td>
			<td align="center" class='inner col2 head1' ><b>Bl Source</b></td>
			<td align="center" class='inner col2 head1' ><b>Lineitemnumber</b></td>
			<td align="center" class='inner col2 head1' ><b>Order Number</b></td>
			<td align="center" class='inner col31 head1' ><b>Inv Amt</b></td>
			<td align="center" class='inner col31 head1' ><b>Amt</b></td>
			<td align="center" class='inner col31 head1' ><b>Total Amount</b></td>
			<td align="center" class='inner col2 head1' ><b>Period In Month</b></td>
			<td align="center" class='inner col31 head1' ><b>Tot Po Amt</b></td>
			<td align="center" class='inner col2 head1' ><b>Party Id</b></td>
			<td align="center" class='inner col2 head1' ><b>Cust Account Id</b></td>
			<td align="center" class='inner col2 head1' ><b>M6 Product Id</b></td>
			<td align="center" class='inner col2 head1' ><b>M6 Order Id</b></td>
			<td align="center" class='inner col31 head1' ><b>Cust Tot Po Amt</b></td>
			<td align="center" class='inner col2 head1' ><b>M6 Order No</b></td>
			<td align="center" class='inner col2 head1' ><b>Business Serial No</b></td>
			<td align="center" class='inner col2 head1' ><b>Order Line Id</b></td>
			<!-- [002] start -->
			<td align="center" class='inner col2 head1'><b>Main Line Item No</b></td>
			<!-- [002] start -->
			<td align="center" class='inner col2 head1' ><b>Ib Service List Id</b></td>
			<td align="center" class='inner col2 head1' ><b>Ib Pk Charges Id</b></td>
			<td align="center" class='inner col2 head1' ><b>Pk Charges Id</b></td>
			<td align="center" class='inner col2 head1' ><b>Contract Period Mnths</b></td>
			<td align="center" class='inner col2 head1' ><b>Annual Rate</b></td>
			<td align="center" class='inner col2 head1' ><b>Service Number</b></td>
			<td align="center" class='inner col31 head1' ><b>Po Expiry Date</b></td>					
			<td align="center" class='inner col2 head1' ><b>Opportunity Id</b></td>
			<td align="center" class='inner col3 head1' ><b>Group Name</b></td>						
					<!-- vijay add few more columns -->			
			<td align="center" class='inner col3 head1'><b>A Site - Customer Inst. Address 1 </b></td>
			<td align="center" class='inner col3 head1'><b>A Site - Customer Inst. Address 2 </b></td>
			<td align="center" class='inner col3 head1'><b>A Site - Customer Inst. Address 3 </b></td>
			<td align="center" class='inner col3 head1'><b>B Site - Site Name </b></td>
			<td align="center" class='inner col3 head1'><b>B Site - Line Address 1 </b></td>
			<td align="center" class='inner col3 head1'><b>B Site - Line Address 2 </b></td>
		<!-- vijay end -->
                        <!-- Shourya add few more columns -->	

				<td align="center" class='inner col3 head1'><b>Opms LineItemNumber</b></td>
				<td align="center" class='inner col3 head1'><b>Old LSI</b></td>
                <%--[003] Start--%>
				<td align="center" class='inner col3 head1'><b>Service Segment</b></td>
                <%--[003] End--%>
				<!-- Shourya end -->
			<%--[004] Start--%>
			<td align="center" class='inner col3 head1'><b>Installation From City</b></td>
			<td align="center" class='inner col3 head1'><b>Installation To City</b></td>
			<td align="center" class='inner col3 head1'><b>Installation From State</b></td>
			<td align="center" class='inner col3 head1'><b>Installation To State</b></td>
			<td align="center" class='inner col3 head1'><b>Billing Contact Name</b></td>
			<td align="center" class='inner col3 head1'><b>Billing Contact Number</b></td>
			<td align="center" class='inner col3 head1'><b>Billing EmailId</b></td>
			<%--[004] End--%>
			<%--Satyapan OSP--%>
			<td align="center" class='inner col3 head1'><b>OSPTagging Required</b></td>
			<td align="center" class='inner col3 head1'><b>OSPRegistration No</b></td>
			<td align="center" class='inner col3 head1'><b>OSPRegistration Date</b></td>
			<td align="center" class='inner col3 head1'><b>ISPTagging Required</b></td>
			<td align="center" class='inner col3 head1'><b>ISP Catageory</b></td>
			<td align="center" class='inner col3 head1'><b>ISPLicense No</b></td>
			<td align="center" class='inner col3 head1'><b>ISPLicense Date</b></td>
			<%--Satyapan OSP--%>
                        <%--[006] Start--%>
			<td align="center" class='inner col3 head1'><b>Published Date</b></td>
			<%--[006] End--%>
			<%--[005] Start--%>
			<td align="center" class='inner col3 head1'><b>Change Order Reason</b></td>
			<td align="center" class='inner col3 head1'><b>LD Clause</b></td>
			<%--[005] End--%>
			<%--[007] Start --%>
			<td align="center" class='inner col2 head1'><b>SalesForce Opportunity Number</b></td>

			<td align="center" class='inner col1 head1'><b>Network Type</b></td>
			<!-- <td align="center" class='inner col1 head1'><b>Partner id</b></td> [008]-->
			<%--[007] End --%>
			<!-- [008] start -->
			<td align="center" class='inner col1 head1'><b>Partner Code</b></td>
			<td align="center" class='inner col2 head1'><b>Partner Name</b></td>
			<td align="center" class='inner col3 head1'><b>Field Engineer</b></td>
			<!-- [008] end -->
			<!--[009] Start -->
			
			
			<td align="center" class='inner col3 head1'><b>ePCN No.</b></td>
			
			
			<!-- [009] End -->
			
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
								<td align="center" class='inner col31' ><bean:write  name="row" property="party_num"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="logicalSINo"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="custSINo"/>&nbsp;</td>
								<td align="left" class='inner col4' ><bean:write  name="row" property="serviceName"/>&nbsp;</td>
								<td align="left" class='inner col4' ><bean:write  name="row" property="linename"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="chargeTypeName"/>&nbsp;</td>
								<td align="center" class='inner col1' ><bean:write  name="row" property="chargeTypeID"/>&nbsp;</td>
								<td align="left" class='inner col4' ><bean:write  name="row" property="chargeName"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="chargeFrequency"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="billPeriod"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="startDate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="accountID"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="creditPeriodName"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="currencyName"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="entity"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="billingMode"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="billingTypeName"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="billingformat"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="licCompanyName"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="taxation"/>&nbsp;</td>
								<!-- [002] start -->
								<td align="left" class='inner col31'><bean:write  name="row" property="taxExemptReasonName"/>&nbsp;</td>
								<!-- [002] end -->
								<td align="left" class='inner col31' ><bean:write  name="row" property="billingLevelName"/>&nbsp;</td>
								<td align="center" class='inner col2' ><bean:write  name="row" property="billingLevelNo"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="store"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="hardwaretypeName"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="formAvailable"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="saleNature"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="saleTypeName"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="primaryLocation"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="seclocation"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="poNumber"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="poDate"/>&nbsp;</td>
								<td align="left" class='inner col4' ><bean:write  name="row" property="chargeAnnotation"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="fx_charge_status"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="fx_sd_charge_status"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="fx_Ed_Chg_Status"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="token_ID"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="modifiedDate"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="billingTriggerFlag"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="pm_pro_date"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="locDate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="billing_trigger_date"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="challenno"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="challendate"/>&nbsp;</td>
								<td align="left" class='inner col51' ><bean:write  name="row" property="fx_external_acc_id"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="child_account_creation_status"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="orderDate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="copcapprovaldate"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="orderType"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="pmapprovaldate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="copcapprovaldate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="billingtrigger_createdate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="custSINo"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="chargeable_Distance"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="ratio"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="productName"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="subProductName"/>&nbsp;</td>
								<td align="left" class='inner col1' ><bean:write  name="row" property="hardwareType"/>&nbsp;</td>
								<td align="center" class='inner col1' ><bean:write  name="row" property="chargeTypeID"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="serviceStage"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="orderStage"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="orderCreationDate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="rfsDate"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="poReceiveDate"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="custPoDetailNo"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="custPoDate"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="charge_status"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="LOC_No"/>&nbsp;</td>
								<td align="left" class='inner col51' ><bean:write  name="row" property="address1"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="m6cktid"/>&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="ordersubtype"/>&nbsp;</td>
									<!-- Shourya add few more columns -->
								<td align="left" class='inner col31'><bean:write  name="row" property="opms_Account_Id"/>&nbsp;</td>
								<!-- Shourya end-->
								<td align="left" class='inner col2' ><bean:write  name="row" property="region"/>&nbsp;</td>
								<td align="center" class='inner col2' ><bean:write  name="row" property="bandwaidth"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="vertical"/>&nbsp;</td>
								<!-- Shourya add few more columns -->
								<td align="left" class='inner col31' ><bean:write  name="row" property="mocn_no"/>&nbsp;</td>
								<!-- Shourya end -->
								<td align="left" class='inner col31' ><bean:write  name="row" property="projectManager"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="accountManager"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="rate_code"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="lmMedia"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="lmRemarks"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="chargeable_Distance"/>&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col51' ><bean:write  name="row" property="dispatchAddress1"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="indicative_value"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="productName"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="partyName"/>&nbsp;</td>
								<td align="left" class='inner col51' ><bean:write  name="row" property="billing_location_from"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="demo"/>&nbsp;</td>
								<td align="left" class='inner col31' ><bean:write  name="row" property="crm_productname"/>&nbsp;</td>
								<td align="left" class='inner col31' >&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="fromLocation"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="toLocation"/>&nbsp;</td>
								<td align="center" class='inner col2' ><bean:write  name="row" property="billing_bandwidth"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="billing_uom"/>&nbsp;</td>
								<td align="left" class='inner col2' ><bean:write  name="row" property="blSource"/>&nbsp;</td>
								<td align="center" class='inner col2' ><bean:write  name="row" property="serviceproductid"/>&nbsp;</td>
								<td align="center" class='inner col2' ><bean:write  name="row" property="orderNumber"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="chargeAmount"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="lineamt"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="chargesSumOfLineitem"/>&nbsp;</td>
								<td align="center" class='inner col2' ><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="totalPoAmt"/>&nbsp;</td>
								<td align="center" class='inner col2' ><bean:write  name="row" property="party_id"/>&nbsp;</td>
								<td align="center" class='inner col2' ><bean:write  name="row" property="crmAccountNoString"/>&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="center" class='inner col2' ><bean:write  name="row" property="m6OrderNo"/>&nbsp;</td>	
								<td align="center" class='inner col31' ><bean:write  name="row" property="cust_total_poamt"/>&nbsp;</td>
								<td align="center" class='inner col2' ><bean:write  name="row" property="m6OrderNo"/>&nbsp;</td>
								<!-- Shourya add few more columns -->
								<td align="left" class='inner col2'><bean:write  name="row" property="business_serial_n"/>&nbsp;</td>
								<!-- Shourya end -->
								<td align="center" class='inner col2' ><bean:write  name="row" property="serviceproductid"/>&nbsp;</td>
								<!-- [002] start -->
								<td align="center" class='inner col2'><bean:write  name="row" property="mainSpid"/>&nbsp;</td>
								<!-- [002] end -->
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="left" class='inner col2' >&nbsp;</td>
								<td align="center" class='inner col2' ><bean:write  name="row" property="pk_charge_id"/>&nbsp;</td>
								<td align="center" class='inner col2' ><bean:write  name="row" property="contractPeriod"/>&nbsp;</td>
								<td align="center" class='inner col2' ><bean:write  name="row" property="annual_rate"/>&nbsp;</td>
								<td align="center" class='inner col2' ><bean:write  name="row" property="serviceId"/>&nbsp;</td>
								<td align="center" class='inner col31' ><bean:write  name="row" property="poExpiryDate"/>&nbsp;</td>
								<td align="center" class='inner col2' ><bean:write  name="row" property="opportunityId"/>&nbsp;</td>
								<td align="center" class='inner col3' ><bean:write  name="row" property="groupName"/>&nbsp;</td>
														<!-- vijay add few more columns -->		
								<td align="left" class='inner col3'><bean:write  name="row" property="asiteAdd1"/>&nbsp;</td>			
								<td align="left" class='inner col3'><bean:write  name="row" property="asiteAdd2"/>&nbsp;</td>			
								<td align="left" class='inner col3'><bean:write  name="row" property="asiteAdd3"/>&nbsp;</td>			
								<td align="left" class='inner col3'><bean:write  name="row" property="bsiteName"/>&nbsp;</td>			
								<td align="left" class='inner col3'><bean:write  name="row" property="bsiteLineAdd1"/>&nbsp;</td>			
								<td align="left" class='inner col3'><bean:write  name="row" property="bsiteLineAdd2"/>&nbsp;</td>			
						<!-- vijay end -->
<!-- Shourya add few more columns -->
                                <td align="left" class='inner col3'><bean:write  name="row" property="opms_lineItemNumber"/>&nbsp;</td>
                                <td align="left" class='inner col3'><bean:write  name="row" property="oldLsi"/>&nbsp;</td>
                                <%--[003] Start--%>
                                <td align="left" class='inner col3'><bean:write  name="row" property="serviceSegment"/>&nbsp;</td>
                                <%--[003] End--%>
							<!-- Shourya end -->
							<%--[004] Start--%>
							<td align="center" class='inner col3'><bean:write  name="row" property="installationFromCity"/>&nbsp;</td>
							<td align="center" class='inner col3'><bean:write  name="row" property="installationToCity"/>&nbsp;</td>
							<td align="center" class='inner col3'><bean:write  name="row" property="installationFromState"/>&nbsp;</td>
							<td align="center" class='inner col3'><bean:write  name="row" property="installationToState"/>&nbsp;</td>
							<td align="center" class='inner col3'><bean:write  name="row" property="billingContactName"/>&nbsp;</td>
							<td align="center" class='inner col3'><bean:write  name="row" property="billingContactNumber"/>&nbsp;</td>
							<td align="center" class='inner col3'><bean:write  name="row" property="billingEmailId"/>&nbsp;</td>
							<%--[004] End--%>
							<%--Satyapan OSP/ISP--%>
							<td align="center" class='inner col3'><bean:write  name="row" property="isOspRequired"/>&nbsp;</td>
							<td align="center" class='inner col3'><bean:write  name="row" property="ospRegistrationNo"/>&nbsp;</td>
							<td align="center" class='inner col3'><bean:write  name="row" property="ospRegistrationDate"/>&nbsp;</td>
							<td align="center" class='inner col3'><bean:write  name="row" property="isIspRequired"/>&nbsp;</td>
							<td align="center" class='inner col3'><bean:write  name="row" property="ispCatageory"/>&nbsp;</td>
							<td align="center" class='inner col3'><bean:write  name="row" property="ispLicenseNo"/>&nbsp;</td>
							<td align="center" class='inner col3'><bean:write  name="row" property="ispLicenseDate"/>&nbsp;</td>
							<%--Satyapan OSP/ISP--%>
                                                        <%--[006] Start--%>
							<td align="center" class='inner col3'><bean:write  name="row" property="publishedDate"/>&nbsp;</td>
							<%--[006] End--%>
							<%--[005] Start--%>
							<td align="center" class='inner col3'><bean:write  name="row" property="standardReason"/>&nbsp;</td>
							<td align="center" class='inner col3'><bean:write  name="row" property="ldClause"/>&nbsp;</td>
							
							<%--[005] End--%>
							<%--[007] Start --%>
							<td align="center" class='inner col2'><bean:write  name="row" property="salesForceOpportunityNumber"/>&nbsp;</td>

							<td align="center" class='inner col1'><bean:write  name="row" property="networkType"/>&nbsp;</td>
							<%-- <td align="center" class='inner col1'><bean:write  name="row" property="partnerId"/>&nbsp;</td> [008]--%>
							<%--[007] End --%>
							<!-- [008] start -->
							<td align="center" class='inner col1'><bean:write  name="row" property="partnerCode"/>&nbsp;</td>
							<td align="center" class='inner col2'><bean:write  name="row" property="channelPartner"/>&nbsp;</td>
							<td align="center" class='inner col3'><bean:write  name="row" property="fieldEngineer"/>&nbsp;</td>
							<!-- [008] end -->
							<!-- [009 start -->
							<td align="center" class='inner col3'><bean:write  name="row" property="ePCNNo"/>&nbsp;</td>
							
							<!-- [009 end -->
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
	                    	<div class='horizontal-scrollactivelinereport' onscroll='reposHorizontal(this);'>
	                        	<div>
	                        	</div>
	                    	</div>
	                	</td>
            		</tr>
				</table>
				<!-- End Scrolling and Freezing of Active Line Report -->		
				<logic:empty  name="customerList">
					<% String fromPageSubmit=request.getParameter("fromPageSubmit");
							if(("1").equalsIgnoreCase(fromPageSubmit)){%>
					   <tr align="center" >
						 <td colspan="46" align="center"><B><font color="red">No Records Found</font></B></td>
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
