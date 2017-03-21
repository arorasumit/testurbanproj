<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.utilities.AppConstants"%>
<!------------------------------------------------------------------->
<html:html>
<head>
<title>ChangeOrder Workflow</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" href="/theme/Master.css" type="text/css">
<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>
<!--<script type="text/javascript" src="npd/js/inputColor.js"></script>-->
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
<!------------------------------------------------------------------------------------->
<script type="text/javascript">
function goToHome()
{
	var myForm=document.getElementById('myform');
	//myForm.toExcel.value='false';
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}
function submitHistory()
{
	var errorString="";
	if(document.getElementById("npdCategoryId").value==-1)
	{
	errorString+="Please Select Change Order Category"+"\n";
	}
	if(errorString!="")
	{
	alert(errorString);
	return false;
	}
	else
	{
	showLayer();
	return true;
	}
}

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function show(tbl,btn,sno)
{
counter=0;
if (btn.value=="-")
	{
		while(true)
		{
			section =""+tbl+sno+"."+counter;
			if(document.getElementById(section)!=null)
			{
				counter++;
				document.getElementById(section).style.display="none";
			}
			else
			{
			break;
			}
		}
		btn.value="+";
	}
	else
	{
	while(true)
		{
			
			section =""+tbl+sno+"."+counter;
			if(document.getElementById(section)!=null)
			{
				counter++;
				document.getElementById(section).style.display="";
			}
			else
			{
			break;
			}
		}
		
		btn.value="-";
	}
}
function onBodyLoad()
{
	myForm=document.getElementById('myform');
	<% String str=(String)request.getAttribute(AppConstants.INITIAL_SERVLET_REQUESTED);
		//System.err.println("-->"+str);
		
	%>
	myForm.action.value="<%="/"+(String)request.getAttribute(AppConstants.INITIAL_SERVLET_REQUESTED)%>";
		
}
function onViewBackButton()
{
	window.close();
}
</script>

</head>
<!------------------------------------------------------------------------------------------------------->
<body onload="onBodyLoad()">
<div id="shadow" class="opaqueLayer"> 
	
</div>

<div class="head"> <span>Master Project Workflow History </span> </div>

<span id="viewBackButton" class="buttonLarge" onClick="onViewBackButton()">Back</span>

<table width="98%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
		
		</td>
	</tr>
</table>

<table class="tab2" align="center">
<tr><td>
	<div class="error" align="center">
			<logic:present name="validation_errors">
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>
			</logic:present>
	</div>
</td></tr>
</table>
<!--------------------------------------------------------------------------------------------->
<html:form action="/masterPlanVersion" method="post" styleId="myform" onsubmit="return submitHistory();">
	<div class="scroll">
	<table width="100%"  border="0" class="tab2" cellpadding="0" cellspacing="0" align="left">
		<tr>
			<!--<input type="hidden" id="HomeInvisible" value="<%=request.getParameter("spPage") %>"/>-->
			<td width="70%">	
				<table width="100%"  border="0" class="tab2" cellpadding="0" cellspacing="0" align="center">
					<tr>
						<th width="30%" scope="row">
							<div align="center">ChangeOrder Category <font color="#990033">*</font></div>
						</th>
						<td width="70%">
							<html:select property="npdCategoryId" style="width:320px" styleClass="inputBorder1">
							<html:option value="-1">Select</html:option>
							<logic:notEmpty name="masterProjectPlanBean" property="npdCategoryList">
								<html:optionsCollection property="npdCategoryList" label="npdcatdesc" value="npdcatid" />
							</logic:notEmpty>
							</html:select>
						</td>
					</tr>										
					<html:hidden property="workflowId" />					
					<tr align="center">
						<th colspan="2" scope="row">
							<table width="50%" border="0">
								<tr>
									<td>
										<!--<span id="saveButton" property="method" class="buttonLarge">View History</span>-->													
										<html:submit property="method">
											<bean:message key="submit.viewHistory" bundle="ButtonResources" />
										</html:submit>
						  			</td>
								</tr>								
								<tr>														
									<logic:notEmpty property="taskList" name="masterProjectPlanBean">																					
									<table width="80%" border="1" class="tab2" cellpadding="3" cellspacing="1" align="center">
										<tr class="grayBg">
											<th width="20%" nowrap="nowrap">Version</th>
											<th width="30%" nowrap="nowrap">Workflow Name</th>									
											<th width="40%" nowrap="nowrap">Task</th>
											<th width="40%" nowrap="nowrap">Stake Holder</th>
										</tr>
										<c:set var="workflowId" value="0"></c:set>
										<c:set var="stageId" value="0"></c:set>
										<c:set var="sno" value="0"></c:set>
										<logic:iterate id="masterProjectPlanBean_id" name="masterProjectPlanBean" property="taskList" indexId="index1">
											<c:if test="${masterProjectPlanBean_id.stagehistoryid.workflowhistoryid.id!=workflowId}">
												<c:set var="sno" value="${sno+1}"></c:set>
												<c:set var="workflowId" value="${masterProjectPlanBean_id.stagehistoryid.workflowhistoryid.id}"></c:set>
											</c:if>
										</logic:iterate>
										<c:set var="workflowId" value="0"></c:set>
										<c:set var="stageId" value="0"></c:set>
										<logic:iterate id="masterProjectPlanBean_id" name="masterProjectPlanBean" property="taskList" indexId="index1">
										<c:if test="${masterProjectPlanBean_id.stagehistoryid.workflowhistoryid.id!=workflowId}">
										<tr class="normal">
											<c:set var="counter" value="0"></c:set>
											<td>
												<input name="btn1" onClick="show('tbl',this,<c:out value="${sno}"/>)" type="button" value="+" style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;&nbsp;
												<c:out value="Version ${sno}.0" />
											</td>
											<c:set var="sno" value="${sno-1}" />
											<td>
												<bean:write name="masterProjectPlanBean_id" property="stagehistoryid.workflowhistoryid.workflowname" />																						
											</td>
										</tr>
										<c:set var="workflowId" value="${masterProjectPlanBean_id.stagehistoryid.workflowhistoryid.id}"></c:set>
										</c:if>
										<tbody id="tbl${sno+1}.${counter}" style="display:none">
										<c:if test="${masterProjectPlanBean_id.stagehistoryid.stageid!=stageId}">
										<tr class="normal">
											<td></td>
											<td></td>												
											<td></td>
										</tr>
										<c:set var="stageId" value="${masterProjectPlanBean_id.stagehistoryid.stageid}"></c:set>
										</c:if>
										<tr class="lightBg">
											<td></td>
											<td></td>											
											<td>
												<bean:write name="masterProjectPlanBean_id" property="taskname" />
											</td>
											<td width="40%" nowrap="nowrap">
												<bean:write name="masterProjectPlanBean_id" property="stakeholder.rolename" />
											</td>
										</tr>
										
										<c:set var="counter" value="${counter+1}"></c:set>
										</tbody>
										</logic:iterate>
									</table>								
									</logic:notEmpty>									
								</tr>															
								<tr>
									<logic:messagesPresent message="true">
										<table width="50%" align="center" id='messageBody'>
											<tr>
												<td>&nbsp;</td>
											</tr>
											<tr>
												<td>
													<font color="red" face="Verdana" size="2">
														<html:messages id="message" message="true">
															<li><bean:write name="message" /></li>
														</html:messages>
													</font>
												</td>
											</tr>
											<tr>
												<td>&nbsp;</td>
											</tr>
										</table>									
									</logic:messagesPresent>
							</tr>							
						</table>																	
					</tr>
				</table>				
				</tr>
				</table>
				</div>																																
</html:form>
</BODY>
</html:html>
