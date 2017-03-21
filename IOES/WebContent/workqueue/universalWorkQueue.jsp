<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ROHIT VERMA	17-Feb-11	00-05422		Is Demo Column Added -->
<!--[002] 	 Vishwa 		7-NOV-2011	00-05422		Adding New Columns-->
<!--[003]	 SAURABH SINGH	23-Oct-11	00-05422		Opening New and Change Order in New Window-->
<!--[00011]   Neha Maheshwari    added service segment description-->
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
<title>:: Universal Work Queue: Integrated Order Entry System</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="./gifs/style.css" type="text/css" rel="stylesheet">
<link href="./gifs/freezeStyle.css" type="text/css" rel="stylesheet">


<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<script language="javascript" src="js/calendar.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script type="text/javascript" src="js/headerFreeze.js"></script>

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
<%
	HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
	HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
	UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>
function goToHome()
{
	document.forms[0].action="<%=request.getContextPath()%>/defaultAction.do?method=goToHomeAfterClosing";
	showLayer();
	document.forms[0].submit();
}



function fnViewOrder(orderNo,ordertype)
{
	 // document.forms[0].checkedOrderNo.value=orderNo;
      //document.forms[0].action="./viewOrderAction.do?methodName=viewOrder&orderNo="+orderNo;
	//---------[Start:Order once approved then open in view mode for same role]-----------------------------------      
	var modeValue="editON";      
    var roleID=<%=objUserDto.getUserRoleId()%>;
    var userId = "<%=objUserDto.getUserId() %>";
	var employeeId = "<%=objUserDto.getEmployeeId() %>";	
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
    var shortCode = jsonrpc.processes.getShortCode(roleID);
    var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleID,userId);
    var stage=orderDetails.list[0].stageName;
    
    //lawkush Start
	var userIDLogged=orderDetails.list[0].userID;
	var userNameLogged=orderDetails.list[0].userName;
    var  roleNameLogged=orderDetails.list[0].roleName;
      
      if(userIDLogged!=null && userIDLogged!='')
      {
      		if(!confirm("User "+userNameLogged+" [User ID "+userIDLogged+ "] has already Logged on this Order with Role "+roleNameLogged+""+'\n'+" Do you continue viewing in View Order"))
	      		{
	      			return ;
	      		}
      }
      
      //lawkush End
    
    if(stage != "Partial Publish" && stage != "Partial Initiated" ){
    	if(stage.toUpperCase()!= shortCode.toUpperCase()){
			modeValue="viewMode";
		}
	}
	//--------[End:Order once approved then open in view mode for same role]------------------------------------
	
    if(ordertype=="New")
	{
		//document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
		//showLayer();
		//document.forms[0].submit();
		//Start[003]
		var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
		window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
		//End[003]
	}
	if(ordertype=="Change")
    {
		//document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
		//showLayer();
		//document.forms[0].submit();
		//Start[003]
		if(modeValue=="viewMode"){
			var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
			window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
		}else{
			var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
			window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
		}
		//End[003]
	}
}
function searchSubmit()
{
	
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';	
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";
	if(isBlankForm()){		
		return;
	}
	
	if( trim(myForm.searchCRMOrder.value).length>0)
	{
			
		 i = ValidateTextField(myForm.searchCRMOrder,path,'Order No');	
			if(i == false)
		{
			return false;
		}
		
		
					
	}
	
	
	if( trim(myForm.searchAccountNo.value).length>0)
	{
	
	
	    i = ValidateTextField(myForm.searchAccountNo,path,'Account No');
	    
				if(i == false)
		{
			return false;
		}
						
	}
	
	
	if( trim(myForm.searchAccountName.value).length>0)
	{		
		i = ValidateTextField(myForm.searchAccountName,path,'Account Name');
	
			if(i == false)
		{
			return false;
		}
		
						
	}
	

	
	if( trim(myForm.searchPartyNumber.value).length>0)
	{		
		i = ValidateTextField(myForm.searchPartyNumber,path,'Party No');	
			if(i == false)
		{
			return false;
		}
					
	}
	
	 	

	myForm.action="<%=request.getContextPath()%>/universalWorkQueueAction.do?methodName=viewUniversalWorkQueue";
	showLayer();
	myForm.submit();
	
}

function searchClick()
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";

	if(!isBlankForm()){
		
		return;
	} 
	else {
		myForm.action="<%=request.getContextPath()%>/universalWorkQueueAction.do?method=viewUniversalWorkQueue";
		showLayer();
		myForm.submit(); 
	}
}

function sortSubmit(sortBy)
{
	myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	//myform.reset();
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
     	myForm.action="<%=request.getContextPath()%>/universalWorkQueueAction.do?methodName=viewUniversalWorkQueue";
		showLayer();
	myForm.submit();
	
}
function pagingSubmit(val)
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	//setFormBean(myform);
	//myform.reset();
	myForm.elements["pagingSorting.pageNumber"].value=val;	
     	myForm.action="<%=request.getContextPath()%>/universalWorkQueueAction.do?methodName=viewUniversalWorkQueue";
     	showLayer();
     	myForm.submit();     
}

function clearFields()
{	
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	myForm.searchCRMOrder.value='' ; 	
	myForm.searchAccountNo.value='' ; 
	myForm.searchAccountName.value='' ;	
	myForm.searchfromDate.value='' ;
	myForm.searchToDate.value='' ;	
	myForm.searchOrderType.value='' ;
	myForm.searchSubChangeType.value='' ;
	myForm.searchAmFromDate.value='' ;
	myForm.searchAmToDate.value='' ;
	myForm.searchPmFromDate.value='' ;
	myForm.searchPmToDate.value='' ;
	myForm.searchCopcFromDate.value='' ;
	myForm.searchCopcToDate.value='' ;
	myForm.searchRegion.value='' ;
	myForm.searchOrderStage.value='' ;
	myForm.searchDemoType.value='' ;
	myForm.searchPartyNumber.value='' ;
    //[00011] Start
	myForm.searchServiceSegment.value='';
    //[00011] End
	myForm.searchIndustrySegment.value='' ;
	myForm.searchCustomerSegment.value='' ;
	
}




function validation(obj)
{	
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	if(	document.getElementById('searchCRMOrder').value == "0")
	{
		alert('please enter value greater than 0');					
		myForm.searchCRMOrder.value = "";	
		myForm.searchCRMOrder.focus();						
		return false;           
	}
	if(	document.getElementById('searchAccountNo').value == "0")
	{
		alert('please enter value greater than 0');					
		myForm.searchAccountNo.value = "";	
		myForm.searchAccountNo.focus();						
		return false;         
	}		
	
	 searchSubmit();
	
		
	
}


function isBlankForm()
{
	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='false';
	var crmOrderNo=myForm.searchCRMOrder.value;
	var accountNo=myForm.searchAccountNo.value;
	var accountName=myForm.searchAccountName.value;
	var fromDate=myForm.searchfromDate.value;
	var toDate=myForm.searchToDate.value;
	var orderType=myForm.searchOrderType.value;
	var subChangeType =myForm.searchSubChangeType.value;
	var amFromDate =myForm.searchAmFromDate.value;
	var amToDate =myForm.searchAmToDate.value;
	var pmFromDate =myForm.searchPmFromDate.value;
	var pmToDate =myForm.searchPmToDate.value;
	var copcFromDate =myForm.searchCopcFromDate.value;
	var copcToDate =myForm.searchCopcToDate.value;
	var region =myForm.searchRegion.value;
	var orderStage =myForm.searchOrderStage.value;
	var demoType =myForm.searchDemoType.value;
	var partyNo =myForm.searchPartyNumber.value;
    //[00011] Start
	var serviceSegment =myForm.searchServiceSegment.value;
    //[00011] End
	var industrySegment =myForm.searchIndustrySegment.value;
	var customerSegment =myForm.searchCustomerSegment.value;
	

	if((orderType==null && crmOrderNo==null && accountNo == null  && accountName == null  && fromDate == null && toDate == null && subChangeType==null 
		&& amFromDate==null && amToDate==null && pmFromDate==null && pmToDate==null && copcFromDate == null  && copcToDate == null  && region == null && orderStage == null 
		&& demoType==null && partyNo==null && serviceSegment==null && industrySegment==null && customerSegment==null) 
		|| (trim(orderType)=="" && trim(crmOrderNo)=="" && trim(accountNo)=="" && trim(accountName) == ""  && trim(fromDate)=="" && trim(subChangeType)==""
		&& trim(amFromDate)=="" && trim(amToDate)=="" && trim(pmFromDate)=="" && trim(pmToDate) == "" && trim(copcFromDate) == "" && trim(copcToDate)=="" 
		&& trim(demoType)=="" && trim(partyNo)=="" && trim(serviceSegment)=="" && trim(industrySegment)=="" && trim(customerSegment) == "" && trim(region) == "" && trim(orderStage)=="" )){
		alert("Please enter atleast one search criteria!");
		return false;
		
	} 
	else {
				
		if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
		{
			alert("Please enter From Date");
			return true;
		}
		else if((toDate == null || trim(toDate)== "" ) && (fromDate != null && trim(fromDate) != "" ))
		{
			alert("Please enter To Date");
			return true;
		}
		if(dateCompare(fromDate,toDate)==1)
		{			
			alert("From Date cannot be greater than To Date");
			return true;
		}
		
		if((amFromDate == null || trim(amFromDate)== "" ) && (amToDate != null && trim(amToDate) != "" ))
		{
			alert("Please enter AM From Date");
			return true;
		}
		else if((amToDate == null || trim(amToDate)== "" ) && (amFromDate != null && trim(amFromDate) != "" ))
		{
			alert("Please enter AM To Date");
			return true;
		}
		if(dateCompare(amFromDate,amToDate)==1)
		{			
			alert("AM From Date cannot be greater than AM To Date");
			return true;
		}
		
		if((pmFromDate == null || trim(pmFromDate)== "" ) && (pmToDate != null && trim(pmToDate) != "" ))
		{
			alert("Please enter PM From Date");
			return true;
		}
		else if((pmToDate == null || trim(pmToDate)== "" ) && (pmFromDate != null && trim(pmFromDate) != "" ))
		{
			alert("Please enter PM To Date");
			return true;
		}
		if(dateCompare(pmFromDate,pmToDate)==1)
		{			
			alert("PM From Date cannot be greater than PM To Date");
			return true;
		}
		
		if((copcFromDate == null || trim(copcFromDate)== "" ) && (copcToDate != null && trim(copcToDate) != "" ))
		{
			alert("Please enter COPC From Date");
			return true;
		}
		else if((copcToDate == null || trim(copcToDate)== "" ) && (copcFromDate != null && trim(copcFromDate) != "" ))
		{
			alert("Please enter COPC To Date");
			return true;
		}
		if(dateCompare(copcFromDate,copcToDate)==1)
		{			
			alert("COPC From Date cannot be greater than COPC To Date");
			return true;
		}						
		
		return false;
	}	
}


function getTip(orderno , accountname)
{
	
	var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var lstItem = jsonrpc.processes.getProductDetails(orderno);
	var strSer = "";
	
	 	for(j=0;j<lstItem.list.length;j++)
		{
			strSer = strSer + "<tr><td>";			
			strSer = strSer + lstItem.list[j].serviceName;
			strSer = strSer + "</td></tr>";	
		}
	
	Tip("<Table border=1> "+ strSer +"   </table>");	
}

function exportToExcel()
{	var myForm=document.getElementById('searchForm');
	myForm.toExcel.value='true';
	myForm.action="<%=request.getContextPath()%>/universalWorkQueueAction.do?methodName=viewUniversalWorkQueue&inExcel=true";
	myForm.submit();
	
}
path='<%=request.getContextPath()%>';
</script>
</head>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body>
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>

<html:form action="/universalWorkQueueAction" method="post" styleId="searchForm">
	<jsp:include page="../header.jsp" flush="true"/>
	<div style="overflow:auto; height: 100%;width: 100% " >
	<table border="0" align="center" cellpadding="0" cellspacing="0">
		<tr align="left">
			<logic:equal value="0" property="isBillingOrder"
				name="UniversalWorkQueueLogicalFormBean">
				<td class="head">&nbsp;Universal Work Queue</td>
			</logic:equal>
		</tr>
		<tr valign="middle" id="newProduct">
			<td valign="bottom" align="center">
				<%String []colors=new String[]{"th","lightBg"}; %> 
				<bean:define id="formBean" name="UniversalWorkQueueLogicalFormBean"></bean:define>
				<bean:define id="pagingSorting" name="formBean" property="pagingSorting" /> 
				<html:hidden name="formBean" property="pagingSorting.pageNumber" /> 
				<html:hidden property="toExcel" /> 
				<html:hidden name="formBean" property="pagingSorting.sortByColumn" /> 
				<html:hidden name="formBean" property="pagingSorting.sortByOrder" />
				<table border="1" class="tab2" cellpadding="2" cellspacing="1" align="center" width="100%" >
					<tr>
						<td class="tab2" colspan="15">
							<bean:define id="pagingBean" name="formBean"></bean:define> 
							<%String  currPageNumber =String.valueOf(((UniversalWorkQueueFormBean)formBean).getPagingSorting().getPageNumber());
								String  maxPageNumber =String.valueOf(((UniversalWorkQueueFormBean)formBean).getPagingSorting().getMaxPageNumber());
							%> 
							<jsp:include flush="true" page="/paging.jsp">
								<jsp:param name="currPageNumber" value="<%=currPageNumber %>" />
								<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>" />
							</jsp:include>
						</td>
						<td align="right" style="padding-right:10px;" colspan="5">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" 
								alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();" />&nbsp;
							<a href="#"><img onclick="searchSubmit();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;
							<img src="./gifs/top/home.gif" onClick="goToHome();"></img> 
							<img title="Export To Excel" src="./gifs/top/excel.gif" onClick="javascript:exportToExcel();"></img> 
							<html:hidden property="checkedOrderNo" />
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td >
				<div class="div_freezepanes_wrapper" >

				<div class="div_verticalscroll" onmouseover="this.style.cursor='pointer'">
					<div style="height:50%;" onmousedown="upp();" onmouseup="upp(1);"><img class="buttonUp" src="./gifs/uF035.png"></div>
					<div style="height:50%;" onmousedown="down();" onmouseup="down(1);"><img class="buttonDn" src="./gifs/uF036.png"></div>
				</div>
		
			
			<table border="1" id="t1" width="100%" style=" border-color:#CCCC99; border: 1px ;font-family:Verdana, Arial, Helvetica, sans-serif; font-size:11px; color:#000000; border:2px #CCCC99 solid;" cellpadding="2">
				<logic:equal value="0" property="isBillingOrder" 
					name="UniversalWorkQueueLogicalFormBean">
				</logic:equal>
					<tbody>
						<tr>
							<td width="5%" align="center"><b>SNo.</b></td>
							<td width="5%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('PARTYNO')">Party No</a>
												</logic:notEmpty>
												<logic:empty  name="universalWorkQueueList" scope="request">
										Party No
												</logic:empty>
											</logic:present></b></td>
									
										<td width="5%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('ORDERNO')">Order No</a>
												</logic:notEmpty>
												<logic:empty  name="universalWorkQueueList" scope="request">
													Order No
												</logic:empty>
											</logic:present></b></td>
									
										<td width="5%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('ORDERTYPE')">Order Type</a>
												</logic:notEmpty>
												<logic:empty  name="universalWorkQueueList" scope="request">
													Order Type
												</logic:empty>
											</logic:present></b></td>
											
										<td width="5%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('ACCOUNTNO')">CRM Account No</a>
												</logic:notEmpty>
												<logic:empty  name="universalWorkQueueList" scope="request">
													CRM Account No
												</logic:empty>
											</logic:present></b></td>
											
								    <td width="5%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('ACTNAME')">Account Name</a>
												</logic:notEmpty>
												<logic:empty  name="universalWorkQueueList" scope="request">
													Account Name
												</logic:empty>
											</logic:present></b></td>
											
									<td width="5%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('ORDERDATE')">Order Date</a>
												</logic:notEmpty>
												<logic:empty  name="universalWorkQueueList" scope="request">
												Order Date
												</logic:empty>
											</logic:present></b></td>
											
								<td width="5%" align="center" nowrap><b>AM Name </b></td>
				   			    <td width="5%" align="center" nowrap><b>Order Value </b></td>	
									
											
								<td width="5%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('REGION')">Region Name</a>
												</logic:notEmpty>
												<logic:empty  name="universalWorkQueueList" scope="request">
												Region Name
												</logic:empty>
											</logic:present></b></td>
											
									 <td width="5%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('STAGE')">Order Stage</a>
												</logic:notEmpty>
												<logic:empty  name="universalWorkQueueList" scope="request">
											Order Stage
												</logic:empty>
											</logic:present></b></td>
											
								   <td width="5%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('DEMO')">Demo Type</a>
												</logic:notEmpty>
												<logic:empty  name="universalWorkQueueList" scope="request">
											Demo Type
												</logic:empty>
											</logic:present></b></td>
											
									 <td width="5%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('TASKNO')">Task No</a>
												</logic:notEmpty>
												<logic:empty  name="universalWorkQueueList" scope="request">
											Task No
												</logic:empty>
											</logic:present></b></td>
									 <td width="5%" align="center"><b>	Task Status</b></td>	
									 
                                  <!--[00011] Start-->
								  <td width="5%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('SERSEG')">Service Segment</a>
												</logic:notEmpty>
												<logic:empty  name="universalWorkQueueList" scope="request">
									Service Segment
												</logic:empty>
											</logic:present></b></td>
								  <!--[00011] End-->	
								  <td width="5%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('INDSEG')">Industry Segment</a>
												</logic:notEmpty>
												<logic:empty  name="universalWorkQueueList" scope="request">
									Industry Segment
												</logic:empty>
											</logic:present></b></td>
											
									 <td width="5%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('AMADATE')">AM Approval Date</a>
												</logic:notEmpty>
												<logic:empty  name="universalWorkQueueList" scope="request">
								AM Approval Date
												</logic:empty>
											</logic:present></b></td>
											
									<td width="4%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('PMADATE')">PM Approval Date</a>
												</logic:notEmpty>
												<logic:empty  name="universalWorkQueueList" scope="request">
								PM Approval Date
												</logic:empty>
											</logic:present></b></td>
											
									<td width="4%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('CADATE')">COPC Approval Date</a>
												</logic:notEmpty>
												<logic:empty name="universalWorkQueueList" scope="request">
								COPC Approval Date
												</logic:empty>
											</logic:present></b></td>
											
									<td width="4%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('CUSSEG')">Customer Segment</a>
												</logic:notEmpty>
												<logic:empty  name="universalWorkQueueList" scope="request">
								Customer Segment
												</logic:empty>
											</logic:present></b></td>
											
								<td width="4%" align="center"><b>	
											<logic:present name="universalWorkQueueList" scope="request">
													<logic:notEmpty name="universalWorkQueueList" scope="request">
														<a href="#" onclick="sortSubmit('SUBCHANGETYPE')">Subchange Type</a>
												</logic:notEmpty>
												<logic:empty  name="universalWorkQueueList" scope="request">
								Subchange Type
												</logic:empty>
											</logic:present></b></td>
											
								<td width="10%" align="center" nowrap="nowrap" ><b>Bin Aging</b></td>
								</tr>
								  	<tr class="th">	
									<td></td>
									<td width="5%"><html:text property="searchPartyNumber"
										maxlength="10" size="7" styleId="id_orderdate_search"
										onkeydown="if(event.keyCode == 13){validation(this)};"
									 />
									</td>
									<td width="5%"><html:text property="searchCRMOrder"
										size="4" maxlength="8" styleId="id_orderno_search"
										onkeydown="if(event.keyCode == 13){validation(this)};"
									 /></td>
		
									<td width="5%"><html:select property="searchOrderType"
										styleId="id_ordertype_search" style="width:75px"
										onkeydown="if (event.keyCode == 13) return searchSubmit();">
										<html:option value="">--Select--</html:option>
										<html:optionsCollection name="listOrderType" label="orderType"
											value="orderType" />
									</html:select></td>
		
									<td width="5%"><html:text property="searchAccountNo"
										maxlength="11" size="10" styleId="id_accountno_search"
										onkeydown="if(event.keyCode == 13){validation(this)};"
										></html:text></td>
		
									<td width="5%"><html:text property="searchAccountName"
										size="10" style="width:100px" styleId="id_accountname_search"
										onkeydown="if (event.keyCode == 13){validation(this)};"
										></html:text>
									</td>
		
									<td width="5%">
										<table>
											<tr>
												<td><font size="0"> FROM</font></td>
												<td><html:text property="searchfromDate"  size="9"
													readonly="true" styleId="dateFrom"></html:text></td>
												<td><font size="1"> <a
													href="javascript:show_calendar(searchForm.searchfromDate);"
													class="borderTabCal"><img
													src="<%=request.getContextPath()%>/images/cal.gif"
													border="0px" alt="Loading..."></a> </font></td>
											<tr>
												<td><font size="0"> TO</font></td>
												<td><html:text property="searchToDate"  size="9"
													readonly="true" styleId="dateTo"></html:text></td>
												<td><font size="1"> <a
													href="javascript:show_calendar(searchForm.searchToDate);"
													class="borderTabCal"><img
													src="<%=request.getContextPath()%>/images/cal.gif"
													border="0px" alt="Loading..."></a> </font></td>
											</tr>
										</table>
									</td>
		
									<td width="5%">&nbsp;</td>		
									<td width="5%">&nbsp;</td>									
		
									<td width="5%"><html:select property="searchRegion"
										styleId="searchSourceNameId" style="width:100px"
										onkeydown="if (event.keyCode == 13) return searchSubmit();">
										<html:option value="">--Select--</html:option>
										<html:optionsCollection name="listRegionName"
											label="searchRegionName" value="searchRegionName" />
									</html:select></td>
		
									<td width="5%"><html:select property="searchOrderStage"
										styleId="searchSourceNameId" style="width:75px"
										onkeydown="if (event.keyCode == 13) return searchSubmit();">
										<html:option value="">--Select--</html:option>
										<html:optionsCollection name="listOrderStageName"
											label="searchOrderStageName" value="searchOrderStageName" />
									</html:select></td>
		
									<td width="5%"><html:select property="searchDemoType"
										styleId="searchSourceNameId" style="width:75px"
										onkeydown="if (event.keyCode == 13) return searchSubmit();">
										<html:option value="">--Select--</html:option>
										<html:option value="Yes">Yes</html:option>
										<html:option value="No">No</html:option>
									</html:select></td>
		
									<td width="5%">&nbsp;</td>
		
									<td width="5%">&nbsp;</td>
		
									
		                        <!--[00011] Start-->  
		                        <td width="5%"><html:select property="searchServiceSegment"
										styleId="searchSourceNameId" style="width:100px"
										onkeydown="if (event.keyCode == 13) return searchSubmit();">
										<html:option value="">--Select--</html:option>
										<html:optionsCollection name="listServiceSegmentName"
											label="searchServiceSegmentName"
											value="searchServiceSegmentName" />
									</html:select></td>
								<!--[00011] End-->
									
								<td width="5%"><html:select property="searchIndustrySegment"
										styleId="searchSourceNameId" style="width:100px"
										onkeydown="if (event.keyCode == 13) return searchSubmit();">
										<html:option value="">--Select--</html:option>
										<html:optionsCollection name="listIndustrySegmentName"
											label="searchIndustrySegmentName"
											value="searchIndustrySegmentName" />
									</html:select></td>
		
									<td width="5%">
										<table>
											<tr>
												<td><font size="0"> AM FROM</font></td>
												<td><html:text property="searchAmFromDate"  size="9"
													readonly="true" styleId="dateAMFrom"></html:text></td>
												<td><font size="1"> <a
													href="javascript:show_calendar(searchForm.searchAmFromDate);"
													class="borderTabCal"><img
													src="<%=request.getContextPath()%>/images/cal.gif"
													border="0px" alt="Loading..."></a> </font></td>
											<tr>
												<td><font size="0"> AM TO</font></td>
												<td><html:text property="searchAmToDate"  size="9"
													readonly="true" styleId="dateAMTo"></html:text></td>
												<td><font size="1"> <a
													href="javascript:show_calendar(searchForm.searchAmToDate);"
													class="borderTabCal"><img
													src="<%=request.getContextPath()%>/images/cal.gif"
													border="0px" alt="Loading..."></a> </font></td>
											</tr>
										</table>
									</td>
		
									<td width="5%">
										<table>
											<tr>
												<td><font size="0"> PM FROM</font></td>
												<td><html:text property="searchPmFromDate"  size="9"
													readonly="true" styleId="datePMFrom"></html:text></td>
												<td><font size="1"> <a
													href="javascript:show_calendar(searchForm.searchPmFromDate);"
													class="borderTabCal"><img
													src="<%=request.getContextPath()%>/images/cal.gif"
													border="0px" alt="Loading..."></a> </font></td>
											<tr>
												<td><font size="0"> PM TO</font></td>
												<td><html:text property="searchPmToDate"  size="9"
													readonly="true" styleId="datePMTo"></html:text></td>
												<td><font size="1"> <a
													href="javascript:show_calendar(searchForm.searchPmToDate);"
													class="borderTabCal"><img
													src="<%=request.getContextPath()%>/images/cal.gif"
													border="0px" alt="Loading..."></a> </font></td>
											</tr>
										</table>
									</td>
		
									<td width="5%">
										<table>
											<tr>
												<td><font size="0"> COPC FROM</font></td>
												<td><html:text property="searchCopcFromDate"  size="9"
													readonly="true" styleId="dateCOPCFrom"></html:text></td>
												<td><font size="0"> <a
													href="javascript:show_calendar(searchForm.searchCopcFromDate);"
													class="borderTabCal"><img
													src="<%=request.getContextPath()%>/images/cal.gif"
													border="0px" alt="Loading..."></a> </font></td>
											<tr>
												<td><font size="0"> COPC TO</font></td>
												<td><html:text property="searchCopcToDate"  size="9"
													readonly="true" styleId="dateCOPCTo"></html:text></td>
												<td><font size="0"> <a
													href="javascript:show_calendar(searchForm.searchCopcToDate);"
													class="borderTabCal"><img
													src="<%=request.getContextPath()%>/images/cal.gif"
													border="0px" alt="Loading..."></a> </font></td>
											</tr>
										</table>
									</td>
		
									<td width="5%"><html:select property="searchCustomerSegment"
										styleId="searchSourceNameId" style="width:75px"
										onkeydown="if (event.keyCode == 13) return searchSubmit();">
										<html:option value="">--Select--</html:option>
										<html:optionsCollection name="listCustomerSegmentName"
											label="searchCustomerSegmentName"
											value="searchCustomerSegmentName" />
									</html:select></td>
		
									<td width="5%"><html:select property="searchSubChangeType"
										styleId="id_subchange_search" style="width:100px"
										onkeydown="if (event.keyCode == 13) return searchSubmit();">
										<html:option value="">--Select--</html:option>
										<html:optionsCollection name="listSubChangeType"
											label="subChangeType" value="subChangeType" />
									</html:select></td>
									
								</tr>

											<logic:equal value="0" property="isBillingOrder"
												name="UniversalWorkQueueLogicalFormBean">
												<logic:empty name="formBean" property="universalWorkQueueList">
													<tr class="normal">
														<td colspan="20" align="center">No Project Found</td>
													</tr>
												</logic:empty>
												<logic:notEmpty name="universalWorkQueueList" scope="request">
						<logic:iterate id="row"	name="universalWorkQueueList" 
							scope="request" indexId="index1">
                        <!--[00011] Start-->
						<%!String colorRow; %>
						<logic:equal name = "row" property = 'serviceSegment' value="NORTH STAR" >
						    <% colorRow = "greenBg";  %>
						</logic:equal>
						<logic:notEqual name = "row" property = 'serviceSegment' value="NORTH STAR">
						    <% colorRow = colors[index1.intValue()%2]; %>
						</logic:notEqual>
						<tr style="border: medium" class="<%=colorRow%>">
                        <!--[00011] End-->
							<%	int count = ((index1.intValue()) + 1);%>
						   	<td nowrap><%=index1.intValue()+1 %></td>
							<td width="3%"><bean:write name="row"
								property='partyNo' />&nbsp;</td>
							<td width="3%">
									<% 
									String strText = "";
									strText = "";
								%> <a onmouseover="Tip('<bean:write name="row" property="searchAccountName" />')" onmouseout="UnTip()"
								href="javascript:fnViewOrder('<bean:write name="row" property="searchCRMOrder" />','<bean:write  name="row" property="searchOrderType" />');">
								<font color="Brown"> <bean:write name="row"
									property="searchCRMOrder" /> </font> </a></td>
							<td width="5%" onmouseout="UnTip()"
								onmouseover="getTip('<bean:write name="row" property="searchCRMOrder" />','<bean:write name="row" property="searchAccountName" />')">
							<bean:write name="row" property="searchOrderType" /></td>
							<td width="5%"><bean:write name="row"
								property="searchAccountNo" />&nbsp;</td>
							<td width="1%"><bean:write name="row"
								property="searchAccountName" />&nbsp;</td>
							<td width="9%"><bean:write name="row"
								property="searchfromDate" />&nbsp;</td>	
							<td width="2%" nowrap><bean:write name="row"
								property='accMangerName' />&nbsp;</td>	
							<td width="2%" nowrap><bean:write name="row"
								property='poAmount' />&nbsp;</td>
							<td width="6%"><bean:write name="row"
								property='regionName' />&nbsp;</td>
							<td width="5%"><bean:write name="row"
								property='orderStage' />&nbsp;</td>
							<td width="5%"><bean:write name="row"
								property='demoType' />&nbsp;</td>
							<td width="3%"><bean:write name="row" property='taskNo' />
							</td>
							<td width="3%"><bean:write name="row"
								property='taskStatus' />&nbsp;</td>
                            <!--[00011] Start-->
							<td width="6%"><bean:write name="row"
								property='serviceSegment' />&nbsp;</td>
							<!--[00011] End-->
							<td width="6%"><bean:write name="row"
								property='industrySegment' />&nbsp;</td>
							<td width="8%"><bean:write name="row"
								property='amApprovalDate' />&nbsp;</td>
							<td width="8%"><bean:write name="row"
								property='pmApprovalDate' />&nbsp;</td>
							<td width="8%"><bean:write name="row"
								property='copcApprovalDate' />&nbsp;</td>
							<td width="5%"><bean:write name="row"
								property='customerSegment' />&nbsp;</td>
							<td width="2%"><bean:write name="row"
								property='subChangeType' />&nbsp;</td>
							<td width="10%" nowrap="nowrap" align="left"><bean:write name="row"
								property='agingDaysHours' />&nbsp;</td>
						</tr>
						</logic:iterate>
					</logic:notEmpty>
				</logic:equal>
			</tbody>									
			</table>
					
</div>
</html:form>
</body>
<script type="text/javascript">
	LoadToolTip()
</script>

</html>
