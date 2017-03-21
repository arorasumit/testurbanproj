<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.beans.RfiBean"%>
<%@page import="com.ibm.ioes.npd.utilities.Messages"%>
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
<title>ChangeOrder Workflow</title>

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

<script type="text/javascript" src="js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<script type="text/javascript">

function raiseRfi(var1)
{	
   	var1 = encrypt(var1,'<%=request.getContextPath()%>');
	windowURL="./pendingRfi.do?method=providerfi&rfiId="+var1;
	windowDefault="resizable=yes;dialogWidth:700px;dialogHeight:400px;center:yes;resizable:yes;status:no;";
	window.open(windowURL,'Approve','scrollbars=yes,resizable=yes,width=500,height=400,left=280,top=250');
	//var newwin=window.showModalDialog(windowURL,self,windowDefault);

}

function searchSubmit()
{
	myform=document.getElementById('rfiBean');
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	/*if(checkForm()==false)
	{
		return ;
	}*/
	showLayer();
	myform.submit();
}

function pagingSubmit(val)
{
	myform=document.getElementById('rfiBean');
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.action="<c:out value='${sessionScope.MenuContextPath}' />/pendingRfi.do?method=viewrfi";
	showLayer();
	myform.submit();
}
function sortSubmit(sortBy)
{
	myform=document.getElementById('rfiBean');
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
	/*if(checkForm()==false)
	{
		return ;
	}*/
	showLayer();
	myform.submit();
}

function viewChart(projectid)
{
	da=new Date();
	var myForm=document.getElementById('searchForm');
	var_projectId=projectid
	windowDefault='<%=Messages.getMessageValue("windowDefault")%>';
	var chil=window.open("","Assign"+da.getTime(),windowDefault);
	var strF="<HTML><HEAD></HEAD><BODY><FORM name='childform' method='post' action='<c:out value='${sessionScope.MenuContextPath}' />/projectStatusReport.do'>";
	var str1="<input type='hidden' name='method' value='viewChart'>";	
	var str2="<input type='hidden' name='searchProjectid' value='"+var_projectId+"'>";		
	var strL="</FORM></BODY></HTML>";			

	var str=strF+str1+str2+strL;
	chil.document.write(str);
	var chilForm=chil.document.childform;
	chilForm.submit();
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
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="newProduct">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Pending
				RFI</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<br>

<html:form action="/pendingRfi" method="post" styleId="searchForm">
	<table width="100%" border="2" class="tabledata" cellpadding="0" cellspacing="0" align="center">
		<html:hidden property="pagingSorting.pageNumber" />
		<html:hidden property="pagingSorting.sortByColumn" />
		<html:hidden property="pagingSorting.sortByOrder" />
		<tr>
			<td colspan="15" class="tabledata"><bean:define id="pagingBean" name="rfiBean"></bean:define> 
			<%String currPageNumber = String.valueOf(((RfiBean) pagingBean).getPagingSorting().getPageNumber());
		      String maxPageNumber = String.valueOf(((RfiBean) pagingBean).getPagingSorting().getMaxPageNumber());%> 
		    	<jsp:include flush="true" page="html-jsp/commons/paging.jsp">
					<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
					<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
				</jsp:include>
			</td>
		</tr>
	</table>
	<div style="overflow:scroll;height:320px;width:100%" class="scroll">
	<table align="center" cellSpacing="1" cellPadding="4" border="0" class="tabledata" id="tblRollList" width="100%">	
		<tr valign="middle" class="rptHeader">
			<td>ProjectId</td>
			<td>ProjectName</td>
			<td>Priority of Launch</td>
			<td>Product Brief</td>
			<td>Target Market</td>
			<td>Product Category</td>
			<td>ChangeOrder Category</td>
			<td>Airtel Potential</td>
			<td>Total Market potential</td>
			<td>Capex Requirement</td>
			<td>Current stage</td>
			<td>Current Task</td>
			<td>No. of days RFI with current
			Owner</td>
			<td>Provide RFI</td>
			<td>View Charts</td>
		</tr>
		<logic:notEmpty name="rfiBean" property="projectDetailList">
			<logic:iterate id="projectDetailList_id" name="rfiBean"
				property="projectDetailList" indexId="index1">
				<c:if test="${index1%2==0}">
					<tr class="redBg">
						<td><bean:write name='projectDetailList_id'
							property='projectid' /></td>
						<td><bean:write name='projectDetailList_id'
							property='projectName' /></td>
						<td><bean:write name='projectDetailList_id'
							property='launchPriority' /></td>
						<td><bean:write name='projectDetailList_id'
							property='productBrief' /></td>
						<td><bean:write name='projectDetailList_id'
							property='targetMkt' /></td>
						<td><bean:write name='projectDetailList_id'
							property='npdCategory.npdcatdesc' /></td>
						<td><bean:write name='projectDetailList_id'
							property='productCatDesc' /></td>
						<td><bean:write name='projectDetailList_id'
							property='airtelPotential' /></td>
						<td><bean:write name='projectDetailList_id'
							property='totalMktPotential' /></td>
						<td><bean:write name='projectDetailList_id'
							property='capexReq' /></td>
						<td><bean:write name='projectDetailList_id'
							property='currentstatus' /></td>
						<td><bean:write name='projectDetailList_id'
							property='currentTask' /></td>
						<td><bean:write name='projectDetailList_id'
							property='daysWithCurrentUser' /></td>
						<td><a href="#"
							onclick="raiseRfi(<bean:write name='projectDetailList_id' property='rfiId' />);">Provide
						RFI</a></td>
						<td align="center"><a href="#" onclick="viewChart(<bean:write name="projectDetailList_id" property="projectid"/>);">View Charts</td>
					</tr>
				</c:if>
				<c:if test="${index1%2!=0}">
					<tr class="alterRedBg">
						<td><bean:write name='projectDetailList_id'
							property='projectid' /></td>
						<td><bean:write name='projectDetailList_id'
							property='projectName' /></td>
						<td><bean:write name='projectDetailList_id'
							property='launchPriority' /></td>
						<td><bean:write name='projectDetailList_id'
							property='productBrief' /></td>
						<td><bean:write name='projectDetailList_id'
							property='targetMkt' /></td>
						<td><bean:write name='projectDetailList_id'
							property='npdCategory.npdcatdesc' /></td>
						<td><bean:write name='projectDetailList_id'
							property='productCatDesc' /></td>
						<td><bean:write name='projectDetailList_id'
							property='airtelPotential' /></td>
						<td><bean:write name='projectDetailList_id'
							property='totalMktPotential' /></td>
						<td><bean:write name='projectDetailList_id'
							property='capexReq' /></td>
						<td><bean:write name='projectDetailList_id'
							property='currentstatus' /></td>
						<td><bean:write name='projectDetailList_id'
							property='currentTask' /></td>
						<td><bean:write name='projectDetailList_id'
							property='daysWithCurrentUser' /></td>
						<td><a href="#"
							onclick="raiseRfi(<bean:write name='projectDetailList_id' property='rfiId' />);">Provide
						RFI</a></td>
						<td align="center"><a href="#" onclick="viewChart(<bean:write name="projectDetailList_id" property="projectid"/>);">View Charts</td>
					</tr>
				</c:if>
			</logic:iterate>
		</logic:notEmpty>
		<logic:empty name="rfiBean" property="projectDetailList">
			<tr class="alterRedBg" align="center">
				<td colspan="15">NO RECORDS FOUND</td>
			</tr>
		</logic:empty>
	</table>
	</div>
</html:form>
</div>
</body>
</html:html>

