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
<title>BillingInfo</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<body>
	
		<!-- <input name="btn1" id="btnBillingInformation"
			onClick="show('tblBillingInformation',this)" type="button" value="+"
			style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;
		 -->
			<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblBillingInformation" style="display: Inline;">
				<tr>
					<td width="100%" height="100%" valign="top">
					<div align="left"><b>Billing Info Details:</b></div>
						<table border="1" cellspacing="0" cellpadding="0"  width="100%">
							<tr>
							<!-- Start[004] -->
								<td width="16%" align="left" nowrap class="columnFontSmall">
									Cust PO Detail Number
								</td>
								<td width="32%" align="left" nowrap><select onmouseover="getTip_DD(this,this.selectedIndex,this.name)"  name="txtPODetailNo" style="width:150px"
									id="txtPODetailNo" class="dropdownMargin"
									isRequired="0" onfocus="getPrevCustPoNo(this.selectedIndex)" onchange="if(checkchargevalidation()==false){return false;}else{fnFetchEntity();selectPoId(this)}">
									<option value="0">Select Cust PO Detail No</option>
									</select>
								</td>		
								<!-- End[004] -->
									<td width="17%" align="left" nowrap class="columnFontSmall">
										PO Date
									</td>
									<td width="16%" align="left" nowrap class="columnFontSmall">
										<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  class="inputBorder1" name="txtBillingPODate" id="txtBillingPODate" isRequired="0" style="width:90px" readonly="true" value="<%=request.getParameter("orderDate")%>">
									</td>
								<td width="40%" nowrap class="columnFontSmall">
									Contract Period 
								</td>
								<td width="17%" align="left" nowrap class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  class="inputBorder1" name="txtCntrtMonths" id="txtCntrtMonths"  style="width:50px" isRequired="0" readonly="true">
									(Months)
								</td>
							</tr>
								
							<tr>
								<td width="16%" align="left" nowrap class="columnFontSmall">
									 Legal Entity
								</td>
								<td width="32%" align="left" nowrap>									
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.name)"  name="txtEntity" style="width:150px"
										id="txtEntity" class="dropdownMargin" onchange="fnFetchLicCompany()" isRequired="0">
										<option value="0">Select Legal Entity</option>
									</select>									
								</td>
								<td width="17%" align="left" nowrap class="columnFontSmall">
									 A\C No
								</td>
								<td width="16%" align="left" nowrap class="columnFontSmall">																		
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder1" style="width:90px"
									name="txtBillingAccNo" id="txtBillingAccNo" isRequired="0"
									value="<%=request.getParameter("crmAccountName")%>" readonly="true"
									size="24">
								</td>
								<td width="40%" nowrap class="columnFontSmall">
								 Credit Period
								</td>
								<td width="17%" align="left" nowrap class="columnFontSmall">
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.name)"  name="txtBillingCP" id="txtBillingCP" class="inputBorder1" isRequired="0" >
										<option value="0">Select Credit Period</option>
									</select>
									<input type="hidden" name="hdnBillingCP">
									<input type="hidden" name="txtBillingAC" id="txtBillingAC" value="<%=request.getParameter("accountID")%>">																		
								</td>
							</tr>
							
							<tr>
								<td width="16%" align="left" nowrap class="columnFontSmall">
										   Bill Format
								</td>
								<td width="32%" align="left" nowrap>
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.name)"  onfocus="getPrevBillFormat(this.selectedIndex)"  name="txtBillingformat" style="width:150px" id="txtBillingformat" class="dropdownMargin" isRequired="0" onchange="setTypeofSale()">
										<option value="0">Select Bill Format</option>									
									</select>
									<input type="hidden" name="hdnBillingformat">
									
								</td>
								<td width="17%" align="left" nowrap class="columnFontSmall">
									Taxation								
								</td>
								<td width="16%" align="left" nowrap class="columnFontSmall">
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.name)"  name="txtTaxation" style="width:90px"
									class="dropdownMargin" id="txtTaxation" isRequired="0" onchange=setPopulateStdReason(this.value);>
									<option value="0">Select Taxation</option>
				
									</select> <input type="hidden" name="hdnTaxation">									
								</td>
								
								<td width="40%" nowrap class="columnFontSmall">
									Standard Reason
								</td>
								<td width="17%" align="left" nowrap class="columnFontSmall">
									<select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' name="changeReason" style="width:150px" id="changeReason" class="dropdownMargin" >
										<option value="0">Select Standard Reason</option>
									</select>
								</td>																
							</tr>
								
							<tr>
								<td width="16%" align="left" nowrap class="columnFontSmall">
									Licence Co 
								</td>
								<td width="32%" align="left" nowrap>
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.name)"  name="txtLicenceCo" style="width:150px"
									id="txtLicenceCo" class="dropdownMargin" isRequired="0" onchange='populateStore(path);'>
									<option value="0">Select Licence Company</option>
									</select><input type="hidden" name="hdnLicenceCo">
								</td>
								<td width="17%" align="left" nowrap class="columnFontSmall">		
							<!--[001] start -->
										 PO ID
								</td>
								<td width="16%" align="left" nowrap class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" id="txtPoId" name="txtPoId" value="0" style="margin-left:10px; width:90px"   readonly="readonly">
									<input type="hidden" name="hdnBillingInfoID">
									
								</td>								
								<!--[001] end -->											
								<td width="40%" nowrap class="columnFontSmall">
								 	Notice Period
								</td>
								<td width="17%" align="left" nowrap class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" style="width:50px" class="inputBorder1" name="txtNoticePeriod" value="0" id="txtNoticePeriod" maxlength="5" isRequired="0" onblur="if( trim(this.value).length>0){ return checkdigits(this)};"/>
									<!-- Added Days in label By Lawkush Start-->
										(Days)	
									<!-- Added Days in label By Lawkush End-->								
								</td>
							</tr>														
							
							<tr>
								<td width="16%" align="left" nowrap class="columnFontSmall">
									Billing Level
								</td>
								<td width="32%" align="left" nowrap>
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.name)"  name="txtBillingLvl"  style="width:150px" id="txtBillingLvl"class="dropdownMargin"  onchange=setBillingLevelNo(this.value); isRequired="0">
										<option value="0">Select Billing Level</option>
									</select>
									<input type="hidden" name="hdnBillingLvl"> 
								</td>
								
								<td width="17%" align="left" nowrap class="columnFontSmall">		
									Billing Level No
								</td>
								<td width="16%" align="left" nowrap class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder1" maxlength="5" style="width:90px;float:left;"
								 	value="0" name="txtBillingLevelNo" id="txtBillingLevelNo"
									readonly="readonly" isRequired="0"  onblur="if( trim(this.value).length>0){return checkdigits(this)};">
								</td>
									
								<!-- Start[004] -->
								<td width="40%" nowrap class="columnFontSmall">
									 Billing Type
								</td>
								<td width="17%" align="left" nowrap class="columnFontSmall">
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.name)"  name="txtBillingType" style="width:150px" class="dropdownMargin" id="txtBillingType">
										<option value="0">Select Bill Type</option>
									
									</select>
									<input type="hidden" name="hdnBillingType">
								</td>
								<!-- End[004] -->
							</tr>
							
							<tr>
								<td width="16%" align="left" nowrap class="columnFontSmall">
									Billing Mode
								</td>
								<td width="32%" align="left" nowrap>
									<select onfocus="getTip_DD(this,this.selectedIndex,this.name)" name="txtBillingMode" id="txtBillingMode" class="dropdownMargin" isRequired="0" style="width:150px" >
												<option value="0" title="Select Billing Mode">Select Billing Mode</option>
												
												
									</select>									
								</td>
								<td width="17%" align="left" nowrap class="columnFontSmall">		
									Currency	
								</td>
								<td width="16%" align="left" nowrap class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" name="txtCur" style="width:90px" class="inputBorder1" value="<bean:write name="newOrderBean" property="curShortCode"/>" readonly="true">
								</td>
								<!--Start[003]  -->
								<td width="40%" nowrap class="columnFontSmall">
									Commitment Period
								</td>
								<td width="17%" align="left" nowrap class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  style="width:50px" class="inputBorder1" name="txtCmtPeriod" maxlength="5" value="0" id="txtCmtPeriod" isRequired="0" onblur="if( trim(this.value).length>0){ return checkdigits(this)};">
									(Months)
								</td>								
								<!-- End[003] -->
							</tr>
							<tr>
								<td width="16%" align="left" nowrap class="columnFontSmall">
									Usage	
								</td>
								<td width="32%" align="left" nowrap>
									<input name="chkIsUsage" id="chkIsUsage" value="0" type="checkbox" disabled="disabled">
								</td>
								<td width="17%" align="left" nowrap class="columnFontSmall">		
									Charge Redirection LSI	
								</td>
								<td width="16%" align="left" nowrap class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  style="width:80px;" class="inputBorder1" name="txtFxRedirectionLSI" id="txtFxRedirectionLSI" isRequired="0" readonly="readonly" value="0" >	
									<div class="searchBg" style="float: left" isDataEntry="1"><a href="#" onclick="popUpForArborLSILookup()" id="popUpLsiLink">..</a></div>
								</td>
								<!--Start[003]  -->
								<td width="40%" nowrap class="columnFontSmall">
									Billing Scenario
								</td>
								<td width="17%" align="left" nowrap class="columnFontSmall">
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.name)"  name="txtBillingScenario"  id="txtBillingScenario" class="dropdownMargin" disabled="disabled" isRequired="0">
										<option value="0">DEFAULT</option>
										<option value="1">CONSOLIDATE</option>
										<option value="2">CUMULATIVE</option>
									</select>
								</td>								
								<!-- End[003] -->
							</tr>							
							<tr>
											
								<td width="20%" nowrap class="columnFontSmall">
									OSP
								</td>
								<td width="20%" align="left" nowrap class="columnFontSmall">
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.name)"  name="txtOSPTagging" id="txtOSPTagging" class="inputBorder1" isRequired="1" style="width:100px" onchange="checkOSPTaggingMandate(this.value)">
										<option value="0">No</option>
										<option value="1">Yes</option>
									</select>
								</td>	
								<td width="20%" nowrap class="columnFontSmall">
									OSP RegNo
								</td>
								<td width="20%" align="left" nowrap class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  style="width:120px"  maxlength="150" class="inputBorder2" name="txtOSPRegNo" value="" id="txtOSPRegNo" isRequired="0" disabled="true" >
									
								</td>		
								<td width="20%" nowrap class="columnFontSmall">
									OSP RegDate(dd/mm/yyyy)
								</td>
								<td width="20%" align="left" nowrap class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text"  style="width:120px"  class="inputBorder2" name="txtOSPRegDate" value="" id="txtOSPRegDate" isRequired="0" disabled="true"  onblur="if(this.value.length > 0){return checkdate(this)}" />&nbsp;<font size="1"><a href="javascript:show_calendar(document.getElementById('txtOSPRegDate'));"   class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" id="ospDateImage"  border="0px" alt="Loading..."></a>
									</font>
								</td>				
							</tr>							
							<tr>
								<td width="16%" align="left" nowrap class="columnFontSmall">
									Penalty Clause
								</td>
								<td colspan="3" align="left">
									<textarea  class="inputBorder1" rows="3" id="txtPenaltyClause" name="txtPenaltyClause" maxlength="999" style="width:100%;" isRequired="0" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Penalty Clause')};"></textarea> 
								</td>	
								
								<td width="40%" nowrap class="columnFontSmall">
									 NFA 
								</td>
								<td width="17%" align="left" nowrap class="columnFontSmall">
									<input name="chkSelectNfa" id="chkSelectNfa" type="checkbox" onClick="checkNfa()">	
								</td>							
							</tr>																			
							<!-- WARRANTY CLAUSE ADDED BY MANISHA START -->
							<tr>
								<td width="16%" align="left" nowrap class="columnFontSmall">
									Warranty Clause
								</td>
								<td colspan="3" align="left">
									<textarea  class="inputBorder1" rows="3" id="txtWarrantClause" name="txtWarrantClause" maxlength="999" style="width:100%;" isRequired="1" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'WarrantyClause')};"></textarea> 
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
									<input type="text" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:32%;float:left;" name="txtBCPAddressSuggestService" ctrltype='dropdown' retval='AUTOSUGGESTBCPSERVICE' id="txtBCPAddressSuggestService" class="dropdownMargin"><a id="bcpAutoSuggestServiceLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownBillingforService()">Show</a>								
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
							<td width="16%" align="left" nowrap class="columnFontSmall">
								<div align="left"><b>Billing BCP Details</b></div>
							</td>
							<td colspan="5" align="left" nowrap>
									<!--     Select BCP commented lawkush  
									<select onmouseover="getTip_DD(this,this.selectedIndex,this.name)" name="bbPrimaryBCP" id="bbPrimaryBCPID" style="width:60%;float:left;" class="dropdownMargin" onchange="FetchBillingDetails()" size="0">
											<option value="">Select BCP Details</option>
									</select>-->
									 <!--   Auto suggest BCP Start lawkush  -->
									<input type="text" onmouseover="getTip(value)" onmouseout="UnTip()" style="width:32%;float:left;" name="txtBCPAddressSuggest" ctrltype='dropdown' retval='AUTOSUGGESTBCP' id="txtBCPAddressSuggest" class="dropdownMargin"><a id="bcpAutoSuggestLinkId" ctrltype='dropdownlink' onclick="javascript:getDropDownBilling()">Show</a>								
									<input type="hidden" name="bbPrimaryBCP" id="bbPrimaryBCPID" isrequired="1">
									 <!--   Auto suggest BCP End lawkush  -->
							</td>
						</tr>
						<tr>							
								<td width="16%" align="left" nowrap class="columnFontSmall">
								    Address 1
								</td>
								<td width="32%" colspan="2" align="left" nowrap>
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBAddress1" id="txtBAddress1" readonly="readonly">
								</td>
								<td width="16%" align="left" nowrap class="columnFontSmall" > Address 2</td>
								<td width="40%" colspan="2" nowrap class="columnFontSmall">
                                  	<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBAddress2" id="txtBAddress2" readonly="readonly">
								</td>
					  </tr>
					  <tr>							
								<td width="16%" align="left" nowrap class="columnFontSmall">
								    Address 3
								</td>
								<td width="32%" colspan="2" align="left" nowrap>
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBAddress3" id="txtBAddress3" readonly="readonly">
								</td>
								<td width="16%" align="left" nowrap class="columnFontSmall" > Address 4</td>
								<td width="40%" colspan="2" nowrap class="columnFontSmall">
                                  	<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBAddress4" id="txtBAddress4" readonly="readonly">	
						</td>
					  </tr>
					  <tr>							
								<td width="16%" align="left" nowrap class="columnFontSmall">
								    Postal Code
								</td>
								<td width="32%" colspan="2" align="left" nowrap>
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBPincode" id="txtBPincode" readonly="readonly">
								</td>
								<td width="16%" align="left" nowrap class="columnFontSmall" >
								    Email 
								</td>
								<td width="40%" colspan="2" nowrap class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBEmailId" id="txtBEmailId" readonly="readonly">	
								</td>
					  </tr>
					  <tr>							
								<td width="16%" align="left" nowrap class="columnFontSmall">
									Designation 
								</td>
								<td colspan="2" align="left" nowrap>
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="designation" id="designation" readonly="readonly">
								</td>
								<td width="16%" align="left" nowrap class="columnFontSmall" >
								    Contact Number
								</td>
								<td colspan="2" nowrap class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBContactNo" id="txtBContactNo" readonly="readonly">		
								</td>
					  </tr>
					  <tr>							
								<td width="16%" align="left" nowrap class="columnFontSmall">
									Lst Date
								</td>
								<td colspan="2" align="left" class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="lst_Date" id="lst_Date" readonly="readonly">
								</td>
								<td width="16%" align="left" class="columnFontSmall" >
									Lst No
								</td>
								<td colspan="2" class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="lst_No" id="lst_No" readonly="readonly">	
								</td>
					  </tr>
					  <tr>							
								<td width="16%" align="left" nowrap class="columnFontSmall">
								    City
								</td>
								<td colspan="2" align="left" class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBCity" id="txtBCity" readonly="readonly">
								</td>
								<td width="16%" align="left" class="columnFontSmall" >
								    State
								</td>
								<td colspan="2" class="columnFontSmall">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBState" id="txtBState" readonly="readonly">
								</td>
					  </tr>
					  <tr>							
								<td width="16%" align="left" nowrap class="columnFontSmall">
							    Country </td>
								<td colspan="2" align="left">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBCountry" id="txtBCountry" readonly="readonly">
								</td>
								<td width="16%" align="left" nowrap class="columnFontSmall">
							    Billing Contact Name </td>
								<td colspan="2" align="left">
									<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtBContactName" id="txtBContactName" readonly="readonly">
								</td>
					  </tr>
					<!-- Start[005] -->
						<tr>
							<td width="16%" align="left" class="columnFontSmall" >
								Circle
							</td>
							<td colspan="2" class="columnFontSmall">
								<input onmouseover="getTip(value)" onmouseout="UnTip()" type="text" class="inputBorder" style="width:80%;float:left;" name="txtCircle" id="txtCircle" readonly="readonly">									
							</td>
							<td colspan="3" class="columnFontSmall">
								&nbsp;
							</td>
						</tr>
					<!-- End[005] -->
												
					</table>
					</td>
				</tr>
			</table>		
</body>
</html>
