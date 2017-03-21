//*************************************************************************************************************************************
	/*Created By: Anil Kumar
	  Date:		  20-Mar-2012
	  Purpose:	  Enable Disable Control, and access prevent for role for order entry system
	*/
//**************************************************************************************************************************************

/*
=========================================================================================================
									         CLEP ORDER ENTRY
CLEP AND SAAS ONLY ORDERS
--------------------------
STATE-1 {Order is Aproval for AM}									         
	-->Disable all changes(coming from mpp) in order except add/delete new charges from GUI
STATE-2 {Order is Aproval for PM/COPC}
	--> Disable Full Order changes, allow only for aproval
STATE-3 {Order is in SED after final aproval before mpp RFBT response}	
	-->Before Request For Billing Trigger(RFBT) from mpp order is in SED, then Publish is Disable
	-->(in Change Order->Order is available in SED for Aproval, then Publishing)
	(Note: State 3 is not currently used requirement has been chaged)
STATE-4 {Order is in SED Queue for Publish after RFBT}
	-->Only allow for Publishing details changes
	-->In Change Order->After Aproval for SED it is available for Publish	
	(Note: State 4 is not currently used requirement has been chaged)
STATE-5 (Order is in COPC for Billing Trigger)
	
Changes will be effected on following jsp pages
-----------------------------------------------
NewOrder.jsp
ChangeOrder.jsp
ViewProductCatelog.jsp
ChangeViewProductCatelog.jsp
ServiceAttributes.jsp
ChangeServiceAttribute.jsp
==========================================================================================================	
*/

function orderEntryEnableDisable(pageId,state, sectionName)
{
	try{
			if(pageId=='NEWORDER' || pageId=='CHANGEORDER' || pageId=='BILLING_TRRIGER'){		
				//==========================[ start clep codes ]=======================================		
				if(state==1 || state==2 || state==5 ){														
						enableDisableOrderEntryPage(state,pageId);
					}
				//==========================[ end clep codes ]=========================================
			}			
			if(pageId=='SERVICEATTRIBUTE' || pageId=='CHANGESERVICEATTRIBUTE'){
				//==========================[ start clep codes ]=======================================
				if(state==1 || state==2 || state==3 || state==4){														
						enableDisableOrderEntryPage(state,pageId);
					}
				//==========================[ end clep codes ]=========================================
			}
			if(pageId=='VIEWPRODUCTCATELOG' || pageId=='CHANGEVIEWPRODUCTCATELOG'){
				//==========================[ start clep codes ]=======================================
					if(state==1 || state==2 || state==3 || state==4){														
						enableDisableProductCatelog(changeSubTypeID,state,pageId, sectionName);
					}
				//==========================[ end clep codes ]=========================================
			}	
	}catch(e)
	{
		alert('error code 187: '+ e.message);
	}
}

function  getOrderStateforClep(orderno,path){
	try{
		jsonrpc = new JSONRpcClient(path + "/JSON-RPC");
		var stateId=jsonrpc.processes.getOrderStateforClep(orderno);	
		return stateId;	
	}catch(e)
	{
		alert('error code 188: '+ e.message);
	}
}

function enableDisableProductCatelog(subchangetypeid,stateId,pageid, sectionName){
	try{
		//==================================[ start clep codes ]==========================================================
		if(stateId==1 || stateId==2 || stateId==3 || stateId==4){
			if(pageid=='VIEWPRODUCTCATELOG' || pageid=='CHANGEVIEWPRODUCTCATELOG'){
				if(subchangetypeid==0){
					//Puneet disabling the section only in case of billing
					if(null != sectionName && sectionName == billingSection){
					//disable view product catelog page controls for new lineitems created by mpp
						enableDisableFieldsetGeneric('billingInfoFieldsetId','DISABLE');						
						
						if(stateId==1)
						{
							var arrEnableDisableFlags=clep_arrEnableDisableFlags;							
							for(var i=0;i<arrEnableDisableFlags.length;i++){																													
								if(arrEnableDisableFlags[i]=='ENABLE')
								{
									arrEnableDisableFlags[i]='';
								}							
							}						
							enableDisableChargeFieldsetGeneric(arrEnableDisableFlags);
						}
						else if (stateId==2 || stateId==3 || stateId==4 )
						{
							enableDisableFieldsetGeneric('chargesInfoFieldsetId','DISABLE');	
							//DISABLE add charge/delete charge button
							document.getElementById('btnAddContact').disabled=true;
			  				document.getElementById('btnDeletePoDetail').disabled=true;
						}
					}
				}
				if(subchangetypeid !=0 && subchangetypeid > 0){
					if(null != sectionName && sectionName == billingSection){
						enableDisableFieldsetGeneric('billingInfoFieldsetId','DISABLE');						
						enableDisableFieldsetGeneric('chargesInfoFieldsetId','DISABLE');
						document.getElementById('btnAddContact').disabled=true;
			  			document.getElementById('btnDeletePoDetail').disabled=true;
					}	
			  			document.getElementById('saveImage').style.display="none";
				}
			}			
		}
		//======================================[ end clep codes ]===========================================================
	}catch(e)
	{
		alert('error code 189: '+ e.message);
	}
}

function enableDisableFieldsetGeneric(fieldSetId,enableDisableFlag)
{
	try{
		var elmList=document.getElementById(fieldSetId).getElementsByTagName('*');	
		var true_false;
		if(enableDisableFlag=='ENABLE')		
		{
			true_false=false;
		}
		else if(enableDisableFlag=='DISABLE')		
		{
			true_false=true;
		}	
		
		for(var i=0; i<elmList.length; i++) {	
			  		 			
			switch(elmList[i].nodeName) 
			{
			        case 'INPUT':
			        	//elmList[i].readOnly=true_false;	
			        	if(elmList[i].ctrltype !='undefined' && elmList[i].ctrltype != null && elmList[i].ctrltype=='dropdown')
		        			elmList[i].disabled=true_false;
		        		else
		        			elmList[i].readOnly=true_false;
			           	if(elmList[i].type=='checkbox'){
							elmList[i].disabled=true_false;
						}						      	             	
			            break;    
			        case 'SELECT':
			            elmList[i].disabled=true_false;		            		           	
			            break;
			        case 'TEXTAREA':
			            elmList[i].readOnly=true_false;		            		           	
			            break;		        
			        default:       		        	
		   }
	    }
	}catch(e)
	{
		alert('error code 190: '+ e.message);
	}
}
function enableDisableOrderEntryPage(stateId,pageid){
	try{
		//==================================[ start clep codes ]==========================================================
		if(stateId==1 || stateId==2 || stateId==3 || stateId==4 || stateId==5){			
			if(pageid=='NEWORDER' || pageid=='CHANGEORDER'){
			
				
				//disabled all buttons as well as prevent onclick functionality for this control
				//check also this control is disabled true if it is then return false in onclick function
						
				document.getElementById('saveIcon').style.display="none";
				document.getElementById('validateOrderDiv').disabled=true;										
				document.getElementById('cancelServiceDiv').disabled=true;
				document.getElementById('deleteProductDiv').disabled=true;																			
			}
			if(pageid=='NEWORDER'){
																	
					document.getElementById('btnAddMoreService').disabled=true;			
					document.getElementById('productcatelog').disabled=true;
					document.getElementById('copyProductIcon').disabled=true;
					document.getElementById('CopyOrderImg').disabled=true;
					document.getElementById('idCopyChargeImg').disabled=true;		
					document.getElementById('idCancelAndCopyImg').disabled=true;	
					document.getElementById('idDeleteServiceImg').disabled=true;		
																												
				if(stateId==3){
					document.getElementById('idPublishbtn').disabled=true;
					document.getElementById('idSendToCOPCbtn').disabled=true;																					
				}
				if(stateId==4){
					document.getElementById('idSendToCOPCbtn').disabled=true;
				}
				
			}
			if(pageid=='CHANGEORDER'){
			
				if(stateId==3){
					document.getElementById('idPublishbtn').disabled=true;
				}
				document.getElementById('copyProductIcon').disabled=true;
				document.getElementById('productCatetogIcon').disabled=true;
				document.getElementById('addChildNodeIcon').disabled=true;	
				document.getElementById('idDeleteServiceDiv').disabled=true;	
				
				if(document.getElementById('idLSILookupImg')!=null)
				{
					document.getElementById('idLSILookupImg').disabled=true;
				}
					
										
			}
			if(pageid=='SERVICEATTRIBUTE' || pageid=='CHANGESERVICEATTRIBUTE'){
				document.getElementById('imgSave').disabled=true;
			}
			if(pageid=='BILLING_TRRIGER'){							
				document.getElementById("fnBillingTrigger4").disabled=true;
				document.getElementById("saveIcon").disabled=true;
				document.getElementById("copy_loc_date").disabled=true;
				document.getElementById("copy_loc_No").disabled=true;
			}
		}
		//======================================[ end clep codes ]========================================================
	}catch(e)
	{
		alert('error code 191: '+ e.message);
	}
}

//==============================[ Start Clep Codes ]======================================================
function enableDisableChargeFieldsetGeneric(arrEnableDisableFlags)
{
	try{
		var temp_chkChargeLine=document.getElementsByName('chkSelectChargeDetail');
		var temp_chargestable_rows=document.getElementById('tableCharges').rows;
		var tempLength=document.getElementById('tableCharges').rows.length;
			
		for(var i=Number(temp_chkChargeLine.length)-1;i>=0;i--)
		{		
			tempLength=tempLength-1;
			var true_false;
					
			if(arrEnableDisableFlags[i]=='ENABLE')		
			{			
				true_false=false;
			}
			else if(arrEnableDisableFlags[i]=='DISABLE')		
			{			
				true_false=true;
			}else
			{
				continue;
			}
					
			var elmList=temp_chargestable_rows[tempLength].getElementsByTagName('*');				
			for(var j=0; j<elmList.length; j++) {				
				switch(elmList[j].nodeName) 
				{
				        case 'INPUT':
				           	//elmList[j].readOnly=true_false;
				           	if(elmList[j].ctrltype !='undefined' && elmList[j].ctrltype != null && elmList[j].ctrltype=='dropdown')
				           		elmList[j].disabled=true_false;
			        		else
			        			elmList[j].readOnly=true_false;
							if(elmList[j].type=='checkbox'){
								elmList[j].disabled=true_false;
							}		           	             	
				            break;    
				        case 'SELECT':
				            elmList[j].disabled=true_false;		            		           	
				            break;
				        case 'TEXTAREA':
				            elmList[j].readOnly=true_false;		            		           	
				            break;		        
				        default:       		        	
			   }
	    	}
		}
	}catch(e)
	{
		alert('error code 192: '+ e.message);
	}
}
//==========================================[ End Clep Codes ]================================================