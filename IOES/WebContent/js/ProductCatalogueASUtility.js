//[001] VIPIN SAHARIA 12/06/2014 Removed hardcoded Check for Hardware Service while Cust PO Selection and added isOldLineItem_RateRenewal check.
function getDropDown(attrid){
	getAutoSuggest($("#autoSuggProdAttVal_"+attrid));
	$("#autoSuggProdAttVal_"+attrid).autocomplete( "search", "" );
	$("#autoSuggProdAttVal_"+attrid).focus();
}
function getDropDownReason(){
	getAutoSuggest($("#txtReason"));
	$("#txtReason").autocomplete( "search", "" );
	$("#txtReason").focus();
}
function getLicenseCo(){
	getAutoSuggest($("#licenseCo"));
	$("#licenseCo").autocomplete( "search", "" );
	$("#licenseCo").focus();
}
function getSaleNature(){
	getAutoSuggest($("#textSaleNature"));
	$("#textSaleNature").autocomplete( "search", "" );
	$("#textSaleNature").focus();
}
function getDropDownChargeName(attrid){
	getAutoSuggest($("#chargeName"+attrid));
	$("#chargeName"+attrid).autocomplete( "search", "" );
	$("#chargeName"+attrid).focus();
}
function getDropDownBillingFormat(){
	getAutoSuggest($("#billingFormat"));
	$("#billingFormat").autocomplete( "search", "" );
	$("#billingFormat").focus();
}
function getFormType(){
	getAutoSuggest($("#textFormTXT"));
	$("#textFormTXT").autocomplete( "search", "" );
	$("#textFormTXT").focus();
}
//Auto suggest BCP Start lawkush
function getDropDownBilling(){
	getAutoSuggest($("#txtBCPAddressSuggest"));
	$("#txtBCPAddressSuggest").autocomplete( "search", "" );
	$("#txtBCPAddressSuggest").focus();
}
function getDropDownBillTaxation(){
	getAutoSuggest($("#taxationID"));
	$("#taxationID").autocomplete( "search", "" );
	$("#taxationID").focus();
}
function getDropDownBillingType(){
	getAutoSuggest($("#billingType"));
	$("#billingType").autocomplete( "search", "" );
	$("#billingType").focus();
}
function getDropDownLegalEntity(){
	getAutoSuggest($("#legalEntityTXT"));
	$("#legalEntityTXT").autocomplete("search", "" );
	$("#legalEntityTXT").focus();
}
function getSaleType(){
	getAutoSuggest($("#textSaleType"));
	$("#textSaleType").autocomplete("search", "" );
	$("#textSaleType").focus();
}
function getHardwareType(){
	getAutoSuggest($("#hTypeText"));
	$("#hTypeText").autocomplete("search", "" );
	$("#hTypeText").focus();
}
function getPODetailNo(){
	getPrevCustPoNo();
	getAutoSuggest($("#poDetailNo"));
	$("#poDetailNo").autocomplete("search", "" );
	$("#poDetailNo").focus();
}
function getStore(){
	getAutoSuggest($("#txtStoreText"));
	$("#txtStoreText").autocomplete("search", "" );
	$("#txtStoreText").focus();
}
function getDropDownDispatch(){
	getAutoSuggest($("#txtDispatchAddressSuggest"));
	$("#txtDispatchAddressSuggest").autocomplete( "search", "" );
	$("#txtDispatchAddressSuggest").focus();
}
//Auto suggest BCP End lawkush
//Auto suggest Cust Location Start lawkush
function getDropDownPriCustLocation(){
	getAutoSuggest($("#txtPriCustLocationSuggest"));
	$("#txtPriCustLocationSuggest").autocomplete( "search", "" );
	$("#txtPriCustLocationSuggest").focus();
}
function getDropDownBillingforService(){
	getAutoSuggest($("#txtBCPAddressSuggestService"));
	$("#txtBCPAddressSuggestService").autocomplete( "search", "" );
	$("#txtBCPAddressSuggestService").focus();

}
function getDropDownSecCustLocation(){
	getAutoSuggest($("#txtSecCustLocationSuggest"));
	$("#txtSecCustLocationSuggest").autocomplete( "search", "" );
	$("#txtSecCustLocationSuggest").focus();
}
function getBillingCP(){
	getAutoSuggest($("#billingCP"));
	$("#billingCP").autocomplete( "search", "" );
	$("#billingCP").focus();
}
function getDropDownBillingMode(){
	getAutoSuggest($("#billingMode"));
	$("#billingMode").autocomplete( "search", "" );
	$("#billingMode").focus();
}
function getDropDownBillingLevel(){
	getAutoSuggest($("#billingLevel"));
	$("#billingLevel").autocomplete( "search", "" );
	$("#billingLevel").focus();
}
//Added by Deepak for ItemCode
function getDropDownItemCode(attrid){
	getAutoSuggest($("#itemName"+attrid));
	$("#itemName"+attrid).autocomplete( "search", "" );
	$("#itemName"+attrid).focus();
}
function getDropDownDelLocation(attrid){
	getAutoSuggest($("#delLocation"+attrid));
	$("#delLocation"+attrid).autocomplete( "search", "" );
	$("#delLocation"+attrid).focus();
}
function getDropDownSubInventory(attrid){
	getAutoSuggest($("#subInvent"+attrid));
	$("#subInvent"+attrid).autocomplete( "search", "" );
	$("#subInvent"+attrid).focus();
}
function getDropDownBudgetHead(attrid){
	getAutoSuggest($("#budgHead"+attrid));
	$("#budgHead"+attrid).autocomplete( "search", "" );
	$("#budgHead"+attrid).focus();
}
//end Deepak
function setASValuesForNoRecord(thisEl){
	if(thisEl.attr("retval")=="AUTOSUGGESTITEMCODE"){
		$(thisEl).val("");
	   	 $("#txtItemCode"+thisEl.attr("countervalItem")).val("0");
	    				return;
	}
	if(thisEl.attr("retval")=="AUTOSUGGESTDELLOCATION"){
		$(thisEl).val("");
	   	 $("#txtDelLocId"+thisEl.attr("countervalDel")).val("0");
	    				return;
	}
	if(thisEl.attr("retval")=="AUTOSUGGESTSUBINVENTORY"){
		$(thisEl).val("");
	   	 $("#txtSubInvent"+thisEl.attr("countervalSub")).val("0");
	    				return;
	}
	if(thisEl.attr("retval")=="AUTOSUGGESTBUDGHEAD"){
		$(thisEl).val("");
	   	 $("#txtBudgetHead"+thisEl.attr("countervalBud")).val("0");
	    				return;
	}
 	if(thisEl.attr("retval")=="AUTOSUGGESTBCP"){
 		$(thisEl).val("");	
		$("#bbPrimaryBCPID").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBCPSERVICE"){
 		$(thisEl).val("");	
		$("#bbPrimaryBCPIDService").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTDISPATCH"){
 		$(thisEl).val("");	
		$("#txtdispatchAddress").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTPRICUSTLOC"){
 		$(thisEl).val("");	
		$("#ddPrimaryBCP").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTSECCUSTLOC"){
 		$(thisEl).val("");	
		$("#ddSecondaryBCP").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLTAXATION"){
 		$(thisEl).val("");	
		$("#txtTaxation").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTLEGALENTITY"){
 		$(thisEl).val("");	
		$("#txtEntity").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTCUSTPODETAIL"){
 		$(thisEl).val("");	
		$("#txtPODetailNo").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTCP"){
 		$(thisEl).val("");	
		$("#txtBillingCP").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTREASON"){
 		$(thisEl).val("");	
		$("#changeReason").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTHTYPE"){
 		$(thisEl).val("");	
		$("#txtHtype").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTTEXTFORM"){
 		$(thisEl).val("");	
		$("#txtform").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTSALETYPE"){
 		$(thisEl).val("");	
		$("#txtTSale").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTSTORE"){
 		$(thisEl).val("");	
		$("#txtStore").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTSALENATURE"){
 		$(thisEl).val("");	
		$("#txtNSale").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTLICENSECO"){
 		$(thisEl).val("");	
		$("#txtLicenceCo").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTCHARGENAME"){
 		$(thisEl).val("");	
		$("#txtCName" + thisEl.attr("counterval")).val("0");
		$("#txtCAnnotation" + thisEl.attr("counterval")).val("");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGFORMAT"){
 		$(thisEl).val("");	
		$("#txtBillingformat").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGMODE"){
 		$(thisEl).val("");	
		$("#txtBillingMode").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGTYPE"){
 		$(thisEl).val("");	
		$("#txtBillingType").val("0");
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGLEVEL"){
 		$(thisEl).val("");	
		$("#txtBillingLvl").val("0");
 	}else{
 		$(thisEl).val("");	
		$("#prodAttVal_"+thisEl.attr("counterval")).val("0");
		//to reset child LOV by Kalpana
		if(thisEl.attr("isParentAtt")==1){
        	resetChildAttr(thisEl);
        }
		//end
	}
}

//Start [TRNG22032013018]

var serviceListArray=new Array();
function treeViewPagingMethod(methodName){
	var serviceId=0;
	var enteredServiceIndex=0;
	if (serviceListArray.length==0){//ServiceListArray will be loaded only once when user click on navigation links
	    var serviceList = jsonrpc.processes.poulateServiceListForArrayLoading(Number(callerWindowObj.document.getElementById('poNumber').value));
	    for (var i = 0 ; i <serviceList.list.length; i++){
	    	serviceListArray[i]=serviceList.list[i].serviceId;
	    }
	}
	if(methodName=='GoToPage'){
		if(trim(document.getElementById('serviceId_goToPage').value)==""){
			alert('Please enter any digit!!');
			document.getElementById('serviceId_goToPage').focus;
			return false;
		}
		serviceId = Number(document.getElementById('serviceId_goToPage').value);
		enteredServiceIndex=serviceListArray.indexOf(serviceId)
		if(enteredServiceIndex==-1){
			alert('This service is not present in current Order No!!!\n Please enter service of this order No.');
			return false;
		}
		getServiceDetailsForSwitchingService(serviceId);
	}else if(methodName=='Next'){
		document.getElementById('serviceId_goToPage').value="";
		serviceId = Number(document.getElementById('id_span_ServiceNo').innerHTML);
		enteredServiceIndex=serviceListArray.indexOf(serviceId)
		var maxIndex=serviceListArray.length;
		maxIndex = maxIndex-1;
		if(enteredServiceIndex==maxIndex){
			alert('There is no next service!!');
			return false;
		}
		enteredServiceIndex=enteredServiceIndex+1;
		serviceId = serviceListArray[enteredServiceIndex];
		getServiceDetailsForSwitchingService(serviceId);
	}else if(methodName=='Prev'){
		document.getElementById('serviceId_goToPage').value="";
		serviceId =Number(document.getElementById('id_span_ServiceNo').innerHTML);
		enteredServiceIndex=serviceListArray.indexOf(serviceId)
		if(enteredServiceIndex==0){
			alert('There is no previous service!!');
			return false;
		}
		enteredServiceIndex=enteredServiceIndex-1;
		serviceId = serviceListArray[enteredServiceIndex];
		getServiceDetailsForSwitchingService(serviceId);
	}
}

function getServiceDetailsForSwitchingService(serviceId){
	var dataforswitching = jsonrpc.processes.getServiceDetailsForSwitchingService(serviceId);
	if(dataforswitching.list.length==1){
		document.getElementById('id_span_ServiceNo').innerHTML = serviceId;
		document.getElementById('id_span_ServiceName').innerHTML = dataforswitching.list[0].serviceTypeName;
		document.getElementById('lblServiceProductId').innerHTML = "LineItem No:"+dataforswitching.list[0].serviceProductID;
		callerWindowObj.document.getElementById('hdnserviceid').value = serviceId;
		serviceid=dataforswitching.list[0].serviceTypeId;
		serviceNo = serviceId;
		serviceName = dataforswitching.list[0].serviceName;
		productName = dataforswitching.list[0].serviceDetDescription;
		serviceDetailID = dataforswitching.list[0].serviceDetailID;
		serviceProductID = dataforswitching.list[0].serviceProductID;
		document.getElementById('hdnServiceProductID').value = serviceProductID
		callerWindowObj.document.forms[0].SPIDUrlforVPC.value = dataforswitching.list[0].link;
		fncServiceTreeView();
		getServiceAttributes();
		//closeWaitingDialog();
	}
}

function attachCSForAS(item){
	var thisEl = item;
	thisEl.attr("tabIndex", -1)
		.attr( "title", "Show All Items" )
		.button({
			icons: {
				primary: "ui-icon-triangle-1-s"
			},
			text: false
		})
		.removeClass( "ui-corner-all" )
		.addClass( "ui-corner-right ui-button-icon" );
}
function callOnAddedCharge(counter, chargeSumCounter){
	$("#txtCTAmt" + counter).bind("paste cut keyup", function(e) {
		var thisCtrl = $(this);
		if(this.value.length > 0){
			if(checknumber_charges_section(thisCtrl)){
				thisCtrl.attr("oldvalue", thisCtrl.val());
				calculateFreqAmount(counter);
				calculateTotalLineAmount(thisCtrl.val(),chargeSumCounter);
			}else{
				thisCtrl.val(thisCtrl.attr("oldvalue"));
				calculateFreqAmount(counter);
				calculateTotalLineAmount(thisCtrl.val(),chargeSumCounter);
				//return false;
			}
		}else{
			thisCtrl.attr("oldvalue", thisCtrl.val());
			calculateTotalLineAmount(thisCtrl.val(),chargeSumCounter);
			//return false;
		}
		updateHiddenChargeVal(thisCtrl);
	}); 
}
function callOnChargeAmountChange(){
	$(":text[name='txtCTAmt']").bind("paste cut keyup", function(e) {
			var thisCtrl = $(this);
			attachEventChargeAmountChange(thisCtrl);
			updateHiddenChargeVal(thisCtrl);
		});
}
function updateHiddenChargeVal(thisCtrl){
	var index = thisCtrl.attr("chargeSumCounterVal");
	var totAm=document.getElementById('txtCTAmt'+index).value;
	if(totAm!=null && totAm!=''){
		document.getElementById('hdnCTAmt'+index).value=totAm;
	}
}
function callOnChargeAmountChangeCtrl(thisCtrl){
	thisCtrl.bind("paste cut keyup", function(e) {
		attachEventChargeAmountChange(thisCtrl);
		updateHiddenChargeVal(thisCtrl);
	});
}
function attachEventChargeAmountChange(thisCtrl){
	if(thisCtrl.val().length > 0){
		if(checknumber_charges_section(thisCtrl)){
			thisCtrl.attr("oldvalue", thisCtrl.val());
			calculateFreqAmount(thisCtrl.attr("counterVal"));
			calculateTotalLineAmount(thisCtrl.val(),thisCtrl.attr("chargeSumCounterVal"));
		}else{
			thisCtrl.val(thisCtrl.attr("oldvalue"));
			calculateFreqAmount(thisCtrl.attr("counterVal"));
			calculateTotalLineAmount(thisCtrl.val(),thisCtrl.attr("chargeSumCounterVal"));
			return false;
		}
	}else{
		thisCtrl.attr("oldvalue", thisCtrl.val());
		return false;
	}
}
function setBillingMode(){
	var billFormat=document.getElementById("txtBillingformat").value;
	
	if(billFormat == 3){
	  	//Puneet Commenting for auto suggest
		document.getElementById("txtBillingMode").value=1;
		document.getElementById("billingMode").value="AUTOMATIC";//Automatic
	}else if(billFormat == 1 || billFormat == 2){
		document.getElementById("txtBillingMode").value=2; //Manual
		document.getElementById("billingMode").value="MANUAL";
  	}else{
   		document.getElementById("txtBillingMode").value=0; //Select
   		document.getElementById("billingMode").value="Select";
  	}
}
function getParam(thisEl){
	var param;
	if(thisEl.attr("retval")=="AUTOSUGGESTCUSTPODETAIL"){
			if(productCatalogueType=="CHANGE"){
				//Added by Deepak Kumar hardware line additional node condition with value as change1
				//[001] removed Hardware ServiceTypeId check and added isOldLineItem_RateRenewal check
				if(null != editSolutionChangeOldProduct && undefined != editSolutionChangeOldProduct && editSolutionChangeOldProduct == 1||(additionalNodeFlagCheck()==1)){
					param = callerWindowObj.document.getElementById("poNumber").value+"_"+document.getElementById("hdnServiceProductID").value+"_"+document.getElementById("txtEntity").value;
				}else if(null != isOldLineItem_RateRenewal && undefined != isOldLineItem_RateRenewal && isOldLineItem_RateRenewal == 1){
					param = callerWindowObj.document.getElementById("poNumber").value+"_"+document.getElementById("hdnServiceProductID").value+"_"+document.getElementById("txtEntity").value;
				}else{
					param = callerWindowObj.document.getElementById("poNumber").value+"_"+document.getElementById("hdnserviceDetailId").value;
					
				}	
			}else{
				param = callerWindowObj.document.getElementById("poNumber").value+"_"+document.getElementById("hdnserviceDetailId").value;
			}
		}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGFORMAT"){
			param = document.getElementById("txtHdnServiceName").value+"_"+document.getElementById("hdnConfigValue").value;
			getPrevBillFormat();
		}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGMODE"){
			param = document.getElementById("hdnConfigValue").value;
		}else if(thisEl.attr("retval")=="AUTOSUGGESTCHARGENAME"){
			param = $("#ddCType"+thisEl.attr("counterval")).val() + "_" +$("#hdnServiceDetailID").val() + "_"+$("#txtEntity").val();
		}else if(thisEl.attr("retval")=="AUTOSUGGESTREASON"){
			param = 1;
		}else if(thisEl.attr("retval")=="AUTOSUGGESTLICENSECO"){
			var poNo = $("#txtPODetailNo").val();
			if(productCatalogueType=="CHANGE" && poNo==0 && (((hdnChangeTypeId==5 && changeSubTypeID==5) || (hdnChangeTypeId==4 && changeSubTypeID==12) )
					||(1==additionalNodeFlagCheck() && $("#hdnHardwareInfo").val()==1))){
				param = serviceProductID;
			}else{
				param = $("#txtEntity").val() + "_" + serviceDetailID;
			}	
		}else if(thisEl.attr("retval")=="AUTOSUGGESTSALETYPE"){
			if(billingDetailsFetched){
				param = document.getElementById("txtBillingformat").value;
			}else{
				if(billinglists == null){
					var jsBillingData = {
						serviceId:servicesID,
						serviceProductID:serviceProductID
					};
					billinglists = jsonrpc.processes.populateBillingDetails(jsBillingData);
				}
				if(null != billinglists && billinglists.list.length>0){
					param = billinglists.list[0].billingformat;
				}else{
					param = 0;
				} 
			}
		}else if(thisEl.attr("retval")=="AUTOSUGGESTLEGALENTITY"){
			var poNo = $("#txtPODetailNo").val();
			
			if(productCatalogueType=="CHANGE" && poNo==0 && (((hdnChangeTypeId==5 && changeSubTypeID==5) || (hdnChangeTypeId==4 && changeSubTypeID==12) )
					||(1==additionalNodeFlagCheck() && $("#hdnHardwareInfo").val()==1))){
				param = serviceProductID;
				
			}else{
				param = $("#txtPODetailNo").val();
				
			}
		}else if(thisEl.attr("retval")=="AUTOSUGGESTSTORE"){
			if(billingDetailsFetched){
				param = $("#txtLicenceCo").val();
			}else{
				if(billinglists == null){
					var jsBillingData = {
						serviceId:servicesID,
						serviceProductID:serviceProductID
					};
					billinglists = jsonrpc.processes.populateBillingDetails(jsBillingData);
				}
				if(null != billinglists && billinglists.list.length>0){
					param = billinglists.list[0].licCompanyID;
				}else{
					param = 0;
				} 
			}
		}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGLEVEL"){
			param = $("#hdnConfigValue").val()+ "_" + serviceProductID;
		}
		else if(thisEl.attr("retval")=="AUTOSUGGESTDELLOCATION")
		{
			if((null==selectedCircle)||(""==selectedCircle))
			{
				if(selectedCircleInView==undefined)
				{
					alert("Please First Select  CIRCLE (Operating Unit) in PR Attributes");
					return;
				}
				param=selectedCircleInView;	
			}
			else
			{
			param =selectedCircle;
			}
		}
		else if(thisEl.attr("retval")=="AUTOSUGGESTSUBINVENTORY")
		{
			if((null==selectedCircle)||(""==selectedCircle))
			{
				if(selectedCircleInView==undefined)
				{
					alert("Please First Select CIRCLE (Operating Unit) in PR Attributes");
					return;
				}
				param=selectedCircleInView;	
			}
			else
			{
			param =selectedCircle;
			}
		}
	
		else{
			/*param = $("#txtBillingAC").val();
			if(null == param || "null" == param)*/
				param = callerWindowObj.document.getElementById("accNo").value;
			}
				//Added By Kalpna
				var parentCounter=	thisEl.attr("parentCounter");
				if(parentCounter!=undefined){
					param = callerWindowObj.document.getElementById("accNo").value;
					if(thisEl.attr("parntAttId")!=undefined && parentCounter!=0){
						var parentVal=$("#newprodAttVal_"+parentCounter).val();
						if(parentVal==undefined){
							param=$("#prodAttVal_"+parentCounter).val();
						}else{
							param=parentVal;
						}
			       	}else{
			       		param=0;
			       	}
				}
				//end Kalpna
				
	return param;
}
function getCallType(thisEl){
	var callType = null;
	if(thisEl.attr("retval")=="AUTOSUGGESTLEGALENTITY" || thisEl.attr("retval")=="AUTOSUGGESTLICENSECO"){
		var poNo = $("#txtPODetailNo").val();
		if(productCatalogueType=="CHANGE" && poNo==0 && (((hdnChangeTypeId==5 && changeSubTypeID==5) || (hdnChangeTypeId==4 && changeSubTypeID==12) )
				||(1==additionalNodeFlagCheck() && $("#hdnHardwareInfo").val()==1))){
			callType = "CHANGECALL";
		}else{
			callType = "NEWCALL";
		}	
	}
	return callType;
}
function getProdCatType(thisEl){
	if(thisEl.attr("retval")=="AUTOSUGGESTCUSTPODETAIL"){
		if(null != editSolutionChangeOldProduct && undefined != editSolutionChangeOldProduct && editSolutionChangeOldProduct == 1){
			return productCatalogueType + editSolutionChangeOldProduct;
		}else if(null != isOldLineItem_RateRenewal && undefined != isOldLineItem_RateRenewal && isOldLineItem_RateRenewal == 1){	//[001] Start
			return productCatalogueType + isOldLineItem_RateRenewal; //[001] End
		}else if(productCatalogueType=="CHANGE")
		{
			if(additionalNodeFlagCheck()==1)
			{
			return productCatalogueType + 1; //Added by Deepak Kumar hardware line additional node condition with value as change1
			}
			
		}else{
			return productCatalogueType;
		}
	}
	return productCatalogueType;
}
//flag to check if any autosuggest is opened
function getAutoSuggest(thisEl){
    //$(":text[ctrltype='dropdown']").each(function() {
		//var thisEl = $(this);
	if(thisEl.is(":disabled")){
		return;
	}
	var conditionalUrl;
	var serviceIdNo = document.getElementById('id_span_ServiceNo').innerHTML;
	var param=getParam(thisEl);
	param=encodeURIComponent(param);
	
   	thisEl.autocomplete({
   		source: function(request, response){
	        //Puneet parameter for call type
	        var callType = getCallType(thisEl);
	        var prodCatType = getProdCatType(thisEl);
	        if(thisEl.attr("retval")=="AUTOSUGGESTLICENSECO")
	    	{
	    		conditionalUrl = "/NewOrderAction.do?method=populateServiceAttMasterValue&searchval="+request.term+"&attributefor="+thisEl.attr("retval")+"&sourceType=" +prodCatType+ "&param="+param+ "&callType="+callType+"&parntAttId="+thisEl.attr("parntAttId")+"&serviceId="+serviceIdNo+"&Date="+new Date();
	    		
	    	}
	    	else
	    	{
	    		conditionalUrl = "/NewOrderAction.do?method=populateServiceAttMasterValue&searchval="+request.term+"&attributefor="+thisEl.attr("retval")+"&sourceType=" +prodCatType+ "&param="+param+ "&callType="+callType+"&parntAttId="+thisEl.attr("parntAttId");
	    	}
           	$.ajax({
           		//added one parameter to check that it is called from service summary section in NewOrderAction by Kalpana
           		url: path + conditionalUrl,
                data: "{ edition: '" + request.term + "',targetctrl: 'test' }",
                dataType: "json",
                type: "Get",
                selectFirst:true,		                   
                contentType: "application/json; charset=utf-8",
                dataFilter: function(data) {  return data; },
               	success: function(data) {	                        
               		response($.map(data.glossary, function(item) {
                    return {
                    	value: item.value,
                        label: item.label,
                        addParam: item.addParam 
                    }
               		}))
               },async:false,
               error: function(XMLHttpRequest, textStatus, errorThrown) {
                   alert(errorThrown);
               }
           });
       },select: function (event, ui){                                	                                                           
    	   if(ui.item.value=="-1"){
    		   setASValuesForNoRecord(thisEl);
           }else{
        	   setASValuesForFetchedRecord(thisEl, ui);
           }
    	   return false;
       },focus: function(event, ui) {
    	   if(focusEnabled){
   				thisEl.val(ui.item.label );
    	   }else{
    		   focusEnabled = true;
    	   }
   			return false;  
		},change: function( event, ui){	
			if(ui.item==null || thisEl.val()=="-No Record Found-" ||thisEl.val()=="-1"
				|| $("#prodAttVal_"+thisEl.attr("counterval")).val()=="0"){
				
  				var value2,label2,addParam;
  				var param=getParam(thisEl);
  				var callType = getCallType(thisEl);
  				var prodCatType = getProdCatType(thisEl);
			 	var validateData=$.ajax({
			 		//added one parameter to check that it is called from service summary section in NewOrderAction by Kalpana
					url: path + "/NewOrderAction.do?method=populateServiceAttMasterValueValidate&searchval="+thisEl.val()+"&attributefor="+thisEl.attr("retval")+"&sourceType="+prodCatType+ "&param="+param+ "&callType="+callType+"&parntAttId="+thisEl.attr("parntAttId"),
					data: "{ edition: '" + thisEl.val() + "',targetctrl: 'test' }",
		  			dataType: "json",
		   			type: "Get",
		  			contentType: "application/json; charset=utf-8",
		   			dataFilter: function(data) {  return data; },
					success: function(data) {
      					value2=data.glossary[0].value;
      					label2=data.glossary[0].label;
      					addParam=data.glossary[0].addParam;
      				},async:false,
      				error: function(XMLHttpRequest, textStatus, errorThrown) {
		            	alert(errorThrown);
					}
				});
				if(value2=="-1" ||label2=="-No Record Found-"){
					setASValuesForNoRecord(thisEl);
				}else{
					setASValuesForFetchedRecordForChange(thisEl, ui, value2, label2);
				}
			}else{
				value2 = ui.item.value;
				label2 = ui.item.label;
				if(value2=="-1" ||label2=="-No Record Found-"){
					setASValuesForNoRecord(thisEl);
				}else{
					setASValuesForFetchedRecordForChange(thisEl, ui, value2, label2);
				}
				if(thisEl.attr("tabType") == "servSumm")
					fnGetDestLabelValueForLOV(thisEl.attr("retval"),ui.item.label);			
					//to reset child LOV by Kalpana
					if(thisEl.attr("isParentAtt")==1){
			        	resetChildAttr(thisEl);
			        }
					//end
			}																
		},								
       minLength: 0
   });
//});
}

function resetLicenseCo(){
	$('#txtLicenceCo').val("0");
	$('#licenseCo').val("Select Licence Company");
	if(null != $('#licenseCo').data() && null != $('#licenseCo').data().autocomplete)
		$('#licenseCo').data().autocomplete.term = null;
	if($("#hdnHardwareInfo").val()==1)
		resetStore();
}
//Puneet function to reset the auto suggest store values
function resetStore(){
	getHardwareDetails();
	$('#txtStore').val("0");
	$('#txtStoreText').val("Select Store");
	if(undefined != $('#txtStoreText').data() && null != $('#txtStoreText').data() 
			&& undefined != $('#txtStoreText').data().autocomplete && null != $('#txtStoreText').data().autocomplete)
		$('#txtStoreText').data().autocomplete.term = null;
}
function resetContMonth(){
	$('#txtCntrtMonths').val("");
}
function resetEntity(){
	$('#txtEntity').val("0");
	$('#legalEntityTXT').val("Select Legal Entity");
	if(null != $('#legalEntityTXT').data() && null != $('#legalEntityTXT').data().autocomplete)
		$('#legalEntityTXT').data().autocomplete.term = null;
	resetLicenseCo();
	
}
function AttachCSStoAutoSuggestButton(){		
	$("[ctrltype='dropdownlink']").each(function()
	 {
		/*var thisEl = $(this);
		thisEl.attr( "tabIndex", -1 )
			.attr( "title", "Show All Items" )
			.button({
				icons: {
					primary: "ui-icon-triangle-1-s"
				},
				text: false
			})
			.removeClass( "ui-corner-all" )
			.addClass( "ui-corner-right ui-button-icon" )*/
			 attachCSForAS($(this));
   });
  // callOnBlur();
}

//Puneet for auto suggest of customerPO, assigning all the dependent values
function updateselectedCustPODetailDepValues(custPOId){
	var poDetails = jsonrpc.processes.populatePODetailsForSelectedPO(custPOId);
	if(null != poDetails){
		document.getElementById('txtBillingPODate').value = poDetails.custPoDate;
		document.getElementById('txtCntrtMonths').value = poDetails.periodsInMonths;
	}else{
		document.getElementById('txtBillingPODate').value = "";
		document.getElementById('txtCntrtMonths').value = "";
	}
}
//Start[001]
function selectPoIdAS(obj){
	if(obj.val()==0){
		$('#txtPoId').val("");
	}else{
		$('#txtPoId').val($('#txtPODetailNo').val());
	}
}

//*******************For Taxation Exemption Filling Standard Reason Start************************
function setPopulateStdReason(taxationValue){
	//Puneet Changing for auto select
	if(taxationValue=="")
		return;
	var combo = document.getElementById("changeReason");
	var txtReason = document.getElementById("txtReason");
	if(taxationValue==2){
		//populateReasonForChange();
		combo.disabled=false;
		combo.className = "inputBorder1";
		txtReason.disabled=false;
		txtReason.className = "inputBorder1";
		document.getElementById('reasonAutoSuggestLinkId').disabled=false;
		$("#txtReason").autocomplete( "enable" );
		combo.value = 0;
		txtReason.value="Select Standard Reason";
	}else{
		 //removeAllOptions(combo);
		 combo.value = -1;
		 txtReason.value="";
		 combo.disabled=true;
		 combo.className = "inputDisabled";
		 txtReason.disabled=true;
		 txtReason.className = "inputDisabled";
		 document.getElementById('reasonAutoSuggestLinkId').disabled=true;
		 //$("#txtReason").autocomplete( "disable" );
	}
	var i;
	var tableCharges = document.getElementById('tableCharges');
	if(null != tableCharges){
        var counter = (tableCharges.rows.length);
		for(i=1; i<=counter;i++){
			getTaxRate(i);
		}
	}
}

//Puneet Changing for auto suggest
function getTaxRate(var_counter){		
	if(document.getElementById("txtTaxation").value==1){
		fetchTaxRateForAutoSuggest(path,var_counter);
	}else if(document.getElementById("txtTaxation").value==2){
		document.getElementById("txtTaxRate"+var_counter).value="";
	}
}

function setBlankChargeName(attrid){
	$("#chargeName"+attrid).val("Select Charge Name");
	$("#txtCName" + attrid).val("-1");
	$("#txtCAnnotation" + attrid).val("");
}
function getEndDateRCOption(){
	return "<option title='Billing Trigger Date' value='BTD'>Billing Trigger Date</option>" + 
				"<option title='Till Disconnection' value='TD'>Till Disconnection</option>";
}

function getEndDateNRCOption(){
	return "<option title='One Time Charge' value='OT'>One Time Charge</option>";
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

function getComponentStartDateOption(){
	var startTypeOption = "<option value='-1' title='Select Start Date Logic'>Select Start Date Logic</option>";
	try{
		var startDateLogicList;
		//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
		startDateLogicList = jsonrpc.processes.fillComponentStartDateLogic();
		//var cboStartDateLogic=document.getElementById('txtCompStartDateLogic'+index);		
		for(k=0;k<startDateLogicList.list.length;k++){	   
			startTypeOption = startTypeOption + "<option value='" + startDateLogicList.list[k].componentStartDateLogicId + "'>" + startDateLogicList.list[k].componentStartDateLogicDesc + "</option>";
		}
	}catch(e){
		alert("Error :"+e);	
		return "<option value='-1' title='Select Start Date Logic'>Select Start Date Logic</option>";
	}
	return startTypeOption;
}

function getComponentEndDateOption(){
	var endTypeOption = "<option value='-1' title='Select End Date Logic'>Select End Date Logic</option>"; 
	try{
		var endDateLogicList;
		//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
		endDateLogicList = jsonrpc.processes.fillComponentEndDateLogic();
		//var cboStartDateLogic=document.getElementById('txtCompStartDateLogic'+index);		
		for(k=0;k<endDateLogicList.list.length;k++){	   
			endTypeOption = endTypeOption + "<option value='" + endDateLogicList.list[k].componentEndDateLogicId + "'>" + endDateLogicList.list[k].componentEndDateLogicDesc + "</option>";
		}
	}catch(e){
		alert("Error :"+e);	
		return "<option value='-1' title='Select End Date Logic'>Select End Date Logic</option>";
	}
	return endTypeOption;
}

function allFieldRoleMappingValidation(lstFieldList, ctrls){
	var cntrlArry = new Array();
	for(i = 0 ; i < ctrls.length; i++)
		cntrlArry[i] = document.getElementById(ctrls[i]);
	var myForm=document.getElementById('productCatelog');
	for (iField = 0 ; iField < lstFieldList.list.length; iField++){
			for(iCtrl=0;iCtrl< cntrlArry.length;iCtrl++){
				if(cntrlArry[iCtrl].name==undefined){
				  for(ictrlName=0;ictrlName<cntrlArry[iCtrl].length;ictrlName++){					  
				   ctrl = cntrlArry[iCtrl];
				   ctrlName = ctrl[ictrlName].name;
				   if(ctrlName==lstFieldList.list[iField].feildName){
						if(lstFieldList.list[iField].isReadOnly=="1" || !(isView == null || isView == 'null')){									
							if(ctrl[ictrlName].type=="select-one" || cntrlArry[iCtrl].ctrltype == 'dropdownlink')
								ctrl[ictrlName].disabled  = true;
							else if(ctrl[ictrlName].type=="button"){
								ctrl[ictrlName].disabled="disabled";
							}else
							    ctrl[ictrlName].readOnly  = true;		
						}else{
							if(ctrl[ictrlName].type=="select-one" || cntrlArry[iCtrl].ctrltype == 'dropdownlink')
								ctrl[ictrlName].disabled  = false;
							else
							    ctrl[ictrlName].readOnly  = false;		
						}
						ctrl[ictrlName].isRequired = lstFieldList.list[iField].isMand;
						if(cntrlArry[iCtrl].ctrltype != 'dropdownlink'){
							if(ctrl[ictrlName].isRequired=="0")
								ctrl[ictrlName].className = "inputBorder2";
							else
								ctrl[ictrlName].className = "inputBorder1";
						}
						ctrl[ictrlName].Disp = lstFieldList.list[iField].fieldLabel;
					}
				  }
				  cntrlArry.splice(iCtrl, 1); 
			  }else{
				  	ctrlName = cntrlArry[iCtrl].name;
				  	if(ctrlName==lstFieldList.list[iField].feildName){
				  		if(lstFieldList.list[iField].isReadOnly=="1" || !(isView == null || isView == 'null')){		
				  			if(cntrlArry[iCtrl].type=="select-one" || cntrlArry[iCtrl].ctrltype == 'dropdownlink')
				  				cntrlArry[iCtrl].disabled  = true;
				  			else if(cntrlArry[iCtrl].type=="button" )
				  				cntrlArry[iCtrl].disabled="disabled";
				  			else
				  				cntrlArry[iCtrl].readOnly  = true;		
				  		}else{
				  			if(cntrlArry[iCtrl].type=="select-one" || cntrlArry[iCtrl].ctrltype == 'dropdownlink')
				  				cntrlArry[iCtrl].disabled  = false;
				  			else
				  				cntrlArry[iCtrl].readOnly  = false;		
				  		}
				  		cntrlArry[iCtrl].isRequired = lstFieldList.list[iField].isMand;
				  		if(cntrlArry[iCtrl].ctrltype != 'dropdownlink'){
				  			if(cntrlArry[iCtrl].isRequired=="0")
				  				cntrlArry[iCtrl].className = "inputBorder2";
				  			else
				  				cntrlArry[iCtrl].className = "inputBorder1";
				  			cntrlArry[iCtrl].Disp = lstFieldList.list[iField].fieldLabel;
				  		}
				  		cntrlArry.splice(iCtrl, 1);
				  	}
			  	}						
			}	
		}
		if(!(isView == null || isView == 'null')){
			if(document.getElementById('btnAddContact') != null){
				document.getElementById('btnAddContact').disabled = true;
			}
			if(document.getElementById('btnDeletePoDetail') != null){
				document.getElementById('btnDeletePoDetail').disabled = true;
			}
		}
}

function resetProducCatalogueFlags(){
	billingDetailsFetched = false;
	hardwareDetailsFetched = false;
	locationDetailsFetched = false;
	componentDetailsFetched = false;
	serviceSummaryFetched=false;
	//clicking all the opened tabs
	$("form :button[value='-']").each(function() {
		$(this).trigger('click');
	});
}

function getAutoSuggestOnDownKeyPress(){
	getDropDownLegalEntity();
}

function resetBillingLevel(){
	$('#txtBillingLvl').val("0");
	$('#billingLevel').val("Select Billing Level");
	if(null != $('#billingLevel').data() && null != $('#billingLevel').data().autocomplete)
		$('#billingLevel').data().autocomplete.term = null;
}

function resetBillingFormat(){
	$('#txtBillingformat').val("0");
	$('#billingFormat').val("Select Billing Format");
	if(null != $('#billingFormat').data() && null != $('#billingFormat').data().autocomplete)
		$('#billingFormat').data().autocomplete.term = null;
}

function resetBillingMode(){
	$('#txtBillingMode').val("0");
	$('#billingMode').val("Select");
	if(null != $('#billingMode').data() && null != $('#billingMode').data().autocomplete)
		$('#billingMode').data().autocomplete.term = null;
}
//Start Deepak Kumar for third Party(SCM)
//To get selected Item_Code 
function getSelectedItemCodeLabel(itemId)
{
var itemCombo;
var itemCodeOption=null;
itemCombo = jsonrpc.processes.populateItemCodeSCM(itemId);
if(null != itemCombo){
		if(itemCombo.list.length==0){
			alert('No Item Code against this product!!');
		}else{
			for(var z=0;z<itemCombo.list.length;z++){
				itemCodeOption =itemCombo.list[z].itemCode;
			}
		}
	}
	return itemCodeOption;
	}
//To get selected DeliveryLocation	
function getSelectedDeliveryLocLabel(locId)
{
var delCombo;
var delCodeOption=null;
delCombo = jsonrpc.processes.populateDeliveryLocation(locId);
if(null != delCombo){
		if(delCombo.list.length==0){
			alert('No Delvery Location against this product!!');
		}else{
			for(var z=0;z<delCombo.list.length;z++){
				delCodeOption =delCombo.list[z].deliveryLocation;
			}
		}
	}
	return delCodeOption;
	}
//To get selected SubInventory	
function getSelectedSubInventoryLabel(subInId)
{
var subInvenCombo;
var subInvenOption=null;
subInvenCombo = jsonrpc.processes.populateSubInventory(subInId);
if(null != subInvenCombo){
		if(subInvenCombo.list.length==0){
			alert('No SubInventory against this product!!');
		}else{
			for(var z=0;z<subInvenCombo.list.length;z++){
				subInvenOption =subInvenCombo.list[z].subInventory;
			}
		}
	}
	return subInvenOption;
}
function getSelectedAop1BudgetLabel(aop1Id)
{
var aop1_BudgetCombo;
var aop1_BudgetOption=null;
aop1_BudgetCombo = jsonrpc.processes.populateBudgetHeadAop1(aop1Id);
if(null != aop1_BudgetCombo){
		if(aop1_BudgetCombo.list.length==0){
			alert('No Budget Head1 against this product!!');
		}else{
			for(var z=0;z<aop1_BudgetCombo.list.length;z++){
				aop1_BudgetOption =aop1_BudgetCombo.list[z].budgetHead1; 
				
			}
		}
	}
	return aop1_BudgetOption;
}
function getSelectedAop2Budget(aop1_Id)
{
var aop2_BudgetCombo;
var aop2_BudgetOption="<option value='-1'>Select Budget Head2</option>";
aop2_BudgetCombo = jsonrpc.processes.populateBudgetHeadAop2(aop1_Id);
if(null != aop2_BudgetCombo){
		if(aop2_BudgetCombo.list.length==0){
			alert('No Budget Head2 against this product!!');
		}else{
			for(var z=0;z<aop2_BudgetCombo.list.length;z++){
				aop2_BudgetOption = aop2_BudgetOption + "<option value='" + aop2_BudgetCombo.list[z].aop2_Id + "'>" + aop2_BudgetCombo.list[z].budgetHead2 + "</option>";
			}
		}
	}
	return aop2_BudgetOption;
}		
function getFinancialYr()
{
var year=new Array();
year[0]=currentYearFormat1();
year[1]=previousYearFormat1();
return year;
}
function previousYearFormat1()
{
var now = new Date();
var thisYear = now.getFullYear();
var thisYr=parseInt(thisYear)-2000;
var lastYear = parseInt(now.getFullYear()) - 1;
var lastYr=parseInt(lastYear)-2000;
return lastYr+"-"+thisYr;
}
function currentYearFormat1()
{
var now = new Date();
var thisYear = now.getFullYear();
var thisyr=parseInt(thisYear)-2000;
var nextYear = parseInt(now.getFullYear())+1;
var nxtyr=parseInt(nextYear)-2000;
return thisyr+"-"+nxtyr;
}	
function getSelectedAopYear()
{
var aop2_BudgetOption="<option value='-1'>Select Financial Year</option>";
var aop2_BudgetCombo;
aop2_BudgetCombo =getFinancialYr();
if(null != aop2_BudgetCombo){
		if(aop2_BudgetCombo.length==0){
			alert('No Year against this product!!');
		}else{
			for(var z=0;z<aop2_BudgetCombo.length;z++){
				aop2_BudgetOption = aop2_BudgetOption + "<option value='" + aop2_BudgetCombo[z] + "'>" + aop2_BudgetCombo[z]+ "</option>";
			}
		}
	}
return aop2_BudgetOption;
}

function blankDelLocAsSubInvent()
{
var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
var len=chkChargeLine.length;
if(len>0){
for(var i=len-1;i>=0;i--)
		{
		var chkboxCharge=chkChargeLine[i];
		var index=chkboxCharge.value;
document.getElementById('delLocation'+index).value='Select Delivery Location';
document.getElementById('subInvent'+index).value='Select SubInventory';
document.getElementById('txtDelLocId'+index).value=-1;
document.getElementById('txtSubInvent'+index).value=-1;
}
}
}
//end Deepak