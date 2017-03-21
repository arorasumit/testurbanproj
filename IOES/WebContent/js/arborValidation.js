//*************************************************************************************************************************************
	/*Created By: Saurabh Singh
	  Date:		  03-Jul-2012
	  Purpose:	  For Arbor Validations in New and Change Order.
	*/
//**************************************************************************************************************************************
/* Tag Name Resource Name  Date		     CSR No			       Description
 * [001]    Gunjan Singla 29-Apr-2015   20150202-R2-021037    Suppression of creation of new billable account in case of change order
 * */

/*Function billingChangeForChargeAndCompInfo for showing ComponentInfo 
on selecting 95Percentile service Flavor and chargeInfo on other flavor */
function loadUpdateConfigIfApplicable(attMstrId,fieldName,fieldValue,page, ctrlType, compLength, chargeLength){
	var SPID=0;
	var toComputeAlert=0;
	var SDID=0;
	if(document.getElementById('serviceDetailID')!=null)
		SDID=document.getElementById('serviceDetailID').value;
	//--------To find config value based on servicedetailid and set config value on product catelog page----------
	if(page == 'VPC' || page == 'CVPC'){
		SPID=serviceProductID;
		SDID=serviceDetailID;
	}
	if(page=='CVPC' && changeSubTypeID !=0){
		toComputeAlert=1;
	}
	var jsonrpc = new JSONRpcClient(pathM6 + "/JSON-RPC");
	var jsConfigData = {
		serviceDetailID:SDID,
		serviceId:servicesID,
		m6AttributeId:attMstrId,
		m6AttributeValue:fieldValue,
		serviceProductID:SPID,
		toComputeAlert:toComputeAlert,
		oldConfigId:document.getElementById('hdnConfigValue').value
	};
	var configValueList=jsonrpc.processes.fetchConfigValue(jsConfigData);

	if(page=='CVPC' && changeSubTypeID !=0){
		if(document.getElementById('hdnConfigValue').value != configValueList.configValue && document.getElementById('hdnConfigValue').value != 0){
			alert(guiAlertForConfig);
			return false;
		}else if(document.getElementById('hdnConfigValue').value != configValueList.configValue && document.getElementById('hdnConfigValue').value == 0){
			alert(configValueList.newConfigAlert);
			return false;
		}else if(document.getElementById('hdnConfigValue').value == configValueList.configValue && document.getElementById('hdnConfigValue').value != 0){
			alert(guiAlertForConfig);
			return false;
		}
	}else{
		if(document.getElementById('hdnConfigValue').value != configValueList.configValue || (document.getElementById('hdnConfigValue').value == configValueList.configValue && configValueList.configValue != 0)){
			var totalLineItemAttached=configValueList.totalLineItemAttached;
			var maxLineItemAllow=configValueList.maxLineItemAllow;
			var isUsageValue=configValueList.isUsageAllow;
			var compInfo=configValueList.compInfo;
			var chargeInfo=configValueList.chargeInfo;
			fxChargeRedirectionType=configValueList.fxChargeRedirectionType;
			fxChargeRedirectionTypeCumulative=configValueList.fxChargeRedirectionTypeCumulative;
			if(((maxLineItemAllow !=null) && (maxLineItemAllow !=0)) && (totalLineItemAttached >= maxLineItemAllow) ){
				alert("This option cannot be selected more than "+maxLineItemAllow+" time(s) in Service No "+ servicesID);
				//document.getElementById('saveImage').style.display='none';
				//document.getElementById("serviceDetailID").value=0;
				return false;
			}
			if(compInfo==0 && document.getElementById('hdnComponentInfo').value==1){
				//var chkCompLine=document.getElementsByName('chkComponents');
				//if(chkCompLine.length>0){
				if(compLength>0){
					alert('Please delete all components line first');								
					return false;
				}
			}
			if(chargeInfo==0 && document.getElementById('hdnChargeInfo').value==1){
				//var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
				//if(chkChargeLine.length>0){
				if(chargeLength>0){
					alert('Please delete all charges line first');								
					return false;
				}
			}
			document.getElementById('hdnComponentInfo').value=compInfo;
			document.getElementById('hdnChargeInfo').value=chargeInfo;
			if(compInfo==0){
				document.getElementById('Components').style.display='none';
			}else
			{
				document.getElementById('Components').style.display='block';
			}
	
			if(chargeInfo==0)
			{
				document.getElementById('ChargeDetails').style.display='none';
			}else
			{
				document.getElementById('ChargeDetails').style.display='block';
			}
	
			document.getElementById('hdnConfigValue').value=configValueList.configValue;	
			if(document.getElementById('hdnBillingInfo').value==1 ){
				if(ctrlType == 'AS'){
					resetBillingLevel();
					resetBillingFormat();
					resetBillingMode();
				}else{
					loadBillingLevel();
					loadBillingFormat();
					loadBillingMode();
				}
				loadUsageCheck(isUsageValue, ctrlType);
				enableDisableRedirectionLSIIfPresent(fxChargeRedirectionType,page, ctrlType);
			}
		}	
	}
	return true;
}

function loadBillingLevel()
{
	var billingLevelList;
	var combo = document.getElementById("txtBillingLvl");
	var iCountRows = combo.length;
	  
	for (var i = 1; i <  iCountRows; i++)
	{
		combo.remove(1);
	}
	
	jsonrpc = new JSONRpcClient(pathM6 + "/JSON-RPC");
	billingLevelList = jsonrpc.processes.populateBillingLevelDetails(document.getElementById("hdnConfigValue").value,serviceProductID);
	for(var j=0;j<billingLevelList.list.length;j++)
	{
	   	var option = document.createElement("option");
	  	option.text = billingLevelList.list[j].billingLevelName;
		option.value = billingLevelList.list[j].billingLevelId;
		option.title = billingLevelList.list[j].billingLevelName;
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

function loadBillingFormat(){
	var billingFormatList;
	var combo = document.getElementById("txtBillingformat");
	var iCountRows = combo.length;
	for (var i = 1; i <  iCountRows; i++){
		combo.remove(1);
	}
	jsonrpc = new JSONRpcClient(pathM6 + "/JSON-RPC");		
    billingFormatList = jsonrpc.processes.populateBillingFormatDetails(document.getElementById("txtHdnServiceName").value,document.getElementById("hdnConfigValue").value);
    for(var j=0;j<billingFormatList.list.length;j++){
	   	var option = document.createElement("option");
	  	option.text = billingFormatList.list[j].billingFormatName;
		option.value = billingFormatList.list[j].billingFormatId;
	  	option.title = billingFormatList.list[j].billingFormatName;
		try{
			combo.add(option, null); //Standard
		}catch(error){
			combo.add(option); // IE only
		}
    }
}

function loadBillingMode()
{
	var billingModeList;
 	var combo=document.getElementById('txtBillingMode');
 	var iCountRows = combo.length;
 	for(var i = 1; i <  iCountRows; i++)
 	{
 		combo.remove(1);
 	}
 	jsonrpc = new JSONRpcClient(pathM6 + "/JSON-RPC");	
 	billingModeList=jsonrpc.processes.fetchBillingMode(document.getElementById("hdnConfigValue").value);
 	for(var j=0;j<billingModeList.list.length;j++)
 	{	 	
	 	var option=document.createElement("option");
	 	option.text=billingModeList.list[j].billingModeName;
	 	option.value=billingModeList.list[j].billingModeId;
	 	option.title=billingModeList.list[j].billingModeName;
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

function loadUsageCheck(isUsageValue, ctrlType){
	if(isUsageValue !=null && isUsageValue =="USAGE"){			
		document.getElementById('chkIsUsage').checked = true;
		document.getElementById("chkIsUsage").value=1;			
	}else{
		document.getElementById('chkIsUsage').checked = false;
		document.getElementById("chkIsUsage").value=0;	
	}
	if(ctrlType == 'AS')
		isUsageCheckValueChanged = true;
}

function enableDisableRedirectionLSIIfPresent(fxChargeRedirectionType,page,IsDbSavedValueLoading, ctrlType){
	if(fxChargeRedirectionType==null || fxChargeRedirectionType==""){
		document.getElementById("popUpLsiLink").disabled=true;
		//if saved data loaded then saved data should be display on page else its display blank
		if(IsDbSavedValueLoading==1){
			//document.getElementById("txtFxRedirectionLSI").value="";
			//document.getElementById("hdnFxRedirectionSPID").value=0;
			//document.getElementById("txtBillingScenario").value=0;
		}else{
			if(ctrlType == 'AS')
				chargeRedValueChanged = true;
			document.getElementById("txtFxRedirectionLSI").value="";
			document.getElementById("hdnFxRedirectionSPID").value=0;
			document.getElementById("txtBillingScenario").value=0;
		}
	}else{
		if(!(page=='CVPC' && editSolutionChangeOldProduct==1))
		document.getElementById("popUpLsiLink").disabled=false;
	}
	
}
//[001] start
function setDefaultBillingLvlForChannel(){
	
	var callerWindowObj = dialogArguments;
	var cust_seg=callerWindowObj.document.getElementById('lob').value;

	if(cust_seg=='Channel' || cust_seg=='AES Channel'){
		
		var billingLvlOb=document.getElementById("txtBillingLvl");
		for(var i=0;i<billingLvlOb.length;i++){
			
			if(billingLvlOb[i].value==3)
			{
				billingLvlOb.value=3;
				break;
			}
		
		}
	}
	
}
////[001] end