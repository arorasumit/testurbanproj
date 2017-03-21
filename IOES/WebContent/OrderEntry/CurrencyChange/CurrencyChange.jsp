<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.ReportsBean"%>
<%@page import="com.ibm.ioes.utilities.AppUtility"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="java.util.HashMap"%>
<html>
<head>
<title>Currency Change</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<%
			HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession sessionObj = (HttpSession) userHashMap.get(request.getSession().getId());
			UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
 %>
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
var count=0;
var counter = 1;
var lsiArray = new Array();
var SIDArray = new Array();
var oldCurrencyArray=new Array();
var newCurrencyArray=new Array();
var rateArray=new Array();
var logicalSINo="";
var noOfLSI =0;
var lstProducts="";
var currentDate = '<%=AppUtility.getTodayDate()%>';
var currencyCount=1;
var strLSINO="";
var strServiceID="";
var strCurrencyID="";
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	showLayer();
	myForm.submit();
}

function popitup(url) 
{
	
	if(url=="SelectParty")
	{
		var mytable = document.getElementById('LSITable');
		var mytable1 = document.getElementById('ProcessInfo');	 
	   	var iCountRows = mytable.rows.length;
	   	var iCountRows1 = mytable1.rows.length;	   
	   	if(iCountRows>2)
	   	{
		   for (i =iCountRows-1; i >=2; i--)
		   {	
		       mytable.deleteRow(i);
		       
		   } 		   
		 }
		 if(iCountRows1>1)
		 {
			 for (i =iCountRows1-1; i >=1; i--)
			   {	
			       mytable1.deleteRow(i);
			       
			   }
		  }
		currencyCount=1; 
		logicalSINo=""; 
		var path = "<%=request.getContextPath()%>/currencyChange.do?method=fetchParty";		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
	}
	
	if(url=="SelectLSI")
	{
	 if(document.getElementById('sourcePartyNo').value=="0")
			     {
			    
			    alert("Please enter Source Party Number");
			    return;
			        }
			
			if(currencyCount>1)
			{	
			
	 			for(i=0;i<lsiArray.length;i++)		
	 			{
					if(logicalSINo == "")
					{
					logicalSINo = lsiArray[i] ; 
					}else {
						logicalSINo = logicalSINo + ","+ lsiArray[i];
					} 
	 			} 			
			}else
			{
				logicalSINo="0";
			}
			document.getElementById('listOfLSINo').value=logicalSINo;
			var path = "<%=request.getContextPath()%>/currencyChange.do?method=fetchLogicalSI&logicalSINo="+logicalSINo;		
			window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
	}
	
		      
		
	
}
function selectCurrency(currencyCount)
{

		var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=fetchCurrency&currencyCount="+currencyCount;		
		window.showModalDialog(path,window,"status:false;dialogWidth:750px;dialogHeight:450px");
	
}

function drawLSITable(logicalSINo,serviceName,serviceId,accountNo,crmAccountNo,shortCode,currencyId,currencyName)
{
       lsiArray = logicalSINo;       
       SIDArray = serviceId; 
       oldCurrencyArray=currencyId;
       if(lsiArray.length>0)
       {
	       for(i=0;i<lsiArray.length;i++)		
			{
				if(strLSINO == "")
				{
					strLSINO = lsiArray[i];
					strServiceID= SIDArray[i];
					strCurrencyID=oldCurrencyArray[i];
				}else {
					strLSINO = strLSINO +","+ lsiArray[i];
					strServiceID= strServiceID +","+ SIDArray[i];
					strCurrencyID=strCurrencyID +","+ oldCurrencyArray[i];
				} 
			}
		}      
		lsiArray=strLSINO.split(",");
		SIDArray = strServiceID.split(","); 
        oldCurrencyArray=strCurrencyID.split(",");     
       var str;
	   var frm=document.getElementById('searchForm');
	  
	   var mytable = document.getElementById('LSITable');	
	   var iCountRows = mytable.rows.length;
  
	   /*for (i = 2; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(2);
	   }   */

	 var lsi =new Array();
	 lsi = logicalSINo;
	
	for (var i = 0; i <  logicalSINo.length; i++ ,noOfLSI++)
	 {
		
		var tblRow=document.getElementById('LSITable').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		//str=logicalSINo[i];
		str ="<input type='text' class='inputBorder1' name='logicalSINo' value = ' " + logicalSINo[i] + " ' readonly='ture' style='width:90px;float:left;' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		//str=serviceName[i];
		str ="<input type='text' class='inputBorder1' name='serviceName' value = '"+ serviceName[i] + "' readonly='ture' style='width:200px;float:left;'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		//str=accountNo[i];
		str ="<input type='text' class='inputBorder1' name='crmAccountNo' value = ' " + crmAccountNo[i] + "' readonly='ture' style='width:90px;float:left;'><input type='hidden' name='accountNo' value = ' " + accountNo[i] + "'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
	//	str=shortCode[i];
		str ="<input type='text' class='inputBorder1' name='shortCode' value = ' " + shortCode[i] + " ' readonly='ture' style='width:90px;float:left;'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
	//	str=shortCode[i];
		str ="<input type='text' class='inputBorder1' name='currencyName' value = ' " + currencyName[i] + " ' readonly='ture' style='width:90px;float:left;'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
	//	str=shortCode[i];
		str ="<input type='text' class='inputBorder1' id='rate" + logicalSINo[i] + " ' name='rate' value = '' style='width:90px;float:left;' onblur='if(this.value.length > 0){return checknumber(this)}'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
	//	str=shortCode[i];
		str ="<input type='text' class='inputBorder1' id='curShortCode' name='curShortCode' value = '' readonly='ture' style='width:90px;float:left;'> <input type='hidden' class='inputBorder1' id='currencyID' name='currencyID' value = '' ><div class='searchBg1'><a href='#' onClick=selectCurrency('"+currencyCount+"') >...</a></div> ";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		//tblcol=tblRow.insertCell();
		//tblcol.align = "left";
		//tblcol.vAlign="top";	
		//str ="<input type='checkbox'  id='chkbox"+currencyCount+"' name='chkbox' value = '' >";
		//CellContents = str;
		//tblcol.innerHTML = CellContents;
		
		//tblcol=tblRow.insertCell();
		//tblcol.align = "left";
		//tblcol.vAlign="top";
	//	str=shortCode[i];
		//str ="<input type='text' class='inputBorder1' name='bdisconorderno' value = '' id='bdisconorderno"+logicalSINo[i]+"'>";
		//CellContents = str;
		//tblcol.innerHTML = CellContents;	
		drawTargetCurrencyInfo(currencyCount);	
		currencyCount=currencyCount+1;
		
		
	}	
	if( logicalSINo.length == 0)
	{
	var tblRow=document.getElementById('LSITable').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 3;
		str='NO RECORD FOUND'
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}
	
		
	return false;
}
function drawTargetCurrencyInfo(currencyCount)
{
	var tblRow=document.getElementById('ProcessInfo').insertRow();
	tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";	
		str ="<input type='text' class='inputBorder1' name='newOrderNo' value = '' readonly='ture' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";	
		str ="<input type='text' class='inputBorder1' name='DisOrderNo' value = '' readonly='ture'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str ="<input type='text' showcal='date' class='inputBorder1' name='dateOfTransfer1' id='dateOfTransfer1"+currencyCount+"' readonly='true'><font size='1'><a href=\"javascript:show_calendar(searchForm.dateOfTransfer1"+ currencyCount +");\" class=\"borderTabCal\"><img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...'></a></font>";	
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";	
		str ="<input type='text' class='inputBorder1' name='errorMsg' value = '' readonly='ture'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		setShowCal();
		
	
}

function processChangeCurrency()
{
 				var myForm=document.getElementById('searchForm');	
 				 // var mytable = document.getElementById('ProductsTable' );
 				 	
 			var roleid="0";
 			roleid="<%= request.getAttribute("userroleid") %>";
			if(document.getElementsByName('sourcePartyName').value=="")	
			{
				alert("Please Select Source Party.!");
 				return false;
			}
 			if(lsiArray.length==0)
 			{
 				alert("Please Select Logical SI.!");
 				return false;
 				
 			}		
			for(i=0;i<lsiArray.length;i++)
			 {
			 	
					   if(document.getElementsByName('curShortCode')[i].value=="")
					    {
					       alert("Please Enter Currency for Logical SI : "  +  lsiArray[i] );
					         return false;
					    }
					   if(document.getElementsByName('rate')[i].value=="")
					    {
					       
					       alert("Please enter Rate for Logical SI : "  +  lsiArray[i] );
					       return false;
					    }					    
					    if(document.getElementsByName('dateOfTransfer1')[i].value=="")
					    {
					       alert("Please Enter Transfer Date for Logical SI : "  +  lsiArray[i] );
					         return false;
					    }
					    if(dateCompare(document.getElementsByName('dateOfTransfer1')[i].value,currentDate)==1)
						{			
							alert("Currency Transfer Date Cannot be greater than Current Date For LSI : " + lsiArray[i]) ;
							return false;
						}
					    if(trim(document.getElementsByName('curShortCode')[i].value) == trim(document.getElementsByName('currencyName')[i].value))
					    {
					    	alert("Please Select Different Curreny For LSI : " + lsiArray[i]) ;
							return false;
					    }
					    
			    }
 			var listOfLSI = "";
 			for(i=0;i<lsiArray.length;i++)		
 			{
				if(listOfLSI == "")
				{
				listOfLSI = lsiArray[i] + ","; 
				}else {
					listOfLSI = listOfLSI + lsiArray[i] + ",";
				} 
 			}
			var listOfServiceID = "";
			for(var i=0;i<SIDArray.length;i++)
			{
				if(listOfServiceID == "")
				{
				listOfServiceID = SIDArray[i] + ","; 
				}else {
					listOfServiceID = listOfServiceID + SIDArray[i] + ",";
				} 
			}
			var listOfOldCurId="";
			for(var i=0;i<oldCurrencyArray.length;i++)
			{
				if(listOfOldCurId == "")
				{
				listOfOldCurId = oldCurrencyArray[i] ; 
				}else {
					listOfOldCurId = listOfOldCurId + ","+ oldCurrencyArray[i];
				} 
			}
 				
 			//rate
 			var listOfRate="";
 			for(var i=0;i<lsiArray.length;i++)
			{				
				if(listOfRate== "")
				{
				
					listOfRate = document.getElementsByName('rate')[i].value ; 
					
				}else {
					listOfRate = listOfRate + "," + document.getElementsByName('rate')[i].value ;
				} 
			}
						
 			//newcurrency
 			var listOfAccNo="";
 			for(var i=0;i<lsiArray.length;i++)
			{				
				if(listOfNewCurrency== "")
				{				
					listOfAccNo = document.getElementsByName('AccountNo')[i].value ; 
					
				}else {
					listOfAccNo = listOfAccNo + "," + document.getElementsByName('AccountNo')[i].value ;
				} 
			}		
			
 			//newcurrency
 			var listOfNewCurrency="";
 			for(var i=0;i<lsiArray.length;i++)
			{				
				if(listOfNewCurrency== "")
				{
				
					listOfNewCurrency = document.getElementsByName('currencyID')[i].value ; 
					
				}else {
					listOfNewCurrency = listOfNewCurrency + "," + document.getElementsByName('currencyID')[i].value ;
				} 
			}			
 			//transferDate
 			var listOfTranferDate="";
 			for(var i=0;i<lsiArray.length;i++)
			{				
				if(listOfTranferDate== "")
				{
				
					listOfTranferDate = document.getElementsByName('dateOfTransfer1')[i].value ; 
					
				}else {
					listOfTranferDate = listOfTranferDate + "," + document.getElementsByName('dateOfTransfer1')[i].value ;
				} 
			}
			var batchId=document.getElementById('batchID').value;
			if(batchId == "")
			{
			 batchId=0;
			}
					//rahul tandon  
					var empId = "<%=objUserDto.getEmployeeId() %>";
					//rahul tandon	
 					var jsData = 
				    {
				    	batchID:batchId,
				    	logicalSistr:listOfLSI,
				    	dateOfTransfer:document.getElementById('dateOfTransfer').value,
				    	statusOfSITransfer:document.getElementById('status').value,
				    	sourcePartyNo:document.getElementById('sourcePartyNo').value,				    	
				    	accountno:listOfAccNo,				    	
				    	error:document.getElementById('errorMsg').value,
				    	serviceIdString:listOfServiceID,
				    	oldCurIds:listOfOldCurId,
				    	rateStr:listOfRate,
				    	newCurIds:listOfNewCurrency,
				    	dateOfTransfer:listOfTranferDate,
				    	role_id:roleid,
				    	//rahul tandon
				    	employeeid:empId
				    	//rahul tandon
				    	 
				    }; 
				   	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
					 var listDto = jsonrpc.processes.processCurrencyChange(jsData);
					 var result ='';
					 if(listDto.list.length >0)
					 {
					 	 result = 'Success';
					 } else 
					 {
					 	 result ='Failure';
					 }
					 
					 if(result == 'Success'){
					 	//populate Account table
					 	
					 	/* for (i = 0; i <  listDto.list.length; i++) 
					 	 {
					 	  
					 		document.getElementById('neworderno'+listDto.list[i].logicalSINo).value = listDto.list[i].neworderno;
					 		document.getElementById('bdisconorderno'+listDto.list[i].logicalSINo).value = listDto.list[i].bdisconorderno;
						}
						 document.getElementById('batchID').value = listDto.list[0].batchID; */
						 alert('Transaction Completed. Batch ID : ' + listDto.list[0].batchID);
						 myForm.action="<%=request.getContextPath()%>/currencyChange.do?method=ViewCurrencyChangePage&BatchId="+listDto.list[0].batchID;
						 
						 
						 showLayer();
						 myForm.submit();
						 
					 }
					 else 
					 {
					 	alert('Currency Transfer Failed. Please Retry !!');
					 	return;
					 }
					 
					
					
	
}
path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body >
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/currencyChange" styleId="searchForm" method="post">
		<bean:define id="formBean" name="currencyChangeBean"></bean:define>
		<input type="hidden" name="method" />
		<input type="hidden" name="source" id="source"/>
		
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> <span>CURRENCY CHANGE</span> </div>
		<div border="1" class="error" align="center">
			<logic:present name="validation_errors">
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>
			</logic:present>
			<html:messages id="message" message="true">
				<li><font color="red"><bean:write name="message"/></font></li>
			</html:messages>
		</div>
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Batch Details</b></legend>
				<table border="0"  align="left" style="margin-top:7px">
				<tr>
					
						<td align="right" style="font-size:9px">Batch ID</td>
						<td align="left" nowrap>
							<html:text  property="batchID" value="" styleId="batchID" styleClass="inputBorder1" style="width:90px;float:left;" readonly="true"></html:text>
						</td>
						<td align="right" style="font-size:9px">Status</td>
						<td align="left" nowrap>
							<html:text  property="status" styleId="status"  styleClass="inputBorder1" style="width:90px;float:left;" readonly="true" value="NEW"></html:text>
						</td>
						
						<td align="right" style="font-size:9px">Date</td>
						<td align="left" nowrap>
							<html:text  property="dateOfTransfer" styleId="dateOfTransfer" styleClass="inputBorder1" style="width:90px;float:left;" readonly="true"></html:text>
							<font size="1">
											<a href="javascript:show_calendar(searchForm.dateOfTransfer);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
									</font>
						</td>
				</tr>
					<tr>
					
						<td align="right" style="font-size:9px">Party Number</td>
						<td align="left" nowrap>
							<html:text  property="sourcePartyNo" styleClass="inputBorder1" value="" style="width:90px;float:left;" readonly="true"></html:text>
							<div class="searchBg1"><a href="#" onClick="popitup('SelectParty')">...</a></div> 
						</td>
						<td align="right" style="font-size:9px"> Party Name:</td>
						<td align="left" nowrap>
							<html:text  property="sourcePartyName" value="" styleClass="inputBorder1" style="width:90px;" readonly="true"></html:text>
							
						</td>
						</tr>				
					
				</table>
		</fieldset>
		
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Source Currency Info</b></legend>
				<table border="1"  align="left" style="margin-top:7px" id="LSITable">
					<tr>				
				<td colspan="9" >
				<html:text  property="logicalSINo" styleClass="inputBorder1" style="width:90px;float:left;" readonly="true"></html:text>
				<input type="hidden" id="listOfLSINo"/>
				<div class="searchBg1"><a href="#" onClick="popitup('SelectLSI')">...</a></div> 
				</td>
				</tr>
				<tr>
						<td align="left" style="width:90px;float:left;">Logical_SI_NO</td>
						<td align="left" style="width:200px;float:left;">Product Name</td>						
						<td align="left" style="width:90px;float:left;">Customer A/C NO</td>
						<td align="left" style="width:90px;float:left;">Short Code</td>
						<td align="left" style="width:90px;float:left;">Currency</td>
						<td align="left" style="width:90px;float:left;">Rate</td>						
						<td align="left" style="width:200px;float:left;">New Currency</td>						
						
				</tr>
				
				</table>
		</fieldset>
		
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Target Currency Info</b></legend>
				<table border="1" id="ProcessInfo" align="left" style="margin-top:7px">
			
				<tr>				
				<td align="left" width="5%"  style="font-size:9px">New Order No</td>
				<td align="left" width="5%"  style="font-size:9px">Billing Disconnection Order NO</td>
				<td align="left" width="5%"  style="font-size:9px;">Transfer Date</td>					
				<td align="left" width="5%"  style="font-size:9px;">Error Message</td>								
						
				</tr>					
			</table>
		</fieldset>		
		<fieldset width="100%" border="1" align="center" class="border3">			
				<table border="0" id="process" align="center" style="margin-top:7px">
					<tr>
						<td align="center" style="font-size:9px" > 
						<div class="searchBg1"><a href="#" onClick="processChangeCurrency()">Process</a></div> 
				
						</td>
					</tr>
					
				</table>
		</fieldset>
	</html:form>
</body>

<script type="text/javascript">

	function setShowCal()
	{
		$(":text[showcal='date']").each(function() {
			var thisEl = $(this).attr("id");
		    $("#"+thisEl ).datepicker({dateFormat: 'dd/mm/yy',showOn: 'none' });
		});
	}
	
	
	
</script>



</html>
