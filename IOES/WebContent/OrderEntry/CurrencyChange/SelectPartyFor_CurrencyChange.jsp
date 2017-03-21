<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.beans.CurrencyChangeBean"%>
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<html>
<head>
<title>Select Party</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
function getInfoAndUpdate(partyNo,partyName) 
{
	var callerWindowObj = dialogArguments;
	
	
		callerWindowObj.document.searchForm.sourcePartyNo.value = partyNo;
		callerWindowObj.document.searchForm.sourcePartyName.value = unescape(partyName);
		window.close();
	
	
}





function DrawPartyTable()
{

    
	   var str;
	   var frm=document.getElementById('parties');
	    
	   var mytable = document.getElementById('tabParty');	
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
				sourcePartyID:0,                               //set sourcePartyID 0 For fetching all parties
				party_num:document.getElementById('txtPartyNo').value,
				partyName:document.getElementById('txtPartyName').value
				
			};   
	  		 
	 jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	var lstParty = jsonrpc.processes.fetchPartywithSorting(jsData);
	
	   iTotalLength=lstParty.list.length;	
		if(iTotalLength !=0)
		
		{
		iNoOfPages = lstParty.list[0].maxPageNo;
		}


        else
		{     
	        iNoOfPages=1;
		}
	
	document.getElementById('txtMaxPageNo').value=iNoOfPages;	 
	
	
	
	var counter = <%=request.getAttribute("count")%>;
	  
	 for (i = 0; i <  lstParty.list.length; i++)
	 {
	    //var partyNo=lstParty.list[i].sourcePartyNo;
	    //var partyName=lstParty.list[i].sourcePartyName;
		var tblRow=document.getElementById('tabParty').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";

		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(lstParty.list[i].sourcePartyNo) +"','"+ escape(lstParty.list[i].sourcePartyName) +"') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstParty.list[i].sourcePartyNo;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstParty.list[i].sourcePartyName;
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
			
		
	if(lstParty.list.length == 0)
	{
	var tblRow=document.getElementById('tabParty').insertRow();
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
	document.getElementById('txtPartyNo').value="";
	document.getElementById('txtPartyName').value="";
	
}
var path='<%=request.getContextPath()%>';
function isBlankFields()
{
	var partyNo=document.getElementById('txtPartyNo').value;
	var partyName=document.getElementById('txtPartyName').value;
	
	
		if(document.getElementById('txtPartyNo').value=="0")
		{
			alert("Value O is not allowed");
			return false;
		}
	
		var searchFlag=1;
		if(document.getElementById('txtPartyNo').value.length > 0)
		{
			if(checkdigits(document.getElementById('txtPartyNo')))
			{
			searchFlag=1;
			}
			else
			{
			 searchFlag=0;
			}
		}
		if( trim(document.getElementById('txtPartyName').value).length>0)
		{
			if(ValidateTextField(document.getElementById('txtPartyName'),path,'Party Name')==false)
			{
			searchFlag=0;
			return false;
			}
		}
		
		
		
		if(searchFlag==1)
		{
			searchFromList('PARTY_NO','SELECTPARTY');
		}
		
		
		
	
}
function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     		isBlankFields();
    }      
}



</script>
</head>
<body>
<html:form action="/currencyChange" styleId="parties" method="post">
<bean:define id="formBean" name="currencyChangeBean"></bean:define>
<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
<!-- start [003] -->
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('PARTY_NO','SELECTPARTY')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('PARTY_NO','SELECTPARTY')">Next</a></td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('PARTY_NO','SELECTPARTY')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('PARTY_NO','SELECTPARTY')">Last</a></td>
	</tr>
	</table>
	
	 <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Party No:</strong><input type="text" id="txtPartyNo" name="txtPartyNo" class="inputBorder1" maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();" ></div>
					</td>
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Party Name:</strong><input type="text" id="txtPartyName" name="txtPartyNo" class="inputBorder1"  maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
					</div></td>
				    <td>
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
					<!-- <div class="searchBg" onclick="DrawAccountType()"  style="margin-right:10px;" title="Search here"><a href="#">Search</a></div></td>-->
				</tr>
	</table>
	
	
	<fieldset class="border1">
			<legend> <b>Party List</b> </legend>
			<table width="100%"  border="1" id="tabParty" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   	<tr class="grayBg">
				    <td width="3%" align="center">Select</td>
				    	<td width="8%" align="center">
				    <a href="#" onclick="sortOrder('PARTY_NO','SELECTPARTY')">Party No</a>
				    </td>
					<td width="16%" align="center">
					
					  <a href="#" onclick="sortOrder('PARTYNAME','SELECTPARTY')">Party Name</a>
					</td>
				</tr>
				
			</table>
		</fieldset>
</html:form>
</body>
<script type="text/javascript">
	//DrawAccountType()
	DrawTable('PARTY_NO','SELECTPARTY')
	
	// <!-- end [003] -->
</script>
</html>


	
