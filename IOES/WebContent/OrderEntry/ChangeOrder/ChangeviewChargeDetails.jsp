<!--Tag Name Resource Name  Date		CSR No			Description -->
<!--[001]	 ANIL KUMAR	    14-feb-2011 				Annotation field added in charges section against charges name -->
<!--[002]	 Rohit Verma	17-feb-2011 				Logic Start Date and End Date Label Chnaged -->
<!--[003]	 ANIL KUMAR		26-feb-2011 				fetch Charge Type RC/NRC based on bill format for hardware in charge section-->
<!--[004]	 ANIL KUMAR 	08-march-2011				Start date logic and End date logic should be default in charge section-->
<!--[005] 	 ANIL KUMAR 	09-march-2011				display Tax Rate in charge section-->
<!--[006] 	 RAGHU	16-Mar-11	00-05422		TotalCharges auto populate in service summary in Toatal Feild-->
<!--[007] 	 Lawkush 		11-April-2011				In order to validate fields according to database driven mandatory or nonmandatory in all section-->
<!--[008]	 ANIL KUMAR		21-june-2011				Change Label PO Amount in charge details as PO Line Item Amount  -->
<!--[009]	 Vishwa			28-Sep-2011				    Annual Charge Rate is added  -->
<!--[010]	 Saurabh		03-Oct-2011				    Some rows and column added in charge info -->
<!--[077]    ASHUTOSH SINGH  01-DEC-11  00-05422        Changes in PO AMOUNT(Decimal validation) -->
<!--[078]    Vijay           08-Feb-13                  In Charges detals Charge Type dropdown was not working properly if user delete middle charge and then add charge, So modified some code for resolution. -->
<!--[011]	 Rohit Verma	 						    Customer Billing Experence--Making Payment Term editable for hardware and Service-BFR13-->
<!--[013]    Gunjan          25-Apr-14                   added a new column 'Latest State Logic'-->
<!-- [014] VIPIN SAHARIA 04-06-2014 Added hidden field for FxChargeID + logic required for extra logic DC_COLO_SERVICE_TAX Charge (Hardware DC) -->
<!-- [015] Raveendra Kumar  15-May-2015 20141219-R2-020936-Billing Efficiency Program      Annotation length in IB2B-->
<!-- [0016] Gunjan Singla 24-Apr-2015   20150202-R2-021037    Suppression of creation of new billable account in case of change order -->
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="com.ibm.ioes.utilities.Messages"%>
<html>
<head>
<title>viewChargeDetails</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="GENERATOR" content="Rational Software Architect">
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
var callerWindowObj = dialogArguments;	
path="<%=request.getContextPath()%>";
path1='<%=request.getSession().getId() %>';

//added order_creation_source is 2 for CLEP
var ORDER_CREATION_SOURCE = 2;
//added order_creation_source is 2 for CLEP
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

function calculateTotalChargeAmount(index)
{
	//calculateTotalAmount();
}

function fillChargeNames(var_counter, chargeTypeValue,chargeNameID)
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
	    for (i = 1; i <  local_RowCount; i++)
	    {
	       local_typeCombo.remove(1);
	    } 
	    for(i=0;i<chargeCombo.list.length;i++)
	    {
	    	var option = document.createElement("option");
	   		option.text = gchargeName[i];
	   		option.title = gchargeName[i];
			option.value = gchargeid[i];
			try 
			{
				local_typeCombo.add(option, null); //Standard
			}
			catch(error) 
			{
				local_typeCombo.add(option); // IE only
			}
	    }	 
	     local_typeCombo.value=chargeNameID;
	     //alert(local_typeCombo.value);	     
    }
    catch(e)
	{
		alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
		showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
	}
}


///////////////////////////////
	
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
	   		option.title= 'Till Disconnection';
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

//inputAction : 1 for Add Charge Click ,2 : for Disconnect and Copy Click
function AddCharge(inputAction){

	if(!(isView == null || isView == 'null')){
					return;
		}
		//nagarjuna start
		/* if(document.getElementById('txtBillingLvl').value==2 && gb_addChrgeAllowed=="not_allowed" && inputAction=1){
			alert('You cannot add charges at Billing level is PO.');
			return false;
		} */
		//[0016] start
		/* if((evaluatedCondition_PoLevelScenario=="PO_LEVEL_DIFFERENTIAL_CASE_ENABLE" && inputAction==1)
				|| gbChargesAdditionForDisabledPo=="not_allowed"){
			alert('You cannot add charges at Billing level is PO.');
			return false;
		} */
		//[0016] end
		//nagarjuna end
		// commented by [0016] start
		/* if(document.getElementById('txtPODetailNo').value == 0 && document.getElementById('txtBillingLvl').value!=2 ){
			alert('Please Select PO first');
			return false;
		}else if(document.getElementById('txtBillingLvl').value==2 && editSolutionChangeOldProduct ==1){
			if(evaluatedCondition_PoLevelScenario=="PO_LEVEL_DIFFERENTIAL_CASE_ENABLE" && inputAction==2){
				//do nothing				
			}else{
				alert('You cannot add charges at Billing level is PO');
				return false;	
			}
		} */
		// commented by [0016] end
		//[0016] start
		if(document.getElementById('txtPODetailNo').value == 0 ){
			alert('Please Select PO first');
			return false;
		}
		//[0016] end
		
	var contractPeriod;
	if(document.forms[0].txtCntrtMonths!=null){
		contractPeriod=document.forms[0].txtCntrtMonths.value;		
		if(contractPeriod==null || contractPeriod==''){
			alert('Please Select PO first');
			return ;
		}
	}else{
		alert('No PO selected');
		return;
	}
	if(document.getElementById('hdnHardwareInfo').value==1){
		if(document.getElementById('txtBillingformat').value == "0"){
			alert('No bill format selected, please select bill format first');
			return;
		}
	}
	var counter = parseInt(document.getElementById('hdnChargeCount').value)+1;
//	var account = parseInt(document.getElementById('hdnPOAccount').value);
	document.getElementById('hdnChargeCount').value = counter;
	//Start[007]
	srvDetailId=document.getElementById("hdnServiceDetailID").value;
	//End[007]
	var str;
	//--[078]--start--//
	//counter = document.getElementById('tableCharges').rows.length;
	//--[078]--end--//
	var tblRow=document.getElementById('tableCharges').insertRow();
	chargeSumCounter++;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col1 toprow";
	str ="<input name='chkSelectChargeDetail' id='chkSelectChargeDetail' type='checkbox' value='"+counter+"'  onclick='allChargesCheckNone()' >";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
		
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";

	tblcol.className="innerdata col2";		
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder1' name='pkChargeId' id = 'pkChargeId"+counter+"' value=''>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
		
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";

	tblcol.className="innerdata col2";		
	str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px;' name='ddCType' id='ddCType"+counter+"' isRequired='0' Disp='' class='dropdownMargin' onchange='CheckRCRequiredForCLEP(this,"+counter+");setBlankChargeName("+counter+");changeFrequency("+counter+");calculateFreqAmount("+counter+");fillEndDateLogic("+counter+");'><option value='-1'>Select a Type</option></select>";

	str=str+"<input type='hidden' name='hdnChargeId' id ='hdnChargeId"+counter+"'  value=''>";
	str =str + "<input name='isAddedInCurrentService' id='isAddedInCurrentService"+counter+"' type='hidden' value='1'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	//start [003]
	if(document.getElementById('hdnHardwareInfo').value==1)
	{				
		var local_typeCombo = document.getElementById("ddCType"+counter);
		//var productid = document.getElementById('serviceDetailID').value;			
		if(document.getElementById('txtBillingformat').value==2)
		{					
			fillChargeTypeForHardware(path,srvDetailId,1,local_typeCombo);						
		}
		if(document.getElementById('txtBillingformat').value==3)
		{		
			fillChargeTypeForHardware(path,srvDetailId,2,local_typeCombo);			
		}		
	}
	else
	{	
		fillChargeType(counter,srvDetailId);
	}
	//fillChargeType(counter);
	//end [003]
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";

	tblcol.className="innerdata col3";	
	//str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px;' name='txtCName' id='txtCName"+counter+"' isRequired='0' Disp='' onchange='fillAnnotation("+counter+");getTaxRate("+counter+");getLineNSublineItemLbl("+counter+");' class='dropdownMargin'><option value='-1'>Select Name</option></select>";
	str="<input type='text' value='Select Charge Name' onmouseover='getTip(value)' readonly='true' counterval='"+ counter +"' onmouseout='UnTip()' style='width:160px' isRequired='0'  name='chargeName' ctrltype='dropdown' retval='AUTOSUGGESTCHARGENAME' id='chargeName" + counter + "' class='dropdownMargin'><a name='chargeNameLink' id='chargeNameLink" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownChargeName(" + counter + ")'>Show</a>";
	str=str+"<input type='hidden' name='txtCName' id='txtCName"+counter+"' isrequired='0' value='-1'>";
	str=str+"<input type='hidden' name='hdnChargeName' id='hdnChargeName"+counter+"' value=''>";
	//[014]Start
	str=str+"<input type='hidden' name='hdnFxChargeID' id='hdnFxChargeID"+counter+"'>";
	//[014] End
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	//add [001] 
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col3";
	//[015] Start
	if(document.getElementById('hdnHardwareInfo').value==1)
	{
	str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder1' name='txtCAnnotation' id='txtCAnnotation"+counter+"' onblur=\"if(this.value.length > 0){if(CheckMinLength(this)){return ValidateTextField(this,path,'Annotation')}}\"  maxlength='240'>";
	}
	else
	{
	str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder1' name='txtCAnnotation' id='txtCAnnotation"+counter+"' onblur=\"if(this.value.length > 0){if(CheckMinLength(this)){return ValidateTextField(this,path,'Annotation')}}\"  maxlength='340'>";
	}
	//[015] End
	CellContents = str;
	tblcol.innerHTML = CellContents;
	//end [001] 
	
	//start [005]
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col5";
	str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtTaxRate' id='txtTaxRate"+counter+"' readonly='true'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;			
	//end [005]
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col6";
	str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' maxlength='4' class='inputBorder1' name='txtCPeriod' id='txtCPeriod"+counter+"' value='"+contractPeriod+"' onblur='if(this.value.length > 0){return checkdigits(this)}'>";
	str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' maxlength='4' class='inputBorder1' name='txtCPeriod' id='txtCPeriod"+counter+"' value='"+contractPeriod+"' onblur='if(this.value.length > 0){if(checkdigits(this)){fillFrequency_Charge("+counter+",1);}else{return false;}}'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col7";
	//str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder1' maxlength='15' name='txtCTAmt' id='txtCTAmt"+counter+"' oldvalue='' onkeyup='if(this.value.length > 0){if(checknumber_max2decimal_charges_section(this,0)){this.oldvalue=this.value;calculateFreqAmount("+counter+");calculateTotalLineAmount(this.value,"+chargeSumCounter+");}else{this.value=this.oldvalue;calculateFreqAmount("+counter+");calculateTotalLineAmount(this.value,"+chargeSumCounter+");return false;}}else{calculateTotalLineAmount(this.value,"+chargeSumCounter+");return false;}' onblur='if(this.value.length > 0){if(checknumber_max2decimal_charges_section(this,1)){this.oldvalue=this.value;calculateFreqAmount("+counter+");calculateTotalLineAmount(this.value,"+chargeSumCounter+");}else{this.value=this.oldvalue;calculateFreqAmount("+counter+");calculateTotalLineAmount(this.value,"+chargeSumCounter+");return false;}}else{calculateTotalLineAmount(this.value,"+chargeSumCounter+");return false;}'  > ";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder1' maxlength='15' name='txtCTAmt' id='txtCTAmt"+counter+"' oldvalue='' counterVal='" + counter + "' chargeSumCounterVal='" + chargeSumCounter + "'  > ";
	str=str+"<input type='hidden' name='hdnCTAmt' id='hdnCTAmt"+chargeSumCounter+"' > ";
	str=str+"<input type='hidden' name='hdnCTAmtDisconnectType' id='hdnCTAmtDisconnectType"+chargeSumCounter+"' value = '0' > ";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	callOnAddedCharge(counter, chargeSumCounter);
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";

	tblcol.className="innerdata col8";	
	str = "<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px;' name='txtCFrequency' id='txtCFrequency"+counter+"' Disp='' isRequired='0' class='dropdownMargin' disabled='true' onchange='calculateFreqAmount("+counter+");' ><option value='-1'>Select a Frequency Type</option></select>";

	str= str+ "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='hidden' name='hdnPoNoOfCharge' id = 'hdnPoNoOfCharge"+counter+"' value='0'>"
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	fillFrequency_Charge(counter,1);
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col9";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' class='inputDisabled' Disp='' isRequired='0' name='txtCFreqAmt' id='txtCFreqAmt"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}' readonly='readonly'> ";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	//Start [010]
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col4";
	str="<select  name='txtCStartDate' id='txtCStartDate"+counter+"' isRequired='0' Disp='' class='dropdownMargin' style='width:125px;'><option value='-1'>Select Start Date Logic</option><option value='BTD'>Billing Trigger</option></select></td>";
	str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtStartDays'  id='txtStartDays"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'></td>";
	str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtStartMonth' id='txtStartMonth"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'><input type='hidden' name='hdnstartdate' value=''></td></tr></table>";
	CellContents = str;
	tblcol.innerHTML = CellContents;

	/*var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0' readonly='true' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtStartDays'  id='txtStartDays"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'>";
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
	str="<table><tr><td><select  name='txtCEndDate' id='txtCEndDate"+counter+"' isRequired='0' Disp='' class='dropdownMargin' onchange='changeEndDays("+counter+");' style='width:125px;'><option value='-1'>Select End Date Logic</option><option value='BTD'>Billing Trigger Date</option><option value='TD'>Till Disconnection</option></select></td>";
	str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='0'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtEndDays' id='txtEndDays"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'></td>";
	str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;'  value='0' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtEndMonth' id='txtEndMonth"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'></td></tr></table>";
	CellContents = str;
	tblcol.innerHTML = CellContents;

	/*var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' value='0' readonly='true' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtEndDays' id='txtEndDays"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;

	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;'  value='0' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtEndMonth' id='txtEndMonth"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;*/
	//End [010]
	
	
	//<!-- Start   [009] -->
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col3";
	str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' value=''  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtAnnualCharge' id='txtAnnualCharge"+counter+"' readonly='readonly'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	//<!-- End   [009] -->

	//Start [00777] 30 JAN
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col3";	
	str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' name='cmbLineItem' style='display:none;width:175px;' id='cmbLineItem"+counter+"' isRequired='0' Disp=''  class='dropdownMargin'><option value='-1'>Select Line Item</option></select>";

	str=str+"<input type='hidden' name='hdnChargeName' value=''>";
	str=str+"<label id='lineItemLbl"+counter+"'> &nbsp; </label> ";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	fillLineItem(counter);
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";

	tblcol.className="innerdata col3";	
	str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' name='cmbSubLineItem' style='display:none;width:175px;' id='cmbSubLineItem"+counter+"' isRequired='0' Disp=''  class='dropdownMargin'><option value='-1'>Select Sub Line Item</option></select>";

	str=str+"<input type='hidden' name='hdnChargeName' value=''>";
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
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' value=''  isRequired='0' Disp='' maxlength='300' class='inputBorder' name='txtRemarks' id='txtRemarks"+counter+"'  onBlur=\"if( trim(this.value).length>0){return ValidateTextField(this,path,'Remarks')};\">";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col3";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' value=''  isRequired='0' Disp='' maxlength='50' class='inputBorder' name='txtChargeStartDate' id='txtChargeStartDate"+counter+"'  readonly='readonly'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col3";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' value=''  isRequired='0' Disp='' maxlength='50' class='inputBorder' name='txtChargeEndDate' id='txtChargeEndDate"+counter+"'  readonly='readonly'>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	//End [010]

	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col1";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' class='inputBorder' name='chkexcludeCharges' id='chkexcludeCharges"+counter+"' type='checkbox'>";
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
	//lawkush
	//[011] Start
	/*
	if(document.getElementById('hdnHardwareInfo').value==1)
	{*/	
	//[011] End	
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata col12";
		str = "<table><tr><td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtPayment1' id='txtPayment1"+counter+"' onblur='if(this.value.length > 0){checkdigits(this)}' ></td>";
		str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtPayment2' id='txtPayment2"+counter+"' onblur='if(this.value.length > 0){checkdigits(this)}'   ></td> ";
		str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtPayment3' id='txtPayment3"+counter+"' onblur='if(this.value.length > 0){checkdigits(this)}'  ></td> ";
		str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtPayment4' id='txtPayment4"+counter+"' onblur='if(this.value.length > 0){checkdigits(this)}'  ></td></tr></table> ";
		CellContents = str;
		tblcol.innerHTML = CellContents;
	//[011] Start
	/*}
	else
	{
		var tblcol=tblRow.insertCell();
		tblcol.align = "left";
		tblcol.vAlign="top";
		tblcol.className="innerdata col12";
		str = "<table><tr><td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtPayment1' id='txtPayment1"+counter+"'></td>";
		str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtPayment2' id='txtPayment2"+counter+"'></td> ";
		str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtPayment3' id='txtPayment3"+counter+"'></td> ";
		str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtPayment4' id='txtPayment4"+counter+"'></td></tr></table> ";
		CellContents = str;
		tblcol.innerHTML = CellContents;
	}*/
	//[011] End
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col3";
	//str="<input type='text' isRequired='0' Disp='' maxlength='4' class='inputBorder'>";
	str="&nbsp;&nbsp;";
	CellContents = str;
	tblcol.innerHTML = CellContents;

	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col2";
	//str="<input type='text' isRequired='0' Disp='' maxlength='4' class='inputBorder'>";
	str="&nbsp;&nbsp;";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	var tblcol=tblRow.insertCell();
	tblcol.align = "left";
	tblcol.vAlign="top";
	tblcol.className="innerdata col2";
	str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;float:left' isRequired='0' Disp='' class='inputBorder' name='txtOBCharge' id='txtOBCharge"+counter+"' value='0' readonly='readonly'>";
	//str = str+"<div class='searchBg' id='divpopUpOBChargeLinking' style='float: left'><a href='#' onclick='popUpForDisconnectedChargeLookup("+counter+")' id='popUpOBChargeLinking"+counter+"' >...</a></div>";
	CellContents = str;
	tblcol.innerHTML = CellContents;
	
	if(counter>=0)
	{
		document.getElementById('SelectAllChckCharges').disabled=false;
	}
		
	//lawkush
	
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
	ctrlArry1[9] =  document.getElementById("txtTaxRate"+counter);
	ctrlArry1[10] =  document.getElementById("txtRemarks"+counter);
	ctrlArry1[11] =  document.getElementById("chargeName"+counter);
	ctrlArry1[12] =  document.getElementById("chargeNameLink"+counter);
//	ctrlArry1[13] =  document.getElementById("cmbLineItem"+counter);
//	ctrlArry1[14] =  document.getElementById("cmbSubLineItem"+counter);
	
	var callerWindowObj = dialogArguments;		

	fieldRoleMappingValidation(callerWindowObj.linesTabFieldList,ctrlArry1);
	
	//start [004]
	document.getElementById("txtCStartDate"+counter).selectedIndex=1;
	document.getElementById("txtCEndDate"+counter).selectedIndex=2;
	//end [004]
	attachCSForAS($("#chargeNameLink"+counter));
	//callOnChargeAmountChangeCtrl($("#txtCTAmt"+counter));
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
function checkContractPeriod(counter)


{



	 if((parseFloat(document.getElementById('txtCPeriod'+counter).value))>(parseFloat(document.getElementById('txtCntrtMonths').value)))
	 {
	 
		 alert("You cant enter Charge period greater than Contract Period");
		 document.getElementById('txtCPeriod'+counter).value=document.getElementById('txtCntrtMonths').value;
		 
		 return false;
	 }
 
 }

function fillChargeType(var_counter,serviceProductID)
{
	try
	{
		//if(gbchargeTypeCached==0)
		//{
			//cache charge type
			var productid = serviceProductID;						
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");			
			chargeCombo = jsonrpc.processes.populateChargeType(productid);			
			gbchargeTypeIds=new Array();
			gbchargeTypeValues=new Array()
			for(z=0;z<chargeCombo.list.length;z++)
		    {
		    	gbchargeTypeIds[z] = chargeCombo.list[z].chargeTypeID;
				gbchargeTypeValues[z] = chargeCombo.list[z].chargeTypeName;
		    }
		//}		
		//gbchargeTypeCached=1;
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
	   		option.title = gbchargeTypeValues[i];
			option.value = gbchargeTypeIds[i];
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


/*function fillAddChargeName(var_counter,ChargeType)
{	

	try
	{
			//cache charge type

			var chargetype = ChargeType;
			var productid = document.getElementById("hdnServiceDetailID").value;
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			chargeCombo = jsonrpc.processes.populateChargeName(chargetype,productid);
		
			gchargeName=new Array();
			gchargeid=new Array()
			for(z=0;z<chargeCombo.list.length;z++)
		    {
		    	gchargeName[z] = chargeCombo.list[z].chargeName;
				gchargeid[z] = chargeCombo.list[z].chargeTypeID;
		    }		    
		   var callerWindowObj = dialogArguments;		
		//read from cache
		var local_typeCombo = document.getElementById("txtCName"+var_counter);
	    var local_RowCount = local_typeCombo.length;
	    for (i = 1; i <  local_RowCount; i++)
	    {
	       local_typeCombo.remove(1);
	    } 
	    for(i=0;i<gchargeid.length;i++)
		{
	    	var option = document.createElement("option");
	   		option.text = gchargeName[i];
			option.value = gchargeid[i];
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
}*/

function fillFrequency_Charge(var_counter, chkContractPeriod){

		//Meenakshi : Change to restrict the change of Contract Period on Load of Charges Section
	if(chkContractPeriod ==1){
       		checkContractPeriod(var_counter);
       	}
		gbfrequencyCached=0;
	if(gbfrequencyCached==0){
			//cache charge type
		try{
				jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
				frequencyCombo = jsonrpc.processes.populateFrequencyType(document.getElementById('txtCPeriod'+var_counter).value);
		}catch(e){
				alert("<%=Messages.getMessageValue("javascriptExceptionMessage")%>");
				showJavascriptException(e,'<%=Messages.getMessageValue("javascriptExceptionShow")%>');
				return;
			}
			gbfrequencyId=new Array();
			gbfrequencyName=new Array();
			factorVal=new Array();
		for(z=0;z<frequencyCombo.list.length;z++){
		    	gbfrequencyId[z] = frequencyCombo.list[z].frequencyID;
				gbfrequencyName[z] = frequencyCombo.list[z].frequencyName;
				factorVal[z]= frequencyCombo.list[z].factor;
		    }
		}
		gbfrequencyCached=1;
		//read from cache
		var local_Combo = document.getElementById("txtCFrequency"+var_counter);
	    var local_RowCount = local_Combo.length;
	for (i = 1; i <  local_RowCount; i++){
	       local_Combo.remove(1);
	    } 
	for(i=0;i<gbfrequencyId.length;i++){
	    	var option = document.createElement("option");
	   		option.text = gbfrequencyName[i];
	   		option.title = gbfrequencyName[i];
			option.value = gbfrequencyId[i];
		try{
				local_Combo.add(option, null); //Standard
		}catch(error){
				local_Combo.add(option); // IE only
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
		document.getElementById('txtCFrequency'+var_counter).value='-1';
	}
}
function DeleteCharge()
{
	var chargeInfoID="";//Added for charge details paing shorting
	//var myForm=document.getElementById('searchForm');
	var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
	if(chkChargeLine==null)
	{
		alert('No Charge Present');
		 document.getElementById('SelectAllChckCharges').checked=false;
		 document.getElementById('SelectAllChckCharges').disabled=true;
		return;
	}

	var len=chkChargeLine.length;
	if(len==null)
	{//only one chk box present

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
			//-------------[ Added below line for selected charges put into hidden field for delete ]-----------------
				var counter=chkChargeLine.value;				
				chargeInfoID=chargeInfoID+document.getElementById('hdnChargeId'+counter).value+",";					
				document.getElementById('hdnDeletedChargesListId').value=chargeInfoID;	
			//--------------------------------------------------------------------------------------------------------	
			
			//--------------[Delete the entry from an array that is maintaining to keep the linked charges id]--------
				deleteDisconnectLinkedCharge(document.getElementById('txtOBCharge'+counter).value); // if this charge is present in array then delete
			//--------------------------------------------------------------------------------------------------------
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
				//-------------[ Added below line for selected charges put into hidden field for delete ]-----------------
				var counter=chkChargeLine[i].value;				
				chargeInfoID=chargeInfoID+document.getElementById('hdnChargeId'+counter).value+",";	
				document.getElementById('hdnDeletedChargesListId').value=chargeInfoID;	
				//--------------------------------------------------------------------------------------------------------
				
				//--------------[Delete the entry from an array that is maintaining to keep the linked charges id]--------
				deleteDisconnectLinkedCharge(document.getElementById('txtOBCharge'+counter).value); // if this charge is present in array then delete
				//--------------------------------------------------------------------------------------------------------
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
		//lawkush start
					
		selectAllCheckBoxEnableDisable(2);
		
		//lawkush end
	
	
	
	/*
	if(chkChargeLine.length==0)
	{
		 document.getElementById('SelectAllChckCharges').checked=false;
		 document.getElementById('SelectAllChckCharges').disabled=true;
		
	}
	*/
	calculateTotalLineAmount('fromDeleteFunction',null);
//start	[006]
	//calculateTotalAmount();
//end	[006]	
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
function calculateTotalLineAmount(ctrlValue,counter){
	if(ctrlValue!='fromDeleteFunction' && ctrlValue!='fromOnLoadFunction' && counter != null){
		if(document.getElementById("hdnCTAmt"+counter)!=null){
			document.getElementById("hdnCTAmt"+counter).value=ctrlValue;
	}}
	document.getElementById('totalLineAmount').value= 0.0 ;
	
	for(i=1;i<=chargeSumCounter;i++){	
		if(document.getElementById("hdnCTAmt"+i)!=null && document.getElementById("hdnCTAmtDisconnectType"+i).value!=1){
			if(document.getElementById("hdnCTAmt"+i).value==null || document.getElementById("hdnCTAmt"+i).value=="")
				document.getElementById("hdnCTAmt"+i).value=0.0;
			document.getElementById('totalLineAmount').value = parseFloat(document.getElementById('totalLineAmount').value) + parseFloat(document.getElementById("hdnCTAmt"+i).value);
		}
	}
	/*below method is called to assign lineAmount to hardware lineItem serviceSummary attribute(2D.Indicative Value)*/
	if(ctrlValue!='fromOnLoadFunction'){
	calculateTotalAmount(); }
}
/*End calculateTotalLineAmount()*/

function formulaFreqAmt(index)
{
	var frqAmount=0;
	//<!-- Start   [009] -->
	var annualAmount=0;
	var factor=factorVal[document.getElementById("txtCFrequency"+index).selectedIndex-1];
	var Cperiod=document.getElementById("txtCPeriod"+index).value;
	var TotalAmount=document.getElementById("txtCTAmt"+index).value;
	frqAmount=((TotalAmount/Cperiod)*factor).toFixed(2);
	//alert("factor="+factor+",Cperiod="+Cperiod+",TotalAmount="+TotalAmount+",frqAmount="+frqAmount);
	annualAmount=(frqAmount*12/factor);
	document.getElementById("txtCFreqAmt"+index).value=roundNumber(frqAmount,2);
	document.getElementById("txtAnnualCharge"+index).value=Math.round(annualAmount);
}	
/*Function Name	:	fillAnnotation
	@Author		:	Anil Kumar
	Date		:	14-feb-11
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
	
function getLineNSublineItemLbl(var_counter)
{	
	var chargeNameCombo=document.getElementById('txtCName'+var_counter);	
	var index=chargeNameCombo.selectedIndex;
	if(index!=0)
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
		//[014] Start
		if(lstLineNSublineItemLbl.list[0].fxChareId=='NA' || lstLineNSublineItemLbl.list[0].fxChareId==null)
	 	{
	 		document.getElementById("hdnFxChargeID"+var_counter).value="&nbsp;";
	 	}
	 	else
	 	{
			document.getElementById("hdnFxChargeID"+var_counter).value=lstLineNSublineItemLbl.list[0].fxChareId;
		}
		//[014] Start
		}
	
}
//lawkush start
 
 function fnCheckChargesAll()
{
  
	    var i;
        var myForm=document.getElementById('productCatelog');		
	    var counter=1;
	    chargesTab = document.getElementById('tableCharges');
        var rowlen= chargesTab.rows.length;
         var chkLength1=document.forms[0].chkSelectChargeDetail.length;
         
		if(document.getElementById('SelectAllChckCharges').checked==true)
		{
	
			        if(chkLength1==undefined)
	        
	                {
		               if(document.forms[0].chkSelectChargeDetail.disabled==false)
			             {
		                    document.forms[0].chkSelectChargeDetail.checked=true;
		                }
		                else
		                {
		                rowlen--;
		                }
		                 
					}
          
			      else
			        {    
				         for (i =0; i<chkLength1; i++)
						   { 
						   		if(document.forms[0].chkSelectChargeDetail[i].disabled==false)
			             			{
										myForm.chkSelectChargeDetail[i].checked=true ;
									}
									else
									{
									rowlen--;
									}
									 
					       }
					       
				    }
	
	 
	         
	
		}	
	else
	   {
	           if(chkLength1==undefined)
        
		          {
		          
		          	if(document.forms[0].chkSelectChargeDetail.disabled==false)
			           {
			          document.forms[0].chkSelectChargeDetail.checked=false;
			          }
			          else
			          {
			          rowlen--;
			          }
		          }
			      else
			      {    
			         for (i =0; i<chkLength1; i++)
					   { 
					   			if(document.forms[0].chkSelectChargeDetail[i].disabled==false)
			           				{
							        myForm.chkSelectChargeDetail[i].checked=false ;
							        
							        } 
							        else
							        {
							        rowlen--;
							        }
						
					   }
				}
	
		       
		}
	
	
				if(rowlen==0)
				 {
				
					//  alert("No new charge");
					document.getElementById('SelectAllChckCharges').checked=false;
			 		document.getElementById('SelectAllChckCharges').disabled=true;
				//  return false;
				 }
				 else
				 {
				 document.getElementById('SelectAllChckCharges').disabled=false;
				 }

	
}
function allChargesCheckNone()
{
	document.getElementById('SelectAllChckCharges').checked=false;
}
 
//lawkush end

//Start:CheckRCRequiredForCLEP
//Date:11-jun-2012
//Purpose:To check on adding extra charges for clep from GUI,Only allow NRC charges
function CheckRCRequiredForCLEP(chargetype,var_counter)
{
	
			var callerWindowObj = dialogArguments;
			var order_creation_source = callerWindowObj.document.getElementById('hdnOrderCreationSourceId').value;
	  		
	  		if(order_creation_source==ORDER_CREATION_SOURCE && chargetype.value=="1")
	  		{	  		    
	  			alert('Only NRC is Allowed with this type of product.Please Contact system Admin.')
	  			var ChargeTypeCombo = document.getElementById("ddCType"+var_counter);
	  			var ChargeNameCombo = document.getElementById("txtCName"+var_counter);
	  			document.getElementById("ddCType"+var_counter).value=2;
	  			ChargeNameCombo.selectedIndex=-1;
	  			return false;
	  		}	
}
//End:CheckRCRequiredForCLEP

//vijay add method for copy disconnected charges
function AddDisconnectedCharge(){
	
	if(!(isView == null || isView == 'null')){
		return;
	}
	//commented by [0016] start
	/* if(document.getElementById('txtPODetailNo').value == 0 && document.getElementById('txtBillingLvl').value!=2 ){
		alert('Please Select PO first');
		return false;
	}
	else if((document.getElementById('txtBillingLvl').value==2 && editSolutionChangeOldProduct ==1)
				|| gbChargesAdditionForDisabledPo=="not_allowed"){
		alert('You cannot add charges at Billing level is PO');
		return;
	} */
	//commented by [0016] end
	//[0016] start
	
	if(document.getElementById('txtPODetailNo').value == 0){
		alert('Please Select PO first');
		return false;
	}
	//[0016] end
	var contractPeriod;
	if(document.forms[0].txtCntrtMonths!=null){
		contractPeriod=document.forms[0].txtCntrtMonths.value;		
		if(contractPeriod==null || contractPeriod==''){
			alert('Please Select PO first');
			return ;
		}
	}
	else{
		alert('No PO selected');
		return;
	}
	if(document.getElementById('hdnHardwareInfo').value==1){
		if(document.getElementById('txtBillingformat').value == "0"){
			alert('No bill format selected, please select bill format first');
			return;
		}
	}
	
	var path1 = gb_path +"/ChangeOrderAction.do?method=copyDisconnectedCharge&serviceProductID="+serviceProductID;
	path1 = path1+"&serviceNo="+serviceNo+"&orderNo="+callerWindowObj.document.getElementById('poNumber').value;
	window.showModalDialog(path1,window,"status:false;dialogWidth:800px;dialogHeight:500px");
}
</script>

</head>
<body>
	<fieldset class="border1" id="chargesInfoFieldsetId">
		<div align="left"><b>Charges Details:<font color="red" size="2">(Below Mentioned Annotation will be printed on Invoice)</font></b></div>	
		<!----------------------------------------- [ PAGING-CHARGES-LINE-04-12-2012 ]------------------------------------------------------------------- -->
				<table width="100%"  border="1" align="center" cellpadding="0" cellspacing="0" class="tab2" id="viewChargeLineTabNavigatorId">	
				<tr>
					<td align="center"><a href="#" id= "first" onclick="FirstPage('CHARGEINFOID','CHANGEVIEWCHARGETABLE')">First</a>&nbsp;&nbsp;<a href="#" id="next" onclick="NextPage('CHARGEINFOID','CHANGEVIEWCHARGETABLE')">Next</a></td>
					<td align="center">
						<input type="text" id="txtPageNumber" class="inputBorder2" readonly="true" size="2"/>of
						<input type="text" id="txtMaxPageNo"  class="inputBorder2" readonly="true" size="2"/>Pages
					</td>
					<td align="center"><a href="#" id="prev" onclick="PrevPage('CHARGEINFOID','CHANGEVIEWCHARGETABLE')">Prev</a>&nbsp;&nbsp;<a href="#" id="last" onclick="LastPage('CHARGEINFOID','CHANGEVIEWCHARGETABLE')">Last</a></td>
				</tr>
				</table>
		<!----------------------------------------- [ PAGING-CHARGES-LINE-04-12-2012 ]------------------------------------------------------------------- -->
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
                                    Select <input type="checkbox" name="SelectAllChckCharges" id="SelectAllChckCharges" onclick="javascript:fnCheckChargesAll();" disabled="disabled" />
                                </td>
                                <td class='inner col2 head1'>
                                	Pk Charge Id
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
                                <td class='inner col3 head1'>  <!-- 090-->
                                    Disconnect/Revert Charge
                                </td>	
                                <td class='inner col2 head1'>
                                   Charge Status   <!-- 090 -->
                                </td>								                                                        
                              <!--    <td class='inner col11 head1'>
                                	Is OB Charged
                                </td> -->
				<td class='inner col2 head1'>
                                	Linked Charge
                                </td>
                                <td class='inner col2 head1'>
                                  <span onmouseover='getTip("Close: Charge Disconnected By User</br>Inactive : Future Charge Disconnected By User</br>Till Disconnection: Charge having No End Date</br>Billing Trigger Date : Charge with End Date Specified</br>One Time Charge : NRC(Non discoonected)")' onmouseout='UnTip()'>
                                  Latest State Logic</span> 
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
                    <div class='horizontal-scroll-changeview' onscroll='reposHorizontal(this);'>
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
					    <input type="button" id="idChargesAddBtn" name="btnAddContact" value="Add Charges" onClick="AddCharge(1)">
					    <input type="button"  id="idChargesDeleteBtn" name="btnDeletePoDetail" value="Delete Charges" onClick="DeleteCharge()">
					    <input type="button" id="idDisChargesAddBtn" name="btnAddDisCharge" value="Disconnect and Copy Charge" onClick="AddDisconnectedCharge()">
					    <input type="hidden" id="hdnDeletedChargesListId" value="0" name="hdnDeletedChargesList"/>
				</td>
			</tr>
        </table>
	</fieldset>
</body>
</html>
