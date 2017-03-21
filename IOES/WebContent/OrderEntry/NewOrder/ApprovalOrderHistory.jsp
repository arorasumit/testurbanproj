<!--Tag Name Resource Name  Date		CSR No			Description -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.beans.UserInfoDto"%>
<%@page import="com.ibm.ioes.utilities.AppConstants"%>
<%@page import="com.ibm.ioes.utilities.AppUtility"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="java.util.HashMap"%>
<html>
<head>
<title>Approval Order History</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>

<script language="javascript" type="text/javascript">

<%
	HashMap userHashMap = (HashMap)request.getSession().getServletContext().getAttribute(AppConstants.APP_SESSION); 
	HttpSession sessionObj = (HttpSession) userHashMap.get(session.getId());
	UserInfoDto objUserDto  = (UserInfoDto)sessionObj.getAttribute(AppConstants.APP_SESSION_USER_INFO);
%>
var PAGE_SIZE_ORDER_APPROVAL=<%=objUserDto.getPageSizeOrderApprovalHistory()%>;

function m6servicenoLoading()
{
	var poNumber = <%=request.getParameter("poNumber")%>;
	var stage = "<%=request.getParameter("stage")%>";
	jsonrpc1 = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");	
	var approvalTabVisible = jsonrpc1.processes.approvalTabVisible(poNumber);
	if (approvalTabVisible == 'Valid')
	{
		
		jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");	
		//var m6servicenoList = jsonrpc.processes.getm6servicenoList(poNumber);
		var getServiceList = jsonrpc.processes.getServiceList(poNumber);
		var tr, td, i, j, oneRecord;  	
		/*if(m6servicenoList.list.length <= 3)	
		{
			orderHistoryList();
		}   */
		//else
		//{		
		if(stage == 'AM' || stage == 'PM' || stage == 'COPC' || stage == 'SED' || stage == 'New')	
		{
			DrawApprovalList();
		}
		else
		{
			DrawApprovalList();
		    for(j=0;j<getServiceList.list.length;j++)
	    	{      
	   	  	    var combo = document.getElementById('serviceno_m6');	    		
		    	var opt = document.createElement("option");
		   		opt.text = getServiceList.list[j].serviceID;
				opt.value = getServiceList.list[j].serviceID;			  		
				try 
				{				
					combo.add(opt, null); //Standard				
				}
				catch(error) 
				{								
					combo.add(opt); // IE only
					
				}		    	
	    	}
		//}
		}
		
	}
}
//Satish Starts
function exportToExcel()
{
	<%-- alert('here in export to excel'); --%>
		var poNumber = <%=request.getParameter("poNumber")%>;
	var myForm=document.getElementById('apprvalhistory');
	<%-- alert(myForm.toExcel.value);--%>
	myForm.toExcel.value='true';
	<%--alert(myForm.toExcel.value);--%>
	<%-- var excelName= '<%= AppConstants.ACTION_EXCEL %>'; --%>
		myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=fetchApprovalOrderHistory&poNumber="+poNumber;
		/* showLayerExcel(); */
		myForm.submit();
}
//Satish Ends

function searchM6No()
{	
	searchFromList('TASK_OWNER_ID','SELECTAPPROVAL');
}


function getTip(value)
{	
	Tip("<Table border=0> "+ value +"   </table>");	
}
function DrawApprovalList()
{
	pageRecords=PAGE_SIZE_ORDER_APPROVAL;
	var m6servicenoList;
	var m6OrderNo = document.getElementById('serviceno_m6').value;
	var poNumber = <%=request.getParameter("poNumber")%>;
	var mytable = document.getElementById('orderHistory');  
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
				pageRecords:pageRecords
			};   
	  		 
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	if(m6OrderNo == 0)
	{
		m6servicenoList = jsonrpc.processes.getm6servicenoList_all(poNumber,jsData);
	}
	else
	{
	  m6servicenoList = jsonrpc.processes.getm6servicenoList_selected(poNumber,m6OrderNo,jsData);
	}
	var iTotalLength=m6servicenoList.list.length;	
	if(iTotalLength !=0)
	{
		iNoOfPages = m6servicenoList.list[0].maxPageNo;
	}
	
	else
	{     
		 iNoOfPages=1;
	}
	document.getElementById('txtMaxPageNo').value=iNoOfPages;	 
	for(var i=0 ; i < m6servicenoList.list.length ; i++)
	{
	//alert('length = '+m6servicenoList.list.length);
	var tblRow=document.getElementById('orderHistory').insertRow();
			
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' style='width:96%;'  class='inputBorder5' value='"+m6servicenoList.list[i].historyActionRoleName+"'  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
		
	if(m6servicenoList.list[i].taskOwnerId == 1)
	{
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)'class='inputBorder5' style='width:96%;'  value='AM'  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	}
	if(m6servicenoList.list[i].taskOwnerId == 2)
	{
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)'class='inputBorder5' style='width:96%;'  value='PM'  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	}
	if(m6servicenoList.list[i].taskOwnerId == 3)
	{
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)'class='inputBorder5' style='width:96%;'  value='COPC'  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	}
	if(m6servicenoList.list[i].taskOwnerId == 4)
	{
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)'class='inputBorder5' style='width:96%;'  value='SED'  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	}
	if(m6servicenoList.list[i].taskOwnerId == 0)
	{
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)'class='inputBorder5' style='width:96%;' value='-'  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	}
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)'class='inputBorder5' style='width:96%;' value='"+m6servicenoList.list[i].actionTakenById+"'  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)'class='inputBorder5' style='width:96%;' value='"+m6servicenoList.list[i].actionTakenByName+"'  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)'class='inputBorder5' style='width:96%;'  value='"+m6servicenoList.list[i].historyAction+"'  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' class='inputBorder5' style='width:96%;' value='"+m6servicenoList.list[i].historyActionDate+"'  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	if(m6servicenoList.list[i].taskSendTo == null)
	{
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)'class='inputBorder5'  style='width:96%;' value=''  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	}
	else
	{
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' class='inputBorder5' style='width:96%;' value='"+m6servicenoList.list[i].taskSendTo+"'  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	}
	
	if(m6servicenoList.list[i].historyRejectionSendTo == null)
	{
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)'class='inputBorder5' style='width:96%;'  value=''  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	}
	else
	{
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)'class='inputBorder5' style='width:96%;'  value='"+m6servicenoList.list[i].historyRejectionSendTo+"'  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	}
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	//str = "<span onmouseover='getTip(this.innerText)'>" + m6servicenoList.list[i].historyRemraks + "</span>" ;
	str = "<textarea onmouseover='getTip(value)' readonly='true'>" + m6servicenoList.list[i].historyRemraks + "</textarea>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' class='inputBorder5' style='width:96%;'  value='"+m6servicenoList.list[i].serviceno_m6+"'  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;	
	
	//added by satish start for exporting two more column m6 and service id
	 var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' class='inputBorder5' style='width:96%;'  value='"+m6servicenoList.list[i].m6No+"'  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;	
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' class='inputBorder5' style='width:96%;'  value='"+m6servicenoList.list[i].serviceId+"'  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents; 
	//added by satish end
	
			
	//LAWKUSH START
	
	
	if(m6servicenoList.list[i].reasonName == null)
	{
	
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str="<input onmouseover='getTip(value)' class='inputBorder5' style='width:96%;'  value=''  type='' readonly='true'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}
	else
	{		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str="<input onmouseover='getTip(value)' class='inputBorder5' style='width:96%;'  value='"+m6servicenoList.list[i].reasonName+"'  type='' readonly='true'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
	}
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' class='inputBorder5' style='width:96%;'  value='"+m6servicenoList.list[i].delayReasonValue+"'  type='' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	//LAWKUSH END
	
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
	
	
	
}



//-------------------------------------------------------------------------
</script>
</head>
<body onload="m6servicenoLoading();">

<div class="head"> <span>Order History
 <div class="searchBg" style="font-size:11px; text-align: right;color: red;"><a href="#" title="Export To Excel" onclick="exportToExcel();">Export To Excel</a></div></span>
 </div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
<center>
<html:form action="/NewOrderAction" styleId="apprvalhistory" method="post">
<bean:define id="formBean" name="newOrderBean"></bean:define>
<html:hidden property="toExcel"/>
<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
<!-- start [003] -->	
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('TASK_OWNER_ID','SELECTAPPROVAL')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('TASK_OWNER_ID','SELECTAPPROVAL')">Next</a></td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo"  class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('TASK_OWNER_ID','SELECTAPPROVAL')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('TASK_OWNER_ID','SELECTAPPROVAL')">Last</a></td>
	</tr>
	</table>	   		
  
 <table width="100%" border="1"  id="orderHistory" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg" >
		<!-- vijay
		 modified code for make remaks as a wrapble text -->
			<td  align="center"><span>Task Owner</span></td>
			<td  align="center"><span>RoleName</span></td>
			<td  align="center"><span>Employee Id</span></td>
			<td  align="center"><span>Action Taken by</span></td>
			<td  align="center"><span>Task Action</span></td>
			<td  align="center"><span>Action Date</span></td>
			<td  align="center"><span>Task Send To</span></td>
			<td  align="center"><span>Rejection Send to</span></td>
			<td  align="center"><span>      Remarks      </span></td>							
			<td  align="center"> <span>
			<select id="serviceno_m6" name="serviceno_m6" onchange="searchM6No()">
				<option value="0" >Select Service/M6OrderNo</option>									  			
			</select>						
			</span>
			</td>	
			<td  align="center"><span>Rejection Reason </span></td>						
			<td  align="center"><span>Delay Reason </span></td>							
		</tr>						
	</table>
  	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<td width="18%" >&nbsp;</td>
			<td width="20%" colspan="3">&nbsp;</td>
		</tr>
	</table>	
</html:form>
</center>
</body>
<script type="text/javascript">
DrawTable('TASK_OWNER_ID','SELECTAPPROVAL')
</script>


</html>