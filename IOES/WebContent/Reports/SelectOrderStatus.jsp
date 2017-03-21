<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Select Order Status</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" type="text/javascript">

function getInfoAndUpdate(cType) 
{
	var callerWindowObj = dialogArguments;
	callerWindowObj.document.searchForm.orderStatusStr.value = unescape(cType);
	window.close();
	
}

function DrawOrderStatus()
{

	   var str;
	   var frm=document.getElementById('orderStatus');
	  
	   var mytable = document.getElementById('taborderStatus');	
	   var iCountRows = mytable.rows.length;
  
	   for (i = 2; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(2);
	   }   
	   var jsData =
			{
				orderStatus:frm.orderStatus.value
			};
				  
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var lstService = jsonrpc.processes.searchOrderStatus(jsData);
	 
	 for (i = 0; i <  lstService.list.length; i++)
	 {
	    var orderStatus=lstService.list[i].orderStatus;
		var tblRow=document.getElementById('taborderStatus').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";

		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(lstService.list[i].orderStatus) +"') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=lstService.list[i].orderStatus;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
	}	
	if(lstService.list.length == 0)
	{
	var tblRow=document.getElementById('taborderStatus').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 3;
		str='NO RECORD FOUND'
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}	
		return false;
}


</script>
</head>
<body style="overflow: auto;" >
<html:form action="/reportsAction" styleId="orderStatus" method="post">
		<fieldset width="98%" class="border1">
			<legend> <b>Order Status List</b> </legend>
			<table width="98%"  border="1" id="taborderStatus" align="center" class="tab2" >
			   <tr class="grayBg">
					<td align="center" colspan="2">
						<input width="100px" type="text" name="orderStatus" class="inputBorder1" onkeydown="if (event.keyCode == 13) return DrawOrderStatus();" />
						<div class="searchBg" onclick="DrawOrderStatus();"  style="margin-right:10px;"><a href="#" title="Search">Search</a></div>
					</td>
				</tr>
				<tr class="grayBg">
					<td width="3%" align="center">Select</td>
					<td width="16%" align="left">Order Status</td>
				</tr>
			</table>
		</fieldset>
</html:form>
</body>
<script type="text/javascript">
	DrawOrderStatus();
</script>
</html>
