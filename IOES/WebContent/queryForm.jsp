<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	SAURABH SINGH	23-Oct-11	00-05422		Opening New and Change Order in New Window-->
<!--[002]	ANIL KUMAR	18-May-15		20141219_R2_020936		Added new column COPC Approval Date-->
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
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>Query Form</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="./gifs/style.css" type="text/css" rel="stylesheet">
<link href="./gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/Calender/jquery-ui.min.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script> 
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script language="javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<%
	HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
	HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
	UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>
<script language="javascript">
var roleID=<%=objUserDto.getUserRoleId()%>;
var userId = "<%=objUserDto.getUserId() %>";
var employeeId = "<%=objUserDto.getEmployeeId() %>";
var rgnLst = "<%=objUserDto.getRegionId() %>";//by kalpana for copc region change HYPR22032013003
path='<%=request.getContextPath()%>';
function goToHome()
{
	document.forms[0].action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	document.forms[0].submit();
}
//Start[001]
function fnViewOrder(orderNo,ordertype,stageName,regionId)//by kalpana for copc region change HYPR22032013003
{	       		
	  var accessMode;
	  var createdBy;
	  var userIDLogged;
	  var userNameLogged;
	  var roleNameLogged;
      var modeValue="editON";
      var ordType=ordertype.toUpperCase();
      var stage=stageName.toUpperCase();      
      var regionArray = new Array();
      var foundReg="false";
      var viewModeforSingleApproval=false;
      jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
      var shortCode = jsonrpc.processes.getShortCode(roleID);
      var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleID,userId);
      if(orderDetails.list.length>0)
		{
			accessMode=orderDetails.list[0].mode;
			createdBy=orderDetails.list[0].createdBy;
			userIDLogged=orderDetails.list[0].userID;
    		userNameLogged=orderDetails.list[0].userName;
			roleNameLogged=orderDetails.list[0].roleName;
			viewModeforSingleApproval=orderDetails.list[0].singleApprovalMode;
		}
      if(userIDLogged!=null && userIDLogged!='' )
      {    
     		if(!confirm("User "+userNameLogged+" [User ID "+userIDLogged+ "] has already Logged on this Order with Role "+roleNameLogged+""+'\n'+" Do you continue viewing in View Order"))
     		{
     			return ;
     		}
      }
      if(viewModeforSingleApproval==true){
    	  var modeValue="viewMode";
		  var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
		  window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
		  return;
    	  
      }
      if((ordType=="NEW") && (stage=="NEW"))
	  {
			if(shortCode=="AM")
			{
				if(createdBy==employeeId)
				{
					var modeValue="editOFF";
					var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
					window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
				}
				else
				{
					var modeValue="viewMode";
					var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
					window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
				}
			}else if(shortCode=="SALES_COORDINATOR")			
			{
				if(createdBy==employeeId)
				{
					var modeValue="editOFF";
					var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
					window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
				}
				else
				{
					var modeValue="viewMode";
					var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
					window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
				}
			}
			else
			{
				var modeValue="viewMode";
	      		var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo+"&modeName="+modeValue;
				window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
	      	}	
	  }
      else if((ordType=="NEW") && (stage=="AM" ||stage=="SALES_COORDINATOR" ||stage=="PM" ||stage=="COPC" ||stage=="SED" ||stage=="PARTIAL%20PUBLISH" ||stage=="COMPLETED" ||stage=="M6_CANCELLED" ||stage=="PUBLISHED"  || stage == "CANCELLED" || stage == "PARTIAL%20INITIATED" ))
	  {
		  	
			if(shortCode==stage)
			{
				//Added By Saurabh To Check AM or PM Roled Id
				if(stage=="AM")
				{
					if(createdBy==employeeId)
					{
						var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					}
					else
					{
						var modeValue="viewMode";
						var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					}
				}else if(stage=="SALES_COORDINATOR")
				{
					if(createdBy==employeeId)
					{
						var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					}
					else
					{
						var modeValue="viewMode";
						var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					}
				}
				else if(stage=="PM")
				{
					if(accessMode=="Write")
					{
						var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					}
					else if(accessMode=="Read")
					{
						var modeValue="viewMode";
						var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					}
				}else if(stage=="COPC"){
					regionArray=rgnLst.split(",");
					for(i=0;i<regionArray.length;i++){
						if(regionId==regionArray[i]){
							foundReg="true";
							break;
						}else{
							foundReg="false";
						}
					}		
					 	if(foundReg=="true"){
						 	var modeValue="editON";
							var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
							window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					 	}else{
					 		var modeValue="viewMode";
							var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
							window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					 	}	
				}
				else
				{
					var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
					window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
				}
			}
			else if(stage == "PARTIAL%20INITIATED"){
				var modeValue="";
				var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
			}
			else
			{
				var modeValue="viewMode";
				var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
			}
	  }
	  else if((ordType=="NEW") && stage=="BILLING%20TRIGGER")
	  {	  	
			if(shortCode=="COPC")
			{
	      		regionArray=rgnLst.split(",");
					for(i=0;i<regionArray.length;i++){
						if(regionId==regionArray[i]){
							foundReg="true";
							break;
						}else{
							foundReg="false";
						}
					}		
					 	if(foundReg=="true"){
						 	var modeValue="editON";
							var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
							window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					 	}else{
					 		var modeValue="viewMode";
							var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
							window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					 	}	//var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
			}
			else
			{
				var modeValue="viewMode";
				var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
	      	}
	   }
	   
	    else if((ordType=="CHANGE") && (stage=="NEW"))
		{
			if(shortCode=="AM")
			{
				if(createdBy==employeeId)
				{
					var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
					window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
				}
				else
				{
					var modeValue="viewMode";
					var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
					window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
				}
			}else if(shortCode=="SALES_COORDINATOR")
			{
				if(createdBy==employeeId)
				{
					var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
					window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
				}
				else
				{
					var modeValue="viewMode";
					var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
					window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
				}
			}
			else
			{
				var modeValue="viewMode";
      			var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
      		}	
		}
		else if((ordType=="CHANGE") && (stage=="AM" ||stage=="SALES_COORDINATOR" ||stage=="PM" ||stage=="COPC" ||stage=="SED" ||stage=="PARTIAL%20PUBLISH"  ||stage=="COMPLETED" ||stage=="M6_CANCELLED" ||stage=="PUBLISHED"  || stage == "CANCELLED" || stage == "PARTIAL%20INITIATED" ))
		{
			if(shortCode==stage)
			{
				//Added By Saurabh To Check AM or PM Roled Id
				if(stage=="AM")
				{
					if(createdBy==employeeId)
					{
						var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					}
					else
					{
						var modeValue="viewMode";
						var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					}
				}
				else if(stage=="SALES_COORDINATOR")
				{
					if(createdBy==employeeId)
					{
						var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					}
					else
					{
						var modeValue="viewMode";
						var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					}
				}
				else if(stage=="PM")
				{
					if(accessMode=="Write")
					{
						var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					}
					else if(accessMode=="Read")
					{
						var modeValue="viewMode";
						var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
						window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					}
				}else if(stage=="COPC"){
					regionArray=rgnLst.split(",");
					for(i=0;i<regionArray.length;i++){
						if(regionId==regionArray[i]){
							foundReg="true";
							break;
						}else{
							foundReg="false";
						}
					}		
					 	if(foundReg=="true"){
						 	var modeValue="editON";
							var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
							window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					 	}else{
					 		var modeValue="viewMode";
							var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
							window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
					 	}	
				}
				else
				{
					var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
					window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
				}
				//End By Saurabh
			}
			else if(stage == "PARTIAL%20INITIATED"){
				var modeValue="";
				var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
			}
			else
			{
				var modeValue="viewMode";
				var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
			}
			
		}
		
		else if((ordType=="CHANGE") && stage=="BILLING%20TRIGGER"){  
			if(shortCode=="COPC"){
				regionArray=rgnLst.split(",");
				for(i=0;i<regionArray.length;i++){
					if(regionId==regionArray[i]){
						foundReg="true";
						break;
					}else{
							foundReg="false";
					}
				}		
				if(foundReg=="true"){
					var modeValue="editON";
					var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
					window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
				}else{
					 var modeValue="viewMode";
					 var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
					 window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
				}	
				//var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
			}else if(stage == "PARTIAL%20INITIATED"){ 
				var modeValue="";
				var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
			}else{
				var modeValue="viewMode";
				//document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				//document.forms[0].submit();
				var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
				window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
			}
		}	   
}
//End[001]
function isBlank()
{	
	var w = document.forms[0].queryOptions.selectedIndex;
    var selected_value = document.forms[0].queryOptions.options[w].value;
    var expectedValue=document.getElementById('hdnExpectedValue').value;
    
    if(document.forms[0].queryOptions.value=="0")
    {
    	alert('Please select a Query Option!!!');
    	document.getElementById('queryOptions').focus();
    	return true;
    }
    if(document.getElementById('txtValueID').value=="")
    {
    	//if select CLEP order then skip validation for text box entry
    	if(selected_value==15){
    		document.getElementById('txtValueID').value=0;
			return false;
		}else{
			alert('Please enter value!');
    		document.getElementById('txtValueID').focus();
    		return true;
		}
    }
    if(expectedValue=="NUMERIC")
    {
    	var txt=document.getElementById('txtValueID');
    	
		if (parseInt(txt.value)==0 || parseInt(txt.value)< 0)
		{
			alert("Sorry! Please Enter Value Greater Than 0!");
			txt.focus();
			return true;
		}		
    }
               
}
function ValidateNumericField(val) {
	
   var reg =/^([0-9]+)$/;
   if(reg.test(val) == false) {
      return false;
   }
   return true;
}
function goToExecute()
{
	if(!isBlank())
	{
	        var w = document.forms[0].queryOptions.selectedIndex;
     		var selected_value = document.forms[0].queryOptions.options[w].value;
     		//Queryform Numeric field validation For OrderNo,AccountNo,CustLSINO,LSINO,LineNO,Serviceid By Nagarjuna
     		if (selected_value=='1'||selected_value=='2'||selected_value=='4'||selected_value=='5'||selected_value=='8'||selected_value=='12'){
	     		
	     		var i=ValidateNumericField(document.getElementById('txtValueID').value);
	     		if(i==false){
	     		alert('Please Enter Numeric values !!'); 
	     		return false;
	     		}
     		}
			//end Nagarjuna
		if(selected_value==2)
		{
			searchFromList('ACCOUNTID','QUERYFORMLIST');		
		}
		else if (selected_value==3)
		{
			searchFromList('ACCOUNTNAME','QUERYFORMLIST');		
		}
		else if (selected_value==6)
		{
			searchFromList('M6ORDERNO','QUERYFORMLIST');		
		}
		else if (selected_value==12)
		{
			searchFromList('SINO','QUERYFORMLIST');		
		}
		else
		{
			searchFromList('ORDERNO','QUERYFORMLIST');		
		}

		
	}
}
function DrawQueryFormTable()
{    	
	 var frm=document.getElementById('searchForm');		  
	 var mytable = document.getElementById('queyValueListTabId');	
	 var iCountRows = mytable.rows.length;
	 var w = document.forms[0].queryOptions.selectedIndex;
     var selected_value = document.forms[0].queryOptions.options[w].value;
     var selected = document.forms[0].queryOptions.options[w];
    
     if( trim(document.getElementById('txtValueID').value).length>0)
	{
	
			
		 i = ValidateTextField(document.getElementById('txtValueID'),path,'Selected Value');	
	
			if(i == false)
		{
			return false;
		}
		
		
					
	}
	
     
     
     	
	 for (i = iCountRows; i >=1; --i)
	 {	 	
		mytable.deleteRow(0);
	 }   
	 	 		    
	 document.getElementById('txtPageNumber').value= pageNumber;
	 sync();	
	    	   	   	   
	 var jsData =
				{				
					startIndex:startRecordId,
					endIndex:endRecordId,
					sortBycolumn:sortByColumn,
					sortByOrder:sortByOrder,	
					queryNameID:selected_value,
					queryFormValue:document.getElementById('txtValueID').value
				};	
				jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
				var listQueryValue=jsonrpc.processes.getQueryFormValueList(jsData);
				iTotalLength=listQueryValue.list.length;
				
				if(iTotalLength !=0)
				{
					iNoOfPages = listQueryValue.list[0].maxPageNo;
				}		
	        	else
				{     
		        	iNoOfPages=1;
				}		
				document.getElementById('txtMaxPageNo').value=iNoOfPages;	
				if((listQueryValue!=null) && (iTotalLength !=0))
				{
					document.getElementById('pagingSortingTabId').style.display='block';
					var tblRow=document.getElementById('queyValueListTabId').insertRow();
					tblRow.className="grayBg";
					
					tblcol=tblRow.insertCell();
					tblcol.align = "center";
					tblcol.vAlign="top";
					str="SR.NO.";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					tblcol=tblRow.insertCell();
					tblcol.align = "center";
					tblcol.vAlign="top";
					str="<a href='#' onClick=sortOrder('ORDERNO','QUERYFORMLIST')>ORDERNO</a>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					tblcol=tblRow.insertCell();
					tblcol.align = "center";
					tblcol.vAlign="top";
					str="<a href='#' onClick=sortOrder('ACCOUNTID','QUERYFORMLIST')>ACCOUNTID</a>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					tblcol=tblRow.insertCell();
					tblcol.align = "center";
					tblcol.vAlign="top";
					str="<a href='#' onClick=sortOrder('ACCOUNTNAME','QUERYFORMLIST')>ACCOUNTNAME</a>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					tblcol=tblRow.insertCell();
					tblcol.align = "center";
					tblcol.vAlign="top";
					str="<a href='#' onClick=sortOrder('STAGE','QUERYFORMLIST')>STAGE</a>";
					CellContents = str;
					tblcol.innerHTML = CellContents;										
					
					tblcol=tblRow.insertCell();
					tblcol.align = "center";
					tblcol.vAlign="top";
					str="<a href='#' onClick=sortOrder('ORDERTYPE','QUERYFORMLIST')>ORDERTYPE</a>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					tblcol=tblRow.insertCell();
					tblcol.align = "center";
					tblcol.vAlign="top";
					str="<a href='#' onClick=sortOrder('SINO','QUERYFORMLIST')>SERVICE NO</a>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					tblcol=tblRow.insertCell();
					tblcol.align = "center";
					tblcol.vAlign="top";
					str="<a href='#' onClick=sortOrder('M6ORDERNO','QUERYFORMLIST')>M6.ORDER.NO</a>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					//Start[002]
					tblcol=tblRow.insertCell();
					tblcol.align = "center";
					tblcol.vAlign="top";
					str="COPC APPROVAL DATE";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					//End[002]
					if(selected_value == '14')
					 {			
						tblcol=tblRow.insertCell();
						tblcol.align = "center";
						tblcol.vAlign="top";
						str="<a href='#' onClick=sortOrder('MIGRATEDORDERS','QUERYFORMLIST')>MIGRATED ORDERS</a>";
						CellContents = str;
						tblcol.innerHTML = CellContents;
					}	
					var colors=new Array("normal","lightBg");
					for (i = 0 ; i <iTotalLength; i++) 
					{	
						var colorValue=parseInt(i)%2;	 
						var tblRow2=document.getElementById('queyValueListTabId').insertRow();
						tblRow2.className=colors[colorValue];
											
						tblcol=tblRow2.insertCell();
						tblcol.align = "center";
						tblcol.vAlign="top";
						str=(i+1);
						CellContents = str;
						tblcol.innerHTML = CellContents;						
						
						tblcol=tblRow2.insertCell();
						tblcol.align = "center";
						tblcol.vAlign="top";												
						str="<a href='#' onClick=fnViewOrder('"+escape(listQueryValue.list[i].orderNo)+"','"+escape(listQueryValue.list[i].orderType)+"','"+escape(listQueryValue.list[i].stageName)+"','"+escape(listQueryValue.list[i].regionId)+"')><font color='brown'>"+listQueryValue.list[i].orderNo+"</font></a>";												
						CellContents = str;
						tblcol.innerHTML = CellContents;
						
						tblcol=tblRow2.insertCell();
						tblcol.align = "center";
						tblcol.vAlign="top";
						str=listQueryValue.list[i].accountID;
						CellContents = str;
						tblcol.innerHTML = CellContents;
						
						tblcol=tblRow2.insertCell();
						tblcol.align = "center";
						tblcol.vAlign="top";
						str=listQueryValue.list[i].accountName;
						CellContents = str;
						tblcol.innerHTML = CellContents;
												
						tblcol=tblRow2.insertCell();
						tblcol.align = "center";
						tblcol.vAlign="top";
						str=listQueryValue.list[i].orderStageAnnotationName;
						CellContents = str;
						tblcol.innerHTML = CellContents;												
												
						tblcol=tblRow2.insertCell();
						tblcol.align = "center";
						tblcol.vAlign="top";
						str=listQueryValue.list[i].orderType;
						CellContents = str;
						tblcol.innerHTML = CellContents;
						
						tblcol=tblRow2.insertCell();
						tblcol.align = "center";
						tblcol.vAlign="top";
						if(trim(listQueryValue.list[i].serviceIdString) == ""){
							str="&nbsp;&nbsp;";
						}else{
							str=listQueryValue.list[i].serviceIdString;
						}						
						CellContents = str;
						tblcol.innerHTML = CellContents;
						
						tblcol=tblRow2.insertCell();
						tblcol.align = "center";
						tblcol.vAlign="top";						
						if(trim(listQueryValue.list[i].m6OrderNoString) == ""){
							str="&nbsp;&nbsp;";
						}else{
							str=listQueryValue.list[i].m6OrderNoString;
						}						
						CellContents = str;
						tblcol.innerHTML = CellContents;	
						//Start[002]							
						tblcol=tblRow2.insertCell();
						tblcol.align = "center";
						tblcol.vAlign="top";						
						if(trim(listQueryValue.list[i].copcApproveDate) == ""){
							str="&nbsp;&nbsp;";	
						}else{
							str=listQueryValue.list[i].copcApproveDate;	
						}						
						CellContents = str;
						tblcol.innerHTML = CellContents;
						//End[002]
						if(selected_value == '14' &&  listQueryValue.list[i].serviceTypeId ==21)
						{
						tblcol=tblRow2.insertCell();
						tblcol.align = "center";
						tblcol.vAlign="top";												
						str="<a href='#' onClick=popMigrationOrders('dccolo','"+escape(listQueryValue.list[i].orderNo)+"')><font color='brown'>Migrated Order</font></a>";												
						CellContents = str;
						tblcol.innerHTML = CellContents;
						}
						if(selected_value == '14' &&  listQueryValue.list[i].serviceTypeId ==4)
						{
						tblcol=tblRow2.insertCell();
						tblcol.align = "center";
						tblcol.vAlign="top";												
						str="<a href='#' onClick=popMigrationOrders('teleport','"+escape(listQueryValue.list[i].orderNo)+"')><font color='brown'>Migrated Order</font></a>";												
						CellContents = str;
						tblcol.innerHTML = CellContents;
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
				}
				else
				{
					document.getElementById('pagingSortingTabId').style.display='none';
					var tblRow=document.getElementById('queyValueListTabId').insertRow();
					tblRow.className="grayBg";
					
		    		tblcol=tblRow.insertCell();
					tblcol.align = "center";
					tblcol.vAlign="top";
					tblcol.colSpan = 8;
					str='NO RECORD FOUND'
					CellContents = str;
					tblcol.innerHTML = CellContents;
				}
    
}
function popMigrationOrders(ServiceType,orderNo) 
{
	if(ServiceType =='dccolo')
	{
		var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=fetchMigrationOrders&migrationOrderNo="+ orderNo +"&serviceType="+ServiceType+"";	
		window.showModalDialog(path,window,"status:false;dialogWidth:800px;dialogHeight:500px");
	}
	else
	{
	   var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=fetchMigrationOrders&migrationOrderNo="+ orderNo +"&serviceType="+ServiceType+"";		
		window.showModalDialog(path,window,"status:false;dialogWidth:800px;dialogHeight:500px");
	}
	
	    return false;
}
function onloadFieldDisable()
{
	document.getElementById('pagingSortingTabId').style.display='none';	
}
function getExpectedValue()
{
	var w = document.forms[0].queryOptions.selectedIndex;
    var selected_value = document.forms[0].queryOptions.options[w].value;
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var expectedValue=jsonrpc.processes.getExpectedValueForQueryForm(selected_value);	
	if(expectedValue==null)	{
		document.getElementById('hdnExpectedValue').value=expectedValue;
	}
	else	{
		document.getElementById('hdnExpectedValue').value=expectedValue.toUpperCase();
	}
	//if select CLEP Order then no require to input in text field all data will be display
	if(selected_value==15){
		document.getElementById('txtValueID').disabled=true;
		document.getElementById('txtValueID').className="inputDisabled";
		document.getElementById('txtValueID').value="";
	}else{
		document.getElementById('txtValueID').disabled=false;
		document.getElementById('txtValueID').className="inputBorder1";
		//document.getElementById('txtValueID').value="";
	}
		
}
</script>
</head>
<body onload="onloadFieldDisable();">
	<jsp:include page="header.jsp" flush="true"/>
	<div class="head"><span>Query Form</span></div>
	<html:form action="/universalWorkQueueAction" method="post" styleId="searchForm">
	<bean:define id="formBean" name="UniversalWorkQueueLogicalFormBean"></bean:define>	
	
		<table border="0" align="center" width="100%" class="tab2">
			<tr class=grayBg>
				<td align="center">Select Query Options:				
					<html:select property="queryOptions" styleClass="inputBorder1" onchange="getExpectedValue()">
						<logic:empty name="UniversalWorkQueueLogicalFormBean" property="queryOptionList">
							<html:option value="0">----------No Options----------</html:option>
						</logic:empty>
						<logic:notEmpty name="UniversalWorkQueueLogicalFormBean" property="queryOptionList">
							<html:option value="0">----------Select Query Options----------</html:option>
							<html:optionsCollection name="UniversalWorkQueueLogicalFormBean" 
								label="queryName" value="queryNameID" property="queryOptionList"/>
						</logic:notEmpty>
					</html:select>			
				</td>
				
				<td align="center">Enter value&nbsp;:		
   					<html:text property="valueID" size="30"  styleId="txtValueID"  styleClass="inputBorder1" onkeydown="if (event.keyCode == 13) return goToExecute();">   </html:text>
				</td>   							
				<td>
					<div class='searchBg1'><a href='#' onClick='goToExecute();'>GO</a></div>									
				</td>
				<td><img src="./gifs/top/home.gif" onClick="goToHome();"></img></td>
			</tr>						
		</table>
		
		<table width="100%"  id="pagingSortingTabId" border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
		<tr>
			<td align="center"><a href="#" id= "first" onclick="FirstPage('ORDERNO','QUERYFORMLIST')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('ORDERNO','QUERYFORMLIST')">Next</a></td>
			<td align="center">
				<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
				<input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2"/>Pages
			</td>
			<td align="center"><a href="#" id="prev" onclick="PrevPage('ORDERNO','QUERYFORMLIST')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('ORDERNO','QUERYFORMLIST')">Last</a></td>
		</tr>
		</table>
		
		<table border="0" bordercolor="#000000" align="center" width="100%" class="tab2" id="queyValueListTabId">
							
		</table>
		
		<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">			
			<td width="20%" colspan="5">&nbsp;</td>
		</tr>
		</table>
		
		<html:hidden property="methodName"/>		
		<input type="hidden" id="hdnExpectedValue"/>
	</html:form>
</body>
<script type="text/javascript">
	LoadToolTip()
</script>

</html>