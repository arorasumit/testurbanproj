<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<html>
<head>
<title>ViewOrder</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
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
function goToHome()
{
	document.forms[0].action="<%=request.getContextPath()%>/defaultAction.do?method=goToHome";
	showLayer();
	document.forms[0].submit();
}

var counter = 1;
function changeTab(tabVal,view1,view2,view3,view4,view5)
{
	document.getElementById('hdnTabVal').value=tabVal;
	document.getElementById('Page_1').style.display=view1;
	document.getElementById('Page_2').style.display=view2;
	document.getElementById('Page_3').style.display=view3;
	document.getElementById('Page_5').style.display=view4;
	document.getElementById('Page_6').style.display=view5;

}

function drawTable()
{
   var tr, td, i, j, oneRecord;
   var test;
   mytable = document.getElementById('ServiceList');
  
   var iCountRows = mytable.rows.length;
  
   for (i = 1; i <  iCountRows; i++)
   {
       mytable.deleteRow(1);
   }   
   
    var jsData =			
    {
		poNumber:document.getElementById('poNumber').value
	};
    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    test = jsonrpc.processes.poulateServiceList(jsData);
      
    for (i = 0 ; i < test.list.length; i++)
    {
		var str;
		var tblRow=document.getElementById('ServiceList').insertRow();
				
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str="<input name='chkService' id='chkService' type='radio' value='' onclick=fncServiceTreeView()>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str="<input name='txtServiceNo"+counter+"' type='text' value='"+ test.list[i].serviceId +"' class='inputBorder1' readonly='true' size='5'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<input name='txtServiceName"+counter+"' value='"+ test.list[i].serviceTypeName +"' class='inputBorder1' type='text' readonly='true'> <input name='txtServiceTypeID"+counter+"' value='"+ test.list[i].serviceTypeId +"' type='hidden' readonly='true'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
			
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<input name='txtServiceSubTypeName"+counter+"' value='"+ test.list[i].serviceSubTypeName +"' class='inputBorder1' type='text' readonly='true'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
			
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<div class='searchBg1'><a href='#' onclick=popServiceAttribute('ServiceAttributes',"+ counter +",'"+ test.list[i].serviceId +"')>...</a></div>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str="<div class='searchBg1'><a href='#' onclick=serviceProductAttribute("+ counter +")>...</a></div>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		counter++;
    }
}

function popServiceAttribute(url,cntr,serviceId)
{
	//logicalSINo=document.getElementById("txtServiceName"+id).value+"-"+document.NewOrder.txtAccountNo.value+"-"+serviceID
	var serviceId ;
	if(cntr==0)
	{
		serviceId = document.getElementById("txtServiceNo").value
		
		if(document.getElementById("txtServiceName").value=="")
		{
			alert("Please Select A service Type");
			return false;
		}
		else
		{
			var path = "<%=request.getContextPath()%>/selectservice.do?method=goToviewServiceAttribute&txtServiceID="+ cntr +"&serviceID="+serviceId+"";
			//url = url + "?txtServiceID="+cntr+"&serviceID="+serviceId;
			window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:350px");
		}
	}
	else
	{
		serviceId = document.getElementById("txtServiceNo" + cntr).value
		if(document.getElementById("txtServiceName"+cntr).value=="")
		{
			alert("Please Select A service Type");
			return false;
		}
		else
		{
			var path = "<%=request.getContextPath()%>/selectservice.do?method=goToviewServiceAttribute&txtServiceID="+ cntr +"&serviceID="+serviceId+"";
			window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:350px");
		}
	}
}
function popTaskStatus(id,ownername) 
{
	
	var frm=document.getElementById('searchForm');
	frm.searchTaskId.value = id;
	frm.searchTaskOwner.value = ownername;
	
	var path = "<%=request.getContextPath()%>/viewOrderAction.do?methodName=fetchNotes&taskId="+id;
   	window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
 	//window.open(path);
}

function submitParent()
{
	var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/viewOrderAction.do?methodName=viewOrder&orderNo="+document.getElementById('orderNo').value;
	 showLayer();
	myForm.submit();
}

function popTaskAction(id,ownerId) 
{
	
	var frm=document.getElementById('searchForm');
	frm.searchTaskId.value = id;
	frm.searchTaskOwnerId.value = ownerId;
	var path = "<%=request.getContextPath()%>/viewOrderAction.do?methodName=TaskAction";
   	window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
 
}
function showNotes(taskId,status)
{

   var sessionId='<%=request.getSession().getId() %>';	
   mytable = document.getElementById('tabNotes');
  
   var iCountRows = mytable.rows.length;
  
   for (i = 1; i <  iCountRows; i++)
   {
       mytable.deleteRow(1);
   }   
	var jsData =			
    {
		taskID:taskId
	};
	

    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    
    var retVal = jsonrpc.processes.ViewNotes(jsData,sessionId);
    
    
    for (i = 0 ; i < retVal.list.length; i++)
    {
		var str;
		var tblRow=document.getElementById('tabNotes').insertRow();
				
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=retVal.list[i].notesType
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=retVal.list[i].notesMeaning
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=retVal.list[i].createdBy
		CellContents = str;
		tblcol.innerHTML = CellContents;

		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str=retVal.list[i].createdDate
		CellContents = str;
		tblcol.innerHTML = CellContents;

	
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		str="<div class='deleteBg1'><a href='#' onclick=deleteNote("+ retVal.list[i].taskNoteId +","+ retVal.list[i].taskID +",'"+ escape(status) +"')>...</a></div>";
		var ownerId = <%=SessionObjectsDto.getInstance().getUserRoleId() %>;

		if(status=="Open" && ownerId ==retVal.list[i].ownerId)
			CellContents = str;
		else
			CellContents = "";	
		tblcol.innerHTML = CellContents;

	}	
	
	if(retVal.list.length==0)
	{
		var tblRow=document.getElementById('tabNotes').insertRow();
				
		var tblcol=tblRow.insertCell();
		tblcol.align = "Center";
		tblcol.vAlign="top";
		tblcol.colSpan ="5";
		CellContents = "No Notes Found";
		tblcol.innerHTML = CellContents;
	}
}

function deleteNote(searchtaskNotesId,taskId,status)
{
	if (confirm("Are you sure you want to delete this note")) 
	{
    	
	var jsData =			
    {
		taskNoteId:searchtaskNotesId
	};
	
	    jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    var retVal = jsonrpc.processes.DeleteNotes(jsData);
	    alert(retVal.msgOut);
	    showNotes(taskId,unescape(status));
    }
    
}

function serviceProductAttribute(ctrlNo) 
{
	document.getElementById('hdnserviceid').value = document.getElementById('txtServiceNo'+ctrlNo).value;
	var serviceNo=document.getElementById('txtServiceNo'+ctrlNo).value;
	var serviceTypeId=document.getElementById('txtServiceTypeID'+ctrlNo).value;
	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=getToProductAttDetail&hdnserviceid="+serviceNo+'&hdnserviceTypeId='+serviceTypeId;		
	window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
}

function ViewServiceTree()
{
		var myForm=document.getElementById('searchForm');
		myForm.action="<%=request.getContextPath()%>/NewOrderAction.do?method=getServiceTreeview";
		 showLayer();
		myForm.submit();
}

function fnViewProductCatelog(url)
{
	var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=productCatelogforUpdate&"+ unescape(url) +"&viewModify=2";
	window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
}

var treeNodes;
function getTree(treeView,nextNode,nextLabel,nextUrl)
{
	
	var getNode="";
	var getNextNode="";
	var str="";
	var i;
	//str = "<li><span class='folder'><a href='#' onclick=fnViewProductCatelog(\'"+ escape(nextUrl) +"\');>"+ nextLabel + "</a></span>"	
	str = "<li><span class='folder'><input type='radio' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
			
	for(i=0;i<treeView.lstTreeItems.list.length;i++)
	{
		if(nextNode==treeView.lstTreeItems.list[i].serviceProductParentId)
		{
//		 	alert(treeView.lstTreeItems.list[i].treeViewURL)
		 	
		 	str=str+"<ul>"+getTree(treeView,treeView.lstTreeItems.list[i].serviceProductChildId,treeView.lstTreeItems.list[i].treeViewText,treeView.lstTreeItems.list[i].treeViewURL)+"</ul>";
		 	
		}
	}
	str=str+"</li>";
	//alert('AT the End '+ nextNode)
	return str;
}
//var iCountTreeNode=0;	
function TreeNode(nextNode,nextUrl , nextLabel)
{
	if(unescape(nextUrl)=="")
	{
	//	iCountTreeNode=2;
		alert('Total Products in ' + unescape(nextLabel) + ' is/are ' + getTreeNodeCount(treeNodes,nextNode));
	}	
	else
	{
		//iCountTreeNode=0;	
		alert('Total Products in ' + unescape(nextLabel) + ' is/are ' + getTreeNodeCount(treeNodes,nextNode));
		fnViewProductCatelog(nextUrl);
	}	
}

function getTreeNodeCount(treeView,nextNode)
{
	var i;
	var localCount=0;
	for(i=0;i<treeView.lstTreeItems.list.length;i++)
	{
		if(nextNode==treeView.lstTreeItems.list[i].serviceProductParentId)
		{
			localCount = localCount + Number(getTreeNodeCount(treeView,treeView.lstTreeItems.list[i].serviceProductChildId));
		}
	}
	localCount = localCount +1;
	return localCount;
}

//Saurabh
function fncServiceTreeView()
{
	var newCounter=1;
	var chkLength=document.forms[0].chkService.length;
	if(chkLength==undefined)
	{
		if(document.forms[0].chkService.checked==true)
		{
			if(document.forms[0].txtServiceNo1.value=="")
			{
				alert("Pls. Enter Service");
				return false;
			}
			else
			{
				document.getElementById('browser').innerHTML= "";
			    var jsData = 
			    {
			    	serviceId:document.forms[0].txtServiceNo1.value,
			    	orderNumber:document.getElementById('poNumber').value
			    };
			  	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
				 var treeView = jsonrpc.processes.ViewServiceTreeView(jsData);
				 
				 //////
				 treeNodes = treeView;
				var url ="";
				var nodeText ="";
				var parentNodeId;
				
				for(i=0;i<treeView.lstTreeItems.list.length;i++)
				{
					if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
					{
					 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
					 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
					 	break;
					}
				}
				 
				//alert(document.forms[0].txtServiceNo1.value); 
				 
				var treeString=getTree(treeView,parentNodeId,nodeText,"");

			 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
			 	$("#browser").treeview({
			 		add: branches
			 	});
				 ////////////////
				 
				 /*
				var treeString="";
			  	for(var j=0;j<treeView.lstTreeItems.list.length;j++)
			  	{
					treeString = treeString + "<li><span class='file'><a href='#' onclick=fnViewProductCatelog(\'"+ escape(treeView.lstTreeItems.list[j].treeViewURL) +"\');>"+ treeView.lstTreeItems.list[j].treeViewText + "</a></span></li>"    		
			   	}	
			 	var branches = $("<li><span class='folder'>" + document.forms[0].txtServiceName1.value + "</span><ul>" + 
			 		treeString +
			 		"</ul></li>").appendTo("#browser");
			 	$("#browser").treeview({
			 		add: branches
			 	});*/
			}
		}
	}
	else
	{
		for(i=0;i<chkLength;i++,newCounter++)
		{
			var myform=document.getElementById("searchForm")
			if(myform.chkService[i].checked==true)
			{
				if(document.getElementById('txtServiceNo'+newCounter).value=="")
				{
					alert("Pls. Enter Service For " + newCounter + " Element.");
					return false;
				}
				else
				{
					document.getElementById('browser').innerHTML= "";
				    var jsData = 
				    {
				    	serviceId:document.getElementById('txtServiceNo'+newCounter).value,
				    	orderNumber:document.getElementById('poNumber').value
				    };
				  	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
					 var treeView = jsonrpc.processes.ViewServiceTreeView(jsData);
					 
					 /////
					 treeNodes = treeView;
						var treeString="";
						var url ="";
						var nodeText ="";
						var parentNodeId;
						
						for(i=0;i<treeView.lstTreeItems.list.length;i++)
						{
							if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
							{
							 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
							 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
							 	break;
							}
						}
						 
						//alert(document.forms[0].txtServiceNo1.value); 
						 
						var treeString=getTree(treeView,parentNodeId,nodeText,"");
		
					 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
					 	$("#browser").treeview({
					 		add: branches
					 	});
						break;
					 
					 /////////
					 /*
					var treeString="";
				  	for(var j=0;j<treeView.lstTreeItems.list.length;j++)
				  	{
						treeString = treeString + "<li><span class='file'><a href='#' onclick=fnViewProductCatelog(\'"+ treeView.lstTreeItems.list[j].treeViewURL +"\');>"+ treeView.lstTreeItems.list[j].treeViewText + "</a></span></li>"    		
				   	}	
				 	var branches = $("<li><span class='folder'>" + document.getElementById('txtServiceName'+newCounter).value + "</span><ul>" + 
				 		treeString +
				 		"</ul></li>").appendTo("#browser");
				 	$("#browser").treeview({
				 		add: branches
				 	});
					break;*/
				}
			}
		}
	}
}

function fnPublish()
{
	//alert("Data of Order Entry will be inserted in Fx billing engine.");
	var myForm=document.getElementById('searchForm');
    var poNum=document.searchForm.poNumber.value;
    //alert(poNum);
	var url = "<%=request.getContextPath()%>/viewOrderAction.do?methodName=fnPublishOrder";
	url = url + "&PONum="+poNum;
	myForm.action=url;
	 showLayer();
	myForm.submit();
}


function fnBillingTrigger3(chiWindow)
{
              // alert(2);
               chiWindow.close();
               fnBillingTrigger();
}

function fnBillingTrigger()
{
	poNum=document.searchForm.poNumber.value;
	var url = "<%=request.getContextPath()%>/viewOrderAction.do?methodName=fnBillingTrigger";
	{
		url = url + "&PONum="+poNum;
		//features="scrollbars=yes,resizable=yes,width=800,height=540,left=100,top=100";
		//window.open(url,'xyz',features);
		window.showModalDialog(url,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
	}
}

function fnChargeSummary()
{
	poNum=document.searchForm.poNumber.value;
	var url = "<%=request.getContextPath()%>/viewOrderAction.do?methodName=fnChargeSummary";
	{
		url = url + "&PONum="+poNum;
		//features="scrollbars=yes,resizable=yes,width=800,height=540,left=100,top=100";
		//window.open(url,'xyz',features);
		window.showModalDialog(url,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
	}
}
// function is used for fetch zone list according to region
function fnGetZoneList()
{
    
   var regionId=document.getElementById("region").value;	
   var test;
   var combo = document.getElementById("zone");	
   var iCountRows = combo.length;
  
   for (i = 1; i <  iCountRows; i++)
   {
       combo.remove(1);
   }
	
    var jsData =			
    {
		regionId:regionId
	};
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    test = jsonrpc.processes.populateZoneList(jsData);   
    
	    for(j=0;j<test.list.length;j++)
	    {
	    	var option = document.createElement("option");
	   		option.text = test.list[j].zoneName;
			option.value = test.list[j].zoneId;
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
 
 
function fnGetSelectedZone()
{
  var regionId=document.getElementById("region").value;	
    var test;
   var combo = document.getElementById("zone");	
   var iCountRows = combo.length;
  
   for (i = 1; i <  iCountRows; i++)
   {
       combo.remove(1);
   }
	
    var jsData =			
    {
		regionId:regionId
	};
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    test = jsonrpc.processes.populateZoneList(jsData);   
    var selectedZone = document.getElementById('zone_1').value;
	    for(j=0;j<test.list.length;j++)
	    {
	    	var option = document.createElement("option");
	   		option.text = test.list[j].zoneName;
			option.value = test.list[j].zoneId;
			try 
			{
			combo.add(option, null); //Standard
			
			}
			catch(error) 
			{
				combo.add(option); // IE only
			}
	    } 
	    combo.value = selectedZone;
}
//function used for validate extended attribute details and region,zone fields and submit form
//created by anil
function validate()
{
	if(document.getElementById('region').value=="0")
	{
		alert('Please select region');
		return false;
	}
	if(document.getElementById('zone').value=="0")	
	{
		alert('Please select zone');
		return false;
	}
	var countAttributes=document.getElementById('attCount').value;
	for(i=1;i<=countAttributes;i++)
	{
		if(document.getElementById('Mandatory_'+i).value=="Y")
		{ 
			if(document.getElementById('AttributeVal_'+i).value=="")
			{
				alert("Please Input Extended Attributes Details!!");
				document.getElementById('AttributeVal_'+i).focus();
				return false;
			}
		}
	}
	
	var myForm=document.getElementById('searchForm');
	myForm.action="<%=request.getContextPath()%>/viewOrderAction.do?methodName=updateMainViewOrder";
	 showLayer();
	myForm.submit();
	
}


</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body onload="drawTable();fnGetSelectedZone()">
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
	<html:form action="/NewOrderAction" styleId="searchForm" method="post">
	<table width="60" border="0" cellpadding="0" cellspacing="0">
	<tr>
		<td width="20" align="left" ><img src="gifs/top/home.gif" onClick="goToHome();" title="Home"></img></td>
	</tr>
</table>
	
		<bean:define id="formBean" name="newOrderBean"></bean:define>
		<html:hidden property="hdnServiceCounter"/>	
		
		<!-- Start: For Billing Trigger -->
			<input type="hidden" id="hdnBillingTrigger" name="hdnBillingTrigger" value='<bean:write name="ViewOrderLogicalFormBean" property="billingOrderNo"/>'>
			<input type="hidden" id="hdnBillingOrderNo" name="hdnBillingOrderNo" value='<bean:write name="ViewOrderLogicalFormBean" property="strBillingTrigger"/>'/>	
   		<!-- End: For Billing Trigger -->
   		
		<input type="hidden" id="hdnserviceid" name="hdnserviceid"/>	
		<div class="head"> <span>View Order Entry </span> </div>
		<div class="error" align="center">
			<logic:present name="validation_errors">
				<logic:iterate id="error_row" name="validation_errors" scope="request">
						<font color="red"><bean:write name="error_row" /></font><BR>
				</logic:iterate>
			</logic:present>
		</div>
		<div class="error" align="center">
			<logic:notEmpty name="ViewOrderLogicalFormBean" property="message">
			<tr>
				<td align="center" colspan="2"><b>
				<font color="red">
					<bean:write name="ViewOrderLogicalFormBean" property="message"/>
				</font></b>
				</td>
			</tr>
			</logic:notEmpty>
		</div>
		<table width="100%"  border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="40%" valign="top">
					<fieldset class="border1">
						<legend> <b>Customer</b> </legend>
						<logic:present name="accountDetailsBean" scope="request">
							<logic:notEmpty name="accountDetailsBean" scope="request">
								<logic:iterate id="row1"  name="accountDetailsBean" indexId="index_report">
									<table border="0" cellspacing="0" cellpadding="1" style="margin-top:7px ">
										<tr>
											<td align="left" style="font-size:9px"> A\C No </td>
											<td>
												<html:text name="row1" property="accountID" styleClass="inputBorder1" style="width:50px;float:left;" readonly="true"></html:text>												
											</td>
											<td align="left" style="font-size:9px;" nowrap>Party Name</td>
											<td><html:text name="row1"  property="accountName" styleClass="inputBorder1" style="width:100px;" readonly="true"></html:text></td>
										</tr>
										<tr>
											<td align="left" style="font-size:9px" nowrap> A\C Mgr </td>
											<td><html:text name="row1" property="accountManager" styleClass="inputBorder1" style="width:100px;" readonly="true"></html:text></td>
											<td align="left" style="font-size:9px"> Phone No </td>
											<td><html:text name="row1" property="accphoneNo" styleClass="inputBorder1" style="width:100px;" readonly="true"></html:text></td>		
										</tr>
										
										<tr>
											<td align="left" style="font-size:9px" nowrap>Project Mgr </td>
									        <td>
									        <html:text name="PMBean" property="projectManager" styleClass="inputBorder1" style="width:100px;" readonly="true"></html:text>								        
									        </td>
											<td align="left" style="font-size:9px"> LOB </td>
											<td><html:text name="row1" property="lob" styleClass="inputBorder1" style="width:100px;" readonly="true"></html:text></td>
										</tr>
									</table>
								</logic:iterate>
							</logic:notEmpty>
						</logic:present>
					</fieldset>
				</td>
				<td width="60%" valign="top">
					<fieldset class="border1">
						<legend> <b>Order </b> </legend>
						<logic:present name="accountDetailsBean" scope="request">
							<logic:notEmpty name="accountDetailsBean" scope="request">
								<logic:iterate id="row1"  name="accountDetailsBean" indexId="index_report">
									<table border="0" cellspacing="0" cellpadding="1" style="margin-top:7px ">
										<tr>
											<td align="left" style="font-size:9px"> Order Type </td>
											<td><html:text property="orderType" name="row1" readonly="true" styleClass="inputBorder1" style="width:100px;" readonly="true"></html:text> </td>
											<td align="left" style="font-size:9px">Order Date</td>
											<td><html:text property="orderDate" name="row1" readonly="true" styleClass="inputBorder1" style="width:100px;" readonly="true"></html:text></td>
											<td align="left" style="font-size:9px">Source</td>
											<td>
												<html:text property="sourceName" name="row1" styleClass="inputBorder1" style="width:70px; float:left;" readonly="true"></html:text>												
											</td>
											</tr>
											<tr>
											<td align="left" style="font-size:9px"> Quote No </td>
											<td nowrap><html:text property="quoteNo" name="row1" styleClass="inputBorder1" style="width:100px;" readonly="true"></html:text></td>
											<td align="left" style="font-size:9px"> Currency </td>
											<td>
												<html:text property="currencyCode" name="row1" styleClass="inputBorder1" style="width:70px; float:left;" readonly="true"></html:text>
												<html:hidden property="currencyID" name="row1"></html:hidden>																								
											</td>
										</tr> 
										<tr>
											<td align="left" style="font-size:9px">Order No</td>
											<td>
												<html:text property="poNumber" name="row1" readonly="true" styleClass="inputBorder1" style="width:100px;" readonly="true"></html:text> 
												<html:hidden property="hdnOrderNo"/>
											</td>
											<td align="left" style="font-size:9px"> Status </td>
											<td>
												<html:select property="status">
													<html:option value="1">Incomplete</html:option>
													<html:option value="2">Compelte</html:option>
												</html:select>
											</td>
											<td align="left" style="font-size:9px"> Stage </td>
											<td><html:text property="stageName" readonly="true" styleClass="inputBorder1" style="width:100px;" name="row1"></html:text> </td>
										</tr>
									</table>
								</logic:iterate>
							</logic:notEmpty>
						</logic:present>
						</fieldset>
					</td>
				</tr>
			</table>
			<table width="100%"  border="0" cellspacing="0" cellpadding="0">
				<tr>
					<td width="50%">
						<OL id="toc" class="tabledata">
							<li onClick="changeTab(1,'block','none','none','none','none')"><a href="#" >MAIN</a></li>
							<li onClick="changeTab(2,'none','block','none','none','none')"><a href="#">CONTACT</a></li>
							<li onClick="changeTab(3,'none','none','block','none','none')"><a href="#">PO DETAILS</a></li>
							<li onClick="changeTab(5,'none','none','none','block','none')"><a href="#">LINES</a></li>
							<li onClick="changeTab(6,'none','none','none','none','block')"><a href="#">APPROVAL</a></li>
							<input type="hidden" name="hdnTabVal" value="1">				
						</OL>
					</td>
					<td width="50%" align="right">
						<logic:equal name="ViewOrderLogicalFormBean" property="isOrderPublished" value="1">
							<html:button property="" value="Publish" onclick="javascript:fnPublish();" disabled="true"/>&nbsp;
						</logic:equal>	
						<logic:equal name="ViewOrderLogicalFormBean" property="isOrderPublished" value="2">
							<html:button property="" value="Publish" onclick="javascript:fnPublish();"/>&nbsp;
						</logic:equal>
						<logic:equal name="ViewOrderLogicalFormBean" property="isBillingTriggerReady" value="1">
							<html:button property="" value="Billing Trigger" onclick="javascript:fnBillingTrigger();"/>&nbsp;
						</logic:equal>
						<logic:equal name="ViewOrderLogicalFormBean" property="isBillingTriggerReady" value="2">
							<html:button property="" value="Billing Trigger" onclick="javascript:fnBillingTrigger();" disabled="true" />&nbsp;
						</logic:equal>
						<html:button property="" value="Charge Summary" onclick="javascript:fnChargeSummary();"/>&nbsp;&nbsp;
					</td>
				</tr>
			</table>
			<!--  FOR tab 1 Start -->
			<div style="margin:0px 2px 0px 2px;display: block" id="Page_1" class="content" >
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="100%" valign="top">
							<jsp:include flush="true" page="viewExtendedAttributes.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</div>
			<!--  FOR tab 1 Ends -->
			<!--  FOR tab 2 Start -->
			<div style="margin:0px 2px 0px 2px;display: none" id="Page_2" class="content">
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td valign="top">
						    <jsp:include flush="true" page="viewContact.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</div>
			<!--  FOR tab 2 End -->
			<!--  FOR tab 3 Start -->
			<div style="margin:0px 2px 0px 2px;display: none" id="Page_3" class="content">
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td valign="top">
						    <jsp:include flush="true" page="ViewPODetails.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</div>
			<!--  FOR tab 3 End -->
			<!--  FOR tab 5 Start -->
			<div style="margin:0px 2px 0px 2px;display: none" id="Page_5" class="content">
				<table width="114%" border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="4%" align="left" ><img src="gifs/middle/1.gif"></img></td>
						<td width="4%" align="left" ><img src="gifs/middle/2.gif"></img></td>
						<td width="4%" align="left"><img src="gifs/middle/4.gif"></td>
						<td width="4%" align="left" ><img src="gifs/middle/5.gif"></img></td>
						<td width="4%" align="left" ><img src="gifs/middle/6.gif"></td>
						<td width="4%" align="left" ><img src="gifs/middle/7.gif"></td>
						<td width="4%" align="left" ><img src="gifs/middle/8.gif"></td>
						<td width="4%" align="left" ><img src="gifs/middle/9.gif"></td>
						<td width="5%" align="left" ><img src="gifs/middle/10.gif"></td>
						<td width="5%" align="left" ><img src="gifs/middle/11.gif"></td>
						<td width="2%" align="left"><input type="text" class="inputBorder1" size="1"></td>
						<td width="8%" align="left" >&nbsp;Access Filter</td>
						<td width="4%" align="left" ><input type="text" class="inputBorder1" size="1"></td>
						<td width="5%" align="left" ><div class="searchBg"><a href="#" >Go</a></div></td>
						<td width="18%" align="left" ><div class="searchBg"><a href="#" >Cancel Service</a></div></td>
					</tr>
				</table>
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td valign="top" width="60%">
							<jsp:include flush="true" page="viewServiceType.jsp"></jsp:include>
						</td>
						<td width="40%" valign="top">
							<fieldset class="border1" style="width:50%">
								<legend> <b>Service Details</b> </legend>
								<table width="100%"  border="1" cellspacing="0" cellpadding="0">
									<tr>
										<td>
											<jsp:include flush="true" page="TreeView.jsp"></jsp:include>
										</td>
									</tr>
								</table>
							</fieldset>
						</td>
					</tr>
				</table>
			</div>
			<!--  FOR tab 5 End -->
			<!--  FOR tab 3 Start -->
			<div style="margin:0px 2px 0px 2px;display: none" id="Page_6" class="content">
				<table width="100%"  border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td valign="top">
						    <jsp:include flush="true" page="approval.jsp"></jsp:include>
						</td>
					</tr>
				</table>
			</div>
			<!--  FOR tab 3 End -->
		</html:form>
</body>
</html>
