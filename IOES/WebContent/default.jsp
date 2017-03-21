<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- 
[003]       Gunjan Singla      24/06/14  CSR_20140526_R1_020159     Order Cancellation Post Publish

 -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Welcome Page</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
	<script type="text/javascript">
	window.history.forward(1);
	function noBack() 
	{ 
		window.history.forward(1); 
	}
</script>
<!-- bug id [OPS01042013003] -vijay
add a style for footer -->
<style type="text/css">
	#footer {
		background: #CCCC99;
	    bottom: 0;
	    height: 15px;
	    overflow: hidden;
	    padding: 5px;
	    position: fixed;
	    width: 100%;
	   }
</style>
<!-- end of style for footer -->	
</head>
<script type="text/javascript">
function fnChangeNavigation_temp(val)
{
	if (val=="OE")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenOECount.value;
		for(i=1 ;i<=count;i++)
		{
			document.getElementById('OE'+i).style.display='block';
		}
	}
	else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenOECount.value;
		for(i=1 ;i<=count;i++)
		{	
			document.getElementById('OE'+i).style.display='none';
		}
	}
	if (val=="OA")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenOACount.value;
		for(i=1 ;i<=count;i++)
		{	
			document.getElementById('OA'+i).style.display='block';
		}
	}else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenOACount.value;
		for(i=1 ;i<=count;i++)
		{
			document.getElementById('OA'+i).style.display='none';
		}
	}
	if (val=="SI")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenSICount.value;
		for(i=1 ;i<=count;i++)
		{	
			document.getElementById('SI'+i).style.display='block';
		}
	}else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenSICount.value;
		for(i=1 ;i<=count;i++)
		{
			document.getElementById('SI'+i).style.display='none';
		}
	}
	if (val=="CC")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenCCCount.value;
		for(i=1 ;i<=count;i++)
		{	
			document.getElementById('CC'+i).style.display='block';
		}
	}else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenCCCount.value;
		for(i=1 ;i<=count;i++)
		{
			document.getElementById('CC'+i).style.display='none';
		}
	}
	if (val=="DT")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenDTCount.value;
		for(i=1 ;i<=count;i++)
		{	
			document.getElementById('DT'+i).style.display='block';
		}
	}else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenDTCount.value;
		for(i=1 ;i<=count;i++)
		{
			document.getElementById('DT'+i).style.display='none';
		}
	}
	
	if (val=="MSTR")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenMSTRCount.value;
		for(i=1 ;i<=count;i++)
		{		
			document.getElementById('MSTR'+i).style.display='block';
		}
	}else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenMSTRCount.value;
		for(i=1 ;i<=count;i++)
		{
			document.getElementById('MSTR'+i).style.display='none';
		}	
	}
	if (val=="RPT")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenRPTCount.value;
		for(i=1 ;i<=count;i++)
		{		
			document.getElementById('RPT'+i).style.display='block';
		}
	}else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenRPTCount.value;
		for(i=1 ;i<=count;i++)
		{		
			document.getElementById('RPT'+i).style.display='none';
		}
	}
	if (val=="WRKMSTR")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenWRKMSTRCount.value;
		for(i=1 ;i<=count;i++)
		{
			document.getElementById('WRKMSTR'+i).style.display='block';
		}
	}
	else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenWRKMSTRCount.value;
		for(i=1 ;i<=count;i++)
		{	
			document.getElementById('WRKMSTR'+i).style.display='none';
		}
	}
	if (val=="WRKPRJ")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenWRKPRJCount.value;
		for(i=1 ;i<=count;i++)
		{
			document.getElementById('WRKPRJ'+i).style.display='block';
		}
	}
	else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenWRKPRJCount.value;
		for(i=1 ;i<=count;i++)
		{	
			document.getElementById('WRKPRJ'+i).style.display='none';
		}
	}
	
	if (val=="ACCESS_MATRIX")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenACCESS_MATRIXCount.value;
		for(i=1 ;i<=count;i++)
		{
			document.getElementById('ACCESS_MATRIX'+i).style.display='block';
		}
	}
	else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenACCESS_MATRIXCount.value;
		for(i=1 ;i<=count;i++)
		{	
			document.getElementById('ACCESS_MATRIX'+i).style.display='none';
		}
	}
	
	if (val=="EDIT_CHANNEL_PARTNER")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenEDIT_CHANNEL_PARTNERCount.value;
		for(i=1 ;i<=count;i++)
		{
			document.getElementById('EDIT_CHANNEL_PARTNER'+i).style.display='block';
		}
	}
	else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenEDIT_CHANNEL_PARTNERCount.value;
		for(i=1 ;i<=count;i++)
		{	
			document.getElementById('EDIT_CHANNEL_PARTNER'+i).style.display='none';
		}
	}
	
	//VIPIN
	if (val=="ARCHIVE_ORDERS")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenARCHIVE_ORDERSCount.value;
		for(i=1 ;i<=count;i++)
		{
			document.getElementById('ARCHIVE_ORDERS'+i).style.display='block';
		}
	}
	else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenARCHIVE_ORDERSCount.value;
		for(i=1 ;i<=count;i++)
		{	
			document.getElementById('ARCHIVE_ORDERS'+i).style.display='none';
		}
	}
	//VIPIN
	
	if (val=="BULKCHANGE")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenBULKCHANGECount.value;
		for(i=1 ;i<=count;i++)
		{
			document.getElementById('BULKCHANGE'+i).style.display='block';
		}
	}
	else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenBULKCHANGECount.value;
		for(i=1 ;i<=count;i++)
		{	
			document.getElementById('BULKCHANGE'+i).style.display='none';
		}
	}
	if (val=="GAM")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenGAMCount.value;
		for(i=1 ;i<=count;i++)
		{
			document.getElementById('GAM'+i).style.display='block';
		}
	}
	else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenGAMCount.value;
		for(i=1 ;i<=count;i++)
		{	
			document.getElementById('GAM'+i).style.display='none';
		}
	}
	if (val=="QF")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenQFCount.value;
		for(i=1 ;i<=count;i++)
		{		
			document.getElementById('QF'+i).style.display='block';
		}
	}else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenQFCount.value;
		for(i=1 ;i<=count;i++)
		{		
			document.getElementById('QF'+i).style.display='none';
		}
	}
	if (val=="OE1")
	{
		document.getElementById('newOrdTDId').style.display='block';
		var count =document.forms[0].hiddenOE1Count.value;	
		var countOE1 =document.forms[0].hiddenOE1Count.value;	
		for(i=1 ;i<=count;i++)
		{
			document.getElementById('OENWORD'+i).style.display='block';
		}
		if(countOE1>0){
		document.getElementById('OE1').style.display='block';
		}
		//document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenOECount.value;
		for(i=1 ;i<=count;i++)
		{
			document.getElementById('OE'+i).style.display='block';
		}
	}
	else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenOE1Count.value;
		for(i=1 ;i<=count;i++)
		{	
			document.getElementById('OENWORD'+i).style.display='none';
		}
	}
	if (val=="EM")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenEMCount.value;
		for(i=1 ;i<=count;i++)
		{
			document.getElementById('EM'+i).style.display='block';
		}
	}
	else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenEMCount.value;
		for(i=1 ;i<=count;i++)
		{	
			document.getElementById('EM'+i).style.display='none';
		}
	}
	//[003]
	if (val=="LSICANCEL")
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenLSICANCELCount.value;
		for(i=1;i<=count;i++)
		{
			document.getElementById('LSICANCEL'+i).style.display='block';
		}
	}
	else
	{
		document.getElementById('newOrdTDId').style.display='none';
		var count =document.forms[0].hiddenLSICANCELCount.value;
		for(i=1;i<=count;i++)
		{	
			document.getElementById('LSICANCEL'+i).style.display='none';
		}
	}
}
function fnInterface(val){
	var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>" + val;
	myForm.submit();
}

function loadModules(){

	var countOE =document.forms[0].hiddenOECount.value;
	var countOA =document.forms[0].hiddenOACount.value;
	var countMSTR =document.forms[0].hiddenMSTRCount.value;
	var countRPT =document.forms[0].hiddenRPTCount.value;
	var countWRKMSTR=document.forms[0].hiddenWRKMSTRCount.value;
	var countWRKPRJ=document.forms[0].hiddenWRKPRJCount.value;
	var countACCESS_MATRIX=document.forms[0].hiddenACCESS_MATRIXCount.value;
	var countEDIT_CHANNEL_PARTNER=document.forms[0].hiddenEDIT_CHANNEL_PARTNERCount.value;
	//VIPIN
	var countARCHIVE_ORDERS=document.forms[0].hiddenARCHIVE_ORDERSCount.value;
	//VIPIN
	var countSI =document.forms[0].hiddenSICount.value;
	var countCC =document.forms[0].hiddenCCCount.value;
	var countDT =document.forms[0].hiddenDTCount.value;
	var countBULKCHANGE=document.forms[0].hiddenBULKCHANGECount.value;	
	var countGAM=document.forms[0].hiddenGAMCount.value;
	var countQF=document.forms[0].hiddenQFCount.value;
	var countEM=document.forms[0].hiddenEMCount.value;
	//[003]
	var countLSICANCEL=document.forms[0].hiddenLSICANCELCount.value;
	var totalCount=0;	
	if(countOE>0){
		document.getElementById('OE').style.display='block';
		totalCount=totalCount+1;
	}
	if(countOA>0){
		document.getElementById('OA').style.display='block';
		totalCount=totalCount+1;
	}
	if(countMSTR>0){
		document.getElementById('MSTR').style.display='block';
		totalCount=totalCount+1;
	}
	if(countRPT>0){
		document.getElementById('RPT').style.display='block';
		totalCount=totalCount+1;
	}
	if(countWRKMSTR>0){
		document.getElementById('WRKMSTR').style.display='block';
		totalCount=totalCount+1;
	}
	if(countWRKPRJ>0){
		document.getElementById('WRKPRJ').style.display='block';
		totalCount=totalCount+1;
	}	
	if(countACCESS_MATRIX>0){
		document.getElementById('ACCESS_MATRIX').style.display='block';
		totalCount=totalCount+1;
	}
	if(countEDIT_CHANNEL_PARTNER>0){
		document.getElementById('EDIT_CHANNEL_PARTNER').style.display='block';
		totalCount=totalCount+1;
	}
	//VIPIN
	if(countARCHIVE_ORDERS>0){
		document.getElementById('ARCHIVE_ORDERS').style.display='block';
		totalCount=totalCount+1;
	}
	//VIPIN
	if(countSI>0){
		document.getElementById('SI').style.display='block';
		totalCount=totalCount+1;
	}
	if(countCC>0){
		document.getElementById('CC').style.display='block';
		totalCount=totalCount+1;
	}
	if(countDT>0){
		document.getElementById('DT').style.display='block';
		totalCount=totalCount+1;
	}
	if(countBULKCHANGE>0){
		document.getElementById('BULKCHANGE').style.display='block';
		totalCount=totalCount+1;
	}
	if(countGAM>0){
		document.getElementById('GAM').style.display='block';
		totalCount=totalCount+1;
	}
	if(countQF>0){
		document.getElementById('QF').style.display='block';
		totalCount=totalCount+1;
	}
	if(countEM>0){
		document.getElementById('EM').style.display='block';
		totalCount=totalCount+1;
	}
	if(countLSICANCEL>0){
		document.getElementById('LSICANCEL').style.display='block';
		totalCount=totalCount+1;
	}
	document.getElementById('newOrdTDId').style.display='none';
	if(totalCount==0){
		alert("Not a valid user !!")
		return false;
	}
}	
	


</script>
<body style="background:#ffffff; " onload="noBack();loadModules()" 
onpageshow="if (event.persisted) noBack();" onunload="">
	<table width="100%"  border="0" cellspacing="0" cellpadding="0" >
		<tr height="25">
			<td background="gifs/bg-header-main.jpg" width="50%" align="left"><img src="gifs/logo-inner.jpg">&nbsp;</td>
			<td background="gifs/bg-header-main.jpg" width="50%" align="right">&nbsp;<font face="verdana" size="4" color="white"><b><i>iB2B-Integrated Order Entry Management System</i></b></font></td>
		</tr>
	</table>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0" >
		<tr>
			<td width="95%" class="userName">
			<% if (null==request.getAttribute("resname")) { %>
				UserId: <b><%=request.getAttribute("userId")%></b> 
			<% }else { %>	
				UserId: <b><%=request.getAttribute("userId")%></b> and Role: <b><%=request.getAttribute("resname")%>&nbsp;|&nbsp;</b>	
			<%} %>
			</td>
			<td width="5%" align="right"><div class="link" align="right"><a href="./defaultAction.do?method=LogOutAction"> <b>Log Out</b> </a></div></td>
		</tr>
	</table>
	<div class="nav">Navigator</div>
	
	<html:form action="/defaultAction" styleId="searchForm" method="post">
		<table width="70%"  border="0" cellspacing="0" cellpadding="0"  style="margin-left:20px;padding-bottom:20px;  ">
			<tr>
				<td width="100%">
					<table  border="0" cellspacing="0" cellpadding="0" align="right">
						<tr>
							<td></td>
						</tr>
					</table> 
				</td>
			</tr>
			<tr>
				<td>
					<table width="100%"  border="0" cellspacing="0" cellpadding="0" class="newPage">
							<tr>
							<td width="40%" valign="top">
								<ul>
									<li id="OE" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('OE')">Order Entry</a></li>
									<li id="OA" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('OA')">Order Approval</a></li>
									<li id="MSTR" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('MSTR')">Masters</a></li>
									<li id="WRKMSTR" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('WRKMSTR')">Change Order Workflow Masters</a></li>
									<li id="WRKPRJ" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('WRKPRJ')">Change Order Workflow Projects</a></li>
									<!-- rahul Start -->
									<li id="ACCESS_MATRIX" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('ACCESS_MATRIX')">ACCESS   MATRIX</a></li>
									<!-- rahul End -->
									<!-- rahul Start -->
									<li id="EDIT_CHANNEL_PARTNER" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('EDIT_CHANNEL_PARTNER')">CHANNEL PARTNER</a></li>
									<!-- rahul End -->
									<!-- vipin Start -->
									<li id="ARCHIVE_ORDERS" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('ARCHIVE_ORDERS')">ARCHIVE ORDERS</a></li>
									<!-- vipin End -->
									<li id="RPT" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('RPT')">Reports</a></li>
									<li id="SI" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('SI')">SI Transfer</a></li>
									<li id="CC" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('CC')">Currency Change</a></li>
									<li id="DT" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('DT')">Document Tracking</a></li>
									<li id="BULKCHANGE" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('BULKCHANGE')">BulkUpload for Change Order</a></li>
									<li id="GAM" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('GAM')">GAM</a></li>
									<!-- [003] -->
									<li id="LSICANCEL" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('LSICANCEL')">LSI Cancellation</a></li>
									<li id="QF" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('QF')">Query Form</a></li>
									<li id="EM" style="display:none;"><a href="#" onClick="fnChangeNavigation_temp('EM')">Escalation Matrix</a></li>
								</ul>
							</td>
							<td valign="top" width="30%">
								<% int OECount=0; %>
								<% int OACount=0; %>
								<% int MSTRCount=0; %>
								<% int RPTCount=0; %>
								<% int WRKMSTRCount=0; %>
								<% int WRKPRJCount=0; %>
								<% int ACCESS_MATRIXCount=0; %>
								<% int EDIT_CHANNEL_PARTNERCount=0; %>
								<% int ARCHIVE_ORDERSCount=0; %>
								<% int SICount=0; %>
								<% int CCCount=0; %>								
								<% int DTCount=0; %>	
								<% int BULKCHANGECount=0; %>															
								<% int GAMCount=0; %>
								<% int QFCount=0; %>
								<% int EMCount=0; %>
								<!-- [003] -->	
								<% int LSICANCELCount=0; %>													
								<logic:present name="MODULE_INTERFACE_LIST" scope="request">
								<logic:iterate id="row" name="MODULE_INTERFACE_LIST" indexId="recordId">
							
									<ul>
									
										<logic:equal name="row" property="moduleName"  value="OE">
										<%
										OECount = (OECount + 1);
										
										%>
										<!--<li id="OE1" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/folder.gif) 3px 2px no-repeat;">-->
										<!--<a href="#" onclick="fnChangeNavigation_temp('OE1')" ><bean:write name="row" property="interfaceName"/></a></li>-->										
										<!--  <a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')" ><bean:write name="row" property="interfaceName"/></a></li>-->
										
										<li id="OE<%=OECount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')" ><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
										<logic:equal name="row" property="moduleName"  value="OA">
										<%
										OACount = (OACount+ 1);
										%>
										<li id="OA<%=OACount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')" ><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
										<logic:equal name="row" property="moduleName"  value="MSTR">
										<%
										MSTRCount = (MSTRCount + 1);
										%>
										<li id="MSTR<%=MSTRCount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')"><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
										<logic:equal name="row" property="moduleName"  value="RPT">
										<%
										RPTCount = (RPTCount + 1);
										%>
										<li id="RPT<%=RPTCount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')"><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
										<logic:equal name="row" property="moduleName"  value="WRKMSTR">
										<%
										WRKMSTRCount = (WRKMSTRCount + 1);
										%>
										<li id="WRKMSTR<%=WRKMSTRCount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')"><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
										<logic:equal name="row" property="moduleName"  value="WRKPRJ">
										<%
										WRKPRJCount = (WRKPRJCount + 1);
										%>
										<li id="WRKPRJ<%=WRKPRJCount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')"><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
										
										
										<logic:equal name="row" property="moduleName"  value="ACCESS_MATRIX">
										<%
										ACCESS_MATRIXCount = (ACCESS_MATRIXCount + 1);
										%>
										<li id="ACCESS_MATRIX<%=ACCESS_MATRIXCount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')"><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
										<logic:equal name="row" property="moduleName"  value="EDIT_CHANNEL_PARTNER">
										<%
										EDIT_CHANNEL_PARTNERCount = (EDIT_CHANNEL_PARTNERCount + 1);
										%>
										<li id="EDIT_CHANNEL_PARTNER<%=EDIT_CHANNEL_PARTNERCount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')"><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
										<!-- VIPIN -->
										<logic:equal name="row" property="moduleName"  value="ARCHIVE_ORDERS">
										<%
										ARCHIVE_ORDERSCount = (ARCHIVE_ORDERSCount + 1);
										%>
										<li id="ARCHIVE_ORDERS<%=ARCHIVE_ORDERSCount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')"><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
										<!-- VIPIN -->
										
										<logic:equal name="row" property="moduleName"  value="SI">
										<%
										SICount = (SICount+ 1);
										%>
										<li id="SI<%=SICount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')" ><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
										<logic:equal name="row" property="moduleName"  value="CC">
										<%
										CCCount = (CCCount+ 1);
										%>
										<li id="CC<%=CCCount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')" ><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
										
										<logic:equal name="row" property="moduleName"  value="DT">
										<%
										DTCount = (DTCount+ 1);
										%>
										<li id="DT<%=DTCount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')" ><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
										
										<logic:equal name="row" property="moduleName"  value="BULKCHANGE">
										<%
										BULKCHANGECount = (BULKCHANGECount + 1);
										%>
										<li id="BULKCHANGE<%=BULKCHANGECount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')"><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
										<logic:equal name="row" property="moduleName"  value="GAM">
										<%
										GAMCount = (GAMCount + 1);
										%>
										<li id="GAM<%=GAMCount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')"><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
										<logic:equal name="row" property="moduleName"  value="QF">
										<%
										QFCount = (QFCount + 1);
										%>
										<li id="QF<%=QFCount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')"><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
										<logic:equal name="row" property="moduleName"  value="Escalation">
										<%
										EMCount = (EMCount + 1);
										%>
										<li id="EM<%=EMCount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')"><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
										<!-- [003] -->
										<logic:equal name="row" property="moduleName"  value="LSICANCEL">
										<%
										LSICANCELCount = (LSICANCELCount + 1);
										%>
										<li id="LSICANCEL<%=LSICANCELCount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">
										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')"><bean:write name="row" property="interfaceName"/></a></li>
										
										</logic:equal>
									</ul>
								</logic:iterate>
								</logic:present>
						</td>
						<td valign="top" id="newOrdTDId" style="display:none" width="60%">
						<%int NWORDCount=0; %>
						<logic:present name="NEWORDER_INTERFACE_LIST" scope="request">
								<logic:iterate id="row" name="NEWORDER_INTERFACE_LIST" indexId="recordId">
								<ul>									
								<logic:equal name="row" property="moduleName"  value="OE1">								
										<%
											NWORDCount = (NWORDCount+ 1);
										%>
										<li id="OENWORD<%=NWORDCount %>" style="display:none; padding:2px 2px 2px 22px; background:url(gifs/i.gif) 3px 2px no-repeat;">										
										<a href="#" onclick="return fnInterface('<bean:write name="row" property="url"/>')" ><bean:write name="row" property="interfaceName"/></a></li>								
								</logic:equal>
								</ul>
						</logic:iterate>
					 </logic:present>
					</td>
						</tr>
					</table>
				</td>				
			</tr>
		</table>
		<!-- [OPS01042013003] add footer -->
		<div align="center" id="footer" style="vertical-align: bottom" class="footer">
			Supported Browsers: Microsoft Internet Explorer 7.0 and above (with Compatiblity View)
		</div>			
		<!-- [OPS01042013003] end of footer -->
		<input type="hidden" name="hiddenOECount" value="<%=OECount%>">
		<input type="hidden" name="hiddenOACount" value="<%=OACount%>">
		<input type="hidden" name="hiddenMSTRCount" value="<%=MSTRCount%>">
		<input type="hidden" name="hiddenRPTCount" value="<%=RPTCount%>">
		<input type="hidden" name="hiddenWRKMSTRCount" value="<%=WRKMSTRCount%>">
		<input type="hidden" name="hiddenWRKPRJCount" value="<%=WRKPRJCount%>">
		<input type="hidden" name="hiddenACCESS_MATRIXCount" value="<%=ACCESS_MATRIXCount%>">
		<input type="hidden" name="hiddenEDIT_CHANNEL_PARTNERCount" value="<%=EDIT_CHANNEL_PARTNERCount%>">
		<input type="hidden" name="hiddenARCHIVE_ORDERSCount" value="<%=ARCHIVE_ORDERSCount%>">
		<input type="hidden" name="hiddenOE1Count" value="<%=NWORDCount%>">
		<input type="hidden" name="hiddenSICount" value="<%=SICount%>">
		<input type="hidden" name="hiddenCCCount" value="<%=CCCount%>">
		<input type="hidden" name="hiddenDTCount" value="<%=DTCount%>">
		<input type="hidden" name="hiddenBULKCHANGECount" value="<%=BULKCHANGECount%>">
		<input type="hidden" name="hiddenGAMCount" value="<%=GAMCount%>">
		<input type="hidden" name="hiddenQFCount" value="<%=QFCount%>">
		<input type="hidden" name="hiddenEMCount" value="<%=EMCount%>">
		<!-- [003] -->
		<input type="hidden" name="hiddenLSICANCELCount" value="<%=LSICANCELCount%>">
	</html:form>
	<div class="nav"></div>
</body>
</html>
