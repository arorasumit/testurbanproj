<!--[001]	 MANISHA GARG	02-mar-11	00-05422		Add new Field Notice Period  -->
<!--[002]	 ANIL KUMAR 	21-June-11	00-05422		Add new Fields Billing Contact and Email  -->
<!--[003]	 LAWKUSH		28-June-11	CSR00-05422     Added NFA checkbox on product catelog page  -->
<!--[004]	 LAWKUSH		13-July-11	CSR00-05422     For changing PO detail no. and Customer po detail no  -->
<!--[005]	 Neelesh		24-May-13	CSR00-08463     For Addition of Revenue Circle  -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>viewBillingInfo</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<body>
	<fieldset class="border1" id="billingInfoFieldsetId">
		<legend>		
		<!--<input name="btn1" id="btnBillingInformation"
			onClick="show('tblBillingInformation',this)" type="button" value="+"
			style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">
		--><b>Billing Information</b> </legend>
		<div class="scroll" style="height:100%">
			<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblBillingInformation" style="display: Inline;">
				<tr>
					<td width="42%" valign="top">
						<table border="0" cellspacing="0" cellpadding="1" style="margin-top:5px " width="100%">
							<tr>
							<!-- Start[004] -->
								<td align="left" nowrap class="columnFontSmall">
									Cust PO Detail Number
								</td>
								<td colspan="1">
									<!-- <select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' name="txtPODetailNo"	id="txtPODetailNo" class="dropdownMargin"
									isRequired="0" onfocus="getPrevCustPoNo(this.selectedIndex)" onchange="if(checkchargevalidation()==false){return false;}else{fnFetchEntity(this.value);selectPoId(this)}">
									<option value="0">Select Cust PO Detail No</option>
								
									</select>-->
									<!--   Auto suggest PO Puneet  -->
									<input type="text" readonly="true" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:130px" isRequired="0"  name="poDetailNo" ctrltype='dropdown' retval='AUTOSUGGESTCUSTPODETAIL' id="poDetailNo" class="dropdownMargin"><a name="poDetailNoTXTLinkId" id="poDetailNoTXTLinkId" ctrltype='dropdownlink' onclick="javascript:getPODetailNo();">Show</a>
									<input type="hidden" value="0" name="txtPODetailNo" id="txtPODetailNo" isrequired="0">
									<!--   Auto suggest PO Puneet  -->	
								</td>
								<!-- End[004] -->
								<td align="left"  class="columnFontSmall" width="200">
									PO Date
								</td>
								<td>
									<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text"  class="inputBorder1" name="txtBillingPODate" id="txtBillingPODate" readonly="true" isRequired="0" style="width:90px">
								</td>
								<td align="left"  nowrap class="columnFontSmall">
									Contract Period (Months)
							  </td>
								<td>
									<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text"  class="inputBorder1" name="txtCntrtMonths" id="txtCntrtMonths" readonly="true" maxlength="5" isRequired="0" style="width:150px">
								</td>
							</tr>
								
							<tr>
								<td align="left" class="columnFontSmall">
									A\C No
								</td>
								<td colspan="1"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder1"
									name="txtBillingAccNO" id="txtBillingAccNo" isRequired="0" style="width:150px"
									value="<%=request.getParameter("crmAccountName")%>" readonly="true">
											  </td>
								<td align="left" nowrap class="columnFontSmall" width="200">
									Credit Period
								</td>
								<td align="left">
								<!--
									<select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' name="txtBillingCP" id="txtBillingCP" class="inputBorder1" style="margin-left:10px; width:150px" isRequired="0">
										<option value="0">Select Credit Period</option>
									</select>-->
									<!--   Auto suggest Credit Period Puneet  -->
								 	<input type="text" readonly="true" onmouseover="getTip(value)" onmouseout="UnTip()" style="margin-left:10px;width:135px" isRequired="0"  name="billingCP" ctrltype='dropdown' retval='AUTOSUGGESTCP' id="billingCP" class="dropdownMargin"><a id="billingCPTXTLinkId" name="billingCPTXTLinkId" ctrltype='dropdownlink' onclick="javascript:getBillingCP()">Show</a>
										<input type="hidden" value="0" name="txtBillingCP" id="txtBillingCP" isrequired="0">
									<!--   Auto suggest Credit Period Puneet  -->
									<input type="hidden" name="hdnBillingCP">
								</td>
								<td align="left" class="columnFontSmall">
									Currency
								</td>
								<td align="left">
									<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" name="txtCur" id="txtCur" class="inputBorder1" value="" readonly="true" isRequired="0" style="width:150px">
								</td>
							</tr>
							
							<tr>
								<td align="left" nowrap class="columnFontSmall">
									Legal Entity
								</td>
								<td align="left">
									<!-- <select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' name="txtEntity" id="txtEntity" class="dropdownMargin" onchange="fnFetchLicCompany()"  isRequired="0" style="width:150px">
										<option value="0">Select a Legal Entity</option>
									</select> -->
									<!--   Auto suggest Legal entity Puneet  -->
								 	<input type="text" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:135px" isRequired="0"  name="legalEntityTXT" ctrltype='dropdown' retval='AUTOSUGGESTLEGALENTITY' id="legalEntityTXT" class="dropdownMargin" readonly="true"><a id="legalEntityTXTLinkId" name="legalEntityTXTLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownLegalEntity();">Show</a>
										<input type="hidden" value="0" name="txtEntity" id="txtEntity" isrequired="0">
									<!--   Auto suggest Legal entity Puneet  -->	
								</td>
								<td align="left" class="columnFontSmall" width="200">
									Bill Format
								</td>
								<td align="left">
									<!--   Auto suggest Bill Format Puneet  
									<select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' onfocus='getPrevBillFormat(this.selectedIndex);' name="txtBillingformat" id="txtBillingformat" class="dropdownMargin" isRequired="0" style="width:150px"  onchange="setTypeofSale()">
										<option value="0">Select a Bill Format</option>
									
									</select>-->
									<!--   Auto suggest Bill Format Puneet  -->
								 	<input type="text" readonly="true" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:135px" isRequired="0"  name="billingFormat" ctrltype='dropdown' retval='AUTOSUGGESTBILLINGFORMAT' id="billingFormat" class="dropdownMargin"><a id="billingFormatTXTLinkId" name="billingFormatTXTLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownBillingFormat()">Show</a>
										<input type="hidden" value="0" name="txtBillingformat" id="txtBillingformat" isrequired="0">
									<input type="hidden" name="hdnBillingformat">
									<input type="hidden" name="txtBillingAC" id="txtBillingAC" value="<%=request.getParameter("accountID")%>">
								</td>
								<td align="left" class="columnFontSmall">
									Billing Mode
								</td>
								<td align="left">
								<!--   Auto suggest Bill Mode Puneet  
									<select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' name="txtBillingMode" id="txtBillingMode" class="dropdownMargin" isRequired="0" style="width:150px">
												<option value="0" title="Select Billing Mode">Select Billing Mode</option>												
									</select>-->
									<input type="text" readonly="true" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:130px" isRequired="0"  name="billingMode" ctrltype='dropdown' retval='AUTOSUGGESTBILLINGMODE' id="billingMode" class="dropdownMargin"><a id="billingModeTXTLinkId" name="billingModeTXTLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownBillingMode()">Show</a>
										<input type="hidden" value="0" name="txtBillingMode" id="txtBillingMode" isrequired="0">
								</td>
							</tr>
								
							<tr>
								<td align="left" nowrap class="columnFontSmall">
									Licence Co
								</td>
								<td align="left" colspan="1">
									<!--  <select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' name="txtLicenceCo" id="txtLicenceCo" class="dropdownMargin" isRequired="0" style="width:150px" onchange='populateStore(path);'>
										<option value="0">Select a Licence Company</option>
									</select> -->
									<!--   Auto suggest License Co Puneet  -->
								 	<input type="text" readonly="true" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:135px" isRequired="0"  name="licenseCo" ctrltype='dropdown' retval='AUTOSUGGESTLICENSECO' id="licenseCo" class="dropdownMargin"><a id="licenseCoLinkId" name="licenseCoLinkId" ctrltype='dropdownlink' onclick="javascript:getLicenseCo()">Show</a>
										<input type="hidden" value="0" name="txtLicenceCo" id="txtLicenceCo" isrequired="0">
									<input type="hidden" name="hdnLicenceCo">
									<!--   Auto suggest License Co Puneet  -->
								</td>
								<!--[001] start -->
								
								<td align="left" nowrap class="columnFontSmall" width="200">
									Notice Period
									<!-- Added Days in label By Lawkush Start-->
										(Days)	
									<!-- Added Days in label By Lawkush End-->		
							  	</td>
								<td >
									<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder1" name="txtNoticePeriod" id="txtNoticePeriod" isRequired="0" maxlength="5" style="width:150px" onblur="if(this.value.length > 0){return checknumber(this)}" /> 
								</td>
								<!--[001] end -->
								
								<td align="left" nowrap class="columnFontSmall">
									Billing Type
								</td>
								<td align="left">
									<!--<select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' name="txtBillingType" id="txtBillingType" class="dropdownMargin" isRequired="0" style="width:150px">
										<option value="0">Select Bill Type</option>
									
									</select>-->
									<!--   Auto suggest Bill Type Puneet  -->
								 	<input type="text" readonly="true" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:130px" isRequired="0"  name="billingType" ctrltype='dropdown' retval='AUTOSUGGESTBILLINGTYPE' id="billingType" class="dropdownMargin"><a id="billingTypeLinkId" name="billingTypeLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownBillingType()">Show</a>
										<input type="hidden" value="0" name="txtBillingType" id="txtBillingType" isrequired="0">
									<!--   Auto suggest Bill Type Puneet  -->
									<input type="hidden" name="hdnBillingType">
								</td>
							</tr>
							
							<tr>
								<td align="left" class="columnFontSmall">
									Taxation
								</td>
								<td align="left">
									<!--<select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' name="txtTaxation" id="txtTaxation" class="dropdownMargin" isRequired="0" style="width:150px" onchange=setPopulateStdReason(this.value);>
										<option value="0">Select Taxation</option>
									
									</select>-->
									<!--   Auto suggest Select Taxation Puneet  -->
								 	<input type="text" readonly="true" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:135px" isRequired="0"  name="taxationID" ctrltype='dropdown' retval='AUTOSUGGESTBILLTAXATION' id="taxationID" class="dropdownMargin"><a id="taxationAutoSuggestLinkId" name="taxationAutoSuggestLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownBillTaxation()">Show</a>
										<input type="hidden" value="0" name="txtTaxation" id="txtTaxation" isrequired="0">
									<!--   Auto suggest Select Taxation Puneet  -->
									<input type="hidden" name="hdnTaxation" isRequired="0" style="width:150px">
								</td>
								<td class="columnFontSmall" colspan="1" align="left" width="200">
									Standard Reason
								</td>
								<td align="left">
									<!--  <select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' name="changeReason" style="width:150px" id="changeReason" class="dropdownMargin" >
										<option value="0">Select Standard Reason</option>
									</select>-->
									<input type="text" readonly="true" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:135px" isRequired="0"  name="txtReason" ctrltype='dropdown' retval='AUTOSUGGESTREASON' id="txtReason" class="dropdownMargin"><a id="reasonAutoSuggestLinkId" name="reasonAutoSuggestLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownReason()">Show</a>
										<input type="hidden" value="0" name="changeReason" id="changeReason" isrequired="0">
								</td>
								<td class="columnFontSmall" colspan="0" align="left">
									Commitment Period(Months)
								</td>
								<td align="left">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  style="width:150px" class="inputBorder1" name="txtCmtPeriod" maxlength="5" value="0" id="txtCmtPeriod" isRequired="0" onblur="if( trim(this.value).length>0){ return checkdigits(this)};">
								</td>
							</tr>
							
							<tr>
								<td align="left" nowrap class="columnFontSmall">
									Billing Level
								</td>
								<td align="left" colspan="1">
								<!--   Auto suggest Bill Level Puneet
									<select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' name="txtBillingLvl"  id="txtBillingLvl" class="dropdownMargin"  onchange=setBillingLevelNo(this.value); isRequired="0" style="width:150px">
										<option value="0">Select a Billing Level</option>
								
									</select>-->
									<!--   Auto suggest Bill Level Puneet  -->
								 	<input type="text" readonly="true" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:135px" isRequired="0"  name="billingLevel" ctrltype='dropdown' retval='AUTOSUGGESTBILLINGLEVEL' id="billingLevel" class="dropdownMargin"><a id="billingLevelLinkId" name="billingLevelLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownBillingLevel()">Show</a>
										<input type="hidden" value="0" name="txtBillingLvl" id="txtBillingLvl" isrequired="0">
									<!--   Auto suggest Bill Level Puneet  -->
									<input type="hidden" name="hdnBillingLvl"> 
								</td>
								<td colspan="1" align="left" nowrap class="columnFontSmall" width="200">Billing Level No</td>
								<td colspan="1" align="left" ><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputBorder1"
									width="5" maxlength="5" value="0" name="txtBillingLevelNo" id="txtBillingLevelNo"
									readonly="readonly"   onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'BillingLevelNo')};"></td>
									<!-- Start[004] -->
								<td align="left" class="columnFontSmall">
									 PO ID
								</td>
								<td align="left">
									<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" id="txtPoId" name="txtPoId" value="0" readonly="readonly">
									<input type="hidden" name="hdnBillingInfoID">
								</td>
								
							<!-- End[004] -->
							</tr>
								<td align="left" nowrap class="columnFontSmall">
									Charge Redirection LSI
								</td>
								<td align="left" colspan="1">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  style="width:80px;float: left" class="inputBorder2" name="txtFxRedirectionLSI" id="txtFxRedirectionLSI" isRequired="0" readonly="readonly" value="0" >	
									<div class="searchBg" id="divPopUpLsiLink" style="float: left" isDataEntry="1"><a href="#" isDataEntry="1" onclick="popUpForArborLSILookup()" id="popUpLsiLink">..</a></div>
								</td>
								<td colspan="1" align="left" nowrap class="columnFontSmall" width="200">
									Billing Scenario
								</td>
								<td colspan="1" align="left" >
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.name)"  name="txtBillingScenario"  id="txtBillingScenario" class="dropdownMargin" disabled="disabled" isRequired="0">
										<option value="0">DEFAULT</option>
										<option value="1">CONSOLIDATE</option>
										<option value="2">CUMULATIVE</option>
									</select>
								</td>
									<!-- Start[004] -->
								<td align="left" class="columnFontSmall">
									 Usage
								</td>
								<td align="left">
									<input name="chkIsUsage" id="chkIsUsage" value="0" type="checkbox" disabled="disabled">
								</td>
							<tr>
								
							</tr>
							<tr>		
								<td width="20%" nowrap class="columnFontSmall">
										OSP
								</td>
								<td width="20%" align="left" nowrap class="columnFontSmall">
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.name)"  name="txtOSPTagging" id="txtOSPTagging" class="inputBorder1" isRequired="1" style="width:100px" onchange="checkOSPTaggingMandate(this.value)" >
										<option value="0">No</option>
										<option value="1">Yes</option>
									</select>
								</td>	
								<td width="20%" nowrap class="columnFontSmall">
										OSP RegNo
								</td>
								<td width="20%" align="left" nowrap class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  style="width:120px" maxlength="150" class="inputBorder2" name="txtOSPRegNo" value="" id="txtOSPRegNo" isRequired="0" disabled="true"  >
										
								</td>		
								<td width="20%" nowrap class="columnFontSmall">
										OSP RegDate(dd/mm/yyyy)
								</td>
								<td width="20%" align="left" nowrap class="columnFontSmall">
										<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  style="width:120px" class="inputBorder2" name="txtOSPRegDate" value="" id="txtOSPRegDate" isRequired="0" disabled="true"  onblur="if(this.value.length > 0){return checkdate(this)}" />&nbsp;<font size="1"><a href="javascript:show_calendar(document.getElementById('txtOSPRegDate'));" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
										</font>
										
								</td>				
							</tr>	
							<tr>
								<td align="left" nowrap class="columnFontSmall">
									Penalty Clause
								</td>
								<td colspan="3" align="left"><textarea class="inputBorder1" onmouseover='getTip(value)' onmouseout='UnTip()'
									rows="3" id="txtPenaltyClause" name="txtPenaltyClause" style="width:500px;
									maxlength="999" isRequired="0"></textarea></td>
									
								<!-- Start[003] -->
								<td align="left" nowrap class="columnFontSmall" width="140">
									NFA 
								</td>
								<td colspan="0"  name="nfaId" id="nfaId" width="361">
								<input name="chkSelectNfa" id="chkSelectNfa" type="checkbox" onClick="checkNfa()" value="0">
								</td>
								<!-- End[003] -->	
							</tr>
							<!-- WARRANTY CLAUSE ADDED BY MANISHA START -->
							<tr>
								<td width="16%" align="left" nowrap class="columnFontSmall">
									Warranty Clause
								</td>
								<td colspan="3" align="left">
									<textarea  class="inputBorder1" rows="3" id="txtWarrantClause" name="txtWarrantClause" maxlength="999" style="width:100%;" isRequired="0" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'WarrantyClause')};"></textarea> 
								</td>	
								
								<td width="40%" nowrap class="columnFontSmall">
									  
								</td>
								<td width="17%" align="left" nowrap class="columnFontSmall">
								</td>							
							</tr>		
							<!-- WARRANTY CLAUSE ADDED BY MANISHA End -->
							<!-- bcp details for services ADDED BY MANISHA START-->
							<tr>
							<td width="16%" align="left" nowrap class="columnFontSmall">
								<div align="left"><b>Ship to Address</b></div>
							</td>
							<td colspan="5" align="left" nowrap>
									<!--     Select BCP commented lawkush  
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.name)" name="bbPrimaryBCP" id="bbPrimaryBCPID" style="width:60%;float:left;" class="dropdownMargin" onchange="FetchBillingDetails()" size="0">
											<option value="">Select BCP Details</option>
									</select>-->
									 <!--   Auto suggest BCP Start lawkush  -->
									<input type="text" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:32%;float:left;" name="txtBCPAddressSuggestService" ctrltype='dropdown' retval='AUTOSUGGESTBCPSERVICE' id="txtBCPAddressSuggestService" class="dropdownMargin" readOnly='true'><a id="bcpAutoSuggestServiceLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownBillingforService()">Show</a>								
									<input type="hidden" name="bbPrimaryBCPService" id="bbPrimaryBCPIDService" isrequired="1">
									 <!--   Auto suggest BCP End lawkush  -->
							</td>
							</tr>
							<tr>							
									<td width="16%" align="left" nowrap class="columnFontSmall">
									    Address 1
									</td>
									<td width="32%" colspan="2" align="left" nowrap>
										<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBAddress1Service" id="txtBAddress1Service" readonly="readonly">
									</td>
									<td width="16%" align="left" nowrap class="columnFontSmall" > Address 2</td>
									<td width="40%" colspan="2" nowrap class="columnFontSmall">
	                                  	<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBAddress2Service" id="txtBAddress2Service" readonly="readonly">
									</td>
						  </tr>
						  <tr>							
									<td width="16%" align="left" nowrap class="columnFontSmall">
									    Address 3
									</td>
									<td width="32%" colspan="2" align="left" nowrap>
										<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBAddress3Service" id="txtBAddress3Service" readonly="readonly">
									</td>
									<td width="16%" align="left" nowrap class="columnFontSmall" > Address 4</td>
									<td width="40%" colspan="2" nowrap class="columnFontSmall">
	                                  	<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBAddress4Service" id="txtBAddress4Service" readonly="readonly">	
							</td>
						  </tr>
						  <tr>							
									<td width="16%" align="left" nowrap class="columnFontSmall">
									    Postal Code
									</td>
									<td width="32%" colspan="2" align="left" nowrap>
										<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBPincodeService" id="txtBPincodeService" readonly="readonly">
									</td>
									<td width="16%" align="left" nowrap class="columnFontSmall" >
									    Email 
									</td>
									<td width="40%" colspan="2" nowrap class="columnFontSmall">
										<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBEmailIdService" id="txtBEmailIdService" readonly="readonly">	
									</td>
						  </tr>
						  <tr>							
									<td width="16%" align="left" nowrap class="columnFontSmall">
										Designation 
									</td>
									<td colspan="2" align="left" nowrap>
										<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="designationService" id="designationService" readonly="readonly">
									</td>
									<td width="16%" align="left" nowrap class="columnFontSmall" >
									    Contact Number
									</td>
									<td colspan="2" nowrap class="columnFontSmall">
										<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBContactNoService" id="txtBContactNoService" readonly="readonly">		
									</td>
						  </tr>
						  <tr>							
									<td width="16%" align="left" nowrap class="columnFontSmall">
										Lst Date
									</td>
									<td colspan="2" align="left" class="columnFontSmall">
										<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="lst_DateService" id="lst_DateService" readonly="readonly">
									</td>
									<td width="16%" align="left" class="columnFontSmall" >
										Lst No
									</td>
									<td colspan="2" class="columnFontSmall">
										<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="lst_NoService" id="lst_NoService" readonly="readonly">	
									</td>
						  </tr>
						  <tr>							
									<td width="16%" align="left" nowrap class="columnFontSmall">
									    City
									</td>
									<td colspan="2" align="left" class="columnFontSmall">
										<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBCityService" id="txtBCityService" readonly="readonly">
									</td>
									<td width="16%" align="left" class="columnFontSmall" >
									    State
									</td>
									<td colspan="2" class="columnFontSmall">
										<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBStateService" id="txtBStateService" readonly="readonly">
									</td>
						  </tr>
						  <tr>							
									<td width="16%" align="left" nowrap class="columnFontSmall">
								    Country </td>
									<td colspan="2" align="left">
										<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBCountryService" id="txtBCountryService" readonly="readonly">
									</td>
									<td width="16%" align="left" nowrap class="columnFontSmall">
								    Billing Contact Name </td>
									<td colspan="2" align="left">
										<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBContactNameService" id="txtBContactNameService" readonly="readonly">
									</td>
						  </tr>
							<tr>
								<td width="16%" align="left" class="columnFontSmall" >
									Circle
								</td>
								<td colspan="2" class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtCircleService" id="txtCircleService" readonly="readonly">									
								</td>
								<td colspan="3" class="columnFontSmall">
									&nbsp;
								</td>
							</tr>
					<!-- bcp details for services ADDED BY MANISHA END-->
							<tr>
								<td  align="left"><b>
									Billing BCP Details</b>
								</td>
								<td align="left" colspan="8">
									<!--  <select onmouseover="getTip_DD(this,this.selectedIndex,this.name)" name="bbPrimaryBCP" id="bbPrimaryBCPID" style="width:70%;float:left;" class="dropdownMargin" onchange="FetchBillingDetails()" size="0">
										<option value="">Select BCP Details</option>
									</select>-->
									<input type="text" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:32%;float:left;" name="txtBCPAddressSuggest" ctrltype='dropdown' retval='AUTOSUGGESTBCP' id="txtBCPAddressSuggest" class="dropdownMargin" readOnly='true'><a id="bcpAutoSuggestLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownBilling()">Show</a>								
									<input type="hidden" name="bbPrimaryBCP" id="bbPrimaryBCPID" isrequired="1">
								</td>
							</tr>
							<tr>
								<td align="left" nowrap class="columnFontSmall">
									Billing Address 1
								</td>
								<td align="left" colspan="2">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBAddress1" readonly="readonly">
								</td>
								
								<td align="left" nowrap class="columnFontSmall">
									Billing Address 2
								</td>
								<td align="left" colspan="2">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBAddress2" readonly="readonly">
								</td>
								
								<td align="left" nowrap class="columnFontSmall">
									Billing Address 3
								</td>
								<td align="left" colspan="2">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBAddress3" readonly="readonly">
								</td>
							</tr>	
							<tr>
								<td align="left" nowrap class="columnFontSmall">
									Billing Address 4
								</td>
								<td align="left" colspan="2">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBAddress4" readonly="readonly">
								</td>
								
								<td align="left" nowrap class="columnFontSmall">
									Billing City
								</td>
								<td align="left" colspan="2">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBCity" readonly="readonly">
								</td>
								
								<td align="left" nowrap class="columnFontSmall">
									Billing State
								</td>
								<td align="left" colspan="2">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBState" readonly="readonly">
								</td>
							</tr>
							<tr>
								<td align="left" nowrap class="columnFontSmall">
									Billing Postal Code
								</td>
								<td align="left" colspan="2">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBPincode" readonly="readonly">
								</td>
								
								<td align="left" nowrap class="columnFontSmall">
									Billing Country Address
								</td>
								<td align="left" colspan="2">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBCountry" readonly="readonly">
								</td>
								
								<td align="left" nowrap class="columnFontSmall">
									Billing Contact Number
								</td>
								<td align="left" colspan="2">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBContactNo" readonly="readonly">
								</td>
							</tr>
							<!-- Start   [002] -->
							<tr>							
								<td align="left" nowrap class="columnFontSmall">
									Billing Email 
								</td>
								<td align="left" colspan="2">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBEmailId" readonly="readonly">
								</td>
								
								<td align="left" nowrap class="columnFontSmall">
									Designation 
								</td>
								<td align="left" colspan="2">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="designation" readonly="readonly">
								</td>
								
								<td align="left" nowrap class="columnFontSmall">
									LST NO
								</td>
								<td align="left" colspan="2">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="lst_No" readonly="readonly">
								</td>
							</tr>
							<tr>
								<td align="left" nowrap class="columnFontSmall">
									LST DATE
								</td>
								<td align="left" colspan="2">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="lst_Date" readonly="readonly">
								</td>
								<!-- end   [002] -->
								
								<td align="left" nowrap class="columnFontSmall">
								Billing Contact Name 
								</td>
								<td align="left" colspan="2">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBContactName" id="txtBContactName" readonly="readonly">
								</td>
								<!-- Start[005] -->
								<td align="left" nowrap class="columnFontSmall">
								Circle 
								</td>
								<td align="left" colspan="2">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtCircle" id="txtCircle" readonly="readonly">
								</td>
								<!-- End[005] -->
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</fieldset>	
</body>
</html>
