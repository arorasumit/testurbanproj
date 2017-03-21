
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.ReportsBean"%>
<%int strcount=0;%>
<html>
<head>
<title>View Currency Change Data</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
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
var count=0;
var counter = 1;
var lsiArray = new Array();
var SIDArray = new Array();
var logicalSINo="";
var noOfLSI =0;
var lstProducts="";
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	showLayer();
	myForm.submit();
}
function openOrder(order_type,orderNo)
{
 	var modeValue="editON";
	if(order_type == 'New')
	{
	 	    document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
	     showLayer();
	      document.forms[0].submit();
	}
	else if(order_type =='Change')
	{
		   document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
	      showLayer();
	      document.forms[0].submit();
	}


}






path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
i
<body >
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/currencyChange" styleId="searchForm" method="post">
		<bean:define id="formBean" name="currencyChangeBean"></bean:define>
		<input type="hidden" name="method" />
		<input type="hidden" name="source" id="source"/>
		
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> <span> View Currency Change Data</span> </div>
		<div border="1" class="error" align="center">
			<logic:present name="validation_errors">
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>
			</logic:present>
			<html:messages id="message" message="true">
				<li><font color="red"><bean:write name="message"/></font></li>
			</html:messages>
		</div>
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Batch Details</b></legend>
				<table border="0"  align="left" style="margin-top:7px">
						<logic:present name="listCurrencyChangeBatchDetails" scope="request">
					<logic:notEmpty  name="listCurrencyChangeBatchDetails" scope="request"> 					
						<logic:iterate id="row" name="listCurrencyChangeBatchDetails" indexId="recordId">
							<%
								String classType=null;
								if(recordId.intValue() % 2 == 0)
								{
								classType="lightBg";
								}
								else
								{
								classType="normal";				
								}	
							%>				
							<tr class="<%=classType%>">			
					
						<td align="right" style="font-size:9px">Batch ID</td>
						<td align="left" nowrap>
						
							<input type="text" readonly="true" class="inputBorder1" style="width:90px;float:left;" value="<bean:write name="row" property="batchID"/>"/>
						</td>
						<td align="right" style="font-size:9px">Status</td>
						<td align="left" nowrap>
								<input type="text" readonly="true" class="inputBorder1" style="width:90px;float:left;" value='<bean:write name="row" property="statusOfCurrencyChange"/>'>
						</td>
						
						
				</tr>
					<tr>					
						<td align="right" style="font-size:9px">Source Party</td>
						<td align="left" nowrap>
							<input type="text" readonly="true" class="inputBorder1" style="width:90px;float:left;" value='<bean:write name="row" property="sourcePartyNo"/>'>
						</td>
						<td align="right" style="font-size:9px;">Source Party Name</td>
						<td align="left"><input type="text" readonly="true" class="inputBorder1" style="width:90px;float:left;" value='<bean:write name="row" property="sourcePartyName"/>'></td>
						
						
					</tr>
					
				</logic:iterate>	
				</logic:notEmpty>
				<logic:empty  name="listCurrencyChangeBatchDetails">
					   <tr align="center" >
						 <td colspan="17" align="center"><B><font color="red">No Records Found</font></B></td>
					   </tr>
					</logic:empty>	
				
				</logic:present>
				</table>
		</fieldset>
		
		
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Source A/C Info</b></legend>
				<table border="1"  align="left" style="margin-top:7px">
				
				
							
				
				<tr>
				
					
					
						<td align="left" width="5%" style="font-size:9px">Logical SI</td>
						<td align="left" width="5%" style="font-size:9px">Product Name</td>						
						<td align="left" width="5%" style="font-size:9px;">Cust Acct No</td>
						<td align="left" width="5%" style="font-size:9px;">Short Code</td>
						<td align="left" style="font-size:9px;">Currency</td>
						<td align="left" style="font-size:9px;">Rate</td>						
						<td align="left" style="font-size:9px;">New Currency</td>					
						
						
					</tr>
				
				<logic:present name="listLogicalSIDetails" scope="request">
					<logic:notEmpty  name="listLogicalSIDetails" scope="request"> 					
						<logic:iterate id="row2" name="listLogicalSIDetails" indexId="recordId1">
							<%
								String classType=null;
								if(recordId1.intValue() % 2 == 0)
								{
								classType="lightBg";
								}
								else
								{
								classType="normal";				
								}	
								
									strcount++;		
							%>	
							
							
					<tr class="<%=classType%>">
					<td width="5%" align="center" style="font-size:9px">						
						<input type="text" size="15" readonly="true" class="inputBorder1" name="logicalSistr<%=strcount%>" style="width:90px;float:left;" value="<bean:write name="row2" property="logicalSistr"/>"/>
						</td>					
					<td width="5%" style="font-size:9px" align="center">
	                 <input type="text" size="15" readonly="true" class="inputBorder1" name="serviceName<%=strcount%>" style="width:90px;float:left;" value="<bean:write name="row2" property="serviceName"/>"/>
					</td>				
					
					<td  width="5%" style="font-size:9px" align="center">
					   <input type="text" size="15" readonly="true" class="inputBorder1" name="accountno<%=strcount%>" style="width:90px;float:left;" value="<bean:write name="row2" property="accountno"/>"/>
					</td>
						<td  width="5%" style="font-size:9px" align="center">
	                 <input type="text" size="15" readonly="true" class="inputBorder1" name="shortCode<%=strcount%>" style="width:90px;float:left;" value="<bean:write name="row2" property="shortCode"/>"/>
					</td>	
					<td width="5%" style="font-size:9px" align="center">
					   <input type="text" readonly="true" class="inputBorder1" name="neworderno<%=strcount%>" style="width:90px;float:left;" value="<bean:write name="row2" property="oldCurrencyName"/>"/>
					</td>
						<td width="5%" style="font-size:9px" align="center">
	                  <input type="text" readonly="true" class="inputBorder1" name="bdisconorderno<%=strcount%>" style="width:90px;float:left;" value="<bean:write name="row2" property="rate"/>"/>
					</td>
					<td width="5%" style="font-size:9px" align="center">
	                  <input type="text" readonly="true" class="inputBorder1" name="bdisconorderno<%=strcount%>" style="width:90px;float:left;" value="<bean:write name="row2" property="newCurrencyName"/>"/>
					
					
					</logic:iterate>
					</logic:notEmpty>
					</logic:present>
					
					
					
				</table>
		</fieldset>
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Target A/C Info</b></legend>
				<table border="1"  align="left" style="margin-top:7px">
				<tr>
						<td align="left" width="5%" style="font-size:9px">New Order No</td>
						<td align="left" width="5%" style="font-size:9px">Change Order No</td>						
						<td align="left" width="5%" style="font-size:9px;">Transfer Date</td>
						<td align="left" width="5%" style="font-size:9px;">Error</td>
					</tr>
				
				<logic:present name="listCurrencyChangeDetails" scope="request">
					<logic:notEmpty  name="listCurrencyChangeDetails" scope="request"> 					
						<logic:iterate id="row1" name="listCurrencyChangeDetails" indexId="recordId1">
							<%
								String classType=null;
								if(recordId1.intValue() % 2 == 0)
								{
								classType="lightBg";
								}
								else
								{
								classType="normal";				
								}	
								
									strcount++;		
							%>				
							
					
					<tr class="<%=classType%>">
					
					
						
					<td>
					   <input type="text" readonly="true" class="inputBorder1" name="neworderno<%=strcount%>" style="width:90px;float:left;" value="<bean:write name="row1" property="neworderno"/>"/>
					   <div class="searchBg1"><a href="#" onClick="openOrder('New',<bean:write name="row1" property="neworderno"/>)">...</a></div> 
					</td>
					<td>
	                  <input type="text" readonly="true" class="inputBorder1" name="bdisconorderno<%=strcount%>" style="width:90px;float:left;" value="<bean:write name="row1" property="bdisconorderno"/>"/>
					  <div class="searchBg1"><a href="#" onClick="openOrder('Change',<bean:write name="row1" property="bdisconorderno"/>)">...</a></div> 	
					</td>						
						<td>							
							<input type="text" readonly="true" class="inputBorder1" name="transferdate<%=strcount%>" style="width:90px;float:left;" value="<bean:write name="row1" property="transferdate"/>"/>					
					</td>
						<td>
						<input type="text" readonly="true" class="inputBorder1" name="error<%=strcount%>" style="width:90px;float:left;" value="<bean:write name="row1" property="error"/>"/>
					</td>
				</tr>
				</logic:iterate>
				</logic:notEmpty>
				</logic:present>	
				</table>
		</fieldset>
		
	</html:form>
</body>
</html>
