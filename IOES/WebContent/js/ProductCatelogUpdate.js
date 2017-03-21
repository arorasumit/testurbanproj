/******************************************************************************************************************
=========================================================
FileName	:ProductCatelogUpdate.js
@Author		:Raghu
Date		:22-FEB-11
Purpose		:Use update product catelog.jsp page.			
=========================================================
********************************************************************************************************************/

/*Function	:saveProductCatelog_Update
*@Author	:Raghu
*Date		:22-Aug-11
*Purpose	:To update product catelog.jsp page only
**/
/*
[001]	 Rohit Verma    26-Feb-2013	 00-07480 	Validation for Duplicate Dail COMID, IRN No, TGNO No, Tool Free No
[003] 	 Vipin Saharia	27-Aug-14	GDB Lic Comp. segregation changed if condition for new licence company
*/
function saveProductCatelog_Update(sessionId_for_update,servicesID,serviceproductID,changeTypeId,changeType)
{
	var servicesID=servicesID;//service id .
	var serviceProductID=serviceproductID;//ServiceProductID .
	
	//	[013]	Start
	var butSummaryFlag = false;
	var butBillFlag = false;
	var butLocFlag = false;
	var butHWFlag = false;
	var butWarranFlag = false;
//	[013]	End
	var jsBillingData =			
		{
			serviceId:servicesID,
			serviceProductID:serviceproductID
		};
		  var jsonrpc = new JSONRpcClient(path + "/JSON-RPC");
        //Raghu: get billingInfoID ,locationInfoID and hardwareDetailID fetch from database use for update same ids.
		var billinglists = jsonrpc.processes.populateBillingDetails(jsBillingData);
		var locationlists = jsonrpc.processes.populateLocationDetails(jsBillingData);
		var hardwarelists = jsonrpc.processes.populateHardwareDetails(jsBillingData);
		if(billinglists.list.length==0)
		{
			document.getElementById('txtBillingAC').value = "0";
		}
		else
		{
				if(billinglists.list[0].isNfa==1)
				{
					document.getElementById('chkSelectNfa').checked=true;
				}
				else
				{
					document.getElementById('chkSelectNfa').checked=false;
				}
				
	      document.getElementById('txtBillingAC').value=billinglists.list[0].accountID;
	     }
	       for (i = 0 ; i < billinglists.list.length; i++)
			{
				document.getElementById('hdnBillingInfoID').value=billinglists.list[i].billingInfoID;
			}
		  for (i = 0 ; i < locationlists.list.length; i++)
			{
			document.getElementById('hdnServiceInfoID').value=locationlists.list[i].locationInfoID;
			}
		  for (i = 0 ; i < hardwarelists.list.length; i++)
			{
			document.getElementById('hdnHardwareInfoID').value=hardwarelists.list[i].hardwareDetailID;
			}
	var serviceNos=servicesID; 
	var but;
	var componentsList;
	if(document.getElementById("txtTaxation").value==2 && document.getElementById("changeReason").value==0)
	{
		alert("Please Select Standard Reason ");
		return false;
	}
	
	var callerWindowObj = dialogArguments;
	var orderNo = callerWindowObj.document.getElementById('poNumber').value
	var roleName = '1';
	var userId = '1';
	var jsonrpc = new JSONRpcClient(path + "/JSON-RPC");
	var orderDetails = jsonrpc.processes.getOrderDetailForSearching(orderNo,roleName,userId);
	var shortCode = 'AM';
	if(orderDetails.list.length>0)
	{
		stage=orderDetails.list[0].stageName;
		if(shortCode==stage)
		{
		}
		else if(stage=="New" || stage=="NEW")
		{
		}
		else if(stage=="Billing Trigger" && shortCode=="COPC")
		{
			alert("You are not authorised to save or update the values");
			return false;
		}
		else if(stage=="Partial Publish" && (shortCode=="COPC" || shortCode=="SED"))
		{
		}
		else if(shortCode=="SED" || stage=="SED")
		{
			alert("You are not authorised to save or update the values");
			return false;
		}
		else
		{
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
		}
	}
	if(changeType == 'NEW')
	{
	fnCalculateChargeSumForServiceSummary(document.getElementById('hdnServiceSummary').value,document.getElementById('hdnChargeInfo').value,document.getElementById('txtBillingformat').value);
	}
	var eLogicalCircuitId="";
	var infraOderNo="";
	var metasolvCircuitId="";
	var eLogicalCircuitId_new="";
	var infraOderNo_new="";
	var metasolvCircuitId_new="";
	var linKageFlag="NEW"
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
		var attfor_validation =new  Array();
		var attributeMaxLength = new Array();
		
		for(i=1;i<=countAttributes;i++)
		{
			if(document.getElementById('prodExpVal_'+i).value == "DROPDOWN")
			{
				if(document.getElementById('prodAttMandatory_'+i).value=="Y" && document.getElementById('prodAttVal_'+i).value=="0") 
				{
					if(document.getElementById('prodAttVal_'+i).isSummaryReq==1)
					{
						alert("Please input values in Service Summary section!!");
						document.getElementById('prodAttVal_'+i).focus();
						return false;
					}
					else
					{
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
						//[001] Start
						if((document.getElementById('hdnProdAttVal_'+i).value == 3932) || (document.getElementById('hdnProdAttVal_'+i).value == 3944) || 
						(document.getElementById('hdnProdAttVal_'+i).value == 3948)) //Checking for Duplicay of Dail Comid, IRN No,Toll Free No and TGNO No
						{
							//var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
	   	       				var flag = jsonrpc.processes.checkDuplicateAttributes(document.getElementById('prodAttVal_'+i).value,document.getElementById('hdnProdAttVal_'+i).value,0);
	   	       				if(flag == 1)
	   	       				{
								alert('Data Already Exists, Please Input Some Other Value');
								document.getElementById('prodAttVal_'+i).focus();
								return false;
							}
						}
						//[001] End
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
					//[001] Start
					if((document.getElementById('hdnProdAttVal_'+i).value == 3932) || (document.getElementById('hdnProdAttVal_'+i).value == 3944) || 
					(document.getElementById('hdnProdAttVal_'+i).value == 3948)) //Checking for Duplicay of Dail Comid, IRN No,Toll Free No and TGNO No
					{
						//var jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
   	       				var flag = jsonrpc.processes.checkDuplicateAttributes(document.getElementById('prodAttVal_'+i).value,document.getElementById('hdnProdAttVal_'+i).value,0);
   	       				if(flag == 1)
   	       				{
							alert('Data Already Exists, Please Input Some Other Value');
							document.getElementById('prodAttVal_'+i).focus();
							return false;
						}
					}
					//[001] End
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
	if(document.getElementById('hdnBillingInfo').value==1)
	{	
		if(selectDropdown("txtPODetailNo","Cust PO Detail No")==false)
		return false;
		
		if(selectDropdown("txtEntity","Entity")==false)
		return false;
		
		if(selectDropdown("txtBillingformat","Billing Format")==false)
		return false;
		
		if(selectDropdown("txtLicenceCo","Licence Company")==false)
		return false;
		
		if(selectDropdown("txtBillingType","Billig Type")==false)
		return false;
		
		if(selectDropdown("txtTaxation","Taxation")==false)
		return false;
		
		if(selectDropdown("txtBillingLvl","Billing Level")==false)
		return false;
		
		//VIPIN
		if(document.getElementById('hdnConfigValue').value==1)
		{	//[003] Start
			if(document.getElementById('txtLicenceCo').value!=282 && document.getElementById('txtLicenceCo').value!=382)
			{
				alert("Invalid License Company!!For 95p Orders, You have to select 402-BAL 95P or 402-BAL 95P_GB License Company");
				return false;
			}	//[003] End
		}
		
	}
	//For Location
	if(document.getElementById('hdnLocationInfo').value==1)
	{
		
		
		if(selectDropdown("ddPrimaryLocType","Primary Location")==false)
		return false;
		
		if(selectDropdown("ddPrimaryLocType","Primary Location")==false)
		return false;
		
		if(document.getElementById('ddPrimaryLocType').value=="1")
		{
			if(selectDropdown("ddPrimaryBCP","Primary BCP ID")==false)
			return false;
		}
			if(selectDropdown("ddSecondaryLocType","Secondary Location")==false)
			return false;
			
			if(document.getElementById('ddSecondaryLocType').value=="1")
			{
				if(selectDropdown("ddSecondaryBCP","Secondary BCP ID")==false)
				return false;
			}
			
	}
	if(document.getElementById('hdnHardwareInfo').value==1)
	{
		if(selectDropdown("txtStore","Store")==false)
		return false;
		if(selectDropdown("txtdispatchAddress","Dispatch Address Code")==false)
		return false;
	}
	
	if(document.getElementById('hdnComponentInfo').value==1)
	{
	
		var myComponentList=[];
		var chkComponentLine=document.getElementsByName('chkComponents');
		var myComponentList=[];
		
		for(i=0;i<chkComponentLine.length;i++)
		{
		
		
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

					    startDateLogic:(document.getElementById("txtCompStartDateLogic"+index).value),
						endDateLogic:(document.getElementById("txtCompEndDateLogic"+index).value),						

						startDateDays:Number(document.getElementById("txtStartDays"+index).value),
						startDateMonth:Number(document.getElementById("txtStartMonth"+index).value),
						endDateDays:Number(document.getElementById("txtEndDays"+index).value),
						endDateMonth:Number(document.getElementById("txtEndMonth"+index).value),
						
						isReqStartdatelogic:Number(document.getElementById('txtCompStartDateLogic'+index).isRequired),
						isReqEnddateLogic:Number(document.getElementById('txtCompEndDateLogic'+index).isRequired)

						
					};
			myComponentList[i]=objComponents;
			
		}
	
		componentsList={"javaClass": "java.util.ArrayList",
				"list": myComponentList
				};
		 }
	
	
	//COMPONENT CODE START
	/*if(document.getElementById('hdnComponentInfo').value==1)
	{
	   alert('hdnComponentsInfo info end');
	    alert('hdnComponentsInfo: '+document.getElementById('hdnComponentsInfo').value);
	    alert('in component if');
		var myComponentList=[];
		var chkComponentLine=document.getElementsByName('chkComponents');
		var myComponentList=[];
		for(i=0;i<chkComponentLine.length;i++)
		{
			var chkboxComponent=chkComponentLine[i];
			var index=chkboxComponent.value;
			alert('index:  '+index);
			var objComponents={"javaClass": "com.ibm.ioes.forms.ComponentsDto",
						componentInfoID:Number(document.getElementById("component_infoID"+index).value),
						componentID:Number(document.getElementById("components"+index).value),
						packageID:Number(document.getElementById("packageID"+index).value),
						componentInfoID_String:(document.getElementById("component_infoID"+index).value),
						componentName:(document.getElementById("componentsName"+index).value),
					    packageName:(document.getElementById("packageName"+index).value),
						componentStatus:(document.getElementById("status"+index).value),
					    startLogic:(document.getElementById("startdatelogic"+index).value),
						endLogic:(document.getElementById("enddatelogic"+index).value),
						start_date:(document.getElementById("startdate"+index).value),
						end_date:(document.getElementById("enddate"+index).value)
					};
			myComponentList[i]=objComponents;
		}
		componentsList={"javaClass": "java.util.ArrayList",
				"list": myComponentList
				};
		 }*/
	//COMPONENT CODE END
	if(document.getElementById('hdnChargeInfo').value==1)
	{
		var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		for(i=0;i<chkChargeLine.length;i++)
		{
			var chkboxCharge=chkChargeLine[i];
			var index=chkboxCharge.value;
			if(checkTotalAmount()==false)
			{
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
			if(document.getElementById('hdnHardwareInfo').value==1)
		{	
			
 			var payment11=document.getElementById('txtPayment1'+index).value;
 			
			var payment22=document.getElementById('txtPayment2'+index).value;
			
			var payment33=document.getElementById('txtPayment3'+index).value;
			
			var payment44=document.getElementById('txtPayment4'+index).value;
			
	if(payment11 == "" && payment22 == "" && payment33 == "" && payment44 == "")
		{
			payment1=0;
			payment2=0;
			payment3=0;
			payment4=0;
		}
	else if(payment11 == "0" && payment22 == "0" && payment33 == "0" && payment44 == "0")
	{
			payment1="";
			payment2="";
			payment3="";
			payment4="";
	}
	else
		{
			if (payment11 > 100 || payment22 > 100 || payment33 > 100 || payment44 > 100 )
			{
				alert('Sum of all payment terms should be equal to 100');
				document.getElementById('txtPayment1'+index).value = "";
				document.getElementById('txtPayment2'+index).value = "";
				document.getElementById('txtPayment3'+index).value = "";
				document.getElementById('txtPayment4'+index).value = "";
				document.getElementById('txtPayment1'+index).focus();
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
				alert('Sum of all payment terms should be equal to 100');
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
						lineItemName:document.getElementById("cmbLineItem"+index).value,
						subLineItemName:document.getElementById("cmbSubLineItem"+index).value,
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
			serviceProductID:Number(serviceProductID),
			serviceId:servicesID,
			podetailID:document.getElementById('txtPODetailNo').value,
			chargesDetails : chargesList
		};
	    jsonrpc = new JSONRpcClient(path + "/JSON-RPC");
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
						//lawkush
						isReqTxtCStartDays:Number(document.getElementById('txtStartDays'+index).isRequired),
						isReqTxtCStartMonths:Number(document.getElementById('txtStartMonth'+index).isRequired),
						isReqTxtCEndDays:Number(document.getElementById('txtEndDays'+index).isRequired),
						isReqTxtCEndMonths:Number(document.getElementById('txtEndMonth'+index).isRequired),
						//lawkush
						chargeInfoID_String:document.getElementById("hdnChargeId"+index).value,
						chargeAnnotation:document.getElementById("txtCAnnotation"+index).value,
						isReqTxtCAnnotation:Number(document.getElementById('txtCAnnotation'+index).isRequired),
						chargeRemarks:document.getElementById("txtRemarks"+index).value,
						paymentTerm1:Number(document.getElementById('txtPayment1'+index).value),
						paymentTerm2:Number(document.getElementById('txtPayment2'+index).value),
						paymentTerm3:Number(document.getElementById('txtPayment3'+index).value),
						paymentTerm4:Number(document.getElementById('txtPayment4'+index).value),
						lineItemName:document.getElementById("cmbLineItem"+index).value,
						subLineItemName:document.getElementById("cmbSubLineItem"+index).value,
						isReqLineItem:Number(document.getElementById('cmbLineItem'+index).isRequired),
						isReqSubLineItem:Number(document.getElementById('cmbSubLineItem'+index).isRequired),
						taxRate:document.getElementById("txtTaxRate"+index).value,
						isReqTxtRemarks:Number(document.getElementById("txtRemarks"+index).isRequired)
					
						};
			myList[i]=ob;
		}
		chargesDetailList={"javaClass": "java.util.ArrayList",
				"list": myList
				};
    }
    if(document.getElementById('hdnServiceSummary').value==1)
	{
	   if(document.getElementById('hdnBillingInfo').value==1)
		{
		if(document.getElementById('txtPODetailNo').isRequired=="1"  && document.getElementById('txtPODetailNo').value == "0" )
			{
			alert("Please enter PO ID");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
					butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtPODetailNo').focus();
		     return false;
			}
		else if(document.getElementById('txtPODetailNo').isRequired=="0"  && document.getElementById('txtPODetailNo').value == "0" )
			{
			document.getElementById('txtPODetailNo').value=0;
			}
		if(document.getElementById('txtBillingCP').isRequired=="1"  && document.getElementById('txtBillingCP').value == "0" )
			{
			alert("Please enter Credit Period");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingCP').focus();
			return false;
			}
		else if(document.getElementById('txtBillingCP').isRequired=="0"  && document.getElementById('txtBillingCP').value == "0" )
			{
			document.getElementById('txtBillingCP').value=0;
			}
		if(document.getElementById('txtEntity').isRequired=="1"  && document.getElementById('txtEntity').value == "0" )
		{
			alert("Please enter Legal Entity");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtEntity').focus();
			return false;
		}
		else if(document.getElementById('txtEntity').isRequired=="0"  && document.getElementById('txtEntity').value == "0" )
		{
			document.getElementById('txtEntity').value=0;
		}
		if(document.getElementById('txtBillingMode').isRequired=="1"  && document.getElementById('txtBillingMode').value == "0" )
		{
			alert("Please enter Billing Mode");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingMode').focus();
			return false;
		}
		else if(document.getElementById('txtBillingMode').isRequired=="0"  && document.getElementById('txtBillingMode').value == "0" )
		{
			document.getElementById('txtBillingMode').value=0;
		}
		if(document.getElementById('txtBillingformat').isRequired=="1"  && document.getElementById('txtBillingformat').value == "0" )
		{
			alert("Please enter Bill Format");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingformat').focus();
			return false;
		}
		else if(document.getElementById('txtBillingformat').isRequired=="0"  && document.getElementById('txtBillingformat').value == "0" )
		{
			document.getElementById('txtBillingformat').value=0;
		}
		if(document.getElementById('txtLicenceCo').isRequired=="1"  && document.getElementById('txtLicenceCo').value == "0" )
		{
			alert("Please enter Licence Co");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtLicenceCo').focus();
			return false;
		}
		else if(document.getElementById('txtLicenceCo').isRequired=="0"  && document.getElementById('txtLicenceCo').value == "0" )
		{
			document.getElementById('txtBillingformat').value=0;
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
		if(document.getElementById('txtBillingType').isRequired=="1"  && document.getElementById('txtBillingType').value == "0" )
		{
			alert("Please enter Billing Type");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingType').focus();
			return false;
		}
		else if(document.getElementById('txtBillingType').isRequired=="0"  && document.getElementById('txtBillingType').value == "0" )
		{
			document.getElementById('txtBillingType').value=0;
		}
		if(document.getElementById('txtTaxation').isRequired=="1"  && document.getElementById('txtTaxation').value == "0" )
		{
			alert("Please enter Taxation");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtTaxation').focus();
			return false;
		}
		else if(document.getElementById('txtTaxation').isRequired=="0"  && document.getElementById('txtTaxation').value == "0" )
		{
			document.getElementById('txtTaxation').value=0;
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
		if(document.getElementById('txtBillingLvl').isRequired=="1"  && document.getElementById('txtBillingLvl').value == "0" )
		{
			alert("Please enter Billing Level");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingLvl').focus();
			return false;
		}
		else if(document.getElementById('txtBillingLvl').isRequired=="0"  && document.getElementById('txtBillingLvl').value == "0" )
		{
			document.getElementById('txtBillingLvl').value=0;
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
		if(document.getElementById('hdnHardwareInfo').value!=1)//if Hardware
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
		if(document.getElementById('bbPrimaryBCP').isRequired=="1"  && document.getElementById('bbPrimaryBCP').value == "" )
		{
			alert("Please enter   BCP details");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('bbPrimaryBCP').focus();
			return false;
		}
		else if(document.getElementById('bbPrimaryBCP').isRequired=="0"  && document.getElementById('bbPrimaryBCP').value == "" )
		{
			document.getElementById('bbPrimaryBCP').value=0;
		}
		
		     	but=document.getElementById('btnBillingAndChargesInformation');
		if(but.value=="-" && butBillFlag == true)
					{
						show('tblBillingAndChargesInformation',but);
					}
					
		if(document.getElementById('hdnISFLEFLAG').value==1  && document.getElementById('txtBillingLvl').value!=2 )
			{
				
				alert("Billing Level should be PO Level");
				but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
							butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
				document.getElementById('txtBillingLvl').focus();
				return false;
			
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
			else
			{  if(serviceTypeId==4)
			 document.getElementById('ddSecondaryLocType').value="2";
			}
		}
		
		if(document.getElementById('ddPrimaryLocType').value=="1" && document.getElementById("ddPrimaryLocType").isRequired=="1")
		{
			if(document.getElementById("ddPrimaryBCP").value=="0" && document.getElementById("ddPrimaryBCP").isRequired=="1")
			{
			alert("Please enter Primary Location BCP");
			but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="+")
					{
						butLocFlag = true;
						show('tblServiceLocationDetails',but);
					}
			document.getElementById('ddPrimaryBCP').focus();
			return false;
			}
			else if(document.getElementById('ddPrimaryBCP').isRequired=="0"  && document.getElementById('ddPrimaryBCP').value == "0" )
			{
			document.getElementById('ddPrimaryBCP').value=0;
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
			if(document.getElementById("ddSecondaryBCP").value=="0" && document.getElementById("ddSecondaryBCP").isRequired=="1")
			{
			alert("Please enter Secondary Location BCP");
			but=document.getElementById('btnServiceLocationDetails');
					if(but.value=="+")
					{
						butLocFlag = true;
						show('tblServiceLocationDetails',but);
					}
			document.getElementById('ddSecondaryBCP').focus();
			return false;
			}
			else if(document.getElementById('ddSecondaryBCP').isRequired=="0"  && document.getElementById('ddSecondaryBCP').value == "0" )
			{
			document.getElementById('ddSecondaryBCP').value=0;
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
	if(document.getElementById('hdnHardwareInfo').value==1)
	{
		if(document.getElementById('txtStore').isRequired=="1"  && document.getElementById('txtStore').value == "0" )
		{
			alert("Please enter Store");
				but=document.getElementById('btnHwDetails');
					if(but.value=="+")
					{
						butHWFlag = true;
						show('tblHwDetails',but);
					}
			document.getElementById('txtStore').focus();
			return false;
		}
		else if(document.getElementById('txtStore').isRequired=="0"  && document.getElementById('txtStore').value == "0" )
		{
			document.getElementById('txtStore').value=0;
		}
		if(document.getElementById('txtHtype').value=="0" && document.getElementById('txtHtype').isRequired=="1")
		{
			alert("Please enter Hardware type");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			document.getElementById('txtHtype').focus();
			return false;
		}
		else if(document.getElementById('txtHtype').value=="0" && document.getElementById('txtHtype').isRequired=="0")
		{
			document.getElementById('txtHtype').value=0;
		}
		if(document.getElementById('txtform').value=="0" &&document.getElementById('txtform').isRequired=="1" )
		{
			alert("Please enter Form available");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			document.getElementById('txtform').focus();
			return false;
		}
		else if(document.getElementById('txtform').value=="0" && document.getElementById('txtform').isRequired=="0" )
		{
			document.getElementById('txtform').value=0;
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
		if(document.getElementById('txtTSale').isRequired=="1"  && document.getElementById('txtTSale').value == "0" )
		{
			alert("Please enter Type of sale");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			document.getElementById('txtTSale').focus();
			return false;
		}
		else if(document.getElementById('txtTSale').isRequired=="0"  && document.getElementById('txtTSale').value == "0" )
		{
			document.getElementById('txtTSale').value=0;
		}
		but=document.getElementById('btnHwDetails');
					if(but.value=="-" && butHWFlag == true)
					{
						show('tblHwDetails',but);
					}
		if(document.getElementById('txtdispatchAddress').isRequired=="1"  && (document.getElementById('txtdispatchAddress').value == "" || document.getElementById('txtDispatchAddressSuggest').value=="" ))
		{
			alert("Please enter Dispatch Address");
			but=document.getElementById('btnDispatchAddress');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblDispatchAddress',but);
				}
			document.getElementById('txtdispatchAddress').focus();
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
	}
		but=document.getElementById('btnWarrentyDetails');
				if(but.value=="-" && butWarranFlag == true)
				{
					show('tblWarrentyDetails',but);
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
			prodAttriMaxLength:attributeMaxLength,
			serviceProductID:Number(serviceProductID),
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
			dispatchContactName:document.getElementById('txtDispatchContactName').value,
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
			serviceProductID:Number(serviceProductID),
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
		    serviceInfoValue:Number(document.getElementById('hdnServiceSummary').value),
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
			poNumber:Number(orderNo),	
			changeTypeId:Number(changeTypeId),
			componentDetails:componentsList,
			componentInfoValue:Number(document.getElementById('hdnComponentInfo').value),
			configValue:Number(document.getElementById('hdnConfigValue').value)	
		};
	}
	else
	{
		if(document.getElementById('hdnBillingInfo').value==1)
		{
		if(document.getElementById('txtPODetailNo').isRequired=="1"  && document.getElementById('txtPODetailNo').value == "0" )
			{
			alert("Please enter PO ID");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtPODetailNo').focus();
			return false;
			}
		else if(document.getElementById('txtPODetailNo').isRequired=="0"  && document.getElementById('txtPODetailNo').value == "0" )
			{
			document.getElementById('txtPODetailNo').value=0;
			}
		if(document.getElementById('txtBillingCP').isRequired=="1"  && document.getElementById('txtBillingCP').value == "0" )
			{
			alert("Please enter Credit Period");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingCP').focus();
			return false;
			}
		else if(document.getElementById('txtBillingCP').isRequired=="0"  && document.getElementById('txtBillingCP').value == "0" )
			{
			document.getElementById('txtBillingCP').value=0;
			}
		if(document.getElementById('txtEntity').isRequired=="1"  && document.getElementById('txtEntity').value == "0" )
		{
			alert("Please enter Legal Entity");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtEntity').focus();
			return false;
		}
		else if(document.getElementById('txtEntity').isRequired=="0"  && document.getElementById('txtEntity').value == "0" )
		{
			document.getElementById('txtEntity').value=0;
		}
		if(document.getElementById('txtBillingMode').isRequired=="1"  && document.getElementById('txtBillingMode').value == "0" )
		{
			alert("Please enter Billing Mode");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingMode').focus();
			return false;
		}
		else if(document.getElementById('txtBillingMode').isRequired=="0"  && document.getElementById('txtBillingMode').value == "0" )
		{
			document.getElementById('txtBillingMode').value=0;
		}
		if(document.getElementById('txtBillingformat').isRequired=="1"  && document.getElementById('txtBillingformat').value == "0" )
		{
			alert("Please enter Bill Format");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingformat').focus();
			return false;
		}
		else if(document.getElementById('txtBillingformat').isRequired=="0"  && document.getElementById('txtBillingformat').value == "0" )
		{
			document.getElementById('txtBillingformat').value=0;
		}
		if(document.getElementById('txtLicenceCo').isRequired=="1"  && document.getElementById('txtLicenceCo').value == "0" )
		{
			alert("Please enter Licence Co");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtLicenceCo').focus();
			return false;
		}
		else if(document.getElementById('txtLicenceCo').isRequired=="0"  && document.getElementById('txtLicenceCo').value == "0" )
		{
			document.getElementById('txtBillingformat').value=0;
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
		if(document.getElementById('txtBillingType').isRequired=="1"  && document.getElementById('txtBillingType').value == "0" )
		{
			alert("Please enter Billing Type");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingType').focus();
			return false;
		}
		else if(document.getElementById('txtBillingType').isRequired=="0"  && document.getElementById('txtBillingType').value == "0" )
		{
			document.getElementById('txtBillingType').value=0;
		}
		if(document.getElementById('txtTaxation').isRequired=="1"  && document.getElementById('txtTaxation').value == "0" )
		{
			alert("Please enter Taxation");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtTaxation').focus();
			return false;
		}
		else if(document.getElementById('txtTaxation').isRequired=="0"  && document.getElementById('txtTaxation').value == "0" )
		{
			document.getElementById('txtTaxation').value=0;
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
		if(document.getElementById('txtBillingLvl').isRequired=="1"  && document.getElementById('txtBillingLvl').value == "0" )
		{
			alert("Please enter Billing Level");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
			document.getElementById('txtBillingLvl').focus();
			return false;
		}
		else if(document.getElementById('txtBillingLvl').isRequired=="0"  && document.getElementById('txtBillingLvl').value == "0" )
		{
			document.getElementById('txtBillingLvl').value=0;
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
		if(document.getElementById('hdnHardwareInfo').value!=1)//if Hardware
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
		if(document.getElementById('bbPrimaryBCP').isRequired=="1"  && document.getElementById('bbPrimaryBCP').value == "" )
		{
			alert("Please enter   BCP details");
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
				}
		if(document.getElementById('bbPrimaryBCP').isRequired=="1"  && document.getElementById('bbPrimaryBCP').value == "" )
		{
			alert("Please enter   BCP details");
			but=document.getElementById('btnBillingAddress');
				if(but.value=="+")
				{
					show('tblBillingAddress',but);
				}
			document.getElementById('bbPrimaryBCP').focus();
			return false;
		}
		else if(document.getElementById('bbPrimaryBCP').isRequired=="0"  && document.getElementById('bbPrimaryBCP').value == "" )
		{
			document.getElementById('bbPrimaryBCP').value=0;
		}
			but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="-" && butBillFlag == true)
					{
						show('tblBillingAndChargesInformation',but);
				    }
				    
		if(document.getElementById('hdnISFLEFLAG').value==1  && document.getElementById('txtBillingLvl').value!=2 )
			{
				
				alert("Billing Level should be PO Level");
				but=document.getElementById('btnBillingAndChargesInformation');
					if(but.value=="+")
					{
						butBillFlag = true;
						show('tblBillingAndChargesInformation',but);
					}
				document.getElementById('txtBillingLvl').focus();
				return false;
			
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
			if(document.getElementById("ddPrimaryBCP").value=="0" && document.getElementById("ddPrimaryBCP").isRequired=="1")
			{
			alert("Please enter Primary Location BCP");
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}
			document.getElementById('ddPrimaryBCP').focus();
			return false;
			}
			else if(document.getElementById('ddPrimaryBCP').isRequired=="0"  && document.getElementById('ddPrimaryBCP').value == "0" )
			{
			document.getElementById('ddPrimaryBCP').value=0;
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
			if(document.getElementById("ddSecondaryBCP").value=="0" && document.getElementById("ddSecondaryBCP").isRequired=="1")
			{
			alert("Please enter Secondary Location BCP");
			but=document.getElementById('btnServiceLocationDetails');
				if(but.value=="+")
				{
					butLocFlag = true;
					show('tblServiceLocationDetails',but);
				}
			document.getElementById('ddSecondaryBCP').focus();
			return false;
			}
			else if(document.getElementById('ddSecondaryBCP').isRequired=="0"  && document.getElementById('ddSecondaryBCP').value == "0" )
			{
			document.getElementById('ddSecondaryBCP').value=0;
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
	if(document.getElementById('hdnHardwareInfo').value==1)
	{
		if(document.getElementById('txtStore').isRequired=="1"  && document.getElementById('txtStore').value == "0" )
		{
			alert("Please enter Store");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			document.getElementById('txtStore').focus();
			return false;
		}
		else if(document.getElementById('txtStore').isRequired=="0"  && document.getElementById('txtStore').value == "0" )
		{
			document.getElementById('txtStore').value=0;
		}
		if(document.getElementById('txtHtype').value=="0" && document.getElementById('txtHtype').isRequired=="1")
		{
			alert("Please enter Hardware type");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			document.getElementById('txtHtype').focus();
			return false;
		}
		else if(document.getElementById('txtHtype').value=="0" && document.getElementById('txtHtype').isRequired=="0")
		{
			document.getElementById('txtHtype').value=0;
		}
		if(document.getElementById('txtform').value=="0" && document.getElementById('txtform').isRequired=="1" )
		{
			alert("Please enter Form available");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			document.getElementById('txtform').focus();
			return false;
		}
		else if(document.getElementById('txtform').value=="0" && document.getElementById('txtform').isRequired=="0" )
		{
			document.getElementById('txtform').value=0;
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
		if(document.getElementById('txtTSale').isRequired=="1"  && document.getElementById('txtTSale').value == "0" )
		{
			alert("Please enter Type of sale");
			but=document.getElementById('btnHwDetails');
				if(but.value=="+")
				{
					butHWFlag = true;
					show('tblHwDetails',but);
				}
			document.getElementById('txtTSale').focus();
			return false;
		}
		else if(document.getElementById('txtTSale').isRequired=="0"  && document.getElementById('txtTSale').value == "0" )
		{
			document.getElementById('txtTSale').value=0;
		}
		but=document.getElementById('btnHwDetails');
				if(but.value=="-" && butHWFlag == true)
				{
					show('tblHwDetails',but);
				}
		if(document.getElementById('txtdispatchAddress').isRequired=="1"  && document.getElementById('txtdispatchAddress').value == "0" )
		{
			alert("Please enter Dispatch Address");
			but=document.getElementById('btnDispatchAddress');
				if(but.value=="+")
				{	
					butHWFlag = true;
					show('tblDispatchAddress',but);
				}
			document.getElementById('txtdispatchAddress').focus();
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
			dispatchContactName:document.getElementById('txtDispatchContactName').value,
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
			serviceProductID:Number(serviceProductID),
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
            isReqTxtPODetailNo:document.getElementById('txtPODetailNo').isRequired,
			isReqTxtBillingAC:document.getElementById('txtBillingAC').isRequired,
			isReqTxtBillingCP:document.getElementById('txtBillingCP').isRequired,
			isReqTxtEntity:document.getElementById('txtEntity').isRequired,
			isReqTxtBillingMode:document.getElementById('txtBillingMode').isRequired,
			isReqTxtBillingformat:document.getElementById('txtBillingformat').isRequired,
			isReqTxtLicenceCo:document.getElementById('txtLicenceCo').isRequired,
			isReqTxtTaxation:document.getElementById('txtTaxation').isRequired,
			isReqTxtCmtPeriod:document.getElementById('txtCmtPeriod').isRequired,
			isReqTxtBillingLvl:document.getElementById('txtBillingLvl').isRequired,
			isReqTxtPenaltyClause:document.getElementById('txtPenaltyClause').isRequired,
			isReqTxtWarrantyClause:Number(document.getElementById('txtWarrantClause').isRequired),
			//satyapan OSP By nagarjuna
			isReqOSPTagging:Number(document.getElementById('txtOSPTagging').isRequired),//satyapan osp
			isReqOSPRegNo:Number(document.getElementById('txtOSPRegNo').isRequired),
			isReqOSPRegDate:document.getElementById('txtOSPRegDate').isRequired,
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
			isReqTxtStore:document.getElementById('txtStartDateLogic').isRequired,
			isReqTxtHtype:document.getElementById('txtEndDateLogic').isRequired,
			isReqTxtFromLocation:document.getElementById('txtFAddress').isRequired,
			isReqTxtToLocation:document.getElementById('txtToAddress').isRequired, // by Saurabh for validation of to and from location
			poNumber:Number(orderNo),	
			changeTypeId:Number(changeTypeId),
			componentDetails:componentsList,
			componentInfoValue:Number(document.getElementById('hdnComponentInfo').value),
			configValue:Number(document.getElementById('hdnConfigValue').value)	
			 
		};
	}
	/*if((document.getElementById('hdnServiceSummary').value==1) && attributeID != null)
	   {
	         var jsonrpc1 = new JSONRpcClient(path + "/JSON-RPC");
    	       var productType = jsonrpc1.processes.checkProductServiceType(serviceDetID);
    	       if(productType=='Hardware')
    	       {
    	        var Billingformat=document.getElementById('txtBillingformat').value;
			    var result=  checkProductServiceType(attributeID,attributeVal,Billingformat);
			   if(result == false)
			    return;
			  }
	   }*/
	try
	{
	    jsonrpc = new JSONRpcClient(path + "/JSON-RPC");
		var sessionId=sessionId_for_update;
    	var insertPcat = jsonrpc.processes.updateProductCatelog(jsData,sessionId);
    	//Raghu: fetch Component id and Charge id after update product catalog and change product catalog page 	
    	if(document.getElementById('hdnComponentInfo').value==1)
    	{
			var dtoListComponentIds=insertPcat.listComponent;
			if(dtoListComponentIds!=null)
			{
				var chkComponents=document.getElementsByName('chkComponents');
				if(chkComponents.length > 0)
				{
					for(i=0;i<chkComponents.length;i++)
					{
				 		var chkboxComponent=chkComponents[i];
				 		var index=chkboxComponent.value;
				   	document.getElementById("component_infoID"+index).value = dtoListComponentIds.list[i];
					}
				}
			}	
		}
		if(document.getElementById('hdnChargeInfo').value==1)
		{
    	    var dtoListChargeInfoIds=insertPcat.listChargeInfoIdList;
	        var chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
	        if(dtoListChargeInfoIds!=null)
	        {
		        if(chkChargeLine.length > 0)
		        {
			    	for(i=0;i<chkChargeLine.length;i++)
					{
						var chkboxCharge=chkChargeLine[i];
						var index=chkboxCharge.value;
					    document.getElementById("hdnChargeId"+index).value = dtoListChargeInfoIds.list[i];
					}
				}
			}	
       }
       //Raghu:END
    }
    catch(e)
    {	alert("exception :  "+ e);
    }
    
    if(isNull(insertPcat.errors)==true)
    {
		var callerWindowObj = dialogArguments;
    	alert(insertPcat.msgOut);  
    		var flag=5;
    }	
    else
    {
    	alert(insertPcat.errors.list[0]);		
    	return false;
    }
}
/*
//lawkush
Method fetched List of values for auto suggestion 

*/
destAttID=new Array();
function fnGetDestLabelValueForLOV(attMstrId,fieldName)
{
	var labelValue;
		
	var countAttributes=document.getElementById('hdnSeriveAttCounter').value;
	var attLabelvalue = fieldName;
	var jsData =			
    {
		attMasterId:attMstrId,
		attributeLabel:attLabelvalue
	};
	
	//jsonrpc = new JSONRpcClient("<%=request.getContextPath()%>" + "/JSON-RPC");
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
				if(document.getElementById("hdnProdAttVal_"+i) != null)
				{
					if(document.getElementById("hdnProdAttVal_"+i).value == destAttID[j])
					{
						//[004.1] Start
						//document.getElementById("prodAttVal_"+i).value=labelValue.list[j].planName; Code Commented
						var depValue=labelValue.list[j].planName;//Assigning Default Value from DB to a Variable e.g prodAttVal_15 or abc
						//destText Added By Kalpana to get Text for selectd Plan
						var destText=labelValue.list[j].destText;//Assigning Default TEXT Value from DB to destination attribute
						//end
						if(depValue.indexOf("prodAttVal")!=-1)//checking prodAttVal value in depValue varible via contains function of Javascript. if it founds it goes in if or in else
						{
							var splitValue=depValue.split('_');//Spliting depValue by using _ So element1 is prodAttVal and element is 8 
							document.getElementById("prodAttVal_"+i).value=document.getElementById("prodAttVal_"+splitValue[1]).value;//assigning Dependent Value, 2nd elememnt of split function
						}
						else
						{
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
							
							if(document.getElementById("autoSuggProdAttVal_"+i) != null)
							{
								document.getElementById("autoSuggProdAttVal_"+i).className='dropdownMargin1';	
							}						
						}
						if(labelValue.list[j].mandatory == '1')
						{
							document.getElementById("prodAttVal_"+i).isSummaryReq='1';
							document.getElementById("prodAttVal_"+i).className='inputBorder1';
							
							if(document.getElementById("autoSuggProdAttVal_"+i) != null)
							{
								document.getElementById("autoSuggProdAttVal_"+i).className='dropdownMargin';	
							}	
						}
						//[004.1] End
					}
				}
			}	
		}
	}
}