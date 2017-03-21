<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.utilities.Messages"%>
<%@page import="com.ibm.ioes.npd.utilities.AppConstants"%>
<html:html>
<head>
<title>ChangeOrder Workflow</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
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
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>
<script type="text/javascript" src="npd/js/inputColor.js"></script>
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
	errorString+="Please Select ChangeOrder Category"+"\n";
	}
	if(errorString!="")
	{
	alert(errorString);
	return false;
	}
	else
	{
	return true;
	}
}
function showVersion(val)
{
	
	//myform
	var da=new Date();
 	windowURL="<c:out value='${sessionScope.MenuContextPath}' /><%="/"+(String)request.getAttribute(AppConstants.INITIAL_SERVLET_REQUESTED)%>?method=viewHistory&npdCategoryId="+val+"&"+da.getTime();
//	windowDefault="resizable=yes;dialogWidth:700px;dialogHeight:400px;center:yes;resizable:yes;status:no;";
	windowDefault='<%=Messages.getMessageValue("windowDefault")%>';
	//window.open(windowURL,"Versions",windowDefault);
	
	var chil=window.open("","Versions"+da.getTime(),windowDefault);

	
	//var strF="<HTML><HEAD></HEAD><BODY><FORM name='childform' method='post' action='<c:out value='${sessionScope.MenuContextPath}' /><%="/"+(String)request.getAttribute(AppConstants.INITIAL_SERVLET_REQUESTED)%>'>";
	var strF="<HTML><HEAD></HEAD><BODY><FORM name='childform' method='post' action='/IOES/masterPlanVersion.do?spPage=viewHist'>";	
	var str1="<input type='hidden' name='method' value='viewHistory'>";
	var str2="<input type='hidden' name='npdCategoryId' value='"+val+"'>";	
	var strL="</FORM></BODY></HTML>";			
	
	var str=strF+str1+str2+strL;		
	chil.document.write(str);
	var chilForm=chil.document.childform;	
	chilForm.submit();
	
	
}

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}
function editWorkflow(var1)
{
	document.getElementById("workflowId").value=var1;
	document.masterProjectPlanBean.action="/IOES/editMasterPlan.do?method=viewMasterPlan&editWorkflow=editWorkflow";
	showLayer();
	document.masterProjectPlanBean.submit();	
}

function editFinalizedWorkflow(var1)
{
	answer=confirm("Opening finalized Workflow will create a new and replace an Existing Draft . Do you want to continue.");
	if(answer)
	{
		document.getElementById("workflowId").value=var1;
		document.masterProjectPlanBean.action="/IOES/editMasterPlan.do?method=viewMasterPlan&editFinalizedWorkflow=editFinalizedWorkflow";
		showLayer();
		document.masterProjectPlanBean.submit();	
	}
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
	myForm.action.value="<%="/"+(String)request.getAttribute(AppConstants.INITIAL_SERVLET_REQUESTED)%>";
}
</script>

</head>
<body onload="onBodyLoad();">
<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">
				<td><img src="gifs/top/home.gif"  title="Home" onClick="goToHome();"></img></td>
			</tr>
</table>
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
</div>
<table width="98%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="projectPlan">
		<td valign="bottom" width="100%" align="center">
		
		</td>
	</tr>
</table>
<div class="head"> <span>Master Work Flow List </span> </div>
<html:form action="/addMasterPlan" method="post" styleId="myform">
<html:hidden property="workflowId" />
			<table width="100%" border="0" class="tab2" cellpadding="0"
				cellspacing="0" align="center">
				<tr class="normal">
					<logic:empty name="masterProjectPlanBean"
						property="existingWorkflowList">
						<td align="center">No Workflow Present</td>
					</logic:empty>
					<logic:notEmpty name="masterProjectPlanBean"
						property="existingWorkflowList">
						<table width="75%" border="1" class="tab2" cellpadding="3"
							cellspacing="1" align="center">
							<tr class="grayBg">

									<td>iB2B Workflow Category</td>
									<td>
										Workflow Name									
									</td>										
									<td>
										Status
									</td>
									<td>
										Version
									</td>
								</tr>
							<logic:iterate id="existingWorkflowList_id"
								name="masterProjectPlanBean" property="existingWorkflowList"
								indexId="index1">
								<%
									String classType=null;
									if(index1.intValue() % 2 == 0)
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
										value="${existingWorkflowList_id.npdCategory.npdcatdesc}" /></td>
									<td>
										<logic:equal name="existingWorkflowList_id" property="isactive" value="2">
											<a href="#"
												onclick="editWorkflow(<bean:write name='existingWorkflowList_id' property='workflowid' />);">
												<c:out value="${existingWorkflowList_id.workflowname}" />
												</a>
										</logic:equal>
										<logic:equal name="existingWorkflowList_id" property="isactive" value="1">
											<a href="#"
												onclick="editFinalizedWorkflow(<bean:write name='existingWorkflowList_id' property='workflowid' />);">
												<c:out value="${existingWorkflowList_id.workflowname}" />
											</a>
										</logic:equal>
										
										</td>
									<td>
										<logic:equal name="existingWorkflowList_id" property="isactive" value="1">Finalized</logic:equal>
										<logic:equal name="existingWorkflowList_id" property="isactive" value="2">Draft</logic:equal>										
									</td>										
									<td>
										<logic:equal name="existingWorkflowList_id" property="isactive" value="2">
											-
										</logic:equal>
										<logic:equal name="existingWorkflowList_id" property="isactive" value="1">
											<a href="#" onclick='showVersion(<c:out value="${existingWorkflowList_id.npdCategory.npdcatid}" />)'>See Versions</a>
										</logic:equal>
										
									</td>
								</tr>

							</logic:iterate>

						</table>
					</logic:notEmpty>
				</tr>
			</table>
</html:form>
</div>
</BODY>


</html:html>
