<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="com.ibm.ioes.npd.hibernate.beans.MyToDoListDto"%>
<%@taglib uri="http://struts.apache.org/tags-html"  prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean"  prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic"	prefix="logic"%>
<%@page import="com.ibm.ioes.npd.utilities.AppConstants"%>
			<%@page import="com.ibm.ioes.npd.beans.PlrUploadingFormBean"%>
<html:html>
<head>
<title>MyToDo List</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link rel="stylesheet" type="text/css" href="gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">
<script type="text/javascript" src="js/chrome.js"></script>
<script language="javascript" src="js/calendar.js"></script>
<script type="text/javascript"	src="js/opaqueLayer.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>
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

<script type="text/javascript">

	function ValidateFields()
	{
		 myform=document.getElementById('searchForm');

 		if(CheckNumeric(myform.searchproject,"Project ID")==false)
			{
			return false;
			}

		if(ValidateTextField(myform.searchproject,'<%=request.getContextPath()%>',"Project Id")==false)
			{return false;
			}

		if(ValidateTextField(myform.searchProjectPlanName,'<%=request.getContextPath()%>',"Project Name")==false)
		     { return false;
		     }

		if(ValidateTextField(myform.searchLaunchPriority,'<%=request.getContextPath()%>',"Priority of Launch")==false)
			{return false;
			}
	}



function searchSubmit(falg)
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	if(ValidateFields()==false)
	 {
				return false;
	 }

	document.searchForm.action="<%=request.getContextPath()%>/PlrUploading.do?method=projectList";	
	showLayer();
	myform.submit();

}
function pagingSubmit(val)
{
		if(ValidateFields()==false)
		  {
				return false;
		  }

	myform=document.getElementById('searchForm');
	myform.pageNumber.value=val;
	document.searchForm.action="<%=request.getContextPath()%>/PlrUploading.do?method=projectList";
	showLayer();
	myform.submit();
}


function sortSubmit(sortBy)
{
		if(ValidateFields()==false)
		  {
				return false;
		  }

	myform=document.getElementById('searchForm');
	myform.pageNumber.value=1;
	if(myform.sortBy.value==sortBy)
	{
		if(myform.sortByOrder.value=="decrement")
		{
			myform.sortByOrder.value="increment";
		}
		else
		{
			myform.sortByOrder.value="decrement";
		}
	}
	else
	{
		myform.sortBy.value=sortBy;
		myform.sortByOrder.value="decrement";
	}
	document.searchForm.action="<%=request.getContextPath()%>/PlrUploading.do?method=projectList";
	showLayer();
	myform.submit();
}


function mapstakeholder(projectId)
{
		if(ValidateFields()==false)
		  {
				return false;
		  }
	projectId = encrypt(projectId,'<%=request.getContextPath()%>');
	var path = "<%=request.getContextPath()%>/PlrUploading.do?method=viewProjectList&projectId="+projectId;	
	window.open(path,'MapStakeHolder','scrollbars=yes,resizable=yes,width=600,height=400,left=280,top=250');
}

function uploadDocument(projectId,dayforplr)
{
		if(ValidateFields()==false)
		  {
				return false;
		  }
	//alert(dayforplr);
		if(dayforplr=="0")
		{
			alert('No PLR Pending For uploading!')
			return false;
		}
		else
		{
			projectId = encrypt(projectId,'<%=request.getContextPath()%>');
			var path = "<%=request.getContextPath()%>/PlrUploading.do?method=uploadPlr&projectId="+projectId;	
			window.open(path,'UploadDocument','scrollbars=yes,resizable=yes,width=600,height=400,left=280,top=250');
	}
}

function viewhistory(projectId)
{
		if(ValidateFields()==false)
		  {
				return false;
		  }
    projectId = encrypt(projectId,'<%=request.getContextPath()%>');
	var path = "<%=request.getContextPath()%>/PlrUploading.do?method=viewhistory&projectId="+projectId;	
	window.open(path,'ViewHistory','scrollbars=yes,resizable=yes,width=1000,height=600,left=80,top=250');
	
}



</script>
</head>
<body>
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
    <br /><br /><br />
</div>

<html:form action="/PlrUploading" method="post" styleId="searchForm"
	enctype="multipart/form-data">
	<html:hidden property="sortBy" />
	<html:hidden property="sortByOrder" />
	<html:hidden property="pageNumber" />
	<html:hidden property="projectId" />
	<html:hidden property="taskId" />
	<html:hidden property="stageId" />


	<input type="hidden" name="method" value="projectList" />


	<table border="0" cellpadding="0" cellspacing="0" class="border"
		align="center" >
		<tr valign="middle" id="projectPlan">
			<td align="center" valign="bottom">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr>
					<td width="0" height="19"><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" height="19"
						width="26%">PLR Uploading</td>
					<td height="19" width="0"><img src="npd/Images/tabRight.gif"></td>
					<td align="left" height="19" width="772"><font size="1"></td>
					<td align="right" style="padding-right:10px;" width="166" height="19">
						<a href="#"><img onclick="searchSubmit();" src="npd/Images/search.gif" title="search" height="15"></a>
					</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<logic:present name="validation_errors">
	<table class="tabledata" align="center"><tr><td>
	<div class="error" align="center">

		<logic:iterate id="error_row" name="validation_errors" scope="request">
				<font color="red"><bean:write name="error_row" /></font><BR>
		</logic:iterate>

	</div></td></tr> </table>
	</logic:present>
					<bean:define id="pagingBean" name="plrUploading"></bean:define>
					<table width="100%" border="0" align="center" cellpadding="0"
						cellspacing="0" class="tabledata">
						<tr class="rptHeader">
							<td align="left" style="color:#FFFFFF"><bean:define
								id="currPageNumber" name="pagingBean" property="pageNumber" /> <bean:define
								id="maxPageNumber" name="pagingBean" property="maxPageNumber" />
							<logic:equal name="currPageNumber" value="1">FIRST</logic:equal>
							<logic:notEqual name="currPageNumber" value="1">
								<a href="#" onclick="pagingSubmit('1')" style="color:#FFFFFF">FIRST</a>
							</logic:notEqual> &nbsp;|&nbsp; <%
 			if (Integer.parseInt((String) currPageNumber) < Integer
 			.parseInt((String) maxPageNumber)) {
 %>
							<a href="#"
								onclick="pagingSubmit('<%=Integer.parseInt((String)currPageNumber)+1%>')"
								style="color:#FFFFFF">NEXT </a> <%
 } else {
 %> NEXT <%
 }
 %>
							</td>
							<td align="center" style="color:#FFFFFF">Page <bean:write
								name="currPageNumber" /> of <bean:write name="maxPageNumber" /></td>
							<td align="right" style="color:#FFFFFF"><logic:equal
								name="currPageNumber" value="1">PREV</logic:equal> <logic:notEqual
								name="currPageNumber" value="1">
								<a href="#"
									onclick="pagingSubmit('<%=Integer.parseInt((String)currPageNumber)-1%>')"
									style="color:#FFFFFF">PREV </a>
							</logic:notEqual> &nbsp;|&nbsp; <%
 			if (Integer.parseInt((String) currPageNumber) < Integer
 			.parseInt((String) maxPageNumber)) {
 %>
							<a href="#" onclick="pagingSubmit('<%=maxPageNumber %>')"
								style="color:#FFFFFF">LAST</a> <%
 } else {
 %> LAST <%
 }
 %>
							</td>
						</tr>
	</table>
				<div style="overflow:scroll;height:320px;width:100%"
						class="scroll">
					<table cellSpacing="1" width="100%" border="0"
						class="tabledata" id="TABLE1">
						<tr class="rptHeader">
							<td align="center" >SNo.</td>
							<td align="center" nowrap="nowrap"><a href="#"
								 onclick="sortSubmit('projectId')">Project
							Id</a></td>
							<td align="center" nowrap="nowrap"><a href="#"
								onclick="sortSubmit('projectName')">Project
							Name</a></td>
							<td align="center" nowrap="nowrap"><a href="#"
								onclick="sortSubmit('priorityOfLaunch')">Priority
							Of<br>
							Launch</a></td>
							<td align="center" nowrap="nowrap"><a href="#"
								onclick="sortSubmit('productBrief')">Product
							Brief</a></td>
							<td align="center" nowrap="nowrap"><a href="#"
								onclick="sortSubmit('targetMarket')">Target
							Market</a></td>
							<td align="center" nowrap="nowrap"><a href="#"
								onclick="sortSubmit('productCategory')">Product
							Category</a></td>
							<td align="center" nowrap="nowrap"><a href="#"
								onclick="sortSubmit('npdCategory')">NPD
							Category</a></td>
							<td align="center" nowrap="nowrap"><a href="#"
								onclick="sortSubmit('airtelPotential')">Airtel<br>
							Potential</a></td>
							<td align="center" nowrap="nowrap"><a href="#"
								onclick="sortSubmit('totalMarketPotential')">Total Market<br>
							Potential</a></td>
							<td align="center" nowrap="nowrap"><a href="#"
								onclick="sortSubmit('capexRequirement')">Capex
							<br>
							Requirement</a></td>
							<logic:equal name="plrUploading" property ="roleid" value="<%=AppConstants.ADMIN_USERID %>">
								<td align="center" nowrap="nowrap" width="132">Map
								StakeHolder</td>
							</logic:equal>

							<td align="center" nowrap="nowrap" >Upload
							Document</td>
							<td align="center" nowrap="nowrap" >View History</td>
						</tr>

						<tr class="rptHeader">
							<td align="center"></td>
							<td align="center"><html:text size="6" property="searchproject" maxlength="10"/></td>
							<td align="center"><html:text
								property="searchProjectPlanName" maxlength="100"/></td>
							<td align="center"><html:text
								property="searchLaunchPriority" maxlength="25"/></td>
							<td align="center"></td>
							<td align="center"></td>
							<td align="center"></td>
							<td align="center" nowrap="nowrap"></td>
							<td align="center" nowrap="nowrap"></td>
							<td align="center" nowrap="nowrap"></td>
							<td align="center"></td>
							<logic:equal name="plrUploading" property ="roleid" value="<%=AppConstants.ADMIN_USERID %>">
								<td align="center" nowrap="nowrap" width="132"></td>
							</logic:equal>
							<td align="center" nowrap="nowrap" width="132"></td>
							<td align="center" nowrap="nowrap" width="132"></td>
						</tr>
						<%
						int nSNo = 0;
//						int newsno=((PlrUploadingFormBean)pagingBean).getPagingSorting().getSerialNoStart();
						int newsno=1+(Integer.parseInt(((PlrUploadingFormBean)pagingBean).getPageNumber())-1)*
										(((PlrUploadingFormBean)pagingBean).getPageRecords());
						%>
						<logic:present name="accountList" scope="request">
							<logic:empty name="accountList">
								<tr class="alterRedBg" align="center" >
									<td colspan="15" align="center" nowrap><B>No Record
									Found</td>
								</tr>
							</logic:empty>
							<logic:notEmpty name="accountList">
								<logic:iterate id="row" name="accountList" indexId="recordId" type="com.ibm.ioes.npd.hibernate.beans.PlrUploadingDto">
									<%
												String classType = null;
												if (recordId.intValue() % 2 == 0) {
											classType = "redBg";
												} else {
											classType = "alterRedBg";
												}
												nSNo++;
									%>
									<tr class="<%=classType%>">
										<td align="center"><%=newsno%></td>
										<td align="center"><bean:write name="row"
											property="projectDetails.projectid" /></td>
										<td nowrap="nowrap"><bean:write  name="row"
											property="projectDetails.projectName" /></td>
										<td nowrap="nowrap"><bean:write name="row"
											property="projectDetails.launchPriority" /></td>
										<td align="center" nowrap="nowrap"><bean:write name="row"
											property="projectDetails.productBrief" /></td>
										<td align="center" nowrap="nowrap"><bean:write name="row"
											property="projectDetails.targetMkt" /></td>
										<td align="center" nowrap="nowrap"><bean:write name="row"
											property="projectDetails.prdCategory.prodcatdesc" /></td>
										<td align="center" nowrap="nowrap"><bean:write name="row"
											property="projectDetails.npdCategory.npdcatdesc" /></td>
										<td align="center" nowrap="nowrap"><bean:write name="row"
											property="projectDetails.airtelPotential" /></td>
										<td align="center" nowrap="nowrap"><bean:write name="row"
											property="projectDetails.totalMktPotential" /></td>
										<td align="center" nowrap="nowrap"><bean:write name="row"
											property="projectDetails.capexReq" /></td>
										<logic:equal name="plrUploading" property ="roleid" value="<%=AppConstants.ADMIN_USERID %>">
											<td align="center" nowrap="nowrap"><a href="#"
												onclick="mapstakeholder('<bean:write name="row" property="projectDetails.projectid"/>');" />
											Map Stakeholder</a></td>
										</logic:equal>
										<% String userid = String.valueOf(row.getIsupload());  %>
										<% String plrday = String.valueOf(row.getDayforplr()); %>
										<logic:equal name="plrUploading" property ="userId" value="<%=userid %>">
										<td align="center" nowrap="nowrap"><a href="#"
											onclick="uploadDocument('<bean:write name="row" property="projectDetails.projectid"/>','<bean:write name="row" property="dayforplr"/>');" />Upload
										PLR Document</a></td>
										</logic:equal>
										<logic:notEqual name="plrUploading" property ="userId" value="<%=userid %>">
										<td align="center" nowrap="nowrap">Not Mapped</td>
										</logic:notEqual>

										<td align="center" nowrap="nowrap"><a href="#"
											onclick="viewhistory('<bean:write name="row" property="projectDetails.projectid"/>');" />View
										Document History</a></td>

									</tr>
									<%newsno=newsno+1; %>
								</logic:iterate>
							</logic:notEmpty>
						</logic:present>
					</table>
					</div>
	
	
</html:form>
</div>
</body>
</html:html>
