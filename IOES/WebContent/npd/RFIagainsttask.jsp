<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>


<%@page import="com.ibm.ioes.npd.utilities.Messages"%>
<html:html>
<head>
<link rel="stylesheet" type="text/css" href="gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">
<script type="text/javascript" src="js/scw.js"></script>
<script language="javascript" src="js/utility.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>
<title>My To Do List - RFI</title>

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

<%
	//TODO Change Made BY Sumit on 09-Mar-2010  Pending RFI Should Be According to role
 %>
<script type="text/javascript">

	function saveTask()
	{
		myform=document.getElementById('searchForm');
		if(document.getElementById("employeeId").value=="-1")
		{
			alert('Please Select Stakeholder First!')
			return false;
		}
		
		if(document.getElementById("rolemapped").value=="-1")
		{
			alert('Please Select Stakeholder Role!')
			return false;
		}

		if(document.getElementById("attachment").value!=null&&document.getElementById("attachment").value!="")
		{
			if (isValidFileExtension(document.getElementById("attachment").value)==false) 
			{
				return false;                                 
			}
		}
		
		 if(ValidateTextField(myform.taskapproveComments,'<%=request.getContextPath()%>','RFI Comments')==false)
			return false;

		 if(chkDescriptionLength(myform.taskapproveComments.value,'RFI Comments')==false)
			return false;
		
		
	 	document.searchForm.action="<%=request.getContextPath()%>/MyToDoList.do?methodName=saveApproveRFI";
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

function fillEmployeeBasedOnRole(elem)
{
	var appContextPath = '<%=request.getContextPath()%>';
	try
	{	
		var obj = document.getElementById('rolemapped');
			jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	   		var result;
			result = jsonrpc.processes.getRoleMappedWithEmployee(elem.value);

			for(i=obj.options.length-1;i>=0 ;i--)
			{
			obj.options[i] = null;
			}
			obj.options[0] = new Option("Select", "-1");
			if( result !=null && result.list.length > 0)
			{
				for(i=0;i < result.list.length;i++)
				{
				var name = result.list[i].rolename;
				var id=result.list[i].roleid;
				obj.options[i+1] = new Option(name,id);
				
				}//for
			}//if result
	}
	catch(e) {
		//alert(e);
		return false;
	}
}



function onBodyLoad()
{
	showMessage();
}
	

</script>

</head>
<body onload="javascript:validate();onBodyLoad();document.body.click();">
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
<html:form action="/MyToDoList" method="post"
	enctype="multipart/form-data" styleId="searchForm">

	<input type="hidden" name="methodName" value="saveApproveTask">
	<html:hidden property="hiddenReturnFlag" />
	<html:hidden property="projectId" />
	<html:hidden property="taskId" />
	<html:hidden property="stageId" />
	<html:hidden property="searchprojectid" />

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
						width="26%">R F I</td>
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
		<tr height="470">
			<td align="center">
			<table cellSpacing="0" cellPadding="0" width="91%" border="0"
				id="tbl1" class="tabledata" style="display:block" align="center">

				<tr height="238">
					<td colspan="2" align="center">
					<div style="overflow:scroll;height:270px;width:100%" class="scroll">
					<table align="center" cellSpacing="1" cellPadding="4" border="0"
						class="scroll" id="tblRollList" width="100%">
						<tr>
							<td>RFI Comments</td>
							<td><html:textarea property="taskapproveComments" rows="6"
								cols="20" style="width:140px" /></td>
						</tr>
						<tr>
							<td> Select Stakeholder</td>
							<td align="left"><html:select property="employeeId"
								style="width:250px" onchange="fillEmployeeBasedOnRole(this)">
								<html:option value="-1">Select</html:option>
								<logic:notEmpty name="formBean" property="lstEmployee">
									<html:optionsCollection property="lstEmployee" label="empname"
										value="npdempid" />
								</logic:notEmpty>
							</html:select>
							</td>
						</tr>
						<tr>
							<td> Select Role</td>
							<td width="60%">
								<html:select property="rolemapped" styleId="rolemapped" style="width:140px">
								<html:option value="-1">Select</html:option>
									<logic:notEmpty name="formBean" property="roleList">
										<html:optionsCollection  property="roleList"	label="rolename" value="roleid" />
									</logic:notEmpty>
								</html:select>
							</td>
						</tr>

						<tr>
							<td>Attachments</td>
							<td width="70%"><html:file property="attachment" size="20"></html:file></td>
						</tr>
						<tr>
							<td colspan="1">
							<div class="buttonVSmall" onClick="javascript:saveTask();">Save</div>
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
