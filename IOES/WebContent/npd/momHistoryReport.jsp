<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.ibm.ioes.npd.beans.MeetingBean;"%>
<html:html>
<head>
<title>ChangeOrder Workflow</title>
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">

<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>
<script type="text/javascript" src="js/jsonrpc.js"></script>
<script type="text/javascript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/scw.js"></script>
<script type="text/javascript" src="js/timepicker.js"></script>
<script type="text/javascript" src="js/inputColor.js"></script>
<script language="javascript" src="js/utility.js"></script>
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

function saveMomHistory()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	if((document.getElementById("subject").value==null||trim(document.getElementById("subject").value)=="")&&(document.getElementById("meetingId").value==null||trim(document.getElementById("meetingId").value)=="")&&(document.getElementById('toDate').value==""&&document.getElementById('fromDate').value==""))
	{
		alert("Please Enter Subject or Meeting ID or From And To Date");
		return false;
	}
	
	if(ValidateTextField(myform.subject,'<%=request.getContextPath()%>',"Subject")==false)
	{
		return false;
	}
		
	if(CheckNumeric(myform.meetingId,"Meeting ID")==false)
	{
		return false;
	}
	
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
	
	showLayer();
	document.meetingBean.action="<c:out value='${sessionScope.MenuContextPath}' />/manageMeetingsMOMHistory.do?method=View History";
}

function trim(stringToTrim) 
{
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function downloadFile(var1)
{
	
	var appContextPath = '<%=request.getContextPath()%>';
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var1 = jsonrpc.processes.encryptString(var1);	
	document.meetingBean.action="<c:out value='${sessionScope.MenuContextPath}' />/manageMeetingsMOMHistory.do?method=downloadFile&momId="+var1;
	document.meetingBean.submit();	
}

function downloadMeetingAttachment(var1,var2)
{
	var appContextPath = '<%=request.getContextPath()%>';
	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var1 = jsonrpc.processes.encryptString(var1);
	var2 = jsonrpc.processes.encryptString(var2);
	document.meetingBean.action="<c:out value='${sessionScope.MenuContextPath}' />/manageMeetingsMOMHistory.do?method=downloadFile&meetingId="+var1+"&attachmentId="+var2;
	document.meetingBean.submit();	
}


function searchSubmit()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	if((document.getElementById("subject").value==null||trim(document.getElementById("subject").value)=="")&&(document.getElementById("meetingId").value==null||trim(document.getElementById("meetingId").value)=="")&&(document.getElementById('toDate').value==""&&document.getElementById('fromDate').value==""))
	{
		alert("Please Enter Subject or Meeting ID or From And To Date");
		return false;
	}
	
	if(ValidateTextField(myform.subject,'<%=request.getContextPath()%>',"Subject")==false)
	{
		return false;
	}
		
	if(CheckNumeric(myform.meetingId,"Meeting ID")==false)
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
	myform=document.getElementById('meetingBean');
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;

	showLayer();
	myform.submit();
}

function pagingSubmit(val)
{
		myform=document.getElementById('searchForm');
	if((document.getElementById("subject").value==null||trim(document.getElementById("subject").value)=="")&&(document.getElementById("meetingId").value==null||trim(document.getElementById("meetingId").value)=="")&&(document.getElementById('toDate').value==""&&document.getElementById('fromDate').value==""))
	{
		alert("Please Enter Subject or Meeting ID or From And To Date");
		return false;
	}
	
	if(ValidateTextField(myform.subject,'<%=request.getContextPath()%>',"Subject")==false)
	{
		return false;
	}
		
	if(CheckNumeric(myform.meetingId,"Meeting ID")==false)
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
	myform=document.getElementById('meetingBean');
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.action="<c:out value='${sessionScope.MenuContextPath}' />/manageMeetingsMOMHistory.do?method=View History";
	showLayer();
	myform.submit();
}
function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	if((document.getElementById("subject").value==null||trim(document.getElementById("subject").value)=="")&&(document.getElementById("meetingId").value==null||trim(document.getElementById("meetingId").value)=="")&&(document.getElementById('toDate').value==""&&document.getElementById('fromDate').value==""))
	{
		alert("Please Enter Subject or Meeting ID or From And To Date");
		return false;
	}
	
	if(ValidateTextField(myform.subject,'<%=request.getContextPath()%>',"Subject")==false)
	{
		return false;
	}
		
	if(CheckNumeric(myform.meetingId,"Meeting ID")==false)
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
	myform=document.getElementById('meetingBean');
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
<table width="98%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">View
				MOM History</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<div class="error" align="center">
			<logic:present name="validation_errors">
			<table class="tabledata"><tr><td>
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>
				</td></tr></table>
			</logic:present>
		</div>
<html:form action="/manageMeetingsMOMHistory" method="post" styleId="searchForm" enctype="multipart/form-data" onsubmit="return saveMomHistory();">
	<table width="98%" border="2" class="tabledata" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td width="70%">
				<table width="100%" border="0" class="tabledata" cellpadding="0" cellspacing="0" align="center">
					<tr>
						<th width="30%" scope="row">
							<div align="left">Subject/Agenda <font color="#990033"></font></div>
						</th>
						<td width="70%"><html:text property="subject" maxlength="100" /></td>
					</tr>
					<bean:define id="formBean" name="meetingBean"></bean:define>
					<html:hidden name="formBean" property="pagingSorting.pageNumber" />
					<html:hidden name="formBean" property="pagingSorting.sortByColumn" />
					<html:hidden name="formBean" property="pagingSorting.sortByOrder" />
					<tr>
						<th width="30%" scope="row">
							<div align="left">Meeting ID <font color="#990033"></font></div>
						</th>
						<td width="70%"><html:text property="meetingId" style="width:120px" maxlength="10"/></td>
					</tr>

				<tr>
					<th width="30%" scope="row">
						<div align="left">From Date<font color="#990033"></font></div>
					</th>
					<td>
						<html:text property="fromDate" readonly="true" /> 
						<img src="npd/Images/cal.gif" width="16" height="16" border="0" alt="Pick a date" onclick="scwShow(scwID('fromDate'),event);">
					</td>
				</tr>

				<tr>
					<th width="30%" scope="row">
					<div align="left">To Date<font color="#990033"></font></div>
					</th>
					<td>
						<html:text property="toDate" readonly="true" /> 
						<img src="npd/Images/cal.gif" width="16" height="16" border="0" alt="Pick a date" onclick="scwShow(scwID('toDate'),event);">
					</td>
				</tr>

				<tr align="center">
					<th colspan="2" scope="row">
						<table width="50%" border="0">
							<tr>
								<td>
									<html:submit property="method">
										<bean:message key="submit.viewHistory" bundle="ButtonResources" />
									</html:submit>
								</td>
							</tr>
							<tr>
								<td></td>
								<td></td>
							</tr>
							<logic:messagesPresent message="true">
								<table width="50%" align="center">
									<tr>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td>
											<font color="red" face="Verdana" size="2"><html:messages id="message" message="true">
											<li><bean:write name="message" /></li>
											</html:messages></font>
										</td>
									</tr>
									<tr>
										<td>&nbsp;</td>
									</tr>
								</table>
							</logic:messagesPresent>
							<logic:notEmpty property="momList" name="meetingBean">
								<table width="50%" border="1" class="tabledata" cellpadding="3" cellspacing="1" align="center">
									<tr>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td></td>
										<td></td>
										<td></td>
									</tr>
									<tr>
										<td colspan="15" class="tabledata">
											<bean:define id="pagingBean" name="meetingBean"></bean:define> 
											<%
												String currPageNumber = String.valueOf(((MeetingBean) pagingBean).getPagingSorting().getPageNumber());
												String maxPageNumber = String.valueOf(((MeetingBean) pagingBean).getPagingSorting().getMaxPageNumber());
 											%> 
 											<jsp:include flush="true" page="html-jsp/commons/paging.jsp">
											<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
											<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
											</jsp:include>
										</td>
									</tr>
									<tr bgcolor="#FF9255">
										<th width="1" nowrap="nowrap">Meeting ID</th>
										<th width="6" nowrap="nowrap">Meeting Agenda</th>
										<th width="1%" nowrap="nowrap">MoM Versions</th>
										<th width="1%" nowrap="nowrap">Created Date</th>
										<th width="1%" nowrap="nowrap">Created By</th>
										<th width="1%" nowrap="nowrap">Attachment 1</th>
										<th width="1%" nowrap="nowrap">Attachment 2</th>
										<th width="1%" nowrap="nowrap">Attachment 3</th>
									</tr>
									<c:set var="meetingNo" value="0"></c:set>
									<c:set var="sno" value="0"></c:set>
									<logic:iterate id="meetingBean_id" name="meetingBean" property="momList" indexId="index1">
										<c:if test="${meetingBean_id.tmMeetings.meetingid!=meetingNo}">
											<tr class="alterRedBg">
												<td><bean:write name="meetingBean_id"
													property="tmMeetings.meetingid" /> <c:set var="sno"
													value="0"></c:set></td>
												<td><font color="black"><bean:write
													name="meetingBean_id" property="tmMeetings.subject" /></font></td>
												<td></td>
												<td></td>
												<td></td>
	
												<td><c:if
													test="${meetingBean_id.tmMeetings.filename!=null&&meetingBean_id.tmMeetings.filename!=''}">
													<a href="#"
														onclick="downloadMeetingAttachment(<bean:write name='meetingBean_id' property='tmMeetings.meetingid' />,1);"><c:out
														value="${meetingBean_id.tmMeetings.filename}" /> </a>
												</c:if></td>
												<td><c:if
													test="${meetingBean_id.tmMeetings.filename1!=null&&meetingBean_id.tmMeetings.filename1!=''}">
													<a href="#"
														onclick="downloadMeetingAttachment(<bean:write name='meetingBean_id' property='tmMeetings.meetingid' />,2);"><c:out
														value="${meetingBean_id.tmMeetings.filename1}" /> </a>
												</c:if></td>
												<td><c:if
													test="${meetingBean_id.tmMeetings.filename2!=null&&meetingBean_id.tmMeetings.filename2!=''}">
													<a href="#"
														onclick="downloadMeetingAttachment(<bean:write name='meetingBean_id' property='tmMeetings.meetingid' />,3);"><c:out
														value="${meetingBean_id.tmMeetings.filename2}" /> </a>
												</c:if></td>
	
											</tr>
											<c:set var="meetingNo" value="${meetingBean_id.tmMeetings.meetingid}"></c:set>
										</c:if>
										<tr class="redBg">
											<td></td>
											<td></td>
											<c:set var="sno" value="${sno+1}"></c:set>
											<td><a href="#"
											onclick="downloadFile('<bean:write name='meetingBean_id' property='meetingmomid' />');">
											<c:out value="Version ${sno}.0" /></a></td>
											<td>created on <fmt:parseDate
											value="${meetingBean_id.createddate}" type="DATE"
											pattern="yyyy-MM-dd" var="parsedDate" /> <fmt:formatDate
											value="${parsedDate}" pattern="dd-MMM-yyyy" type="DATE"
											var="formatedDate" /><c:out value="${formatedDate}" /></td>
											<td><bean:write name="meetingBean_id"
												property="createdby.empname" /></td>
											<td></td>
											<td></td>
											<td></td>
										</tr>
									</logic:iterate>
								
							</table></logic:notEmpty>
						</table>
					</tr>
				</table>
			</td>
		</tr>
	</table>
			<!-- input color --> <script type="text/javascript">
<!--
 // initInputHighlightScript();
//-->
</script> </html:form>
</div>
</BODY>
</html:html>
