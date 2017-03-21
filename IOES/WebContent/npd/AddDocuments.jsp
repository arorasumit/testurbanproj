<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.ibm.ioes.npd.beans.RepositoryUploadBean"%>
<%@page import="com.ibm.ioes.npd.utilities.Messages"%>
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
function enableStage(flags)
{
	document.searchForm.stageFilter.disabled=flags;
	if(flags==true)
	{
		document.getElementById("taskDisplay").style.display='none';
		document.getElementById("taskDetails").style.display='none';
	}
	else
	{
		document.getElementById("taskDisplay").style.display='block';
		document.getElementById("taskDetails").style.display='block';
	}
}

function ViewTask()
{
	stageID=document.searchForm.stageFilter.value;
	projectId=document.searchForm.selectedProjID.value;
	if(stageID=="-1")
	{
		alert("Please Select a Stage");
		return false;
	}
	else
	{
		var appContextPath = '<%=request.getContextPath()%>';
		jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");		
		document.forms[0].stageID.value=stageID;
		
		stageID = jsonrpc.processes.encryptString(stageID);
		projectId=jsonrpc.processes.encryptString(projectId);
		windowURL="<%=request.getContextPath()%>/RepositoryUpload.do?method=getViewList&stageID="+stageID+"&projectId="+projectId;
//		windowDefault="resizable=yes;center:yes;resizable:yes;status:no;scrollbars=yes";
		windowDefault='<%=Messages.getMessageValue("windowDefault")%>';
		var newwin=window.open(windowURL,"abc",windowDefault);
	}
}

function setSelectedTask(taskName,val)
{
    document.forms[0].selectedTaskName.value=taskName;
    document.forms[0].selectedTaskID.value=val;
} 

function saveTask()
{
	myform=document.getElementById('searchForm');
	if(document.getElementById("docName").value=="")
	{
		alert("Please Enter Document Name");
		return false;
	}
	
	if(ValidateTextField(myform.docName,'<%=request.getContextPath()%>',"Document Name")==false)
	{
		return false;
	}

	if(chkDescriptionLength(myform.docName.value,"Document name")==false)
	{
		return false;
	}
	
	if(document.getElementById("attachment").value==null||document.getElementById("attachment").value=="")
	{
		alert("Please Select An Attachment");
		return false;
	}
	
	if(document.getElementById("attachment").value!=null&&document.getElementById("attachment").value!="")
	{
		if (isValidFileExtension(document.getElementById("attachment").value)==false) 
		{
			return false;                                    
		}
	}
	
	if(document.getElementById("taskDisplay").style.display=='block')
	{
		stageID=document.searchForm.stageFilter.value;
		if(stageID=="-1")
		{
			alert("Please Select a Stage");
			return false;
		}
		if(document.getElementById("selectedTaskName").value=="")
		{
			alert("Please Select a Task");
			return false;
		}
	}

	document.searchForm.action="<%=request.getContextPath()%>/RepositoryUpload.do?method=saveDocument";
	showLayer();
	document.searchForm.submit(); 
}

function showMessage()
{
	var msg='';
	<html:messages id="message" message="true"  >
		msg=msg+'\n'+'<bean:write name="message" />';
	</html:messages>
	if(msg!='') {
	alert(msg);
	}

}

function onBodyLoad()
{
	showMessage();
}

function trim(stringToTrim) 
{
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function clearTask()
{
	myform=document.getElementById('searchForm');
	myform.selectedTaskName.value="";
}
</script>

</head>
<body onload="javascript:document.body.click();javascript:onBodyLoad();">
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
<html:form action="/RepositoryUpload" styleId="searchForm" method="post" enctype="multipart/form-data">
	<table width="80%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="borderTab">
					<tr>
						<td><img src="npd/Images/tabLeft.gif"></td>
						<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Upload Documents</td>
						<td><img src="npd/Images/tabRight.gif"></td>
						<td width="70%"><img src="npd/Images/zero.gif" width="15" height="1"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table width="80%" border="2" class="tabledata" cellpadding="0" cellspacing="0" align="center">
		<tr class="redBg">
			<td colspan="2" align="center" style="font-size:11px; font: bold">Please Select Either Project or Stage to Upload a Document</td>
		</tr>
		<tr>
			<td width="39%" align="Center" valign="bottom">
				 <Strong>Project ID</Strong>
			</td>
			<td width="61%">
				<Strong><bean:write property="selectedProjID" name="repositoryUploadBean"/></Strong>
			</td>
		</tr>
		<tr>	
			<td width="39%" align="Center" valign="bottom">
				<html:radio property="docType" value="1" onclick="javascript:enableStage(true);"> <Strong>Select Project</strong></html:radio>
			</td>
			<td width="61%">
				<html:text property="projectName" name="repositoryUploadBean" readonly="true" size="50"></html:text>
				<html:hidden property="selectedProjID"/>
				<html:hidden property="projWorkFlowID"/>
			</td>		
		</tr>
		<tr>
			<td width="39%" align="center">
				<html:radio property="docType" value="0" onclick="javascript:enableStage(false);"><Strong>Select Stage</Strong></html:radio>
			</td>
			<td width="61%">	
				<html:select property="stageFilter" style="width:140px" disabled="true" onchange="clearTask();">
					<html:option value="-1">Select Stage</html:option>
					<logic:notEmpty name="repositoryUploadBean" property="listStage">
						<html:optionsCollection property="listStage" label="stageName" value="stageID"/>
					</logic:notEmpty>
				</html:select>
				<html:hidden property="stageID"/>
				<a href="#" onclick="ViewTask();" style="display:none;" id="taskDisplay"><Strong>View\Select Tasks</Strong></a>
			</td>
		</tr>
		<tr style="display:none" id="taskDetails">
			<td align="center" style="size: 15" valign="bottom"><Strong>Task Name</Strong></td>
			<td align="Left" style="size: 15" >
				<html:hidden property="selectedTaskID"/>
				<html:text property="selectedTaskName" readonly="true" size="50"></html:text>
			</td>
		</tr>
		<tr>
			<td align="center"><Strong>Name of Document</Strong></td>
			<td><html:textarea property="docName" rows="6" cols="20" style="width:140px"/></td>
		</tr>
		<tr>
			<td align="center"><Strong>Attachments</Strong></td>
			<td width="70%"><html:file  property="attachment" size="20" onkeydown='this.blur()'></html:file></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<div class="buttonVSmall" onClick="javascript:saveTask();">Save</div>
			</td>
		</tr>
	</table>
</html:form>
</div>
</BODY>
</html:html>
