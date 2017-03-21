<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

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



function performAction(val)
{
	//dataForm=document.getElementById("dataForm");
	//setFormBean(myform1);
	<logic:equal name="newIdeaBean" property="mode" value="MODE_NEW">
		if(val=='saveNew')
		{
			var myForm=document.getElementById('dataForm');
			setFormBean(myForm);
			
			if(document.getElementById("id_briefDesc").value==null||trim(document.getElementById("id_briefDesc").value)=="")
			{
				alert("Please enter Brief Description");
				document.getElementById("id_briefDesc").focus();
				return false;
			}else if(document.getElementById("id_briefDesc").value.length>1000)
			{
				alert("Maximum Length Allowed in Brief Description is 1000.");
				document.getElementById("id_briefDesc").focus();
				return false;
			}
			if(ValidateTextField(document.getElementById("id_briefDesc"),'<%=request.getContextPath()%>',"Brief Description")==false)
			{
				return false;
			}
//			id_benefit
			if(document.getElementById("id_benefit").value==null||trim(document.getElementById("id_benefit").value)=="")
			{
				alert("Please enter Benefit of Idea");
				document.getElementById("id_benefit").focus();
				return false;
			}else if(document.getElementById("id_benefit").value.length>1000)
			{
				alert("Maximum Length Allowed in Benefit of Idea is 1000.");
				document.getElementById("id_benefit").focus();
				return false;
			}
			if(ValidateTextField(document.getElementById("id_benefit"),'<%=request.getContextPath()%>',"Benefit of Idea")==false)
			{
				return false;
			}
			
			//id_similarProductExist
			if(document.getElementById("id_similarProductExist").value=='-1')
			{
				alert("Please Select - Is a similar product exist anywhere.");
				document.getElementById("id_similarProductExist").focus();
				return false;
			}else if(document.getElementById("id_similarProductExist").value=='Y')
			{
				if(document.getElementById("id_organisation").value==null||trim(document.getElementById("id_organisation").value)=="")
				{
					alert("Please enter Organisation");
					document.getElementById("id_organisation").focus();
					return false;
				}else if(ValidateTextField(document.getElementById("id_organisation"),'<%=request.getContextPath()%>',"Organisation")==false)
				{
					return false;
				}
				
				if(document.getElementById("id_country").value==null||trim(document.getElementById("id_country").value)=="")
				{
					alert("Please enter Country");
					document.getElementById("id_country").focus();
					return false;
				}else if(ValidateTextField(document.getElementById("id_country"),'<%=request.getContextPath()%>',"Country")==false)
				{
					return false;
				}
				
				//////
				if(document.getElementById("id_prdDescription").value==null||trim(document.getElementById("id_prdDescription").value)=="")
				{
					alert("Please enter Product Description");
					document.getElementById("id_prdDescription").focus();
					return false;
				}else if(document.getElementById("id_prdDescription").value.length>1000)
				{
					alert("Maximum Length Allowed in Product Description is 1000.");
					document.getElementById("id_prdDescription").focus();
					return false;
				}
				if(ValidateTextField(document.getElementById("id_prdDescription"),'<%=request.getContextPath()%>',"Product Description")==false)
				{
					return false;
				}
				
				if(document.getElementById("id_marketSize").value==null||trim(document.getElementById("id_marketSize").value)=="")
				{
					alert("Please enter Market Size");
					document.getElementById("id_marketSize").focus();
					return false;
				}else if(ValidateTextField(document.getElementById("id_marketSize"),'<%=request.getContextPath()%>',"Market Size")==false)
				{
					return false;
				}				
			}
			
//			id_industryVertical
			if(document.getElementById("id_industryVertical").value==null||trim(document.getElementById("id_industryVertical").value)=="")
			{
				alert("Please enter Industry Verticals");
				document.getElementById("id_industryVertical").focus();
				return false;
			}else if(document.getElementById("id_industryVertical").value.length>1000)
			{
				alert("Maximum Length Allowed in Industry Verticals is 1000.");
				document.getElementById("id_industryVertical").focus();
				return false;
			}
			if(ValidateTextField(document.getElementById("id_industryVertical"),'<%=request.getContextPath()%>',"Industry Verticals")==false)
			{
				return false;
			}
//id_usp
			if(document.getElementById("id_usp").value==null||trim(document.getElementById("id_usp").value)=="")
			{
				alert("Please enter USP");
				document.getElementById("id_usp").focus();
				return false;
			}else if(document.getElementById("id_usp").value.length>1000)
			{
				alert("Maximum Length Allowed in USP is 1000.");
				document.getElementById("id_usp").focus();
				return false;
			}
			if(ValidateTextField(document.getElementById("id_usp"),'<%=request.getContextPath()%>',"USP")==false)
			{
				return false;
			}



			myForm.method.value='save';
			
			showLayer(); 
			myForm.submit();
		}
		else if(val=='backForSave')
		{
			var myForm=document.getElementById('dataForm');
			myForm.method.value='backForSave';
			showLayer(); 
			myForm.submit();
		}
	</logic:equal>
	<logic:equal name="newIdeaBean" property="mode" value="MODE_VIEW">
		if(val=='backForView')
		{
			var myForm=document.getElementById('dataForm');
			myForm.method.value='backForView';
			showLayer(); 
			myForm.submit();
		}
	</logic:equal>
}
function fnSimiPrdExistToggle()
{
	val=document.getElementById('id_similarProductExist').value;
	if(val=='Y')
	{	document.getElementById('id_country').disabled=false;
		document.getElementById('id_organisation').disabled=false;
		document.getElementById('id_prdDescription').disabled=false;
		document.getElementById('id_marketSize').disabled=false;
	}
	else if(val=='N' || val=='-1')
	{
		document.getElementById('id_country').disabled=true;
		document.getElementById('id_organisation').disabled=true;
		document.getElementById('id_prdDescription').disabled=true;
		document.getElementById('id_marketSize').disabled=true;
	}
}

function onBodyLoad()
{
	document.body.click();
	fnSimiPrdExistToggle();
	<logic:equal name="newIdeaBean" property="mode" value="MODE_NEW">
		
	</logic:equal>
	<logic:equal name="newIdeaBean" property="mode" value="MODE_VIEW">
		
	</logic:equal>
}

</script>

</head>
<table width="100%"  border="0" cellspacing="0" cellpadding="0" background="Images/gifs/header-bg.gif">
  <tr>
    <td> <img src="Images/gifs/logo.gif" height="40" width="94"></td>
    <td><img src="Images/gifs/npdPortal.gif" height="40" width="351"></td>
    <td width="100%" background="Images/gifs/right-bg.gif" style="background-position:right; background-repeat:no-repeat"></td>
  </tr>
</table>
<br>
<body onload="javascript:onBodyLoad();">
<div id="menu_abc" style="display: block;">
<div id="shadow" class="opaqueLayer"> 
	<br /><br /><br /><br /><br /><br /><br /><br />
    <font color="#FFFFFF" size="9"><b>Please Wait..</b></font>
    <br /><br /><br />
</div>

<logic:messagesPresent message="true">
	<table width="50%" align="center" id='messageBody'>
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
<table width="100%" border="0" cellpadding="0" cellspacing="0"
	class="border" align="center">
	<tr valign="middle" id="newProduct">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="borderTab">
			<tr>
				<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">New Product Idea Generator</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
<br>
<logic:present name="validation_errors">
<table class="tabledata" align="center">
<tr>
	<td align="left">
		<div class="error" align="left">
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>
		</div>
	</td>
</tr>
</table>
<br>
</logic:present>
<html:form action="/HaveAnIdea" method="post" styleId="dataForm"
	enctype="multipart/form-data" >
	<input type="hidden" name="method"/>
	<bean:define id="formBean" name="newIdeaBean"></bean:define>
	
	<html:hidden property="hrms_employeenumber"/>
	<table width="80%" border="2" class="tabledata" cellpadding="0"
		cellspacing="0" align="center">

		<tr>
			<td width="40%" style="vertical-align: middle">Name of the Idea Generator :</td>
			<td width="60%"><html:text property="nameGenerator" maxlength="100" size="25" readonly="true"></html:text></td>
			
		</tr>
		<tr>
			<td colspan="2">
				<table width="100%">
					<tr>
						<td width="12%" style="vertical-align: middle">Mail Id :<font color="#990033"></font></td>
						<td width="30%" align="left"><html:text property="mailId" maxlength="50" size="40" readonly="true"></html:text>
						</td>
						<td width="20%" align="right" style="vertical-align: middle">Mobile No :<font color="#990033"></font></td>
						<td width="30%" align="left"><html:text property="phoneNo" maxlength="15" size="40" readonly="true"></html:text>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<table width="100%">
					<tr>
						<td width="12%" style="vertical-align: middle">Function :<font color="#990033"></font></td>
						<td width="30%" align="left"><html:text property="function" maxlength="100" size="40" readonly="true"></html:text>
						</td>
						<td width="20%" align="right" style="vertical-align: middle">Location :<font color="#990033"></font></td>
						<td width="30%" align="left"><html:text property="location" maxlength="100" size="40" readonly="true"></html:text>
						</td>
					</tr>
				</table>
			</td>
		</tr>
				

		
		<tr>
			<td width="40%">A brief description of the Idea<font color="#990033">*</font></td>
			<td width="60%"><html:textarea property="briefDesc" rows="3" styleId="id_briefDesc"
				cols="50"></html:textarea></td>
		</tr>

		<tr>
			<td width="40%">What is the benefit of the Idea to the customer<font color="#990033">*</font></td>
			<td width="60%"><html:textarea property="benefit" rows="3" styleId="id_benefit"
				cols="50"></html:textarea></td>
		</tr>

		<tr>
			<td colspan="2">
				<table>
					<tr>
						<td width="40%">Is a similar product already existing anywhere.(Y/N) <font color="#990033">*</font></td>
						<td width="60%">
							<html:select property="isSimilarProductExist" 
									onchange="javacript:fnSimiPrdExistToggle();" styleId="id_similarProductExist">
								<html:option value="-1">--</html:option>
								<html:option value="N">N</html:option>
								<html:option value="Y">Y</html:option>					
							</html:select>
						</td>
					</tr>
					<tr>
						<td align="right">
							If Yes , provide
						</td>
						<td>&nbsp;
						</td>
					</tr>
					<tr>
						<td align="right">
							Organisation :
						</td>
						<td>&nbsp;<html:text property="organisation" maxlength="50" 
										size="25" disabled="true" styleId="id_organisation"></html:text></td>
					</tr>
					<tr>
						<td align="right">
							Country :
						</td>
						<td>&nbsp;<html:text property="country" maxlength="50" 
										size="25" disabled="true" styleId="id_country"></html:text></td>
					</tr>
					<tr>
						<td align="right">
							Product Description :
						</td>
						<td>&nbsp;<html:textarea property="prdDescription" rows="3" cols="50" 
										disabled="true" styleId="id_prdDescription"></html:textarea></td>
					</tr>
					<tr>
						<td align="right">
							Market Size :
						</td>
						<td>&nbsp;<html:text property="marketSize" maxlength="50" 
										size="25" styleId="id_marketSize"></html:text></td>
					</tr>
				</table>
			</td>
		</tr>
		
		<tr>
			<td width="40%">Which industry verticals do you think this idea is best suited for<font color="#990033">*</font></td>
			<td width="60%"><html:textarea property="industryVertical" rows="2" styleId="id_industryVertical"
				cols="50"></html:textarea></td>
		</tr>

		<tr>
			<td width="40%">What is the USP of the idea<font color="#990033">*</font></td>
			<td width="60%"><html:textarea styleId="id_usp"
				property="usp" rows="2" cols="50"></html:textarea></td>
		</tr>

		<tr>
			<logic:equal name="formBean" property="mode" value="MODE_NEW">
			<td align="right">
				<span id="firstButton" class="buttonSmall"
					onClick="performAction('saveNew')">Send</span> 
			</td>
			<td align="left">
				<span id="firstButton" class="buttonSmall"
					onClick="performAction('backForSave')">Back</span> 
			</td>
			</logic:equal>
			<logic:equal name="formBean" property="mode" value="MODE_VIEW">
				
			</logic:equal>
		</tr>
		<tr >
			<td colspan="2"><font color="#990033" size="2">* Your idea will be submitted to and reviewed by the Innovation team</font></td>
		</tr>
	</table>
</html:form>
</div>
</body>
</html:html>

