<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 SAURABH SINGH	4-Feb-11	00-05422		Sending ShortCode on New Order -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Select Contact</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
// getting global JSON object
jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
var accountNumber="<%= request.getParameter("accountNumber") %>";
var poNumber="<%= request.getParameter("poNumber") %>";
var callerWindowObj = dialogArguments;
var sortByColumn='FIRSTNAME';
//function getInfoAndUpdate(contactType,contactTypeId,salutationName,firstName,lastName,cntEmail,contactCell,contactFax,address1,address2,address3,cityName,stateName,countyName,pinNo,cityCode,stateCode,countyCode) 
function getInfoAndUpdate(control)
{
	var myForm=callerWindowObj.document.getElementById('searchForm');
	var oldContactCounter;
	var oldAddressCounter;
	var oldLength
	var contactCounter;
	var addressCounter;
	var length;
	
	/*alert('in method ')
	alert('contactType '+control.contactType)
	alert('contactTypeId '+control.contactTypeId)
	alert('salutationName '+control.salutationName)
	alert('firstName '+control.firstName)
	alert('lastName '+control.lastName)
	alert('cntEmail '+control.cntEmail)
	alert('contactCell '+control.contactCell)
	alert('contactFax '+control.contactFax)
	alert('address1 '+control.address1)
	alert('address2 '+control.address2)
	alert('address3 '+control.address3)
	alert('cityName '+control.cityName)
	alert('stateName '+control.stateName)
	alert('countyName '+control.countyName)
	alert('pinNo '+control.pinNo)
	alert('cityCode '+control.cityCode)
	alert('stateCode '+control.stateCode)
	alert('countyCode '+control.countyCode)*/

	oldContactCounter= parseInt(callerWindowObj.document.getElementById('hdnContactCount').value);
	oldAddressCounter= parseInt(callerWindowObj.document.getElementById('hdnAddressCount').value);
	oldLength = parseInt(callerWindowObj.document.getElementById('tableContact').rows.length);
	
	contactCounter = oldContactCounter-1;
	addressCounter = oldAddressCounter-1;
	length = oldLength-2;
	
	if(oldContactCounter==1 && oldAddressCounter==1
	&& trim(callerWindowObj.document.getElementById('contactType'+contactCounter).value)==''
		&& trim(callerWindowObj.document.getElementById('salutationName'+contactCounter).value)==''
		&& trim(callerWindowObj.document.getElementsByName('firstName')[length].value)==''
		&& trim(callerWindowObj.document.getElementsByName('lastName')[length].value)==''
		&& trim(callerWindowObj.document.getElementsByName('cntEmail')[length].value)==''
		&& trim(callerWindowObj.document.getElementsByName('contactCell')[length].value)==''
		&& trim(callerWindowObj.document.getElementsByName('contactFax')[length].value)==''
		&& trim(callerWindowObj.document.getElementsByName('address1')[length].value)==''
		&& trim(callerWindowObj.document.getElementsByName('address2')[length].value)==''
		&& trim(callerWindowObj.document.getElementsByName('address3')[length].value)==''
		&& trim(callerWindowObj.document.getElementById('cityName'+contactCounter).value)==''
		&& trim(callerWindowObj.document.getElementById('stateName'+contactCounter).value)==''
		&& trim(callerWindowObj.document.getElementById('countyName'+contactCounter).value)==''
		&& trim(callerWindowObj.document.getElementById('pinCode'+contactCounter).value)=='')
	{
	 	//alert('pincode '+callerWindowObj.document.getElementById('pinCode'+contactCounter).value)
		//alert('pinNo '+callerWindowObj.document.getElementById('pinNo'+contactCounter).value)
		//alert('data not present');
		//return false;
		//dont add any row
	}
	else
	{
		//alert('data present')
		//return false;
		callerWindowObj.AddContact();
		oldContactCounter= parseInt(callerWindowObj.document.getElementById('hdnContactCount').value);
		oldAddressCounter= parseInt(callerWindowObj.document.getElementById('hdnAddressCount').value);
		oldLength = parseInt(callerWindowObj.document.getElementById('tableContact').rows.length);
	
		contactCounter = oldContactCounter-1;
		addressCounter = oldAddressCounter-1;
		length = oldLength-2;
	}
		
	callerWindowObj.document.getElementById('contactType'+contactCounter).value=control.contactType;
	callerWindowObj.document.getElementById('contactTypeId'+contactCounter).value=control.contactTypeId;
	callerWindowObj.document.getElementById('salutationName'+contactCounter).value=control.salutationName;
	callerWindowObj.document.getElementById('salutationId'+contactCounter).value=control.salutationName;
	callerWindowObj.document.getElementsByName('firstName')[length].value=control.firstName;
	callerWindowObj.document.getElementsByName('lastName')[length].value=control.lastName;
	callerWindowObj.document.getElementsByName('cntEmail')[length].value=control.cntEmail;
	callerWindowObj.document.getElementsByName('contactCell')[length].value=control.contactCell;
	callerWindowObj.document.getElementsByName('contactFax')[length].value=control.contactFax;
	
	callerWindowObj.document.getElementsByName('address1')[length].value=control.address1;
	callerWindowObj.document.getElementsByName('address2')[length].value=control.address2;
	callerWindowObj.document.getElementsByName('address3')[length].value=control.address3;
	callerWindowObj.document.getElementById('cityName'+addressCounter).value=control.cityName;
	callerWindowObj.document.getElementById('cityCode'+addressCounter).value=control.cityCode;
	callerWindowObj.document.getElementById('stateName'+addressCounter).value=control.stateName;
	callerWindowObj.document.getElementById('stateCode'+addressCounter).value=control.stateCode;
	callerWindowObj.document.getElementById('pinNo'+addressCounter).value=control.pinNo;
	callerWindowObj.document.getElementById('pinCode'+addressCounter).value=control.pinNo;
	callerWindowObj.document.getElementById('countyName'+addressCounter).value=control.countyName;
	callerWindowObj.document.getElementById('countyCode'+addressCounter).value=control.countyCode;
}

function DrawContactListTable()
{
	var str;
	var frm=document.getElementById('contactFormId');
	var mytable = document.getElementById('tabContact');	
	var iCountRows = mytable.rows.length;
	
	document.getElementById("checks").checked=false;	
	for (i = 1; i <  iCountRows; i++){
		mytable.deleteRow(1);
	}  	
	    
	document.getElementById('txtPageNumber').value= pageNumber;
	sync();	   	   	   	   
	   
	var jsData ={
		startIndex:startRecordId,
		endIndex:endRecordId,
		sortBycolumn:sortByColumn,
		sortByOrder:sortByOrder,
		firstName:document.getElementById('txtFirstName').value,
		lastName:document.getElementById('txtLastName').value,
		orderNumber:Number(document.getElementById('txtOrderNumber').value),
		address1:document.getElementById('txtAddress1').value,
		cityName:document.getElementById('txtCityName').value,
		stateName:document.getElementById('txtStateName').value,
		accountno:Number(accountNumber)
	};
			
	var lstContact = jsonrpc.processes.getContactDetails(jsData);				
	iTotalLength=lstContact.list.length;	
	if(iTotalLength !=0){
		iNoOfPages = lstContact.list[0].maxPageNo;
	}else{     
	    iNoOfPages=1;
	}
	document.getElementById('txtMaxPageNo').value=iNoOfPages;

	if(iTotalLength==0)
	{
		var tblRow=document.getElementById('tabContact').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 7;
		str='NO RECORD FOUND'
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		document.getElementById('first').disabled=true;
	    document.getElementById('prev').disabled=true;
	    document.getElementById('last').disabled=true;
	    document.getElementById('next').disabled=true;
	}
	else
	{
		var colors=new Array("normal","lightBg");
		for (i = 0 ; i <iTotalLength; i++)
		{
			
		 	var colorValue=parseInt(i)%2;	 		  
			var tblRow=document.getElementById('tabContact').insertRow();
			tblRow.className=colors[colorValue];
			
			var tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str="<input name=chk id='chk"+i+"' type=checkbox onclick='unCheckMaster("+i+")' contactType='"+lstContact.list[i].contactType+ "' contactTypeId='"+lstContact.list[i].contactTypeId+ "' salutationName='"+lstContact.list[i].salutationName+ "' firstName='"+lstContact.list[i].firstName+ "' lastName='"+lstContact.list[i].lastName+ "' cntEmail='"+lstContact.list[i].cntEmail+ "' contactCell='"+lstContact.list[i].contactCell+ "' contactFax='"+lstContact.list[i].contactFax+ "' address1='"+ lstContact.list[i].address1+ "' address2='"+lstContact.list[i].address2+ "'address3='"+lstContact.list[i].address3+ "' cityName='"+lstContact.list[i].cityName+ "' stateName='"+lstContact.list[i].stateName+ "' countyName='"+lstContact.list[i].countyName+ "' pinNo='"+lstContact.list[i].pinNo+ "' cityCode='"+lstContact.list[i].cityCode+ "' stateCode='"+lstContact.list[i].stateCode+ "' countyCode='" +lstContact.list[i].countyCode+ "' />";
			//str="<input name=chk type=radio onclick=getInfoAndUpdate('"+escape(lstContact.list[i].contactType)+ "','"+escape(lstContact.list[i].contactTypeId)+ "','"+escape(lstContact.list[i].salutationName)+ "','"+escape(lstContact.list[i].firstName)+ "','"+escape(lstContact.list[i].lastName)+ "','"+escape(lstContact.list[i].cntEmail)+ "','"+escape(lstContact.list[i].contactCell)+ "','"+escape(lstContact.list[i].contactFax)+ "','"+ escape(lstContact.list[i].address1)+ "','"+escape(lstContact.list[i].address2)+ "','"+escape(lstContact.list[i].address3)+ "','"+escape(lstContact.list[i].cityName)+ "','"+escape(lstContact.list[i].stateName)+ "','"+escape(lstContact.list[i].countyName)+ "','"+escape(lstContact.list[i].pinNo)+ "','"+escape(lstContact.list[i].cityCode)+ "','"+escape(lstContact.list[i].stateCode)+ "','" +escape(lstContact.list[i].countyCode)+ "') />";
			//alert(str);
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=lstContact.list[i].firstName;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=lstContact.list[i].lastName;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=lstContact.list[i].orderNumber;
			CellContents = str;
			tblcol.innerHTML = CellContents;	
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=lstContact.list[i].address1;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=lstContact.list[i].cityName;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=lstContact.list[i].stateName;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			document.getElementById('hdnCounterLength').value=i;

		}
		
		var tblRow=document.getElementById('tabContact').insertRow();
   		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 7;
		str="<div class='searchBg' align='center' onclick='selectInsert()' ><a href='#'>OK</a></div>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var pagenumber=document.getElementById('txtPageNumber').value;
		var MaxPageNo=document.getElementById('txtMaxPageNo').value;
		if(pagenumber && MaxPageNo==1)
		{
			document.getElementById('first').disabled=true;
	     	document.getElementById('prev').disabled=true;
			document.getElementById('last').disabled=true;
	     	document.getElementById('next').disabled=true;
		}
		if(pagenumber==1 && MaxPageNo!=1){
			document.getElementById('first').disabled=true;
			document.getElementById('prev').disabled=true;
			document.getElementById('last').disabled=false;
			document.getElementById('next').disabled=false;
		}
		if(pagenumber==MaxPageNo && pagenumber!=1){
	   		document.getElementById('last').disabled=true;
	  		document.getElementById('next').disabled=true;
	   		document.getElementById('first').disabled=false;
		    document.getElementById('prev').disabled=false;
		}
	 	if(pagenumber!=MaxPageNo && pagenumber!=1){
	    	document.getElementById('last').disabled=false;
	    	document.getElementById('next').disabled=false;
	    	document.getElementById('first').disabled=false;
	    	document.getElementById('prev').disabled=false;
	 	}
	}
}

function clearFields()
{
	document.getElementById('txtFirstName').value="";
	document.getElementById('txtLastName').value="";
	document.getElementById('txtOrderNumber').value="";
	document.getElementById('txtAddress1').value="";
	document.getElementById('txtCityName').value="";
	document.getElementById('txtStateName').value="";
}

function isBlankFields()
{

	var path='<%=request.getContextPath()%>';
	
	var firstName = document.getElementById('txtFirstName');
	var lastName = document.getElementById('txtLastName');
	var orderNo = document.getElementById('txtOrderNumber');
	var address1 = document.getElementById('txtAddress1');
	var cityName = document.getElementById('txtCityName');
	var stateName = document.getElementById('txtStateName');
	
	/*if(firstName.value=="" && lastName.value=="" && address1.value=="" 
		&& cityName.value=="" && stateName.value=="" && orderNo.value=="")
	{
		alert("Please enter atleast one search criteria");
		return false;
	}*/
	if(orderNo.value=="0"){
		alert("Value O is not allowed");
		return false;
	}
	var searchFlag=1;
	
	if( trim(firstName.value).length>0){
		if(ValidateTextField(firstName,path,'First Name')==false){
			searchFlag=0;
			return false;
		}
	}
	
	if( trim(lastName.value).length>0){
		if(ValidateTextField(lastName,path,'Last Name')==false){
			searchFlag=0;
			return false;
		}
	}
	if( trim(orderNo.value).length>0){
		if(checkdigits(orderNo)==false){
			searchFlag=0;
			return false;
		}
		//return checkdigits(orderNo);
	}
	
	if( trim(address1.value).length>0){
		if(ValidateTextField(address1,path,'Address1')==false){
			searchFlag=0;
			return false;
		}
	}
	
	if( trim(cityName.value).length>0){
		if(ValidateTextField(cityName,path,'City Name')==false){
			searchFlag=0;
			return false;
		}
	}
	
	if( trim(stateName.value).length>0){
		if(ValidateTextField(stateName,path,'State Name')==false){
			searchFlag=0;
			return false;
		}
	}
	
	if(searchFlag==1){
			searchFromList('FIRSTNAME','SELECTCONTACT');
	}
}

function fnCheckAll()
{
	count=document.getElementById('hdnCounterLength').value;
	if(document.getElementById("checks").checked==true)
	{
		for (i = 0 ; i <= count; i++)
		{
			//alert(i)
			document.getElementById('chk'+i).checked=true;
		}
	}
	else
	{
		for (i = 0 ; i <= count; i++)
		{
			document.getElementById('chk'+i).checked=false;
		}
	}
}

function unCheckMaster(counter)
{
	if(document.getElementById('chk'+counter).checked==false)
	{
		document.getElementById("checks").checked=false;
	}	
}

function selectInsert()
{
	var checkedContacts=0;
	var answer = confirm("Do You Want To  Continue")
	if(answer)
	{
		count=document.getElementById('hdnCounterLength').value;
		for (i = 0 ; i <= count; i++)
		{
			if(document.getElementById('chk'+i).checked==true)
			{
				checkedContacts++;
				getInfoAndUpdate(document.getElementById('chk'+i));
			}
		}
		if(checkedContacts==0)
		{
			alert('Please select atleast one checkbox !');
		}
		else
		{
			window.close();
		}
	}
}

function onPressEnterSearch(key_event){
    if (key_event.keyCode==13){
     	isBlankFields();
    }      
}
//<script type="text/javascript">
	//setValue()
	//DrawAccountType()	
	
	// <!-- end [003] -->
//

function pagingMethod(var_sortByColumn,var_pageName,var_methodName)
{
	var_sortByColumn=sortByColumn;
	if(var_methodName=='NextPage')
	{
		NextPage(var_sortByColumn,var_pageName);
	}
	else if(var_methodName=='LastPage')
	{
		LastPage(var_sortByColumn,var_pageName);
	}
	else if(var_methodName=='FirstPage')
	{
		FirstPage(var_sortByColumn,var_pageName);
	}
	else if(var_methodName=='PrevPage')
	{
		PrevPage(var_sortByColumn,var_pageName);
	}

}

function sortingMethod(var_sortByColumn,var_pageName)
{
	sortByColumn=var_sortByColumn;
	sortOrder(var_sortByColumn,var_pageName);

}

//-------------------------------------------------------------------------
</script>
</head>
<body onload="searchFromList('FIRSTNAME','SELECTCONTACT')">
<div class="head"> <span>Contact List </span> </div>
<center>
<html:form action="/NewOrderAction" styleId="contactFormId" method="post">
<bean:define id="formBean" name="newOrderBean"></bean:define>
<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="pagingMethod('FIRSTNAME','SELECTCONTACT','FirstPage')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="pagingMethod('FIRSTNAME','SELECTCONTACT','NextPage')">Next</a></td>
		<td align="center">
			<input type="text"  id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="pagingMethod('FIRSTNAME','SELECTCONTACT','PrevPage')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="pagingMethod('FIRSTNAME','SELECTCONTACT','LastPage')">Last</a></td>
	</tr>
	</table>	   		

   	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
	   <tr class="normal">
			<td  align="center" colspan="4">
			<div style="float:left;"><strong>First Name:</strong><input type="text" id="txtFirstName" name="txtFirstName" class="inputBorder1" maxlength="50" onkeydown="if (event.keyCode == 13)  return isBlankFields();" >										
			</div></td>

			<td  align="center" colspan="4">
			<div style="float:left;"><strong>Last Name:</strong><input type="text" id="txtLastName" name="txtLastName" class="inputBorder1"  maxlength="50" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
			</div></td>

			<td  align="center" colspan="4">
			<div style="float:left;"><strong>Order No:</strong><input type="text" id="txtOrderNumber" name="txtOrderNumber" class="inputBorder1"  maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();" >
			</div></td>

			<td  align="center" colspan="4">
			<div style="float:left;"><strong>Address1:</strong><input type="text" id="txtAddress1" name="txtAddress1" class="inputBorder1" maxlength="50" onkeydown="if (event.keyCode == 13)  return isBlankFields();" >										
			</div></td>

			<td  align="center" colspan="4">
			<div style="float:left;"><strong>City:</strong><input type="text" id="txtCityName" name="txtCityName" class="inputBorder1"  maxlength="50" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
			</div></td>

			<td  align="center" colspan="4">
			<div style="float:left;"><strong>State:</strong><input type="text" id="txtStateName" name="txtStateName" class="inputBorder1"  maxlength="50" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
			</div></td>

			<td>
			<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15" id="imgSearchId"></a>
			<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;
			</td>
		</tr>
	</table>
	
	<table width="100%" border="1" id="tabContact" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
		<tr valign="bottom" align="center" >
			<td align="center" width="8%">Select
				<input type="checkbox" name="checks" id="checks" onclick="fnCheckAll();"/>
				<input type="hidden" name="hdnCounterLength" id="hdnCounterLength" />
			</td>
			<td align="center" width="15%">
				<a href="#" onclick="sortingMethod('FIRSTNAME','SELECTCONTACT')">First Name</a>
			</td>
			<td width="15%" align="center">
				<a href="#" onclick="sortingMethod('LASTNAME','SELECTCONTACT')">Last Name</a>
			</td>
			<td width="15%" align="center">
				<a href="#" onclick="sortingMethod('ORDERNO','SELECTCONTACT')">Order No</a>
			</td>
			<td width="16%" align="center">
				<a href="#" onclick="sortingMethod('ADDRESS1','SELECTCONTACT')">Address 1</a>
			</td>
			<td width="16%" align="center">
				<a href="#" onclick="sortingMethod('CITY','SELECTCONTACT')">City</a>
			</td>
			<td width="16%" align="center">
				<a href="#" onclick="sortingMethod('STATE','SELECTCONTACT')">State</a>
			</td>
		</tr>								
	</table>
		
</html:form>
</center>
</body>
</html>