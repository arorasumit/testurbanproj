<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.beans.CurrencyChangeBean"%>
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>Select Logical LSI</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
//Start[Date:01-04-2013 Paging Size Configurable for Bulkupload LSI Look up]---
<%
	HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
	HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
	UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>
//PAGE_SIZE_LSILOOKUP_LINE-01-04-2013
var PAGE_SIZE_LSILOOKUP_LINE="<%=objUserDto.getPageSizeLookUPLSILines()%>";
function DrawLSIBULKListTable()
{
	//------------Set Page Size for LSI Lookup from BulkUpload Line Table Paging Start --------------
   		pageRecords=PAGE_SIZE_LSILOOKUP_LINE;
   	//------------Set Page Size for LSI Lookup from BulkUpload Line Table Paging End ----------------
	   if(document.getElementById('chkAllLSI')!=null)
	   {
	   		document.getElementById('chkAllLSI').checked=false;
	   }	
	   var str,accountIDsList,subChangeType;	
	   var callerWindowObj = dialogArguments;
	     
	   accountIDsList=document.getElementById('hdnAccountIdList').value;
	   subChangeType=callerWindowObj.subChangeTypeId;		    
	   var frm=document.getElementById('logicalLsiFormId');
	  
	   var mytable = document.getElementById('tabLogicalLSIList');	
	   var iCountRows = mytable.rows.length;
		
	   for (i = 1; i <  iCountRows; i++)
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
				logicalSINo:document.getElementById('txtLogicalSI').value,
				searchAccountNo:document.getElementById('txtAccountNo').value,
				accountIDString:accountIDsList,
				subChangeTypeId:subChangeType,
				pageRecords:pageRecords				
			};
			
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	var listLogicalLsi = jsonrpc.processes.populateLSI(jsData);						
	
		iTotalLength=listLogicalLsi.list.length;	
		if(iTotalLength !=0)
		{
			iNoOfPages = listLogicalLsi.list[0].maxPageNo;			
		}
        else
		{     
	        iNoOfPages=1;
		}
	
	document.getElementById('txtMaxPageNo').value=iNoOfPages;				
	var colors=new Array("normal","lightBg");
	for (j = 0 ; j <iTotalLength; j++) 
	{		 		
	 		var colorValue=parseInt(j)%2;	 		  
			var tblRow=document.getElementById('tabLogicalLSIList').insertRow();
			tblRow.className=colors[colorValue];		
			
			var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str="<input name='chka' id='chka"+j+"' type='checkbox'  value='"+listLogicalLsi.list[j].logicalsiNo+"'>";
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str= listLogicalLsi.list[j].logicalsiNo;
			CellContents = str;
			tblcol.innerHTML = CellContents;
									
			var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str= listLogicalLsi.list[j].crmAccountNo;
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
		var tblRow=document.getElementById('tabLogicalLSIList').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 3;
		str='NO RECORD FOUND'
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}	
	return false;	
}

function clearFields()
{
	document.getElementById('txtLogicalSI').value="";
}
var path='<%=request.getContextPath()%>';

function isBlankFields()
{	
	var logicalLSI=document.getElementById('txtLogicalSI').value;
	
		if(document.getElementById('txtLogicalSI').value=="0")
		{
			alert("Value O is not allowed");
			return false;
		}
	
		var searchFlag=1;
		if(document.getElementById('txtLogicalSI').value.length > 0)
		{
			if(checkdigits(document.getElementById('txtLogicalSI')))
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
			searchFromList('LOGICALLSI','SELECTLSIFORBULKUPLOAD');
		}
			
}
function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     		isBlankFields();
    }      
}
function addLSIToList()
{
	var i=0,j=0;
	var selectedLSI="";
	var callerWindowObj = dialogArguments;
	callerWindowObj.addList=new Array();
	while(eval(document.getElementById("chka"+i)))
	{	
		if(eval(document.getElementById("chka"+i)))
		{
			if(eval(document.getElementById("chka"+i)).checked)
			{
				if(selectedLSI=="")
				{ 
					selectedLSI = eval(document.getElementById("chka"+i)).value;	
					callerWindowObj.addList[j]=	eval(document.getElementById("chka"+i)).value;		
					j=j+1;	
				}
				else
				{
					selectedLSI = selectedLSI+','+eval(document.getElementById("chka"+i)).value;	
					callerWindowObj.addList[j]=	eval(document.getElementById("chka"+i)).value;				
					j=j+1;
				}
			}
		}
		i=i+1;		
	}				
	callerWindowObj.submitLSI();
	window.close();
}

	function CheckAll()
	{
		var chk=true;
		if(document.getElementById('chkAllLSI').checked==false)
		{
			chk=false;
		}
		var i=0;
		while(eval(document.getElementById("chka"+i)))
		{
			eval(document.getElementById("chka"+i)).checked=chk;
			i=i+1;
		}
		
		
	}
	
	
</script>
</head>
<body>
<div class="head"> <span>Logical LSI List</span> </div>
<center>
<html:form action="/currencyChange" styleId="logicalLsiFormId" method="post">
<bean:define id="formBean" name="currencyChangeBean"></bean:define>
<bean:define id="formBeanPaging" name="currencyChangeBean"></bean:define>
<bean:define id="pagingSorting" name="formBeanPaging" property="pagingSorting"/>
<!-- start [003] -->
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('LOGICALLSI','SELECTLSIFORBULKUPLOAD')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('LOGICALLSI','SELECTLSIFORBULKUPLOAD')">Next</a></td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('LOGICALLSI','SELECTLSIFORBULKUPLOAD')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('LOGICALLSI','SELECTLSIFORBULKUPLOAD')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="4">
						<div style="float:left;"><strong>Logical LSI:</strong><input type="text" id="txtLogicalSI" name="logicalSI" class="inputBorder1" maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();" ></div>
					</td>
					<td  align="center" colspan="4">
						<div style="float:left;"><strong>Account NO:</strong><input type="text" id="txtAccountNo" name="logicalSI" class="inputBorder1" maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();" ></div>
					</td>										
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
					<!-- <div class="searchBg" onclick="DrawAccountType()"  style="margin-right:10px;" title="Search here"><a href="#">Search</a></div></td>-->
				</tr>
	</table>
	<table width="100%" border="1"  id="tabLogicalLSIList" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="4%" align="left">Select<input type="checkbox" id="chkAllLSI" align="top" onclick="CheckAll()"/></td>
				<td width="8%" align="left">
					<a href="#" onclick="sortOrder('LOGICALLSI','SELECTLSIFORBULKUPLOAD')">Logical LSI</a>
				</td>	
				<td width="8%" align="left">
					<a href="#" onclick="sortOrder('CRMACCOUNTNO','SELECTLSIFORBULKUPLOAD')">Account No</a>
				</td>											
			</tr>								
	</table>
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<td width="18%" colspan="4"><input type="button" name="btnOK" value="OK"
			style="width:100px" onclick="addLSIToList()"></td>			
		</tr>
	</table>	
	<html:hidden property="accountIdList" name="formBean"
			styleId="hdnAccountIdList" />
</html:form>
</center>
</body>
<script type="text/javascript">
	//DrawAccountType()
	DrawTable('LOGICALLSI','SELECTLSIFORBULKUPLOAD')
	
	// <!-- end [003] -->
</script>
</html>