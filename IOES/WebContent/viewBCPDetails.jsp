<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!-- [001]	Rohit Verma 6-Feb-14	Export to Excel for BCP -->
<html>
<head>
<title>BCP Details</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
var path='<%=request.getContextPath()%>';

function DrawBCPListTable()
{
	var lstBCPDetails;
	var sessionid ='<%=request.getSession().getId() %>';
	   try
	   {
		   var str;
		   var frm=document.getElementById('viewBCPFormId');		  
		   var mytable = document.getElementById('tabViewBcpDetailsId');	
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
					bcpID:Number(document.getElementById('txtBCPId').value),					
					sessionid:sessionid
				};
				
			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");						
			lstBCPDetails = jsonrpc.processes.getBCPDetailsWithSorting(jsData);							
			
			if(lstBCPDetails==null)
			{
				var callerWindowObj = dialogArguments;
				var myForm=callerWindowObj.document.getElementById('accountFormId');				
				myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=invalidsessoion";
				myForm.submit();
				alert("Session has been expired!!");		
				window.close();	
			}
		
			iTotalLength=lstBCPDetails.list.length;	
			if(iTotalLength !=0)
			{
				iNoOfPages = lstBCPDetails.list[0].maxPageNo;
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
				var tblRow=document.getElementById('tabViewBcpDetailsId').insertRow();
				tblRow.className=colors[colorValue];							
			
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstBCPDetails.list[i].bcpID;
				CellContents = str;
				tblcol.innerHTML = CellContents;
			
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstBCPDetails.list[i].bfirstname+" "+lstBCPDetails.list[i].blastname;
				CellContents = str;
				tblcol.innerHTML = CellContents;
			
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstBCPDetails.list[i].baddress1;
				CellContents = str;
				tblcol.innerHTML = CellContents;
			
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstBCPDetails.list[i].baddress2;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstBCPDetails.list[i].baddress3;
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstBCPDetails.list[i].baddress4;
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstBCPDetails.list[i].bcontactNo;
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstBCPDetails.list[i].bemailID;
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstBCPDetails.list[i].bpincode;
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstBCPDetails.list[i].bcity;
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstBCPDetails.list[i].bstate;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstBCPDetails.list[i].bcountry;
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
			var tblRow=document.getElementById('tabViewBcpDetailsId').insertRow();
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
		if(document.getElementById('txtBCPId').value=="0")
		{
			alert("Value 0 is not allowed");
			return false;
		}
	
		var searchFlag=1;
		if(document.getElementById('txtBCPId').value.length > 0)
		{
			if(checkdigits(document.getElementById('txtBCPId')))
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
			searchFromList('BCPID','VIEWBCPDETAILS');
		}							
}

function clearFields()
{
	document.getElementById('txtBCPId').value="";	
}
//[001] Start
function exportToExcel()
{	
	var myForm=document.getElementById('viewBCPFormId');
	var accountId=<%=request.getParameter("accountId")%>
	myForm.toExcel.value='true';
	myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=goToViewBCPDetailsPage&inExcel=true&accountId="+accountId;
	myForm.submit();
}
//[001] End
</script>
</head>
<body>
<div class="head"> <span>BCP Details</span> </div>
<html:form action="/NewOrderAction" styleId="viewBCPFormId" method="post">
<html:hidden property="toExcel"/>
<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('BCPID','VIEWBCPDETAILS')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('BCPID','VIEWBCPDETAILS')">Next</a></td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('BCPID','VIEWBCPDETAILS')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('BCPID','VIEWBCPDETAILS')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="6">
						<div style="float:left;"><strong>Search BCPID:</strong><input type="text" id="txtBCPId" name="BCPId" class="inputBorder1" maxlength="25" onkeydown="if (event.keyCode == 13)  return isBlankFields();" >
						<img onclick="isBlankFields()" src="${pageContext.request.contextPath}/npd/Images/search.gif" title="search"  height="15">
						<!-- [001] Start--> 
							<img title="Export BCP Details" src="./gifs/top/excel1.gif" onClick="javascript:exportToExcel();"></img> 
						<!-- [001] End-->
						<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" height="15" onclick="clearFields();"/></div></td>													
					
				</tr>
	</table>
	<table width="100%" border="1"  id="tabViewBcpDetailsId" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="10%" align="center">
					<a href="#" onclick="sortOrder('BCPID','VIEWBCPDETAILS')">BCP ID</a>
				</td>
				<td width="20%" align="center">
					BCP NAME
				</td>
				<td width="20%" align="center">
					<a href="#" onclick="sortOrder('ADDRESS1','VIEWBCPDETAILS')">ADDRESS1</a>
				</td>
				<td width="20%" align="center">
					<a href="#" onclick="sortOrder('ADDRESS2','VIEWBCPDETAILS')">ADDRESS2</a>
				</td>
				<td width="20%" align="center">
					<a href="#" onclick="sortOrder('ADDRESS3','VIEWBCPDETAILS')">ADDRESS3</a>
				</td>
				<td width="20%" align="center">
					<a href="#" onclick="sortOrder('ADDRESS4','VIEWBCPDETAILS')">ADDRESS4</a>
				</td>
				<td width="20%" align="center">
					CONTACT NO
				</td>
				<td width="20%" align="center">
					EMAIL
				</td>
				<td width="35%" align="center">
					<a href="#" onclick="sortOrder('PINCODE','VIEWBCPDETAILS')">PIN CODE</a>
				</td>
				<td width="20%" align="center">
					<a href="#" onclick="sortOrder('CITY','VIEWBCPDETAILS')">CITY</a>
				</td>
				<td width="20%" align="center">
					<a href="#" onclick="sortOrder('STATE','VIEWBCPDETAILS')">STATE</a>
				</td>
				<td width="20%" align="center">
					<a href="#" onclick="sortOrder('COUNTRY','VIEWBCPDETAILS')">COUNTRY</a>
				</td>				
			</tr>								
	</table>
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<td width="18%" align="center" vAlign="top" colspan="12">			
			<input type="button"	name="btnAddContact" value="Ok" width="5" onClick="window.close();">
			<!--<div class="searchBg1"><a href="#" onClick="window.close()">&nbsp;&nbsp;OK&nbsp;&nbsp;</a></div>-->
			</td>			
		</tr>
	</table>
	<html:hidden property="accountID" name="newOrderBean"
			styleId="hdnAccountID" />
</html:form>
</body>
<script type="text/javascript">
	//DrawAccountType()
	DrawTable('BCPID','VIEWBCPDETAILS')
	
	// <!-- end [003] -->
</script>
</html>