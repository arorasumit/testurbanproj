<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ANIL KUMAR 	21-June-11	00-05422		Add new Fields dispatch CST No  -->
<!--[002]	 Raghu	    13-July-2012				Code change for allowing Maxlength from backend for service summary section		UAT of 7 Product-->
<!--[003]	 Raghu	    13-July-2012				changes for dispatch contact name for hardware details -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>viewHardwareDetails</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<body>
	<fieldset class="border1">
	<legend> <b>Hardware</b> </legend>
	<div class="scroll" style="height:100%">
		<fieldset class="border1">
			<legend> 
			<input name="btn1" id="btnHwDetails"
				onClick="if(this.value == '+')getHardwareDetails();show('tblHwDetails',this)" type="button" value="+"
				style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;
			<b>Hardware Related Details</b> </legend>
			<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblHwDetails" style="display: none;">
				<tr>
					<td width="100%" valign="top" align="left">
						<table width="100%" border="0" cellpadding="1" cellspacing="0" style="margin-top:7px ">
							<tr>
								<td width="25%" align="left" style="font-size:9px">Store</td>
								<td width="25%">
								<!--   Auto suggest Store Puneet  
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.id)" name="txtStore" id="txtStore" class="dropdownMargin" onchange='refreshTaxRate(path);' isRequired="0" style="width:150px;">
										<option selected="selected"  value="0">Select Store</option>
									</select>-->
									<!--   Auto suggest Store Puneet  -->
								 	<input type="text" readonly="true" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:150px" isRequired="0"  name="txtStoreText" ctrltype='dropdown' retval='AUTOSUGGESTSTORE' id="txtStoreText" class="dropdownMargin"><a id="textStoreLinkId" name="textStoreLinkId" ctrltype='dropdownlink' onclick="javascript:getStore()">Show</a>
										<input type="hidden" name="txtStore" id="txtStore" isrequired="0">
									<!--   Auto suggest Store Puneet  -->	
									<input type="hidden" name="hdnStore"> 
									<input type="hidden" name="hdnHardwareInfoID" value="0">
								</td>
								<td width="25%" align="left" style="font-size:9px">Hardware type</td>
								<td width="25%" align="left" style="font-size:9px">
								<!--   Auto suggest Hardware Type Puneet  
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.id)" name="txtHtype" id="txtHtype" class="dropdownMargin" onchange='refreshTaxRate(path);' isRequired="0" style="width:150px;" isRequired="0" style="width:150px;">
										<option selected="selected" value="0">Select Hardware Type</option>
									</select>
								-->
									<!--   Auto suggest Hardware Type Puneet  -->
								 	<input type="text" readonly="true" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:150px" isRequired="0"  name="hTypeText" ctrltype='dropdown' retval='AUTOSUGGESTHTYPE' id="hTypeText" class="dropdownMargin"><a id="hTypeLinkId" name = "hTypeLinkId" ctrltype='dropdownlink' onclick="javascript:getHardwareType()">Show</a>
										<input type="hidden" name="txtHtype" id="txtHtype" isrequired="0">
									<!--   Auto suggest  Hardware Type Puneet  -->
								</td>
								
				<td width="25%"></td>
							</tr>
							<tr>
								<td align="left" style="font-size:9px">Form available</td>
								<td>
								<!-- Puneet commenting for auto suggest
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.id)"  name="txtform" id="txtform" class="dropdownMargin" onchange='refreshTaxRate(path);' isRequired="0" style="width:150px;">
									<option  selected="selected" value="0">Select Form</option>
									</select>
								-->
								<!--   Auto suggest Form Puneet  -->
								 	<input type="text" readonly="true" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:150px" isRequired="0"  name="textFormTXT" ctrltype='dropdown' retval='AUTOSUGGESTTEXTFORM' id="textFormTXT" class="dropdownMargin"><a id="textFormTXTLinkId" name = "textFormTXTLinkId" ctrltype='dropdownlink' onclick="javascript:getFormType()">Show</a>
										<input type="hidden" name="txtform" id="txtform" isrequired="0">
								<!--   Auto suggest Form Puneet  -->	
								</td>
								<td align="left" style="font-size:9px">Nature of sale</td>
								<td align="left" style="font-size:9px">
									<!--   Auto suggest Sale Nature Puneet  
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.id)" name="txtNSale"
										id="txtNSale" class="dropdownMargin" onchange='refreshTaxRate(path);' isRequired="0" style="width:150px;" style="width:100px"">
										<option selected="selected" value="0">Select Sale Nature</option>
									</select>-->
									<!--   Auto suggest Sale Nature Puneet  -->
								 	<input type="text" readonly="true" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:150px" isRequired="0"  name="textSaleNature" ctrltype='dropdown' retval='AUTOSUGGESTSALENATURE' id="textSaleNature" class="dropdownMargin"><a id="textSaleNatureLinkId" name="textSaleNatureLinkId" ctrltype='dropdownlink' onclick="javascript:getSaleNature()">Show</a>
										<input type="hidden" name="txtNSale" id="txtNSale" isrequired="0">
									<!--   Auto suggest Sale Nature Puneet  -->
								</td>
				<td></td>
							</tr>
				<tr>
					<td align="left" style="font-size:9px">Type of sale</td>
					<td> 
					<!--   Auto suggest Sale type Puneet  
						<select onmouseover="getTip_DD(this,this.selectedIndex,this.id)"  name="txtTSale" id="txtTSale" class="dropdownMargin" isRequired="0" style="width:150px;" style="width:100px"  onchange="setPAandIR();refreshTaxRate(path);">
							<option  selected="selected" value="0">Select Type Of Sale</option>
						</select>-->
						<!--   Auto suggest Form Puneet  -->
						<input type="text" readonly="true" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:150px" isRequired="0"  name="textSaleType" ctrltype='dropdown' retval='AUTOSUGGESTSALETYPE' id="textSaleType" class="dropdownMargin"><a id="saleTypeTXTLinkId" name="saleTypeTXTLinkId" ctrltype='dropdownlink' onclick="javascript:getSaleType()">Show</a>
							<input type="hidden" name="txtTSale" id="txtTSale" isrequired="0">
						<!--   Auto suggest Form Puneet  -->		
					</td>
							<td align="left" style="font-size:9px"> Principal Amount </td>
				<td align="left" style="font-size:9px"><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"
					style="width:150px;" class="inputBorder1" name="txtPrincipalAmount" id="txtPrincipalAmount"
					 value="0"></td>
			</tr>
			<tr>
				<td align="left" style="font-size:9px">Interest Rate</td>
				<td><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" style="width:150px;" class="inputBorder1"
					name="txtInterestRate" id="txtInterestRate"
					 value="0"></td>
				<td align="left" style="font-size:9px"></td>
				<td align="left" style="font-size:9px"></td>
				<td></td>
			</tr>
		</table>
					</td>
				</tr>
			</table>
	</fieldset>
	<fieldset class="border1">
			<legend> 
			<input name="btn1" id="btnDispatchAddress"
				onClick="if(this.value == '+')getHardwareDetails();show('tblDispatchAddress',this)" type="button" value="+"
				style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;
			 <b>Dispatch Address</b> </legend>
			<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblDispatchAddress" style="display: none;">
				<tr>
					<td width="100%" valign="top" align="left">
						<table width="100%" border="0" cellpadding="1" cellspacing="0" style="margin-top:7px ">
							<tr>
								<td width="25%" align="left" style="font-size:9px">Select Dispatch Address Code</td>
								<td width="25%">
									<!--<select onmouseover="getTip_DD(this,this.selectedIndex,this.name)"  name="txtdispatchAddress" class="dropdownMargin" onchange="getDispatchAddress()"  isRequired="0" style="width:150px;" style="width:100px">
										<option selected="selected" value="0">Select Dispatch Address Code</option>
									</select>
								-->
								 <!--   Auto suggest Dispatch Address Start lawkush  -->
									<input type="text" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:50%;float:left;" name="txtDispatchAddressSuggest" ctrltype='dropdown' retval='AUTOSUGGESTDISPATCH' id="txtDispatchAddressSuggest" class="dropdownMargin"><a id="dispatchAutoSuggestLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownDispatch()">Show</a>								
									<input type="hidden" name="txtdispatchAddress" id="txtdispatchAddress" isrequired="1">
									 <!--   Auto suggest BCP End lawkush  -->
								
								</td>
							</tr>
							<tr>
								<td width="25%" align="left" style="font-size:9px">Address</td>
								<td width="25%"><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" style="width:150px;" class="inputBorder" name="txtHAddress1" readonly="readonly"> </td>
								<td width="25%" align="left" style="font-size:9px">City Name</td>
								<td width="25%"><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" style="width:150px;" class="inputBorder" name="txtHCityName" readonly="readonly"></td>
							</tr>
							<tr>
								<td align="left" style="font-size:9px"></td>
								<td><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" style="width:150px;" class="inputBorder" name="txtHAddress2" readonly="readonly"></td>
								<td align="left" style="font-size:9px">State Name</td>
								<td><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" style="width:150px;" class="inputBorder" name="txtHStateName" readonly="readonly"> </td>
							</tr>
							<tr>
								<td align="left" style="font-size:9px"></td>
								<td><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" style="width:150px;" class="inputBorder" name="txtHAddress3" readonly="readonly"> </td>
								<td align="left" style="font-size:9px">PinCode</td>
								<td><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" style="width:150px;" class="inputBorder" name="txtHPincode" readonly="readonly"> </td>
							</tr>
							<tr>
								<td align="left" style="font-size:9px">Phone No</td>
								<td><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" style="width:150px;" class="inputBorder" name="txtHPhnNo" readonly="readonly"> </td>
								<td align="left" style="font-size:9px">Country Name</td>
								<td><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" style="width:150px;" class="inputBorder" name="txtHCountryName" readonly="readonly"> </td>
							</tr>
							<tr>
								<td align="left" style="font-size:9px">LST Date</td>
								<td><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" style="width:150px;" class="inputBorder" name="txtHLstDate" readonly="readonly"> </td>
								<td align="left" style="font-size:9px">LST No</td>
								<td><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" style="width:150px;" class="inputBorder" name="txtHLstNo" readonly="readonly"> </td>
							</tr>
							<!-- Start   [001] -->
							<tr>
								<td align="left" style="font-size:9px">CST No</td>
								<td><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" style="width:150px;" class="inputBorder" name="txtHCstNo" readonly="readonly"> </td>
								<td align="left" style="font-size:9px">Contact Name</td>
								<td><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" style="width:150px;" class="inputBorder1" isRequired="0" id="txtDispatchContactName" name="txtDispatchContactName" maxlength="200"> </td>
							
							</tr>
							<!-- End   [001] -->
					  </table>
					</td>
				</tr>
			</table>
	</fieldset>
	<fieldset class="border1">
			<legend> 
			<input name="btn1" id="btnWarrentyDetails"
				onClick="if(this.value == '+')getHardwareDetails();show('tblWarrentyDetails',this)" type="button" value="+"
				style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;
			<b>Warranty Details</b> </legend>
			<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblWarrentyDetails" style="display: none;">
			<tr>
				
			<td align="left" class="columnFontSmall">Start Date Logic</td>
			<td align="left" ><select onmouseover="getTip_DD(this,this.selectedIndex,this.id)"  name="txtStartDateLogic" style="width:150px"
			class="dropdownMargin" id="txtStartDateLogic" isRequired="0">
			<option value="0">Select Start Date Logic</option></select> </td><td>	&nbsp;	</td>

				<td align="left" style="font-size:9px"> Months </td>

				<td align="left" style="font-size:9px"><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"

				style="width:60px;" class="inputBorder2" name="txtHStartMonth"  id="txtHStartMonth"  maxlength="4"  value="0" onblur="if(this.value.length > 0){return checknumber(this)}"></td><td>	&nbsp;	</td>
			
			<td align="left" style="font-size:9px"> Days</td>

			<td align="left" style="font-size:9px"><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"

			style="width:60px;" class="inputBorder2" name="txtHStartDays" id="txtHStartDays"   maxlength="4"  value="0" onblur="if(this.value.length > 0){return checknumber(this)}"></td><td>	&nbsp;	</td>
			

				<td width="25%" align="left" style="font-size:9px" nowrap="nowrap">Start Date</td>
				<td width="25%">
					<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  class="inputBorder1" name="txtStartDate" readonly="readonly" isRequired="0" style="width:100px">
					
				</td>

			

			
				</tr>
			
			<tr>
			<td align="left" class="columnFontSmall">End Date Logic</td>
			<td align="left" ><select onmouseover="getTip_DD(this,this.selectedIndex,this.id)"  name="txtEndDateLogic" style="width:150px"
			class="dropdownMargin" id="txtEndDateLogic" isRequired="0">
			<option value="0">Select End Date Logic</option></select> </td><td>	&nbsp;	</td>
				 <td align="left" style="font-size:9px"> Months </td>

				<td align="left" style="font-size:9px"><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"

				style="width:60px;" class="inputBorder2" name="txtHEndMonth" id="txtHEndMonth"  maxlength="4"   value="0"  onblur="if(this.value.length > 0){return checknumber(this)}"></td><td>	&nbsp;	</td>
			
			<td align="left" style="font-size:9px"> Days</td>

			<td align="left" style="font-size:9px"><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"

			style="width:60px;" class="inputBorder2" name="txtHEndDays" id="txtHEndDays"   maxlength="4"  value="0" onblur="if(this.value.length > 0){return checknumber(this)}"></td><td>	&nbsp;	</td>
				 
				 
				<td width="25%" align="left" style="font-size:9px" nowrap="nowrap">End Date</td>
				<td width="25%"><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder1" name="txtEndDate" readonly="readonly" isRequired="0" style="width:100px"></td>
			</tr>
			<tr>
			<td style="width:150px" > <td/>
			<td>	&nbsp;	</td>
				<td align="left" style="font-size:9px"> Months </td>

				<td align="left" style="font-size:9px"><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"

				style="width:60px;" class="inputBorder2" name="txtHExtMonth" id="txtHExtMonth"  maxlength="4"  value="0"  onblur="if(this.value.length > 0){return checknumber(this)}"></td><td>	&nbsp;	</td>
			
			<td align="left" style="font-size:9px"> Days</td>

			<td align="left" style="font-size:9px"><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"

			style="width:60px;" class="inputBorder2" name="txtHExtDays" id="txtHExtDays"   maxlength="4"   value="0"  onblur="if(this.value.length > 0){return checknumber(this)}"></td><td>	&nbsp;	</td>
				 
				 
				<td width="25%" align="left" style="font-size:9px" nowrap="nowrap">Extend Support End Date</td>
				<td width="25%"><input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  class="inputBorder2" name="txtHExtDate"  readonly="readonly" isRequired="0" style="width:100px"></td>
			
			</tr>
			</table>
	</fieldset>
</div>
</fieldset>
</body>
</html>
