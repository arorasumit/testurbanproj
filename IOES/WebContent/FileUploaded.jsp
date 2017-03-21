<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]  LAWKUSH			5-July-11	CSR00-05422    file upload checks according to various roles   -->
<!--[15032013011]  VIJAY	        27-Feb-2013				Added a another column(upload by) on 'view document for download' page -->
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
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>Download File</title>
<script type="text/javascript" src="js/jsonrpc.js"></script>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
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
//start[001]
// lawkush Start commented
/*
<%
HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>

//Modified  by Shubhranshu 4-APR-2016

var roleId = <%= ( (objUserDto.getUserRoleId()==null) ? "0" : ( objUserDto.getUserRoleId() ) ) %>;
var userId = "<%=objUserDto.getUserId() %>";
var empId = <%= (  ( objUserDto.getEmployeeId()==null) ? "0" : ( objUserDto.getEmployeeId() ) ) %>;
var isViewOrder = "<%= request.getParameter("isViewOrder")%>"; 
*/
//End Modified
//lawkush End Commented
//End[001]




function downloadFile(fileName,hdnslno,sharepointUrl)
{
//satish
	//alert(sharepointUrl.length);
	/* return false; */
	if (sharepointUrl.length>0){
		//alert('i am here');
		//alert(sharepointUrl);
		window.open(sharepointUrl,'_blank',"width=1000,height=1000");
		//window.open('/sharepointUrl');
	}
	else
		{
		/* alert('i am here'); */		
	var accountID=document.getElementById('accountID').value;	
	var hdnOrderNo=document.getElementById('hdnOrderNo').value;		
	//var fileName=document.getElementById('hdnfileName').value;	
	var createDate=document.getElementById('hdncreateDate').value;	
	//var hdnslno=document.getElementById('hdnslno').value;	
			  		
	var myForm=document.getElementById('attachForm');
	//--added by vijay--//
	//Set the value of file name and no. in hidden fields. Before it file name and no. was sending by URL
	//but it was causing a problem, if file name contain special characters.
	
	document.getElementById('hdnfileName').value=fileName;
	document.getElementById('hdnslno').value = hdnslno;	
	
	// myForm.action="<%-- request.getContextPath()--%>/NewOrderAction.do?method=goToDownloadFile&accountID="+accountID+'&hdnOrderNo='+hdnOrderNo+'&fileName='+fileName+'&createDate='+createDate+'&sLNO='+hdnslno;
	myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=goToDownloadFile&accountID="+accountID+'&hdnOrderNo='+hdnOrderNo+'&createDate='+createDate;	
	
	//--end of code--//
	
	myForm.submit();
	
	//alert("download File!!!!!!!!!!!!!!!!!!!!!!!!!!");	
		
	isDownloadSuccessfull();
										
	 //myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=goToDownloadedFilePage&accountNo="+accountID+'&orderNo='+hdnOrderNo;		
			
	//var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=getListofDownloadedFile&accountID="+accountID+'&hdnOrderNo='+hdnOrderNo;		
	//window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
		//	myForm.submit();											
	
	//return true;
	}
}


function isDownloadSuccessfull()
{	
	var uploadStatus=document.getElementById('hdnIsDownload').value;	
	if(uploadStatus == "successDownload")
	{
		alert('File downloaded Successfully!!!');
		window.close();
	}	
	
}
function DeleteDownLoadFile()
{

		var sentMethod=document.getElementById('hdnSentMethod').value;

	     var myform=document.getElementById("attachForm");
	      var chkDownloadFile=document.getElementsByName('chkDownloadFile');
	      var accountID=document.getElementById('accountID').value;	
	      var hdnOrderNo=document.getElementById('hdnOrderNo').value;
	   
	      var str="";
	    
	  
	       var chkLength=document.forms[0].chkDownloadFile.length;
	      
	     if(chkLength==undefined)
	 	{
	 
		   if(myform.chkDownloadFile.checked==true)
		   {
		   
		   
		     if(str=="")
		     	   {
		     	     str="'"+myform.chkDownloadFile.value+"'";
		     	     
		     	    
		     	    }
		     	     else
		     	     {
		     	     
		     	              str=str + "," + "'"+myform.chkDownloadFile.value+"'";
		     	             
		     	       }
		      
		   myform.action="<%=request.getContextPath()%>/NewOrderAction.do?method=DeleteDownLoadFile&hdnslno="+str+"&accountID="+accountID+"&hdnOrderNo="+hdnOrderNo+"&empId="+empId+"&sentMethod="+sentMethod;
		   showLayer();
		   myform.submit();
	      }
	   }
		    
	 else
	 {
	      
	      
	      for(i=0;i<myform.chkDownloadFile.length;i++)
		{
	    
		     if(myform.chkDownloadFile[i].checked==true)
		     	
		     	{
		     	   if(str=="")
		     	   {
		     	     str="'"+myform.chkDownloadFile[i].value+"'";
		     	 
		     	    
		     	    }
		     	     else
		     	     {
		     	     
		     	              str=str + "," + "'"+myform.chkDownloadFile[i].value+"'";
		     	       
		     	       }
		      
		      
	    
		
	   		myform.action="<%=request.getContextPath()%>/NewOrderAction.do?method=DeleteDownLoadFile&hdnslno="+str+"&accountID="+accountID+"&hdnOrderNo="+hdnOrderNo+"&empId="+empId+"&sentMethod="+sentMethod; 
		  	showLayer();
		    myform.submit();
		
	 }
	              }		
							  
}				  
				  
}
function ischkSelected()
{
	 var myform=document.getElementById("attachForm");
	 var chkDownloadFile=document.getElementsByName('chkDownloadFile');
	 var flag=0;
	//Start[001]
	if(myform.chkDownloadFile.length==undefined)
	{
		if(myform.chkDownloadFile.checked==true)
	  	{
	  		flag=1;
	  	}	
	  	 if(flag)
	 	 {
		  	return true;
	  	  }
	 	 else
	 	   {
	  			return false;
	  		}
	}
	else
	{
	//End[001]
	  for(i=0;i<myform.chkDownloadFile.length;i++)
	  {	 
	  
	  	if(myform.chkDownloadFile[i].checked==true)
	  	{
	  		flag=1;
	  	}	  	
	  }	
	  if(flag)
	  {
	  	return true;
	  }
	  else
	  {
	  	return false;
	  }
	 }
}
//lawkush Start commented
/*
function confirmDelete()
{
	if(isViewOrder != null )
	{
		if(isViewOrder==1)
		{
			alert("This is View Order. You Cannot Delete it");
			return false;
		}
	}		
	if(ischkSelected())
	{
		if(confirm("Do you want to delete file(s)?"))
		{
			DeleteDownLoadFile();
		}
	}
	else
	{
		alert('Please select atleast one file');
	}
}
function selectAll()
{
	 var myform=document.getElementById("attachForm");
	 var chkDownloadFile=document.getElementsByName('chkDownloadFile');
	if( myform.chkDownloadFile.length==undefined)
	{
	myform.chkDownloadFile.checked=true;
	}
	else
	{
	
	
		  for(i=0;i<myform.chkDownloadFile.length;i++)
		  {	  
		  	myform.chkDownloadFile[i].checked=true;
		  	
		  }
	  }
}
function unCheckedAll()
{

	 var myform=document.getElementById("attachForm");
	 var chkDownloadFile=document.getElementsByName('chkDownloadFile');
	 if( myform.chkDownloadFile.length==undefined)
	{
	myform.chkDownloadFile.checked=false;
	}
	else
	{
	  for(i=0;i<myform.chkDownloadFile.length;i++)
	  {
	  	myform.chkDownloadFile[i].checked=false;
	  	
	  }
	}
}
*/
//lawkush End commented
//Start[001]
 function checkForEmpty()
{

//lawkush Start commented
/*
	var hdnOrderNo=document.getElementById('hdnOrderNo').value;	
	var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	var orderDetails = jsonrpc.processes.getOrderDetailForSearching(hdnOrderNo,roleId,userId);
	orderno = orderDetails.list[0].orderNumber;
	stage=orderDetails.list[0].stageName;
	ordertype=orderDetails.list[0].orderType;
	
	if(document.getElementById("hdnvariable").value=="1")
	{
		document.getElementById('btnDeleteDownLoadFile').style.display="none";
		 document.getElementById('selectAll').style.display="none";
		  document.getElementById('unCheckedAll').style.display="none";
	}
	else
	{
		if(((stage=='Partial Publish' || stage=='SED' )&& roleId==4)|| ((stage=='Billing Trigger'||stage=='Partial Publish') && roleId==3))
		{
		document.getElementById('btnDeleteDownLoadFile').style.display="none";
		document.getElementById('selectAll').style.display="none";
		  document.getElementById('unCheckedAll').style.display="none";
		}
	}
	//End[001]
	
	*/
//lawkush End commented	
	if(document.getElementById('hdnSentMethod').value==1)
		{	
			document.getElementById('fileAttachedType').selectedIndex=1;
			document.getElementById('fileAttachedType').disabled="true";
		}
	
	
}

//lawkush start


function searchDocumentType()
{
						var sentMethod=0;
						var fileTypeIdN=document.getElementById('fileAttachedType').value;
						var accountID=document.getElementById('hdnAccountID').value;
						var poNumber=document.getElementById('hdnOrderNo').value;
					    	var width  = 1000;
				 			var height = 600;
				 			var left   = (screen.width  - width)/2;
				 			var top    = (screen.height - height)/2;
				 			var params = 'width='+width+', height='+height;
				 				params += ', top='+top+', left='+left;
				 				params += ', directories=no';
				 				params += ', location=no';
				 				params += ', menubar=no';
				 				params += ', resizable=no';
				 				params += ', scrollbars=yes';
				 				params += ', status=no';
				 				params += ', toolbar=no';
				 				params += ', status=yes';
				 				
					    	var myForm=document.getElementById('attachForm');
							myForm.action = "<%=request.getContextPath()%>/NewOrderAction.do?method=goToDownloadedFilePage&orderNo="+poNumber+"&accountNo="+accountID+"&fileTypeId="+fileTypeIdN+"&sentMethod="+sentMethod;	
							myForm.submit();
						

}

//lawkush end


</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload="checkForEmpty()">
<div id="shadow" class="opaqueLayer"><font color="yellow" size="8"> Please wait......</font>
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
	<div class="head"><span>Download File</span></div>
	<fieldset class="border1">
	<legend><b>Select File for Download</b></legend>
	<table id="doctyes"  width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr align="center">
			<logic:notEmpty name="fileAttachmentTypeList"  scope="request">
				<td align="right">
					Select a Document Type
				</td>
			   	<td align="left">
					<html:select   property="fileType" styleId="fileAttachedType" style="width:200px;float:left;" styleClass="inputBorder1"  onkeydown='if (event.keyCode == 13)  return searchDocumentType();'   >
						<html:option value="0"  >Select a Document Type</html:option>
						<logic:iterate id="row1" name="fileAttachmentTypeList" indexId="index1">
							<html:option value="${row1.fileTypeId}" >
								<c:out value="${row1.fileType}" />
							</html:option>
						</logic:iterate>
					</html:select>
				</td>
		  	</logic:notEmpty>
		</tr>
	</table>
	<table id="tableFiles"  width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
		<tr>						
			<td>
				<!-- Rakshika  : Code Start -->
				<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tab2" id="tblAMApprovalOrder" style="display:block;" >
					<tr align="center" class="grayBg">
   						<td align="center" width="5%">S.No.</td>
						<td align="center">Order No</td>
						<td align="center">Account No:</td>
         				<td align="center">Document Name</td>
						<td align="center">File Name</td>	
						<td align="center">Document Type</td>	
						<td align="center">Uploaded Date</td>
					<!-- [15032013011] -start -->
						<td align="center">Uploaded By</td>
						<td align="center">Email Id</td>
						<td nowrap="nowrap" align="center">Link to Download</td>
					<!-- [15032013011] -endt -->	
						<!--//lawkush Start Comment
						<td align="center">Select to delete</td>
						//lawkush End Comment-->
					</tr>
					<%int nSNo=0;%>
					<%int strcount=0;%>
					<html:hidden property="hdnslno" value=""/>
						<logic:present name="listFileAttached" scope="request">
							<logic:empty name="listFileAttached">
								<tr align="center">
									<td colspan="10">
										<!-- Start[001] -->
										<!-- lawkush Start commented 
										<html:hidden property="hdnvariable" value="1" styleId="hdnvariable"/>
											lawkush End Commented -->
										<!-- End[001] -->
										<b>No File uploaded for this order</b>
									</td>
								</tr>
							</logic:empty>
							<logic:notEmpty name="listFileAttached">
								<logic:iterate name="listFileAttached" id="List" indexId="recordId">
								<%String classType=null;
								if(recordId.intValue() % 2 == 0)
								{
									classType="normal";
								}
								else
								{
									classType="lightBg";				
								}	
								nSNo++;	
								strcount++;%>				
									<tr class="<%=classType%>">
										<td align="center"><%=nSNo%></td>
											<input type="hidden" id="SLNO" value="<%=nSNo%>"/>
										<td nowrap="nowrap" align="center"><bean:write  name="List" property="hdnOrderNo" /></td>
										<td nowrap="nowrap" align="center">
											<bean:write name="List" property="hdnAccountNo" />
										</td>
										<td nowrap="nowrap" align="center">
											<bean:write name="List" property="description" />
										</td>
										<td nowrap="nowrap" align="center">
											<div><a href="javascript:downloadFile('<bean:write name="List" property="fileName" />','<bean:write name="List" property="slno" />');" onClick="return downloadFile()" alt="Download File" title="Download Here"></a></div> 
											<bean:write name="List" property="fileName" />
											<input type="hidden" name="hdnFileName" value="<bean:write name="List" property="fileName" />">
										</td>
										<td nowrap="nowrap" align="center">
											<bean:write name="List" property="docName" />
											<input type="hidden" name="fileTypeId" value="<bean:write name="List" property="fileTypeId" />">
										</td>
										<td nowrap="nowrap" align="center">
											<bean:write name="List" property="createDate" />
											<input type="hidden" name="hdnCreateDate" value="<bean:write name="List" property="createDate" />">
											<input type="hidden" id="hdnslno" name="hdnslno" value="<bean:write name="List" property="slno" />">
										</td>
										<!-- [15032013011] -start -->
										<td nowrap="nowrap" align="center">
											<bean:write name="List" property="uploadedBy" />
										</td>
										<!-- [15032013011] -start -->	
										<td nowrap="nowrap" align="center">
											<bean:write name="List" property="emailId" />
										</td>
										<td width="10%" align="left"> 
											<div class="searchBg" align="left"><a href="#" onClick="return downloadFile('<bean:write name="List" property="fileName" />','<bean:write name="List" property="slno" />','<bean:write name="List" property="sharepointUrl"/>')" alt="Download File" title="Download Here">Download</a></div> 
										</td>	
										<!-- Lawkush Start Commented 
										<td width="10%" align="center">
							       			<input type="checkbox" id="chkDownloadFile<%=nSNo%>" name="chkDownloadFile" value="<bean:write name="List" property="slno"/>">
										</td>
										  Start[001] 
										
										<html:hidden property="hdnvariable" value='0'  styleId="hdnvariable"/>
										 End[001]
										Lawkush End commented   	-->
									</tr>
								</logic:iterate>	
							</logic:notEmpty>
						</logic:present>
					<!-- Rakshika  : Code end -->		
			<tr>
				<td width="407"><html:hidden property="accountID" name="newOrderBean" styleId="hdnAccountID"/></td>
				<td><html:hidden property="hdnOrderNo" name="newOrderBean" styleId="hdnOrderNo"/></td>
				<td><html:hidden property="isDownload" name="newOrderBean" styleId="hdnIsDownload"/></td>
				<html:hidden property="sentMethod" name="newOrderBean" styleId="hdnSentMethod" />
			</tr>
			<tr>				
				<td align="right" width="407">
				<!-- lawkush Start commented
					<div class="searchBg" id = "selectAll" align="right" style="display:block;"><a href="#" onclick="selectAll();">Select All</a></div><div class="searchBg"  align="right" id = "unCheckedAll" style="display:block;"><a href="#" onclick="unCheckedAll();">Unselect All</a></div>
				 	<input type="button" name="btnDeleteDownLoadFile" value="Delete File" onClick="confirmDelete();" styleId="btnDeleteDownLoadFile"/>
				 	 lawkush End Commented -->
				</td>				 
			</tr>
		</table>	
	</fieldset>
</html:form>
</body>
</html>
