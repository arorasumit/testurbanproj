<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- [001]  Priya Gupta 19-06-2015    Added ldclause column -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top">
			<fieldset class="border1">
				<legend > <b>PO Details</b> </legend>
				<div class="scrollWithAutoScroll"  style="height:180px;width:945px;">
					<table  border="1" cellspacing="0" cellpadding="0" align="center" width="100%" id='tablePO'>
						<%int count=1; %>
						<tr>
						    <td  nowrap>PO Detail Number</td>
						    <td nowrap>PO Date</td>
							<td  nowrap>A\C No</td>
							<td nowrap>PO Recieve Date</td>
							<td  nowrap>Contract Start Date</td>
							<td nowrap>Contract End Date</td>
							<td  nowrap>Period In Months</td>
							<td nowrap>Total PO Amount</td>
							<td  nowrap>Entity</td>
							<!-- [001] -->
						    <td nowrap>Ld Clause</td> 
						</tr>
						<logic:present name="listPoDetails" scope="request">
							<logic:notEmpty name="listPoDetails" >
								<logic:iterate id="poDetailID" name="listPoDetails" indexId="recordId" >
									<%count=((recordId.intValue())+1);%>
									<tr>
										 <td>
											<input type="hidden" name="contactId<%=count %>" class="inputBorder4" value="<bean:write name="poDetailID" property="contactId"/>"  />
											<input type="text"  name="poDetailNo<%=count %>" class="inputBorder4" value="<bean:write name="poDetailID" property="poDetailNo"/>" readonly="true"/>
										</td>
						  				 <td><input type="text"  name="poDate<%=count %>" class="inputBorder4" value="<bean:write name="poDetailID" property="poDate"/>"/></td>
										<td><input type="text"  name="accountID<%=count %>" class="inputBorder4" value="<bean:write name="poDetailID" property="accountID"/>" readonly="true"/></td>
										<td><input type="text"  name="poReceiveDate<%=count %>" class="inputBorder4" value="<bean:write name="poDetailID" property="poReceiveDate"/>"/></td>
										<td><input type="text"  name="contractStartDate<%=count %>" class="inputBorder4" value="<bean:write name="poDetailID" property="contractStartDate"/>"/></td>
										<td><input type="text"  name="contractEndDate<%=count %>" class="inputDisabled" value="<bean:write name="poDetailID" property="contractEndDate"/>"/></td>
										<td><input type="text"  name="periodsInMonths<%=count %>" class="inputBorder4" value="<bean:write name="poDetailID" property="periodsInMonths"/>"/></td>
										<td><input type="text"  name="totalPoAmt<%=count %>" class="inputBorder4" value="<bean:write name="poDetailID" property="totalPoAmt"/>"/></td>
										<td><input type="text"  name="entity<%=count %>" class="inputBorder4" value="<bean:write name="poDetailID" property="entity"/>"/></td>
									 	<!-- [001] -->
									<td><input type="text"  name="ldClause<%=count %>" class="inputBorder4" value="<bean:write name="poDetailID" property="ldClause"/>" readonly="true"/></td> 
									</tr>
						      	</logic:iterate>
							</logic:notEmpty>
							<logic:empty name="listPoDetails" >
								<tr>
							<!--[001] change the value of colspan from 9 to 10  -->	
									<td colspan="10">
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

