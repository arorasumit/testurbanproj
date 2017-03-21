<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
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
function addProductCategory()
{
	productForm = document.getElementById('addProductForm');
	setFormBean(productForm);
	if(document.getElementById('descId').value == ""){
		alert('Please Enter some description!');
		return false;
	}

	if(ValidateTextField(document.getElementById('descId'),'<%=request.getContextPath()%>',"Description")==false)
	{
		return false;
	}
	
	productForm.action="<%=request.getContextPath()%>/ProductCategory.do?method=addProductCategory";
	showLayer();
	productForm.submit();
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
<logic:present name="validation_errors">
	<table width="80%" class="tabledata" align="center">
		<tr>
			<td>
				<div class="error" align="center">
					<logic:iterate id="error_row" name="validation_errors" scope="request">
							<font color="red"><bean:write name="error_row" /></font><BR>
					</logic:iterate>
				</div>
			</td>
		</tr>
	</table>
</logic:present>
<br>
<html:form action="/ProductCategory" styleId="addProductForm"
	method="post">
	<table width="80%" border="0" cellpadding="0" cellspacing="0"
		class="border" align="center">
		<tr valign="middle" id="newProduct">
			<td valign="bottom" width="100%" align="center">
			<table width="100%" border="0" cellspacing="0" cellpadding="0"
				align="center" class="borderTab">
				<tr>
					<td width="5"><img src="npd/Images/tabLeft.gif"></td>
					<td background="npd/Images/tabbg.gif" class="tabHeading" width="367">Add Product Category</td>
					<td width="9"><img src="npd/Images/tabRight.gif"></td>
					<td align="left" width="703"><font size="1"> </font></td>
					<td width="62" align="right" style="padding-right:10px;">&nbsp;</td>
				</tr>
			</table>
			</td>
		</tr>
	</table>
	<table width="80%" border="1" class="tabledata" cellpadding="3" cellspacing="1" align="center">
		<tr>
			<td>
			<table align="center" cellSpacing="1" cellPadding="4" border="0"
				class="scroll" id="tblRollList" width="100%">
				<tr>
					<td ></td>
					<td align="right"><a href="<%=request.getContextPath()%>/ProductCategory.do?method=viewProductCategory">Edit Product Category</a></td>
				</tr>
				<tr>
					<td width="30%">Description<font color="#990033">*</font></td>
					<td width="60%">
					<input type="text" id="descId" name="description">
					
					</td>
				</tr>
				<tr>
					<td width="30%">Status<font color="#990033">*</font></td>
					<td width="60%">
					<input type="radio" name="isActive" value="Y" checked="checked">Active
					<input type="radio" name="isActive" value="N">InActive
					
					</td>

				</tr>
				<tr>
				<td align="center" colspan="5">
						<input type="Button" name="Add" value="Add" onclick="addProductCategory();" >
					 </td>	
				</tr>
				
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
			</table>
			</td>
		</tr>
	</table>
</html:form>
</div>
</body>
</html:html>