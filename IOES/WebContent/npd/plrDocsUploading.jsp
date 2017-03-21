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



	function ValidateFields()
	{
		 myform=document.getElementById('searchForm');

		 if(document.getElementById("attachment").value!=null&&document.getElementById("attachment").value!="")
			{
				
				if (isValidFileExtension(document.getElementById("attachment").value)==false) 
				{
					//alert('Not A Valid File.Please Select a Valid type Of File');
					return false;    
				}
			}
		   else
		   {
		   	alert('Please Select an Attachment');
		   	return false;
		   }	
		 
		 //if(ValidateTextField(myform.taskapproveComments,'<%=request.getContextPath()%>')==false)
		//	return false;
	}

function MapStakeholder()
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

	

		//alert(	selectedemp.substring(0,selectedemp.length -1));
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

	function UploadPLR()
	{
		
		if(ValidateFields()==false)
		  {
				return false;
		  }
		
	 	document.searchForm.action="<%=request.getContextPath()%>/PlrUploading.do?method=uploadPlrDoc";
		document.searchForm.submit(); 
		
	}

function showMessage()
{
	var msg='';
	<html:messages id="message" message="true"  >
		msg=msg+'\n'+'<bean:write name="message" />';
	</html:messages>
	if(msg!='') {
	alert(msg);
	}

}

function onBodyLoad()
{
	showMessage();
}
</script>

</head>
<body onload="javascript:validate();onBodyLoad();">
<div id="menu_abc" style="display: block;">
<script type="text/javascript" src="js/scw.js"></script>
<html:form action="/PlrUploading" method="post" enctype="multipart/form-data"
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
	<table border="0" cellpadding="0" cellspacing="0" class="border"
		align="center" width="91%">
		<tr valign="middle" id="projectPlan">
			<td height="48" align="center" valign="bottom">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr>
					<td width="0" height="19"><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" height="19"
						width="26%">Upload PLR</td>
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

	<table cellSpacing="0" cellPadding="0" width="91%" border="0" id="tbl1"
		class="tabledata" style="display:block" align="center">

		<tr>
			<td>
			<table border="0" cellpadding="0" cellspacing="0" class="border"
				align="center" width="100%">
			</table>

			</td>
		</tr>
		<logic:present name="validation_errors">
		<tr><td>
		<table class="tabledata" align="center"><tr><td>
		<div class="error" align="center">
	
			<logic:iterate id="error_row" name="validation_errors" scope="request">
					<font color="red"><bean:write name="error_row" /></font><BR>
			</logic:iterate>
	
		</div></td></tr> </table></td></tr>
		</logic:present>
		<tr valign="bottom" align="center">
			<td align="right">
			<table cellSpacing="0" cellPadding="0" width="100%" border="0"
				id="tbl1" class="tabledata" style="display:block" align="center">
				<tr>
					<td align="right">
						PLR For The Period
					</td>

					<td align="left">
						<html:select property="monthid" style="width:250px">
						<logic:notEmpty name="formBean" property="lstMonthYear">
							<html:optionsCollection property="lstMonthYear" label="monthlabel"
								value="monthvalue" />
						</logic:notEmpty>
						</html:select>
					</td>
				</tr>

				<tr>
					<td align="right">
						Attachment
					</td>

					<td align="left">
						<html:file  property="attachment" size="20"></html:file>
					</td>
				</tr>

				<tr>
					<td align="center">
						<div class="buttonVSmall" onClick="javascript:UploadPLR();">Upload</div>
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
