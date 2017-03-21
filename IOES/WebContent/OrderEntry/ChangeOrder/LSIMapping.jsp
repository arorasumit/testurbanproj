 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--Tag Name Resource Name  	Date		CSR No			Description -->
<!--		 Rohit Verma		19-FEB-13  00-07481		New Interface to show Mapping & Redirection Between LSI  -->
<!-- [001] Gunjan Singla                                added  jsonrpc populatereportusagedetails method-->
<!-- [002] VIPIN SAHARIA	27th Aug 2015  Set interface id to -1 as exception was coming while fetching Linking Details GUI-->
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<html>
<head>
<title>LSI Mapping & Redirection</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
var userId = '<%=request.getAttribute("userId")%>';
var interfaceId = '<%=request.getParameter("interfaceId")%>';
var actionType;
function DrawLSIMapping()
{
   var str;
   var frm=document.getElementById('logicalTypes');
   var mytable = document.getElementById('tabLogicalType');	
   var iCountRows = mytable.rows.length;
   interfaceId=-1; // [002] earlier passing null so exception was coming hence set to -1 
   for (i = 2; i <  iCountRows; i++)
   {
       mytable.deleteRow(2);
   } 
	
	document.getElementById('txtPageNumber').value= pageNumber;   	
	document.getElementById('id_paging_goToPage').value= pageNumber;   	
	sync();			
	var index_var=0;
   	index_var=startRecordId;
	try
	{
		var pagingDto=
		{
			startIndex:startRecordId,
			endIndex:endRecordId,
			sortBycolumn:sortByColumn,
			sortByOrder:sortByOrder
		};
	   var jsData =
		{	
			
			lsiNO:document.getElementById('logicalSINo').value,
			redirectedLSINo:document.getElementById('redLSiNumber').value,
			mappedLSINo:document.getElementById('mappedLSINumber').value	,
			accountNo:document.getElementById('crmAccountNo').value	,
			accountName:document.getElementById('accountName').value,
			pagingDto:pagingDto,
			pagingRequired:1
		};	
 
		jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		var listLogicalSINumber = jsonrpc.processes.getMappingLSINumber(jsData);
	
		var searchName= '<%= AppConstants.ACTION_SEARCH %>';
		var jsData =	{
			userId:userId,
			interfaceId:interfaceId,
			actionType:searchName
			
		};		
	    
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		jsonrpc.processes.populateReportUsageDetails(jsData);
	}
	catch(e)
	{
		alert("Exception :"+e);
	}
	var iTotalLength=listLogicalSINumber.list.length;
	if(iTotalLength !=0)
	{
		iNoOfPages = listLogicalSINumber.list[0].maxPageNo;
	}
	else
	{     
	        iNoOfPages=1;
	}
	var counter = <%=request.getAttribute("count")%>;
	
	document.getElementById('txtMaxPageNo').value=iNoOfPages;				
	var colors=new Array("normal","lightBg");
	 
	 for (i = 0; i <  iTotalLength; i++)
	 {	
	 	var colorValue=parseInt(i)%2;
		var tblRow=document.getElementById('tabLogicalType').insertRow();
		tblRow.className=colors[colorValue];
		
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<label>" +index_var+ "</label>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].accountNo);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].accountName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].lsiNO);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].productName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].lineNo);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].lineName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].redirectedLSINo);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].redirectedProductName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].redirectedLineNo);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].redirectedLineName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].mappedLSINo);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].mappedProductName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].mappedLineNo);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].mappedLineName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		index_var++;
	}	
	if(listLogicalSINumber.list.length == 0)
	{
	var tblRow=document.getElementById('tabLogicalType').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 15;
		str='No Records Found';
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}	
	return false;
}
function clearFields()
{
	document.getElementById('logicalSINo').value="";
	document.getElementById('redLSiNumber').value="";
	document.getElementById('mappedLSINumber').value="";
	document.getElementById('crmAccountNo').value="";
	document.getElementById('accountName').value="";
}

function isBlankFields()
{
	var LSINumber=document.getElementById('logicalSINo');
	var RedLSINumber=document.getElementById('redLSiNumber');	
	var MappedLSINumber=document.getElementById('mappedLSINumber');	
	var AccountNo=document.getElementById('crmAccountNo');
	var AccountName=document.getElementById('accountName');
	var searchFlag=1;
	if( trim(LSINumber.value).length>0)
	{ 		
		if(checkdigits(LSINumber))
		{
			searchFlag=1;
		}
		else
		{
			searchFlag=0;
		}	  		  		  	  		  	 	 
	}
	if( trim(RedLSINumber.value).length>0)
	{ 		
		if(checkdigits(RedLSINumber))
		{
			searchFlag=1;
		}
		else
		{
			searchFlag=0;
		}	  		  		  	  		  	 	 
	}
	if( trim(MappedLSINumber.value).length>0)
	{ 		
		if(checkdigits(MappedLSINumber))
		{
			searchFlag=1;
		}
		else
		{
			searchFlag=0;
		}	  		  		  	  		  	 	 
	}
	if( trim(AccountNo.value).length>0)
	{ 		
		if(checkdigits(AccountNo))
		{
			searchFlag=1;
		}
		else
		{
			searchFlag=0;
		}	  		  		  	  		  	 	 
	}	
	if( trim(AccountName.value).length>0)	
	{				
		if(ValidateTextField(AccountName,path,'Account Name')=='undefined')
		{
			 searchFlag=0;
			 return true;
		}
	}
	if(searchFlag==1)
	{
		searchFromList('SOURCE_LOGICAL_SI_NO','LSIMAPPING');
		//DrawLSIMapping();
	}		
}
	
function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     	isBlankFields();
    }      
}
function goToHome()
{
	var myForm=document.getElementById('logicalTypes');
	myForm.toExcel.value='false';
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}
function exportToExcel()
{	
	var myForm=document.getElementById('logicalTypes');
	myForm.toExcel.value='true';
	var excelName= '<%= AppConstants.ACTION_EXCEL %>';
	var jsData =	{
		userId:userId,
		interfaceId:interfaceId,
		actionType:excelName
		
	};		
    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	jsonrpc.processes.populateReportUsageDetails(jsData); 
	myForm.action="<%=request.getContextPath()%>/reportsAction.do?method=viewLSIMapping&inExcel=true";
	myForm.submit();
}
path='<%=request.getContextPath()%>';
</script>
</head>
<body >
<html:form action="/reportsAction" styleId="logicalTypes" method="post">
	<html:hidden property="toExcel"/>
	<bean:define id="formBean" name="reportsBean"></bean:define>
	<div class="head"> <span>LSI Mapping & Redirection</span> </DIV>
		<fieldset class="border1">
			<legend> <b>LSI Mapping & Redirection</b> </legend>
			<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="left" ><a href="#" onclick="FirstPage('SOURCE_LOGICAL_SI_NO','LSIMAPPING')">First</a>&nbsp;&nbsp;<a href="#" onclick="NextPage('SOURCE_LOGICAL_SI_NO','LSIMAPPING')">Next</a></td>
		<td align="center"  class="last">Go To Page :
			<input type="text" class="inputNew" name="goToPageNumber" size="4" 
				maxlength="10" value="" id="id_paging_goToPage">
				<a href="#" onclick="goToPageSubmit('SOURCE_LOGICAL_SI_NO','LSIMAPPING')">GO </a>
		</td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo"  class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="right"><a href="#" onclick="PrevPage('SOURCE_LOGICAL_SI_NO','LSIMAPPING')">Prev</a>&nbsp;&nbsp;<a href="#" onclick="LastPage('SOURCE_LOGICAL_SI_NO','LSIMAPPING')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
	   <tr class="normal">
		   <td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
	   		<td  align="center" colspan="4">
				<div style="float:left;"><strong>A\c Number:</strong><html:text  property="crmAccountNo" styleId="crmAccountNo"  onkeypress="onPressEnterSearch(event)" styleClass="inputBorder1"></html:text></div>
			</td>
			<td  align="center" colspan="4">
				<div style="float:left;"><strong>A\c Name:</strong><html:text  property="accountName" styleId="accountName" onkeypress="onPressEnterSearch(event)"  styleClass="inputBorder1"></html:text></div>
			</td>
			<td  align="center" colspan="4">
				<div style="float:left;"><strong>LSI No:</strong><html:text  property="logicalSINo" styleId="logicalSINo" onkeypress="onPressEnterSearch(event)"  styleClass="inputBorder1"></html:text></div>
			</td>
			<td  align="center" colspan="4">
				<div style="float:left;"><strong>Redirected LSI No:</strong><html:text  property="redLSiNumber" styleId="redLSiNumber" onkeypress="onPressEnterSearch(event)"  styleClass="inputBorder1"></html:text></div>
			</td>
			<td  align="center" colspan="4">
				<div style="float:left;"><strong>Mapped LSI No:</strong><html:text  property="mappedLSINumber"  styleId="mappedLSINumber" onkeypress="onPressEnterSearch(event)"  styleClass="inputBorder1"></html:text></div>
			</td>
			<td>
				<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
				<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;
				<!-- <div class="searchBg" onclick="DrawAccountType()"  style="margin-right:10px;" title="Search here"><a href="#">Search</a></div></td>-->
			</td>
			
			<td align="right" style="padding-right:10px;" colspan="5">
				<img title="Export To Excel" src="./gifs/top/excel1.gif" onClick="javascript:exportToExcel();"></img> 
			</td>
		</tr>
	</table>
	<table width="100%" border="1"  id="tabLogicalType" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
		<tr class="grayBg">
			<td width="8%" align="center" colspan="3"><b>Account Details</b></td>
			<td width="8%" align="center" colspan="4"><b>LSI Details</b></td>
			<td width="8%" align="center" colspan="4"><b>Redirected LSI Details</b></td>
			<td width="8%" align="center" colspan="4"><b>Mapped LSI Details</b></td>				
		</tr>
		<tr class="grayBg">
			<td width="2%" align="center"><b>S.No.</b></td>
			<td width="5%" align="center"><b>A\C No</b></td>
			<td width="9%" align="center"><b>A\C Name</b></td>
			<td width="5%" align="center"><b>LSI No</b></td>
			<td width="10%" align="center"><b>Service Name</b></td>
			<td width="5%" align="center"><b>Line No</b></td>
			<td width="10%" align="center"><b>Line Name</b></td>
			<td width="5%" align="center"><b>LSI No</b></td>
			<td width="10%" align="center"><b>Service Name</b></td>
			<td width="5%" align="center"><b>Line No</b></td>
			<td width="10%" align="center"><b>Line Name</b></td>
			<td width="5%" align="center"><b>LSI No</b></td>
			<td width="10%" align="center"><b>Service Name</b></td>
			<td width="5%" align="center"><b>Line No</b></td>
			<td width="10%" align="center"><b>Line Name</b></td>					
		</tr>								
	</table>
</fieldset>
</html:form>
</body>
<script type="text/javascript">
	//DrawLSIMapping()
	DrawTable('SOURCE_LOGICAL_SI_NO','LSIMAPPING')
</script>
</html>


	

