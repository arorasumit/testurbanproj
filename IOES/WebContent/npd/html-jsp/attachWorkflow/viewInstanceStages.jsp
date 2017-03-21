<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="com.ibm.ioes.npd.utilities.Messages"%>
<%@page import="com.ibm.ioes.npd.beans.ProjectPlanInstanceBean"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">
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
<script language="JavaScript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/utility.js"></script>
<script type="text/javascript" src="npd/js/scw.js"></script>
<script type="text/javascript" src="npd/js/timepicker.js"></script>
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>
<script type="text/javascript" src="npd/js/inputColor.js"></script>
<title>ChangeOrder Workflow</title>
<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>

<script type="text/javascript">

function planActions(val)
{
	if(val=='draft')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='draft';
		showLayer();
		myForm.submit();
	}
	else if(val=='workflowHome')
	{
		var myPlanForm=document.getElementById('planForm');
		
		myform=document.createElement("form");
		document.body.appendChild(myform);
		myform.action="<%=request.getContextPath()%>/attachEditProjectPlan.do";
		
		getHiddenField('','');
		myform.appendChild(getHiddenField("method","attachedProjectPlanHome"));
		myform.appendChild(getHiddenField("projectId",myPlanForm.projectId.value));	
		myform.appendChild(getHiddenField("attachMode",myPlanForm.attachMode.value));	
		showLayer();
		myform.submit();
	}
	else if(val=='viewEditTasks')
	{
		var myForm=document.getElementById('planForm');
		myForm.method.value='viewEditTasks';
		showLayer();
		myForm.submit();
	}
}
function checkForm()
{
	if(ValidateTextField(document.getElementById("id_stageName_search"),'<%=request.getContextPath()%>',"Search Field-Stage Name")==false)
	{
		return false;
	}

	return true;
}
function searchSubmit()
{
	myform=document.getElementById('planForm');
	myform.elements["stagesPS.sortByColumn"].value="";
	myform.elements["stagesPS.sortByOrder"].value="";
	myform.elements["stagesPS.pageNumber"].value=1;

	if(checkForm()==false)
	{
		return ;
	}
	showLayer();
	myform.method.value='viewEditStages';	
	myform.submit();
}

function pagingSubmit(val)
{
	myform=document.getElementById('planForm');
	myform.elements["stagesPS.pageNumber"].value=val;
	myform.method.value='viewEditStages';
	if(checkForm()==false)
	{
		return ;
	}
	showLayer();
	myform.submit();
}
function sortSubmit(sortBy)
{
	myform=document.getElementById('planForm');
	myform.elements["stagesPS.pageNumber"].value=1;
	if(myform.elements["stagesPS.sortByColumn"].value==sortBy)
	{
		if(myform.elements["stagesPS.sortByOrder"].value=="decrement")
		{
			myform.elements["stagesPS.sortByOrder"].value="increment";
		}
		else
		{
			myform.elements["stagesPS.sortByOrder"].value="decrement";
		}
	}
	else
	{
		myform.elements["stagesPS.sortByColumn"].value=sortBy;
		myform.elements["stagesPS.sortByOrder"].value="decrement";
	}
	if(checkForm()==false)
	{
		return ;
	}
	showLayer();
	myform.method.value='viewEditStages';	
	myform.submit();
}


function fnRadioSelection()
{
	var selStage = document.getElementById('stage<bean:write name="projectPlanInstanceBean" property="selectedStageId"/>');
	if(selStage!=null)
	{
		selStage.checked=true;
	}
}

function fnOnBodyLoad()
{
	fnRadioSelection();
	document.body.click();
}
</script>

</head>
<body onload="javascript:fnOnBodyLoad()">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
		<br /><br /><br /><br /><br /><br /><br /><br />
	    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
	    <br /><br /><br />
</div>
<html:form action="/attachEditProjectPlan" styleId="planForm">
<bean:define id="formBean" name="projectPlanInstanceBean"></bean:define>
<input type="hidden" name="method"/>
<html:hidden property="projectId"/>
<html:hidden property="projectWorkflowId"/>
<html:hidden property="stagesPS.sortByColumn"/>
<html:hidden property="stagesPS.sortByOrder"/>
<html:hidden property="stagesPS.pageNumber"/>
<html:hidden property="attachMode"/>

<div class="head"> <span>Attach Project Plan</span> </div>
<table width="100%" border="0" cellpadding="0" cellspacing="0"
			class="border" align="center">
<tr valign="middle" id="newProduct">
	<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Attach Project Plan</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1">
				</td>
			</tr>
	</table>
</tr>

			<%String []colors=new String[]{"redBg","alterRedBg"}; %>
<tr>
	<td>
	<table width="100%" border="0" cellpadding="0" cellspacing="0"
			align="center">
	<tr>
	<td width="50%">
			<table width="80%" 	border="0" class="tabledata" cellpadding="3"
							cellspacing="1" align="center">
				<logic:present name="validation_errors" scope="request">
				<tr>
					<td colspan="3"><table class="tabledata" align="center"><tr><td>
						<div class="error" align="center">
							<logic:present name="validation_errors">
								<logic:iterate id="error_row" name="validation_errors" scope="request">
									<font color="red"><bean:write name="error_row" /></font><BR>
								</logic:iterate>
							</logic:present>
						</div></td></tr></table>
					</td>
				</tr>
				</logic:present>
				<tr  >
					<td colspan="3">
						<table width="100%" class="tabledata">
						<tr>
						<th  class="rptHeader" align="left" width="54">
							Stages
						</th>
						<td  align="right" colspan="2" width="319">
							<a href="#">
								<img onclick="searchSubmit();" src="npd/Images/search.gif" title="search" height="15"></a>
						</td>
						</tr>
						</table>
					</td>
					
				</tr>
				<tr >
					<td colspan="3">
						  <bean:define id="pagingBean" name="formBean"></bean:define>
							<%String  currPageNumber =String.valueOf(((ProjectPlanInstanceBean)formBean).getStagesPS().getPageNumber());
								String  maxPageNumber =String.valueOf(((ProjectPlanInstanceBean)formBean).getStagesPS().getMaxPageNumber());
							%>
							<jsp:include flush="true" page="./../commons/paging.jsp">
								<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
								<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
							</jsp:include>
					</td>
				</tr>	
				<tr class="rptHeader">
					<th width="28">
						<html:radio property="selectedStageId" value="-1"/>
					</th>
					<th width="34">S.No.</th>
					<th width="83%"><a href="#" onclick="sortSubmit('stageName')">Stage Name</a></th>
				</tr>
				<tr class="rptHeader">
					<th width="28">
						&nbsp;
					</th>
					<th width="34">&nbsp;</th>
					<th width="319" align="center"><html:text property="searchStageName" maxlength="20" styleId="id_stageName_search"/></th>
				</tr>
				<logic:empty name="formBean" property="stagesView">
					<tr>
						<td colspan="3" align="center">No Stages Present</td>
					</tr>
				</logic:empty>
				<logic:notEmpty name="formBean" property="stagesView">
				<%int stageSno= 1+(((ProjectPlanInstanceBean)formBean).getStagesPS().getPageNumber()-1)*
										((ProjectPlanInstanceBean)formBean).getStagesPS().getPageRecords();%>
				
				<logic:iterate id="stageRow"	name="formBean" property="stagesView" indexId="index_stage">
						<tr class="<%=colors[index_stage.intValue()%2]%>">
							<td width="28" align="center"">
							<input type="radio" name="selectedStageId" id='stage<bean:write name="stageRow" property="currentstageid"/>' value='<bean:write name="stageRow" property="currentstageid"/>'>
							</td>											
							<td width="34" align="center"><%=stageSno++%></td>						
							<td width="319" align="center"><bean:write name="stageRow"	property="stagename" /></td>
							</tr>
				</logic:iterate>
				</logic:notEmpty>
				
			</table>
		</td>
		<td width="50%">
			<table width="60%" border="0" cellpadding="3"
							cellspacing="1" align="center">
				<tr align="left">
					<td colspan="3">
						<table width="100%" border="0" cellpadding="3" class="tabledata"
										cellspacing="1" align="center">
							<tr align="left">						
								<th align="left" >
									Product Id: <bean:write name="formBean" property="project.projectid"/>
								</th>
								<th align="left">
									Product Name: <bean:write name="formBean" property="project.projectName"/>
								</th>	
							</tr>
						</table>
					</td>
				</tr>
				<tr align="left" col>
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr align="left">
					<td align="center" nowrap><span id="search" class="buttonLagre"
						onClick="planActions('draft');">Draft &amp; Close<span></td>
					<td align="center" nowrap><span id="search" class="buttonLagre"
						onClick="planActions('workflowHome');">Workflow Home<span></td>								
					<td align="center" nowrap><span id="search" class="buttonLagre"
						onClick="planActions('viewEditTasks');">View/Edit Tasks<span></td>	
				</tr>
				
				
				
		
			</table>
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
