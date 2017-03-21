 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--Tag Name Resource Name  Date		CSR No			Description -->
<!-- [001]   Gunjan Singla  5-Mar-14                   Added A drop down  -->
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
<title>:: Cancel Order: Integrated Order Entry System</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="js/NewOrder.js"></script>
<script language="javascript">
var path='<%=request.getContextPath()%>';
//[001]
var answer='false';

function populateReasonForCancel()
{	
	//alert("populateReasonForCancel");
	var action_ord='<%= AppConstants.ACTION_ORDER %>';
	
	var tr, td, i, j, oneRecord;
    var test;
    //var interFaceStdReason=2;
   var combo = document.getElementById("cancelReasonDD");	
   var iCountRows = combo.length;
   for (i = 1; i <  iCountRows; i++)
   {
       combo.remove(i);
   }
  
   //var jsData =			
	 //   {
		//  ordServFlag:action_ord
		//};
   
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	var stdReason = jsonrpc.processes.populateReasonForCancel(action_ord);
	
	    for(j=0;j<stdReason.list.length;j++)
	    {
	    	var reasonname= stdReason.list[j].reasonName;
	     
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
		var cr=document.getElementById("cancelReason").value;
		var crd=document.getElementById("cancelReasonDD").value;
		var callerWindowObj = dialogArguments;
		if(document.getElementById('cancelReasonDD').value=='0')
		{
		alert("Please Select Cancellation Reason");
		return false;
		}
		if(cr.length>500){
			alert("please input less than 500 characters in cancellation remarks");
			return false;
		}
		if(trim(cr).length>0)
		{
			if(!ValidateRemarks(document.getElementById("cancelReason"),path,'Cancellation Remarks'))
			{
				return;
			}
		}
		
		callerWindowObj.document.getElementById('cancelReason').value=cr;
		callerWindowObj.document.getElementById('cancelReasonDD').value=crd;
		answer=confirm("Are you sure you want to cancel this order ?");
		
		if(!answer)
		window.close();
		else{
		callerWindowObj.document.getElementById('answer').value=answer;
		window.close();
		}
	}
function closeSelected(){

	window.close();
}

</script>
</head>
<body onload="populateReasonForCancel();">

<html:form action="/NewOrderAction" enctype="text/plain">
<div>
		<fieldset class="border1">
			<table width="100%"  border="1" align="center" id = "taskTable" cellpadding="0" cellspacing="0" class="tab2" >
				
				<!-- [001] -->
				<tr>
					<td align="center"  colspan="2" >
						Cancellation Reason
					</td>
					<td colspan="2" align="center" >
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
						
						 <html:textarea  styleId='cancelReason' onkeypress="return (this.value.length < 500);" style="width: 300px;" property="cancelOrderReason"> </html:textarea>
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
		