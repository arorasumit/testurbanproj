<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.beans.ProductCreationBean"%>
<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">

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

<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<script language="JavaScript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/utility.js"></script>
<script type="text/javascript" src="npd/js/scw.js"></script>
<script type="text/javascript" src="npd/js/timepicker.js"></script>
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>
<script type="text/javascript" src="npd/js/inputColor.js"></script>
<title>ChangeOrder Workflow</title>
<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>

<script type="text/javascript">
function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function showDetails(var1) {
	if(var1=='createProject')
	{
	document.productCreationBean.action="./createProduct.do?method=initProduct";
	document.productCreationBean.submit();  
	
	}
	if(var1=='searchProject')
	{
	document.productCreationBean.action="./createProduct.do?method=initSearchProduct";
	document.productCreationBean.submit();  
	
	}
	
}

function showProjectDetails(var1)
 {
 	
 		windowURL="./createProduct.do?method=initUpdateProject&projectId="+var1;
		windowDefault="resizable=yes;dialogWidth:700px;dialogHeight:400px;center:yes;resizable:yes;status:no;";
		window.showModalDialog(windowURL,self,windowDefault);
 
//	document.productCreationBean.action="./createProduct.do?method=initUpdateProject&projectId="+var1;
//	document.productCreationBean.submit();  
}

function updateProject(val1,val2)
{
	var errorString="";
	
	myform1=document.getElementById("updateForm");
	setFormBean(myform1);

	if(document.getElementById("productBrief").value==null||trim(document.getElementById("productBrief").value)=="")
	{
		errorString+="Please enter Product Brief     "+"\n";
	
	}
	if(document.getElementById("airtelPotential").value==null||trim(document.getElementById("airtelPotential").value)=="")
	{
		errorString+="Please enter Airtel Potential"+"\n";
	
	}
	if(document.getElementById("capexRequirement").value==null||trim(document.getElementById("capexRequirement").value)=="")
	{
		errorString+="Please enter Capex Requirement "+"\n";
	
	}
	if(document.getElementById("targetMarket").value==null||trim(document.getElementById("targetMarket").value)=="")
	{
		errorString+="Please enter Target Market  "+"\n";
	
	}
	if(document.getElementById("totalMktPotential").value==null||trim(document.getElementById("totalMktPotential").value)=="")
	{
		errorString+="Please enter Total Market Potential   "+"\n";
	
	}
	if(document.getElementById("launchDate").value==null||trim(document.getElementById("launchDate").value)=="")
	{
		errorString+="Please Enter Expected Launch Date   "+"\n";
	
	}
	if(val1=='takeAction')
	{
		//validate  other fields also-launchPriority-id_prdCategoryId
		if(document.getElementById("id_prdCategoryId").value==-1)
		{
		errorString+="Please Select Product Category"+"\n";
		}
		if(document.getElementById("launchPriority").value==null||trim(document.getElementById("launchPriority").value)=="" || trim(document.getElementById("launchPriority").value)=="Select")
		{
			errorString+="Please Select Priority of Launch    "+"\n";
		
		}
	}

	if(errorString!="")
	{
	alert(errorString);
	return false;
	}
	else
	{
		if(ValidateTextField(document.getElementById("productBrief"),'<%=request.getContextPath()%>',"Product Brief")==false)
		{
			return false;
		}
		if(CheckNumeric(document.getElementById("airtelPotential"),"Airtel Potential")==false)
		{
			return false;
		}
		if(CheckNumeric(document.getElementById("capexRequirement"),"Capex Requirement")==false)
		{
			return false;
		}
		if(ValidateTextField(document.getElementById("targetMarket"),'<%=request.getContextPath()%>',"Target Market")==false)
		{
			return false;
		}
		if(CheckNumeric(document.getElementById("totalMktPotential"),"Total Market Potential")==false)
		{
			return false;
		}




		if(val1=='saveDraft')
		{
			myform=document.getElementById('updateForm');
			myform.method.value="saveUpdateProductDraft";
			showLayer();
			myform.submit();
		}
		else
		if(val1=='takeAction')
		{
			if(ValidateTextField(document.getElementById("launchPriority"),'<%=request.getContextPath()%>',"Launch Priority")==false)
			{
				return false;
			}
			myform=document.getElementById('updateForm');
			if(val2=='approve')
			{
				myform.updateActionType.value="approveOverride";
			}
			if(val2=='reject')
			{
				myform.updateActionType.value="reject";			
			}
			myform=document.getElementById('updateForm');
			myform.method.value="approveRejectOverrideDraft";
			showLayer();
			myform.submit();
		}
		
	}
	
			
}
function fnClose()
{
	window.close();
}
function removeRows()
{
	var current=document.getElementById('id_pol');
    current.parentElement.removeChild(current);
    
	current=document.getElementById('id_pc');
    current.parentElement.removeChild(current);
    
	current=document.getElementById('userRow');
    current.parentElement.removeChild(current);
    
}
function forEntryDraft()
{
//hide priority of launch and product category :id_pol ,id_pc
	removeRows();
	document.getElementById('id_update').style.display="block";	
	document.getElementById('id_close').style.display="block";	
	document.getElementById('id_approveOverride').style.display="none";
	document.getElementById('id_reject').style.display="none";	
}
function forViewDraft()
{
//hide priority of launch and product category :id_pol ,id_pc
	removeRows();	
	document.getElementById('id_productBrief').readOnly=true;
	document.getElementById('id_airtelPotential').readOnly=true;
	document.getElementById('id_capexRequirement').readOnly=true;
	document.getElementById('id_targetMarket').readOnly=true;
	document.getElementById('id_totalMktPotential').readOnly=true;
	document.getElementById('id_launchDate').readOnly=true;		
	document.getElementById('calen').disabled="true";		
	
	document.getElementById('id_close').style.display="block";
	document.getElementById('id_update').style.display="none";	
	document.getElementById('id_approveOverride').style.display="none";
	document.getElementById('id_reject').style.display="none";	
}
function forActionOnDraft()
{
	document.getElementById('id_close').style.display="block";
	document.getElementById('id_update').style.display="none";	
	document.getElementById('id_approveOverride').style.display="block";
	document.getElementById('id_reject').style.display="block";	
}

function forViewAction()
{
	document.getElementById('tableGridData').style.display="none";
}


function forMode()
{
	<logic:present name="productCreationBean" property="updateMode">
		<logic:equal name="productCreationBean" property="updateMode" value="entryDraft">
			forEntryDraft();
		</logic:equal>
		<logic:equal name="productCreationBean" property="updateMode" value="viewDraft">
			forViewDraft();
		</logic:equal>
		<logic:equal name="productCreationBean" property="updateMode" value="actionOnDraft">
			forActionOnDraft();
		</logic:equal>	
		<logic:equal name="productCreationBean" property="updateMode" value="viewAction">
			forViewAction();
		</logic:equal>				
		
	</logic:present>
}
function parentPagingSubmit()
{
	myform=window.opener.document.getElementById('searchForm');
	//myform.elements["pagingSorting.pageNumber"].value=val;
	//showLayer();
	myform.submit();
}
function forParentRefresh()
{
	//window.dialogArguments.fromChildPagingSubmit();
	parentPagingSubmit();
//	window.opener.
	//window.close();
}
function onBodyLoad()
{
	document.body.click();
	forMode();
	<logic:present name="refreshParent">
		forParentRefresh();
	</logic:present>
}
</script>

</head>
<body onload="onBodyLoad()">
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
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="newProduct">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Update
				Project</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<html:form action="/createProduct" method="post" styleId="updateForm">
	<input type="hidden" name="method" >
	<html:hidden property="projectId"/>
	<html:hidden property="updateActionType"/>
	<html:hidden property="updationRequestedBy"/>

	
	<table width="100%" border="2" class="tabledata" cellpadding="0"
		cellspacing="0" align="left">
		<tr>
			<td>
				<table width="100%" border="0" align="center" >
								
					<tr valign="middle" id="newProduct">
						<td valign="bottom" width="100%" class="tabledata"  align="center">
							<jsp:include flush="true" page="./html-jsp/commons/projectDetailHeader.jsp">
							</jsp:include>
						</td>
					</tr>		
				</table>
			</td>
		</tr>
		<html:messages id="message" message="true" >
		<tr>
			<td colspan="1" align="center" style="color: red;">
				
					<li><bean:write name="message"/></li>
				
			</td>
		</tr>
		</html:messages>
		
		<tr>
			<td width="100%">

			<table width="100%" border="0" class="tabledata" cellpadding="0"
				id="tableGridData" cellspacing="0" align="center">
				<tr id="userRow">
					<td colspan="2">
						<table width="100%" border="0" cellpadding="0" class="tabledata"
							id="tableGrid" cellspacing="0" align="center">
							<tr class="redBg">

								<th>Requested By :</th>
								<th align="left"><bean:write name="productCreationBean" property="updationRequestedByName"/></th>
								<th>Email Id :</th>
								<th align="left"><bean:write name="productCreationBean" property="updationRequestedByEmail"/></th>

							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<th width="30%" scope="row">
					<div align="left">Product Brief <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text property="productBrief" styleId="id_productBrief"

						maxlength="100" style="width:140px" /></td>

				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="left">Airtel Potential (In Million Rs)<font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text property="airtelPotential" styleId="id_airtelPotential"

						maxlength="10" style="width:100px" /></td>
				</tr>

				<tr>
					<th width="30%" scope="row">
					<div align="left">Capex Requirement (In Million Rs)<font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text property="capexRequirement" styleId="id_capexRequirement"

						maxlength="10" style="width:100px" /></td>
				</tr>

				<tr>
					<th width="30%" scope="row">
					<div align="left">Target Market <font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text property="targetMarket" styleId="id_targetMarket"
						 maxlength="100"
						style="width:300px" /></td>
				</tr>

				<tr>
					<th width="30%" scope="row">
					<div align="left">Total Market Potential (In Million Rs)<font
						color="#990033">*</font></div>
					</th>
					<td width="70%"><html:text property="totalMktPotential" styleId="id_totalMktPotential"
						
						maxlength="100" style="width:140px" /></td>
				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="left">Expected Launch Date<font color="#990033">*</font></div>
					</th>
					<td><html:text property="launchDate" readonly="true" styleId="id_launchDate"
						 /> <img id="calen"
						src="npd/Images/cal.gif" width="16" height="16" border="0"
						alt="Pick a date" onclick="scwShow(scwID('launchDate'),event);"></td>
				</tr>
				<tr>
					<th width="30%" scope="row">
					<div align="left">Project Start Date<font color="#990033">*</font></div>
					</th>
					<td><html:text property="prjStartDate" readonly="true" styleId="id_launchDate"/> </td>
				</tr>
				<tr id="id_pol">
					<th width="30%" scope="row">
					<div align="left">Priority of Launch<font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:select property="launchPriority"  
						style="width:140px">
						<html:option value="Select">Select</html:option>
						<html:option value="High">High</html:option>
						<html:option value="Medium">Medium</html:option>
						<html:option value="Low">Low</html:option>
					</html:select></td>
					<!--<td><html:text property="launchPriority" styleId="id_launchPriority"/> </td>-->
					
					</tr>
				<tr id="id_pc">
					<th width="30%" scope="row">
					<div align="left">Product Category<font color="#990033">*</font></div>
					</th>
					<td width="70%"><html:select property="prdCategoryId" styleId="id_prdCategoryId"
						style="width:140px">
						<logic:notEmpty name="productCreationBean"
							property="prdCategoryList">
							<html:optionsCollection property="prdCategoryList"
								label="prodcatdesc" value="prodcatid" />
						</logic:notEmpty>
					</html:select></td>
				</tr>	
				<tr align="center">
					<th colspan="2" scope="row" width="50%">
					<table width="50%" border="0">
						<tr>
							<td>
							<div id="id_update">
							<span id="search" class="buttonVsmall"
								onClick="updateProject('saveDraft','');">Update</span>
							</div></td>
							<td><div id="id_close">
							<span id="search" class="buttonVsmall"
								onClick="fnClose();">Close</span></div></td>
							<td><div id="id_approveOverride">
							<span id="search" class="buttonLagre" 
								onClick="return updateProject('takeAction','approve');">Approve/Override</span></div></td>
							<td><div id="id_reject">
							<span id="search" class="buttonVsmall" 
								onClick="return updateProject('takeAction','reject');">Reject</span></div></td>
						</tr>
					</table>
					</th>
				</tr>
				<tr>
					<td></td>
					<td></td>
				</tr>
			</table>
			</td>
</tr></table>
			</html:form>
</div>
</BODY>


</html:html>
