<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 MANISHA GARG	01-FEB-13	00-05422		Displaying LSI NO DEFECT NO 100-->
<!--[002]   Gunjan Singla 26-sep-14  CBR_20140704-XX-020224     Global Data Billing Efficiencies Phase1-->
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.forms.ChargeSummaryProductCatelog"%>
<%@page import="com.ibm.ioes.forms.ChargeSummaryChargeSection"%>
<%@page import="com.ibm.ioes.forms.ChargeSummaryComponentSection"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<html:html>
<head>
<%
response.setContentType("application/vnd.ms-excel");
%>
</head>
<body>
	<% response.setHeader("content-Disposition","attachment;filename=ChargeSummary.xls"); %>
	<%String []classTypes={"normal","lightBg"}; %>
	<bean:define id="formBean" name="ViewOrderLogicalFormBean" type="com.ibm.ioes.forms.ViewOrderFormBean"></bean:define>
	<table border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" width="100%" height="650px;" >
		<tr>
			<td colspan="17" align="left" style="font-size:10px"><strong>CHARGE SUMMARY</strong></td>
		</tr>
		<tr class="lightBgWihtoutHover">
			<td width="100%" align="left" valign="top">
				<table width="100%" border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" id="tblAMApprovalOrder" style="display:block;">
					<!--<tr class="grayBg">
						<td></td>
						<td>Service No</td>
						<td>Service Type</td>
						<td>Service Sub Type</td>
						<td>Service Stage</td>
						<td>Customer Logical SI No</td>
						<td>Effective Start Date</td>
						<td>Effective End Date</td>
						<td>Remarks</td>
					</tr>-->	
					<logic:present name="formBean" property="services">
						<tr class="grayBg" >
							<td align="center" colspan="9">
								<div style="height:100%;width: 925px" class="scroll" >
									<table  width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2"  id="tbl" style="display:block;" >
										<tr class="grayBg">
											<td align="center" style="font-size:9px;"  bgcolor="#FF9255"><b>Service No</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Service Type</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Product Type</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Service Sub Type</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Service Stage</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Customer Logical SI No</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Logical SI No</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>RFS Date</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Effective Start Date</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Line Item Name</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Service Type</b></td>
											<!--[002] start -->
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Line Item No</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>SI ID</b></td>
											<!--[002] end -->
											<!-- //billing information -->
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Customer PO DetailNo</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Customer PO Date</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Contract Period</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Account No</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Credit Period</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Currency</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Legal Entity</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Billing Mode</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Bill Format</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Licence Co</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Billing Type</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Taxation</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Commitment Period</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Billing Level</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Penalty Clause</b></td>
											<!--  //billing address -->
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Bcp Id-Billing Address</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Address1-Billing Address</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Address2-Billing Address</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Address3-Billing Address</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Address4-Billing Address</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>City-Billing Address</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>State-Billing Address</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Postal Code-Billing Address</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Country-Billing Address</b></td>
											<!-- //service Location -->
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Location Type-Primary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Location Code-Primary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Address-Primary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Bcp Id-Primary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Address1-Primary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Address2-Primary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Address3-Primary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Address4-Primary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>City-Primary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>State-Primary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>PostalCode-Primary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Country-Primary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Location Type-Secondary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Location Code-Secondary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Address-Secondary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Bcp Id-Secondary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Address1-Secondary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Address2-Secondary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Address3-Secondary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Address4-Secondary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>City-Secondary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>State-Secondary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>PostalCode-Secondary</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Country-Secondary</b></td>
											<!-- //hardware -->
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Store</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Hardware Type</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Form Available</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Nature Of Sale</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Type Of Sale</b></td>
											<!-- //dispatch address -->
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Dispatch Address Code</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Address</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>City Name</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>State Name</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Pin Code</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Phone No</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Country Name</b></td>
											<!-- //charge section -->
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Charge Type</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Charge Name</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Charge Period</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Total Amount </b></td>
										    <td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Charge Status</b></td>
										    <td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Bill_Period</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>FrequencyAmt</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Tax Rate</b></td>
								            <td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Logic(Charge Start Date)</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Days(Charge Start Date)</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Months(Charge Start Date)</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Charge Start Dt</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Logic(Charge End Date)</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Days(Charge End Date)</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Months(Charge End Date)</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Charge End Dt</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Annual Rate</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Annotation</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Token No(Charge Start Details)</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Fx Status(Charge Start Details)</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Fx No(Charge Start Details)</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Token No(Charge End Details)</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Fx Status(Charge End Details)</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Fx No(Charge End Details)</b></td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Charge Start Status</b></td>
									        <td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Charge End Status</b></td>			
									        <td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Frequency</b></td>
									        <td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Charge Id</b></td>
									        <td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>FX View Id</b></td>
									        <td align="center" style="font-size:10px;"  bgcolor="#FF9255"><b>Charge Remarks</b></td>
									        <td align="center" style="font-size:10px;"  bgcolor="#FF9255">Components ID</td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">Components Name</td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">Package ID</td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">Package Name</td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">Components Status</td>
								            <td align="center" style="font-size:10px;"  bgcolor="#FF9255">Logic(Component Start Date)</td>
								            <td align="center" style="font-size:10px;"  bgcolor="#FF9255">Days(Component Start Date)</td>
								            <td align="center" style="font-size:10px;"  bgcolor="#FF9255">Month(Component Start Date)</td>
									        <td align="center" style="font-size:10px;"  bgcolor="#FF9255">Start Dt(Component Start Date)</td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">Logic(Component End Date)</td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">Days(Component End Date)</td>
								            <td align="center" style="font-size:10px;"  bgcolor="#FF9255">Month(Component End Date)</td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">End Dt(Component End Date)</td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">Token No(Component Start Details)</td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">Fx Status(Component Start Details)</td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">Fx No(Component Start Details)</td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">Token No(Component End Details)</td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">Fx Status(Component End Details)</td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">Fx No(Component End Details)</td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">Component Start Status</td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">Component End Status</td>			
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">IB2B Component Instance ID</td>
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">FX Component Instance ID</td>	
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">Package Instance ID</td>	
											<td align="center" style="font-size:10px;"  bgcolor="#FF9255">Package Instance ID Serv</td>	
										</tr>
										<logic:empty name="formBean" property="services">
											<tr align="center" class="grayBg">
												<td colspan="17">
													<b>No Charge Summary Exists</b>
												</td>
											</tr>
										</logic:empty>
										<logic:notEmpty name="formBean" property="services">
											<logic:iterate id="service" name="formBean" property="services" indexId="count" type="com.ibm.ioes.forms.ChargeSummaryServiceDto">
							
												<%HashMap map_serviceId_prdCatelogs = formBean.getMap_serviceId_prdCatelogs();
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
												request.setAttribute("flag",flag);%>
										<logic:notEqual name="flag" value="0">
												<logic:notEmpty name="prdCatlogs" >
												<!--  <tr class="<%=classTypes[count.intValue()%2] %>" >
														<td align="center">
														<input name="btn1" onClick="show('tbl<%=count %>',this)" type="button" value="+" style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;&nbsp;</td>
														<td align="center"><bean:write name="service" property="serviceId"/></td>
														<td align="center"><bean:write name="service" property="serviceType"/></td>
														<td align="center"><bean:write name="service" property="serviceSubType"/></td>
														<td align="center"><bean:write name="service" property="serviceStage"/></td>
														<td align="center"><bean:write name="service" property="custLogicalSINo"/></td>
														<td align="center"><bean:write name="service" property="effectiveStartDate"/></td>
														<td align="center"><bean:write name="service" property="effectiveEndDate"/></td>
														<td align="center"><bean:write name="service" property="remarks"/></td>
														</tr>-->
										
		<logic:iterate id="prdCatelogRow" name="prdCatlogs" indexId="prdCatelogRowCount">
					<%//for(int i=0;i<prdCatlogs.size();i++){
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
					   	   request.setAttribute("prdCatlogs_components",prdCatlogs_components);	%>	
													   	   											
					  <logic:iterate id="prdCatelogchargesRow" name="prdCatlogs_charges" indexId="prdCatelogchargesRowCount">											
						    <tr class="<%=classTypes[prdCatelogchargesRowCount.intValue()%2] %>">
	 	    					<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="serviceId"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="serviceType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="productType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="serviceSubType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="serviceStage"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="custLogicalSINo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="logicalSINo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="rfsDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="effectiveStartDate"/></td>
								<!-- //billing information -->
						 		<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="productName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="serviceType"/></td>
								<!-- [002] start -->
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="serviceproductid"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="siId"/></td>
								<!-- [002] end -->
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="custPODetailNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="poDate"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="contractPeriod"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="acNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="creditPeriod"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="currency"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="legalEntity"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="billingMode"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="billFormat"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="licenceCo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="billingType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="taxation"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="commitmentPeriod"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="billingLevel"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="penaltyClause"/></td>
								<!-- //billing address -->
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="bcpDetails_BillingAddress"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address1_BillingAddress"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address2_BillingAddress"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address3_BillingAddress"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address4_BillingAddress"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="city_BillingAddress"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="state_BillingAddress"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="postalCode_BillingAddress"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="country_BillingAddress"/></td>
								<!-- //service Location -->
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="locType_Primary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="locationCode_Primary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address_Primary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="bcpDetails_Primary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address1_Primary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address2_Primary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address3_Primary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address4_Primary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="city_Primary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="state_Primary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="postalCode_Primary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="country_Primary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="locType_Secondary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="locationCode_Secondary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address_Secondary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="bcpDetails_Secondary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address1_Secondary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address2_Secondary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address3_Secondary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address4_Secondary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="city_Secondary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="state_Secondary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="postalCode_Secondary"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="country_Secondary"/></td>
								<!-- //hardware -->
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="store"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="hardwareType"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="formAvailable"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="natureOfSale"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="typeOfSale"/></td>
								<!-- //dispatch address -->
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="dispatchAddressCode"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="cityName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="stateName"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="pinCode"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="phoneNo"/></td>
								<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="countryName"/></td>
								 <!-- //Charge Summary -->
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="charges_type"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="charge_name"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="chargeperiod"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="chargeamount"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="chargestatus"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="bill_period"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="frequencyamt"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="tax_Rate"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="startdatelogic"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="start_date_days"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="start_date_month"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="charge_start_date"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="enddatelogic"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="end_date_days"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="end_date_month"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="charge_end_date"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="annual_rate"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="annotation"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="start_token_no"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="start_fx_status"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="start_fx_no"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="end_token_no"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="end_fx_status"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="end_fx_no"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="charge_start_status"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="charge_end_status"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="chargefrequency"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="charge_id"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="fx_view_id"/></td>
					            <td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogchargesRow" property="chargeRemarks"/></td>
					            <!-- //Component Info start -->
					            <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <td align="left" style="font-size:9px" width="5%"></td>
							    <!-- //Component Info end-->
							</tr>
				  </logic:iterate>
				  <logic:iterate id="prdCatelogcomponentsRow" name="prdCatlogs_components" indexId="prdCatelogcomponentsRowCount">											
			 	   	 	<tr class="<%=classTypes[prdCatelogcomponentsRowCount.intValue()%2] %>">
					 	   	<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="serviceId"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="serviceType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="productType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="serviceSubType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="serviceStage"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="custLogicalSINo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="logicalSINo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="rfsDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="service" property="effectiveStartDate"/></td>
							<!-- //billing information -->
					 		<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="productName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="serviceType"/></td>
							<!-- [002] start -->
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="serviceproductid"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="siId"/></td>
							<!-- [002] end -->
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="custPODetailNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="poDate"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="contractPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="acNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="creditPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="currency"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="legalEntity"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="billingMode"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="billFormat"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="licenceCo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="billingType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="taxation"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="commitmentPeriod"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="billingLevel"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="penaltyClause"/></td>
							<!-- //billing address -->
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="bcpDetails_BillingAddress"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address1_BillingAddress"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address2_BillingAddress"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address3_BillingAddress"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address4_BillingAddress"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="city_BillingAddress"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="state_BillingAddress"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="postalCode_BillingAddress"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="country_BillingAddress"/></td>
							
							
							<!-- //service Location -->
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="locType_Primary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="locationCode_Primary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address_Primary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="bcpDetails_Primary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address1_Primary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address2_Primary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address3_Primary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address4_Primary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="city_Primary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="state_Primary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="postalCode_Primary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="country_Primary"/></td>
							
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="locType_Secondary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="locationCode_Secondary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address_Secondary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="bcpDetails_Secondary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address1_Secondary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address2_Secondary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address3_Secondary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address4_Secondary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="city_Secondary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="state_Secondary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="postalCode_Secondary"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="country_Secondary"/></td>
							
							<!-- //hardware -->
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="store"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="hardwareType"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="formAvailable"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="natureOfSale"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="typeOfSale"/></td>
							
							<!-- //dispatch address -->
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="dispatchAddressCode"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="address"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="cityName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="stateName"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="pinCode"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="phoneNo"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogRow" property="countryName"/></td>
							
						    <!-- //Charge Summary -->
							<td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <td align="left" style="font-size:9px" width="5%"></td>
						    <!-- //Component Summary -->
						        
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="component_id"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="component_name"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="package_id"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="package_name"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="component_status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="component_start_logic"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="component_start_days"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="component_start_month"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="component_start_date"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="component_end_logic"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="component_end_days"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="component_end_month"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="component_end_date"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="start_token_no"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="start_fx_status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="start_fx_no"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="end_token_no"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="end_fx_status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="end_fx_no"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="fx_start_status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="fx_end_status"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="componentinfoid"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="cominstid"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="packageInstId"/></td>
							<td align="left" style="font-size:9px" width="5%"><bean:write name="prdCatelogcomponentsRow" property="packageInstIdServ"/></td>
						</tr>
			  	</logic:iterate>
			</logic:iterate>					 	
		</logic:notEmpty>
	</logic:notEqual>		 	
</logic:iterate>
</logic:notEmpty>
								</table>
							</div>
						</td>	
					</tr>		
				</logic:present>			
			</table>
		</td>
	</tr>
</table>
</body>
</html:html>

