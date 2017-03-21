<!--Tag Name Resource Name  			Date		CSR No			Description -->
<!--[001]	 LAWKUSH					06-05-11	00-05422		Setting paging sorting -->
<!--[002]	 RAMANA		    			13-06-11	00-05422		Validations for the fields present in this page -->
<!-- [00044]	 Ashutosh Singh			23-June-11	CSR00-05422     Creating Change Order From Existing order in Billing Queue -->
<!-- [045]	 Rohit Verma				21-Feb-13	CSR00-07481	    Arbor Development to view LSI Mapping-->
<!--[046] VIPIN SAHARIA 11th Aug 2015 CBR_20141219-R2-020936-Billing Efficiency Program Drop 3 (BFR 17) Reverting MBIC CC mapping as per L3-VCS mapping -->

<!-- ==========================================Sumit Arora====================================================================================== -->
<!-- [SUMIT001]  	Sumit Arora	05-Oct-11	00-05422		Changes Made For Currency Should Checked for LSI Listed i.e. Only those LSI having same Currency as Order-->
<!-- ================================================================================================================================ -->
<!-- [047]   Gunjan Singla  20160219-XX-022117   CBR-ANG bandwidth correction in iB2B and M6 -->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<html>
<head>
<title>SelectLogicakSINumber</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta name="GENERATOR" content="Rational Software Architect">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/PagingSorting.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="js/formValidations.js"></script>
<script language="javascript" type="text/javascript">
var callerWindowObj = dialogArguments;
function getInfoAndUpdate(custSINo,serviceName,serviceID)
{
		callerWindowObj.document.SNPType.logicalSINo.value = unescape(custSINo);
		callerWindowObj.document.SNPType.serviceName.value = unescape(serviceName);
		callerWindowObj.document.SNPType.serviceId.value = unescape(serviceID);
		callerWindowObj.tableDrawing();
		//window.close();
	  
}

function lsiDisplay()
{

	var frm=document.getElementById('logicalTypes');
	var flag;
	var strlogicalSINo='';
	var strServiceId='';	
	if(frm.chk==undefined)
	{
		strlogicalSINo = "No Lsi found";
		return false;
	}
	if(frm.chk.length==undefined)
	{
		if(frm.chk.checked==true)
		{			
			strServiceId=frm.chk.value;
			strlogicalSINo=document.getElementById('hdnLogicalSiNo_'+strServiceId).value;
			flag=true;
		}
	}
	
	for(i=0;i<frm.chk.length;i++)
	{
		
			if(frm.chk[i].checked==true)
			{	
				flag=true;				
				if(strServiceId=='')
				{
					strServiceId=frm.chk[i].value;
					strlogicalSINo=document.getElementById('hdnLogicalSiNo_'+strServiceId).value;					
				}else
				{
					strServiceId=strServiceId+","+frm.chk[i].value;
					strlogicalSINo=strlogicalSINo+","+document.getElementById('hdnLogicalSiNo_'+frm.chk[i].value).value;					
					
				}
				
			}	
	}
	//[046] START
	/* if(mbic_cc_map_ServiceList!='' && mbic_cc_map_lSIList!='')
	{
		strServiceId=strServiceId+""+mbic_cc_map_ServiceList;
		strlogicalSINo=strlogicalSINo+""+mbic_cc_map_lSIList;
	} */
	//alert('strlogicalSINo==>'+strlogicalSINo);
	//[047] start
	var SubChangeType=callerWindowObj.document.getElementById('hdnSubChangeTypeId').value;
	//if(SubChangeType==3){
	var msg=jsonrpc.processes.checkDropAndCarryLSiForPD(strlogicalSINo,SubChangeType);
	if(msg!=''){
		alert(msg);
	    clearSelection();	    
	    return false;
	}
   //}
	//[047] end
		callerWindowObj.document.SNPType.lsi.value = strlogicalSINo;
		
}
//[047] start
function clearSelection(){
	var frm=document.getElementById('logicalTypes');
	
	//for single check
	if(frm.chk.length==undefined)
	{
		
		if(frm.chk.checked==true)
		{			
			frm.chk.checked=false;
		}
	}
	//for multiple checks
	for(i=0;i<frm.chk.length;i++)
	{
		
		if(frm.chk[i].checked==true){
			frm.chk[i].checked=false;
		}
	}
		
}
//[047] end
//var mbic_cc_map_ServiceList='';
//var mbic_cc_map_lSIList='';
/* function checkMBIC_CC_MAP(serviceId)
{
	mbic_cc_map_ServiceList='';
	mbic_cc_map_lSIList='';	
	var mbic_cc_MapDetails='';
	var serviceTypeId=document.getElementById('hdnMBICServiceTypeID'+serviceId).value;
	var mbic_ServiceId='';	;
	var mbic_LSI='';
	var mbic_OrderNo='';
	var mbiccDeatilList='';	
	var isFx_BT_END='';
	var messageStr1='';
	var messageStr2='';
	var currentOrderNo=callerWindowObj.document.SNPType.hdnOrderNo.value;
	mbic_cc_MapDetails=document.getElementById('hdnMBICServiceID'+serviceId).value;	
	if(mbic_cc_MapDetails!='' && mbic_cc_MapDetails!="null" )
	{
		if(serviceTypeId==181)
		{
			messageStr1="Clear Channel";
			messageStr2="MBIC";
		}
		else if(serviceTypeId==413)
		{
			messageStr1="MBIC";
			messageStr2="Clear Channel";
		}
		mbiccDeatilList=mbic_cc_MapDetails.split(":");
		mbic_ServiceId=mbiccDeatilList[0];
		mbic_LSI=mbiccDeatilList[1];
		isFx_BT_END=mbiccDeatilList[2];
		mbic_OrderNo=mbiccDeatilList[3];		
		if(document.getElementById('chk'+serviceId).checked==true )
		{
			if(isFx_BT_END=='FX_BT_END' )
			{
				if(confirm(""+messageStr1+" is Map With "+messageStr2+". So "+messageStr2+" LSI will also Import.  Do You Want to Continue"))
				{
					if(document.getElementById('chk'+mbic_ServiceId)!=null)
					{
						document.getElementById('chk'+mbic_ServiceId).checked=true;
					}
					else
					{
						mbic_cc_map_ServiceList=mbic_cc_map_ServiceList+","+mbic_ServiceId;
						mbic_cc_map_lSIList=mbic_cc_map_lSIList+","+mbic_LSI;
					}
				}
				else 
				{	
					if(serviceTypeId==413)
					document.getElementById('chk'+serviceId).checked=false; 
					if(document.getElementById('chk'+mbic_ServiceId)!=null )
					{
						document.getElementById('chk'+mbic_ServiceId).checked=false;
					}
					return false;
				}
			}
			else if(mbic_OrderNo!=currentOrderNo)
			{
				alert(""+messageStr1+" is Map With "+messageStr2+". Please Billing Trigger The "+messageStr2+" Service No:"+mbic_ServiceId+" First")
				document.getElementById('chk'+serviceId).checked=false;
				return false;
			}
		}else
		{			
			if(isFx_BT_END='FX_BT_END')
			{
				if(confirm(""+messageStr1+" is Map With "+messageStr2+". Both  "+messageStr1+" And "+messageStr2+" LSI will not Process. Do You want to Continue. "))
				{
					if(document.getElementById('chk'+mbic_ServiceId)!=null)
						document.getElementById('chk'+mbic_ServiceId).checked=false;
					else
					{
						removeLSIFromMap(mbic_ServiceId,mbic_LSI);
					}
				}else 
				{
					//if(serviceTypeId==413)
					document.getElementById('chk'+serviceId).checked=true; 
					if(document.getElementById('chk'+mbic_ServiceId)!=null )
					{
						document.getElementById('chk'+mbic_ServiceId).checked=true;
					}
					return false;
				}	
			}				
		}
	}	
}//End of Funtion

function removeLSIFromMap(mbic_ServiceId,mbic_LSI)
{
	mbic_cc_map_ServiceList=mbic_cc_map_ServiceList.replace(mbic_ServiceId,"");
	mbic_cc_map_lSIList=mbic_cc_map_lSIList.replace(mbic_LSI,"");
	var temArray=mbic_cc_map_ServiceList.split(",");
	var temArray1=mbic_cc_map_lSIList.split(",");
	mbic_cc_map_ServiceList='';	
	mbic_cc_map_lSIList='';
	for(i=0;i<temArray.length;i++)
	{
		if(temArray[i]!='')
		{
			mbic_cc_map_ServiceList=mbic_cc_map_ServiceList+","+temArray[i];
			mbic_cc_map_lSIList=mbic_cc_map_lSIList+","+temArray1[i];
		}
	}
}*/
//[046] START
//for Selecting Multiple services
function SelectMultipleServices()
{
	
	var frm=document.getElementById('logicalTypes');
	var flag;
	var strlogicalSINo='';
	var strServiceId='';	
	//Vijay start 
	var totalService=0;
	//Vijay end
	if(frm.chk==undefined)
	{
		alert("There is no Records!!")
		return false;
	}
	if(frm.chk.length==undefined)
	{
		if(frm.chk.checked==true)
		{			
			strServiceId=frm.chk.value;
			strlogicalSINo=document.getElementById('hdnLogicalSiNo_'+strServiceId).value;
			flag=true;
		}
	}
	
	for(i=0;i<frm.chk.length;i++)
	{
		
			if(frm.chk[i].checked==true)
			{	
				flag=true;				
				if(strServiceId=='')
				{
					strServiceId=frm.chk[i].value;
					strlogicalSINo=document.getElementById('hdnLogicalSiNo_'+strServiceId).value;					
				}else
				{
					strServiceId=strServiceId+","+frm.chk[i].value;
					strlogicalSINo=strlogicalSINo+","+document.getElementById('hdnLogicalSiNo_'+frm.chk[i].value).value;					
					
				}
				
				totalService = totalService+1;
			}	
	}
	
	if(flag!=true)
		{
			alert("Please select Services");
			return false;
				
		}
	else
		{
			//vijay. add a restriction and limit to copy the service in single order	
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			var currentOrderNo=callerWindowObj.document.SNPType.hdnOrderNo.value;
			var totalServiceMessage = jsonrpc.processes.totalServiceCountCheck(currentOrderNo,totalService);
			if(totalServiceMessage!= '' ){
				alert(totalServiceMessage);
				callerWindowObj.document.SNPType.lsi.value="";
				return false;
			}
			//Vijay end
			//[046] START
			//MBIC AND CC
			/* if(mbic_cc_map_ServiceList!='' && mbic_cc_map_lSIList!='')
			{
				strServiceId=strServiceId+""+mbic_cc_map_ServiceList;
				strlogicalSINo=strlogicalSINo+""+mbic_cc_map_lSIList;
			} */
			//[046] END
			callerWindowObj.document.SNPType.logicalSINo.value = strlogicalSINo;			
			callerWindowObj.document.SNPType.serviceId.value = strServiceId;			
			callerWindowObj.tableDrawing();			
			window.close();	
			
		}
	
}
function DrawLogicalType()
{
	   var str;
	   var frm=document.getElementById('logicalTypes');
	   document.getElementById('blanksearchcriteria').style.display='none';
	   document.getElementById('logicalSINumberPaging').style.display='block';
        callerWindowObj = dialogArguments;
	    frm.hdnAccountId.value =callerWindowObj.document.SNPType.hdnAccountId.value;	  
	    frm.issuspended.value =callerWindowObj.document.SNPType.issuspended.value;
	    frm.isdisconnected.value =callerWindowObj.document.SNPType.isdisconnected.value;
	    //[00044] Start
	    var changeType = callerWindowObj.document.getElementById('hdnChangeType').value;
	    var changeSubType = callerWindowObj.document.getElementById('hdnSubChangeTypeId').value;	     
	    //[00044] End	
	    
	    /*Vijay start. fetch orderType that is demo or not.
	    *If order is Demo them orderType variable contian 'D' other wise it would be 'N'
	    */
	     
	    var orderType = callerWindowObj.document.getElementById('hdnOrderType').value;
    	var stage = document.getElementById('searchSourceNameId').value;
    	/*When records will reload then 'select check All' would be uncheck */
	    document.getElementById('SelectAllChck').checked=false;
	    
	    //Vijay end
	    
	    var isUDS = 0;
	    
	    var lookUPMode="<%=request.getParameter("lookupMode")%>";
	    var vIRN_number="";
	    if(lookUPMode=="IRN" && document.getElementById("txtIRNnumber")!=null){
	    	vIRN_number=document.getElementById("txtIRNnumber").value;
	    }
	    
	    if(changeSubType == 17 || changeSubType == 18 || changeSubType == 19) {
	    	isUDS = 1;
	    }
	   	var mytable = document.getElementById('tabLogicalType');	
	   	var iCountRows = mytable.rows.length;
  
	   for (i = 1; i <  iCountRows; i++)
	   {
	       mytable.deleteRow(1);
	   } 
		
			document.getElementById('txtPageNumber').value= pageNumber;   	
			sync();			
			
		try{
		   var jsData =
				{	
				
					startIndex:startRecordId,
					endIndex:endRecordId,
					sortBycolumn:sortByColumn,
					sortByOrder:sortByOrder,
					custSINo:document.getElementById('txtLogicalSiNumber').value,
					serviceName:document.getElementById('txtServiceName').value,
					accountIDString:frm.hdnAccountId.value,
					changeTypeId:changeType,
					subChangeTypeId:changeSubType,
					issuspended:frm.issuspended.value,
					isdisconnected:frm.isdisconnected.value,
					isUDS:isUDS,
					orderNo:callerWindowObj.document.SNPType.hdnOrderNo.value,	//[SUMIT001]
					lookupmode:lookUPMode,
					IRN_Number:vIRN_number,
					fxInternalId:Number(document.getElementById('txtChildAccount').value),	
					order_type:orderType,
					orderStage:stage		
				};	
				//vijay add an other variable order_type for recognize the demo order	  
			jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			var listLogicalSINumber = jsonrpc.processes.getLogicalSINumber(jsData);
			}
			catch(e)
			{
				alert("Exception :"+e);
			}
	iTotalLength=listLogicalSINumber.list.length;
	if(iTotalLength !=0)
	{
		iNoOfPages = listLogicalSINumber.list[0].maxPageNo;
	}
	else
	{     
	        iNoOfPages=1;
	}
	var counter = <%=request.getAttribute("count")%>;
	
	document.getElementById('txtMaxPageNo').value=iNoOfPages;				
	var colors=new Array("normal","lightBg");
	 
	 for (i = 0; i <  iTotalLength; i++)
	 {	
	 	
	 	var colorValue=parseInt(i)%2;
		var tblRow=document.getElementById('tabLogicalType').insertRow();
		tblRow.className=colors[colorValue];
		
		var tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		//[046] START
		/*if(changeType==3 || changeType==2)
			str="<input name=chk id='chk"+listLogicalSINumber.list[i].serviceId+"' type='checkbox' value='"+listLogicalSINumber.list[i].serviceId+"' onclick='javascript:checkMBIC_CC_MAP("+listLogicalSINumber.list[i].serviceId+"); uncheckSelectAll();'  /><input type='hidden' name='hdnLogicalSiNo' id='hdnLogicalSiNo_"+listLogicalSINumber.list[i].serviceId+"' value='"+listLogicalSINumber.list[i].custSINo+"'/><input type='hidden' id='hdnMBICServiceID" + listLogicalSINumber.list[i].serviceId + "' value='" +listLogicalSINumber.list[i].mbicServiceId+ "' ><input type='hidden' id='hdnMBICServiceTypeID" + listLogicalSINumber.list[i].serviceId + "' value='" +listLogicalSINumber.list[i].serviceTypeId+ "' >";
		else
			str="<input name=chk id='chk"+listLogicalSINumber.list[i].serviceId+"' type='checkbox' value='"+listLogicalSINumber.list[i].serviceId+"' onclick='javascript:uncheckSelectAll();' /><input type='hidden' name='hdnLogicalSiNo' id='hdnLogicalSiNo_"+listLogicalSINumber.list[i].serviceId+"' value='"+listLogicalSINumber.list[i].custSINo+"'/>";
		*/
		str="<input name=chk id='chk"+listLogicalSINumber.list[i].serviceId+"' type='checkbox' value='"+listLogicalSINumber.list[i].serviceId+"' onclick='javascript:uncheckSelectAll();' /><input type='hidden' name='hdnLogicalSiNo' id='hdnLogicalSiNo_"+listLogicalSINumber.list[i].serviceId+"' value='"+listLogicalSINumber.list[i].custSINo+"'/>";
		//[046] END
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=listLogicalSINumber.list[i].custSINo;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=listLogicalSINumber.list[i].serviceName;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		
		
		if(lookUPMode=="IRN"){
			tblcol=tblRow.insertCell();
			tblcol.align = "center";
			tblcol.vAlign="top";
			str=listLogicalSINumber.list[i].attributeValue;
			CellContents = str;
			tblcol.innerHTML = CellContents;
		}		
		//Added by Ashutosh for FxChildAccount
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		if(listLogicalSINumber.list[i].fxInternalId==0)		
			str="";
		else
			str=listLogicalSINumber.list[i].fxInternalId;
		CellContents = str;
		tblcol.innerHTML = CellContents;		
		
		
		//Vijay add another column. show order no if LSI is in use
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		if(listLogicalSINumber.list[i].cc_M6_Progress_status != "FX_BT_END" || stage != "Completed"){
			//Vijay. make disabled check box if LSI is in use
			document.getElementById("chk" +listLogicalSINumber.list[i].serviceId ).disabled=true;
		}
		str=listLogicalSINumber.list[i].orderNumber;

		CellContents = str;
		tblcol.innerHTML = CellContents;
		//Vijay. end
		
	
		
		//Vijay add another column. show order stage if LSI is in use
		tblcol=tblRow.insertCell();
		tblcol.align = "center";
		tblcol.vAlign="top";
		str=listLogicalSINumber.list[i].stageName;
		CellContents = str;
		tblcol.innerHTML = CellContents;
		//Vijay. end
	}	
	if(listLogicalSINumber.list.length == 0)
	{
	var tblRow=document.getElementById('tabLogicalType').insertRow();
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
function clearFields()
{
	document.getElementById('txtLogicalSiNumber').value="";
	document.getElementById('txtServiceName').value="";
	document.getElementById('txtChildAccount').value="";
}

function isBlankFields()
{
	var logicalSiNumber=document.getElementById('txtLogicalSiNumber').value;
	var serviceName=document.getElementById('txtServiceName').value;	
	var serviceName1=document.getElementById('txtServiceName');	
	var logicalSiNumber1=document.getElementById('txtLogicalSiNumber');	
	var childAccount=document.getElementById('txtChildAccount').value;
	var childAccount1=document.getElementById('txtChildAccount');
	var searchFlag=1;	
	if( trim(serviceName).length>0)	
	{				
		if(ValidateTextField(serviceName1,path,'Service Name')==false)
			 {
			 searchFlag=0;
			 }
	}	
	if( trim(logicalSiNumber).length>0)
	{ 		
		if(checkdigits(logicalSiNumber1))
			{
			searchFlag=1;
			}
			else
			{
			 searchFlag=0;
			}	  		  		  	  		  	 	 
	}
	if( trim(childAccount).length>0)	
	{				
		if(checkdigits(childAccount1))
			{
			searchFlag=1;
			}
			else
			{
			 searchFlag=0;
			}	  		  		  	  		  	 	 
	}	
	if(searchFlag==1)
		{
			searchFromList('LOGICAL_SI_NO','SELECTLOGICAL_SI_NO');
		}		
}
	
function onPressEnterSearch(key_event)
{
    if (key_event.keyCode==13) 
    {
     	isBlankFields();
    }      
}

path='<%=request.getContextPath()%>';
//[045] Start
function viewLSILinking()
{
	var width  = 1500;
	var height = 1500;
	var left   = 10;
	var top    = 10;
	var params = 'width='+width+', height='+height;
 				params += ', top='+top+', left='+left;
 				params += ', directories=no';
 				params += ', location=no';
 				params += ', menubar=no';
 				params += ', resizable=no';
 				params += ', scrollbars=yes';
 				params += ', status=no';
 				params += ', toolbar=no';
	var path = "<%=request.getContextPath()%>/reportsAction.do?method=viewLSIMapping";		
	window.open(path,"LSIMapping",params);
}
//[45] End
//Vijay. add a method for searching 
function searchSubmit()
{
 isBlankFields();
/*	var myForm=document.getElementById('logicalTypes');
    var lookupMode= "<%=request.getParameter("lookupMode")%>";
    var accountID = callerWindowObj.document.SNPType.hdnAccountId.value;
	myForm.action="<%=request.getContextPath()%>/ChangeOrderAction.do?method=getLogicalSINumber&accountID="+accountID+"&lookupMode="+lookupMode;
	//var path = "<%=request.getContextPath()%>/ChangeOrderAction.do?method=getLogicalSINumber&accountID="+accountID+"&lookupMode="+lookupMode;		
	//showLayer();
	myForm.submit(); */

}
function fnCheckAll()
{
	var myForm=document.getElementById('logicalTypes');	
	var allCheck = document.getElementsByName('chk');
	
	if( allCheck.length==0)
	{
		alert("There is no records");
		document.getElementById('SelectAllChck').checked=false;
		return false;
	}else{
		if(document.getElementById('SelectAllChck').checked){
			for(i=0; i < allCheck.length; i++)
			{
				if(! allCheck[i].disabled){
				   allCheck[i].checked = true;
				 }  
			}
		}else{
			for(i=0; i < allCheck.length; i++)
			{
			   allCheck[i].checked = false;
			}
		}
	}
}
function enableDisableCheckAll(){
	var stage = document.getElementById('searchSourceNameId').value;
	if(stage!="Completed"){
		document.getElementById('SelectAllChck').disabled = true;	
	}
}

//Vijay start
function uncheckSelectAll(){
/*if any select box is checked then "select all check box" should be unchecked */
document.getElementById('SelectAllChck').checked=false;
}
//vijay end

</script>
</head>
<body onload="enableDisableCheckAll();">
<html:form action="/ChangeOrderAction" styleId="logicalTypes" method="post">
	<div class="head"> <span>Select LogicalSINumber</span> </DIV>
		<fieldset class="border1">
			<legend> <b>LogicalSINumber List</b> </legend>
			<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" id="logicalSINumberPaging" style="display:none;">	
	<tr>
		<td align="center"><a href="#" onclick="FirstPage('LOGICAL_SI_NO','SELECTLOGICAL_SI_NO')">First</a>&nbsp;&nbsp;<a href="#" onclick="NextPage('LOGICAL_SI_NO','SELECTLOGICAL_SI_NO')">Next</a></td>
		<td align="center">
			<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
			<input type="text" id="txtMaxPageNo"  class="inputBorder2" readonly="true" size="2"/>Pages
		</td>
		<td align="center"><a href="#" onclick="PrevPage('LOGICAL_SI_NO','SELECTLOGICAL_SI_NO')">Prev</a>&nbsp;&nbsp;<a href="#" onclick="LastPage('LOGICAL_SI_NO','SELECTLOGICAL_SI_NO')">Last</a></td>
	</tr>
	</table>	   		
   <table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" >
			   <tr class="normal">
					<td  align="center" colspan="25%">
						<div style="float:left;"><strong>Logical SI Number:</strong><input type="text" id="txtLogicalSiNumber" onKeyPress="onPressEnterSearch(event)" name="servicetype" class="inputBorder1">
						</div>
					</td>
					<td  align="center" colspan="25%">
						<div style="float:left;"><strong>Service Name:</strong><input type="text" id="txtServiceName" onKeyPress="onPressEnterSearch(event)" name="servicetype" class="inputBorder1">
						</div>
					</td>		
					<!-- Start Below code added for display IRN Tag when select look up mode is IRN -->
					<% String lookupMode=request.getParameter("lookupMode");
					if("IRN".equalsIgnoreCase(lookupMode)){ %>
					<td  align="center" colspan="25%">
						<div style="float:left;"><strong>IRN:</strong><input type="text" id="txtIRNnumber" onKeyPress="onPressEnterSearch(event)" name="servicetype" class="inputBorder1">
						</div>
					</td>
					<% }%>
					<!-- END Above code added for display IRN Tag when select look up mode is IRN-->
					<td  align="center" colspan="25%">
						<div style="float:left;"><strong>Child Account:</strong><input type="text" id="txtChildAccount" onKeyPress="onPressEnterSearch(event)" name="servicetype" class="inputBorder1">
						</div>
					</td>
					<td  align="center" colspan="25%">
						<a href="#"><img onclick="isBlankFields()" src="npd/Images/search.gif" title="search" height="15"></a>
						<img border="0" src="${pageContext.request.contextPath}/gifs/clear.GIF" alt="Clear Fields" width="24" height="18" onclick="clearFields();"/>&nbsp;				
						<!-- [045] Start-->
						<img id="idLinking" src="gifs/middle/linking.GIF" width="20px" height="20px" alt="View LSI Linking for Arbor Products" onclick="viewLSILinking()" title="View LSI Linking"></td>
						<!-- [045] End-->
						<!-- <div class="searchBg" onclick="DrawAccountType()"  style="margin-right:10px;" title="Search here"><a href="#">Search</a></div></td>-->
					</td>
				</tr>
	</table>
	<table width="100%" border="1"  id="tabLogicalType" align="center" cellpadding="0" cellspacing="0" class="tab2" >				
			<tr class="grayBg">
				<td  width="15%" align="center">Select
					
					<input type="checkbox" name="SelectAllChck" id="SelectAllChck" onclick="javascript:fnCheckAll();"/>
				</td>
				<td width="30%" align="center">
					<a href="#" onclick="sortOrder('LOGICAL_SI_NO','SELECTLOGICAL_SI_NO')">Logical SI Number</a>
				</td>
				<td width="30%" align="center">
					<a href="#" onclick="sortOrder('SERVICENAME','SELECTLOGICAL_SI_NO')">Service Name</a>
				</td>
				
				<!-- Start Below code added for display IRN Tag when select look up mode is IRN -->
				<% lookupMode=request.getParameter("lookupMode");
				if("IRN".equalsIgnoreCase(lookupMode)){ %>
				<td width="30%" align="center">
					<a href="#" onclick="sortOrder('IRN_NO','SELECTLOGICAL_SI_NO')">IRN Number</a>
				</td>
				<% }%>
				<!-- END Above code added for display IRN Tag when select look up mode is IRN-->
				<td width="30%" align="center">
					<a href="#" onclick="sortOrder('CHILD_ACCOUNT','SELECTLOGICAL_SI_NO')">Child Account</a>
				</td>
			<!-- vijay start -->	
				<td  width="30%" align="center" nowrap>
					Order No
				</td>
				<td width="30%" align="center" nowrap >
					&nbsp;Order Stage &nbsp;
					</br>
							<html:select property="searchOrderStage"
										styleId="searchSourceNameId" style="width:130px"
										onkeydown="if (event.keyCode == 13) return searchSubmit();">
										<html:option value="">--Select--</html:option>
										<html:optionsCollection name="listOrderStageName"
											label="searchOrderStageName" value="searchOrderStageCode" />
									</html:select>

					 <!-- vijay end -->	
					<input type="hidden" name="hdnAccountId" id="hdnAccountId" />
					<input type="hidden" name="issuspended" id="issuspended" />
					<input type="hidden" name="isdisconnected" id="isdisconnected" />				
				</td>
			</tr>								
	</table>
	<table   border="0" align="center" cellpadding="0" cellspacing="0" class="tab2" >
		<tr class="grayBg" align="center">
		<td width="282"></td>		
		<td  align="center" colspan=3 width="524">
			<div  class="searchBg" onclick="SelectMultipleServices()" >
					<a align="center" href="#" onclick="lsiDisplay()">OK</a></div>
			</td>
		<td width="157"></td>			
		</tr>
		<tr class="grayBg" align="center">
		<td  align="center" colspan=5  align="center">
			<div  id="blanksearchcriteria">
			<b>Please Enter Any Search Criteria or Click Search Button to see all Logical SI</b>
			</div>
			</td>
				
		</tr>
	</table>
		</fieldset>
</html:form>
</body>
<script type="text/javascript">
	//DrawLogicalType()
	//DrawTable('LOGICAL_SI_NO','SELECTLOGICAL_SI_NO')
</script>
</html>


	
