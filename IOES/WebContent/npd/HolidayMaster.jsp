<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.ibm.ioes.npd.hibernate.beans.HolidayMasterDto"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.ibm.ioes.npd.beans.HolidayMasterBean;"%>
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
function searchSubmit(flag)
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	
	var frmDt=document.getElementById("fromDate").value;
	var toDt=document.getElementById("toDate").value;
	
	if(ValidateTextField(document.getElementById("searchHolidayName"),'<%=request.getContextPath()%>',"Holiday Name")==false)
	{
		return false;
	}
	
	if(!compareTwoDates(frmDt,toDt))
	{			
		alert("From date should not be greater than To date");
		return false;
	}
	
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	document.searchForm.action="<%=request.getContextPath()%>/holidayMaster.do?method=displayHolidayList";
	showLayer();
	myform.submit();
}

function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	var frmDt=document.getElementById("fromDate").value;
	var toDt=document.getElementById("toDate").value;
	
	if(ValidateTextField(document.getElementById("searchHolidayName"),'<%=request.getContextPath()%>',"Holiday Name")==false)
	{
		return false;
	}
	
	if(!compareTwoDates(frmDt,toDt))
	{			
		alert("From date should not be greater than To date");
		return false;
	}
	
	myform.holidayID.value="";
	myform.holidayName.value="";
	myform.holidayDate.value="";
	myform.actionType.value="";
	
	myform.elements["pagingSorting.pageNumber"].value=val;
	document.searchForm.action="<%=request.getContextPath()%>/holidayMaster.do?method=displayHolidayList";
	showLayer();
	myform.submit();
}

function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	
	var frmDt=document.getElementById("fromDate").value;
	var toDt=document.getElementById("toDate").value;
	
	if(ValidateTextField(document.getElementById("searchHolidayName"),'<%=request.getContextPath()%>',"Holiday Name")==false)
	{
		return false;
	}
	
	if(!compareTwoDates(frmDt,toDt))
	{			
		alert("From date should not be greater than To date");
		return false;
	}
	
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

	document.searchForm.action="<%=request.getContextPath()%>/holidayMaster.do?method=displayHolidayList";
	showLayer();
	myform.submit();
}

function AddHoliday(actionType)
{
	myform=document.getElementById('searchForm');
	
	if(trim(myform.holidayName.value)=="")
	{
		alert("Please Enter Holiday Name");	
		return false;
	}
	
	if(trim(myform.holidayDate.value)=="")
	{
		alert("Please Enter Date of Holiday");	
		return false;
	}
	
	if(ValidateTextField(document.getElementById("holidayName"),'<%=request.getContextPath()%>',"Holiday Name")==false)
	{
		return false;
	}
	
	if(!checkholidayDate())
	{
		return false;
	}
	
	if(!checkholidayName())
	{
		return false;
	}
	
	myform.actionType.value=actionType;
	document.searchForm.action="<%=request.getContextPath()%>/holidayMaster.do?method=AddHoliday";
	showLayer();
	myform.submit();
}

function checkholidayDate()
{		
	var hdnHolidayID;
	if((document.getElementById('holidayID').value)=="")
	{
		hdnHolidayID=0
	}
	else
	{
		hdnHolidayID=document.getElementById('holidayID').value;
	}

	var appContextPath = '<%=request.getContextPath()%>';
	try
	{	
		var obj = document.getElementById('holidayDate');
		jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	   	var result;
		result = jsonrpc.processes.checkDuplicateHolidayDate(obj.value,hdnHolidayID);
		if(result>=1)
		{
			alert("User can't Enter Holiday With Same Date");
			return false;
		}
			
	}
	catch(e) {
		//alert(e);
		return false;
	}
	return true;
}	

function checkholidayName()
{		
	var hdnHolidayID;
	if((document.getElementById('holidayID').value)=="")
	{
		hdnHolidayID=0
	}
	else
	{
		hdnHolidayID=document.getElementById('holidayID').value;
	}
	var appContextPath = '<%=request.getContextPath()%>';
	try
	{	
		var obj = document.getElementById('holidayName');
		jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	   	var result;
		result = jsonrpc.processes.checkDuplicateHolidayName(obj.value,hdnHolidayID);
		if(result>=1)
		{
			alert("User can't Enter Holiday With Same Name");
			return false;
		}
			
	}
	catch(e) {
		//alert(e);
		return false;
	}
	return true;
}	

function updateHoliday(id,name,date)
{
	myform=document.getElementById('searchForm');
	myform.holidayID.value=id;
	myform.holidayName.value=name;
	myform.holidayDate.value=date;
	document.getElementById('AddBtn').style.display='none';
	document.getElementById('SaveBtn').style.display='inline';
	document.getElementById('CancalBtn').style.display='inline';
	document.getElementById('add').style.display='none';
	document.getElementById('edit').style.display='inline';
}

function loadInitialPage()
{
	myform.holidayID.value="";
	myform.holidayName.value="";
	myform.holidayDate.value="";
	myform.actionType.value="";
	document.searchForm.action="<%=request.getContextPath()%>/holidayMaster.do?method=displayHolidayList";
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
	<table width="80%" class="tabledata" align="center">
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
<br>
<html:form action="/holidayMaster" method="post"  styleId="searchForm">
	<logic:messagesPresent message="true">
		<table width="50%" align="center" id='messageBody'>
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
	<table width="80%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
			<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="borderTab">
				<tr>
					<td><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%" id="add" style="display:inline;">Add Holiday</td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%" id="edit" style="display:none;">Edit Holiday</td>
					<td><img src="npd/Images/tabRight.gif"></td>
					<td width="70%"><img src="npd/Images/zero.gif" width="15" height="1"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<table width="80%" border="2" class="tabledata" cellpadding="0" cellspacing="0" align="center">
		<tr class="tabledata">
			<td width="30%" align="center" style="font: bold;vertical-align: bottom">Holiday Name<font color="#990033">*</font></td>
			<td width="70%">
				<input type="text" id="holidayName" name="holidayName" maxlength="50" size="50">
			</td>
		</tr>
		<tr class="tabledata">
			<td width="30%" align="center" style="font: bold;vertical-align: bottom">Holiday Date<font color="#990033">*</font></td>
			<td width="70%">
				<html:text property="holidayDate" size="10" readonly="true" styleId="holidayDate" styleClass="tabledata"/>
				<font size="1">
					<img src="npd/Images/cal.gif" width="16" height="16" border="0" alt="Pick a date" onclick="scwShow(scwID('holidayDate'),event);">
				</font>
				<html:hidden property="holidayID"/>
				<html:hidden property="actionType"/>
			</td>
		</tr>
		<tr class="tabledata">
			<td width="100%" align="center" colspan="2">
				<span id="AddBtn" class="buttonLagre" 	onClick="AddHoliday(1);" style="display:inline;">Add Holiday</span>
				<span id="SaveBtn" class="buttonLagre" 	onClick="AddHoliday(2);" style="display:none;">Save Holiday</span>
				<span id="CancalBtn" class="buttonLagre" onClick="loadInitialPage();" style="display:none;">Cancal</span>
			</td>
		</tr>
	</table>
	<br>
	
	<table width="80%" border="0" cellpadding="0" cellspacing="0"
		class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr>
					<td><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">View
					Holiday</td>
					<td><img src="npd/Images/tabRight.gif"></td>
					<td width="70%"><img src="npd/Images/zero.gif" width="15"
						height="1"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<table width="80%" border="2" class="tabledata" cellpadding="0"
		cellspacing="0" align="center">
		<tr>
			<td width="70%">
			<table width="100%" border="0" class="tabledata" cellpadding="0"
				cellspacing="0">
				<tr align="center">
				<tr>
					<th width="30%" scope="row">
						<div align="center"><font color="#990033"></font></div>
					</th>
				</tr>
				<bean:define id="formBean" name="holidayMasterBean"></bean:define>
				<html:hidden name="formBean" property="pagingSorting.sortByColumn"/>
				<html:hidden name="formBean" property="pagingSorting.sortByOrder"/>
				<html:hidden name="formBean" property="pagingSorting.pageNumber"/>
				<input type="hidden" name="method" value="displayHolidayList"/>
		
				<table width="100%" border="1" class="tabledata" cellpadding="3" cellspacing="1" align="center">
					<tr>
						<td colspan="15" class="tabledata" >
		  					<bean:define id="pagingBean" name="formBean"></bean:define>
							<%  String  currPageNumber =String.valueOf(((HolidayMasterBean)pagingBean).getPagingSorting().getPageNumber());
								String  maxPageNumber =String.valueOf(((HolidayMasterBean)pagingBean).getPagingSorting().getMaxPageNumber());
							%>
							<jsp:include flush="true" page="html-jsp/commons/paging.jsp">
								<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
								<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
							</jsp:include>
						</td>
					</tr>
					<tr bgcolor="#FF9255">
						<th background="#FF9255">S.No.</th>
						<th background="#FF9255"><a href="#" onclick="sortSubmit('holidayName')">Holiday Name</th>
						<th background="#FF9255"><a href="#" onclick="sortSubmit('holidayDate')">Date of Holiday</th>
						<th background="#FF9255">Modify</th>
					</tr>
					<tr bgcolor="#FF9255" class="rptHeader">	
						<th background="#FF9255">&nbsp;</th>
						<th background="#FF9255"><html:text name="holidayMasterBean" property="searchHolidayName" maxlength="20"/></th>
						<th background="#FF9255">
							
								From Date<html:text property="fromDate" size="10" readonly="true" styleId="dateFrom" styleClass="tabledata"/>
								<img src="npd/Images/cal.gif" width="16" height="16" border="0" alt="Pick a date" onclick="scwShow(scwID('fromDate'),event);">
								To Date<html:text property="toDate" size="10" readonly="true" styleId="dateTo" styleClass="tabledata"/>
								<font size="1">
								<img src="npd/Images/cal.gif" width="16" height="16" border="0" alt="Pick a date" onclick="scwShow(scwID('toDate'),event);"></font>
							
						</th>
						<td background="#FF9255"><a href="#"><img onclick="searchSubmit();" src="npd/Images/search.gif" title="search" height="15"></td>
					</tr>
					<%int sno;
					sno=0;
					int newsno=((HolidayMasterBean)pagingBean).getPagingSorting().getSerialNoStart();
					%> 
					<logic:notEmpty name="holidayDetailList">
						<logic:iterate id="holiday_id" name="holidayDetailList" indexId="index1">
							
							
								<tr class="redBg" align="center">
									<td><%=newsno%></td>
									<%newsno=newsno+1; %>
									<td style="width:200"><bean:write name='holiday_id' property='holidayName' /></td>
									<td>
										<fmt:parseDate value="${holiday_id.holidayDate}" type="DATE" pattern="yyyy-MM-dd" var="parsedDate" /> 
										<fmt:formatDate value="${parsedDate}" pattern="dd-MMM-yyyy" type="DATE" var="formatedDate" /><c:out value="${formatedDate}" />
									</td>
									<td style="width:50">
									<bean:define id="holi_date" name='holiday_id' property='holidayDate'/>
									<%
										SimpleDateFormat in_sdf=new SimpleDateFormat("yyyy-MM-dd");
										SimpleDateFormat out_sdf=new SimpleDateFormat("dd-MMM-yyyy");										
										String newHoliDate=out_sdf.format(in_sdf.parse((String)holi_date));
									 %>
									<a href="#"
										onclick="updateHoliday(<bean:write name='holiday_id' property='holidayID'/>,'<bean:write name='holiday_id' property='holidayName'/>','<%=newHoliDate%>');">Edit</a></td>
								</tr>
							
							
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty  name="holidayDetailList">
						<tr class="redBg" align="center">
							<td colspan="4">No Records Found</td>
						</tr>
					</logic:empty>
				</table>
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
