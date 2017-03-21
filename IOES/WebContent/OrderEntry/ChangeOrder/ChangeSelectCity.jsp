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
<title>Select City</title>
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
	var callerScreenName = '<%=request.getAttribute("hdnCityListScreen")%>';
	if (callerScreenName == 'REPORT')
	{
		callerWindowObj.document.getElementsByName('contactTypeStr')[0].value = unescape(cType);
	}
	else
	{
	   var count=(<%=request.getParameter("counter")%>);
		callerWindowObj.document.getElementById('pinNo'+count).value="";
		callerWindowObj.document.getElementById('pinCode'+count).value="";	
		
		if(callerWindowObj.document.getElementById('cityName'+count)==undefined)
		{
			callerWindowObj.document.getElementById('cityName').value = unescape(cType);
			callerWindowObj.document.getElementById('cityCode').value = unescape(cTypeId);
			}
		else
		{
			
			
			callerWindowObj.document.getElementById('cityName'+count).value = unescape(cType);
			callerWindowObj.document.getElementById('cityCode'+count).value = unescape(cTypeId);
			}
	}
	window.close();
	
}

function DrawCityList()
{
	var count=(<%=request.getParameter("counter")%>);
	   var str;
	   var frm=document.getElementById('city');
	   var mytable = document.getElementById('tabCity');	
	   var iCountRows = mytable.rows.length;
  
	   for (i = 1; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(1);
	   } 
	   document.getElementById('txtPageNumber').value= pageNumber;
	   sync(); 
	   var callerWindowObj = dialogArguments;  
	   if(callerWindowObj.document.getElementById('stateCode'+count).value=="")
	   {
	    alert("Please Select State First!!");
	    window.close();
	    //return false;
	     
	   }
	
	   var jsData =
			{
				startIndex:startRecordId,
				endIndex:endRecordId,
				sortBycolumn:sortByColumn,
				sortByOrder:sortByOrder,
				stateCode:callerWindowObj.document.getElementById('stateCode'+count).value,
				cityName:document.getElementById('txtCity').value
			};
			
			
				  
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var lstService = jsonrpc.processes.searchCityName(jsData);
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
		var tblRow=document.getElementById('tabCity').insertRow();
		tblRow.className=colors[colorValue];
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(lstService.list[i].cityName) +"','"+ escape(lstService.list[i].cityCode) +"') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstService.list[i].cityName;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
	}	
	if(lstService.list.length == 0)
	{
	var tblRow=document.getElementById('tabCity').insertRow();
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
	document.getElementById('txtCity').value="";
}

function isBlankFields()
{
	var city=document.getElementById('txtCity').value;
	
	if(city =="")
	{
		alert('Please enter  search creteria');		
	}
	else
	{
		searchFromList('CITY','SELECTCITY');
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
<html:form action="/NewOrderAction" styleId="city" method="post">
		<fieldset class="border1">
			<legend> <b>City List</b> </legend>
			<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" onclick="FirstPage('CITY','SELECTCITY')">First</a>&nbsp;&nbsp;<a href="#" onclick="NextPage('CITY','SELECTCITY')">Next</a></td>
		<td align="center"><input type="text" id="txtPageNumber" class="inputBorder1" readonly="true" size="2"/>of
		<input type="text" id="txtMaxPageNo" class="inputBorder1" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" onclick="PrevPage('CITY','SELECTCITY')">Prev</a>&nbsp;&nbsp;<a href="#" onclick="LastPage('CITY','SELECTCITY')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>City Name:</strong><input type="text" id="txtCity" onKeyPress="onPressEnterSearch(event)" name="city" class="inputBorder1">
					</div></td>
					<td>
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
				</tr>
	</table>
	<table width="100%" border="1"  id="tabCity" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="2%" align="center">Select</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('CITY','SELECTCITY')">City Name</a>
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
	//DrawCityList()
	DrawTable('CITY','SELECTCITY')
</script>
</html>
