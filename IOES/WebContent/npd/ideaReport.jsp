<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page import="com.ibm.ioes.npd.beans.NewIdeaBean"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">

<script language="JavaScript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/scw.js"></script>
<script type="text/javascript" src="js/timepicker.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>
<script type="text/javascript" src="js/inputColor.js"></script>
<script language="javascript" src="js/utility.js"></script>
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

<script type="text/javascript" src="js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<title>ChangeOrder Workflow</title>

<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>

<script type="text/javascript">
function checkForm()
{
	myform=document.getElementById('searchForm');
	if(ValidateTextField(myform.searchName,'<%=request.getContextPath()%>',"Search Field- Name")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.searchOracleId,'<%=request.getContextPath()%>',"Search Field- Oracle Id")==false)
	{
		return false;
	}

	var frmDt=document.getElementById('fromDate').value;
	var toDt=document.getElementById('toDate').value;
	
	if(frmDt=='' && toDt!='')
	{
		alert('Please select From Date');
		return false
	}
	else if(frmDt!='' && toDt=='')
	{
		alert('Please select To Date');
		return false
	}
	else if(frmDt=='' && toDt=='')
	{
		alert('Please select From and To Date');
		return false
	}
	else if(!compareTwoDates(frmDt,toDt))
	{			
		alert("From date should not be greater than To date");
		return false;
	}
	return true;
}

function searchSubmit(flag)
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	
	
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	if(checkForm()==false)
	{
		return ;
	}
	
	document.searchForm.action="<%=request.getContextPath()%>/HaveAnIdeaReport.do?method=list";
	document.searchForm.exportExcel.value="";
	
	showLayer();
	myform.submit();
}

function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.pageNumber"].value=val;
	
	if(checkForm()==false)
	{
		return ;
	}
	
	document.searchForm.action="<%=request.getContextPath()%>/HaveAnIdeaReport.do?method=list";
	document.searchForm.exportExcel.value="";
	showLayer();
	myform.submit();
}

function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
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
	
	document.searchForm.action="<%=request.getContextPath()%>/HaveAnIdeaReport.do?method=list";
	document.searchForm.exportExcel.value="";
	showLayer();
	myform.submit();
}

function exportToExcel()
{
	myform=document.getElementById('searchForm');
	if(checkForm()==false)
	{
		return ;
	}
	
	document.searchForm.action="<%=request.getContextPath()%>/HaveAnIdeaReport.do?method=list";
	document.searchForm.exportExcel.value="exportExcel";
	document.searchForm.submit();
}
</script>

</head>
<body onload="javascript:document.body.click();">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
    <br /><br /><br />
</div>
<logic:present name="validation_errors">
	<table  class="tabledata" align="center">
		<tr>
			<td>
				<div class="error" align="center">
					<logic:iterate id="error_row" name="validation_errors" scope="request">
							<font color="red"><bean:write name="error_row" /></font><BR>
					</logic:iterate>
				</div>
			</td>
		</tr>
	</table>
</logic:present>
<html:form action="/HaveAnIdeaReport" styleId="searchForm" method="post">
	<html:hidden property="exportExcel" />
	<table width="100%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
				<table width="100%" cellspacing="0" cellpadding="0" align="center" class="borderTab">
					<tr>
						<td><img src="npd/Images/tabLeft.gif"></td>
						<td background="npd/Images/tabbg.gif" class="tabHeading" width="25%">Idea Report</td>
						<td><img src="npd/Images/tabRight.gif"></td>
						<td align="center" width="70%">
							<font size="1"> 
								Created Date : 
								From Date<html:text property="fromDate" size="10" readonly="true" styleId="dateFrom" styleClass="tabledata"/>
								<img src="npd/Images/cal.gif" width="16" height="16" border="0" alt="Pick a date" onclick="scwShow(scwID('fromDate'),event);">
								To Date<html:text property="toDate" size="10" readonly="true" styleId="dateTo" styleClass="tabledata"/>
								<font size="1">
								<img src="npd/Images/cal.gif" width="16" height="16" border="0" alt="Pick a date" onclick="scwShow(scwID('toDate'),event);"></font>
							</font> 	
							
						</td>
						<td width="4%"><img src="npd/Images/zero.gif" width="15" height="1"></td>
						<td align="right" style="padding-right:10px;" width="20%"><a href="#"><img onclick="searchSubmit();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;</td>
						<td align="right" style="padding-right:10px;" width="36%">
							<logic:present name="reportData" scope="request">
							<logic:notEmpty name="reportData" scope="request">
								<img id="excelImage" src="<%=request.getContextPath()%>/Images/excel.gif" border="0" onclick="javascript:exportToExcel();" style="visibility: visible" height="15"/>								
							</logic:notEmpty>
							</logic:present>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>

	<bean:define id="formBean" name="newIdeaBean"></bean:define>
	<html:hidden name="formBean" property="pagingSorting.sortByColumn"/>
	<html:hidden name="formBean" property="pagingSorting.sortByOrder"/>
	<html:hidden name="formBean" property="pagingSorting.pageNumber"/>
	<input type="hidden" name="method" value="list"/>
	
	<html:hidden property="reportID" value="7"/>
	<table width="100%" border="2" class="tabledata" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td width="70%">
				
					<table width="100%" border="1" class="tabledata" cellpadding="0" cellspacing="0">
							<tr>
								<td  class="tabledata" >
				  					<bean:define id="pagingBean" name="formBean"></bean:define>
									<%  String  currPageNumber =String.valueOf(((NewIdeaBean)pagingBean).getPagingSorting().getPageNumber());
										if("0".equals(currPageNumber) || "".equals(currPageNumber))currPageNumber="1";
										String  maxPageNumber =String.valueOf(((NewIdeaBean)pagingBean).getPagingSorting().getMaxPageNumber());
									%>
									<jsp:include flush="true" page="html-jsp/commons/paging.jsp">
										<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
										<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
									</jsp:include>
								</td>
							</tr>	
							<tr  >	
								<td>
								<div style="overflow:scroll;height:370px;width:992px" class="scroll">
								<table border="1" width="100%"  class="tabledata" cellSpacing="1" cellPadding="4" align="center">
									<tr class="rptHeader" bgcolor="#FF9255">
									<th background="#FF9255">S.No</th>
									<th background="#FF9255"><a href="#" onclick="sortSubmit('name')">Name</a></th>
									<th background="#FF9255"><a href="#" onclick="sortSubmit('oracleId')">Oracle Id</a></th>
									<th background="#FF9255"><a href="#" onclick="sortSubmit('email')">Email</a></th>
									<th background="#FF9255">Mobile</th>
									<th background="#FF9255"><a href="#" onclick="sortSubmit('function')">Function</a></th>
									<th background="#FF9255"><a href="#" onclick="sortSubmit('location')">Location</a></th>
									<th background="#FF9255"><a href="#" onclick="sortSubmit('submittedDate')">Submitted Date</a></th>
									<th background="#FF9255">Brief Description</th>
									<th background="#FF9255">Benefit to Customer</th>									
									<th background="#FF9255">Do Similar Product Exist </th>
									<th background="#FF9255">Organisation</th>								
									<th background="#FF9255">Country</th>								
									<th background="#FF9255">Product Description</th>								
									<th background="#FF9255">Market Size</th>								
									<th background="#FF9255">Industry Verticals Best Suited For</th>								
									<th background="#FF9255">USP of Idea</th>
									</tr>
									<tr bgcolor="#FF9255" class="rptHeader">	
										<th background="#FF9255">&nbsp;</th>
										<th background="#FF9255"><html:text property="searchName" style="width:100%" maxlength="50"/></th>
										<th background="#FF9255"><html:text property="searchOracleId" style="width:100%" maxlength="50"/></th>
										<th background="#FF9255">&nbsp;</th>
										<th background="#FF9255">&nbsp;</th>
										<th background="#FF9255">&nbsp;</th>
										<th background="#FF9255">&nbsp;</th>
										<th background="#FF9255">&nbsp;</th>
										<th background="#FF9255">&nbsp;</th>
										<th background="#FF9255">&nbsp;</th>									
										<th background="#FF9255">&nbsp;</th>
										<th background="#FF9255">&nbsp;</th>								
										<th background="#FF9255">&nbsp;</th>								
										<th background="#FF9255">&nbsp;</th>								
										<th background="#FF9255">&nbsp;</th>								
										<th background="#FF9255">&nbsp;</th>								
										<th background="#FF9255">&nbsp;</th>
									</tr>
									<%int sno;
									sno=0;
									int newsno=((NewIdeaBean)pagingBean).getPagingSorting().getSerialNoStart();
									%> 
									<logic:notEmpty name="reportData">	
									<%String []colors=new String[]{"redBg","alterRedBg"}; %>						
									<logic:iterate id="row"  name="reportData" indexId="index_report">
										<%sno=sno+1;%>
										
										<tr class="<%=colors[index_report.intValue()%2]%>" align="center">
											<td><%=newsno%></td>
											<td><bean:write name='row' property="nameGenerator" /></td>
											<td><bean:write name='row' property="hrms_employeenumber" /></td>
											<td><bean:write name='row' property="mailId" /></td>
											<td><bean:write name='row' property="phoneNo" /></td>
											<td><bean:write name='row' property="function" /></td>
											<td><bean:write name='row' property="location" /></td>
											<td><bean:write name='row' property="createdDateString" /></td>
											<td><bean:write name='row' property="briefDescShort" /></td>
											<td><bean:write name='row' property="benefitShort" /></td>
											<td><bean:write name='row' property="isSimilarProductExist" /></td>
											<td><bean:write name='row' property="organisation" /></td>
											<td><bean:write name='row' property="country" /></td>
											<td><bean:write name='row' property="prdDescriptionShort" /></td>
											<td><bean:write name='row' property="marketSize" /></td>
											<td><bean:write name='row' property="industryVerticalShort" /></td>
											<td><bean:write name='row' property="uspShort" /></td>									
										</tr>
									
									
									<%newsno=newsno+1;%>
									</logic:iterate>
								</logic:notEmpty>
								<logic:empty name="reportData">
									<tr class="alterRedBg" align="center">
										<td colspan="17">NO RECORDS FOUND</td>
									</tr>
								</logic:empty>
								</table>	
								</div>
								</td>
							</tr>
					</table>
			
			</td>
		</tr>
	</table>
	
</html:form>
</div>
</BODY>
</html:html>
