<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Ashutosh Singh	06-APR-11	CSR00-05422     Code Changed for Javascript Errors-->
<!--[002]	 SAURABH SINGH	16-Feb-12	CSR00-05422		Restrict User From Saving Attributes For Cancel Service-->
<!--[003]	 Lawkush 	12-June-12	    CSR00-05422		For making service product attributes editable or non editable according to change type , subchange type and SUBCHANGETYPE_NETWORK_CHANGE_EDITABLE flag  in database -->
<!--[004]	 Kalpana		13-JUN-13	CBR-CSR-20130404-XX-08714     For M6 Editable fields ,added isServiceSummReadonly to make the fields readonly and disable 
 if flag=1 and calling getFieldValidationForService to fetch readonly flag according to role wise -->
 <!-- [005] VIPIN 8-Jan-2014 Code added to populate all values for dropdown attributes added for service Managed Colo (21)  -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@page import="com.ibm.ioes.forms.ProductCatelogDTO"%>
<%@page import="com.ibm.ioes.forms.FieldAttibuteDTO"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.utilities.Utility"%>
<html>
<head>
<title>SelectProductLevelAttribute</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jsonrpc.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/formValidations.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/productCatelogUtility.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/utility.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script> 
<script type="text/javascript">
var serviceTypeId = <%=request.getParameter("hdnserviceTypeId")%>;
var path='<%=request.getContextPath()%>';
	function setFrmValue()
	{
	//Start[003]
		var callerWindowObj = dialogArguments;
		var serviceList ;
	//End[003]	
		//added for disconnection
		//Vijay add an other condition for demo regularize 
		if(document.getElementById('HdnChangeTypeID').value==3 || document.getElementById('HdnChangeTypeID').value==5
			|| (document.getElementById('HdnChangeTypeID').value==4 && document.getElementById('HdnSubChangeTypeID').value==12) )
		{
			document.getElementById('btnAddContact').disabled  = true;			
		}
		
		document.getElementById('hdnServiceNo').value = callerWindowObj.document.getElementById('hdnserviceid').value;
		if(callerWindowObj.document.getElementById('isBillingTriggerReady').value=="ENABLE")
		{
			document.searchForm.btnAddContact.style.visibility="hidden";
		}
		else
		{
			document.searchForm.btnAddContact.style.visibility="visible";
		}
		jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			var sessionid ='<%=request.getSession().getId() %>';
			
		serviceList = jsonrpc.processes.getFieldValidationForService(sessionid,document.getElementById('hdnServiceNo').value);
		for (iField = 0,count=1 ; iField < serviceList.list.length; iField++,count++)
    	{
    	if(serviceList.list[iField].isServiceSummReadonly==1)
			{	
				document.getElementById('prodAttValue_'+count).disabled  = true;
				document.getElementById('prodAttValue_'+count).className = "inputBorder2";
			}else if(serviceList.list[iField].isMand==1){
				document.getElementById('prodAttValue_'+count).className = "inputBorder1";
			}else{
				document.getElementById('prodAttValue_'+count).className = "inputBorder2";
			}
    	}
		
			
	}	
	
	function validateServicePrdAttValues()
	{
		var countAttributes=document.getElementById('attCount').value;
		setFormBean(document.forms[0]);
		for(i=1;i<=countAttributes;i++)
		{
			if(document.getElementById('prodIsMandatory_'+i).value=="Y" && document.getElementById('prodAttValue_'+i).value=="") 
			{
				alert("Please Input Service Product Attributes Details!!");
				document.getElementById('prodAttValue_'+i).focus();
				return false;
			}
		}
	}
   function insertTProductAttDetail()
	{
		//Start[002]
		var serviceId = document.getElementById('hdnServiceNo').value;
		
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");	
	
		serviceInactiveFlagCheck=jsonrpc.processes.serviceInactiveFlagCheck(serviceId);
		
		if(serviceInactiveFlagCheck == 1 || serviceInactiveFlagCheck == 2)
		{
			alert('Service cancelled , you cant save now !! ');
			return false;
		}
		//End[002]
		
		if(document.searchForm.prodAttValue==null)
		{
			alert("No Attributes Present");
			return false;
		}
		
		if(validateServicePrdAttValues()==false)
		{
			return false;
		}
		//Gunjan Start
		if(serviceTypeId == 221 || serviceTypeId==141 || serviceTypeId==321)
		{
			OrderBandwidthAttIds = jsonrpc.processes.getL2_L3_ISP_BW_AttIds();
			if(document.searchForm.prodAttId.length == undefined)
			{
				return;
			}	
			for(var i=0;i<document.searchForm.prodAttId.length;i++)
			{
				for(j=0;j<OrderBandwidthAttIds.list.length;j++){
					
					if(document.searchForm.prodAttId[i].value==OrderBandwidthAttIds.list[j]){
							if(checkdecimal(document.searchForm.prodAttValue[i])==false)
							return false;
					}
				}
			}
		
		}
		//Gunjan End
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
		var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleName,userId);
		var shortCode = jsonrpc.processes.getShortCode(roleName);
		
		if(orderDetails.list.length>0)
		{
			stage=orderDetails.list[0].stageName;
			if(shortCode==stage)
			{
				//do nothing
			}
			else if(stage=="New" || stage=="NEW")
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
	   	// Added by Ramana for Digital Signage LSI validation	
	   	//Meenakshi : Modified for Cinema
		//for loop added by kalpana to validate product having multiple attributes
		if(document.searchForm.prodAttId.length == undefined)
			{
				if(document.searchForm.prodAttId.value == 185 || document.searchForm.prodAttId.value == 347)
		   			{
				   		//var lsi = document.searchForm.prodAttValue.value;
				   		//var callerWindowObj = dialogArguments;
				   		 //Meenakshi: added for signage Child validation
				   		//var orderNo=document.searchForm.prodAttValue.value;
						//added by kalpana to validate prodIdValue for above attribute id
				   		var	 orderNo=document.searchForm.prodAttValue.value;
				   		//end
				   		var orderNumber = callerWindowObj.document.searchForm.poNumber.value;
				   		var errorMessage = jsonrpc.processes.validateDMXLogicalLSI(orderNo,orderNumber);
			   			if(errorMessage != '')
						{
						    alert(errorMessage);
						    //Meenakshi: added for signage Child validation
						    document.searchForm.prodAttValue.value='';
						    return false;
						}
					
		   			}
			}
			else
			{
		   for(var i=0;i<document.searchForm.prodAttId.length;i++)
			{
				if(document.searchForm.prodAttId[i].value == 185 || document.searchForm.prodAttId[i].value == 347)
		   		{
			   		//alert('lsi = '+document.searchForm.prodAttValue.value);
			   		//var lsi = document.searchForm.prodAttValue.value;
			   		//var callerWindowObj = dialogArguments;
			   		 //Meenakshi: added for signage Child validation
			   		//var orderNo=document.searchForm.prodAttValue.value;
					//added by kalpana to validate prodIdValue for above attribute id
				   	var orderNo=document.searchForm.prodAttValue[i].value;
				   	//end
			   		var orderNumber = callerWindowObj.document.searchForm.poNumber.value;
			   		var errorMessage = jsonrpc.processes.validateDMXLogicalLSI(orderNo,orderNumber);
						if(errorMessage != '')
						{
						    alert(errorMessage);
						    //Meenakshi: added for signage Child validation
						    document.searchForm.prodAttValue.value='';
						    return false;
						}
				}
		   	}
		   }
	   	var i;
	   	var count = 1;
	    var tabData = document.getElementById('tablePO');
		var jsData; 
		var stringarrayLabel = new Array();
		var stringarrayValue = new Array();
		var stringarrayDataTypes = new Array();
		var stringarrayLabels = new Array();
		var stringarrayIsMandatory = new Array();
		var stringarrayMaxlenth = new Array();
		
		if(document.searchForm.prodAttId.length==null)
		{
			stringarrayLabel[0]= document.searchForm.prodAttValue.value;
			stringarrayValue[0]= document.searchForm.prodAttId.value;
			stringarrayDataTypes[0]=document.searchForm.prodDataTypes.value;
			stringarrayLabels[0]=document.searchForm.prodLabels.value;
			stringarrayIsMandatory[0]=document.searchForm.prodIsMandatory.value;
			stringarrayMaxlenth[0]=document.searchForm.prodAttriMaxlength.value;
		}
		else
		{
			for(var i=0;i<document.searchForm.prodAttValue.length;i++)
			{
				
				stringarrayLabel[i]= document.searchForm.prodAttValue[i].value;
			}
	     	
	
			for(var i=0;i<document.searchForm.prodAttId.length;i++)
			{
				
				stringarrayValue[i]= document.searchForm.prodAttId[i].value;
				stringarrayDataTypes[i]=document.searchForm.prodDataTypes[i].value;
				stringarrayLabels[i]=document.searchForm.prodLabels[i].value;
				stringarrayIsMandatory[i]=document.searchForm.prodIsMandatory[i].value;
				stringarrayMaxlenth[i]=document.searchForm.prodAttriMaxlength[i].value;
			}
		}
     	var jsData =
     			 {
	    			prodAttributeLabelArray:stringarrayLabel,
	    			prodAttributeValueArray:stringarrayValue,
	    			prodAttributeDataTypeArray:stringarrayDataTypes,
	    			prodAttributeDisplayLabelArray:stringarrayLabels,
	    			prodAttributeIsMandatory:stringarrayIsMandatory,
	    			prodServiceAttMaxLength:stringarrayMaxlenth,
	    			serviceDetailID:document.getElementById('hdnServiceNo').value
				 };

	  try
	  {
	  
	  
	  jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
   	  //[001] Start
	  var sessionId='<%=request.getSession().getId() %>';
   	  //var statusDto = jsonrpc.processes.insertTProductAttDetail(jsData);
   	  var statusDto = jsonrpc.processes.insertTProductAttDetail(jsData,sessionId);
   	  //[001] End
   	  
   	  if(showErrorsIfAny(statusDto))
		{
			return;
		}
	
   	  if(statusDto.status=="1")
   	  {		
   	  	var callerWindowObj = dialogArguments;
   		  alert("Service Product Attributes Added Succesfully");
	   	  counterVal=callerWindowObj.document.getElementById('hdnCurrentTempCounter').value;

	   	  var nam="prdAttrEntered"+counterVal;
   		 callerWindowObj.document.getElementById(nam).value = '1';
   		  	
   	  }
   	  else
   	  		alert("Error While Adding Service Product Attributes !!!!")
   	  
   	  }
   	  catch(e)
   	  {
   	  	alert(e);
   	  }
	    window.close();
	}
	
function showErrorsIfAny(statusDto)
{
	var errors=statusDto.errors_Validation;
	
	if(errors==null)
	{
		return false;
	}
	if(errors.list.length>0)
		{
			for (i = 0; i <  errors.list.length; i++)
			{
				/*var tblRow=document.getElementById('tabaccountType').insertRow();
			    tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				tblcol.colSpan = 3;
				tblcol.style.color="red";
				str=errors.list[i];
				CellContents = str;
				tblcol.innerHTML = CellContents;*/
				alert(errors.list[i]);
			}
			return true;
		}
		else
		{
			return false;
		}
	
}
function disableAttributesInView()
{	
	var callerWindowObj = dialogArguments;
	var stage = callerWindowObj.document.getElementById('stageName').value;
	
	if(stage == 'Published' || stage == 'Billing Trigger' || stage == 'Completed')
	{		
		document.getElementById('btnAddContact').disabled=true;
		if(document.searchForm.prodAttId == undefined)
		{
			return false;
		}		
		for(var i=0;i<document.searchForm.prodAttId.length;i++)
		{				
			document.searchForm.prodAttValue[i].disabled=true;	
		}
			
	}
}
//Start Amit Sharma
function getprchildData()
{
	
	if(serviceTypeId == 181)
	{
		var serviceId=document.getElementById('hdnServiceNo').value;
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		DropAndCarryDataForCCList = jsonrpc.processes.getDropAndCarryDataForCC(serviceId); 
		 
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		KeyValuesList = jsonrpc.processes.newOrderKeyValues();
		 
		
				if(document.searchForm.prodAttId.length == undefined)
				{
					return;
				}		
				for(var i=0;i<document.searchForm.prodAttId.length;i++)
				{	
					for(var j=0;j< KeyValuesList.list.length; j++)
					{
						
						if(document.searchForm.prodAttId[i].value == KeyValuesList.list[j] || document.searchForm.prodAttId[i].value == KeyValuesList.list[j])
						{
							if(DropAndCarryDataForCCList.list[0].serviceFlavor = "DC"){
								if(DropAndCarryDataForCCList.list[0].circuitType == "DROP")
								{
									document.searchForm.prodAttValue[i].disabled=true;
						 			document.getElementById('prodIsMandatory_'+(i+1)).value = "N";
								}
								else{
									document.searchForm.prodAttValue[i].isRequired =1;
									document.getElementById('prodAttValue_'+(i+1)).className = "inputBorder1";
								}
						 //document.getElementById('prodAttValue_'+i).className = "inputBorder2";
						}
							else{
								document.searchForm.prodAttValue[i].isRequired =1;
								document.getElementById('prodAttValue_'+(i+1)).className = "inputBorder1";
							}
						}
					}
					
				}
	}
	/* else{
		return ;
	} */
	
//Gunjan Start
	
	LayerRateAttIds = jsonrpc.processes.getLayerRateAttIds();
	if(document.searchForm.prodAttId.length == undefined)
	{
		
		return;
	}		
	for(var i=0;i<document.searchForm.prodAttId.length;i++)
	{
		for(j=0;j<LayerRateAttIds.list.length;j++){
			
			if(document.searchForm.prodAttId[i].value==LayerRateAttIds.list[j]){
				//alert("Gunjan4: "+document.searchForm.prodAttValue[i].value);
				if(document.searchForm.prodAttValue[i].value==0){
					document.searchForm.prodAttValue[i].value=6;
				}
			}
		}
		
		
	}
	//Gunjan End
}
						
//End Amit Sharma						 
</script>

<body onload="setFrmValue();disableAttributesInView();getprchildData();">
<html:form action="/NewOrderAction" styleId="searchForm" method="post">
	<input type="hidden" id="hdnServiceNo" name="hdnServiceNo" >
	<input type="hidden" name="HdnChangeTypeID" value="<%=request.getParameter("HdnChangeTypeID") %>">		
	<!-- vijay  add a hidden field for subChangeTypeID for demo-regularize purpose-->
		<input type="hidden" id="HdnSubChangeTypeID" name="HdnSubChangeTypeID" value="<%=request.getParameter("hdnSubChangeTypeID") %>">		
	<!-- vijay end -->	
      <table width="100%"  border="0" cellspacing="0" cellpadding="0">
	    <tr>
		  <td valign="top">
			<fieldset class="border1">
			  <legend > <b>Service Product Attribute</b> </legend>
			     <table width="100%"  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="50%" align="center">Label Name</td>
						<td  width="50%" align="center">Value</td>
					</tr>
				</table>
				
					<table  border="1" cellspacing="0" cellpadding="0" align="center" width="100%" id='tablePO'>
					 <%int count=1; %>
					 <%int sno=0; %>
						<logic:present name="productAttDetailList" scope="request">
		                   <logic:iterate id="tpDetailID" name="productAttDetailList" indexId="recordId" >
		                     <%  count=((recordId.intValue())+1);%>
		                     <%sno=sno+1; %>
							  <tr>
							    <td width="50%"><bean:write  name="tpDetailID" property="attMasterName"/></td>
							    <td width="50%">
							    
							    <% Long serviceTypeID =(Long)request.getAttribute("serviceTypeId");							    		
							    		if(serviceTypeID.intValue() == 21)
							    		{
							    %>
							    
							    <logic:equal name="tpDetailID" property="expectedValue" value="DROPDOWN">
							    <!-- Start[003] -->
							    <select name="prodAttValue" class='inputBorder4' id="prodAttValue_<%=sno %>"  <%=Utility.getEnableDisableServiceProductAttributes("DISABLE_PROPERTY",Integer.parseInt(request.getParameter("HdnChangeTypeID").toString()),Integer.parseInt(request.getAttribute("hdnSubChangeTypeList").toString()),((ProductCatelogDTO)tpDetailID).getSubchangetypeNetworkChangeEditable()) %>   >
							    <!-- End[003] -->						
										<logic:iterate id="attLabel" name="tpDetailID" property="serviceSummary" >
											<bean:define id="storedValue" value="0"  type="java.lang.Object"/>
											<bean:define id="currDDValue" value="1" type="java.lang.Object"/>
											<logic:present name="tpDetailID" property="prodAttributeValue"  >
												<bean:define id="storedValue" name="tpDetailID" property="prodAttributeValue"  type="java.lang.Object"/>
												<bean:define id="currDDValue" name="attLabel" property="serviceSummaryValues" type="java.lang.Object" />
											</logic:present>
											<!-- [005] Start-->									
										   	<logic:equal name="tpDetailID" property="attMasterId" value="81">
											<% String hdnSubChangeTypeID = (String)request.getAttribute("hdnSubChangeTypeList");
																					
											if((("8".equalsIgnoreCase(hdnSubChangeTypeID)) || ("17".equalsIgnoreCase(hdnSubChangeTypeID))) && ("7".equals(((FieldAttibuteDTO)attLabel).getServiceSummaryValues())))
											{%>
										   		<option value="<bean:write name="attLabel" property="serviceSummaryValues"/>" 
										   <%if(Utility.fnCheckNull((Object)storedValue).equals((String)currDDValue)) {%>selected="selected"<%} %> 
										   >
											  <bean:write name="attLabel" property="serviceSummaryText"/>
											</option>
											<%}%>
											
											<%if((("9".equalsIgnoreCase(hdnSubChangeTypeID)) || ("18".equalsIgnoreCase(hdnSubChangeTypeID))) && ("2".equals(((FieldAttibuteDTO)attLabel).getServiceSummaryValues())))
											{%>
										   		<option value="<bean:write name="attLabel" property="serviceSummaryValues"/>" 
										   <%if(Utility.fnCheckNull((Object)storedValue).equals((String)currDDValue)) {%>selected="selected"<%} %> 
										   >
											  <bean:write name="attLabel" property="serviceSummaryText"/>
											</option>
											<%}%>
											
											<%if((("10".equalsIgnoreCase(hdnSubChangeTypeID)) || ("19".equalsIgnoreCase(hdnSubChangeTypeID))) && ("5".equals(((FieldAttibuteDTO)attLabel).getServiceSummaryValues())))
											{%>
										   		<option value="<bean:write name="attLabel" property="serviceSummaryValues"/>" 
										   <%if(Utility.fnCheckNull((Object)storedValue).equals((String)currDDValue)) {%>selected="selected"<%} %> 
										   >
											  <bean:write name="attLabel" property="serviceSummaryText"/>
											</option>
											<%}%>
											
											<%if((("6".equalsIgnoreCase(hdnSubChangeTypeID)) || ("13".equalsIgnoreCase(hdnSubChangeTypeID)))   && ("6".equals(((FieldAttibuteDTO)attLabel).getServiceSummaryValues())))
											{%>
										   		<option value="<bean:write name="attLabel" property="serviceSummaryValues"/>" 
										   <%if(Utility.fnCheckNull((Object)storedValue).equals((String)currDDValue)) {%>selected="selected"<%} %> 
										   >
											  <bean:write name="attLabel" property="serviceSummaryText"/>
											</option>
											<%}%>
											
											<%if((("7".equalsIgnoreCase(hdnSubChangeTypeID)) || ("14".equalsIgnoreCase(hdnSubChangeTypeID))) && ("4".equals(((FieldAttibuteDTO)attLabel).getServiceSummaryValues())))
											{%>
										   		<option value="<bean:write name="attLabel" property="serviceSummaryValues"/>" 
										   <%if(Utility.fnCheckNull((Object)storedValue).equals((String)currDDValue)) {%>selected="selected"<%} %> 
										   >
											  <bean:write name="attLabel" property="serviceSummaryText"/>
											</option>
											<%}%>
											
											<%if((("3".equalsIgnoreCase(hdnSubChangeTypeID)) || ("15".equalsIgnoreCase(hdnSubChangeTypeID))) && ("1".equals(((FieldAttibuteDTO)attLabel).getServiceSummaryValues())))
											{%>
										   		<option value="<bean:write name="attLabel" property="serviceSummaryValues"/>" 
										   <%if(Utility.fnCheckNull((Object)storedValue).equals((String)currDDValue)) {%>selected="selected"<%} %> 
										   >
											  <bean:write name="attLabel" property="serviceSummaryText"/>
											</option>
											<%}%>
											
											<%if((("4".equalsIgnoreCase(hdnSubChangeTypeID)) || ("16".equalsIgnoreCase(hdnSubChangeTypeID))) && ("1".equals(((FieldAttibuteDTO)attLabel).getServiceSummaryValues())))
											{%>
										   		<option value="<bean:write name="attLabel" property="serviceSummaryValues"/>" 
										   <%if(Utility.fnCheckNull((Object)storedValue).equals((String)currDDValue)) {%>selected="selected"<%} %> 
										   >
											  <bean:write name="attLabel" property="serviceSummaryText"/>
											</option>
											<%}%>
											
											<%if(("5".equalsIgnoreCase(hdnSubChangeTypeID)) && ("3".equals(((FieldAttibuteDTO)attLabel).getServiceSummaryValues())))
											{%>
										   		<option value="<bean:write name="attLabel" property="serviceSummaryValues"/>" 
										   <%if(Utility.fnCheckNull((Object)storedValue).equals((String)currDDValue)) {%>selected="selected"<%} %> 
										   >
											  <bean:write name="attLabel" property="serviceSummaryText"/>
											</option>
											<%}%>
																						
											<%if(("20".equalsIgnoreCase(hdnSubChangeTypeID)) && ("3".equals(((FieldAttibuteDTO)attLabel).getServiceSummaryValues())))
											{%>
										   		<option value="<bean:write name="attLabel" property="serviceSummaryValues"/>" 
										   <%if(Utility.fnCheckNull((Object)storedValue).equals((String)currDDValue)) {%>selected="selected"<%} %> 
										   >
											  <bean:write name="attLabel" property="serviceSummaryText"/>
											</option>
											<%}%>	
											
											<%if(("11".equalsIgnoreCase(hdnSubChangeTypeID)) && ("3".equals(((FieldAttibuteDTO)attLabel).getServiceSummaryValues())))
											{%>
										   		<option value="<bean:write name="attLabel" property="serviceSummaryValues"/>" 
										   <%if(Utility.fnCheckNull((Object)storedValue).equals((String)currDDValue)) {%>selected="selected"<%} %> 
										   >
											  <bean:write name="attLabel" property="serviceSummaryText"/>
											</option>
											<%}%>
											
											<%if(("12".equalsIgnoreCase(hdnSubChangeTypeID)) && ("3".equals(((FieldAttibuteDTO)attLabel).getServiceSummaryValues())))
											{%>
										   		<option value="<bean:write name="attLabel" property="serviceSummaryValues"/>" 
										   <%if(Utility.fnCheckNull((Object)storedValue).equals((String)currDDValue)) {%>selected="selected"<%} %> 
										   >
											  <bean:write name="attLabel" property="serviceSummaryText"/>
											</option>
											<%}%>										
											</logic:equal>
											<logic:notEqual name="tpDetailID" property="attMasterId" value="81">
												<option value="<bean:write name="attLabel" property="serviceSummaryValues"/>" 
										   		<%if(Utility.fnCheckNull((Object)storedValue).equals((String)currDDValue)) {%>selected="selected"<%}%> 
										   			title="<bean:write name="attLabel" property="serviceSummaryText"/>">
											  		<bean:write name="attLabel" property="serviceSummaryText"/>
												</option>
											</logic:notEqual>
											<!-- [005] END-->									
																				
										 </logic:iterate>
								     </select>
							     </logic:equal>
							     <%}
							     else 
							     {%>
							     <logic:equal name="tpDetailID" property="expectedValue" value="DROPDOWN">	
							      <!-- Start[003] -->
							     	 <select name="prodAttValue"  id="prodAttValue_<%=sno %>" <%=Utility.getEnableDisableServiceProductAttributes("DISABLE_PROPERTY",Integer.parseInt(request.getParameter("HdnChangeTypeID").toString()),Integer.parseInt(request.getAttribute("hdnSubChangeTypeList").toString()),((ProductCatelogDTO)tpDetailID).getSubchangetypeNetworkChangeEditable()) %>   >
							     
								    <!-- End[003] -->
										<logic:iterate id="attLabel" name="tpDetailID" property="serviceSummary" >
											<bean:define id="storedValue" value="0"  type="java.lang.Object"/>
											<bean:define id="currDDValue" value="1" type="java.lang.Object"/>
											<logic:present name="tpDetailID" property="prodAttributeValue"  >
												<bean:define id="storedValue" name="tpDetailID" property="prodAttributeValue"  type="java.lang.Object"/>
												<bean:define id="currDDValue" name="attLabel" property="serviceSummaryValues" type="java.lang.Object" />
											</logic:present>									
										   	
											<option value="<bean:write name="attLabel" property="serviceSummaryValues"/>" 
											<%if(Utility.fnCheckNull((Object)storedValue).equals((String)currDDValue)) {%>selected="selected"<%} %> 
										   >
											  <bean:write name="attLabel" property="serviceSummaryText"/>
											</option>
											
										 </logic:iterate>
								     </select>
							     </logic:equal>
							     <%} %>
							     
							     <logic:notEqual name="tpDetailID" property="expectedValue" value="DROPDOWN">
							     <!-- Start[003]  called getEnableDisableServiceProductAttributes func-->
							         <input type="text"  name="prodAttValue" id="prodAttValue_<%=sno %>" 
							    		
							    		value="<bean:write name="tpDetailID" property="prodAttributeValue"/>" maxlength="<%=((ProductCatelogDTO)tpDetailID).getProdAttributeMaxlength()%>"  
							    		<%if("VARCHAR".equalsIgnoreCase(((ProductCatelogDTO)tpDetailID).getExpectedValue())){%> onblur="if( trim(this.value).length>0){  return ValidateTextField(this,path,'<%=((ProductCatelogDTO)tpDetailID).getAttMasterName() %>')};" onkeyup="CheckMaxLength(this,<%=((ProductCatelogDTO)tpDetailID).getProdAttributeMaxlength()%>)" <%}%>
							    		<%if("NUMERIC".equalsIgnoreCase(((ProductCatelogDTO)tpDetailID).getExpectedValue())){%>onblur="if(this.value.length > 0){return checknumber(this)}" onkeyup="CheckMaxLength(this,<%=((ProductCatelogDTO)tpDetailID).getProdAttributeMaxlength()%>)"  <%}%>
							    		<%if ("YN".equalsIgnoreCase(((ProductCatelogDTO)tpDetailID).getExpectedValue()))  {%>onblur="if(this.value.length > 0){return YN(this)}" <% } %>
 									    <%if("DATETIME".equalsIgnoreCase(((ProductCatelogDTO)tpDetailID).getExpectedValue())){%>readonly="readonly"  <%}%>
							    		<%if("EMAIL".equalsIgnoreCase(((ProductCatelogDTO)tpDetailID).getExpectedValue())){%>onblur="if(this.value.length > 0){return emailValidate1(this)}" <%}%>
							    		
							    		  <%=Utility.getEnableDisableServiceProductAttributes("READONLY_PROPERTY",Integer.parseInt(request.getParameter("HdnChangeTypeID").toString()),Integer.parseInt(request.getAttribute("hdnSubChangeTypeList").toString()),((ProductCatelogDTO)tpDetailID).getSubchangetypeNetworkChangeEditable()) %>
							    		
							    		> 
							    <!-- End[003] -->
							    	</logic:notEqual>
							    	<%if("DATETIME".equals(((ProductCatelogDTO)tpDetailID).getExpectedValue())){%>
							    	<font size="1">
							    	<!-- Start[003] -->
							    		<img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."  <%=Utility.getEnableDisableServiceProductAttributes("DISABLE_PROPERTY",Integer.parseInt(request.getParameter("HdnChangeTypeID").toString()),Integer.parseInt(request.getAttribute("hdnSubChangeTypeList").toString()),((ProductCatelogDTO)tpDetailID).getSubchangetypeNetworkChangeEditable()) %>   onclick="javascript:show_calendar(searchForm.prodAttValue_<%=sno %>);" style="vertical-align: bottom"/>
									<!-- End[003] -->
									</font>
							    	<%}%>
							    <input type="hidden"  name="prodAttId" class="inputBorder4" value="<bean:write name="tpDetailID" property="attMasterId"/>">
							    <input type="hidden"  name="prodDataTypes" class="inputBorder4" value="<bean:write name="tpDetailID" property="expectedValue"/>">
							    <input type="hidden"  name="prodLabels" class="inputBorder4" value="<bean:write name="tpDetailID" property="attMasterName"/>">
							    <input type="hidden"  name="prodIsMandatory" class="inputBorder4" id="prodIsMandatory_<%=sno %>" value="<bean:write name="tpDetailID" property="mandatory"/>">
							   <input type="hidden"  name="prodAttriMaxlength" id="prodAttriMaxlength_<%=sno %>" value="<bean:write name="tpDetailID" property="prodAttributeMaxlength"/>">
							    </td>
							    
							    
						     </tr>
				          </logic:iterate>

				       </logic:present>
				       <input type="hidden" name="attCount" value="<%=sno %>">
					</table>
					<logic:present name="productAttDetailList" scope="request">
					<table width="100%"  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td colspan="2" align="center">
						<input type="button" name="btnAddContact" value="Submit" onClick="insertTProductAttDetail();">
						</td>
					</tr>
				  </table>	
				  </logic:present>
			</fieldset>
		</td>
	</tr>
</table>
</html:form>
</body>
</html>
