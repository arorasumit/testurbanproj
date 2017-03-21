<!--[001]	 Anil Kumar		3-Oct-11	CSR00-05422     Use Ctrl + S for Save  -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.utilities.AppConstants"%>
<html:html>
<head>
<title>ChangeOrder Workflow</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
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
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>
<script language="javascript" src="npd/js/utility.js"></script>
<!--<script type="text/javascript" src="npd/js/inputColor.js"></script>-->
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<script type="text/javascript">
function goToHome()
{
	var myForm=document.getElementById('myform');
	//myForm.toExcel.value='false';
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}
function saveWorkflow()
{
	var errorString="";
	myform11=document.getElementById("myform");
	setFormBean(myform11);
	if(document.getElementById("npdCategoryId").value==-1)
	{
	errorString+="Please Select ChangeOrder Category"+"\n";
	}
	if(document.getElementById("workflowDesc").value==null||trim(document.getElementById("workflowDesc").value)=="")
	{
	errorString+="Please enter Workflow Description"+"\n";
	}
	if(errorString!="")
	{
	alert(errorString);
	return false;
	}
	else
	{
	if(ValidateTextField(document.getElementById("workflowDesc"),'<%=request.getContextPath()%>',"Description")==false)
	{
		return false;
	}
	if(verifyToReplaceExistingDraft())
	{
		showLayer();		
		document.masterProjectPlanBean.action="/IOES/addMasterPlan.do";		
		return true;	
	}
	return false;
	}
}
function verifyToReplaceExistingDraft()
{	
	//check if already a draft is present for the same npdcategory
	var appContextPath = '<%=request.getContextPath()%>';
	try
	{	

	jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	var result;
	

    	result = jsonrpc.processes.checkAnotherDraftWK(document.getElementById('npdCategoryId').value);
    
		if( result !=null)
		{
	//		var	ans=execScript('n = msgbox("Heelo","4132")', "vbscript");
//			alert(ans);
		//	alert(n);
//DialogResult a=MessageBox.Show("Are You Sure You Want To Close The Application", "Confirmation", MessageBoxButtons.YesNo, MessageBoxIcon.Asterisk) ;
//alert(a);
//			DialogResult result = MessageBox.Show("Excel Generated....Would you like to have glance of it.", "Logical Audit-Module", MessageBoxButtons.YesNo, MessageBoxIcon.Information); // HERE YOU CAN SEE
	/*		if (result == DialogResult.Yes)
			{
				alert('yes');
			}
			if (result == DialogResult.No)
			{
				alert('no');			
			}*/
			answer=confirm("Another Workflow for the same Stage is in draft mode . Clicking on 'OK' will replace it.");	
			if(answer)
			{
				return true
			}
			return false;
		}//if result
		return true;
		
		
	}
	catch(e) {
		//alert(e);
		return false;
	}
	
	
}



function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function onBodyLoad()
{
	myForm=document.getElementById('myform');
	myForm.action.value="<%="/"+(String)request.getAttribute(AppConstants.INITIAL_SERVLET_REQUESTED)%>";
}

//start[001]
document.onkeydown=checkKeyPress;
function checkKeyPress()
{
	if (event.ctrlKey && event.keyCode == 83) {						
				event.keyCode=4; 
				saveWorkflow();
				document.getElementById("saveWorkflow").click();							   				   						
   }   
}
//end[001]
</script>

</head>	
<body onload="onBodyLoad()">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
		
</div>
<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">
				<td><img src="gifs/top/home.gif" title="Home" onClick="goToHome('Home');"></img></td>
			</tr>
</table>
<div class="head"> <span>Add Workflow</span> </div>
<table width="98%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
		<!--<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Add
				Workflow</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>-->
		</td>
	</tr>
</table>

<html:form action="/addMasterPlan" method="post" styleId="myform">

	<table width="100%" border="2" class="tab2" cellpadding="0"
		cellspacing="0" align="left">

		<tr>

			<td width="60%">
			<table width="100%" border="0" class="tab2" cellpadding="0"
				cellspacing="0" align="center">
				<tr>
					<th width="30%" scope="row">
					<div align="left">ChangeOrder Category <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:select property="npdCategoryId"
						style="width:320px" styleClass="inputBorder1">
						<html:option value="-1">Select</html:option>
						<logic:notEmpty name="masterProjectPlanBean"
							property="npdCategoryList">
							<html:optionsCollection property="npdCategoryList"
								label="npdcatdesc" value="npdcatid"/>
						</logic:notEmpty>
					</html:select></td>
				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="left">WorkflowDesc <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text property="workflowDesc"
						maxlength="50" style="width:300px;" styleClass="inputBorder1"/></td>

				</tr>
				<html:hidden property="workflowId" />

				<tr align="center">
					<th colspan="2" scope="row">
					
						
							
							 <!--  <span
								id="saveButton" property="method" class="buttonLarge"
								onClick="return saveWorkflow();">Save</span>
								<bean:message key="submit.saveWorkflow" bundle="ButtonResources" />-->
								
																													
							<html:submit property="method" styleId="saveWorkflow" 
								onclick="return saveWorkflow();">
								<bean:message key="submit.saveWorkflow" bundle="ButtonResources" />
							</html:submit>

													
					
					</th>
				</tr>
				<tr align="center">
				<td colspan="2">
				<div class="error" align="center">
					<logic:present name="validation_errors">
						<logic:iterate id="error_row" name="validation_errors" scope="request">
								<font color="red"><bean:write name="error_row" /></font><BR>
						</logic:iterate>
					</logic:present>
				</div>
				</td>
				</tr>
				


			</table>
		</td>
		<td width="40%">
			<table width="100%" border="0" class="tab2" cellpadding="0"
				cellspacing="0" align="center">
				<tr>
					<logic:notEmpty name="masterProjectPlanBean"
						property="existingWorkflowList">
						<table width="100%" border="1" class="tab2" cellpadding="3"
							cellspacing="1" align="center">


							<tr class="grayBg">
								<th colspan="2">Existing Workflow</th>
							</tr>
							<logic:iterate id="existingWorkflowList_id"
								name="masterProjectPlanBean" property="existingWorkflowList"
								indexId="index1">
								<%String classType="";
									if(index1.intValue()%2 == 0)
									{
										classType="normal";
									}
									else
									{
										classType="lightBg";
									}
								 %>
								<tr class="<%=classType%>">

									<td><c:out
										value="${existingWorkflowList_id.npdCategory.npdcatdesc}--${existingWorkflowList_id.workflowname}" /></td>
									<td>
										<logic:equal name="existingWorkflowList_id" property="isactive" value="1">Finalized</logic:equal>
										<logic:equal name="existingWorkflowList_id" property="isactive" value="2">Draft</logic:equal>										
									</td>										
									
								</tr>

							</logic:iterate>

						</table>
					</logic:notEmpty>
				</tr>
			</table>
		</td>
		</tr>
	</table>
			<!-- input color --> <script type="text/javascript">
<!--
 // initInputHighlightScript();
//-->
</script> 
</html:form>
</div>
</BODY>


</html:html>
