<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
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
<script type="text/javascript" src="js/jsonrpc.js"></script>
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
function saveMeetingMOM()
{
	if(document.getElementById("attachment").value==null||document.getElementById("attachment").value=="")
	{
		alert("Please enter Attachment");
		return false;
	}
	if(document.getElementById("attachment").value!=null&&document.getElementById("attachment").value!="")
	{
		if (isValidFileExtension(document.getElementById("attachment").value)==false) 
		{
			return false;                                    
		}
	}
	//meetingIdCreated
	var ids=document.meetingBean.meetingIdCreated;
	if(ids==null)
	{
		alert("No Meeting Present.");
		return false;
	}
		var temp_chk=0;
		
		if(document.forms[0].meetingIdCreated.length!=null)
		{
			for(i=0;i<document.forms[0].meetingIdCreated.length;i++ )
			{
				if(document.forms[0].meetingIdCreated[i].checked==true)
				{
					temp_chk=temp_chk+1;
				}
			}
		}
		else
		{
			if(document.forms[0].meetingIdCreated.checked==true)
				{
					temp_chk=temp_chk+1;
				}
		}
		if(temp_chk==0)
		{
		 alert('No Meeting Selected. Please Select a Meeting.');
		return false;
		}
		return true;


	alert(ob.selectedIndex);
	if(ob.value==null || ob.value=='')
	{
		alert("Please Select a Meeting");
		return false;
	}
	showLayer();
	return true;
}

function trim(stringToTrim) 
{
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function assignValue(ref)
{
	document.getElementById("meetingIdCreated").value = ref;
}

function initializePage()
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
	if(myform.DocSearch!="")
	{
		if(ValidateTextField(myform.docSearch,'<%=request.getContextPath()%>',"Meeting Type")==false)
		{
			return false;
		}
	}
	if(CheckNumeric(myform.searchProjectId,"Project ID")==false)
	{
		return false;
	}
	if(myform.searchProjectName!="")
	{
		if(ValidateTextField(myform.searchProjectName,'<%=request.getContextPath()%>',"Search Field-Product Name")==false)
		{
			return false;
		}
	}
	document.meetingBean.action="<c:out value='${sessionScope.MenuContextPath}' />/addMOM.do?method=viewAddMom";
	showLayer();
	document.meetingBean.submit();			
}


</script>
<script type="text/javascript" src="js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
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
</head>
<body onload="javascript:document.body.click();">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait While Data Loads..</b></font>
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
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Adding
				MOM</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<html:form action="/addMOM" method="post" styleId="searchForm" enctype="multipart/form-data" onsubmit="return saveMeetingMOM();">
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
	<div class="error" align="center">
		<logic:present name="validation_errors">
			<logic:iterate id="error_row" name="validation_errors" scope="request">
					<font color="red"><bean:write name="error_row" /></font><BR>
			</logic:iterate>
		</logic:present>
	</div>
		<table cellSpacing="0" cellPadding="0" width="98%" border="0"
			id="tblRollList" class="tabledata" style="display:block"
			align="center">
			<tr>
				<th width="20%" scope="row">
				<div align="center">Attachment <font color="#990033">*</font></div>
				</th>
				<td width="80%"><html:file property="attachment" size="20"
					onkeydown='this.blur()'></html:file>&nbsp;&nbsp;&nbsp; <html:submit
					property="method">
					<bean:message key="submit.saveMOM" bundle="ButtonResources" />
				</html:submit>
				From Date
								<html:text property="fromDate" readonly="true" /> <img
									src="npd/Images/cal.gif" width="16" height="16" border="0"
									alt="Pick a date" onclick="scwShow(scwID('fromDate'),event);">
								To Date &nbsp; &nbsp; <html:text property="toDate" readonly="true" /> <img
									src="npd/Images/cal.gif" width="16" height="16" border="0"
									alt="Pick a date" onclick="scwShow(scwID('toDate'),event);">
				<img src="npd/Images/search.gif"
									onclick="initializePage();" />
				</td>
				
			</tr>
			<tr valign="bottom" align="center">
				<td align="right" colspan="7">
				<table cellSpacing="0" cellPadding="0" width="95%" border="0"
					align="center">
					<tr valign="bottom" align="center">
						<td align="center" colspan="7">
						<div style="overflow:scroll;height:370px;width:100%"
							class="scroll">
						<table cellSpacing="1" cellPadding="4" width="99%" border="0"
							align="center">
							<tr height="20" valign="middle">
								<td nowrap class="noScroll"></td>
								<td nowrap class="noScroll">MeetingType</td>
								<td nowrap class="noScroll">MeetingDate</td>
								<td nowrap class="noScroll">MeetingId</td>
								<td nowrap class="noScroll">Meeting Agenda</td>
								<td nowrap class="noScroll">Product Id</td>
								<td nowrap class="noScroll">Product Name</td>																
							</tr>
							<tr height="20" valign="middle" class="rptHeader">
								<td ></td>
								<td ><html:text property="docSearch" maxlength="10" size="10"/> </td>
								<td>&nbsp;</td>
								<td >&nbsp;</td>
								<td >&nbsp;</td>
								<td ><html:text property="searchProjectId" maxlength="10" size="10"/></td>									
								<td ><html:text property="searchProjectName" maxlength="20"/></td>																	
							</tr>
							<logic:empty property="meetingList" name="meetingBean">
							<tr align="Center">
								<td colspan="7">No Record Found</td>
							</tr>
							</logic:empty>
							<logic:notEmpty property="meetingList" name="meetingBean">
							<logic:iterate id="meetingBean_id" name="meetingBean"
								property="meetingList" indexId="index1">
								<c:if test="${index1%2==0}">
									<tr class="redBg">
										<td><html:radio property="meetingIdCreated"
											onclick="assignValue(this.value);"
											value="${meetingBean_id.meetingid}">
										</html:radio></td>
										<td><c:out
											value="${meetingBean_id.tmMeetings.meetingtype}" /></td>
										<td><fmt:parseDate value="${meetingBean_id.meetingdate}"
											type="DATE" pattern="yyyy-MM-dd" var="parsedDate" /> <fmt:formatDate
											value="${parsedDate}" pattern="dd-MMM-yyyy" type="DATE"
											var="formatedDate" /><c:out value="${formatedDate}" /></td>
										<td><bean:write name="meetingBean_id"
											property="meetingid" /></td>
										<td><bean:write name="meetingBean_id" property="subject" /></td>
										<td>&nbsp;
										<logic:present name="meetingBean_id" property="ttrnProduct">
										<bean:write name="meetingBean_id" property="ttrnProduct.projectid" />										
										</logic:present>
										</td>
										
										<td>&nbsp;
										<logic:present name="meetingBean_id" property="ttrnProduct">
										<bean:write name="meetingBean_id" property="ttrnProduct.projectName" />
										</logic:present>
										</td>																				
									</tr>
								</c:if>
								<c:if test="${index1%2!=0}">
									<tr class="alterRedBg">
										<td><html:radio property="meetingIdCreated"
											value="${meetingBean_id.meetingid}"
											onclick="assignValue(this.value);"></html:radio></td>
										<td><c:out
											value="${meetingBean_id.tmMeetings.meetingtype}" /></td>
										<td><fmt:parseDate value="${meetingBean_id.meetingdate}"
											type="DATE" pattern="yyyy-MM-dd" var="parsedDate" /> <fmt:formatDate
											value="${parsedDate}" pattern="dd-MMM-yyyy" type="DATE"
											var="formatedDate" /><c:out value="${formatedDate}" /></td>
										<td><bean:write name="meetingBean_id"
											property="meetingid" /></td>
										<td><bean:write name="meetingBean_id" property="subject" /></td>
										<td>&nbsp;
										<logic:present name="meetingBean_id" property="ttrnProduct">
										<bean:write name="meetingBean_id" property="ttrnProduct.projectid" />										
										</logic:present>
										</td>
										
										<td>&nbsp;
										<logic:present name="meetingBean_id" property="ttrnProduct">
										<bean:write name="meetingBean_id" property="ttrnProduct.projectName" />
										</logic:present>
										</td>	
									</tr>
								</c:if>
							</logic:iterate>
							</logic:notEmpty>

						</table>
						</div>
						</td>
					</tr>


				</table>
				</td>

			</tr>
		</table>




	<!-- input color -->
	<script type="text/javascript">
<!--
 // initInputHighlightScript();
//-->
</script>
</html:form>
</div>
</BODY>
</html:html>
