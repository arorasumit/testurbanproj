<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<html>
<head>
<title>viewChargeDetails</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<script>


	
</script>
</head>
<body>
	<fieldset class="border1">
	<legend> 
		<input name="btn1" id="btnChargesDetails"
				onClick="show('tblChargesDetails',this)" type="button" value="+"
				style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;
	<b>Charges Details</b> </legend>
	<div class="scroll" style="height:100%">
		<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblChargesDetails" >
			<tr>
				<td width="100%" valign="top" align="left">
					<div   style="height:180px;width:800px;">
					
						<table  border="1" cellspacing="0" cellpadding="0" align="center" width="100%" id='tableCharges'>
							<input type="hidden" name="hdnChargeCount" value="0"/>
							
							<tr>
								<td  nowrap>Select</td>
							    <td  nowrap>Type</td>
							    <td  nowrap>Name</td>
							    <td  nowrap>Total Amount</td>
							    <td  nowrap>New Charge Amount</td>
								<td  nowrap>Effective Date</td>
								<td  nowrap>Std Reason</td>
							    <td nowrap>Charge Period(Months)</td>
								<td nowrap>Frequency</td>
								<td  nowrap>Frequency Amount</td>
								<td nowrap>Start Date Logic</td>
								<td  nowrap>End Date Logic</td>
								
							</tr>
							
						</table>
						
					</div>
					
				</td>
			</tr>
		</table>
	</div>
</fieldset>
</body>
</html>
