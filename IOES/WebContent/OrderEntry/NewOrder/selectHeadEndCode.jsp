<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Select HeadEndLSI</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jsonrpc.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/utility.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/PagingSorting.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/formValidations.js"></script>
</head>

<script language="javascript" type="text/javascript">

 var ordNo="<%=request.getParameter("orderNo")%>";
 
 function setHeadEndCode(val)
 {
	 //alert('in setHeadEndCode');	 
	 var element=document.getElementById('txtHeadEndCode'); 
	 /* alert(element.value); */
	 //return false;
	 if(trim(element.value)==null || trim(element.value)=='')
	 {
		 /* alert('here in if '); */
		 element.value=trim(val);
 	 }
	 else
		 {
		 /* alert('now here '); */
		 element.value='';
		 element.value=trim(val);
		 }
	 $('#HeadEndCodeDialog').dialog('close');
	 //$('#HeadEndCodeDialog').empty();
 }
 
 function DrawHeadEndLsiListTable()
 {
	 	var str;
		var frm=document.getElementById('LSIFormId');
	  
		var mytable = document.getElementById('tabLSIType');	
		var iCountRows = mytable.rows.length; 
		
		for (var i = 1; i <  iCountRows; i++)
		{
	    	mytable.deleteRow(1);
		}
		document.getElementById('txtPageNumber').value= pageNumber;
		sync();	   
		var lsiFromSearchField=Number(document.getElementById('txtLSINo').value);
		if(lsiFromSearchField=='')
			{
			lsiFromSearchField=0;
			}
		
		var jsData =
		{
		startIndex:startRecordId,
		endIndex:endRecordId,		
		sortByOrder:sortByOrder,
		logicalSINo:lsiFromSearchField,  //Number(document.getElementById('txtLSINo').value)	
		orderNumber:ordNo // orderNo:ordNo
		};
		
		jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		var data=jsonrpc.processes.getHeadEndLsiForDropAndCarryWithSorting(jsData);
		iTotalLength=data.list.length;
		
		if(iTotalLength!=0)
		{
			iNoOfPages = data.list[0].maxPageNo;
		}
		else
		{	     
    	iNoOfPages=1;
		}
		
		document.getElementById('txtMaxPageNo').value=iNoOfPages;				
		var colors=new Array("normal","lightBg");
	
		
		for (var i = 0 ; i <iTotalLength; i++) 
		{		 		
	 		var colorValue=parseInt(i)%2;	 		  
			var tblRow=document.getElementById('tabLSIType').insertRow();
			tblRow.className=colors[colorValue];
			
			var tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str="<input name=chk id=chk type=radio onclick=setHeadEndCode('"+data.list[i].headEndCode+"')>" ; // setHeadEndCode(escape(data.list[i].HeadEndCode))
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=data.list[i].logical_si_no;
			CellContents = str;
			tblcol.innerHTML = CellContents;
	
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=data.list[i].headEndCode;
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
				str='NO RECORD FOUND';
				CellContents = str;
				tblcol.innerHTML = CellContents;
			} 
			return 0;
 }
 
	/*
			var tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str="<input name=chk id=chk type=radio onclick="escape(alert('hello'))"/>"; // setHeadEndCode(escape(data.list[i].HeadEndCode))
			CellContents = str;
			tblcol.innerHTML = CellContents;
	
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=data.list[i].logical_si_no;
			CellContents = str;
			tblcol.innerHTML = CellContents;
	
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=data.list[i].headEndCode;
			CellContents = str;
			tblcol.innerHTML = CellContents;
	} */
		
 	function clearFields()
	{
		document.getElementById('txtLSINo').value="";	
		
	}
	var path='<%=request.getContextPath()%>';
	function isBlankFields()
	{
		var logicalSI=document.getElementById('txtLSINo').value;	
		
		var searchFlag=1;
		if(trim(document.getElementById('txtLSINo').value).length > 0)
		{
			if(checknumber(document.getElementById('txtLSINo'))==false)
			{
	   			return false;
			};
		}		
		if(searchFlag==1)
		{
			searchFromList('LOGICAL_SI_NO','SELECTHEADENDLSI');
		};
	}
	function onPressEnterSearch(key_event)
	{
	    if (key_event.keyCode==13) 
	    {
	     		isBlankFields();
	    }      
	} 

<%-- function DrawHeadEndLsiListTable()
	{
	 	alert('hrer in DrawHeadEndLsiListTable');
	 	return false;
		var str;
		var frm=document.getElementById('LSIFormId');
	  
		var mytable = document.getElementById('tabLSIType');	
		var iCountRows = mytable.rows.length; alert(iCountRows); 
		
		for (var i = 1; i <  iCountRows; i++)
		{
	    	mytable.deleteRow(1);
		}
		document.getElementById('txtPageNumber').value= pageNumber;
		sync();	   	   	   	  
		
		var lsiFromSearchField=Number(document.getElementById('txtLSINo').value);
		if(lsiFromSearchField=='')
			{
			lsiFromSearchField=0;
			}
		var jsData =
			{
			startIndex:startRecordId,
			endIndex:endRecordId,		
			sortByOrder:sortByOrder,
			logicalSINo:lsiFromSearchField,  //Number(document.getElementById('txtLSINo').value)	
			orderNumber:3033016 // orderNo:ordNo
			};
			
			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	/* var lstArborLSI = jsonrpc.processes.getVCS_BridgeLSIListWithSorting(jsData);		 */	
	
			var data=jsonrpc.processes.getHeadEndLsiForDropAndCarryWithSorting(jsData);
			iTotalLength=data.list.length;	alert(iTotalLength); 
			
		if(iTotalLength !=0)
			{
				iNoOfPages = data.list[0].maxPageNo;
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
			str="<input name=chk id=chk type=radio onclick="alert('hello')"/>"; // setHeadEndCode(escape(data.list[i].HeadEndCode))
			CellContents = str;
			tblcol.innerHTML = CellContents;
		
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=data.list[i].logical_si_no;
			CellContents = str;
			tblcol.innerHTML = CellContents;
		
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=data.list[i].headEndCode;
			CellContents = str;
			tblcol.innerHTML = CellContents;
		
		/* tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=lstArborLSI.list[i].orderNumber;
		CellContents = str;
		tblcol.innerHTML = CellContents; */	
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
			str='NO RECORD FOUND';
			CellContents = str;
			tblcol.innerHTML = CellContents;
		} 
	return 0 ;	
}
 --%>
<%-- 	function clearFields()
	{
		document.getElementById('txtLSINo').value="";	
		
	}
	var path='<%=request.getContextPath()%>';
	function isBlankFields()
	{
		var logicalSI=document.getElementById('txtLSINo').value;	
		
		var searchFlag=1;
		if(trim(document.getElementById('txtLSINo').value).length > 0)
		{
			if(checknumber(document.getElementById('txtLSINo'))==false)
			{
	   			return false;
			};
		}		
		if(searchFlag==1)
		{
			searchFromList('LOGICAL_SI_NO','SELECTHEADENDLSI');
		};
	}
	function onPressEnterSearch(key_event)
	{
	    if (key_event.keyCode==13) 
	    {
	     		isBlankFields();
	    }      
	} 
 --%>	
</script>
<body>
<div class="head"> <span>HeadEndLSI List </span> </div>
<center>
<html:form action="/NewOrderAction" styleId="LSIFormId" method="post">
<!-- <bean:define id="formBean" name="newOrderBean"></bean:define>
<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/> -->
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('LOGICAL_SI_NO','SELECTHEADENDLSI')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('LOGICAL_SI_NO','SELECTHEADENDLSI')">Next</a></td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo"  class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('LOGICAL_SI_NO','SELECTHEADENDLSI')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('LOGICAL_SI_NO','SELECTHEADENDLSI')">Last</a></td>
	</tr>
	</table>	   		
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="normal">
			<!-- <td  align="center" colspan="3">
			<div class="searchBg" style="margin-right:5px;float:left;"><a href="#" onclick="setLSIBlank()"><strong>Set LSI Blank</strong></a></div>
			</td> -->
			<td  align="center" colspan="3">
			<div style="float:left;"><strong>LSI NO:</strong><input type="text" id="txtLSINo" name="LSINo" class="inputBorder1" maxlength="8" onkeydown="if (event.keyCode == 13)  return isBlankFields();" ></div>
			</td>					
			<td>
			<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
			<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Fields" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
		</tr>
	</table>
	<table width="100%" border="1"  id="tabLSIType" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="2%" align="center">Select</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('LOGICAL_SI_NO','SELECTHEADENDLSI')">Logical Si No.</a>
				</td>
				<td width="8%" align="center" >HeadEndCode</td>				
			</tr>								
	</table>
</html:form>
</center>
</body>
<script type="text/javascript">
	//DrawHeadEndLsiListTable();
	//DrawTable('LOGICAL_SI_NO','SELECTHEADENDLSI'); 
</script>
</html>