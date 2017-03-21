//[012]	 Rohit Verma	 						    Customer Billing Experence--Making Payment Term editable for hardware and Service-BFR13
//[013] Saurabh Singh 	Code done for fixing defect MASDB00194471 
//[014]	Santosh.S	    29-Nov-13   CSR-IT-09463   	2 New Methods added for Displaying and Savinf Advance Payment against Line item
//[015] VIPIN SAHARIA 04-06-2014 Added hidden field for FxChargeID required for extra logic DC_COLO_SERVICE_TAX Charge (Hardware DC) 
//[017] Vipin Saharia	27-Aug-14	GDB Lic Comp. segregation changed if condition for new licence company
//[018] Vipin Saharia 14-jan-2015 added undefined chk as creating problem at view prod catalog
//[019]Pankaj Thakur  10-april-2015 added a condition to make View Product Catelog page non-editable  in case of viewMode
//[020] Raveendra Kumar  15-May-2015 20141219-R2-020936-Billing Efficiency Program      Annotation length in IB2B
//[0021] Gunjan Singla 25-Apr-2015   20150202-R2-021037    Suppression of creation of new billable account in case of change order
//[022] Priya Gupta	2-Mar-2015 Save the value of LOC Date during Loading
var gb_sessionid;
var integer_only_warned = false;
var productCatalogueType="NEW";
var focusEnabled = true;
var lineType = "EXISTING";
function checkchargevalidation(){
	var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
	if(chkChargeLine.length>0){
		//Puneet commenting for auto suggest
		//document.getElementById("txtPODetailNo").selectedIndex=vPrevCustPoNo;
		document.getElementById('txtPODetailNo').value=vPrevCustPoValue;
		document.getElementById('poDetailNo').value=vPrevCustPoNo;
		alert('please delete All the Charges first');
	  	return false;
	}
}

function getTip(value){	
	Tip("<Table border=0> "+ value +"   </table>");	
}
function getTip_DD(obj,value,objName){	
	var SelectedValue;
	var selIndex = value;
	var myForm=document.getElementById('productCatelog');
	SelectedValue=document.getElementById(objName).options[selIndex].text	
	var combo = document.getElementById(objName);
	combo.title = SelectedValue;
}

function fnCheckAll(){
	var myForm=document.getElementById('tableComponents');	
    if(componentCheckCount==0)
    {
    	componentCheckCount=myForm.rows.length-2;
    }
    var rowlen= myForm.rows.length-2;
    alert("rowlen :"+rowlen)
    if(rowlen==1)
	{
		alert("please add one row to delete");
		document.getElementById('SelectAllChck').checked=false;
		return false;
	}
	
	if(document.getElementById('SelectAllChck').checked==true)
	{
		
		 for (i =0; i<componentCheckCount; i++)
		 {
		 	if(document.getElementById('chkComponents'+i)!=null)
		 		document.getElementById('chkComponents'+i).checked=true;
		 	/*else
		 	{ 
		 		var j=i+1;
		 		if(document.getElementById('chkComponents'+j)!=null)
		 		document.getElementById('chkComponents'+j).checked=true;
		 	}*/
		 		
		 }
		
	}
	else
	{
		for (i =0; i<componentCheckCount; i++)
		 {
		 	if(document.getElementById('chkComponents'+i)!=null)
			 	document.getElementById('chkComponents'+i).checked=false;
			 /*else
			 {
			 	var j=i+1; 
			 	if(document.getElementById('chkComponents'+j)!=null)
		 		document.getElementById('chkComponents'+j).checked=false;
		 	 }*/
		 }
	}	
}
function changeEndDays(index){
	if(document.getElementById('txtCEndDate'+index).value=='TD'){
		  document.getElementById('txtEndDays'+index).value=0;
		  document.getElementById('txtEndMonth'+index).value=0;
		}
}

function resetComponentsRows() {
			var mytable = document.getElementById('tableComponents');	
			var iCountRows = document.getElementById('tableComponents').rows.length;
	for (i =iCountRows-1; i >=0; i--){	
			       mytable.deleteRow(i);
			   } 
			document.getElementById('hdnComponentsCount').value =0;
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
	//Puneet - commenting for auto suggest
	//vPrevCustPoNo=var_custpono;
	vPrevCustPoValue=document.getElementById('txtPODetailNo').value;
	vPrevCustPoNo=document.getElementById('poDetailNo').value;
}
function fillEndDateLogic(counter){
	var endDateLogic=document.getElementById('txtCEndDate'+counter).value;	
	var endDateLogicCombo=document.getElementById('txtCEndDate'+counter);		   
	var index=endDateLogicCombo.selectedIndex;
	var comboLength=endDateLogicCombo.length;
	
	for (i = comboLength+1; i >0 ; i--){
	       endDateLogicCombo.remove(1);
	}
	
	if(document.getElementById('ddCType'+counter).value==2){
	    var option = document.createElement("option");
	   	option.text = 'One Time Charge';
		option.value = 'OT';
		option.title = 'One Time Charge';
		document.getElementById("txtCEndDate"+counter).selectedIndex=1;
		try{
			endDateLogicCombo.add(option, null); //Standard
		}catch(error){
			endDateLogicCombo.add(option); // IE only
		}
	}else{
	    {
	    	var option = document.createElement("option");
	   		option.text = 'Billing Trigger Date';
			option.value = 'BTD';
			option.title = 'Billing Trigger Date';
			try{
				endDateLogicCombo.add(option, null); //Standard
			}catch(error){
				endDateLogicCombo.add(option); // IE only
			}
	    }
	    {
	    	var option = document.createElement("option");
	   		option.text = 'Till Disconnection';
			option.value = 'TD';
			option.title = 'Till Disconnection';
			try{
				endDateLogicCombo.add(option, null); //Standard
			}catch(error){
				endDateLogicCombo.add(option); // IE only
			}
			document.getElementById("txtCEndDate"+counter).selectedIndex=2;
	    }
	}    
}

		
/*Puneet commenting the function as reason is converted to auto suggest
function populateReasonForChange(){
	var tr, td, i, j, oneRecord;
    var test;
    var interFaceStdReason=1;
    var combo = document.getElementById("changeReason");	
    var iCountRows = combo.length;
    for (i = 1; i <  iCountRows; i++){
    	combo.remove(i);
    }
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
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
// *******************For Taxation Exemption Filling Standard Reason  END*************************
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
				/*for(i=0;i<chkChargeLine.length;i++)
				{
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
				 	for(j=1;j<=countAttributes;j++)
				 	{ //alert('DB att value = '+document.getElementById('hdnProdAttVal_'+j).value)
							if(document.getElementById('prodExpVal_'+j).value != "DROPDOWN" && document.getElementById('prodExpVal_'+j).value != "LOV" && values[i] == document.getElementById('hdnProdAttVal_'+j).value){							
								document.getElementById('prodAttVal_'+j).value = totalAmount;			
								break;
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

function checkTotalAmount()
{
		var chkChargeLine;
		var totalCPeriod=0;
		chkChargeLine=document.getElementsByName('chkSelectChargeDetail')
			
			for(i=0;i<chkChargeLine.length;i++)
			{
				var chkboxCharge=chkChargeLine[i];
				var index=chkboxCharge.value;
				
				if(document.getElementById("ddCType"+index).value==1)
				{
				
					if(document.getElementById("txtCEndDate"+index).value=="BTD")
					{
						if(parseInt(document.getElementById("txtEndMonth"+index).value) < parseInt(document.getElementById("txtStartMonth"+index).value))
						{	
						    var lineno=index-1;
							alert('End Date Month should be greater than Start Date Month in Line No:' + lineno);
							return false;
				        }
				   }
				 }  
				
				totalCPeriod = parseFloat(totalCPeriod) + parseFloat(document.getElementById("txtCPeriod"+index).value);
			}		
			if(parseFloat(document.getElementById("txtCPeriod"+index).value)>parseFloat(document.getElementById("txtCntrtMonths").value))
			{
				alert('Charge Period should not be greater than Contract Period');
				return false;
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
	ctrlArry[14] =  myForm.poDetailNo;
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
	//[003]start
	ctrlArry[46] =  myForm.txtDispatchContactName;
	ctrlArry[47] =  myForm.legalEntityTXT;
	ctrlArry[48] =  myForm.billingFormat;
	ctrlArry[49] =  myForm.billingMode;
	ctrlArry[50] =  myForm.licenseCo;
	ctrlArry[51] =  myForm.taxationID;
	ctrlArry[52] =  myForm.txtReason;
	ctrlArry[53] =  myForm.billingLevel;
	ctrlArry[54] =  myForm.txtStoreText;
	ctrlArry[55] =  myForm.hTypeText;
	ctrlArry[56] =  myForm.textFormTXT;
	ctrlArry[57] =  myForm.textSaleNature;
	ctrlArry[58] =  myForm.textSaleType;
	ctrlArry[59] =  myForm.billingType;
	ctrlArry[60] =  myForm.billingCP;
	ctrlArry[61] =  document.getElementById('poDetailNoTXTLinkId');//myForm.poDetailNoTXTLinkId;
	ctrlArry[62] =  document.getElementById('billingCPTXTLinkId');//myForm.billingCPTXTLinkId;
	ctrlArry[63] =  document.getElementById('textStoreLinkId');//myForm.textStoreLinkId;
	ctrlArry[64] =  document.getElementById('hTypeLinkId');//myForm.hTypeLinkId;/
	ctrlArry[65] =  document.getElementById('textFormTXTLinkId');//myForm.textFormTXTLinkId;
	ctrlArry[66] =  document.getElementById('textSaleNatureLinkId');//myForm.textSaleNatureLinkId;
	ctrlArry[67] =  document.getElementById('saleTypeTXTLinkId');//myForm.saleTypeTXTLinkId;
	ctrlArry[68] =  document.getElementById('billingLevelLinkId');//myForm.billingLevelLinkId;
	ctrlArry[69] =  document.getElementById('reasonAutoSuggestLinkId');//myForm.reasonAutoSuggestLinkId;
	ctrlArry[70] =  document.getElementById('taxationAutoSuggestLinkId');//myForm.taxationAutoSuggestLinkId;
	ctrlArry[71] =  document.getElementById('billingTypeLinkId');//myForm.billingTypeLinkId;
	ctrlArry[72] =  document.getElementById('licenseCoLinkId');//myForm.licenseCoLinkId;
	ctrlArry[73] =  document.getElementById('billingFormatTXTLinkId');//myForm.billingFormatTXTLinkId;
	ctrlArry[74] =  document.getElementById('legalEntityTXTLinkId');//myForm.legalEntityTXTLinkId;
	ctrlArry[75] =  document.getElementById('billingModeTXTLinkId');
	// WARRANTY CLAUSE ADDED BY MANISHA START 
	ctrlArry[76] =  myForm.txtWarrantClause;
	// WARRANTY CLAUSE ADDED BY MANISHA end
	//bcp details for services ADDED BY MANISHA START
	ctrlArry[77] =  myForm.bbPrimaryBCPService;
	//bcp details for services ADDED BY MANISHA end
	//ctrlArry[76] =  myForm.poDetailNo;
	//satyapan By nagarjuna
	ctrlArry[78] =  myForm.txtOSPTagging;
	ctrlArry[79] =  myForm.txtOSPRegNo;
	ctrlArry[80] =  myForm.txtOSPRegDate;
	
	//satyapan By Nagarjuna
	//[003]end
	var callerWindowObj = dialogArguments;
	fieldRoleMappingValidation(callerWindowObj.linesTabFieldList,ctrlArry);
}

function setServiceSummaryAttributes(){
	var callerWindowObj = dialogArguments;
	if(callerWindowObj.serviceSummaryList.list[0].feildName=="ServiceSummary"){
		if(callerWindowObj.serviceSummaryList.list[0].isReadOnly=="1"){
					isServiceReadOnly="1";
		}
	}
}
	
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
	   // var comboSaleType = document.getElementById("txtTSale");
		document.getElementById('txtTSale').value="0";
		document.getElementById('textSaleType').value="Select Type Of Sale";
		if(null != $('#textSaleType').data() && null != $('#textSaleType').data().autocomplete)
			$('#textSaleType').data().autocomplete.term = null;
	    //Puneet commenting as it is converted to auto suggest
	    /*removeAllOptions(document.getElementById("txtTSale"));
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
        var saleType = 0;
        saleType= jsonrpc.processes.typeOfSale(billFormat);
       
        for(j=0;j<saleType.list.length;j++){ 
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
	}
}
//====================================================================================================
//---------------------------------Added For Field Level Validations----------------------------------
//====================================================================================================

function fnCalculateHardwareEndDate(startDate,period)
{		
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
function calculateFreqAmt()
{
	if(document.getElementById("txtCFrequency").value!=-1)
	{
		if(document.getElementById("txtCTAmt").value!="")
		{
			var frqAmount=0;
			var annualAmount=0;
			var factor=factorVal[document.getElementById("txtCFrequency").selectedIndex-1];
			var Cperiod=document.getElementById("txtCPeriod").value;
			var TotalAmount=document.getElementById("txtCTAmt").value;
			frqAmount=((TotalAmount/Cperiod)*factor).toFixed(2);
			annualAmount=(frqAmount*12/factor);
			document.getElementById("txtCFreqAmt").value=Math.round(frqAmount);
			document.getElementById("txtAnnualCharge").value=Math.round(annualAmount);
		}
		else
		{
			alert("Please input Total Amount");
			document.getElementById("txtCFrequency").value=-1;
			return false;
		}
	}
}	
//Puneet for performance tuning
function getServiceSummary(){
	var hdnServiceSummary = document.getElementById('hdnServiceSummary');
	if(serviceSummaryFetched || hdnServiceSummary.value!=1){
		return;
	}
	document.getElementById('serviceType').style.display='block';
	//var tr, td, i, j, oneRecord;
    var test,test1,linkageInfoList;
   //FOR SERVICE LIST STARTS
   //Puneet - for performance tuning
   /*var mytable = document.getElementById('ServiceList');
   var iCountRows = mytable.rows.length;
  
   for (i = 0; i <  iCountRows; i++){
       mytable.deleteRow(0);
   }*/
   var mytableDiv = document.getElementById('ServiceListDiv');
   mytableDiv.innerHTML="";
   var callerWindowObj = dialogArguments;
   var order = callerWindowObj.document.getElementById('poNumber').value;
   var ordertype = "New";
   var jsData =	{
		serviceDetailID:serviceDetailID,
		serviceProductID:serviceProductID,
		poNumber:order,
		orderType:ordertype
	};
	var linakageUpdateFlag="NEW_PRODUCT";
	linkageInfoList = jsonrpc1.processes.populateLinkageDetails(jsData,sessionid,linakageUpdateFlag);
	if(linkageInfoList.list.length != 0 ){
		document.getElementById('eLogicalCircuitId').value=linkageInfoList.list[0].logicalCircuitId;
    	document.getElementById('InfraOderNo').value=linkageInfoList.list[0].infraOderNo;
    	document.getElementById('metasolvCircuitId').value=linkageInfoList.list[0].metasolvCircuitId;
    }
	 //jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
    test = jsonrpc.processes.populateServiceAttMasterValue(jsData,sessionid);
    if(test.list.length==0 && linkageInfoList.list.length==0){
    	document.getElementById('lineItemServiceSummary').style.display='none';
    }else{
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
    	var productAttClassName="";
    	var productAttDisabled="false";
    	if(!(isView ==null || isView == 'null')){
    		productAttClassName="inputDisabled";
    		productAttDisabled='true';
			document.getElementById('eLogicalCircuitId').className="inputDisabled";
    	   	document.getElementById('InfraOderNo').className="inputDisabled";
    	   	document.getElementById('metasolvCircuitId').className="inputDisabled";
    	}
    	//Puneet creating an array of dropdown and lov to fetch the dependent values
    	var dropDownLovIDLabels=new Array();
    	var dropDownLovIDLabelIndex = 0;
    	//Puneet commenting for performance tuning
    	var parentCounter=0;
    	for (i = 0 ; i < test.list.length; i++,counter++){
    		str="";
    		
    		//var tblRow=document.getElementById('ServiceList').insertRow();
    		/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.width="41%"
			tblcol.vAlign="top";
			tblcol.style.fontSize="12px";
			tblcol.style.fontWeight="bold";*/
    		
    		serviceListTable = serviceListTable + tableRowStart;
    		
    		tableDataStart = "<td align = 'left' width='41%' vAlign='top' style.fontSize='12px' style.fontWeight='bold'>";
			if(test.list[i].prodAttributeLabel == 'B0.HW CircuitID'){
				str = "<label><center>label</center></label></br>";
				str=str + test.list[i].prodAttributeLabel;
			}else if(test.list[i].prodAttributeLabel == 'C1.HW Circuit ID'){
				str = "<label><center>label</center></label></br>";
				str=str + test.list[i].prodAttributeLabel;
			}else 
				str= test.list[i].prodAttributeLabel;								
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
			
			if(test.list[i].prodExpectedValue=='DROPDOWN'){
			    /*var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";*/
				//[002]start
				tableDataStart = "<td align = 'left' vAlign='top'>";
				
				//Puneet commenting for performance tuning
				//document.getElementById("prodAttVal_"+counter).value=test.list[i].newProdAttVal;//Puneet for performance tuning
				var autoSuggProdAttVal;
				if(test.list[i].newProdAttVal==0){//Puneet for performance tuning
					//Puneet commenting the code as assigning the value directly
					//document.getElementById("autoSuggProdAttVal_"+counter).value="";//Puneet for performance tuning
					autoSuggProdAttVal = "";
				}else{
					autoSuggProdAttVal = test.list[i].destText;
					//Puneet for performance tuning
					//for(j=0;j<test.list[i].serviceSummary.list.length;j++){
						/*var combo = document.getElementById("prodAttVal_"+counter);
						var option = document.createElement("option");
						option.text = test.list[i].serviceSummary.list[j].serviceSummaryText;
						option.value = test.list[i].serviceSummary.list[j].serviceSummaryValues;
						option.title = test.list[i].serviceSummary.list[j].serviceSummaryText;
						try{
							combo.add(option, null); //Standard
						}catch(error){
							combo.add(option); // IE only
						}
						if(test.list[i].serviceSummary.list[j].serviceSummaryText=="Upgrade Order" || test.list[i].serviceSummary.list[j].serviceSummaryText=="Downgrade Order" || test.list[i].serviceSummary.list[j].serviceSummaryText=="Disconnection Order"|| test.list[i].serviceSummary.list[j].serviceSummaryText=="Temporary disconnection" || test.list[i].serviceSummary.list[j].serviceSummaryText=="Reconnection"){
							combo.remove(i);
						}
						if(test.list[i].serviceSummary.list[j].serviceSummaryText=="Resumption" || test.list[i].serviceSummary.list[j].serviceSummaryText=="Suspension" || test.list[i].serviceSummary.list[j].serviceSummaryText=="Upgrade" || test.list[i].serviceSummary.list[j].serviceSummaryText=="Disconnection" || test.list[i].serviceSummary.list[j].serviceSummaryText=="Downgrade"){
							combo.remove(1);
						}
						document.getElementById("prodAttVal_"+counter).value=test.list[i].newProdAttVal;*/
						//Puneet - taking it up for performance tuning
						//document.getElementById("prodAttVal_"+counter).value=test.list[i].newProdAttVal;
						//if(test.list[i].newProdAttVal==test.list[i].serviceSummary.list[j].serviceSummaryValues){
							/* Puneet taking it above for loop for performance tuning
							   if(test.list[i].newProdAttVal==0){
								document.getElementById("autoSuggProdAttVal_"+counter).value="";
							}else{*/
								//Puneet commenting the code as assigning the value directly
								//document.getElementById("autoSuggProdAttVal_"+counter).value=test.list[i].serviceSummary.list[j].serviceSummaryText;
								//autoSuggProdAttVal = test.list[i].serviceSummary.list[j].serviceSummaryText;
								//break;
							//}
						//}	
					//}
				}
				
				if(test.list[i].isServiceSummReadonly==1){
						str="<input type='hidden' value = '" + test.list[i].newProdAttVal + "' isSummaryReq='0' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"'  id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0' ><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()'  disabled='disabled' class='dropdownMargin1' readonly='true' style='width:180px' counterval='"+ counter +"' readonly='true'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' retval='"+ test.list[i].prodAttributeID +"' value='" + autoSuggProdAttVal + "'  id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' /> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
				}else{
					if(test.list[i].isServiceSummMandatory==1){
						//str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' onchange='fnGetDestLabelValue("+test.list[i].prodAttributeID+",this)' class='dropdownMargin' style='width:250px' isSummaryReq='1' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' ></select> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						str="<input type='hidden' value = '" + test.list[i].newProdAttVal + "' isSummaryReq='1' class='dropdownMargin' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:180px' readonly='true' class='dropdownMargin' readonly='true' counterval='"+ counter +"'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"'  value='" + autoSuggProdAttVal + "' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
					}else{
						//str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' onchange='fnGetDestLabelValue("+test.list[i].prodAttributeID+",this)'  class='dropdownMargin1' style='width:250px' isSummaryReq='0' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' ></select> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						str="<input type='hidden' value = '" + test.list[i].newProdAttVal + "' isSummaryReq='0' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0' ><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='dropdownMargin1' readonly='true' style='width:180px' counterval='"+ counter +"' readonly='true'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"'  value='" + autoSuggProdAttVal + "' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "' /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
					}
				}
				//[002]end	
				/*CellContents = str;
				tblcol.innerHTML = CellContents;*/
				//Puneet for commercial attributes
				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				if(test.list[i].isParentAtt==1){
					parentCounter=counter;//if parent then set the parent counter as current counter
				}else if(test.list[i].parntAttId!=0){
					parentCounter=parentCounter;//if child then set parent counter as parent counter
				}else{
					parentCounter=0;
				}
				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
				dropDownLovIDLabels[dropDownLovIDLabelIndex] =  test.list[i].prodAttributeID + "-" + autoSuggProdAttVal;
				dropDownLovIDLabelIndex++;
			}//added LOV condition for autosuggestion in service summary section  by Lawkush Start
			else if(test.list[i].prodExpectedValue=='LOV'){
			    /* Puneet commenting for performance tuning
			    var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.vAlign="top";*/
				tableDataStart = "<td align = 'left' vAlign='top'>";
				var autoSuggProdAttVal;
				if(test.list[i].newProdAttVal==0){//Puneet taken it outside for loop for performance 
					//Puneet commenting the code as assigning directly
					//document.getElementById("autoSuggProdAttVal_"+counter).value="";//Puneet taken it outside for loop for performance
					autoSuggProdAttVal="";
				}else{
					autoSuggProdAttVal = test.list[i].destText;
					//Puneet taken it outside for loop for performance
					/*Puneet commenting for performance tuning as drop down value is fetched directly
					 for(j=0;j<test.list[i].serviceSummaryLov.list.length;j++){
						//Puneet commenting for performance tuning
						//document.getElementById("prodAttVal_"+counter).value=test.list[i].newProdAttVal; 
						if(test.list[i].newProdAttVal==test.list[i].serviceSummaryLov.list[j].serviceSummaryValues){
							//Puneet commenting for performance tuning
							/*if(test.list[i].newProdAttVal==0){
								document.getElementById("autoSuggProdAttVal_"+counter).value="";
							}else{
								//Puneet commenting as assigning the value directly
								//document.getElementById("autoSuggProdAttVal_"+counter).value=test.list[i].serviceSummaryLov.list[j].serviceSummaryText;
								autoSuggProdAttVal=test.list[i].serviceSummaryLov.list[j].serviceSummaryText;
								break;
							//}
						}				
					}*/	
				}
				
				//[002]start
				if(test.list[i].isServiceSummReadonly==1){
					str="<input type='hidden' value = '" + test.list[i].newProdAttVal + "' isSummaryReq='0' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0' ><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' disabled='disabled' class='dropdownMargin1' readonly='true' style='width:180px' counterval='"+ counter +"'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' value='" + autoSuggProdAttVal + "' retval='"+ test.list[i].prodAttributeID +"' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "'   /> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}else{
					if(test.list[i].isServiceSummMandatory==1){
						//str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' onchange='fnGetDestLabelValue("+test.list[i].prodAttributeID+",this)' class='dropdownMargin' style='width:250px' isSummaryReq='1' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' ></select> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						str="<input type='hidden' value = '" + test.list[i].newProdAttVal + "' isSummaryReq='1' class='dropdownMargin' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0'><input type='text'  onmouseover='getTip(value)' onmouseout='UnTip()' style='width:180px' class='dropdownMargin' counterval='"+ counter +"'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"'  value='" + autoSuggProdAttVal + "' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "'  /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					}else{
						// str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' onchange='fnGetDestLabelValueForLOV("+test.list[i].prodAttributeID+",this)'  class='dropdownMargin1' style='width:250px' isSummaryReq='0' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' ></select> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						str="<input type='hidden' value = '" + test.list[i].newProdAttVal + "' isSummaryReq='0' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' name='prodAttVal_" + counter + "' value='0' ><input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='dropdownMargin1' style='width:180px' counterval='"+ counter +"'  ctrltype='dropdown' tabType='servSumm' configVal='"+ test.list[i].attMasterId+"' retval='"+ test.list[i].prodAttributeID +"' parentCounter='"+parentCounter+"' parntAttId='"+test.list[i].parntAttId+"' isParentAtt='"+test.list[i].isParentAtt+"' value='" + autoSuggProdAttVal + "' id='autoSuggProdAttVal_" + counter + "' name ='autoSuggProdAttVal_" + counter + "'   /><a id='autoSuggProdAttLink_" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDown(" + counter + ")'>Show</a> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
					}
				}
				/* Puneet commenting for performance tuning
				CellContents = str;
				tblcol.innerHTML = CellContents;*/
				//Puneet for commercial attributes
				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
				dropDownLovIDLabels[dropDownLovIDLabelIndex] =  test.list[i].prodAttributeID + "-" + autoSuggProdAttVal;
				dropDownLovIDLabelIndex++;
				//Puneet adding it to string
				//document.getElementById("prodAttVal_"+counter).value=test.list[i].newProdAttVal;//Puneet taken it outside for loop for performance
				if(test.list[i].isParentAtt==1){
					parentCounter=counter;//if parent then set the parent counter as current counter
				}else if(test.list[i].parntAttId!=0){
					parentCounter=parentCounter;//if child then set parent counter as parent counter
				}else{
					parentCounter=0;
				}
			}
			//added LOV condition for autosuggestion in service summary section  by Lawkush End
			else if(test.list[i].prodExpectedValue=='LINK'){
				/*Puneet commenting for performance tuning
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.width="59%";
				tblcol.vAlign="top";*/
				tableDataStart = "<td align = 'left' vAlign='top' width='59%'>";
				if(test.list[i].isServiceSummMandatory==1){
					str="<input onmouseover='getTip(value)' value='"+test.list[i].newProdAttVal+"' onmouseout='UnTip()' type='text' isSummaryReq='1' readOnly ='true' class='inputBorder1' style='width:150px' name='prodAttVal_" + counter + "' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' islinkType='1'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
					str=str+"<div class='searchBg1'><a href='#' title='Select LSI No' id='serviceSummaryLink_" + counter + "' onClick='return popUpServiceSummary("+test.list[i].prodAttributeID+","+counter+",\""+test.list[i].linkPopUpId+"\")'  >...</a></div><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}else{
					str="<input onmouseover='getTip(value)' value='"+test.list[i].newProdAttVal+"' onmouseout='UnTip()' type='text' isSummaryReq='1' readOnly ='true' class='inputBorder2' style='width:150px' name='prodAttVal_" + counter + "' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' islinkType='1'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
					str=str+"<div class='searchBg1'><a href='#' title='Select LSI No' id='serviceSummaryLink_" + counter + "' onClick='return popUpServiceSummary("+test.list[i].prodAttributeID+","+counter+",\""+test.list[i].linkPopUpId+"\")'  >...</a></div><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
				}
				/*Puneet commenting for performance tuning
				CellContents = str;
				tblcol.innerHTML = CellContents;*/
				//Puneet for commercial attributes
				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
			}else if(test.list[i].prodExpectedValue == 'datetime'){
				/*Puneet commenting for performance tuning
				var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.width="59%"
				tblcol.vAlign="top";*/
				tableDataStart = "<td align = 'left' vAlign='top' width='59%'>";
				//strA = "javascript: if(\""+ test.list[i].prodExpectedValue +"\"==\"numeric\".toUpperCase()){if(this.value.length > 0){return checknumber(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"datetime\"){if(this.value.length > 0){return checkdate(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"email\"){if(this.value.length > 0){return validateEmail(this)}} else if(\""+ test.list[i].prodExpectedValue +"\"==\"varchar\".toUpperCase()){if(this.value.length > 0){return ValidateTextFieldM6(this,pathM6)}}";
				strA="javascript: return false";
				if(test.list[i].isServiceSummMandatory==1){
					if(test.list[i].newProdAttVal=="null" || test.list[i].newProdAttVal=="0" )
						str="<input type='text' readOnly ='true' isSummaryReq='1'  value='' class='inputBorder1' name='prodAttVal_'" + counter + " className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y' ><img src='" + requestContextPath + "/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
					else
						str="<input type='text' readOnly ='true' isSummaryReq='1'  value='' class='inputBorder1' name='prodAttVal_'" + counter + " className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'><img src='" + requestContextPath + "/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
				}else{
					if(test.list[i].attServiceName=="SaaS"){
						if(test.list[i].newProdAttVal=="null" || test.list[i].newProdAttVal=="0" )
							str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' readOnly ='false'  value='' style='width:235px' class='inputBorder2' name='prodAttVal_" + counter + "' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <img src='" + requestContextPath + "/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
						else
							str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' readOnly ='false'  value='" + test.list[i].newProdAttVal + "' style='width:235px' class='inputBorder2' name='prodAttVal_" + counter + "' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <img src='" + requestContextPath + "/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
					}else{
						//str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly ='true'  isSummaryReq='0'  value='" + test.list[i].newProdAttVal + "'  class='inputBorder2' name='prodAttVal_" + counter + "'  id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'><img src='<%=request.getContextPath()%>/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_" + counter + "'  id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_" + counter + "'  id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_" + counter + "'  id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_" + counter + "'  id='prodAttMandatory_" + counter + "'>";
						
						if(test.list[i].isServiceSummReadonly==1) //Added  by Deepak Kumar to disable date image link if it is readonly
						{
							
							str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly ='true' isSummaryReq='0'  value='" + test.list[i].newProdAttVal + "' class='inputBorder2' name='prodAttVal_" + counter + "' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'><img src='" + requestContextPath + "/images/cal.gif' border='0px' alt='Loading...' disabled='true' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_" + counter + "' id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_" + counter + "'  id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_" + counter + "'  id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
						}
						else
						{
							str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' readOnly ='true' isSummaryReq='0'  value='" + test.list[i].newProdAttVal + "' class='inputBorder2' name='prodAttVal_" + counter + "' className='" + productAttClassName + "' disabled= '" + productAttDisabled +"' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'><img src='" + requestContextPath + "/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.prodAttVal_" + counter + ");\" style='vertical-align: bottom'/></font> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_" + counter + "' id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_" + counter + "'  id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_" + counter + "'  id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
						}
					}
				}
				/*Puneet commenting for performance tuning
				CellContents = str;
				tblcol.innerHTML = CellContents;*/
				//Puneet for commercial attributes
				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
			}else{
				/*Puneet commenting for performance tuning
			 	var tblcol=tblRow.insertCell();
				tblcol.align = "left";
				tblcol.width="59%"
				tblcol.vAlign="top";*/
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
				//strA="javascript: return false";
				//strA = "javascript: if(\""+ test.list[i].prodExpectedValue +"\"==\"numeric\".toUpperCase()){if(this.value.length > 0){return checknumber(this)}}";
				if(test.list[i].isServiceSummReadonly==1){
					str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0'  dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' disabled='disabled' readonly='true'  name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
				}else{
					if(test.list[i].isServiceSummMandatory==1){
						if(test.list[i].prodAttributeID=="1041")
							str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='1' dmxtotal='1' value='" + test.list[i].newProdAttVal + "' class='inputBorder1' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_" + counter + "' id='prodAttMaxLength_" + counter + "'>";
						else{
							if(test.list[i].prodAttributeID == "2470"){
								str="<label><center><bold>SUPPLIER</bold></center></label></br><input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' value='"+test.list[i].defValue+"' onmouseout='UnTip()' type='text' isSummaryReq='1' dmxtotal='0' class='inputBorder1' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
							}else if(test.list[i].prodAttributeID == "2476"){
								str="<label><center><bold>BROADCASTER</bold></center></label></br><input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' value='"+test.list[i].defValue+"' onmouseout='UnTip()' type='text' isSummaryReq='1' dmxtotal='0' class='inputBorder1' style='width:235px' name='prodAttVal_" + counter + "' id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'>";
							}
							else{//Puneet un commented as below string assignment will always perform irrespective of above conditions
								str="<input onmouseover='getTip(value)' onkeyup='copyTextValue(this.value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='1' dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder1' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"'  zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
							}
						}
					}else{
						if(test.list[i].prodAttributeID=="1041")
							str="<input onmouseover='getTip(value)' onkeyup='copyTextValue(this.value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0' dmxtotal='1' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
						else{
							if(test.list[i].prodAttributeID == "2470"){							
								str="<label><center><bold>SUPPLIER</bold></center></label></br><input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0' dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
							}else if(test.list[i].prodAttributeID == "2476"){						
								str="<label><center>BROADCASTER</center></label></br><input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0' dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
							}else{	
								str="<input onkeyup='copyTextValue(this.value)' onmouseover='getTip(value)' onmouseout='UnTip()' type='text' onkeyup='CheckMaxLength(this, "+test.list[i].prodAttMaxLength+")' maxlength='"+test.list[i].prodAttMaxLength+"' isSummaryReq='0' dmxtotal='0' value='" + test.list[i].newProdAttVal + "' class='inputBorder2' name='prodAttVal_'" + counter + " id='prodAttVal_" + counter + "' onBlur='"+ strA +"' zeroValueAllowed='Y'> <input type='hidden' value='"+test.list[i].prodAttributeID+"' name='hdnProdAttVal_'" + counter + " id='hdnProdAttVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodExpectedValue+"' name='prodExpVal_'" + counter + " id='prodExpVal_" + counter + "'> <input type='hidden' value='"+test.list[i].prodAttributeLabel+"' name='prodAttName_'" + counter + " id='prodAttName_" + counter + "'><input type='hidden' value='"+test.list[i].mandatory+"' name='prodAttMandatory_'" + counter + " id='prodAttMandatory_" + counter + "'><input type='hidden' value='"+test.list[i].prodAttMaxLength+"' name='prodAttMaxLength_'" + counter + " id='prodAttMaxLength_" + counter + "'>";
							}
						}
					}
				}
				/*Puneet commenting for performance tuning
				CellContents = str;
				tblcol.innerHTML = CellContents;*/
				//Puneet for commercial attributes
				str= str + "<input type='hidden' value = '" + test.list[i].isCommercial + "' id='comAttr_" + counter + "' name='comAttr_" + counter + "'>";
				serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
			}
			/*Puneet commenting for performance tuning and taking it out from for loop
			 	if(!(isView ==null || isView == "null")){
				//Puneet Commenting for performance tuning
				//document.getElementById("prodAttVal_"+counter).className="inputDisabled";
				//document.getElementById("prodAttVal_"+counter).disabled='true';
				document.getElementById('eLogicalCircuitId').className="inputDisabled";
    	   		document.getElementById('InfraOderNo').className="inputDisabled";
    	   		document.getElementById('metasolvCircuitId').className="inputDisabled";
			}*/
			// added by manisha for service summary validation start defect no : 19032013001
			/*Puneet commenting for performance tuning
			var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.width="50%"
			tblcol.vAlign="top";*/
			tableDataStart = "<td align = 'left' vAlign='top' width='50%'>";
			str="<input type='hidden' class='inputBorder2' name='txt_prod_attvalidation_" + counter + "' id='txt_prod_attvalidation_" + counter + "' value='"+test.list[i].for_validation+"' readonly='true'>";
			/*Puneet commenting for performance tuning
			CellContents = str;
			tblcol.innerHTML = CellContents;*/
			serviceListTable = serviceListTable + tableDataStart + str + tableDataEnd;
			serviceListTable = serviceListTable + tableRowEnd;
			// added by manisha for service summary validation end
	    }
    	serviceListTable = serviceListTable + tableDataEnd;
    	document.getElementById("ServiceListDiv").innerHTML=serviceListTable;
    }	
    /*Puneet commenting the above code as changed the logic to pass the array of controls with the input as attaMAsterId-AttLabelValue
     for(m = 0 ; m < test.list.length; m++){
	    	if(test.list[m].prodExpectedValue=='DROPDOWN' || test.list[m].prodExpectedValue=='LOV'){
				if(document.getElementById("prodAttVal_"+m)!=null)
					fnGetDestLabelValue_onLoad(test.list[m].prodAttributeID,document.getElementById("autoSuggProdAttVal_"+(m+1)).value);
				}
	    	} */  
	    //================================================================
    //passing the array of values
    fnGetDestLabelValue_onLoad(dropDownLovIDLabels);
	//FOR SERVICE LIST ENDS
    serviceSummaryFetched = true;
    $("#ServiceListDiv").find("[ctrltype='dropdownlink']").each(function() {
		attachCSForAS($(this));
	});
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
//Puneet for performance tuning, billing details will only be fetched if user want to
//and details will only be fetched once
function getBillingInfo(){
	var hdnBillingInfo = document.getElementById("hdnBillingInfo");
	if(billingDetailsFetched || hdnBillingInfo.value!=1){
		return;
	}

	/* Puneet commenting code for auto suggest
	// *******************For Taxation Combo Filling by Ajax************************
	var tr, td, i, j, oneRecord;
	var taxationList; 
	var iCountRows = combo.length;
	for (i = 1; i <  iCountRows; i++){
	   combo.remove(1);
	}
	var comboDiv = document.getElementById("selectTaxationDiv");
	comboDiv.innerHTML = "";
	var combo = document.getElementById("taxationID");
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    taxationList = jsonrpc.processes.populateTaxationDetails();
    for(j=0;j<taxationList.list.length;j++){
	   	var option = document.createElement("option");
  		option.text = taxationList.list[j].taxationName;
		option.value = taxationList.list[j].txtTaxation;
		option.title = taxationList.list[j].taxationName;
		try{
			combo.add(option, null); //Standard
		}catch(error){
			combo.add(option); // IE only
		}
    }*/
	
	// *******************For Taxation Combo Filling by Ajax END************************
	// *******************For Billing Level Combo Filling by Ajax Start************************
	//Puneet commenting for auto suggest
	//loadBillingLevel();
    // *******************For Billing Level Combo Filling by Ajax EnD************************
    // *******************For Billing Format Combo Filling by Ajax Start************************
	//Puneet commenting for auto suggest
	//loadBillingFormat();
	// *******************For Billing Format Combo Filling by Ajax EnD************************
	// *******************For Billing Mode Combo Filling by Ajax Start************************
	//Puneet commenting for Auto suggest
 	//loadBillingMode();
 // *******************For Billing Mode Combo Filling by Ajax End************************
	// *******************For Billing Type Combo Filling by Ajax Start************************
    //Puneet commenting as billing type is converted into auto suggest
	/*var tr, td, i, j, oneRecord;
    var billingTypeList;
    var combo = document.getElementById("txtBillingType");
    var iCountRows = combo.length;
 
    for (i = 1; i <  iCountRows; i++){
       combo.remove(1);
    }

	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    billingTypeList = jsonrpc.processes.populateBillingTypeDetails();
    for(j=0;j<billingTypeList.list.length;j++){
	   	var option = document.createElement("option");
	  	option.text = billingTypeList.list[j].billingTypeName;
		option.value = billingTypeList.list[j].billingTypeId;
	  	option.title = billingTypeList.list[j].billingTypeName;
		try{
			combo.add(option, null); //Standard
		}catch(error){
			combo.add(option); // IE only
		}
   }*/
	   // *******************For Billing Type Combo Filling by Ajax EnD************************
   // *******************For Credit Period Combo Filling by Ajax Start************************
	//var tr, td, i, j, oneRecord;
	/*Puneet Commenting for Auto suggest
	 
	 var creditPeriodList;
	var combo = document.getElementById("txtBillingCP");
	var iCountRows = combo.length;
	
	for (i = 1; i <  iCountRows; i++)
	{
	   combo.remove(1);
	}

	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	creditPeriodList = jsonrpc.processes.getCreditPeriodDetails();
	for(j=0;j<creditPeriodList.list.length;j++)
	{ 	
		var option = document.createElement("option");
		option.text = creditPeriodList.list[j].creditPeriodName;
		option.value = creditPeriodList.list[j].creditPeriodId;
		option.title = creditPeriodList.list[j].creditPeriodName;
		try 
		{
			combo.add(option, null); //Standard
		}
			catch(error) 
		{
			combo.add(option); // IE only
		}
	}*/
 	// *******************For Credit Period Combo Filling by Ajax EnD************************
	//Puneet commenting for auto suggest
	//fetchPODetailsEntity(poNumber);
	//Puneet Commenting the assignment for auto suggest
	//fnFetchEntity();
	
		var jsBillingData ={
			serviceId:servicesID,
			serviceProductID:serviceProductID
		};
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
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
			
		// *******************For Billing Address i.e. BCPID Combo Filling by Ajax Start************************

//Auto suggest BCP Start lawkush
 
    /*   
  document.getElementById('txtBillingAC').value=billinglists.list[0].accountID;
   
	var combo11 = document.getElementById("bbPrimaryBCP");
    var iCountRows3 = combo11.length;
    var i;

    for (i = 1; i <  combo11; i++)
    {
       combo11.remove(1);
    }
   
	 var test11;
		try 
		{
			 var jsData11 =			
   			 {
					accountID:document.getElementById('txtBillingAC').value
				};
			jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			 test11 = jsonrpc.processes.populateBCP(jsData11);
		}
		catch(e) 
		{
				alert(e);
		}
	
	
   
      var j;
    for(j=0;j<test11.list.length;j++)
    {
    	
    	var option = document.createElement("option");
   		option.text = test11.list[j].bcpName;
		option.value = test11.list[j].bcpID;
   		option.title = test11.list[j].bcpName;
		try 
		{
			combo11.add(option, null); //Standard
		}
		catch(error) 
	    {
			combo11.add(option); // IE only
		}
    }
    */
    
   //Auto suggest BCP End lawkush
 	// *******************For Billing Address i.e. BCPID Filling by Ajax EnD************************
 	document.getElementById('BillingNChargeInfo').style.display='block';
		document.getElementById('BillingInfo').style.display='block';
		for (i = 0 ; i < billinglists.list.length; i++,counter++){
			document.getElementById('hdnBillingInfoID').value=billinglists.list[i].billingInfoID;
			document.getElementById('txtPODetailNo').value=billinglists.list[i].podetailID;
			//Puneet adding for auto suggest
			if(billinglists.list[i].poRemarks==null ||billinglists.list[i].poRemarks=="")					    	
				document.getElementById('poDetailNo').value=billinglists.list[i].custPONumber;
			else
				document.getElementById('poDetailNo').value=billinglists.list[i].custPONumber+"-"+billinglists.list[i].poRemarks;
			document.getElementById('txtCntrtMonths').value=billinglists.list[i].periodInMonths;
			document.getElementById('txtBillingAC').value=billinglists.list[i].accountID;
			document.getElementById('txtBillingCP').value=billinglists.list[i].creditPeriod;
			//Puneet credit period value for auto suggest
			document.getElementById('billingCP').value=billinglists.list[i].creditPeriodName;
			//Puneet commenting for auto suggest
			//fnFetchEntity();
			document.getElementById('txtEntity').value=billinglists.list[i].entityID;
			//Puneet adding the entity name for auto suggest
			document.getElementById('legalEntityTXT').value=billinglists.list[i].entityName;
			document.getElementById('hdnISFLEFLAG').value=billinglists.list[i].isFLE;
			document.getElementById('txtBillingType').value=billinglists.list[i].billingType;
			$("#billingType").val(billinglists.list[i].billingTypeName);
			document.getElementById('txtBillingMode').value=billinglists.list[i].billingMode;
			document.getElementById('billingMode').value=billinglists.list[i].billingModeName;
			document.getElementById('txtBillingformat').value=billinglists.list[i].billingformat;
			document.getElementById('billingFormat').value=billinglists.list[i].billingFormatName;
			//Puneet Commenting for auto suggest
			//fnFetchLicCompany(billinglists.list[i].entityID);
			//fnFetchLicCompany();
			$("#licenseCo").val(billinglists.list[i].licCompanyName);
			document.getElementById('txtLicenceCo').value=billinglists.list[i].licCompanyID;
			/*Puneet Commenting the assignment for auto suggest
			document.getElementById('taxationID').value=billinglists.list[i].taxation;*/
			document.getElementById('taxationID').value=billinglists.list[i].taxationName;
			document.getElementById('txtTaxation').value=billinglists.list[i].taxation;
			document.getElementById('txtCmtPeriod').value=billinglists.list[i].commitmentPeriod;
			document.getElementById('txtBillingLvl').value=billinglists.list[i].billingLevel;
			$("#billingLevel").val(billinglists.list[i].billingLevelName);
			document.getElementById('txtPenaltyClause').value=billinglists.list[i].penaltyClause;
			//lawkush Done By Lawkush 
			//satyapan osp tagging by nagarjuna
			
			document.getElementById('txtOSPTagging').value=billinglists.list[i].isOSPTagging;
			if(billinglists.list[i].isOSPTagging=="1"){
			document.getElementById('txtOSPRegNo').value=billinglists.list[i].txtOSPRegNo;
			document.getElementById('txtOSPRegDate').value=billinglists.list[i].txtOSPRegDate;
			document.getElementById('txtOSPRegNo').disabled=false;
			document.getElementById('txtOSPRegDate').disabled=false;
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
			//WARRANTY CLAUSE ADDED BY MANISHA START
			document.getElementById('txtWarrantClause').value=billinglists.list[i].warrantyClause;
			//WARRANTY CLAUSE ADDED BY MANISHA end
		//	document.getElementById('txtBillingPODate').value=billinglists.list[i].poDate;
     		document.getElementById('txtBillingPODate').value=billinglists.list[i].custPoDate;
			document.getElementById('txtCur').value=billinglists.list[i].currencyCode;
			document.getElementById('txtBillingLevelNo').value = billinglists.list[i].billingLevelNo;
			document.getElementById('chkSelectNfa').value = billinglists.list[i].isNfa;
			document.getElementById('chkIsUsage').value = billinglists.list[i].isUsage;
			document.getElementById("bbPrimaryBCP").value=billinglists.list[i].billingBCPId;
		 	document.getElementById("txtBCPAddressSuggest").value=billinglists.list[i].bcpName;
			document.getElementById('txtBAddress1').value = billinglists.list[i].baddress1;
			document.getElementById('txtBAddress2').value = billinglists.list[i].baddress2;
			document.getElementById('txtBAddress3').value = billinglists.list[i].baddress3;
			document.getElementById('txtBAddress4').value = billinglists.list[i].baddress4;
			document.getElementById('txtBCity').value = billinglists.list[i].bcity;
			document.getElementById('txtBState').value = billinglists.list[i].bstate;
			document.getElementById('txtBPincode').value =billinglists.list[i].bpincode;
			document.getElementById('txtBCountry').value = billinglists.list[i].bcountry;
			document.getElementById('txtNoticePeriod').value = billinglists.list[i].noticePeriod;
			document.getElementById('txtBContactNo').value =billinglists.list[i].bcontactNo;				
			document.getElementById('txtBEmailId').value = billinglists.list[i].bemailid;
			document.getElementById('designation').value = billinglists.list[i].designation;
			document.getElementById('lst_No').value = billinglists.list[i].lst_No;
			document.getElementById('lst_Date').value = billinglists.list[i].lstDate;
			document.getElementById('txtBContactName').value = billinglists.list[i].bcontactName;
			//010	Start				
			document.getElementById('txtCircle').value = billinglists.list[i].revCircle;
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
			//011	End
			if(chargeRedValueChanged){
				chargeRedValueChanged = false;
			}else{
				document.getElementById('txtBillingScenario').value = billinglists.list[i].billingScenario;
				document.getElementById('hdnFxRedirectionSPID').value = billinglists.list[i].fxRedirectionSPID;
				document.getElementById('txtFxRedirectionLSI').value = billinglists.list[i].fxRedirectionLSI;
			}
			/*Puneet Commenting for taxation auto suggest
			if(document.getElementById('taxationID').value==2)*/
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
		fnFetchEntity();
		//selectPoId(document.getElementById('txtPODetailNo'));
		selectPoIdAS($("#txtPODetailNo"));
 	}
	if($("#hdnChargeInfo").val()==1){
		document.getElementById('ChargeDetails').style.display='block';
		DrawTable('CHARGEINFOID','VIEWCHARGETABLE');
	}
	billingDetailsFetched = true;
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
	enableDisableCommercialFields(billingSec, roleWiseSectionDetail, lineType);
	enableDisableCommercialFields(chargeSec, roleWiseSectionDetail, lineType);
}

//Puneet for performance tuning getting the hardware details
function getHardwareDetails(){
	if(hardwareDetailsFetched || $("#hdnHardwareInfo").val()!=1){
		return;
	}
	//if Hardware
	//Puneet commenting as it is directly set
	//document.getElementById('HardwareDetails').style.display='block';
	//commenting the call as store is converted to auto suggest
	//populateStore(path);
   //Puneet commenting as it is converted to auto suggest
	/*var comboHType = document.getElementById("txtHtype");
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    var HType = jsonrpc.processes.populateHardwareType();
    for(j=0;j<HType.list.length;j++){
    	var option = document.createElement("option");
   		option.text = HType.list[j].hardwaretypeName;
		option.value = HType.list[j].hardwaretypeId;
   		option.title = HType.list[j].hardwaretypeName;
		try{
			comboHType.add(option, null); //Standard
		}catch(error){
			comboHType.add(option); // IE only
		}
    }
    //document.getElementById("txtHtype").value = 0;
    //document.getElementById("txtHtype").selectedIndex=0;
    comboHType.value=0;
    comboHType.selectedIndex=0;*/
    
    //Puneet commenting the code as the form type is converted to auto suggest
    /*var comboForms = document.getElementById("txtform");
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    var formType = jsonrpc.processes.formAvailable();
    for(j=0;j<formType.list.length;j++){
    	var option = document.createElement("option");
   		option.text = formType.list[j].formName;
		option.value = formType.list[j].formId;
   		option.title = formType.list[j].formName;
		try 
		{
			comboForms.add(option, null); //Standard
		}
		catch(error) 
		{
			comboForms.add(option); // IE only
		}
    }
    
 document.getElementById("txtform").selectedIndex=0;*/	
    //Puneet commenting the code as sale nature is converted to auto suggest
	/*var comboSaleNature = document.getElementById("txtNSale");
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    var saleNature = jsonrpc.processes.natureOfSale();
     
    for(j=0;j<saleNature.list.length;j++){
    	var option = document.createElement("option");
   		option.text = saleNature.list[j].saleNatureName;
		option.value = saleNature.list[j].saleNatureId;
   		option.title = saleNature.list[j].saleNatureName;
		try{
			comboSaleNature.add(option, null); //Standard
		}catch(error){
			comboSaleNature.add(option); // IE only
		}
    }
    document.getElementById("txtNSale").selectedIndex=0;*/	
    /*Puneet commenting as the sale type is converted to auto suggest
    if(document.getElementById("txtBillingformat").value!=0){
    	var comboSaleType = document.getElementById("txtTSale");
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    	var saleType = jsonrpc.processes.typeOfSale(document.getElementById("txtBillingformat").value);
    	for(j=0;j<saleType.list.length;j++){
    		var option = document.createElement("option");
    		option.text = saleType.list[j].saleTypeName;
    		option.value = saleType.list[j].saleTypeId;
    		option.title = saleType.list[j].saleTypeName;
    		try{
    			comboSaleType.add(option, null); //Standard
    		}catch(error){
    			comboSaleType.add(option); // IE only
    		}
    	}
    }*/
	
	var test;
    var combo11 = document.getElementById("txtStartDateLogic");
    var combo22 = document.getElementById("txtEndDateLogic");
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    test = jsonrpc.processes.populateHDateLogic();
	for(j=0;j<test.list.length;j++){
    	if(test.list[j].section=='START'){
		    var option = document.createElement("option");
		   	option.text = test.list[j].dateLogicValue;
			option.value = test.list[j].dateLogicValue;			
	    	option.title = test.list[j].dateLogicValue;
			try{
				combo11.add(option, null); //Standard
			}catch(error){
				combo11.add(option); // IE only
			}
		}
		if(test.list[j].section=='END'){
		    var option = document.createElement("option");
		   	option.text = test.list[j].dateLogicValue;
		   	option.value = test.list[j].dateLogicValue;			
		   	option.title = test.list[j].dateLogicValue;
			try{
				combo22.add(option, null); //Standard
			}catch(error){
				combo22.add(option); // IE only
			}
		}
    }	   
    var combo2 = document.getElementById("txtdispatchAddress");
   /* var jsData2 =			
    {
		accountID:document.getElementById('txtBillingAC').value
	};
	jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
    test2 = jsonrpc.processes.populateDispatchCode(jsData2);
     
    for(j=0;j<test2.list.length;j++)
    {
    	var option = document.createElement("option");
   		option.text = test2.list[j].dispatchAddressName;
		option.value = test2.list[j].dispatchAddressID;
   		option.title = test2.list[j].dispatchAddressName;
		try 
		{ if(option.value == 0){
			option.text= 'Select Dispatch Address Code';
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
    var jsBillingData ={
		serviceProductID:serviceProductID,
		serviceId:servicesID
	};
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");

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
	    	// end
	    	if(hardwarelists.list[i].selectedDispatchID != 0){
	    		getDispatchAddress();
	    	}
			document.getElementById('txtStore').value=hardwarelists.list[i].selectedStoreID;
			$("#txtStoreText").val(hardwarelists.list[i].storeName);	
			document.getElementById('txtform').value=hardwarelists.list[i].formAvailable;
			//Puneet for Form type auto suggest
			document.getElementById('textFormTXT').value=hardwarelists.list[i].formName;
			document.getElementById('txtHtype').value=hardwarelists.list[i].hardwareType;
			$('#hTypeText').val(hardwarelists.list[i].hardwaretypeName);
			document.getElementById('txtNSale').value=hardwarelists.list[i].saleNature;
			$('#textSaleNature').val(hardwarelists.list[i].saleNatureName);
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
			document.getElementById('txtDispatchContactName').value=hardwarelists.list[i].dispatchContactName;
			if(hardwarelists.list[i].txtExtDate==null){
				document.getElementById('txtHExtDate').value='';
			}else
				document.getElementById('txtHExtDate').value=hardwarelists.list[i].txtExtDate;
			refreshTaxRateForAS(path);	    	
    	}
    }
    hardwareDetailsFetched = true;
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
	if(locationDetailsFetched || $("#hdnLocationInfo").val()!=1){
		return;
	}
   	//document.getElementById('ServiceLocation').style.display='block';
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
	document.getElementById('txtFAddress').value="";
	document.getElementById('txtToAddress').value="";
	document.getElementById('txtPContactNo').value="";
	document.getElementById('txtSContactNo').value="";
	document.getElementById('txtPEmailId').value="";
	document.getElementById('txtSEmailId').value="";
	//  Start [006]
	/*
		
    	var callerWindowObj = dialogArguments;
    	var accountID=callerWindowObj.document.getElementById("accountID").value;
	    var combo3 = document.getElementById("ddPrimaryBCP");
	    var iCountRows3 = combo3.length;
  
	    for (i = 1; i <  iCountRows3; i++)
	    {
	       combo3.remove(1);
	    }
	   
	    var jsData3 =			
	    {
			accountID:accountID
		};
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		
        var test3 = jsonrpc.processes.populateBCPWithDispatch(jsData3);
         
        for(j=0;j<test3.list.length;j++)
	    {
	    	
	    	var option = document.createElement("option");
	   		option.text = test3.list[j].bcpName;
			option.value = test3.list[j].bcpID;
			option.title = test3.list[j].bcpName;
			try 
			{
				combo3.add(option, null); //Standard
			}
			catch(error) 
			{
				combo3.add(option); // IE only
			}
	    }
	    
	    //For Fetching BCPID on basis of Account ID
	    var combo4 = document.getElementById("ddSecondaryBCP");
	    var iCountRows2 = combo4.length;
  
	    for (i = 1; i <  iCountRows2; i++)
	    {
	       combo4.remove(1);
	    }
	    var jsData4 =			
	    {
			  accountID:accountID
		};
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		
        var test4 = jsonrpc.processes.populateBCPWithDispatch(jsData4);
         
        for(j=0;j<test4.list.length;j++)
	    {
	    	
	    	var option = document.createElement("option");
	   		option.text = test4.list[j].bcpName;
			option.value = test4.list[j].bcpID;
	   		option.title = test4.list[j].bcpName;
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
	    
	var jsLocationData ={
		serviceProductID:serviceProductID,
		serviceId:servicesID
	};
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    locationlists = jsonrpc.processes.populateLocationDetails(jsLocationData);
    if(locationlists.list.length==0){
    	  document.getElementById('ServiceLocation').style.display='none';
    }else{
    	document.getElementById('ServiceLocation').style.display='block';
    	document.getElementById('onlyNetworkLoc').style.display='none';
    	var sendToM6Check = jsonrpc.processes.getM6LineItemCheck(serviceDetailID);
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
    locationDetailsFetched=true;
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

	var fxChargeRedirectionType="";
	var fxChargeRedirectionTypeCumulative="";
function getServiceAttributes(){
	//var startTime = new Date().getTime();
//	var startServiceTime = new Date().getTime();
	document.getElementById("hdnServiceDetailID").value=serviceDetailID;
	document.getElementById('txtProductName').value=productName;
	document.getElementById('txtHdnServiceName').value=serviceName;	
	var jsHdnData =	{
		serviceDetailID:serviceDetailID,
		serviceProductID:serviceProductID
	};
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	var testHdnVal = jsonrpc.processes.populateHdnProductCatelogValue(jsHdnData);
	var hdnBillingInfo = document.getElementById('hdnBillingInfo');
	var hdnServiceSummary = document.getElementById('hdnServiceSummary');
	var hdnChargeInfo = document.getElementById('hdnChargeInfo');
	var hdnLocationInfo = document.getElementById('hdnLocationInfo');
	var hdnHardwareInfo = document.getElementById('hdnHardwareInfo');
	var hdnComponentInfo = document.getElementById('hdnComponentInfo');
	hdnServiceSummary.value = testHdnVal.list[0].serviceInfoValue;
	hdnBillingInfo.value = testHdnVal.list[0].billingInfoValue;
	hdnChargeInfo.value = testHdnVal.list[0].chargeInfoValue;
	hdnLocationInfo.value = testHdnVal.list[0].locationInfoValue;
	hdnHardwareInfo.value = testHdnVal.list[0].hardwareInfoValue;
	hdnComponentInfo.value = testHdnVal.list[0].componentInfoValue;
    document.getElementById('hdnConfigValue').value=testHdnVal.list[0].configValue;
    document.getElementById('txtBCPAddressSuggestService').className = "dropdownMargin";
    document.getElementById('txtBCPAddressSuggestService').disabled=false;
     $("#txtBCPAddressSuggestService").autocomplete( "enable" );
 		$("#bcpAutoSuggestServiceLinkId").show();
    //--------To find config value based on servicedetailid and set config value on product catelog page----------
	//var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
    var jsConfigData =	{
		serviceDetailID:serviceDetailID,
		serviceId:servicesID,
		configValue:testHdnVal.list[0].configValue
	};
	var configValueList=jsonrpc.processes.fetchConfigValue(jsConfigData);
	fxChargeRedirectionType=configValueList.fxChargeRedirectionType;
	fxChargeRedirectionTypeCumulative=configValueList.fxChargeRedirectionTypeCumulative;
	attFLI_PO_DisableValue=configValueList.fli_po_disable;
	//-------------------------------------------------------------------------------------------------------------
    if(hdnBillingInfo.value == 0 && hdnServiceSummary.value == 0){
    	document.getElementById('saveImage').disabled=true;
    }else{
    	document.getElementById('saveImage').disabled=false;
    }
    if(null == roleWiseSectionDetail){
    	var callerWindowObj = dialogArguments;
    	var roleName = callerWindowObj.gb_roleID;
    	roleWiseSectionDetail = getRoleWiseSectionDetails(roleName);
    }
  //  var beforeServ = new Date().getTime();
  //  alert("Before Service summary " + Number(beforeServ - startServiceTime));
 //   startServiceTime = new Date().getTime(); 
    //Puneet resetting all the flag
    resetProducCatalogueFlags();
    //Puneet for performance tuning, service summary details will only be called when user will open the service summary
	if(hdnServiceSummary.value==1){
		document.getElementById('lineItemServiceSummary').style.display='block';
	//	getServiceSummary();
	}else{
		document.getElementById('lineItemServiceSummary').style.display='none';
	}
//	var endServiceTime = new Date().getTime();
//	alert("Service summary time " + Number(endServiceTime-startServiceTime));
//	var billStartTime = new Date().getTime();
    if(hdnBillingInfo.value==1){
    	//Puneet for performace tuning
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
		// Start [013]
		document.getElementById('BillingNChargeInfo').style.display='none';
    	document.getElementById('BillingInfo').style.display='none';
    	// End [013]
	}
 //   var endBillingTime = new Date().getTime();
//	alert("billing summary time " + Number(endBillingTime-billStartTime));
 	//FOR BILLING INFO LIST ENDS
 	//FOR CHARGE LIST STARTS
//	 var startchargeTime = new Date().getTime(); 
    //Puneet commenting the code as charge details will be fetched when user clicks on billing
  /*  if(hdnChargeInfo.value==1){
		document.getElementById('ChargeDetails').style.display='block';
		DrawTable('CHARGEINFOID','VIEWCHARGETABLE');
	}*/
  //  var endchargeTime = new Date().getTime();
//	alert("charge time " + Number(endchargeTime-startchargeTime));
    //FOR CHARGE LIST ENDS
    //FOR Hardware Info Starts
//	var startHardTime = new Date().getTime();
    if(hdnHardwareInfo.value==1){
    	//Puneet for performance tuning fetching the details only when it is required
    	//getHardwareDetails()
    	document.getElementById('HardwareDetails').style.display='block';
    	 document.getElementById('txtBCPAddressSuggestService').className = "inputBorder2";
    	$("#txtBCPAddressSuggestService").attr('disabled', 'disabled');		
		$("#bcpAutoSuggestServiceLinkId").hide();
    }else{	
    	document.getElementById('txtform').value="0";
    	document.getElementById('textFormTXT').value="Select Form";
		document.getElementById('txtHtype').value="0";
		$('#hTypeText').val("Select Hardware Type");
		document.getElementById('txtNSale').value="0";
		$('#textSaleNature').val("Select Sale Nature");
		document.getElementById('txtTSale').value="0";
		document.getElementById('textSaleType').value="Select Type Of Sale";
		document.getElementById('hdnHardwareInfoID').value=0;
		document.getElementById('HardwareDetails').style.display='none';
    }
  //  var endHardTime = new Date().getTime();
 //   alert("HArdware Time " + Number(endHardTime - startHardTime) );
 //   var locStartTime = new Date().getTime();
   if(hdnLocationInfo.value==1){
	   //Puneet for performance tuning
	   //getLocationDetails();
	   document.getElementById('ServiceLocation').style.display='block';
   }else{
	// Start [013]
	   document.getElementById('ServiceLocation').style.display='none';
	// End [013]
   }
//   var endLocTime = new Date().getTime();
//   alert("Location Time " + Number(endLocTime - locStartTime) );
	var jsDataDisable = {
		serviceProductID:serviceProductID
	};
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	newOrderStatus = jsonrpc.processes.newOrderStatus(serviceProductID);
	var callerWindowObj = dialogArguments;
	if(((callerWindowObj.document.getElementById('isBillingTriggerReady').value=="ENABLE") & roleId==3) || 
		(roleId==4 & newOrderStatus > 0 )){
	}else{
		document.getElementById('saveImage').style.display="block";
	}
    if(hdnComponentInfo.value==1){
		document.getElementById('Components').style.display='block';
		//alert('before getComponentAttributes()');
		//getComponentAttributes();
		//alert('after getComponentAttributes()');	
		// commented by Ramana }
	}else{
		// Start [013]
		document.getElementById('Components').style.display='none';
		// Start [013]
	}
	enableDisableRedirectionLSIIfPresent(fxChargeRedirectionType,'VPC',1,'');	
	// start:CLEP by Anil this will be last call in this method,please add new code before this line
	//Puneet commenting as it will be called section wise
	enableDisableCLEP(null);
	//end :CLEP
	//called for enabling or disabling checkboxes
	//if(  document.getElementById('hdnChargeInfo').value == 1)
		//{
			//selectAllCheckBoxEnableDisable(1);
		//}
	
	   //Auto suggest BCP Start lawkush
	    //called getAutoSuggest() and AttachCSStoAutoSuggestButton() for autosuggestion
		//getAutoSuggest();	
		AttachCSStoAutoSuggestButton();
	//================================================================
	//Auto suggest BCP End lawkush
		//var endTime = new Date().getTime();
		//alert("Time taken " + Number(endTime - startTime));
}

/* Puneet commented for performance tuning as instead of filling charge type for each select individually, collectively
 * all the drop downs will be filled
 function fillChargeType(var_counter,serviceProductID){
	try{
		if(gbchargeTypeCached==0){
			var productid = serviceProductID;
			//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			chargeCombo = jsonrpc.processes.populateChargeType(productid);
			gbchargeTypeIds=new Array();
			gbchargeTypeValues=new Array()
			for(z=0;z<chargeCombo.list.length;z++){
		    	gbchargeTypeIds[z] = chargeCombo.list[z].chargeTypeID;
				gbchargeTypeValues[z] = chargeCombo.list[z].chargeTypeName;
		    }
		}
		gbchargeTypeCached=1;
		//read from cache
		var local_typeCombo = document.getElementById("ddCType"+var_counter);
	    var local_RowCount = local_typeCombo.length;
	    for (i = 1; i <  local_RowCount; i++){
	       local_typeCombo.remove(1);
	    } 
	    for(i=0;i<gbchargeTypeIds.length;i++){
	    	var option = document.createElement("option");
	   		option.text = gbchargeTypeValues[i];
			option.value = gbchargeTypeIds[i];
			option.title = gbchargeTypeValues[i];
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
}*/
///////////////////////////////
function fillAllChargeType(counter,serviceProductID, chargeTypeComboDivId){
	try{
		var chargeCombo = jsonrpc.processes.populateChargeType(serviceProductID);
		var option="";
		for(z=0;z<chargeCombo.list.length;z++){
			option = option + "<option value='" + chargeCombo.list[z].chargeTypeID + "'>" + chargeCombo.list[z].chargeTypeName + "</option>";
		}
		for (j = 1; j <= counter; j++){
			var chargeTypeSelect = "<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px;'  name='ddCType' id='ddCType"+j+"' isRequired='0' Disp='' class='dropdownMargin' onchange='javascript:CheckRCRequiredForCLEP(this,"+j+");getDropDownChargeName("+j+");changeFrequency("+j+");calculateFreqAmount("+j+");fillEndDateLogic("+j+");'>";
	   		chargeTypeSelect = chargeTypeSelect + option + "</select>";
	   		chargeTypeComboDiv = document.getElementById(chargeTypeComboDivId+j);
	   		chargeTypeComboDiv.innerHTML = chargeTypeSelect;
		}
    }catch(e){
		alert(gb_exceptionMessage);
		showJavascriptException(e,gb_exceptionMessageShow);
	}
}

//Onclick backgroud Color change for row : Done by Ashutosh
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
function getComponentAttributes(){
	if(componentDetailsFetched || $("#hdnComponentInfo").val()!=1)
		return;
	var counter = parseInt(document.getElementById('hdnComponentsCount').value);
	initCount = counter;
	var jsComponentData = {
		serviceProductID:serviceProductID,
		serviceId:servicesID
	};
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	componentslists = jsonrpc.processes.populateComponentsDetails(jsComponentData);
	
	if(componentslists.list.length==0){
	}else{
		var compDiv = document.getElementById('contentscroll2');
		var compTable = "<table border='0' cellpadding='0' cellspacing='0' class='content' id='tableComponents'>";
    	var tableEnd = '</table>';
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
			//document.getElementById('hdnComponentsCount').value = counter;
			compTable = compTable + tableRowStart;	
			/*var str;
			//counter = document.getElementById('tableComponents').rows.length;
			var tblRow = document.getElementById('tableComponents').insertRow();
			tblRow.onclick = function(){changeUpperBackground();};//Added by Ashutosh for highlighting row*/
				
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata col1 toprow";*/
			str ="<input name='chkComponents' id='chkComponents"+i+"' type='checkbox' value='"+counter+"'>";
			str=str+"<input type='hidden' name='component_infoID' id = 'component_infoID"+i+"' value='"+componentslists.list[i].componentInfoID+"'>";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col1 toprow'>";
			compTable = compTable + tableDataStart + str + tableDataEnd;
				
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata secondColumn";*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata secondColumn'>";
			str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' style='width:100px' class='inputDisabled' name='components' isRequired='0'  id='components"+counter+"'  value = ' " + componentslists.list[i].componentID + " ' readonly='true'>";
			//str=str+"<input type='hidden' name='component_infoIDs' id = 'component_infoIDs"+i+"' value='"+componentslists.list[i].componentInfoID+"'>";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			compTable = compTable + tableDataStart + str + tableDataEnd;
				
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata descColumn";*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata descColumn'>";
			str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='inputDisabled' style='width:250px' name='componentsName' isRequired='0' id='componentsName"+counter+"'  value = ' " + componentslists.list[i].componentName + " ' readonly='true'>";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			compTable = compTable + tableDataStart + str + tableDataEnd;
		
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata secondColumn";*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata secondColumn'>";
			str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' style='width:100px' class='inputDisabled' name='packageID' isRequired='0' id='packageID"+counter+"'  value = ' " + componentslists.list[i].packageID + " ' readonly='true'>";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			compTable = compTable + tableDataStart + str + tableDataEnd;
				
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata descColumn";*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata descColumn'>";
			str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='inputDisabled' style='width:250px' name='packageName' isRequired='0' id='packageName"+counter+"'  value = ' " + componentslists.list[i].packageName + " ' readonly='true'>";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			compTable = compTable + tableDataStart + str + tableDataEnd;
			
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata secondColumn";*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata secondColumn'>";
			str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' style='width:100px' class='inputDisabled' name='status' isRequired='0' id='status"+counter+"'  value = ' " + componentslists.list[i].componentStatus + " ' readonly='true'>";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			compTable = compTable + tableDataStart + str + tableDataEnd;
			
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata col4";*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col4'>";
			//str ="<input type='text' class='inputBorder1' style='width=200px'name='startdatelogic' isRequired='0' id='startdatelogic"+counter+"'  value = ' " + componentslists.list[i].startLogic + " ' >";
			str="<table><tr><td><select  name='txtCompStartDateLogic' id='txtCompStartDateLogic"+counter+"' isRequired='1' Disp='' class='dropdownMargin' style='width:125px'>";
			str = str + startDateOptions + "</select></td>";
			str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='"+componentslists.list[i].startDateDays+"'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtStartDays'  id='txtStartDays"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'></td> ";
			str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='"+componentslists.list[i].startDateMonth+"'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtStartMonth' id='txtStartMonth"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'><input type='hidden' name='hdnstartdate' value=''></td></tr></table> ";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			compTable = compTable + tableDataStart + str + tableDataEnd;
			
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata col4";*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col4'>";
			//str ="<input type='text' class='inputBorder1' style='width=200px'name='enddatelogic' isRequired='0' id='enddatelogic"+counter+"'  value = ' " + componentslists.list[i].endLogic + " ' >";
			str="<table><tr><td><select  name='txtCompEndDateLogic' id='txtCompEndDateLogic"+counter+"' isRequired='1' Disp='' class='dropdownMargin' onchange='changeCompEndDays("+counter+");' style='width:125px'>";
			str= str + endDateOptions + "</select></td>";
			str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='"+componentslists.list[i].endDateDays+"'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtEndDays' id='txtEndDays"+counter+"'  onblur='if(this.value.length > 0){return checknumber(this)}'></td> ";
			str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' value='"+componentslists.list[i].endDateMonth+"'  isRequired='0' Disp='' maxlength='4' class='inputBorder' name='txtEndMonth' id='txtEndMonth"+counter+"' onblur='if(this.value.length > 0){return checknumber(this)}'></td></tr></table> ";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			compTable = compTable + tableDataStart + str + tableDataEnd;
			//---------------Start Date Logic and End Date Logic--------------------------------------------------------
			//fillComponentStartEndDateLogic(counter);									
			//document.getElementById('txtCompStartDateLogic'+counter).value=componentslists.list[i].startDateLogic;
			//document.getElementById('txtCompEndDateLogic'+counter).value=componentslists.list[i].endDateLogic;
			//-----------------------------------------------------------------------------------------------------------
		
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata dateField";*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata dateField'>";
			str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='inputDisabled' style='width:100px' name='startdate' isRequired='0' id='startdate"+counter+"'  value = ' " + componentslists.list[i].start_date + " ' readonly='true'>";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			compTable = compTable + tableDataStart + str + tableDataEnd;
			
			/*var tblcol=tblRow.insertCell();
			tblcol.align = "left";
			tblcol.vAlign="top";
			tblcol.className="innerdata dateField";*/
			tableDataStart = "<td align = 'left' vAlign='top' class='innerdata dateField'>";
			str ="<input type='text' onmouseover='getTip(value)' onmouseout='UnTip()' class='inputDisabled' style='width:100px' name='enddate' isRequired='0' id='enddate"+counter+"'  value = ' " + componentslists.list[i].end_date + " ' readonly='true'>";
			/*CellContents = str;
			tblcol.innerHTML = CellContents;*/
			compTable = compTable + tableDataStart + str + tableDataEnd;
			
			// 023 start
			/*var ctrlArry = new Array();
			ctrlArry[0] =  document.getElementById("components"+counter);
			ctrlArry[1] =  document.getElementById("componentsName"+counter);
			ctrlArry[2] =  document.getElementById("packageID"+counter);
			ctrlArry[3] =  document.getElementById("packageName"+counter);
			ctrlArry[4] =  document.getElementById("status"+counter);				
			ctrlArry[5] =  document.getElementById("startdate"+counter);
			ctrlArry[6] =  document.getElementById("enddate"+counter);
			ctrlArry[7] =  document.getElementById("txtCompStartDateLogic"+counter);
			ctrlArry[8] =  document.getElementById("txtCompEndDateLogic"+counter);
			var callerWindowObj = dialogArguments;		
			fieldRoleMappingValidation(callerWindowObj.linesTabFieldList,ctrlArry);*/
			ctrlArry[fieldIndex +0] =  "components"+counter;
			ctrlArry[fieldIndex +1] =  "componentsName"+counter;
			ctrlArry[fieldIndex +2] =  "packageID"+counter;
			ctrlArry[fieldIndex +3] =  "packageName"+counter;
			ctrlArry[fieldIndex +4] =  "status"+counter;
			ctrlArry[fieldIndex +5] =  "startdate"+counter;
			ctrlArry[fieldIndex +6] =  "enddate"+counter;
			ctrlArry[fieldIndex +7] =  "txtCompStartDateLogic"+counter;
			ctrlArry[fieldIndex +8] =  "txtCompEndDateLogic"+counter;	
			fieldIndex = fieldIndex + 9;
			compTable = compTable + tableRowEnd;
			counter++;
				
			// 023 end
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
		}
		document.getElementById('hdnComponentsCount').value = counter;
		compTable = compTable + tableEnd;
		compDiv.innerHTML = compTable;
		var callerWindowObj = dialogArguments;		
		allFieldRoleMappingValidation(callerWindowObj.linesTabFieldList,ctrlArry);
		for (i = 0 ; i < componentslists.list.length; i++){
			document.getElementById('txtCompStartDateLogic'+initCount).value=componentslists.list[i].startDateLogic;
			document.getElementById('txtCompEndDateLogic'+initCount).value=componentslists.list[i].endDateLogic;
			initCount++;
		}
	}
	componentDetailsFetched = true;
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

/*Puneet commenting the function as it is not getting used
 * function fillChargeNames(var_counter, chargeTypeValue,chargeNameID){
	try{
		//cache charge type
		var chargetype = chargeTypeValue;
		var productid = document.getElementById('hdnServiceDetailID').value;
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		chargeCombo = jsonrpc.processes.populateChargeName(chargetype,productid);
		gchargeName=new Array();
		gchargeid=new Array()
		for(z=0;z<chargeCombo.list.length;z++){
		   	gchargeName[z] = chargeCombo.list[z].chargeName;
			gchargeid[z] = chargeCombo.list[z].chargeNameID;
		}
		//read from cache
		var local_typeCombo = document.getElementById("txtCName"+var_counter);
	    var local_RowCount = local_typeCombo.length;
	    for (i = local_RowCount-1; i >0 ; i--){
	       local_typeCombo.remove(1);
	    } 
	    for(i=0;i<chargeCombo.list.length;i++){
	    	var option = document.createElement("option");
	   		option.text = gchargeName[i];
			option.value = gchargeid[i];
			option.title = gchargeName[i];
			try{
				local_typeCombo.add(option, null); //Standard
			}catch(error){
				local_typeCombo.add(option); // IE only
			}
	    }
	    local_typeCombo.value=chargeNameID;
    }catch(e){
		alert(gb_exceptionMessage);
		showJavascriptException(e,gb_exceptionMessageShow);
	}
}*/

function fillFrequency(var_counter){
	checkContractPeriod(var_counter);
	if(gbfrequencyCached==0){
		//cache charge type
		try{
			//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
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
	//read from cache
	var local_Combo = document.getElementById("txtCFrequency"+var_counter);
    var local_RowCount = local_Combo.length;
    for (i = 1; i <  local_RowCount; i++){
	   local_Combo.remove(1);
	}
	for(i=0;i<gbfrequencyId.length;i++){
		var option = document.createElement("option");
		option.text = gbfrequencyName[i];
		option.value = gbfrequencyId[i];
		option.title = gbfrequencyName[i];
		try{
			local_Combo.add(option, null); //Standard
		}catch(error){
			local_Combo.add(option); // IE only
		}
	}
}

function fnHideAll()
{
	document.getElementById('lineItemServiceSummary').style.display='none';
	document.getElementById('BillingNChargeInfo').style.display='none';
   	document.getElementById('BillingInfo').style.display='none';
   	document.getElementById('serviceType').style.display='block';
   	document.getElementById('ChargeDetails').style.display='none';
   	document.getElementById('saveImage').style.display='block';
   	document.getElementById('HardwareDetails').style.display='none';
   	document.getElementById('ServiceLocation').style.display='none';
}
function fnViewProductCatelog(url)
{
	var path = requestContextPath + "/NewOrderAction.do?method=productCatelogforUpdate&"+ unescape(url) +"&viewModify=2";
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

	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");

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
function getDispatchAddress(){
	var tr, td, i, j, oneRecord;
    var test4;
    var jsData4 ={
		dispatchAddressID:document.getElementById("txtdispatchAddress").value
	};
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	if(document.getElementById("txtdispatchAddress").value != "0"){
    	test4 = jsonrpc.processes.populateDispatchAddress(jsData4);
    	if(test4.list.length>0){ //if logic added by saurabh
	    	document.getElementById('txtHAddress1').value = test4.list[0].dispatchAddress1;
			document.getElementById('txtHAddress2').value = test4.list[0].dispatchAddress2;
			document.getElementById('txtHAddress3').value = test4.list[0].dispatchAddress3;
			document.getElementById('txtHCityName').value = test4.list[0].dispatchCityName;
			document.getElementById('txtHCountryName').value = test4.list[0].dispatchCountyName;
			document.getElementById('txtHPincode').value = test4.list[0].dispatchPinNo;
			document.getElementById('txtHPhnNo').value = test4.list[0].dispatchPhoneNo;
			document.getElementById('txtHStateName').value = test4.list[0].dispatchStateName;
			//[202020]
			if(test4.list[0].lst_No!=0){
				document.getElementById('txtHLstNo').value = test4.list[0].lst_No;
			}else{
				document.getElementById('txtHLstNo').value = '-';
			}
			//[202020]
		}
	}else{
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

/*Puneet commenting for auto suggest
 * function fetchPODetailsEntity(poNumber)
{
	var tr, td, i, j, oneRecord;
    var test;
    var combo = document.getElementById("txtPODetailNo");
	contractVal=new Array();  
    chargeVal=new Array();
    poDateVal=new Array();
    
   var iCountRows = combo.length;
   for (i = iCountRows-1; i >=0; i--)
   {
       combo.remove(i);
   }

    var jsData =			
    {
    	serviceDetailID:document.getElementById("hdnserviceDetailId").value,
		poNumber:poNumber
	};
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    test = jsonrpc.processes.populatePODetailsNoForProduct(jsData);
    var option = document.createElement("option");
   		option.text = "Select Cust PO Detail No";
 				option.value = 0;
 				option.title = "Select Cust PO Detail No";
 				try 
		{
			combo.add(option, null); //Standard
		}
		catch(error) 
		{
			combo.add(option); // IE only
		}
	
    for(j=0;j<test.list.length;j++)
    {
    	var option = document.createElement("option");
    	if(test.list[j].poRemarks==null ||test.list[j].poRemarks=="")					    	
			option.text = test.list[j].custPoDetailNo;
		else
   		option.text = test.list[j].custPoDetailNo+"-"+test.list[j].poRemarks;
		option.title = option.text;
		option.value = test.list[j].poDetailNo;
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

function fnFetchEntity(){
//	var combo = document.getElementById("txtEntity");
//	if(document.getElementById('txtPODetailNo').value==0){
//		document.getElementById('txtCntrtMonths').value="";
//		
//	   var iCountRows = combo.length;
//	   for (i = 1; i <  iCountRows; i++)
//	   {
//	       combo.remove(1);
//	   }
//	   combo.value=0;
//	}
//	else
//	{
//		var tr, td, i, j, oneRecord;
//	    var test;
//	 
//	 	//var combo = document.getElementById("txtEntity");
//	   
//		   var iCountRows = combo.length;
//		  
//		   for (i = 1; i <  iCountRows; i++)
//		   {
//		       combo.remove(1);
//		   }
//	    var jsData =			
//	    {
//			poDetailNo:document.getElementById("txtPODetailNo").value
//		};
//		
//		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
//	
//	    test = jsonrpc.processes.populateEntity(jsData);
//	      
//	    for(j=0;j<test.list.length;j++)
//	    {
//	    	document.getElementById('hdnISFLEFLAG').value=test.list[j].isFLEFlag;
//	    	//var combo = document.getElementById("txtEntity");
//	    	var option = document.createElement("option");
//	   		option.text = test.list[j].entity;
//			option.value = test.list[j].entityID;
//			option.title = test.list[j].entity;
//			try 
//			{
//				combo.add(option, null); //Standard
//			}
//			catch(error) 
//			{
//				combo.add(option); // IE only
//			}
//	    }
//	    combo.selectedIndex = 1;
//	    document.getElementById('txtCntrtMonths').value=contractVal[document.getElementById("txtPODetailNo").selectedIndex-1];
//	    document.getElementById('txtBillingPODate').value=poDateVal[document.getElementById("txtPODetailNo").selectedIndex-1];
//	   	fnFetchLicCompany(combo.value);
		if(document.getElementById('txtBillingLvl') != null){
			setBillingLevelNo(document.getElementById('txtBillingLvl').value)
		}
	//}
}

//Puneet commenting the code as license company is converted to auto suggest
/*function fnFetchLicCompany(){
	var entId = document.getElementById("txtEntity").value;
	var tr, td, i, j, oneRecord;
    var test;
 	var combo = document.getElementById("txtLicenceCo");
 	var serviceDetailID1=Number(document.getElementById("hdnserviceDetailId").value);
 	var iCountRows = combo.length;
	   //for (i = 1; i <  iCountRows; i++)
	   //{
	   //    combo.remove(1);
	   //}
    var jsData = {
		entityID:entId,
		serviceDetailID:serviceDetailID1
	};
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    test = jsonrpc.processes.populateLicCompany(jsData);
    removeAllOptions(combo); 
    for(j=0;j<test.list.length;j++){
    	var option = document.createElement("option");
   		option.text = test.list[j].licCompanyName;
		option.value = test.list[j].licCompanyID;
		option.title = test.list[j].licCompanyName;
		try{
			combo.add(option, null); //Standard
		}catch(error){
			combo.add(option); // IE only
		}
    }
}*/

function selectPrimaryLocation(DispValue, sendToM6Check){
	var displayValue=DispValue;
	var sendToM6Check;
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	//Puneet commenting for performance tuning
	if(null == sendToM6Check){
		sendToM6Check = jsonrpc.processes.getM6LineItemCheck(serviceDetailID);
	}
	var ddPrimaryLocType = document.getElementById('ddPrimaryLocType');	
	if(sendToM6Check==0 && serviceid == 4){
		if(ddPrimaryLocType.value==0){
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
		}else if(ddPrimaryLocType.value==1){
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='block'; 
		}else{
			alert('Network location is not Allowed for this Line Item');
			document.getElementById('ddPrimaryLocType').value=0;
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
		}		
	}

	if(sendToM6Check==1 && serviceid == 4){
		if(ddPrimaryLocType.value==0){
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
		}else if(ddPrimaryLocType.value==1){
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='block'; 
		}else{
			document.getElementById('PriNetworkPopLocation').style.display='block'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
			if(displayValue==1){
				PopLocTypePopup('PRILOC');
			}
		}
	}
	if(sendToM6Check==1 && serviceid != 4){
		if(ddPrimaryLocType.value==0){
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
		}else if(ddPrimaryLocType.value==1){
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='block'; 
		}else{
			document.getElementById('PriNetworkPopLocation').style.display='block'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
			if(displayValue==1){
				PopLocTypePopup('PRILOC');
			} 
		}
	}
	if(sendToM6Check==0 && serviceid != 4){
		if(ddPrimaryLocType.value==0){
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
		}else if(ddPrimaryLocType.value==1){
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='block'; 
		}else{
			alert('Network location is not Allowed for this Line Item');
			document.getElementById('ddPrimaryLocType').value=0;
			document.getElementById('PriNetworkPopLocation').style.display='none'; 
			document.getElementById('PriBCPLocation').style.display='none'; 
		}		
	}
}

function selectSecondaryLocation(DispValue, sendToM6Check){
	var displayValue=DispValue;
	var sendToM6Check;
	if(null == sendToM6Check){
		//var jsonrpc1 = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		sendToM6Check = jsonrpc1.processes.getM6LineItemCheck(serviceDetailID);
	}	
	var ddSecondaryLocType = document.getElementById('ddSecondaryLocType');	
	if(sendToM6Check==0 && serviceid == 4){
		if(ddSecondaryLocType.value==0){
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
		}else if(ddSecondaryLocType.value==1){
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='block'; 
		}else if(ddSecondaryLocType.value==2){
			alert('Network location is not Allowed for this Line Item');
			document.getElementById('ddSecondaryLocType').value=0;
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
		}			
	}

	if(sendToM6Check==1 && serviceid != 4){
		if(ddSecondaryLocType.value==0){
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
		}else if(ddSecondaryLocType.value==1){
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='block'; 
		}else{
			document.getElementById('SecNetworkLocation').style.display='block'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
			if(displayValue==1){
				PopLocTypePopup('SECLOC');
			}
		}
	}
	if(sendToM6Check==1 && serviceid == 4){
		if(ddSecondaryLocType.value==0){
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
		}else if(ddSecondaryLocType.value==2){
			document.getElementById('SecNetworkLocation').style.display='block'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
			if(displayValue==1){
				PopLocTypePopup('SECLOC');
			}
		}else if(ddSecondaryLocType.value==1){
			alert('Customer location is not Allowed for this Line Item');
			ddSecondaryLocType.value=0;
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
		}	
    }
	if(sendToM6Check==0 && serviceid != 4){
		if(ddSecondaryLocType.value==0){
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
		}else if(ddSecondaryLocType.value==1){
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='block'; 
		}else if(ddSecondaryLocType.value==2){
			alert('Network location is not Allowed for this Line Item');
			ddSecondaryLocType.value=0;
			document.getElementById('SecNetworkLocation').style.display='none'; 
			document.getElementById('SecBCPLocation').style.display='none'; 
		}			
	}
}

function FetchPriBCPDetails(){
	var tr, td, i, j, oneRecord;
    var test5;
   	if(document.getElementById("ddPrimaryBCP").value != 0){
	    var jsData5 =	{
			bcpID:document.getElementById("ddPrimaryBCP").value
		};
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    test5 = jsonrpc.processes.populateprmBCPDetails(jsData5);
	    if(test5.list.length>0){ //if logic added by saurabh 
	    	//  Start [006]
	    	document.getElementById('txtPriCustLocationSuggest').value = test5.list[0].bcpName;
		   //  End [006]
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
	}else{
		//  Start [006]
		document.getElementById('txtPriCustLocationSuggest').value ="" ;
		//  End [006]
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

function FetchSecBCPDetails(){
	var tr, td, i, j, oneRecord;
    var test6;
   	if(document.getElementById("ddSecondaryBCP").value != 0){
	    var jsData6 ={
			bcpID:document.getElementById("ddSecondaryBCP").value
		};
		
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	    test6 = jsonrpc.processes.populateBCPSecDetails(jsData6);
	
	    if(test6.list.length>0){ //if logic added by saurabh
	    	//lawkush
	    	document.getElementById('txtSecCustLocationSuggest').value = test6.list[0].bcpName;
		//lawkush
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
	}else{
		//lawkush
		document.getElementById('txtSecCustLocationSuggest').value = "" ;
		//lawkush
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

function FetchPNLocationAddress(){
	if(document.getElementById("ddPNLocation").value != 0){
		var tr, td, i, j, oneRecord;
	    var test7;
	   
	    var jsData7 ={
			plocationCode:document.getElementById("ddPNLocation").value
		};
		
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	
	    test7 = jsonrpc.processes.populateNPLocationAddress(jsData7);
	
	    if(test7.list.length>0){ //if logic added by saurabh
	    	document.getElementById('txtPAddress').value = test7.list[0].ptxtPAddress;
	    }
    }else{ 
       document.getElementById('txtPAddress').value="";
    }
}

function FetchSNLocationAddress(){
 	if(serviceTypeId==4){
	 	 document.getElementById('allLoc').style.display='block';
	   	 document.getElementById('onlyNetworkLoc').style.display='none';
	}else{ 	
	 	document.getElementById('onlyNetworkLoc').style.display='none';
	   	document.getElementById('allLoc').style.display='block';
 	}
   	if(document.getElementById("ddSNLocation").value != 0){
		var tr, td, i, j, oneRecord;
		var test8;
	    var jsData8 ={
				plocationCode:document.getElementById("ddSNLocation").value
		};
				
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
			
		test8 = jsonrpc.processes.populateNSLocationAddress(jsData8);
			
		if(test8.list.length>0){ //if logic added by saurabh
	    	document.getElementById('txtSAddress').value = test8.list[0].stxtSAddress;
	    }
	}else{ 
		document.getElementById('txtSAddress').value="";
	}
	document.getElementById('SecNetworkLocation').style.display='block';
}


function fnCalculateChargeSumForServiceSummary(isServiceSummary, isCharge, isHarwareSale)
{
	if(isCharge && isServiceSummary && isHarwareSale ==3)
	{
		var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
			var attributeID=new Array();
			//var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
			var totalAmount = 0.0;		
			/*for(i=0;i<chkChargeLine.length;i++)
			{
				var chkboxCharge=chkChargeLine[i];
				var index=chkboxCharge.value;
				totalAmount = parseFloat(totalAmount) + parseFloat(document.getElementById("txtCTAmt"+index).value);
				
			}*/
			for(var i=1;i<=chargeSumCounter;i++)
			{	
				if(document.getElementById("hdnCTAmt"+i)!=null)
				{
					if(document.getElementById("hdnCTAmt"+i).value==null || document.getElementById("hdnCTAmt"+i).value=="")
						document.getElementById("hdnCTAmt"+i).value=0.0;
					
					totalAmount = parseFloat(totalAmount) + parseFloat(document.getElementById("hdnCTAmt"+i).value);
				}
			}
			var values = attMasterIdList.split(",");
			//alert('values = '+values);
			if (values.length > 0) 
			{	
				for (var i = 0; i < values.length; i++) 
				{
				 //alert('att id described value = '+values[i]);
				 	for(j=1;j<=countAttributes;j++)
				 	{ //alert('DB att value = '+document.getElementById('hdnProdAttVal_'+j).value)
					  if(document.getElementById('prodExpVal_'+j).value != "DROPDOWN" && document.getElementById('prodExpVal_'+j).value != "LOV" && values[i] == document.getElementById('hdnProdAttVal_'+j).value)
						{							
									document.getElementById('prodAttVal_'+j).value = totalAmount;	
									break;
						}
					}
				}
			}
	}
	
}

function saveProductCatelog(){
	//[019] start
	var callerWindowObj = dialogArguments;
	if(callerWindowObj.gb_modeName != null && callerWindowObj.gb_modeName == 'viewMode'){
		return false;
	}
	//[019]end
	
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
		getComponentAttributes();
	if(!locationDetailsFetched)
		getLocationDetails();
	
	if(serviceListArray.length==0){
		var servicesID = serviceNo;
		var serviceNos = serviceNo;
	}else{
		var servicesID=Number(document.getElementById('id_span_ServiceNo').innerHTML);
		var serviceNos=Number(document.getElementById('id_span_ServiceNo').innerHTML);
	} 
	var but;
	var butSummaryFlag = false;
	var butBillFlag = false;
	var butLocFlag = false;
	var butHWFlag = false;
	var butDispFlag = false;
	var butWarranFlag = false;
	var componentsList;
	/*Puneet Commenting for taxation auto suggest
	if(document.getElementById('taxationID').value==2 && document.getElementById("changeReason").value==0)*/
	if(document.getElementById("txtTaxation").value==2 && document.getElementById("changeReason").value==0){
		alert("Please Select Standard Reason");
		return false;
	}
	if(!(isView==null || isView == 'null')){ //to check if through view order then no saving
		return false;
	}
	var callerWindowObj = dialogArguments;
	var orderNo = callerWindowObj.document.getElementById('poNumber').value
	
	//var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleName,userId);
	var shortCode = jsonrpc.processes.getShortCode(roleName);
	
	if(orderDetails.list.length>0)
	{
		stage=orderDetails.list[0].stageName;
		if(shortCode==stage)
		{
			//do nothing
		}
		else if(stage=="New" || stage=="NEW")
		{
			//do nothing
		}
		else if(stage=="Billing Trigger" && shortCode=="COPC")
		{
			alert("You are not authorised to save or update the values");
			return false;
		}
		else if(stage=="Partial Publish" && (shortCode=="COPC" || shortCode=="SED"))
		{
			//do nothing
		}
		/* As discussed SED can update the product catalogue
		 * else if(shortCode=="SED" || stage=="SED")
		{
			alert("You are not authorised to save or update the values");
			return false;
		}*/
		else
		{
			//do nothing		
		}
	}
	var serviceLength = callerWindowObj.document.forms[0].chkService.length;
	for(var m=0;m<serviceLength;m++)
	{
		if(callerWindowObj.document.getElementById('txtServiceNo'+(m+1)).value==serviceNos)
		{
			if(callerWindowObj.document.getElementById('chk'+m).value=="Yes")
			{
				alert("The Service of this product has been published already \n You cannot save or edit the data");
				return false;
			}
			if(callerWindowObj.document.getElementById('serviceStatus'+(m+1)).value=="CancelAndCopy" || callerWindowObj.document.getElementById('serviceStatus'+(m+1)).value=="Cancelled in CRM")
			{	
				alert("Service cancelled , you cant save now !!");
				return false;
			}
			//Puneet adding for performance tuning
			break;
		}
	}
	// TODO : CALCULATE CHARGES SUM N ASSIGN TO A VARIABLE IN SS
	
	fnCalculateChargeSumForServiceSummary(document.getElementById('hdnServiceSummary').value,document.getElementById('hdnChargeInfo').value,document.getElementById('txtBillingformat').value);
		
	var eLogicalCircuitId="";
	var infraOderNo="";
	var metasolvCircuitId="";
	var eLogicalCircuitId_new="";
	var infraOderNo_new="";
	var metasolvCircuitId_new="";
	var linKageFlag="NEW"
	//For Service Summary 
	if(document.getElementById('hdnServiceSummary').value==1)
	{
	
		but=document.getElementById('btnServiceSummary');
		
		if(but.value=="+")
		{
			butSummaryFlag=true;
			show('tblServiceSummary',but);
		}
		//For Service Summary 
		var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
		var attributeID=new Array();
		var attributeVal=new Array();
		var attributeExpVal=new Array();
		var attributeName=new Array();
		var attributeMandatory=new Array();
		var attServiceSummMand = new Array();
		var attfor_validation =new  Array();
		var attributeMaxLength = new Array();
		var flagForDropdown;
		// [101010] START
		var logicalCircuitIdLength=document.getElementById('eLogicalCircuitId').value.length;
		var infraOrderNoLength=document.getElementById('InfraOderNo').value.length;
		var metasolvCircuitIdLength=document.getElementById('metasolvCircuitId').value.length;
		if(logicalCircuitIdLength > 25)
		{
		alert("Logical Circuit ID can not be more than 25 char");
		document.getElementById('eLogicalCircuitId').focus();
		return false;
		}
		else if(infraOrderNoLength > 25)
		{
		alert("Infra Order No can not be more than 25 char");
		document.getElementById('InfraOderNo').focus();
		return false;
		}
		else if(metasolvCircuitIdLength > 25)
		{
		alert("Metasolv Circuit ID can not be more than 25 char");
		document.getElementById('metasolvCircuitId').focus();
		return false;
		}
		// [101010] END	
			
		for(i=1;i<=countAttributes;i++)
		{
			if(document.getElementById('prodExpVal_'+i).value == "DROPDOWN" || document.getElementById('prodExpVal_'+i).value == "LOV" )
			{
				if(document.getElementById('prodAttMandatory_'+i).value=="Y" && document.getElementById('prodAttVal_'+i).value=="0") 
				{
					if(document.getElementById('prodAttVal_'+i).isSummaryReq==1)
					{
						alert("Please input values in Service Summary section!!");
						//if(document.getElementById('prodExpVal_'+i).value == "LOV"){						
							document.getElementById('autoSuggProdAttVal_'+i).focus();
							return false;
						//}
						//else{
						//document.getElementById('prodAttVal_'+i).focus();
						//return false;
						//}
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
						attributeMaxLength[i-1]=document.getElementById('prodAttMaxLength_'+i).value;
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
					attributeMaxLength[i-1]=document.getElementById('prodAttMaxLength_'+i).value;
				}
			}
			else
			{	
				if(document.getElementById('prodAttMandatory_'+i).value=="Y" && document.getElementById('prodAttVal_'+i).value=="" ) 
				{
					if(document.getElementById('prodAttVal_'+i).isSummaryReq==1)
					{
						alert("Please input values in Service Summary section!!");
						document.getElementById('prodAttVal_'+i).focus();
						return false;
					}
					else
					{
						if(document.getElementById('hdnProdAttVal_'+i).value == 2470 && flagForDropdown == 2)
						{ 
							//var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    	       				var flag = jsonrpc.processes.lsiValidationForMediaExchangeAssociation(document.getElementById('prodAttVal_'+i).value);
    	       				if(flag == 1)
    	       				{
								alert('Please enter a valid LSI');
								document.getElementById('prodAttVal_'+i).focus();
								return false;
							}
						}
						if(document.getElementById('hdnProdAttVal_'+i).value == 2476 && flagForDropdown == 1)
						{ 
							//var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    	       				var flag = jsonrpc.processes.lsiValidationForMediaExchangeAssociation(document.getElementById('prodAttVal_'+i).value);
    	       				if(flag == 1)
    	       				{
								alert('Please enter a valid LSI');
								document.getElementById('prodAttVal_'+i).focus();
								return false;
							}
						}
						//[008] Start
						if((document.getElementById('hdnProdAttVal_'+i).value == 3932) || (document.getElementById('hdnProdAttVal_'+i).value == 3944) || 
						(document.getElementById('hdnProdAttVal_'+i).value == 3948)) //Checking for Duplicay of Dail Comid, IRN No,Toll Free No and TGNO No
						{
							//var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	   	       				var flag = jsonrpc.processes.checkDuplicateAttributes(document.getElementById('prodAttVal_'+i).value,document.getElementById('hdnProdAttVal_'+i).value,document.getElementById('hdnServiceProductID').value);
	   	       				if(flag == 1)
	   	       				{
								alert('Data Already Exists, Please Input Some Other Value');
								document.getElementById('prodAttVal_'+i).focus();
								return false;
							}
						}
						//[008] End
						attributeVal[i-1]=document.getElementById('prodAttVal_'+i).value;
						attributeID[i-1]=document.getElementById('hdnProdAttVal_'+i).value;
						attributeExpVal[i-1]=document.getElementById('prodExpVal_'+i).value;
						attributeName[i-1]=document.getElementById('prodAttName_'+i).value;
						attributeMandatory[i-1]=document.getElementById('prodAttMandatory_'+i).value;
						attServiceSummMand[i-1]=document.getElementById('prodAttVal_'+i).isSummaryReq;
						attributeMaxLength[i-1]=document.getElementById('prodAttMaxLength_'+i).value;
					}
				}
				else
				{
					if(document.getElementById('hdnProdAttVal_'+i).value == 2470 && flagForDropdown == 2)
						{ 
							//var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    	       				var flag = jsonrpc.processes.lsiValidationForMediaExchangeAssociation(document.getElementById('prodAttVal_'+i).value);
    	       				if(flag == 1)
    	       				{
								alert('Please enter a valid LSI');
								document.getElementById('prodAttVal_'+i).focus();
								return false;
							}
						}
						if(document.getElementById('hdnProdAttVal_'+i).value == 2476 && flagForDropdown == 1)
						{ 
							//var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    	       				var flag = jsonrpc.processes.lsiValidationForMediaExchangeAssociation(document.getElementById('prodAttVal_'+i).value);
    	       				if(flag == 1)
    	       				{
								alert('Please enter a valid LSI');
								document.getElementById('prodAttVal_'+i).focus();
								return false;
							}
						}
						//[008] Start
						if((document.getElementById('hdnProdAttVal_'+i).value == 3932) || (document.getElementById('hdnProdAttVal_'+i).value == 3944) || 
						(document.getElementById('hdnProdAttVal_'+i).value == 3948)) //Checking for Duplicay of Dail Comid, IRN No,Toll Free No and TGNO No
						{
							//var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	   	       				var flag = jsonrpc.processes.checkDuplicateAttributes(document.getElementById('prodAttVal_'+i).value,document.getElementById('hdnProdAttVal_'+i).value,document.getElementById('hdnServiceProductID').value)
	   	       				if(flag == 1)
	   	       				{
								alert('Data Already Exists, Please Input Some Other Value');
								document.getElementById('prodAttVal_'+i).focus();
								return false;
							}
						}
						//[008] End						
					attributeVal[i-1]=document.getElementById('prodAttVal_'+i).value;
					attributeID[i-1]=document.getElementById('hdnProdAttVal_'+i).value;
					attributeExpVal[i-1]=document.getElementById('prodExpVal_'+i).value;
					attributeName[i-1]=document.getElementById('prodAttName_'+i).value;
					attributeMandatory[i-1]=document.getElementById('prodAttMandatory_'+i).value;
					attServiceSummMand[i-1]=document.getElementById('prodAttVal_'+i).isSummaryReq;
					attributeMaxLength[i-1]=document.getElementById('prodAttMaxLength_'+i).value;
				}
			}
			// added by manisha for service summary validation start defect no : 19032013001
			
			attfor_validation[i-1]=document.getElementById('txt_prod_attvalidation_'+i).value;
			
				if(fproduct_att_validation(attributeName[i-1],attributeVal[i-1],attributeExpVal[i-1],attfor_validation[i-1],attServiceSummMand[i-1])==false)
				{
					return false;
				
				}
			
			// end	
			
		}
		 eLogicalCircuitId=document.getElementById('eLogicalCircuitId').value;
			 infraOderNo=document.getElementById('infraOderNo').value;
			 metasolvCircuitId=document.getElementById('metasolvCircuitId').value;	
	}
	
	but=document.getElementById('btnServiceSummary');
		
		if(but.value=="-" && butSummaryFlag==true)
		{
			show('tblServiceSummary',but);
		}
	
	//For Billing Information
	if(document.getElementById('hdnBillingInfo').value==1){	
		if(selectAutoSuggest("txtPODetailNo","Cust PO Detail No", "poDetailNo")==false)
			return false;
		if(selectAutoSuggest("txtEntity","Entity", "legalEntityTXT")==false)
			return false;
		if(selectAutoSuggest("txtBillingformat","Billing Format", "billingFormat")==false)
			return false;
		if(selectAutoSuggest("txtLicenceCo","Licence Company", "licenseCo")==false)
			return false;
		if(selectAutoSuggest("txtBillingType","Billig Type", "billingType")==false)
			return false;
		if(selectAutoSuggest("txtTaxation","Taxation","taxationID")==false)
			return false;
		if(selectAutoSuggest("txtBillingLvl","Billing Level", "billingLevel")==false)
			return false;
		//[009] Start	
		//alert(document.getElementById('hdnConfigValue').value)
		if(document.getElementById('hdnConfigValue').value==1){
			//[017] Start
			if(document.getElementById('txtLicenceCo').value!=282 && document.getElementById('txtLicenceCo').value!=382){
				alert("Invalid License Company!!For 95p Orders, You have to select 402-BAL 95P or 402-BAL 95P_GB License Company");
				return false;
			} //[017] End
		}
		//[009] End	
	}
	//For Location
	if(document.getElementById('hdnLocationInfo').value==1){
		if(selectDropdown("ddPrimaryLocType","Primary Location")==false)
			return false;
		if(document.getElementById('ddPrimaryLocType').value=="1"){
			if(selectDropdown("txtPriCustLocationSuggest","Primary BCP ID")==false)
				return false;
		}
		if(selectDropdown("ddSecondaryLocType","Secondary Location")==false)
			return false;
		if(document.getElementById('ddSecondaryLocType').value=="1"){
			if(selectDropdown("txtSecCustLocationSuggest","Secondary BCP ID")==false)
				return false;
		}
	}
	//For Hardware
	if(document.getElementById('hdnHardwareInfo').value==1){
		if(selectAutoSuggest("txtStore","Store","txtStoreText")==false)
			return false;
		if(selectDropdown("txtdispatchAddress","Dispatch Address Code")==false)
			return false;
	}
	if(document.getElementById('hdnComponentInfo').value==1){
		var myComponentList=[];
		var chkComponentLine=document.getElementsByName('chkComponents');
		var myComponentList=[];
		for(i=0;i<chkComponentLine.length;i++){
			var chkboxComponent=chkComponentLine[i];
			//var index=document.getElementById("chkComponents"+i).value;
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
	if(document.getElementById('hdnChargeInfo').value==1){
		var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		for(i=0;i<chkChargeLine.length;i++){
			var chkboxCharge=chkChargeLine[i];
			var index=chkboxCharge.value;
			if(checkTotalAmount()==false){
				return false;
			}
		}
	}
	if(document.getElementById('hdnChargeInfo').value==1){
		var myChargeList=[];
		var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		var myList=[];
		for(i=0;i<chkChargeLine.length;i++){
			var chkboxCharge=chkChargeLine[i];
			var index=chkboxCharge.value;
			//[012] Start
			if((document.getElementById('hdnHardwareInfo').value==1) || (document.getElementById('hdnHardwareInfo').value!=1)) {
				
				var payment11=document.getElementById('txtPayment1'+index).value;
				var payment22=document.getElementById('txtPayment2'+index).value;
				var payment33=document.getElementById('txtPayment3'+index).value;
				var payment44=document.getElementById('txtPayment4'+index).value;
				if(payment11 == "" && payment22 == "" && payment33 == "" && payment44 == ""){
					/*payment1=0;
					payment2=0;
					payment3=0;
					payment4=0;*/
					//[012] Start - Payment Term has been made mandatory
					document.getElementById('txtPayment1'+index).value=0;
					document.getElementById('txtPayment2'+index).value=0;
					document.getElementById('txtPayment3'+index).value=0;
					document.getElementById('txtPayment4'+index).value=0;
					alert("Please input Payment Terms");
					return false;
					//[012] End - Payment Term has been made mandatory
				}
				/*[012] Start
				else if(payment11 == "0" && payment22 == "0" && payment33 == "0" && payment44 == "0"){
					payment1="";
					payment2="";
					payment3="";
					payment4="";
				}[012] End*/
				else{
					if (payment11 > 100 || payment22 > 100 || payment33 > 100 || payment44 > 100 ){
						alert('Sum of all payment terms should be equal to 100');
						//document.getElementById('txtPayment1'+index).value = "";
						//document.getElementById('txtPayment2'+index).value = "";
						//document.getElementById('txtPayment3'+index).value = "";
						//document.getElementById('txtPayment4'+index).value = "";
						document.getElementById('txtPayment1'+index).focus();
						return false;
					}
					if (payment11 == ""){
						payment11=0;
					}
					if (payment22 == ""){
						payment22=0;
					}
					if (payment33 == ""){
						payment33=0;
					}
					if (payment44 == ""){
						payment44=0;
					}
					var payment = parseFloat(payment11) + parseFloat(payment22) + parseFloat(payment33) + parseFloat(payment44);
					if(payment != 100){	
						alert('Sum of all payment terms should be equal to 100');
						document.getElementById('txtPayment1'+index).focus();
						return false;
					}else{
						payment1=payment11;
						payment2=payment22;
						payment3=payment33;
						payment4=payment44;
					}
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
						//lineItemName:document.getElementById("cmbLineItem"+index).value, - Puneet commented as talked with Sumit
						//subLineItemName:document.getElementById("cmbSubLineItem"+index).value,
						ldDateClause:document.getElementById("txtLDDateClause"+index).value,
						delayedTimeInDayes:Number(document.getElementById("txtDelayedTimeInDays"+index).value),
						ldPercentage:Number(document.getElementById("txtLDPercentage"+index).value),
						maxPercentage:Number(document.getElementById("txtMaxPenaltyPercentage"+index).value),
						//isReqLineItem:Number(document.getElementById('cmbLineItem'+index).isRequired), - Puneet commented as discussed with Sumit
						//isReqSubLineItem:Number(document.getElementById('cmbSubLineItem'+index).isRequired),
						taxRate:document.getElementById("txtTaxRate"+index).value,
						isReqTxtRemarks:Number(document.getElementById("txtRemarks"+index).isRequired)
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
			var serviceDetID=document.getElementById("hdnServiceDetailID").value;
			if(serviceDetID==342 || serviceDetID==344 )
			{
			   var result=  CheckMPLSWithEnterpriseSignage(attributeID,attributeVal);
			   if(result == false)
			    return;
			}
		var chargesList={"javaClass": "java.util.ArrayList",
				"list": myList
				};
				
			
		var jsData =
		{
			prodAttID: attributeID,
			prodAttValue:attributeVal,
			serviceDetailID:serviceProductID,
			serviceProductID:Number(document.getElementById('hdnServiceProductID').value),
			serviceId:servicesID,
			podetailID:document.getElementById('txtPODetailNo').value,
			chargesDetails : chargesList
		};

	    //jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		   try{
		    var retDto = jsonrpc.processes.validateCharges(jsData);
		    }
		    catch(e)
		    {	alert("exception :  "+ e);
		    }
		    
    var chargesList={"javaClass": "java.util.ArrayList",
			"list": myList};
    var chargesDetailList;
     var excludecharge=0;
    	var myChargeList=[];
		var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		var myList=[];
		totalExtraChrgeAmount=0;
		
		for(i=0;i<chkChargeLine.length;i++)
		{
			var chkboxCharge=chkChargeLine[i];
			var index=chkboxCharge.value;
			if(document.getElementById('chkexcludeCharges'+index).checked==true)
			{
			   excludecharge=1;
			  
			}
			
			
			else
			
			{
			    excludecharge=0;
			}
			//Start[007]
			
			//below code added by Anil for CLEP requirement
			if(clepState==1){
				if(document.getElementById("hdnChargeId"+index).value=="" ||document.getElementById("hdnChargeId"+index).value=="0" ){
					totalExtraChrgeAmount=Number(totalExtraChrgeAmount)+Number(document.getElementById("txtCTAmt"+index).value);
				}
			}
			//end CLEP
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
						isReqTxtCStartDays:Number(document.getElementById('txtStartDays'+index).isRequired),
						isReqTxtCStartMonths:Number(document.getElementById('txtStartMonth'+index).isRequired),
						isReqTxtCEndDays:Number(document.getElementById('txtEndDays'+index).isRequired),
						isReqTxtCEndMonths:Number(document.getElementById('txtEndMonth'+index).isRequired),
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
						isReqTxtRemarks:Number(document.getElementById("txtRemarks"+index).isRequired)
						};
			myList[i]=ob;
		}
		chargesDetailList={"javaClass": "java.util.ArrayList",
				"list": myList
				};
				
    }
   
    if(document.getElementById('hdnServiceSummary').value==1){
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
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('legalEntityTXT').focus();
			return false;
		}else if(txtEntity.isRequired=="0"  && txtEntity.value == "0" ){
			txtEntity.value = "0";
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
		}else if(txtBillingMode.isRequired=="0" && txtBillingMode.value == "0" ){
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
			/*but=document.getElementById('btnBillingInformation');
					if(but.value=="+")
					{
						show('tblBillingInformation',but);
					}
			but=document.getElementById('btnChargesDetails');
					if(but.value=="+")
					{
						show('tblChargesDetails',but);
					}*/
			//txtLicenceCo.focus();
			$("#licenseCo").focus();
			return false;
		}else if(txtLicenceCo.isRequired=="0" && txtLicenceCo.value == "0" ){
			document.getElementById('txtBillingformat').value=0;
			document.getElementById('billingFormat').value="Select Bill Format";
			txtLicenceCo.value=0;
			$("#licenseCo").val("Select Licence Company")
			
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
		//Puneet Chaging for auto suggest
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
		}if(document.getElementById('txtCmtPeriod').isRequired=="1"  && document.getElementById('txtCmtPeriod').value == "" )
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
				alert("Please enter Ship to Address");
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
			document.getElementById('txtBCPAddressSuggest').focus();
			return false;
		}
		else if(document.getElementById('bbPrimaryBCP').isRequired=="0"  && (document.getElementById('bbPrimaryBCP').value == "" || document.getElementById('txtBCPAddressSuggest').value==""))
		{
			document.getElementById('bbPrimaryBCP').value=0;
		}
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="-" && butBillFlag == true)
					{
						show('tblBillingAndChargesInformation',but);
					}
		var txtBillingLvl = document.getElementById('txtBillingLvl');			
		if(document.getElementById('hdnISFLEFLAG').value==1  && txtBillingLvl.value!=2 ){
				if(!(txtBillingLvl.value==3 && attFLI_PO_DisableValue =="Y")){
					alert("Billing Level should be PO Level");
					but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+"){
							butBillFlag = true
						show('tblBillingAndChargesInformation',but);
					}
					//document.getElementById('txtBillingLvl').focus();
					$("#billingLevel").focus();
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
			if((document.getElementById("ddPrimaryBCP").value=="0" || document.getElementById("txtPriCustLocationSuggest").value=="0")&& document.getElementById("ddPrimaryBCP").isRequired=="1")
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
			else if(document.getElementById('ddPrimaryBCP').isRequired=="0"  && (document.getElementById('ddPrimaryBCP').value == "0" || document.getElementById('txtPriCustLocationSuggest').value == "0" ))
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
			if((document.getElementById("ddSecondaryBCP").value=="0" || document.getElementById("txtSecCustLocationSuggest").value=="0" ) && document.getElementById("ddSecondaryBCP").isRequired=="1")
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
			alert("Please enter To Address");
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
		
	}
	but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="-" && butLocFlag == true)
					{
						show('tblServiceLocationDetails',but);
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
		var txtNSale = document.getElementById('txtNSale');
		if(txtNSale.value=="0" && txtNSale.isRequired=="1" ){
			alert("Please enter Nature of sale");
			but=document.getElementById('btnHwDetails');
			if(but.value=="+"){
					butHWFlag = true;
				show('tblHwDetails',but);
			}
			//document.getElementById('txtNSale').focus();
			$("#textSaleNature").focus();
			return false;
		}else if(txtNSale.value=="0" && txtNSale.isRequired=="0"){
			document.getElementById('txtNSale').value=0;
			$("#textSaleNature").val("Select Sale Nature");
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
					butDispFlag = true;					
					show('tblDispatchAddress',but);
				}
			//Start[RAAM]AutoSuggest for Dispatch	
			document.getElementById('txtDispatchAddressSuggest').focus();
			//End[RAAM]AutoSuggest for Dispatch	
			return false;
		}
		else if(document.getElementById('txtdispatchAddress').isRequired=="0"  && (document.getElementById('txtDispatchAddress').value == "" || document.getElementById('txtDispatchAddressSuggest').value=="" ))
		{
			document.getElementById('txtdispatchAddress').value=0;
		}
		but=document.getElementById('btnDispatchAddress');
				if(but.value=="-" && butDispFlag == true)
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
	
	}
		but=document.getElementById('btnWarrentyDetails');
				if(but.value=="-" && butWarranFlag == true)
				{
					show('tblWarrentyDetails',but);
				}
		if(document.getElementById("chkSelectNfa").value=="on")
			{
				document.getElementById("chkSelectNfa").value=0
			} 
	   
	    var jsData =
		{
			prodAttID: attributeID,
			prodAttValue:attributeVal,
			prodAttExptdValue:attributeExpVal,
			prodAttName:attributeName,
			prodAttMandatory:attributeMandatory,
			serviceSummaryMandatory:attServiceSummMand,
			prodAttriMaxLength:attributeMaxLength,
			hdnSeriveAttCounter:Number(countAttributes),
			serviceProductID:Number(document.getElementById('hdnServiceProductID').value),
			serviceId:Number(servicesID),
			podetailID:Number(document.getElementById('txtPODetailNo').value),
			accountID:Number(document.getElementById('txtBillingAC').value),
			creditPeriod:Number(document.getElementById('txtBillingCP').value),
			entityID:Number(document.getElementById('txtEntity').value),
			billingType:Number(document.getElementById('txtBillingType').value),
			billingMode:document.getElementById('txtBillingMode').value,
			noticePeriod:Number(document.getElementById('txtNoticePeriod').value),
		    billingformat:document.getElementById('txtBillingformat').value,
		    billingInfoID:Number(document.getElementById('hdnBillingInfoID').value),
		    locationInfoID:Number(document.getElementById('hdnServiceInfoID').value),
		    hardwareDetailID:Number(document.getElementById('hdnHardwareInfoID').value),
			licenceCoID:Number(document.getElementById('txtLicenceCo').value),
			//Puneet Changing for taxation auto suggest
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
		    isNfa:document.getElementById('chkSelectNfa').value,
		    isUsage:document.getElementById('chkIsUsage').value,
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
		    //[003]start
			dispatchContactName:document.getElementById('txtDispatchContactName').value,
			//[003]end
            isReqTxtPODetailNo:document.getElementById('txtPODetailNo').isRequired,
			isReqTxtBillingAC:document.getElementById('txtBillingAC').isRequired,
			isReqTxtBillingCP:document.getElementById('txtBillingCP').isRequired,
			isReqTxtEntity:document.getElementById('txtEntity').isRequired,
			isReqTxtBillingMode:document.getElementById('txtBillingMode').isRequired,
			isReqTxtBillingformat:document.getElementById('txtBillingformat').isRequired,
			isReqTxtLicenceCo:document.getElementById('txtLicenceCo').isRequired,
			isReqtaxationId:document.getElementById('txtTaxation').getAttribute('isRequired'),
			isReqTxtCmtPeriod:document.getElementById('txtCmtPeriod').isRequired,
			
			isReqTxtBillingLvl:document.getElementById('txtBillingLvl').isRequired,
			isReqTxtPenaltyClause:document.getElementById('txtPenaltyClause').isRequired,
			isReqTxtWarrantyClause:Number(document.getElementById('txtWarrantClause').isRequired),
			isReqSelectedDispatchID:document.getElementById('txtdispatchAddress').isRequired,
			isReqDdPrimaryLocType:document.getElementById('ddPrimaryLocType').isRequired,
			isReqDdSecondaryLocType:document.getElementById('ddSecondaryLocType').isRequired,
			isReqSelectedPNLocation:document.getElementById('ddPNLocation').isRequired,
			isReqSelectedPrimaryBCP:document.getElementById('ddPrimaryBCP').isRequired,
			isReqSelectedSNLocation:document.getElementById('ddSNLocation').isRequired,
			isReqSelectedSecBCP:document.getElementById('ddSecondaryBCP').isRequired,
			isReqTxtWarrantyClause:Number(document.getElementById('txtWarrantClause').isRequired),
			//satyapan OSP By nagarjuna
			isReqOSPTagging:Number(document.getElementById('txtOSPTagging').isRequired),//satyapan osp
			isReqOSPRegNo:Number(document.getElementById('txtOSPRegNo').isRequired),
			isReqOSPRegDate:document.getElementById('txtOSPRegDate').isRequired,
			//Satyapan OSP By Nagarjuna
			isReqTxtStore:document.getElementById('txtStore').isRequired,
			isReqTxtHtype:document.getElementById('txtHtype').isRequired,
			isReqTxtform:document.getElementById('txtform').isRequired,
			isReqTxtTSale:document.getElementById('txtTSale').isRequired,
			isReqTxtNSale:document.getElementById('txtNSale').isRequired,
			isReqStartDateLogic:document.getElementById('txtStartDateLogic').isRequired,
			isReqEndDateLogic:document.getElementById('txtEndDateLogic').isRequired,
			isReqStartDate:document.getElementById('txtStartDate').isRequired,
			isReqEndDate:document.getElementById('txtEndDate').isRequired,
			serviceInfoValue:document.getElementById('hdnServiceSummary').value,
			isReqTxtFromLocation:document.getElementById('txtFAddress').isRequired,
			isReqTxtToLocation:document.getElementById('txtToAddress').isRequired, // by Saurabh for validation of to and from location
			poNumber:poNumber,
			componentDetails:componentsList,
			componentInfoValue:Number(document.getElementById('hdnComponentInfo').value),
			configValue:Number(document.getElementById('hdnConfigValue').value),
			deletedChargesList:document.getElementById('hdnDeletedChargesListId').value		
		};
	}
	else{
		if(document.getElementById('hdnBillingInfo').value==1){
		if(document.getElementById('txtPODetailNo').isRequired=="1"  && document.getElementById('txtPODetailNo').value == "0" ){
			alert("Please enter PO ID");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+"){
						butBillFlag = true;
				show('tblBillingAndChargesInformation',but);
			}
			document.getElementById('poDetailNo').focus();
			return false;
		}else if(document.getElementById('txtPODetailNo').isRequired=="0"  && document.getElementById('txtPODetailNo').value == "0" ){
			document.getElementById('txtPODetailNo').value=0;
			document.getElementById('poDetailNo').value="";
		}
		if(document.getElementById('txtBillingCP').isRequired=="1"  && document.getElementById('txtBillingCP').value == "0" ){
			alert("Please enter Credit Period");
			but=document.getElementById('btnBillingAndChargesInformation');
			if(but.value=="+"){
						butBillFlag = true;
				show('tblBillingAndChargesInformation',but);
			}
			document.getElementById('billingCP').focus();
			return false;
		}else if(document.getElementById('txtBillingCP').isRequired=="0"  && document.getElementById('txtBillingCP').value == "0"){
			document.getElementById('txtBillingCP').value=0;
			document.getElementById('billingCP').value="";
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
		if(txtBillingformat.isRequired=="1" && txtBillingformat.value == "0" ){
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
			$("#licenseCo").val("Select Licence Company")
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
		//Puneet changing for taxation auto suggest
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
		if(document.getElementById('txtCmtPeriod').isRequired=="1"  && document.getElementById('txtCmtPeriod').value == "" ){
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
				alert("Please enter Ship to Address");
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
		/*if(document.getElementById('bbPrimaryBCP').isRequired=="1"  && document.getElementById('bbPrimaryBCP').value == "" )
		{
			alert("Please enter   BCP details");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						show('tblBillingAndChargesInformation',but);
					}
			}*/
		if(document.getElementById('bbPrimaryBCP').isRequired=="1"  && (document.getElementById('bbPrimaryBCP').value == "" || document.getElementById('txtBCPAddressSuggest').value=="") )
		{
			alert("Please enter   BCP details");
			but=document.getElementById('btnBillingAddress');
				if(but.value=="+")
				{
					show('tblBillingAddress',but);
				}
			document.getElementById('txtBCPAddressSuggest').focus();
			return false;
		}
		else if(document.getElementById('bbPrimaryBCP').isRequired=="0"  && (document.getElementById('bbPrimaryBCP').value == "" || document.getElementById('txtBCPAddressSuggest').value=="") )
		{
			document.getElementById('bbPrimaryBCP').value=0;
		}
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="-" && butBillFlag == true)
					{
						show('tblBillingAndChargesInformation',but);
					}
					
		var txtBillingLvl = document.getElementById('txtBillingLvl');			
		if(document.getElementById('hdnISFLEFLAG').value==1  && txtBillingLvl.value!=2 ){
			if(!(txtBillingLvl.value==3 && attFLI_PO_DisableValue =="Y")){
					alert("Billing Level should be PO Level");
					but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+"){
							butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
					//document.getElementById('txtBillingLvl').focus();
					$("#billingLevel").focus();
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
			if((document.getElementById("ddPrimaryBCP").value=="0" || document.getElementById("txtPriCustLocationSuggest").value=="0"  ) && document.getElementById("ddPrimaryBCP").isRequired=="1")
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
			else if(document.getElementById('ddPrimaryBCP').isRequired=="0"  && (document.getElementById('ddPrimaryBCP').value == "0" || document.getElementById('txtPriCustLocationSuggest').value == "0"))
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
			document.getElementById('txtStoreText').focus();
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
			$("#hTypeText").focus();
			return false;
		}else if(txtHtype.value=="0" && txtHtype.isRequired=="0"){
			txtHtype.value=0;
			$("#hTypeText").val("Select Hardware Type");
		}
		if(document.getElementById('txtform').value=="0" && document.getElementById('txtform').isRequired=="1" ){
			alert("Please enter Form available");
			but=document.getElementById('btnHwDetails');
			if(but.value=="+"){
					butHWFlag = true;
				show('tblHwDetails',but);
			}
			//document.getElementById('txtform').focus();
			document.getElementById('textFormTXT').focus();
			return false;
		}else if(document.getElementById('txtform').value=="0" && document.getElementById('txtform').isRequired=="0" ){
			document.getElementById('txtform').value=0;
			document.getElementById('textFormTXT').value="Select Form";
		}
		if(txtNSale.value=="0" && txtNSale.isRequired=="1" ){
			alert("Please enter Nature of sale");
			but=document.getElementById('btnHwDetails');
			if(but.value=="+"){
					butHWFlag = true;
				show('tblHwDetails',but);
			}
			//document.getElementById('txtNSale').focus();
			$("#textSaleNature").focus();
			return false;
		}else if(txtNSale.value=="0" && txtNSale.isRequired=="0"){
			document.getElementById('txtNSale').value=0;
			$("#textSaleNature").val("Select Sale Nature");
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
				if(but.value=="-")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
		if(document.getElementById('txtdispatchAddress').isRequired=="1"  && (document.getElementById('txtDispatchAddress').value == "" || document.getElementById('txtDispatchAddressSuggest').value=="" ) )
		{
			alert("Please enter Dispatch Address");
			but=document.getElementById('btnDispatchAddress');
				if(but.value=="+")
				{
					butDispFlag = true;
					show('tblDispatchAddress',but);
				}
			//Start[RAAM]AutoSuggest for Dispatch	
			document.getElementById('txtDispatchAddressSuggest').focus();
			//End[RAAM]AutoSuggest for Dispatch	
			return false;
		}
		else if(document.getElementById('txtdispatchAddress').isRequired=="0"  && (document.getElementById('txtDispatchAddress').value == "" || document.getElementById('txtDispatchAddressSuggest').value=="" ) )
		{
			document.getElementById('txtdispatchAddress').value=0;
		}
		but=document.getElementById('btnDispatchAddress');
				if(but.value=="-" && butDispFlag == true)
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
	}
	
	else if((document.getElementById('hdnHardwareInfo').value==0))
	{
		document.getElementById('txtStartDateLogic').value=0;
		document.getElementById('txtEndDateLogic').value=0;
	}
	but=document.getElementById('btnWarrentyDetails');
				if(but.value=="-" && butWarranFlag == true)
				{
					show('tblWarrentyDetails',but);
				}

		var jsData =
		{
			serviceDetailID:serviceProductID,
			serviceId:servicesID,
			podetailID:document.getElementById('txtPODetailNo').value,
			accountID:document.getElementById('txtBillingAC').value,
			creditPeriod:document.getElementById('txtBillingCP').value,
			entityID:document.getElementById('txtEntity').value,
			billingType:document.getElementById('txtBillingType').value,
			billingMode:document.getElementById('txtBillingMode').value,
			noticePeriod:document.getElementById('txtNoticePeriod').value,
			billingformat:document.getElementById('txtBillingformat').value,
			licenceCoID:document.getElementById('txtLicenceCo').value,
			taxation:document.getElementById('txtTaxation').value,
			stdReasonId:document.getElementById('changeReason').value,
			commitmentPeriod:document.getElementById('txtCmtPeriod').value,
			billingLevel:document.getElementById('txtBillingLvl').value,
			hardwareDetailID:document.getElementById('hdnHardwareInfoID').value,
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
			chargesDetails : chargesDetailList,
			serviceInfoValue:Number(document.getElementById('hdnServiceSummary').value),
			billingInfoID:Number(document.getElementById('hdnBillingInfoID').value),
			hardwareDetailID:Number(document.getElementById('hdnHardwareInfoID').value),
			serviceProductID:Number(document.getElementById('hdnServiceProductID').value),
			billingInfoValue:document.getElementById('hdnBillingInfo').value,
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
		    isUsage:document.getElementById('chkIsUsage').value,
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
		    
            isReqTxtPODetailNo:document.getElementById('txtPODetailNo').isRequired,
			isReqTxtBillingAC:document.getElementById('txtBillingAC').isRequired,
			isReqTxtBillingCP:document.getElementById('txtBillingCP').isRequired,
			isReqTxtEntity:document.getElementById('txtEntity').isRequired,
			isReqTxtBillingMode:document.getElementById('txtBillingMode').isRequired,
			isReqTxtBillingformat:document.getElementById('txtBillingformat').isRequired,
			isReqTxtLicenceCo:document.getElementById('txtLicenceCo').isRequired,
			isReqtaxationId:document.getElementById('txtTaxation').getAttribute('isRequired'),
			isReqTxtCmtPeriod:document.getElementById('txtCmtPeriod').isRequired,
			isReqTxtBillingLvl:document.getElementById('txtBillingLvl').isRequired,
			isReqTxtPenaltyClause:document.getElementById('txtPenaltyClause').isRequired,
			isReqTxtWarrantyClause:Number(document.getElementById('txtWarrantClause').isRequired),
			//satyapan OSP By nagarjuna
			isReqOSPTagging:Number(document.getElementById('txtOSPTagging').isRequired),//satyapan osp
			isReqOSPRegNo:Number(document.getElementById('txtOSPRegNo').isRequired),
			isReqOSPRegDate:document.getElementById('txtOSPRegNo').isRequired,
			//Satyapan OSP By Nagarjuna
			isReqSelectedDispatchID:document.getElementById('txtdispatchAddress').isRequired,
			isReqDdPrimaryLocType:document.getElementById('ddPrimaryLocType').isRequired,
			isReqDdSecondaryLocType:document.getElementById('ddSecondaryLocType').isRequired,
			isReqSelectedPNLocation:document.getElementById('ddPNLocation').isRequired,
			isReqSelectedPrimaryBCP:document.getElementById('ddPrimaryBCP').isRequired,
			isReqSelectedSNLocation:document.getElementById('ddSNLocation').isRequired,
			isReqSelectedSecBCP:document.getElementById('ddSecondaryBCP').isRequired,
			isReqTxtStore:document.getElementById('txtStore').isRequired,
			isReqTxtHtype:document.getElementById('txtHtype').isRequired,
			isReqTxtform:document.getElementById('txtform').isRequired,
			isReqTxtTSale:document.getElementById('txtTSale').isRequired,
			isReqTxtNSale:document.getElementById('txtNSale').isRequired,
			isReqStartDateLogic:document.getElementById('txtStartDateLogic').isRequired,
			isReqEndDateLogic:document.getElementById('txtEndDateLogic').isRequired,
			isReqTxtFromLocation:document.getElementById('txtFAddress').isRequired,
			isReqTxtToLocation:document.getElementById('txtToAddress').isRequired, // by Saurabh for validation of to and from location
			poNumber:poNumber,
			componentDetails:componentsList,
			componentInfoValue:Number(document.getElementById('hdnComponentInfo').value),
			configValue:Number(document.getElementById('hdnConfigValue').value),
	    		deletedChargesList:document.getElementById('hdnDeletedChargesListId').value			 
		};

	}
	/*if((document.getElementById('hdnServiceSummary').value==1) && attributeID != null)
	   {
	         var jsonrpc1 = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	         var serviceDetID=document.getElementById("hdnServiceDetailID").value;
    	       var productType = jsonrpc1.processes.checkProductServiceType(serviceDetID);
    	       if(productType=='Hardware')
    	       {
    	        var Billingformat=document.getElementById('txtBillingformat').value;
			    var result=  checkProductServiceType(attributeID,attributeVal,Billingformat);
			   if(result == false)
			    return;
			  }
	   }*/
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	try
	{
		//[005] Start
		// add by anil for clep
		if(totalExtraChrgeAmount > 0 && clepState==1){
			alert("Total Charge amount "+totalExtraChrgeAmount+" will be added in PO Amount");
			totalExtraChrgeAmount=0;
		}
		//end clep
    	var insertPcat = jsonrpc.processes.updateProductCatelog(jsData,gb_sessionid);
    	//[0021] if condition added
    	if(isNull(insertPcat.errors)==true){
    	resetProducCatalogueFlags();
    	}
    }
    catch(e)
    {	alert("exception :  "+ e);
    }
    
    if(isNull(insertPcat.errors)==true)
    {
		//var callerWindowObj = dialogArguments;
		//DrawTable('CHARGEINFOID','VIEWCHARGETABLE');
		FirstPage('CHARGEINFOID','VIEWCHARGETABLE');
    	alert(insertPcat.msgOut);  
    		var flag=5;
    	//---------[Reset Components Info and again populate components from DB,
    	//so that if user remain same page and again save it could not entry two times for new components]---------------
    		resetComponentsRows();
    		componentDetailsFetched = false;
    		getComponentAttributes();
    	//---------------------------------------------------
    	//callerWindowObj.ViewServiceTree(5);
    	/*//Puneet setting the css for each charge name
    	$("#tableCharges").find("[ctrltype='dropdownlink']").each(function() {
    		attachCSForAS($(this));
    	});*/
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
	var orderNo = callerWindowObj.document.getElementById('poNumber').value
	//var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleName,userId);
	var shortCode = jsonrpc.processes.getShortCode(roleNameId);
	
	if(orderDetails.list.length>0)
	{
		stage=orderDetails.list[0].stageName;
		if(shortCode==stage)
		{
			//do nothing
		}
		else if(stage=="New" || stage=="NEW")
		{
			//do nothing
		}
		else if(stage=="Billing Trigger" && shortCode=="COPC")
		{
			alert("You are not authorised to delete the data");
			return false;
		}
		else if(stage=="Partial Publish" && (shortCode=="COPC" || shortCode=="SED"))
		{
			//do nothing
		}
		else if(shortCode=="SED" || stage=="SED")
		{
			alert("You are not authorised to delete the data");
			return false;
		}
		else
		{
			//do nothing		
		}
	}
	var serviceLength = callerWindowObj.document.forms[0].chkService.length;
	for(var m=0;m<serviceLength;m++)
	{
		if(callerWindowObj.document.getElementById('txtServiceNo'+(m+1)).value==servicesID)
		{
			if(callerWindowObj.document.getElementById('chk'+m).value=="Yes")
			{
				alert("The Service of this product has been published already \n You cannot delete the data");
				return false;
			}
		}
	}
	
	if(window.confirm("Do you want to Delete this Product Catelog? View Products Page will close on deletion."))
	{
		var jsData =
		{
			serviceProductID:serviceProductID
		};
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
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
function FetchBillingDetails(){
	var tr, td, i, j, oneRecord;
    var test5;
   	if(document.getElementById("bbPrimaryBCP").value != ""){
	    var jsData5 = {
			bcpID:document.getElementById("bbPrimaryBCP").value
		};
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	    test5 = jsonrpc.processes.populateBCPDetails(jsData5);
	    if(test5.list.length>0){
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
			//011	Start
			document.getElementById('txtCircle').value = test5.list[0].revCircle;
			//011	End
		}
	}else{ 
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
		//011	Start
		document.getElementById('txtCircle').value = "";
		//011	End
	}
}


//bcp details for services ADDED BY MANISHA START
function FetchBillingDetailsforService()
{

	var tr, td, i, j, oneRecord;
    var test5;
   
   	if(document.getElementById("bbPrimaryBCPService").value != "" || document.getElementById("bbPrimaryBCPService").value!=0)
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

//bcp details for services ADDED BY MANISHA END
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
function getTree(treeView,nextNode,nextLabel,nextUrl,serviceDetailId,isCharge){
	var getNode="";
	var getNextNode="";
	var str="";
	var i;
	if(callerWindowObj.document.forms[0].SPIDUrlforVPC.value == nextUrl){
		str = "<li><span class='folder'><input type='radio' name='rdo1' id='rad" +ctindex+ "' value = "+ nextNode +" checked = 'checked' onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >";	
		callerWindowObj.document.forms[0].SPIDUrlforVPC.value = "";
	}else{
		str = "<li><span class='folder'><input type='radio' name='rdo1' id='rad" +ctindex+ "' value = "+ nextNode +" onclick=TreeNode("+ nextNode +",\'"+ escape(nextUrl) +"\',\'"+ escape(nextLabel) +"\') >";	
	}
	if(isCharge=="1")
		str=str+"<input type='hidden' name='hdnserviceDetailId' value='"+serviceDetailId+"'><input type='hidden' name='serviceDetailId' value='"+nextNode+"'><font style='color:#FF0000'>"+ nextLabel + "</font></span>";
	else
		str=str+"<input type='hidden' name='hdnserviceDetailId' value='"+serviceDetailId+"'><input type='hidden' name='serviceDetailId' value='"+nextNode+"'>"+ nextLabel + "</span>";
	ctindex++;
	for(i=0;i<treeView.lstTreeItems.list.length;i++){
		if(nextNode==treeView.lstTreeItems.list[i].serviceProductParentId){
			str=str+"<ul>"+getTree(treeView,treeView.lstTreeItems.list[i].serviceProductChildId,treeView.lstTreeItems.list[i].treeViewText,treeView.lstTreeItems.list[i].treeViewURL,treeView.lstTreeItems.list[i].serviceChildId,treeView.lstTreeItems.list[i].isChargePresent)+"</ul>";
		}
	}
	str=str+"</li>";
	return str;
}
function fncServiceTreeView(){
	document.getElementById("hdnServiceDetailID").value=serviceDetailID;
	document.getElementById('txtProductName').value=productName;
	
	document.getElementById('txtHdnServiceName').value=serviceName;
	var serviceTypeId=callerWindowObj.document.getElementById('hdnservicetypeid').value;
	document.getElementById('browser').innerHTML= "";
	var jsData = {
					serviceId:Number(serviceNo),  //TRNG22032013026 added by manisha
			    	orderNumber:poNumber
			    };
	//jsonrpc = new JSONRpcClient('<%=request.getContextPath()%>' + "/JSON-RPC");
	var treeView = jsonrpc.processes.ViewServiceTreeView(jsData);
	treeNodes = treeView;
	var url ="";
	var nodeText ="";
	var parentNodeId;
	var serviceDetailId;
	var isChargePresent=0;
	for(i=0;i<treeView.lstTreeItems.list.length;i++){
		if(treeView.lstTreeItems.list[i].serviceProductParentId=="0"){
			parentNodeId = treeView.lstTreeItems.list[i].serviceProductChildId;
			nodeText = treeView.lstTreeItems.list[i].treeViewText;
			serviceDetailId = treeView.lstTreeItems.list[i].serviceChildId;
			isChargePresent =  	treeView.lstTreeItems.list[i].isChargePresent;
			break;
		}
	}
	global_firstNode=parentNodeId;
	var treeString=getTree(treeView,parentNodeId,nodeText,"",serviceDetailId,isChargePresent);
 	var branches = $("<ul>" + treeString  + "</ul>").appendTo("#browser");
 	$("#browser").treeview({
 		add: branches
 	});
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
      	var path = requestContextPath+"/NewOrderAction.do?method=getTProductAttDetail&hdnserviceid="+serviceNo+'&hdnserviceTypeId='+serviceid+"&isView2=1&pc=1";		
		window.showModalDialog(path,window,"status:false;dialogWidth:500px;dialogHeight:300px");
		//window.open(path);
	 }
	 
}
//TRNG22032013026 added by manisha end
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
		if(window.confirm('Total Products in product level ' + unescape(nextLabel) + ' is/are ' + getTreeNodeCount(treeNodes,nextNode) + '\n' + "Do you want to save last product details?If Yes then Click Ok and Save the Data"))
		{
			fnCheckPreviousSelectedRadioButton(serviceProductID);
		}else {
		hdnLineItemName=nextLabel;
		resetChargesRows();
		resetComponentsRows();//Refresh Components on Tree Swaping with or without components tab on 17-April-2013
		var oTokens = unescape(nextUrl).tokenize('&',' ',true);
			
			productName=oTokens[5].substring(oTokens[5].indexOf("=")+1,oTokens[5].length);
			serviceName=oTokens[6].substring(oTokens[6].indexOf("=")+1,oTokens[6].length);
			serviceDetailID=oTokens[2].substring(oTokens[2].indexOf("=")+1,oTokens[2].length);
			serviceProductID=oTokens[3].substring(oTokens[3].indexOf("=")+1,oTokens[3].length);
			poNumber=oTokens[0].substring(oTokens[0].indexOf("=")+1,oTokens[0].length);
			document.getElementById('hdnServiceProductID').value=serviceProductID;
			document.getElementById('hdnServiceID').value=serviceDetailID;
			document.getElementById('BillingNChargeInfo').style.display='none';
			document.getElementById('BillingInfo').style.display='none';
			document.getElementById('ServiceLocation').style.display='none';
			document.getElementById('HardwareDetails').style.display='none';
			document.getElementById('ChargeDetails').style.display='none';
			document.getElementById('lineItemServiceSummary').style.display='none';
			document.getElementById('lblServiceProductId').innerHTML="LineItem No:"+serviceProductID;
			document.getElementById('Components').style.display='none';//Hide Components Section on Tree Swaping with components tab on 17-April-2013			
			getServiceAttributes();
		}
		
		insertScMBtnForView(serviceDetailID);
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

function resetChargesRows(){
	/*var mytable = document.getElementById('tableCharges');
	if(null != mytable.rows && null != mytable.rows.length){
		var iCountRows = mytable.rows.length;
		for (i =iCountRows-1; i >=0; i--){	
			mytable.deleteRow(i);
		}
	}
	mytable.innerHTML = "";*/
	var chargeTableDiv =  document.getElementById('contentscroll');
   	chargeTableDiv.innerHTML = "<table border='0' cellpadding='0' cellspacing='0' class='content' id='tableCharges'></table>";
}
/*Puneet commenting the function as it is not getting used
 * function fillAnnotationField(var_counter){	
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
//Puneet Changing for auto suggest
/*function getTaxRate(var_counter){		
	if(document.getElementById("txtTaxation").value==1){
		fetchTaxRateForAutoSuggest(path,var_counter);
	}else if(document.getElementById("txtTaxation").value==2){
		document.getElementById("txtTaxRate"+var_counter).value="";
	}
}*/
function PopLocTypePopup(LOC){
	if(!(isView == null || isView == 'null')){
		return false;
	}
	if (LOC=='PRILOC'){
		document.getElementById("location").value='PRILOC';
	}else{
		document.getElementById("location").value='SECLOC';
	}
	var path = requestContextPath+"/NewOrderAction.do?method=fetchNetworkPopLocation";		
	window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:530px");
}

function checkNfa(){
	if(document.getElementById("chkSelectNfa").checked==true){
		document.getElementById("chkSelectNfa").value=1
		alert("You are converting this order as NFA");
	}else{
		document.getElementById("chkSelectNfa").value=0
	}
}
document.onkeydown=checkKeyPress;
function checkKeyPress(){
	if (event.ctrlKey && event.keyCode == 83) {						
		//alert('This functionality is not allowed for this page.Please click on the SAVE button to save!! ');
		//return false;				
		if(true==savekeyPressScm)
		{
			
		saveProductCatalogForScm();
		document.getElementById("saveImageScm").tabIndex = -1;
		document.getElementById("saveImageScm").focus();	
		
		return;
		}
		if(document.getElementById('saveImage').disabled==false && $("#saveImage").is(":visible")){
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
window.onbeforeunload = function() 
{
	if(hook==true){
	if(serviceListArray.length==0){
		callerWindowObj.document.getElementById('hdnServiceProductID').value=serviceProductID;
		callerWindowObj.document.getElementById('hdnserviceid').value = serviceNo;
		callerWindowObj.fncServiceTreeView(serviceNo);
	}else{
		callerWindowObj.document.getElementById('hdnServiceProductID').value=initialSPID;
		callerWindowObj.document.getElementById('hdnserviceid').value = initialServiceNo;
	    callerWindowObj.fncServiceTreeView(initialServiceNo);
	}
	
	if (hook) 
	if ((window.event.clientX < 0) || (window.event.clientY < 0) || (window.event.clientX < -80)){
		{
			return "This will cause losing any unsaved data ! "
		} 
    }
	}
	else
	{
		if ((window.event.clientX < 0) || (window.event.clientY < 0) || (window.event.clientX < -80)){
			return "This will cause losing any unsaved data ! "
		} 
    }
}
function unhook() 
{
     hook=false;
}

//Start[020]
function fnCheckForM6LineItem()
{
	var sendToM6Check;
	if(document.getElementById('ddPrimaryLocType').value==2)
	{
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		sendToM6Check = jsonrpc.processes.getM6LineItemCheck(serviceDetailID);
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
		//var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
		sendToM6Check = jsonrpc.processes.getM6LineItemCheck(serviceDetailID);
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

function fnGetDestLabelValueForLOV(attMstrId,fieldName){
	var labelValue;
	//var textValue = fieldName.value;
	//var select_list_field = fieldName; 
	//var select_list_selected_index = select_list_field.selectedIndex;
	//var attLabelvalue = select_list_field.options[select_list_selected_index].text;
	var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
	var attLabelvalue=fieldName;
	var jsData ={
		attMasterId:attMstrId,
		attributeLabel:attLabelvalue
	};
	
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    labelValue = jsonrpc.processes.fillDropDownDependentlabel(jsData);
    if(labelValue.list.length>0){
		/* Puneet commented for performance tuning
		 for(var k=0;k<destAttID.length;k++){
			destAttID.pop();
		}*/
		destAttID=new Array();
		for(var j = 0;j<labelValue.list.length;j++){
			destAttID[j] = labelValue.list[j].destAttId;
			for(var i=1;i<=countAttributes;i++){
				if(document.getElementById("hdnProdAttVal_"+i) != null){
					if(document.getElementById("hdnProdAttVal_"+i).value == destAttID[j]){
						//[004.1] Start
						var depValue=labelValue.list[j].planName;//Assigning Default Value from DB to a Variable e.g prodAttVal_15 or abc 
						//destText Added By Kalpana to get Text for selectd Plan
						var destText=labelValue.list[j].destText;//Assigning Default TEXT Value from DB to destination attribute
						//end
						if(depValue.indexOf("prodAttVal")!=-1){//checking prodAttVal value in depValue varible via contains function of Javascript. if it founds it goes in if or in else
							var splitValue=depValue.split('_');//Spliting depValue by using _ So element1 is prodAttVal and element is 8 
							document.getElementById("prodAttVal_"+i).value=document.getElementById("prodAttVal_"+splitValue[1]).value;//assigning Dependent Value, 2nd elememnt of split function
						}else{
							document.getElementById("prodAttVal_"+i).value=depValue;//Assigning value from DB
							//set value in id autosuggest by kalpana
							if(document.getElementById("autoSuggProdAttVal_"+i)!=null){
								if(destText!=null){
									document.getElementById("autoSuggProdAttVal_"+i).value=destText;
								}else{
									document.getElementById("autoSuggProdAttVal_"+i).value=depValue;
								}
							}
							//end
						}
						//Start [005]
						/*if(document.getElementById("autoSuggProdAttVal_"+i) != null)
						{
							document.getElementById("autoSuggProdAttVal_"+i).value='';
						}*/
						//End [005]
						//[004.1] End
						if(labelValue.list[j].isReadOnly == '1'){
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
						if(labelValue.list[j].isReadOnly == '0'){
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
						if(labelValue.list[j].mandatory == '0'){
							document.getElementById("prodAttVal_"+i).isSummaryReq='0';
							document.getElementById("prodAttVal_"+i).className='inputBorder2';
						}else if(labelValue.list[j].mandatory == '1'){
							document.getElementById("prodAttVal_"+i).isSummaryReq='1';
							document.getElementById("prodAttVal_"+i).className='inputBorder1';
						}
						//[004.1] End
						//Puneet adding break for performance tuning
						break;
					}
				}
			}	
		}
	}
}
/*
Method fetched List of values for Dropdowns

*/
function fnGetDestLabelValue(attMstrId,fieldName)
{
	var labelValue;
	//lawkush Start
	//var textValue = fieldName.value;
	//var select_list_field = fieldName; 
	//var select_list_selected_index = select_list_field.selectedIndex;
	//var attLabelvalue = select_list_field.options[select_list_selected_index].text;
	// lawkush End
	var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
	//lawkush Start
	var attLabelvalue=fieldName;
	//lawkush End
	var jsData =			
    {
		attMasterId:attMstrId,
		attributeLabel:attLabelvalue
	};
	
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    labelValue = jsonrpc.processes.fillDropDownDependentlabel(jsData);
    if(labelValue.list.length>0)
	{
    	/*Puneet Commenting for performance tuning
		for(var k=0;k<destAttID.length;k++)
		{
			destAttID.pop();
		}*/
		destAttID=new Array();
		for(var j = 0;j<labelValue.list.length;j++){
			destAttID[j] = labelValue.list[j].destAttId;
			for(var i=1;i<=countAttributes;i++){
				if(document.getElementById("hdnProdAttVal_"+i) != null){
					if(document.getElementById("hdnProdAttVal_"+i).value == destAttID[j]){
						//[004.1] Start
						var depValue=labelValue.list[j].planName;//Assigning Default Value from DB to a Variable e.g prodAttVal_15 or abc 
						if(depValue.indexOf("prodAttVal")!=-1){//checking prodAttVal value in depValue varible via contains function of Javascript. if it founds it goes in if or in else
							var splitValue=depValue.split('_');//Spliting depValue by using _ So element1 is prodAttVal and element is 8 
							document.getElementById("prodAttVal_"+i).value=document.getElementById("prodAttVal_"+splitValue[1]).value;//assigning Dependent Value, 2nd elememnt of split function
						}else{
							document.getElementById("prodAttVal_"+i).value=depValue;//Assigning value from DB
						}
						if(document.getElementById("autoSuggProdAttVal_"+i) != null){
							document.getElementById("autoSuggProdAttVal_"+i).value='';
						}
						//[004.1] End
						if(labelValue.list[j].isReadOnly == '1'){
							if(document.getElementById("prodAttVal_"+i).type=='select-one')
								document.getElementById("prodAttVal_"+i).disabled=true;
							else	
								document.getElementById("prodAttVal_"+i).readOnly=true; 
							//added zeroValueAllowed control to check whether zero value is allowed on that control or not
							document.getElementById("prodAttVal_"+i).zeroValueAllowed='Y';
						}
						if(labelValue.list[j].isReadOnly == '0'){
							if(document.getElementById("prodAttVal_"+i).type=='select-one')
								document.getElementById("prodAttVal_"+i).disabled=false;
							else	
								document.getElementById("prodAttVal_"+i).readOnly=false; 
							//added zeroValueAllowed control to check whether zero value is allowed on that control or not
							document.getElementById("prodAttVal_"+i).zeroValueAllowed='N';
						}
						//[004.1] Start
						if(labelValue.list[j].mandatory == '0'){
							document.getElementById("prodAttVal_"+i).isSummaryReq='0';
							document.getElementById("prodAttVal_"+i).className='inputBorder2';
						}
						if(labelValue.list[j].mandatory == '1'){
							document.getElementById("prodAttVal_"+i).isSummaryReq='1';
							document.getElementById("prodAttVal_"+i).className='inputBorder1';
						}
						//[004.1] End
						//Puneet adding break for performance
						break;
					}
				}
			}	
		}
	}
} 
//Puneet changed the function signature to get the dep label values of all drop downs
//function fnGetDestLabelValue_onLoad(attMstrId,fieldName){
function fnGetDestLabelValue_onLoad(dropDownLovIDLabels){
	//lawkush start
	//var textValue = fieldName.value;
	//var select_list_field = fieldName; 
	//var select_list_selected_index = select_list_field.selectedIndex;	
	//var attLabelvalue = select_list_field.options[select_list_selected_index].text;	
	//lawkush End
	var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
	//lawkush Start
	//var attLabelvalue=fieldName;
	//lawkush End
	//Puneet Commenting
	/*var jsData = {
		attMasterId:attMstrId,
		attributeLabel:attLabelvalue
	};*/
	
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
    //Puneet commenting to pass the array
	//labelValue = jsonrpc.processes.fillDropDownDependentlabel(jsData);
	var allLabelValue = jsonrpc.processes.fillAllDropDownDependentlabel(dropDownLovIDLabels);
    if(null != allLabelValue && allLabelValue.list.length>0){
		/*Puneet commented for performance tuning
		 for(var k=0;k<destAttID.length;k++){
			destAttID.pop();
		}*/
    	destAttID=new Array();
    
    	var destAttIndex = 0;
		for(var j = 0;j<allLabelValue.list.length;j++){
			for(var h = 0;h<allLabelValue.list[j].serviceSummary.list.length;h++){
				var labelValue = allLabelValue.list[j].serviceSummary.list[h];
				destAttID[destAttIndex] = labelValue.destAttId;
				for(var i=1;i<=countAttributes;i++){
					if(document.getElementById("hdnProdAttVal_"+i) != null){
						if(document.getElementById("hdnProdAttVal_"+i).value == destAttID[destAttIndex]){
							//document.getElementById("prodAttVal_"+i).value=labelValue.list[j].planName;
							if(labelValue.isReadOnly == '1'){
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
							if(labelValue.isReadOnly == '0'){
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
							if(labelValue.mandatory == '0'){
								document.getElementById("prodAttVal_"+i).isSummaryReq='0';
								document.getElementById("prodAttVal_"+i).className='inputBorder2';
							}
							if(labelValue.mandatory == '1'){
								document.getElementById("prodAttVal_"+i).isSummaryReq='1';
								document.getElementById("prodAttVal_"+i).className='inputBorder1';
							}
							//[004.1] End
							//Puneet adding break for performance tuning
							break;
						}
					}
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
	orderEntryEnableDisable('VIEWPRODUCTCATELOG',stateid, sectionName);
}


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
   		 // alert("chkSelectChargeDetail >> "+document.forms[0].chkSelectChargeDetail);
   		 // alert("rowlen == "+rowlen);
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
//
//[004.1] Start
function copyTextValue(sourceValue)
{
	//alert(document.getElementById('hdnSeriveAttCounter').value)
	var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
	for(var i=1;i<=countAttributes;i++)
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
//[004.1] End

//------------------[ Start:ComponentStartDateLogic and ComponentEndDateLogic ]-----------------------------------------------------------------
function fillComponentStartEndDateLogic(index){
	try
	{											
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
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
function changeCompEndDays(index)
{
	if(document.getElementById('txtCompEndDateLogic'+index).value=='TD')
	{
	  document.getElementById('txtEndDays'+index).value=0;
	  document.getElementById('txtEndMonth'+index).value=0;		  		
	}
}
//------------------[ End:ComponentStartDateLogic and ComponentEndDateLogic ]-----------------------------------------------------------------
function popUpServiceSummary(attId,counter,popUpId)
{
	/*if(popUpId=='popUpForArborLSILookup')
	{
		popUpForArborLSILookup(attId,counter)
	}
	else*/
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
	var path = requestContextPath + "/NewOrderAction.do?method=arborLSILookup&fxChargeRedirectionTypeCumulative="+fxChargeRedirectionTypeCumulative;	
	path=path+"&logicalSIno="+logicalSIno+"&orderNo="+ib2bOrderNo+"&configId="+configId+"&serviceDetailID="+serviceDetailID+"&fxChargeRedirectionType="+fxChargeRedirectionType;
	window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:700px");
}
function popLSINoForMBIC(attMasterId,productCounter) 
{
	var orderNo = callerWindowObj.document.getElementById('poNumber').value;
	var isServiceInactive=0;
	var serviceLength = callerWindowObj.document.forms[0].chkService.length;
	for(var m=0;m<serviceLength;m++)
	{
		if(callerWindowObj.document.getElementById('txtServiceNo'+(m+1)).value==servicesID)
		{
			if(callerWindowObj.document.getElementById('serviceStatus'+(m+1)).value=="CancelAndCopy" || callerWindowObj.document.getElementById('serviceStatus'+(m+1)).value=="Cancelled in CRM")
			{	
				isServiceInactive=1;
				alert("Service Cancelled , You Can't Attach LSI!!");
				return false;
			}
		}
	}
	var path = requestContextPath+"/NewOrderAction.do?method=fetchLSINoForMBIC&attMasterId="+attMasterId+"&ib2bOrderNo="+orderNo+"&servicesID="+servicesID+"&mbiclogicalSIno="+logicalSIno+"&productCounter="+productCounter+"&serviceProductID="+serviceProductID+"&isServiceInactive="+isServiceInactive;		
	window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:530px");
}
function popUpForVCSBridgeLSILookup(attMasterId,productCounter)
{
	var orderNo = callerWindowObj.document.getElementById('poNumber').value;
	var isServiceInactive=0;	
	var serviceLength = callerWindowObj.document.forms[0].chkService.length;
	for(var m=0;m<serviceLength;m++)
	{
		if(callerWindowObj.document.getElementById('txtServiceNo'+(m+1)).value==servicesID)
		{
			if(callerWindowObj.document.getElementById('serviceStatus'+(m+1)).value=="CancelAndCopy" || callerWindowObj.document.getElementById('serviceStatus'+(m+1)).value=="Cancelled in CRM")
			{	
				isServiceInactive=1;
				alert("Service Cancelled , You Can't Attach LSI!!");
				return false;
			}
		}
	}
	var path = requestContextPath+"/NewOrderAction.do?method=fetchVCS_BridgeLSI&attMasterId="+attMasterId+"&ib2bOrderNo="+orderNo+"&servicesID="+servicesID+"&logicalSIno="+logicalSIno+"&productCounter="+productCounter+"&serviceProductID="+serviceProductID+"&serviceDetailID="+serviceDetailID;		
	window.showModalDialog(path,window,"status:false;dialogWidth:900px;dialogHeight:530px");
}

function getChargeFrequency(){
	frequencyCombo = jsonrpc.processes.populateFrequencyType();
	var frequencyOption="<option value='-1'>Select a Frequency Type</option>";
	if(null != frequencyCombo){
		factorVal=new Array();
		for(z=0;z<frequencyCombo.list.length;z++){
			frequencyOption = frequencyOption + "<option value='" + frequencyCombo.list[z].frequencyID + "'>" + frequencyCombo.list[z].frequencyName + "</option>";
			factorVal[z]= frequencyCombo.list[z].factor;
		}
	}
	return frequencyOption;
}

//=============================[ Draw Charge Table-04-12-2012 ]=====================================
function drawViewChargeTable(){
	//------------Set Page Size for Charge Line Table Paging Start --------------
   	pageRecords=PAGE_SIZE_CHARGE_LINE;
   	//------------Set Page Size for Charge Line Table Paging End ----------------
	var contractFreqValue="";
    var cFrequency="";
    //Puneet commenting for performance tuning
    /*var myChargeTable = document.getElementById('tableCharges');
 
	var iCountRows = myChargeTable.rows.length;
	for (j = 0; j <  iCountRows; j++){
     	myChargeTable.deleteRow(0);
	}  */ 
    var jsChargeData =	{
			startIndex:startRecordId,
			endIndex:endRecordId,
			sortBycolumn:sortByColumn,
			sortByOrder:sortByOrder,
			pageRecords:pageRecords,
			serviceProductID:serviceProductID,
			serviceId:servicesID,
			ponum:document.getElementById("txtPODetailNo").value
		};
		//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");		
	var chargelists=0;
    chargelists = jsonrpc.processes.populateChargeDetails(jsChargeData);	   
    iTotalLength=chargelists.list.length;
    var chargeTableDiv =  document.getElementById('contentscroll');
    if(iTotalLength>0){
    	chargeTableDiv.innerHTML = "";
    	chargeSumCounter = 0;
    }
   	//-------------------[ Find PageNumber,StartEndIndex,PageRecords ]---------------------------------------
   	document.getElementById('txtPageNumber').value= pageNumber;
   	sync(); 
   	//-------------------[ Find PageNumber,StartEndIndex,PageRecords ]---------------------------------------
	    //iTotalLength=0;
		if(iTotalLength !=0){
			iNoOfPages = chargelists.list[0].maxPageNo;
		}else{     
        	iNoOfPages=1;
		}
		
		document.getElementById('txtMaxPageNo').value=iNoOfPages;	
			
		if(iTotalLength > 1){
			document.getElementById('SelectAllChckCharges').disabled=false;
		}else{
			document.getElementById('SelectAllChckCharges').disabled=true;
		}
	    if(iTotalLength > 0){
			document.getElementById('viewChargeLineTabNavigatorId').style.display='block';			
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
	    	var chargeTypeOption = getChargeType(chargelists.list[iChargeIndexCounter].chargeType);
			var frequencyOption = getChargeFrequency();
			var txtCntrtMonths = document.getElementById('txtCntrtMonths').value;
			var ctrlArry2 = new Array();
			var controlIndex = 0;
			var chargeTypeSelectedValue = new Array();
			var chargeSDSelectedValue = new Array();
			var chargeEDSelectedValue = new Array();
			var chargeFqSelectedValue = new Array();
	    	for (iChargeIndexCounter = 0 ; iChargeIndexCounter < iTotalLength; iChargeIndexCounter++){		    	
				//var counter = parseInt(hdnChargeCount.value)+1;
				//hdnChargeCount.value = counter;
	    		chargeListTable = chargeListTable + tableRowStart;
	    		chargeSumCounter++;
				counter++;
				//counter = tableCharges.rows.length;
				//Puneet commented for performance tuning
				//var tblRow=tableCharges.insertRow();
				var created_serviceid=chargelists.list[iChargeIndexCounter].created_serviceid;
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col1 toprow";
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col1 toprow'>";
				str ="<input name='chkSelectChargeDetail' id='chkSelectChargeDetail'  type='checkbox' value='"+counter+"'  onclick='allChargesCheckNone()' >";
				//Puneet for performance tuning
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				//Puneet for performance tuning
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col2";
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col2' >";
				str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px;'  name='ddCType' id='ddCType"+counter+"' isRequired='0' Disp='' class='dropdownMargin' onchange='javascript:CheckRCRequiredForCLEP(this,"+counter+");setBlankChargeName("+counter+");changeFrequency("+counter+");calculateFreqAmount("+counter+");fillEndDateLogic("+counter+");'>";
				str = str + chargeTypeOption;
				str = str + "</select>";
				chargeTypeSelectedValue[iChargeIndexCounter] =  chargelists.list[iChargeIndexCounter].chargeType;
				str=str+"<input type='hidden' name='hdnChargeId' id = 'hdnChargeId"+counter+"' value='"+chargelists.list[iChargeIndexCounter].chargeInfoID+"'>";
				var ChargeType=chargelists.list[iChargeIndexCounter].chargeType;
				//start CLEP
				str=str+"<input type='hidden' name='hdnChargeCreationSource' id = 'hdnChargeCreationSource"+counter+"' value='"+chargelists.list[iChargeIndexCounter].charge_creation_source+"'>";
				if(chargelists.list[iChargeIndexCounter].charge_creation_source=="2"){
					clep_arrEnableDisableFlags[iChargeIndexCounter]='DISABLE';
				}else{					
					clep_arrEnableDisableFlags[iChargeIndexCounter]='ENABLE';
				}
				//End CLEP
				//Puneet for performance tuning
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				/*srvDetailId=document.getElementById("hdnServiceDetailID").value;
				if(document.getElementById('hdnHardwareInfo').value==1){
					var local_typeCombo = document.getElementById("ddCType"+counter);
					Puneet commented for performance tuning
					var txtBillingformat = document.getElementById('txtBillingformat');
					if(txtBillingformat.value==2){					
						fillChargeTypeForHardware(path,srvDetailId,1,local_typeCombo);						
					}else if(txtBillingformat.value==3){		
						fillChargeTypeForHardware(path,srvDetailId,2,local_typeCombo);			
					}		
				}else{	
					fillChargeType(counter,srvDetailId);
				}*/
				//Puneet for performance tuning
				//document.getElementById('ddCType' + counter).value = chargelists.list[iChargeIndexCounter].chargeType;
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
				//Puneet for performance tuning
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col3";
				//Puneet commenting for auto suggest of charge name
				//str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px;'  name='txtCName' id='txtCName"+counter+"' isRequired='0' Disp='' onchange='fillAnnotationField("+counter+");getTaxRate("+counter+");getLineNSublineItemLbl("+counter+");' class='dropdownMargin'><option value='-1'>Select Name</option></select>";
				str="<input type='text' readonly='true' value='"+chargelists.list[iChargeIndexCounter].chargeNameVal+"' onmouseover='getTip(value)' counterval='"+ counter +"' onmouseout='UnTip()' style='width:160px' isRequired='0'  name='chargeName' ctrltype='dropdown' retval='AUTOSUGGESTCHARGENAME' id='chargeName" + counter + "' class='dropdownMargin'><a name='chargeNameLink' id='chargeNameLink" + counter + "' ctrltype='dropdownlink' onclick='javascript:getDropDownChargeName(" + counter + ")'>Show</a>";
				str=str+"<input type='hidden' name='txtCName' id='txtCName"+counter+"' isrequired='0' value='"+chargelists.list[iChargeIndexCounter].chargeName+"'>";
				str=str+"<input type='hidden' name='hdnChargeName' value='"+chargelists.list[iChargeIndexCounter].chargeName+"'>";
				//[015] Start
				str=str+"<input type='hidden' name='hdnFxChargeID' value='"+chargelists.list[iChargeIndexCounter].fxChargeId+"' id='hdnFxChargeID"+counter+"'>";
				//[015] End
				//Puneet for performance tuning
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				//Puneet commenting the code as directly fetching the charge name value
				//fillChargeNames(counter,chargelists.list[iChargeIndexCounter].chargeType,chargelists.list[iChargeIndexCounter].chargeNameID);
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
				//Puneet for performance tuning
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col3";
				//Start [020]
				if(document.getElementById('hdnHardwareInfo').value==1){
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' class='inputBorder1' name='txtCAnnotation' isRequired='0' Disp='' readonly id='txtCAnnotation"+counter+"' onblur=\"if(this.value.length > 0){if(CheckMinLength(this)){return ValidateTextField(this,path,'Annotation')}}\"  maxlength='240' value='"+chargelists.list[iChargeIndexCounter].chargeAnnotation+"'>";
				}else{
					str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' class='inputBorder1' name='txtCAnnotation' isRequired='0' Disp='' readonly id='txtCAnnotation"+counter+"' onblur=\"if(this.value.length > 0){if(CheckMinLength(this)){return ValidateTextField(this,path,'Annotation')}}\"  maxlength='340' value='"+chargelists.list[iChargeIndexCounter].chargeAnnotation+"'>";
				}
				//End [020]
				//Puneet for performance tuning
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col5'>";
				//Puneet for performance tuning
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col5";
				str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' value='" + chargelists.list[iChargeIndexCounter].taxRate + "' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtTaxRate' id='txtTaxRate"+counter+"' readonly='true'>";
				//Puneet for performance tuning
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				//below condition code is commented because the function "getTaxRate" is generic 
				//var serviceType=document.getElementById("txtHdnServiceName").value.toUpperCase();
				//if(serviceType.indexOf("SERVICE")!=-1)
				//{
					//Puneet commenting the function call as tax rate is already populated
				//getTaxRate(counter);
				//}						
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col6'>";
				//Puneet for performance tuning
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col6";
				//Puneet for performance tuning
				var txtCPeriodValue = chargelists.list[iChargeIndexCounter].chargePeriod;
				if(parseFloat(txtCPeriodValue)>parseFloat(txtCntrtMonths)){
					 alert("You cant enter Charge period greater than Contract Period");
					 txtCPeriodValue=txtCntrtMonths;
				}
				str = "<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' class='inputBorder1' name='txtCPeriod' isRequired='0' Disp='' id='txtCPeriod"+counter+"' value='"+txtCPeriodValue+"' onblur='if(this.value.length > 0){if(checkdigits(this)){fillFrequency("+counter+");}else{return false;}}'>";
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col7'>";
				//Puneet for performance tuning
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col7";//Puneet end
				//str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' class='inputBorder1' maxlength='15' name='txtCTAmt' isRequired='0' Disp=''  id='txtCTAmt"+counter+"' value = '"+chargelists.list[i].chargeAmount+"' onblur='if(this.value.length > 0){if(checknumber(this)){calculateFreqAmount("+counter+");}else{calculateFreqAmount("+counter+");return false;}}'  > ";
				//str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' class='inputBorder1' maxlength='15' name='txtCTAmt' isRequired='0' Disp=''  id='txtCTAmt"+counter+"' value = '"+chargelists.list[i].chargeAmount+"' oldvalue='' onkeyup='if(this.value.length > 0){if(checknumber_max2decimal_charges_section(this,0)){this.oldvalue=this.value;calculateFreqAmount("+counter+");calculateTotalLineAmount(this.value,"+chargeSumCounter+");}else{this.value=this.oldvalue;calculateFreqAmount("+counter+");calculateTotalLineAmount(this.value,"+chargeSumCounter+");return false;}}else{calculateTotalLineAmount(this.value,"+chargeSumCounter+");return false;}' onblur='if(this.value.length > 0){if(checknumber_max2decimal_charges_section(this,1)){this.oldvalue=this.value;calculateFreqAmount("+counter+");calculateTotalLineAmount(this.value,"+chargeSumCounter+");}else{this.value=this.oldvalue;calculateFreqAmount("+counter+");calculateTotalLineAmount(this.value,"+chargeSumCounter+");return false;}}else{calculateTotalLineAmount(this.value,"+chargeSumCounter+");return false;}' > ";
				str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' class='inputBorder1' maxlength='15' name='txtCTAmt' isRequired='0' Disp=''  id='txtCTAmt"+counter+"' value = '"+chargelists.list[iChargeIndexCounter].chargeAmount+"' oldvalue='"+chargelists.list[iChargeIndexCounter].chargeAmount+"' counterVal='" + counter + "' chargeSumCounterVal='" + chargeSumCounter + "'> ";
				str=str+"<input type='hidden' name='hdnCTAmt' id='hdnCTAmt"+chargeSumCounter+"' value = '"+chargelists.list[iChargeIndexCounter].chargeAmount+"' > ";
				//Puneet performance tuning
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col8'>";
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col8";
				//Puneet taking it up
				/*var txtCFrequencyDis = true;
				if(chargelists.list[iChargeIndexCounter].chargeType == 1){
					txtCFrequencyDis = false;
				}*/
				str = "<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' style='width:175px;'  name='txtCFrequency' id='txtCFrequency"+counter+"' isRequired='0' Disp='' class='dropdownMargin' onchange='calculateFreqAmount("+counter+");' >";
				str = str + frequencyOption + "</select>";
				chargeFqSelectedValue[iChargeIndexCounter]=chargelists.list[iChargeIndexCounter].chargeFrequency;
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;								
				//Puneet commented for performance tuning as the options are already fetched Check
				//fillFrequency(counter);
				//document.getElementById('txtCFrequency' + counter).value  = chargelists.list[iChargeIndexCounter].chargeFrequency;
				//if(document.getElementById('ddCType' + counter).value==1){
					//document.getElementById('txtCFrequency' + counter).disabled=false;
				//}
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col9'>";
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col9";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputDisabled' name='txtCFreqAmt' id='txtCFreqAmt"+counter+"' value ='"+chargelists.list[iChargeIndexCounter].chargeFrequencyAmt+"'   onblur='if(this.value.length > 0){return checknumber(this)}' readonly='readonly'> ";
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col4'>";
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col4";
				chargeSDSelectedValue[iChargeIndexCounter] = chargelists.list[iChargeIndexCounter].startDateLogic;
				str="<table><tr><td><select onmouseover='getTip_DD(this,this.selectedIndex,this.id)'  name='txtCStartDate' id='txtCStartDate"+counter+"' isRequired='0' Disp='' class='dropdownMargin' style='width:125px;'><option value='-1' >Select Start Date Logic</option><option value='BTD'>Billing Trigger</option></select>";
				str=str+"<input type='hidden' name='txtCStartDate' value='"+chargelists.list[iChargeIndexCounter].startDateLogic+"'></td>";
				str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' isRequired='0' Disp='' class='inputBorder' name='txtStartDays' value ='"+chargelists.list[iChargeIndexCounter].startDateDays+"'  id='txtStartDays"+counter+"' onblur='if(this.value.length > 0){return checkdigits(this)}'></td>";
				str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' isRequired='0' Disp='' class='inputBorder' name='txtStartMonth' id='txtStartMonth"+counter+"' value='"+chargelists.list[iChargeIndexCounter].startDateMonth+"'  onblur='if(this.value.length > 0){return checkdigits(this)}'><input type='hidden' name='hdnstartdate' value='"+chargelists.list[iChargeIndexCounter].startDateMonth+"'></td></tr></table>";
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col4'>";
				//Puneet for performance tuning
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col4";
				var endDateOptions = "<option value='-1'>Select End Date Logic</option>";
				if(chargelists.list[iChargeIndexCounter].chargeType == 2){
					endDateOptions = getEndDateNRCOption();
				}else{
					endDateOptions = getEndDateRCOption();
				}
				chargeEDSelectedValue[iChargeIndexCounter] = chargelists.list[iChargeIndexCounter].endDateLogic;
				str="<table><tr><td><select onmouseover='getTip_DD(this,this.selectedIndex,this.id)'  name='txtCEndDate' id='txtCEndDate"+counter+"' value='"+chargelists.list[iChargeIndexCounter].endDateLogic+"' isRequired='0' Disp='' class='dropdownMargin' onchange='changeEndDays("+counter+");' style='width:125px;'>";
				str=str + endDateOptions + "</select>";
				str=str+"<input type='hidden' name='txtCEndDate' value='"+chargelists.list[iChargeIndexCounter].endDateLogic+"'></td>";
				str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;'  isRequired='0' Disp='' class='inputBorder' name='txtEndDays' value='"+chargelists.list[iChargeIndexCounter].endDateDays+"' id='txtEndDays"+counter+"' onblur='if(this.value.length > 0){return checkdigits(this)}'></td>";
				str= str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:100px;' isRequired='0' Disp='' class='inputBorder' name='txtEndMonth' value='"+chargelists.list[iChargeIndexCounter].endDateMonth+"' id='txtEndMonth"+counter+"' onblur='if(this.value.length > 0){return checkdigits(this)}'></td></tr></table>";
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
			    //Puneet commented for performance tuning
			    //fillEndDateLogic(counter);
			    var annualCharge;
				if(chargelists.list[iChargeIndexCounter].chargeType==1){
					annualCharge = ((chargelists.list[iChargeIndexCounter].chargeAmount)*12/(chargelists.list[iChargeIndexCounter].chargePeriod));
				}else 
					annualCharge='';
				//Puneet for performance
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col3";
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputDisabled' name='txtAnnualCharge' id='txtAnnualCharge"+counter+"' value ='"+annualCharge+"'   onblur='if(this.value.length > 0){return checknumber(this)}' readonly='readonly'> ";
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				tableDataStart = "<td align = 'center' vAlign='top' class='innerdata col3'>";
				//Puneet for performance tuning
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "center";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col3";
				var lineItemLabel = "&nbsp;";
				if(!(chargelists.list[iChargeIndexCounter].lineItemType=='NA' || chargelists.list[iChargeIndexCounter].lineItemType == null))
					lineItemLabel = chargelists.list[iChargeIndexCounter].lineItemType;
				//Puneet commenting as told by Sumit
				//str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' name='cmbLineItem' style='display:none' id='cmbLineItem"+counter+"' isRequired='0' Disp=''  class='dropdownMargin'><option value='-1'>Select Line Item</option></select>";
				//str=str+"<input type='hidden' name='hdnChargeName' value=''>";
				str="<input type='hidden' name='hdnChargeName' value=''>";
				str=str+"<label id='lineItemLbl"+counter+"'>" + lineItemLabel + "</label> ";
				//Puneet for perfromance tuning
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				//Puneet commenting the function call as select is not displayed
				/*fillLineItem(counter);
				document.getElementById('cmbLineItem' + counter).value  = chargelists.list[iChargeIndexCounter].lineItemId;*/
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				tableDataStart = "<td align = 'center' vAlign='top' class='innerdata col3'>";
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "center";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col3";
				var subLineItemLabel = "&nbsp;";
				if(!(chargelists.list[iChargeIndexCounter].subLineItemType=='NA' || chargelists.list[iChargeIndexCounter].subLineItemType == null))
					subLineItemLabel = chargelists.list[iChargeIndexCounter].subLineItemType;
				//Puneet commenting as told by Sumit
				//str="<select onmouseover='getTip_DD(this,this.selectedIndex,this.id)' name='cmbSubLineItem' style='display:none' id='cmbSubLineItem"+counter+"' isRequired='0' Disp=''  class='dropdownMargin'><option value='-1'>Select Sub Line Item</option></select>";
				//str=str+"<input type='hidden' name='hdnChargeName' value=''>";
				str="<input type='hidden' name='hdnChargeName' value=''>";
				str=str+"<label id='subLineItemLbl"+counter+"'>" + subLineItemLabel + "</label> ";
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				//Puneet commenting as select is not getting used
				//fillSubLineItem(counter);
				//document.getElementById('cmbSubLineItem' + counter).value  = chargelists.list[iChargeIndexCounter].sublineItemId;
				//Puneet commenting as directly fetching the value
				//getLineNSublineItemLbl(counter);
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col3";
				str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' maxlength='300' class='inputBorder' name='txtRemarks' id='txtRemarks"+counter+"' value='"+chargelists.list[iChargeIndexCounter].chargeRemarks+"' >";
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col3";
				str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' maxlength='50' class='inputBorder' name='txtStartDate' id='txtStartDate"+counter+"' value='"+chargelists.list[iChargeIndexCounter].startDate+"' readonly='readonly'>";
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col3'>";
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col3";
				str ="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' maxlength='50' class='inputBorder' name='txtEndDate' id='txtEndDate"+counter+"' value='"+chargelists.list[iChargeIndexCounter].endDate+"' readonly='readonly'>";
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				var excludecharge=chargelists.list[iChargeIndexCounter].excludecheck;
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col1'>";
				if(excludecharge==1){
					//Puneet for performance tuning
					//var tblcol=tblRow.insertCell();
				    //tblcol.align = "left";
					//tblcol.vAlign="top";
					//tblcol.className="innerdata col1";
					str="<input type='checkbox'  isRequired='0' Disp='' class='inputBorder' name='chkexcludeCharges' id='chkexcludeCharges"+counter+"' checked='true'>";
					//CellContents = str;
					//tblcol.innerHTML = CellContents;
				}else{
					//var tblcol=tblRow.insertCell();
				    //tblcol.align = "left";
					//tblcol.vAlign="top";
					//tblcol.className="innerdata col1";
					str="<input type='checkbox'  isRequired='0' Disp='' class='inputBorder' name='chkexcludeCharges' id='chkexcludeCharges"+counter+"'>";
					//CellContents = str;
					//tblcol.innerHTML = CellContents;
				}
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col13'>";
				//Billing Efficiencu in charge Details
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col13";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:90px;' value='"+chargelists.list[iChargeIndexCounter].ldDateClause+"'  isRequired='0' Disp='' maxlength='10' class='inputBorder' name='txtLDDateClause' id='txtLDDateClause"+counter+"'  readonly='true'><img src='" + requestContextPath + "/images/cal.gif' border='0px' alt='Loading...' onclick=\"javascript:show_calendar(productCatelog.txtLDDateClause" + counter + ");\" style='vertical-align: bottom'/></font>";
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col11'>";
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col11";
				str="<input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:50px;' value='"+chargelists.list[iChargeIndexCounter].delayedTimeInDayes+"'  isRequired='0' Disp='' maxlength='2' class='inputBorder' name='txtDelayedTimeInDays' id='txtDelayedTimeInDays"+counter+"'  onblur='if(this.value.length > 0){if(checkdigitsValidation(this)){checkUperLimit(this.value,this.id,30,"+counter+");}else {return false;}}'>";
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col13'>";
				//var tblcol=tblRow.insertCell();
				//tblcol.align = "left";
				//tblcol.vAlign="top";
				//tblcol.className="innerdata col13";
				str = "<table><tr><td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:50px;' value='"+chargelists.list[iChargeIndexCounter].ldPercentage+"'  isRequired='0' Disp='' maxlength='3' class='inputBorder' name='txtLDPercentage' id='txtLDPercentage"+counter+"' onblur='if(this.value.length > 0){if(checkdigitsValidation(this)){checkUperLimit(this.value,this.id,100,"+counter+");}else {return false;}}'></td>";
				str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:50px;' value='"+chargelists.list[iChargeIndexCounter].maxPercentage+"'  isRequired='0' Disp='' maxlength='3' class='inputBorder' name='txtMaxPenaltyPercentage' id='txtMaxPenaltyPercentage"+counter+"' onblur='if(this.value.length > 0){if(checkdigitsValidation(this)){checkUperLimit(this.value,this.id,100,"+counter+");}else {return false;}}'></td></tr></table> ";
				//CellContents = str;
				//tblcol.innerHTML = CellContents;
				chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				//End Billing Efficiency
				
				//Puneet commeting for performance tuning as directly setting the value in string
				/*document.getElementById('txtCStartDate' + counter).value=chargelists.list[iChargeIndexCounter].startDateLogic;
				document.getElementById('txtCEndDate' + counter).value=chargelists.list[iChargeIndexCounter].endDateLogic;*/
				
				tableDataStart = "<td align = 'left' vAlign='top' class='innerdata col12'>";
				//[012] Start
				//if(document.getElementById('hdnHardwareInfo').value==1){
				//[012] End
					//var tblcol=tblRow.insertCell();
					//tblcol.align = "left";
					//tblcol.vAlign="top";
					//tblcol.className="innerdata col12";
					//str = "<table><tr><td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtPayment1' id='txtPayment1"+counter+"' onblur='if(this.value.length > 0){checkdigits(this)}' ></td>";
					//str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtPayment2' id='txtPayment2"+counter+"' onblur='if(this.value.length > 0){checkdigits(this)}'   ></td> ";
					//str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtPayment3' id='txtPayment3"+counter+"' onblur='if(this.value.length > 0){checkdigits(this)}'  ></td> ";
					//str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtPayment4' id='txtPayment4"+counter+"' onblur='if(this.value.length > 0){checkdigits(this)}'  ></td></tr></table> ";
					//CellContents = str;
					//tblcol.innerHTML = CellContents;
					//chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
					//[012] Start
					//}else{
					//var tblcol=tblRow.insertCell();
					//tblcol.align = "left";
					//tblcol.vAlign="top";
					//tblcol.className="innerdata col12";
					str = "<table><tr><td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtPayment1' id='txtPayment1"+counter+"' value='" + chargelists.list[iChargeIndexCounter].paymentTerm1 +"'  ></td>";
					str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtPayment2' id='txtPayment2"+counter+"'  value='" + chargelists.list[iChargeIndexCounter].paymentTerm2 +"' ></td> ";
					str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtPayment3' id='txtPayment3"+counter+"'  value='" + chargelists.list[iChargeIndexCounter].paymentTerm3 +"' ></td> ";
					str = str + "<td><input onmouseover='getTip(value)' onmouseout='UnTip()' type='text' style='width:175px;' isRequired='0' Disp='' class='inputBorder' name='txtPayment4' id='txtPayment4"+counter+"'  value='" + chargelists.list[iChargeIndexCounter].paymentTerm4 +"' ></td></tr></table> ";
					/*//CellContents = str;
					//tblcol.innerHTML = CellContents;*/
					chargeListTable = chargeListTable + tableDataStart + str + tableDataEnd;
				//}
				//[012] End
				//Puneet commenting the 
				/*document.getElementById('txtPayment1' + counter).value=chargelists.list[iChargeIndexCounter].paymentTerm1;
				document.getElementById('txtPayment2' + counter).value=chargelists.list[iChargeIndexCounter].paymentTerm2;
				document.getElementById('txtPayment3' + counter).value=chargelists.list[iChargeIndexCounter].paymentTerm3;
				document.getElementById('txtPayment4' + counter).value=chargelists.list[iChargeIndexCounter].paymentTerm4;*/
				//Puneet commenting the code as field role mapping will be done once the table will be created														
				/*var ctrlArry2 = new Array();
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

				fieldRoleMappingValidation(callerWindowObj.linesTabFieldList,ctrlArry2);*/
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
				//Puneet commenting the code as it is directly checked
				//changeFrequency(counter);
				chargeListTable = chargeListTable + tableRowEnd;
			}
	    	chargeListTable = chargeListTable + tableEnd;
	    	chargeTableDiv.innerHTML=chargeListTable;
	    	//Puneet calling all field role validation
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
			//-------------------------[PagingSorting:Date:04-Dec-2012 Navigation Disable Enable]----------------------------------
		    var pagenumber=document.getElementById('txtPageNumber').value;
			var MaxPageNo=document.getElementById('txtMaxPageNo').value;
			if(pagenumber && MaxPageNo==1){	
			     document.getElementById('first').disabled=true;
			     document.getElementById('prev').disabled=true;
			     document.getElementById('last').disabled=true;
			     document.getElementById('next').disabled=true;		
			 }else if(pagenumber==1 && MaxPageNo!=1){
			     document.getElementById('first').disabled=true;
			     document.getElementById('prev').disabled=true;
			     document.getElementById('last').disabled=false;
			     document.getElementById('next').disabled=false;	 
			 }else if(pagenumber==MaxPageNo && pagenumber!=1){
			     document.getElementById('last').disabled=true;
			     document.getElementById('next').disabled=true;
			     document.getElementById('first').disabled=false;
			     document.getElementById('prev').disabled=false;	 
			 }else if(pagenumber!=MaxPageNo && pagenumber!=1){
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
	}else{
		document.getElementById('viewChargeLineTabNavigatorId').style.display='none';
		return false;
	}
}
function setASValuesForFetchedRecord(thisEl, ui){
	
	if(thisEl.attr("retval")=="AUTOSUGGESTBCP"){
		if(ui.item.value != $("#bbPrimaryBCPID").val()){
			$("#bbPrimaryBCPID").val(ui.item.value);
			$(thisEl).val(ui.item.label);
			FetchBillingDetails();
		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBCPSERVICE"){
		if(ui.item.value != $("#bbPrimaryBCPIDService").val()){
			$("#bbPrimaryBCPIDService").val(ui.item.value);
			$(thisEl).val(ui.item.label);
			FetchBillingDetailsforService();
		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTDISPATCH"){
 		if(ui.item.value != $("#txtdispatchAddress").val()){
 			$("#txtdispatchAddress").val(ui.item.value);
 			$(thisEl).val(ui.item.label);
 			getDispatchAddress();
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTPRICUSTLOC"){
 		if(ui.item.value != $("#ddPrimaryBCP").val()){
 			$("#ddPrimaryBCP").val(ui.item.value);
 			$(thisEl).val(ui.item.label);		                                 
 			FetchPriBCPDetails();
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTSECCUSTLOC"){
 		if(ui.item.value != $("#ddSecondaryBCP").val()){
 			$("#ddSecondaryBCP").val(ui.item.value);
 			$(thisEl).val(ui.item.label);		                                 
 			FetchSecBCPDetails();
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLTAXATION"){
 		if(ui.item.value != $("#txtTaxation").val()){
 			$("#txtTaxation").val(ui.item.value);
 			$(thisEl).val(ui.item.label);		                                 
 			setPopulateStdReason(ui.item.value);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTLEGALENTITY"){
 		if(ui.item.value != $("#txtEntity").val()){
 			$("#txtEntity").val(ui.item.value);
 			$(thisEl).val(ui.item.label);		                                 
 			resetLicenseCo();
 			//resetContMonth();
 			$("#hdnISFLEFLAG").val(ui.item.addParam);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTSTORE"){
 		if(ui.item.value != $("#txtStore").val()){
 			$(thisEl).val(ui.item.label);	
 			$("#txtStore").val(ui.item.value);
 			refreshTaxRateForAS(path);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGMODE"){
 		if(ui.item.value != $("#txtBillingMode").val()){
 		$("#txtBillingMode").val(ui.item.value);
    	$(thisEl).val(ui.item.label);		                                 
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTCP"){
 		$("#txtBillingCP").val(ui.item.value);
    	$(thisEl).val(ui.item.label);		                                 
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTSALENATURE"){
 		if(ui.item.value != $("#txtNSale").val()){
 			$(thisEl).val(ui.item.label);	
 			$("#txtNSale").val(ui.item.value);
 			refreshTaxRateForAS(path);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTLICENSECO"){
 		if(ui.item.value != $("#txtLicenceCo").val()){
 			$(thisEl).val(ui.item.label);	
 			$("#txtLicenceCo").val(ui.item.value);
 			if($("#hdnHardwareInfo").val()==1)
 			resetStore();
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTREASON"){
 		$(thisEl).val(ui.item.label);	
		$("#changeReason").val(ui.item.value);
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
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTHTYPE"){
 		if(ui.item.value != $("#txtHtype").val()){
 			$(thisEl).val(ui.item.label);	
 			$("#txtHtype").val(ui.item.value);
 			refreshTaxRateForAS(path);
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
 	}
 	else if(thisEl.attr("retval")=="AUTOSUGGESTITEMCODE"){
		if(ui.item.value != $("#txtItemCode").val()){
			$(thisEl).val(ui.item.label);
   	 $("#txtItemCode"+thisEl.attr("countervalItem")).val(ui.item.value);
	   }
		
	}
 	else if(thisEl.attr("retval")=="AUTOSUGGESTDELLOCATION"){
		if(ui.item.value != $("#txtDelLocId").val()){
    			$(thisEl).val(ui.item.label);	
		$("#txtDelLocId"+thisEl.attr("countervalDel")).val(ui.item.value);
	   }
		
	}
 	else if(thisEl.attr("retval")=="AUTOSUGGESTSUBINVENTORY"){
		if(ui.item.value != $("#txtSubInvent").val()){
			$(thisEl).val(ui.item.label);
		$("#txtSubInvent"+thisEl.attr("countervalSub")).val(ui.item.value);
	   }
		
	}
 	else if(thisEl.attr("retval")=="AUTOSUGGESTBUDGHEAD"){
		if(ui.item.value != $("#txtBudgetHead").val()){
			$(thisEl).val(ui.item.label);
   	 $("#txtBudgetHead"+thisEl.attr("countervalBud")).val(ui.item.value);
   	getBudgetHeadAop2(thisEl.attr("countervalBud"),ui.item.value);
   	enableBudgetHead(thisEl.attr("countervalBud"));
	   }
		
	}
 	else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGFORMAT"){
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
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTCUSTPODETAIL"){
 		if(ui.item.value != $("#txtPODetailNo").val()){
 			//getPrevCustPoNo();
 			if(checkchargevalidation()==false){
 				focusEnabled = false;
 				return false;
 			}else{
 				fnFetchEntity();
 				//selectPoId(this);
 				selectPoIdAS($("#txtPODetailNo"));
 			$("#txtPODetailNo").val(ui.item.value);
 			$(thisEl).val(ui.item.label);
 			resetEntity();
 			updateselectedCustPODetailDepValues(ui.item.value);
 			}
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGTYPE"){
 		$(thisEl).val(ui.item.label);	
		$("#txtBillingType").val(ui.item.value);
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGLEVEL"){
 		if(ui.item.value != $("#txtBillingLvl").val()){
 			$(thisEl).val(ui.item.label);	
 			$("#txtBillingLvl").val(ui.item.value);
 			setBillingLevelNo(ui.item.value);
 		}
 	}else{
		var l_AllowChangeaction=1;
		//after arbor merge
		if(thisEl.attr("configVal")!=null && thisEl.attr("configVal")!=0 
			&& ui.item.value!=$("#prodAttVal_"+thisEl.attr("counterval")).val()){
				l_AllowChangeaction=0;
				var compLength = 0;
				var chargeLength = 0;
				focusEnabled = false;
				if(document.getElementById('hdnComponentInfo').value==1 && !componentDetailsFetched){
					var jsComponentData = {
							serviceProductID:serviceProductID,
							serviceId:servicesID
						};
						//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
					componentslists = jsonrpc.processes.populateComponentsDetails(jsComponentData);
					//componentslists = getComponentList();//jsonrpc.processes.populateComponentsDetails(jsComponentData);
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
     			if(loadUpdateConfigIfApplicable(thisEl.attr("retval"),ui.item.label,ui.item.value,'VPC','AS', compLength, chargeLength)==true){
					l_AllowChangeaction=1;
				}
		}
		//end: after arbor merge
		if(l_AllowChangeaction==1){
         	 $("#prodAttVal_"+thisEl.attr("counterval")).val(ui.item.value);
             $(thisEl).val(ui.item.label);
  			//Added by Deepak
           if((thisEl.attr("retAttDescKey")!=undefined)&&(thisEl.attr("retAttDescKey")==attDescKey)){//[018] adding undefined chk
        	   
        	   selectedCircle=ui.item.label;
        	   blankDelLocAsSubInvent();
             	}
           //end Deepak
             fnGetDestLabelValueForLOV(thisEl.attr("retval"),ui.item.label);
             if(thisEl.attr("isParentAtt")==1){
             	resetChildAttr(thisEl);
     	}
     }
}
}
function setASValuesForFetchedRecordForChange(thisEl, ui, value2, label2){
	//var label2 = ui.item.label;
	//var value2 = ui.item.value;
	if(thisEl.attr("retval")=="AUTOSUGGESTITEMCODE"){
		$(thisEl).val(label2);
	   	 $("#txtItemCode"+thisEl.attr("countervalItem")).val(value2);
	    			return;
	    		}
	if(thisEl.attr("retval")=="AUTOSUGGESTDELLOCATION"){
		$(thisEl).val(label2);
	   	 $("#txtDelLocId"+thisEl.attr("countervalDel")).val(value2);
	    			return;
	    		}
	if(thisEl.attr("retval")=="AUTOSUGGESTSUBINVENTORY"){
		$(thisEl).val(label2);
	   	 $("#txtSubInvent"+thisEl.attr("countervalSub")).val(value2);
	    			return;
	    		}
	if(thisEl.attr("retval")=="AUTOSUGGESTBCP"){
		if(value2 != $("#bbPrimaryBCPID").val()){
			$(thisEl).val(label2);
			$("#bbPrimaryBCPID").val(value2);
			FetchBillingDetails();
		}
	}else if(thisEl.attr("retval")=="AUTOSUGGESTBCPSERVICE"){
		if(value2 != $("#bbPrimaryBCPIDService").val()){
			$(thisEl).val(label2);
			$("#bbPrimaryBCPIDService").val(value2);
			FetchBillingDetailsforService();
		}
	}else if(thisEl.attr("retval")=="AUTOSUGGESTDISPATCH"){
		if(value2 != $("#txtdispatchAddress").val()){
			$(thisEl).val(label2);
			$("#txtdispatchAddress").val(value2);
			getDispatchAddress();
		}
	}else if(thisEl.attr("retval")=="AUTOSUGGESTPRICUSTLOC"){
		if(value2 != $("#ddPrimaryBCP").val()){
			$(thisEl).val(label2);
			$("#ddPrimaryBCP").val(value2);
			FetchPriBCPDetails();
		}
    }else if(thisEl.attr("retval")=="AUTOSUGGESTSECCUSTLOC"){
    	if(value2 != $("#ddSecondaryBCP").val()){
    		$(thisEl).val(label2);
    		$("#ddSecondaryBCP").val(value2);
    		FetchSecBCPDetails();
    	}
    }else if(thisEl.attr("retval")=="AUTOSUGGESTBILLTAXATION"){
    	if(value2 != $("#txtTaxation").val()){
    		$(thisEl).val(label2);
    		$("#txtTaxation").val(value2);
    		setPopulateStdReason(value2);
    	}
    }else if(thisEl.attr("retval")=="AUTOSUGGESTLEGALENTITY"){
    	if(value2 != $("#txtEntity").val()){
    		$("#txtEntity").val(value2);
    		$(thisEl).val(label2);		                                 
    		resetLicenseCo();
    		//resetContMonth();
    		if(null != ui && null != ui.item && null != ui.item.addParam)
    			$("#hdnISFLEFLAG").val(ui.item.addParam);
    	}	
    }else if(thisEl.attr("retval")=="AUTOSUGGESTCP"){
    	$("#txtBillingCP").val(value2);
        $(thisEl).val(label2);		                                 
    }else if(thisEl.attr("retval")=="AUTOSUGGESTSALENATURE"){
    	if(value2 != $("#txtNSale").val()){
    		$(thisEl).val(label2);	
    		$("#txtNSale").val(value2);
    		refreshTaxRateForAS(path);
    	}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGMODE"){
 		if(value2 != $("#txtBillingMode").val()){
 		$("#txtBillingMode").val(value2);
    	$(thisEl).val(label2);		                                 
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTTEXTFORM"){
 		if(value2 != $("#txtform").val()){
 			$(thisEl).val(label2);	
 			$("#txtform").val(value2);
 			refreshTaxRateForAS(path);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTSTORE"){
 		if(value2 != $("#txtStore").val()){
 			$(thisEl).val(label2);	
 			$("#txtStore").val(value2);
			refreshTaxRateForAS(path);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTLICENSECO"){
 		if(value2 != $("#txtLicenceCo").val()){
 			$(thisEl).val(label2);	
 			$("#txtLicenceCo").val(value2);
 			if($("#hdnHardwareInfo").val()==1)
 			resetStore();
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTREASON"){
 		$(thisEl).val(label2);	
		$("#changeReason").val(value2);
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTSALETYPE"){
 		if(value2 != $("#txtTSale").val()){
 			$(thisEl).val(label2);	
 			$("#txtTSale").val(value2);
 			setPAandIR();
 			refreshTaxRateForAS(path);
 		}
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTHTYPE"){
 		if(value2 != $("#txtHtype").val()){
 			$(thisEl).val(label2);	
 			$("#txtHtype").val(value2);
 			refreshTaxRateForAS(path);
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
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTCUSTPODETAIL"){
 		if(value2 != $("#txtPODetailNo").val()){
 			//getPrevCustPoNo();
 			if(checkchargevalidation()==false){
 				focusEnabled = false;
 				return false;
 			}else{
 				fnFetchEntity();
 				//selectPoId(this);
 				selectPoIdAS($("#txtPODetailNo"));
 			$("#txtPODetailNo").val(value2);
 			$(thisEl).val(label2);
 			resetEntity();
 			updateselectedCustPODetailDepValues(value2);
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
	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGTYPE"){
 		$(thisEl).val(label2);	
		$("#txtBillingType").val(value2);
 	}else if(thisEl.attr("retval")=="AUTOSUGGESTBILLINGLEVEL"){
 		if(value2 != $("#txtBillingLvl").val()){
 			$(thisEl).val(label2);	
 			$("#txtBillingLvl").val(value2);
 			setBillingLevelNo(value2);
 		}
 	}else{
		$(thisEl).val(label2);
		$("#prodAttVal_"+thisEl.attr("counterval")).val(value2);
		if(thisEl.attr("isParentAtt")==1){
       	resetChildAttr(thisEl);
        }
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

/*function callOnChargeAmountChange(){
	$(":text[name='txtCTAmt']").bind("paste cut keyup", function(e) {
			var thisCtrl = $(this);
			if(thisCtrl.val().length > 0){
				if(checknumber_charges_section(thisCtrl)){
					thisCtrl.attr("oldvalue", thisCtrl.val());
					calculateFreqAmount(thisCtrl.attr("counterVal"));
				}else{
					thisCtrl.val(thisCtrl.attr("oldvalue"));
					calculateFreqAmount(thisCtrl.attr("counterVal"));
					return false;
				}
			}else{
				thisCtrl.attr("oldvalue", thisCtrl.val());
				return false;
			}
		});
}
function callOnBlur(){
	$(":text[ctrltype='dropdown']").each(
		function(){
			var thisEl = $(this);				
			$(thisEl).blur(
				function(){				
				}
			);	
		}
	);
}	*/
/*function getBillTaxationDetails(){
	var txtTaxation = document.getElementById('txtTaxation');
	if(txtTaxation.value != '' && null != txtTaxation.value){
		taxationList = jsonrpc.processes.populateTaxationDetails(txtTaxation.value);
		if(null != taxationList && taxationList.list.length >0){
			txtTaxation.value = taxationList.list[0].txtTaxation;
			document.getElementById('taxationID').value = taxationList.list[0].taxationName; 
		}
	}
}*/
//=============================[ Draw Charge Table-04-12-2012 ]=====================================
//[014] Start
function advPayment() 
{
	
	
	if(document.getElementById('hdnBillingInfo').value==0)
	{
		alert("User can't input Advance Payment Details as 17 Parameter for this line item doesn't get generated");
		return false;
	}
	else
	{
		var path = requestContextPath + "/NewOrderAction.do?method=getAdvancePaymentDetails&orderNo="+poNumber+"&lineItemNo="+serviceProductID+"&serviceNo="+serviceNo+"&logicalSIno="+logicalSIno+"&isView="+isView;
		window.showModalDialog(path,window,"status:false;dialogWidth:1000px;dialogHeight:1000px");
	}
}
//[014] End
	// Added by Deepak Kumar
	function saveProductCatalogForScm()
	{ 
		if(isServiceCancle!=0)
		{
			alert("Service cancelled , You can't save now !!");
			return false;
		}
		var selectedPrId=document.getElementById("hdnprValue").value;
		if((!serviceSummaryFetched)&&(!scmLineDetailsFetched)){
			alert("Nothing has been updated to save");
			return;
		}
		
		if((isView=='null')||(isView==null)) 
		{	
		document.getElementById('saveImageScm').disabled =false;
		}
		else
		{
			document.getElementById('saveImageScm').disabled =true;
			document.getElementById('creatNewPr').disabled=true;
			document.getElementById('btnprReuse').disabled=true;
			return;
		}
		var but;
		var butSummaryFlag = false;
		var orderNo =poNumber;
		var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleName,userId);
		var shortCode = jsonrpc.processes.getShortCode(roleName);
		if(orderDetails.list.length>0)
		{
			stage=orderDetails.list[0].stageName;
			if(shortCode==stage)
			{
				//do nothing
			}
			else if(stage=="New" || stage=="NEW")
			{
				//do nothing
			}
			else if(stage=="Billing Trigger" && shortCode=="COPC")
			{
				alert("You are not authorised to save or update the values");
				return false;
			}
			else if(stage=="Partial Publish" && (shortCode=="COPC" || shortCode=="SED"))
			{
				//do nothing
			}
			else if(shortCode=="SED" || stage=="SED")
			{
				alert("You are not authorised to save or update the values");
				return false;
			}
			else
			{
				//do nothing		
			}
		}
		if(document.getElementById('hdnPrServiceSummary').value==1)
		{
		
			but=document.getElementById('btnServiceSummary');
			
			if(but.value=="+")
			{
				butSummaryFlag=true;
				show('tblServiceSummary',but);
			}
			//For Service Summary 
			var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
			var attributeID=new Array();
			var attributeVal=new Array();
			var attributeExpVal=new Array();
			var attributeName=new Array();
			var attributeMandatory=new Array();
			var attServiceSummMand = new Array();
			var attfor_validation =new  Array();
			var flagForDropdown;
			for(i=1;i<=countAttributes;i++)
			{
				if(document.getElementById('prodExpVal_'+i).value == "DROPDOWN" || document.getElementById('prodExpVal_'+i).value == "LOV" )
				{
					if(document.getElementById('prodAttMandatory_'+i).value==1 && document.getElementById('prodAttVal_'+i).value=="0") 
					{
						if(document.getElementById('prodAttVal_'+i).isSummaryReq==1)
						{
							alert("Please Input Values in PR Attributes Section!!");
												
								// document.getElementById('autoSuggProdAttVal_'+i).focus();
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
					}
				}
				else
				{	
					if(document.getElementById('prodAttMandatory_'+i).value==1 && document.getElementById('prodAttVal_'+i).value=="" ) 
					{
						if(document.getElementById('prodAttVal_'+i).isSummaryReq==1)
						{
							alert("Please Input Values in PR Attributes Section!!");
							// document.getElementById('prodAttVal_'+i).focus();
							return false;
						}
						else
						{
							if(document.getElementById('hdnProdAttVal_'+i).value == 2470 && flagForDropdown == 2)
							{ 
								
	    	       				var flag = jsonrpc.processes.lsiValidationForMediaExchangeAssociation(document.getElementById('prodAttVal_'+i).value);
	    	       				if(flag == 1)
	    	       				{
									alert('Please enter a valid LSI');
									document.getElementById('prodAttVal_'+i).focus();
									return false;
								}
							}
						
					
							if((document.getElementById('hdnProdAttVal_'+i).value == 3932) || (document.getElementById('hdnProdAttVal_'+i).value == 3944) || 
							(document.getElementById('hdnProdAttVal_'+i).value == 3948)) //Checking for Duplicay of Dail Comid, IRN No,Toll Free No and TGNO No
							{
						
		   	       				var flag = jsonrpc.processes.checkDuplicateAttributes(document.getElementById('prodAttVal_'+i).value,document.getElementById('hdnProdAttVal_'+i).value,document.getElementById('hdnServiceProductID').value);
		   	       				if(flag == 1)
		   	       				{
									alert('Data Already Exists, Please Input Some Other Value');
									// document.getElementById('prodAttVal_'+i).focus();
									return false;
								}
							}
							
							attributeVal[i-1]=document.getElementById('prodAttVal_'+i).value;
							attributeID[i-1]=document.getElementById('hdnProdAttVal_'+i).value;
							attributeExpVal[i-1]=document.getElementById('prodExpVal_'+i).value;
							attributeName[i-1]=document.getElementById('prodAttName_'+i).value;
							attributeMandatory[i-1]=document.getElementById('prodAttMandatory_'+i).value;
							attServiceSummMand[i-1]=document.getElementById('prodAttVal_'+i).isSummaryReq;
						}
					}
					else
					{
						
					if((document.getElementById('hdnProdAttVal_'+i).value == 3932) || (document.getElementById('hdnProdAttVal_'+i).value == 3944) || 
							(document.getElementById('hdnProdAttVal_'+i).value == 3948)) //Checking for Duplicay of Dail Comid, IRN No,Toll Free No and TGNO No
							{
							
		   	       				var flag = jsonrpc.processes.checkDuplicateAttributes(document.getElementById('prodAttVal_'+i).value,document.getElementById('hdnProdAttVal_'+i).value,document.getElementById('hdnServiceProductID').value)
		   	       				if(flag == 1)
		   	       				{
									alert('Data Already Exists, Please Input Some Other Value');
									// document.getElementById('prodAttVal_'+i).focus();
									return false;
								}
							}
													
						attributeVal[i-1]=document.getElementById('prodAttVal_'+i).value;
						attributeID[i-1]=document.getElementById('hdnProdAttVal_'+i).value;
						attributeExpVal[i-1]=document.getElementById('prodExpVal_'+i).value;
						attributeName[i-1]=document.getElementById('prodAttName_'+i).value;
						attributeMandatory[i-1]=document.getElementById('prodAttMandatory_'+i).value;
						attServiceSummMand[i-1]=document.getElementById('prodAttVal_'+i).isSummaryReq;
					}
				}
		
				
				}
			 
		}
		but=document.getElementById('btnServiceSummary');
			
			if(but.value=="-" && butSummaryFlag==true)
			{
				show('tblServiceSummary',but);
			}
			var isNull=validateAddCharge();
			if(isNull==false)
			{
				return;
			}
			var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
			var myList=[];
			for(i=0;i<chkChargeLine.length;i++) {
			    	var chkboxCharge=chkChargeLine[i];
			    	var index=chkboxCharge.value;
			    	
			    
		     // Charge details
		   var ob1={"javaClass": "com.ibm.ioes.forms.ChargeDetailsSCM",
			              itemCode:document.getElementById("txtItemCode"+index).value,
						  chargeValue: document.getElementById("txtValue"+index).value,
				          chargeQuantity:document.getElementById("txtQuantity"+index).value,
				          deliveryLocationId:Number(document.getElementById("txtDelLocId"+index).value),
				          subInventryId:Number(document.getElementById("txtSubInvent"+index).value),
						  chargeId_Scm:Number(document.getElementById("hdnChargeId"+index).value),
				          aopYear:document.getElementById("financeYear"+index).value,
					      aop1_Id:Number(document.getElementById("txtBudgetHead"+index).value),
					      aop2_Id:Number(document.getElementById("txtBudget"+index).value),
					      poNumer:document.getElementById("txtPoNumber"+index).value,
					      poDtae:document.getElementById("txtPoDate"+index).value,
						  poAmount:Number(document.getElementById("txtPoAmount"+index).value),
						  scmMessage:document.getElementById("txtScmMessage"+index).value,
						  chargeIdList:Number(document.getElementById("hdnChargeId"+index).value),
						  isActiveFlag:document.getElementById("txtIsActive"+index).value
		        };
		       
		myList[i]=ob1;
		
			}
			var chargesList={"javaClass": "java.util.ArrayList",
					"list": myList
					};
					
			 if(chargesList.list.length==0)
	    	 {
	    	 alert("Please Add Atleast One Scm Line");
	    	 return;
	    	 }
			if(pr_viewValue==true)
			{
		var jsData =
			{
		    	prodAttID: attributeID,
				prodAttValue:attributeVal,
				prodAttExptdValue:attributeExpVal,
				prodAttName:attributeName,
				prodAttMandatory:attributeMandatory,
				serviceSummaryMandatory:attServiceSummMand,
				hdnSeriveAttCounter:Number(countAttributes),	
				serviceProductID:serviceProductID,
				serviceInfoValue:Number(document.getElementById('hdnPrServiceSummary').value),
				sendToSCM:1,
				chargeInfoValue:Number(hiddenPrLineinfo),
				serviceId:servicesID,
				poNumber:poNumber,
				prId:selectedPrId,
				prReuseUpadte:prReuseUpadteViewFlag,
				chargeDetailsSCM:chargesList,
				serviceDetailID:serviceDetailID
					};
				try
				{
					var insertPcat = jsonrpc.processes.updateProductCatelogForScm(jsData,gb_sessionid);
				 }
			    catch(e)
			    {	alert("exception :  "+ e);
			    }
				if(insertPcat.successMsgScm=='done')
			    {
					alert(insertPcat.msgOut);
					prReuseUpadteViewFlag++;
					prNu=document.getElementById("hdnprValue").value;
			    }	
			    else
			    {
			    	alert(insertPcat.msgOut);		
			    	return false;
			    }
				return;
			}
			if((document.getElementById('createNewpr').value==1)||(document.getElementById('createNewpr').value==2))
			{
				document.getElementById("hdnprValue").value="";
			}
			if(notSaveFlag==true)
			{
				
				var jsData =
				{
			    	prodAttID: attributeID,
					prodAttValue:attributeVal,
					prodAttExptdValue:attributeExpVal,
					prodAttName:attributeName,
					prodAttMandatory:attributeMandatory,
					serviceSummaryMandatory:attServiceSummMand,
					hdnSeriveAttCounter:Number(countAttributes),	
					serviceProductID:serviceProductID,
					serviceInfoValue:Number(document.getElementById('hdnPrServiceSummary').value),
					sendToSCM:2,
					chargeInfoValue:Number(hiddenPrLineinfo),
					serviceId:servicesID,
					poNumber:poNumber,
					newPrFlagForView:Number(document.getElementById('createNewpr').value),
					notSaveInScm:1,
					chargeDetailsSCM:chargesList,
					serviceDetailID:serviceDetailID
					};
				try
				{
					var insertPcat = jsonrpc.processes.updateProductCatelogForScm(jsData,gb_sessionid);
				 }
			    catch(e)
			    {	alert("exception :  "+ e);
			    }
				if(insertPcat.successMsgScm=='done')
			    {
					alert(insertPcat.msgOut);
					var jsChargeId =
				  	 {
				    	serviceProductID:serviceProductID
					};
					var test1=jsonrpc.processes.findScmId(jsChargeId);
					var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
					for(i=0;i<chkChargeLine.length;i++) {
					var chkboxCharge=chkChargeLine[i];
					var index=chkboxCharge.value;
					document.getElementById("hdnChargeId"+index).value=test1.list[i].chargeIdList;
					}
					if(deleteScmLineIdArray.length!=0)
			    	 {
			    		 for(i=0;i<deleteScmLineIdArray.length;i++) {
			    				var jsChargeId =
			    	  	 		{
			    	    	serviceProductID:serviceProductID,
			    	    	chargeId_Scm:deleteScmLineIdArray[i]
			    				};
			    				//do nothing
			    				
			    				var deleteScmList=jsonrpc.processes.deleteScmLine(jsChargeId);
			    				
			    				
			    				}
			    	 }
					notSaveFlag=true;
			    }	
			    else
			    {
			    	alert(insertPcat.msgOut);		
			    	return false;
			    }
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
				hdnSeriveAttCounter:Number(countAttributes),	
				serviceProductID:serviceProductID,
				serviceInfoValue:Number(document.getElementById('hdnPrServiceSummary').value),
				sendToSCM:1,
				chargeInfoValue:Number(hiddenPrLineinfo),
				serviceId:servicesID,
				poNumber:poNumber,
				notSaveInScm:0,
				newPrFlagForView:Number(document.getElementById('createNewpr').value),
				chargeDetailsSCM:chargesList,
				serviceDetailID:serviceDetailID
				};
		try
		{ 
			
			var insertPcat = jsonrpc.processes.updateProductCatelogForScm(jsData,gb_sessionid);
		 
		if(insertPcat.successMsgScm=='done')
	    {
			alert(insertPcat.msgOut);
			var jsChargeId =
		  	 {
		    	serviceProductID:serviceProductID
			};
			var test1=jsonrpc.processes.findScmId(jsChargeId);
			var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
			for(i=0;i<chkChargeLine.length;i++) {
			var chkboxCharge=chkChargeLine[i];
			var index=chkboxCharge.value;
			document.getElementById("hdnChargeId"+index).value=test1.list[i].chargeIdList;
			}
			if(deleteScmLineIdArray.length!=0)
	    	 {
				
				
	    		 for(i=0;i<deleteScmLineIdArray.length;i++) {
	    				var jsChargeId =
	    	  	 		{
	    	    	serviceProductID:serviceProductID,
	    	    	chargeId_Scm:deleteScmLineIdArray[i]
	    				};
	    				//do nothing
	    				
	    				var deleteScmList=jsonrpc.processes.deleteScmLine(jsChargeId);
	    				
	    				
	    				}
	    	 }
			notSaveFlag=true;
			if(notSaveFlag==true)
			{
			var jsChargeId =
	  	 {
	    	serviceProductID:serviceProductID
		};
		var test1=jsonrpc.processes.findScmId(jsChargeId);
		var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		for(i=0;i<chkChargeLine.length;i++) {
		var chkboxCharge=chkChargeLine[i];
		var index=chkboxCharge.value;
		document.getElementById("hdnChargeId"+index).value=test1.list[i].chargeIdList;
		}
			}
	    }	
	    else
	    {
	    	alert(insertPcat.msgOut);		
	    	return false;
	    }
		}
	    catch(e)
	    {	alert("exception :  "+ e);
	    }
	}
	// This method is Added by Deepak Kumar for Third party(SCM)
	function insertViewScMBtn()
	{	
		accountNo=dialogArguments.document.getElementById("accNo").value;
		document.getElementById("accNo").value=accountNo;
		var dbList;
		var jsDataSerDetId=
		{
			serviceDetailID:serviceDetailID
		};
		dbList=jsonrpc.processes.getServiceDetailId(jsDataSerDetId);
		
		if((serviceDetailID==dbList.serDetIdAskeyValue)&&(dbList.thirdPartyFlag==1))
					{
				var tblRow=document.getElementById('scmBtn');
			 	var tblcol=tblRow.insertCell();
			 	var str1 ="<input type='button' readonly='readonly' align='right' width='100' onclick='openUpdateWindowSCM()' value='PR Attributes Details' name='viewScm'/>";
				CellContents = str1;
				tblcol.innerHTML = CellContents;
				document.getElementById("insertViewScmButton").value=1;
					}
		else{
			document.getElementById("insertViewScmButton").value=0;
		}
			
	}
	//This method is used to Validate Scm line and added by Deepak Kumar
	function validateAddCharge()

	{
				var chkChargeDetails=document.getElementsByName('chkSelectChargeDetail');
				for(i=0;i<chkChargeDetails.length;i++)
				{
			    var chkCharge=chkChargeDetails[i];
			    var indx=chkCharge.value;
				var itmCode=document.getElementById("txtItemCode"+indx).value;
				var scmValue=document.getElementById("txtValue"+indx).value;
				var chaQnity=document.getElementById("txtQuantity"+indx).value;
				var delLoc=document.getElementById("txtDelLocId"+indx).value;
				var subInven=document.getElementById("txtSubInvent"+indx).value;
				var aopYr=document.getElementById("financeYear"+indx).value;
				var aop1_ID=document.getElementById("txtBudgetHead"+indx).value;
				var aop2_ID=document.getElementById("txtBudget"+indx).value;
				var delL=document.getElementById("delLocation"+indx).value;
				var sub=document.getElementById("subInvent"+indx).value;
				var item=document.getElementById("itemName"+indx).value;
				var budgetAop=document.getElementById("budgHead"+indx).value;
				
				}
				//alert("In validateAddCharge for ");
				if((itmCode==-1)||(item==''))
				{
				alert("Please Fill Item Code in SCM Line");
				return false;
				}
				else if(scmValue=="")
				{
				alert("Please Fill Value in SCM Line");
				return false;
				}
				else if(chaQnity=="")
				{
				alert("Please Fill Quantity in SCM Line");
				return false;
				}
				else if((delLoc==-1)||(delL==''))
				{
				alert("Please Fill Delivery Location in SCM Line");
				return false;
				}
				else if((subInven==-1)||(sub==''||sub==''))
				{
				alert("Please Fill SubInventory in SCM Line");
				return false;
				}
				else if(aopYr==-1)
				{
				alert("Please Fill Fianacial Year in SCM Line");
				return false;
				}
				else if((aop1_ID==-1)||(budgetAop==''))
				{
				alert("Please Fill Budget Head 1 in SCM Line");
				return false;
				}
				else if(aop2_ID==-1)
				{
				alert("Please Fill Budget Head 2 in SCM Line");
				return false;
				}
				
				
				
	}
	
	function insertScMBtnForView(serviecDetId)
	{	
		var dbList;
		var jsDataSerDetId=
		{
			serviceDetailID:serviecDetId
		};
		
		dbList=jsonrpc.processes.getServiceDetailId(jsDataSerDetId);
		
		if(((serviecDetId==dbList.serDetIdAskeyValue)&&(dbList.thirdPartyFlag==1))&&(document.getElementById("insertViewScmButton").value==0))
					{
			if(viewScmButtonFlag==1){
				var tblRow=document.getElementById('scmBtn');
				var tblcol=tblRow.insertCell();
			 	var str1 ="<input type='button' readonly='readonly' align='right' width='100' onclick='openUpdateWindowSCM()' value='PR Attributes Details' name='viewScm'/>";
				CellContents = str1;
				tblcol.innerHTML = CellContents;
				document.getElementById("insertViewScmButton").value=1;
				viewScmButtonFlag++;
				
				}
			else{
				
				document.getElementById('scmBtn').style.display='block';
				return;
			}
						}
					
		else
		{
			if(document.getElementById('scmBtn')!=null){
				if(null==dbList.serDetIdAskeyValue){
				document.getElementById('scmBtn').style.display='none';
				document.getElementById("insertViewScmButton").value=0;
				viewScmButtonFlag++;
				}
				else
				{
					document.getElementById('scmBtn').style.display='block';
				}
			
			}
		}
	}
	
	function integersOnly(obj) {
	    var value_entered = obj.value;
	    if (!integer_only_warned) {
	        if (value_entered.indexOf(".") > -1) {
	            alert('Please enter an integer only. No decimal places.');
	            integer_only_warned = true;
	            obj.value = value_entered.replace(/[^0-9]/g,'');
	        }
	    }
	    obj.value = value_entered.replace(/[^0-9]/g,'');        
	}
	
	function checknumberSCM(obj)
	{
		try{
			var x = obj.value
			var anum=/(^\d+$)|(^\d+\.\d+$)/
			if (anum.test(x))
				testresult=true
			else
			{
				alert("Please input a valid number!")
				testresult=false
				obj.value = "";
				obj.focus();
			}
			return (testresult)
		}catch(e)
		{
			alert('error code 356: '+ e.message);
			return false;
		}
	}