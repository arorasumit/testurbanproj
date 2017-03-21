<!-- 
SNo.          CSR NO        Date                 Name                                Desc
[001]                     22-Jan, 2014       Santosh Srivastava              Added a field as lob to send responsibility to action
 -->

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
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="java.util.HashMap"%>
<html>
<head>
<title>SI Transfer</title>
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
var accountArray = new Array();
var logicalSINo="";
var noOfLSI =0;
var lstProducts="";
var lSINo = "";
var currentDate = '<%=AppUtility.getTodayDate()%>';
var hardwareAllowed= new Array();
var BillingDetailsAllowed = new Array();//For SI Tansfer Bug as on Date 14-Jun-2012
var countLookupIcon =-1;
var strLSINO="";
var strServiceID="";
var strAccountID="";
var logicalSiCount=0;
function goToHome()
{
	var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	showLayer();
	myForm.submit();
}

function popDispatchAdd(count,logicalSiNumber)
{
     if(document.getElementById('accountno').value=="")
			     {
			    
			    alert("please enter Account Number");
			        }
	        
			    else
			    {    

var path = "<%=request.getContextPath()%>/siTransfer.do?method=fetchDispatchInfo&count="+count+"&logicalSiNumber="+logicalSiNumber;		
		window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:530px");
}
}

function popBillingAdd(count,logicalSiNumber)
{

  if(document.getElementById('accountno').value=="")
			     {
			    
			    alert("please enter Account Number");
			      }
	        
			    else
			    {    
                    var path = "<%=request.getContextPath()%>/siTransfer.do?method=fetchBillingInfo&count="+count+"&logicalSiNumber="+logicalSiNumber;	
		                window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:530px");
                }
}
function clearData()
{
		lsiArray =new Array() ;       
       SIDArray = new Array(); 
       accountArray = new Array();
	var mytable = document.getElementById('LSITable');	
			var iCountRows = document.getElementById('LSITable').rows.length;
			 for (i =iCountRows-1; i >=1; i--)
			   {	
			       mytable.deleteRow(i);
			   } 
			   
	var mytable = document.getElementById('ProductsTable');	
			var iCountRows = document.getElementById('ProductsTable').rows.length;
			 for (i =iCountRows-1; i >=0; i--)
			   {	
			       mytable.deleteRow(i);
			   } 			   
}
function popitup(url,source) 
{
	

	if(source == 'SOURCE')
	{
	document.getElementById('source').value = 'SOURCE';

	
	}else {
		document.getElementById('source').value = 'TARGET';
	}
	if(url=="SelectParty")
	{
			if(source == 'SOURCE')
			{
				
				document.getElementById('targetPartyName').value = "";
				document.getElementById('targetPartyNo').value = "0";
			}
		
		clearData();
		
		document.getElementById('accountnoString').value = "";
		document.getElementById('accountno').value = "";
		var path = "<%=request.getContextPath()%>/siTransfer.do?method=fetchParty";		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
	}
	
	if(url=="SelectLSI")
	{
		
					 if(document.getElementById('sourcePartyNo').value=="0")
					     {
					    
					   		 alert("Please enter Source Party Number");
					  		  return;
					      }
		      
						 var mytable = document.getElementById('LSITable');	
					  	 var iCountRows = mytable.rows.length;
					  	
					  	 var LSICount = iCountRows-2;
					  	 
					  	 if(LSICount>=1)
							{			
					 			for(i=0;i<lsiArray.length;i++)		
					 			{
									if(lSINo == "")
									{
									lSINo = lsiArray[i] ; 
									}else {
										lSINo = lSINo + ","+ lsiArray[i];
									} 
			 					} 			
							}
						else
							{
								lSINo="0";
							}
							document.getElementById('listOfLSINo').value=lSINo;
							var path = "<%=request.getContextPath()%>/siTransfer.do?method=fetchLogicalSI";		
							window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
		     		 
	}
		if(url=="SelectAccount")
	{

	  
		  if(document.getElementById('targetPartyNo').value=="0")
			     {
			    
			    alert("please enter Target Party Number");
			        }
	        
		    else
		    {    
		    	 clearData();
			var path = "<%=request.getContextPath()%>/siTransfer.do?method=fetchAccount";		
			window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:530px");
		     }
	
	}
	return false;
}

function drawLSITable(logicalSINo,serviceName,serviceId,accountNo,crmAccountNo,shortCode)
{
            
       //---------
       
       lsiArray = logicalSINo;       
       SIDArray = serviceId; 
       accountArray = accountNo;
       if(lsiArray.length>0)
       {
	       for(i=0;i<lsiArray.length;i++)		
			{
				if(strLSINO == "")
				{
					strLSINO = lsiArray[i];
					strServiceID= SIDArray[i];
					strAccountID=accountArray[i];
				}else {
					strLSINO = strLSINO +","+ lsiArray[i];
					strServiceID= strServiceID +","+ SIDArray[i];
					strAccountID=strAccountID +","+ accountArray[i];
				} 
			}
		}      
		lsiArray=strLSINO.split(",");
		SIDArray = strServiceID.split(","); 
        accountArray=strAccountID.split(",");
        
       //--------
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
	 if(iCountRows == 1)
	 {	 
	  var tblRow=document.getElementById('LSITable').insertRow();
		 var tblcol=tblRow.insertCell();
        tblcol.align = "center";
		tblcol.vAlign="top";
		str ="Logical SI Number";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str ="Service Name";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str ="Account Number";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str ="Short Code";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str ="Transfer Date";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		}

	for (var i = 0; i <  logicalSINo.length; i++ ,noOfLSI++)
	 {
		
		var tblRow=document.getElementById('LSITable').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		//str=logicalSINo[i];
		str ="<input type='text' class='inputBorder1' name='logicalSINo' value = ' " + logicalSINo[i] + " ' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		//str=serviceName[i];
		str ="<input type='text' class='inputBorder1' name='serviceName' value = '"+ serviceName[i] + "' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		//str=accountNo[i];
		str ="<input type='text' class='inputBorder1' name='crmAccountNo' value = ' " + crmAccountNo[i] + "' ><input type='hidden' name='accountNo' value = ' " + accountNo[i] + "' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
	//	str=shortCode[i];
		str ="<input type='text' class='inputBorder1' name='shortCode' value = ' " + shortCode[i] + " ' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
		
		//tblcol=tblRow.insertCell();
		//tblcol.align = "left";
		//tblcol.vAlign="top";
	//	str=shortCode[i];
		//str ="<input type='text' class='inputBorder1' name='neworderno' value = '' id='neworderno"+logicalSINo[i]+"'>";
		//CellContents = str;
		//tblcol.innerHTML = CellContents;
		
		//tblcol=tblRow.insertCell();
		//tblcol.align = "left";
		//tblcol.vAlign="top";
	//	str=shortCode[i];
		//str ="<input type='text' class='inputBorder1' name='bdisconorderno' value = '' id='bdisconorderno"+logicalSINo[i]+"'>";
		//CellContents = str;
		//tblcol.innerHTML = CellContents;
		
		//lawkush Start
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str ="<input  type='text'  class='inputBorder1' name='transferdates' size='12' isRequired='0' id='transferdates"+logicalSiCount+"'  readonly='true' onblur='if(this.value.length > 0){return checkdate(this)};' /><font size='1'><a href=\"javascript:show_calendar(searchForm.transferdates"+ logicalSiCount +");\" class=\"borderTabCal\"><img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...'></a></font>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
	
		logicalSiCount=logicalSiCount+1;
		
			//lawkush End
		
		getProductList(serviceId[i],noOfLSI, logicalSINo[i] );
		
		
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
	
	
	setShowCal();
		
	return false;
}

function getProductList(serviceid,counter, logicalSINo)
{
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	 lstProducts = jsonrpc.processes.fetchProductList(serviceid);
	
	
 		var str;
	   var frm=document.getElementById('searchForm');
	  
	  
		
	
	   var mytable = document.getElementById('ProductsTable');	
	     

		var tblRow1=document.getElementById('ProductsTable').insertRow();
		
	
		var tblcol1=tblRow1.insertCell();
		tblcol1.align = "left";
		tblcol1.vAlign="top";
		str ="<table id = 'ProductsTable" + counter + "' ><tr><td><b>" +logicalSINo+"</b></td></tr></table>";
		CellContents = str;
		tblcol1.innerHTML = CellContents;
		
		
		
		 var tab1 = document.getElementById('ProductsTable'+ count);	
		 var tblRow=document.getElementById('ProductsTable'+ counter).insertRow();
		 var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str ="";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str ="SUB SI";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str ="PRODUCT NAME";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str ="BILLING DETAILS";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str ="";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str ="DISPATCH DETAILS";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str ="";
		CellContents = str;
		tblcol.innerHTML = CellContents;
			
		
	 for (var i = 0; i <  lstProducts.list.length; i++)
	 {
		countLookupIcon = countLookupIcon+1;
		var tblRow=document.getElementById('ProductsTable' + counter).insertRow();
		var spid = lstProducts.list[i].serviceProductID;
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
	    str ="<input type='hidden' class='inputBorder1' name='sino' id='sino' value = ' " + count+ " ' >";
	     CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str ="<input type='text' class='inputBorder1' name='spid' id='spid_"+spid+"' value = ' " + lstProducts.list[i].serviceProductID + " ' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str ="<input type='text' class='inputBorder1' name='serviceName' maxlength='15' id='serviceName_"+spid+"' value = ' " + lstProducts.list[i].serviceName + " '>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str ="<input type='text' class='inputBorder1' style='width:170px;float:left;' readonly='true' name='billingAdd' id='billingAdd_"+spid+"' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		//Start :For SI Tansfer Bug as on Date 14-Jun-2012
		BillingDetailsAllowed[countLookupIcon]=lstProducts.list[i].bcpId;
		if(lstProducts.list[i].bcpId != 0)
		{
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
	    str ="<div class='searchBg1'><a href='#' onClick='popBillingAdd("+ count +","+logicalSINo+" )'>...</a></div>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		}
		else
		{
			tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
		    str ="<div ></div>";
			CellContents = str;
			tblcol.innerHTML = CellContents;
		}
		//End
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str ="<input type='text' class='inputBorder1' name='dispatchAdd'  readonly='true' style='width:170px;float:left;' maxlength='15' id='dispatchAdd_"+spid+"' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		hardwareAllowed[countLookupIcon] = lstProducts.list[i].hardwareAllowed;
	//	alert('value :::::;' + hardwareAllowed[countLookupIcon]);
	//	alert('position ::::::' + countLookupIcon);
		
		if(lstProducts.list[i].hardwareAllowed == 1)
		{
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
	    str ="<div class='searchBg1'><a href='#' onClick='popDispatchAdd("+ count +","+logicalSINo+")'>...</a></div>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		}
		count=count+1;
		
		
	}	
	if(  lstProducts.list.length == 0)
	{
	var tblRow=document.getElementById('ProductsTable' + counter).insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 3;
		str='NO RECORD FOUND'
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}
	
		
	//return false;
}
function processSITransfer()
{
 				var myForm=document.getElementById('searchForm');	
 				var mytable = document.getElementById('ProductsTable' );
 				var roleid="0";
 				roleid="<%=request.getAttribute("userroleid")%>";
 				 
					if(lsiArray.length < 1)
					{
						alert('Please Select Atleast One LSI for SI Transfer.');
							return false;
					}
					
 					for(i=0;i<lsiArray.length;i++)
		 					{
 						if(document.getElementsByName('transferdates')[i].value=="")
						{			
							alert("Enter SI Transfer Date for LSI : " + lsiArray[i]) ;
							return false;
						}
		 			   if(dateCompare(document.getElementsByName('transferdates')[i].value,currentDate)==1)
								{			
									alert("SI Transfer Date Cannot be greater than Current Date For LSI : " + lsiArray[i]) ;
									return false;
								}
							}
							
 					for(i=0,j=0;i<count;i++,j++)
 					 {
		 					   if(document.getElementsByName('billingAdd')[i].value=="" && BillingDetailsAllowed[j]!=0)//For SI Tansfer Bug as on Date 14-Jun-2012
		 					    {
		 					       
		 					       alert("Please enter Billing Address for ServiceProductId : "  +  document.getElementsByName('spid')[i].value );
		 					       return false;
		 					    }
		 					    //alert('hardwareAllowed array ::::;' + hardwareAllowed);
		 					   // alert('hardwareAllowed :::::' + hardwareAllowed[i] );
		 					     if(hardwareAllowed[j] == 1 && document.getElementsByName('dispatchAdd')[i].value=="")
		 					    {
		 					       alert("Please Enter Dispatch Address for ServiceProductId : "  +  document.getElementsByName('spid')[i].value );
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
 					
 					var listOfAccount = "";
 					for(var i=0;i<accountArray.length;i++)
 					{
 						if(listOfAccount == "")
 						{
 						listOfAccount = accountArray[i] + ","; 
 						}else {
 							listOfAccount = listOfAccount + accountArray[i] + ",";
 						} 
 					}
 					var listOfTransferDates = "";
 					for(i=0;i<lsiArray.length;i++)		
 					{
 						if(listOfTransferDates == "")
 						{
 							listOfTransferDates = document.getElementById('transferdates'+i).value + ","; 
 						}
 						else {
 							listOfTransferDates = listOfTransferDates + document.getElementById('transferdates'+i).value + ",";
 						} 
 					}
 				//	alert('listOfAccount : ' + listOfAccount);
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
				    //	dateOfTransfer:document.getElementById('transferdate').value,
				    	statusOfSITransfer:document.getElementById('status').value,
				    	sourcePartyNo:document.getElementById('sourcePartyNo').value,
				    	targetPartyNo:document.getElementById('targetPartyNo').value,
				    	accountno:document.getElementById('accountno').value,
				    	remarks:document.getElementById('remarks').value,
				    	error:document.getElementById('error').value,
				    	serviceIdString:listOfServiceID,
				    	accountstr:listOfAccount,
				    	dateOfTransfers:listOfTransferDates,
				    	role_id:roleid,
				    	//rahul tandon
				    	employeeid:empId
				    	//rahul tandon
				    }; 
				   	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
					 var listDto = jsonrpc.processes.processSITransfer(jsData);
					 var result ='';
					 var errorMsg = "";
					 if(listDto.list.length >0 && listDto.list[0].userSpecificError == "")
					 {
   						 result = 'Success';
					 }
					 else 
					 {
					 	 result ='Failure';
					 	 if(listDto.list.length > 0){
					 		errorMsg = listDto.list[0].userSpecificError;
					 	 }	
					 }
					 
					 if(result == 'Success'){
					 	//populate Account table
					 	
					 	/* for (i = 0; i <  listDto.list.length; i++) 
					 	 {
					 	  
					 		document.getElementById('neworderno'+listDto.list[i].logicalSINo).value = listDto.list[i].neworderno;
					 		document.getElementById('bdisconorderno'+listDto.list[i].logicalSINo).value = listDto.list[i].bdisconorderno;
						}
						 document.getElementById('batchID').value = listDto.list[0].batchID; */
						 alert('Transaction Successful. Batch ID : ' + listDto.list[0].batchID);
						 myForm.action="<%=request.getContextPath()%>/siTransfer.do?method=ViewSITransferPage&BatchId="+listDto.list[0].batchID;
						 showLayer();
						 myForm.submit();
						 
					 }
					 else 
					 {
					 	alert('SI Transfer Failed. Please Retry !! \n '+errorMsg);
					 	return;
					 }
					 
					
					
	
}
path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body >
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/siTransfer" styleId="searchForm" method="post">
		<bean:define id="formBean" name="siTransferBean"></bean:define>
		<input type="hidden" name="method" />
		<input type="hidden" name="source" id="source"/>
		<input type="hidden" name="m6ShortCode" id="m6ShortCode"/>
		
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			<tr align="right">
				<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
			</tr>
		</table>	
			
		<div border="1" class="head"> <span>SI TRANSFER</span> </div>
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
			<legend><b></b></legend>
				<table border="0"  align="center" style="margin-top:7px">
				<tr>
					<td width="90px"/>
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
						</td>
				</tr>
					<tr>
					<td width="90px"/>
						<td align="right" style="font-size:9px">Source Party</td>
						<td align="left" nowrap>
							<html:text  property="sourcePartyNo" styleClass="inputBorder1" value="0" style="width:90px;float:left;" readonly="true"></html:text>
							<div class="searchBg1"><a href="#" onClick="popitup('SelectParty','SOURCE')">...</a></div> 
						</td>
						<td align="right" style="font-size:9px;">Source Party Name:</td>
						<td align="left"><html:text  property="sourcePartyName" styleClass="inputBorder1" style="width:90px;" readonly="true"></html:text></td>
						
						
						</tr>
						<tr>
							<td width="90px"/>
					<td align="right" style="font-size:9px">Target Party</td>
						<td align="left" nowrap>
						<html:text  property="targetPartyNo" styleClass="inputBorder1" value="0" style="width:90px;float:left;" readonly="true"></html:text>
							<div class="searchBg1"><a href="#" onClick="popitup('SelectParty','TARGET')">...</a></div> 
						</td>
						<td align="right" style="font-size:9px;">Target Party Name:</td>
						<td align="left"><html:text  property="targetPartyName" value="" styleClass="inputBorder1" style="width:90px;" readonly="true"></html:text></td>
					</tr>
					
				</table>
		</fieldset>
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Target A/C Info</b></legend>
				<table border="0"  align="center" style="margin-top:7px" id="targetACTable">
				<tr>
				
						<td align="center" style="font-size:9px">Customer A/C NO</td>
						<td align="center" style="font-size:9px">A/C Creation</td>
						<td align="center" style="font-size:9px;">Remarks</td>
						<td align="center" style="font-size:9px;">Error</td>
					</tr>
					
					<tr>
					<td>
						<html:text  property="accountnoString" styleId="accountnoString" styleClass="inputBorder1" style="width:90px;float:left;" readonly="true"></html:text>
						<html:hidden property="accountno" styleId="accountno"/>
						<div class="searchBg1"><a href="#" onClick="return popitup('SelectAccount','TARGET')">...</a></div></td>
					
					
						<td>
						<html:text  property="accountcreation" styleClass="inputBorder1" style="width:90px;float:left;" readonly="true"></html:text>
					</td>
					<td>
						<html:text  property="remarks" styleId="remarks" styleClass="inputBorder1" style="width:90px;float:left;" onblur="if( trim(this.value).length>0){return ValidateTextField(this,path,'Remarks')};" ></html:text>
					</td>
			  		<!-- 
			  		<td>
						<html:text  property="transferdate"  styleId="transferdate" styleClass="inputBorder1" style="width:90px;float:left;" readonly="true"></html:text>
						<font size="1">
											<a href="javascript:show_calendar('forms[0].transferdate');" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
									</font>
	
					</td>
						 -->
						<td>
						<html:text  property="error" styleId="error"  styleClass="inputBorder1" style="width:90px;float:left;" ></html:text>
					</td>
					
				</table>
		</fieldset>
		
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Source A/C Info</b></legend>
				<table border="0" id="LSITable" align="center" style="margin-top:7px">
				<tr>
					<td align="left" style="font-size:9px">Select Logical SI</td>
						<td align="left" nowrap>
							<input type="hidden" id="listOfLSINo"/>
						
							<div class="searchBg1"><a href="#" onClick="popitup('SelectLSI','SOURCE')">...</a></div> 
						</td>
				</tr>
					
					
				</table>
		</fieldset>
		<fieldset width="100%" border="1" align="center" class="border3">
			<legend><b>Products Details</b></legend>
				<table border="1" id="ProductsTable" align="center" style="margin-top:7px">
					
					
				</table>
		</fieldset>
		<fieldset width="100%" border="0" align="center" class="border3">
			<legend><b></b></legend>
				<table border="0" id="process" align="center" style="margin-top:7px">
					<tr>
						<td align="center" style="font-size:9px" > 
						<div class="searchBg1"><a href="#" onClick="processSITransfer()">Process</a></div> 
				
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
