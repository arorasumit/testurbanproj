<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<html>
<head>
<title>Attach Workflow</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
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
<script language="javascript" type="text/javascript">

function attchchworkflow(workflowid)
{
	
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var callerWindowObj = dialogArguments;
	callerWindowObj.document.searchForm.hdnworkflowid.value = workflowid;
	//callerWindowObj.attachWorkflowForSelectedProject();
	
	var orderNo = '<%=request.getParameter("changeOrderNo")%>';
	var roleId = '<%=request.getParameter("roleName")%>';
	
	var jsData =			
    {
		poNumber:orderNo,
		projectWorkflowId:workflowid,
		roleId:roleId
	};
		
    var isWorkFlowAttached = jsonrpc.processes.attachworkflowForChangeOrder(jsData);
   	if(isWorkFlowAttached==1)
   		alert('Workflow inserted successfully');	   		

   	callerWindowObj.ViewServiceTree(6);
	window.close();	
}
function getNpdCategory(ib2bWorkflowAttachedName)    // Added By Saurabh to view workflow in changeorder
{
	//alert(ib2bWorkflowAttachedName);
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	var npdCategory = jsonrpc.processes.getNpdCategory(ib2bWorkflowAttachedName);
	showVersion(npdCategory);
}
function showVersion(val)		// Added By Saurabh to view workflow in changeorder
{
	//alert(val);
	var da=new Date();
 	windowURL="/IOES/null?method=viewHistory&npdCategoryId="+val+"&"+da.getTime();
	windowDefault='!windowDefault!';
	
	var chil=window.open("","Versions"+da.getTime(),windowDefault);
	
	var strF="<HTML><HEAD></HEAD><BODY><FORM name='childform' method='post' action='/IOES/masterPlanVersion.do?spPage=viewHist'>";	
	var str1="<input type='hidden' name='method' value='viewChangeOrderWorkflow'>";
	var str2="<input type='hidden' name='npdCategoryId' value='"+val+"'>";	
	var strL="</FORM></BODY></HTML>";			
	
	var str=strF+str1+str2+strL;		
	chil.document.write(str);
	var chilForm=chil.document.childform;			
		//showLayer();
	chilForm.submit();
}
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body>
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/NewOrderAction" styleId="serviceAttributes" method="post">
		<DIV class="head"> Attach Workflow </DIV>
		<fieldset class="border1" >
			<legend> <b>Workflow Details</b> </legend>
			<table width="96%"  border="1" cellspacing="1" cellpadding="2">
				<tr>
					<td width="15%">Select Workflow</td>
					<td width="15%">Service</td>
					<td width="14%">Workflow Attached</td>
					<td width="14%">View Workflow</td>
				</tr>
				<logic:present name="listChangeSubtype">
					<logic:iterate id="row" name="listChangeSubtype" scope="request" indexId="rowno">
						<tr>		
							<td width="10%"><input type="radio" id="chkSelectworkflow" onclick="attchchworkflow('<bean:write name="row" property="ib2bWorkflowAttachedId" />')" name="chkSelectworkflow"  /></td>
							<td width="15%"><bean:write name="row" property="subChangeTypeName" /></td>
							<td width="14%"><bean:write name="row" property="ib2bWorkflowAttachedName" /></td>
							<td width="10%"><a href="#" onclick="getNpdCategory('<bean:write name="row" property="ib2bWorkflowAttachedName" />')" name="viewWorkflow"  id="workflowAttachedName" />viewWorkflow</a></td>
						</tr>	
					</logic:iterate>
				</logic:present>
			</table>
		</fieldset>
	</html:form>
</body>
</html>
					
