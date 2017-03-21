<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic"
	prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-nested"
	prefix="nested"%>
<html>
<head>
<title>:: View Order: Integrated Order Entry System</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="./gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/formValidations.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
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
var path='<%=request.getContextPath()%>';
function goToHome()
{
	document.forms[0].action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	showLayer();
	document.forms[0].submit();
}
function setformValue()
{
	
	var callerWindowObj = dialogArguments;
	document.getElementById('searchTaskId').value =  callerWindowObj.document.getElementById("searchTaskId").value;
	document.getElementById('searchTaskOwner').value =  callerWindowObj.document.getElementById("searchTaskOwner").value;
}

function SaveFrm()
{
	    
		/*if(isBlank('notesType','Notes Type')==false) 
			return false;

		if(isBlank('notesMeaning','Notes Meaning')==false) 
			return false;
*/
		    
	
	var jsData =			
    {
		taskID:document.getElementById('searchTaskId').value,
		taskType:document.getElementById('searchTaskOwner').value,
		createdBy:document.getElementById('createdBy').value,
		createdDate:document.getElementById('createdDate').value,
		notesType:document.getElementById('notesType').value,
		notesMeaning:document.getElementById('notesMeaning').value
	};
	
    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    
    var retVal = jsonrpc.processes.SaveNotes(jsData);

   
    
    if(isNull(retVal.errors)==true)
    {
    	alert(retVal.msgOut);  
    	window.close();
    }	
    else
    	alert(retVal.errors.list[0]);		
    return false;
}
function fncancelNotes()
{
	window.close();
}
function getTip(value)
{	
	Tip("<Table border=0> "+ value +"   </table>");	
}

function getTip_DD(obj,value,objName)
{	
	var SelectedValue;
	var selIndex = value;
	var myForm=document.getElementById('productCatelog');
	SelectedValue=document.getElementById(objName).options[selIndex].text	
	var combo = document.getElementById(objName);
	combo.title = SelectedValue;
}
</script>
</head>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload=" setformValue()">
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
<html:form action="/viewOrderAction" enctype="text/plain">
<div>
	<div class="head"><span>Add Notes</span></DIV>
	<fieldset class="border1"><legend> <b>Notes
	Details</b> </legend>
	<table width="100%" border="1" align="center" cellpadding="0"
		cellspacing="0" class="tab2">
		<tr>
			<td width="20%">Task ID</td>
			<td width="30%"><html:text onmouseover="getTip(value)" onmouseout="UnTip()" property="searchTaskId" styleClass="inputBorder1" maxlength="50" style="width:20%;float:left;" readonly="true"/></td>			
		</tr>
		<tr>
			<td width="20%">Notes Type</td>
			<td width="30%"><html:text onmouseover="getTip(value)" onmouseout="UnTip()" property="notesType" styleClass="inputBorder1"  maxlength="200" style="width:20%;float:left;" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Note Type')};" /></td>			
		</tr>
		<tr>
			<td width="20%">Task Owner</td>
			<td width="30%"><html:text onmouseover="getTip(value)" onmouseout="UnTip()" property="searchTaskOwner" styleClass="inputBorder1" maxlength="200" style="width:40%;float:left;" readonly="true"/></td>			
			<!-- Created date and created by will not show on notes page, it will show on notes history message -->
			<!--<td width="20%">Created Date</td>-->
			<html:hidden onmouseover="getTip(value)" onmouseout="UnTip()" property="createdDate" styleClass="inputBorder1" />
		</tr>
		<tr>			
			<td width="20%">Meaning</td>
			<!-- text area charcters length and control size increased -->
			<td width="80%"><html:textarea  property="notesMeaning" styleClass="inputBorder1" style="width:100%;float:left;" rows="8" cols="2" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Meaning')};" /></td>						
			<!--  <td>Created By</td>-->			
				<html:hidden onmouseover="getTip(value)" onmouseout="UnTip()" property="createdBy" styleClass="inputBorder1"  />			
		</tr>
		<tr>
			<td colspan="4" align="center">
			<table>
				<tr>
					<td>
					<div class="searchBg"><a href="#" onClick="SaveFrm();">Save</a></div>
					</td>
					<td>
					<div class="searchBg"><a href="#" onClick="fncancelNotes();">Cancel</a></div>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	</fieldset>
	</div>
</html:form>
</body>
</html>
