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
<title>Select LSI</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
var serviceDetailId=<%=request.getParameter("serviceDetailID")%>;
var currentLSI=<%=request.getParameter("logicalSIno")%>;
var orderNo=<%=request.getParameter("orderNo")%>;
var configId=<%=request.getParameter("configId")%>;
var fxChargeRedirectionType="<%=request.getParameter("fxChargeRedirectionType")%>";
var fxChargeRedirectionTypeCumulative="<%=request.getParameter("fxChargeRedirectionTypeCumulative")%>";
var callerWindowObj = dialogArguments;
function setServiceSummaryAttribute(logicalSINumber,spid)
{
	callerWindowObj.document.getElementById("txtFxRedirectionLSI").value = unescape(logicalSINumber);
	callerWindowObj.document.getElementById("txtBillingScenario").value =document.getElementById("txtBillScenario").value;
	callerWindowObj.document.getElementById("hdnFxRedirectionSPID").value =unescape(spid);
	window.close();
}

function DrawArborLSIListTable()
{


	var str;
	var frm=document.getElementById('LSIFormId');
	//lawkush Start
	var lsisRedirected='';  
	//lawkush End
	var mytable = document.getElementById('tabLSIType');	
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
		logicalSINumber:Number(document.getElementById('txtLSINo').value),
		fxInternalId:Number(document.getElementById('txtFxInternalId').value),
		isDummy:Number(document.getElementById("txtsearchDD").value),
		configValue:configId,
		serviceDetailID:serviceDetailId,
		orderNumber:orderNo,
		logicalSINo:currentLSI,
		noofuses:document.getElementById("txtBillScenario").value
	};
			
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	var lstArborLSI = jsonrpc.processes.getArborLSIListWithSorting(jsData);		
	
	//lawkush Start stateName arborLSIList
	if(lstArborLSI.stateName=="CAN_NOT_SELECT")
	{
	
							iTotalLengthRedirected=lstArborLSI.arborLSIList.list.length;	
					for (i = 0 ; i <iTotalLengthRedirected; i++) 
					{
						
						
								if(lsisRedirected=='')
								{	
									lsisRedirected=lstArborLSI.arborLSIList.list[i].logicalSINo;							
								}else
								{	
									lsisRedirected=lsisRedirected+","+lstArborLSI.arborLSIList.list[i].logicalSINo;									
								}				
								
								
					}
				if(lsisRedirected!='')
				{
					alert("This/These (Order/s-LSI/s-Line Item Id/s)  "+lsisRedirected+ " is/are already mapped to the current LSI \n Please unmap the current LSI from this/these LSI/s , If you want to redirect this LSI to other.");
					window.close();
					return false;
				}
	
	}
	else
	{

	
	//lawkush End
	
	iTotalLength=lstArborLSI.arborLSIList.list.length;	
	if(iTotalLength !=0)
	{
		iNoOfPages = lstArborLSI.arborLSIList.list[0].maxPageNo;
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
		var tblRow=document.getElementById('tabLSIType').insertRow();
		tblRow.className=colors[colorValue];
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<input name=chk id=chk type=radio onclick=setServiceSummaryAttribute('"+escape(lstArborLSI.arborLSIList.list[i].logicalSINo) + "','"+ escape(lstArborLSI.arborLSIList.list[i].serviceProductID) + "') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstArborLSI.arborLSIList.list[i].logicalSINo;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstArborLSI.arborLSIList.list[i].fxInternalId;
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
		var tblRow=document.getElementById('tabLSIType').insertRow();
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
}

function clearFields()
{
	document.getElementById('txtLSINo').value="";
	document.getElementById('txtFxInternalId').value="";
	
}
var path='<%=request.getContextPath()%>';
function isBlankFields()
{
	var logicalSI=document.getElementById('txtLSINo').value;
	var internalId=document.getElementById('txtFxInternalId').value;
	
	var searchFlag=1;
	if(trim(document.getElementById('txtLSINo').value).length > 0)
	{
		if(checknumber(document.getElementById('txtLSINo'))==false)
		{
   			return false;
		}
	}

	if(trim(document.getElementById('txtFxInternalId').value).length>0)
	{
		if(checknumber(document.getElementById('txtFxInternalId'))==false)
		{
			return false;
		}
	}
		
	if(searchFlag==1)
	{
		searchFromList('LOGICAL_SI_NO','SELECTARBORLSI');
	}
}
function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     		isBlankFields();
    }      
}
function setLSIBlank()
{
	callerWindowObj.document.getElementById("txtFxRedirectionLSI").value = "";
	callerWindowObj.document.getElementById("txtBillingScenario").value =0;
	callerWindowObj.document.getElementById("hdnFxRedirectionSPID").value =0;
	window.close();
}

function enableDisableDropDown()
{
	if(fxChargeRedirectionType=='DUMMY')
	{
		document.getElementById("txtsearchDD").value=1;
		document.getElementById("txtsearchDD").disabled=true;
	}
	if(fxChargeRedirectionTypeCumulative!='OFF')
	{
		document.getElementById("txtBillScenario").value=1;
		document.getElementById("txtBillScenario").disabled=true;
	}
}			

</script>
</head>
<body onload="enableDisableDropDown()">
<div class="head"> <span>LSI List </span> </div>
<center>
<html:form action="/NewOrderAction" styleId="LSIFormId" method="post">
<bean:define id="formBean" name="newOrderBean"></bean:define>
<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('LOGICAL_SI_NO','SELECTARBORLSI')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('LOGICAL_SI_NO','SELECTARBORLSI')">Next</a></td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo"  class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('LOGICAL_SI_NO','SELECTARBORLSI')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('LOGICAL_SI_NO','SELECTARBORLSI')">Last</a></td>
	</tr>
	</table>	   		
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="normal">
			<td  align="center" colspan="3">
			<div class="searchBg" style="margin-right:10px;float:left;"><a href="#" onclick="setLSIBlank()"><strong>Set LSI Blank</strong></a></div>
			</td>
			<td  align="center" colspan="3">
			<div style="float:left;"><strong>LSI NO:</strong><input type="text" id="txtLSINo" name="LSINo" class="inputBorder2" maxlength="8" onkeydown="if (event.keyCode == 13)  return isBlankFields();" ></div>
			</td>
			<td  align="center" colspan="3">
			<div style="float:left;"><strong>Fx Child Account</strong><input type="text" id="txtFxInternalId" name="fxInternalId" class="inputBorder2" maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
			</div></td>
			<td  align="center" colspan="3">
				<div style="float:left;"><strong>Dummy NonDummy</strong>
					<select  name="searchDD" id="txtsearchDD" class="dropdownMargin" isRequired="0" style="width:150px" onChange="isBlankFields()" >
						<option value="1" title="Dummmy LSI">Dummmy LSI</option>
						<option value="2" title="Non Dummmy LSI">Non Dummy LSI</option>
					</select>
				</div></td>
			<td  align="center" colspan="3">
				<div style="float:left;"><strong>Charge Redirection Type</strong>
					<select  name="billScenarioDD" id="txtBillScenario" class="dropdownMargin" isRequired="0" style="width:150px" onChange="isBlankFields()" >
						<option value="1" title="Consolidate">Consolidate</option>
						<option value="2" title="Cumulative">Cumulative</option>
					</select>
				</div></td>
			<td>
			<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
			<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Fields" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
		</tr>
	</table>
	<table width="100%" border="1"  id="tabLSIType" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="2%" align="center">Select</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('LOGICAL_SI_NO','SELECTARBORLSI')">Logical Si No.</a>
				</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('INTERNAL_ID','SELECTARBORLSI')">Fx Child Account No.(Internal)</a>
				</td>
			</tr>								
	</table>
</html:form>
</center>
</body>
<script type="text/javascript">
	DrawTable('LOGICAL_SI_NO','SELECTARBORLSI');
</script>
</html>