<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
	<%@page import="com.ibm.ioes.npd.beans.RepositoryUploadBean"%>
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
<script type="text/javascript"><!--
function searchSubmit(flag)
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	if(flag=='5')										// 5 -- Go Button Filter
	{
		if(document.forms[0].dateFilter.value=='0')
		{
			alert("Please Select Date Filter Type !!");
			return false;
		}
	}
	
	if(CheckNumeric(myform.searchProjectID,"Project ID")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.projectName,'<%=request.getContextPath()%>',"Project Name")==false)
	{
		return false;
	}
	
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	document.searchForm.action="<%=request.getContextPath()%>/RepositoryUpload.do?method=getProjectList";
	showLayer();
	myform.submit();
}

function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.pageNumber"].value=val;

	if(CheckNumeric(myform.searchProjectID,"Project ID")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.projectName,'<%=request.getContextPath()%>',"Project Name")==false)
	{
		return false;
	}
	
	document.searchForm.action="<%=request.getContextPath()%>/RepositoryUpload.do?method=getProjectList";
	showLayer();
	myform.submit();
}

function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	if(CheckNumeric(myform.searchProjectID,"Project ID")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.projectName,'<%=request.getContextPath()%>',"Project Name")==false)
	{
		return false;
	}
	
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

	document.searchForm.action="<%=request.getContextPath()%>/RepositoryUpload.do?method=getProjectList";
	showLayer();
	myform.submit();
}

function viewClosedProjects()
{
	myform=document.getElementById('searchForm');	
	myform.elements["pagingSorting.pageNumber"].value=1;
	document.searchForm.action="<%=request.getContextPath()%>/RepositoryUpload.do?method=getProjectList";
	showLayer();
	document.searchForm.submit();
}

function addDocuments(projID,projName,workFlowID)
{
	myform=document.getElementById('searchForm');
	if(CheckNumeric(myform.searchProjectID,"Project ID")==false)
	{
		return false;
	}
	
	if(ValidateTextField(myform.projectName,'<%=request.getContextPath()%>',"Project Name")==false)
	{
		return false;
	}
	//document.repositoryUploadBean.action="<c:out value='${sessionScope.MenuContextPath}' />/RepositoryUpload.do?method=viewStage&projectID="+projID+"&projectName="+projName+"&workFlowID="+workFlowID;
	document.repositoryUploadBean.action="<c:out value='${sessionScope.MenuContextPath}' />/RepositoryUpload.do?method=viewStage";
	document.repositoryUploadBean.selectedProjID.value =projID
	document.repositoryUploadBean.selectedProjName.value =projName
	document.repositoryUploadBean.selectedWorkFlowID.value =workFlowID
	showLayer();
	document.repositoryUploadBean.submit();
}

function viewDocuments(projID,projName,workFlowID)
{
	projID = encrypt(projID,'<%=request.getContextPath()%>');
	projName = encrypt(projName,'<%=request.getContextPath()%>');
	windowURL="<%=request.getContextPath()%>/RepositoryUpload.do?method=viewProjectDocReport&projectID="+projID+"&projectName="+projName;
	windowDefault="resizable=yes;dialogWidth:700px;dialogHeight:400px;center:yes;resizable:yes;status:no;";
	var newwin=window.open(windowURL,"abc",windowDefault);
}
--></script>

</head>
<body onload="javascript:document.body.click();">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
    <br /><br /><br />
</div>
<html:form action="/RepositoryUpload" styleId="searchForm" method="post">
	<table width="80%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center" class="borderTab">
					<tr>
						<td><img src="npd/Images/tabLeft.gif"></td>
						<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Repository Upload</td>
						<td><img src="npd/Images/tabRight.gif"></td>
						<td width="70%"><img src="npd/Images/zero.gif" width="15" height="1"></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<logic:messagesPresent>
		<table width="50%" align="center">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><font color="red" face="Verdana" size="2"><html:messages
					id="message">
					<li><bean:write name="message" /></li>
				</html:messages></font></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
	</logic:messagesPresent>
	<logic:messagesPresent message="true">
		<table width="50%" align="center">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td><font color="red" face="Verdana" size="2"><html:messages
					id="message" message="true">
					<li><bean:write name="message" /></li>
				</html:messages></font></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
		</table>
	</logic:messagesPresent>
	<div class="error" align="center">
		<logic:present name="validation_errors">
			<logic:iterate id="error_row" name="validation_errors" scope="request">
					<font color="red"><bean:write name="error_row" /></font><BR>
			</logic:iterate>
		</logic:present>
	</div>
	<table width="80%" border="2" class="tabledata" cellpadding="0" cellspacing="0" align="center">
		<tr>
			<td width="30%" align="Center" valign="bottom" style="vertical-align: bottom"><strong>Select ChangeOrder Category</strong></td>
			<td width="70%" align="LEFT">
				<html:select property="npdCategoryFilter" style="width:140px" onchange="javascript:viewClosedProjects();">
					<html:option value="-1">Select ChangeOrder Category</html:option>
					<logic:notEmpty name="repositoryUploadBean" property="listNPDcategory">
						<html:optionsCollection property="listNPDcategory" label="npdCategoryName" value="npdCategoryID" />
					</logic:notEmpty>
				</html:select>
			</td>
		</tr>
	</table>

	<bean:define id="formBean" name="repositoryUploadBean"></bean:define>
	<html:hidden name="formBean" property="pagingSorting.sortByColumn" />
	<html:hidden name="formBean" property="pagingSorting.sortByOrder" />
	<html:hidden name="formBean" property="pagingSorting.pageNumber" />
	<html:hidden property="methodName" />
	<html:hidden property="selectedProjID" />
	<html:hidden property="selectedProjName" />
	<html:hidden property="selectedWorkFlowID" />
	
	<logic:present name="repositoryUploadBean" property="listProjects">
		<table width="80%" border="2" class="tabledata" cellpadding="0" cellspacing="0" align="center">
			<tr>
				<td colspan="15" class="tabledata">
					<bean:define id="pagingBean" name="formBean"></bean:define> 
					<%String currPageNumber = String.valueOf(((RepositoryUploadBean) pagingBean).getPagingSorting().getPageNumber());
				  	String maxPageNumber = String.valueOf(((RepositoryUploadBean) pagingBean).getPagingSorting().getMaxPageNumber());%> 
				  	<jsp:include flush="true" page="html-jsp/commons/paging.jsp">
					<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
					<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
					</jsp:include>
					<a href="#"><img onclick="searchSubmit();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;
				</td>
			</tr>
			<tr>
				<td>
					<div style="overflow:scroll;height:315px;width:100%" class="scroll">
						<table align="center" cellSpacing="1" cellPadding="4" border="0" class="scroll" id="tblRollList" width="100%">
							<tr valign="middle" class="rptHeader">	
								<td width="20%" background="#FF9255">Sno</td>
								<td width="20%" background="#FF9255"><a href="#" onclick="sortSubmit('projectID')">Project ID</td>
								<td width="30%" background="#FF9255"><a href="#" onclick="sortSubmit('projectName')">Project Name</td>
								<td width="15%" background="#FF9255">Add Documents</td>
								<td width="15%" background="#FF9255">View Documents</td>
							</tr>
							<tr bgcolor="#FF9255" class="rptHeader">
								<td background="#FF9255"></td>
								<td background="#FF9255"><html:text name="repositoryUploadBean" property="searchProjectID" maxlength="5" size="5"/></td>
								<td background="#FF9255"><html:text name="repositoryUploadBean" property="projectName" maxlength="20" /></td>
								<td background="#FF9255"></td>
								<td background="#FF9255"></td>
							</tr>
							<logic:notEmpty name="repositoryUploadBean" property="listProjects">
								<%int sno=0;
								int newsno=((RepositoryUploadBean)pagingBean).getPagingSorting().getSerialNoStart();
								%>
								<logic:iterate id="assumptionDetailList_id" name="repositoryUploadBean" property="listProjects" indexId="index1">
									<%sno=sno+1;%>
									<c:if test="${index1%2==0}">
										<tr class="redBg" align="center">
											<td width="20%"><%=newsno%></td>
											<td width="20%"><bean:write name='assumptionDetailList_id' property='projectID' /></td>
											<td width="30%"><bean:write name='assumptionDetailList_id' property='projectName' /></td>
											<td width="15%"><a href="#" onclick="addDocuments(<bean:write name='assumptionDetailList_id' property='projectID'/>,'<bean:write name='assumptionDetailList_id' property='projectName'/>',<bean:write name='assumptionDetailList_id' property='projectWorkflowID'/>);">Add Docs</a></td>
											<td width="15%"><a href="#" onclick="viewDocuments(<bean:write name='assumptionDetailList_id' property='projectID'/>,'<bean:write name='assumptionDetailList_id' property='projectName'/>',<bean:write name='assumptionDetailList_id' property='projectWorkflowID'/>);">View Docs</a></td>
										</tr>
									</c:if>
									<c:if test="${index1%2!=0}">
										<tr class="alterRedBg" align="center">
											<td width="20%"><%=newsno%></td>
											<td width="20%"><bean:write name='assumptionDetailList_id' property='projectID' /></td>
											<td width="30%"><bean:write name='assumptionDetailList_id' property='projectName' /></td>
											<td width="15%"><a href="#" onclick="addDocuments(<bean:write name='assumptionDetailList_id' property='projectID' />,'<bean:write name='assumptionDetailList_id' property='projectName'/>',<bean:write name='assumptionDetailList_id' property='projectWorkflowID'/>);">Add Docs</a></td>
											<td width="15%"><a href="#" onclick="viewDocuments(<bean:write name='assumptionDetailList_id' property='projectID'/>,'<bean:write name='assumptionDetailList_id' property='projectName'/>',<bean:write name='assumptionDetailList_id' property='projectWorkflowID'/>);">View Docs</a></td>
										</tr>
									</c:if>
									<%newsno=newsno+1;%>
								</logic:iterate>
							</logic:notEmpty>
							<logic:empty name="repositoryUploadBean" property="listProjects">
								<tr bgcolor="#FF9255">
									<td background="#FF9255" colspan="5" align="Center">NO Records Found</td>
								</tr>
							</logic:empty>
						</table>
					</div>
				</td>
			</tr>
		</table>
	</logic:present>
</html:form>
</div>
</BODY>
</html:html>
