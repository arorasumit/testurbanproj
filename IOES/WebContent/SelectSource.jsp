<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Rohit Verma	03-Feb-11	CSR00-05422     Code Changed for QuoteNo value to 0 in case of CRM order  -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Select Source</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script language="javascript" type="text/javascript">
var path='<%=request.getContextPath()%>';
function getInfoAndUpdate(sourceName) 
{
	var callerWindowObj = dialogArguments;
	callerWindowObj.document.searchForm.sourceName.value = unescape(sourceName);
	if(sourceName=="CRMORDER")
	{
		//[001]	Start
		callerWindowObj.document.searchForm.txtquotesNo.value="";
		//[001]	End
		callerWindowObj.document.searchForm.quoteNo.readOnly=true;
	}
	else
	{
		//[001] Start
		//callerWindowObj.document.searchForm.quoteNo.value="";
		//[001] End
		callerWindowObj.document.searchForm.quoteNo.readOnly=false;
	}
	window.close();
}
function DrawSourceType()
{
       var frm=document.getElementById('sourceFormId');
       var source=frm.sourceName;
	   var str;
	   if(ValidateTextField(source,path,'SourceName')==false)
	   {
	   		return false;
	   }
	  
	   var mytable = document.getElementById('tabsourceType');	
	   var iCountRows = mytable.rows.length;
  
	   for (i = 2; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(2);
	   }   
	   var jsData =
			{
				sourceName:frm.sourceName.value
			};
				  
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	var lstSource = jsonrpc.processes.getSourceDetails(jsData);
	 
	 for (i = 0; i <  lstSource.list.length; i++)
	 {
	    var sourceName=lstSource.list[i].sourceName;
		var tblRow=document.getElementById('tabsourceType').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";

		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(lstSource.list[i].sourceName) +"') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstSource.list[i].sourceName;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
	}	
	if(lstSource.list.length == 0)
	{
	var tblRow=document.getElementById('tabsourceType').insertRow();
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
<body>
<html:form action="/NewOrderAction" styleId="sourceFormId" method="post">
		<fieldset class="border1">
			<legend> <b>Source List</b> </legend>
			<table width="100%"  border="1" id="tabsourceType" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="grayBg"> 
					<td width="10%" align="left" colspan="2">
						<div style="float:left;" ><strong>Input Source Name:</strong><input type="text" name="sourceName" class="inputBorder" maxlength="50" onkeydown="if (event.keyCode == 13) return DrawSourceType();"></div>
						<div class="searchBg" onclick="DrawSourceType()"  style="margin-right:10px;"><a href="#">Go</a></div>
					</td>
					
				</tr>
				<tr class="grayBg">
					<td width="3%" align="center">Select</td>
					<td width="17%" align="center">Source Name</td>
				</tr>
			</table>
		</fieldset>
</html:form>
</body>
<script type="text/javascript">
	DrawSourceType()
</script>
</html>
