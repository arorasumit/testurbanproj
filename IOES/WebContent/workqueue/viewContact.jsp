<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top">
		<fieldset class="border1"><legend> <b>Contacts</b></legend>
		<div class="scrollWithAutoScroll" style="height:180px;width:945px;">
		<table border="1" cellspacing="0" cellpadding="0" align="center" width="100%" id='tableContact'>
			<%int count = 1;%>
			<tr>
				<td nowrap>S.No.</td>
				<td nowrap>Contact Type</td>
				<td nowrap>Salutation</td>
				<td nowrap>First Name</td>
				<td nowrap>Last Name</td>
				<td nowrap>Email</td>
				<td nowrap>Cell No</td>
				<td nowrap>Fax</td>
			</tr>
			<logic:present name="listContactDetails" scope="request">
				<logic:notEmpty name="listContactDetails">
					<logic:iterate id="contactDetailID" name="listContactDetails" indexId="recordId">
						<%count = ((recordId.intValue()) + 1);%>
						<tr>
							<td nowrap><input type="text" class="inputBorder4" value="<%=recordId.intValue()+1 %>" /> 
							<input type="hidden" name="contactId<%=count %>" class="inputBorder4" value="<bean:write name="contactDetailID" property="contactId"/>" /></td>
							<td nowrap ><input type="text" id="contactType<%=count %>" name="contactType<%=count %>" class="inputBorder4" value="<bean:write name="contactDetailID" property="contactType"/>" readonly="readonly" /></td>
							<td nowrap><input type="text" id="saluation<%=count %>" name="saluation<%=count %>" class="inputBorder4" value="<bean:write name="contactDetailID" property="saluation"/>" /></td>
							<td nowrap><input type="text" id="firstName<%=count %>" name="firstName<%=count %>" class="inputBorder4" value="<bean:write name="contactDetailID" property="firstName"/>" /></td>
							<td nowrap><input type="text" id="lastName<%=count %>"  name="lastName<%=count %>" class="inputBorder4"  value="<bean:write name="contactDetailID" property="lastName"/>" /></td>
							<td nowrap><input type="text" id="cntEmail<%=count %>"  name="cntEmail<%=count %>" class="inputBorder4" value="<bean:write name="contactDetailID" property="cntEmail"/>" /></td>
							<td nowrap><input type="text" id="contactCell<%=count %>" name="contactCell<%=count %>" class="inputBorder4" value="<bean:write name="contactDetailID" property="contactCell"/>" /></td>
							<td nowrap><input type="text" id="contactFax<%=count %>" name="contactFax<%=count %>" class="inputBorder4" value="<bean:write name="contactDetailID" property="contactFax"/>" /></td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
				<logic:empty name="listContactDetails" >
					<tr>
						<td colspan="8" align="center">
							No Records Found
						</td>
					</tr>
				</logic:empty>
			</logic:present>
		</table>
		</div>
		</fieldset>
		</td>
	</tr>
	<tr>
		<td valign="top">
		<fieldset class="border1"><legend> <b>Address</b></legend>
		<div class="scrollWithAutoScroll" style="height:100px; width:945px;">
			<table border="1" cellspacing="0" cellpadding="0" align="center" width="100%" id="AddAddress">
				<tr>
					<td nowrap>S.No.</td>
					<td nowrap>Address1</td>
					<td nowrap>Address2</td>
					<td nowrap>Address3</td>
					<td nowrap>City Name</td>
					<td nowrap>State Name</td>
					<td nowrap>Country Name</td>
					<td nowrap>Pin</td>
				</tr>
				<logic:present name="listAddressDetails" scope="request">
					<logic:notEmpty name="listAddressDetails">
						<logic:iterate id="addressDetailID" name="listAddressDetails" indexId="recordId">
							<tr>
								<%	count = ((recordId.intValue()) + 1);%>
								<td nowrap><input type="text" class="inputBorder4" value="<%=recordId.intValue()+1 %>" /> 
								 <input type="hidden" name="addID<%=count%>" class="inputBorder4" value="<bean:write name="addressDetailID" property="addID"/>" /></td>
								<td><input type="text" id="address1<%=count%>" name="address1<%=count %>" class="inputBorder4" value="<bean:write name="addressDetailID" property="address1"/>" /></td>
								<td><input type="text" id="address2<%=count%>" name="address2<%=count %>" class="inputBorder4" value="<bean:write name="addressDetailID" property="address2"/>" /></td>
								<td><input type="text" id="address3<%=count%>" name="address3<%=count %>" class="inputBorder4" value="<bean:write name="addressDetailID" property="address3"/>" /></td>
								<td><input type="text" id="cityName<%=count%>" name="cityName<%=count %>" class="inputBorder4" value="<bean:write name="addressDetailID" property="cityName"/>" /></td>
								<td><input type="text" id="stateName<%=count%>" name="stateName<%=count %>" class="inputBorder4" value="<bean:write name="addressDetailID" property="stateName"/>" /></td>
								<td><input type="text" id="countyName<%=count%>" name="countyName<%=count %>" class="inputBorder4" value="<bean:write name="addressDetailID" property="countyName"/>" /></td>
								<td><input type="text" id="pinNo<%=count%>"	name="pinNo<%=count %>" class="inputBorder4" value="<bean:write name="addressDetailID" property="pinNo"/>" />
								</td>
							</tr>
						</logic:iterate>
					</logic:notEmpty>
					<logic:empty name="listAddressDetails" >
					<tr>
						<td colspan="8" align="center">
							No Records Found
						</td>
					</tr>
				</logic:empty>
				</logic:present>
			</table>
		</div>
		</fieldset>
	</td>
	</tr>
</table>

