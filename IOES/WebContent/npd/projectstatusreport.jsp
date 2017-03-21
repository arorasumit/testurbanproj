<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.beans.ProjectStatusReportBean"%>
<html:html>
<head>
<link rel="stylesheet" type="text/css" href="gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">
<%@page import="com.ibm.ioes.npd.utilities.Messages"%>
<script language="JavaScript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/scw.js"></script>
<script type="text/javascript" src="js/timepicker.js"></script>
<script language="javascript" src="js/utility.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>
<script type="text/javascript" src="js/inputColor.js"></script>

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
// to check whether stage already exists in the database.

	function ValidateFields()
	{
		 myform=document.getElementById('searchForm');
		
		 if(ValidateTextField(myform.searchprojectid,'<%=request.getContextPath()%>',"Project Id")==false)
			return false;
		 if(CheckNumeric(myform.searchprojectid,'Project Id')==false)
			return false;
		 if(ValidateTextField(myform.searchProjectName,'<%=request.getContextPath()%>',"Project Name")==false)
			return false;
		 if(ValidateTextField(myform.searchNpdCategory,'<%=request.getContextPath()%>',"ChangeOrder Category")==false)
			return false;
		 if(ValidateTextField(myform.searchProductCatId,'<%=request.getContextPath()%>',"Product Category")==false)
			return false;

	}

function searchSubmit(flag)
{
	myform=document.getElementById('searchForm');
	setFormBean(myform);
	if(ValidateFields()==false)
	  {
			//return false;
	  }

	if(flag=='5')										// 5 -- Go Button Filter
	{
		if(document.forms[0].dateFilter.value=='0')
		{
			alert("Please Select Date Filter Type !!");
			return false;
		}
	}
	
	
	myform.elements["pagingSorting.sortByColumn"].value="";
	myform.elements["pagingSorting.sortByOrder"].value="";
	myform.elements["pagingSorting.pageNumber"].value=1;
	
	/*if(checkForm()==false)
	{
		return ;
	}*/
	
	showLayer();
	document.searchForm.action="<%=request.getContextPath()%>/projectStatusReport.do?method=viewrProjectStatusDetail";
	myform.submit();
}

function exportToExcel(projectid)
{
	if(ValidateFields()==false)
	  {
			//return false;
	  }

	document.searchForm.action="<%=request.getContextPath()%>/projectStatusReport.do?method=exportProjectStatus";
	document.searchForm.searchProjectid.value = projectid;
	//showLayer();
	document.searchForm.submit();
}
	function ExportToExcel(flag)
	{
		var nCount = document.forms[0].chk.length;
		var values="";
		var stages="";
		var projectId = "";
		var checkCount=0;
		var countValue=1;
		var searchprojectid = "";
		var checkCount = 0;
		//alert(nCount);
		
		
		if(ValidateFields()==false)
		  {
				//return false;
		  }
		
		if(nCount==undefined)
		{
		  if(document.forms[0].chk.checked==true)
		  {	
			checkCount = 1;
			
			values=document.searchForm("projectid1").value;
		  }	
		}	
		else
		{
	
				if (nCount>=0)
			{
				for(i=0;i<nCount;i++)
				{
					if(document.forms[0].chk[i].checked==true)
					{
					    
						if (values=="")
						{
							values = document.searchForm("projectid"+countValue).value;
						}
						else
						{			
							values = values + "," +  document.searchForm("projectid"+countValue).value;
						}
						checkCount++;
					}		
					countValue++;
				}
			}
		}
		
		if(checkCount<=0)
		{
			alert("Please Select a Project!!");
			return false;
		}
		
		document.searchForm.searchProjectid.value = values;
		//alert(values);
		document.searchForm.action="<%=request.getContextPath()%>/projectStatusReport.do?method=exportProjectStatus";		
		//showLayer();
		document.searchForm.submit();

}

function pagingSubmit(val)
{
	if(ValidateFields()==false)
	  {
			return false;
	  }

	myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.pageNumber"].value=val;
	document.searchForm.action="<%=request.getContextPath()%>/projectStatusReport.do?method=viewrProjectStatusDetail";
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
	//showLayer();
	document.searchForm.action="<%=request.getContextPath()%>/projectStatusReport.do?method=viewrProjectStatusDetail";
	showLayer();
	myform.submit();
}

function fnCheckAll()
{
	var nCount = document.forms[0].chk.length;
	var i;

	if(nCount==undefined)
	{
		if(document.getElementById("chkAll").checked==true)
		   document.forms[0].chk.checked=true;
		else
		   document.forms[0].chk.checked=false;
	}
	else
	{	
		if(document.getElementById("chkAll").checked==true)
		{		
			for(i=0;i<nCount;i++)
			{
				document.forms[0].chk[i].checked=true;
			}
		}
		else
		{
			for(i=0;i<nCount;i++)
			{
				document.forms[0].chk[i].checked=false;
			}	
		}
	}
}

function viewChart(projectid)
{
	if(ValidateFields()==false)
	{
		//return false;
	}
	
	//document.searchForm.action="<%=request.getContextPath()%>/projectStatusReport.do?method=viewChart";
	//document.searchForm.submit();
	document.searchForm.searchProjectid.value = projectid;
	da=new Date();
	var myForm=document.getElementById('searchForm');
	var_projectId=myForm.searchProjectid.value
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
<div class="error" align="center">
	<logic:present name="validation_errors">
		<logic:iterate id="error_row" name="validation_errors" scope="request">
				<font color="red"><bean:write name="error_row" /></font><BR>
		</logic:iterate>
	</logic:present>
</div>
<html:form action="/projectStatusReport" method="post" styleId="searchForm">
	<input type="hidden" name="method" value="viewrProjectStatusDetail">
<bean:define id="formBean" name="projectStatusReportBean"></bean:define>

<html:hidden name="formBean" property="pagingSorting.sortByColumn"/>
<html:hidden name="formBean" property="pagingSorting.sortByOrder"/>
<html:hidden name="formBean" property="pagingSorting.pageNumber"/>
<input type="hidden" name="method" value="viewProjectPlan"/>
<html:hidden name="formBean" property="searchProjectid"/>

			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr >
					<td width="5"><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" width="367">Project
					Status Report</td>
					<td width="9"><img src="npd/Images/tabRight.gif"></td><td align="left" width="703"><font size="1"> 
					</font></td>
					<td width="62" align="right" style="padding-right:10px;"><a href="#"><img
						onclick="searchSubmit();" src="npd/Images/search.gif" title="search"
						height="15"></a>&nbsp;
					
					<a
						href="#"><logic:equal value="1" name="projectStatusReportBean" property="checkRptData">
						<img onClick="ExportToExcel();"
						src="Images/excel.gif" title="Export To Excel" height="15">
						</logic:equal></a>
					</td>
				</tr>
			</table>
		<table cellSpacing="0" cellPadding="0" width="100%" border="0" id="tbl1" class="tabledata" style="display:block" align="center">
		 <tr>
			<td colspan="15" class="tabledata" >
				  <bean:define id="pagingBean" name="formBean"></bean:define>
					<%  String  currPageNumber = String.valueOf(((ProjectStatusReportBean)pagingBean).getPagingSorting().getPageNumber());
						String  maxPageNumber = String.valueOf(((ProjectStatusReportBean)pagingBean).getPagingSorting().getMaxPageNumber());
					%>
					<jsp:include flush="true" page="html-jsp/commons/paging.jsp">
						<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
						<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
					</jsp:include>
			</td>
		</tr>	
		</table>
			<div style="overflow:scroll;height:370px;width:100%" class="scroll">			 
			    <table align="center" cellSpacing="1" cellPadding="0" border="0" class="tabledata" id="tblRollList" width="100%">
				<tr valign="middle" class="rptHeader">
					<td align="left" nowrap>
					<input name="chkAll" id="chkAll"
						   value="0" onClick="javascript:fnCheckAll();" type="checkbox"
						   class="none">
					</td>
					<td align="left" nowrap>SNo.</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('projectid')">Project Id</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('projectName')">Project Name</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('npdCategory')">ChangeOrder Category</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('productCategory')">Product Category</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('airtelPotential')">Airtel Potential</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('totalMarketPotential')">Total Market Potential</td>
					<td align="left" nowrap><a href="#" onclick="sortSubmit('capexRequirement')">Capex Requirement</td>
					<td align="left" nowrap>No of Days in Project</td>
					<td align="left" nowrap>View Plan</td>
					<td align="left" nowrap>View Chart</td>
				</tr>
				<tr valign="middle" class="rptHeader">
					<td align="left" nowrap="nowrap">&nbsp;</td>
					<td align="left" nowrap="nowrap">&nbsp;</td>
					<td align="left" nowrap="nowrap"><html:text name="projectStatusReportBean" property="searchprojectid"/></td>
					<td align="left" nowrap="nowrap"><html:text name="projectStatusReportBean" property="searchProjectName"/></td>
					<td align="left" nowrap="nowrap"><html:text name="projectStatusReportBean" property="searchNpdCategory"/></td>
					<td align="left" nowrap="nowrap"><html:text name="projectStatusReportBean" property="searchProductCatId"/></td>
					<td align="left" nowrap>&nbsp;</td>
					<td align="left" nowrap>&nbsp;</td>
					<td align="left" nowrap>&nbsp;</td>
					<td align="left" nowrap>&nbsp;</td>
					<td align="left" nowrap>&nbsp;</td>
					<td align="left" nowrap>&nbsp;</td>
				</tr>				
				<%int nSNo=0;
				int newsno=((ProjectStatusReportBean)pagingBean).getPagingSorting().getSerialNoStart(); %>
				<logic:notEmpty name="listProjectPlan">
				<logic:present name="listProjectPlan" scope="request">
				<logic:iterate name="listProjectPlan" id="row" indexId="recordId" type="com.ibm.ioes.npd.hibernate.beans.ProjectStatusReportDto">
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
				    <td>
				    <input id="chk1" name="chk"	value="<bean:write name="row" property="idCollection"/>"
						   type="checkbox" class="none">
						   
					</td>
					<td align="center"><%=newsno %>
					<input type="hidden" name="projectid<%=nSNo%>" id="projectid<%=nSNo%>" value="<bean:write name="row" property="projectId"/>">
					</td>				
					<td nowrap="nowrap"><bean:write name="row" property="projectId" /></td>
					<td nowrap="nowrap"><bean:write name="row" property="projectName" /></td>
					<td><bean:write name="row" property="npdCategory" /></td>
					<td><bean:write name="row" property="productCategory" /></td>
					<td><bean:write name="row" property="airtelPotential" /></td>
					<td><bean:write name="row" property="totalMarketPotential" /></td>
					<td><bean:write name="row" property="capexRequirement" /></td>
					<td><bean:write name="row" property="daysInProject" /></td>
					<td align="left" nowrap><a href="#" onclick="exportToExcel(<bean:write name="row" property="projectId" />)">Export To Excel</td>
					<td align="left" nowrap><a href="#" onclick="viewChart(<bean:write name="row" property="projectId" />)">View Chart</td>
				</tr>
				<%newsno=newsno+1;%>
				</logic:iterate>	
				</logic:present>
					</logic:notEmpty>
					<logic:empty name="listProjectPlan">
						<tr class="alterRedBg" align="center">
							<td colspan="10">NO RECORDS FOUND</td>
						</tr>
					</logic:empty>
			    </table>
			  	</div>
					
</html:form>
</div>
</BODY>
</html:html>
