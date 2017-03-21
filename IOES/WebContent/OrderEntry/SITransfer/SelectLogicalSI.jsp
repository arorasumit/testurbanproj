<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Anil Kumar		23-May-11	CSR00-05422     Session Expired code with AjaxJSON  -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.utilities.AppUtility"%>
<%@page import="com.ibm.ioes.beans.SITransferBean"%>
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<html>
<head>
<title>Select Logical SI Number</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
var currentDate = '<%=AppUtility.getTodayDate()%>';
var callerWindowObj = dialogArguments;
function getInfoAndUpdate(custSINo,serviceName,serviceID)
{
		callerWindowObj.document.getElementById('logicalSINo').value = unescape(custSINo);
		callerWindowObj.document.getElementById('serviceName').value= unescape(serviceName);
//		callerWindowObj.somemethodto draw Lsi table
		window.close();
	  
}
//for Selecting Multiple services
function SelectMultipleServices()
{
	
	var frm=document.getElementById('logicalTypes');
	var flag;
	var strlogicalSINo='';
	var strServiceId='';
	var logicalSINo=new Array();
	var serviceId=new Array();
	var serviceName=new Array();
	var accountNo = new Array();
	var crmAccountNo = new Array();
	var shortCode = new Array();
		
	if(frm.chk==undefined)
	{
		alert("There is no Records!!")
		return false;
	}
	if(frm.chk.length==undefined)
	{
	
		if(frm.chk.checked==true)
		{		
			
			serviceId[0]=frm.chk.value;
			strServiceId=frm.chk.value;
			logicalSINo[0]=document.getElementById('hdnLogicalSiNo_'+strServiceId).value;
			serviceName[0]=document.getElementById('hdnServiceName_'+strServiceId).value;
			accountNo[0]=document.getElementById('hdnAccountNo_'+strServiceId).value;
			crmAccountNo[0]=document.getElementById('hdnCRMAccountNo_'+strServiceId).value;
			shortCode[0]=document.getElementById('hdnShortCode_'+strServiceId).value;
			flag=true;
		}
	}
	else {
	for(i=0,j=0;i<frm.chk.length;i++)
	{
		
			if(frm.chk[i].checked==true)
			{	
						
				
					strServiceId=frm.chk[i].value;
					serviceId[j]=frm.chk[i].value;
					logicalSINo[j]=document.getElementById('hdnLogicalSiNo_'+strServiceId).value;
					serviceName[j]=document.getElementById('hdnServiceName_'+strServiceId).value;
					accountNo[j]=document.getElementById('hdnAccountNo_'+strServiceId).value;
					crmAccountNo[j]=document.getElementById('hdnCRMAccountNo_'+strServiceId).value;
					shortCode[j]=document.getElementById('hdnShortCode_'+strServiceId).value;	
					j++;
					flag=true;
								
				
				
			}	
	}
	}
	if(flag!=true)
		{
			alert("Please select Services");
			return false;
				
		}
	else
		{
			
			callerWindowObj.drawLSITable(logicalSINo,serviceName,serviceId,accountNo,crmAccountNo,shortCode);			
			window.close();	
			
		}
	
}
function DrawLogicalType()
{
	   var str;
	   var sessionid ='<%=request.getSession().getId() %>';
	   var frm=document.getElementById('logicalTypes');
        callerWindowObj = dialogArguments;
	   var hdnSourcePartyNo =callerWindowObj.document.getElementById('sourcePartyNo').value;	
	  //  var transferdate =callerWindowObj.document.getElementById('transferdate').value;	  
	
		var transferdate =currentDate;
	  var targetAccountNo = callerWindowObj.document.getElementById('accountno').value;	  
	  var listOfLSINo=callerWindowObj.document.getElementById('listOfLSINo').value;
	  
	   	var mytable = document.getElementById('tabLogicalType');	
	   	var iCountRows = mytable.rows.length;
	    	for (i = 2; i <  iCountRows; i++)
			   {
			       mytable.deleteRow(2);
			   }
	     document.getElementById('txtPageNumber').value= pageNumber;
	   sync();
	  
	   var jsData =
			{
				startIndex:startRecordId,
				endIndex:endRecordId,
				sortBycolumn:sortByColumn,
				sortByOrder:sortByOrder,
				logicalSINo:frm.logicalSINumber.value,
				sourcePartyNo:hdnSourcePartyNo,
				transferdate:transferdate,
				accountno:targetAccountNo,
				filterLSIList:listOfLSINo
				

			};
				  
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	var listLogicalSINumber = jsonrpc.processes.getLogicalSINumberswithSorting(jsData,sessionid);
	
	iTotalLength=listLogicalSINumber.list.length;	
		if(iTotalLength !=0)
		
		{
		iNoOfPages = listLogicalSINumber.list[0].maxPageNo;
		}


        else
		{     
	        iNoOfPages=1;
		}
	
	document.getElementById('txtMaxPageNo').value=iNoOfPages;	 
	
	if(listLogicalSINumber==null)
	{
	    // document.getElementById('tab2LogicalType').style.display="none";	
		//start [001]		
			var callerWindowObj = dialogArguments;
			var myForm=callerWindowObj.document.getElementById('searchForm');				
			myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=invalidsessoion";
			myForm.submit();
			alert("Session has been expired!!");		
			window.close();			
		//end [001]
	}
	  if(listLogicalSINumber.list.length == 0)
	  {
	    document.getElementById('tab2LogicalType').style.display="none";	
	  }
	 for (i = 0; i <  listLogicalSINumber.list.length; i++)
	 {
		var tblRow=document.getElementById('tabLogicalType').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		//alert('m6ShortCode : from list : ' + listLogicalSINumber.list[i].shortCode);
		//alert('m6ShortCode : from callerwindow : ' + callerWindowObj.document.searchForm.m6ShortCode.value);
		if(listLogicalSINumber.list[i].shortCode == callerWindowObj.document.searchForm.m6ShortCode.value)
		{
			str="<input name=chk id='chk' type='checkbox' value='"+listLogicalSINumber.list[i].serviceId+"'  /><input type='hidden' name='hdnLogicalSiNo' id='hdnLogicalSiNo_"+listLogicalSINumber.list[i].serviceId+"' value='"+listLogicalSINumber.list[i].custSINo+"'/>";
		
		}else
		{
			
			str="<input name=chk id='chk' type='checkbox' disabled='true' value='"+listLogicalSINumber.list[i].serviceId+"'  /><input type='hidden' name='hdnLogicalSiNo' id='hdnLogicalSiNo_"+listLogicalSINumber.list[i].serviceId+"' value='"+listLogicalSINumber.list[i].custSINo+"'/>";
		}
		
		str=str+"<input type='hidden' name='hdnAccountNo' id='hdnAccountNo_"+listLogicalSINumber.list[i].serviceId+"' value='"+listLogicalSINumber.list[i].accountIDString+"'/>";
		str=str+"<input type='hidden' name='hdnCRMAccountNo' id='hdnCRMAccountNo_"+listLogicalSINumber.list[i].serviceId+"' value='"+listLogicalSINumber.list[i].accountno+"'/>";
		str=str+"<input type='hidden' name='hdnShortCode' id='hdnShortCode_"+listLogicalSINumber.list[i].serviceId+"' value='"+listLogicalSINumber.list[i].shortCode+"'/>";
		str=str+"<input type='hidden' name='hdnServiceName' id='hdnServiceName_"+listLogicalSINumber.list[i].serviceId+"' value='"+listLogicalSINumber.list[i].serviceName+"'/>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=listLogicalSINumber.list[i].custSINo;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=listLogicalSINumber.list[i].serviceName;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=listLogicalSINumber.list[i].shortCode;
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
		
	if(listLogicalSINumber.list.length == 0)
	{
	var tblRow=document.getElementById('tabLogicalType').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 4;
		str='NO RECORD FOUND';
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}	
		return false;
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
	var logicalSI=document.getElementById('txtlogicalSINumber').value;
	
	
	
		if(document.getElementById('txtlogicalSINumber').value=="0")
		{
			alert("Value O is not allowed");
			return false;
		}
	
		var searchFlag=1;
		if(document.getElementById('txtlogicalSINumber').value.length > 0)
		{
			if(checkdigits(document.getElementById('txtlogicalSINumber')))
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
			searchFromList('LOGICAL_SI_NO','SELECTLOGICALSI_FORSITRANSFER');
		}
		
		
		
	
}
function clearFields()
{
	document.getElementById('txtlogicalSINumber').value="";
	
}


</script>
</head>
<body>
<html:form action="/siTransfer" styleId="logicalTypes" method="post">
<bean:define id="formBean" name="siTransferBean"></bean:define>
<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
<!-- start [003] -->
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('LOGICAL_SI_NO','SELECTLOGICALSI_FORSITRANSFER')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('LOGICAL_SI_NO','SELECTLOGICALSI_FORSITRANSFER')">Next</a></td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo"  class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('LOGICAL_SI_NO','SELECTLOGICALSI_FORSITRANSFER')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('LOGICAL_SI_NO','SELECTLOGICALSI_FORSITRANSFER')">Last</a></td>
	</tr>
	</table>
	<div class="head"> <span>Select Logical SI Number</span> </DIV>
		<fieldset class="border1">
			<legend> <b>Logical SI Number List</b> </legend>
			<table width="100%"  border="1" id="tabLogicalType" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="grayBg">
			        
					<td   colspan="4">
					<input type="text" name="logicalSINumber" id="txtlogicalSINumber" style="margin-right:50px;float:left" class="inputBorder1" onkeydown="if (event.keyCode == 13) return DrawLogicalType();">
					<div class="searchBg" onclick="DrawLogicalType()" ><a href="#">Search</a></div>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>
					<div width="100"  ></div>
					</td>
				</tr>
				<tr class="grayBg">
				    <td width="" align="center"> </td>
					<td width="" align="center"><a href="#" onclick="sortOrder('LOGICAL_SI_NO','SELECTLOGICALSI_FORSITRANSFER')">Logical SI No</a></td>
					<td width="" align="center"><a href="#" onclick="sortOrder('SERVICESTAGE','SELECTLOGICALSI_FORSITRANSFER')">Service Name</a></td>
					<td width="" align="center"><a href="#" onclick="sortOrder('M6SHORT_CODE','SELECTLOGICALSI_FORSITRANSFER')">Account Short Code</a></td>
				</tr>
			</table>
			<table width="100%"  id="tab2LogicalType"  align="center"  cellpadding="0" cellspacing="0"  >
				<tr class="grayBg">
				<td width="40%"></td>
				<td  width="40%" align="center" colspan="3">
					<div class="searchBg" onclick="SelectMultipleServices()"  align="center"><a href="#">OK</a></div>
					</td>
				<td width="40%"></td>
				</tr>
			</table>
		</fieldset>
</html:form>
</body>
<script type="text/javascript">
DrawTable('LOGICAL_SI_NO','SELECTLOGICALSI_FORSITRANSFER')
</script>
</html>


	
