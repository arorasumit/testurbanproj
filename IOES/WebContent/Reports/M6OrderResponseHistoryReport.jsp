<!-- [001] Gunjan Singla     added  jsonrpc populatereportusagedetails method-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>M6 Order Response History - Report</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="../js/utility.js"></script>
<script type="text/javascript" src="../js/jsonrpc.js"></script>
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

var counter = 1;

var userId = '<%=request.getAttribute("userId")%>';
var interfaceId = '<%=request.getParameter("interfaceId")%>';
var actionType;
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	showLayer();
	myForm.submit();
}

function checkForm()
{
	return true;
}


function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	myform.toExcel.value='false';
	setFormBean(myform);
	myform.reset();
	myform.elements["pagingSorting.pageNumber"].value=1;
	if(myform.elements["pagingSorting.sortByColumn"].value==sortBy)
	{
		if(myform.elements["pagingSorting.sortByOrder"].value=="decrement")
		{
			myform.elements["pagingSorting.sortByOrder"].value="increment";
		}
		else
		{
			myform.elements["pagingSorting.sortByOrder"].value="decrement";
		}
	}
	else
	{
		myform.elements["pagingSorting.sortByColumn"].value=sortBy;
		myform.elements["pagingSorting.sortByOrder"].value="decrement";
	}
	if(checkForm()==false)
	{
		return ;
	}

	myform.method.value='viewM6OrderList';	
	showLayer();
	myform.submit();
}


function popitup(url) 
{
	if(url=="SelectAccount")
	{
		var path = "<%=request.getContextPath()%>/bcpAddress.do?method=fetchAccount";		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
	}
	return false;
}







function exportToExcel(m6OrderNo)
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='true';
  
	var excelName= '<%= AppConstants.ACTION_EXCEL %>';
	var jsData =	{
		userId:userId,
		interfaceId:interfaceId,
		actionType:excelName
		
	};		
    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	jsonrpc.processes.populateReportUsageDetails(jsData);  
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewM6RspnsHistory&inExcel=true&m6OrderNo="+m6OrderNo;
		myForm.submit();
	
}




path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body>
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/reportsAction" styleId="searchForm" method="post">
		<bean:define id="formBean" name="reportsBean"></bean:define>
		<html:hidden property="toExcel" />
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="pagingSorting.sortByOrder"/>
		<html:hidden property="pagingSorting.pageNumber"/>
		<input type="hidden" name="method" />
		
		
			<div class="scrollWithAutoScroll1" class="head"  style="height:350px;width:99% " >
			<table class="tab2" style="overflow:auto;" width="99%" border="1" align="center" id='tblM6OrderRspnsHistoryReport'>
				<tr>
					<td align="center" style="font-size:9px"><b>OE Order No.</b></td>
					<td align="center" style="font-size:9px"><b>M6 Order No. </b></td>							
					<td align="center" style="font-size:9px"><b>Remarks</b></td>
						<td align="center" style="font-size:9px"><b>Date</b></td>
					
				</tr>
				
				
					<logic:present name="listReponseHistory" scope="request">
					<logic:notEmpty  name="listReponseHistory" scope="request"> 					
						<logic:iterate id="row" name="listReponseHistory" indexId="recordId">
							<%
								String classType=null;
								if(recordId.intValue() % 2 == 0)
								{
								classType="lightBg";
								}
								else
								{
								classType="normal";				
								}	
							%>				
							<tr class="<%=classType%>">
								<td align="left" style="font-size:9px"><bean:write name="row" property="orderNo"/></td>
								<td align="left" style="font-size:9px"><bean:write  name="row" property="m6OrderNo"/></td>
								<td align="left" style="font-size:9px"><bean:write  name="row" property="remarks"/></td>
							<td align="left" style="font-size:9px"><bean:write  name="row" property="createdDate"/></td>
								
							</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty  name="listReponseHistory">
					   <tr align="center" >
						 <td colspan="17" align="center"><B><font color="red">No Records Found</font></B></td>
					   </tr>
					</logic:empty>	
				</logic:present>
							
			</table>
			</div>
	<input type="hidden" name="hdnOrderNo" id="hdnOrderNo" />
	</html:form>
</body>
</html>
