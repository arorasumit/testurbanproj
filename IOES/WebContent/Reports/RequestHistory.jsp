<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Rohit Verma	5-Mar-13	00-08440	Report for Hardware Cancel Line Item -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.ReportsBean"%>
<html>
<head>
<title>Hardware Cancel Line Item Report</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
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
function DrawHistoryTable()
{
	var requestID = <%=request.getParameter("requestID")%>;
   	var str;
   	var frm=document.getElementById('requesthistory');
   	var mytable = document.getElementById('tabHardwareLines');	
   	var iCountRows = mytable.rows.length;
 
   	for (i = 1; i <  iCountRows; i++)
   	{
       	mytable.deleteRow(1);
   	} 
	try
	{
		var jsData =
		{	
			srno:requestID
		};	
 
		jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		var requestHistoryList = jsonrpc.processes.getRequestHistory(jsData);
	}
	catch(e)
	{
		alert("Exception :"+e);
	}
	var iTotalLength=requestHistoryList.list.length;	
	var colors=new Array("normal","lightBg");
	 for (i = 0; i <  iTotalLength; i++)
	 {	
	 	var sNo=i+1;
	 	var colorValue=parseInt(i)%2;
		var tblRow=document.getElementById('tabHardwareLines').insertRow();
		tblRow.className=colors[colorValue];
		
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<label>" +sNo+ "</label>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(requestHistoryList.list[i].srno);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(requestHistoryList.list[i].lineno);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(requestHistoryList.list[i].orderNumber);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(requestHistoryList.list[i].m6OrderNumber);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(requestHistoryList.list[i].neworder_remarks);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(requestHistoryList.list[i].srDate);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
	}	
	if(requestHistoryList.list.length == 0)
	{
	var tblRow=document.getElementById('tabHardwareLines').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 8;
		str='No Records Found';
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}	
	return false;
}

function closePage()
{
	window.close();
}
path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload="DrawHistoryTable()">
 <div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div> 
	<html:form action="/reportsAction" styleId="searchForm" method="post">
		<bean:define id="formBean" name="reportsBean"></bean:define>
		<input type="hidden" name="method" />
		<div border="1" class="head"> <span>Request History</span> </div>				
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Request History</b></legend>			
			<div class="scrollWithAutoScroll1" class="head"  style="height:300px;width:99%; vertical-align: top" >
				<table style="display:block;overflow:auto vertical-align: top;" width="99%" border="1" align="center" class="tab2"  id="tabHardwareLines" >
					<tr class="grayBg">
						<td width="2%" align="center"><b>S.No.</b></td>
						<td width="7%" align="center"><b>Request No </b></td>
						<td width="7%" align="center"><b>Line Item No</b></td>
						<td width="7%" align="center"><b>Order No</b></td>
						<td width="7%" align="center"><b>M6 Order No</b></td>
						<td width="10%" align="center"><b>Action</b></td>
						<td width="8%" align="center"><b>Action Date</b></td>	
					</tr>
				</table>
			</div>
			<table border="0" cellspacing="0" cellpadding="0" align="left" width="100%" id='selectPOtable'>	
					<tr>
						<td colspan="7" align="center">
						    <input type="button" name="btnClose" style="font-style: normal;" value="Close" onClick="closePage()">
						</td>
					</tr>
				</table>
		</fieldset>
	</html:form>
</body>
</html>
