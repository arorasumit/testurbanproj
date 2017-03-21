<!--Tag Name Resource Name  Date		CSR No			Description -->
<!-- AUTHOR : Vipin Saharia -->
<!--001    Nancy  28/10/2016 20160719-R2-022519    Added Button for One Time Migration of IB2B ATTACHMENTS-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@page import="com.ibm.ioes.beans.DefaultBean"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html:html>
<head>
<title>Archive Ib2b Orders</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="./gifs/style.css" type="text/css" rel="stylesheet">
<link href="./css/ReportHeaderFreeze.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ReportHeaderFreeze.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<script language="javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
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
.opaqueLayerNote
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
path='<%=request.getContextPath()%>';
var sessionId='<%=request.getSession().getId() %>';
jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
function showLayerWithNote() 
{
	setLayerPositionNote();
    var shadow = document.getElementById("shadowNote");
    shadow.style.display = "block"; 
    shadow = null;
}
function setLayerPositionNote() 
{
    var shadow = document.getElementById("shadowNote");
    var bws = getBrowserHeight();
    shadow.style.width = bws.width + "px";
    shadow.style.height = bws.height + "px";
    shadow = null;
}
function goToHome()
{
	document.forms[0].action="<%=request.getContextPath()%>/defaultAction.do?method=goToHomeAfterClosing";
	showLayer();
	document.forms[0].submit();
}
//check to input only numbers
function isNumber(evt) {
	 evt = (evt) ? evt : window.event;
	    var charCode = (evt.which) ? evt.which : evt.keyCode;
	    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
	    	alert("input only numbers");
	        return false;
	    }
	    return true;
}
function validate(){ 
    var myForm=document.getElementById('searchForm');
	var batchID= myForm.elements["valueBatchID"].value;
	var exclusionNo= myForm.elements["valueEXcludingID"].value;
	if (batchID==null || batchID==""){  
	  alert("BatchID is Mandatory for Archival"); 
	  return false; 
	}else{
		//alert(exclusionNo);
		var patron = /^(-?\d*\.?\d*,?)+$/;
		if (!patron.test(exclusionNo)) 
		{        
		    alert("exclusion no should be comma sepearated.Please avoid any spaces if present");                      
		}
		else 
		{
			 initiateArchival(4);    
		}
			
	}
}

//check to input only comma seperate values
function isCommaSperatedValue(evt){
	evt = (evt) ? evt : window.event;
   var charCode = (evt.which) ? evt.which : event.keyCode;
   if (charCode != 44 && charCode > 31 && (charCode < 48 || charCode > 57)){
	   alert("input only numbers or comma seperated");
      return false;
   }
   return true;
}

function initiateArchival(categoryId){
	var myForm=document.getElementById('searchForm');
	
	var batchID= myForm.elements["valueBatchID"].value;
	//disable all 3 buttons on GUI
	
	//priya
	var exclusionNo= myForm.elements["valueEXcludingID"].value;
	var confirmation = confirm("Do you want to continue ?");
	
	if(confirmation==true)
	{
		
		/*Archival Starts@Satish Start */
		 if(categoryId ==4 ){	
			 jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			 var flag = jsonrpc.processes.checkArchivalPhase(batchID);
		    	//alert(flag);
		    	if(flag=="ARCHIVAL"){
		    		alert("It has been already Archived.Kindly Compute Again ");
					return false;
		    	}else {
		    	       var lsiData = jsonrpc.processes.checkLsiGroupIdServiceList(batchID,exclusionNo);
		    	       //alert(lsiData);
		    	       if(lsiData == false){
					   alert("One or more Data doesn't exist in DB ");
					   return false;
		    	                  }  		
			         myForm.action="<%=request.getContextPath()%>/defaultDraftNewOrdAction.do?method=goToArchiveIb2bRecords&categoryId="+categoryId;
			        showLayerWithNote();
			         myForm.submit();
	  /*Archival Starts@Satish End */
		    	}
		}else{ 
	  myForm.action="<%=request.getContextPath()%>/defaultDraftNewOrdAction.do?method=goToComputeIb2bRecords&categoryId="+categoryId;
	  showLayerWithNote();
	  myForm.submit();
		}
	 
	 }
 }


function doPurging()
{
	var success = jsonrpc.processes.performPurging();
	if(success ==1)
		alert('purging has been done succesfully')
	else
		alert('some error occured')
}
//[001]START
function initiateMigration()
{
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	var noOfRecords = jsonrpc.processes.countOfRecordsToMigrate();
	//alert("No Of Records To Migrate Are :" + noOfRecords);
	if(noOfRecords==0)
		{
			alert("No Attachments To Migrate");
		}
	else
		{
			alert("No Of Attachments To Migrate Are :" + noOfRecords);
			var message = jsonrpc.processes.initiateMigration();
			if(message!=null)
			{
			alert(message);
			}
			else
				{
				alert("Migration failed");
				}
			
		}
} //[001]END

</script>
</head>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<body>
	<div id="shadow" class="opaqueLayer">
		<font color="yellow" size="6"> Please wait..</font>
	</div>
	<div id="shadowNote" class="opaqueLayerNote">
		<font color="yellow" size="6"> Please wait..</font><br />
		<br />
		<br /> <font color="red" size="6">*It might take some time to
			archive data. Be Patient !!!</font>
	</div>
	<html:form action="/defaultDraftNewOrdAction" method="post"
		styleId="searchForm">
		<!-- ArchiveOrderAction -->
		<jsp:include page="../header.jsp" flush="true" />
		<img src="gifs/top/home.gif" title="Home" onClick="goToHome('Home');"></img>
		<div class="head">
			<span>Archive Ib2b Orders</span>
		</div>
		<div align="center">
			<span><font color="red" size="4">*It might take some
					time to archive data. Be Patient !!! </font><br />
			<br />
			<br />
			<br /></span>
		</div>
				<fieldset class="border1">
			<legend>
				<b>Compute Archival Data</b>
			</legend>
			<table align="center" width="100%" height="50%">
				<tr>
					<td align="center" height="30%"><html:button property=""
							value="Draft New Orders" style="align:center;font-size:15px;"
							onclick="initiateArchival(1);" /></td>
					<td align="center" height="30%"><br /> <html:button
							property="" value="Cancelled New Orders"
							style="align:center;font-size:15px;"
							onclick="initiateArchival(2);" /></td>
					<td align="center" height="30%"><br /> <html:button
							property="" value="Permanent Disconnection Orders"
							style="align:center;font-size:15px;"
							onclick="initiateArchival(3);" /></td>
				</tr>
			</table>
		</fieldset>
	&nbsp;
	&nbsp;
	  <fieldset class="border2" >
	      <legend> <b> Archival Data</b></legend>	
	      <table align="center"  width="100%" height="50%">
	      
	             <tr align="center" height="30%" >
	             <td>Batch ID</td>		
   					<td><input type="text"  class="textfield" name="valueBatchID" size="30"  value=""  id="valueBatchID" onkeypress="javascript:return isNumber(event)"></td> 
				 </tr> 
				<tr align="center" height="30%">
				<td>ExcluSion No:</td>		
   				<td>
   				<input type="text" class="textfield"  name="valueEXcludingID" size="30"  value=""  id="valueEXcludingID" onkeypress="javascript:return isCommaSperatedValue(event)" >
   				</td>
				<td align="left">
				<span style="color:red;font-size:20px;">
				*In Case of Draft/Cancel Enter OrderNO.In Case of PD Enter LSI.
				</span>
				</td>				  
				</tr>
	       <br/> 
	       
		<tr><td></td>
		<td align="center"  height="30%">
		<html:button property="" value="Archive Order " style="align:center;font-size:15px;" onclick="validate()"/></td>
					</td>
			</table>
		</fieldset>
		<!--Satish Ends -->
		
		<%-- <table align="center" width="100%" height="50%">
			<tr>
				<td align="center" height="30%"><br />
				<html:button property="" value="Archive Draft New Orders"
						style="align:center;font-size:15px;"
						onclick="initiateArchival(1);" /></td>
			</tr>
			<tr>
				<td align="center" height="30%"><br />
				<html:button property="" value="Archive Cancelled New Orders"
						style="align:center;font-size:15px;"
						onclick="initiateArchival(2);" /></td>
			</tr>
			<tr>
				<td align="center" height="30%"><br />
				<html:button property=""
						value="Archive Permanent Disconnection Orders"
						style="align:center;font-size:15px;"
						onclick="initiateArchival(3);" /></td>
			</tr>  	</table> --%>
			<tr>
				<td align="center" height="30%"><br />
				<html:button property="" value="Purge Temp"
						style="align:center;font-size:15px;" onclick="doPurging();" /></td>
			</tr>
			<%--001 STARTS --%>
			<tr>
				<td align="center" height="30%"><br /> <input type="hidden"
					value="One Time Migration of ib2b Attachments"
					style="align: center; font-size: 15px; visibility: none;"
					onclick="initiateMigration();" /></td>
			</tr>
			<%--OO1 ENDS --%>
	
		<input type="hidden" name="isArchivalAction" value="1" />
		<logic:messagesPresent message="true">
			<table width="50%" align="center" id='messageBody'>
				<tr>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td><font color="red" face="Verdana" size="2"><html:messages
								id="message" message="true">
								<li><bean:write name="message" /></li>
							</html:messages></font></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
				</tr>
			</table>
		</logic:messagesPresent>
	</html:form>
</body>
</html:html>
