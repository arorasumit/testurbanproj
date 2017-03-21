<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ANIL KUMAR	    15-feb-2011 				Annotation field added in charges section against charges name -->
<!--[002]	 Rohit Verma	17-feb-2011 				Logic Start Date and End Date Label Chnaged -->
<!--[003]	 ANIL KUMAR		26-feb-2011 				fetch Charge Type RC/NRC based on bill format for hardware in charge section-->
<!--[004]	 ANIL KUMAR 	08-march-2011				Start date logic and End date logic should be default in charge section-->
<!--[005] 	 ANIL KUMAR 	09-march-2011				display Tax Rate in charge section-->
<!--[006] 	 Lawkush 		11-April-2011				In order to validate fields according to database driven mandatory or nonmandatory in all section-->
<!--[007]	 ANIL KUMAR		21-june-2011				Change Label PO Amount in charge details as PO Line Item Amount  -->
<!--[008]	 Vishwa			28-Sep-2011				    Annual Charge Rate is added  -->
<!--[010]	 Saurabh		03-Oct-2011				    Some rows and column added in charge info -->
<!--[077]    ASHUTOSH SINGH  01-DEC-11  00-05422        Changes in PO AMOUNT(Decimal validation) -->
<!--[078]    Vijay           08-Feb-13                  In Charges detals Charge Type dropdown was not working properly if user delete middle charge and then add charge, So modified some code for resolution. -->
<!--[011]	 Rohit Verma	 						    Customer Billing Experence--Making Payment Term editable for hardware and Service-BFR13-->
<!-- [012] VIPIN SAHARIA 04-06-2014 Added hidden field for FxChargeID + logic required for extra logic DC_COLO_SERVICE_TAX Charge (Hardware DC) -->
<!-- [013] Raveendra Kumar  15-May-2015 20141219-R2-020936-Billing Efficiency Program      Annotation length in IB2B-->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<html>
<head>
<title>ChargeDetails</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
<link href="./gifs/style.css" type="text/css" rel="stylesheet">
<link href="./css/chargeTableFreeze.css" type="text/css" rel="stylesheet">
<script>
var gbchargeTypeIds;
var gbchargeTypeValues;
var gbchargeTypeCached=0;
var gbfrequencyId;
var gbfrequencyName;
var gbfrequencyCached=0;
var factorVal;
var gchargeName;
var gchargeid;
var totalAmount=0;
var chargeSumCounter=0;
path="<%=request.getContextPath()%>";
path1='<%=request.getSession().getId() %>';

function checkUperLimit(value,ObjName,uperLimit,counter)
{
    if(value>uperLimit || value<1)
    {
	    alert("Enter value 1 to "+uperLimit+" Only");
	    if(uperLimit==30)
	    {
	    	document.getElementById('txtDelayedTimeInDays'+counter).value="";
	    	document.getElementById('txtDelayedTimeInDays'+counter).focus();
	    }
	    else if(ObjName=='txtLDPercentage'+counter)	
	    {
			document.getElementById('txtLDPercentage'+counter).value="";
			document.getElementById('txtLDPercentage'+counter).focus();
		}
		else if(ObjName=='txtMaxPenaltyPercentage'+counter)
		{
			document.getElementById('txtMaxPenaltyPercentage'+counter).value="";
			document.getElementById('txtMaxPenaltyPercentage'+counter).focus();
		}			
		return false;
	}	
}	
function changeEndDays(index)
{

    
	if(document.getElementById('txtCEndDate'+index).value=='TD')
		{
		  document.getElementById('txtEndDays'+index).value=0;
		  document.getElementById('txtEndMonth'+index).value=0;
		  
		
		}

}


function fillChargeNames(var_counter, chargeTypeValue)
{
	try
	{
			//cache charge type
			
			var chargetype = chargeTypeValue;
			var productid =document.getElementById('hdnServiceDetailID').value;
			var entityid=document.getElementById('txtEntity').value;
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			chargeCombo = jsonrpc.processes.populateChargeName(chargetype,productid,entityid);
			gchargeName=new Array();
			gchargeid=new Array()
			for(z=0;z<chargeCombo.list.length;z++)
		    {
		    	gchargeName[z] = chargeCombo.list[z].chargeName;
				gchargeid[z] = chargeCombo.list[z].chargeNameID;
		    }
		//read from cache
		var local_typeCombo = document.getElementById("txtCName"+var_counter);
	    var local_RowCount = local_typeCombo.length;
	    for (i =1; i < local_RowCount; i++)
	    {
	       local_typeCombo.remove(1);
	    } 
	    //Start[006]
	    for(i=0;i<chargeCombo.list.length;i++)
	    {
	    //End[006]
	    	var option = document.createElement("option");
	   		option.text = gchargeName[i];
			option.value = gchargeid[i];
			option.title = gchargeName[i];
			try 
			{
				local_typeCombo.add(option, null); //Standard
			}
			catch(error) 
			{
				local_typeCombo.add(option); // IE only
			}
	    }
    }
    catch(e)
	{
		alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
		showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
	}
}

function calculateTotalChargeAmount(index)
{
	//calculateTotalAmount();
}
///////////////////////////////
function checkContractPeriod(counter)

{



	 if((parseFloat(document.getElementById('txtCPeriod'+counter).value))>(parseFloat(document.getElementById('txtCntrtMonths').value)))
	 {
	 
		 alert("You cant enter Charge period greater than Contract Period");
		 document.getElementById('txtCPeriod'+counter).value=document.getElementById('txtCntrtMonths').value;
		 
		 return false;
	 }
 
 }	

function AddCharge()
{	
	var contractPeriod;
	if(document.forms[0].txtCntrtMonths!=null)
	{
		contractPeriod=document.forms[0].txtCntrtMonths.value;
		if(contractPeriod==null || contractPeriod=='')
		{
			alert('Please Select PO first');
			return ;
		}
	}
	else
	{
		alert('No PO selected');
		return;
	}
	//start [003]
	if(document.getElementById('hdnHardwareInfo').value==1)
	{
		if(document.getElementById('txtBillingformat').value == "0")
		{
			alert('No bill format selected, please select bill format first');
			return;
		}
	}
	//end [003]
	var counter = parseInt(document.getElementById('hdnChargeCount').value)+1;

//	var account = parseInt(document.getElementById('hdnPOAccount').value);
	document.getElementById('hdnChargeCount').value = counter;
	
	var str;
	//--[078]--start--//
	//counter = document.getElementById('tableCharges').rows.length;
	//--[078]--end--//
	chargeSumCounter++;
	
	var tblRow=document.getElementById('tableCharges').insertRow();
	
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col1 toprow";
	str ="<input name='chkSelectChargeDetail' id='chkSelectChargeDetail' type='checkbox' value='"+counter+"' onclick='allChargesCheckNone()'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col2";
	str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px' name='ddCType' id='ddCType"+counter+"' isRequired='0' Disp=''  class='dropdownMargin' onchange='javascript:fillChargeNames("+counter+",this.value);changeFrequency("+counter+");calculateFreqAmount("+counter+");fillEndDateLogic("+counter+");'><option value='-1'>Select a Type</option></select>";
	str=str+"<input type='hidden' name='hdnChargeId' id='hdnChargeId"+counter+"' value=''>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	//start [003]
	if(document.getElementById('hdnHardwareInfo').value==1)
	{				
		var local_typeCombo = document.getElementById("ddCType"+counter);
		var productid = document.getElementById('serviceDetailID').value;			
		if(document.getElementById('txtBillingformat').value==2)
		{					
			fillChargeTypeForHardware(path,productid,1,local_typeCombo);						
		}
		if(document.getElementById('txtBillingformat').value==3)
		{		
			fillChargeTypeForHardware(path,productid,2,local_typeCombo);			
		}		
	}
	else
	{	
		fillChargeType(counter);
	}
	//fillChargeType(counter);
	//end [003]
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col3";
	str="<select onfocus='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px' name='txtCName' id='txtCName"+counter+"' isRequired='0' Disp='' onchange='fillAnnotation("+counter+");getLineNSublineItemLbl("+counter+");getTaxRate("+counter+");' class='dropdownMargin'><option value='-1'>Select Name</option></select>";
	str=str+"<input type='hidden' name='hdnChargeName' value=''>";
	//[012] Start
	str=str+"<input type='hidden' name='hdnFxChargeID' id='hdnFxChargeID"+counter+"'>";
	//[012] End
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	//add [001] 
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col3";
	//[013] Start
	if(document.getElementById('hdnHardwareInfo').value==1)
	{
	str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder1' name='txtCAnnotation' id='txtCAnnotation"+counter+"' onblur=\"if(this.value.length > 0){if(CheckMinLength(this)){return ValidateTextField(this,path,'Annotation')}}\"  maxlength='240'>";
	}
	else
	{
	str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder1' name='txtCAnnotation' id='txtCAnnotation"+counter+"' onblur=\"if(this.value.length > 0){if(CheckMinLength(this)){return ValidateTextField(this,path,'Annotation')}}\"  maxlength='340'>";
	}
	//[013] End
	CellContents = str;
	tblcol.innerHTML = CellContents;
	//end [001]
	
	//start [005]
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col5";
	str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtTaxRate' id='txtTaxRate"+counter+"' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;		
	//end [005]
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col6";
	str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' maxlength='4' class='inputBorder1' name='txtCPeriod' id='txtCPeriod"+counter+"' value='"+contractPeriod+"' onblur='if(this.value.length > 0){return checkdigits(this)}'>";
	str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' maxlength='4' class='inputBorder1' name='txtCPeriod' id='txtCPeriod"+counter+"' value='"+contractPeriod+"' onblur='if(this.value.length > 0){if(checkdigits(this)){fillFrequency("+counter+");}else{return false;}}'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col7";
	//str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder1' maxlength='15' name='txtCTAmt' id='txtCTAmt"+counter+"' onkeyup='if(this.value.length > 0){if(checknumber(this)){calculateFreqAmount("+counter+");calculateTotalChargeAmount("+counter+");}else{calculateFreqAmount("+counter+");return false;}}'  > ";
	//Meenakshi: Change for allowing decimal and not allowing -ve charge
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder1' maxlength='15' name='txtCTAmt' id='txtCTAmt"+counter+"' oldvalue='' onkeyup='if(this.value.length > 0){if(checknumber_max2decimal_charges_section(this,0)){this.oldvalue=this.value;calculateFreqAmount("+counter+");calculateTotalLineAmount(this.value,"+chargeSumCounter+");}else{this.value=this.oldvalue;calculateFreqAmount("+counter+");calculateTotalLineAmount(this.value,"+chargeSumCounter+");return false;}}else{this.oldvalue=this.value;calculateTotalLineAmount(this.value,"+chargeSumCounter+");return false;}' onblur='if(this.value.length > 0){if(checknumber_max2decimal_charges_section(this,1)){this.oldvalue=this.value;calculateFreqAmount("+counter+");calculateTotalLineAmount(this.value,"+chargeSumCounter+");}else{this.value=this.oldvalue;calculateFreqAmount("+counter+");calculateTotalLineAmount(this.value,"+chargeSumCounter+");return false;}}else{this.oldvalue=this.value;calculateTotalLineAmount(this.value,"+chargeSumCounter+");return false;}'  > ";
	str=str+"<input type='hidden' name='hdnCTAmt' id='hdnCTAmt"+chargeSumCounter+"' > ";

	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col8";
	str = "<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px' name='txtCFrequency' id='txtCFrequency"+counter+"' Disp='' isRequired='0' class='dropdownMargin' disabled='true' onchange='calculateFreqAmount("+counter+");' ><option value='-1'>Select a Frequency Type</option></select>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	fillFrequency(counter);
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col9";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' class='inputDisabled' Disp='' isRequired='0' name='txtCFreqAmt' id='txtCFreqAmt"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}' readonly='readonly'> ";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	//Start [010]
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col4";
	str="<table><tr><td><select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' name='txtCStartDate' id='txtCStartDate"+counter+"' isRequired='0' Disp='' class='dropdownMargin' style='width:125px;'><option value='-1'>Select Start Date Logic</option><option value='BTD'>Billing Trigger</option></select></td>";
	str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtStartDays'  id='txtStartDays"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'></td>";
	str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtStartMonth' id='txtStartMonth"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'><input type='hidden' name='hdnstartdate' value=''></td></tr></table>";
	CellContents = str;
	tblcol.innerHTML = CellContents;

	/*var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtStartDays'  id='txtStartDays"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtStartMonth' id='txtStartMonth"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'><input type='hidden' name='hdnstartdate' value=''>";
	CellContents = str;
	tblcol.innerHTML = CellContents;*/

	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col4";
	str="<table><tr><td><select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' name='txtCEndDate' id='txtCEndDate"+counter+"' isRequired='0' Disp='' class='dropdownMargin' onchange='changeEndDays("+counter+");' style='width:125px;'><option value='-1'>Select End Date Logic</option></select></td>";
	str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtEndDays' id='txtEndDays"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'></td>";
	str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;'  value='0' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtEndMonth' id='txtEndMonth"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'></td></tr></table>";
	CellContents = str;
	tblcol.innerHTML = CellContents;

	/*var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtEndDays' id='txtEndDays"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;

	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;'  value='0' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtEndMonth' id='txtEndMonth"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;*/
	//End [010]
	
	
	//<!-- Start   [008] -->
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col3";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' value=''  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtAnnualCharge' id='txtAnnualCharge"+counter+"' readonly='readonly'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	//<!-- End   [008] -->
	
	//Start [00777] 30 JAN
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	tblcol.className="innerdata col3";
	str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' name='cmbLineItem' style='display:none;width:175px;' id='cmbLineItem"+counter+"' isRequired='0' Disp=''  class='dropdownMargin'><option value='-1'>Select Line Item</option></select>";
	str=str+"<input type='hidden' name='hdnChargeName' value=''>";
	str=str+"<label id='lineItemLbl"+counter+"'> &nbsp; </label> ";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	fillLineItem(counter);
	var tblcol=tblRow.insertCell();
	tblcol.align = "center";
	tblcol.vAlign="top";
	tblcol.className="innerdata col3";
	str="<select onfocus='getTip_DD(this,this.selectedIndex,this.id)' name='cmbSubLineItem' style='display:none;width:175px;' id='cmbSubLineItem"+counter+"' isRequired='0' Disp=''  class='dropdownMargin'><option value='-1'>Select Sub Line Item</option></select>";
	//str=str+"<input type='hidden' name='hdnChargeName' value=''>";
	str=str+"<label id='subLineItemLbl"+counter+"'> &nbsp; </label> ";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	fillSubLineItem(counter);
	//End [000777]30 JAN
	//Start [010]
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col3";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' value=''  isRequired='0' Disp='' maxlength='300' class='inputBorder' name='txtRemarks' id='txtRemarks"+counter+"'   onBlur=\"if( trim(this.value).length>0){return ValidateTextField(this,path,'Remarks')};\">";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col3";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' value=''  isRequired='0' Disp='' maxlength='50' class='inputBorder' name='txtStartDate' id='txtStartDate"+counter+"' readonly='readonly'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col3";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' value=''  isRequired='0' Disp='' maxlength='50' class='inputBorder' name='txtEndDate' id='txtEndDate"+counter+"' readonly='readonly'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	//End [010]

	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col1";
	str ="<input name='chkexcludeCharges' id='chkexcludeCharges"+counter+"' type='checkbox'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;

	//Billing Efficiencu in charge Details
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col13";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:90px;' value=''  isRequired='0' Disp='' maxlength='10' class='inputBorder' name='txtLDDateClause' id='txtLDDateClause"+counter+"' readonly='true'><img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.txtLDDateClause" + counter + ");\" style='vertical-align: bottom'/></font>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col11";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:50px;' value=''  isRequired='0' Disp='' maxlength='2' class='inputBorder' name='txtDelayedTimeInDays' id='txtDelayedTimeInDays"+counter+"' onblur='if(this.value.length > 0){if(checkdigitsValidation(this)){checkUperLimit(this.value,this.id,30,"+counter+");}else {return false;}}'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col13";
	str = "<table><tr><td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:50px;' value=''  isRequired='0' Disp='' maxlength='3' class='inputBorder' name='txtLDPercentage' id='txtLDPercentage"+counter+"' onblur='if(this.value.length > 0){if(checkdigitsValidation(this)){checkUperLimit(this.value,this.id,100,"+counter+");}else {return false;}}'></td>";
	str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:50px;' value=''  isRequired='0' Disp='' maxlength='3' class='inputBorder' name='txtMaxPenaltyPercentage' id='txtMaxPenaltyPercentage"+counter+"' onblur='if(this.value.length > 0){if(checkdigitsValidation(this)){checkUperLimit(this.value,this.id,100,"+counter+");}else {return false;}}'></td></tr></table> ";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	//End Billing Efficiency
//[011] Start
//if(document.getElementById('hdnHardwareInfo').value==1)
	//{		
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata col12";
		str = "<table><tr><td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtPayment1' id='txtPayment1"+counter+"' onblur='if(this.value.length > 0){checkdigits(this)}' ></td>";
		str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtPayment2' id='txtPayment2"+counter+"' onblur='if(this.value.length > 0){checkdigits(this)}'   ></td> ";
		str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtPayment3' id='txtPayment3"+counter+"' onblur='if(this.value.length > 0){checkdigits(this)}'  ></td> ";
		str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtPayment4' id='txtPayment4"+counter+"' onblur='if(this.value.length > 0){checkdigits(this)}'  ></td></tr></table> ";
		CellContents = str;
		tblcol.innerHTML = CellContents;
	/*}
	else
	{
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata col12";
		str = "<table><tr><td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtPayment1' id='txtPayment1"+counter+"'></td>";
		str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtPayment2' id='txtPayment2"+counter+"' ></td> ";
		str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtPayment3' id='txtPayment3"+counter+"' ></td> ";
		str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtPayment4' id='txtPayment4"+counter+"' ></td></tr></table> ";
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}*/


	if(counter>=0)
	{
		document.getElementById('SelectAllChckCharges').disabled=false;
	}


	var ctrlArry1 = new Array();
	ctrlArry1[0] =  document.getElementById("ddCType"+counter);
	ctrlArry1[1] =  document.getElementById("txtCName"+counter);
	ctrlArry1[2] =  document.getElementById("txtCPeriod"+counter);
	ctrlArry1[3] =  document.getElementById("txtCTAmt"+counter);
	ctrlArry1[4] =  document.getElementById("txtCFrequency"+counter);
	ctrlArry1[5] =  document.getElementById("txtCFreqAmt"+counter);
	ctrlArry1[6] =  document.getElementById("txtCStartDate"+counter);
	ctrlArry1[7] =  document.getElementById("txtCEndDate"+counter);
	ctrlArry1[8] =  document.getElementById("txtCAnnotation"+counter);
	ctrlArry1[9] =  document.getElementById("txtRemarks"+counter);
//	ctrlArry1[10] =  document.getElementById("cmbLineItem"+counter);
//	ctrlArry1[11] =  document.getElementById("cmbSubLineItem"+counter);
	
	var callerWindowObj = dialogArguments;		
	fieldRoleMappingValidation(callerWindowObj.linesTabFieldList,ctrlArry1);
	
	//start [004]
	document.getElementById("txtCStartDate"+counter).selectedIndex=1;
	//document.getElementById("txtCEndDate"+counter).selectedIndex=2;
	//end [004]
}
//Start [000777] JAN 30 Adding Line Item and Sub Line Item Combo in Charges Section***************************
function fillLineItem(var_counter)
{
	try
	{
	   var combo = document.getElementById('cmbLineItem'+var_counter);	
	   var iCountRows = combo.length;
	   for (i = 1; i <  iCountRows; i++)
	   {
	       combo.remove(i);
	   }
	    //Fill Line Item
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		var lineItemComboList = jsonrpc.processes.populateLineItem();			
	    for(i=0;i<lineItemComboList.list.length;i++)
	    {
	    	var option = document.createElement("option");
	   		option.text = lineItemComboList.list[i].lineItemName;
			option.value = lineItemComboList.list[i].lineItemId;
			option.title = lineItemComboList.list[i].lineItemName;
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
    catch(e)
	{
		alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
		showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
	}
}
function fillSubLineItem(var_counter)
{
	try
	{
	   var combo = document.getElementById('cmbSubLineItem'+var_counter);	
	   var iCountRows = combo.length;
	   for (i = 1; i <  iCountRows; i++)
	   {
	       combo.remove(i);
	   }
	    //Fill Line Item
		jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		var subLineItemComboList = jsonrpc.processes.populateSubLineItem();			
	    for(i=0;i<subLineItemComboList.list.length;i++)
	    {
	    	var option = document.createElement("option");
	   		option.text = subLineItemComboList.list[i].subLineItemName;
			option.value = subLineItemComboList.list[i].sublineItemId;
			option.title = subLineItemComboList.list[i].subLineItemName;
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
    catch(e)
	{
		alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
		showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
	}
}
//End   [000777] JAN 30 Adding Line Item and Sub Line Item Combo in Charges Section***************************

function fillChargeType(var_counter)
{
	try
	{
		if(gbchargeTypeCached==0)
		{
			//cache charge type
			var productid = document.getElementById('serviceDetailID').value
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			chargeCombo = jsonrpc.processes.populateChargeType(productid);
			gbchargeTypeIds=new Array();
			gbchargeTypeValues=new Array()
			for(z=0;z<chargeCombo.list.length;z++)
		    {
		    	gbchargeTypeIds[z] = chargeCombo.list[z].chargeTypeID;
				gbchargeTypeValues[z] = chargeCombo.list[z].chargeTypeName;
		    }
		}
		gbchargeTypeCached=1;
		//read from cache
		var local_typeCombo = document.getElementById("ddCType"+var_counter);
	    var local_RowCount = local_typeCombo.length;
	    for (i = 1; i <  local_RowCount; i++)
	    {
	       local_typeCombo.remove(1);
	    } 
	    for(i=0;i<gbchargeTypeIds.length;i++)
	    {
	    	var option = document.createElement("option");
	   		option.text = gbchargeTypeValues[i];
			option.value = gbchargeTypeIds[i];
			option.title = gbchargeTypeValues[i];
			try 
			{
				local_typeCombo.add(option, null); //Standard
			}
			catch(error) 
			{
				local_typeCombo.add(option); // IE only
			}
	    }
    }
    catch(e)
	{
		alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
		showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
	}
}


function fillChargeName(var_counter)
{
	try
	{
			//cache charge type
			
			var chargetype = document.getElementById("ddCType"+var_counter).value;
			var productid = document.getElementById('serviceDetailID').value;
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			chargeCombo = jsonrpc.processes.populateChargeName(chargetype,productid);
			gchargeName=new Array();
			gchargeid=new Array()
			for(z=0;z<chargeCombo.list.length;z++)
		    {
		    	gchargeName[z] = chargeCombo.list[z].chargeName;
				gchargeid[z] = chargeCombo.list[z].chargeName;
		    }
		//read from cache
		var local_typeCombo = document.getElementById("txtCName"+var_counter);
	    var local_RowCount = local_typeCombo.length;
	    for (i = 1; i <  local_RowCount; i++)
	    {
	       local_typeCombo.remove(1);
	    } 
	    for(i=0;i<gbchargeTypeIds.length;i++)
	    {
	    	var option = document.createElement("option");
	   		option.text = gchargeName[i];
			option.value = gchargeid[i];
			option.title = gchargeName[i];
			try 
			{
				local_typeCombo.add(option, null); //Standard
			}
			catch(error) 
			{
				local_typeCombo.add(option); // IE only
			}
	    }
    }
    catch(e)
	{
		alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
		showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
	}
}

function fillFrequency(var_counter)
{

    checkContractPeriod(var_counter);

		gbfrequencyCached=0;
		if(gbfrequencyCached==0)
		{
			//cache charge type
			try
			{
				jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
				frequencyCombo = jsonrpc.processes.populateFrequencyType(document.getElementById('txtCPeriod'+var_counter).value);
			}
		    catch(e)
			{
				alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
				showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
				return;
			}
			gbfrequencyId=new Array();
			gbfrequencyName=new Array();
			factorVal=new Array();
			for(z=0;z<frequencyCombo.list.length;z++)
		    {
		    	gbfrequencyId[z] = frequencyCombo.list[z].frequencyID;
				gbfrequencyName[z] = frequencyCombo.list[z].frequencyName;
				factorVal[z]= frequencyCombo.list[z].factor;
		    }
		}
		gbfrequencyCached=1;
		//read from cache
		var local_Combo = document.getElementById("txtCFrequency"+var_counter);
	    var local_RowCount = local_Combo.length;
	    for (i = 1; i <  local_RowCount; i++)
	    {
	       local_Combo.remove(1);
	    } 
	    for(i=0;i<gbfrequencyId.length;i++)
	    {
	    	var option = document.createElement("option");
	   		option.text = gbfrequencyName[i];
			option.value = gbfrequencyId[i];
			option.title = gbfrequencyName[i];
			try 
			{
				local_Combo.add(option, null); //Standard
			}
			catch(error) 
			{
				local_Combo.add(option); // IE only
			}
	    }
    
}

function changeFrequency(var_counter)
{
	if(document.getElementById('ddCType'+var_counter).value=="1")
	{
		document.getElementById('txtCFrequency'+var_counter).disabled=false;
		document.getElementById('txtCFrequency'+var_counter).value='-1';
	}
	else
	{
		document.getElementById('txtCFrequency'+var_counter).disabled=true;
		document.getElementById('txtCFrequency'+var_counter).value='-1';
	}
}
function DeleteCharge()
{
	//var myForm=document.getElementById('searchForm');
	var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
	if(chkChargeLine==null)
	{
		alert('No Charge Present');
		return;
	}

	var len=chkChargeLine.length;
	if(len==0)
	{
		 alert('No Charge Present');
		 document.getElementById('SelectAllChckCharges').checked=false;
		 document.getElementById('SelectAllChckCharges').disabled=true;
		return;
	}
	if(len==null)
	{//only one chk box present
	//alert(1);
		if(chkChargeLine.checked==true)
		{
			var answer = confirm("Do You Want To Continue Deleting Charge Line")
			if(answer)
			{
				//continue deleting
			}
			else
			{
				return false;
			}
			deleteChargeLine(chkChargeLine);
		}
		else
		{
			alert('No Charge Selected.');
			return;
		}
	}
	else
	{//multiple checkboxex present
		var count=0;
		var answer = confirm("Do You Want To Continue Deleting Charge Line");
		for(i=len-1;i>=0;i--)
		{
			if(chkChargeLine[i].checked==true)
			{
				//var answer = confirm("Do You Want To Continue Deleting Charge Line")
				if(answer)
				{
					//continue deleting
				}
				else
				{
					return false;
				}
				deleteChargeLine(chkChargeLine[i]);
				count=count+1;
			}
		}
		if(count==0)
		{
			alert('No Charge Selected.');
			return;
		}
	}
		if(chkChargeLine.length==0)
			{
				 document.getElementById('SelectAllChckCharges').checked=false;
				 document.getElementById('SelectAllChckCharges').disabled=true;
				
			}
	
		calculateTotalLineAmount('fromDeleteFunction',null);
		//calculateTotalAmount();
}
function deleteChargeLine(current) {

            //here we will delete the line
            while ( (current = current.parentElement)  && current.tagName !="TR");
            current.parentElement.removeChild(current);
      }  

function deletedAllCharges()
{//return;
// to cache the frequency again if the user selects different PO Detail No.
gbfrequencyCached=0;
	var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
	if(chkChargeLine.length==0)
	{
		return;
	}
	else
	{
	//	alert("Please fill Charge Details Again.");
	}
	for(i=chkChargeLine.length-1;i>=0;i--)
	{
		deleteChargeLine(chkChargeLine[i]);
	}
	document.getElementById('totalLineAmount').value= 0.0 ;
}
function roundNumber(num, dec) {
	var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
	return result;
}
function calculateFreqAmount(index)
{
//calculateFreqAmt("+counter+")
	//delete earlier f amount
	//if tot amount or freq or type is blank/unseleted -> return
	//else if NRC simply copy
	//if RC calculate
	document.getElementById('txtCFreqAmt'+index).value='';
	//alert(index);
	//alert(document.getElementById('ddCType'+index).value);
	if(document.getElementById('ddCType'+index).value=='2')
	{
		var totAm=document.getElementById('txtCTAmt'+index).value;
		//alert(totAm);
		if(totAm!=null && totAm!='')
		{
			//document.getElementById('txtCFreqAmt'+index).value=Math.round(totAm);
			document.getElementById('txtCFreqAmt'+index).value=totAm;
		}
	}
	else if(document.getElementById('ddCType'+index).value=='1')
	{
		var totAm=document.getElementById('txtCTAmt'+index).value;
		var freq=document.getElementById('txtCFrequency'+index).value;
		if(totAm!=null && totAm!='' && freq!='-1')
		{
			formulaFreqAmt(index);
		}
	}
		//calculateTotalAmount()		
}

/*Created By Saurabh to calculate the total line amount and display it in a separate column*/
/*Start calculateTotalLineAmount()*/
function calculateTotalLineAmount(ctrlValue,counter)
{
	if(ctrlValue!='fromDeleteFunction' && counter != null){
		if(document.getElementById("hdnCTAmt"+counter)!=null){
			document.getElementById("hdnCTAmt"+counter).value=ctrlValue;
	}}
	document.getElementById('totalLineAmount').value= 0.0 ;
	
	for(i=1;i<=chargeSumCounter;i++)
	{	
		if(document.getElementById("hdnCTAmt"+i)!=null)
		{
			if(document.getElementById("hdnCTAmt"+i).value==null || document.getElementById("hdnCTAmt"+i).value=="")
				document.getElementById("hdnCTAmt"+i).value=0.0;
			
			document.getElementById('totalLineAmount').value = parseFloat(document.getElementById('totalLineAmount').value) + parseFloat(document.getElementById("hdnCTAmt"+i).value);
		}
	}
	/*below method is called to assign lineAmount to hardware lineItem serviceSummary attribute(2D.Indicative Value)*/
	calculateTotalAmount(); 
}
/*End calculateTotalLineAmount()*/

function formulaFreqAmt(index)
{
	var frqAmount=0;
	var annualAmount=0;
	var factor=factorVal[document.getElementById("txtCFrequency"+index).selectedIndex-1];
	var Cperiod=document.getElementById("txtCPeriod"+index).value;
	var TotalAmount=document.getElementById("txtCTAmt"+index).value;
	frqAmount=((TotalAmount/Cperiod)*factor).toFixed(2);
	//alert("factor="+factor+",Cperiod="+Cperiod+",TotalAmount="+TotalAmount+",frqAmount="+frqAmount);
	//<!-- Start   [008] -->
	annualAmount=(frqAmount*12/factor);
	document.getElementById("txtCFreqAmt"+index).value=roundNumber(frqAmount,2);
	document.getElementById("txtAnnualCharge"+index).value=Math.round(annualAmount);
	//<!-- End   [008] -->
}	
/*Function Name	:	fillAnnotation
	@Author		:	Anil Kumar
	Date		:	15-feb-11
	Purpose		:	To display the value of annotation on selection of charge name
*/
function fillAnnotation(var_counter)
{	
	var annotationText=document.getElementById('txtCAnnotation'+var_counter).value;	
	var chargeNameCombo=document.getElementById('txtCName'+var_counter);		   
	var index=chargeNameCombo.selectedIndex;	
	if(index!="0")
	{
		document.getElementById('txtCAnnotation'+var_counter).value=chargeNameCombo.options[index].text;	
		annotationText=document.getElementById('txtCAnnotation'+var_counter).value;
	}
	else
	{	
		document.getElementById('txtCAnnotation'+var_counter).value=annotationText;
	}
}
//start [005]
function getTaxRate(var_counter)
{		
	if(document.getElementById("txtTaxation").value==1)
	{
	fetchTaxRate(path,var_counter);
	}
	else if(document.getElementById("txtTaxation").value==2)
	{
		document.getElementById("txtTaxRate"+var_counter).value="";
	}
}
//lawkush
function fillEndDateLogic(counter)
{
	var endDateLogic=document.getElementById('txtCEndDate'+counter).value;	
	var endDateLogicCombo=document.getElementById('txtCEndDate'+counter);		   
	var index=endDateLogicCombo.selectedIndex;
	var comboLength=endDateLogicCombo.length;
	
	for (i = comboLength+1; i >0 ; i--)
	    {
	       endDateLogicCombo.remove(1);
	    }
	
	if(document.getElementById('ddCType'+counter).value==2)
	{
		 
	    
	//document.getElementById('txtCEndDate'+counter).value=0;
	//document.getElementById('txtCEndDate'+counter).option='One Time Charge';
	//<option value='-1'>Select End Date Logic</option>
	//<option value='BTD'>Billing Trigger Date</option>
	//<option value='TD'>Till Disconnection</option></select>
	    	var option = document.createElement("option");
	   		option.text = 'One Time Charge';
	   		option.title = 'One Time Charge';
			option.value = 'OT';
			
			try 
			{
				endDateLogicCombo.add(option, null); //Standard
			}
			catch(error) 
			{
				endDateLogicCombo.add(option); // IE only
			}
			document.getElementById("txtCEndDate"+counter).selectedIndex=1;
	
	}
	else
	{
	    {
	    	var option = document.createElement("option");
	   		option.text = 'Billing Trigger Date';
	   		option.title = 'Billing Trigger Date';
			option.value = 'BTD';
			try 
			{
				endDateLogicCombo.add(option, null); //Standard
			}
			catch(error) 
			{
				endDateLogicCombo.add(option); // IE only
			}
	    }
	    {
	    	var option = document.createElement("option");
	   		option.text = 'Till Disconnection';
	   		option.title = 'Till Disconnection';
			option.value = 'TD';
			try 
			{
				endDateLogicCombo.add(option, null); //Standard
			}
			catch(error) 
			{
				endDateLogicCombo.add(option); // IE only
			}
			document.getElementById("txtCEndDate"+counter).selectedIndex=2;
	    }
	}    
	
}
//end [005]
	
function getLineNSublineItemLbl(var_counter)
{	

	var jsData =
			{								
				chargeInfoID:document.getElementById("txtCName"+var_counter).value
			};	
	var jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var lstLineNSublineItemLbl = jsonrpc.processes.getLineNSublineItemLbl(jsData);

	 	if(lstLineNSublineItemLbl.list[0].lineItemLbl=='NA' || lstLineNSublineItemLbl.list[0].lineItemLbl==null)
	 	{
	 		document.getElementById("lineItemLbl"+var_counter).innerHTML="&nbsp;";
	 	}
	 	else
	 	{
			document.getElementById("lineItemLbl"+var_counter).innerHTML=lstLineNSublineItemLbl.list[0].lineItemLbl;
		}
		if(lstLineNSublineItemLbl.list[0].subLineItemLbl=='NA' || lstLineNSublineItemLbl.list[0].subLineItemLbl==null)
	 	{
			document.getElementById("subLineItemLbl"+var_counter).innerHTML="&nbsp;";
	 	}
	 	else
	 	{
			document.getElementById("subLineItemLbl"+var_counter).innerHTML=lstLineNSublineItemLbl.list[0].subLineItemLbl;
		}
		//FX_CHARGEID RETREIVAL
		//[012] Start
		if(lstLineNSublineItemLbl.list[0].fxChareId=='NA' || lstLineNSublineItemLbl.list[0].fxChareId==null)
	 	{
	 		document.getElementById("hdnFxChargeID"+var_counter).value="&nbsp;";
	 	}
	 	else
	 	{
			document.getElementById("hdnFxChargeID"+var_counter).value=lstLineNSublineItemLbl.list[0].fxChareId;
		}
		//[012] End
	
}
//lawkush start

 function fnCheckChargesAll()
{
  
	    var i;
        var myForm=document.getElementById('productCatelog');		
	    var counter=1;
	    chargesTab = document.getElementById('tableCharges');
        var rowlen= chargesTab.rows.length;
         if(rowlen==0)
			 {
			 // alert("Please add charge");
			 document.getElementById('SelectAllChckCharges').checked=false;
			 document.getElementById('SelectAllChckCharges').disabled=true;
			 return false;
			 }
         var chkLength1=document.forms[0].chkSelectChargeDetail.length;
		if(document.getElementById('SelectAllChckCharges').checked==true)
		{
			        if(chkLength1==undefined)
	        
	                {
		               
		                    document.forms[0].chkSelectChargeDetail.checked=true;
		                
					}
          
			      else
			        {    
				         for (i =0; i<chkLength1; i++)
						   { 
								myForm.chkSelectChargeDetail[i].checked=true ;
					       }
				    }
	
	 
	         
	
		}	
	else
	   {
	           if(chkLength1==undefined)
        
		          {
			          document.forms[0].chkSelectChargeDetail.checked=false;
		          }
			      else
			      {    
			         for (i =0; i<chkLength1; i++)
					   { 
							        myForm.chkSelectChargeDetail[i].checked=false ;
						
					   }
				}
	
		       
		}
	
	


	
}
function allChargesCheckNone()
{
	document.getElementById('SelectAllChckCharges').checked=false;
}
//lawkush end

</script>
<script language='javascript' type='text/javascript'>
        function reposHead(e) {
            var h = document.getElementById('headscroll');
            h.scrollLeft = e.scrollLeft;
            var f = document.getElementById('divfrozen');
            f.scrollTop = e.scrollTop;
        }
        function reposHorizontal(e) {
            var h = document.getElementById('headscroll');
            var c = document.getElementById('contentscroll');
            h.scrollLeft = e.scrollLeft;
            c.scrollLeft = e.scrollLeft;
            
        }
        function reposVertical(e) {
            var h = document.getElementById('divfrozen');
            var c = document.getElementById('contentscroll');
            h.scrollTop = e.scrollTop;
            c.scrollTop = e.scrollTop;
           
        }
</script>
</head>
<body>
	
		<!--<input name="btn1" id="btnChargesDetails"
				onClick="show('tblChargesDetails',this)" type="button" value="+"
				style="width:20px;height:20px;background-color:#FFFFFF;border-bottom-width:1px;border-Top-width:1px;border-Left-width:1px;border-right-width:1px;border-color:#000000  ">&nbsp;-->
				<div align="left"><b>Charges Details:<font color="red" size="2">(Below Mentioned Annotation will be printed on Invoice)</font></b></div>	
	<table border="1" cellpadding="0" cellspacing="0" class='main' id="tblChargesDetails" >   
		<tr>
			<td class='tablefrozencolumn'>
                    <div id='divroot' class='root'>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" class='root'>
                            <tr>
                                <td class='inner frozencol colwidth head1'>
                                    
                                </td>
                            </tr>
                        </table>
                    </div>
                    <div id='divfrozen' class='frozen'>
                        <table border="0" cellpadding="0" cellspacing="0" width="100%" class='frozen'>
                            <tr>
                                <td class='inner frozencol toprow'>
                                    
                                </td>
                            </tr>                            
                        </table>
                    </div>
            </td>
            <td class='tablecontent' valign="top">
                    <div id='headscroll' class='divhead' style="vertical-align:top">                               	
                        <table border="0" cellpadding="0" cellspacing="0" class='head1' height="5%">
						 <input type="hidden" name="hdnChargeCount" value="0"/>        
                              <tr>
                                <td class='inner col1 head1'>
                                    Select <input type="checkbox" name="SelectAllChckCharges" id="SelectAllChckCharges" onclick="javascript:fnCheckChargesAll();" disabled="disabled"/>
                                </td>
                                <td class='inner col2 head1'>
                                    Type
                                </td>
                                <td class='inner col3 head1'>
                                    Name
                                </td>
                                <td class='inner col3 head1'>
                                    Annotation
                                </td>
                                <td class='inner col5 head1'>
                                    Tax Rate
                                </td>
                                <td class='inner col6 head1'>
                                    Charge Period(Months)
                                </td>
                                <td class='inner col7 head1'>
                                    PO Line Item Amount
                                </td>
                                <td class='inner col8 head1'>
                                    Billing Frequency
                                </td>
                                <td class='inner col9 head1'>
                                    Frequency Amount
                                </td>
                                <td class='inner col4 head1' valign="top">
                                    <table border="1" width="100%">
											<tr>
												<td align="center" colspan="3">Start Date Logic</td>
											</tr>
											<tr>
												<!-- Start   [003] -->
												<td  align="center" width="150px">Date Logic</td>
												<!-- End   [003] -->
												<td  align="center" width="150px">Days</td>
												<td  align="center" width="150px">Month</td>
											</tr>
								  </table>
                                </td>
                                <td class='inner col4 head1' valign="top">
                                    <table border="1" width="100%">
											<tr>
												<td align="center" colspan="3">End Date Logic</td>
											</tr>
											<tr>
												<!-- Start   [003] -->
												<td  align="center" width="150px">Date Logic</td>
												<!-- End   [003] -->
												<td  align="center" width="150px">Days</td>
												<td  align="center" width="150px">Month</td>
											</tr>
									</table>	
                                </td>
                                <td class='inner col3 head1'>
                                    Annual Charge Rate
                                </td>
                                <td class='inner col3 head1'>
                                    Line Item
                                </td>
                                <td class='inner col3 head1'>
                                    Sub Line Item
                                </td>
                                <td class='inner col3 head1'>
                                    Remarks
                                </td>
                                <td class='inner col3 head1'>
                                    Charge Start Date
                                </td>
                                <td class='inner col3 head1'>
                                    Charge End Date
                                </td>
                                <td class='inner col1 head1'>
                                    Exclude
                                </td>
                                <td class='inner col13 head1' >LD Date Clause</td>
								<td class='inner col11 head1' >Delayed Time Days (1 - 30)</td>
								<td class='inner col13 head1' valign="top">
									<table border="1" width="100%">
											<tr>
												<td align="left" colspan="2" width="100%">LD Percentage (1-100)</td>
											</tr>
											<tr>
												<td width="50%" nowrap >LD </td>
												<td width="50%" nowrap >Max Penalty </td>				                               
				                            </tr>
									</table>
                                </td>
                                <td class='inner col12 head1' valign="top">
                                	<table border="1" width="100%">
											<tr>
												<td align="center" colspan="4">Payment Term</td>
											</tr>
											<tr>
												<!-- Start   [003] -->
												<td  width="25%" nowrap>DND Dispatch But Not Delivered</td>
												<!-- End   [003] -->
												<td   width="25%" nowrap>DND Dispatch And Delivered</td>
												<td   width="25%" nowrap>DDNI Disp, Del Not Installed</td>
												<td   width="25%" nowrap>DDNI Disp, Delivered, Installed</td>
											</tr>
								  </table>
                                </td>                                
                            </tr>                            
                        </table>
                    </div>
                    <div id='contentscroll' class='content' onscroll='reposHead(this);'>
                        <table border="0" cellpadding="0" cellspacing="0" class='content' id='tableCharges'>                        	
                                                                            
                        </table>
                    </div>
                </td>
                <td class='tableverticalscroll' rowspan="2">
                    <div class='vertical-scroll' onscroll='reposVertical(this);'>
                        <div>
                        </div>
                    </div>
                    <div class='ff-fill'>
                    </div>
                </td>
			</tr>
			<tr>
                <td colspan="3">
                    <div class='horizontal-scroll' onscroll='reposHorizontal(this);'>
                        <div>
                        </div>
                    </div>
                </td>
            </tr>
            <tr>
				<td colspan="9" align="center"><b>Total Line Amount</b>
				<input type="text" name="totalLineAmount" id="totalLineAmount" value="0.0" readonly="readonly">
				</td>
			</tr>
			<tr>
				<td colspan="9" align="center">
					    <input type="button" name="btnAddContact" value="Add Charges" onClick="AddCharge()">
					    <input type="button" name="btnDeletePoDetail" value="Delete Charges" onClick="DeleteCharge()">
				</td>
			</tr>
	</table>	
</body>
</html>