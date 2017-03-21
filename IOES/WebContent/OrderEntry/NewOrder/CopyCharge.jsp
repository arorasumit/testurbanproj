<!--[002]	 LAWKUSH 		18-AUG-11	CSR00-05422     For Copy Charge Validation  -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<html>
<head>
<title>Copy Charge</title>
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

var callerWindowObj = dialogArguments;
<%String serviceId=request.getParameter("serviceID");%>
var serviceId='<%=serviceId%>';
function productlist1()  // To get line items with charges in them
{ 
	var tr, td, i, j, oneRecord;
    var test;
    
 	var combo = document.getElementById("txtSource");

    var iCountRows = combo.length;
	  
	   for (i = 1; i <  iCountRows; i++)
	   {
	       combo.remove(1);
	   }
	   
	
	var jsData =			
		{
		serviceId:serviceId,
		chargeType:1
		};		
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	test = jsonrpc.processes.populateProductAndId(jsData);
	
	for(j=0;j<test.list.length;j++)
    {
    	var combo = document.getElementById("txtSource");
    	var option = document.createElement("option");
   		option.text = test.list[j].prodNameAndId;
   		option.value = test.list[j].serviceProductID;
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
function productlist2() // To get line items with no charges in them
{ 
	var tr, td, i, j, oneRecord;
    var test;
   
 	
 	var combo1= document.getElementById("txtDestination");
	   
	var iCountRows1 = combo1.length;
	  
	   for (i = 1; i <  iCountRows1; i++)
	   {
	       combo1.remove(1);
	   }
	      
	var jsData =			
		{
		serviceId:serviceId,
		chargeType:0
		};		
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	test = jsonrpc.processes.populateProductAndId(jsData);
	
	
    for(j=0;j<test.list.length;j++)
    {
    	var combo1 = document.getElementById("txtDestination");
    	var option = document.createElement("option");
   		option.text = test.list[j].prodNameAndId;
   		option.value = test.list[j].serviceProductID;
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
function copyproduct()
{
	var valueSource = document.getElementById("txtSource").value;
	var valueDestination = document.getElementById("txtDestination").value;
	if(valueSource==0 || valueDestination==0)
	{
		alert("Please Select Line Item");
		return false;
	}
	if(valueSource==valueDestination)
	{
		alert("Please Select Different Products");
		return false;
	}
	else
	{
		
				
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			//Added By Saurabh for Partial Publish Check
		    //Start By Saurabh
			newOrderStatusServiceId = jsonrpc.processes.newOrderStatusServiceId(serviceId);
			if(serviceId == newOrderStatusServiceId)
			{
				alert("Service already published");
				return false;
			}
			//End By Saurabh
		//	alert('ajdkljasldlasjdjalsdjlasjdl');
			//alert("txtSource  >>>"+document.getElementById("txtSource").value);
			
		//	alert("txtDestination  >>>"+document.getElementById("txtDestination").value);
			// Start[002]
			//alert('ajdkljasldlasjdjalsdjlasjdl');
			// Start[002]
			var test11;
			var jsData1 =			
				{
					sourceProductId:document.getElementById("txtSource").value,
					destinationProductId:document.getElementById("txtDestination").value
				};
					
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");		
			
			test11 = jsonrpc.processes.validateDestinationCharges(jsData1);
			//alert('65664564566656');
		var	LegalEntity=test11.list[0];
   		var	LicensingCompany=test11.list[1];
   // 	var	StoreMapped=test11.list[2] ;
    	var msg='';
    	
   // alert("LegalEntity  >>"+LegalEntity);
    	
    //	alert("LicensingCompany  >>"+LicensingCompany);
    	
    //	alert("StoreMapped  >>"+StoreMapped);
    	
    	if(LegalEntity==0)
    	{
    	msg=(msg+'Legal Entity Not Mapped To Destination Product\n');
    	}
    	 if(LicensingCompany==0)
    	{
    	msg=(msg+'Licensing Company Not Mapped To Destination Product\n');
    	}
  //  	if(StoreMapped==0)
  //  	{
   // 	msg=(msg+'Store Not Mapped To Destination Product');
   // 	}
    if(msg!='')	
    {
	alert(msg);
	return false;
	}
			//end [002]
		
			//end [002]
			
			var test;
			var jsData =			
		{
			sourceProductId:document.getElementById("txtSource").value,
			destinationProductId:document.getElementById("txtDestination").value
		};
			test = jsonrpc.processes.copyChargeFromOneToOther(jsData);
		
		
		
		if(test=="0")
		{
			alert("some errors occured while copying the charges.Please Try Again");
			window.close();
		}
		else if(test=="1")
		{
			alert("charges copied");
			callerWindowObj.fncServiceTreeView();
			window.close();
		}
		else
		{
			alert(test);
			return false;
		}
	}
}

</script>
<body onload= "productlist1();productlist2()">
<table width="11%" border="1" cellspacing="0" cellpadding="0"
	height="213">

	<tr>
		<td valign="top">
		<fieldset class="border1"><legend> <b>Copy
		Charge</b> </legend>
		<div class="scrollWithAutoScroll" style="height:200px;width:400px;">
		<table border="1" cellspacing="0" cellpadding="0" align="center"
			width="60%" id='copyCharge'>


			<tr style="font-weight: bold;">
				<td align="left" nowrap height="40" colspan="2" width="200">Source
				Line Item</td>
				<td align="left" nowrap height="40" width="41">Destination
				Line Item</td>
			</tr>
			<tr style="font-weight: bold;">
				<td align="left" nowrap height="40" colspan="2" width="200"><select
					name="txtSource" id="txtSource" style="width:150px;float:left"
					class="dropdownMargin">
					<option value="0">Select a Line Item</option>
				</select></td>
				<td align="left" nowrap height="40" width="41"><select
					name="txtDestination" id="txtDestination"
					style="width:150px;float:left" class="dropdownMargin">
					<option value="0">Select a Line Item</option>
				</select></td>
			</tr>
			<tr>
				<td align="center" colspan="3">
				<button name="copyButton" id="copyButton" onclick="copyproduct()">Copy</button>
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