<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.forms.NewOrderDto"%>
<%@page import="java.util.ArrayList;"%>
<html>
<head>
<title>Select Service</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/formValidations.js"></script>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script language="javascript" type="text/javascript">

	function saveService(servicetTypeId,serviceTypeName)
	{
	       //RAGHU
	var orderNo1 = <%=request.getParameter("orderNo")%>;
	/*var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    var result = jsonrpc.processes.fnDmxValidation(orderNo1);
    //alert(result.list[0]);//COUNT_GLOBALSERVICE
    ///alert(result.list[1]);//NO_OF_PLAYER
    //alert(result.list[2]);//COUNT_CHILD_LINEITEM
    if(servicetTypeId == 201)
    {
	    if(result.list[0] ==1 )
	    {
	      alert('You can add only one global order!!!');
	      window.close();
	      return false;
	    }
	}
	if(servicetTypeId == 202)
    {
      if (result.list[0]< 1)
      {
       alert('Please add global order first then add child order!!!');
       window.close();
       return false;
      }
     if(result.list[2] == result.list[1])
     {
      alert('Child CRM orders should equal the no of screens as in global CRM order!!!');
      window.close();
      return false;
     }
	}
	*/
	//
			serviceTypeName = unescape(serviceTypeName);
			var orderNo = <%=request.getParameter("orderNo")%>;
			var frm=document.getElementById('searchForm');
			
			if(frm.chk.length==undefined)
			{
					if(frm.chk.checked==true)
						{
							var selectedText = document.getElementById("serviceSubTypeIdchk0");
							
							var jsData ={
				                 serviceTypeId:servicetTypeId,
				                 serviceTypeName:serviceTypeName,
				                 serviceSubtypeId:document.getElementById("serviceSubTypeIdchk0").value,
				                 poNumber:orderNo
				                };
							jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
							var lstService = jsonrpc.processes.insertService(jsData);
							UpdateParentInterface(lstService.serviceId,serviceTypeName,selectedText.options[selectedText.selectedIndex].text);
						}
				
			}
			else
			{
				for(i=0;i<frm.chk.length;i++)
				{
					if(frm.chk[i].checked==true)
						{
							var selectedText = document.getElementById("serviceSubTypeIdchk"+i);
							
							var jsData ={
				                 serviceTypeId:servicetTypeId,
				                 serviceTypeName:serviceTypeName,
				                 serviceSubtypeId:document.getElementById("serviceSubTypeIdchk"+i).value,
				                 poNumber:orderNo
				                };
							jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
							var lstService = jsonrpc.processes.insertService(jsData);
							UpdateParentInterface(lstService.serviceId,serviceTypeName,selectedText.options[selectedText.selectedIndex].text);
						}
				}
			}
			
			
	}
	function UpdateParentInterface(serviceno , serviceTypeName , serviceSubTypeName)
	{
			var frm = document.getElementById('searchForm');
			var callerWindowObj = dialogArguments;
			var iCounter =  frm.hdnServiceCounter.value;

			
			if(iCounter<1)
			{
				callerWindowObj.document.getElementById("txtServiceName").value = serviceTypeName;
				callerWindowObj.document.getElementById("txtServiceSubTypeName").value = serviceSubTypeName;
				callerWindowObj.document.getElementById("txtServiceNo").value = serviceno;
				callerWindowObj.document.getElementById("ServiceAtt").style.display="block";
			}
			else
			{
				callerWindowObj.document.getElementById("txtServiceName"+iCounter).value = serviceTypeName;
				callerWindowObj.document.getElementById("txtServiceSubTypeName"+iCounter).value = serviceSubTypeName;
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
	
	
	function drawTable()
   {
   var tr, td, i, j, oneRecord;
   var test;
   mytable = document.getElementById('SearchService');
  
   var iCountRows = mytable.rows.length;
  
   
	
    var jsData =			
    {
		serviceTypeName:document.getElementById('serviceTypeName').value
	};
    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    test = jsonrpc.processes.populateSearch(jsData);
    if(test.list!="")
    {
		    if(isNull(test.list[0].errors)==false)
		    {
		    	alert(test.list[0].errors.list[0]);
		    	return false;
		    }
		    else
		    {
		       for (i = 1; i <  iCountRows; i++)
				   {
				       mytable.deleteRow(1);
				   }   
		    
		    
		    for (i = 0 ; i < test.list.length; i++)
		    {
				var str;
				var tblRow=document.getElementById('SearchService').insertRow();
						
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str="<input name='chk' id='chk' type='radio' onClick=saveService('"+test.list[i].serviceTypeId+"','"+escape(test.list[i].serviceTypeName)+"') value=''>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str= test.list[i].serviceTypeName;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=test.list[i].serviceTypeId;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
			
				var tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				//str="<select  name='serviceSubTypeId' id='serviceSubTypeId' ></select>"
				str="<select  name='serviceSubTypeIdchk"+ i +"' id='serviceSubTypeIdchk"+ i +"' ></select>"
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				
				
				for(j=0;j<test.list[i].serviceSubType.list.length;j++)
				{
					var combo = document.getElementById("serviceSubTypeIdchk"+i);
					var option = document.createElement("option");
					option.text = test.list[i].serviceSubType.list[j].serviceSubTypeName;
					option.value = test.list[i].serviceSubType.list[j].serviceSubTypeId;
				try 
					{
						combo.add(option, null); //Standard
					}
					catch(error) 
					{
						combo.add(option); // IE only
					}
				}	
				
				
			
		    }
		    
		  }
    }
    else
    {
    		for (i = 1; i <  iCountRows; i++)
				   {
				       mytable.deleteRow(1);
				   }
    		var tblRow=document.getElementById('SearchService').insertRow();
	    	var tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.colSpan = 4;
			tblcol.vAlign="top";
			tblcol.innerHTML = "No Records Found";
    	
    }
    
}
	
	
</script>
<body>
<html:form action="/ChangeOrderAction" styleId="searchForm" method="post">
		
		<bean:define id="formBean" name="newOrderBean"></bean:define>
		<html:hidden property="hdnServiceCounter"  />
		<div class="head"> <span>Select Service</span> </div>
		<fieldset class="border1">
				<legend> <b>Service List</b> </legend>
	
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
					<tr class="grayBg">
						<td width="10%" align="center" colspan="2"><html:text property="serviceTypeName" maxlength="50" onkeydown="if (event.keyCode == 13) return addCombo();"  style="width:70px; float:left;" styleClass="inputBorder1"/></td>
						<td width="10%" align="center" colspan="2">
						<div class="searchBg" onclick="addCombo()"  style="margin-right:10px;"><a href="#">Search</a></div>
						</td>
					</tr>
				</table>
				
				<table width="100%"  border="1" cellspacing="0" cellpadding="0" id="SearchService" class="tab2">
						<tr class="grayBg" align="center">
							<td width="5%">Select</td>
							<td width="43%">Service Type</td>
							<td width="12%">Product Id</td>
							<td width="10%">Service Sub Type</td>
						</tr>
							
						
				  </table>	
				 	
			</fieldset>
</html:form>		
</body>
<script type="text/javascript">
	drawTable()
</script>
</html>