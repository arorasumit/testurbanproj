<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@ page errorPage="ErrorPage.jsp"%>
<%@page import="java.util.*"%>
<%@ page session="true"%>
<%@page import="com.ibm.ioes.npd.hibernate.beans.TmEmployee"%>
<%@page import="com.ibm.ioes.npd.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.npd.beans.RoleSelectionBean"%>
<%@page import="com.ibm.ioes.npd.hibernate.beans.RoleSelectionDto"%>
<%@page import="com.ibm.ioes.npd.beans.SubMenuBean"%>
<link rel="stylesheet" type="text/css" href="gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="CSS/style.css">
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" type="text/css" href="CSS/jqueryslidemenu.css" />
<script type="text/javascript" src="js/jquery.min.js"></script>
<script type="text/javascript" src="js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="js/chrome.js"></script>
<script type="text/javascript" src="js/jsonrpc.js"></script>
<script type="text/javascript" src="js/inputColor.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<title>NPD</title>
<script language="javascript">
function openMenu(url)
{
   document.forms[0].action=url;
   document.forms[0].method="POST";
   document.forms[0].target="rightFrame"
   document.forms[0].submit();
}
function myTodoListAccess(roleID,request,empID,type)
{
	var appContextPath = '<%=request.getContextPath()%>';
	try
	{	
			jsonrpc = new JSONRpcClient(appContextPath + "/JSON-RPC");
	   		var result;
			result = jsonrpc.processes.dashboardAuthorization(roleID,request,empID);
			if (result==false)
			{
				if(type==1)
				{
					alert("User Doesn't Have Access to My To Do List Interface");
					return false;
				}
				if(type==2)
				{
					alert("User Doesn't Have Access to RFI Pending Interface");
					return false;
				}
				if(type==3)
				{
					alert("User Doesn't Have Access to PLR Uploading Interface");
					return false;
				}
			}
			else
			{
				if(type==1)
				{
					openMenu("MyToDoList.do?methodName=myToDoList");
				}
				if(type==2)
				{
					openMenu("pendingRfi.do?method=viewrfi");
				}
				if(type==3)
				{
					openMenu("PlrUploading.do?method=projectList");
				}
			}
	}
	catch(e) {
		//alert(e);
		return false;
	}
	
}
</script>
<body leftmargin="0" topmargin="0">
<table width="100%"  border="0" cellspacing="0" cellpadding="0" background="Images/gifs/header-bg.gif">
  <tr>
    <td><img src="Images/gifs/IOESTitle.gif" ></td>
  </tr>
</table>
<html:form action="/Dashboard" styleId="searchForm" method="post">
	<table align="center" width="100%" cellpadding="0" cellspacing="0"
		border="0">
		
	</table>
</html:form>
<html:form action="/RoleSelection" method="POST">
	<table border="0" cellspacing="0" cellpadding="0" class="chromestyle"
		id="chromemenu" width="100%">
		<tr>
			<td align="left">
				<div id="myslidemenu" class="jqueryslidemenu">
					<ul>	
						<logic:iterate id="moduleName" name="moduleNameList" type="String" >
							<logic:notEqual value="Admin Reports" name="moduleName">
								<li><a href="#"><bean:write name="moduleName" /></a>
									<ul>
										<logic:iterate id="subMenuRow" name="subMenuList">
										<%if(((RoleSelectionDto)subMenuRow).getModules().equals((String)moduleName))
										{
										%>					   					
										<li><a href="#" onClick=openMenu('<%=((RoleSelectionDto)subMenuRow).getUrl()%>');><bean:write name="subMenuRow" property="interfaceName"/></a></li>
										<%
										}
										%>
										</logic:iterate>
										<logic:equal value="Reports" name="moduleName">
											<logic:notEmpty name="subAdminMenuList"	>
												<li><a href="#">Admin Reports</a>
													<ul>
														<logic:iterate id="subAdminMenuList" name="subAdminMenuList">
															
																<li><a href="#" onClick=openMenu('<%=((RoleSelectionDto)subAdminMenuList).getUrl()%>');><bean:write name="subAdminMenuList" property="interfaceName"/></a></li>
															
														</logic:iterate>
													</ul>
												</li>
												
											</logic:notEmpty>
										</logic:equal>										
									</ul>
								</li>
							</logic:notEqual>
						</logic:iterate>
				  </ul>
				</div>
			</td>
				<logic:empty name="moduleNameList">
					<div id="myslidemenu" align="center">
						<strong>This Role Doesn't Have Any Access.Contact Administrator to get Access!!</strong>
					</div>
				</logic:empty>
			<td>
			</td>
		</tr>
	</table>
</html:form>
<br>
<iframe name="rightFrame" src="" height="77%" width="100%"
	scrolling="auto" frameborder="0" marginheight="0"></iframe>
<br>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
	<tr align="center">
		<td class="foot">Copyright © 2010 Airtel, All Rights Reserved.</td>
	</tr>
</table>
</body>
