<!--[210]---Anoop Tiwari ---13-March-2014--------O2C Drop 2-------Showing Action  button depending on Service presence in user's bin  -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.forms.SessionObjectsDto"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.utilities.Utility"%>
<%@page import="com.ibm.ws.sib.msgstore.task.TaskList"%>
<html>
<head>
<title>:: View Order: Integrated Order Entry System</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<link href="./gifs/style.css" type="text/css" rel="stylesheet">
<script language="javascript">
function goToHome()
{
	document.forms[0].action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	document.forms[0].submit();
}



</script>
</head>
<body>
<html:form action="/viewOrderAction"  enctype="text/plain" styleId="searchForm" method="post">
<bean:define id="formBean" name="newOrderBean"></bean:define>
<html:hidden property="searchTaskId" />
<html:hidden property="searchTaskOwner" />
<html:hidden property="searchTaskOwnerId" />

<html:hidden property="orderNo"/>

<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
<tr align="right">
	<td></td>
</tr>
<tr>
	<td>&nbsp;</td>
</tr>

<tr>
	<td>&nbsp;<br></td>
</tr>
<tr>
	<td>
		<!-- <table border="0" align="center" cellpadding="0" cellspacing="0" class="tab2" width="98.5%" height="650px;" >
		<tr class="lightBgWihtoutHover">
			<td width="10%" align="left" valign="top">
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="85%">
						<OL id="toc" class="tabledata">
							<li onClick="changeTab(4)"><a href="index1.html#Page_4">APPROVALS</a></li> 
						</OL>
					</td>
				</tr>
				</table>
			</td>
			<td width="90%" align="left" valign="top">&nbsp;</td>
		</tr>
		<tr>
			<td colspan="2" width="1005">&nbsp;<br></td>
		</tr>
		</table> -->
		
   </td>
</tr>
<tr>
	<td>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td valign="top">
			<fieldset class="border1">
				<legend> <b>Tasks</b> </legend>
				  <div class="scrollWithAutoScroll"  style="height:180px;width:100%;">
					<table  border="1" cellspacing="0" cellpadding="0" align="center" width="100%" bgcolor="">
					<tr>
						<td width="2%" nowrap>&nbsp;</td>
						<td width="2%" nowrap>&nbsp;</td>
						<td width="3%" nowrap>Task Number</td>
						<td width="10%" nowrap>Owner Type</td>
						<td width="25%" nowrap>Task Status</td>
						<td width="20%" nowrap>Task Start Date</td>
						<td width="20%" nowrap>Task End Date</td>
					</tr>
					<% long nSNo=0; %>
					<logic:present name="taskListOfOrder" scope="request">
						<logic:empty name="taskListOfOrder">
							<tr align="center">
								<td colspan="12" title="No Task Exists for this Order">
									<b>No Task Exists for this Order</b>
								</td>
							</tr>
						 </logic:empty>
						 <logic:notEmpty name="taskListOfOrder">
							<logic:iterate name="taskListOfOrder" id="TaskList" indexId="recordId">
							<%  
								String classType=null;
								if(recordId.intValue() % 2 == 0)
								{
								classType="normal";
								}
								else
								{
								classType="lightBg";				
								}	
								nSNo++;%>				
							  <logic:notEqual value="10" name="TaskList" property="changeorderstatus">	
							<tr class="<%=classType%>">
								<td align="center"><%=nSNo%></td>
								<td>
									<input type="radio" id="chkSelectTask" name="chkSelectTask" value="" onClick="showNotes(<bean:write name='TaskList' property='taskID'/>,'<bean:write name='TaskList' property='taskStatus'/>');">
								</td>
								<td title="<bean:write name='TaskList' property='taskID'/>">
									<bean:write name='TaskList' property='taskID'/>
								</td>
								
								<td title="<bean:write name='TaskList' property='ownerType'/>">
									<bean:write name='TaskList' property='ownerType'/>
								</td>
								<td>
									<table width="100%">
										<tr>
											<td width="10%" title="<bean:write name='TaskList' property='taskStatus'/>">
												<bean:write name='TaskList' property='taskStatus'/>
												
											</td>
											<td>
											<%
												HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
												HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
												UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
											 %>
											     <logic:equal value="<%=AppConstants.OPEN %>" name="TaskList" property="taskStatus">
												   <logic:equal value="<%= objUserDto.getUserRoleId() %>" name="TaskList" property="ownerTypeId">
												   <logic:equal value="<%= objUserDto.getUserRoleId() %>" name="formBean" property="orderOwner">
												   
														<input type="button" onClick="return popTaskStatus(<bean:write name='TaskList' property='taskID'/>,'<bean:write name='TaskList' property='ownerType'/>')" value="Notes">&nbsp;
														<input type="button" id="btnTaskAction" onClick="return popTaskActionChangeOrder(<bean:write name='TaskList' property='taskID'/>,<bean:write name='TaskList' property='ownerTypeId'/>)" value="Action">
												
													
													<!-- Changes Done for Role Sales Coordinator Workflow by Vishwa:: 17-Apr-2012 :: -->
													<%
														if(objUserDto.getUserRoleId().equalsIgnoreCase("1") || objUserDto.getUserRoleId().equalsIgnoreCase("10090"))
														{
														String ispmpresent = String.valueOf(request.getAttribute("ispmpresent"));
														Utility.SysErr("From JSP Page-->"+ ispmpresent);
														if(ispmpresent.equalsIgnoreCase("1"))
														 {
													%>
													<!-- Changes Made By Sumit For PM to be Displayed only in Case of PM Present in Workflow :: 20-Oct-2011 :: -->
													
													 <fieldset class="border1">
															<legend> <b>PM Name</b> </legend>
														  	
														  <html:select onfocus="getTip_DD(this,this.selectedIndex,this.name)" property='projectManagerAssigned' styleId='projectManagerAssigned' name="newOrderBean" styleClass='inputBorder1' style="width:220px;float:left;" >
												 		 
												 		 <html:optionsCollection name="projectManagerNameListAll" label="projectManager"  value="projectManagerID"></html:optionsCollection>
												       	</html:select>
														</fieldset>
													<% 
													}
													} %> 
													</logic:equal>
													<!-- Changes Made By Sumit For PM to be Displayed only in Case of PM Present in Workflow :: 20-Oct-2011 :: -->
													</logic:equal>	
												</logic:equal>	
											
											</td>
										</tr>
									</table>
								</td>
								<td>
									<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" readonly="true" class="inputBorderView1" id="txtTaskStartDate" name="taskStartDate" value="<bean:write name='TaskList' property='taskStartDate'/>">
								</td>
								<td>
									<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" readonly="true" class="inputBorderView1" id="txtTaskEndDate name="taskEndDate" value="<bean:write name='TaskList' property='taskEndDate'/>">
								</td>
							</tr>
							</logic:notEqual>
							</logic:iterate>	
						</logic:notEmpty>
					</logic:present>
					</table>
				</div>
			</fieldset>
			</td>
		</tr>
		<tr>
			<td valign="top">
			<fieldset class="border1" >
				<legend> <b>Notes</b> </legend>
				<div id="displayNotes" class="scrollWithAutoScroll" style="height:100px; width:100%;">
					<table  border="1" id="tabNotes" cellspacing="0" cellpadding="0" align="center" width="100%" class="tab2">
						<tr class="grayBg">
							<td nowrap align="left" width="25%">Notes Type</td>
							<td nowrap align="left" width="35%">Meaning</td>
							<td nowrap align="left" width="20%">Created By</td>
							<td nowrap align="left">Creation Date</td>
							<td nowrap align="left">Delete</td>
						</tr>
					</table>
				</div>
			</fieldset>
			</td>
		</tr>
		
		</table>
	</td>
</tr>
</table>
</html:form>
</body>
</html>
		