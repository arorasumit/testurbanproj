<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Select Contact Type</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script language="javascript" type="text/javascript">
var path='<%=request.getContextPath()%>';
function getInfoAndUpdate(cType,cTypeId) 
{
	var callerWindowObj = dialogArguments;
	var callerScreenName = '<%=request.getAttribute("hdnContactTypeScreen")%>';
	if (callerScreenName == 'REPORT')
	{
		callerWindowObj.document.getElementsByName('contactTypeStr')[0].value = unescape(cType);
	}
	else
	{
	    var count = (<%=request.getParameter("counter")%>);
	    
	 	callerWindowObj.document.getElementById("contactType"+count).value = unescape(cType);
		callerWindowObj.document.getElementById("contactTypeId"+count).value = unescape(cTypeId);
	}
	window.close();
	
}

function DrawContactType()
{
	   var str;
	   var frm=document.getElementById('contactTypes');
	   var contact=frm.contactTypeName;
	  /* if(ValidateTextField(contact,path,'Contact Name')==false)
	   {
	   		return false;
	   }*/
	   
	   var mytable = document.getElementById('tabcontactType');	
	   var iCountRows = mytable.rows.length;
  
	   for (i = 2; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(2);
	   }   
	   var jsData =
			{
				contactTypeName:document.getElementById('contactTypeName').value
			};
				  
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	var lstService = jsonrpc.processes.searchContactType(jsData);
	var counter = <%=request.getAttribute("count")%>;
	  
	 for (i = 0; i <  lstService.list.length; i++)
	 {
	    var contactTypeName=lstService.list[i].contactTypeName;
		var tblRow=document.getElementById('tabcontactType').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(lstService.list[i].contactTypeName) +"','"+ escape(lstService.list[i].contactTypeId) +"') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=lstService.list[i].contactTypeName;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
	}	
	if(lstService.list.length == 0)
	{
	var tblRow=document.getElementById('tabcontactType').insertRow();
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

function setValue()   //call this function on onload Sumit requirement
{	//return false;
	var count=(<%=request.getParameter("counter")%>);
	var callerWindowObj = dialogArguments;
	document.getElementById('contactTypeName').value=callerWindowObj.document.getElementById('contactType'+count).value;
}
function isBlankField()
{
	if(document.getElementById('contactTypeName').value==""){
		alert("Please enter search value");
		DrawContactType();
		document.getElementById('contactTypeName').focus();
		return false;
	}else{
		DrawContactType();
		return false;
	}
}
</script>
</head>
<body>
<html:form action="/NewOrderAction" styleId="contactTypes" method="post">
		<fieldset class="border1">
			<legend> <b>Contact Type List</b> </legend>
			<table width="100%"  border="1" id="tabcontactType" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="grayBg">
					<td width="10%" align="center" colspan="2">
						<div style="float: left;"><strong>Input Contact Type Name</strong><input type="text" name="contactTypeName" id="contactTypeName"  class="inputBorder1" maxlength="50" onkeydown="if (event.keyCode == 13) return isBlankField();"></div>
						<div class="searchBg" onclick="isBlankField()"  style="margin-right:10px;"><a href="#">Go</a></div>
					</td>
				</tr>
				<tr class="grayBg">
					<td width="3%" align="center">Select</td>
						<!-- <td width="17%">Contact TypeId</td>-->
					<td width="16%">Contact Type Name</td>
				</tr>
			</table>
		</fieldset>
</html:form>
</body>
<script type="text/javascript">
    setValue()
	DrawContactType()
</script>
</html>
