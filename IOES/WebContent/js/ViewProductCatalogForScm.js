
var gb_sessionid;
var productCatalogueType="NEW";
var focusEnabled=true;
var butSummaryFlag=false;

function enableBudgetHeadd(var_counter,value)
{

document.getElementById('txtBudget'+var_counter).disabled = false;
} 
function getBudgetHead(var_counter1,aop_Id){
var BudgetHeadCombb;
		 var aop1_Id=aop_Id;
		
		try
		{
		var getBudgett=new Array();
		var getAop2ID=new Array();
		BudgetHeadCombb=jsonrpc.processes.populateBudgetHeadAop2ForUpdate(aop1_Id);
		for(var z=0;z<BudgetHeadCombb.list.length;z++)
		{
		 getBudgett[z]=BudgetHeadCombb.list[z].budgetHead2;
		getAop2ID[z]=BudgetHeadCombb.list[z].aop2_Id;
		}
			var local_typeCombo = document.getElementById("txtBudget"+var_counter1);
	        var cvalue=1;
	        for(i=0;i< BudgetHeadCombb.list.length;i++)
	        {
	        var option = document.createElement("option");
	    		option.text = getBudgett[i];
		    	option.value=getAop2ID[i];
		    	option.title = getBudgett[i];
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
			} 	
}


function drawViewChargeTable1()
{
	if(scmLineDetailsFetched==true)
	{
		return;
	}
	

var jsChargeData =	{
			
			serviceProductID:serviceProductID
					};

 var chargelists;
 
    chargelists = jsonrpc.processes.populateScmLineDetails(jsChargeData);
    
    iTotalLength=chargelists.list.length;
   
    var chargeTableDiv =  document.getElementById('contentscroll');
     if(iTotalLength>0){
    	chargeTableDiv.innerHTML = "";
    	
    }
    
    if(iTotalLength > 1){
			 document.getElementById('SelectAllChckCharges').disabled=false;
		
		}else{
			 document.getElementById('SelectAllChckCharges').disabled=true;
		}
			    if(iTotalLength > 0){
			// document.getElementById('viewChargeLineTabNavigatorId').style.display='block';			
			var iChargeIndexCounter = 0;
			var hdnChargeCount = document.getElementById('hdnChargeCount');
			
			//var tableCharges = document.getElementById('tableCharges');
			var counter = 0;
			var chargeListTable = "<table cellpadding='0' cellspacing='0' class='content' id='tableCharges'>";
	    	var tableEnd = '</table>';
	    	var tableRowStart= "<tr>";
	    	var tableRowEnd = "</tr>";
	    	var tableDataStart;
	    	var tableDataEnd = "</td>";
	    	var str;
	    	var aopYearOption=getSelectedAopYear();
	    	var itemSelectedValue = new Array();
			var itemSelectedLabel = new Array();
			var delLocSelectedValue = new Array();
			var delLocSelectedLabel = new Array();
			var subInvSelectedValue = new Array();
			var subInvSelectedLabel = new Array();
			var aop1BudgetSelectedLabel = new Array();
			var aop1BudgetSelectedValue = new Array();
			var aop2BudgetSelectedValue = new Array();
			var aopYearSelectedValue=new Array();
			
	    	for (iChargeIndexCounter = 0 ; iChargeIndexCounter < iTotalLength; iChargeIndexCounter++){		    	
	    		var itemTypeOption = getSelectedItemCodeLabel(chargelists.list[iChargeIndexCounter].itemCode_Id);
	    		var delLocOption=getSelectedDeliveryLocLabel(chargelists.list[iChargeIndexCounter].deliveryLocationId);
	    		var subInvOption=getSelectedSubInventoryLabel(chargelists.list[iChargeIndexCounter].subInventryId);
	    		var aop1BudgetOption=getSelectedAop1BudgetLabel(chargelists.list[iChargeIndexCounter].aop1_Id);
	    		var aop2BudgetOption=getSelectedAop2Budget(chargelists.list[iChargeIndexCounter].aop1_Id);
	    		chargeListTable = chargeListTable + tableRowStart;
	    		// chargeSumCounter++;
				counter++;
				// var created_serviceid=chargelists.list[iChargeIndexCounter].created_serviceid;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col1 toprow'>";
				str ="<input name='chkSelectChargeDetail' id='chkSelectChargeDetail'  type='checkbox' value='"+counter+"'onclick='allChargesCheckNone()' >";
				str=str+"<input type='hidden' name='hdnDeleteChargeId' id='hdnDeleteChargeId"+counter+"'  value=''>";
				str=str+"<input type='hidden' name='hdnChargeId' id='hdnChargeId"+counter+"'  value='"+chargelists.list[iChargeIndexCounter].chargeId_Scm+"'>";
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col2' >";
				
				str="<input type='text' value='' onmouseover='getTip(value)'  countervalItem='"+ counter +"' onmouseout='UnTip()' style='width:width:175px' isRequired='0'  name='itemName' ctrltype='dropdown' class='dropdownMargin' retval='AUTOSUGGESTITEMCODE' id='itemName" + counter + "'><a name='chargeNameLink' id='chargeNameLink" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownItemCode(" + counter + ")'>Show</a>";
				str=str+"<input type='hidden' name='txtItemCode' id='txtItemCode"+counter+"' value='-1' countervalItem='"+ counter +"'>";
				itemSelectedValue[iChargeIndexCounter]=chargelists.list[iChargeIndexCounter].itemCode_Id;
				itemSelectedLabel[iChargeIndexCounter]=itemTypeOption;
				// str=str+"<input type='hidden' name='hdnChargeId' id='hdnChargeId"+counter+"'  value='"+chargelists.list[iChargeIndexCounter].chargeId_Scm+"'>";
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col10' >";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()'   type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder1' name='txtValue' id='txtValue"+counter+"'value='"+chargelists.list[iChargeIndexCounter].cahrgeValue+"' maxlength='10' onblur='if(this.value.length > 0){return checknumberSCM(this)}'>";
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col10' >";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()'  type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder1' name='txtQuantity' id='txtQuantity"+counter+"' value='"+chargelists.list[iChargeIndexCounter].quntity+"' maxlength='10' onkeyup='if(this.value.length > 0){integersOnly(this)}'>";
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col5' >";
				str="<input type='text'  value='Select Delivery Location' onmouseover='getTip(value)'  countervalDel='"+ counter +"' onmouseout='UnTip()' style='width:width:175px' isRequired='0'  name='delLocation' ctrltype='dropdown' class='dropdownMargin' retval='AUTOSUGGESTDELLOCATION' id='delLocation" + counter + "'><a name='chargeNameLink' id='chargeNameLink" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownDelLocation(" + counter + ")'>Show</a>";
				str=str+"<input type='hidden' name='txtDelLocId' id='txtDelLocId"+counter+"' value='-1' countervalDel='"+ counter +"'>";
				delLocSelectedLabel[iChargeIndexCounter]=delLocOption;
				delLocSelectedValue[iChargeIndexCounter]=chargelists.list[iChargeIndexCounter].deliveryLocationId;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col6' >";
				str="<input type='text'  value='Select SubInventory' onmouseover='getTip(value)'  countervalSub='"+ counter +"' onmouseout='UnTip()' style='width:width:175px' isRequired='0'  name='subInvent' ctrltype='dropdown' class='dropdownMargin' retval='AUTOSUGGESTSUBINVENTORY' id='subInvent" + counter + "'><a name='chargeNameLink' id='chargeNameLink" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownSubInventory(" + counter + ")'>Show</a>";
				str=str+"<input type='hidden' name='txtSubInvent' id='txtSubInvent"+counter+"' value='-1' countervalSub='"+ counter +"'>";
				subInvSelectedValue[iChargeIndexCounter]=chargelists.list[iChargeIndexCounter].subInventryId;
				subInvSelectedLabel[iChargeIndexCounter]=subInvOption;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col7' >";
				str="<input type='text'  value='Select Budget Head1' onmouseover='getTip(value)'  countervalBud='"+ counter +"' onmouseout='UnTip()' style='width:width:175px' isRequired='0'  name='budgHead' ctrltype='dropdown' class='dropdownMargin' retval='AUTOSUGGESTBUDGHEAD'  id='budgHead" + counter + "'><a name='chargeNameLink' id='chargeNameLink" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownBudgetHead(" + counter + ")'>Show</a>";
				str=str+"<input type='hidden' name='txtBudgetHead' id='txtBudgetHead"+counter+"' value='-1' countervalBud='"+ counter +"'>";
				aop1BudgetSelectedValue[iChargeIndexCounter]=chargelists.list[iChargeIndexCounter].aop1_Id;
				aop1BudgetSelectedLabel[iChargeIndexCounter]=aop1BudgetOption;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col8' >";
				str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px' name='txtBudget' id='txtBudget"+counter+"' isRequired='0' Disp=''disabled='true' class='dropdownMargin'";
				str = str + aop2BudgetOption;
				str = str + "</select>";
				aop2BudgetSelectedValue[iChargeIndexCounter]=chargelists.list[iChargeIndexCounter].aop2_Id;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col9' >";
				str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px' name='financeYear' id='financeYear"+counter+"' isRequired='0' Disp='' class='dropdownMargin'";
				str = str + aopYearOption;
				str = str + "</select>";
				aopYearSelectedValue[iChargeIndexCounter]=chargelists.list[iChargeIndexCounter].aopYear;
				
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col10' >";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()'   type='text' readonly='true' style='width:175px' isRequired='0' Disp='' class='dropdownMargin1' name='txtPoNumber' id='txtPoNumber"+counter+"'value='"+chargelists.list[iChargeIndexCounter].poNumer+"' maxlength='340'>";
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col10' >";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()'   type='text' readonly='true' style='width:175px' isRequired='0' Disp='' class='dropdownMargin1' name='txtPoDate' id='txtPoDate"+counter+"'value='"+chargelists.list[iChargeIndexCounter].poDtae+"' maxlength='340'>";
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col10' >";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()'   type='text' readonly='true' style='width:175px' isRequired='0' Disp='' class='dropdownMargin1' name='txtPoAmount' id='txtPoAmount"+counter+"'value='"+chargelists.list[iChargeIndexCounter].poAmount+"' maxlength='340'>";
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col10' >";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()'   type='text' readonly='true' style='width:175px' isRequired='0' Disp='' class='dropdownMargin1' name='txtIsActive' id='txtIsActive"+counter+"'value='"+chargelists.list[iChargeIndexCounter].isActive+"' maxlength='340'>";
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col10' >";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()'  readonly='true' type='text' style='width:175px' isRequired='0' Disp=''  class='dropdownMargin1' name='txtScmMessage' id='txtScmMessage"+counter+"'value='"+chargelists.list[iChargeIndexCounter].scmMessage+"' maxlength='340'>";
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				chargeListTable = chargeListTable + tableRowEnd;
			}
	    	chargeListTable = chargeListTable + tableEnd;
	    	chargeTableDiv.innerHTML=chargeListTable;
	    	for (iChargeIndexCounter = 0 ; iChargeIndexCounter < iTotalLength; iChargeIndexCounter++){
	     		$("#txtItemCode" + (iChargeIndexCounter +1)).val(itemSelectedValue[iChargeIndexCounter]);
	     		$("#itemName" + (iChargeIndexCounter +1)).val(itemSelectedLabel[iChargeIndexCounter]);
	     		$("#txtDelLocId" + (iChargeIndexCounter +1)).val(delLocSelectedValue[iChargeIndexCounter]);
	     		$("#delLocation" + (iChargeIndexCounter +1)).val(delLocSelectedLabel[iChargeIndexCounter]);
	     		$("#txtSubInvent" + (iChargeIndexCounter +1)).val(subInvSelectedValue[iChargeIndexCounter]);
	     		$("#subInvent" + (iChargeIndexCounter +1)).val(subInvSelectedLabel[iChargeIndexCounter]);
	     		$("#txtBudgetHead" + (iChargeIndexCounter +1)).val(aop1BudgetSelectedValue[iChargeIndexCounter]);
	     		$("#budgHead" + (iChargeIndexCounter +1)).val(aop1BudgetSelectedLabel[iChargeIndexCounter]);
	     		$("#txtBudget" + (iChargeIndexCounter +1)).val(aop2BudgetSelectedValue[iChargeIndexCounter]);
	     		$("#financeYear" + (iChargeIndexCounter +1)).val(aopYearSelectedValue[iChargeIndexCounter]);
	     		
	     		
	     	}
	    
	     	hdnChargeCount.value = counter;

	    	//Puneet assigning the css
			$("#tableCharges").find("[ctrltype='dropdownlink']").each(function() {
	    		attachCSForAS($(this));
	    	});
			// callOnChargeAmountChange();
			scmLineDetailsFetched=true;
			if(isPrReuseFlag==1)
			{
				$("#tableCharges").find("input,button,select").attr("disabled", "disabled");
				document.getElementById('btnAddCharges').disabled=true;
				document.getElementById('btnDeletePoDetail').disabled=true;
				
			}
			
			return ;
			   

	}
			   
else{
	//alert("Table is not drawn");
	return false;
	}
}

function getParameter()
{
	var hdnServiceSummary = document.getElementById('hdnPrServiceSummary');
	hdnServiceSummary.value=hiddenPrSerSummary;
	if(hiddenPrSerSummary==1){
	document.getElementById('lineItemServiceSummary').style.display='block';
	
	}
	
}


function getServiceSummary1(){

var nfaNo=poNumber+"/"+servicesID+"/"+serviceProductID;
	if(serviceSummaryFetched){
		return;
	}
	
	var test;
   	var mytableDiv = document.getElementById('ServiceListDiv');
   mytableDiv.innerHTML="";
   var order =poNumber;
   var ordertype = "New";
   //alert("SERVICE ID : "+servicesID);
 var jsData =	{
		serviceDetailID:serviceDetailID,
		serviceProductID:serviceProductID,
		poNumber:order,
		orderType:ordertype,
		serviceID:servicesID
	};
	
	test = jsonrpc.processes.populateServiceAttMasterValueForSCM(jsData,sessionid);
	var lableDescription = jsonrpc.processes.getDescrption();

	
    	document.getElementById('lineItemServiceSummary').style.display='block';
    
    	counter=1;
    	document.getElementById('hdnSeriveAttCounter').value=test.list.length;
    	var str;
    	var serviceListTable = '<table width="100%"  border="1" cellspacing="1" cellpadding="2"  id="ServiceList">';
    	var tableEnd = '</table>';
    	var tableRowStart= "<tr>";
    	var tableRowEnd = "</tr>";
    	var tableDataStart;
    	var tableDataEnd = "</td>";
    	var dropDownLovIDLabels=new Array();
    	var dropDownLovIDLabelIndex = 0;
    	var productAttClassName="";
    	var productAttDisabled="false";
    	for (i = 0 ; i < test.list.length; i++,counter++){
    		if(test.list[i].notSaveInScm==1){
    			document.getElementById('hdnNotSaveFlag').value=test.list[i].notSaveInScm;
    		str="";
    		serviceListTable = serviceListTable + tableRowStart;
    		tableDataStart = "<td align = 'left' width='41%' vAlign='top' style.fontSize='12px' style.fontWeight='bold'>";
		 
				str= test.list[i].prodAttributeLabel;								
					serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
			
			if(test.list[i].prodExpectedValue=='DROPDOWN'){
			    	tableDataStart = "<td align = 'left' vAlign='top'>";
				
			
				var autoSuggProdAttVal="";
				/*if(test.list[i].newProdAttVal==0){
					
					autoSuggProdAttVal = "";
				}else{
					autoSuggProdAttVal = test.list[i].destText;
				
				}*/
				
				if(test.list[i].isServiceSummReadonly==1){
		
						str="<input type='hidden' value = '' isSummaryReq='0' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"'  id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0' ><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()'  disabled='disabled' class='dropdownMargin1' readonly='true' style='width:180px' counterval='"+ counter +"' readonly='true'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' retval='"+ test.list[i].prodAttributeID +"' value='" + autoSuggProdAttVal + "'  id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' /> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
				}else{
					if(test.list[i].isServiceSummMandatory==1){
						
						str="<input type='hidden' value = '' isSummaryReq='1' class='dropdownMargin' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:180px' readonly='true' class='dropdownMargin' readonly='true' counterval='"+ counter +"'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' retval='"+ test.list[i].prodAttributeID +"' value='" + autoSuggProdAttVal + "' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
					}else{
						
						str="<input type='hidden' value = '' isSummaryReq='0' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0' ><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='dropdownMargin1' readonly='true' style='width:180px' counterval='"+ counter +"' readonly='true'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' retval='"+ test.list[i].prodAttributeID +"' value='" + autoSuggProdAttVal + "' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
					}
				}
				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
				dropDownLovIDLabels[dropDownLovIDLabelIndex] =  test.list[i].prodAttributeID + "-" + autoSuggProdAttVal;
				dropDownLovIDLabelIndex++;
				
			}
			else if(test.list[i].prodExpectedValue=='LOV'){
				var autoSuggProdAttVal;
				var autoSuggProdAttVal_Value;
				tableDataStart = "<td align = 'left' vAlign='top'>";
				if(test.list[i].attDefaultVal){
			      	//alert(test.list[i].attDefaultVal);
			      	for(j=0;j<test.list[i].serviceSummaryLov.list.length;j++){
							//alert("VIPIN : "+test.list[i].serviceSummaryLov.list[j].serviceSummaryText);
						if(test.list[i].serviceSummaryLov.list[j].serviceSummaryValues == test.list[i].attDefaultVal )
						{
							//alert("serviceSummaryLov.list[j].serviceSummaryText "+test.list[i].serviceSummaryLov.list[j].serviceSummaryText);
							autoSuggProdAttVal=test.list[i].serviceSummaryLov.list[j].serviceSummaryText;
							autoSuggProdAttVal_Value=test.list[i].serviceSummaryLov.list[j].serviceSummaryValues;
							//document.getElementById("autoSuggProdAttVal_"+counter).value=test.list[i].serviceSummaryLov.list[j].serviceSummaryText;
							//document.getElementById("prodAttVal_"+counter).value=test.list[i].serviceSummaryLov.list[j].serviceSummaryValues;
						}
					}
				}else
				{
					autoSuggProdAttVal="";
					autoSuggProdAttVal_Value=0;
				}
				/*if(test.list[i].notSaveInScm==1){
				autoSuggProdAttVal="";
				}else{
					autoSuggProdAttVal = test.list[i].destText;
					
						
				}*/
				
				
				if(test.list[i].isServiceSummReadonly==1){
					
					str="<input type='hidden' value = '' isSummaryReq='0' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0' ><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' disabled='disabled' class='dropdownMargin1' readonly='true' style='width:180px' counterval='"+ counter +"'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' value='" + autoSuggProdAttVal + "' retval='"+ test.list[i].prodAttributeID +"' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "'   /> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					 
				}else{
					if(test.list[i].isServiceSummMandatory==1){
						if(test.list[i].attributeKey ==lableDescription.attributeValue)
						{
							attDescKey=lableDescription.attributeValue;
						}
						str="<input type='hidden' isSummaryReq='1' class='dropdownMargin' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='"+autoSuggProdAttVal_Value+"'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:180px' class='dropdownMargin' counterval='"+ counter +"'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' retval='"+ test.list[i].prodAttributeID +"' retAttDescKey='"+ test.list[i].attributeKey + "' retval12='AUTOSUGGESTSUMMARY" + counter + "' value='" + autoSuggProdAttVal + "' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "'   /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink'  onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						
					}else{
						
						str="<input type='hidden' isSummaryReq='0' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='"+autoSuggProdAttVal_Value+"'><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='dropdownMargin1' style='width:180px' counterval='"+ counter +"'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' retAttDescKey='"+ test.list[i].attributeKey + "' retval='"+ test.list[i].prodAttributeID +"' value='" + autoSuggProdAttVal + "' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "'   /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					}
				}
				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
				dropDownLovIDLabels[dropDownLovIDLabelIndex] =  test.list[i].prodAttributeID + "-" + autoSuggProdAttVal;
				dropDownLovIDLabelIndex++;
				 
			}
			
			else if(test.list[i].prodExpectedValue=='LINK'){
			
					tableDataStart = "<td align = 'left' vAlign='top' width='59%'>";
				if(test.list[i].isServiceSummMandatory==1){
				
					str="<input onmouseover='getTip(value)' value='"+test.list[i].newProdAttVal+"' onmouseout='UnTip()' type='text' isSummaryReq='1' readOnly ='true' class='inputBorder1' style='width:150px' name='prodAttVal_" + counter + "' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' islinkType='1'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
					str=str+"<div class='searchBg1'><a href='#' title='Select LSI No' id='serviceSummaryLink_" + counter + "' onClick='return popUpServiceSummary("+test.list[i].prodAttributeID+","+counter+",\""+test.list[i].linkPopUpId+"\")'  >...</a></div><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}else{
					str="<input onmouseover='getTip(value)' value='"+test.list[i].newProdAttVal+"' onmouseout='UnTip()' type='text' isSummaryReq='1' readOnly ='true' class='inputBorder2' style='width:150px' name='prodAttVal_" + counter + "' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' islinkType='1'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
					str=str+"<div class='searchBg1'><a href='#' title='Select LSI No' id='serviceSummaryLink_" + counter + "' onClick='return popUpServiceSummary("+test.list[i].prodAttributeID+","+counter+",\""+test.list[i].linkPopUpId+"\")'  >...</a></div><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}
				
				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				
				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
			}else if(test.list[i].prodExpectedValue == 'datetime'){
					tableDataStart = "<td align = 'left' vAlign='top' width='59%'>";
					
					
				strA="javascript: return false";
				if(test.list[i].isServiceSummMandatory==1){
			
					str="<input type='text' isSummaryReq='1'  value='"+test.list[i].rfsDate+"' class='inputBorder1' name='prodAttVal_'" + counter + " className='" + productAttClassName + "'  id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'><img src='" + requestContextPath + "/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
					
				}else{
					
						str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text'  isSummaryReq='0'  value='"+test.list[i].rfsDate+"' class='inputBorder2' name='prodAttVal_" + counter + "' className='" + productAttClassName + "'  id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'><img src='" + requestContextPath + "/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_" + counter + "' id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_" + counter + "'  id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_" + counter + "'  id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
					
				}
				
				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				
				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
			}else{
			
				if(serviceSummaryIndValChanged && null != serviceSummaryIndVal){
					var values = attMasterIdList.split(",");
					if (values.length > 0){	
						for (var j = 0; j < values.length; j++){
							if(values[j] == test.list[i].prodAttributeID){
								test.list[i].newProdAttVal = serviceSummaryIndVal;
								serviceSummaryIndValChanged = false;
								serviceSummaryIndVal = null;
								break;
							}
						}
					}
				}
				tableDataStart = "<td align = 'left' vAlign='top' width='59%'>";
				strA = "javascript: if(\""+ test.list[i].prodExpectedValue +"\"==\"numeric\".toUpperCase()){if(this.value.length > 0){return checknumber(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"datetime\".toUpperCase()){if(this.value.length > 0){return checkdate(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"email\".toUpperCase()){if(this.value.length > 0){return validateEmail(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"varchar\".toUpperCase()){if(this.value.length > 0){return ValidateTextFieldM6(this,pathM6)}}";
				
				if(test.list[i].isServiceSummReadonly==1){
			str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readonly='true' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0'  dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' style='width:235px'  name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
				if(test.list[i].attributeKey ==lableDescription.descLabel){
					str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0'  dmxtotal='0' value='"+nfaNo+"' class='inputBorder2' style='width:235px'  readonly='true'  name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
						}
						else if(test.list[i].currentRole=='4'&&(test.list[i].newProdAttVal!="null"||test.list[i].newProdAttVal!=null)){
							
						str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0'  dmxtotal='0' value='' class='inputBorder2' style='width:235px'  readonly='true'  name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
						}
						else{
							str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0'  dmxtotal='0' value='' class='inputBorder2' style='width:235px'  readonly='true'  name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
						}
				
				}else{
					if(test.list[i].isServiceSummMandatory==1){
						if(test.list[i].prodAttributeID=="1041")
							str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='1' dmxtotal='1' value='" + test.list[i].newProdAttVal + "' class='inputBorder1' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
						else{
							if(test.list[i].prodAttributeID == "2470"){
								str="<label><center><bold>SUPPLIER</bold></center></label></br><input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' value='"+test.list[i].defValue+"' onmouseout='UnTip()' type='text' isSummaryReq='1' dmxtotal='0' class='inputBorder1' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
							}else if(test.list[i].prodAttributeID == "2476"){
								str="<label><center><bold>BROADCASTER</bold></center></label></br><input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' value='"+test.list[i].defValue+"' onmouseout='UnTip()' type='text' isSummaryReq='1' dmxtotal='0' class='inputBorder1' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
							}
							else{//Puneet un commented as below string assignment will always perform irrespective of above conditions
								str="<input onmouseover='getTip(value)' onkeyup='copyTextValue(this.value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='1' dmxtotal='0' value='' class='inputBorder1' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"'  zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
							}
						}
					}else{
						if(test.list[i].prodAttributeID=="1041")
							str="<input onmouseover='getTip(value)' onkeyup='copyTextValue(this.value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0' dmxtotal='1' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						else{
							if(test.list[i].prodAttributeID == "2470"){							
								str="<label><center><bold>SUPPLIER</bold></center></label></br><input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0' dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
							}else if(test.list[i].prodAttributeID == "2476"){						
								str="<label><center>BROADCASTER</center></label></br><input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0' dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
							}else{	
								str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0' dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
							}
						}
					}
				}
				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
			}
				tableDataStart = "<td align = 'left' vAlign='top' width='50%'>";
			str="<input type='hidden' class='inputBorder2' name='txt_prod_attvalidation_" + counter + "' id='txt_prod_attvalidation_" + counter + "' value='"+test.list[i].for_validation+"' readonly='true'>";
			
			serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
			serviceListTable = serviceListTable + tableRowEnd;
			serviceSummaryFetched = true;
    		}
    		else
    		{
    			document.getElementById('hdnNotSaveFlag').value=0;
    	 		str="";
        		serviceListTable = serviceListTable + tableRowStart;
        		tableDataStart = "<td align = 'left' width='41%' vAlign='top' style.fontSize='12px' style.fontWeight='bold'>";
    		 
    				str= test.list[i].prodAttributeLabel;								
    					serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
    			
    			if(test.list[i].prodExpectedValue=='DROPDOWN'){
    			    	tableDataStart = "<td align = 'left' vAlign='top'>";
    				
    			
    				var autoSuggProdAttVal;
    				if(test.list[i].newProdAttVal==0){
    					
    					autoSuggProdAttVal = "";
    				}else{
    					autoSuggProdAttVal = test.list[i].destText;
    				
    				}
    				
    				if(test.list[i].isServiceSummReadonly==1){
    		
    						str="<input type='hidden' value = '" + test.list[i].newProdAttVal + "' isSummaryReq='0' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"'  id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0' ><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()'  disabled='disabled' class='dropdownMargin1' readonly='true' style='width:180px' counterval='"+ counter +"' readonly='true'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' retval='"+ test.list[i].prodAttributeID +"' value='" + autoSuggProdAttVal + "'  id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' /> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
    				}else{
    					if(test.list[i].isServiceSummMandatory==1){
    						
    						str="<input type='hidden' value = '" + test.list[i].newProdAttVal + "' isSummaryReq='1' class='dropdownMargin' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:180px' readonly='true' class='dropdownMargin' readonly='true' counterval='"+ counter +"'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' retval='"+ test.list[i].prodAttributeID +"' value='" + autoSuggProdAttVal + "' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
    					}else{
    						
    						str="<input type='hidden' value = '" + test.list[i].newProdAttVal + "' isSummaryReq='0' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0' ><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='dropdownMargin1' readonly='true' style='width:180px' counterval='"+ counter +"' readonly='true'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' retval='"+ test.list[i].prodAttributeID +"' value='" + autoSuggProdAttVal + "' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
    					}
    				}
    				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
    				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
    				dropDownLovIDLabels[dropDownLovIDLabelIndex] =  test.list[i].prodAttributeID + "-" + autoSuggProdAttVal;
    				dropDownLovIDLabelIndex++;
    				
    			}
    			else if(test.list[i].prodExpectedValue=='LOV'){
    			   if(test.list[i].attributeKey==lableDescription.attributeValue)
    			   {
    				   attDescKey=lableDescription.attributeValue;
    			   }
    				tableDataStart = "<td align = 'left' vAlign='top'>";
    				var autoSuggProdAttVal;
    				if(test.list[i].newProdAttVal==0){
    				autoSuggProdAttVal="";
    				}else{
    					autoSuggProdAttVal = test.list[i].destText;
    					
    					if(test.list[i].attributeKey==lableDescription.attributeValue)
    						{
    						selectedCircleInView=test.list[i].destText;
    						}
    				}
    				
    				
    				if(test.list[i].isServiceSummReadonly==1){

    					str="<input type='hidden' value = '" + test.list[i].newProdAttVal + "' isSummaryReq='0' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0' ><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' disabled='disabled' class='dropdownMargin1' readonly='true' style='width:180px' counterval='"+ counter +"'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' value='" + autoSuggProdAttVal + "' retval='"+ test.list[i].prodAttributeID +"' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "'   /> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
    					 
    				}else{
    					if(test.list[i].isServiceSummMandatory==1){
    						
    						str="<input type='hidden' value = '" + test.list[i].newProdAttVal + "' isSummaryReq='1' class='dropdownMargin' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:180px' class='dropdownMargin' counterval='"+ counter +"'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' retval='"+ test.list[i].prodAttributeID +"' retAttDescKey='"+ test.list[i].attributeKey + "' value='" + autoSuggProdAttVal + "' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "'   /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink'  onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
    						
    					}else{
    						
    						str="<input type='hidden' value = '" + test.list[i].newProdAttVal + "' isSummaryReq='0' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0' ><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='dropdownMargin1' style='width:180px' counterval='"+ counter +"'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' retval='"+ test.list[i].prodAttributeID +"' retAttDescKey='"+ test.list[i].attributeKey + "' value='" + autoSuggProdAttVal + "' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "'   /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
    					}
    				}
    				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
    				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
    				dropDownLovIDLabels[dropDownLovIDLabelIndex] =  test.list[i].prodAttributeID + "-" + autoSuggProdAttVal;
    				dropDownLovIDLabelIndex++;
    				 
    			}
    			
    			else if(test.list[i].prodExpectedValue=='LINK'){
    			
    					tableDataStart = "<td align = 'left' vAlign='top' width='59%'>";
    				if(test.list[i].isServiceSummMandatory==1){
    				
    					str="<input onmouseover='getTip(value)' value='"+test.list[i].newProdAttVal+"' onmouseout='UnTip()' type='text' isSummaryReq='1' readOnly ='true' class='inputBorder1' style='width:150px' name='prodAttVal_" + counter + "' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' islinkType='1'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
    					str=str+"<div class='searchBg1'><a href='#' title='Select LSI No' id='serviceSummaryLink_" + counter + "' onClick='return popUpServiceSummary("+test.list[i].prodAttributeID+","+counter+",\""+test.list[i].linkPopUpId+"\")'  >...</a></div><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
    				}else{
    					str="<input onmouseover='getTip(value)' value='"+test.list[i].newProdAttVal+"' onmouseout='UnTip()' type='text' isSummaryReq='1' readOnly ='true' class='inputBorder2' style='width:150px' name='prodAttVal_" + counter + "' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' islinkType='1'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
    					str=str+"<div class='searchBg1'><a href='#' title='Select LSI No' id='serviceSummaryLink_" + counter + "' onClick='return popUpServiceSummary("+test.list[i].prodAttributeID+","+counter+",\""+test.list[i].linkPopUpId+"\")'  >...</a></div><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
    				}
    				
    				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
    				
    				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
    			}else if(test.list[i].prodExpectedValue == 'datetime'){
    					tableDataStart = "<td align = 'left' vAlign='top' width='59%'>";
    					
    					
    				strA="javascript: return false";
    				if(test.list[i].isServiceSummMandatory==1){
    			
    					if(test.list[i].newProdAttVal=="null" || test.list[i].newProdAttVal=="0" )
    					
    						str="<input type='text'  isSummaryReq='1'  value='"+test.list[i].rfsDate+"' class='inputBorder1' name='prodAttVal_'" + counter + " className='" + productAttClassName + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y' ><img src='" + requestContextPath + "/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
    					else
    						str="<input type='text'  isSummaryReq='1'  value='" + test.list[i].newProdAttVal + "' class='inputBorder1' name='prodAttVal_'" + counter + " className='" + productAttClassName + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'><img src='" + requestContextPath + "/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
    					
    				}else{
    					if(test.list[i].attServiceName=="SaaS"){
    						
    						
    						if(test.list[i].newProdAttVal=="null" || test.list[i].newProdAttVal=="0" )
    							
    							str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' readOnly ='false'  value='' style='width:235px' class='inputBorder2' name='prodAttVal_" + counter + "' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <img src='" + requestContextPath + "/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
    						else
    							str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' readOnly ='false'  value='" + test.list[i].newProdAttVal + "' style='width:235px' class='inputBorder2' name='prodAttVal_" + counter + "' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <img src='" + requestContextPath + "/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
    					}else{
    						
    						str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly ='true' isSummaryReq='0'  value='" + test.list[i].prodAttVal + "' class='inputBorder2' name='prodAttVal_" + counter + "' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'><img src='" + requestContextPath + "/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_" + counter + "' id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_" + counter + "'  id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_" + counter + "'  id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
    					}
    				}
    				
    				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
    				
    				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
    			}else{
    			
    				if(serviceSummaryIndValChanged && null != serviceSummaryIndVal){
    					var values = attMasterIdList.split(",");
    					if (values.length > 0){	
    						for (var j = 0; j < values.length; j++){
    							if(values[j] == test.list[i].prodAttributeID){
    								test.list[i].newProdAttVal = serviceSummaryIndVal;
    								serviceSummaryIndValChanged = false;
    								serviceSummaryIndVal = null;
    								break;
    							}
    						}
    					}
    				}
    				tableDataStart = "<td align = 'left' vAlign='top' width='59%'>";
    				strA = "javascript: if(\""+ test.list[i].prodExpectedValue +"\"==\"numeric\".toUpperCase()){if(this.value.length > 0){return checknumber(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"datetime\".toUpperCase()){if(this.value.length > 0){return checkdate(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"email\".toUpperCase()){if(this.value.length > 0){return validateEmail(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"varchar\".toUpperCase()){if(this.value.length > 0){return ValidateTextFieldM6(this,pathM6)}}";
    				
    				if(test.list[i].isServiceSummReadonly==1){
    				
    			str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0'  dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' style='width:235px'  readonly='true'  name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
    				if(test.list[i].prodAttributeLabel=='NFA Number'){
    					str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0'  dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' style='width:235px'  readonly='true'  name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
    						}
    						else if(test.list[i].currentRole=='4'&&(test.list[i].newProdAttVal!="null"||test.list[i].newProdAttVal!=null)){
    							
    						str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0'  dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' style='width:235px'  readonly='true'  name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
    						}
    						else{
    							str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0'  dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' style='width:235px'  readonly='true'  name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
    						}
    				}else{
    					if(test.list[i].isServiceSummMandatory==1){
    						if(test.list[i].prodAttributeID=="1041")
    							str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='1' dmxtotal='1' value='" + test.list[i].newProdAttVal + "' class='inputBorder1' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
    						else{
    							if(test.list[i].prodAttributeID == "2470"){
    								str="<label><center><bold>SUPPLIER</bold></center></label></br><input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' value='"+test.list[i].defValue+"' onmouseout='UnTip()' type='text' isSummaryReq='1' dmxtotal='0' class='inputBorder1' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
    							}else if(test.list[i].prodAttributeID == "2476"){
    								str="<label><center><bold>BROADCASTER</bold></center></label></br><input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' value='"+test.list[i].defValue+"' onmouseout='UnTip()' type='text' isSummaryReq='1' dmxtotal='0' class='inputBorder1' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
    							}
    							else{//Puneet un commented as below string assignment will always perform irrespective of above conditions
    								str="<input onmouseover='getTip(value)' onkeyup='copyTextValue(this.value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='1' dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder1' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"'  zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
    							}
    						}
    					}else{
    						if(test.list[i].prodAttributeID=="1041")
    							str="<input onmouseover='getTip(value)' onkeyup='copyTextValue(this.value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0' dmxtotal='1' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
    						else{
    							if(test.list[i].prodAttributeID == "2470"){							
    								str="<label><center><bold>SUPPLIER</bold></center></label></br><input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0' dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
    							}else if(test.list[i].prodAttributeID == "2476"){						
    								str="<label><center>BROADCASTER</center></label></br><input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0' dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
    							}else{	
    								str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0' dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
    							}
    						}
    					}
    				}
    				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
    				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
    			}
    				tableDataStart = "<td align = 'left' vAlign='top' width='50%'>";
    			str="<input type='hidden' class='inputBorder2' name='txt_prod_attvalidation_" + counter + "' id='txt_prod_attvalidation_" + counter + "' value='"+test.list[i].for_validation+"' readonly='true'>";
    			
    			serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
    			serviceListTable = serviceListTable + tableRowEnd;
    		}
	    }
    	serviceListTable = serviceListTable + tableDataEnd;
    	document.getElementById("ServiceListDiv").innerHTML=serviceListTable;
    	
    	
    	
    	 fnGetDestLabelValue_onLoad(dropDownLovIDLabels);
    	 
	//FOR SERVICE LIST ENDS
    serviceSummaryFetched = true;
    $("#ServiceListDiv").find("[ctrltype='dropdownlink']").each(function() {
		attachCSForAS($(this));
	});
    if(isPrReuseFlag==1)
    {
    	
    	
    	$("#ServiceList").find("input,button,select").attr("readonly",true);
    	$("#ServiceList").find("img").attr("disabled","disabled");
 		$("#ServiceList").find("a").attr("disabled","disabled");
 		document.getElementById('saveImageScm').disabled=true;
 		disabledKeys();
 		
    }
   
    
    // enableDisableCLEP(serSummSection);
    fetchScmPrStatus();
	var serSec = new Array();
	serSec[0]="SERVICESUMMARY";
	serSec[1]="tblServiceSummary";
	if(null == roleWiseSectionDetail){
    	// var callerWindowObj = dialogArguments;
    	var roleNamee = roleName;
    	
    	// roleWiseSectionDetail = getRoleWiseSectionDetails(roleName);
    	
    	}
    	
   //  enableDisableCommercialFields(serSec, roleWiseSectionDetail);
    
    }
    
    //This method is used to load  label Value using attMasterIDLabels
    function fnGetDestLabelValue_onLoad(dropDownLovIDLabels){
	var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
	
	var allLabelValue = jsonrpc.processes.fillAllDropDownDependentlabel(dropDownLovIDLabels);
    if(null != allLabelValue && allLabelValue.list.length>0){
		
    	destAttID=new Array();
    	var destAttIndex = 0;
		for(var j = 0;j<allLabelValue.list.length;j++){
			for(var h = 0; h<allLabelValue.list[j].serviceSummary.list.length;h++){
				var labelValue = allLabelValue.list[j].serviceSummary.list[h];
				destAttID[destAttIndex] = labelValue.destAttId;
				for(var i=1;i<=countAttributes;i++){
					if(document.getElementById("hdnProdAttVal_"+i) != null){
						if(document.getElementById("hdnProdAttVal_"+i).value == destAttID[destAttIndex]){
						
							if(labelValue.isReadOnly == '1'){
								if(document.getElementById("prodAttVal_"+i).type=='select-one')
									document.getElementById("prodAttVal_"+i).disabled=true;
								else
									document.getElementById("prodAttVal_"+i).readOnly=true; 
								
								document.getElementById("prodAttVal_"+i).zeroValueAllowed='Y';
							
								if(document.getElementById("autoSuggProdAttVal_"+i) != null){
									document.getElementById("autoSuggProdAttVal_"+i).disabled=true;
									document.getElementById("autoSuggProdAttLink_"+i).style.display='none';															
								}
							}
							if(labelValue.isReadOnly == '0'){
								if(document.getElementById("prodAttVal_"+i).type=='select-one')
									document.getElementById("prodAttVal_"+i).disabled=false;
								else
									document.getElementById("prodAttVal_"+i).readOnly=false; 
								
								document.getElementById("prodAttVal_"+i).zeroValueAllowed='N';
								if(document.getElementById("autoSuggProdAttVal_"+i) != null){
									document.getElementById("autoSuggProdAttVal_"+i).disabled=false;
									document.getElementById("autoSuggProdAttLink_"+i).style.display='block';															
								}
							}
						
							if(labelValue.mandatory == '0'){
								document.getElementById("prodAttVal_"+i).isSummaryReq='0';
								document.getElementById("prodAttVal_"+i).className='inputBorder2';
							}
							if(labelValue.mandatory == '1'){
								document.getElementById("prodAttVal_"+i).isSummaryReq='1';
								document.getElementById("prodAttVal_"+i).className='inputBorder1';
							}
							
							
							break;
						}
					}
				}
				destAttIndex++;
			}
		}
	}
}
function fetchScmPrStatus()
{
var scmPrStatusList;
var i;
var jsPrData =	{
	
		serviceProductID:serviceProductID
		
	};
	
scmPrStatusList= jsonrpc.processes.populateScmPrStatus(jsPrData);

for(i=0;i<scmPrStatusList.list.length;i++)

{
if(scmPrStatusList.list.length==1){
document.getElementById('prNumber').value=scmPrStatusList.list[i].pr_number;
document.getElementById('scmProgressStatus').value=scmPrStatusList.list[i].scmProgress_Status;
}
}
}
function displayScmLine()
{
	var hdnPrLine=document.getElementById('hdnPrLineInfo');
	hdnPrLine.value=hiddenPrLineinfo;
	if(hiddenPrLineinfo==1){
	document.getElementById('ViewScmLineItem').style.display='block';
	}
}

//[001] Start
function repushXML(){
var res;
var msg="Some Problem occured while Repushing PR Request";
res=jsonrpc.processes.callCreateScmXmlRepush(poNumber,servicesID,serviceProductID);
if(res==1){
	msg="PR Request has been Repushed Successfully";
	document.getElementById('btnRepush').disabled=true;
}
alert("Message : "+msg);
}
//[001] End
//Added for disable ChangeViewProductCatelog.jsp
function disableChangeView()
{
var jsPrData =	{
	
		serviceProductID:serviceProductID
		
	};
	
isPrReuse= jsonrpc.processes.disableViewScm(jsPrData);
isPrReuseFlag=document.getElementById('IsPrReuse').value=isPrReuse.is_Pr_Reuse;
hiddenServiceID=document.getElementById('changeServiceID').value=isPrReuse.changeServiceId;
hiddenChangeServiceId=document.getElementById('serviceIDD').value=isPrReuse.serviceId;

  
}

function showPR(){

	var  btn=document.getElementById('btnServiceSummary');
	var btnValue=document.getElementById('btnChargeSummary');
	
	 if (btn.value=="-"){
	    document.all.item('tblServiceSummary').style.display="None";
	    btn.value="+";
	 }    
 if(btnValue.value=="-")
 {
	 document.getElementById('ChargeList').style.display='None';
	 btnValue.value="+";
 }
 
	
    window.showModalDialog(pathPrReuse,window,"status:false;dialogWidth:700px;dialogHeight:500px");

}
function PopulateDataForPRReuse(){
	if(!serviceSummaryFetched)
	{
		getServiceSummary1();
	}
	
	 pr_Value=localStorage.getItem('seletedPrId');
	 prNumber=localStorage.getItem('selectedPrNum');
	 document.getElementById('saveImageScm').disabled=false;
 	
	if(prNu!="")
 	{
 	if(pr_Value==prNu)
 	{
 	alert("You Already Selected This PR Number! So Please Select Another PR Number");
 	return;
 	}
 	}
 	document.getElementById("hdnprValue").value=pr_Value;
 	prNu=document.getElementById("hdnprValue").value;
 	if(pr_Value!=0)
 	{
 		disabledKeys();
 		$("#ServiceList").find("input,button,select").attr("readonly",true);
 		$("#ServiceList").find("img").attr("disabled","disabled");
 		$("#ServiceList").find("a").attr("disabled","disabled");
 		
 	}
 	var jsData=
    {
	prId:pr_Value
	
	};
 	try{ 
		
	
		var PRList = jsonrpc.processes.getPRDetailsForPRResue(jsData);
		var lableDescription = jsonrpc.processes.getDescrption();
		
		var rowCount = $('#ServiceList tr').length;
		var j=0;
		for (var i=1; i<=rowCount;i++,j++){
			if(document.getElementById('prodExpVal_'+i).value=="LOV"){
				document.getElementById('autoSuggProdAttVal_'+i).value=PRList.list[j].displayText;
			    document.getElementById('prodAttVal_'+i).value=PRList.list[j].attvalue;
			   
			}
			else if((document.getElementById('prodExpVal_'+i).value!="LOV")&& (lableDescription.descLabel==PRList.list[j].attDescription)){
			
			document.getElementById('prodAttVal_'+i).value=poNumber+"/"+servicesID+"/"+serviceProductID;
		 		
			}
			else
			{
			document.getElementById('prodAttVal_'+i).value=PRList.list[j].attvalue;
			}
			
		 		
		    }
		  
		    
		    drawPrReuseTable(); 
		    document.getElementById('prNumber').value=prNumber;
		    pr_viewValue=true;
		    document.getElementById('hdnNotSaveFlag').value=0;
		    document.getElementById("btnAddCharges").disabled=true;
		    document.getElementById("btnDeletePoDetail").disabled=true;
		    document.getElementById('creatNewPr').disabled=false;
	 	 }
 	catch(e){
	 	alert(e.message);
 	}
	}
function drawPrReuseTable()
{
	if((pr_Value!=0)){
		jsChargeData={
				prId:pr_Value
					};
		var chargelists;
		 
	    chargelists = jsonrpc.processes.populateScmLineDetails(jsChargeData);
	   
	    
	    iTotalLength=chargelists.list.length;
	    var chargeTableDiv =  document.getElementById('contentscroll');
	     if(iTotalLength>0){
	    	chargeTableDiv.innerHTML = "";
	    	
	    }
	    
	    if(iTotalLength > 1){
				 document.getElementById('SelectAllChckCharges').disabled=false;
			
			}else{
				 document.getElementById('SelectAllChckCharges').disabled=true;
			}
				    if(iTotalLength > 0){
				// document.getElementById('viewChargeLineTabNavigatorId').style.display='block';			
				var iChargeIndexCounter = 0;
				var hdnChargeCount = document.getElementById('hdnChargeCount');
				
				//var tableCharges = document.getElementById('tableCharges');
				var counter = 0;
				var chargeListTable = "<table cellpadding='0' cellspacing='0' class='content' id='tableCharges'>";
		    	var tableEnd = '</table>';
		    	var tableRowStart= "<tr>";
		    	var tableRowEnd = "</tr>";
		    	var tableDataStart;
		    	var tableDataEnd = "</td>";
		    	var str;
		    	var aopYearOption=getSelectedAopYear();
		    	var itemSelectedValue = new Array();
	 			var itemSelectedLabel = new Array();
	 			var delLocSelectedValue = new Array();
	 			var delLocSelectedLabel = new Array();
	 			var subInvSelectedValue = new Array();
	 			var subInvSelectedLabel = new Array();
	 			var aop1BudgetSelectedLabel = new Array();
	 			var aop1BudgetSelectedValue = new Array();
	 			var aop2BudgetSelectedValue = new Array();
	 			var aopYearSelectedValue=new Array();
	 			
	 	    	for (iChargeIndexCounter = 0 ; iChargeIndexCounter < iTotalLength; iChargeIndexCounter++){		    	
	 	    		var itemTypeOption = getSelectedItemCodeLabel(chargelists.list[iChargeIndexCounter].itemCode_Id);
	 	    		var delLocOption=getSelectedDeliveryLocLabel(chargelists.list[iChargeIndexCounter].deliveryLocationId);
	 	    		var subInvOption=getSelectedSubInventoryLabel(chargelists.list[iChargeIndexCounter].subInventryId);
	 	    		var aop1BudgetOption=getSelectedAop1BudgetLabel(chargelists.list[iChargeIndexCounter].aop1_Id);
	 	    		var aop2BudgetOption=getSelectedAop2Budget(chargelists.list[iChargeIndexCounter].aop1_Id);
	 	    		chargeListTable = chargeListTable + tableRowStart;
	 	    		
	 				counter++;
	 				
	 				
	 				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col1 toprow'>";
	 				str ="<input name='chkSelectChargeDetail' id='chkSelectChargeDetail'  type='checkbox' value='"+counter+"'onclick='allChargesCheckNone()' >";
	 				str=str+"<input type='hidden' name='hdnDeleteChargeId' id='hdnDeleteChargeId"+counter+"'  value=''>";
	 				str=str+"<input type='hidden' name='hdnChargeId' id='hdnChargeId"+counter+"'  value='"+chargelists.list[iChargeIndexCounter].chargeId_Scm+"'>";
	 				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
	 				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col2' >";
	 				
	 				str="<input type='text' value='' onmouseover='getTip(value)'  countervalItem='"+ counter +"' onmouseout='UnTip()' style='width:width:175px' isRequired='0'  name='itemName' ctrltype='dropdown' class='dropdownMargin' retval='AUTOSUGGESTITEMCODE' id='itemName" + counter + "'><a name='chargeNameLink' id='chargeNameLink" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownItemCode(" + counter + ")'>Show</a>";
	 				str=str+"<input type='hidden' name='txtItemCode' id='txtItemCode"+counter+"' value='-1' countervalItem='"+ counter +"'>";
	 				itemSelectedValue[iChargeIndexCounter]=chargelists.list[iChargeIndexCounter].itemCode_Id;
	 				itemSelectedLabel[iChargeIndexCounter]=itemTypeOption;
	 				// str=str+"<input type='hidden' name='hdnChargeId' id='hdnChargeId"+counter+"'  value='"+chargelists.list[iChargeIndexCounter].chargeId_Scm+"'>";
	 				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
	 				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col10' >";
	 				str="<input onmouseover='getTip(value)' onmouseout='UnTip()'   type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder1' name='txtValue' id='txtValue"+counter+"'value='"+chargelists.list[iChargeIndexCounter].cahrgeValue+"' maxlength='340'>";
	 				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
	 				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col10' >";
	 				str="<input onmouseover='getTip(value)' onmouseout='UnTip()'  type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder1' name='txtQuantity' id='txtQuantity"+counter+"' value='"+chargelists.list[iChargeIndexCounter].quntity+"' maxlength='340'>";
	 				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
	 				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col5' >";
	 				str="<input type='text'  value='Select Delivery Location' onmouseover='getTip(value)'  countervalDel='"+ counter +"' onmouseout='UnTip()' style='width:width:175px' isRequired='0'  name='delLocation' ctrltype='dropdown' class='dropdownMargin' retval='AUTOSUGGESTDELLOCATION' id='delLocation" + counter + "'><a name='chargeNameLink' id='chargeNameLink" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownDelLocation(" + counter + ")'>Show</a>";
	 				str=str+"<input type='hidden' name='txtDelLocId' id='txtDelLocId"+counter+"' value='-1' countervalDel='"+ counter +"'>";
	 				delLocSelectedLabel[iChargeIndexCounter]=delLocOption;
	 				delLocSelectedValue[iChargeIndexCounter]=chargelists.list[iChargeIndexCounter].deliveryLocationId;
	 				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
	 				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col6' >";
	 				str="<input type='text'  value='Select SubInventory' onmouseover='getTip(value)'  countervalSub='"+ counter +"' onmouseout='UnTip()' style='width:width:175px' isRequired='0'  name='subInvent' ctrltype='dropdown' class='dropdownMargin' retval='AUTOSUGGESTSUBINVENTORY' id='subInvent" + counter + "'><a name='chargeNameLink' id='chargeNameLink" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownSubInventory(" + counter + ")'>Show</a>";
	 				str=str+"<input type='hidden' name='txtSubInvent' id='txtSubInvent"+counter+"' value='-1' countervalSub='"+ counter +"'>";
	 				subInvSelectedValue[iChargeIndexCounter]=chargelists.list[iChargeIndexCounter].subInventryId;
	 				subInvSelectedLabel[iChargeIndexCounter]=subInvOption;
	 				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
	 				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col7' >";
	 				str="<input type='text'  value='Select Budget Head1' onmouseover='getTip(value)'  countervalBud='"+ counter +"' onmouseout='UnTip()' style='width:width:175px' isRequired='0'  name='budgHead' ctrltype='dropdown' class='dropdownMargin' retval='AUTOSUGGESTBUDGHEAD'  id='budgHead" + counter + "'><a name='chargeNameLink' id='chargeNameLink" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownBudgetHead(" + counter + ")'>Show</a>";
	 				str=str+"<input type='hidden' name='txtBudgetHead' id='txtBudgetHead"+counter+"' value='-1' countervalBud='"+ counter +"'>";
	 				aop1BudgetSelectedValue[iChargeIndexCounter]=chargelists.list[iChargeIndexCounter].aop1_Id;
	 				aop1BudgetSelectedLabel[iChargeIndexCounter]=aop1BudgetOption;
	 				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
	 				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col8' >";
	 				str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px' name='txtBudget' id='txtBudget"+counter+"' isRequired='0' Disp=''disabled='true' class='dropdownMargin'";
	 				str = str + aop2BudgetOption;
	 				str = str + "</select>";
	 				aop2BudgetSelectedValue[iChargeIndexCounter]=chargelists.list[iChargeIndexCounter].aop2_Id;
	 				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
	 				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col9' >";
	 				str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px' name='financeYear' id='financeYear"+counter+"' isRequired='0' Disp='' class='dropdownMargin'";
	 				str = str + aopYearOption;
	 				str = str + "</select>";
	 				aopYearSelectedValue[iChargeIndexCounter]=chargelists.list[iChargeIndexCounter].aopYear;
	 				
	 				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
	 				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col10' >";
	 				str="<input onmouseover='getTip(value)' onmouseout='UnTip()'   type='text' readonly='true' style='width:175px' isRequired='0' Disp='' class='dropdownMargin1' name='txtPoNumber' id='txtPoNumber"+counter+"'value='"+chargelists.list[iChargeIndexCounter].poNumer+"' maxlength='340'>";
	 				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
	 				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col10' >";
	 				str="<input onmouseover='getTip(value)' onmouseout='UnTip()'   type='text' readonly='true' style='width:175px' isRequired='0' Disp='' class='dropdownMargin1' name='txtPoDate' id='txtPoDate"+counter+"'value='"+chargelists.list[iChargeIndexCounter].poDtae+"' maxlength='340'>";
	 				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
	 				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col10' >";
	 				str="<input onmouseover='getTip(value)' onmouseout='UnTip()'   type='text' readonly='true' style='width:175px' isRequired='0' Disp='' class='dropdownMargin1' name='txtPoAmount' id='txtPoAmount"+counter+"'value='"+chargelists.list[iChargeIndexCounter].poAmount+"' maxlength='340'>";
	 				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
	 				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col10' >";
	 				str="<input onmouseover='getTip(value)' onmouseout='UnTip()'   type='text' readonly='true' style='width:175px' isRequired='0' Disp='' class='dropdownMargin1' name='txtIsActive' id='txtIsActive"+counter+"'value='"+chargelists.list[iChargeIndexCounter].isActive+"' maxlength='340'>";
	 				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
	 				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col10' >";
	 				str="<input onmouseover='getTip(value)' onmouseout='UnTip()'  readonly='true' type='text' style='width:175px' isRequired='0' Disp=''  class='dropdownMargin1' name='txtScmMessage' id='txtScmMessage"+counter+"'value='"+chargelists.list[iChargeIndexCounter].scmMessage+"' maxlength='340'>";
	 				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
	 				chargeListTable = chargeListTable + tableRowEnd;
	 			}
	 	    	chargeListTable = chargeListTable + tableEnd;
	 	    	chargeTableDiv.innerHTML=chargeListTable;
	 	    	for (iChargeIndexCounter = 0 ; iChargeIndexCounter < iTotalLength; iChargeIndexCounter++){
	 	     		$("#txtItemCode" + (iChargeIndexCounter +1)).val(itemSelectedValue[iChargeIndexCounter]);
	 	     		$("#itemName" + (iChargeIndexCounter +1)).val(itemSelectedLabel[iChargeIndexCounter]);
	 	     		$("#txtDelLocId" + (iChargeIndexCounter +1)).val(delLocSelectedValue[iChargeIndexCounter]);
	 	     		$("#delLocation" + (iChargeIndexCounter +1)).val(delLocSelectedLabel[iChargeIndexCounter]);
	 	     		$("#txtSubInvent" + (iChargeIndexCounter +1)).val(subInvSelectedValue[iChargeIndexCounter]);
	 	     		$("#subInvent" + (iChargeIndexCounter +1)).val(subInvSelectedLabel[iChargeIndexCounter]);
	 	     		$("#txtBudgetHead" + (iChargeIndexCounter +1)).val(aop1BudgetSelectedValue[iChargeIndexCounter]);
	 	     		$("#budgHead" + (iChargeIndexCounter +1)).val(aop1BudgetSelectedLabel[iChargeIndexCounter]);
	 	     		$("#txtBudget" + (iChargeIndexCounter +1)).val(aop2BudgetSelectedValue[iChargeIndexCounter]);
	 	     		$("#financeYear" + (iChargeIndexCounter +1)).val(aopYearSelectedValue[iChargeIndexCounter]);
	 	     		
	 	     		
	 	     	}
	 	    
	 	     	hdnChargeCount.value = counter;
	 	     	$("#tableCharges").find("input,button,select").attr("disabled", "disabled");
	 	    	
	 			$("#tableCharges").find("[ctrltype='dropdownlink']").each(function() {
	 	    		attachCSForAS($(this));
	 	    	});
	 			
	 			scmLineDetailsFetched=true;
	 			
	 			   

	 	}
	}	   
	 else{
	 	
	 	return false;
	 	}
}
function createNewPr(){
var answer=confirm("Do You Want Continue! Your Data might be lost");
	if(answer==false)
	{
		return;	
	}
	if(!scmLineDetailsFetched)
	{
		drawViewChargeTable1();
	}
	
    pr_viewValue=false;
    document.getElementById('saveImageScm').disabled=false;
	if(document.getElementById('hdnNotSaveFlag').value==1)
	{
		var  btn=document.getElementById('btnServiceSummary');
		var btnValue=document.getElementById('btnChargeSummary');
		
		 if (btn.value=="+"){
		    document.all.item('tblServiceSummary').style.display="block";
		    btn.value="-";
		 }    
	 if(btnValue.value=="+")
	 {
	 	document.all.item('ChargeList').style.display="block";
		btnValue.value="-";
	 }
	 document.getElementById('createNewpr').value=1;
		return;
	}
	if(document.getElementById('hdnNotSaveFlag').value==0||pr_viewValue==false)
	{
		if(!serviceSummaryFetched)
		{
			getServiceSummary1();
		}
		document.getElementById('createNewpr').value=2;
		var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		var len=chkChargeLine.length;
		for(var i=len-1;i>=0;i--){
		var chkboxCharge=chkChargeLine[i];
		var index=chkboxCharge.value;
		document.getElementById("hdnDeleteChargeId"+index).value=document.getElementById("hdnChargeId"+index).value;
		deleteScmLineIdArray.push(document.getElementById("hdnDeleteChargeId"+index).value);
		}
		
		document.getElementById("btnAddCharges").disabled=false;
		document.getElementById("btnDeletePoDetail").disabled=false;
		$("#ServiceList").find("a").removeAttr('disabled');
		enabledKeys();
		document.getElementById('prNumber').value="";
		document.getElementById('hdnprValue').value=0;
		var  btn=document.getElementById('btnServiceSummary');
		var btnValue=document.getElementById('btnChargeSummary');
		
		
		 if (btn.value=="+"){
		    document.all.item('tblServiceSummary').style.display="block";
		    btn.value="-";
		 }    
	 if(btnValue.value=="+")
	 {
		 
		document.all.item('ChargeList').style.display="block";
		btnValue.value="-";
	 }
	
		var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		var len=chkChargeLine.length;
		var answer=true;
		
				if(chkChargeLine.length>0)
				{
				var count=0;
				for(var i=len-1;i>=0;i--)
			{
				if(chkChargeLine[i].checked==false)
				{
					if(answer)
					{
						
					}
					else
					{
						return false;
					}
					deleteScmLine(chkChargeLine[i]);
					count=count+1;
				}
			}
				
				}
	 var jsData =			
		{   sendToSCM:'1',
			serviceDetailID:serviceDetailID,
			serviceId:servicesID
		};
		
		var test11 = jsonrpc.processes.populateServiceAttributeListSCM(jsData,sessionid);
		
		var lableDescription = jsonrpc.processes.getDescrption();
	
		var serviceCounter=1;
		for (var r=0; r<test11.list.length;r++,serviceCounter++){
			
			if(document.getElementById('prodExpVal_'+serviceCounter).value=="LOV"){
				
				document.getElementById('autoSuggProdAttVal_'+serviceCounter).value="";
				document.getElementById('autoSuggProdAttVal_'+serviceCounter).readOnly=false;
			   
			   if(test11.list[r].attDefaultVal){
		      	
		      	for(var x=0;x<test11.list[r].serviceSummaryLov.list.length;x++){
						
					if(test11.list[r].serviceSummaryLov.list[x].serviceSummaryValues == test11.list[r].attDefaultVal )
					{
						document.getElementById("autoSuggProdAttVal_"+serviceCounter).value=test11.list[r].serviceSummaryLov.list[x].serviceSummaryText;
						document.getElementById("prodAttVal_"+serviceCounter).value=test11.list[r].serviceSummaryLov.list[x].serviceSummaryValues;	
						document.getElementById('autoSuggProdAttVal_'+serviceCounter).readOnly=true;
					}
				}
			} 
			  
			}
			else if((document.getElementById('prodExpVal_'+serviceCounter).value!="LOV")&& (lableDescription.descLabel==test11.list[r].attributeKey)){
			
			document.getElementById('prodAttVal_'+serviceCounter).value=poNumber+"/"+servicesID+"/"+serviceProductID;
		 		
			}else if(document.getElementById('prodExpVal_'+serviceCounter).value=="datetime"){
				document.getElementById('prodAttVal_'+serviceCounter).value=test11.list[r].rfsDate;
				
			}
			
		else{
			if(((test11.list[r].isServiceSummReadonly==1)&&(lableDescription.preparer||lableDescription.deliverRequester)))
			{
				{
					document.getElementById('prodAttVal_'+serviceCounter).value="";
					document.getElementById('prodAttVal_'+serviceCounter).readOnly=true;
					
				}
				
			}
			else
			{
				$("#ServiceList").find("img").removeAttr('disabled');
				document.getElementById('prodAttVal_'+serviceCounter).value="";
				document.getElementById('prodAttVal_'+serviceCounter).readOnly=false;
					
				
			}
			}
			
			}
	
		return;
	}
	
	
}

function deleteScmLine(current) {
	
    
    while ( (current = current.parentElement)  && current.tagName !="TR");
    current.parentElement.removeChild(current);
}
// Function to get IsPublished flag so that we can disable Create_New_Pr and Pr_Reuse Button
function getIsPublished(){
	var isPublished;
	isPublished=jsonrpc.processes.callGetIsPublished(servicesID);
	if(isPublished==1){
		document.getElementById('btnprReuse').disabled=true;
		document.getElementById('creatNewPr').disabled=true;
	}
}
function DeleteCharge()
{

	var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
	if(chkChargeLine==null)
	{
		alert('No SCM Line Present');
		return;
	}

	var len=chkChargeLine.length;
	if(len==0)
	{
		 alert('No SCM Line Present');
		 document.getElementById('SelectAllChckCharges').checked=false;
		 document.getElementById('SelectAllChckCharges').disabled=true;
		 return;
		
	}
	if(len==null)
	{
		if(chkChargeLine.checked==true)
		{
			var answer = confirm("Do You Want To Continue Deleting Selected SCM Line ?")
			if(answer)
			{
			
			}
			else
			{
				return false;
			}
			deleteChargeLine(chkChargeLine);
		}
		else
		{
			alert('No SCM Line Selected.');
			return;
		}
	}
	else
	{//multiple checkboxex present
		var count=0;
		var answer = confirm("Do You Want To Continue Deleting Selected SCM Line");
		for(var i=len-1;i>=0;i--)
		
		{
			
			var chkboxCharge=chkChargeLine[i];
			var index=chkboxCharge.value;
			if(chkChargeLine[i].checked==true)
			{
				
				if(answer)
				{
					
					document.getElementById("hdnDeleteChargeId"+index).value=document.getElementById("hdnChargeId"+index).value;
					deleteScmLineIdArray.push(document.getElementById("hdnDeleteChargeId"+index).value);
					
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
			alert('No SCM Line Selected.');
			return;
		}
	}
	if(chkChargeLine.length==0)
	{
		 document.getElementById('SelectAllChckCharges').checked=false;
		 document.getElementById('SelectAllChckCharges').disabled=true;
		
	}
	
}
function deleteChargeLine(current) {

    
    while ( (current = current.parentElement)  && current.tagName !="TR");
    current.parentElement.removeChild(current);
}
function fnCheckAllScmLines()
{
 
	   
	    chargesTab = document.getElementById('tableCharges');
        var rowlen= chargesTab.rows.length;
       
         if(rowlen==0)
			 {
			 
			 document.getElementById('SelectAllChckCharges').checked=false;
			 document.getElementById('SelectAllChckCharges').disabled=true;
			 return false;
			 }
         var chkLength1=document.getElementsByName('chkSelectChargeDetail').length;
		if(document.getElementById('SelectAllChckCharges').checked==true)
		{
			        if(chkLength1==undefined)
	        
	                {
		               
			        	document.getElementsByName('chkSelectChargeDetail').checked=true;
		                
					}
          
			      else
			        {    
				         for (var i =0; i<chkLength1; i++)
						   { 
				        	 document.getElementsByName('chkSelectChargeDetail')[i].checked=true ;
					       }
				    }
	
	 
	         
	
		}	
	else
	   {
	           if(chkLength1==undefined)
        
		          {
	        	   document.getElementsByName('chkSelectChargeDetail').checked=false;
		          }
			      else
			      {    
			         for (var i =0; i<chkLength1; i++)
					   { 
			        	 document.getElementsByName('chkSelectChargeDetail')[i].checked=false ;
						
					   }
				  }
	
		       
		}
	
	}

function disabledKeys()
{
	var ar = new Array(37, 38, 39, 40);
    $(document).keydown(function(e) {
        var key = e.which;
        if ($.inArray(key, ar) > -1) {
            e.preventDefault();
            return false; 
       }
       
    });
        

}
function enabledKeys()
{
	 var ar = new Array(37, 38, 39, 40);
     $(document).keydown(function(e) {
         var key = e.which;
         if ($.inArray(key, ar) > -1) {              
             return true;
         }           
     });
}

function disablePrReuse()
{
	 if((isPrReuseFlag==1)&&((hiddenServiceID&&hiddenChangeServiceId)!=0))
	    {
	    	
	    	if(hiddenServiceID!=hiddenChangeServiceId)
	    	{
	    		
	    	$("#ServiceList").find("input,button,select").attr("readonly",true);
	    	$("#ServiceList").find("img").attr("disabled","disabled");
	 		$("#ServiceList").find("a").attr("disabled","disabled");
	 		document.getElementById('btnprReuse').disabled=true;
	 		document.getElementById('saveImageScm').disabled=true;
	 		document.getElementById('creatNewPr').disabled=true;
	 		disabledKeys();
	    	}
	 		
	    }	
}
function getServiceStatus(servicesID)
{
	
	var serviceStatus;
     serviceStatus = jsonrpc.processes.findServiceStatus(servicesID);
     return serviceStatus;
   	
}
