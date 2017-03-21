<!--[001]	 LAWKUSH 		06-MAY-11	00-05422		Setting Paging ans sorting -->
<!--[002]    NANCY          01-JUL-16   20160301-XX-022139  Saving ePCN NO. On Opportunity Dialog Box-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Select Opportunity</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script language="javascript" type="text/javascript">

//[002] start
function callDisplayEpcn()
{
	var orderNo = <%=request.getParameter("orderNo")%>;
	var ePCNTextBox = document.getElementById('ePCNId');
	//ePCNTextBox.value='';

	var getEpcn=  jsonrpc.processes.getEpcnSaved(orderNo);
	if((getEpcn==null))
		{
	ePCNTextBox.value= '' ;
		}
	else
		ePCNTextBox.value= getEpcn ;
}
//[002] end
function selectRows(oppId,quoteNo,index,salesforceOpportunityNo) 
{
	if(document.getElementById('chk'+index).checked == true) 	
	{		
		var mytable = document.getElementById('taboppDetails');	
		var iCountRows = mytable.rows.length;
		if(iCountRows > 5){
		   	alert('Cannot add more than 5 rows.');
		 	document.getElementById('chk'+index).checked = false;
		  	return false;
	   	}		
		for(var i=1; i<iCountRows; i++){				
			var rows = mytable.rows[i];	
			var cellValue = rows.cells[1].innerHTML;	
			if(cellValue == oppId){
				alert('Cannot duplicate OpportunityId');
				document.getElementById('chk'+index).checked = false;
				return false;
			}					
		}
		displayOppRows(oppId,quoteNo,salesforceOpportunityNo,iCountRows-1);			
	}		
		
}

function callDisplayOppRows(){
	var orderNo = <%=request.getParameter("orderNo")%>;
	var mytable = document.getElementById('taboppDetails');	
	var iCountRows = mytable.rows.length;
	
	for(var i=1; i<iCountRows; i++){		
		mytable.deleteRow(1);
		
	}
	var lstSavedOpportunity = jsonrpc.processes.getOpportunitySaved(orderNo);
	var v_iTotalLength=lstSavedOpportunity.list.length;	
	for (var si = 0; si <  v_iTotalLength; si++)
	{		 	 
	    var oppIdSaved=lstSavedOpportunity.list[si].opportunityId;
	    var quoteNoSaved=lstSavedOpportunity.list[si].quoteNo;	  
	    var salesforceOpportunityNoSaved=lstSavedOpportunity.list[si].salesForceopportunityNo;	  
		displayOppRows(oppIdSaved,quoteNoSaved,salesforceOpportunityNoSaved,si)
	}
}

function displayOppRows(oppId,quoteNo,salesforceOpportunityNoSaved,index){
	
		var colorValue=parseInt(index)%2;
		var colors=new Array("normal","lightBg");
		
		var str;	  	 		 
		var mytable = document.getElementById('taboppDetails');	
	 	var iCountRows = mytable.rows.length;		 	
			var tblRow=document.getElementById('taboppDetails').insertRow();			
			tblRow.className=colors[colorValue];
			if(salesforceOpportunityNoSaved==null)
				salesforceOpportunityNoSaved=' -';				
				
			
			
			
			var tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=index+1;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str=oppId;
			CellContents = str;
			tblcol.innerHTML = CellContents;
			
			tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str=quoteNo;
			CellContents = str;
			tblcol.innerHTML = CellContents;	
			
			tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			str=salesforceOpportunityNoSaved;
			CellContents = str;
			tblcol.innerHTML = CellContents;						
			
			var tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";			
			str="<input name=delSelect"+iCountRows+" id=delSelect"+iCountRows+" type=checkbox onclick=delSelectCheck("+iCountRows+"); />";
			CellContents = str;
			tblcol.innerHTML = CellContents;
}
function deleteRow(index){
	alert('to delete this function');
	var mytable = document.getElementById('taboppDetails');
	mytable.deleteRow(index);	
}

function DrawOpportunityType()
{		
	
	   var orderNo = <%=request.getParameter("orderNo")%> ;
	   var accountId = <%=request.getParameter("accountID")%> ;
	  
	   var str;
	   var frm=document.getElementById('opportunityTypes');
	  
	   var mytable = document.getElementById('tabopportunityType');	
	   var iCountRows = mytable.rows.length;
  
	   for (i = 1; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(1);
	   } 
	  
	   document.getElementById('txtPageNumber').value= pageNumber;
	   sync();	
	   var jsData={				
				opportunityId:document.getElementById('opportunityId').value,
				quoteNo:document.getElementById('quoteNo').value,	
				salesForceopportunityNo:document.getElementById('salesForceopportunityNo').value,			
				accountId:accountId		
			};
	   var jsDataPage =
			{
				startIndex:startRecordId,
				endIndex:endRecordId,
				sortBycolumn:sortByColumn,
				sortByOrder:sortByOrder				
			};
				  
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	
	//var lstOpportunity = jsonrpc.processes.opportunityType(jsData,jsDataPage);
	///temp
	var returnOpportunity = jsonrpc.processes.opportunityType(jsData,jsDataPage);
	
	var lstOpportunity = returnOpportunity.opportunityDto;
	
	var lstPageFormat = returnOpportunity.pagingCommonDto;
	///temp end
	var v_iTotalLength=lstOpportunity.list.length;	
	if(v_iTotalLength !=0)
	{
		var v_iNoOfPages = lstPageFormat.maxPageNumber;
		iNoOfPages=v_iNoOfPages;		
	}
	 else
	{     
	    v_iNoOfPages=1;
	}
	
	
	document.getElementById('txtMaxPageNo').value=v_iNoOfPages;				
	var colors=new Array("normal","lightBg");	
	var txtColorArray = new Array("#FEFEFE","#F7F7E7");
	
	var counter = <%=request.getAttribute("count")%>;
	  
	 for (i = 0; i <  v_iTotalLength; i++)
	 {
	 	var colorValue=parseInt(i)%2;
	    var opportunityId=lstOpportunity.list[i].opportunityId;
	    var quoteNo=lstOpportunity.list[i].quoteNo;	
	    var salesForceopportunityNo=lstOpportunity.list[i].salesForceopportunityNo;	   
	   // alert('salesForceopportunityNo salesForceopportunityNo '+salesForceopportunityNo)
	    if (salesForceopportunityNo==null)
	    	salesForceopportunityNo=' -';
	    
		var tblRow=document.getElementById('tabopportunityType').insertRow();
		tblRow.className=colors[colorValue];
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<input name=chk"+i+" id=chk"+i+" type=checkbox onclick=selectRows('"+ escape(lstOpportunity.list[i].opportunityId) +"','"+ escape(lstOpportunity.list[i].quoteNo)+"','"+i+"','"+ escape(salesForceopportunityNo)+"') />";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=lstOpportunity.list[i].opportunityId;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=lstOpportunity.list[i].quoteNo;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
			
			
		
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";		
		str = "<input type='text' tabindex='-1' style='border: 0px solid;background:'"+ txtColorArray[colorValue] +"' value ='"+ salesForceopportunityNo +"' readonly />";		
		CellContents = str;
		tblcol.innerHTML = CellContents;
		/*
		tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str="";
		CellContents = str;
		tblcol.innerHTML = CellContents;				
		*/
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
	
	if(v_iTotalLength== 0)
	{
	var tblRow=document.getElementById('tabopportunityType').insertRow();
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
//
function clearFields()
{
	document.getElementById('opportunityId').value="";
	document.getElementById('quoteNo').value="";
	document.getElementById('salesforceOpportunityNo').value="";
}

function saveSelected(){
	var mytable = document.getElementById('taboppDetails');	
	var func = "save"; //[002]
	var orderNo = <%=request.getParameter("orderNo")%> ;
	var oppId = "";
	var sfOpportId = "";
	var iCountRows = mytable.rows.length;
	for(var i=1; i<iCountRows; i++){				
		var rows = mytable.rows[i];	
		var cellValue = rows.cells[1].innerHTML;	
		var cellValueSFOpportId = rows.cells[3].innerHTML;	
		if(oppId != "")
			oppId = oppId +","+cellValue;
		else
			oppId = cellValue;
			
		if(sfOpportId != "")
			sfOpportId = sfOpportId +","+cellValueSFOpportId;
		else
			sfOpportId = cellValueSFOpportId;	
	}
	var ePCN= document.getElementById("ePCNId").value; //[002]
	var successMsg=jsonrpc.processes.saveMultipleOpportunity(orderNo, oppId, sfOpportId,ePCN,func);
	if(successMsg==0){
		if(iCountRows<2){
			alert("ePCN Updated Successfully!!"); //[002]
			alert("No Rows to save for Opportunity");	
		}
		else{
		      alert('Saved successfully!!');
			}
		var callerWindowObj = dialogArguments;
		callerWindowObj.document.searchForm.sourceName.value = 'CRMQUOTE';
	}
	else
	{
		alert('save failed!!');
	}
	
}
function deleteSelected(){	
	var mytable = document.getElementById('taboppDetails');	
	var orderNo = <%=request.getParameter("orderNo")%>
	var iCountRows = mytable.rows.length;
	var oppId = "";
	var sfOppId = "";
	var func="delete" ;
	var flag = false;
	for(var i=1; i<iCountRows; i++){
		if(document.getElementById('delSelect'+i).checked == false){				
			var rows = mytable.rows[i];	
			var cellValue = rows.cells[1].innerHTML;
			var cellValueSfOpportunityId = rows.cells[3].innerHTML;	
			if(oppId != "")
				oppId = oppId +","+cellValue;
			else
				oppId = cellValue;
				
			if(sfOppId != "")
				sfOppId = sfOppId +","+cellValueSfOpportunityId;
			else
				sfOppId = cellValueSfOpportunityId;
					
		}else{
			flag = true;
		}
	}
	if(flag == false){
		alert('Please select rows to Delete.');
		return false;
	}
	
	if(confirm('Are you Sure?'))
	{
		//var successMsg=jsonrpc.processes.saveMultipleOpportunity(orderNo, oppId, sfOppId);
          var ePCNNO= document.getElementById('ePCNId').value;
		  var successMsg=jsonrpc.processes.saveMultipleOpportunity(orderNo, oppId, sfOppId,ePCNNO,func);
		if(successMsg==0)
		 {
			alert('Deleted successfully!!');
			callDisplayOppRows();
			if(oppId == ""){
				var callerWindowObj = dialogArguments;
				callerWindowObj.document.searchForm.sourceName.value = 'CRMORDER';
		 }
		}
		else
		{
			alert('Deletion failed!!');
		}	
	}
}
function closeSelected(){
	if(confirm('This will cause losing any unsaved data!\nPress OK to continue, or Cancel to stay on the current page.'))
	{
		window.close();
	}
}
function selectAll(){
	var mytable = document.getElementById('taboppDetails');	
	var iCountRows = mytable.rows.length;
	var flag = document.getElementById('selectAllID').checked;	
	for(var i=1; i< iCountRows; i++){		
		if(document.getElementById('delSelect'+i)){					
			if(flag == true)
				document.getElementById('delSelect'+i).checked=true;
			else
				document.getElementById('delSelect'+i).checked=false;		
		}
	}
}
function delSelectCheck(counter){
	if(document.getElementById('delSelect'+counter).checked == false)
			document.getElementById('selectAllID').checked = false;
}
var path='<%=request.getContextPath()%>';
function isBlankFields()
{
	//accountValidation
		var searchFlag=1;
		var opportunityId=document.getElementById('opportunityId').value;
		var quoteNo=document.getElementById('quoteNo').value;
		var sfOpportunityNo=document.getElementById('salesforceOpportunityNo').value;
		
		/*
		if(!trim(opportunityId).length>0 && !trim(quoteNo).length>0){
			alert('Please enter atleast one search creteria.');
			return false;		
		}
		*/		
		
		if( trim(sfOpportunityNo).length>0)
		{
			if(accountValidation(document.getElementById('salesforceOpportunityNo'),'Salesforce Number')==false)
			{
				searchFlag=0;
				return false;
			}
		}
		
		if( trim(opportunityId).length>0)
		{
			if(accountValidation(document.getElementById('opportunityId'),'Opportunity Id')==false)
			{
				searchFlag=0;
				return false;
			}
		}
		
		if( trim(quoteNo).length>0)
		{
			if(accountValidation(document.getElementById('quoteNo'),'Quote Number')==false)
			{
				searchFlag=0;
				return false;
			}
		}
		
		if(searchFlag==1)
		{
			searchFromList('OPPORTUNITY','SELECTOPPORTUNITY');
		}
		
}
	function setValue()   //called this function on onload 
{
	
	var callerWindowObj = dialogArguments;
	
}
var hook=true;
window.onbeforeunload = function() 
{
/*    if(isView == null)
   	{
    	callerWindowObj.ServiceTreeViewAfterDisconnection(serviceNo);
  	}	*/
    if (hook) 
    if ((window.event.clientX < 0) || (window.event.clientY < 0) || (window.event.clientX < -80))
    {
    {
      return "This will cause losing any unsaved data ! "
    } 
    }   
}
function unhook() 
{
     hook=false;
}
//
</script>
</head>
<body>

<center>
<html:form action="/NewOrderAction" styleId="opportunityTypes" method="post">
<!--  [002] start-->
<br/>
<table width="100%" border="1"  id="tabePCNNo." align="center" cellpadding="0" cellspacing="0" class="tab2" >		
		<tr class="head">
			<th colspan="2"> EPCN NO.</th>
		</tr>
</table>
<br/>		
<fieldset class="border1">
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
		<tr class="normal"> 
			<td align="center" colspan="6">
				<div style="float:left;"><strong>ePCN No : </strong><input type="text" maxlength="50" id= "ePCNId" name="ePCNId"  onblur="if(trim(this.value).length>0){return ValidateTextField(this,path,'ePCN No')};" ondrop="return false;"
			        onpaste="return false;"  />
				</div></td>
		</tr>
	</table>
</fieldset>
<br/>
<table width="100%" border="1"  id="tabePCNNo." align="center" cellpadding="0" cellspacing="0" class="tab2" >		
		<tr class="head">
			<th colspan="2">SELECT OPPORTUNITY</th>
		</tr>
</table>
<br/>
<!-- [002] end-->
<fieldset class="border1">
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
		<tr>
			<td align="center"><a href="#" id= "first" onclick="FirstPage('OPPORTUNITY','SELECTOPPORTUNITY')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('OPPORTUNITY','SELECTOPPORTUNITY')">Next</a></td>
			<td align="center">
				<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
				<input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
			<td align="center"><a href="#" id="prev" onclick="PrevPage('OPPORTUNITY','SELECTOPPORTUNITY')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('OPPORTUNITY','SELECTOPPORTUNITY')">Last</a></td>
		</tr>
	</table>			
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="6">
					<div style="float:left;"><strong>Opportunity:</strong><input type="text" maxlength="25" id="opportunityId" name="opportunityId" class="inputBorder1" onkeydown="if (event.keyCode == 13)  return isBlankFields();"   >
					</div></td>
					<td  align="center" colspan="6">
					<div style="float:left;"><strong>Quote Number:</strong><input type="text" maxlength="10" id="quoteNo" name="quoteNo" class="inputBorder1" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
					</div></td>
					<td  align="center" colspan="6">
					<div style="float:left;"><strong>SF Opportunity No:</strong><input type="text" maxlength="20" id="salesforceOpportunityNo" name="salesforceOpportunityNo" class="inputBorder1" onkeydown="if (event.keyCode == 13)  return isBlankFields();"  >
					</div></td>						
					<td>
					<td  align="center" colspan="o">
					&nbsp;</td>					
					<td>
					<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
					<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Search Criteria" width="24" height="18" onclick="clearFields();"/>&nbsp;</td>
				</tr>
	</table>
			<table width="100%"  border="1" id="tabopportunityType" align="center" cellpadding="0" cellspacing="0" class="tab2" >			   
				<tr class="grayBg">
				    <td width="5%" align="center">Select</td>
					<td width="40%" align="left">
						<a href="#" onclick="sortOrder('OPPORTUNITY','SELECTOPPORTUNITY')">Opportunity No</a>				
					</td>
					<td width="40%" align="left">
						<a href="#" onclick="sortOrder('QUOTENO','SELECTOPPORTUNITY')">Quote No</a>					
					</td>	
					<td width="40%" align="left">
						<a href="#" onclick="sortOrder('SF_OPPORTUNITY_ID','SELECTOPPORTUNITY')">SF Opportunity No.</a>					
					</td>									
				</tr>
			</table>	
</fieldset>
<br>
<fieldset class="border1">
	
	<!--  <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<td width="20%" align="center"><input name="saveRows"
				id="saveRows" onClick="saveSelected();" type="button" value="Save Rows"></td>
			<td width="20%" align="center"><input name="deleteRows"
				id="deleteRows" onClick="deleteSelected();" type="button" value="Delete Rows"></td>
			<td width="20%" align="center"><input name="closeRows"
				id="closeRows" onClick="closeSelected();" type="button" value="Close"></td>
		</tr>
	</table>	-->
			<table width="100%"  border="1" id="taboppDetails" align="center" cellpadding="0" cellspacing="0" class="tab2" >			   
				<tr class="grayBg">
				    <td width="5%" align="center">S No.</td>
					<td width="40%" align="left">
						Opportunity No			
					</td>
					<td width="40%" align="left">
						Quote No				
					</td>
					<td width="40%" align="left">
						SF Opportunity No				
					</td>					
					<td width="20%" align="center">
						<input type="checkbox" id="selectAllID" name="selectAllID"  onclick="selectAll()">		
					</td>					
				</tr>
			</table>	
			<br/>
			<br/>
			<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<td width="20%" align="center"><input name="deleteRows"
				id="deleteRows" onClick="deleteSelected();" type="button" value="Delete Rows"></td>
			<td width="20%" align="center"><input name="closeRows"
				id="closeRows" onClick="closeSelected();" type="button" value="Close"></td>
		</tr>
		
	</table>
	<br/>	
	<center><div width=20%><input name="saveRows"
				id="saveRows" onClick="saveSelected();" type="button" value="Save Rows/ePCN"></div></center>
	
</fieldset>		
</html:form>
</center>
</body>
<script type="text/javascript">
	//DrawOpportunityType()
	//setValue()	
	DrawTable('OPPORTUNITY','SELECTOPPORTUNITY');
	callDisplayOppRows();
	// nancy
	callDisplayEpcn(); 
</script>
</html>


	
