<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 Manisha Garg	22-jun-11	00-05422		Select All Functionality-->
<!--[002]	 Manisha Garg	22-jun-11	00-05422		Search Logical SI-->
<!--[003]	 Manisha Garg	23-jun-11	00-05422		Search Customer Logical SI-->
<!--[004]	 Manisha Garg	23-jun-11	00-05422		Copy LOC Date-->
<!--[005]	 Manisha Garg	24-jun-11	00-05422		ADD SOME NEW COLUMNS-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.ibm.ioes.beans.SessionObjectsDto"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<%@page import="com.ibm.ioes.forms.ViewOrderFormBean"%>
<%int strcount=0;%>
<html>
<head>
<title>:: Billing Trigger Details</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/calendar.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/fxUtility.js"></script>

 <!--  001 start-->
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/BillingTrigger.js"></script>
 <!--  001 end-->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
</head>
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
window.name="BILLINGTRIGGER_FORNEW";
<!-- Java Script Code Will Comes Here-->
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
//004 start
function copyLoc_Date()
  {
  
     var i;
    var myForm=document.getElementById('frmBillingTrigger');		
    
    var copy_loc_date=document.getElementById('CopyLOCdate').value;
  
  var counter=1;

  
  if(copy_loc_date!="")
   {
   

         if(document.forms[0].chkService)
         
         {
         
        
            var chkLength_loc_date=document.forms[0].LOCDATE.length;
            
              
	         if(chkLength_loc_date==undefined)
	         
	         {
	             if(document.forms[0].chkService.disabled==false)
	             {
	                 document.forms[0].LOCDATE.value=copy_loc_date;
	                  counter++;
	             }
	             
	            
	         }
         
	         else
	         
	         {
	         		
		           for (i=0; i<chkLength_loc_date; i++)
				   { 
				 
				         if(myForm.chkService[i].disabled==false)
				         {
				        
				             myForm.LOCDATE[i].value=copy_loc_date;
				             counter++;
				         }
				       
							   
				   }
	         }
	         
	         
	         if(counter==1)
	         {
				alert("No lIne Items Available for Selection");         
	         }
	         
       }
       
     
         
       
   }
  else
  
	  {
	       alert("Please Enter LOC_DATE to Copy");
	  }

  }

//004 end
//002 START
function fnSearchLogicalSI()
    {
    
            var myForm=document.getElementById('frmBillingTrigger');				
				showLayer();
			myForm.submit();
    
    }
    
    //002 END
function fnGetChargeDetails( serviceProductID,path)
{
	var i;
	var j;
	var jsonrpc ;
	var counter;
	var test;
	try
	{
	
		jsonrpc = new JSONRpcClient(path + "/JSON-RPC");
		var jsData =			
	    {
		  serviceProductID:serviceProductID
		  
		};
		test = jsonrpc.processes.fnGetChargesAllOrders(jsData);

		var rowCount = document.getElementById("tableCharges").getElementsByTagName("TR").length;
		for(j=rowCount-1;j>0;j--)
		{
			document.getElementById('tableCharges').deleteRow(j);
		}
	
	
			for (i = 0 ; i < test.list.length; i++,counter++)
			    {
			
			    
			    var str;
			    var tblRow=document.getElementById('tableCharges').insertRow();
				
					//chargetype
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtCName'  value='"+test.list[i].chargeType+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					//chargename
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtCName'  value='"+test.list[i].chargeName+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					//chargeperiod
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtCperiod'  value='"+test.list[i].chargePeriod+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					//chargeamount
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtCamount'  value='"+test.list[i].chargeAmt+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					
					
					//chargefrequency
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtCfrequency'  value='"+test.list[i].chargefrequency+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
			//chargefrequencyamount
			          var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtCfrequencyAmt'  value='"+test.list[i].chargefrequencyamount+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
			
			
			///startdatelogic
			
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtstartdatelogic'  value='"+test.list[i].startdatelogic+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					
			//start_date_days
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtstartdatedays'  value='"+test.list[i].start_date_days+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
			//start_date_month
			
			
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtstartdatemonth'  value='"+test.list[i].start_date_month+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
			
			//ENDDATELOGIC
			
			   	  var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtenddatelogic'  value='"+test.list[i].enddatelogic+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					
			///END_DATE_DAYS
			
			
			
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtenddatedays'  value='"+test.list[i].end_date_days+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
			//end_date_month
			
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtenddatemonth'  value='"+test.list[i].end_date_month+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
			
			//CHARGESTATUS
			
				var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					tblcol.nowrap="nowrap";
					tblcol.width="150px";
					//str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100;' name='txtchargestatus'  value='"+test.list[i].chargeStatus+"' readonly='true'>";
					str=test.list[i].chargeStatus+"";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					
					
					//fxstatus
					
					
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					tblcol.nowrap="nowrap";
					tblcol.width="150px";
					//str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtfxstatus'  value='"+test.list[i].fxStatus+"' readonly='true'>";
					str=test.list[i].fxStatus+"";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					
					//FX_NO
					
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtfxno'  value='"+test.list[i].fxno+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
			
			///CHARGE_CREATEDON
			
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtchargecreatedon'  value='"+test.list[i].charge_createdon+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
					
					///////CHARGE_EndedON//////////////
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' name='txtchargeendedon'  value='"+test.list[i].charge_endon+"' readonly='true'>";
					CellContents = str;
					tblcol.innerHTML = CellContents;
		}
	}
	catch(e)
	{
		alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
		showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
	}
}
function changeVal(i)
{
	if(document.frmBillingTrigger("chkService"+i).checked==true)
	{
		document.frmBillingTrigger("LOCno"+i).disabled=false;
		document.frmBillingTrigger("LOCdate"+i).disabled=false;
		document.frmBillingTrigger("BTdate"+i).disabled=false;
		
		//document.frmBillingTrigger("LOCno"+i).readonly=false;
		//document.frmBillingTrigger("LOCdate"+i).readonly=false;
		//document.frmBillingTrigger("BTdate"+i).readonly=false;
	}
	else
	{
		document.frmBillingTrigger("LOCno"+i).disabled=true;
		document.frmBillingTrigger("LOCdate"+i).disabled=true;
		document.frmBillingTrigger("BTdate"+i).disabled=true;
	}
	return false;
}

function fnBillingTriggerSubmit()
{

var flag,i,strBillingTrigger,dataChanged;
	//var callerWindowObj = dialogArguments;
	flag=0;
	strBillingTrigger="";
	dataChanged="";
	var line_prop	=	new Array();
	index_lp=0;
	
	for(i=1;i<=document.frmBillingTrigger.itemServiceCount.value;i++)
	{
		if(document.frmBillingTrigger("chkService"+i).checked==true)
		{
			//alert(document.frmBillingTrigger("chkService"+i).value);
			ServiceProductId=document.frmBillingTrigger("hdnSID"+i).value;
			var LOCNo=document.frmBillingTrigger("LOCno"+i).value;
			var LOCdate=document.frmBillingTrigger("LOCdate"+i).value;
			var BillingTriggerDate=document.frmBillingTrigger("BTdate"+i).value;
			
			if(LOCNo=="")
			{
			alert("Please Enter LOC No for Product Number : "+ ServiceProductId);
			return false;
			}
			if(LOCdate=="")
			{
			alert("Please Enter LOCDate for Product Number : " + ServiceProductId);
			return false;
			}
			if(BillingTriggerDate=="")
			{
			alert("Please Enter BillingTriggerDate for Product Number : " + ServiceProductId);
			return false;
			}
			
			if(ServiceProductId=="")
			{
				ServiceProductId="0";
			}
			
			
			if(strBillingTrigger=="")
			{
				strBillingTrigger=ServiceProductId+"~"+LOCNo+"~"+LOCdate+"~"+BillingTriggerDate;
				dataChanged="1";
			}
			else
			{
				strBillingTrigger=strBillingTrigger+"@@"+ServiceProductId+"~"+LOCNo+"~"+LOCdate+"~"+BillingTriggerDate;
				dataChanged=dataChanged+"@@"+"1";
			}
			flag=1;
			
			line_prop[index_lp]=document.frmBillingTrigger("line_prop"+i).value;
			index_lp++;
			
		}
	}
	if(flag ==0)
	{
		alert("Please Select atleast One Line Item before Selecting Billing Trigger!!");
		return false;
	}
	if(strBillingTrigger!="")
	{
		var billingOrderNo;
		document.forms[0].strBillingTrigger.value=strBillingTrigger;
		//document.forms[0].dataChanged.value=dataChanged;
		billingOrderNo=document.frmBillingTrigger.orderNo.value;
	}
	

	fnSetBillingTriggerData(strBillingTrigger,billingOrderNo,dataChanged,line_prop);

}

		
function fnSetBillingTriggerData(val1,val2,dataChanged,line_prop)
{

var jsonrpc ;
var test;
var result_list;
var result_list_view_order_dto;

var myForm=document.getElementById('frmBillingTrigger');


	//alert("strBillingTrigger-->"+val1);
	//alert("billingOrderNo-->"+val2);
	//document.searchForm.hdnBillingTrigger.value=val1;
	//document.searchForm.hdnBillingOrderNo.value=val2;
	
	try
	{
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		var jsData =			
	    {
		  orderNo:val2,
		  billingTriggerString:val1,
		  dataChanged:dataChanged,
		  line_prop:line_prop
		};
		document.getElementById('fnBillingTrigger4').disabled=true;

		test = jsonrpc.processes.fnTriggerBilling(jsData);
		
	}
	

	catch(e)
	{
		alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
		showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
		dialogArguments.fnBillingTrigger3(window);
		return;
	}
	
	
		if(test!=null)
		{	
			if(test.ifAnyFailValidation=='FAILURE')
			{
				result_list_view_order_dto = test.arrViewOrderDto;
				//
				var j;
				var msg ="";
				for(j=0;j<result_list_view_order_dto.list.length;j++)
				{
						
					if(result_list_view_order_dto.list[j].dateValidationStatus =='FAIL')
					{
						msg = msg+"\n"+"Billing Trigger Date can not be less than : "+result_list_view_order_dto.list[j].accountActiveDate+" for Line No :"+result_list_view_order_dto.list[j].serviceProductID;
					}
				
				}
				
				alert(msg);
				document.getElementById('fnBillingTrigger4').disabled=false;
				
				//returning as validation failure
				return;
			}
		}
		if(test!=null){
			//alert(result_list.list.length);
			//alert('Action Performed. ');
			result_list=test.billingTriggerResult;
		}
		var successcount=0;
		var falcount=0;
		var i;
		var str1="";
		var str3;
	
		for(i=0;i<result_list.list.length;i++)
		{

			if(result_list.list[i].billingTriggerStatus=='20')
			{
		successcount=successcount+1;
			}
			else
			{
			
		 str3=(result_list.list[i].serviceProductID);
		     str1=str1 + ',' + str3;
			falcount=falcount+1;
			}
		
		}
		
		if(str1.length>0)
			{
				str1=str1.substring(1);
			}
		
		//alert(successcount+"SUCCESS");
		//alert(falcount+"FAILURE");
		//alert(str1);
		var msg="Billing Trigger Result :" + "\n" +   "Request Submitted Successfully for "+successcount+" Products";
		if(falcount>0)
		{
			msg=msg + "\n" +" Request Submission Failed for - "+falcount+". \nFollowing are the Product Numbers that have Failed" + str1;
		}
		alert(msg);
	    myForm.submit();
		//dialogArguments.fnBillingTrigger3(window);
		
		
}



<logic:equal name="orderData" property="allAccountsCreated" value="false">
	function retryAccountCreate()
	{
	    var myForm=document.getElementById('frmBillingTrigger');
		var jsonrpc ;
		try
		{
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			
			document.getElementById('retryAccountCreate').disabled=true;
			test = jsonrpc.processes.fnReTryAccountCreateInFx(document.frmBillingTrigger.orderNo.value);
			if(test.accountPending>0)
			{
				alert(test.accountPending+" Account(s) are Pending.");
			}
			else
			{
				alert("All account are Successfully created");
			}
			
			//dialogArguments.fnBillingTrigger3(window);
			myForm.submit();
		}
		catch(e)
		{
			alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
			showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
		}
		
	}
</logic:equal>

var path="<%=request.getContextPath()%>";
</script>
<script type="text/javascript" src="npd/js/opaqueLayer.js"></script>
<script language="javascript">window.onresize = setLayerPosition;</script>
<body >
<div id="shadow" class="opaqueLayer"><font color="yellow" size="6"> Please wait..</font></div>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/wz_tooltip.js"></script>
<html:form action="/viewOrderAction" styleId="frmBillingTrigger" method="post" target="BILLINGTRIGGER_FORNEW">
<bean:define id="formBean" name="ViewOrderLogicalFormBean"></bean:define>
<DIV class="head"> Billing Trigger Details</div>
<fieldset class="border1">
<legend> <b>Billing Trigger Details</b></legend>
<input type="hidden" name="methodName" value="fnBillingTrigger"/>

<div class="scroll">
	<table  border="0" cellspacing="0" cellpadding="0" align="center" width="100%">
	<!--  //004 start --><tr>
		<td width="10%">
		 	LOC Date
		</td>
		<td width="15%">
	 <input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" name="CopyLOCdate" id="CopyLOCdate" class="inputBorder1" style="width:120px;" onblur="if(this.value.length > 0){return checkdate(this)}">
	 			<img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" style="vertical-align: bottom;" onClick="javascript:show_calendar('frmBillingTrigger.CopyLOCdate');">
	 
	 	</td>
		<td width="10%">
			<div class="searchBg"><a href id="copy_loc_date" onClick="copyLoc_Date()">Copy LOC Date</a></div>
		</td>
		<!-- 004 end -->
	     <!--<td width="10%">
			<div class="searchBg"><a href="#">Pre Impl. Disconn.</a></div>
		</td>-->
		<td width="45%">
			<table border="0" cellspacing="0" cellpadding="0" align="right">
				<tr>
				<!--  002 START-->
					<td>
				<div class="searchBg"><a href id="Query" onClick="fnSearchLogicalSI()">Query</a></div>
					</td>
				</tr>
				<!--  002 END-->
			</table>
		</td>
		<!-- <td width="10%">
			<div class="searchBg"><a href="#">Pre Impl. Disconn.</a></div>
		</td> -->
	</tr>
	<tr>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
	  <td>&nbsp;</td>
	  <td></td>
	  <td>&nbsp;</td>
	</tr>
	</table>
	<table  border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
		<tr>
			<td width="70%">
				<!-- <div style="height:200px;" class="scroll">  -->
				<table  border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
					<tr>
					  <td align="center">&nbsp;</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  <td>&nbsp;</td>
					  <td></td>
					  <td colspan="2" align="center" nowrap>------------- Order Line -----------------------------</td>
					  <td align="center" nowrap>&nbsp;</td>
					  <td nowrap>&nbsp;</td>
					  <td nowrap>&nbsp;</td>
					  <td colspan="2" align="center" nowrap>--------------Child Billing Account-------------------- </td>
				  </tr>
					<tr>
					<td>Select</td>
					  <td align="center">Select</td>
						<td align="center">PM Provisioning Datesssss</td>
						<td align="center">LOC No</td>
						<td align="center">LOC Date</td>
						<td align="center">Billing Trigger Date</td>
						<td align="center">Number</td>
						<td align="center">Line Item Name</td>
						<td nowrap align="center">Logical SI No </td>
						<td nowrap align="center">Cust. Logical SI No </td>
						<td nowrap align="center">SI ID </td>
						<td nowrap align="center">A/c No </td>
						<td nowrap align="center">FX Status 
							<logic:equal name="orderData" property="allAccountsCreated" value="false">
								<a href="#" id="retryAccountCreate" onclick="javascript:retryAccountCreate()">(Retry)</a>
							</logic:equal>
						</td>
						<!-- 005 START -->
						<td align="center">Fx Token No</td>
						<td align="center">Fx Response</td>
						<td align="center">Fx Status</td>
						<td align="center">Challen No</td>
						<td align="center">Challen Date</td>
						<!-- 005 END -->
					</tr>
					 <!--  001 start-->
					<tr>
					<td>Select All</td>
					<td>
					<input type="checkbox" name="SelectAllChck" id="SelectAllChck" onclick="javascript:fnCheckAll();"/>
					</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				
				
				<td>	
				 <!--  002 start-->
				 
						<html:select property="searchLSI" styleId="searchLSI" style="width:100px" styleClass="inputBorder1">
								<option value="-1">Select</option>
								<html:optionsCollection name="LSIResult" label="label" value="value"/>
								</html:select>
										 <!--  002 end-->
								</td>
						
				<td>
				<!-- 003 start -->
				<html:select property="searchCustomerLSI" styleId="searchCustomerLSI" style="width:100px" styleClass="inputBorder1">
								<option value="-1">Select</option>
								<html:optionsCollection name="custLSIResult" label="label" value="value"/>
								</html:select>
								<!-- 003 end -->
				</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
					
					
					</tr>
					 <!--  001 end-->
					<logic:present name="selectServiceDetailsList" scope="request">
						<logic:empty name="selectServiceDetailsList">
						<tr align="center">
							<td colspan="12">
								<b>No Products Available for Billing Trigger</b>
							</td>
						</tr>
						</logic:empty>
					<logic:notEmpty name="selectServiceDetailsList">
					<logic:iterate name="selectServiceDetailsList" id="QueueList" indexId="recordId">
				<%
						String classType=null;
							if(recordId.intValue() % 2 == 0)
						{
						classType="normal";
						}
						else
						{
						classType="lightBg";				
						}	
						strcount++;					
					%>			
					<input type="hidden" name="line_prop<%=strcount%>" value='NEW_LINE'/>		
					<tr class="<%=classType%>">
						<td><input type="radio" name="chargerow" onclick="javascript:fnGetChargeDetails('<bean:write name='QueueList' property='lineNumber'/>',path)"> </td>
						<td align="center">
						<logic:notEqual name="QueueList" property="billingTriggerStatus" value="20">
							<logic:notEqual name="QueueList" property="fxAccNo" value="">
								<input type="checkbox" name="chkService<%=strcount%>" id="chkService" value="1" onClick="changeVal('<%=strcount%>')" >							
							</logic:notEqual>
							<logic:equal name="QueueList" property="fxAccNo" value="">
								<input type="checkbox" name="chkService<%=strcount%>" id="chkService" value="1" onClick="changeVal('<%=strcount%>')" disabled="disabled" style="background-color: red">							
							</logic:equal>
							
						</logic:notEqual>	
						<logic:equal name="QueueList" property="billingTriggerStatus" value="20">
							<input type="checkbox" name="chkService<%=strcount%>" value="1" id="chkService" onClick="changeVal('<%=strcount%>')" disabled="disabled" style="background-color: blue">
						</logic:equal>	
							<input type="hidden" name="hdnSID<%=strcount%>" value='<bean:write name="QueueList" property="lineNumber"/>'>
						</td>
						<td nowrap>
							<input  onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputborder2" size="15" readonly="readonly" name="taskDate<%=strcount%>" value='<bean:write name="QueueList" property="pmProvisioningDate"/>' title='<bean:write name="QueueList" property="pmProvisioningDate"/>'>
							<!-- <img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" style="vertical-align: bottom;" onClick="javascript:show_calendar('frmBillingTrigger.taskDate<%=strcount%>');"> -->
						</td>
						<td nowrap><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" id="LOCNO" size="10" class="inputborder1" name="LOCno<%=strcount%>" disabled value='<bean:write name="QueueList" property="locNo"/>' title='<bean:write name="QueueList" property="locNo"/>'></td>
						<td nowrap>
							<!--<input type="text" class="inputborder1" name="LOCdate<%=strcount%>" size="10" disabled readonly="readonly" value='<bean:write name="QueueList" property="locDate"/>'>-->
							<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" class="inputborder1" id="LOCDATE" name="LOCdate<%=strcount%>" size="10" disabled  value='<bean:write name="QueueList" property="locDate"/>' title='<bean:write name="QueueList" property="locDate"/>' readonly onblur="if(this.value.length > 0){return checkdate(this)}">
							<img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" style="vertical-align: bottom;" onClick="javascript:show_calendar('frmBillingTrigger.LOCdate<%=strcount%>');">
						</td>
						<td nowrap>
							<!-- <input type="text" id="BTDATE" size="10" class="inputborder1" name="BTdate<%=strcount%>" disabled readonly="readonly" value='<bean:write name="QueueList" property="billingTriggerDate"/>'> -->
							<input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" id="BDATE" class="inputborder1" name="BTdate<%=strcount%>" size="10" disabled value='<bean:write name="QueueList" property="billingTriggerDate"/>' readonly onblur="if(this.value.length > 0){return checkdate(this)}">
							<img src="<%=request.getContextPath()%>/images/cal.gif" border="0px" style="vertical-align: bottom;" onClick="javascript:show_calendar('frmBillingTrigger.BTdate<%=strcount%>');">
						</td>
						<td nowrap width="8%"><input type="text" size="10" readonly="readonly" class="inputborder2" name="SID<%=strcount%>" value='<bean:write name="QueueList" property="lineNumber"/>' title='<bean:write name="QueueList" property="lineNumber"/>'></td>
						<td nowrap width="15%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" readonly="readonly" class="inputborder2" name="Sdesp<%=strcount%>" value='<bean:write name="QueueList" property="lineName"/>' title='<bean:write name="QueueList" property="lineName"/>'></td>
						<td nowrap width="8%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" name="logicalSI<%=strcount%>" value='<bean:write name="QueueList" property="logicalSino"/>' title='<bean:write name="QueueList" property="logicalSino"/>'></td>
						<td nowrap width="8%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" name="CustlogicalSI<%=strcount%>" value='<bean:write name="QueueList" property="custLogicalSino"/>' title='<bean:write name="QueueList" property="logicalSino"/>'></td>
						<td nowrap width="10%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="siid"/>' title='<bean:write name="QueueList" property="siid"/>'></td>
						<td nowrap><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="fx_ACCOUNT_EXTERNAL_ID"/>' title='<bean:write name="QueueList" property="fx_ACCOUNT_EXTERNAL_ID"/>'></td>
						<td nowrap><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="fxStatus"/>' title='<bean:write name="QueueList" property="fxStatus"/>'></td>
						<!-- 005 START -->
						<td nowrap width="8%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="fx_Token_no"/>' title='<bean:write name="QueueList" property="fx_Token_no"/>'></td>
						<td nowrap width="8%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="fx_Response"/>' title='<bean:write name="QueueList" property="fx_Token_no"/>'></td>
						<td nowrap width="8%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="5" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="fx_status"/>' title='<bean:write name="QueueList" property="fx_status"/>'></td>
						<td nowrap width="8%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="challen_No"/>' title='<bean:write name="QueueList" property="challen_No"/>'></td>
						<td nowrap width="8%"><input onmouseover='getTip(value)' onmouseout='UnTip()' type="text" size="10" readonly="readonly" class="inputborder2" value='<bean:write name="QueueList" property="challen_date"/>' title='<bean:write name="QueueList" property="challen_No"/>'></td>
						<!-- 005 END -->
					</tr>
					</logic:iterate>	
					</logic:notEmpty>
					</logic:present>
				</table>
			</td>
		</tr>
	</table>
	</div>
	</fieldset>
	
	<fieldset class="border1" >
	<legend scrolling="yes"> <b>Charge Details</b> </legend>
	<div class="scroll">
	<table id="tableCharges"  border="1" cellspacing="0" cellpadding="0" align="center" width="100%" >
		<tr>
		
			<td>Type</td>
			<td>
				Name</td>
			<td>
				Charge Period Month </td>
			<td>
				Total Amount </td>
			<td>
				Charge Frequency </td>
			<td>
				Charge Frequency Amount </td>
			<td> Start Date logic </td>
			<td> Start Date Days </td>
			<td>Start Date Month </td>
			<td>End Date Logic </td>
			<td>End Date Days </td>
			<td>End_Date_Month </td>
			<td nowrap>Charge Status</td>
			<td nowrap>FX Status(,Disonnection Status)</td>
			<td nowrap>IB2B NO / FX NO</td>
			<td>Charge Created On</td>
			<td>Charge Ended On</td>
		</tr>

	</table>
	<table  border="0" cellspacing="0" cellpadding="0" align="center" width="100%" >
	<tr>
		<td>
			<table border="0" cellspacing="0" cellpadding="0" align="center">
				<tr>
					<td>
						<div class="searchBg"><a href id="fnBillingTrigger4" onClick="fnBillingTriggerSubmit()">Trigger Billing</a></div>
						<input type="hidden" name="itemServiceCount" value="<%=strcount%>">
 					    <input type="hidden" name="orderNo" value='<bean:write name="ViewOrderLogicalFormBean" property="billingOrderNo"/>'>
				    <html:hidden name="ViewOrderLogicalFormBean" property="strBillingTrigger"/>
 					</td>
				</tr>
			</table>
		</td>
	</tr>
	</table>
</div>
</fieldset>
</html:form>
</body>
</html>