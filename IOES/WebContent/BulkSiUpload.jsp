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
<title>Bulk SI Upload Report</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
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
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	showLayer();
	myForm.submit();
}


function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	myform.toExcel.value='false';
	//myform.reset();
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.method.value='viewBulkSIUploadReport';	
	showLayer();
	myform.submit();
}

function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	myform.toExcel.value='false';
	//myform.reset();
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
	myform.method.value='viewBulkSIUploadReport';	
	showLayer();
	myform.submit();
}

//function popitup(url) 
//{
//
//	if(url=="SelectAccount")
//	{
//		var path = "<%=request.getContextPath()%>/bcpAddress.do?method=fetchAccount";		
//		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
//	}
//	if(url=="SelectStatus")
//	{
//
//		var path = "<%=request.getContextPath()%>/reportsAction.do?method=fetchStatus";		
//		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:330px");
//	}
//	return false;
//}

function searchClick()
{
	
	myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";

	
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewBulkSIUploadReport";
		showLayer();
		myForm.submit(); 
	
}

function exportToExcel()
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='true';
	
		myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewBulkSIUploadReport&inExcel=true";
		myForm.submit();
	
}

function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.date_of_installation.value='' ;	
	myForm.servicename.value='' ;	
	myForm.partyNo.value='' ;	
	myForm.orderNo.value='' ;
	myForm.logical_SI_No.value='' ;
	myForm.linename.value='' ;
	
}



//function fnViewOrder(orderNo)
//{
//      document.forms[0].action="./viewOrderAction.do?methodName=viewOrder&orderNo="+orderNo;
//      document.forms[0].submit();
//}

path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body >
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/reportsAction" styleId="searchForm" method="post">
		<bean:define id="formBean" name="reportsBean"></bean:define>
		<html:hidden property="toExcel"/>
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="pagingSorting.sortByOrder"/>
		<html:hidden property="pagingSorting.pageNumber"/>
		<input type="hidden" name="fromPageSubmit" value="1"/>
		<input type="hidden" name="method" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> <span>Bulk SI Upload Report</span> </div>
		<div border="1" class="error" align="center">
			<logic:present name="validation_errors">
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>
			</logic:present>
			<html:messages id="message" message="true">
				<li><font color="red"><bean:write name="message"/></font></li>
			</html:messages>
		</div>
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Search</b></legend>
				<table border="0"  align="center" style="margin-top:7px">
					<tr>
					
						<td width="10px"/>
						<td align="right" style="font-size:9px;">Date Of Installation:</td>
						<td nowrap><html:text  property="date_of_installation" styleClass="inputBorder1" style="width:75px;" readonly="true"></html:text>
						    <font size="1">
								<a href="javascript:show_calendar('forms[0].date_of_installation');" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td>
						
						<td width="10px"/>
						<td align="right" style="font-size:9px">Services:</td>
						<td align="left">
							<html:text  property="servicename" styleClass="inputBorder1" style="width:75px;float:left;" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Services')};"
							maxlength="18" ></html:text>
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px">Party No:</td>
						<td align="left">
							<html:text  property="partyNo" styleClass="inputBorder1" style="width:75px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};" 
							maxlength="18" ></html:text>
						</td>
						</tr>
						<tr>
						<td width="50px"/>
						<td align="right" style="font-size:9px"> Crm Order No:</td>
						<td align="left" nowrap>
							<html:text  property="orderNo" styleClass="inputBorder1" style="width:90px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
						</td>
						<td width="50px"/>
						<td align="right" style="font-size:9px"> Logical Id:</td>
						<td align="left" nowrap>
							<html:text  property="logical_SI_No" styleClass="inputBorder1" style="width:90px;float:left;" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"></html:text>
						</td>
						<td width="50px"/>
						<td align="right" style="font-size:9px">CRM Product Name:</td>
						<td align="left" nowrap>
							<html:text  property="linename" styleClass="inputBorder1" style="width:90px;float:left;" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'CRM Product Name')};"></html:text>
						</td>
						<td width="60px"/>
						<td align="center" valign="top">
							<div class="searchBg" style="margin-right:10px;"><a href="#" title="Search" onClick="searchClick();">Search</a></div>
						</td>
						<td width="1%"/>
						<td align="left" >
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
						</td>
						<logic:present name="BulkSIUploadReport" scope="request">
							<logic:notEmpty  name="BulkSIUploadReport" scope="request"> 
							<td align="left" width="20%"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a href="#" title="Export To Excel" onClick="javascript:exportToExcel();">Export To Excel</a></div></td>
							</logic:notEmpty>
							<logic:empty  name="BulkSIUploadReport" scope="request"> 		
							<td align="left"><div class="searchBg" style="margin-right:10px;font-size:11px;"><a style="cursor:default;">Export To Excel</a></div></td>
							</logic:empty>
						</logic:present>
					</tr>
				</table>
		</fieldset>
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>Bulk SI Upload Report Report</b></legend>
			<br/>
			<table width="100%">
				<tr>
					<td>
						<%	String currPageNumber =String.valueOf(((ReportsBean)formBean).getPagingSorting().getPageNumber());
							String 	maxPageNumber =String.valueOf(((ReportsBean)formBean).getPagingSorting().getMaxPageNumber());
						%>
						<jsp:include flush="true" page="../paging.jsp">
							<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
							<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
						</jsp:include>
					</td>
				</tr>
			</table>
			
			<div class="scrollWithAutoScroll1" class="head"  style="height:325px;width:99%;" >
			<table style="display:block;overflow:auto;" width="99%" border="1" align="center" class="tab2">
				<tr>
					
					<td align="center" style="font-size:9px"  ><b>Accoun Name </b></td>
					<td align="center" style="font-size:9px"  ><b> Crm Order Mocn No</b></td>
					<td align="center" style="font-size:9px"  ><b>Crm Service Opms Id</b></td>
					<td align="center" style="font-size:9px"  ><b>Services </b></td>
					<td align="center" style="font-size:9px"  ><b>Installation Address A1</b></td>
					<td align="center" style="font-size:9px"  ><b>Installation Address A2 </b></td>
					<td align="center" style="font-size:9px"  ><b>Installation Address A3</b></td>
					<td align="center" style="font-size:9px"  ><b>From City </b></td>
					<td align="center" style="font-size:9px"  ><b>From State</b></td>
					<td align="center" style="font-size:9px"  ><b>From Country </b></td>
					<td align="center" style="font-size:9px"  ><b>Installation Address B1</b></td>
					<td align="center" style="font-size:9px"  ><b>Installation Address B2 </b></td>
					<td align="center" style="font-size:9px"  ><b>Installation Address B3</b></td>
					<td align="center" style="font-size:9px"  ><b> To City</b></td>
					<td align="center" style="font-size:9px"  ><b>To State</b></td>
					<td align="center" style="font-size:9px"  ><b>To Country </b></td>
					<td align="center" style="font-size:9px"  ><b>Date Of Installation</b></td>
					<td align="center" style="font-size:9px"  ><b>Date Of Activation </b></td>
					<td align="center" style="font-size:9px"  ><b>Bandwidth Value</b></td>
					<td align="center" style="font-size:9px"  ><b>UOM </b></td>
					<td align="center" style="font-size:9px"  ><b>Parent Product</b></td>
					<td align="center" style="font-size:9px"  ><b>Parent Circuit </b></td>
					<td align="center" style="font-size:9px"  ><b>LOB</b></td>
					<td align="center" style="font-size:9px"  ><b>Circle </b></td>
					<td align="center" style="font-size:9px"  ><b>Zone</b></td>
					<td align="center" style="font-size:9px"  ><b>Support Location A </b></td>
					<td align="center" style="font-size:9px"  ><b>Support Location B</b></td>
					<td align="center" style="font-size:9px"  ><b>Commited SLA </b></td>
					<td align="center" style="font-size:9px"  ><b>HUB Location</b></td>
					<td align="center" style="font-size:9px"  ><b>Platform </b></td>
					<td align="center" style="font-size:9px"  ><b> 	
					<logic:present name="BulkSIUploadReport" scope="request">
							<logic:notEmpty  name="BulkSIUploadReport" scope="request"> 
								<a href="#" onclick="sortSubmit('CUSTOMER_LOGICAL_SI_NO')">Logical SI No</a>
							</logic:notEmpty>
							<logic:empty  name="BulkSIUploadReport" scope="request">
								Logical SI No
							</logic:empty>
						</logic:present>
						</b></td>
					<td align="center" style="font-size:9px"  ><b>SI Id </b></td>
					<td align="center" style="font-size:9px"  ><b>IPLC</b></td>
					<td align="center" style="font-size:9px"  ><b>Managed Yes No</b></td>
					<td align="center" style="font-size:9px"  ><b>Act Mgr</b></td>
					<td align="center" style="font-size:9px"  ><b>Prj Mgr</b></td>
					<td align="center" style="font-size:9px"  ><b>TL</b></td>
					<td align="center" style="font-size:9px"  ><b>Service Provider</b></td>
				
				</tr>
				<logic:present name="BulkSIUploadReport" scope="request">
					<logic:notEmpty  name="BulkSIUploadReport" scope="request"> 					
						<logic:iterate id="row" name="BulkSIUploadReport" indexId="recordId">
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
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="accountName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="orderNumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="crm_service_opms_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="serviceName"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="installation_addressaa1"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="installation_addressaa2"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="installation_addressaa3"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="from_city"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="from_state"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="from_country"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="installation_addressab1"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="installation_addressab2"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="installation_addressab3"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="to_city"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="to_state"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="to_country"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="date_of_inst"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="date_of_act"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="bandwaidth"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="uom"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="parent_name"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="parent_circuit"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="lob"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="circle"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="zone"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_from"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="location_to"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="commited_sla"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="hub_location"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="platform"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="customer_logicalSINumber"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="si_id"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="ipls"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="managed_yes_no"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="actmngname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="prjmngname"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="tl"/></td>
						<td align="left" style="font-size:9px" width="14%"><bean:write  name="row" property="service_provider"/></td>
			

						



					</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty  name="BulkSIUploadReport">
					     <% String fromPageSubmit=request.getParameter("fromPageSubmit");
							if(("1").equalsIgnoreCase(fromPageSubmit)){%>
					   <tr align="center" >
						 <td colspan="17" align="center"><B><font color="red">No Records Found</font></B></td>
					   </tr>
						<%}%>
					</logic:empty>	
				</logic:present>				
			</table>
			</div>
		</fieldset>
	</html:form>
</body>
</html>
