<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@page import="java.util.ArrayList;"%>
<link href="<%=request.getContextPath()%>/gifs/style.css" type="text/css" rel="stylesheet">
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="57%" valign="top">
			<fieldset class="border1" >
				<legend> <b>Extended Attributes</b> </legend>
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="50%" align="center">Label Name</td>
						<td  width="50%" align="center">Value</td>
					</tr>
				</table>
				<div class="scroll" >
					<table width="96%"  border="1" cellspacing="1" cellpadding="2">
						<%int sno=0; %>
						<logic:present name="MainDetailsWithAttributesBean" scope="request">
							<input type="hidden" name="updateType" value="2">
							<% ArrayList getData1=(ArrayList)request.getAttribute("MainDetailsWithAttributesBean");%>
							<logic:notEmpty name="MainDetailsWithAttributesBean" scope="request">
								<logic:iterate id="row1"  name="MainDetailsWithAttributesBean" indexId="index_report">
									<%sno=sno+1; %>
									<tr>
										<td width="50%"><bean:write name="row1" property="attributeLabel"/></td>
										<td>
											<input type="text" width="100%" class="inputBorder1" style="inputBorder4" name="AttributeVal_<%=sno %>" value="<bean:write name="row1" property="attributeValue"/>"
											<%if (((NewOrderDto)getData1.get(index_report.intValue())).getExpectedValue().equalsIgnoreCase("numeric")) {%>onblur="if(this.value.length > 0){return checknumber(this)}" <% } %>
												<%if (((NewOrderDto)getData1.get(index_report.intValue())).getExpectedValue().equalsIgnoreCase("datetime") ) {%>onblur="if(this.value.length > 0){return checkdate(this)}" <% } %>
												<%if (((NewOrderDto)getData1.get(index_report.intValue())).getAlisName().equalsIgnoreCase("email")) {%>onblur="if(this.value.length > 0){return emailValidate(this)}" <% } %>												
												<%if (((NewOrderDto)getData1.get(index_report.intValue())).getAlisName().equalsIgnoreCase("YN"))  {%>onblur="if(this.value.length > 0){return YN(this)}" <% } %>/>
											 <%if (((NewOrderDto)getData1.get(index_report.intValue())).getExpectedValue().equalsIgnoreCase("datetime") ) {%>
												<font size="1">
													<a href="javascript:show_calendar('forms[0].AttributeVal_<%=sno %>');" class="borderTabCal"></a>
											</font>
											
											 <%}%>
											
											<input type="hidden" name="hdnAttributeID_<%=sno %>" value="<bean:write name="row1" property="attributeID"/>"/>
											<input type="hidden" name="hdnAttributeDataType_<%=sno %>" value="<bean:write name="row1" property="dataType"/>"/>
											<input type="hidden" name="hdnAttributeName_<%=sno %>" value="<bean:write name="row1" property="attributeLabel"/>"/>
											<input type="hidden" name="hdnAttributeExpectedValue_<%=sno %>" value="<bean:write name="row1" property="expectedValue"/>"/>
											<input type="hidden" name="Mandatory_<%=sno %>" value="<bean:write name="row1" property="mandatory"/>"/>
										</td>
									</tr>

								</logic:iterate>
							</logic:notEmpty>
						</logic:present>
					</table>
					<input type="hidden" name="attCount" value="<%=sno %>">
				</div>
			</fieldset>
		</td>
		<td width="43%" valign="top" align="left">
			<logic:present name="accountDetailsBean" scope="request">
					<logic:notEmpty name="accountDetailsBean" scope="request">
						<logic:iterate id="row1"  name="accountDetailsBean" indexId="index_report">
							<fieldset class="border1" style="width:60%">
								<legend> <b>Region Details</b> </legend>
								<table width="96%"  border="1" cellspacing="1" cellpadding="2">
									<tr>
										<td width="41%"><b>Region</b></td>
										<td width="59%">
											<html:select disabled="true" name="PMBean" property="regionId" styleId="region" styleClass="inputBorder1" style="width:140px;float:left;" onchange="fnGetZoneList();">
											<html:option value="0">Select a Region</html:option>
											<html:optionsCollection name="listRegion" value="regionId" label="regionName"/>	
											</html:select>
										</td>
									</tr>
									<tr>
										<td width="41%"><b>Zone</b></td>
										<td width="59%">
											<html:select disabled="true" name="PMBean" property="zoneId"  styleId="zone" styleClass="inputBorder1"  style="width:140px;float:left;">
											<html:option value="0">Select a Zone</html:option>
											</html:select>
											<html:hidden name="PMBean" property="zoneId" styleId="zone_1"  ></html:hidden>									
										</td>										
									</tr>
								</table>
							</fieldset>
							<fieldset class="border1" style="width:60% ">
								<legend> <b>Sales Person Details</b> </legend>
								<table width="96%"  border="1" cellspacing="1" cellpadding="2">
									<tr>
										<td width="41%"><b>First Name</b></td>
										<td width="59%"><html:text property="spFirstname" styleClass="inputBorder1" name="row1" style="width:100px;" readonly="true"></html:text></td>
									</tr>
									<tr>
										<td width="41%"><b>Last Name</b></td>
										<td width="59%"><html:text property="spLastName" styleClass="inputBorder1" name="row1" style="width:100px;" readonly="true"></html:text></td>
									</tr>
									<tr>
										<td width="41%"><b>Phone No</b></td>
										<td width="59%"><html:text property="spLPhno" styleClass="inputBorder1" name="row1" style="width:100px;" readonly="true"></html:text></td>
									</tr>
									<tr>
										<td width="41%"><b>Email ID</b></td>
										<td width="59%"><html:text property="spLEmail" styleClass="inputBorder1" name="row1" style="width:100px;" readonly="true"></html:text></td>
									</tr>
								</table>
							</fieldset>
						</logic:iterate>
					</logic:notEmpty>
				</logic:present>
		</td>
	</tr>
</table>

