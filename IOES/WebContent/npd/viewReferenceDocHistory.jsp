<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.ibm.ioes.npd.beans.ReferenceDocBean;"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">

<script language="JavaScript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/scw.js"></script>
<script type="text/javascript" src="js/timepicker.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>
<script type="text/javascript" src="js/inputColor.js"></script>
<script language="javascript" src="js/utility.js"></script>
<title>ChangeOrder Workflow</title>

<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>
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

<script type="text/javascript" src="js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>

<script type="text/javascript">

function downloadFile(var1)
{
	var appContextPath = '<%=request.getContextPath()%>';
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var1 = jsonrpc.processes.encryptString(var1)
	document.referenceDocBean.action="<c:out value='${sessionScope.MenuContextPath}' />/manageReferenceDocHistory.do?method=downloadFile&refDocId="+var1;
	document.referenceDocBean.submit();	
}

function initializePage()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	var frmDt=document.getElementById('fromDate').value;
	var toDt=document.getElementById('toDate').value;
	if(frmDt=='' && toDt!='')
	{
		alert('Please select From Date');
		return false;
	}
	else if(frmDt!='' && toDt=='')
	{
		alert('Please select To Date');
		return false;
	}
	if(!compareTwoDates(frmDt,toDt))
	{			
		alert("From date should not be greater than To date");
		return false;
		
	}
	if(myform.rfDocSearch!="")
	{
		if(ValidateTextField(myform.rfDocSearch,'<%=request.getContextPath()%>',"Document Name")==false)
		{
			return false;
		}
	}
	document.referenceDocBean.action="<c:out value='${sessionScope.MenuContextPath}' />/manageReferenceDocHistory.do?method=viewReferenceDocHistory";
	
	showLayer();
	document.referenceDocBean.submit();			
}
function searchSubmit()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	var frmDt=document.getElementById('fromDate').value;
	var toDt=document.getElementById('toDate').value;
	if(frmDt=='' && toDt!='')
	{
		alert('Please select From Date');
		return false;
	}
	else if(frmDt!='' && toDt=='')
	{
		alert('Please select To Date');
		return false;
	}
	if(!compareTwoDates(frmDt,toDt))
	{			
		alert("From date should not be greater than To date");
		return false;
		
	}
	if(myform.rfDocSearch!="")
	{
		if(ValidateTextField(myform.rfDocSearch,'<%=request.getContextPath()%>',"Document Name	")==false)
		{
			return false;
		}
	}
	myform=document.getElementById('referenceDocBean');
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	showLayer();
	myform.submit();
}

function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	var frmDt=document.getElementById('fromDate').value;
	var toDt=document.getElementById('toDate').value;
	if(frmDt=='' && toDt!='')
	{
		alert('Please select From Date');
		return false
	}
	else if(frmDt!='' && toDt=='')
	{
		alert('Please select To Date');
		return false
	}
	if(!compareTwoDates(frmDt,toDt))
	{			
		alert("From date should not be greater than To date");
		return false;
		
	}
	if(myform.rfDocSearch!="")
	{
		if(ValidateTextField(myform.rfDocSearch,'<%=request.getContextPath()%>',"Document Name	")==false)
		{
			return false;
		}
	}
	myform=document.getElementById('referenceDocBean');
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.action="<c:out value='${sessionScope.MenuContextPath}' />/manageReferenceDocHistory.do?method=viewReferenceDocHistory";
	showLayer();
	myform.submit();
}
function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	var frmDt=document.getElementById('fromDate').value;
	var toDt=document.getElementById('toDate').value;
	if(frmDt=='' && toDt!='')
	{
		alert('Please select From Date');
		return false
	}
	else if(frmDt!='' && toDt=='')
	{
		alert('Please select To Date');
		return false
	}
	if(!compareTwoDates(frmDt,toDt))
	{			
		alert("From date should not be greater than To date");
		return false;
		
	}
	if(myform.rfDocSearch!="")
	{
		if(ValidateTextField(myform.rfDocSearch,'<%=request.getContextPath()%>',"Document Name	")==false)
		{
			return false;
		}
	}
	myform=document.getElementById('referenceDocBean');
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
	showLayer();
	myform.submit();
}
</script>

</head>

<body onload="javascript:document.body.click();">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
    <br /><br /><br />
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="newProduct">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Reference
				Doc History</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<br>
<logic:present name="validation_errors">
<table width="100%" class="tabledata" align="center"><tr><td>
<div class="error" align="center">
			
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>

</div></td></tr></table>
</logic:present>
<html:form action="/manageReferenceDocHistory" method="post" styleId="searchForm">
	<table cellSpacing="0" cellPadding="0" width="98%" border="0" id="tblRollList" class="tabledata" style="display:block" align="center">
		<tr height="20" valign="middle" bgcolor="#FF9255">
			<td width="10%" nowrap>Document Name</td>
			<td width="16%" nowrap>Created Date</td>
			<td width="16%" nowrap>Document Version</td>
			<td width="10%" nowrap>created By</td>
		</tr>
		<html:hidden property="pagingSorting.pageNumber" />
		<html:hidden property="pagingSorting.sortByColumn" />
		<html:hidden property="pagingSorting.sortByOrder" />
		<tr height="20" valign="middle">

			<td width="10%"><html:text property="rfDocSearch" maxlength="30"/> </td>
			<td>From Date
			<html:text property="fromDate" readonly="true" /> <img
				src="npd/Images/cal.gif" width="16" height="16" border="0"
				alt="Pick a date" onclick="scwShow(scwID('fromDate'),event);">
			<br>To Date &nbsp; &nbsp; <html:text property="toDate" readonly="true" /> <img
				src="npd/Images/cal.gif" width="16" height="16" border="0"
				alt="Pick a date" onclick="scwShow(scwID('toDate'),event);"></td>
			<td width="16%" colspan="3"><img src="npd/Images/search.gif"
				onclick="initializePage();" /></td>


		</tr>
		<logic:notEmpty name="referenceDocBean" property="referenceDocList">
			<tr>
				<td colspan="15" class="tabledata"><bean:define id="pagingBean"
					name="referenceDocBean"></bean:define> <%
 			String currPageNumber = String
 			.valueOf(((ReferenceDocBean) pagingBean).getPagingSorting()
 			.getPageNumber());
 	String maxPageNumber = String
 			.valueOf(((ReferenceDocBean) pagingBean).getPagingSorting()
 			.getMaxPageNumber());
 %> <jsp:include flush="true" page="html-jsp/commons/paging.jsp">
					<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
					<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
				</jsp:include></td>
			</tr>
			<logic:iterate id="referenceDocBean_id" name="referenceDocBean"
				property="referenceDocList" indexId="index1">
				
				<tr class="alterRedBg">
					<bean:size id="inactiveRefdoclength" name="referenceDocBean_id"
						property="value" />
						<c:if test="${referenceDocBean_id.key.refdocname!=null}">
					<td><c:out value="${referenceDocBean_id.key.refdocname}" /></td>
					
					<td><fmt:parseDate
						value="${referenceDocBean_id.key.createddate}" type="DATE"
						pattern="yyyy-MM-dd" var="parsedDate" /> <fmt:formatDate
						value="${parsedDate}" pattern="dd-MMM-yyyy" type="DATE"
						var="formatedDate" /><c:set var="createdDate" value="${formatedDate}"/><c:out value="${formatedDate}" /></td>
					<td>
					<a href="#"
						onclick="downloadFile(<bean:write name="referenceDocBean_id" property="key.refdocid" />);">
					<c:out value="Version ${inactiveRefdoclength+1}.0" /></a></td>
					<td><c:out
						value="${referenceDocBean_id.key.createdby.empname}" /></td>
						</c:if>
				
					<logic:notEmpty property="value" name="referenceDocBean_id">
						<logic:iterate id="inactiveRefList" name="referenceDocBean_id"
							property="value" indexId="index1">
							<tr class="redBg">
								<td><bean:write name="inactiveRefList"
									property="refdocname" /></td>

								<td><fmt:parseDate value="${inactiveRefList.createddate}"
									type="DATE" pattern="yyyy-MM-dd" var="parsedDate" /> <fmt:formatDate
									value="${parsedDate}" pattern="dd-MMM-yyyy" type="DATE"
									var="formatedDate" /><c:out value="${formatedDate}" /></td>
								<td><a href="#"
									onclick="downloadFile(<bean:write name="inactiveRefList" property="refdocid" />);">
								<c:out value="Version ${inactiveRefdoclength-index1}.0" /></a></td>
								<td><c:out value="${inactiveRefList.createdby.empname}" /></td>
						</logic:iterate>
					</logic:notEmpty>
				<tr bgcolor="red">
					<td colspan="4" style="border-width:2px; border-color:#FF9255;">
					</td>
				</tr>

			</logic:iterate>
		</logic:notEmpty>
		<logic:empty name="referenceDocBean" property="referenceDocList">
			<tr align="center">
				<td colspan="4"><font color="red">No Record Exists</font></td>
			</tr>
		</logic:empty>

	</table>
</html:form>
</div>
</body>
</html:html>

