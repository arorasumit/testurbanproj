<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.beans.UploadProductPlanBean"%>
<%@page import="com.ibm.ioes.npd.beans.ProjectPlanInstanceBean"%>
<html:html>
<head>
<title>ChangeOrder Workflow</title>
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">

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
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/utility.js"></script>
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>
<script type="text/javascript" src="npd/js/inputColor.js"></script>

<script type="text/javascript">
function planActions(val)
{
	if(val=='downloadMasterExcel')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='downloadPlanExcelForEdit';
		myForm.submit();
	}
	else if(val=='downloadTemplateExcel')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='downloadTemplateExcel';
		myForm.submit();
	}
	if(val=='uploadExcelPlan')
	{
		var myForm=document.getElementById('planForm');
		
		//validation
		
		if(document.getElementById("id_attachment").value==null || document.getElementById("id_attachment").value=="")
		{
			alert("Please Select Template file");
			return false;  
		}else
		{
				var data=document.getElementById("id_attachment").value;
				data = data.replace(/^\s|\s$/g, "");
			   if (data.match(/([^\/\\]+)\.((xls))$/i) )
			   {
				  //do nothing 
			   }
			   else
			   {
					alert("Selected Attachment is of Wrong Type");
				    return false ;				
			   }
		}
		
		myForm.method.value='uploadPlanExcel';
		myForm.elements["uploadProductPlanBean.actionName"].value='<%=UploadProductPlanBean.ACTION_NEW_LOAD%>';		
		showLayer();
		myForm.submit();
	}
	else if(val=='validateExcelPlan')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='uploadPlanExcel';
		myForm.elements["uploadProductPlanBean.actionName"].value='<%=UploadProductPlanBean.ACTION_VALIDATE%>';	
		showLayer();	
		myForm.submit();
	}
	else if(val=='replaceExcelPlan')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='uploadPlanExcel';
		myForm.elements["uploadProductPlanBean.actionName"].value='<%=UploadProductPlanBean.ACTION_REPLACE%>';	
		showLayer();	
		myForm.submit();
	}else if(val=='downloadErrorPlan')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='downloadErrorPlan';
		myForm.submit();
	}
	else if(val=='finalizeExcelPlan')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='uploadPlanExcel';
		myForm.elements["uploadProductPlanBean.actionName"].value='<%=UploadProductPlanBean.ACTION_FINALIZE%>';	
		showLayer();	
		myForm.submit();
	}
	else if(val=='viewEditTasks')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='viewEditTasks';
		showLayer();
		myForm.submit();
	}
}


</script>

</head>
<body >
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
		<br /><br /><br /><br /><br /><br /><br /><br />
	    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
	    <br /><br /><br />
</div>
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
					<td height="19" width="1"><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" height="19" width="29%">
						Product Plan Upload										
					</td>
					<td height="19" width="0"><img src="npd/Images/tabRight.gif"></td>
					<td align="left" height="19" width="772"><font size="1">
				<td ><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<table class="tabledata" align="center">
<tr>
	<td>
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


<html:form action="/attachEditProjectPlan" method="post" styleId="planForm" enctype="multipart/form-data">
	<bean:define id="formBean" name="projectPlanInstanceBean" type="com.ibm.ioes.npd.beans.ProjectPlanInstanceBean"/>


	<html:hidden property="projectId"/>	
	<html:hidden property="projectWorkflowId"/>	
	<html:hidden property="attachMode"/>
	<input type="hidden" name="method"/>	
	<html:hidden property="uploadProductPlanBean.actionName"/>
	
	<div style="overflow:scroll;height:100%;width:98%"
									class="scroll">
									
	<table width="95%" border="2" class="tabledata" cellpadding="0"
		cellspacing="0" align="center">
		<tr>
			<td>
				<table width="100%" border="0" align="center" >
								
					<tr valign="middle" id="newProduct">
						<td valign="bottom" width="100%" class="tabledata"  align="center">
							<bean:define id="projectBean" name="formBean" property="project"></bean:define>
							<%request.setAttribute("projectBean",projectBean); %>
							<jsp:include flush="true" page="./../commons/projectDetailHeader.jsp">
							</jsp:include>
						</td>
					</tr>		
				</table>
			</td>
		</tr>
		<html:messages id="message" message="true">
		<tr>
			<td align="center">
				<table width="80%" align="center"><tr><td align="left" style="color: red;text-align: left;" >
					<li><bean:write name="message"/></li>
				</td></tr></table>
			</td>
		</tr>
		</html:messages>
		<tr>

			<td width="100%">
			<table width="100%"  class="tabledata" border="0" cellpadding="3"
							cellspacing="1" align="center">
				<tbody id='newUpload' >
					<tr>
							<th scope="row" colspan="3" class="rptHeader">
								<div align="left">Upload Plan </div>
							</th>
					</tr>
					<tr class="rptHeader">
							<th scope="row" width="40%">
								Steps
							</th>
							<th scope="row" width="20%">Actions</th>
							<th >Status
							</th>
					</tr>
					<tr class="redBg">
						<th scope="row" width="40%">
							<div align="left"><b><u>Step-1</u></b>&nbsp;&nbsp; Download Template :  </div>
						</th>
						<td scope="row" width="20%">&nbsp;<span id="search" class="buttonsmall"
								onClick="planActions('downloadTemplateExcel');">Download<span></td>
						<td >&nbsp;
						</td>
					</tr>
					<tr class="alterRedBg">
						<th scope="row">
							<div align="left"><b><u>Step-2</u></b>&nbsp;&nbsp; Download Master (Optional) :  </div>
						</th>
						<td scope="row">&nbsp;<span id="search" class="buttonsmall"
								onClick="planActions('downloadMasterExcel');">Download<span></td>
						<td >&nbsp;
						</td>
					</tr>					
					<tr class="redBg">
						<th scope="row">
							<div align="left"><b><u>Step-3</u></b>&nbsp;&nbsp; Browse : <html:file property="uploadProductPlanBean.uploadFile" styleId="id_attachment"/> </div>
						</th>
						<td scope="row">&nbsp;<span id="search" class="buttonsmall"
								onClick="planActions('uploadExcelPlan');">Upload</span></td>
						<td >&nbsp;
							<% if(((ProjectPlanInstanceBean)formBean).getUploadProductPlanBean().getProgress()
										>=UploadProductPlanBean.PROGRESS_UPLOAD_SUCCESS)
										{%>
								<b>Uploaded File :<bean:write name="formBean" property="uploadProductPlanBean.uploadFileName"/></b>
										<%} %>
						</td>
					</tr>
					<tr class="alterRedBg">
						<th  scope="row">
							<div align="left"><b><u>Step-4</u></b>&nbsp;&nbsp; Validate</div>
						</th>
						<td >&nbsp;
							<% int progress=((ProjectPlanInstanceBean)formBean).getUploadProductPlanBean().getProgress();
							if(progress==UploadProductPlanBean.PROGRESS_UPLOAD_SUCCESS )
										{%>
								<span id="search" class="buttonsmall" 
									onClick="planActions('validateExcelPlan');">Validate</span>
							<%} %>
						</td>
						<td >&nbsp;
							<% if(((ProjectPlanInstanceBean)formBean).getUploadProductPlanBean().getProgress()
										>=UploadProductPlanBean.PROGRESS_VALIDATE_SUCCESS)
										{%>
								<b>No Errors.</b>
							<%} %>
							<% if(((ProjectPlanInstanceBean)formBean).getUploadProductPlanBean().getProgress()
										==UploadProductPlanBean.PROGRESS_VALIDATE_FAILURE)
										{%>
								<a href="#" onclick="planActions('downloadErrorPlan');"><b>Download Error Log</b></a>
							<%} %>
						
						</td>
					</tr>
					<tr class="redBg">
						<th scope="row">
							<div align="left"><b><u>Step-5</u></b>&nbsp;&nbsp; Replace Existing Plan</div>
						</th>
						<td >&nbsp;
							<% if(((ProjectPlanInstanceBean)formBean).getUploadProductPlanBean().getProgress()
										==UploadProductPlanBean.PROGRESS_VALIDATE_SUCCESS)
										{%>
							<span id="search" class="buttonsmall"
								onClick="planActions('replaceExcelPlan');">Replace</span>
							<%} %>
						</td>
						<td >&nbsp;
							<% if(((ProjectPlanInstanceBean)formBean).getUploadProductPlanBean().getProgress()
										>=UploadProductPlanBean.PROGRESS_REPLACE_SUCCESS)
										{%>
								<b>Plan Replaced.</b>
							<%} %>
						</td>
					</tr>
					<tr class="alterRedBg">
						<th scope="row">
							<div align="left"><b><u>Step-6</u></b>&nbsp;&nbsp; Finalize Plan</div>
						</th>
						<td >&nbsp;
							<% 
							int canFinalize=-1;
							String finalizeMessage="";
							if(((ProjectPlanInstanceBean)formBean).getUploadProductPlanBean().getProgress()
										>=UploadProductPlanBean.PROGRESS_REPLACE_SUCCESS)
							{	canFinalize=1;
								if("".equals(formBean.getTotalTasks()))
								{
									canFinalize=0;
									finalizeMessage="Cannot Finalize. Please Refer Checklist";
								}
								else
								{
									if(formBean.getFirstTask()==null)
									{
										canFinalize=0;
										finalizeMessage="Cannot Finalize. Please Refer Checklist";
									}
									if(formBean.getLastTask()==null)
									{
										canFinalize=0;
										finalizeMessage="Cannot Finalize. Please Refer Checklist";
									}
									if(formBean.getNoPrevTaskList().size()>0)
									{
										canFinalize=0;
										finalizeMessage="Cannot Finalize. Please Refer Checklist";
									}
									if(formBean.getNoStakeUserList().size()>0)
									{
										canFinalize=0;
										finalizeMessage="Cannot Finalize. Please Refer Checklist";
									}
								}
								if(canFinalize==1)
								{%>
							<span id="search" class="buttonSmall"
								onClick="planActions('finalizeExcelPlan');">Finalize<span>
							<%}} %>
						</td>
						<td >&nbsp;<font color="red" style="font-style: italic"><b><%=finalizeMessage %></b></font></td>
					</tr>
				</tbody>
				
				
			</table>
			</td>
		</tr>

	</table>
	<%if(((ProjectPlanInstanceBean)formBean).getUploadProductPlanBean().getProgress()
										==UploadProductPlanBean.PROGRESS_REPLACE_SUCCESS) 
		{%>
			<table width="40%" border="0" class="tabledata" cellpadding="3"
							cellspacing="1" align="center" style="display: block;">
				<tr>
					<td class="rptHeader" width="100%" align="left" colspan="2">
						Checklist
						<html:hidden property="totalTasks"/>
					</td>
				</tr>
				<tr class="redBg">
					<td align="center" nowrap>First Task :</td>		
					<td align="center" nowrap>
						<logic:present name="formBean" property="firstTask">
							YES (<bean:write name="formBean" property="firstTask.taskname"/>)
							<input type="hidden" name="firstTaskDefined" value="YES"/>
						</logic:present>
						<logic:notPresent name="formBean" property="firstTask">
							NOT DEFINED
							<input type="hidden" name="firstTaskDefined" value="NO"/>						
						</logic:notPresent>					
					</td>		
				</tr>						
				<tr class="alterRedBg">
					<td align="center" nowrap>Last Task :</td>		
					<td align="center" nowrap>
						<logic:present name="formBean" property="lastTask">
							YES (<bean:write name="formBean" property="lastTask.taskname"/>)
							<input type="hidden" name="lastTaskDefined" value="YES"/>						
						</logic:present>
						<logic:notPresent name="formBean" property="lastTask">
							NOT DEFINED
							<input type="hidden" name="lastTaskDefined" value="NO"/>												
						</logic:notPresent>					
					</td>		
				</tr>		
				<tr class="redBg">
					<td align="center" nowrap>Previous Tasks of all Tasks Defined :</td>		
					<td align="center" nowrap>
						<logic:empty name="formBean" property="noPrevTaskList">
							<input type="hidden" name="allPrevTaskDefined" value="YES"/>											
							YES
						</logic:empty>
						<logic:notEmpty name="formBean" property="noPrevTaskList">
							<input type="hidden" name="allPrevTaskDefined" value="NO"/>																
							NO
						</logic:notEmpty>
					</td>		
				</tr>											
				<tr class="redBg">
					<td align="center" nowrap>All Stake Holder Users Defined :</td>		
					<td align="center" nowrap>
						<logic:empty name="formBean" property="noStakeUserList">
							<input type="hidden" name="allStakeUsersDefined" value="YES"/>											
							YES
						</logic:empty>
						<logic:notEmpty name="formBean" property="noStakeUserList">
							<input type="hidden" name="allStakeUsersDefined" value="NO"/>																
							NO
						</logic:notEmpty>
					</td>		
				</tr>							
			</table>
		<%} %>
										
	<table width="80%" border="0" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td colspan="2" align="center" width="50%">
				<div class="buttonLagre" onClick="javascript:planActions('viewEditTasks');">Workflow Home</div>
			</td>
		</tr>
	</table>
	</div>
</html:form>
</div>
</BODY>


</html:html>
