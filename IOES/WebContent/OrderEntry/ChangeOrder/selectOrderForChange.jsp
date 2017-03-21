<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<link href="gifs/treeview.css" type="text/css" rel="stylesheet">
<title>Selected Order For Change</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jsonrpc.js"></script>
<script language="javascript" type="text/javascript">
window.name='OrderForChange';
var callerWindowObj = dialogArguments;
function selectOrderNoForChange()
{
		var changeTypeId=document.getElementById("changeTypeId").value;
		var subChangeTypeId=document.getElementById("subChangeTypeId").value;
		var issuspended=0;
		var isdisconnected=0;
		if(subchangeTypeId==3 ||subchangeTypeId==7 ||subchangeTypeId==16 ||subchangeTypeId==14 )
		{
			issuspended = 1;
         	isdisconnected =0;
		}
		var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=getListOfOrderNo&accountID="+accountID;		
		window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:600px");
		
}
function DrawOrderForChange()
{
	
	if(document.getElementById('subChangeTypeId').selectedIndex==0)
	{
		alert("Please Select Sub Change  Type!!!");
		return false;
	}
	else if(document.getElementById('txtOrderNumber').value=='')
	{
		alert("Please Enter Order No First!!!");
		return false;
	}
	var changeTypeId=document.getElementById("changeTypeId").value;
	var subChangeTypeId=document.getElementById("subChangeTypeId").value;
	 
	//callerWindowObj.document.searchForm.hdnOrderForChange.value =document.getElementById('txtOrderNumber').value;
	//callerWindowObj.document.searchForm.changeType.value =changeTypeId;	
	//callerWindowObj.document.searchForm.hdnSubchangeType.value = subChangeTypeId;	
	//var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=getOrderForChange";	
	//window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:600px");
	var orderNo=document.getElementById('txtOrderNumber').value;
	var jsData =
	{
		orderNo:orderNo,
		changeTypeId:changeTypeId,
		subChangeTypeId:subChangeTypeId	
	}
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	var createdOrderNO = jsonrpc.processes.getCreatedChangeOrderNumber(jsData);	
	
	if(createdOrderNO=='' || createdOrderNO=='0')
	{
		alert("Error Occur..")
	}
	else
	{
	    //var myForm=document.getElementById('OrderType');
		//myForm.action="./ChangeOrderAction.do?method=incompleteOrder&draft=1&orderNo="+createdOrderNO;
		//myForm.submit();	
		delayer(createdOrderNO);	
	}
	
	
	window.close();
}
function delayer(createdOrderNO){
    callerWindowObj.window.location = "./ChangeOrderAction.do?method=incompleteOrder&draft=1&orderNo="+createdOrderNO;
}

function getChangeType()
{	
		var j;
	    var ChangeTypeList;
	    var combo=document.getElementById("changeTypeId");
	    try{
	    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    ChangeTypeList = jsonrpc.processes.getChangeType();
	    }
	    catch(e)
	    {
	    	alert("Exception :"+e);
	    }
	    for(j=0;j<ChangeTypeList.list.length;j++)
	    {
	    	
		   	var option = document.createElement("option");
		  		option.text = ChangeTypeList.list[j].changeTypeName;
		  		option.value = ChangeTypeList.list[j].changeTypeId;
			try 
			{
				combo.add(option, null); //Standard
			}
			catch(error) 
			{
				combo.add(option); // IE only
			}
	    }
	    var orderNoForChange="<%= request.getParameter("orderNo") %>";	    
	    document.getElementById('txtOrderNumber').value=orderNoForChange;
	   
}
function getSubChangeType()
{
          
     var i, j ;
     var ChangeTypeList;
     //alert("COMBO :"+document.getElementById("subChangeTypeId"))
     var combo = document.getElementById("subChangeTypeId");
     var iCountRows = combo.length;
	 // alert("iCountRows :"+iCountRows)
	    for (i = 1; i <  iCountRows; i++)
	    {
	       combo.remove(1);
	    }	
	    //alert("HdnChangeTypeId :"+document.getElementById('changeTypeId').selectedIndex);
	 	var changeTypeId=Number(document.getElementById('changeTypeId').value);
	 	try{
	    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    ChangeTypeList = jsonrpc.processes.populateChangeType(changeTypeId);
	    }
	    catch(e)
	    {
	    	alert("exception     " + e);
	    }
	    for(j=0;j<ChangeTypeList.list.length;j++)
	    {
	    	
		   	var option = document.createElement("option");
		  		option.text = ChangeTypeList.list[j].subChangeTypeName;
		  		option.value = ChangeTypeList.list[j].subChangeTypeId;
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

</script>
</head>
<body onload="getChangeType()" >
<html:form action="/ChangeOrderAction" styleId="OrderType" method="post" target="OrderForChange">
	<div class="head"> <span>Selected Order For Change</span> </DIV>
		<fieldset class="border1">
			<legend> <b>Selected Order</b> </legend>
			<table width="100%"  border="1" id="tabLogicalType" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="grayBg">
			        
										
					<tr>
						<td width="41%"><b>Change Type</b></td>
						<td width="59%">
						<select name="changeTypeId" id="changeTypeId" style="width:200px;float:left" class="dropdownMargin" onclick="getSubChangeType();" >
				   			<option value="0">Select Change Type</option>
			        	</select>
						</td>
					</tr>
					<tr>
						<td width="41%"><b>Sub Change Type</b></td>
						<td width="59%">
						<select name="subChangeTypeId" id="subChangeTypeId" style="width:200px;float:left" class="dropdownMargin"  >
				   			<option value="0">Select Sub Change Type</option>
			        	</select>
						</td>
					</tr>
					<tr>
						<td width="41%"><b>Selected Order No</b></td>
						<td   colspan="3">
						<input type="text" name="txtOrderNumber" id="txtOrderNumber" style="margin-right:50px;float:left" class="inputBorder1" readonly="true"/>
						<input type="button" name="btn" value="Ok" onClick="DrawOrderForChange()">											
					</td>
					</tr>
					

				
			</table>
		</fieldset>
</html:form>
</body>
</html>