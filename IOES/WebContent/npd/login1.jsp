<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="java.util.Enumeration"%>
<%@page import="com.ibm.ioes.npd.utilities.AppConstants"%>
<html>
	<head>
		<title>NPD Portal</title>
		<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
		<link type="text/css" rel="stylesheet" href="CSS/style.css">
		<script language="javascript" type="text/javascript">
		function funOnLoad()
		{
			<%String iv_user="";
			if(request.getHeaderNames()!=null)
			{
				Enumeration enum1 = request.getHeaderNames(); // Fetching the values from header variable.
				String name="";
				String value="";
				String tmp = "";
				while (enum1.hasMoreElements()) 
				{
					name = (String) enum1.nextElement();
					value = request.getHeader(name);
					System.out.println(name + "=" + value);
					tmp += "name=" + name + ", value=" + value;
					
					if (name.equalsIgnoreCase("iv-user"))
					{
						iv_user = java.net.URLDecoder.decode(value);
						session.setAttribute(AppConstants.SSFID,iv_user);
					} 
				}// end of while
				iv_user = "B0038065";//REMOVE
				session.setAttribute(AppConstants.SSFID,iv_user);
			}
			else
			{
			   out.print("null value");
			}	%>
				document.forms[0].ssfUserID.value= '<%=iv_user%>';
				//document.forms[0].action="<c:out value='${sessionScope.MenuContextPath}' />/LoginAction.do?method=Submit"; 
				document.forms[0].action="<c:out value='${sessionScope.MenuContextPath}' />/WelcomeOption.do?method=init"; 
				
				document.forms[0].method="post";
				document.forms[0].submit();
			
		}
		
						
		</script>
	</head>
	<body leftMargin=0 topMargin=0 MARGINHEIGHT="0" MARGINWIDTH="0" onload="funOnLoad()">
		<table cellSpacing=0 cellPadding=0 width=100% border=0  align="center"> 
			<tbody>
				<tr>
					<td vAlign=top align=center width="100%" height="440" bgColor="#ffffff" style="padding-top:50px;">
						<html:form action="/LoginAction" >
							<table>
								<tr>
									<td></td>
									<td><html:hidden  property="ssfUserID"/></td>
								</tr>
							</table>
						</html:form>
					</td>	
				</tr>
			</tbody>
		</table>	
	</body>
</html>
