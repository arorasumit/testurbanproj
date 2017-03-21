<!--[001]	 LAWKUSH 		06-MAY-11	00-05422		Setting Paging ans sorting -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Select Currency</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script language="javascript" type="text/javascript">
function getInfoAndUpdate(curID,curCode) 
{
var callerWindowObj = dialogArguments;
    var currencyCount =(<%=request.getParameter("currencyCount")%>);    
    if(currencyCount > 0)
    {
	    if(callerWindowObj.document.getElementsByName('currencyID')==undefined)
		{
			callerWindowObj.document.getElementsByName('currencyID').value = unescape(curID);
			callerWindowObj.document.getElementsByName('curShortCode').value = unescape(curCode);
	    	
	    }
	    else
	    {
	    	callerWindowObj.document.getElementsByName('currencyID')[(<%=request.getParameter("currencyCount")%>)-1].value = unescape(curID);
	    	callerWindowObj.document.getElementsByName('curShortCode')[(<%=request.getParameter("currencyCount")%>)-1].value = unescape(curCode);
	    	
	    }
    }
	else{
	callerWindowObj.document.searchForm.currencyID.value = unescape(curID);
	callerWindowObj.document.searchForm.curShortCode.value = unescape(curCode);
	}
	window.close();
}

function DrawCurrencyType()
{
	   var str;
	   var frm=document.getElementById('currencyTypes');
	  
	   var mytable = document.getElementById('tabcurrencyType');	
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
				currencyName:document.getElementById('txtCurrencyName').value,
				currencyCode:document.getElementById('txtCurrencyCode').value
			};
				  
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	var lstCurrency = jsonrpc.processes.currencyType(jsData);
	
	var v_iTotalLength=lstCurrency.list.length;	
	if(v_iTotalLength !=0)
	{
		var v_iNoOfPages = lstCurrency.list[0].maxPageNo;
		iNoOfPages=v_iNoOfPages;
	}
	 else
	{     
	    v_iNoOfPages=1;
	}
	
	
	document.getElementById('txtMaxPageNo').value=v_iNoOfPages;				
	var colors=new Array("normal","lightBg");
	
	var counter = <%=request.getAttribute("count")%>;
	  
	 for (i = 0; i <  v_iTotalLength; i++)
	 {
	 	var colorValue=parseInt(i)%2;	 
	    var currencyTypeName=lstCurrency.list[i].currencyCode;
	    var currencyID=lstCurrency.list[i].currencyID;
		var tblRow=document.getElementById('tabcurrencyType').insertRow();
		tblRow.className=colors[colorValue];
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";

		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(lstCurrency.list[i].currencyID) +"','"+ escape(lstCurrency.list[i].currencyCode) +"') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=lstCurrency.list[i].currencyName;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=lstCurrency.list[i].currencyCode;
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
	
	if(v_iTotalLength== 0)
	{
	var tblRow=document.getElementById('tabcurrencyType').insertRow();
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
//
function clearFields()
{
	document.getElementById('txtCurrencyName').value="";
	document.getElementById('txtCurrencyCode').value="";	
}
var path='<%=request.getContextPath()%>';
function isBlankFields()
{
	var currencyName=document.getElementById('txtCurrencyName').value;
	var currencyCode=document.getElementById('txtCurrencyCode').value;
		
		var searchFlag=1;
	
		if( trim(document.getElementById('txtCurrencyName').value).length>0)
		{
			if(ValidateTextField(document.getElementById('txtCurrencyName'),path,'Currency Name')==false)
			{
				searchFlag=0;
				return false;
			}
		}
		
		if( trim(document.getElementById('txtCurrencyCode').value).length>0)
		{
			if(ValidateTextField(document.getElementById('txtCurrencyCode'),path,'Currency Code')==false)
			{
				searchFlag=0;
				return false;
			}
		}
		if(searchFlag==1)
		{
			searchFromList('CURNAME','SELECTCURRENCY');
		}
}
	function setValue()   //called this function on onload 
{
	
	var callerWindowObj = dialogArguments;
	document.getElementById('txtCurrencyCode').value=callerWindowObj.document.getElementById('curShortCode').value ;

}
//
</script>
</head>
<body>

<center>
<html:form action="/NewOrderAction" styleId="currencyTypes" method="post">
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
		<tr>
			<td align="center"><a href="#" id= "first" onclick="FirstPage('CURNAME','SELECTCURRENCY')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('CURNAME','SELECTCURRENCY')">Next</a></td>
			<td align="center">
				<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
				<input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
			<td align="center"><a href="#" id="prev" onclick="PrevPage('CURNAME','SELECTCURRENCY')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('CURNAME','SELECTCURRENCY')">Last</a></td>
		</tr>
	</table>			
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Currency Name:</strong><input type="text" maxlength="25" id="txtCurrencyName" name="currencyName" class="inputBorder1" onkeydown="if (event.keyCode == 13)  return isBlankFields();"   >
					</div></td>
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Currency Code:</strong><input type="text" maxlength="10" id="txtCurrencyCode" name="currencyCode" class="inputBorder1" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
					</div></td>					
					<td>
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
					<!-- <div class="searchBg" onclick="DrawAccountType()"  style="margin-right:10px;" title="Search here"><a href="#">Search</a></div></td>-->
				</tr>
	</table>
			<table width="100%"  border="1" id="tabcurrencyType" align="center" cellpadding="0" cellspacing="0" class="tab2" >			   
				<tr class="grayBg">
				    <td width="2%" align="center">Select</td>
					<td width="20%" align="left">
						<a href="#" onclick="sortOrder('CURNAME','SELECTCURRENCY')">Currency</a>				
					</td>
					<td width="8%" align="left">
						<a href="#" onclick="sortOrder('CURSHORTCODE','SELECTCURRENCY')">Currency Code</a>					
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
	//DrawCurrencyType()
	setValue()
	DrawTable('CURNAME','SELECTCURRENCY');
</script>
</html>


	
