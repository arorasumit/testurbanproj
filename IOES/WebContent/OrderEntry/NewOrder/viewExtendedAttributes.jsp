<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ROHIT VERMA	18-Feb-11	00-05422		Region and Zone will be fetched against an Account-->
<!--[012]	 Lawkush		3-March-11	00-05422		In order to make nom mandatory field white and mandatory yellow -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.forms.FieldAttibuteDTO"%>
<%@page import="java.util.ArrayList;"%>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
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
											<input type="text"  onmouseover='getTip(value)' onmouseout='UnTip()'width="100%"  class="inputBorder1" isRequired="0" id="AttributeVal_<%=sno %>" name="AttributeVal_<%=sno %>" value="<bean:write name="row1" property="attributeValue"/>"
											<%if (((FieldAttibuteDTO)getData1.get(index_report.intValue())).getExpectedValue().equalsIgnoreCase("numeric")) {%>maxlength="15" onblur="if(this.value.length > 0){return checknumber(this)}" <% } %>
												<%if (((FieldAttibuteDTO)getData1.get(index_report.intValue())).getExpectedValue().equalsIgnoreCase("datetime") ) {%> readonly="readonly" onblur="if(this.value.length > 0){return checkdate(this)}" <% }%>
												<%if (((FieldAttibuteDTO)getData1.get(index_report.intValue())).getExpectedValue().equalsIgnoreCase("email")) {%>maxlength="50" onblur="if(this.value.length > 0){return emailValidate(this)}" <% } %>												
												<%if (((FieldAttibuteDTO)getData1.get(index_report.intValue())).getExpectedValue().equalsIgnoreCase("YN"))  {%>maxlength="5" onblur="if(this.value.length > 0){return YN(this)}" <% } %>/>
											
											<input type="hidden" name="hdnAttributeID_<%=sno %>" value="<bean:write name="row1" property="attributeID"/>"/>
											<input type="hidden" name="hdnAttributeDataType_<%=sno %>" value="<bean:write name="row1" property="dataType"/>"/>
											<input type="hidden" name="hdnAttributeName_<%=sno %>" value="<bean:write name="row1" property="attributeLabel"/>"/>
											<input type="hidden" name="hdnAttributeExpectedValue_<%=sno %>" value="<bean:write name="row1" property="expectedValue"/>"/>
											<input type="hidden" name="Mandatory_<%=sno %>" value="<bean:write name="row1" property="mandatory"/>"/>
											<!-- Start[012]-->
											<input type="hidden" name="IsRequired_<%=sno %>" />
											<!-- End[012] -->
										</td>
									</tr>

								</logic:iterate>
							</logic:notEmpty>
						</logic:present>
						
						<logic:notPresent name="MainDetailsWithAttributesBean" scope="request">
							<input type="hidden" name="updateType" value="1">
							<% ArrayList getData=(ArrayList)request.getAttribute("MainDetailsBean");%>
							<logic:notEmpty name="MainDetailsBean" scope="request">
								<logic:iterate id="row1"  name="MainDetailsBean" indexId="index1">
									<%sno=sno+1; %>
									<tr>
										<td width="50%"><bean:write name="row1" property="attributeLabel"/></td>
										<td>
											<input type="text"  onmouseover='getTip(value)' onmouseout='UnTip()'width="100%" class="inputBorder1" id="AttributeVal_<%=sno %>" isRequired="0" name="AttributeVal_<%=sno %>" 
											<%if (((FieldAttibuteDTO)getData.get(index1.intValue())).getExpectedValue().equalsIgnoreCase("numeric")) {%>maxlength="15" onblur="if(this.value.length > 0){return checknumber(this)}" <% } %>
												<%if (((FieldAttibuteDTO)getData.get(index1.intValue())).getExpectedValue().equalsIgnoreCase("datetime") ) {%> readonly="readonly"  onblur="if(this.value.length > 0){return checkdate(this)}" <% } %>
												<%if (((FieldAttibuteDTO)getData.get(index1.intValue())).getExpectedValue().equalsIgnoreCase("email")) {%>maxlength="50" onblur="if(this.value.length > 0){return emailValidate(this)}" <% } %>												
												<%if ((((FieldAttibuteDTO)getData.get(index1.intValue())).getExpectedValue().equalsIgnoreCase("YN")))  {%>maxlength="5" onblur="if(this.value.length > 0){return YN(this)}" value="N"<% } %> />
											
											<input type="hidden" name="hdnAttributeID_<%=sno %>" value="<bean:write name="row1" property="attributeID"/>"/>
											<input type="hidden" name="hdnAttributeDataType_<%=sno %>" value="<bean:write name="row1" property="dataType"/>"/>
											<input type="hidden" name="hdnAttributeName_<%=sno %>" value="<bean:write name="row1" property="attributeLabel"/>"/>
											<input type="hidden" name="hdnAttributeExpectedValue_<%=sno %>" value="<bean:write name="row1" property="expectedValue"/>"/>
											<input type="hidden" name="Mandatory_<%=sno %>" value="<bean:write name="row1" property="mandatory"/>"/>
											<!-- start[012]-->
											<input type="hidden" name="IsRequired_<%=sno %>" />
											<!-- end[012] -->
										</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</logic:notPresent>
					</table>
					<input type="hidden" name="attCount" value="<%=sno %>">
				</div>
			</fieldset>
		</td>
		<logic:present name="accountDetailsBean" scope="request">
		 <logic:notEmpty name="accountDetailsBean">
		  <logic:iterate id="row1"  name="accountDetailsBean" indexId="index_report">
		   
		    <td width="43%" valign="top" align="left">
			  <fieldset class="border1" style="width:60%">
				<legend> <b>Region Details draft</b> </legend>
					<table width="96%"  border="1" cellspacing="1" cellpadding="2">
						<tr>
						    <td width="41%"><b>Region</b></td>
						    <!-- [001 Start -->
							<td width="59%"><html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' property="region" name= "row1" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>
							<!-- [001 End -->
						</tr>
						<tr>
							<td width="41%"><b>Zone</b></td>
							<!-- [001 Start -->
							<td width="59%"><html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' property="zone" name="row1" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>
							<!-- [001 End -->
						</tr>
					<% String subChangeTypeId=request.getAttribute("subChangeTypeId").toString();
						if(!subChangeTypeId.equalsIgnoreCase("0")){%>
						<tr>
							<td width="41%"><b>Change Type</b></td>
							<td width="59%">
							
							<html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="changeTypeName" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>
							<input type="hidden" name="changeType" id="HdnChangeTypeID" value="<bean:write name="row1" property="changeTypeId"/>">
						</tr>						
						<tr>
							<td width="41%"><b>Sub Change Type</b></td>
							<td width="59%">
							
							<html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="subChangeTypeName" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>
							<input type="hidden" name="subChangeType" id="hdnSubChangeTypeID" value="<bean:write name="row1" property="subChangeTypeId"/>">
							<input type="hidden" name="subChangeType" id="subChangeTypeID" value="<bean:write name="row1" property="subChangeTypeId"/>">
						</tr>
						<%}%>	
					</table>
			</fieldset>
			<fieldset class="border1" style="width:60% ">
				<legend> <b>Sales Person Details</b> </legend>
				<table width="96%"  border="1" cellspacing="1" cellpadding="2">
					<tr>
						<td width="41%"><b>First Name</b></td>
						<td width="59%"><html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="spFirstname" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>
					</tr>
					<tr>
						<td width="41%"><b>Last Name</b></td>
						<td width="59%"><html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="spLastName" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>
					</tr>
					<tr>
						<td width="41%"><b>Phone No</b></td>
						<td width="59%"><html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="spLPhno" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>
					</tr>
					<tr>
						<td width="41%"><b>Email ID</b></td>
						<td width="59%"><html:text onmouseover='getTip(value)' onmouseout='UnTip()' onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="spLEmail" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>
					</tr>
				</table>
			</fieldset>
		  </td>
		
		</logic:iterate>
		</logic:notEmpty>
		</logic:present>
		<logic:notPresent name="accountDetailsBean" scope="request">
		  
		    <td width="43%" valign="top" align="left">
			  <fieldset class="border1" style="width:60%">
				<legend> <b>Region Details New Order</b> </legend>
					<table width="96%"  border="1" cellspacing="1" cellpadding="2">
						<tr>
							<td width="41%"><b>Region</b></td>
							<!-- [001] Start -->
							<td width="59%"><html:text onmouseover='getTip(value)' onmouseout='UnTip()' name="row1" property="region" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>
							<!--[001]	End-->
						</tr>
						<tr>
							<td width="41%"><b>Zone</b></td>
							<!-- [001 Start -->
							<td width="59%"><html:text onmouseover='getTip(value)' onmouseout='UnTip()' property="zone" name="row1" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>
							<!-- [001 End -->
						</tr>
					</table>
			</fieldset>
			<fieldset class="border1" style="width:60% ">
				<legend> <b>Sales Coordinator Details</b> </legend>
				<table width="96%"  border="1" cellspacing="1" cellpadding="2">
					<tr>
						<td width="41%"><b>First Name</b></td>
						<td width="59%"><html:text onmouseover='getTip(value)' onmouseout='UnTip()' property="spFirstname" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>
					</tr>
					<tr>
						<td width="41%"><b>Last Name</b></td>
						<td width="59%"><html:text onmouseover='getTip(value)' onmouseout='UnTip()' property="spLastName" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>
					</tr>
					<tr>
						<td width="41%"><b>Phone No</b></td>
						<td width="59%"><html:text onmouseover='getTip(value)' onmouseout='UnTip()' property="spLPhno" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>
					</tr>
					<tr>
						<td width="41%"><b>Email ID</b></td>
						<td width="59%"><html:text onmouseover='getTip(value)' onmouseout='UnTip()' property="spLEmail" styleClass="inputDisabled" style="width:100px;" readonly="true"></html:text></td>
					</tr>
				</table>
			</fieldset>
		  </td>
		
		</logic:notPresent>
	</tr>
</table>

