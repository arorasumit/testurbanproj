<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]    ROHIT VERMA	05-Nov-12	CSR00-07843		Moving Account Short Code in Customer Section-->
<!--[002]    MANISHA GARG	07-Feb-13	CSR00-07843		show high priority attribute -->
<!--[002]    Vijay          12-Feb-13                   selected page's tab contain  a diffrent color than non-selected pages Tabs. In this way selected Tab contain uniqeness.-->
<!--[15032013017]    RAMPRATAP	13-MAR-13	ADDED NEW BUTTON TO COUNT SELECTED LINE ITEMS-->

<!--[003]	 Neelesh		24-May-13	CSR00-08463     For Addition of Category  -->
<!--[004]	 Neelesh		06-Jun-13	TRNG22032013037     For Mulitple Opportunity  -->
<%-- [006]  VIPIN SAHARIA 11-May-2015 20150403-R2-021204 Project Satyapan Adding ISP tagging fields to header section --%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.utilities.AppUtility"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>View Order</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<link href="gifs/treeview.css" type="text/css" rel="stylesheet">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/NewChangeOrderUtility.js"></script>

<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script type="text/javascript"  src="<%=request.getContextPath()%>/js/jsonrpc.js"></script>
<script type="text/javascript" src="js/Tokenizer.js"></script>


<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript">
var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
<%
	HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
	HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
	UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>
<%
	HashMap hashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
	HttpSession httpSessionObj = (HttpSession) hashMap.get(session.getId());
	UserInfoDto userDto  = (UserInfoDto)httpSessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>
var gb_roleID = "<%=userDto.getUserRoleId() %>";
var gb_userID =  "<%=userDto.getUserId() %>";
var gb_empID = "<%= Long.parseLong(userDto.getEmployeeId()) %>";
<%
HashMap userHashMapSeesion = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
HttpSession sessionObject = (HttpSession) userHashMapSeesion.get(session.getId());
UserInfoDto objectUserDto  = (UserInfoDto)sessionObject.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>
var PAGE_SIZE_SERVICE_LINE=<%=objectUserDto.getPageSizeServiceLine()%>;
var gb_currentDate = '<%=AppUtility.getTodayDate()%>';
var gb_sessionid ='<%=request.getSession().getId() %>';
var gb_path='<%=request.getContextPath()%>';
var roleId = <%=userDto.getUserRoleId() %>;
var gb_roleID = "<%=userDto.getUserRoleId() %>";
var gb_AttachIconAttribute = "<%= request.getAttribute("showAttachIcon")%>";
var gb_CheckedServiceNoParam = "<%= request.getParameter("checkedServiceNumber")%>";
var gb_FlagParam = "<%= request.getParameter("flag")%>";
var gb_modeName="<%= request.getParameter("modeName") %>";
var gb_ExceptionMessage = "<%=Messages.getMessageValue("javascriptExceptionMessage")%>";
var gb_ExceptionShow = "<%=Messages.getMessageValue("javascriptExceptionShow")%> ";
<% 
response.setHeader("Cache-Control","no-cache"); 
response.setHeader("Cache-Control","no-store"); 
response.setDateHeader("Expires", 0);
response.setHeader("Pragma","no-cache");
%>
</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/ViewOrder.js"></script> 
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
<script type="text/javascript">
function onloadMessage()
{
	try{
	<logic:present name="m6StatusCode">
		<logic:equal name="m6StatusCode" value="1">
			alert("Order Published Successfully");			
			document.getElementById('saveIcon').style.display="none";	
			document.getElementById('btnAddMoreService').style.display="none";	
			//lawkush Start
			document.getElementById('refreshAccountNo').style.display="none";	
			//lawkush End	
		</logic:equal>
		<logic:equal name="m6StatusCode" value="-1">
			alert("Due to some problem, Order has not be published!!");			
			document.getElementById('saveIcon').style.display="block";
			document.getElementById('btnAddMoreService').style.display="block";	
			//lawkush Start
			document.getElementById('refreshAccountNo').style.display="block";	
			//lawkush End	
		</logic:equal>
	</logic:present>
	//checkPublishStatus();
	}catch(e)
	{
		alert('error code 342: '+ e.message);
	}
}
//--[002]--start--//
function showTab(){
	try{
	 	if(document.getElementById('Page_1').style.display=="block"){
 			selectCurrentTab('li1');
		 }
	}catch(e)
	{
		alert('error code 343: '+ e.message);
	 }
}
//--[002]--start--//

</script>
<!--  PAGING-SERVICE-LINE-14-10-2012-->

<!-- <script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script> -->

<body onload="showNoOfDays();onloadMessage();showTab();" >
<!-- <div id="shadow" class="opaqueLayer"><font color="yellow" size="8"> Please wait...File is uploading...</font>
</div> -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
	<html:form action="/NewOrderAction" styleId="searchForm" method="post" enctype="multipart/form-data">
		<jsp:include page="../../TopMenu.jsp" flush="true"/>
		<bean:define id="formBean" name="newOrderBean"></bean:define>
		<html:hidden property="hdnServiceCounter"  />
		<html:hidden property="SPIDUrlforVPC"  />		
		<input type="hidden" id="hdnserviceid" name="hdnserviceid" /> 		<input type="hidden" id="hdnservicetypeid" name="hdnservicetypeid"/>
		<input type="hidden" name="hdnCurrentTempCounter" id="hdnCurrentTempCounter"/>
		<input type="hidden" name="hdnSelectedServiceDetailId">
		<input type="hidden" name="selectedPODetails">
		<input type="hidden" name="hdnprojectmgrid">
		<input type="hidden" id="hdnServiceProductID" />
		<input type="hidden" id="hdnServiceProductName" />
		<html:hidden property="logicalSINo"  />
		<input type="hidden" name="isBillingTriggerReady" value="<bean:write name='formBean' property='isBillingTriggerReady'/>">
		<input type="hidden" id="latestTab" value="<bean:write name='formBean' property='latestTab'/>">	
		<input type="hidden" id="isValidatePO" value="0">
		<!-- add [005] --><input type="hidden" name="hdnShowAttachIcon" id="hdnShowAttachIcon"/><!-- end [005] -->
		<div class="head"> <span>View Order </span> </div>
		<div class="error" align="center">
			<logic:present name="validation_errors">
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>
			</logic:present>
		</div>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="40%" valign="top">
					<fieldset class="border1">
						<legend> <b>Customer</b> </legend>
						<logic:present name="accountDetailsBean" scope="request">
							<logic:notEmpty name="accountDetailsBean" scope="request">
								<logic:iterate id="row1"  name="accountDetailsBean" indexId="index_report">
									<table border="0" cellspacing="0" cellpadding="1" style="margin-top:7px ">
										<tr>
											<td align="right" style="font-size:9px" > A\C No </td>
											<td>
												<html:hidden name="row1" property="accountID" styleId="accNo"></html:hidden>
												<html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="crmAccountNo" styleId="crmAccNo" styleClass="inputBorder1" style="width:110px;float:left;" readonly="true"></html:text>
												<img border="0" src="<%=request.getContextPath()%>/images/loading.gif" alt="Loading..." width="20" height="21"  id="imgLoadingId" style="display:none" />
											</td>
											<td align="right" style="font-size:9px;" nowrap>Party Name</td>
											<td colspan="4"><html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' name="row1"  property="accountName" styleClass="inputDisabled" style="width:300px;" readonly="true"></html:text></td>
										</tr>
										<tr>
											<td align="right" style="font-size:9px" nowrap> A\C Mgr </td>
											<td><html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="accountManager" styleClass="inputDisabled" style="width:110px;" readonly="true"></html:text></td>
											<!-- [001] Start-->
											<td align="right" style="font-size:9px" nowrap> Short Code</td>
											<td><html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="m6ShortCode" styleClass="inputDisabled" style="width:110px;" readonly="true"></html:text></td>		
											<!-- [001] End-->
											<td align="right" style="font-size:9px" nowrap> Collection Mgr </td>
											<td><html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="collectionMgr" styleClass="inputDisabled" style="width:101px;" readonly="true"></html:text></td>		
										</tr>
										<tr>
											<!-- Start[007] -->
										<html:hidden property="projectManagerID" styleId="projectManagerID"></html:hidden>
											<td align="right" style="font-size:9px" nowrap> Project Mgr </td>
											<td><html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="projectManager" styleClass="inputDisabled" style="width:110px;" readonly="true"></html:text></td>
											<!-- End[007] -->
											
											</td>
											<td align="right" style="font-size:9px"> Segment </td>
											<td ><html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="lob" styleClass="inputDisabled" style="width:110px;" readonly="true"></html:text></td>
											<%--CBR SATYAPAN OSP --%>
											<td align="right" style="font-size:9px"> A\C OSP </td>
											<td ><html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="osp" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>
										</tr>
										<tr>
											<td align="right" style="font-size:9px"> Circle </td>
											<td ><html:text onmouseover='getTip(value)' onmouseout='UnTip()'  name="row1" property="circle" styleClass="inputDisabled" style="width:110px;" readonly="true"></html:text></td>
											<!-- Start [003] -->
											<td align="right" style="font-size:9px"> Category </td>
											<td ><html:text onmouseover='getTip(value)' onmouseout='UnTip()' name="row1"  property="category" styleClass="inputDisabled" style="width:110px;" readonly="true"></html:text></td>									
											<!-- End [003] -->
											<!-- Start [089] -->
											<td align="right" style="font-size:9px"> Group Name </td>
											<td ><html:text onmouseover='getTip(value)' onmouseout='UnTip()' name="row1"  property="groupName" styleClass="inputDisabled" style="width:110px;" readonly="true"></html:text></td>										
											<!-- End [089] -->
										</tr>
										<tr>
										    <td align = "right" style="font-size:9px"> Service Segment </td>
										    <td ><html:text onmouseover='getTip(value)' onmouseout='UnTip()' name="row1"  property="serviceSegment" styleClass="inputDisabled" style="width:110px;" readonly="true"></html:text></td>
										</tr>
									</table>
								</logic:iterate>
							</logic:notEmpty>
						</logic:present>
						<logic:notPresent name="accountDetailsBean" scope="request">
							<table border="0" cellspacing="0" cellpadding="1" style="margin-top:7px ">
								<tr>
									<td align="right" style="font-size:9px"> A\C No </td>
									<td>
										<html:text onmouseover='getTip(value)' onmouseout='UnTip()'  property="crmAccountNo" styleId="accNo" styleClass="inputBorder1" style="width:110px;float:left;"  readonly="true"></html:text>
										<div class="searchBg1"><a href="#"  title="Select Account"  onClick="popitup('SelectAccount')">...</a></div> 
									</td>
									<td align="right" style="font-size:9px;" nowrap="nowrap">Party Name</td>
									<td colspan="4"><html:text onmouseover='getTip(value)' onmouseout='UnTip()'  property="accountName" styleClass="inputDisabled" style="width:290px;" readonly="true"></html:text></td>
								</tr>
								<tr>
									<td align="right" style="font-size:9px" nowrap="nowrap"> A\C Mgr </td>
									<td><html:text onmouseover='getTip(value)' onmouseout='UnTip()' property="accountManager" styleClass="inputDisabled" style="width:110px;float:left;" readonly="true"></html:text></td>
									<!-- [001] Start -->
									<td align="right" style="font-size:9px" nowrap>Short Code</td>
									<td><html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' property="m6ShortCode" styleClass="inputDisabled" style="width:110px;" readonly="true"></html:text></td>		
									<!-- [001] End -->
									<td align="right" style="font-size:9px" nowrap> Collection Mgr </td>
									<td><html:text onmouseover='getTip(value)' onmouseout='UnTip()' property="collectionMgr" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>		
								</tr>
								<tr>
									<!-- Start[007] -->
										<html:hidden property="projectManagerID" styleId="projectManagerID"></html:hidden>
											<td align="right" style="font-size:9px" nowrap> Project Mgr </td>
											<td><html:text onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="projectManager" styleClass="inputDisabled" style="width:110px;" readonly="true"></html:text></td>
											<!-- End[007] -->
											
									
									</td>
									<td align="right" style="font-size:9px"> Segment </td>
									<td ><html:text onmouseover='getTip(value)' onmouseout='UnTip()'  property="lob" styleClass="inputDisabled" style="width:110px;" readonly="true"></html:text></td>
									<%--CBR SATYAPAN OSP --%> 
									<td align="right" style="font-size:9px"> A\C OSP </td>
									<td ><html:text onmouseover='getTip(value)' onmouseout='UnTip()'  property="osp" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>
								</tr>
								<tr>
									<td align="right" style="font-size:9px"> Circle </td>
									<td ><html:text  property="circle" styleClass="inputDisabled" style="width:110px;" readonly="true"></html:text></td>
									<!-- Start [003] -->
									<td align="right" style="font-size:9px"> Category </td>
									<td ><html:text  property="category" styleClass="inputDisabled" style="width:110px;" readonly="true"></html:text></td>
									<!-- End [003] -->
									<!-- Start [005] -->
									<td align="right" style="font-size:9px"> Group Name </td>
									<td ><html:text  property="groupName" styleClass="inputDisabled" style="width:110px;" readonly="true"></html:text></td>
									<!-- End [005] -->
									
									
								</tr>
								<tr>
								    <td align="right" style="font-size:9px"> Service Segment </td>
								    <td ><html:text onmouseover='getTip(value)' onmouseout='UnTip()'  property="serviceSegment" styleClass="inputDisabled" style="width:110px;" readonly="true"></html:text></td>
								</tr>
							</table>
						</logic:notPresent>
					</fieldset>
				</td>
				<td width="60%" valign="top">
					<fieldset class="border1">
						<legend> <b>Order </b> </legend>
						<logic:present name="accountDetailsBean" scope="request">
							<logic:notEmpty name="accountDetailsBean" scope="request">
								<logic:iterate id="row1"  name="accountDetailsBean" indexId="index_report" type="com.ibm.ioes.forms.OrderHeaderDTO">
									<table border="0" cellspacing="0" cellpadding="1" style="margin-top:7px ">
										<tr>
											<td align="right" style="font-size:9px"> Order Type </td>
											<td><html:text onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="orderType" readonly="true" styleClass="inputDisabled" style="width:75px;" readonly="true"></html:text> </td>
											<td align="right" style="font-size:9px">Order Date</td>
											<td><html:text onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="orderDate" readonly="true" styleClass="inputDisabled" style="width:80px;" readonly="true"></html:text></td>
											<td align="right" style="font-size:9px">Order Origin</td>
											<td nowrap="nowrap">
												<html:text onmouseover='getTip(value)' onmouseout='UnTip()'  property="orderCreationSourceName" name="row1" styleClass="inputDisabled" style="width:150px;" styleId="txtOrderCreationSourceName" readonly="true"></html:text>
											</td>
											<td align="right" style="font-size:9px">Source</td>
											<td>
												<html:text onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="sourceName" styleClass="inputBorder1" style="width:100px; float:left;" readonly="true"></html:text>
											</td>
											<td align="right" style="font-size:9px"></td>
											<td nowrap="nowrap">
												
											</td>
											</tr>
											<tr>
									<!-- [004]	START	-->
											<!--
											<td align="right" style="font-size:9px"> Quote No </td>
											<td nowrap="nowrap">
											<div id="txtQuoteNo" style="display:block; ">
												<html:text onmouseover='getTip(value)' onmouseout='UnTip()'  property="quoteNo" name="row1" styleClass="inputDisabled" style="width:75px;" styleId="txtquotesNo" readonly="true"></html:text>
										</div>	
										<div id="cboQuoteNo" style="display:none; ">										
											<html:select property="quoteNo" styleId="quoteNo" styleClass="inputBorder1" style="width:110px;float:left;">
												<html:option value="0">--Select--</html:option>
											</html:select>											
										</div>
											</td>
											-->
									<!-- [004]	End	-->		
											<td align="right" style="font-size:9px"> Currency </td>
											<td  colspan="2">
												<html:text onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="curShortCode" styleClass="inputBorder1" style="width:80px; float:left;" readonly="true"></html:text>
												<html:hidden property="currencyID"></html:hidden>
												<div class="searchBg1"><a href="#" onClick="return popitup('SelectCurrency')">...</a></div>
											</td>
											<!-- [001]	Start 
											<td align="right" style="font-size:9px"> Opportunity ID </td>
											<td>
												<html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()'  property="opportunityId" name="row1" styleClass="inputDisabled" style="width:75px;" readonly="true"></html:text>
											</td>
											 [001]	End -->
											
									<!-- [004]	Start -->											
											<td colspan="2">											
											<input name="btnOpportunity" id="btnOpportunity" onClick="return popitup('SelectOpportunity');" type="button" value="View Opportunity/ePcn No.">
											<html:hidden property="opportunityId" styleId="opportunityId"/>
											</td>
											<html:hidden property="quoteNo" styleId="quoteNo"/>
											<html:hidden property="txtquotesNo" styleId="txtquotesNo"/>
									<!-- [004]	End -->		
								
											<!-- [002]	085 defect start -->
											
											<td align="right" style="font-size:9px" colspan="1" nowrap> High Priority</td>
											<td>
											<%
													if(row1.getIsUrgent()==1)
													{	
												 %>
													<input onmouseover='getTip(value)' onmouseout='UnTip()' type="checkbox" id="isUrgent" checked="checked" disabled="disabled" name="chkIsUrgent" CHECKED > 
												<%
													}
												 %>
												 <%
													if(row1.getIsUrgent()==0)
													{	
												 %>
													<input onmouseover='getTip(value)' onmouseout='UnTip()' type="checkbox" id="isUrgent" disabled="disabled" name="chkIsUrgent"  UNCHECKED DISABLED> 
												<%
													}
												 %>	
											</td>
											<!-- [002]	085 defect end -->
											
										</tr> 
										<tr>
											<td align="right" style="font-size:9px">Order No</td>
											<td>
												<html:text onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="poNumber" readonly="true" styleClass="inputDisabled" style="width:75px;" readonly="true" maxlength="10"  ></html:text> 
												<html:hidden property="hdnOrderNo"/>
												<html:hidden property="hdnOrderCreationSource" styleId="hdnOrderCreationSourceId"/>
											</td>
											<td align="right" style="font-size:9px"> Status </td>
											<td>
												<html:text onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="orderStatusValue" readonly="true" styleClass="inputDisabled" style="width:80px;" readonly="true"></html:text> 
											</td>
											<td align="right" style="font-size:9px"> Stage </td>
											<!-- [002] Start -->
											<td>
												<html:hidden property="stageName" name="row1"/>
												<html:text onmouseover='getTip(value)' onmouseout='UnTip()' property="orderStageAnnotationName" name="row1" readonly="true" styleClass="inputDisabled" style="width:100px;"></html:text>
											<!-- [002] End -->
											 </td>
											 	<td align="right" style="font-size:9px"> Is Demo </td>
												<td>
												<%
													if(("D").equalsIgnoreCase(row1.getChkIsDemo()))
													{	
												 %>
													<input type="checkbox" id="chkIsDemo" checked="checked" name="chkIsDemo" CHECKED DISABLED> 
												<%
													}
												 %>
												 <%
													if(!("D").equalsIgnoreCase(row1.getChkIsDemo()))
													{	
												 %>
													<input type="checkbox" id="chkIsDemo"  name="chkIsDemo" onclick="showNoOfDays()" UNCHECKED DISABLED> 
												<%
													}
												 %>	
												</td>
												<td>
													<div id="dvNoOfDays" style="visibility: hidden;"> 
														<!-- [004]	Start --> 
														No of Days<html:text onmouseover='getTip(value)' onmouseout='UnTip()' property="noOfDaysForDemo"  name="row1" styleClass="inputBorder1" style="width:80px;" readonly="true"></html:text>  
														<!-- [004]	End --> 
													</div>
												
												</td> 
										</tr>
										<%-- [006] Start PROJECT SATYAPAN --%>
										<tr>
										<%if(row1.getIspTagging()!=1){ %>
											<c:set var="isMand" value="2"></c:set>
											<c:set var="valIspTagging" value="NO"></c:set>
										<%}else{ %>
											<c:set var="isMand" value="1"></c:set>
											<c:set var="valIspTagging" value="YES"></c:set>
										<%} %>			
											<td align="right" style="font-size:9px" >ISP Tagging</td>
											<td nowrap>										
												<input type="text" name="ispTagging" styleId="ispTagging" class="inputBorder1" style="width:100px; float:left;"  readonly="readonly" value="${valIspTagging }">
						
											</td>
											<td align="right" style="font-size:9px" >ISP License Category</td>
											<td nowrap>
											<c:choose >
												<c:when test="${row1.getIspLicCtgry()==1 }"><c:set var="valIspLicCtgry" value="A"></c:set> </c:when>
												<c:when test="${row1.getIspLicCtgry()==2 }"><c:set var="valIspLicCtgry" value="B"></c:set> </c:when>
												<c:when test="${row1.getIspLicCtgry()==3 }"><c:set var="valIspLicCtgry" value="C"></c:set> </c:when>
												<c:otherwise><c:set var="valIspLicCtgry" value="NA"></c:set></c:otherwise>
											</c:choose>										
												<input type="text" name="ispLicCtgry" styleId="ispLicCtgry" class="inputBorder${isMand }" style="width:100px; float:left;" readonly="readonly" value="${valIspLicCtgry }">
												
											</td>
											<td align="right" style="font-size:9px" >ISP License Date</td>
											<td nowrap>
												<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" width="100%" class="inputBorder${isMand }" isRequired="0" id="ispLicDate" name="ispLicDate" value="<bean:write name="row1" property="ispLicDate"/>" readonly="readonly" >
												<font size="1">
													<a href="#" class="borderTabCal"><img id="dateIspLic" src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Select date" style="vertical-align: bottom;" ></a>											
												</font>
											</td>
											<td align="right" style="font-size:9px" >ISP License Number</td>
											<td nowrap>
												<input type="text" onmouseover='getTip(value)' onmouseout='UnTip()' name="ispLicNo" class="inputBorder${isMand }" readonly="readonly" style="width:80px;" value="${row1.getIspLicNo()}" maxlength="50">
											</td>
										</tr>
										<%-- [006] END PROJECT SATYAPAN --%>
										
										<tr> 
											<%-- <%if(!row1.getChannelMasterTagging().equalsIgnoreCase("1")){ %> --%>
											<%if(!("1".equalsIgnoreCase(row1.getChannelMasterTagging()))){ %>
											<c:set var="isMand" value="2"></c:set>
											<c:set var="valChannelMasterTagging" value="NO"></c:set>
											<%}else{ %>
											<c:set var="isMand" value="1"></c:set>
											<c:set var="valChannelMasterTagging" value="YES"></c:set>
											<%} %> 
											<td align="right" nowrap="nowrap" style="font-size:9px" >Channel Partner</br>Tagging </td>
											<td nowrap>										
												<%-- <input type="text" name="channelMasterTagging" styleId="channelMasterTagging" class="inputBorder1" style="width:100px; float:left;"  styleClass="inputDisabled" readonly="readonly" value="${valChannelMasterTagging }"> --%>
											<html:text name="channelMasterTagging" styleId="channelMasterTagging"  style="width:100px; float:left;"  styleClass="inputDisabled" readonly="true" property="${valChannelPartnerCtgry }"  value="${valChannelMasterTagging }">
											</html:text>
											</td>
											
											<td align="right" nowrap="nowrap" style="font-size:9px" >&nbsp;&nbsp;&nbsp;Channel Partner</td>
											
											<td align="right" nowrap="nowrap" colspan="2">
											<c:choose >
												<c:when test="${row1.getChannelPartnerId()!='' && row1.getChannelPartnerId()!=null }"><c:set var="valChannelPartnerCtgry" value="${row1.getChannelPartnerName()}"></c:set> </c:when>
												<c:otherwise><c:set var="valChannelPartnerCtgry" value="NA"></c:set></c:otherwise>
											</c:choose>										
											<html:text name="channelPartnerId" styleId="channelPartnerCtgry"  style="width:250px; float:left;margin-left:10px" styleClass="inputDisabled" readonly="true"  property="${valChannelPartnerCtgry }" value="${valChannelPartnerCtgry }">
											</html:text>
											</td>
											
											<td align="right" nowrap="nowrap" colspan="0">
											<c:choose >
												<c:when test="${row1.getChannelPartnerId()!='' && row1.getChannelPartnerId()!=null }"><c:set var="valChannelPartnerCode" value="${row1.getChannelpartnerCode()}"></c:set> </c:when>
												<c:otherwise><c:set var="valChannelPartnerCode" value="NA"></c:set></c:otherwise>
											</c:choose>										
											<html:text name="channelpartnerCode" styleId="channelPartnerCode"  style="width:170px; float:left;margin:2px" styleClass="inputDisabled" readonly="true"  property="${valChannelPartnerCode }" value="${valChannelPartnerCode }">
											</html:text>
											</td>
											
											<td align="right" nowrap="nowrap"  style="font-size:9px;margin-left:10px">FSE</td>
											
											<td nowrap="nowrap">
											<c:choose >
												<c:when test="${row1.getChannelPartnerId()!='' && row1.getChannelPartnerId()!=null }"><c:set var="valFieldEngineer" value="${row1.getFieldEngineer()}"></c:set> </c:when>
												<c:otherwise><c:set var="valFieldEngineer" value="NA"></c:set></c:otherwise>
											</c:choose>										
											<html:text name="fieldEngineer" styleId="fieldEngineer"  style="width:150px; float:left;" styleClass="inputDisabled" readonly="true"  property="${valFieldEngineer }" value="${valFieldEngineer }">
											</html:text>
											</td>
											
										</tr>
									</table>
								</logic:iterate>
							</logic:notEmpty>
						</logic:present>
						<logic:notPresent name="accountDetailsBean" scope="request">
							<table border="0" cellspacing="0" cellpadding="1" style="margin-top:7px ">
								<tr>
									<td style="width:20px;float:left;font-size:9px"> Order Type </td>
									<td><input name="orderType" class="inputDisabled" size="5" readonly="true" value="New" style="width:75px;"/></td>
									<td align="right" style="font-size:9px">Order Date</td>
									<%SimpleDateFormat sdf_Date=new SimpleDateFormat("dd/MM/yyyy");%>
									<td><input style="width:80px;" name="orderDate" class="inputDisabled" size="10" readonly="true" value="<%=sdf_Date.format(new Date(System.currentTimeMillis()))%>"/></td>
									<td align="right" style="font-size:9px">Order Origin</td>
									<td nowrap="nowrap">
										<html:text onmouseover='getTip(value)' onmouseout='UnTip()'  property="orderCreationSourceName"  styleClass="inputDisabled" style="width:150px;" styleId="txtOrderCreationSourceName" readonly="true"></html:text>
									</td>
									<td align="right" style="font-size:9px">Source</td>
									<td>
										<html:text onmouseover='getTip(value)' onmouseout='UnTip()' property="sourceName" styleClass="inputBorder1" style="width:100px; float:left;" readonly="true"></html:text>
										<div class="searchBg1"><a href="#" title="Select Source" onClick="popitup('SelectSource')">...</a></div>
									</td>
									<td align="right" style="font-size:9px"></td>
										<td nowrap="nowrap">
																				
									</td>
									</tr>
									<tr>
								<!-- [004]	START	-->
									<!--
									<td align="right" style="font-size:9px"> Quote No </td>
																		<td nowrap="nowrap">
										<div id="txtQuoteNo" style="display:block; ">
												<html:text onmouseover='getTip(value)' onmouseout='UnTip()'  property="txtquotesNo" styleClass="inputDisabled" styleId="txtquotesNo" style="width:100px;float:left;" readonly="true"></html:text>
										</div>	
										<div id="cboQuoteNo" style="display:none; ">										
											<html:select property="quoteNo" styleId="quoteNo" styleClass="inputBorder1" style="width:110px;float:left;">
												<html:option value="0">--Select--</html:option>
											</html:select>											
										</div>
									</td>		-->						
								<!-- [004]	End	-->
									<td align="right" style="font-size:9px"> Currency </td>
									<td colspan="2">
										<html:text onmouseover='getTip(value)' onmouseout='UnTip()' property="curShortCode" styleClass="inputBorder1" style="width:80px; float:left;" readonly="true"></html:text>
										<html:hidden property="currencyID"></html:hidden>
										<div class="searchBg1"><a href="#" title="Select Currency" onClick="return popitup('SelectCurrency')">...</a></div></td>
										<!-- [001]	Start 
										<td align="right" style="font-size:9px"> Opportunity ID </td>
										<td>
											<html:select property="opportunityId" styleClass="inputBorder1" style="width:110px;float:left;">
												<html:option value="0">--Select--</html:option>
											</html:select>	
										</td>
										 
										 [001]	End -->
								<!-- [004]	Start -->											
											<td colspan="2">											
											<input name="btnOpportunity" id="btnOpportunity" onClick="return popitup('SelectOpportunity');" type="button" value="View Opportunity/ePcn No.">
											<html:hidden property="opportunityId" styleId="opportunityId"/>
											</td>
											<html:hidden property="quoteNo" styleId="quoteNo"/>
											<html:hidden property="txtquotesNo" styleId="txtquotesNo"/>
								<!-- [004]	End -->	
									</tr> 
									
									<tr>
										<td align="right" style="font-size:9px">Order No</td>
										<td>
											<%int orderNo=((Integer)request.getAttribute("MaxOrderBean")).intValue();
											if (orderNo!=0)
											{%>
											<input type="text"  onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()'style="width:100px;float:left;" readonly="readonly" value="<%=orderNo %>" name="poNumber" class="inputDisabled" size="5" maxlength="10"/> 	
											<%} else {%>
											<html:text onmouseover='getTip(value)' onmouseout='UnTip()' property="poNumber" readonly="true" styleClass="inputDisabled" style="width:100px;float:left;" readonly="true" maxlength="10" ></html:text> 
											<html:hidden property="hdnOrderNo"/>
											<html:hidden property="hdnOrderCreationSource" styleId="hdnOrderCreationSourceId"/>
											<%} %>
										</td>
										<td align="right" style="font-size:9px"> Status </td>
										<td>
											<html:text onmouseover='getTip(value)' onmouseout='UnTip()' value="Incomplete" property="status" readonly="true" styleClass="inputDisabled" style="width:80px;" readonly="true"></html:text> 
										</td>
										<td align="right" style="font-size:9px"> Stage </td>
										<td>
											<html:hidden property="stageName" value="New"/>
											<html:text onmouseover='getTip(value)' onmouseout='UnTip()' property="orderStageAnnotationName" readonly="true" styleClass="inputDisabled" style="width:100px;" value="New"></html:text> 
										</td>
										<td align="right" style="font-size:9px"> Is Demo </td>
										<td><input type="checkbox" id="chkIsDemo" name="chkIsDemo" onclick="showNoOfDays()"> 
										</td>
										<td>
											<div id="dvNoOfDays">
												<!-- [004]	Start --> 
												No Of Days<html:text onmouseover='getTip(value)' onmouseout='UnTip()' property="noOfDaysForDemo" styleClass="inputBorder1" style="width:80px;" onblur="if( trim(this.value).length>0){ return demoValidation(this)};"></html:text>  
												<!-- [004]	End --> 
											<div>
										
										</td>
									</tr>
									<%-- [006] Start PROJECT SATYAPAN --%>
										<tr>
											<td align="right" style="font-size:9px" >ISP Tagging</td>
											<td nowrap>										
												<html:select property="ispTagging" styleId="ispTagging" styleClass="inputBorder1" style="width:100px; float:left;"  onchange="checkIspTaggingMandate(this.selectedIndex);" onfocus="chkIsISPChangeAllowed(1,this.id);">
													<html:option value="0">NO</html:option>
													<html:option value="1">YES</html:option>
												</html:select>
											</td>
											<td align="right" style="font-size:9px" >ISP License Category</td>
											<td nowrap>										
												<html:select property="ispLicCtgry" styleId="ispLicCtgry" styleClass="inputBorder2" style="width:100px; float:left;"  onfocus="chkIsISPChangeAllowed(0,this.name);">
													<html:option value="0">NA</html:option>
													<html:option value="1">A</html:option>
													<html:option value="2">B</html:option>
													<html:option value="3">C</html:option>
												</html:select>
											</td>
											<td align="right" style="font-size:9px" >ISP License Date</td>
											<td nowrap>
												<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" width="100%" class="inputBorder2" isRequired="0" id="ispLicDate" name="ispLicDate" readonly="readonly" onblur="if(this.value.length > 0){return checkdate(this)}" onfocus="chkIsISPChangeAllowed(0,this.name);">
												<font size="1">
													<a href="#" class="borderTabCal"><img id="dateIspLic" src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Select date" style="vertical-align: bottom;"></a>											
												</font>
											</td>
											<td align="right" style="font-size:9px" >ISP License Number</td>
											<td nowrap>
												<html:text onmouseover='getTip(value)' onmouseout='UnTip()' property="ispLicNo" styleClass="inputBorder2" style="width:80px;" onfocus="chkIsISPChangeAllowed(0,this.name);" maxlength="50"></html:text>
											</td>
										</tr>
										
										<%-- <tr>
											<td align="right" nowrap="nowrap" style="font-size:9px" >Channel Partner Tagging</td>
											<td nowrap>										
												<html:select property="channelMasterTagging" styleId="channelMasterTagging" styleClass="inputDisabled" style="width:100px; float:left;"  onchange="checkchannelMasterTaggingMandate(this.selectedIndex);" onfocus="chkIschannelMasterTaggingAllowed(1,this.id);">
													<html:option value="0">NO</html:option>
													<html:option value="1">YES</html:option>
												</html:select>
											</td>
											
											<td align="right" nowrap="nowrap" style="font-size:9px" >&nbsp;&nbsp;&nbsp;Channel Partner</td>
											<td nowrap>										
												<html:select property="channelPartnerCtgry" styleId="channelPartnerCtgry" styleClass="inputDisabled" style="width:100px; float:left;"  onfocus="chkIschannelMasterTaggingAllowed(0,this.name);">
													<html:option value="">--Select--</html:option>
												</html:select>
												
											</td>
										</tr> --%>
										
										
										<%-- [006] END PROJECT SATYAPAN --%>
								</table>
							</logic:notPresent>
						</fieldset>
					</td>
				</tr>
			</table>
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="50%">
						<OL id="toc" class="tabledata">
						<!-- //--[002]--start of modiyed some code--// -->
							<li id="li1" class="current" onClick="changeTab('li1',1,'block','none','none','none','none')"><a href="#" >MAIN</a></li>
							<logic:present name="MainDetailsWithAttributesBean" scope="request"> <li id="li2" class="" onClick="changeTab('li2',2,'none','block','none','none','none')"><a href="#">CONTACT</a></li></logic:present>
							<!-- [016] Start-->
							<logic:notEqual value="3" name="formBean" property="changeTypeID">
								<logic:notEqual value="1" name="formBean" property="changeTypeID">
									<logic:present name="listContactDetails" scope="request">
										<logic:notEmpty name="listContactDetails">
											<li id="li3" class="" onClick="changeTab('li3',3,'none','none','block','none','none')"><a href="#">PO DETAILS</a></li>
										</logic:notEmpty>
									</logic:present>
								</logic:notEqual>	
							</logic:notEqual>
					
							<logic:notEqual value="3" name="formBean" property="changeTypeID">
								<logic:notEqual value="1" name="formBean" property="changeTypeID">
									<logic:present name="listPoDetails" scope="request">
										<logic:notEmpty name="listPoDetails">
											<li id="li5" class="" onClick="changeTab('li5',5,'none','none','none','block','none')"><a href="#">LINES</a></li>
										</logic:notEmpty>
									</logic:present>
								</logic:notEqual>	
							</logic:notEqual>
							
							<logic:equal value="3" name="formBean" property="changeTypeID">
								<logic:notEmpty name="listContactDetails">
									<li id="li5" class="" onClick="changeTab('li5',5,'none','none','none','block','none')"><a href="#">LINES</a></li>
								</logic:notEmpty>	
							</logic:equal>
							
							<logic:equal value="1" name="formBean" property="changeTypeID">
								<logic:notEmpty name="listContactDetails">
									<li id="li5" class="" onClick="changeTab('li5',5,'none','none','none','block','none')"><a href="#">LINES</a></li>
								</logic:notEmpty>	
							</logic:equal>
							<!-- [017] End-->
							
							<% String modeValue=request.getParameter("modeName");
								//if(("editON").equalsIgnoreCase(modeValue) || ("editOFF").equalsIgnoreCase(modeValue) || ("viewMode").equalsIgnoreCase(modeValue))
								{%>
							<logic:present name="taskListOfOrder" scope="request">
								<!-- [017] Start-->
								<logic:notEqual value="3" name="formBean" property="changeTypeID">
									<logic:notEqual value="1" name="formBean" property="changeTypeID">	
										<logic:notEmpty name="listPoDetails">
											<li id="li6" class="" onClick="changeTab('li6',6,'none','none','none','none','block')"><a href="#">APPROVAL</a></li>
										</logic:notEmpty>
									</logic:notEqual>
								</logic:notEqual>	
								<logic:equal value="3" name="formBean" property="changeTypeID">
									<li id="li6" class="" onClick="changeTab('li6',6,'none','none','none','none','block')"><a href="#">APPROVAL</a></li>
								</logic:equal>
								<logic:equal value="1" name="formBean" property="changeTypeID">
									<li id="li6" class="" onClick="changeTab('li6',6,'none','none','none','none','block')"><a href="#">APPROVAL</a></li>
								</logic:equal>
								<!-- //--[002]--end of modiyed some code--// -->
								<!-- [017] End-->
							</logic:present>
							<%}%>
							<input type="hidden" name="hdnTabVal" value="1">				
						</OL>
					</td>
						<!-- [15032013017]  Start-->
						
					<td width="4%" align="left"><img id="idCountLineItemsImg" src="gifs/middle/calc.gif" width="25px" height="25px" alt="Count Line Items" onclick="return countitems(gb_path,'','View')" title="Count Items"></td>
					<!-- [15032013017]  Start-->
					
				<%String modeValue2= request.getParameter("modeName");
					if(("editON").equals(modeValue2))
					{
				 %>
					<td width="50%" align="right">
						<!--<logic:equal name="formBean" property="isOrderPublished" value="DISABLE" >
							<html:button property="" value="Publish"  disabled="true"/>&nbsp;
						</logic:equal>-->	
						<html:button property="" value="Publish"  disabled="true"/>&nbsp;
						<% 
						if((AppConstants.SED_ROLE).equals(objUserDto.getUserRoleId()))
						{
						%>
						<!--<logic:equal name="formBean" property="isOrderPublished" value="ENABLE">
							<html:button property="" value="Publish" onclick="javascript:fnPublish();"/>&nbsp;
						</logic:equal>-->
						<%
						}
						 %>						
						<!--<logic:equal name="formBean" property="isBillingTriggerReady" value="ENABLE">
							<html:button property="" styleId="billingTriggerID" value="Billing Trigger" onclick="javascript:fnBillingTrigger();" />&nbsp;
						</logic:equal>-->
							<html:button property="" styleId="billingTriggerID" value="Billing Trigger" onclick="javascript:fnBillingTrigger();" />&nbsp;						
						<!--<logic:equal name="formBean" property="isBillingTriggerReady" value="DISABLE">
							<html:button property=""  styleId="billingTriggerID" value="Billing Trigger" disabled="true" onclick="javascript:fnBillingTrigger();"/>&nbsp;
						</logic:equal>-->
						
						<!--<logic:equal name="formBean" property="isOrderPublished" value="1">
						<logic:equal name="formBean" property="isBillingTriggerReady" value="2">
							<html:button property=""  value="Check Status" onclick="fnBillingTriggerEnable();" />&nbsp;
						</logic:equal>
						</logic:equal>-->
						
						
						
						<html:button property="" value="Charge Summary" onclick="javascript:fnChargeSummary();"/>&nbsp;&nbsp;
						<html:button property="" value="Export Charge Summary" onclick="javascript:fnExportChargeSummary();"/>&nbsp;&nbsp;
					</td>
					<%} else { %>
					<td width="50%" align="right">
						<html:button property="" value="Charge Summary" onclick="javascript:fnChargeSummary();"/>&nbsp;&nbsp;
						<html:button property="" value="Export Charge Summary" onclick="javascript:fnExportChargeSummary();"/>&nbsp;&nbsp;
						</td>
					<%}  %>	
				</tr>
			</table>
			<!--  FOR tab 1 Start -->
			<div style="margin:0px 2px 0px 2px;display: block" id="Page_1" class="content" >
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100%" valign="top">
							<jsp:include flush="true" page="viewExtendedAttributes.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</div>
			<!--  FOR tab 1 Ends -->
			<!--  FOR tab 2 Start -->
			<div style="margin:0px 2px 0px 2px;display: none" id="Page_2" class="content">
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td valign="top">
						    <jsp:include flush="true" page="Contact.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</div>
			<!--  FOR tab 2 End -->
			<!--  FOR tab 3 Start -->
			<div style="margin:0px 2px 0px 2px;display: none" id="Page_3" class="content">
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td valign="top">
						    <jsp:include flush="true" page="PODetails.jsp"></jsp:include>
							<!--#include file="Contacts.asp" -->
						</td>
					</tr>
				</table>
			</div>
			<!--  FOR tab 3 End -->
			<!--  FOR tab 5 Start -->
			<!--  Billing trigger extra button removed because it was appearing twice in View order  -->
			<div style="margin:0px 2px 0px 2px;display: none" id="Page_5" class="content">
			<table width="130%" border="0" cellpadding="0" cellspacing="0">
				<tr>
				   <td width="60%"></td>
					<td >
					  <logic:equal name="formBean" property="isBillingTriggerReady" value="ENABLE">
							<html:button property="" styleId="billingTriggerID" value="Billing Trigger" onclick="javascript:fnBillingTrigger();" />&nbsp;
						</logic:equal>
						<logic:equal name="formBean" property="isBillingTriggerReady" value="DISABLE">
							<html:button property=""  styleId="billingTriggerID" value="Billing Trigger" disabled="true" onclick="javascript:fnBillingTrigger();"/>&nbsp;
						</logic:equal>
					</td>
				</tr>
			</table>
				<!--<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<logic:equal name="formBean" property="isBillingTriggerReady" value="DISABLE">							
							<td width="4%" align="left"><img src="gifs/middle/4.gif"
					onclick="SelectProductCatelog()" title="Product Catelog"
					id="productcatelog"></td>							
							<td width="4%" align="left" ><img src="gifs/middle/Copy Order.gif" onClick="CopyOrder();" title="Copy Order"></td>
							<td width="4%" align="left"><img src="gifs/middle/Copy Charge.GIF" alt="Copy charge for service" onclick="copyServiceCharge();" title="Copy Charge"></td>
							<td width="4%" align="left"><input type="text" class="inputBorder1" name="txtCopyProduct" />&nbsp;&nbsp;&nbsp;</td>
							<td width="4%" align="left"><img src="gifs/middle/11.gif" alt="Copy product for service" onclick="copyServiceProduct();" title="Copy Product"></td>	                        							
							<td width="5%" align="left" ><div class="searchBg"><a href="#" onclick="cancelService();" title="Cancel Service">Cancel Service</a></div></td>
							<td width="5%">
								<div class="searchBg" style="margin-right:10px;"><a href="#" onClick="return validateOrder(0)" title="Validate Order will Submit your Order for Approval">Validate Order</a></div>
							</td>
							<td width="5%" align="left" ><div class="searchBg"><a href="#" onclick="fnDeletePro();" title="Delete Product">Delete Product</a></div></td>
						</logic:equal>
						<logic:equal name="formBean" property="isBillingTriggerReady" value="">							
							<td width="4%" align="left"><img src="gifs/middle/4.gif" onClick="SelectProductCatelog()" title="Product Catelog"  id="productcatelog"></td>							
							<td width="4%" align="left" ><img src="gifs/middle/Copy Order.gif" onClick="CopyOrder();" title="Copy Order"></td>
							<td width="4%" align="left"><img src="gifs/middle/Copy Charge.GIF" alt="Copy charge for service" onclick="copyServiceCharge();" title="Copy Charge"></td>
							<td width="4%" align="left"><input type="text" class="inputBorder1" name="txtCopyProduct" />&nbsp;&nbsp;&nbsp;</td>
							<td width="4%" align="left"><img src="gifs/middle/11.gif" alt="Copy product for service" onclick="copyServiceProduct();" title="Copy Product"></td>				
							<td width="5%" align="left" ><div class="searchBg"><a href="#" onclick="cancelService();" title="Cancel Service">Cancel Service</a></div></td>
							<td width="5%">
								<div class="searchBg" style="margin-right:10px;"><a href="#" onClick="return validateOrder(0)" title="Validate Order will Submit your Order for Approval">Validate Order</a></div>
							</td>
							<td width="5%" align="left" ><div class="searchBg"><a href="#" onclick="fnDeletePro();" title="Delete Product">Delete Product</a></div></td>
						</logic:equal>
					</tr>
				</table> -->
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
					<tr width=100%>
						<td  valign="top" width="63%">
							<jsp:include flush="true" page="ServiceType.jsp"></jsp:include>
						</td>
						<td  valign="top" width="48%">
							<fieldset class="border1" style="width:88%">
								<legend id="lblServiceDetail"  class="legendwrap"> <b>Service Details</b> </legend>
								<table width="100%"  border="1" cellspacing="0" cellpadding="0">
									<tr>
										<td>
											<jsp:include flush="true" page="TreeView.jsp"></jsp:include>
										</td>
									</tr>
								</table>
							</fieldset>
						</td>
					</tr>
				</table>
			</div>
			<!--  FOR tab 5 End -->
			<!--  FOR tab 6 Start -->
			<div style="margin:0px 2px 0px 2px;display: none" id="Page_6" class="content">
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td valign="top">
						    <jsp:include flush="true" page="../../workqueue/approval.jsp"></jsp:include>
							<!--#include file="Approval.asp" -->
						</td>
					</tr>
				</table>
			</div>
			
		</html:form>
</body>
<!--start [012]-->
<script type="text/javascript">
	var currentTab = document.getElementById('latestTab').value;
	if("li1" == currentTab )
		changeTab(currentTab,1,'block','none','none', 'none','none');	
	else if("li2" == currentTab )
		changeTab(currentTab,2,'none','block','none', 'none','none');	
	else if("li3" == currentTab )
		changeTab(currentTab,3,'none','none', 'block', 'none','none');	
	else if("li5" == currentTab )
		changeTab(currentTab,5,'none','none', 'none', 'block', 'none');
	else
		fetchFeildList('Main');		

</script>
<!--End [012] -->
</html>
