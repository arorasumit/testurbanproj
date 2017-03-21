<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<html>
<head>
<title>Product Configurator</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">

var counter = 1;
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}

function fnCheckAll()
{
	ids=document.forms[0].chkbox;
		if(ids==null)
		{	
			alert('No Request Present.');
			document.getElementById('chkHead').checked=false;
			return false;
		}

	var i;
	if(document.getElementById("chkHead").checked==true)
	{	
		if(document.forms[0].chkbox.length!=null)
		{	for(i=0;i<document.forms[0].chkbox.length;i++ )
			{
				document.forms[0].chkbox[i].checked=true;
				attributeId=document.forms[0].chkbox[i].value;
				
				document.getElementById('oeParentID'+attributeId).disabled=false;
				document.getElementById('m6ParentID'+attributeId).disabled=false;
				document.getElementById('m6ChildID'+attributeId).disabled=false;
				document.getElementById('chkboxFX'+attributeId).disabled=false;
				document.getElementById('chkboxM6'+attributeId).disabled=false;
				document.getElementById('chkboxSS'+attributeId).disabled=false;
				document.getElementById('chkboxBI'+attributeId).disabled=false;
				document.getElementById('chkboxCI'+attributeId).disabled=false;
				document.getElementById('chkboxHI'+attributeId).disabled=false;
				document.getElementById('chkboxSL'+attributeId).disabled=false;
				
			}
		}
		else
		{
			document.forms[0].chkbox.checked=true;
			attributeId=document.forms[0].chkbox.value;
			attributeId=document.forms[0].chkbox[i].value;
				
				document.getElementById('oeParentID'+attributeId).disabled=false;
				document.getElementById('m6ParentID'+attributeId).disabled=false;
				document.getElementById('m6ChildID'+attributeId).disabled=false;
				document.getElementById('chkboxFX'+attributeId).disabled=false;
				document.getElementById('chkboxM6'+attributeId).disabled=false;
				document.getElementById('chkboxSS'+attributeId).disabled=false;
				document.getElementById('chkboxBI'+attributeId).disabled=false;
				document.getElementById('chkboxCI'+attributeId).disabled=false;
				document.getElementById('chkboxHI'+attributeId).disabled=false;
				document.getElementById('chkboxSL'+attributeId).disabled=false;
			
		}
	}
	else
	{
		if(document.forms[0].chkbox.length!=null)
		{
			for(i=0;i<document.forms[0].chkbox.length;i++ )
			{
				document.forms[0].chkbox[i].checked=false;
				attributeId=document.forms[0].chkbox[i].value;
				attributeId=document.forms[0].chkbox[i].value;
				
				document.getElementById('oeParentID'+attributeId).disabled=true;
				document.getElementById('m6ParentID'+attributeId).disabled=true;
				document.getElementById('m6ChildID'+attributeId).disabled=true;
				document.getElementById('chkboxFX'+attributeId).disabled=true;
				document.getElementById('chkboxM6'+attributeId).disabled=true;
				document.getElementById('chkboxSS'+attributeId).disabled=true;
				document.getElementById('chkboxBI'+attributeId).disabled=true;
				document.getElementById('chkboxCI'+attributeId).disabled=true;
				document.getElementById('chkboxHI'+attributeId).disabled=true;
				document.getElementById('chkboxSL'+attributeId).disabled=true;
			}
		}
		else
		{
			document.forms[0].chkbox.checked=false;
			attributeId=document.forms[0].chkbox.value;
		
				document.getElementById('oeParentID'+attributeId).disabled=true;
				document.getElementById('m6ParentID'+attributeId).disabled=true;
				document.getElementById('m6ChildID'+attributeId).disabled=true;
				document.getElementById('chkboxFX'+attributeId).disabled=true;
				document.getElementById('chkboxM6'+attributeId).disabled=true;
				document.getElementById('chkboxSS'+attributeId).disabled=true;
				document.getElementById('chkboxBI'+attributeId).disabled=true;
				document.getElementById('chkboxCI'+attributeId).disabled=true;
				document.getElementById('chkboxHI'+attributeId).disabled=true;
				document.getElementById('chkboxSL'+attributeId).disabled=true;
		}	
		
	}

}
function checkValue(obj, str){

	var str1 = checkdigits(obj)
	if(!str1) {
		return false;
	} else {
		if(obj.value == "0"){
		alert(str + ' Cannot be 0');
		obj.value ="";
		obj.focus();
	 	return false;
		}
	
	}
 		
 				
 			
}

function removeCheckHead()
{
	javascript:document.getElementById('chkHead').checked=false;
	var temp_chk=0;
		if(document.forms[0].chkbox.length!=null)
		{
			for(i=0;i<document.forms[0].chkbox.length;i++ )
			{
				if(document.forms[0].chkbox[i].checked==true)
				{
					attributeId=document.forms[0].chkbox[i].value;
					
					document.getElementById('oeParentID'+attributeId).disabled=false;
				document.getElementById('m6ParentID'+attributeId).disabled=false;
				document.getElementById('m6ChildID'+attributeId).disabled=false;
				document.getElementById('chkboxFX'+attributeId).disabled=false;
				document.getElementById('chkboxM6'+attributeId).disabled=false;
				document.getElementById('chkboxSS'+attributeId).disabled=false;
				document.getElementById('chkboxBI'+attributeId).disabled=false;
				document.getElementById('chkboxCI'+attributeId).disabled=false;
				document.getElementById('chkboxHI'+attributeId).disabled=false;
				document.getElementById('chkboxSL'+attributeId).disabled=false;
					temp_chk=temp_chk+1;
				}else if(document.forms[0].chkbox[i].checked==false){
				
				
					attributeId=document.forms[0].chkbox[i].value;
					
				document.getElementById('oeParentID'+attributeId).disabled=true;
				document.getElementById('m6ParentID'+attributeId).disabled=true;
				document.getElementById('m6ChildID'+attributeId).disabled=true;
				document.getElementById('chkboxFX'+attributeId).disabled=true;
				document.getElementById('chkboxM6'+attributeId).disabled=true;
				document.getElementById('chkboxSS'+attributeId).disabled=true;
				document.getElementById('chkboxBI'+attributeId).disabled=true;
				document.getElementById('chkboxCI'+attributeId).disabled=true;
				document.getElementById('chkboxHI'+attributeId).disabled=true;
				document.getElementById('chkboxSL'+attributeId).disabled=true;
					temp_chk=temp_chk+1;
				
				}
			}
		}
		else 
		{	if(document.forms[0].chkbox.checked==true) {
					attributeId=document.forms[0].chkbox.value;
					
					document.getElementById('oeParentID'+attributeId).disabled=false;
				document.getElementById('m6ParentID'+attributeId).disabled=false;
				document.getElementById('m6ChildID'+attributeId).disabled=false;
				document.getElementById('chkboxFX'+attributeId).disabled=false;
				document.getElementById('chkboxM6'+attributeId).disabled=false;
				document.getElementById('chkboxSS'+attributeId).disabled=false;
				document.getElementById('chkboxBI'+attributeId).disabled=false;
				document.getElementById('chkboxCI'+attributeId).disabled=false;
				document.getElementById('chkboxHI'+attributeId).disabled=false;
				document.getElementById('chkboxSL'+attributeId).disabled=false;
					temp_chk=temp_chk+1;
				} else if(document.forms[0].chkbox.checked==false)
		{
		
				attributeId=document.forms[0].chkbox.value;
					
				document.getElementById('oeParentID'+attributeId).disabled=true;
				document.getElementById('m6ParentID'+attributeId).disabled=true;
				document.getElementById('m6ChildID'+attributeId).disabled=true;
				document.getElementById('chkboxFX'+attributeId).disabled=true;
				document.getElementById('chkboxM6'+attributeId).disabled=true;
				document.getElementById('chkboxSS'+attributeId).disabled=true;
				document.getElementById('chkboxBI'+attributeId).disabled=true;
				document.getElementById('chkboxCI'+attributeId).disabled=true;
				document.getElementById('chkboxHI'+attributeId).disabled=true;
				document.getElementById('chkboxSL'+attributeId).disabled=true;
					temp_chk=temp_chk+1;
			
		}
		}
		
}

function fnUpdate(){
	
	    var csvVal1="";
		var csvVal2="";
		var csvVal3="";
		var csvVal4="";
		var csvVal5="";
		var csvVal6="";
		var csvVal7="";
		var csvVal8="";
		var csvVal9="";
		var csvVal10="";
		var csvVal11="";
		

		var objCnt="";
		if(document.forms[0].chkbox==null)
		{
		
		return;
		
		}
		if(document.forms[0].chkbox.length!=null)
		{
			for(i=0;i<document.forms[0].chkbox.length;i++)
			{
				if(document.forms[0].chkbox[i].checked==true)
				{
					
					attributeId=document.forms[0].chkbox[i].value;
					
					
					objCnt=document.getElementById('attID'+attributeId);
					val1=objCnt.innerText;
					csvVal1=csvVal1+","+val1;
					
					objCnt=document.getElementById('oeParentID'+attributeId);
					val2=objCnt.value;
					csvVal2=csvVal2+","+val2;
					
					objCnt=document.getElementById('m6ParentID'+attributeId);
					val3=objCnt.value;
					csvVal3=csvVal3+","+val3;
					
					objCnt=document.getElementById('m6ChildID'+attributeId);
					val4=objCnt.value;
					csvVal4=csvVal4+","+val4;
					
					objCnt=document.getElementById('chkboxFX'+attributeId);
					if(objCnt.checked)
					{
						objCnt.value="1"
						csvVal5=csvVal5+","+objCnt.value;
					}else{
					
						objCnt.value="0"
						csvVal5=csvVal5+","+objCnt.value;
					
					}
					
					
					objCnt=document.getElementById('chkboxM6'+attributeId);
					if(objCnt.checked)
					{
						objCnt.value="1"
						csvVal6=csvVal6+","+objCnt.value;
					}else{
					
						objCnt.value="0"
						csvVal6=csvVal6+","+objCnt.value;
					
					}
					
					
					objCnt=document.getElementById('chkboxSS'+attributeId);
					if(objCnt.checked)
					{
						objCnt.value="1"
						csvVal7=csvVal7+","+objCnt.value;
					}else{
					
						objCnt.value="0"
						csvVal7=csvVal7+","+objCnt.value;
					
					}
					
					objCnt=document.getElementById('chkboxBI'+attributeId);
					if(objCnt.checked)
					{
						objCnt.value="1"
						csvVal8=csvVal8+","+objCnt.value;
					}else{
					
						objCnt.value="0"
						csvVal8=csvVal8+","+objCnt.value;
					
					}
					
					objCnt=document.getElementById('chkboxCI'+attributeId);
					if(objCnt.checked)
					{
						objCnt.value="1"
						csvVal9=csvVal9+","+objCnt.value;
					
					}else{
					
						objCnt.value="0"
						csvVal9=csvVal9+","+objCnt.value;
					
					}
					
					objCnt=document.getElementById('chkboxHI'+attributeId);
					if(objCnt.checked)
					{
						objCnt.value="1"
						csvVal10=csvVal10+","+objCnt.value;
					}else{
					
						objCnt.value="0"
						csvVal10=csvVal10+","+objCnt.value;
					
					}
					
					objCnt=document.getElementById('chkboxSL'+attributeId);
					if(objCnt.checked)
					{
						objCnt.value="1"
						csvVal11=csvVal11+","+objCnt.value;
					}else{
					
						objCnt.value="0"
						csvVal11=csvVal11+","+objCnt.value;
					}
				

				}
			}
		
		}
		else
		{
			if(document.forms[0].chkbox.checked==true)
				{
					
					attributeId=document.forms[0].chkbox.value;
					
					
					objCnt=document.getElementById('attID'+attributeId);
					val1=objCnt.innerText;
					csvVal1=csvVal1+","+val1;
					
					objCnt=document.getElementById('oeParentID'+attributeId);
					val2=objCnt.value;
					csvVal2=csvVal2+","+val2;
					
					objCnt=document.getElementById('m6ParentID'+attributeId);
					val3=objCnt.value;
					csvVal3=csvVal3+","+val3;
					
					objCnt=document.getElementById('m6ChildID'+attributeId);
					val4=objCnt.value;
					csvVal4=csvVal4+","+val4;
					
					objCnt=document.getElementById('chkboxFX'+attributeId);
					csvVal5=csvVal5+","+objCnt.value;
					
					objCnt=document.getElementById('chkboxM6'+attributeId);
					csvVal6=csvVal6+","+objCnt.value;
					
					objCnt=document.getElementById('chkboxSS'+attributeId);
					csvVal7=csvVal7+","+objCnt.value;
					
					objCnt=document.getElementById('chkboxBI'+attributeId);
					csvVal8=csvVal8+","+objCnt.value;
					
					objCnt=document.getElementById('chkboxCI'+attributeId);
					csvVal9=csvVal9+","+objCnt.value;
					
					objCnt=document.getElementById('chkboxHI'+attributeId);
					csvVal10=csvVal10+","+objCnt.value;
					
					objCnt=document.getElementById('chkboxSL'+attributeId);
					csvVal11=csvVal11+","+objCnt.value;
			
				}
			
		}
		document.forms[0].attIdList.value=csvVal1;
		document.forms[0].attOEParentIdList.value=csvVal2;
		document.forms[0].attM6ParentIdList.value=csvVal3;
		document.forms[0].attM6ChildIdList.value=csvVal4;
		document.forms[0].attSend2FXList.value=csvVal5;
		document.forms[0].attSend2M6List.value=csvVal6;
		document.forms[0].attServiceSummaryList.value=csvVal7;
		document.forms[0].attBillInfoList.value=csvVal8;
		document.forms[0].attChargeInfoList.value=csvVal9;
		document.forms[0].attHarwareInfoList.value=csvVal10;
		document.forms[0].attServiceLocationList.value=csvVal11;
		
		if(document.forms[0].attIdList.value==0){
		
		alert("Please select an Attribute");
		return false;
		
		}
		
				
		document.forms[0].action="<%=request.getContextPath()%>/productConfiguratorAction.do?method=updateProductConfigAttributes";
		document.forms[0].submit();
}

function fetchAttributes()
{

	var myForm = document.getElementById('searchForm');
	
	var serviceId=document.getElementById("serviceList1").value;
	myForm.hiddenServiceTypeId.value=serviceId;
	
	myForm.action="<%=request.getContextPath()%>/productConfiguratorAction.do?method=getServiceAttributeList";

    myForm.submit();

}

path='<%=request.getContextPath()%>';
</script>

<body >
	<html:form action="/productConfiguratorAction" styleId="searchForm" method="post">
		<bean:define id="formBean" name="masterAttributesBean"></bean:define>
		<input type="hidden" name="method" />
		<html:hidden property="hiddenServiceTypeId" />
		<html:hidden property="attIdList" />
		<html:hidden property="attOEParentIdList" />
		<html:hidden property="attM6ParentIdList" />
		<html:hidden property="attM6ChildIdList" />
		<html:hidden property="attSend2FXList" />
		<html:hidden property="attSend2M6List" />
		<html:hidden property="attBillInfoList" />
		<html:hidden property="attServiceSummaryList" />
		<html:hidden property="attChargeInfoList" />
		<html:hidden property="attHarwareInfoList" />
		<html:hidden property="attServiceLocationList" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> <span>PRODUCT CONFIGURATOR</span> </div>
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
		<fieldset border="1" align="center" class="border2" >
			<legend > <b>Product Configuration Details</b></legend>
			<br/>
			<div  style="height:40px; width:945px;">
			<table border="0" cellspacing="0" cellpadding="0" style="margin-top:7px " width="100%">
				<tr>
				<td align="right" style="font-size:9px" width="50%"><b>Select Service Type</b></td>
				<td align="left" style="font-size:9px" width="50%">
				<html:select property="serviceList" styleId="serviceList1" styleClass="inputBorder1" style="width:110px;" onchange="javascript:fetchAttributes();">
					<html:option value="0">---Select---</html:option>
					<html:optionsCollection name="masterAttributesBean" property="services"  value="serviceTypeId" label="serviceType"></html:optionsCollection>
				</html:select>
				</td>
				</tr>
						
			</table>	
		</div>
		<div  style="height:40px; width:945px;">
	<table class="tab2" style="overflow:auto;" width="99%" border="1"
		align="center">
		<tr class="normal">
			<td align="center" style="font-size:9px" width="3%">&nbsp; <input
				type="checkbox" id="chkHead" onclick="fnCheckAll();" class="none" />
			</td>
			<td align="center" style="font-size:9px" width="79"><b>OE ID</b></td>
			<td align="center" style="font-size:9px" width="138"><b>Product
			Name</b></td>
			<td align="center" style="font-size:9px" width="54"><b>OE
			Parent ID</b></td>
			<td align="center" style="font-size:9px" width="66"><b>M6
			Parent ID</b></td>
			<td align="center" style="font-size:9px" width="66"><b>M6
			Child ID</b></td>
			<td align="center" style="font-size:9px" width="19"><b>FX</b></td>
			<td align="center" style="font-size:9px" width="37"><b>M6</b></td>
			<td align="center" style="font-size:9px" width="117"><b>Service Type</b></td>
			<td align="center" style="font-size:9px" width="22"><b>Service Summary</b></td>
			<td align="center" style="font-size:9px"><b>Billing Info</b></td>
			<td align="center" style="font-size:9px"><b>Charge Info</b></td>
			<td align="center" style="font-size:9px" width="27"><b>Hardware Info</b></td>
			<td align="center" style="font-size:9px" width="49"><b>Service
			Location</b></td>

		</tr>
		<logic:present name="attributeList" scope="request">
			<logic:notEmpty name="attributeList" scope="request">
				<logic:iterate id="row" name="attributeList" indexId="recordId">
					<%
							String classType=null;
							if((recordId.intValue())%2==0) {
						 classType="lightBg" ;
						 }else{ 
						 classType="normal";
						 } %>
						 <tr class="<%=classType %>">
						<td align="center" style="font-size:9px" width="3%"><input
							type="checkbox" id="chkbox" onclick="removeCheckHead();"
							class="none" value="<bean:write name="row" property="attID"/>" />
						</td>
						<td width="11%">&nbsp; <bean:write name="row"
							property="attID" /></td>
						<td align="left" width="12%">&nbsp; <bean:write name="row"
							property="attDescription" /></td>
						<td width="14%">&nbsp;<input type="text" style="width:60px;"
							id="oeParentID<bean:write name="row" property="attID"/>"
							class="inputBorder1" disabled="true" name="oeParentID"
							value="<bean:write  name="row" property="oeParentID"/>"
							onblur="if( trim(this.value).length>=0){return checkValue(this, 'OE PARENT ID');};" />
						</td>
						<td width="14%">&nbsp; <input type="text" style="width:60px;"
							id="m6ParentID<bean:write name="row" property="attID"/>"
							class="inputBorder1" disabled="true" name="m6ParentID"
							value="<bean:write  name="row" property="m6ParentID"/>"
							onblur="if( trim(this.value).length>=0){return checkValue(this, 'M6 PARENT ID');};" />
						</td>
						<td width="14%">&nbsp; <input type="text" style="width:60px;"
							id="m6ChildID<bean:write name="row" property="attID"/>"
							class="inputBorder1" name="m6ChildID" disabled="true"
							value="<bean:write  name="row" property="m6ChildID"/>"
							onblur="if( trim(this.value).length>=0){return checkValue(this, 'M6 CHILD ID');};" />
						</td>
						<td align="center" width="2%">&nbsp; <logic:equal value="1"
							name="row" property="sendToFX">
							<input type="checkbox"
								id="chkboxFX<bean:write name="row" property="attID"/>"
								class="none" disabled="true"
								value="<bean:write name="row" property="sendToFX"/>"
								checked="true" />
						</logic:equal> <logic:equal value="0" name="row" property="sendToFX">
							<input type="checkbox"
								id="chkboxFX<bean:write name="row" property="attID"/>"
								class="none" disabled="true"
								value="<bean:write name="row" property="sendToFX"/>" />
						</logic:equal></td>
						<td align="center" width="4%">&nbsp; <logic:equal value="1"
							name="row" property="sendToM6">
							<input type="checkbox"
								id="chkboxM6<bean:write name="row" property="attID"/>"
								class="none" disabled="true"
								value="<bean:write name="row" property="sendToM6"/>"
								checked="true" />
						</logic:equal> <logic:equal value="0" name="row" property="sendToM6">
							<input type="checkbox"
								id="chkboxM6<bean:write name="row" property="attID"/>"
								class="none" disabled="true"
								value="<bean:write name="row" property="sendToM6"/>" />
						</logic:equal></td>
						<td width="14%">&nbsp; <bean:write name="row"
							property="serviceType" /></td>
						<td align="center" width="3%">&nbsp; <logic:equal value="1"
							name="row" property="serviceSummary">
							<input type="checkbox"
								id="chkboxSS<bean:write name="row" property="attID"/>"
								class="none" disabled="true"
								value="<bean:write name="row" property="serviceSummary"/>"
								checked="true" />
						</logic:equal> <logic:equal value="0" name="row" property="serviceSummary">
							<input type="checkbox"
								id="chkboxSS<bean:write name="row" property="attID"/>"
								class="none" disabled="true"
								value="<bean:write name="row" property="serviceSummary"/>" />
						</logic:equal></td>
						<td width="3%" align="center">&nbsp; <logic:equal value="1"
							name="row" property="billingInfo">
							<input type="checkbox"
								id="chkboxBI<bean:write name="row" property="attID"/>"
								class="none" disabled="true"
								value="<bean:write name="row" property="billingInfo"/>"
								checked="true" />
						</logic:equal> <logic:equal value="0" name="row" property="billingInfo">
							<input type="checkbox"
								id="chkboxBI<bean:write name="row" property="attID"/>"
								class="none" disabled="true"
								value="<bean:write name="row" property="billingInfo"/>" />
						</logic:equal></td>
						<td width="3%" align="center">&nbsp; <logic:equal value="1"
							name="row" property="chargeInfo">
							<input type="checkbox"
								id="chkboxCI<bean:write name="row" property="attID"/>"
								class="none" disabled="true"
								value="<bean:write name="row" property="chargeInfo"/>"
								checked="true" />
						</logic:equal> <logic:equal value="0" name="row" property="chargeInfo">
							<input type="checkbox"
								id="chkboxCI<bean:write name="row" property="attID"/>"
								class="none" disabled="true"
								value="<bean:write name="row" property="chargeInfo"/>" />
						</logic:equal></td>
						<td align="center" width="3%">&nbsp; <logic:equal value="1"
							name="row" property="hardwareInfo">
							<input type="checkbox"
								id="chkboxHI<bean:write name="row" property="attID"/>"
								class="none" disabled="true"
								value="<bean:write name="row" property="hardwareInfo"/>"
								checked="true" />
						</logic:equal> <logic:equal value="0" name="row" property="hardwareInfo">
							<input type="checkbox"
								id="chkboxHI<bean:write name="row" property="attID"/>"
								class="none" disabled="true"
								value="<bean:write name="row" property="hardwareInfo"/>" />
						</logic:equal></td>
						<td align="center" width="5%">&nbsp; <logic:equal value="1"
							name="row" property="serviceLocation">
							<input type="checkbox"
								id="chkboxSL<bean:write name="row" property="attID"/>"
								class="none" disabled="true"
								value="<bean:write name="row" property="serviceLocation"/>"
								checked="true" />
						</logic:equal> <logic:equal value="0" name="row" property="serviceLocation">
							<input type="checkbox"
								id="chkboxSL<bean:write name="row" property="attID"/>"
								class="none" disabled="true"
								value="<bean:write name="row" property="serviceLocation"/>" />
						</logic:equal></td>

						<div style="display: none"><span
							id="attID<bean:write name="row" property="attID"/>"><bean:write
							name="row" property="attID" /></span></div>
					</tr>
				</logic:iterate>
				<tr class="tableTd">
					<td colspan="14" align="center" nowrap="nowrap"><input
						type="button" value="Update" onclick="javascript:fnUpdate();" />
					</td>
				</tr>
			</logic:notEmpty>
			<logic:empty name="attributeList">
				<tr align="center">
					<td colspan="14" align="center"><B><font color="red">No
					Record Found</font></B></td>
				</tr>
			</logic:empty>
		</logic:present>
	</table>
	</div>
</fieldset>
</html:form>
</body>
</html>
