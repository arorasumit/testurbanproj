 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--Tag Name Resource Name  	Date		CSR No			Description -->
<!--		 Rohit Verma		28-FEB-13  00-08440		New Interface to Request Hardware Line Item for Deletion in M6  -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Delete Hardware Line Item</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
window.name="CANCELLED_HW_LINEDETAILS";
var callerWindowObj = dialogArguments;
var orderNo = callerWindowObj.document.getElementById('poNumber').value;
function DrawTable()
{
	var str;
	var frm=document.getElementById('hardwarelineItem');
	var mytable = document.getElementById('tabHardwareLines');	
	var iCountRows = mytable.rows.length;

	for (i = 1; i <  iCountRows; i++)
	{
	    mytable.deleteRow(1);
	} 
	try
	{
		var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		var cancelledHardwareLineDetails = jsonrpc.processes.getCancelledHardwareLineDetails(orderNo);
	}
	catch(e)
	{
		alert("Exception :"+e);
	}
	var colors=new Array("normal","lightBg");
	for (i = 0; i <  cancelledHardwareLineDetails.list.length; i++)
	{	
		var colorValue=parseInt(i)%2;
		var tblRow=document.getElementById('tabHardwareLines').insertRow();
		tblRow.className=colors[colorValue];
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].serviceProductID);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].serviceDetDescription);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].serviceNo);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].requestedBy);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].userId);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].createdBy);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].logicalCircuitId);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].infraOderNo);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].metasolvCircuitId);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].podetailID);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].creditPeriodName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].legalEntity);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].billingMode);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].billingformat);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].licCompanyName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].taxationName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].billingLevel);
		CellContents = str;
		tblcol.innerHTML = CellContents;
				
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].commitmentPeriod);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].penaltyClause);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].billingTypeName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].bcpName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].billingLevelNo);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].noticePeriod);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		if(cancelledHardwareLineDetails.list[i].isNfa==0)
			str="No";
		else
			str="Yes"
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].taxation);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		if(cancelledHardwareLineDetails.list[i].billingScenario==0)
			str="DEFAULT";
		else if(cancelledHardwareLineDetails.list[i].billingScenario==1)
			str="CONSOLIDATE";
		else //if(cancelledHardwareLineDetails.list[i].billingScenario==2)
			str="CUMULATIVE";	
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].storeName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].hardwareType);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].formAvailable);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].saleType);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].saleNature);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].dispatchName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].txtStartDateLogic);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].txtEndDateLogic);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].warrentyMonths);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].principalAmount);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].interestRate);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].txtStartMonth);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].txtStartDays);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].txtEndMonth);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].txtEndDays);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].txtExtMonth);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].txtExtDays);
		CellContents = str;
		tblcol.innerHTML = CellContents;
				
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].dispatchContactName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].lineManagedBy);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].line2DProduct);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].lineIndicativeValue);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].linePaymentTerms);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].lineRemarks);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].chargeTypeName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].chargeName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].chargePeriod);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].chargeAmount);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].frequencyName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].chargeFrequencyAmount);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].startDateLogic);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].endDateLogic);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].chargeStartMonths);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].chargeStartDays);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].chargeEndMonths);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].chargeEndDays);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].annotation);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].annualRate);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].chargeRemarks);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(cancelledHardwareLineDetails.list[i].chargeTaxRate);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
	}	
	if(cancelledHardwareLineDetails.list.length == 0)
	{
		var tblRow=document.getElementById('tabHardwareLines').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 15;
		str='No Records Found';
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}	
}

function exportToExcel()
{
	var myForm=document.getElementById('lineDetailsForm');
	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=getCancelledHardwareLineDetailsInExcel&orderNo="+orderNo;
	myForm.submit();
}
//target="CANCELLED_HW_LINEDETAILS"--NewOrderAction
path='<%=request.getContextPath()%>';
</script>
</head>
<body >
<html:form action="/NewOrderAction" styleId="lineDetailsForm" method="post" >
	<!--<div class="head"><span>Cancelled Hardware Line Item Details</span> </DIV>-->
	<fieldset class="border1">
		<!--<legend> <b>Cancelled Hardware Line Item Details</b> </legend>-->
			<table width="100%">
				<tr>
					<td width="20%"  align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel"  onClick="exportToExcel()">Export To Excel</a></div></td>
					<td width="80%"></td>
				</tr>
			</table>
			<table width="100%" border="1"  id="tabHardwareLines" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
				<tr class="grayBg">
					<td width="6%" align="center"><b>Line Item No</b></td>
					<td width="6%" align="center"><b>Line Item Name</b></td>
					<td width="7%" align="center"><b>Service No</b></td>
					<td width="7%" align="center"><b>Requested By</b></td>
					<td width="7%" align="center"><b>User Id</b></td>
					<td width="5%" align="center"><b>Line Created By</b></td>
					<td width="7%" align="center"><b>E1.logical CircuitId</b></td>
					<td width="7%" align="center"><b>E2.InfraOrderNo</b></td>
					<td width="8%" align="center"><b>E3.MetaSolv CircuitId</b></td>
					<td width="8%" align="center"><b>PO Number</b></td>
					<td width="8%" align="center"><b>Credit Period</b></td>
					<td width="8%" align="center"><b>Legal Entity</b></td>
					<td width="8%" align="center"><b>Billng Mode</b></td>
					<td width="8%" align="center"><b>Billing Format</b></td>
					<td width="8%" align="center"><b>Licence Company</b></td>
					<td width="8%" align="center"><b>Taxation</b></td>
					<td width="8%" align="center"><b>Billng Level</b></td>
					<td width="8%" align="center"><b>Commitment Period</b></td>
					<td width="8%" align="center"><b>Penalty Clause</b></td>
					<td width="8%" align="center"><b>Billing Type</b></td>
					<td width="8%" align="center"><b>BCP Name</b></td>
					<td width="8%" align="center"><b>Billing LevelNo</b></td>
					<td width="8%" align="center"><b>Notice Period</b></td>
					<td width="8%" align="center"><b>Is NFA</b></td>
					<td width="8%" align="center"><b>Reason</b></td>
					<td width="8%" align="center"><b>Billing Scenario</b></td>
					<td width="8%" align="center"><b>Store</b></td>
					<td width="8%" align="center"><b>hardwareType</b></td>
					<td width="8%" align="center"><b>Form Available</b></td>
					<td width="8%" align="center"><b>Sale Type</b></td>
					<td width="8%" align="center"><b>Sale Nature</b></td>
					<td width="8%" align="center"><b>Dispatch Address</b></td>
					<td width="8%" align="center"><b>H/W Start Date logic</b></td>
					<td width="8%" align="center"><b>H/W End Date Logic</b></td>
					<td width="8%" align="center"><b>Warrenty Months</b></td>
					<td width="8%" align="center"><b>Principal Amount</b></td>
					<td width="8%" align="center"><b>Interest Rate</b></td>
					<td width="8%" align="center"><b>Start Months</b></td>
					<td width="8%" align="center"><b>Start Days</b></td>
					<td width="8%" align="center"><b>End Months</b></td>
					<td width="8%" align="center"><b>End Days</b></td>
					<td width="8%" align="center"><b>Extended Months</b></td>
					<td width="8%" align="center"><b>Extended Days</b></td>
					<td width="8%" align="center"><b>Dispatch ContactName</b></td>
					<td width="8%" align="center"><b>Managed By</b></td>
					<td width="8%" align="center"><b>Product</b></td>
					<td width="8%" align="center"><b>Indicative Value</b></td>
					<td width="8%" align="center"><b>Payment Terms</b></td>
					<td width="8%" align="center"><b>Remarks</b></td>
					<td width="8%" align="center"><b>Charge Type</b></td>
					<td width="8%" align="center"><b>Charge Name</b></td>
					<td width="8%" align="center"><b>Charge Period</b></td>
					<td width="8%" align="center"><b>Charge Amount</b></td>
					<td width="8%" align="center"><b>Frequeny</b></td>
					<td width="8%" align="center"><b>Frequency Amount</b></td>
					<td width="8%" align="center"><b>Charge Start Date Logic</b></td>
					<td width="8%" align="center"><b>Charge End Date Logic</b></td>
					<td width="8%" align="center"><b>Charge Start Months</b></td>
					<td width="8%" align="center"><b>Charge Start Days</b></td>
					<td width="8%" align="center"><b>Charge End Months</b></td>
					<td width="8%" align="center"><b>Charge End Days</b></td>
					<td width="8%" align="center"><b>Annotation</b></td>
					<td width="8%" align="center"><b>Annual Rate</b></td>
					<td width="8%" align="center"><b>Charge Remarks</b></td>
					<td width="8%" align="center"><b>TaxRate</b></td>
				</tr>				
			</table>
	</fieldset>
</html:form>	
</body>
<script type="text/javascript">
	DrawTable()
</script>
</html>