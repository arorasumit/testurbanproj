<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 LawKush		31-Jan-11	CSR00-05422     Code Changed for Quanity Label for DC Colo-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>PR Attributes</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<body>
	<table width="100%"  border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td width="100%" valign="top">
				<fieldset class="border1" style="width:100%; display:none;" id="lineItemServiceSummary" >
					<legend> <input name="btn1" id="btnServiceSummary"
								onClick="if(this.value == '+')getServiceSummary1();show('tblServiceSummary',this)" type="button" value="+"
								style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;
				<b>PR Attributes</b> </legend>
					<table width="100%"  border="0" cellspacing="0" cellpadding="0" id="tblServiceSummary" style="display: none;"> 
					<tr><td width="100%">
					<table width="100%"  border="1" cellspacing="0" cellpadding="0"> 
						<tr>
							<td width="41%" align="center">Label</td>
							<!-- [001]	Start -->
							<% 
							if(request.getParameter("ServiceID").equalsIgnoreCase("21"))
							{
							%>
							<td width="59%" align="center">Quantity</td>							
							<%
							}
							else
							{
							%>
								<td width="75%" align="center">Value</td>							
							<%} %>
							<!-- [001]	End -->
						</tr>
				  	</table>
				  	<div id = "ServiceListDiv">
				  		
				  	</div>			  		
					<fieldset class="border1" id="CrossLinking">
				  			<legend><b>SCM PR Status</b> </legend>
					  		<table width="100%"  border="1" cellspacing="1" cellpadding="2"  >
					  			<tr>
					  				<td width="41%" align="left"><b>PR Number</b></td>
					  				<td width="59%" align="center">
					  				  <input  type="text" onmouseover="getTip(value)" onmouseout="UnTip()" readonly="readonly" value="" class="inputBorder2" style="width:66%;float:left" id="prNumber" name="prNumber" />
					  				</td>	
					  			</tr>
					  			<tr>
					  			    <td width="41%" align="left"><b>SCM Progress Status</b></td>
					  				<td width="59%" align="center">
					  				  <input type="text" readonly="readonly" value="" onmouseover="getTip(value)" onmouseout="UnTip()" class="inputBorder2" style="width:66%;float:left" id="scmProgressStatus" name="scmProgressStatus" />
					  				</td>
					  			</tr>
					  			
					  		</table>
					  		
					  	
				  		</fieldset>
				  	</td></tr></table>
				</fieldset>
			</td>
		</tr>	 
	</table>
</body>
</html>