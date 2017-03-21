<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>SelectAccount</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" type="text/javascript">
function getInfoAndUpdate(accID,accName) 
{
	var callerWindowObj = dialogArguments;
	<logic:present name="ADD_CUSTLOC" scope="request">
		callerWindowObj.document.searchForm.accountIdStr1.value = accID;
		callerWindowObj.document.searchForm.accountNameStr1.value = unescape(accName);
		window.close();
		return;
	</logic:present>
	<logic:present name="ADD_DALOC" scope="request">
		callerWindowObj.document.searchForm.accountIdStr1.value = accID;
		callerWindowObj.document.searchForm.accountNameStr1.value = unescape(accName);
		window.close();
		return;
	</logic:present>
	callerWindowObj.document.searchForm.accountIdStr.value = accID;
	callerWindowObj.document.searchForm.accountNameStr.value = unescape(accName);
	

	window.close();
}

function DrawAccountType()
{
	   var str;
	   var frm=document.getElementById('searchAccount');
	  
	   var mytable = document.getElementById('tabaccountType');	
	   var iCountRows = mytable.rows.length;
  
	   for (i = 2; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(2);
	   }   
	   var jsData =
			{
				accountName:frm.accountName.value
			};
	
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		  
	var lstAccount = jsonrpc.processes.getAccountDetails(jsData);
	
	 for (i = 0; i <  lstAccount.list.length; i++)
	 {
		var tblRow=document.getElementById('tabaccountType').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";

		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+escape(lstAccount.list[i].accountID) + "','"+ escape(lstAccount.list[i].accountName) + "','"+ escape(lstAccount.list[i].accphoneNo) + "','" + escape(lstAccount.list[i].lob) + "','"+ escape(lstAccount.list[i].accountManager) + "','"+ escape(lstAccount.list[i].projectManager) + "','"+ escape(lstAccount.list[i].spFirstname) + "','"+ escape(lstAccount.list[i].spLastName) +	"','"+ escape(lstAccount.list[i].spLPhno) + "','"+ escape(lstAccount.list[i].spLEmail) + "','"+ escape(lstAccount.list[i].region) + "','"+ escape(lstAccount.list[i].zone) + "') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].accountID;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].accountName;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
	}	
	if(lstAccount.list.length == 0)
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
</script>
</head>
<body>
	<html:form action="/customerLocation" method="post" styleId="searchAccount">
		<div class="head"> <span>Select Account</span> </DIV>
		<bean:define id="formBean" name="locationDetailBean"></bean:define>
		<fieldset class="border1">
			<legend> <b>Account List</b> </legend>
			<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" id="tabaccountType"  >
				<tr class="grayBg" >
					<td align="center" colspan="3"> 
						Search An Account
						<input type="text" name="accountName" class="inputBorder1" maxlength="150" onkeypress="if(event.keyCode=='13') return DrawAccountType()";>
						<div class="searchBg" style="margin-right:10px;"><a href="#" onClick="DrawAccountType()">Search</a></div>
						
					</td>
				</tr>
				<tr class="grayBg">
					<td width="3%" align="center">Select</td>
					<td width="23%">Account ID</td>
					<td width="71%">Account Name</td>
				</tr>
				
			</table>
		</fieldset>
	</html:form>
</body>
<script type="text/javascript">
	DrawAccountType()
</script>
</html>
