<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<html>
<head>
<title>:: Incomplete Orders: Integrated Order Entry System</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="./gifs/style.css" type="text/css" rel="stylesheet">
<script language="javascript">
function goToHome()
{
	
	 document.forms[0].action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	 document.forms[0].submit();
}

function fnViewOrder(orderNo)
{
	 // document.forms[0].checkedOrderNo.value=orderNo;
      document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
      document.forms[0].submit();
}
</script>
</head>
<body>
<html:form action="/ChangeOrderAction" enctype="text/plain">
<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
<tr align="right">
	<td><img src="./gifs/top/home.gif" onClick="goToHome();"></img></td>
</tr>
<tr>
	<td>&nbsp;</td>
</tr>
<tr align="left">
	<td class="head">&nbsp;Incomplete Orders</td>
</tr>

<tr>

    <td>
	  <table border="0" align="center" cellpadding="0" cellspacing="0" class="tab2" width="98.5%" height="650px;" >
		<tr class="lightBgWihtoutHover">
			<td width="80%" align="left" valign="top">
				<!--  Table for Order Required Approval From AM -->
				<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tab2" id="tblAMApprovalOrder" style="display:block;" >
				<tr align="center">
				     <td align="center">
						S.No.
					</td>
					<td align="center">
						CRM Order No
					</td>
					<td align="center">
						Order Type
					</td>
					<td align="center">
						Order Date
					</td>
					<td align="center">
						Source Name
					</td>
					<td align="center">
						Quote Number
					</td>
					<td align="center">
						Currency 
					</td>
					<td align="center">
						Stage Name
					</td>
				</tr>
				<%int nSNo=0;%>
				<logic:present name="incompleteOrderList" scope="request">
					<logic:empty name="incompleteOrderList">
					<tr align="center">
						<td colspan="12">
							<b>No Universal Work Queue Exists for Logged User</b>
						</td>
					</tr>
					</logic:empty>
				<logic:notEmpty name="incompleteOrderList">
				<logic:iterate name="incompleteOrderList" id="List" indexId="recordId">
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
					nSNo++;					
				%>				
				<tr class="<%=classType%>">
					<td align="center"><%=nSNo%></td>
					<td align="center">						
						<a href="javascript:fnViewOrder('<bean:write name="List" property="poNumber" />');">
							<font color="Brown">                       
								<bean:write name="List" property="poNumber" />
							</font>
						</a>
					</td>
					<td nowrap="nowrap" align="center">
						<bean:write  name="List" property="orderType" />
					</td>
					<td nowrap="nowrap" align="center">
						<bean:write name="List" property="orderDate" />
					</td>
					<td>
						<bean:write name="List" property="sourceName" />
					</td>
					<td nowrap="nowrap" align="center">
						<bean:write name="List" property="quoteNo" />
					</td>
					<td nowrap="nowrap" align="center">
						<bean:write name="List" property="currencyName" />
					</td >
					<td nowrap="nowrap" align="center">
						<bean:write name="List" property="stageName" />
					</td>
				</tr>
				</logic:iterate>	
				</logic:notEmpty>
				</logic:present>
				
				</table>
	  	   </td>
		</tr>
		</table>
   </td>
</tr>
</table>
</html:form>
</body>
</html>
