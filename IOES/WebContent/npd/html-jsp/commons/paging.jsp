<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Navigation</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">
</head>
<body>
<table border="0" align="center" cellpadding="0" cellspacing="0" class="tab2" width="100%">  
							<tr class="lightBgWihtoutHover">
							<% 
							String  currPageNumber =request.getParameter("currPageNumber");
							String  maxPageNumber =request.getParameter("maxPageNumber");
							request.setAttribute("currPageNumber",currPageNumber);
							request.setAttribute("maxPageNumber",maxPageNumber);
							%>
						<td align="left">
							<font color="brown">
							<logic:equal name="currPageNumber" value="1">FIRST</logic:equal>
							<logic:notEqual name="currPageNumber" value="1">
								<a href="#" onclick="pagingSubmit('1')">FIRST</a>
							</logic:notEqual>
							&nbsp;|&nbsp;						
							<%if(Integer.parseInt((String)currPageNumber)<Integer.parseInt((String)maxPageNumber)){ %>
								<a href="#" onclick="pagingSubmit('<%=Integer.parseInt((String)currPageNumber)+1%>')">NEXT </a>
							<%}else { %>
								NEXT
							<%}%>
							</font>
						</td>
						<td align="center" style="color:#000000">Page <bean:write name="currPageNumber"/> of <bean:write name="maxPageNumber"/></td>
						<td align="right">
						<font color="brown">
							<logic:equal name="currPageNumber" value="1">PREV</logic:equal>
							<logic:notEqual name="currPageNumber" value="1">
								<a href="#" onclick="pagingSubmit('<%=Integer.parseInt((String)currPageNumber)-1%>')">PREV </a>
							</logic:notEqual>
							&nbsp;|&nbsp;
							<%if(Integer.parseInt((String)currPageNumber)<Integer.parseInt((String)maxPageNumber)){ %>
								<a href="#" onclick="pagingSubmit('<%=maxPageNumber %>')">LAST</a>
							<%}else { %>
								LAST
							<%}%>
							</font>
						</td>
					</tr>
						</table>
</body>
</html>