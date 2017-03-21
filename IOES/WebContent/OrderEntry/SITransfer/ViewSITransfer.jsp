<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.beans.SITransferBean"%>
<%@page import="com.ibm.ioes.beans.DefaultBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.ibm.ioes.forms.SITransferDto"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.utilities.AppUtility"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>






<%@page import="com.ibm.icu.util.Calendar"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">

<script language="JavaScript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/scw.js"></script>
<script type="text/javascript" src="npd/js/timepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<script type="text/javascript" src="npd/js/inputColor.js"></script>
<script language="javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>

<title>View SI Transfer</title>
<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>
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



<script  language="javascript"  type="text/javascript" >


function goToHome()
{
	var myForm=document.getElementById('searchForm');
	//myForm.toExcel.value='false';
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	showLayer();
	myForm.submit();
}



function fnViewSITransferPage(BatchId)

{




      document.forms[0].action="./siTransfer.do?method=ViewSITransferPage&BatchId="+BatchId;
      showLayer();
      document.forms[0].submit();
}


function checkForm()
{
	return true;
}

function searchSubmit()
{
	var searchFlag=1;
	var myForm=document.getElementById('searchForm');
	
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";
	var fromDate=document.getElementById('searchfromDate').value;
	var toDate=document.getElementById('searchToDate').value;
	var batchid=document.getElementById('searchBatchId').value;
	
	if(batchid==""&&(fromDate=="" || toDate==""))
			  {
			  alert("please enter either BatchID or From Date and TO Date");
			  return false;
			     }
			     
			     
	if(document.getElementById('searchBatchId').value.length > 0)
		{
			if(checkdigits(document.getElementById('searchBatchId')))
			{
			searchFlag=1;
			}
			else
			{
			 searchFlag=0;
			}
		}
		
	if(searchFlag==1)
	{
		if(dateCompare(fromDate,toDate)==1)
				{			
					alert("To Date can't be less than From Date!!!");
					return false;
				} 
				
				
				
				
		else
		{
		myForm.action="<%=request.getContextPath()%>/siTransfer.do?method=ViewSITransferList";
		showLayer();
		myForm.submit(); 
		}
	}	
	
}

function pagingSubmit(val)
{

	var myform=document.getElementById('searchForm');
	
	//setFormBean(myform);
	//myform.reset();
	myform.elements["pagingSorting.pageNumber"].value=val;
	//myform.method.value='viewOrderList';	
	myform.action="<%=request.getContextPath()%>/siTransfer.do?method=ViewSITransferList";
	showLayer();
	myform.submit();
}





function clearFields()
{
	var myForm=document.getElementById('searchForm');
	myForm.accountIdStr.value='' ; 
	myForm.accountNameStr.value='' ;
	myForm.bcpIdStr.value='' ; 
	myForm.bcpNameStr.value='' ;	
}





</script>

</head>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body>

<div id="menu_abc">
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	
</div>
<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">
				<td><img src="gifs/top/home.gif" title="Home" onClick="goToHome('Home');"></img></td>
			</tr>
</table>
<div class="Head"><span>View SI Transfer</span></div>
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="newProduct">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="tab2">
			<tr>
				<!--<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Finalize New Project Plans</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>-->
				<td align="right" style="padding-right:10px;">
					<a href="#"><img onclick="searchSubmit();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;
				</td>
					
			</tr>
		</table>
		</td>
	</tr>
</table>

			<%String []colors=new String[]{"normal","lightBg"}; %>
	<html:form action="/siTransfer" method="post" styleId="searchForm">
	<bean:define id="formBean" name="siTransferBean"></bean:define>
	<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
	<html:hidden name="formBean" property="pagingSorting.pageNumber"/>

	<html:hidden name="formBean" property="pagingSorting.sortByColumn"/>
	<html:hidden name="formBean" property="pagingSorting.sortByOrder"/>	
	
	

	<div style="height:370px;width:100%">
	<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Search</b></legend>
	
						<table cellSpacing="0" cellPadding="0" width="100%" border="0" id="tbl1" class="tab2" align="center">
						<tr class="lightBgWihtoutHover">
						   	   <td width="5%" align="center">Batch Id</td>
								<td width="5%" align="center">From Date</td>
								<td width="5%" align="center">To Date</td>
							
						   	
													
						</tr>
						<tr class="normal">
						
							<td width="5%" align="center">
								<html:text property="searchBatchId" maxlength="8" size="15" styleClass="inputBorder1" styleId="id_orderno_search" onblur="if(this.value.length > 0){return checkdigits(this)}" onkeydown="if (event.keyCode == 13) return searchSubmit();" />
						</td>
							
								
										
							<td width="5%" align="center">
						 	 
						 	 	
						 	 	
						 
						<html:text property="searchfromDate" size="15" readonly="true" styleId="datefrom" styleClass="inputBorder1"></html:text>
								<font size="1">
											<a href="javascript:show_calendar(searchForm.searchfromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
									</font>
					
						</td>
						
						<td width="5%" align="center">
						
						 
						<html:text property="searchToDate" size="15" readonly="true" styleId="dateTo" styleClass="inputBorder1"></html:text>
								<font size="1">
											<a href="javascript:show_calendar(searchForm.searchToDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
									</font>
	
						</td>
						
						
						
							
				
							
							
							
						</tr>
					</table>
		</fieldset>		
		
		<fieldset border="1" align="center" class="border2" >
			<legend> <b>BCP Details</b></legend>
			<br/>
			<table width="100%">
				<tr>
					<td>
						<%	String currPageNumber =String.valueOf(((SITransferBean)formBean).getPagingSorting().getPageNumber());
							String 	maxPageNumber =String.valueOf(((SITransferBean)formBean).getPagingSorting().getMaxPageNumber());
						%>
						<jsp:include flush="true" page="/paging.jsp">
							<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
							<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
						</jsp:include>
					</td>
				</tr>
			</table>
			<div class="scrollWithAutoScroll1" class="head"  style="height:350px;width:99% " >
			<table class="tab2" style="overflow:auto;" width="99%" border="1" align="center" id='tblSITransferDetails'>
				<tr>
					<td align="center" style="font-size:9px"><b>Batch Id</b></td>
					<td align="center" style="font-size:9px"><b>Transfer Date</b></td>
					<td align="center" style="font-size:9px"><b>Progress</b></td>
									</tr>
				<logic:present name="SITransferList" scope="request">
					<logic:notEmpty  name="SITransferList" scope="request"> 					
						<logic:iterate id="row" name="SITransferList" indexId="recordId">
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
								<td align="left" style="font-size:9px">
									<a href="javascript:fnViewSITransferPage('<bean:write name="row" property="searchBatchId" />');">
									<font color="Brown">   
								<bean:write  name="row" property="searchBatchId"/>
								</font>
								</a>
									
								</td>
							
								<td align="left" style="font-size:9px"><bean:write  name="row" property="transferdate"/></td>
								<td align="left" style="font-size:9px"><bean:write  name="row" property="progress"/></td>
								
							</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty  name="SITransferList">
					   <tr align="center" >
						 <td colspan="17" align="center"><B><font color="red">No Records Found</font></B></td>
					   </tr>
					</logic:empty>	
				</logic:present>				
			</table>
			</div>
		</fieldset>
	</html:form>
</body>
</html:html>
