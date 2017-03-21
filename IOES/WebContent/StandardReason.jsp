<!--Tag Name Resource Name  Date		CSR No					Description -->
<!--[001]	ANIL KUMAR	14-May-15		20141219_R2_020936		Fetch Standard reason, Insert Standard reason and Update Standard reason-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Standard Reason</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
var path='<%=request.getContextPath()%>';
var sessionid ='<%=request.getSession().getId() %>';

function goToHome()
{
	var myForm=document.getElementById('viewStandardReasonFormId');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	myForm.submit();
}

function addNewStandardReason(){
	var stdReasonName,stdReasonStatus,stdReasonInterfaceType;
	stdReasonName=document.getElementById('txtStdReasonName');;
	stdReasonStatus=document.getElementById('ddstdReasonStatus');
	stdReasonInterfaceType=document.getElementById('ddstdReasonInterfaceType');
	
	
	if(stdReasonName.value==""||stdReasonName.value==null){
		alert("Please fill standard reason name");
		return false;
	}
	if(stdReasonStatus.selectedIndex==0){
		alert("Please select status");
		return false;
	}
	if(stdReasonInterfaceType.selectedIndex==0){
		alert("Please select interface type");
		return false;
	}
	if(stdReasonName.value.length > 0){
		if(ValidateTextField(stdReasonName,path,"Standard Reason name")==false){			
			return false;
		}	 
	}
	
	
	var jsData =
	{
		stdReasonName:stdReasonName.value,
		stdReasonStatus:stdReasonStatus.value,
		interfaceType:stdReasonInterfaceType.value
	};
				
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");						
	var status = jsonrpc.processes.insertNewStandardReason(jsData,sessionid);
	
	if(status=="-2"){
		alert("Duplicate Standard Reason Name found.\nPlease enter unique Standard Reason Name !!");
		stdReasonName.focus();
		return false;
	}
	if(status=="-1"){
		alert("New Standard Reason insertion failed");	
		return false;		
	}
	if(status=="1"){
		alert("Standard Reason inserted sucessfully !!");
		clear();		
		DrawTable('REASONNAME','STANDARDREASON');		
	}else{
		alert(status);
		return false;
	}
	
}
function clear(){
	var stdReasonName,stdReasonStatus,stdReasonInterfaceType;
	stdReasonName=document.getElementById('txtStdReasonName');
	stdReasonStatus=document.getElementById('ddstdReasonStatus');
	stdReasonInterfaceType=document.getElementById('ddstdReasonInterfaceType');
	
	stdReasonName.value="";
	stdReasonStatus.value=-1;
	stdReasonInterfaceType.value=-1;
}

function DrawStandardReasonListTable()
{
	var lstSTDReasonDetails;	
	   try
	   {
		   var str;		   	  
		   var mytable = document.getElementById('tabViewStandardReason');	
		   var iCountRows = mytable.rows.length;
		   
		   for (i = 1; i <  iCountRows; i++)
		   {
		       mytable.deleteRow(1);
		   }   	
		    
		   document.getElementById('txtPageNumber').value= pageNumber;
		   document.getElementById('id_paging_goToPage').value= pageNumber; 
		   sync();	
		      	   	   
		   	var pagingDto=
				{
					startIndex:startRecordId,
					endIndex:endRecordId,
					sortBycolumn:sortByColumn,
					sortByOrder:sortByOrder
				};	   
		   var jsData =
				{
					pagingDto:pagingDto
				};
				
			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");						
			lstSTDReasonDetails = jsonrpc.processes.fetchStandardReason(jsData);							
			
			if(lstSTDReasonDetails==null)
			{
				var tblRow=document.getElementById('tabViewStandardReason').insertRow();
			    tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				tblcol.colSpan = 12;
				str='NO RECORD FOUND';
				CellContents = str;
				tblcol.innerHTML = CellContents;
			}
		
			iTotalLength=lstSTDReasonDetails.list.length;	
			if(iTotalLength !=0)
			{
				iNoOfPages = lstSTDReasonDetails.list[0].maxPageNo;
			}		
	        else
			{     
		        iNoOfPages=1;
			}
		
			document.getElementById('txtMaxPageNo').value=iNoOfPages;				
			var colors=new Array("normal","lightBg");
			var i;
			for (i = 0 ; i <iTotalLength; i++) 
			{		 		
				
		 		var colorValue=parseInt(i)%2;	 		  
				var tblRow=document.getElementById('tabViewStandardReason').insertRow();
				tblRow.className=colors[colorValue];							
			
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=(i+1);
				str=str+"<input type='hidden' name='hdnStdReasonId' value='"+lstSTDReasonDetails.list[i].stdReasonId+"'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
			
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=lstSTDReasonDetails.list[i].stdReasonName;
				CellContents = str;
				tblcol.innerHTML = CellContents;
			
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str="<select name='ddstdReasStatus' id='ddstdReasStatus"+i+"' class='dropdownMargin' style='width:80%;'><option value='-1' >Select status</option><option value='1'>Active</option><option value='0'>Inactive</option></select>";				
				str=str+"<input type='hidden'  id='hdnStdReasonStatus"+i+"'  value='"+lstSTDReasonDetails.list[i].stdReasonStatus+"'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
			
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				
				if(lstSTDReasonDetails.list[i].interfaceType==1){
					str="Non Taxable Reason";
				}else if(lstSTDReasonDetails.list[i].interfaceType==2){
					str="LSI Change Reason";
				}
				
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str="<input type='checkbox' name='chkStdReason' id='chkStdReason"+i+"' value='"+ lstSTDReasonDetails.list[i].stdReasonId +"'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				document.getElementById('ddstdReasStatus'+i).value = lstSTDReasonDetails.list[i].stdReasonStatus;
																			
		}
					
		var pagenumber=document.getElementById('txtPageNumber').value;
		var MaxPageNo=document.getElementById('txtMaxPageNo').value;
		if((pagenumber == 1) && (MaxPageNo==1))
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
			var tblRow=document.getElementById('tabViewStandardReason').insertRow();
		    tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			tblcol.colSpan = 12;
			str='NO RECORD FOUND';
			CellContents = str;
			tblcol.innerHTML = CellContents;
		}	
		return false;
	}
	catch(e)
	{
		alert(e);		
	}
}
function selectAll()
{
	if(document.getElementById('selectAllID').checked)
	{
		CheckAll(true);
	}else{
		CheckAll(false);
	}
}

function CheckAll(chkbox)
{
	var i=0;
	while(eval(document.getElementById('chkStdReason'+i)))
	{
			if(document.getElementById('chkStdReason'+i).disabled==false)
			{
				eval(document.getElementById('chkStdReason'+i)).checked=chkbox;
			}	
		i=i+1;
	}
}

function fnUpdateStandardReason()
{
	var myForm=document.getElementById('viewStandardReasonFormId');
	var str="",strStatus="";
	var checkedCounter1=0;
	var isStatusSelected=true;
	var isStatusUpdate=true;	
	var strSrNo="";
	var statusOldValue,statusNewValue;	
	if(document.forms[0].chkStdReason)
	{
   		var chkLength=document.forms[0].chkStdReason.length;
   		if(chkLength==undefined)
		{
			if(document.forms[0].chkStdReason.checked==true)
   			{
   				checkedCounter1++;   	   								
				statusOldValue=document.getElementById('hdnStdReasonStatus'+0).value;
				statusNewValue=document.getElementById('ddstdReasStatus'+0).value;
					if(statusOldValue==statusNewValue){
						isStatusUpdate=false;
						strSrNo=strSrNo+"S. No:"+(0+1)+"\n";					
					}				   			
	   			if(str=="")
		     	{
		     		str=document.forms[0].chkStdReason.value;
		     		strStatus=document.getElementById('ddstdReasStatus'+0).value;
		     	}
	     	    else
		     	{
		     		str=str + "," +document.forms[0].chkStdReason.value;
		     		strStatus=strStatus+","+document.getElementById('ddstdReasStatus'+0).value;
		     	}       			
		     	if(document.getElementById('ddstdReasStatus'+0).value==-1){					
						isStatusSelected=false;
				}
       		}
     	}
		else
		{
			var i;
			for(i=0;i<document.forms[0].chkStdReason.length;i++)
			{
				
				if(myForm.chkStdReason[i].checked==true)
		     	{		     				     		
					checkedCounter1++;					
					statusOldValue=document.getElementById('hdnStdReasonStatus'+i).value;
					statusNewValue=document.getElementById('ddstdReasStatus'+i).value;
					if(statusOldValue==statusNewValue){
						isStatusUpdate=false;
						strSrNo=strSrNo+" S. No: "+(i+1)+"\n";					
					}	     		
		     	    if(str=="")
		     	   	{
						str=myForm.chkStdReason[i].value;
						strStatus=document.getElementById('ddstdReasStatus'+i).value;
		    		}
		     	    else
		     	    {
		     	    	str=str + "," +myForm.chkStdReason[i].value;
		     	    	strStatus=strStatus+","+document.getElementById('ddstdReasStatus'+i).value;
		     	    }
		     	    if(document.getElementById('ddstdReasStatus'+i).value==-1){
					
						isStatusSelected=false;
						break;
					}
		     	}
		     }	
		}
		if(str!="")
		{											
				if(isStatusSelected==false){
					alert("Please select status");
					return false;
				}	
				if(isStatusUpdate==false){
					
					alert("Following S.No(s) are having no changes.Please unselect them before continuing ...\n"+strSrNo);
					return false;
				}
				var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");	
				var sessionid ='<%=request.getSession().getId() %>';		
				var status = jsonrpc.processes.editStandardReason(str,strStatus,sessionid);	
				if(status==-1){
					alert("Standard reason updation failed !!");
					return false;
				}					 	 				 
				if(status==1){
					alert("Standard reason status updated sucessfully !!");
					goToPageSubmit('REASONNAME','STANDARDREASON');
				}
		}
		if(checkedCounter1==0)
		{
			alert("Please Select standard reason!!");
			return false;
		}
	}	
 	else
 	{
 		if (document.forms[0].chkStdReason == null)
 		{
 			alert("No standard reason present !!");
			return false;
 		}		
	}	
}

</script>
</head>
<body>
<div class="head"> <span>Standard Reason Master</span></div>

<html:form action="/defaultDraftNewOrdAction" styleId="viewStandardReasonFormId" method="post">
<table width="100%"  border="0"  cellpadding="0" cellspacing="0">	
		<tr>	
			<td width="10%" align="right" vAlign="top">
				<img src="gifs/top/home.gif" title="Home" onClick="goToHome();"></img>
			</td>			
		</tr>
</table>
<table width="60%" border="1"  id="tabNewStandardReason" align="center" cellpadding="0" cellspacing="0" class="tab2" >		
			<tr class="head">
				<th colspan="2">Add New Standard Reason</th>
			</tr>					
			<tr>
				<td width="20%">
					Standard Reason name
				</td>
				<td>
					<input type="text" id="txtStdReasonName" name="reasonName" class="inputBorder1" maxlength="255" style="width:30%">
				</td>
			</tr>
			<tr>
				<td width="20%">
					Status
				</td>
				<td>
					<select style="width:18%" name='ddstdReasonStatus' id='ddstdReasonStatus' class='dropdownMargin'>
						<option value='-1'>Select Status</option>
						<option value='1'>Active</option>
						<option value='0'>Inactive</option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="20%">
					Interface Type
				</td>
				<td>
					<select style="width:25%" name='ddstdReasonInterfaceType' id='ddstdReasonInterfaceType' class='dropdownMargin'>
						<option value='-1'>Select Interface</option>
						<option value='2'>LSI Change Reason</option>
						<option value='1'>Non Taxable Reason</option>
					</select>
				</td>
			</tr>
			<tr>				
				<td colspan="2"  align="center">
					<input type="button" name="btnAddStandardReason" value="Add New Standard Reason" width="5" onclick="addNewStandardReason()">
				</td>
			</tr>					
	</table>
<br/>
<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >	
	<tr class="head">
		<th colspan="4">Edit Standard Reason</th>
	</tr>	
	<tr>
		<td align="center"><a href="#" id= "first" onclick="FirstPage('REASONNAME','STANDARDREASON')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('REASONNAME','STANDARDREASON')">Next</a></td>
		<td align="center"  class="last">Go To Page :
			<input type="text" class="inputNew" name="goToPageNumber" size="4" 
				maxlength="10" value="" id="id_paging_goToPage">
				<a href="#" onclick="goToPageSubmit('REASONNAME','STANDARDREASON')">GO </a>
		</td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo" class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" id="prev" onclick="PrevPage('REASONNAME','STANDARDREASON')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('REASONNAME','STANDARDREASON')">Last</a></td>
	</tr>
	</table>	   		   
	<table width="100%" border="1"  id="tabViewStandardReason" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="5%%" align="center">
						S.No
				</td>
				<td width="20%" align="center">
					Standard Reason name
				</td>
				<td width="7%" align="center">
					Status
				</td>
				<td width="20%" align="center">
					Interface Type
				</td>
				<td width="5%" align="center">
					Select
					<input type="checkbox" id="selectAllID" name="selectAllID"  onclick="selectAll()">
				</td>								
			</tr>											
	</table>
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<tr>				
				<td colspan="5" align="center">
					<input type="button" name="btnEditStandardReason" value="Update Standard Reason" onclick="fnUpdateStandardReason()">
				</td>
			</tr>				
		</tr>
	</table>	
</html:form>
</body>
<script type="text/javascript">
	DrawTable('REASONNAME','STANDARDREASON');
</script>
</html>