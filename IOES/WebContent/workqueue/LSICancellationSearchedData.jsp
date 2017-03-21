<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@page import="com.ibm.ioes.forms.LSICancellationDto"%>

<table border="0" cellpadding="0" cellspacing="0" class='content1' id='IdtableServices'>
<%int nSNo=0;%>
<logic:empty name="formBean" property="eligibleLSIForCancelList">
 <% String fromPageSubmit=request.getParameter("fromPageSubmit");
if(("1").equalsIgnoreCase(fromPageSubmit)){%>
<tr class="normal">
	<td colspan="10" align="center" style="text-align: center;color: red;font-size: small;font-weight: bold;">No Record Found</td>
</tr>
<%}%> 
</logic:empty>
<logic:notEmpty name="formBean" property="eligibleLSIForCancelList">
<logic:iterate id="row"	name="formBean" property="eligibleLSIForCancelList" indexId="recordId">
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
	<td class='inner col1' align="center"><%=nSNo%></td>
	<%if(nSNo==1){%>
		<input type="hidden" name="hiddenMaxPageno"  id="hiddenMaxPageno" value="<bean:write name="row" property="maxPageNo" />">
	<%}%>
	<td class='inner col1' align="center"><input name="chkSelectService" id="chkSelectService<%=nSNo%>" value="<bean:write name="row" property="searchServiceNo" />" type="checkbox"></td>
	<td class='inner col2' align="center">
		<a href="javascript:fnViewOrder('<bean:write name="row" property="searchCRMOrder" />');">
				<font color="Brown">                       
					<bean:write name="row" property="searchCRMOrder" />
				</font>
		</a>
	</td>
	<td class='inner col2' align="center"><input id="LSI<%=nSNo-1%>" name="LSI" readonly="true" value="<bean:write name='row' property='searchLSI' />" >&nbsp;</td>
	<td class='inner col2' align="center"><bean:write name='row' property='searchServiceNo' />&nbsp;</td>
	<td class='inner col3' align="center"><bean:write name='row' property='serviceName' />&nbsp;</td>

	<td class='inner col5' align="center" id ="combo">
		<select id="cancelReasonDD<%=nSNo-1%>" name="eligibleLSIForCancelList" style="background:#FFFF99;width:175px;" property="cancelllationReasonList">
			<option value="0">Select Reason </option>&nbsp; 
			<logic:iterate id="row2" name="row" property="cancelllationReasonList">
					<option name="<bean:write name="row2" property="reasonName"/>" value="<bean:write name="row2" property="reasonID"/>"><bean:write name="row2" property="reasonName"/> </option>&nbsp;
			</logic:iterate>
		</select>
	</td>

	<td class='inner col3' align="center"><textarea name="cancelRemarks" property="cancelServiceRemarks" onkeypress="return (this.value.length < 500);" rows ="1" id="cancelRemarks<%=(nSNo-1)%>">
	</textarea>&nbsp;</td>
	
	<td class='inner col3' align="center"><bean:write name='row' property='searchAccountName' />&nbsp;</td>
	<td class='inner col2' align="center"><bean:write name='row' property='productName' />&nbsp;</td>
	<td class='inner col2' align="center"><bean:write name='row' property='subProductName' />&nbsp;</td>
	
	<td class='inner col2' align="center"><bean:write name='row' property='ordType' />&nbsp;</td>
	<td class='inner col2' align="center"><bean:write name='row' property='ordStage' />&nbsp;</td>
	<td class='inner col3' align="center"><bean:write name='row' property='ordChangeType' />&nbsp;</td>
	<td class='inner col3' align="center"><bean:write name='row' property='ordSubChangeType' />&nbsp;</td>
	
	<td class='inner col4' colspan="2" align="right"><bean:write name='row' property='search_ord_create_date' />&nbsp;</td>
	<%-- <td class='inner col4' colspan="2" align="right"><bean:write name='row' property='search_ord_create_date' />&nbsp;</td> --%>
	
	<td class='inner col2' align="center"><bean:write name='row' property='searchAccountNo' />&nbsp;</td>
	
</tr> 

</logic:iterate>
</logic:notEmpty>
</table>