<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.DispatchAddressBean"%>
<html>
<head>
<title>Dispatch Address</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/formValidations.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script language="javascript" type="text/javascript">

var counter = 1;
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}

function checkForm()
{

	var myForm=document.getElementById('searchForm');

	if(isBlank("accountNameStr1","A\\C No") == false) {  return false;}
	if(isBlank("dispatchAddressName","Dispatch Name") == false) { return false;}
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
	if(selectDropdown("ddCountry","Country")  == false) {  return false;}
	if(selectDropdown("ddState1","State")  == false) {  return false;}
	if(selectDropdown("ddCity","City")  == false) {  return false;}
	if(isBlank("postalCode","Postal Code")  == false) { return false;}
	
	return true;
}
function checkEditForm()
{
	var myForm=document.getElementById('searchForm');

	if(isBlank("edittitle","Title") == false) { return false;}
	if(isBlank("editfirstname","First Name")  == false) { return false;}
	if(isBlank("editlastName","Last Name")  == false) { return false;}
	if(isBlank("edittelephonePhno","Telephone Number")  == false) { return false;}
	if(isBlank("editemail_Id","E-Mail")  == false) { return false;}
	if(isBlank("editfax","Fax")  == false) { return false;}
	if(isBlank("editaddress1","Address1")  == false) { return false;}
	if(isBlank("editaddress2","Address2")  == false) { return false;}
	if(isBlank("editaddress3","Address3")  == false) { return false;}
	if(isBlank("editaddress4","Address4")  == false) { return false;}
	if(selectDropdown("editddCountry","Country")  == false) {  return false;}
	if(selectDropdown("editddState","State")  == false) {  return false;}
	if(selectDropdown("editddCity","City")  == false) {  return false;}
	if(isBlank("editpostalCode","Postal Code")  == false) { return false;}
	
	return true;
}


function addNewDipatchAddress()
{

	var myForm=document.getElementById('searchForm');	
	if(checkForm()==false)
	{
		return false;
	}	
	myForm.action="<%=request.getContextPath()%>/dispatchAddress.do?method=addNewDispatchAddress";
    myForm.submit(); 
}
function updateDipatchAddress()
{
	
	var myForm=document.getElementById('searchForm');
	myForm.hiddenDispatchAddressId.value=myForm.editDispatchAddressId.value;

	myForm.editaccountID.value=document.getElementById('editID').value;
	myForm.editaccountName.value=document.getElementById('editaccountName').value;
	myForm.editDispatchAddressName.value=document.getElementById('editName').value;
	if(checkEditForm()==false)
	{
		return false;
	}	
	myForm.action="<%=request.getContextPath()%>/dispatchAddress.do?method=updateDispatchAddress";
	
   	myForm.submit();    
}
function displayDispatchAddressList()
{
	var myForm = document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/dispatchAddress.do?method=viewDispatchAddressList";
	
   myForm.submit();	
}
function editDispatchAddress(locId)
{

	var myForm = document.getElementById('searchForm');
	myForm.actiontype.value="edit";
	
	myForm.hiddenDispatchAddressId.value=locId;
	myForm.action="<%=request.getContextPath()%>/dispatchAddress.do?method=editDispatchAddress";

    myForm.submit();
}
function fetchState(val)
{

	if(val==1)
	{
		try{
			var combo1 = document.getElementById("ddState");
			var frm=document.getElementById('searchForm');
			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			 var jsData=			
			    {
					countryCode:document.getElementById("ddCountry1").value
				};
				
			var lstService = jsonrpc.processes.fetchStates(jsData);
			
			for(i=combo1.options.length-1;i>=0 ;i--)
				{
				
				combo1.options[i] = null;
				
				}
			var combo2 = document.getElementById("ddCity");
			for(i=combo2.options.length-1;i>=0 ;i--)
			{
			
			combo2.options[i] = null;
			
			}
		
			if(lstService.list.length>0)
			{
			
				combo1.options[0] = new Option("---Select---", "0");
				combo2.options[0] = new Option("---Select---", "0");
			
			}
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
	}else if(val==2)
	{
	
		try{
			var combo2 = document.getElementById("editddState");
			var combo3 = document.getElementById("editddCity");
			var frm=document.getElementById('searchForm');
			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			 var jsData=			
			    {
					countryCode:document.getElementById("ddCountry2").value
				};
				
			var lstService = jsonrpc.processes.fetchStates(jsData);
			
			for(i=combo2.options.length-1;i>=0 ;i--)
				{
				combo2.options[i] = null;
				}
				for(i=combo3.options.length-1;i>=0 ;i--)
				{
				combo3.options[i] = null;
				}
				
		
				combo2.options[0] = new Option("---Select---", "0");
				combo3.options[0] = new Option("---Select---", "0");
				
		
			if(lstService!=null)
			{
								
				 for(j=0;j<lstService.list.length;j++)
		    	{
			    	var name = lstService.list[j].stateName;
					var id=lstService.list[j].stateId;
					combo2.options[j+1] = new Option(name,id);
		    	}	
			}
			}
			catch(e)
			{}
	}
}

function fetchCity(val)
{
	if(val==1)
	{
		try{
			var combo1 = document.getElementById("ddCity");
			var frm=document.getElementById('searchForm');
			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			 var jsData=			
			    {
					stateId:document.getElementById("ddState1").value
				};
				
			var lstService = jsonrpc.processes.fetchCities(jsData);
			
			for(i=combo1.options.length-1;i>=0 ;i--)
				{
				combo1.options[i] = null;
				}
			if(lstService.list.length>0)
			{
				combo1.options[0] = new Option("---Select---", "0");
			}
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
	}else if(val==2)
	{
			try{
			var combo2 = document.getElementById("editddCity");
			var frm=document.getElementById('searchForm');
			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			 var jsData=			
			    {
					stateId:document.getElementById("ddState2").value
				};
				
			var lstService = jsonrpc.processes.fetchCities(jsData);
			
			for(i=combo2.options.length-1;i>=0 ;i--)
				{
				combo2.options[i] = null;
				}
			if(lstService.list.length>0)
			{
				combo2.options[0] = new Option("---Select---", "0");
			}
			if(lstService!=null)
			{
				
				
				 for(j=0;j<lstService.list.length;j++)
		    	{
			    	var name = lstService.list[j].cityName;
					var id=lstService.list[j].cityId;
					combo2.options[j+1] = new Option(name,id);
		    	}	
			}
			}
			catch(e)
			{}
	
	}
}

function fnAddNewDispatchAddress()
{
	var myForm = document.getElementById('searchForm');

	myForm.actiontype.value="add";
	
	document.forms[0].accountNameStr1.value='' ;
	myForm.action="<%=request.getContextPath()%>/dispatchAddress.do?method=editDispatchAddress";
	myForm.submit();
}


function popitup(url) 
{
	if(url=="SelectAccount")
	{
		var path = "<%=request.getContextPath()%>/dispatchAddress.do?method=fetchAccount";		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
		document.forms[0].accountIdStr1.value='' ;
		
	}
	if(url=="SelectAccount1")
	{
		var path = "<%=request.getContextPath()%>/dispatchAddress.do?method=fetchAccountAdd";		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
		
		
	}
	if(url=="SelectDispatchAddress")
	{
		var path = "<%=request.getContextPath()%>/dispatchAddress.do?method=fetchDispatchAddress";		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
		
	}
	if (window.focus) {window.focus()}
	return false;
}


function dispatchSearch()
{
	var myForm=document.getElementById('searchForm');
	var accId=myForm.accountIdStr.value;
	var dipId=myForm.dispatchAddressIdStr.value;
	var dipName=myForm.dispatchAddressStr.value;
	if((accId=="" && dipId=="" && dipName=="") || (accId==null && dipId==null && dipName==null) ){
	
	alert("Please enter atleast one search criteria!");
	return;
	}else{
	searchSubmit();
	myForm.action="<%=request.getContextPath()%>/dispatchAddress.do?method=viewDispatchAddressList";
	myForm.submit();
	}
}

function searchSubmit()
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	

	myform.method.value='viewDispatchAddressList';
		//showLayer();
	myform.submit();
}

function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	//setFormBean(myform);
	//myform.reset();
	myform.elements["pagingSorting.pageNumber"].value=val;
	
	myform.method.value='viewDispatchAddressList';	
	
	//showLayer();
	myform.submit();
}
function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	//setFormBean(myform);
	//myform.reset();
	myform.elements["pagingSorting.pageNumber"].value=1;
	if(myform.elements["pagingSorting.sortByColumn"].value==sortBy)
	{
		if(myform.elements["pagingSorting.sortByOrder"].value=="decrement")
		{
			myform.elements["pagingSorting.sortByOrder"].value="increment";
		}
		else
		{
			myform.elements["pagingSorting.sortByOrder"].value="decrement";
		}
	}
	else
	{
		myform.elements["pagingSorting.sortByColumn"].value=sortBy;
		myform.elements["pagingSorting.sortByOrder"].value="decrement";
	}
	

	myform.method.value='viewDispatchAddressList';	
		//showLayer();
	myform.submit();
}

function displaySection(flag)
{

	if(flag=='1') // add new customer dispatch Address details
	{
		
		tblLocationList.style.display='none';
		tblEditLocation.style.display='none';
		tblAddLocation.style.display='block';
		
	}
	if(flag=='2')   // dispatch Address
	{
		
		tblLocationList.style.display='block';
		tblEditLocation.style.display='none';
		tblAddLocation.style.display='none';
		
	}
	if(flag=='3')  // Update existing dispatch Address details
	{
		tblEditLocation.style.display='block';
		tblLocationList.style.display='none';
		tblAddLocation.style.display='none';
	
	}
}
function validate(val)
{
		var myForm=document.getElementById('searchForm');
		if( val==1)
		{
			if(myForm.accountID.value == 0)
			{
				alert("Please Select Account") ;
				myForm.accountID.focus();
				return false;
			}
			if(document.getElementById('dispatchAddressName').value == '')
			{
				alert("Please Enter Dispatch Address Name") ;
				document.getElementById('dispatchAddressName').focus();
				return false;
			}
			if(document.getElementById('title').value == '')
			{
				alert("Please Enter Title ") ;
				document.getElementById('title').focus();
				return false;
			}
			if(document.getElementById('firstname').value == '')
			{
				alert("Please Enter First Name ") ;
				document.getElementById('firstname').focus();
				return false;
			}
			if(document.getElementById('lastName').value == '')
			{
				alert("Please Enter Last Name") ;
				document.getElementById('lastName').focus();
				return false;
			}
			if(document.getElementById('telephonePhno').value == '')
			{
				alert("Please Enter Telephone No") ;
				document.getElementById('telephonePhno').focus();
				return false;
			}
			if(document.getElementById('email_Id').value == '')
			{
				alert("Please Enter Email") ;
				document.getElementById('email_Id').focus();
				return false;
			}
		
			if(document.getElementById('fax').value == '')
			{
				alert("Please Enter Fax no ") ;
				document.getElementById('fax').focus();
				return false;
			}
		 	if(document.getElementById('pin').value == '')
			{
				alert("Please Enter Pin No ") ;
				document.getElementById('pin').focus();
				return false;
			}
			if(document.getElementById('address1').value == '')
			{
				alert("Please Enter Address1 ") ;
				document.getElementById('address1').focus();
				return false;
			}
			if(document.getElementById('address2').value == '')
			{
				alert("Please Enter Address2") ;
				document.getElementById('address2').focus();
				return false;
			}
			if(document.getElementById('address3').value == '')
			{
				alert("Please Enter Address3 ") ;
				document.getElementById('address3').focus();
				return false;
			}
			if(document.getElementById('address4').value == '')
			{
				alert("Please Enter Address4 ") ;
				document.getElementById('address4').focus();
				return false;
			}
			if(document.getElementById('postalCode').value == '')
			{
				alert("Please Enter Postal Address ") ;
				document.getElementById('postalCode').focus();
				return false;
			}
			if(document.getElementById('ddCountry').value == '')
			{
				alert("Please Enter County  ") ;
				document.getElementById('ddCountry').focus();
				return false;
			}
			if(document.getElementById('ddState').value == '')
			{
				alert("Please Enter State  ") ;
				document.getElementById('ddState').focus();
				return false;
			}
		  	if(document.getElementById('ddCity').value == '')
			{
				alert("Please Enter City  ") ;
				document.getElementById('ddCity').focus();
				return false;
			}
		return true;
		}else if(val==2)
		{
		
			if(document.getElementById('edittitle').value == '')
			{
				alert("Please Enter Title ") ;
				document.getElementById('edittitle').focus();
				return false;
			}
			if(document.getElementById('editfirstname').value == '')
			{
				alert("Please Enter First Name ") ;
				document.getElementById('editfirstname').focus();
				return false;
			}
			if(document.getElementById('editlastName').value == '')
			{
				alert("Please Enter Last Name") ;
				document.getElementById('editlastName').focus();
				return false;
			}
			if(document.getElementById('edittelephonePhno').value == '')
			{
				alert("Please Enter Telephone No") ;
				document.getElementById('edittelephonePhno').focus();
				return false;
			}
			if(document.getElementById('editemail_Id').value == '')
			{
				alert("Please Enter Email") ;
				document.getElementById('editemail_Id').focus();
				return false;
			}
		
			if(document.getElementById('editfax').value == '')
			{
				alert("Please Enter Fax no ") ;
				document.getElementById('editfax').focus();
				return false;
			}
		 	if(document.getElementById('editpin').value == '')
			{
				alert("Please Enter Pin No ") ;
				document.getElementById('editpin').focus();
				return false;
			}
			if(document.getElementById('editaddress1').value == '')
			{
				alert("Please Enter Address1 ") ;
				document.getElementById('editaddress1').focus();
				return false;
			}
			if(document.getElementById('editaddress2').value == '')
			{
				alert("Please Enter Address2") ;
				document.getElementById('editaddress2').focus();
				return false;
			}
			if(document.getElementById('editaddress3').value == '')
			{
				alert("Please Enter Address3 ") ;
				document.getElementById('editaddress3').focus();
				return false;
			}
			if(document.getElementById('editaddress4').value == '')
			{
				alert("Please Enter Address4 ") ;
				document.getElementById('editaddress4').focus();
				return false;
			}
			if(document.getElementById('editpostalCode').value == '')
			{
				alert("Please Enter Postal Address ") ;
				document.getElementById('editpostalCode').focus();
				return false;
			}
			if(document.getElementById('editddCountry').value == '')
			{
				alert("Please Enter County  ") ;
				document.getElementById('editddCountry').focus();
				return false;
			}
			if(document.getElementById('editddState').value == '')
			{
				alert("Please Enter State  ") ;
				document.getElementById('editddState').focus();
				return false;
			}
		  	if(document.getElementById('editddCity').value == '')
			{
				alert("Please Enter City  ") ;
				document.getElementById('editddCity').focus();
				return false;
			}
		return true;
		
		}


}

function bodyLoad()
{
	//check flag here
	var myForm=document.getElementById('searchForm');
	var status=myForm.hiddenFlag.value;
	if(status=='1'){
	
		displaySection(1);
		return;
	
	}else if(status=='2'){
	
		displaySection(2);
		return;
	
	}else if(status=='3'){
	
		displaySection(3);
		
		return;
	
	}

	<logic:present name="message">
		alert('<bean:write name="message"/>');
	</logic:present>
	<logic:present name="displayEditTab">
		displaySection(3);
	</logic:present>
	<logic:present name="displayListTab">
		displaySection(2);
	</logic:present>
	<logic:present name="displayAddTab">
		displaySection(1);
	</logic:present>
}
path='<%=request.getContextPath()%>';
</script>
<body onload="javascript:bodyLoad();">
	<html:form action="/dispatchAddress" styleId="searchForm" method="post">
	<bean:define id="formBean" name="dispatchAddressBean"></bean:define>
	<html:hidden property="hiddenDispatchAddressId"/>
	<html:hidden property="pagingSorting.sortByColumn"/>
	<html:hidden property="pagingSorting.sortByOrder"/>
	<html:hidden property="pagingSorting.pageNumber"/>

<html:hidden property="hiddenFlag" name="dispatchAddressBean"/>
<html:hidden property="actiontype" name="dispatchAddressBean"/>
<input type="hidden" name="method" />
<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
<tr align="right">
	<td><img src="./gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
</tr>
</table>		
		
		<div class="head"> <span>DISPATCH ADDRESS</span> </div>
		<div class="error" align="center">
			<logic:present name="validation_errors">
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>
			</logic:present>
			<html:messages id="message" message="true">
								<li><font color="red"><bean:write name="message"/></font></li>
			</html:messages>
		</div>
		<table width="100%"   border="0" cellspacing="0" cellpadding="0" align="center" id='tblLocationList'>
		<tr>
		<td>
			<fieldset class="border2" style="width:97%;"><legend> <b>Search</b>
			</legend>
				<table width="55%"   cellspacing="0" cellpadding="0" align="center" id='tableContact' >
				<tr>
				<td align="center"  width="50%" valign="top">
						<logic:notPresent name="accountDetailsBean" scope="request">
							<table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin-top:7px ">
								<tr width="100%">
									<td valign="top" align="right" style="font-size:9px;width:28%;"> A\C No </td>
									<td valign="top" align="left" style="font-size:9px;width:22%;">
										<html:text  property="accountIdStr" styleClass="inputBorder1" style="width:50px;float:left;" readonly="true" ></html:text>
										<div class="searchBg1"><a href="#" onClick="return popitup('SelectAccount')">...</a></div> 
									</td>
									<td valign="top" align="right"  style="font-size:9px;" width="20%" nowrap>Party Name</td>
									<td  valign="top" align="left" style="font-size:9px;width:30%;">
									<html:text  property="accountNameStr" styleClass="inputBorder1" style="width:100px;" readonly="true" ></html:text>
									<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Field" width="24" height="18" onclick="javascript:{ document.forms[0].accountIdStr.value='' ; document.forms[0].accountNameStr.value='' ;  document.forms[0].accountIdStr1.value='' ;}">
									</td>
								</tr>
								<tr width="100%">
									<td  valign="top" align="right" width="28%" style="font-size:9px;" nowrap> Ddispatch Address Code </td>
									<td  valign="top" align="left" width="22%">
										<html:text  property="dispatchAddressIdStr" styleClass="inputBorder1" style="width:50px;float:left;" onblur="if( trim(this.value).length>0){ this.form.dispatchAddressStr.value='';return checkdigits(this)};"></html:text>
									</td>
									<td  valign="top" width="20%" align="right" style="font-size:9px;" nowrap>Dispatch Address</td>
									<td  valign="top" align="left" width="30%">
									<html:text  property="dispatchAddressStr" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ this.form.dispatchAddressIdStr.value=''; return ValidateTextField(this,path,'Title')};"></html:text>
									</td>
								</tr>
							</table>
						</logic:notPresent>
				</td>
				</tr>
				
				</table>	
				</fieldset>
			</td>
			</tr>
			<tr>
					<td align="center" width="50%" valign="top">
					<table border="0" align="center" >
					<tr align="center">
						<td align="center" width="50%" valign="top">
						<div class="searchBg" style="margin-right:10px;"><a href="#" onClick="javascript:dispatchSearch();">Search</a></div>
						</td>
					</tr>
					</table>
				</td>
				</tr>
			<tr>
			<td>
			<fieldset class="border2"><legend> <b>Dispatch Address Details</b>
			</legend>
			
			
			<!-- View User List -->
			<div style="height:500px; width:945px; " >
			<table id="" align="center" width="100%" style="display:block"   cellspacing="0" cellpadding="0" align="center">
				
				<tr>
					<td  align="left" style="font-size:10px" nowrap="nowrap" width="82%"></td>
					
					<td nowrap="nowrap">
					<div class="searchBg" style="margin-right:10px;"><a onclick="javascript:fnAddNewDispatchAddress();" ><span>Add Address</span></a></div>
					</td>
				</tr>
				</table>
			
				<table id="" align="center" width="100%" style="display:block"   cellspacing="0" cellpadding="0" align="center">
				<tr>
					<td>
						<%	String currPageNumber =String.valueOf(((DispatchAddressBean )formBean).getPagingSorting().getPageNumber());
							String 	maxPageNumber =String.valueOf(((DispatchAddressBean)formBean).getPagingSorting().getMaxPageNumber());
						%>
						<jsp:include flush="true" page="../paging.jsp">
						<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
						<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
					</jsp:include>
					</td>
				</tr>
			</table>
	
			
					
					<div class="scrollWithAutoScroll" class="head" style="height:500px; width:945px; " >
						<table cellpadding="0" align="center" cellspacing="0" class="tab2" width="100%" border="1" >
						<tr class="normal">
							<td>&nbsp;</td>
							<logic:present name="customerList" scope="request">
								<logic:notEmpty   name="customerList" scope="request"> 
							<td align="center">&nbsp;<b><a href="#" onclick="sortSubmit('accountId')">Account Id</a></b></td>
							<td align="center">&nbsp;<b><a href="#" onclick="sortSubmit('dispatchAddCode')">Dispatch Address Code</a></b></td>
							<td align="center">&nbsp;<b>Dispatch Address</b></td>
							<td align="center">&nbsp;<b>Name</b></td>
							<td align="center">&nbsp;<b>Telephone Number</b></td>
							<td align="center">&nbsp;<b>E-Mail</b></td>
							<td align="center">&nbsp;<b>Fax</b></td>
							<td align="center">&nbsp;<b>Address1</b></td>
							<td align="center">&nbsp;<b>Address2</b></td>
							<td align="center">&nbsp;<b>Address3</b></td>
							<td align="center">&nbsp;<b>Address4</b></td>
							<td align="center">&nbsp;<b>City</b></td>
							<td align="center">&nbsp;<b>State</b></td>
							<td align="center">&nbsp;<b>Postal Code</b></td>
							<td align="center">&nbsp;<b>Country</b></td>
							</logic:notEmpty>
							</logic:present>	
							
						</tr>
						
						<logic:present name="customerList" scope="request">
								
						<logic:notEmpty   name="customerList" scope="request"> 					
						<logic:iterate id="row" name="customerList" indexId="recordId">
						<%
							String classType=null;
							if((recordId.intValue())%2==0) {
						 classType="lightBg" ;
						 }else{ 
						 classType="normal";
						 } %>
						 <tr class="<%=classType %>">
							<td  align="left" width="30px">
								<a href="#" 
								onClick="javascript:editDispatchAddress('<bean:write name="row" property="dispatchAddressId"/>');">Edit</a>
							</td>
						
							<td style="font-size:9px"> <bean:write  name="row" property="accountID"/></td>
							<td style="font-size:9px"><bean:write  name="row" property="dispatchAddressId"/></td>
							<td style="font-size:9px"><bean:write  name="row" property="dispatchAddressName"/></td>
							<td style="font-size:9px"><bean:write  name="row" property="customerName"/></td>
							<td style="font-size:9px"><bean:write  name="row" property="telephonePhno"/></td>
							<td style="font-size:9px"><bean:write  name="row" property="email_Id"/></td>
							<td style="font-size:9px"><bean:write  name="row" property="fax"/></td>
							<td style="font-size:9px"><bean:write  name="row" property="address1"/></td>
							<td style="font-size:9px"><bean:write  name="row" property="address2"/></td>
							<td style="font-size:9px"><bean:write  name="row" property="address3"/></td>
							<td style="font-size:9px"><bean:write  name="row" property="address4"/></td>
							<td style="font-size:9px"><bean:write  name="row" property="cityName"/></td>
							<td style="font-size:9px"><bean:write  name="row" property="stateName"/></td>
							<td style="font-size:9px"><bean:write  name="row" property="postalCode"/></td>
							<td style="font-size:9px"><bean:write  name="row" property="countryName"/></td>
						</tr>
						</logic:iterate>		
						</logic:notEmpty>
						<logic:empty  name="customerList">
						   <tr align="center" class="tableTd">
							 <td colspan="15" align="center"><B><font color="red">No Record Found</font></td>
						   </tr>
						</logic:empty>	
						</logic:present>				
						</table>
					</td>
				</tr>
			
			</table>
			
				
			<br>
			<br>	
			
			<!-- Add New User -->
		
			<table id="tblAddLocation" align="center" width="50%" style="display:none"  >
			<tr>
				<td  align="left" style="font-size:17px" nowrap="nowrap">Add New Dispatch Address</td>
			</tr>
			<tr>
				<td>
					<table cellpadding="0" align="center" cellspacing="1" width="100%" border="1">
				
						
						<tr height="20px">
						<td width="50%" align="right" style="font-size:9px"> A\C  </td>
						<td>
						<html:hidden property="accountIdStr1" />
						<html:text  property="accountNameStr1" styleClass="inputBorder1" style="font-size:9px" style="width:150px;float:left;" readonly="true" ></html:text>
						<div class="searchBg1"><a href="#" onClick="return popitup('SelectAccount1')">...</a></div>	
						<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Field" width="24" height="18" onclick="javascript:{ document.forms[0].accountNameStr1.value='' ;}">			
						</td>
						
						</tr>
					
						
						<tr height="20px">
						<td width="50%" align="right" style="font-size:9px">&nbsp;Dispatch Address</td>
						<td align="left" style="font-size:9px"><html:text  property="dispatchAddressName" styleClass="inputBorder1" style="width:100px;" maxlength="200"/></td>
						</tr>
						<tr height="20px">
						<td width="50%" align="right" style="font-size:9px">&nbsp;Title</td>
						<td align="left" style="font-size:9px"><html:text property="title" styleClass="inputBorder1" style="width:100px;" maxlength="15" onblur="if( trim(this.value).length>0){  return ValidateTextField(this,path,'Title')};" /></td>
						</tr>
						<tr height="20px">
						<td width="50%" align="right" style="font-size:9px">&nbsp;First Name</td>
						<td align="left" style="font-size:9px"><html:text  property="firstname" styleClass="inputBorder1" style="width:100px;" maxlength="200" onblur="if( trim(this.value).length>0){  return ValidateTextField(this,path,'First Name')};"/></td>
						</tr>
						<tr height="20px">
						<td width="50%" align="right" style="font-size:9px">&nbsp;Last Name</td>
						<td align="left" style="font-size:9px"><html:text  property="lastName" styleClass="inputBorder1" style="width:100px;" maxlength="200" onblur="if( trim(this.value).length>0){  return ValidateTextField(this,path,'Last Name')};"/></td>
						</tr>
						<tr height="20px">
						<td width="50%" align="right" style="font-size:9px">&nbsp;Telephone Number</td>
						<td align="left" style="font-size:9px"><html:text  property="telephonePhno" styleClass="inputBorder1" style="width:100px;" maxlength="15" onblur="if(this.value.length > 0){return checkdigits(this)}"/></td>
						</tr>
						<tr height="20px">
						<td width="50%" align="right" style="font-size:9px">&nbsp;E-Mail</td>
						<td align="left" style="font-size:9px"><html:text  property="email_Id" styleClass="inputBorder1" style="width:100px;" maxlength="200" onblur="if(this.value.length > 0){return emailValidate(this)}" /></td>
						</tr>
						<tr height="20px">
						<td width="50%" align="right" style="font-size:9px">&nbsp;Fax</td>
						<td align="left" style="font-size:9px"><html:text  property="fax" styleClass="inputBorder1" style="width:100px;" maxlength="15" onblur="if(this.value.length > 0){return checkdigits(this)}"/></td>
						</tr>
						<html:hidden  property="pin"  value= "1" />
						
						<tr height="20px">
						<td width="50%" align="right" style="font-size:9px">&nbsp;Address1</td>
						<td align="left" style="font-size:9px"><html:textarea  property="address1" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,200)) { return ValidateTextField(this,path,'Address1')}};"/></td>
						</tr>
						<tr height="20px">
						<td width="50%" align="right" style="font-size:9px">&nbsp;Address2</td>
						<td align="left" style="font-size:9px"><html:textarea  property="address2" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,200)) { return ValidateTextField(this,path,'Address2')}};"/></td>
						</tr>
						<tr height="20px">
						<td width="50%" align="right" style="font-size:9px">&nbsp;Address3</td>
						<td align="left" style="font-size:9px"><html:textarea  property="address3" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,200)) { return ValidateTextField(this,path,'Address3')}};"/></td><tr >
						</tr>
						<tr height="20px">
						<td width="50%" align="right" style="font-size:9px">&nbsp;Address4</td>
						<td align="left" style="font-size:9px"><html:textarea  property="address4" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,200)) { return ValidateTextField(this,path,'Address4')}};"/></td>
						</tr>
						
						<tr >
						<td width="50%" align="right" style="font-size:9px">&nbsp;Country</td>
						<td align="left" style="font-size:9px">
							<html:select property="ddCountry" styleId="ddCountry1" styleClass="inputBorder1" style="width:110px;" onchange="javascript:fetchState(1)">
								<html:option value="0">---Select---</html:option>
								<html:optionsCollection name="dispatchAddressBean" property="countries" value="countryCode" label="countryName"></html:optionsCollection>
							</html:select></td>
						</tr>
						<tr height="20px">
						<td width="50%" align="right" style="font-size:9px">&nbsp;State</td>
						<td>
						<html:select property="ddState" styleId="ddState1" styleClass="inputBorder1" style="width:110px;" onchange="fetchCity(1);">
						<html:option value="0">---Select---</html:option>
						
						</html:select>
						</td>
						</tr>
						<tr height="20px">
						<td width="50%" align="right" style="font-size:9px">&nbsp;City</td>
						<td>
						<html:select property="ddCity" styleClass="inputBorder1" style="width:110px;">
						<html:option value="0">---Select---</html:option>
						
						</html:select>
						</td>
						</tr>
						<tr height="20px" >
							<td width="50%" style="font-size:9px" align="right">&nbsp;Postal Code</td>
							<td align="left" style="font-size:9px"><html:text  property="postalCode" styleClass="inputBorder1" style="width:100px;" maxlength="15" onblur="if(this.value.length > 0){return checkdigits(this)}"/></td>
						</tr>
					</table>
					<table align="center">
							<tr>
								<td nowrap="nowrap" ><input type = "button" value="Save" onclick="javascript:addNewDipatchAddress();" /> </td>
								<td nowrap="nowrap" ><input type = "button" value="Cancel" onclick="javascript:displayDispatchAddressList();"/> 
							</tr>
					</table>

				</td>
			</tr>
			</table>

			<br>
			<br>
			<!-- Edit User -->
			<table id="tblEditLocation" align="center" width="50%" style="display:none">
			<tr>
				<td align="left" style="font-size:17px"  nowrap="nowrap">Edit Dispatch Address</td>
			</tr>
			<tr>
				<td>
					<table cellpadding="0" align="center" cellspacing="1" border="1" width="100%">
						<tr >
							<td width="50%" align="right" style="font-size:9px"><b>Account Name</b></td>
							
							<td>
							<html:hidden property="editaccountID"  styleId="editID" />
								<html:text property="editaccountName"  styleId="editaccountName" style="font-size:9px" styleClass="inputBorder1" style="width:200px;" readonly="true"  /></td>			
						</tr>
						<tr >
							<td width="50%" align="right" style="font-size:9px"><b>Dispatch Address Code</b></td>
							<td><html:text property="editDispatchAddressId"  styleClass="inputBorder1" style="width:100px;" style="font-size:9px" readonly="true"/></td>			
						</tr>
						<tr >
							<td width="50%" align="right" style="font-size:9px"><b>Dispatch Address</b></td>
							<td><html:text property="editDispatchAddressName"  styleId="editName" readonly="true" style="font-size:9px" styleClass="inputBorder1" style="width:100px;"/></td>			
						</tr>
						<tr >
						<td width="50%" align="right" style="font-size:9px">&nbsp;Title</td>
						<td style="font-size:9px"><html:text property="edittitle" styleId="title" styleClass="inputBorder1" style="font-size:9px" style="width:100px;" maxlength="15" onblur="if( trim(this.value).length>0){  return ValidateTextField(this,path,'Title')};"/></td>
						</tr>
						<tr >
						<td width="50%" align="right" style="font-size:9px">&nbsp;First Name</td>
						<td  style="font-size:9px"><html:text  property="editfirstname"  styleId="firstname123" styleClass="inputBorder1"  style="font-size:9px" style="width:100px;" maxlength="200" onblur="if( trim(this.value).length>0){  return ValidateTextField(this,path,'First Name')};"/></td>
						</tr>
						<tr >
						<td width="50%" align="right" style="font-size:9px">&nbsp;Last Name</td>
						<td style="font-size:9px"><html:text  property="editlastName" styleId="lastName" style="font-size:9px" styleClass="inputBorder1" style="width:100px;" maxlength="200" onblur="if( trim(this.value).length>0){  return ValidateTextField(this,path,'Last Name')};" /></td>
						</tr>
						<tr >
						<td width="50%" align="right" style="font-size:9px">&nbsp;Telephone Number</td>
						<td  style="font-size:9px"><html:text  property="edittelephonePhno" style="font-size:9px" styleId="telephonePhno" styleClass="inputBorder1" style="width:100px;" maxlength="15" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"/></td>
						</tr>
						<tr >
						<td width="50%" align="right"style="font-size:9px">&nbsp;E-Mail</td>
						<td  style="font-size:9px"><html:text  property="editemail_Id" styleId="email_Id" styleClass="inputBorder1" style="width:100px;" maxlength="200" onblur="if(this.value.length > 0){return emailValidate(this)}" /></td>
						</tr>
						<tr >
						<td width="50%" align="right" style="font-size:9px">&nbsp;Fax</td>
						<td  style="font-size:9px"><html:text  property="editfax" styleId="fax" styleClass="inputBorder1" style="width:100px;" maxlength="15" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"/></td>
						</tr>
						<html:hidden  property="editpin"  value= "1" />
						<tr >
						<td width="50%" align="right" style="font-size:9px">&nbsp;Address1</td>
						<td  style="font-size:9px"><html:textarea  property="editaddress1" styleId="address1" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,200)) { return ValidateTextField(this,path,'Address1')}};" /></td>
						</tr>
						<tr >
						<td width="50%" align="right" style="font-size:9px">&nbsp;Address2</td>
						<td  style="font-size:9px"><html:textarea  property="editaddress2" styleId="address2" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,200)) { return ValidateTextField(this,path,'Address2')}};"/></td>
						</tr>
						<tr >
						<td width="50%" align="right" style="font-size:9px">&nbsp;Address3</td>
						<td  style="font-size:9px"><html:textarea  property="editaddress3" styleId="address3" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,200)) { return ValidateTextField(this,path,'Address3')}};"/></td><tr >
						</tr>
						<tr >
						<td width="50%" align="right" style="font-size:9px">&nbsp;Address4</td>
						<td  style="font-size:9px"><html:textarea  property="editaddress4" styleId="address4" styleClass="inputBorder1" style="width:100px;" onblur="if( trim(this.value).length>0){ if(checkTextAreaLength(this,200)) { return ValidateTextField(this,path,'Address4')}};" /></td>
						</tr>
						
							<tr >
						<td width="50%" align="right" style="font-size:9px">&nbsp;Country</td>
						<td align="left" style="font-size:9px">
							<html:select property="editddCountry" styleId="ddCountry2"  styleClass="inputBorder1" style="width:110px;" onchange="javascript:fetchState(2)">
								<html:option value="0">---Select---</html:option>
								<html:optionsCollection name="dispatchAddressBean" property="countries" value="countryCode" label="countryName"></html:optionsCollection>
							</html:select></td>
						</tr>
						<tr height="20px">
						<td width="50%" align="right" style="font-size:9px">&nbsp;State</td>
						<td>
						<html:select property="editddState" styleId="ddState2" styleClass="inputBorder1" style="width:110px;" onchange="fetchCity(2);">
						<html:option value="0">---Select---</html:option>
						<logic:present name="dispatchAddressBean" property="states" >
							<html:optionsCollection name="dispatchAddressBean" property="states" value="stateId" label="stateName"></html:optionsCollection>
						</logic:present>
						</html:select>
						</td>
						</tr>
						<tr height="20px">
						<td width="50%" align="right" style="font-size:9px">&nbsp;City</td>
						<td>
						<html:select property="editddCity" styleClass="inputBorder1" style="width:110px;">
						<html:option value="0">---Select---</html:option>
						<logic:present name="dispatchAddressBean" property="cities">
							<html:optionsCollection name="dispatchAddressBean" property="cities" value="cityId" label="cityName"></html:optionsCollection>
						</logic:present>
						</html:select>
						</td>
						</tr>
						<tr >
						<td width="50%" align="right" style="font-size:9px">&nbsp;Postal Code</td>
						<td  style="font-size:9px"><html:text  property="editpostalCode" styleId="postalCode" styleClass="inputBorder1" style="width:100px;" maxlength="15" onblur="if(this.value.length > 0){return checkdigits(this)}" /></td>
						</tr>
						</table>
						<table align="center">
							<tr>
								<td nowrap="nowrap" ><input type = "button" value="Update" onclick="javascript:updateDipatchAddress();" /> </td>
								<td nowrap="nowrap" ><input type = "button" value="Cancel" onclick="javascript:displayDispatchAddressList();"/> 
							</tr>
						</table>
						</td>
						</tr>
					</table>

		</html:form>
		
</body>

</html>
