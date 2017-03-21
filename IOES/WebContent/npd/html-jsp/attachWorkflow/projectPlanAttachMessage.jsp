<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">

<script language="JavaScript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/utility.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/scw.js"></script>
<script type="text/javascript" src="npd/js/timepicker.js"></script>
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>
<script type="text/javascript" src="npd/js/inputColor.js"></script>
<title>Project Workflow Attach Message</title>
<script type="text/javascript">
function goToHome()
{
	var myForm=document.getElementById('messageForm');
	//myForm.toExcel.value='false';
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}
</script>
</head>
<body>
<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">
				<td><img src="gifs/top/home.gif" title="Home" onClick="goToHome('Home');"></img></td>
			</tr>
</table>
<form action="/attachEditProjectPlan" id="messageForm" method="post">
<div id="menu_abc" style="display: block;">
<logic:present name="finalized">
	<table width="50%" border="2" class="tabledata" cellpadding="0"
			cellspacing="0" align="center">
			<tr><td align="center">
			<html:messages id="message" message="true" property="finalized">
				<b><bean:write name="message"/></b>
			</html:messages>
			</td></tr>
			<tr><td align="center"><div style="display: none;">
				View Finalized Project Workflow 
				<span id="attachProjectPlanButton" class="buttonLagre"
					onClick="void(0)">View</span></div>
			</td></tr>
	</table>
</logic:present>	
<logic:present name="draft">
	<table width="50%" border="2" class="tabledata" cellpadding="0"
			cellspacing="0" align="center">
			<tr><td align="center">
			<html:messages id="message" message="true" property="draft">
				<b><bean:write name="message"/></b>
			</html:messages>
			</td></tr>
			<tr><td align="center"><div style="display: none;">
				View Draft Project Workflow 
				<span id="attachProjectPlanButton" class="buttonLagre"
					onClick="void(0)">View</span></div>
			</td></tr>
	</table>
</logic:present>	


<logic:present name="discardDraft">
	<table width="50%" border="2" class="tabledata" cellpadding="0"
			cellspacing="0" align="center">
			<tr><td align="center">
			<html:messages id="message" message="true" property="discardDraft">
				<b><bean:write name="message"/></b>
			</html:messages>
			</td></tr>
	</table>
</logic:present>	
</div>
</form>
</body>
</html>