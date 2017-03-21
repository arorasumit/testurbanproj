<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.NetworkLocationBean"%>
<html>
<head>
<title>Network Location</title>
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

function editCustomerLocation(networkLocationId)
{

	var myForm = document.getElementById('searchForm');
	//myForm.actiontype.value="edit";
	
	myForm.hiddenNetworkLocationId.value=networkLocationId;
	myForm.action="<%=request.getContextPath()%>/networkLocation.do?method=editNetworkLocationUser";

    myForm.submit();
}
function fnAddNewLocation()
{
	var myForm = document.getElementById('searchForm');

	//myForm.actiontype.value="add";
	myForm.action="<%=request.getContextPath()%>/networkLocation.do?method=addNetworkLocationUserInit";
	myForm.submit();
}




function networkLocationSearch()
{


	var myForm=document.getElementById('searchForm');
	var networkLocationNameStr=myForm.networkLocationNameStr.value;
	var networkLocationIdStr=myForm.networkLocationIdStr.value;
	if((networkLocationNameStr=="" && networkLocationIdStr=="") || (networkLocationNameStr==null && networkLocationIdStr==null) ){
	
	alert("Please enter atleast one search criteria!");
	return;
	}else{
	searchSubmit();
	myForm.action="<%=request.getContextPath()%>/networkLocation.do?method=viewNetworkLocationList";
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
	
	if(checkForm()==false)
	{
		return ;
	}

	myform.method.value='viewNetworkLocationList';
		//showLayer();
	myform.submit();
}
function checkForm()
{
	
	

	return true;
}
function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	//setFormBean(myform);
	//myform.reset();
	myform.elements["pagingSorting.pageNumber"].value=val;
	
	myform.method.value='viewNetworkLocationList';	
	if(checkForm()==false)
	{
		return ;
	}
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
	if(checkForm()==false)
	{
		return ;
	}

	myform.method.value='viewNetworkLocationList';	
		//showLayer();
	myform.submit();
}


path='<%=request.getContextPath()%>';
</script>
<body >
	<html:form action="/networkLocation" styleId="searchForm" method="post">
	
	<bean:define id="formBean" name="networkLocationBean"></bean:define>
<html:hidden property="pagingSorting.sortByColumn"/>
<html:hidden property="pagingSorting.sortByOrder"/>
<html:hidden property="pagingSorting.pageNumber"/>

<html:hidden property="hiddenNetworkLocationId"/>

<input type="hidden" name="method" />
	<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
		<tr align="right">
			<td><img src="./gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
		</tr>
	</table>	
		
		<div class="head" > <span>NETWORK LOCATION</span> </div>
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
		
		<table width="100%"   border="0" cellspacing="0" cellpadding="0" align="center" id='tblNetworkLocationList'>
	
		
		
				<tr>
				<td align="center" width="50%" valign="top">
						<fieldset class="border2" style="width:97%;"><legend> <b>Search</b>
						</legend>
						<logic:notPresent name="networkLocationDetailsBean" scope="request">
							<table border="0" cellspacing="0" cellpadding="1" style="margin-top:7px ">
								<tr>
									<td align="center" style="font-size:9px"> Network Location Code </td>
									<td>
										<html:text  property="networkLocationIdStr" styleClass="inputBorder1" style="width:50px;float:left;" maxlength="18" onblur="if( trim(this.value).length>0){ this.form.networkLocationNameStr.value='';return checkdigits(this)};"></html:text>
										
									</td>
									<td align="center" style="font-size:9px;" nowrap>Network Location Name</td>
									<td><html:text  property="networkLocationNameStr" styleClass="inputBorder1" style="width:100px;" maxlength="200" onblur="if( trim(this.value).length>0){ this.form.networkLocationIdStr.value=''; return ValidateTextField(this,path,'Title')};"></html:text></td>
								</tr>
								
							</table>
						</logic:notPresent>
						</fieldset>
						
					
				</td>
			
				</tr>
				
				<tr>
					<td align="center" width="50%" valign="top">
					<table border="0" align="center" >
					<tr align="center">
						<td align="center" width="50%" valign="top">
						<div class="searchBg" style="margin-right:10px;"><a href="#" onClick="javascript:networkLocationSearch();">Search</a></div>
						</td>
					</tr>
					</table>
				</td>
				</tr>
					
			<tr><td>
			<fieldset class="border2"><legend> <b>Network Location Details</b>
			</legend>
		
			<!-- View User List -->
			<table id="" align="center" width="100%" style="display:block"   cellspacing="0" cellpadding="0" align="center">
				
				<tr>
					<td  align="left" style="font-size:10px" nowrap="nowrap" width="82%"></td>
					
					<td nowrap="nowrap">
					<div class="searchBg" style="margin-right:10px;"><a onclick="javascript:fnAddNewLocation();" ><span>Add New Location</span></a></div>
					</td>
				</tr>
			</table>
			<table width="100%">
				<tr>
					<td>
						<%	String currPageNumber =String.valueOf(((NetworkLocationBean)formBean).getPagingSorting().getPageNumber());
							String 	maxPageNumber =String.valueOf(((NetworkLocationBean)formBean).getPagingSorting().getMaxPageNumber());
						%>
						<jsp:include flush="true" page="../paging.jsp">
						<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
						<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
					</jsp:include>
					</td>
				</tr>
			</table>
			<div class="scrollWithAutoScroll" class="head" style="height:400px; width:945px; " >
			<table id="" align="center" width="100%" style="display:block"   cellspacing="0" cellpadding="0" align="center">
				<tr>
					<td colspan="3">
						<table cellpadding="0" align="center" cellspacing="0" class="tab2" width="100%" border="1" >
						<tr class="normal">
							<td>&nbsp;</td>
						
							<td align="center" style="font-size:9px">&nbsp;<b><a href="#" onclick="sortSubmit('LOCATION_CODE')">Network Location Id</a></b></td>
							<td align="center" style="font-size:9px">&nbsp;<b>Title</b></td>
							<td align="center" style="font-size:9px">&nbsp;<b>First Name</b></td>
							<td align="center" style="font-size:9px">&nbsp;<b>Last Name</b></td>
							<td align="center" style="font-size:9px">&nbsp;<b>Telephone Number</b></td>
							<td align="center" style="font-size:9px">&nbsp;<b>E-Mail</b></td>
							<td align="center" style="font-size:9px">&nbsp;<b>Fax</b></td>							
							<td align="center" style="font-size:9px">&nbsp;<b>Address1</b></td>
							<td align="center" style="font-size:9px">&nbsp;<b>Address2</b></td>
							<td align="center" style="font-size:9px">&nbsp;<b>Address3</b></td>
							<td align="center" style="font-size:9px">&nbsp;<b>Address4</b></td>
							<td align="center" style="font-size:9px">&nbsp;<b>City</b></td>
							<td align="center" style="font-size:9px">&nbsp;<b>State</b></td>
							<td align="center" style="font-size:9px">&nbsp;<b>Postal Code</b></td>
							<td align="center" style="font-size:9px">&nbsp;<b>Country</b></td>
							
							
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
							<td>
							<a href="#" 
								onclick="javascript:editCustomerLocation('<bean:write name="row" property="networkLocationId"/>');">Edit</a></td>
							
							<td align="left" style="font-size:9px">&nbsp;<bean:write  name="row" property="networkLocationId"/></td>
							<td align="left" style="font-size:9px">&nbsp;<bean:write  name="row" property="title"/></td>
							<td align="left" style="font-size:9px">&nbsp;<bean:write  name="row" property="firstname"/></td>
							<td align="left" style="font-size:9px">&nbsp;<bean:write  name="row" property="lastName"/></td>														
							<td align="left" style="font-size:9px">&nbsp;<bean:write  name="row" property="telephonePhno"/></td>
							<td align="left" style="font-size:9px">&nbsp;<bean:write  name="row" property="email_Id"/></td>
							<td align="left" style="font-size:9px">&nbsp;<bean:write  name="row" property="fax"/></td>
							<td align="left" style="font-size:9px">&nbsp;<bean:write  name="row" property="address1"/></td>
							<td align="left" style="font-size:9px">&nbsp;<bean:write  name="row" property="address2"/></td>
							<td align="left" style="font-size:9px">&nbsp;<bean:write  name="row" property="address3"/></td>
							<td align="left" style="font-size:9px">&nbsp;<bean:write  name="row" property="address4"/></td>
							<td align="left" style="font-size:9px">&nbsp;<bean:write  name="row" property="cityName"/></td>
							<td align="left" style="font-size:9px">&nbsp;<bean:write  name="row" property="stateName"/></td>
							<td align="left" style="font-size:9px">&nbsp;<bean:write  name="row" property="postalCode"/></td>
							<td align="center" style="font-size:9px">&nbsp;<bean:write  name="row" property="countryName"/></td>
						</tr>
						</logic:iterate>		
						</logic:notEmpty>
						<logic:empty  name="customerList">
						   <tr align="center" >
							 <td colspan="16" align="center"><B><font color="red">No Record Found</font></B></td>
						   </tr>
						</logic:empty>	
						</logic:present>				
						</table>
					</td>
				</tr>
			
			</table>
			</div>
			</fieldset>
			</td>
			</tr>
			</table>
			
			</html:form>
		
</body>

</html>
