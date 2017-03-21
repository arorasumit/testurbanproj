<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Manisa garg	13-Mar-13	00-05422		Select Account to reassign PM defect no 15032013010 -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.forms.FieldAttibuteDTO"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
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

<%
			HashMap var_userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
			HttpSession var_sessionObj = (HttpSession) var_userHashMap.get(session.getId());
			UserInfoDto var_objUserDto  = (UserInfoDto)var_sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>



<script language="javascript" type="text/javascript">

//Rakshika : Code Start
var flag=0;
//Rakshika : Code ends

//[001] Start
function getInfoAndUpdate(crmAccountId) 
//[001] End
{
	var callerWindowObj = dialogArguments;
	callerWindowObj.document.searchForm.crmAccountNo.value = unescape(crmAccountId);
	window.close();
}



function DrawAccountListTable()
{
	
		var employeeId = "<%=var_objUserDto.getEmployeeId() %>";
	   var str;
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
				shortCode:document.getElementById('txtShortCode').value,
				employeeid:employeeId
			};
			
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	var lstAccount = jsonrpc.processes.getAccountDetailsWithSortingforReassigmPm(jsData);				
	
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
		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+escape(lstAccount.list[i].crmAccountId)+"') />";
		//[001] End
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].crmAccountId;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].m6ShortCode;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].accountName;
		CellContents = str;
		tblcol.innerHTML = CellContents;	
		
		var accountid=lstAccount.list[i].accountID;
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<a href='#' onclick='viewBCPDetailsPopup("+accountid+")'>View BCP Details</a>";
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
	document.getElementById('txtShortCode').value="";
	document.getElementById('txtAccountID').value="";
}
var path='<%=request.getContextPath()%>';
function isBlankFields()
{
	var accountName=document.getElementById('txtAccountName').value;
	var shortCode=document.getElementById('txtShortCode').value;
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
		
		 if( trim(document.getElementById('txtShortCode').value).length>0)
		{
			 if(ValidateTextField(document.getElementById('txtShortCode'),path,'Short Code')==false)
			 {
			 searchFlag=0;
			 return false;
			 }
		}
		
		if(searchFlag==1)
		{
			searchFromList('CRMACCOUNTNO','SELECTACCOUNT');
		}
		
		
		
	
}
function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     		isBlankFields();
    }      
}



function viewBCPDetailsPopup(accountid)
{
	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=goToViewBCPDetailsPage&accountId="+accountid;		
	window.showModalDialog(path,window,"status:false;dialogWidth:1020px;dialogHeight:530px");
}





</script>
</head>
<body>
<div class="head"> <span>Account List </span> </div>
<center>
<html:form action="/NewOrderAction" styleId="accountFormId" method="post">
<bean:define id="formBean" name="newOrderBean"></bean:define>
<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
<!-- start [003] -->
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('CRMACCOUNTNO','SELECTACCOUNT')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('CRMACCOUNTNO','SELECTACCOUNT')">Next</a></td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo"  class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('CRMACCOUNTNO','SELECTACCOUNT')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('CRMACCOUNTNO','SELECTACCOUNT')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Account NO:</strong><input type="text" id="txtAccountID" name="accountID" class="inputBorder1" maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();" ></div>
					</td>
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Short Code:</strong><input type="text" id="txtShortCode" name="accountName" class="inputBorder1"  maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
					</div></td>
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
				<td width="2%" align="center">Select</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('CRMACCOUNTNO','SELECTACCOUNT')">Account No</a>
				</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('M6shortCode','SELECTACCOUNT')">Short Code</a>
				</td>
				<td width="20%" align="center">
					<a href="#" onclick="sortOrder('ACCOUNTNAME','SELECTACCOUNT')">Account Name</a>
				</td>
				<td width="20%" align="center">View BCP Details</td>
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
	//DrawAccountListTable();
	DrawTable('CRMACCOUNTNO','SELECTACCOUNT');
	
	
</script>
</html>