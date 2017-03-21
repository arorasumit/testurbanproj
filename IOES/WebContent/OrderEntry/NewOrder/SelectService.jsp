<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ROHIT VERMA	4-Mar-11	00-05422		Created By -->
<!--[002]	 LAWKUSH		06-05-11	00-05422		Setting paging sorting -->
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<%@page import="java.util.ArrayList;"%>
<html>
<head>
<title>Select Service</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/formValidations.js"></script>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script language="javascript" type="text/javascript">

 function saveService(servicetTypeId,serviceTypeName)
 {
	//RAGHU
	var callerWindowObj = dialogArguments;
	var orderNo1 = <%=request.getParameter("orderNo")%>;
	var roleID = <%=request.getParameter("roleID")%>;
	var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    var result = jsonrpc.processes.fnDmxValidation(orderNo1);
    //alert(result.list[0]);//COUNT_GLOBALSERVICE
    ///alert(result.list[1]);//NO_OF_PLAYER
    //alert(result.list[2]);//COUNT_CHILD_LINEITEM
    //alert(result.list[3]);//COUNT_GLOBAL_LINEITEM_IN_SERVICE
    if(servicetTypeId == 201)
    {
	    if(result.list[0] ==1 )
	    {
	      alert('You can add only one global order!!!');
	      window.close();
	      return false;
	    }
	}
	//alert(result.list[3]);
	if(servicetTypeId == 202)
    {	
    //Meenakshi : Commenting condition to enable Child service selection without adding Global in New Order.
      /*if (result.list[0]< 1)
      {
       alert('Please add global order first then add child order!!!');
       window.close();
       return false;
      } */
    /*  if (result.list[3]< 1)
      { alert(58);
       alert('Please add line Item in Global first!!!');              
       callerWindowObj.countService=1;
	   callerWindowObj.drawTable();
       window.close();
       return false;
      } */
      //Ramana  start
   
    /* if((result.list[1] !=0) && (result.list[2] == result.list[1]))
     {
      alert('Total No of Interactive Player should not be exceed the Global Limit!!!');
      window.close();
      return false;
     } */
      //Ramana end
    }
     
     
	//}
			serviceTypeName = unescape(serviceTypeName);
			var orderNo = <%=request.getParameter("orderNo")%>;
			var frm=document.getElementById('searchForm');
			
			if(frm.chk.length==undefined)
			{
					if(frm.chk.checked==true)
						{
							//var selectedText = document.getElementById("serviceSubTypeIdchk0");
							
							var jsData ={
				                 serviceTypeId:servicetTypeId,
				                 serviceTypeName:serviceTypeName,
				               //  serviceSubtypeId:document.getElementById("serviceSubTypeIdchk0").value,
				                 poNumber:orderNo,
				                 roleID:roleID
				                };
							jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
							//[001] Start
							var sessionId='<%=request.getSession().getId() %>';
							var lstService = jsonrpc.processes.insertService(jsData,sessionId);
							//[001] End
							//UpdateParentInterface(lstService.serviceId,serviceTypeName,selectedText.options[selectedText.selectedIndex].text);
							
							//PAGING-SERVICE-LINE-14-10-2012
							//UpdateParentInterface(lstService.serviceId,serviceTypeName);
							callerWindowObj.FirstPage('SERVICENO','SERVICELINETABLE');							
							window.close();	
						}
				
			}
			else
			{
				for(i=0;i<frm.chk.length;i++)
				{
					if(frm.chk[i].checked==true)
						{
							//var selectedText = document.getElementById("serviceSubTypeIdchk"+i);
							
							var jsData ={
				                 serviceTypeId:servicetTypeId,
				                 serviceTypeName:serviceTypeName,
				                 //serviceSubtypeId:document.getElementById("serviceSubTypeIdchk"+i).value,
				                 poNumber:orderNo,
				                 roleID:roleID
				                };
							jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
							//[001] Start
							var sessionId='<%=request.getSession().getId() %>';
							var lstService = jsonrpc.processes.insertService(jsData,sessionId);
							//[001] End
							//UpdateParentInterface(lstService.serviceId,serviceTypeName,selectedText.options[selectedText.selectedIndex].text);
							
							//PAGING-SERVICE-LINE-14-10-2012
							//UpdateParentInterface(lstService.serviceId,serviceTypeName);
							callerWindowObj.FirstPage('SERVICENO','SERVICELINETABLE');							
							window.close();	
						}
				}
			}
			
			
	}
	//function UpdateParentInterface(serviceno , serviceTypeName , serviceSubTypeName)
	function UpdateParentInterface(serviceno , serviceTypeName )
	{
			var frm = document.getElementById('searchForm');
			var callerWindowObj = dialogArguments;
			var iCounter =  frm.hdnServiceCounter.value;

			
			if(iCounter<1)
			{
				callerWindowObj.document.getElementById("txtServiceName").value = serviceTypeName;
				//callerWindowObj.document.getElementById("txtServiceSubTypeName").value = serviceSubTypeName;
				callerWindowObj.document.getElementById("txtServiceNo").value = serviceno;
				
				if(callerWindowObj.btnAttributes=="0")
					callerWindowObj.document.getElementById("ServiceAtt").style.display="block";
				else
				    callerWindowObj.document.getElementById("ServiceAtt").style.display="none";	
			}
			else
			{
				callerWindowObj.document.getElementById("txtServiceName"+iCounter).value = serviceTypeName;
				//callerWindowObj.document.getElementById("txtServiceSubTypeName"+iCounter).value = serviceSubTypeName;
				callerWindowObj.document.getElementById("txtServiceNo"+iCounter).value = serviceno;
			}	
			
			callerWindowObj.countService=1;
			callerWindowObj.drawTable();
			window.close();
	}
	
	function addCombo() 
	{
				drawTable();
				return false;
						
		
		/*for(i=0;i<lstService.list[0].serviceSubType.list.length;i++)
		{
			var combo = document.getElementById("combo");
			var option = document.createElement("option");
			option.text = lstService.list[0].serviceSubType.list[i].serviceSubTypeName;
			option.value = lstService.list[0].serviceSubType.list[i].serviceSubTypeId;
		try 
			{
				combo.add(option, null); //Standard
			}
			catch(error) 
			{
				combo.add(option); // IE only
			}
		}	*/
	}
	
	
	function DrawServiceTypeTable()
   {
   var tr, td, i, j, oneRecord;
   var frm=document.getElementById('searchForm');
   
   mytable = document.getElementById('tabServiceType');
  
   var iCountRows = mytable.rows.length;
   for (i = 1; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(1);
	   }   
 		
   //[001] Start
   <%String orderNo=request.getParameter("orderNo");%>
   var orderNo='<%=orderNo%>';
   	document.getElementById('txtPageNumber').value= pageNumber;   	
	sync();
    var jsData =			
    {
    	startIndex:startRecordId,
		endIndex:endRecordId,
		sortBycolumn:sortByColumn,
		sortByOrder:sortByOrder,
		serviceTypeName:document.getElementById('txtServiceTypeName').value,
		serviceTypeId:Number(document.getElementById('txtServiceTypeId').value),
		orderNo:orderNo
	};
    //[001] End 
    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    var lstService = jsonrpc.processes.populateSearch(jsData);    
    iTotalLength=lstService.list.length;
	if(iTotalLength !=0)
	{
		iNoOfPages = lstService.list[0].maxPageNo;
	}
	
	document.getElementById('txtMaxPageNo').value=iNoOfPages;				
	var colors=new Array("normal","lightBg");
		    
		    for (i = 0 ; i < iTotalLength; i++)
		    {
				var str;
				var colorValue=parseInt(i)%2;	 		  
				var tblRow=document.getElementById('tabServiceType').insertRow();
				tblRow.className=colors[colorValue];
						
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str="<input name='chk' id='chk' type='radio' onClick=saveService('"+lstService.list[i].serviceTypeId+"','"+escape(lstService.list[i].serviceTypeName)+"') value=''>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str= lstService.list[i].serviceTypeName;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstService.list[i].serviceTypeId;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
			
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "center";
				//tblcol.vAlign="top";
				//str="<select  name='serviceSubTypeId' id='serviceSubTypeId' ></select>"
				//str="<select  name='serviceSubTypeIdchk"+ i +"' id='serviceSubTypeIdchk"+ i +"' ></select>"
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				
				
				
			/*	for(j=0;j<lstService.list[i].serviceSubType.list.length;j++)
				{
					var combo = document.getElementById("serviceSubTypeIdchk"+i);
					var option = document.createElement("option");
					option.text = lstService.list[i].serviceSubType.list[j].serviceSubTypeName;
					option.value = lstService.list[i].serviceSubType.list[j].serviceSubTypeId;
				try 
					{
						combo.add(option, null); //Standard
					}
				catch(error) 
					{
						combo.add(option); // IE only
					}
				}	 */
				
				
			
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
   
    		var tblRow=document.getElementById('tabServiceType').insertRow();
	    	var tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.colSpan = 4;
			tblcol.vAlign="top";
			tblcol.innerHTML = "No Records Found";
   		 }
   		 return false;
    
}
	
function clearFields()
{
	document.getElementById('txtServiceTypeName').value="";
	document.getElementById('txtServiceTypeId').value="";
}
var path='<%=request.getContextPath()%>';
function isBlankFields()
{
	var serviceTypeName=document.getElementById('txtServiceTypeName').value;
	var serviceTypeId=document.getElementById('txtServiceTypeId').value;
	
	var searchFlag=1;
	
	
	 if( trim(document.getElementById('txtServiceTypeName').value).length>0)
		{
			 if(ValidateTextField(document.getElementById('txtServiceTypeName'),path,'Service Type Name')==false)
			 {
			 searchFlag=0;
			 return false;
			 }
		}
		
		if(document.getElementById('txtServiceTypeId').value.length > 0)
		{
			if(checkdigits(document.getElementById('txtServiceTypeId')))
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
		searchFromList('SERVICETYPENAME','SELECTSERVICETYPE');
		}
	
	
}

</script>
<body>
<html:form action="/NewOrderAction" styleId="searchForm" method="post">
		
		<bean:define id="formBean" name="newOrderBean"></bean:define>
		<html:hidden property="hdnServiceCounter"  />
		<div class="head"> <span>Select Service</span> </div>
		<fieldset class="border1">
				<legend> <b>Service List</b> </legend>
	
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('SERVICETYPENAME','SELECTSERVICETYPE')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('SERVICETYPENAME','SELECTSERVICETYPE')">Next</a></td>
		<td align="center"><input type="text" id="txtPageNumber" class="inputBorder1" readonly="true" size="2"/>of
		<input type="text" id="txtMaxPageNo" class="inputBorder1" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#"  id="prev" onclick="PrevPage('SERVICETYPENAME','SELECTSERVICETYPE')">Prev</a>&nbsp;&nbsp;<a href="#"  id="last" onclick="LastPage('SERVICETYPENAME','SELECTSERVICETYPE')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Service Type Name:</strong><input type="text" id="txtServiceTypeName"  name="servicetype" maxlength=40 class="inputBorder1"  onkeydown="if (event.keyCode == 13)  return isBlankFields();">
					</div></td>
					<td  align="center" colspan="4">
					<div style="float:left;"><strong>Service Type Id:</strong><input type="text" id="txtServiceTypeId"  name="servicetype" class="inputBorder1"  onkeydown="if (event.keyCode == 13)  return isBlankFields();">
					</div></td>
					<td>
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
					<!-- <div class="searchBg" onclick="DrawAccountType()"  style="margin-right:10px;" title="Search here"><a href="#">Search</a></div></td>-->
				</tr>
	</table>
	<table width="100%" border="1"  id="tabServiceType" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="2%" align="center">Select</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('SERVICETYPENAME','SELECTSERVICETYPE')">Service Type Name</a>
				</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('SERVICETYPEID','SELECTSERVICETYPE')">Service Type Id</a>
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
	//DrawServiceTypeTable()
	DrawTable('SERVICETYPENAME','SELECTSERVICETYPE')
</script>
</html>