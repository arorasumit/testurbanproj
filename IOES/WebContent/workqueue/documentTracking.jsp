<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]    SUMIT ARORA    07-MAR-11   00-05422        Length Of Remarks to increased and restricted to 256 -->
<!--[00055]	 Ashutosh Singh		08-July-11	CSR00-05422     Document tracking for Diffrent Role -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<html>
<head>
<title>:: View Order: Integrated Order Entry System</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="./gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<style type="text/css">
    .opaqueLayer
    {
        display:none;
        position:absolute;
        top:0px;
        left:0px;
        opacity:0.5;
        filter:alpha(opacity=50);
        background-color: #000000;
        z-Index:1000;
        text-align:center;
        vertical-align:middle;
        padding:100px;
    }
    
</style>
<script language="javascript"  type="text/javascript" >
//[00055] Start
var roleId=0;
var orderNo=0;
//[00055] End

function goToHome()
{
	document.forms[0].action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	showLayer();
	document.forms[0].submit();
	
}

function setformValue()
{
	var callerWindowObj = dialogArguments;
	//document.getElementById('searchTaskId').value =  callerWindowObj.document.getElementById("searchTaskId").value;
	//[00055] Start (Added By Ashutosh)
	orderNo="<%=request.getParameter("orderNo")%>";
	//roleId = callerWindowObj.document.getElementById('searchTaskOwnerId').value;	
	getUploadedFileforApproval(orderNo,roleId);
	////[00055]  End
}
//[00055] Start
function getUploadedFileforApproval(orderNo,roleId)
{
	
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var roleList = jsonrpc.processes.getListOfRoleFromWorkflow(orderNo);
	
   	var mytable = document.getElementById('fileCheckListTable');
 
    var iCountRows = mytable.rows.length;
   
  
    for (i = 0; i <  iCountRows; i++)
    {
        mytable.deleteRow(0);
    }   
  
   	var headerRow=document.getElementById('fileCheckListTable').insertRow();
   	var tblcol=headerRow.insertCell();
	tblcol.align = "left";
	tblcol.width="10%"
	tblcol.vAlign="top";
	tblcol.style.fontSize="12px";
	tblcol.style.fontWeight="bold";
	str= "S.NO";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
   	var tblcol=headerRow.insertCell();
	tblcol.align = "left";
	tblcol.width="60%"
	tblcol.vAlign="top";
	tblcol.style.fontSize="12px";
	tblcol.style.fontWeight="bold";
	str= "Document Name";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	//<!--Added to disable  for RoleChange by Vishwa on 18-Apr-2012	-->		
	var OrderCreatedBy;
	if(roleList.list.length>0)
	{
		OrderCreatedBy=roleList.list[0].roleId;
		
	}
	
	for(i=0;i<roleList.list.length;i++)
	{
		var roleName=roleList.list[i].roleName;
		
		var tblcol=headerRow.insertCell();
		tblcol.align = "left";
		tblcol.width="12%"
		tblcol.vAlign="top";
		tblcol.style.fontSize="12px";
		tblcol.style.fontWeight="bold";
		str= ""+roleName;
		CellContents = str;	
		tblcol.innerHTML = CellContents;
		
		
	}
	var tblcol=headerRow.insertCell();
	tblcol.align = "left";
	tblcol.width="60%"
	tblcol.vAlign="top";
	tblcol.style.fontSize="12px";
	tblcol.style.fontWeight="bold";
	str= "Contarct Tracking";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var fileList = jsonrpc.processes.getUploadedFileList(orderNo,roleId);
	var counter=1;
	
	for(i=0;i<fileList.list.length;i++)
	{
		var fileId=fileList.list[i].fileId;
		var str;
		var tblRow=document.getElementById('fileCheckListTable').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=""+counter+"<input type='hidden' name='fileId' id='fileId"+counter+"' value='"+fileId+"' >"
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=""+fileList.list[i].fileName+"<input type='hidden'  name='fileName' id='fileName"+counter+"' value='"+fileList.list[i].fileName+"'>"
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		for(j=0;j<roleList.list.length;j++)
		{
			if(roleList.list[j].roleId==1)
			{
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				if(roleId==1 && fileList.list[i].roleId==null)
					str ="<input type='hidden' class='inputBorder1' name='AM' id='AM"+counter+"' value='0'><input name='chkAMDetail' id='chkAMDetail"+fileId+"' type='checkbox'>";
				else if(roleId==1 && fileList.list[i].roleId!=null ) 
					str ="<input type='hidden' class='inputBorder1' name='AM' id='AM"+counter+"' value='0'><input name='chkAMDetail' id='chkAMDetail"+fileId+"' type='checkbox'>";
				else 
					str ="<input type='hidden' class='inputBorder1' name='AM' id='AM"+counter+"' value='0'><input name='chkAMDetail' id='chkAMDetail"+fileId+"' type='checkbox' disabled='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
			}
			else if(roleList.list[j].roleId==2)
			{
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				if(roleId==2 && fileList.list[i].roleId==null)
					str ="<input type='hidden' class='inputBorder1' name='PM' id='PM"+counter+"' value='0'><input name='chkPMDetail' id='chkPMDetail"+fileId+"' type='checkbox' >";
				else if(roleId==2 && fileList.list[i].roleId!=null ) 
					str ="<input type='hidden' class='inputBorder1' name='PM' id='PM"+counter+"' value='0'><input name='chkPMDetail' id='chkPMDetail"+fileId+"' type='checkbox' >";
				else
					str ="<input type='hidden' class='inputBorder1' name='PM' id='PM"+counter+"' value='0'><input name='chkPMDetail' id='chkPMDetail"+fileId+"' type='checkbox' disabled='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
			}
			else if(roleList.list[j].roleId==3)
			{
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				if(roleId==3 && fileList.list[i].roleId==null)
					str ="<input type='hidden' class='inputBorder1' name='COPC' id='COPC"+counter+"' value='0'><input name='chkCOPCDetail' id='chkCOPCDetail"+fileId+"' type='checkbox' >";
				else if(roleId==3 && fileList.list[i].roleId!=null ) 
					str ="<input type='hidden' class='inputBorder1' name='COPC' id='COPC"+counter+"' value='0'><input name='chkCOPCDetail' id='chkCOPCDetail"+fileId+"' type='checkbox' >";			
				else
					str ="<input type='hidden' class='inputBorder1' name='COPC' id='COPC"+counter+"' value='0'><input name='chkCOPCDetail' id='chkCOPCDetail"+fileId+"' type='checkbox' disabled='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
			}
			
			else if(roleList.list[j].roleId==4)
			{
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				if(roleId==4 && fileList.list[i].roleId==null)
					str ="<input type='hidden' class='inputBorder1' name='SED' id='SED"+counter+"' value='0'><input name='chkSEDDetail' id='chkSEDDetail"+fileId+"' type='checkbox' >";
				else if(roleId==4 && fileList.list[i].roleId!=null ) 
					str ="<input type='hidden' class='inputBorder1' name='SED' id='SED"+counter+"' value='0'><input name='chkSEDDetail' id='chkSEDDetail"+fileId+"' type='checkbox' >";			
				else
					str ="<input type='hidden' class='inputBorder1' name='SED' id='SED"+counter+"' value='0'><input name='chkSEDDetail' id='chkSEDDetail"+fileId+"' type='checkbox' disabled='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
			}
			else if(roleList.list[j].roleId==OrderCreatedBy)
			{
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				if(roleId==OrderCreatedBy && fileList.list[i].roleId==null)
					str ="<input type='hidden' class='inputBorder1' name='OTHER' id='OTHER"+counter+"' value='0'><input name='chkOTHERDetail' id='chkOTHERDetail"+fileId+"' type='checkbox'>";
				else if(roleId==OrderCreatedBy && fileList.list[i].roleId!=null ) 
					str ="<input type='hidden' class='inputBorder1' name='OTHER' id='OTHER"+counter+"' value='0'><input name='chkOTHERDetail' id='chkOTHERDetail"+fileId+"' type='checkbox'>";
				else 
					str ="<input type='hidden' class='inputBorder1' name='OTHER' id='OTHER"+counter+"' value='0'><input name='chkOTHERDetail' id='chkOTHERDetail"+fileId+"' type='checkbox' disabled='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
			}
			 
		}
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str ="<input name='chkContractTracking' id='chkContractTracking"+fileId+"' type='checkbox'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;				
		
		counter++;
	}
	showAllUserSelectedFileCHKList(orderNo);
}

function showAllUserSelectedFileCHKList(orderNo)
{
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var selectedFileList = jsonrpc.processes.getALLSelectedFileCHKList(orderNo);
	var rowCounter=1;	
	for(i=0;i<selectedFileList.list.length;i++,rowCounter++)
	{
		var fileId=selectedFileList.list[i].fileId;		
		if(selectedFileList.list[i].roleId==1 && selectedFileList.list[i].selectDocStatus==1)
		{
			document.getElementById('chkAMDetail'+fileId).checked=true;
		}
		else if(selectedFileList.list[i].roleId==2 && selectedFileList.list[i].selectDocStatus==1)
		{
			document.getElementById('chkPMDetail'+fileId).checked=true;
		}
		else if(selectedFileList.list[i].roleId==3 && selectedFileList.list[i].selectDocStatus==1)
		{
			document.getElementById('chkCOPCDetail'+fileId).checked=true;
		}
		else if(selectedFileList.list[i].roleId==4 && selectedFileList.list[i].selectDocStatus==1)
		{
			document.getElementById('chkSEDDetail'+fileId).checked=true;
		}
		else if(selectedFileList.list[i].selectDocStatus==1)
		{
			document.getElementById('chkOTHERDetail'+fileId).checked=true;
		}
		 
		if(selectedFileList.list[i].finalTracking!=0)
		{
			document.getElementById('chkContractTracking'+fileId).checked=true;
		}
	}
}
function insertSelectedFileCHKList()
{	
	var fileTable = document.getElementById('fileCheckListTable');
			
	var selectedFileList ="";
	var roleName="";    	
	var rowCounter = 1;
	var status="";
	 if(fileTable.rows.length>1)
	 {
	 		if(roleId==2)  	
	 			roleName="PM";
	 		else if(roleId==3)
	 			roleName="COPC";
	 		else if(roleId==4)
	 			roleName="SED";
	 		else if(roleId==1)
	 		 	roleName="AM";
	 		else
	 			roleName="OTHER";
	 		 
	 			
			var chkLength=document.getElementsByName(''+roleName);				    	
	    	if(chkLength==undefined)
	    	{
	    		if(document.getElementById(''+roleName+''+rowCounter).checked==true)
	    		{
	    		   	selectedFileList=document.getElementById('fileId'+rowCounter).value;
		     	}	
	    	}
	    	else
	    	{	    		
		    	for (i = 0 ; i < fileTable.rows.length - 1; i++)
		    	{	
		    		var fileId=document.getElementById('fileId'+rowCounter).value;
		    		
			    	if(document.getElementById('chkContractTracking'+fileId).checked==true)
		    		{	    		   	
		    		   	selectedFileList=selectedFileList+','+fileId;
		    		   	status=status+','+1;		    		   	    		   	
			     	}else
			     	{
			     		selectedFileList=selectedFileList+','+fileId;
		    		   	status=status+','+0;		    		   
			     	}			     			     	
			     	rowCounter++;
		    	}
		    	
		    }
		    		
		   var jsData =			
		   {
			fileIds:selectedFileList,
			orderNumber:orderNo,	
			roleId:roleId,
			docStatus:status
					
		};
		
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		if(selectedFileList!="")
		{
			var insertUpdate = jsonrpc.processes.saveUploadedFileCHKList(jsData);
			alert(insertUpdate);
		}
		
	}
   
}
//[00055] End
function takeAction()
{
	
	insertSelectedFileCHKList();	
	window.close();
    callerWindowObj.submitParent();
    return false;
			
}



</script>
</head>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload="setformValue()">
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<html:form action="/viewOrderAction" enctype="text/plain">
<div>

<html:hidden property="searchTaskId" />
<html:hidden property="searchTaskOwner" />
<div class="head"> <span>Document Tracking </span></DIV>
		<fieldset class="border1">
			<legend> Document Tracking</legend>
			<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
				
				<tr class="lightBg">
					<table width="100%"  border="1" align="center" id = "fileCheckListTable" cellpadding="0" cellspacing="0" class="tab2" >
					</table>
				</tr>
				<tr class="lightBg">
					<td colspan="2" align="center"> <div class="searchBg1"><a href="#" onClick="return takeAction()">Submit</a></div> 	</td>
				
				</tr>
				
			</table>
		</fieldset>
	</div>
</html:form>
</body>
</html>
		