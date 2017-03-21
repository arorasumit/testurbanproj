<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="com.ibm.ioes.forms.LSICancellationDto"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>LSI Cancellation Confirmation</title>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="./gifs/style.css" type="text/css" rel="stylesheet">
<link href="./css/ReportHeaderFreeze.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ReportHeaderFreeze.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet"> 
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>
<script language="javascript" type="text/javascript">

 
function confirmCancelLsi(){
	if (confirm('Are you sure you want to cancel these LSIs ?')) {
    		window.dialogArguments.document.getElementById('flagForCancelLsi').value = 1 ;
    		window.close();
	} else {
	    // Do nothing!
	} 
 	
}
 
function makeTable()    
{
	 //$('#tblRoleList tr:not(:first)').remove();
	 var myListinChildWindow=window.dialogArguments.myList;
	 var cancelRemarks;
	 var i=1;
    	for(var key in myListinChildWindow) {
		    if(myListinChildWindow.hasOwnProperty(key)) {
		    	//alert('myList[key].searchServiceNomyList[key].searchServiceNo:'+myListinChildWindow[key].cancellationRemarks.length+':')
		     	/* if(myListinChildWindow[key].cancellationRemarks.length=='0')
		    		cancelRemarks='&nbsp;';
		    	else
		    		cancelRemarks=myListinChildWindow[key].cancellationRemarks; 
		    	 */
		    	$('#tblRoleList').append('<tr><td class="inner col1" align="center"> '+ i + ' </td><td class="inner col2" align="center"> '+ myListinChildWindow[key].searchLSI + ' </td><td class="inner col2" align="center"> '+ myListinChildWindow[key].searchServiceNo + ' </td><td class="inner col3" align="center"> '+ myListinChildWindow[key].cancellationRemarks + '&nbsp;'+ ' </td></tr>')
		    	i=i+1;
		    }
		}
}
</script>

<body onload="makeTable()" style="vertical-align: top;">
		<DIV class="head"> LSI's to be Cancelled </DIV>
		
		<fieldset class="border1" >
			<legend> <b>LSI to be Cancelled</b> </legend>
			
			<table width="96%"  border="1" cellspacing="1" cellpadding="2">
				
				<tr align="center">
					<td colspan="4" >
		    			<button type="button"  onclick="confirmCancelLsi()"   style="margin-left:10%;display:block">Cancel LSI</button> 
					</td>
				</tr>
				
				<tr>
					<td class='inner col1 head1' align="center"><b>S.No.</b></td>
					<td class='inner col2 head1' align="center"><b>LSI</b></td>
					<td class='inner col2 head1' align="center"><b>Service No.</b></td>
					<!-- <td class='inner col5 head1' align="center"><b>Cancellation reason</b></td> -->
					<td class='inner col3 head1' align="center"><b>Cancellation remarks</b></td>
					<!-- <td width="14%">Workflow Attached</td> -->
				</tr>
				<%-- <tr>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="makeTable();"/>
				</tr> --%>
			</table>
			
			<table id=tblRoleList width="96%"  border="1" cellspacing="1" cellpadding="2">
			</table>
			
			
			
		</fieldset>

</body>

</html>