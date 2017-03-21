<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<html>
<head>
<title>Account Update Status</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" type="text/javascript">
</script>
<script>
<%String orderNo=request.getParameter("orderNo");%>
var orderNo=<%=orderNo%>;
function drawtable()
{
	var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	try
	{
	   	updatedAccountList = jsonrpc.processes.getUpdatedAccountList(orderNo);
	}
  	catch(e)
  	{	
  		alert("exception :  "+ e);
	}
   	for (i = 0; i <  updatedAccountList.list.length; i++)
 	{
		var count=0;
		count = count+i;
			
		var tblRow=document.getElementById('accountUpdateStatus').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=updatedAccountList.list[i].acctExternalId;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var  tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=updatedAccountList.list[i].acctInternalId;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=updatedAccountList.list[i].accountUpdateStatus;
		CellContents = str;
		tblcol.innerHTML = CellContents;
			
		document.getElementById('hdnCounterLength').value=count;
	}	
	if(updatedAccountList.list.length == 0)
	{
		var tblRow=document.getElementById('accountUpdateStatus').insertRow();
   		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 7;
		str='NO UPDATED ACCOUNT FOUND';
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}
}
</script>
<body onload= "drawtable()">
<table width="11%" border="1" cellspacing="0" cellpadding="0" height="213">
	<tr>
		<td valign="top">
			<fieldset class="border1"><legend> <b> Account Update Status</b> </legend>
				<div class="scrollWithAutoScroll" style="height:250px;width:450px;">
					<table border="1" cellspacing="0" cellpadding="0" align="center"
						width="60%" id='accountUpdateStatus'>
						<tr style="font-weight: bold;">
							<td align="center" nowrap height="40" colspan="1" width="150">Account ExternalId</td>
							<td align="center" nowrap height="40" colspan="1" width="150">Account InternalId</td>
							<td align="center" nowrap height="40" colspan="2" width="150">Status</td>
							<input type="hidden" name="hdnCounterLength" id="hdnCounterLength" />
						</tr>
					</table>
				</div>
			</fieldset>
		</td>
	</tr>
</table>
</body>
</html>