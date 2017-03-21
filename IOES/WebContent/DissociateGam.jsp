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
<script type="text/javascript" src="js/formValidations.js"></script>
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

<script language="javascript" type="text/javascript">

var totalRecords=0;

function openGAMOrderList()
{	
	 
	  	 var str;		   	    	      
	   	var frm=document.getElementById('dissociateGAM');   
		var gam_id=document.getElementById('gam_id').value;    	    
	   	var mytable = document.getElementById('DissociateTabGAMList');	
	   	var iCountRows = mytable.rows.length;
	   	for (i = 1; i <  iCountRows; i++)
	   	{
	       mytable.deleteRow(1);
	   	} 
		try{
		   var jsData =
				{										
					gam_id:gam_id
				};		  
			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			var gamOrderList = jsonrpc.processes.getGamOrderList(jsData);
			}
			catch(e)
			{
				alert("Exception :"+e);
			}
			iTotalLength=gamOrderList.list.length;
			totalRecords=iTotalLength;			
		var colors=new Array("normal","lightBg");		
	 //***************************************************************************************************
	 //Populating Order List According to Global Account Manager (GAM)
	 if(iTotalLength>0){
	 		for (i = 0; i <  iTotalLength; i++)
			{	
	 	
			 	var colorValue=parseInt(i)%2;
				var tblRow=document.getElementById('DissociateTabGAMList').insertRow();
				tblRow.className=colors[colorValue];						
		
				
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=gamOrderList.list[i].orderNumber;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=gamOrderList.list[i].orderDate;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=gamOrderList.list[i].stageName;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=gamOrderList.list[i].quoteNo;
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=gamOrderList.list[i].accountName;
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str=gamOrderList.list[i].crmAccountNo;
				CellContents = str;
				tblcol.innerHTML = CellContents;	
				
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";				
				str="<input name=chk id='chk"+i+"' type='checkbox'   value='"+gamOrderList.list[i].gam_id+"' />";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var  orderNumber=gamOrderList.list[i].orderNumber;
				var  quoteNo=gamOrderList.list[i].quoteNo;
				tblcol=tblRow.insertCell();
				tblcol.align = "center";
				tblcol.vAlign="top";
				str="<a href='#' onclick='viewGAMDetailsPopup("+orderNumber+")'>View GAM Details</a>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
								
			}	
																
	}	
	else
	{				   	    
		var tblRow=document.getElementById('DissociateTabGAMList').insertRow();
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

function dissociateGAMList()
{
	var frm=document.getElementById('dissociateGAM');
	var flag=false;	
	var strGAMId='';
	
	if(frm.chk==undefined)
	{
		alert("There is no Records!!")
		return false;
	}
	/*		
	if(totalRecords==1)
	{
		strGAMId=document.getElementById('chk0').value;		
		flag=true;
	}
	*/
	for(i=0;i<frm.chk.length;i++)
	{		
			if(frm.chk[i].checked==true)
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
	if(flag!=true)
	{
			alert("Please select atleast one Order No");
			return false;				
	}else{
		if(strGAMId !='')
		{
		
			var sessionid ='<%=request.getSession().getId() %>';
			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
				if(confirm('Are you Sure?'))
				{
					var successMsg=jsonrpc.processes.dissociateOrderGam(strGAMId);
					if(successMsg==0){
						alert('GAMs Dissociated Successfully!!');
						openGAMOrderList();
					}else{
						alert('GAMs Dissociation failed!!');
					}
				}	
			
				
		}
	}
	
	checkFieldLength();
}
function checkFieldLength()
{
	var mytable=document.getElementById('DissociateTabGAMList');
	var totalRowLength=mytable.rows.length;
	if(totalRowLength > 1 )
	{
		document.getElementById('btnDissociateGAM').disabled=false;
	}else{
		document.getElementById('btnDissociateGAM').disabled=true;
	}
}
function viewGAMDetailsPopup(orderNo)
{
	
		var quoteNo=quoteNo;	    				    			   	
	    var orderNumber=orderNo;
	    var isInView=111;	
	
		var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=goToViewGAM&orderNo="+orderNumber+"&quoteNo="+quoteNo+"&isInView="+isInView;									
		window.showModalDialog(path,window,"status:false;dialogWidth:800px;dialogHeight:550px"); 
	
}

function goToHome()
{

	var myForm=document.getElementById('dissociateGAM');
	myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=goToHomeAfterClosing";
	showLayer();
	myForm.submit();
}




</script>
</head>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body> 
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
<html:form action="/NewOrderAction" styleId="dissociateGAM" method="post">
<div><td width="4%" align="left" ><img src="gifs/top/home.gif" onClick="goToHome()" alt="Home" title="Home"></img></td></DIV>
	<div class="head"> <span>Dissociate Global Account Manager (GAM)</span> </DIV>	
		<fieldset class="border1">
			<legend> <b>List of Global Account Managers</b> </legend>
	 		<table  border="1" cellspacing="0" cellpadding="0" align="center" width="90%" >
					<tr align="center">
						<logic:notEmpty name="gamList_orderAttached"  scope="request">
						<td colspan="1" align="right" style="font-size:11px">
							Select a GAM to Dissociate
						</td>
						
							<td colspan="1" align="left" class="">
								<html:select   property="gam_id" styleId="gam_id" style="width:400px;float:left;" onchange="openGAMOrderList()" >
									<html:option value="0" style="background-color: #CCCC99"  >Select a GAM to Dissociate</html:option>
									<logic:iterate id="row1" name="gamList_orderAttached" indexId="index1">
										<html:option value="${row1.gam_id}" >
											<c:out value="${row1.gam_name}" />
										</html:option>
									</logic:iterate>	
								</html:select>
							</td>
						</logic:notEmpty>
						<logic:empty name="gamList_orderAttached" scope="request">
								<tr class="alterRedBg" align="center" >
									<td colspan="14" align="center" nowrap><B>No GAM attached with any order</td>
								</tr>
							</logic:empty>
					</tr>
				</table>
	 		<logic:notEmpty name="gamList_orderAttached"  scope="request">				
	<table width="100%" border="1"  id="DissociateTabGAMList" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td width="8%" align="center">Order No</td>
				<td width="8%" align="center">Order Date</td>
				<td width="8%" align="center">Order Stage</td>
				<td width="8%" align="center">Quote No</td>
				<td width="8%" align="center">Account No</td>	
				<td width="8%" align="center">Account Name</td>
				<td width="8%" align="center">Dissociate</td>
				<td width="8%" align="center">View Gam</td>								
			</tr>										
	</table>
	
	<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
			<td width="18%" align="center" vAlign="top" colspan="12">			
			&nbsp;						
			</td>								
		</tr>
	</table>
	
	<!-- lawkus start -->
	
	<table width="100%" id="tablDissociateOrderList" border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg">
		
			<td width="18%" align="center" vAlign="top" colspan="5">			
				<input type="button"	name="btnDissociateGAM" id="btnDissociateGAM" value="DISSOCIATE GAM" width="10" onClick="dissociateGAMList()">							
			</td>	
								
		</tr>
	</table>	
	</logic:notEmpty>		
	<!-- lawkus End -->
	
</fieldset>

</html:form>
</body>
<script type="text/javascript">
</script>
</html>


	
