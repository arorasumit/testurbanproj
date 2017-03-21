/******************************************************************************************************************
=========================================================
FileName	:productCatelogUtility.js
@Author		:Anil Kumar
Date		:26-feb-11
Purpose		:Use product catelog functionalities			
=========================================================
********************************************************************************************************************/

/*Function	:fillChargeTypeForHardware
*@Author	:Anil Kumar
*Date		:26-feb-11
*Purpose	:To fetch charge type based on bill format for hardware 
**/
//<!--Tag Name Resource Name  Date		CSR No			Description -->
//<!--[001]	 LAWKUSH		13-July-11	CSR00-05422     For changing PO detail no. and Customer po detail no  -->
//<!--[002]	 Raghu	    13-July-2012				Raghu:Check MaxLength of text box-->
//<!--[003] VIPIN SAHARIA 04-06-2014 Added logic for FxChargeID required for DC_COLO_SERVICE_TAX Charge (Hardware DC) 
//<!--[004]	VIPIN SAHARIA 17-07-2014 Added methods to get charge details for Service - Sales charges validation for DC hardware products.
//<!--[005]Pankaj Thakur   26-02-2015  Added method 'checkServiceTreeContainsCommercialLineForCopyProduct()' to Restrict  copy commerical line at PM and SED stage on click of radio button in change order 
//<!--[OSP tag] Nagarjuna while adding Non commercial Lines by PM,SED we are slecting index 1 value as default value but in case of OSP selected index 1 means Yes but we must show NO by default having index as 0 -->
/*Functions	:populateStore and  fnFetchLicCompany
*@Author	:Ramana Kumar
*Date		:14-july-11
*Purpose	:The first populates store basing on licensing company and the later populates licensing company basing on Legal entity. 
**/

/*function fillAllChargeTypeForHardware(path,productid,billformat,chargeTypeComboDivId, counter){											
	jsonrpc = new JSONRpcClient(path + "/JSON-RPC");				
	chargeCombo = jsonrpc.processes.populateChargeTypeForHardware(productid,billformat);
	if(chargeCombo.list.length==0){
		alert('No Charge Type against this product!!');
	}
	//Puneet for performance tuning
	String option="";
	for(z=0;z<chargeCombo.list.length;z++){
		option = option + "<option value='" + chargeCombo.list[z].chargeTypeID + "'>" + chargeCombo.list[z].chargeTypeName + "</option>";
	}
   	for (j = 1; j <= counter; j++){
   		var chargeTypeSelect = "<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px;'  name='ddCType' id='ddCType"+j+"' isRequired='0' Disp='' class='dropdownMargin' onchange='javascript:CheckRCRequiredForCLEP(this,"+j+");fillChargeNames("+j+",this.value,-1);changeFrequency("+j+");calculateFreqAmount("+j+");fillEndDateLogic("+j+");'>";
   		chargeTypeSelect = chargeTypeSelect + option + "</select>";
   		chargeTypeComboDiv = document.getElementById(chargeTypeComboDivId+j);
   		//Puneet for performance tuning
   		chargeTypeComboDiv.innerHTML = chargeTypeSelect;
   		var local_RowCount = chargeTypeCombo.length;
   		for (i = 1; i <  local_RowCount; i++){
   	   		chargeTypeCombo.remove(1);
   		}
   		for(i=0;i<gbchargeTypeIds.length;i++){
   			var option = document.createElement("option");
   		   	option.text = gbchargeTypeValues[i];
   			option.value = gbchargeTypeIds[i];
   			try{
   				chargeTypeCombo.add(option, null); //Standard
   			}catch(error){
   				chargeTypeCombo.add(option); // IE only
   			}
   	   	}	
   	}
}*/

var serSummSection = "SERVICESUMMARY";
var billingSection = "BILLING";
var hardwareSection = "HARDWARE";
var serviceLocSection = "SERVICELOCATION";
var compSection = "COMPONENT";
function fillChargeTypeForHardware(path,productid,billformat,chargeTypeCombo){											
	jsonrpc = new JSONRpcClient(path + "/JSON-RPC");				
	chargeCombo = jsonrpc.processes.populateChargeTypeForHardware(productid,billformat);
	if(chargeCombo.list.length==0){
		alert('No Charge Type against this product!!');
	}
	gbchargeTypeIds=new Array();
	gbchargeTypeValues=new Array()
	for(z=0;z<chargeCombo.list.length;z++){
		gbchargeTypeIds[z] = chargeCombo.list[z].chargeTypeID;
		gbchargeTypeValues[z] = chargeCombo.list[z].chargeTypeName;
	}
			
	var local_RowCount = chargeTypeCombo.length;
	for (i = 1; i <  local_RowCount; i++){
		chargeTypeCombo.remove(1);
	} 
	for(i=0;i<gbchargeTypeIds.length;i++){
		var option = document.createElement("option");
	   	option.text = gbchargeTypeValues[i];
		option.value = gbchargeTypeIds[i];
		try{
			chargeTypeCombo.add(option, null); //Standard
		}catch(error){
			chargeTypeCombo.add(option); // IE only
		}
	}	      
}
//Puneet to get the charge type
function getChargeType(chargeTypeSelectedVal){
	//Puneet for performance tuning, getting charge type
	srvDetailId=document.getElementById("hdnServiceDetailID").value;
	var chargeCombo;
	var chargeTypeOption="<option value='-1'>Select a Type</option>";
	if(document.getElementById('hdnHardwareInfo').value==1){
		var txtBillingformat = document.getElementById('txtBillingformat');
		if(txtBillingformat.value==2){
			chargeCombo = jsonrpc.processes.populateChargeTypeForHardware(srvDetailId,1);
		}else if(txtBillingformat.value==3){
			chargeCombo = jsonrpc.processes.populateChargeTypeForHardware(srvDetailId,2);
		}
	}else{
		chargeCombo = jsonrpc.processes.populateChargeType(srvDetailId);
	}
	if(null != chargeCombo){
		if(chargeCombo.list.length==0){
			alert('No Charge Type against this product!!');
		}else{
			for(z=0;z<chargeCombo.list.length;z++){
				chargeTypeOption = chargeTypeOption + "<option value='" + chargeCombo.list[z].chargeTypeID + "'>" + chargeCombo.list[z].chargeTypeName + "</option>";
			}
		}
	}
	return chargeTypeOption;
}

/*Function	:fetchTaxRate
*@Author	:Anil Kumar
*Date		:09-march-11
*Purpose	:To fetch TaxRate in charge section.
**/
function fetchTaxRate(path,var_counter){
	var serviceType=document.getElementById("txtHdnServiceName").value.toUpperCase();
	var ponumber;
	var fxChargeId = document.getElementById("hdnFxChargeID"+var_counter).value;
	if(hdnChangeTypeId == 2 || hdnChangeTypeId == 5  || hdnChangeTypeId == 3 || hdnChangeTypeId == 1 || hdnChangeTypeId == 4){
		if(document.getElementById("hdnPoNoOfCharge"+var_counter).value != 0){
	 		ponumber=document.getElementById("hdnPoNoOfCharge"+var_counter).value;	
	 	}else{
		 	var ponumberCombo=document.getElementById("txtPODetailNo");	
			ponumber=ponumberCombo.options[ponumberCombo.selectedIndex].value;
	 	}
	}else{
		var ponumberCombo=document.getElementById("txtPODetailNo");	
		ponumber=ponumberCombo.options[ponumberCombo.selectedIndex].value;
	}
	var chargeNameCombo=document.getElementById("txtCName"+var_counter);
	if(serviceType.indexOf("SERVICE")!=-1 || document.getElementById('hdnISFLEFLAG').value=='1'){
		var mappingID=chargeNameCombo.options[chargeNameCombo.selectedIndex].value;	
		jsonrpc = new JSONRpcClient(path + "/JSON-RPC");	
		taxrate=jsonrpc.processes.populateTaxRate(ponumber,mappingID);	
		document.getElementById("txtTaxRate"+var_counter).value=taxrate;	
	}else{
		var storeCombo=document.getElementById('txtStore');
		var hTypeCombo=document.getElementById('txtHtype');
		var formAvailCombo=document.getElementById('txtform');
		var nSaleCombo=document.getElementById('txtNSale');
		var tSaleCombo=document.getElementById('txtTSale');
		if((tSaleCombo.selectedIndex==-1||tSaleCombo.selectedIndex==0) 
				&& (nSaleCombo.selectedIndex==-1||nSaleCombo.selectedIndex==0)
				&& (formAvailCombo.selectedIndex==-1||formAvailCombo.selectedIndex==0)
				&& (storeCombo.selectedIndex==-1||storeCombo.selectedIndex==0)
				&& (hTypeCombo.selectedIndex==-1||hTypeCombo.selectedIndex==0)){
				//alert('please select all hardware related details');
		}
			/*else if(storeCombo.selectedIndex==-1||storeCombo.selectedIndex==0)
			{
				//alert('please select all hardware related details');
			}
			else if(formAvailCombo.selectedIndex==-1||formAvailCombo.selectedIndex==0)
			{
				//alert('please select all hardware related details');
			}
			else if(nSaleCombo.selectedIndex==-1||nSaleCombo.selectedIndex==0)
			{
				//alert('please select all hardware related details');
			}
			else if(tSaleCombo.selectedIndex==-1||tSaleCombo.selectedIndex==0)
			{
				//alert('please select all hardware related details');
			}*/
			else
			{				
				var storeid=storeCombo.options[storeCombo.selectedIndex].value;
				var hTypeId=hTypeCombo.options[hTypeCombo.selectedIndex].value;
				var formAvailId=formAvailCombo.options[formAvailCombo.selectedIndex].value;
				var nSaleId=nSaleCombo.options[nSaleCombo.selectedIndex].value;
				var tSaleId=tSaleCombo.options[tSaleCombo.selectedIndex].value;
														
				jsonrpc = new JSONRpcClient(path + "/JSON-RPC");	
				taxrate=jsonrpc.processes.populateTaxRateForHardware(storeid,nSaleId,tSaleId,formAvailId,hTypeId,ponumber,fxChargeId);					
				document.getElementById("txtTaxRate"+var_counter).value=taxrate;	
			}
		}	
}

/*Function	:fetchTaxRateForAutoSuggest
*@Author	:Puneet
*Date		:17-July-2013
*Purpose	:To fetch TaxRate in charge section.
**/
function fetchTaxRateForAutoSuggest(path,var_counter){
	var serviceType=document.getElementById("txtHdnServiceName").value.toUpperCase();
	var ponumber;
	var fxChargeId = document.getElementById("hdnFxChargeID"+var_counter).value;
	if(hdnChangeTypeId == 2 || hdnChangeTypeId == 5  || hdnChangeTypeId == 3 || hdnChangeTypeId == 1 || hdnChangeTypeId == 4){
		if(document.getElementById("hdnPoNoOfCharge"+var_counter).value != 0){
	 		ponumber=document.getElementById("hdnPoNoOfCharge"+var_counter).value;	
	 	}else{
		 	//var ponumberCombo=document.getElementById("txtPODetailNo");	
			//ponumber=ponumberCombo.options[ponumberCombo.selectedIndex].value;
	 		ponumber=document.getElementById("txtPODetailNo").value;
	 	}
	}else{
		//var ponumberCombo=document.getElementById("txtPODetailNo");	
		//ponumber=ponumberCombo.options[ponumberCombo.selectedIndex].value;
		ponumber=document.getElementById("txtPODetailNo").value;
	}
	var chargeNameCombo=document.getElementById("txtCName"+var_counter);
	if(serviceType.indexOf("SERVICE")!=-1 || document.getElementById('hdnISFLEFLAG').value=='1'){
		//var mappingID=chargeNameCombo.options[chargeNameCombo.selectedIndex].value;
		var mappingID=chargeNameCombo.value;
		jsonrpc = new JSONRpcClient(path + "/JSON-RPC");	
		taxrate=jsonrpc.processes.populateTaxRate(ponumber,mappingID);	
		document.getElementById("txtTaxRate"+var_counter).value=taxrate;	
	}else{
		var storeCombo=document.getElementById('txtStore');
		var hTypeCombo=document.getElementById('txtHtype');
		var formAvailCombo=document.getElementById('txtform');
		var nSaleCombo=document.getElementById('txtNSale');
		var tSaleCombo=document.getElementById('txtTSale');
		if((tSaleCombo.value==-1||tSaleCombo.value==0) 
				&& (nSaleCombo.value==-1||nSaleCombo.value==0)
				&& (formAvailCombo.value==-1||formAvailCombo.value==0)
				&& (storeCombo.value==-1||storeCombo.value==0)
				&& (hTypeCombo.value==-1||hTypeCombo.value==0)){
				//alert('please select all hardware related details');
		}
			/*else if(storeCombo.selectedIndex==-1||storeCombo.selectedIndex==0)
			{
				//alert('please select all hardware related details');
			}
			else if(formAvailCombo.selectedIndex==-1||formAvailCombo.selectedIndex==0)
			{
				//alert('please select all hardware related details');
			}
			else if(nSaleCombo.selectedIndex==-1||nSaleCombo.selectedIndex==0)
			{
				//alert('please select all hardware related details');
			}
			else if(tSaleCombo.selectedIndex==-1||tSaleCombo.selectedIndex==0)
			{
				//alert('please select all hardware related details');
			}*/
		else{				
			var storeid=storeCombo.value;
			var hTypeId=hTypeCombo.value;
			var formAvailId=formAvailCombo.value;
			var nSaleId=nSaleCombo.value;
			var tSaleId=tSaleCombo.value;
													
			jsonrpc = new JSONRpcClient(path + "/JSON-RPC");	
			taxrate=jsonrpc.processes.populateTaxRateForHardware(storeid,nSaleId,tSaleId,formAvailId,hTypeId,ponumber,fxChargeId);					
			document.getElementById("txtTaxRate"+var_counter).value=taxrate;	
		}
	}	
}

function fetchTaxRateForHW(path,fxChargeId){
	var ponumber=0;
	//Commented by Ravneet as ponumber is not used inside the hjavacode/proc , and it was giving error
	/*if(hdnChangeTypeId == 2 || hdnChangeTypeId == 5  || hdnChangeTypeId == 3 || hdnChangeTypeId == 1 || hdnChangeTypeId == 4){
		if(document.getElementById("hdnPoNoOfCharge"+var_counter).value != 0){
	 		ponumber=document.getElementById("hdnPoNoOfCharge"+var_counter).value;	
	 	}else{
	 		ponumber=document.getElementById("txtPODetailNo").value;
	 	}
	}else{
		ponumber=document.getElementById("txtPODetailNo").value;
	}*/
	var storeCombo=document.getElementById('txtStore');
	var hTypeCombo=document.getElementById('txtHtype');
	var formAvailCombo=document.getElementById('txtform');
	var nSaleCombo=document.getElementById('txtNSale');
	var tSaleCombo=document.getElementById('txtTSale');
	var storeid=storeCombo.value;
	var hTypeId=hTypeCombo.value;
	var formAvailId=formAvailCombo.value;
	var nSaleId=nSaleCombo.value;
	var tSaleId=tSaleCombo.value;
											
	jsonrpc = new JSONRpcClient(path + "/JSON-RPC");	
	taxrate=jsonrpc.processes.populateTaxRateForHardware(storeid,nSaleId,tSaleId,formAvailId,hTypeId,ponumber,fxChargeId);					
	return taxrate;
}

function fetchTaxRateForHWAS(path,fxChargeId){
	var ponumber=0;
	//Commented by Ravneet as ponumber is not used inside the hjavacode/proc , and it was giving error
	/*if(hdnChangeTypeId == 2 || hdnChangeTypeId == 5  || hdnChangeTypeId == 3 || hdnChangeTypeId == 1 || hdnChangeTypeId == 4){
		if(document.getElementById("hdnPoNoOfCharge"+var_counter).value != 0){
	 		ponumber=document.getElementById("hdnPoNoOfCharge"+var_counter).value;	
	 	}else{
	 		ponumber=document.getElementById("txtPODetailNo").value;
	 	}
	}else{
		ponumber=document.getElementById("txtPODetailNo").value;
	}*/
	var storeid;
	var hTypeId;
	var formAvailId;
	var nSaleId;
	var tSaleId;
	if(hardwareDetailsFetched){
		var storeCombo=document.getElementById('txtStore');
		var hTypeCombo=document.getElementById('txtHtype');
		var formAvailCombo=document.getElementById('txtform');
		var nSaleCombo=document.getElementById('txtNSale');
		var tSaleCombo=document.getElementById('txtTSale');
		storeid=storeCombo.value;
		hTypeId=hTypeCombo.value;
		formAvailId=formAvailCombo.value;
		nSaleId=nSaleCombo.value;
		tSaleId=tSaleCombo.value;
	}else{
		 var jsBillingData ={
					serviceProductID:serviceProductID,
					serviceId:servicesID
				};
		 hardwarelists = jsonrpc.processes.populateHardwareDetails(jsBillingData);
		 storeid=hardwarelists.list[0].selectedStoreID;;
		 hTypeId=hardwarelists.list[0].hardwareType;
		 formAvailId=hardwarelists.list[0].formAvailable;
		 nSaleId=hardwarelists.list[0].saleNature;
		 tSaleId=hardwarelists.list[0].saleType;
	}										
	jsonrpc = new JSONRpcClient(path + "/JSON-RPC");	
	taxrate=jsonrpc.processes.populateTaxRateForHardware(storeid,nSaleId,tSaleId,formAvailId,hTypeId,ponumber,fxChargeId);					
	return taxrate;
}

/*Function	:refreshTaxRate
*@Author	:Anil Kumar
*Date		:10-march-11
*Purpose	:To refresh TaxRate in charge section.
**/
function refreshTaxRate(path){
	var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');	
	if(chkChargeLine.length>0){
		var counter=parseInt(document.getElementById('chkSelectChargeDetail').value);
		var serviceType=document.getElementById("txtHdnServiceName").value.toUpperCase();
		//Puneet added for non hardware
		if(serviceType.indexOf("SERVICE")!=-1 || document.getElementById('hdnISFLEFLAG').value=='1'){
			for(i=0;i<chkChargeLine.length;i++){		
				fetchTaxRate(path,counter);
				counter++;			
			}
		}else{
				//[003] Start
				var taxRate =null;
				var taxRateFromMap="NO TAX";
				var tempFxChargeIDMap = {};
				for(var x in fxChargeIDConst)
					tempFxChargeIDMap[x]=fxChargeIDConst[x];
				for(var i=1;i<=chkChargeLine.length;i++){
					//var chargeNameCombo=document.getElementById("txtCName"+i);
					var fxChargeId = document.getElementById("hdnFxChargeID"+counter).value;
					if( fxChargeId in tempFxChargeIDMap){
						if(tempFxChargeIDMap[fxChargeId]==-1||tempFxChargeIDMap[fxChargeId]=="NO TAX"){
							taxRateFromMap=fetchTaxRateForHW(path,fxChargeId);
							tempFxChargeIDMap[fxChargeId]=taxRateFromMap;
							document.getElementById("txtTaxRate"+counter).value=taxRateFromMap;
						}else{
							taxRateFromMap=tempFxChargeIDMap[fxChargeId];
							document.getElementById("txtTaxRate"+counter).value=taxRateFromMap;
						}
					}else{
						if(null==taxRate){
							taxRate=fetchTaxRateForHW(path,fxChargeId);
							document.getElementById("txtTaxRate"+counter).value=taxRate;
						}
						else{
							document.getElementById("txtTaxRate"+counter).value=taxRate;
						}
					}
					counter++;
				}
				/*for(i=0;i<chkChargeLine.length;i++){
					document.getElementById("txtTaxRate"+counter).value=taxrate;
					counter++;			
				}*/
		}//[003] END
	}	
}

/*Function	:refreshTaxRate for auto suggesty
*@Author	:Puneet Chugh
*Date		:23-july-13
*Purpose	:To refresh TaxRate in charge section.
**/
function refreshTaxRateForAS(path){
	var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');	
	if(chkChargeLine.length>0){
		var counter=parseInt(document.getElementById('chkSelectChargeDetail').value);
		var serviceType=document.getElementById("txtHdnServiceName").value.toUpperCase();
		//Puneet added for non hardware
		if(serviceType.indexOf("SERVICE")!=-1 || document.getElementById('hdnISFLEFLAG').value=='1'){
			for(i=0;i<chkChargeLine.length;i++){		
				fetchTaxRateForAutoSuggest(path,counter);
				counter++;			
			}
		}/*else{
			var taxRate = fetchTaxRateForHWAS(path);
			for(i=0;i<chkChargeLine.length;i++){		
				document.getElementById("txtTaxRate"+counter).value=taxrate;
				counter++;			
			}
		}*/
		//[003]START
		else{
			var taxRate =null; 
			var taxRateFromMap="NO TAX";
			var tempFxChargeIDMap = {};
			for(var x in fxChargeIDConst)
				tempFxChargeIDMap[x]=fxChargeIDConst[x];
			for(var i=1;i<=chkChargeLine.length;i++){
				//var chargeNameCombo=document.getElementById("txtCName"+i);
				var fxChargeId = document.getElementById("hdnFxChargeID"+i).value;
				if( fxChargeId in tempFxChargeIDMap){
					if(tempFxChargeIDMap[fxChargeId]==-1||tempFxChargeIDMap[fxChargeId]=="NO TAX"){
						taxRateFromMap=fetchTaxRateForHWAS(path,fxChargeId);
						tempFxChargeIDMap[fxChargeId]=taxRateFromMap;
						document.getElementById("txtTaxRate"+counter).value=taxRateFromMap;
					}else{
						taxRateFromMap=tempFxChargeIDMap[fxChargeId];
						document.getElementById("txtTaxRate"+counter).value=taxRateFromMap;
					}
				}else{
					if(null==taxRate){
						taxRate=fetchTaxRateForHWAS(path,fxChargeId);
						document.getElementById("txtTaxRate"+counter).value=taxRate;
					}
					else{
						document.getElementById("txtTaxRate"+counter).value=taxRate;
					}
				}
				counter++;
			}
	}//[003] END
	}	
}

//Start[001]
function selectPoId(obj){
	if(obj.value==0){
		document.getElementById('txtPoId').value="";
	}else{
		document.getElementById('txtPoId').value=document.getElementById('txtPODetailNo').value;
	}
}  
//End[001]

function populateStore(path){
	    var combo1 = document.getElementById("txtStore");
    	licenseCo = Number(document.getElementById('txtLicenceCo').value);	
		jsonrpc = new JSONRpcClient(path + "/JSON-RPC");
		//var sessionid =path1;
        test1 = jsonrpc.processes.populateStoreList(licenseCo);   
      	
        removeAllOptions(document.getElementById("txtStore")); 
        if(test1!=null)
        {
	        for(j=0;j<test1.list.length;j++)
		    {
		    	
		    	var option = document.createElement("option");
		   		option.text = test1.list[j].storeName;
				option.value = test1.list[j].storeID;
				option.title = test1.list[j].storeName;
				try 
				{
					combo1.add(option, null); //Standard
				}
				catch(error) 
				{
					combo1.add(option); // IE only
				}
			
				if(test1.list.length == 1)
					{
						combo1.selectedIndex=1;
					}
			
			
		    }
	    }
	    else if(test1==null)
	    {
	    	//start[008]
	    	var callerWindowObj = dialogArguments;
			var myForm=callerWindowObj.document.getElementById('searchForm');				
			myForm.action=path +"/defaultAction.do?method=invalidsessoion";
			myForm.submit();
			alert("Session has been expired!!");		
			window.close();
			//end[008]	
	    }
}
function fnFetchLicCompany(path)
{
	var tr, td, i, j, oneRecord;
    var test;
 
 	var combo = document.getElementById("txtLicenceCo");
 	var serviceDetailID=Number(document.getElementById("serviceDetailID").value);
   
	   var iCountRows = combo.length;
	  
	   for (i = 1; i <  iCountRows; i++)
	   {
	       combo.remove(1);
	   }
   
    var jsData =			
    {
		entityID:document.getElementById("txtEntity").value,
		serviceDetailID:serviceDetailID
	};
	
	jsonrpc = new JSONRpcClient(path + "/JSON-RPC");

    test = jsonrpc.processes.populateLicCompany(jsData);
    removeAllOptions(document.getElementById("txtLicenceCo")); 
    
    for(j=0;j<test.list.length;j++)
    {    	
    	var option = document.createElement("option");
   		option.text = test.list[j].licCompanyName;
		option.value = test.list[j].licCompanyID;
		try 
		{
			combo.add(option, null); //Standard
		}
		catch(error) 
		{
			combo.add(option); // IE only
		}
    }
    //combo.value=0;
}
// [002]start
function CheckMaxLength(Object, MaxLen)      
{       
	if (Object.value.length > MaxLen) 
	{ 
	 alert('You cannot enter more than ' +MaxLen+ ' characters');
	 return false;    
	}           
} 
//Puneet to get the charge type
function getChargeType(chargeTypeSelectedVal){
	//Puneet for performance tuning, getting charge type
	srvDetailId=document.getElementById("hdnServiceDetailID").value;
	var chargeCombo;
	var chargeTypeOption="<option value='-1'>Select a Type</option>";
	if(document.getElementById('hdnHardwareInfo').value==1){
		var txtBillingformat = document.getElementById('txtBillingformat');
		if(txtBillingformat.value==2){
			chargeCombo = jsonrpc.processes.populateChargeTypeForHardware(srvDetailId,1);
		}else if(txtBillingformat.value==3){
			chargeCombo = jsonrpc.processes.populateChargeTypeForHardware(srvDetailId,2);
		}
	}else{
		chargeCombo = jsonrpc.processes.populateChargeType(srvDetailId);
	}
	if(null != chargeCombo){
		if(chargeCombo.list.length==0){
			alert('No Charge Type against this product!!');
		}else{
			for(z=0;z<chargeCombo.list.length;z++){
				chargeTypeOption = chargeTypeOption + "<option value='" + chargeCombo.list[z].chargeTypeID + "'>" + chargeCombo.list[z].chargeTypeName + "</option>";
			}
		}
	}
	return chargeTypeOption;
}
function setBillingLevelNo(billingLevel){
	if(billingLevel == 2) {
		document.getElementById('txtBillingLevelNo').value = document.getElementById('txtPODetailNo').value;
	}else if (billingLevel == 1){
		document.getElementById('txtBillingLevelNo').value = 0;
	}else if (billingLevel == 3){
		document.getElementById('txtBillingLevelNo').value = logicalSIno;
	}
}
// [002]end
function getRoleWiseSectionDetails(roleName){
	var test;
	jsonrpc = new JSONRpcClient(path + "/JSON-RPC");
    test = jsonrpc.processes.getRoleWiseSectionDetails(roleName);
    return test;
}
function getSectionDetail(roleWiseSectionDetail, sectionName){
	var sectionDetails=new Array();
	for(z=0;z<roleWiseSectionDetail.list.length;z++){
		if(roleWiseSectionDetail.list[z].section == sectionName){
			sectionDetails[0]=roleWiseSectionDetail.list[z].commercialAllowed;
			sectionDetails[1]=roleWiseSectionDetail.list[z].subSectionAttributeCheck;
			sectionDetails[2]=roleWiseSectionDetail.list[z].sectionCommercial;
			break;
		}
	}
	return sectionDetails;
} 
function preventSelectValueChange(e) {
	alert("You are not authorized to edit commercial attribute");
	e.stopPropagation();
	e.preventDefault();
	//selectedText = $(this).val();
}
var selectedText = null;
function disableAllChildNodes(parentElemId, lineType){
	//alert("in disableAllChildNodes");
	if(lineType == "EXISTING"){
	$("#" +parentElemId) .find( "input[type!='hidden'][id!='btnHwDetails'][id!='btnDispatchAddress'][id!='btnWarrentyDetails'][id!='btnServiceLocationDetails'], textarea" ).attr("readonly",true);	
	$("#" +parentElemId) .find("a").attr("disabled",true);
	$("#" +parentElemId) .find(":checkbox").attr("disabled",true);
	$("#" +parentElemId) .find(":button").attr("disabled",true);
	$("#" +parentElemId) .find( "input[type!='hidden'][id!='btnHwDetails'][id!='btnDispatchAddress'][id!='btnWarrentyDetails'][id!='btnServiceLocationDetails'], textarea" ).bind('keydown', 'down', function(e) {
		e.stopPropagation();
	});
	$("#" +parentElemId) .find("select").each(function(){
		$(this).bind("click dblclick", function(e){
			preventSelectValueChange(e);
		});
		$(this).bind('keydown', 'down', function(e){
			preventSelectValueChange(e);
		});
		/*$(this).bind("change select", function(e) {
			alert("You are not authorized to edit commercial attribute");
			$(this).val(selectedText);
			selectedText = null;
		});*/
	});
	//$("#" +parentElemId) .find( "input[type!='hidden'][id!='btnHwDetails'][id!='btnDispatchAddress'][id!='btnWarrentyDetails'][id!='btnServiceLocationDetails'], a[ctrltype='dropdownlink'], select, textarea" ).attr("disabled","disabled");
	//$("#" +parentElemId) .find( "input[type!='hidden'][id!='btnHwDetails'][id!='btnDispatchAddress'][id!='btnWarrentyDetails'][id!='btnServiceLocationDetails'], a[ctrltype='dropdownlink'], select, textarea" ).attr("readOnly, disabled" , true, true);	
	}else{
		$("#" +parentElemId) .find( "input[type!='hidden'][id!='btnHwDetails'][id!='btnDispatchAddress'][id!='btnWarrentyDetails'][id!='btnServiceLocationDetails'], a[ctrltype='dropdownlink'], select, textarea" ).attr("disabled", true);
	}
}
//Puneet to check the line item contains any commercial attributes
function checkCommercialLineAllowed(){
	var roleName = callerWindowObj.gb_roleID;
	if(null == roleWiseSectionDetail)
		roleWiseSectionDetail = getRoleWiseSectionDetails(roleName);
	//getting whether the commercial service summary section is allowed with given role
	var sectionDetail = getSectionDetail(roleWiseSectionDetail, "SERVICESUMMARY");
	var commercialAllowed = true;
	if(sectionDetail.length == 3){
		commercialAllowed = sectionDetail[0];
	}
	//check whether service summary section is allowed
	if(!commercialAllowed){
		//if(document.getElementById('hdnServiceSummary').value==1){
			//get whether service summary section contains any commercial attributes
			var serviceSummmaryCommercial=jsonrpc.processes.checkServiceSummaryAttributesCommercial(document.getElementById("serviceDetailID").value);
			if(serviceSummmaryCommercial){
				alert("You are not authorized to add Commercial Line");
				return false;
			}
		//}
	}
	return true;
}
function enableDisableCommercialFields(sectionDetails, roleWiseSectionDetail, lineType){
	var sectionDisabled = false;
	var sectionName = sectionDetails[0];
	var sectionParentId = sectionDetails[1];
	var sectionDetail = getSectionDetail(roleWiseSectionDetail, sectionName);
	var commercialAllowed = true;
	var sectionCommercial = false;
	var subSectionAttributeCheck = true;
	if(sectionDetail.length == 3){
		commercialAllowed = sectionDetail[0];
		subSectionAttributeCheck = sectionDetail[1];
		sectionCommercial = sectionDetail[2];
	}
	if(sectionCommercial && !commercialAllowed){
		if(subSectionAttributeCheck){
			disableAttribute(sectionParentId, lineType);
		}else{
			disableAllChildNodes(sectionParentId, lineType);
		}
		sectionDisabled = true;
	}
	return sectionDisabled;
}
function disableAttribute(sectionParentId, lineType){
	if(lineType == "EXISTING"){
	$("#" +sectionParentId).find("input[id^='comAttr'][value=1]").each(function(){
		//var idNo = (this.id.split("_")[1])-1;
		//$("#" + sectionParentId).find("table tr:eq(" + idNo + ")").find("[type!='hidden']").attr("disabled", true);
		$(this).parent().find( "[type!='hidden']").attr("readonly", true);
		$(this).parent().find("a").attr("disabled",true);	
		$(this).parent() .find(":checkbox").attr("disabled",true);
		$(this).parent() .find(":button").attr("disabled",true);
		$(this).parent().find( "[type!='hidden']").bind('keydown', 'down', function(e) {
			e.stopPropagation();
		});	
		$(this).parent().find("select").bind("click dblclick", function(e) {
			preventSelectValueChange(e);
			//			selectedText = $(this).val();
		});
		
		$(this).parent().find("select").bind('keydown', 'down', function(e){
			preventSelectValueChange(e);
		});
		/*$(this).parent().find("select").bind("change select", function(e) {
				alert("You are not authorized to edit commercial attribute");
				$(this).val(selectedText);
				selectedText = null;
			});*/
		});
	}else{
		$("#" +sectionParentId).find("input[id^='comAttr'][value=1]").each(function(){
		$(this).parent().find( "[type!='hidden']" ).attr("disabled", true);
	});}
}
function checkRoleWiseCommercialNotAllowed(roleWiseSectionDetail){
	var sectionDetail = getSectionDetail(roleWiseSectionDetail, serSummSection);
	if(!sectionDetail[0]){
		return true;
	}
	sectionDetail = getSectionDetail(roleWiseSectionDetail, billingSection);
	if(!sectionDetail[0]){
		return true;
	}
	sectionDetail = getSectionDetail(roleWiseSectionDetail, "CHARGE");
	if(!sectionDetail[0]){
		return true;
	}
	sectionDetail = getSectionDetail(roleWiseSectionDetail, hardwareSection);
	if(!sectionDetail[0]){
		return true;
	}
	sectionDetail = getSectionDetail(roleWiseSectionDetail, serviceLocSection);
	if(!sectionDetail[0]){
		return true;
	}
	sectionDetail = getSectionDetail(roleWiseSectionDetail, compSection);
	if(!sectionDetail[0]){
		return true;
	}
	return false;
}
function checkServiceTreeContainsCommercialLine(treeID){
	var commercialLinePresent = false;
	var orderType = document.getElementById('orderType').value;
	if(orderType == "New"){
	$("#" +treeID).find("input[type='checkbox']:checked").each(function(){
		//var idNo = (this.id.split("_")[1])-1;
		//$("#" + sectionParentId).find("table tr:eq(" + idNo + ")").find("[type!='hidden']").attr("disabled", true);
		if($(this).parent().find("input[type='radio']" ).attr("commercial") == 1){
			commercialLinePresent = true;
		}
	});
	}else{
		$("#" +treeID).find("input[type='checkbox']:checked").each(function(){
			//var idNo = (this.id.split("_")[1])-1;
			//$("#" + sectionParentId).find("table tr:eq(" + idNo + ")").find("[type!='hidden']").attr("disabled", true);
			if($(this).parent().find("input[type='radio']" ).attr("commercial") == 1){
				commercialLinePresent = true;
			}
		});
	}
	return commercialLinePresent;
}
//[005] start

function checkServiceTreeContainsCommercialLineForCopyProduct(treeID){
	var commercialLinePresent = false;
	var orderType = document.getElementById('orderType').value;
	if(orderType == "New"){
	$("#" +treeID).find("input[type='checkbox']:checked").each(function(){
		//var idNo = (this.id.split("_")[1])-1;
		//$("#" + sectionParentId).find("table tr:eq(" + idNo + ")").find("[type!='hidden']").attr("disabled", true);
		if($(this).parent().find("input[type='radio']" ).attr("commercial") == 1){
			commercialLinePresent = true;
		}
	});
	}else{
		$("#" +treeID).find("input[type='radio']:checked").each(function(){
			//var idNo = (this.id.split("_")[1])-1;
			//$("#" + sectionParentId).find("table tr:eq(" + idNo + ")").find("[type!='hidden']").attr("disabled", true);
			if($(this).parent().find("input[type='radio']" ).attr("commercial") == 1){
				commercialLinePresent = true;
			}
		});
	}
	return commercialLinePresent;
}

//[005] end


/*By Vijay
 * 
 * Old version of this method is validating only for checked line items. But new version of 
 * this method "checkServiceTreeContainsAnyCommercialLine()" is consider all lines items irrespective 
 * of lines are checked or not. 
 * 
 * New version should be use when we want to check commercial lines at service level, that means
 * service contain any commercial line or not. 
 * 
 * Old version should be use when we want to check commercial lines at line level, that means 
 * we need to validate commercial line only for selected lines.
 */
function checkServiceTreeContainsAnyCommercialLine(treeID){
	var commercialLinePresent = false;
	var orderType = document.getElementById('orderType').value;
	if(orderType == "New"){
	$("#" +treeID).find("input[type='radio']").each(function(){
		if($(this).parent().find("input[type='radio']" ).attr("commercial") == 1){
			commercialLinePresent = true;
		}
	});
	}else{
		$("#" +treeID).find("input[type='radio']").each(function(){
			if($(this).parent().find("input[type='radio']" ).attr("commercial") == 1){
				commercialLinePresent = true;
			}
		});
	}
	return commercialLinePresent;
}

//added by manisha o2c start
function setDefaultValueInProductCatalog(sectionParentId, callType, roleId, sourceType, param, isHardware)
{
	$("#" +sectionParentId).find("textarea").each(function(){
		$(this).val("NA"); 
	});
	$("#" +sectionParentId).find("select").each(function(){
		if($(this).find("option").length>1){
			$(this).attr('selectedIndex', 1); 
			if( $(this).attr("id") == "txtOSPTagging" ){
				$("#txtOSPTagging").attr('selectedIndex',0);// OSP default value while adding lines by PM,SED by  nagarjuna
			}
		}
	});
	$("#" +sectionParentId).find(":text[ctrltype='dropdown']").each(function(){
		if(!(isHardware == 1 && ($(this).attr("id") == "txtBCPAddressSuggestService"))){
		var resVal = getAutoCompleteValue($(this).attr("retval"), sourceType, param, callType, roleId);
		var glossaryVal = $.parseJSON(resVal).glossary;
		var label;
		var value;
		if(glossaryVal.length>0){
			if(glossaryVal.length == 1){
				if(glossaryVal[0].value != 0 && glossaryVal[0].value != -1){
					label = glossaryVal[0].label;
					value = glossaryVal[0].value;
				}
			}else{
				if(glossaryVal[0].value == 0 || glossaryVal[0].value == -1){
					label = glossaryVal[1].label;
					value = glossaryVal[1].value;
				}else{
					label = glossaryVal[0].label;
					value = glossaryVal[0].value;
				}
			}
		}
		setASValueOnSelect($(this), value, label);
		}
	});
}// added by manisha o2c end
function getAutoCompleteValue(attributefor, sourceType, param, callType, roleId){
	var resVal = jsonrpc.processes.getAutoSuggestServiceAttribute(
		"", attributefor, sourceType, param, callType, roleId);
	return resVal;
}
//added by kalpana to reset child LOV
function resetChildAttr(thisEl){
	var counter=thisEl.attr("counterval");
	var childCounter=parseInt(counter)+1;
	var childChgOrdrVal=$("#newprodAttVal_"+childCounter).val();
	if(childChgOrdrVal!=undefined){
		$("#newprodAttVal_"+childCounter).val("0");
		$("#autoSuggNewProdAttVal_"+childCounter).val();
	}else{
		$("#prodAttVal_"+childCounter).val("0");
		$("#autoSuggProdAttVal_"+childCounter).val();
	}
}
//end
//added by kalpana to get value of parent LOV or DD
function getParentVal(thisEl){
	var param;
	var parentCounter=	thisEl.attr("parentCounter");
	if(thisEl.attr("parntAttId")!=undefined && parentCounter!=0){
   		param=$("#prodAttVal_"+parentCounter).val();
   	}else{
   		param=0;
   	}
   return param;
}

//end
//[004] Start VIPIN
function doValidateDCHWChargeAndUpdateFlag(){
	var poNumber=document.getElementById('poNumber').value;
	result = jsonrpc.processes.validateDCHWChargeAndUpdateFlag(poNumber,gb_roleID);
		return result;
}
//[004] End VIPIN
