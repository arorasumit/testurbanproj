<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.beans.SITransferBean"%>
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<html>
<head>
<title>Select Account</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">

function getInfoAndUpdate(accountno,accountId) 
{
	
    
	var callerWindowObj = dialogArguments;
	callerWindowObj.document.searchForm.accountnoString.value = unescape(accountno) + '-' + document.getElementById('hdnShortCode_'+unescape(accountno)).value;
	callerWindowObj.document.searchForm.m6ShortCode.value = document.getElementById('hdnShortCode_'+unescape(accountno)).value;
	callerWindowObj.document.searchForm.accountno.value = unescape(accountId) ;
	//unescape(m6ShortCode);
	

	window.close();
	
	
}
var path='<%=request.getContextPath()%>';

function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     		isBlankFields();
    }      
}

function isBlankFields()
{
	var partyNo=document.getElementById('txtActNo').value;
	var partyName=document.getElementById('txtshortcode').value;
	
	
		if(document.getElementById('txtActNo').value=="0")
		{
			alert("Value O is not allowed");
			return false;
		}
	
		var searchFlag=1;
		if(document.getElementById('txtActNo').value.length > 0)
		{
			if(checkdigits(document.getElementById('txtActNo')))
			{
			searchFlag=1;
			}
			else
			{
			 searchFlag=0;
			}
		}
		if( trim(document.getElementById('txtshortcode').value).length>0)
		{
			if(ValidateTextField(document.getElementById('txtshortcode'),path,'Short Code')==false)
			{
			searchFlag=0;
			return false;
			}
		}
		
		
		
		if(searchFlag==1)
		{
			searchFromList('CRMACCOUNTNO','SELECTACCOUNT_FORSITRANSFER');
		}
		
		
		
	
}
function clearFields()
{
	document.getElementById('txtActNo').value="";
	document.getElementById('txtshortcode').value="";
	
}


function DrawAccountTable()
{       
		var str;
 		var callerWindowObj = dialogArguments;
	    var frm=document.getElementById('accountids');
	    var mytable = document.getElementById('tabAccount');	
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
				accountIDString:document.getElementById('txtActNo').value,
				m6ShortCode:document.getElementById('txtshortcode').value,
			    party:callerWindowObj.document.searchForm.targetPartyNo.value
			};   
	  		 
		 jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		 var lstAccount = jsonrpc.processes.fetchAccountwithsorting(jsData);
		 iTotalLength=lstAccount.list.length;	
			if(iTotalLength !=0)
			
			{
			iNoOfPages = lstAccount.list[0].maxPageNo;
			}
	
	
	        else
			{     
		        iNoOfPages=1;
			}
	
		document.getElementById('txtMaxPageNo').value=iNoOfPages;	 
		var counter = <%=request.getAttribute("count")%>;
		
	
	
	
	if(lstAccount.list.length==1)
	{
	
	callerWindowObj.document.searchForm.accountnoString.value=lstAccount.list[0].accountno + '-'+ lstAccount.list[0].m6ShortCode;
	callerWindowObj.document.searchForm.m6ShortCode.value =lstAccount.list[0].m6ShortCode;
	//document.getElementById('hdnShortCode_'+lstAccount.list[0].accountno);
	//Account id
	callerWindowObj.document.searchForm.accountno.value=lstAccount.list[0].accountIDString;
	//alert(callerWindowObj.document.searchForm.m6ShortCode.value);
	// unescape(m6ShortCode);
	window.close();
	
	}
	else
	{
	
	  
	 for (i = 0; i <  lstAccount.list.length; i++)
	 {
	   
		var tblRow=document.getElementById('tabAccount').insertRow();
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";

		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(lstAccount.list[i].accountno)+ "','"+ escape(lstAccount.list[i].accountIDString) +"') />";
		str=str+"<input type='hidden' name='hdnShortCode' id='hdnShortCode_"+lstAccount.list[i].accountno+"' value='"+lstAccount.list[i].m6ShortCode+"'/>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].accountno;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].m6ShortCode;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
	}
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
			
		
	   

	if(lstAccount.list.length == 0)
	{
	var tblRow=document.getElementById('tabAccount').insertRow();
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
	   
	
</script>
</head>
<body>
<html:form action="/siTransfer" styleId="accountids" method="post">
<bean:define id="formBean" name="siTransferBean"></bean:define>
<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
<!-- start [003] -->
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('CRMACCOUNTNO','SELECTACCOUNT_FORSITRANSFER')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('CRMACCOUNTNO','SELECTACCOUNT_FORSITRANSFER')">Next</a></td>
		<td align="center"><input type="text" id="txtPageNumber" class="inputBorder1" readonly="true" size="2"/>of
		<input type="text" id="txtMaxPageNo" class="inputBorder1" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('CRMACCOUNTNO','SELECTACCOUNT_FORSITRANSFER')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('CRMACCOUNTNO','SELECTACCOUNT_FORSITRANSFER')">Last</a></td>
	</tr>
	</table>
	
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Account No:</strong><input type="text" id="txtActNo" name="txtActNo" class="inputBorder1" maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();" ></div>
					</td>
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Short Code:</strong><input type="text" id="txtshortcode" name="txtshortcode" class="inputBorder1"  maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
					</div></td>
				    <td>
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
					<!-- <div class="searchBg" onclick="DrawAccountType()"  style="margin-right:10px;" title="Search here"><a href="#">Search</a></div></td>-->
				</tr>
	</table>
		<fieldset class="border1">
			<legend> <b>Account List</b> </legend>
			<table width="100%"  border="1" id="tabAccount" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   	<tr class="grayBg">
				    <td width="3%" align="center">Select</td>
					<td width="17%" align="center">
					<a href="#" onclick="sortOrder('CRMACCOUNTNO','SELECTACCOUNT_FORSITRANSFER')">Account No</a>
				    </td>
					<td width="16%" align="center">
					<a href="#" onclick="sortOrder('M6SHORT_CODE','SELECTACCOUNT_FORSITRANSFER')">M6 Short Code</a>
					</td>
				</tr>
				
			</table>
		</fieldset>
</html:form>
</body>
<script type="text/javascript">
DrawTable('CRMACCOUNTNO','SELECTACCOUNT_FORSITRANSFER')
</script>
</html>


	

