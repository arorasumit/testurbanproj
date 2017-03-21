<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@page import="java.util.ArrayList;"%>

<table width="100%"  border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td width="100%" valign="top">
			<fieldset class="border1" >
				<legend> <b>Service Type</b> </legend>
				<div class="scrollWithAutoScroll"  style="height:330px;width:100%;">
					<table width="80%"  border="1" cellspacing="0" cellpadding="0" id="ServiceList">
						<tr align="Center">
							<td width="5%">Select</td>
							<td width="12%">Service No</td>
							<td width="43%">Service Type</td>
							<td width="10%">Service Sub Type</td>
							<td width="19%" id="ServiceAttHeader" style="display:block">Attributes</td>
							<td width="9%">Attribute Label Value</td>
						</tr>
						<input type="hidden" name="hdnServiceCount" value="1">
				   </table>						 
				</div>
			</fieldset>
		</td>
	</tr>
</table>

