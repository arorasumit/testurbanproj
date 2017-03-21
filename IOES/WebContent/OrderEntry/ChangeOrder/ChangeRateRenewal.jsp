<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@page import="com.ibm.ioes.utilities.Messages"%>
<html>
<head>
<title>ProductCatelog</title>
<link href="gifs/style.css" type="text/css" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
</head>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/utility.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jsonrpc.js"></script>
<link href="gifs/jquery-ui.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="js/Calender/jquery.min.js"></script>
<script type="text/javascript" src="js/Calender/jquery-ui.min.js"></script>  
<script type="text/javascript" src="js/formValidations.js"></script>
<script type="text/javascript" src="js/gen_validatorv31.js"></script>
<script language="javascript" src="<%=request.getContextPath()%>/js/calendar.js"></script>
<script language="javascript" type="text/javascript">

var counter = 1;
var factorVal;
var gbchargeTypeIds;
var gbchargeTypeValues;
var gbchargeTypeCached=0;
var gbfrequencyId;
var gbfrequencyName;
var gbfrequencyCached=0;
var factorVal;
var callerWindowObj = dialogArguments;

function getServiceAttributes()
{

      
   	   var ServiceProductId=callerWindowObj.ServiceProductIds;
   	   var ServiceProductName=callerWindowObj.ServiceProductName;
   	    var ServiceIds=callerWindowObj.ServiceIds;
     for(j=0;j<ServiceProductId.length;j++)
      {
		 var tblRow1=document.getElementById('tableCharges').insertRow();
		     var tblRow1=document.getElementById('tableCharges').insertRow();
				var tblcol=tblRow1.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.colSpan = 12;
				CellContents =ServiceProductName[j] ;
				tblcol.innerHTML = CellContents;
		var jsChargeData =			
		{
			serviceProductID:ServiceProductId[j]
		};
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	    var chargelists = jsonrpc.processes.populateChargeDetails(jsChargeData);
	    if(chargelists.list.length==0)
	    {
	    	//document.getElementById('ChargeDetails').style.display='none';
			
	    }
		else {
		
	    	for (i = 0 ; i < chargelists.list.length; i++,counter++)
		    {
				var str;
				
				//var counter = document.getElementById('tableCharges').rows.length;
				//alert('Counter'+counter);
				var tblRow=document.getElementById('tableCharges').insertRow();
				
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str ="<input name='chkSelectChargeDetail' id='chkSelectChargeDetail"+counter+"' type='checkbox' value='"+chargelists.list[i].chargeInfoID+"'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				if(chargelists.list[i].chargeType==1)
				{
				str="<input type='text' name='ddCType' id='ddCType"+counter+"' value='RC' readonly='true'>";
				}
				else
				{
				 str="<input type='text' name='ddCType' id='ddCType"+counter+"' value='NRC' readonly='true'>";
				}
				str=str+"<input type='hidden' name='hdnChargeId' id = 'hdnChargeId"+counter+"' value='"+chargelists.list[i].chargeInfoID+"'>";
				str=str+"<input type='hidden' name='hdnServiceProductID' id = 'hdnServiceProductID"+counter+"' value='"+ServiceProductId[j]+"'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str="<input type='text' style='width:100px;'  name='txtCName' id='txtCName"+counter+"' value ='"+chargelists.list[i].chargeName+"' readonly='true'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str ="<input type='text' style='width:100px;'  readonly='true' maxlength='15' name='txtCTAmt' id='txtCTAmt"+counter+"' value = '"+chargelists.list[i].chargeAmount+"' onblur='if(this.value.length > 0){if(checknumber(this)){calculateFreqAmount("+counter+");}else{calculateFreqAmount("+counter+");return false;}}'  > ";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str ="<input type='text' style='width:100px;' class='inputBorder1' name='newChargeAmt' class='inputBorder1'   id='newChargeAmt"+counter+"' onblur='if( trim(this.value).length>0){ return checkdigits(this)};'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				
				
			 	var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str ="<input type='text'  size='15%' name='effDate_"+counter+"' class='inputBorder1' id='effDate_"+counter+"' readonly='true'><font size='1'><img src='<%=request.getContextPath()%>/images/cal.gif' onclick=\"javascript:show_calendar(productCatelog.effDate_"+counter+");\" border='0px' alt='Loading...'></font>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str ="	<select name='stdReason_"+counter+"' class='inputBorder1' id='stdReason_"+counter+"' ><option value='-1' selected='selected'>-Select-</option></select>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str = "<input type='text' style='width:100px;'  readonly='true' name='txtCPeriod' id='txtCPeriod"+counter+"' value='"+chargelists.list[i].chargePeriod+"' onblur='if(this.value.length > 0){return checknumber(this)}' readonly='readonly' >";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
			
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				
				str = "<input type='text' name='txtCFrequency' id='txtCFrequency"+counter+"'  readonly='true' value='"+chargelists.list[i].chargeFrequency+"'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				
				if(document.getElementById('ddCType' + counter).value==1)
				{
					document.getElementById('txtCFrequency' + counter).disabled=false;
				}
				
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str="<input type='text' style='width:100px;'  readonly='true' name='txtCFreqAmt' id='txtCFreqAmt"+counter+"' value ='"+chargelists.list[i].chargeFrequencyAmt+"'   onblur='if(this.value.length > 0){return checknumber(this)}' readonly='readonly'> ";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str= "<input type='text' style='width:100px;'  readonly='true' name='txtCStartDate' value ='"+chargelists.list[i].startDateLogic+"' id='txtCStartDate"+counter+"'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str ="<input type='text' style='width:100px;'  readonly='true' name='txtCEndDate' value ='"+chargelists.list[i].endDateLogic+"'  id='txtCEndDate"+counter+"'>";
				CellContents = str;
				tblcol.innerHTML = CellContents;
				
				
				
			   stdReasonComo(counter);
		
			}
		
		}
	}
	   var tblRow1=document.getElementById('tableCharges').insertRow();
		     var tblRow1=document.getElementById('tableCharges').insertRow();
				var tblcol=tblRow1.insertCell();
				tblcol.align = "middle";
				tblcol.vAlign="top";
				tblcol.colSpan = 12;
				str='<input type="button" name="btn" value="Update" onClick="updateData()">';
				CellContents =str ;
				tblcol.innerHTML = CellContents;
				
				
				setShowCal();
				
}

/*function createHeader()
{
						var strHTML = "<div   style='height:180px;width:800px;'>"
							strHTML = strHTML + "<table  border='1' cellspacing='0' cellpadding='0' align='center' width='100%' id='tableCharges'>"
							strHTML = strHTML + "<input type='hidden' name='hdnChargeCount' value='0'/>"
							strHTML = strHTML + " <tr>"
							strHTML = strHTML + "	<td  nowrap>Select</td>"
							strHTML = strHTML + "    <td  nowrap>Type</td>"
							 strHTML = strHTML + "   <td  nowrap>Name</td>"
							 strHTML = strHTML + "   <td nowrap>Charge Period(Months)</td>"
							strHTML = strHTML + "	<td  nowrap>Total Amount</td>"
							strHTML = strHTML + "	<td nowrap>Frequency</td>"
							strHTML = strHTML + "	<td  nowrap>Frequency Amount</td>"
							strHTML = strHTML + "	<td nowrap>Start Date Logic</td>"
							strHTML = strHTML + "	<td  nowrap>End Date Logic</td>"
							strHTML = strHTML + "	<td  nowrap>New Charge Amount</td>"
							strHTML = strHTML + "	<td  nowrap>Effective Date</td>"
							strHTML = strHTML + "	<td  nowrap>Std Reason</td>"
							strHTML = strHTML + "</tr>"
							strHTML = strHTML + "</table>"
							strHTML = strHTML + "</div>"
							return strHTML;
}*/

function stdReasonComo(spid)
{
	     var tr, td, i, j, oneRecord;
	     var changeStdReasonList;
	     
	     	 var combo = document.getElementById('stdReason_'+spid);
	    
	    
	     var iCountRows = combo.length;
	  
	    for (i = 1; i <  iCountRows; i++)
	    {
	       combo.remove(1);
	    }
		var jsData =			
					    {
						changeTypeId:5
						};
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    changeStdReasonList = jsonrpc.processes.getStdReasonCombo(jsData);
	    for(j=0;j<changeStdReasonList.list.length;j++)
	    {
		   	var option = document.createElement("option");
		  	option.text = changeStdReasonList.list[j].stdReasonName;
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



function changeFrequency(var_counter)
{
	
	if(document.getElementById('ddCType'+var_counter).value=="1")
	{
		document.getElementById('txtCFrequency'+var_counter).disabled=false;
	}
	else
	{
		document.getElementById('txtCFrequency'+var_counter).disabled=true;
	}
}

function show(tbl,btn)
{
	if (btn.value=="-")
	{
		document.all.item(tbl).style.display="None";
		btn.value="+";
	}
	else
	{
		document.all.item(tbl).style.display="Inline";
		btn.value="-";
	}
}

function updateData(){
 	 var chkLength=document.forms[0].chkSelectChargeDetail.length;
   	 var  hdnOrderNo=callerWindowObj.document.SNPType.hdnOrderNo.value;
     var effectiveDate= new Array();
	  var rateChange= new Array();
	 var stdReason= new Array();
	 var chargeID= new Array();
	 var SPID = new Array();
	
     var result="";
     	var myList=[];
     	var val=1;
     	
    if(chkLength==undefined)
	{
		if(document.forms[0].chkSelectChargeDetail.checked==true)
		{
		 
		effectiveDate[0]=document.getElementById('effDate_'+val).value;
		
		
		 rateChange[0]= document.getElementById('newChargeAmt'+val).value;
		 
		 stdReason[0]= document.getElementById('stdReason_'+val).value;
		 chargeID[0]=document.getElementById('chkSelectChargeDetail'+val).value;
		SPID[0] = document.getElementById('hdnServiceProductID'+val).value;
		
		  
		
			var ob={"javaClass": "com.ibm.ioes.forms.ChargesDetailDto",
						orderNo:hdnOrderNo,
						effectiveDateForRenewal:effectiveDate[0],
						newChargeAmount:rateChange[0],
						reasonForRenewal:stdReason[0],
						chargeInfoID:chargeID[0],
						serviceProductID:SPID[0]
						};
			myList[0]=ob;
		
		var chargesList={"javaClass": "java.util.ArrayList",
				"list": myList
				};
				
		var jsData =
		{
			chargesDetails : chargesList
			
		};
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			 result = jsonrpc.processes.updateChargesForRenewal(jsData);
			if(result == 0)
		       {
		       		alert("Data Saved successfully");
		       }
		       else
		       {
		       		alert("Data Not Save successfully!!");
		       		return false;
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
		for(i=0,j=1;i<chkLength;i++,j++)
		{
			var myform=document.getElementById("productCatelog");
			if(myform.chkSelectChargeDetail[i].checked==true)
			{
				
				
					 
				 	 effectiveDate[index]=document.getElementById('effDate_'+j).value;
					 rateChange[index]= document.getElementById('newChargeAmt'+j).value;
				 	 stdReason[index]= document.getElementById('stdReason_'+j).value;
				 	 chargeID[index]=document.getElementById('chkSelectChargeDetail'+j).value;
				 	 SPID[index] = document.getElementById('hdnServiceProductID'+j).value;
				 	
				 index++;
				 count++;
			}
		}
		
		if(count > 1)
		{
			for(i=0;i<count-1;i++)
		{
		
			var ob={"javaClass": "com.ibm.ioes.forms.ChargesDetailDto",
						orderNo:hdnOrderNo,
						effectiveDateForRenewal:effectiveDate[i],
						newChargeAmount:rateChange[i],
						reasonForRenewal:stdReason[i],
						chargeInfoID:chargeID[i],
						serviceProductID:SPID[i]
						};
			myList[i]=ob;
		}
		var chargesList={"javaClass": "java.util.ArrayList",
				"list": myList
				};
					
		var jsData =
		{
			chargesDetails : chargesList
			
		};
	
    
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			 result = jsonrpc.processes.updateChargesForRenewal(jsData);
			  if(result == 0)
		       {
		       		alert("Data Saved successfully");
		       }
		       else
		       {
		       		alert("Data Not Save successfully!!");
		       		return false;
		       }
		}
		
	}
	
	callerWindowObj.setDataForService();
	
	
	window.close();
 }
 


</script>
<body onload="getServiceAttributes()">
	<html:form action="/NewOrderAction" styleId="productCatelog" method="post">
	<bean:define id="formBean" name="newOrderBean"></bean:define>
		<DIV class="head"> Rate Renewal</DIV>
		<fieldset class="border1">
			<legend> <b> Rate Renewal</b> </legend>
			<div class="scroll" style="height:100%">
				<table  border="1" cellspacing="0" cellpadding="0" align="center" width="90%" >
					<tr id="serviceType" style="display:none">
						<td colspan="1" align="right" style="font-size:11px">
							Service Type
						</td>
						<td colspan="1" align="left" style="font-size:11px">
							<input type="hidden" readonly="readonly" value="" name="hdnChargeInfo"/>
							<html:hidden property="serviceID" name="formBean"/>
							<html:hidden property="logicalSINo"  name="formBean"/>
							<html:hidden property="hdnOrderNo" name="formBean"/>
							
						</td>
					</tr>
				</table>
			</div>
		</fieldset>
		<tr align="center" id="ChargeDetails" >
						<td colspan="2" width="50%">
							<jsp:include flush="true" page="viewChargeDetails.jsp"></jsp:include>
						</td>
					</tr>
					
						
					</html:form>
</body>

	<script type="text/javascript">

	function setShowCal()
	{
		$(":text[showcal='date']").each(function() {
			var thisEl = $(this).attr("id");
		 $("#"+thisEl ).datepicker({dateFormat: 'dd/mm/yy',showOn: 'none' });
		});
	}
	</script>

</html>
