<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.utilities.ApplicationFlags"%>
<html>
<head>
<title>scheduler</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">

function change_ReportsExcelMaxSize()
{
	var myForm=document.getElementById('searchForm');
	id_ReportsExcelMaxSize.disabled=true;
	myForm.action="<%=request.getContextPath()%>/ApplicationPerformanceLog.do?method=change_ReportsExcelMaxSize";
	myForm.submit();
}
function change_IB2B_FX_DB_PASSWORD_CREATION()
{
	var myForm=document.getElementById('searchForm');
	id_IB2B_FX_DB_PASSWORD_CREATION.disabled=true;
	myForm.action="<%=request.getContextPath()%>/ApplicationPerformanceLog.do?method=change_IB2B_FX_DB_PASSWORD_CREATION";
	myForm.submit();
}
function change_IB2B_DB_CONNECTION_LOGGER_FLAG()
{
	var myForm=document.getElementById('searchForm');
	id_IB2B_DB_CONNECTION_LOGGER_FLAG.disabled=true;
	myForm.action="<%=request.getContextPath()%>/ApplicationPerformanceLog.do?method=change_IB2B_DB_CONNECTION_LOGGER_FLAG";
	myForm.submit();
}
function runSchedularForChargeRedirection()
{
	var myForm=document.getElementById('searchForm');
    id_runforchargeredirect.disabled=true;	
	myForm.action="<%=request.getContextPath()%>/FXScheduler.do?method=FXSchedulerForChargeRedirection";
	myForm.submit(); 
}
function fnAccount_for_modify()
{
	var myForm=document.getElementById('searchForm');
	id_account_for_modify.disabled=true;
	myForm.action="<%=request.getContextPath()%>/FXScheduler.do?method=account_modify";
	myForm.submit();
}
function change_IB2B_FX_TEST_ACCOUNT_CREATION()
{
	var myForm=document.getElementById('searchForm');
	id_IB2B_FX_TEST_ACCOUNT_CREATION.disabled=true;
	myForm.action="<%=request.getContextPath()%>/ApplicationPerformanceLog.do?method=change_IB2B_FX_TEST_ACCOUNT_CREATION";
	myForm.submit();
}
function runSchedular_FX_SYNC()
{
	var myForm=document.getElementById('searchForm');
	id_runforfindact.disabled=true;
	myForm.action="<%=request.getContextPath()%>/FindAccount.do?method=sync";
	myForm.submit();
}
function findAccount()
{
	var myForm=document.getElementById('searchForm');
	id_runforfindact.disabled=true;
	myForm.action="<%=request.getContextPath()%>/FindAccount.do?method=findAccount";
	myForm.submit();
	
}


function setBTEnd()
{
	var myForm=document.getElementById('searchForm');
	id_btend.disabled=true;
	myForm.action="<%=request.getContextPath()%>/FindAccount.do?method=setBTEnd";
	myForm.submit();
	
}

function runSchedular()
{
	var myForm=document.getElementById('searchForm');
	var servicecreationorderno=document.getElementById('servicecreationorderno').value;
	id_run.disabled=true;
    myForm.action="<%=request.getContextPath()%>/FXScheduler.do?method=FXScheduler&servicecreationorderno="+servicecreationorderno;
	myForm.submit();
	
}

function runSchedularForAutoBilling()
{
         var myForm=document.getElementById('searchForm');
         id_AUTO_BILLING.disabled=true;
         var var_autoOption=document.getElementById('autoOption').value;;
         myForm.action="<%=request.getContextPath()%>/FXSchedulerforAutoBilling.do?method=FXSchedulerforAutoBilling&autoOption="+var_autoOption;
	     myForm.submit();
}

function runSchedularForAutoRenewalMail()
{
         var myForm=document.getElementById('searchForm');
         id_AUTO_RENEWAL.disabled=true;
	     myForm.action="<%=request.getContextPath()%>/SchedularForAutoRenewalMail.do?method=SchedularForAutoRenewalMail";
	     myForm.submit();
}


function runSchedularForPushLocDataToFx()
{
         var myForm=document.getElementById('searchForm');
         id_pushlocdata.disabled=true;
	     myForm.action="<%=request.getContextPath()%>/pushLocDataToFx.do?method=pushLocDataToFx";
	     myForm.submit();
}
function runSchedularForAccountCreationInFx()
{
         var myForm=document.getElementById('searchForm');
         var accountcreationorderno=document.getElementById('accountcreationorderno').value;
         myForm.action="<%=request.getContextPath()%>/FXScheduler.do?method=FXSchedulerForAccountCreate&accountcreationorderno="+accountcreationorderno;
	     id_ACCOUNT_CREATION.disabled=true;
	     myForm.submit();
}

function runSchedularforRCcharges()
{
	var myForm=document.getElementById('searchForm');
	var pushrcorderno=document.getElementById('pushrcorderno').value;
	id_runforrccharges.disabled=true;
	myForm.action="<%=request.getContextPath()%>/FXSchedulerforRCCharges.do?method=FXSchedulerforRCCharges&pushrcorderno="+pushrcorderno;
	myForm.submit();
	
}
function terminateAllDbConnection()
{
	var myForm=document.getElementById('searchForm');
	id_terminateAllDbConnection.disabled=true;
	myForm.action="<%=request.getContextPath()%>/ApplicationPerformanceLog.do?method=terminateAllDbConnection";
	myForm.submit();
}
function displayAllDbConnection()
{
	var myForm=document.getElementById('searchForm');
	id_displayAllDbConnection.disabled=true;
	myForm.action="<%=request.getContextPath()%>/ApplicationPerformanceLog.do?method=displayAllDbConnection";
	myForm.submit();
}
function runSchedularforNRCcharges()
{
	var myForm=document.getElementById('searchForm');
	var pushnrcorderno=document.getElementById('pushnrcorderno').value;
	id_runfornrccharges.disabled=true;
	myForm.action="<%=request.getContextPath()%>/FXSchedulerforNRCCharges.do?method=FXSchedulerforNRCCharges&pushnrcorderno="+pushnrcorderno;
	myForm.submit();
	
}
function runSchedularForDisconnection()
{
	var myForm=document.getElementById('searchForm');
	var chargedisorderno=document.getElementById('chargedisorderno').value;
	id_runforchargesdis.disabled=true;
    myForm.action="<%=request.getContextPath()%>/FXSchedulerforDisconnection.do?method=FXSchedulerforDisconnection&chargedisorderno="+chargedisorderno;
	myForm.submit();
	
}
function runSchedularForAddressChange()
{
	var myForm=document.getElementById('searchForm');
	id_runfornrccharges.disabled=true;
	myForm.action="<%=request.getContextPath()%>/FXScheduler.do?method=FXSchedulerforAddressChange";
	myForm.submit();
	
}
function runSchedularForPackage()
{
	var myForm=document.getElementById('searchForm');
	id_runforpackcreate.disabled=true;
	var packcreationorderno=document.getElementById('packcreationorderno').value;
	myForm.action="<%=request.getContextPath()%>/FXScheduler.do?method=FXSchedulerForPackages&packcreationorderno="+packcreationorderno;
	myForm.submit();
	
}
function runSchedularForGamToFX()
{
	var myForm=document.getElementById('searchForm');
	id_gamToFX.disabled=true;
	myForm.action="<%=request.getContextPath()%>/FXScheduler.do?method=SchedularForGamToFX";
	myForm.submit();

}
function runSchedularForComponent()
{
	var myForm=document.getElementById('searchForm');
	id_runforcomcreate.disabled=true;
	var compcreationorderno=document.getElementById('compcreationorderno').value;
	myForm.action="<%=request.getContextPath()%>/FXScheduler.do?method=FXSchedulerforComponents&compcreationorderno="+compcreationorderno;
	myForm.submit();
	
}
function runSchedularForServiceDisconnection()
{
	var myForm=document.getElementById('searchForm');
	id_runforserdis.disabled=true;
	var servicedisorderno=document.getElementById('servicedisorderno').value;
	myForm.action="<%=request.getContextPath()%>/FXScheduler.do?method=FXSchedulerforServiceDisconnection&servicedisorderno="+servicedisorderno;
	myForm.submit();
	
}
function runSchedularForServiceUpdate()
{
	var myForm=document.getElementById('searchForm');
	id_runforservupdate.disabled=true;
	var serviceupdateorderno=document.getElementById('serviceupdateorderno').value;
	myForm.action="<%=request.getContextPath()%>/FXScheduler.do?method=FXSchedulerForServiceUpdate&serviceupdateorderno="+serviceupdateorderno;
	myForm.submit();
	
}
/*--sandeep*/
function runSchedularForComponentDisconnection()
{
	var myForm=document.getElementById('searchForm');
    id_runforcomdis.disabled=true;
	var comdiscorderno=document.getElementById('comdiscorderno').value;
	myForm.action="<%=request.getContextPath()%>/FXScheduler.do?method=FXSchedulerForComponentDisconnection&comdiscorderno="+comdiscorderno;
	myForm.submit(); 
}
/*Saurabh*/
function runSchedularForUsageBasedUpdate()
{
	var myForm=document.getElementById('searchForm');
	id_runforUsageBasedUpdate.disabled=true;
	myForm.action="<%=request.getContextPath()%>/FXScheduler.do?method=FXSchedulerForArborUpdate";
	myForm.submit();
	
}

function runSchedularForACSProvisioningMail()
{
	var myForm=document.getElementById('searchForm');
	id_runforACSProvisioningMail.disabled=true;
	myForm.action="<%=request.getContextPath()%>/FXScheduler.do?method=FXSchedulerForACSProvisioningMail";
	myForm.submit();
	
}
function runSchedularForAccountUpdate()
{
	var myForm=document.getElementById('searchForm');
	id_runforacctupdate.disabled=true;
	var accountupdateorderno=document.getElementById('accountupdateorderno').value;
	myForm.action="<%=request.getContextPath()%>/FXScheduler.do?method=FXSchedulerForAccountUpdate&accountupdateorderno="+accountupdateorderno;
	myForm.submit();
	
}
function runSchedularForCumulative()
{
	var myForm=document.getElementById('searchForm');
    document.getElementById('id_runforcumulative').disabled=true;	
    var cumulativeorderno=document.getElementById('cumulativeorderno').value;
	myForm.action="<%=request.getContextPath()%>/FXScheduler.do?method=FXSchedulerForCumulative&cumulativeorderno="+cumulativeorderno;
	myForm.submit(); 
}
function runSchedularForBulk()
{
	var myForm=document.getElementById('searchForm');
    document.getElementById('id_runforbulk').disabled=true;	
    var bulkId=document.getElementById('bulkId').value;
	myForm.action="<%=request.getContextPath()%>/FXScheduler.do?method=BulkUpload&bulkId="+bulkId;
	myForm.submit(); 
}

</script>
<body >
	<form action="<%=request.getContextPath()%>/FXScheduler.do" id="searchForm" method="post">
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%">
		</table>	
		<div class="head"> <span>Schedular </span> </div>
		<table width="100%"   border="0" cellspacing="0" cellpadding="0" align="center" id='tblBCPList'>
			<tr>
				<td align="center"  width="100%" valign="top">
					<table border="1" cellspacing="0" width="80%" cellpadding="0" style="margin-top:7px ">
								<tr>
									<td align="center" style="font-size:9px" width="316">Send To Schedular</td>
								</tr>
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedular();" id="id_run">Service Schedular</a></div>
									</td>
									
									   <td align="center" valign="top" width="64%">Service Create Order No : <input type="text" id="servicecreationorderno" value="<%=(request.getParameter("servicecreationorderno")==null)? "0" :request.getParameter("servicecreationorderno")%>" name="servicecreationorderno" /> (Enter 0 for all Services)    
									
									</td>
									
									
								</tr>
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularforRCcharges();" id="id_runforrccharges">RC Charges Schedular</a></div>
									</td>
									
									  <td align="center" valign="top" width="64%">RC Charge Order No : <input type="text" id="pushrcorderno" value="<%=(request.getParameter("pushrcorderno")==null)? "0" :request.getParameter("pushrcorderno")%>" name="pushrcorderno" />(Enter 0 for all RCs)    
									
									</td>
									
								</tr>	
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularforNRCcharges();" id="id_runfornrccharges">NRC Charges Schedular</a></div>
									</td>
									
									  <td align="center" valign="top" width="64%">NRC Charge Order No : <input type="text" id="pushnrcorderno" value="<%=(request.getParameter("pushnrcorderno")==null)? "0" :request.getParameter("pushnrcorderno")%>" name="pushnrcorderno" />(Enter 0 for all NRCs)      
									
									</td>
								</tr>	
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForDisconnection();" id="id_runforchargesdis">Disconnection Schedular</a></div>
									</td>
									  <td align="center" valign="top" width="64%">Charge Disconnection Order No : <input type="text" id="chargedisorderno" value="<%=(request.getParameter("chargedisorderno")==null)? "0" :request.getParameter("chargedisorderno")%>" name="chargedisorderno" />(Enter 0 for all Charges Disconnection)     
									
									</td>
								</tr>
								
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForAddressChange();" id="id_runforbcpadrchange">BCP Address Change</a></div>
									</td>
									<td align="center" style="font-size:9px;" nowrap width="64%">&nbsp;</td>
								</tr>
							
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForServiceDisconnection();" id="id_runforserdis">ServiceDisconnection</a></div>
									</td>
									  <td align="center" valign="top" width="64%">Service Disconnection Order No : <input type="text" id="servicedisorderno" value="<%=(request.getParameter("servicedisorderno")==null)? "0" :request.getParameter("servicedisorderno")%>" name="servicedisorderno" />(Enter 0 for all Service Disconnection)       
									
									</td>
								</tr>
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForServiceUpdate();" id="id_runforservupdate">ServiceUpdate</a></div>
									</td>
									<td align="center" valign="top" width="64%">Service Update Order No : <input type="text" id="serviceupdateorderno" value="<%=(request.getParameter("serviceupdateorderno")==null)? "0" :request.getParameter("serviceupdateorderno")%>" name="serviceupdateorderno" />(Enter 0 for all Service Update)         
									
									</td>
								</tr>
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForAccountUpdate();" id="id_runforacctupdate">AccountUpdate</a></div>
									</td>
									<td align="center" valign="top" width="64%">Account Update Order No : <input type="text" id="accountupdateorderno" value="<%=(request.getParameter("accountupdateorderno")==null)? "0" :request.getParameter("accountupdateorderno")%>" name="accountupdateorderno" />(Enter 0 for all Account Update)         
									
									</td>
								</tr>
								<tr>	
									<td align="left" valign="top" width="36%">
										
									Test FX Connectivity. Search By FX Account External Id: <input type="text"  name="findaccount" property="findaccount"/>   <a href="#" onclick="javascript:findAccount();" id="id_runforfindact">Find Account</a> 
									</td>
									<td width="10%"> 
										<%=request.getAttribute("accountno") %>
									</td>

								</tr>
								<tr>	
									<td align="left" valign="top" width="36%">
										
									Account Internal Id : <input type="text"  name="account_for_modify" property="account_for_modify"/>    
									
									</td>
									<td width="30%"> 
										<%=request.getAttribute("account_for_modify") %>
									</td>
										<td width="34%">				
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:fnAccount_for_modify1111();" id="id_account_for_modify">Change to Test Account</a></div>
									</td>
									<td><%if(request.getAttribute("account_modify_message")!=null) { %>
										<%=request.getAttribute("account_modify_message") %>
									<%} %>
										</td>
								</tr>
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedular_FX_SYNC();" id="id_FX_SYNC">FX Sync</a></div>
									</td>
										<td align="center" style="font-size:9px;" nowrap width="64%">&nbsp;</td>
								</tr>
									<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForAutoBilling();" id="id_AUTO_BILLING">Auto Billing Trigger (For HW Sale New Lines, GB/EGB Permanent Disconnection Orders, LEPM based, RR ), LEPM data Import</a></div>
										<input type="text" id="autoOption" value="<%=(request.getParameter("autoOption")==null)? "ALL" :request.getParameter("autoOption")%>" name="autoOption" />[ALL,PRE,BT,LOC_UPDATE,LEPM_DATA_IMPORT,LEPM_LOC_NOT_RECEIVED]
									</td>
										<td align="center" style="font-size:9px;" nowrap width="64%">&nbsp;</td>
								</tr>
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForAutoRenewalMail();" id="id_AUTO_RENEWAL">Auto Renewal Mail</a></div>
									</td>
										<td align="center" style="font-size:9px;" nowrap width="64%">&nbsp;</td>
								</tr>
								
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForUsageBasedUpdate();" id="id_runforUsageBasedUpdate">FX_SI_Id Updation , ACS/95P Partial Billing Trigger</a></div>
									</td>
										<td align="center" style="font-size:9px;" nowrap width="64%">&nbsp;</td>
								</tr>
									
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForPushLocDataToFx();" id="id_pushlocdata">Push LOC Data to FX for Auto Billing Trigger Cases</a></div>
									</td>
										<td align="center" style="font-size:9px;" nowrap width="64%">&nbsp;</td>
								</tr>
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForAccountCreationInFx();" id="id_ACCOUNT_CREATION">Account Creation In FX</a></div>
									</td>
									
							        <td align="center" valign="top" width="64%">
										
										Account Creation Order No : <input type="text" id="accountcreationorderno" value="<%=(request.getParameter("accountcreationorderno")==null)? "0" :request.getParameter("accountcreationorderno")%>" name="accountcreationorderno" />(Enter 0 for all Accounts)     
									
									</td>
									
								</tr>
								<tr>	
									<td align="left" valign="top" width="36%">
										
									IB2B Order No : <input type="text"  name="orderno" />    
									
									</td>
									<td width="558"> 
										<%=request.getAttribute("orderno") %>
									</td>
									<td>			
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:setBTEnd();" id="id_btend">Set BT End</a></div>
									</td>
									</tr>
								<tr>	
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForPackage();" id="id_runforpackcreate">Package Create</a></div>
									</td>
									 <td align="center" valign="top" width="64%">
										
										Package Creation Order No : <input type="text" value="<%=(request.getParameter("packcreationorderno")==null)? "0" :request.getParameter("packcreationorderno")%>" id="packcreationorderno" name="packcreationorderno" />(Enter 0 for all Packages)     
									
									</td>
								</tr>
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForComponent();" id="id_runforcomcreate">Component Create</a></div>
									</td>
								 <td align="center" valign="top" width="64%">
										
										Component Creation Order No : <input type="text" value="<%=(request.getParameter("compcreationorderno")==null)? "0" :request.getParameter("compcreationorderno")%>" id="compcreationorderno" name="compcreationorderno" />(Enter 0 for all Components)     
									
									</td>
								</tr>
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForGamToFX();" id="id_gamToFX">GAM To FX</a></div>
									</td>
									<td align="center" style="font-size:9px;" nowrap width="64%">&nbsp;</td>
								</tr>
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForComponentDisconnection();" id="id_runforcomdis">CptDisconn</a></div>
									</td>
									 <td align="center" valign="top" width="64%">
										
										Component Disconnection Order No : <input type="text" value="<%=(request.getParameter("comdiscorderno")==null)? "0" :request.getParameter("comdiscorderno")%>" id="comdiscorderno" name="comdiscorderno" /> (Enter 0 for all Components Disc.)    
									
									</td>
								</tr>
								<tr>
									<td align="center" valign="top" width="36%">
										<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForACSProvisioningMail();" id="id_runforACSProvisioningMail">ACS Mail Provisioning</a></div>
									</td>
								</tr>
								<tr>
									<td align="center" valign="top" width="36%">
												<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForChargeRedirection();" id="id_runforchargeredirect">Charge Redirection</a></div>
									</td>
								
								</tr>
								<tr>
									<td align="center" valign="top" width="36%">
												<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForCumulative();" id="id_runforcumulative">CumulativeTo FX</a></div>
									</td>
									 <td align="center" valign="top" width="64%">
										
										Cumulative Order No : <input type="text" id="cumulativeorderno" value="<%=(request.getParameter("cumulativeorderno")==null)? "0" :request.getParameter("cumulativeorderno")%>" name="cumulativeorderno" />(Enter 0 for All)     
									
									</td>
								</tr>
								<tr>
									<td align="center" valign="top" width="36%">
												<div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:runSchedularForBulk();" id="id_runforbulk">Bulk</a></div>
									</td>
									 <td align="center" valign="top" width="64%">
										
										Schedular Id : <input type="text" id="bulkId" value="<%=(request.getParameter("bulkId")==null)? "0" :request.getParameter("bulkId")%>" name="bulkId" />(Enter 1 for Suspension,2 for resump,3 for PD , 4 for BT,7 for CT)     
									
									</td>
								</tr>
								
					</table>
					<hr/>
					<p><b>Application Utilities</b></p>
					<hr/>
					
					<table border="0" cellspacing="0" cellpadding="0" style="margin-top:7px ">
						<tr>
							<td>Minutes :<input type="text"  name="minutes" value='<%=(String)request.getParameter("minutes") %>'/> </td>
							<td>Stack Trace Count :<input type="text"  name="stc" value='<%=(request.getParameter("stc")==null)?"8":(String)request.getParameter("stc") %>'/> </td>							
							<td><div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:terminateAllDbConnection();" id="id_terminateAllDbConnection">Terminate All DB Connections</a></div></td>
							<td><div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:displayAllDbConnection();" id="id_displayAllDbConnection">Display All DB Connections</a></div></td>
						</tr>
					</table>
					<hr/>
					<p><b>Application Flags From ApplicationFlags Class</b></p>
					<hr/>
					<table border="1" cellspacing="0" cellpadding="0" style="margin-top:7px ">
						<tr>
							<td>Flag Name</td>
							<td>Current Value </td>							
							<td>New Value</td>
							<td>Action</td>
						</tr>
						<tr>
							<td>IB2B_DB_CONNECTION_LOGGER_FLAG</td>
							<td><%=ApplicationFlags.IB2B_DB_CONNECTION_LOGGER_FLAG%> </td>							
							<td><input type="text" name="IB2B_DB_CONNECTION_LOGGER_FLAG_NEW_VALUE"></td>
							<td><div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:change_IB2B_DB_CONNECTION_LOGGER_FLAG();" id="id_IB2B_DB_CONNECTION_LOGGER_FLAG">Change</a></div></td>
						</tr>
						<tr>
							<td>IB2B_FX_TEST_ACCOUNT_CREATION</td>
							<td><%=ApplicationFlags.IB2B_FX_TEST_ACCOUNT_CREATION%> </td>							
							<td><input type="text" name="IB2B_FX_TEST_ACCOUNT_CREATION_NEW_VALUE"></td>
							<td><div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:change_IB2B_FX_TEST_ACCOUNT_CREATION();" id="id_IB2B_FX_TEST_ACCOUNT_CREATION">Change</a></div></td>
						</tr>
						<tr>
							<td>ReportsExcelMaxSize</td>
							<td><%=ApplicationFlags.ReportsExcelMaxSize%> </td>							
							<td><input type="text" name="ReportsExcelMaxSize"></td>
							<td><div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:change_ReportsExcelMaxSize();" id="id_ReportsExcelMaxSize">Change</a></div></td>
						</tr>
						<tr>
							<td>IB2B_ENVIRONMENT</td>
							<td><%=ApplicationFlags.IB2B_ENVIRONMENT%> </td>							
							<td><input type="text" name="IB2B_ENVIRONMENT_NEW_VALUE"></td>
							<td>Value Changing Not Allowed</td>
						</tr>
					</table>
					<hr/>
					<p><b>DB Password Encryption</b></p>
					<hr/>
					<table border="1" cellspacing="0" cellpadding="0" style="margin-top:7px ">
					<tr>
					<td align="right">Enter Password :</td>
					<td><input type="text" name="IB2B_FX_DB_PASSWORD_CREATION_NEW_VALUE"></td>
					</tr>
					<tr>
					<td align="right">Encrypted Password :</td>
					<td><input type="text" readonly name="IB2B_FX_DB_PASSWORD_ENCRYPTED_VALUE"></td>
					</tr>
					<tr>
					<td></td>
					<td><div class="searchBg" style="margin-right:5px;"><a href="#" onclick="javascript:change_IB2B_FX_DB_PASSWORD_CREATION();" id="id_IB2B_FX_DB_PASSWORD_CREATION">Encrypt</a></div></td>
					</tr>
					</table>
				</td>	
			</tr>
		</table>
	</form>
</body>

</html>
