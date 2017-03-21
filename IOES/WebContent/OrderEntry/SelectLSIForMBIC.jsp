<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Anil Kumar		23-May-11	CSR00-05422     Session Expired code with AjaxJSON  -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="com.ibm.ioes.beans.CurrencyChangeBean"%>
<%@page import="com.ibm.ioes.forms.PagingDto"%>
<html>
<head>
<title>Clear Channel To MBIC LSI Mapping</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
//Global parameter
<%String mbiclogicalSIno=request.getParameter("mbiclogicalSIno");
String serviceProductID=request.getParameter("serviceProductID");
String productCounter=request.getParameter("productCounter");
String servicesID=request.getParameter("servicesID");
String isServiceInactive=request.getParameter("isServiceInactive");

%>
var serviceProductID='<%=serviceProductID%>';	
var mbiclogicalSIno='<%=mbiclogicalSIno%>';	
var productCounter ='<%=productCounter%>';
var servicesID='<%=servicesID%>';
var isServiceInactive='<%=isServiceInactive%>';

<%String attMasterId=request.getParameter("attMasterId");%>	
<%String ib2bOrderNo=request.getParameter("ib2bOrderNo");%>	
var attMasterId='<%=attMasterId%>';
var ib2bOrderNo='<%=ib2bOrderNo%>';
	
var callerWindowObj = dialogArguments;
var listLogicalSINumber=0;
function getInfoAndUpdate()
{
	window.close();
}
var isCCAttachWithMBIC=0;
var currentAttachedMBIC=0;
function attachDettachLSI(ccLogicaLSINo,MBICServiceID,MBICServiceProductID,isAttach)
{
	var jsData =
	{
		orderNumber:ib2bOrderNo,
		mbicServiceId:MBICServiceID,
		mbicServiceProductId:MBICServiceProductID,
		custSINo:ccLogicaLSINo,
		isAttach:isAttach
					
	};
	  
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");		
	var isDettach = jsonrpc.processes.dettachMBICLSI(jsData);
}
function getAttachDetachLSI(ccLogicaLSINo,MBICServiceID,MBICServiceProductID,isAttach)
{
	
	if(isAttach==1 )
	{
		if(isCCAttachWithMBIC==1)
		{
			if (confirm("MBIC LSI is Already Attach with other CC LSI. Do You Want Continue...")) 
				{
					document.getElementById("lblMBIC"+currentAttachedMBIC).value="";
					document.getElementById("attachButton"+currentAttachedMBIC).disabled=false;		
					document.getElementById("detachButton"+currentAttachedMBIC).disabled=true;
					
					document.getElementById("lblMBIC"+ccLogicaLSINo).value=mbiclogicalSIno;
					document.getElementById("attachButton"+ccLogicaLSINo).disabled=true;		
					document.getElementById("detachButton"+ccLogicaLSINo).disabled=false;
					callerWindowObj.document.getElementById('prodAttVal_'+productCounter).value=ccLogicaLSINo;
					isCCAttachWithMBIC=1;
					currentAttachedMBIC=ccLogicaLSINo;
				
				}
				else
				{
					return false;
				}
				
		}
		else if(isCCAttachWithMBIC==2)
		{
			alert("MBIC LSI "+mbiclogicalSIno+" Is Already Attach With Other CC LSI. Please Dettach First.");
			return false;
		}
		else
		{			
			document.getElementById("lblMBIC"+ccLogicaLSINo).value=mbiclogicalSIno;			
			document.getElementById("attachButton"+ccLogicaLSINo).disabled=true;		
			document.getElementById("detachButton"+ccLogicaLSINo).disabled=false;
			callerWindowObj.document.getElementById('prodAttVal_'+productCounter).value=ccLogicaLSINo;
			isCCAttachWithMBIC=1;
			currentAttachedMBIC=ccLogicaLSINo;
		}
		if(serviceProductID!=0)
		{
			attachDettachLSI(ccLogicaLSINo,servicesID,serviceProductID,isAttach);
			DrawLSI_TableForMBIC();
		}
	}
	else
	{	
		if (confirm("Do You Want To Continue To Dettach")) 
		{
			if(mbiclogicalSIno==document.getElementById("lblMBIC"+ccLogicaLSINo).value)
			{
				if(listLogicalSINumber!=0)
				{
					 for (j = 0; j <listLogicalSINumber.list.length; j++)
			 		 {
			 		 	if(listLogicalSINumber.list[j].isCCMapWithMBIC==0)
			 		 	{
			 		 		
							document.getElementById('attachButton'+listLogicalSINumber.list[j].custSINo).disabled=false;
							document.getElementById('detachButton'+listLogicalSINumber.list[j].custSINo).disabled=true;
			 		 	}
			 		 	else if(listLogicalSINumber.list[j].isCCMapWithMBIC!=document.getElementById("lblMBIC"+ccLogicaLSINo).value)
			 		 	{
			 		 		document.getElementById('attachButton'+listLogicalSINumber.list[j].custSINo).disabled=true;
							document.getElementById('detachButton'+listLogicalSINumber.list[j].custSINo).disabled=false;
			 		 	}	
			 		 }
				}
				
			}
			if(document.getElementById("lblMBIC"+ccLogicaLSINo).value==mbiclogicalSIno)
			{
				isCCAttachWithMBIC=0;
				callerWindowObj.document.getElementById('prodAttVal_'+productCounter).value="";
			}			
			
			
			document.getElementById("lblMBIC"+ccLogicaLSINo).value="";
			document.getElementById("detachButton"+ccLogicaLSINo).disabled=true;
			document.getElementById("attachButton"+ccLogicaLSINo).disabled=false;			
			if(serviceProductID!=0)
			{
				attachDettachLSI(ccLogicaLSINo,MBICServiceID,MBICServiceProductID,isAttach);
				DrawLSI_TableForMBIC();
			}
		}
		else
		{
			return false;
		}
		
	}
}
function DrawLSI_TableForMBIC()
{
	var i;
   	var str;
   	var sessionid ='<%=request.getSession().getId() %>';
   	var frm=document.getElementById('logicalTypes');
   	callerWindowObj = dialogArguments;	   
   	document.getElementById('tab2LogicalType').style.display="block";
   	var mytable = document.getElementById('tabLogicalType');	
   	var iCountRows = mytable.rows.length;
   	for (i = 1; i <iCountRows; i++)
   	{
       	mytable.deleteRow(1);
   	}
	sync();
	jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
		
	listLogicalSINumber = jsonrpc.processes.fetchLSINoForMBIC(attMasterId,ib2bOrderNo,sessionid);
	iTotalLength=listLogicalSINumber.list.length;
	if(listLogicalSINumber==null)
	{
		//start [001]		
		var callerWindowObj = dialogArguments;
		var myForm=callerWindowObj.document.getElementById('searchForm');				
		myForm.action="<%=request.getContextPath()%>/defaultAction.do?method=invalidsessoion";
		myForm.submit();
		alert("Session has been expired!!");		
		window.close();			
		//end [001]
	}
	if(listLogicalSINumber.list.length == 0)
  	{
    	document.getElementById('tab2LogicalType').style.display="none";	
  	}	 
	 for (i = 0; i <listLogicalSINumber.list.length; i++)
	 {
	    var MBICServiceID=listLogicalSINumber.list[i].mbicServiceId;
	    var MBICServiceProductID=listLogicalSINumber.list[i].mbicServiceProductId;
		var tblRow=document.getElementById('tabLogicalType').insertRow();
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=listLogicalSINumber.list[i].custSINo;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		if(listLogicalSINumber.list[i].isCCMapWithMBIC==0)
			str="<input type='text' readOnly='true' id='lblMBIC" + listLogicalSINumber.list[i].custSINo + "' > <input type='hidden' id='hdnMBICServiceID" + MBICServiceID + "' ><input type='hidden' id='hdnMBICServiceProductID" + MBICServiceProductID + "' >";
		else if(listLogicalSINumber.list[i].isCCMapWithMBIC==mbiclogicalSIno)
		{
			tblRow.className="rowHighlight";
			str="<input type='text' readOnly='true' id='lblMBIC" + listLogicalSINumber.list[i].custSINo + "' value='"+listLogicalSINumber.list[i].isCCMapWithMBIC+"' > <input type='hidden' id='hdnMBICServiceID" + MBICServiceID + "' ><input type='hidden' id='hdnMBICServiceProductID" + MBICServiceProductID + "' >";
		}
		else
			str="<input type='text' readOnly='true' id='lblMBIC" + listLogicalSINumber.list[i].custSINo + "' value='"+listLogicalSINumber.list[i].isCCMapWithMBIC+"' > <input type='hidden' id='hdnMBICServiceID" + MBICServiceID + "' ><input type='hidden' id='hdnMBICServiceProductID" + MBICServiceProductID + "' >";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		if(listLogicalSINumber.list[i].isCCMapWithMBIC==0)
			str="<input type='button' style='width:60px;' name='attachButton' value='Attach' id='attachButton"+listLogicalSINumber.list[i].custSINo+"'   onclick='javascript:getAttachDetachLSI("+listLogicalSINumber.list[i].custSINo+","+MBICServiceID+","+MBICServiceProductID+","+1+"); 'disabled='false' > / <input type='button' style='width:60px;' name='detachButton' value='Dettach' id='detachButton"+listLogicalSINumber.list[i].custSINo+"'   onclick='javascript:getAttachDetachLSI("+listLogicalSINumber.list[i].custSINo+","+MBICServiceID+","+MBICServiceProductID+","+0+");' disabled='true'>";
		else
			str="<input type='button' style='width:60px;' name='attachButton' value='Attach' id='attachButton"+listLogicalSINumber.list[i].custSINo+"'   onclick='javascript:getAttachDetachLSI("+listLogicalSINumber.list[i].custSINo+","+MBICServiceID+","+MBICServiceProductID+","+1+"); 'disabled='true' > / <input type='button' style='width:60px;' name='detachButton' value='Dettach' id='detachButton"+listLogicalSINumber.list[i].custSINo+"'   onclick='javascript:getAttachDetachLSI("+listLogicalSINumber.list[i].custSINo+","+MBICServiceID+","+MBICServiceProductID+","+0+");' disabled='false'>";
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		if(serviceProductID!=0)//View Product Catalog
		{
			if(mbiclogicalSIno==listLogicalSINumber.list[i].isCCMapWithMBIC)
			{
				//document.getElementById("lblMBIC"+listLogicalSINumber.list[i].custSINo).value=listLogicalSINumber.list[i].isCCMapWithMBIC;
				document.getElementById("attachButton"+listLogicalSINumber.list[i].custSINo).disabled=true;		
				document.getElementById("detachButton"+listLogicalSINumber.list[i].custSINo).disabled=false;
				isCCAttachWithMBIC=1;
				currentAttachedMBIC=listLogicalSINumber.list[i].custSINo;
			}
			else 
			if(listLogicalSINumber.list[i].isCCMapWithMBIC!=0)
			{
				document.getElementById("attachButton"+listLogicalSINumber.list[i].custSINo).disabled=true;		
				document.getElementById("detachButton"+listLogicalSINumber.list[i].custSINo).disabled=false;
			}
			else if(document.getElementById("lblMBIC"+listLogicalSINumber.list[i].custSINo).value==0 ||document.getElementById("lblMBIC"+listLogicalSINumber.list[i].custSINo).value=="")
			{
				document.getElementById("attachButton"+listLogicalSINumber.list[i].custSINo).disabled=false;		
				document.getElementById("detachButton"+listLogicalSINumber.list[i].custSINo).disabled=true;
			}
			/*else 
			{
				//document.getElementById("lblMBIC"+listLogicalSINumber.list[i].custSINo).value=listLogicalSINumber.list[i].isCCMapWithMBIC;
				document.getElementById("attachButton"+listLogicalSINumber.list[i].custSINo).disabled=true;		
				document.getElementById("detachButton"+listLogicalSINumber.list[i].custSINo).disabled=true;
			}*/
		}
		else //Product Catalog
		{
			if(listLogicalSINumber.list[i].isCCMapWithMBIC!=0)
			{	
				//document.getElementById("lblMBIC"+listLogicalSINumber.list[i].custSINo).value=listLogicalSINumber.list[i].isCCMapWithMBIC;
				document.getElementById("attachButton"+listLogicalSINumber.list[i].custSINo).disabled=true;		
				document.getElementById("detachButton"+listLogicalSINumber.list[i].custSINo).disabled=false;
			}
			else
			{
				document.getElementById("attachButton"+listLogicalSINumber.list[i].custSINo).disabled=false;		
				document.getElementById("detachButton"+listLogicalSINumber.list[i].custSINo).disabled=true;			
			}
			
		}
		
	}
	if(listLogicalSINumber.list.length == 0)
	{
		var tblRow=document.getElementById('tabLogicalType').insertRow();
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

function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     		isBlankFields();
    }      
}
</script>
</head>
<body >
<html:form action="/currencyChange" styleId="logicalTypes" method="post">
<bean:define id="formBean" name="currencyChangeBean"></bean:define>
<bean:define id="pagingSorting" name="formBean" property="pagingSorting"/>
	
	<div class="head"> <span>Clear Channel To MBIC LSI Mapping</span> </DIV>
		<fieldset class="border1">
			<legend> <b>Logical SI Number List</b> </legend>			
			<table width="100%"  border="1" id="tabLogicalType" align="center" cellpadding="0" cellspacing="0" class="tab2" >
				<tr class="grayBg">				    
					<td >Clear Channel LSI No</td>
					<td colspan="1">MBIC LSI No</td>
					<td colspan="1"></td>
				</tr>
			</table>
			<table width="100%"  id="tab2LogicalType"  align="center"  cellpadding="0" cellspacing="0"  >
				<tr class="grayBg">
				<td width="40%"></td>
				<td  width="40%" align="center" colspan="3">
					<div class="searchBg" onclick="getInfoAndUpdate();"  align="center"><a href="#">Close</a></div>
					</td>
				<td width="40%"></td>
				</tr>
			</table>
		</fieldset>
</html:form>
</body>
<script type="text/javascript">
	DrawLSI_TableForMBIC()
</script>
</html>


	
