<!--Tag Name Resource Name  Date		CSR No			Description -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@page import="com.ibm.ioes.beans.DefaultBean"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="java.util.HashMap"%><html:html>
<head>
<title>LSI Cancellation</title>
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
<script type="text/javascript" src="${pageContext.request.contextPath}/js/fxUtility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/enableDisableOrderEntryUtility.js"></script>
<!-- 001 START --><script type="text/javascript" src="${pageContext.request.contextPath}/js/BillingTrigger.js"></script><!-- 001 END -->
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
path='<%=request.getContextPath()%>';
var sessionId='<%=request.getSession().getId() %>';
var myList=[]; 
//var noOfLSICancelled;
//var javaList=[]; 

<%
	HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
	HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
	UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
	%>
	var PAGE_SIZE_CHARGES=<%=objUserDto.getPageSizeLSICancellation()%>;
			

<%--  
var tops = document.getElementsByTagName('input[type="checkbox"]');
<%

for (var i=0, len=tops.length; i<len; i++) {
            tops[i].onclick = updateTotal;
        
    }
%> --%>


function goToHome()
{
	document.forms[0].action="<%=request.getContextPath()%>/defaultAction.do?method=goToHomeAfterClosing";
	showLayer();
	document.forms[0].submit();
}

function fnViewOrder(orderNo)
{
		var modeValue="viewMode";
    	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
		window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
}


/* function Map() {
		    if (!(this instanceof Map)) {
		        return new Map();
		    }
       } */


       /*
       	return false :
       			true :
       */
function validateAndMaintainListOnSelectionUtility(){

	var myForm=document.getElementById('searchForm');
	var checkedCounter=0;
	var scenario=document.getElementById('searchLSIScenarioId').value;
 	var serviceReasonList;
	var index=0;
	
	
/* //this loop is for validation only : we r nt going to maintain our global list in this loop bcoz we can maintain list 
//only after succesfull validation otherwise we have to delete values from globallist if suppose last cycle of loop failed to validate
if(document.forms[0].chkSelectService)
    {
  for(i=0;i<document.forms[0].chkSelectService.length;i++)
      {
	      if(myForm.chkSelectService[i].checked==true)
	     		{
	     			var serviceval=myForm.chkSelectService[i].value;
	   				checkedCounter++;
	   				var idval=document.getElementById('cancelReasonDD'+i).value;
	    			var remarksval=trim(document.getElementById('cancelRemarks'+i).value);
	     			
	    			if(idval=='0')
	     			{
	     				alert("Please select cancellation reason");
	     				return false;
	     			}					
	     			if(remarksval.length>500){
	     				alert("please input less than 500 characters in cancellation remarks");
	     				return false;
	     			}
	     			if(remarksval.length>0){
	     				
						if(!ValidateRemarks(document.getElementById('cancelRemarks'+i),path,'Cancellation Remarks'))
	     				{
	     					return false;
	     				}
	     			}
	     	     }
			}
		}	 */

	if(document.forms[0].chkSelectService)
    {
    	var myTempList=[];
    	var chkLength=document.forms[0].chkSelectService.length;
    	if(chkLength==undefined) /* While after search for one record then check length is undefined */
 		{    			
	   			if(document.forms[0].chkSelectService.checked==true)
	   			{		
	   				var serviceval=document.forms[0].chkSelectService.value;
	   				checkedCounter++;
	   				/* var idval=document.forms[0].cancelReasonDD.value; */
	   				var idval=document.getElementById('cancelReasonDD'+"0").value;
	   				//var reasonName=document.forms[0].cancelReasonDD;	   					
	    			var remarksval=trim(document.forms[0].cancelRemarks.value);
	    			var lSiNo=document.forms[0].LSI.value;
	    				
	    			if(idval=='0')
	     			{	
	     				alert("Please select cancellation reason");
	     				return false;
	     			}					
	     			if(remarksval.length>500){
	     				alert("please input less than 500 characters in cancellation remarks");
	     				return false;
	     			}
	     			if(remarksval.length>0){
	     				
						if(!ValidateRemarks(document.forms[0].cancelRemarks,path,'Cancellation Remarks'))
	     				{
	     					return false;
	     				}
	     			}
	     			var ob={"javaClass": "com.ibm.ioes.forms.LSICancellationDto",
	   						searchServiceNo:serviceval,
	   						cancellationReasonId:idval,
	   						cancellationRemarks:remarksval,
	   						searchLSI:lSiNo
	   						};
	   				myList[serviceval]=ob;
	   				//alert(serviceval+' added to global list through utility')
	   			}else{        
	   				 //deleting entry from list if have any, of unchecked check boxes
	   				 
	   				var serviceval=document.forms[0].chkSelectService.value;
	    				
					  if (serviceval in myList) {
						  delete myList[serviceval];	
						  //alert('obj with key:'+serviceval+'deleted from myList')
					  } 
	   			}
	     	}	     
		else
		{			
			for(i=0;i<document.forms[0].chkSelectService.length;i++)
			{		
			
	     		if(myForm.chkSelectService[i].checked==true)
	     		{	
	     			//var myTempList=[];
	     			var serviceval=myForm.chkSelectService[i].value;
	   				checkedCounter++;
	   				var idval=document.getElementById('cancelReasonDD'+i).value;
	    			var remarksval=trim(document.getElementById('cancelRemarks'+i).value);
	     			var lSiNo=document.getElementById('LSI'+i).value;
	     			var reasonName=document.getElementById('cancelReasonDD'+i);
	    			if(idval=='0')
	     			{
	     				alert("Please select cancellation reason");
	     				myTempList=[];
	     				return false;
	     			}					
	     			if(remarksval.length>500){
	     				alert("please input less than 500 characters in cancellation remarks");
	     				myTempList=[];
	     				return false;
	     			}
	     			if(remarksval.length>0){
	     				
						if(!ValidateRemarks(document.getElementById('cancelRemarks'+i),path,'Cancellation Remarks'))
	     				{
	     					myTempList=[];
	     					return false;
	     				}
	     			} 
	     			var ob={"javaClass": "com.ibm.ioes.forms.LSICancellationDto",
	   						searchServiceNo:serviceval,
	   						cancellationReasonId:idval,
	   						cancellationRemarks:remarksval,
	   						searchLSI:lSiNo
	   						};
	   				myTempList[serviceval]=ob;
	   				//myList[serviceval].searchServiceNo;
	   				//alert(serviceval+' added to global list through utility')
	   				index++;
	     	     }else{        
	   				 //deleting entry from list if have any, of unchecked check boxes
	   				
	     			var serviceval=myForm.chkSelectService[i].value;
	     			if (serviceval in myList) {
						   delete myList[serviceval];	
						   //alert('obj with key:'+serviceval+'deleted from myList')
					    } 
	   			}
	     	}
			for(var key in myTempList) {
			    if(myTempList.hasOwnProperty(key)) {
			    	myList[key]=myTempList[key];
			        //alert(myList[key]);// *= 2;
			    }
			}		
		}
		
		//myTempList[]=myList[];
		//alert('myTempList.lengthmyTempList.lengthmyTempList.lengthmyTempList.length:'+myTempList.length)
		
		/* 
		if(myTempList.length !=0) 
			myList.concat(myTempList);
		 */	//myList = myTempList.slice();    //This copys the myTempList array from the starting position i.e. 0 through the end of the array into myList array
    }
}


function actionCheckbox(element,serviceNoAtt,sNo)
{		
	
		var jT = $(this);
 		var serviceNo=serviceNoAtt.value;
		var cancelReasonId=document.getElementById('cancelReasonDD'+(sNo-1)).value;
		var remarks=document.getElementById('cancelRemarks'+(sNo-1)).value;
		//alert('serviceNoserviceNo'+serviceNo)
/* 		delete Object.getPrototypeOf(map);
 */		  if (!(element.checked)){
		   	// alert('del from list')    // change on checked check box  i.e. unselected
			 
							 if (serviceNo in map) {
									delete map[serviceNo];	
									//alert('obj with key:'+serviceNo+'deleted from map')
								} else {
								}
			 
			 
		  }else {
		    	//alert('add on list')	  // change on unchecked check box i.e. selected
		    	//map.set("bar", "foo");
		    	map[serviceNo]={
	   						searchServiceNo:serviceNo,
	   						cancellationReasonId:cancelReasonId,
	   						cancellationRemarks:remarks
	   				   };

					//alert('obj with key:'+serviceNo+' added in map')
					//alert('map[serviceNo]map[serviceNo]map[serviceNo]map[serviceNo]'+map[serviceNo])	
					
					/* $H(map).each(function(pair){
					  alert(pair.key);
					  alert(pair.value);
					});	 */
	
				//	var keys = Object.keys(ob); 
					 // alert(keys + " -> " + map[keys]);	
				//var obj2 = map.get(serviceNo)	  
		  }
				//alert('Object.keys({}).length'+map.size)
				
		// or remove it
	    //	delete map[key1];
		// or determine whether a key exists
}

function searchSubmit()
{
	if(document.getElementById('searchLSIScenario').value =='0')
	{
		alert('Please select LSI scenario.');
		return false;
	}
	myList=[]; 
	if(document.forms[0].chkSelectService){
	var chkLength1=document.forms[0].chkSelectService.length; 
	if(chkLength1==undefined)
         {
	        if(document.forms[0].chkSelectService.checked==true)
	         {
	           document.forms[0].chkSelectService.checked=false;
	         }
         }
      else
      {    
	         for (i =0; i<chkLength1; i++)
			   { 
			     if(document.forms[0].chkSelectService[i].checked==true)
					 {
					     document.forms[0].chkSelectService[i].checked=false ;
					}      
		      }
	}
	 }
	
		FirstPageLsiCancellation('SERVICEID','LSICANCELLATION')
	}
	<%-- var myForm=document.getElementById('searchForm');
	var jsData =			
			    {   
			        searchCRMOrder:myForm.searchCRMOrder.value,
			        searchAccountNo:myForm.searchAccountNo.value,
				    searchAccountName:myForm.searchAccountName.value,
					searchLSI:myForm.searchLSI.value,			    
				    searchFromOrderDate:myForm.searchFromOrd_Date.value,
				    searchToOrderDate:myForm.searchToOrd_Date.value,
				    searchServiceNo:myForm.searchServiceNo.value,
				    searchLSIScenario:document.getElementById('searchLSIScenarioId').value
				};

	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";
	if(isBlankForm()==false){
		return false;
	}
	myForm.action="<%=request.getContextPath()%>/defaultDraftNewOrdAction.do?method=goToLSICancellation";
	//showLayer();
	//myForm.submit();
	getAndShowTableDataOnSearchbutton(jsData);
	//showCheckedServices()
 --%>
<%-- 
function pagingSubmit(val)
{
	var myform=document.getElementById('searchForm');
	var jsData =			
			    {   
			        searchCRMOrder:myForm.searchCRMOrder.value,
			        searchAccountNo:myForm.searchAccountNo.value,
				    searchAccountName:myForm.searchAccountName.value,
					searchLSI:myForm.searchLSI.value,			    
				    searchFromOrderDate:myForm.searchFromOrd_Date.value,
				    searchToOrderDate:myForm.searchToOrd_Date.value,
				    searchServiceNo:myForm.searchServiceNo.value
				};
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.action="<%=request.getContextPath()%>/defaultDraftNewOrdAction.do?method=goToLSICancellation";
	//showLayer();
	//myform.submit();
	
	getAndShowTableData(jsData);
	
	//showCheckedServices()
	
}
 --%>
function getAndShowTableData(){
	var myForm=document.getElementById('searchForm');
	var jsData =			
			    {   
			        searchCRMOrder:myForm.searchCRMOrder.value,
			        searchAccountNo:myForm.searchAccountNo.value,
				    searchAccountName:myForm.searchAccountName.value,
					searchLSI:myForm.searchLSI.value,			    
				    /* searchFromOrderDate:myForm.searchFromOrd_Date.value, */
				    /* searchToOrderDate:myForm.searchToOrd_Date.value, */
				    searchServiceNo:myForm.searchServiceNo.value,
				    searchLSIScenario:document.getElementById('searchLSIScenarioId').value
				};
				
				document.getElementById('txtPageNumber').value= pageNumber;
				pageRecords=<%=objUserDto.getPageSizeLSICancellation()%>;
				sync();

		var jsData_Paging =			
	    {
		    startIndex:startRecordId,
			endIndex:endRecordId,
     		sortBycolumn:sortByColumn,
			pageRecords:pageRecords,
			sortByOrder:sortByOrder
		};
		
	var pagingRequired=1;			
	var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var LsiCancellationTableResult = jsonrpc.processes.GetLsiCancellationTable(jsData,jsData_Paging,pagingRequired);

	if(LsiCancellationTableResult.statusCode=='UNEXCEPTED_ERROR'){
		alert(LsiCancellationTableResult.tableData);
		return;
	}
	
	if(LsiCancellationTableResult.statusCode=='OK'){
		document.getElementById("contentscroll").innerHTML=LsiCancellationTableResult.tableData;	
		//iTotalLength=document.forms[0].chkSelectService.length;	
		iTotalLength=document.getElementsByName('chkSelectService').length;
		if(iTotalLength !=0)
		{
			iNoOfPages = document.forms[0].hiddenMaxPageno.value;
		}
        else
		{     
	        iNoOfPages=1;
		}
		 document.getElementById('txtMaxPageNo').value=iNoOfPages;	
	     var pagenumber=document.getElementById('txtPageNumber').value;
		 var MaxPageNo=document.getElementById('txtMaxPageNo').value;

	   if(pageNumber==1 && MaxPageNo==1)
		 {
		     document.getElementById('first').disabled=true;
		     document.getElementById('prev').disabled=true;
		     document.getElementById('last').disabled=true;
		     document.getElementById('next').disabled=true;
		 }
		 
		 if(pageNumber==1 && MaxPageNo!=1)
		 {
		     document.getElementById('first').disabled=true;
		     document.getElementById('prev').disabled=true;
		     document.getElementById('last').disabled=false;
		     document.getElementById('next').disabled=false;
		 }
		 
		if(pageNumber==MaxPageNo && pageNumber != 1)
		 {
		     document.getElementById('last').disabled=true;
		     document.getElementById('next').disabled=true;
		     document.getElementById('first').disabled=false;
		     document.getElementById('prev').disabled=false;
		 
		 }
		 if(pageNumber!=MaxPageNo && pageNumber != 1)
		 {
		     document.getElementById('last').disabled=false;
		     document.getElementById('next').disabled=false;
		     document.getElementById('first').disabled=false;
		     document.getElementById('prev').disabled=false;
		 }
	}	 
	 //showCheckedServices()	 
}

function NextPageLsiCancellation(var_sortByColumn,var_pageName)
{	

		/* var hiddenCancellationReason=document.getElementById('hiddenCancellationReason').value
		var hiddenRemarks=document.getElementById('hiddenRemarks').value */
    //validateAndMaintainListOnSelectionUtility()
    if ( validateAndMaintainListOnSelectionUtility()!=false ) {
    
    	NextPage(var_sortByColumn,var_pageName)
    	showCheckedServices()
	}
	
}
//end:NextPage()

/*function	:LastPage()
purpose		:to navigate last page
*/
function LastPageLsiCancellation(var_sortByColumn,var_pageName)
{	
   // validateAndMaintainListOnSelectionUtility()
    if ( validateAndMaintainListOnSelectionUtility()!=false ) {
		 LastPage(var_sortByColumn,var_pageName)
		showCheckedServices()
    }
}
//end:LastPage()

/*function	:FirstPage()
Purpose		:To navigate first page
*/
function FirstPageLsiCancellation(var_sortByColumn,var_pageName)
{		
	    //validateAndMaintainListOnSelectionUtility()
	    if ( validateAndMaintainListOnSelectionUtility()!=false ) {
			FirstPage(var_sortByColumn,var_pageName)
			showCheckedServices()
	     }
}
//end:FirstPage()

/*function	:PrevPage()
Purpose		:To navigate previous page
*/
function PrevPageLsiCancellation(var_sortByColumn,var_pageName)
{	
	//validateAndMaintainListOnSelectionUtility()
    if ( validateAndMaintainListOnSelectionUtility()!=false ) {
		PrevPage(var_sortByColumn,var_pageName)
		showCheckedServices()
    }
}


function getAndShowTableDataOnSearchbutton(formOb){
//	PagingDto objPagingDto,int pagingRequired,String sessionId
				document.getElementById('txtPageNumber').value= pageNumber;
				pageRecords=<%=objUserDto.getPageSizeLSICancellation()%>;
				sync();

		var jsData_Paging =			
	    {
		    startIndex:startRecordId,
			endIndex:endRecordId,
     		sortBycolumn:sortByColumn,
			pageRecords:pageRecords,
			sortByOrder:sortByOrder
		};
		
	var pagingRequired=1;			
	
	var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var LsiCancellationTableResult = jsonrpc.processes.GetLsiCancellationTable(formOb,jsData_Paging,pagingRequired);
	if(LsiCancellationTableResult.statusCode='OK'){
		document.getElementById("contentscroll").innerHTML=LsiCancellationTableResult.tableData;	
	}else if(LsiCancellationTableResult.statusCode='UNEXCEPTED_ERROR'){
		alert(LsiCancellationTableResult.tableData);
	}
}

function showCheckedServices(){

		//IdtableServices
	//	var noofRows=document.getElementById('IdtableServices').
		var noofRows = $('#IdtableServices tr').length;
		var hiddenCancellationReason=document.getElementById('hiddenCancellationReason').value
		var hiddenRemarks=document.getElementById('hiddenRemarks').value
		if(document.forms[0].chkSelectService){
			var chkLength=document.forms[0].chkSelectService.length;
			if(chkLength==undefined) /* While after search for one record then check length is undefined */
				{    			
					 var serviceNo=document.forms[0].chkSelectService.value;
					 if (serviceNo in myList) {
						//make checkbox selected whose value is serviceno	
						//$("#chkSelectService1").prop("checked", true);
						//document.getElementById("#chkSelectService1").checked = true;
						document.forms[0].chkSelectService[0].checked = true;
						document.forms[0].eligibleLSIForCancelList[0].value = myList[serviceNo].cancellationReasonId;
						document.forms[0].cancelRemarks[0].value = myList[serviceNo].cancellationRemarks;
					 }
					 else{
	            	//// adding this condition because if o/p of search criteria returns only single row then document.forms[0].eligibleLSIForCancelList[0] will be undefined			  
						if(document.forms[0].cancelRemarks[0] != 'undefined' && document.forms[0].eligibleLSIForCancelList[0] != 'undefined'){  
							//document.forms[0].eligibleLSIForCancelList[0].value = hiddenCancellationReason;
							document.getElementById('cancelReasonDD'+0).value = hiddenCancellationReason;
							document.getElementById('cancelRemarks'+0).value = hiddenRemarks;
							//document.forms[0].cancelRemarks[0].value = hiddenRemarks;
						}					  
					 } 
										 
				}else{
					for(i=0;i<document.forms[0].chkSelectService.length;i++)
						{				
							var serviceNo=document.forms[0].chkSelectService[i].value;
							if (serviceNo in myList) {
									//make checkbox selected whose value is serviceno	
									//$("#chkSelectService"+i).prop("checked", true);
									//document.getElementById("#chkSelectService"+i+1).checked = true;
								    document.forms[0].chkSelectService[i].checked = true;
									//document.forms[0].cancelReasonDD[i].value = myList[serviceNo].cancellationReasonId;
									document.forms[0].eligibleLSIForCancelList[i].value = myList[serviceNo].cancellationReasonId;
									document.forms[0].cancelRemarks[i].value = myList[serviceNo].cancellationRemarks;
									//alert(serviceNo+' going to be checked')
								}
								else{
									if(hiddenCancellationReason!='' && hiddenRemarks!=''){
						 				document.forms[0].eligibleLSIForCancelList[i].value = hiddenCancellationReason;
										document.getElementById('cancelRemarks'+i).value = hiddenRemarks;
										}
					 				}
						}
				}
		
		}
}

function clearFields()
{	
	var myForm=document.getElementById('searchForm');
	myForm.searchCRMOrder.value='' ; 	
	myForm.searchAccountNo.value='' ; 
	myForm.searchAccountName.value='' ;
	myForm.searchLSI.value='' ;	
	/* myForm.searchFromOrd_Date.value=''; */
	/* myForm.searchToOrd_Date.value=''; */
	myForm.searchServiceNo.value='';		
}

function fnCheckServiceAll()
	{
	  	var myForm=document.getElementById('searchForm');
 		//var str="";
 		//var checkedCounter1=0;
	   	if(document.forms[0].chkSelectService)
	    {
	    	var chkLength=document.forms[0].chkSelectService.length;
	    	if(document.getElementById('chkAllService').checked==true)
			 {
			 	if(chkLength==undefined)
	                {
		              if(document.forms[0].chkSelectService.disabled==false)
			           {
		                    document.forms[0].chkSelectService.checked=true;
		               }
                  	}
          
      			else
      			{    
	         		for (i =0; i<chkLength; i++)
			  		 { 
		     			if(myForm.chkSelectService[i].disabled==false)
					 	{
					        myForm.chkSelectService[i].checked=true ;
						}      
		      		}
				}
			}	

		else
	   	{
	   	  if(chkLength==undefined)
        	{
	           if(document.forms[0].chkSelectService.disabled==false)
		          {
	   				document.forms[0].chkSelectService.checked=false;
	              }
          }
          
      	else
      	{    
         	for (i =0; i<chkLength; i++)
		   	{ 
		     if(myForm.chkSelectService[i].disabled==false)
				 {
				     myForm.chkSelectService[i].checked=false ;
				 }       
		   }
		}
     }
     
   }
   
  else
   {
   	 alert('No Service To Select');
   	 document.getElementById('chkAllService').checked=false;
   	 return false;
   } 
     
}

function isBlankForm()
{
	
	var myForm=document.getElementById('searchForm');
	/* var fromorddate=myForm.searchFromOrd_Date.value; */
	/* var Toorddate=myForm.searchToOrd_Date.value; */
	//var serviceno=myForm.searchServiceNo.value;
	if(	document.getElementById('searchCRMOrder').value == "0")
	{
		alert('please enter value greater than 0 in OrderNo');					
		myForm.searchCRMOrder.value = "";	
		myForm.searchCRMOrder.focus();						
		return false;           
	}
	if(document.getElementById('searchCRMOrder').value.length>0)
	{
			if(checkdigits(document.getElementById('searchCRMOrder'))==false){		
					return false;
				}
	   
	}
	if(	document.getElementById('searchAccountNo').value == "0")
	{
		alert('please enter value greater than 0 in Account Name');					
		myForm.searchAccountNo.value = "";	
		myForm.searchAccountNo.focus();						
		return false;         
	}
	
	if(document.getElementById('searchAccountNo').value.length>0)
	{
			if(checkdigits(document.getElementById('searchAccountNo'))==false){		
					return false;
				}
	   
	}
	
	if(	document.getElementById('searchLSI').value == "0")
	{
		alert('please enter value greater than 0 in LSI');					
		myForm.searchLSI.value = "";	
		myForm.searchLSI.focus();						
		return false;         
	}
	
	
	if(document.getElementById('searchLSI').value.length>0)
	{
			if(checkdigits(document.getElementById('searchLSI'))==false){		
					return false;
				}
	   
	}
	
	if(document.getElementById('searchAccountName').value.length>0)
	{
			if(ValidateTextField((document.getElementById('searchAccountName')),path,'Account Name')==false){		
					return false;
				}
	   
	}
	
	if(	document.getElementById('searchServiceNo').value == "0")
	{
		alert('please enter value greater than 0 in Service No');					
		myForm.searchServiceNo.value = "";	
		myForm.searchServiceNo.focus();						
		return false;         
	}
	
	
	if(document.getElementById('searchServiceNo').value.length>0)
	{
			if(checkdigits(document.getElementById('searchServiceNo'))==false){		
					return false;
				}
	   
	}
			
	if((fromorddate == null || trim(fromorddate)== "" ) && (Toorddate != null && trim(Toorddate) != "" ))
		{
			alert("Please enter From Order Date ");
			return false;
		}
	else if((Toorddate == null || trim(Toorddate)== "" ) && (fromorddate != null && trim(fromorddate) != "" ))
		{
			alert("Please enter To Order Date ");
			return false;
		}
		
	if(dateCompare(fromorddate,Toorddate)==1)
			{			
				alert("From Order Date cannot be greater than To Order Date");
				return false;
			}	
	 if(document.getElementById('searchLSIScenarioId').value == ""){
			alert("please select LSI Scenario first");
			return false;
		}

	return true;	 

}


function fnSubmit(){
		var javaList=[];
		document.getElementById('flagForCancelLsi').value=0;
		var scenario=document.getElementById('searchLSIScenarioId').value;
		
		if(validateAndMaintainListOnSelectionUtility()!=false){
		
			var gb_path="<%=request.getContextPath()%>";
			var path = gb_path +"/defaultDraftNewOrdAction.do?method=getLsiConfirmation&javaList="+javaList+"&d="+Date();	
			var i=0;
	    	for(var key in myList) {
			    if(myList.hasOwnProperty(key)) {
			    	javaList[i]=myList[key];
			    	i=i+1;
			        //alert(myList[key]);// *= 2;
			    }
			}	
				
			if(javaList.length>0){
				noOfLSICancelled=javaList.length;
				//alert(noOfLSICancelled)
				window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:600px");
				if(document.getElementById('flagForCancelLsi').value == 1){
	    	
					myList= [];
					//alert('in parent window')
					var serviceReasonList={"javaClass": "java.util.ArrayList",
							"list": javaList
						};
		    	
		    		var jsData =			
		 		    {
		    		   searchLSIScenario:scenario,
		    		   LSICancellationDtolist:serviceReasonList 			 			  
		 			};
		 			
		 			var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		 			//showLayer();
		 			var status = jsonrpc.processes.LSICancellation(jsData,sessionId);
					
		 			if(status !="" && status !=null ){
		 				alert(status);
		 			}
		 			
		 			if(status="LSI/Service(s) cancelled successfully!!!"){
		 				//should be blank list
		 				myList=[];
		 				searchSubmit()
			 			//FirstPageLsiCancellation('SERVICEID','LSICANCELLATION')
		 			}
	 			
	    		}
			}else{
			    		alert('please select service')
			    	}
	    	
	    	}
}

function populateReasonForCancel()
{
	var action_srv='<%= AppConstants.ACTION_SERVICE %>';
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	var stdReason = jsonrpc.processes.populateReasonForCancel(action_srv);
	var cancelReasonCombo = document.getElementsByName('cancelReasonDD');
	/* Added for loading LOVs for main cancellation reason and remarks*/
	var combomain = document.getElementById("cancelReasonDDMain");	
	   var iCountRows = combomain.length;
	   for (var i = 1; i <  iCountRows; i++)
	   {
		   combomain.remove(i);
	   }
	 
	   for(var j=0;j<stdReason.list.length;j++)
	     {
		    var reasonname= stdReason.list[j].reasonName;
	    	var optionmain = document.createElement("option");	    	
	    	
	   		optionmain.text = stdReason.list[j].reasonName;
	   		optionmain.title = stdReason.list[j].reasonName;
			optionmain.value = stdReason.list[j].reasonID;
			try 
			{
				combomain.add(optionmain, null); //Standard
				
			}
			catch(error) 
			{
				combomain.add(optionmain); // IE only
				
			} 	
	     }
	   
	   
	for(i=0; i< cancelReasonCombo.length;i++)
	{
    	 var combo=document.getElementById('cancelReasonDD'+i);
     
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
}
function fnCopy(){
	var myForm=document.getElementById('searchForm');
	var cancelreasonmain=document.getElementById('cancelReasonDDMain').value;
	var cancelremarksmain=trim(document.getElementById('cancelRemarksMain').value);
	var counter=0;
	if(cancelreasonmain!='0'||cancelremarksmain!=""){
		if(document.forms[0].chkSelectService){
			var chkLength=document.forms[0].chkSelectService.length;
			document.getElementById('hiddenCancellationReason').value= cancelreasonmain;
	    	document.getElementById('hiddenRemarks').value= cancelremarksmain;
	    	if(chkLength==undefined) /*  after search for one record then check length is undefined */
	 		{    			
	    		/* if(document.forms[0].chkSelectService.checked==true)
	   			{ */
	    		document.getElementById('cancelReasonDD'+'0').value=cancelreasonmain;
	    		document.forms[0].cancelRemarks.value=cancelremarksmain;
                counter++;
	   			/* } */
	 		}
	    	else{
	    		for(i=0;i<chkLength;i++)
				{
	    			/* if(myForm.chkSelectService[i].checked==true){ */
	    				document.getElementById('cancelReasonDD'+i).value=cancelreasonmain;
	    				document.getElementById('cancelRemarks'+i).value=cancelremarksmain;
	    				counter++;
	    			/* } */
				}
	    	}
	    	if(counter==0){
				document.getElementById('hiddenCancellationReason')= '';
	    		document.getElementById('hiddenRemarks')= '';	    	
	    		alert("Value(s) not copied");
	    	}else{
		    	for(var key in myList) {
				    if(myList.hasOwnProperty(key)) {
				    	myList[key].cancellationReasonId= cancelreasonmain;
				    	myList[key].cancellationRemarks= cancelremarksmain;
				    }	
			    }
	    	}
	    	
		}
	}
	else{
		alert("Please enter values(s) to copy");
	}
}



/* $(document).ready(function(){
        $('input[type="checkbox"]').click(function(){
            if($(this).prop("checked") == true){
                alert("Checkbox is checked.");
            }
            else if($(this).prop("checked") == false){
                alert("Checkbox is unchecked.");
            }
        });
    }); */
    
/*     document.getElementById('terms').onclick = function() {
    // access properties using this keyword
    if ( this.checked ) {
        // if checked ...
        alert( this.value );
    } else {
        // if not checked ...
    }
}; */

</script>
</head>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<%-- <%String pageSubmit=request.getParameter("fromPageSubmit");
	if (("1").equals(1))
	{%>
		<body onload="populateReasonForCancel();">
	<%
   }
   else{	
%>
<body>
<%
   }
%> --%>

<body onload="populateReasonForCancel();" style="vertical-align: top;">

<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<html:form action="/defaultDraftNewOrdAction" method="post" styleId="searchForm">
	<jsp:include page="../header.jsp" flush="true"/> 
	<div class="head"><span>LSI Cancellation</span></div>
	<table border="0" align="left" cellpadding="0" cellspacing="0" width="95%" style="margin: 10px" >
	<tr>
	<td>
	<table border="0" align="center" cellpadding="0" cellspacing="0" width="100%" >
		<tr valign="middle">
			<td valign="bottom" align="center">
				<bean:define id="formBean" name="defaultBean"></bean:define>
				<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
				<html:hidden name="formBean" property="pagingSorting.pageNumber"/>
				<html:hidden name="formBean" property="pagingSorting.sortByColumn"/>
				<html:hidden name="formBean" property="pagingSorting.sortByOrder"/>
				<input type="hidden" name="fromPageSubmit" value="1"/>
				<table border="1" class="tab2" cellpadding="2" cellspacing="1" align="center" width="100%" >
					<tr>
						<%-- <td class="tab2" colspan="7">
							<bean:define id="pagingBean" name="formBean"></bean:define>
							<%String  currPageNumber =String.valueOf(((DefaultBean)formBean).getPagingSorting().getPageNumber());
							String  maxPageNumber =String.valueOf(((DefaultBean)formBean).getPagingSorting().getMaxPageNumber());%>
							<jsp:include flush="true" page="../paging.jsp">							
								<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
								<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
							</jsp:include>
						</td> --%>
						
						    <td>
								<td align="center"><a href="#" id= "first" onclick="FirstPageLsiCancellation('SERVICEID','LSICANCELLATION')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPageLsiCancellation('SERVICEID','LSICANCELLATION')">Next</a></td>
								<td align="center">
									<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
									<input type="text" id="txtMaxPageNo"  class="inputBorder2" readonly="true" size="2"/>Pages
								</td>
								<td align="center"><a href="#" id="prev" onclick="PrevPageLsiCancellation('SERVICEID','LSICANCELLATION')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPageLsiCancellation('SERVICEID','LSICANCELLATION')">Last</a></td>
							</td>	
											
						<td align="right" style="padding-right:10px;" colspan="3">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;
							<a href="#"><img onclick="searchSubmit();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;
							<img src="gifs/top/home.gif" title="Home" onClick="goToHome('Home');"></img>
						</td>
							
<!-- 						<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
 -->						
						<!-- </table> -->
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table border="1" class="tab2" align="center" cellpadding="0" cellspacing="0" width="100%" >
		<tr align="left">
			<td  colspan="10">LSI Scenario &nbsp;&nbsp;  
				<html:select property="searchLSIScenario"
							styleId="searchLSIScenarioId" style="width:200px" styleClass="inputBorder1">
							<html:option value="0">--Please Select--</html:option>
							<html:option value="BEFORE_PUBLISH">Before Publish</html:option>
							<html:option value="AFTER_PUBLISH">After Publish</html:option>
						</html:select>
			</td>	
			<td id ="combo">Cancellation Reason &nbsp;&nbsp;
							<select id="cancelReasonDDMain" name="cancelReasonDDMain" style="width:175px;" property="cancelllationReasonId">
							<option value="0">Select Reason </option>&nbsp;
							</select>&nbsp;														
			</td>
			<td>
				<input type="hidden" name="hiddenCancellationReason"  id="hiddenCancellationReason" value="">
				<input type="hidden" name="hiddenRemarks"  id="hiddenRemarks" value="">
				<input type="hidden" name="flagForCancelLsi"  id="flagForCancelLsi" value="">
			</td>
			<td>Cancellation Remarks &nbsp;&nbsp;
			<textarea name="cancelRemarksMain" property="cancelServiceRemarks" onkeypress="return (this.value.length < 500);" rows="1" id="cancelRemarksMain">
			</textarea>&nbsp;</td>	
			<td>
			<div class="searchBg"><a href id="copy" onClick="fnCopy()">Copy</a></div>
		    </td>
		</tr>
	</table>
	<table border="0" cellpadding="0" cellspacing="0" class='lsicancellationmain'>
		<tr>
			<td class='lsicanceltablefrozencolumn'>
				<div id='divroot' class='root'>
                    <table border="0" cellpadding="0" cellspacing="0" width="100%" class='lsicancellationroot'>
                        <tr>
                            <td class='inner lsicancellationfrozencol colwidth head1'>
                                
                            </td>
                        </tr>
                    </table>
	            </div>
	            <div id='divfrozen' class='lsicancellationfrozen'>
                    <table border="0" cellpadding="0" cellspacing="0" width="100%" class='lsicancellationfrozen'>
                        <tr>
                            <td class='inner lsicancellationfrozencol toprow'>
                                
                            </td>
                        </tr>                            
                    </table>
                </div>
			</td>
			<td class='lsicanceltablecontent'>
			<div id='headscroll' class='divhead'>
             <table border="0" cellpadding="0" cellspacing="0" class='head1'>
				<tr>
					<td class='inner col1 head1' align="center"><b>S.No</b></td>
					<td class='inner col1 head1' align="center"><b>Select</b></td>
					<td class='inner col2 head1' align="center"><b>Order No</b></td>
					<td class='inner col2 head1' align="center"><b>Logical SI No</b></td>
					<td class='inner col2 head1' align="center"><b>Service No</b></td>
					<td class='inner col3 head1' align="center"><b>Service Name</b></td>
					<td class='inner col5 head1' align="center"><b>Cancellation Reason</b></td>
					<td class='inner col3 head1' align="center"><b>Cancellation Remarks</b></td>
					<td class='inner col3 head1' align="center"><b>Account Name</b></td>
					<td class='inner col2 head1' align="center"><b>Product</b></td>
					<td class='inner col2 head1' align="center"><b>Sub Product</b></td>	
					<td class='inner col2 head1' align="center"><b>Order Type</b></td>
					<td class='inner col2 head1' align="center"><b>Order Stage</b></td>
					<td class='inner col3 head1' align="center"><b>Changetype</b></td>
					<td class='inner col3 head1' align="center"><b>SubChangetype</b></td>
					<td class='inner col4 head1' align="center"><b>Order Created Date</b></td>
 					<!-- <td class='inner col4 head1' align="center"><b>To Order Date</b></td> -->
 					<td class='inner col2 head1' align="center"><b>Account No</b></td>
				</tr>
				<!-- adding search  -->
           		<tr class="th">
           			<td class='inner col1' align="center">&nbsp;</td>
					<td class='inner col1' align="center"><input name="chkAllService" id="chkAllService"  type="checkbox" onclick="fnCheckServiceAll()"> </td>
					<td class='inner col2' align="center">
						<html:text property="searchCRMOrder" size="10"  maxlength="18" styleId="id_orderno_search"  
						onkeydown="if (event.keyCode == 13){searchSubmit()};"
						/>
						</td>
						<td class='inner col2' align="center">
						<html:text property="searchLSI" size="10" maxlength="18" styleId="id_lsi_search" 
						onkeydown="if (event.keyCode == 13){searchSubmit()};" 
						></html:text>
					</td>
					<td class='inner col2' align="center">
						<html:text property="searchServiceNo" size="10" maxlength="18" styleId="id_srvno_search" 
						onkeydown="if (event.keyCode == 13){searchSubmit()};"></html:text>
					</td>
					<td class='inner col3' align="center">&nbsp;</td>
					<td class='inner col5' align="center">&nbsp;</td>
					<td class='inner col3' align="center">&nbsp;</td>
					<td class='inner col3' align="center">
						<html:text property="searchAccountName" maxlength="100"  size="15" styleId="id_accountname_search" 
						onkeydown="if (event.keyCode == 13){searchSubmit()};" 
						></html:text>
					</td>
					<td class='inner col2' align="center">&nbsp;</td>
					<td class='inner col2' align="center">&nbsp;</td>
					<td class='inner col2' align="center">&nbsp;</td>
					<td class='inner col2' align="center">&nbsp;</td>
					<td class='inner col3' align="center">&nbsp;</td>
					<td class='inner col3' align="center">&nbsp;</td>
					<td class='inner col3' align="center">&nbsp;</td>
					<%-- <td class='inner col4' align="center">
							<html:text property="searchFromOrd_Date" size="12" readonly="true"  styleId="orddateFrom" ></html:text>
							<font size="1">
								<a href="javascript:show_calendar(searchForm.searchFromOrd_Date);" class="borderTabCal">
								<img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td> --%>	
						<%-- <td class='inner col4' align="center">
							<html:text property="searchToOrd_Date" size="12" readonly="true"  styleId="orddateTo" ></html:text>
							<font size="1">
								<a href="javascript:show_calendar(searchForm.searchToOrd_Date);" class="borderTabCal">
								<img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
							</font>
						</td> --%>
						<td class='inner col2' align="center">
							<html:text property="searchAccountNo" size="10" maxlength="18" styleId="id_accountno_search" 
							onkeydown="if (event.keyCode == 13){searchSubmit()};" 
							></html:text>
						</td>
           		</tr>								
			</table>
            </div> 
             <div id='contentscroll' class='lsicancelcontent1' onscroll='reposVertical(this);'>
             	

             		
             </div>               	
			</td>
			<td class='tableverticalscroll' rowspan="2">
				<div class='vertical-scrolllsicancellation' onscroll='reposVertical(this);'>
                    <div></div>
                </div>
                <div class='ff-fill'></div>
			</td>
		</tr>   
		<tr>
           	<td colspan="3">
               	<div class='horizontal-scrolllsicancellation' onscroll='reposHorizontal(this);' ">
                   	<div>
                   	</div>
               	</div>
           	</td>
         </tr>
	</table>
	<table border="1" class="tab2" cellspacing="0" cellpadding="0" align="left" width="100%" >	
		<tr>
			<td colspan="8" align="center">
				 <input type="button" value="Submit" onclick="fnSubmit()"/>
			</td>
		</tr>
	</table>
</td></tr>
</table>
</html:form>
</body>
</html:html>
