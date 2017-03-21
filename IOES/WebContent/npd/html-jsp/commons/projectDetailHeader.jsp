<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.npd.utilities.Utility"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">

<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jsonrpc.js"></script>
<script type="text/javascript" src="npd/js/inputColor.js"></script>
<title>Project Detail</title>
</head>
<body onLoad="javascript:document.body.click();">
<form id="headerForm">
<table border="0" align="center" cellpadding="0" cellspacing="0" class="tab2" width="100%">
	<bean:define id="valueBean" name="projectBean"/>
	<tr>
		<td class="lightBg" width="16%">Project Id</td>
			<td class="normal" width="18%"><bean:write name="valueBean" property="projectid"/></td>
		<td class="lightBg" width="16%">Project Name</td>
			<td class="normal" width="18%"><bean:write name="valueBean" property="projectName"/></td>
		<td class="lightBg">ChangeOrder Category</td>
			<td class="normal"><bean:write name="valueBean" property="npdCategory.npdcatdesc"/></td>									

	</tr>
</table>
</form>
</body>
</html>
