<!--Tag Name Resource Name  Date		CSR No			Description -->

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
<title>Select Customer Logical LSI</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
var flag=0;
function getInfoAndUpdate(logicalSINO) 
{
	var callerWindowObj = dialogArguments;
	callerWindowObj.document.serviceAttributes.custSINo.value = logicalSINO;
	flag=1;
	window.close();
}

function DrawCustLogicalSITable()
{
	  
	   var callerWindowObj = dialogArguments;
	   var str;
	   var frm=document.getElementById('logicalSIFormId');
	   var serviceId=callerWindowObj.document.serviceAttributes.serviceID.value;
	   
	   var mytable = document.getElementById('tabaccountType');	
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
				custSINo:document.getElementById('CustLogicalSI').value,
				serviceIdString:serviceId
			};
			
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	var lstCustLogicalSI = jsonrpc.processes.getCustLogicalSIDetailsWithSorting(jsData);				
	
	iTotalLength=lstCustLogicalSI.list.length;	
		if(iTotalLength !=0)
		{
		iNoOfPages = lstCustLogicalSI.list[0].maxPageNo;
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
		var tblRow=document.getElementById('tabaccountType').insertRow();
		tblRow.className=colors[colorValue];
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		//[001] Start
		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+escape(lstCustLogicalSI.list[i].logicalSINumber) + "') />";
		//[001] End
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstCustLogicalSI.list[i].logicalSINumber;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstCustLogicalSI.list[i].serviceId;
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
		var tblRow=document.getElementById('tabaccountType').insertRow();
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
	//document.getElementById('txtAccountName').value="";
	//document.getElementById('txtShortCode').value="";
	//document.getElementById('txtAccountID').value="";
	
	//document.getElementById('txtserviceId').value="";
	document.getElementById('txtcustLogicalSI').value="";
}
var path='<%=request.getContextPath()%>';
function isBlankFields()
{
	
	
	var custLogicalSI=document.getElementById('txtcustLogicalSI').value;
	
		if(document.getElementById('txtcustLogicalSI').value=="0")
		{
			alert("Value 0 is not allowed");
			return false;
		}
	
		var searchFlag=1;
		if(document.getElementById('txtcustLogicalSI').value.length > 0)
		{
			if(checkdigits(document.getElementById('txtcustLogicalSI')))
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
			searchFromList('LOGICALSINO','SELECTLOGICALSINO');
		}
		
		
		
	
}
function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     		isBlankFields();
    }      
}

function setDefaultValue()
{
	var callerWindowObj = dialogArguments;
	if (flag==0){
		if(callerWindowObj.document.serviceAttributes.custSINo.value>0)
		{
		}
		else
		{
		callerWindowObj.document.serviceAttributes.custSINo.value = "";
		
		}
	}
	
}



</script>
</head>
<body onunload="javascript:setDefaultValue();">
<div class="head"> <span>Cust Logical SI List </span> </div>
<center>
<html:form action="/NewOrderAction" styleId="logicalSIFormId" method="post">
<bean:define id="formBean" name="newOrderBean"></bean:define>
<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('LOGICALSINO','SELECTLOGICALSINO')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('LOGICALSINO','SELECTLOGICALSINO')">Next</a></td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo"  class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('LOGICALSINO','SELECTLOGICALSINO')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('LOGICALSINO','SELECTLOGICALSINO')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Cust Logical SI NO:</strong><input type="text" id="txtCustLogicalSI" name="custLogicalSI" class="inputBorder1" maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();" ></div>
					</td>
									
					<td>
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
					<!-- <div class="searchBg" onclick="DrawAccountType()"  style="margin-right:10px;" title="Search here"><a href="#">Search</a></div></td>-->
				</tr>
	</table>
	<table width="100%" border="1"  id="tabaccountType" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="2%" align="center">Select</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('LOGICALSINO','SELECTLOGICALSINO')">Cust Logical SI No</a>
				</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('SERVICEID','SELECTLOGICALSINO')">Service Id</a>
				</td>				
							
			</tr>								
	</table>
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<td width="18%" >&nbsp;</td>
			<td width="20%" colspan="3">&nbsp;</td>
		</tr>
	</table>	
</html:form>
</center>
</body>
<script type="text/javascript">
	
	DrawTable('LOGICALSINO','SELECTLOGICALSINO')	
</script>
</html>