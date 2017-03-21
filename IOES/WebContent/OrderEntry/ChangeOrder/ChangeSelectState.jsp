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
<title>Select State</title>
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
	var callerScreenName = '<%=request.getAttribute("hdnStateListScreen")%>';
	if (callerScreenName == 'REPORT')
	{
		callerWindowObj.document.getElementsByName('contactTypeStr')[0].value = unescape(cType);
	}
	else
	{
	   var count=(<%=request.getParameter("counter")%>);
		callerWindowObj.document.getElementById('pinNo'+count).value="";
		callerWindowObj.document.getElementById('pinCode'+count).value="";	
		jsonrpc1 = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		
		if(callerWindowObj.document.getElementById('stateName'+count)==undefined)
		{
		
		
			callerWindowObj.document.getElementById('stateName').value = unescape(cType);
			callerWindowObj.document.getElementById('stateCode').value = unescape(cTypeId);
			if(callerWindowObj.document.getElementById('stateCode'+count).value != "")
 			{ 		
 				var jsData1 =
					{				
						stateCode:callerWindowObj.document.getElementById('stateCode'+count).value				
					};			
				var service3 = jsonrpc1.processes.searchCountryName1(jsData1);
				if(service3.list.length == 1)
 				{
 					callerWindowObj.document.getElementById('countyName'+count).value=service3.list[0].countyName;
 					callerWindowObj.document.getElementById('countyCode'+count).value=service3.list[0].countyCode;
 		
 				}
			}
			
			
			}
		else
		{	
			callerWindowObj.document.getElementById('cityName'+count).value="";
			callerWindowObj.document.getElementById('cityCode'+count).value="";
			callerWindowObj.document.getElementById('stateName'+count).value = unescape(cType);
			callerWindowObj.document.getElementById('stateCode'+count).value = unescape(cTypeId);
			if(callerWindowObj.document.getElementById('stateCode'+count).value != "")
 			{ 		
 				var jsData1 =
					{				
						stateCode:callerWindowObj.document.getElementById('stateCode'+count).value				
					};			
				var service3 = jsonrpc1.processes.searchCountryName1(jsData1);
			if(service3.list.length == 1)
 				{
 					callerWindowObj.document.getElementById('countyName'+count).value=service3.list[0].countyName;
 					callerWindowObj.document.getElementById('countyCode'+count).value=service3.list[0].countyCode;
 		
 				}
			}
			
			
		}
	}
	window.close();
	
}

function DrawStateList()
{
      var count=(<%=request.getParameter("counter")%>);
	   var str;
	   var frm=document.getElementById('state');
	   var mytable = document.getElementById('tabState');	
	   var iCountRows = mytable.rows.length;  	
	   for (i =1; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(1);
	   } 
	   var callerWindowObj = dialogArguments;
	  
	   
		if(callerWindowObj.document.getElementById('countyCode'+count).value=="")
	   {
	    alert("Please Select Country First!!");
	    window.close();
	    //return false;
	     
	   }	
	   document.getElementById('txtPageNumber').value= pageNumber;
	   sync();	  
	   var jsData =
			{	
				startIndex:startRecordId,
				endIndex:endRecordId,
				sortBycolumn:sortByColumn,
				sortByOrder:sortByOrder,
				countyCode:callerWindowObj.document.getElementById('countyCode'+count).value,
				stateName:document.getElementById('txtState').value
			};		  
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var lstService = jsonrpc.processes.searchStateName(jsData);
	iTotalLength=lstService.list.length;	
	if(iTotalLength !=0)
	{
		iNoOfPages = lstService.list[0].maxPageNo;
	}
	
	document.getElementById('txtMaxPageNo').value=iNoOfPages;
	var colors=new Array("normal","lightBg");
	
	//var counter = <%=request.getAttribute("count")%>;
	 for (i = 0; i <  lstService.list.length; i++)
	 {
	    var colorValue=parseInt(i)%2;
		var tblRow=document.getElementById('tabState').insertRow();
		tblRow.className=colors[colorValue];
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(lstService.list[i].stateName) +"','"+ escape(lstService.list[i].stateCode) +"') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=lstService.list[i].stateName;
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
	
	if(lstService.list.length == 0)
	{
	var tblRow=document.getElementById('tabState').insertRow();
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
	document.getElementById('txtState').value="";
}
var path='<%=request.getContextPath()%>';
function isBlankFields()
{
	var accountName=document.getElementById('txtState').value;
	
	var searchFlag=1;
	if( trim(document.getElementById('txtState').value).length>0)
		{
			/*if(ValidateTextField(document.getElementById('txtState'),path,'State')==false)
			{
			searchFlag=0;
			return false;
			}*/	
		}
	
	if(searchFlag==1)
		{
		searchFromList('STATE','SELECTSTATE');
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
<html:form action="/NewOrderAction" styleId="state" method="post">
		<fieldset class="border1">
			<legend> <b>State List</b> </legend>
			<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id="first" onclick="FirstPage('STATE','SELECTSTATE')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('STATE','SELECTSTATE')">Next</a></td>
		<td align="center"><input type="text" id="txtPageNumber" class="inputBorder1" readonly="true" size="2"/>of
		<input type="text" id="txtMaxPageNo" class="inputBorder1" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('STATE','SELECTSTATE')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('STATE','SELECTSTATE')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>State Name:</strong><input type="text" id="txtState" name="state" onKeyPress="onPressEnterSearch(event)" class="inputBorder1">
					</div></td>
					<td>
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
				</tr>
	</table>
	<table width="100%" border="1"  id="tabState" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="2%" align="center">Select</td>
				<td width="8%" align="left">
					<a href="#" onclick="sortOrder('STATE','SELECTSTATE')">State Name</a>
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
	//DrawStateList()
	DrawTable('STATE','SELECTSTATE')
</script>
</html>
