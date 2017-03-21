 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--Tag Name Resource Name  	Date		CSR No			Description -->
<!--		 Rohit Verma		28-FEB-13  00-08440		New Interface to Request Hardware Line Item for Deletion in M6  -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Delete Hardware Line Item</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
function clearFields()
{
	document.getElementById('accountID').value="";
	document.getElementById('accountName').value="";
	document.getElementById('searchCRMOrder').value="";
	document.getElementById('serviceID').value="";
	document.getElementById('parentServiceProductId').value="";
}

function isBlankFields()
{
	var LSINumber=document.getElementById('serviceID');
	var LineItemNo=document.getElementById('parentServiceProductId');	
	var OrderNo=document.getElementById('searchCRMOrder');	
	var AccountNo=document.getElementById('accountID');
	var AccountName=document.getElementById('accountName');
	var searchFlag=1;
	if( trim(LSINumber.value).length>0)
	{ 		
		if(checkdigits(LSINumber))
		{
			searchFlag=1;
		}
		else
		{
			searchFlag=0;
		}	  		  		  	  		  	 	 
	}
	if( trim(LineItemNo.value).length>0)
	{ 		
		if(checkdigits(LineItemNo))
		{
			searchFlag=1;
		}
		else
		{
			searchFlag=0;
		}	  		  		  	  		  	 	 
	}
	if( trim(OrderNo.value).length>0)
	{ 		
		if(checkdigits(OrderNo))
		{
			searchFlag=1;
		}
		else
		{
			searchFlag=0;
		}	  		  		  	  		  	 	 
	}
	if( trim(AccountNo.value).length>0)
	{ 		
		if(checkdigits(AccountNo))
		{
			searchFlag=1;
		}
		else
		{
			searchFlag=0;
		}	  		  		  	  		  	 	 
	}	
	if( trim(AccountName.value).length>0)	
	{				
		if(ValidateTextField(AccountName,path,'Account Name')=='undefined')
		{
			 searchFlag=0;
			 return true;
		}
	}
	if(searchFlag==1)
	{
		searchFromList('ORDERNO','CANCEL_HARDWARE_LINEITEM');
	}		
}
	
function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     	isBlankFields();
    }      
}
function goToHome()
{
	var myForm=document.getElementById('hardwarelineItem');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}
function DrawHardwareDetailsTable()
{
	var str;
	var frm=document.getElementById('hardwarelineItem');
	var mytable = document.getElementById('tabHardwareLines');	
	var iCountRows = mytable.rows.length;

	for (i = 2; i <  iCountRows; i++)
	{
	    mytable.deleteRow(2);
	} 

	document.getElementById('chkAllOrder').checked=false;
	document.getElementById('txtPageNumber').value= pageNumber;   	
	document.getElementById('id_paging_goToPage').value= pageNumber;   	
	sync();			
	var index_var=0;
   	index_var=startRecordId;
	try
	{
		var pagingDto=
		{
			startIndex:startRecordId,
			endIndex:endRecordId,
			sortBycolumn:sortByColumn,
			sortByOrder:sortByOrder
		};
	   var jsData =
		{	
			
			lsiNO:document.getElementById('serviceID').value,
			orderNo:document.getElementById('searchCRMOrder').value,
			lineNo:document.getElementById('parentServiceProductId').value	,
			accountNo:document.getElementById('accountID').value	,
			accountName:document.getElementById('accountName').value,
			pagingDto:pagingDto,
			pagingRequired:1
		};	
 
		jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		var listLogicalSINumber = jsonrpc.processes.getHardwareLineDetails(jsData);
	}
	catch(e)
	{
		alert("Exception :"+e);
	}
	var iTotalLength=listLogicalSINumber.list.length;
	if(iTotalLength !=0)
	{
		iNoOfPages = listLogicalSINumber.list[0].maxPageNo;
	}
	else
	{     
	        iNoOfPages=1;
	}
	var counter = <%=request.getAttribute("count")%>;
	
	document.getElementById('txtMaxPageNo').value=iNoOfPages;				
	var colors=new Array("normal","lightBg");
	 
	 for (i = 0; i <  iTotalLength; i++)
	 {	
	 	var colorValue=parseInt(i)%2;
		var tblRow=document.getElementById('tabHardwareLines').insertRow();
		tblRow.className=colors[colorValue];
		
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<label>" +index_var+ "</label>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].accountNo);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].accountName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].orderNo);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].orderStage);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].orderType);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].serviceNo);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].serviceName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].lsiNO);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].m6OrderNo);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].lineNo);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].lineName);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=setBlankIfNull(listLogicalSINumber.list[i].circuitID);
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		
		str="<input tabIndex='-1' name='chkSelectLineItem' id='chkSelectLineItem"+index_var+"' value='"+ listLogicalSINumber.list[i].lineNo +"'  type='checkbox'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
	
		index_var++;
	}	
	if(listLogicalSINumber.list.length == 0)
	{
	var tblRow=document.getElementById('tabHardwareLines').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 15;
		str='No Records Found';
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}	
	return false;
}
function fnProcessRequest()
{
	var myForm=document.getElementById('hardwarelineItem');
	var str="";
	var checkedCounter1=0;
	if(document.forms[0].chkSelectLineItem)
	{
   		var chkLength=document.forms[0].chkSelectLineItem.length;
   		if(chkLength==undefined)
		{
			if(document.forms[0].chkSelectLineItem.checked==true)
   			{
   				checkedCounter1++;
	   			if(str=="")
		     	{
		     		str=str=document.forms[0].chkSelectLineItem.value;
		     	}
	     	    else
		     	{
		     		str=str + "," +document.forms[0].chkSelectLineItem.value;
		     	}
       			
       		}
     	}
		else
		{
			for(i=0;i<document.forms[0].chkSelectLineItem.length;i++)
			{
				if(myForm.chkSelectLineItem[i].checked==true)
		     	{
		     		checkedCounter1++
		     	    if(str=="")
		     	   	{
						str=myForm.chkSelectLineItem[i].value;
		    		}
		     	    else
		     	    {
		     	    	str=str + "," +myForm.chkSelectLineItem[i].value;
		     	    }
		     	}
		     }	
		}
		if(str!="")
		{
			var answer = confirm("Are you sure you want to Delete these Hardware Line item(s)")
			if(answer)
			{
				var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");	
				var sessionid ='<%=request.getSession().getId() %>';		
				var status = jsonrpc.processes.fnProcessLineItems(str,sessionid);	
				var successcount=0;
				var falcount=0;
				var msg="";
				var str1="";
				var str3="";
				if(status.lineItemStatusList!=null && status.lineItemStatusList.list.length>0)
				{
					for(i=0;i<status.lineItemStatusList.list.length;i++)
					{
						if(status.lineItemStatusList.list[i].requestStatus=='Success')
						{
							successcount=successcount+1;
						}
						else
						{
							if(str1=="")
							{
								str1=(status.lineItemStatusList.list[i].lineNo);
							}
							else
							{
								str3=(status.lineItemStatusList.list[i].lineNo);
					     		str1=str1 + ',' + str3;
								falcount=falcount+1;
							}
						}
							
					}
				} 
			 	if(successcount>0)
	 	   		{
	 	   			msg="Request Generated for " + "\n" +successcount+"- Line Items";
		   		} 
	        
		   		if(falcount>0)
				{
			   		msg=msg + "\n"+ falcount+"- Request Failed:" +"\n" +"Following Line Item Request Creation have Failed" +str1;
		   		}
			 	alert(msg);
			 	DrawTable('ORDERNO','CANCEL_HARDWARE_LINEITEM')
			 }	
		}
		if(checkedCounter1==0)
		{
			alert("Please Select Line Item!!");
			return false;
		}
	}	
 	else
 	{
 		if (document.forms[0].chkSelectLineItem == null)
 		{
 			alert("No line item present!");
			return false;
 		}
		//alert("Line Item Can't be Processed!!");
		//return false;
	}	
}
function fnCheckOrderAll()
{
 	var myForm=document.getElementById('hardwarelineItem');
	var str="";
	var checkedCounter1=0;
   	if(document.forms[0].chkSelectLineItem)
    {
  		var chkLength=document.forms[0].chkSelectLineItem.length;
  		if(document.getElementById('chkAllOrder').checked==true)
		{
 			if(chkLength==undefined)
          	{
       			if(document.forms[0].chkSelectLineItem.disabled==false)
        		{
                	document.forms[0].chkSelectLineItem.checked=true;
              	}
            }
   			else
   			{    
       			for (i =0; i<chkLength; i++)
  		 		{ 
    				if(myForm.chkSelectLineItem[i].disabled==false)
		 			{
		        		myForm.chkSelectLineItem[i].checked=true ;
					}      
     			}
			}
		}	
		else
	   	{
	   		if(chkLength==undefined)
        	{
	           if(document.forms[0].chkSelectLineItem.disabled==false)
		       {
					document.forms[0].chkSelectLineItem.checked=false;
	           }
          	}          
      		else
      		{    
         		for (i =0; i<chkLength; i++)
		   		{ 
		     		if(myForm.chkSelectLineItem[i].disabled==false)
				 	{
				    	myForm.chkSelectLineItem[i].checked=false ;
				 	}       
		   		}
			}
     	}
	}
   	else
	{
		alert('No Line Items To Select');
  	 	document.getElementById('chkAllOrder').checked=false;
  	 	return false;
  	}     
}
path='<%=request.getContextPath()%>';
</script>
</head>
<body >
<html:form action="/NewOrderAction" styleId="hardwarelineItem" method="post">
	<bean:define id="formBean" name="newOrderBean"></bean:define>
	<div class="head"> <span>Delete Hardware Line Item</span> </DIV>
		<fieldset class="border1">
			<legend> <b>Delete Hardware Line Item</b> </legend>
			<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="left" ><a href="#" onclick="FirstPage('ORDERNO','CANCEL_HARDWARE_LINEITEM')">First</a>&nbsp;&nbsp;<a href="#" onclick="NextPage('ORDERNO','CANCEL_HARDWARE_LINEITEM')">Next</a></td>
		<td align="center"  class="last">Go To Page :
			<input type="text" class="inputNew" name="goToPageNumber" size="4" 
				maxlength="10" value="" id="id_paging_goToPage">
				<a href="#" onclick="goToPageSubmit('ORDERNO','CANCEL_HARDWARE_LINEITEM')">GO </a>
		</td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo"  class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="right"><a href="#" onclick="PrevPage('ORDERNO','CANCEL_HARDWARE_LINEITEM')">Prev</a>&nbsp;&nbsp;<a href="#" onclick="LastPage('ORDERNO','CANCEL_HARDWARE_LINEITEM')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
	   <tr class="normal">
	   		<td><img src="gifs/top/home.gif" onClick="goToHome('Home');"></img></td>
	   		<td  align="center" colspan="4">
				<div style="float:left;"><strong>A\c Number:</strong><html:text  property="accountID" styleId="accountID"  onkeypress="onPressEnterSearch(event)" styleClass="inputBorder1" maxlength="10" ></html:text></div>
			</td>
			<td  align="center" colspan="4">
				<div style="float:left;"><strong>A\c Name:</strong><html:text  property="accountName" styleId="accountName" onkeypress="onPressEnterSearch(event)"  styleClass="inputBorder1" maxlength="25" ></html:text></div>
			</td>
			<td  align="center" colspan="4">
				<div style="float:left;"><strong>Order No:</strong><html:text  property="searchCRMOrder" styleId="searchCRMOrder" onkeypress="onPressEnterSearch(event)"  styleClass="inputBorder1" maxlength="10" ></html:text></div>
			</td>
			<td  align="center" colspan="4">
				<div style="float:left;"><strong>LSI No:</strong><html:text  property="serviceID" styleId="serviceID" onkeypress="onPressEnterSearch(event)"  styleClass="inputBorder1" maxlength="10" ></html:text></div>
			</td>
			<td  align="center" colspan="4">
				<div style="float:left;"><strong>Line Item No:</strong><html:text  property="parentServiceProductId"  styleId="parentServiceProductId" onkeypress="onPressEnterSearch(event)"  styleClass="inputBorder1" maxlength="10" ></html:text></div>
			</td>
			<td>
				<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
				<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;
				<!-- <div class="searchBg" onclick="DrawAccountType()"  style="margin-right:10px;" title="Search here"><a href="#">Search</a></div></td>-->
			</td>
		</tr>
	</table>
	<table width="100%" border="1"  id="tabHardwareLines" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
		<tr class="grayBg">
			<td width="8%" align="center" colspan="3"><b>Account Details</b></td>
			<td width="8%" align="center" colspan="3"><b>Order Details</b></td>
			<td width="8%" align="center" colspan="4"><b>Service Details</b></td>
			<td width="8%" align="center" colspan="4"><b>Line Item Details</b></td>				
		</tr>
		<tr class="grayBg">
			<td width="2%" align="center"><b>S.No.</b></td>
			<td width="5%" align="center"><b>A\C No</b></td>
			<td width="9%" align="center"><b>A\C Name</b></td>
			<td width="7%" align="center"><b>Order No</b></td>
			<td width="6%" align="center"><b>Order Stage</b></td>
			<td width="6%" align="center"><b>Order Type</b></td>
			<td width="6%" align="center"><b>Service No</b></td>
			<td width="10%" align="center"><b>Service Name</b></td>
			<td width="5%" align="center"><b>LSI No</b></td>
			<td width="7%" align="center"><b>M6 Order No</b></td>
			<td width="7%" align="center"><b>Line Item No</b></td>
			<td width="10%" align="center"><b>Line Item Name</b></td>
			<td width="8%" align="center"><b>CircuitID</b></td>	
			<td width="3%" align="center"><b>Select<input name="chkAllOrder" id="chkAllOrder"  type="checkbox" onclick="fnCheckOrderAll()"></b></td>							
		</tr>				
	</table>
	<table border="1" cellspacing="0" cellpadding="0" align="left" width="100%" id='selectPOtable'>	
		<tr>
			<td colspan="8" align="center">
			    <input type="button" name="btnApproveOrderDetail" style="font-style: normal;" value="Request for Deletion" onClick="fnProcessRequest()">
			</td>
		</tr>
	</table>
</fieldset>
</html:form>
</body>
<script type="text/javascript">
	DrawTable('ORDERNO','CANCEL_HARDWARE_LINEITEM')
</script>
</html>


	

