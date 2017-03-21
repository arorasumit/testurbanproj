<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<html:html>
<head>
<title>View select parameters</title>
<link href="<%=request.getContextPath()%>/gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/popUPDivWindow.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
</head>
<script language="javascript" type="text/javascript">
var callerWindowObj = dialogArguments;
function doLoadValue()
{
	document.getElementById('viewSelListId').value=callerWindowObj.document.bulkUploadBean.lsiList.value;
}
</script>
<body onload="doLoadValue()">
<div class="head"><span>View Selected Parameter Values</span></div>
<table style="vertical-align:top;" width="100%" border="0" id="tabLSI" align="center"
				cellpadding="0" cellspacing="0" class="tab2">
				<tr class="grayBg">
					<td></td>
					<td align="center">
						<textarea id="viewSelListId" readonly="readonly" size="50"
			cols="32" rows="8"></textarea>
					</td>
					<td></td>
				</tr>
				<tr class="grayBg">
					<td align="center" colspan="3"><div class="buttonLarge" onclick="window.close();" title="Click to Close" align="center"><b>Close</b></div></td>
				</tr>
</table>
	
<div class="head" align="center" ></div>
</body>
</html:html>