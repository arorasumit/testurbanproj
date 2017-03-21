 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ROHIT VERMA	23-Feb-11	00-05422		System Testing Defect for AM cant Approve an Order -->
<!--[002]    SUMIT ARORA    07-MAR-11   00-05422        Length Of Remarks to increased and restricted to 256 -->
<!--[250511AKS] ASHUTOSH  	25-MAY-11   00-05422        Defects: Validate PO During Aciton close and Publish -->
<!--[003]	 ANIL KUMAR		23-May-11	00-05422     	Session Expired code with AjaxJSON  -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>Send To COPC</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="./gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script language="javascript">
var path='<%=request.getContextPath()%>';
function SaveAction(action)
{
	var callerWindowObj = dialogArguments;
	var orderNo = callerWindowObj.document.getElementById('poNumber').value;
	if(document.getElementById('actionRemarks').value=="")
	{
		alert('Please Enter Request for Information Remarks');
		return false;
	}	
	if(document.getElementById('actionRemarks').value.length>250)
	{
		alert('Remarks should be less than 250 characters');
		return false;
	}
	
	var jsData =			
    {
		orderNo:orderNo,
		actionRemraks:document.getElementById('actionRemarks').value,
		userid:callerWindowObj.gb_roleID
	};
    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    var sessionId='<%=request.getSession().getId() %>';	
    var retVal = jsonrpc.processes.SaveActionFromSEDToCOPC(jsData,sessionId);
    if(retVal==null)
    {
    		var callerWindowObj = dialogArguments;
			var myForm=callerWindowObj.document.getElementById('searchForm');				
			myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=invalidsessoion";
			myForm.submit();
			alert("Session has been expired!!");		
			window.close();	
			return false;
    }
    else
    {
	    alert(retVal.msgOut);  
	    window.close();
	    if(retVal.msgOut=="Action taken Successfully"){
	    	callerWindowObj.submitParent();	    
	   }
	    return false;
    }   
}
</script>
</head>
<body>					
<table width="100%" border="1" cellspacing="0" cellpadding="0">
	<tr>
		<td valign="top">
			<fieldset class="border1"><legend> <b> Send To COPC</b> </legend>
				<div class="scrollWithAutoScroll" style="height:250px;width:450px;">
					<table border="1" cellpadding="0" cellspacing="0" align="center" width="100%" id="sendToCOPC">
				   		<tr>
				   			<td align="center">
								<textarea class="inputBorder1" rows="6" id="actionRemarks"
								name="actionRemarks" maxlength="250" width="4000%" cols="35" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Send To Copc')};" ></textarea> 
					   		</td>
					   	</tr>
					   	<tr>
							<td align="left">
								<div class="searchBg" onclick="return SaveAction(2)" align="left"><a href="#">Submit</a></div>
							</td>
						</tr>
					</table>
				</div>
			</fieldset>
		</td>
	</tr>
</table>
</body>
</html>
		