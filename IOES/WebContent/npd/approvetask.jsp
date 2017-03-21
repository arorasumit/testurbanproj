<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<html:html>
<head>
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">

<script language="javascript" src="npd/js/utility.js"></script>
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>

<title>My To Do List - Approve Task</title>

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
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>


<script type="text/javascript">

	function ValidateFields()
	{
		 myform=document.getElementById('searchForm');

		 if(document.getElementById("attachment").value!=null&&document.getElementById("attachment").value!="")
			{
				if (isValidFileExtension(document.getElementById("attachment").value)==false) 
				{
					alert('Not A Valid File.Please Select a Valid type Of File');
					return false;                                 
				}
			}
		 
		 if(ValidateTextField(myform.taskapproveComments,'<%=request.getContextPath()%>',"Comments")==false)
			return false;

		 if(chkDescriptionLength(myform.taskapproveComments.value,'Approve Task Comments')==false)
			return false;
			
	}

	function saveTask()
	{
	
	
		if(ValidateFields()==false)
		  {
				return false;
		  }
		
		
	 	document.searchForm.action="<%=request.getContextPath()%>/MyToDoList.do?methodName=saveApproveTask";
	 	showLayer();
		document.searchForm.submit(); 
		
	}
	function validate()
	{
		document.body.click();
		if(document.searchForm.hiddenReturnFlag.value == "1")
		{
		window.close();
		if (window.opener && !window.opener.closed) {
//		window.opener.location.reload();
		myform=window.opener.document.getElementById('searchForm');
		myform.action="<%=request.getContextPath()%>/MyToDoList.do?methodName=myToDoList";
		myform.submit();
			} 	
		}
		
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
function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}


function onBodyLoad()
{
	document.body.click();
	showMessage();
}
</script>

</head>
<body onload="javascript:validate();onBodyLoad();">
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
<html:form action="/MyToDoList" method="post" enctype="multipart/form-data" styleId="searchForm">

	<input type="hidden" name="methodName" value="saveApproveTask">
	<html:hidden property="hiddenReturnFlag" />
	<html:hidden property="projectId"/>
	<html:hidden property="taskId"/>
	<html:hidden property="stageId"/>
	<html:hidden property="isTaskMandatory"/>
	
	
	<bean:define id="formBean" name="myToDoListFormBean"></bean:define>


	<table border="0" cellpadding="0" cellspacing="0" class="border"
		align="center" width="91%">
		<tr valign="middle" id="projectPlan">
			<td height="48" align="center" valign="bottom">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr>
					<td width="0" height="19"><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" height="19"
						width="26%">Approve Task</td>
					<td height="19" width="0"><img src="npd/Images/tabRight.gif"></td>
					<td align="left" height="19" width="772"></td>
					<td align="right" style="padding-right:10px;" width="166"
						height="19"></td>
				</tr>
			</table>
			</td>
		</tr>
	</table>

	<table cellSpacing="0" cellPadding="0" width="91%" border="0" id="tbl1"
		class="tabledata" style="display:block" align="center">
		<tr height="305">
			<td align="center">
			<table cellSpacing="0" cellPadding="0" width="91%" border="0"
				id="tbl1" class="tabledata" style="display:block" align="center">

				<tr>
					<td colspan="2" align="center">
					<div style="overflow:scroll;height:200px;width:100%" class="scroll">
					<table align="center" cellSpacing="1" cellPadding="4" border="0"
						class="scroll" id="tblRollList" width="100%">
						<tr>
							<td>Approve Comments</td>
							<td><html:textarea property="taskapproveComments" rows="6"
								cols="20" style="width:140px"  /></td>
						</tr>
						<tr>
							<td>Attachments</td>
							<td width="70%"><html:file  property="attachment" size="20"></html:file></td>
						</tr>
						<tr>
							<td colspan="1">
								<div class="buttonVSmall" onClick="javascript:saveTask();">Approve</div>
							</td>
						</tr>
					</table>
					</div>
					</td>
				</tr>

			</table>

			</td>
		</tr>
	</table>
</html:form>
</div>
</BODY>
</html:html>
