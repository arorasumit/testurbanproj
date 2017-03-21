<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.ibm.ioes.npd.beans.UserNpdSpocs;"%>
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

function searchEmployeeRoleHistory()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	if((document.getElementById("employeeName").value==null||trim(document.getElementById("employeeName").value)==""))
	{
		alert("Please enter Employee Name");
		return false;
	}
	else
	{
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
		if(ValidateTextField(myform.employeeName,'<%=request.getContextPath()%>',"Employee Name")==false)
		{
			return false;
		}
		
		//showLayer();
		return true;
	}
}

function trim(stringToTrim) 
{
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function searchSubmit()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	if((document.getElementById("employeeName").value==null||trim(document.getElementById("employeeName").value)==""))
	{
		alert("Please enter Employee Name");
		return false;
	}
	else
	{
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
		if(ValidateTextField(myform.employeeName,'<%=request.getContextPath()%>',"Employee Name")==false)
		{
			return false;
		}
	}
	myform=document.getElementById('userNpdSpocs');
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	showLayer();
	myform.submit();
}

function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	if((document.getElementById("employeeName").value==null||trim(document.getElementById("employeeName").value)==""))
	{
		alert("Please enter Employee Name");
		return false;
	}
	else
	{
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
		if(ValidateTextField(myform.employeeName,'<%=request.getContextPath()%>',"Employee Name")==false)
		{
			return false;
		}
	}
	myform=document.getElementById('userNpdSpocs');
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.action="<c:out value='${sessionScope.MenuContextPath}' />/manageNpdSpocsHistory.do?method=search";
	showLayer();
	myform.submit();
}
function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	if((document.getElementById("employeeName").value==null||trim(document.getElementById("employeeName").value)==""))
	{
		alert("Please enter Employee Name");
		return false;
	}
	else
	{
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
		if(ValidateTextField(myform.employeeName,'<%=request.getContextPath()%>',"Employee Name")==false)
		{
			return false;
		}
	}
	myform=document.getElementById('userNpdSpocs');
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
	/*if(checkForm()==false)
	{
		return ;
	}*/
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

<br>
<html:form action="/manageNpdSpocsHistory" method="post" styleId="searchForm"
	onsubmit="return searchEmployeeRoleHistory();">
	<table width="80%" border="0" cellpadding="0" cellspacing="0"
		class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr>
					<td><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Employee
					Role History</td>
					<td><img src="npd/Images/tabRight.gif"></td>
					<td width="70%"><img src="npd/Images/zero.gif" width="15"
						height="1"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
<logic:present name="validation_errors">
<table width="80%" class="tabledata" align="center"><tr><td>
<div class="error" align="center">
			
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>

</div></td></tr></table>
</logic:present>
	<table width="80%" border="2" class="tabledata" cellpadding="0"
		cellspacing="0" align="center">
		<tr>

			<td width="70%">
			<table width="100%" border="0" class="tabledata" cellpadding="0"
				cellspacing="0">
				<tr align="center">
				<tr>
					<th width="30%" scope="row">
					<div align="center">Employee Name<font color="#990033"></font></div>
					</th>
					<td><html:text property="employeeName" maxlength="100"
						style="width:140px" /></td>
				</tr>

				<tr>
					<th width="30%" scope="row">
					<div align="center">From Date<font color="#990033"></font></div>
					</th>
					<td><html:text property="fromDate" readonly="true" /> <img
						src="npd/Images/cal.gif" width="16" height="16" border="0"
						alt="Pick a date" onclick="scwShow(scwID('fromDate'),event);"></td>
				</tr>

				<tr>
					<th width="30%" scope="row">
					<div align="center">To Date<font color="#990033"></font></div>
					</th>
					<td><html:text property="toDate" readonly="true" /> <img
						src="npd/Images/cal.gif" width="16" height="16" border="0"
						alt="Pick a date" onclick="scwShow(scwID('toDate'),event);"></td>
				</tr>

				<html:hidden property="fromDate" />
				<html:hidden property="toDate" />
				<html:hidden property="pagingSorting.pageNumber"/>
				<html:hidden property="pagingSorting.sortByColumn"/>
				<html:hidden property="pagingSorting.sortByOrder"/>
				<tr align="center">
					<th colspan="4" scope="row">
					<table width="50%" border="0">
						<tr>
							<td><html:submit property="method">
								<bean:message key="submit.search" bundle="ButtonResources" /> 
							</html:submit> &nbsp;<html:submit property="method">
								<bean:message key="submit.export" bundle="ButtonResources" />
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

				<logic:notEmpty name="userNpdSpocs" property="roleHistoryList">
					<table width="100%" border="1" class="tabledata" cellpadding="3"
						cellspacing="1" align="center">

						
				<tr>
							<td colspan="15" class="tabledata" >
								  <bean:define id="pagingBean" name="userNpdSpocs"></bean:define>
									<%String  currPageNumber =String.valueOf(((UserNpdSpocs)pagingBean).getPagingSorting().getPageNumber());
										String  maxPageNumber =String.valueOf(((UserNpdSpocs)pagingBean).getPagingSorting().getMaxPageNumber());
									%>
									<jsp:include flush="true" page="html-jsp/commons/paging.jsp">
										<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
										<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
									</jsp:include>
							</td>
						</tr>
						<tr bgcolor="#FF9255">
							<th background="#FF9255">Employee Name</th>
							<th background="#FF9255">Old Roles</th>
							<th background="#FF9255">New Roles</th>
							<th background="#FF9255">Old Level 1</th>
							<th background="#FF9255">New Level 1</th>
							<th background="#FF9255">Old Level 2</th>
							<th background="#FF9255">New Level 2</th>
							<th background="#FF9255">Created Date</th>
						</tr>
						<logic:iterate id="roleHistoryList_id" name="userNpdSpocs"
							property="roleHistoryList" indexId="index1">
							<c:if test="${index1%2==0}">
								<tr class="redBg">
								<td><bean:write name='roleHistoryList_id'
										property='employeeName' /></td>
									<td><bean:write name='roleHistoryList_id'
										property='oldRoles' /></td>
									<td><bean:write name='roleHistoryList_id'
										property='newRoles' /></td>
									<td><bean:write name='roleHistoryList_id'
										property='oldLevel1' /></td>
									<td><bean:write name='roleHistoryList_id'
										property='newLevel1' /></td>
									<td><bean:write name='roleHistoryList_id'
										property='oldLevel2' /></td>
									<td><bean:write name='roleHistoryList_id'
										property='newLevel2' /></td>
									<td><c:out value="${roleHistoryList_id.createdDate}" /></td>

								</tr>
							</c:if>
							<c:if test="${index1%2!=0}">
								<tr class="alterRedBg">
									<td><bean:write name='roleHistoryList_id'
										property='employeeName' /></td>
									<td><bean:write name='roleHistoryList_id'
										property='oldRoles' /></td>
									<td><bean:write name='roleHistoryList_id'
										property='newRoles' /></td>
									<td><bean:write name='roleHistoryList_id'
										property='oldLevel1' /></td>
									<td><bean:write name='roleHistoryList_id'
										property='newLevel1' /></td>
									<td><bean:write name='roleHistoryList_id'
										property='oldLevel2' /></td>
									<td><bean:write name='roleHistoryList_id'
										property='newLevel2' /></td>
									<td><c:out value="${roleHistoryList_id.createdDate}" /></td>
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
