<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 MANISHA GARG	24-FEB-12	00-05422		Displaying Product Charge Data-->
<!--[002]	 MANISHA GARG	01-FEB-13	00-05422		Displaying LSI NO DEFECT NO 100-->
<!--[003]    Gunjan Singla  25-Sept-14  CBR_20140704-XX-020224    Global Data Billing Efficiencies Phase1 -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ibm.ioes.forms.ChargeSummaryProductCatelog"%>
<%@page import="com.ibm.ioes.forms.ChargeSummaryChargeSection"%>
<%@page import="com.ibm.ioes.forms.ChargeSummaryComponentSection"%>
<html>
<head>
<title>:: Charge Summary</title>

<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">
function show(tbl,btn)
{
	if (btn.value=="-")
	{
		document.all.item(tbl).style.display="None";
		btn.value="+";
	}
	else
	{
		document.all.item(tbl).style.display="Inline";
		btn.value="-";
	}
}


</script>


</head>
<body>
<fieldset class="border1">
	<legend> <b>Charge Summary</b> </legend>
	<%String []classTypes={"normal","lightBg"}; %>
	<bean:define id="formBean" name="ViewOrderLogicalFormBean" type="com.ibm.ioes.forms.ViewOrderFormBean"></bean:define>
	<table border="0" align="center" cellpadding="0" cellspacing="0" class="tab2" width="100%" height="650px;" >
		<tr class="lightBgWihtoutHover">
			<td width="100%" align="left" valign="top">
				<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="tab2" id="tblAMApprovalOrder" style="display:block;">
				<tr class="grayBg">
					<td></td>
					<td>Service No</td>
					<td>Service Type</td>
					<td>Product Type</td>
					<td>Service Sub Type</td>
					<td>Service Stage</td>
					<td>Customer Logical SI No</td>
					<td>Logical SI No</td>  <!-- [002] -->
					<td>RFS Date</td>
					<td>Effective Start Date</td>
					<td>Effective End Date</td>
					<td>Remarks</td>
				</tr>	
				<logic:present name="formBean" property="services">
					<logic:empty name="formBean" property="services">
						<tr align="center" class="grayBg">
							<td colspan="9">
								<b>No Charge Summary Exists</b>
							</td>
						</tr>
					</logic:empty>
					<logic:iterate id="service" name="formBean" property="services" indexId="count" type="com.ibm.ioes.forms.ChargeSummaryServiceDto">
					
<%							HashMap map_serviceId_prdCatelogs = formBean.getMap_serviceId_prdCatelogs();
							ArrayList prdCatlogs=(ArrayList)map_serviceId_prdCatelogs.get(service.getServiceId()); 
							if(prdCatlogs==null)
							{
								prdCatlogs=new ArrayList();
							}
						    request.setAttribute("prdCatlogs",prdCatlogs);
						    	
							int chargecounter=0;
							int comcounter=0;
							String flag="0";
							
							for (int i = 0; i < prdCatlogs.size(); i++) {
							
								HashMap map_serviceproductId_prdCatelogs_charges = formBean.getMap_serviceproductId_prdCatelogs_charges();
								ArrayList prdCatlogs_charges=(ArrayList)map_serviceproductId_prdCatelogs_charges.get(((ChargeSummaryProductCatelog)prdCatlogs.get(i)).getServiceproductid()); 
								if(prdCatlogs_charges!=null && prdCatlogs_charges.size()>0){
										chargecounter++;
								}
											
								HashMap map_serviceproductId_prdCatelogs_components = formBean.getMap_serviceproductId_prdCatelogs_components();
								ArrayList prdCatlogs_components=(ArrayList)map_serviceproductId_prdCatelogs_components.get(((ChargeSummaryProductCatelog)prdCatlogs.get(i)).getServiceproductid()); 
								if(prdCatlogs_components!=null && prdCatlogs_components.size()>0){
										comcounter++;
								}
								
								if(chargecounter>0 || comcounter>0){
								
									flag="1";
									break;						
								}
							}
							request.setAttribute("flag",flag);
%>
				
				<logic:notEqual name="flag" value="0">
				<logic:notEmpty name="prdCatlogs" >
						<tr class="<%=classTypes[count.intValue()%2] %>" >
							<td align="center">
										<input name="btn1"
										onClick="show('tbl<%=count %>',this)" type="button" value="+"
										style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;&nbsp;
							</td>
							<td align="center"><bean:write name="service" property="serviceId"/></td>
							<td align="center"><bean:write name="service" property="serviceType"/></td>
							<td align="center"><bean:write name="service" property="productType"/></td>
							<td align="center"><bean:write name="service" property="serviceSubType"/></td>
							<td align="center"><bean:write name="service" property="serviceStage"/></td>
							<td align="center"><bean:write name="service" property="custLogicalSINo"/></td>
							<td align="center"><bean:write name="service" property="logicalSINo"/></td>  <!-- [002] -->
							<td align="center"><bean:write name="service" property="rfsDate"/></td>
							<td align="center"><bean:write name="service" property="effectiveStartDate"/></td>
							<td align="center"><bean:write name="service" property="effectiveEndDate"/></td>
							<td align="center"><bean:write name="service" property="remarks"/></td>
						</tr>
						<tr class="grayBg" >
							<td align="center" colspan="11">
									<div style="height:100%;width: 925px" class="scroll" >
									<table  width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tab2"  id="tbl<%=count%>" style="display:none;" >
	<!-- //001 start -->
									<tr class="grayBg">
									<!-- //billing information -->
										<td align="center">Product Name</td>
										<td align="center">Service Type</td>
										<!-- [003] start -->
										<td align="center">Line Item No</td>
										<td align="center">SI ID </td>	
										<!-- [003] end -->
										<td align="center">Customer PO DetailNo</td>
										<td align="center">Customer PO Date</td>
										<td align="center">Contract Period</td>
										<td align="center">Account No</td>
										<td align="center">Credit Period</td>
										<td align="center">Currency</td>
										<td align="center">Legal Entity</td>
										<td align="center">Billing Mode</td>
										<td align="center">Bill Format</td>
										<td align="center">Licence Co</td>
										<td align="center">Billing Type</td>
										<td align="center">Taxation</td>
										<td align="center">Commitment Period</td>
										<td align="center">Billing Level</td>
										<td align="center">Penalty Clause</td>
										<!--  //billing address -->
										<td align="center">Bcp Id-Billing Address</td>
										<td align="center">Address1-Billing Address</td>
										<td align="center">Address2-Billing Address</td>
										<td align="center">Address3-Billing Address</td>
										<td align="center">Address4-Billing Address</td>
										<td align="center">City-Billing Address</td>
										<td align="center">State-Billing Address</td>
										<td align="center">Postal Code-Billing Address</td>
										<td align="center">Country-Billing Address</td>
										
										
										<!-- //service Location -->
										<td align="center">Location Type-Primary</td>
										<td align="center">Location Code-Primary</td>
										<td align="center">Address-Primary</td>
										<td align="center">Bcp Id-Primary</td>
										<td align="center">Address1-Primary</td>
										<td align="center">Address2-Primary</td>
										<td align="center">Address3-Primary</td>
										<td align="center">Address4-Primary</td>
										<td align="center">City-Primary</td>
										<td align="center">State-Primary</td>
										<td align="center">PostalCode-Primary</td>
										<td align="center">Country-Primary</td>
										
										<td align="center">Location Type-Secondary</td>
										<td align="center">Location Code-Secondary</td>
										<td align="center">Address-Secondary</td>
										<td align="center">Bcp Id-Secondary</td>
										<td align="center">Address1-Secondary</td>
										<td align="center">Address2-Secondary</td>
										<td align="center">Address3-Secondary</td>
										<td align="center">Address4-Secondary</td>
										<td align="center">City-Secondary</td>
										<td align="center">State-Secondary</td>
										<td align="center">PostalCode-Secondary</td>
										<td align="center">Country-Secondary</td>
										
										<!-- //hardware -->
										<td align="center">Store</td>
										<td align="center">Hardware Type</td>
										<td align="center">Form Available</td>
										<td align="center">Nature Of Sale</td>
										<td align="center">Type Of Sale</td>
										
										<!-- //dispatch address -->
										<td align="center">Dispatch Address Code</td>
										<td align="center">Address</td>
										<td align="center">City Name</td>
										<td align="center">State Name</td>
										<td align="center">Pin Code</td>
										<td align="center">Phone No</td>
										<td align="center">Country Name</td>
										
										<!-- //charge section -->
										
										<td align="center">Charge Type</td>
										<td align="center">Charge Name</td>
										<td align="center">Charge Period</td>
										<td align="center">Total Amount </td>
									    <td align="center">Charge Status</td>
									    <td align="center">Bill_Period</td>
										<td align="center">FrequencyAmt</td>
										<td align="center">Tax Rate</td>
							            <td align="center">Logic(Charge Start Date)</td>
										<td align="center">Days(Charge Start Date)</td>
										<td align="center">Months(Charge Start Date)</td>
										<td align="center">Charge Start Dt</td>
										<td align="center">Logic(Charge End Date)</td>
										<td align="center">Days(Charge End Date)</td>
										<td align="center">Months(Charge End Date)</td>
										<td align="center">Charge End Dt</td>
										<td align="center">Annual Rate</td>
										<td align="center">Annotation</td>
										<td align="center">Token No(Charge Start Details)</td>
										<td align="center">Fx Status(Charge Start Details)</td>
										<td align="center">Fx No(Charge Start Details)</td>
										<td align="center">Token No(Charge End Details)</td>
										<td align="center">Fx Status(Charge End Details)</td>
										<td align="center">Fx No(Charge End Details)</td>
										<td align="center">Charge Start Status</td>
								        <td align="center">Charge End Status</td>			
								        <td align="center">Frequency</td>
								        <td align="center">Charge Id</td>
								        <td align="center">FX View Id</td>	
								        <td align="center">Charge Remarks</td>	
										<!-- //component section -->
									    <td align="center">Components ID</td>
										<td align="center">Components Name</td>
										<td align="center">Package ID</td>
										<td align="center">Package Name</td>
										<td align="center">Components Status</td>
							            <td align="center">Logic(Component Start Date)</td>
							            <td align="center">Days(Component Start Date)</td>
							            <td align="center">Month(Component Start Date)</td>
								        <td align="center">Start Dt(Component Start Date)</td>
										<td align="center">Logic(Component End Date)</td>
										<td align="center">Days(Component End Date)</td>
							            <td align="center">Month(Component End Date)</td>
										<<td align="center">End Dt(Component End Date)</td>
										<td align="center">Token No(Component Start Details)</td>
										<td align="center">Fx Status(Component Start Details)</td>
										<td align="center">Fx No(Component Start Details)</td>
										<td align="center">Token No(Component End Details)</td>
										<td align="center">Fx Status(Component End Details)</td>
										<td align="center">Fx No(Component End Details)</td>
										<td align="center">Component Start Status</td>
										<td align="center">Component End Status</td>			
										<td align="center">IB2B Component Instance ID</td>
										<td align="center">FX Component Instance ID</td>
										<td align="center">Package Instance ID</td>
										<td align="center">Package Instance ID Serv</td>
												</tr>
										
		<logic:iterate id="prdCatelogRow" name="prdCatlogs" indexId="prdCatelogRowCount">
					   
<%		 				

						//for(int i=0;i<prdCatlogs.size();i++){
						HashMap map_serviceproductId_prdCatelogs_charges = formBean.getMap_serviceproductId_prdCatelogs_charges();
						ArrayList prdCatlogs_charges=(ArrayList)map_serviceproductId_prdCatelogs_charges.get(((ChargeSummaryProductCatelog)prdCatelogRow).getServiceproductid()); 
						
						if(prdCatlogs_charges==null)
						{
								prdCatlogs_charges=new ArrayList();
						}
						request.setAttribute("prdCatlogs_charges",prdCatlogs_charges);
													
						HashMap map_serviceproductId_prdCatelogs_components = formBean.getMap_serviceproductId_prdCatelogs_components();
						ArrayList prdCatlogs_components=(ArrayList)map_serviceproductId_prdCatelogs_components.get(((ChargeSummaryProductCatelog)prdCatelogRow).getServiceproductid());
						if(prdCatlogs_components==null)
						{
							prdCatlogs_components=new ArrayList();
						}
				   request.setAttribute("prdCatlogs_components",prdCatlogs_components);
				
						
%>												
					  <logic:iterate id="prdCatelogchargesRow" name="prdCatlogs_charges" indexId="prdCatelogchargesRowCount">											
									 	    <tr class="<%=classTypes[prdCatelogchargesRowCount.intValue()%2] %>">
									 		<td align="center"><bean:write name="prdCatelogRow" property="productName"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="serviceType"/></td>
											<!-- [003] start -->
											<td align="center"><bean:write name="prdCatelogRow" property="serviceproductid"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="siId"/></td>
									     		<!-- [003] start -->
											<!-- //billing information -->
											<td align="center"><bean:write name="prdCatelogRow" property="custPODetailNo"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="poDate"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="contractPeriod"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="acNo"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="creditPeriod"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="currency"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="legalEntity"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="billingMode"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="billFormat"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="licenceCo"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="billingType"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="taxation"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="commitmentPeriod"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="billingLevel"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="penaltyClause"/></td>
											<!-- //billing address -->
											<td align="center"><bean:write name="prdCatelogRow" property="bcpDetails_BillingAddress"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address1_BillingAddress"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address2_BillingAddress"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address3_BillingAddress"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address4_BillingAddress"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="city_BillingAddress"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="state_BillingAddress"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="postalCode_BillingAddress"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="country_BillingAddress"/></td>
											
											
											<!-- //service Location -->
											<td align="center"><bean:write name="prdCatelogRow" property="locType_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="locationCode_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="bcpDetails_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address1_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address2_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address3_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address4_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="city_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="state_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="postalCode_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="country_Primary"/></td>
											
											<td align="center"><bean:write name="prdCatelogRow" property="locType_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="locationCode_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="bcpDetails_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address1_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address2_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address3_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address4_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="city_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="state_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="postalCode_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="country_Secondary"/></td>
											
											<!-- //hardware -->
											<td align="center"><bean:write name="prdCatelogRow" property="store"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="hardwareType"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="formAvailable"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="natureOfSale"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="typeOfSale"/></td>
											
											<!-- //dispatch address -->
											<td align="center"><bean:write name="prdCatelogRow" property="dispatchAddressCode"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="cityName"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="stateName"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="pinCode"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="phoneNo"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="countryName"/></td>
											
											 <!-- //Charge Summary -->
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="charges_type"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="charge_name"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="chargeperiod"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="chargeamount"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="chargestatus"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="bill_period"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="frequencyamt"/></td>
								             <td align="center"><bean:write name="prdCatelogchargesRow" property="tax_Rate"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="startdatelogic"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="start_date_days"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="start_date_month"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="charge_start_date"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="enddatelogic"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="end_date_days"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="end_date_month"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="charge_end_date"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="annual_rate"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="annotation"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="start_token_no"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="start_fx_status"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="start_fx_no"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="end_token_no"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="end_fx_status"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="end_fx_no"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="charge_start_status"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="charge_end_status"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="chargefrequency"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="charge_id"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="fx_view_id"/></td>
								            <td align="center"><bean:write name="prdCatelogchargesRow" property="chargeRemarks"/></td>
								             <!-- //Component Summary -->
								            <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										   
										    
										    
						</logic:iterate>
							<logic:iterate id="prdCatelogcomponentsRow" name="prdCatlogs_components" indexId="prdCatelogcomponentsRowCount">											
									 	    <tr class="<%=classTypes[prdCatelogcomponentsRowCount.intValue()%2] %>">
									 		<td align="center"><bean:write name="prdCatelogRow" property="productName"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="serviceType"/></td>
											<!-- [003] start -->
											<td align="center"><bean:write name="prdCatelogRow" property="serviceproductid"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="siId"/></td>
									     		<!-- [003] start -->
											<!-- //billing information -->
											<td align="center"><bean:write name="prdCatelogRow" property="custPODetailNo"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="poDate"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="contractPeriod"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="acNo"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="creditPeriod"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="currency"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="legalEntity"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="billingMode"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="billFormat"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="licenceCo"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="billingType"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="taxation"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="commitmentPeriod"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="billingLevel"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="penaltyClause"/></td>
											<!-- //billing address -->
											<td align="center"><bean:write name="prdCatelogRow" property="bcpDetails_BillingAddress"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address1_BillingAddress"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address2_BillingAddress"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address3_BillingAddress"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address4_BillingAddress"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="city_BillingAddress"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="state_BillingAddress"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="postalCode_BillingAddress"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="country_BillingAddress"/></td>
											
											
											<!-- //service Location -->
											<td align="center"><bean:write name="prdCatelogRow" property="locType_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="locationCode_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="bcpDetails_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address1_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address2_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address3_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address4_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="city_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="state_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="postalCode_Primary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="country_Primary"/></td>
											
											<td align="center"><bean:write name="prdCatelogRow" property="locType_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="locationCode_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="bcpDetails_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address1_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address2_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address3_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address4_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="city_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="state_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="postalCode_Secondary"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="country_Secondary"/></td>
											
											<!-- //hardware -->
											<td align="center"><bean:write name="prdCatelogRow" property="store"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="hardwareType"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="formAvailable"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="natureOfSale"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="typeOfSale"/></td>
											
											<!-- //dispatch address -->
											<td align="center"><bean:write name="prdCatelogRow" property="dispatchAddressCode"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="address"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="cityName"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="stateName"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="pinCode"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="phoneNo"/></td>
											<td align="center"><bean:write name="prdCatelogRow" property="countryName"/></td>
											
										    <!-- //Charge Summary -->
											<td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <td align="center"></td>
										    <!-- //Component Summary -->
										        
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="component_id"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="component_name"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="package_id"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="package_name"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="component_status"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="component_start_logic"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="component_start_days"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="component_start_month"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="component_start_date"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="component_end_logic"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="component_end_days"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="component_end_month"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="component_end_date"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="start_token_no"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="start_fx_status"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="start_fx_no"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="end_token_no"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="end_fx_status"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="end_fx_no"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="fx_start_status"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="fx_end_status"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="componentinfoid"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="cominstid"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="packageInstId"/></td>
											<td align="center"><bean:write name="prdCatelogcomponentsRow" property="packageInstIdServ"/></td>
									      
								<!-- //001 end -->			
											
											
				</logic:iterate>	
				
				</logic:iterate>					 	
									 		</table>
								</div>
							</td>
						</tr>
						</logic:notEmpty>
				</logic:notEqual>		 	
				</logic:iterate>			
				</logic:present>
				</table>
			</td>
		</tr>
	</table>
</fieldset>
</body>
</html>
