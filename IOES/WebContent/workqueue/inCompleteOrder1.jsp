<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ROHIT VERMA	18-Oct-11	00-05422		Displaying List of New and Change Order Together in Draft Order(UAT Defect Point no: 99)-->
<!--[002]	 SAURABH SINGH	23-Jan-12	00-05422		Opening New And Change Order in new Window-->
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.beans.DefaultBean"%>

<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.utilities.AppUtility"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>

<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">
<link href="./gifs/style.css" type="text/css" rel="stylesheet">
<link href="./gifs/freezeStyle.css" type="text/css" rel="stylesheet">


<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="npd/js/inputColor.js"></script>
<script language="javascript" src="js/calendar.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<script type="text/javascript" src="js/formValidations.js"></script>
<script type="text/javascript" src="js/headerFreeze.js"></script>

<title>Draft Orders</title>
<style type="text/css">
/* CSS for the demo */
img{
	border:0px;
}
</style>
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

<script type="text/javascript" type="text/javascript">
<%
	HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
	HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
	UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>
function goToHome()
{
//Meenakshi : commented entire form submit. Made a dummy form and called action
	/*	var myForm=document.getElementById('searchForm');
		//myForm.toExcel.value='false';
		myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
		showLayer();
		myForm.submit(); */
		var goToHomeForm=createDummyForm("<%=request.getContextPath()%>/defaultAction.do");
		attachHiddenField(goToHomeForm,"method","goToHome");
		showLayer();
		goToHomeForm.submit();
}
//[001] Start 
function fnViewOrder(orderNo)
{

		//lawkush Start
			var roleID=<%=objUserDto.getUserRoleId()%>;
		    var userId = "<%=objUserDto.getUserId() %>";
		    jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleID,userId);
			var userIDLogged=orderDetails.list[0].userID;
			var userNameLogged=orderDetails.list[0].userName;
		    	var  roleNameLogged=orderDetails.list[0].roleName;
		      if(userIDLogged!=null && userIDLogged!='' )
		      {
			      if(!confirm("User "+userNameLogged+" [User ID "+userIDLogged+ "]  has already Logged on this Order with Role "+roleNameLogged+""+'\n'+" Do you continue viewing in View Order"))
			      		{
			      			return ;
			      		}
		      }
     
		
		//lawkush End


	//document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
	//showLayer();
	//document.forms[0].submit();
	//Start[002]
	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
	window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0"); 
	//End[002]
}
//[001] End
function fnViewChangeOrder(orderNo)
{
	// document.forms[0].checkedOrderNo.value=orderNo;
	// document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
	// showLayer();
	// document.forms[0].submit();
	//Start[002]
	var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
	window.open(path,'',"scrollbars=yes,resizable=yes,width=1030,height=650,top=0,left=0");
	//End[002]
}
	
function checkForm()
{
	return true;
}

function searchSubmit()
{


	var myForm=document.getElementById('searchForm');
	
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";
	var i;
	if(isBlankForm()){
		return		
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
	
	
	if( trim(myForm.searchQuoteNumber.value).length>0)
	{		
		i = ValidateTextField(myForm.searchQuoteNumber,path,'Quote No');	
			if(i == false)
		{
			return false;
		}
					
	}
	
	 	
	
	
	myForm.action="<%=request.getContextPath()%>/defaultDraftNewOrdAction.do?method=viewIncompleteOrder";
	showLayer();
	myForm.submit();
		
}

function pagingSubmit(val)
{
	var myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.pageNumber"].value=val;
	myform.action="<%=request.getContextPath()%>/defaultDraftNewOrdAction.do?method=viewIncompleteOrder";
	showLayer();
	myform.submit();
}



function validation(obj)
{	
	var myForm=document.getElementById('searchForm');
	var i;
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

function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	myform.elements["pagingSorting.pageNumber"].value=1;
	if(myform.elements["pagingSorting.sortByColumn"].value==sortBy)
	{
		if(myform.elements["pagingSorting.sortByOrder"].value=="decrement")
		{
			myform.elements["pagingSorting.sortByOrder"].value="increment";
		}
		else
		{
			myform.elements["pagingSorting.sortByOrder"].value="decrement";
		}
	}
	else
	{
		myform.elements["pagingSorting.sortByColumn"].value=sortBy;
		myform.elements["pagingSorting.sortByOrder"].value="decrement";
	}
	if(checkForm()==false)
	{
		return ;
	}

	myform.action="<%=request.getContextPath()%>/defaultDraftNewOrdAction.do?method=viewIncompleteOrder";
	showLayer();
	myform.submit();
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
	var currency=myForm.searchCurrency.value;
	var i;
	

	if((quoteNumber==null  && source==null && crmOrderNo==null && accountNo == null && currency == null && accountName == null  & fromDate == null & toDate == null) 
		|| (trim(crmOrderNo)=="" && trim(accountNo)=="" && trim(accountName) == "" && trim(source) == "" && trim(quoteNumber)=="" && trim(fromDate)=="") && trim(currency)==""){
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
		return false;
	}
	
	
	
	
 	
	
		
}



function clearFields()
{	
	var myForm=document.getElementById('searchForm');
	myForm.searchCRMOrder.value='' ; 	
	myForm.searchAccountNo.value='' ; 
	myForm.searchAccountName.value='' ;	
	myForm.searchSource.value='' ;	
	myForm.searchfromDate.value='' ;
	myForm.searchToDate.value='' ;	
	myForm.searchQuoteNumber.value='' ;
	myForm.searchCurrency.value='' ;		
}
path='<%=request.getContextPath()%>';
</script>
</head>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body>
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
	
<html:form action="/defaultDraftNewOrdAction" method="post" styleId="searchForm">
	<jsp:include page="../header.jsp" flush="true"/>
	<div style="overflow:auto; height: 100%;width: 100% " >
	<table border="0" align="center" cellpadding="0" cellspacing="0">
		<tr align="left">
			<td class="head">&nbsp;Draft Orders</td>
		</tr>
		<tr valign="middle" id="newProduct">
			<td valign="bottom" align="center">
				<%String []colors=new String[]{"th","lightBg"}; %>
				<bean:define id="formBean" name="defaultBean"></bean:define>
				<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
				<html:hidden name="formBean" property="pagingSorting.pageNumber"/>
				<html:hidden name="formBean" property="pagingSorting.sortByColumn"/>
				<html:hidden name="formBean" property="pagingSorting.sortByOrder"/>
				<table border="1" class="tab2" cellpadding="2" cellspacing="1" align="center" width="100%" >
					<tr>
						<td class="tab2" colspan="7">
							<bean:define id="pagingBean" name="formBean"></bean:define>
							<%String  currPageNumber =String.valueOf(((DefaultBean)formBean).getPagingSorting().getPageNumber());
							String  maxPageNumber =String.valueOf(((DefaultBean)formBean).getPagingSorting().getMaxPageNumber());%>
							<jsp:include flush="true" page="../paging.jsp">
								<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
								<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
							</jsp:include>
						</td>
						<td align="right" style="padding-right:10px;" colspan="3">
							<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;
							<a href="#"><img onclick="searchSubmit();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;
							<img src="gifs/top/home.gif" title="Home" onClick="goToHome('Home');"></img>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
			<div class="div_freezepanes_wrapper" >

				<div class="div_verticalscroll" onmouseover="this.style.cursor='pointer'">
					<div style="height:50%;" onmousedown="upp();" onmouseup="upp(1);"><img class="buttonUp" src="./gifs/uF035.png"></div>
					<div style="height:50%;" onmousedown="down();" onmouseup="down(1);"><img class="buttonDn" src="./gifs/uF036.png"></div>
				</div>
				
			
				<table border="1" id="t1" width="100%" style=" border-color:#CCCC99; border: 1px ;font-family:Verdana, Arial, Helvetica, sans-serif; font-size:11px; color:#000000; border:2px #CCCC99 solid;" cellpadding="2">
					 
					
								<tbody>
										<tr>
									<th class="th" width="3%">Sr NO.</th>

									
								<th class="th" width="9%">	
											<logic:present name="formBean" property="orderList">
													<logic:notEmpty name="formBean" property="orderList">
														<a href="#" onclick="sortSubmit('ORDERNO')">Order No</a>
												</logic:notEmpty>
												<logic:empty  name="formBean" property="orderList">
													Order No
												</logic:empty>
											</logic:present></b></th>
									
								  <th class="th" width="9%">
											<logic:present name="formBean" property="orderList">
													<logic:notEmpty name="formBean" property="orderList">
														<a href="#" onclick="sortSubmit('ACCOUNTNO')">CRM Account No</a>
												</logic:notEmpty>
												<logic:empty  name="formBean" property="orderList">
													CRM Account No
												</logic:empty>
											</logic:present></b></th>
											
									 <th class="th" width="14%">
											<logic:present name="formBean" property="orderList">
													<logic:notEmpty name="formBean" property="orderList">
														<a href="#" onclick="sortSubmit('ACCOUNTNAME')">Account Name</a>
												</logic:notEmpty>
												<logic:empty  name="formBean" property="orderList">
													Account Name
												</logic:empty>
											</logic:present></b></th>
																		
									  	<th class="th" width="9%">	
											<logic:present name="formBean" property="orderList">
													<logic:notEmpty name="formBean" property="orderList">
														<a href="#" onclick="sortSubmit('ORDERTYPE')">Order Type</a>
												</logic:notEmpty>
												<logic:empty  name="formBean" property="orderList">
												Order Type
												</logic:empty>
											</logic:present></b></th>
												
									 	<th class="th" width="9%">
											<logic:present name="formBean" property="orderList">
													<logic:notEmpty name="formBean" property="orderList">
														<a href="#" onclick="sortSubmit('DATE')">From Date</a>
												</logic:notEmpty>
												<logic:empty  name="formBean" property="orderList">
													From Date
												</logic:empty>
											</logic:present></b></th>	
											
									<th class="th" width="9%">
											<logic:present name="formBean" property="orderList">
													<logic:notEmpty name="formBean" property="orderList">
														<a href="#" onclick="sortSubmit('DATE')">TO Date</a>
												</logic:notEmpty>
												<logic:empty  name="formBean" property="orderList">
													TO Date
												</logic:empty>
											</logic:present></b></th>
											
									<th class="th" width="9%">	
											<logic:present name="formBean" property="orderList">
													<logic:notEmpty name="formBean" property="orderList">
														<a href="#" onclick="sortSubmit('SOURCENAME')">Source Name</a>
												</logic:notEmpty>
												<logic:empty  name="formBean" property="orderList">
													Source Name
												</logic:empty>
											</logic:present></b></th>
											
										<th class="th" width="5%">	
											<logic:present name="formBean" property="orderList">
													<logic:notEmpty name="formBean" property="orderList">
														<a href="#" onclick="sortSubmit('QUOTENO')">Quote No</a>
												</logic:notEmpty>
												<logic:empty  name="formBean" property="orderList">
												Quote No
												</logic:empty>
											</logic:present></b></th>
											
									<th class="th" width="10%">	
											<logic:present name="formBean" property="orderList">
													<logic:notEmpty name="formBean" property="orderList">
														<a href="#" onclick="sortSubmit('CURRENCY')">Currency</a>
												</logic:notEmpty>
												<logic:empty  name="formBean" property="orderList">
													Currency
												</logic:empty>
											</logic:present></b></th>
											
								 	<th class="th" width="14%">
											<logic:present name="formBean" property="orderList">
													<logic:notEmpty name="formBean" property="orderList">
														<a href="#" onclick="sortSubmit('PRODUCTNAME')">Product Name</a>
												</logic:notEmpty>
												<logic:empty  name="formBean" property="orderList">
													Product Name
												</logic:empty>
											</logic:present></b></th>
								</tr>
								<tr class="th">
									<td width="3%"></td>
									<td width="9%">
										<html:text property="searchCRMOrder" size="4" maxlength="8" styleId="id_orderno_search" 
										onkeydown="if (event.keyCode == 13){validation(this)};"
										/>
									</td>
									<td width="9%">
										<html:text property="searchAccountNo" size="10" maxlength="11" styleId="id_accountno_search" 
										onkeydown="if (event.keyCode == 13){validation(this)};"></html:text>
									</td>
									<td width="14%">
										<html:text property="searchAccountName" size="10" styleId="id_accountname_search" 
										onkeydown="if (event.keyCode == 13){validation(this)};" 
										></html:text>
									</td>
									<td width="9%"  >
									
									</td>						
									<td width="9%"  >
										<html:text property="searchfromDate" size="8" readonly="true"  styleId="dateFrom" ></html:text>
										<font size="1">
											<a href="javascript:show_calendar(searchForm.searchfromDate);" class="borderTabCal">
											<img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
										</font>
									</td>	
									<td width="9%">
										<html:text property="searchToDate" size="8" readonly="true"  styleId="dateTo" ></html:text>
										<font size="1">
											<a href="javascript:show_calendar(searchForm.searchToDate);" class="borderTabCal">
											<img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
										</font>
									</td>						
									<td width="9%">
								    	<html:select property="searchSource" styleId="searchSourceNameId" style="width:80px"  
								    	onkeydown="if (event.keyCode == 13) return searchSubmit();"  >
											<html:option value="">--Select--</html:option>
											<html:optionsCollection name="listSourceName" label="searchSourceName" value="searchSourceName" />
										</html:select>
									</td>
									<td width="5%"  >
										<html:text property="searchQuoteNumber" maxlength="8" size="5"  styleId="id_orderdate_search" 
										onkeydown= "if(event.keyCode == 13){validation(this)};"/>
									</td>
									<td width="10%"  >
										<html:select property="searchCurrency" styleId="searchCurrencyId" style="width:80px"  
										onkeydown="if (event.keyCode == 13) return searchSubmit();" >
											<html:option value="">--Select--</html:option>
											<html:optionsCollection name="listCurrency" label="searchCurrencyName" value="searchCurrencyName" />
										</html:select>
									</td>
									<td width="14%">&nbsp;</td>
								</tr>
									
														
												<%int nSNo=0;%>
												<logic:empty name="formBean" property="orderList">
													<tr class="normal">
														<td colspan="10" align="center">No Record Found</td>
													</tr>
												</logic:empty>
												<logic:notEmpty name="formBean" property="orderList">
													<logic:iterate id="row"	name="formBean" property="orderList" indexId="recordId">
													<%
														String classType=null;
														if(recordId.intValue() % 2 == 0)
														{
														classType="th";
														}
														else
														{
														classType="lightBg";				
														}	
														nSNo++;					
													%>	
													<tr class="<%=classType%>">
														<td width ="1%" align="center"><%=nSNo%></td>
														<td width ="9%" align="center">
															<!-- [001] Start -->
															<logic:equal value="Change" property="searchOrderType" name="row">
																<a href="javascript:fnViewChangeOrder('<bean:write name="row" property="searchCRMOrder" />');">
																	<font color="Brown">                       
																		<bean:write name="row" property="searchCRMOrder" />
																	</font>
																</a>
															</logic:equal>
															<logic:notEqual value="Change" property="searchOrderType" name="row">
																<a href="javascript:fnViewOrder('<bean:write name="row" property="searchCRMOrder" />');">
																	<font color="Brown">                       
																		<bean:write name="row" property="searchCRMOrder" />
																	</font>
																</a>
															</logic:notEqual>
															<!-- [001] End -->
														</td>
														<td width ="10%" align="center"><bean:write name='row' property='searchAccountNo' />&nbsp;</td>
														<td width ="14.5%" align="center"><bean:write name='row' property='searchAccountName' />&nbsp;</td>
														<td width ="9%"  align="center"><bean:write name='row' property='searchOrderType' />&nbsp;</td>
														<td width ="18%" align="center" colspan="2"><bean:write name='row' property='searchfromDate' />&nbsp;</td>
														<td width ="9.5%" align="center"><bean:write name='row' property='searchSource' />&nbsp;</td>
														<td width ="7%"  align="center"><bean:write name='row' property='searchQuoteNumber' />&nbsp;</td>
														<td width ="10%" align="center"><bean:write name='row' property='searchCurrency' />&nbsp;</td>
														<td width ="12%" align="center"><bean:write name='row' property='serviceName' />&nbsp;</td>
													</tr>
													</logic:iterate>
												</logic:notEmpty>
											</table>
										</div>
									</td>
								</tr>
							</table>
						</div>
</html:form>
</body>
<script type="text/javascript">
	LoadToolTip()
</script>

</html:html>
