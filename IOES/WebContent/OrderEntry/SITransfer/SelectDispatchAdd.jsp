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
<title>Select Dispatch Details</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">

function getInfoAndUpdate(dispatchId,dispatchName) 
{
	
    
	
	  	var lno=(<%=request.getParameter("logicalSiNumber")%>);
      
	   var callerWindowObj = dialogArguments;
	  var spid=callerWindowObj.document.searchForm.spid.value;
      callerWindowObj.document.getElementsByName('dispatchAdd')[(<%=request.getParameter("count")%>)].value = unescape(dispatchId+'-'+dispatchName);
      
  
		    
		var abc=callerWindowObj.document.getElementsByName('billingAdd')[(<%=request.getParameter("count")%>)].value; 
		var add="";
		
		if(abc.length>0)
		{
		    
		   for(var i=0;i<abc.length;i++)
		     {
		                
		                 
		       		var str= abc.substring(i,i+1);
		            
		      
		              if(str=='-')
		              {
		              
		              break;
		              }
					  
					else
					{     
					         add=add+str;
					        
					   
		     }     
		 }
		 
	}
		 

           var jsData =
			{
			   sino:Number(callerWindowObj.document.getElementsByName('sino')[(<%=request.getParameter("count")%>)].value),
			   dispatchId:Number( dispatchId),
			   spid:Number(callerWindowObj.document.getElementsByName('spid')[(<%=request.getParameter("count")%>)].value),
			   logicalSINumber:Number(lno),
			   bcpId:Number(add)
			   
			 
			 };

	  jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	   jsonrpc.processes.InsertUpdateSIDetails(jsData);
   

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
	var partyNo=document.getElementById('txtDispatchNo').value;
	var partyName=document.getElementById('txtDispatchName').value;
	
	
		if(document.getElementById('txtDispatchNo').value=="0")
		{
			alert("Value O is not allowed");
			return false;
		}
	
		var searchFlag=1;
		if(document.getElementById('txtDispatchNo').value.length > 0)
		{
			if(checkdigits(document.getElementById('txtDispatchNo')))
			{
			searchFlag=1;
			}
			else
			{
			 searchFlag=0;
			}
		}
		if( trim(document.getElementById('txtDispatchName').value).length>0)
		{
			if(ValidateTextField(document.getElementById('txtDispatchName'),path,'Dispatch Name')==false)
			{
			searchFlag=0;
			return false;
			}
		}
		
		
		
		if(searchFlag==1)
		{
			searchFromList('DISPATCH_ADDRESS_CODE','SELECTDISPATCH_FORSITRANSFER');
		}
		
		
		
	
}
function clearFields()
{
	document.getElementById('txtDispatchNo').value="";
	document.getElementById('txtDispatchName').value="";
	
}

function DrawDispatchTable()
{

 var str;
 var callerWindowObj = dialogArguments;
	   var frm=document.getElementById('dispatchids');
	  
	   var mytable = document.getElementById('tabDispatch');	
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
					accountno:callerWindowObj.document.searchForm.accountno.value,
					selectedDispatchID:Number(document.getElementById('txtDispatchNo').value),
					dispatch_name:document.getElementById('txtDispatchName').value
			  };

	

	
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	var lstDispatch = jsonrpc.processes.fetchDispatchInfowithsorting(jsData);
	var counter = <%=request.getAttribute("count")%>;
	iTotalLength=lstDispatch.list.length;	
		if(iTotalLength !=0)
		{
		iNoOfPages = lstDispatch.list[0].maxPageNo;
		}

		else
		{     
	        iNoOfPages=1;
		}
	
	document.getElementById('txtMaxPageNo').value=iNoOfPages;	 
	
	if(lstDispatch.list.length==1)
	{
	
	callerWindowObj.document.getElementsByName('dispatchAdd')[(<%=request.getParameter("count")%>)].value=lstDispatch.list[0].dispatchId + '- ' + lstDispatch.list[0].dispatch_details;
	var abc=callerWindowObj.document.getElementsByName('billingAdd')[(<%=request.getParameter("count")%>)].value;
	 var lno=(<%=request.getParameter("logicalSiNumber")%>);   
	 var add="";
	if(abc.length>0)
		{
		    
		   for(var i=0;i<abc.length;i++)
		     {
		                
		                 
		       		var str= abc.substring(i,i+1);
		            
		      
		              if(str=='-')
		              {
		              
		              break;
		              }
					  
					else
					{     
					         add=add+str;
					        
					   
		     }     
		 }
		 
	}
		 
	 
	 var jsData =
			{
			   
			   dispatchId:Number(lstDispatch.list[0].dispatchId), 
			   spid:Number(callerWindowObj.document.getElementsByName('spid')[(<%=request.getParameter("count")%>)].value),
			   logicalSINumber:Number(lno),
			      bcpId:Number(add)
			 
			 };

	  jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	   jsonrpc.processes.InsertUpdateSIDetails(jsData);
   
	window.close();
	
	}
	else
	{
	
	  
	
	
	  
	 for (i = 0; i <  lstDispatch.list.length; i++)
	 {
	    //var partyNo=lstParty.list[i].sourcePartyNo;
	    //var partyName=lstParty.list[i].sourcePartyName;
		var tblRow=document.getElementById('tabDispatch').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";

		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(lstDispatch.list[i].dispatchId)+"','"+ escape(lstDispatch.list[i].dispatch_details) +"') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstDispatch.list[i].dispatchId;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstDispatch.list[i].dispatch_details;
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
	         

	if(lstDispatch.list.length == 0)
	{
	var tblRow=document.getElementById('tabDispatch').insertRow();
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
<html:form action="/siTransfer" styleId="dispatchids" method="post">
<bean:define id="formBean" name="siTransferBean"></bean:define>
<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
<!-- start [003] -->
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('DISPATCH_ADDRESS_CODE','SELECTDISPATCH_FORSITRANSFER')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('DISPATCH_ADDRESS_CODE','SELECTDISPATCH_FORSITRANSFER')">Next</a></td>
		<td align="center"><input type="text" id="txtPageNumber" class="inputBorder1" readonly="true" size="2"/>of
		<input type="text" id="txtMaxPageNo" class="inputBorder1" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('DISPATCH_ADDRESS_CODE','SELECTDISPATCH_FORSITRANSFER')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('DISPATCH_ADDRESS_CODE','SELECTDISPATCH_FORSITRANSFER')">Last</a></td>
	</tr>
	</table>
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Dispatch Id:</strong><input type="text" id="txtDispatchNo" name="txtDispatchNo" class="inputBorder1" maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();" ></div>
					</td>
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Dispatch Name:</strong><input type="text" id="txtDispatchName" name="txtDispatchName" class="inputBorder1"  maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
					</div></td>
				    <td>
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
					<!-- <div class="searchBg" onclick="DrawAccountType()"  style="margin-right:10px;" title="Search here"><a href="#">Search</a></div></td>-->
				</tr>
	</table>
		<fieldset class="border1">
			<legend> <b>Dispatch List</b> </legend>
			<table width="100%"  border="1" id="tabDispatch" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   	<tr class="grayBg">
				    <td width="3%" align="center">Select</td>
					<td width="17%" align="center">
					<a href="#" onclick="sortOrder('DISPATCH_ADDRESS_CODE','SELECTDISPATCH_FORSITRANSFER')">Dispatch ID</a>
				    </td>
					<td width="16%" align="center">
					<a href="#" onclick="sortOrder('DISPATCH_DETAILS','SELECTDISPATCH_FORSITRANSFER')">Dispatch Name</a>
					</td>
				
				</tr>
				
			</table>
		</fieldset>
</html:form>
</body>
<script type="text/javascript">
DrawTable('DISPATCH_ADDRESS_CODE','SELECTDISPATCH_FORSITRANSFER')
</script>
</html>


	

