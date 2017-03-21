<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Anil Kumar		23-May-11	CSR00-05422     Session Expired code with AjaxJSON  -->
<!--[002]	 Anil Kumar		3-Oct-11	CSR00-05422     Use Ctrl + S for Save  -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>Product/SubProduct</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script type="text/javascript" src="js/gen_validatorv31.js"></script>
<script language="javascript" type="text/javascript">
</script>
<script>
<%String serviceId=request.getParameter("serviceID");%>
var serviceId='<%=serviceId%>';
function productlist()  // To get line items with charges in them
{ 
	var tr, td, i, j, oneRecord;
    var test;
    
 	var combo = document.getElementById("txtProduct");

    var iCountRows = combo.length;
	  
	   for (i = 1; i <  iCountRows; i++)
	   {
	       combo.remove(1);
	   }
	   
	
	var jsData =			
		{
		serviceId:serviceId
		};
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	test = jsonrpc.processes.getProductAndId(jsData);
	
	if(document.getElementById('serviceTypeId_1')==null)
    {
		for(j=0;j<test.list.length;j++)
	    {
	    	var combo = document.getElementById("txtProduct");
	    	var option = document.createElement("option");
	   		option.text = test.list[j].serviceName;
	   		option.value = test.list[j].serviceTypeId;
			try 
			{
				combo.add(option, null); //Standard
			}
			catch(error) 
			{
				combo.add(option); // IE only
			}
	    }
	}
	else
    {
       var selectedProduct = document.getElementById('serviceTypeId_1').value;
	    for(j=0;j<test.list.length;j++)
	    {
	    	var combo1 = document.getElementById("txtProduct");
	    	var option = document.createElement("option");
	   		option.text = test.list[j].serviceName;
			option.value = test.list[j].serviceTypeId;
			try 
			{
				combo1.add(option, null); //Standard
			
			}
			catch(error) 
			{
				combo1.add(option); // IE only
			}
	    }
	    combo1.value = selectedProduct;
	}	    
    
    
}
function subproductlist() // To get line items with no charges in them
{ 
	var tr, td, i, j, oneRecord;
    var test;
    var serviceTypeId = document.getElementById("txtProduct").value;
 	var combo1= document.getElementById("txtSubProduct");
	   
	var iCountRows1 = combo1.length;
	  
	   for (i = 1; i <  iCountRows1; i++)
	   {
	       combo1.remove(1);
	   }
	
	if(document.getElementById("txtProduct").value==0)
 	{
 		return false;
 	}
	      
	var jsData =			
		{
		serviceTypeId:serviceTypeId
		};		
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	test = jsonrpc.processes.getSubProductAndId(jsData);
	
	
    if(document.getElementById('serviceSubTypeId_1')==null)
    {
	    for(j=0;j<test.list.length;j++)
    	{
	    	var combo1 = document.getElementById("txtSubProduct");
	    	var option = document.createElement("option");
	   		option.text = test.list[j].serviceSubTypeName;
	   		option.value = test.list[j].serviceSubtypeId;
			try 
			{
				combo1.add(option, null); //Standard
			}
			catch(error) 
			{
				combo1.add(option); // IE only
			}
    	}
    }
    else
    {
       var selectedSubProduct = document.getElementById('serviceSubTypeId_1').value;
	    for(j=0;j<test.list.length;j++)
	    {
	    	var combo1 = document.getElementById("txtSubProduct");
	    	var option = document.createElement("option");
	   		option.text = test.list[j].serviceSubTypeName;
			option.value = test.list[j].serviceSubtypeId;
			try 
			{
				combo1.add(option, null); //Standard
			
			}
			catch(error) 
			{
				combo1.add(option); // IE only
			}
	    }
	    combo1.value = selectedSubProduct;
	}     	
}
function getUpdateData()
{
		var jsData =			
		{
			serviceId:serviceId
		};
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
		test = jsonrpc.processes.getUpdateData(jsData);
		if(test.list.length>0)
		{
			document.getElementById("serviceTypeId_1").value=test.list[0].serviceTypeId;
			document.getElementById("serviceSubTypeId_1").value=test.list[0].serviceSubtypeId;
			//document.getElementById("isPublished").value=test.list[0].isPublished;
			//var combo2 = document.getElementById("txtIsPublish");
			//combo2.value=document.getElementById("isPublished").value;
			productlist();
			subproductlist();
		}
		else
		{
			productlist();
		}
		
}
function saveData()
{
	var productId = document.getElementById("txtProduct").value;
	var subProductId = document.getElementById("txtSubProduct").value;
	//var isPublished = document.getElementById("txtIsPublish").value;
	var isPublished = 0;
	var callerWindowObj = dialogArguments;
	var cntr = callerWindowObj.document.getElementById('hdnCurrentTempCounter').value;
	if(callerWindowObj.document.getElementById('chk'+(cntr-1)).value=="Yes")
	{	
		alert("Service already published \n Cannot Edit values");
		return false;
	}
	
	var sessionid ='<%=request.getSession().getId() %>';	
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");	
	newOrderStatusServiceId = jsonrpc.processes.newOrderStatusServiceId(serviceId);
	if(serviceId == newOrderStatusServiceId)
	{
		alert("Service already published");
		return false;
	}
	
	//Start by Saurabh for role check
	var callerWindowObj = dialogArguments;
	var orderNo = callerWindowObj.document.getElementById('poNumber').value
	
	<%
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
	%>
	
	var roleName = <%=objUserDto.getUserRoleId() %>;
	
	var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo);
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
		else if(stage=="Partial Publish" && (shortCode=="COPC" || shortCode=="SED"))
		{
			//do nothing
		}
		else
		{
			alert("You are not authorised to save or update the values");
			return false;
		}
	}
	//End By Saurabh
	
	if(productId==0 || subProductId==0)
	{
		alert("Please select all entries properly");
		return false;
	}
	else
	{
		try
		{
		var jsData =			
		{
			serviceId:serviceId,
			serviceTypeId:productId,
			serviceSubtypeId:subProductId,
			isPublished:isPublished,
			sessionid:sessionid
			
		};
				
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		test = jsonrpc.processes.savePublish(jsData);
		//start[001]
		//Meenakshi: commenting unreachable code
		/*if(test==null)
		{			
			var callerWindowObj = dialogArguments;
			var myForm=callerWindowObj.document.getElementById('searchForm');				
			myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=invalidsessoion";
			myForm.submit();
			alert("Session has been expired!!");		
			window.close();	
		} */
		//end[001]
		alert(test);
		window.close();
		}
		catch(e)
		{
			alert(e);		
			//Meenakshi: commenting unreachable code
			/*var callerWindowObj = dialogArguments;
			var myForm=callerWindowObj.document.getElementById('searchForm');				
			myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=invalidsessoion";
			myForm.submit();*/
			alert("Session has been expired!!");		
			window.close();				
		}
		
	}
}
//start[002]
document.onkeydown=checkKeyPress;
function checkKeyPress()
{
	if (event.ctrlKey && event.keyCode == 83) {						
				event.keyCode=4; 
				saveData();
				document.getElementById("saveButton").tabIndex = -1;
				document.getElementById("saveButton").focus();				   				   						
   }   
}
//end[002]
</script>
<body onload= "getUpdateData()">
<table width="11%" border="1" cellspacing="0" cellpadding="0"
	height="213">

	<tr>
		<td valign="top">
		<fieldset class="border1"><legend> <b>Product/SubProduct</b> </legend>
		<div class="scrollWithAutoScroll" style="height:250px;width:450px;">
		<table border="1" cellspacing="0" cellpadding="0" align="center"
			width="60%" id='partialPublish'>
			<tr style="font-weight: bold;">
				<td align="left" nowrap height="40" colspan="2" width="200">Product
				Name</td>
				<td align="left" nowrap height="40" colspan="2" width="200"><select
					name="txtProduct" id="txtProduct" onchange="subproductlist()"
					style="width:150px;float:left" class="dropdownMargin">
					<option value="0">Select a Product</option>
				</select></td>
			</tr>
			<tr style="font-weight: bold;">
				<td align="left" nowrap height="40" colspan="2" width="200">Sub Product
				Name</td>
				<td align="left" nowrap height="40" width="41"><select
					name="txtSubProduct" id="txtSubProduct"
					style="width:150px;float:left" class="dropdownMargin">
					<option value="0">Select a Sub Product</option>
				</select></td>
			</tr>
			<!-- <tr style="font-weight: bold;">
				<td align="left" nowrap height="40" colspan="2" width="200">Publish
				</td>
				<td align="left" nowrap height="40" width="41"><select
					name="txtIsPublish" id="txtIsPublish"
					style="width:150px;float:left" class="dropdownMargin">
					<option value="0">Select</option>
					<option value="1">Yes</option>
					<option value="2">No</option>
				</select></td>
			</tr> -->
			<tr>
				<td align="center" colspan="3">
				<button name="saveButton" id="saveButton" onclick="saveData()">Save</button>
				<input type="hidden" id="serviceTypeId_1">
				<input type="hidden" id="serviceSubTypeId_1">
				<input type="hidden" id="isPublished">
				</td>
			</tr>
		</table>
		</div>
		</fieldset>
		</td>
	</tr>
</table>
</body>
</html>