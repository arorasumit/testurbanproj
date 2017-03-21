<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ROHIT VERMA	4-Mar-11	CSR00-05422		Created By and Modified by -->
<!--[002]	 SAURABH SINGH	16-Feb-12	CSR00-05422		Restrict User From Saving Attributes For Cancel Service-->
<!-- [101010] RAM PRATAP    06-03-2013   removed 'break;' statement  -->
<!--[004]	 Kalpana		13-JUN-13	CBR-CSR-20130404-XX-08714     For M6 Editable fields ,added isServiceSummReadonly to make the fields readonly and disable if flag=1  -->
<!-- [005] VIPIN 8-Jan-2014 Code added to populate all values for dropdown attributes added for service Managed Colo (21)  -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
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
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script> 
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">

<script type="text/javascript">
var isView = <%=request.getParameter("isView2")%>;
var pc = <%=request.getParameter("pc")%>;
var serviceNo = <%=request.getParameter("hdnserviceid")%>;
var serviceTypeId = <%=request.getParameter("hdnserviceTypeId")%>;
var lineType = "EXISTING";

function getTip(value)
{	
	Tip("<Table border=0> "+ value +"   </table>");	
}

function getTip_DD(obj,value,objName)
{	
	var SelectedValue;
	var selIndex = value;
	var myForm=document.getElementById('searchForm')
	SelectedValue=document.getElementById(objName).options[selIndex].text	
	var combo = document.getElementById(objName);
	combo.title = SelectedValue;
}
var path='<%=request.getContextPath()%>';
var serviceList ;
var KeyValuesList;
var DropAndCarryDataForCCList;
var callerWindowObj = dialogArguments;
	function setFrmValue()
	{
		
		var callerWindowObj = dialogArguments;
		//TRNG22032013026 added by manisha start
		if(pc==1)
		{
			document.getElementById('hdnServiceNo').value = serviceNo;
		}
		else
		{
			document.getElementById('hdnServiceNo').value = callerWindowObj.document.getElementById('hdnserviceid').value;
			
		}
		//TRNG22032013026 added by manisha end
		
		
		/*if(callerWindowObj.document.getElementById('chk'+cntr).value=="Yes")
		{	var cntr = callerWindowObj.document.getElementById('hdnCurrentTempCounter').value;
			document.searchForm.btnAddContact.style.visibility="hidden";
		}
		else
		{
			document.searchForm.btnAddContact.style.visibility="visible";
		}*/
			
			var myForm=document.getElementById('searchForm')
			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			var sessionid ='<%=request.getSession().getId() %>';
			
			serviceList = jsonrpc.processes.getFieldValidationForService(sessionid,document.getElementById('hdnServiceNo').value);
			//serviceList = roleFeild.list[0];
			initFieldValidation();
	}	
	
	function initFieldValidation()
	{

			var myForm=document.getElementById('searchForm')
			var ctrlArry = new Array();
			
			for(var i=0;i< serviceList.list.length; i++)
			{
				ctrlArry[i] =  serviceList.list[i];
				ctrlArry[i].name =  serviceList.list[i].feildName;
				
			}
			
			fieldRoleMappingValidation(serviceList,ctrlArry);
			
				
	}
	function fieldRoleMappingValidation(lstFieldList,ctrlArry)
	{

			var myForm=document.getElementById('searchForm')
			
			for (iField = 0 ; iField < lstFieldList.list.length; iField++)
    		{
	 			for(iCtrl=0,count=1;iCtrl< ctrlArry.length;iCtrl++,count++)
				{					
					  if(ctrlArry[iCtrl].name==undefined)
					  {
					  
						  for(ictrlName=0;ictrlName<ctrlArry[iCtrl].length;ictrlName++)
						  {					  
						   ctrl = ctrlArry[iCtrl];
						   ctrlName = ctrl[ictrlName].name;				
							if(ctrlName==lstFieldList.list[iField].feildName)
							{
								if(lstFieldList.list[iField].isServiceSummReadonly==1)
								{	
									ctrl[ictrlName].disabled  = true;
									document.getElementById('prodAttValue_'+count).className = "inputBorder1";
								}else{
							  
								if(lstFieldList.list[iField].isReadOnly=="1")
								{
									
									if(ctrl[ictrlName].type=="select-one")
										ctrl[ictrlName].disabled  = true;
									else
									if(ctrl[ictrlName].type=="button")
									{
										ctrl[ictrlName].disabled="disabled";
									}	
									else
									    ctrl[ictrlName].readOnly  = true;		
								}
								else
								{
									if(ctrl[ictrlName].type=="select-one")
										ctrl[ictrlName].disabled  = false;
									else
									    ctrl[ictrlName].readOnly  = false;		
								}
								document.getElementById('prodAttValue_'+count).isRequired = lstFieldList.list[iField].isMand;
								ctrl[ictrlName].Disp = lstFieldList.list[iField].fieldLabel;
								//---[012]---Start
								if(document.getElementById('prodAttValue_'+count)=="0")
									document.getElementById('prodAttValue_'+count).className = "inputBorder2";
									
								else
								document.getElementById('prodAttValue_'+count).className = "inputBorder1";
								//---[012]---End
							}
							}
						  }
					  }
					  else	  
					  {
					 
					    ctrlName = ctrlArry[iCtrl].name;
						if(ctrlName==lstFieldList.list[iField].feildName)
						{
						if(lstFieldList.list[iField].isServiceSummReadonly==1)
							{	
								document.getElementById('prodAttValue_'+count).disabled=true;
								document.getElementById('prodAttValue_'+count).className = "inputBorder2";
							}else{
							if(lstFieldList.list[iField].isReadOnly=="1")
							{
							
								if(ctrlArry[iCtrl].type=="select-one")
									ctrlArry[iCtrl].disabled  = true;
								else
								if(ctrlArry[iCtrl].type=="button")
									ctrlArry[iCtrl].disabled="disabled";
								else
								    ctrlArry[iCtrl].readOnly  = true;		
								
							}
							else
							{
								if(ctrlArry[iCtrl].type=="select-one")
									ctrlArry[iCtrl].disabled  = false;
								else
								    ctrlArry[iCtrl].readOnly  = false;		
								
							}
							document.getElementById('prodAttValue_'+count).isRequired = lstFieldList.list[iField].isMand;
							if(document.getElementById('prodAttValue_'+count).isRequired=="0")
							{
								document.getElementById('prodAttValue_'+count).className = "inputBorder2";
							}
							else
							{
								document.getElementById('prodAttValue_'+count).className = "inputBorder1";
							}
						}
						}
					  }						
				}	
			}
	}
	function validateServicePrdAttValues()
	{
		var countAttributes=document.getElementById('attCount').value;
		setFormBean(document.forms[0]);
		for(i=1;i<=countAttributes;i++)
		{
			/*if(document.getElementById('prodIsMandatory_'+i).value=="Y" && document.getElementById('prodAttValue_'+i).value=="") 
			{
				alert("Please Input Service Product Attributes Details!!");
				document.getElementById('prodAttValue_'+i).focus();
				return false;
			}*/
			
			if((document.getElementById('prodDataTypes_'+i).value == 'DROPDOWN') && document.getElementById('prodAttValue_'+i).isRequired==1 )
			{
				if( selectDropdownValue(document.getElementById('prodAttValue_'+i).value,document.getElementById('prodLabels_'+i).value) == false){
					//[101010]START
					//break;
					return false;
					//[101010]END
				}
				
			}
			else if(document.getElementById('prodAttValue_'+i).isRequired==1 && document.getElementById('prodAttValue_'+i).value=="") 
			{
				alert("Please Input Service Product Attributes Details!!");
				document.getElementById('prodAttValue_'+i).focus();
				return false;
			}
		}
		//return false;
	}
	function selectDropdownValue(ctrl , label)
	{
	
	if(ctrl==0) 
	{
		alert('Please Select ' + label + '!!');
		//document.getElementById(ctrl).focus();
		return false;
	}
	}
   function insertTProductAttDetail()
	{

		var serviceId = document.getElementById('hdnServiceNo').value;	

		var callerWindowObj = dialogArguments;
		var cntr = callerWindowObj.document.getElementById('hdnCurrentTempCounter').value;
		if(callerWindowObj.document.getElementById('chk'+(cntr-1)).value=="Yes")
		{	
			alert("Service already published \n Cannot Edit values");
			return false;
		}
		
		//Start[002]
		if(callerWindowObj.document.getElementById('serviceStatus'+(cntr)).value=="CancelAndCopy" || callerWindowObj.document.getElementById('serviceStatus'+(cntr)).value=="Cancelled in CRM")
		{	
			alert("Service cancelled , you cant save now !!");
			return false;
		}
		//End[002]
		
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		newOrderStatusServiceId = jsonrpc.processes.newOrderStatusServiceId(serviceId);
		
		if(serviceId == newOrderStatusServiceId)
		{
			alert("Service already published \n Cannot Edit values");
			return false;
		}
		
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
					/* if(!(checkdigits(document.searchForm.prodAttValue[i]))) */
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
			else if((stage=="Partial Publish" || stage=="Partial Initiated") && (shortCode=="COPC" || shortCode=="SED"))
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
				   		
				   		//var lsi = document.searchForm.prodAttValue.value;
				   		//var callerWindowObj = dialogArguments;
				   		 //Meenakshi: added for signage Child validation
				   		//var orderNo=document.searchForm.prodAttValue.value;
						//added by kalpana to validate prodIdValue for above attribute id
				   		var	 orderNo=document.searchForm.prodAttValue[i].value;
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
		//[003] start
		var stringarrayMaxlenth = new Array();
		//[003]end
		if(document.searchForm.prodAttId.length == undefined)
		{
			stringarrayLabel[0]= document.searchForm.prodAttValue.value;
			stringarrayValue[0]= document.searchForm.prodAttId.value;
			stringarrayDataTypes[0]=document.searchForm.prodDataTypes.value;
			stringarrayLabels[0]=document.searchForm.prodLabels.value;
			////[003] start
			stringarrayMaxlenth[0]=document.searchForm.prodAttriMaxlength.value;
			//[003] end
			if(document.searchForm.prodAttValue.isRequired == 1)
			{
				stringarrayIsMandatory[0]='Y';
			}
			else 
			{
				stringarrayIsMandatory[0]='N';
			}
			stringarrayIsMandatory[0]=document.searchForm.prodAttValue.isRequired;
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
				////[003] start
				stringarrayMaxlenth[i]=document.searchForm.prodAttriMaxlength[i].value;
				//[003]end
				if(document.searchForm.prodAttValue[i].isRequired == 1)
				{
					stringarrayIsMandatory[i]='Y';
				}
				else 
				{
					stringarrayIsMandatory[i]='N';
				}
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
	//TRNG22032013026 added by manisha start
	if(pc==1)
		{
			document.getElementById('button').disabled=true;
			if(document.searchForm.prodAttId == undefined)
			{
				return false;
			}		
			for(var i=0;i<document.searchForm.prodAttId.length;i++)
			{				
				document.searchForm.prodAttValue[i].disabled=true;	
			}
			
		}
		
	else
	{	
		//TRNG22032013026 added by manisha end
		
		var stage = callerWindowObj.document.getElementById('stageName').value;
		
		if(isView == 1 || stage == 'Published' || stage == 'Billing Trigger')
		{
			//alert(document.searchForm.prodAttId);
			document.getElementById('button').disabled=true;
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
}
// Start Amit Sharma
function getprchildData()
{
	
	if(serviceTypeId == 181)
	{
		var serviceId=serviceNo;
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
						
						if(document.searchForm.prodAttId[i].value == KeyValuesList.list[j])
						{
							if(DropAndCarryDataForCCList.list[0].serviceFlavor = "DC"){
								if(DropAndCarryDataForCCList.list[0].circuitType == "DROP")
								{
						 			document.searchForm.prodAttValue[i].disabled=true;
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
	//Gunjan Start
	
	<%-- jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC"); --%>
	LayerRateAttIds = jsonrpc.processes.getLayerRateAttIds();
	if(document.searchForm.prodAttId.length == undefined)
	{	
		return;
	}		
	for(var i=0;i<document.searchForm.prodAttId.length;i++)
	{
		for(j=0;j<LayerRateAttIds.list.length;j++){
			
			if(document.searchForm.prodAttId[i].value==LayerRateAttIds.list[j]){
				
				if(document.searchForm.prodAttValue[i].value==0){
					document.searchForm.prodAttValue[i].value=6;  //6 IS ID FOR DEFAULT VALUE 'MBPS'
				}
			}
		}
		
		
	}
	//Gunjan End
	
	
}
//End Amit Sharma
</script>

<body onload="setFrmValue();disableAttributesInView();getprchildData();">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
<html:form action="/NewOrderAction" styleId="searchForm" method="post">
	<input type="hidden" id="hdnServiceNo" name="hdnServiceNo" >	
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
								    <select onfocus="getTip_DD(this,this.selectedIndex,this.id)" name="prodAttValue"  id="prodAttValue_<%=sno %>">
										<logic:iterate id="attLabel" name="tpDetailID" property="serviceSummary" >
											<bean:define id="storedValue" value="0"  type="java.lang.Object"/>
											<bean:define id="currDDValue" value="1" type="java.lang.Object"/>
											<logic:present name="tpDetailID" property="prodAttributeValue"  >
												<bean:define id="storedValue" name="tpDetailID" property="prodAttributeValue"  type="java.lang.Object"/>
												<bean:define id="currDDValue" name="attLabel" property="serviceSummaryValues" type="java.lang.Object" />
											</logic:present>									
											<!-- [005] Start-->									
										   	<logic:equal name="tpDetailID" property="attMasterId" value="81">
											<% 											
												if(("3".equals(((FieldAttibuteDTO)attLabel).getServiceSummaryValues())))
												{%>
										   		<option value="<bean:write name="attLabel" property="serviceSummaryValues"/>" 
										   <%if(Utility.fnCheckNull((Object)storedValue).equals((String)currDDValue)) {%>selected="selected"<%} %> 
										   title="<bean:write name="attLabel" property="serviceSummaryText"/>">
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
								    <select  name="prodAttValue"  id="prodAttValue_<%=sno %>">
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
							         <input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" isRequired=0  name="prodAttValue" id="prodAttValue_<%=sno %>" 
							     		value="<bean:write name="tpDetailID" property="prodAttributeValue"/>" maxlength="<%=((ProductCatelogDTO)tpDetailID).getProdAttributeMaxlength()%>" 
							    		<%if("VARCHAR".equalsIgnoreCase(((ProductCatelogDTO)tpDetailID).getExpectedValue())){%> onblur="if( trim(this.value).length>0){  return ValidateTextField(this,path,'<%=((ProductCatelogDTO)tpDetailID).getAttMasterName() %>')};" onkeyup="CheckMaxLength(this,<%=((ProductCatelogDTO)tpDetailID).getProdAttributeMaxlength()%>)" <%}%>
							    		<%if("NUMERIC".equalsIgnoreCase(((ProductCatelogDTO)tpDetailID).getExpectedValue())){%>onblur="if(this.value.length > 0){return checknumber(this)}" onkeyup="CheckMaxLength(this,<%=((ProductCatelogDTO)tpDetailID).getProdAttributeMaxlength()%>)" <%}%>
							    		<%if ("YN".equalsIgnoreCase(((ProductCatelogDTO)tpDetailID).getExpectedValue()))  {%>onblur="if(this.value.length > 0){return YN(this)}" <% } %>
 									    <%if("DATETIME".equalsIgnoreCase(((ProductCatelogDTO)tpDetailID).getExpectedValue())){%>readonly="readonly" <%}%>
							    		<%if("EMAIL".equalsIgnoreCase(((ProductCatelogDTO)tpDetailID).getExpectedValue())){%>onblur="if(this.value.length > 0){return emailValidate1(this)}" <%}%>
							    		> 
							    	</logic:notEqual>
							    	<%if("DATETIME".equals(((ProductCatelogDTO)tpDetailID).getExpectedValue())){%>
							    	<font size="1">
							    		<a href="javascript:show_calendar(searchForm.prodAttValue_<%=sno %>);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."  style="vertical-align: bottom"/></a>
									</font>
							    	<%}%>
							    <input type="hidden"  name="prodAttId" value="<bean:write name="tpDetailID" property="attMasterId"/>">
							    <input type="hidden"  name="prodDataTypes" id="prodDataTypes_<%=sno %>" value="<bean:write name="tpDetailID" property="expectedValue"/>">
							    <input type="hidden"  name="prodLabels" id="prodLabels_<%=sno %>" value="<bean:write name="tpDetailID" property="attMasterName"/>">
							    <input type="hidden"  name="prodIsMandatory" id="prodIsMandatory_<%=sno %>" value="<bean:write name="tpDetailID" property="mandatory"/>">
							    <input type="hidden"  name="prodAttriMaxlength" id="prodAttriMaxlength_<%=sno %>" value="<bean:write name="tpDetailID" property="prodAttributeMaxlength"/>">
							    <input type="hidden"  name="prodIsCommercial" id="comAttr" value="<bean:write name="tpDetailID" property="commercial"/>">
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
						<input type="button" id="button" name="btnAddContact" value="Submit" onClick="insertTProductAttDetail();" style="visibility: visible">
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
<script type = "text/javascript" >
var roleName = <%=objUserDto.getUserRoleId() %>;
var roleWiseSectionDetail;
var path = '<%=request.getContextPath()%>' ;
var serSec = new Array();
	serSec[0]="SERVICE";
	serSec[1]="tablePO";
	if(null == roleWiseSectionDetail){
    	roleWiseSectionDetail = getRoleWiseSectionDetails(roleName);
    }
	enableDisableCommercialFields(serSec, roleWiseSectionDetail, lineType);
</script>
