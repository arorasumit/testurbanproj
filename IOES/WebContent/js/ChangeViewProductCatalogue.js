//[001] SANTOSH SRIVASTAVA	ADDED A METHOD FOR ADVANCE PAYMENT DETAILS FOR CHANGE VIEW ORDER
//[0002] Gunjan 				Added for status of charge 
//[003] VIPIN SAHARIA 04-06-2014 Added hidden field for FxChargeID required for extra logic DC_COLO_SERVICE_TAX Charge (Hardware DC)
// [005] 	 Vipin Saharia	27-Aug-14	GDB Lic Comp. segregation changed if condition for new licence company
// [007] VIPIN SAHARIA 30-Jan-2014 Logic added for read only attributes in Service Summary Section.
//[008] Pankaj Thakur   25-02-2015    added 'editableString ' to change the remarks after first save
//[009]Pankaj Thakur  10-april-2015 added a condition to make View Product Catelog page non-editable  in case of viewMode
//[0010] Gunjan Singla 24-Apr-2015   20150202-R2-021037    Suppression of creation of new billable account in case of change order

//[013] Raveendra Kumar  15-May-2015 20141219-R2-020936-Billing Efficiency Program      Annotation length in IB2B
//[015] Gunjan Singla    20-Aug-2015    value for old attributes in solution change order
//[017] Gunjan Singla  2-sept-15 20150603-R2-021385    Charge End Date and Short Billing
var productCatalogueType="CHANGE";
var focusEnabled = true;
var lineType = "EXISTING";
var linkChargeArry = new Array(); //this array will contain the counter value who has linked charge id
var disLinkChargeArray = new Array();
function checkchargevalidation(){
	var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	test = jsonrpc.processes.getCreatedinServiceid(serviceProductID);
	if(test==servicesID){
		if(chkChargeLine.length>0){
			//document.getElementById("txtPODetailNo").value=vPrevCustPoNo;
			document.getElementById('txtPODetailNo').value=vPrevCustPoValue;
			document.getElementById('poDetailNo').value=vPrevCustPoNo;
			alert('please delete All the Charges first');
		  	return false;
		}
	}
}
function getTip(value){	
	Tip("<Table border=0> "+ value +"   </table>");	
}
function getTip_DD(obj,value,objName){	
	var SelectedValue;
	var selIndex = value;
	var myForm=document.getElementById('productCatelog');
	SelectedValue=document.getElementById(objName).options[selIndex].text;	
	var combo = document.getElementById(objName);
	combo.title = SelectedValue;
}
 
function changeEndDays(index){
	if(document.getElementById('txtCEndDate'+index).value=='TD'){
		document.getElementById('txtEndDays'+index).value=0;
		document.getElementById('txtEndMonth'+index).value=0;
	}
}
function getPrevBillFormat(){	
	if(gbPrevBillFormatIndexflag==0){
		vPrevBillFormat=document.getElementById('txtBillingformat').value;
		vPrevBillFormatValue=document.getElementById('billingFormat').value;
	}else{
		gbPrevBillFormatIndexflag=0;
	}
}
function getPrevCustPoNo(){		
	//vPrevCustPoNo=var_custpono;
	vPrevCustPoValue=document.getElementById('txtPODetailNo').value;
	vPrevCustPoNo=document.getElementById('poDetailNo').value;
}
/*function fillEndDateLogic(counter)
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
	    	var option = document.createElement("option");
	   		option.text = 'One Time Charge';
	   		option.title = 'One Time Charge';
			option.value = 'OT';
			document.getElementById("txtCEndDate"+counter).selectedIndex=1;
			try 
			{
				endDateLogicCombo.add(option, null); //Standard
			}
			catch(error) 
			{
				endDateLogicCombo.add(option); // IE only
			}
	
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
			option.value = 'TD';
			option.title = 'Till Disconnection';
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

function populateReasonForChange()
{	
	var tr, td, i, j, oneRecord;
    var test;
    var interFaceStdReason=1;
   var combo = document.getElementById("changeReason");	
   var iCountRows = combo.length;
   for (i = 1; i <  iCountRows; i++)
   {
       combo.remove(i);
   }
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	var stdReason = jsonrpc.processes.populateReasonForChange(interFaceStdReason);
	    for(j=0;j<stdReason.list.length;j++)
	    {
	    	var option = document.createElement("option");	    	
	   		option.text = stdReason.list[j].reasonName;
	   		option.title = stdReason.list[j].reasonName;
			option.value = stdReason.list[j].reasonID;
			try 
			{
			combo.add(option, null); //Standard
			}
			catch(error) 
			{
				combo.add(option); // IE only
			}
	    }    
}*/
function checkContractPeriod(counter){
	 if((parseFloat(document.getElementById('txtCPeriod'+counter).value))>(parseFloat(document.getElementById('txtCntrtMonths').value))){
		 alert("You cant enter Charge period greater than Contract Period");
		 document.getElementById('txtCPeriod'+counter).value=document.getElementById('txtCntrtMonths').value;
		 return false;
	 }
 
}
function calculateTotalAmount(){		
	//alert('attMasterIdList = '+attMasterIdList);	
	if(document.getElementById('txtBillingformat').value == 3 && document.getElementById('hdnServiceSummary').value == 1 
			&& document.getElementById('hdnChargeInfo').value == 1){
		var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
		var attributeID=new Array();
		//var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		var totalAmount = 0.0;		
		/*for(i=0;i<chkChargeLine.length;i++){
			var chkboxCharge=chkChargeLine[i];
			var index=chkboxCharge.value;
			totalAmount = parseFloat(totalAmount) + parseFloat(document.getElementById("txtCTAmt"+index).value);
				
		}*/
		totalAmount = Number(document.getElementById('totalLineAmount').value);
		var values = attMasterIdList.split(",");
		//alert('values = '+values);
		if(serviceSummaryFetched){
		if (values.length > 0){	
			for (var i = 0; i < values.length; i++){
			 //alert('att id described value = '+values[i]);
			 	for(j=1;j<=countAttributes;j++){ 
			 		//alert('DB att value = '+document.getElementById('hdnProdAttVal_'+j).value)
			 		if(document.getElementById('prodExpVal_'+j).value != "DROPDOWN" && document.getElementById('prodExpVal_'+j).value != "LOV" 
			 			&& values[i] == document.getElementById('hdnProdAttVal_'+j).value){							
			 			document.getElementById('prodAttVal_'+j).value = totalAmount;							
			 		}
			 	}
			}
		}
	}else{
		serviceSummaryIndValChanged = true;
		serviceSummaryIndVal = totalAmount;
	}	
	}
}

function checkTotalAmount(){
	var chkChargeLine;
	var totalCPeriod=0;
	chkChargeLine=document.getElementsByName('chkSelectChargeDetail')
			
	for(i=0;i<chkChargeLine.length;i++){
		var chkboxCharge=chkChargeLine[i];
		var index=chkboxCharge.value;
		if(document.getElementById("ddCType"+index).value==1){
			if(document.getElementById("txtCEndDate"+index).value=="BTD"){
						//commented by Ravneet , for reverse migration correction
						//if(parseInt(document.getElementById("txtEndMonth"+index).value) < parseInt(document.getElementById("txtStartMonth"+index).value))
						if((parseInt(document.getElementById("txtEndMonth"+index).value) < parseInt(document.getElementById("txtStartMonth"+index).value))
								&& document.getElementById("isAddedInCurrentService"+index).value=='1')
						{	
				    var lineno=index-1;
					alert('End Date Month should be greater than Start Date Month in Line No:' + lineno);
					return false;
				}
			}
		}  
		totalCPeriod = parseFloat(totalCPeriod) + parseFloat(document.getElementById("txtCPeriod"+index).value);
	}		
}
//====================================================================================================
//---------------------------------Added For Field Level Validations----------------------------------
//			1. initFieldValidation -- For Initialising Fields For Validation
//			2. setServiceSummaryAttributes -- Setting Service Summary Attributes Read Only
//			3. fieldRoleMappingValidation -- Checking Role Level Mapping With Feilds and Setting 
//											 the attributes accordingly
//====================================================================================================
function initFieldValidation(){
	var myForm=document.getElementById('productCatelog');
	var ctrlArry = new Array();
    ctrlArry[0] =  myForm.txtPoId;
	ctrlArry[1] =  myForm.txtPODetailNo;
	ctrlArry[2] =  myForm.txtBillingPODate;
	ctrlArry[3] =  myForm.txtCntrtMonths;
	ctrlArry[4] =  myForm.txtBillingAC;
	ctrlArry[5] =  myForm.txtBillingCP;
	ctrlArry[6] =  myForm.txtCur;
	ctrlArry[7] =  myForm.txtEntity;
	ctrlArry[8] =  myForm.txtBillingMode;
	ctrlArry[9] =  myForm.txtBillingformat;
	ctrlArry[10] =  myForm.txtLicenceCo;
	ctrlArry[11] =  myForm.txtBillingLevelNo;
	ctrlArry[12] =  myForm.txtNoticePeriod;
	ctrlArry[13] =  myForm.txtBillingType;
	ctrlArry[14] =  myForm.txtTaxation;
	ctrlArry[15] =  myForm.txtCmtPeriod;
	ctrlArry[16] =  myForm.txtBillingLvl;
	ctrlArry[17] =  myForm.txtPenaltyClause;
	ctrlArry[18] =  myForm.bbPrimaryBCP;
	ctrlArry[19] =  myForm.ddPrimaryLocType;
	ctrlArry[20] =  myForm.ddSecondaryLocType;
	ctrlArry[21] =  myForm.ddPrimaryBCP;
	ctrlArry[22] =  myForm.ddSecondaryBCP;
	ctrlArry[23] =  myForm.ddPNLocation;
	ctrlArry[24] =  myForm.ddSNLocation;
	ctrlArry[25] =  myForm.txtFAddress;
	ctrlArry[26] =  myForm.txtToAddress;
	ctrlArry[27] =  myForm.txtStore;
	ctrlArry[28] =  myForm.txtHtype;
	ctrlArry[29] =  myForm.txtform;
	ctrlArry[30] =  myForm.txtTSale;
	ctrlArry[31] =  myForm.txtNSale;
	ctrlArry[32] =  myForm.txtdispatchAddress;
	ctrlArry[33] =  myForm.txtStartDateLogic;
	ctrlArry[34] =  myForm.txtEndDateLogic;
	ctrlArry[35] =  myForm.txtStartDate;
	ctrlArry[36] =  myForm.txtEndDate;
	ctrlArry[37] =  myForm.txtInterestRate;
	ctrlArry[38] =  myForm.txtPrincipalAmount; 
	ctrlArry[39] =  myForm.txtHStartMonth;
	ctrlArry[40] =  myForm.txtHStartDays; 
	ctrlArry[41] =  myForm.txtHEndMonth;
	ctrlArry[42] =  myForm.txtHEndDays; 
	ctrlArry[43] =  myForm.txtHExtMonth;
	ctrlArry[44] =  myForm.txtHExtDays; 
	ctrlArry[45] =  myForm.txtHExtDate; 
	//[004]start
	ctrlArry[46] =  myForm.txtDispatchContactName;
	ctrlArry[47] =  myForm.poDetailNo;
	ctrlArry[48] =  myForm.billingCP;
	ctrlArry[49] =  myForm.legalEntityTXT;
	ctrlArry[50] =  document.getElementById('legalEntityTXTLinkId');//myForm.legalEntityTXTLinkId;
	ctrlArry[51] =  document.getElementById('billingCPTXTLinkId');//myForm.billingCPTXTLinkId;
	ctrlArry[52] =  document.getElementById('poDetailNoTXTLinkId');//myForm.poDetailNoTXTLinkId;
	ctrlArry[53] =  myForm.licenseCo;
	ctrlArry[54] =  document.getElementById('licenseCoLinkId');
	ctrlArry[55] =  myForm.billingFormat;
	ctrlArry[56] =  document.getElementById('billingFormatTXTLinkId');
	ctrlArry[57] =  myForm.billingMode;
	ctrlArry[58] =  document.getElementById('billingModeTXTLinkId');
	ctrlArry[59] =  myForm.billingType;
	ctrlArry[60] =  document.getElementById('billingTypeLinkId');
	ctrlArry[61] =  myForm.taxationID;
	ctrlArry[62] =  document.getElementById('taxationAutoSuggestLinkId');
	ctrlArry[63] =  myForm.billingLevel;
	ctrlArry[64] =  document.getElementById('billingLevelLinkId');
	ctrlArry[65] =  myForm.textFormTXT;
	ctrlArry[66] =  document.getElementById('textFormTXTLinkId');
	ctrlArry[67] =  myForm.textSaleType;
	ctrlArry[68] =  document.getElementById('saleTypeTXTLinkId');
	ctrlArry[69] =  myForm.txtStoreText;
	ctrlArry[70] =  document.getElementById('textStoreLinkId');
	ctrlArry[71] =  myForm.hTypeText;
	ctrlArry[72] =  document.getElementById('hTypeLinkId');
	//[004]end
    	// WARRANTY CLAUSE ADDED BY MANISHA START 
	ctrlArry[73] =  myForm.txtWarrantClause;
	// WARRANTY CLAUSE ADDED BY MANISHA end
	//bcp details for services ADDED BY MANISHA START
	ctrlArry[74] =  myForm.bbPrimaryBCPService;
	//satyapan By nagarjuna
	ctrlArry[75] =  myForm.txtOSPTagging;
	ctrlArry[76] =  myForm.txtOSPRegNo;
	ctrlArry[77] =  myForm.txtOSPRegDate;
	//satyapan By Nagarjuna
	//bcp details for services ADDED BY MANISHA end
	var callerWindowObj = dialogArguments;
	fieldRoleMappingValidation(linesTabFieldList,ctrlArry);
}	//Added By Ashutosh for Service Summary Validation As on Date 29-Jun-2012
	
function initValidationForServiceSummary(){
	if(document.getElementById('hdnServiceSummary').value==1){	
		var countAttributes=document.getElementById('hdnSeriveAttCounter').value;				
		var i;			
		for(i=1;i<=countAttributes;i++){
			if(hdnChangeTypeId==2 && (changeSubTypeID==8 || changeSubTypeID==9 || changeSubTypeID==10) 
				|| (hdnChangeTypeId==1 && changeSubTypeID==20)){
					if(document.getElementById('newprodAttMandatory_'+i).value==1)
						document.getElementById('newprodAttVal_'+i).className = "inputBorder1";
					else
						document.getElementById('newprodAttVal_'+i).className = "inputBorder2";
			}					
		}
	}
}
	
function setServiceSummaryAttributes(){
	var callerWindowObj = dialogArguments;
	if(serviceSummaryList.list[0].feildName=="ServiceSummary"){
		if(serviceSummaryList.list[0].isReadOnly=="1"){
			isServiceReadOnly="1";
		}
	}
}
	
	/*function fieldRoleMappingValidation(lstFieldList,ctrlArry){
		var myForm=document.getElementById('productCatelog')
		for (iField = 0 ; iField < lstFieldList.list.length; iField++){
 			for(iCtrl=0;iCtrl< ctrlArry.length;iCtrl++){				
					  if(ctrlArry[iCtrl].name==undefined){
						  for(ictrlName=0;ictrlName<ctrlArry[iCtrl].length;ictrlName++){					  
							  ctrl = ctrlArry[iCtrl];
							  ctrlName = ctrl[ictrlName].name;	
							  if(ctrlName==lstFieldList.list[iField].feildName){
								  if(lstFieldList.list[iField].isReadOnly=="1" || isView!=null){									
									  	if(ctrl[ictrlName].type=="select-one")
									  		ctrl[ictrlName].disabled  = true;
									  	else
									  		if(ctrl[ictrlName].type=="button"){
									  			ctrl[ictrlName].disabled="disabled";
									  		}else
									  			ctrl[ictrlName].readOnly  = true;		
								  }else{
									  if(ctrl[ictrlName].type=="select-one")
										  ctrl[ictrlName].disabled  = false;
									  else
										  ctrl[ictrlName].readOnly  = false;		
								  }
								  ctrl[ictrlName].isRequired = lstFieldList.list[iField].isMand;
								  if(ctrlArry[iCtrl].isRequired=="0")
									  ctrlArry[iCtrl].className = "inputBorder2";
								  else
									  ctrlArry[iCtrl].className = "inputBorder1";
								  ctrl[ictrlName].Disp = lstFieldList.list[iField].fieldLabel;
							  }
						  }
					  }else{
					    ctrlName = ctrlArry[iCtrl].name;
						if(ctrlName==lstFieldList.list[iField].feildName){
							if(lstFieldList.list[iField].isReadOnly=="1" || isView!=null){		
								if(ctrlArry[iCtrl].type=="select-one")
									ctrlArry[iCtrl].disabled  = true;
								else
									if(ctrlArry[iCtrl].type=="button")
										ctrlArry[iCtrl].disabled="disabled";
									else
										ctrlArry[iCtrl].readOnly  = true;		
								
							}else{
								if(ctrlArry[iCtrl].type=="select-one")
									ctrlArry[iCtrl].disabled  = false;
								else
								    ctrlArry[iCtrl].readOnly  = false;		
							}
							ctrlArry[iCtrl].isRequired = lstFieldList.list[iField].isMand;
							if(ctrlArry[iCtrl].isRequired=="0")
								ctrlArry[iCtrl].className = "inputBorder2";
							else
								ctrlArry[iCtrl].className = "inputBorder1";
							ctrlArry[iCtrl].Disp = lstFieldList.list[iField].fieldLabel;
						}
					  }						
				}	
			}
			if(isView != null){
				if(document.getElementById('idChargesDeleteBtn') != null){
					document.getElementById('idChargesDeleteBtn').disabled = true;
				}
				if(document.getElementById('idChargesAddBtn') != null){
					document.getElementById('idChargesAddBtn').disabled = true;
				}
		}
	}*/
function fieldRoleMappingValidation(lstFieldList,ctrlArry){
	var myForm=document.getElementById('productCatelog');
	for (iField = 0 ; iField < lstFieldList.list.length; iField++){
		for(iCtrl=0;iCtrl< ctrlArry.length;iCtrl++){
			if(ctrlArry[iCtrl].name==undefined){
				for(ictrlName=0;ictrlName<ctrlArry[iCtrl].length;ictrlName++){					  
					ctrl = ctrlArry[iCtrl];
					ctrlName = ctrl[ictrlName].name;
					if(ctrlName==lstFieldList.list[iField].feildName){
						if(lstFieldList.list[iField].isReadOnly=="1" || !(isView == null || isView == 'null')){									
							if(ctrl[ictrlName].type=="select-one" || ctrlArry[iCtrl].ctrltype == 'dropdownlink')
								ctrl[ictrlName].disabled  = true;
							else if(ctrl[ictrlName].type=="button"){
								ctrl[ictrlName].disabled="disabled";
							}else
							    ctrl[ictrlName].readOnly  = true;		
						}else{
							if(ctrl[ictrlName].type=="select-one" || ctrlArry[iCtrl].ctrltype == 'dropdownlink')
								ctrl[ictrlName].disabled  = false;
							else
							    ctrl[ictrlName].readOnly  = false;		
						}
						ctrl[ictrlName].isRequired = lstFieldList.list[iField].isMand;
						if(ctrlArry[iCtrl].ctrltype != 'dropdownlink'){
							if(ctrl[ictrlName].isRequired=="0")
								ctrl[ictrlName].className = "inputBorder2";
							else
								ctrl[ictrlName].className = "inputBorder1";
						}
						ctrl[ictrlName].Disp = lstFieldList.list[iField].fieldLabel;
					}
				}
				ctrlArry.splice(iCtrl, 1); 
			}else{
				ctrlName = ctrlArry[iCtrl].name;
				if(ctrlName==lstFieldList.list[iField].feildName){
					if(lstFieldList.list[iField].isReadOnly=="1" || !(isView == null || isView == 'null')){		
						if(ctrlArry[iCtrl].type=="select-one" || ctrlArry[iCtrl].ctrltype == 'dropdownlink')
							ctrlArry[iCtrl].disabled  = true;
						else if(ctrlArry[iCtrl].type=="button" )
							ctrlArry[iCtrl].disabled="disabled";
						else
							ctrlArry[iCtrl].readOnly  = true;		
					}else{
						if(ctrlArry[iCtrl].type=="select-one" || ctrlArry[iCtrl].ctrltype == 'dropdownlink')
							ctrlArry[iCtrl].disabled  = false;
						else
							ctrlArry[iCtrl].readOnly  = false;		
						}
						ctrlArry[iCtrl].isRequired = lstFieldList.list[iField].isMand;
						if(ctrlArry[iCtrl].ctrltype != 'dropdownlink'){
					  		if(ctrlArry[iCtrl].isRequired=="0")
					  			ctrlArry[iCtrl].className = "inputBorder2";
					  		else
					  			ctrlArry[iCtrl].className = "inputBorder1";
					  	}
					  	ctrlArry[iCtrl].Disp = lstFieldList.list[iField].fieldLabel;
					  	ctrlArry.splice(iCtrl, 1);
					  	break;
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
function setPAandIR(){
	var myLocalForm = document.getElementById('productCatelog');
	if(document.getElementById("txtTSale").value==2){		
		document.getElementById("txtPrincipalAmount").disabled=false;
		document.getElementById("txtInterestRate").disabled=false;
	}else{
		document.getElementById("txtPrincipalAmount").disabled=true;
		document.getElementById("txtInterestRate").disabled=true;
	}
}

function removeAllOptions(selectbox){
	var i;
	for(i=selectbox.options.length-1;i>0;i--){
		selectbox.remove(i);
	}
}

function setTypeofSale(){
	gbPrevBillFormatIndexflag=1;
	if(document.getElementById('hdnHardwareInfo').value==1){
		getHardwareDetails();
		var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		if(chkChargeLine.length>0){			
			alert('Please delete all charges line first');
			//Puneet commenting for auto suggest
			//document.getElementById("txtBillingformat").selectedIndex=vPrevBillFormat;
			document.getElementById("txtBillingformat").value=vPrevBillFormat;
			document.getElementById("billingFormat").value=vPrevBillFormatValue;
			focusEnabled = false;
			return false;
		}
	}
    setBillingMode();
    var myLocalForm = document.getElementById('productCatelog');
	var billFormat=document.getElementById("txtBillingformat").value;
	if(billFormat!=2 && billFormat!=3){
		return false;
	}else{
	    /*var comboSaleType = document.getElementById("txtTSale");	    
	    removeAllOptions(document.getElementById("txtTSale"));
		//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
        var saleType = 0;
        saleType= jsonrpc.processes.typeOfSale(billFormat);*/
		document.getElementById('txtTSale').value="0";
		document.getElementById('textSaleType').value="Select Type Of Sale";
		if(null != $('#textSaleType').data() && null != $('#textSaleType').data().autocomplete)
			$('#textSaleType').data().autocomplete.term = null;
        /*for(j=0;j<saleType.list.length;j++){ 
	    	var option = document.createElement("option");
	   		option.text = saleType.list[j].saleTypeName;
			option.value = saleType.list[j].saleTypeId;
			option.title = saleType.list[j].saleTypeName;
			try{
				comboSaleType.add(option, null); //Standard
			}catch(error){
				comboSaleType.add(option); // IE only
			}
	    }*/
	    //if billing format editable, then type of sale must be editable::16-Jan-2013::
	   	 document.getElementById("txtTSale").disabled=false;
	   	 document.getElementById("textSaleType").disabled=false;
	   	 document.getElementById("saleTypeTXTLinkId").disabled=false;
	    //if billing format editable, then type of sale must be editable::16-Jan-2013::
	}
}
function fnCalculateHardwareEndDate(startDate,period){		
	str1=new String(startDate);
	y=str1.substring(6);
	m=str1.substring(3,5);

	var totalMonths=Number(m)+Number(period);
	
	if(totalMonths<12)
	{
		str2=new String(startDate);
		y=str2.substring(6);
		m=totalMonths;
		d=str2.substring(0,2);
		var date2=new Date(Number(y),Number(m)-1,Number(d));	
	}else if(totalMonths>=12){
		str2=new String(startDate);
		y=str2.substring(6);
		m=totalMonths;
		d=str2.substring(0,2);
		var date2=new Date(Number(y),Number(m)-1,Number(d));
	}
	d2=date2.getDate();
	m2=date2.getMonth()+1;
	y2=date2.getYear();
	var endDate =d2 + "/" + m2 + "/" + y2 ;
	document.getElementById('txtEndDate').value=new String(endDate);
	return;
}
function calculateFreqAmt(){
	if(document.getElementById("txtCFrequency").value!=-1){
		if(document.getElementById("txtCTAmt").value!=""){
			var frqAmount=0;
			var annualAmount=0;
			var factor=factorVal[document.getElementById("txtCFrequency").selectedIndex-1];
			var Cperiod=document.getElementById("txtCPeriod").value;
			var TotalAmount=document.getElementById("txtCTAmt").value;
			frqAmount=((TotalAmount/Cperiod)*factor).toFixed(2);
			annualAmount=(frqAmount*12/factor);
			document.getElementById("txtCFreqAmt").value=Math.round(frqAmount);
			document.getElementById("txtAnnualCharge").value=Math.round(annualAmount);
		}else{
			alert("Please Input Total Amount");
			document.getElementById("txtCFrequency").value=-1;
			return false;
		}
	}
}	
	
function fetchFeildList(changeSubTypeID){
	//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	roleFeild = jsonrpc.processes.getFieldValidationForChangeOrder(sessionid,changeSubTypeID);
	linesTabFieldList = roleFeild.list[3];
	serviceSummaryList = roleFeild.list[4];
	initFieldValidation();
}

function getServiceSummary(){
	/*var tr, td, i, j, oneRecord;
	var test,test1;
	var mytable = document.getElementById('ServiceList');
	var iCountRows = mytable.rows.length;
   
	for (i = 0; i <  iCountRows; i++){
       mytable.deleteRow(0);
	}*/   
	var hdnServiceSummary = document.getElementById('hdnServiceSummary');
	if(serviceSummaryFetched || hdnServiceSummary.value!=1){
		return;
	}
	var mytableDiv = document.getElementById('ServiceListDiv');
	mytableDiv.innerHTML="";
	var ordertype = "Change";
	var jsData ={
		serviceDetailID:serviceDetailID,
		serviceProductID:serviceProductID,
		poNumber:changeOrderNo,
		orderType:ordertype,
		serviceId:servicesID
	};
	var linakageUpdateFlag="NEW_PRODUCT";
	
	if(editSolutionChangeOldProduct==1){
		linakageUpdateFlag="CHANGE_PRODUCT";
	}
	linkageInfoList = jsonrpc1.processes.populateLinkageDetails(jsData,sessionid,linakageUpdateFlag);
	if(linkageInfoList.list.length != 0){
		document.getElementById('eLogicalCircuitId').value=linkageInfoList.list[0].logicalCircuitId;
    	document.getElementById('InfraOderNo').value=linkageInfoList.list[0].infraOderNo;
    	document.getElementById('metasolvCircuitId').value=linkageInfoList.list[0].metasolvCircuitId;
    	if(editSolutionChangeOldProduct==1 || !(isView ==null || isView == "null")){
    		document.getElementById('eLogicalCircuitId').className="inputDisabled";
    		document.getElementById('InfraOderNo').className="inputDisabled";
    		document.getElementById('metasolvCircuitId').className="inputDisabled";
    	}
//    	var callerWindowObj = dialogArguments;
	   //Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
    	if(editSolutionChangeOldProduct==1){ //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10)
    		document.getElementById('eLogicalCircuitId_new').value=linkageInfoList.list[0].logicalCircuitId_new;
    		document.getElementById('InfraOderNo_new').value=linkageInfoList.list[0].infraOderNo_new;
    		document.getElementById('metasolvCircuitId_new').value=linkageInfoList.list[0].metasolvCircuitId_new;
    	}
    }
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
    test = jsonrpc.processes.populateServiceAttMasterValue(jsData,sessionid);
    if(test.list.length==0){
    	document.getElementById('lineItemServiceSummary').style.display='none';
    	hdnServiceSummary.value=0;
    }else{
		/*var sectionDetail = getSectionDetail(roleWiseSectionDetail, "SERVICESUMMARY");
		var commercialAllowed = true;
		var sectionCommercial = false;
		var subSectionAttributeCheck = true;
		
		if(sectionDetail.list.length == 3){
			commercialAllowed = sectionDetail[0];
			//nonCommercialAllowed = sectionDetail[2];
			subSectionAttributeCheck = sectionDetail[1];
			sectionCommercial = sectionDetail[2];
		}
		var attributeDisabled = false;*/
    	document.getElementById('lineItemServiceSummary').style.display='block';
    	counter=1;
    	document.getElementById('hdnSeriveAttCounter').value=test.list.length;
    	/*var mytable = document.getElementById('header_ServiceList');
	   	var iCountRows = mytable.rows.length;
	   	for (i = 0; i <  iCountRows; i++){
	       mytable.deleteRow(0);
	   	}*/
    	var headerMyTable = document.getElementById('ServiceListHeaderDiv');
    	headerMyTable.innerHTML="";
    	var headerTable = "<table width='100%'  border='1' cellspacing='0' cellpadding='0' id='header_ServiceList'>";
    	var tableEnd = '</table>';
    	var tableRowStart= "<tr>";
    	var tableRowEnd = "</tr>";
    	var tableDataStart;
    	var tableDataEnd = "</td>";
    	
    	///var headerRow=document.getElementById('header_ServiceList').insertRow();
    	headerTable = headerTable + tableRowStart;
    	
    	tableDataStart = "<td align = 'left' width='41%' vAlign='top' style.fontSize='12px' style.fontWeight='bold'>";
    	
    	/*var tblcol=headerRow.insertCell();
		tblcol.align = "left";
		tblcol.width="41%"
		tblcol.vAlign="top";
		tblcol.style.fontSize="12px";
		tblcol.style.fontWeight="bold";*/
		str= "Label";
		/*CellContents = str;
		tblcol.innerHTML = CellContents;*/
		headerTable = headerTable + tableDataStart + str + tableDataEnd;
		
		if((hdnChangeTypeId==2 && (changeSubTypeID==8 || changeSubTypeID==9 || changeSubTypeID==10)) || hdnChangeTypeId==1){
			editSolutionChangeOldProduct=1;
		}
		var valueQuantity="Value";
		if(serviceDetailID==21)
			valueQuantity="Quantity";
		//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh	
		//if(callerWindowObj.document.getElementById("subChangeTypeId").value!= 10)
		//{
			if(editSolutionChangeOldProduct==1){
				document.getElementById('label1').style.display='block';
				document.getElementById('label2').style.display='block';
				document.getElementById('label3').style.display='block';
			}else{
				document.getElementById('label1').style.display='none';
				document.getElementById('label2').style.display='none';
				document.getElementById('label3').style.display='none';
			}
		//}
		//var callerWindowObj = dialogArguments;
		//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh

		if(editSolutionChangeOldProduct==1){ //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10)
			/*var tblcol=headerRow.insertCell();
			tblcol.align = "left";
			tblcol.width="30%"
			tblcol.vAlign="top";
			tblcol.style.fontSize="12px";
			tblcol.style.fontWeight="bold";*/
			str= "Old "+valueQuantity;
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			
			tableDataStart = "<td align = 'left' width='30%' vAlign='top' style.fontSize='12px' style.fontWeight='bold'>";
			headerTable = headerTable + tableDataStart + str + tableDataEnd;
			
			/*var tblcol=headerRow.insertCell();
			tblcol.align = "left";
			tblcol.width="29%"
			tblcol.vAlign="top";
			tblcol.style.fontSize="12px";
			tblcol.style.fontWeight="bold";*/
			str= "New "+valueQuantity;
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			tableDataStart = "<td align = 'left' width='29%' vAlign='top' style.fontSize='12px' style.fontWeight='bold'>";
			headerTable = headerTable + tableDataStart + str + tableDataEnd;
		}else{	
			/*var tblcol=headerRow.insertCell();
			tblcol.align = "left";
			tblcol.width="41%"
			tblcol.vAlign="top";
			tblcol.style.fontSize="12px";
			tblcol.style.fontWeight="bold";*/
			str= ""+valueQuantity;
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			tableDataStart = "<td align = 'left' width='41%' vAlign='top' style.fontSize='12px' style.fontWeight='bold'>";
			headerTable = headerTable + tableDataStart + str + tableDataEnd;
		}
		headerTable = headerTable + tableRowEnd + tableEnd;
		headerMyTable.innerHTML=headerTable;
		var serviceListTable = "<table width='100%'  border='1' cellspacing='1' cellpadding='2'  id='ServiceList'>";
		var str;
		var dropDownLovIDLabels=new Array();
    	var dropDownLovIDLabelIndex = 0;
    	var dropDownLovIDLabelsNew=new Array();
    	var dropDownLovIDLabelIndexNew = 0;
    	var parentCounter=0;
		for (i = 0 ; i < test.list.length; i++,counter++){
			//Puneet to check for disabling complete section or sub section
  			/*if(!subSectionAttributeCheck && sectionCommercial && !commercialAllowed){
  				attributeDisabled = true;
  				test.list[i].isServiceSummReadonly=1;
  				test.list[i].isServiceSummMandatory=0;
  			}else if(subSectionAttributeCheck && !commercialAllowed && test.list[i].isCommercial==1){	
  			//Puneet to check for disabling commercial attributes, if not allowed
  				attributeDisabled = true;
  				test.list[i].isServiceSummReadonly=1;
  				test.list[i].isServiceSummMandatory=0;
  			}*/
	    	serviceListTable = serviceListTable + tableRowStart;
	    	if(test.list[i].guiAlert!=null){
	    		guiAlertForConfig=test.list[i].guiAlert;
	    	}
			/*var tblRow=document.getElementById('ServiceList').insertRow();
			var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.width="41%"
			tblcol.vAlign="top";
			tblcol.style.fontSize="12px";
			tblcol.style.fontWeight="bold";*/
	    	tableDataStart = "<td align = 'left' width='41%' vAlign='top' style.fontSize='12px' style.fontWeight='bold'>";
	    	
			if(test.list[i].prodAttributeID == "2470" || test.list[i].prodAttributeID == "2476"){
				str = "<label><center>label</center></label></br>";
				str=str + test.list[i].prodAttributeLabel;
			}else 
				str= test.list[i].prodAttributeLabel;
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
			//START[13082012]AutoSuggest for BCP
			if(test.list[i].prodExpectedValue=='DROPDOWN'){
				var autoSuggProdAttVal="";
				var newprodAttVal;
				var autoSuggNewProdAttVal="";
				var prodAttVal;
				for(j=0;j<test.list[i].serviceSummary.list.length;j++){
					/*var combo = document.getElementById("prodAttVal_"+counter);
					var option = document.createElement("option");
					option.text = test.list[i].serviceSummary.list[j].serviceSummaryText;
					option.title = test.list[i].serviceSummary.list[j].serviceSummaryText;
					option.value = test.list[i].serviceSummary.list[j].serviceSummaryValues;
					//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
					if(editSolutionChangeOldProduct==1) //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10)\\{
						var comboNew = document.getElementById("newprodAttVal_"+counter);
						var optionNew = document.createElement("option");
						optionNew.text = test.list[i].serviceSummary.list[j].serviceSummaryText;
						optionNew.title = test.list[i].serviceSummary.list[j].serviceSummaryText;
						optionNew.value = test.list[i].serviceSummary.list[j].serviceSummaryValues;
					}
					try{
						combo.add(option, null); //Standard
						//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
						if(editSolutionChangeOldProduct==1){ //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10) {
							comboNew.add(optionNew, null); //Standard
						}
					}catch(error){
						combo.add(option); // IE only
						//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
						if(editSolutionChangeOldProduct==1){ //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10) {
							comboNew.add(optionNew); //Standard
						}
					}*/
					var callerWindowObj = dialogArguments;
					var subChangeType = callerWindowObj.document.getElementById('hdnSubChangeTypeID').value;
					var ChangeType = callerWindowObj.document.getElementById('HdnChangeTypeID').value;										
					//document.getElementById("prodAttVal_"+counter).value=test.list[i].prodAttVal;
					prodAttVal = test.list[i].prodAttVal;
					if(test.list[i].prodAttVal==test.list[i].serviceSummary.list[j].serviceSummaryValues){
						if(test.list[i].prodAttVal==0){
							//document.getElementById("autoSuggProdAttVal_"+counter).value="";
							autoSuggProdAttVal = "";
						}else{
							//document.getElementById("autoSuggProdAttVal_"+counter).value=test.list[i].serviceSummary.list[j].serviceSummaryText;
							autoSuggProdAttVal = test.list[i].serviceSummary.list[j].serviceSummaryText;
						}
					}
					if(editSolutionChangeOldProduct==1){ //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10)
						if(test.list[i].newProdAttVal!=null){
							//document.getElementById("newprodAttVal_"+counter).value=test.list[i].newProdAttVal;
							newprodAttVal = test.list[i].newProdAttVal;
							if(test.list[i].newProdAttVal==test.list[i].serviceSummary.list[j].serviceSummaryValues){
								if(test.list[i].newProdAttVal==0){
									//document.getElementById("autoSuggNewProdAttVal_"+counter).value="";
									autoSuggNewProdAttVal = "";
								}else{
									//document.getElementById("autoSuggNewProdAttVal_"+counter).value=test.list[i].serviceSummary.list[j].serviceSummaryText;
									autoSuggNewProdAttVal = test.list[i].serviceSummary.list[j].serviceSummaryText;
								}
							}
						}else{	
							if(subChangeType == 9 && test.list[i].serviceSummary.list[j].serviceSummaryText == "New Order"){	
								//document.getElementById("newprodAttVal_"+counter).value=2;
								newprodAttVal = 2;
							}else if(subChangeType == 8 && test.list[i].serviceSummary.list[j].serviceSummaryText == "New Order"){
								//document.getElementById("newprodAttVal_"+counter).value=1;
								newprodAttVal = 1;
							}else{
								//document.getElementById("newprodAttVal_"+counter).value=test.list[i].prodAttVal;
								newprodAttVal = test.list[i].prodAttVal;
								if(test.list[i].prodAttVal==test.list[i].serviceSummary.list[j].serviceSummaryValues){
									if(test.list[i].prodAttVal==0){
										//document.getElementById("autoSuggNewProdAttVal_"+counter).value="";
										autoSuggNewProdAttVal = "";
									}else{
										//document.getElementById("autoSuggNewProdAttVal_"+counter).value=test.list[i].serviceSummary.list[j].serviceSummaryText;
										autoSuggNewProdAttVal = test.list[i].serviceSummary.list[j].serviceSummaryText;
									}
								}
							}	
						}
					}
				}
				dropDownLovIDLabels[dropDownLovIDLabelIndex]=test.list[i].prodAttributeID+"-"+autoSuggProdAttVal;
				dropDownLovIDLabelsNew[dropDownLovIDLabelIndexNew]=test.list[i].prodAttributeID+"-"+autoSuggNewProdAttVal;
				dropDownLovIDLabelIndex++;
				dropDownLovIDLabelIndexNew++;
				//start[003]
			    /*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";*/
				//str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' onchange='fnGetDestLabelValue("+test.list[i].prodAttributeID+",this)'  class='dropdownMargin' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' zeroValueAllowed='Y' ></select> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'>";
				
				if(editSolutionChangeOldProduct==1 || subChangeType == 3 || subChangeType == 4 || subChangeType == 6 || subChangeType == 7){
					str="<input type='hidden' disabled='true' class='inputDisabled' value = '" + prodAttVal + "' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' zeroValueAllowed='Y'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:135px' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' readOnly='true' class='inputDisabled' id='autoSuggProdAttVal_" + counter + "' value='" + autoSuggProdAttVal +"' name ='autoSuggProdAttVal_" + counter + "' readOnly='true' /> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";					
					str="<input type='hidden' disabled='true' class='inputDisabled' value = '" + prodAttVal + "' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' zeroValueAllowed='Y'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:135px' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' readOnly='true' class='inputDisabled' id='autoSuggProdAttVal_" + counter + "' value='" + autoSuggProdAttVal +"' name ='autoSuggProdAttVal_" + counter + "' readOnly='true' /> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";					
				}else if(!(isView ==null || isView == "null")){
					str="<input type='hidden' class='inputDisabled' disabled='true' value = '" + prodAttVal + "' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' zeroValueAllowed='Y'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:135px' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' class='dropdownMargin' id='autoSuggProdAttVal_" + counter + "' value='" + autoSuggProdAttVal +"' name ='autoSuggProdAttVal_" + counter + "' readOnly='true' /> <a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					str="<input type='hidden' class='inputDisabled' disabled='true' value = '" + prodAttVal + "' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' zeroValueAllowed='Y'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:135px' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' class='dropdownMargin' id='autoSuggProdAttVal_" + counter + "' value='" + autoSuggProdAttVal +"' name ='autoSuggProdAttVal_" + counter + "' readOnly='true' /> <a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}else{
					//[007] Start 
					if(test.list[i].isServiceSummReadonly==1){
							str="<input type='hidden' class='dropdownMargin' value = '" + prodAttVal + "' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' zeroValueAllowed='Y'><input type='text'  disabled='disabled' onmouseover='getTip(value)' onmouseout='UnTip()' style='width:135px' class='dropdownMargin1' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' id='autoSuggProdAttVal_" + counter + "' value='" + autoSuggProdAttVal +"' name ='autoSuggProdAttVal_" + counter + "' readOnly='true' /> <a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";						
					}else{
						str="<input type='hidden' class='dropdownMargin' value = '" + prodAttVal + "' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' zeroValueAllowed='Y'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:135px' class='dropdownMargin' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' id='autoSuggProdAttVal_" + counter + "' value='" + autoSuggProdAttVal +"' name ='autoSuggProdAttVal_" + counter + "' readOnly='true' /> <a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						str="<input type='hidden' class='dropdownMargin' value = '" + prodAttVal + "' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' zeroValueAllowed='Y'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:135px' class='dropdownMargin' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' id='autoSuggProdAttVal_" + counter + "' value='" + autoSuggProdAttVal +"' name ='autoSuggProdAttVal_" + counter + "' readOnly='true' /> <a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					}
					//[007] End
				}
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				tableDataStart = "<td align = 'left' vAlign='top'>";
				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
				//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
				if(editSolutionChangeOldProduct==1){ 
				//&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10){
					/*var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";*/
					if(test.list[i].isServiceSummReadonly==1){
						str="<input type='hidden' baseCtrlType='DROPDOWN' class='dropdownMargin' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"' value = '" + newprodAttVal + "' id='newprodAttVal_" + counter + "' name='newprodAttVal_" + counter + "' value='0'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:107px' disabled='disabled' class='dropdownMargin1' readonly='true' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' value = '" + autoSuggNewProdAttVal + "' id='autoSuggNewProdAttVal_" + counter + "' name ='autoSuggNewProdAttVal_" + counter + "' zeroValueAllowed='Y' readOnly='true' /> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";					
					}else{
						str="<input type='hidden' baseCtrlType='DROPDOWN' class='dropdownMargin' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"' value = '" + newprodAttVal + "' id='newprodAttVal_" + counter + "' name='newprodAttVal_" + counter + "' value='0'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:107px' class='dropdownMargin' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' value='" + autoSuggNewProdAttVal + "' id='autoSuggNewProdAttVal_" + counter + "' name ='autoSuggNewProdAttVal_" + counter + "' zeroValueAllowed='Y' readOnly='true' /><a id='autoSuggNewProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownForNewValue(" + counter + ")'>Show</a>  <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					}
					tableDataStart = "<td align = 'left' vAlign='top'>";
					/*CellContents = str;
					tblcol.innerHTML = CellContents;*/
					str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttrNew_" + counter + "' name='comAttrNew_" + counter + "'>";
					serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
					//End[003]
				}
				if(test.list[i].isParentAtt==1){
					parentCounter=counter;
				}else if(test.list[i].parntAttId!=0){
					parentCounter=parentCounter;//if child then set parent counter as parent counter
				}else{
					parentCounter=0;
				}
			}else if(test.list[i].prodExpectedValue=='LOV'){
				var prodAttVal;
				var autoSuggProdAttVal="";
				var newprodAttVal;
				var autoSuggNewProdAttVal="";
				for(j=0;j<test.list[i].serviceSummaryLov.list.length;j++){
					//var combo = document.getElementById("prodAttVal_"+counter);
					//var option = document.createElement("option");
					//option.text = test.list[i].serviceSummary.list[j].serviceSummaryText;
					//option.title = test.list[i].serviceSummary.list[j].serviceSummaryText;
					//option.value = test.list[i].serviceSummary.list[j].serviceSummaryValues;
					//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
					if(editSolutionChangeOldProduct==1){ //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10)
						//var comboNew = document.getElementById("newprodAttVal_"+counter);
						//var optionNew = document.createElement("option");
						//optionNew.text = test.list[i].serviceSummary.list[j].serviceSummaryText;
						//optionNew.title = test.list[i].serviceSummary.list[j].serviceSummaryText;
						//optionNew.value = test.list[i].serviceSummary.list[j].serviceSummaryValues;
					}
					try{
						//combo.add(option, null); //Standard
						//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
						if(editSolutionChangeOldProduct==1){ //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10) {
							//comboNew.add(optionNew, null); //Standard
						}
					}catch(error){
						//combo.add(option); // IE only
						//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
						if(editSolutionChangeOldProduct==1){ //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10) {
							//comboNew.add(optionNew); //Standard							
						}
					}
					var callerWindowObj = dialogArguments;
					var subChangeType = callerWindowObj.document.getElementById('hdnSubChangeTypeID').value;
					var ChangeType = callerWindowObj.document.getElementById('HdnChangeTypeID').value;										
					//document.getElementById("prodAttVal_"+counter).value=test.list[i].prodAttVal;
					prodAttVal = test.list[i].prodAttVal;
					if(test.list[i].prodAttVal==test.list[i].serviceSummaryLov.list[j].serviceSummaryValues){
						if(test.list[i].prodAttVal==0){
							//document.getElementById("autoSuggProdAttVal_"+counter).value="";
							autoSuggProdAttVal="";
						}else{
							//document.getElementById("autoSuggProdAttVal_"+counter).value=test.list[i].serviceSummaryLov.list[j].serviceSummaryText;
							autoSuggProdAttVal = test.list[i].serviceSummaryLov.list[j].serviceSummaryText;
						}
					}
					//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
					if(editSolutionChangeOldProduct==1){ //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10)
						if(test.list[i].newProdAttVal!=null){
							//document.getElementById("newprodAttVal_"+counter).value=test.list[i].newProdAttVal;
							newprodAttVal = test.list[i].newProdAttVal;
						if(test.list[i].newProdAttVal==test.list[i].serviceSummaryLov.list[j].serviceSummaryValues){
							if(test.list[i].newProdAttVal==0){
								//document.getElementById("autoSuggNewProdAttVal_"+counter).value="";
								autoSuggNewProdAttVal="";
							}else{
								//document.getElementById("autoSuggNewProdAttVal_"+counter).value=test.list[i].serviceSummaryLov.list[j].serviceSummaryText;
								autoSuggNewProdAttVal=test.list[i].serviceSummaryLov.list[j].serviceSummaryText;
							}
						}	
					}else{	
						if(subChangeType == 9 && test.list[i].serviceSummaryLov.list[j].serviceSummaryText == "New Order"){	
							//document.getElementById("newprodAttVal_"+counter).value=2;
							newprodAttVal=2;
							//document.getElementById("autoSuggNewProdAttVal_"+counter).value="";
							autoSuggNewProdAttVal="";
						}else if(subChangeType == 8 && test.list[i].serviceSummaryLov.list[j].serviceSummaryText == "New Order"){
							//document.getElementById("newprodAttVal_"+counter).value=1;											
							//document.getElementById("autoSuggNewProdAttVal_"+counter).value="";		
							newprodAttVal=1;
							autoSuggNewProdAttVal="";
						}else{
							//document.getElementById("newprodAttVal_"+counter).value=test.list[i].prodAttVal;
							newprodAttVal=test.list[i].prodAttVal;
							if(test.list[i].prodAttVal==test.list[i].serviceSummaryLov.list[j].serviceSummaryValues){
								if(test.list[i].serviceSummaryLov.list[j].serviceSummaryText=="New" ||test.list[i].serviceSummaryLov.list[j].serviceSummaryText=="New Order"){
									//document.getElementById("newprodAttVal_"+counter).value=0;
									//document.getElementById("autoSuggNewProdAttVal_"+counter).value="";
									newprodAttVal = 0;
									autoSuggNewProdAttVal="";
								}else{
									if(test.list[i].prodAttVal==0){
										//document.getElementById("autoSuggNewProdAttVal_"+counter).value="";
										autoSuggNewProdAttVal = "";
									}else{
										//document.getElementById("autoSuggNewProdAttVal_"+counter).value=test.list[i].serviceSummaryLov.list[j].serviceSummaryText;
										autoSuggNewProdAttVal=test.list[i].serviceSummaryLov.list[j].serviceSummaryText;
									}
								}
							}
						}	
					}						
				}
			}
				dropDownLovIDLabels[dropDownLovIDLabelIndex]=test.list[i].prodAttributeID+"-"+autoSuggProdAttVal;
				dropDownLovIDLabelsNew[dropDownLovIDLabelIndexNew]=test.list[i].prodAttributeID+"-"+autoSuggNewProdAttVal;
				dropDownLovIDLabelIndex++;
				dropDownLovIDLabelIndexNew++;	
				//changes for clientside and serverside validation from database driven for maxlength in service summary section  by Raghu
				//added LOV condition for autosuggestion in service summary section  by Lawkush Start
				//start[003]
			    /*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";*/
				tableDataStart = "<td align = 'left' vAlign='top'>";
			//	str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.name)'  class='dropdownMargin' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' ></select> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				if(editSolutionChangeOldProduct==1 || subChangeType == 3 || subChangeType == 4 || subChangeType == 6 || subChangeType == 7){
					str="<input type='hidden' class='inputDisabled' disabled='true' value ='" + prodAttVal + "' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' ><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' style='width:135px' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' value = '" + autoSuggProdAttVal + "' class='inputDisabled' readOnly='true' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' zeroValueAllowed='Y' /> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}else if(!(isView ==null || isView == "null")){
					str="<input type='hidden' class='inputDisabled' disabled='true' value = '" + prodAttVal + "' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "'><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' style='width:135px' class='dropdownMargin' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' value = '" + autoSuggProdAttVal + "' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' readOnly='true' zeroValueAllowed='Y' /> <a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}else{
					str="<input type='hidden' class='dropdownMargin' value = '" + prodAttVal + "' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "'><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' style='width:135px' class='dropdownMargin' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' value = '" + autoSuggProdAttVal + "' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' readOnly='true' zeroValueAllowed='Y' /> <a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
				//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
				if(editSolutionChangeOldProduct==1){ //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10){
					/*var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";*/
					tableDataStart = "<td align = 'left' vAlign='top'>";
					if(test.list[i].isServiceSummReadonly==1){
						str="<input type='hidden' baseCtrlType='LOV' class='dropdownMargin' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"' value='" + newprodAttVal + "' id='newprodAttVal_" + counter + "' name='newprodAttVal_" + counter + "' value='0'><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' disabled='disabled' class='dropdownMargin1' readonly='true' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"' style='width:107px' class='dropdownMargin' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' value = '" + autoSuggNewProdAttVal + "' id='autoSuggNewProdAttVal_" + counter + "' name ='autoSuggNewProdAttVal_" + counter + "' zeroValueAllowed='Y' />  <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";					
					}else{
						//str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.name)' onchange='fnGetDestLabelValueForLOV("+test.list[i].prodAttributeID+",this)'  subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"'  class='dropdownMargin' name='newprodAttVal_" + counter + "' id='newprodAttVal_" + counter + "' zeroValueAllowed='Y' ></select> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						str="<input type='hidden' baseCtrlType='LOV' class='dropdownMargin' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"' value = '" + newprodAttVal + "' id='newprodAttVal_" + counter + "' name='newprodAttVal_" + counter + "' value='0'><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"' style='width:107px' class='dropdownMargin' counterval='"+ counter +"'  ctrltype='dropdown' configVal='"+ test.list[i].attMasterId +"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' value = '" + autoSuggNewProdAttVal + "' id='autoSuggNewProdAttVal_" + counter + "' name ='autoSuggNewProdAttVal_" + counter + "' zeroValueAllowed='Y' /><a id='autoSuggNewProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownForNewValue(" + counter + ")'>Show</a>  <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					}
					/*CellContents = str;
					tblcol.innerHTML = CellContents;*/
					str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttrNew_" + counter + "' name='comAttrNew_" + counter + "'>";
					serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
					//End[003]
				}
				if(test.list[i].isParentAtt==1){
					parentCounter=counter;
				}else if(test.list[i].parntAttId!=0){
					parentCounter=parentCounter;//if child then set parent counter as parent counter
				}else{
					parentCounter=0;
				}
			}else if(test.list[i].prodExpectedValue=='LINK'){
			//added LOV condition for autosuggestion in service summary section  by Lawkush End
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";*/
			var tblWidth;
			if(editSolutionChangeOldProduct==1){
				tblWidth = 30;
				//tblcol.width="30%"
			}else{
				//tblcol.width="41%"
				tblWidth = 41;
			}
			tableDataStart = "<td align = 'left' vAlign='top' width='" + tblWidth+ "%'>";
			//tblcol.vAlign="top";
			if(test.list[i].prodAttVal==null || test.list[i].prodAttVal=="0"){
				str="<input onmouseover='getTip(value)' value='' onmouseout='UnTip()' type='text' isSummaryReq='1' readOnly ='true' class='inputBorder1' style='width:150px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' islinkType='1'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
				str=str+"<div class='searchBg1'><a href='#' title='Select LSI No' id='serviceSummaryLink_" + counter + "' onClick='return popUpServiceSummary("+test.list[i].prodAttributeID+","+counter+",\""+test.list[i].linkPopUpId+"\")'  >...</a></div><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'><input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'>";
			}else if(!(isView ==null || isView == "null") || editSolutionChangeOldProduct==1){
				//[015]
				str="<input onmouseover='getTip(value)' value='"+test.list[i].prodAttVal+"' onmouseout='UnTip()' type='text' isSummaryReq='1' readOnly ='true' class='inputDisabled' style='width:150px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' islinkType='1'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
				str=str+"<div class='searchBg1'><a href='#' title='Select LSI No' id='serviceSummaryLink_" + counter + "' onClick='return popUpServiceSummary("+test.list[i].prodAttributeID+","+counter+",\""+test.list[i].linkPopUpId+"\")'  >...</a></div><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'><input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'>";
			}else{
				str="<input onmouseover='getTip(value)' value='"+test.list[i].prodAttVal+"' onmouseout='UnTip()' type='text' isSummaryReq='1' readOnly ='true' class='inputBorder1' style='width:150px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' islinkType='1'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
				str=str+"<div class='searchBg1'><a href='#' title='Select LSI No' id='serviceSummaryLink_" + counter + "' onClick='return popUpServiceSummary("+test.list[i].prodAttributeID+","+counter+",\""+test.list[i].linkPopUpId+"\")'  >...</a></div><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'><input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'>";
			}
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
			serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
			
			if(editSolutionChangeOldProduct==1){
				/*tblcol.innerHTML = CellContents;
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.width="29%"
				tblcol.vAlign="top";*/
				tableDataStart = "<td align = 'left' vAlign='top' width='29%'>";
				if(test.list[i].newProdAttVal==null || test.list[i].newProdAttVal=="0"){
					str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' value='"+ test.list[i].prodAttVal+ "' dmxtotal='0' readOnly ='true' class='inputBorder1' name='newprodAttVal_'" + counter + " id='newprodAttVal_" + counter + "' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"' islinkType='1'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'>";
					str=str+"<div class='searchBg1'><a href='#' title='Select LSI No' id='newserviceSummaryLink_" + counter + "' onClick='return popUpServiceSummary("+test.list[i].prodAttributeID+","+counter+",\""+test.list[i].linkPopUpId+"\")' >...</a></div><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}else{
					str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' value='"+test.list[i].newProdAttVal+"' dmxtotal='0' readOnly ='true' class='inputBorder1' name='newprodAttVal_'" + counter + " id='newprodAttVal_" + counter + "' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"'  islinkType='1'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'>";
					str=str+"<div class='searchBg1'><a href='#' title='Select LSI No' id='newserviceSummaryLink_" + counter + "' onClick='return popUpServiceSummary("+test.list[i].prodAttributeID+","+counter+",\""+test.list[i].linkPopUpId+"\")'  >...</a></div><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttrNew_" + counter + "' name='comAttrNew_" + counter + "'>";
				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
			}
			}else if(test.list[i].prodExpectedValue == 'datetime'){
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";*/
				//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
				var tblWidth;
				if(editSolutionChangeOldProduct==1){ //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10)	
					//tblcol.width="30%";
					tblWidth=30;
				}else{
					//tblcol.width="41%";
					tblWidth=41;
				}
				//tblcol.vAlign="top";
				//strA = "javascript: if(\""+ test.list[i].prodExpectedValue +"\"==\"numeric\".toUpperCase()){if(this.value.length > 0){return checknumber(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"datetime\"){if(this.value.length > 0){return checkdate(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"email\"){if(this.value.length > 0){return  validateEmail(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"varchar\".toUpperCase()){if(this.value.length > 0){return ValidateTextFieldM6(this,path)}}";
				tableDataStart = "<td align = 'left' vAlign='top' width='" + tblWidth+ "%'>";
				strA="javascript: return false";
				if(test.list[i].prodAttVal==null || test.list[i].prodAttVal=="0"){
					str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly ='true' value='' class='inputBorder1' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}else if(!(isView ==null || isView == "null") || editSolutionChangeOldProduct==1){
					str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly ='true' value='" + test.list[i].prodAttVal + "' class='inputDisabled' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}else{
					//[007] Start 
					/*if(test.list[i].isServiceSummReadonly==1){
						if(test.list[i].newProdAttVal==null || test.list[i].newProdAttVal=="0"){
							str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' value='"+ test.list[i].prodAttVal+ "' readOnly ='true' class='dropdownMargin1'  subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"' isDate='1' name='newprodAttVal_" + counter + "' id='newprodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'><img src='<%=request.getContextPath()%>/images/cal.gif' id='newprodAttImgVal_" + counter + "'   border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.newprodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_" + counter + "' id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_" + counter + "' id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_" + counter + "' id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_" + counter + "' id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_" + counter + "' id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						}else{
							str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly ='true' class='dropdownMargin1' value='"+test.list[i].newProdAttVal+"' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"' isDate='1'   name='newprodAttVal_" + counter + "' id='newprodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'><img src='<%=request.getContextPath()%>/images/cal.gif'  id='newprodAttImgVal_" + counter + "'     border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.newprodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_" + counter + "' id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_" + counter + "' id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_" + counter + "' id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_" + counter + "' id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_" + counter + "' id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						}//[003] end
					}else{*/
						str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly ='true' value='" + test.list[i].prodAttVal + "' class='inputBorder1' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					//}
					//[007] End
				}
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
				//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
				if(editSolutionChangeOldProduct==1){ //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10){			
					/*tblcol.innerHTML = CellContents;
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.width="29%"
					tblcol.vAlign="top";*/
					tableDataStart = "<td align = 'left' vAlign='top' width='29%'>";
					strA="javascript: return false";
					//start[003]
					if(test.list[i].isServiceSummReadonly==1){
						if(test.list[i].newProdAttVal==null || test.list[i].newProdAttVal=="0"){
							str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' value='"+ test.list[i].prodAttVal+ "' readOnly ='true' class='dropdownMargin1'  subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"' isDate='1' name='newprodAttVal_" + counter + "' id='newprodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'><img src='<%=request.getContextPath()%>/images/cal.gif' id='newprodAttImgVal_" + counter + "'   border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.newprodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_" + counter + "' id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_" + counter + "' id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_" + counter + "' id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_" + counter + "' id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_" + counter + "' id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						}else{
							str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly ='true' class='dropdownMargin1' value='"+test.list[i].newProdAttVal+"' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"' isDate='1'   name='newprodAttVal_" + counter + "' id='newprodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'><img src='<%=request.getContextPath()%>/images/cal.gif'  id='newprodAttImgVal_" + counter + "'     border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.newprodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_" + counter + "' id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_" + counter + "' id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_" + counter + "' id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_" + counter + "' id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_" + counter + "' id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						}//[003] end
					}else{
						if(test.list[i].newProdAttVal==null || test.list[i].newProdAttVal=="0"){
							str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' value='"+ test.list[i].prodAttVal+ "' readOnly ='true' class='inputBorder1'  subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"' isDate='1' name='newprodAttVal_" + counter + "' id='newprodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'><img src='<%=request.getContextPath()%>/images/cal.gif' id='newprodAttImgVal_" + counter + "'   border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.newprodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_" + counter + "' id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_" + counter + "' id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_" + counter + "' id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_" + counter + "' id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_" + counter + "' id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						}else{
							str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly ='true' class='inputBorder1' value='"+test.list[i].newProdAttVal+"' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"' isDate='1'   name='newprodAttVal_" + counter + "' id='newprodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'><img src='<%=request.getContextPath()%>/images/cal.gif'  id='newprodAttImgVal_" + counter + "'     border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.newprodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_" + counter + "' id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_" + counter + "' id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_" + counter + "' id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_" + counter + "' id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_" + counter + "' id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						}//[003] end
					}
					/*CellContents = str;
					tblcol.innerHTML = CellContents;*/
					str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttrNew_" + counter + "' name='comAttrNew_" + counter + "'>";
					serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
				}
			}else{
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";*/
				var tblWidth;
				if(editSolutionChangeOldProduct==1){	
					//tblcol.width="30%";
					tblWidth = 30;
				}else{
					//tblcol.width="41%";
					tblWidth=41;
				}
				//tblcol.vAlign="top";
				tableDataStart = "<td align = 'left' vAlign='top' width='" + tblWidth+ "%'>";
				//strA="javascript: return false";
				//strA = "javascript: if(\""+ test.list[i].prodExpectedValue +"\"==\"numeric\".toUpperCase()){if(this.value.length > 0){return checknumber(this)}}";
				strA = "javascript: "
							+"if(this.readOnly==false && this.disabled==false){"
								+"if(\""+ test.list[i].prodExpectedValue +"\"==\"numeric\".toUpperCase()){if(this.value.length > 0){return checknumber(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"datetime\".toUpperCase()){if(this.value.length > 0){return checkdate(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"email\".toUpperCase()){if(this.value.length > 0){return validateEmail(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"varchar\".toUpperCase()){if(this.value.length > 0){return ValidateTextFieldM6(this,pathM6)}}"
							+"}";
				
			 	//strA="javascript: return false";
				if(serviceSummaryIndValChanged && null != serviceSummaryIndVal){
					var values = attMasterIdList.split(",");
					if (values.length > 0){	
						for (var j = 0; j < values.length; j++){
							if(values[j] == test.list[i].prodAttributeID){
								test.list[i].prodAttVal = serviceSummaryIndVal;
								serviceSummaryIndValChanged = false;
								serviceSummaryIndVal = null;
								break;
							}
						}
					}
				}
				
				if(test.list[i].prodAttributeID == "2470"){ 
					if(!(isView ==null || isView == "null") || editSolutionChangeOldProduct==1){
						str="<label><center><bold>SUPPLIER</bold></center></label></br><input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly='true'  onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' value='" + test.list[i].prodAttVal + "' class='inputDisabled'  name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'>  <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					}else{
						str="<label><center><bold>SUPPLIER</bold></center></label></br><input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' value='" + test.list[i].prodAttVal + "' class='inputBorder1'  name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'>  <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					}
				}else if(test.list[i].prodAttributeID == "2476"){
					if(!(isView ==null || isView == "null") || editSolutionChangeOldProduct==1){
						str="<label><center><bold>BROADCASTER</bold></center></label></br><input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' value='" + test.list[i].prodAttVal + "' class='inputDisabled' name='prodAttVal_'" + counter + "readOnly = 'true' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'>  <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					}else{
						str="<label><center><bold>BROADCASTER</bold></center></label></br><input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' value='" + test.list[i].prodAttVal + "' class='inputBorder1' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'>  <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					}
				} 
				// if (test.list[i].prodAttributeID == "3253" || test.list[i].prodAttributeID == "3249")
				//		{
				//			str="<input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' value='" + test.list[i].prodAttVal + "'  class='inputBorder1' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'>  <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				//		}
				else{
					if(!(isView ==null || isView == "null") || editSolutionChangeOldProduct==1){
						str="<input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' value='" + test.list[i].prodAttVal + "' class='inputDisabled' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y' readOnly = 'true'>  <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					}else{
						//[007] Start 
						if(test.list[i].isServiceSummReadonly==1){
							str="<input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)'  onmouseout='UnTip()' type='text' disabled='disabled' class='dropdownMargin1' readonly='true' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"'  value='"+ test.list[i].prodAttVal+ "'  class='inputBorder1' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"'   onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						}else{
						str="<input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' value='" + test.list[i].prodAttVal + "' class='inputBorder1' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'>  <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						}
						//[007] End
					}
				}
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
				//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
				if(editSolutionChangeOldProduct==1){ //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10){			
					/*tblcol.innerHTML = CellContents;
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.width="29%"
					tblcol.vAlign="top";	*/			
					tableDataStart = "<td align = 'left' vAlign='top' width='29%'>";
					//strA="javascript: return false";
					//strA = "javascript: if(\""+ test.list[i].prodExpectedValue +"\"==\"numeric\".toUpperCase()){if(this.value.length > 0){return checknumber(this)}}";
					strA = "javascript: if(\""+ test.list[i].prodExpectedValue +"\"==\"numeric\".toUpperCase()){if(this.value.length > 0){return checknumber(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"datetime\".toUpperCase()){if(this.value.length > 0){return checkdate(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"email\".toUpperCase()){if(this.value.length > 0){return  validateEmail(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"varchar\".toUpperCase()){if(this.value.length > 0){return ValidateTextFieldM6(this,path)}}";
					if(test.list[i].isServiceSummReadonly==1){
						var tempTextReadOnlyStr="";
						if(test.list[i].newProdAttVal==null){
							tempTextReadOnlyStr=test.list[i].prodAttVal;
						}else{
							tempTextReadOnlyStr=test.list[i].newProdAttVal;
						}
						str="<input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)'  onmouseout='UnTip()' type='text' disabled='disabled' class='dropdownMargin1' readonly='true' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"'  value='"+ tempTextReadOnlyStr+ "'  dmxtotal='0' class='inputBorder1' name='newprodAttVal_'" + counter + " id='newprodAttVal_" + counter + "' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"'   onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					}else{
						//start[003]
						if(test.list[i].newProdAttVal==null){
							if(test.list[i].prodAttributeID=="1041")
								str="<input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)'  onmouseout='UnTip()' type='text' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"' onBlur='"+ strA +"' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"'  value='"+ test.list[i].prodAttVal+ "' dmxtotal='1' class='inputBorder1'   name='newprodAttVal_'" + counter + " id='newprodAttVal_" + counter + "' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"'   onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
							// else if (test.list[i].prodAttributeID == "3253" || test.list[i].prodAttributeID == "3249")
							//	 {
							//	 str="<input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)'   onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' value='"+ test.list[i].prodAttVal+ "'  dmxtotal='0' class='inputBorder1' name='newprodAttVal_'" + counter + " id='newprodAttVal_" + counter + "' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"'   onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
							//	 }
							else  if(test.list[i].prodAttributeID == "2470"){ 	
								str="<label><center><bold>SUPPLIER</bold></center></label></br><input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)'  onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"'  value='"+ test.list[i].prodAttVal+ "'  dmxtotal='0' class='inputBorder1' name='newprodAttVal_'" + counter + " id='newprodAttVal_" + counter + "' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"'   onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
							}else if(test.list[i].prodAttributeID == "2476"){ 
								str="<label><center><bold>BROADCASTER</bold></center></label></br><input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)'  onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"'  value='"+ test.list[i].prodAttVal+ "'  dmxtotal='0' class='inputBorder1' name='newprodAttVal_'" + counter + " id='newprodAttVal_" + counter + "' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"'   onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
							}else
								str="<input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)'  onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"'  value='"+ test.list[i].prodAttVal+ "'  dmxtotal='0' class='inputBorder1' name='newprodAttVal_'" + counter + " id='newprodAttVal_" + counter + "' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"'   onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						}else{  
							if(test.list[i].prodAttributeID=="1041")
								str="<input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' value='"+test.list[i].newProdAttVal+"' dmxtotal='1' class='inputBorder1' name='newprodAttVal_'" + counter + " id='newprodAttVal_" + counter + "' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
							else  if(test.list[i].prodAttributeID == "2470"){ 	
								str="<label><center><bold>SUPPLIER</bold></center></label></br><input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' value='"+test.list[i].newProdAttVal+"' dmxtotal='0' class='inputBorder1' name='newprodAttVal_'" + counter + " id='newprodAttVal_" + counter + "' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"'  onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
							}else if(test.list[i].prodAttributeID == "2476"){ 
								str="<label><center><bold>BROADCASTER</bold></center></label></br><input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' value='"+test.list[i].newProdAttVal+"' dmxtotal='0' class='inputBorder1' name='newprodAttVal_'" + counter + " id='newprodAttVal_" + counter + "' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"'  onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
							} 
							//	else if (test.list[i].prodAttributeID == "3253" || test.list[i].prodAttributeID == "3249")
							//		{
							//			str="<input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)'   onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' value='"+test.list[i].newProdAttVal+"' dmxtotal='0' class='inputBorder1' name='newprodAttVal_'" + counter + " id='newprodAttVal_" + counter + "' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"'   onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
							//		}
							else
								str="<input onkeyup='copyTextValue(this.value,this)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' value='"+test.list[i].newProdAttVal+"' dmxtotal='0' class='inputBorder1' name='newprodAttVal_'" + counter + " id='newprodAttVal_" + counter + "' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"' subchangetypeNetworkChangeEditable='"+test.list[i].subchangetypeNetworkChangeEditable+"' changeTypeSolutionChangeEditable='"+test.list[i].changetypeSolutionChangeEditable+"'   onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].attributeID+"' name='hdnAttributeValueID_'" + counter + " id='hdnAttributeValueID_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnnewProdAttVal_'" + counter + " id='hdnnewProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='newprodExpVal_'" + counter + " id='newprodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='newprodAttName_'" + counter + " id='newprodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].isServiceSummMandatory+"' name='newprodAttMandatory_'" + counter + " id='newprodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						}
					}
					//End[003]
					/*CellContents = str;
					tblcol.innerHTML = CellContents;*/
					str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttrNew_" + counter + "' name='comAttrNew_" + counter + "'>";
					serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
				}
			}
			/*if(editSolutionChangeOldProduct==1){
				if(test.list[i].prodExpectedValue=='LOV'){
		    		document.getElementById("prodAttVal_"+counter).className="inputDisabled";
		    		document.getElementById("autoSuggProdAttVal_"+counter).className="inputDisabled";
		   			document.getElementById("autoSuggProdAttVal_"+counter).readOnly=true;	
		   			document.getElementById("autoSuggProdAttLink_"+counter).style.display='none';
		  		}else{
	    			document.getElementById("prodAttVal_"+counter).className="inputDisabled";	
	    		}								
	    		//START[13082012]AutoSuggest for BCP								
				if(test.list[i].prodExpectedValue=='DROPDOWN'){
					document.getElementById("prodAttVal_"+counter).className="inputDisabled";
			    	document.getElementById("autoSuggProdAttVal_"+counter).className="inputDisabled";
			    	document.getElementById("autoSuggProdAttVal_"+counter).readOnly=true;	
			    	document.getElementById("autoSuggProdAttLink_"+counter).style.display='none';
				}else{
					document.getElementById("prodAttVal_"+counter).className="inputDisabled";	
				}
				//END[13082012]AutoSuggest for BCP			
			}
			if(subChangeType == 3 || subChangeType == 4 || subChangeType == 6 || subChangeType == 7){
				if(test.list[i].prodExpectedValue=='LOV'){
					document.getElementById("prodAttVal_"+counter).className="inputDisabled";
			    	document.getElementById("autoSuggProdAttVal_"+counter).className="inputDisabled";
			    	document.getElementById("autoSuggProdAttVal_"+counter).readOnly=true;	
			    	document.getElementById("autoSuggProdAttLink_"+counter).style.display='none';			    			
				}else{
					document.getElementById("prodAttVal_"+counter).className="inputDisabled";
					document.getElementById("prodAttVal_"+counter).disabled='true';	
				}					    										
				//START[13082012]AutoSuggest for BCP	
				if(test.list[i].prodExpectedValue=='DROPDOWN'){
			    	document.getElementById("prodAttVal_"+counter).className="inputDisabled";
			    	document.getElementById("autoSuggProdAttVal_"+counter).className="inputDisabled";
			    	document.getElementById("autoSuggProdAttVal_"+counter).readOnly=true;	
			    	document.getElementById("autoSuggProdAttLink_"+counter).style.display='none';			    			
				}else{
					document.getElementById("prodAttVal_"+counter).className="inputDisabled";
					document.getElementById("prodAttVal_"+counter).disabled='true';	
				}
				//START[13082012]AutoSuggest for BCP						    										
			}
			if( !(isView ==null || isView == "null")){
				if(document.getElementById("prodAttVal_"+counter) != null){
					document.getElementById("prodAttVal_"+counter).className="inputDisabled";
					document.getElementById("prodAttVal_"+counter).disabled='true';
			 	}
			 	if(document.getElementById("hdnnewProdAttVal_"+counter) !=null){
					document.getElementById("hdnnewProdAttVal_"+counter).className="inputDisabled";
					document.getElementById("hdnnewProdAttVal_"+counter).disabled='true';
			 	}
	 		}*/
			// added by manisha for service summary validation start defect no : 19032013001
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.width="50%"
			tblcol.vAlign="top";*/
			tableDataStart = "<td align = 'left' vAlign='top' width='50%'>";
			str="<input type='hidden' class='inputBorder2' name='txt_prod_attvalidation_" + counter + "' id='txt_prod_attvalidation_" + counter + "' value='"+test.list[i].for_validation+"' readonly='true'>";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
			serviceListTable = serviceListTable + tableRowEnd;
			// end
		}
		serviceListTable = serviceListTable + tableDataEnd;
		document.getElementById("ServiceListDiv").innerHTML=serviceListTable;
		//Puneet commenting for performance tuning
		/*for(m = 0 ; m < test.list.length; m++){
    		if(test.list[m].prodExpectedValue=='DROPDOWN' || test.list[m].prodExpectedValue=='LOV'){				
				if(document.getElementById("newprodAttVal_"+m)!=null)
					fnGetDestLabelValue_onLoad(test.list[m].prodAttributeID,document.getElementById("autoSuggNewProdAttVal_"+(m+1)).value);				
				if(document.getElementById("prodAttVal_"+m)!=null) 
					fnGetDestLabelValue_onLoad(test.list[m].prodAttributeID,document.getElementById("autoSuggProdAttVal_"+(m+1)).value);				
			}
    	}*/
		if(null != dropDownLovIDLabels && dropDownLovIDLabels.length > 0){
			fnGetDestLabelValue_onLoad(dropDownLovIDLabels, "old");
		} 
		if(null != dropDownLovIDLabelsNew && dropDownLovIDLabelsNew.length > 0){
			fnGetDestLabelValue_onLoad(dropDownLovIDLabelsNew, "new");
		}
    }
    //Added By Ashutosh for Service Summary Validation As on Date 29-Jun-2012
    initValidationForServiceSummary();
    serviceSummaryFetched = true;
    $("#ServiceListDiv").find("[ctrltype='dropdownlink']").each(function() {
		attachCSForAS($(this));
	});
    enableDisableSections(serSummSection);
    enableDisableCLEP(serSummSection);
    var serSec = new Array();
	serSec[0]="SERVICESUMMARY";
	serSec[1]="tblServiceSummary";
	if(null == roleWiseSectionDetail){
    	var callerWindowObj = dialogArguments;
    	var roleName = callerWindowObj.gb_roleID;
    	roleWiseSectionDetail = getRoleWiseSectionDetails(roleName);
    }
	enableDisableCommercialFields(serSec, roleWiseSectionDetail, lineType);
}

function getBillingInfo(){
	var hdnBillingInfo = document.getElementById("hdnBillingInfo");
	if(billingDetailsFetched || hdnBillingInfo.value!=1){
		return;
	}
	var callerWindowObj = dialogArguments;
	
	//[0010] start commented by Gunjan
	/*evaluatedCondition_PoLevelScenario=null;
	gb_mandatory_19Val=null;
	gb_mandatory_19Val_meta="not_assigned";
	gbChargesAdditionForDisabledPo=null;*/
	// [0010] end

	
	/*var tr, td, i, j, oneRecord;
	var taxationList;
	var combo = document.getElementById("txtTaxation");
	var iCountRows = combo.length;
	
	for (i = 1; i <  iCountRows; i++)
	{
	   combo.remove(1);
	}

	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
    taxationList = jsonrpc.processes.populateTaxationDetails();
    for(j=0;j<taxationList.list.length;j++)
    {
	   	var option = document.createElement("option");
  		option.text = taxationList.list[j].taxationName;
		option.title = taxationList.list[j].taxationName;
		option.value = taxationList.list[j].taxationId;
		try 
		{
			combo.add(option, null); //Standard
		}
		catch(error) 
		{
			combo.add(option); // IE only
		}
    }*/
   // loadBillingLevel();
	//loadBillingFormat();
		
		// *******************For Billing Mode Combo Filling by Ajax Start************************
 	//loadBillingMode();
 // *******************For Billing Mode Combo Filling by Ajax End************************
 
    /*var tr, td, i, j, oneRecord;
    var billingTypeList;
    var combo = document.getElementById("txtBillingType");
    var iCountRows = combo.length;
 
    for (i = 1; i <  iCountRows; i++)
    {
       combo.remove(1);
    }

	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
    billingTypeList = jsonrpc.processes.populateBillingTypeDetails();
    for(j=0;j<billingTypeList.list.length;j++)
    {
	   	var option = document.createElement("option");
	  	option.text = billingTypeList.list[j].billingTypeName;
	  	option.title = billingTypeList.list[j].billingTypeName;
		option.value = billingTypeList.list[j].billingTypeId;
		try 
		{
			combo.add(option, null); //Standard
		}
		catch(error) 
		{
			combo.add(option); // IE only
		}
   }*/
    /*var tr, td, i, j, oneRecord;
	var creditPeriodList;
	var combo = document.getElementById("txtBillingCP");
	var iCountRows = combo.length;
	
	for (i = 1; i <  iCountRows; i++)
	{
	   combo.remove(1);
	}

	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	creditPeriodList = jsonrpc.processes.getCreditPeriodDetails();
	for(j=0;j<creditPeriodList.list.length;j++)
	{ 	
		var option = document.createElement("option");
		option.text = creditPeriodList.list[j].creditPeriodName;
		option.title = creditPeriodList.list[j].creditPeriodName;
		option.value = creditPeriodList.list[j].creditPeriodId;
		try 
		{
			combo.add(option, null); //Standard
		}
			catch(error) 
		{
			combo.add(option); // IE only
		}
	}*/
	var jsBillingData =	{
		serviceProductID:serviceProductID,
		serviceId:servicesID
	};
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	
	billinglists = jsonrpc.processes.populateBillingDetails(jsBillingData);
	if(billinglists.list.length==0){
		document.getElementById('txtBillingAC').value = "0";
	}else{
		if(billinglists.list[0].isNfa==1){
			document.getElementById('chkSelectNfa').checked=true;
		}else{
			document.getElementById('chkSelectNfa').checked=false;
		}
		if(isUsageCheckValueChanged){
			isUsageCheckValueChanged = false;
		}else{
			if(billinglists.list[0].isUsage==1){
				document.getElementById('chkIsUsage').checked=true;
			}else{
				document.getElementById('chkIsUsage').checked=false;
			}
		}
		document.getElementById('txtBillingAC').value=billinglists.list[0].accountID;
		//START[13082012]AutoSuggest for BCP
		/*
		var combo11 = document.getElementById("bbPrimaryBCP");
    	var iCountRows3 = combo11.length;
    	var i;

    	for (i = 1; i <  combo11; i++){
       		combo11.remove(1);
    	}
   
	 	var test11;
		try{
			var jsData11 = {
				accountID:document.getElementById('txtBillingAC').value
			};
			//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
			test11 = jsonrpc.processes.populateBCP(jsData11);
		}catch(e){
			alert(e);
		}
   
      	var j;
    	for(j=0;j<test11.list.length;j++){
    		var option = document.createElement("option");
   			option.text = test11.list[j].bcpName;
   			option.title = test11.list[j].bcpName;
			option.value = test11.list[j].bcpID;
			try{
				combo11.add(option, null); //Standard
			}catch(error){
				combo11.add(option); // IE only
			}
    	}*/
		//END[13082012]AutoSuggest for BCP
    
		document.getElementById('BillingNChargeInfo').style.display='block';
		document.getElementById('BillingInfo').style.display='block';
		document.getElementById('txtBCPAddressSuggestService').className = "dropdownMargin";
		document.getElementById('txtBCPAddressSuggestService').disabled=false;
		$("#txtBCPAddressSuggestService").autocomplete( "enable" );
		$("#bcpAutoSuggestServiceLinkId").show();
		var poNo;
		//var neworderno=objCallerWindow.document.getElementById('poNumber').value;
		for (i = 0 ; i < billinglists.list.length; i++,counter++){
			document.getElementById('hdnBillingInfoID').value=billinglists.list[i].billingInfoID;
			document.getElementById('txtPODetailNo').value=billinglists.list[i].podetailID;
			//Puneet adding for auto suggest
			if(billinglists.list[i].poRemarks==null ||billinglists.list[i].poRemarks=="")					    	
				document.getElementById('poDetailNo').value=billinglists.list[i].custPONumber;
			else
				document.getElementById('poDetailNo').value=billinglists.list[i].custPONumber+"-"+billinglists.list[i].poRemarks;
			poNo = billinglists.list[i].podetailID;
			entityNo = billinglists.list[i].entityID;
			licCoNo = billinglists.list[i].licCompanyID;
			poNoForChking=billinglists.list[i].podetailID;
			document.getElementById('txtBillingAC').value=billinglists.list[i].accountID;
			document.getElementById('txtBillingCP').value=billinglists.list[i].creditPeriod;
			//Puneet credit period value for auto suggest
			document.getElementById('billingCP').value=billinglists.list[i].creditPeriodName;
			//fetchPODetailsEntity(poNumber,entityNo);
			//Puneet for performance tuning
			//fnFetchEntity();
			//selectPoId(document.getElementById('txtPODetailNo'));
			var entity_ID=billinglists.list[i].entityID;
			//Puneet adding the entity name for auto suggest
			document.getElementById('legalEntityTXT').value=billinglists.list[i].entityName;
			document.getElementById('txtEntity').value=entity_ID;
			/*if(document.getElementById('txtEntity').value == ""){
				document.getElementById('txtEntity').value = 0;
			}*/
			document.getElementById('txtBillingType').value=billinglists.list[i].billingType;
			$("#billingType").val(billinglists.list[i].billingTypeName);
			document.getElementById('txtBillingMode').value=billinglists.list[i].billingMode;
			//Puneet for auto suggest
			document.getElementById('billingMode').value=billinglists.list[i].billingModeName;
			document.getElementById('txtBillingformat').value=billinglists.list[i].billingformat;
			//Puneet for auto suggest
			document.getElementById('billingFormat').value=billinglists.list[i].billingFormatName;
			document.getElementById('txtPoId').value = document.getElementById('txtPODetailNo').value;
			document.getElementById('txtPODetailNo').value=poNo;
			
			document.getElementById('txtLicenceCo').value=licCoNo;
			$("#licenseCo").val(billinglists.list[i].licCompanyName);
			document.getElementById('txtTaxation').value=billinglists.list[i].taxation;
			document.getElementById('taxationID').value=billinglists.list[i].taxationName;
			document.getElementById('txtCmtPeriod').value=billinglists.list[i].commitmentPeriod;
			document.getElementById('txtBillingLvl').value=billinglists.list[i].billingLevel;
			$("#billingLevel").val(billinglists.list[i].billingLevelName);
			document.getElementById('txtPenaltyClause').value=billinglists.list[i].penaltyClause;
			//lawkush Done By Lawkush 
				//WARRANTY CLAUSE ADDED BY MANISHA START
				document.getElementById('txtWarrantClause').value=billinglists.list[i].warrantyClause;
				//WARRANTY CLAUSE ADDED BY MANISHA end
			//satyapan By nagarjuna
			document.getElementById('txtOSPTagging').value=billinglists.list[i].isOSPTagging;
			if(billinglists.list[i].isOSPTagging=="1"){
				document.getElementById('txtOSPRegNo').value=billinglists.list[i].txtOSPRegNo;
				document.getElementById('txtOSPRegDate').value=billinglists.list[i].txtOSPRegDate;
				document.getElementById("txtOSPRegNo").className = "inputBorder1";
				document.getElementById("txtOSPRegDate").className = "inputBorder1";
					
			}else{
				document.getElementById('txtOSPRegNo').value="";
				document.getElementById('txtOSPRegDate').value="";	
				document.getElementById("txtOSPRegNo").className = "inputBorder2";
				document.getElementById("txtOSPRegDate").className = "inputBorder2";
				document.getElementById('txtOSPRegNo').disabled=true;
				document.getElementById('txtOSPRegDate').disabled=true;
			}
				
				//satyapan By nagarjuna
			//document.getElementById('txtBillingPODate').value=billinglists.list[i].poDate;
			document.getElementById('txtBillingPODate').value=billinglists.list[i].custPoDate;
			document.getElementById('txtCur').value=billinglists.list[i].currencyCode;
			document.getElementById('chkIsUsage').value = billinglists.list[i].isUsage;
			document.getElementById("bbPrimaryBCP").value=billinglists.list[i].billingBCPId;
			//START[13082012]AutoSuggest for BCP
			document.getElementById("txtBCPAddressSuggest").value=billinglists.list[i].bcpName;
			//END[13082012]AutoSuggest for BCP
			document.getElementById('txtBAddress1').value = billinglists.list[i].baddress1;
			document.getElementById('txtBAddress2').value = billinglists.list[i].baddress2;
			document.getElementById('txtBAddress3').value = billinglists.list[i].baddress3;
			document.getElementById('txtBAddress4').value = billinglists.list[i].baddress4;
			document.getElementById('txtBCity').value = billinglists.list[i].bcity;
			document.getElementById('txtBState').value = billinglists.list[i].bstate;
			document.getElementById('txtBPincode').value =billinglists.list[i].bpincode;
			document.getElementById('txtBCountry').value = billinglists.list[i].bcountry;
		    document.getElementById('txtNoticePeriod').value = billinglists.list[i].noticePeriod;
			document.getElementById('txtBillingLevelNo').value = billinglists.list[i].billingLevelNo;
			document.getElementById('txtBContactNo').value =billinglists.list[i].bcontactNo;				
			document.getElementById('txtBEmailId').value = billinglists.list[i].bemailid;
			document.getElementById('designation').value = billinglists.list[i].designation;
			document.getElementById('lst_No').value = billinglists.list[i].lst_No;
			document.getElementById('lst_Date').value = billinglists.list[i].lstDate;
			document.getElementById('txtBContactName').value = billinglists.list[i].bcontactName;
			//011	Start
			document.getElementById('txtCircle').value = billinglists.list[i].revCircle;
			//011	End
				//bcp details for services ADDED BY MANISHA START
			
			document.getElementById("bbPrimaryBCPService").value=billinglists.list[i].billingBCPIDService;
			document.getElementById("txtBCPAddressSuggestService").value=billinglists.list[i].billingBCPNameService;
			document.getElementById("txtBAddress1Service").value = billinglists.list[i].bcpAddress1Service;
			document.getElementById("txtBAddress2Service").value = billinglists.list[i].bcpAddress2Service;
			document.getElementById("txtBAddress3Service").value = billinglists.list[i].bcpAddress3Service;
			document.getElementById('txtBAddress4Service').value = billinglists.list[i].bcpAddress4Service;
			document.getElementById('txtBCityService').value = billinglists.list[i].bcpcityservice;
			document.getElementById('txtBStateService').value = billinglists.list[i].bcpstateservice;
			document.getElementById('txtBPincodeService').value =billinglists.list[i].bpin_code_service;
			document.getElementById('txtBCountryService').value = billinglists.list[i].bcpcountryservice;
			document.getElementById('txtBContactNoService').value =billinglists.list[i].bcontact_no_service;				
			document.getElementById('txtBEmailIdService').value = billinglists.list[i].bemailid_service;
			document.getElementById('designationService').value = billinglists.list[i].bdesignation_service;
			document.getElementById('lst_NoService').value = billinglists.list[i].blsi_no_service;
			document.getElementById('lst_DateService').value = billinglists.list[i].blsi_date_service;
			document.getElementById('txtBContactNameService').value = billinglists.list[i].bcontactname_service;		
			document.getElementById('txtCircleService').value = billinglists.list[i].bcircle_service;
			//bcp details for services ADDED BY MANISHA end
			if(chargeRedValueChanged){
				chargeRedValueChanged = false;
			}else{
				document.getElementById('txtBillingScenario').value = billinglists.list[i].billingScenario;
				document.getElementById('hdnFxRedirectionSPID').value = billinglists.list[i].fxRedirectionSPID;
				document.getElementById('txtFxRedirectionLSI').value = billinglists.list[i].fxRedirectionLSI;
			}
			if(document.getElementById('txtTaxation').value==2){
				//Puneet commenting the code as value is directly assigned
				//populateReasonForChange();
				var changeReason = document.getElementById('changeReason');
				var txtReason = document.getElementById('txtReason');
				changeReason.value = billinglists.list[i].stdReasonId;
				txtReason.value = billinglists.list[i].reasonName;
				if(!(isView == null || isView == 'null')){
					changeReason.disabled=true;
					txtReason.disabled=true;
					document.getElementById('reasonAutoSuggestLinkId').disabled=true;
					//$("#txtReason").autocomplete( "disable" );
				}
			}else{
				var changeReason = document.getElementById("changeReason");
				var txtReason = document.getElementById("txtReason");
				changeReason.disabled=true;
				changeReason.className = "inputDisabled";
				txtReason.disabled=true;
				txtReason.className = "inputDisabled";
				document.getElementById('reasonAutoSuggestLinkId').disabled=true;
				//$("#txtReason").autocomplete( "disable" );
			}
 		}
		/*if(poNo != 0){
			fnFetchLicCompany(billinglists.list[i].entityID);
		}*/
		fnFetchEntity();
		selectPoIdAS($("#txtPODetailNo"));
 	}
 	fnFetchContractPeriod(document.getElementById('txtPODetailNo').value);
 	if(document.getElementById('hdnChargeInfo').value==1){
		document.getElementById('ChargeDetails').style.display='block';
		DrawTable('CHARGEINFOID','CHANGEVIEWCHARGETABLE');
	}else{
		document.getElementById('ChargeDetails').style.display='none';
	}
 	billingDetailsFetched = true;
 	enableDisableSections(billingSection);
 	enableDisableRedirectionLSIIfPresent(fxChargeRedirectionType,'CVPC',1,'');
 	enableDisableCLEP(billingSection);
 	var billingSec = new Array();
	billingSec[0]="BILLING";
	billingSec[1]="BillingInfo";
	
	var chargeSec = new Array();
	chargeSec[0]="CHARGE";
	chargeSec[1]="ChargeDetails";
	if(null == roleWiseSectionDetail){
    	var callerWindowObj = dialogArguments;
    	var roleName = callerWindowObj.gb_roleID;
    	roleWiseSectionDetail = getRoleWiseSectionDetails(roleName);
    }
	if(document.getElementById('hdnComponentInfo').value==0 && document.getElementById('hdnChargeInfo').value == 1){
		selectAllCheckBoxEnableDisable(1);
	}
	enableDisableCommercialFields(billingSec, roleWiseSectionDetail, lineType);
	enableDisableCommercialFields(chargeSec, roleWiseSectionDetail, lineType);
	//now going to disable fields if charge is linked with disconnected charge
	disableOBLinkeChargeRow(linkChargeArry);
	getDisconnectLinkedCharge();
}
function getHardwareDetails(){
	if(hardwareDetailsFetched || document.getElementById("hdnHardwareInfo").value!=1){
		return;
	}
	//if Hardware
	$("#txtBCPAddressSuggestService").attr('disabled', 'disabled');		
	$("#bcpAutoSuggestServiceLinkId").hide();
	document.getElementById('txtBCPAddressSuggestService').className = "inputBorder2";
    //populateStore(path);
    /*var comboHType = document.getElementById("txtHtype");
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
    var HType = jsonrpc.processes.populateHardwareType();
    for(j=0;j<HType.list.length;j++){
    	var option = document.createElement("option");
   		option.text = HType.list[j].hardwaretypeName;
   		option.title = HType.list[j].hardwaretypeName;
		option.value = HType.list[j].hardwaretypeId;
		try{
			comboHType.add(option, null); //Standard
		}catch(error){
			comboHType.add(option); // IE only
		}
    }
    document.getElementById("txtHtype").value = 0;
	document.getElementById("txtHtype").selectedIndex=0;*/	
	//Puneet commenting as it is converted to auto suggest
   /* var comboForms = document.getElementById("txtform");
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
    var formType = jsonrpc.processes.formAvailable();
    for(j=0;j<formType.list.length;j++){
    	var option = document.createElement("option");
   		option.text = formType.list[j].formName;
   		option.title = formType.list[j].formName;
		option.value = formType.list[j].formId;
		try{
			comboForms.add(option, null); //Standard
		}catch(error){
			comboForms.add(option); // IE only
		}
    }
	document.getElementById("txtform").selectedIndex=0;*/	
    var comboSaleNature = document.getElementById("txtNSale");
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
    var saleNature = jsonrpc.processes.natureOfSale();
     
    for(j=0;j<saleNature.list.length;j++){
    	var option = document.createElement("option");
   		option.text = saleNature.list[j].saleNatureName;
   		option.title = saleNature.list[j].saleNatureName;
		option.value = saleNature.list[j].saleNatureId;
		try{
			comboSaleNature.add(option, null); //Standard
		}catch(error){
			comboSaleNature.add(option); // IE only
		}
    }
    
	document.getElementById("txtNSale").selectedIndex=0;	
	 /*Puneet commenting as the sale type is converted to auto suggest
    if(document.getElementById("txtBillingformat").value != 0){
    	var comboSaleType = document.getElementById("txtTSale");
    	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
    	var saleType = jsonrpc.processes.typeOfSale(document.getElementById("txtBillingformat").value);
    	for(j=0;j<saleType.list.length;j++){
    		var option = document.createElement("option");
    		option.text = saleType.list[j].saleTypeName;
    		option.title = saleType.list[j].saleTypeName;
    		option.value = saleType.list[j].saleTypeId;
    		try{
    			comboSaleType.add(option, null); //Standard
    		}catch(error){
    			comboSaleType.add(option); // IE only
    		}
    	}
    	document.getElementById("txtTSale").selectedIndex=0;	
    }*/
	var test;
    var combo11 = document.getElementById("txtStartDateLogic");
    var combo22 = document.getElementById("txtEndDateLogic");
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	
    test = jsonrpc.processes.populateHDateLogic();
	
	for(j=0;j<test.list.length;j++){
    	if(test.list[j].section=='START'){
		    	var option = document.createElement("option");
		    	option.text = test.list[j].dateLogicValue;
		    	option.title = test.list[j].dateLogicValue;
				option.value = test.list[j].dateLogicValue;			
				try 
				{
				combo11.add(option, null); //Standard
				}
				catch(error) 
				{
				combo11.add(option); // IE only
				}
		}
		if(test.list[j].section=='END'){
		    	var option = document.createElement("option");
		    	option.text = test.list[j].dateLogicValue;
		    	option.title = test.list[j].dateLogicValue;
				option.value = test.list[j].dateLogicValue;			
				try 
				{
				combo22.add(option, null); //Standard
				}
				catch(error) 
				{
				combo22.add(option); // IE only
				}
		}
    }	   
  
    /*var combo2 = document.getElementById("txtdispatchAddress");
    var jsData2 =			
    {
		accountID:document.getElementById('txtBillingAC').value
	};
	jsonrpc = new JSONRpcClient("<=request.getContextPath()%>" + "/JSON-RPC");
	
    test2 = jsonrpc.processes.populateDispatchCode(jsData2);
     
    for(j=0;j<test2.list.length;j++)
    {
    	var option = document.createElement("option");
   		option.text = test2.list[j].dispatchAddressName;
   		option.title = test2.list[j].dispatchAddressName;
		option.value = test2.list[j].dispatchAddressID;
		try 
		{ if(option.value == 0){
			option.text= 'Select Dispatch Address Code';
			option.title= 'Select Dispatch Address Code';
			option.value=0;
			}else {
			combo2.add(option, null); //Standard
			}
		}
		catch(error) 
		{
			combo2.add(option); // IE only
		}
    }*/
    var jsBillingData =			
    {		serviceId:servicesID,
		serviceProductID:serviceProductID
	};
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");

    hardwarelists = jsonrpc.processes.populateHardwareDetails(jsBillingData);
    if(hardwarelists.list.length==0){
    	  document.getElementById('HardwareDetails').style.display='none';
    }else{
    	document.getElementById('HardwareDetails').style.display='block';
    	for (i = 0 ; i < hardwarelists.list.length; i++,counter++){
	    	document.getElementById('hdnHardwareInfoID').value=hardwarelists.list[i].hardwareDetailID;
	    	document.getElementById('txtdispatchAddress').value=hardwarelists.list[i].selectedDispatchID;
	    	// start :added for autusuggestdispatch
	    	document.getElementById('txtDispatchAddressSuggest').value=hardwarelists.list[i].dispatchName;
	    	
	    	if(hardwarelists.list[i].selectedDispatchID != 0){
	    		getDispatchAddress();
	    	}
	    	// end
			document.getElementById('txtStore').value=hardwarelists.list[i].selectedStoreID;
			$("#txtStoreText").val(hardwarelists.list[i].storeName);
			storeName=hardwarelists.list[i].selectedStoreID;
			storeNameVal = hardwarelists.list[i].storeName;
			document.getElementById('txtform').value=hardwarelists.list[i].formAvailable;
			document.getElementById('textFormTXT').value=hardwarelists.list[i].formName;
			document.getElementById('txtHtype').value=hardwarelists.list[i].hardwareType;
			$('#hTypeText').val(hardwarelists.list[i].hardwaretypeName);
			document.getElementById('txtNSale').value=hardwarelists.list[i].saleNature;
			document.getElementById('txtTSale').value=hardwarelists.list[i].saleType;
			document.getElementById('textSaleType').value=hardwarelists.list[i].saleTypeName;
			document.getElementById('txtStartDateLogic').value=hardwarelists.list[i].startDateLogic;
			document.getElementById('txtEndDateLogic').value=hardwarelists.list[i].endDateLogic;
			if(hardwarelists.list[i].startDate==null){
				document.getElementById('txtStartDate').value='';
			}else
				document.getElementById('txtStartDate').value=hardwarelists.list[i].startDate;
			if(hardwarelists.list[i].endDate==null){
				document.getElementById('txtEndDate').value='';
			}else
				document.getElementById('txtEndDate').value=hardwarelists.list[i].endDate;	
			document.getElementById('txtHStartMonth').value=hardwarelists.list[i].txtStartMonth;
			document.getElementById('txtHStartDays').value=hardwarelists.list[i].txtStartDays;
			document.getElementById('txtHEndMonth').value=hardwarelists.list[i].txtEndMonth;
			document.getElementById('txtHEndDays').value=hardwarelists.list[i].txtEndDays;
			document.getElementById('txtHExtMonth').value=hardwarelists.list[i].txtExtMonth;
			document.getElementById('txtHExtDays').value=hardwarelists.list[i].txtExtDays;
			//[004] start
			document.getElementById('txtDispatchContactName').value=hardwarelists.list[i].dispatchContactName;
			//[004] end
			
			if(hardwarelists.list[i].txtExtDate==null){
				document.getElementById('txtHExtDate').value='';
			}else
				document.getElementById('txtHExtDate').value=hardwarelists.list[i].txtExtDate;
			refreshTaxRate(path);	    	
    	}
    }
    fnFetchEntity();
    hardwareDetailsFetched = true;
    enableDisableSections(hardwareSection);
    enableDisableCLEP(hardwareSection);
    var hardwareSec = new Array();
	hardwareSec[0]="HARDWARE";
	hardwareSec[1]="HardwareDetails";
	if(null == roleWiseSectionDetail){
    	var callerWindowObj = dialogArguments;
    	var roleName = callerWindowObj.gb_roleID;
    	roleWiseSectionDetail = getRoleWiseSectionDetails(roleName);
    }
	enableDisableCommercialFields(hardwareSec, roleWiseSectionDetail, lineType);
}

function getLocationDetails(){
	if(locationDetailsFetched || document.getElementById('hdnLocationInfo').value !=1){
		return;
	}
	document.getElementById('txtPAddress').value="";
	document.getElementById('txtSAddress').value="";
	document.getElementById('txtPAddress1').value="";
	document.getElementById('txtPAddress2').value="";
	document.getElementById('txtPAddress3').value="";
	document.getElementById('txtPAddress4').value="";
	document.getElementById('txtPCity').value="";
	document.getElementById('txtPState').value="";
	document.getElementById('txtPPincode').value="";
	document.getElementById('txtPCountry').value="";
	document.getElementById('txtSAddress1').value="";
	document.getElementById('txtSAddress2').value="";
	document.getElementById('txtSAddress3').value="";
	document.getElementById('txtSAddress4').value="";
	document.getElementById('txtSCity').value="";
	document.getElementById('txtSState').value="";
	document.getElementById('txtSPincode').value="";
	document.getElementById('txtSCountry').value="";
	document.getElementById('txtPContactNo').value="";
	document.getElementById('txtSContactNo').value="";
	document.getElementById('txtPEmailId').value="";
	document.getElementById('txtSEmailId').value="";
	
	//  Start [006]
	/*
	
    var combo3 = document.getElementById("ddPrimaryBCP");
    var iCountRows3 = combo3.length;

    for (i = 1; i <  iCountRows3; i++)
    {
       combo3.remove(1);
    }
   
    var jsData3 =			
    {
		accountID:document.getElementById('txtBillingAC').value
	};
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	
    var test3 = jsonrpc.processes.populateBCPWithDispatch(jsData3);
     
    for(j=0;j<test3.list.length;j++)
    {
    	
    	var option = document.createElement("option");
   		option.text = test3.list[j].bcpName;
   		option.title = test3.list[j].bcpName;
		option.value = test3.list[j].bcpID;
		try 
		{
			combo3.add(option, null); //Standard
		}
		catch(error) 
		{
			combo3.add(option); // IE only
		}
    }
    
    var combo4 = document.getElementById("ddSecondaryBCP");
    var iCountRows2 = combo4.length;

    for (i = 1; i <  iCountRows2; i++)
    {
       combo4.remove(1);
    }
    var jsData4 =			
    {
		accountID:document.getElementById('txtBillingAC').value
	};
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	
    var test4 = jsonrpc.processes.populateBCPWithDispatch(jsData4);
    for(j=0;j<test4.list.length;j++)
    {
    	var option = document.createElement("option");
   		option.text = test4.list[j].bcpName;
   		option.title = test4.list[j].bcpName;
		option.value = test4.list[j].bcpID;
		try 
		{
			combo4.add(option, null); //Standard
		}
		catch(error) 
		{
			combo4.add(option); // IE only
		}
    }
    
     */
    //  End [006]
    var jsLocationData = {
    		serviceId:servicesID,
		serviceProductID:serviceProductID
	};
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");

    locationlists = jsonrpc.processes.populateLocationDetails(jsLocationData);
    if(locationlists.list.length==0){
    	  document.getElementById('ServiceLocation').style.display='none';
    }else
    {
    	var callerWindowObj = dialogArguments;
    	document.getElementById('ServiceLocation').style.display='block';
    	serviceTypeId=callerWindowObj.document.getElementById('hdnservicetypeid').value;
			if(serviceTypeId==4){
				document.getElementById('onlyNetworkLoc').style.display='none';
    		document.getElementById('allLoc').style.display='block';
    	}else{
    		document.getElementById('allLoc').style.display='block';
    		document.getElementById('onlyNetworkLoc').style.display='none';
    	}
		var	sendToM6Check = jsonrpc1.processes.getM6LineItemCheck(serviceDetailID);
    	for (i = 0 ; i < locationlists.list.length; i++,counter++){
	    	document.getElementById('txtFAddress').value=locationlists.list[i].faddress;
	    	document.getElementById('txtToAddress').value=locationlists.list[i].toaddress;
	    	document.getElementById('hdnServiceInfoID').value=locationlists.list[i].locationInfoID;
	    	document.getElementById('ddPrimaryLocType').value=locationlists.list[i].selectedPriLocType;
	    	selectPrimaryLocation(0, sendToM6Check);
			document.getElementById('ddSecondaryLocType').value=locationlists.list[i].selectedSecLocType;
			selectSecondaryLocation(0, sendToM6Check);
			if(locationlists.list[i].selectedPNLocation!=0){
				document.getElementById('ddPNLocation').value=locationlists.list[i].selectedPNLocation;
				FetchPNLocationAddress();
			}else{
				document.getElementById('ddPrimaryBCP').value=locationlists.list[i].selectedPrimaryBCP;
				FetchPriBCPDetails();
			}
			if(locationlists.list[i].selectedSNLocation!=0){
				document.getElementById('ddSNLocation').value=locationlists.list[i].selectedSNLocation;
				FetchSNLocationAddress();
			}else{
				document.getElementById('ddSecondaryBCP').value=locationlists.list[i].selectedSecBCP;
				FetchSecBCPDetails();
			}
    	}
    }
    locationDetailsFetched = true;
    enableDisableSections(serviceLocSection);
    enableDisableCLEP(serviceLocSection);
    var locSec = new Array();
	locSec[0]="SERVICELOCATION";
	locSec[1]="ServiceLocation";
	if(null == roleWiseSectionDetail){
    	var callerWindowObj = dialogArguments;
    	var roleName = callerWindowObj.gb_roleID;
    	roleWiseSectionDetail = getRoleWiseSectionDetails(roleName);
    }
	enableDisableCommercialFields(locSec, roleWiseSectionDetail, lineType);
}

function getServiceAttributes(){
	//---------------below code find orderStage for enable disable after publish order ---------------
	var objCallerWindow = dialogArguments;
	var orderNo = objCallerWindow.document.getElementById('poNumber').value;
	var subChangeType = objCallerWindow.document.getElementById('hdnSubChangeTypeID').value;
	//var jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleName,userId);	
	orderStage=orderDetails.list[0].stageName;
	if((!(isView ==null || isView=="null") && isView==1)||(orderStage=="Billing Trigger"||orderStage=="Published")){
		document.getElementById("btnAddComponents").disabled=true;
		document.getElementById("btnDeleteComponents").disabled=true;
	}
	//---------------above code find orderStage for enable disable after publish order ---------------
	if(hdnChangeTypeId==3 || hdnChangeTypeId==1 ){		
		if(hdnChangeTypeId==3)
			document.getElementById('saveImage').disabled=true;	
		document.getElementById('Network/POPLocation').disabled=true;	
		document.getElementById('Network/POPLocation1').disabled=true;	
	}
	//Vijay add condition for demo-regularize 
	//alert("Vijay. Checking that subChangeType has value "+ subChangeType +"After check delete this alert ");
	if(hdnChangeTypeId==5 || (hdnChangeTypeId==4 && subChangeType==12)){		
		if(hdnChangeTypeId==5 || hdnChangeTypeId==4)
			document.getElementById('Network/POPLocation').disabled=true;	
		document.getElementById('Network/POPLocation1').disabled=true;	
	}
	editSolutionChangeOldProduct=0;
	changeSubTypeID=0;
	isOldLineItem_RateRenewal=0;
	var jsHdnData ={		
		serviceProductID:serviceProductID,
		poNumber:changeOrderNo
	};
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	changeSubTypeID = jsonrpc.processes.getChangeSubTypeID(jsHdnData);
		
	fetchFeildList(changeSubTypeID);
	document.getElementById("hdnServiceDetailID").value=serviceDetailID;
	document.getElementById('txtProductName').value=productName;
	document.getElementById('txtHdnServiceName').value=serviceName;

	var jsHdnData ={
		serviceDetailID:serviceDetailID,
		serviceProductID:serviceProductID
	};
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	var testHdnVal = jsonrpc.processes.populateHdnProductCatelogValue(jsHdnData);
	var hdnServiceSummary = document.getElementById('hdnServiceSummary');
    var hdnBillingInfo = document.getElementById('hdnBillingInfo');
    var hdnChargeInfo = document.getElementById('hdnChargeInfo');
    var hdnLocationInfo = document.getElementById('hdnLocationInfo');
    var hdnHardwareInfo = document.getElementById('hdnHardwareInfo');
    var hdnComponentInfo = document.getElementById('hdnComponentInfo');
    var hdnConfigValue = document.getElementById('hdnConfigValue');
    
	hdnServiceSummary.value = testHdnVal.list[0].serviceInfoValue;
    hdnBillingInfo.value = testHdnVal.list[0].billingInfoValue;
    hdnChargeInfo.value = testHdnVal.list[0].chargeInfoValue;
    hdnLocationInfo.value = testHdnVal.list[0].locationInfoValue;
    hdnHardwareInfo.value = testHdnVal.list[0].hardwareInfoValue;
    hdnComponentInfo.value = testHdnVal.list[0].componentInfoValue;
    hdnConfigValue.value=testHdnVal.list[0].configValue;
	resetProducCatalogueFlags();
    //for fetching hdn Values Ends
	//--------To find config value based on servicedetailid and set config value on product catelog page----------
	//var jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	
    var jsConfigData ={
		serviceDetailID:serviceDetailID,
		serviceId:servicesID,
		configValue:testHdnVal.list[0].configValue
	};
	var configValueList=jsonrpc.processes.fetchConfigValue(jsConfigData);
	fxChargeRedirectionType=configValueList.fxChargeRedirectionType;
	fxChargeRedirectionTypeCumulative=configValueList.fxChargeRedirectionTypeCumulative;
	//start[009]
	attFLI_PO_DisableValue=configValueList.fli_po_disable;
	//end[009]
	//-------------------------------------------------------------------------------------------------------------
    var additionalNodeFlag1 = additionalNodeFlagCheck();
    if(additionalNodeFlag1 == 1){
    	document.getElementById('hdnLocationInfo').value = 0;
    	document.getElementById('hdnServiceSummary').value = 0;
    }
	/*if(document.getElementById('hdnBillingInfo').value == 0)
    {
     document.getElementById('saveImage').disabled=true;
    }
    else
    {
     document.getElementById('saveImage').disabled=false;
    }*/
    if((hdnChangeTypeId==2 && (changeSubTypeID==8 || changeSubTypeID==9 || changeSubTypeID==10)) || (hdnChangeTypeId==1 && changeSubTypeID==20)){
		editSolutionChangeOldProduct=1;
	}
	if(hdnChangeTypeId==5 && changeSubTypeID==5){
		isOldLineItem_RateRenewal=1;
	}
	if(hdnServiceSummary.value==1){
		document.getElementById('serviceType').style.display='block';
		document.getElementById('lineItemServiceSummary').style.display='block';
		//getServiceSummary();
	}else{
		document.getElementById('serviceType').style.display='none';
		document.getElementById('lineItemServiceSummary').style.display='none';
	}

    if(document.getElementById('hdnBillingInfo').value==1){
    	document.getElementById('BillingNChargeInfo').style.display='block';
    	document.getElementById('BillingInfo').style.display='block';
    	//getBillingInfo();
    }else{	
		document.getElementById('txtBillingMode').value="0";
		document.getElementById('billingMode').value="Select Billing Mode";
		document.getElementById('txtCmtPeriod').value=0;
		document.getElementById('txtPenaltyClause').value="0";
		document.getElementById('hdnBillingInfoID').value=0;
		document.getElementById('txtBillingAC').value=0;
		document.getElementById('BillingNChargeInfo').style.display='none';
		document.getElementById('BillingInfo').style.display='none';
	}
    
    if(document.getElementById('hdnComponentInfo').value==1){
		document.getElementById('Components').style.display='block';
		//getComponentAttribute();
	}else{
		document.getElementById('Components').style.display='none';
	}
	
    if(document.getElementById('hdnHardwareInfo').value==1){
    	document.getElementById('HardwareDetails').style.display='block';
    	//getHardwareDetails();
    }else{	
    	document.getElementById('txtform').value="0";
    	document.getElementById('textFormTXT').value="Select Form";
		document.getElementById('txtHtype').value="0";
		$('#hTypeText').val("Select Hardware Type");
		document.getElementById('txtNSale').value="0";
		document.getElementById('txtTSale').value="0";
		document.getElementById('textSaleType').value="Select Type Of Sale";
		document.getElementById('hdnHardwareInfoID').value=0;
		document.getElementById('HardwareDetails').style.display='none';
    }
    
   if(document.getElementById('hdnLocationInfo').value==1){
	   document.getElementById('ServiceLocation').style.display='block';
	  // getLocationDetails();	 
   }else{
	   document.getElementById('ServiceLocation').style.display='none';
   }
	enableDisableSections(null);
	
	var callerWindowObj = dialogArguments;
	if(callerWindowObj.document.getElementById('isBillingTriggerReady').value=="ENABLE"){
		document.getElementById('saveImage').style.display="none";
	}else{
		document.getElementById('saveImage').style.display="block";
	}
	if(hdnChangeTypeId==3){
		document.getElementById('saveImage').style.display="none";
	}
	if(hdnChangeTypeId==3 || hdnChangeTypeId==1 ){
		if(document.getElementById('hdnComponentInfo').value==1){
		   	document.getElementById('btnAddComponents').disabled=true;
		   	document.getElementById('btnDeleteComponents').disabled=true;
		}
	}
	enableDisableRedirectionLSIIfPresent(fxChargeRedirectionType,'CVPC',1,'');
	//----------------[ Start CLEP2012 ]--------------------------------
	enableDisableCLEP(null);
	//----------------[ End CLEP2012 ]--------------------------------
	//Puneet commentingit as taking it to getBillingDetails
	/*if(document.getElementById('hdnComponentInfo').value==0 && document.getElementById('hdnChargeInfo').value == 1){
		selectAllCheckBoxEnableDisable(1);
	}*/
	//================================================================
	//By Lawkush
	//called getAutoSuggest() and AttachCSStoAutoSuggestButton() for autosuggestion
	//getAutoSuggest();	
	AttachCSStoAutoSuggestButton();
	//================================================================
}
//Start Anil Kumar[21-June-2013]:Changes when charge disconnect then not whole page refresh only charge section refresh of charge status
function disconnectCharge(chargesID,disconnectionTyped,index,chargestype,rcActiveDateCrossed,nrcActiveDateCrossed,chargesEndDateLogic,rcInactiveDateCrossed, isAlertDispaly) {
	var status;
		//[017] commented code for disconnection type=1
			/*if(disconnectionTyped == 1) {
				if(isAlertDispaly==1){
				if(window.confirm(" Do you want to INACTIVE the charge ?")){
					//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
				 	status = jsonrpc.processes.disconnectCharge(chargesID,changeOrderNo,disconnectionTyped);
				 	if( status == 0) {						
						document.getElementById('txtChargeStatus'+index).value='Inactive';								
						document.getElementById('disconnchargeButton'+index).onclick=function() {alreadyDisconnectCharge(chargesID,1,index,chargestype,rcActiveDateCrossed,nrcActiveDateCrossed,chargesEndDateLogic,rcInactiveDateCrossed)};																				
						alert("Charge Disconnected");															
					}
				}				
				}else{
					//by pass the alert for 'disconnect and copy charge' functionality
					status = jsonrpc.processes.disconnectCharge(chargesID,changeOrderNo,disconnectionTyped);
				 	if( status == 0) {						
						document.getElementById('txtChargeStatus'+index).value='Inactive';								
						document.getElementById('disconnchargeButton'+index).onclick=function() {alreadyDisconnectCharge(chargesID,1,index,chargestype,rcActiveDateCrossed,nrcActiveDateCrossed,chargesEndDateLogic,rcInactiveDateCrossed)};																				
					}
				}
			} else */
		//[017] end
		//[017] start
			if (disconnectionTyped == 2) {
				if(isAlertDispaly==1){
					if(window.confirm("  DO you want to CLOSE the Charge ?")){				
						//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
					 	status = jsonrpc.processes.disconnectCharge(chargesID,changeOrderNo,disconnectionTyped);
					 	if(status == 0) {								 	
							document.getElementById('txtChargeStatus'+index).value='Close';								
							document.getElementById('disconnchargeButton'+index).onclick=function() {alreadyDisconnectCharge(chargesID,2,index,chargestype,rcActiveDateCrossed,nrcActiveDateCrossed,chargesEndDateLogic,rcInactiveDateCrossed)};						
							alert("Charge Disconnected");																	
						}
					}
				}else{
					//by pass the alert for 'disconnect and copy charge' functionality
					status = jsonrpc.processes.disconnectCharge(chargesID,changeOrderNo,disconnectionTyped);
				 	if(status == 0) {								 	
						document.getElementById('txtChargeStatus'+index).value='Close';								
						document.getElementById('disconnchargeButton'+index).onclick=function() {alreadyDisconnectCharge(chargesID,2,index,chargestype,rcActiveDateCrossed,nrcActiveDateCrossed,chargesEndDateLogic,rcInactiveDateCrossed)};						
					}
				}				
			}						
return false;
}
function disconnectComponent(componentinfoID,disconnectionType,counter) {
	var status=-1;
	
			if(disconnectionType == 1) {
				if(window.confirm(" Do you want to INACTIVE the component ?")){
					//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
					var inactive="INACTIVE";
				 	statusList = jsonrpc.processes.fndisconnectComponent(componentinfoID,inactive,"N");		
				 	status=statusList.returnStatus;	
				 	if( status == 0) {						
							document.getElementById('status'+counter).value=' CHANGED';		
							document.getElementById('textCompEndDateLogic'+counter).value=' Inactive';								
							document.getElementById('txtEndDays'+counter).value=0;						
							document.getElementById('txtEndMonth'+counter).value=0;
							document.getElementById('disconnButton'+counter).onclick=function() {alreadyDisconnectComponent(componentinfoID,1,0,counter)};																				
							alert("Component Disconnected");
					}
				}				
			}
			else if (disconnectionType == 2) {
				if(window.confirm("  DO you want to CLOSE the component ?")){
					//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
					var close="CLOSE";
				 	statusList = jsonrpc.processes.fndisconnectComponent(componentinfoID,close,"N");	
				 	status=statusList.returnStatus;	
				 	if(status == 0) {								 	
							document.getElementById('status'+counter).value=' CHANGED';		
							document.getElementById('textCompEndDateLogic'+counter).value=' Close';								
							document.getElementById('txtEndDays'+counter).value=0;						
							document.getElementById('txtEndMonth'+counter).value=0;
							document.getElementById('disconnButton'+counter).onclick=function() {alreadyDisconnectComponent(componentinfoID,0,1,counter)};						
							alert("Component Disconnected");	
					}
				}
			}						
			else {
				return;
			}
}
//Start Anil Kumar[21-June-2013]:Changes when charge disconnect then not whole page refresh only charge section refresh of charge status
function alreadyDisconnectCharge(chargeID,disconnectionType,chargeIndexVal,chargetype,rcActiveDateCross,nrcActiveDateCross,chargeEndDateLogic,rcInactiveDateCross)
{	
		if(window.confirm(" Charges already Disconnected DO you want to Revert the Changes ?"))
		{
			var flagOfDeleteLinkCharge = true; // by default this value would be true
			
			/*Find this charge is linked to an new charge if yes the show a confirmation alert and if no charge find the go ahead*/
			if(findDisconnectLinkedCharge(chargeID)){
				flagOfDeleteLinkCharge = false;
				if(window.confirm(" This charge is linked to a New Charge, so the new charge is also going to delete. \nDO you want to continue? "))
				{
					//set the flag of confirmation
					flagOfDeleteLinkCharge = true;
				}
			}
			 if(flagOfDeleteLinkCharge){
				//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
				 status = jsonrpc.processes.revertDisconnectCharge(chargeID,changeOrderNo,disconnectionType);
				 if(status == 0) {
					 //[0002] starts
					 if(chargeEndDateLogic==1)
						 document.getElementById('txtChargeStatus'+chargeIndexVal).value='Billing Trigger Date';
					 else if(chargeEndDateLogic==2)
						 document.getElementById('txtChargeStatus'+chargeIndexVal).value='Till Disconnection';
					 else if(chargeEndDateLogic==3)
						 document.getElementById('txtChargeStatus'+chargeIndexVal).value='One Time Charge';
				     //[0002] ends
				 	//document.getElementById('txtChargeStatus'+chargeIndexVal).value='Active';
				 	
				 			if(chargetype == 1  && rcActiveDateCross == '1')// changes by Gunjan as Inactive charges will be marked as close
							{							
							document.getElementById('disconnchargeButton'+chargeIndexVal).onclick=function() {disconnectCharge(chargeID,2,chargeIndexVal,chargetype,rcActiveDateCross,nrcActiveDateCross,chargeEndDateLogic,rcInactiveDateCross,1)};
								isDisconnectionChargeButtonCreated=1;
								alert("Charge reverted!!");								
							}
							else if (chargetype == 2  && nrcActiveDateCross == '1')
							{							
							document.getElementById('disconnchargeButton'+chargeIndexVal).onclick=function() {disconnectCharge(chargeID,1,chargeIndexVal,chargetype,rcActiveDateCross,nrcActiveDateCross,chargeEndDateLogic,rcInactiveDateCross,1)};
								isDisconnectionChargeButtonCreated=1;
								alert("Charge reverted!!");								
							}
							else if(chargetype == 1  && rcActiveDateCross == '0')
							{ 
								if(chargeEndDateLogic == '2')
								{								
								document.getElementById('disconnchargeButton'+chargeIndexVal).onclick=function() {disconnectCharge(chargeID,2,chargeIndexVal,chargetype,rcActiveDateCross,nrcActiveDateCross,chargeEndDateLogic,rcInactiveDateCross,1)};
									isDisconnectionChargeButtonCreated=1;
									alert("Charge reverted!!");	
									document.getElementById("idChargesAddBtn").focus();
								} 							
								else if(chargeEndDateLogic == '1' && rcInactiveDateCross == '1')
								{								
								document.getElementById('disconnchargeButton'+chargeIndexVal).onclick=function() {disconnectCharge(chargeID,2,chargeIndexVal,chargetype,rcActiveDateCross,nrcActiveDateCross,chargeEndDateLogic,rcInactiveDateCross,1)};						
									isDisconnectionChargeButtonCreated=1;
									alert("Charge reverted!!");	
									document.getElementById("idChargesAddBtn").focus();
								}
								else if(chargeEndDateLogic == '1' && rcInactiveDateCross=='0')
								{
									document.getElementById('disconnchargeButton'+chargeIndexVal).style.display='none';
									alert("Charge reverted!!");	
								}
							} 																																									
					//resetChargesRows();
					//getServiceAttributes();
					 	removeOBPKChargeLinking(chargeID);//remove the OB PK charge linking if exist with this charge 
					 	deleteDisconnectLinkedCharge(chargeID); //remove from the array which contain the disconnected linked charge list
					
					}
				}			
			}
	return false;
}

function alreadyDisconnectComponent(compInfoId,isInActive,isClose,indexval)
{
	if(window.confirm("Component already disconnected!\nDO you want to revert?")){		
		statusList = jsonrpc.processes.fndisconnectComponent(compInfoId,"NA","Y");
		if(statusList.returnStatus==0){	
			document.getElementById('status'+indexval).value=' '+statusList.componentStatus;				
			if(statusList.compEndLogic=="BTD"){
				document.getElementById('textCompEndDateLogic'+indexval).value=	" Billing Trigger Date";
			}else if(statusList.compEndLogic=="TD"){
				document.getElementById('textCompEndDateLogic'+indexval).value=	" Till Disconnection";	
			}								
			document.getElementById('txtEndDays'+indexval).value=statusList.compEndDays;		
			document.getElementById('txtEndMonth'+indexval).value=statusList.compEndMonths;
			if(isInActive==1){
				document.getElementById('disconnButton'+indexval).onclick=function() {disconnectComponent(compInfoId,1,indexval)};
				alert("Component reverted!!");	
			}else if(isClose==1){
				document.getElementById('disconnButton'+indexval).onclick=function() {disconnectComponent(compInfoId,2,indexval)};				
				alert("Component reverted!!");	
			}else if(isInActive==0 && isClose==0){
				document.getElementById('disconnButton'+indexval).onclick=function() {componentEndDatePassed(isClose,isInActive)};				
			}					
		}else{
			return;
		}
	}else{	
		return;
	}
}

//Onclick backgroud Color change for row : Done by Ashutosh
var upperSectionSelectedTR=null;
 function changeUpperBackground()
 {
 	var current = window.event.srcElement;
 	while ( (current = current.parentElement)  && current.tagName !="TR");
	current.style.backgroundColor = "blue";
	if(upperSectionSelectedTR!=null)
	{
		upperSectionSelectedTR.style.backgroundColor = "#F7F7E7";//body color
	}
	upperSectionSelectedTR=current;
    
 }
function componentEndDatePassed(isclose,isinactive){
	if(isclose==0 && isinactive==0){
		alert('Component Cannot be disconnected beacuse its End date has passed.');
		return;
	}
}
function getComponentAttribute(){
	if(componentDetailsFetched || $("#hdnComponentInfo").val()!=1)
		return;
 	var count = parseInt(document.getElementById('hdnComponentsCount').value);
 	var initCount = count;
	var jsComponentData ={
			serviceProductID:serviceProductID,
			serviceId:servicesID
	};
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	
	componentslists = jsonrpc.processes.populateComponentsDetails(jsComponentData);
	if(componentslists.list.length==0){
	}else{
		var compDiv = document.getElementById('contentscroll2');
		var compTable = "<table border='0' cellpadding='0' cellspacing='0' class='content' id='tableComponents'>";
    	var tableEnd = "</table>";
    	var tableRowStart= "<tr onclick = 'changeUpperBackground();'>";
    	var tableRowEnd = "</tr>";
    	var tableDataStart;
    	var tableDataEnd = "</td>";
    	var str;
    	var startDateOptions = getComponentStartDateOption();
    	var endDateOptions = getComponentEndDateOption();
    	var ctrlArry = new Array();
    	var fieldIndex = 0;
		for (i = 0 ; i < componentslists.list.length; i++){
			//var counter = parseInt(document.getElementById('hdnComponentsCount').value)+1;
			//document.getElementById('hdnComponentsCount').value = counter;
			//counter = document.getElementById('tableComponents').rows.length;
			//var tblRow = document.getElementById('tableComponents').insertRow();
			compTable = compTable + tableRowStart;
			//tblRow.onclick = function(){changeUpperBackground();};//Added by Ashutosh for highlighting row
			var created_serviceid=componentslists.list[i].created_serviceid;
			if(created_serviceid==servicesID){
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col1 toprow";*/
				str ="<input name='chkComponents' id='chkComponents"+i+"' type='checkbox' value='"+count+"'>";
				str=str+"<input type='hidden' name='component_infoID' id = 'component_infoID"+i+"' value='"+componentslists.list[i].componentInfoID+"'>";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
			}else{
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col1 toprow";*/
				str ="<input name='chkComponents' id='chkComponents"+i+"' type='checkbox' value='"+count+"' disabled='disabled'>";
				str=str+"<input type='hidden' name='component_infoID' id = 'component_infoID"+count+"' value='"+componentslists.list[i].componentInfoID+"'>";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
			}
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col1 toprow'>";
			compTable = compTable + tableDataStart + str + tableDataEnd;
			
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata secondColumn";*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata secondColumn'>";
			str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' style='width:100px'  class='inputBorder1' name='components' isRequired='0' id='components"+count+"'  value = ' " + componentslists.list[i].componentID + " ' readonly='true'>";
			str=str+"<input type='hidden' name='component_infoIDs' id = 'component_infoIDs"+i+"' value='"+componentslists.list[i].componentInfoID+"'>";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			compTable = compTable + tableDataStart + str + tableDataEnd;
				
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata descColumn";*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata descColumn'>";
			str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='inputDisabled' style='width=250px'name='componentsName' isRequired='0' id='componentsName"+count+"'  value = ' " + componentslists.list[i].componentName + " ' readonly='true'>";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			compTable = compTable + tableDataStart + str + tableDataEnd;
			
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata secondColumn";*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata secondColumn'>";
			str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' style='width:100px' class='inputDisabled' name='packageID' isRequired='0' id='packageID"+count+"'  value = ' " + componentslists.list[i].packageID + " ' readonly='true'>";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			compTable = compTable + tableDataStart + str + tableDataEnd;
				
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata descColumn";*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata descColumn'>";
			str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='inputDisabled' style='width=250px'name='packageName' isRequired='0' id='packageName"+count+"'  value = ' " + componentslists.list[i].packageName + " ' readonly='true'>";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			compTable = compTable + tableDataStart + str + tableDataEnd;
			
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata secondColumn";*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata secondColumn'>";
			str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' style='width:100px' class='inputDisabled' name='status' isRequired='0' id='status"+count+"'  value = ' " + componentslists.list[i].componentStatus + " ' readonly='true'>";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			compTable = compTable + tableDataStart + str + tableDataEnd;
			if(created_serviceid==servicesID){
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col4";*/
				//str ="<input type='text' class='inputBorder1' style='width=200px'name='startdatelogic' id='startdatelogic"+count+"'  value = ' " + componentslists.list[i].startLogic + " ' >";
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col4'>";
				str="<table><tr><td><select  name='txtCompStartDateLogic' id='txtCompStartDateLogic"+count+"' isRequired='1' Disp='' class='dropdownMargin' style='width:130px'>";
				str = str + startDateOptions + "</select></td>";
				str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='"+componentslists.list[i].startDateDays+"'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtStartDays'  id='txtStartDays"+count+"' onblur='if(this.value.length > 0){return checknumber(this)}'></td> ";
				str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='"+componentslists.list[i].startDateMonth+"'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtStartMonth' id='txtStartMonth"+count+"' onblur='if(this.value.length > 0){return checknumber(this)}'><input type='hidden' name='hdnstartdate' value=''></td></tr></table> ";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				compTable = compTable + tableDataStart + str + tableDataEnd;
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col4";*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col4'>";
				//str ="<input type='text' class='inputBorder1' style='width=200px'name='enddatelogic' id='enddatelogic"+count+"'  value = ' " + componentslists.list[i].endLogic + " ' >";
				str="<table><tr><td><select  name='txtCompEndDateLogic' id='txtCompEndDateLogic"+count+"' isRequired='1' Disp='' class='dropdownMargin' onchange='changeCompEndDays("+count+");' style='width:130px'>";
				str= str + endDateOptions + "</select></td>";
				str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='"+componentslists.list[i].endDateDays+"'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtEndDays' id='txtEndDays"+count+"'  onblur='if(this.value.length > 0){return checknumber(this)}'></td> ";
				str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='"+componentslists.list[i].endDateMonth+"'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtEndMonth' id='txtEndMonth"+count+"' onblur='if(this.value.length > 0){return checknumber(this)}'></td></tr></table> ";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				compTable = compTable + tableDataStart + str + tableDataEnd;
				
				//---------------Start Date Logic and End Date Logic--------------------------------------------------------
				//fillComponentStartEndDateLogic(count);									
				/*document.getElementById('txtCompStartDateLogic'+count).value=componentslists.list[i].startDateLogic;
				document.getElementById('txtCompEndDateLogic'+count).value=componentslists.list[i].endDateLogic;*/
				//-----------------------------------------------------------------------------------------------------------
			}else{
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col4";*/
				//str ="<input type='text' class='inputBorder1' style='width=200px'name='startdatelogic' id='startdatelogic"+count+"'  value = ' " + componentslists.list[i].startLogic + " ' >";
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col4'>";
				str="<table><tr><td><input type='text' class='inputBorder1' style='width=130px'  id='textCompStartDateLogic"+count+"'  value = ' " + componentslists.list[i].startDateLogicName + " ' ><input type='hidden' isRequired='1' id='txtCompStartDateLogic"+count+"' value='"+componentslists.list[i].startDateLogic+"'></td> ";
				str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readonly='readonly' style='width:100px;' value='"+componentslists.list[i].startDateDays+"'  class='inputBorder' name='txtStartDays'  id='txtStartDays"+count+"'></td>";
				str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readonly='readonly' style='width:100px;' value='"+componentslists.list[i].startDateMonth+"'  class='inputBorder' name='txtStartMonth' id='txtStartMonth"+count+"'></td></tr></table> ";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				compTable = compTable + tableDataStart + str + tableDataEnd;
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col4";*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col4'>";
				//str ="<input type='text' class='inputBorder1' style='width=200px'name='enddatelogic' id='enddatelogic"+count+"'  value = ' " + componentslists.list[i].endLogic + " ' >";
				str="<table><tr><td><input type='text' class='inputBorder1' style='width=130px'  id='textCompEndDateLogic"+count+"'  value = ' " + componentslists.list[i].endDateLogicName + " ' ><input type='hidden' id='txtCompEndDateLogic"+count+"' isRequired='1' value='"+componentslists.list[i].endDateLogic+"'></td> ";
				str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readonly='readonly' style='width:100px;' value='"+componentslists.list[i].endDateDays+"' class='inputBorder' name='txtEndDays' id='txtEndDays"+count+"'></td> ";
				str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readonly='readonly' style='width:100px;' value='"+componentslists.list[i].endDateMonth+"' class='inputBorder' name='txtEndMonth' id='txtEndMonth"+count+"'></td></tr></table> ";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				compTable = compTable + tableDataStart + str + tableDataEnd;
			}
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata dateField";*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata dateField'>";
			str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' readonly='readonly' class='inputDisabled' style='width=100px'name='startdate' isRequired='0' id='startdate"+count+"'  value = ' " + componentslists.list[i].start_date + " ' >";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			compTable = compTable + tableDataStart + str + tableDataEnd;
				
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata dateField";*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata dateField'>";
			str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' readonly='readonly' class='inputDisabled' style='width=100px'name='enddate' isRequired='0' id='enddate"+count+"'  value = ' " + componentslists.list[i].end_date + " ' >";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			compTable = compTable + tableDataStart + str + tableDataEnd;
				
			if(created_serviceid==servicesID){
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col1";*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col1'>";
				str= "&nbsp;&nbsp;";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				compTable = compTable + tableDataStart + str + tableDataEnd;
			}else{	
				var isDisconnectionButtonCreated=0;
				//Vijay add condition for demo-regularize
				if(((hdnChangeTypeId==2 && (changeSubTypeID==8 || changeSubTypeID==9 || changeSubTypeID==10)) || hdnChangeTypeId==5 
						|| (hdnChangeTypeId==4 && changeSubTypeID==12 ))){
						/*var tblcol=tblRow.insertCell();
						tblcol.align = "left";
						tblcol.vAlign="top";
						tblcol.className="innerdata col1";*/
						tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col1'>";
						str="";
						if(componentslists.list[i].disconnectedInCurrentService==0 
								|| componentslists.list[i].disconnectedInCurrentService==null || componentslists.list[i].disconnectedInCurrentService==servicesID){								
							    if(componentslists.list[i].isDisconnected==1){
									str= "<div class='searchBg1'  ><a href='#' id='disconnButton"+count+"' onClick='alreadyDisconnectComponent("+componentslists.list[i].componentInfoID+","+componentslists.list[i].inactive+","+componentslists.list[i].isclose+","+count+")' >...</a></div>";
									isDisconnectionButtonCreated=1;
								}else if(componentslists.list[i].inactive ==1){
									str= "<div class='searchBg1'  ><a href='#' id='disconnButton"+count+"' onClick='disconnectComponent("+componentslists.list[i].componentInfoID+",1,"+count+")'>...</a></div>";
									isDisconnectionButtonCreated=1;
								}else if (componentslists.list[i].isclose ==1){
									str= "<div class='searchBg1'  ><a href='#' id='disconnButton"+count+"' onClick='disconnectComponent("+componentslists.list[i].componentInfoID+",2,"+count+")'>...</a></div>";	
									isDisconnectionButtonCreated=1;
								}
								else if(componentslists.list[i].isclose==0 && componentslists.list[i].inactive ==0){//end date had passed , so cannot be disconnected for non disconnected components
									str= "<div class='searchBg1'  ><a href='#' id='disconnButton"+count+"' onClick='componentEndDatePassed("+componentslists.list[i].isclose+","+componentslists.list[i].inactive+")' >...</a></div>";														
									isDisconnectionButtonCreated=1;
								}
						}else{
							str="&nbsp;";
						}
							
						//if order in viewMode (non edit mode) then all disconnect button must be disable
						if(isDisconnectionButtonCreated==1){								
							if((isView!=null && isView==1)||(orderStage=="Billing Trigger" || orderStage=="Published")){
								str="<div class='searchBg1'><a href='#' id='disconnButton"+count+"'>...</a></div>";	
							}								
						}							
						/*CellContents = str;
						tblcol.innerHTML = CellContents; */			
						compTable = compTable + tableDataStart + str + tableDataEnd;
					}else{
						/*var tblcol=tblRow.insertCell();
						tblcol.align = "left";
						tblcol.vAlign="top";
						tblcol.className="innerdata col1";*/
						tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col1'>";
						str= "&nbsp;&nbsp;";
						/*CellContents = str;
						tblcol.innerHTML = CellContents;*/
						compTable = compTable + tableDataStart + str + tableDataEnd;
					}
				}	
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str ="<input type='text' class='inputBorder1' name='activeDate'  id='activeDate"+componentslists.list[i].componentID+"'  readonly='true' value = ' " + componentslists.list[i].activeDate + " ' >";
				CellContents = str;
				tblcol.innerHTML = CellContents;
					
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				str ="<input type='text' class='inputBorder1' name='inactiveDate'  id='inactiveDate"+componentslists.list[i].componentID+"'  readonly='true' value = ' " + componentslists.list[i].inactiveDate + " ' >";
				CellContents = str;
				tblcol.innerHTML = CellContents;*/
				
				// 023 start								
				
				ctrlArry[fieldIndex +0] =  "components"+count;
				ctrlArry[fieldIndex +1] =  "componentsName"+count;
				ctrlArry[fieldIndex +2] =  "packageID"+count;
				ctrlArry[fieldIndex +3] =  "packageName"+count;
				ctrlArry[fieldIndex +4] =  "status"+count;
				ctrlArry[fieldIndex +5] =  "startdate"+count;
				ctrlArry[fieldIndex +6] =  "enddate"+count;
				ctrlArry[fieldIndex +7] =  "txtCompStartDateLogic"+count;
				ctrlArry[fieldIndex +8] =  "txtCompEndDateLogic"+count;	
				fieldIndex = fieldIndex + 9;
				//var callerWindowObj = dialogArguments;		
				//fieldRoleMappingValidation(callerWindowObj.linesTabFieldList,ctrlArry);
				count++;
				//023 end		
				compTable = compTable + tableRowEnd;
			}
			compTable = compTable + tableEnd;
			compDiv.innerHTML = compTable;
			var callerWindowObj = dialogArguments;		
			allFieldRoleMappingValidation(callerWindowObj.linesTabFieldList,ctrlArry);
			document.getElementById('hdnComponentsCount').value = count;
			for (i = 0 ; i < componentslists.list.length; i++){
				document.getElementById('txtCompStartDateLogic'+initCount).value=componentslists.list[i].startDateLogic;
				document.getElementById('txtCompEndDateLogic'+initCount).value=componentslists.list[i].endDateLogic;
				initCount++;
			}
	}
	componentDetailsFetched = true;
	enableDisableSections(compSection);
	enableDisableCLEP(compSection);
	var compSec = new Array();
	compSec[0]="COMPONENT";
	compSec[1]="Components";
	if(null == roleWiseSectionDetail){
    	var callerWindowObj = dialogArguments;
    	var roleName = callerWindowObj.gb_roleID;
    	roleWiseSectionDetail = getRoleWiseSectionDetails(roleName);
    }
	enableDisableCommercialFields(compSec, roleWiseSectionDetail, lineType);
}
/*function fillChargeType(var_counter){
	try{
		if(gbchargeTypeCached==0){
			//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
			chargeCombo = jsonrpc.processes.populateChargeType();
			gbchargeTypeIds=new Array();
			gbchargeTypeValues=new Array()
			for(z=0;z<chargeCombo.list.length;z++){
		    	gbchargeTypeIds[z] = chargeCombo.list[z].chargeTypeID;
				gbchargeTypeValues[z] = chargeCombo.list[z].chargeTypeName;
		    }
		}
		gbchargeTypeCached=1;
		var local_typeCombo = document.getElementById("ddCType"+var_counter);
	    var local_RowCount = local_typeCombo.length;
	    for (i = 1; i <  local_RowCount; i++){
	       local_typeCombo.remove(1);
	    } 
	    for(i=0;i<gbchargeTypeIds.length;i++){
	    	var option = document.createElement("option");
	   		option.text = gbchargeTypeValues[i];
	   		option.title = gbchargeTypeValues[i];
			option.value = gbchargeTypeIds[i];
			try{
				local_typeCombo.add(option, null); //Standard
			}catch(error){
				local_typeCombo.add(option); // IE only
			}
	    }
    }catch(e){
    	alert(gb_exceptionMessage);
		showJavascriptException(e,gb_exceptionMessageShow);
	}
}

function fillChargeNames(var_counter, chargeTypeValue,chargeNameID){
	try
	{
			var chargetype = chargeTypeValue;
			var productid =document.getElementById('hdnServiceDetailID').value;
			//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
			chargeCombo = jsonrpc.processes.populateChargeName(chargetype,productid);
			gchargeName=new Array();
			gchargeid=new Array()
			
			for(z=0;z<chargeCombo.list.length;z++)
		    {
		    	gchargeName[z] = chargeCombo.list[z].chargeName;
				gchargeid[z] = chargeCombo.list[z].chargeNameID;
		    }
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
	      local_typeCombo.value=chargeNameID;
    }
    catch(e)
	{
    	alert(gb_exceptionMessage);
		showJavascriptException(e,gb_exceptionMessageShow);
	}
}
function fillFrequency(var_counter){
	checkContractPeriod(var_counter);
	if(gbfrequencyCached==0){
		try{
			//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
			frequencyCombo = jsonrpc.processes.populateFrequencyType();
		}catch(e){
	    	alert(gb_exceptionMessage);
			showJavascriptException(e,gb_exceptionMessageShow);
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
}*/
function fnHideAll()
{
	document.getElementById('lineItemServiceSummary').style.display='none';
	document.getElementById('BillingNChargeInfo').style.display='none';
   	document.getElementById('BillingInfo').style.display='none';
   	//document.getElementById('serviceType').style.display='block';
   	document.getElementById('ChargeDetails').style.display='none';
   	document.getElementById('Components').style.display='none';
   	//document.getElementById('saveImage').style.display='block';
   	document.getElementById('HardwareDetails').style.display='none';
   	document.getElementById('ServiceLocation').style.display='none';
}
function fnViewProductCatelog(url)
{
	var path = path + "/NewOrderAction.do?method=productCatelogforUpdate&"+ unescape(url) +"&viewModify=2";
	window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
}

function changeFrequency(var_counter){
	if(document.getElementById('ddCType'+var_counter).value=="1"){
		document.getElementById('txtCFrequency'+var_counter).disabled=false;
	}else{
		document.getElementById('txtCFrequency'+var_counter).disabled=true;
	}
}
function changehdnServiceType()
{
	var tr, td, i, j, oneRecord;
    var test9;
   
    var jsData =			
    {
		serviceDetailID:document.getElementById("serviceDetailID").value
	};

	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
    test9 = jsonrpc.processes.fetchAccessDetails(jsData);

    if(test9.list.length>0) //if logic added by saurabh
	{
	    document.getElementById('hdnServiceSummary').value = test9.list[0].serviceInfoValue;
	    document.getElementById('hdnBillingInfo').value = test9.list[0].billingInfoValue;
	    document.getElementById('hdnChargeInfo').value = test9.list[0].chargeInfoValue;
	    document.getElementById('hdnLocationInfo').value = test9.list[0].locationInfoValue;
	    document.getElementById('hdnHardwareInfo').value = test9.list[0].hardwareInfoValue;
		document.getElementById("hdnServiceDetailID").value=document.getElementById("serviceDetailID").value;
		document.getElementById("txtHdnServiceName").value=document.getElementById("hdnServiceDetailID").options[document.getElementById("hdnServiceDetailID").selectedIndex].text
	}
}
function getDispatchAddress()
{
	var tr, td, i, j, oneRecord;
    var test4;
     
    var jsData4 =			
    {
		dispatchAddressID:document.getElementById("txtdispatchAddress").value
	};
	
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");

    if(document.getElementById("txtdispatchAddress").value != "0")
	{
    	test4 = jsonrpc.processes.populateDispatchAddress(jsData4);
    	if(test4.list.length>0) //if logic added by saurabh
    	{
	    	document.getElementById('txtHAddress1').value = test4.list[0].dispatchAddress1;
			document.getElementById('txtHAddress2').value = test4.list[0].dispatchAddress2;
			document.getElementById('txtHAddress3').value = test4.list[0].dispatchAddress3;
			document.getElementById('txtHCityName').value = test4.list[0].dispatchCityName;
			document.getElementById('txtHCountryName').value = test4.list[0].dispatchCountyName;
			document.getElementById('txtHPincode').value = test4.list[0].dispatchPinNo;
			document.getElementById('txtHPhnNo').value = test4.list[0].dispatchPhoneNo;
			document.getElementById('txtHStateName').value = test4.list[0].dispatchStateName;
			//[505050]
			if(test4.list[0].lst_No!=0)
			{
			document.getElementById('txtHLstNo').value = test4.list[0].lst_No;
			}
			else
			{
			document.getElementById('txtHLstNo').value = '-';
			}
			//[505050]
			
		}
	}
	else
	{
		document.getElementById('txtHAddress1').value="0";
		document.getElementById('txtHAddress2').value="0";
		document.getElementById('txtHAddress3').value="0";
		document.getElementById('txtHCityName').value="0";
		document.getElementById('txtHCountryName').value="0";
		document.getElementById('txtHPincode').value="0";
		document.getElementById('txtHPhnNo').value="0";
		document.getElementById('txtHStateName').value="0";
		document.getElementById('txtHLstDate').value="0";
		document.getElementById('txtHLstNo').value="0";						
	}
}
//TODO Puneet check arrays
/*function fetchPODetailsEntity(poNumber,entity_ID){
	var tr, td, i, j, oneRecord;
    var test;
    var combo = document.getElementById("txtPODetailNo");
	contractVal=new Array();  
    chargeVal=new Array();
    poDateVal = new Array();
   	var iCountRows = combo.length;
  
   	for (i = 1; i <  iCountRows; i++){
   		combo.remove(1);
   	}

    var jsData = {
		poNumber:poNumber,
		serviceProductID:serviceProductID,
		legalEntity:entity_ID
	};
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	if(editSolutionChangeOldProduct == 1){
		test = jsonrpc.processes.populatePODetailsNoForChangeViewWithLE(jsData);
		if(test.list.length == 0){
			if(hdnChangeTypeId!=1)
	    {
    	alert('No PO for this Legal Entity exsist in this Order. Please enter PO first.');
    	}
    	
    	return;
    }
    }else {
    	test = jsonrpc.processes.populatePODetailsNoForChangeView(jsData);
    }
    for(j=0;j<test.list.length;j++)
    {
    	
    	var option = document.createElement("option");
   		option.text = test.list[j].custPoDetailNo;
   		option.title = test.list[j].custPoDetailNo;
		option.value = test.list[j].poDetailNo;
		document.getElementById("txtPODetailNo").value=option.value;
		chargeVal[j]= test.list[j].periodsInMonths;
		contractVal[j]= test.list[j].periodsInMonths;
		//added by Lawkush
		//poDateVal[j]= test.list[j].poDate;		
		poDateVal[j]= test.list[j].custPoDate;
		try 
		{
			combo.add(option, null); //Standard
		}
		catch(error) 
		{
			combo.add(option); // IE only
		}
    }    
}*/
function additionalNodeFlagCheck(){
	additionalNodeFlag=0;		
	var jsData = {
		serviceId:callerWindowObj.document.getElementById('hdnserviceid').value,								    	
		serviceProductID:serviceProductID
	};
	//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	additionalNodeFlag = jsonrpc.processes.AdditionalNodeFlagCheck(jsData);
	return additionalNodeFlag;
}
var gb_selectedLicense_Comp=0;
var gb_selectedLicense_Text="Select Licence Company";
function fnFetchEntity(){
	var poNo = document.getElementById("txtPODetailNo").value;
	gb_selectedLicense_Comp=document.getElementById("txtLicenceCo").value;
	gb_selectedLicense_Text = document.getElementById("licenseCo").value;
	additionalNodeFlag1 = additionalNodeFlagCheck();
	if((hdnChangeTypeId==5 && changeSubTypeID==5) || (hdnChangeTypeId==4 && changeSubTypeID==12) ){
		editSolutionChangeOldProduct=1;
	}
	if(poNo==0){
		document.getElementById('txtCntrtMonths').value="";
	    if(editSolutionChangeOldProduct ==1){/*
	    	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	    	test = jsonrpc.processes.populateEntityForChange(serviceProductID);
	    	var combo = document.getElementById("txtEntity");
		    for(j=0;j<test.list.length;j++){
		    	document.getElementById('hdnISFLEFLAG').value=test.list[j].isFLEFlag;
		    	var option = document.createElement("option");
		   		option.text = test.list[j].entity;
		   		option.title = test.list[j].entity;
				option.value = test.list[j].entityID;
				try{
					combo.add(option, null); //Standard
				}catch(error){
					combo.add(option); // IE only
				}
		    }
		    var combo1 = document.getElementById("txtLicenceCo");
		    //jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	    	test1 = jsonrpc.processes.populateLicCompanyForChange(serviceProductID);
		    for(j=0;j<test1.list.length;j++){    	
		    	var option = document.createElement("option");
		   		option.text = test1.list[j].licCompanyName;
		   		option.title = test1.list[j].licCompanyName;
				option.value = test1.list[j].licCompanyID;
				try{
					combo1.add(option, null); //Standard
				}catch(error){
					combo1.add(option); // IE only
				}
		    }
	   	 	document.getElementById("txtEntity").value = entityNo;
	    */}else if(additionalNodeFlag1 == 1 && poNo==0){ 	 
			if(document.getElementById('hdnHardwareInfo').value==1){		  
				//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");	
				/*test = jsonrpc.processes.populateEntityForChange(serviceProductID);
				var combo = document.getElementById("txtEntity");
				for(j=0;j<test.list.length;j++){
					document.getElementById('hdnISFLEFLAG').value=test.list[j].isFLEFlag;
					var option = document.createElement("option");
					option.text = test.list[j].entity;
					option.title = test.list[j].entity;
					option.value = test.list[j].entityID;
					try{
						combo.add(option, null); //Standard
					}catch(error){
						combo.add(option); // IE only
					}
				}*/
				//document.getElementById('txtEntity').disabled=true;	
				document.getElementById('legalEntityTXT').disabled=true;	
				document.getElementById('legalEntityTXTLinkId').disabled=true;	
				/*var combo1 = document.getElementById("txtLicenceCo");
				//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");	
				test1 = jsonrpc.processes.populateLicCompanyForChange(serviceProductID);		    		    
				for(j=0;j<test1.list.length;j++){    	
					var option = document.createElement("option");
					option.text = test1.list[j].licCompanyName;
					option.title = test1.list[j].licCompanyName;
					option.value = test1.list[j].licCompanyID;
					try{
						combo1.add(option, null); //Standard
					}catch(error){
						combo1.add(option); // IE only
					}
				}
	   		 	document.getElementById("txtEntity").value = entityNo;*/
				
	   		 	document.getElementById('licenseCo').disabled=true;
				document.getElementById('licenseCoLinkId').disabled=true;
				document.getElementById('txtLicenceCo').disabled=true;
			}
	    }	  
	}else{/*
		var tr, td, i, j, oneRecord;
	    var test;
	 	var combo = document.getElementById("txtEntity");
	 	var iCountRows = combo.length;
	 	for (i = 1; i <  iCountRows; i++){
		       combo.remove(1);
	 	}
	 
	    var jsData ={
			poDetailNo:poNo
		};
		//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	    test = jsonrpc.processes.populateEntity(jsData);
	    var combo = document.getElementById("txtEntity");
	    for(j=0;j<test.list.length;j++){
	    	document.getElementById('hdnISFLEFLAG').value=test.list[j].isFLEFlag;
	    	var option = document.createElement("option");
	   		option.text = test.list[j].entity;
	   		option.title = test.list[j].entity;
			option.value = test.list[j].entityID;
			try{
				combo.add(option, null); //Standard
			}catch(error){
				combo.add(option); // IE only
			}
	    }
	    if(editSolutionChangeOldProduct ==1){
	   		 document.getElementById("txtEntity").value = test.list[0].entityID;
	    }else*/ if(additionalNodeFlag1 == 1 && document.getElementById('hdnHardwareInfo').value==1){
			//document.getElementById("txtEntity").value = test.list[0].entityID;
			//document.getElementById('txtEntity').disabled=true;		
			document.getElementById('legalEntityTXT').disabled=true;	
			document.getElementById('legalEntityTXTLinkId').disabled=true;
		}
		//TODO
	 	/*fnFetchLicCompany(document.getElementById("txtEntity").value);
		fnFetchContractPeriod(poNo);
	    if(document.getElementById('txtBillingLvl') != null ){
			setBillingLevelNo(document.getElementById('txtBillingLvl').value)
		}
		//Puneet commenting the below line as date is directly set on change
	    //document.getElementById('txtBillingPODate').value=poDateVal[document.getElementById("txtPODetailNo").selectedIndex-1];
	*/}
	if(hdnChangeTypeId==5 && changeSubTypeID==5 || (hdnChangeTypeId==4 && changeSubTypeID==12)){
		editSolutionChangeOldProduct=0;
	}
}
/*function fnFetchLicCompany(entId){
	var tr, td, i, j, oneRecord;
    var test;
 	var combo = document.getElementById("txtLicenceCo");
 	var serviceDetailID1=Number(document.getElementById("hdnserviceDetailId").value);
 	var iCountRows = combo.length;
 	for (i = 1; i <  iCountRows; i++){
 		combo.remove(1);
 	}
   
    var jsData ={
		entityID:document.getElementById("txtEntity").value,
		serviceDetailID:serviceDetailID1
	};
	
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");

    test = jsonrpc.processes.populateLicCompany(jsData);
    removeAllOptions(document.getElementById("txtLicenceCo")); 
    
    for(j=0;j<test.list.length;j++{
    	var option = document.createElement("option");
   		option.text = test.list[j].licCompanyName;
   		option.title = test.list[j].licCompanyName;
		option.value = test.list[j].licCompanyID;
		try{
			combo.add(option, null); //Standard
		}catch(error){
			combo.add(option); // IE only
		}
    }
     if(editSolutionChangeOldProduct ==1){
    	 if(document.getElementById("txtLicenceCo").disabled==true){
    		 document.getElementById("txtLicenceCo").value = gb_selectedLicense_Comp;
    		 document.getElementById("licenseCo").value = gb_selectedLicense_Text;
    	 }else{
    		 document.getElementById("txtLicenceCo").value = test.list[0].licCompanyID;
    	 }
    	 if(document.getElementById('hdnHardwareInfo').value==1){//if Hardware
	    	var path='/IOES';
	    	populateStore(path);
	    	var index=document.getElementById("txtStore").selectedIndex;
	    	if(index < 2){
	    		document.getElementById('txtStore').value=storeName;
	    	}
	    } 
    }
    var additionalNodeFlag1 = additionalNodeFlagCheck();
    if(additionalNodeFlag1 == 1 && document.getElementById('hdnHardwareInfo').value==1){
    	document.getElementById("txtLicenceCo").value = test.list[0].licCompanyID;
    	document.getElementById('txtLicenceCo').disabled=true;
	    {
	    	var path='/IOES';
	    	populateStore(path);
	    	var index=document.getElementById("txtStore").selectedIndex;
	    	if(index < 2){
	    		document.getElementById('txtStore').value=storeName;
	    	}
	    } 
    }
}*/

function selectPrimaryLocation(DispValue, sendToM6Check)
{
	var displayValue=DispValue;
	var sendToM6Check;
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	if(null == sendToM6Check)
		sendToM6Check = jsonrpc.processes.getM6LineItemCheck(serviceDetailID);
		
	if(sendToM6Check==0 && serviceid == 4)
	{
		if(document.getElementById('ddPrimaryLocType').value==0)
		{
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
		}
		else if(document.getElementById('ddPrimaryLocType').value==1)
		{
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='block'; 
		}
		else
		{
			alert('Network location is not Allowed for this Line Item');
			document.getElementById('ddPrimaryLocType').value=0;
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
		}		
	}

	if(sendToM6Check==1 && serviceid == 4)
	{
		if(document.getElementById('ddPrimaryLocType').value==0)
		{
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
		}
		else if(document.getElementById('ddPrimaryLocType').value==1)
		{
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='block'; 
		}
		else
		{
			document.getElementById('PriNetworkPopLocation').style.display='block'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
			if(displayValue==1)
			{
				PopLocTypePopup('PRILOC');
			}
		}
	}
	if(sendToM6Check==1 && serviceid != 4)
	{
		if(document.getElementById('ddPrimaryLocType').value==0)
		{
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
		}
		else if(document.getElementById('ddPrimaryLocType').value==1)
		{
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='block'; 
		}
		else
		{
			document.getElementById('PriNetworkPopLocation').style.display='block'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
			if(displayValue==1)
			{
				PopLocTypePopup('PRILOC');
			}
		}
	}
		if(sendToM6Check==0 && serviceid != 4)
		{
			if(document.getElementById('ddPrimaryLocType').value==0)
			{
				document.getElementById('PriNetworkPopLocation').style.display='none'; 
				document.getElementById('PriBCPLocation').style.display='none'; 
			}
			else if(document.getElementById('ddPrimaryLocType').value==1)
			{
				document.getElementById('PriNetworkPopLocation').style.display='none'; 
				document.getElementById('PriBCPLocation').style.display='block'; 
			}
			else
			{
				alert('Network location is not Allowed for this Line Item');
				document.getElementById('ddPrimaryLocType').value=0;
				document.getElementById('PriNetworkPopLocation').style.display='none'; 
				document.getElementById('PriBCPLocation').style.display='none'; 
			}		
		}
}
function selectSecondaryLocation(DispValue, sendToM6Check)
{
	var displayValue=DispValue;
	var sendToM6Check;
	//var jsonrpc1 = new JSONRpcClient(path + "" + "/JSON-RPC");
	if(null == sendToM6Check)
		sendToM6Check = jsonrpc1.processes.getM6LineItemCheck(serviceDetailID);
		
	if(sendToM6Check==0 && serviceid == 4)
	{
		if(document.getElementById('ddSecondaryLocType').value==0)
		{
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
		}
		else if(document.getElementById('ddSecondaryLocType').value==1)
		{
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='block'; 
		}
		else if(document.getElementById('ddSecondaryLocType').value==2)
		{
			alert('Network location is not Allowed for this Line Item');
			document.getElementById('ddSecondaryLocType').value=0;
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
		}			
	}
	if(sendToM6Check==1 && serviceid != 4)
	{
	    if(document.getElementById('ddSecondaryLocType').value==0)
		{
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
		}
		else if(document.getElementById('ddSecondaryLocType').value==1)
		{
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='block'; 
		}
		else
		{
			document.getElementById('SecNetworkLocation').style.display='block'; 
			document.getElementById('SecBCPLocation').style.display='none';
			if(displayValue==1)
			{
				PopLocTypePopup('SECLOC');
			} 
		}
  	}
	if(sendToM6Check==1 && serviceid == 4)
	{
		if(document.getElementById('ddSecondaryLocType').value==0)
		{
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
		}
		else if(document.getElementById('ddSecondaryLocType').value==2)
		{
			document.getElementById('SecNetworkLocation').style.display='block'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
			if(displayValue==1)
			{
				PopLocTypePopup('SECLOC');
			}
		}
		else if(document.getElementById('ddSecondaryLocType').value==1)
		{
			alert('Customer location is not Allowed for this Line Item');
			document.getElementById('ddSecondaryLocType').value=0;
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
		}	
	}
	if(sendToM6Check==0 && serviceid != 4)
	{
		if(document.getElementById('ddSecondaryLocType').value==0)
		{
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
		}
		else if(document.getElementById('ddSecondaryLocType').value==1)
		{
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='block'; 
		}
		else if(document.getElementById('ddSecondaryLocType').value==2)
		{
			alert('Network location is not Allowed for this Line Item');
			document.getElementById('ddSecondaryLocType').value=0;
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
		}			
	}
	
}

function FetchPriBCPDetails()
{
	var tr, td, i, j, oneRecord;
    var test5;
   if(document.getElementById("ddPrimaryBCP").value != 0)
   {
	    var jsData5 =			
	    {
			bcpID:document.getElementById("ddPrimaryBCP").value
		};
		
		//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	
	    test5 = jsonrpc.processes.populateprmBCPDetails(jsData5);
		
	    if(test5.list.length>0) //if logic added by saurabh 
	    {
		        //Start[006]
		    	document.getElementById('txtPriCustLocationSuggest').value = test5.list[0].bcpName;
			 //End[006]
	    		document.getElementById('txtPAddress1').value = test5.list[0].paddress1;
			document.getElementById('txtPAddress2').value = test5.list[0].paddress2;
			document.getElementById('txtPAddress3').value = test5.list[0].paddress3;
			document.getElementById('txtPAddress4').value = test5.list[0].paddress4;
			document.getElementById('txtPCity').value = test5.list[0].pcity;
			document.getElementById('txtPState').value = test5.list[0].pstate;
			document.getElementById('txtPPincode').value = test5.list[0].ppincode;
			document.getElementById('txtPCountry').value = test5.list[0].pcountry;
			document.getElementById('txtPContactNo').value = test5.list[0].pcontactNO;
			document.getElementById('txtPEmailId').value = test5.list[0].pemailId;
		}
	}
	else
	{
		 //Start[006]
		document.getElementById('txtPriCustLocationSuggest').value = "" ;
		 //End[006]
	    	document.getElementById('txtPAddress1').value = "";
		document.getElementById('txtPAddress2').value = "";
		document.getElementById('txtPAddress3').value = "";
		document.getElementById('txtPAddress4').value = "";
		document.getElementById('txtPCity').value ="";
		document.getElementById('txtPState').value = "";
		document.getElementById('txtPPincode').value ="";
		document.getElementById('txtPCountry').value = "";
		document.getElementById('txtPContactNo').value = "";
		document.getElementById('txtPEmailId').value = "";
	}
	
}

function FetchSecBCPDetails()
{
	var tr, td, i, j, oneRecord;
    var test6;
   	if(document.getElementById("ddSecondaryBCP").value != 0)
   	{
    	var jsData6 =			
    	{
			bcpID:document.getElementById("ddSecondaryBCP").value
		};
	
		//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	
	    test6 = jsonrpc.processes.populateBCPSecDetails(jsData6);
	
	    if(test6.list.length>0) //if logic added by saurabh
		{
			//Start[006]
			document.getElementById('txtSecCustLocationSuggest').value = test6.list[0].bcpName;
			//End[006]
		    	document.getElementById('txtSAddress1').value = test6.list[0].saddress1;
			document.getElementById('txtSAddress2').value = test6.list[0].saddress2;
			document.getElementById('txtSAddress3').value = test6.list[0].saddress3;
			document.getElementById('txtSAddress4').value = test6.list[0].saddress4;
			document.getElementById('txtSCity').value = test6.list[0].scity;
			document.getElementById('txtSState').value = test6.list[0].sstate;
			document.getElementById('txtSPincode').value = test6.list[0].spincode;
			document.getElementById('txtSCountry').value = test6.list[0].scountry;
			document.getElementById('txtSContactNo').value = test6.list[0].scontactNO;
			document.getElementById('txtSEmailId').value = test6.list[0].semailId;
		}
	}
	else
	{
		//Start[006]
		document.getElementById('txtSecCustLocationSuggest').value = "" ;
		//End[006]
	    	document.getElementById('txtSAddress1').value = "";
		document.getElementById('txtSAddress2').value = "";
		document.getElementById('txtSAddress3').value = "";
		document.getElementById('txtSAddress4').value = "";
		document.getElementById('txtSCity').value = "";
		document.getElementById('txtSState').value = "";
		document.getElementById('txtSPincode').value = "";
		document.getElementById('txtSCountry').value = "";
		document.getElementById('txtSContactNo').value = "";
		document.getElementById('txtSEmailId').value = "";
	}
}

function FetchPNLocationAddress()
{
 	if(document.getElementById("ddPNLocation").value != 0)
 	{
		var tr, td, i, j, oneRecord;
	    var test7;
	    var jsData7 =			
	    {
			plocationCode:document.getElementById("ddPNLocation").value
		};
		//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	    test7 = jsonrpc.processes.populateNPLocationAddress(jsData7);
	    if(test7.list.length>0) //if logic added by saurabh
		{
	    	document.getElementById('txtPAddress').value = test7.list[0].ptxtPAddress;
	    }
    }
    else
    { 
       document.getElementById('txtPAddress').value="";
    }
}

function FetchSNLocationAddress()
{
	 	serviceTypeId=callerWindowObj.document.getElementById('hdnservicetypeid').value;
 		if(serviceTypeId==4){
		 	 document.getElementById('allLoc').style.display='block';
		   	 document.getElementById('onlyNetworkLoc').style.display='none';
		}
	 	else
	 	{ 	 	
	 		document.getElementById('onlyNetworkLoc').style.display='none';
	 	   	document.getElementById('allLoc').style.display='block';
	 	}
		if(document.getElementById("ddSNLocation").value != 0)
		{
			var tr, td, i, j, oneRecord;
		    var test8;
		    var jsData8 =			
		    {
				plocationCode:document.getElementById("ddSNLocation").value
			};
			//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
		    test8 = jsonrpc.processes.populateNSLocationAddress(jsData8);
		    if(test8.list.length>0) //if logic added by saurabh
			{
		    	document.getElementById('txtSAddress').value = test8.list[0].stxtSAddress;
		    }
		}    
    	else
    	{ 
     		document.getElementById('txtSAddress').value="";
		}
		document.getElementById('SecNetworkLocation').style.display='block';
}

/*function selectPrimaryLocation(DispValue)
{
	if(fnCheckForM6LineItem()== false)
	return false;
	if(document.getElementById('ddPrimaryLocType').value==1)
	{
		document.getElementById('PriNetworkPopLocation').style.display='none'; 
		document.getElementById('PriBCPLocation').style.display='block'; 
	}
	else
	{
		document.getElementById('PriNetworkPopLocation').style.display='block'; 
		document.getElementById('PriBCPLocation').style.display='none'; 
		if(displayValue==1)
		{
			PopLocTypePopup('PRILOC');
		}
	}
}

function selectSecondaryLocation(DispValue)
{
	if(fnCheckForM6LineItem()== false)
	return false;
	var displayValue=DispValue;
	if(serviceTypeId!=4){
	if(document.getElementById('ddSecondaryLocType').value==1)
	{
		document.getElementById('SecNetworkLocation').style.display='none'; 
		document.getElementById('SecBCPLocation').style.display='block'; 
	}
	else
	{
		document.getElementById('SecNetworkLocation').style.display='block'; 
		document.getElementById('SecBCPLocation').style.display='none';
		if(displayValue==1)
		{
			PopLocTypePopup('SECLOC');
		} 
	}
  }
  else
  {    document.getElementById('ddSecondaryLocType').value=2;
	   document.getElementById('SecNetworkLocation').style.display='block'; 
	   if(displayValue==1)
		{
			PopLocTypePopup('SECLOC');
		}
  }
}*/
function saveProductCatelog(){
	//[009]start
	var callerWindowObj = dialogArguments;
	if(callerWindowObj.gb_modeName != null && callerWindowObj.gb_modeName == 'viewMode'){
		return false;
	}
	//[009]end
	
	if(!serviceSummaryFetched && !billingDetailsFetched && !hardwareDetailsFetched && !locationDetailsFetched && !componentDetailsFetched){
		//Puneet nothing has been updated, hence returning directly
		alert("Nothing has been updated to save");
		return;
	}
	if(!serviceSummaryFetched)
		getServiceSummary();
	if(!billingDetailsFetched)
		getBillingInfo();
	if(!hardwareDetailsFetched)
		getHardwareDetails();
	if(!componentDetailsFetched)
		getComponentAttribute();
	if(!locationDetailsFetched)
		getLocationDetails();
	if(serviceListArray.length==0){
		var servicesID = serviceNo;
	}else{
		var servicesID=Number(document.getElementById('id_span_ServiceNo').innerHTML);
	} 
	var but;
	var butSummaryFlag = false;
	var butBillFlag = false;
	var butLocFlag = false;
	var butHWFlag = false;
	var butWarranFlag = false;
	var componentsList;
	//var jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");	
	serviceInactiveFlagCheck=jsonrpc.processes.serviceInactiveFlagCheck(servicesID);
	
	if(serviceInactiveFlagCheck == 1 || serviceInactiveFlagCheck == 2)
	{
		alert('Service cancelled , you cant save now !! ');
		return false;
	}

	if(document.getElementById("txtTaxation").value==2 && document.getElementById("changeReason").value==0){
		alert("Please Select Standard Reason");
		return false;
	}
	if(!(isView==null || isView == 'null')){
		return false;
	}
	
	var callerWindowObj = dialogArguments;
	var orderNo = callerWindowObj.document.getElementById('poNumber').value
	//var jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleName,userId);
	var shortCode = jsonrpc.processes.getShortCode(roleName);
	
	if(orderDetails.list.length>0)
	{
		stage=orderDetails.list[0].stageName;
		if(shortCode==stage)
		{
			
		}
		else if(stage=="New" || stage=="NEW")
		{
			
		}
		else if(stage=="Billing Trigger" )
		{
			alert("You are not authorised to save or update the values");
			return false;
		}
		else if(stage=="Partial Initiated" ||(stage=="Partial Publish" && (shortCode=="COPC" || shortCode=="SED")))
		{
			
		}
		else
		{
			alert("You are not authorised to save or update the values");
			return false;
		}
	}
	
	
	if(editSolutionChangeOldProduct == 1 ){
		document.getElementById('txtPODetailNo').isRequired = 0;
		document.getElementById('poDetailNo').isRequired = 0;
		if(document.getElementById('txtBillingPODate') == null || document.getElementById('txtBillingPODate').value ==null){
			document.getElementById('txtBillingPODate').isRequired = 0;
		}
		var txtEntity = document.getElementById('txtEntity');
		if( txtEntity == null || txtEntity.value ==null) {
			txtEntity.isRequired = 0;
			txtEntity.value = 0;
			document.getElementById("legalEntityTXT").value = "Select Legal Entity";
			document.getElementById("legalEntityTXT").isRequired = 0;
		}
		var txtLicenceCo = document.getElementById('txtLicenceCo');
		if( txtLicenceCo == null || txtLicenceCo.value ==null) {
			txtLicenceCo.isRequired = 0;
			txtLicenceCo.value = 0;
			document.getElementById("licenseCo").value = "Select Licence Company";
			document.getElementById("licenseCo").isRequired = 0;
		}
		if(document.getElementById('txtPoId') == null || document.getElementById('txtPoId').value ==null) {
			document.getElementById('txtPoId').isRequired = 0;
		}
		if(document.getElementById('txtCntrtMonths') == null || document.getElementById('txtCntrtMonths').value ==null) {
			document.getElementById('txtCntrtMonths').isRequired = 0;
		}
	}   
	var eLogicalCircuitId="";
	var infraOderNo="";
	var metasolvCircuitId="";
	var eLogicalCircuitId_new="";
	var infraOderNo_new="";
	var metasolvCircuitId_new="";
	var linKageFlag="NEW"
	var flagForDropdown;
	if(document.getElementById('hdnServiceSummary').value==1)
	{
		but=document.getElementById('btnServiceSummary');
		if(but.value=="+")
		{
			butSummaryFlag=true;
			show('tblServiceSummary',but);
		}
		
		var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
		var attributeID=new Array();
		var attributeVal=new Array();
		var attributeExpVal=new Array();
		var attributeName=new Array();
		var attributeMandatory=new Array();
		var attServiceSummMand = new Array();
		var newAttributeVal = new Array();
		var attValueID=new Array();
		var attributeMaxLength = new Array();
		var attributeNewMandatory=new Array();
		var attfor_validation =new  Array();
		// [101010] START
		var logicalCircuitIdLength=document.getElementById('eLogicalCircuitId_new').value.length;
		var infraOrderNoLength=document.getElementById('InfraOderNo_new').value.length;
		var metasolvCircuitIdLength=document.getElementById('metasolvCircuitId_new').value.length;
		if(logicalCircuitIdLength > 25)
		{
		alert("Logical Circuit ID can not be more than 25 char");
		document.getElementById('eLogicalCircuitId_new').focus();
		return false;
		}
		else if(infraOrderNoLength > 25)
		{
		alert("Infra Order No can not be more than 25 char");
		document.getElementById('InfraOderNo_new').focus();
		return false;
		}
		else if(metasolvCircuitIdLength > 25)
		{
		alert("Metasolv Circuit ID can not be more than 25 char");
		document.getElementById('metasolvCircuitId_new').focus();
		return false;
		}
		// [101010] END	
		
		for(i=1;i<=countAttributes;i++)
		{
			var editSolutionChangeOldProduct=0;			
			if(hdnChangeTypeId==2 && (changeSubTypeID==8 || changeSubTypeID==9 || changeSubTypeID==10) || (hdnChangeTypeId==1 && changeSubTypeID==20) )
			{
				editSolutionChangeOldProduct=1;
			}
			//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
			if(editSolutionChangeOldProduct==1) //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10)
			{
				if(document.getElementById('newprodAttMandatory_'+i).value=="1" && (document.getElementById('newprodAttVal_'+i).value=="" ||document.getElementById('newprodAttVal_'+i).selectedIndex=="0" ))
				{
					if(document.getElementById("newprodAttVal_"+i).disabled==false && document.getElementById("newprodAttVal_"+i).readOnly==false)//Arbor Mig related Changes : Done By Ashutosh
					{							 
					alert("Please Input New Values in Service Summary Section!! ");
					//START[13082012]AutoSuggest for BCP
					if(document.getElementById('prodExpVal_'+i).value == "DROPDOWN" || document.getElementById('prodExpVal_'+i).value == "LOV")
						document.getElementById('autoSuggNewProdAttVal_'+i).focus();
					else
					document.getElementById('newprodAttVal_'+i).focus();
					//END[13082012]AutoSuggest for BCP
					return false;
					}
				}

			}
			if(document.getElementById('prodAttMandatory_'+i).value=="1" && document.getElementById('prodAttVal_'+i).value=="") 
			{			
			   if(document.getElementById('prodAttVal_'+i).isSummaryReq==1)
			   {	
				alert("Please input values in Service Summary Section!!");
					//START[13082012]AutoSuggest for BCP
					if(document.getElementById('prodExpVal_'+i).value == "DROPDOWN" || document.getElementById('prodExpVal_'+i).value == "LOV")
						document.getElementById('autoSuggProdAttVal_'+i).focus();
					else
				document.getElementById('prodAttVal_'+i).focus();
					//END[13082012]AutoSuggest for BCP	
				return false;
				}
				else
				{
						if(document.getElementById('prodAttVal_'+i).value == 1 && document.getElementById('hdnProdAttVal_'+i).value == 2469)
						{
							flagForDropdown = 1;
						}
						if(document.getElementById('prodAttVal_'+i).value == 2 && document.getElementById('hdnProdAttVal_'+i).value == 2469)
						{
							flagForDropdown = 2;
						}
					attributeVal[i-1]=document.getElementById('prodAttVal_'+i).value;
					attributeID[i-1]=document.getElementById('hdnProdAttVal_'+i).value;
					attributeExpVal[i-1]=document.getElementById('prodExpVal_'+i).value;
					attributeName[i-1]=document.getElementById('prodAttName_'+i).value;
					attributeMandatory[i-1]=document.getElementById('prodAttMandatory_'+i).value;
					attServiceSummMand[i-1]=document.getElementById('prodAttVal_'+i).isSummaryReq;
					//[003]start
					attributeMaxLength[i-1]=document.getElementById('prodAttMaxLength_'+i).value;
					//[003]end
					//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
					if(editSolutionChangeOldProduct==1) //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10)
						newAttributeVal[i-1]=document.getElementById('newprodAttVal_'+i).value;
					else
						newAttributeVal[i-1]=document.getElementById('prodAttVal_'+i).value;
					attValueID[i-1]=document.getElementById('hdnAttributeValueID_'+i).value;
				}
			}
			else
			{	
				if(document.getElementById('prodAttVal_'+i).value == 1 && document.getElementById('hdnProdAttVal_'+i).value == 2469)
						{
							flagForDropdown = 1;
						}
						if(document.getElementById('prodAttVal_'+i).value == 2 && document.getElementById('hdnProdAttVal_'+i).value == 2469)
						{
							flagForDropdown = 2;
						}
				attributeVal[i-1]=document.getElementById('prodAttVal_'+i).value;
				attributeID[i-1]=document.getElementById('hdnProdAttVal_'+i).value;
				attributeExpVal[i-1]=document.getElementById('prodExpVal_'+i).value;
				attributeName[i-1]=document.getElementById('prodAttName_'+i).value;
				attributeMandatory[i-1]=document.getElementById('prodAttMandatory_'+i).value;
				attServiceSummMand[i-1]=document.getElementById('prodAttVal_'+i).isSummaryReq;
				//[003]start
				attributeMaxLength[i-1]=document.getElementById('prodAttMaxLength_'+i).value;
				//[003]end
				//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
				if(editSolutionChangeOldProduct==1) //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10)
					newAttributeVal[i-1]=document.getElementById('newprodAttVal_'+i).value;
				else
						newAttributeVal[i-1]=document.getElementById('prodAttVal_'+i).value;
				attValueID[i-1]=document.getElementById('hdnAttributeValueID_'+i).value;
			}
			if(editSolutionChangeOldProduct==1)
			{ 
					attributeNewMandatory[i-1]=document.getElementById("newprodAttVal_"+i).isSummaryReq;
			}
			else
			{
					attributeNewMandatory[i-1]=document.getElementById("prodAttVal_"+i).isSummaryReq;
			}
			
				// added by manisha for service summary validation start defect no : 19032013001
			attfor_validation[i-1]=document.getElementById('txt_prod_attvalidation_'+i).value;
			
				if(fproduct_att_validation(attributeName[i-1],newAttributeVal[i-1],attributeExpVal[i-1],attfor_validation[i-1],attributeNewMandatory[i-1])==false)
				{
					return false;
				
				}
			
			// end	
			//Ramana start
//
				if(document.getElementById('hdnProdAttVal_'+i).value == 2470 && flagForDropdown == 2)
						{
							//var jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
    	       				var flag = jsonrpc.processes.lsiValidationForMediaExchangeAssociation(document.getElementById('newprodAttVal_'+i).value);
    	       				if(flag == 1)
    	       				{
								alert('Please enter a valid LSI');
								document.getElementById('newprodAttVal_'+i).focus();
								return false;
							}
						}
				if(document.getElementById('hdnProdAttVal_'+i).value == 2476 && flagForDropdown == 1)
						{
							//var jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
    	       				var flag = jsonrpc.processes.lsiValidationForMediaExchangeAssociation(document.getElementById('newprodAttVal_'+i).value);
    	       				if(flag == 1)
    	       				{
								alert('Please enter a valid LSI');
								document.getElementById('newprodAttVal_'+i).focus();
								return false;
							}
						}
			//Ramana End
		}
		     eLogicalCircuitId=document.getElementById('eLogicalCircuitId').value;
			 infraOderNo=document.getElementById('infraOderNo').value;
			 metasolvCircuitId=document.getElementById('metasolvCircuitId').value;	
			 var callerWindowObj = dialogArguments;
			 //Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
			if(editSolutionChangeOldProduct==1) //&& callerWindowObj.document.getElementById("subChangeTypeId").value!= 10)
			 {
			 linKageFlag="CHANGE_VIEW"
			 eLogicalCircuitId_new=document.getElementById('eLogicalCircuitId_new').value;
			 infraOderNo_new=document.getElementById('infraOderNo_new').value;
			 metasolvCircuitId_new=document.getElementById('metasolvCircuitId_new').value;	
			 }
	}

	but=document.getElementById('btnServiceSummary');
		
		if(but.value=="-" && butSummaryFlag==true)
		{			
			show('tblServiceSummary',but);
		}

	if(document.getElementById('hdnBillingInfo').value==1){	
		if(selectAutoSuggest("txtPODetailNo","Cust PO Detail No", "poDetailNo")==false)
			return false;
		if(!(editSolutionChangeOldProduct == 1 && document.getElementById('txtBillingLvl').value == 2)){//Ideally this if condition line shoule be removed because now existing Line has no differnt behaviour for PO Level, it is now same as LSI or Account level . 
			if(selectAutoSuggest("txtEntity","Entity", "legalEntityTXT")==false)
				return false;
			if(selectAutoSuggest("txtLicenceCo","Licence Company", "licenseCo")==false)
				return false;
		}
		if(selectAutoSuggest("txtBillingformat","Billing Format", "billingFormat")==false)
			return false;
		if(selectAutoSuggest("txtBillingType","Billing Type","billingType")==false)
			return false;
		if(selectAutoSuggest("txtTaxation","Taxation","taxationID")==false)
			return false;
		if(selectAutoSuggest("txtBillingLvl","Billing Level", "billingLevel")==false)
			return false;
		//[010] Start
		if(editSolutionChangeOldProduct!=1){	
			//[005] Start
			if(document.getElementById('hdnConfigValue').value==1){
				if(document.getElementById('txtLicenceCo').value!=282 && document.getElementById('txtLicenceCo').value!=382){
					alert("Invalid License Company!!For 95p Orders, You have to select 402-BAL 95P or 402-BAL 95P_GB License Company");
					return false;
				}	//[005] End
			}
		}
		
		//[010] End	
	}

	if(document.getElementById('hdnLocationInfo').value==1)
	{
		if(selectDropdown("ddPrimaryLocType","Primary Location")==false)
		return false;
		
		if(selectDropdown("ddPrimaryLocType","Primary Location")==false)
		return false;
		
		if(document.getElementById('ddPrimaryLocType').value=="1")
		{
			if(selectDropdown("txtPriCustLocationSuggest","Primary BCP ID")==false)
			return false;
		}
	
		var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		if(chkChargeLine.length>0)
		{			
	
		if(document.getElementById('txtCPeriod').isRequired==1 && document.getElementById('txtCPeriod').value == "" )
		{
			if(selectDropdown("ddSecondaryLocType","Secondary Location")==false)
			return false;
			
			if(document.getElementById('ddSecondaryLocType').value=="1")
			{
				if(selectDropdown("txtSecCustLocationSuggest","Secondary BCP ID")==false)
				return false;
			}
		}
		}
		
	}
	
	if(document.getElementById('hdnHardwareInfo').value==1){
		if(selectAutoSuggest("txtStore","Store","txtStoreText")==false)
			return false;
		if(selectDropdown("txtdispatchAddress","Dispatch Address Code")==false)
			return false;
	}
	//Meenakshi
	
	
		//Start[007]
	if(document.getElementById('hdnComponentInfo').value==1)
	{
	
		var myComponentList=[];
		var chkComponentLine=document.getElementsByName('chkComponents');
		var myComponentList=[];
		
		for(i=0;i<chkComponentLine.length;i++)
		{
		
		
			var chkboxComponent=chkComponentLine[i];
			var index=chkboxComponent.value;
			
			
			if(document.getElementById("txtCompStartDateLogic"+index).value=="-1"){
				alert("Please Fill Component Start Date Logic!");
				return false;
			}				
			if(document.getElementById("txtCompEndDateLogic"+index).value=="-1"){
				alert("Please Fill Component End Date Logic!");
				return false;
			}	
			var objComponents={"javaClass": "com.ibm.ioes.forms.ComponentsDto",
						componentInfoID:Number(document.getElementById("component_infoID"+index).value),
						componentID:Number(document.getElementById("components"+index).value),
						packageID:Number(document.getElementById("packageID"+index).value),
						componentInfoID_String:(document.getElementById("component_infoID"+index).value),
						componentName:(document.getElementById("componentsName"+index).value),
					    packageName:(document.getElementById("packageName"+index).value),
						componentStatus:(document.getElementById("status"+index).value),
					    startDateLogic:(document.getElementById("txtCompStartDateLogic"+index).value),
						endDateLogic:(document.getElementById("txtCompEndDateLogic"+index).value),
						start_date:(document.getElementById("startdate"+index).value),
						end_date:(document.getElementById("enddate"+index).value),
						startDateDays:Number(document.getElementById("txtStartDays"+index).value),
						startDateMonth:Number(document.getElementById("txtStartMonth"+index).value),
						endDateDays:Number(document.getElementById("txtEndDays"+index).value),
						endDateMonth:Number(document.getElementById("txtEndMonth"+index).value),
						isReqComponentid:Number(document.getElementById('components'+index).isRequired),
						isReqComponentname:Number(document.getElementById('componentsName'+index).isRequired),
						isReqPackageid:Number(document.getElementById('packageID'+index).isRequired),
						isReqPackagename:Number(document.getElementById('packageName'+index).isRequired),
						isReqStatus:Number(document.getElementById('status'+index).isRequired),
						isReqStartdatelogic:Number(document.getElementById('txtCompStartDateLogic'+index).isRequired),
						isReqEnddateLogic:Number(document.getElementById('txtCompEndDateLogic'+index).isRequired),
						isReqStartDate:Number(document.getElementById('startdate'+index).isRequired),
						isReqEndDate:Number(document.getElementById('enddate'+index).isRequired)
					};
			myComponentList[i]=objComponents;
			
		}
	
		componentsList={"javaClass": "java.util.ArrayList",
				"list": myComponentList
				};
		 }
	
	
	//Meenakshi
	if(document.getElementById('hdnChargeInfo').value==1){
		var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		for(i=0;i<chkChargeLine.length;i++){
			var chkboxCharge=chkChargeLine[i];
			var index=chkboxCharge.value;
			if(document.getElementById('txtCName'+index).isRequired=="1"){
				if(isBlank("txtCName"+index,"Charge Name")==false)
				return false;
			}

			if(checkTotalAmount()==false){
				return false;
			}
		}
		
	}
	
	if(document.getElementById('hdnChargeInfo').value==1)
	{

		var myChargeList=[];
		var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		
		var myList=[];
		for(i=0;i<chkChargeLine.length;i++)
		{
			var chkboxCharge=chkChargeLine[i];
			var index=chkboxCharge.value;
			//[012] Start
			//if(document.getElementById('hdnHardwareInfo').value==1)
			//{	
			//[012] End
			
		    var payment11=document.getElementById('txtPayment1'+index).value;
		
			var payment22=document.getElementById('txtPayment2'+index).value;
			
			var payment33=document.getElementById('txtPayment3'+index).value;
			
			var payment44=document.getElementById('txtPayment4'+index).value;
		
			if(payment11 == "" && payment22 == "" && payment33 == "" && payment44 == "")
			{
				//[012] Start
				document.getElementById('txtPayment1'+index).value=0;
				document.getElementById('txtPayment2'+index).value=0;
				document.getElementById('txtPayment3'+index).value=0;
				document.getElementById('txtPayment4'+index).value=0;
				alert("Please Inputs Payment terms");
				return false;
				//[012] Start
			}
			else
			{
				if (payment11 > 100 || payment22 > 100 || payment33 > 100 || payment44 > 100 )
				{
					alert('Sum of all payments should be equal to 100');
					//[012] Start
					/*
					document.getElementById('txtPayment1'+index).value = "";
					document.getElementById('txtPayment2'+index).value = "";
					document.getElementById('txtPayment3'+index).value = "";
					document.getElementById('txtPayment4'+index).value = "";
					document.getElementById('txtPayment1'+index).focus();
					*/
					//[012] Start
					return false;
					
				}
				if (payment11 == "")
				{
					payment11=0;
				}
				if (payment22 == "")
				{
					payment22=0;
				}
				if (payment33 == "")
				{
					payment33=0;
				}
				if (payment44 == "")
				{
					payment44=0;
				}
				var payment = parseFloat(payment11) + parseFloat(payment22) + parseFloat(payment33) + parseFloat(payment44);
		
				if(payment != 100)
				{	
					alert('Sum of all payments1 should be equal to 100');
					if($('#txtPayment1'+index).is(":visible"))
						document.getElementById('txtPayment1'+index).focus();
					return false;
				}	
				else
				{
					payment1=payment11;
					payment2=payment22;
					payment3=payment33;
					payment4=payment44;
					
				}
	 		}
		 	//[012] Start			
			//}
			//[012] End
			var excludecharge=0;	
	        if(document.getElementById('chkexcludeCharges'+index).checked==true)
			{
			   excludecharge=1;
			}
			else
			{
			    excludecharge=0;
			}
			if(document.getElementById('txtCPeriod'+index).editable != 'undefined')
			{
				if(document.getElementById('txtCPeriod'+index).editable == 'allow')
				{
					document.getElementById('txtCPeriod'+index).isRequried = 0;
				}
			}
			if(document.getElementById('isAddedInCurrentService'+index)!=null){
				if(document.getElementById('isAddedInCurrentService'+index).value ==1 && document.getElementById('txtPODetailNo').value == 0){
					alert('Please Select PO for the Charge you have added.');
					return false;
				}
			}
			var ob={"javaClass": "com.ibm.ioes.forms.ChargesDetailDto",
						chargeName:document.getElementById("txtCName"+index).value,
						chargePeriod:Number(document.getElementById("txtCPeriod"+index).value),
						chargeType:Number(document.getElementById("ddCType"+index).value),
						chargeAmount_String:document.getElementById("txtCTAmt"+index).value,
						chargeFrequency:document.getElementById("txtCFrequency"+index).value,
						chargeFrequencyAmt_String:document.getElementById("txtCFreqAmt"+index).value,
						startDateLogic:document.getElementById("txtCStartDate"+index).value,
						endDateLogic:document.getElementById("txtCEndDate"+index).value,
						startDateDays:Number(document.getElementById("txtStartDays"+index).value),
						startDateMonth:Number(document.getElementById("txtStartMonth"+index).value),
						endDateDays:Number(document.getElementById("txtEndDays"+index).value),
						endDateMonth:Number(document.getElementById("txtEndMonth"+index).value),
						excludecheck:Number(excludecharge),						
						isReqDdCType:Number(document.getElementById('ddCType'+index).isRequired),
						isReqTxtCName:Number(document.getElementById('txtCName'+index).isRequired),
						isReqTxtCPeriod:Number(document.getElementById('txtCPeriod'+index).isRequired),
						isReqTxtCTAmt:Number(document.getElementById('txtCTAmt'+index).isRequired),
						isReqTxtCFrequency:Number(document.getElementById('txtCFrequency'+index).isRequired),
						isReqTxtCFreqAmt:Number(document.getElementById('txtCFreqAmt'+index).isRequired),
						isReqTxtCStartDate:Number(document.getElementById('txtCStartDate'+index).isRequired),
						isReqTxtCEndDate:Number(document.getElementById('txtCEndDate'+index).isRequired),
						chargeAnnotation:document.getElementById("txtCAnnotation"+index).value,
						isReqTxtCAnnotation:Number(document.getElementById('txtCAnnotation'+index).isRequired),
						isReqTxtCStartDays:Number(document.getElementById("txtStartDays"+index).isRequired),
						isReqTxtCStartMonths:Number(document.getElementById("txtStartMonth"+index).isRequired),
						isReqTxtCEndDays:Number(document.getElementById("txtEndDays"+index).isRequired),
						isReqTxtCEndMonths:Number(document.getElementById("txtEndMonth"+index).isRequired),
						chargeRemarks:document.getElementById("txtRemarks"+index).value,
						paymentTerm1:Number(document.getElementById('txtPayment1'+index).value),
						paymentTerm2:Number(document.getElementById('txtPayment2'+index).value),
						paymentTerm3:Number(document.getElementById('txtPayment3'+index).value),
						paymentTerm4:Number(document.getElementById('txtPayment4'+index).value),
						//lineItemName:document.getElementById("cmbLineItem"+index).value,
						//subLineItemName:document.getElementById("cmbSubLineItem"+index).value,
						ldDateClause:document.getElementById("txtLDDateClause"+index).value,
						delayedTimeInDayes:Number(document.getElementById("txtDelayedTimeInDays"+index).value),
						ldPercentage:Number(document.getElementById("txtLDPercentage"+index).value),
						maxPercentage:Number(document.getElementById("txtMaxPenaltyPercentage"+index).value),
						//isReqLineItem:Number(document.getElementById('cmbLineItem'+index).isRequired),
						//isReqSubLineItem:Number(document.getElementById('cmbSubLineItem'+index).isRequired),
						taxRate:document.getElementById("txtTaxRate"+index).value,
						isReqTxtRemarks:Number(document.getElementById("txtRemarks"+index).isRequired),
						obLinkChargeId:Number(document.getElementById("txtOBCharge"+index).value)
					};
					
			myList[i]=ob;
			
			if(document.getElementById('txtCTAmt'+index).isRequired==0 && document.getElementById('txtCTAmt'+index).value == ""){
			document.getElementById('txtCTAmt'+index).value=0;
			}		
			
			if(document.getElementById('txtCFreqAmt'+index).isRequired==0 && document.getElementById('txtCFreqAmt'+index).value == "" ){
			document.getElementById('txtCFreqAmt'+index).value=0;
			}	
				
			if(document.getElementById('txtCPeriod'+index).isRequired==0 && document.getElementById('txtCPeriod'+index).value == "" ){
			document.getElementById('txtCPeriod'+index).value=0;
			}		
			
			if(document.getElementById('txtCFrequency'+index).isRequired==0 && document.getElementById('txtCFrequency'+index).value == "" ){
			document.getElementById('txtCFrequency'+index).value="0";
			}		
			
			if(document.getElementById('txtCStartDate'+index).isRequired==0  && document.getElementById('txtCStartDate'+index).value == "" ){
			document.getElementById('txtCStartDate'+index).value="0";	
			}			
			
			if(document.getElementById('txtCEndDate'+index).isRequired==0  && document.getElementById('txtCEndDate'+index).value == "" ){
			document.getElementById('txtCEndDate'+index).value="0";	
			}
			
		}
		var chargesList={"javaClass": "java.util.ArrayList",
				"list": myList
				};
				
		var jsData =
		{
			prodAttID: attributeID,
			prodAttValue:attributeVal,
			serviceDetailID:serviceProductID,
			serviceProductID:0,
			serviceId:servicesID,
			podetailID:document.getElementById('txtPODetailNo').value,
			chargesDetails :chargesList
		};
  
	    //jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
		   try{
	
		    var retDto = jsonrpc.processes.validateCharges(jsData);
	
		    }
		    catch(e)
		    {	alert("exception :  "+ e);
		    }
    var chargesList={"javaClass": "java.util.ArrayList",
			"list": myList};
    var chargesDetailList;
 
    	var myChargeList=[];
		var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		var myList=[];
		totalExtraChrgeAmount=0;
		for(i=0;i<chkChargeLine.length;i++)
		{
			var chkboxCharge=chkChargeLine[i];
			var index=chkboxCharge.value;
			 var excludecharge=0;	
	                      
	      if(document.getElementById('chkexcludeCharges'+index).checked==true)
			{
			   excludecharge=1;
			}
			else
			{
			    excludecharge=0;
			}
			//start[008]
			//add  by anil for clep requirement
			if(clepState==1){
				if(document.getElementById("hdnChargeId"+index).value=="" ||document.getElementById("hdnChargeId"+index).value=="0" ){
					totalExtraChrgeAmount=Number(totalExtraChrgeAmount)+Number(document.getElementById("txtCTAmt"+index).value);
				}
			}
			//add  by anil for clep requirement
			
			var ob={"javaClass": "com.ibm.ioes.forms.ChargesDetailDto",
						chargeName:document.getElementById("txtCName"+index).value,
						chargePeriod:Number(document.getElementById("txtCPeriod"+index).value),
						chargeType:Number(document.getElementById("ddCType"+index).value),
						chargeAmount_String:document.getElementById("txtCTAmt"+index).value,
						chargeFrequency:document.getElementById("txtCFrequency"+index).value,
						chargeFrequencyAmt_String:document.getElementById("txtCFreqAmt"+index).value,
						startDateLogic:document.getElementById("txtCStartDate"+index).value,
						endDateLogic:document.getElementById("txtCEndDate"+index).value,
						startDateDays:Number(document.getElementById("txtStartDays"+index).value),
						startDateMonth:Number(document.getElementById("txtStartMonth"+index).value),
						endDateDays:Number(document.getElementById("txtEndDays"+index).value),
						endDateMonth:Number(document.getElementById("txtEndMonth"+index).value),
						excludecheck:Number(excludecharge),				
						isReqDdCType:Number(document.getElementById('ddCType'+index).isRequired),
						isReqTxtCName:Number(document.getElementById('txtCName'+index).isRequired),
						isReqTxtCPeriod:Number(document.getElementById('txtCPeriod'+index).isRequired),
						isReqTxtCTAmt:Number(document.getElementById('txtCTAmt'+index).isRequired),
						isReqTxtCFrequency:Number(document.getElementById('txtCFrequency'+index).isRequired),
						isReqTxtCFreqAmt:Number(document.getElementById('txtCFreqAmt'+index).isRequired),
						isReqTxtCStartDate:Number(document.getElementById('txtCStartDate'+index).isRequired),
						isReqTxtCEndDate:Number(document.getElementById('txtCEndDate'+index).isRequired),
						chargeInfoID_String:document.getElementById("hdnChargeId"+index).value,
						isReqTxtCAnnotation:Number(document.getElementById('txtCAnnotation'+index).isRequired),
						chargeAnnotation:document.getElementById("txtCAnnotation"+index).value,
						chargeRemarks:document.getElementById("txtRemarks"+index).value,
						paymentTerm1:Number(document.getElementById('txtPayment1'+index).value),
						paymentTerm2:Number(document.getElementById('txtPayment2'+index).value),
						paymentTerm3:Number(document.getElementById('txtPayment3'+index).value),
						paymentTerm4:Number(document.getElementById('txtPayment4'+index).value),
						//lineItemName:document.getElementById("cmbLineItem"+index).value,
						//subLineItemName:document.getElementById("cmbSubLineItem"+index).value,
						ldDateClause:document.getElementById("txtLDDateClause"+index).value,
						delayedTimeInDayes:Number(document.getElementById("txtDelayedTimeInDays"+index).value),
						ldPercentage:Number(document.getElementById("txtLDPercentage"+index).value),
						maxPercentage:Number(document.getElementById("txtMaxPenaltyPercentage"+index).value),
						//isReqLineItem:Number(document.getElementById('cmbLineItem'+index).isRequired),
						//isReqSubLineItem:Number(document.getElementById('cmbSubLineItem'+index).isRequired),
						taxRate:document.getElementById("txtTaxRate"+index).value,
						isReqTxtRemarks:Number(document.getElementById("txtRemarks"+index).isRequired),
						obLinkChargeId:Number(document.getElementById("txtOBCharge"+index).value)
						};
			myList[i]=ob;
		}
		
		chargesDetailList={"javaClass": "java.util.ArrayList",
				"list": myList
				};
    }
	var callerWindowObj = dialogArguments;
	if(document.getElementById('hdnServiceSummary').value==1)
	{
		if(document.getElementById('hdnBillingInfo').value==1){
		var txtPODetailNo = document.getElementById('txtPODetailNo');
		if(txtPODetailNo.isRequired=="1" && txtPODetailNo.value == "0"){
			alert("Please enter PO ID");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+"){
				butBillFlag = true;
				show('tblBillingAndChargesInformation',but);
			}
			document.getElementById('poDetailNo').focus();
			return false;
		}else if(txtPODetailNo.isRequired=="0"  && txtPODetailNo.value == "0" ){
			txtPODetailNo.value=0;
			document.getElementById('poDetailNo').value="";
		}
		var txtBillingCP = document.getElementById('txtBillingCP');	
		if(txtBillingCP.isRequired=="1"  && txtBillingCP.value == "0" ){
			alert("Please enter Credit Period");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+"){
				butBillFlag = true;
				show('tblBillingAndChargesInformation',but);
			}
			document.getElementById('billingCP').focus();
			return false;
		}else if(txtBillingCP.isRequired=="0"  && txtBillingCP.value == "0" ){
			txtBillingCP.value=0;
			document.getElementById('billingCP').value="Select Credit Period";
		}
		var txtEntity = document.getElementById('txtEntity');
		if(txtEntity.isRequired=="1"  && txtEntity.value == "0" ){
			alert("Please enter Legal Entity");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+"){
				butBillFlag = true;
				show('tblBillingAndChargesInformation',but);
			}
			document.getElementById('legalEntityTXT').focus();
			return false;
		}else if(txtEntity.isRequired=="0"  && txtEntity.value == "0" ){
		   		document.getElementById("txtEntity").value = "0";
			   	document.getElementById("legalEntityTXT").value = "Select Legal Entity";
		}
		var txtBillingMode = document.getElementById('txtBillingMode');
		if(txtBillingMode.isRequired=="1"  && txtBillingMode.value == "0" ){
			alert("Please enter Billing Mode");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+"){
				butBillFlag = true;
				show('tblBillingAndChargesInformation',but);
			}
			document.getElementById('billingMode').focus();
			return false;
		}else if(txtBillingMode.isRequired=="0"  && txtBillingMode.value == "0" ){
			txtBillingMode.value=0;
			document.getElementById('billingMode').value="Select Billing Mode";//TTS
		}
		var txtBillingformat = document.getElementById('txtBillingformat');
		if(txtBillingformat.isRequired=="1"  && txtBillingformat.value == "0"){
			alert("Please enter Bill Format");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+"){
				butBillFlag = true;
				show('tblBillingAndChargesInformation',but);
			}
			document.getElementById('billingFormat').focus();
			return false;
		}else if(txtBillingformat.isRequired=="0"  && txtBillingformat.value == "0" ){
			txtBillingformat.value=0;
			document.getElementById('billingFormat').value="Select Bill Format";
		}
		var txtLicenceCo = document.getElementById('txtLicenceCo');
		if(txtLicenceCo.isRequired=="1"  && txtLicenceCo.value == "0"){
			alert("Please enter Licence Co");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+"){
				butBillFlag = true;
				show('tblBillingAndChargesInformation',but);
			}
			$("#licenseCo").focus();
			return false;
		}else if(txtLicenceCo.isRequired=="0"  && txtLicenceCo.value == "0"){
			document.getElementById('txtBillingformat').value=0;
			document.getElementById('billingFormat').value="Select Bill Format";
			txtLicenceCo.value=0;
			$("#licenseCo").val("Select Licence Company");
		}
		
		if(document.getElementById('txtNoticePeriod').isRequired=="1"  && document.getElementById('txtNoticePeriod').value == "" )
		{
			alert("Please enter Notice Period (months)");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtNoticePeriod').focus();
			return false;
		}
		else if(document.getElementById('txtNoticePeriod').isRequired=="0"  && document.getElementById('txtNoticePeriod').value == "" )
		{
			document.getElementById('txtNoticePeriod').value=0;
		}
		var txtBillingType = document.getElementById('txtBillingType');
		if(txtBillingType.isRequired=="1"  && txtBillingType.value == "0" ){
			alert("Please enter Billing Type");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+"){
				butBillFlag = true;
				show('tblBillingAndChargesInformation',but);
			}
			//document.getElementById('txtBillingType').focus();
			$("#billingType").focus();
			return false;
		}else if(txtBillingType.isRequired=="0"  && txtBillingType.value == "0" ){
			txtBillingType.value=0;
			$("#billingType").val("Select Bill Type");
		}
		var txtTaxation = document.getElementById('txtTaxation');
		if(txtTaxation.isRequired=="1" && txtTaxation.value == "0"){
			alert("Please enter Taxation");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+"){
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
			}
			document.getElementById('taxationID').focus();
			return false;
		//Puneet changing for auto suggest	
		}else if(txtTaxation.isRequired=="0" && txtTaxation.value == "0"){
			txtTaxation.value=0;
			document.getElementById('taxationID').value="Select Taxation";
		}
			if(document.getElementById('txtCmtPeriod').isRequired=="1"  && document.getElementById('txtCmtPeriod').value == "" )
			{
				alert("Please enter Commitment Period (Months)");
				but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
				document.getElementById('txtCmtPeriod').focus();
				return false;
			}		
		else if(document.getElementById('txtCmtPeriod').isRequired=="0"  && document.getElementById('txtCmtPeriod').value == "" )
		{
			document.getElementById('txtCmtPeriod').value=0;
		}
			var txtBillingLvl = document.getElementById('txtBillingLvl');
			if(txtBillingLvl.isRequired=="1"  && txtBillingLvl.value == "0" ){
				alert("Please enter Billing Level");
				but=document.getElementById('btnBillingAndChargesInformation');
				if(but.value=="+"){
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
				}
				//document.getElementById('txtBillingLvl').focus();
				$("#billingLevel").focus();
				return false;
			}else if(txtBillingLvl.isRequired=="0"  && txtBillingLvl.value == "0" ){
				txtBillingLvl.value=0;
				$("#billingLevel").val("Select Billing Level");
			}
		if(document.getElementById('txtBillingLevelNo').isRequired=="1"  && document.getElementById('txtBillingLevelNo').value == "" )
		{
			alert("Please enter Billing Level No");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingLevelNo').focus();
			return false;
		}
		else if(document.getElementById('txtBillingLevelNo').isRequired=="0"  && document.getElementById('txtBillingLevelNo').value == "" )
		{
			document.getElementById('txtBillingLevelNo').value=0;
		}
		if(document.getElementById('txtPenaltyClause').isRequired=="1"  && document.getElementById('txtPenaltyClause').value == "" )
		{
			alert("Please enter Penalty Clause");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtPenaltyClause').focus();
			return false;
		}
		else if(document.getElementById('txtPenaltyClause').isRequired=="0"  && document.getElementById('txtPenaltyClause').value == "" )
		{
			document.getElementById('txtPenaltyClause').value=0;
		}
		
		//satyapan OSP
		if((document.getElementById('txtOSPRegNo').isRequired=="1" ||document.getElementById('txtOSPTagging').value==1 ) && (document.getElementById('txtOSPRegNo').value == "" || document.getElementById('txtOSPRegNo').value ==null) )
		{
			alert("Please enter OSPRegNO");
			document.getElementById('txtOSPRegNo').focus();
			return false;
		}
	  	else if(document.getElementById('txtOSPRegNo').isRequired=="0" && document.getElementById('txtOSPRegNo').value=="")
		{
			document.getElementById('txtOSPRegNo').value="";
		}	
		 
		if((document.getElementById('txtOSPRegDate').isRequired=="1" ||document.getElementById('txtOSPTagging').value==1 )  && (document.getElementById('txtOSPRegDate').value == "" || document.getElementById('txtOSPRegDate').value ==null) )
		{
			alert("Please enter OSPRegDate");
			
			document.getElementById('txtOSPRegDate').focus();
			return false;
		}
		else if(document.getElementById('txtOSPRegDate').isRequired=="0" && document.getElementById('txtOSPRegDate').value=="" )
		{
			document.getElementById('txtOSPRegDate').value="";
		}
		
		
		//END satyapan OSP
			//WARRANTY CLAUSE ADDED BY MANISHA START
		if(document.getElementById('txtWarrantClause').isRequired=="1"  && document.getElementById('txtWarrantClause').value == "" )
		{
			alert("Please enter Warranty Clause");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtWarrantClause').focus();
			return false;
		}
		else if(document.getElementById('txtWarrantClause').isRequired=="0"  && document.getElementById('txtWarrantClause').value == "" )
		{
			document.getElementById('txtWarrantClause').value=0;
		}	
		
		//WARRANTY CLAUSE ADDED BY MANISHA end
			//  bcp details for services ADDED BY MANISHA START
		if(document.getElementById('hdnHardwareInfo').value!=1)
		{	
			if(document.getElementById('bbPrimaryBCPService').isRequired=="1"  && (document.getElementById('bbPrimaryBCPService').value == "" || document.getElementById('txtBCPAddressSuggestService').value=="") )
			{
				alert("Please enter BCP details for Service");
				but=document.getElementById('btnBillingAndChargesInformation');
						if(but.value=="+")
						{
							show('tblBillingAndChargesInformation',but);
						}
				document.getElementById('txtBCPAddressSuggestService').focus();
				
				return false;
			}
			else if(document.getElementById('bbPrimaryBCPService').isRequired=="0"  && (document.getElementById('bbPrimaryBCPService').value == "" || document.getElementById('txtBCPAddressSuggestService').value=="") )
			{
				document.getElementById('txtBCPAddressSuggestService').value=0;
			}					 
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="-")
			{
				show('tblBillingAndChargesInformation',but);
			}
		}	
		//  bcp details for services ADDED BY MANISHA END
		if(document.getElementById('bbPrimaryBCP').isRequired=="1"  && (document.getElementById('bbPrimaryBCP').value == "" || document.getElementById('txtBCPAddressSuggest').value=="")  )
		{
			alert("Please enter   BCP details");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			//START[13082012]AutoSuggest for BCP		
			document.getElementById('txtBCPAddressSuggest').focus();
			//END[13082012]AutoSuggest for BCP
			return false;
		}
		else if(document.getElementById('bbPrimaryBCP').isRequired=="0"  && document.getElementById('bbPrimaryBCP').value == "" )
		{
			document.getElementById('bbPrimaryBCP').value=0;
		}
		but = document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="-")
					{
						show('tblBillingAndChargesInformation',but);
					}
		//start[010] attFLI_PO_DisableValue					
		if(document.getElementById('hdnISFLEFLAG').value==1  && document.getElementById('txtBillingLvl').value!=2 ){
			if(!(document.getElementById('txtBillingLvl').value==3 && attFLI_PO_DisableValue =="Y")){
				alert("Billing Level should be PO Level");
				but=document.getElementById('btnBillingAndChargesInformation');
				if(but.value=="+"){
							butBillFlag = true;
							show('tblBillingAndChargesInformation',but);
				}
				//document.getElementById('txtBillingLvl').focus();
				document.getElementById('billingLevel').focus();
				return false;
			}
		}
		}
	if(document.getElementById('hdnLocationInfo').value==1)
	{
		if(document.getElementById('ddPrimaryLocType').isRequired=="1"  && document.getElementById('ddPrimaryLocType').value == "0" )
		{
			alert("Please enter Primary Location Type");
				but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="+")
					{
						butLocFlag = true;
						show('tblServiceLocationDetails',but);
					}
			document.getElementById('ddPrimaryLocType').focus();
			return false;
		}
		else if(document.getElementById('ddPrimaryLocType').isRequired=="0"  && document.getElementById('ddPrimaryLocType').value == "0" )
		{
			document.getElementById('ddPrimaryLocType').value=0;
		}
		if(document.getElementById('ddSecondaryLocType').isRequired=="1"  && document.getElementById('ddSecondaryLocType').value == "0" )
		{
			alert("Please enter Secondary Location Type");
			but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="+")
					{
						butLocFlag = true;
						show('tblServiceLocationDetails',but);
					}
			document.getElementById('ddSecondaryLocType').focus();
			return false;
		}
		else if(document.getElementById('ddSecondaryLocType').isRequired=="0"  && document.getElementById('ddSecondaryLocType').value == "0" )
		{
			if(document.getElementById('ddSecondaryLocType').value == "" || document.getElementById('ddSecondaryLocType').value == "0" ){
			document.getElementById('ddSecondaryLocType').value="0";	
			}
		}
		
		if(document.getElementById('ddPrimaryLocType').value=="1" && document.getElementById("ddPrimaryLocType").isRequired=="1")
		{
			if((document.getElementById("ddPrimaryBCP").value=="0" || document.getElementById("txtPriCustLocationSuggest").value=="0" ) && document.getElementById("ddPrimaryBCP").isRequired=="1")
			{
			alert("Please enter Primary Location BCP");
			but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="+")
					{
						butLocFlag = true;
						show('tblServiceLocationDetails',but);
					}
			document.getElementById('txtPriCustLocationSuggest').focus();
			return false;
			}
			else if(document.getElementById('ddPrimaryBCP').isRequired=="0"  && ( document.getElementById('ddPrimaryBCP').value == "0" || document.getElementById("txtPriCustLocationSuggest").value=="0" ) )
			{
			document.getElementById('txtPriCustLocationSuggest').value=0;
			}
			
		}
		if(document.getElementById('ddPrimaryLocType').value=="2" && document.getElementById('ddPrimaryLocType').isRequired=="1")
		{
			if(document.getElementById('ddPNLocation').value=="0" && document.getElementById('ddPNLocation').isRequired=="1")
			{
			alert("Please enter Primary Location Network ");
			but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="+")
					{
						butLocFlag = true;
						show('tblServiceLocationDetails',but);
					}
			return false;
			}
			else if(document.getElementById('ddPNLocation').isRequired=="0"  && document.getElementById('ddPNLocation').value == "0" )
			{
			document.getElementById('ddPNLocation').value=0;
			}
		}
		if(document.getElementById('ddSecondaryLocType').value=="1" && document.getElementById('ddSecondaryLocType').isRequired=="1")
		{
			if((document.getElementById("ddSecondaryBCP").value=="0" || document.getElementById("txtSecCustLocationSuggest").value=="0") && document.getElementById("ddSecondaryBCP").isRequired=="1")
			{
			alert("Please enter Secondary Location BCP");
			but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="+")
					{
						butLocFlag = true;
						show('tblServiceLocationDetails',but);
					}
			document.getElementById('txtSecCustLocationSuggest').focus();
			return false;
			}
			else if(document.getElementById('ddSecondaryBCP').isRequired=="0"  && (document.getElementById('ddSecondaryBCP').value == "0" || document.getElementById("txtSecCustLocationSuggest").value=="0") )
			{
			document.getElementById('txtSecCustLocationSuggest').value=0;
			}
		}
		if(document.getElementById('ddSecondaryLocType').value=="2" && document.getElementById('ddSecondaryLocType').isRequired=="1")
		{
			if(document.getElementById('ddSNLocation').value=="0" && document.getElementById('ddSNLocation').isRequired=="1")
			{
			alert("Please enter Secondary Location Network ");
			but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="+")
					{
						butLocFlag = true;
						show('tblServiceLocationDetails',but);
					}
			return false;
			}
			else if(document.getElementById('ddSNLocation').isRequired=="0"  && document.getElementById('ddSNLocation').value == "0" )
			{
			document.getElementById('ddSNLocation').value=0;
			}
		}
		
		if(document.getElementById('txtFAddress').isRequired=="1"  && document.getElementById('txtFAddress').value == "" )
		{
			but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="+")
					{
						butLocFlag = true;
						show('tblServiceLocationDetails',but);
					}
			document.getElementById('txtFAddress').focus();
		}
		else if(document.getElementById('txtFAddress').isRequired=="0"  && document.getElementById('txtFAddress').value == "" )
		{
			document.getElementById('txtFAddress').value=0;
		}
		if(document.getElementById('txtToAddress').isRequired=="1"  && document.getElementById('txtToAddress').value == "" )
		{
			but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="+")
					{
						butLocFlag = true;
						show('tblServiceLocationDetails',but);
					}
			document.getElementById('txtToAddress').focus();
		}
		else if(document.getElementById('txtToAddress').isRequired=="0"  && document.getElementById('txtToAddress').value == "" )
		{
			document.getElementById('txtToAddress').value=0;
		}
		but=document.getElementById('btnServiceLocationDetails');
			if(but.value=="-" && butLocFlag == true)
			{
				show('tblServiceLocationDetails',but);
			}
	}
	
	if(document.getElementById('hdnHardwareInfo').value==1){
		var txtStore = document.getElementById('txtStore');
		if(txtStore.isRequired=="1"  && txtStore.value == "0" ){
			alert("Please enter Store");
			but=document.getElementById('btnHwDetails');
			if(but.value=="+"){
						butHWFlag = true;
						show('tblHwDetails',but);
			}
			//document.getElementById('txtStore').focus();
			$("#txtStoreText").focus();
			return false;
		}else if(txtStore.isRequired=="0"  && txtStore.value == "0" ){
			document.getElementById('txtStore').value=0;
			$("#txtStoreText").val("Select Store");
		}
		var txtHtype = document.getElementById('txtHtype');
		if(txtHtype.value=="0" && txtHtype.isRequired=="1"){
			alert("Please enter Hardware type");
			but=document.getElementById('btnHwDetails');
			if(but.value=="+"){
					butHWFlag = true;
					show('tblHwDetails',but);
			}
			//document.getElementById('txtHtype').focus();
			$('#hTypeText').focus();
			return false;
		}else if(txtHtype.value=="0" && txtHtype.isRequired=="0"){
			txtHtype.value=0;
			$("#hTypeText").val("Select Hardware Type");
		}
		var txtform = document.getElementById('txtform');
		if(txtform.value=="0" && txtform.isRequired=="1"){
			alert("Please enter Form available");
			but=document.getElementById('btnHwDetails');
			if(but.value=="+"){
					butHWFlag = true;
					show('tblHwDetails',but);
			}
			//document.getElementById('txtform').focus();
			document.getElementById('textFormTXT').focus();
			return false;
		}else if(txtform.value=="0" && txtform.isRequired=="0"){
			txtform.value=0;
			document.getElementById('textFormTXT').value="Select Form";
		}
		if(document.getElementById('txtNSale').value=="0" && document.getElementById('txtNSale').isRequired=="1" )
		{
			alert("Please enter Nature of sale");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			document.getElementById('txtNSale').focus();
			return false;
		}
		else if(document.getElementById('txtNSale').value=="0" && document.getElementById('txtNSale').isRequired=="0")
		{
			document.getElementById('txtNSale').value=0;
		}
		var txtTSale = document.getElementById('txtTSale');
		if(txtTSale.isRequired=="1"  && txtTSale.value == "0" ){
			alert("Please enter Type of sale");
			but=document.getElementById('btnHwDetails');
			if(but.value=="+"){
					butHWFlag = true;
					show('tblHwDetails',but);
			}
			//document.getElementById('txtTSale').focus();
			document.getElementById('textSaleType').focus();
			return false;
		}else if(txtTSale.isRequired=="0"  && txtTSale.value == "0" ){
			txtTSale.value=0;
			document.getElementById('textSaleType').value="Select Type Of Sale";
		}
		but=document.getElementById('btnHwDetails');
					if(but.value=="-" && butHWFlag == true)
					{
						show('tblHwDetails',but);
					}
		if(document.getElementById('txtdispatchAddress').isRequired=="1"  && (document.getElementById('txtDispatchAddress').value == "" || document.getElementById('txtDispatchAddressSuggest').value=="" ) )
		{
			alert("Please enter Dispatch Address");
			but=document.getElementById('btnDispatchAddress');
				if(but.value=="+")
				{
					show('tblDispatchAddress',but);
				}
			document.getElementById('txtDispatchAddressSuggest').focus();
			return false;
		}
		else if(document.getElementById('txtdispatchAddress').isRequired=="0"  && document.getElementById('txtdispatchAddress').value == "0" )
		{
			document.getElementById('txtdispatchAddress').value=0;
		}
		but=document.getElementById('btnDispatchAddress');
				if(but.value=="-")
				{
					show('tblDispatchAddress',but);
				}
	if((document.getElementById('txtStartDateLogic').value != "0" || document.getElementById('txtEndDateLogic').value != "0" )
		||
		(document.getElementById('txtStartDateLogic').isRequired==1 || document.getElementById('txtEndDateLogic').isRequired==1 ||
			document.getElementById('txtHStartMonth').isRequired==1 || document.getElementById('txtHEndMonth').isRequired==1 ||
			document.getElementById('txtHStartDays').isRequired==1 || document.getElementById('txtHEndDays').isRequired==1 ||
			document.getElementById('txtHExtMonth').isRequired==1 || document.getElementById('txtHExtDays').isRequired==1
			)
		)
	{
		
		if(document.getElementById('txtStartDateLogic').value == "0" )
		{
			alert("Please enter Start Date Logic");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtStartDateLogic').focus();
			return false;
		}
		
		if( document.getElementById('txtHStartMonth').value == "" )
		{
			alert("Please enter Start Month ");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHStartMonth').focus();
			return false;
		}
		
		if( document.getElementById('txtHStartDays').value == "" )
		{
			alert("Please enter Start Days ");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHStartDays').focus();
			return false;
		}
		
		if( document.getElementById('txtEndDateLogic').value == "0" )
		{
			alert("Please enter End Date Logic");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtEndDateLogic').focus();
			return false;
		}
		
		if( document.getElementById('txtHEndMonth').value == "" )
		{
			alert("Please enter End Month ");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHEndMonth').focus();
			return false;
		}
		if( document.getElementById('txtHEndDays').value == "" )
		{
			alert("Please enter End Days ");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHEndDays').focus();
			return false;
		}
		if( document.getElementById('txtHExtMonth').value == "" )
		{
			alert("Please enter Extended Month");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHExtMonth').focus();
			return false;
		}
		if( document.getElementById('txtHExtDays').value == "" )
		{
			alert("Please enter Extended Days");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHExtDays').focus();
			return false;
		}
	}
	
		but=document.getElementById('btnWarrentyDetails');
				if(but.value=="-" && butWarranFlag == true)
				{
					show('tblWarrentyDetails',but);
				}
	}
			var serviceDetID=document.getElementById("hdnServiceDetailID").value;
			if(serviceDetID==342 || serviceDetID==344 )
			{
			   var result=  CheckMPLSWithEnterpriseSignage(attributeID,newAttributeVal);
			   if(result == false)
			    return;
			}
		
		
		
	    var jsData =
		{
			prodAttID: attributeID,
			prodAttValue:attributeVal,
			prodAttExptdValue:attributeExpVal,
			prodAttName:attributeName,
			prodAttMandatory:attributeMandatory,
			serviceSummaryMandatory:attServiceSummMand,		
			hdnSeriveAttCounter:countAttributes,
			//[003]start
			prodAttriMaxLength:attributeMaxLength,
			//[003]end
			serviceProductID:Number(document.getElementById('hdnServiceProductID').value),
			newProdAttValue:newAttributeVal,
			attributeValuIDs:attValueID,
			serviceId:Number(servicesID),
			podetailID:Number(document.getElementById('txtPODetailNo').value),
			accountID:Number(document.getElementById('txtBillingAC').value),
			creditPeriod:Number(document.getElementById('txtBillingCP').value),
			entityID:document.getElementById('txtEntity').value,
			billingType:Number(document.getElementById('txtBillingType').value),
			billingMode:document.getElementById('txtBillingMode').value,
		    billingformat:document.getElementById('txtBillingformat').value,
		    billingInfoID:Number(document.getElementById('hdnBillingInfoID').value),
		    locationInfoID:Number(document.getElementById('hdnServiceInfoID').value),
		    hardwareDetailID:Number(document.getElementById('hdnHardwareInfoID').value),
			licenceCoID:Number(document.getElementById('txtLicenceCo').value),
			taxation:document.getElementById('txtTaxation').value,
			stdReasonId:document.getElementById('changeReason').value,
			commitmentPeriod:Number(document.getElementById('txtCmtPeriod').value),
			billingLevel:document.getElementById('txtBillingLvl').value,
			penaltyClause:document.getElementById('txtPenaltyClause').value,
			warrantyClause:document.getElementById('txtWarrantClause').value,
			//satyapan osp
			isOSPTagging:document.getElementById('txtOSPTagging').value,//satyapan osp
			txtOSPRegNo:document.getElementById('txtOSPRegNo').value,
			txtOSPRegDate:document.getElementById('txtOSPRegDate').value,
			//satyapan osp
			serviceType:document.getElementById('txtHdnServiceName').value,
			selectedDispatchID:Number(document.getElementById('txtdispatchAddress').value),
			//[004] start
			dispatchContactName:document.getElementById('txtDispatchContactName').value,
			//[004] end
			selectedStoreID:Number(document.getElementById('txtStore').value),
			formAvailable:document.getElementById('txtform').value,
			hardwareType:document.getElementById('txtHtype').value,
			saleNature:document.getElementById('txtNSale').value,
			saleType:document.getElementById('txtTSale').value,
			selectedPriLocType:Number(document.getElementById('ddPrimaryLocType').value),
			selectedSecLocType:Number(document.getElementById('ddSecondaryLocType').value),
			selectedPNLocation:Number(document.getElementById('ddPNLocation').value),
			selectedPrimaryBCP:Number(document.getElementById('ddPrimaryBCP').value),
			selectedSNLocation:Number(document.getElementById('ddSNLocation').value),
			selectedSecBCP:Number(document.getElementById('ddSecondaryBCP').value),
			billingLevelNo:Number(document.getElementById('txtBillingLevelNo').value),
			principalAmount:Number(document.getElementById('txtPrincipalAmount').value),
			interestRate:Number(document.getElementById("txtInterestRate").value),
			chargesDetails : chargesDetailList,
			locationInfoID:Number(document.getElementById('hdnServiceInfoID').value),
			billingInfoID:Number(document.getElementById('hdnBillingInfoID').value),
			hardwareDetailID:Number(document.getElementById('hdnHardwareInfoID').value),
			serviceProductID:Number(document.getElementById('hdnServiceProductID').value),
			billingInfoValue:Number(document.getElementById('hdnBillingInfo').value),
			chargeInfoValue:Number(document.getElementById('hdnChargeInfo').value),
			locationInfoValue:Number(document.getElementById('hdnLocationInfo').value),
			hardwareInfoValue:Number(document.getElementById('hdnHardwareInfo').value),
			billingBCPId:document.getElementById('bbPrimaryBCPID').value,
			billingBCPIdService:document.getElementById('bbPrimaryBCPIDService').value,
			startHWDateLogic:document.getElementById('txtStartDateLogic').value,
			endHWDateLogic:document.getElementById('txtEndDateLogic').value,
			startDate:document.getElementById('txtStartDate').value,
			endDate:document.getElementById('txtEndDate').value,
			fromLocation:document.getElementById('txtFAddress').value,
		    toLocation:document.getElementById('txtToAddress').value,
		    isNfa:Number(document.getElementById('chkSelectNfa').value),
		    //End[010]
		    isUsage:Number(document.getElementById('chkIsUsage').value),
		    billingScenario:document.getElementById('txtBillingScenario').value,
		    fxRedirectionSPID:Number(document.getElementById('hdnFxRedirectionSPID').value),
			fxRedirectionLSI:Number(document.getElementById('txtFxRedirectionLSI').value),
		    
		     logicalCircuitId:eLogicalCircuitId,
			 infraOderNo:infraOderNo,
			 metasolvCircuitId:metasolvCircuitId,	
			 logicalCircuitId_new:eLogicalCircuitId_new,
			 infraOderNo_new:infraOderNo_new,
			 metasolvCircuitId_new:metasolvCircuitId_new,
			 linkageInfoFlag:linKageFlag,
		    txtStartMonth:document.getElementById('txtHStartMonth').value,
			txtStartDays:document.getElementById('txtHStartDays').value,
			txtEndMonth:document.getElementById('txtHEndMonth').value,
			txtEndDays:document.getElementById('txtHEndDays').value,
			txtExtMonth:document.getElementById('txtHExtMonth').value,
			txtExtDays:document.getElementById('txtHExtDays').value,
			txtExtDate:document.getElementById('txtHExtDate').value,
		    
            isReqTxtPODetailNo:Number(document.getElementById('txtPODetailNo').isRequired),
			isReqTxtBillingAC:Number(document.getElementById('txtBillingAC').isRequired),
			isReqTxtBillingCP:Number(document.getElementById('txtBillingCP').isRequired),
			isReqTxtEntity:Number(document.getElementById('txtEntity').isRequired),
			isReqTxtBillingMode:Number(document.getElementById('txtBillingMode').isRequired),
			isReqTxtBillingformat:Number(document.getElementById('txtBillingformat').isRequired),
			isReqTxtLicenceCo:Number(document.getElementById('txtLicenceCo').isRequired),
			isReqTxtTaxation:Number(document.getElementById('txtTaxation').isRequired),
			isReqTxtCmtPeriod:Number(document.getElementById('txtCmtPeriod').isRequired),
			isReqTxtBillingLvl:Number(document.getElementById('txtBillingLvl').isRequired),
			isReqTxtPenaltyClause:Number(document.getElementById('txtPenaltyClause').isRequired),
			isReqTxtWarrantyClause:Number(document.getElementById('txtWarrantClause').isRequired),
			//satyapan OSP By nagarjuna
			isReqOSPTagging:Number(document.getElementById('txtOSPTagging').isRequired),//satyapan osp
			isReqOSPRegNo:Number(document.getElementById('txtOSPRegNo').isRequired),
			isReqOSPRegDate:Number(document.getElementById('txtOSPRegDate').isRequired),
			//Satyapan OSP By Nagarjuna
			
			isReqSelectedDispatchID:Number(document.getElementById('txtdispatchAddress').isRequired),
			//[004] start
			isReqDispatchContactName:Number(document.getElementById('txtDispatchContactName').isRequired),
			//[004] end
			isReqDdPrimaryLocType:Number(document.getElementById('ddPrimaryLocType').isRequired),
			isReqDdSecondaryLocType:Number(document.getElementById('ddSecondaryLocType').isRequired),
			isReqSelectedPNLocation:Number(document.getElementById('ddPNLocation').isRequired),
			isReqSelectedPrimaryBCP:Number(document.getElementById('ddPrimaryBCP').isRequired),
			isReqSelectedSNLocation:Number(document.getElementById('ddSNLocation').isRequired),
			isReqSelectedSecBCP:Number(document.getElementById('ddSecondaryBCP').isRequired),
			
			isReqTxtStore:Number(document.getElementById('txtStore').isRequired),
			isReqTxtHtype:Number(document.getElementById('txtHtype').isRequired),
			isReqTxtform:Number(document.getElementById('txtform').isRequired),
			isReqTxtTSale:Number(document.getElementById('txtTSale').isRequired),
			isReqTxtNSale:Number(document.getElementById('txtNSale').isRequired),
			isReqTxtStore:Number(document.getElementById('txtStartDateLogic').isRequired),
			isReqTxtHtype:Number(document.getElementById('txtEndDateLogic').isRequired),
			isReqStartDate:Number(document.getElementById('txtStartDate').isRequired),
			isReqEndDate:Number(document.getElementById('txtEndDate').isRequired),
			isReqPrincipalAmount:Number(document.getElementById('txtPrincipalAmount').isRequired),
			isReqInterestRate:Number(document.getElementById("txtInterestRate").isRequired),
			serviceInfoValue:Number(document.getElementById('hdnServiceSummary').value),
			isReqTxtFromLocation:Number(document.getElementById('txtFAddress').isRequired),
			isReqTxtToLocation:Number(document.getElementById('txtToAddress').isRequired), // by Saurabh for validation of to and from location
			poNumber:Number(changeOrderNo),
			changeTypeId:Number(callerWindowObj.document.getElementById('HdnChangeTypeID').value),
			componentDetails:componentsList,
			componentInfoValue:Number(document.getElementById('hdnComponentInfo').value),
			configValue:Number(document.getElementById('hdnConfigValue').value),
			deletedChargesList:document.getElementById('hdnDeletedChargesListId').value		
			
		};
	}
	else{
		if(document.getElementById('hdnBillingInfo').value==1){
			var txtPODetailNo = document.getElementById('txtPODetailNo');
			if(txtPODetailNo.isRequired=="1" && txtPODetailNo.value == "0"){
				alert("Please enter PO ID");
				but=document.getElementById('btnBillingAndChargesInformation');
				if(but.value=="+"){
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
				}
				document.getElementById('poDetailNo').focus();
				return false;
			}else if(txtPODetailNo.isRequired=="0"  && txtPODetailNo.value == "0" ){
				txtPODetailNo.value=0;
				document.getElementById('poDetailNo').value="";
			}
			var txtBillingCP = document.getElementById('txtBillingCP');	
			if(txtBillingCP.isRequired=="1"  && txtBillingCP.value == "0" ){
				alert("Please enter Credit Period");
				but=document.getElementById('btnBillingAndChargesInformation');
				if(but.value=="+"){
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
				}
				document.getElementById('billingCP').focus();
				return false;
			}else if(txtBillingCP.isRequired=="0"  && txtBillingCP.value == "0" ){
				txtBillingCP.value=0;
				document.getElementById('billingCP').value="Select Credit Period";
			}
			var txtEntity = document.getElementById('txtEntity');
			if(txtEntity.isRequired=="1"  && txtEntity.value == "0" ){
				alert("Please enter Legal Entity");
				but=document.getElementById('btnBillingAndChargesInformation');
				if(but.value=="+"){
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
				}
				document.getElementById('legalEntityTXT').focus();
				return false;
			}else if(txtEntity.isRequired=="0"  && txtEntity.value == "0" ){
			   		document.getElementById("txtEntity").value = "0";
				   	document.getElementById("legalEntityTXT").value = "Select Legal Entity";
			}
			var txtBillingMode = document.getElementById('txtBillingMode');
			if(txtBillingMode.isRequired=="1"  && txtBillingMode.value == "0" ){
				alert("Please enter Billing Mode");
				but=document.getElementById('btnBillingAndChargesInformation');
				if(but.value=="+"){
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
				}
				document.getElementById('billingMode').focus();
				return false;
			}else if(txtBillingMode.isRequired=="0"  && txtBillingMode.value == "0" ){
				txtBillingMode.value=0;
				document.getElementById('billingMode').value="Select Billing Mode";
			}
		var txtBillingformat = document.getElementById('txtBillingformat');
		if(txtBillingformat.isRequired=="1"  && txtBillingformat.value == "0"){
			alert("Please enter Bill Format");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+"){
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
			}
			document.getElementById('billingFormat').focus();
			return false;
		}else if(txtBillingformat.isRequired=="0"  && txtBillingformat.value == "0" ){
			txtBillingformat.value=0;
			document.getElementById('billingFormat').value="Select Bill Format";
		}
		var txtLicenceCo = document.getElementById('txtLicenceCo');
		if(txtLicenceCo.isRequired=="1"  && txtLicenceCo.value == "0"){
			alert("Please enter Licence Co");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+"){
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
			}
			$("#licenseCo").focus();
			return false;
		}else if(txtLicenceCo.isRequired=="0"  && txtLicenceCo.value == "0"){
			document.getElementById('txtBillingformat').value=0;
			document.getElementById('billingFormat').value="Select Bill Format";
			txtLicenceCo.value=0;
			$("#licenseCo").val("Select Licence Company");
		}
		if(document.getElementById('txtNoticePeriod').isRequired=="1"  && document.getElementById('txtNoticePeriod').value == "" )
		{
			alert("Please enter Notice period");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtNoticePeriod').focus();
			return false;
		}
		else if(document.getElementById('txtNoticePeriod').isRequired=="0"  && document.getElementById('txtNoticePeriod').value == "" )
		{
			document.getElementById('txtNoticePeriod').value=0;
		}
		var txtBillingType = document.getElementById('txtBillingType');
		if(txtBillingType.isRequired=="1"  && txtBillingType.value == "0" ){
			alert("Please enter Billing Type");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+"){
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
			}
			//document.getElementById('txtBillingType').focus();
			$("#billingType").focus();
			return false;
		}else if(txtBillingType.isRequired=="0"  && txtBillingType.value == "0" ){
			txtBillingType.value=0;
			$("#billingType").val("Select Bill Type");
		}
		var txtTaxation = document.getElementById('txtTaxation');
		if(txtTaxation.isRequired=="1" && txtTaxation.value == "0"){
			alert("Please enter Taxation");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+"){
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
			}
			document.getElementById('taxationID').focus();
			return false;
		//Puneet changing for auto suggest	
		}else if(txtTaxation.isRequired=="0" && txtTaxation.value == "0"){
			txtTaxation.value=0;
			document.getElementById('taxationID').value="Select Taxation";
		}
		if(document.getElementById('txtCmtPeriod').isRequired=="1"  && document.getElementById('txtCmtPeriod').value == "" )
		{
			alert("Please enter Commitment Period (Months)");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtCmtPeriod').focus();
			return false;
		}
		else if(document.getElementById('txtCmtPeriod').isRequired=="0"  && document.getElementById('txtCmtPeriod').value == "" )
		{
			document.getElementById('txtCmtPeriod').value=0;
		}
		var txtBillingLvl = document.getElementById('txtBillingLvl');
		if(txtBillingLvl.isRequired=="1"  && txtBillingLvl.value == "0" ){
			alert("Please enter Billing Level");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+"){
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
			}
			//document.getElementById('txtBillingLvl').focus();
			$("#billingLevel").focus();
			return false;
		}else if(txtBillingLvl.isRequired=="0"  && txtBillingLvl.value == "0" ){
			txtBillingLvl.value=0;
			$("#billingLevel").val("Select Billing Level");
		}
		if(document.getElementById('txtBillingLevelNo').isRequired=="1"  && document.getElementById('txtBillingLevelNo').value == "" )
		{
			alert("Please enter Billing Level No");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingLevelNo').focus();
			return false;
		}
		else if(document.getElementById('txtBillingLevelNo').isRequired=="0"  && document.getElementById('txtBillingLevelNo').value == "" )
		{
			document.getElementById('txtBillingLevelNo').value=0;
		}
		if(document.getElementById('txtPenaltyClause').isRequired=="1"  && document.getElementById('txtPenaltyClause').value == "" )
		{
			alert("Please Enter Penalty ");
			but=document.getElementById('btnBillingAndChargesInformation');
				if(but.value=="+")
				{
					butBillFlag = true;
					show('tblBillingAndChargesInformation',but);
				}
			alert("Please enter Penalty Clause");

			document.getElementById('txtPenaltyClause').focus();
			return false;
		}
		else if(document.getElementById('txtPenaltyClause').isRequired=="0"  && document.getElementById('txtPenaltyClause').value == "" )
		{
			document.getElementById('txtPenaltyClause').value=0;
		}
		//satyapan OSP
		if((document.getElementById('txtOSPRegNo').isRequired=="1" ||document.getElementById('txtOSPTagging').value==1 ) && (document.getElementById('txtOSPRegNo').value == "" || document.getElementById('txtOSPRegNo').value ==null) )
		{
			alert("Please enter OSPRegNO");
			document.getElementById('txtOSPRegNo').focus();
			return false;
		}
	  	else if(document.getElementById('txtOSPRegNo').isRequired=="0" && document.getElementById('txtOSPRegNo').value=="")
		{
			document.getElementById('txtOSPRegNo').value="";
		}	
		 
		if((document.getElementById('txtOSPRegDate').isRequired=="1" ||document.getElementById('txtOSPTagging').value==1 )  && (document.getElementById('txtOSPRegDate').value == "" || document.getElementById('txtOSPRegDate').value ==null) )
		{
			alert("Please enter OSPRegDate");
			
			document.getElementById('txtOSPRegDate').focus();
			return false;
		}
		else if(document.getElementById('txtOSPRegDate').isRequired=="0" && document.getElementById('txtOSPRegDate').value=="" )
		{
			document.getElementById('txtOSPRegDate').value="";
		}
		
		
		//END satyapan OSP
			//WARRANTY CLAUSE ADDED BY MANISHA START
		if(document.getElementById('txtWarrantClause').isRequired=="1"  && document.getElementById('txtWarrantClause').value == "" )
		{
			alert("Please enter Warranty Clause");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtWarrantClause').focus();
			return false;
		}
		else if(document.getElementById('txtWarrantClause').isRequired=="0"  && document.getElementById('txtWarrantClause').value == "" )
		{
			document.getElementById('txtWarrantClause').value=0;
		}	
		
		//WARRANTY CLAUSE ADDED BY MANISHA end
		
		//  bcp details for services ADDED BY MANISHA START
		if(document.getElementById('hdnHardwareInfo').value!=1)
		{
			if(document.getElementById('bbPrimaryBCPService').isRequired=="1"  && (document.getElementById('bbPrimaryBCPService').value == "" || document.getElementById('txtBCPAddressSuggestService').value=="") )
			{
				alert("Please enter BCP details for Service");
				but=document.getElementById('btnBillingAndChargesInformation');
						if(but.value=="+")
						{
							show('tblBillingAndChargesInformation',but);
						}
				document.getElementById('txtBCPAddressSuggestService').focus();
				
				return false;
			}
			else if(document.getElementById('bbPrimaryBCPService').isRequired=="0"  && (document.getElementById('bbPrimaryBCPService').value == "" || document.getElementById('txtBCPAddressSuggestService').value=="") )
			{
				document.getElementById('txtBCPAddressSuggestService').value=0;
			}					 
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="-")
			{
				show('tblBillingAndChargesInformation',but);
			}
		}	
			//  bcp details for services ADDED BY MANISHA END
		if(document.getElementById('bbPrimaryBCP').isRequired=="1"  && (document.getElementById('bbPrimaryBCP').value == "" || document.getElementById('txtBCPAddressSuggest').value=="") )
		{
			alert("Please enter   BCP details");
			but=document.getElementById('btnBillingAndChargesInformation');
				if(but.value=="+")
				{
					butBillFlag = true;
					show('tblBillingAndChargesInformation',but);
				}
			//Start[13082012]AutoSuggest for BCP	
			document.getElementById('txtBCPAddressSuggest').focus();
			//END[13082012]AutoSuggest for BCP
			return false;
		}
		else if(document.getElementById('bbPrimaryBCP').isRequired=="0"  && (document.getElementById('bbPrimaryBCP').value == "" || document.getElementById('txtBCPAddressSuggest').value=="") )
		{
			document.getElementById('bbPrimaryBCP').value=0;
		}
		but = document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="-")
			{
				show('tblBillingAndChargesInformation',but);
			}
			//start[010] attFLI_PO_DisableValue
			if(document.getElementById('hdnISFLEFLAG').value==1  && document.getElementById('txtBillingLvl').value!=2){
				if(!(document.getElementById('txtBillingLvl').value==3 && attFLI_PO_DisableValue =="Y")){
					alert("Billing Level should be PO Level");
					but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+"){
							butBillFlag = true;
							show('tblBillingAndChargesInformation',but);
					}
					//document.getElementById('txtBillingLvl').focus();
					document.getElementById('billingLevel').focus();
					return false;
				}
			}
		}
	if(document.getElementById('hdnLocationInfo').value==1)
	{
		if(document.getElementById('ddPrimaryLocType').isRequired=="1"  && document.getElementById('ddPrimaryLocType').value == "0" )
		{
			alert("Please enter Primary Location Type");
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}
			document.getElementById('ddPrimaryLocType').focus();
			return false;
		}
		else if(document.getElementById('ddPrimaryLocType').isRequired=="0"  && document.getElementById('ddPrimaryLocType').value == "0" )
		{
			document.getElementById('ddPrimaryLocType').value=0;
		}
		if(document.getElementById('ddSecondaryLocType').isRequired=="1"  && document.getElementById('ddSecondaryLocType').value == "0" )
		{
			alert("Please enter Secondary Location Type");
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}
			document.getElementById('ddSecondaryLocType').focus();
			return false;
		}
		else if(document.getElementById('ddSecondaryLocType').isRequired=="0"  && document.getElementById('ddSecondaryLocType').value == "0" )
		{
			if(document.getElementById('ddSecondaryLocType').value == "" || document.getElementById('ddSecondaryLocType').value == "0" ){
			document.getElementById('ddSecondaryLocType').value="0";	
			}
		}
		
		if(document.getElementById('ddPrimaryLocType').value=="1" && document.getElementById("ddPrimaryLocType").isRequired=="1")
		{
			if((document.getElementById("ddPrimaryBCP").value=="0" || document.getElementById("txtPriCustLocationSuggest").value=="0") && document.getElementById("ddPrimaryBCP").isRequired=="1")
			{
			alert("Please enter Primary Location BCP");
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}
			document.getElementById('txtPriCustLocationSuggest').focus();
			return false;
			}
			else if(document.getElementById('ddPrimaryBCP').isRequired=="0"  && (document.getElementById('ddPrimaryBCP').value == "0" || document.getElementById("txtPriCustLocationSuggest").value=="0") )
			{
			document.getElementById('txtPriCustLocationSuggest').value=0;
			}
			
		}
		if(document.getElementById('ddPrimaryLocType').value=="2" && document.getElementById('ddPrimaryLocType').isRequired=="1")
		{
			if(document.getElementById('ddPNLocation').value=="0" && document.getElementById('ddPNLocation').isRequired=="1")
			{
			alert("Please enter Primary Location Network ");
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}
			return false;
			}
			else if(document.getElementById('ddPNLocation').isRequired=="0"  && document.getElementById('ddPNLocation').value == "0" )
			{
			document.getElementById('ddPNLocation').value=0;
			}
		}
		if(document.getElementById('ddSecondaryLocType').value=="1" && document.getElementById('ddSecondaryLocType').isRequired=="1")
		{
			if((document.getElementById("ddSecondaryBCP").value=="0" || document.getElementById("txtSecCustLocationSuggest").value=="0") && document.getElementById("ddSecondaryBCP").isRequired=="1")
			{
			alert("Please enter Secondary Location BCP");
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}
			document.getElementById('txtSecCustLocationSuggest').focus();
			return false;
			}
			else if(document.getElementById('ddSecondaryBCP').isRequired=="0"  && (document.getElementById('ddSecondaryBCP').value == "0" || document.getElementById("txtSecCustLocationSuggest").value=="0"))
			{
			document.getElementById('txtSecCustLocationSuggest').value=0;
			}
		}
		if(document.getElementById('ddSecondaryLocType').value=="2" && document.getElementById('ddSecondaryLocType').isRequired=="1")
		{
			if(document.getElementById('ddSNLocation').value=="0" && document.getElementById('ddSNLocation').isRequired=="1")
			{
			alert("Please enter Secondary Location Network ");
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}
			return false;
			}
			else if(document.getElementById('ddSNLocation').isRequired=="0"  && document.getElementById('ddSNLocation').value == "0" )
			{
			document.getElementById('ddSNLocation').value=0;
			}
		}
		
		if(document.getElementById('txtFAddress').isRequired=="1"  && document.getElementById('txtFAddress').value == "" )
		{
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}
			document.getElementById('txtFAddress').focus();
		}
		else if(document.getElementById('txtFAddress').isRequired=="0"  && document.getElementById('txtFAddress').value == "" )
		{
			document.getElementById('txtFAddress').value=0;
		}
		if(document.getElementById('txtToAddress').isRequired=="1"  && document.getElementById('txtToAddress').value == "" )
		{
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}
			document.getElementById('txtToAddress').focus();
		}
		else if(document.getElementById('txtToAddress').isRequired=="0"  && document.getElementById('txtToAddress').value == "" )
		{
			document.getElementById('txtToAddress').value=0;
		}
		but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="-" && butLocFlag == true)
				{
					show('tblServiceLocationDetails',but);
				}
	}
	if(document.getElementById('hdnHardwareInfo').value==1){
		var txtStore = document.getElementById('txtStore');
		if(txtStore.isRequired=="1"  && txtStore.value == "0" ){
			alert("Please enter Store");
			but=document.getElementById('btnHwDetails');
			if(but.value=="+"){
					butHWFlag = true;
					show('tblHwDetails',but);
			}
			//document.getElementById('txtStore').focus();
			$("#txtStoreText").focus();
			return false;
		}else if(txtStore.isRequired=="0"  && txtStore.value == "0" ){
			document.getElementById('txtStore').value=0;
			$("#txtStoreText").val("Select Store");
		}
		var txtHtype = document.getElementById('txtHtype');
		if(txtHtype.value=="0" && txtHtype.isRequired=="1"){
			alert("Please enter Hardware type");
			but=document.getElementById('btnHwDetails');
			if(but.value=="+"){
					butHWFlag = true;
					show('tblHwDetails',but);
			}
			//document.getElementById('txtHtype').focus();
			$('#hTypeText').focus();
			return false;
		}else if(txtHtype.value=="0" && txtHtype.isRequired=="0"){
			txtHtype.value=0;
			$("#hTypeText").val("Select Hardware Type");
		}
		var txtform = document.getElementById('txtform');
		if(txtform.value=="0" && txtform.isRequired=="1" ){
			alert("Please enter Form available");
			but=document.getElementById('btnHwDetails');
			if(but.value=="+"){
					butHWFlag = true;
					show('tblHwDetails',but);
			}
			//document.getElementById('txtform').focus();
			document.getElementById('textFormTXT').focus();
			return false;
		}
		else if(txtform.value=="0" && txtform.isRequired=="0" ){
			txtform.value=0;
			document.getElementById('textFormTXT').value="Select Form";
		}
		if(document.getElementById('txtNSale').value=="0" && document.getElementById('txtNSale').isRequired=="1" )
		{
			alert("Please enter Nature of sale");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			document.getElementById('txtNSale').focus();
			return false;
		}
		else if(document.getElementById('txtNSale').value=="0" && document.getElementById('txtNSale').isRequired=="0")
		{
			document.getElementById('txtNSale').value=0;
		}
		var txtTSale = document.getElementById('txtTSale');
		if(txtTSale.isRequired=="1"  && txtTSale.value == "0" ){
			alert("Please enter Type of sale");
			but=document.getElementById('btnHwDetails');
			if(but.value=="+"){
					butHWFlag = true;
					show('tblHwDetails',but);
			}
			//document.getElementById('txtTSale').focus();
			document.getElementById('textSaleType').focus();
			return false;
		}else if(txtTSale.isRequired=="0"  && txtTSale.value == "0" ){
			txtTSale.value=0;
			document.getElementById('textSaleType').value="Select Type Of Sale";
		}
		but=document.getElementById('btnHwDetails');
				if(but.value=="-" && butHWFlag == true)
				{
					show('tblHwDetails',but);
				}
		if(document.getElementById('txtdispatchAddress').isRequired=="1"  && (document.getElementById('txtDispatchAddress').value == "" || document.getElementById('txtDispatchAddressSuggest').value=="" )  )
		{
			alert("Please enter Dispatch Address");
			but=document.getElementById('btnDispatchAddress');
				if(but.value=="+")
				{
					show('tblDispatchAddress',but);
				}
			document.getElementById('txtDispatchAddressSuggest').focus();
			return false;
		}
		else if(document.getElementById('txtdispatchAddress').isRequired=="0"  && document.getElementById('txtdispatchAddress').value == "0" )
		{
			document.getElementById('txtdispatchAddress').value=0;
		}
		but=document.getElementById('btnDispatchAddress');
				if(but.value=="-")
				{
					show('tblDispatchAddress',but);
				}
	if((document.getElementById('txtStartDateLogic').value != "0" || document.getElementById('txtEndDateLogic').value != "0" )
		||
		(document.getElementById('txtStartDateLogic').isRequired==1 || document.getElementById('txtEndDateLogic').isRequired==1 ||
			document.getElementById('txtHStartMonth').isRequired==1 || document.getElementById('txtHEndMonth').isRequired==1 ||
			document.getElementById('txtHStartDays').isRequired==1 || document.getElementById('txtHEndDays').isRequired==1 ||
			document.getElementById('txtHExtMonth').isRequired==1 || document.getElementById('txtHExtDays').isRequired==1
			)
		)
	{
		if( document.getElementById('txtStartDateLogic').value == "0" )
		{
			alert("Please enter Start Date Logic");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtStartDateLogic').focus();
			return false;
		}
		
		if( document.getElementById('txtHStartMonth').value == "" )
		{
			alert("Please enter Start Month ");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHStartMonth').focus();
			return false;
		}
		
		if( document.getElementById('txtHStartDays').value == "" )
		{
			alert("Please enter Start Days ");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHStartDays').focus();
			return false;
		}
		
		if( document.getElementById('txtEndDateLogic').value == "0" )
		{
			alert("Please enter End Date Logic");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtEndDateLogic').focus();
			return false;
		}
		
		
		
		if( document.getElementById('txtHEndMonth').value == "" )
		{
			alert("Please enter End Month ");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHEndMonth').focus();
			return false;
		}
		if( document.getElementById('txtHEndDays').value == "" )
		{
			alert("Please enter End Days ");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHEndDays').focus();
			return false;
		}
		if( document.getElementById('txtHExtMonth').value == "" )
		{
			alert("Please enter Extended Month");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHExtMonth').focus();
			return false;
		}
		if( document.getElementById('txtHExtDays').value == "" )
		{
			alert("Please enter Extended Days");
			but=document.getElementById('btnWarrentyDetails');
				if(but.value=="+")
				{
					butWarranFlag = true;
					show('tblWarrentyDetails',but);
				}
			document.getElementById('txtHExtDays').focus();
			return false;
		}
		
		
	}
		but=document.getElementById('btnWarrentyDetails');
			if(but.value=="-" && butWarranFlag == true)
			{
				show('tblWarrentyDetails',but);
			}
	}
	//ADDED TO COPY POID in Billing Level No. while PO Level is selected as Blling Level
	if(document.getElementById('txtBillingLvl').value=="2"){
		document.getElementById('txtBillingLevelNo').value=document.getElementById('txtPODetailNo').value;
	}
	//ADDED TO COPY POID in Billing Level No. while PO Level is selected as Blling Level
	
		var jsData =
		{
			serviceDetailID:Number(serviceProductID),
			serviceId:servicesID,
			podetailID:Number(document.getElementById('txtPODetailNo').value),
			accountID:Number(document.getElementById('txtBillingAC').value),
			creditPeriod:Number(document.getElementById('txtBillingCP').value),
			entityID:document.getElementById('txtEntity').value,
			billingType:Number(document.getElementById('txtBillingType').value),
			billingMode:document.getElementById('txtBillingMode').value,
			billingformat:document.getElementById('txtBillingformat').value,
			licenceCoID:Number(document.getElementById('txtLicenceCo').value),
			taxation:document.getElementById('txtTaxation').value,
			stdReasonId:document.getElementById('changeReason').value,
			commitmentPeriod:Number(document.getElementById('txtCmtPeriod').value),
			billingLevel:document.getElementById('txtBillingLvl').value,
			hardwareDetailID:Number(document.getElementById('hdnHardwareInfoID').value),
			billingInfoID:Number(document.getElementById('hdnBillingInfoID').value),
			locationInfoID:Number(document.getElementById('hdnServiceInfoID').value),
			penaltyClause:document.getElementById('txtPenaltyClause').value,
			warrantyClause:document.getElementById('txtWarrantClause').value,
			//satyapan osp
			isOSPTagging:document.getElementById('txtOSPTagging').value,//satyapan osp
			txtOSPRegNo:document.getElementById('txtOSPRegNo').value,
			txtOSPRegDate:document.getElementById('txtOSPRegDate').value,
			//satyapan osp
			serviceType:document.getElementById('txtHdnServiceName').value,
			selectedDispatchID:Number(document.getElementById('txtdispatchAddress').value),
			//[004] start
			dispatchContactName:document.getElementById('txtDispatchContactName').value,
			//[004] end
			selectedStoreID:Number(document.getElementById('txtStore').value),
			formAvailable:document.getElementById('txtform').value,
			hardwareType:document.getElementById('txtHtype').value,
			saleNature:document.getElementById('txtNSale').value,
			saleType:document.getElementById('txtTSale').value,
			selectedPriLocType:Number(document.getElementById('ddPrimaryLocType').value),
			selectedSecLocType:Number(document.getElementById('ddSecondaryLocType').value),
			selectedPNLocation:Number(document.getElementById('ddPNLocation').value),
			selectedPrimaryBCP:Number(document.getElementById('ddPrimaryBCP').value),
			selectedSNLocation:Number(document.getElementById('ddSNLocation').value),
			selectedSecBCP:Number(document.getElementById('ddSecondaryBCP').value),
			billingLevelNo:Number(document.getElementById('txtBillingLevelNo').value),
			principalAmount:Number(document.getElementById('txtPrincipalAmount').value),
			interestRate:Number(document.getElementById("txtInterestRate").value),
			chargesDetails : chargesDetailList,
			serviceInfoValue:Number(document.getElementById('hdnServiceSummary').value),
			billingInfoID:Number(document.getElementById('hdnBillingInfoID').value),
			hardwareDetailID:Number(document.getElementById('hdnHardwareInfoID').value),
			serviceProductID:Number(document.getElementById('hdnServiceProductID').value),
			billingInfoValue:Number(document.getElementById('hdnBillingInfo').value),
			chargeInfoValue:Number(document.getElementById('hdnChargeInfo').value),
			locationInfoValue:Number(document.getElementById('hdnLocationInfo').value),
			hardwareInfoValue:Number(document.getElementById('hdnHardwareInfo').value),
			billingBCPId:document.getElementById('bbPrimaryBCPID').value,
			billingBCPIdService:document.getElementById('bbPrimaryBCPIDService').value,
			startHWDateLogic:document.getElementById('txtStartDateLogic').value,
			endHWDateLogic:document.getElementById('txtEndDateLogic').value,
			startDate:document.getElementById('txtStartDate').value,
			endDate:document.getElementById('txtEndDate').value,
			fromLocation:document.getElementById('txtFAddress').value,
		    toLocation:document.getElementById('txtToAddress').value,
		    isNfa:document.getElementById('chkSelectNfa').value,
		    isUsage:Number(document.getElementById('chkIsUsage').value),
		    billingScenario:document.getElementById('txtBillingScenario').value,
		    fxRedirectionSPID:Number(document.getElementById('hdnFxRedirectionSPID').value),
			fxRedirectionLSI:Number(document.getElementById('txtFxRedirectionLSI').value),
		    
		    txtStartMonth:document.getElementById('txtHStartMonth').value,
			txtStartDays:document.getElementById('txtHStartDays').value,
			txtEndMonth:document.getElementById('txtHEndMonth').value,
			txtEndDays:document.getElementById('txtHEndDays').value,
			txtExtMonth:document.getElementById('txtHExtMonth').value,
			txtExtDays:document.getElementById('txtHExtDays').value,
			txtExtDate:document.getElementById('txtHExtDate').value,
		    
		    isReqTxtPODetailNo:Number(document.getElementById('txtPODetailNo').isRequired),
			isReqTxtBillingAC:Number(document.getElementById('txtBillingAC').isRequired),
			isReqTxtBillingCP:Number(document.getElementById('txtBillingCP').isRequired),
			isReqTxtEntity:Number(document.getElementById('txtEntity').isRequired),
			isReqTxtBillingMode:Number(document.getElementById('txtBillingMode').isRequired),
			isReqTxtBillingformat:Number(document.getElementById('txtBillingformat').isRequired),
			isReqTxtLicenceCo:Number(document.getElementById('txtLicenceCo').isRequired),
			isReqTxtTaxation:Number(document.getElementById('txtTaxation').isRequired),
			isReqTxtCmtPeriod:Number(document.getElementById('txtCmtPeriod').isRequired),
			isReqTxtBillingLvl:Number(document.getElementById('txtBillingLvl').isRequired),
			isReqTxtPenaltyClause:Number(document.getElementById('txtPenaltyClause').isRequired),
			isReqTxtWarrantyClause:Number(document.getElementById('txtWarrantClause').isRequired),
			//satyapan OSP By nagarjuna
			isReqOSPTagging:Number(document.getElementById('txtOSPTagging').isRequired),//satyapan osp
			isReqOSPRegNo:Number(document.getElementById('txtOSPRegNo').isRequired),
			isReqOSPRegDate:Number(document.getElementById('txtOSPRegDate').isRequired),
			//Satyapan OSP By Nagarjuna
			isReqSelectedDispatchID:Number(document.getElementById('txtdispatchAddress').isRequired),
			//[004] start
			isReqDispatchContactName:Number(document.getElementById('txtDispatchContactName').isRequired),
			//[004] end
			isReqDdPrimaryLocType:Number(document.getElementById('ddPrimaryLocType').isRequired),
			isReqDdSecondaryLocType:Number(document.getElementById('ddSecondaryLocType').isRequired),
			isReqSelectedPNLocation:Number(document.getElementById('ddPNLocation').isRequired),
			isReqSelectedPrimaryBCP:Number(document.getElementById('ddPrimaryBCP').isRequired),
			isReqSelectedSNLocation:Number(document.getElementById('ddSNLocation').isRequired),
			isReqSelectedSecBCP:Number(document.getElementById('ddSecondaryBCP').isRequired),
			isReqPrincipalAmount:Number(document.getElementById('txtPrincipalAmount').isRequired),
			isReqInterestRate:Number(document.getElementById("txtInterestRate").isRequired),
			isReqTxtStore:Number(document.getElementById('txtStore').isRequired),
			isReqTxtHtype:Number(document.getElementById('txtHtype').isRequired),
			isReqTxtform:Number(document.getElementById('txtform').isRequired),
			isReqTxtTSale:Number(document.getElementById('txtTSale').isRequired),
			isReqTxtNSale:Number(document.getElementById('txtNSale').isRequired),
			isReqTxtStore:Number(document.getElementById('txtStartDateLogic').isRequired),
			isReqTxtHtype:Number(document.getElementById('txtEndDateLogic').isRequired),
			isReqTxtFromLocation:Number(document.getElementById('txtFAddress').isRequired),
			isReqTxtToLocation:Number(document.getElementById('txtToAddress').isRequired), // by Saurabh for validation of to and from location
			poNumber:Number(changeOrderNo),
			changeTypeId:Number(callerWindowObj.document.getElementById('HdnChangeTypeID').value),
			componentDetails:componentsList,
			componentInfoValue:Number(document.getElementById('hdnComponentInfo').value),
			configValue:Number(document.getElementById('hdnConfigValue').value),
			deletedChargesList:document.getElementById('hdnDeletedChargesListId').value		
			
		};
	}
	/*if((document.getElementById('hdnServiceSummary').value==1) && attributeID != null)
	  {
	    //var jsonrpc1 = new JSONRpcClient(path + "" + "/JSON-RPC");
    	       var productType = jsonrpc1.processes.checkProductServiceType(serviceDetID);
    	       if(productType=='Hardware')
    	       {
    	        var Billingformat=document.getElementById('txtBillingformat').value;
			    var result=  checkProductServiceType(attributeID,newAttributeVal,Billingformat);
			   if(result == false)
			    return;
			  }
		}*/
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	try{	
		// add by anil for clep
		if(totalExtraChrgeAmount > 0 && clepState==1){
			alert("Total Charge amount "+totalExtraChrgeAmount+" will be added in PO Amount");
			totalExtraChrgeAmount=0;
		}
		//end clep
		
		//[003] Start
		//var sessionId='<%=request.getSession().getId() %>';
    	var insertPcat = jsonrpc.processes.updateProductCatelog(jsData,sessionid);
    	//[0010] added by Gunjan to remove existing bug
    	if(isNull(insertPcat.errors)==true){
    	 resetProducCatalogueFlags();
    	}
	}catch(e){
    	alert("exception :  "+ e);
    }
    
    if(isNull(insertPcat.errors)==true){
		var callerWindowObj = dialogArguments;
		FirstPage('CHARGEINFOID','CHANGEVIEWCHARGETABLE');
    	alert(insertPcat.msgOut);  
    	//---------[Reset Components Info and again populate components from DB,
    	//so that if user remain same page and again save it could not entry two times]---------------
    		resetComponentsRows();
    		getComponentAttribute();
    	//---------------------------------------------------
        //var flag=5;
    }	
    else
    {
    	alert(insertPcat.errors.list[0]);		
    	return false;
    }
}

function deleteProductCatelog()
{
	var callerWindowObj = dialogArguments;
	if(window.confirm("Do you want to Delete this Product Catelog? View Products Page will close on deletion."))
	{
		var jsData =
		{
			serviceProductID:serviceProductID
		};
		//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	    var deletePcat = jsonrpc.processes.deleteProductCatelog(jsData);
	    
	    if(isNull(deletePcat.errors)==true)
	    {
	    	alert(deletePcat.msgOut);  
	    	callerWindowObj.ViewServiceTree();
	    	window.close();
	    }	
	    else
	    {
	    	alert(deletePcat.errors.list[0]);		
	    	return false;
	    }
	}
    window.close();
}
function FetchBillingDetails()
{
	var tr, td, i, j, oneRecord;
    var test5;
   if(document.getElementById("bbPrimaryBCP").value != "")
    {
	    var jsData5 =			
	    {
			bcpID:document.getElementById("bbPrimaryBCP").value
		};
		
		//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
	
	    test5 = jsonrpc.processes.populateBCPDetails(jsData5);
		
	    if(test5.list.length>0) //if logic added by saurabh
	    {
		     document.getElementById('txtBAddress1').value = test5.list[0].baddress1;
			document.getElementById('txtBAddress2').value = test5.list[0].baddress2;
			document.getElementById('txtBAddress3').value = test5.list[0].baddress3;
			document.getElementById('txtBAddress4').value = test5.list[0].baddress4;
			document.getElementById('txtBCity').value = test5.list[0].bcity;
			document.getElementById('txtBState').value = test5.list[0].bstate;
			document.getElementById('txtBPincode').value = test5.list[0].bpincode;
			document.getElementById('txtBCountry').value = test5.list[0].bcountry;
			document.getElementById('txtBContactNo').value = test5.list[0].bcontactNo;
			document.getElementById('txtBEmailId').value = test5.list[0].bemailid;
			document.getElementById('designation').value = test5.list[0].designation;
			document.getElementById('lst_No').value = test5.list[0].lst_No;
			document.getElementById('lst_Date').value = test5.list[0].lstDate;
			document.getElementById('txtBContactName').value = test5.list[0].bcontactName;
		}
	}
	else
	{ 
	    document.getElementById('txtBAddress1').value = "";
		document.getElementById('txtBAddress2').value = "";
		document.getElementById('txtBAddress3').value = "";
		document.getElementById('txtBAddress4').value = "";
		document.getElementById('txtBCity').value = "";
		document.getElementById('txtBState').value = "";
		document.getElementById('txtBPincode').value = "";
		document.getElementById('txtBCountry').value = "";
		document.getElementById('txtBContactNo').value = "";
		document.getElementById('txtBEmailId').value = "";
		document.getElementById('designation').value = "";
		document.getElementById('lst_No').value = "";
		document.getElementById('lst_Date').value = "";
		document.getElementById('txtBContactName').value = "";
	}
}


function show(tbl,btn){
	if (btn.value=="-"){
		document.all.item(tbl).style.display="None";
		btn.value="+";
	}else{
		document.all.item(tbl).style.display="Inline";
		btn.value="-";
	}
}

function blankDispatchAddress(){

document.getElementById('txtHAddress1').value = "";
document.getElementById('txtHAddress2').value = "";
document.getElementById('txtHAddress3').value = "";
document.getElementById('txtHCityName').value = "";
document.getElementById('txtHStateName').value = "";
document.getElementById('txtHPincode').value = "";
document.getElementById('txtHPhnNo').value = "";
document.getElementById('txtHCountryName').value = "";

}

var treeNodes;
var global_firstNode;
var isProductDisconnected = 0;
function fncServiceTreeView()
{
	var guiServiceId=callerWindowObj.document.getElementById('hdnserviceid').value;

	document.getElementById("hdnServiceDetailID").value=serviceDetailID;
	document.getElementById('txtProductName').value=productName;
	document.getElementById('txtHdnServiceName').value=serviceName;
	var serviceTypeId=callerWindowObj.document.getElementById('hdnservicetypeid').value;
	document.getElementById('browser').innerHTML= "";
	 var jsData = 
			    {
			    	serviceId:callerWindowObj.document.getElementById('hdnserviceid').value,
			    	poNumber:changeOrderNo,
			    	logicalSINo:callerWindowObj.document.forms[0].logicalSINo.value,
					changeTypeId:callerWindowObj.document.getElementById('HdnChangeTypeID').value
			    };
			    
			  	//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
				var treeView = jsonrpc.processes.ViewServiceTreeAfterDisconnection(jsData);
				treeNodes = treeView;
				var url ="";
				var nodeText ="";
				var parentNodeId;
				var serviceDetailId;
				var changeSubtypeID = 0;
				var isDisconnected =0;
				var isAdditionalNode = 0;
				var billingFormat = 0;
				var isChargePresent=0;
				var createdInServiceId=0;
				
			
				for(i=0;i<treeView.lstTreeItems.list.length;i++)
				{
					if(treeView.lstTreeItems.list[i].serviceProductParentId=="0")
					{
					 	parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
					 	nodeText = treeView.lstTreeItems.list[i].treeViewText;
					 	serviceDetailId = treeView.lstTreeItems.list[i].serviceChildId;
					 	isChargePresent =  	treeView.lstTreeItems.list[i].isChargePresent;
					 	break;
					}
				}
				 global_firstNode=parentNodeId;
			
				var treeString=getTree(treeView,parentNodeId,nodeText,"",isDisconnected,changeSubtypeID,billingFormat,isAdditionalNode,isChargePresent,createdInServiceId,guiServiceId);

			 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
			 	$("#browser").treeview({
			 		add: branches
			 	});
				if(selectedIndexToBeChecked >0)
				{
				 document.getElementById('rad'+selectedIndexToBeChecked).checked =true;
				 chkIndexForRadio = 0;
				
				}
}
function TreeNode(nextNode,nextUrl , nextLabel)
{
	global_currTreeNode=nextNode;
	
	if(unescape(nextUrl)=="")
	{
		alert('Total Products in service level ' + unescape(nextLabel) + ' is/are ' + getTreeNodeCount(treeNodes,nextNode));
		fnCheckPreviousSelectedRadioButton(serviceProductID);
	}	
	else
	{
		if (isProductDisconnected ==1 ) {
			alert('Total Products in product level ' + unescape(nextLabel) + ' is/are ' + getTreeNodeCount(treeNodes,nextNode));
			var oTokens = unescape(nextUrl).tokenize('&',' ',true);
			
			productName=oTokens[5].substring(oTokens[5].indexOf("=")+1,oTokens[5].length);
			serviceName=oTokens[6].substring(oTokens[6].indexOf("=")+1,oTokens[6].length);
			serviceDetailID=oTokens[2].substring(oTokens[2].indexOf("=")+1,oTokens[2].length);
			serviceProductID=oTokens[3].substring(oTokens[3].indexOf("=")+1,oTokens[3].length);
			poNumber=oTokens[0].substring(oTokens[0].indexOf("=")+1,oTokens[0].length);
			document.getElementById('lblServiceProductId').innerHTML="LineItem No:"+serviceProductID;
			getServiceAttributes();
			
		}
		else 
		{
			if(window.confirm('Total Products in product level ' + unescape(nextLabel) + ' is/are ' + getTreeNodeCount(treeNodes,nextNode) + '\n' + "Do you want to save last product details?If Yes then Click Ok and Save the Data"))
			{
				fnCheckPreviousSelectedRadioButton(serviceProductID);
			}  
			else 
			{
				hdnLineItemName=nextLabel;
				resetChargesRows();
				resetComponentsRows();
				var oTokens = unescape(nextUrl).tokenize('&',' ',true);
			
				productName=oTokens[5].substring(oTokens[5].indexOf("=")+1,oTokens[5].length);
				serviceName=oTokens[6].substring(oTokens[6].indexOf("=")+1,oTokens[6].length);
				serviceDetailID=oTokens[2].substring(oTokens[2].indexOf("=")+1,oTokens[2].length);
				serviceProductID=oTokens[3].substring(oTokens[3].indexOf("=")+1,oTokens[3].length);
				poNumber=oTokens[0].substring(oTokens[0].indexOf("=")+1,oTokens[0].length);
				document.getElementById('hdnServiceProductID').value=serviceProductID;
				document.getElementById('hdnServiceID').value=serviceDetailID;
				document.getElementById('BillingNChargeInfo').style.display='block';
				document.getElementById('BillingInfo').style.display='none';
				document.getElementById('ServiceLocation').style.display='none';
				document.getElementById('HardwareDetails').style.display='none';
				document.getElementById('ChargeDetails').style.display='none';
				document.getElementById('Components').style.display='none';
				document.getElementById('lineItemServiceSummary').style.display='none';
				document.getElementById('lblServiceProductId').innerHTML="LineItem No:"+serviceProductID;
				getServiceAttributes();
				insertScMBtnForView(serviceDetailID);//Add By Deepak Kumar
			}
		}
	}	
}

function getTreeNodeCount(treeView,nextNode)
{

	var i;
	var localCount=0;
	for(i=0;i<treeView.lstTreeItems.list.length;i++)
	{
		if(nextNode==treeView.lstTreeItems.list[i].serviceProductParentId)
		{
			localCount = localCount + Number(getTreeNodeCount(treeView,treeView.lstTreeItems.list[i].serviceProductChildId));
		}
	}
	localCount = localCount +1;
	return localCount;
}
	
function getTree(treeView,nextNode,nextLabel,nextUrl,isDisconnected,changeSubtypeID,billingFormat,isAdditionalNode,isCharges,createdInServiceId,guiServiceId)
{

	var getNode="";
	var getNextNode="";
	var str="";
	var i;
	var chk = 0;
	var testURL = escape(nextUrl);
	if(chkIndexForRadio == 0 && callerWindowObj.document.forms[0].SPIDUrlforVPC.value == nextUrl) {
			chkIndexForRadio = 1;
	}
	if(isDisconnected == 1 && (changeSubtypeID ==3) ||  changeSubtypeID ==4) 
	{
		if(isCharges=="1")
			str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/30.gif' title='Disconnected Product'> <input type='radio'  id='rad" +ctindex+ "'  name='rdo1'  value = "+ nextNode +" onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
		else
			str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/30.gif' title='Disconnected Product'> <input type='radio'  id='rad" +ctindex+ "'  name='rdo1'  value = "+ nextNode +" onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
			
	} 
	else if (isDisconnected == 0 && billingFormat !=3 && changeSubtypeID==3)
	{	
		if(isCharges=="1")
			str = "<li><span > <input name='chkSPID' id='chkSPID' type='checkbox' value = "+ nextNode +"  ><input type='radio'   value = "+ nextNode +" id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
		else
			str = "<li><span > <input name='chkSPID' id='chkSPID' type='checkbox' value = "+ nextNode +"  ><input type='radio'   value = "+ nextNode +" id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
			
	} 
	else if(isDisconnected == 0 && billingFormat ==3 && (changeSubtypeID==3 ||  changeSubtypeID ==4)) 
	{
		if(isCharges=="1")
			str = "<li><span ><input type='radio' value = "+ nextNode +" id='rad" +ctindex+ "' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
		else
			str = "<li><span ><input type='radio' value = "+ nextNode +" id='rad" +ctindex+ "' name='rdo1' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
			
	}
	else {
		if (isAdditionalNode == 1 && changeSubtypeID ==0 ) 
		{
			if(isCharges=="1")
				str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/star.gif' title='Additional Product'> <input type='radio'   id='rad" +ctindex+ "' name='rdo1' value = "+ nextNode +" onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
			else
				str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/star.gif' title='Additional Product'> <input type='radio'   id='rad" +ctindex+ "' name='rdo1' value = "+ nextNode +" onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
		}
		else if(changeSubtypeID == 10 || changeSubtypeID == 19)
		{
			if(billingFormat==3 && createdInServiceId!=guiServiceId)
		    {
		    //[003] start
			    if(isCharges=="1")
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Shifted Product'> <input type='radio' disabled=true  value = "+ nextNode +" id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Shifted Product'> <input type='radio' disabled=true  value = "+ nextNode +" id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else
			{
				if(isCharges=="1")
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Shifted Product'> <input type='radio'  value = "+ nextNode +" id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else	
				  str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Shifted Product'> <input type='radio'  value = "+ nextNode +" id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			
			}
		}
		else if(changeSubtypeID == 9 || changeSubtypeID == 18)
		{
			if(billingFormat==3 && createdInServiceId!=guiServiceId)
		    {
			     if(isCharges=="1")
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Downgraded Product'> <input type='radio' disabled=true  value = "+ nextNode +" id='rad" +ctindex+ "'  name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
				    str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Downgraded Product'> <input type='radio' disabled=true  value = "+ nextNode +" id='rad" +ctindex+ "'  name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"		
			}
			else
			{
				if(isCharges=="1")
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Downgraded Product'> <input type='radio'  value = "+ nextNode +" id='rad" +ctindex+ "'  name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"
				else
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Downgraded Product'> <input type='radio'  value = "+ nextNode +" id='rad" +ctindex+ "'  name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
		}
		else if(changeSubtypeID == 8 || changeSubtypeID == 17)
		{
			if(billingFormat==3 && createdInServiceId!=guiServiceId)
		    {
			    if(isCharges=="1")
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Upgraded Product'> <input type='radio' disabled=true value = "+ nextNode +"  id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Upgraded Product'> <input type='radio' disabled=true value = "+ nextNode +"  id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
			else
			{
				if(isCharges=="1")
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Upgraded Product'> <input type='radio' value = "+ nextNode +"  id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Upgraded Product'> <input type='radio' value = "+ nextNode +"  id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\',\'"+ escape(changeSubtypeID) +"\') >"+ nextLabel + "</span>"	
			}
		}
		else if(changeSubtypeID == 6 || changeSubtypeID == 13)
		{
			if(isCharges=="1")
				str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/Suspend.jpg' title='Suspended Product'> <input type='radio' value = "+ nextNode +"  id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"
			else
				str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/Suspend.jpg' title='Suspended Product'> <input type='radio' value = "+ nextNode +"  id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
		}
		else if(changeSubtypeID == 7 || changeSubtypeID == 14)
		{
			 if(isCharges=="1")
				str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/resume.jpg' title='Resumed Product'> <input type='radio' value = "+ nextNode +"  id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
			else
				str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/resume.jpg' title='Resumed Product'> <input type='radio' value = "+ nextNode +"  id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
		}
		else if(changeSubtypeID == 20)
		{
			if(billingFormat==3 && createdInServiceId!=guiServiceId)
		    {
			    if(isCharges=="1")
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Network Change Product'> <input type='radio' value = "+ nextNode +" disabled=true   id='rad" +ctindex+ "'  name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
				 	str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Network Change Product'> <input type='radio' value = "+ nextNode +" disabled=true   id='rad" +ctindex+ "'  name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
			}
			else
			{
				if(isCharges=="1")
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Network Change Product'> <input type='radio' value = "+ nextNode +" id='rad" +ctindex+ "'  name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Network Change Product'> <input type='radio' value = "+ nextNode +" id='rad" +ctindex+ "'  name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
			}
		}
		else if(changeSubtypeID == 5)
		{
			if(billingFormat==3 && createdInServiceId!=guiServiceId)
		    {
			    if(isCharges=="1")
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Rate Renewed Product'> <input type='radio' value = "+ nextNode +" disabled=true  id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Rate Renewed Product'> <input type='radio' value = "+ nextNode +" disabled=true  id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
			}
			else
			{
				if(isCharges=="1")
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Rate Renewed Product'> <input type='radio' value = "+ nextNode +"  id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"	
				else
					str = "<li><span ><img src='<%=request.getContextPath()%>/gifs/top/lock.gif' title='Rate Renewed Product'> <input type='radio' value = "+ nextNode +"  id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
			}
		} 
		else {
			if(isCharges=="1")
				str = "<li><span class='folder'><input name='chkSPID' id='chkSPID' type='checkbox' value = "+ nextNode +"  ><input type='radio' value = "+ nextNode +" id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') ><font style='color:#FF0000'>"+ nextLabel + "</font></span>"
			else
			    str = "<li><span class='folder'><input name='chkSPID' id='chkSPID' type='checkbox' value = "+ nextNode +"  ><input type='radio' value = "+ nextNode +" id='rad" +ctindex+ "' name='rdo1'  onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >"+ nextLabel + "</span>"	
		//[003] end
		}
	}
	if(chkIndexForRadio == 1)
	{
	selectedIndexToBeChecked= ctindex;
	chkIndexForRadio = 0;
	}	
	
	ctindex++;
	for(i=0;i<treeView.lstTreeItems.list.length;i++)
	{
		isDisconnected = treeView.lstTreeItems.list[i].isChildDisconnected;
		changeSubtypeID = treeView.lstTreeItems.list[i].changeSubTypeID;
		isAdditionalNode=treeView.lstTreeItems.list[i].isAdditionalNode;
		billingFormat=treeView.lstTreeItems.list[i].billFormat;
		createdInServiceId=treeView.lstTreeItems.list[i].serviceId;
		//[003] start
		var guiServiceId=callerWindowObj.document.getElementById('hdnserviceid').value;
		if(nextNode==treeView.lstTreeItems.list[i].serviceProductParentId)
		{
		 	str=str+"<ul>"+getTree(treeView,treeView.lstTreeItems.list[i].serviceProductChildId,treeView.lstTreeItems.list[i].treeViewText,treeView.lstTreeItems.list[i].treeViewURL,isDisconnected,changeSubtypeID,billingFormat,isAdditionalNode,treeView.lstTreeItems.list[i].isChargePresent,createdInServiceId,guiServiceId)+"</ul>";
		}
		// [003] end
	}
	str=str+"</li>";
	return str;
}

function resetChargesRows() {
	/*var mytable = document.getElementById('tableCharges');	
	var iCountRows = document.getElementById('tableCharges').rows.length;
	for (i =iCountRows-1; i >=0; i--){	
       mytable.deleteRow(i);
	}*/ 
	var chargeTableDiv =  document.getElementById('contentscroll');
   	chargeTableDiv.innerHTML = "<table border='0' cellpadding='0' cellspacing='0' class='content' id='tableCharges'></table>";			 
}
function resetComponentsRows() {
	/*var mytable = document.getElementById('tableComponents');	
	var iCountRows = document.getElementById('tableComponents').rows.length;
	for (i =iCountRows-1; i >=0; i--){	
		mytable.deleteRow(i);
	}*/
	var compDiv = document.getElementById('contentscroll2');
	var compTable = "<table border='0' cellpadding='0' cellspacing='0' class='content' id='tableComponents'></table>";
	compDiv.innerHTML = compTable;
	document.getElementById('hdnComponentsCount').value =0;
}
/*function fillAnnotationField(var_counter)
{	
	var annotationText=document.getElementById("txtCAnnotation"+var_counter).value;		
	var local_typeCombo = document.getElementById("txtCName"+var_counter);	   
	var index=local_typeCombo.selectedIndex;	
	if(index!="0")
	{
		document.getElementById('txtCAnnotation'+var_counter).value=local_typeCombo.options[index].text;	
		annotationText=document.getElementById('txtCAnnotation'+var_counter).value;
	}
	else
	{	
		document.getElementById('txtCAnnotation'+var_counter).value=annotationText;
	}
}*/
/*function getTaxRate(var_counter)
{		
	if(document.getElementById("txtTaxation").value==1)
	{
	fetchTaxRate(path,var_counter);
	}
	else if(document.getElementById("txtTaxation").value==2)
	{
		document.getElementById("txtTaxRate"+var_counter).value="";
	}
}*/

//var gb_addChrgeAllowed;
//var gb_addChrgeAllowedRemarks;
var evaluatedCondition_PoLevelScenario=null;
var gb_mandatory_19Val=null;
var gb_mandatory_19Val_meta="not_assigned";
//var gbChargesAdditionForDisabledPo=null;// commented by Gunjan [0010]
function enableDisableFieldset(fieldSetId,enableDisableFlag)
{
	var elmList=document.getElementById(fieldSetId).getElementsByTagName('*');
	var true_false;
	if(enableDisableFlag=='ENABLE')		
	{
		true_false=false;
		//START[13082012]AutoSuggest for BCP
		$("#txtBCPAddressSuggest").attr('disabled', 'disabled');		
		$("#bcpAutoSuggestLinkId").hide();
		$("#txtBCPAddressSuggestService").attr('disabled', 'disabled');		
		$("#bcpAutoSuggestServiceLinkId").hide();
		//END[13082012]AutoSuggest for BCP
		//START[13082012]AutoSuggest for DISPATCH
		$("#txtDispatchAddressSuggest").attr('disabled', 'disabled');		
		$("#dispatchAutoSuggestLinkId").hide();
		//END[13082012]AutoSuggest for DISPATCH
	}
	else if(enableDisableFlag=='DISABLE')		
	{
		true_false=true;
		//START[13082012]AutoSuggest for BCP
		//[202020] START
		$("#txtBCPAddressSuggest").attr('disabled', 'disabled');		
		$("#bcpAutoSuggestLinkId").hide();
		$("#txtBCPAddressSuggestService").attr('disabled', 'disabled');		
		$("#bcpAutoSuggestServiceLinkId").hide();
		//END[13082012]AutoSuggest for BCP
		//START[13082012]AutoSuggest for DISPATCH
		$("#txtDispatchAddressSuggest").attr('disabled', 'disabled');		
		$("#dispatchAutoSuggestLinkId").hide();
		//[202020] END
		//END[13082012]AutoSuggest for DISPATCH
	}
	for(var i=0; i<elmList.length; i++) {
		  if(fieldSetId == 'chargesInfoFieldsetId'  && elmList[i].editable=='allow') {
		  	continue;
		  }
	switch(elmList[i].nodeName) {
        case 'INPUT':
        	if(elmList[i].id != 'btnHwDetails' && elmList[i].id != 'btnDispatchAddress' && elmList[i].id != 'btnWarrentyDetails' && elmList[i].id != 'btnServiceLocationDetails')
        		if(elmList[i].ctrltype !='undefined' && elmList[i].ctrltype != null && elmList[i].ctrltype=='dropdown')
        			elmList[i].disabled=true_false;
        		else
        			elmList[i].readOnly=true_false;
            break;    
        case 'SELECT':
            elmList[i].disabled=true_false;
            break;
        case 'TEXTAREA':
            elmList[i].readOnly=true_false;
            break;
        case 'a':
        	elmList[i].disabled=true_false;
        	break;
        case 'DIV':
            if(elmList[i].isDataEntry==1)
            {
            	document.getElementById("popUpLsiLink").disabled=true;
            	//The above disabling code is written for disabling RedirectionLsipopUp 
            	//only after checking isDataEntry==1 condition
            }
            break;
        default:
        }
    }
        if(editSolutionChangeOldProduct ==1 || hdnChangeTypeId == 3){
	    if(fieldSetId == 'billingInfoFieldsetId'){
	    	var txtPODetailNo = document.getElementById('txtPODetailNo');
	    	var poDetailNo = document.getElementById('poDetailNo');
	    	txtPODetailNo.disabled = false;
	    	poDetailNo.disabled = false;
	    	if(poNoForChking ==0){
	    		txtPODetailNo.value = 0;
	    		poDetailNo.value = "Select Cust PO Detail No";
	    	}
	    	if(hdnChangeTypeId == 1){
	    		txtPODetailNo.value = 0;
	    		poDetailNo.value = "Select Cust PO Detail No";
	    	}
	    } 
	    // [0010] -- if condition changed
	    //Vijay. Add condition for demo-regularize
    	//if(hdnChangeTypeId == 1 || hdnChangeTypeId == 3  || editSolutionChangeOldProduct == 1 && document.getElementById('txtBillingLvl').value == 2 && (hdnChangeTypeId ==5 || hdnChangeTypeId == 2 || (hdnChangeTypeId==4 && changeSubTypeID==12 )) ) {
	    if(hdnChangeTypeId == 1 || hdnChangeTypeId == 3) {
	    //nagarjuna start
    		//var gb_mandatory_19Val;
    		//if(callerWindowObj.document.getElementById("Mandatory_19").value != undefined ){
	    	//commented by [0010] start
    		/*if(gb_mandatory_19Val_meta=="not_assigned"){
    			//mandatory_19Val=callerWindowObj.document.getElementById("Mandatory_19").value;
    			var l_orderNo=callerWindowObj.document.getElementById('poNumber').value;
    			mandatory_19Val=jsonrpc.processes.getOrderAttribute(l_orderNo,39);
    			gb_mandatory_19Val_meta="assigned";
    		}*/
	    	
    		/*if(!(hdnChangeTypeId==5 && editSolutionChangeOldProduct == 1 && document.getElementById('txtBillingLvl').value == 2 && mandatory_19Val=="Y" ))
    		{*/
	    	//commented by [0010] end
    		var txtPODetailNo = document.getElementById('txtPODetailNo');
	    	var poDetailNo = document.getElementById('poDetailNo');
	    	var poDetailNoTXTLinkId = document.getElementById('poDetailNoTXTLinkId');
	    	txtPODetailNo.disabled = true;
	    	txtPODetailNo.isRequired = 0;
	    	poDetailNo.disabled = true;
	    	poDetailNo.isRequired = 0;
	    	poDetailNoTXTLinkId.disabled = true;
	    	poDetailNoTXTLinkId.isRequired = 0;
	    	
			document.getElementById('txtBillingPODate').isRequired = 0;
			document.getElementById('txtEntity').isRequired = 0;
			document.getElementById('legalEntityTXT').isRequired = 0;
			document.getElementById('txtLicenceCo').isRequired = 0;
			document.getElementById('licenseCo').isRequired = 0;
			document.getElementById('txtPoId').isRequired = 0;
			document.getElementById('txtCntrtMonths').isRequired = 0;
			
			//gbChargesAdditionForDisabledPo="not_allowed"; // commented by Gunjan [0010]
			////commented by [0010] start
    		/*}else{
    			//document.getElementById('idChargesAddBtn').disabled=true;

    			evaluatedCondition_PoLevelScenario="PO_LEVEL_DIFFERENTIAL_CASE_ENABLE";
    			//gb_addChrgeAllowed="not_allowed";
    			//gb_addChrgeAllowedRemarks="Add Charges are not allowed in PO Billing Level!";

    		}*/
			//commented by [0010] end
    		//nagarjuna end
		}   
    }
}

function enableDisableServiceSummaryOldAttributes(enableDisableFlag)
{
	if(document.getElementById('hdnServiceSummary').value==0)
	{
		return false;
	}	
	var true_false;
	var enableDisable_attributes=new Array();
	if(enableDisableFlag=='ENABLE')		
	{
		true_false=false;
	}
	else if(enableDisableFlag=='DISABLE')		
	{
		true_false=true;
	}
	var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
	for(i=1;i<=countAttributes;i++)
	{
		switch(document.getElementById('prodAttVal_'+i).nodeName) {
        case 'INPUT':
        	if(document.getElementById('prodAttVal_'+i).islinkType!=undefined){
        		//document.getElementById("newserviceSummaryLink_"+i).style.visibility="hidden";
        		document.getElementById("serviceSummaryLink_"+i).style.visibility="hidden";  
        	}else{
        		if(null != document.getElementById('autoSuggProdAttVal_'+i) && undefined != document.getElementById('autoSuggProdAttVal_'+i)){
        			document.getElementById('autoSuggProdAttVal_'+i).disabled=true;
        			if(null != document.getElementById('autoSuggProdAttLink_'+i) && undefined != document.getElementById('autoSuggProdAttLink_'+i)){
        				document.getElementById('autoSuggProdAttLink_'+i).disabled=true;
        			}
        		}
           		document.getElementById('prodAttVal_'+i).disabled=true_false;
           	}
            break;    
        case 'SELECT':
            document.getElementById('prodAttVal_'+i).disabled=true_false;
            break;
        case 'TEXTAREA':
            document.getElementById('prodAttVal_'+i).readOnly=true_false;
            break;
        default:
        }
	}
}
function readOnlyForSolutionChange(hdnChangeTypeId,changeSubTypeID, sectionName){
	if(null != sectionName && sectionName == serSummSection){
		enableDisableServiceSummaryOldAttributes('DISABLE');
		//Start[002]
		if(hdnChangeTypeId==1 || changeSubTypeID==20){
			enableDisableServiceSummaryNewAttributes('',hdnChangeTypeId,changeSubTypeID);
		}
		if(hdnChangeTypeId==2){
			enableDisableServiceSummaryNewAttributes('',2,0);
		}
	}
	//End[002]
	if(null != sectionName && sectionName == billingSection){
		enableDisableFieldset('billingInfoFieldsetId','DISABLE');//'SHRI RAM');
		enableDisableFieldset('chargesInfoFieldsetId','DISABLE');
	}
	if(null != sectionName && sectionName == serviceLocSection){
		enableDisableFieldset('serviceLocationFieldsetId','DISABLE');
	}
	if(null != sectionName && sectionName == hardwareSection){
		enableDisableFieldset('hardwareInfoFieldsetId','DISABLE');
	}
	
	/*var callerWindowObj = dialogArguments;
	var orderNo = callerWindowObj.document.getElementById('poNumber').value;
	var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleId,userIdNo);
	if(orderDetails.list.length>0){
		stage=orderDetails.list[0].stageName;
	}*/
		
	//Vijay. modify the condition for Demo-regularize	
	//if((hdnChangeTypeId==2 || hdnChangeTypeId==5 || (hdnChangeTypeId==4 && changeSubTypeID==12)) && stage!="Billing Trigger")
	if(null != sectionName && sectionName == billingSection){
		if(hdnChangeTypeId==2 || hdnChangeTypeId==5 || (hdnChangeTypeId==4 && changeSubTypeID==12) ){
			document.getElementById('idChargesAddBtn').disabled=false;
			document.getElementById('idChargesDeleteBtn').disabled=false;
		    //[0010]
			document.getElementById('idDisChargesAddBtn').disabled=false;
			
		}else{
			document.getElementById('idChargesAddBtn').disabled=true;
			document.getElementById('idChargesDeleteBtn').disabled=true;
			//[0010]
			document.getElementById('idDisChargesAddBtn').disabled=true;
			
		}
	}
	if(hdnChangeTypeId==2){
		if(changeSubTypeID==8 || changeSubTypeID==9 || changeSubTypeID==10){
			var serviceName=document.getElementById('txtHdnServiceName').value;
			if(null != sectionName && sectionName == serviceLocSection){
				enableDisableFieldset('serviceLocationFieldsetId','ENABLE');
			}
			if(serviceName=='Hardware'||serviceName=='Hardware-DC'||serviceName=='Hardware-TELE' ){
				if(null != sectionName && sectionName == serSummSection){
					enableDisableServiceSummaryOldAttributes('DISABLE');
				}
				var callerWindowObj = dialogArguments;
				//Commented sifting code For ISP UAT Observation as on Date 25-jul-12 : Ashutosh
				//if(callerWindowObj.document.getElementById("subChangeTypeId").value!= 10)
				//{
					if(document.getElementById('hdnServiceSummary').value==0){
						return false;
					}
					// 0,0 for all types of change type and sub change types
			 	    //Start[002]	
					if(null != sectionName && sectionName == serSummSection){
						enableDisableServiceSummaryNewAttributes('DISABLE',0,0);
						//End[002]
						document.getElementById('eLogicalCircuitId_new').readOnly=true;
						document.getElementById('InfraOderNo_new').readOnly=true;
						document.getElementById('metasolvCircuitId_new').readOnly=true;
					}
				//}
			}
		}
	}	
}
//start[002]
// used this function for enabling disabling on the basis of change type,subchange type and a flag in TPRODUCTLINEATTMASTER 


function enableDisableServiceSummaryNewAttributes(enableDisableFlag,changeType,subChangeType)
{
	  	if(changeType==0 && subChangeType==0)
	  	{	
	  
				var callerWindowObj = dialogArguments;
				 	if(document.getElementById('hdnServiceSummary').value==0)
					{
						return false;
					}
					var true_false;
					var enableDisable_attributes=new Array();
					if(enableDisableFlag=='ENABLE')		
					{
						true_false=false;
					}
					else if(enableDisableFlag=='DISABLE')		
					{
						true_false=true;
					}
					var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
					for(i=1;i<=countAttributes;i++)
					{
						switch(document.getElementById('newprodAttVal_'+i).nodeName) {
				        case 'INPUT':
				        	if(document.getElementById('newprodAttVal_'+i).islinkType!=undefined){
				        		if(true_false==true){
				           				document.getElementById("newserviceSummaryLink_"+i).disabled=false; 				           						           		
			           			}else{
									document.getElementById("newserviceSummaryLink_"+i).disabled=true;			           						           						           			
			           			}
				        	}else if(document.getElementById('newprodAttVal_'+i).isDate==undefined){
				           		document.getElementById('newprodAttVal_'+i).disabled=true_false;
				           		if(document.getElementById('autoSuggNewProdAttVal_'+i) !=null 
				           				&& document.getElementById('autoSuggNewProdAttLink_'+i) !=null){
				           			if(document.getElementById('newprodAttVal_'+i).baseCtrlType != undefined ){
				           				if(document.getElementById('newprodAttVal_'+i).baseCtrlType=='LOV' || document.getElementById('newprodAttVal_'+i).baseCtrlType=='DROPDOWN'){
				           					document.getElementById('autoSuggNewProdAttVal_'+i).disabled=true_false;
				           				}
				           			}
				           			//document.getElementById('autoSuggNewProdAttLink_'+i).readOnly=true_false;	
				           			if(true_false==true){
				           				document.getElementById("autoSuggNewProdAttLink_"+i).style.visibility="hidden"; 				           						           		
				           			}
				           		}
				           	}else{
				           		document.getElementById('newprodAttVal_'+i).disabled=true;
			           			document.getElementById('newprodAttImgVal_'+i).disabled=true_false;
			           			if(document.getElementById('autoSuggNewProdAttVal_'+i) !=null){
			           				document.getElementById('autoSuggNewProdAttVal_'+i).disabled=true;
			           				document.getElementById('autoSuggNewProdAttLink_'+i).disabled=true;
							    }
				           	}
				            break;  
				        case 'SELECT':
				            document.getElementById('newprodAttVal_'+i).disabled=true_false;
				            break;
				        case 'TEXTAREA':
				            document.getElementById('newprodAttVal_'+i).readOnly=true_false;
				            break;
				        default:
				        }
					}
					 
 	  	}
 	  	else if ((changeType==1 && subChangeType==20)||(changeType==2))
 		{
			 	if(document.getElementById('hdnServiceSummary').value==0)
				{
					return false;
				} 
				var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
				var true_false;
				
				for(i=1;i<=countAttributes;i++)
				{					
						if(changeType==1 && subChangeType==20)
						{
								/*if((document.getElementById('newprodAttVal_'+i).subchangetypeNetworkChangeEditable==1))
					       	{
					       		true_false=false;
					       	}else
					       	{*/
					       		true_false=true;
					       	//}
				       	}else if(changeType==2)
				       	{
					       	if(document.getElementById('newprodAttVal_'+i).changeTypeSolutionChangeEditable==1)
					       	{
					       		true_false=false;
					       	}else
					       	{
					       		true_false=true;
					       	}
				       	}
					switch(document.getElementById('newprodAttVal_'+i).nodeName) {
				        case 'INPUT':
				        	if(document.getElementById('newprodAttVal_'+i).islinkType!=undefined){
				        		if(true_false==true){
				           				document.getElementById("newserviceSummaryLink_"+i).disabled=false; 				           						           		
			           			}else			           			{
									document.getElementById("newserviceSummaryLink_"+i).disabled=true;			           						           						           			
			           			}
				        	}else if(document.getElementById('newprodAttVal_'+i).isDate==undefined){
				           		document.getElementById('newprodAttVal_'+i).readOnly=true_false;
				           		if(document.getElementById('autoSuggNewProdAttVal_'+i) !=null && document.getElementById('autoSuggNewProdAttLink_'+i) !=null){
				           			if(document.getElementById('newprodAttVal_'+i).baseCtrlType != undefined ){
				           				if(document.getElementById('newprodAttVal_'+i).baseCtrlType=='LOV'){
				           					document.getElementById('autoSuggNewProdAttVal_'+i).readOnly=true_false;
				           				}
				           			}
				           			//document.getElementById('autoSuggNewProdAttLink_'+i).readOnly=true_false;	
				           			if(true_false==true){
				           				document.getElementById("autoSuggNewProdAttLink_"+i).style.visibility="hidden"; 				           						           		
				           			}
				           		}
				           	}else{
				           		document.getElementById('newprodAttVal_'+i).readOnly=true;
			           			document.getElementById('newprodAttImgVal_'+i).disabled=true_false;
			           			if(document.getElementById('autoSuggNewProdAttVal_'+i) !=null){
							    document.getElementById('autoSuggNewProdAttVal_'+i).readOnly=true;
							    document.getElementById('autoSuggNewProdAttLink_'+i).readOnly=true;
							    }
				           	}
				            break;    
				        case 'SELECT':
				            document.getElementById('newprodAttVal_'+i).disabled=true_false;
				            break;
				        case 'TEXTAREA':
				            document.getElementById('newprodAttVal_'+i).readOnly=true_false;
				            break;
				        default:
					}
			       
		         
				}
 		} 
}
//End[002]
function enableDisableSections(sectionName){
	var oldEditSolutionChangeOldProduct = editSolutionChangeOldProduct;
	//Vijay. add condition for demo-regularize
	if((hdnChangeTypeId==2 && (changeSubTypeID==8 || changeSubTypeID==9 || changeSubTypeID==10)) 
			|| (hdnChangeTypeId==5 && changeSubTypeID==5) || ((hdnChangeTypeId==4 && changeSubTypeID==12)) 
			|| (hdnChangeTypeId==1 && changeSubTypeID==20)){
		editSolutionChangeOldProduct=1;
	}else if(hdnChangeTypeId==5 ){
		if(null != sectionName && sectionName == billingSection){
			document.getElementById('idChargesAddBtn').disabled=false;
			document.getElementById('idChargesDeleteBtn').disabled=false;
			//[0010]
			document.getElementById('idDisChargesAddBtn').disabled=false;
		}
	}
	//[303030] START
	if(editSolutionChangeOldProduct==1 || hdnChangeTypeId  == 3 || hdnChangeTypeId  == 1 ){
	//[303030 END]
		readOnlyForSolutionChange(hdnChangeTypeId,changeSubTypeID, sectionName);
	} 	
	
	if(hdnChangeTypeId==2 || hdnChangeTypeId==5 || (hdnChangeTypeId==4 && changeSubTypeID==12) ){
		if(null != sectionName && (sectionName == hardwareSection || sectionName == serviceLocSection)){
			var jsData ={
					serviceId:callerWindowObj.document.getElementById('hdnserviceid').value,
			    	poNumber:changeOrderNo,
			    	logicalSINo:callerWindowObj.document.forms[0].logicalSINo.value,
					changeTypeId:callerWindowObj.document.getElementById('HdnChangeTypeID').value
			};
		  	//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
			var treeView = jsonrpc.processes.ViewServiceTreeAfterDisconnection(jsData);
			for(i=0;i<treeView.lstTreeItems.list.length;i++){
				var additionalNode=treeView.lstTreeItems.list[i].isAdditionalNode;
				if(additionalNode == 1 && serviceProductID == treeView.lstTreeItems.list[i].serviceProductChildId){	
					if(null != sectionName && sectionName == hardwareSection){
						enableDisableFieldset('hardwareInfoFieldsetId','DISABLE');
					}
					if(null != sectionName && sectionName == serviceLocSection){
						enableDisableFieldset('serviceLocationFieldsetId','DISABLE');
					}
					$("#txtBCPAddressSuggest").attr('disabled', false);		
					$("#bcpAutoSuggestLinkId").show();
					$("#txtBCPAddressSuggestService").attr('disabled', false);
					if(document.getElementById('hdnHardwareInfo').value==1){
						$("#bcpAutoSuggestServiceLinkId").hide();
					}else{
						$("#bcpAutoSuggestServiceLinkId").show();
					}	
				}
			}
		}
	}
	editSolutionChangeOldProduct = oldEditSolutionChangeOldProduct;
}
function PopLocTypePopup(LOC) 
	{
			if(!(isView ==null || isView == "null"))
			{
				return false;
			}
		    if (LOC=='PRILOC')
		    {
		      document.getElementById("location").value='PRILOC';
		    }
		    else
		    {
		     document.getElementById("location").value='SECLOC';
		    }
			var path1 = path + "/NewOrderAction.do?method=fetchNetworkPopLocation";		
			window.showModalDialog(path1,window,"status:false;dialogWidth:900px;dialogHeight:530px");
	}
function fnFetchContractPeriod(poId){
	var callerWindowObj = dialogArguments;
	counter = callerWindowObj.document.getElementById('tablePO').rows.length;
	for(var i=1;i<counter;i++){
		if(poId==callerWindowObj.document.getElementsByName('poDetailNo')[i-1].value){
			document.getElementById('txtCntrtMonths').value = callerWindowObj.document.getElementsByName('periodsInMonths')[i-1].value;
			//Puneet adding for performance tuning
			break;
		}
	}
}
	function checkNfa()
	{
		if(document.getElementById("chkSelectNfa").checked==true)
		{
		document.getElementById("chkSelectNfa").value=1
		alert("You are converting this order as NFA");
		}
		else
		{
		document.getElementById("chkSelectNfa").value=0
		}
	}
document.onkeydown=checkKeyPress;
function checkKeyPress(){
	if (event.ctrlKey && event.keyCode == 83) {		
		if(document.getElementById('saveImage').disabled==false && $("#saveImage").is(":visible")){
			//alert('This functionality is not allowed for this page.Please click on the SAVE button to save!! ');
			//return false;				
			event.keyCode=4; 
			saveProductCatelog();
			document.getElementById("saveImage").tabIndex = -1;
			document.getElementById("saveImage").focus();	
		}														   				   						
	}else if(event.keyCode == 40 && event.srcElement.ctrltype=="dropdown" && null == $("#" + event.srcElement.id).data().autocomplete){
		getAutoSuggest($("#" + event.srcElement.id));
		$("#" + event.srcElement.id).autocomplete("search", "" );
		$("#" + event.srcElement.id).focus();
	}else if(event.srcElement.ctrltype=="dropdown" && null == $("#" + event.srcElement.id).data().autocomplete){
	   	getAutoSuggest($("#" + event.srcElement.id));
	   	$("#" + event.srcElement.id).autocomplete("search", "" );
		$("#" + event.srcElement.id).autocomplete("change", "" );
		$("#" + event.srcElement.id).focus();
	}      
}

var hook=true;
window.onbeforeunload = function() 
{
	
	if(serviceListArray.length==0)
	{
		callerWindowObj.document.getElementById('hdnServiceProductID').value=serviceProductID;
		callerWindowObj.document.getElementById('hdnserviceid').value = serviceNo;
		if(isView == null)
	   	{
	    	callerWindowObj.ServiceTreeViewAfterDisconnection(serviceNo);
	  	}
	}
	else
	{
		callerWindowObj.document.getElementById('hdnServiceProductID').value=initialSPID;
		callerWindowObj.document.getElementById('hdnserviceid').value = initialServiceNo;
	    if(isView == null)
	   	{
	    	callerWindowObj.ServiceTreeViewAfterDisconnection(initialServiceNo);
	  	}
	}
	
   	callerWindowObj.document.getElementById('hdnServiceProductName').value=hdnLineItemName;
   	
    if (hook) 
    if ((window.event.clientX < 0) || (window.event.clientY < 0) || (window.event.clientX < -80))
    {
    {
      return "This will cause losing any unsaved data ! "
    } 
    }   
}
function unhook() 
{
     hook=false;
}

function fnCheckForM6LineItem()
{
	var sendToM6Check;
	var serviceDetailId = document.getElementById('hdnserviceDetailID').value;
	if(document.getElementById('ddPrimaryLocType').value==2)
	{
		//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
		sendToM6Check = jsonrpc.processes.getM6LineItemCheck(serviceDetailId);
		if(sendToM6Check==0)
		{
			alert('Network location is not Allowed for this Line Item')
			document.getElementById('ddPrimaryLocType').value=0;
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
			return false;
		}
	}
	if(document.getElementById('ddSecondaryLocType').value==2)
	{
		//var jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
		sendToM6Check = jsonrpc.processes.getM6LineItemCheck(serviceDetailId);
		if(sendToM6Check==0)
		{
			alert('Network location is not Allowed for this Line Item')
			document.getElementById('ddSecondaryLocType').value=0;
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='none';
			return false;
		}
	}
}

function fnCheckPreviousSelectedRadioButton(ServiceProductId)
{
	for(var i =1;i<=ctindex;i++)
	{
		if((document.getElementById('rad'+i)) != null)
		{
			if(document.getElementById('rad'+i).value==ServiceProductId)
			{
				document.getElementById('rad'+i).checked=true;
			}
		}	
	}		
}

var destAttID=new Array();
function fnGetDestLabelValueForLOV(attMstrId,fieldName)
{
	var labelValue;
	//var textValue = fieldName.value;
	//var select_list_field = fieldName; 
	//var select_list_selected_index = select_list_field.selectedIndex;
	//var attLabelvalue = select_list_field.options[select_list_selected_index].text;
	var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
	// added by manisha defect no : 19032013001 start 
		// added by manisha defect no : 19032013001 start 
	var new_old_servicesummary_present=0;			
			if(hdnChangeTypeId==2 && (changeSubTypeID==8 || changeSubTypeID==9 || changeSubTypeID==10) || (hdnChangeTypeId==1 && changeSubTypeID==20) )
			{
				new_old_servicesummary_present=1;
			}
	// added by manisha defect no : 19032013001	end
		
	var attLabelvalue = fieldName;
	var jsData =			
    {
		attMasterId:attMstrId,
		attributeLabel:attLabelvalue
	};
	
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
    labelValue = jsonrpc.processes.fillDropDownDependentlabel(jsData);
    if(labelValue.list.length>0)
	{
		for(var k=0;k<destAttID.length;k++)
		{
			destAttID.pop();
		}
		destAttID=new Array();
		for(var j = 0;j<labelValue.list.length;j++)
		{
			destAttID[j] = labelValue.list[j].destAttId;
			for(var i=1;i<=countAttributes;i++)
			{
					if(new_old_servicesummary_present==1) // if condtion added by manisha defect no : 19032013001
					{ 
							if(document.getElementById("hdnnewProdAttVal_"+i) != null)
								{
									if(document.getElementById("hdnnewProdAttVal_"+i).value == destAttID[j])
									{
										//[004.1] Start
										var depValue=labelValue.list[j].planName;//Assigning Default Value from DB to a Variable e.g prodAttVal_15 or abc 
										//destText Added By Kalpana to get Text for selectd Plan
										var destText=labelValue.list[j].destText;//Assigning Default TEXT Value from DB to destination attribute
										//end
										if(depValue.indexOf("newprodAttVal")!=-1)//checking prodAttVal value in depValue varible via contains function of Javascript. if it founds it goes in if or in else
										{
											var splitValue=depValue.split('_');//Spliting depValue by using _ So element1 is prodAttVal and element is 8 
											document.getElementById("newprodAttVal_"+i).value=document.getElementById("newprodAttVal_"+splitValue[1]).value;//assigning Dependent Value, 2nd elememnt of split function
										}
										else
										{
											document.getElementById("newprodAttVal_"+i).value=depValue;//Assigning value from DB
											//set value in id autosuggest by kalpana
											if(document.getElementById("autoSuggNewProdAttVal_"+i) != null){
												if(destText!=null){
													document.getElementById("autoSuggNewProdAttVal_"+i).value=destText;
												}else{
													document.getElementById("autoSuggNewProdAttVal_"+i).value=depValue;
												}
											}
											//end
										}
										//Start [005]
										/*if(document.getElementById("autoSuggNewProdAttVal_"+i) != null)
										{
											document.getElementById("autoSuggNewProdAttVal_"+i).value='';
										}*/
										//End [005]
										//[004.1] End
										if(labelValue.list[j].isReadOnly == '1')
										{
											
											if(document.getElementById("newprodAttVal_"+i).type=='select-one')
											{
												
												document.getElementById("newprodAttVal_"+i).disabled=true;
											}
											else
											{
												
												document.getElementById("newprodAttVal_"+i).readOnly=true; 
											}
											//added zeroValueAllowed control to check whether zero value is allowed on that control or not
											document.getElementById("newprodAttVal_"+i).zeroValueAllowed='Y';
											if(document.getElementById("autoSuggNewProdAttVal_"+i) != null)
											{
													document.getElementById("autoSuggNewProdAttVal_"+i).disabled=true;
													document.getElementById("autoSuggNewProdAttLink_"+i).style.display='none';															
											}
											
										}
										if(labelValue.list[j].isReadOnly == '0')
										{
											
											if(document.getElementById("newprodAttVal_"+i).type=='select-one')
											{
												
												document.getElementById("newprodAttVal_"+i).disabled=false;
											}
											else{
												
												document.getElementById("newprodAttVal_"+i).readOnly=false; 
												}
											//added zeroValueAllowed control to check whether zero value is allowed on that control or not
											document.getElementById("newprodAttVal_"+i).zeroValueAllowed='N';
											
											if(document.getElementById("autoSuggNewProdAttVal_"+i) != null)
											{
													document.getElementById("autoSuggNewProdAttVal_"+i).disabled=false;
													document.getElementById("autoSuggNewProdAttLink_"+i).style.display='block';															
											}
										}
										//[004.1] Start
										if(labelValue.list[j].mandatory == '0')
										{
											document.getElementById("newprodAttVal_"+i).isSummaryReq='0';
											document.getElementById("newprodAttVal_"+i).className='inputBorder2';
										}
										if(labelValue.list[j].mandatory == '1')
										{
											document.getElementById("newprodAttVal_"+i).isSummaryReq='1';
											document.getElementById("newprodAttVal_"+i).className='inputBorder1';
										}
										//[004.1] End
									}
								}
							}	
						
					else  // else condtion added by manisha defect no : 19032013001	start
					{
						if(document.getElementById("hdnProdAttVal_"+i) != null)
						{
						if(document.getElementById("hdnProdAttVal_"+i).value == destAttID[j])
							{
								//[004.1] Start
								//document.getElementById("prodAttVal_"+i).value=labelValue.list[j].planName;
								var depValue=labelValue.list[j].planName;//Assigning Default Value from DB to a Variable e.g prodAttVal_15 or abc 
								if(depValue.indexOf("prodAttVal")!=-1)//checking prodAttVal value in depValue varible via contains function of Javascript. if it founds it goes in if or in else
								{
									var splitValue=depValue.split('_');//Spliting depValue by using _ So element1 is prodAttVal and element is 8 
									document.getElementById("prodAttVal_"+i).value=document.getElementById("prodAttVal_"+splitValue[1]).value;//assigning Dependent Value, 2nd elememnt of split function
								}
								else
								{
									document.getElementById("prodAttVal_"+i).value=depValue;//Assigning value from DB
								}
								if(document.getElementById("autoSuggProdAttVal_"+i) != null)
								{
									document.getElementById("autoSuggProdAttVal_"+i).value='';
								}
								//[004.1] End
								document.getElementById("prodAttVal_"+i).value=labelValue.list[j].planName;
								if(labelValue.list[j].isReadOnly == '1')
								{
									if(document.getElementById("prodAttVal_"+i).type=='select-one')
										document.getElementById("prodAttVal_"+i).disabled=true;
									else
										document.getElementById("prodAttVal_"+i).readOnly=true; 
									//added zeroValueAllowed control to check whether zero value is allowed on that control or not
									document.getElementById("prodAttVal_"+i).zeroValueAllowed='Y';
									
									if(document.getElementById("autoSuggProdAttVal_"+i) != null)
									{
										document.getElementById("autoSuggProdAttVal_"+i).disabled=true;
										document.getElementById("autoSuggProdAttLink_"+i).style.display='none';															
									}
								}
								if(labelValue.list[j].isReadOnly == '0')
								{
									if(document.getElementById("prodAttVal_"+i).type=='select-one')
										document.getElementById("prodAttVal_"+i).disabled=false;
									else
										document.getElementById("prodAttVal_"+i).readOnly=false; 
									//added zeroValueAllowed control to check whether zero value is allowed on that control or not
									document.getElementById("prodAttVal_"+i).zeroValueAllowed='N';
									
									if(document.getElementById("autoSuggProdAttVal_"+i) != null)
									{
										document.getElementById("autoSuggProdAttVal_"+i).disabled=false;
										document.getElementById("autoSuggProdAttLink_"+i).style.display='block';															
									}
								}
								//[004.1] Start
								if(labelValue.list[j].mandatory == '0')
								{
									document.getElementById("prodAttVal_"+i).isSummaryReq='0';
									document.getElementById("prodAttVal_"+i).className='inputBorder2';
								}
								if(labelValue.list[j].mandatory == '1')
								{
									document.getElementById("prodAttVal_"+i).isSummaryReq='1';
									document.getElementById("prodAttVal_"+i).className='inputBorder1';
								}
								//[004.1] End
						}
				}	// else condtion added by manisha defect no : 19032013001	end	
			}
		}	}
	}
}
/*
Method fetched List of values for Dropdowns 

*/
function fnGetDestLabelValue(attMstrId,fieldName){
	var labelValue;
	var textValue = fieldName.value;
	var select_list_field = fieldName; 
	var select_list_selected_index = select_list_field.selectedIndex;
	var attLabelvalue = select_list_field.options[select_list_selected_index].text;
	var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
	var jsData = {
		attMasterId:attMstrId,
		attributeLabel:attLabelvalue
	};
	
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
    labelValue = jsonrpc.processes.fillDropDownDependentlabel(jsData);
    if(labelValue.list.length>0)
	{
		for(var k=0;k<destAttID.length;k++)
		{
			destAttID.pop();
		}
		destAttID=new Array();
		for(var j = 0;j<labelValue.list.length;j++)
		{
			destAttID[j] = labelValue.list[j].destAttId;
			for(var i=1;i<=countAttributes;i++)
			{
				//[004.1] Start
				if(fieldName.name.indexOf("newprodAttVal_")!=-1)//check whether user have changed New Value for existing line item or Old Value for New Line item
				{
					//lawkush Start
					if(document.getElementById("hdnnewProdAttVal_"+i) != null)
					{
						if(document.getElementById("hdnnewProdAttVal_"+i).value == destAttID[j])
						{
							//[004.1] Start
							//document.getElementById("newprodAttVal_"+i).value=labelValue.list[j].planName;
							var depValue=labelValue.list[j].planName;//Assigning Default Value from DB to a Variable e.g prodAttVal_15 or abc 
							if(depValue.indexOf("prodAttVal")!=-1)//checking prodAttVal value in depValue varible via contains function of Javascript. if it founds it goes in if or in else
							{
								var splitValue=depValue.split('_');//Spliting depValue by using _ So element1 is prodAttVal and element is 8 
								document.getElementById("newprodAttVal_"+i).value=document.getElementById("newprodAttVal_"+splitValue[1]).value;//assigning Dependent Value, 2nd elememnt of split function
							}
							else
							{
								document.getElementById("newprodAttVal_"+i).value=depValue;//Assigning value from DB
							}
							if(document.getElementById("autoSuggNewProdAttVal_"+i) != null)
							{
								document.getElementById("autoSuggNewProdAttVal_"+i).value='';
							}
							//[004.1] End
							if(labelValue.list[j].isReadOnly == '1')
							{
								if(document.getElementById("newprodAttVal_"+i).type=='select-one')
									document.getElementById("newprodAttVal_"+i).disabled=true;
								else
									document.getElementById("newprodAttVal_"+i).readOnly=true; 
								//added zeroValueAllowed control to check whether zero value is allowed on that control or not
								document.getElementById("newprodAttVal_"+i).zeroValueAllowed='Y';
							}
							if(labelValue.list[j].isReadOnly == '0')
							{
								if(document.getElementById("newprodAttVal_"+i).type=='select-one')
									document.getElementById("newprodAttVal_"+i).disabled=false;
								else
									document.getElementById("newprodAttVal_"+i).readOnly=false; 
								//added zeroValueAllowed control to check whether zero value is allowed on that control or not
								document.getElementById("newprodAttVal_"+i).zeroValueAllowed='N';
							}
							//[004.1] Start
							if(labelValue.list[j].mandatory == '0')
							{
								document.getElementById("newprodAttMandatory_"+i).value='0';
								document.getElementById("newprodAttMandatory_"+i).className='inputBorder2';
								initValidationForServiceSummary()
							}
							if(labelValue.list[j].mandatory == '1')
							{
								document.getElementById("newprodAttMandatory_"+i).value='1';
								document.getElementById("newprodAttMandatory_"+i).className='inputBorder1';
								initValidationForServiceSummary()
							}
							//[004.1] End
				
							if(document.getElementById("hdnProdAttVal_"+i) != null)
							{
								if(document.getElementById("hdnProdAttVal_"+i).value == destAttID[j])
								{
									//[004.1] Start
									//document.getElementById("newprodAttVal_"+i).value=labelValue.list[j].planName;
									var depValue=labelValue.list[j].planName;//Assigning Default Value from DB to a Variable e.g prodAttVal_15 or abc 
									if(depValue.indexOf("prodAttVal")!=-1)//checking prodAttVal value in depValue varible via contains function of Javascript. if it founds it goes in if or in else
									{
										var splitValue=depValue.split('_');//Spliting depValue by using _ So element1 is prodAttVal and element is 8 
										document.getElementById("prodAttVal_"+i).value=document.getElementById("prodAttVal_"+splitValue[1]).value;//assigning Dependent Value, 2nd elememnt of split function
									}	
									else
									{
										document.getElementById("prodAttVal_"+i).value=depValue;//Assigning value from DB
									}
									if(document.getElementById("autoSuggProdAttVal_"+i) != null)
									{
										document.getElementById("autoSuggProdAttVal_"+i).value='';
									}
									//[004.1] End
									document.getElementById("prodAttVal_"+i).value=labelValue.list[j].planName;
									if(labelValue.list[j].isReadOnly == '1')
									{
								if(document.getElementById("prodAttVal_"+i).type=='select-one')
									document.getElementById("prodAttVal_"+i).disabled=true;
								else
										document.getElementById("prodAttVal_"+i).readOnly=true; 
										//added zeroValueAllowed control to check whether zero value is allowed on that control or not
										document.getElementById("prodAttVal_"+i).zeroValueAllowed='Y';
									}
									if(labelValue.list[j].isReadOnly == '0')
									{
								if(document.getElementById("prodAttVal_"+i).type=='select-one')
									document.getElementById("prodAttVal_"+i).disabled=false;
								else
									document.getElementById("prodAttVal_"+i).readOnly=false; 
									//added zeroValueAllowed control to check whether zero value is allowed on that control or not
									document.getElementById("prodAttVal_"+i).zeroValueAllowed='N';
									}
								}
							}
							//[004.1] Start
							if(labelValue.list[j].mandatory == '0')
							{
								document.getElementById("prodAttVal_"+i).isSummaryReq='0';
								document.getElementById("prodAttVal_"+i).className='inputBorder2';
								initValidationForServiceSummary()
							}
							if(labelValue.list[j].mandatory == '1')
							{
								document.getElementById("prodAttVal_"+i).isSummaryReq='1';
								document.getElementById("prodAttVal_"+i).className='inputBorder1';
								initValidationForServiceSummary()
							}
							//[004.1] End
							//lawkush Start
							if(document.getElementById("hdnnewProdAttVal_"+i) != null)
							{
								if(document.getElementById("hdnnewProdAttVal_"+i).value == destAttID[j])
								{
									document.getElementById("newprodAttVal_"+i).value=labelValue.list[j].planName;
									if(labelValue.list[j].isReadOnly == '1')
									{
									document.getElementById("newprodAttVal_"+i).readOnly=true; 
									//added zeroValueAllowed control to check whether zero value is allowed on that control or not
									document.getElementById("newprodAttVal_"+i).zeroValueAllowed='Y';
									}
									if(labelValue.list[j].isReadOnly == '0')
									{
									document.getElementById("newprodAttVal_"+i).readOnly=false; 
									//added zeroValueAllowed control to check whether zero value is allowed on that control or not
									document.getElementById("newprodAttVal_"+i).zeroValueAllowed='N';
									}
								}
							}	
							//lawkush End
						}	
					}
				}
			}
		}
	}
}
//Puneet changed the function signature to get the dep label values of all drop downs
//function fnGetDestLabelValue_onLoad(attMstrId,fieldName){
function fnGetDestLabelValue_onLoad(dropDownLovIDLabels, callType){
	//var labelValue;
	//var textValue = fieldName.value;
	//var select_list_field = fieldName; 
	//var select_list_selected_index = select_list_field.selectedIndex;	
	//var attLabelvalue = select_list_field.options[select_list_selected_index].text;	
	var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
	/*var attLabelvalue = fieldName;
	var jsData = {
		attMasterId:attMstrId,
		attributeLabel:attLabelvalue
	};*/
	
	//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
    //labelValue = jsonrpc.processes.fillDropDownDependentlabel(jsData);
	var allLabelValue = jsonrpc.processes.fillAllDropDownDependentlabel(dropDownLovIDLabels);
    if(null != allLabelValue && allLabelValue.list.length>0){
		/*for(var k=0;k<destAttID.length;k++){
			destAttID.pop();
		}*/
    	destAttID = new Array();
    	var destAttIndex = 0;
		for(var j = 0;j<allLabelValue.list.length;j++){
			for(var h = 0; h<allLabelValue.list[j].serviceSummary.list.length;h++){
				var labelValue = allLabelValue.list[j].serviceSummary.list[h];	
				destAttID[destAttIndex] = labelValue.destAttId;
				//destAttID[j] = labelValue.list[j].destAttId;			
				for(var i=1;i<=countAttributes;i++){
					if(document.getElementById("hdnnewProdAttVal_"+i) != null && callType == "new"){
						if(document.getElementById("hdnnewProdAttVal_"+i).value == destAttID[destAttIndex]){
							//document.getElementById("newprodAttVal_"+i).value=labelValue.list[j].planName;
							if(labelValue == '1'){
								if(document.getElementById("newprodAttVal_"+i).type=='select-one')
									document.getElementById("newprodAttVal_"+i).disabled=true;
								else
									document.getElementById("newprodAttVal_"+i).readOnly=true; 
								//added zeroValueAllowed control to check whether zero value is allowed on that control or not
								document.getElementById("newprodAttVal_"+i).zeroValueAllowed='Y';
							
								if(document.getElementById("autoSuggNewProdAttVal_"+i) != null){
									document.getElementById("autoSuggNewProdAttVal_"+i).disabled=true;
									document.getElementById("autoSuggNewProdAttLink_"+i).style.display='none';															
								}
							}
							if(labelValue == '0'){
								if(document.getElementById("newprodAttVal_"+i).type=='select-one')
									document.getElementById("newprodAttVal_"+i).disabled=false;
								else
									document.getElementById("newprodAttVal_"+i).readOnly=false; 
								//added zeroValueAllowed control to check whether zero value is allowed on that control or not
								document.getElementById("newprodAttVal_"+i).zeroValueAllowed='N';
							
								if(document.getElementById("autoSuggNewProdAttVal_"+i) != null){
									document.getElementById("autoSuggNewProdAttVal_"+i).disabled=false;
									document.getElementById("autoSuggNewProdAttLink_"+i).style.display='block';															
								}							
							}
							//[004.1] Start
							if(labelValue == '0'){
								document.getElementById("newprodAttMandatory_"+i).value='0';
								document.getElementById("newprodAttVal_"+i).className='inputBorder2';
								//initValidationForServiceSummary()
							}
							if(labelValue == '1'){
								document.getElementById("newprodAttMandatory_"+i).value='1';
								document.getElementById("newprodAttVal_"+i).className='inputBorder1';
								//initValidationForServiceSummary()
							}
							//[004.1] End
						}
						//Puneet adding for performance tuning
						break;
					}
					//lawkush Start
				
				if(document.getElementById("hdnProdAttVal_"+i) != null && callType == "old"){
					if(document.getElementById("hdnProdAttVal_"+i).value == destAttID[destAttIndex]){
						// document.getElementById("prodAttVal_"+i).value=labelValue.list[j].planName;
						if(labelValue == '1'){
							if(document.getElementById("prodAttVal_"+i).type=='select-one')
								document.getElementById("prodAttVal_"+i).disabled=true;
							else
								document.getElementById("prodAttVal_"+i).readOnly=true; 
							//added zeroValueAllowed control to check whether zero value is allowed on that control or not
							document.getElementById("prodAttVal_"+i).zeroValueAllowed='Y';
							
							if(document.getElementById("autoSuggProdAttVal_"+i) != null){
								document.getElementById("autoSuggProdAttVal_"+i).disabled=true;
								document.getElementById("autoSuggProdAttLink_"+i).style.display='none';															
							}							
						}
						if(labelValue == '0'){
							if(document.getElementById("prodAttVal_"+i).type=='select-one')
								document.getElementById("prodAttVal_"+i).disabled=false;
							else
								document.getElementById("prodAttVal_"+i).readOnly=false; 
							//added zeroValueAllowed control to check whether zero value is allowed on that control or not
							document.getElementById("prodAttVal_"+i).zeroValueAllowed='N';
							
							if(document.getElementById("autoSuggProdAttVal_"+i) != null){
								document.getElementById("autoSuggProdAttVal_"+i).disabled=false;
								document.getElementById("autoSuggProdAttLink_"+i).style.display='block';															
							}
						}
						//[004.1] Start
						if(document.getElementById("hdnnewProdAttVal_"+i)==null){
							//[004.1] Start
							if(labelValue == '0'){
								document.getElementById("prodAttVal_"+i).isSummaryReq='0';
								document.getElementById("prodAttVal_"+i).className='inputBorder2';
							}
							if(labelValue == '1'){ 
								document.getElementById("prodAttVal_"+i).isSummaryReq='1';
								document.getElementById("prodAttVal_"+i).className='inputBorder1';
							}
						}	
						//[004.1] End
					}
					//Puneet for performance tuning
					break;
				}
				
				//lawkush End
				}	
				destAttIndex++;	
			}
		}
	}
}
//start :CLEP Enable Disable Order Entry
function enableDisableCLEP(sectionName){
	var orderNo=callerWindowObj.document.getElementById('poNumber').value;	
	var stateid=getOrderStateforClep(orderNo,path);		
	clepState=stateid;	
	orderEntryEnableDisable('CHANGEVIEWPRODUCTCATELOG',stateid, sectionName);
}
//end CLEP
//Start:CheckRCRequiredForCLEP
//Date:11-jun-2012
//Purpose:To check on adding extra charges for clep from GUI,Only allow NRC charges
function CheckRCRequiredForCLEP(chargetype,var_counter){
	var callerWindowObj = dialogArguments;
	var order_creation_source = callerWindowObj.document.getElementById('hdnOrderCreationSourceId').value;
	if(order_creation_source==ORDER_CREATION_SOURCE && chargetype.value=="1"){	  		    
		alert('Only NRC is Allowed with this type of product.Please Contact system Admin.')
	  	//var ChargeTypeCombo = document.getElementById("ddCType"+var_counter);
	  	//var ChargeNameCombo = document.getElementById("txtCName"+var_counter);
	  	document.getElementById("ddCType"+var_counter).value=2;
	  	//ChargeNameCombo.selectedIndex=-1;
	  	return false;
	}	
}
//End:CheckRCRequiredForCLEP
//lawkush start
function selectAllCheckBoxEnableDisable(flag)
{
			if(chargelists != 0)
			{
			//Flag 1 for onload and Flag 2 for deleting the charge
				if(flag==1)
				{
		   		   var rowlen= chargelists.list.length;
		   		}
		   		else if(flag==2)
		   		{
		   			var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		   			var rowlen= chkChargeLine.length ;
		   		}
				if(document.forms[0].chkSelectChargeDetail!=undefined)
				   		{
						   var chkLength1=document.forms[0].chkSelectChargeDetail.length;
						   if(chkLength1==undefined)
				     		{
				               if(document.forms[0].chkSelectChargeDetail.disabled==true)
				                {
				                rowlen--;
				                }
							}
				        
					      else
					        {    
						         for (i =0; i<chkLength1; i++)
								   { 
								   		if(document.forms[0].chkSelectChargeDetail[i].disabled==true)
											{
											rowlen--;
											}
							       }
						    }
						}
					if(rowlen==0)
						 {
							document.getElementById('SelectAllChckCharges').checked=false;
					 		document.getElementById('SelectAllChckCharges').disabled=true;
						 }
						 else
						 {
						 document.getElementById('SelectAllChckCharges').disabled=false;
						 }
					}		  
}
//lawkush end
//[004.1] Start
function copyTextValue(sourceValue,fieldname)
{
	//alert(document.getElementById('hdnSeriveAttCounter').value)
	var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
	for(var i=1;i<=countAttributes;i++)
	{
		if(fieldname.name.indexOf("newprodAttVal_")!=-1)
		{
			if(document.getElementById("hdnnewProdAttVal_"+i).value==2109)
			{
				if((document.getElementById("newprodAttVal_"+i).value != "1") && (document.getElementById("newprodAttVal_"+i).value!="19") && (document.getElementById("newprodAttVal_"+i).value!="0"))
				{
					document.getElementById("newprodAttVal_18").value=document.getElementById("newprodAttVal_4").value;
				}
			}
		}
		else
		{
			if(document.getElementById("hdnProdAttVal_"+i).value==2109)
			{
				if((document.getElementById("prodAttVal_"+i).value != "1") && (document.getElementById("prodAttVal_"+i).value!="19") && (document.getElementById("prodAttVal_"+i).value!="0"))
				{
					document.getElementById("prodAttVal_18").value=document.getElementById("prodAttVal_4").value;
				}
			}
		}
	}
}
//[004.1] End
function popUpServiceSummary(attId,counter,popUpId)
{
	/*if(popUpId=='popUpForArborLSILookup')
	{
		popUpForArborLSILookup(attId,counter)
	}*/
	if(popUpId=='popLSINoForMBIC')
	{
		popLSINoForMBIC(attId,counter)
	}
	else if(popUpId=='popUpForVCSBridgeLSILookup')
	{
		popUpForVCSBridgeLSILookup(attId,counter)
	}
}
function popUpForArborLSILookup()
{
	var callerWindowObj = dialogArguments;
	var ib2bOrderNo = callerWindowObj.document.getElementById('poNumber').value;
	var configId=document.getElementById("hdnConfigValue").value;
	var path = path + "/NewOrderAction.do?method=arborLSILookup&fxChargeRedirectionTypeCumulative="+fxChargeRedirectionTypeCumulative;	
	path=path+"&logicalSIno="+logicalSIno+"&orderNo="+ib2bOrderNo+"&configId="+configId+"&serviceDetailID="+serviceDetailID+"&fxChargeRedirectionType="+fxChargeRedirectionType;
	window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:700px");
}
function popLSINoForMBIC(attMasterId,Counter)
{
	return false;
}
function popUpForVCSBridgeLSILookup(attMasterId,productCounter)
{   
	path = path + "/NewOrderAction.do?method=fetchVCS_BridgeLSI&attMasterId="+attMasterId+"&ib2bOrderNo="+changeOrderNo+"&servicesID="+servicesID+"&logicalSIno="+logicalSIno+"&productCounter="+productCounter+"&serviceProductID="+serviceProductID+"&serviceDetailID="+serviceDetailID;		
	window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:530px");
}
//------------------[ Start:ComponentStartDateLogic and ComponentEndDateLogic ]-----------------------------------------------------------------
// Puneet - commented for performance tuning
 function fillComponentStartEndDateLogic(index){
	try
	{											
		//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
		var startDateLogicList = jsonrpc.processes.fillComponentStartDateLogic();
		var cboStartDateLogic=document.getElementById('txtCompStartDateLogic'+index);		
	    for (k = 1; k <  startDateLogicList.list.length; k++)
	    {
	       cboStartDateLogic.remove(1);
	    } 	    
	    
	    for(k=0;k<startDateLogicList.list.length;k++)
	    {	    	
	    	var option = document.createElement("option");
	   		option.text = startDateLogicList.list[k].componentStartDateLogicDesc;
			option.value = startDateLogicList.list[k].componentStartDateLogicId;
			option.title = startDateLogicList.list[k].componentStartDateLogicDesc;		

			try 
			{				
				cboStartDateLogic.add(option, null); //Standard
			}
			catch(error) 
			{				
				cboStartDateLogic.add(option); // IE only
			}
	    }
	    
	    var endDateLogicList = jsonrpc.processes.fillComponentEndDateLogic();
		var cboEndDateLogic=document.getElementById('txtCompEndDateLogic'+index);		
	    for (k = 1; k <  endDateLogicList.list.length; k++)
	    {
	       cboEndDateLogic.remove(1);
	    } 	    
	    for(k=0;k<endDateLogicList.list.length;k++)
	    {	    	
	    	var option = document.createElement("option");
	   		option.text = endDateLogicList.list[k].componentEndDateLogicDesc;
			option.value = endDateLogicList.list[k].componentEndDateLogicId;
			option.title = endDateLogicList.list[k].componentEndDateLogicDesc;					
			try 
			{
				cboEndDateLogic.add(option, null); //Standard
			}
			catch(error) 
			{
				cboEndDateLogic.add(option); // IE only
			}
	    }
    }
    catch(e)
	{
		alert("Error :"+e);		
	}
}
function changeCompEndDays(index){
	if(document.getElementById('txtCompEndDateLogic'+index).value=='TD'){
	  document.getElementById('txtEndDays'+index).value=0;
	  document.getElementById('txtEndMonth'+index).value=0;		  		
	}
}

	
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
        //<!-- End:Added above code for Charge Table freeze -->
        function reposHeadComponent(e) {
            var h = document.getElementById('headscroll2');
            h.scrollLeft = e.scrollLeft;
            var f = document.getElementById('divfrozen2');
            f.scrollTop = e.scrollTop;
        }
        function reposHorizontalComponent(e) {
            var h = document.getElementById('headscroll2');
            var c = document.getElementById('contentscroll2');
            h.scrollLeft = e.scrollLeft;
            c.scrollLeft = e.scrollLeft;
            
        }
function reposVerticalComponent(e) {
	var h = document.getElementById('divfrozen2');
    var c = document.getElementById('contentscroll2');
    h.scrollTop = e.scrollTop;
    c.scrollTop = e.scrollTop;          
}
        //<!-- End:Added above code for Component Table freeze -->
//=============================[ Draw Charge Table-15-02-2013 ]=====================================
function drawChangeViewChargeTable(){
	//------------Set Page Size for Charge Line Table Paging Start --------------
		pageRecords=PAGE_SIZE_CHARGE_LINE;
	//------------Set Page Size for Charge Line Table Paging End ----------------
	 var contractFreqValue="";
	    var cFrequency="";
	    /* Puneet commented for performance tuning
	    var myChargeTable = document.getElementById('tableCharges');
   		var iCountRows = myChargeTable.rows.length;
   		for (j = 0; j <  iCountRows; j++)
   		{
       		myChargeTable.deleteRow(0);
   		}   */
   		//-------------------[ Find PageNumber,StartEndIndex,PageRecords ]---------------------------------------
   			document.getElementById('txtPageNumber').value= pageNumber;
   			sync(); 
   		//-------------------[ Find PageNumber,StartEndIndex,PageRecords ]---------------------------------------
		var jsChargeData = {
			startIndex:startRecordId,
			endIndex:endRecordId,
			sortBycolumn:sortByColumn,
			sortByOrder:sortByOrder,
			pageRecords:pageRecords,
			serviceProductID:serviceProductID,
			changeTypeId:hdnChangeTypeId,
			serviceId:servicesID
		};
		//jsonrpc = new JSONRpcClient(path + "" + "/JSON-RPC");
		var chargelists=0;
	    chargelists = jsonrpc.processes.populateChargeDetailsForChangeOrders(jsChargeData);
	    iTotalLength=chargelists.list.length;
	    //iTotalLength=0;
		if(iTotalLength !=0){
			iNoOfPages = chargelists.list[0].maxPageNo;
		}else{     
        	iNoOfPages=1;
		}
		
		var chargeTableDiv =  document.getElementById('contentscroll');
	    
		document.getElementById('txtMaxPageNo').value=iNoOfPages;	
		if(iTotalLength > 1){
			document.getElementById('SelectAllChckCharges').disabled=false;
		}else{
			document.getElementById('SelectAllChckCharges').disabled=true;
		}
	    if(iTotalLength > 0){
	    	chargeTableDiv.innerHTML = "";
	    	chargeSumCounter = 0;
			document.getElementById('viewChargeLineTabNavigatorId').style.display='block';			
			var iChargeIndexCounter = 0;
			var counter = 0;
			var chargeListTable = "<table border='0' cellpadding='0' cellspacing='0' class='content' id='tableCharges'>";
	    	var tableEnd = '</table>';
	    	var tableRowStart= "<tr>";
	    	var tableRowEnd = "</tr>";
	    	var tableDataStart;
	    	var tableDataEnd = "</td>";
	    	var chargeTypeOption = getChargeType(chargelists.list[iChargeIndexCounter].chargeType);
	    	var chargeTypeSelectedValue = new Array();
	    	var chargeFqSelectedValue = new Array();
	    	var chargeSDSelectedValue = new Array();
			var chargeEDSelectedValue = new Array();
	    	var ctrlArry2 = new Array();	  
	    	var linkChargeArryCounter = -1;
	    	var controlIndex = 0;
	    	var hdnChargeCount = document.getElementById('hdnChargeCount');
	    	for (iChargeIndexCounter = 0 ; iChargeIndexCounter < iTotalLength; iChargeIndexCounter++){		
	    		counter++;
				/*var counter = parseInt(document.getElementById('hdnChargeCount').value)+1;
				document.getElementById('hdnChargeCount').value = counter;*/
				var str;
				//counter = document.getElementById('tableCharges').rows.length;
				chargeSumCounter++;
				//var tblRow=document.getElementById('tableCharges').insertRow();
				chargeListTable = chargeListTable + tableRowStart;
				var created_serviceid=chargelists.list[iChargeIndexCounter].created_serviceid;
	         	var editableString = '';
	         	tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col1 toprow'>";
				if(created_serviceid==servicesID){
					/*var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					tblcol.className="innerdata col1 toprow";*/
					str ="<input name='chkSelectChargeDetail' id='chkSelectChargeDetail' type='checkbox' value='"+counter+"'  onclick='allChargesCheckNone()' >";
					str =str + "<input name='isAddedInCurrentService' id='isAddedInCurrentService"+counter+"' type='hidden' value='1'>";
					/*CellContents = str;
					tblcol.innerHTML = CellContents;*/
					editableString='editable=allow';
				}else{
					/*var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					tblcol.className="innerdata col1 toprow";*/
					str ="<input name='chkSelectChargeDetail' id='chkSelectChargeDetail' type='checkbox' value='"+counter+"' disabled='disabled'>";
					str =str + "<input name='isAddedInCurrentService' id='isAddedInCurrentService"+counter+"' type='hidden' value='0'>";
					/*CellContents = str;
					tblcol.innerHTML = CellContents;*/
				}
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col2'>";
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col2";*/
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' name='pkChargeId' id = 'pkChargeId"+counter+"' style='width:175px;' isRequired='0' Disp='' class='inputBorder' value='"+chargelists.list[iChargeIndexCounter].chargeInfoID+"' readonly='readonly'>";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col2'>";
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col2";*/
				str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px' name='ddCType' id='ddCType"+counter+"' isRequired='0' Disp='' class='dropdownMargin' onchange='javascript:CheckRCRequiredForCLEP(this,"+counter+");setBlankChargeName("+counter+");changeFrequency("+counter+");calculateFreqAmount("+counter+");fillEndDateLogic("+counter+");chkDifferentialChargeForChargeType("+counter+");' " + editableString + ">";
				str = str + chargeTypeOption + "</select>"; 
				str=str+"<input type='hidden' name='hdnChargeId' id = 'hdnChargeId"+counter+"' value='"+chargelists.list[iChargeIndexCounter].chargeInfoID+"'>";
				str=str+"<input type='hidden' name='hdnPoNoOfCharge' id = 'hdnPoNoOfCharge"+counter+"' value='"+chargelists.list[iChargeIndexCounter].poNoOfCharge+"'>";
				//start clep
				str=str+"<input type='hidden' name='hdnChargeCreationSource' id = 'hdnChargeCreationSource"+counter+"' value='"+chargelists.list[iChargeIndexCounter].charge_creation_source+"'>";				
				if(chargelists.list[iChargeIndexCounter].charge_creation_source=="2"){
					clep_arrEnableDisableFlags[iChargeIndexCounter]='DISABLE';
				}else{
					clep_arrEnableDisableFlags[iChargeIndexCounter]='ENABLE';
				}
				//End CLEP
				chargeTypeSelectedValue[iChargeIndexCounter] =  chargelists.list[iChargeIndexCounter].chargeType;
				//var ChargeType=chargelists.list[iChargeIndexCounter].chargeType;
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				/*srvDetailId=document.getElementById("hdnServiceDetailID").value;
				if(document.getElementById('hdnHardwareInfo').value==1){				
					var local_typeCombo = document.getElementById("ddCType"+counter);
					if(document.getElementById('txtBillingformat').value==2){					
						fillChargeTypeForHardware(path,srvDetailId,1,local_typeCombo);						
					}else if(document.getElementById('txtBillingformat').value==3){		
						fillChargeTypeForHardware(path,srvDetailId,2,local_typeCombo);			
					}		
				}else{	
					fillChargeType(counter,srvDetailId);
				}
				document.getElementById('ddCType' + counter).value = chargelists.list[iChargeIndexCounter].chargeType;*/
				
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";

				tblcol.className="innerdata col3";	*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
				str="<input type='text' readonly='true' value='"+chargelists.list[iChargeIndexCounter].chargeNameVal+"' onmouseover='getTip(value)' counterval='"+ counter +"' onmouseout='UnTip()' style='width:160px' isRequired='0'  name='chargeName' ctrltype='dropdown' retval='AUTOSUGGESTCHARGENAME' id='chargeName" + counter + "' class='dropdownMargin'" + editableString + "><a name='chargeNameLink' id='chargeNameLink" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownChargeName(" + counter + ")'>Show</a>";
				//str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px' name='txtCName' id='txtCName"+counter+"' isRequired='0' Disp='' onchange='fillAnnotationField("+counter+");getTaxRate("+counter+")' class='dropdownMargin' " + editableString + " ><option value='-1'>Select Name</option></select>";
				str=str+"<input type='hidden' name='txtCName' id='txtCName"+counter+"' isrequired='0' value='"+chargelists.list[iChargeIndexCounter].chargeName+"'>";
				str=str+"<input type='hidden' name='hdnChargeName' value='"+chargelists.list[iChargeIndexCounter].chargeName+"'>";
				//[003] START
				str=str+"<input type='hidden' name='hdnFxChargeID' value='"+chargelists.list[iChargeIndexCounter].fxChargeId+"' id='hdnFxChargeID"+counter+"'>";
				//[003] END
				/*CellContents = str;
				tblcol.innerHTML = CellContents;
				fillChargeNames(counter,chargelists.list[iChargeIndexCounter].chargeType,chargelists.list[iChargeIndexCounter].chargeNameID);*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col3";*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
				//Start [013]
				if(document.getElementById('hdnHardwareInfo').value==1){
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' class='inputBorder1' name='txtCAnnotation' isRequired='0' Disp='' id='txtCAnnotation"+counter+"' maxlength='240' value='"+chargelists.list[iChargeIndexCounter].chargeAnnotation+"' " + editableString +" onblur=\"if(this.value.length > 0){if(CheckMinLength(this)){return ValidateTextField(this,path,'Annotation')}}\" >";
				}else{
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' class='inputBorder1' name='txtCAnnotation' isRequired='0' Disp='' id='txtCAnnotation"+counter+"' maxlength='340' value='"+chargelists.list[iChargeIndexCounter].chargeAnnotation+"' " + editableString +" onblur=\"if(this.value.length > 0){if(CheckMinLength(this)){return ValidateTextField(this,path,'Annotation')}}\" >";
				}
				//End [013]
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col5";*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col5'>";
				str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' value='" + chargelists.list[iChargeIndexCounter].taxRate + "' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtTaxRate' id='txtTaxRate"+counter+"' readonly='true' "+ editableString + " >";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				//below condition code is commented because the function "getTaxRate" is generic 
				//var serviceType=document.getElementById("txtHdnServiceName").value.toUpperCase();
				//if(serviceType.indexOf("SERVICE")!=-1)
				//{
					//Puneet commenting the function call as tax rate is already populated
					//getTaxRate(counter);
				//}		
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col6";*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col6'>";
				if(created_serviceid==servicesID)
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' class='inputBorder1' name='txtCPeriod' isRequired='0' Disp='' id='txtCPeriod"+counter+"' value='"+chargelists.list[iChargeIndexCounter].chargePeriod+"' onblur='if(this.value.length > 0){if(checkdigits(this)){checkContractPeriod("+counter+");fillFrequency_Charge("+counter+" ,0);}else{return false;}}' "+ editableString + ">";
	            else
	            	str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' class='inputBorder1' name='txtCPeriod' isRequired='0' Disp='' id='txtCPeriod"+counter+"' value='"+chargelists.list[iChargeIndexCounter].chargePeriod+"'"+ editableString + ">";
	           
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col7'>";
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col7";*/
				//str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder1' maxlength='15' name='txtCTAmt' id='txtCTAmt"+counter+"' value = '"+chargelists.list[iChargeIndexCounter].chargeAmount+"' oldvalue='' onkeyup='if(this.value.length > 0){if(checknumber_max2decimal_charges_section(this,0)){this.oldvalue=this.value;calculateFreqAmount("+counter+");calculateTotalLineAmount(this.value,"+chargeSumCounter+");}else{this.value=this.oldvalue;calculateFreqAmount("+counter+");calculateTotalLineAmount(this.value,"+chargeSumCounter+");return false;}}else{calculateTotalLineAmount(this.value,"+chargeSumCounter+");return false;}' onblur='if(this.value.length > 0){if(checknumber_max2decimal_charges_section(this,1)){this.oldvalue=this.value;calculateFreqAmount("+counter+");calculateTotalLineAmount(this.value,"+chargeSumCounter+");}else{this.value=this.oldvalue;calculateFreqAmount("+counter+");calculateTotalLineAmount(this.value,"+chargeSumCounter+");return false;}}else{calculateTotalLineAmount(this.value,"+chargeSumCounter+");return false;}' "+ editableString + " > ";
				str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder1' maxlength='15' name='txtCTAmt' id='txtCTAmt"+counter+"' value = '"+chargelists.list[iChargeIndexCounter].chargeAmount+"' oldvalue='"+chargelists.list[iChargeIndexCounter].chargeAmount+"' chargeSumCounterVal='" + chargeSumCounter + "' counterVal='" + counter + "'" + editableString + " > ";
				str=str+"<input type='hidden' name='hdnCTAmt' id='hdnCTAmt"+chargeSumCounter+"' value = '"+chargelists.list[iChargeIndexCounter].chargeAmount+"' > ";
				str=str+"<input type='hidden' name='hdnCTAmtDisconnectType' id='hdnCTAmtDisconnectType"+chargeSumCounter+"' value = '"+chargelists.list[iChargeIndexCounter].isdisconnected+"' > ";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col8";	*/
				var txtCFrequencyDis = true;
				if(chargelists.list[iChargeIndexCounter].chargeType == 1){
					txtCFrequencyDis = false;
				}
				var frequencyOption = getChargeFrequencyChange(chargelists.list[iChargeIndexCounter].chargePeriod);
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col8'>";
				str = "<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px' name='txtCFrequency' id='txtCFrequency"+counter+"' isRequired='0' Disp='' class='dropdownMargin' onchange='calculateFreqAmount("+counter+");chkDifferentialChargeForFrequency("+counter+");' " + editableString +" >";
				str = str + frequencyOption + "</select>";
				chargeFqSelectedValue[iChargeIndexCounter]=chargelists.list[iChargeIndexCounter].chargeFrequency;
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;	
				/*fillFrequency_Charge(counter,0);
				document.getElementById('txtCFrequency' + counter).value  = chargelists.list[iChargeIndexCounter].chargeFrequency;
				if(document.getElementById('ddCType' + counter).value==1){
					document.getElementById('txtCFrequency' + counter).disabled=false;
				}*/
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col9";*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col9'>";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' class='inputDisabled' isRequired='0' Disp='' name='txtCFreqAmt' id='txtCFreqAmt"+counter+"' value ='"+chargelists.list[iChargeIndexCounter].chargeFrequencyAmt+"'   onblur='if(this.value.length > 0){return checknumber(this)}' readonly='readonly' " + editableString + " > ";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col4";	*/		
				chargeSDSelectedValue[iChargeIndexCounter] = chargelists.list[iChargeIndexCounter].startDateLogic;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col4'>";
				str="<table><tr><td><select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:125px' name='txtCStartDate' id='txtCStartDate"+counter+"' isRequired='0' Disp='' class='dropdownMargin' " + editableString + " ><option value='-1'>Select Start Date Logic</option><option value='BTD'>Billing Trigger</option><option value='TD'>Till Disconnection</option></select>";
				str=str+"<input type='hidden' name='txtCStartDate' value='"+chargelists.list[iChargeIndexCounter].startDateLogic+"'></td>";
				str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' class='inputBorder' isRequired='0' Disp='' name='txtStartDays' value ='"+chargelists.list[iChargeIndexCounter].startDateDays+"'  id='txtStartDays"+counter+"' " +editableString + "></td>";
				str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' isRequired='0' Disp='' class='inputBorder' name='txtStartMonth' id='txtStartMonth"+counter+"' value='"+chargelists.list[iChargeIndexCounter].startDateMonth+"'  onblur='if(this.value.length > 0){return checknumber(this)}'><input type='hidden' name='hdnstartdate' value='"+chargelists.list[iChargeIndexCounter].startDateMonth+"' " + editableString + "></td></tr></table>";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col4";*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col4'>";
				var endDateOptions = "<option value='-1'>Select End Date Logic</option>";
				if(chargelists.list[iChargeIndexCounter].chargeType == 2){
					endDateOptions = getEndDateNRCOption();
				}else{
					endDateOptions = getEndDateRCOption();
				}
				chargeEDSelectedValue[iChargeIndexCounter] = chargelists.list[iChargeIndexCounter].endDateLogic;
				str="<table><tr><td><select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:125px' name='txtCEndDate' id='txtCEndDate"+counter+"' isRequired='0' Disp='' class='dropdownMargin' " + editableString + " onchange='changeEndDays("+counter+");chkDifferentialChargeForEndDateLogic("+counter+");' >";
				str=str + endDateOptions + "</select>";
				str=str+"<input type='hidden' name='txtCEndDate' value='"+chargelists.list[iChargeIndexCounter].endDateLogic+"'></td>";
				str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;'  isRequired='0' Disp='' class='inputBorder' name='txtEndDays' value='"+chargelists.list[iChargeIndexCounter].endDateDays+"' id='txtEndDays"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'  "+ editableString  + "></td>";
				str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' isRequired='0' Disp='' class='inputBorder' name='txtEndMonth' value='"+chargelists.list[iChargeIndexCounter].endDateMonth+"' id='txtEndMonth"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}' " + editableString + " ></td></tr></table>";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
			    
			  //  fillEndDateLogic(counter);
				var annualCharge='';
				if(chargelists.list[iChargeIndexCounter].chargeType==1){
					var annualCharge = ((chargelists.list[iChargeIndexCounter].chargeAmount)*12/(chargelists.list[iChargeIndexCounter].chargePeriod));
				}	
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col3";*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' class='inputDisabled' name='txtAnnualCharge' id='txtAnnualCharge"+counter+"' value ='"+annualCharge+"'   onblur='if(this.value.length > 0){return checknumber(this)}' readonly='readonly'> ";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col3";*/		
				var lineItemLabel = "&nbsp;";
				if(!(chargelists.list[iChargeIndexCounter].lineItemType=='NA' || chargelists.list[iChargeIndexCounter].lineItemType == null))
					lineItemLabel = chargelists.list[iChargeIndexCounter].lineItemType;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
				//str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' name='cmbLineItem' style='display:none'  id='cmbLineItem"+counter+"' isRequired='0' Disp=''  class='dropdownMargin'><option value='-1'>Select Line Item</option></select>";
				//str=str+"<input type='hidden' name='hdnChargeName' value=''>";
				str="<label id='lineItemLbl"+counter+"'> " + lineItemLabel + " </label> ";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;
				fillLineItem(counter);
				document.getElementById('cmbLineItem' + counter).value  = chargelists.list[iChargeIndexCounter].lineItemId;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col3";	*/	
				var subLineItemLabel = "&nbsp;";
				if(!(chargelists.list[iChargeIndexCounter].subLineItemType=='NA' || chargelists.list[iChargeIndexCounter].subLineItemType == null))
					subLineItemLabel = chargelists.list[iChargeIndexCounter].subLineItemType;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
				//str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' name='cmbSubLineItem' style='display:none'  id='cmbSubLineItem"+counter+"' isRequired='0' Disp=''  class='dropdownMargin'><option value='-1'>Select Sub Line Item</option></select>";
				//str=str+"<input type='hidden' name='hdnChargeName' value=''>";
				str="<label id='subLineItemLbl"+counter+"'> "+ subLineItemLabel +"</label> ";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;
				fillSubLineItem(counter);
				document.getElementById('cmbSubLineItem' + counter).value  = chargelists.list[iChargeIndexCounter].sublineItemId;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				//getLineNSublineItemLbl(counter);
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col3";*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
				str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' maxlength='300' class='inputBorder' " + editableString + " name='txtRemarks' id='txtRemarks"+counter+"' value='"+chargelists.list[iChargeIndexCounter].chargeRemarks+"' onBlur=\"if( trim(this.value).length>0){return ValidateTextField(this,path,'Remarks')};\">";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col3";*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
				str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtChargeStartDate' id='txtChargeStartDate"+counter+"' value='"+chargelists.list[iChargeIndexCounter].startDate+"' readonly='readonly'>";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col3";*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
				str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtChargeEndDate' id='txtChargeEndDate"+counter+"' value='"+chargelists.list[iChargeIndexCounter].endDate+"'  readonly='readonly'>";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				var excludecharge=chargelists.list[iChargeIndexCounter].excludecheck;
				if(created_serviceid==servicesID){
					  if(excludecharge==1){
							/*var tblcol=tblRow.insertCell();
						    tblcol.align = "left";
							tblcol.vAlign="top";
							tblcol.className="innerdata col1";*/
							str="<input type='checkbox'  isRequired='0' Disp='' class='inputBorder' name='chkexcludeCharges' id='chkexcludeCharges"+counter+"' checked='true'>";
							/*CellContents = str;
							tblcol.innerHTML = CellContents;*/
				     }else{
							/*var tblcol=tblRow.insertCell();
						    tblcol.align = "left";
							tblcol.vAlign="top";
							tblcol.className="innerdata col1";*/
							str="<input type='checkbox'  isRequired='0' Disp='' class='inputBorder' name='chkexcludeCharges' id='chkexcludeCharges"+counter+"'>";
							/*CellContents = str;
							tblcol.innerHTML = CellContents;*/
						}
					 tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col1'>";
					 chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				}else{
					if(excludecharge==1){
						/*var tblcol=tblRow.insertCell();
						tblcol.align = "left";
						tblcol.vAlign="top";
						tblcol.className="innerdata col1";*/
						str="<input type='checkbox'  isRequired='0' Disp='' class='inputBorder' name='chkexcludeCharges' id='chkexcludeCharges"+counter+"' checked='true' disabled='disabled'>";
						/*CellContents = str;
						tblcol.innerHTML = CellContents;*/
					}else{
						/*var tblcol=tblRow.insertCell();
						tblcol.align = "left";
						tblcol.vAlign="top";
						tblcol.className="innerdata col1";*/
						str="<input type='checkbox'  isRequired='0' Disp='' class='inputBorder' name='chkexcludeCharges' id='chkexcludeCharges"+counter+"' disabled='disabled'>";
						/*CellContents = str;
						tblcol.innerHTML = CellContents;*/
					}
					tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col1'>";
					chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
			    }
			    //Billing Efficiency in charge Details
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col13";*/
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:90px;' value='"+chargelists.list[iChargeIndexCounter].ldDateClause+"'  isRequired='0' Disp='' maxlength='10' class='inputBorder' name='txtLDDateClause' id='txtLDDateClause"+counter+"'  readonly='true'><img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.txtLDDateClause" + counter + ");\" style='vertical-align: bottom'/></font>";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col13'>";
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col11";*/
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:50px;' value='"+chargelists.list[iChargeIndexCounter].delayedTimeInDayes+"'  isRequired='0' Disp='' maxlength='2' class='inputBorder' name='txtDelayedTimeInDays' id='txtDelayedTimeInDays"+counter+"'  onblur='if(this.value.length > 0){if(checkdigitsValidation(this)){checkUperLimit(this.value,this.id,30,"+counter+");}else {return false;}}'>";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col11'>";
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col13";*/
				str = "<table><tr><td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:50px;' value='"+chargelists.list[iChargeIndexCounter].ldPercentage+"'  isRequired='0' Disp='' maxlength='3' class='inputBorder' name='txtLDPercentage' id='txtLDPercentage"+counter+"' onblur='if(this.value.length > 0){if(checkdigitsValidation(this)){checkUperLimit(this.value,this.id,100,"+counter+");}else {return false;}}'></td>";
				str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:50px;' value='"+chargelists.list[iChargeIndexCounter].maxPercentage+"'  isRequired='0' Disp='' maxlength='3' class='inputBorder' name='txtMaxPenaltyPercentage' id='txtMaxPenaltyPercentage"+counter+"' onblur='if(this.value.length > 0){if(checkdigitsValidation(this)){checkUperLimit(this.value,this.id,100,"+counter+");}else {return false;}}'></td></tr></table> ";
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col13'>";
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				//End Billing Efficiency
				//[012] Start
				/*
			    if(document.getElementById('hdnHardwareInfo').value==1)
				{		
				//[012] End
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					tblcol.className="innerdata col12";*/
					str = "<table><tr><td><input onmouseover='getTip(value)' onmouseout='UnTip()' value='" + chargelists.list[iChargeIndexCounter].paymentTerm1 + "' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtPayment1' id='txtPayment1"+counter+"' onblur='if(this.value.length > 0){checkdigits(this)}' ></td>";
					str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' value='" + chargelists.list[iChargeIndexCounter].paymentTerm2 + "' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtPayment2' id='txtPayment2"+counter+"' onblur='if(this.value.length > 0){checkdigits(this)}'   ></td> ";
					str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' value='" + chargelists.list[iChargeIndexCounter].paymentTerm3 + "' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtPayment3' id='txtPayment3"+counter+"' onblur='if(this.value.length > 0){checkdigits(this)}'  ></td> ";
					str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' value='" + chargelists.list[iChargeIndexCounter].paymentTerm4 + "' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtPayment4' id='txtPayment4"+counter+"' onblur='if(this.value.length > 0){checkdigits(this)}'  ></td></tr></table> ";
					/*CellContents = str;
					tblcol.innerHTML = CellContents;*/
				//[012] Start
				/*
					
				}else{
					var tblcol=tblRow.insertCell();
					tblcol.align = "left";
					tblcol.vAlign="top";
					tblcol.className="innerdata col12";
					str = "<table><tr><td><input onmouseover='getTip(value)' onmouseout='UnTip()' value='" + chargelists.list[iChargeIndexCounter].paymentTerm1 + "' type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtPayment1' id='txtPayment1"+counter+"' readonly='readonly'  ></td>";
					str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' value='" + chargelists.list[iChargeIndexCounter].paymentTerm2 + "'type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtPayment2' id='txtPayment2"+counter+"' readonly='readonly' ></td> ";
					str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' value='" + chargelists.list[iChargeIndexCounter].paymentTerm3 + "'type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtPayment3' id='txtPayment3"+counter+"' readonly='readonly' ></td> ";
					str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' value='" + chargelists.list[iChargeIndexCounter].paymentTerm4 + "'type='text' style='width:175px' isRequired='0' Disp='' class='inputBorder' name='txtPayment4' id='txtPayment4"+counter+"' readonly='readonly' ></td></tr></table> ";
					CellContents = str;
					tblcol.innerHTML = CellContents;
				}*/
			    tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col12'>";
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				/*document.getElementById('txtPayment1' + counter).value=chargelists.list[iChargeIndexCounter].paymentTerm1;
				document.getElementById('txtPayment2' + counter).value=chargelists.list[iChargeIndexCounter].paymentTerm2;
				document.getElementById('txtPayment3' + counter).value=chargelists.list[iChargeIndexCounter].paymentTerm3;
				document.getElementById('txtPayment4' + counter).value=chargelists.list[iChargeIndexCounter].paymentTerm4;*/
				
				//Vijay. add condition for demo-regularize				
				if(((hdnChangeTypeId==2 && (changeSubTypeID==8 || changeSubTypeID==9 || changeSubTypeID==10)) 
						|| hdnChangeTypeId==5 || (hdnChangeTypeId==4 && changeSubTypeID==12 ) )){
					if(created_serviceid==servicesID 
							|| (chargelists.list[iChargeIndexCounter].isdisconnected==1 
											&& chargelists.list[iChargeIndexCounter].disconnected_orderno!=neworderno)||chargelists.list[iChargeIndexCounter].isRedisconnected==1){
								
									
									/*var tblcol=tblRow.insertCell();
									tblcol.align = "left";
									tblcol.vAlign="top";
									tblcol.className="innerdata col3";*/
									//str="<input type='text' isRequired='0' Disp='' maxlength='4' class='inputBorder'>";
									str="&nbsp;&nbsp;";
									/*CellContents = str;
									tblcol.innerHTML = CellContents;*/
									//[0002]
									tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
									chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
									
									}else{
										
										var isDisconnectionChargeButtonCreated=0;
										/*var tblcol=tblRow.insertCell();
										tblcol.align = "left";
										tblcol.vAlign="top";
										tblcol.className="innerdata col3";*/
										str="&nbsp;&nbsp;";		
										var end_date_logic="";
										
										if(chargelists.list[iChargeIndexCounter].endDateLogic=='BTD'){
											end_date_logic=1;
										}else if(chargelists.list[iChargeIndexCounter].endDateLogic=='TD'){
											end_date_logic=2;
										}else if(chargelists.list[iChargeIndexCounter].endDateLogic=='OT'){  //[0002] added one more elseif 
											end_date_logic=3;
										}else {
											end_date_logic=0;
										}						
										if(chargelists.list[iChargeIndexCounter].isdisconnected==1){
											//str= "<div class='searchBg1' ><a href='#' id='disconnchargeButton"+counter+"' onClick='alreadyDisconnectCharge("+chargelists.list[iChargeIndexCounter].chargeInfoID+","+chargelists.list[iChargeIndexCounter].disconnection_type+","+counter+","+chargelists.list[iChargeIndexCounter].chargeType+","+chargelists.list[iChargeIndexCounter].rcActiveDateCrossed+","+chargelists.list[iChargeIndexCounter].nrcActiveDateCrossed+","+end_date_logic+","+chargelists.list[iChargeIndexCounter].rcInactiveDateCrossed+") return false;' >...</a></div>";					
											str="<input type='button' id='disconnchargeButton"+counter+"' name='disconnchargeButton"+counter+"' title='Click to disconnect/revert charge'   value='...' onClick='alreadyDisconnectCharge("+chargelists.list[iChargeIndexCounter].chargeInfoID+","+chargelists.list[iChargeIndexCounter].disconnection_type+","+counter+","+chargelists.list[iChargeIndexCounter].chargeType+","+chargelists.list[iChargeIndexCounter].rcActiveDateCrossed+","+chargelists.list[iChargeIndexCounter].nrcActiveDateCrossed+","+end_date_logic+","+chargelists.list[iChargeIndexCounter].rcInactiveDateCrossed+")'>";
											isDisconnectionChargeButtonCreated=1;
										}else if(chargelists.list[iChargeIndexCounter].chargeType == 1  && chargelists.list[iChargeIndexCounter].rcActiveDateCrossed == '1'){// change by Gunjan as inactive charges will be marked as close
											//str= "<div class='searchBg1'><a href='#' id='disconnchargeButton"+counter+"' onClick='disconnectCharge("+chargelists.list[iChargeIndexCounter].chargeInfoID+",1,"+counter+","+chargelists.list[iChargeIndexCounter].chargeType+","+chargelists.list[iChargeIndexCounter].rcActiveDateCrossed+","+chargelists.list[iChargeIndexCounter].nrcActiveDateCrossed+","+end_date_logic+","+chargelists.list[iChargeIndexCounter].rcInactiveDateCrossed+")'>...</a></div>";						
											str="<input type='button' id='disconnchargeButton"+counter+"' name='disconnchargeButton"+counter+"' title='Click to disconnect/revert charge' value='...' onClick='disconnectCharge("+chargelists.list[iChargeIndexCounter].chargeInfoID+",2,"+counter+","+chargelists.list[iChargeIndexCounter].chargeType+","+chargelists.list[iChargeIndexCounter].rcActiveDateCrossed+","+chargelists.list[iChargeIndexCounter].nrcActiveDateCrossed+","+end_date_logic+","+chargelists.list[iChargeIndexCounter].rcInactiveDateCrossed+",1)'>";
											isDisconnectionChargeButtonCreated=1;
										}else if (chargelists.list[iChargeIndexCounter].chargeType == 2  && chargelists.list[iChargeIndexCounter].nrcActiveDateCrossed == '1'){
											//str= "<div class='searchBg1'><a href='#' id='disconnchargeButton"+counter+"' onClick='disconnectCharge("+chargelists.list[iChargeIndexCounter].chargeInfoID+",1,"+counter+","+chargelists.list[iChargeIndexCounter].chargeType+","+chargelists.list[iChargeIndexCounter].rcActiveDateCrossed+","+chargelists.list[iChargeIndexCounter].nrcActiveDateCrossed+","+end_date_logic+","+chargelists.list[iChargeIndexCounter].rcInactiveDateCrossed+")'>...</a></div>";						
											str="<input type='button' id='disconnchargeButton"+counter+"' name='disconnchargeButton"+counter+"' title='Click to disconnect/revert charge' value='...' onClick='disconnectCharge("+chargelists.list[iChargeIndexCounter].chargeInfoID+",1,"+counter+","+chargelists.list[iChargeIndexCounter].chargeType+","+chargelists.list[iChargeIndexCounter].rcActiveDateCrossed+","+chargelists.list[iChargeIndexCounter].nrcActiveDateCrossed+","+end_date_logic+","+chargelists.list[iChargeIndexCounter].rcInactiveDateCrossed+",1)'>";
											isDisconnectionChargeButtonCreated=1;
										}else if(chargelists.list[iChargeIndexCounter].chargeType == 1  && chargelists.list[iChargeIndexCounter].rcActiveDateCrossed == '0'){
											if(chargelists.list[iChargeIndexCounter].endDateLogic == 'TD'){
												//str= "<div class='searchBg1'><a href='#' id='disconnchargeButton"+counter+"' onClick='disconnectCharge("+chargelists.list[iChargeIndexCounter].chargeInfoID+",2,"+counter+","+chargelists.list[iChargeIndexCounter].chargeType+","+chargelists.list[iChargeIndexCounter].rcActiveDateCrossed+","+chargelists.list[iChargeIndexCounter].nrcActiveDateCrossed+","+end_date_logic+","+chargelists.list[iChargeIndexCounter].rcInactiveDateCrossed+")'>...</a></div>";							
												str="<input type='button' id='disconnchargeButton"+counter+"' name='disconnchargeButton"+counter+"' title='Click to disconnect/revert charge' value='...' onClick='disconnectCharge("+chargelists.list[iChargeIndexCounter].chargeInfoID+",2,"+counter+","+chargelists.list[iChargeIndexCounter].chargeType+","+chargelists.list[iChargeIndexCounter].rcActiveDateCrossed+","+chargelists.list[iChargeIndexCounter].nrcActiveDateCrossed+","+end_date_logic+","+chargelists.list[iChargeIndexCounter].rcInactiveDateCrossed+",1)'>";
												isDisconnectionChargeButtonCreated=1;
											}else if(chargelists.list[iChargeIndexCounter].endDateLogic == 'BTD' && chargelists.list[iChargeIndexCounter].rcInactiveDateCrossed == '0'){
													//str= "<div class='searchBg1'><a href='#' id='disconnchargeButton"+counter+"' >...</a></div>";								
													str="&nbsp;&nbsp;";
											}else if(chargelists.list[iChargeIndexCounter].endDateLogic == 'BTD' && chargelists.list[iChargeIndexCounter].rcInactiveDateCrossed == '1'){
												//str= "<div class='searchBg1'><a href='#' id='disconnchargeButton"+counter+"' onClick='disconnectCharge("+chargelists.list[iChargeIndexCounter].chargeInfoID+",2,"+counter+","+chargelists.list[iChargeIndexCounter].chargeType+","+chargelists.list[iChargeIndexCounter].rcActiveDateCrossed+","+chargelists.list[iChargeIndexCounter].nrcActiveDateCrossed+","+end_date_logic+","+chargelists.list[iChargeIndexCounter].rcInactiveDateCrossed+")'>...</a></div>";							
												str="<input type='button' id='disconnchargeButton"+counter+"' name='disconnchargeButton"+counter+"' title='Click to disconnect/revert charge' value='...' onClick='disconnectCharge("+chargelists.list[iChargeIndexCounter].chargeInfoID+",2,"+counter+","+chargelists.list[iChargeIndexCounter].chargeType+","+chargelists.list[iChargeIndexCounter].rcActiveDateCrossed+","+chargelists.list[iChargeIndexCounter].nrcActiveDateCrossed+","+end_date_logic+","+chargelists.list[iChargeIndexCounter].rcInactiveDateCrossed+",1)'>";
												isDisconnectionChargeButtonCreated=1;
											}
										}else if(chargelists.list[iChargeIndexCounter].chargeType == 2  && chargelists.list[iChargeIndexCounter].nrcActiveDateCrossed == '0'){
											//str= "<div class='searchBg1'><a href='#' id='disconnchargeButton"+counter+"' >...</a></div>";						
											str="&nbsp;&nbsp;";
										}
										if(isDisconnectionChargeButtonCreated==1){								
											//[0002]
											var callerWindowObj = dialogArguments;
											if((isView!=null && isView==1)||(orderStage=="Billing Trigger" || orderStage=="Published" || (orderStage=="Partial Initiated" && callerWindowObj.gb_roleID=="51184"))){
												//str="<div class='searchBg1'><a href='#' id='disconnchargeButton"+counter+"'>...</a></div>";
												str="<input type='button' id='disconnchargeButton"+counter+"' name='disconnchargeButton"+counter+"' value='...'  disabled='disabled'>";	
												//str="&nbsp;&nbsp;";
											}								
										}
										tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
										chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
										/*CellContents = str;
										tblcol.innerHTML = CellContents;*/			
									
									}
								/*var tblcol=tblRow.insertCell();
								tblcol.align = "left";
								tblcol.vAlign="top";
								tblcol.className="innerdata col3";
								//str="<input type='text' isRequired='0' Disp='' maxlength='4' class='inputBorder'>";
								str="&nbsp;&nbsp;";
								CellContents = str;
								tblcol.innerHTML = CellContents;
								//[0002]
								tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
								chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;*/
									
					}else{
						/*var tblcol=tblRow.insertCell();
						tblcol.align = "left";
						tblcol.vAlign="top";
						tblcol.className="innerdata col3";*/
						tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
						//str="<input type='text' isRequired='0' Disp='' maxlength='4' class='inputBorder'>";
						str="&nbsp;&nbsp;";
						/*CellContents = str;
						tblcol.innerHTML = CellContents;*/
						chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				}
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col2";*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
				str="&nbsp;&nbsp;";
				// 090 start
				if(chargelists.list[iChargeIndexCounter].isdisconnected==0 && created_serviceid!=servicesID){
					//[0002] starts
					if(chargelists.list[iChargeIndexCounter].endDateLogic=='BTD'){
						str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:150px;' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtChargeStatus' id='txtChargeStatus"+counter+"' value='Billing Trigger Date' readonly='readonly'>";
					}
					else if(chargelists.list[iChargeIndexCounter].endDateLogic=='TD'){
						str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:150px;' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtChargeStatus' id='txtChargeStatus"+counter+"' value='Till Disconnection' readonly='readonly'>";
					}
					else if(chargelists.list[iChargeIndexCounter].endDateLogic=='OT'){
						str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:150px;' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtChargeStatus' id='txtChargeStatus"+counter+"' value='One Time Charge' readonly='readonly'>";
					}
					//[0002] ends
				}else if(chargelists.list[iChargeIndexCounter].isdisconnected==1 && chargelists.list[iChargeIndexCounter].disconnection_type=='1'){
					str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtChargeStatus' id='txtChargeStatus"+counter+"' value='Inactive'  readonly='readonly'>";
				}else if(chargelists.list[iChargeIndexCounter].isdisconnected==1 && chargelists.list[iChargeIndexCounter].disconnection_type=='2'){
					str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtChargeStatus' id='txtChargeStatus"+counter+"' value='Close'  readonly='readonly'>";
				}
				
				/*CellContents = str;
				tblcol.innerHTML = CellContents; */
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				/*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";
				tblcol.className="innerdata col2";*/
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col2'>";
				str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;float:left' isRequired='0' Disp='' class='inputBorder' name='txtOBCharge' id='txtOBCharge"+counter+"' value='"+chargelists.list[iChargeIndexCounter].obLinkChargeId+"' readOnly='readOnly'>";
				if(chargelists.list[iChargeIndexCounter].obLinkChargeId > 0 ){
					//this charge is linked with a disconnected charge so, should be disable. For that purpose we put this charge id into an array
					linkChargeArryCounter ++ ;//initialize with 1
					linkChargeArry[linkChargeArryCounter]=counter; 
					
				}
			//	if(created_serviceid==servicesID && !((isView!=null && isView==1)||(orderStage=="Billing Trigger" || orderStage=="Published")))
				//	str = str+"<div class='searchBg' id='divpopUpOBChargeLinking' style='float: left'><a href='#' onclick='popUpForDisconnectedChargeLookup("+counter+")' id='popUpOBChargeLinking"+counter+"'>...</a></div>"; -->
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				/*document.getElementById('txtCStartDate' + counter).value=chargelists.list[iChargeIndexCounter].startDateLogic;
				document.getElementById('txtCEndDate' + counter).value=chargelists.list[iChargeIndexCounter].endDateLogic;
						
				var ctrlArry2 = new Array();
				ctrlArry2[0] =  document.getElementById("ddCType"+counter);
				ctrlArry2[1] =  document.getElementById("txtCName"+counter);
				ctrlArry2[2] =  document.getElementById("txtCPeriod"+counter);
				ctrlArry2[3] =  document.getElementById("txtCTAmt"+counter);
				ctrlArry2[4] =  document.getElementById("txtCFrequency"+counter);
				ctrlArry2[5] =  document.getElementById("txtCFreqAmt"+counter);
				ctrlArry2[6] =  document.getElementById("txtCStartDate"+counter);
				ctrlArry2[7] =  document.getElementById("txtCEndDate"+counter);
				ctrlArry2[8] =  document.getElementById("txtCAnnotation"+counter);
				ctrlArry2[9] =  document.getElementById("txtRemarks"+counter);
				var callerWindowObj = dialogArguments;		

				fieldRoleMappingValidation(callerWindowObj.linesTabFieldList,ctrlArry2);
				changeFrequency(counter);*/
				ctrlArry2[0+controlIndex] =  "ddCType"+counter;
				ctrlArry2[1+controlIndex] =  "txtCName"+counter;
				ctrlArry2[2+controlIndex] =  "txtCPeriod"+counter;
				ctrlArry2[3+controlIndex] =  "txtCTAmt"+counter;
				ctrlArry2[4+controlIndex] =  "txtCFrequency"+counter;
				ctrlArry2[5+controlIndex] =  "txtCFreqAmt"+counter;
				ctrlArry2[6+controlIndex] =  "txtCStartDate"+counter;
				ctrlArry2[7+controlIndex] =  "txtCEndDate"+counter;
				ctrlArry2[8+controlIndex] =  "txtCAnnotation"+counter;
				ctrlArry2[9+controlIndex] =  "txtRemarks"+counter;
				ctrlArry2[10+controlIndex] =  "chargeName"+counter;
				ctrlArry2[11+controlIndex] =  "chargeNameLink"+counter;
				controlIndex = controlIndex + 12;
				chargeListTable = chargeListTable + tableRowEnd;
			}
	    	chargeListTable = chargeListTable + tableEnd;
	    	chargeTableDiv.innerHTML=chargeListTable;
	    	var callerWindowObj = dialogArguments;	
	    	allFieldRoleMappingValidation(callerWindowObj.linesTabFieldList,ctrlArry2);
	    	for (iChargeIndexCounter = 0 ; iChargeIndexCounter < iTotalLength; iChargeIndexCounter++){
	     		$("#ddCType" + (iChargeIndexCounter +1)).val(chargeTypeSelectedValue[iChargeIndexCounter]);
	     		$("#txtCFrequency" + (iChargeIndexCounter +1)).val(chargeFqSelectedValue[iChargeIndexCounter]);
	     		if(chargeTypeSelectedValue[iChargeIndexCounter]==1){
					document.getElementById('txtCFrequency' + (iChargeIndexCounter +1)).disabled=false;
				}else{
					document.getElementById('txtCFrequency' + (iChargeIndexCounter +1)).disabled=true;
				}	    
	     		$("#txtCStartDate" + (iChargeIndexCounter +1)).val(chargeSDSelectedValue[iChargeIndexCounter]);
	     		$("#txtCEndDate" + (iChargeIndexCounter +1)).val(chargeEDSelectedValue [iChargeIndexCounter]);
	     	}
	    	hdnChargeCount.value = counter;
	    	//Puneet calling all field role validation
			//-------------------------[PagingSorting:Date:04-Dec-2012 Navigation Disable Enable]----------------------------------
		    var pagenumber=document.getElementById('txtPageNumber').value;
			var MaxPageNo=document.getElementById('txtMaxPageNo').value;
			if(pagenumber && MaxPageNo==1){	
			     document.getElementById('first').disabled=true;
			     document.getElementById('prev').disabled=true;
			     document.getElementById('last').disabled=true;
			     document.getElementById('next').disabled=true;		
			 }	 
			 if(pagenumber==1 && MaxPageNo!=1){
			     document.getElementById('first').disabled=true;
			     document.getElementById('prev').disabled=true;
			     document.getElementById('last').disabled=false;
			     document.getElementById('next').disabled=false;	 
			 }	 
			 if(pagenumber==MaxPageNo && pagenumber!=1){
			     document.getElementById('last').disabled=true;
			     document.getElementById('next').disabled=true;
			     document.getElementById('first').disabled=false;
			     document.getElementById('prev').disabled=false;	 
			 }	 
			 if(pagenumber!=MaxPageNo && pagenumber!=1){
			     document.getElementById('last').disabled=false;
			     document.getElementById('next').disabled=false;
			     document.getElementById('first').disabled=false;
			     document.getElementById('prev').disabled=false;	 
			 }											
			//-------------------------[PagingSorting:Date:04-Dec-2012 Navigation Disable Enable]----------------------------------										 
			//calculateTotalLineAmount('fromOnLoadFunction',null);
			document.getElementById('totalLineAmount').value=chargelists.list[0].totalChargeAmount;
			document.getElementById('hdnDeletedChargesListId').value=0;
			//Puneet assigning the css
			$("#tableCharges").find("[ctrltype='dropdownlink']").each(function() {
	    		attachCSForAS($(this));
	    	});
			callOnChargeAmountChange();
			return false;
		}
	else{
		document.getElementById('viewChargeLineTabNavigatorId').style.display='none';
		return false;
	}
}
//=============================[ Draw Charge Table-15-02-2013 ]=====================================
//Start [TRNG22032013018]
var serviceListArray=new Array();
function treeViewPagingMethod(methodName)
{
	var serviceId=0;
	var enteredServiceIndex=0;
	if (serviceListArray.length==0)//ServiceListArray will be loaded only once when user click on navigation links
	{
	    var serviceList = jsonrpc.processes.poulateServiceListForArrayLoading(Number(callerWindowObj.document.getElementById('poNumber').value));
	    for (var i = 0 ; i <serviceList.list.length; i++)
	    {
	    	serviceListArray[i]=serviceList.list[i].serviceId;
	    }
	}
	if(methodName=='GoToPage')
	{
		if(trim(document.getElementById('serviceId_goToPage').value)==""){
			alert('Please enter any digit!!');
			document.getElementById('serviceId_goToPage').focus;
			return false;
		}
		serviceId = Number(document.getElementById('serviceId_goToPage').value);
		enteredServiceIndex=serviceListArray.indexOf(serviceId)
		if(enteredServiceIndex==-1)
		{
			alert('This service is not present in current Order No!!!\n Please enter service of this order No.');
			return false;
		}
		getServiceDetailsForSwitchingService(serviceId);
	}
	else if(methodName=='Next')
	{
		document.getElementById('serviceId_goToPage').value="";
		serviceId = Number(document.getElementById('id_span_ServiceNo').innerHTML);
		enteredServiceIndex=serviceListArray.indexOf(serviceId)
		var maxIndex=serviceListArray.length;
		maxIndex = maxIndex-1;
		if(enteredServiceIndex==maxIndex)
		{
			alert('There is no next service!!');
			return false;
		}
		enteredServiceIndex=enteredServiceIndex+1;
		serviceId = serviceListArray[enteredServiceIndex];
		getServiceDetailsForSwitchingService(serviceId);
	}
	else if(methodName=='Prev')
	{
		document.getElementById('serviceId_goToPage').value="";
		serviceId =Number(document.getElementById('id_span_ServiceNo').innerHTML);
		enteredServiceIndex=serviceListArray.indexOf(serviceId)
		if(enteredServiceIndex==0)
		{
			alert('There is no previous service!!');
			return false;
		}
		enteredServiceIndex=enteredServiceIndex-1;
		serviceId = serviceListArray[enteredServiceIndex];
		getServiceDetailsForSwitchingService(serviceId);
	}
	
}
function getServiceDetailsForSwitchingService(serviceId)
{
	var dataforswitching = jsonrpc.processes.getServiceDetailsForSwitchingService(serviceId);
	if(dataforswitching.list.length==1)
	{
		document.getElementById('id_span_ServiceNo').innerHTML = serviceId;
		document.getElementById('id_span_ServiceName').innerHTML = dataforswitching.list[0].serviceTypeName;
		document.getElementById('lblServiceProductId').innerHTML = "LineItem No:"+dataforswitching.list[0].serviceProductID;
		callerWindowObj.document.getElementById('hdnserviceid').value = serviceId;
		serviceNo = serviceId;
		serviceName = dataforswitching.list[0].serviceName;
		productName = dataforswitching.list[0].serviceDetDescription;
		serviceDetailID = dataforswitching.list[0].serviceDetailID;
		serviceProductID = dataforswitching.list[0].serviceProductID;
		document.getElementById('hdnServiceProductID').value = serviceProductID
		callerWindowObj.document.forms[0].SPIDUrlforVPC.value = dataforswitching.list[0].link;
		fncServiceTreeView();
		document.getElementById('BillingNChargeInfo').style.display='none';
		document.getElementById('BillingInfo').style.display='none';
		document.getElementById('ServiceLocation').style.display='none';
		document.getElementById('HardwareDetails').style.display='none';
		document.getElementById('ChargeDetails').style.display='none';
		document.getElementById('Components').style.display='none';
		document.getElementById('lineItemServiceSummary').style.display='none';
		fncServiceTreeView();
		getServiceAttributes();
	}
}
//End [TRNG22032013018]
function advPayment() 
{
	if(document.getElementById('hdnBillingInfo').value==0)
	{
		alert("User can't input Advance Payment Details as 17 Parameter for this line item doesn't get generated");
		return false;
	}
	else
	{
		if(hdnChangeTypeId==3 || hdnChangeTypeId==1 )
		{
			alert("Advance Payment Details can't updated for Disconnection Order or Network Change Order");
			return false;
		}
		else
		{
			var paths = path+"/NewOrderAction.do?method=getAdvancePaymentDetails&orderNo="+poNumber+"&lineItemNo="+serviceProductID+"&serviceNo="+serviceNo+"&logicalSIno="+logicalSIno+"&isView="+isView;
			window.showModalDialog(paths,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
		}
	}
}
//remove OB PK charge linking if disconnected charge is revert back (Active)
function removeOBPKChargeLinking(disChargeId){
	/*the disChargeId is  a charge that is reverted back that means now 
	* it has active. So we should removed the OB PK charge linking if exist 
	*/
	obPKCharge = document.getElementsByName('txtOBCharge');
	var counter = parseInt(document.getElementById('hdnChargeCount').value);
	var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
	
	for(i=counter; i >= 1; i--){
		if(document.getElementById('txtOBCharge'+i) != undefined && document.getElementById('txtOBCharge'+i).value==disChargeId){
			for(k=0; k < chkChargeLine.length; k++ ){
				if(chkChargeLine[k].value == i && chkChargeLine[k].disabled==false){
					//delete the charge which was linked with disconnected charge
					deleteChargeLine(chkChargeLine[k]); 
					break;
				} 
			}
		}
	}
}
/*Vijay
 * Disable all fields for all charge that is passing in this array. This method is calling every time whenever 
 * page is loading and linkChargeArry contains the list of all charge that is new and linked with disconnected charge.
 * So need to be disable all fields except the amount fields
 */
function disableOBLinkeChargeRow(linkChargeArry){
	if(linkChargeArry.length > 0){
		for(linkChIndex = 0; linkChIndex < linkChargeArry.length; linkChIndex++){
			var counter = linkChargeArry[linkChIndex] //get the counter of elements that is going to be disable
			
			document.getElementById('ddCType'+counter).disabled=true;
			document.getElementById('chargeName'+counter).disabled=true;
			document.getElementById('txtCAnnotation'+counter).disabled=true;
			document.getElementById('txtTaxRate'+counter).disabled=true;
			document.getElementById('txtCPeriod'+counter).disabled=true;
			document.getElementById('txtCFrequency'+counter).disabled=true;
			document.getElementById('txtCFreqAmt'+counter).disabled=true;
			document.getElementById('txtCStartDate'+counter).disabled=true;
			document.getElementById('txtStartDays'+counter).disabled=true;
			document.getElementById('txtStartMonth'+counter).disabled=true;
			document.getElementById('txtCEndDate'+counter).disabled=true;
			document.getElementById('txtEndDays'+counter).disabled=true;
			document.getElementById('txtEndMonth'+counter).disabled=true;
			document.getElementById('txtAnnualCharge'+counter).disabled=true;
			document.getElementById('txtChargeStartDate'+counter).disabled=true;
			document.getElementById('txtChargeEndDate'+counter).disabled=true;
			document.getElementById('chkexcludeCharges'+counter).disabled=true;
			document.getElementById('txtLDDateClause'+counter).disabled=true;
			document.getElementById('txtDelayedTimeInDays'+counter).disabled=true;
			document.getElementById('txtLDPercentage'+counter).disabled=true;
			document.getElementById('txtMaxPenaltyPercentage'+counter).disabled=true;
			//document.getElementById('txtRemarks'+counter).readOnly=false;
			
			document.getElementById('txtPayment1'+counter).disabled=true;
			document.getElementById('txtPayment2'+counter).disabled=true;
			document.getElementById('txtPayment3'+counter).disabled=true; 
			document.getElementById('txtPayment4'+counter).disabled=true;	
		}
	}
}

/*Vijay
 * Get the all charge list those are disconnected and linked with new charge
 *  for this current service and service product id
 */
function getDisconnectLinkedCharge(){
	//Going to fetch the charges id that is disconnected and also linked with new charges
	//servicesID on the basis of service product id
	var linkedChargeIds = jsonrpc.processes.getDisconnectLinkedCharges(serviceProductID, servicesID);
	if(linkedChargeIds.length > 0){
		for(i=0; i < linkedChargeIds.length; i++ ){
			//add this charge ids into an array for further validation
			disLinkChargeArray[i]=linkedChargeIds[i]; //setting the disconnected linked charge id into Array
		}
	}
}

/*Vijay
 * The passing chargeId would be disconnected charge id and going to revert. 
 *Here we are finding that chargeId is linked to a new Charge or not.
 */
function findDisconnectLinkedCharge(chargeId){
	var findFlag = false;
	if(disLinkChargeArray.length > 0){
		for ( var i = 0; i < disLinkChargeArray.length; i++) {
			if(disLinkChargeArray[i]==chargeId){
				findFlag = true;
				break;
			}
		}
	}
	return findFlag;
}

/*Vijay
 * The passing chargeId is going to add to an array and that array contains all disconnected 
 * charges id that is linked with new charge
 */
function addDisconnectLinkedCharge(chargeId){
	if(disLinkChargeArray != null ){
		disLinkChargeArray[disLinkChargeArray.length] = chargeId
	}
}

/*Vijay
 * The passing chargeId is going to remove from an array. The array containing the all disconnected charge ids 
 * which are linked with new charges. And since the passing charge is has been unlinked so need to be remove from the array
 */
function deleteDisconnectLinkedCharge(chargeId){
	if(disLinkChargeArray != null && disLinkChargeArray.length >0){
		for ( var i = 0; i < disLinkChargeArray.length; i++) {
			if(disLinkChargeArray[i]==chargeId){
				disLinkChargeArray[i] = -1 //here setting a default value that means charge has been deleted in the list
			}
		}
	}
}
//bcp details for services ADDED BY MANISHA START
function FetchBillingDetailsforService()
{
	var tr, td, i, j, oneRecord;
    var test5;
   	if(document.getElementById("bbPrimaryBCPService").value != "")
    {
    	var jsData5 =			
    	{
			bcpID:document.getElementById("bbPrimaryBCPService").value
		};
	
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");

    	test5 = jsonrpc.processes.populateBCPDetails(jsData5);
	
		if(test5.list.length>0)
		{
		    document.getElementById('txtBAddress1Service').value = test5.list[0].baddress1;
			document.getElementById('txtBAddress2Service').value = test5.list[0].baddress2;
			document.getElementById('txtBAddress3Service').value = test5.list[0].baddress3;
			document.getElementById('txtBAddress4Service').value = test5.list[0].baddress4;
			document.getElementById('txtBCityService').value = test5.list[0].bcity;
			document.getElementById('txtBStateService').value = test5.list[0].bstate;
			document.getElementById('txtBPincodeService').value = test5.list[0].bpincode;
			document.getElementById('txtBCountryService').value = test5.list[0].bcountry;
			document.getElementById('txtBContactNoService').value = test5.list[0].bcontactNo;
			document.getElementById('txtBEmailIdService').value = test5.list[0].bemailid;
			document.getElementById('designationService').value = test5.list[0].designation;
			document.getElementById('lst_NoService').value = test5.list[0].lst_No;
			document.getElementById('lst_DateService').value = test5.list[0].lstDate;
			document.getElementById('txtBContactNameService').value = test5.list[0].bcontactName;
			//010	Start
			document.getElementById('txtCircleService').value = test5.list[0].revCircle;
			//010	End
		}
	}
	else
	{ 
	     	document.getElementById('txtBAddress1Service').value = "";
			document.getElementById('txtBAddress2Service').value = "";
			document.getElementById('txtBAddress3Service').value = "";
			document.getElementById('txtBAddress4Service').value = "";
			document.getElementById('txtBCityService').value = "";
			document.getElementById('txtBStateService').value = "";
			document.getElementById('txtBPincodeService').value ="";
			document.getElementById('txtBCountryService').value = "";
			document.getElementById('txtBContactNoService').value ="";
			document.getElementById('txtBEmailIdService').value = "";
			document.getElementById('designationService').value = "";
			document.getElementById('lst_NoService').value ="";
			document.getElementById('lst_DateService').value ="";
			document.getElementById('txtBContactNameService').value = "";
			//010	Start
			document.getElementById('txtCircleService').value = "";
			//010	End
	}
}
//========================================================================================================

/*function callOnBlur()
{
	$(":text[ctrltype='dropdown']").each(function() {
			var thisEl = $(this);				
		$(thisEl).blur(function()
		{				
			
		});	
	});
}	

function getAutoSuggest() 
{
			       		         	         
         $(":text[ctrltype='dropdown']").each(function() {
			var thisEl = $(this);																				
        	thisEl.autocomplete({
    			   
		            source: function(request, response) 
		            {
		            			            																		
                	$.ajax({
						url: path + "/NewOrderAction.do?method=populateServiceAttMasterValue&searchval="+request.term+"&attributefor="+thisEl.attr("retval")+"&sourceType=CHANGE&serviceId="+serviceNo+"&SPID="+serviceProductID+"&accountId="+document.getElementById('txtBillingAC').value,
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
                                        label: item.label 
	                            }
	                        }))
                    },async:false,
                    error: function(XMLHttpRequest, textStatus, errorThrown) {
                        alert(errorThrown);
                    }
                });
            },select: function (event, ui)
                            {                                	                                                           
                                 if(ui.item.value=="-1")
                                 {
                                 	if(thisEl.attr("retval")=="AUTOSUGGESTBCP")
                                 	{
                                 	$(thisEl).val("");	
										$("#bbPrimaryBCPID").val("0");
                                 	}
                                     	//bcp details for services ADDED BY MANISHA START
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTBCPSERVICE")
                                     	{
                                     		$(thisEl).val("");	
						$("#bbPrimaryBCPIDService").val("0");
                                     	}
                                     	//bcp details for services ADDED BY MANISHA end
                                 	else if(thisEl.attr("retval")=="AUTOSUGGESTDISPATCH")
                                 	{
                                 		$(thisEl).val("");	
										$("#txtdispatchAddress").val("0");
                                 	}
                                 	//Start [006]
                                 	else if(thisEl.attr("retval")=="AUTOSUGGESTPRICUSTLOC")
                                 	{
                                 		$(thisEl).val("");	
										$("#ddPrimaryBCP").val("0");
                                 	}
                                 	else if(thisEl.attr("retval")=="AUTOSUGGESTSECCUSTLOC")
                                 	{
                                 		$(thisEl).val("");	
										$("#ddSecondaryBCP").val("0");
                                 	}
                                 	//End [006]
                                 	else{
                                 		$(thisEl).val("");	
                                 		if(editSolutionChangeOldProduct==0)
                                 			$("#prodAttVal_"+thisEl.attr("counterval")).val("0");
                                 		else
											$("#newprodAttVal_"+thisEl.attr("counterval")).val("0");
										
									}
                                 }
                                 else
                                 {
                                 	if(thisEl.attr("retval")=="AUTOSUGGESTBCP")
                                 	{
                                 		$("#bbPrimaryBCPID").val(ui.item.value);
	                                    $(thisEl).val(ui.item.label);
	                                    FetchBillingDetails();
                                 	}
                                     	//bcp details for services ADDED BY MANISHA START
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTBCPSERVICE")
                                     	{
                                     		$("#bbPrimaryBCPIDService").val(ui.item.value);
		                                    $(thisEl).val(ui.item.label);
		                                    FetchBillingDetailsforService();
                                     	}
                                     	//bcp details for services ADDED BY MANISHA end
                                 	else if(thisEl.attr("retval")=="AUTOSUGGESTDISPATCH")
                                 	{
                                 		$("#txtdispatchAddress").val(ui.item.value);
	                                    $(thisEl).val(ui.item.label);
	                                    getDispatchAddress();
                                 	}
                                 	//Start [006]
                                 	else if(thisEl.attr("retval")=="AUTOSUGGESTPRICUSTLOC")
                                 	{
                                 	$("#ddPrimaryBCP").val(ui.item.value);
	                                $(thisEl).val(ui.item.label);		                                 
									FetchPriBCPDetails();
                                 	}
                                 	else if(thisEl.attr("retval")=="AUTOSUGGESTSECCUSTLOC")
                                 	{
                                 	$("#ddSecondaryBCP").val(ui.item.value);
	                                $(thisEl).val(ui.item.label);		                                 
									FetchSecBCPDetails();
                                 	}
                                 	//End [006]
                                 	else{
                                     	var allowValueChange=1;
                                     	
                                     	allowValueChange=isValueToBeChanged_ProductConfigIdValidation(thisEl,ui);
									 	
									 	if(allowValueChange==1)
									 	{
									 		$("#newprodAttVal_"+thisEl.attr("counterval")).val(ui.item.value);
                                     		$(thisEl).val(ui.item.label);
                                     		onChangeofServiceSummaryAttrValue(editSolutionChangeOldProduct,thisEl,ui);
									 	}
                                     }
                                 }
                                 
       return false;
   },
   change: function( event, ui ) 
						{																													
							if(ui.item==null || thisEl.val()=="-No Record Found-" ||thisEl.val()=="-1"|| $("#newprodAttVal_"+thisEl.attr("counterval")).val()=="0")
							{
									                				
                				var value2,label2;
                			 	var validateData=$.ajax({
												url: path + "/NewOrderAction.do?method=populateServiceAttMasterValueValidate&searchval="+thisEl.val()+"&attributefor="+thisEl.attr("retval")+"&sourceType=CHANGE&serviceId="+serviceNo+"&SPID="+serviceProductID+"&accountId="+document.getElementById('txtBillingAC').value,
				                    			data: "{ edition: '" + thisEl.val() + "',targetctrl: 'test' }",
				                    			dataType: "json",
				                    			type: "Get",
				                    			contentType: "application/json; charset=utf-8",
				                    			dataFilter: function(data) {  return data; },
			                    				success: function(data) {
			                    				
			                    					value2=data.glossary[0].value;
			                    					label2=data.glossary[0].label;
			                    				},async:false,
			                    				error: function(XMLHttpRequest, textStatus, errorThrown) {
			                        				alert(errorThrown);
			                    				}
			                				});	    
								if(value2=="-1" ||label2=="-No Record Found-"){
									if(thisEl.attr("retval")=="AUTOSUGGESTBCP")
                                 	{
									$(thisEl).val("");
										$("#bbPrimaryBCPID").val("0");	
                                 	}
                                     	//bcp details for services ADDED BY MANISHA START
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTBCPSERVICE")
                                     	{
                                     		$(thisEl).val("");	
											$("#bbPrimaryBCPIDService").val("0");
                                     	}
                                     	//bcp details for services ADDED BY MANISHA end
                                 	else if(thisEl.attr("retval")=="AUTOSUGGESTDISPATCH")
                                 	{
                                 		$(thisEl).val("");	
										$("#txtdispatchAddress").val("0");
                                 	}
                                 	//Start [006]
                                 	else if(thisEl.attr("retval")=="AUTOSUGGESTPRICUSTLOC")
                                 	{
                                 		$(thisEl).val("");	
										$("#ddPrimaryBCP").val("0");
                                 	}
                                 	else if(thisEl.attr("retval")=="AUTOSUGGESTSECCUSTLOC")
                                 	{
                                 		$(thisEl).val("");	
										$("#ddSecondaryBCP").val("0");
                                 	}
                                 	//End [006]
                                 	else{
										$(thisEl).val("");
										if(editSolutionChangeOldProduct==0)
                                 			$("#prodAttVal_"+thisEl.attr("counterval")).val("0");
                                 		else
									$("#newprodAttVal_"+thisEl.attr("counterval")).val("0");		
									}	
								}else{
									if(thisEl.attr("retval")=="AUTOSUGGESTBCP")
                                 	{
									$(thisEl).val(label2);
										$("#bbPrimaryBCPID").val(value2);
										FetchBillingDetails();
                                 	}
                                     	//bcp details for services ADDED BY MANISHA START
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTBCPSERVICE")
                                     	{
		                                    $(thisEl).val(label2);
											$("#bbPrimaryBCPIDService").val(value2);
											FetchBillingDetailsforService();
                                     	}
                                     	//bcp details for services ADDED BY MANISHA end
                                 	else if(thisEl.attr("retval")=="AUTOSUGGESTDISPATCH")
                                 	{
                                 	$(thisEl).val(label2);
									$("#txtdispatchAddress").val(value2);
									getDispatchAddress();
                                 	}
                                 	//Start [006]
                                 	else if(thisEl.attr("retval")=="AUTOSUGGESTPRICUSTLOC")
                                 	{
                                 	$(thisEl).val(label2);
									$("#ddPrimaryBCP").val(value2);
									FetchPriBCPDetails();
                                 	}
                                 	else if(thisEl.attr("retval")=="AUTOSUGGESTSECCUSTLOC")
                                 	{
                                 	$(thisEl).val(label2);
									$("#ddSecondaryBCP").val(value2);
									FetchSecBCPDetails();
                                 	}
                                 	//End [006]
                                 	else{
										$(thisEl).val(label2);
										if(editSolutionChangeOldProduct==0)
                                 			$("#prodAttVal_"+thisEl.attr("counterval")).val(ui.item.value);
                                 		else
									$("#newprodAttVal_"+thisEl.attr("counterval")).val(value2);
									fnGetDestLabelValueForLOV(thisEl.attr("retval"),label2);
									}
								}				                				    			
															
							}else{	
							
								if(thisEl.attr("retval")=="AUTOSUGGESTBCP")
                                     	{
                                     	//
                                     	}
                                     	else if(thisEl.attr("retval")=="AUTOSUGGESTBCPSERVICE")
                                 	{
                                 	//
                                 	}
                                 	else if(thisEl.attr("retval")=="AUTOSUGGESTDISPATCH")
                                 	{
                                 	}
                                 	//Start [006]
                                 	else if(thisEl.attr("retval")=="AUTOSUGGESTPRICUSTLOC")
                                 	{
                                 	}
                                 	else if(thisEl.attr("retval")=="AUTOSUGGESTSECCUSTLOC")
                                 	{
                                 	}
                                 	//End [006]
                                 	else
                                 	{
									 //added to fetch dependent level values by lawkush  								
									 fnGetDestLabelValueForLOV(thisEl.attr("retval"),ui.item.label);
									}
								
							}																
						},								
            minLength: 0
        });
		});	
		}						
function AttachCSStoAutoSuggestButton(){
		$("[ctrltype='dropdownlink']").each(function(){
			var thisEl = $(this);
			thisEl.attr( "tabIndex", -1 )
				.attr( "title", "Show All Items" )
				.button({
					icons: {
						primary: "ui-icon-triangle-1-s"
					},
					text: false
				})
				.removeClass( "ui-corner-all" )
				.addClass( "ui-corner-right ui-button-icon" )
	    });	
	    callOnBlur();
}

function getDropDown(attrid){							
	$("#autoSuggProdAttVal_"+attrid).autocomplete( "search", "" );
	$("#autoSuggProdAttVal_"+attrid).focus();	
}
//START[13082012]AutoSuggest for BCP
function getDropDownBilling(){
	$("#txtBCPAddressSuggest").autocomplete( "search", "" );
	$("#txtBCPAddressSuggest").focus();
}
//END[13082012]AutoSuggest for BCP
//========================================================================================================
//START[13082012]AutoSuggest for DISPATCH
function getDropDownDispatch(){
	$("#txtDispatchAddressSuggest").autocomplete( "search", "" );
	$("#txtDispatchAddressSuggest").focus();
}
//END[13082012]AutoSuggest for DISPATCH

//Auto suggest Cust Location Start [006]
function getDropDownPriCustLocation(){
	$("#txtPriCustLocationSuggest").autocomplete( "search", "" );
	$("#txtPriCustLocationSuggest").focus();
}

function getDropDownSecCustLocation(){
	$("#txtSecCustLocationSuggest").autocomplete( "search", "" );
	$("#txtSecCustLocationSuggest").focus();
}*/


function setASValuesForFetchedRecord(thisEl, ui){
	if(thisEl.attr("retval")=="AUTOSUGGESTBCP"){
		if(ui.item.value != $("#bbPrimaryBCPID").val()){
			$("#bbPrimaryBCPID").val(ui.item.value);
			$(thisEl).val(ui.item.label);
			FetchBillingDetails();
		}	
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTDISPATCH"){
 		if(ui.item.value != $("#txtdispatchAddress").val()){
 			$("#txtdispatchAddress").val(ui.item.value);
 			$(thisEl).val(ui.item.label);
 			getDispatchAddress();
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTCUSTPODETAIL"){
 		if(ui.item.value != $("#txtPODetailNo").val()){
 			getPrevCustPoNo();
 			if(checkchargevalidation()==false){
 				focusEnabled = false;
 				return false;
 			}else{
 				fnFetchEntity();
 				setPODetailDepOnChange();
 				selectPoIdAS(thisEl);
 				$("#txtPODetailNo").val(ui.item.value);
 	 			$(thisEl).val(ui.item.label);
 	 			if(canLegalEntityChanged())
 	 				resetEntity();
 	 			updateselectedCustPODetailDepValues(ui.item.value);
 			}
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBCPSERVICE"){
		if(ui.item.value != $("#bbPrimaryBCPIDService").val()){
			$("#bbPrimaryBCPIDService").val(ui.item.value);
			$(thisEl).val(ui.item.label);
			FetchBillingDetailsforService();
		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTPRICUSTLOC"){//Start [006]
 		if(ui.item.value != $("#ddPrimaryBCP").val()){
 			$("#ddPrimaryBCP").val(ui.item.value);
 			$(thisEl).val(ui.item.label);		                                 
 			FetchPriBCPDetails();
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTCP"){
    	$("#txtBillingCP").val(ui.item.value);
        $(thisEl).val(ui.item.label);		                                 
    }else if(thisEl.attr("retval")=="AUTOSUGGESTSECCUSTLOC"){
 		if(ui.item.value != $("#ddSecondaryBCP").val()){
 			$("#ddSecondaryBCP").val(ui.item.value);
 			$(thisEl).val(ui.item.label);		                                 
 			FetchSecBCPDetails();
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTLEGALENTITY"){
 		if(ui.item.value != $("#txtEntity").val()){
 			$("#txtEntity").val(ui.item.value);
 			$(thisEl).val(ui.item.label);		                                 
 			resetLicenseCo();
 			//resetContMonth();
 			$("#hdnISFLEFLAG").val(ui.item.addParam);
 			
 			setLegalEntityDepValuesOnChange();
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTLICENSECO"){
 		if(ui.item.value != $("#txtLicenceCo").val()){
 			var additionalNodeFlag1 = additionalNodeFlagCheck();
 			if(editSolutionChangeOldProduct ==1){
 	 	    	 if(document.getElementById("legalEntityTXTLinkId").disabled==true){
 	 	    		 document.getElementById("txtLicenceCo").value = gb_selectedLicense_Comp;
 	 	    		 document.getElementById("licenseCo").value = gb_selectedLicense_Text;
 	 	    	 }else{
 	 	    		$(thisEl).val(ui.item.label);	
 	 	 			$("#txtLicenceCo").val(ui.item.value);
 	 	    	 }
 	 	    	 if($("#hdnHardwareInfo").val()==1){//if Hardware
 	 	    		resetStore();
 	 	    	 } 
 	 	    }else if(additionalNodeFlag1 == 1 && $("#hdnHardwareInfo").val()==1){
 	 	    	$(thisEl).val(ui.item.label);	
	 	 		$("#txtLicenceCo").val(ui.item.value);
	 	 		$("#licenseCo").attr('disabled', 'disabled');
 				$("#licenseCoLinkId").attr('disabled', 'disabled');
	 	 		resetStore();
 	 	    }else{
 	 	    	$(thisEl).val(ui.item.label);	
	 	 		$("#txtLicenceCo").val(ui.item.value);
	 	 		if($("#hdnHardwareInfo").val()==1)
	 	 			resetStore();
 	 	    }
 			
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGFORMAT"){
		if(ui.item.value != $("#txtBillingformat").val()){
			var hardwareEnabled = document.getElementById('hdnHardwareInfo');
			/*if(hardwareEnabled.value==1){
				getPrevBillFormat();
			}*/
			$("#txtBillingformat").val(ui.item.value);
			$(thisEl).val(ui.item.label);
			if(hardwareEnabled.value==1){
				setTypeofSale();
			}
		}
	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGMODE"){
 		if(ui.item.value != $("#txtBillingMode").val()){
 			$("#txtBillingMode").val(ui.item.value);
 			$(thisEl).val(ui.item.label);	
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGTYPE"){
 		$(thisEl).val(ui.item.label);	
		$("#txtBillingType").val(ui.item.value);
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLTAXATION"){
 		if(ui.item.value != $("#txtTaxation").val()){
 			$("#txtTaxation").val(ui.item.value);
 			$(thisEl).val(ui.item.label);		                                 
 			setPopulateStdReason(ui.item.value);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTREASON"){
 		$(thisEl).val(ui.item.label);	
		$("#changeReason").val(ui.item.value);
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGLEVEL"){
 		if(ui.item.value != $("#txtBillingLvl").val()){
 			$(thisEl).val(ui.item.label);	
 			$("#txtBillingLvl").val(ui.item.value);
 			setBillingLevelNo(ui.item.value);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTCHARGENAME"){
 		if(ui.item.value != $("#txtCName" + thisEl.attr("counterval")).val()){
 			$(thisEl).val(ui.item.label);	
 			$("#txtCName" + thisEl.attr("counterval")).val(ui.item.value);
 			if(ui.item.value != 0)
 				$("#txtCAnnotation" + thisEl.attr("counterval")).val(ui.item.label);
 			else
 				$("#txtCAnnotation" + thisEl.attr("counterval")).val("");
 			getLineNSublineItemLbl(thisEl.attr("counterval"));
 			getTaxRate(thisEl.attr("counterval"));
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTTEXTFORM"){
 		if(ui.item.value != $("#txtform").val()){
 			$(thisEl).val(ui.item.label);	
 			$("#txtform").val(ui.item.value);
 			refreshTaxRateForAS(path);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTSALETYPE"){
 		if(ui.item.value != $("#txtTSale").val()){
 			$(thisEl).val(ui.item.label);	
 			$("#txtTSale").val(ui.item.value);
 			setPAandIR();
 			refreshTaxRateForAS(path);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTSTORE"){
 		if(ui.item.value != $("#txtStore").val()){
 			$(thisEl).val(ui.item.label);	
 			$("#txtStore").val(ui.item.value);
 			refreshTaxRateForAS(path);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTHTYPE"){
 		if(ui.item.value != $("#txtHtype").val()){
 			$(thisEl).val(ui.item.label);	
 			$("#txtHtype").val(ui.item.value);
 			refreshTaxRateForAS(path);
 		}
 	}else{//End [006]
 		var allowValueChange=1;
 		focusEnabled = false;
     	allowValueChange=isValueToBeChanged_ProductConfigIdValidation(thisEl,ui);
	 	if(allowValueChange==1){
	 		$("#newprodAttVal_"+thisEl.attr("counterval")).val(ui.item.value);
     		$(thisEl).val(ui.item.label);
     		onChangeofServiceSummaryAttrValue(editSolutionChangeOldProduct,thisEl,ui);
      		if(thisEl.attr("isParentAtt")==1){
              	resetChildAttr(thisEl);
            }
	 	}
 	}
}
function setLegalEntityDepValuesOnChange(){
	if(editSolutionChangeOldProduct ==1){
    	 if(document.getElementById("licenseCoLinkId").disabled==true){
    		 $("#txtLicenceCo").val(gb_selectedLicense_Comp);
    		 $("#licenseCo").val(gb_selectedLicense_Text);
    	 }else{
    		 $("#txtLicenceCo").val("0");
    		 $("#licenseCo").val("Select Licence Company");
    	 }
    	 if($("#hdnHardwareInfo").val()==1){//if Hardware
	    	/*var path='/IOES';
	    	populateStore(path);
	    	var index=document.getElementById("txtStore").selectedIndex;
	    	if(index < 2){*/
	    		document.getElementById('txtStore').value=storeName;
	    		$("#txtStoreText").val(storeNameVal);
	    	//}
	    } 
    }
    var additionalNodeFlag1 = additionalNodeFlagCheck();
    if(additionalNodeFlag1 == 1 && document.getElementById('hdnHardwareInfo').value==1){
    	
    	 if(document.getElementById("licenseCoLinkId").disabled==true){
    		 $("#txtLicenceCo").val(gb_selectedLicense_Comp);
    		 $("#licenseCo").val(gb_selectedLicense_Text);
    	 }else{	
    		 var serviceDetailID1=Number(document.getElementById("hdnserviceDetailId").value);
    		 var jsData ={
    					entityID:document.getElementById("txtEntity").value,
    					serviceDetailID:serviceDetailID1
    				};
		    test = jsonrpc.processes.populateLicCompany(jsData);
 	     	document.getElementById("txtLicenceCo").value = test.list[0].licCompanyID;
 	     	document.getElementById("licenseCo").value = test.list[0].licCompanyName;
 	    	 $("#licenseCo").attr('disabled', 'disabled');
 	    	 $("#licenseCoLinkId").attr('disabled', 'disabled');
 	     }	
	    /*{
	    	var path='/IOES';
	    	populateStore(path);
	    	var index=document.getElementById("txtStore").selectedIndex;
	    	if(index < 2){*/
    	        
	    		//document.getElementById('txtStore').value=storeName;
	    		//$("#txtStoreText").val(storeNameVal);
	    	//}
	    //} 
    }
}
function setPODetailDepOnChange(){
	var poNo = $("#txtPODetailNo").val();
	if(1==additionalNodeFlagCheck() && $("#hdnHardwareInfo").val()==1 && poNo==0){
		$("#licenseCo").attr('disabled', 'disabled');
		$("#licenseCoLinkId").attr('disabled', 'disabled');
		$("#legalEntityTXTLinkId").attr('disabled', 'disabled');
	}else if(!(poNo==0 && (hdnChangeTypeId==5 && changeSubTypeID==5) || (hdnChangeTypeId==4 && changeSubTypeID==12))){
		setBillingLevelNo($("#txtBillingLvl").val());
		if(editSolutionChangeOldProduct ==1 && canLegalEntityChanged()){
	   		$("#txtEntity").val("0");
	   		$("#legalEntityTXT").val("Select Legal Entity");
	    }else if(additionalNodeFlagCheck() == 1 && $("#hdnHardwareInfo").val()==1 && canLegalEntityChanged()){
	    	$("#txtEntity").val("0");
	   		$("#legalEntityTXT").val("Select Legal Entity");
	   		$("#legalEntityTXTLinkId").attr('disabled', 'disabled');
	   		$("#legalEntityTXT").attr('disabled', 'disabled');
	   		$("#txtEntity").attr('disabled', 'disabled');
		}
	}
}
function setASValuesForFetchedRecordForChange(thisEl, ui, value2, label2){
	//var label2 = ui.item.label;
	//var value2 = ui.item.value;
	if(thisEl.attr("retval")=="AUTOSUGGESTBCP"){
		if(value2 != $("#bbPrimaryBCPID").val()){
			$(thisEl).val(label2);
			$("#bbPrimaryBCPID").val(value2);
			FetchBillingDetails();
		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBCPSERVICE"){
		if(ui.item.value != $("#bbPrimaryBCPIDService").val()){
			$("#bbPrimaryBCPIDService").val(ui.item.value);
			$(thisEl).val(ui.item.label);
			FetchBillingDetailsforService();
		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTDISPATCH"){
 		if(value2 != $("#txtdispatchAddress").val()){
 			$(thisEl).val(label2);
 			$("#txtdispatchAddress").val(value2);
 			getDispatchAddress();
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTCUSTPODETAIL"){
 		if(value2 != $("#txtPODetailNo").val()){
 			getPrevCustPoNo();
 			if(checkchargevalidation()==false){
 				focusEnabled = false;
 				return false;
 			}else{
 				fnFetchEntity();
 				setPODetailDepOnChange();
 				selectPoIdAS(thisEl);
 				$("#txtPODetailNo").val(value2);
 	 			$(thisEl).val(label2);
 	 			if(canLegalEntityChanged())
 	 				resetEntity();
 	 			updateselectedCustPODetailDepValues(value2);
 			}
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTPRICUSTLOC"){//Start [006]
 		if(value2 != $("#ddPrimaryBCP").val()){
 			$(thisEl).val(label2);
 			$("#ddPrimaryBCP").val(value2);
 			FetchPriBCPDetails();
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTCP"){
    	$("#txtBillingCP").val(value2);
        $(thisEl).val(label2);		                                 
    }else if(thisEl.attr("retval")=="AUTOSUGGESTSECCUSTLOC"){
 		if(value2 != $("#ddSecondaryBCP").val()){
 			$(thisEl).val(label2);
 			$("#ddSecondaryBCP").val(value2);
 			FetchSecBCPDetails();
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTLEGALENTITY"){
 		if(value2 != $("#txtEntity").val()){
 			$("#txtEntity").val(value2);
 			$(thisEl).val(label2);		                                 
 			resetLicenseCo();
 			//resetContMonth();
 			if(null != ui && null != ui.item && null != ui.item.addParam)
 				$("#hdnISFLEFLAG").val(ui.item.addParam);
 			setLegalEntityDepValuesOnChange();
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTLICENSECO"){
 		if(value2 != $("#txtLicenceCo").val()){
 			var additionalNodeFlag1 = additionalNodeFlagCheck();
 			if(editSolutionChangeOldProduct ==1){
 	 	    	 if(document.getElementById("legalEntityTXTLinkId").disabled==true){
 	 	    		 document.getElementById("txtLicenceCo").value = gb_selectedLicense_Comp;
 	 	    		 document.getElementById("licenseCo").value = gb_selectedLicense_Text;
 	 	    	 }else{
 	 	    		$(thisEl).val(label2);	
 	 	 			$("#txtLicenceCo").val(value2);
 	 	    	 }
 	 	    	 if($("#hdnHardwareInfo").val()==1){//if Hardware
 	 	    		resetStore();
 	 	    	 } 
 	 	    }else if(additionalNodeFlag1 == 1 && $("#hdnHardwareInfo").val()==1){
 	 	    	$(thisEl).val(label2);	
	 	 		$("#txtLicenceCo").val(value2);
	 	 		$("#licenseCo").attr('disabled', 'disabled');
 				$("#licenseCoLinkId").attr('disabled', 'disabled');
	 	 		resetStore();
 	 	    }else{
 	 	    	$(thisEl).val(label2);	
	 	 		$("#txtLicenceCo").val(value2);
	 	 		if($("#hdnHardwareInfo").val()==1)
	 	 			resetStore();
 	 	    }
 			
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGFORMAT"){
		if(value2 != $("#txtBillingformat").val()){
			var hardwareEnabled = document.getElementById('hdnHardwareInfo');
			/*if(hardwareEnabled.value==1){
				getPrevBillFormat();
			}*/
			$("#txtBillingformat").val(value2);
			$(thisEl).val(label2);
			if(hardwareEnabled.value==1){
				setTypeofSale();
			}
		}
	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGMODE"){
 		if(value2 != $("#txtBillingMode").val()){
 			$("#txtBillingMode").val(value2);
 			$(thisEl).val(label2);	
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGTYPE"){
 		$(thisEl).val(label2);	
		$("#txtBillingType").val(value2);
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLTAXATION"){
 		if(value2 != $("#txtTaxation").val()){
 			$("#txtTaxation").val(value2);
 			$(thisEl).val(label2);		                                 
 			setPopulateStdReason(value2);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTREASON"){
 		$(thisEl).val(label2);	
		$("#changeReason").val(value2);
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGLEVEL"){
 		if(value2 != $("#txtBillingLvl").val()){
 			$(thisEl).val(label2);	
 			$("#txtBillingLvl").val(value2);
 			setBillingLevelNo(value2);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTCHARGENAME"){
 		if(value2 != $("#txtCName" + thisEl.attr("counterval")).val()){
 			$(thisEl).val(label2);	
 			$("#txtCName" + thisEl.attr("counterval")).val(value2);
 			if(value2 != 0)
 				$("#txtCAnnotation" + thisEl.attr("counterval")).val(label2);
 			else
 				$("#txtCAnnotation" + thisEl.attr("counterval")).val("");
 			getLineNSublineItemLbl(thisEl.attr("counterval"));
 			getTaxRate(thisEl.attr("counterval"));
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTTEXTFORM"){
 		if(value2 != $("#txtform").val()){
 			$(thisEl).val(label2);	
 			$("#txtform").val(value2);
 			refreshTaxRateForAS(path);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTSALETYPE"){
 		if(value2 != $("#txtTSale").val()){
 			$(thisEl).val(label2);	
 			$("#txtTSale").val(value2);
 			setPAandIR();
 			refreshTaxRateForAS(path);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTSTORE"){
 		if(value2 != $("#txtStore").val()){
 			$(thisEl).val(label2);	
 			$("#txtStore").val(value2);
 			refreshTaxRateForAS(path);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTHTYPE"){
 		if(value2 != $("#txtHtype").val()){
 			$(thisEl).val(label2);	
 			$("#txtHtype").val(value2);
 			refreshTaxRateForAS(path);
 		}
 	}else{//End [006]
 		focusEnabled = false;
		$(thisEl).val(label2);
		if(editSolutionChangeOldProduct==0)
 			$("#prodAttVal_"+thisEl.attr("counterval")).val(value2);
 		else
 			$("#newprodAttVal_"+thisEl.attr("counterval")).val(value2);
		fnGetDestLabelValueForLOV(thisEl.attr("retval"),label2);
	}
}

//Auto suggest Cust Location End [006]
function getChargeFrequencyChange(chargePeriod){
	var frequencyOption="<option value='-1'>Select a Frequency Type</option>";
	try{
		frequencyCombo = jsonrpc.processes.populateFrequencyType(chargePeriod);
		factorVal=new Array();
		for(z=0;z<frequencyCombo.list.length;z++){
			frequencyOption = frequencyOption + "<option value='" + frequencyCombo.list[z].frequencyID + "'>" + frequencyCombo.list[z].frequencyName + "</option>";
			factorVal[z]= frequencyCombo.list[z].factor;
		}
	}catch(e){
		alert(gb_exceptionMessage);
		showJavascriptException(e,gb_exceptionMessageShow);
		return "<option value='-1'>Select a Frequency Type</option>";
	}
	return frequencyOption;
}
function getDropDownForNewValue(attrid){
	getAutoSuggest($("#autoSuggNewProdAttVal_"+attrid));
	$("#autoSuggNewProdAttVal_"+attrid).autocomplete( "search", "" );
	$("#autoSuggNewProdAttVal_"+attrid).focus();					
}
function isValueToBeChanged_ProductConfigIdValidation(thisEl,ui){
	var l_allowValueChange=1;
	if(thisEl.attr("configVal")!=null && thisEl.attr("configVal")!=0 && $("#newprodAttVal_"+thisEl.attr("counterval")).val()!=ui.item.value){
		var compLength = 0;
		var chargeLength = 0;
		if(document.getElementById('hdnComponentInfo').value==1 && !componentDetailsFetched){
			var jsComponentData ={
					serviceProductID:serviceProductID,
					serviceId:servicesID
			};
			componentslists = jsonrpc.processes.populateComponentsDetails(jsComponentData);
			compLength = componentslists.list.length;
		}else if(document.getElementById('hdnComponentInfo').value==1 && componentDetailsFetched){
			compLength = document.getElementsByName('chkComponents').length;
		}
		if(document.getElementById('hdnBillingInfo').value==1 && !billingDetailsFetched){
			getBillingInfo();
		    chargeLength=document.getElementsByName('chkSelectChargeDetail').length;
		}else if(document.getElementById('hdnBillingInfo').value==1 && billingDetailsFetched){
			chargeLength=document.getElementsByName('chkSelectChargeDetail').length;
		}
		if( loadUpdateConfigIfApplicable(thisEl.attr("retval"),ui.item.label,ui.item.value,'CVPC','AS', compLength, chargeLength)==false){
			l_allowValueChange=0;
		}
 	}
 	return l_allowValueChange;
}
function onChangeofServiceSummaryAttrValue(editSolutionChangeOldProduct,thisEl,ui){
	if(editSolutionChangeOldProduct==0)
        $("#prodAttVal_"+thisEl.attr("counterval")).val(ui.item.value);
	else
 		$("#newprodAttVal_"+thisEl.attr("counterval")).val(ui.item.value);
	$(thisEl).val(ui.item.label);
	fnGetDestLabelValueForLOV(thisEl.attr("retval"),ui.item.label);
}
//TRNG22032013026 added by manisha start
function viewserviceproductattributes() 
{
      if(serviceNo == "")
      {
        alert('Please Select Service First');
      }
      
      else
      {
      	callerWindowObj.document.getElementById('hdnserviceid').value=serviceNo;
		var path = "<%=request.getContextPath()%>/NewOrderAction.do?method=getTProductAttDetail&hdnserviceid="+serviceNo+'&hdnserviceTypeId='+serviceid+"&isView2=1&pc=1";		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
		//window.open(path);
	 }
	 
}
//TRNG22032013026 added by manisha end
//bcp details for services ADDED BY MANISHA START
	/*function getDropDownBillingforService(){
		$("#txtBCPAddressSuggestService").autocomplete( "search", "" );
		$("#txtBCPAddressSuggestService").focus();

	}*/
	//bcp details for services ADDED BY MANISHA end
//[001]	
function canLegalEntityChanged(){
	if($("#legalEntityTXT").is(":disabled"))
		return false;
	else
		return true;
}	
//[001]		