<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Kalpana	    11-January-2014				Added conditions to start,stop SCM listener and send file to SCM and process file receive from SCM for third party -->
<!-- [003] raveendra      20150403-R1-021203      05-May-2015                 Online Payment fix -->
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
<title>Search Billing Address Detail</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">


function runSchedular()
{
	var myForm=document.getElementById('searchForm');
	id_run.disabled=true;
	myForm.action="<%=request.getContextPath()%>/SendXmlToM6.do?method=SendxmlToM6&flag=new";
	myForm.submit();
	
}
function runSchedular_Change()
{
	var myForm=document.getElementById('searchForm');
	id_run_change.disabled=true;
	myForm.action="<%=request.getContextPath()%>/SendXmlToM6.do?method=SendxmlToM6&flag=change";
	myForm.submit();
	
}
function runProcessXmls()
{
	var myForm=document.getElementById('searchForm');
	id_process_xmls.disabled=true;
	myForm.action="<%=request.getContextPath()%>/SendXmlToM6.do?method=SendxmlToM6&flag=processXmls";
	myForm.submit();
}
function start()
{	
	var myForm=document.getElementById('searchForm');	
	myForm.action="<%=request.getContextPath()%>/SendXmlToM6.do?method=SendxmlToM6&flag=m6ListnerStart";
	myForm.submit();
}
function stop()
{
	var myForm=document.getElementById('searchForm');	
	myForm.action="<%=request.getContextPath()%>/SendXmlToM6.do?method=SendxmlToM6&flag=m6ListnerStop";
	myForm.submit();
}
function startCLEP()
{	
	var myForm=document.getElementById('searchForm');	
	myForm.action="<%=request.getContextPath()%>/SendXmlToM6.do?method=SendxmlToM6&flag=clepListnerStart";
	myForm.submit();
}
function stopCLEP()
{
	var myForm=document.getElementById('searchForm');	
	myForm.action="<%=request.getContextPath()%>/SendXmlToM6.do?method=SendxmlToM6&flag=clepListnerStop";
	myForm.submit();
}
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}
function runHardwareCancellationScheduler()
{
	var myForm=document.getElementById('searchForm');
	id_Cancel_Line.disabled=true;
	myForm.action="<%=request.getContextPath()%>/SendXmlToM6.do?method=SendxmlToM6&flag=lineScheduler";
	myForm.submit();
	
}
//Start[001]
function runSCMSchedular()
{
	var myForm=document.getElementById('searchForm');
	id_run.disabled=true;
	myForm.action="<%=request.getContextPath()%>/SendXmlToM6.do?method=SendxmlToM6&flag=sendToScm";
	myForm.submit();
}
function runSCMProcessXmls()
{
	var myForm=document.getElementById('searchForm');
	id_process_xmls.disabled=true;
	myForm.action="<%=request.getContextPath()%>/SendXmlToM6.do?method=SendxmlToM6&flag=processSCMXmls";
	myForm.submit();
}
function startSCMListener()
{	
	var myForm=document.getElementById('searchForm');	
	myForm.action="<%=request.getContextPath()%>/SendXmlToM6.do?method=SendxmlToM6&flag=scmListnerStart";
	myForm.submit();
}
function stopSCMListener()
{
	var myForm=document.getElementById('searchForm');	
	myForm.action="<%=request.getContextPath()%>/SendXmlToM6.do?method=SendxmlToM6&flag=scmListnerStop";
	myForm.submit();
}
//End[001]
/* start [003] */
function runRFBTSchedular(){
	
	var myForm=document.getElementById('searchForm');
	id_rfbt_schedular.disabled=true;
	myForm.action="<%=request.getContextPath()%>/SendXmlToM6.do?method=SendxmlToM6&flag=rfbtSchedular";
	myForm.submit();
}
/* end [003] */
</script>
<body >
	<form action="<%=request.getContextPath()%>/SendXmlToM6.do" id="searchForm" method="post">
	<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
		<tr align="right">
			<td><img src="./gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
		</tr>
	</table>	
		<div class="head"> <span>Schedular </span> </div>
		<table width="100%"   border="0" cellspacing="0" cellpadding="0" align="center" id='tblBCPList'>
			<tr>
				<td align="center"  width="50%" valign="top">
						<logic:notPresent name="accountDetailsBean" scope="request">
							<table border="0" cellspacing="0" cellpadding="0" style="margin-top:7px ">
								<tr>
									<td align="center" style="font-size:9px">Send To M6 Schedular</td>
									<td align="center" valign="top" width="58%">
										<div class="searchBg" style="margin-right:10px;"><a href="#"
											onclick="javascript:runSchedular();" id="id_run">New Schedular</a></div>
										<div class="searchBg" style="margin-right:10px;"><a href="#"
											onclick="javascript:runSchedular_Change();" id="id_run_change">Change
										Schedular</a></div>
										<div class="searchBg" style="margin-right:10px;"><a href="#"
											onclick="javascript:runProcessXmls();" id="id_process_xmls">Process
											XMLs</a></div>
											<!-- /* start [003] */ -->
											<div class="searchBg" style="margin-right:10px;"><a href="#"
											onclick="javascript:runRFBTSchedular();" id="id_rfbt_schedular">RFBT Schedular</a></div>
											<!-- /* End [003] */ -->
									</td>
									<td align="center" style="font-size:9px;" nowrap></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
									<td>
									</td>
									<td height="37">
										<center>
										<div class="searchBg" style="margin-right:12px;"><a href="#" onclick="javascript:start();" id="start">START M6 LISTENER</a></div>
										<div class="searchBg" style="margin-right:12px;"><a href="#" onclick="javascript:stop();" id="stop">STOP M6 LISTENER</a></div>
										</center>
									</td>														
								</tr>
								
								<tr>
									<td height="3">M6 Listner State</td>
									<td height="3" width="454"><%=application.getAttribute(AppConstants.M6_LISTENER_STATE)%> </td>							
								</tr>
								<tr>
									<td></td>
									<td colspan="3">
										<div class="searchBg" style="margin-right:12px;"><a href="#" onclick="javascript:startCLEP();" id="CLEPstartId">START CLEP LISTENER</a></div>
										<div class="searchBg" style="margin-right:12px;"><a href="#" onclick="javascript:stopCLEP();" id="CLEPstopId">STOP CLEP LISTENER</a></div>
										<div class="searchBg" style="margin-right:12px;"><a href="#" onclick="javascript:runHardwareCancellationScheduler();" id="id_Cancel_Line">Line Scheduler</a></div>
									</td>									
								</tr>
								<tr>
										<td></td>
										<td colspan="3"><b>CLEP LISTENER STATE:&nbsp;&nbsp;
											<%=application.getAttribute(AppConstants.CLEP_LISTENER_STATE)%> 
										</b></td>																							
								</tr>
								<!-- Start[001] -->
								<tr>
									<td>
									</td>
									<td height="37">
										<center>
										<div class="searchBg" style="margin-right:12px;"><a href="#" onclick="javascript:startSCMListener();" id="startSCM">START SCM LISTENER</a></div>
										<div class="searchBg" style="margin-right:12px;"><a href="#" onclick="javascript:stopSCMListener();" id="stopSCM">STOP SCM LISTENER</a></div>
										</center>
									</td>														
								</tr>
								<tr>
									<td align="center" style="font-size:9px">Send To SCM Schedular</td>
									<td align="center" valign="top" width="58%">
										<div class="searchBg" style="margin-right:10px;"><a href="#"
											onclick="javascript:runSCMSchedular();" id="id_run_send">Send To SCM</a></div>
										<div class="searchBg" style="margin-right:10px;"><a href="#"
											onclick="javascript:runSCMProcessXmls();" id="id_process_xmls_scm">Process SCM
											XMLs</a></div>
									</td>
									<td align="center" style="font-size:9px;" nowrap></td>
									<td></td>
									<td></td>
								</tr>
								<tr>
										<td></td>
										<td colspan="3"><b>SCM LISTENER STATE:&nbsp;&nbsp;
											<%=application.getAttribute(AppConstants.SCM_LISTENER_STATE)%> 
										</b></td>																							
								</tr>
								<!-- End[001] -->
							</table>
						</logic:notPresent>
					
				</td>
				
				</tr>
				
				<tr>
					<td align="center" width="50%" valign="top">
					
				</td>
				</tr>
			<logic:present name="validation_errors">
			<tr><td align="center">
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><br>
				</logic:iterate>
				</td>
			</tr>
			</logic:present>
			<html:messages id="message" message="true">
			<tr>
				<td colspan="2" align="center" style="color: red;">
	
						<li><bean:write name="message"/></li>
	
				</td>
			</tr>
			</html:messages>
			<tr><td>
			
			</td>
			</tr>
		</table>
	</form>
</body>
</html>
