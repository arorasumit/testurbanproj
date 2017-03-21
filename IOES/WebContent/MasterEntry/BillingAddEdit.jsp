<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<html>
<head>
<title>Search Billing Address Detail</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/formValidations.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}
<logic:present name="MODULE_SECTION">

function checkForm()
{
	var myForm=document.getElementById('searchForm');

	if(isBlank("account_Name","A\\C") == false) { return false;}
	if(isBlank("title","Title") == false) { return false;}
	if(isBlank("firstname","First Name")  == false) { return false;}
	if(isBlank("lastName","Last Name")  == false) { return false;}
	if(isBlank("telephonePhno","Telephone Number")  == false) { return false;}
	if(isBlank("email_Id","E-Mail")  == false) { return false;}
	if(isBlank("fax","Fax")  == false) { return false;}
	if(isBlank("address1","Address1")  == false) { return false;}
	if(isBlank("address2","Address2")  == false) { return false;}
	if(isBlank("address3","Address3")  == false) { return false;}
	if(isBlank("address4","Address4")  == false) { return false;}
	if(selectDropdown("country","Country")  == false) {  return false;}
	if(selectDropdown("state","State")  == false) {  return false;}
	if(selectDropdown("city","City")  == false) {  return false;}
	if(isBlank("postalCode","Postal Code")  == false) { return false;}
	
	return true;
}

function checkFormEdit()
{
	var myForm=document.getElementById('searchForm');

	if(isBlank("accountName","A\\C") == false) { return false;}
	if(isBlank("title","Title") == false) { return false;}
	if(isBlank("firstname","First Name")  == false) { return false;}
	if(isBlank("lastName","Last Name")  == false) { return false;}
	if(isBlank("telephonePhno","Telephone Number")  == false) { return false;}
	if(isBlank("email_Id","E-Mail")  == false) { return false;}
	if(isBlank("fax","Fax")  == false) { return false;}
	if(isBlank("address1","Address1")  == false) { return false;}
	if(isBlank("address2","Address2")  == false) { return false;}
	if(isBlank("address3","Address3")  == false) { return false;}
	if(isBlank("address4","Address4")  == false) { return false;}
	if(selectDropdown("country","Country")  == false) {  return false;}
	if(selectDropdown("state","State")  == false) {  return false;}
	if(selectDropdown("city","City")  == false) {  return false;}
	if(isBlank("postalCode","Postal Code")  == false) { return false;}
	
	return true;
}
<logic:equal name="MODULE_SECTION" value="NEW_BCP_INIT">
function fillAddAcount()
{
	alert("shri ram");
}
function popitup(url) 
{
	if(url=="SelectAccount")
	{
		var path = "<%=request.getContextPath()%>/bcpAddress.do?method=fetchAccountForBCPAdd";		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
		//window.open(path,"","");
	}
	
	if (window.focus) {window.focus()}
	return false;
}
function addNewCustomerLocation()
{
	var myForm=document.getElementById('searchForm');	
	setFormBean(myForm);
	
	if(checkForm()==false)
	{
	
		return false;
	}	
	
	myForm.action="<%=request.getContextPath()%>/bcpAddress.do?method=addNewBCPLocation";
    myForm.submit(); 
}
</logic:equal>
<logic:equal name="MODULE_SECTION" value="EDIT_BCP_INIT">
function updateCustomerLocation()
{
	
	var myForm=document.getElementById('searchForm');
	setFormBean(myForm);
	if(checkFormEdit()==false)
	{
		return false;
	}

	myForm.action="<%=request.getContextPath()%>/bcpAddress.do?method=updateBCPLocation";
   	myForm.submit();    
}
</logic:equal>
function displayBCPList()
{
	var myForm = document.getElementById('searchForm');
	//myForm.accountIdStr.value="";
	//myForm.accountNameStr.value="";
	//myForm.bcpIdStr.value="";
	//myForm.bcpNameStr.value="";
	myForm.action="<%=request.getContextPath()%>/bcpAddress.do?method=viewBCPList";
	
   myForm.submit();	
}

function fetchState(val)
{
	try{
		var combo1 = document.getElementById("state");
		var frm=document.getElementById('searchForm');
		jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		 var jsData=			
		    {
				countryCode:document.getElementById("country").value
			};
			
		var lstService = jsonrpc.processes.getAllStates(jsData);
		
		for(i=combo1.options.length-1;i>=0 ;i--)
			{
			combo1.options[i] = null;
			}
			
		var cityCombo = document.getElementById("city");
		for(i=cityCombo.options.length-1;i>=0 ;i--)
			{
			cityCombo.options[i] = null;
			}
		
			combo1.options[0] = new Option("---Select---", "0");
			cityCombo.options[0] = new Option("---Select---", "0");
		
		if(lstService!=null)
		{
			
			
			 for(j=0;j<lstService.list.length;j++)
	    	{
		    	var name = lstService.list[j].stateName;
				var id=lstService.list[j].stateId;
				combo1.options[j+1] = new Option(name,id);
	    	}	
		}
		}
		catch(e)
		{}
}

function loadCities()
{
try{
		var combo1 = document.getElementById("city");
		var frm=document.getElementById('searchForm');
		jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		 var jsData=			
		    {
				stateId:document.getElementById("state").value
			};
			
		var lstService = jsonrpc.processes.getAllCities(jsData);
		
		for(i=combo1.options.length-1;i>=0 ;i--)
			{
			combo1.options[i] = null;
			}
		
			combo1.options[0] = new Option("---Select---", "0");
		
		if(lstService!=null)
		{
			
			
			 for(j=0;j<lstService.list.length;j++)
	    	{
		    	var name = lstService.list[j].cityName;
				var id=lstService.list[j].cityId;
				combo1.options[j+1] = new Option(name,id);
	    	}	
		}
		}
		catch(e)
		{}
}

</logic:present>

path='<%=request.getContextPath()%>';
</script>
<body >
	<html:form action="/bcpAddress" styleId="searchForm" method="post">
	<html:hidden property="accountIdStr"/>
	<html:hidden property="accountNameStr"/>
	<html:hidden property="bcpIdStr"/>
	<html:hidden property="bcpNameStr"/>
	<bean:define id="formBean" name="bcpAddressBean"></bean:define>

<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
		<tr align="right">
			<td><img src="./gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
		</tr>
	</table>	
		
		<div class="head"> <span>BCP ADDRESS</span> </div>
		
			<!-- Add New User -->
			<logic:present name="MODULE_SECTION">
			<logic:equal name="MODULE_SECTION" value="NEW_BCP_INIT">
			<table id="tblAddBCP" align="center" width="50%"  >
			<tr>
				<td  align="left" style="font-size:17px" nowrap="nowrap">Add New BCP</td>
			</tr>
			
			<logic:present name="validation_errors">
			<tr><td>
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
				
			<tr>
				<td>
					<table cellpadding="0" align="center" cellspacing="1" width="100%" border="1">
				
						
						<tr>
							<td align="right" style="font-size:9px" nowrap> A\C  
								<html:hidden property="accountID"  />
								
							</td>
							<td><input type="text"  name="account_Name" class="inputBorder1" style="width:200px;float: left;" readonly="readonly" >
								<div class="searchBg1"><a href="#" onClick="return popitup('SelectAccount')">...</a></div> 
								<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Field" width="24" height="18" onclick="javascript:{ document.forms[0].accountID.value='' ; document.forms[0].account_Name.value='' ;}">
							</td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Title</td>
						<td align="left" style="font-size:9px"><html:text property="title" styleId="title" styleClass="inputBorder1" style="width:100px;" maxlength="15" onblur="if( trim(this.value).length>0){  return ValidateTextField(this,path,'Title')};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;First Name</td>
						<td align="left" style="font-size:9px"><html:text  property="firstname" styleId="firstname" styleClass="inputBorder1" style="width:100px;" maxlength="100" onblur="if( trim(this.value).length>0){  return ValidateTextField(this,path,'First Name')};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Last Name</td>
						<td align="left" style="font-size:9px"><html:text  property="lastName" styleId="lastName" styleClass="inputBorder1" style="width:100px;" maxlength="100" onblur="if( trim(this.value).length>0){  return ValidateTextField(this,path,'Last Name')};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Telephone Number</td>
						<td align="left" style="font-size:9px">
									<html:text  property="telephonePhno" styleClass="inputBorder1"  styleId="telephonePhno"
													style="width:100px;" maxlength="50" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;E-Mail</td>
						<td align="left" style="font-size:9px"><html:text  property="email_Id" styleId="email_Id" styleClass="inputBorder1" style="width:100px;" maxlength="150" onblur="if( trim(this.value).length>0){ return emailValidate(this)};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Fax</td>
						<td align="left" style="font-size:9px"><html:text  property="fax" styleId="fax" styleClass="inputBorder1" style="width:100px;" maxlength="50" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Address1</td>
						<td align="left" style="font-size:9px"><html:textarea  property="address1" styleId="address1" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,200)) { return ValidateTextField(this,path,'Address1')}};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Address2</td>
						<td align="left" style="font-size:9px"><html:textarea  property="address2" styleId="address2" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,200)) { return ValidateTextField(this,path,'Address2')}};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Address3</td>
						<td align="left" style="font-size:9px"><html:textarea  property="address3" styleId="address3" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,200)) { return ValidateTextField(this,path,'Address3')}};"/></td><tr >
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Address4</td>
						<td align="left" style="font-size:9px"><html:textarea  property="address4" styleId="address4" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,200)) { return ValidateTextField(this,path,'Address4')}};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Country</td>
						<td align="left" style="font-size:9px">
							<html:select property="country" styleId="country" styleClass="inputBorder1" style="width:110px;" onchange="javascript:fetchState()">
								<html:option value="0">---Select---</html:option>
								<html:optionsCollection name="formBean" property="countries" value="countryCode" label="countryName"></html:optionsCollection>
					</html:select></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;State</td>
						<td align="left" style="font-size:9px">
							<html:select property="state" styleClass="inputBorder1" style="width:110px;" styleId="state" onchange="loadCities()">
								<html:option value="0">---Select---</html:option>
							</html:select></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;City</td>
						<td align="left" style="font-size:9px">
							<html:select property="city" styleClass="inputBorder1" style="width:110px;" styleId="city">
								<html:option value="0">---Select---</html:option>
							</html:select></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Postal Code</td>
						<td align="left" style="font-size:9px"><html:text  property="postalCode" styleId="postalCode" styleClass="inputBorder1" style="width:100px;" maxlength="15" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"/></td>
						</tr>
	
					</table>
					<table align="center">
							<tr>
								<td nowrap="nowrap" ><input type = "button" value="Save" onclick="javascript:addNewCustomerLocation();" /> </td>
								<td nowrap="nowrap" ><input type = "button" value="Cancel" onclick="javascript:displayBCPList();"/> </td>
							</tr>
					</table>

				</td>
			</tr>
			</table>
			</logic:equal>
			
			<!-- Edit User -->
			<logic:equal name="MODULE_SECTION" value="EDIT_BCP_INIT">
			
			<html:hidden property="hiddenBCPId"/>
			<table id="tblEditBCP" align="center" width="50%" >
			<tr>
				<td align="left" style="font-size:17px"  nowrap="nowrap">Edit BCP</td>
			</tr>
			<logic:present name="validation_errors">
			<tr><td>
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
			<tr>
				<td>
					<table cellpadding="0" align="center" cellspacing="1" width="100%" border="1">
				
						
						<tr>
						<td align="right" style="font-size:9px"> A\C  </td>
						<td>
							<html:hidden property="accountID"/>
							<html:text  property="accountName"  styleClass="inputBorder1"  style="width:200pt" readonly="true"/>
						</td>
						</tr>
						<tr >
							<td width="143" align="right"><b>BCP Id</b></td>
							<td><html:text property="bcpId" styleClass="inputBorder1" style="width:100px;" readonly="true"/></td>			
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Title</td>
						<td align="left" style="font-size:9px"><html:text property="title" styleId="title" styleClass="inputBorder1" style="width:100px;" maxlength="15" onblur="if( trim(this.value).length>0){  return ValidateTextField(this,path,'Title')};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;First Name</td>
						<td align="left" style="font-size:9px"><html:text  property="firstname" styleId="firstname" styleClass="inputBorder1" style="width:100px;" maxlength="100" onblur="if( trim(this.value).length>0){  return ValidateTextField(this,path,'First Name')};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Last Name</td>
						<td align="left" style="font-size:9px"><html:text  property="lastName" styleId="lastName" styleClass="inputBorder1" style="width:100px;" maxlength="100" onblur="if( trim(this.value).length>0){  return ValidateTextField(this,path,'Last Name')};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Telephone Number</td>
						<td align="left" style="font-size:9px">
									<html:text  property="telephonePhno" styleClass="inputBorder1"  styleId="telephonePhno"
													style="width:100px;" maxlength="50" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;E-Mail</td>
						<td align="left" style="font-size:9px"><html:text  property="email_Id" styleId="email_Id" styleClass="inputBorder1" style="width:100px;" maxlength="150" onblur="if( trim(this.value).length>0){ return emailValidate(this)};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Fax</td>
						<td align="left" style="font-size:9px"><html:text  property="fax" styleId="fax" styleClass="inputBorder1" style="width:100px;" maxlength="50" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Address1</td>
						<td align="left" style="font-size:9px"><html:textarea  property="address1" styleId="address1" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,200)) { return ValidateTextField(this,path,'Address1')}};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Address2</td>
						<td align="left" style="font-size:9px"><html:textarea  property="address2" styleId="address2" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,200)) { return ValidateTextField(this,path,'Address2')}};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Address3</td>
						<td align="left" style="font-size:9px"><html:textarea  property="address3" styleId="address3" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,200)) { return ValidateTextField(this,path,'Address3')}};"/></td><tr >
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Address4</td>
						<td align="left" style="font-size:9px"><html:textarea  property="address4" styleId="address4" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,200)) { return ValidateTextField(this,path,'Address4')}};"/></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Country</td>
						<td align="left" style="font-size:9px">
							<html:select property="country" styleId="country" styleClass="inputBorder1" style="width:110px;" onchange="javascript:fetchState()">
								<html:option value="0">---Select---</html:option>
								<html:optionsCollection name="formBean" property="countries" value="countryCode" label="countryName"></html:optionsCollection>
					</html:select></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;State</td>
						<td align="left" style="font-size:9px">
							<html:select property="state" styleClass="inputBorder1" style="width:110px;" styleId="state" onchange="loadCities()">
								<html:option value="0">---Select---</html:option>
								<html:optionsCollection name="formBean" property="states" value="stateId" label="stateName"></html:optionsCollection>
							</html:select></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;City</td>
						<td align="left" style="font-size:9px">
							<html:select property="city" styleClass="inputBorder1" style="width:110px;" styleId="city">
								<html:option value="0">---Select---</html:option>
								<html:optionsCollection name="formBean" property="cities" value="cityId" label="cityName"></html:optionsCollection>
							</html:select></td>
						</tr>
						<tr >
						<td align="right" style="font-size:9px">&nbsp;Postal Code</td>
						<td align="left" style="font-size:9px"><html:text  property="postalCode" styleId="postalCode" styleClass="inputBorder1" style="width:100px;" maxlength="15" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"/></td>
						</tr>
	
					</table>
					
						<table align="center">
							<tr>
								<td nowrap="nowrap" ><input type = "button" value="Update" onclick="javascript:updateCustomerLocation();" /> </td>
								<td nowrap="nowrap" ><input type = "button" value="Cancel" onclick="javascript:displayBCPList();"/> </td>
							</tr>
						</table>
						</td>
						</tr>
					</table>
					
			</logic:equal>
			</logic:present>
			</html:form>
		
</body>

</html>
