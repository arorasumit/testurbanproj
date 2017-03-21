<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Select Location</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="js/utility.js"></script>
<script language="javascript" type="text/javascript">
function getInfoAndUpdate(locID,locName) 
{
	var callerWindowObj = dialogArguments;
	callerWindowObj.document.searchForm.locationIdStr.value = locID;
	callerWindowObj.document.searchForm.locationNameStr.value = unescape(locName);

	window.close();
}

function checkValidate()
{
	if(ValidateTextField(document.getElementById("searchLocation"),'<%=request.getContextPath()%>',"Search Field-Location Name")==false)
	{
		return false;
	}
	
}

function DrawLocation()
{
	   var str;
	   var frm=document.getElementById('locationFormId');
	   
	   if(checkValidate()==false)
	   {
	   		return;
	   }
	  
	   var mytable = document.getElementById('tabLocation');	
	   var iCountRows = mytable.rows.length;
  
	   for (i = 2; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(2);
	   }   
	  // alert(document.getElementById("accountIdStr").value);
	  	var callerWindowObj = dialogArguments;
	    var jsData =
			{
				searchAccount:callerWindowObj.document.searchForm.accountIdStr.value,
				searchLocation:document.getElementById('searchLocation').value
			};
				
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		  
	var lstLocation = jsonrpc.processes.getLocationDetails(jsData);
	
	if(showErrorsIfAny(lstLocation))
	{
		return;
	}
	
	 for (i = 0; i <  lstLocation.list.length; i++)
	 {
		var tblRow=document.getElementById('tabLocation').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";

		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+escape(lstLocation.list[i].locationId) + "','"+ escape(lstLocation.list[i].locationName) + "') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstLocation.list[i].locationId;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstLocation.list[i].locationName;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
	}	
	if(lstLocation.list.length == 0)
	{
	var tblRow=document.getElementById('tabLocation').insertRow();
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


function showErrorsIfAny(var_lstLocation)
{
	if(var_lstLocation.list.length == 1)
	{
		var errors=var_lstLocation.list[0].errors_Validation;
		if(errors.list.length>0)
		{
			for (i = 0; i <  errors.list.length; i++)
			{
				var tblRow=document.getElementById('tabLocation').insertRow();
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
	<html:form action="/customerLocation" method="post" styleId="locationFormId">
		<div class="head"> <span>Select Location</span> </DIV>
		<bean:define id="formBean" name="locationDetailBean"></bean:define>
		<fieldset class="border1">
			<legend> <b>Location List</b> </legend>
			<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" id="tabLocation">
				<tr class="grayBg" >
					<td align="center" colspan="3" > 
						Search a Location
						<input type="text" name="searchLocation" id="searchLocation" class="inputBorder1" maxlength="200" onkeypress="if(event.keyCode=='13') return DrawLocation()";/>
						<html:hidden property="accountIdStr" styleId="accountIdStr"/>

						<div class="searchBg" style="margin-right:10px;"><a href="#" onClick="DrawLocation()">Search</a></div>
					</td>
				</tr>
				<tr class="grayBg">
					<td width="3%" align="center">Select</td>
					<td width="23%">Location ID</td>
					<td width="71%">Location Name</td>
				</tr>
				
			</table>
		</fieldset>
	</html:form>
</body>
<script type="text/javascript">
	DrawLocation()
</script>
</html>

