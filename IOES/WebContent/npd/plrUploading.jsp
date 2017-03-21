<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
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
<script language="javascript" src="js/utility.js"></script>
<%@include file="/js/scwblank.html"%>


<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>


<script type="text/javascript">

function MapStakeholder1()
{
	var errorString="";
	var selectedRole=0;
	var selectedemp = '';
	if(document.getElementById("employeeId").value==-1)
	{
		errorString+="Please Select Employee"+"\n";
	}
	for(x=0;x<document.getElementById("employeeId").length;x++)
		{
				if(document.getElementById("employeeId").options[x].selected)
				{
					selectedemp = selectedemp + document.getElementById("employeeId").options[x].value + "," ;		
				}
		}

	if(selectedRole<1)
		{
		errorString+="Please Select atleast 1 Role \n";
		}
		else if(selectedRole>document.getElementById("employeeId").length-2)
		{
		errorString+="Please Select upto "+(document.getElementById("employeeId").length-2)+" Roles \n";
		}

	

		
		document.getElementById("stakeholderidlist").value = selectedemp.substring(0,selectedemp.length -1);
	 	document.searchForm.action="<%=request.getContextPath()%>/PlrUploading.do?method=mapStakeholder";
		document.searchForm.submit(); 
	
}

	function validate()
	{
		document.body.click();
		if(document.searchForm.hiddenReturnFlag.value == "1")
		{
		window.close();
		if (window.opener && !window.opener.closed) {
		myform=window.opener.document.getElementById('searchForm');
		myform.action="<%=request.getContextPath()%>/PlrUploading.do?methodName=projectList";
		myform.submit();
			} 	
		}
		
	}

	function MapStakeholder(flag)
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
		
		
		
		if(nCount==undefined)
		{
		  if(document.forms[0].chk.checked==true)
		  {	
			checkCount = 1;
			if(CheckNumeric(document.searchForm("plannedduration1"),'Planned Duration At Row 1')==false)
			return false;
			values=document.searchForm("plannedduration1").value + '_'+ document.searchForm("plrmapped1").value;
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
						    
							if(CheckNumeric(document.searchForm("plannedduration"+countValue),'Planned Duration At Row '+ countValue)==false)
								return false;
							if(isPositiveIntegerGreaterThan0(document.searchForm("plannedduration"+countValue).value)==false)
							{
								alert('Only Positive No. Greater than 0 is allowed');
							    return false;
							}    
							values = document.searchForm("plannedduration"+countValue).value + '_'+document.searchForm("plrmapped"+countValue).value;
						}
						else
						{
							if(isPositiveIntegerGreaterThan0(document.searchForm("plannedduration"+countValue).value)==false)
							{
								alert('Only Positive No. Greater than 0 is allowed');
							    return false;
							}    
						 if(CheckNumeric(document.searchForm("plannedduration"+countValue),'Planned Duration At Row '+ countValue)==false)
							return false;			
						  values = values + "," +  document.searchForm("plannedduration"+countValue).value + '_'+ document.searchForm("plrmapped"+countValue).value;
						}
						checkCount++;
					}		
					countValue++;
				}
			}
		}
		
		document.searchForm.selectedRow.value = values;
		//alert(values);
		document.searchForm.action="<%=request.getContextPath()%>/PlrUploading.do?method=mapStakeholder";	
		//showLayer();
		document.searchForm.submit();

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

</script>

</head>
<body onload="javascript:validate();">
<div id="menu_abc" style="display: block;">
<script type="text/javascript" src="js/scw.js"></script>
<html:form action="/PlrUploading" method="post"
	styleId="searchForm">

	<input type="hidden" name="method" value="viewProjectList">
	<bean:define id="formBean" name="plrUploading"></bean:define>
	<html:hidden name="formBean" property="pagingSorting.sortByColumn" />
	<html:hidden name="formBean" property="pagingSorting.sortByOrder" />
	<html:hidden name="formBean" property="pagingSorting.pageNumber" />
	<html:hidden name="formBean" property="stakeholderidlist" />
	<html:hidden property="hiddenReturnFlag" />
	<html:hidden property="projectId"/>


	<html:hidden property="projectPlanId" />
	<html:hidden property="selectedRow" />
	
	<table border="0" cellpadding="0" cellspacing="0" class="border"
		align="center" width="100%">
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
	<div style="width:100%;overflow:scroll"	class="scroll">
			<table cellSpacing="1" width="100%" border="0"	class="tabledata" id="TABLE1">
				<tr class="rptheader">
				
					<td>S.No.</td>
					<td>StakeHolder</td>
					<td>Role</td>
					<td>Planned Duration(Months)</td>
					<td><input name="chkAll" id="chkAll"
						   value="0" onClick="javascript:fnCheckAll();" type="checkbox"
						   class="none">Map</td>
					
		</tr>
				<%int nSNo=0; %>
				<logic:iterate id="row"  name="mappedlist" indexId="index1"  >
					<%
							String classType=null;
							if(index1.intValue() % 2 == 0)
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
									<td align="center"><%=nSNo%></td>
									<td><bean:write name='row' property="tmployee.empname" /></td>
									<td><bean:write name='row' property="rolename" /></td>
									<td>
										<input type="text" id="plannedduration<%=nSNo%>" maxlength="2" size="2" value=<bean:write name='row' property="plannedduration" /> />
										<input type="hidden" id="oldplannedduration<%=nSNo%>" value=<bean:write name='row' property="plannedduration" /> />
									</td>
									<td>
									<input type="checkbox" id="chk1" name="chk" value="<bean:write name='row' property='ismapped' />"
										  <logic:equal name="row" property='ismapped' value="1">
										  checked="checked" </logic:equal>
										/>
									<input type="hidden" id="plrmapped<%=nSNo%>" value="<bean:write name='row' property='roleid' />_<bean:write name='row' property='tmployee.npdempid'/>" >										
									</td>
								</tr>
				</logic:iterate>
				<tr>
					<td colspan="4" align="center">
							<div class="buttonVSmall" onClick="javascript:MapStakeholder();">Map</div>
					</td>
				</tr>
				
			</table>
	</div>
					
						
</html:form>
</div>
</BODY>

</html:html>
