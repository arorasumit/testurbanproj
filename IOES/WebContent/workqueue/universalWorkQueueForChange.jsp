<!-- Created by Ramana on 11-March-2011 -->

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
<html>
<head>
<title>:: Universal Work Queue: Integrated Order Entry System</title>
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
<script language="javascript"  type="text/javascript">
var moduleName = "<%=request.getAttribute("ModuleName")%>";
function goToHome()
{
	document.forms[0].action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	showLayer();
	document.forms[0].submit();
}

function fnViewOrder(orderNo,ordertype)
{
	 // document.forms[0].checkedOrderNo.value=orderNo;
      //document.forms[0].action="./viewOrderAction.do?methodName=viewOrder&orderNo="+orderNo;
      
      var modeValue="editON";
      if(ordertype=="New")
      {
	      document.forms[0].action="./NewOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
	      showLayer();
	      document.forms[0].submit();
	  }
      if(ordertype=="Change")
      {
	      document.forms[0].action="./ChangeOrderAction.do?method=incompleteOrder&orderNo="+orderNo+"&modeName="+modeValue;
	      showLayer();
	      document.forms[0].submit();
	  }
	      
}
//Added by Ashutosh For SubChangeType
function selectOrderForChange(orderNo,ordertype) 
{
	//var moduleName = "<%=request.getAttribute("ModuleName")%>";
	if(moduleName=="DocumentTracking")
	{
		var path = "<%=request.getContextPath()%>/universalWorkQueueAction.do?methodName=viewDocumentTrackingList&orderNo="+orderNo;		
		window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:500px");
	}
	else
	{
    var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=fetchOrderForChange&orderNo="+orderNo;		
	window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
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
		myForm.action="<%=request.getContextPath()%>/universalWorkQueueAction.do?methodName=viewBillingOrderForChange&moduleName="+moduleName;
		showLayer();
		myForm.submit(); 
	}		
			
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
     	myForm.method.value='viewBillingOrderForChange';	
		showLayer();
		myForm.submit();
     
	//myForm.method.value='viewUniversalWorkQueue_new';	
		//showLayer();
	//myForm.submit();
}

function pagingSubmit(val)
{
	var myForm=document.getElementById('searchForm');
	moduleName = "<%=request.getAttribute("ModuleName")%>";	
	//setFormBean(myform);
	//myform.reset();
	if(moduleName=="DocumentTracking")
	{
		myForm.elements["pagingSorting.pageNumber"].value=val;	  
     	myForm.action="<%=request.getContextPath()%>/universalWorkQueueAction.do?methodName=viewBillingOrderForDocumentTracking";
		showLayer();
		myForm.submit();    
	}
	else
	{
		myForm.elements["pagingSorting.pageNumber"].value=val;	  
     	myForm.action="<%=request.getContextPath()%>/universalWorkQueueAction.do?methodName=viewBillingOrderForChange";
		showLayer();
		myForm.submit();    
	}
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

path='<%=request.getContextPath()%>';
</script>
</head>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body>
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<html:form action="/universalWorkQueueAction" method="post" styleId="searchForm">
<table border="0" align="center" cellpadding="0" cellspacing="0" width="98%">
<tr align="right">
	<td><img src="./gifs/top/home.gif" onClick="goToHome();"></img></td>
</tr>
<tr valign="middle" id="newProduct">
		<td valign="bottom" width="100%" align="center">
		<table width="100%" border="0" cellspacing="0" cellpadding="0"
			align="center" class="tab2">
			<tr>					
					<td align="right" style="padding-right:10px;">
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
					<a href="#"><img onclick="searchSubmit();" src="npd/Images/search.gif" title="search" height="15"></a>&nbsp;
				    </td>								
			</tr>
		</table>
		<%String []colors=new String[]{"normal","lightBg"}; %>
	<bean:define id="formBean" name="UniversalWorkQueueLogicalFormBean"></bean:define>
	<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
	<html:hidden name="formBean" property="pagingSorting.pageNumber"/>

	<html:hidden name="formBean" property="pagingSorting.sortByColumn"/>
	<html:hidden name="formBean" property="pagingSorting.sortByOrder"/>		
	<table width="100%" border="1" class="tab2" cellpadding="3" cellspacing="1" align="center">
		<tr>
			
							<td class="tab2" >
								  <bean:define id="pagingBean" name="formBean"></bean:define>					
					<%String  currPageNumber =String.valueOf(((UniversalWorkQueueFormBean)formBean).getPagingSorting().getPageNumber());
									  String  maxPageNumber =String.valueOf(((UniversalWorkQueueFormBean)formBean).getPagingSorting().getMaxPageNumber());
									%>					
					<jsp:include flush="true" page="/paging.jsp">
										<jsp:param name="currPageNumber" value="<%=currPageNumber %>"/>
										<jsp:param name="maxPageNumber" value="<%=maxPageNumber %>"/>							
									</jsp:include>
							</td>
	</tr>	

   </table>			
		</td>
	</tr>
		
<tr align="left">
	<logic:equal value="1" property="isBillingOrder" name="UniversalWorkQueueLogicalFormBean">
		<td class="head">&nbsp;Billing Order No For Change</td>		
	</logic:equal>	
</tr>
<tr>
	<td>&nbsp;<br><html:hidden property="checkedOrderNo"/></td>
</tr>
<tr>

	<td>
		<table border="0" align="center" cellpadding="0" cellspacing="0" class="tab2" width="98.5%" height="650px;" >
		
		<tr class="lightBgWihtoutHover">
		<logic:equal value="0" property="isBillingOrder" name="UniversalWorkQueueLogicalFormBean">
			<td width="20%" align="left" valign="top">
				<table width="100%"  border="0" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			
      </logic:equal>			
			<td width="80%" align="left" valign="top">
				<!--  Table for Order Required Approval From AM -->
				<div style="overflow:scroll;height:370px;width:100%" class="scroll">
						<table cellSpacing="0" cellPadding="0" width="100%" border="0" id="tbl1" class="tab2" align="center">
						<tr class="lightBgWihtoutHover">
							<td><b>SNo.</b></td>
							<td><b>CRM Order No</b></td>
							<td><b>Order Type</b></td>	
							<td><b>Order Date</b></td>		
							<td><b>Account No</b></td>						
							<td><b>Account Name</b></td>									
						    <td><b>Source Name</b></td>
						    <td><b>Quote Number</b></td>	
						    <td><b>Subchange Type</b></td>						  										
					   </tr>		
						<tr class="normal">
						<td width="1%"  >								
						</td>
							<td width="1%"  >
								<html:text property="searchCRMOrder" maxlength="8" size="5" styleClass="inputBorder1" styleId="id_orderno_search" onkeydown= "if(event.keyCode == 13){onEnterValidation(this)};" onchange="return validate(this)"/>
						   </td>
						   
						   
						   <td width="4%">
						      <html:select property="searchOrderType" styleId="id_ordertype_search" style="width:75px" styleClass="inputBorder1" onkeydown="if (event.keyCode == 13) return searchSubmit();">
								<html:option value="">--Select--</html:option>
								<html:optionsCollection name="listOrderType" label="orderType" value="orderType" />
							</html:select>	
							
												   	  
				           <td "width:10px">
				           		<table><tr><td>
				           		FROM DATE</td><td>				 
						        <html:text property="searchfromDate" size="10" readonly="true" styleId="datefrom" styleClass="inputBorder1"></html:text>
								</td><td>
								<font size="1">
											<a href="javascript:show_calendar(searchForm.searchfromDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
								</font></td><tr><td>
								TO DATE</td><td>
								<html:text property="searchToDate" size="10" readonly="true" styleId="dateTo" styleClass="inputBorder1"></html:text>
								</td><td>
								<font size="1">
											<a href="javascript:show_calendar(searchForm.searchToDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."></a>
									</font></td></tr>
								</table>
						</td>  
						   <td width="5%"  >
								<html:text property="searchAccountNo" maxlength="20" size="5" styleClass="inputBorder1" styleId="id_accountno_search" onkeydown= "if(event.keyCode == 13){onEnterValidation(this)};" onchange="return validate(this)"></html:text>
				           </td>
						   <td width="5%"  >
								<html:text property="searchAccountName" maxlength="20" size="30" styleClass="inputBorder1" styleId="id_accountname_search" onkeydown="if (event.keyCode == 13){validation(this)};" onchange="if( trim(this.value).length>0){return ValidateTextField(this,path,'Account Name')};"></html:text>
				           </td>
				           
				           
						   <td width="4%">
						      <html:select property="searchSource" styleId="searchSourceNameId" style="width:100px" styleClass="inputBorder1" onkeydown="if (event.keyCode == 13) return searchSubmit();"  >
								<html:option value="">--Select--</html:option>
								<html:optionsCollection name="listSourceName" label="searchSourceName" value="searchSourceName" />
							</html:select>
					       </td>
					       
					       
							<td width="5%"  >
							<html:text property="searchQuoteNumber" maxlength="8" size="5" styleClass="inputBorder1" styleClass="inputBorder1" styleId="id_orderdate_search" onkeydown= "if(event.keyCode == 13){onEnterValidation(this)};" onchange="if( trim(this.value).length>0){ return checkdigits(this)};"/>
					        </td>	
					        <td width="7%">
						      <html:select property="searchSubChangeType" styleId="id_subchange_search" style="width:75px"  onkeydown="if (event.keyCode == 13) return searchSubmit();">
								<html:option value="">--Select--</html:option>
								<html:optionsCollection name="listSubChangeType" label="subChangeType" value="subChangeType" />
							</html:select>	
							</td>			           					
						</tr>	
						
						<logic:equal value="1" property="isBillingOrder" name="UniversalWorkQueueLogicalFormBean">						
						<logic:empty name="formBean" property="billingWorkQueueList">
							<tr class="normal">
								<td colspan="12" align="center">No Project Found</td>
							</tr>						
						</logic:empty>
						<logic:notEmpty name="billingWorkQueueList" scope="request">
							<logic:iterate id="row"	name="billingWorkQueueList" scope="request" indexId="index1">
								<tr class="<%=colors[index1.intValue()%2]%>">
									<%	int count = ((index1.intValue()) + 1);%>
							    	<td nowrap><%=index1.intValue()+1 %></td>
									<td>
										<a href="javascript:selectOrderForChange('<bean:write name="row" property="searchCRMOrder" />','<bean:write  name="row" property="searchOrderType" />');">
										<font color="Brown"> 
										<bean:write name="row" property="searchCRMOrder" />
										</font>
										</a>
									</td>
									<td>
										<bean:write name="row" property="searchOrderType" />
									</td>
									<td>
										<bean:write name="row" property="searchfromDate" />
									</td>
									<td>
										<bean:write name="row" property="searchAccountNo" />
									</td>
									<td>
										<bean:write name="row" property="searchAccountName" />
									</td>
									<td>
										<bean:write name="row" property='searchSource' />
									</td>
									<td>
										<bean:write name="row" property='searchQuoteNumber' />
									</td>	
									<td>
										<bean:write name="row" property='subChangeType' />
									</td>																											
								</tr>
						</logic:iterate>
					</logic:notEmpty>
					</logic:equal>									
				</table>				
			</table>
	  	  </td>
		</tr>
	</table>   
</html:form>
</body>
</html>
