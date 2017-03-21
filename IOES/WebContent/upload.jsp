<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<html>
<head>
<title>Upload</title>
<script language="javascript" type="text/javascript">

function uploadFile(){

	
		var myForm=document.getElementById('searchForm');
		
		//validation
		
		if(document.getElementById("id_attachment").value==null || document.getElementById("id_attachment").value=="")
		{
			alert("Please Select Template file");
			return false;  
		}else
		{
				var data=document.getElementById("id_attachment").value;
				data = data.replace(/^\s|\s$/g, "");
			   if (data.match(/([^\/\\]+)\.((xls))$/i) )
			   {
				  //do nothing 
			   }
			   else
			   {
					alert("Selected Attachment is of Wrong Type");
				    return false ;				
			   }
		}
		
		myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=uploadPlanExcel";
		myForm.submit();



}
</script>
<body>
	<html:form action="/NewOrderAction" styleId="searchForm" method="post" enctype="multipart/form-data">
			<bean:define id="formBean" name="newOrderBean"></bean:define>
			<div align="center">
				<html:messages id="message" message="true">
					<li><font color="red"><bean:write name="message"/></font></li>
			</html:messages>
			</div>
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="5%" align="left" >
						Browse : <html:file property="uploadedFile" styleId="id_attachment"/> 
						</td>
						<td width="5%" align="left" ><div class="searchBg"><a href="#" onclick="uploadFile();" >upload</a></div></td>
						<td width="5%">
						</td>
						</tr>
						</table>
						</html:form>
						</body>
						
</html>