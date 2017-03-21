<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ROHIT VERMA	17-Feb-11	00-05422		Is Demo Column Added -->
<!--[002]	 RAVNEET SINGH	11-Jul-14					Performace Validations -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@page import="com.ibm.ioes.forms.UniversalWorkQueueFormBean"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.utilities.AppUtility"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<html>
<head>
<title>Create New Order with Existing Orders</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="./gifs/style.css" type="text/css" rel="stylesheet">

<script language="JavaScript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/scw.js"></script>
<script type="text/javascript" src="npd/js/timepicker.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<script type="text/javascript" src="npd/js/inputColor.js"></script>
<script language="javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<%
	HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
	HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
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
<script language="javascript">
var roleID=<%=objUserDto.getUserRoleId()%>;
var userId = "<%=objUserDto.getUserId() %>";
function goToHome()
{
	//Meenakshi : commented entire form submit. Made a dummy form and called action
	/*document.forms[0].action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	document.forms[0].submit(); */

	var goToHomeForm=createDummyForm("<%=request.getContextPath()%>/defaultAction.do");
	attachHiddenField(goToHomeForm,"method","goToHome");
	goToHomeForm.submit();
}

function fnViewOrder(orderNo,ordertype)
{
	 // document.forms[0].checkedOrderNo.value=orderNo;
      //document.forms[0].action="./viewOrderAction.do?methodName=viewOrder&orderNo="+orderNo;
      
      var accessMode;
      var modeValue="editON";
      var ordType=ordertype.toUpperCase();    
      var userIDLogged;
      var  roleNameLogged;
      
      jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");    
      var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleID,userId);
      var shortCode = jsonrpc.processes.getShortCode(roleID);
      
      if(orderDetails.list.length==0)
	  {
			alert('Order Does Not Exist \n Please Try Valid Order No');
			return false;
	  }
	  else if(orderDetails.list.length>0)
	  {
			stage=orderDetails.list[0].stageName;
			orderType=orderDetails.list[0].orderType;
			orderNo=orderDetails.list[0].orderNumber;
			accessMode=orderDetails.list[0].mode;
			userIDLogged=orderDetails.list[0].userID;
       	   		userNameLogged=orderDetails.list[0].userName;
			roleNameLogged=orderDetails.list[0].roleName;
	  }
	  
	   //lawkush Start
     
      		      
		      if(userIDLogged!=null && userIDLogged!='')
		      {
		    	if(!confirm("User "+userNameLogged+" [User ID "+userIDLogged+ "] has already Logged on this Order with Role "+roleNameLogged+""+'\n'+" Do you continue viewing in View Order"))
		      		{
		      			return ;
		      		}
		      }
     
		
		//lawkush End
      
      
	  
	  if(ordType=="NEW" && ( stage=="New" || stage=="NEW"))
		{
			/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
      		document.forms[0].submit();*/
      		var incompleteNewOrderForm=createDummyForm("<%=request.getContextPath()%>/NewOrderAction.do");
			attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
			attachHiddenField(incompleteNewOrderForm,"draft","1");
			attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
			incompleteNewOrderForm.submit();
		}
		else if(ordType=="NEW" && (stage=="AM" ||stage=="PM" ||stage=="COPC" ||stage=="SED" ||stage=="Partial Publish" ||stage=="Published" ||stage=="Completed" ||stage=="M6_CANCELLED"  || stage == "CANCELLED"))
		{
			var incompleteNewOrderForm=createDummyForm("<%=request.getContextPath()%>/NewOrderAction.do");
			if(shortCode==stage)
			{
				//Added By Saurabh To Check AM or PM Roled Id
				if(stage=="AM" ||stage=="PM")
				{
					if(accessMode=="Write")
					{
						/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit();*/
						attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
						attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
						attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
						incompleteNewOrderForm.submit();
					}
					else if(accessMode=="Read")
					{
						var modeValue="viewMode";
						/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit();*/
						attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
						attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
						attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
						incompleteNewOrderForm.submit();
					}
				}
				else
				{
					/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
					document.forms[0].submit();*/
					attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
					attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
					attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
					incompleteNewOrderForm.submit();
				}	
				//End By Saurabh
			}
			else
			{
				var modeValue="viewMode";
				/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				document.forms[0].submit();*/
				attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
				attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
				attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
				incompleteNewOrderForm.submit();
			}
		}
		else if(ordType=="NEW" && stage=="Billing Trigger")
		{
			var incompleteNewOrderForm=createDummyForm("<%=request.getContextPath()%>/NewOrderAction.do");
			if(shortCode=="COPC")
			{
				/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
	      		document.forms[0].submit();*/
	      		attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
				attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
				attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
				incompleteNewOrderForm.submit();
			}
			else
			{
				var modeValue="viewMode";
				/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				document.forms[0].submit();*/
				attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
				attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
				attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
				incompleteNewOrderForm.submit();
	      	}
		}
		else if(ordType=="CHANGE" && ( stage=="New" || stage=="NEW"))
		{
			var incompleteChangeOrderForm=createDummyForm("<%=request.getContextPath()%>/ChangeOrderAction.do");
			/*document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
      		document.forms[0].submit();*/
      		attachHiddenField(incompleteChangeOrderForm,"method","incompleteOrder");
			attachHiddenField(incompleteChangeOrderForm,"draft","1");
			attachHiddenField(incompleteChangeOrderForm,"orderNo",orderNo);
			incompleteChangeOrderForm.submit();
		}
		else if(ordType=="CHANGE" && (stage=="AM" ||stage=="PM" ||stage=="COPC" ||stage=="SED" ||stage=="Partial Publish" ||stage=="Published" ||stage=="Completed" ||stage=="M6_CANCELLED"  || stage == "CANCELLED"))
		{
			var incompleteChangeOrderForm=createDummyForm("<%=request.getContextPath()%>/ChangeOrderAction.do");
			if(shortCode==stage)
			{
				//Added By Saurabh To Check AM or PM Roled Id
				if(stage=="AM" ||stage=="PM")
				{
					if(accessMode=="Write")
					{
						/*document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit();*/
						attachHiddenField(incompleteChangeOrderForm,"method","incompleteOrder");
						attachHiddenField(incompleteChangeOrderForm,"modeName",modeValue);
						attachHiddenField(incompleteChangeOrderForm,"orderNo",orderNo);
						incompleteChangeOrderForm.submit();
					}
					else if(accessMode=="Read")
					{
						var modeValue="viewMode";
						/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit();*/
						var incompleteNewOrderForm=createDummyForm("<%=request.getContextPath()%>/NewOrderAction.do");
						attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
						attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
						attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
						incompleteNewOrderForm.submit();
					}
				}
				else
				{
					/*document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
					document.forms[0].submit();*/
					attachHiddenField(incompleteChangeOrderForm,"method","incompleteOrder");
					attachHiddenField(incompleteChangeOrderForm,"modeName",modeValue);
					attachHiddenField(incompleteChangeOrderForm,"orderNo",orderNo);
					incompleteChangeOrderForm.submit();
					
				}
				//End By Saurabh
			}
			else
			{
				var modeValue="viewMode";
				/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				document.forms[0].submit(); */
				var incompleteNewOrderForm=createDummyForm("<%=request.getContextPath()%>/NewOrderAction.do");
				attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
				attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
				attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
				incompleteNewOrderForm.submit();
			}
			
		}
		else if(ordType=="CHANGE" && stage=="Billing Trigger")
		{
			if(shortCode=="COPC")
			{
				/*document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				document.forms[0].submit();*/
				var incompleteChangeOrderForm=createDummyForm("<%=request.getContextPath()%>/ChangeOrderAction.do");
				attachHiddenField(incompleteChangeOrderForm,"method","incompleteOrder");
				attachHiddenField(incompleteChangeOrderForm,"modeName",modeValue);
				attachHiddenField(incompleteChangeOrderForm,"orderNo",orderNo);
				incompleteChangeOrderForm.submit();
			}
			else
			{
				var modeValue="viewMode";
				/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				document.forms[0].submit();*/
				var incompleteNewOrderForm=createDummyForm("<%=request.getContextPath()%>/NewOrderAction.do");
				attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
				attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
				attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
				incompleteNewOrderForm.submit();
			}
		}            
	      
}
//[002] Start
function getCopyWaitMessage(mins,screen){
	if(screen=='CREATE_ORDER'){
		//return "Please wait....This may take atleast "+10*Math.round(mins/10)+"mins time . You can work on other links.";
		return "Please wait....  This may take approx. "+Math.round(mins)+" mins. Pls check Draft Orders List after this time. You may work on other links.";
	}
}
//[002] End
function createOrder(orderNo)
{	  
      if(confirm("Do you want to proceed ?"))
      {
		  //[002] Start
    	  jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");    
		  var mins = jsonrpc.processes.getMinutesForCreateNewOrder(orderNo);
		  var longWaitingDisplay=0;
    	  if(mins>2){
    		  var fon=document.getElementById("id_shadow_font");
        	  //fon.innerText=getCopyWaitMessage(mins,'COPY_ORDER');
        	  longWaitingDisplay=1;
    	  }
    	  
    	  if(longWaitingDisplay==0){
      	  	showLayer();
    	  }
		  //[002] End
      	    //Meenakshi : commented entire form submit. Made a dummy form and called action
	      /*document.forms[0].action="./universalWorkQueueAction.do?methodName=createNewOrderWithExisting&orderNo="+orderNo;
	      document.forms[0].submit();*/
	    
		var createOrderForm=createDummyForm("<%=request.getContextPath()%>/universalWorkQueueAction.do");
		attachHiddenField(createOrderForm,"methodName","createNewOrderWithExisting");
		attachHiddenField(createOrderForm,"orderNo",orderNo);
		createOrderForm.submit();
		//[002] Start
		if(longWaitingDisplay==1){
			var id_tab_MainHeaderOb=document.getElementById("id_tab_MainHeader");
			id_tab_MainHeaderOb.style.display='none';
			
			var id_tab_MsgHeaderOb=document.getElementById("id_tab_MsgHeader");
			id_tab_MsgHeaderOb.style.display='block';
			
			
			var tblRow=id_tab_MsgHeaderOb.insertRow();
			var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.width="50%"
			tblcol.vAlign="top";
			str=getCopyWaitMessage(mins,'CREATE_ORDER');
			CellContents = str;				
			tblcol.innerHTML = CellContents;		
		}
		//[002] End
	  }	 
}
function searchSubmit()
{
	var myForm=document.getElementById('searchForm');	
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";
	if(!isBlankForm()){		
		showLayer();
		myForm.action="<%=request.getContextPath()%>/universalWorkQueueAction.do?methodName=goToCreateNewOrderWithExistingOrder";
		myForm.submit();
	} 		
}

function searchClick()
{
	var myForm=document.getElementById('searchForm');
	
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";

	if(!isBlankForm()){
		
		showLayer();
		myForm.action="<%=request.getContextPath()%>/universalWorkQueueAction.do?method=goToCreateNewOrderWithExistingOrder";
		myForm.submit(); 
	} 	
}

function sortSubmit(sortBy)
{
	myForm=document.getElementById('searchForm');		
	myForm.elements["pagingSorting.pageNumber"].value=1;
	if(myForm.elements["pagingSorting.sortByColumn"].value==sortBy)
	{
		if(myForm.elements["pagingSorting.sortByOrder"].value=="decrement")
		{
			myForm.elements["pagingSorting.sortByOrder"].value="increment";
		}
		else
		{
			myForm.elements["pagingSorting.sortByOrder"].value="decrement";
		}
	}
	else
	{
		myForm.elements["pagingSorting.sortByColumn"].value=sortBy;
		myForm.elements["pagingSorting.sortByOrder"].value="decrement";
	}	
		
		myForm.action="<%=request.getContextPath()%>/universalWorkQueueAction.do?methodName=goToCreateNewOrderWithExistingOrder";
     	myForm.submit();       	
}
function pagingSubmit(val)
{
		var myForm=document.getElementById('searchForm');		
		showLayer();
		myForm.elements["pagingSorting.pageNumber"].value=val;	
     	myForm.action="<%=request.getContextPath()%>/universalWorkQueueAction.do?methodName=goToCreateNewOrderWithExistingOrder";
     	myForm.submit();     
}

function clearFields()
{	
	var myForm=document.getElementById('searchForm');
	myForm.searchCRMOrder.value='' ; 	
	myForm.searchAccountNo.value='' ; 
	myForm.searchAccountName.value='' ;	
	myForm.searchQuoteNumber.value='' ;	
	myForm.searchfromDate.value='' ;
	myForm.searchToDate.value='' ;	
	myForm.searchOrderType.value='' ;
	myForm.searchSource.value='' ;		
}
function onEnterValidation(obj)
{		
	var myForm=document.getElementById('searchForm');
	if(document.getElementById('searchCRMOrder').value == "0")
	{
		alert('please enter value greater than 0');					
		myForm.searchCRMOrder.value = "";
		return false;
	}
	if(document.getElementById('searchAccountNo').value == "0")
	{
		alert('please enter value greater than 0');					
		myForm.searchAccountNo.value = "";
		return false;					
	}	
	if(trim(myForm.searchCRMOrder.value).length>0 || trim(myForm.searchAccountNo.value).length>0 || trim(myForm.searchQuoteNumber.value).length>0)
	{ 				
		var i = checkdigits(obj);		  	
	}     		    
 	if(i == true)
 	{ 		 	 
 		 searchSubmit();
 	}  		
}
function validate(obj)
{
	var myForm=document.getElementById('searchForm');
	if(document.getElementById('searchCRMOrder').value == "0")
	{
		alert('please enter value greater than 0');					
		myForm.searchCRMOrder.value = "";	
		myForm.searchCRMOrder.focus();						
		return false;           
	}
	if(document.getElementById('searchAccountNo').value == "0")
	{
		alert('please enter value greater than 0');					
		myForm.searchAccountNo.value = "";	
		myForm.searchAccountNo.focus();						
		return false;         
	}
	if(trim(myForm.searchCRMOrder.value).length>0)
	{ 
		  checkdigits(obj);			  					
		  return false; 	 	 
	}
	if(trim(myForm.searchAccountNo.value).length>0)
	{ 
		  checkdigits(obj);	
		  return false; 		  	 	 
	}		
}
function validation(obj)
{	
	var myForm=document.getElementById('searchForm');
	if(trim(myForm.searchAccountName.value).length>0)
	{		
		var i = ValidateTextField(obj,path,'Account Name');						
	}	
	if(i == undefined)
	{
		searchSubmit();
	}
}
function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	var crmOrderNo=myForm.searchCRMOrder.value;
	var accountNo=myForm.searchAccountNo.value;
	var accountName=myForm.searchAccountName.value;
	var quoteNumber=myForm.searchQuoteNumber.value;
	var source=myForm.searchSource.value;
	var fromDate=myForm.searchfromDate.value;
	var toDate=myForm.searchToDate.value;
	var orderType=myForm.searchOrderType.value;
	
	if((quoteNumber==null && orderType==null && source==null && crmOrderNo==null && accountNo == null  && accountName == null  && fromDate == null && toDate == null) 
		|| (trim(orderType)=="" && trim(crmOrderNo)=="" && trim(accountNo)=="" && trim(accountName) == "" && trim(source) == "" && trim(quoteNumber)=="" && trim(fromDate)=="")){
		
	return false;
	} 
	if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
	{
			alert("Please enter From Date");
			return true;
	}
	if((toDate == null || trim(toDate)== "" ) && (fromDate != null && trim(fromDate) != "" ))
	{
			alert("Please enter To Date");
			return true;
	}
	if(dateCompare(fromDate,toDate)==1)
	{			
			alert("From Date cannot be greater than To Date");
			return true;
	}	
	if(trim(myForm.searchCRMOrder.value).length>0)
	{ 
		var txt=document.getElementById('id_orderno_search');
		if (isNaN(txt.value) == true)
		{
			alert("Please Enter only Numeric Values!");
			txt.focus();
			return true;
		}
		if (parseInt(txt.value)==0 || parseInt(txt.value)< 0)
		{
			alert("Please Enter Value Greater Than 0!");
			txt.focus();
			return true;
		}				  		
	}
	if(trim(myForm.searchAccountNo.value).length>0)
	{ 
				
		var txt=document.getElementById('id_accountno_search');
		if (isNaN(txt.value) == true)
		{
			alert("Please Enter only Numeric Values!");
			txt.focus();
			return true;
		}
		if (parseInt(txt.value)==0 || parseInt(txt.value)< 0)
		{
			alert("Please Enter Value Greater Than 0!");
			txt.focus();
			return true;
		}		
		  		  	 	 
	}
	if(trim(myForm.searchQuoteNumber.value).length>0)
	{ 				
		var txt=document.getElementById('id_orderdate_search');
		if (isNaN(txt.value) == true)
		{
			alert("Please Enter only Numeric Values!");
			txt.focus();
			return true;
		}
		if (parseInt(txt.value)==0 || parseInt(txt.value)< 0)
		{
			alert("Please Enter Value Greater Than 0!");
			txt.focus();
			return true;
		}		
		  		  	 	 
	}
	if(trim(myForm.searchAccountName.value).length>0)
	{		
		var txt=document.getElementById('id_accountname_search');					
		if(txt.value != ""||txt.value!=null)
		{
			var iChars = "@#-`~!$%^&*()+=[]\\\';,/{}|\":<>?";
			var arr=new Array();
			arr=iChars.split(",");
			for (var i = 0; i < txt.value.length; i++)
			{
		  		if (iChars.indexOf(txt.value.charAt(i)) != -1) 
		  		{
			  		alert ("Only AlphaNumeric Character are allowed ");
					txt.focus();	
					return  true;
		  		}
		  	}
		}						
	}			
}
function onloadMessage()
{
	<logic:present name="PONUMBEROUT">
		<logic:notEqual name="PONUMBEROUT" value="-1">
		
			var orderNo=<bean:write name="PONUMBEROUT"/>;		
			if(orderNo==-100){
					alert('Error:Region or Zone is NA with this Order,please create New Order,Order Copy Failed!!');
					return false;					
			}	
			if(orderNo==-101){
					alert('Error:Service not allowed for new order entry,Order Copy Failed!!');
					return false;					
			}	
			if(confirm("Order Created Successfully with ORDERNO="+orderNo+" !!Do you want to view?"))
			{
				var modeValue="editON";
				var accessMode;      			      
      			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");    
      			var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleID,userId);
      			var shortCode = jsonrpc.processes.getShortCode(roleID);
      
      			if(orderDetails.list.length==0)
	  			{
					alert('Order Does Not Exist \n Please Try Valid Order No');
					return false;
	  			}
	  			else if(orderDetails.list.length>0)
	  			{
					stage=orderDetails.list[0].stageName;
					orderType=orderDetails.list[0].orderType;
					//orderNo=orderDetails.list[0].orderNumber;
					accessMode=orderDetails.list[0].mode;
	  			}
	  			
	  			if(orderType=="New" && ( stage=="New" || stage=="NEW"))
				{
					/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
		      		document.forms[0].submit();*/
		      		var incompleteNewOrderForm=createDummyForm("<%=request.getContextPath()%>/NewOrderAction.do");
					attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
					attachHiddenField(incompleteNewOrderForm,"draft","1");
					attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
					incompleteNewOrderForm.submit();
				}
				else if(orderType=="New" && (stage=="AM" ||stage=="PM" ||stage=="COPC" ||stage=="SED" ||stage=="Partial Publish" ||stage=="Published" ||stage=="Completed" ||stage=="M6_CANCELLED"  || stage == "CANCELLED"))
				{
					var incompleteNewOrderForm=createDummyForm("<%=request.getContextPath()%>/NewOrderAction.do");
					if(shortCode==stage)
					{
						//Added By Saurabh To Check AM or PM Roled Id
						if(stage=="AM" ||stage=="PM")
						{
							if(accessMode=="Write")
							{
								/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
								document.forms[0].submit();*/
								attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
								attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
								attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
								incompleteNewOrderForm.submit();
							}
							else if(accessMode=="Read")
							{
								var modeValue="viewMode";
								/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
								document.forms[0].submit();*/
								attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
								attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
								attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
								incompleteNewOrderForm.submit();
							}
						}
						else
						{
							/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
							document.forms[0].submit();*/
							attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
							attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
							attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
							incompleteNewOrderForm.submit();
						}	
						//End By Saurabh
					}
					else
					{
						var modeValue="viewMode";
						/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit();*/
						attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
						attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
						attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
						incompleteNewOrderForm.submit();
					}
				}
				else if(orderType=="New" && stage=="Billing Trigger")
				{
					var incompleteNewOrderForm=createDummyForm("<%=request.getContextPath()%>/NewOrderAction.do");
					if(shortCode=="COPC")
					{
						/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
			      		document.forms[0].submit();*/
			      		attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
						attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
						attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
						incompleteNewOrderForm.submit();
					}
					else
					{
						var modeValue="viewMode";
						/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit();*/
						attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
						attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
						attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
						incompleteNewOrderForm.submit();
			      	}
				}
				else if(orderType=="Change" && ( stage=="New" || stage=="NEW"))
				{
					var incompleteChangeOrderForm=createDummyForm("<%=request.getContextPath()%>/ChangeOrderAction.do");
					attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
					attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
					attachHiddenField(incompleteNewOrderForm,"draft","1");
					incompleteChangeOrderForm.submit();
					/*document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
		      		document.forms[0].submit();*/
				}
				else if(orderType=="Change" && (stage=="AM" ||stage=="PM" ||stage=="COPC" ||stage=="SED" ||stage=="Partial Publish" ||stage=="Published" ||stage=="Completed" ||stage=="M6_CANCELLED"  || stage == "CANCELLED"))
				{
					var incompleteChangeOrderForm=createDummyForm("<%=request.getContextPath()%>/ChangeOrderAction.do");
					
					if(shortCode==stage)
					{
						//Added By Saurabh To Check AM or PM Roled Id
						if(stage=="AM" ||stage=="PM")
						{
							if(accessMode=="Write")
							{
								/*document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
								document.forms[0].submit();*/
								attachHiddenField(incompleteChangeOrderForm,"method","incompleteOrder");
								attachHiddenField(incompleteChangeOrderForm,"orderNo",orderNo);
								attachHiddenField(incompleteChangeOrderForm,"modeName",modeValue);
								incompleteChangeOrderForm.submit();
							}
							else if(accessMode=="Read")
							{
								var modeValue="viewMode";
								/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
								document.forms[0].submit();*/
								attachHiddenField(incompleteChangeOrderForm,"method","incompleteOrder");
								attachHiddenField(incompleteChangeOrderForm,"orderNo",orderNo);
								attachHiddenField(incompleteChangeOrderForm,"modeName",modeValue);
								incompleteChangeOrderForm.submit();
							}
						}
						else
						{
							/*document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
							document.forms[0].submit();*/
							attachHiddenField(incompleteChangeOrderForm,"method","incompleteOrder");
								attachHiddenField(incompleteChangeOrderForm,"orderNo",orderNo);
								attachHiddenField(incompleteChangeOrderForm,"modeName",modeValue);
							incompleteChangeOrderForm.submit();
						}
						//End By Saurabh
					}
					else
					{
						var modeValue="viewMode";
						/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit();*/
						var incompleteNewOrderForm=createDummyForm("<%=request.getContextPath()%>/NewOrderAction.do");
						attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
						attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
						attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
						incompleteNewOrderForm.submit();
					}
			
				}
				else if(orderType=="Change" && stage=="Billing Trigger")
				{
					if(shortCode=="COPC")
					{
						/*document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit();*/
						var incompleteChangeOrderForm=createDummyForm("<%=request.getContextPath()%>/ChangeOrderAction.do");
						attachHiddenField(incompleteChangeOrderForm,"method","incompleteOrder");
						attachHiddenField(incompleteChangeOrderForm,"orderNo",orderNo);
						attachHiddenField(incompleteChangeOrderForm,"modeName",modeValue);
						incompleteChangeOrderForm.submit();
					}
					else
					{
						var modeValue="viewMode";
						/*document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						document.forms[0].submit();*/
						var incompleteNewOrderForm=createDummyForm("<%=request.getContextPath()%>/NewOrderAction.do");
						attachHiddenField(incompleteNewOrderForm,"method","incompleteOrder");
						attachHiddenField(incompleteNewOrderForm,"orderNo",orderNo);
						attachHiddenField(incompleteNewOrderForm,"modeName",modeValue);
						incompleteNewOrderForm.submit();
						
					}
				} 
			}			
		</logic:notEqual> 	
	</logic:present>	
}
path='<%=request.getContextPath()%>';
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
</head>
<body onload="onloadMessage();">
<jsp:include page="../../header.jsp" flush="true"/>
<div id="shadow" class="opaqueLayer"><font color="white" size="8" id="id_shadow_font"> Please wait....</font>
</div>
	<html:form action="/universalWorkQueueAction" method="post" styleId="searchForm">
	<div style="overflow:auto; height: 100%;width: 100% " >
	<table border="0" align="center" cellpadding="0" cellspacing="0" id="id_tab_MsgHeader" style="display: none;" width="80%">
		<tr>
			<td class="head">&nbsp;Create New Order with Existing Orders</td>
		</tr class="grayBg">	
		<tr>
			<td align="right"><img src="./gifs/top/home.gif" onClick="goToHome();"></img></td>
		</tr>
	</table>
	<table border="0" align="center" cellpadding="0" cellspacing="0" id="id_tab_MainHeader">
		<tr>
			<td class="head">&nbsp;Create New Order with Existing Orders</td>
		</tr>
		<tr valign="middle" class="grayBg">			
			<td valign="bottom" align="center">
			<%String []colors=new String[]{"normal","lightBg"}; %>
	<bean:define id="formBean" name="UniversalWorkQueueLogicalFormBean"></bean:define>
	<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
	<html:hidden name="formBean" property="pagingSorting.pageNumber"/>
	<html:hidden name="formBean" property="pagingSorting.sortByColumn"/>
	<html:hidden name="formBean" property="pagingSorting.sortByOrder"/>	
			<html:hidden property="ponumber" styleId="hdnPoNumber"/>
			<html:hidden property="checkedOrderNo"/>
			<bean:define id="pagingBean" name="formBean"></bean:define>					
				<table border="1" class="tab2" cellpadding="0" cellspacing="0" align="center" width="100%" >
					<tr>
						<td class="tab2" colspan="20">
			<%String  currPageNumber =String.valueOf(((UniversalWorkQueueFormBean)formBean).getPagingSorting().getPageNumber());
									  String  maxPageNumber =String.valueOf(((UniversalWorkQueueFormBean)formBean).getPagingSorting().getMaxPageNumber());
			%>					
			<jsp:include flush="true" page="/paging.jsp">
					<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
					<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
			</jsp:include>
		 </td>
					 	<td>
					 		<a href="#"><img border="1" src="${pageContext.request.contextPath}/gifs/clear2011.png"
							alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();" /></a>
							<a href="#"><img onclick="searchSubmit();" src="${pageContext.request.contextPath}/gifs/search2011.png"
							title="search" width="18" height="20"></a>
							<img src="./gifs/top/home.gif" onClick="goToHome();"></img>
						</td>
	</tr>	
   </table>
			</td>
		</tr>		
		<tr>
			<td>
				<table border="0" align="center" cellpadding="0" cellspacing="0"
					class="tab2" width="99.5%">
					<tr class="lightBgWihtoutHover">
						<td></td>
						<td widtd="5%">CRM Order No</td>
						<td widtd="5%">Order Type</td>
						<td>Order Date</td>
						<td>Account No</td>
						<td>Account Name</td>
						<td>Source</td>
						<td>Quote No</td>
						<td></td>
					</tr>
					<tr class="lightBgWihtoutHover">						
						<td width="5%"></td>
						<td align="center"><html:text property="searchCRMOrder"
							maxlength="10" size="10" 
				styleId="id_orderno_search"
				onkeydown="if(event.keyCode == 13){onEnterValidation(this)};"/>
					
			</td>
			
						<td align="center"><html:select property="searchOrderType"
							styleId="id_ordertype_search" style="width:100px"
							onkeydown="if (event.keyCode == 13) return searchSubmit();">
							<html:option value="">--Select--</html:option>
							<html:optionsCollection name="listOrderType" label="orderType"
								value="orderType" />
							</html:select>
			</td>
						<td align="center">
				<table border="0">
					<tr>
									<td>From Date:<html:text property="searchfromDate" size="8"
							onkeypress="onPressEnterSearch(event)" readonly="true"
										styleId="dateFrom" ></html:text> <font size="1"> <a
							href="javascript:show_calendar(searchForm.searchfromDate);"
							title="pick date here" class="borderTabCal"><img
							src="<%=request.getContextPath()%>/images/cal.gif" border="0px"
							alt="pick date here"></a> </font></td>
					</tr>
					<tr>
									<td>To Date:&nbsp;&nbsp;&nbsp;<html:text property="searchToDate" size="8" 
										onkeypress="onPressEnterSearch(event)" readonly="true" 
										styleId="dateTo" ></html:text> <font size="1"> <a
							href="javascript:show_calendar(searchForm.searchToDate);"
							title="pick date here" class="borderTabCal"><img
							src="<%=request.getContextPath()%>/images/cal.gif" border="0px"
							alt="pick date here"></a> </font></td>
					</tr>
				</table>
				</td>
						<td align="center"><html:text property="searchAccountNo"
							maxlength="20" size="8" 
							styleId="id_accountno_search"
							onkeydown="if(event.keyCode == 13){onEnterValidation(this)};"
							></html:text>
						</td>
						<td align="center"><html:text property="searchAccountName"
							maxlength="20" size="15" 
							styleId="id_accountname_search"
							onkeydown="if (event.keyCode == 13){validation(this)};"
							></html:text>
						</td>
						<td align="center"><html:select property="searchSource"
							styleId="searchSourceNameId" style="width:75px"
					
					onkeydown="if (event.keyCode == 13) return searchSubmit();">
					<html:option value="">--Select--</html:option>
					<html:optionsCollection name="listSourceName"
					label="searchSourceName" value="searchSourceName" />
					</html:select>
				</td>
						<td align="center"><html:text property="searchQuoteNumber"
							maxlength="8" size="8" 
							styleId="id_orderdate_search"
				
							onkeydown="if(event.keyCode == 13){onEnterValidation(this)};"
							 />
						</td>
		</tr>
   		<logic:equal value="0" property="isBillingOrder" name="UniversalWorkQueueLogicalFormBean">						
			<logic:empty name="formBean" property="universalWorkQueueList">
				<tr class="normal">
					<td colspan="12" align="center">No Records Found</td>
				</tr>						
			</logic:empty>
			<logic:notEmpty name="universalWorkQueueList" scope="request">
											<tr class="normal" height="20px">
												<td align="center" widtd="5%" class="headingFont"><b>S. No.</b></td>
												<td align="left" widtd="5%">						
													<a href="#" onClick="sortSubmit('ORDERNO')" title="Sorting"><b>View Order</b></a>
												</td>
												<td align="left" widtd="5%"><b>					
													Order Type</b>						
												</td>					
												<td align="center"><b>						
													Order Date</b>						
												</td>			
												<td >
													<a href="#" onClick="sortSubmit('ACCOUNTID')" title="Sorting"><b>Account No</b></a>
												</td>
												<td align="center">
													<a href="#" onClick="sortSubmit('ACCOUNTNAME')" title="Sorting"><b>Account Name</b></a>
												</td>
												<td align="center"><b>
													Source Name</b>
												</td>
												<td align="center"><b>
													<a href="#" onClick="sortSubmit('QUOTENO')" title="Sorting"><b>Quote No</b></a>
												</td>
												<td align="center"><b>						
													Create New Order</b>				
												</td>
				</tr>					
											<tr>
											<td colspan="20">
												<div class="scrollWithAutoScroll1" class="head"
													style="width:100% ">
													<table cellSpacing="0" cellPadding="0" width="100%" border="0"
														id="tbl2" class="tab2" align="center">					
				<logic:iterate id="row"	name="universalWorkQueueList" scope="request" indexId="index1">	
					<tr class="<%=colors[index1.intValue()%2]%>">								
						<%	int count = ((index1.intValue()) + 1);%>						
															<td width ="4%" align="center"><%=index1.intValue()+1 %>.</td>
															<td width ="9%" >
																<a href="javascript:fnViewOrder('<bean:write name="row" property="searchCRMOrder" />','
																<bean:write name="row" property="searchOrderType" />');">
								<font color="Brown"> 
										<bean:write name="row" property="searchCRMOrder" />
								</font>
							</a>
						</td>
															<td width ="11%" >
							<bean:write name="row" property="searchOrderType" />
						</td>
															<td width ="17%" align="center">
							<bean:write name="row" property="searchfromDate" />
						</td>
															<td width ="8%" >
							<bean:write name="row" property="searchAccountNo" />
						</td>
															<td width ="13%" >
							<bean:write name="row" property="searchAccountName" />
						</td>
															<td width ="9%" >
							<bean:write name="row" property='searchSource' />
						</td>
															<td width ="8%" >
							<bean:write name="row" property='searchQuoteNumber' />
						</td>
															<td width ="6%" >
																<input type="button" value="Create Ord" onclick="javascript:createOrder('<bean:write name="row" property="searchCRMOrder" />')" />
						</td>
					</tr>
				</logic:iterate>
												</table>
											</div>
										</td>	
									</tr>	
			</logic:notEmpty>
		</logic:equal>  				
   </table>	
					</div>
				</td>
		</tr>
	</table>	
	</td>
</tr>
</table>
</div>					   
</html:form>
</body>
</html>