<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<html>
<head>
<title>SelectCurrency</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/formValidations.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<script type="text/javascript">
var path='<%=request.getContextPath()%>';
	function setFrmValue()
	{
		var callerWindowObj = dialogArguments;
		document.getElementById('hdnServiceNo').value = callerWindowObj.document.getElementById('hdnserviceid').value;
	}	

   function insertTProductAttDetail()
	{
	   var i;
	   var count = 1;
	    var tabData = document.getElementById('tablePO');
		var jsData; 
		var stringarrayLabel = new Array();
		var stringarrayValue = new Array();


		for(var i=0;i<document.searchForm.prodAttValue.length;i++)
		{
			
			stringarrayLabel[i]= document.searchForm.prodAttValue[i].value;
		}
     	

		for(var i=0;i<document.searchForm.prodAttId.length;i++)
		{
			
			stringarrayValue[i]= document.searchForm.prodAttId[i].value;
		}

     	var jsData =
     			 {
	    			prodAttributeLabelArray:stringarrayLabel,
	    			prodAttributeValueArray:stringarrayValue,
	    			serviceDetailID:document.getElementById('hdnServiceNo').value
				 };

	  try
	  {
	  
	  
	  jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
   	  var status = jsonrpc.processes.insertTProductAttDetail(jsData);
   	  
   	  if(status=="1")
   	  {		alert("Service Product Attributes Added Succesfully")
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
</script>

<body onload="setFrmValue();">
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
						<logic:present name="productAttDetailList" scope="request">
		                   <logic:iterate id="tpDetailID" name="productAttDetailList" indexId="recordId" >
		                     <%  count=((recordId.intValue())+1);%>
							  <tr>
							    <td width="50%"><bean:write  name="tpDetailID" property="attMasterName"/></td>
							    <td width="50%">
								    <input type="text"  name="prodAttValue1" id="prodAttValue" class="inputBorder4" value="<bean:write name="tpDetailID" property="prodAttributeValue"/>" readonly="readonly"> 
								    <input type="hidden"  name="prodAttId" class="inputBorder4" value="<bean:write name="tpDetailID" property="attMasterId"/>"></td>
						     </tr>
				          </logic:iterate>
				       </logic:present>
					</table>
			</fieldset>
		</td>
	</tr>
</table>
</html:form>
</body>
</html>
