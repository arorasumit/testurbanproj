<!--Tag Name 	Resource Name  		Date		CSR No			Description -->
<!--[001] 		Lawkush			 11-April-11	  00-05422		In order to validate fields according to database driven mandatory or nonmandatory in all section-->
<!--[008]  	 Ashutosh		14-JUNE-11 00-05422         ddPNLocation and ddSNLocation Defects -->
<!--[002]	 	ANIL KUMAR 		 21-June-11	00-05422			Add new Fields Billing Contact and Email  -->
<!--[003]	 LAWKUSH	    07-Feb-2013				Service Location Customer Address like search Hypercare Point 5-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>viewServiceLocation</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<body>
	<fieldset class="border1" id="serviceLocationFieldsetId">
	<legend>
		<input name="btn1" id="btnServiceLocationDetails"
				onClick="if(this.value == '+')getLocationDetails();show('tblServiceLocationDetails',this)" type="button" value="+"
				style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;
	 <b>ServiceLocation Details</b> </legend>
	<table width="100%"  border="1" cellspacing="0" cellpadding="0" id="tblServiceLocationDetails" style="display: none;">
		<tr align="center">
			<td class="HeadingBlue">
				<strong>Primary Location</strong>
			</td>
			<td class="HeadingBlue">
				<strong>Secondary Location</strong>
			</td>
		</tr>
		<tr align="center">
			<td>
				Please Select a Type
				<select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' name="ddPrimaryLocType" id="ddPrimaryLocType" class="dropdownMargin" onchange="selectPrimaryLocation(1, null)"  isRequired="0">
					<option value="0" title="Select a Type">Select a Type</option>
					<option value="1" title="Customer Location">Customer Location</option>
					<option value="2" title="Network Pop Location">Network Pop Location</option>
				</select>
				<input type="hidden" name="hdnServiceInfoID" value="0">
			</td>
			<!-- Start[001] -->
			<td id ="allLoc">
				Please Select a Type
				<select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' name="ddSecondaryLocType" id="ddSecondaryLocType" class="dropdownMargin" onchange="selectSecondaryLocation(1, null)" isRequired="0">
					<option value="0" title="Select a Type">Select a Type</option>
					<option value="1" title="Customer Location">Customer Location</option>
					<option value="2" title="Network Pop Location">Network Pop Location</option>					
				</select>
			</td>
			  <td width="25%" id="onlyNetworkLoc">
			<!--	Please Select a Type
				<select onfocus='getTip_DD(this,this.selectedIndex,this.name)' name="ddSecondaryLocType" id="ddSecondaryLocType" style="width:85%;float:right" class="dropdownMargin" onchange="selectSecondaryLocation()" isRequired="1" >
					<option value="0">Select a Type</option>
					<option value="2" selected ="selected">Network Pop Location</option>
				</select>-->
			</td>
			<!-- End[001] -->
			
		</tr>
		<tr>
			<td>
				<fieldset class="border1" id="PriNetworkPopLocation" style="display: none">
					<legend> <b>Network/POP Location</b> </legend>
					<table width="100%"  border="0" cellspacing="2" cellpadding="0">
						
						<tr>
							<td width="50%">
								Please Select Network/POP Location:-
								<div class="searchBg1"><a href="#" title="Select Network Pop Location" id="Network/POPLocation" onClick="return PopLocTypePopup('PRILOC')"  >...</a></div>
								<textarea onmouseover='getTip(value)' onmouseout='UnTip()' class="inputBorder1" style="width:80%;float:right" name="txtPAddress" id="txtPAddress" readonly="readonly"></textarea>
								<!-- Start[008] -->
								<input type="hidden" name="ddPNLocation" id="ddPNLocation"   value="0" readonly="true" isRequired="1" styleClass="inputBorder1" style="width:110px;float:right;">
								<!-- End[008] -->
							    <input type="hidden" name="location" id="location"/>
							</td>
						</tr>
					</table>
				</fieldset>
				<fieldset class="border1" id="PriBCPLocation" style="display: none">
					<legend> <b>Customer Location</b> </legend>
					<table width="100%"  border="0" cellspacing="2" cellpadding="0" style="table-layout: fixed;">
						<tr>
							<td width="20%" align="left">
								BCP Details
							</td>
							<td width="80%">
							<!--     Select Customer Location  commented [003] Start
								<select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' name="ddPrimaryBCP" id="ddPrimaryBCP"  style="width:80%;float:right" class="dropdownMargin" onchange="FetchPriBCPDetails()" isRequired="0">
									<option value="0">Select Installation Address</option>
								</select>
								Auto suggest Customer Location  lawkush  -->
								<!--   Auto suggest Customer location   [003] End  -->
								<input type="text" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:80%;float:left" name="txtPriCustLocationSuggest" ctrltype='dropdown' retval='AUTOSUGGESTPRICUSTLOC' id="txtPriCustLocationSuggest" class="dropdownMargin"><a id="priCustLocAutoSuggestLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownPriCustLocation()">Show</a>								
								<input type="hidden" name="ddPrimaryBCP" id="ddPrimaryBCP" isrequired="1">
								<!--   Auto suggest Customer location end [003]   -->
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">
								Address 1
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtPAddress1" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">
								Address 2
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtPAddress2" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">
								Address 3
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtPAddress3" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">
								Address 4
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtPAddress4" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">
								City
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtPCity" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">
								State
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtPState" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td width="30%" align="left">
								Postal Code
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left"name="txtPPincode" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td width="30%" align="left">
								Country
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtPCountry" readonly="readonly">
							</td>
						</tr>
						<!-- Start   [002] -->
						<tr>
							<td width="30%" align="left">
								Contact Number
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtPContactNo" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td width="30%" align="left">
								Email
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtPEmailId" readonly="readonly">
							</td>
						</tr>
						<!-- end   [002] -->
					</table>
				</fieldset>
			</td>
			<td>
				<fieldset class="border1" id="SecNetworkLocation" style="display: none">
					<legend> <b>Network/POP Location</b> </legend>
					<table width="100%"  border="0" cellspacing="2" cellpadding="0">
						<tr>
							<td width="50%">
								Please Select Network/POP Location:-
								<div class="searchBg1"><a href="#" title="Select Network Pop Location" id="Network/POPLocation1" onClick="return PopLocTypePopup('SECLOC')"  >...</a></div>
								<textarea onmouseover='getTip(value)' onmouseout='UnTip()' class="inputBorder1" style="width:80%;float:right" name="txtSAddress" id="txtSAddress" readonly="readonly"></textarea>
								<!-- Start[008] -->
								<input type="hidden" name="ddSNLocation" id="ddSNLocation" value="0"  readonly="true" isRequired="1" styleClass="inputBorder1" style="width:110px;float:right;">
								<!-- End[008] -->							
							</td>
						</tr>
					</table>
				</fieldset>
				<fieldset class="border1" id="SecBCPLocation" style="display: none">
					<legend> <b>Customer Location</b> </legend>
					<table width="100%"  border="0" cellspacing="2" cellpadding="0" style="table-layout: fixed;">
						<tr>
							<td width="20%" align="left">
								BCP Details
							</td>
							<td width="80%">
							<!-- Lawkush 30 Jan Secondary Location   [003] Start
								<select name="ddSecondaryBCP" id="ddSecondaryBCP"  style="width:80%;float:right" class="dropdownMargin" onchange="FetchSecBCPDetails()" isRequired="0">
									<option value="0">Select Installation Address</option>
								</select>
								 Lawkush 30 Jan Secondary Location [003]  End -->
								 <input type="text" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:80%;float:left" name="txtSecCustLocationSuggest" ctrltype='dropdown' retval='AUTOSUGGESTSECCUSTLOC' id="txtSecCustLocationSuggest" class="dropdownMargin"><a id="secCustLocAutoSuggestLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownSecCustLocation()">Show</a>								
								<input type="hidden" name="ddSecondaryBCP" id="ddSecondaryBCP" isrequired="1">
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">
								Address 1
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtSAddress1" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">
								Address 2
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtSAddress2" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">
								Address 3
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtSAddress3" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">
								Address 4
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtSAddress4" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">
								City
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtSCity" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">
								State
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtSState" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td width="30%" align="left">
								Postal Code
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtSPincode" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td width="20%" align="left">
								Country
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtSCountry" readonly="readonly">
							</td>
						</tr>
						<!-- Start   [002] -->
						<tr>
							<td width="30%" align="left">
								Contact Number
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtSContactNo" readonly="readonly">
							</td>
						</tr>
						<tr>
							<td width="30%" align="left">
								Email
							</td>
							<td width="80%">
								<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:left" name="txtSEmailId" readonly="readonly">
							</td>
						</tr>
						<!-- end   [002] -->
					</table>
				</fieldset>
			</td>
		</tr>
		<tr>
			<td colspan="2" width="100%">
			    <fieldset class="border1" id="SecBCPLocation" >			
				<legend> <b>From/To Location</b> </legend>
				<table width="100%"  border="0" cellspacing="2" cellpadding="0">
					<tr>
						<td width="15%">
							From Address
						</td>
						<td width="35%" align="right">
							<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder"
					style="width:78%;" name="txtFAddress" value=""
					maxlength="25" isRequired="0"
					onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'From Address')};"
					size="19">
						</td>
						<td width="17%" align="center">
							To Address
						</td>
						<td width="35%">
							<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder" style="width:80%;float:right" name="txtToAddress" value="" maxlength="25" isRequired="0" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'To Address')};">
						</td>
					</tr>
				</table>
				</fieldset>
			</td>
		</tr>		
	</table>
</fieldset>
</body>
</html>
