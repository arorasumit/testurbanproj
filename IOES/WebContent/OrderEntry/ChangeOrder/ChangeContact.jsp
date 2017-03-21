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
		<fieldset class="border1"><legend> <b>Contacts</b>
		</legend>
			<!--<div class="scrollWithAutoScroll" style="height:180px;width:945px;">-->
			<table border="1" cellspacing="0" cellpadding="0" align="center"
			width="100%" id='selecttable'>
			
			<tr style="font-weight: bold;">
			<td width="11%">Select All
					        <input type="checkbox" name="SelectAllChckContact" id="SelectAllChckContact" onclick="javascript:fnCheckContactAll();"/>
					         </td>
					         <td width="62%">
					         <div class="searchBg1"><a href="#" alt="select contact type" title="Select Contact" onClick="return popContact()">...</a></div>
					         </td>
			</tr>
			</table>
		<table border="1" cellspacing="0" cellpadding="0" align="center"
			width="100%" id='tableContact'>

			<%
			int count = 1;
			%>
			<tr style="font-weight: bold;">
				<td nowrap width="50" align="center">S.No.</td>
				<td nowrap width="50" align="center">&nbsp;</td>
				<td nowrap width="100" align="center">Contact Type</td>
				<td nowrap width="100" align="center">Salutation</td>
				<td nowrap width="100" align="center">First Name</td>
				<td nowrap width="100" align="center">Last Name</td>
				<td nowrap width="100" align="center">Email</td>
				<td nowrap width="100" align="center">Cell No</td>
				<td nowrap width="100" align="center">Fax</td>
			</tr>
			<logic:present name="listContactDetails" scope="request">
				<logic:notEmpty name="listContactDetails">
					<html:hidden property="contactUpdateType" value="2" />
					<logic:iterate id="contactDetailID" name="listContactDetails"
						indexId="recordId">
						<%
						count = ((recordId.intValue()) + 1);
						%>
						<tr id="row<%=count %>">
						    <td nowrap><%=recordId.intValue()+1 %> 	</td>
						    
						    <td width="1"><input name="chkSelectContactDetail" id="chkSelectContactDetail<%=count-1%>" 
						    	type="checkbox" value="<%=count-1 %>" style="width:20px; float:left;" onclick="unCheckedContactMaster();">
								<input type="hidden" name="contactId" id="contactId" size="20" 
									class="inputBorder4" value="<bean:write name="contactDetailID" 
									property="contactId"/>" /></td>
							
							<td nowrap>
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" 
								name="contactType"  id="contactType<%=count-1%>" isRequired="1"  
								value="<bean:write name="contactDetailID" property="contactType"/>" 
								readonly="true" style="width:90px; float:left;" 
								onchange="autoFillContactType('<%=count-1 %>');" 
								onkeydown="if (event.keyCode == 13)  return autoFillContactType('<%=count-1 %>');" />
								
								<input type="hidden" name="contactTypeId"  id="contactTypeId<%=count-1%>" 
								value="<bean:write name="contactDetailID" property="contactTypeId"/>"/>
								
								<div class="searchBg1" align="left">
									<a	href="#" onclick="return popContactType('<%=count-1%>')">...</a>
								</div>
							</td>
							
							<td nowrap>
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" 
								name="salutationName"  id="salutationName<%=count-1%>" isRequired="1"  
								value="<bean:write name="contactDetailID" property="salutationName" />" 
								readonly="true" style="width:50px; float:left;" size="113" 
								onchange="autoFillSalutation('<%=count-1 %>');" 
								onkeydown="if (event.keyCode == 13)  return autoFillSalutation('<%=count-1 %>');"  />
								
								<input type="hidden" name="salutationId"  id="salutationId<%=count-1%>" 
								value="<bean:write name="contactDetailID" property="salutationName"/>"   />
								
								<div class="searchBg1" align="left">
									<a	href="#" onclick="return popSalutationList('<%=count-1%>')">...</a>
								</div>
							</td>
							
							<td nowrap><input onmouseover='getTip(value)' onmouseout='UnTip()' 
								type="text" name="firstName" id="firstName" style="width:100px; float:left;" 
								isRequired="1"  class="inputBorder1" maxlength="100" 
								onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'First Name')};" 
								value="<bean:write name="contactDetailID" property="firstName"/>" /></td>
							
							<td nowrap><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" name="lastName" 
								id="lastName"  style="width:100px; float:left;" isRequired="1"  class="inputBorder1"  
								maxlength="100" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Last Name')};" 
								value="<bean:write name="contactDetailID" property="lastName"/>" /></td>
							
							<td nowrap><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" name="cntEmail" 
								id="cntEmail" style="width:100px; float:left;" isRequired="0"   class="inputBorder1" 
								maxlength="255" onBlur="if(this.value.length > 0){return emailValidate(this)}" 
								value="<bean:write name="contactDetailID" property="cntEmail"/>" /></td>
							
							<td nowrap><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" 
								name="contactCell" id="contactCell"  style="width:90px; float:left;" isRequired="1" 
								maxlength="50" onBlur="cellValidation(this);" 
								value="<bean:write name="contactDetailID" property="contactCell"/>" maxlength="50"/></td>
							
							<td nowrap><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" 
								name="contactFax" id="contactFax" isRequired="0"  style="width:90px; float:left;" 
								onBlur="if(this.value.length > 0){return checknumber(this)}" 
								value="<bean:write name="contactDetailID" property="contactFax"/>"  maxlength="15" /></td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
			</logic:present>
			<logic:empty name="listContactDetails">
				<html:hidden property="contactUpdateType" value="1" />
				<tr id="row<%=count %>">
					<td nowrap><%=count%> </td>
					
					<td width="1"><input type="hidden" class='inputBorder1' name="contactId" id="contactId" value="0">
						<input name="chkSelectContactDetail" id="chkSelectContactDetail<%=count-1%>" type="checkbox" 
						value="<%=count-1%>" style="width:20px; float:left;" onclick="unCheckedContactMaster();"></td>
					
					<td ><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text"  id="contactType<%=count-1 %>" 
						isRequired="0" style="width:90px; float:left;" name="contactType" onchange="autoFillContactType('<%=count-1 %>');" 
						onkeydown="if (event.keyCode == 13)  return autoFillContactType('<%=count-1 %>');" />
					    <input type="hidden"  id="contactTypeId<%=count-1 %>"  name="contactTypeId" readonly="readonly" />
						<div class="searchBg1">
							<a href="#" title="Select Contact" onClick="return popContactType('<%=count-1 %>')">...</a>
						</div></td>
					
					<td nowrap>
						<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" name="salutationName"  
						id="salutationName<%=count-1 %>" isRequired="0"  value=""  style="width:50px; float:left;" 
						size="113"  onchange="autoFillSalutation('<%=count-1 %>');" 
						onkeydown="if (event.keyCode == 13)  return autoFillSalutation('<%=count-1 %>');"  />
						<input type="hidden" name="salutationId"  id="salutationId<%=count-1 %>" value=""   />
						<div class="searchBg1" align="left">
							<a	href="#" title="Select Salutation" onclick="return popSalutationList('<%=count-1%>')">...</a>
						</div></td>
					
					<td nowrap><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text"  id="firstName" 
						style="width:100px; float:left;" isRequired="1"  name="firstName" maxlength="100" 
						onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'First Name')};"></td>
					
					<td nowrap><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text"  id="lastName" 
						style="width:100px; float:left;" isRequired="1"  name="lastName" maxlength="100" 
						onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Last Name')};"></td>
					
					<td nowrap><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text"  id="cntEmail" 
						style="width:100px; float:left;" isRequired="0"  name="cntEmail" maxlength="255" 
						onBlur="if(this.value.length > 0){return emailValidate(this)}"></td>
					
					<td nowrap><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text"  id="contactCell" 
						isRequired="1" style="width:90px; float:left;" name="contactCell" value=""  maxlength="50"	
						onBlur="cellValidation(this);"></td>
					
					<td nowrap><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text"  id="contactFax" 
						isRequired="0" name="contactFax" style="width:90px; float:left;" value=""  maxlength="15" 
						onBlur="if(this.value.length > 0){return checknumber(this)}">
					</td>
				</tr>
			</logic:empty>
		</table>
			<!--</div>-->
		</fieldset>
		</td>
	</tr>
	<tr>
		<td colspan="8" align="center"><input type="button"	name="btnAddContact" value="Add More Contact" onClick="AddContact()">
		<input type="button" name="btnDeleteContactDetail" value="Delete Contact Detail" onClick="DeleteContact()">
		<input type="hidden" value="<%=count%>" name="hdnContactCount">
		<input type="hidden" value="0" id="hdnCheckTab">
		</td>
	</tr>
	<tr>
		<td valign="top">
		<fieldset class="border1"><legend> <b>Address</b>
		</legend>
			<!--<div class="scrollWithAutoScroll" style="height:100px; width:945px;">-->
		<table border="1" cellspacing="0" cellpadding="0" align="center"
			width="100%" id="tableAddress">
			<tr style="font-weight: bold;">
				<td nowrap width="50" align="center">S.No.</td>
				<td nowrap width="130" align="center">Address1</td>
				<td nowrap width="100" align="center">Address2</td>
				<td nowrap width="100" align="center">Address3</td>
				<td nowrap width="100" align="center">City Name</td>
				<td nowrap width="100" align="center">State Name</td>
				<td nowrap width="100" align="center">Pin</td>
				<td nowrap width="100" align="center">Country Name</td>
			</tr>
			<logic:present name="listAddressDetails" scope="request">
				<logic:notEmpty name="listAddressDetails">
					<logic:iterate id="addressDetailID" name="listAddressDetails" indexId="recordId">
						<tr>
							<%	count = ((recordId.intValue()) + 1);%>
							<td nowrap><%=recordId.intValue()+1 %></td>
							
							<td><input type="hidden"   name="addID" id="addID" class="inputBorder4" 
								value="<bean:write name="addressDetailID" property="addID"/>" />
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text"  name="address1" 
								id="address1" style="width:100px; float:left;" isRequired="0"  class="inputBorder1" 
								maxlength="255" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Address1')};" 
								value="<bean:write name="addressDetailID" property="address1"/>" />
							</td>
							
							<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text"  name="address2" id="address2" 
							style="width:100px; float:left;" isRequired="0"  class="inputBorder2" maxlength="255" 
							onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Address2')};" 
							value="<bean:write name="addressDetailID" property="address2"/>" /></td>
							
							<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text"  name="address3" id="address3" 
							style="width:100px; float:left;" isRequired="0" class="inputBorder2" maxlength="255" 
							onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Address3')};" 
							value="<bean:write name="addressDetailID" property="address3"/>" /></td>
							
							<td nowrap>
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" name="cityName"  
								id="cityName<%=count-1 %>" isRequired="0" class="inputBorder4" 
								value="<bean:write name="addressDetailID" property="cityName" />" 
								readonly="true" style="width:90px; float:left;" size="113" onchange="$('#hdnCheckTab').val(0); autoFill('<%=count-1 %>');" 
								onkeydown="if (event.keyCode == 13) { $('#hdnCheckTab').val(0); return autoFill('<%=count-1 %>');}" />
								<input type="hidden" name="cityCode"  id="cityCode<%=count-1 %>" 
								value="<bean:write name="addressDetailID" property="cityCode"/>"   />
								<div class="searchBg1" align="left">
									<a	href="#" onclick="return popCityList('<%=count-1%>')">...</a>
								</div>
							</td>
							
							<td nowrap>
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" name="stateName"  
								id="stateName<%=count-1 %>" isRequired="0" class="inputBorder4" 
								value="<bean:write name="addressDetailID" property="stateName" />" readonly="true" 
								style="width:90px; float:left;" size="113" />
								<input type="hidden" name="stateCode"  id="stateCode<%=count-1 %>" 
								value="<bean:write name="addressDetailID" property="stateCode"/>"   />
								<div class="searchBg1" align="left">
									<a	href="#" onclick="return popStateList('<%=count-1%>')">...</a>
								</div>
							</td>
							
							<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text"  name="pinNo" id="pinNo<%=count-1 %>" 
								isRequired="0" style="width:90px; float:left;" class="inputBorder4" 
								value="<bean:write name="addressDetailID" property="pinNo"/>"  maxlength="15"/>
								<input type="hidden" name="pinCode"  id="pinCode<%=count-1 %>" 
								value="<bean:write name="addressDetailID" property="pinCode"/>"   />
								<div class="searchBg1" align="left">
									<a	href="#" onclick="return popPinList('<%=count-1%>')">...</a>
								</div>	
							</td>
							
							<td nowrap>
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" name="countyName"  
								id="countyName<%=count-1 %>" isRequired="0" class="inputBorder4" 
								value="<bean:write name="addressDetailID" property="countyName" />" readonly="true" 
								style="width:90px; float:left;" size="113" />
								<input type="hidden" name="countyCode"  id="countyCode<%=count-1 %>" 
								value="<bean:write name="addressDetailID" property="countyCode"/>"   />
								<div class="searchBg1" align="left">
									<a	href="#" onclick="return popCountryList('<%=count-1%>')">...</a>
								</div>
							</td>
						</tr>
					</logic:iterate>
				</logic:notEmpty>
			</logic:present>
			<logic:empty name="listAddressDetails">
				<tr>
					<td nowrap ><%=count%> </td>
					
					<td nowrap><input type="hidden" class='inputBorder4' name="addID"  id="addID" value="0">
						<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder1" id="address1" 
						name="address1" style="width:100px; float:left;" isRequired="0"  maxlength="255" 
						onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Address1')};"></td>
					
					<td nowrap><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder2" 
						id="address2" style="width:100px; float:left;" isRequired="0"  name="address2" maxlength="255" 
						onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Address2')};"></td>
					
					<td nowrap><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder2" 
						id="address3" style="width:100px; float:left;" isRequired="0"  name="address3" maxlength="255" 
						onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Address3')};"></td>
					
					<td nowrap>
						<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" name="cityName" 
						id="cityName<%=count-1 %>" isRequired="0" class="inputBorder4" value="" readonly="true" 
						style="width:90px; float:left;" size="113" onchange="autoFill('<%=count-1 %>');" 
						onkeydown="if (event.keyCode == 13)  return autoFill('<%=count-1 %>');"/>
						<input type="hidden" name="cityCode"  id="cityCode<%=count-1 %>" value=""   />
						<div class="searchBg1" align="left">
							<a	href="#"  title="Select City" onclick="return popCityList('<%=count-1 %>')">...</a>
						</div></td>				
					
					<td nowrap>
						<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" name="stateName"  
						id="stateName<%=count-1 %>" isRequired="0" class="inputBorder4" value="" readonly="true" 
						style="width:90px; float:left;" size="113" 
						onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Country')};"/>
						<input type="hidden" name="stateCode"  id="stateCode<%=count-1 %>" value=""   />
						<div class="searchBg1" align="left">
							<a	href="#" title="Select State"  onclick="return popStateList('<%=count-1 %>')">...</a>
						</div></td>
					
					<td nowrap>
						<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder4" 
						id="pinNo<%=count-1 %>" isRequired="0" style="width:90px; float:left;" name="pinNo" value=""  
						maxlength="15" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Pin No')};"/>
						<input type="hidden" name="pinCode"  id="pinCode<%=count-1 %>" value=""   />
						<div class="searchBg1" align="left">
							<a	href="#" title="Select Pin" onclick="return popPinList('<%=count-1 %>')">...</a>
						</div></td>
						
					<td nowrap>
						<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" name="countyName"  
						id="countyName<%=count-1 %>" isRequired="0" class="inputBorder4" value="" readonly="true" 
						style="width:90px; float:left;" size="113" 
						onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Country')};"/>
						<input type="hidden" name="countyCode"  id="countyCode<%=count-1 %>" value=""   />
						<div class="searchBg1" align="left">
							<a	href="#" title="Select Country" onclick="return popCountryList('<%=count-1 %>')">...</a>
						</div></td>
				</tr>
				<input type="hidden" value="Modify" name="Type">
			</logic:empty>


		</table>
			<!--</div>-->
		</fieldset>
		</td>
	</tr>
	<tr>
		<td colspan="8" align="center">
		<!-- 	<input type="button" name="btnAddContact" value="Reset selected Details">
	    <input type="button" name="btnAddContact" value="Add More Address" onClick="AddAddress()">
		<input type="button" name="btnDeleteContactDetail" value="Delete Contact Detail" onClick="DeleteAddress()">-->
		<input type="hidden" value="<%=count%>" name="hdnAddressCount">
		</td>
	</tr>
</table>

