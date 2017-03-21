<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>

<html>
<head>
<title>Dispatch Address Details</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
var path='<%=request.getContextPath()%>';

function DrawDispatchListTable()
{
	var lstDispatchDetails;
	var sessionid ='<%=request.getSession().getId() %>';
	   try
	   {
		   var str,i;		  	  
		   var mytable = document.getElementById('tabViewDispatchDetailsId');	
		   var iCountRows = mytable.rows.length;
		   
		   for (i = 1; i <  iCountRows; i++)
		   {
		       mytable.deleteRow(1);
		   }   	
		    
		   document.getElementById('txtPageNumber').value= pageNumber;
		   sync();	   	   	   	   
		   var jsData =
				{
					startIndex:Number(startRecordId),
					endIndex:Number(endRecordId),
					sortBycolumn:sortByColumn,
					sortByOrder:sortByOrder,
					accountID:document.getElementById('hdnAccountID').value,
					dispatchAddressID:Number(document.getElementById('txtDispatchId').value),					
					sessionid:sessionid
				};
				
			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");						
			lstDispatchDetails = jsonrpc.processes.getDispatchDetailsWithSorting(jsData);							
			
			if(lstDispatchDetails==null)
			{
				var callerWindowObj = dialogArguments;
				var myForm=callerWindowObj.document.getElementById('accountFormId');				
				myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=invalidsessoion";
				myForm.submit();
				alert("Session has been expired!!");		
				window.close();	
			}
		
			iTotalLength=lstDispatchDetails.list.length;	
			if(iTotalLength !=0)
			{
				iNoOfPages = lstDispatchDetails.list[0].maxPageNo;
			}		
	        else
			{     
		        iNoOfPages=1;
			}
		
			document.getElementById('txtMaxPageNo').value=iNoOfPages;				
			var colors=new Array("normal","lightBg");
			
			for (i = 0 ; i <iTotalLength; i++) 
			{		 		
		 		var colorValue=parseInt(i)%2;	 		  
				var tblRow=document.getElementById('tabViewDispatchDetailsId').insertRow();
				tblRow.className=colors[colorValue];							
			
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstDispatchDetails.list[i].dispatchAddressID;
				CellContents = str;
				tblcol.innerHTML = CellContents;
			
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstDispatchDetails.list[i].dispatchAddressName;
				if(str==" "){
					str="&nbsp;&nbsp;";					
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;
			
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";				
				str=lstDispatchDetails.list[i].dispatchAddress1;
				if(str==" "){
					str="&nbsp;&nbsp;";					
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;
			
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstDispatchDetails.list[i].dispatchAddress2;
				if(str==" "){
					str="&nbsp;&nbsp;";					
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstDispatchDetails.list[i].dispatchAddress3;
				if(str==" "){
					str="&nbsp;&nbsp;";					
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstDispatchDetails.list[i].dispatchAddress4;
				if(str==" "){
					str="&nbsp;&nbsp;";					
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstDispatchDetails.list[i].dispatchPhoneNo;
				if(str==" "){
					str="&nbsp;&nbsp;";					
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstDispatchDetails.list[i].dispatchEmail;
				if(str==" "){
					str="&nbsp;&nbsp;";					
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstDispatchDetails.list[i].dispatchPinNo;
				if(str==" "){
					str="&nbsp;&nbsp;";					
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstDispatchDetails.list[i].dispatchCityName;
				if(str==" "){
					str="&nbsp;&nbsp;";					
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstDispatchDetails.list[i].dispatchStateName;
				if(str==" "){
					str="&nbsp;&nbsp;";					
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstDispatchDetails.list[i].dispatchCountyName;
				if(str==" "){
					str="&nbsp;&nbsp;";					
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstDispatchDetails.list[i].dispatchLSTNo;
				if(str==" "){
					str="&nbsp;&nbsp;";					
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstDispatchDetails.list[i].dispatchDesignation;
				if(str==" "){
					str="&nbsp;&nbsp;";					
				}
				CellContents = str;
				tblcol.innerHTML = CellContents;												
		}
					
		var pagenumber=document.getElementById('txtPageNumber').value;
		var MaxPageNo=document.getElementById('txtMaxPageNo').value;
		if((pagenumber == 1) && (MaxPageNo==1))
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
			var tblRow=document.getElementById('tabViewDispatchDetailsId').insertRow();
		    tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			tblcol.colSpan = 12;
			str='NO RECORD FOUND'
			CellContents = str;
			tblcol.innerHTML = CellContents;
		}	
		return false;
	}
	catch(e)
	{
		alert(e);		
	}
}
function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     		isBlankFields();
    }      
}
function isBlankFields()
{		
		if(document.getElementById('txtDispatchId').value=="0")
		{
			alert("Value 0 is not allowed");
			return false;
		}
	
		var searchFlag=1;
		if(document.getElementById('txtDispatchId').value.length > 0)
		{
			if(checkdigits(document.getElementById('txtDispatchId')))
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
			searchFromList('DISPATCH_ADDRESS_CODE','VIEWDISPATCHADDRESS');
		}							
}

function clearFields()
{
	document.getElementById('txtDispatchId').value="";	
}
//[001] Start
function exportToExcel()
{	
	var myForm=document.getElementById('viewDispatchFormId');
	var accountId=<%=request.getParameter("accountId")%>
	myForm.toExcel.value='true';
	myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=goToViewDispatchDetailsPage&inExcel=true&accountId="+accountId;
	myForm.submit();
}
//[001] End
</script>
</head>
<body>
<div class="head"> <span>Dispatch Address Details</span> </div>

<html:form action="/NewOrderAction" styleId="viewDispatchFormId" method="post">
<html:hidden property="toExcel"/>
<div>
<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('DISPATCH_ADDRESS_CODE','VIEWDISPATCHADDRESS')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('DISPATCH_ADDRESS_CODE','VIEWDISPATCHADDRESS')">Next</a></td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('DISPATCH_ADDRESS_CODE','VIEWDISPATCHADDRESS')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('DISPATCH_ADDRESS_CODE','VIEWDISPATCHADDRESS')">Last</a></td>
	</tr>
</table>
</div>
<div>	   		
<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
 <tr class="normal">
<td  align="center" colspan="6">
	<div style="float:left;"><strong>Search Dispatch ID:</strong><input type="text" id="txtDispatchId" name="DispatchId" class="inputBorder1" maxlength="25" onkeydown="if (event.keyCode == 13)  return isBlankFields();" >
	<img onclick="isBlankFields()" src="${pageContext.request.contextPath}/npd/Images/search.gif" title="search"  height="15">
	<!-- [001] Start--> 
		<img title="Export Dispatch Details" src="./gifs/top/excel1.gif" onClick="javascript:exportToExcel();"></img> 
	<!-- [001] End-->
	<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" height="15" onclick="clearFields();"/></div>																			
	</tr>
</table>
</div>
<div>
<table width="100%" border="1"  id="tabViewDispatchDetailsId" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="10%" align="center">
					<a href="#" onclick="sortOrder('DISPATCH_ADDRESS_CODE','VIEWDISPATCHADDRESS')">DISPATCH ID</a>
				</td>
				<td width="20%" align="center">
					DISPATCH NAME
				</td>
				<td width="40%" align="center">
					ADDRESS1
				</td>
				<td width="20%" align="center">
					ADDRESS2
				</td>
				<td width="20%" align="center">
					ADDRESS3
				</td>
				<td width="20%" align="center">
					ADDRESS4
				</td>
				<td width="10%" align="center">
					CONTACT NO
				</td>
				<td width="10%" align="center">
					EMAIL
				</td>
				<td width="10%" align="center">
					PIN CODE
				</td>
				<td width="10%" align="center">
					CITY
				</td>
				<td width="10%" align="center">
					STATE
				</td>
				<td width="10%" align="center">
					COUNTRY
				</td>		
				<td  align="center">
					LST NO
				</td>	
				<td width="20%" align="center">
					DESIGNATION
				</td>			
			</tr>								
</table>
</div>
<div>
<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
	<tr class="grayBg">
		<td align="center" vAlign="top" colspan="12">			
		<input type="button"	name="btnOK" value="OK" width="5" onClick="window.close();">			
		</td>			
	</tr>
</table>
</div>
<html:hidden property="accountID" name="newOrderBean" styleId="hdnAccountID" />
</html:form>
</body>
<script type="text/javascript">
	
	DrawTable('DISPATCH_ADDRESS_CODE','VIEWDISPATCHADDRESS');
		
</script>
</html>