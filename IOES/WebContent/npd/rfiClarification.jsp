<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="com.ibm.ioes.npd.beans.RfiBean"%>
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
<title>NPD PORTAL-RFI Clarification</title>

<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>

<script type="text/javascript">


	function validate()
	{
		document.body.click();
		if(document.searchForm.hiddenReturnFlag.value == "1")
		{
		window.close();
		if (window.opener && !window.opener.closed) {
		window.opener.location.reload();
			} 	
		}
		
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
	<logic:present name="SAVED" scope="request">
		//window.opener.searchSubmit();
		window.close();
		if (window.opener && !window.opener.closed) {
		myform=window.opener.document.getElementById('searchForm');
		myform.action="<%=request.getContextPath()%>/pendingRfi.do?method=viewrfi";
		myform.submit();
			}
	</logic:present>
}
	


function saveRfi(var1)
{
	var valid_extensions = /(.xls|.doc|.xlsx|.docx|.ppt|.pps|.jpg|.gif|.txt)$/i;
	var errorString="";
	if(document.getElementById("attachment").value==null||document.getElementById("attachment").value=="")
	{
	}
	else if (!valid_extensions.test(document.getElementById("attachment").value)) 
	{
	errorString+="The Attachment file is of the wrong type."+"\n";
	 }
	 
	if(document.getElementById("rfiClarification").value==null||trim(document.getElementById("rfiClarification").value)=="")
	{
	errorString+="Please enter RFI Clarification"+"\n";
	}
	else
		{
			if(chkClarificationLength(document.getElementById("rfiClarification").value))
			{
			errorString+="";
			}
		}
	if(errorString!="")
	{
	alert(errorString);
	return false;
	
	}
	else
	{
	
	document.rfiBean.action="./pendingRfi.do?method=saveclarification&projectId="+var1;
	showLayer();
	document.rfiBean.submit();
	//window.close();
	
	}
}

function trim(stringToTrim) {
	return stringToTrim.replace(/^\s+|\s+$/g,"");
}

function chkClarificationLength(remarks)
{
	if (remarks != '' && 
	trim(remarks) != '' && 
	trim(remarks).length >500)
	{
		alert ("Clarification cannot contain more than 500 characters");
		return false;
	}
	return true;
}


function downloadFile()
{
	document.rfiBean.action="<c:out value='${sessionScope.MenuContextPath}' />/pendingRfi.do?method=downloadFile";
	document.rfiBean.submit();	
}
</script>
</head>

<body onload="javascript:onBodyLoad();document.body.click();">
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
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">RFI
				Clarification</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>
			</tr>
		</table>
		</td>
	</tr>
</table>

<br>
<br>

<html:form action="/pendingRfi" method="post"
	enctype="multipart/form-data">

	<table width="100%" border="2" class="tabledata" cellpadding="0"
		cellspacing="0" align="left" id="tableGrid">
		
		<tr>
			<td><b>Rasied RFI Remarks</b> :</td>
			<td><html:textarea property="rfiRemarks" readonly="true"></html:textarea></td>
		</tr>
		<tr>
			<td><b>Raised RFI Doc</b>  :</td>
			<td><a href="#" onclick="downloadFile()"><bean:write name="rfiBean" property="rfiDocumentName"/></a></td>
			<html:hidden property="projectId" />

		</tr>
	

		<tr>
			<td><b>Attachment</b></td>
			<td><html:file property="attachment" size="20"
				onkeydown='this.blur()'
				title="File Accepts jpg,jpeg,gif,xls,pdf,vsd,word extensions"></html:file></td>
		</tr>
		<tr>
			<td><b>Clarification</b> <font color="red">*</font> :</td>
			<td><html:textarea property="rfiClarification"></html:textarea></td>
			<html:hidden property="projectId" />

		</tr>
	<html:hidden property="rfiId"/>
		<tr>
			<td align="center" colspan="2"><span id="search"
				class="buttonVsmall"
				onClick="return saveRfi(<bean:write name="rfiBean" property="projectworkflowid"/>);">Save<span></td>
		</tr>
		<logic:messagesPresent message="true">
			<tr colspan="4">
				<td align="center"><font color="red" face="Verdana" size="2"><html:messages
					id="message" message="true">
					<li><bean:write name="message" /></li>
				</html:messages></font></td>

			</tr>

		</logic:messagesPresent>

	</table>


</html:form>
</div>
</body>

</html:html>


