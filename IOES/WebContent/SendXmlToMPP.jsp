<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>SendXmlToMPP</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script language="javascript" type="text/javascript">


function sendXMLToMPP()
{	

	//document.getElementById("txtAccountSearchResult").value="[Account Manager Id:12345678],[Project Manager Id:12345678],[FX STATUS:Completed]\n[Party Id:12345678],[Account Name:abc account],[ZoneId:122]";
	
	document.getElementById('btnSendXmlToMpp').disabled=true;	
	var varTxtInputQName="",varTxtQChannel="",varTxtQManager="";
	var varTxtQHostIP="",varTxtQPort=0,varTxtXmlData="",varSentMsgId="",var_UserId="",var_Pwd="";
	
	varTxtInputQName=document.getElementById("txtInputQName").value;
	varTxtQChannel=document.getElementById("txtInputChannelName").value;
	varTxtQManager=document.getElementById("txtInputQManagerName").value;
	varTxtQHostIP=document.getElementById("txtInputHostIP").value;
	varTxtQPort=document.getElementById("txtInputPort").value;
	varTxtXmlData=document.getElementById("txtInputMessage").value;
	var_UserId=document.getElementById("txtUserId").value;
	var_Pwd=document.getElementById("txtPwd").value;
	if(document.getElementById('txtInputPort').value.length > 0)
	{						
		if(accountValidation(document.getElementById('txtInputPort'),'Port')==false)
		{
			document.getElementById('btnSendXmlToMpp').disabled=false;	
			return false;
		}
	}
	if(varTxtInputQName=="" && varTxtQChannel=="" && varTxtQManager=="" && varTxtQHostIP=="" && varTxtQPort==0 && varTxtXmlData==""){
		alert("Please enter required fields");
		document.getElementById('btnSendXmlToMpp').disabled=false;	
		return false;
	}
	if(varTxtInputQName=="" || varTxtQChannel=="" || varTxtQManager=="" || varTxtQHostIP=="" || varTxtQPort==0 || varTxtXmlData==""){
		alert("Please enter required fields");
		document.getElementById('btnSendXmlToMpp').disabled=false;	
		return false;
	}
	
	var jsData =
			{
				userId:var_UserId,
				password:var_Pwd,
				clepQueue_reqQName:varTxtInputQName,
				clepQueue_channel:varTxtQChannel,
				clepQueue_qmgrName:varTxtQManager,
				clepQueue_host:varTxtQHostIP,
				clepQueue_port:varTxtQPort,
				xmlData:varTxtXmlData
			};
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	var msgDetails = jsonrpc.processes.sendXmlToMpp(jsData);	
	document.getElementById("txtSentMsgId").value=msgDetails;
	document.getElementById('btnSendXmlToMpp').disabled=false;
	document.getElementById("txtUserId").value="";
	document.getElementById("txtPwd").value="";
}
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}

</script>
<body >
	<form action="<%=request.getContextPath()%>/SendXmlToM6.do" id="searchForm" method="post">	
	<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
		<tr align="right">
			<td><img src="./gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
		</tr>
	</table>	
	<div class="head"> <span>Send Xml to Mpp and Search CLEP Account </span> </div>		
	<table width="100%"   border="1" cellspacing="0" cellpadding="0" align="center" class="tab2">
	<tr class="grayBg">
		<td align="left"  width="100%" valign="top"><font color="blue"><b>Send Xml to Mpp</b></font></td>
	</tr>	
	<tr>				
		<td align="center"  width="50%" valign="top">																	
			<table border="1" cellspacing="0" cellpadding="0" width="100%" style="margin-top:7px ">							
				<tr>
					<td align="left" width="30%"><font color="red">*</font>Input Queue Name:</td>
					<td align="left" valign="top" width="70%">
						<input type="text" id="txtInputQName" class="inputBorder1" value="MPP.IB2B.ORD.REQ.01"/>
						<font color="red">*</font>UserId:<input type="text" id="txtUserId" class="inputBorder1"/>
						<font color="red">*</font>Password:<input type="password" id="txtPwd" class="inputBorder1"/>
					</td>																																											
				</tr>
				<tr>
					<td align="left" width="30%"><font color="red">*</font>Input Channel Name:</td>
					<td align="left" valign="top" width="70%">
						<input type="text" id="txtInputChannelName" class="inputBorder1" value="SYSTEM.DEF.SVRCONN"/>
					</td>																																											
				</tr>
				<tr>
					<td align="left" width="30%"><font color="red">*</font>Input Q Manager Name:</td>
					<td align="left" valign="top" width="70%">
						<input type="text" id="txtInputQManagerName" class="inputBorder1" value="QMEIGS1"/>
					</td>																																											
				</tr>
				<tr>
					<td align="left" width="25%"><font color="red">*</font>Host:</td>
					<td align="left" valign="top" width="25%">
						<input type="text" id="txtInputHostIP" class="inputBorder1" value="10.14.107.178"/>
					</td>	
				</tr>
				<tr>
					<td align="left" width="25%"><font color="red">*</font>Port:</td>
					<td align="left" valign="top" width="25%">
						<input type="text" id="txtInputPort" value="5121" class="inputBorder1"/>
					</td>																																										
				</tr>
				<tr>
					<td align="left" width="25%"><font color="red">*</font>Input Message:</td>
					<td align="left" valign="top" width="25%">
						<textarea rows="2" cols="94" id="txtInputMessage" class="inputBorder1"></textarea>
					</td>																																										
				</tr>
				<tr>
					<td align="center" width="25%">
						<div class="searchBg" style="margin-right:10px;" align="center">
						<a href="#"
							onclick="sendXMLToMPP()" id="btnSendXmlToMpp">Send XML</a>
						</div>
							Sent message details:
					</td>	
					<td align="left" valign="top" width="25%">					
						<textarea rows="2" cols="94" id="txtSentMsgId" class='inputDisabled' readonly="readonly"></textarea>
					</td>																																												
				</tr>				
			</table>										
			</td>				
		</tr>									
		<tr class="grayBg">
				<td align="center" width="100%" valign="top">									
				</td>
		</tr>																
	</table>			
</form>		
</body>
</html>