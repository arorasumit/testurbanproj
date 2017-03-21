<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="ISO-8859-1"%>


<%@page import="com.ibm.ioes.npd.hibernate.beans.TtrnProjecthierarchy"%>
<%@page import="java.util.ArrayList"%>
<html>
<head>
	<title>Role Hierarchy</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="<%=request.getContextPath()%>/npd/CSS/style.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/npd/js/chrome.js"></script>
<link rel="StyleSheet" href="<%=request.getContextPath()%>/npd/CSS/dtree.css" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/npd/js/vertdtree.js"></script>
</head>
<body>
<div id="menu_abc" style="display: block;">
<table width="98%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
<tr valign="middle" id="projectPlan">
	<td valign="bottom" width="100%" align="center">
		<table width="100%"  border="0" cellspacing="0" cellpadding="0" align="center" class="borderTab">
		  <tr>
			<td><img src="<%=request.getContextPath()%>/npd/Images/tabLeft.gif"></td>
			<td background="<%=request.getContextPath()%>/npd/Images/tabbg.gif" class="tabHeading" width="30%">
				Product
			Plan</td>
			<td><img src="<%=request.getContextPath()%>/npd/Images/tabRight.gif"></td>
			 <td width="70%" ><img src="<%=request.getContextPath()%>/npd/Images/zero.gif" width="15" height="1"></td>
			  </tr>
		</table>
	</td>
</tr>
</table>
<table align="center" class="tabledata" width="98%" border="0" cellpadding="0" cellspacing="0">
<tr>
	<td align="center">
		<div style="overflow:scroll;height:100%;width:100%" class="scroll">	
		<table align="center" class="tabledata" width="98%" border="0" cellpadding="0" cellspacing="0">		
		<tr>
			<td align="center" ><B>
			<script language=javascript>
				mytree = new dTree('mytree');
				<% 
				  ArrayList aList=(ArrayList)request.getAttribute("taskList");
				  for(int a=0;a<aList.size();a++){
				  TtrnProjecthierarchy dd=(TtrnProjecthierarchy)aList.get(a);
				  %>
				  mytree.add(<%=dd.getCurrenttaskid() %>, <%=dd.getPrevtaskid()%>, ' <%=dd.getTaskname()%> ', '', ' <%=dd.getTaskname()%> ', '', '');
				  <%
				  }
				%>
				document.write(mytree); 
			</script> 
			</td>
		</tr>
		</table>
		</div>
	</td>
</tr>
</table>
	
</div>
</body>
</html>