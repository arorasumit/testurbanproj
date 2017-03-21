<!--Tag Name Resource Name  			Date		CSR No			Description -->
<!-- [001] 	ANIL KUMAR					26-dec-2011					Select Global Account Manager Page-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.beans.NewOrderBean"%>
<html>
<head>
<title>Global Account Managers (GAM)</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">

var count=0;
var tblViewGAMList=" ";
var maxGAM=0;
var isValidate=-1;
var totalRecords=0;
var formulaAttachStatus=0;
var attachFormulaName=" ";
var isValidOrderNo=0;
var prevObjIndex="1";
var prevClassName="normal";
var attchedFormulaID=0;
var  isInView=0;
var isFormulaAttachedFlag=0;
function DrawGAMList()
{	

	   var str="";		   	    	      	   		
		
         isInView=document.getElementById('hdnIsInView').value;
        var quote=document.getElementById('hdnQuoteNo').value;
		var accno=document.getElementById('hdnAccountID').value;
		var orderno=document.getElementById('hdnOrderNo').value;
		var stage=document.getElementById('hdnStage').value;
		document.getElementById("orderNoLbl").innerHTML=" Order No."+orderno;
	   	var mytable = document.getElementById('tabGAMList');	
	   	var iCountRows = mytable.rows.length;
    		 		
	   	for (i = 1; i <  iCountRows; i++)
	   	{
	       mytable.deleteRow(1);
	   	} 
		try{
		   var jsData =
				{										
					quoteNo:quote,
					orderNumber:orderno								
				};		  
			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			var GAMList = jsonrpc.processes.getGAMListWithQuotes(jsData);
			}
			catch(e)
			{
				alert("Exception :"+e);
			}
			iTotalLength=GAMList.list.length;
			totalRecords=iTotalLength;
		var colors=new Array("normal","lightBg");		
	 
	 //***************************************************************************************************
	 //Populating Global Account Manager List(GAM)
	 if(iTotalLength>0){
	 		for (i = 0; i <  iTotalLength; i++)
			{	
	 	
			 	var colorValue=parseInt(i)%2;
				var tblGAMRow=document.getElementById('tabGAMList').insertRow();
				tblGAMRow.className=colors[colorValue];										
				
				var tblcol=tblGAMRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str=(i+1)+"<input type='hidden' name='hdnViewGAMID' id='hdnViewGAMID"+i+"' value='"+GAMList.list[i].gam_id+"'/>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblGAMRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str=GAMList.list[i].gam_name;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblGAMRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str=GAMList.list[i].gam_emailid;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblGAMRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=GAMList.list[i].gam_contact;
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				if(isInView!=111 && stage!='Published' && stage!='Billing Trigger' && stage!='Completed')
					{
					tblcol=tblGAMRow.insertCell();
					tblcol.align = "center";
					tblcol.vAlign="top";				
					str="<input name=chk id='chk"+i+"' type='checkbox' value='"+GAMList.list[i].gam_id+"' onclick='allCheckNone()' />";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					}
				count=i;	
			}	
			if(isInView==111 || stage=='Published' || stage=='Billing Trigger' ||stage=='Completed')
			{
			
			document.getElementById('btnRemoveGamList').style.display='none';
			document.getElementById('btnAddGAM').style.display='none';
			document.getElementById('btnDetachFormula').style.display='none';
			}															
	}	
	else
	{				
		count=0;		
	}		
	maxGAM=jsonrpc.processes.getMaximumGAM();
	checkFieldLength();
	return false;	
}
function DrawFormulaList()
{		
	   	var str="";		   	    	      	   		
		checkFormulaAttachedStatus();
		 isInView=document.getElementById('hdnIsInView').value;
        var quote=document.getElementById('hdnQuoteNo').value;
		var accno=document.getElementById('hdnAccountID').value;
		var orderno=document.getElementById('hdnOrderNo').value;
		var stage=document.getElementById('hdnStage').value;
			    	    
	   	var mytable = document.getElementById('tabFormulaList');	
	   	var iCountRows = mytable.rows.length;
    		 		
	   	for (i = 1; i <  iCountRows; i++)
	   	{
	       mytable.deleteRow(1);
	   	} 
									
		try{	
					   
				jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
				var FormulaList = jsonrpc.processes.getGAMFormulaList(orderno);
			}
			catch(e)
			{
				alert("Exception :"+e);
			}
			
			iTotalLength=FormulaList.list.length;										
			var colors=new Array("normal","lightBg");		
	 
	 //***************************************************************************************************
	 //Populating Formula List
	 if(iTotalLength>0){
	 		for (i = 0; i <  iTotalLength; i++)
			{	
	 			
	 			var formulaID=0;
			 	var colorValue=parseInt(i)%2;
				var tblGAMRow=document.getElementById('tabFormulaList').insertRow();
				tblGAMRow.className=colors[colorValue];	
													
				formulaID=FormulaList.list[i].formulaId;
				if(formulaID==attchedFormulaID){
					tblGAMRow.className="rowHighlight";
				}
				var tblcol=tblGAMRow.insertCell();				
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=(i+1)+"<input type='hidden' name='hdnFormulaIdID' id='hdnFormulaIdID"+i+"' value='"+FormulaList.list[i].formulaId+"'/>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
												
				tblcol=tblGAMRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str=FormulaList.list[i].formulaName;
				CellContents = str;
				tblcol.innerHTML = CellContents;
			
				tblcol=tblGAMRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=FormulaList.list[i].baseCredit;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblGAMRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=FormulaList.list[i].kamCredit;
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblGAMRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";				
				str=FormulaList.list[i].gamCredit;
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblGAMRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";				
				str=FormulaList.list[i].creditDistribution;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				if(isInView!=111 && stage!='Published' && stage!='Billing Trigger' && stage!='Completed')
					{
					tblcol=tblGAMRow.insertCell();
					tblcol.align = "center";
					tblcol.vAlign="top";				
					str="<input name='btnAttach' id='btnAttach"+i+"' type='button' value='Attach' onclick=attachFormula("+formulaID+") />";
					CellContents = str;
					tblcol.innerHTML = CellContents;				
					}
			}																
	}			
	checkFieldLength();
	
	  isFormulaAttachedWithOrder();	 
	
	but=document.getElementById('btnGamDetails');
	if(but.value=="+")
	{
		show('tabGamList',but);
	}
				
	but=document.getElementById('btnFormulaDetails');
	if(but.value=="+")
	{
		show('tabFormulaList',but);
	}
	
	return false;	
}
function openGAMList()
{

but=document.getElementById('btnGamDetails');
	if(but.value=="+")
	{
		show('tabGamList',but);
	}
	var quote=document.getElementById('hdnQuoteNo').value;
	var accno=document.getElementById('hdnAccountID').value;
	var orderno=document.getElementById('hdnOrderNo').value;    	
	validateGAMList();
	var mytable=document.getElementById('tabGAMList');
	tblViewGAMList=mytable; 	
	validateOrderNo();		
	if(isValidate != 1){
		if(isValidOrderNo==1){
			var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=goToAttachGAM&orderNo="+orderno+"&accountNo="+accno+"&quoteNo="+quote;									
			window.showModalDialog(path,window,"status:false;dialogWidth:800px;dialogHeight:550px"); 
		}else{
			alert("OrderNo is not valid for attach GAM!!");
			document.getElementById('btnAddGAM').disabled=true;
			return false;
		}
	}
	checkFieldLength();
}

function checkFieldLength()
{
	var mytable=document.getElementById('tabGAMList');
	var totalRowLength=mytable.rows.length;
	if(totalRowLength > 1 &&  isInView!=111)
	{
		document.getElementById('btnRemoveGamList').style.display="block";
		// added by lawkush 
		document.getElementById("selectAllID").disabled=false;
	}else{
		document.getElementById('btnRemoveGamList').style.display="none";
		// added by lawkush 
		document.getElementById("selectAllID").checked=false;
		document.getElementById("selectAllID").disabled=true;
	}
	

}

function validateGAMList()
{
	var mytable = document.getElementById('tabGAMList');	
	var iCountRows = mytable.rows.length;
	
	var mytable=document.getElementById('tabGAMList');
	var totalRowLength=mytable.rows.length;
	if((totalRowLength-1)==maxGAM)
	{
		alert('Maximum '+maxGAM+' GAMs are allowed in an order');
		isValidate=1;
		return false;
	}
	else{
		isValidate=0;
	}	
	
}

function removeGAMList()
{
	var frm=document.getElementById('selectGAMForm');
	var flag=false;	
	var strGamId='';
	
	if(frm.chk==undefined)
	{
		alert("There is no Records!!")
		return false;
	}		
	if(totalRecords==1 && frm.chk0.checked==true)
	{
		strGamId=document.getElementById('chk0').value;		
		flag=true;
	}
	for(i=0;i<frm.chk.length;i++)
	{		
			if(frm.chk[i].checked==true)
			{	
				flag=true;				
				if(strGamId=='')
				{					
					strGamId=document.getElementById('chk'+i).value;									
				}else
				{					
					strGamId=strGamId+","+document.getElementById('chk'+i).value;									
				}				
			}			
	}
	if(flag!=true)
	{
			alert("Please select atleast one Global Account Manager(GAM)");
			return false;				
	}else{
		if(strGamId !='')
		{
			var sessionid ='<%=request.getSession().getId() %>';
			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			var orderno=document.getElementById('hdnOrderNo').value;
			validateOrderNo();
			if(isValidOrderNo==1){
				if(confirm('Are you Sure?'))
				{
					var successMsg=jsonrpc.processes.deleteGamList(strGamId,orderno,sessionid);
					if(successMsg==0){
						alert('GAMs deleted successfully!!');
						DrawGAMList();
					}else{
						alert('GAMs deletion failed!!');
					}
				}	
			}else{
				alert("OrderNo is not valid for remove GAM!!");
				document.getElementById('btnRemoveGamList').disabled=true;
				return false ;
			}		
		}
	}
	checkFieldLength();
}
function show(tbl,btn)  
{

	if (btn.value=="-")
	{	
		document.all.item(tbl).style.display="None";		
		btn.value="+";
		if(tbl=='tabGamList')
		{
			document.getElementById('btnRemoveGamList').style.display="none";
			document.getElementById('btnAddGAM').style.display='none';
		}
		if(tbl=='tabFormulaList')
		{
			document.getElementById('btnDetachFormula').style.display='none';
		}
		
	}
	else
	{
		document.all.item(tbl).style.display="Inline";		
		btn.value="-";
		
		if(tbl=='tabGamList')
		{
			checkFieldLength();
		}
		if(tbl=='tabFormulaList')
		{
			isFormulaAttachedWithOrder();	
		}
		var stage=document.getElementById('hdnStage').value;
		if(isInView==111 || stage=='Published' || stage=='Billing Trigger' ||stage=='Completed')
			{
			
			document.getElementById('btnRemoveGamList').style.display='none';
			document.getElementById('btnAddGAM').style.display='none';
			document.getElementById('btnDetachFormula').style.display='none';
			}
			else
			{
			document.getElementById('btnAddGAM').style.display='block';
			}
		
		
	}
}

function checkFormulaAttachedStatus()
{
	var orderno=document.getElementById('hdnOrderNo').value; 
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC"); 
	var attachStatus=jsonrpc.processes.getFormulaAttachStatus(orderno);
	formulaAttachStatus=attachStatus.attachFormulaStatus;
	attachFormulaName=attachStatus.formulaName;
	attchedFormulaID=attachStatus.formulaId;
}

function validateOrderNo()
{	
	var orderno=document.getElementById('hdnOrderNo').value; 
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC"); 
	isValidOrderNo=jsonrpc.processes.isValidOrderNoForGAM(orderno);	
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
	while(eval(document.getElementById('chk'+i)))
	{
		eval(document.getElementById('chk'+i)).checked=chkbox;
		i=i+1;
	}
}
// Added to make selectall checkbox checked or unchecked  by Lawkush Start


 function allCheckNone()
{
	
	var myForm=document.getElementById('selectGAMForm');
	var chkLength1=document.forms[0].chk.length;
	var counter=0;
	
		if(chkLength1==undefined)
		{
				if(document.getElementById('chk').checked==false)
				{
					document.getElementById("selectAllID").checked=false;
				}
				
				if(document.getElementById('chk').checked==true)
				{
					document.getElementById("selectAllID").checked=true;
				}
		}
	
	
		else
	      { 
	      	   
	         for (i =0; i<chkLength1; i++)
			   { 
			     
			     if(document.getElementById('chk'+i).checked==false)
			     
					 {
					       document.getElementById("selectAllID").checked=false ;
					 }
					 
				if(document.getElementById('chk'+i).checked==true)
			     
					 {
					       counter++;
					 }
			   if(counter==chkLength1)
			   
			   {
			   		  document.getElementById("selectAllID").checked=true ;
					          
			   }
		}
	}
}

// Added to make selectall checkbox checked or unchecked  by Lawkush End

function attachFormula(val)
{
	var orderno=document.getElementById('hdnOrderNo').value;  
	var sessionid ='<%=request.getSession().getId() %>';
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC"); 
		
	
		 	 	 	 	 	
	but=document.getElementById('btnFormulaDetails');
	if(but.value=="+")
	{
		show('tabFormulaList',but);
	}
	validateOrderNo();	
	
	if(isValidOrderNo==1){
	if((val !=-1)&&(val !=0)){
		var successMsg=jsonrpc.processes.saveGamFormula(val,orderno,sessionid);
		if(successMsg==0){
			alert('Formula attached successfully !!');		
			DrawFormulaList();
		}else if(successMsg==-2){
			alert('Err:Session expired!!');			
		}else{
			alert('Error occured in formula attachment');						
		}
	}else{
		alert('Please select formula');		
		return false;
	}
	}else{
		alert("Err:OrderNo is not valid for attach Formula!!");		
		return false;
	}		 		 	
}


function detachFormula()
{
var orderno=document.getElementById('hdnOrderNo').value;  
var sessionid ='<%=request.getSession().getId() %>';
var successMsg=jsonrpc.processes.deleteGamFormula(orderno,sessionid);
		if(successMsg==0){
			alert('Formula Detached successfully !!');	
			DrawFormulaList();
		}
		else if(successMsg==-2){
			alert('Err:Session expired!!');			
		}
		else{
			alert('Error occured in formula Detachment');						
		}
}

function isFormulaAttachedWithOrder()
{	
	var orderno=document.getElementById('hdnOrderNo').value; 
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC"); 
	isFormulaAttachedFlag=jsonrpc.processes.isFormulaAttachedWithOrder(orderno);	
	if(isFormulaAttachedFlag==1 && isInView!=111)  
			{
			document.getElementById('btnDetachFormula').style.display="block";
			}
			else
			{
			document.getElementById('btnDetachFormula').style.display="none";
			}
}


path='<%=request.getContextPath()%>';

</script>
</head>
<body> 
<html:form action="/NewOrderAction" styleId="selectGAMForm" method="post">
	<div class="head"> <span>Global Account Managers (GAM)</span> </DIV>	
	<fieldset class="border1">
		<legend><input name="btn1" id="btnGamDetails"
				onClick="show('tabGamList',this)" type="button" value="+"
				style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;
				 <b>GAM Attached to &nbsp;<label id="orderNoLbl"> Order No </label> </b> </legend>
		
			<html:hidden property="hdnOrderNo" name="newOrderBean"   
			styleId="hdnOrderNo" /> 
			
					
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<td width="18%" align="center" vAlign="top" colspan="5">			
			&nbsp;						
			</td>								
		</tr>
	</table>
	
	<table width="100%" border="1"  id="tabGAMList" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="2%" align="center">Sl.No</td>
				<td width="8%" align="center">Name</td>
				<td width="8%" align="center">Email Id</td>
				<td width="8%" align="center">Contact No</td>
				<%
				if(!request.getParameter("isInView").equalsIgnoreCase("111"))
					{
					%>
				<td width="8%" align="center">Remove
				<input type="checkbox" id="selectAllID" name="selectAllID"  onclick="selectAll()">
				<%
				}
				%>
				</td>	
			</tr>										
	</table>
	
	<table width="100%" id="tablSelectedGAMList" border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<td colspan="9" align="center">			
				<input type="button"	name="btnAddGAM" id="btnAddGAM" value="Add GAM"  onClick="openGAMList()">	
				</td>
				<td colspan="9" align="center">		
				<input type="button"	name="btnRemoveGAM" id="btnRemoveGamList" value="Remove GAM"  onClick="removeGAMList()">							
			</td>								
		</tr>
	</table>		
</fieldset>
<br>
<fieldset class="border1">
<legend> <input name="btn1" id="btnFormulaDetails"
				onClick="show('tabFormulaList',this)" type="button" value="+"
				style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;
				<b>Attach Credit Sharing Formula</b> </legend>				
	<table width="100%" border="1"  id="tabFormulaList" align="center" cellpadding="0" cellspacing="0" class="tab2" >							
			<tr class="grayBg">
				<td width="2%" align="center">Sl.No</td>
				<td width="8%" align="center">Formula Name</td>
				<td width="8%" align="center">Base Credit (%)</td>
				<td width="8%" align="center">KAM Credit (%)</td>
				<td width="8%" align="center">GAM Credit (%)</td>
				<td width="8%" align="center">Credit Distribution</td>
				<%
				if((!request.getParameter("isInView").equalsIgnoreCase("111")) && (!(request.getParameter("stage").equalsIgnoreCase("Published") ||request.getParameter("stage").equalsIgnoreCase("Billing Trigger")|| request.getParameter("stage").equalsIgnoreCase("Completed")) ))
					{
					%>
				<td width="8%" align="center">Attach Formula</td>
				<%
				}
				%>
			</tr>										
	</table>
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<td width="18%" align="center" vAlign="top" colspan="7">	
				<input type="button"	name="btnDetachFormula" id="btnDetachFormula" value="Detach Formula" width="10" onClick="detachFormula()">								
			&nbsp;						
			</td>								
		</tr>
	</table>			
</fieldset>
<html:hidden property="accountID" name="newOrderBean"
			styleId="hdnAccountID" />

<html:hidden property="quoteNo" name="newOrderBean"
			styleId="hdnQuoteNo" />
<html:hidden property="selectGAMEmpid" name="newOrderBean"
			styleId="hdnSelectedEmpid" />
<html:hidden property="isInView" name="newOrderBean"
			styleId="hdnIsInView" />
<html:hidden property="stage" name="newOrderBean"
			styleId="hdnStage" />
</html:form>
</body>
<script type="text/javascript">
	DrawGAMList();
	DrawFormulaList();
</script>
</html>


	
