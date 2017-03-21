
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>SCM Line</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<meta name="GENERATOR" content="Rational Software Architect">
</head>

<body>
	
		<fieldset class="border1" style="width:100%;" id="ScmLineItem">
		<legend> <input name="btn1" id="btnChargeSummary"
								onClick="drawViewChargeTable1();show('ChargeList',this);displayScmLine()" type="button" value="+"
								style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;
			<b>SCM Line Info</b> </legend>
			
					<table width="100%" border="1" cellspacing="0" cellpadding="0" id="ChargeList" style="display: none;">
			     
       	
				<tr align="center">
						<td colspan="2" width="50%" style="display:none" id="ViewScmLineItem">
							<jsp:include flush="true" page="ViewScmLineDetail.jsp"></jsp:include>
						</td>
					</tr>
			        
			        
			        
			        </table>
			   
			       
	</fieldset>
</body>
</html>
