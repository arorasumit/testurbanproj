<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Copy and Cancel Report</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<style type="text/css">
    .opaqueLayer
    {
        display:none;
        position:absolute;
        top:0px;
        left:0px;
        opacity:0.5;
        filter:alpha(opacity=50);
        background-color: #000000;
        z-Index:1000;
        text-align:center;
        vertical-align:middle;
        padding:100px;
    }
    
</style>
<script language="javascript" type="text/javascript">
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	//myForm.toExcel.value='false';
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	showLayer();
	myForm.submit();
}

var poNumber = <%=request.getParameter("poNumber")%>;	
var serviceId = <%=request.getParameter("serviceId")%>;	
var isNew = <%=request.getParameter("isNew")%>;	
function DrawReport()
{
	   var str;
	   var frm=document.getElementById('searchForm');
	   var mytable = document.getElementById('tabParty');	
	   var iCountRows = mytable.rows.length;
  
	   for (i = 2; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(2);
	   }   
		
		if(poNumber==null)
		{
			poNumber=0;
		}
		if(serviceId==null)
		{
			serviceId=0;
		}
		if(isNew==null)
		{
			isNew=2;
		}	
		var jsData =              			
	   	{
			poNumber:poNumber,
			serviceId:serviceId,
			isNewOrder:isNew
		};
	  			  
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	var lstParty = jsonrpc.processes.fetchCancelCopyReport(jsData);
	var counter = <%=request.getAttribute("count")%>;
	  
	 for (i = 0; i <  lstParty.list.length; i++)
	 {
		var tblRow=document.getElementById('tabParty').insertRow();
			
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=i+1;
		CellContents = str;
		tblcol.innerHTML = CellContents;	
			
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstParty.list[i].oldOrderNo;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstParty.list[i].newOrderNo;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstParty.list[i].rootOrderNo;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstParty.list[i].oldServiceNo;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstParty.list[i].newServiceNo;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstParty.list[i].createdBy;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstParty.list[i].createdDate;
		CellContents = str;
		tblcol.innerHTML = CellContents;		
	}	
	if(lstParty.list.length == 0)
	{
	var tblRow=document.getElementById('tabParty').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 8;
		str='NO RECORD FOUND'
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}	
		return false;
}
</script>
</head>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body>
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<html:form action="/reportsAction" styleId="searchForm" method="post">
		<fieldset class="border1">
			<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
				<tr align="left">
					<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
				</tr>
			</table>
			<legend> <b>Copy and Cancel Report</b> </legend>
			<table width="100%"  border="1" id="tabParty" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   	<tr class="grayBg">
				    <td width="5%" align="center"><S.No</td>
					<td width="10%" align="center">Old Order No</td>
					<td width="10%" align="center">New Order No</td>
					<td width="10%" align="center">Root Order No</td>
					<td width="10%" align="center">Old Service No</td>
					<td width="10%" align="center">New Service No</td>
					<td width="10%" align="center">Action Taken By</td>
					<td width="10%" align="center">Action Date</td>
				</tr>
			</table>
		</fieldset>
</html:form>
</body>
<script type="text/javascript">
DrawReport()
</script>
</html>


	
