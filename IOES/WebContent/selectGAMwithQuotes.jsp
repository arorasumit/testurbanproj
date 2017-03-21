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
<title>Select Global Account Manager (GAM)</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
var isValidOrder=0;
var noOfCount=0;

function openGAMListWithQuotes()
{	
	var quoteno=document.getElementById('hdnQuoteNo').value;	
	if(document.getElementById('isQuoteList').checked==true)
	{
		DrawAllGAMList(quoteno);
	}else{
		DrawAllGAMList('0');
	}	
}

function DrawAllGAMList(QuoteNo)
{		
	   var str;		   	    	      
	   var frm=document.getElementById('selectGAM');       
		var accno=document.getElementById('hdnAccountID').value;
		var orderno=document.getElementById('hdnOrderNo').value;
			    	    
	   	var mytable = document.getElementById('SelecttabGAMList');	
	   	var iCountRows = mytable.rows.length;
  
	   	for (i = 1; i <  iCountRows; i++)
	   	{
	       mytable.deleteRow(1);
	   	} 
									
		try{
		   var jsData =
				{	
					startIndex:startRecordId,
					endIndex:endRecordId,
					sortBycolumn:sortByColumn,
					sortByOrder:sortByOrder,									
					quoteNo:QuoteNo,
					orderNumber:orderno	,
					gam_name:document.getElementById('txtGamID').value							
				};		  
			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			var GAMList = jsonrpc.processes.getGAMListAll(jsData);
			}
			catch(e)
			{
				alert("Exception :"+e);
			}
			
			iTotalLength=GAMList.list.length;
							
		var colors=new Array("normal","lightBg");		
	 
	 //***************************************************************************************************
	 //Populating Global Account Manager List(GAM)
	 if(iTotalLength>0){
	 		for (i = 0; i <  iTotalLength; i++)
			{	
	 	
			 	var colorValue=parseInt(i)%2;
				var tblRow=document.getElementById('SelecttabGAMList').insertRow();
				tblRow.className=colors[colorValue];						
		
				var tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str="<input type='checkbox' name='chk' id='chk"+i+"' onClick=countClick(this) value='"+GAMList.list[i].gam_id+"'  /><input type='hidden' name='hdnGAMID' id='hdnGAMID_"+GAMList.list[i].gam_id+"' value='"+GAMList.list[i].gam_id+"'/>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str=GAMList.list[i].gam_name;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str=GAMList.list[i].gam_emailid;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=GAMList.list[i].gam_contact;
				CellContents = str;
				tblcol.innerHTML = CellContents;	
								
			}														
	}	
	else
	{				   	    
		var tblRow=document.getElementById('SelecttabGAMList').insertRow();
	    tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		tblcol.colSpan = 4;
		str='NO RECORD FOUND';
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}	
	return false;
}
var counter=0;
function addGAMList(gamempid,gamname,gamemailid,gamcontact)
{
	var callerWindowObj = dialogArguments;																			
	callerWindowObj.count=counter;										
	SaveGameList(gamempid);																																						
	callerWindowObj.DrawGAMList();
	window.close();
}
function SaveGameList(gamempid)
{		
		var orderno=document.getElementById('hdnOrderNo').value;
		var quoteno=document.getElementById('hdnQuoteNo').value;
		
		var sessionid ='<%=request.getSession().getId() %>';
		jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		validateOrderNo();
	//	alert(isValidOrder);
		if(isValidOrder==1){
		if((gamempid !='' && orderno !='') || (gamempid !=0 && orderno !=0) ){		
			var validationMsg=jsonrpc.processes.validateGAMBeforeSave(gamempid,orderno,sessionid);
			
		if(validationMsg.max_allowed_gam_validate==1)
		{
		alert("There can't be  more than 7 GAMs attached to an Order");
		return false;
		}
		else
		{	
			if(validationMsg.gam_validate_status==0){
				var successMsg=jsonrpc.processes.saveGamList(gamempid,orderno,quoteno,sessionid);
				if(successMsg ==0){
					alert("Successfully attached GAMs");				
				}else
				{
					alert("Err:GAM attachment failed!!");
				}
			}else if(validationMsg.gam_validate_status==1){
				alert('Already attached with this order!!');
				return false;
			}else{
				alert('Err:Error occured in Gam Attachment validation');
				return false;
			}
		}
	}	
		}else{
			alert("OrderNo is not valid for attach GAM!!");
			return false;
		}
}
function validateOrderNo()
{
	
	var orderno=document.getElementById('hdnOrderNo').value; 
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC"); 
	isValidOrder=jsonrpc.processes.isValidOrderNoForGAM(orderno);	
//	alert(isValidOrder);
}
function closeSelectGamWindow()
{
	
	window.close();
}

path='<%=request.getContextPath()%>';

function isBlankFields()
{
	var gamName=document.getElementById('txtGamID').value;
	
	
		var searchFlag=1;
	
		if( trim(document.getElementById('txtGamID').value).length>0)
		{
			if(ValidateTextField(document.getElementById('txtGamID'),path,'Gam Name')==false)
			{
			searchFlag=0;
			return false;
			}
		}
		
	
		
		if(searchFlag==1)
		{
			searchFromList('GAMNAME','SELECTGAM');
		}
		return false;
		
		
	
}
//lawkush Start


function attachGAMList()
{
 var myform=document.getElementById("selectGAM");	
 
 	var flag=false;	
	var strGAMId='';
	if(myform.chk==undefined)
	{
		alert("There is no Records!!")
		return false;
	}
	// added by lawkush
	if(myform.chk.length==undefined)
	{
		myform.chk.length=1;
	}
	
	for(i=0;i<myform.chk.length;i++)
	{	
			
			if(document.getElementById('chk'+i).checked==true)
			{	
				flag=true;				
				if(strGAMId=='')
				{	
					strGAMId=document.getElementById('chk'+i).value;									
				}else
				{	
					strGAMId=strGAMId+","+document.getElementById('chk'+i).value;									
				}				
			}			
	}
//	alert("strGAMId  "+strGAMId);
//	retun false;
	if(flag!=true)
	{
			alert("Please select atleast one GAM");
			return false;				
	}
	else
	{
	
	var callerWindowObj = dialogArguments;																			
	callerWindowObj.count=counter;										
	SaveGameList(strGAMId);																																				
	callerWindowObj.DrawGAMList();
	//callerWindowObj.DrawFormulaList()
	window.close();
	 
	}
	
	
	
}

//lawkush End 

function countClick(ctrl)
{
	
	if(ctrl.checked==true)
	{
	noOfCount++;
	}
	else
	{
	noOfCount--;
	}
	if(noOfCount>7)
	{
		ctrl.checked=false;
		noOfCount--;
		alert("You can't select more than 7 GAMS at a time");
		return false;
	}
	
}


</script>
</head>
<body> 
<html:form action="/NewOrderAction" styleId="selectGAM" method="post">
	<div class="head"> <span>Select Global Account Manager (GAM)</span> </DIV>	
		<fieldset class="border1">
			<legend> <b>List of Global Account Managers</b> </legend>
							
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">

			<td width="18%" align="left" vAlign="top" colspan="12">			
				<input type="checkbox"	id ="isQuoteList" width="10" onClick="openGAMListWithQuotes()">
				Select with Quote							
			</td>	
				
			<td  align="center" colspan="4">
					<div style="float:left;"><strong>GAM Name:</strong><input type="text" id="txtGamID" name="gamID" class="inputBorder1" maxlength="10" onkeydown="if (event.keyCode == 13)  return isBlankFields();" ></div>
					</td>
			
			<td width="18%" align="right" vAlign="top" colspan="12">			
				<input type="button"	name="btnCloseWindow" id="btnCloseWindow" value="Close Window" width="10" onClick="closeSelectGamWindow()">							
			</td>	
			
			
									
		</tr>
	</table>
								
	<table width="100%" border="1"  id="SelecttabGAMList" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="2%" align="center">Select</td>
				
				<td width="8%" align="left">
					<a href="#" onclick="sortOrder('GAMNAME','SELECTGAM')">Name</a>
				</td>
				<td width="8%" align="left">
					<a href="#" onclick="sortOrder('GAMEMAIL','SELECTGAM')">Email Id</a>
				</td>
				<td width="8%" align="center">
					<a href="#" onclick="sortOrder('GAMCONTACT','SELECTGAM')">Contact No</a>
				</td>
	<!--        <td width="8%" align="center">Name</td>
				<td width="8%" align="center">Email Id</td>
				<td width="8%" align="center">Contact No</td>					-->			
			</tr>										
	</table>
	
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<td width="18%" align="center" vAlign="top" colspan="12">
			<input type="button"	name="btnAttachGAM" id="btnAttachGAM" value="Attach GAM" width="10" onClick="attachGAMList()">				
			&nbsp;						
			</td>								
		</tr>
	</table>
	
</fieldset>
<html:hidden property="accountID" name="newOrderBean"
			styleId="hdnAccountID" />
<html:hidden property="hdnOrderNo" name="newOrderBean"
			styleId="hdnOrderNo" />
<html:hidden property="quoteNo" name="newOrderBean"
			styleId="hdnQuoteNo" />
</html:form>
</body>
<script type="text/javascript">
	//DrawAllGAMList('0');
	DrawTable('GAMNAME','SELECTGAM')
</script>
</html>


	
