<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page errorPage="ErrorPage.jsp"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@page import="com.ibm.ioes.beans.DefaultBean"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.utilities.AppUtility"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>




<html:html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link rel="stylesheet" type="text/css" href="npd/gifs/sdmenu.css" />
<link rel="stylesheet" type="text/css" href="npd/CSS/chromestyle.css">
<link type="text/css" rel="stylesheet" href="npd/CSS/style.css">

<script language="JavaScript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/jquery.min.js"></script>
<script type="text/javascript" src="npd/js/jqueryslidemenu.js"></script>
<script type="text/javascript" src="npd/js/chrome.js"></script>
<script type="text/javascript" src="npd/js/scw.js"></script>
<script type="text/javascript" src="npd/js/timepicker.js"></script>


<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>


<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>
<script language="javascript" src="js/calendar.js"></script>




<script type="text/javascript" src="npd/js/inputColor.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<title>InComplete ChangeOrder Workflow</title>
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


function goToHome()
{
	var myForm=document.getElementById('searchForm');
	//myForm.toExcel.value='false';
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	showLayer();
	myForm.submit();
}
function fnViewOrder(orderNo)
{
	 // document.forms[0].checkedOrderNo.value=orderNo;
      document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&draft=1&orderNo="+orderNo;
      showLayer();
      document.forms[0].submit();
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
	

	if((quoteNumber==null && orderType==null && source==null && crmOrderNo==null && accountNo == null  && accountName == null  & fromDate == null & toDate == null) 
		|| (trim(orderType)=="" && trim(crmOrderNo)=="" && trim(accountNo)=="" && trim(accountName) == "" && trim(source) == "" && trim(quoteNumber)=="" && trim(fromDate)=="")){
		//alert("Please enter atleast one search criteria!");
		//return false;
		return true;
	} 
	else {
			
		if((fromDate == null || trim(fromDate)== "" ) && (toDate != null && trim(toDate) != "" ))
		{
			alert("Please enter From Date");
			return false;
		}		
		else if((toDate == null || trim(toDate)== "" ) && (fromDate != null && trim(fromDate) != "" ))
		{
			alert("Please enter To Date");
			return false;
		}
		
		if(dateCompare(fromDate,toDate)==1)
			{		
				alert("From Date cannot be greater than To Date");
				return false;
			}			
		return true;
	}	
}


function searchSubmit()
{
	var myForm=document.getElementById('searchForm');
	
	myForm.elements["pagingSorting.sortByColumn"].value="";
	myForm.elements["pagingSorting.sortByOrder"].value="";
	myForm.elements["pagingSorting.pageNumber"].value="1";
	if(!isBlankForm()){
		
		return;
	} 	
	else {		
	myForm.action="<%=request.getContextPath()%>/defaultDraftChangeOrdAction.do?method=viewIncompleteChangeOrder";
	showLayer();
	myForm.submit(); 
	}
}

function pagingSubmit(val)
{

	var myform=document.getElementById('searchForm');
	
	//setFormBean(myform);
	//myform.reset();
	myform.elements["pagingSorting.pageNumber"].value=val;
	//myform.method.value='viewOrderList';	
	myform.action="<%=request.getContextPath()%>/defaultDraftChangeOrdAction.do?method=viewIncompleteChangeOrder";
	showLayer();
	myform.submit();
}

function sortSubmit(sortBy)
{
	myform=document.getElementById('searchForm');
	//setFormBean(myform);
	//myform.reset();
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

	myform.method.value='viewOrderList_Change';	
	showLayer();
	myform.submit();
}


function onEnterValidation(obj)
{		
	var myForm=document.getElementById('searchForm');	
	if( trim(myForm.searchCRMOrder.value).length>0 || trim(myForm.searchAccountNo.value).length>0 || trim(myForm.searchQuoteNumber.value).length>0)
		{ 			
			var i = checkdigits(obj);		  	
		}     		    
 	if(i==true)
 		{ 		 	 
 		 	 searchSubmit();
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

function onEnterValidation(obj)
{		
	var myForm=document.getElementById('searchForm');
	if(	document.getElementById('searchCRMOrder').value == "0")
				{
					alert('please enter value greater than 0');					
					myForm.searchCRMOrder.value = "";
		            return false;
				}
	if(	document.getElementById('searchAccountNo').value == "0")
				{
					alert('please enter value greater than 0');					
					myForm.searchAccountNo.value = "";
		            return false;					
				}	
	if( trim(myForm.searchCRMOrder.value).length>0 || trim(myForm.searchAccountNo.value).length>0 || trim(myForm.searchQuoteNumber.value).length>0)
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
	if( trim(myForm.searchCRMOrder.value).length>0)
	{ 
		  checkdigits(obj);			  					
		  return false; 	 	 
	}
	if( trim(myForm.searchAccountNo.value).length>0)
	{ 
		  checkdigits(obj);	
		  return false; 		  	 	 
	}		
}
function validation(obj)
{	
	var myForm=document.getElementById('searchForm');	
	if( trim(myForm.searchAccountName.value).length>0)
	{		
		var i = ValidateTextField(obj,path,'Account Name');				
	}	
	if(i == undefined)
	{
		searchSubmit();
	}
}

path='<%=request.getContextPath()%>';
</script>

</head>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body >

<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<jsp:include page="/header.jsp" flush="true"/>

<!-- <div class="Head"><span>Draft Change Orders</span></div> -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" class="border" align="center">
	<tr align="left">
			<td class="head">&nbsp;Draft Change Orders</td>
		</tr>
	<tr valign="middle" id="newProduct">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="tab2">
			<tr>
				<!--<td><img src="npd/Images/tabLeft.gif"></td>
				<td background="npd/Images/tabbg.gif" class="tabHeading" width="30%">Finalize New Project Plans</td>
				<td><img src="npd/Images/tabRight.gif"></td>
				<td width="70%"><img src="npd/Images/zero.gif" width="15"
					height="1"></td>-->
				<td><img src="gifs/top/home.gif" title="Home" onClick="goToHome('Home');"></img></td>
				<td align="right" style="padding-right:10px;">
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;
					<a href="#"><img onclick="searchSubmit();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;
				</td>
					
			</tr>
		</table>
		</td>
	</tr>
</table>

			<%String []colors=new String[]{"normal","lightBg"}; %>
	<html:form action="/defaultDraftChangeOrdAction" method="post" styleId="searchForm">
	<bean:define id="formBean" name="defaultBean"></bean:define>
	<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
	<html:hidden name="formBean" property="pagingSorting.pageNumber"/>

	<html:hidden name="formBean" property="pagingSorting.sortByColumn"/>
	<html:hidden name="formBean" property="pagingSorting.sortByOrder"/>	
	
	<table width="100%" border="1" class="tab2" cellpadding="3" cellspacing="1" align="center">
		<tr>
			
							<td class="tab2" >
								  <bean:define id="pagingBean" name="formBean"></bean:define>
									<%String  currPageNumber =String.valueOf(((DefaultBean)formBean).getPagingSorting().getPageNumber());
										String  maxPageNumber =String.valueOf(((DefaultBean)formBean).getPagingSorting().getMaxPageNumber());
									%>
										<jsp:include flush="true" page="paging.jsp">
										<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
										<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
									</jsp:include>
									
									  
									
							</td>
						</tr>	

		</table>				

	<div style="height:370px;width:100%" >
						<table cellSpacing="0" cellPadding="0" width="100%" border="0" id="tbl1" class="tab2" align="center">
						<tr class="lightBgWihtoutHover">
						     <td><b>Sr NO.</b></td>
							<td><b>CRM Order No</b></td>
							<td><b>CRM Account No</b></td>
							<td><b>Account Name</b></td>							
							<td><b>Order Type</b></td>	
							<td><b>From Date</b></td>	
							<td><b>To Date</b></td>	
						      <td><b>Source Name</b></td>
						     <td><b>Quote Number</b></td>
						     <td><b>Currency</b></td>
						      <td><b>Service Name</b></td>		
						 
													
						</tr>
						<tr class="normal">
						<td width="1%">
						</td>
							<td width="9%"  >
								<html:text property="searchCRMOrder" maxlength="8" size="5"  styleId="id_orderno_search" onkeydown= "if(event.keyCode == 13){onEnterValidation(this)};" onchange="return validate(this)"/>
						</td>
							<td width="9%"  >
								<html:text property="searchAccountNo" maxlength="10" size="6"    styleId="id_accountno_search" onkeydown= "if(event.keyCode == 13){onEnterValidation(this)};" onchange="return validate(this)"></html:text>
				</td>
								<td width="14%"  >
								<html:text property="searchAccountName" maxlength="20" size="35"    styleId="id_accountname_search" onkeydown="if (event.keyCode == 13){validation(this)};" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Account Name')};"></html:text>
				</td>
							<td width="9%"  >
							<html:text property="searchOrderType" value="Change" maxlength="8" size="5"  styleId="id_ordertype_search" readonly="true"/>
					</td>						
							<td width="9%"  >
						 
						<html:text property="searchfromDate" size="8" styleId="dateFrom" readonly="true" ></html:text>
								<font size="1">
											<a href="javascript:show_calendar(searchForm.searchfromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
									</font>
	
						</td>
						
						<td width="9%"  >
						 
						<html:text property="searchToDate" size="8" styleId="dateTo" readonly="true" ></html:text>
								<font size="1">
											<a href="javascript:show_calendar(searchForm.searchToDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
								</font>
	
						</td>
						
						
						
							<td width="9%">
						      <html:select property="searchSource" styleId="searchSourceNameId" style="width:80px"  onkeydown="if (event.keyCode == 13) return searchSubmit();"  >
								<html:option value="">--Select--</html:option>
								<html:optionsCollection name="listSourceName" label="searchSourceName" value="searchSourceName" />
							</html:select>
					</td>
							<td width="5%"  >
							<html:text property="searchQuoteNumber" maxlength="8" size="5"  styleId="id_orderdate_search" onkeydown= "if(event.keyCode == 13){onEnterValidation(this)};" onchange="if( trim(this.value).length>0){ return checkdigits(this)};"/>
					</td>
							<td width="10%"  >
							  <html:select property="searchCurrency" styleId="searchCurrencyId" style="width:80px"  onkeydown="if (event.keyCode == 13) return searchSubmit();"  >
								<html:option value="">--Select--</html:option>
								<html:optionsCollection name="listCurrency" label="searchCurrencyName" value="searchCurrencyName" />
							</html:select>
					</td>
							
							
							
							
						</tr>
						
								<%int nSNo=0;%>
						
						<logic:empty name="formBean" property="orderList">
							<tr class="normal">
								<td colspan="12" align="center">No Record Found</td>
							</tr>
						</logic:empty>
						<logic:notEmpty name="formBean" property="orderList">

						<logic:iterate id="row"	name="formBean" property="orderList" indexId="recordId">
								
								<%
					String classType=null;
					if(recordId.intValue() % 2 == 0)
					{
					classType="normal";
					}
					else
					{
					classType="lightBg";				
					}	
					nSNo++;					
				%>	
						<tr class="<%=classType%>">
				
				
				
							<td align="center"><%=nSNo%></td>
								
								
									<td>
									<a href="javascript:fnViewOrder('<bean:write name="row" property="searchCRMOrder" />');">
							<font color="Brown">                       
								<bean:write name="row" property="searchCRMOrder" />
							</font>
						</a>
										
						
					</td>
									<td>
									<bean:write name='row' property='searchAccountNo' /></td>
									<td>
									<bean:write name='row' property='searchAccountName' /></td>
									<td align="center">
									<bean:write name='row' property='searchOrderType' /></td>
									<td colspan="2" align="center">
									<bean:write name='row' property='searchfromDate' /></td>
									
									<td>
									<bean:write name='row' property='searchSource' /></td>
									<td>
									<bean:write name='row' property='searchQuoteNumber' /></td>
									
									<td>
									<bean:write name='row' property='searchCurrency' /></td>
										<td>
									<bean:write name='row' property='serviceName' /></td>
									
									
									
								</tr>
						</logic:iterate>
						</logic:notEmpty>
						
						
						
						
						
						
					</table>
</div>					
</html:form>


</BODY>
</html:html>
