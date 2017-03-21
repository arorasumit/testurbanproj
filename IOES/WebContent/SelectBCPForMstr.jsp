<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>SelectLocation</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="js/utility.js"></script>
<script language="javascript" type="text/javascript">
function getInfoAndUpdate(bcpId,bcpname) 
{
	var callerWindowObj = dialogArguments;
	callerWindowObj.document.searchForm.bcpIdStr.value = bcpId;
	callerWindowObj.document.searchForm.bcpNameStr.value = unescape(bcpname);

	window.close();
}

function bcpSearch()
{
	var myForm=document.getElementById('searchBCP');
	
	myForm.action="<%=request.getContextPath()%>/bcpAddress.do?method=fetchBCP";
	
	myForm.submit();
}

function checkValidate()
{
	if(ValidateTextField(document.getElementById("searchBCP"),'<%=request.getContextPath()%>',"Search Field-BCP Name")==false)
	{
		return false;
	}
	
}

function DrawBCP()
{
	   var str;
	   var frm=document.getElementById('bcpFormId');
	   
	   if(checkValidate()==false)
	   {
	   		return;
	   }
	  
	   var mytable = document.getElementById('tabBcp');	
	   var iCountRows = mytable.rows.length;
  
	   for (i = 2; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(2);
	   }   
	   var jsData =
			{
				searchBCP:frm.searchBCP.value,
				searchAccount:frm.accountIdStr.value
			};
	
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		  
	var lstBCP = jsonrpc.processes.getBcpDetails(jsData);
	
	if(showErrorsIfAny(lstBCP))
	{
		return;
	}
	
	 for (i = 0; i <  lstBCP.list.length; i++)
	 {
		var tblRow=document.getElementById('tabBcp').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";

		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+escape(lstBCP.list[i].BCPId) + "','"+ escape(lstBCP.list[i].firstname+" "+lstBCP.list[i].lastName) + "') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstBCP.list[i].BCPId;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstBCP.list[i].firstname+" "+lstBCP.list[i].lastName;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
	}	
	if(lstBCP.list.length == 0)
	{
	var tblRow=document.getElementById('tabBcp').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 3;
		str='NO RECORD FOUND'
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}
}

function showErrorsIfAny(var_lstBCP)
{
	if(var_lstBCP.list.length == 1)
	{
		var errors=var_lstBCP.list[0].errors_Validation;
		if(errors.list.length>0)
		{
			for (i = 0; i <  errors.list.length; i++)
			{
				var tblRow=document.getElementById('tabBcp').insertRow();
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
	<html:form action="/bcpAddress" method="post" styleId="bcpFormId">
		<div class="head"> <span>Select BCP</span> </DIV>
		<bean:define id="formBean" name="bcpAddressBean"></bean:define>
		<fieldset class="border1">
			<legend> <b>BCP List</b> </legend>
			<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" id="tabBcp">
				<tr class="grayBg" >
					<td align="center" colspan="3" > 
						Search a BCP
						<input type="text" name="searchBCP" class="inputBorder1" maxlength="200">
						<html:hidden property="accountIdStr"/>

						<div class="searchBg" style="margin-right:10px;"><a href="#" onClick="DrawBCP()">Search</a></div>
					</td>
				</tr>
				<tr class="grayBg">
					<td width="3%" align="center">Select</td>
					<td width="23%">BCP IDe</td>
					<td width="71%">BCP Name</td>
				</tr>
				
			</table>
		</fieldset>
	</html:form>
</body>
<script type="text/javascript">
	DrawBCP()
</script>
</html>
