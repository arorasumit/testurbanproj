 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ROHIT VERMA	23-Feb-11	00-05422		System Testing Defect for AM cant Approve an Order -->
<!--[002]    SUMIT ARORA    07-MAR-11   00-05422        Length Of Remarks to increased and restricted to 256 -->
<!--[250511AKS] ASHUTOSH  	25-MAY-11   00-05422        Defects: Validate PO During Aciton close and Publish -->
<!--[003]	 ANIL KUMAR		23-May-11	00-05422     	Session Expired code with AjaxJSON  -->
<!-- [004]   Gunjan Singla  5-Mar-14                    Added A drop down  -->
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
<%String isCancelAndCopy=request.getParameter("isCancelAndCopy");
											if (("2").equals(isCancelAndCopy))
											{%>

<title>:: Cancel & Copy Service: Integrated Order Entry System</title>
<%
   }
   else{	
%>

<title>:: Cancel Service: Integrated Order Entry System</title>
<%
   }
%>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="js/NewOrder.js"></script>
<script language="javascript">
var path='<%=request.getContextPath()%>';
//[004]
var answer='false';
var buttonClicked;
function populateReasonForCancel()
{	
	//alert("populateReasonForCancel");
	var action_srv='<%= AppConstants.ACTION_SERVICE %>';
	var tr, td, i, j, oneRecord;
    var test;
    //var interFaceStdReason=2;
   var combo = document.getElementById("cancelReasonDD");	
   var iCountRows = combo.length;
   for (i = 1; i <  iCountRows; i++)
   {
       combo.remove(i);
   }
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	var stdReason = jsonrpc.processes.populateReasonForCancel(action_srv);
	
	    for(j=0;j<stdReason.list.length;j++)
	    {
	    	var reasonname= stdReason.list[j].reasonName;
	     	//alert(reasonname);
	    	var option = document.createElement("option");	    	
	    	
	   		option.text = stdReason.list[j].reasonName;
	   		option.title = stdReason.list[j].reasonName;
			option.value = stdReason.list[j].reasonID;
			try 
			{
			combo.add(option, null); //Standard
			}
			catch(error) 
			{
				combo.add(option); // IE only
			}
	    }    
}
function takeAction()
{
	//[004]
	
	var crd=document.getElementById("cancelReasonDD").value;
	buttonClicked=1;
	//alert("crd:"+crd);
	if(document.getElementById('cancelReasonDD').value=='0')
		{
		alert("Please Select Cancellation Reason");
		return false;
		}
	if(trim(document.getElementById("cancelReason").value).length>500){
			alert("please input less than 500 characters in cancellation remarks");
			return false;
		}
	if(trim(document.getElementById("cancelReason").value).length>0)
		{
			if(!ValidateRemarks(document.getElementById("cancelReason"),path,'Cancellation Remarks'))
			{
			return;
			}
		}
	//[250511AKS]Start
	var callerWindowObj = dialogArguments;
	var isCancelAndCopy = <%=request.getParameter("isCancelAndCopy")%>;
	//[004]
	callerWindowObj.document.getElementById('cancelReasonDD').value=crd;
	callerWindowObj.document.getElementById('buttonClicked').value=buttonClicked;
	//Done By Saurabh to handle code for CopyAndCancel with multiple Services using Checkbox
	if(isCancelAndCopy == 2)
	{
		var serviceIdString = "<%=request.getParameter("serviceIdString")%>";
		//callerWindowObj.cancelAndCopyService(document.forms[0].cancelServiceReason.value,serviceIdString);
		callerWindowObj.reasonForCancelService=document.forms[0].cancelServiceReason.value;
		window.close();
	}
	else
	{
		callerWindowObj.reasonForCancelService=document.forms[0].cancelServiceReason.value;	
			//callerWindowObj.CancellationOfService(document.forms[0].cancelServiceReason.value,isCancelAndCopy);
			window.close();
			}
	
}
function closeSelected(){
	buttonClicked=0;
	var callerWindowObj = dialogArguments;
	//alert("inside close");
	callerWindowObj.document.getElementById('buttonClicked').value=buttonClicked;
	window.close();
	
}

</script>
</head>
<body onload="populateReasonForCancel();">
<html:form action="/NewOrderAction" enctype="text/plain">
<div>
		<fieldset class="border1">
			<table width="100%"  border="1" align="center" id = "taskTable" cellpadding="0" cellspacing="0" class="tab2" >
				
				<!-- [004] -->
				<tr>
					<td align="center"  colspan="2" >
						 Cancellation Reason
					</td>
					<td colspan="2" align="center" width="50%">
						<select name="cancelReasonDD"  id="cancelReasonDD" class="dropdownMargin" property="cancelOrderReasonDD" style="width: 375px;">
						<option value="0">Select Reason </option>
						</select>
					</td>
					</tr>
					<tr class="lightBg">
					<td colspan="2" align="center">
						Cancellation Remarks
					</td>
					<td colspan="2" align="center">
						
						<html:textarea  styleId='cancelReason' onkeypress="return (this.value.length < 500);" style="width: 300px;" property="cancelServiceReason"> </html:textarea>
					</td>
					
				</tr>
				<tr class="lightBg" >
				<td colspan="4" align="center">
				<input type="button" value="Submit" onClick=" takeAction()">
				<input type="button" value="Close" onClick=" closeSelected()">
				
				</tr>
			</table>
		</fieldset>
	</div>
</html:form>
</body>
</html>
		