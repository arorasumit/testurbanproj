<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.ibm.ioes.npd.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.npd.hibernate.beans.TmEmployee"%>
<%@page import="com.ibm.ioes.npd.beans.AssumptionCaptureBean;"%>
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

function searchAssumptionDetails()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	if((document.getElementById("projectId").value==null||trim(document.getElementById("projectId").value)=="")&&(document.getElementById("projectName").value==null||trim(document.getElementById("projectName").value)=="")&&document.getElementById('fromDate').value==""&&document.getElementById('toDate').value=="")
	{
		alert("Please enter Project Id or Project Name or From & To Date");
		return false;
	}
	
	
	if(CheckNumeric(myform.projectId,"Project ID")==false)
	{
		return false;
	}	

	if(ValidateTextField(document.getElementById("projectName"),'<%=request.getContextPath()%>',"Project Name")==false)
	{
		return false;
	}	
	
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
	
	showLayer();
	return true;
}

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}


function searchSubmit()
{
	myform=document.getElementById('assumptionBean');
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	showLayer();
	myform.submit();
}

function pagingSubmit(val)
{
	myform=document.getElementById('assumptionBean');
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.action="<c:out value='${sessionScope.MenuContextPath}' />/assumptionCapture1.do?method=search";
	showLayer();
	myform.submit();
}
function sortSubmit(sortBy)
{
	myform=document.getElementById('assumptionBean');
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
<logic:present name="validation_errors">
	<table width="80%" class="tab2" align="center">
		<tr>
			<td>
				<div class="error" align="center">
					<logic:iterate id="error_row" name="validation_errors" scope="request">
							<font color="red"><bean:write name="error_row" /></font><BR>
					</logic:iterate>
				</div>
			</td>
		</tr>
	</table>
</logic:present>
<logic:present name="validation_errors1">
	<table width="80%" class="tab2" align="center">
		<tr>
			<td>
				<div class="error" align="center">
					<font color="red">Please Select Project ID, Project Name or From and To Date to Search</font><BR>
				</div>
			</td>
		</tr>
	</table>
</logic:present>
<br>
<html:form action="/assumptionCapture1" method="post" styleId="searchForm"
	onsubmit="return searchAssumptionDetails();">
	<table width="80%" border="0" cellpadding="0" cellspacing="0"
		class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
			<!--<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr>
					<td><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">View
					Assumption</td>
					<td><img src="npd/Images/tabRight.gif"></td>
					<td width="70%"><img src="npd/Images/zero.gif" width="15"
						height="1"></td>
				</tr>
			</table>-->
			</td>
		</tr>
	</table>
	<table width="80%" border="2" class="tab2" cellpadding="0"
		cellspacing="0" align="center">
		<tr>
			<td width="70%">
			<table width="100%" border="0" class="tab2" cellpadding="0"
				cellspacing="0">
				<tr align="center">
				<tr>
					<th width="30%" scope="row">
					<div align="center">Project ID<font color="#990033"></font></div>
					</th>
					<td><html:select property="projectId" styleClass="inputBorder1" >
							<html:option value="">-Select-</html:option>
							<logic:iterate id="projectList_id" name="assumptionBean" property="searchProjectList"  indexId="index1">
								<html:option value="${projectList_id.projectid}">
									<c:out 	value="${projectList_id.projectid}-${projectList_id.projectNameShort}" />
								</html:option>
							</logic:iterate>
						</html:select>
						</td>
				</tr>
				<html:hidden property="pagingSorting.pageNumber" />
				<html:hidden property="pagingSorting.sortByColumn" />
				<html:hidden property="pagingSorting.sortByOrder" />

				<tr>
					<th width="30%" scope="row">
					<div align="center">Project Name<font color="#990033"></font></div>
					</th>
					<td><html:text property="projectName" maxlength="20"
						style="width:140px" /></td>
				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="center">From Date<font color="#990033"></font></div>
					</th>
					<td><html:text property="fromDate" readonly="true" /> <img
						src="npd/Images/cal.gif" width="16" height="16" border="0"
						alt="Pick a date" onclick="scwShow(scwID('fromDate'),event);">
					</td>
				</tr>

				<tr>
					<th width="30%" scope="row">
					<div align="center">To Date<font color="#990033"></font></div>
					</th>
					<td><html:text property="toDate" readonly="true" /> <img
						src="npd/Images/cal.gif" width="16" height="16" border="0"
						alt="Pick a date" onclick="scwShow(scwID('toDate'),event);">
					</td>
				</tr>

				<html:hidden property="fromDate" />
				<html:hidden property="toDate" />

				<tr align="center">
					<th colspan="4" scope="row">
					<table width="50%" border="0">
						<tr>
							<td><html:submit property="method">
								<bean:message key="submit.onlysearchAssumption"
									bundle="ButtonResources" />
							</html:submit></td>

						</tr>
					</table>
				</tr>
					<br>

				<logic:messagesPresent>
					<table width="50%" align="center">
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td><font color="red" face="Verdana" size="2"><html:messages
								id="message">
								<li><bean:write name="message" /></li>
							</html:messages></font></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
					</table>
				</logic:messagesPresent>
				<logic:messagesPresent message="true">
					<table width="50%" align="center">
						<tr>
							<td>&nbsp;</td>
						</tr>
						<tr>
							<td><font color="red" face="Verdana" size="2"><html:messages
								id="message" message="true">
								<li><bean:write name="message" /></li>
							</html:messages></font></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
						</tr>
					</table>
				</logic:messagesPresent>
				<br>
				<br>
				<br>
				<logic:notEmpty name="assumptionBean"
					property="assumptionDetailList">
					<table width="100%" border="1" class="tab2" cellpadding="3"
						cellspacing="1" align="center">
						<tr>
							<td colspan="4"><bean:define
								id="pagingBean" name="assumptionBean"></bean:define> <%
 			String currPageNumber = String
 			.valueOf(((AssumptionCaptureBean) pagingBean).getPagingSorting().getPageNumber());
 	String maxPageNumber = String
 			.valueOf(((AssumptionCaptureBean) pagingBean).getPagingSorting().getMaxPageNumber());
 %> <jsp:include flush="true" page="html-jsp/commons/paging.jsp">
								<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
								<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
							</jsp:include></td>
						</tr>
						<tr class="grayBg">
							<th background="#FF9255">ProjectId</th>
							<th background="#FF9255">Description</th>
							<th background="#FF9255">Impact</th>
							<th background="#FF9255">Raised On</th>
						</tr>
						<logic:iterate id="assumptionDetailList_id" name="assumptionBean"
							property="assumptionDetailList" indexId="index1">
							<c:if test="${index1%2==0}">
								<tr class="normal" align="center">
									<td><bean:write name='assumptionDetailList_id'
										property='ttrnProject.projectid' /></td>
									<td style="width:200"><bean:write
										name='assumptionDetailList_id' property='description' /></td>
									<td><logic:equal name='assumptionDetailList_id'
										property='impact' value="0">
										 High
										</logic:equal> <logic:equal name='assumptionDetailList_id' property='impact'
										value="1">
										 Medium
										</logic:equal> <logic:equal name='assumptionDetailList_id' property='impact'
										value="2">
										Low
										</logic:equal></td>
									<td><fmt:parseDate
										value="${assumptionDetailList_id.raiseddate}" type="DATE"
										pattern="yyyy-MM-dd" var="parsedDate" /> <fmt:formatDate
										value="${parsedDate}" pattern="dd-MMM-yyyy" type="DATE"
										var="formatedDate" /><c:out value="${formatedDate}" /></td>
								</tr>
							</c:if>
							<c:if test="${index1%2!=0}">
								<tr class="lightBg" align="center">
									<td><bean:write name='assumptionDetailList_id'
										property='ttrnProject.projectid' /></td>
									<td style="width:200"><bean:write
										name='assumptionDetailList_id' property='description' /></td>
									<td><logic:equal name='assumptionDetailList_id'
										property='impact' value="0">
										 High
										</logic:equal> <logic:equal name='assumptionDetailList_id' property='impact'
										value="1">
										 Medium
										</logic:equal> <logic:equal name='assumptionDetailList_id' property='impact'
										value="2">
										Low
										</logic:equal></td>
									<td><fmt:parseDate
										value="${assumptionDetailList_id.raiseddate}" type="DATE"
										pattern="yyyy-MM-dd" var="parsedDate" /> <fmt:formatDate
										value="${parsedDate}" pattern="dd-MMM-yyyy" type="DATE"
										var="formatedDate" /><c:out value="${formatedDate}" /></td>
								</tr>
							</c:if>
						</logic:iterate>
					</table>
				</logic:notEmpty>
			</table>
			</td>
		</tr>
	</table>
	<!-- input color -->
	<script type="text/javascript">
 // initInputHighlightScript();
</script>


</html:form>
</div>
</BODY>
</html:html>
