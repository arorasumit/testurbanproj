
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Billing And Charge Info</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<body>
	
		<fieldset class="border1">
		<legend> 
		<input name="btn1" id="btnBillingAndChargesInformation"
			onClick="show('tblBillingAndChargesInformation',this)" type="button" value="+"
			style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;
		<b>Billing And Charge Information</b> </legend>
		<div class="scroll" style="height:100%">
			<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblBillingAndChargesInformation" style="display: none;">
			<tr align="center" id="BillingInfo" style="display:none">
						<td colspan="2" width="50%">
							<jsp:include flush="true" page="ChangeBillingInfo.jsp"></jsp:include>
						</td>
					</tr>
					<tr align="center" id="ChargeDetails" style="display:none">
						<td colspan="2" width="50%">
							<jsp:include flush="true" page="ChangeChargeDetails.jsp"></jsp:include>
						</td>
					</tr>
			</table>
		</div>
	</fieldset>
</body>
</html>
