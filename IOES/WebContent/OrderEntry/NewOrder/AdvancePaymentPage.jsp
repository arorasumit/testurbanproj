<!-- [001]Pankaj Thakur  10-april-2015 added a condition to make Advance Payment page non-editable  in case of viewMode -->


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--[001] SANTOSH SRIVASTAVA,  THIS PAGE IS USED FOR ADVANCE PAYMENT DETAIL INFORMATION -->
<!-- [002] Raveendra  12/02/2015   add new field for refund process -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.forms.AdvancePaymentDTO"%>
<html>
<head>
<title>Advance Payment Details</title>

<link href="gifs/treeview.css" type="text/css" rel="stylesheet">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="/IOES/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="/IOES/js/calendar.js"></script>
<script type="text/javascript" src="/IOES/js/PagingSorting.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery-latest.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<!-- [002] added to disable copy & Paste :: oncopy="return false" oncut="return false" onpaste="return false"-->
<body onload="fetchAdvancePaymentdetails();" oncopy="return false" oncut="return false" onpaste="return false" >

<script type="text/javascript" src="js/formValidations.js"></script>
<script type="text/javascript" src="/IOES/js/wz_tooltip.js"></script>

<script type="text/javascript">

	<%
		HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
		HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
		UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
	%>

	var roleName = <%=objUserDto.getUserRoleId() %>;
	var userId = "<%=objUserDto.getUserId() %>";
	var roleId = <%=objUserDto.getUserRoleId() %>;

	var advPaymentDTO = null;
	var isView = "<%=request.getParameter("isView")%>";
	//USED TO SAVE ADVANCE PAYMENT DETAILS FOR ARS AND OTS SECTION
	function saveAdvancePaymentDetails() 
	{
		if(isView != 'null') 
		{
			alert("Can't Update Payment Details");
			return false;
		}
		if(document.getElementById('arcAdvancePaymentExemptd').value=="Yes")
		{
		 	document.getElementById('arcCheckNumber').value='';
		 	document.getElementById('arcCheckDate').value='';
		 	document.getElementById('arcBankName').value='';
		 	document.getElementById('arcCheckamount').value=0;
		 	document.getElementById('arcAudjestedAmount').value=0;
		 	//Start [002]
		 	document.getElementById('arcReEnterCheckNumber').value='';
		 	document.getElementById('arcReEnterCheckamount').value=0;
		 	document.getElementById('arcBankAccountNumber').value='';
		 	document.getElementById('arcReEnterBankAccountNumber').value='';
		 	document.getElementById('arcIfscCode').value='';
		 	document.getElementById('arcReEnterIfscCode').value='';
		 	//End [002]
		 	
		 	
		}
		else 
		{
			if(Number(document.getElementById('arcCheckamount').value) <= Number(document.getElementById('arcAudjestedAmount').value))
		 	{
				alert("ARC check amount shouldn't be less than ARC audjested amount");
				advaceTransForm.arcAudjestedAmount.focus();
				return false;
			} 
		}
		 
		if(document.getElementById('otcAdvancPaymentExemptd').value=="Yes")
		{
		 	document.getElementById('otcCheckNumber').value='';
		 	document.getElementById('otcCheckDate').value='';
		 	document.getElementById('otcBankName').value='';
		 	document.getElementById('otcCheckamount').value=0;
		 	document.getElementById('otcAudjestedAmount').value=0;
		 	
		 	//Start [002]
		 	document.getElementById('otcReEnterCheckNumber').value='';
		 	document.getElementById('otcReEnterCheckamount').value=0;
		 	document.getElementById('otcBankAccountNumber').value='';
		 	document.getElementById('otcReEnterBankAccountNumber').value='';
		 	document.getElementById('otcIfscCode').value='';
		 	document.getElementById('otcReEnterIfscCode').value='';
		 	//End [002]
		 	
		}
		else
		{
		 	if(Number(document.getElementById('otcCheckamount').value) <= Number(document.getElementById('otcAudjestedAmount').value))
		 	{
				alert("OTC check amount shouldn't be less than OTC audjested amount");
				advaceTransForm.otcAudjestedAmount.focus();
				return false;
			}
		}
				
		var jsData =
		{
		 	arcAdvancePaymentExemptd:document.getElementById('arcAdvancePaymentExemptd').value,
		 	arcExemptionReason:document.getElementById('arcExemptionReason').value,
		 	arcCheckNumber:document.getElementById('arcCheckNumber').value,
		 	arcCheckDate:document.getElementById('arcCheckDate').value,
		 	arcBankName:document.getElementById('arcBankName').value,
		 	arcCheckamount:document.getElementById('arcCheckamount').value,
		 	arcAudjestedAmount:document.getElementById('arcAudjestedAmount').value,
		 	//Start [002]
		 	arcReEnterCheckNumber:document.getElementById('arcReEnterCheckNumber').value,
		 	arcReEnterCheckamount:document.getElementById('arcReEnterCheckamount').value,
		 	arcBankAccountNumber:document.getElementById('arcBankAccountNumber').value,
		 	arcReEnterBankAccountNumber:document.getElementById('arcReEnterBankAccountNumber').value,
		 	arcIfscCode:document.getElementById('arcIfscCode').value,
		 	arcReEnterIfscCode:document.getElementById('arcReEnterIfscCode').value,
		 	//End [002]
		 		 	
		 	otcAdvancPaymentExemptd:document.getElementById('otcAdvancPaymentExemptd').value,
		 	otcExemptdReason:document.getElementById('otcExemptdReason').value,
		 	otcCheckNumber:document.getElementById('otcCheckNumber').value,
		 	otcCheckDate:document.getElementById('otcCheckDate').value,
		 	otcCheckamount:document.getElementById('otcCheckamount').value,
		 	otcBankName:document.getElementById('otcBankName').value,
		 	otcAudjestedAmount:document.getElementById('otcAudjestedAmount').value,
		 	
		 	//Start [002]
		 	otcReEnterCheckNumber:document.getElementById('otcReEnterCheckNumber').value,
		 	otcReEnterCheckamount:document.getElementById('otcReEnterCheckamount').value,
		 	otcBankAccountNumber:document.getElementById('otcBankAccountNumber').value,
		 	otcReEnterBankAccountNumber:document.getElementById('otcReEnterBankAccountNumber').value,
		 	otcIfscCode:document.getElementById('otcIfscCode').value,
		 	otcReEnterIfscCode:document.getElementById('otcReEnterIfscCode').value,
		 	//End [002]
		 	
		 	
		 	orderNo:document.getElementById('orderNo').value,
		 	serviceNo:document.getElementById('serviceNo').value,
		 	lineItemNo:document.getElementById('lineItemNo').value,
		 	advanceId:document.getElementById('advanveServiceId').value
		 };
		 
		 	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			advPaymentDTO = jsonrpc.processes.setAdvancePaymentDetails(jsData);
			document.getElementById('advanveServiceId').value=advPaymentDTO.advanceId;
	
			var errorVal = advPaymentDTO.errorStatus;
		
			if(errorVal == 0)
    		{
    			alert(advPaymentDTO.errorMsg);
    		}  
    		else 
    		{
    			alert(advPaymentDTO.errorMsg);
    		}
	}
	
	
	function closeCurrentPage() {
	
		var x;
		var r=confirm("Do you want to close this page?");
		if (r==true)
  		{
  			x="OK";
  			window.close();
  			return true;
 		 }
			else
  			{
  				x="Cancel";
  				return false;
  			}
			document.getElementById("closeButtonId").innerHTML=x;
		
	}
	
	//Fetch advance payment details on hel[per Id base
	function fetchAdvancePaymentdetails() 
	{
		if(isView == 'null') 
		{
			document.getElementById('saveImage').disabled = false;
		}
		else
		{
			document.getElementById('saveImage').disabled = true;
		}
		var jsData =
	
		{
			serviceNo:document.getElementById('serviceNo').value,
	 		lineItemNo:document.getElementById('lineItemNo').value
		};
		
			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			var advancePaymentDTO = jsonrpc.processes.getAdvancePaymentDetails(jsData);
			document.getElementById('advanveServiceId').value = advancePaymentDTO.advanceId;
			

			if(document.getElementById('advanveServiceId').value!=0)
			{
				var arcSelectOption = document.getElementById('arcAdvancePaymentExemptd').value = advancePaymentDTO.arcAdvancePaymentExemptd;
				
				if(arcSelectOption == "Yes") 
				{
					document.getElementById('arcExemptionReason').value = advancePaymentDTO.arcExemptionReason;
					document.getElementById('arcExemptionReason').readOnly = false;
					var arcExempReason = document.getElementById('arcExemptionReason');
					arcExempReason.className = "inputBorder1";
					
					document.getElementById('arcCheckNumber').readOnly = true;
					var arcNum = document.getElementById('arcCheckNumber');
					arcNum.className = "inputDisabled";
							
					document.getElementById('arcCheckDate').desabled = true;
					var arcDate = document.getElementById('arcCheckDate');
					arcDate.className = "inputDisabled";
					
					document.getElementById('arcBankName').readOnly = true;
					var arcBank = document.getElementById('arcBankName');
					arcBank.className = "inputDisabled";
					
					document.getElementById('arcCheckamount').readOnly = true;
					var arcChkAmt = document.getElementById('arcCheckamount');
					arcChkAmt.className = "inputDisabled";
					
					document.getElementById('arcAudjestedAmount').readOnly = true;
					var adjAmount = document.getElementById('arcAudjestedAmount');
					adjAmount.className = "inputDisabled";
					
					//Start [002]
				 	document.getElementById('arcReEnterCheckNumber').readOnly=true;
				 	var arcReEnterCheckNumber= document.getElementById('arcReEnterCheckNumber');
				 	arcReEnterCheckNumber.className="inputDisabled";
				 	
				 	document.getElementById('arcBankAccountNumber').readOnly=true;
				 	var arcBankAccountNumber= document.getElementById('arcBankAccountNumber');
				 	arcBankAccountNumber.className="inputDisabled";
				 	
				 	document.getElementById('arcReEnterBankAccountNumber').readOnly=true;
				 	var arcReEnterBankAccountNumber= document.getElementById('arcReEnterBankAccountNumber');
				 	arcReEnterBankAccountNumber.className="inputDisabled";
				 	
				 	document.getElementById('arcReEnterCheckamount').readOnly=true;
				 	var arcReEnterCheckamount= document.getElementById('arcReEnterCheckamount');
				 	arcReEnterCheckamount.className="inputDisabled";
				 	
				 	document.getElementById('arcIfscCode').readOnly=true;
				 	var arcIfscCode= document.getElementById('arcIfscCode');
				 	arcIfscCode.className="inputDisabled";
				 	
				 	document.getElementById('arcReEnterIfscCode').readOnly=true;
				 	var arcIfscCode= document.getElementById('arcReEnterIfscCode');
				 	arcIfscCode.className="inputDisabled";
				 	//End [002]
										
					document.getElementById('arcBankName').value = '';
					document.getElementById('arcCheckDate').value = '';
					document.getElementById('arcCheckNumber').value = '';
					document.getElementById('arcCheckamount').value = '';
					document.getElementById('arcAudjestedAmount').value = '';
					//Start [002]
				 	document.getElementById('arcReEnterCheckNumber').value='';
				 	document.getElementById('arcReEnterCheckamount').value='';
				 	document.getElementById('arcBankAccountNumber').value='';
				 	document.getElementById('arcReEnterBankAccountNumber').value='';
				 	document.getElementById('arcIfscCode').value='';
				 	document.getElementById('arcReEnterIfscCode').value='';
				 	//End [002]
				}
				if(arcSelectOption == "No") 
				{	
					document.getElementById('arcExemptionReason').value = '';	
					document.getElementById('arcBankName').value = advancePaymentDTO.arcBankName;
					document.getElementById('arcCheckDate').value = advancePaymentDTO.arcCheckDate;
					document.getElementById('arcCheckNumber').value = advancePaymentDTO.arcCheckNumber;
					document.getElementById('arcCheckamount').value = advancePaymentDTO.arcCheckamount;
					document.getElementById('arcAudjestedAmount').value = advancePaymentDTO.arcAudjestedAmount;
					
					//Start [002]
				 	document.getElementById('arcReEnterCheckNumber').value = advancePaymentDTO.arcReEnterCheckNumber;
				 	document.getElementById('arcReEnterCheckamount').value = advancePaymentDTO.arcReEnterCheckamount;
				 	document.getElementById('arcBankAccountNumber').value = advancePaymentDTO.arcBankAccountNumber;
				 	document.getElementById('arcReEnterBankAccountNumber').value = advancePaymentDTO.arcReEnterBankAccountNumber;
				 	document.getElementById('arcIfscCode').value = advancePaymentDTO.arcIfscCode;
				 	document.getElementById('arcReEnterIfscCode').value = advancePaymentDTO.arcReEnterIfscCode;
				 	//End [002]
					
					document.getElementById('arcExemptionReason').readOnly = true;
					var arcExempReason = document.getElementById('arcExemptionReason');
					arcExempReason.className = "inputDisabled";
					document.getElementById('arcExemptionReason').value = '';
					
					document.getElementById('arcCheckNumber').readOnly = false;
					var arcNum = document.getElementById('arcCheckNumber');
					arcNum.className = "inputBorder1";
					
					document.getElementById('arcCheckDate').disabled = false;
					var arcDate = document.getElementById('arcCheckDate');
					arcDate.className = "inputBorder1";
					
					document.getElementById('arcBankName').readOnly = false;
					var arcBank = document.getElementById('arcBankName');
					arcBank.className = "inputBorder1";
					
					document.getElementById('arcCheckamount').readOnly = false;
					var arcChkAmt = document.getElementById('arcCheckamount');
					arcChkAmt.className = "inputBorder1";
					
					document.getElementById('arcAudjestedAmount').readOnly = false;
					var adjAmount = document.getElementById('arcAudjestedAmount');
					adjAmount.className = "inputBorder1";
					
					//Start [002]
				 	document.getElementById('arcReEnterCheckNumber').readOnly=false;
				 	var arcReEnterCheckNumber= document.getElementById('arcReEnterCheckNumber');
				 	arcReEnterCheckNumber.className="inputBorder1";
				 	
				 	document.getElementById('arcReEnterCheckamount').readOnly=false;
				 	var arcBankAccountNumber= document.getElementById('arcReEnterCheckamount');
				 	arcBankAccountNumber.className="inputBorder1";
				 	
				 	
				 	document.getElementById('arcBankAccountNumber').readOnly=false;
				 	var arcBankAccountNumber= document.getElementById('arcBankAccountNumber');
				 	arcBankAccountNumber.className="inputBorder1";
				 	
				 	document.getElementById('arcReEnterBankAccountNumber').readOnly=false;
				 	var arcReEnterBankAccountNumber= document.getElementById('arcReEnterBankAccountNumber');
				 	arcReEnterBankAccountNumber.className="inputBorder1";
				 	
				 	document.getElementById('arcIfscCode').readOnly=false;
				 	var arcIfscCode= document.getElementById('arcIfscCode');
				 	arcIfscCode.className="inputBorder1";
				 	
				 	document.getElementById('arcReEnterIfscCode').readOnly=false;
				 	var arcReEnterIfscCode= document.getElementById('arcReEnterIfscCode');
				 	arcReEnterIfscCode.className="inputBorder1";
				 	//End [002]
					
					
				}
				var otcSelectOtion = document.getElementById('otcAdvancPaymentExemptd').value = advancePaymentDTO.otcAdvancPaymentExemptd;
				if(otcSelectOtion == "Yes") 
				{
					document.getElementById('otcExemptdReason').value = advancePaymentDTO.otcExemptdReason;
					document.getElementById('otcExemptdReason').readOnly = false;
					var otcExempReason = document.getElementById('otcExemptdReason');
					otcExempReason.className = "inputBorder1";
					
					document.getElementById('otcCheckNumber').readOnly = true;
					var otcNum = document.getElementById('otcCheckNumber');
					otcNum.className = "inputDisabled";
					
					document.getElementById('otcCheckDate').desabled = true;
					var otcDate = document.getElementById('otcCheckDate');
					otcDate.className = "inputDisabled";
					
					document.getElementById('otcBankName').readOnly = true;
					var otcBank = document.getElementById('otcBankName');
					otcBank.className = "inputDisabled";
					
					document.getElementById('otcCheckamount').readOnly = true;
					var otcChkAmt = document.getElementById('otcCheckamount');
					otcChkAmt.className = "inputDisabled";
					
					document.getElementById('otcAudjestedAmount').readOnly = true;
					var otcAdjAmount = document.getElementById('otcAudjestedAmount');
					otcAdjAmount.className = "inputDisabled";
					
					//Start [002]
				 	document.getElementById('otcReEnterCheckNumber').readOnly = true;
				 	var otcReEnterCheckNumber= document.getElementById('otcReEnterCheckNumber');
				 	otcReEnterCheckNumber.className="inputDisabled";
				 	
				 	document.getElementById('otcReEnterCheckamount').readOnly = true;
				 	var otcReEnterCheckamount= document.getElementById('otcReEnterCheckamount');
				 	otcReEnterCheckamount.className="inputDisabled";
				 	
				 	document.getElementById('otcBankAccountNumber').readOnly = true;
				 	var otcBankAccountNumber= document.getElementById('otcBankAccountNumber');
				 	otcBankAccountNumber.className="inputDisabled";
				 	
				 	document.getElementById('otcReEnterBankAccountNumber').readOnly = true;
				 	var otcReEnterBankAccountNumber= document.getElementById('otcReEnterBankAccountNumber');
				 	otcReEnterBankAccountNumber.className="inputDisabled";
				 	
				 	document.getElementById('otcIfscCode').readOnly = true;
				 	var otcIfscCode= document.getElementById('otcIfscCode');
				 	otcIfscCode.className="inputDisabled";
				 	
				 	document.getElementById('otcReEnterIfscCode').readOnly = true;
				 	var otcReEnterIfscCode= document.getElementById('otcReEnterIfscCode');
				 	otcReEnterIfscCode.className="inputDisabled";
				 	//End [002]
					
					
					
					document.getElementById('otcBankName').value =  '';
					document.getElementById('otcCheckDate').value =  '';
					document.getElementById('otcCheckNumber').value =  '';
					document.getElementById('otcCheckamount').value =  '';
					document.getElementById('otcAudjestedAmount').value =  '';
					
					
					//Start [002]
				 	document.getElementById('otcReEnterCheckNumber').value='';
				 	document.getElementById('otcReEnterCheckamount').value='';
				 	document.getElementById('otcBankAccountNumber').value='';
				 	document.getElementById('otcReEnterBankAccountNumber').value='';
				 	document.getElementById('otcIfscCode').value='';
				 	document.getElementById('otcReEnterIfscCode').value='';
				 	//End [002]
				 	
				}
				
				if(otcSelectOtion == "No") 
				{
					document.getElementById('otcExemptdReason').value = '';	
					document.getElementById('otcBankName').value = advancePaymentDTO.otcBankName;
					document.getElementById('otcCheckDate').value = advancePaymentDTO.otcCheckDate;
					document.getElementById('otcCheckNumber').value = advancePaymentDTO.otcCheckNumber;
					document.getElementById('otcCheckamount').value = advancePaymentDTO.otcCheckamount;
					document.getElementById('otcAudjestedAmount').value = advancePaymentDTO.otcAudjestedAmount;
					
					//Start [002]
				 	document.getElementById('otcReEnterCheckNumber').value= advancePaymentDTO.otcReEnterCheckNumber;
				 	document.getElementById('otcReEnterCheckamount').value= advancePaymentDTO.otcReEnterCheckamount;
				 	document.getElementById('otcBankAccountNumber').value= advancePaymentDTO.otcBankAccountNumber;
				 	document.getElementById('otcReEnterBankAccountNumber').value= advancePaymentDTO.otcReEnterBankAccountNumber;
				 	document.getElementById('otcIfscCode').value= advancePaymentDTO.otcIfscCode;
				 	document.getElementById('otcReEnterIfscCode').value= advancePaymentDTO.otcReEnterIfscCode;
				 	//End [002]
				 	
					
					document.getElementById('otcExemptdReason').readOnly = true;
					var otcExemptdReason = document.getElementById('otcExemptdReason');
					otcExemptdReason.className = "inputDisabled";
					document.getElementById('otcExemptdReason').value = '';
					
					document.getElementById('otcCheckNumber').readOnly = false;
					var otcNum = document.getElementById('otcCheckNumber');
					otcNum.className = "inputBorder1";
					
					document.getElementById('otcCheckDate').disabled = false;
					var otcDate = document.getElementById('otcCheckDate');
					otcDate.className = "inputBorder1";
					
					document.getElementById('otcBankName').readOnly = false;
					var otcBank = document.getElementById('otcBankName');
					otcBank.className = "inputBorder1";
					
					document.getElementById('otcCheckamount').readOnly = false;
					var otcChkAmt = document.getElementById('otcCheckamount');
					otcChkAmt.className = "inputBorder1";
					
					document.getElementById('otcAudjestedAmount').readOnly = false;
					var otcAdjAmount = document.getElementById('otcAudjestedAmount');
					otcAdjAmount.className = "inputBorder1";
					
					//Start [002]
				 	document.getElementById('otcReEnterCheckNumber').readOnly=false;
				 	var otcReEnterCheckNumber= document.getElementById('otcReEnterCheckNumber');
				 	otcReEnterCheckNumber.className="inputBorder1";
				 	
				 	document.getElementById('otcReEnterCheckamount').readOnly=false;
				 	var otcReEnterCheckamount= document.getElementById('otcReEnterCheckamount');
				 	otcReEnterCheckamount.className="inputBorder1";
				 	
				 	document.getElementById('otcBankAccountNumber').readOnly=false;
				 	var otcBankAccountNumber= document.getElementById('otcBankAccountNumber');
				 	otcBankAccountNumber.className="inputBorder1";
				 	
				 	document.getElementById('otcReEnterBankAccountNumber').readOnly=false;
				 	var otcReEnterBankAccountNumber= document.getElementById('otcReEnterBankAccountNumber');
				 	otcReEnterBankAccountNumber.className="inputBorder1";
				 	
				 	document.getElementById('otcIfscCode').readOnly=false;
				 	var otcIfscCode= document.getElementById('otcIfscCode');
				 	otcIfscCode.className="inputBorder1";
				 	
				 	document.getElementById('otcReEnterIfscCode').readOnly=false;
				 	var otcReEnterIfscCode= document.getElementById('otcReEnterIfscCode');
				 	otcReEnterIfscCode.className="inputBorder1";
				 	//End [002]
					
					
					
				}	
			}
			else
			{
				selectOnPageLoad();
			}

	}
	//THIS METHOD WILL SELECT "NO" OPTION ON LOAD TIME BY DEFAULT
	function selectOnPageLoad() {
	
	
			// ARC selection
			document.getElementById('arcExemptionReason').readOnly = true;
			var arcExempReason = document.getElementById('arcExemptionReason');
			arcExempReason.className = "inputDisabled";
			document.getElementById('arcExemptionReason').value = '';
			
			document.getElementById('arcCheckNumber').readOnly = false;
			var arcNum = document.getElementById('arcCheckNumber');
			arcNum.className = "inputBorder1";
			
			document.getElementById('arcCheckDate').disabled = false;
			var arcDate = document.getElementById('arcCheckDate');
			arcDate.className = "inputBorder1";
			
			document.getElementById('arcBankName').readOnly = false;
			var arcBank = document.getElementById('arcBankName');
			arcBank.className = "inputBorder1";
			
			document.getElementById('arcCheckamount').readOnly = false;
			var arcChkAmt = document.getElementById('arcCheckamount');
			arcChkAmt.className = "inputBorder1";
			
			document.getElementById('arcAudjestedAmount').readOnly = false;
			var adjAmount = document.getElementById('arcAudjestedAmount');
			adjAmount.className = "inputBorder1";
			
			
			//Start [002]
		 	document.getElementById('arcReEnterCheckNumber').readOnly=false;
		 	var arcReEnterCheckNumber= document.getElementById('arcReEnterCheckNumber');
		 	arcReEnterCheckNumber.className="inputBorder1";
		 	
		 	document.getElementById('arcReEnterCheckamount').readOnly=false;
		 	var arcBankAccountNumber= document.getElementById('arcReEnterCheckamount');
		 	arcBankAccountNumber.className="inputBorder1";
		 	
		 	
		 	document.getElementById('arcBankAccountNumber').readOnly=false;
		 	var arcBankAccountNumber= document.getElementById('arcBankAccountNumber');
		 	arcBankAccountNumber.className="inputBorder1";
		 	
		 	document.getElementById('arcReEnterBankAccountNumber').readOnly=false;
		 	var arcReEnterBankAccountNumber= document.getElementById('arcReEnterBankAccountNumber');
		 	arcReEnterBankAccountNumber.className="inputBorder1";
		 	
		 	document.getElementById('arcIfscCode').readOnly=false;
		 	var arcIfscCode= document.getElementById('arcIfscCode');
		 	arcIfscCode.className="inputBorder1";
		 	
		 	document.getElementById('arcReEnterIfscCode').readOnly=false;
		 	var arcReEnterIfscCode= document.getElementById('arcReEnterIfscCode');
		 	arcReEnterIfscCode.className="inputBorder1";
		 	//End [002]
			
			
			//OTC selection
			
			document.getElementById('otcExemptdReason').readOnly = true;
			var otcExemptdReason = document.getElementById('otcExemptdReason');
			otcExemptdReason.className = "inputDisabled";
			document.getElementById('otcExemptdReason').value = '';
			
			document.getElementById('otcCheckNumber').readOnly = false;
			var otcNum = document.getElementById('otcCheckNumber');
			otcNum.className = "inputBorder1";
			
			document.getElementById('otcCheckDate').disabled = false;
			var otcDate = document.getElementById('otcCheckDate');
			otcDate.className = "inputBorder1";
			
			document.getElementById('otcBankName').readOnly = false;
			var otcBank = document.getElementById('otcBankName');
			otcBank.className = "inputBorder1";
			
			document.getElementById('otcCheckamount').readOnly = false;
			var otcChkAmt = document.getElementById('otcCheckamount');
			otcChkAmt.className = "inputBorder1";
			
			document.getElementById('otcAudjestedAmount').readOnly = false;
			var otcAdjAmount = document.getElementById('otcAudjestedAmount');
			otcAdjAmount.className = "inputBorder1";
			
			//Start [002]
		 	document.getElementById('otcReEnterCheckNumber').readOnly=false;
		 	var otcReEnterCheckNumber= document.getElementById('otcReEnterCheckNumber');
		 	otcReEnterCheckNumber.className="inputBorder1";
		 	
		 	document.getElementById('otcReEnterCheckamount').readOnly=false;
		 	var otcReEnterCheckamount= document.getElementById('otcReEnterCheckamount');
		 	otcReEnterCheckamount.className="inputBorder1";
		 	
		 	document.getElementById('otcBankAccountNumber').readOnly=false;
		 	var otcBankAccountNumber= document.getElementById('otcBankAccountNumber');
		 	otcBankAccountNumber.className="inputBorder1";
		 	
		 	document.getElementById('otcReEnterBankAccountNumber').readOnly=false;
		 	var otcReEnterBankAccountNumber= document.getElementById('otcReEnterBankAccountNumber');
		 	otcReEnterBankAccountNumber.className="inputBorder1";
		 	
		 	document.getElementById('otcIfscCode').readOnly=false;
		 	var otcIfscCode= document.getElementById('otcIfscCode');
		 	otcIfscCode.className="inputBorder1";
		 	
		 	document.getElementById('otcReEnterIfscCode').readOnly=false;
		 	var otcReEnterIfscCode= document.getElementById('otcReEnterIfscCode');
		 	otcReEnterIfscCode.className="inputBorder1";
		 	//End [002]
	
			
	
	}
	//THIS METHOD ID USED TO SELECT OPTION AT RUN TIME FOR ARC SECTION		
	function arcSelectionOperation() {
		
		var arcSelectedVal = document.getElementById('arcAdvancePaymentExemptd').value;
		
		if(arcSelectedVal == "Yes") {

			document.getElementById('arcExemptionReason').readOnly = false;
			var arcExempReason = document.getElementById('arcExemptionReason');
			arcExempReason.className = "inputBorder1";
			
			document.getElementById('arcCheckNumber').readOnly = true;
			var arcNum = document.getElementById('arcCheckNumber');
			arcNum.className = "inputDisabled";
					
			document.getElementById('arcCheckDate').desabled = true;
			var arcDate = document.getElementById('arcCheckDate');
			arcDate.className = "inputDisabled";
			
			document.getElementById('arcBankName').readOnly = true;
			var arcBank = document.getElementById('arcBankName');
			arcBank.className = "inputDisabled";
			
			document.getElementById('arcCheckamount').readOnly = true;
			var arcChkAmt = document.getElementById('arcCheckamount');
			arcChkAmt.className = "inputDisabled";
			
			document.getElementById('arcAudjestedAmount').readOnly = true;
			var adjAmount = document.getElementById('arcAudjestedAmount');
			adjAmount.className = "inputDisabled";
			
			//Start [002]
		 	document.getElementById('arcReEnterCheckNumber').readOnly=true;
		 	var arcReEnterCheckNumber= document.getElementById('arcReEnterCheckNumber');
		 	arcReEnterCheckNumber.className="inputDisabled";
		 	
		 	document.getElementById('arcBankAccountNumber').readOnly=true;
		 	var arcBankAccountNumber= document.getElementById('arcBankAccountNumber');
		 	arcBankAccountNumber.className="inputDisabled";
		 	
		 	document.getElementById('arcReEnterBankAccountNumber').readOnly=true;
		 	var arcReEnterBankAccountNumber= document.getElementById('arcReEnterBankAccountNumber');
		 	arcReEnterBankAccountNumber.className="inputDisabled";
		 	
		 	document.getElementById('arcReEnterCheckamount').readOnly=true;
		 	var arcReEnterCheckamount= document.getElementById('arcReEnterCheckamount');
		 	arcReEnterCheckamount.className="inputDisabled";
		 	
		 	document.getElementById('arcIfscCode').readOnly=true;
		 	var arcIfscCode= document.getElementById('arcIfscCode');
		 	arcIfscCode.className="inputDisabled";
		 	
		 	document.getElementById('arcReEnterIfscCode').readOnly=true;
		 	var arcReEnterIfscCode= document.getElementById('arcReEnterIfscCode');
		 	arcReEnterIfscCode.className="inputDisabled";
		 	//End [002]
			
			 
			document.getElementById('arcCheckNumber').value= '';
			document.getElementById('arcCheckDate').value= '';
			document.getElementById('arcBankName').value= '';
			document.getElementById('arcCheckamount').value= '';
			document.getElementById('arcAudjestedAmount').value = '';
			
			//Start [002]
		 	document.getElementById('arcReEnterCheckNumber').value='';
		 	document.getElementById('arcReEnterCheckamount').value='';
		 	document.getElementById('arcBankAccountNumber').value='';
		 	document.getElementById('arcReEnterBankAccountNumber').value='';
		 	document.getElementById('arcIfscCode').value='';
		 	document.getElementById('arcReEnterIfscCode').value='';
		 	//End [002]
		 	
			
		}
		if(arcSelectedVal == "No") {

			document.getElementById('arcExemptionReason').readOnly = true;
			var arcExempReason = document.getElementById('arcExemptionReason');
			arcExempReason.className = "inputDisabled";
			document.getElementById('arcExemptionReason').value = '';
			
			document.getElementById('arcCheckNumber').readOnly = false;
			var arcNum = document.getElementById('arcCheckNumber');
			arcNum.className = "inputBorder1";
			
			document.getElementById('arcCheckDate').disabled = false;
			var arcDate = document.getElementById('arcCheckDate');
			arcDate.className = "inputBorder1";
			
			document.getElementById('arcBankName').readOnly = false;
			var arcBank = document.getElementById('arcBankName');
			arcBank.className = "inputBorder1";
			
			document.getElementById('arcCheckamount').readOnly = false;
			var arcChkAmt = document.getElementById('arcCheckamount');
			arcChkAmt.className = "inputBorder1";
			
			document.getElementById('arcAudjestedAmount').readOnly = false;
			var adjAmount = document.getElementById('arcAudjestedAmount');
			adjAmount.className = "inputBorder1";
			
			//Start [002]
		 	document.getElementById('arcReEnterCheckNumber').readOnly=false;
		 	var arcReEnterCheckNumber= document.getElementById('arcReEnterCheckNumber');
		 	arcReEnterCheckNumber.className="inputBorder1";
		 	
		 	document.getElementById('arcReEnterCheckamount').readOnly=false;
		 	var arcBankAccountNumber= document.getElementById('arcReEnterCheckamount');
		 	arcBankAccountNumber.className="inputBorder1";
		 	
		 	
		 	document.getElementById('arcBankAccountNumber').readOnly=false;
		 	var arcBankAccountNumber= document.getElementById('arcBankAccountNumber');
		 	arcBankAccountNumber.className="inputBorder1";
		 	
		 	document.getElementById('arcReEnterBankAccountNumber').readOnly=false;
		 	var arcReEnterBankAccountNumber= document.getElementById('arcReEnterBankAccountNumber');
		 	arcReEnterBankAccountNumber.className="inputBorder1";
		 	
		 	document.getElementById('arcIfscCode').readOnly=false;
		 	var arcIfscCode= document.getElementById('arcIfscCode');
		 	arcIfscCode.className="inputBorder1";
		 	
		 	document.getElementById('arcReEnterIfscCode').readOnly=false;
		 	var arcReEnterIfscCode= document.getElementById('arcReEnterIfscCode');
		 	arcReEnterIfscCode.className="inputBorder1";
		 	//End [002]
			
			
		}
	}	
		//THIS METHOD ID USED TO SELECT OPTION AT RUN TIME FOR OTC SECTION		
		function otcSelectionOperation() {
		
		var otcSelectedVal = document.getElementById('otcAdvancPaymentExemptd').value;
		
		if(otcSelectedVal == "Yes") {
		
			document.getElementById('otcExemptdReason').readOnly = false;
			var otcExempReason = document.getElementById('otcExemptdReason');
			otcExempReason.className = "inputBorder1";
			
			document.getElementById('otcCheckNumber').readOnly = true;
			var otcNum = document.getElementById('otcCheckNumber');
			otcNum.className = "inputDisabled";
			
			document.getElementById('otcCheckDate').desabled = true;
			var otcDate = document.getElementById('otcCheckDate');
			otcDate.className = "inputDisabled";
			
			document.getElementById('otcBankName').readOnly = true;
			var otcBank = document.getElementById('otcBankName');
			otcBank.className = "inputDisabled";
			
			document.getElementById('otcCheckamount').readOnly = true;
			var otcChkAmt = document.getElementById('otcCheckamount');
			otcChkAmt.className = "inputDisabled";
			
			document.getElementById('otcAudjestedAmount').readOnly = true;
			var otcAdjAmount = document.getElementById('otcAudjestedAmount');
			otcAdjAmount.className = "inputDisabled";
			
			
			//Start [002]
		 	document.getElementById('otcReEnterCheckNumber').readOnly=true;
		 	var otcReEnterCheckNumber= document.getElementById('otcReEnterCheckNumber');
		 	otcReEnterCheckNumber.className="inputDisabled";
		 	
		 	document.getElementById('otcReEnterCheckamount').readOnly=true;
		 	var otcReEnterCheckamount= document.getElementById('otcReEnterCheckamount');
		 	otcReEnterCheckamount.className="inputDisabled";
		 	
		 	document.getElementById('otcBankAccountNumber').readOnly=true;
		 	var otcBankAccountNumber= document.getElementById('otcBankAccountNumber');
		 	otcBankAccountNumber.className="inputDisabled";
		 	
		 	document.getElementById('otcReEnterBankAccountNumber').readOnly=true;
		 	var otcReEnterBankAccountNumber= document.getElementById('otcReEnterBankAccountNumber');
		 	otcReEnterBankAccountNumber.className="inputDisabled";
		 	
		 	document.getElementById('otcIfscCode').readOnly=true;
		 	var otcIfscCode= document.getElementById('otcIfscCode');
		 	otcIfscCode.className="inputDisabled";
		 	
		 	document.getElementById('otcReEnterIfscCode').readOnly=true;
		 	var otcReEnterIfscCode= document.getElementById('otcReEnterIfscCode');
		 	otcReEnterIfscCode.className="inputDisabled";
		 	//End [002]
	
	
			 
			document.getElementById('otcCheckNumber').value= '';
			document.getElementById('otcCheckDate').value= '';
			document.getElementById('otcBankName').value= '';
			document.getElementById('otcCheckamount').value= '';
			document.getElementById('otcAudjestedAmount').value = '';
			
			//Start [002]
		 	document.getElementById('otcReEnterCheckNumber').value='';
		 	document.getElementById('otcReEnterCheckamount').value='';
		 	document.getElementById('otcBankAccountNumber').value='';
		 	document.getElementById('otcReEnterBankAccountNumber').value='';
		 	document.getElementById('otcIfscCode').value='';
		 	document.getElementById('otcReEnterIfscCode').value='';
		 	//End [002]
		 	
			
		}
		if(otcSelectedVal == "No") {

			document.getElementById('otcExemptdReason').readOnly = true;
			var otcExemptdReason = document.getElementById('otcExemptdReason');
			otcExemptdReason.className = "inputDisabled";
			document.getElementById('otcExemptdReason').value = '';
			
			document.getElementById('otcCheckNumber').readOnly = false;
			var otcNum = document.getElementById('otcCheckNumber');
			otcNum.className = "inputBorder1";
			
			document.getElementById('otcCheckDate').disabled = false;
			var otcDate = document.getElementById('otcCheckDate');
			otcDate.className = "inputBorder1";
			
			document.getElementById('otcBankName').readOnly = false;
			var otcBank = document.getElementById('otcBankName');
			otcBank.className = "inputBorder1";
			
			document.getElementById('otcCheckamount').readOnly = false;
			var otcChkAmt = document.getElementById('otcCheckamount');
			otcChkAmt.className = "inputBorder1";
			
			document.getElementById('otcAudjestedAmount').readOnly = false;
			var otcAdjAmount = document.getElementById('otcAudjestedAmount');
			otcAdjAmount.className = "inputBorder1";
			
			//Start [002]
		 	document.getElementById('otcReEnterCheckNumber').readOnly=false;
		 	var otcReEnterCheckNumber= document.getElementById('otcReEnterCheckNumber');
		 	otcReEnterCheckNumber.className="inputBorder1";
		 	
		 	document.getElementById('otcReEnterCheckamount').readOnly=false;
		 	var otcReEnterCheckamount= document.getElementById('otcReEnterCheckamount');
		 	otcReEnterCheckamount.className="inputBorder1";
		 	
		 	document.getElementById('otcBankAccountNumber').readOnly=false;
		 	var otcBankAccountNumber= document.getElementById('otcBankAccountNumber');
		 	otcBankAccountNumber.className="inputBorder1";
		 	
		 	document.getElementById('otcReEnterBankAccountNumber').readOnly=false;
		 	var otcReEnterBankAccountNumber= document.getElementById('otcReEnterBankAccountNumber');
		 	otcReEnterBankAccountNumber.className="inputBorder1";
		 	
		 	document.getElementById('otcIfscCode').readOnly=false;
		 	var otcIfscCode= document.getElementById('otcIfscCode');
		 	otcIfscCode.className="inputBorder1";
		 	
		 	document.getElementById('otcReEnterIfscCode').readOnly=false;
		 	var otcReEnterIfscCode= document.getElementById('otcReEnterIfscCode');
		 	otcReEnterIfscCode.className="inputBorder1";
		 	//End [002]
	
			
	
			
			
		}
		
	}
	//THIS METHOD ID USED TO ALL BLACK FIELDS BEFORE PAGE SUBMISSTION		
	function isBlankForm()	{
		
		//[001]start
		var callerWindowObj =dialogArguments;
		if(callerWindowObj.callerWindowObj.gb_modeName != null && callerWindowObj.callerWindowObj.gb_modeName == 'viewMode'){
			return false;
		}
        //[001]end
        
		var arcSelectedVal = document.getElementById('arcAdvancePaymentExemptd').value;
		var otcSelectedVal = document.getElementById('otcAdvancPaymentExemptd').value;

  		var arcExemptionReason=advaceTransForm.arcExemptionReason.value;
  		var arcCheckNumber = advaceTransForm.arcCheckNumber.value;
		var arcCheckDate = advaceTransForm.arcCheckDate.value;
		var arcBankName = advaceTransForm.arcBankName.value;
		var arcCheckamount = advaceTransForm.arcCheckamount.value;
		var arcAudjestedAmount = advaceTransForm.arcAudjestedAmount.value;
		//Start [002]
	 	var arcReEnterCheckNumber = advaceTransForm.arcReEnterCheckNumber.value;
	 	var arcReEnterCheckamount = advaceTransForm.arcReEnterCheckamount.value;
	 	var arcBankAccountNumber = advaceTransForm.arcBankAccountNumber.value;
	 	var arcReEnterBankAccountNumber = advaceTransForm.arcReEnterBankAccountNumber.value;
	 	var arcIfscCode = advaceTransForm.arcIfscCode.value;
	    var arcReEnterIfscCode = advaceTransForm.arcReEnterIfscCode.value;
	 	//End [002]	
		var otcExemptdReason = advaceTransForm.otcExemptdReason.value;
		var otcCheckNumber = advaceTransForm.otcCheckNumber.value;
		var otcCheckDate = advaceTransForm.otcCheckDate.value;
		var otcBankName = advaceTransForm.otcBankName.value;
		var otcCheckamount = advaceTransForm.otcCheckamount.value;
		var otcAudjestedAmount = advaceTransForm.otcAudjestedAmount.value;
		//Start [002]
	 	var otcReEnterCheckNumber = advaceTransForm.otcReEnterCheckNumber.value;
	 	var otcReEnterCheckamount = advaceTransForm.otcReEnterCheckamount.value;
	 	var otcBankAccountNumber = advaceTransForm.otcBankAccountNumber.value;
	 	var otcReEnterBankAccountNumber = advaceTransForm.otcReEnterBankAccountNumber.value;
	 	var otcIfscCode = advaceTransForm.otcIfscCode.value;
	    var otcReEnterIfscCode = advaceTransForm.otcReEnterIfscCode.value;
	 	//End [002]
	 	
			
		if(arcSelectedVal == "Yes") {
		   
		   if(arcExemptionReason == null  || arcExemptionReason == ""){
		   
				alert("Please enter ARC exemption reason!");
				return false;
			}
			
		}	
				if(arcSelectedVal == "No") { 
			    //Start [002]		
				if(arcCheckamount == null || arcCheckamount == "") {
						alert("Please enter ARC check amount");
						return false;
				}
				
				if(arcReEnterCheckamount == null || arcReEnterCheckamount == "") {
					alert("Please enter ARC Re-Enter check amount");
					return false;
			    }
				
				
				if(arcCheckamount != arcReEnterCheckamount) {
					alert("The ARC check amount does not match with the ARC Re-Enter check amount");
					return false;
			    }
				
				
					
				if(arcCheckNumber == null || arcCheckNumber == "") {
					alert("Please enter ARC check number");
					return false;
				}
				
				if(arcReEnterCheckNumber == null || arcReEnterCheckNumber == "") {
					alert("Please enter ARC Re-Enter check number");
					return false;
				}
				
				
				if(arcCheckNumber != arcReEnterCheckNumber) {
					alert("The ARC check number does not match with the ARC Re-Enter check number");
					return false;
			    }
				
				
				if(arcBankAccountNumber == null || arcBankAccountNumber == "") {
					alert("Please enter ARC Bank Account number");
					return false;
				}
				
				if(arcReEnterBankAccountNumber == null || arcReEnterBankAccountNumber == "") {
					alert("Please enter ARC Re-Enter Bank Account number");
					return false;
				}
				
				
				if(arcBankAccountNumber != arcReEnterBankAccountNumber) {
					alert("The ARC Bank Account number does not match with the ARC Re-Enter Bank Account number");
					return false;
			    }
				
				
				
				//End [002]
				
				if(arcCheckDate == null || arcCheckDate == "") {
					alert("Please enter ARC check date");
					return false;
				}
				
				if(arcBankName == null || arcBankName == "") {
					alert("Please enter ARC bank name");
					return false;
				}
					
				
				
				
				if(arcAudjestedAmount == null || arcAudjestedAmount == "") {
					alert("Please enter ARC audjested amount");
					return false;
				}
				
				//Start[002]
				if(arcIfscCode == null || arcIfscCode == "") {
					alert("Please enter ARC IFSC Code");
					return false;
				}
				
				if(arcReEnterIfscCode == null || arcReEnterIfscCode == "") {
					alert("Please enter ARC Re-Enter IFSC Code");
					return false;
				}
				
				if(arcIfscCode != arcReEnterIfscCode) {
					alert("The ARC IFSC Code does not match with the ARC Re-Enter IFSC Code");
					return false;
			    }
				
				
				
				//end [002]
		}
		if(otcSelectedVal == "Yes") {
		
					 if(otcExemptdReason == null || otcExemptdReason == "") {
			 	alert("Please enter OTC excepted reason");
			 	return false;
			 }
		
		}
		
		if(otcSelectedVal == "No" ){
			
			  //Start [002]		
			if(otcCheckamount == null || otcCheckamount == "") {
					alert("Please enter OTC check amount");
					return false;
			}
			
			if(otcReEnterCheckamount == null || otcReEnterCheckamount == "") {
				alert("Please enter OTC Re-Enter check amount");
				return false;
		    }
			
			
			if(otcCheckamount != otcReEnterCheckamount) {
				alert("The OTC check amount does not match with the OTC Re-Enter check amount");
				return false;
		    }
			
				
			if(otcCheckNumber == null || otcCheckNumber == "") {
				alert("Please enter OTC check number");
				return false;
			}
			
			if(otcReEnterCheckNumber == null || otcReEnterCheckNumber == "") {
				alert("Please enter OTC Re-Enter check number");
				return false;
			}
			
			
			if(otcCheckNumber != otcReEnterCheckNumber) {
				alert("The OTC check number does not match with the OTC Re-Enter check number");
				return false;
		    }
			
			
			if(otcBankAccountNumber == null || otcBankAccountNumber == "") {
				alert("Please enter OTC Bank Account number");
				return false;
			}
			
			if(otcReEnterBankAccountNumber == null || otcReEnterBankAccountNumber == "") {
				alert("Please enter OTC Re-Enter Bank Account number");
				return false;
			}
			
			if(otcBankAccountNumber != otcReEnterBankAccountNumber) {
				alert("The OTC Bank Account number does not match with the OTC Re-Enter Bank Account number");
				return false;
		    }
			
			//End [002]
			
				
				if(otcCheckDate == null || otcCheckDate == "") {
					alert("Please enter OTC check date");
					return false;
				}
				
				if(otcBankName == null || otcBankName == "") {
					alert("Please enter OTC bank name");
					return false;
				}
				
				
				if(otcAudjestedAmount == null || otcAudjestedAmount == "") {
					alert("Please enter OTC audjected amount");
					return false;
				}
				
				//Start[002]
				if(otcIfscCode == null || otcIfscCode == "") {
					alert("Please enter OTC IFSC Code");
					return false;
				}
				
				if(otcReEnterIfscCode == null || otcReEnterIfscCode == "") {
					alert("Please enter OTC Re-Enter IFSC Code");
					return false;
				}
				
				if(otcIfscCode != otcReEnterIfscCode) {
					alert("The OTC IFSC Code does not match with the OTC Re-Enter IFSC Code");
					return false;
			    }
				
				//end [002]

			
		}
		
		saveAdvancePaymentDetails();
	}
	
	document.onkeyup=function(){
		if(event.keyCode == 17) isCtrl=false; 
	}
	
	document.onkeydown=checkKeyPress;
	function checkKeyPress()
	{
		if (event.ctrlKey && event.keyCode == 83) 
		{
					event.keyCode=4; 
					isBlankForm();
					document.getElementById("saveImage").tabIndex = -1;
					document.getElementById("saveImage").focus();		
   		}   
	}
	
	function getTip(value)
	{	
		Tip("<Table border=0> "+ value +"   </table>");	
	}
	
	function getTip_DD(obj,value,objName)
	{	
		var SelectedValue;
		var selIndex = value;
		var myForm=document.getElementById('advaceTransForm');
		SelectedValue=document.getElementById(objName).options[selIndex].text ;
		var combo = document.getElementById(objName);
		combo.title = SelectedValue;
	}
	
	function lengthRestriction(elem, min, max)
	{
		if(!checkdigitsValidation(elem)==true)
		{
			return false;
		}
		var uInput = elem.value;
		if(uInput.length >= min && uInput.length <= max)
		{
			return true;
		}
		else
		{
			if(min==max)
			{
				alert("Please input atleast " +min+ " characters");
				elem.focus();
				return false;
			}
			else
			{
				alert("Minimum Length should be:" +min+ " and Maximum Length should be " +max+ " characters");
				elem.focus();
				return false;
			}
		}
	}
	
		path='<%=request.getContextPath()%>';
</script>

	<html:form action="/NewOrderAction" styleId="advaceTransForm"  method="post">
		<fieldset class="border1"><legend> <b>Advance Payment Details</b> </legend>
			<div class="scroll" style="height:80%; margin-top: 20px;" align="right" >
				<table  border="1" cellspacing="0" cellpadding="0" align="center" width="90%"  >
					<tr align="center">
						<td colspan="0" align="left" >
							<label class="hintanchor"><b>Order No:</b><span id="id_span_ServiceNo"><%=request.getAttribute("OrderNo") %></span></label>
							<input type="hidden" name="orderNo" id="orderNo" value="<%=request.getAttribute("OrderNo") %>" />
							<input type="hidden" name="poNumber" id="poNumber" value="<%=request.getAttribute("OrderNo") %>" />
						</td>
						<td colspan="0" align="left" >							
							<label class="hintanchor" id="lblServiceProductId"><b>LineItem No:</b><%=request.getAttribute("LineItemNo") %></label>
							<input type="hidden" name="lineItemNo" id="lineItemNo" value="<%=request.getAttribute("LineItemNo") %>" />
						</td>
						<td colspan="0" align="left" >
							<label class="hintanchor"><b>Service No:</b><span id="id_span_ServiceName"><%=request.getAttribute("ServiceNo") %></span></label>
							<input type="hidden" name="serviceNo" id="serviceNo" value="<%=request.getAttribute("ServiceNo") %>" />
						</td>
						<td colspan="0" align="left" >
							<label class="hintanchor"><b>LSI No:</b><span id="id_span_LSINo"><%=request.getParameter("logicalSIno") %></span></label>
							<input type="hidden" name="logicalSIno" id="logicalSIno" value="<%=request.getParameter("logicalSIno") %>" />
						</td>
						<input type="hidden" name="advanveServiceId" id="advanveServiceId" value="" />
						<input type="hidden" name="errorMessageId" id="errorMessageId" value="" />
					</tr>
				</table>
			</div>
	
		<fieldset class="border1"><legend> <b>ARC Advance</b> </legend>
			<table border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
				<tr >
					<td align="left" nowrap width="20%" height="30">ARC Exempted</td>
					<td align="left" nowrap width="30%" height="30"><select
						name="arcAdvancePaymentExemptd" id="arcAdvancePaymentExemptd"
						onchange="arcSelectionOperation();" onmouseover='getTip_DD(this,this.selectedIndex,this.id)'
						style="width: 150px; float: left" class="dropdownMargin">
						<option value="No" selected="selected" >No</option>
						<option value="Yes">Yes</option>
						</select>
					</td>
					<td align="left" nowrap width="20%" height="30">Drawn of Bank</td>
					<td align="left" nowrap width="30%" height="30"><input name="arcBankName" id="arcBankName" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" class="inputBorder1" maxlength="50"
						onBlur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Drawn of Bank')};" />
					</td>
				</tr>
				<tr>
				<td align="left" nowrap width="20%" height="30">Cheque Amount</td>
					<td align="left" nowrap width="30%" height="30">
					<input name="arcCheckamount" id="arcCheckamount" type="text" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" class="inputBorder1" maxlength="20" onblur="if(this.value.length > 0){return checknumber_max2decimal_charges_section(this,1)}"  />
					</td>
					<td align="left" nowrap width="20%" height="30">Re-Enter Cheque Amount</td>
					<td align="left" nowrap width="30%" height="30">
					<input name="arcReEnterCheckamount" id="arcReEnterCheckamount" type="text" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" class="inputBorder1" maxlength="20" onblur="if(this.value.length > 0){return checkConfirmField('arcCheckamount','arcReEnterCheckamount','Cheque Amount','Re-Enter Cheque Amount')}"   />
					</td>
				</tr>
				<tr>
					<td align="left" nowrap width="20%" height="30">Cheque Number</td>
					<td align="left" nowrap width="30%" height="30">
					<input name="arcCheckNumber" id="arcCheckNumber" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" 
						class="inputBorder1" maxlength="6" onblur="if(this.value.length > 0){return lengthRestriction(this, 6 , 6)}">
					</td>
					
					<td align="left" nowrap width="20%" height="30">Re-Enter Cheque Number</td>
					<td align="left" nowrap width="30%" height="30">
						<input name="arcReEnterCheckNumber" id="arcReEnterCheckNumber" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" 
						class="inputBorder1" maxlength="6" onblur="if(this.value.length > 0){return checkConfirmField('arcCheckNumber','arcReEnterCheckNumber','Cheque Number','Re-Enter Cheque Number')}" >
					</td>
				</tr>
				
				<tr>
					<td align="left" width="20%" height="30">Cheque Date</td>
					<td align="left" nowrap width="30%" height="30">
						<input  onmouseover="getTip(value)" onmouseout="UnTip()" width="100%" class="inputBorder1" isRequired="0" id="arcCheckDate" name="arcCheckDate" 
						 readonly="readonly"  onblur="if(this.value.length > 0){return arcCheckDate(this)} " />
				
						<font size="1">
							<a href="#" class="borderTabCal"><img id="AttributeValDate_7" src="/IOES/images/cal.gif" border="0px" alt="Select date" style="vertical-align: bottom;"></a>											
						</font>
					</td>
					<td align="left" nowrap width="20%" height="30">Amount to be Adjusted against this LSI</td>
					<td align="left" nowrap width="30%" height="30">
						<input name="arcAudjestedAmount" id="arcAudjestedAmount" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" class="inputBorder1" maxlength="20" 
						onblur="if(this.value.length > 0){return checknumber_max2decimal_charges_section(this,1)}"   >
					</td>
				</tr>
				
				<tr>
					<td align="left" width="20%" height="30">Bank Account Number</td>
					<td align="left" nowrap width="30%" height="30">
						<input name="arcBankAccountNumber" id="arcBankAccountNumber" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" class="inputBorder1" maxlength="25" 
						onblur="if(this.value.length > 0){return lengthRestriction(this, 6 , 25)}"  >
				
					</td>
					<td align="left" nowrap width="20%" height="30">Re-Enter Bank Account Number</td>
					<td align="left" nowrap width="30%" height="30">
					<input name="arcReEnterBankAccountNumber" id="arcReEnterBankAccountNumber" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" class="inputBorder1" maxlength="25" 
						onblur="if(this.value.length > 0){return checkConfirmField('arcBankAccountNumber','arcReEnterBankAccountNumber','Bank Account Number','Re-Enter Bank Account Number')}"   ></td>
				</tr>
				
				<tr>
					<td align="left" width="20%" height="30">IFSC Code</td>
					<td align="left" nowrap width="30%" height="30">
						<input name="arcIfscCode" id="arcIfscCode" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" 
						class="inputBorder1" maxlength="14" onBlur="if( trim(this.value).length>0){return ValidateTextField(this,path,'IFSC Code')};" >
				
					</td>
					<td align="left" nowrap width="20%" height="30">Re-Enter IFSC Code</td>
					<td align="left" nowrap width="30%" height="30">
						<input name="arcReEnterIfscCode" id="arcReEnterIfscCode" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" class="inputBorder1" maxlength="14" 
						onblur="if(this.value.length > 0){return checkConfirmField('arcIfscCode','arcReEnterIfscCode','IFSC Code','Re-Enter IFSC Code')}"  >
					</td>
				</tr>
				
				
				
				<tr>
				
					<td align="left" nowrap width="20%" height="30">Exemption Reason</td>
					<td align="left" nowrap width="30%" height="30">
						<input name="arcExemptionReason" id="arcExemptionReason" type="text" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" class="inputBorder1" maxlength="80"
						onBlur="if( trim(this.value).length>0){return ValidateTextField(this,path,'ARC Exemption Reason')};" />
					</td>
					<td height="30"></td>
				</tr>
				
				
			</table>
		</fieldset>
		<fieldset class="border1"><legend> <b>OTC Advance</b> </legend>
			<table border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
				<tr>
					<td align="left" nowrap width="20%" height="30">OTC Exempted</td>
					<td align="left" nowrap width="30%" height="30">
						<select	name="otcAdvancPaymentExemptd" id="otcAdvancPaymentExemptd" onchange="otcSelectionOperation();" onmouseover='getTip_DD(this,this.selectedIndex,this.id)'
							style="width: 150px; float: left" class="dropdownMargin">
							<option value="No" selected="selected" >No</option>
							<option value="Yes">Yes</option>
						</select>
					</td>
					<td align="left" nowrap width="20%" height="30">Drawn of Bank</td>
					<td align="left" nowrap width="30%" height="30">
						<input name="otcBankName" id="otcBankName" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" class="inputBorder1" maxlength="50" 
						onBlur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Drawn of Bank')};" />
					</td>
				</tr>
				<tr>
					<td align="left" nowrap width="20%" height="30">Cheque Amount</td>
					<td align="left" nowrap width="30%" height="30">
						<input 	name="otcCheckamount" id="otcCheckamount" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" class="inputBorder1" maxlength="20" 
						onblur="if(this.value.length > 0){return checknumber_max2decimal_charges_section(this,1)}"   />
					</td>
					
					<td align="left" nowrap width="20%" height="30">Re-Enter Cheque Amount</td>
					<td align="left" nowrap width="30%" height="30">
						<input 	name="otcReEnterCheckamount" id="otcReEnterCheckamount" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" class="inputBorder1" maxlength="20" 
						onblur="if(this.value.length > 0){return checkConfirmField('otcCheckamount','otcReEnterCheckamount','Cheque Amount','Re-Enter Cheque Amount')}"   />
					</td>
				</tr>
				<tr>
					<td align="left" nowrap width="20%" height="30">Cheque Number</td>
					<td align="left" nowrap width="30%" height="30">
						<input name="otcCheckNumber" id="otcCheckNumber" maxlength="6" onmouseover="getTip(value)" onmouseout="UnTip()"
						onblur="if(this.value.length > 0){return lengthRestriction(this, 6 , 6)}"
						style="width: 150px; float: left" class="inputBorder1" />
					</td>
					<td align="left" nowrap width="20%" height="30">Re-Enter Cheque Number</td>
					<td align="left" nowrap width="30%" height="30">
						<input name="otcReEnterCheckNumber" id="otcReEnterCheckNumber" maxlength="6" onmouseover="getTip(value)" onmouseout="UnTip()"
						onblur="if(this.value.length > 0){return checkConfirmField('otcCheckNumber','otcReEnterCheckNumber','Cheque Number','Re-Enter Cheque Number')}" 
						style="width: 150px; float: left" class="inputBorder1" />
	               </td>
				</tr>
				<tr>
					<td align="left" width="20%" height="30">Cheque Date</td>
					<td align="left" nowrap width="30%" height="30">
						<input onmouseover="getTip(value)" onmouseout="UnTip()" width="100%" class="inputBorder1" isRequired="0" id="otcCheckDate" name="otcCheckDate" 
					 	readonly="readonly"  onblur="if(this.value.length > 0){return otcCheckDate(this)}" />			
						<font size="1">
							<a href="#" class="borderTabCal"><img id="AttributeValDate_1" src="/IOES/images/cal.gif" border="0px" alt="Select date" style="vertical-align: bottom;"></a>											
						</font>
			 		</td>
			 		<td align="left" nowrap width="20%" height="30">Amount to be Adjusted against this LSI</td>
					<td align="left" nowrap width="30%" height="30">
						<input name="otcAudjestedAmount" id="otcAudjestedAmount" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" class="inputBorder1" maxlength="20" 
						onblur="if(this.value.length > 0){return checknumber_max2decimal_charges_section(this,1)}"   />
					</td>
				</tr>
				
				<tr>
					<td align="left" nowrap width="20%" height="30">Bank Account Number</td>
					<td align="left" nowrap width="30%" height="30">
						<input name="otcBankAccountNumber" id="otcBankAccountNumber" maxlength="25" onmouseover="getTip(value)" onmouseout="UnTip()"
						onblur="if(this.value.length > 0){return lengthRestriction(this, 6 , 25)}"
						style="width: 150px; float: left" class="inputBorder1" />
					</td>
					<td align="left" nowrap width="20%" height="30">Re-Enter Bank Account Number</td>
					<td align="left" nowrap width="30%" height="30">
						<input name="otcReEnterBankAccountNumber" id="otcReEnterBankAccountNumber" maxlength="25" onmouseover="getTip(value)" onmouseout="UnTip()"
							onblur="if(this.value.length > 0){return checkConfirmField('otcBankAccountNumber','otcReEnterBankAccountNumber','Bank Account Number','Re-Enter Bank Account Number')}" 
						style="width: 150px; float: left" class="inputBorder1" />
	               </td>
				</tr>
				
				<tr>
					<td align="left" width="20%" height="30">IFSC Code</td>
					<td align="left" nowrap width="30%" height="30">
						<input name="otcIfscCode" id="otcIfscCode" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" class="inputBorder1" maxlength="14" 
						onBlur="if( trim(this.value).length>0){return ValidateTextField(this,path,'IFSC Code')};">
					</td>
					<td align="left" nowrap width="20%" height="30">Re-Enter IFSC Code</td>
					<td align="left" nowrap width="30%" height="30">
						<input name="otcReEnterIfscCode" id="otcReEnterIfscCode" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" class="inputBorder1" maxlength="14" 
						onblur="if(this.value.length > 0){return checkConfirmField('otcIfscCode','otcReEnterIfscCode','IFSC Code','Re-Enter IFSC Code')}"  >
					</td>
				</tr>
				
				
				<tr>
					<td align="left" nowrap width="20%" height="30">Exemption Reason</td>
					<td align="left" nowrap width="30%" height="30">
						<input
						name="otcExemptdReason" id="otcExemptdReason" onmouseover="getTip(value)" onmouseout="UnTip()"
						style="width: 150px; float: left" class="inputBorder1" maxlength="100" 
						onBlur="if( trim(this.value).length>0){return ValidateTextField(this,path,'OTC Exemption Reason')};" />
					</td>
					
					<td height="30"></td>
					</tr>
				
				
			</table>
		</fieldset>
	</fieldset>
	<center>
		<input type="button" value="Save" id="saveImage" onclick="isBlankForm();" disabled=""/>
		<input type="button" value="Close" id="closeButtonId" onclick="closeCurrentPage();" />
	</center>
</html:form>
</body>
<!--start [012]-->
<script type="text/javascript">
	
	/*fetchFeildList();
	//PAGING-SERVICE-LINE-14-10-2012
	//drawTable();	
	DrawTable('SERVICENO','SERVICELINETABLE');*/
	
	$(function(){		
		$("#otcCheckDate" ).datepicker({dateFormat: 'dd/mm/yy', showOn: 'none' });
		$("#arcCheckDate" ).datepicker({dateFormat: 'dd/mm/yy',showOn: 'none' });
	});
	$('#AttributeValDate_1').click(function() {
	$('#otcCheckDate').datepicker('show');
	});
	$('#AttributeValDate_7').click(function() {
      $('#arcCheckDate').datepicker('show');
	});
	
	function setShowCal()
	{
		$(":text[showcal='date']").each(function() {
			var thisEl = $(this).attr("id");
		 $("#"+thisEl ).datepicker({dateFormat: 'dd/mm/yy',showOn: 'none' });
		});
	}

</script>
<!--End [012] -->
</html>

