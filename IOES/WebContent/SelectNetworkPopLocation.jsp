<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ROHIT VERMA	4-Feb-11	00-05422		Sending ShortCode on New Order -->
<!--[002]	 ROHIT VERMA	18-Feb-11	00-05422		Fetching Region and Zone AGainst an Account -->
<!--[003]	 ANIL KUMAR 	23-msr-11	00-05422		Setting Paging ans sorting in select account popup window-->
<!--[004]	 LAWKUSH 		06-MAY-11	00-05422		Setting Paging ans sorting -->
<!--[005]	 SAURABH 		03-FEB-13	00-05422		Pop Location Desc Field added for searching Point No 106 HyperCare-->
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
<title>Select Pop Location Code</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
var flag=0;
function getInfoAndUpdate(popLocCode , popNetLocCode,popNetLocName,address1) 
{
     
	var callerWindowObj = dialogArguments;
	var Location=callerWindowObj.document.productCatelog.location.value;
	if(Location=='PRILOC')
	{
	callerWindowObj.document.productCatelog.txtPAddress.value = unescape(popNetLocCode) + ' - ' + unescape(address1);
	callerWindowObj.document.productCatelog.ddPNLocation.value = unescape(popLocCode);
	}
	else
	{
	  callerWindowObj.document.productCatelog.txtSAddress.value = unescape(popNetLocCode) + ' - ' + unescape(address1);
	  callerWindowObj.document.productCatelog.ddSNLocation.value = unescape(popLocCode);
	}
	flag=1;
	window.close();
}

function DrawPopLocType()
{
	   var str;
	   var frm=document.getElementById('popLocFormId');
	   
	   var sessionid ='<%=request.getSession().getId() %>';
	   var mytable = document.getElementById('tabPopLocType');	
	   var iCountRows = mytable.rows.length;
		
	   for (i = 2; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(2);
	   }   	
	    
	   document.getElementById('txtPageNumber').value= pageNumber;
	   sync();	   
	   var jsData =
			{	
			
			    searchAlphabet:var_alphabet,
				startIndex:startRecordId,
				endIndex:endRecordId,
				sortBycolumn:sortByColumn,
				sortByOrder:sortByOrder,
				popNetLocName:document.getElementById('txtPopLocName').value,
				popNetLocCode:document.getElementById('txtPopLocCode').value,
				popNetLocDesc:document.getElementById('txtPopLocDesc').value,
				sessionid:sessionid
			};
			
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var lstAccount = jsonrpc.processes.populateNPLocationAddress1(jsData);
	if(lstAccount==null)
	{
		var callerWindowObj = dialogArguments;
		var myForm=callerWindowObj.document.getElementById('productCatelog');						
		alert("Session has been expired!!");		
		window.close();
		callerWindowObj.window.close();
	}
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
		var tblRow=document.getElementById('tabPopLocType').insertRow();
		tblRow.className=colors[colorValue];
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.width="1%";
		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(lstAccount.list[i].popLocationCode)+"','"+ escape(lstAccount.list[i].popNetLocCode) +"','"+ escape(lstAccount.list[i].popNetLocName) +"','"+ escape(lstAccount.list[i].popNetLocDesc) +"') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		//By Saurabh --> Removed Code from Above to show blank names
		//'"+ escape("'"+ lstAccount.list[i].popNetLocName +"'") +"'
		//End By Saurabh
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].popNetLocCode;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
		CellContents = str;
		tblcol.innerHTML = CellContents;
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].popNetLocName;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		CellContents = str;
		tblcol.innerHTML = CellContents;
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstAccount.list[i].popNetLocDesc;
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
			
	if(iTotalLength==0)		
	{
		var tblRow=document.getElementById('tabPopLocType').insertRow();
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

var path='<%=request.getContextPath()%>';
function isBlankFields()
{
	var accountName=document.getElementById('txtPopLocName').value;
	var accountID=document.getElementById('txtPopLocCode').value;
	
		if(document.getElementById('txtPopLocCode').value=="0")
		{
		alert("Value O is not allowed");
		return false;
		}
	
		var searchFlag=1;
		/*if(document.getElementById('txtPopLocCode').value.length > 0)
		{
			if(checkdigits(document.getElementById('txtPopLocCode')))
			{
			searchFlag=1;
			}
			else
			{
			 searchFlag=0;
			}
		}*/
		if( trim(document.getElementById('txtPopLocCode').value).length>0)
		{
			if(ValidateTextField(document.getElementById('txtPopLocCode'),path,'Location Code')=='undefined')
			{
			searchFlag=0;
			return true;
			}
		}
		if( trim(document.getElementById('txtPopLocName').value).length>0)
		{
			if(ValidateTextField(document.getElementById('txtPopLocName'),path,'Location Name')=='undefined')
			{
			searchFlag=0;
			return true;
			}
		}
		//Start[005]
		if( trim(document.getElementById('txtPopLocDesc').value).length>0)
		{
			if(ValidateTextField(document.getElementById('txtPopLocDesc'),path,'Location Address')=='undefined')
			{
			searchFlag=0;
			return true;
			}
		}
		//End [005]
		if(searchFlag==1)
		{
	     searchFromList('POP_LOC_CODE','SELECTPOPLOCATION')
		}
}
function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     		isBlankFields();
    }      
}
function clearFields()
{
	document.getElementById('txtPopLocName').value="";
	document.getElementById('txtPopLocCode').value="";
	document.getElementById('txtPopLocDesc').value="";
}

</script>
</head>
<body >
<div class="head"> <span>Network pop location</span> </div>
<center>
<html:form action="/NewOrderAction" styleId="popLocFormId" method="post">
<bean:define id="formBean" name="newOrderBean"></bean:define>
<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
<!-- start [003] -->
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('POP_LOC_CODE','SELECTPOPLOCATION')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('POP_LOC_CODE','SELECTPOPLOCATION')">Next</a></td>
		<td align="center">
			<input type="text"  id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('POP_LOC_CODE','SELECTPOPLOCATION')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('POP_LOC_CODE','SELECTPOPLOCATION')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="1">
					<div style="float:left;"><strong>Pop Location Code:</strong><input type="text" id="txtPopLocCode" name="txtPopLocCode" class="inputBorder1"  maxlength="11" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
					</div></td>
					<td  align="center" colspan="1">
					<div style="float:left;"><strong>Pop Location Name:</strong><input type="text" id="txtPopLocName" name="txtPopLocName" class="inputBorder1"  maxlength="50" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
					</div></td>
					<td  align="center" colspan="1">
					<div style="float:left;"><strong>Pop Location Desc:</strong><input type="text" id="txtPopLocDesc" name="txtPopLocDesc" class="inputBorder1"  maxlength="50" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
					</div></td>
					<td colspan="1">
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
					<!-- <div class="searchBg" onclick="DrawAccountType()"  style="margin-right:10px;" title="Search here"><a href="#">Search</a></div></td>-->
				</tr>
	</table>
	<table width="100%" border="1"  id="tabPopLocType" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
					<tr valign="bottom" align="center"  >
						  <td colspan="1" align="right" valign="top" nowrap width="77"><div align="left">Alphabatical Search<font color="#990033">*</font></div></td>
						  <td align="left" colspan="3">
							<a href="#"  onclick="javascript:searchLocation('A');"><b><font color="blue">A</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('B');"><b><font color="blue">B</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('C');"><b><font color="blue">C</font></b></a>&nbsp;|&nbsp;
							<a href="#"  onclick="javascript:searchLocation('D');"><b><font color="blue">D</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('E');"><b><font color="blue">E</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('F');"><b><font color="blue">F</font></b></a>&nbsp;|&nbsp;
							<a href="#"  onclick="javascript:searchLocation('G');"><b><font color="blue">G</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('H');"><b><font color="blue">H</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('I');"><b><font color="blue">I</font></b></a>&nbsp;|&nbsp;
						    <a href="#"  onclick="javascript:searchLocation('J');"><b><font color="blue">J</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('K');"><b><font color="blue">K</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('L');"><b><font color="blue">L</font></b></a>&nbsp;|&nbsp;
							<a href="#"  onclick="javascript:searchLocation('M');"><b><font color="blue">M</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('N');"><b><font color="blue">N</font></b></a>&nbsp;|&nbsp;
							<a href="#"  onclick="javascript:searchLocation('O');"><b><font color="blue">O</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('P');"><b><font color="blue">P</font></b></a>&nbsp;|&nbsp;
							<a href="#"  onclick="javascript:searchLocation('Q');"><b><font color="blue">Q</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('R');"><b><font color="blue">R</font></b></a>&nbsp;|&nbsp;
						    <a href="#"  onclick="javascript:searchLocation('S');"><b><font color="blue">S</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('T');"><b><font color="blue">T</font></b></a>&nbsp;|&nbsp;
							<a href="#"  onclick="javascript:searchLocation('U');"><b><font color="blue">U</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('V');"><b><font color="blue">V</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('W');"><b><font color="blue">W</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('X');"><b><font color="blue">X</font></b></a>&nbsp;|&nbsp;
							<a href="#"  onclick="javascript:searchLocation('Y');"><b><font color="blue">Y</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('Z');"><b><font color="blue">Z</font></b></a>&nbsp;|&nbsp;<a href="#"  onclick="javascript:searchLocation('All');"><b><font color="blue">All</font></b></a>
						  
						  </td>
					</tr>
					
			<tr class="grayBg">
				<td align="center" width="5%">Select</td>
				<td  align="center" width="213">
					<a href="#" onclick="sortOrder('POP_LOC_CODE','SELECTPOPLOCATION')">Pop Location Code</a>
				</td>
				<td  align="center" width="289">
					<a href="#" onclick="sortOrder('LOCATION_NAME','SELECTPOPLOCATION')">Pop Location Name</a>
				</td>
				<td  align="center" width="392">
					<a href="#" onclick="sortOrder('NWKLOCATIONNAME','SELECTPOPLOCATION')">Pop Location Desc</a>
				</td>
				
			</tr>								
	</table>
		
</html:form>
</center>
</body>
<script type="text/javascript">
	//DrawAccountType()
	DrawTable('POP_LOC_CODE','SELECTPOPLOCATION')
	
	// <!-- end [003] -->
</script>
</html>