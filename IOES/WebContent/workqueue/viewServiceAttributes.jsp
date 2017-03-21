<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<html>
<head>
<title>ServiceAttributes</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
function saveServiceAttributes()
{
	
	var frm=document.getElementById('serviceAttributes');
	if(frm.effStartDate.value=="")
	{
	
		alert("Please Input Effective Start Date!!");
		frm.effStartDate.focus();
		return false;
	}
	/*if(frm.effEndDate.value=="")
	{
		alert("Please Input Effective End Date!!");
		frm.effEndDate.focus();
		return false;
	}*/
	if(frm.logicalSINo.value=="")
	{
		alert("Please Input Customer Logical SI No!!");
		frm.logicalSINo.focus();
		return false;
	}
	if(frm.attRemarks.value=="")
	{
		alert("Please Input Remarks!!");
		frm.attRemarks.focus();
		return false;
	}
	var jsData =
	{
		serviceId:frm.serviceID.value,
		effStartDate:frm.effStartDate.value,
		//effEndDate:frm.effEndDate.value,
		logicalSINo:frm.logicalSINo.value,
		attRemarks:frm.attRemarks.value
	};
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var lstService = jsonrpc.processes.updateServiceAttribute(jsData);
	window.close();
	//UpdateParentInterface(lstService.serviceId,serviceTypeName,selectedText.options[selectedText.selectedIndex].text);
}

function fetchServiceAttribute(serviceID)
{
	var frm=document.getElementById('serviceAttributes');
	var jsData =
	{
		serviceId:frm.serviceID.value
	};
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var lstService = jsonrpc.processes.fetchServiceAttribute(jsData);
	var a=lstService.effStartDate
	if(isNull(lstService.effStartDate)==true)
	{
		frm.effStartDate.value=''
		//frm.effEndDate.value=''
		frm.logicalSINo.value=''
		frm.attRemarks.value=''
		frm.serviceDate.value='DRAFT'
	}
	else
	{
		frm.effStartDate.value=lstService.effStartDate
		//frm.effEndDate.value=lstService.effEndDate
		frm.logicalSINo.value=lstService.logicalSINo
		frm.attRemarks.value=lstService.attRemarks
		frm.serviceDate.value='DRAFT'
	}
	
}
</script>
<body onload="fetchServiceAttribute(<%=request.getParameter("serviceID")%>)">
	<html:form action="/NewOrderAction" styleId="serviceAttributes" method="post">
		<DIV class="head"> Service Attributes</DIV>
		<fieldset class="border1" >
			<legend> <b>Service Details</b> </legend>
			<table  border="0" cellspacing="0" cellpadding="0" align="center" width="100%" >
				<tr>
					<td width="15%">Service Stage</td>
					<td width="14%"><input type="text" name="serviceDate" class="inputBorder1" style="width:120px;" value="DRAFT" readonly="readonly"></td>
					<td width="15%"> Customer Logical SI No</td>
					<td width="15%">
						<input type="text" class="inputBorder1" style="width:120px;" name="logicalSINo" onBlur="" readonly="readonly">	
					</td>
				</tr>
				<tr>
					<td width="15%">Effective Start Date</td>
					<td width="14%">
						<input type="text" class="inputBorder1" style="width:120px;" name="effStartDate" onBlur="if(this.value.length > 0){return checkdate(this)}" readonly="readonly">	</td>
					<!-- <td width="15%"> Effective End Date</td>
					<td width="15%"><input type="text" class="inputBorder1" style="width:120px;" name="effEndDate" onBlur="if(this.value.length > 0){return checkdate(this)}" readonly="readonly">	</td>
					 -->
					<td width="41%" colspan="3"></td>        
				</tr>
				<tr>
					<td width="15%">Remarks</td>
					<td colspan="4">
						<textarea type="text" class="inputBorder1" style="width:97%" rows="5" name="attRemarks" readonly="readonly"></textarea>
					</td>   
					<input type="hidden" name="serviceID" value="<%=request.getParameter("serviceID") %>">
				</tr>
			</table>
		</fieldset>
	</html:form>
</body>
</html>
					
