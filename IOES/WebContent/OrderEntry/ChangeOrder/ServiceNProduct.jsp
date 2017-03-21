<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001] 	 Lawkush		31-05-11  	00-05422		In order to fix javascript error while viewing product catelog-->
<!-- [00044]	 Ashutosh Singh		23-June-11	CSR00-05422     Creating Change Order From Existing order in Billing Queue -->
<!--[002]	 Anil Kumar		3-Oct-11	CSR00-05422     Use Ctrl + S for Save  -->
<!--[003]	 Rohit Verma	5-Nov-11	CSR00-07843     LSI Selection on load  -->
<!--[005]	 SAURABH	    03-Feb-2013				showing Disconnect Button on ChangeOrder.jsp on saving Point No. 33 HyperCare-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>Select Service and Product</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<link href="gifs/treeview.css" type="text/css" rel="stylesheet">
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script src="js/jquery-latest.js"></script>	
<script type="text/javascript" src="js/j1.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui.min.js"></script> 
<script language="javascript" type="text/javascript">
var roleid=<%=request.getParameter("roleID")%>;
function getTip(value)
{	
	Tip("<Table border=0> "+ value +"   </table>");	
}

function getTip_DD(obj,value,objName)
{	
	var SelectedValue;
	var selIndex = value;
	var myForm=document.getElementById('productCatelog');
	SelectedValue=document.getElementById(objName).options[selIndex].text	
	var combo = document.getElementById(objName);
	combo.title = SelectedValue;
}
var callerWindowObj = dialogArguments;
//[00044] Start
//var changeTypeId = callerWindowObj.document.searchForm.HdnChangeTypeID.value;
var changeTypeId = callerWindowObj.document.searchForm.changeTypeID.value;
var subChangeTypeId = callerWindowObj.document.getElementById('hdnSubChangeTypeId').value;
var subChangeTypeName = callerWindowObj.document.getElementById('subChangeTypeName').value;
function setSubChangeType()
{	
	document.getElementById('hdnSubChangeTypeId').value=subChangeTypeId;
	document.getElementById('txtSubChangeTypeName').value=subChangeTypeName;
	document.SNPType.hdnAccountId.value = callerWindowObj.document.searchForm.accountID.value;
    document.SNPType.hdnOrderNo.value = callerWindowObj.document.searchForm.poNumber.value;
    document.getElementById('hdnChangeType').value = callerWindowObj.document.searchForm.changeTypeID.value;	
   // alert("CHANGETYPEID :"+document.getElementById('hdnChangeType').value)  ;  
	document.SNPType.hdnSubChangeType.value=callerWindowObj.document.getElementById('hdnSubChangeTypeId').value;
	/*Vijay start
	*here set the type of order for recognizing the deomo order 
	*/
	document.getElementById('hdnOrderType').value = callerWindowObj.document.searchForm.demoOrderType.value;	

	/*vijay here disable "isDemo" checkbox if order type is demo means "D"
	*/
	if(document.getElementById('hdnOrderType').value == "D"){
		document.getElementById("demoCheck").disabled  = true;;
	}
	//Vijay end
		
		
	
	
}
	//Shubhranshu
	function updateEffectiveDateLabelForRROrders()
	{
		var datelabel=$('#myTable tr:first').find("td").eq(0) ; // ServiceNproduct.jsp
			if(changeTypeId==5)
				{
					datelabel.text("RR Effective Date "); 
	    				}
					else
				  {	
		      	datelabel.text(" Effective Date "); 
		}
	}
	//Shubhranshu
function populateReasonForChange()
{	
	var tr, td, i, j, oneRecord;
    var test;
    var interFaceStdReason=2;
    //
    var subChangeTypeId = callerWindowObj.document.getElementById('hdnSubChangeTypeId').value;
    var accNo=callerWindowObj.document.getElementById('crmAccNo').value;
    //
   var combo = document.getElementById("changeReason");	
   var iCountRows = combo.length;
   for (i = 1; i <  iCountRows; i++)
   {
       combo.remove(i);
   }
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	/* var stdReason = jsonrpc.processes.populateReasonForChange(interFaceStdReason); */
	
	var stdReason = jsonrpc.processes.populateMappedChangeReason(accNo,subChangeTypeId,interFaceStdReason);// modified by Shubhranshu
	
	    for(j=0;j<stdReason.list.length;j++)
	    {
	    	var option = document.createElement("option");	    	
	   		option.text = stdReason.list[j].reasonName;
	   		option.title = stdReason.list[j].reasonName;
			option.value = stdReason.list[j].reasonID;
			try 
			{
			combo.add(option, null); //Standard
			}
			catch(error) 
			{
				combo.add(option); // IE only
			}
	    }    
	updateEffectiveDateLabelForRROrders();	
}
//[00044] END
function getInfoAndUpdate(curID,curCode) 
{
	//var callerWindowObj = dialogArguments;
	
	callerWindowObj.document.searchForm.currencyID.value = unescape(curID);
	callerWindowObj.document.searchForm.curShortCode.value = unescape(curCode);
	window.close();
}
function getChangeSubType(){
       // var callerWindowObj = dialogArguments;
	     document.SNPType.hdnAccountId.value = callerWindowObj.document.searchForm.accountID.value;
	     document.SNPType.hdnOrderNo.value = callerWindowObj.document.searchForm.poNumber.value;
	     //Start[001]
	     document.SNPType.hdnChangeType.value = callerWindowObj.document.getElementById('HdnChangeTypeID').value;
	     //End[001]	    
	     document.SNPType.hdnSubChangeType.value=document.getElementById("txtChangeType").value;
	      
	     if (document.SNPType.hdnChangeType.value == 3 || (document.SNPType.hdnChangeType.value == 4 && document.SNPType.hdnSubChangeType.value == 12)) {
	     // document.getElementById('trShowHeader').style.display = "block";
	      
	     } else {
	    //   document.getElementById('trShowHeader').style.display = "none";
	     
	     
	     }
	     
         var tr, td, i, j, oneRecord;
	     var ChangeTypeList;
	     var combo = document.getElementById("txtChangeType");
	     	
	     var iCountRows = combo.length;
	  
	    for (i = 1; i <  iCountRows; i++)
	    {
	       combo.remove(1);
	    }
	     
	 			var changeTypeId=document.SNPType.hdnChangeType.value;
	    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    ChangeTypeList = jsonrpc.processes.populateChangeType(changeTypeId);
	    for(j=0;j<ChangeTypeList.list.length;j++)
	    {
	    	
		   	var option = document.createElement("option");
		  		option.text = ChangeTypeList.list[j].subChangeTypeName;
		  		option.title = ChangeTypeList.list[j].subChangeTypeName;
		  		option.value = ChangeTypeList.list[j].subChangeTypeId;
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
function fnViewProductCatelog(url)
{
	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=productCatelogforUpdate&"+ unescape(url) +"&viewModify=1";
	window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
}
var global_map_parent_child;
var global_map_spId_Label;
var global_map_spId_ParentId;
var treeNodes;
var level0Id;
var global_map_Link_spid;
var rateRenevalStr;
var disconnectionStr;

function getTree(parentNodeId)
{
	var getNode="";
	var getNextNode="";
	var str="";
	var i;
	var key=''+parentNodeId;
	if(level0Id==parentNodeId)
	{
		//str = "<li><span ><a >"+ global_map_spId_Label[key] + "</a></span>"	;
	}
	else
	{
		str = "<li><div id = 'chk_span"+parentNodeId +"' ><span  ><input type='checkbox' name='chk' id='chk_"+parentNodeId+"' onclick=chckUncheck('"+parentNodeId+"') value='"+parentNodeId+"' ><a title='"+ global_map_spId_Label[key] +"'  href='#' onclick=fnViewProductCatelog('"+escape(global_map_Link_spid[key])+"')>"+ global_map_spId_Label[key] + "</a><input type='hidden' name='parenrSPId' id='parenrSPId_"+parentNodeId+"' value='"+global_map_spId_ParentId[''+parentNodeId]+"'><input type='hidden' name='hdnProdName' id='hdnProdName_"+parentNodeId+"' value='"+global_map_spId_Label[key]+"'></span></div>";
		rateRenevalStr="<span id='rateRenevalDivId_"+parentNodeId +"' style='display: none'>";
		var formObj=document.getElementById('SNPType');
		rateRenevalStr=rateRenevalStr+"<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' size='15%' name='changeRate_"+parentNodeId+"' id='changeRate_"+parentNodeId+"'>&nbsp;<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text'  size='15%' name='effDate_"+parentNodeId+"' id='effDate_"+parentNodeId+"' readonly='true'><font size='1'><img src='<%=request.getContextPath()%>/images/cal.gif' onclick=\"javascript:show_calendar('forms[0].effDate_"+parentNodeId+"');\" border='0px' alt='Loading...'></font>&nbsp;<select onfocus='getTip_DD(this,this.selectedIndex,this.name)' name='stdReason_"+parentNodeId+"' id='stdReason_"+parentNodeId+"' ><option value='-1' selected='selected'>-Select-</option></select></span>";
	    str=str+rateRenevalStr;
	    
	    disconnectionStr="<span id='DisconnectionDivId_"+parentNodeId +"' style='display: none'>";
	    disconnectionStr=disconnectionStr+"<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text'  size='15%' name='effDisconnectionDate_"+parentNodeId+"' id='effDisconnectionDate_"+parentNodeId+"' readonly='true'><font size='1'><img src='<%=request.getContextPath()%>/images/cal.gif' onclick=\"javascript:show_calendar('forms[0].effDisconnectionDate_"+parentNodeId+"');\" border='0px' alt='Loading...'></font>&nbsp;<select onfocus='getTip_DD(this,this.selectedIndex,this.name)' name='stdDisconnectionReason_"+parentNodeId+"' id='stdDisconnectionReason_"+parentNodeId+"' ><option value='-1' selected='selected'>-Select-</option></select></span>";
	    str=str+disconnectionStr;
	    
	    
	}

	var tempArray=global_map_parent_child[key];
	if(tempArray!=null)
	{
		for(i=0;i<tempArray.length;i++)
		{
			var childval=tempArray[i];
			
			str=str+"<ul>"+getTree(childval)+"</ul>";
			
			
		}
	}
	str=str+"</li>";
	//alert('AT the End '+ nextNode)
	return str;
}
function chckUncheck(ServiceProductID)
{

	if(document.getElementById('chk_'+ServiceProductID).checked==true)
	{
	
		checkParentId(ServiceProductID);
	}
	else
	{
	
		unCheckParentId(ServiceProductID);
	}
}
function checkParentId(ServiceProductID)
{
	if(document.getElementById('chk_'+ServiceProductID) !=null)
	{
		document.getElementById('chk_'+ServiceProductID).checked=true;
		var key=ServiceProductID+'';
		var tempArray=global_map_parent_child[key];
		if(tempArray!=null)
		{
		for(i=0;i<tempArray.length;i++)
		{
			var childval=tempArray[i];
			document.getElementById('chk_'+childval).checked=true;
			
		}
	} 
		checkParentId(document.getElementById('parenrSPId_'+ServiceProductID).value);
	}
	
}
//Draw Solution Change by Ramana
function drawSolutionChangeTable(listServiceNProduct)
{
	for (i = 0; i <  listServiceNProduct.list.length; i++)
	 {
		var serviceProductID=listServiceNProduct.list[i].serviceProductID;
   	   	var parentServiceProductID=listServiceNProduct.list[i].parent_serviceProductID;
		
		var key=parentServiceProductID+'';
		var str;
		var tblRow=document.getElementById('tabServiceNProduct').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str = "<input type='checkbox' name='chk' id='chk_"+serviceProductID+"'  value='"+serviceProductID+"' ><a title='"+ listServiceNProduct.list[i].serviceDetDescription +"'  href='#' >"+ listServiceNProduct.list[i].serviceDetDescription + "</a><input type='hidden' name='parenrSPId' id='parenrSPId_"+serviceProductID+"' value='"+listServiceNProduct.list[i].parent_serviceProductID+"'><input type='hidden' name='hdnProdName' id='hdnProdName_"+serviceProductID+"' value='"+listServiceNProduct.list[i].serviceDetDescription+"'><input type='hidden' name='hdnServiceId' id='hdnServiceId_"+serviceProductID+"' value='"+listServiceNProduct.list[i].serviceId+"'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
	}
	
}
//Draw Disconnect Table
function drawDisconnectTable(listServiceNProduct)
{
	for (i = 0; i <  listServiceNProduct.list.length; i++)
	 {
		var serviceProductID=listServiceNProduct.list[i].serviceProductID;
   	   	var parentServiceProductID=listServiceNProduct.list[i].parent_serviceProductID;
		
		var key=parentServiceProductID+'';
		var str;
		var tblRow=document.getElementById('tabServiceNProduct').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";		
		str = "<input type='checkbox' name='chk' id='chk_"+serviceProductID+"'  value='"+serviceProductID+"' ><a title='"+ listServiceNProduct.list[i].serviceDetDescription +"'  href='#' >"+ listServiceNProduct.list[i].serviceDetDescription + "</a><input type='hidden' name='parenrSPId' id='parenrSPId_"+serviceProductID+"' value='"+listServiceNProduct.list[i].parent_serviceProductID+"'><input type='hidden' name='hdnProdName' id='hdnProdName_"+serviceProductID+"' value='"+listServiceNProduct.list[i].serviceDetDescription+"'><input type='hidden' name='hdnServiceId' id='hdnServiceId_"+serviceProductID+"' value='"+listServiceNProduct.list[i].serviceId+"'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";		
		str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text'class='inputBorder1' style='width:20px;float:left' readonly='true' name='custLogicalSINo_"+serviceProductID+"' id='custLogicalSINo_"+serviceProductID+"' value='"+listServiceNProduct.list[i].customer_logicalSINumber+"' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";		
		str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text'class='inputBorder1' style='width:20px;float:left' readonly='true' name='logicalSINo_"+serviceProductID+"' id='logicalSINo_"+serviceProductID+"' value='"+listServiceNProduct.list[i].logicalSINumber+"'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		//Added by Saurabh
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";		
		str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text'class='inputBorder1' style='width:40px;float:left' readonly='true' name='orderNo_"+serviceProductID+"' id='orderNo_"+serviceProductID+"' value='"+listServiceNProduct.list[i].orderNumber+"'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";	
		str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text'  size='15%' name='effDisconnectionDate_"+serviceProductID+"' id='effDisconnectionDate_"+serviceProductID+"' readonly='true'><font size='1'><img src='<%=request.getContextPath()%>/images/cal.gif' onclick=\"javascript:show_calendar('forms[0].effDisconnectionDate_"+serviceProductID+"');\" border='0px' alt='Loading...'></font>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";	
		//str = "<select onfocus='getTip_DD(this,this.selectedIndex,this.name)' name='stdDisconnectionReason_"+serviceProductID+"' id='stdDisconnectionReason_"+serviceProductID+"' ><option value='-1' selected='selected'>-Select-</option></select>";
		str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text'class='inputBorder1' style='width:160px;float:left' name='disconnectionRemarks_"+serviceProductID+"' id='disconnectionRemarks_"+serviceProductID+"'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}
	
}

//Draw Demo Table for Regularize by Saurabh
function drawDemoTable(listServiceNProduct)
{
	for (i = 0; i <  listServiceNProduct.list.length; i++)
	 {
		var serviceProductID=listServiceNProduct.list[i].serviceProductID;
   	   	var parentServiceProductID=listServiceNProduct.list[i].parent_serviceProductID;
		
		var key=parentServiceProductID+'';
		var str;
		var tblRow=document.getElementById('tabServiceNProduct').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";		
		str = "<input type='checkbox' onclick='checkorder(this)' name='chk' id='chk_"+serviceProductID+"' value='"+serviceProductID+"' ><a title='"+ listServiceNProduct.list[i].serviceDetDescription +"'  href='#' >"+ listServiceNProduct.list[i].serviceDetDescription + "</a><input type='hidden' name='parenrSPId' id='parenrSPId_"+serviceProductID+"' value='"+listServiceNProduct.list[i].parent_serviceProductID+"'><input type='hidden' name='hdnProdName' id='hdnProdName_"+serviceProductID+"' value='"+listServiceNProduct.list[i].serviceDetDescription+"'><input type='hidden' name='hdnServiceId' id='hdnServiceId_"+serviceProductID+"' value='"+listServiceNProduct.list[i].serviceId+"'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";		
		str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text'class='inputBorder1' style='width:20px;float:left' readonly='true' name='custLogicalSINo_"+serviceProductID+"' id='custLogicalSINo_"+serviceProductID+"' value='"+listServiceNProduct.list[i].customer_logicalSINumber+"' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";		
		str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text'class='inputBorder1' style='width:20px;float:left' readonly='true' name='logicalSINo_"+serviceProductID+"' id='logicalSINo_"+serviceProductID+"' value='"+listServiceNProduct.list[i].logicalSINumber+"'>";
		
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		//Added by Saurabh
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";		
		str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text'class='inputBorder1' style='width:40px;float:left' readonly='true' name='orderNo_"+serviceProductID+"' id='orderNo_"+serviceProductID+"' value='"+listServiceNProduct.list[i].orderNumber+"'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";	
		str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text'  size='15%' name='effDisconnectionDate_"+serviceProductID+"' id='effDisconnectionDate_"+serviceProductID+"' readonly='true'><font size='1'><img src='<%=request.getContextPath()%>/images/cal.gif' onclick=\"javascript:show_calendar('forms[0].effDisconnectionDate_"+serviceProductID+"');\" border='0px' alt='Loading...'></font>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";	
		//str = "<select onfocus='getTip_DD(this,this.selectedIndex,this.name)' name='stdDisconnectionReason_"+serviceProductID+"' id='stdDisconnectionReason_"+serviceProductID+"' ><option value='-1' selected='selected'>-Select-</option></select>";
		str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text'class='inputBorder1' style='width:160px;float:left' name='disconnectionRemarks_"+serviceProductID+"' id='disconnectionRemarks_"+serviceProductID+"'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
	}
	
}

//Added for upgrade/downgrade/shifting of Demo order types
function drawDemoTableForUDS(listServiceNProduct)
{
	for (i = 0; i <  listServiceNProduct.list.length; i++)
	 {
		var serviceProductID=listServiceNProduct.list[i].serviceProductID;
   	   	var parentServiceProductID=listServiceNProduct.list[i].parent_serviceProductID;
		
		var key=parentServiceProductID+'';
		var str;
		var tblRow=document.getElementById('tabServiceNProduct').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";		
		str = "<input type='checkbox' onclick='checkorder(this)' name='chk' id='chk_"+serviceProductID+"' value='"+serviceProductID+"' ><a title='"+ listServiceNProduct.list[i].serviceDetDescription +"'  href='#' >"+ listServiceNProduct.list[i].serviceDetDescription + "</a><input type='hidden' name='parenrSPId' id='parenrSPId_"+serviceProductID+"' value='"+listServiceNProduct.list[i].parent_serviceProductID+"'><input type='hidden' name='hdnProdName' id='hdnProdName_"+serviceProductID+"' value='"+listServiceNProduct.list[i].serviceDetDescription+"'><input type='hidden' name='hdnServiceId' id='hdnServiceId_"+serviceProductID+"' value='"+listServiceNProduct.list[i].serviceId+"'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		//Added by Saurabh
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";		
		str = "<input type='hidden' name='orderNo_"+serviceProductID+"' id='orderNo_"+serviceProductID+"' value='"+listServiceNProduct.list[i].orderNumber+"'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}
	
}

function drawRateRenewalTable(listServiceNProduct)
{
	for (i = 0; i <  listServiceNProduct.list.length; i++)
	 {
		var serviceProductID=listServiceNProduct.list[i].serviceProductID;
   	   	var parentServiceProductID=listServiceNProduct.list[i].parent_serviceProductID;
		
		var key=parentServiceProductID+'';
		var str;
		var tblRow=document.getElementById('tabServiceNProduct').insertRow();
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";		
		str = "<input type='checkbox' name='chk' id='chk"+serviceProductID+"'  value='"+serviceProductID+"' ><a title='"+ listServiceNProduct.list[i].serviceDetDescription +"'  href='#' >"+ listServiceNProduct.list[i].serviceDetDescription + "</a><input type='hidden' name='parenrSPId' id='parenrSPId_"+serviceProductID+"' value='"+listServiceNProduct.list[i].parent_serviceProductID+"'><input type='hidden' name='hdnProdName' id='hdnProdName_"+serviceProductID+"' value='"+listServiceNProduct.list[i].serviceDetDescription+"'><input type='hidden' name='hdnServiceID' id='hdnServiceID_"+serviceProductID+"' value='"+listServiceNProduct.list[i].serviceId+"'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}
	
} 

//check an Unchecl all product
function checkUncheckAll()
{
	ids=document.forms[0].chk;	
		if(ids==null)
		{	
			alert('No Request Present.');
			document.getElementById('chkHead').checked=false;
			return false;
		}
	var i;
	if(document.getElementById("chkHead").checked==true)
	{	
		if(document.forms[0].chk.length!=null)
		{	for(i=0;i<document.forms[0].chk.length;i++ )
			{
				document.forms[0].chk[i].checked=true;
			}
		}
		else
		{
			document.forms[0].chk.checked=true;
		}
	}
	else
	{
		if(document.forms[0].chk.length!=null)
		{
			for(i=0;i<document.forms[0].chk.length;i++ )
			{
				document.forms[0].chk[i].checked=false;
			}
		}
		else
		{
			document.forms[0].chk.checked=false;
		}	
		
	}
	
}
function unCheckParentId(ServiceProductID)
{//global_map_parent_child
if(document.getElementById('chk_'+ServiceProductID) !=null)
	{
		document.getElementById('chk_'+ServiceProductID).checked=false;
		var key=ServiceProductID+'';
		var tempArray=global_map_parent_child[key];
		if(tempArray!=null)
		{
		for(i=0;i<tempArray.length;i++)
		{
			var childval=tempArray[i];
			document.getElementById('chk_'+childval).checked=false;
			
		}
	} 
		unCheckParentId(document.getElementById('parenrSPId_'+ServiceProductID).value);
	}
}
//Added by Saurabh for Demo Regularize
function checkorder(a)
{
	var spID=a.value
	var count=0;
	var frm=document.getElementById('SNPType');
	//alert(document.getElementById("orderNo_"+spID).value)
	//alert(spID)
	var orderNo = document.getElementById("orderNo_"+spID).value;
	for(i=0;i<document.forms[0].chk.length;i++ )
	{
		if(spID!=frm.chk[i].value)
		{
			count++
		}
		else
		{
			break;
		}
	}
	
	//alert(count)
	//alert(frm);
	//alert("check value : "+frm.chk[i].checked);
	//for(i=0;i<document.forms[0].chk.length;i++ )
	//{var order 
		//alert("orderNo = "+document.getElementById('orderNo_'+frm.chk[i].value).value);
		//var orderNo=document.getElementById('orderNo_'+frm.chk[i].value).value;
		
		if(frm.chk[count].checked==true)
			{
			//alert("check value : "+frm.chk[i].checked);
			//var orderNo=document.getElementById('orderNo_'+frm.chk[j].value).value;
			for(j=0;j<document.forms[0].chk.length;j++)
				{
				//alert("in if loop");
				//alert(document.getElementById('orderNo_'+frm.chk[j].value).value);
				if(orderNo==document.getElementById('orderNo_'+frm.chk[j].value).value)
					{
					frm.chk[j].checked=true;
					}
				}
			}
		else if(frm.chk[count].checked==false)
			{
			//alert("check value : "+frm.chk[i].checked);
			//document.forms[0].chk[i].value;
			//var orderNo=document.getElementById('orderNo_'+frm.chk[j].value).value;
			for(j=0;j<document.forms[0].chk.length;j++)
				{
				//alert("in else loop");
				//alert(document.getElementById('orderNo_'+frm.chk[j].value).value);
				if(orderNo==document.getElementById('orderNo_'+frm.chk[j].value).value)
					{
					frm.chk[j].checked=false;
					}
				}
			//}	
	}
}

//Ramana
function SelectedServiceNProduct()
{
			var chkLength=document.forms[0].chk.length;							
			var hdnOrderNo=document.forms[0].hdnOrderNo.value;
			var serviceProductId=new Array();			
			var flag;		
			var j=0;
				
			if(document.forms[0].chk.checked==true)
			{			
				flag=true;
				serviceProductId[j]=document.forms[0].chk.value;	
				j=j+1;				
				
			}
						
			for(i=0; i<chkLength;i++)
			{				
				var val = document.forms[0].chk[i].value;				
		        if(document.getElementById('chk_'+val).checked==true)	
				{
					serviceProductId[j]=val;alert(val);	
					j=j+1;				
				}										
		        if(document.forms[0].chk[i].checked==true)	
		         {
					flag=true;
		         }			
			}						
				
			if(flag!=true)
				{
					alert("Please Select Product!!!!");	
				}  
			else
			{
				var jsdata={
								hdnOrderNo:hdnOrderNo,
								serviceProductIds:serviceProductId,
								subChangeTypeId:document.forms[0].txtChangeType.value
						   };
				jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC"); 
				try
					{
						result = jsonrpc.processes.DisplayServicesInLines(jsdata); 
					}
				catch(e)
				 		{
				 			alert("exception     " + e);
				 		}
				 		
				 	if(result.msgOut != "" && result.msgOut != null)
		       {
		       		
		       		alert(result.msgOut);
		       		callerWindowObj.document.searchForm.changeType.value = document.getElementById("txtChangeType").value;
                                                                       var flag=5;
		       		callerWindowObj.ViewServiceTree(flag);		       		
		       		window.close();	
		       		
		       }
		       else
		       {
		       		alert("Data Not Inserted while Saving !!");
		       }
			}
}
//Ramana
function tableDrawing()
{
	   var mytable = document.getElementById('tabServiceNProduct');	 
	   var iCountRows = mytable.rows.length;
	   //Start[001]
	   document.SNPType.hdnChangeType.value = callerWindowObj.document.getElementById('HdnChangeTypeID').value;
		//End[001]
	   for (i =iCountRows-1; i >=3; i--)
	   {	
	       mytable.deleteRow(i);
	   } 
	  if(document.SNPType.issuspended.value=='' && document.SNPType.isdisconnected.value=='')
	  {
	  	isdisconnected=0;
	  	issuspended=0;
	  }
	  else
	  {
	  	issuspended=document.SNPType.issuspended.value;
	  	isdisconnected=document.SNPType.isdisconnected.value;
	  }
	/* var jsData =
			{
				//serviceIdString:document.getElementById('serviceId').value,
				//changeTypeId:document.getElementById('txtChangeType').value
				serviceIdString:document.getElementById('serviceId').value,
				changeTypeId:document.SNPType.hdnChangeType.value,
				subChangeTypeId:document.forms[0].txtChangeType.value,
				issuspended:issuspended,
				isdisconnected:isdisconnected		
			};
			
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	try{
	var listServiceNProduct = jsonrpc.processes.getServiceNProduct(jsData);	
	}
	catch(e)
	{
		alert("Exception:"+e);
	} */
	var counter = <%=request.getAttribute("count")%>;
	var parentNodeId;
	var nodeText;
	  var map=new Object();
	  global_map_spId_Label=new Object();
	  global_map_spId_ParentId=new Object();
	  global_map_Link_spid=new Object();
		var treeString;
	
	if(document.SNPType.hdnChangeType.value== 3)
	{		
		//drawDisconnectTable(listServiceNProduct);
	} else  if(document.SNPType.hdnChangeType.value== 5)
	{
	//drawRateRenewalTable(listServiceNProduct);
	}
	else if(document.SNPType.hdnChangeType.value== 2)
	{
		//drawSolutionChangeTable(listServiceNProduct);
	}
	//Added by Saurab	
	
	//else  if(document.SNPType.hdnChangeType.value== 4 && (document.forms[0].txtChangeType.value == 13 || document.forms[0].txtChangeType.value == 14 || document.forms[0].txtChangeType.value == 15 || document.forms[0].txtChangeType.value == 16))
	//{	
		//drawDisconnectTable(listServiceNProduct);
	//}
	//else  if(document.SNPType.hdnChangeType.value== 4 && document.forms[0].txtChangeType.value == 12)
	//{
		//drawDemoTable(listServiceNProduct);
	//}
	//else if(document.SNPType.hdnChangeType.value== 4 && (document.forms[0].txtChangeType.value == 17 || document.forms[0].txtChangeType.value==18 || document.forms[0].txtChangeType.value==19))
	//{
	
	//drawDemoTableForUDS(listServiceNProduct);
	//}
	document.getElementById('showFiledsAfterSISelection').style.display = "block";
	return;
	
}

function SelectLogicalSINo(lookupMode) 
{   
		//Added validation for Sub Change Type
		//var changeType=trim(document.forms[0].txtChangeType.value);
		
		var issuspended = 0;
		var isdisconnected = 0; 
		// [00044] Start
		if(subChangeTypeId == 0)
		{
			alert("Please Select Sub Change Type!! ") ;
			return false;
		}		
	 	
        var accountID=document.SNPType.hdnAccountId.value;        
        //for permanent disconnection 
        if (changeTypeId ==3 && subChangeTypeId == 3 )   
        {
         issuspended = 0;
         isdisconnected =0;
        } 
        // for permanent disconnection after suspension
        else if(changeTypeId ==3 && subChangeTypeId == 4 )
        {
         issuspended = 1;
         isdisconnected = 0;
        }
         //for suspension
        else if( changeTypeId ==3 && subChangeTypeId == 6 )
        {
        	issuspended = 0;
        	isdisconnected = 0
        }
        // for resumption
        else if(changeTypeId ==3 && subChangeTypeId == 7 )
        {
          issuspended = 1;
          isdisconnected = 0;
        }
	        
        //------------------- For Demo Order Validation and fetching Logical SI no. and Customer LSI nO.
        
        //for Demo Order EXTENSION 
        if (changeTypeId ==4 && subChangeTypeId == 11 )   
        {
         demo = subChangeTypeId;
        } 
        //for Demo Order REGULARIZE
        if (changeTypeId ==4 && subChangeTypeId == 12 )   
        {
         demo = subChangeTypeId;
        } 
        //for Demo Order SUSPENTION
        if (changeTypeId ==4 && subChangeTypeId == 13 )   
        {
         	issuspended = 0;
        	isdisconnected = 0
        } 
        //for Demo Order RESUMPTION
        if (changeTypeId ==4 && subChangeTypeId == 14 )   
        {
        	issuspended = 1;
          	isdisconnected = 0;
        } 
        //for Demo Order PERMANENT DISCONNECTION
        if (changeTypeId ==4 && subChangeTypeId == 15 )   
        {
         	issuspended = 0;
         	isdisconnected = 0;
        } 
        //for Demo Order PERMANENT DISCONNECTION AFTER SUSPENSION
        if (changeTypeId ==4 && subChangeTypeId == 16 )   
        {
         issuspended = 1;
         isdisconnected = 0;
        } 
        //for Demo Order UPGRADE
        if (changeTypeId ==4 && subChangeTypeId == 17 )   
        {
         demo = subChangeTypeId;
        } 
        //for Demo Order DOWNGRADE
        if (changeTypeId ==4 && subChangeTypeId == 18 )   
        {
         demo = subChangeTypeId;
        } 
        //for Demo Order SHIFTING
        if (changeTypeId ==4 && subChangeTypeId == 19 )   
        {
         demo = subChangeTypeId;
        } 
        //--------------------------------------ended demo condition----
        else if(changeTypeId ==2 && subChangeTypeId == 8 )
        {
          issuspended = 0;
          isdisconnected = 0;
        }	
        else if(changeTypeId ==2 && subChangeTypeId == 9 )
        {
          issuspended = 0;
          isdisconnected = 0;
        }	
        // [00044] End
       /*else if(document.SNPType.hdnChangeType.value ==2 && document.forms[0].txtChangeType.value == 10 )
        {
          issuspended = 0;
          isdisconnected = 0;
        }*/			
		document.SNPType.issuspended.value=issuspended;
		document.SNPType.isdisconnected.value=isdisconnected;		
		var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=getLogicalSINumber&accountID="+accountID+"&lookupMode="+lookupMode;		
		window.showModalDialog(path,window,"status:false;dialogWidth:800px;dialogHeight:600px");		
}

//saveSolutionChangeType()

function saveSolutionChangeType()
{
			
				alert(result.msgOut);
			 if(result.msgOut != "" && result.msgOut != null)
		       {
		       		saveSuccess=true;
		       		alert(result.msgOut);alert("loop");
		       		
		       }
		       else
		       {
		       		alert("Data Not saved successfully!!");
		       }
		       if(saveSuccess==true)
		       {
					callerWindowObj.document.searchForm.hdnShowTree.value = "true";
					callerWindowObj.document.searchForm.hdnServiceNo.value =ServiceId;	
					callerWindowObj.document.searchForm.logicalSINo.value =document.forms[0].logicalSINo.value;
					callerWindowObj.document.searchForm.hdnOrderNo.value =hdnOrderNo;
					/* alert('txtChangeType : '  + document.getElementById("txtChangeType").value); */	
					callerWindowObj.document.searchForm.changeType.value = document.getElementById("txtChangeType").value;
				/* alert('Callerwidow txtChangeType : '  + callerWindowObj.document.searchForm.changeType.value); */
var flag=5;	
					callerWindowObj.ViewServiceTree(flag);
				
					window.close();		       
		       }
}


//savaDisconnectType()
function saveDisconnectType()
{

			var chkLength=document.forms[0].chk.length;
			var ServiceProductId=new Array();
			//var ServiceId=document.getElementById('serviceId').value;
			var hdnOrderNo=document.forms[0].hdnOrderNo.value;
			var effectiveDate= new Array();
			var disconnectRemarks= new Array();
			var ServiceIds=new Array();
			var custLogicalSINo=new Array();
			var logicalSINo=new Array();
			var result="";
			var saveSuccess;
			//alert(serviceId);
			//var val = document.forms[0].chk.value;		  	
			// effectiveDate[0]=document.getElementById('effDisconnectionDate_'+val).value;	
			 //alert("effctive Date"+document.getElementById('effDisconnectionDate_'+val).value);
			var index=0;	
			if(chkLength==undefined)
			{
				var val = document.forms[0].chk.value;
				if(document.getElementById('chk_'+val).checked==true)	
				{
					ServiceProductId[index]=val;
					effectiveDate[index]=document.getElementById('effDisconnectionDate_'+val).value;
					disconnectRemarks[index]=document.getElementById('disconnectionRemarks_'+val).value;
					ServiceIds[index]=document.getElementById('hdnServiceId_'+val).value;
					custLogicalSINo[index]=document.getElementById('custLogicalSINo_'+val).value;
					logicalSINo[index]=document.getElementById('logicalSINo_'+val).value;
					if(document.getElementById('effDisconnectionDate_'+val).value =='')
						{
							alert("Please Enter Effective Disconnection Date!! ") ;							
							return false;
						}
					
					if(document.getElementById('disconnectionRemarks_'+val).value == '')
						{
							alert("Please Enter Disconnection remarks!! ") ;							
							return false;
						}
						index=index+1;
					}
			}			 
			for(i=0; i<chkLength;i++)
			{				
				
				var val = document.forms[0].chk[i].value;				
				if(document.getElementById('chk_'+val).checked==true)	
				{
					ServiceProductId[index]=val;
					effectiveDate[index]=document.getElementById('effDisconnectionDate_'+val).value;
					disconnectRemarks[index]=document.getElementById('disconnectionRemarks_'+val).value;
					ServiceIds[index]=document.getElementById('hdnServiceId_'+val).value;
					custLogicalSINo[index]=document.getElementById('custLogicalSINo_'+val).value;
					logicalSINo[index]=document.getElementById('logicalSINo_'+val).value;
					
					if(document.getElementById('effDisconnectionDate_'+val).value =='')
						{
							alert("Please Enter Effective Disconnection Date!! ") ;							
							return false;
						}
					
					if(document.getElementById('disconnectionRemarks_'+val).value == '')
						{
							alert("Please Enter Disconnection remarks!! ") ;							
							return false;
						}
						index=index+1;
					}
				}
				if(index>0)
				{
					
					var jsData =			
						    {
							serviceProductIds:ServiceProductId,						
							hdnOrderNo:hdnOrderNo,
							changeTypeId:document.SNPType.hdnChangeType.value,
							subChangeTypeId:document.forms[0].txtChangeType.value,											
							effectiveDate:effectiveDate					
							
							};			
				jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");			
				try{
				 result = jsonrpc.processes.SaveServiceProductType(jsData);
				 }
				 catch(e)
				 {
				 alert("exception     " + e);
				 }
			 }
			 else
			 {
			 		alert("Please Select Product!!!!");
					return false;
				}
			 if(result.msgOut != "" && result.msgOut != null)
		       {
		       		saveSuccess=true;
		       		alert(result.msgOut);
		       		
		       }
		       else
		       {
		       		alert("Data Not Saved Successfully !!");
		       }
		       if(saveSuccess==true)
		       {
					callerWindowObj.document.searchForm.hdnShowTree.value = "true";
					callerWindowObj.document.searchForm.hdnServiceNo.value =ServiceIds;	
					callerWindowObj.document.searchForm.logicalSINo.value =logicalSINo;
					callerWindowObj.document.searchForm.hdnOrderNo.value =hdnOrderNo;
					callerWindowObj.document.searchForm.changeType.value = document.getElementById("txtChangeType").value;	
					var flag=5;	
					callerWindowObj.ViewServiceTree(flag);
				
					window.close();		       
		       }
}			

//saveDemoTypeRegularize()
function saveDemoTypeRegularize()
{
			var chkLength=document.forms[0].chk.length;
			var ServiceProductId=new Array();
			//var ServiceId=document.getElementById('serviceId').value;
			var hdnOrderNo=document.forms[0].hdnOrderNo.value;
			var effectiveDate= new Array();
			var ChangeRemarks= new Array();
			var ServiceIds=new Array();
			var custLogicalSINo=new Array();
			var logicalSINo=new Array();
			var result="";
			var saveSuccess;
			var index=0;	
			if(chkLength==undefined)
			{
				var val = document.forms[0].chk.value;
				if(document.getElementById('chk_'+val).checked==true)	
				{
					ServiceProductId[index]=val;
					effectiveDate[index]=document.getElementById('effDisconnectionDate_'+val).value;
					ChangeRemarks[index]=document.getElementById('disconnectionRemarks_'+val).value;
					ServiceIds[index]=document.getElementById('hdnServiceId_'+val).value;
					custLogicalSINo[index]=document.getElementById('custLogicalSINo_'+val).value;
					logicalSINo[index]=document.getElementById('logicalSINo_'+val).value;
					if(document.getElementById('effDisconnectionDate_'+val).value =='')
						{
							alert("Please Enter Effective Change Date!! ") ;							
							return false;
						}
					
					if(document.getElementById('disconnectionRemarks_'+val).value == '')
						{
							alert("Please Enter remarks!! ") ;							
							return false;
						}
						index=index+1;
					}
			}			 
			for(i=0; i<chkLength;i++)
			{				
				
				var val = document.forms[0].chk[i].value;				
				if(document.getElementById('chk_'+val).checked==true)	
				{
					ServiceProductId[index]=val;
					effectiveDate[index]=document.getElementById('effDisconnectionDate_'+val).value;
					ChangeRemarks[index]=document.getElementById('disconnectionRemarks_'+val).value;
					ServiceIds[index]=document.getElementById('hdnServiceId_'+val).value;
					custLogicalSINo[index]=document.getElementById('custLogicalSINo_'+val).value;
					logicalSINo[index]=document.getElementById('logicalSINo_'+val).value;
					
					if(document.getElementById('effDisconnectionDate_'+val).value =='')
						{
							alert("Please Enter Effective Change Date!! ") ;							
							return false;
						}
					
					if(document.getElementById('disconnectionRemarks_'+val).value == '')
						{
							alert("Please Enter remarks!! ") ;							
							return false;
						}
						index=index+1;
					}
				}
				if(index>0)
				{
					
					var jsData =			
						    {
							serviceProductIds:ServiceProductId,						
							hdnOrderNo:hdnOrderNo,
							changeTypeId:document.SNPType.hdnChangeType.value,
							subChangeTypeId:document.forms[0].txtChangeType.value,											
							effectiveDate:effectiveDate,
							stdReason:ChangeRemarks					
							
							};			
				jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");			
				try{
				 result = jsonrpc.processes.SaveServiceProductType(jsData);
				 }
				 catch(e)
				 {
				 alert("exception     " + e);
				 }
			 }
			 else
			 {
			 		alert("Please Select Product!!!!");
					return false;
				}
			 if(result.msgOut != "" && result.msgOut != null)
		       {
		       		saveSuccess=true;
		       		alert(result.msgOut);
		       		
		       }
		       else
		       {
		       		alert("Data Not Saved Successfully !!");
		       }
		       if(saveSuccess==true)
		       {
					callerWindowObj.document.searchForm.hdnShowTree.value = "true";
					callerWindowObj.document.searchForm.hdnServiceNo.value =ServiceIds;	
					callerWindowObj.document.searchForm.logicalSINo.value =logicalSINo;
					callerWindowObj.document.searchForm.hdnOrderNo.value =hdnOrderNo;
					callerWindowObj.document.searchForm.changeType.value = document.getElementById("txtChangeType").value;	
					var flag=5;	
					callerWindowObj.ViewServiceTree(flag);
				
					window.close();		       
		       }
}

//saveDemoTypeRegularize()
function saveDemoTypeUDS()
{
			var chkLength=document.forms[0].chk.length;							
			var hdnOrderNo=document.forms[0].hdnOrderNo.value;
			var serviceProductId=new Array();			
			var flag;		
			var j=0;
				
			if(document.forms[0].chk.checked==true)
			{			
				flag=true;
				serviceProductId[j]=document.forms[0].chk.value;	
				j=j+1;				
				
			}
						
			for(i=0; i<chkLength;i++)
			{				
				var val = document.forms[0].chk[i].value;				
		        if(document.getElementById('chk_'+val).checked==true)	
				{
					serviceProductId[j]=val;
					j=j+1;				
				}										
		        if(document.forms[0].chk[i].checked==true)	
		         {
					flag=true;
		         }			
			}						
				
			if(flag!=true)
				{
					alert("Please Select Product!!!!");	
				}  
			else
			{
				var jsdata={
								hdnOrderNo:hdnOrderNo,
								serviceProductIds:serviceProductId,
								subChangeTypeId:document.forms[0].txtChangeType.value
						   };
				jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC"); 
				try
					{
						result = jsonrpc.processes.DisplayServicesInLines(jsdata); 
					}
				catch(e)
				 		{
				 			alert("exception     " + e);
				 		}
				 		
				 	if(result.msgOut != "" && result.msgOut != null)
		       {
		       		
		       		alert(result.msgOut);
		       	alert('txtChangeType : '  + document.getElementById("txtChangeType").value);	
					callerWindowObj.document.searchForm.hdnSubchangeType.value = document.getElementById("txtChangeType").value;
			alert('Callerwidow txtChangeType : '  + callerWindowObj.document.searchForm.hdnSubchangeType.value);	
				var flag=5;	
					callerWindowObj.ViewServiceTree(flag);     		
		       		window.close();	
		       		
		       }
		       else
		       {
		       		alert("Data Not Saved Successfully !!");
		       }
			}
}


function SaveProductType()
{

	// var callerWindowObj = dialogArguments;
 	if ( document.SNPType.hdnChangeType.value== 3)
		{
			saveDisconnectType();	
		}
	else if(document.SNPType.hdnChangeType.value== 2)	
	{
		saveSolutionChangeType();
	}
	//Added by Ashutosh & Saurabh
	else if(document.SNPType.hdnChangeType.value== 4)
		{
			if(document.forms[0].txtChangeType.value==13 || document.forms[0].txtChangeType.value==14 || document.forms[0].txtChangeType.value==15 || document.forms[0].txtChangeType.value==16)
			{
			
				saveDisconnectType();
			}
			else if(document.forms[0].txtChangeType.value==12)
			{
				saveDemoTypeRegularize();
			}
			else if(document.forms[0].txtChangeType.value==17 || document.forms[0].txtChangeType.value==18 || document.forms[0].txtChangeType.value==19 )
			{
				saveDemoTypeUDS();
			}
		}
	else 
	{
   		var chkLength=document.forms[0].chk.length;
   		var  ServiceId=document.forms[0].serviceId.value;
    	var  hdnOrderNo=document.forms[0].hdnOrderNo.value;
	    var ServiceProductId=new Array();
	    var effectiveDate= new Array();
		var rateChange= new Array();
		var stdReason= new Array();
	    var result="";
     /* if(trim(document.forms[0].logicalSINo.value) == '')
		{
			alert("Please Enter logicalSINo!! ") ;
			return false;
		}
		if(trim(document.forms[0].txtChangeType.value)== 0)
		{
			alert("Please Enter txtChangeType!! ") ;
			return false;
		}
		if(trim(document.forms[0].serviceName.value) == '')
		{
			alert("Please Enter ServiceName!! ") ;
			return false;
		}
		if (document.getElementById('txtChangeType').value != 3) {
		if(trim(document.forms[0].effChangeStartDate.value) == '')
		{
			alert("Please Enter Effective ChangeStartDate !!") ;
			return false;
		}
		if(trim(document.forms[0].effChangeEndDate.value) == '')
		{
			alert("Please Enter Effective ChangeEndDate!! ") ;
			return false;
		} */
		/*var effChangeStartDate=document.forms[0].effChangeStartDate.value;
		var effChangeEndDate=document.forms[0].effChangeEndDate.value;
		
		if(dateCompare(effChangeStartDate,effChangeEndDate)==1)
			{			
				alert("Effective ChangeStartDate Cannot be less than Effective ChangeEndDate!!");
				return false;
			}
		if(trim(document.forms[0].remarks.value) == '')
		{
			alert("Please Enter remarks!! ") ;
			document.forms[0].remarks.focus();
			return false;
		} */
		
    if(chkLength==undefined)
	{
		if(document.forms[0].chk.checked==true)
		{
		  ServiceProductId[0]=document.forms[0].chk.value;
		  var val = document.forms[0].chk.value;
		  if (document.getElementById('txtChangeType').value == 5) {
		 effectiveDate[0]=document.getElementById('effDate_'+val).value;
		 rateChange[0]= document.getElementById('changeRate_'+val).value;
		 stdReason[0]= document.getElementById('stdReason_'+val).value;
		 } else if(document.getElementById('txtChangeType').value ==3) {
		  	effectiveDate[0]=document.getElementById('effDisconnectionDate_'+val).value;
			stdReason[0]= document.getElementById('stdDisconnectionReason_'+val).value;
		 }
			var jsData =			
					    {
						serviceProductIds:ServiceProductId,
						serviceIdString :ServiceId,
						hdnOrderNo:hdnOrderNo,
						effStartDate:document.forms[0].effChangeStartDate.value,
						effEndDate:document.forms[0].effChangeEndDate.value,
						changeTypeId:document.forms[0].txtChangeType.value,
						attRemarks:document.forms[0].remarks.value,
						effectiveDate:effectiveDate,
						rateChange:rateChange,
						stdReason:stdReason
						
						};
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			 result = jsonrpc.processes.SaveServiceProductType(jsData);
			if(result.msgOut != "" && result.msgOut != null)
		       {
		       		alert(result.msgOut);
		       }
		       else
		       {
		       		alert("Data Not Saved Successfully !!");
		       }
		}
		else
		{
		 alert('Please Select Product Id!!');
		 return false;
		}
	}
	else
	{
	    var count=1;
	    index=0;
		for(i=0;i<chkLength;i++)
		{
			var myform=document.getElementById("SNPType");
			if(myform.chk[i].checked==true)
			{
				 ServiceProductId [index]= myform.chk[i].value;
				 var val = myform.chk[i].value;
				 
				
				if (document.getElementById('txtChangeType').value ==5)
				{
				   
					 effectiveDate[index]=document.getElementById('effDate_'+val).value;
					 rateChange[index]= document.getElementById('changeRate_'+val).value;
				 	 stdReason[index]= document.getElementById('stdReason_'+val).value;
				} else if(document.getElementById('txtChangeType').value == 3) {
				    
				    
		  			effectiveDate[index]=document.getElementById('effDisconnectionDate_'+val).value;
		  			
					stdReason[index]= document.getElementById('stdDisconnectionReason_'+val).value;
					
		 			}
				 index++;
				 count++;
			}
		}
		if(count > 1)
		{
			var jsData =			
					    {
						serviceProductIds:ServiceProductId,
						serviceIdString:ServiceId,
						hdnOrderNo:hdnOrderNo,
						effStartDate:document.forms[0].effChangeStartDate.value,
						effEndDate:document.forms[0].effChangeEndDate.value,
						changeTypeId:document.forms[0].txtChangeType.value,
						attRemarks:document.forms[0].remarks.value,
						effectiveDate:effectiveDate,
						rateChange:rateChange,
						stdReason:stdReason
						};
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			 result = jsonrpc.processes.SaveServiceProductType(jsData);
			  if(result.msgOut != "" && result.msgOut != null)
		       {
		       		alert(result.msgOut);
		       }
		       else
		       {
		       		alert("Data Not Saved Successfully !!");
		       }
		}
		else
		{
			alert('Please Select Product Id!!');
			return false;
		}
	}
	
	callerWindowObj.document.searchForm.hdnShowTree.value = "true";
	callerWindowObj.document.searchForm.hdnServiceNo.value =ServiceId;	
	callerWindowObj.document.searchForm.logicalSINo.value =document.forms[0].logicalSINo.value;
	callerWindowObj.document.searchForm.hdnOrderNo.value =hdnOrderNo;	
	callerWindowObj.document.searchForm.changeType.value = document.getElementById("txtChangeType").value;
	var flag=5;	
					callerWindowObj.ViewServiceTree(flag);

	window.close();

	}
}

function changeRatereneval()
{ 
	var changeTypeID = document.getElementById('txtChangeType').value;		
	document.SNPType.hdnSubChangeType.value=document.getElementById("txtChangeType").value;
	
   if(document.getElementById('txtChangeType').value==6 && document.SNPType.hdnChangeType.value== 5)
   {
	  /* if(document.forms[0].chk != null)
	   {
	   
		 		if(document.forms[0].chk.length==null)  
		 		{
		 		        var spid=document.forms[0].chk.value;
		 		        stdReasonComo(spid,changeTypeID);
		 		        document.getElementById('chk_span'+spid).style.styleFloat  ="left";
		 		       	document.getElementById('rateRenevalDivId_'+spid).style.display="block";

		 		       	
					  		 
		 		}
		 		else
		 		{
		 		
		 			for(i=0;i<document.forms[0].chk.length;i++)
		 			{
		 			   var spid=document.forms[0].chk[i].value;
		 			   stdReasonComo(spid,changeTypeID);
		 			    document.getElementById('chk_span'+spid).style.styleFloat  ="left";
		 			 	document.getElementById('rateRenevalDivId_'+spid).style.display="block";	 		
		 			 	
						
		 			}
		 		}
		 	}
		else
		{
		   alert('No Product In this Logical Id Please Select Valid Logical Id !!');
		}*/
   }
   
   //Only for Disconnection type
   else if(document.SNPType.hdnChangeType.value== 3 || document.SNPType.hdnChangeType.value== 4)
   {   		
   		var subChangeTypeId=document.getElementById('txtChangeType').value
   		document.getElementById('logicalSINo').value="";
   	   	var mytable = document.getElementById('tabServiceNProduct');	 
	   	var iCountRows = mytable.rows.length;
	   for (i =iCountRows-1; i >=3; i--)
	   {	
	       mytable.deleteRow(i);
	   } 
	   /*if(document.forms[0].chk != null)
	   {
		 		if(document.forms[0].chk.length==null)  
		 		{
		 		        var spid=document.forms[0].chk.value;
		 		        stdReasonComo(spid,changeTypeID);
		 		        document.getElementById('chk_span'+spid).style.styleFloat  ="left";
		 		       	document.getElementById('DisconnectionDivId_'+spid).style.display="block";

		 		}
		 		else
		 		{
		 			for(i=0;i<document.forms[0].chk.length;i++)
		 			{
		 			    var spid=document.forms[0].chk[i].value;
		 			   stdReasonComo(spid,changeTypeID);
		 			    document.getElementById('chk_span'+spid).style.styleFloat  ="left";
		 			 	document.getElementById('DisconnectionDivId_'+spid).style.display="block";	 		
 
		 			}
		 		}
		 	}
		else
		{
		   //alert('No Product In this Logical Id Please Select Valid Logical Id !!');
		}*/ 
   }
  else
    if(document.forms[0].chk != null)
    {
   
	 		if(document.forms[0].chk.length==null)  
	 		{
	 		       var spid=document.forms[0].chk.value;
	 		        document.getElementById('chk_span'+spid).style.styleFloat  ="none";
				  	document.getElementById('rateRenevalDivId_'+spid).style.display="none";	 		
	 
	 		}
	 		else
	 		{
	 			for(i=0;i<document.forms[0].chk.length;i++)
	 			{
	 			    var spid=document.forms[0].chk[i].value;
	 			     document.getElementById('chk_span'+spid).style.styleFloat  ="none";
	 			 	document.getElementById('rateRenevalDivId_'+spid).style.display="none";	 		

	 			}
	 		}
	 	}
	 	else
		{
		  // alert('No Product In this Logical Id Please Select Valid Logical Id !!');
		}
}
function stdReasonComo(spid,changeTypeID)
{
	     var tr, td, i, j, oneRecord;
	     var changeStdReasonList;
	     if(changeTypeID == 3) {
	     	 var combo = document.getElementById('stdDisconnectionReason_'+spid);
	     }else {
	     	 var combo = document.getElementById('stdReason_'+spid);
	     }
	    
	     var iCountRows = combo.length;
	  
	    for (i = 1; i <  iCountRows; i++)
	    {
	       combo.remove(1);
	    }
		var jsData =			
					    {
						changeTypeId:changeTypeID
						};
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    changeStdReasonList = jsonrpc.processes.getStdReasonCombo(jsData);
	    for(j=0;j<changeStdReasonList.list.length;j++)
	    {
		   	var option = document.createElement("option");
		  	option.text = changeStdReasonList.list[j].stdReasonName;
		  	option.title = changeStdReasonList.list[j].stdReasonName;
			option.value = changeStdReasonList.list[j].stdReasonId;
	
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

 var ServiceProductIds=new Array();
 var ServiceProductName=new Array();
 var ServiceIds=new Array();
function popRateRenewal(count) 
{


     var chkLength=document.forms[0].chk.length;
		
    if(chkLength==undefined)
	{
		if(document.forms[0].chk.checked==true)
		{
		  ServiceProductIds[0]=document.forms[0].chk.value;
		  ServiceProductName[0]=document.forms[0].hdnProdName.value;
		  ServiceIds[0]=document.forms[0].hdnServiceID.value;
		}  
	}
	else
	{
	    var count=1;
	    index=0;
		for(i=0;i<chkLength;i++)
		{
			var myform=document.getElementById("SNPType");
			if(myform.chk[i].checked==true)
			{
				 ServiceProductIds [index]= myform.chk[i].value;
				 ServiceProductName[index]=document.forms[0].hdnProdName[i].value;
				 ServiceIds[index]=document.forms[0].hdnServiceID.value;
				 index++;
				 count++;
			}
		}
		
	}
    
    
    
	var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=rateRenewal";		
		window.showModalDialog(path,window,"status:false;dialogWidth:600px;dialogHeight:600px");
		//window.open (path,"mywindow","status=1,toolbar=1 width=800,height=600"); 
 }
 
 function setDataForService()
 { 

 	var  ServiceId=document.forms[0].serviceId.value;
 	var  hdnOrderNo=document.forms[0].hdnOrderNo.value;
	callerWindowObj.document.searchForm.hdnServiceNo.value =ServiceId;	
	callerWindowObj.document.searchForm.logicalSINo.value =document.forms[0].logicalSINo.value;
	callerWindowObj.document.searchForm.hdnOrderNo.value =hdnOrderNo;	
	callerWindowObj.document.searchForm.changeType.value = document.getElementById("txtChangeType").value;
	var flag=5;	
					callerWindowObj.ViewServiceTree(flag);
	window.close();
   
 }
var path='<%=request.getContextPath()%>';
 function SaveService(){

		// [00044] Start
		if(subChangeTypeId==0)
		{
		alert("Please enter sub Change Type");
		return false;
		}
		// [00044] End
		if(document.SNPType.logicalSINo.value=="")
		{
		alert("Please enter logical SI Number");
		return false;
		}
		if(document.getElementById('effectiveDate').value=="")
		{
			if(changeTypeId==5) // for RR , Shubhranshu
				{alert("Please enter RR effective Date");}
			else
				{alert("Please enter effective Date");}
		return false;
		}
		//Meenakshi: Making Remarks non Mandatory as per ME UAT Observations
		//if(document.getElementById('remarks').value=="")
		//{
		//alert("Please enter Remarks");
		//return false;
		//}
		if(document.getElementById('changeReason').value=='0')
		{
		alert("Please Select Reason for Change");  //modified By Shubhranshu
		return false;
		}
			
			var hdnOrderNo=document.forms[0].hdnOrderNo.value;
			var effectiveDate= document.getElementById('effectiveDate').value;
			var resaonId= document.getElementById('changeReason').value;
			var reasonName=document.getElementById("changeReason").options[document.getElementById("changeReason").selectedIndex].text;
			var ServiceIds=document.SNPType.serviceId.value;
			var logicalSINo=document.SNPType.logicalSINo.value;
			var result="";
			var saveSuccess;
			//Vijay start 
			var demoChk =  document.getElementById("demoCheck");
			var demoValue ="0";
			if(demoChk.checked) {
				demoValue="1";
			}
			//Vijay end
			
			//alert(serviceId);
			//var val = document.forms[0].chk.value;		  	
			// effectiveDate[0]=document.getElementById('effDisconnectionDate_'+val).value;	
			 //alert("effctive Date"+document.getElementById('effDisconnectionDate_'+val).value);
			
			//vijay. here jsData value is modifiying. Adding another argument "chkIsDemo"
				
					
					var jsData =			
						    {
							serviceIdString:ServiceIds,						
							hdnOrderNo:hdnOrderNo,
							changeTypeId:changeTypeId,
							subChangeTypeId:subChangeTypeId,											
							effDate:effectiveDate,
							stdReasonId:resaonId,
							stdReasonName:reasonName,
							remarks:document.getElementById('remarks').value,					
							chkIsDemo:demoValue,
							roleId:roleid
							
							};			
				jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");			
				try{
				 result = jsonrpc.processes.SaveServiceProductType(jsData);
				 }
				 catch(e)
				 {
				 alert("exception     " + e);
				 }
		
			 
			 if(result.msgOut != "" && result.msgOut != null)
		       {
		       		saveSuccess=true;
		       		alert(result.msgOut);
		       		
		       }
		       else
		       {
		       		alert("Data Not Saved !!");
		       }
		       if(saveSuccess==true)
		       {
		       		var callerWindowObject = dialogArguments;
					callerWindowObject.document.searchForm.hdnShowTree.value = "true";
					callerWindowObject.document.searchForm.hdnServiceNo.value =ServiceIds;	
					callerWindowObject.document.searchForm.logicalSINo.value =logicalSINo;
					callerWindowObject.document.searchForm.hdnOrderNo.value =hdnOrderNo;
					// [00044] Start
					//callerWindowObj.document.searchForm.changeType.value = document.getElementById("txtChangeType").value;	
					callerWindowObject.document.searchForm.hdnSubchangeType.value = subChangeTypeId;						
					// [00044] End
					//var flag=5;	
					//callerWindowObj.ViewServiceTree(flag);
					callerWindowObj.countService=1;
    				//callerWindowObj.drawTable();
    				//callerWindowObj.DrawTable('SERVICENO','SERVICELINETABLE');
    				//Start [005]
    				callerWindowObj.checkpage();
					//End [005]
					window.close();		       
		       }
 
 }
 //start[002]
document.onkeydown=checkKeyPress;
function checkKeyPress()
{
	if (event.ctrlKey && event.keyCode == 83) {						
				event.keyCode=4; 
				SaveService();
				document.getElementById("saveButton").tabIndex = -1;
				document.getElementById("saveButton").focus();				   				   						
   }   
}
//end[002]

//vijay start
function IsDemo(){
	var demoChk =  document.getElementById("demoCheck");
	if(demoChk.checked){
		 alert("You are going to convert selected services in Demo");
	}
}
//vijay end

</script>
 <style sr type="text/css">
  #browser {
    font-family: Verdana, helvetica, arial, sans-serif;
    font-size: 100%;
  }
  </style>
  <script>
 /* $(document).ready(function()
 {
 	$("#browser").treeview();
  }); */
  </script>
</head>
 <!-- [003] Start -->	
 <!-- [00044] Start -->	
<body onload="setSubChangeType();populateReasonForChange();SelectLogicalSINo('undefined');">
<!-- [00044] End -->	
<!-- [003] End -->
<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
<html:form action="/ChangeOrderAction" styleId="SNPType" method="post">
<bean:define id="formBean" name="newOrderBean"></bean:define>
<html:hidden property="hdnServiceNo"></html:hidden>
	<div class="head"> <span>Service and Product</span> </DIV>
		<fieldset class="border1">
			<legend> <b>Service and Product</b> </legend>
		    <div class="scroll" style="height:100%">
			    
		    
			<table width="100%"  border="1" id="tabServiceNProduct" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			  <tr class="grayBg">
					<td align="left" class="columnFontSmall" width="30%">
									Sub Change Type
					</td>
					<!--<td align="left" colspan="3">
					<select onfocus='getTip_DD(this,this.selectedIndex,this.name)' name="txtChangeType" id="txtChangeType" style="width:200px;float:left" class="dropdownMargin" onchange="changeRatereneval();" >
					   <option value="0">Select Sub Change Type</option>
				        </select>
					</td>-->
				<!-- [00044] Start -->	
				<td align="left" width="70%" colspan="3">
					<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" name="txtSubChangeTypeName" id="txtSubChangeTypeName" style="width:200px;float:left" class="inputBorder1" readonly="true" />
					<input type="hidden" name="hdnSubChangeTypeId" id="hdnSubChangeTypeId"/>
				</td>
				<!-- [00044] End -->	
				
				
				<input type="hidden" name="hdnAccountId" id="hdnAccountId" />
				<input type="hidden" name="hdnOrderNo" id="hdnOrderNo" />
				<input type="hidden" name="hdnChangeType" id="hdnChangeType" />
				<input type="hidden" name="hdnSubChangeType" id="hdnSubChangeType" />				
                <input type="hidden" name="serviceId" id="serviceId" />
                <input type="hidden" name="issuspended" id="issuspended" />
                <input type="hidden" name="isdisconnected" id="isdisconnected" />
 <!-- vijay start. hiddine variable for recognize the demo order or not-->               
                <input type="hidden" name="hdnOrderType" id="hdnOrderType" />
 <!-- vijay end -->                               
               
				</tr>
			   <tr class="grayBg">
			        <td align="left" class="columnFontSmall" colspan="1" width="25%" >
									Customer Logical SI no.
					</td>
					<td  align="center" width="25%">
					
					     <input type="hidden"  class="inputBorder1" name="logicalSINo" id="logicalSINo" value="" readonly="true"  style="width:190px;float:left;">
						  <div class="searchBg1" ><a href="#" onClick="SelectLogicalSINo('undefined')">...</a></div>
					</td>
					<td align="left" class="columnFontSmall" width="25%" >
									IRN
					</td>
					<td  align="center"width="25%">
					
					     <input type="hidden"  class="inputBorder1" name="hdnIRN" id="hdnIRN" value="" readonly="true"  style="width:190px;float:left;">
						 <div class="searchBg1" ><a href="#" onClick="SelectLogicalSINo('IRN')" title="Select IRN Number">...</a></div>
					</td>					
				</tr>					
			</table>
			
			</div>
			<div id="showFiledsAfterSISelection" style="display: none;">
				<table id="myTable" width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
					<tr>
					<td align="left" class="columnFontSmall" colspan="1" width="30%">
<!-- 						Effective Date -->
					</td>
					<%SimpleDateFormat sdf_Date=new SimpleDateFormat("dd/MM/yyyy");%>
					<td align="left" colspan="3">
					
			<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" style="width:80px;" class="inputBorder1" id="effectiveDate" name="effectiveDate" value="" /><%-- <%=sdf_Date.format(new Date(System.currentTimeMillis()))%> --%>
			<a href="javascript:show_calendar(SNPType.effectiveDate);" class="borderTabCal"><img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" alt="Loading..."style="vertical-align: bottom"/></a><%-- value="<%=sdf_Date.format(new Date(System.currentTimeMillis()))%>" --%> 
					</td>
					</tr>
					<tr>
					<td align="left" class="columnFontSmall" colspan="1" width="30%">
						Remarks
					</td>
					<td>
						<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" id= "remarks"  class="inputBorder3" onblur="ValidateTextField(this,path,'Reason for change')" />
					</td>
					</tr>
					<tr>
					<td align="left" class="columnFontSmall" colspan="1" width="30%">
						Reason For Change
					</td>
					<td>
						<!--<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" id= "changeReason"  class="inputBorder1" onblur="ValidateTextField(this,path,'Reason for change')"/>-->
						<select onfocus='getTip_DD(this,this.selectedIndex,this.name)' name="changeReason" style="width:150px" id="changeReason" class="dropdownMargin" >
						<option value="0">Select Reason for Change</option>
						</select>
					</td>
					</tr>
					<tr>
					<td>
						Selected LSIs
					</td>
					<td>
						<textarea id="lsi" readonly="readonly"></textarea> 
				<!-- Vijay start -->	
						&nbsp;&nbsp;Is Demo 
						<input name="demoCheck" id="demoCheck" type="checkbox" value="" onclick="IsDemo()"  />
				<!-- vijay end -->		
					</td>
					</tr>
					<tr align="center">
					<td width="30%" align="left"></td>
		 			<td   align="center" colspan="3" style="text-align: center;">
		 				<span style="text-align: center;">
							<div class="searchBg" onclick="SaveService()" id="saveButton" align="left">
								<a align="center" href="#">Save</a></div>
						</span>
					</td>
					
					
					</tr>
					</table>
					</div>
		</fieldset>
		
</html:form>
</body>

<script type="text/javascript">
tableDrawing();
</script>
</html>