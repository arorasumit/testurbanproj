<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.beans.PlrUploadingFormBean"%>
<html:html>
<head>
<link rel="stylesheet" type="text/css" href="gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">

<script language="JavaScript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/chrome.js"></script>

<script type="text/javascript" src="js/timepicker.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>
<script type="text/javascript" src="js/inputColor.js"></script>
<%@include file="/js/scwblank.html"%>


<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>


<script type="text/javascript">

function searchSubmit(flag)
{
	if(flag=='5')										// 5 -- Go Button Filter
	{
		if(document.forms[0].dateFilter.value=='0')
		{
			alert("Please Select Date Filter Type !!");
			return false;
		}
	}
	
	myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	/*if(checkForm()==false)
	{
		return ;
	}*/
	//showLayer();
	document.searchForm.action="<%=request.getContextPath()%>/PlrUploading.do?method=viewhistory";
	myform.submit();
}

function pagingSubmit(val)
{
	myform=document.getElementById('searchForm');
	myform.pageNumber.value=val;
	document.searchForm.action="<%=request.getContextPath()%>/PlrUploading.do?method=viewhistory";
	myform.submit();
}



function sortSubmit(sortBy)
{
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
	/*if(checkForm()==false)
	{
		return ;
	}	showLayer();
	*/
	document.searchForm.action="<%=request.getContextPath()%>/PlrUploading.do?method=viewhistory";
	myform.submit();
}

function isNumberKey(evt)
{
   var charCode = (evt.which) ? evt.which : event.keyCode
   if (charCode > 31 && (charCode < 48 || charCode > 57))
   {
      return false;
   }
   return true;
}


function validate()
{
	document.body.click();
}



</script>

</head>
<body onload="javascript:validate();">
<div id="menu_abc" style="display: block;">
<script type="text/javascript" src="js/scw.js"></script>
<html:form action="/PlrUploading" method="post"
	styleId="searchForm">

<html:hidden property="sortBy"/>
<html:hidden property="sortByOrder"/>
<html:hidden property="pageNumber" />
<html:hidden property="projectId"/>
<html:hidden property="taskId"/>
<html:hidden property="stageId"/>

	<input type="hidden" name="method" value="viewProjectList">
	<bean:define id="formBean" name="plrUploading"></bean:define>
	<html:hidden name="formBean" property="pagingSorting.sortByColumn" />
	<html:hidden name="formBean" property="pagingSorting.sortByOrder" />
	<html:hidden name="formBean" property="pagingSorting.pageNumber" />
	<html:hidden name="formBean" property="stakeholderidlist" />
	<html:hidden property="hiddenReturnFlag" />
	<html:hidden property="projectId"/>


	<html:hidden property="projectPlanId" />
	<table border="0" cellpadding="0" cellspacing="0" class="border"
		align="center" width="85%">
		<tr valign="middle" id="projectPlan">
			<td height="48" align="center" valign="bottom">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr>
					<td width="0" height="19"><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" height="19"
						width="26%">Select Stakeholder</td>
					<td height="19" width="0"><img src="npd/Images/tabRight.gif"></td>
					<td align="left" height="19" width="772"></td>
					<td align="right" style="padding-right:10px;" width="166"
						height="19"><a href="#"></a>&nbsp;<a
						href="#"></a></td>
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
	<table cellSpacing="0" cellPadding="0" width="85%" border="0" id="tbl1"
		class="tabledata" style="display:block" align="center">
		
			<tr>
				<td width="91%">
					<bean:define id="pagingBean" name="plrUploading"></bean:define>
					<table border="0" align="center" cellpadding="0" cellspacing="0" class="tabledata" width="100%">  
					<tr class="rptHeader">
						<td width="51%" align="left" style="color:#FFFFFF">
							<bean:define id="currPageNumber" name="pagingBean" property="pageNumber"/>
							<bean:define id="maxPageNumber" name="pagingBean" property="maxPageNumber"/>
							<logic:equal name="currPageNumber" value="1">FIRST</logic:equal>
							<logic:notEqual name="currPageNumber" value="1">
								<a href="#" onclick="pagingSubmit('1')" style="color:#FFFFFF">FIRST</a>
							</logic:notEqual>
							&nbsp;|&nbsp;						
							<%if(Integer.parseInt((String)currPageNumber)<Integer.parseInt((String)maxPageNumber)){ %>
								<a href="#" onclick="pagingSubmit('<%=Integer.parseInt((String)currPageNumber)+1%>')" style="color:#FFFFFF">NEXT </a>
							<%}else { %>
								NEXT
							<%}%>
						</td>
						<td width="16%" align="center" style="color:#FFFFFF">Page <bean:write name="currPageNumber"/> of <bean:write name="maxPageNumber"/></td>
						<td width="33%" align="right" style="color:#FFFFFF">
							<logic:equal name="currPageNumber" value="1">PREV</logic:equal>
							<logic:notEqual name="currPageNumber" value="1">
								<a href="#" onclick="pagingSubmit('<%=Integer.parseInt((String)currPageNumber)-1%>')" style="color:#FFFFFF">PREV </a>
							</logic:notEqual>
							&nbsp;|&nbsp;
							<%if(Integer.parseInt((String)currPageNumber)<Integer.parseInt((String)maxPageNumber)){ %>
								<a href="#" onclick="pagingSubmit('<%=maxPageNumber %>')" style="color:#FFFFFF">LAST</a>
							<%}else { %>
								LAST
							<%}%>
						</td>
					</tr>
					<tr >
					  <td colspan="2" align="left" >
					  Stake Holder: <html:select onchange="javascript:searchSubmit();"
								property="employeeId" style="width:250px">
								<logic:notEmpty name="formBean" property="lstemployee">
									<html:option value="All"> All </html:option>
									<html:optionsCollection property="lstemployee" label="employeelabel"
										value="employeevalue" />
								</logic:notEmpty>
							</html:select>
					  </td>
					  	<td>
						</td>
					  </tr>
					<tr >
					  <td colspan="3" align="left" >
					  <table cellSpacing="0" cellPadding="0" width="100%" border="0"
						id="tbl1" class="tabledata" style="display:block" align="center">
						<tr>
							<td colspan="6" align="left">
							</td>
						</tr>
						<tr>
							<td>
							<div style="width:100%;overflow:scroll" class="scroll">
							<table cellSpacing="1" cellPadding="4" border="0" width="100%"
								class="tabledata" id="TABLE1">
								<tr class="rptHeader">
									<td align="center" style="color:#FFFFFF">SNo.</td>
									<td align="center" nowrap="nowrap"><a href="#"
										style="color:#FFFFFF" onclick="sortSubmit('version')">Document
									Name</a></td>
									<td align="center" nowrap="nowrap"><a href="#"
										style="color:#FFFFFF" onclick="sortSubmit('version')">Created
									By</a></td>
									<td align="center" nowrap="nowrap"><a href="#"
										style="color:#FFFFFF" onclick="sortSubmit('version')">Created
									Date</a></td>
									<td align="center" nowrap="nowrap"><a href="#"
										style="color:#FFFFFF" onclick="sortSubmit('version')">Version</a></td>
								</tr>

								<tr class="rptHeader">

									<td align="center">&nbsp;</td>
									<td align="center"></td>
									<td align="center"></td>
									<td align="center"></td>
									<td align="center"></td>
								</tr>
								<%int nSNo=0; 
								int newsno=1+(Integer.parseInt(((PlrUploadingFormBean)pagingBean).getPageNumber())-1)*
										(((PlrUploadingFormBean)pagingBean).getPageRecords());
								%>
								<logic:present name="accountList" scope="request">
									<logic:empty name="accountList">
										<tr class="alterRedBg" align="center" height="16">
											<td colspan="6" align="center" nowrap><B>No Record
											Found</td>
										</tr>
									</logic:empty>
									<logic:notEmpty name="accountList">
										<logic:iterate id="row" name="accountList" indexId="recordId">
											<%
							String classType=null;
							if(recordId.intValue() % 2 == 0)
							{
							classType="redBg";
							}
							else
							{
							classType="alterRedBg";				
							}	
							nSNo++;					
						%>
											<tr class="<%=classType%>">
												<td align="center"><%=newsno%></td>
												<td><bean:write name="row" property="refDocName" /></td>
												<td><bean:write name="row" property="createdBy" /></td>
												<td><bean:write name="row" property="createdDate" /></td>
												<td><bean:write name="row" property="version" /></td>

											</tr>
											<%newsno=newsno+1; %>
										</logic:iterate>
									</logic:notEmpty>
								</logic:present>
							</table>
							</div>

							</td>
						</tr>

					</table>
					  </td>
					  </tr>
		<tr>
			<td>
			<table border="0" cellpadding="0" cellspacing="0" class="border"
				align="center" width="91%">
			</table>

			</td>
		</tr>
		<tr valign="bottom" align="center">
			<td align="right">
					
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
