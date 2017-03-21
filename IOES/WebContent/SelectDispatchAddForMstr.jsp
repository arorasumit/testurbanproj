<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>SelectDispatch</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" type="text/javascript">
function getInfoAndUpdate(dipID,dipName) 
{
	var callerWindowObj = dialogArguments;
	callerWindowObj.document.searchForm.dispatchAddressIdStr.value = dipID;
	callerWindowObj.document.searchForm.dispatchAddressStr.value = unescape(dipName);

	window.close();
}

function DrawDispatch()
{
	   var str;
	   var frm=document.getElementById('dispatchFormId');
	   
	  
	  
	   var mytable = document.getElementById('tabDispatch');	
	   var iCountRows = mytable.rows.length;
  
	   for (i = 2; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(2);
	   }   
	   var callerWindowObj = dialogArguments;
	   var jsData =
			{
				searchAccount:callerWindowObj.document.searchForm.accountIdStr.value,
				searchDispatchAddress:document.getElementById('searchDispatchAddress').value
				
			};
	
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		  
	var lstDispatch = jsonrpc.processes.getDispatchAddressDetails(jsData);
	
	if(showErrorsIfAny(lstDispatch))
	{
		return;
	}
	
	 for (i = 0; i <  lstDispatch.list.length; i++)
	 {
		var tblRow=document.getElementById('tabDispatch').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";

		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+escape(lstDispatch.list[i].dispatchAddressId) + "','"+ escape(lstDispatch.list[i].dispatchAddressName) + "') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstDispatch.list[i].dispatchAddressId;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstDispatch.list[i].dispatchAddressName;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
	}	
	if(lstDispatch.list.length == 0)
	{
	var tblRow=document.getElementById('tabDispatch').insertRow();
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


function showErrorsIfAny(var_lstDispatch)
{
	if(var_lstDispatch.list.length == 1)
	{
		var errors=var_lstDispatch.list[0].errors_Validation;
		if(errors.list.length>0)
		{
			for (i = 0; i <  errors.list.length; i++)
			{
				var tblRow=document.getElementById('tabDispatch').insertRow();
			    tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				tblcol.colSpan = 3;
				tblcol.style.color="red";
				str=errors.list[i];
				CellContents = str;
				tblcol.innerHTML = CellContents;
			}
			return true;
		}
		else
		{
			return false;
		}
	}
	else
	{
		return false;
	}
}

</script>
</head>
<body>
		<html:form action="/dispatchAddress" method="post" styleId="dispatchFormId">
		<div class="head"> <span>Select Dispatch Address</span> </DIV>
		<bean:define id="formBean" name="dispatchAddressBean"></bean:define>
		<fieldset class="border1">
			<legend> <b>Dispatch Address List</b> </legend>
			<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" id="tabDispatch">
				<tr class="grayBg" >
					<td align="center" colspan="3" > 
						Search Dispatch Address
						<input type="text" name="searchDispatchAddress" id="searchDispatchAddress" class="inputBorder1" maxlength="200" onkeypress="if(event.keyCode=='13') return DrawDispatch()";>
						<html:hidden property="accountIdStr"/>

						<div class="searchBg" style="margin-right:10px;"><a href="#" onClick="DrawDispatch()">Search</a></div>
					</td>
				</tr>
				<tr class="grayBg">
					<td width="3%" align="center">Select</td>
					<td width="23%">Dispatch Address Code</td>
					<td width="71%">Dispatch Address</td>
				</tr>
				
			</table>
		</fieldset>
	</html:form>
</body>
<script type="text/javascript">
	DrawDispatch()
</script>
</html>