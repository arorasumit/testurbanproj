<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 SAURABH SINGH	24-Jan-13	00-05422		To publish order in single transaction point no 86 HyperCare-->
<!--[002]   Raveendra           20-01-2015               Partial service validation -->
<!--[003] VIPIN SAHARIA 11th Aug 2015 CBR_20141219-R2-020936-Billing Efficiency Program Drop 3 (BFR 17) Reverting MBIC CC mapping as per L3-VCS mapping -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppUtility"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title> View Partial Publish</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script type="text/javascript" src="js/gen_validatorv31.js"></script>
<script language="javascript" type="text/javascript">
<%
	HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
	HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
	UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>
<%String poNumber=request.getParameter("poNumber");%>
var poNumber='<%=poNumber%>';
var PAGE_SIZE_SERVICE_LINE="<%=objUserDto.getPageSizeServiceLine()%>";
var callerWindowObj = dialogArguments;
var sortByColumn='SERVICEID';
var varCurrentService=0;
var varDummyService=0; // This is used to represent parent LSI for Arbor Line Items
var varDummyOrder=0;
var varIsDummyServicePublished=0;
var nonDummyServiceList="";
var dummyServiceList="";
var	dummyOrderList="";

function DrawPartialPublishTable()
{
	//vijay start
	pageRecords=PAGE_SIZE_SERVICE_LINE;
    var mytable = document.getElementById('viewPartialPublish');	
    var iCountRows = mytable.rows.length;
 
    for (i = 1; i <  iCountRows; i++)
    {
       mytable.deleteRow(1);
    } 
	   
	//vijay end
	 document.getElementById('txtPageNumber').value= pageNumber;
	   sync();	
		var jsData = 
		{
			startIndex:startRecordId,
			endIndex:endRecordId,
			sortBycolumn:sortByColumn,
			sortByOrder:sortByOrder,
			orderNo:poNumber
		};
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		try
		{
			//[003]
		   	serviceList = jsonrpc.processes.getServiceListForTheOrder(jsData);
		   	//serviceListInM6 = jsonrpc.processes.getServicesForTheOrderInM6(jsData);
		}
   		catch(e)
   		{	
   			alert("exception :  "+ e);
		}
		
		//vijay start
		iTotalLength=serviceList.list.length;	
		if(iTotalLength !=0){
			iNoOfPages = serviceList.list[0].maxPageNo;
		}else{     
		    iNoOfPages=1;
		}
		document.getElementById('txtMaxPageNo').value=iNoOfPages;
		//vijay end
		
	   	for (i = 0; i <  serviceList.list.length; i++)
	 	{
			
			var count=0;
			count = count+i;
				
			var tblRow=document.getElementById('viewPartialPublish').insertRow();
			
			var tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=serviceList.list[i].serviceId;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			var  tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=serviceList.list[i].serviceName;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			
			if(serviceList.list[i].isPublished==1)
				str="<input name='chk' id='chk"+i+"' type='checkbox' value='"+serviceList.list[i].serviceId+"' Checked='check' disabled='true'/>";
			else
				str="<input name='chk' id='chk"+i+"' type='checkbox' value='"+serviceList.list[i].serviceId+"' onclick='unCheckMaster("+i+");checkForDummyServiceAgainstTheServiceChecked("+i+");'/>";
			
			str=str+"<input name='dummyServiceNo' 		   id='dummyServiceNo"+i+"' 		 type='hidden' value='"+serviceList.list[i].dummyServiceId+"' >";
			str=str+"<input name='isDummyServicePublished' id='isDummyServicePublished"+i+"' type='hidden' value='"+serviceList.list[i].isDummyServicePublished+"' >";
			str=str+"<input name='dummyOrderNo'   		   id='dummyOrderNo"+i+"'   		 type='hidden' value='"+serviceList.list[i].dummyOrderNo+"' >";
			//str=str+"<input name='isMbicLineDummy'   		   id='isMbicLineDummy"+ serviceList.list[i].serviceId +"'   		 type='hidden' value='"+serviceList.list[i].isMbicLineDummy+"' >";
			//str=str+"<input type='hidden' id='hdnMBICServiceID" + serviceList.list[i].serviceId + "' value='" +serviceList.list[i].mbicServiceId+ "' ><input type='hidden' id='hdnMBICServiceTypeID" + serviceList.list[i].serviceId + "' value='" +serviceList.list[i].serviceTypeId+ "' ><input type='hidden' id='hdnTGNO_Number" + serviceList.list[i].serviceId + "' value='" +serviceList.list[i].tgno_Number+ "' ><input type='hidden' id='hdnCC_M6_Progess_Status" + serviceList.list[i].serviceId + "' value='" +serviceList.list[i].cc_M6_Progress_status+ "' > ";
			CellContents = str;
			tblcol.innerHTML = CellContents;
				
			document.getElementById('hdnCounterLength').value=count;
		}	
		if(serviceList.list.length == 0)
		{
			var tblRow=document.getElementById('viewPartialPublish').insertRow();
    		tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			tblcol.colSpan = 7;
			str='NO SERVICES';
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			//vijay start
			document.getElementById('first').disabled=true;
		    document.getElementById('prev').disabled=true;
		    document.getElementById('last').disabled=true;
		    document.getElementById('next').disabled=true;
		    //vijay end
		}
		else{
			//vijay start for paging functionality
			
			document.getElementById("checks").checked=false;
			var pagenumber=document.getElementById('txtPageNumber').value;
			var MaxPageNo=document.getElementById('txtMaxPageNo').value;
			if(pagenumber && MaxPageNo==1)
			{
				document.getElementById('first').disabled=true;
		     	document.getElementById('prev').disabled=true;
				document.getElementById('last').disabled=true;
		     	document.getElementById('next').disabled=true;
			}
			if(pagenumber==1 && MaxPageNo!=1){
				document.getElementById('first').disabled=true;
				document.getElementById('prev').disabled=true;
				document.getElementById('last').disabled=false;
				document.getElementById('next').disabled=false;
			}
			if(pagenumber==MaxPageNo && pagenumber!=1){
		   		document.getElementById('last').disabled=true;
		  		document.getElementById('next').disabled=true;
		   		document.getElementById('first').disabled=false;
			    document.getElementById('prev').disabled=false;
			}
		 	if(pagenumber!=MaxPageNo && pagenumber!=1){
		    	document.getElementById('last').disabled=false;
		    	document.getElementById('next').disabled=false;
		    	document.getElementById('first').disabled=false;
		    	document.getElementById('prev').disabled=false;
		 	}
			//vijay end for paging functionality
		}		
		 
		 
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		//var jsData2 = 
		//{
		//	orderNo:poNumber
		//};
		//try
		//{
		//   	serviceListInM6 = jsonrpc.processes.getServicesForTheOrderInM6(jsData2);
		//}
   		//catch(e)
   		//{	
   		//	alert("exception :  "+ e);
		//}
	//	if(serviceListInM6.list.length >0)
	//	{
	//		for(var i=0; i < serviceListInM6.list.length; i++)
	//		{
	///			for(var j=0; j <= count; j++)
	//			{
	///				if(document.getElementById('chk'+[j]).value==serviceListInM6.list[i].serviceId)
	///				document.getElementById('chk'+[j]).disabled=true;
	///			}
	//		}
	//	}
}
function cancelSelection()
{
	window.close();
}
function checkMBICTGNO_Number(serviceId)
{
	var result=true;
	if(document.getElementById('hdnMBICServiceTypeID'+serviceId).value==413)
	{
		var tgno_Number=document.getElementById('hdnTGNO_Number'+serviceId).value;
		var cc_m6_progress_status=document.getElementById('hdnCC_M6_Progess_Status'+serviceId).value;
		var isMbicLineDummy=document.getElementById('isMbicLineDummy'+serviceId).value;
		if(isMbicLineDummy=='' && (tgno_Number=='' || (cc_m6_progress_status != 'M6_END-FX_BT_START' && cc_m6_progress_status != 'FX_BT_END')) )
		{
			alert("Please Enter TGNO Number For MBIC Service No:"+serviceId+" Or Check Clear Channel M6 Close Which is Mapped By This MBIC Service.");
			result=false;
		}
	}
	return result;
}

function unCheckMaster(counter)
{
	var serviceId=document.getElementById('chk'+counter).value;
	/*if(document.getElementById('chk'+counter).checked==true && document.getElementById('hdnMBICServiceTypeID'+serviceId).value==413)
	{
		if(!checkMBICTGNO_Number(serviceId))
			document.getElementById('chk'+counter).checked=false;
	}*/
	count=document.getElementById('hdnCounterLength').value;
	for (i = 0 ; i <= count; i++)
	{
		if(document.getElementById('chk'+i).checked==false)
		{
			document.getElementById("checks").checked=false;
		}
	}
}

function selectNupdate(){
	var serviceList='';
	var publishList='';
	var answer = confirm("Do You Want To  Continue")
	if(answer){
		//Start[001]
		var var_start_publish_part1=new Date();
	/*Vijay
	* following method call "sumit()" is commented because in this page pagination is using  and if user
	* click on "Check All" button then only current page's services would be selected rather than all services. 
	* That means if users want to publish all service in one go then he will click on "Publish All" button.
	*/	
	
	//	if(document.getElementById("checks").checked==true){
	//	submit(serviceList,publishList);
	   		
			/*var jsData = 
				{
					orderNo:poNumber,
					published_by_empid:callerWindowObj.document.getElementById('empID').value,
					published_by_roleid:callerWindowObj.document.getElementById('roleID').value
				};
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			try
				{
			  	 	var status = jsonrpc.processes.updateFullPublish(jsData);
				}
			catch(e)
				{	
			 		alert("exception :  "+ e);
				}
			if(status=="success")
				{
			 		submit();
				}
			else
			 	{
			 		alert("status");
			 		return false;
			 	}*/
		//}else{
			var serviceList='';  //All services goes with this variable
			var serviceIds='';   //goes only selected service ids 
			var publishList='';  //goes a list of value in the form of 0/1 
			var number = -1;
			var mbicServicesWithTgnoBlank='';
			count=document.getElementById('hdnCounterLength').value;
			//bring services having TGNO details blank in history table for a particular order at once. 
			var listServicesWithTgnoBlank = jsonrpc.processes.getMBICServicesWithTGNOBlank(poNumber);
			for (i = 0 ; i <= count; i++){
	  			if(document.getElementById('chk'+i).disabled==false){
		  			if(document.getElementById('chk'+i).checked){
		  				
		  				//chk if serviceId in map/set  ->if yes then append 0 and keep serviceid in different string also
		  				if(listServicesWithTgnoBlank.indexOf(document.getElementById('chk'+i).value)!=-1){
		  					mbicServicesWithTgnoBlank=mbicServicesWithTgnoBlank+document.getElementById('chk'+i).value + ",";
		  					
		  					serviceList = serviceList + document.getElementById('chk'+i).value + ",";
							publishList = publishList + 0 + ",";
		  				}
		  				else{
							serviceList = serviceList + document.getElementById('chk'+i).value + ",";
							publishList = publishList + 1 + ",";
							if(serviceIds==''){
								serviceIds=document.getElementById('chk'+i).value;
							}else{
								serviceIds=serviceIds+","+document.getElementById('chk'+i).value;
							}
		  				}
					}else{
						serviceList = serviceList + document.getElementById('chk'+i).value + ",";
						publishList = publishList + 0 + ",";
						number++;
					}
				}else{
					number++;
				}	
				if(number==count){
					alert("Please check atleast one service");
					return false;
				}
			}	
			
			var var_start_validate_time=new Date();
			//Start [002]
			callerWindowObj.validateOrder('1',false,serviceIds);
			var isValidatePO =	callerWindowObj.document.getElementById("isValidatePO").value;	
			var var_end_validate_time=new Date();
			var var_total_validate_time=var_end_validate_time.getTime()-var_start_validate_time.getTime();
		    //if Validate successfully 
		    if(isValidatePO==0){
			//var validInvalid = jsonrpc.processes.updateStatus(order,0);
			//callerWindowObj.updateStatus(0);
			//aNIL cHANGE FOR cLOSE WINDOW ERROR
			window.close();return;		
		    }

		   //End [002]

			
			var jsData = {
				serviceIdString:serviceList,
				isPublishedString:publishList,
				serviceId1:serviceIds,
				orderNumber:poNumber,
				published_by_empid:callerWindowObj.document.getElementById('empID').value,
				published_by_roleid:callerWindowObj.document.getElementById('roleID').value
			};
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			try{
		   	 	var status = jsonrpc.processes.updateViewIsPublish(jsData);
		    }catch(e){	
	   			alert("exception :  "+ e);
   			}
		    
		    //if tgno string is not blank concat : status and then tgno related message
		    if(mbicServicesWithTgnoBlank!='')
		    	status=((null==status)?"":status)+"\nKindly fill mandatory field TGNO number for services with Service No. : "+(mbicServicesWithTgnoBlank.substring(0, mbicServicesWithTgnoBlank.length - 1));
		    
	   		if(status=="" ||status==null){
	   			try{
	   				var var_end_publish_part1=new Date();
					var total_time_elapsed=(var_end_publish_part1.getTime()-var_start_publish_part1.getTime())-var_total_validate_time;
					loggTotalTimeTaken(poNumber,var_start_publish_part1,total_time_elapsed,'PUBLISH_PART1');
				}catch(e){alert('Logging of validation recoding time failed!!');}
	   			submit(serviceList,publishList);
	   		}else{
	   			alert(" "+status);
   				return false;
   			}
   		//}
   		//End [001]
    }else{
		return false;
	}
}

function submit(serviceList,publishList)
{
	//alert("Selected Services Published");
	callerWindowObj.publishAfterViewPartialPublish(serviceList,publishList);
	window.close();
}

function fnCheckAll()
{
	count=document.getElementById('hdnCounterLength').value;
	nonDummyServiceList="";
	dummyServiceList="";
	dummyOrderList="";
	if(document.getElementById("checks").checked==true)
	{
		for (i = 0 ; i <= count; i++)
		{
			if(document.getElementById('chk'+i).disabled==false)
			{
				var serviceId=document.getElementById('chk'+i).value;
				/*if(document.getElementById('hdnMBICServiceTypeID'+serviceId).value==413)
				{
					if(!checkMBICTGNO_Number(serviceId))
					{
						document.getElementById('chk'+i).checked=false;
						document.getElementById("checks").checked=false;//Added as on 30-jan-2013	
					}
					else
						document.getElementById('chk'+i).checked=true;	
				}
				else*/{
				document.getElementById('chk'+i).checked=true;
				checkForNonDummyServicesChecked(i);
				}
			}
		}
		if(nonDummyServiceList!="")
		{
			var str="dummy service(s) " +dummyServiceList+ " \n against following service(s) " +nonDummyServiceList;
			str=str+"\n in order no "+dummyOrderList+ " respectively are not published. \n Please publish those dummy service(s) before these service(s)"
			alert(str);
			document.getElementById("checks").checked=false;
		}
	}
	else
	{
		for (i = 0 ; i <= count; i++)
		{
			if(document.getElementById('chk'+i).disabled==false)
			{
				document.getElementById('chk'+i).checked=false;
			}
		}
	}
}
function checkForDummyServiceAgainstTheServiceChecked(counter)
{
	var count=document.getElementById('hdnCounterLength').value;
	
	varCurrentService=document.getElementById('chk'+counter).value;
	varDummyService=document.getElementById('dummyServiceNo'+counter).value;
	varDummyOrder=document.getElementById('dummyOrderNo'+counter).value;
	varIsDummyServicePublished=document.getElementById('isDummyServicePublished'+counter).value;
	
	if(document.getElementById('chk'+counter).checked==true)
	{
		if(varDummyService!=0 )//This will check if there is any dummy service atached
		{
			if(varDummyOrder==poNumber)// This will check if the dummy service is in same order
			{
				for (var i = 0 ; i <= count; i++)
				{
					if(document.getElementById('chk'+i).value==varDummyService && document.getElementById('chk'+i).checked==false)
					{
						alert("Please select Charge Redirection Linked Service No: "+varDummyService+" Before Selecting This Service");
						document.getElementById('chk'+counter).checked=false;
						break;
					}
				}
			}
			else
			{
				if(varIsDummyServicePublished==0)//This will check if the dummy service present in other order is published or not
				{
					alert("Please Publish Charge Redirection Linked Service No: "+varDummyService+"\n of OrderNo "+varDummyOrder+ " Before Selecting/Publishing This Service" );
					document.getElementById('chk'+counter).checked=false;
				}
			}
		}
	}
	else
	{
		var serviceNoAginstDummy = "";
		for (var i = 0 ; i <= count; i++)
		{
			if(document.getElementById('dummyServiceNo'+i).value==varCurrentService && document.getElementById('chk'+i).checked==true)
			{
				if(serviceNoAginstDummy=="")
					serviceNoAginstDummy = document.getElementById('chk'+i).value;
				else
					serviceNoAginstDummy = serviceNoAginstDummy + ", "+document.getElementById('chk'+i).value;
			}
		}
		if(serviceNoAginstDummy!="")
		{
			alert("Please Uncheck Service No "+serviceNoAginstDummy+ " Before Unchecking This Charge Redirection Linked Service");
			document.getElementById('chk'+counter).checked=true;
		}
	}
}

function checkForNonDummyServicesChecked(counter)
{
	var count=document.getElementById('hdnCounterLength').value;
	
	varCurrentService=document.getElementById('chk'+counter).value;
	varDummyService=document.getElementById('dummyServiceNo'+counter).value;
	varDummyOrder=document.getElementById('dummyOrderNo'+counter).value;
	varIsDummyServicePublished=document.getElementById('isDummyServicePublished'+counter).value;
	
	if(varDummyService!=0 && varDummyOrder!=poNumber && varIsDummyServicePublished==0)
	{
		document.getElementById('chk'+counter).checked=false;
		if(nonDummyServiceList=="")
		{
			nonDummyServiceList = varCurrentService;
			dummyServiceList=varDummyService;
			dummyOrderList=varDummyOrder;
		}else{
			nonDummyServiceList = nonDummyServiceList + ", "+varCurrentService;
			dummyServiceList= dummyServiceList + ", "+varDummyService;
			dummyOrderList = dummyOrderList + ", "+varDummyOrder;
		}
	}
}		

//Vijay add new method to publish all service in one go
function publishAll(){
	var serviceList='';
	var publishList='';
	//Start [002]
    	callerWindowObj.validateOrder('1',true,'');
		var isValidatePO =	callerWindowObj.document.getElementById("isValidatePO").value;	
	//if Validate successfully 
	if(isValidatePO==0){
		//var validInvalid = jsonrpc.processes.updateStatus(order,0);
		//callerWindowObj.updateStatus(0);
		//aNIL cHANGE FOR cLOSE WINDOW ERROR
		window.close();return;		
	}
	
    //End [002]
    
	
	
	var answer = confirm("Do You Want To  Continue")
	if(answer){
		//going to validate to all services and remove those services who are not eligilble.
		validationForPublishAll();
		//submit(serviceList,publishList);
	}
	else{
		return false;
	}
}
function loggTotalTimeTaken(orderno,start_time,total_elapsed_time,actiontype){
	jsonrpc.processes.loggTotalTimeTaken(orderno,start_time,total_elapsed_time,actiontype);
}
function validationForPublishAll(){

	var var_start_publish_part1=new Date();
	
  	var serviceList='';  //All services goes with this variable
	var serviceIds='';   //goes only selected service ids 
	var publishList='';  //goes a list of value in the form of 0/1 
	var isTgnoFail=false; //check TGNO validation
	var isDummyFail= false;   //check dummy services validation
	var validServiceCount = 0;
	
	//==================================================================================
			/* 
			 *  Fetch the all services and do validate all things same as implemented 
			 *  in fnCheckAll() method. During Validation we are filtering only those 
			 *  service/s who are eligible for publish. 
			*/				
	//==================================================================================
	var jsData = 
	{
		orderNo:poNumber
	};
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	try
	{
		//[003]
	   	allServiceList = jsonrpc.processes.getServiceListForTheOrderWithoutPaging(jsData);
	   	var listServicesWithTgnoBlank = jsonrpc.processes.getMBICServicesWithTGNOBlank(poNumber);
	   	for (i = 0; i <  allServiceList.list.length; i++)
	 	{
		   	var serviceId = 	          allServiceList.list[i].serviceId;			    //it is service Id
		    var dummyServiceNo=	          allServiceList.list[i].dummyServiceId;			//it is dummyServiceId
		    var isDummyServicePublished=  allServiceList.list[i].isDummyServicePublished;	//it is isDummyServicePublished
		    var dummyOrderNo=             allServiceList.list[i].dummyOrderNo;				//it is dummyOrderNo
		    //var isMbicLineDummy=          allServiceList.list[i].isMbicLineDummy;			//it is isMbicLineDummy
		    //var hdnMBICServiceID=         allServiceList.list[i].mbicServiceId;			//it is mbicServiceId
		   	//var hdnMBICServiceTypeID=     allServiceList.list[i].serviceTypeId;		    //it is serviceTypeId
		   	//var tgno_Number=	          allServiceList.list[i].tgno_Number;				//it is tgno_Number	
		   	//var cc_M6_Progress_status=    allServiceList.list[i].cc_M6_Progress_status;	//it is cc_M6_Progress_status
		   	var isPublished          =    allServiceList.list[i].isPublished;				//it's value would be 1 or 0. (1 means has been published & 0 means yet not published) 
	  
			  //same validation as 'unCheckMaster()' this method  
			  if(isPublished==0)
			  {
			  	//check validation for TGNO number and if validation fail then remove that service for publish 
			  	/*if(hdnMBICServiceTypeID==413){
				   	if(isMbicLineDummy=='' && (tgno_Number=='' || cc_M6_Progress_status != 'M6_END-FX_BT_START') )
					{
						isTgnoFail = true;
						serviceList = serviceList + serviceId + ",";
						publishList = publishList + 0 + ",";
					}
			     }
					    //check validation for dummy services
			     else*/ if(dummyServiceNo !=0 && dummyOrderNo!=poNumber && isDummyServicePublished==0) 
				 {
				 	isDummyFail=true;
				  	serviceList = serviceList + serviceId + ",";
				  	publishList = publishList + 0 + ",";
				 }
				 else
				 {
					 if(listServicesWithTgnoBlank.indexOf(serviceId)!=-1){
	  					serviceList = serviceList + serviceId + ",";
						publishList = publishList + 0 + ",";
	  				}
	  				else{
					 	validServiceCount++ ;
					 	serviceList = serviceList + serviceId + ",";
						publishList = publishList + 1 + ",";
						if(serviceIds==''){
							serviceIds=serviceId;
						}else{
							serviceIds=serviceIds+","+serviceId;
						}
	  				}			 
				 }
			  }
			  		 
	   	}//End of for loop 	
	}
  	catch(e)
  	{	
  			alert("exception :  "+ e);
	}
	
	//=========================================End of Validation========================
	//==================================================================================
	
	var jsData = {
				serviceIdString:serviceList,
				isPublishedString:publishList,
				serviceId1:serviceIds,
				orderNumber:poNumber,
				published_by_empid:callerWindowObj.document.getElementById('empID').value,
				published_by_roleid:callerWindowObj.document.getElementById('roleID').value
			};
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");

	try{
		/*if( (!isTgnoFail) && (!isDummyFail) ){
			//if all validation is pass then just pass blank value for serviceList and publishList
			submit('','');
		}
		else*/{
			var serviceAndPublishList = jsonrpc.processes.updateViewIsPublishByPublishAll(jsData);
	   	 	var status =  serviceAndPublishList.list[0];
	   	 	
	   	 	try{
	   	 		var var_end_publish_part1=new Date();
				var total_time_elapsed=var_end_publish_part1.getTime()-var_start_publish_part1.getTime();
				loggTotalTimeTaken(poNumber,var_start_publish_part1,total_time_elapsed,'PUBLISHALL_PART1');
			}catch(e){alert('Logging of validation recoding time failed!!');}
	   	 	if((status !=null && status !="")/*&& (isTgnoFail || isDummyFail)*/){
	   	 		var filterServiceList =  serviceAndPublishList.list[1];
	   	 		var filterPublishList =  serviceAndPublishList.list[2];
	   	 		if(filterPublishList.indexOf("0")==-1)
	   	 			submit('','');
	   	 		else
	   	 			submit(filterServiceList,filterPublishList);
	   	 	}
	   	 	else{
		   	 	if(publishList.indexOf("0")==-1)
	   	 			submit('','');
	   	 		else
		   	 		submit(serviceList,publishList);
	   	 	}
		}
   		
    }
   catch(e){	
  		alert("exception :  "+ e);
 	}
   			
} 
//Vija end

</script>
<body onload="DrawPartialPublishTable()">
<table width="11%" border="1" cellspacing="0" cellpadding="0"
	height="213">
	<tr>
		<td valign="top">
		<fieldset class="border1"><legend> <b> View Partial
		Publish</b> </legend>
		<div class="scrollWithAutoScroll" style="height:250px;width:450px;">
				<!-- vijay start -->
				<table width="100%" border="1" align="center" cellpadding="0"
					cellspacing="0" class="tab2">
					<tr>
						<td align="center"><a href="#" id="first"
							onclick="FirstPage('SERVICEID','SELECTPARTIALPUBLISH')">First</a>&nbsp;&nbsp;<a
							href="#" id="next"
							onclick="NextPage('SERVICEID','SELECTPARTIALPUBLISH')">Next</a></td>
						<td align="center"><input type="text" id="txtPageNumber"
							class="inputBorder2" readonly="true" size="2" />of <input
							type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true"
							size="2" />Pages</td>
						<td align="center"><a href="#" id="prev"
							onclick="PrevPage('SERVICEID','SELECTPARTIALPUBLISH')">Prev</a>&nbsp;&nbsp;<a
							href="#" id="last"
							onclick="LastPage('SERVICEID','SELECTPARTIALPUBLISH')">Last</a></td>
					</tr>
				</table>
				<!-- vijay end -->
		<table border="1" cellspacing="0" cellpadding="0" align="center"
			width="60%" id='viewPartialPublish'>
			<tr style="font-weight: bold;">
				<td align="center" nowrap height="40" colspan="1" width="100">Service No</td>
				<td align="center" nowrap height="40" colspan="1" width="250">Service Name</td>
				<td align="center" nowrap height="40" colspan="2" width="50">Publish
				<input type="checkbox" name="checks" id="checks" onclick="fnCheckAll();"/></td>
				<input type="hidden" name="hdnCounterLength" id="hdnCounterLength" />
			</tr>
		</table>
		<table border="0" cellpadding="0" cellspacing="0" align="center"
			width="90%" id="tabOk">
			   	<tr >
					<td align="right">
							<div class="searchBg" onclick="selectNupdate()" align="right"><a
								href="#">OK</a>
							</div>
							<div class="searchBg" onclick="publishAll()" align="center"><a
								href="#">Publish All</a>
							</div>
							</td>
						<td>
							<div class="searchBg" onclick="cancelSelection()" align="left"><a
								href="#">Cancel</a></div>
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