<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Select Entity</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script language="javascript" type="text/javascript">
var path='<%=request.getContextPath()%>';
function getInfoAndUpdate(entityName,entityId) 
{

	var callerWindowObj = dialogArguments;
	  var count=(<%=request.getParameter("counter")%>);
	if(callerWindowObj.document.getElementById('entity'+count)==undefined)
		{
		
			callerWindowObj.document.getElementById('entity' +<%=request.getParameter("counter")%>).value = unescape(entityName);
			callerWindowObj.document.getElementById('entityId'+<%=request.getParameter("counter")%>).value = unescape(entityId);
		}
		else
		{
			callerWindowObj.document.getElementById('entity'+<%=request.getParameter("counter")%>).value = unescape(entityName);
			callerWindowObj.document.getElementById('entityId'+<%=request.getParameter("counter")%>).value = unescape(entityId);
			
		}
		
			window.close();
}

function DrawEntityType()
{
	   var frm=document.getElementById('entityFormId');
	   var entity=frm.entityName;
	   if(ValidateTextField(entity,path,'EntityName')==false)
	   {
	   		return false;
	   }
	   var str;

	   var mytable = document.getElementById('tabEntity');	
	   var iCountRows = mytable.rows.length;
  
	   for (i = 2; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(2);
	   }   
	   var jsData =
			{
				entityName:frm.entityName.value
			};
				  
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	var lstEntity = jsonrpc.processes.getEntity(jsData);
	 for (i = 0; i <  lstEntity.list.length; i++)
	 {
	   
		var tblRow=document.getElementById('tabEntity').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		
		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(lstEntity.list[i].entityName) +"','"+ escape(lstEntity.list[i].entityId) +"') />";
		
		CellContents = str;
		tblcol.innerHTML = CellContents;
	
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstEntity.list[i].entityCode;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=lstEntity.list[i].entityName;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
	}
	if(lstEntity.list.length == 0)
	{
	var tblRow=document.getElementById('tabEntity').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 2;
		str='NO RECORD FOUND'
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}	
	return false;	
}
</script>
</head>
<body>
<html:form action="/NewOrderAction" styleId="entityFormId" method="post">
		<fieldset class="border1">
			<legend> <b>Account List</b> </legend>
			<table width="100%"  border="1" id="tabEntity" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="grayBg">
					<td width="10%" align="center" colspan="4">
					<div style="float:left;"><strong>Input Entity Name:</strong><input type="text" name="entityName" class="inputBorder1" maxlength="100" onkeydown="if (event.keyCode == 13) return DrawEntityType();"></div>
					<div class="searchBg" onclick="DrawEntityType()"  style="margin-right:10px;"><a href="#">Go</a></div></td>
				</tr>
				<tr class="grayBg">
					<td width="2%" align="center">Select</td>
					<td width="8%" align="center">Entity Code</td>
					<td width="20%" align="center">Entity Name</td>
				</tr>
			</table>
		</fieldset>
</html:form>
</body>
<script type="text/javascript">
	DrawEntityType()
</script>
</html>