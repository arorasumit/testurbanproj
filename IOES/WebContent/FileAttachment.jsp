<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Manisha 	03-Feb-13	CSR00-05422     few special chars added  ?@.`~-_" defect no 6-->
<!-- [002] Pankaj Thakur 26-nov-14                          add validation for [txtFileName]  field -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>


<html>
<head>
<title>Attach File</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<link href="<%=request.getContextPath()%>/gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
</head>
<style type="text/css">
    .opaqueLayer
    {
        display:none;
        position:absolute;
        top:0px;
        left:0px;
        opacity:0.5;
        filter:alpha(opacity=50);
        background-color: #000000;
        z-Index:1000;
        text-align:center;
        vertical-align:middle;
        padding:100px;
    }
    
</style>
<script language="javascript" type="text/javascript">
var path='<%=request.getContextPath()%>';
<%
HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>
var roleId = <%=objUserDto.getUserRoleId() %>;
var userId = "<%=objUserDto.getUserId() %>";
var empId = <%= Long.parseLong(objUserDto.getEmployeeId()) %>;
var isViewOrder = "<%= request.getParameter("isViewOrder")%>";

function saveFileAttachment()
{

	if(document.getElementById('fileAttachedType').value==0)
	{
		alert("Please select File Type");
		return false;
	}
	
	var data=document.getElementById('txtFileName').value;
	
	var fileNameObj=document.getElementById('txtFileName');
	
//[002] start
	
	 if(trim(fileNameObj.value).length>0){
		
	
		if(validateFileName(fileNameObj,path,'File Name')==false){
			
		//	searchFlag=0;
				
		return false;
		}
		
		
	}
	//[002] end
	
	if(document.getElementById('txtFileRename').value=="")
	{
		alert("Please Input File Description");
		return false;
	}		
	else
	{
		var fileDesc=document.getElementById('txtFileRename').value;
		if(data != "")
		{
			// 001 start
			var iChars = "!$%^&*()+=[]\\\';,/{}|\":<>?@.`~-_";
			//001 end
			var arr=new Array();
			arr=iChars.split(",");

			for (var i = 0; i < fileDesc.length; i++)
			{
		  		if (iChars.indexOf(fileDesc.charAt(i)) != -1) 
		  		{
			  		alert ("Only AlphaNumeric Character are allowed!!!");
					document.getElementById('txtFileRename').focus();	
					return false;
		  		}
		  	}
		}
	}
	if(isValidFileExtension(data))
	{	  		
			showLayer();	
			var  fileTypeId=document.getElementById('fileAttachedType').value;
			var myForm=document.getElementById('attachForm');
			myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=goToFileUpload&fileTypeId="+fileTypeId+"&empId="+empId;   
			myForm.submit();											
	}
	return true;
}
function  isValidFileExtension(data)      
{
   data = data.replace(/^\s|\s$/g, "");
   if (data.match(/([^\/\\]+)\.((jpg)|(gif)|(jpeg)|(zip)|(ppt)|(pptx)|(doc)|(xls)|(xlsx)|(bmp)|(tif)|(tiff)|(png)|(docx)|(rar)|(pdf)|(txt))$/i) )
   {
   		if(document.getElementById('txtFileRename').value ==""||document.getElementById('txtFileRename').value==null)
   		{
   			alert("Please enter descriptions");
   			document.getElementById('txtFileRename').focus;
   			return false;
   		}
   		else   
	  		return true;  
   }
   else
   {
		alert("Selected Attachment is of Wrong Type");
		document.getElementById('txtFileName').focus;
	    return false ;				
   }   
}

function isUploadSuccessfully()
{	
	var uploadStatus=document.getElementById('hdnIsUpload').value;	
	if(uploadStatus == "successUpload")
	{
		alert('File Uploaded Successfully!!!');
		var counter =document.getElementById('fileTypAttach').value;//counter has the selected radio button serial number passed from selectTaskstatus jsp
		if(counter!=null && counter!='' &&  counter!=0){
			updateChkBox(counter);
		}
		
		window.close();
	}
	else
	{
		if(document.getElementById('hdnSentMethod').value==1)
		{	
			document.getElementById('fileAttachedType').selectedIndex=1;
			document.getElementById('fileAttachedType').disabled="true";
		}
	}	
}
function updateChkBox(counter){
		
		var chkLength1;
		var chkLength2;
		var chkLength3;
		var chkLength4;
					if(roleId==2)
			       { 
			         chkLength2=window.opener.document.forms[0].chkPMDetail.length;
			         if(chkLength2==undefined)
				     {
			              if(window.opener.document.forms[0].chkPMDetail.disabled==false)
			           	  {
			                  window.opener.document.forms[0].chkPMDetail.checked=true;
			                  window.opener.insertSelectedFileCHKList(0);
			                  
			              }
			         }
			 		 else
			 		 {    
			 			if(window.opener.document.getElementById('chkPMDetail'+counter).disabled==false)
			 			{
			        		window.opener.document.getElementById('chkPMDetail'+counter).checked=true ;
			        		window.opener.insertSelectedFileCHKList(0);
			         	}
			         }
			       }
			       else if(roleId==3)
			       { 
			       
			         chkLength3=window.opener.document.forms[0].chkCOPCDetail.length;
			         if(chkLength3==undefined)
				     {
			              if(window.opener.document.forms[0].chkCOPCDetail.disabled==false)
			           	  {
			                  window.opener.document.forms[0].chkCOPCDetail.checked=true;
			                  window.opener.insertSelectedFileCHKList(0);
			                  
			              }
			         }
			 		 else
			 		 {    
			 			if(window.opener.document.getElementById('chkCOPCDetail'+counter).disabled==false)
			 			{
			        		window.opener.document.getElementById('chkCOPCDetail'+counter).checked=true ;
			        		window.opener.insertSelectedFileCHKList(0);
			         	}
			         }
			       }
			       else if(roleId==4)
			       {
			         chkLength4=window.opener.document.forms[0].chkSEDDetail.length;
			         if(chkLength4==undefined)
				     {
			              if(window.opener.document.forms[0].chkSEDDetail.disabled==false)
			           	  {
			                  window.opener.document.forms[0].chkSEDDetail.checked=true;
			                  window.opener.insertSelectedFileCHKList(0);
			                  
			              }
			         }
			 		 else
			 		 {    
			 			if(window.opener.document.getElementById('chkSEDDetail'+counter).disabled==false)
			 			{
			        		window.opener.document.getElementById('chkSEDDetail'+counter).checked=true ;
			        		window.opener.insertSelectedFileCHKList(0);
			         	}
			         }
			       }
			       else
			       {
			       		
			         chkLength1=window.opener.document.forms[0].chkAMDetail.length;
			         if(chkLength1==undefined)
				     {
			              if(window.opener.document.forms[0].chkAMDetail.disabled==false)
			           	  {
			           	  
			           	  		
			                  window.opener.document.forms[0].chkAMDetail.checked=true;
			                  window.opener.insertSelectedFileCHKList(0);
			              }
			         }
			 		 else
			 		 {
			 			if(window.opener.document.getElementById('chkAMDetail'+counter).disabled==false)
			 			{
			        		window.opener.document.getElementById('chkAMDetail'+counter).checked=true ;
			        		window.opener.insertSelectedFileCHKList(0);
			         	}
			         }
			       }
								
		
}
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload="isUploadSuccessfully()">
<div id="shadow" class="opaqueLayer"><font color="yellow" size="8"> Please wait...File is uploading...</font>
</div>
<div class="error" align="center">
			<logic:present name="validation_errors">
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>
			</logic:present>
			<html:messages id="message" message="true">
								<li><font color="red"><bean:write name="message"/></font></li>
			</html:messages>
</div>
<html:form action="/NewOrderAction" method="post" styleId="attachForm" enctype="multipart/form-data">
	<div class="head"><span>Upload File</span></div>
	<fieldset class="border1">
			<legend><b>Select File</b></legend>
	<table width="100%" border="1" align="center" cellpadding="0"
		cellspacing="0" class="tab2" height="105">
		
	<tr class="grayBg">
	<logic:notEmpty name="fileAttachmentTypeList"  scope="request">
			<td align="left" width="200">Select File Type</td>
			    <td align="left" width="273">
						<html:select   property="fileType" styleId="fileAttachedType" style="width:400px;float:left;" styleClass="inputBorder1" >
						<html:option value="0"  >Select a File Type</html:option>
								<logic:iterate id="row1" name="fileAttachmentTypeList" indexId="index1">
										<html:option value="${row1.fileTypeId}" >
											<c:out value="${row1.fileType}" />
										</html:option>
								</logic:iterate>
			
			</html:select>			
		  </td>
		  </logic:notEmpty>		  
	</tr>
		
		<tr class="grayBg">
			<td align="left" width="200">Select File for Upload</td>
			<td align="left" width="273"><html:file
				property="fileAttachment" name="newOrderBean" styleId="txtFileName"
				styleClass="inputBorder1" alt="Loading..." title="File Source" /></td>
			<td align="center" rowspan="2" height="86" width="118">
			<div class="searchBg"><a href="#"
				onclick="return saveFileAttachment()" alt="Upload File"
				title="Upload Here"> Upload</a></div>
			</td>
		</tr>
		<tr class="grayBg">
			<td align="left" width="200">Enter Description:</td>
			<td align="left" width="273"><html:text property="fileRename"
				name="newOrderBean" styleId="txtFileRename"
				styleClass="inputBorder1" style="width:130px;"></html:text></td>
		</tr>

		<html:hidden property="accountID" name="newOrderBean"
			styleId="hdnAccountID" />
		<html:hidden property="hdnOrderNo" name="newOrderBean"
			styleId="hdnOrderNo" />
		<html:hidden property="isUpload" name="newOrderBean"
			styleId="hdnIsUpload" />
			
			<html:hidden property="sentMethod" name="newOrderBean"
			styleId="hdnSentMethod" />
			<html:hidden property="counterAttach" name="newOrderBean"
			styleId="hdnCounterAttach" />
			<html:hidden property="fileTypAttach" name="newOrderBean"
			styleId="hdnfileTypAttach" />
			
	</table>
	</fieldset>
</html:form>
</body>
</html>
