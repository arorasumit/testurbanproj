<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.beans.CurrencyChangeBean"%>
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean;"%>
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

function DrawAccountBULKListTable()
{
	   if(document.getElementById('chkAllLSI')!=null)
	   {
	   		document.getElementById('chkAllLSI').checked=false;
	   }
	   var str,partyID;	  
	   partyID=document.getElementById('hdnPartyID').value;	   
	   var frm=document.getElementById('accountFormId');
	  
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
				accountName:document.getElementById('txtAccountName').value,
				accountIDString:frm.accountID.value,
				sourcePartyNo:partyID			
			};
			
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	var listAccount = jsonrpc.processes.populateAccount(jsData);						
	
		iTotalLength=listAccount.list.length;	
		if(iTotalLength !=0)
		{
			iNoOfPages = listAccount.list[0].maxPageNo;			
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
			var tblRow=document.getElementById('tabaccountType').insertRow();
			tblRow.className=colors[colorValue];		
			
			var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str="<input name='chka' id='chka"+j+"' type='checkbox'  value='"+listAccount.list[j].accountID+"'><input name='hdnchka' id='hdnchka"+j+"' type='hidden'  value='"+listAccount.list[j].crmAccountNo+"'>";
			CellContents = str;
			tblcol.innerHTML = CellContents;
									
			var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str= listAccount.list[j].crmAccountNo;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str= listAccount.list[j].accountName;
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
	document.getElementById('txtAccountName').value="";	
	document.getElementById('txtAccountID').value="";
}
var path='<%=request.getContextPath()%>';

function isBlankFields()
{
	var accountName=document.getElementById('txtAccountName').value;	
	var accountID=document.getElementById('txtAccountID').value;
	
		if(document.getElementById('txtAccountID').value=="0")
		{
			alert("Value O is not allowed");
			return false;
		}
	
		var searchFlag=1;
		if(document.getElementById('txtAccountID').value.length > 0)
		{
			if(checkdigits(document.getElementById('txtAccountID')))
			{
			searchFlag=1;
			}
			else
			{
			 searchFlag=0;
			}
		}
		if( trim(document.getElementById('txtAccountName').value).length>0)
		{
			if(ValidateTextField(document.getElementById('txtAccountName'),path,'Account Name')==false)
			{
			searchFlag=0;
			return false;
			}
		}						
		if(searchFlag==1)
		{
			searchFromList('CRMACCOUNTNO','SELECTACCOUNTFORBULKUPLOAD');
		}
			
}
function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     		isBlankFields();
    }      
}
function addAccountToTextArea()
{
	var i=0;
	var selectedAccountIDs="",selectedAccountNo="";
	var callerWindowObj = dialogArguments;
	
	while(eval(document.getElementById("chka"+i)))
	{
		if(eval(document.getElementById("chka"+i)))
		{
		
			if(eval(document.getElementById("chka"+i)).checked)
			{
				if(selectedAccountIDs=="")
				{
					selectedAccountIDs = eval(document.getElementById("chka"+i)).value;
					selectedAccountNo = eval(document.getElementById("hdnchka"+i)).value;
				}
				else
				{
					selectedAccountIDs = selectedAccountIDs+','+eval(document.getElementById("chka"+i)).value;
					selectedAccountNo = selectedAccountNo+','+eval(document.getElementById("hdnchka"+i)).value;
				}
			}
		}
		i=i+1;
	}	
	callerWindowObj.selectedAccounts=selectedAccountIDs;
	callerWindowObj.document.searchForm.txtAreaAccountNo.value=selectedAccountNo;	
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
<div class="head"> <span>Account List </span> </div>
<center>
<html:form action="/currencyChange" styleId="accountFormId" method="post">
<bean:define id="formBean" name="currencyChangeBean"></bean:define>
<bean:define id="formBeanPaging" name="currencyChangeBean"></bean:define>
<bean:define id="pagingSorting" name="formBeanPaging" property="pagingSorting"/>
<!-- start [003] -->
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('CRMACCOUNTNO','SELECTACCOUNTFORBULKUPLOAD')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('CRMACCOUNTNO','SELECTACCOUNTFORBULKUPLOAD')">Next</a></td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('CRMACCOUNTNO','SELECTACCOUNTFORBULKUPLOAD')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('CRMACCOUNTNO','SELECTACCOUNTFORBULKUPLOAD')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Account NO:</strong><input type="text" id="txtAccountID" name="accountID" class="inputBorder1" maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();" ></div>
					</td>					
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Account Name:</strong><input type="text" id="txtAccountName" name="accountName" class="inputBorder1"  maxlength="50" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
					</div></td>
					<td>
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
					<!-- <div class="searchBg" onclick="DrawAccountType()"  style="margin-right:10px;" title="Search here"><a href="#">Search</a></div></td>-->
				</tr>
	</table>
	<table width="100%" border="1"  id="tabaccountType" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="4%" align="center">Select<input type="checkbox" id="chkAllLSI" align="top" onclick="CheckAll()"/></td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('CRMACCOUNTNO','SELECTACCOUNTFORBULKUPLOAD')">Account No</a>
				</td>				
				<td width="20%" align="center">
					<a href="#" onclick="sortOrder('ACCOUNTNAME','SELECTACCOUNTFORBULKUPLOAD')">Account Name</a>
				</td>				
			</tr>								
	</table>
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<td width="18%" colspan="4"><input type="button" name="btnOK" value="OK"
			style="width:100px" onclick="addAccountToTextArea()"></td>			
		</tr>
	</table>	
	<html:hidden property="partyId" name="formBean"
			styleId="hdnPartyID" />
</html:form>
</center>
</body>
<script type="text/javascript">
	//DrawAccountType()
	DrawTable('CRMACCOUNTNO','SELECTACCOUNTFORBULKUPLOAD')
	
	// <!-- end [003] -->
</script>
</html>