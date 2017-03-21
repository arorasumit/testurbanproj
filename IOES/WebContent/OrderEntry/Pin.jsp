<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Select Pin</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
function getInfoAndUpdate(cType,cTypeId) 
{
	
	var callerWindowObj = dialogArguments;
	var callerScreenName = '<%=request.getAttribute("hdnPincodeListScreen")%>';
	
	if (callerScreenName == 'REPORT')
	{
		callerWindowObj.document.getElementsByName('contactTypeStr')[0].value = unescape(cType);
	}
	else
	{
		 var count=(<%=request.getParameter("counter")%>);
		if(callerWindowObj.document.getElementById('pinNo'+count)==undefined)
		{
			callerWindowObj.document.getElementById('pinNo'+count).value = unescape(cType);
			callerWindowObj.document.getElementById('pinCode'+count).value = unescape(cTypeId);
			}
		else
		{
			callerWindowObj.document.getElementById('pinNo'+count).value = unescape(cType);
			callerWindowObj.document.getElementById('pinCode'+count).value = unescape(cTypeId);
			}
	}
	window.close();
	
}

function DrawPincodeList()
{
	var count=(<%=request.getParameter("counter")%>);
	   var str;
	   var frm=document.getElementById('pin');
	   
	   var mytable = document.getElementById('tabPinCode');	
	   var iCountRows = mytable.rows.length;
  
	   for (i = 2; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(2);
	   } 
	   var callerWindowObj = dialogArguments;  	
	    
	  if(callerWindowObj.document.getElementById('cityCode'+count).value=="")
	    {
	    alert("Please Select City First!!");
	    window.close();
	    return false;
	     
	   }
	   if(callerWindowObj.document.getElementById('stateCode'+count).value=="")
	    {
	    alert("Please Select State!!");
	    window.close();
	    return false;
	     
	   }	   
	   var jsData =
			{
				stateCode:callerWindowObj.document.getElementById('stateCode'+count).value,
				cityCode:callerWindowObj.document.getElementById('cityCode'+count).value
				//pinCode:callerWindowObj.document.getElementById('pinCode'+count).value			
				//pinCode:frm.pinNo.value								
			};
				  
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var lstService = jsonrpc.processes.searchPincode(jsData);
	//var counter = <%=request.getAttribute("count")%>;
	 for (i = 0; i <  lstService.list.length; i++)
	 {
	    var pincode=lstService.list[i].pincode;
		var tblRow=document.getElementById('tabPincode').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(lstService.list[i].pinCode) +"','"+ escape(lstService.list[i].pinCode) +"') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=lstService.list[i].pinCode;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
	}	
	if(lstService.list.length == 0)
	{
	var tblRow=document.getElementById('tabPinCode').insertRow();
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
function isBlankSearch()
{
		var searchValue=0;
		if(document.getElementById('pinCode').value=="")
	   	{
			alert("Please enter search value!!");		    	
	    	SearchPincodeList(searchValue);
	    	document.getElementById('pinCode').focus();  
	    	return false;
		}
		else
		{
				    searchValue=document.getElementById('pinCode').value;
					SearchPincodeList(searchValue);	  
		}			   
}
function SearchPincodeList(searchPinCodeValue)
{	
	   var count=(<%=request.getParameter("counter")%>);
	   var str;
	   var frm=document.getElementById('pin');
	   
	   var mytable = document.getElementById('tabPinCode');	
	   var iCountRows = mytable.rows.length;
  
	   for (i = 2; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(2);
	   } 
	   var callerWindowObj = dialogArguments;  	
	   
	  if(callerWindowObj.document.getElementById('cityCode'+count).value=="")
	    {
	    alert("Please Select City First!!");
	    window.close();
	    return false;
	     
	   }
	   if(callerWindowObj.document.getElementById('stateCode'+count).value=="")
	    {
	    alert("Please Select State!!");
	    window.close();
	    return false;
	     
	   }
	   
	   var jsData =
			{
				
				stateCode:callerWindowObj.document.getElementById('stateCode'+count).value,
				cityCode:callerWindowObj.document.getElementById('cityCode'+count).value,
				pinNo:searchPinCodeValue
			};
				  
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var lstService = jsonrpc.processes.searchPincode(jsData);
	//var counter = <%=request.getAttribute("count")%>;
	 for (i = 0; i <  lstService.list.length; i++)
	 {
	    var pincode=lstService.list[i].pincode;
		var tblRow=document.getElementById('tabPincode').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<input name=chk id=chk type=radio onclick=getInfoAndUpdate('"+ escape(lstService.list[i].pinCode) +"','"+ escape(lstService.list[i].pinCode) +"') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=lstService.list[i].pinCode;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
	}	
	if(lstService.list.length == 0)
	{
	var tblRow=document.getElementById('tabPinCode').insertRow();
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
<html:form action="/NewOrderAction" styleId="pincode" method="post">
		<fieldset class="border1">
			<legend> <b>PinCode List</b> </legend>
			<table width="100%"  border="1" id="tabPincode" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="grayBg">
					<td width="10%" align="center" colspan="2">
						<div style="float: left;"><strong>Input Pincode</strong><input type="text" name="pinCode" id="pinCode" class="inputBorder1" maxlength="10" onkeydown="if (event.keyCode == 13) return isBlankSearch();"></div>
						<div class="searchBg" onclick="isBlankSearch()"  style="margin-right:10px;"><a href="#">Go</a></div>
					</td>
				</tr>
				<tr class="grayBg">
					<td width="3%" align="center">Select</td>
					<td width="16%" align="left">Pincode</td>
				</tr>
			</table>
		</fieldset>
</html:form>
</body>
<script type="text/javascript">
	DrawPincodeList()
</script>
</html>
