<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><%@page
	language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="com.ibm.ioes.forms.ComponentsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<html>
<head>
<title>Select PR</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jsonrpc.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/formValidations.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/productCatelogUtility.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/utility.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/ViewProductCatalogForScm.js"></script>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
 
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script> 
<script>
var sessionId_for_update='<%=request.getSession().getId() %>';
        
var pr_Number;
var callerWindowObj = dialogArguments;
function savePR(seletedPrValue,selectedPrNumber){
  
    	  
   var jsData=
    {
	prId:seletedPrValue,
	pr_number:selectedPrNumber
	};
	 
    	 jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    	 var validPrNumberList = jsonrpc.processes.validatePrId(jsData);
    	 if(null!=validPrNumberList)
    	 {
    	 alert(validPrNumberList.prValidationMsg);
    	 return;
    	 } 
    	 else{
		localStorage.setItem('seletedPrId',seletedPrValue);
		localStorage.setItem('selectedPrNum',selectedPrNumber);
    	callerWindowObj.PopulateDataForPRReuse();
      	window.close();	
   		}
}

function clearFields()
{
	document.getElementById('txtServiceTypeName').value="";
	isBlankFields();
	
}
function isBlankFields()
{
	var serviceTypeName=document.getElementById('txtServiceTypeName').value;
	
	
	var searchFlag=1;
	
	

	 if(document.getElementById('txtServiceTypeName').value >0)
		{
			 if(checkdigits(document.getElementById('txtServiceTypeName')))
			 {
			 searchFlag=1;
			 }
			 else
			 {
			 searchFlag=0;
			 }
		}
		
		
		
		if(searchFlag==1)
		{
		searchFromList('PR_ID','SERVICEID');
		}
	
	
}
function DrawPrSearchTable()
{

var tr, td, i, j, oneRecord;
var mytable = document.getElementById('tabServiceType');
var rowCount=mytable.rows.length;



for(i=1;i<rowCount;i++)
{
 mytable.deleteRow(1);
}
document.getElementById('txtPageNumber').value= pageNumber;  
sync();

	
var jsData =			
    {
    	startIndex:startRecordId,
		endIndex:endRecordId,
		sortBycolumn:sortByColumn,
		sortByOrder:sortByOrder,
		prNumber:document.getElementById('txtServiceTypeName').value
				
	};
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	var lstService = jsonrpc.processes.populatePrSearch(jsData); 
	iTotalLength=lstService.list.length;
	
	
	if(iTotalLength !=0)
	{
		iNoOfPages = lstService.list[0].maxPageNo;
	}

	document.getElementById('txtMaxPageNo').value=iNoOfPages;				
	var colors=new Array("normal","lightBg");
	for (i = 0 ; i < iTotalLength; i++)
		    {
				var str;
				var colorValue=parseInt(i)%2;	 		  
				var tblRow=document.getElementById('tabServiceType').insertRow();
				tblRow.className=colors[colorValue];
						
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str="<input name='chk' id='chk' type='radio' onClick=savePR('"+lstService.list[i].prId+"','"+lstService.list[i].prNumber+"') value=''>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str= lstService.list[i].prNumber;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstService.list[i].circle;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstService.list[i].nfaNumber;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
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
		 
   		if(iTotalLength==0)		
		{
   
    		var tblRow=document.getElementById('tabServiceType').insertRow();
	    	var tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.colSpan = 4;
			tblcol.vAlign="top";
			tblcol.innerHTML = "No Records Found";
			document.getElementById('first').disabled=true;
		    document.getElementById('prev').disabled=true;
		    document.getElementById('last').disabled=true;
		    document.getElementById('next').disabled=true;
		    document.getElementById('txtPageNumber').value=1;
		    document.getElementById('txtMaxPageNo').value=1;
   		 }
   		 return false;
   		 
}
</script>
<title>PRDetails</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR"
	content="Rational® Application Developer for WebSphere® Software">
</head>
<body>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
 <html:form action="/NewOrderAction" styleId="searchForm" method="post">
		
		<bean:define id="formBean" name="newOrderBean"></bean:define>
		<html:hidden property="hdnServiceCounter"  />
		<div class="head"> <span>Select PR Number</span> </div>
		 
		<fieldset class="border1">
				
	
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('PR_ID','SERVICEID')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('PR_ID','SERVICEID')">Next</a></td>
		<td align="center"><input type="text" id="txtPageNumber" class="inputBorder1" readonly="true" size="2"/>of
		<input type="text" id="txtMaxPageNo" class="inputBorder1" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#"  id="prev" onclick="PrevPage('PR_ID','SERVICEID')">Prev</a>&nbsp;&nbsp;<a href="#"  id="last" onclick="LastPage('PR_ID','SERVICEID')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Pr Number:</strong><input type="text" id="txtServiceTypeName"  name="servicetype" maxlength=40 class="inputBorder1" onkeydown="if (event.keyCode == 13)  return isBlankFields();" onkeyup="if(this.value.length > 0){checkdigits(this);}isBlankFields();">
					</div></td>
					<td align="left">
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;<td>
					<!-- <div class="searchBg" onclick="DrawAccountType()"  style="margin-right:10px;" title="Search here"><a href="#">Search</a></div></td>-->
				</tr>
	</table>
	<table width="100%" border="1"  id="tabServiceType" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="2%" align="center">Select</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('PR_ID','SERVICEID')">PR Number</a>
				</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('PR_ID','SERVICEID')">CIRCLE</a>
				</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('PR_ID','SERVICEID')">NFA NUMBER</a>
				</td>
			</tr>								
	</table>
	
				
			</fieldset>
</html:form>	
  </body>
 <script type="text/javascript">
	
	DrawTable('PR_ID','SERVICEID')
</script>
</html>