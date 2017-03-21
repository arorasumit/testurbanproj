<!-- [TRNG22032013031]    Vijay Pathak    8th july-2013    Copy Service/Order - to n-number of times option to be given -->
<!-- [001] 				Saurabh Singh 		8th Sep 2013    Code done to fix defect no MASDB00194469-->
<!-- [002] 				Santosh Srivastava 		30JAN-2014  Fix code to display all PO   -->
<!-- [003] 				Ravneet Singh 		11 Jul-2014  Perfomance Checks   -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.utilities.AppUtility"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.utilities.Utility"%> 

<html>
<head>
<title>Copy Order</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
<%
	HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
	HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
	UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>
var PAGE_SIZE_COPY_ORDER=<%=objUserDto.getPagesizeForCopyOrder()%>;
var gb_roleID = "<%=objUserDto.getUserRoleId() %>";
var counter = 1;
var currentOrderNo;
var callerWindowObj = dialogArguments;
var serviceList;
var testresult;
currentOrderNo = callerWindowObj.document.getElementById('poNumber').value;
function populateService()
{
			var currentOrderNo = callerWindowObj.document.getElementById('poNumber').value;
			var myForm=document.getElementById('searchForm');
			var orderNo=document.getElementById('orderNo').value;
			var accountId=document.getElementById('accountID').value;
			if(orderNo != 0 )
			{
				var mytable = document.getElementById('tabLogicalType');	
	   			var iCountRows = mytable.rows.length;
  
	   			for (i = 1; i <  iCountRows; i++)
	   			{
	      			mytable.deleteRow(1);
	   			}  
	   			var mytable1 = document.getElementById('tabOk');	
	   			var iCountRows1 = mytable1.rows.length;
  
   				for (i = 0; i <  iCountRows1; i++)
	   			{
	      			mytable1.deleteRow(0);
	   			} 
	   			 
				getserviceForThisOrder(orderNo);
			}
			
				else
			{
				 document.getElementById('first').disabled=true;
			     document.getElementById('prev').disabled=true;
			     document.getElementById('last').disabled=true;
			     document.getElementById('next').disabled=true;
			
			}
			
}

function searchOrderNumber()
{	
	searchFromList('SERVICEID','SELECTCOPYORDER');
}


function getOrders()
{
	var currentOrderNo = callerWindowObj.document.getElementById('poNumber').value;
	var myForm=document.getElementById('searchForm');
	var orderNo=document.getElementById('orderNo').value;
	document.getElementById('hdnEnteredOrderNo').value=orderNo;
	var accountId=document.getElementById('accountID').value;
	var orlen=document.getElementById('orderNo').value.length;
	if((orderNo == 0 || trim(orderNo)== "" ))
	{
		alert("Please enter OrderNo ");
		return false;
	}
	
  if((orlen>10 ))
	{
		alert("Length Can't be Greater than 10 ");
		return false;
	}
	else
	{
		
		var jsData = 
		{
			orderNo:orderNo
		};
		jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		try
		{
			var orderDetails = jsonrpc.processes.getOrderDetails(jsData);
			if(orderDetails.list.length >= 1)
			{
				if(orderDetails.list[0].orderType=="Change")
	 				{
						alert("Only New Orders are Allowed for Copy Order/Services");
						return false;
					}
	 			document.getElementById('orderNo').value=orderDetails.list[0].orderNo;
				document.getElementById('accountID').value=orderDetails.list[0].accountID;
				document.getElementById('accountManager').value=orderDetails.list[0].accountManager;
				document.getElementById('projectManager').value=orderDetails.list[0].projectManager;
				document.getElementById('quoteNo').value=orderDetails.list[0].quoteNo;
				document.getElementById('currencyCode').value=orderDetails.list[0].currencyCode;
				searchOrderNumber();
	 		}
			else
			{
				document.getElementById('accountID').value="";
				document.getElementById('accountManager').value="";
				document.getElementById('projectManager').value="";
				document.getElementById('quoteNo').value="";
				document.getElementById('currencyCode').value="";
	 			searchOrderNumber();
			}
	 	}
		catch(e)
		{	
			alert("exception :  "+ e);
		}
	}
}
function copyServices()
{
			var myForm=document.getElementById('searchForm')
			var count = document.getElementById('hdnCounterLength').value;
			var totalService=0;
			var number = -1;
			var serviceList='';
			var serviceProductList='';
			var poList='';
			var licenseCompanyList='';
			var storeList='';
			//[TRNG22032013031] --start 
			var noOfCopy='';
			//[TRNG22032013031] -- end
			for (i = 0 ; i <= count; i++)
    		{
    				if(document.getElementById('chk'+i).checked)
					{
						//lawkush start			
						if(checkdigitsDefaultFilled(document.getElementById('noOfCopy'+i),1)==false)
						{
							break;		
						}	
								
						//lawkush end
					
						//vijay start
						//here we are counting the total service those are going to create		
  					    var numberOfCopy = document.getElementById('noOfCopy'+i).value
	 					 totalService = totalService+ parseInt(numberOfCopy);
						//vijay end		
						var productList = jsonrpc.processes.populateProductList(document.getElementById('chk'+i).value);
	    				if (productList.list.length>0)
							{
	    						serviceList = serviceList + document.getElementById('chk'+i).value + ",";
	    						totalService = totalService+1;
	    					}
	    					
							//[TRNG22032013031] --start 
							noOfCopy = noOfCopy + document.getElementById('noOfCopy'+i).value + ",";
							//[TRNG22032013031] -- end
							
	    					enteredOrderNo = document.getElementById('hdnEnteredOrderNo').value;
	    					if(currentOrderNo!=enteredOrderNo)
	    					{
		    					for (var j = 0; j <  productList.list.length; j++)
		    					{
		    						var serviceProductID = productList.list[j].serviceProductID;
		    						serviceProductList = serviceProductList + serviceProductID + ",";
	    						
		    						//Checking PO details for Each Line Item
		    						if(document.getElementById('po'+serviceProductID).value==0)
		    						{
		    							alert("Please Select Valid PO for Line item" +serviceProductID);
		    							document.getElementById('po'+serviceProductID).focus();
		    							return false;
		    						}
		    						else
		    						{
		    							poList = poList + document.getElementById('po'+serviceProductID).value + ",";
		    						}
		    						//Checking License Company for Each Line Item
		    						if(document.getElementById('lcompany'+serviceProductID).value==0)
		    						{
		    							alert("Please Select License Company for Line item" +serviceProductID);
		    							document.getElementById('lcompany'+serviceProductID).focus();
		    							return false;
		    						}
		    						else
		    						{
		    							licenseCompanyList = licenseCompanyList + document.getElementById('lcompany'+serviceProductID).value + ",";
		    						}
		    						//Checking Store for Each Line Item
		    						if(document.getElementById('store'+serviceProductID).value==0 && document.getElementById('store'+serviceProductID).disabled==false)
		    						{
		    							alert("Please Select Store for Line item" +serviceProductID);
		    							document.getElementById('store'+serviceProductID).focus();
		    							return false;
		    						}
		    						else
		    						{
		    							if(document.getElementById('store'+serviceProductID).disabled==true)
		    							{
		    								storeList = storeList + 0 + ",";
		    							}
		    							else
		    							{
		    							storeList = storeList + document.getElementById('store'+serviceProductID).value + ",";
		    							}
		    						}
		    					}
		    				}	
					}
					else 
					{
						number++;
					}
					if(number==count)
					{
						alert("Please select a service");
						return false;
					}
			}	
			if(testresult==false)
			{
				return false;
			}
			
			currentOrderNo = callerWindowObj.document.getElementById('poNumber').value;
			enteredOrderNo = document.getElementById('hdnEnteredOrderNo').value;
			document.getElementById('hdnCurrentOrderNo').value = currentOrderNo;
		//vijay. add a restriction and limit to copy the service in single order	
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			var totalServiceMessage = jsonrpc.processes.totalServiceCountCheck(currentOrderNo,totalService);
			if(totalServiceMessage!= '' ){
				alert(totalServiceMessage);
				return false;
			}
		//vijay 	
			var jsData = 
				{
					serviceIdString:serviceList,
					orderNo:currentOrderNo
				};
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			var sessionId='<%=request.getSession().getId() %>';
			try
			{
			//[TRNG22032013031] --start 
			//increment an argument 'noOfCopy'
			//passing roleID for setting initiated to in serviceMaster
			//[003] Start
				var data = jsonrpc.processes.getValidationDataForCopyOrder(currentOrderNo,serviceList,noOfCopy);
				if(data.anyException==true){
					alert("Some Error has ocurred.");
					return;
				}
				else if(data.copyOrderAlreadyInProgress==1){
					alert("Copy Order Already In Progress. Pls wait till earlier request is processed.");
					return;
				}else if(data.minsForCopyOrder>2){
					jsonrpc.processes.copyServicesToTheOrder(cb_CopyOrderResult,currentOrderNo,enteredOrderNo,serviceList,serviceProductList,poList,licenseCompanyList,storeList,noOfCopy,gb_roleID,sessionId);
					//hide table
					//show message window
					
					hideUnhideDefaultForCopyOrderCustomAlert("HIDE");
					
					var id_tab_MsgHeaderOb=document.getElementById("id_tab_MsgHeader");
					var tblRow=id_tab_MsgHeaderOb.insertRow();
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.width="50%"
					tblcol.vAlign="top";
					str=getCopyWaitMessage(data.minsForCopyOrder,'COPY_ORDER');
					CellContents = str;				
					tblcol.innerHTML = CellContents;	
					
					return;
				}
				//[003] End
		   	 	var status = jsonrpc.processes.copyServicesToTheOrder(currentOrderNo,enteredOrderNo,serviceList,serviceProductList,poList,licenseCompanyList,storeList,noOfCopy,gb_roleID,sessionId);
		   	//[TRNG22032013031] --start  	
		    }
    		catch(e)
    		{	
    			alert("exception :  "+ e);
    		}
    		//[003] Start
    		process_CopyOrderResult(status)
    			/*alert(status);
    			var flag=5;
    			
    			//PAGING-SERVICE-LINE-14-10-2012
    			//callerWindowObj.countService=1;
    			//callerWindowObj.drawTable();    			
    			callerWindowObj.FirstPage('SERVICENO','SERVICELINETABLE');
				window.close();*/
			//[003] End
			
}
//[003] Start
function getCopyWaitMessage(mins,screen){
	if(screen=='COPY_ORDER'){
		//return "Please wait....This may take atleast "+10*Math.round(mins/10)+"mins time . You can work on other links.";
		return "Please wait.... This may take approx. "+Math.round(mins)+" mins. You may close the window and reopen the order after this time.";
	}
}

function hideUnhideDefaultForCopyOrderCustomAlert(hideStatus){
	if(hideStatus=="HIDE"){
		var searchFormOb=document.getElementById("searchForm");
		searchFormOb.style.display='none';
		var id_tab_MsgHeaderOb=document.getElementById("id_tab_MsgHeader");
		id_tab_MsgHeaderOb.style.display='block';
	}else if(hideStatus=="UNHIDE"){
		var searchFormOb=document.getElementById("searchForm");
		searchFormOb.style.display='block';		
		var id_tab_MsgHeaderOb=document.getElementById("id_tab_MsgHeader");
		id_tab_MsgHeaderOb.style.display='none';
	}
}


function cb_CopyOrderResult(result, exception){
	if(exception){ 
		alert("exception :  "+exception.message); 
	}
	else{
		hideUnhideDefaultForCopyOrderCustomAlert("UNHIDE");
		process_CopyOrderResult(result);
	}
}

function process_CopyOrderResult(result){

	alert(result);
	var flag=5;
	
	//PAGING-SERVICE-LINE-14-10-2012
	//callerWindowObj.countService=1;
	//callerWindowObj.drawTable();    			
	callerWindowObj.FirstPage('SERVICENO','SERVICELINETABLE');
	window.close();
	
}
//[003] End
/*
	Function to display or hide the table having Line Item level details
*/
function show(tbl,btn)  
{
	var currentOrderNo=callerWindowObj.document.getElementById('poNumber').value;
	var enteredOrderNo=document.getElementById('hdnEnteredOrderNo').value;
	if(currentOrderNo==enteredOrderNo)
	{
		return false;
	}
	if (btn.checked)
	{
		document.all.item(tbl).style.display="block";
	}
	else
	{
		document.all.item(tbl).style.display="None";
	}
}

function getserviceForThisOrder(orderNo) 
{
		
		var myForm=document.getElementById('searchForm');
		pageRecords=PAGE_SIZE_COPY_ORDER;
		document.getElementById('txtPageNumber').value= pageNumber;
	   	sync();
				var jsData = 
				{
				orderNo:orderNo,
				startIndex:startRecordId,
				endIndex:endRecordId,
				sortBycolumn:sortByColumn,
				sortByOrder:sortByOrder,
				pageRecords:pageRecords
				};
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		try
		{
		   	serviceList = jsonrpc.processes.getServicesForTheOrder(jsData);
		}
   		catch(e)
   		{	
   			alert("exception :  "+ e);
		}
		var iTotalLength=serviceList.list.length;	
		if(iTotalLength !=0)
		{
			iNoOfPages = serviceList.list[0].maxPageNo;
		}
		
		else
		{     
			 iNoOfPages=1;
		}
		document.getElementById('txtMaxPageNo').value=iNoOfPages;	
		
	   	for (i = 0; i <  serviceList.list.length; i++)
	 	{
			var tblRow=document.getElementById('tabLogicalType').insertRow();
		
			var count=0;
			count=count+i;
			
			var tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str="<input name='chk' id='chk"+count+"' type='checkbox' value='"+serviceList.list[i].serviceId+"' onclick=show('tblServiceProductSummary"+i+"',this) />";
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			if(serviceList.list[i].isDummy==0)
				str='-';
			else
				str='Yes';
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=serviceList.list[i].serviceId;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=serviceList.list[i].serviceTypeName
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			//[TRNG22032013031] start
			var tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str="<input name='noOfCopy' id='noOfCopy"+count+"' type='text' value='0' maxlength='2'  onblur='if(checkdigitsDefaultFilled(this,0)==false){return false};'  style='width:40px;float: right' />";
			CellContents = str;
			tblcol.innerHTML = CellContents;
			//[TRNG22032013031] end 	
			
			document.getElementById('hdnCounterLength').value=count;
			
			var currentOrderNo=callerWindowObj.document.getElementById('poNumber').value;
			var enteredOrderNo=document.getElementById('hdnEnteredOrderNo').value;
			if(currentOrderNo!=enteredOrderNo)
			{
				drawPOTable(i,serviceList.list[i].serviceId);
			}
			   	
		}
		
		var pagenumber=document.getElementById('txtPageNumber').value;
		var MaxPageNo=document.getElementById('txtMaxPageNo').value;
		if(pagenumber && MaxPageNo==1)
		 {
		
		     document.getElementById('first').disabled=true;
		     document.getElementById('prev').disabled=true;
		     document.getElementById('last').disabled=true;
		     document.getElementById('next').disabled=true;
		
		
		 }
		 
		 if(pagenumber==1 && MaxPageNo!=1)
		 {
		     document.getElementById('first').disabled=true;
		     document.getElementById('prev').disabled=true;
		     document.getElementById('last').disabled=false;
		     document.getElementById('next').disabled=false;
		 
		 }
		 
		  if(pagenumber==MaxPageNo && pagenumber!=1)
		  
		 {
		     document.getElementById('last').disabled=true;
		     document.getElementById('next').disabled=true;
		     document.getElementById('first').disabled=false;
		     document.getElementById('prev').disabled=false;
		 
		 }
		 
		 if(pagenumber!=MaxPageNo && pagenumber!=1)
		  
		 {
		     document.getElementById('last').disabled=false;
		     document.getElementById('next').disabled=false;
		     document.getElementById('first').disabled=false;
		     document.getElementById('prev').disabled=false;
		 
		 }
		
 		if(serviceList.list.length == 0)
		{
			var tblRow=document.getElementById('tabLogicalType').insertRow();
    		tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			tblcol.colSpan = 7;
			str='NO RECORD FOUND';
			CellContents = str;
			tblcol.innerHTML = CellContents;
		}
		else
		{
			var tblRow=document.getElementById('tabOk').insertRow();
    		tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			tblcol.colSpan = 7;
			str="<div class='searchBg' align='center' onclick='copyServices()' ><a href='#'>OK</a></div>";
			CellContents = str;
			tblcol.innerHTML = CellContents;
		}	
     		
}

function drawPOTable(i,serviceId)
{
	var tblRow=document.getElementById('tabLogicalType').insertRow();
	
	tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	width="100px"; 
	colspan="10";
	str="";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.colSpan=3;
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="<table width='100%' class='gray' border='1' align='center' cellspacing='0' cellpadding='0' id='tblServiceProductSummary"+i+"' style='display: none;'></table>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	makeHeader(i);
	
	var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	try
	{
    	var productList = jsonrpc.processes.populateProductList(serviceId);
    	for (var j = 0; j <  productList.list.length; j++)
	 	{
			var tblRow=document.getElementById('tblServiceProductSummary'+i).insertRow();
			var serviceProductID = productList.list[j].serviceProductID;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=productList.list[j].serviceProductID + ' - ' + productList.list[j].serviceName;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str="<select name='po' id='po"+serviceProductID+"' width='90%' class='dropdownMargin' onchange='fillLicenseCompany("+serviceProductID+")' ><option value='0'>Select Valid PO</option> </select> ";
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			fillPO(serviceProductID);
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str="<select name='lcompany' id='lcompany"+serviceProductID+"' width='90%' class='dropdownMargin' onchange='fillStore("+serviceProductID+")' ><option value='0'>Select License Company</option> </select>";
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			fillLicenseCompany(serviceProductID);
			
			//lawkush Start
			
			if(document.getElementById('po'+serviceProductID).value!=0)
			    {
			    	document.getElementById('lcompany'+serviceProductID).value=productList.list[j].licCompanyID;
			    }
		   
			//lawkush End
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str="<select name='store' id='store"+serviceProductID+"' width='90%' class='dropdownMargin' ><option value='0'>Select Store</option> </select>";
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			fillStore(serviceProductID);
			//Lawkush Start	
			if(document.getElementById('po'+serviceProductID).value!=0)
	    		{
					document.getElementById('store'+serviceProductID).value=productList.list[j].storeID;
				}
			//lawkush End	
			if(productList.list[j].isServiceActive==0)
			{
			   
				document.getElementById('store'+serviceProductID).disabled="disable";
				document.getElementById('store'+serviceProductID).selectedIndex=0;
			}
		}
	}
	catch(e)
	{	
		alert("exception :  "+ e);
	}
	
}

function makeHeader(i)
{
	var tblRow=document.getElementById('tblServiceProductSummary'+i).insertRow();
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="LineItem Id";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="Valid Po";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="LicenseCompany";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	str="Store";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
}

function fillPO(serviceProductID) 
{
 		var poCombo = document.getElementById('po'+serviceProductID);
 		var currentOrderNo=callerWindowObj.document.getElementById('poNumber').value;
		var enteredOrderNo=document.getElementById('hdnEnteredOrderNo').value;
		var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
        poComboList = jsonrpc.processes.populatePODetailForCopyOrder(currentOrderNo,enteredOrderNo,serviceProductID);
        
        /*for(k=poCombo.options.length-1;k>=1;k--)
		{
			poCombo.remove(k);
		}*/
        
		for(z=0;z<poComboList.list.length;z++)
	    {
	    	
	    	var option = document.createElement("option");
	   		option.text = poComboList.list[z].podetailID;
			option.value = poComboList.list[z].podetailID;
			try 
			{
				poCombo.add(option, null); //Standard
			}
			catch(error) 
			{
				poCombo.add(option); // IE only
			}
			//[002]
//			if(poComboList.list[z].isDefaultPO==1)//TTS
	//		{
				document.getElementById('po'+serviceProductID).selectedIndex=z+1;
		//	}//TTS
		//[002]
	    }
} 

function fillLicenseCompany(serviceProductID) 
{
 		var lcCombo = document.getElementById('lcompany'+serviceProductID);
 		var poId = document.getElementById('po'+serviceProductID).value;
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
        lcComboList = jsonrpc.processes.populateLicenseCompanyForCopyOrder(serviceProductID,poId);
        
        if(lcCombo.options.length>1)
        {
        	for(k=lcCombo.options.length-1;k>=1;k--)
			{
				lcCombo.remove(k);
			}
        }
		for(z=0;z<lcComboList.list.length;z++)
	    {
	    	
	    	var option = document.createElement("option");
	   		option.text = lcComboList.list[z].licCompanyName;
			option.value = lcComboList.list[z].licCompanyID;
			try 
			{
				lcCombo.add(option, null); //Standard
			}
			catch(error) 
			{
				lcCombo.add(option); // IE only
			}
	    }
	    if(document.getElementById('po'+serviceProductID).value!=0)
	    {
	    	document.getElementById('lcompany'+serviceProductID).selectedIndex=1;
	    }
	    if(lcComboList.list.length==0)
	    {
	    	document.getElementById('lcompany'+serviceProductID).selectedIndex=0;
	    }
} 

function fillStore(serviceProductID) 
{
 		var storeCombo = document.getElementById('store'+serviceProductID);
 		var licComp = document.getElementById('lcompany'+serviceProductID).value;
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
        storeComboList = jsonrpc.processes.populateStoreForCopyOrder(serviceProductID,licComp);
        
        for(k=storeCombo.options.length-1;k>=1;k--)
		{
			storeCombo.remove(k);
		}
        
		for(z=0;z<storeComboList.list.length;z++)
	    {
	    	
	    	var option = document.createElement("option");
	   		option.text = storeComboList.list[z].storeName;
			option.value = storeComboList.list[z].storeID;
			try 
			{
				storeCombo.add(option, null); //Standard
			}
			catch(error) 
			{
				storeCombo.add(option); // IE only
			}
	    }
	    /*if(storeComboList.list.length==0)
	    {
	    	document.getElementById('store'+serviceProductID).readOnly="true";
	    }
	    else */if(document.getElementById('po'+serviceProductID).value!=0)
	    {
	    	document.getElementById('store'+serviceProductID).selectedIndex=1;
	    }
	    if(storeComboList.list.length==0)
	    {
	    	document.getElementById('store'+serviceProductID).selectedIndex=0;
	    }
} 


//lawkush Start

function checkdigitsDefaultFilled(obj,flag)
{

	var x = obj.value
	var anum=/(^\d+$)|(^\d+\d+$)/
	//var copyOrderLimit = 
	<% String copy_order_max=Utility.getAppConfigValue("COPY_ORDER_MAX");%>;
	var copyOrderLimit = <%= copy_order_max%>;
	if (anum.test(x)){
	
				//Start [001]
		if(flag==1)
		{
				if((parseInt(obj.value,10) > copyOrderLimit)||(parseInt(obj.value,10)<1)){
					alert("Please enter no of copy between 1 and " + copyOrderLimit);
					testresult=false
					obj.value = 0;
					obj.focus();
				}
				else{
					testresult=true
				}
				//End [001]	
		}
		else
		{
				if((parseInt(obj.value,10) > copyOrderLimit)||(parseInt(obj.value,10)<0)){
					alert("Please enter no of copy between 1 and " + copyOrderLimit);
					testresult=false
					obj.value = 0;
					obj.focus();
				}
				else{
					testresult=true
				}
		
		}
		
	}	
	else if(x.match(/^-\d+$/))//negative number
	{	
		alert("Negative Number Not Allowed!");
		testresult=false
		obj.value = 0;
		obj.focus();
          
	}
	else if(x.match(/^[0-9]*(\.[0-9]*)+$/))//Decimal number
	{	
		alert("Decimal Number Not Allowed!");
		testresult=false
		obj.value = 0;
		obj.focus();
          
	}	
	else if(isEmpty(x)){
		alert("Empty value Not Allowed!");
		testresult=false
		obj.value = 0;
		obj.focus();
	}
	else
	{
		alert("Please Input only Natural Number!");
		testresult=false
		obj.value = 1;
		obj.focus();
	}
	return testresult;
}

//lawkush End


path='<%=request.getContextPath()%>';
</script>

<body >
	<table style="display: none;" id="id_tab_MsgHeader">
		<tr><td><div border="1" class="head"> <span>Copy Order</span> </div></td></tr>
	</table>
	<html:form action="/NewOrderAction" styleId="searchForm" method="post">
		<bean:define id="formBean" name="newOrderBean"></bean:define>
		<html:hidden property="pagingSorting.sortByColumn"/>
		<html:hidden property="pagingSorting.sortByOrder"/>
		<html:hidden property="pagingSorting.pageNumber"/>
		<input type="hidden" name="method" />
		<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
			

		</table>	
			
		<div border="1" class="head"> <span>Search Order</span> </div>
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
			<legend><b>Search</b></legend>
				<table border="0"  align="center" style="margin-top:7px">
					<tr>
					<td width="90px"/>
						<td align="right" style="font-size:9px"> Order No:</td>
						<td align="right" style="font-size:9px">
						<input type="text"  name="orderNo" id="orderNo" maxlength="10" style="width:80px;float: left" align="left" class="inputBorder1" value="" 
							onblur="if( trim(this.value).length>0){ return checkdigits(this)};" 
							onkeydown="if (event.keyCode == 13)  return getOrders();"/>
							<!-- <input type="button" width="20" name = "..." onclick="getOrders()" > -->
					<!-- TRNG22032013031 replace from button
					 	<div class="searchBg1" onclick="getOrders()" ><a href="#">..</a></div>
					   -->
							<html:button property="" value="Get Details" onclick="javascript:getOrders();"/>
     				<!-- [TRNG22032013031] end -->		
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">Account No:</td>
						<td nowrap>				
								<input type="text" name="accountID" id="accountID" width="75px" class="inputBorder1" value="" readonly="true" />
						</td>
						
						
						<td width="10px"/>
						<td align="right" style="font-size:9px;">Account Mgr:</td>
						<td nowrap>
						<input type="text" name="accountManager" id="accountManager" width="75px" class="inputBorder1" value="" readonly="true" />
						</td>
						<td width="50px"/>
					</tr>
					<tr>
					<td width="90px"/>
						<td align="right" style="font-size:9px">Project Mgr :</td>
					<!-- [TRNG22032013031] start -->	
						<td align="left" style="font-size:9px">
					<!-- [TRNG22032013031] end -->	
						<input type="text" name="projectManager" id="projectManager" width="75px" class="inputBorder1" value="" readonly="true" />
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">Quote No:</td>
						<td nowrap>
						<input type="text" name="quoteNo" id="quoteNo"width="75px" class="inputBorder1" value="" readonly="true" />
						</td>
						<td width="10px"/>
						<td align="right" style="font-size:9px;">Currency:</td>
						<td nowrap>
						<input type="text" name="currencyCode" id="currencyCode" width="75px" class="inputBorder1" value="" readonly="true" />
						</td>
						<td width="50px"/>
					</tr>
					<!-- <tr>
					<td width="100px" colspan="10" align="right">
						<input type="button" name="Populate Service" onclick="populateService()" />
					</td>
					</tr> -->
				</table>
		</fieldset>
		
		<fieldset class="border1">
			<legend> <b>Service List</b> </legend>
			<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
				<tr>
					<td align="center"><a href="#" id= "first" onclick="FirstPage('SERVICEID','SELECTCOPYORDER')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('SERVICEID','SELECTCOPYORDER')">Next</a></td>
					<td align="center">
					<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
						<input type="text" id="txtMaxPageNo"  class="inputBorder2" readonly="true" size="2"/>Pages
					</td>
					<td align="center"><a href="#" id="prev" onclick="PrevPage('SERVICEID','SELECTCOPYORDER')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('SERVICEID','SELECTCOPYORDER')">Last</a></td>
				</tr>
			</table>	
			<table width="100%"  border="1" id="tabLogicalType" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   
				<tr class="grayBg">
				    <td width="" align="center">Select</td>
				    <td >Dummy</td>
					<td >serviceId</td>
					<td >serviceTypeName</td>
					<td >Number of copy</td>
					
				</tr>
			</table>
			<table width="100%"  border="0" id="tabOk" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   	<tr class="grayBg">
					<!-- <td>
					<div class="searchBg" onclick="copyServices()" ><a href="#">OK</a></div>
					</td>
					<td>
					<div class="searchBg" onclick="cancelSelection()" ><a href="#">Cancel</a></div>
					</td> -->
				</tr>
			</table>
		</fieldset>
		<input type="hidden" id = "hdnCurrentOrderNo" name="hdnCurrentOrderNo">
		<input type="hidden" name="hdnCounterLength" id="hdnCounterLength" />
		<input type="hidden" name="hdnEnteredOrderNo" id="hdnEnteredOrderNo" />
		<input type="hidden" name="hdnEntityid" id="hdnEntityid" />
		
		
		
	</html:form>
</body>

<script type="text/javascript">
DrawTable('SERVICEID','SELECTCOPYORDER')
</script>

</html>
