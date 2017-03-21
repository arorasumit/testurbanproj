<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 LAWKUSH		06-05-11	00-05422		Setting paging sorting -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Select Country</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script language="javascript" type="text/javascript">
function getInfoAndUpdate(cType,cTypeId) 
{
	var callerWindowObj = dialogArguments;
	var callerScreenName = '<%=request.getAttribute("hdnCountryListScreen")%>';
	if (callerScreenName == 'REPORT')
	{
		callerWindowObj.document.getElementsByName('contactTypeStr')[0].value = unescape(cType);
	}
	else
	{
	    var count=(<%=request.getParameter("counter")%>);
		callerWindowObj.document.getElementById('stateName'+count).value="";
		callerWindowObj.document.getElementById('stateCode'+count).value="";
		callerWindowObj.document.getElementById('cityName'+count).value="";
		callerWindowObj.document.getElementById('cityCode'+count).value="";
		callerWindowObj.document.getElementById('pinNo'+count).value="";
		callerWindowObj.document.getElementById('pinCode'+count).value="";	
		if(callerWindowObj.document.getElementById('countyName'+count)==undefined)
		{
			callerWindowObj.document.getElementById('countyName').value = unescape(cType);
			callerWindowObj.document.getElementById('countyCode').value = unescape(cTypeId);
			}
		else
		{
			// var count=(<%=request.getParameter("counter")%>);
			 callerWindowObj.document.getElementById('countyName'+count).value = unescape(cType);
			 callerWindowObj.document.getElementById('countyCode'+count).value = unescape(cTypeId);
			}
	}
	window.close();
	
}

function DrawCountryList()
{
	   var str;
	   var frm=document.getElementById('country');
	   var mytable = document.getElementById('tabCountry');	
	   var iCountRows = mytable.rows.length;
  
	   for (i = 1; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(1);
	   }  
	   document.getElementById('txtPageNumber').value= pageNumber;
	   sync(); 
	   
	   var jsData =
			{	startIndex:startRecordId,
				endIndex:endRecordId,
				sortBycolumn:sortByColumn,
				sortByOrder:sortByOrder,
				countyName:document.getElementById('txtCountry').value
			};
				  
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var lstService = jsonrpc.processes.searchCountryName(jsData);
	//var counter = <%=request.getAttribute("count")%>;
	iTotalLength=lstService.list.length;	
	if(iTotalLength !=0)
	{
		iNoOfPages = lstService.list[0].maxPageNo;
	}
	
	document.getElementById('txtMaxPageNo').value=iNoOfPages;
	var colors=new Array("normal","lightBg");
	
	 for (i = 0; i <  lstService.list.length; i++)
	 {
	 	
	    var colorValue=parseInt(i)%2;
		var tblRow=document.getElementById('tabCountry').insertRow();
		tblRow.className=colors[colorValue];
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(lstService.list[i].countyName) +"','"+ escape(lstService.list[i].countyCode) +"') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var callerWindowObj = dialogArguments;
		//callerWindowObj.document.getElementsByName('stateName')[(<%=request.getParameter("counter")%>)-1].value="";
		//callerWindowObj.document.getElementsByName('stateCode')[(<%=request.getParameter("counter")%>)-1].value="";
		//callerWindowObj.document.getElementsByName('cityName')[(<%=request.getParameter("counter")%>)-1].value="";
		//callerWindowObj.document.getElementsByName('cityCode')[(<%=request.getParameter("counter")%>)-1].value="";	
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=lstService.list[i].countyName;
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
		
	if(iTotalLength == 0)
	{
	var tblRow=document.getElementById('tabCountry').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 3;
		str='NO RECORD FOUND'
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
	     document.getElementById('first').disabled=true;
	     document.getElementById('prev').disabled=true;
	     document.getElementById('last').disabled=true;
	     document.getElementById('next').disabled=true;
		
	}
	
		return false;
}
function clearFields()
{
	document.getElementById('txtCountry').value="";
}
var path='<%=request.getContextPath()%>';
function isBlankFields()
{
	var country=document.getElementById('txtCountry').value;
	var searchFlag=1;
	
	if( trim(document.getElementById('txtCountry').value).length>0)
	{
		/*if(ValidateTextField(document.getElementById('txtCountry'),path,'Country')==false)
		{
		searchFlag=0;
		return false;
		}*/
	}

	if(searchFlag==1)
	{
		searchFromList('COUNTRY','SELECTCOUNTRY');
	}
}
function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     	isBlankFields();
    }      
}

</script>
</head>
<body>
<html:form action="/NewOrderAction" styleId="country" method="post">
		<fieldset class="border1">
			<legend> <b>Country List</b> </legend>
			<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('COUNTRY','SELECTCOUNTRY')">First</a>&nbsp;&nbsp;<a href="#" id= "next" onclick="NextPage('COUNTRY','SELECTCOUNTRY')">Next</a></td>
		<td align="center"><input type="text" id="txtPageNumber" class="inputBorder1" readonly="true" size="2"/>of
		<input type="text" id="txtMaxPageNo" class="inputBorder1" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('COUNTRY','SELECTCOUNTRY')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('COUNTRY','SELECTCOUNTRY')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Country Name:</strong><input type="text" id="txtCountry" onKeyPress="onPressEnterSearch(event)" name="country" class="inputBorder1">
					</div></td>
					<td>
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
				</tr>
	</table>
	<table width="100%" border="1"  id="tabCountry" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="2%" align="center">Select</td>
				<td width="8%" align="left">
					<a href="#" onclick="sortOrder('COUNTRY','SELECTCOUNTRY')">Country Name</a>
				</td>
				
			</tr>								
	</table>
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<td width="18%" >&nbsp;</td>
			<td width="20%" colspan="3">&nbsp;</td>
		</tr>
		</table>
		</fieldset>
		
</html:form>
</body>
<script type="text/javascript">
	//DrawCountryList()
	DrawTable('COUNTRY','SELECTCOUNTRY')
</script>
</html>
