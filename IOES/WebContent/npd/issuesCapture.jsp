<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
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

function saveIssuesDetails()
{
	myform=document.getElementById('searchForm');
	if(document.getElementById("projectId").value==-1)
	{
		alert("Please select Project ID");
		return false;
	}
	
	if(document.getElementById("description").value==null||trim(document.getElementById("description").value)=="")
	{
		alert("Please enter Description");
		return false;
	}
		
	if(ValidateTextField(myform.description,'<%=request.getContextPath()%>',"Description")==false)
	{
		return false;
	}
	
	if(chkDescriptionLength(myform.description.value,"Description")==false)
	{
		return false;
	}
	
	if(document.getElementById("status").value==-1)
	{
		alert("Please select Status");
		return false;
	}
	
	if(document.getElementById("priority").value==-1)
	{
		alert("Please select Priority");
		return false;
	}
	
	if(document.getElementById("timeframe").value==null||trim(document.getElementById("timeframe").value)=="")
	{
		alert("Please enter Time Frame");
		return false;
	}
	
	if(CheckNumeric(myform.timeframe,"Time Frame for Resolution")==false)
	{
		return false;
	}
	
	if(document.getElementById("raisedon").value==null||trim(document.getElementById("raisedon").value)=="")
	{
		alert("Please enter Raised On Date");
		return false;
	}
	
	if(document.getElementById("resolutionOwner").value==-1)
	{
		alert("Please select ResolutionOwner");
		return false;
	}
	
	if(document.getElementById("plannedReslDate").value==null||trim(document.getElementById("plannedReslDate").value)=="")
	{
		alert("Please enter Planned Resolution Date");
		return false;
	}
	
	if(document.getElementById("actualReslDate").value==null||trim(document.getElementById("actualReslDate").value)=="")
	{
		alert("Please enter Actual Resolution Date");
		return false;
	}
	
	if(document.getElementById("reslutionSteps").value==null||trim(document.getElementById("reslutionSteps").value)=="")
	{
		alert("Please enter Proposed Resolution Steps");
		return false;
	}

	if(ValidateTextField(myform.reslutionSteps,'<%=request.getContextPath()%>',"Proposed Resolution Steps")==false)
	{
		return false;
	}

	if(chkDescriptionLength(myform.reslutionSteps.value,"Description")==false)
	{
		return false;
	}
	showLayer();
	return true;
}



function trim(stringToTrim) 
{
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function fillResoluationOwner(elem)
{
	var appContextPath = '<%=request.getContextPath()%>';
	try
	{	
		var obj = document.getElementById('resolutionOwner');
			jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	   		var result;
			result = jsonrpc.processes.getUsersOfResolution(elem.value);

			for(i=obj.options.length-1;i>=0 ;i--)
			{
			obj.options[i] = null;
			}
			obj.options[0] = new Option("Select", "-1");
			if( result !=null && result.list.length > 0)
			{
				for(i=0;i < result.list.length;i++)
				{
				var name = result.list[i].empname;
				var id=result.list[i].npdempid;
				obj.options[i+1] = new Option(name,id);
				
				}//for
			}//if result
	}
	catch(e) {
		//alert(e);
		return false;
	}
}

</script>

</head>
<body onload="fillResoluationOwner(document.getElementById('id_projectId'));document.body.click();">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
    <br /><br /><br />
</div>
<div class="error" align="center">
	<logic:present name="validation_errors">
		<logic:iterate id="error_row" name="validation_errors" scope="request">
				<font color="red"><bean:write name="error_row" /></font><BR>
		</logic:iterate>
	</logic:present>
</div>
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
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="newProduct">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Issues
				Capture</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<br>
<html:form action="/issueCapture" method="post" styleId="searchForm"
	enctype="multipart/form-data" onsubmit="return saveIssuesDetails();">

	<table width="70%" border="2" class="tabledata" cellpadding="0"
		cellspacing="0" align="center">

		<tr>
			<td width="40%">Project ID<font color="#990033">*</font></td>
			<td width="60%"><html:select property="projectId" styleId="id_projectId"
				style="width:140px" disabled="${issueBean.issueID>0}" onchange="fillResoluationOwner(this);">
				<html:option value="-1">Select</html:option>
				<logic:notEmpty name="issueBean" property="projectList">
				<logic:iterate id="issueBean_id" name="issueBean"
					property="projectList" indexId="index1">
					<html:option value="${issueBean_id.projectid}">
						<c:out
							value="${issueBean_id.projectid}--${issueBean_id.projectName}" />
					</html:option>
				</logic:iterate>
				</logic:notEmpty>

			</html:select>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
				href="issueCapture.do?method=initsearchIssues"> View Issues</a></td>
		</tr>
		<html:hidden property="fromDate" />
		<html:hidden property="toDate" />
		<html:hidden property="issueID" />
		<html:hidden property="projectId" />
		<html:hidden property="projectName" />

		<tr>
			<td width="40%">Description<font color="#990033">*</font></td>
			<td width="60%"><html:textarea property="description" rows="5"
				cols="20"></html:textarea>
		</tr>

		<tr>
			<td width="40%">Status<font color="#990033">*</font></td>
			<td width="300px"><html:select name="issueBean"
				property="status">
				<html:option value="-1">Select</html:option>
				<html:option value="1">Open</html:option>
				<html:option value="0">Closed</html:option>
			</html:select></td>
		</tr>

		<tr>
			<td width="40%">Priority<font color="#990033">*</font></td>
			<td width="60%"><html:select name="issueBean"
				property="priority">
				<html:option value="-1">Select</html:option>
				<html:option value="High">High</html:option>
				<html:option value="Medium">Medium</html:option>
				<html:option value="Low">Low</html:option>
			</html:select></td>
		</tr>
		<tr>
			<td width="40%">Time Frame for resolution<font color="#990033">*</font></td>
			<td width="60%"><html:text property="timeframe" maxlength="3"></html:text>in
			days</td>
		</tr>

		<tr>
			<td width="40%">Raised On</td>
			<td width="60%"><html:text property="raisedon" readonly="true" /></td>
		</tr>

		<tr>
			<td width="40%">Resolution Owner<font color="#990033">*</font></td>
			<td width="60%"><html:select property="resolutionOwner" styleId="resolutionOwner"
				style="width:140px">
				<html:option value="-1">Select</html:option>
				<logic:notEmpty name="issueBean" property="resolutionOwnerList">
					<html:optionsCollection property="resolutionOwnerList"
						label="empname" value="npdempid" />
				</logic:notEmpty>
			</html:select></td>
		</tr>

		<tr>
			<td width="40%">Planned Resolution Date<font color="#990033">*</font></td>
			<td width="60%"><html:text property="plannedReslDate"
				readonly="true" /><img src="npd/Images/cal.gif" width="16" height="16"
				border="0" alt="Pick a date"
				onclick="scwShow(scwID('plannedReslDate'),event);"></td>
		</tr>

		<tr>
			<td width="40%">Actual Resolution Date<font color="#990033">*</font></td>
			<td width="60%"><html:text property="actualReslDate"
				readonly="true" /><img src="npd/Images/cal.gif" width="16" height="16"
				border="0" alt="Pick a date"
				onclick="scwShow(scwID('actualReslDate'),event);"></td>
		</tr>

		<tr>
			<td width="40%">Proposed Resolution Steps<font color="#990033">*</font></td>
			<td width="60%"><html:textarea property="reslutionSteps"
				rows="5" cols="20"></html:textarea></td>
		</tr>

		<tr>
			<td colspan="2" align="center"><html:submit property="method">
				<bean:message key="submit.save" bundle="ButtonResources" />
			</html:submit></td>

		</tr>
	</table>
</html:form>
</div>
</body>
</html:html>

