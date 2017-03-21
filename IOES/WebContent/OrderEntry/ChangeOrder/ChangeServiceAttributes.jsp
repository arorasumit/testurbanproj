<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<html>
<head>
<title>ServiceAttributes</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/enableDisableOrderEntryUtility.js"></script>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
function saveServiceAttributes()
{
	
   var frm=document.getElementById('serviceAttributes');
	
	//Start by Saurabh for role check
	var callerWindowObj = dialogArguments;
	var orderNo = callerWindowObj.document.getElementById('poNumber').value
	
	<%
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
	%>
	
	var roleName = <%=objUserDto.getUserRoleId() %>;
	var userId = "<%=objUserDto.getUserId() %>";
	var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleName,userId);
	var shortCode = jsonrpc.processes.getShortCode(roleName);
	
	if(orderDetails.list.length>0)
	{
		stage=orderDetails.list[0].stageName;
		if(shortCode==stage)
		{
			//do nothing
		}
		else if(stage=="New" || stage=="NEW" || stage=="Partial Initiated")
		{
			//do nothing
		}
		else if(stage=="Billing Trigger" && shortCode=="COPC")
		{
			//do nothing
		}
		else if((stage=="Partial Publish"  || stage=="Partial Initiated") && (shortCode=="COPC" || shortCode=="SED"))
		{
			//do nothing
		}else if(stage=="Partial Initiated"){
			//do nothing
		}
		else
		{
			alert("You are not authorised to save or update the values");
			return false;
		}
	}
	//End By Saurabh
	
	var effStartDate = frm.effStartDate.value;
	/*var effEndDate = frm.effEndDate.value;
	if(dateCompare(effStartDate,effEndDate)==1)
	{			
		alert("Effective EndDate Cannot be less than Effective StartDate !!");
		return false;
	} 
	if(frm.effEndDate.value=="")
	{
		alert("Please Input Effective End Date!!");
		frm.effEndDate.focus();
		return false;
	}
	*/
	if(frm.effStartDate.value=="")
	{
		alert("Please input Effective Start Date!!");
		frm.effStartDate.focus();
		return false;
	}
	
	if(frm.logicalSINo.value=="")
	{
		alert("Please input Customer Logical SI No!!");
		frm.logicalSINo.focus();
		return false;
	}
	
	var jsData =
	{
		serviceId:frm.serviceID.value,
		effStartDate:frm.effStartDate.value,
		//effEndDate:frm.effEndDate.value,
		logicalSINo:frm.logicalSINo.value,
		attRemarks:frm.attRemarks.value,
		updateType:frm.updateType.value
	};
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var lstService = jsonrpc.processes.updateServiceAttribute(jsData);
	if(isNull(lstService.errors)==true)
    {
    	alert("Data saved successFully!!");  
    	//save into parent window the edit status
	    	var callerWindowObj = dialogArguments;
	   	  counterVal=callerWindowObj.document.getElementById('hdnCurrentTempCounter').value;
	   	  var nam="servAttrEntered"+counterVal;
   		 callerWindowObj.document.getElementById(nam).value = '1';

    	window.close();
    }	
    else
    {
    	alert(lstService.errors.list[0]);		
    	return false;
    }
	//UpdateParentInterface(lstService.serviceId,serviceTypeName,selectedText.options[selectedText.selectedIndex].text);
}

function fetchServiceAttribute(serviceID)
{
	var callerWindowObj = dialogArguments;
	if(callerWindowObj.document.getElementById('isBillingTriggerReady').value=="ENABLE")
	{
		document.getElementById('imgSave').style.visibility="hidden";
	}
	else
	{
		document.getElementById('imgSave').style.visibility="visible";
	}
	
	var frm=document.getElementById('serviceAttributes');
	var jsData =
	{
		serviceId:frm.serviceID.value
	};
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var maxSIno = jsonrpc.processes.getLogicalSiNo();
	var lstService = jsonrpc.processes.fetchServiceAttribute(jsData);
	var a=lstService.effStartDate
	frm.logicalSINo.value=maxSIno;
	if(a=="")
	{
	  
		//frm.effStartDate.value=''
		//frm.effEndDate.value=''
		frm.attRemarks.value=''
		frm.serviceDate.value='DRAFT'	
		frm.updateType.value=0
	}
	else
	{
		frm.effStartDate.value=lstService.effStartDate
		//frm.effEndDate.value=lstService.effEndDate
		frm.logicalSINo.value=lstService.logicalSINo
		frm.attRemarks.value=lstService.attRemarks
		frm.serviceDate.value='New'
		frm.updateType.value=1
	}
	
	//==============[ START CLEP]==================
		enableDisableCLEP();
	//==============[ END CLEP]==================
    
}
//start :CLEP Enable Disable Order Entry
function enableDisableCLEP(){
	var callerWindowObj = dialogArguments;
	var orderNo=callerWindowObj.document.getElementById('poNumber').value;	
	var stateid=getOrderStateforClep(orderNo,path);			
	orderEntryEnableDisable('CHANGESERVICEATTRIBUTE',stateid, null);
}
//end clep
</script>
<body onload="fetchServiceAttribute(<%=request.getParameter("serviceID")%>)">
	<html:form action="/NewOrderAction" styleId="serviceAttributes" method="post">
		<fieldset class="border1" >
			<legend> <b>Service Details</b> </legend>
			<table  border="0" cellspacing="0" cellpadding="0" align="center" width="100%" >
				<tr>
					<td width="15%">Service Stage</td>
					<td width="14%"><input type="text" name="serviceDate" class="inputBorder1" readonly="readonly" style="width:120px;" value="DRAFT"></td>
					<td width="15%"> Customer Logical SI No</td>
					<td width="15%">
						<input type="text" class="inputBorder1" style="width:120px;" name="logicalSINo" onBlur="" readonly="readonly">	</td>
					<td width="41%" colspan="3"><img src="gifs/top/4.gif" onClick="saveServiceAttributes()" id="imgSave" style="visibility: visible;"></td>        
				</tr>
				<tr>
					<td width="15%">Effective Start Date </td>
					<%SimpleDateFormat sdf_Date=new SimpleDateFormat("dd/MM/yyyy");%>
					    <td width="14%">
						<input type="text" class="inputBorder1" style="width:80px;" name="effStartDate" id="effStartDate" onBlur="if(this.value.length > 0){return checkdate(this)}" readonly="readonly" value="<%=sdf_Date.format(new Date(System.currentTimeMillis()))%>" size="46">	
						<img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..." onclick="javascript:show_calendar(serviceAttributes.effStartDate);" style="vertical-align: bottom"/>
					</td>
					
					<td width="41%" colspan="3"></td>        
				</tr>
				<tr>
					<td width="15%">Remarks</td>
					<td colspan="4">
						<textarea type="text" class="inputBorder2" style="width:97%" maxlength="1000" rows="5" name="attRemarks"></textarea>
						<input type="hidden" value="" name="updateType">
					</td>   
					<input type="hidden" name="serviceID" value="<%=request.getParameter("serviceID") %>">
				</tr>
			</table>
		</fieldset>
	</html:form>
</body>
</html>
					
