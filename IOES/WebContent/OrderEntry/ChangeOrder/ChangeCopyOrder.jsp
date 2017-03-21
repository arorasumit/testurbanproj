<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>

<html>
<head>
<title>Copy Order</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">

var counter = 1;
var currentOrderNo;
var callerWindowObj = dialogArguments;
var serviceList;
currentOrderNo = callerWindowObj.document.getElementById('poNumber').value;
function populateService()
{
			var currentOrderNo = callerWindowObj.document.getElementById('poNumber').value;
			var myForm=document.getElementById('searchForm');
			var orderNo=document.getElementById('orderNo').value;
			var accountId=document.getElementById('accountID').value;
			if(orderNo != 0 )
			{
				var mytable = document.getElementById('tabLogicalType');	
	   			var iCountRows = mytable.rows.length;
	   			
	   			for (i = 1; i <  iCountRows; i++)
	   			{
	      			mytable.deleteRow(1);
	   			}  
	   			var mytable1 = document.getElementById('tabOk');	
	   			var iCountRows1 = mytable1.rows.length;
   				
   				for (i = 0; i <  iCountRows1; i++)
	   			{
	      			mytable1.deleteRow(0);
	   			}  
				getserviceForThisOrder(orderNo);
			}
			else
			{
				alert("Please Enter Order No ");
				return false;
			}
}
function getOrders()
{
	var currentOrderNo = callerWindowObj.document.getElementById('poNumber').value;
	var myForm=document.getElementById('searchForm');
	var orderNo=document.getElementById('orderNo').value;
	document.getElementById('hdnEnteredOrderNo').value=orderNo;
	var accountId=document.getElementById('accountID').value;
	var orlen=document.getElementById('orderNo').value.length;
	if((orderNo == 0 || trim(orderNo)== "" ))
	{
		alert("Please enter OrderNo ");
		return false;
	}
	
	if((orlen>10 ))
	{
		alert("Length Can't be Greater than 10 ");
		return false;
	}
		
	else
	{
		var jsData = 
			{
			orderNo:orderNo
			};
		jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		try
		{
			var orderDetails = jsonrpc.processes.getOrderDetails(jsData);
			if(orderDetails.list.length >= 1)
			{
				if(orderDetails.list[0].orderType!="Change")
				{
					alert("Only Change Orders are Allowed for Copy Order/Services in Change Order");
					return false;
				}
				document.getElementById('orderNo').value=orderDetails.list[0].orderNo;
				document.getElementById('accountID').value=orderDetails.list[0].accountID;
				document.getElementById('accountManager').value=orderDetails.list[0].accountManager;
				document.getElementById('projectManager').value=orderDetails.list[0].projectManager;
				document.getElementById('quoteNo').value=orderDetails.list[0].quoteNo;
				document.getElementById('currencyCode').value=orderDetails.list[0].currencyCode;
				populateService();
			}
			else
			{
				document.getElementById('accountID').value="";
				document.getElementById('accountManager').value="";
				document.getElementById('projectManager').value="";
				document.getElementById('quoteNo').value="";
				document.getElementById('currencyCode').value="";
				populateService();
			}
  		}
		catch(e)
		{	
			alert("exception :  "+ e);
		}
	}
}
function copyServices()
{
			var myForm=document.getElementById('searchForm')
			var count = document.getElementById('hdnCounterLength').value;
			var number = -1;
			var serviceList='';
			var serviceProductList='';
			var poList='';
			var licenseCompanyList='';
			var storeList='';
			for (i = 0 ; i <= count; i++)
    		{
    				if(document.getElementById('chk'+i).checked)
					{
						//serviceList = serviceList + document.getElementById('chk'+i).value + ",";
						var productList = jsonrpc.processes.populateProductList(document.getElementById('chk'+i).value);
    					if(productList.list.length>0)
	    				{
	    					serviceList = serviceList + document.getElementById('chk'+i).value + ",";
	    				}
    					for (var j = 0; j <  productList.list.length; j++)
    					{
    						var serviceProductID = productList.list[j].serviceProductID;
    						serviceProductList = serviceProductList + serviceProductID + ",";
    						
    						//Checking PO details for Each Line Item
    						if(document.getElementById('po'+serviceProductID).value==0)
    						{
    							alert("Please Select Valid PO for Line item" +serviceProductID);
    							document.getElementById('po'+serviceProductID).focus();
    							return false;
    						}
    						else
    						{
    							poList = poList + document.getElementById('po'+serviceProductID).value + ",";
    						}
    						//Checking License Company for Each Line Item
    						if(document.getElementById('lcompany'+serviceProductID).value==0)
    						{
    							alert("Please Select License Company for Line item" +serviceProductID);
    							document.getElementById('lcompany'+serviceProductID).focus();
    							return false;
    						}
    						else
    						{
    							licenseCompanyList = licenseCompanyList + document.getElementById('lcompany'+serviceProductID).value + ",";
    						}
    						//Checking Store for Each Line Item
    						if(document.getElementById('store'+serviceProductID).value==0 && document.getElementById('store'+serviceProductID).disabled==false)
    						{
    							alert("Please Select Store for Line item" +serviceProductID);
    							document.getElementById('store'+serviceProductID).focus();
    							return false;
    						}
    						else
    						{
    							if(document.getElementById('store'+serviceProductID).disabled=true)
    							{
    								storeList = storeList + 0 + ",";
    							}
    							else
    							{
    							storeList = storeList + document.getElementById('store'+serviceProductID).value + ",";
    							}
    						}
    					}
					}
					else 
					//if(document.getElementById('chk'+i).checked == false)
					{
						number++;
						
						//alert("count "+count);
					}
					if(number==count)
					{
						//alert("serviceList "+serviceList);
						alert("Please select a service");
						return false;
					}
			}	
			currentOrderNo = callerWindowObj.document.getElementById('poNumber').value;
			enteredOrderNo = document.getElementById('hdnEnteredOrderNo').value;
			document.getElementById('hdnCurrentOrderNo').value = currentOrderNo;
			//alert("serviceList >>>>> "+serviceList);
			//alert("serviceProductList >>>>> "+serviceProductList);
			//alert("poList >>>>> "+poList);
			//alert("licenseCompanyList >>>>> "+licenseCompanyList);
			//alert("storeList >>>>> "+storeList);
			//return false;
			var jsData = 
				{
					serviceIdString:serviceList,
					orderNo:currentOrderNo
				};
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			try
			{
		   	 	var status = jsonrpc.processes.copyServicesToTheOrder(currentOrderNo,enteredOrderNo,serviceList,serviceProductList,poList,licenseCompanyList,storeList);
		    }
    		catch(e)
    		{	
    			alert("exception :  "+ e);
    		}
    			alert(status);
    			var flag=5;
    			callerWindowObj.ViewServiceTree(flag);
				window.close();
			
}
/*
	Function to display or hide the table having Line Item level details
*/
function show(tbl,btn)  
{
	var currentOrderNo=callerWindowObj.document.getElementById('poNumber').value;
	var enteredOrderNo=document.getElementById('hdnEnteredOrderNo').value;
	if(currentOrderNo==enteredOrderNo)
	{
		return false;
	}
	if (btn.checked)
	{
		document.all.item(tbl).style.display="block";
	}
	else
	{
		
		document.all.item(tbl).style.display="None";
	}
}

function getserviceForThisOrder(orderNo) 
{
		var myForm=document.getElementById('searchForm');
				var jsData = 
				{
				orderNo:orderNo
				};
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		try
		{
		   	serviceList = jsonrpc.processes.getServicesForTheOrder(jsData);
		}
   		catch(e)
   		{	
   			alert("exception :  "+ e);
		}
	   	for (i = 0; i <  serviceList.list.length; i++)
	 	{
			var tblRow=document.getElementById('tabLogicalType').insertRow();
		
				var count=0;
				count=count+i;
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str="<input name='chk' id='chk"+count+"' type='checkbox' value='"+serviceList.list[i].serviceId+"' onclick=show('tblServiceProductSummary"+i+"',this) />";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=serviceList.list[i].serviceId;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=serviceList.list[i].serviceTypeName
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				document.getElementById('hdnCounterLength').value=count;
				drawPOTable(i,serviceList.list[i].serviceId);
			   	
		}
 		if(serviceList.list.length == 0)
		{
			
			document.getElementById('accountID').value="";
			document.getElementById('accountManager').value="";
			document.getElementById('projectManager').value="";
			document.getElementById('quoteNo').value="";
			document.getElementById('currencyCode').value="";
			
			var tblRow=document.getElementById('tabLogicalType').insertRow();
    		tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			tblcol.colSpan = 7;
			str='NO RECORD FOUND';
			CellContents = str;
			tblcol.innerHTML = CellContents;
		}
		else
		{
			var tblRow=document.getElementById('tabOk').insertRow();
    		tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			tblcol.colSpan = 7;
			str="<div class='searchBg' align='center' onclick='copyServices()' ><a href='#'>OK</a></div>";
			CellContents = str;
			tblcol.innerHTML = CellContents;
		}	
     		
}

function drawPOTable(i,serviceId)
{
	var tblRow=document.getElementById('tabLogicalType').insertRow();
	
	tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	width="100px"; 
	colspan="10";
	str="";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.colSpan=3;
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<table width='100%' class='gray' border='1' align='center' cellspacing='0' cellpadding='0' id='tblServiceProductSummary"+i+"' style='display: none;'></table>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	makeHeader(i);
	var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	try
	{
    	var productList = jsonrpc.processes.populateProductList(serviceId);
    	for (var j = 0; j <  productList.list.length; j++)
	 	{
			var tblRow=document.getElementById('tblServiceProductSummary'+i).insertRow();
			var serviceProductID = productList.list[j].serviceProductID;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=productList.list[j].serviceProductID + ' - ' + productList.list[j].serviceName;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str="<select name='po' id='po"+serviceProductID+"' width='90%' class='dropdownMargin' onchange='fillLicenseCompany("+serviceProductID+")'><option value='0'>Select Valid PO</option> </select>";
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			fillPO(serviceProductID);
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str="<select name='lcompany' id='lcompany"+serviceProductID+"' width='90%' class='dropdownMargin' onchange='fillStore("+serviceProductID+")' ><option value='0'>Select License Company</option> </select>";
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			fillLicenseCompany(serviceProductID);
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str="<select name='store' id='store"+serviceProductID+"' width='90%' class='dropdownMargin' ><option value='0'>Select Store</option> </select>";
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			fillStore(serviceProductID);
			if(productList.list[j].isServiceActive==0)
			{
				document.getElementById('store'+serviceProductID).disabled="disable";
				document.getElementById('store'+serviceProductID).selectedIndex=0;
			}
		}
	}
	catch(e)
	{	
		alert("exception :  "+ e);
	}
	
}

function makeHeader(i)
{
	var tblRow=document.getElementById('tblServiceProductSummary'+i).insertRow();
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="LineItem Id";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="Valid Po";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="LicenseCompany";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="Store";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
}

function fillPO(serviceProductID) 
{
 		var poCombo = document.getElementById('po'+serviceProductID);
		var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
        poComboList = jsonrpc.processes.populatePODetailForCopyOrder(currentOrderNo,serviceProductID);
		
		/*for(k=poCombo.options.length-1;k>=1;k--)
		{
			poCombo.remove(k);
		}*/
		
		for(z=0;z<poComboList.list.length;z++)

	    {
	    	
	    	var option = document.createElement("option");
	   		option.text = poComboList.list[z].podetailID;
			option.value = poComboList.list[z].podetailID;
			try 
			{
				poCombo.add(option, null); //Standard
			}
			catch(error) 
			{
				poCombo.add(option); // IE only
			}
			if(poComboList.list[z].isDefaultPO==1)
			{
				document.getElementById('po'+serviceProductID).selectedIndex=z+1;
			}
	    }
} 

function fillLicenseCompany(serviceProductID) 
{
 		var lcCombo = document.getElementById('lcompany'+serviceProductID);
		var poId = document.getElementById('po'+serviceProductID).value;
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
        lcComboList = jsonrpc.processes.populateLicenseCompanyForCopyOrder(serviceProductID,poId);
        
        if(lcCombo.options.length>1)
        {
        	for(k=lcCombo.options.length-1;k>=1;k--)
			{
				lcCombo.remove(k);
			}
        }
		for(z=0;z<lcComboList.list.length;z++)
	    {
	    	
	    	var option = document.createElement("option");
	   		option.text = lcComboList.list[z].licCompanyName;
			option.value = lcComboList.list[z].licCompanyID;
			try 
			{
				lcCombo.add(option, null); //Standard
			}
			catch(error) 
			{
				lcCombo.add(option); // IE only
			}
	    }
	    if(document.getElementById('po'+serviceProductID).value!=0)
	    {
	    	document.getElementById('lcompany'+serviceProductID).selectedIndex=1;
	    }
	    if(lcComboList.list.length==0)
	    {
	    	document.getElementById('lcompany'+serviceProductID).selectedIndex=0;
	    }
} 

function fillStore(serviceProductID) 
{
 		var storeCombo = document.getElementById('store'+serviceProductID);
		var lCompId = document.getElementById('lcompany'+serviceProductID).value;
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
        storeComboList = jsonrpc.processes.populateStoreForCopyOrder(serviceProductID,lCompId);
        
        for(k=storeCombo.options.length-1;k>=1;k--)
		{
			storeCombo.remove(k);
		}
        
		for(z=0;z<storeComboList.list.length;z++)
	    {
	    	
	    	var option = document.createElement("option");
	   		option.text = storeComboList.list[z].storeName;
			option.value = storeComboList.list[z].storeID;
			try 
			{
				storeCombo.add(option, null); //Standard
			}
			catch(error) 
			{
				storeCombo.add(option); // IE only
			}
	    }
	    /*if(storeComboList.list.length==0)
	    {
	    	document.getElementById('store'+serviceProductID).readOnly="true";
	    }
	    else */if(document.getElementById('po'+serviceProductID).value!=0)
	    {
	    	document.getElementById('store'+serviceProductID).selectedIndex=1;
	    }
	    if(storeComboList.list.length==0)
	    {
	    	document.getElementById('store'+serviceProductID).selectedIndex=0;
	    }
} 
/*
<input name="btn1"
onClick="show('tbl',this,<c:out value="${sno}"/>)"
type="button" value="+"
style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;&nbsp;
*/
path='<%=request.getContextPath()%>';
</script>

<body >
	<html:form action="/NewOrderAction" styleId="searchForm" method="post">
		<bean:define id="formBean" name="newOrderBean"></bean:define>
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="pagingSorting.sortByOrder"/>
		<html:hidden property="pagingSorting.pageNumber"/>
		<input type="hidden" name="method" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			

		</table>	
			
		<div border="1" class="head"> <span>Search Order</span> </div>
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
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Search</b></legend>
				<table border="0"  align="center" style="margin-top:7px">
					<tr>
					<td width="90px"/>
						<td align="right" style="font-size:9px"> Order No:</td>
						<td align="right" style="font-size:9px">
						<input type="text"  name="orderNo" id="orderNo" maxlength="10" style="width:80px;float: left" align="left" class="inputBorder1" value="" 
							onblur="if( trim(this.value).length>0){ return checkdigits(this)};" 
							onkeydown="if (event.keyCode == 13)  return getOrders();"/>
							<!-- <input type="button" width="20" name = "..." onclick="getOrders()" > -->
							<div class="searchBg1" onclick="getOrders()" ><a href="#">..</a></div>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">Account No:</td>
						<td nowrap>				
								<input type="text" name="accountID" id="accountID" width="75px" class="inputBorder1" value="" readonly="true" />
						</td>
						
						
						<td width="10px"/>
						<td align="right" style="font-size:9px;">Account Mgr:</td>
						<td nowrap>
						<input type="text" name="accountManager" id="accountManager" width="75px" class="inputBorder1" value="" readonly="true" />
						</td>
						<td width="50px"/>
					</tr>
					<tr>
					<td width="90px"/>
						<td align="right" style="font-size:9px">Project Mgr :</td>
						<td align="right" style="font-size:9px">
						<input type="text" name="projectManager" id="projectManager" width="75px" class="inputBorder1" value="" readonly="true" />
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">Quote No:</td>
						<td nowrap>
						<input type="text" name="quoteNo" id="quoteNo"width="75px" class="inputBorder1" value="" readonly="true" />
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">Currency:</td>
						<td nowrap>
						<input type="text" name="currencyCode" id="currencyCode" width="75px" class="inputBorder1" value="" readonly="true" />
						</td>
						<td width="50px"/>
					</tr>
					<!-- <tr>
					<td width="100px" colspan="10" align="right">
						<input type="button" name="Populate Service" onclick="populateService()" />
					</td>
					</tr> -->
				</table>
		</fieldset>
		
		<fieldset class="border1">
			<legend> <b>Service List</b> </legend>
			<table width="100%"  border="1" id="tabLogicalType" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   
				<tr class="grayBg">
				    <td width="" align="center">Select</td>
					<td >serviceId</td>
					<td >serviceTypeName</td>
					
				</tr>
			</table>
			<table width="100%"  border="0" id="tabOk" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   	<tr class="grayBg">
					<!-- <td>
					<div class="searchBg" onclick="copyServices()" ><a href="#">OK</a></div>
					</td>
					<td>
					<div class="searchBg" onclick="cancelSelection()" ><a href="#">Cancel</a></div>
					</td> -->
				</tr>
			</table>
		</fieldset>
		<input type="hidden" id = "hdnCurrentOrderNo" name="hdnCurrentOrderNo">
		<input type="hidden" name="hdnCounterLength" id="hdnCounterLength" />
		<input type="hidden" name="hdnEnteredOrderNo" id="hdnEnteredOrderNo" />
		
	</html:form>
</body>
</html>
