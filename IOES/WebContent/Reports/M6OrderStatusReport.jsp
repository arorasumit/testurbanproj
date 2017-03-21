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
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>M6 Order Status - Report</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
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

function checkForm()
{
	return true;
}

function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	myform.toExcel.value='false';
	setFormBean(myform);
	myform.reset();
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.method.value='viewM6OrderList';	
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
	setFormBean(myform);
	myform.reset();
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
	if(checkForm()==false)
	{
		return ;
	}

	myform.method.value='viewM6OrderList';	
    showLayer();
	myform.submit();
}


function popitup(url) 
{
	if(url=="SelectAccount")
	{
		var path = "<%=request.getContextPath()%>/bcpAddress.do?method=fetchAccount";		
		window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:530px");
	}
	return false;
}

function searchClick()
{
	var myForm=document.getElementById('searchForm');
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewM6OrderList";
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
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewM6OrderList&inExcel=true";
		myForm.submit();
	}
}
function fnViewM6OrdRspnsHistory(m6OrderNo)
{	
       var path = "<%=request.getContextPath()%>/reportsAction.do?method=viewM6RspnsHistory&m6OrderNo="+m6OrderNo;		
	
		window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:600px");
      
  } 
function isBlankForm()
{	var myForm=document.getElementById('searchForm');
	var accId=myForm.crmAccountNo.value;
	var locId=myForm.orderNoStr.value;
	var accName=myForm.accountNameStr.value;
	if((accId==null && locId==null  && accName == null) || (trim(accId)=="" && trim(locId)==""  && trim(accName) == "")){
		alert("Please enter atleast one search criteria!");
		return false;
	} 
	else {
		return true;
	}
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.crmAccountNo.value='' ; 
	myForm.accountNameStr.value='' ;
	myForm.orderNoStr.value='' ; 
	
}

path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body >
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/reportsAction" styleId="searchForm" method="post">
		<bean:define id="formBean" name="reportsBean"></bean:define>
		<html:hidden property="interfaceId"/>
		<html:hidden property="toExcel"/>
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="pagingSorting.sortByOrder"/>
		<html:hidden property="pagingSorting.pageNumber"/>
		<input type="hidden" name="method" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="left">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> <span>M6 ORDER STATUS REPORT</span> </div>
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
		<fieldset width="100%" border="1" align="center" class="border2">
			<legend><b>Search</b></legend>
				<table border="0" cellspacing="0"  align="center" cellpadding="0" style="margin-top:7px">
					<tr>
						<td align="right" style="font-size:9px"> A/C No:</td>
						<td align="left">
							<html:text  property="crmAccountNo" styleClass="inputBorder1" style="width:50px;float:left;" readonly="true" ></html:text>
							<div class="searchBg1"><a href="#" onClick="return popitup('SelectAccount')">...</a></div> 
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;" nowrap>A/C Name:</td>
						<td align="left"><html:text  property="accountNameStr" styleClass="inputBorder1" style="width:160px;"  onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'A/C Name')};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text></td>
						<td width="10px"/>
						<td align="right" style="font-size:9px">OE Order Number:</td>
						<td align="left">
							<html:text  property="orderNoStr" styleClass="inputBorder1" style="width:50px;float:left;" maxlength="18" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" onkeydown="if (event.keyCode == 13) return searchClick();"></html:text>
						</td>
						<td width="10px"/>
						<td align="left">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<td width="30px"/>
						<td align="right" width="70px"><div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div></td>
						<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
					</tr>
					<tr height="5px"/>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>M6 Order Status Details</b></legend>
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
			<div class="scrollWithAutoScroll1" class="head"  style="height:350px;width:99% " >
			<table class="tab2" style="overflow:auto;" width="99%" border="1" align="center" id='tblM6OrderStatusReport'>
				<tr>
					<td align="center" style="font-size:9px"><b>
						<logic:present name="orderList" scope="request">
							<logic:notEmpty  name="orderList" scope="request">
								<a href="#" onclick="sortSubmit('accountId')">Account No</a>
							</logic:notEmpty>
							<logic:empty  name="orderList" scope="request">
								Account No
							</logic:empty>
						</logic:present>
					</b></td>
					<td align="center" style="font-size:9px"><b>Account Name </b></td> 							
					<td align="center" style="font-size:9px"><b>
						<logic:present name="orderList" scope="request">
							<logic:notEmpty  name="orderList" scope="request"> 
							<a href="#" onclick="sortSubmit('orderNO')">OE Order No.</a>
							</logic:notEmpty>
							<logic:empty  name="orderList" scope="request">
								OE Order No.
							</logic:empty>
						</logic:present>
					</b></td>
					<td align="center" style="font-size:9px"><b>M6 Order No.</b></td>
					
				</tr>
				<logic:present name="orderList" scope="request">
					<logic:notEmpty  name="orderList" scope="request"> 					
						<logic:iterate id="row" name="orderList" indexId="recordId">
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
								<td align="left" style="font-size:9px"><bean:write  name="row" property="accountID"/></td>
								<td align="left" style="font-size:9px"><bean:write  name="row" property="accountName"/></td>
								<td align="left" style="font-size:9px"><bean:write  name="row" property="orderNo"/></td>
								
								<td align="left" style="font-size:9px" width="5%">
									<a href="javascript:fnViewM6OrdRspnsHistory('<bean:write name="row" property="m6OrderNo" />');">
										<font color="Brown">                       
											<bean:write name="row" property="m6OrderNo" />
										</font>
									</a>
								</td>
								
								
							</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty  name="orderList">
					   <tr align="center" >
						 <td colspan="17" align="center"><B><font color="red">No Records Found</font></B></td>
					   </tr>
					</logic:empty>	
				</logic:present>				
			</table>
			</div>
		</fieldset>
		<input type="hidden" name="hdnOrderNo" id="hdnOrderNo" />
	</html:form>
</body>
</html>
